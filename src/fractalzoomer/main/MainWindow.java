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

/* Made by Kalonakis Chris, hrkalona@gmail.com */
/* Its obviously not a real time fractal zoomer, nor using high presicion (java are you kidding me?), but its a complete application */
/* Thanks to Josef Jelinek for the Supersampling code, and some of the palettes used. */
/* Thanks to Joel Yliluoma for the boundary tracing algorithm (old version). */
/* Thanks to Evgeny Demidov for the boundary tracing algorithm (currently used). */
/* Thanks to David J. Eck for some of the palettes and the orbit concept */
/* David E. Joyce (is he David J. Eck?) for the escape algorithms */
/* Thanks to Evgeny Demidov for the 3D heightmap algorithm. */
/* Thanks to Cogpar for parsing the user's formula */
/* Thanks to Botond Kosa for the bump mapping algorithm */
/* Thanks to claude for Fake Distance Estimation method any many more */
/* Thanks to Mika Seppa for the polar plane projection */
/* Thanks to Jerry Huxtable for many image processing algorithms */
/* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
/* Sorry for the absence of comments, this project was never supposed to reach this level! */
/* Also forgive me for the huge-packed main class, read above! */
package fractalzoomer.main;

//import com.alee.laf.WebLookAndFeel;
import fractalzoomer.app_updater.AppUpdater;
import fractalzoomer.core.drawing_algorithms.BoundaryTracingDraw;
import fractalzoomer.core.drawing_algorithms.BruteForceDraw;
import fractalzoomer.core.Complex;
import fractalzoomer.core.drawing_algorithms.DivideAndConquerDraw;
import fractalzoomer.core.DrawOrbit;
import fractalzoomer.gui.MainPanel;
import fractalzoomer.gui.RequestFocusListener;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.settings.SettingsJulia;
import fractalzoomer.settings.SettingsFractals;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.settings.SettingsFractals1049;
import fractalzoomer.settings.SettingsFractals1050;
import fractalzoomer.settings.SettingsFractals1053;
import fractalzoomer.settings.SettingsFractals1054;
import fractalzoomer.settings.SettingsFractals1055;
import fractalzoomer.settings.SettingsFractals1056;
import fractalzoomer.settings.SettingsFractals1057;
import fractalzoomer.settings.SettingsFractals1065;
import fractalzoomer.settings.SettingsJulia1049;
import fractalzoomer.settings.SettingsJulia1050;
import fractalzoomer.settings.SettingsJulia1053;
import fractalzoomer.settings.SettingsJulia1054;
import fractalzoomer.settings.SettingsJulia1055;
import fractalzoomer.settings.SettingsJulia1056;
import fractalzoomer.settings.SettingsJulia1057;
import fractalzoomer.gui.GreedyAlgorithmsFrame;
import fractalzoomer.gui.ColorChooserFrame;
import fractalzoomer.gui.CustomPaletteEditorFrame;
import fractalzoomer.gui.FileMenu;
import fractalzoomer.gui.FilterOrderSelectionPanel;
import fractalzoomer.gui.FiltersMenu;
import fractalzoomer.gui.FiltersOptionsFrame;
import fractalzoomer.gui.HelpMenu;
import fractalzoomer.gui.Infobar;
import fractalzoomer.gui.LinkLabel;
import fractalzoomer.gui.OptionsMenu;
import fractalzoomer.gui.PlaneVisualizationFrame;
import fractalzoomer.gui.RoundedPanel;
import fractalzoomer.gui.SplashFrame;
import fractalzoomer.gui.Statusbar;
import fractalzoomer.gui.Toolbar;
import fractalzoomer.gui.ToolsMenu;
import fractalzoomer.gui.UserFormulasHelpDialog;
import fractalzoomer.settings.SettingsFractals1058;
import fractalzoomer.settings.SettingsFractals1061;
import fractalzoomer.settings.SettingsFractals1062;
import fractalzoomer.settings.SettingsFractals1063;
import fractalzoomer.settings.SettingsFractals1064;
import fractalzoomer.settings.SettingsFractals1066;
import fractalzoomer.settings.SettingsJulia1058;
import fractalzoomer.settings.SettingsJulia1061;
import fractalzoomer.settings.SettingsJulia1062;
import fractalzoomer.settings.SettingsJulia1063;
import fractalzoomer.settings.SettingsJulia1064;
import fractalzoomer.settings.SettingsJulia1065;
import fractalzoomer.settings.SettingsJulia1066;
import fractalzoomer.utils.CompleteImageTask;
import fractalzoomer.utils.MathUtils;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 *
 * @author hrkalona
 */
public class MainWindow extends JFrame implements Constants {

    private boolean first_paint;
    private boolean[] filters;
    private int[] filters_options_vals;
    private int[][] filters_options_extra_vals;
    private int[] filters_order;
    private boolean orbit;
    private boolean julia;
    private boolean smoothing;
    private boolean exterior_de;
    private double exterior_de_factor;
    private int plane_type;
    private boolean show_orbit_converging_point;
    private int orbit_style;
    private int zoom_window_style;
    private boolean burning_ship;
    private boolean mandel_grass;
    private boolean whole_image_done;
    private boolean fast_julia_filters;
    private boolean periodicity_checking;
    private boolean color_cycling;
    private int color_cycling_speed;
    private boolean runsOnWindows;
    private boolean grid;
    private boolean old_grid;
    private boolean boundaries;
    private boolean old_boundaries;
    private boolean zoom_window;
    private boolean julia_map;
    private boolean polar_projection;
    private boolean old_polar_projection;
    private boolean d3;
    private boolean old_d3;
    private boolean perturbation;
    private boolean use_palette_domain_coloring;
    private boolean init_val;
    private boolean inverse_dem;
    private double color_intensity;
    private boolean entropy_coloring;
    private double entropy_palette_factor;
    private double en_noise_reducing_factor;
    private boolean offset_coloring;
    private int post_process_offset;
    private double of_noise_reducing_factor;
    private boolean greedy_algorithm;
    private boolean reversed_palette;
    private boolean apply_plane_on_julia;
    private boolean apply_plane_on_julia_seed;
    private boolean gaussian_scaling;
    private double gaussian_weight;
    private int gaussian_kernel;
    private double[] coefficients;
    private ThreadDraw[][] threads;
    private DrawOrbit pixels_orbit;
    private double[] perturbation_vals;
    private double[] initial_vals;
    private boolean variable_perturbation;
    private String perturbation_user_formula;
    private String[] user_perturbation_conditions;
    private String[] user_perturbation_condition_formula;
    private int user_perturbation_algorithm;
    private boolean variable_init_value;
    private String initial_value_user_formula;
    private String[] user_initial_value_conditions;
    private String[] user_initial_value_condition_formula;
    private int user_initial_value_algorithm;
    private double old_xCenter;
    private double old_yCenter;
    private double old_size;
    private double old_height_ratio;
    private double xCenter;
    private double yCenter;
    private double xJuliaCenter;
    private double yJuliaCenter;
    private double rotation;
    private int grid_tiles;
    private int boundaries_num;
    private double[] rotation_vals;
    private double[] old_rotation_vals;
    private double[] rotation_center;
    private double[] old_rotation_center;
    private double[] mandel_grass_vals;
    private double[] plane_transform_center;
    private double[] plane_transform_scales;
    private double[] orbit_vals;
    private double fiX;
    private double fiY;
    private double dfi;
    private double color_3d_blending;
    private double d3_size_scale;
    private int min_to_max_scaling;
    private int max_scaling;
    private int mx0;
    private int my0;
    private double d3_height_scale;
    private int height_algorithm;
    //private int d3_draw_method;
    private int boundaries_spacing_method;
    private int boundaries_type;
    private int detail;
    private int function;
    private int nova_method;
    private int color_interpolation;
    private int color_space;
    private int escaping_smooth_algorithm;
    private int converging_smooth_algorithm;
    private boolean first_seed;
    private boolean domain_coloring;
    private double size;
    private double height_ratio;
    private int max_iterations;
    private int n;
    private int greedy_algorithm_selection;
    private int julia_grid_first_dimension;
    private int color_choice;
    private int color_cycling_location;
    private int bailout_test_algorithm;
    private double scale_factor_palette_val;
    private int processing_alg;
    private double n_norm;
    private double bailout;
    private double coupling;
    private int coupling_method;
    private double coupling_amplitude;
    private double coupling_frequency;
    private int coupling_seed;
    private double plane_transform_angle;
    private double plane_transform_angle2;
    private double plane_transform_radius;
    private double plane_transform_amount;
    private int plane_transform_sides;
    private double bm_noise_reducing_factor;
    private double rp_noise_reducing_factor;
    private double circle_period;
    private double z_exponent;
    private double[] z_exponent_complex;
    private double[] z_exponent_nova;
    private double[] relaxation;
    private double zoom_factor;
    private int out_coloring_algorithm;
    private int in_coloring_algorithm;
    private String bailout_test_user_formula;
    private String bailout_test_user_formula2;
    private int bailout_test_comparison;
    private int image_size;
    private int domain_coloring_alg;
    private int color_smoothing_method;
    private long calculation_time;
    private String poly;
    private Color fractal_color;
    private Color dem_color;
    private Color special_color;
    private Color orbit_color;
    private Color grid_color;
    private Color boundaries_color;
    private Color zoom_window_color;
    private Color[] filters_colors;
    private Color[][] filters_extra_colors;
    private Parser parser;
    private String user_formula;
    private String user_formula2;
    private String[] user_formula_iteration_based;
    private String[] user_formula_conditions;
    private String[] user_formula_condition_formula;
    private String[] user_formula_coupled;
    private boolean user_formula_c;
    private int user_plane_algorithm;
    private String user_plane;
    private String[] user_plane_conditions;
    private String[] user_plane_condition_formula;
    private String user_fz_formula;
    private String user_dfz_formula;
    private String user_ddfz_formula;
    private boolean special_use_palette_color;
    private int bail_technique;
    private boolean bump_map;
    private double bumpMappingStrength;
    private double bumpMappingDepth;
    private double lightDirectionDegrees;
    private boolean fake_de;
    private boolean inverse_fake_dem;
    private double fake_de_factor;
    private boolean rainbow_palette;
    private double rainbow_palette_factor;
    private double en_blending;
    private double rp_blending;
    private double of_blending;
    private int entropy_offset;
    private int rainbow_offset;
    private boolean greyscale_coloring;
    private double gs_noise_reducing_factor;
    private int user_in_coloring_algorithm;
    private int user_out_coloring_algorithm;
    private boolean shade_height;
    private int shade_choice;
    private int shade_algorithm;
    private boolean shade_invert;
    private String outcoloring_formula;
    private String[] user_outcoloring_conditions;
    private String[] user_outcoloring_condition_formula;
    private String incoloring_formula;
    private String[] user_incoloring_conditions;
    private String[] user_incoloring_condition_formula;
    private boolean ctrlKeyPressed;
    private boolean shiftKeyPressed;
    private boolean altKeyPressed;
    private double oldDragX;
    private double oldDragY;
    private BufferedImage image;
    private BufferedImage fast_julia_image;
    private BufferedImage backup_orbit;
    private BufferedImage last_used;
    private JFileChooser file_chooser;
    private MainWindow ptr;
    private MainPanel main_panel;
    private Timer timer;
    private JScrollPane scroll_pane;
    private JProgressBar progress;
    private Toolbar toolbar;
    private Statusbar statusbar;
    private Infobar infobar;
    private JMenuBar menubar;
    private FileMenu file_menu;
    private OptionsMenu options_menu;
    private FiltersMenu filters_menu;
    private ToolsMenu tools_menu;
    private HelpMenu help_menu;
    private JCheckBoxMenuItem[] filters_opt;
    private JRadioButtonMenuItem fractal_functions[];
    private JRadioButtonMenuItem[] planes;
    private JRadioButtonMenuItem[] out_coloring_modes;
    private JRadioButtonMenuItem[] in_coloring_modes;
    private JRadioButtonMenuItem[] bailout_tests;
    private JFrame fract_color_frame;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private Cursor rotate_cursor;
    private int temp_color_cycling_location;
    private int i, k;

    /**
     * ** Custom Palettes ***
     */
    private int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    /**
     * *****************************
     */
    public MainWindow() {

        super();

        runsOnWindows = false;

        ptr = this;

        xCenter = 0;
        yCenter = 0;
        zoom_factor = 2;
        size = 6;

        height_ratio = 1;

        old_xCenter = xCenter;
        old_yCenter = yCenter;
        old_size = size;
        old_height_ratio = height_ratio;

        n = 2;
        julia_grid_first_dimension = 0;
        max_iterations = 500;

        grid_tiles = 6;
        boundaries_num = 6;

        n_norm = 2;

        filters = new boolean[34];

        filters_options_vals = new int[filters.length];
        filters_options_extra_vals = new int[2][filters.length];
        filters_order = new int[filters.length - 1];

        filters_colors = new Color[filters.length];
        filters_extra_colors = new Color[2][filters.length];

        fiX = 0.64;
        fiY = 0.82;
        dfi = 0.01;

        ctrlKeyPressed = false;
        shiftKeyPressed = false;
        altKeyPressed = false;

        scale_factor_palette_val = 0;
        processing_alg = PROCESSING_NONE;

        d3_height_scale = 1;
        height_algorithm = 0;
        d3_size_scale = 1;

        color_3d_blending = 0.84;

        domain_coloring = false;
        domain_coloring_alg = 0;
        use_palette_domain_coloring = false;

        //d3_draw_method = 0;
        boundaries_spacing_method = 0;
        boundaries_type = 0;

        color_choice = 0;

        greedy_algorithm = true;

        color_interpolation = 0;
        color_space = 0;

        z_exponent = 2;

        nova_method = NOVA_NEWTON;

        bailout = 2;
        bailout_test_algorithm = 0;

        bailout_test_user_formula = "norm(z)";
        bailout_test_user_formula2 = "bail";
        bailout_test_comparison = 1;

        escaping_smooth_algorithm = 0;
        converging_smooth_algorithm = 0;

        color_cycling_speed = 140;

        rotation_vals = new double[2];
        old_rotation_vals = new double[2];

        rotation = 0;

        color_intensity = 1;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        old_rotation_vals[0] = rotation_vals[0];
        old_rotation_vals[1] = rotation_vals[1];

        rotation_center = new double[2];
        old_rotation_center = new double[2];

        rotation_center[0] = 0;
        rotation_center[1] = 0;

        old_rotation_center[0] = rotation_center[0];
        old_rotation_center[1] = rotation_center[1];

        plane_transform_scales = new double[2];
        plane_transform_scales[0] = 0;
        plane_transform_scales[1] = 0;

        plane_transform_center = new double[2];
        plane_transform_center[0] = 0;
        plane_transform_center[1] = 0;

        plane_transform_angle = 0;
        plane_transform_angle2 = 0;
        plane_transform_radius = 2;
        plane_transform_sides = 3;
        plane_transform_amount = 0;

        perturbation_vals = new double[2];
        perturbation_vals[0] = 0;
        perturbation_vals[1] = 0;

        initial_vals = new double[2];
        initial_vals[0] = 0;
        initial_vals[1] = 0;

        apply_plane_on_julia = false;
        apply_plane_on_julia_seed = true;

        variable_perturbation = false;
        perturbation_user_formula = "0.0";
        variable_init_value = false;
        initial_value_user_formula = "c";

        user_perturbation_algorithm = 0;
        user_perturbation_conditions = new String[2];
        user_perturbation_conditions[0] = "im(c)";
        user_perturbation_conditions[1] = "0.0";
        user_perturbation_condition_formula = new String[3];
        user_perturbation_condition_formula[0] = "0.5";
        user_perturbation_condition_formula[1] = "0.0";
        user_perturbation_condition_formula[2] = "0.0";

        user_initial_value_algorithm = 0;
        user_initial_value_conditions = new String[2];
        user_initial_value_conditions[0] = "re(c)";
        user_initial_value_conditions[1] = "0.0";
        user_initial_value_condition_formula = new String[3];
        user_initial_value_condition_formula[0] = "c / 2";
        user_initial_value_condition_formula[1] = "c";
        user_initial_value_condition_formula[2] = "c";

        z_exponent_complex = new double[2];
        z_exponent_complex[0] = 2;
        z_exponent_complex[1] = 0;

        mandel_grass_vals = new double[2];
        mandel_grass_vals[0] = 0.125;
        mandel_grass_vals[1] = 0;

        z_exponent_nova = new double[2];
        z_exponent_nova[0] = 3;
        z_exponent_nova[1] = 0;

        relaxation = new double[2];
        relaxation[0] = 1;
        relaxation[1] = 0;

        orbit_vals = new double[2];
        orbit_vals[0] = 0;
        orbit_vals[1] = 0;

        perturbation = false;
        init_val = false;

        color_cycling_location = 0;
        temp_color_cycling_location = 0;

        exterior_de = false;
        exterior_de_factor = 2;
        inverse_dem = false;

        polar_projection = false;
        circle_period = 1;
        old_polar_projection = polar_projection;

        bump_map = false;
        bumpMappingStrength = 50;
        bumpMappingDepth = 50;
        lightDirectionDegrees = 0;

        color_smoothing_method = INTERPOLATION_LINEAR;

        gaussian_scaling = false;
        gaussian_weight = 2;
        gaussian_kernel = 1;

        fake_de = false;
        fake_de_factor = 1;
        inverse_fake_dem = false;

        rainbow_palette = false;
        rainbow_palette_factor = 1;

        entropy_coloring = false;
        entropy_palette_factor = 50;
        en_noise_reducing_factor = 0.4;

        offset_coloring = false;
        post_process_offset = 300;
        of_noise_reducing_factor = 0.4;

        en_blending = 0.7;
        rp_blending = 0.7;
        of_blending = 0.7;
        entropy_offset = 0;
        rainbow_offset = 0;

        greyscale_coloring = false;
        gs_noise_reducing_factor = 0.4;

        first_paint = false;

        plane_type = MU_PLANE;
        out_coloring_algorithm = ESCAPE_TIME;
        in_coloring_algorithm = MAXIMUM_ITERATIONS;

        shade_height = false;
        shade_choice = 0;
        shade_algorithm = 0;
        shade_invert = false;

        bm_noise_reducing_factor = 0.4;
        rp_noise_reducing_factor = 0.4;

        detail = 400;
        min_to_max_scaling = 0;
        max_scaling = 100;

        user_formula = "z^2 + c";
        user_formula2 = "c";

        user_plane = "z";
        user_plane_algorithm = 0;

        user_plane_conditions = new String[2];
        user_plane_conditions[0] = "im(z)";
        user_plane_conditions[1] = "0.125";

        user_plane_condition_formula = new String[3];
        user_plane_condition_formula[0] = "re(z) +(im(z) - 2 * (im(z) - 0.125)) * i";
        user_plane_condition_formula[1] = "z";
        user_plane_condition_formula[2] = "z";

        user_formula_iteration_based = new String[4];
        user_formula_iteration_based[0] = "z^2 + c";
        user_formula_iteration_based[1] = "z^3 + c";
        user_formula_iteration_based[2] = "z^4 + c";
        user_formula_iteration_based[3] = "z^5 + c";

        user_formula_conditions = new String[2];
        user_formula_conditions[0] = "re(z)";
        user_formula_conditions[1] = "0.0";

        user_formula_condition_formula = new String[3];
        user_formula_condition_formula[0] = "(z - 1) * c";
        user_formula_condition_formula[1] = "(z + 1) * c";
        user_formula_condition_formula[2] = "(z - 1) * c";

        user_formula_coupled = new String[3];
        user_formula_coupled[0] = "z^2 + c";
        user_formula_coupled[1] = "abs(z)^2 + c";
        user_formula_coupled[2] = "c";

        coupling = 0.1;
        coupling_method = 0;
        coupling_amplitude = 0.5;
        coupling_frequency = 0.001;
        coupling_seed = 1;

        user_in_coloring_algorithm = 0;
        user_out_coloring_algorithm = 0;

        outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

        user_outcoloring_conditions = new String[2];
        user_outcoloring_conditions[0] = "im(z)";
        user_outcoloring_conditions[1] = "0.0";

        user_outcoloring_condition_formula = new String[3];
        user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
        user_outcoloring_condition_formula[1] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001)) + 50";
        user_outcoloring_condition_formula[2] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

        incoloring_formula = "norm(sin(z)) * 100";

        user_incoloring_conditions = new String[2];
        user_incoloring_conditions[0] = "im(z)";
        user_incoloring_conditions[1] = "0.0";

        user_incoloring_condition_formula = new String[3];
        user_incoloring_condition_formula[0] = "norm(sin(z)) * 100";
        user_incoloring_condition_formula[1] = "norm(sin(z)) * 100 + 50";
        user_incoloring_condition_formula[2] = "norm(sin(z)) * 100";

        d3 = false;
        old_d3 = d3;
        orbit = false;
        orbit_style = 0;
        zoom_window_style = 0;
        first_seed = true;
        julia = false;
        burning_ship = false;
        mandel_grass = false;
        fast_julia_filters = false;
        periodicity_checking = false;
        whole_image_done = false;
        color_cycling = false;
        grid = false;
        old_grid = grid;
        boundaries = false;
        old_boundaries = boundaries;
        julia_map = false;
        zoom_window = false;
        special_use_palette_color = true;
        show_orbit_converging_point = true;

        greedy_algorithm_selection = BOUNDARY_TRACING;

        smoothing = false;

        fractal_color = Color.BLACK;
        dem_color = new Color(1, 0, 0);
        special_color = Color.WHITE;
        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;
        boundaries_color = new Color(0, 204, 102);
        zoom_window_color = Color.WHITE;

        parser = new Parser();
        user_formula_c = true;
        bail_technique = 0;

        function = 0;

        coefficients = new double[11];

        Locale.setDefault(Locale.US);

        grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("/fractalzoomer/icons/cursor_grab.gif").getImage(), new Point(16, 16), "grab");
        grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("/fractalzoomer/icons/cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");
        rotate_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("/fractalzoomer/icons/rotate.gif").getImage(), new Point(16, 16), "rotate");

        /*try {
         UIManager.setLookAndFeel ( new WebLookAndFeel () );
         UIManager.setLookAndFeel ( "com.alee.laf.WebLookAndFeel" );
         UIManager.setLookAndFeel ( WebLookAndFeel.class.getCanonicalName () );
         }
         catch(ClassNotFoundException ex) {
         }
         catch(InstantiationException ex) {
         }
         catch(IllegalAccessException ex) {
         }
         catch(UnsupportedLookAndFeelException ex) {
         }*/
        image_size = 788;
        setSize(806, 849);

        if(System.getProperty("os.name").toLowerCase().contains("windows")) {
            runsOnWindows = true;

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch(ClassNotFoundException ex) {
            }
            catch(InstantiationException ex) {
            }
            catch(IllegalAccessException ex) {
            }
            catch(UnsupportedLookAndFeelException ex) {
            }
        }
        else {
            runsOnWindows = false;
            setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 11));
        }

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();

        if(scrnsize.getHeight() > getHeight()) {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), (int)((scrnsize.getHeight() / 2) - (getHeight() / 2)) - 23);
        }
        else {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), 0);
        }

        setResizable(true);

        setLayout(new BorderLayout());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                exit();

            }
        });

        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        menubar = new JMenuBar();

        file_menu = new FileMenu(ptr, "File");

        options_menu = new OptionsMenu(ptr, "Options", color_choice, smoothing, custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg, show_orbit_converging_point, apply_plane_on_julia, apply_plane_on_julia_seed, out_coloring_algorithm, in_coloring_algorithm, function, plane_type, bailout_test_algorithm);

        fractal_functions = options_menu.getFractalFunctions();

        planes = options_menu.getPlanes();

        bailout_tests = options_menu.getBailoutTests();

        out_coloring_modes = options_menu.getOutColoringModes();
        in_coloring_modes = options_menu.getInColoringModes();

        tools_menu = new ToolsMenu(ptr, "Tools");

        filters_menu = new FiltersMenu(ptr, "Filters");
        filters_opt = filters_menu.getFilters();
        defaultFilters(true);
        filters_menu.setCheckedFilters(filters);

        help_menu = new HelpMenu(ptr, "Help");

        toolbar = new Toolbar(ptr);
        add(toolbar, BorderLayout.PAGE_START);

        JPanel status_bars = new JPanel();
        status_bars.setLayout(new BoxLayout(status_bars, BoxLayout.PAGE_AXIS));

        infobar = new Infobar(ptr, color_choice, smoothing, custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg, fractal_color);

        status_bars.add(infobar);

        statusbar = new Statusbar(ptr);
        progress = statusbar.getProgress();

        status_bars.add(statusbar);

        add(status_bars, BorderLayout.PAGE_END);

        main_panel = new MainPanel(this);
        scroll_pane = new JScrollPane(main_panel);

        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        add(scroll_pane);

        menubar.add(file_menu);
        menubar.add(options_menu);
        menubar.add(tools_menu);
        menubar.add(filters_menu);
        menubar.add(help_menu);

        setJMenuBar(menubar);

        scroll_pane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_CONTROL && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        ctrlKeyPressed = true;
                        scroll_pane.setCursor(grab_cursor);
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_SHIFT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        shiftKeyPressed = true;
                        scroll_pane.setCursor(rotate_cursor);
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_ALT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        altKeyPressed = true;
                        scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_CONTROL && ctrlKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        ctrlKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_SHIFT && shiftKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        shiftKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }
                else if(e.getKeyCode() == KeyEvent.VK_ALT && altKeyPressed) {
                    if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {
                        altKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }
            }

        });

        scroll_pane.setFocusable(true);
        scroll_pane.requestFocusInWindow();

        if(scroll_pane.getMouseWheelListeners().length > 0) {
            scroll_pane.removeMouseWheelListener(scroll_pane.getMouseWheelListeners()[0]); // remove the default listener
        }

        scroll_pane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(!orbit && !d3 && !julia_map && (!julia || julia && !first_seed)) {

                    if(!polar_projection) {
                        scrollPoint(e);
                    }
                    else {
                        scrollPointPolar(e);
                    }
                }
            }
        });

        scroll_pane.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if(ctrlKeyPressed || shiftKeyPressed) {
                    double curX = main_panel.getMousePosition().getX();
                    double curY = main_panel.getMousePosition().getY();

                    if(!(curX < 0 || curX > image_size || curY < 0 || curY > image_size)) {
                        oldDragX = curX;
                        oldDragY = curY;
                    }
                }

                if(altKeyPressed) {
                    selectPointForPlane(e);
                }
                else {
                    if(!orbit) {
                        if(!d3) {
                            if(!polar_projection) {
                                if(!julia) { //Regular
                                    selectPointFractal(e);
                                }
                                else {
                                    selectPointJulia(e);
                                }
                            }
                            else { //Polar
                                if(julia && first_seed) {
                                    selectPointJulia(e);
                                }
                                else {
                                    selectPointPolar(e);
                                }
                            }
                        }
                        else { // 3D
                            selectPoint3D(e);
                        }
                    }
                    else { //orbit
                        selectPointOrbit(e);
                    }
                }

                if(julia_map || julia && first_seed) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
                }
                else if((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else if(altKeyPressed) {
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else if(shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(whole_image_done) {
                    if(backup_orbit != null && orbit) {
                        try {
                            pixels_orbit.join();

                        }
                        catch(InterruptedException ex) {

                        }

                        System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    }

                    if(orbit || !color_cycling) {
                        main_panel.repaint();
                    }

                }

                if((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else if((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else if(shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                }
                else if(altKeyPressed) {
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(whole_image_done) {
                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                }

                if((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(whole_image_done) {
                    if(backup_orbit != null && orbit && e.getModifiers() == InputEvent.BUTTON1_MASK) {
                        System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    }
                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                }

                if((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

            }
        });

        addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent e) {

                if(!color_cycling) {
                    main_panel.repaint();
                }

                if(!threadsAvailable()) {
                    return;
                }

                if(e.getOldState() == NORMAL && e.getNewState() == MAXIMIZED_BOTH) {
                    image_size = getWidth() - 35;
                }
                else if(e.getNewState() == NORMAL && e.getOldState() == MAXIMIZED_BOTH) {
                    if(getHeight() > getWidth()) {
                        image_size = getHeight() - 61;
                    }
                    else {
                        image_size = getWidth() - 35;
                    }
                }
                else {
                    return;
                }

                if(image_size > 4000) {
                    tools_menu.getOrbit().setEnabled(false);
                    toolbar.getOrbitButton().setEnabled(false);
                    toolbar.getOrbitButton().setSelected(false);
                    tools_menu.getOrbit().setSelected(false);
                    setOrbitOption();
                }

                whole_image_done = false;

                old_grid = false;

                old_boundaries = false;

                if(!d3) {
                    ThreadDraw.setArrays(image_size);
                }

                main_panel.setPreferredSize(new Dimension(image_size, image_size));

                setOptions(false);

                if(!d3) {
                    progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));
                }

                progress.setValue(0);

                SwingUtilities.updateComponentTreeUI(ptr);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                //Graphics2D graphics = last_used.createGraphics();
                //graphics.drawImage(image, 0, 0, image_size, image_size, null);
                //last_used = null;
                last_used = null;

                image = null;

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                backup_orbit = null;

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                if(julia && first_seed) {
                    julia = false;
                    tools_menu.getJulia().setSelected(false);
                    toolbar.getJuliaButton().setSelected(false);

                    if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED) {
                        rootFindingMethodsSetEnabled(true);
                    }
                    fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                }

                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }

            }
        });

        scroll_pane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(whole_image_done) {

                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                }
            }
        });

        scroll_pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(whole_image_done) {

                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                }
            }
        });

        scroll_pane.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if(orbit) {
                    selectPointOrbit(e);
                }
                else if(d3) {
                    rotate3DModel(e);
                }
                else if(ctrlKeyPressed && !julia_map && (!julia || julia && !first_seed)) {
                    dragPoint(e);
                }
                else if(shiftKeyPressed && !julia_map && (!julia || julia && !first_seed)) {
                    rotatePoint(e);
                }
                else if(altKeyPressed && !julia_map && (!julia || julia && !first_seed)) {
                    selectPointForPlane(e);
                }

                if(julia_map || julia && first_seed) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
                }
                else if((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else if(shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                }
                else if(altKeyPressed) {
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                if((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

                try {
                    if(old_d3) {
                        statusbar.getReal().setText("Real");
                        statusbar.getImaginary().setText("Imaginary");
                        scroll_pane.setCursor(grab_cursor);
                        return;
                    }
                    else if(old_polar_projection) {

                        int x1 = (int)main_panel.getMousePosition().getX();
                        int y1 = (int)main_panel.getMousePosition().getY();

                        if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if(!color_cycling) {
                                main_panel.repaint();
                            }
                            return;
                        }

                        double start;
                        double center = Math.log(old_size);

                        double f, sf, cf, r;
                        double muly = (2 * circle_period * Math.PI) / image_size;

                        double mulx = muly * old_height_ratio;

                        start = center - mulx * image_size * 0.5;

                        f = y1 * muly;
                        sf = Math.sin(f);
                        cf = Math.cos(f);

                        r = Math.exp(x1 * mulx + start);

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(old_xCenter + r * cf, old_yCenter + r * sf, old_rotation_vals, old_rotation_center);

                        statusbar.getReal().setText("" + p.x);

                        statusbar.getImaginary().setText("" + p.y);

                    }
                    else {
                        int x1 = (int)main_panel.getMousePosition().getX();
                        int y1 = (int)main_panel.getMousePosition().getY();

                        if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if(!color_cycling) {
                                main_panel.repaint();
                            }
                            return;
                        }

                        double size_2_x = old_size * 0.5;
                        double size_2_y = (old_size * old_height_ratio) * 0.5;
                        double temp_xcenter_size = old_xCenter - size_2_x;
                        double temp_ycenter_size = old_yCenter + size_2_y;
                        double temp_size_image_size_x = old_size / image_size;
                        double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, old_rotation_vals, old_rotation_center);

                        statusbar.getReal().setText("" + p.x);

                        statusbar.getImaginary().setText("" + p.y);
                    }

                    if(!ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }

                }
                catch(NullPointerException ex) {
                }

                if(julia && first_seed) {
                    fastJulia();
                }

                if(zoom_window) {
                    main_panel.repaint();
                }

            }
        });

        loadPreferences();

        ThreadDraw.setArrays(image_size);

        threads = new ThreadDraw[n][n];

        main_panel.setPreferredSize(new Dimension(image_size, image_size));

        requestFocus();

        progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

        setOptions(false);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        fast_julia_image = new BufferedImage(FAST_JULIA_IMAGE_SIZE, FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

        backup_orbit = null;

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public boolean threadsAvailable() {

        try {
            for(int i = 0; i < threads.length; i++) {
                for(int j = 0; j < threads[i].length; j++) {
                    if(threads[i][j].isAlive()) {
                        return false;
                    }
                }
            }
        }
        catch(Exception ex) {
        }

        return true;

    }

    public MainPanel getMainPanel() {

        return main_panel;

    }

    public ThreadDraw[][] getThreads() {

        return threads;

    }

    public JProgressBar getProgressBar() {

        return progress;

    }

    public void reloadTitle() {

        String temp = "";

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

        temp = "Fractal Zoomer   #";

        switch (function) {
            case MANDELBROT:
                temp += "   Mandelbrot";
                break;
            case MANDELBROTCUBED:
                temp += "   Mandelbrot Cubed";
                break;
            case MANDELBROTFOURTH:
                temp += "   Mandelbrot Fourth";
                break;
            case MANDELBROTFIFTH:
                temp += "   Mandelbrot Fifth";
                break;
            case MANDELBROTSIXTH:
                temp += "   Mandelbrot Sixth";
                break;
            case MANDELBROTSEVENTH:
                temp += "   Mandelbrot Seventh";
                break;
            case MANDELBROTEIGHTH:
                temp += "   Mandelbrot Eighth";
                break;
            case MANDELBROTNINTH:
                temp += "   Mandelbrot Ninth";
                break;
            case MANDELBROTTENTH:
                temp += "   Mandelbrot Tenth";
                break;
            case MANDELBROTNTH:
                temp += "  z = z^" + z_exponent + " + c";
                break;
            case MANDELBROTWTH:
                temp += "  z = z^(" + Complex.toString2(z_exponent_complex[0], z_exponent_complex[1]) + ") + c";
                break;
            case MANDELPOLY:
                temp += "   Multibrot " + poly + " + c";
                break;
            case LAMBDA:
                temp += "   Lambda";
                break;
            case MAGNET1:
                temp += "   Magnet 1";
                break;
            case MAGNET2:
                temp += "   Magnet 2";
                break;
            case NEWTON3:
                temp += "   Newton p(z) = z^3 -1";
                break;
            case NEWTON4:
                temp += "   Newton p(z) = z^4 -1";
                break;
            case NEWTONGENERALIZED3:
                temp += "   Newton p(z) = z^3 -2z +2";
                break;
            case NEWTONGENERALIZED8:
                temp += "   Newton p(z) = z^8 +15z^4 -16";
                break;
            case NEWTONSIN:
                temp += "   Newton f(z) = sin(z)";
                break;
            case NEWTONCOS:
                temp += "   Newton f(z) = cos(z)";
                break;
            case NEWTONPOLY:
                temp += "   Newton " + poly;
                break;
            case NEWTONFORMULA:
                temp += "   Newton Formula";
                break;
            case HALLEY3:
                temp += "   Halley p(z) = z^3 -1";
                break;
            case HALLEY4:
                temp += "   Halley p(z) = z^4 -1";
                break;
            case HALLEYGENERALIZED3:
                temp += "   Halley p(z) = z^3 -2z +2";
                break;
            case HALLEYGENERALIZED8:
                temp += "   Halley p(z) = z^8 +15z^4 -16";
                break;
            case HALLEYSIN:
                temp += "   Halley f(z) = sin(z)";
                break;
            case HALLEYCOS:
                temp += "   Halley f(z) = cos(z)";
                break;
            case HALLEYPOLY:
                temp += "   Halley " + poly;
                break;
            case HALLEYFORMULA:
                temp += "   Halley Formula";
                break;
            case SCHRODER3:
                temp += "   Schroder p(z) = z^3 -1";
                break;
            case SCHRODER4:
                temp += "   Schroder p(z) = z^4 -1";
                break;
            case SCHRODERGENERALIZED3:
                temp += "   Schroder p(z) = z^3 -2z +2";
                break;
            case SCHRODERGENERALIZED8:
                temp += "   Schroder p(z) = z^8 +15z^4 -16";
                break;
            case SCHRODERSIN:
                temp += "   Schroder f(z) = sin(z)";
                break;
            case SCHRODERCOS:
                temp += "   Schroder f(z) = cos(z)";
                break;
            case SCHRODERPOLY:
                temp += "   Schroder " + poly;
                break;
            case SCHRODERFORMULA:
                temp += "   Schroder Formula";
                break;
            case HOUSEHOLDER3:
                temp += "   Householder p(z) = z^3 -1";
                break;
            case HOUSEHOLDER4:
                temp += "   Householder p(z) = z^4 -1";
                break;
            case HOUSEHOLDERGENERALIZED3:
                temp += "   Householder p(z) = z^3 -2z +2";
                break;
            case HOUSEHOLDERGENERALIZED8:
                temp += "   Householder p(z) = z^8 +15z^4 -16";
                break;
            case HOUSEHOLDERSIN:
                temp += "   Householder f(z) = sin(z)";
                break;
            case HOUSEHOLDERCOS:
                temp += "   Householder f(z) = cos(z)";
                break;
            case HOUSEHOLDERPOLY:
                temp += "   Householder " + poly;
                break;
            case HOUSEHOLDERFORMULA:
                temp += "   Householder Formula";
                break;
            case SECANT3:
                temp += "   Secant p(z) = z^3 -1";
                break;
            case SECANT4:
                temp += "   Secant p(z) = z^4 -1";
                break;
            case SECANTGENERALIZED3:
                temp += "   Secant p(z) = z^3 -2z +2";
                break;
            case SECANTGENERALIZED8:
                temp += "   Secant p(z) = z^8 +15z^4 -16";
                break;
            case SECANTCOS:
                temp += "   Secant f(z) = cos(z)";
                break;
            case SECANTPOLY:
                temp += "   Secant " + poly;
                break;
            case SECANTFORMULA:
                temp += "   Secant Formula";
                break;
            case STEFFENSEN3:
                temp += "   Steffensen p(z) = z^3 -1";
                break;
            case STEFFENSEN4:
                temp += "   Steffensen p(z) = z^4 -1";
                break;
            case STEFFENSENGENERALIZED3:
                temp += "   Steffensen p(z) = z^3 -2z +2";
                break;
            case STEFFENSENPOLY:
                temp += "   Steffensen " + poly;
                break;
            case STEFFENSENFORMULA:
                temp += "   Steffensen Formula";
                break;
            case MULLER3:
                temp += "   Muller p(z) = z^3 -1";
                break;
            case MULLER4:
                temp += "   Muller p(z) = z^4 -1";
                break;
            case MULLERGENERALIZED3:
                temp += "   Muller p(z) = z^3 -2z +2";
                break;
            case MULLERGENERALIZED8:
                temp += "   Muller p(z) = z^8 +15z^4 -16";
                break;
            case MULLERSIN:
                temp += "   Muller f(z) = sin(z)";
                break;
            case MULLERCOS:
                temp += "   Muller f(z) = cos(z)";
                break;
            case MULLERPOLY:
                temp += "   Muller " + poly;
                break;
            case MULLERFORMULA:
                temp += "   Muller Formula";
                break;
            case NOVA:
                switch (nova_method) {

                    case MainWindow.NOVA_NEWTON:
                        temp += "   Nova-Newton, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_HALLEY:
                        temp += "   Nova-Halley, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_SCHRODER:
                        temp += "   Nova-Schroder, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_HOUSEHOLDER:
                        temp += "   Nova-Householder, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_SECANT:
                        temp += "   Nova-Secant, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_STEFFENSEN:
                        temp += "   Nova-Steffensen, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                    case MainWindow.NOVA_MULLER:
                        temp += "   Nova-Muller, e: " + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ", r: " + Complex.toString2(relaxation[0], relaxation[1]);
                        break;
                }

                break;
            case BARNSLEY1:
                temp += "   Barnsley 1";
                break;
            case BARNSLEY2:
                temp += "   Barnsley 2";
                break;
            case BARNSLEY3:
                temp += "   Barnsley 3";
                break;
            case MANDELBAR:
                temp += "   Mandelbar";
                break;
            case SPIDER:
                temp += "   Spider";
                break;
            case MANOWAR:
                temp += "   Manowar";
                break;
            case PHOENIX:
                temp += "   Phoenix";
                break;
            case SIERPINSKI_GASKET:
                temp += "   Sierpinski Gasket";
                break;
            case EXP:
                temp += "   z = exp(z) + c";
                break;
            case LOG:
                temp += "   z = log(z) + c";
                break;
            case SIN:
                temp += "   z = sin(z) + c";
                break;
            case COS:
                temp += "   z = cos(z) + c";
                break;
            case TAN:
                temp += "   z = tan(z) + c";
                break;
            case COT:
                temp += "   z = cot(z) + c";
                break;
            case SINH:
                temp += "   z = sinh(z) + c";
                break;
            case COSH:
                temp += "   z = cosh(z) + c";
                break;
            case TANH:
                temp += "   z = tanh(z) + c";
                break;
            case COTH:
                temp += "   z = coth(z) + c";
                break;
            case FORMULA30:
                temp += "   z = sin(z)c";
                break;
            case FORMULA31:
                temp += "   z = cos(z)c";
                break;
            case FROTHY_BASIN:
                temp += "   Frothy Basin";
                break;
            case FORMULA1:
                temp += "   z = 0.25z^2 + c + (z^2 + c)^-2";
                break;
            case FORMULA2:
                temp += "   z = c(z + z^-1)";
                break;
            case FORMULA3:
                temp += "   z = c(z^3 + z^-3)";
                break;
            case FORMULA4:
                temp += "   z = c(5z^-1 + z^5)";
                break;
            case FORMULA5:
                temp += "   z = c(z^5 - 5z)";
                break;
            case FORMULA6:
                temp += "   z = c(2z^2 - z^4)";
                break;
            case FORMULA7:
                temp += "   z = c(2z^-2 - z^-4)";
                break;
            case FORMULA8:
                temp += "   z = c(5z^-1 - z^-5)";
                break;
            case FORMULA9:
                temp += "   z = c(-3z^-3 + z^-9)";
                break;
            case FORMULA10:
                temp += "   z = c(z^2.5 + z^-2.5)";
                break;
            case FORMULA11:
                temp += "   z = c(z^2i + z^-2)";
                break;
            case FORMULA12:
                temp += "   z = c(-3z^-2 + 2z^-3)";
                break;
            case FORMULA13:
                temp += "   z = (c(2z - z^2) + 1)^2";
                break;
            case FORMULA14:
                temp += "   z = (c(2z - z^2) + 1)^3";
                break;
            case FORMULA15:
                temp += "   z = (c(z - z^2) + 1)^2";
                break;
            case FORMULA16:
                temp += "   z = (c(z - z^2) + 1)^3";
                break;
            case FORMULA17:
                temp += "   z = (c(z - z^2) + 1)^4";
                break;
            case FORMULA18:
                temp += "   z = (c(z^3 - z^4) + 1)^5";
                break;
            case FORMULA19:
                temp += "   z = abs(z^-1) + c";
                break;
            case FORMULA20:
                temp += "   z = abs(z^2) + c";
                break;
            case FORMULA21:
                temp += "   z = abs(z)/abs(c) + c";
                break;
            case FORMULA22:
                temp += "   z = abs(z/c) + c";
                break;
            case FORMULA23:
                temp += "   z = abs(z/(0.5 + 0.5i)) + c";
                break;
            case FORMULA24:
                temp += "   z = abs(z)/c + c";
                break;
            case FORMULA25:
                temp += "   z = abs(z)^-3 + c";
                break;
            case FORMULA26:
                temp += "   z = abs(z^-3) + c";
                break;
            case FORMULA27:
                temp += "   z = c((z + 2)^6 + (z + 2)^-6)";
                break;
            case FORMULA28:
                temp += "   z = cz^2 + (cz^2)^-1";
                break;
            case FORMULA29:
                temp += "   z = cz^2 + 1 + (cz^2 + 1)^-1";
                break;
            case FORMULA32:
                temp += "   z = zlog(z + 1) + c";
                break;
            case FORMULA33:
                temp += "   z = zsin(z) + c";
                break;
            case FORMULA34:
                temp += "   z = zsinh(z) + c";
                break;
            case FORMULA35:
                temp += "   z = 2 - 2cos(z) + c";
                break;
            case FORMULA36:
                temp += "   z = 2cosh(z) - 2 + c";
                break;
            case FORMULA37:
                temp += "   z = zsin(z) - c^2";
                break;
            case FORMULA38:
                temp += "   z = z^2 + c/z^2";
                break;
            case FORMULA39:
                temp += "   z = (z^2)exp(1/z) + c";
                break;
            case FORMULA40:
                temp += "   z = (z^10 + c)/(z^8 + c) + c - 2";
                break;
            case FORMULA41:
                temp += "   z = (z^16 - 10)/(z^14 - c) + c - 6";
                break;
            case FORMULA42:
                temp += "   z = z - (z^3 + c)/(3z)";
                break;
            case FORMULA43:
                temp += "   z = z - (z^4 + c)/(4z^2)";
                break;
            case FORMULA44:
                temp += "   z = z - (z^5 + c)/(5z^3)";
                break;
            case FORMULA45:
                temp += "   z = z - (z^8 + c)/(8z^6)";
                break;
            case FORMULA46:
                temp += "   z = (-0.4 + 0.2i)cz^2 + (-0.275i/c)z + c";
                break;
            case USER_FORMULA:
                temp += "   User Formula";
                break;
            case USER_FORMULA_ITERATION_BASED:
                temp += "   User Formula Iteration Based";
                break;
            case USER_FORMULA_CONDITIONAL:
                temp += "   User Formula Conditional";
                break;
            case USER_FORMULA_COUPLED:
                temp += "   User Formula Coupled";
                break;
            case SZEGEDI_BUTTERFLY1:
                temp += "   Szegedi Butterfly 1";
                break;
            case SZEGEDI_BUTTERFLY2:
                temp += "   Szegedi Butterfly 2";
                break;
            case COUPLED_MANDELBROT:
                temp += "   Coupled Mandelbrot";
                break;
            case COUPLED_MANDELBROT_BURNING_SHIP:
                temp += "   Coupled Mandelbrot-Burning Ship";
                break;

        }

        temp += "   Center: " + Complex.toString2(p.x, p.y) + "   Size: " + size;

        setTitle(temp);

        if(d3) {
            statusbar.getReal().setText("Real");
            statusbar.getImaginary().setText("Imaginary");
        }
        else if(polar_projection) {
            try {
                int x1 = (int)main_panel.getMousePosition().getX();
                int y1 = (int)main_panel.getMousePosition().getY();

                if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                    return;
                }

                double start;
                double center = Math.log(size);

                double f, sf, cf, r;
                double muly = (2 * circle_period * Math.PI) / image_size;

                double mulx = muly * height_ratio;

                start = center - mulx * image_size * 0.5;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

                statusbar.getReal().setText("" + p2.x);

                statusbar.getImaginary().setText("" + p2.y);
            }
            catch(Exception ex) {
            }
        }
        else {
            try {
                int x1 = (int)main_panel.getMousePosition().getX();
                int y1 = (int)main_panel.getMousePosition().getY();

                double size_2_x = size * 0.5;
                double size_2_y = (size * height_ratio) * 0.5;
                double temp_xcenter_size = xCenter - size_2_x;
                double temp_ycenter_size = yCenter + size_2_y;
                double temp_size_image_size_x = size / image_size;
                double temp_size_image_size_y = (size * height_ratio) / image_size;

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, rotation_vals, rotation_center);

                statusbar.getReal().setText("" + p2.x);

                statusbar.getImaginary().setText("" + p2.y);
            }
            catch(Exception ex) {
            }
        }
    }

    private void createThreads(boolean quickDraw) {

        ThreadDraw.resetThreadData(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        try {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(julia) {
                        if(d3) {
                            if(greedy_algorithm) {
                                if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                                }
                                else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                                }
                            }
                            else {
                                threads[i][j] = new BruteForceDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                            }
                        }
                        else {
                            if(greedy_algorithm) {
                                if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                                }
                                else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                                }
                            }
                            else {
                                threads[i][j] = new BruteForceDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                            }
                        }
                    }
                    else {
                        if(d3) {
                            if(greedy_algorithm) {
                                if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                                }
                                else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                                }
                            }
                            else {
                                threads[i][j] = new BruteForceDraw(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                            }
                        }
                        else {
                            if(greedy_algorithm) {
                                if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                                }
                                else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                                }
                            }
                            else {
                                threads[i][j] = new BruteForceDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                            }
                        }
                    }
                }
            }
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            main_panel.repaint();
            savePreferences();
            System.exit(-1);
        }
    }

    private void startThreads(int t) {

        for(int i = 0; i < t; i++) {
            for(int j = 0; j < t; j++) {
                threads[i][j].start();
            }
        }

    }

    public void saveSettings() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter)evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI)file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if(index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Settings");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            ObjectOutputStream file_temp = null;

            try {
                file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
                SettingsFractals settings;
                if(julia) {
                    settings = new SettingsJulia1066(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor, dem_color, special_color, special_use_palette_color, rainbow_palette, rainbow_palette_factor, filters, filters_options_vals, scale_factor_palette_val, processing_alg, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, use_palette_domain_coloring, domain_coloring_alg, inverse_dem, inverse_fake_dem, filters_options_extra_vals, filters_extra_colors, color_smoothing_method, filters_order, bm_noise_reducing_factor, rp_noise_reducing_factor, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, apply_plane_on_julia_seed, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);
                }
                else {
                    int temp_bailout_test_algorithm = 0;

                    if(function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                        temp_bailout_test_algorithm = bailout_test_algorithm;
                    }

                    settings = new SettingsFractals1066(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, temp_bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, dem_color, special_color, special_use_palette_color, rainbow_palette, rainbow_palette_factor, filters, filters_options_vals, scale_factor_palette_val, processing_alg, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, use_palette_domain_coloring, domain_coloring_alg, inverse_dem, inverse_fake_dem, filters_options_extra_vals, filters_extra_colors, color_smoothing_method, filters_order, bm_noise_reducing_factor, rp_noise_reducing_factor, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, apply_plane_on_julia_seed, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                }
                file_temp.writeObject(settings);
                file_temp.flush();
            }
            catch(IOException ex) {
            }

            try {
                file_temp.close();
            }
            catch(Exception ex) {
            }
        }

        main_panel.repaint();

    }

    private boolean isSettingsJulia(String class_name) {

        String temp = class_name;
        int index = temp.lastIndexOf(".");

        if(index != -1) {
            temp = temp.substring(index + 1, temp.length());
        }

        return temp.equals("SettingsJulia") || temp.equals("SettingsJulia1049") || temp.equals("SettingsJulia1050") || temp.equals("SettingsJulia1053") || temp.equals("SettingsJulia1054") || temp.equals("SettingsJulia1055") || temp.equals("SettingsJulia1056") || temp.equals("SettingsJulia1057") || temp.equals("SettingsJulia1058") || temp.equals("SettingsJulia1061") || temp.equals("SettingsJulia1062") || temp.equals("SettingsJulia1063") || temp.equals("SettingsJulia1064") || temp.equals("SettingsJulia1065") || temp.equals("SettingsJulia1066");

    }

    private int getSettingsVersion(String class_name) {

        String temp = class_name;
        int index = temp.lastIndexOf(".");

        if(index != -1) {
            temp = temp.substring(index + 1, temp.length());
        }

        if(temp.equals("SettingsJulia") || temp.equals("SettingsFractals")) {
            return 1048;
        }
        else if(temp.equals("SettingsJulia1049") || temp.equals("SettingsFractals1049")) {
            return 1049;
        }
        else if(temp.equals("SettingsJulia1050") || temp.equals("SettingsFractals1050")) {
            return 1050;
        }
        else if(temp.equals("SettingsJulia1053") || temp.equals("SettingsFractals1053")) {
            return 1053;
        }
        else if(temp.equals("SettingsJulia1054") || temp.equals("SettingsFractals1054")) {
            return 1054;
        }
        else if(temp.equals("SettingsJulia1055") || temp.equals("SettingsFractals1055")) {
            return 1055;
        }
        else if(temp.equals("SettingsJulia1056") || temp.equals("SettingsFractals1056")) {
            return 1056;
        }
        else if(temp.equals("SettingsJulia1057") || temp.equals("SettingsFractals1057")) {
            return 1057;
        }
        else if(temp.equals("SettingsJulia1058") || temp.equals("SettingsFractals1058")) {
            return 1058;
        }
        else if(temp.equals("SettingsJulia1061") || temp.equals("SettingsFractals1061")) {
            return 1061;
        }
        else if(temp.equals("SettingsJulia1062") || temp.equals("SettingsFractals1062")) {
            return 1062;
        }
        else if(temp.equals("SettingsJulia1063") || temp.equals("SettingsFractals1063")) {
            return 1063;
        }
        else if(temp.equals("SettingsJulia1064") || temp.equals("SettingsFractals1064")) {
            return 1064;
        }
        else if(temp.equals("SettingsJulia1065") || temp.equals("SettingsFractals1065")) {
            return 1065;
        }
        else if(temp.equals("SettingsJulia1066") || temp.equals("SettingsFractals1066")) {
            return 1066;
        }

        return 9999;

    }

    private void readSettings(SettingsFractals settings) {
        int version = getSettingsVersion("" + settings.getClass());

        if(isSettingsJulia("" + settings.getClass())) {
            if(version == 1048) {
                xJuliaCenter = ((SettingsJulia)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia)settings).getYJuliaCenter();
            }
            else if(version == 1049) {
                xJuliaCenter = ((SettingsJulia1049)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1049)settings).getYJuliaCenter();
            }
            else if(version == 1050) {
                xJuliaCenter = ((SettingsJulia1050)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1050)settings).getYJuliaCenter();
            }
            else if(version == 1053) {
                xJuliaCenter = ((SettingsJulia1053)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1053)settings).getYJuliaCenter();
            }
            else if(version == 1054) {
                xJuliaCenter = ((SettingsJulia1054)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1054)settings).getYJuliaCenter();
            }
            else if(version == 1055) {
                xJuliaCenter = ((SettingsJulia1055)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1055)settings).getYJuliaCenter();
            }
            else if(version == 1056) {
                xJuliaCenter = ((SettingsJulia1056)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1056)settings).getYJuliaCenter();
            }
            else if(version == 1057) {
                xJuliaCenter = ((SettingsJulia1057)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1057)settings).getYJuliaCenter();
            }
            else if(version == 1058) {
                xJuliaCenter = ((SettingsJulia1058)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1058)settings).getYJuliaCenter();
            }
            else if(version == 1061) {
                xJuliaCenter = ((SettingsJulia1061)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1061)settings).getYJuliaCenter();
            }
            else if(version == 1062) {
                xJuliaCenter = ((SettingsJulia1062)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1062)settings).getYJuliaCenter();
            }
            else if(version == 1063) {
                xJuliaCenter = ((SettingsJulia1063)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1063)settings).getYJuliaCenter();
            }
            else if(version == 1064) {
                xJuliaCenter = ((SettingsJulia1064)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1064)settings).getYJuliaCenter();
            }
            else if(version == 1065) {
                xJuliaCenter = ((SettingsJulia1065)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1065)settings).getYJuliaCenter();
            }
            else if(version == 1066) {
                xJuliaCenter = ((SettingsJulia1066)settings).getXJuliaCenter();
                yJuliaCenter = ((SettingsJulia1066)settings).getYJuliaCenter();
            }

            julia = true;
            first_seed = false;
        }
        else {
            julia = false;
            first_seed = true;

            perturbation = settings.getPerturbation();
            init_val = settings.getInitVal();

            if(perturbation) {
                if(version < 1056) {
                    variable_perturbation = false;
                    perturbation_vals = settings.getPerturbationVals();
                }
                else {
                    variable_perturbation = ((SettingsFractals1056)settings).getVariablePerturbation();

                    if(variable_perturbation) {
                        if(version < 1058) {
                            user_perturbation_algorithm = 0;
                        }
                        else {
                            user_perturbation_algorithm = ((SettingsFractals1058)settings).getUserPerturbationAlgorithm();
                        }

                        if(user_perturbation_algorithm == 0) {
                            perturbation_user_formula = ((SettingsFractals1056)settings).getPerturbationUserFormula();
                        }
                        else {
                            user_perturbation_conditions = ((SettingsFractals1058)settings).getUserPerturbationConditions();
                            user_perturbation_condition_formula = ((SettingsFractals1058)settings).getUserPerturbationConditionFormula();
                        }
                    }
                    else {
                        perturbation_vals = settings.getPerturbationVals();
                    }
                }
            }

            if(init_val) {
                if(version < 1056) {
                    variable_init_value = false;
                    initial_vals = settings.getInitialVals();
                }
                else {
                    variable_init_value = ((SettingsFractals1056)settings).getVariableInitValue();

                    if(variable_init_value) {
                        if(version < 1058) {
                            user_initial_value_algorithm = 0;
                        }
                        else {
                            user_initial_value_algorithm = ((SettingsFractals1058)settings).getUserInitialValueAlgorithm();
                        }

                        if(user_initial_value_algorithm == 0) {
                            initial_value_user_formula = ((SettingsFractals1056)settings).getInitialValueUserFormula();
                        }
                        else {
                            user_initial_value_conditions = ((SettingsFractals1058)settings).getUserInitialValueConditions();
                            user_initial_value_condition_formula = ((SettingsFractals1058)settings).getUserInitialValueConditionFormula();
                        }
                    }
                    else {
                        initial_vals = settings.getInitialVals();
                    }
                }
            }
        }

        xCenter = settings.getXCenter();
        yCenter = settings.getYCenter();
        size = settings.getSize();
        max_iterations = settings.getMaxIterations();
        color_choice = settings.getColorChoice();

        fractal_color = settings.getFractalColor();

        if(version < 1061) {
            dem_color = new Color(1, 0, 0);
            special_color = Color.WHITE;
            special_use_palette_color = true;
        }
        else {
            dem_color = ((SettingsFractals1061)settings).getDemColor();
            special_color = ((SettingsFractals1061)settings).getSpecialColor();
            special_use_palette_color = ((SettingsFractals1061)settings).getSpecialUsePaletteColor();
        }

        if(version < 1062) {
            rainbow_palette = false;
            rainbow_palette_factor = 1;

            boolean active_filter = false;
            for(int i = 0; i < filters.length; i++) {
                if(filters[i]) {
                    active_filter = true;
                    break;
                }
            }

            if(active_filter) {
                int ans = JOptionPane.showConfirmDialog(scroll_pane, "Some image filters are activated.\nDo you want to reset them to the default values?", "Image Filters", JOptionPane.YES_NO_OPTION);
                if(ans == JOptionPane.YES_OPTION) {
                    defaultFilters(true);
                }
            }
        }
        else {
            rainbow_palette = ((SettingsFractals1062)settings).getRainbowPalette();
            rainbow_palette_factor = ((SettingsFractals1062)settings).getRainbowPaletteFactor();

            defaultFilters(true);

            boolean[] loaded_filters = ((SettingsFractals1062)settings).getFilters();

            int[] temp_vals = ((SettingsFractals1062)settings).getFiltersOptionsVals();

            Color[] temp_colors;
            int[][] temp_extra_vals;
            Color[][] temp_extra_colors;
            int[] temp_filters_order;

            if(version < 1063) {
                temp_colors = filters_colors;
            }
            else {
                temp_colors = ((SettingsFractals1063)settings).getFiltersColors();
            }

            if(version < 1064) {
                temp_extra_vals = filters_options_extra_vals;
                temp_extra_colors = filters_extra_colors;
            }
            else {
                temp_extra_vals = ((SettingsFractals1064)settings).getFilterExtraVals();
                temp_extra_colors = ((SettingsFractals1064)settings).getFilterExtraColors();
            }

            if(version < 1065) {
                temp_filters_order = filters_order;
            }
            else {
                temp_filters_order = ((SettingsFractals1065)settings).getFiltersOrder();
            }

            for(int i = 0; i < loaded_filters.length; i++) {
                if(loaded_filters[i]) {
                    filters[i] = loaded_filters[i];
                    filters_colors[i] = temp_colors[i];

                    if(i == EMBOSS && version == 1062) {
                        filters_options_vals[i] = filters_options_vals[i] + temp_vals[i] % 10;
                    }
                    else {
                        filters_options_vals[i] = temp_vals[i];
                    }

                    filters_options_extra_vals[0][i] = temp_extra_vals[0][i];
                    filters_options_extra_vals[1][i] = temp_extra_vals[1][i];
                    filters_extra_colors[0][i] = temp_extra_colors[0][i];
                    filters_extra_colors[1][i] = temp_extra_colors[1][i];
                }
            }

            if(temp_filters_order.length == filters_order.length) {
                for(int i = 0; i < temp_filters_order.length; i++) {
                    filters_order[i] = temp_filters_order[i];
                }
            }
            else if(filters_order.length > temp_filters_order.length) {
                int[] filters_order_union = new int[filters_order.length];
                for(int i = 0; i < temp_filters_order.length; i++) {
                    filters_order_union[i] = temp_filters_order[i];
                }

                int k = temp_filters_order.length;
                for(int i = 0; i < filters_order.length; i++) {//add all the missing filters to the end
                    boolean found = false;
                    for(int j = 0; j < filters_order_union.length; j++) {
                        if(filters_order_union[j] == filters_order[i]) {
                            found = true;
                            break;
                        }
                    }

                    if(!found) {
                        filters_order_union[k] = filters_order[i];
                        k++;
                    }
                }

                for(int i = 0; i < filters_order_union.length; i++) {
                    filters_order[i] = filters_order_union[i];
                }
            }
        }

        function = settings.getFunction();
        bailout = settings.getBailout();
        burning_ship = settings.getBurningShip();

        mandel_grass = settings.getMandelGrass();

        if(mandel_grass) {
            mandel_grass_vals = settings.getMandelGrassVals();
        }

        color_cycling_location = settings.getColorCyclingLocation();
        plane_type = settings.getPlaneType();

        if(version < 1057) {
            apply_plane_on_julia = false;
        }
        else {
            apply_plane_on_julia = ((SettingsFractals1057)settings).getApplyPlaneOnJulia();
        }

        if(version < 1066) {
            entropy_coloring = false;
            entropy_palette_factor = 50;
            en_noise_reducing_factor = 0.4;
            apply_plane_on_julia_seed = true;
            offset_coloring = false;
            post_process_offset = 300;
            of_noise_reducing_factor = 0.4;
            en_blending = 0.7;
            of_blending = 0.7;
            rp_blending = 0.7;
            entropy_offset = 0;
            rainbow_offset = 0;
            greyscale_coloring = false;
            gs_noise_reducing_factor = 0.4;
        }
        else {
            entropy_coloring = ((SettingsFractals1066)settings).getEntropyColoring();
            entropy_palette_factor = ((SettingsFractals1066)settings).getEntropyPaletteFactor();
            en_noise_reducing_factor = ((SettingsFractals1066)settings).getEntropyColoringNoiseReducingFactor();
            apply_plane_on_julia_seed = ((SettingsFractals1066)settings).getApplyPlaneOnJuliaSeed();
            offset_coloring = ((SettingsFractals1066)settings).getOffsetColoring();
            post_process_offset = ((SettingsFractals1066)settings).getPostProcessOffset();
            of_noise_reducing_factor = ((SettingsFractals1066)settings).getOffsetColoringNoiseReducingFactor();
            en_blending = ((SettingsFractals1066)settings).getEntropyColoringBlending();
            of_blending = ((SettingsFractals1066)settings).getOffsetColoringBlending();
            rp_blending = ((SettingsFractals1066)settings).getRainbowPaletteBlending();
            entropy_offset = ((SettingsFractals1066)settings).getEntropyColoringOffset();
            rainbow_offset = ((SettingsFractals1066)settings).getRainbowPaletteOffset();
            greyscale_coloring = ((SettingsFractals1066)settings).getGreyscaleColoring();
            gs_noise_reducing_factor = ((SettingsFractals1066)settings).getGreyscaleColoringNoiseReducingFactor();
        }

        if(version < 1053) {
            exterior_de = false;
            height_ratio = 1;

            if(plane_type == FOLDUP_PLANE) {
                plane_transform_center[0] = 0;
                plane_transform_center[1] = -0.25;
            }
            else if(plane_type == FOLDRIGHT_PLANE) {
                plane_transform_center[0] = -1;
                plane_transform_center[1] = 0;
            }
            else if(plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {
                plane_transform_radius = 1;
            }

        }
        else {
            exterior_de = ((SettingsFractals1053)settings).getExteriorDe();

            if(exterior_de) {
                exterior_de_factor = ((SettingsFractals1053)settings).getExteriorDeFactor();
            }

            height_ratio = ((SettingsFractals1053)settings).getHeightRatio();

            if(plane_type == TWIRL_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
                plane_transform_angle = ((SettingsFractals1053)settings).getPlaneTransformAngle();
                plane_transform_radius = ((SettingsFractals1053)settings).getPlaneTransformRadius();
            }
            else if(plane_type == SHEAR_PLANE) {
                plane_transform_scales = ((SettingsFractals1053)settings).getPlaneTransformScales();
            }
            else if(plane_type == KALEIDOSCOPE_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
                plane_transform_angle = ((SettingsFractals1053)settings).getPlaneTransformAngle();
                plane_transform_angle2 = ((SettingsFractals1053)settings).getPlaneTransformAngle2();
                plane_transform_radius = ((SettingsFractals1053)settings).getPlaneTransformRadius();
                plane_transform_sides = ((SettingsFractals1053)settings).getPlaneTransformSides();
            }
            else if(plane_type == PINCH_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
                plane_transform_angle = ((SettingsFractals1053)settings).getPlaneTransformAngle();
                plane_transform_radius = ((SettingsFractals1053)settings).getPlaneTransformRadius();
                plane_transform_amount = ((SettingsFractals1053)settings).getPlaneTransformAmount();
            }
            else if(plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE || plane_type == INFLECTION_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
            }
            else if(plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {
                plane_transform_radius = ((SettingsFractals1053)settings).getPlaneTransformRadius();
            }
            else if(plane_type == CIRCLEINVERSION_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
                plane_transform_radius = ((SettingsFractals1053)settings).getPlaneTransformRadius();
            }
        }

        if(version < 1063) {
            if(plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {
                plane_transform_center[0] = 2;
                plane_transform_center[1] = 0;
            }

            domain_coloring = false;
            use_palette_domain_coloring = false;
            domain_coloring_alg = 0;
        }
        else {
            if(plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {
                plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
            }

            domain_coloring = ((SettingsFractals1063)settings).getDomainColoring();

            if(domain_coloring) {
                use_palette_domain_coloring = ((SettingsFractals1063)settings).getUsePaletteDomainColoring();
                domain_coloring_alg = ((SettingsFractals1063)settings).getDomainColoringAlg();
            }
        }

        if(plane_type == USER_PLANE) {
            if(version < 1058) {
                user_plane_algorithm = 0;
            }
            else {
                user_plane_algorithm = ((SettingsFractals1058)settings).getUserPlaneAlgorithm();
            }

            if(user_plane_algorithm == 0) {
                user_plane = settings.getUserPlane();
            }
            else {
                user_plane_conditions = ((SettingsFractals1058)settings).getUserPlaneConditions();
                user_plane_condition_formula = ((SettingsFractals1058)settings).getUserPlaneConditionFormula();
            }
        }

        if(color_choice == options_menu.getPalette().length - 1) {
            custom_palette = settings.getCustomPalette();
            color_interpolation = settings.getColorInterpolation();
            color_space = settings.getColorSpace();
            reversed_palette = settings.getReveresedPalette();
            temp_color_cycling_location = color_cycling_location;

            if(version < 1062) {
                scale_factor_palette_val = 0;
                processing_alg = PROCESSING_NONE;
            }
            else {
                processing_alg = ((SettingsFractals1062)settings).getProcessingAlgorithm();
                scale_factor_palette_val = ((SettingsFractals1062)settings).getScaleFactorPaletteValue();
            }
        }

        rotation = settings.getRotation();
        rotation_center = settings.getRotationCenter();

        bailout_test_algorithm = settings.getBailoutTestAlgorithm();

        if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
            n_norm = settings.getNNorm();
        }
        else if(bailout_test_algorithm == BAILOUT_TEST_USER) {
            bailout_test_user_formula = ((SettingsFractals1056)settings).getBailoutTestUserFormula();
            bailout_test_comparison = ((SettingsFractals1056)settings).getBailoutTestComparison();
            if(version < 1058) {
                bailout_test_user_formula2 = "bail";
            }
            else {
                bailout_test_user_formula2 = ((SettingsFractals1058)settings).getBailoutTestUserFormula2();
            }
        }

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        out_coloring_algorithm = settings.getOutColoringAlgorithm();

        if(out_coloring_algorithm == ATOM_DOMAIN) { //removed atom domain
            out_coloring_algorithm = ESCAPE_TIME;
        }

        if(out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
            user_out_coloring_algorithm = ((SettingsFractals1057)settings).getUserOutColoringAlgorithm();
            outcoloring_formula = ((SettingsFractals1057)settings).getOutcoloringFormula();
            user_outcoloring_conditions = ((SettingsFractals1057)settings).getUserOutcoloringConditions();
            user_outcoloring_condition_formula = ((SettingsFractals1057)settings).getUserOutcoloringConditionFormula();
        }

        in_coloring_algorithm = settings.getInColoringAlgorithm();

        if(in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
            user_in_coloring_algorithm = ((SettingsFractals1057)settings).getUserInColoringAlgorithm();
            incoloring_formula = ((SettingsFractals1057)settings).getIncoloringFormula();
            user_incoloring_conditions = ((SettingsFractals1057)settings).getUserIncoloringConditions();
            user_incoloring_condition_formula = ((SettingsFractals1057)settings).getUserIncoloringConditionFormula();
        }

        if(version < 1064) {
            inverse_dem = false;
            inverse_fake_dem = false;
        }
        else {
            inverse_dem = ((SettingsFractals1064)settings).getInverseDe();
            inverse_fake_dem = ((SettingsFractals1064)settings).getInverseFakeDe();
            if(version == 1064) {
                boolean[] user_outcoloring_special_color = ((SettingsFractals1064)settings).getUserOutColoringSpecialColor();
                boolean[] user_incoloring_special_color = ((SettingsFractals1064)settings).getUserInColoringSpecialColor();

                if(user_incoloring_special_color[0]) {
                    user_incoloring_condition_formula[0] = "-( " + user_incoloring_condition_formula[0] + " )";
                }
                if(user_incoloring_special_color[1]) {
                    user_incoloring_condition_formula[1] = "-( " + user_incoloring_condition_formula[1] + " )";
                }
                if(user_incoloring_special_color[2]) {
                    user_incoloring_condition_formula[2] = "-( " + user_incoloring_condition_formula[2] + " )";
                }

                if(user_outcoloring_special_color[0]) {
                    user_outcoloring_condition_formula[0] = "-( " + user_outcoloring_condition_formula[0] + " )";
                }
                if(user_outcoloring_special_color[1]) {
                    user_outcoloring_condition_formula[1] = "-( " + user_outcoloring_condition_formula[1] + " )";
                }
                if(user_outcoloring_special_color[2]) {
                    user_outcoloring_condition_formula[2] = "-( " + user_outcoloring_condition_formula[2] + " )";
                }
            }
        }

        smoothing = settings.getSmoothing();

        if(version < 1065) {
            color_smoothing_method = 0;
            bm_noise_reducing_factor = 0.4;
            rp_noise_reducing_factor = 0.4;
        }
        else {
            color_smoothing_method = ((SettingsFractals1065)settings).getColorSmoothingMethod();
            bm_noise_reducing_factor = ((SettingsFractals1065)settings).getBumpMappingNoiseReducingFactor();
            rp_noise_reducing_factor = ((SettingsFractals1065)settings).getRainbowPaletteNoiseReducingFactor();
        }

        if(version < 1054) {
            if(smoothing) {
                escaping_smooth_algorithm = 0;
                converging_smooth_algorithm = 0;
            }

            bump_map = false;
            polar_projection = false;
            color_intensity = 1;
        }
        else {
            if(smoothing) {
                escaping_smooth_algorithm = ((SettingsFractals1054)settings).getEscapingSmoothAgorithm();
                converging_smooth_algorithm = ((SettingsFractals1054)settings).getConvergingSmoothAgorithm();
            }

            bump_map = ((SettingsFractals1054)settings).getBumpMap();

            if(bump_map) {
                bumpMappingStrength = ((SettingsFractals1054)settings).getBumpMapStrength();
                bumpMappingDepth = ((SettingsFractals1054)settings).getBumpMapDepth();
                lightDirectionDegrees = ((SettingsFractals1054)settings).getLightDirectionDegrees();
            }

            polar_projection = ((SettingsFractals1054)settings).getPolarProjection();

            if(polar_projection) {
                circle_period = ((SettingsFractals1054)settings).getCirclePeriod();
            }

            color_intensity = ((SettingsFractals1054)settings).getColorIntensity();
        }

        if(version < 1055) {
            fake_de = false;
        }
        else {
            fake_de = ((SettingsFractals1055)settings).getFakeDe();

            if(fake_de) {
                fake_de_factor = ((SettingsFractals1055)settings).getFakeDeFactor();
            }
        }

        switch (function) {
            case MANDELBROTNTH:
                z_exponent = settings.getZExponent();
                break;
            case MANDELBROTWTH:
                z_exponent_complex = settings.getZExponentComplex();
                break;
            case MANDELPOLY:
            case NEWTONPOLY:
            case HALLEYPOLY:
            case SCHRODERPOLY:
            case HOUSEHOLDERPOLY:
            case SECANTPOLY:
            case STEFFENSENPOLY:
            case MULLERPOLY:
                coefficients = settings.getCoefficients();
                break;
            case NEWTONFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                break;
            case HALLEYFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                break;
            case SCHRODERFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                break;
            case HOUSEHOLDERFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                break;
            case SECANTFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                optionsEnableShortcut2();
                break;
            case STEFFENSENFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                break;
            case MULLERFORMULA:
                user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                break;
            case NOVA:
                z_exponent_nova = settings.getZExponentNova();
                relaxation = settings.getRelaxation();
                nova_method = settings.getNovaMethod();
                break;
            case USER_FORMULA:
                user_formula = settings.getUserFormula();
                bail_technique = settings.getBailTechnique();

                if(version < 1049) {
                    user_formula2 = "c";
                }
                else {
                    user_formula2 = ((SettingsFractals1049)settings).getUserFormula2();
                }
                break;
            case USER_FORMULA_ITERATION_BASED:
                user_formula_iteration_based = ((SettingsFractals1049)settings).getUserFormulaIterationBased();
                bail_technique = settings.getBailTechnique();
                break;
            case USER_FORMULA_CONDITIONAL:
                user_formula_conditions = ((SettingsFractals1050)settings).getUserFormulaConditions();
                user_formula_condition_formula = ((SettingsFractals1050)settings).getUserFormulaConditionFormula();
                bail_technique = settings.getBailTechnique();
                break;
            case USER_FORMULA_COUPLED:
                user_formula_coupled = ((SettingsFractals1063)settings).getUserFormulaCoupled();
                coupling = ((SettingsFractals1063)settings).getCoupling();
                coupling_amplitude = ((SettingsFractals1063)settings).getCouplingAmplitude();
                coupling_frequency = ((SettingsFractals1063)settings).getCouplingFrequency();
                coupling_seed = ((SettingsFractals1063)settings).getCouplingSeed();
                coupling_method = ((SettingsFractals1063)settings).getCouplingMethod();
                bail_technique = settings.getBailTechnique();
                break;
        }

    }

    public void loadSettings() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter)evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI)file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if(index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Load Settings");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            ObjectInputStream file_temp = null;
            try {
                file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
                SettingsFractals settings = (SettingsFractals)file_temp.readObject();
                
                readSettings(settings);

                julia_map = false;
                tools_menu.getJuliaMap().setSelected(false);
                toolbar.getJuliaMapButton().setSelected(false);
                options_menu.getJuliaMapOptions().setEnabled(false);

                zoom_window = false;
                tools_menu.getZoomWindow().setSelected(false);
                
                if(julia) {
                    tools_menu.getJulia().setSelected(true);
                    toolbar.getJuliaButton().setSelected(true);
                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    options_menu.getPerturbation().setEnabled(false);
                    options_menu.getInitialValue().setEnabled(false);
                    rootFindingMethodsSetEnabled(false);
                    fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                }
                else {
                    tools_menu.getJulia().setSelected(false);
                    toolbar.getJuliaButton().setSelected(false);
                    
                    if(!perturbation && !init_val) {
                        rootFindingMethodsSetEnabled(true);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                    }
                    else {
                        tools_menu.getJulia().setEnabled(false);
                        toolbar.getJuliaButton().setEnabled(false);
                        tools_menu.getJuliaMap().setEnabled(false);
                        toolbar.getJuliaMapButton().setEnabled(false);
                        rootFindingMethodsSetEnabled(false);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
                    }
                }

                options_menu.getPerturbation().setSelected(perturbation);
                options_menu.getInitialValue().setSelected(init_val);

                if(size < 0.05) {
                    tools_menu.getBoundaries().setEnabled(false);
                    boundaries = false;
                    tools_menu.getBoundaries().setSelected(false);
                }

                if(rotation != 0 && rotation != 360 && rotation != -360) {
                    tools_menu.getGrid().setEnabled(false);
                    grid = false;
                    tools_menu.getGrid().setSelected(false);

                    tools_menu.getBoundaries().setEnabled(false);
                    boundaries = false;
                    tools_menu.getBoundaries().setSelected(false);
                }

                if(in_coloring_algorithm != MAXIMUM_ITERATIONS) {
                    periodicity_checking = false;
                    options_menu.getPeriodicityChecking().setSelected(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
                }

                for(int k = 0; k < fractal_functions.length; k++) {
                    if(k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                            && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                            && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                            && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                            && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                            && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                            && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                        fractal_functions[k].setEnabled(true);
                    }

                    if(k == SIERPINSKI_GASKET && !julia && !perturbation && !init_val) {
                        fractal_functions[k].setEnabled(true);
                    }
                }

                options_menu.getBurningShipOpt().setEnabled(true);
                options_menu.getMandelGrassOpt().setEnabled(true);

                if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de) {
                    for(int k = 1; k < fractal_functions.length; k++) {
                        fractal_functions[k].setEnabled(false);
                    }

                    options_menu.getInitialValue().setEnabled(false);
                    options_menu.getPerturbation().setEnabled(false);

                    if(exterior_de) {
                        options_menu.getBurningShipOpt().setEnabled(false);
                        options_menu.getMandelGrassOpt().setEnabled(false);
                    }
                }
                else if(out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || out_coloring_algorithm == ESCAPE_TIME_GRID || out_coloring_algorithm == BIOMORPH || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || out_coloring_algorithm == ITERATIONS_PLUS_RE || out_coloring_algorithm == ITERATIONS_PLUS_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || out_coloring_algorithm == BANDED) {
                    rootFindingMethodsSetEnabled(false);
                }
                else {
                    if(!julia && !perturbation && !init_val) {
                        rootFindingMethodsSetEnabled(true);
                    }
                }

                options_menu.getApplyPlaneOnWholeJuliaOpt().setSelected(apply_plane_on_julia);
                options_menu.getApplyPlaneOnJuliaSeedOpt().setSelected(apply_plane_on_julia_seed);

                out_coloring_modes[out_coloring_algorithm].setSelected(true);

                in_coloring_modes[in_coloring_algorithm].setSelected(true);

                bailout_tests[bailout_test_algorithm].setSelected(true);

                if(burning_ship || mandel_grass || init_val || perturbation) {
                    options_menu.getDistanceEstimation().setEnabled(false);
                }

                if(polar_projection) {
                    tools_menu.getGrid().setEnabled(false);
                    tools_menu.getZoomWindow().setEnabled(false);

                    grid = false;
                    boundaries = false;

                    tools_menu.getGrid().setSelected(false);
                    tools_menu.getBoundaries().setSelected(false);

                    options_menu.getPolarProjectionOptions().setEnabled(true);

                }
                else {
                    options_menu.getPolarProjectionOptions().setEnabled(false);
                }

                tools_menu.getPolarProjection().setSelected(polar_projection);
                toolbar.getPolarProjectionButton().setSelected(polar_projection);

                if(domain_coloring) {
                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    tools_menu.getJulia().setEnabled(false);
                    toolbar.getJuliaButton().setEnabled(false);
                    tools_menu.getColorCyling().setEnabled(false);
                    toolbar.getColorCyclingButton().setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        options_menu.getColorsMenu().setEnabled(false);
                        toolbar.getCustomPaletteButton().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(false);
                    }
                    else {
                        options_menu.getDistanceEstimation().setEnabled(false);
                        options_menu.getBumpMap().setEnabled(false);

                        options_menu.getFakeDistanceEstimation().setEnabled(false);
                        options_menu.getEntropyColoring().setEnabled(false);
                        options_menu.getOffsetColoring().setEnabled(false);
                        options_menu.getGreyScaleColoring().setEnabled(false);

                        options_menu.getRainbowPalette().setEnabled(false);
                        options_menu.getOutColoringMenu().setEnabled(false);
                        options_menu.getInColoringMenu().setEnabled(false);
                        options_menu.getFractalColor().setEnabled(false);
                    }

                    options_menu.getGreedyAlgorithm().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutTestMenu().setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        infobar.getPalettePreview().setVisible(false);
                        infobar.getPalettePreviewLabel().setVisible(false);
                    }
                    else {
                        infobar.getPalettePreview().setVisible(true);
                        infobar.getPalettePreviewLabel().setVisible(true);
                    }

                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

                    options_menu.getDomainColoringOptions().setEnabled(true);
                }
                else {
                    infobar.getPalettePreview().setVisible(true);
                    infobar.getPalettePreviewLabel().setVisible(true);

                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);

                    options_menu.getDomainColoringOptions().setEnabled(false);
                }

                tools_menu.getDomainColoring().setSelected(domain_coloring);
                toolbar.getDomainColoringButton().setSelected(domain_coloring);

                filters_menu.setCheckedFilters(filters);

                fractal_functions[function].setSelected(true);

                switch (function) {
                    case NEWTON3:
                    case NEWTON4:
                    case NEWTONGENERALIZED3:
                    case NEWTONGENERALIZED8:
                    case NEWTONSIN:
                    case NEWTONCOS:
                    case HALLEY3:
                    case HALLEY4:
                    case HALLEYGENERALIZED3:
                    case HALLEYGENERALIZED8:
                    case HALLEYSIN:
                    case HALLEYCOS:
                    case SCHRODER3:
                    case SCHRODER4:
                    case SCHRODERGENERALIZED3:
                    case SCHRODERGENERALIZED8:
                    case SCHRODERSIN:
                    case SCHRODERCOS:
                    case HOUSEHOLDER3:
                    case HOUSEHOLDER4:
                    case HOUSEHOLDERGENERALIZED3:
                    case HOUSEHOLDERGENERALIZED8:
                    case HOUSEHOLDERSIN:
                    case HOUSEHOLDERCOS:
                    case SECANT3:
                    case SECANT4:
                    case SECANTGENERALIZED3:
                    case SECANTGENERALIZED8:
                    case SECANTCOS:
                    case STEFFENSEN3:
                    case STEFFENSEN4:
                    case STEFFENSENGENERALIZED3:
                    case MULLER3:
                    case MULLER4:
                    case MULLERGENERALIZED3:
                    case MULLERGENERALIZED8:
                    case MULLERSIN:
                    case MULLERCOS:
                    case NEWTONFORMULA:
                    case HALLEYFORMULA:
                    case SCHRODERFORMULA:
                    case HOUSEHOLDERFORMULA:
                    case SECANTFORMULA:
                    case STEFFENSENFORMULA:
                    case MULLERFORMULA:
                        optionsEnableShortcut2();
                        break;
                    case MANDELPOLY:
                    case NEWTONPOLY:
                    case HALLEYPOLY:
                    case SCHRODERPOLY:
                    case HOUSEHOLDERPOLY:
                    case SECANTPOLY:
                    case STEFFENSENPOLY:
                    case MULLERPOLY:
                        int l;

                        poly = "p(z) = ";
                        for(l = 0; l < coefficients.length - 2; l++) {
                            if(coefficients[l] > 0) {
                                if(poly.length() == 7) {
                                    poly += coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                                }
                                else {
                                    poly += "+" + coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                                }
                            }
                            else if(coefficients[l] < 0) {
                                poly += coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                            }
                        }

                        if(coefficients[l] > 0) {
                            if(poly.length() == 7) {
                                poly += coefficients[l] + "z  ";
                            }
                            else {
                                poly += "+" + coefficients[l] + "z  ";
                            }
                        }
                        else if(coefficients[l] < 0) {
                            poly += coefficients[l] + "z  ";
                        }

                        l++;
                        if(coefficients[l] > 0) {
                            poly += "+" + coefficients[l];
                        }
                        else if(coefficients[l] < 0) {
                            poly += coefficients[l];
                        }

                        if(function == MANDELPOLY) {
                            optionsEnableShortcut();
                        }
                        else {
                            optionsEnableShortcut2();
                        }
                        break;
                    case NOVA:
                        options_menu.getPeriodicityChecking().setEnabled(false);
                        options_menu.getBailout().setEnabled(false);
                        options_menu.getBailoutTestMenu().setEnabled(false);
                        optionsEnableShortcut();
                        break;
                    case SIERPINSKI_GASKET:
                        optionsEnableShortcut();
                        tools_menu.getJulia().setEnabled(false);
                        toolbar.getJuliaButton().setEnabled(false);
                        tools_menu.getJuliaMap().setEnabled(false);
                        toolbar.getJuliaMapButton().setEnabled(false);
                        options_menu.getPeriodicityChecking().setEnabled(false);
                        options_menu.getPerturbation().setEnabled(false);
                        options_menu.getInitialValue().setEnabled(false);
                        break;
                    case MANDELBROT:
                        if(!burning_ship && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                            options_menu.getDistanceEstimation().setEnabled(true);
                        }
                        if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !init_val && !perturbation) {
                            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                        }
                        if(out_coloring_algorithm != BIOMORPH) {
                            out_coloring_modes[BIOMORPH].setEnabled(true);
                        }
                        if(out_coloring_algorithm != BANDED) {
                            out_coloring_modes[BANDED].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                            out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ESCAPE_TIME_GRID) {
                            out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                        }
                        break;
                    case USER_FORMULA:
                        parser.parse(user_formula);

                        user_formula_c = parser.foundC();

                        if(!user_formula_c) {
                            tools_menu.getJulia().setEnabled(false);
                            toolbar.getJuliaButton().setEnabled(false);
                            tools_menu.getJuliaMap().setEnabled(false);
                            toolbar.getJuliaMapButton().setEnabled(false);
                            options_menu.getPerturbation().setEnabled(false);
                            options_menu.getInitialValue().setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            options_menu.getBailout().setEnabled(false);
                            options_menu.getBailoutTestMenu().setEnabled(false);
                            options_menu.getPeriodicityChecking().setEnabled(false);
                        }

                        optionsEnableShortcut();
                        break;
                    case USER_FORMULA_ITERATION_BASED:
                        boolean temp_bool = false;

                        for(int m = 0; m < user_formula_iteration_based.length; m++) {
                            parser.parse(user_formula_iteration_based[m]);
                            temp_bool = temp_bool | parser.foundC();
                        }

                        user_formula_c = temp_bool;

                        if(!user_formula_c) {
                            tools_menu.getJulia().setEnabled(false);
                            toolbar.getJuliaButton().setEnabled(false);
                            tools_menu.getJuliaMap().setEnabled(false);
                            toolbar.getJuliaMapButton().setEnabled(false);
                            options_menu.getPerturbation().setEnabled(false);
                            options_menu.getInitialValue().setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            options_menu.getBailout().setEnabled(false);
                            options_menu.getBailoutTestMenu().setEnabled(false);
                            options_menu.getPeriodicityChecking().setEnabled(false);
                        }

                        optionsEnableShortcut();
                        break;
                    case USER_FORMULA_CONDITIONAL:
                        boolean temp_bool2 = false;

                        for(int m = 0; m < user_formula_condition_formula.length; m++) {
                            parser.parse(user_formula_condition_formula[m]);
                            temp_bool2 = temp_bool2 | parser.foundC();
                        }

                        user_formula_c = temp_bool2;

                        if(!user_formula_c) {
                            tools_menu.getJulia().setEnabled(false);
                            toolbar.getJuliaButton().setEnabled(false);
                            tools_menu.getJuliaMap().setEnabled(false);
                            toolbar.getJuliaMapButton().setEnabled(false);
                            options_menu.getPerturbation().setEnabled(false);
                            options_menu.getInitialValue().setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            options_menu.getBailout().setEnabled(false);
                            options_menu.getBailoutTestMenu().setEnabled(false);
                            options_menu.getPeriodicityChecking().setEnabled(false);
                        }

                        optionsEnableShortcut();
                        break;
                    case USER_FORMULA_COUPLED:
                        temp_bool = false;

                        for(int m = 0; m < user_formula_coupled.length - 1; m++) {
                            parser.parse(user_formula_coupled[m]);
                            temp_bool = temp_bool | parser.foundC();
                        }

                        user_formula_c = temp_bool;

                        if(!user_formula_c) {
                            tools_menu.getJulia().setEnabled(false);
                            toolbar.getJuliaButton().setEnabled(false);
                            tools_menu.getJuliaMap().setEnabled(false);
                            toolbar.getJuliaMapButton().setEnabled(false);
                            options_menu.getPerturbation().setEnabled(false);
                            options_menu.getInitialValue().setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            options_menu.getBailout().setEnabled(false);
                            options_menu.getBailoutTestMenu().setEnabled(false);
                            options_menu.getPeriodicityChecking().setEnabled(false);
                        }

                        optionsEnableShortcut();
                        break;
                    default:
                        optionsEnableShortcut();
                        break;
                }

                options_menu.getPalette()[color_choice].setSelected(true);
                updateColorPalettesMenu();
                updateMaxIterColorPreview();

                options_menu.getBurningShipOpt().setSelected(burning_ship);
                options_menu.getMandelGrassOpt().setSelected(mandel_grass);

                planes[plane_type].setSelected(true);

                if(out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
                    if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                        user_out_coloring_algorithm = 0;

                        outcoloring_formula = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";

                        user_outcoloring_conditions[0] = "im(z)";
                        user_outcoloring_conditions[1] = "0";

                        user_outcoloring_condition_formula[0] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
                        user_outcoloring_condition_formula[1] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp))) + 50";
                        user_outcoloring_condition_formula[2] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
                    }
                    else if(function == MAGNET1) {
                        user_out_coloring_algorithm = 1;

                        outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                        user_outcoloring_conditions[0] = "norm(z-1)^2";
                        user_outcoloring_conditions[1] = "1e-12";

                        user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                        user_outcoloring_condition_formula[1] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                        user_outcoloring_condition_formula[2] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                    }
                    else if(function == MAGNET2) {
                        user_out_coloring_algorithm = 1;

                        outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                        user_outcoloring_conditions[0] = "norm(z-1)^2";
                        user_outcoloring_conditions[1] = "1e-9";

                        user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                        user_outcoloring_condition_formula[1] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                        user_outcoloring_condition_formula[2] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                    }
                    else {
                        user_out_coloring_algorithm = 0;

                        outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                        user_outcoloring_conditions[0] = "im(z)";
                        user_outcoloring_conditions[1] = "0";

                        user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                        user_outcoloring_condition_formula[1] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001)) + 50";
                        user_outcoloring_condition_formula[2] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                    }
                }

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                if(d3) {
                    fiX = 0.64;
                    fiY = 0.82;
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
            catch(ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                savePreferences();
                System.exit(-1);
            }

            try {
                file_temp.close();
            }
            catch(Exception ex) {
            }
        }

    }

    public void saveImage() {

        main_panel.repaint();

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("24-bit Bitmap (*.bmp)", "bmp"));
        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("JPEG (*.jpg)", "jpg"));
        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".bmp"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter)evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI)file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if(index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Image");

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = last_used.createGraphics();
        graphics.drawImage(image, 0, 0, null);

        if(boundaries) {// && !orbit) {
            drawBoundaries(graphics, false);
        }

        if(grid) {// && !orbit) {
            drawGrid(graphics, false);
        }

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = file_chooser.getSelectedFile();

                FileNameExtensionFilter filter = (FileNameExtensionFilter)file_chooser.getFileFilter();

                String extension = filter.getExtensions()[0];

                if(extension.equalsIgnoreCase("bmp")) {
                    ImageIO.write(last_used, "bmp", file);
                }
                else if(extension.equalsIgnoreCase("png")) {
                    ImageIO.write(last_used, "png", file);
                }
                else if(extension.equalsIgnoreCase("jpg")) {
                    ImageIO.write(last_used, "jpg", file);
                }
                else if(extension.equalsIgnoreCase("jpeg")) {
                    ImageIO.write(last_used, "jpeg", file);
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
            catch(IOException ex) {
            }

        }

        last_used = null;

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }

        main_panel.repaint();

    }

    private void defaultFractalSettings() {

        setOptions(false);

        switch (function) {
            case LAMBDA:
                if(julia) {
                    xCenter = 0.5;
                    yCenter = 0;
                    size = 6;
                    bailout = bailout < 8 ? 8 : bailout;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 8;
                    bailout = bailout < 2 ? 2 : bailout;
                }
                break;
            case MAGNET1:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 28;
                    bailout = bailout < 13 ? 13 : bailout;
                }
                else {
                    xCenter = 1.35;
                    yCenter = 0;
                    size = 8;
                    bailout = bailout < 13 ? 13 : bailout;
                }
                break;
            case MAGNET2:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 56;
                    bailout = bailout < 13 ? 13 : bailout;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 7;
                    bailout = bailout < 13 ? 13 : bailout;
                }
                break;
            case NEWTON3:
            case NEWTON4:
            case NEWTONGENERALIZED3:
            case NEWTONGENERALIZED8:
            case NEWTONSIN:
            case NEWTONCOS:
            case NEWTONPOLY:
            case NEWTONFORMULA:
            case HALLEY3:
            case HALLEY4:
            case HALLEYGENERALIZED3:
            case HALLEYGENERALIZED8:
            case HALLEYSIN:
            case HALLEYCOS:
            case HALLEYPOLY:
            case HALLEYFORMULA:
            case SCHRODER3:
            case SCHRODER4:
            case SCHRODERGENERALIZED3:
            case SCHRODERGENERALIZED8:
            case SCHRODERSIN:
            case SCHRODERCOS:
            case SCHRODERPOLY:
            case SCHRODERFORMULA:
            case HOUSEHOLDER3:
            case HOUSEHOLDER4:
            case HOUSEHOLDERGENERALIZED3:
            case HOUSEHOLDERGENERALIZED8:
            case HOUSEHOLDERSIN:
            case HOUSEHOLDERCOS:
            case HOUSEHOLDERPOLY:
            case HOUSEHOLDERFORMULA:
            case SECANT3:
            case SECANT4:
            case SECANTGENERALIZED3:
            case SECANTGENERALIZED8:
            case SECANTCOS:
            case SECANTPOLY:
            case SECANTFORMULA:
            case STEFFENSEN3:
            case STEFFENSEN4:
            case STEFFENSENGENERALIZED3:
            case STEFFENSENPOLY:
            case STEFFENSENFORMULA:
            case MULLER3:
            case MULLER4:
            case MULLERGENERALIZED3:
            case MULLERGENERALIZED8:
            case MULLERSIN:
            case MULLERCOS:
            case MULLERPOLY:
            case MULLERFORMULA:
            case NOVA:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                bailout = bailout < 2 ? 2 : bailout;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                bailout = bailout < 100 ? 100 : bailout;
                break;
            case EXP:
            case LOG:
            case SIN:
            case COS:
            case TAN:
            case COT:
            case SINH:
            case COSH:
            case TANH:
            case COTH:
            case FORMULA30:
            case FORMULA31:
            case FORMULA18:
            case FORMULA34:
            case FORMULA39:
            case FORMULA40:
            case FORMULA41:
            case COUPLED_MANDELBROT:
            case COUPLED_MANDELBROT_BURNING_SHIP:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = bailout < 8 ? 8 : bailout;
                break;
            case FORMULA1:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = bailout < 8 ? 8 : bailout;
                break;
            case FORMULA2:
            case FORMULA13:
            case FORMULA14:
            case FORMULA15:
            case FORMULA16:
            case FORMULA17:
            case FORMULA19:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = bailout < 4 ? 4 : bailout;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = bailout < 4 ? 4 : bailout;
                break;
            case FORMULA4:
            case FORMULA5:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = bailout < 4 ? 4 : bailout;
                ;
                break;
            case FORMULA7:
            case FORMULA12:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = bailout < 8 ? 8 : bailout;
                break;
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = bailout < 16 ? 16 : bailout;
                break;
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = bailout < 100 ? 100 : bailout;
                break;
            case FORMULA27:
                if(julia) {
                    xCenter = -2;
                    yCenter = 0;
                    size = 6;
                    bailout = bailout < 8 ? 8 : bailout;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    bailout = bailout < 8 ? 8 : bailout;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                    bailout = bailout < 16 ? 16 : bailout;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    bailout = bailout < 16 ? 16 : bailout;
                }
                break;
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = bailout < 16 ? 16 : bailout;
                break;
            case FORMULA38:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = bailout < 2 ? 2 : bailout;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 1.5;
                    bailout = bailout < 2 ? 2 : bailout;
                }
                break;
            case FORMULA42:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = bailout < 12 ? 12 : bailout;
                break;
            case FORMULA43:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = bailout < 12 ? 12 : bailout;
                break;
            case FORMULA44:
            case FORMULA45:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = bailout < 16 ? 16 : bailout;
                break;
            case FORMULA46:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = bailout < 100 ? 100 : bailout;
                break;
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = bailout < 4 ? 4 : bailout;
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = bailout < 2 ? 2 : bailout;
                break;
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            fiX = 0.64;
            fiY = 0.82;
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void goToFractal() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        if(!orbit) {

            JTextField field_real = new JTextField();

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

            if(p.x == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + p.x);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            if(p.y == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + p.y);
            }

            JTextField field_size = new JTextField();
            field_size.setText("" + size);

            Object[] message = {
                " ",
                "Set the real and imaginary part of the new center",
                "and the new size.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                "Size:", field_size,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Center and Size", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, tempSize;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText()) - rotation_center[0];
                    tempImaginary = Double.parseDouble(field_imaginary.getText()) - rotation_center[1];
                    tempSize = Double.parseDouble(field_size.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                return;
            }

            if(tempSize <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            size = tempSize;
            /* Inverse rotation */
            xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
            yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];

            if(size < 0.05) {
                tools_menu.getBoundaries().setEnabled(false);
                boundaries = false;
                tools_menu.getBoundaries().setSelected(false);
            }

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            backup_orbit = null;

            whole_image_done = false;

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if(orbit_vals[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + orbit_vals[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(orbit_vals[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + orbit_vals[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(orbit_vals[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + orbit_vals[0]);
                        }

                        field_real.setEditable(true);

                        if(orbit_vals[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + orbit_vals[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            JTextField sequence_points = new JTextField();
            sequence_points.setText("" + (max_iterations > 400 ? 400 : max_iterations));

            Object[] message = {
                " ",
                "Set the real and imaginary part of the new orbit.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center,
                " ",
                "Set the number of sequence points.",
                "Sequence Points:", sequence_points,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Orbit", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary;
            int seq_points;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    seq_points = Integer.parseInt(sequence_points.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                return;
            }

            if(seq_points <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            orbit_vals[0] = tempReal;
            orbit_vals[1] = tempImaginary;

            tempReal -= rotation_center[0];
            tempImaginary -= rotation_center[1];

            /* Inversed Rotation */
            double x_real = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
            double y_imag = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];

            if(pixels_orbit != null) {
                try {
                    pixels_orbit.join();

                }
                catch(InterruptedException ex) {

                }
            }

            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, user_fz_formula, user_dfz_formula, user_ddfz_formula, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                pixels_orbit.start();
            }
            catch(ParserException e) {
                JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                savePreferences();
                System.exit(-1);
            }
            catch(Exception ex) {
            }

        }

    }

    public void goToJulia() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        if(!orbit) {
            if(first_seed) {
                JTextField field_real = new JTextField();

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                if(p.x == 0) {
                    field_real.setText("" + 0.0);
                }
                else {
                    field_real.setText("" + p.x);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                if(p.y == 0) {
                    field_imaginary.setText("" + 0.0);
                }
                else {
                    field_imaginary.setText("" + p.y);
                }

                Object[] message = {
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", field_real,
                    "Imaginary:", field_imaginary,
                    " ",};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Julia Seed", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        xJuliaCenter = Double.parseDouble(field_real.getText());
                        yJuliaCenter = Double.parseDouble(field_imaginary.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    return;
                }

                first_seed = false;
                defaultFractalSettings();
            }
            else {
                JTextField field_real = new JTextField();

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                if(p.x == 0) {
                    field_real.setText("" + 0.0);
                }
                else {
                    field_real.setText("" + p.x);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                if(p.y == 0) {
                    field_imaginary.setText("" + 0.0);
                }
                else {
                    field_imaginary.setText("" + p.y);
                }

                JTextField field_size = new JTextField();
                field_size.setText("" + size);

                xJuliaCenter = xJuliaCenter == 0.0 ? 0.0 : xJuliaCenter;
                yJuliaCenter = yJuliaCenter == 0.0 ? 0.0 : yJuliaCenter;

                JTextField real_seed = new JTextField(16);
                real_seed.setEditable(false);
                JTextField imag_seed = new JTextField(16);
                imag_seed.setEditable(false);

                real_seed.setText("" + xJuliaCenter);
                imag_seed.setText("" + yJuliaCenter);

                JPanel julia_seed = new JPanel();

                julia_seed.add(real_seed);
                julia_seed.add(new JLabel(" "));
                julia_seed.add(imag_seed);
                julia_seed.add(new JLabel("i"));

                Object[] message = {
                    " ",
                    "Set the real and imaginary part of the new center",
                    "and the new size.",
                    "Real:", field_real,
                    "Imaginary:", field_imaginary,
                    "Size:", field_size,
                    " ",
                    "Julia seed:",
                    julia_seed,
                    " ",};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Center and Size", JOptionPane.OK_CANCEL_OPTION);

                double tempReal, tempImaginary, tempSize;

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        tempReal = Double.parseDouble(field_real.getText()) - rotation_center[0];
                        tempImaginary = Double.parseDouble(field_imaginary.getText()) - rotation_center[1];
                        tempSize = Double.parseDouble(field_size.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    return;
                }

                if(tempSize <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                size = tempSize;
                /* Inverse rotation */
                xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
                yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];

                if(size < 0.05) {
                    tools_menu.getBoundaries().setEnabled(false);
                    boundaries = false;
                    tools_menu.getBoundaries().setSelected(false);
                }

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                backup_orbit = null;

                whole_image_done = false;

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
        }
        else {
            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if(orbit_vals[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + orbit_vals[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(orbit_vals[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + orbit_vals[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(orbit_vals[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + orbit_vals[0]);
                        }

                        field_real.setEditable(true);

                        if(orbit_vals[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + orbit_vals[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            JTextField sequence_points = new JTextField();
            sequence_points.setText("" + (max_iterations > 400 ? 400 : max_iterations));

            Object[] message = {
                " ",
                "Set the real and imaginary part of the new orbit.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center,
                " ",
                "Set the number of sequence points.",
                "Sequence Points:", sequence_points,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Orbit", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary;
            int seq_points;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    seq_points = Integer.parseInt(sequence_points.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                return;
            }

            if(seq_points <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            orbit_vals[0] = tempReal;
            orbit_vals[1] = tempImaginary;

            tempReal -= rotation_center[0];
            tempImaginary -= rotation_center[1];

            double x_real = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
            double y_imag = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];

            if(pixels_orbit != null) {
                try {
                    pixels_orbit.join();

                }
                catch(InterruptedException ex) {

                }
            }

            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                pixels_orbit.start();
            }
            catch(ParserException e) {
                JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                savePreferences();
                System.exit(-1);
            }
            catch(Exception ex) {
            }
        }

    }

    public void setFractalColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 370;
        int color_window_height = 430;
        fract_color_frame = new JFrame("Fractal Colors");
        fract_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        fract_color_frame.setSize(color_window_width, color_window_height);
        fract_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));

        fract_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                fract_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JPanel fractal_color_panel = new JPanel();
        fractal_color_panel.setBackground(bg_color);
        fractal_color_panel.setLayout(new FlowLayout());
        fractal_color_panel.setPreferredSize(new Dimension(230, 80));
        fractal_color_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Maximum Iterations Color", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JLabel max_it_color_label = new JLabel("");
        max_it_color_label.setPreferredSize(new Dimension(40, 40));
        max_it_color_label.setOpaque(true);
        max_it_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        max_it_color_label.setBackground(fractal_color);
        max_it_color_label.setToolTipText("Left click to change this color.");

        max_it_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserFrame(ptr, fract_color_frame, "Maximum Iterations Color", max_it_color_label, -1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        final JPanel dem_color_panel = new JPanel();
        dem_color_panel.setBackground(bg_color);
        dem_color_panel.setLayout(new FlowLayout());
        dem_color_panel.setPreferredSize(new Dimension(230, 80));
        dem_color_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Distance Estimation Color", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JLabel dem_color_label = new JLabel("");
        dem_color_label.setPreferredSize(new Dimension(40, 40));
        dem_color_label.setOpaque(true);
        dem_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        dem_color_label.setBackground(dem_color);

        dem_color_label.setToolTipText("Left click to change this color.");

        dem_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserFrame(ptr, fract_color_frame, "Distance Estimation Color", dem_color_label, -1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        final JPanel special_color_panel = new JPanel();
        special_color_panel.setBackground(bg_color);
        special_color_panel.setLayout(new FlowLayout());
        special_color_panel.setPreferredSize(new Dimension(230, 80));
        special_color_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Special Color", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        final JCheckBox use_palette_color = new JCheckBox("Use Palette Color");
        use_palette_color.setBackground(bg_color);
        use_palette_color.setFocusable(false);
        use_palette_color.setSelected(special_use_palette_color);
        use_palette_color.setToolTipText("<html>Sets the special color for the following color algorithms:<br>Binary Decomposition<br>Binary Decomposition 2<br>Biomorph<br>Escape Time + Grid<br>cos(norm(z))<br>Squares 2<br>User Conditional Out Coloring method<br>User Conditional In Coloring method</html>");

        final JLabel special_color_label = new JLabel("");
        special_color_label.setPreferredSize(new Dimension(40, 40));
        special_color_label.setOpaque(true);
        special_color_label.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        special_color_label.setBackground(special_color);

        special_color_label.setToolTipText("Left click to change this color.");

        special_color_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserFrame(ptr, fract_color_frame, "Special Color", special_color_label, -1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        fractal_color_panel.add(max_it_color_label);

        dem_color_panel.add(dem_color_label);

        special_color_panel.add(use_palette_color);
        special_color_panel.add(special_color_label);

        JPanel color_panel = new JPanel();
        color_panel.setBackground(bg_color);
        color_panel.setLayout(new GridLayout(3, 1));
        color_panel.setPreferredSize(new Dimension(260, 280));
        color_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Fractal Colors", TitledBorder.CENTER, TitledBorder.CENTER));

        color_panel.add(fractal_color_panel);
        color_panel.add(dem_color_panel);
        color_panel.add(special_color_panel);

        JButton ok = new JButton("Ok");
        ok.setFocusable(false);

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fractal_color = max_it_color_label.getBackground();
                dem_color = dem_color_label.getBackground();
                special_color = special_color_label.getBackground();
                special_use_palette_color = use_palette_color.isSelected();

                //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
                Color[] c = null;

                if(color_choice < options_menu.getPalette().length - 1) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
                }
                else {
                    c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                }

                boolean flag = true;

                int count = 0;

                boolean fractal_color_up = true;
                boolean dem_color_up = true;
                boolean special_color_up = true;

                while(flag && count < 10) {
                    flag = false;
                    for(int j = 0; j < c.length; j++) {
                        if(c[j].getRGB() == fractal_color.getRGB()) {
                            if(fractal_color_up) {
                                if(fractal_color.getBlue() == 255) {
                                    fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() - 1);
                                    fractal_color_up = false;
                                }
                                else {
                                    fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() + 1);
                                }
                            }
                            else {
                                if(fractal_color.getBlue() == 0) {
                                    fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() + 1);
                                    fractal_color_up = true;
                                }
                                else {
                                    fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() - 1);
                                }
                            }
                            flag = true;
                        }

                        if(c[j].getRGB() == dem_color.getRGB()) {
                            if(dem_color_up) {
                                if(dem_color.getRed() == 255) {
                                    dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                                    dem_color_up = false;
                                }
                                else {
                                    dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                                }
                            }
                            else {
                                if(dem_color.getRed() == 0) {
                                    dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                                    dem_color_up = true;
                                }
                                else {
                                    dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                                }
                            }

                            flag = true;
                        }

                        if(c[j].getRGB() == special_color.getRGB()) {
                            if(special_color_up) {
                                if(special_color.getGreen() == 255) {
                                    special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                                    special_color_up = false;
                                }
                                else {
                                    special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                                }
                            }
                            else {
                                if(special_color.getGreen() == 0) {
                                    special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                                    special_color_up = true;
                                }
                                else {
                                    special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                                }
                            }
                            flag = true;
                        }

                        if(flag) {
                            break;
                        }
                    }

                    if(fractal_color.getRGB() == dem_color.getRGB()) {
                        if(dem_color_up) {
                            if(dem_color.getRed() == 255) {
                                dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                                dem_color_up = false;
                            }
                            else {
                                dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                            }
                        }
                        else {
                            if(dem_color.getRed() == 0) {
                                dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                                dem_color_up = true;
                            }
                            else {
                                dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                            }
                        }
                        flag = true;
                    }

                    if(dem_color.getRGB() == special_color.getRGB()) {
                        if(special_color_up) {
                            if(special_color.getGreen() == 255) {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                                special_color_up = false;
                            }
                            else {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                            }
                        }
                        else {
                            if(special_color.getGreen() == 0) {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                                special_color_up = true;
                            }
                            else {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                            }
                        }
                        flag = true;
                    }

                    if(fractal_color.getRGB() == special_color.getRGB()) {
                        if(special_color_up) {
                            if(special_color.getGreen() == 255) {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                                special_color_up = false;
                            }
                            else {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                            }
                        }
                        else {
                            if(special_color.getGreen() == 0) {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                                special_color_up = true;
                            }
                            else {
                                special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                            }
                        }
                        flag = true;
                    }

                    count++;
                }

                updateMaxIterColorPreview();

                setOptions(false);

                progress.setValue(0);

                setEnabled(true);
                fract_color_frame.dispose();

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                if(filters[ANTIALIASING]) {
                    if(julia_map) {
                        createThreadsJuliaMap();
                    }
                    else {
                        createThreads(false);
                    }

                    calculation_time = System.currentTimeMillis();

                    if(julia_map) {
                        startThreads(julia_grid_first_dimension);
                    }
                    else {
                        startThreads(n);
                    }
                }
                else {
                    if(d3) {
                        createThreads(false);
                    }
                    else {
                        createThreadsPaletteAndFilter();
                    }

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);
                }

            }
        });

        JButton close = new JButton("Cancel");
        close.setFocusable(false);

        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                fract_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JPanel buttons = new JPanel();
        buttons.setBackground(bg_color);

        buttons.add(ok);
        buttons.add(close);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(bg_color);
        round_panel.setPreferredSize(new Dimension(300, 340));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(color_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        fract_color_frame.add(scrollPane);

        fract_color_frame.setVisible(true);

    }

    public void setIterations() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using maximum " + max_iterations + " iterations.\nEnter the new maximum iterations number.", "Maximum Iterations Number", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if(temp < 1) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 80000) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be less than 80001.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            max_iterations = temp;

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setHeightRatio() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + height_ratio + " as stretch factor.\nEnter the new stretch.", "Stretch Factor", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if(temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Stretch factor number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            height_ratio = temp;

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setZoomingFactor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + zoom_factor + " for zooming factor.\nEnter the new zooming factor.", "Zooming Factor", JOptionPane.QUESTION_MESSAGE);

        try {
            Double temp = Double.parseDouble(ans);

            if(temp <= 1.05) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Zooming factor must be greater than 1.05.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 32) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Zooming factor must be less than 33.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            zoom_factor = temp;

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setThreadsNumber() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "Processor cores: " + Runtime.getRuntime().availableProcessors() + "\nYou are using " + n * n + " threads in a " + n + "x" + n + " 2D grid.\nEnter the first dimension, n, of the nxn 2D grid.", "Threads Number", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if(temp < 1) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 100) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid must be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            n = temp;
            threads = new ThreadDraw[n][n];

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setPalette(int temp2) {

        color_choice = temp2;
        if(color_choice != options_menu.getPalette().length - 1) {
            color_cycling_location = 0;
        }

        options_menu.getPalette()[color_choice].setSelected(true);
        //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
        Color[] c = null;

        if(color_choice < options_menu.getPalette().length - 1) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
        }
        else {
            c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
        }

        boolean flag = true;

        int count = 0;

        boolean fractal_color_up = true;
        boolean dem_color_up = true;
        boolean special_color_up = true;

        while(flag && count < 10) {
            flag = false;
            for(int j = 0; j < c.length; j++) {
                if(c[j].getRGB() == fractal_color.getRGB()) {
                    if(fractal_color_up) {
                        if(fractal_color.getBlue() == 255) {
                            fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() - 1);
                            fractal_color_up = false;
                        }
                        else {
                            fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() + 1);
                        }
                    }
                    else {
                        if(fractal_color.getBlue() == 0) {
                            fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() + 1);
                            fractal_color_up = true;
                        }
                        else {
                            fractal_color = new Color(fractal_color.getRed(), fractal_color.getGreen(), fractal_color.getBlue() - 1);
                        }
                    }
                    flag = true;
                }

                if(c[j].getRGB() == dem_color.getRGB()) {
                    if(dem_color_up) {
                        if(dem_color.getRed() == 255) {
                            dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                            dem_color_up = false;
                        }
                        else {
                            dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                        }
                    }
                    else {
                        if(dem_color.getRed() == 0) {
                            dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                            dem_color_up = true;
                        }
                        else {
                            dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                        }
                    }

                    flag = true;
                }

                if(c[j].getRGB() == special_color.getRGB()) {
                    if(special_color_up) {
                        if(special_color.getGreen() == 255) {
                            special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                            special_color_up = false;
                        }
                        else {
                            special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                        }
                    }
                    else {
                        if(special_color.getGreen() == 0) {
                            special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                            special_color_up = true;
                        }
                        else {
                            special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                        }
                    }
                    flag = true;
                }

                if(flag) {
                    break;
                }
            }

            if(fractal_color.getRGB() == dem_color.getRGB()) {
                if(dem_color_up) {
                    if(dem_color.getRed() == 255) {
                        dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                        dem_color_up = false;
                    }
                    else {
                        dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                    }
                }
                else {
                    if(dem_color.getRed() == 0) {
                        dem_color = new Color(dem_color.getRed() + 1, dem_color.getGreen(), dem_color.getBlue());
                        dem_color_up = true;
                    }
                    else {
                        dem_color = new Color(dem_color.getRed() - 1, dem_color.getGreen(), dem_color.getBlue());
                    }
                }
                flag = true;
            }

            if(dem_color.getRGB() == special_color.getRGB()) {
                if(special_color_up) {
                    if(special_color.getGreen() == 255) {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                        special_color_up = false;
                    }
                    else {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                    }
                }
                else {
                    if(special_color.getGreen() == 0) {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                        special_color_up = true;
                    }
                    else {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                    }
                }
                flag = true;
            }

            if(fractal_color.getRGB() == special_color.getRGB()) {
                if(special_color_up) {
                    if(special_color.getGreen() == 255) {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                        special_color_up = false;
                    }
                    else {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                    }
                }
                else {
                    if(special_color.getGreen() == 0) {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() + 1, special_color.getBlue());
                        special_color_up = true;
                    }
                    else {
                        special_color = new Color(special_color.getRed(), special_color.getGreen() - 1, special_color.getBlue());
                    }
                }
                flag = true;
            }

            count++;
        }

        updateColorPalettesMenu();
        updateMaxIterColorPreview();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(filters[ANTIALIASING] || (domain_coloring && use_palette_domain_coloring)) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setFilter(int temp) {

        if(temp == ANTIALIASING) {
            if(!filters_opt[ANTIALIASING].isSelected()) {
                filters[ANTIALIASING] = false;

                setOptions(false);

                progress.setValue(0);

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                    createThreads(false);
                }
                else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
            else {
                filters[ANTIALIASING] = true;

                setOptions(false);

                progress.setValue(0);

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }

            }
        }
        else {
            if(!filters_opt[temp].isSelected()) {
                filters[temp] = false;
            }
            else {
                filters[temp] = true;
            }

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(filters[ANTIALIASING]) {
                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else if(d3) {
                    createThreadsPaletteAndFilter3DModel();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }
            }
            else {
                if(d3) {
                    createThreadsPaletteAndFilter3DModel();
                }
                else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
        }
    }

    public void setGrid() {

        if(!tools_menu.getGrid().isSelected()) {
            grid = false;
            old_grid = grid;
            if(!zoom_window && !julia_map && !orbit && !boundaries) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if(!zoom_window) {
                tools_menu.getPolarProjection().setEnabled(true);
                toolbar.getPolarProjectionButton().setEnabled(true);
            }

            main_panel.repaint();
        }
        else {
            grid = true;
            old_grid = grid;
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            tools_menu.getPolarProjection().setEnabled(false);
            toolbar.getPolarProjectionButton().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setBoundaries() {

        if(!tools_menu.getBoundaries().isSelected()) {
            boundaries = false;
            old_boundaries = boundaries;
            if(!zoom_window && !julia_map && !orbit && !grid) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }
            main_panel.repaint();
        }
        else {
            boundaries = true;
            old_boundaries = boundaries;
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setZoomWindow() {

        if(!tools_menu.getZoomWindow().isSelected()) {
            zoom_window = false;
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if(!julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if(!grid && !boundaries) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if(image_size < 4001) {
                tools_menu.getOrbit().setEnabled(true);
                toolbar.getOrbitButton().setEnabled(true);
            }

            if(!grid) {
                tools_menu.getPolarProjection().setEnabled(true);
                toolbar.getPolarProjectionButton().setEnabled(true);
            }

            if(!domain_coloring) {
                tools_menu.getColorCyling().setEnabled(true);
                toolbar.getColorCyclingButton().setEnabled(true);
            }
            main_panel.repaint();
        }
        else {
            zoom_window = true;
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.getOrbit().setEnabled(false);
            toolbar.getOrbitButton().setEnabled(false);
            tools_menu.getColorCyling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            tools_menu.getPolarProjection().setEnabled(false);
            toolbar.getPolarProjectionButton().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setPeriodicityChecking() {

        if(!options_menu.getPeriodicityChecking().isSelected()) {
            periodicity_checking = false;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
            main_panel.repaint();
        }
        else {
            periodicity_checking = true;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(false);
            main_panel.repaint();
        }

    }

    private void selectPointFractal(MouseEvent e) {

        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        xCenter = xCenter - size_2_x + temp_size_image_size_x * curX;
        yCenter = yCenter + size_2_y - temp_size_image_size_y * curY;

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                size /= zoom_factor;
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                size *= zoom_factor;
                break;
            }
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void scrollPoint(MouseWheelEvent e) {

        if(!threadsAvailable() || ctrlKeyPressed || shiftKeyPressed || altKeyPressed) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        xCenter = xCenter - size_2_x + temp_size_image_size_x * curX;
        yCenter = yCenter + size_2_y - temp_size_image_size_y * curY;

        int notches = e.getWheelRotation();
        if(notches < 0) {
            size /= zoom_factor;
        }
        else {
            size *= zoom_factor;
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void dragPoint(MouseEvent e) {

        if(!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        xCenter -= temp_size_image_size_x * (curX - oldDragX);
        yCenter += temp_size_image_size_y * (curY - oldDragY);

        oldDragX = curX;
        oldDragY = curY;

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointForPlane(MouseEvent e) {

        if(!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.ALT_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_x = xCenter - size_2_x + temp_size_image_size_x * curX;
        double temp_y = yCenter + size_2_y - temp_size_image_size_y * curY;

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_x, temp_y, rotation_vals, rotation_center);

        plane_transform_center[0] = p.x;
        plane_transform_center[1] = p.y;

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void rotatePoint(MouseEvent e) {

        if(!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        double new_angle = MathUtils.angleBetween2PointsDegrees(curX, curY, image_size / 2, image_size / 2);
        double old_angle = MathUtils.angleBetween2PointsDegrees(oldDragX, oldDragY, image_size / 2, image_size / 2);
        if(Math.abs(new_angle - old_angle) > 350 && new_angle > old_angle) {
            old_angle += 360;
        }
        else if(Math.abs(new_angle - old_angle) > 350 && new_angle < old_angle) {
            new_angle += 360;
        }
        rotation += new_angle - old_angle;

        if(rotation > 360) {
            rotation -= 2 * 360;
        }
        else if(rotation < -360) {
            rotation += 2 * 360;
        }

        oldDragX = curX;
        oldDragY = curY;

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        rotation_center[0] = p.x;
        rotation_center[1] = p.y;
        xCenter = rotation_center[0];
        yCenter = rotation_center[1];

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void scrollPointPolar(MouseWheelEvent e) {

        if(!threadsAvailable()) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        int notches = e.getWheelRotation();
        if(notches < 0) {
            size /= zoom_factor;
        }
        else {
            size *= zoom_factor;
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointJulia(MouseEvent e) {

        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        setOptions(false);

        if(first_seed) {
            if(e.getModifiers() == InputEvent.BUTTON1_MASK) {

                int x1 = (int)curX;
                int y1 = (int)curY;

                if(polar_projection) {
                    double start;
                    double center = Math.log(size);

                    double f, sf, cf, r;
                    double muly = (2 * circle_period * Math.PI) / image_size;

                    double mulx = muly * height_ratio;

                    start = center - mulx * image_size * 0.5;

                    f = y1 * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x1 * mulx + start);

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

                    xJuliaCenter = p.x;
                    yJuliaCenter = p.y;
                }
                else {
                    double size_2_x = size * 0.5;
                    double size_2_y = (size * height_ratio) * 0.5;
                    double temp_size_image_size_x = size / image_size;
                    double temp_size_image_size_y = (size * height_ratio) / image_size;

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter - size_2_x + temp_size_image_size_x * x1, yCenter + size_2_y - temp_size_image_size_y * y1, rotation_vals, rotation_center);

                    xJuliaCenter = p.x;
                    yJuliaCenter = p.y;
                }

                first_seed = false;

                if(image_size < 4001) {
                    tools_menu.getOrbit().setEnabled(true);
                    toolbar.getOrbitButton().setEnabled(true);
                }

                main_panel.repaint();

                defaultFractalSettings();
                return;
            }
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;

            xCenter = xCenter - size_2_x + temp_size_image_size_x * curX;
            yCenter = yCenter + size_2_y - temp_size_image_size_y * curY;

            switch (e.getModifiers()) {
                case InputEvent.BUTTON1_MASK: {
                    size /= zoom_factor;
                    break;
                }
                case InputEvent.BUTTON3_MASK: {
                    size *= zoom_factor;
                    break;
                }
            }

            if(size < 0.05) {
                tools_menu.getBoundaries().setEnabled(false);
                boundaries = false;
                tools_menu.getBoundaries().setSelected(false);
            }

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }

    }

    private void selectPointPolar(MouseEvent e) {
        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        double curX, curY;
        try {
            curX = main_panel.getMousePosition().getX();
            curY = main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
            return;
        }

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                size /= zoom_factor;
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                size *= zoom_factor;
                break;
            }
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }
    }

    public void setJuliaOption() {

        if(!tools_menu.getJulia().isSelected()) {
            julia = false;
            toolbar.getJuliaButton().setSelected(false);
            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
            if(!first_seed) {
                defaultFractalSettings();
                main_panel.repaint();
            }
            else {
                setOptions(true);
                main_panel.repaint();
            }
        }
        else {
            julia = true;
            toolbar.getJuliaButton().setSelected(true);
            first_seed = true;
            tools_menu.getOrbit().setEnabled(false);
            toolbar.getOrbitButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            setOptions(false);
        }

    }

    public void setOrbitOption() {

        if(!tools_menu.getOrbit().isSelected()) {
            orbit = false;
            toolbar.getOrbitButton().setSelected(false);
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if(!julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if(!grid && !boundaries) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if(!polar_projection) {
                tools_menu.getZoomWindow().setEnabled(true);
            }

            if(!domain_coloring) {
                tools_menu.getColorCyling().setEnabled(true);
                toolbar.getColorCyclingButton().setEnabled(true);
            }

            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            backup_orbit = null;
            //last_used = null;
        }
        else {
            orbit = true;
            toolbar.getOrbitButton().setSelected(true);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            tools_menu.getColorCyling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            tools_menu.getZoomWindow().setEnabled(false);
            backup_orbit = null;
            //last_used = null;
        }

        main_panel.repaint();

    }

    private void rotate3DModel(MouseEvent e) {

        if(!threadsAvailable() || e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        int x1 = (int)main_panel.getMousePosition().getX();
        int y1 = (int)main_panel.getMousePosition().getY();

        if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
            return;
        }

        statusbar.getReal().setText("Real");
        statusbar.getImaginary().setText("Imaginary");

        fiX += dfi * (y1 - my0);
        fiY += dfi * (x1 - mx0);
        mx0 = x1;
        my0 = y1;

        setOptions(false);

        //progress.setValue(0);
        resetImage();

        Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());

        backup_orbit = null;

        whole_image_done = false;

        createThreadsRotate3DModel();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointOrbit(MouseEvent e) {

        if(!threadsAvailable() || (pixels_orbit != null && pixels_orbit.isAlive())) {
            return;
        }

        int x1, y1;

        try {
            x1 = (int)main_panel.getMousePosition().getX();
            y1 = (int)main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
            return;
        }

        if(e.getModifiers() == InputEvent.BUTTON1_MASK) {
            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                if(julia) {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 400 ? 400 : max_iterations, x1, y1, image_size, image, ptr, orbit_color, orbit_style, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                    pixels_orbit.start();
                }
                else {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 400 ? 400 : max_iterations, x1, y1, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, user_fz_formula, user_dfz_formula, user_ddfz_formula, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                    pixels_orbit.start();
                }
            }
            catch(ParserException ex) {
                JOptionPane.showMessageDialog(scroll_pane, ex.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                savePreferences();
                System.exit(-1);
            }
            catch(Exception ex) {
            }
        }
        else {
            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();
        }

        if(polar_projection) {
            try {
                double start;
                double center = Math.log(size);

                double f, sf, cf, r;
                double muly = (2 * circle_period * Math.PI) / image_size;

                double mulx = muly * height_ratio;

                start = center - mulx * image_size * 0.5;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

                statusbar.getReal().setText("" + p.x);

                statusbar.getImaginary().setText("" + p.y);
            }
            catch(NullPointerException ex) {
            }
        }
        else {
            try {
                double size_2_x = size * 0.5;
                double size_2_y = (size * height_ratio) * 0.5;
                double temp_xcenter_size = xCenter - size_2_x;
                double temp_ycenter_size = yCenter + size_2_y;
                double temp_size_image_size_x = size / image_size;
                double temp_size_image_size_y = (size * height_ratio) / image_size;

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, rotation_vals, rotation_center);

                statusbar.getReal().setText("" + p.x);

                statusbar.getImaginary().setText("" + p.y);
            }
            catch(NullPointerException ex) {
            }
        }

    }

    /*private void zoomAuto() {
    
     new Thread()
     {
     public void run() {
    
     do {
     size /= 1.01;//zoom_factor;
    
     while(!threadsAvailable()) {
     yield();
     }
    
     setOptions(false);
    
     progress.setValue(0);
    
    
     scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
     scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
    
    
     resetImage();
    
     backup_orbit = null;
    
     whole_image_done = false;
    
     createThreads(false);
    
     calculation_time = System.currentTimeMillis();
    
     startThreads(n);
    
     } while( true);
     }
     }.start();  
     }*/
    public void zoomIn() {

        size /= zoom_factor;

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void zoomOut() {

        size *= zoom_factor;

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void moveTo(int where) {

        if(where == UP) {
            if(polar_projection) {
                rotation -= circle_period * 360;

                if(rotation < -360) {
                    System.out.println(rotation);
                    rotation = ((int)(-rotation / 360) + 1) * 360 + rotation;
                }

                rotation_vals[0] = Math.cos(Math.toRadians(rotation));
                rotation_vals[1] = Math.sin(Math.toRadians(rotation));
            }
            else {
                yCenter += (size * height_ratio);
            }
        }
        else if(where == DOWN) {
            if(polar_projection) {
                rotation += circle_period * 360;

                if(rotation > 360) {
                    rotation = ((int)(-rotation / 360) - 1) * 360 + rotation;
                }

                rotation_vals[0] = Math.cos(Math.toRadians(rotation));
                rotation_vals[1] = Math.sin(Math.toRadians(rotation));
            }
            else {
                yCenter -= (size * height_ratio);
            }
        }
        else if(where == LEFT) {

            if(polar_projection) {
                double start;
                double end = Math.log(size);

                double muly = (2 * circle_period * Math.PI) / image_size;

                double mulx = muly * height_ratio;

                start = -mulx * image_size + end;

                size = Math.exp(start);
            }
            else {
                xCenter -= size;
            }
        }
        else {
            if(polar_projection) {
                double start = Math.log(size);
                double end;

                double muly = (2 * circle_period * Math.PI) / image_size;

                double mulx = muly * height_ratio;

                end = mulx * image_size + start;

                size = Math.exp(end);
            }
            else {
                xCenter += size;
            }
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setOptions(Boolean option) {

        file_menu.getStartingPosition().setEnabled(option);
        toolbar.getStartingPositionButton().setEnabled(option);

        file_menu.getSaveSettings().setEnabled(option);
        file_menu.getLoadSettings().setEnabled(option);
        file_menu.getSaveImage().setEnabled(option);

        file_menu.getCodeEditor().setEnabled(option);
        file_menu.getCompileCode().setEnabled(option);

        if((!julia || !first_seed)) {
            file_menu.getGoTo().setEnabled(option);
        }

        options_menu.getFractalFunctionsMenu().setEnabled(option);

        if(!domain_coloring || (domain_coloring && use_palette_domain_coloring)) {
            options_menu.getColorsMenu().setEnabled(option);
        }

        options_menu.getIterationsMenu().setEnabled(option);
        options_menu.getSizeOfImage().setEnabled(option);

        options_menu.getHeightRatio().setEnabled(option);

        if(!domain_coloring || (domain_coloring && use_palette_domain_coloring)) {
            toolbar.getCustomPaletteButton().setEnabled(option);
            toolbar.getRandomPaletteButton().setEnabled(option);
        }
        toolbar.getIterationsButton().setEnabled(option);
        toolbar.getRotationButton().setEnabled(option);
        toolbar.getSaveImageButton().setEnabled(option);
        options_menu.getPoint().setEnabled(option);

        if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
            options_menu.getBailout().setEnabled(option);
            options_menu.getBailoutTestMenu().setEnabled(option);
        }

        options_menu.getOptimizationsMenu().setEnabled(option);
        options_menu.getToolsOptionsMenu().setEnabled(option);

        if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && in_coloring_algorithm == MAXIMUM_ITERATIONS && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
            options_menu.getPeriodicityChecking().setEnabled(option);
        }

        if(!domain_coloring && !d3 && !julia_map) {
            options_menu.getGreedyAlgorithm().setEnabled(option);
        }

        if(((!julia && !orbit) || (!first_seed && !orbit)) && !domain_coloring && !zoom_window && !julia_map && !d3 && !perturbation && !init_val && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            tools_menu.getJulia().setEnabled(option);
            toolbar.getJuliaButton().setEnabled(option);
        }

        if(!zoom_window && !orbit && !color_cycling && !d3 && !domain_coloring) {
            tools_menu.getColorCyling().setEnabled(option);
            toolbar.getColorCyclingButton().setEnabled(option);
        }

        if(!color_cycling && !julia_map) {
            tools_menu.getDomainColoring().setEnabled(option);
            toolbar.getDomainColoringButton().setEnabled(option);
        }

        tools_menu.getPlaneVizualization().setEnabled(option);

        if(!domain_coloring || (domain_coloring && !use_palette_domain_coloring)) {

            if(function == MANDELBROT && !burning_ship && !mandel_grass && !init_val && !perturbation) {
                options_menu.getDistanceEstimation().setEnabled(option);
            }

            options_menu.getBumpMap().setEnabled(option);

            options_menu.getFakeDistanceEstimation().setEnabled(option);

            options_menu.getEntropyColoring().setEnabled(option);

            options_menu.getGreyScaleColoring().setEnabled(option);

            options_menu.getRainbowPalette().setEnabled(option);

            options_menu.getOffsetColoring().setEnabled(option);

            options_menu.getOutColoringMenu().setEnabled(option);
            options_menu.getInColoringMenu().setEnabled(option);
            options_menu.getFractalColor().setEnabled(option);
        }

        if(!zoom_window && !grid) {
            tools_menu.getPolarProjection().setEnabled(option);
            toolbar.getPolarProjectionButton().setEnabled(option);
        }

        options_menu.getFractalOptionsMenu().setEnabled(option);

        filters_menu.getColorsFiltersMenu().setEnabled(option);
        filters_menu.getDetailsFiltersMenu().setEnabled(option);
        filters_menu.getTextureFiltersMenu().setEnabled(option);
        filters_menu.getLightingFiltersMenu().setEnabled(option);

        options_menu.getFiltersOptions().setEnabled(option);

        toolbar.getFiltersOptionsButton().setEnabled(option);

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3 && !polar_projection) {
            tools_menu.getGrid().setEnabled(option);
        }

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3 && size >= 0.05) {
            tools_menu.getBoundaries().setEnabled(option);
        }

        if(!d3 && !orbit && !julia_map && !polar_projection) {
            tools_menu.getZoomWindow().setEnabled(option);
        }

        file_menu.getZoomIn().setEnabled(option);
        toolbar.getZoomInButton().setEnabled(option);

        file_menu.getZoomOut().setEnabled(option);
        toolbar.getZoomOutButton().setEnabled(option);

        file_menu.getUp().setEnabled(option);
        file_menu.getDown().setEnabled(option);

        file_menu.getLeft().setEnabled(option);
        file_menu.getRight().setEnabled(option);

        if(!julia_map && !d3 && image_size < 4001 && !zoom_window) {
            tools_menu.getOrbit().setEnabled(option);
            toolbar.getOrbitButton().setEnabled(option);
        }
        options_menu.getPlanesMenu().setEnabled(option);

        if(!domain_coloring && !zoom_window && !julia && !perturbation && !init_val && !orbit && !d3 && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            tools_menu.getJuliaMap().setEnabled(option);
            toolbar.getJuliaMapButton().setEnabled(option);
        }

        if(!zoom_window && !orbit && !julia_map && !grid && !boundaries) {
            tools_menu.get3D().setEnabled(option);
            toolbar.get3DButton().setEnabled(option);
        }

        options_menu.getRotationMenu().setEnabled(option);

        if(!exterior_de && out_coloring_algorithm != DISTANCE_ESTIMATOR && !julia && !julia_map && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            options_menu.getPerturbation().setEnabled(option);
            options_menu.getInitialValue().setEnabled(option);
        }

        options_menu.getOverview().setEnabled(option);
        infobar.getOverview().setEnabled(option);

    }

    public void setLine() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = 0;
    }

    public void setDot() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = 1;
    }

    public void setShowOrbitConvergingPoint() {

        if(!options_menu.getShowOrbitConvergingPoint().isSelected()) {
            show_orbit_converging_point = false;
        }
        else {
            show_orbit_converging_point = true;
        }
    }

    public void setZoomWindowDashedLine() {

        zoom_window_style = 0;

    }

    public void setZoomWindowLine() {

        zoom_window_style = 1;

    }

    public void setOrbitColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Orbit Color", orbit_color, 0);

        main_panel.repaint();

    }

    public void setGridColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Grid Color", grid_color, 1);

        main_panel.repaint();

    }

    public void setZoomWindowColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Zoom Window Color", zoom_window_color, 2);

        main_panel.repaint();

    }

    public void setBoundariesColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Boundaries Color", boundaries_color, 3);

        main_panel.repaint();
    }

    public void storeColor(int num, Color color) {

        switch (num) {
            case 0:
                orbit_color = color;
                break;
            case 1:
                grid_color = color;
                break;
            case 2:
                zoom_window_color = color;
                break;
            case 3:
                boundaries_color = color;
                break;
        }
    }

    public void setFunction(int temp) {

        int oldSelected = function;
        function = temp;
        int l;

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        switch (function) {
            case MANDELBROTNTH:
                main_panel.repaint();

                String ans = (String)JOptionPane.showInputDialog(scroll_pane, "Enter the exponent of z.\nThe exponent can be a real number.", "Exponent", JOptionPane.QUESTION_MESSAGE, null, null, z_exponent);

                try {
                    z_exponent = Double.parseDouble(ans);
                    z_exponent = z_exponent == 0.0 ? 0.0 : z_exponent;
                }
                catch(Exception ex) {
                    if(ans == null) {
                        main_panel.repaint();
                    }
                    else {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                    }
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }
                optionsEnableShortcut();
                break;
            case MANDELBROTWTH:

                JLabel zw = new JLabel();
                zw.setIcon(getIcon("/fractalzoomer/icons/zw.png"));

                JTextField field_real = new JTextField();
                field_real.setText("" + z_exponent_complex[0]);
                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();
                field_imaginary.setText("" + z_exponent_complex[1]);

                Object[] message = {
                    " ",
                    zw,
                    "Set the real and imaginary part of the exponent of z.",
                    " ",
                    "Real:", field_real,
                    "Imaginary:", field_imaginary,
                    " ",};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Exponent", JOptionPane.OK_CANCEL_OPTION);

                double temp3,
                 temp4;
                if(res == JOptionPane.OK_OPTION) {
                    try {
                        temp3 = Double.parseDouble(field_real.getText());
                        temp4 = Double.parseDouble(field_imaginary.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                z_exponent_complex[0] = temp3 == 0.0 ? 0.0 : temp3;
                z_exponent_complex[1] = temp4 == 0.0 ? 0.0 : temp4;

                optionsEnableShortcut();
                break;
            case NEWTON3:
            case NEWTON4:
            case NEWTONGENERALIZED3:
            case NEWTONGENERALIZED8:
            case NEWTONSIN:
            case NEWTONCOS:
            case HALLEY3:
            case HALLEY4:
            case HALLEYGENERALIZED3:
            case HALLEYGENERALIZED8:
            case HALLEYSIN:
            case HALLEYCOS:
            case SCHRODER3:
            case SCHRODER4:
            case SCHRODERGENERALIZED3:
            case SCHRODERGENERALIZED8:
            case SCHRODERSIN:
            case SCHRODERCOS:
            case HOUSEHOLDER3:
            case HOUSEHOLDER4:
            case HOUSEHOLDERGENERALIZED3:
            case HOUSEHOLDERGENERALIZED8:
            case HOUSEHOLDERSIN:
            case HOUSEHOLDERCOS:
            case SECANT3:
            case SECANT4:
            case SECANTGENERALIZED3:
            case SECANTGENERALIZED8:
            case SECANTCOS:
            case STEFFENSEN3:
            case STEFFENSEN4:
            case STEFFENSENGENERALIZED3:
            case MULLER3:
            case MULLER4:
            case MULLERGENERALIZED3:
            case MULLERGENERALIZED8:
            case MULLERSIN:
            case MULLERCOS:
                optionsEnableShortcut2();
                break;
            case MANDELPOLY:
            case NEWTONPOLY:
            case HALLEYPOLY:
            case SCHRODERPOLY:
            case HOUSEHOLDERPOLY:
            case SECANTPOLY:
            case STEFFENSENPOLY:
            case MULLERPOLY:
                JLabel polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));

                JPanel poly1 = new JPanel();
                JLabel poly_label_1 = new JLabel();
                poly_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField poly_coef_1 = new JTextField(30);
                poly_coef_1.addAncestorListener(new RequestFocusListener());
                poly_coef_1.setText("" + coefficients[0]);
                poly1.setLayout(new FlowLayout());
                poly1.add(poly_label_1);
                poly1.add(new JLabel(""));
                poly1.add(poly_coef_1);

                JPanel poly2 = new JPanel();
                JLabel poly_label_2 = new JLabel();
                poly_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField poly_coef_2 = new JTextField(30);
                poly_coef_2.setText("" + coefficients[1]);
                poly2.setLayout(new FlowLayout());
                poly2.add(poly_label_2);
                poly2.add(new JLabel(" "));
                poly2.add(poly_coef_2);

                JPanel poly3 = new JPanel();
                JLabel poly_label_3 = new JLabel();
                poly_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField poly_coef_3 = new JTextField(30);
                poly_coef_3.setText("" + coefficients[2]);
                poly3.setLayout(new FlowLayout());
                poly3.add(poly_label_3);
                poly3.add(new JLabel(" "));
                poly3.add(poly_coef_3);

                JPanel poly4 = new JPanel();
                JLabel poly_label_4 = new JLabel();
                poly_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField poly_coef_4 = new JTextField(30);
                poly_coef_4.setText("" + coefficients[3]);
                poly4.setLayout(new FlowLayout());
                poly4.add(poly_label_4);
                poly4.add(new JLabel(" "));
                poly4.add(poly_coef_4);

                JPanel poly5 = new JPanel();
                JLabel poly_label_5 = new JLabel();
                poly_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField poly_coef_5 = new JTextField(30);
                poly_coef_5.setText("" + coefficients[4]);
                poly5.setLayout(new FlowLayout());
                poly5.add(poly_label_5);
                poly5.add(new JLabel(" "));
                poly5.add(poly_coef_5);

                JPanel poly6 = new JPanel();
                JLabel poly_label_6 = new JLabel();
                poly_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField poly_coef_6 = new JTextField(30);
                poly_coef_6.setText("" + coefficients[5]);
                poly6.setLayout(new FlowLayout());
                poly6.add(poly_label_6);
                poly6.add(new JLabel(" "));
                poly6.add(poly_coef_6);

                JPanel poly7 = new JPanel();
                JLabel poly_label_7 = new JLabel();
                poly_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField poly_coef_7 = new JTextField(30);
                poly_coef_7.setText("" + coefficients[6]);
                poly7.setLayout(new FlowLayout());
                poly7.add(poly_label_7);
                poly7.add(new JLabel(" "));
                poly7.add(poly_coef_7);

                JPanel poly8 = new JPanel();
                JLabel poly_label_8 = new JLabel();
                poly_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField poly_coef_8 = new JTextField(30);
                poly_coef_8.setText("" + coefficients[7]);
                poly8.setLayout(new FlowLayout());
                poly8.add(poly_label_8);
                poly8.add(new JLabel(" "));
                poly8.add(poly_coef_8);

                JPanel poly9 = new JPanel();
                JLabel poly_label_9 = new JLabel();
                poly_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField poly_coef_9 = new JTextField(30);
                poly_coef_9.setText("" + coefficients[8]);
                poly9.setLayout(new FlowLayout());
                poly9.add(poly_label_9);
                poly9.add(new JLabel(" "));
                poly9.add(poly_coef_9);

                JPanel poly10 = new JPanel();
                JLabel poly_label_10 = new JLabel();
                poly_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField poly_coef_10 = new JTextField(30);
                poly_coef_10.setText("" + coefficients[9]);
                poly10.setLayout(new FlowLayout());
                poly10.add(poly_label_10);
                poly10.add(new JLabel(" "));
                poly10.add(poly_coef_10);

                JPanel poly11 = new JPanel();
                JLabel poly_label_11 = new JLabel();
                poly_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField poly_coef_11 = new JTextField(30);
                poly_coef_11.setText("" + coefficients[10]);
                poly11.setLayout(new FlowLayout());
                poly11.add(poly_label_11);
                poly11.add(new JLabel(" "));
                poly11.add(poly_coef_11);

                Object[] poly_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    poly1,
                    poly2,
                    poly3,
                    poly4,
                    poly5,
                    poly6,
                    poly7,
                    poly8,
                    poly9,
                    poly10,
                    poly11,};

                int poly_res = JOptionPane.showConfirmDialog(scroll_pane, poly_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                double[] temp_coef = new double[11];

                if(poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(poly_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(poly_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(poly_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(poly_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(poly_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(poly_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(poly_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(poly_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(poly_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(poly_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(poly_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                boolean non_zero = false;
                for(l = 0; l < coefficients.length; l++) {
                    if(temp_coef[l] != 0) {
                        non_zero = true;
                        break;
                    }
                }

                if(!non_zero) {
                    JOptionPane.showMessageDialog(scroll_pane, "At least one coefficient must be non zero!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                for(l = 0; l < coefficients.length; l++) {
                    coefficients[l] = temp_coef[l] == 0.0 ? 0.0 : temp_coef[l];
                }

                poly = "p(z) = ";
                for(l = 0; l < coefficients.length - 2; l++) {
                    if(coefficients[l] > 0) {
                        if(poly.length() == 7) {
                            poly += coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                        }
                        else {
                            poly += "+" + coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                        }
                    }
                    else if(coefficients[l] < 0) {
                        poly += coefficients[l] + "z^" + (coefficients.length - l - 1) + "  ";
                    }
                }

                if(coefficients[l] > 0) {
                    if(poly.length() == 7) {
                        poly += coefficients[l] + "z  ";
                    }
                    else {
                        poly += "+" + coefficients[l] + "z  ";
                    }
                }
                else if(coefficients[l] < 0) {
                    poly += coefficients[l] + "z  ";
                }

                l++;
                if(coefficients[l] > 0) {
                    poly += "+" + coefficients[l];
                }
                else if(coefficients[l] < 0) {
                    poly += coefficients[l];
                }

                if(function == MANDELPOLY) {
                    optionsEnableShortcut();
                }
                else {
                    optionsEnableShortcut2();
                }
                break;

            case NEWTONFORMULA:

                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                }

                JTextField field_fz_formula = new JTextField(50);
                field_fz_formula.setText(user_fz_formula);
                field_fz_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula = new JTextField(50);
                field_dfz_formula.setText(user_dfz_formula);

                JPanel formula_fz_panel = new JPanel();

                formula_fz_panel.add(new JLabel("f(z) ="));
                formula_fz_panel.add(field_fz_formula);

                JPanel formula_dfz_panel = new JPanel();

                formula_dfz_panel.add(new JLabel("f '(z) ="));
                formula_dfz_panel.add(field_dfz_formula);

                JLabel imagelabel4 = new JLabel();
                imagelabel4.setIcon(getIcon("/fractalzoomer/icons/newton.png"));
                JPanel imagepanel4 = new JPanel();
                imagepanel4.add(imagelabel4);

                Object[] labels4 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message4 = {
                    labels4,
                    " ",
                    imagepanel4,
                    " ",
                    "Insert the function and its derivative.",
                    formula_fz_panel,
                    formula_dfz_panel,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message4, "Newton Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_dfz_formula.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula.getText();
                        user_dfz_formula = field_dfz_formula.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case HALLEYFORMULA:

                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula2 = new JTextField(50);
                field_fz_formula2.setText(user_fz_formula);
                field_fz_formula2.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula2 = new JTextField(50);
                field_dfz_formula2.setText(user_dfz_formula);

                JTextField field_ddfz_formula2 = new JTextField(50);
                field_ddfz_formula2.setText(user_ddfz_formula);

                JPanel formula_fz_panel2 = new JPanel();

                formula_fz_panel2.add(new JLabel("f(z) ="));
                formula_fz_panel2.add(field_fz_formula2);

                JPanel formula_dfz_panel2 = new JPanel();

                formula_dfz_panel2.add(new JLabel("f '(z) ="));
                formula_dfz_panel2.add(field_dfz_formula2);

                JPanel formula_ddfz_panel2 = new JPanel();

                formula_ddfz_panel2.add(new JLabel("f ''(z) ="));
                formula_ddfz_panel2.add(field_ddfz_formula2);

                JLabel imagelabel41 = new JLabel();
                imagelabel41.setIcon(getIcon("/fractalzoomer/icons/halley.png"));
                JPanel imagepanel41 = new JPanel();
                imagepanel41.add(imagelabel41);

                Object[] labels41 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message41 = {
                    labels41,
                    " ",
                    imagepanel41,
                    " ",
                    "Insert the function and its derivatives.",
                    formula_fz_panel2,
                    formula_dfz_panel2,
                    formula_ddfz_panel2,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message41, "Halley Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula2.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_dfz_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_ddfz_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula2.getText();
                        user_dfz_formula = field_dfz_formula2.getText();
                        user_ddfz_formula = field_ddfz_formula2.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case SCHRODERFORMULA:

                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula3 = new JTextField(50);
                field_fz_formula3.setText(user_fz_formula);
                field_fz_formula3.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula3 = new JTextField(50);
                field_dfz_formula3.setText(user_dfz_formula);

                JTextField field_ddfz_formula3 = new JTextField(50);
                field_ddfz_formula3.setText(user_ddfz_formula);

                JPanel formula_fz_panel3 = new JPanel();

                formula_fz_panel3.add(new JLabel("f(z) ="));
                formula_fz_panel3.add(field_fz_formula3);

                JPanel formula_dfz_panel3 = new JPanel();

                formula_dfz_panel3.add(new JLabel("f '(z) ="));
                formula_dfz_panel3.add(field_dfz_formula3);

                JPanel formula_ddfz_panel3 = new JPanel();

                formula_ddfz_panel3.add(new JLabel("f ''(z) ="));
                formula_ddfz_panel3.add(field_ddfz_formula3);

                JLabel imagelabel42 = new JLabel();
                imagelabel42.setIcon(getIcon("/fractalzoomer/icons/schroder.png"));
                JPanel imagepanel42 = new JPanel();
                imagepanel42.add(imagelabel42);

                Object[] labels42 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message42 = {
                    labels42,
                    " ",
                    imagepanel42,
                    " ",
                    "Insert the function and its derivatives.",
                    formula_fz_panel3,
                    formula_dfz_panel3,
                    formula_ddfz_panel3,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message42, "Schroder Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula3.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_dfz_formula3.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_ddfz_formula3.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula3.getText();
                        user_dfz_formula = field_dfz_formula3.getText();
                        user_ddfz_formula = field_ddfz_formula3.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERFORMULA:

                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula4 = new JTextField(50);
                field_fz_formula4.setText(user_fz_formula);
                field_fz_formula4.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula4 = new JTextField(50);
                field_dfz_formula4.setText(user_dfz_formula);

                JTextField field_ddfz_formula4 = new JTextField(50);
                field_ddfz_formula4.setText(user_ddfz_formula);

                JPanel formula_fz_panel4 = new JPanel();

                formula_fz_panel4.add(new JLabel("f(z) ="));
                formula_fz_panel4.add(field_fz_formula4);

                JPanel formula_dfz_panel4 = new JPanel();

                formula_dfz_panel4.add(new JLabel("f '(z) ="));
                formula_dfz_panel4.add(field_dfz_formula4);

                JPanel formula_ddfz_panel4 = new JPanel();

                formula_ddfz_panel4.add(new JLabel("f ''(z) ="));
                formula_ddfz_panel4.add(field_ddfz_formula4);

                JLabel imagelabel43 = new JLabel();
                imagelabel43.setIcon(getIcon("/fractalzoomer/icons/householder.png"));
                JPanel imagepanel43 = new JPanel();
                imagepanel43.add(imagelabel43);

                Object[] labels43 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message43 = {
                    labels43,
                    " ",
                    imagepanel43,
                    " ",
                    "Insert the function and its derivatives.",
                    formula_fz_panel4,
                    formula_dfz_panel4,
                    formula_ddfz_panel4,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message43, "Householder Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula4.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_dfz_formula4.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_ddfz_formula4.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula4.getText();
                        user_dfz_formula = field_dfz_formula4.getText();
                        user_ddfz_formula = field_ddfz_formula4.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }
                optionsEnableShortcut2();
                break;
            case SECANTFORMULA:
                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula5 = new JTextField(50);
                field_fz_formula5.setText(user_fz_formula);
                field_fz_formula5.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel5 = new JPanel();

                formula_fz_panel5.add(new JLabel("f(z) ="));
                formula_fz_panel5.add(field_fz_formula5);

                JLabel imagelabel44 = new JLabel();
                imagelabel44.setIcon(getIcon("/fractalzoomer/icons/secant.png"));
                JPanel imagepanel44 = new JPanel();
                imagepanel44.add(imagelabel44);

                Object[] labels44 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message44 = {
                    labels44,
                    " ",
                    imagepanel44,
                    " ",
                    "Insert the function.",
                    formula_fz_panel5,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message44, "Secant Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula5.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula5.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case STEFFENSENFORMULA:
                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula6 = new JTextField(50);
                field_fz_formula6.setText(user_fz_formula);
                field_fz_formula6.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel6 = new JPanel();

                formula_fz_panel6.add(new JLabel("f(z) ="));
                formula_fz_panel6.add(field_fz_formula6);

                JLabel imagelabel45 = new JLabel();
                imagelabel45.setIcon(getIcon("/fractalzoomer/icons/steffensen.png"));
                JPanel imagepanel45 = new JPanel();
                imagepanel45.add(imagelabel45);

                Object[] labels45 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message45 = {
                    labels45,
                    " ",
                    imagepanel45,
                    " ",
                    "Insert the function.",
                    formula_fz_panel6,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message45, "Steffensen Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula6.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula6.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }
                optionsEnableShortcut2();
                break;
            case MULLERFORMULA:
                if(function != oldSelected) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula7 = new JTextField(50);
                field_fz_formula7.setText(user_fz_formula);
                field_fz_formula7.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel7 = new JPanel();

                formula_fz_panel7.add(new JLabel("f(z) ="));
                formula_fz_panel7.add(field_fz_formula7);

                JPanel images_panel = new JPanel();
                images_panel.setLayout(new GridLayout(5, 1));

                JLabel imagelabel46 = new JLabel();
                imagelabel46.setIcon(getIcon("/fractalzoomer/icons/muller1.png"));
                JPanel imagepanel46 = new JPanel();
                imagepanel46.add(imagelabel46);

                JLabel imagelabel56 = new JLabel();
                imagelabel56.setIcon(getIcon("/fractalzoomer/icons/muller2.png"));
                JPanel imagepanel56 = new JPanel();
                imagepanel56.add(imagelabel56);

                JLabel imagelabel66 = new JLabel();
                imagelabel66.setIcon(getIcon("/fractalzoomer/icons/muller3.png"));
                JPanel imagepanel66 = new JPanel();
                imagepanel66.add(imagelabel66);

                JLabel imagelabel76 = new JLabel();
                imagelabel76.setIcon(getIcon("/fractalzoomer/icons/muller4.png"));
                JPanel imagepanel76 = new JPanel();
                imagepanel76.add(imagelabel76);

                JLabel imagelabel86 = new JLabel();
                imagelabel86.setIcon(getIcon("/fractalzoomer/icons/muller5.png"));
                JPanel imagepanel86 = new JPanel();
                imagepanel86.add(imagelabel86);

                images_panel.add(imagelabel46);
                images_panel.add(imagelabel56);
                images_panel.add(imagelabel66);
                images_panel.add(imagelabel76);
                images_panel.add(imagelabel86);

                JScrollPane scroll_pane2 = new JScrollPane(images_panel);
                scroll_pane2.setPreferredSize(new Dimension(500, 120));
                scroll_pane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                Object[] labels46 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message46 = {
                    labels46,
                    " ",
                    scroll_pane2,
                    "Insert the function.",
                    formula_fz_panel7,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message46, "Muller Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_fz_formula7.getText());
                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_fz_formula = field_fz_formula7.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case NOVA:
                JLabel novazw = new JLabel();
                novazw.setIcon(getIcon("/fractalzoomer/icons/novazw.png"));

                field_real = new JTextField();
                field_real.setText("" + z_exponent_nova[0]);
                field_real.addAncestorListener(new RequestFocusListener());

                field_imaginary = new JTextField();
                field_imaginary.setText("" + z_exponent_nova[1]);

                JTextField field_real2 = new JTextField();
                field_real2.setText("" + relaxation[0]);

                JTextField field_imaginary2 = new JTextField();
                field_imaginary2.setText("" + relaxation[1]);

                String[] method = {"Newton", "Halley", "Schroder", "Householder", "Secant", "Steffensen", "Muller"};

                JComboBox method_choice = new JComboBox(method);
                method_choice.setSelectedIndex(nova_method);
                method_choice.setToolTipText("Selects the root finding method for the Nova function.");
                method_choice.setFocusable(false);

                Object[] message2 = {
                    " ",
                    "Root Finding Method",
                    method_choice,
                    " ",
                    novazw,
                    "Set the real and imaginary part of the exponent.",
                    "Real:", field_real,
                    "Imaginary:", field_imaginary,
                    " ",
                    "Set the real and imaginary part of the relaxation.",
                    "Real:", field_real2,
                    "Imaginary:", field_imaginary2,
                    " ",};

                res = JOptionPane.showConfirmDialog(scroll_pane, message2, "Method, Exponent, Relaxation", JOptionPane.OK_CANCEL_OPTION);

                double temp5,
                 temp6;
                int temp7;
                if(res == JOptionPane.OK_OPTION) {
                    try {
                        temp7 = method_choice.getSelectedIndex();
                        temp3 = Double.parseDouble(field_real.getText());
                        temp4 = Double.parseDouble(field_imaginary.getText());
                        temp5 = Double.parseDouble(field_real2.getText());
                        temp6 = Double.parseDouble(field_imaginary2.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                z_exponent_nova[0] = temp3 == 0.0 ? 0.0 : temp3;
                z_exponent_nova[1] = temp4 == 0.0 ? 0.0 : temp4;

                relaxation[0] = temp5 == 0.0 ? 0.0 : temp5;
                relaxation[1] = temp6 == 0.0 ? 0.0 : temp6;

                nova_method = temp7;

                optionsEnableShortcut();

                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutTestMenu().setEnabled(false);
                break;
            case SIERPINSKI_GASKET:
                optionsEnableShortcut();
                tools_menu.getJulia().setEnabled(false);
                toolbar.getJuliaButton().setEnabled(false);
                tools_menu.getJuliaMap().setEnabled(false);
                toolbar.getJuliaMapButton().setEnabled(false);
                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getPerturbation().setEnabled(false);
                options_menu.getInitialValue().setEnabled(false);
                break;
            case USER_FORMULA_ITERATION_BASED:

                JTextField field_formula_it_based1 = new JTextField(50);
                field_formula_it_based1.setText(user_formula_iteration_based[0]);
                field_formula_it_based1.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_it_based2 = new JTextField(50);
                field_formula_it_based2.setText(user_formula_iteration_based[1]);

                JTextField field_formula_it_based3 = new JTextField(50);
                field_formula_it_based3.setText(user_formula_iteration_based[2]);

                JTextField field_formula_it_based4 = new JTextField(50);
                field_formula_it_based4.setText(user_formula_iteration_based[3]);

                JPanel formula_panel_it1 = new JPanel();

                formula_panel_it1.add(new JLabel("Every 1st iteration, z ="));
                formula_panel_it1.add(field_formula_it_based1);

                JPanel formula_panel_it2 = new JPanel();

                formula_panel_it2.add(new JLabel("Every 2nd iteration, z ="));
                formula_panel_it2.add(field_formula_it_based2);

                JPanel formula_panel_it3 = new JPanel();

                formula_panel_it3.add(new JLabel("Every 3rd iteration, z ="));
                formula_panel_it3.add(field_formula_it_based3);

                JPanel formula_panel_it4 = new JPanel();

                formula_panel_it4.add(new JLabel("Every 4th iteration, z ="));
                formula_panel_it4.add(field_formula_it_based4);

                JLabel bail2 = new JLabel("Bailout Technique:");
                bail2.setFont(new Font("Arial", Font.BOLD, 11));

                String[] method42 = {"Escaping Algorithm", "Converging Algorithm"};

                JComboBox method42_choice = new JComboBox(method42);
                method42_choice.setSelectedIndex(bail_technique);
                method42_choice.setToolTipText("Selects the bailout technique.");
                method42_choice.setFocusable(false);

                Object[] labels32 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message32 = {
                    labels32,
                    bail2,
                    method42_choice,
                    " ",
                    "Insert your formulas. Different formula will be evaluated in every iteration.",
                    formula_panel_it1,
                    formula_panel_it2,
                    formula_panel_it3,
                    formula_panel_it4,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message32, "User Formula Iteration Based", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {

                    boolean temp_bool = false;

                    try {
                        parser.parse(field_formula_it_based1.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 1st iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 2nd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based3.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 3rd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based4.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 4th iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        user_formula_iteration_based[0] = field_formula_it_based1.getText();
                        user_formula_iteration_based[1] = field_formula_it_based2.getText();
                        user_formula_iteration_based[2] = field_formula_it_based3.getText();
                        user_formula_iteration_based[3] = field_formula_it_based4.getText();
                        user_formula_c = temp_bool;
                        bail_technique = method42_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case USER_FORMULA_CONDITIONAL:

                JPanel formula_panel_cond1 = new JPanel();
                formula_panel_cond1.setLayout(new GridLayout(2, 2));

                JTextField field_condition = new JTextField(24);
                field_condition.setText(user_formula_conditions[0]);
                field_condition.addAncestorListener(new RequestFocusListener());

                JTextField field_condition2 = new JTextField(24);
                field_condition2.setText(user_formula_conditions[1]);

                formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(field_condition);
                formula_panel_cond1.add(field_condition2);

                JTextField field_formula_cond1 = new JTextField(50);
                field_formula_cond1.setText(user_formula_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(50);
                field_formula_cond2.setText(user_formula_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(50);
                field_formula_cond3.setText(user_formula_condition_formula[2]);

                JPanel formula_panel_cond11 = new JPanel();

                formula_panel_cond11.add(new JLabel("left > right, z ="));
                formula_panel_cond11.add(field_formula_cond1);

                JPanel formula_panel_cond12 = new JPanel();

                formula_panel_cond12.add(new JLabel("left < right, z ="));
                formula_panel_cond12.add(field_formula_cond2);

                JPanel formula_panel_cond13 = new JPanel();

                formula_panel_cond13.add(new JLabel("left = right, z ="));
                formula_panel_cond13.add(field_formula_cond3);

                JLabel bail3 = new JLabel("Bailout Technique:");
                bail3.setFont(new Font("Arial", Font.BOLD, 11));

                String[] method43 = {"Escaping Algorithm", "Converging Algorithm"};

                JComboBox method43_choice = new JComboBox(method43);
                method43_choice.setSelectedIndex(bail_technique);
                method43_choice.setToolTipText("Selects the bailout technique.");
                method43_choice.setFocusable(false);

                Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message33 = {
                    labels33,
                    bail3,
                    method43_choice,
                    " ",
                    "Insert your formulas, that will be evaluated under a specific condition.",
                    formula_panel_cond1,
                    formula_panel_cond11,
                    formula_panel_cond12,
                    formula_panel_cond13,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message33, "User Formula Conditional", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {

                    boolean temp_bool = false;

                    try {
                        parser.parse(field_condition.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_condition2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_cond2.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_cond3.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        user_formula_condition_formula[0] = field_formula_cond1.getText();
                        user_formula_condition_formula[1] = field_formula_cond2.getText();
                        user_formula_condition_formula[2] = field_formula_cond3.getText();
                        user_formula_conditions[0] = field_condition.getText();
                        user_formula_conditions[1] = field_condition2.getText();
                        user_formula_c = temp_bool;
                        bail_technique = method43_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case MANDELBROT:
                if(!burning_ship && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                    options_menu.getDistanceEstimation().setEnabled(true);
                }
                if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !init_val && !perturbation) {
                    out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                }
                if(out_coloring_algorithm != BIOMORPH) {
                    out_coloring_modes[BIOMORPH].setEnabled(true);
                }
                if(out_coloring_algorithm != BANDED) {
                    out_coloring_modes[BANDED].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                }
                if(out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                    out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                }
                if(out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                }
                if(out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if(out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                    out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                    out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                }
                if(out_coloring_algorithm != ESCAPE_TIME_GRID) {
                    out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                }
                break;
            case USER_FORMULA:

                JTextField field_formula = new JTextField(50);
                field_formula.setText(user_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_formula2 = new JTextField(50);
                field_formula2.setText(user_formula2);

                JPanel formula_panel = new JPanel();

                formula_panel.add(new JLabel("z ="));
                formula_panel.add(field_formula);

                JPanel formula_panel2 = new JPanel();

                formula_panel2.add(new JLabel("c ="));
                formula_panel2.add(field_formula2);

                JLabel bail = new JLabel("Bailout Technique:");
                bail.setFont(new Font("Arial", Font.BOLD, 11));

                String[] method4 = {"Escaping Algorithm", "Converging Algorithm"};

                JComboBox method4_choice = new JComboBox(method4);
                method4_choice.setSelectedIndex(bail_technique);
                method4_choice.setToolTipText("Selects the bailout technique.");
                method4_choice.setFocusable(false);

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message3 = {
                    labels3,
                    bail,
                    method4_choice,
                    " ",
                    "Insert your formula, that will be evaluated in every iteration.",
                    formula_panel,
                    formula_panel2,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Formula", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_formula.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        boolean temp_bool = parser.foundC();

                        parser.parse(field_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the c formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_formula = field_formula.getText();
                        user_formula2 = field_formula2.getText();
                        user_formula_c = temp_bool;
                        bail_technique = method4_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case USER_FORMULA_COUPLED:
                JTextField field_formula_coupled = new JTextField(50);
                field_formula_coupled.setText(user_formula_coupled[0]);
                field_formula_coupled.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_coupled2 = new JTextField(50);
                field_formula_coupled2.setText(user_formula_coupled[1]);

                JTextField field_formula_coupled3 = new JTextField(50);
                field_formula_coupled3.setText(user_formula_coupled[2]);

                JPanel formula_panel_coupled = new JPanel();

                formula_panel_coupled.add(new JLabel("z ="));
                formula_panel_coupled.add(field_formula_coupled);

                JPanel formula_panel_coupled2 = new JPanel();

                formula_panel_coupled2.add(new JLabel("z2 ="));
                formula_panel_coupled2.add(field_formula_coupled2);

                JPanel formula_panel_coupled3 = new JPanel();

                formula_panel_coupled3.add(new JLabel("z2(0) ="));
                formula_panel_coupled3.add(field_formula_coupled3);

                JLabel bail5 = new JLabel("Bailout Technique:");
                bail5.setFont(new Font("Arial", Font.BOLD, 11));

                String[] method5 = {"Escaping Algorithm", "Converging Algorithm"};

                JComboBox method5_choice = new JComboBox(method5);
                method5_choice.setSelectedIndex(bail_technique);
                method5_choice.setToolTipText("Selects the bailout technique.");
                method5_choice.setFocusable(false);

                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.setPreferredSize(new Dimension(410, 185));
                tabbedPane.setFocusable(false);

                JPanel formula_panel3 = new JPanel();
                JPanel coupling_options_panel = new JPanel();

                tabbedPane.addTab("Formula", formula_panel3);
                tabbedPane.addTab("Coupling", coupling_options_panel);

                JSlider coupling_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(coupling * 100));
                coupling_slid.setPreferredSize(new Dimension(360, 45));
                coupling_slid.setFocusable(false);
                coupling_slid.setToolTipText("Sets the percentange of coupling.");
                coupling_slid.setPaintLabels(true);

                Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
                table3.put(0, new JLabel("0.0"));
                table3.put(25, new JLabel("0.25"));
                table3.put(50, new JLabel("0.5"));
                table3.put(75, new JLabel("0.75"));
                table3.put(100, new JLabel("1.0"));
                coupling_slid.setLabelTable(table3);

                JPanel coupling_panel = new JPanel();
                coupling_panel.add(new JLabel("Coupling: "));
                coupling_panel.add(coupling_slid);

                final JTextField field_amplitude = new JTextField(8);
                field_amplitude.setText("" + coupling_amplitude);

                final JTextField field_frequency = new JTextField(8);
                field_frequency.setText("" + coupling_frequency);

                final JTextField field_seed = new JTextField(8);
                field_seed.setText("" + coupling_seed);

                String[] coupling_method_str = {"Simple", "Cosine", "Random"};

                final JComboBox coupling_method_choice = new JComboBox(coupling_method_str);
                coupling_method_choice.setSelectedIndex(coupling_method);
                coupling_method_choice.setToolTipText("Selects the coupling method.");
                coupling_method_choice.setFocusable(false);

                JPanel coupling_panel2 = new JPanel();

                coupling_panel2.setPreferredSize(new Dimension(400, 40));
                coupling_panel2.setLayout(new GridLayout(2, 4));
                coupling_panel2.add(new JLabel("Method:", SwingConstants.HORIZONTAL));
                coupling_panel2.add(new JLabel("Amplitude:", SwingConstants.HORIZONTAL));
                coupling_panel2.add(new JLabel("Frequency:", SwingConstants.HORIZONTAL));
                coupling_panel2.add(new JLabel("Seed:", SwingConstants.HORIZONTAL));
                coupling_panel2.add(coupling_method_choice);
                coupling_panel2.add(field_amplitude);
                coupling_panel2.add(field_frequency);
                coupling_panel2.add(field_seed);

                JPanel p1 = new JPanel();
                p1.setLayout(new FlowLayout(FlowLayout.LEFT));
                p1.setPreferredSize(new Dimension(400, 18));
                p1.add(new JLabel("Insert your formulas, that will be evaluated in every iteration."));

                JPanel p2 = new JPanel();
                p2.setLayout(new FlowLayout(FlowLayout.LEFT));
                p2.setPreferredSize(new Dimension(400, 18));
                p2.add(new JLabel("Insert the initial value for the second formula."));

                formula_panel3.add(p1);
                formula_panel3.add(formula_panel_coupled);
                formula_panel3.add(formula_panel_coupled2);
                formula_panel3.add(p2);
                formula_panel3.add(formula_panel_coupled3);

                JPanel p3 = new JPanel();
                p3.setLayout(new FlowLayout(FlowLayout.LEFT));
                p3.setPreferredSize(new Dimension(400, 18));
                p3.add(new JLabel("Select the coupling options between the formulas."));

                coupling_options_panel.add(p3);
                coupling_options_panel.add(coupling_panel2);
                coupling_options_panel.add(coupling_panel);

                if(coupling_method == 0) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(false);
                }
                else if(coupling_method == 1) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(true);
                    field_amplitude.setEnabled(true);
                }
                else {
                    field_seed.setEnabled(true);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(true);
                }

                coupling_method_choice.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(coupling_method_choice.getSelectedIndex() == 0) {
                            field_seed.setEnabled(false);
                            field_frequency.setEnabled(false);
                            field_amplitude.setEnabled(false);
                        }
                        else if(coupling_method_choice.getSelectedIndex() == 1) {
                            field_seed.setEnabled(false);
                            field_frequency.setEnabled(true);
                            field_amplitude.setEnabled(true);
                        }
                        else {
                            field_seed.setEnabled(true);
                            field_frequency.setEnabled(false);
                            field_amplitude.setEnabled(true);
                        }
                    }
                });

                Object[] labels5 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, v1 - v20, point");

                Object[] message5 = {
                    labels5,
                    bail5,
                    method5_choice,
                    " ",
                    tabbedPane,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message5, "User Formula Coupled", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        parser.parse(field_formula_coupled.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        boolean temp_bool = parser.foundC();

                        parser.parse(field_formula_coupled2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z2 formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_coupled3.getText());

                        if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn, v1 - v20 cannot be used in the z2(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        double temp_amp, temp_freq;
                        int temp_seed;
                        try {
                            temp_amp = Double.parseDouble(field_amplitude.getText());
                            temp_freq = Double.parseDouble(field_frequency.getText());
                            temp_seed = Integer.parseInt(field_seed.getText());
                        }
                        catch(Exception ex) {
                            JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            function = oldSelected;
                            return;
                        }

                        user_formula_coupled[0] = field_formula_coupled.getText();
                        user_formula_coupled[1] = field_formula_coupled2.getText();
                        user_formula_coupled[2] = field_formula_coupled3.getText();
                        user_formula_c = temp_bool;
                        bail_technique = method5_choice.getSelectedIndex();
                        coupling = coupling_slid.getValue() / 100.0;
                        coupling_amplitude = temp_amp;
                        coupling_seed = temp_seed;
                        coupling_frequency = temp_freq;
                        coupling_method = coupling_method_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        function = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    function = oldSelected;
                    return;
                }
                optionsEnableShortcut();
                break;
            default:
                optionsEnableShortcut();
                break;
        }

        if(oldSelected != function) {
            if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                user_out_coloring_algorithm = 0;

                outcoloring_formula = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";

                user_outcoloring_conditions[0] = "im(z)";
                user_outcoloring_conditions[1] = "0";

                user_outcoloring_condition_formula[0] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
                user_outcoloring_condition_formula[1] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp))) + 50";
                user_outcoloring_condition_formula[2] = "n + (log(cbail) / 2 - log(norm(p - pp))) / (log(norm(z - p)) - log(norm(p - pp)))";
            }
            else if(function == MAGNET1) {
                user_out_coloring_algorithm = 1;

                outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                user_outcoloring_conditions[0] = "norm(z-1)^2";
                user_outcoloring_conditions[1] = "1e-12";

                user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                user_outcoloring_condition_formula[1] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                user_outcoloring_condition_formula[2] = "n + (log(1e-12) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
            }
            else if(function == MAGNET2) {
                user_out_coloring_algorithm = 1;

                outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                user_outcoloring_conditions[0] = "norm(z-1)^2";
                user_outcoloring_conditions[1] = "1e-9";

                user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                user_outcoloring_condition_formula[1] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
                user_outcoloring_condition_formula[2] = "n + (log(1e-9) / 2 - log(norm(p - 1))) / (log(norm(z - 1)) - log(norm(p - 1)))";
            }
            else {
                user_out_coloring_algorithm = 0;

                outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

                user_outcoloring_conditions[0] = "im(z)";
                user_outcoloring_conditions[1] = "0";

                user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
                user_outcoloring_condition_formula[1] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001)) + 50";
                user_outcoloring_condition_formula[2] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
            }
        }

        defaultFractalSettings();

    }

    public void setBailout() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + bailout + " for bailout number.\nEnter the new bailout number.", "Bailout Number", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if(temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Bailout value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 1e70) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Bailout value must be less than 1e70.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            bailout = temp;

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setRotation() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        final JSlider rotation_slid = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int)(rotation)));
        rotation_slid.setPreferredSize(new Dimension(300, 35));
        rotation_slid.setMajorTickSpacing(90);
        rotation_slid.setMinorTickSpacing(1);
        rotation_slid.setToolTipText("Sets the rotation.");
        //color_blend.setPaintTicks(true);
        rotation_slid.setPaintLabels(true);
        //rotation_slid.setSnapToTicks(true);
        rotation_slid.setFocusable(false);

        final JTextField field_rotation = new JTextField();
        field_rotation.setText("" + rotation);

        field_rotation.addAncestorListener(new RequestFocusListener());

        rotation_slid.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                field_rotation.setText("" + ((double)rotation_slid.getValue()));
            }

        });

        field_rotation.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int)(temp + 0.5)));
                }
                catch(Exception ex) {

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int)(temp + 0.5)));
                }
                catch(Exception ex) {

                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int)(temp + 0.5)));
                }
                catch(Exception ex) {

                }
            }

        });

        final JTextField field_real = new JTextField();

        if(rotation_center[0] == 0) {
            field_real.setText("" + 0.0);
        }
        else {
            field_real.setText("" + rotation_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if(rotation_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        }
        else {
            field_imaginary.setText("" + rotation_center[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!current_center.isSelected()) {
                    if(rotation_center[0] == 0) {
                        field_real.setText("" + 0.0);
                    }
                    else {
                        field_real.setText("" + rotation_center[0]);
                    }

                    field_real.setEditable(true);

                    if(rotation_center[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    }
                    else {
                        field_imaginary.setText("" + rotation_center[1]);
                    }
                    field_imaginary.setEditable(true);
                }
                else {
                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                    field_real.setText("" + p.x);
                    field_real.setEditable(false);
                    field_imaginary.setText("" + p.y);
                    field_imaginary.setEditable(false);
                }
            }
        });

        Object[] message = {
            " ",
            "Set the rotation angle in degrees.",
            "Rotation:", rotation_slid, field_rotation,
            " ",
            "Set the rotation center.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center, " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Rotation", JOptionPane.OK_CANCEL_OPTION);

        double tempReal, tempImaginary, temp;

        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(field_rotation.getText());
                tempReal = Double.parseDouble(field_real.getText());
                tempImaginary = Double.parseDouble(field_imaginary.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp < -360) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "Rotation angle must be greater than -361.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
            if(temp > 360) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Rotation angle must be less than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        rotation = temp;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        rotation_center[0] = tempReal;
        rotation_center[1] = tempImaginary;

        rotation_center[0] = rotation_center[0] == 0 ? 0 : rotation_center[0];
        rotation_center[1] = rotation_center[1] == 0 ? 0 : rotation_center[1];

        xCenter = rotation_center[0];
        yCenter = rotation_center[1];

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void increaseRotation() {

        if(rotation == 360) {
            rotation = 0;
        }
        rotation++;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        setOptions(false);

        progress.setValue(0);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void decreaseRotation() {

        if(rotation == -360) {
            rotation = 0;
        }
        rotation--;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        setOptions(false);

        progress.setValue(0);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setBurningShip() {

        if(!options_menu.getBurningShipOpt().isSelected()) {
            burning_ship = false;
            if(function == MANDELBROT && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                options_menu.getDistanceEstimation().setEnabled(true);
            }
        }
        else {
            burning_ship = true;
            options_menu.getDistanceEstimation().setEnabled(false);
        }

        if(function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    public void setMandelGrass() {

        if(!options_menu.getMandelGrassOpt().isSelected()) {
            mandel_grass = false;
            if(function == MANDELBROT && !burning_ship && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                options_menu.getDistanceEstimation().setEnabled(true);
            }
        }
        else {

            if(backup_orbit != null && orbit) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            JTextField field_real = new JTextField();
            field_real.setText("" + mandel_grass_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();
            field_imaginary.setText("" + mandel_grass_vals[1]);

            Object[] message = {
                " ",
                "Set the real and imaginary part of the mandel grass.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Mandel Grass", JOptionPane.OK_CANCEL_OPTION);

            double temp, temp2;
            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp = Double.parseDouble(field_real.getText());
                    temp2 = Double.parseDouble(field_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getMandelGrassOpt().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                options_menu.getMandelGrassOpt().setSelected(false);
                return;
            }

            mandel_grass_vals[0] = temp;
            mandel_grass_vals[1] = temp2;

            mandel_grass = true;
            options_menu.getDistanceEstimation().setEnabled(false);
        }

        if(function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    public void startingPosition() {

        setOptions(false);

        switch (function) {
            case LAMBDA:
                if(julia) {
                    xCenter = 0.5;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 8;
                }
                break;
            case MAGNET1:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 28;
                }
                else {
                    xCenter = 1.35;
                    yCenter = 0;
                    size = 8;
                }
                break;
            case MAGNET2:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 56;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 7;
                }
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                break;
            case FORMULA1:
            case FORMULA44:
            case FORMULA45:
            case FORMULA43:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA4:
            case FORMULA5:
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                break;
            case FORMULA7:
            case FORMULA12:
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
            case FORMULA42:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case FORMULA27:
                if(julia) {
                    xCenter = -2;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                }
                break;
            case FORMULA38:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 1.5;
                }
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
        }

        if(size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setWholeImageDone(Boolean temp) {

        whole_image_done = temp;

    }

    public void setSizeOfImage() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "Your image size is " + image_size + "x" + image_size + " .\nEnter the new image size.\nOnly one dimension is required.", "Image Size", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if(temp < 209) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be greater than 209.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp > 5000) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be less than than 5001.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            whole_image_done = false;

            old_grid = false;

            old_boundaries = false;

            image_size = temp;

            if(image_size > 4000) {
                tools_menu.getOrbit().setEnabled(false);
                toolbar.getOrbitButton().setEnabled(false);
                toolbar.getOrbitButton().setSelected(false);
                tools_menu.getOrbit().setSelected(false);
                setOrbitOption();
            }

            if(!d3) {
                ThreadDraw.setArrays(image_size);
            }

            main_panel.setPreferredSize(new Dimension(image_size, image_size));

            setOptions(false);

            if(!d3) {
                progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));
            }

            progress.setValue(0);

            SwingUtilities.updateComponentTreeUI(this);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            //last_used = null;
            //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            //Graphics2D graphics = last_used.createGraphics();
            //graphics.drawImage(image, 0, 0, image_size, image_size, null);
            last_used = null;

            image = null;

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            backup_orbit = null;

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public JScrollPane getScrollPane() {

        return scroll_pane;

    }

    public void setFastJuliaFilters() {

        if(!options_menu.getFastJuliaFiltersOptions().isSelected()) {
            fast_julia_filters = false;
            main_panel.repaint();
        }
        else {
            fast_julia_filters = true;
            main_panel.repaint();
        }

    }

    private void fastJulia() {

        if(!threadsAvailable()) {
            return;
        }

        double temp_xCenter, temp_yCenter, temp_size, temp_xJuliaCenter, temp_yJuliaCenter;
        int temp_bailout;
        int temp_max_iterations = max_iterations > 250 ? 250 : max_iterations;

        int x1, y1;

        try {
            x1 = (int)main_panel.getMousePosition().getX();
            y1 = (int)main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(polar_projection) {
            double start;
            double center = Math.log(size);

            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image_size;

            double mulx = muly * height_ratio;

            start = center - mulx * image_size * 0.5;

            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x1 * mulx + start);

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter + r * cf, yCenter + r * sf, rotation_vals, rotation_center);

            temp_xJuliaCenter = p.x;

            temp_yJuliaCenter = p.y;
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_xcenter_size = xCenter - size_2_x;
            double temp_ycenter_size = yCenter + size_2_y;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, rotation_vals, rotation_center);

            temp_xJuliaCenter = p.x;
            temp_yJuliaCenter = p.y;

        }

        Arrays.fill(((DataBufferInt)fast_julia_image.getRaster().getDataBuffer()).getData(), 0, FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE, 0);

        switch (function) {
            case LAMBDA:
                temp_xCenter = 0.5;
                temp_yCenter = 0;
                temp_size = 6;
                break;
            case MAGNET1:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 28;
                break;
            case MAGNET2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 56;
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 7;
                break;
            case EXP:
            case LOG:
            case SIN:
            case COS:
            case TAN:
            case COT:
            case SINH:
            case COSH:
            case TANH:
            case COTH:
            case FORMULA30:
            case FORMULA31:
            case FORMULA18:
            case FORMULA34:
            case FORMULA39:
            case FORMULA40:
            case FORMULA41:
            case COUPLED_MANDELBROT:
            case COUPLED_MANDELBROT_BURNING_SHIP:
            case FORMULA2:
            case FORMULA13:
            case FORMULA14:
            case FORMULA15:
            case FORMULA16:
            case FORMULA17:
            case FORMULA19:
            case FORMULA46:
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                break;
            case FORMULA1:
            case FORMULA43:
            case FORMULA44:
            case FORMULA45:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
            case FORMULA8:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                break;
            case FORMULA4:
            case FORMULA5:
            case FORMULA11:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                break;
            case FORMULA7:
            case FORMULA12:
            case FORMULA28:
            case FORMULA29:
            case FORMULA42:
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                break;
            case FORMULA27:
                temp_xCenter = -2;
                temp_yCenter = 0;
                temp_size = 6;
                break;
            default:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                break;
        }

        ThreadDraw.resetThreadData(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        try {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    if(greedy_algorithm) {
                        if(greedy_algorithm_selection == BOUNDARY_TRACING) {
                            threads[i][j] = new BoundaryTracingDraw(color_choice, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, fast_julia_filters, fast_julia_image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, temp_xJuliaCenter, temp_yJuliaCenter);
                        }
                        else if(greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                            threads[i][j] = new DivideAndConquerDraw(color_choice, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, fast_julia_filters, fast_julia_image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, temp_xJuliaCenter, temp_yJuliaCenter);
                        }
                    }
                    else {
                        threads[i][j] = new BruteForceDraw(color_choice, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, fast_julia_filters, fast_julia_image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, temp_xJuliaCenter, temp_yJuliaCenter);
                    }
                }
            }
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            main_panel.repaint();
            savePreferences();
            System.exit(-1);
        }

        startThreads(n);

    }

    public void setColorCycling() {

        if(!tools_menu.getColorCyling().isSelected()) {

            color_cycling = false;
            toolbar.getColorCyclingButton().setSelected(false);

            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    threads[i][j].terminateColorCycling();
                }
            }

            try {
                for(int i = 0; i < n; i++) {
                    for(int j = 0; j < n; j++) {
                        threads[i][j].join();
                    }
                }
            }
            catch(InterruptedException ex) {
            }

            color_cycling_location = threads[0][0].getColorCyclingLocation();

            if(color_choice == options_menu.getPalette().length - 1) {
                temp_color_cycling_location = color_cycling_location;
            }

            updateColorPalettesMenu();

            if(filters[ANTIALIASING]) {
                setOptions(false);

                progress.setValue(0);

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                if(filters[ANTIALIASING]) {
                    if(julia_map) {
                        createThreadsJuliaMap();
                    }
                    else {
                        createThreads(false);
                    }

                    calculation_time = System.currentTimeMillis();

                    if(julia_map) {
                        startThreads(julia_grid_first_dimension);
                    }
                    else {
                        startThreads(n);
                    }
                }
                else {
                    createThreadsPaletteAndFilter();

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);
                }

            }
            else {
                //last_used = null;
                setOptions(true);
            }
        }
        else {
            color_cycling = true;
            toolbar.getColorCyclingButton().setSelected(true);

            tools_menu.getDomainColoring().setEnabled(false);
            toolbar.getDomainColoringButton().setEnabled(false);

            setOptions(false);

            whole_image_done = false;

            createThreadsColorCycling();

            startThreads(n);
        }

    }

    private void createThreadsPaletteAndFilter() {

        ThreadDraw.resetThreadData(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, dem_color, temp_special_color, color_smoothing_method, color_cycling_location, smoothing, filters, filters_options_vals, filters_options_extra_vals, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
            }
        }
    }

    private void createThreadsColorCycling() {

        ThreadDraw.resetThreadData(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, smoothing, image, color_cycling_location, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, color_cycling_speed, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
            }
        }
    }

    private void createThreadsRotate3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, true, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order);
            }
        }

    }

    private void createThreadsPaletteAndFilter3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, false, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order);
            }
        }

    }

    public void shiftPalette() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = (String)JOptionPane.showInputDialog(scroll_pane, "The palette is shifted by " + color_cycling_location + ".\nEnter a number to shift the palette.", "Shift Palette", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if(temp < 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Palette shift value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            color_cycling_location = temp;

            if(color_choice == options_menu.getPalette().length - 1) {
                temp_color_cycling_location = color_cycling_location;
            }

            updateColorPalettesMenu();

            setOptions(false);

            progress.setValue(0);

            //scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            //scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(filters[ANTIALIASING] || (domain_coloring && use_palette_domain_coloring)) {
                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }
            }
            else {
                if(d3) {
                    createThreads(false);
                }
                else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setPlane(int temp) {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        int oldSelected = plane_type;
        plane_type = temp;

        if(plane_type == USER_PLANE) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(430, 190));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(runsOnWindows ? 45 : 40);
            field_formula.setText(user_plane);
            field_formula.addAncestorListener(new RequestFocusListener());

            JPanel formula_panel = new JPanel();

            formula_panel.add(new JLabel("z ="));
            formula_panel.add(field_formula);

            tabbedPane.addTab("Normal", formula_panel);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(user_plane_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(user_plane_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);
            JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond1.setText(user_plane_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond2.setText(user_plane_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond3.setText(user_plane_condition_formula[2]);

            JPanel formula_panel_cond11 = new JPanel();

            formula_panel_cond11.add(new JLabel("left > right, z ="));
            formula_panel_cond11.add(field_formula_cond1);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right, z ="));
            formula_panel_cond12.add(field_formula_cond2);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right, z ="));
            formula_panel_cond13.add(field_formula_cond3);

            JPanel panel_cond = new JPanel();

            panel_cond.setLayout(new FlowLayout());

            panel_cond.add(formula_panel_cond1);
            panel_cond.add(formula_panel_cond11);
            panel_cond.add(formula_panel_cond12);
            panel_cond.add(formula_panel_cond13);

            tabbedPane.addTab("Conditional", panel_cond);

            Object[] labels3 = createUserFormulaLabels("z, maxn, center, size, point");

            tabbedPane.setSelectedIndex(user_plane_algorithm);

            Object[] message3 = {
                labels3,
                " ",
                "Insert your plane transformation.",
                tabbedPane,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Plane", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.OK_OPTION) {
                try {

                    if(tabbedPane.getSelectedIndex() == 0) {
                        parser.parse(field_formula.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }
                    }
                    else {
                        parser.parse(field_condition.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }

                        parser.parse(field_condition2.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }

                        parser.parse(field_formula_cond2.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }

                        parser.parse(field_formula_cond3.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            plane_type = oldSelected;
                            return;
                        }
                    }

                    user_plane_algorithm = tabbedPane.getSelectedIndex();

                    if(user_plane_algorithm == 0) {
                        user_plane = field_formula.getText();
                    }
                    else {
                        user_plane_conditions[0] = field_condition.getText();
                        user_plane_conditions[1] = field_condition2.getText();
                        user_plane_condition_formula[0] = field_formula_cond1.getText();
                        user_plane_condition_formula[1] = field_formula_cond2.getText();
                        user_plane_condition_formula[2] = field_formula_cond3.getText();
                    }

                    defaultFractalSettings();

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }
        }
        else if(plane_type == TWIRL_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            final JTextField field_real = new JTextField();

            if(plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if(plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            Object[] message = {
                " ",
                "Set the twirl angle in degrees.",
                "Angle:", field_rotation,
                " ",
                "Set the twirl radius.",
                "Radius:", field_radius,
                " ",
                "Set the twirl center (User Point).",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Twirl", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, temp3, temp4;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Twirl radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;

            defaultFractalSettings();
        }
        else if(plane_type == SHEAR_PLANE) {
            JTextField field_scale_real = new JTextField();
            field_scale_real.setText("" + plane_transform_scales[0]);

            field_scale_real.addAncestorListener(new RequestFocusListener());

            JTextField field_scale_imaginary = new JTextField();
            field_scale_imaginary.setText("" + plane_transform_scales[1]);

            Object[] message = {
                " ",
                "Set the shear scaling.",
                "Scale Real:", field_scale_real,
                "Scale Imaginary:", field_scale_imaginary,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Shear", JOptionPane.OK_CANCEL_OPTION);

            double temp3, temp4;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_scale_real.getText());
                    temp4 = Double.parseDouble(field_scale_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            plane_transform_scales[0] = temp3 == 0.0 ? 0.0 : temp3;
            plane_transform_scales[1] = temp4 == 0.0 ? 0.0 : temp4;

            defaultFractalSettings();
        }
        else if(plane_type == KALEIDOSCOPE_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            JTextField field_rotation2 = new JTextField();
            field_rotation2.setText("" + plane_transform_angle2);

            final JTextField field_real = new JTextField();

            if(plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            JTextField field_sides = new JTextField();
            field_sides.setText("" + plane_transform_sides);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if(plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            Object[] message = {
                " ",
                "Set the kaleidoscope angles in degrees.",
                "Angle:", field_rotation,
                "Angle 2:", field_rotation2,
                " ",
                "Set the kaleidoscope radius.",
                "Radius:", field_radius,
                " ",
                "Set the kaleidoscope center (User Point).",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " ",
                "Set the kaleidoscope sides.",
                "Sides:", field_sides,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Kaleidoscope", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, temp3, temp4, temp5;
            int temp6;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp5 = Double.parseDouble(field_rotation2.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    temp6 = Integer.parseInt(field_sides.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp6 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope sides must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;
            plane_transform_angle2 = temp5;
            plane_transform_sides = temp6;

            defaultFractalSettings();
        }
        else if(plane_type == PINCH_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            final JTextField field_real = new JTextField();

            if(plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            JTextField field_amount = new JTextField();
            field_amount.setText("" + plane_transform_amount);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if(plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            Object[] message = {
                " ",
                "Set the pinch angle in degrees.",
                "Angle:", field_rotation,
                " ",
                "Set the pinch radius.",
                "Radius:", field_radius,
                " ",
                "Set the pinch amount.",
                "Amount:", field_amount,
                " ",
                "Set the pinch center (User Point).",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Pinch", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, temp3, temp4, temp5;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    temp5 = Double.parseDouble(field_amount.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Pinch radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;
            plane_transform_amount = temp5 == 0.0 ? 0.0 : temp5;

            defaultFractalSettings();
        }
        else if(plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE || plane_type == INFLECTION_PLANE || plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {

            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if(plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + plane_transform_center[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if(plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            String str = "Set the point about the fold (User Point).";
            String title = "Fold";
            if(plane_type == INFLECTION_PLANE) {
                str = "Set the point about the inflection (User Point).";
                title = "Inflection";
            }
            else if(plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {
                str = "Set the focal point (User Point).";
                title = "Bipolar/Inversed Bipolar";
            }

            Object[] message = {
                " ",
                str,
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, title, JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;

            defaultFractalSettings();
        }
        else if(plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);
            field_radius.addAncestorListener(new RequestFocusListener());

            Object[] message = {
                " ",
                "Set the radius of the fold.",
                "Radius:", field_radius,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Fold", JOptionPane.OK_CANCEL_OPTION);

            double temp3;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_radius.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            if(temp3 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Fold radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_radius = temp3;

            defaultFractalSettings();
        }
        else if(plane_type == CIRCLEINVERSION_PLANE) {

            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if(plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if(plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if(!current_center.isSelected()) {
                        if(plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        }
                        else {
                            field_real.setText("" + plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if(plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        }
                        else {
                            field_imaginary.setText("" + plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            Object[] message = {
                " ",
                "Set the circle inversion radius.",
                "Radius:", field_radius,
                " ",
                "Set the circle inversion center (User Point).",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Circle Inversion", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, temp4;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    plane_type = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Circle inversion radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_radius = temp4;

            defaultFractalSettings();
        }
        else {
            defaultFractalSettings();
        }
    }

    public void setBailoutTest(int temp) {

        int oldSelection = bailout_test_algorithm;
        bailout_test_algorithm = temp;

        if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
            if(backup_orbit != null && orbit) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + n_norm + " as the N-Norm.\nEnter the new N-Norm number.", "N-Norm", JOptionPane.QUESTION_MESSAGE);

            try {
                double temp3 = Double.parseDouble(ans);

                n_norm = temp3;

            }
            catch(Exception ex) {
                if(ans == null) {
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    bailout_test_algorithm = oldSelection;
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    bailout_test_algorithm = oldSelection;
                }
                return;
            }
        }
        else if(bailout_test_algorithm == BAILOUT_TEST_USER) {
            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(bailout_test_user_formula);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(bailout_test_user_formula2);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            String[] test_options = {"Escaped", "Not Escaped"};

            final JComboBox combo_box_greater = new JComboBox(test_options);
            final JComboBox combo_box_less = new JComboBox(test_options);

            combo_box_greater.setFocusable(false);
            combo_box_greater.setToolTipText("Sets the test option for the greater than case.");

            final JComboBox combo_box_equal = new JComboBox(test_options);
            combo_box_equal.setFocusable(false);
            combo_box_equal.setToolTipText("Sets the test option for the equal case.");

            combo_box_less.setFocusable(false);
            combo_box_less.setToolTipText("Sets the test option for the less than case.");

            if(bailout_test_comparison == 0) { // >
                combo_box_greater.setSelectedIndex(0);
                combo_box_equal.setSelectedIndex(1);
                combo_box_less.setSelectedIndex(1);
            }
            else if(bailout_test_comparison == 1) { // >=
                combo_box_greater.setSelectedIndex(0);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(1);
            }
            else if(bailout_test_comparison == 2) { // <
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(1);
                combo_box_less.setSelectedIndex(0);
            }
            else if(bailout_test_comparison == 3) { // <=
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(0);
            }
            else if(bailout_test_comparison == 4) { // !=
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(1);
            }
            else if(bailout_test_comparison == 5) { // ! !=
                combo_box_greater.setSelectedIndex(0);
                combo_box_equal.setSelectedIndex(1);
                combo_box_less.setSelectedIndex(0);
            }

            JPanel formula_panel_cond11 = new JPanel();

            formula_panel_cond11.add(new JLabel("left > right"));
            formula_panel_cond11.add(combo_box_greater);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right"));
            formula_panel_cond12.add(combo_box_less);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right"));
            formula_panel_cond13.add(combo_box_equal);

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, center, size, v1 - v20, point");

            Object[] message33 = {
                labels33,
                " ",
                "Set the bailout test.",
                formula_panel_cond1,
                formula_panel_cond11,
                formula_panel_cond12,
                formula_panel_cond13,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message33, "User Test", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.OK_OPTION) {
                try {
                    parser.parse(field_condition.getText());

                    if(parser.foundCbail()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail cannot be used in left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        bailout_tests[oldSelection].setSelected(true);
                        bailout_test_algorithm = oldSelection;
                        return;
                    }

                    parser.parse(field_condition2.getText());

                    if(parser.foundCbail()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        bailout_tests[oldSelection].setSelected(true);
                        bailout_test_algorithm = oldSelection;
                        return;
                    }

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    bailout_test_algorithm = oldSelection;
                    return;
                }
            }
            else {
                main_panel.repaint();
                bailout_tests[oldSelection].setSelected(true);
                bailout_test_algorithm = oldSelection;
                return;
            }

            if((combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0)
                    || (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1)) {

                JOptionPane.showMessageDialog(scroll_pane, "You cannot set all the outcomes to Escaped or Not Escaped.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                bailout_tests[oldSelection].setSelected(true);
                bailout_test_algorithm = oldSelection;
                return;
            }

            bailout_test_user_formula = field_condition.getText();
            bailout_test_user_formula2 = field_condition2.getText();

            if(combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1) { // >
                bailout_test_comparison = 0;
            }
            else if(combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // >=
                bailout_test_comparison = 1;
            }
            else if(combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // <
                bailout_test_comparison = 2;
            }
            else if(combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0) { // <=
                bailout_test_comparison = 3;
            }
            else if(combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // !=
                bailout_test_comparison = 4;
            }
            else if(combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // ! !=
                bailout_test_comparison = 5;
            }
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setOutColoringMode(int temp) {

        int oldSelected = out_coloring_algorithm;

        out_coloring_algorithm = temp;

        for(int k = 0; k < fractal_functions.length; k++) {
            if(k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                    && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                    && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                    && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                    && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                    && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                    && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                fractal_functions[k].setEnabled(true);
            }

            if(k == SIERPINSKI_GASKET && !julia && !julia_map && !perturbation && !init_val) {
                fractal_functions[k].setEnabled(true);
            }
        }

        if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de) {
            for(int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
            options_menu.getInitialValue().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
        }
        else if(out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || out_coloring_algorithm == ESCAPE_TIME_GRID || out_coloring_algorithm == BIOMORPH || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || out_coloring_algorithm == ITERATIONS_PLUS_RE || out_coloring_algorithm == ITERATIONS_PLUS_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);
        }
        else if(out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(495, 190));
            //tabbedPane.setPreferredSize(new Dimension(550, 210));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(runsOnWindows ? 50 : 45);//48
            field_formula.setText(outcoloring_formula);
            field_formula.addAncestorListener(new RequestFocusListener());

            JPanel formula_panel = new JPanel();
            formula_panel.setLayout(new FlowLayout());

            formula_panel.add(new JLabel("out ="));
            formula_panel.add(field_formula);

            tabbedPane.addTab("Normal", formula_panel);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(user_outcoloring_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(user_outcoloring_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond1.setText(user_outcoloring_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond2.setText(user_outcoloring_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond3.setText(user_outcoloring_condition_formula[2]);

            JPanel formula_panel_cond11 = new JPanel();

            formula_panel_cond11.add(new JLabel("left > right, out ="));
            formula_panel_cond11.add(field_formula_cond1);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right, out ="));
            formula_panel_cond12.add(field_formula_cond2);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right, out ="));
            formula_panel_cond13.add(field_formula_cond3);

            JPanel panel_cond = new JPanel();
            panel_cond.setLayout(new FlowLayout());

            panel_cond.add(formula_panel_cond1);
            panel_cond.add(formula_panel_cond11);
            panel_cond.add(formula_panel_cond12);
            panel_cond.add(formula_panel_cond13);

            tabbedPane.addTab("Conditional", panel_cond);

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, cbail, center, size, v1 - v20, point");

            tabbedPane.setSelectedIndex(user_out_coloring_algorithm);

            Object[] message3 = {
                labels33,
                " ",
                "Set the out coloring formula. Only the real component of the complex number will be used.",
                tabbedPane,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Out Coloring Method", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.OK_OPTION) {
                try {
                    if(tabbedPane.getSelectedIndex() == 0) {
                        parser.parse(field_formula.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                    }
                    else {
                        parser.parse(field_condition.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        parser.parse(field_condition2.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        parser.parse(field_formula_cond2.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        parser.parse(field_formula_cond3.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                    }

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    out_coloring_modes[oldSelected].setSelected(true);
                    out_coloring_algorithm = oldSelected;
                    return;
                }
            }
            else {
                main_panel.repaint();
                out_coloring_modes[oldSelected].setSelected(true);
                out_coloring_algorithm = oldSelected;
                return;
            }

            user_out_coloring_algorithm = tabbedPane.getSelectedIndex();

            if(user_out_coloring_algorithm == 0) {
                outcoloring_formula = field_formula.getText();
            }
            else {
                user_outcoloring_conditions[0] = field_condition.getText();
                user_outcoloring_conditions[1] = field_condition2.getText();
                user_outcoloring_condition_formula[0] = field_formula_cond1.getText();
                user_outcoloring_condition_formula[1] = field_formula_cond2.getText();
                user_outcoloring_condition_formula[2] = field_formula_cond3.getText();
            }

            if(!julia_map && !julia && !perturbation && !init_val && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
                rootFindingMethodsSetEnabled(true);
            }
        }
        else {
            /*if(function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
             out_coloring_modes[BIOMORPH].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
             out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
             out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
             out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
             out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
             out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
             out_coloring_modes[BANDED].setEnabled(true);

             }*/

            if(!julia_map && !julia && !perturbation && !init_val && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
                rootFindingMethodsSetEnabled(true);
            }
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setInColoringMode(int temp) {

        int oldSelected = in_coloring_algorithm;

        in_coloring_algorithm = temp;

        if(in_coloring_algorithm == MAXIMUM_ITERATIONS) {
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                options_menu.getPeriodicityChecking().setEnabled(true);
            }
        }
        else {
            if(in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                JTabbedPane tabbedPane = new JTabbedPane();
                //tabbedPane.setPreferredSize(new Dimension(550, 210));
                tabbedPane.setPreferredSize(new Dimension(495, 190));
                tabbedPane.setFocusable(false);

                JTextField field_formula = new JTextField(runsOnWindows ? 50 : 45);//48
                field_formula.setText(incoloring_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JPanel formula_panel = new JPanel();
                formula_panel.setLayout(new FlowLayout());

                formula_panel.add(new JLabel("in ="));
                formula_panel.add(field_formula);

                tabbedPane.addTab("Normal", formula_panel);

                JPanel formula_panel_cond1 = new JPanel();
                formula_panel_cond1.setLayout(new GridLayout(2, 2));

                JTextField field_condition = new JTextField(24);
                field_condition.setText(user_incoloring_conditions[0]);
                field_condition.addAncestorListener(new RequestFocusListener());

                JTextField field_condition2 = new JTextField(24);
                field_condition2.setText(user_incoloring_conditions[1]);

                formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(field_condition);
                formula_panel_cond1.add(field_condition2);

                JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond1.setText(user_incoloring_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond2.setText(user_incoloring_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond3.setText(user_incoloring_condition_formula[2]);

                JPanel formula_panel_cond11 = new JPanel();

                formula_panel_cond11.add(new JLabel("left > right, in ="));
                formula_panel_cond11.add(field_formula_cond1);

                JPanel formula_panel_cond12 = new JPanel();

                formula_panel_cond12.add(new JLabel("left < right, in ="));
                formula_panel_cond12.add(field_formula_cond2);

                JPanel formula_panel_cond13 = new JPanel();

                formula_panel_cond13.add(new JLabel("left = right, in ="));
                formula_panel_cond13.add(field_formula_cond3);

                JPanel panel_cond = new JPanel();

                panel_cond.setLayout(new FlowLayout());

                panel_cond.add(formula_panel_cond1);
                panel_cond.add(formula_panel_cond11);
                panel_cond.add(formula_panel_cond12);
                panel_cond.add(formula_panel_cond13);

                tabbedPane.addTab("Conditional", panel_cond);

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, maxn, center, size, v1 - v20, point");

                tabbedPane.setSelectedIndex(user_in_coloring_algorithm);

                Object[] message3 = {
                    labels3,
                    " ",
                    "Set the in coloring formula. Only the real component of the complex number will be used.",
                    tabbedPane,};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User In Coloring Method", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        if(tabbedPane.getSelectedIndex() == 0) {
                            parser.parse(field_formula.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left > right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left < right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left = right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                in_coloring_modes[oldSelected].setSelected(true);
                                in_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        in_coloring_modes[oldSelected].setSelected(true);
                        in_coloring_algorithm = oldSelected;
                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    in_coloring_modes[oldSelected].setSelected(true);
                    in_coloring_algorithm = oldSelected;
                    return;
                }

                user_in_coloring_algorithm = tabbedPane.getSelectedIndex();

                if(user_in_coloring_algorithm == 0) {
                    incoloring_formula = field_formula.getText();
                }
                else {
                    user_incoloring_conditions[0] = field_condition.getText();
                    user_incoloring_conditions[1] = field_condition2.getText();
                    user_incoloring_condition_formula[0] = field_formula_cond1.getText();
                    user_incoloring_condition_formula[1] = field_formula_cond2.getText();
                    user_incoloring_condition_formula[2] = field_formula_cond3.getText();
                }
            }
            options_menu.getPeriodicityChecking().setEnabled(false);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setSmoothing() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        final JCheckBox enable_smoothing = new JCheckBox("Smoothing");
        enable_smoothing.setSelected(smoothing);
        enable_smoothing.setFocusable(false);

        String[] escaping_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox escaping_alg_combo = new JComboBox(escaping_algorithm_str);
        escaping_alg_combo.setSelectedIndex(escaping_smooth_algorithm);
        escaping_alg_combo.setFocusable(false);
        escaping_alg_combo.setToolTipText("Sets the smooting algorithm for escaping functions.");

        String[] converging_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox converging_alg_combo = new JComboBox(converging_algorithm_str);
        converging_alg_combo.setSelectedIndex(converging_smooth_algorithm);
        converging_alg_combo.setFocusable(false);
        converging_alg_combo.setToolTipText("Sets the smooting algorithm for converging functions.");

        String[] color_interp_str = {"Linear", "Cosine", "Acceleration", "Deceleration", "Exponential", "Catmull-Rom", "Catmull-Rom 2", "Sigmoid"};

        final JComboBox combo_box_color_interp = new JComboBox(color_interp_str);
        combo_box_color_interp.setSelectedIndex(color_smoothing_method);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the smoothing color interpolation option.");

        Object[] message = {
            " ",
            enable_smoothing,
            " ",
            "Set the smoothing algorithm for escaping and converging functions.",
            "Escaping:", escaping_alg_combo,
            "Converging:", converging_alg_combo,
            " ",
            "Set the smoothing color interpolation method.",
            combo_box_color_interp,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Smoothing", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {

            smoothing = enable_smoothing.isSelected();
            escaping_smooth_algorithm = escaping_alg_combo.getSelectedIndex();
            converging_smooth_algorithm = converging_alg_combo.getSelectedIndex();
            color_smoothing_method = combo_box_color_interp.getSelectedIndex();
        }
        else {
            main_panel.repaint();
            return;
        }

        updateColorPalettesMenu();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setBumpMap() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        final JCheckBox enable_bump_map = new JCheckBox("Bump Mapping");
        enable_bump_map.setSelected(bump_map);
        enable_bump_map.setFocusable(false);

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int)(lightDirectionDegrees)));
        direction_of_light.setPreferredSize(new Dimension(300, 40));
        direction_of_light.setMajorTickSpacing(90);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int)(bumpMappingDepth)));
        depth.setPreferredSize(new Dimension(300, 40));
        depth.setMajorTickSpacing(25);
        depth.setMinorTickSpacing(1);
        depth.setToolTipText("Sets the depth of the effect.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        JSlider strength = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int)(bumpMappingStrength)));
        strength.setPreferredSize(new Dimension(300, 40));
        strength.setMajorTickSpacing(25);
        strength.setMinorTickSpacing(1);
        strength.setToolTipText("Sets the strength of the effect.");
        //color_blend.setPaintTicks(true);
        strength.setPaintLabels(true);
        //strength.setSnapToTicks(true);
        strength.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + bm_noise_reducing_factor);

        noise_factor_field.addAncestorListener(new RequestFocusListener());

        Object[] message = {
            " ",
            enable_bump_map,
            " ",
            "Set the direction of light in degrees.",
            "Direction of light:", direction_of_light,
            " ",
            "Set the depth.",
            "Depth:", depth,
            " ",
            "Set the strength.",
            "Strength:", strength,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Bump Mapping", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            double temp = 0;
            try {
                temp = Double.parseDouble(noise_factor_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }

            if(temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(greedy_algorithm && enable_bump_map.isSelected() && !julia_map && !d3) {
                JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            bump_map = enable_bump_map.isSelected();
            lightDirectionDegrees = direction_of_light.getValue();
            bumpMappingDepth = depth.getValue();
            bumpMappingStrength = strength.getValue();
            bm_noise_reducing_factor = temp;

            if(!smoothing && bump_map) {
                JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            whole_image_done = false;

            if(filters[ANTIALIASING]) {
                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }
            }
            else {
                if(d3) {
                    createThreads(false);
                }
                else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
        }
        else {
            main_panel.repaint();
            return;
        }

    }

    public void setRainbowPalette() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField rainbow_palette_factor_field = new JTextField();
        rainbow_palette_factor_field.setText("" + rainbow_palette_factor);

        rainbow_palette_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_rainbow_palette = new JCheckBox("Rainbow Palette");
        enable_rainbow_palette.setSelected(rainbow_palette);
        enable_rainbow_palette.setFocusable(false);

        JTextField rainbow_offset_field = new JTextField();
        rainbow_offset_field.setText("" + rainbow_offset);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(rp_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + rp_noise_reducing_factor);

        Object[] message = {
            " ",
            enable_rainbow_palette,
            " ",
            "Set the rainbow palette factor.",
            "Rainbow Palette factor:", rainbow_palette_factor_field,
            " ",
            "Set the coloring offset.",
            "Coloring Offset:", rainbow_offset_field,
            " ",
            "Set the color blending percentage.",
            "Color Blending percentage:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Rainbow Palette", JOptionPane.OK_CANCEL_OPTION);

        double temp;
        double temp2;
        int temp3;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(rainbow_palette_factor_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
                temp3 = Integer.parseInt(rainbow_offset_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }

        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The rainbow palette factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(temp3 < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(greedy_algorithm && enable_rainbow_palette.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        rainbow_palette = enable_rainbow_palette.isSelected();
        rainbow_palette_factor = temp;
        rp_noise_reducing_factor = temp2;
        rainbow_offset = temp3;
        rp_blending = color_blend_opt.getValue() / 100.0;

        if(!smoothing && rainbow_palette) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if(filters[ANTIALIASING]) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setFakeDistanceEstimation() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField fake_de_factor_field = new JTextField();
        fake_de_factor_field.setText("" + fake_de_factor);

        fake_de_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_fake_de = new JCheckBox("Fake Distance Estimation");
        enable_fake_de.setSelected(fake_de);
        enable_fake_de.setFocusable(false);

        final JCheckBox invert_fake_de = new JCheckBox("Invert Coloring");
        invert_fake_de.setSelected(inverse_fake_dem);
        invert_fake_de.setFocusable(false);

        Object[] message = {
            " ",
            enable_fake_de,
            " ",
            "Set the fake distance estimation factor.",
            "Fake Distance Estimation factor:", fake_de_factor_field,
            " ",
            invert_fake_de,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Fake Distance Estimation", JOptionPane.OK_CANCEL_OPTION);

        double temp;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(fake_de_factor_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The fake distance estimation factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(greedy_algorithm && enable_fake_de.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        fake_de = enable_fake_de.isSelected();
        fake_de_factor = temp;
        inverse_fake_dem = invert_fake_de.isSelected();

        if(!smoothing && fake_de) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if(filters[ANTIALIASING]) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setGreyScaleColoring() {

        final JCheckBox enable_greyscale_coloring = new JCheckBox("Greyscale Coloring");
        enable_greyscale_coloring.setSelected(greyscale_coloring);
        enable_greyscale_coloring.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + gs_noise_reducing_factor);

        Object[] message = {
            " ",
            enable_greyscale_coloring,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Greyscale Coloring", JOptionPane.OK_CANCEL_OPTION);

        double temp2;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp2 = Double.parseDouble(noise_factor_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(greedy_algorithm && enable_greyscale_coloring.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        greyscale_coloring = enable_greyscale_coloring.isSelected();
        of_noise_reducing_factor = temp2;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if(filters[ANTIALIASING]) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setEntropyColoring() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField entropy_factor_field = new JTextField();
        entropy_factor_field.setText("" + entropy_palette_factor);

        entropy_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_entropy_coloring = new JCheckBox("Entropy Coloring");
        enable_entropy_coloring.setSelected(entropy_coloring);
        enable_entropy_coloring.setFocusable(false);

        JTextField entropy_offset_field = new JTextField();
        entropy_offset_field.setText("" + entropy_offset);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(en_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + en_noise_reducing_factor);

        Object[] message = {
            " ",
            enable_entropy_coloring,
            " ",
            "Set the entropy coloring factor.",
            "Entropy Coloring factor:", entropy_factor_field,
            " ",
            "Set the coloring offset.",
            "Coloring Offset:", entropy_offset_field,
            " ",
            "Set the color blending percentage.",
            "Color Blending percentage:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Entropy Coloring", JOptionPane.OK_CANCEL_OPTION);

        double temp, temp2;
        int temp3;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(entropy_factor_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
                temp3 = Integer.parseInt(entropy_offset_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The entropy coloring factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(temp3 < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(greedy_algorithm && enable_entropy_coloring.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        entropy_coloring = enable_entropy_coloring.isSelected();
        entropy_palette_factor = temp;
        en_noise_reducing_factor = temp2;
        entropy_offset = temp3;
        en_blending = color_blend_opt.getValue() / 100.0;

        if(!smoothing && entropy_coloring) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if(filters[ANTIALIASING]) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setOffsetColoring() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField offset_field = new JTextField();
        offset_field.setText("" + post_process_offset);

        offset_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_offset_coloring = new JCheckBox("Offset Coloring");
        enable_offset_coloring.setSelected(offset_coloring);
        enable_offset_coloring.setFocusable(false);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int)(of_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + of_noise_reducing_factor);

        Object[] message = {
            " ",
            enable_offset_coloring,
            " ",
            "Set the coloring offset.",
            "Coloring Offset:", offset_field,
            " ",
            "Set the color blending percentage.",
            "Color Blending percentage:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Offset Coloring", JOptionPane.OK_CANCEL_OPTION);

        int temp;
        double temp2;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Integer.parseInt(offset_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(greedy_algorithm && enable_offset_coloring.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        offset_coloring = enable_offset_coloring.isSelected();
        post_process_offset = temp;
        of_noise_reducing_factor = temp2;
        of_blending = color_blend_opt.getValue() / 100.0;

        if(!smoothing && offset_coloring) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if(filters[ANTIALIASING]) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setExteriorDistanceEstimation() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField dem_factor = new JTextField();
        dem_factor.setText("" + exterior_de_factor);

        dem_factor.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_dem = new JCheckBox("Distance Estimation");
        enable_dem.setSelected(exterior_de);
        enable_dem.setFocusable(false);

        final JCheckBox invert_de = new JCheckBox("Invert Coloring");
        invert_de.setSelected(inverse_dem);
        invert_de.setFocusable(false);

        Object[] message = {
            " ",
            enable_dem,
            " ",
            "Set the distance estimation factor.",
            "Distance Estimation factor:", dem_factor,
            " ",
            invert_de,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Distance Estimation", JOptionPane.OK_CANCEL_OPTION);

        double temp;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(dem_factor.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        if(temp <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The distance estimation factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        exterior_de = enable_dem.isSelected();
        exterior_de_factor = temp;
        inverse_dem = invert_de.isSelected();

        if(exterior_de) {
            for(int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }

            options_menu.getBurningShipOpt().setEnabled(false);
            options_menu.getMandelGrassOpt().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);

        }
        else {
            for(int k = 0; k < fractal_functions.length; k++) {
                if(k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                        && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                        && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                        && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                        && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                        && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                        && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                    fractal_functions[k].setEnabled(true);
                }

                if(k == SIERPINSKI_GASKET && !julia && !julia_map && !perturbation && !init_val) {
                    fractal_functions[k].setEnabled(true);
                }
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !julia && !julia_map && !perturbation && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            options_menu.getBurningShipOpt().setEnabled(true);
            options_menu.getMandelGrassOpt().setEnabled(true);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setJuliaMap() {

        if(!tools_menu.getJuliaMap().isSelected()) {
            julia_map = false;
            toolbar.getJuliaMapButton().setSelected(false);

            options_menu.getJuliaMapOptions().setEnabled(false);

            setOptions(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }

            threads = new ThreadDraw[n][n];

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }
        else {
            main_panel.repaint();
            String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the first dimension, n,\nof the nxn 2D grid.", "Julia Map (2D Grid)", JOptionPane.QUESTION_MESSAGE);
            try {
                int temp = Integer.parseInt(ans);

                if(temp < 2) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    return;
                }
                else {
                    if(temp > 200) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be less than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.getJuliaMap().setSelected(false);
                        toolbar.getJuliaMapButton().setSelected(false);
                        return;
                    }
                }

                julia_grid_first_dimension = temp;
                threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

                toolbar.getJuliaMapButton().setSelected(true);

                options_menu.getJuliaMapOptions().setEnabled(true);

                setOptions(false);

                julia_map = true;

                rootFindingMethodsSetEnabled(false);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(false);

                options_menu.getPerturbation().setEnabled(false);
                options_menu.getInitialValue().setEnabled(false);

                options_menu.getGreedyAlgorithm().setEnabled(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                backup_orbit = null;

                whole_image_done = false;

                createThreadsJuliaMap();

                calculation_time = System.currentTimeMillis();

                startThreads(julia_grid_first_dimension);
            }
            catch(Exception ex) {
                if(ans == null) {
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    main_panel.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    main_panel.repaint();
                }
            }

        }

    }

    public void setJuliaMapOptions() {

        main_panel.repaint();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using a " + julia_grid_first_dimension + "x" + julia_grid_first_dimension + " grid.\nEnter the first dimension, n,\nof the nxn 2D grid.", "Julia Map (2D Grid)", JOptionPane.QUESTION_MESSAGE);
        try {
            int temp = Integer.parseInt(ans);

            if(temp < 2) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 200) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be less than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            julia_grid_first_dimension = temp;
            threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreadsJuliaMap();

            calculation_time = System.currentTimeMillis();

            startThreads(julia_grid_first_dimension);
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void drawZoomWindow(Graphics2D brush) {

        int x1, y1;
        try {
            x1 = (int)main_panel.getMousePosition().getX();
            y1 = (int)main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        if(x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
            return;
        }

        int new_size = (int)(image_size / zoom_factor);

        if(zoom_window_style == 0) {
            BasicStroke old_stroke = (BasicStroke)brush.getStroke();

            float dash1[] = {5.0f};
            BasicStroke dashed
                    = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
            brush.setStroke(dashed);

            brush.setColor(zoom_window_color);
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            brush.drawRect(x1 - new_size / 2, y1 - new_size / 2, new_size, new_size);
            brush.setFont(new Font("Arial", Font.BOLD, 16));
            brush.drawString("x" + String.format("%4.2f", zoom_factor), x1 - new_size / 2 + 5, y1 - new_size / 2 - 5);

            brush.setStroke(old_stroke);
        }
        else {
            brush.setColor(zoom_window_color);
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            brush.drawRect(x1 - new_size / 2, y1 - new_size / 2, new_size, new_size);
            brush.setFont(new Font("Arial", Font.BOLD, 16));
            brush.drawString("x" + String.format("%4.2f", zoom_factor), x1 - new_size / 2 + 5, y1 - new_size / 2 - 5);
        }

    }

    public void drawBoundaries(Graphics2D brush, boolean mode) {

        brush.setColor(boundaries_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        if(old_polar_projection) {
            double start;
            double center = Math.log(old_size);
            double muly = (2 * circle_period * Math.PI) / image_size;

            double mulx = muly * old_height_ratio;

            start = center - mulx * image_size * 0.5;

            for(int i = 1; i <= boundaries_num; i++) {

                double num;
                if(boundaries_spacing_method == 0) {
                    num = Math.pow(2, i - 1);
                }
                else {
                    num = i;
                }

                if(num == bailout && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                    continue;
                }

                int radius_x = (int)((Math.log(num) - start) / mulx + 0.5);
                brush.drawLine(radius_x, 0, radius_x, image_size);

                if(mode) {
                    brush.drawString("" + num, radius_x + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                }
                else {
                    brush.drawString("" + num, radius_x + 6, 12);
                }
            }

            if(bailout < 100 && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                int radius_x = (int)((Math.log(bailout) - start) / mulx + 0.5);

                BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                float dash1[] = {5.0f};
                BasicStroke dashed
                        = new BasicStroke(1.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                5.0f, dash1, 0.0f);
                brush.setStroke(dashed);

                brush.drawLine(radius_x, 0, radius_x, image_size);

                if(mode) {
                    brush.drawString("" + bailout, radius_x + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                }
                else {
                    brush.drawString("" + bailout, radius_x + 6, 12);
                }
                brush.setStroke(old_stroke);
            }

        }
        else {
            double size_2_x = old_size * 0.5;
            double size_2_y = (old_size * old_height_ratio) * 0.5;
            double temp_xcenter_size = old_xCenter - size_2_x;
            double temp_ycenter_size = old_yCenter + size_2_y;
            double temp_size_image_size_x = old_size / image_size;
            double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

            int x0 = (int)((0 - temp_xcenter_size) / temp_size_image_size_x + 0.5);
            int y0 = (int)((-0 + temp_ycenter_size) / temp_size_image_size_y + 0.5);

            brush.drawLine(x0, 0, x0, image_size);
            brush.drawLine(0, y0, image_size, y0);

            if(mode) {
                brush.drawString("0.0", x0 + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                brush.drawString("0.0", 6 + scroll_pane.getHorizontalScrollBar().getValue(), y0 + 12);
            }
            else {
                brush.drawString("0.0", x0 + 6, 12);
                brush.drawString("0.0", 6, y0 + 12);
            }

            if(boundaries_type == 0) {
                for(int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if(boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1);
                    }
                    else {
                        num = i;
                    }

                    if(num == bailout && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));
                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, (int)(x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int)(y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);
                }

                if(bailout < 100 && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                    int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                    float dash1[] = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + bailout, (int)(x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int)(y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);

                    brush.setStroke(old_stroke);
                }

            }
            else if(boundaries_type == 1) {
                for(int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if(boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1);
                    }
                    else {
                        num = i;
                    }

                    if(num == bailout && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, x0 - radius_x - 10, y0 - radius_y - 5);
                }

                if(bailout < 100 && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {

                    int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                    float dash1[] = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + bailout, x0 - radius_x - 10, y0 - radius_y - 5);

                    brush.setStroke(old_stroke);

                }
            }
            else {
                for(int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if(boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1);
                    }
                    else {
                        num = i;
                    }

                    if(num == bailout && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    brush.drawLine(x0 - radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 + radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 - radius_x, y0, x0, y0 + radius_y);

                    brush.drawString("" + num, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);
                }

                if(bailout < 100 && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {

                    int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int)((-bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                    float dash1[] = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawLine(x0 - radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 + radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 - radius_x, y0, x0, y0 + radius_y);

                    brush.drawString("" + bailout, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);

                    brush.setStroke(old_stroke);

                }
            }
        }

    }

    public void drawGrid(Graphics2D brush, boolean mode) {

        double size_2_x = old_size * 0.5;
        double size_2_y = (old_size * old_height_ratio) * 0.5;
        double temp_xcenter_size = old_xCenter - size_2_x;
        double temp_ycenter_size = old_yCenter + size_2_y;
        double temp_size_image_size_x = old_size / image_size;
        double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

        brush.setColor(grid_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        double temp, temp2;
        for(int i = 1; i < grid_tiles; i++) {
            brush.drawLine(i * image_size / grid_tiles, 0, i * image_size / grid_tiles, image_size);
            brush.drawLine(0, i * image_size / grid_tiles, image_size, i * image_size / grid_tiles);

            temp = temp_xcenter_size + temp_size_image_size_x * i * image_size / grid_tiles;
            temp2 = temp_ycenter_size - temp_size_image_size_y * i * image_size / grid_tiles;

            if(temp >= 1e-8) {
                temp = Math.floor(1000000000 * temp + 0.5) / 1000000000;
            }
            else if(Math.abs(temp) < 1e-15) {
                temp = 0;
            }

            if(temp2 >= 1e-8) {
                temp2 = Math.floor(1000000000 * temp2 + 0.5) / 1000000000;
            }
            else if(Math.abs(temp2) < 1e-15) {
                temp2 = 0;
            }

            if(mode) {
                brush.drawString("" + temp, i * image_size / grid_tiles + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                brush.drawString("" + temp2, 6 + scroll_pane.getHorizontalScrollBar().getValue(), i * image_size / grid_tiles + 12);
            }
            else {
                brush.drawString("" + temp, i * image_size / grid_tiles + 6, 12);
                brush.drawString("" + temp2, 6, i * image_size / grid_tiles + 12);
            }
        }

    }

    public long getCalculationTime() {

        return calculation_time;

    }

    public void setFirstPaint() {

        first_paint = true;

    }

    public boolean getFirstPaint() {

        return first_paint;

    }

    public boolean getWholeImageDone() {

        return whole_image_done;

    }

    public boolean getGrid() {

        return old_grid;

    }

    public boolean getBoundaries() {

        return old_boundaries;

    }

    public boolean getZoomWindow() {

        return zoom_window;

    }

    public BufferedImage getImage() {

        return image;

    }

    public BufferedImage getLastUsed() {

        return last_used;

    }

    public boolean getColorCycling() {

        return color_cycling;

    }

    private void customPaletteEditor(final int number) {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new CustomPaletteEditorFrame(ptr, options_menu.getPalette(), smoothing, greedy_algorithm, d3, julia_map, number, color_choice, custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg);

    }

    public boolean getJuliaMap() {

        return julia_map;

    }

    public void goTo() {

        if(julia) {
            goToJulia();
        }
        else {
            goToFractal();
        }
    }

    public void exit() {

        if(!color_cycling) {
            main_panel.repaint();
        }
        int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
        if(ans == JOptionPane.YES_OPTION) {
            ptr.saveSettings();
            ptr.savePreferences();
            System.exit(0);
        }
        else {
            if(ans == JOptionPane.NO_OPTION) {
                ptr.savePreferences();
                System.exit(0);
            }
        }
    }

    private void createThreadsJuliaMap() {

        ThreadDraw.resetThreadData(julia_grid_first_dimension * julia_grid_first_dimension);

        int n = julia_grid_first_dimension;

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        try {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    threads[i][j] = new BruteForceDraw(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, color_smoothing_method, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, color_choice == options_menu.getPalette().length - 1, custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
                }
            }
        }
        catch(ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            main_panel.repaint();
            savePreferences();
            System.exit(-1);
        }

    }

    public void increaseIterations() {

        if(max_iterations >= 80000) {
            return;
        }
        max_iterations++;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void decreaseIterations() {

        if(max_iterations > 1) {
            max_iterations--;
        }
        else {
            return;
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void shiftPaletteForward() {

        color_cycling_location++;

        color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

        if(color_choice == options_menu.getPalette().length - 1) {
            temp_color_cycling_location = color_cycling_location;
        }

        updateColorPalettesMenu();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(filters[ANTIALIASING] || (domain_coloring && use_palette_domain_coloring)) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void shiftPaletteBackward() {

        if(color_cycling_location > 0) {
            color_cycling_location--;

            if(color_choice == options_menu.getPalette().length - 1) {
                temp_color_cycling_location = color_cycling_location;
            }
        }
        else {
            return;
        }

        updateColorPalettesMenu();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        if(filters[ANTIALIASING] || (domain_coloring && use_palette_domain_coloring)) {
            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                createThreads(false);
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void setPerturbation() {

        if(!options_menu.getPerturbation().isSelected()) {
            perturbation = false;

            if(function == MANDELBROT && !burning_ship && !init_val && !mandel_grass && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                options_menu.getDistanceEstimation().setEnabled(true);
            }

            if(function == MANDELBROT && !init_val) {
                out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }
        else {
            if(backup_orbit != null && orbit) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            panel1.setLayout(new FlowLayout());
            panel2.setLayout(new FlowLayout());

            JTextField field_real = new JTextField(45);
            field_real.setText("" + perturbation_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(45);
            field_imaginary.setText("" + perturbation_vals[1]);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(runsOnWindows ? 550 : 630, 595));
            tabbedPane.setFocusable(false);

            JPanel panel11 = new JPanel();

            panel11.setLayout(new GridLayout(8, 1));

            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Set the real and imaginary part of the perturbation."));
            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Real:"));
            panel11.add(field_real);
            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Imaginary:"));
            panel11.add(field_imaginary);

            panel1.add(panel11);

            JPanel panel21 = new JPanel();
            panel21.setLayout(new FlowLayout());
            JPanel panel22 = new JPanel();
            panel22.setLayout(new FlowLayout());

            JLabel html_label = createUserFormulaHtmlLabels("c, maxn, center, size, point", false);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(45);
            field_formula.setText("" + perturbation_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(runsOnWindows ? 500 : 580, 190));
            tabbedPane2.setFocusable(false);

            panel22.add(new JLabel("z(0) = z(0) +"));
            panel22.add(field_formula);

            tabbedPane2.addTab("Normal", panel22);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(user_perturbation_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(user_perturbation_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(user_perturbation_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(user_perturbation_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
            field_formula_cond3.setText(user_perturbation_condition_formula[2]);

            JPanel formula_panel_cond11 = new JPanel();

            formula_panel_cond11.add(new JLabel("left > right, z(0) = z(0) +"));
            formula_panel_cond11.add(field_formula_cond1);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right, z(0) = z(0) +"));
            formula_panel_cond12.add(field_formula_cond2);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right, z(0) = z(0) +"));
            formula_panel_cond13.add(field_formula_cond3);

            JPanel panel_cond = new JPanel();

            panel_cond.setLayout(new FlowLayout());

            panel_cond.add(formula_panel_cond1);
            panel_cond.add(formula_panel_cond11);
            panel_cond.add(formula_panel_cond12);
            panel_cond.add(formula_panel_cond13);

            tabbedPane2.addTab("Conditional", panel_cond);

            JButton info_user = new JButton("Help");
            info_user.setToolTipText("Shows the details of the user formulas.");
            info_user.setFocusable(false);
            info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
            info_user.setPreferredSize(new Dimension(105, 23));

            info_user.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    showUserFormulaHelp();

                }

            });

            JButton code_editor = new JButton("Edit User Code");
            code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
            code_editor.setFocusable(false);
            code_editor.setIcon(getIcon("/fractalzoomer/icons/code_editor2.png"));
            code_editor.setPreferredSize(new Dimension(160, 23));

            code_editor.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    codeEditor();

                }

            });

            JButton compile_code = new JButton("Compile User Code");
            compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
            compile_code.setFocusable(false);
            compile_code.setIcon(getIcon("/fractalzoomer/icons/compile2.png"));
            compile_code.setPreferredSize(new Dimension(180, 23));

            compile_code.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    compileCode(true);

                }

            });

            JPanel info_panel = new JPanel();
            info_panel.setLayout(new FlowLayout());
            info_panel.add(info_user);
            info_panel.add(code_editor);
            info_panel.add(compile_code);
            info_panel.setPreferredSize(new Dimension(500, 28));

            panel2.add(info_panel);
            panel2.add(panel21);
            panel2.add(tabbedPane2);

            tabbedPane.addTab("Static value", panel1);
            tabbedPane.addTab("Variable value", panel2);

            if(variable_perturbation) {
                tabbedPane.setSelectedIndex(1);
                tabbedPane2.setSelectedIndex(user_perturbation_algorithm);
            }
            else {
                tabbedPane.setSelectedIndex(0);
            }

            Object[] message = {
                tabbedPane
            };

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Perturbation", JOptionPane.OK_CANCEL_OPTION);

            double temp = 0, temp2 = 0;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    if(tabbedPane.getSelectedIndex() == 0) {
                        temp = Double.parseDouble(field_real.getText());
                        temp2 = Double.parseDouble(field_imaginary.getText());
                    }
                    else {
                        if(tabbedPane2.getSelectedIndex() == 0) {
                            parser.parse(field_formula.getText());
                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getPerturbation().setSelected(false);
                    main_panel.repaint();
                    return;
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getPerturbation().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                options_menu.getPerturbation().setSelected(false);
                return;
            }

            if(tabbedPane.getSelectedIndex() == 0) {
                perturbation_vals[0] = temp == 0.0 ? 0.0 : temp;
                perturbation_vals[1] = temp2 == 0.0 ? 0.0 : temp2;
                variable_perturbation = false;
            }
            else {
                variable_perturbation = true;

                user_perturbation_algorithm = tabbedPane2.getSelectedIndex();

                if(user_perturbation_algorithm == 0) {
                    perturbation_user_formula = field_formula.getText();
                }
                else {
                    user_perturbation_conditions[0] = field_condition.getText();
                    user_perturbation_conditions[1] = field_condition2.getText();
                    user_perturbation_condition_formula[0] = field_formula_cond1.getText();
                    user_perturbation_condition_formula[1] = field_formula_cond2.getText();
                    user_perturbation_condition_formula[2] = field_formula_cond3.getText();
                }
            }

            perturbation = true;
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            options_menu.getDistanceEstimation().setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        }

        defaultFractalSettings();

    }

    public void setInitialValue() {

        if(!options_menu.getInitialValue().isSelected()) {
            init_val = false;

            if(function == MANDELBROT && !burning_ship && !mandel_grass && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                options_menu.getDistanceEstimation().setEnabled(true);
            }

            if(function == MANDELBROT && !perturbation) {
                out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }
        else {
            if(backup_orbit != null && orbit) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            panel1.setLayout(new FlowLayout());
            panel2.setLayout(new FlowLayout());

            JTextField field_real = new JTextField(45);
            field_real.setText("" + initial_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(45);
            field_imaginary.setText("" + initial_vals[1]);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(runsOnWindows ? 550 : 630, 595));
            tabbedPane.setFocusable(false);

            JPanel panel11 = new JPanel();

            panel11.setLayout(new GridLayout(8, 1));

            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Set the real and imaginary part of the initial value."));
            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Real:"));
            panel11.add(field_real);
            panel11.add(new JLabel(""));
            panel11.add(new JLabel("Imaginary:"));
            panel11.add(field_imaginary);

            panel1.add(panel11);

            JPanel panel21 = new JPanel();
            panel21.setLayout(new FlowLayout());
            JPanel panel22 = new JPanel();
            panel22.setLayout(new FlowLayout());

            JLabel html_label = createUserFormulaHtmlLabels("c, maxn, center, size, point", true);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(45);
            field_formula.setText("" + initial_value_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(runsOnWindows ? 500 : 580, 190));
            tabbedPane2.setFocusable(false);

            panel22.add(new JLabel("z(0) ="));
            panel22.add(field_formula);

            tabbedPane2.addTab("Normal", panel22);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(user_initial_value_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(user_initial_value_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(user_initial_value_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(user_initial_value_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
            field_formula_cond3.setText(user_initial_value_condition_formula[2]);

            JPanel formula_panel_cond11 = new JPanel();

            formula_panel_cond11.add(new JLabel("left > right, z(0) ="));
            formula_panel_cond11.add(field_formula_cond1);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right, z(0) ="));
            formula_panel_cond12.add(field_formula_cond2);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right, z(0) ="));
            formula_panel_cond13.add(field_formula_cond3);

            JPanel panel_cond = new JPanel();

            panel_cond.setLayout(new FlowLayout());

            panel_cond.add(formula_panel_cond1);
            panel_cond.add(formula_panel_cond11);
            panel_cond.add(formula_panel_cond12);
            panel_cond.add(formula_panel_cond13);

            tabbedPane2.addTab("Conditional", panel_cond);

            JButton info_user = new JButton("Help");
            info_user.setToolTipText("Shows the details of the user formulas.");
            info_user.setFocusable(false);
            info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
            info_user.setPreferredSize(new Dimension(105, 23));

            info_user.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    showUserFormulaHelp();

                }

            });

            JButton code_editor = new JButton("Edit User Code");
            code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
            code_editor.setFocusable(false);
            code_editor.setIcon(getIcon("/fractalzoomer/icons/code_editor2.png"));
            code_editor.setPreferredSize(new Dimension(160, 23));

            code_editor.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    codeEditor();

                }

            });

            JButton compile_code = new JButton("Compile User Code");
            compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
            compile_code.setFocusable(false);
            compile_code.setIcon(getIcon("/fractalzoomer/icons/compile2.png"));
            compile_code.setPreferredSize(new Dimension(180, 23));

            compile_code.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    compileCode(true);

                }

            });

            JPanel info_panel = new JPanel();
            info_panel.setLayout(new FlowLayout());
            info_panel.add(info_user);
            info_panel.add(code_editor);
            info_panel.add(compile_code);
            info_panel.setPreferredSize(new Dimension(500, 28));

            panel2.add(info_panel);
            panel2.add(panel21);
            panel2.add(tabbedPane2);

            tabbedPane.addTab("Static value", panel1);
            tabbedPane.addTab("Variable value", panel2);

            if(variable_init_value) {
                tabbedPane.setSelectedIndex(1);
                tabbedPane2.setSelectedIndex(user_initial_value_algorithm);
            }
            else {
                tabbedPane.setSelectedIndex(0);
            }

            Object[] message = {
                tabbedPane
            };

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Initial Value", JOptionPane.OK_CANCEL_OPTION);

            double temp = 0, temp2 = 0;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    if(tabbedPane.getSelectedIndex() == 0) {
                        temp = Double.parseDouble(field_real.getText());
                        temp2 = Double.parseDouble(field_imaginary.getText());
                    }
                    else {
                        if(tabbedPane2.getSelectedIndex() == 0) {
                            parser.parse(field_formula.getText());
                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundAnyVar()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, v1 - v20 cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getInitialValue().setSelected(false);
                    main_panel.repaint();
                    return;
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getInitialValue().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                options_menu.getInitialValue().setSelected(false);
                return;
            }

            if(tabbedPane.getSelectedIndex() == 0) {
                initial_vals[0] = temp == 0.0 ? 0.0 : temp;
                initial_vals[1] = temp2 == 0.0 ? 0.0 : temp2;
                variable_init_value = false;
            }
            else {
                variable_init_value = true;
                user_initial_value_algorithm = tabbedPane2.getSelectedIndex();

                if(user_initial_value_algorithm == 0) {
                    initial_value_user_formula = field_formula.getText();
                }
                else {
                    user_initial_value_conditions[0] = field_condition.getText();
                    user_initial_value_conditions[1] = field_condition2.getText();
                    user_initial_value_condition_formula[0] = field_formula_cond1.getText();
                    user_initial_value_condition_formula[1] = field_formula_cond2.getText();
                    user_initial_value_condition_formula[2] = field_formula_cond3.getText();
                }
            }

            init_val = true;
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            options_menu.getDistanceEstimation().setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        }

        defaultFractalSettings();

    }

    public void setColorCyclingButton() {

        if(!toolbar.getColorCyclingButton().isSelected()) {
            toolbar.getColorCyclingButton().setSelected(true);
            tools_menu.getColorCyling().setSelected(true);
        }
        else {
            toolbar.getColorCyclingButton().setSelected(false);
            tools_menu.getColorCyling().setSelected(false);
        }

        setColorCycling();

    }

    public void setDomainColoringButton() {

        if(!toolbar.getDomainColoringButton().isSelected()) {
            toolbar.getDomainColoringButton().setSelected(true);
            tools_menu.getDomainColoring().setSelected(true);
        }
        else {
            toolbar.getDomainColoringButton().setSelected(false);
            tools_menu.getDomainColoring().setSelected(false);
        }

        setDomainColoring();

    }

    public void setPolarProjectionButton() {

        if(!toolbar.getPolarProjectionButton().isSelected()) {
            toolbar.getPolarProjectionButton().setSelected(true);
            tools_menu.getPolarProjection().setSelected(true);
        }
        else {
            toolbar.getPolarProjectionButton().setSelected(false);
            tools_menu.getPolarProjection().setSelected(false);
        }

        setPolarProjection();

    }

    public void set3DButton() {

        if(!toolbar.get3DButton().isSelected()) {
            toolbar.get3DButton().setSelected(true);
            tools_menu.get3D().setSelected(true);
        }
        else {
            toolbar.get3DButton().setSelected(false);
            tools_menu.get3D().setSelected(false);
        }

        set3DOption();

    }

    public void setJuliaMapButton() {

        if(!toolbar.getJuliaMapButton().isSelected()) {
            toolbar.getJuliaMapButton().setSelected(true);
            tools_menu.getJuliaMap().setSelected(true);
        }
        else {
            toolbar.getJuliaMapButton().setSelected(false);
            tools_menu.getJuliaMap().setSelected(false);
        }

        setJuliaMap();

    }

    public void setJuliaButton() {

        if(!toolbar.getJuliaButton().isSelected()) {
            toolbar.getJuliaButton().setSelected(true);
            tools_menu.getJulia().setSelected(true);
        }
        else {
            toolbar.getJuliaButton().setSelected(false);
            tools_menu.getJulia().setSelected(false);
        }

        setJuliaOption();

    }

    public void setOrbitButton() {

        if(!toolbar.getOrbitButton().isSelected()) {
            toolbar.getOrbitButton().setSelected(true);
            tools_menu.getOrbit().setSelected(true);
        }
        else {
            toolbar.getOrbitButton().setSelected(false);
            tools_menu.getOrbit().setSelected(false);
        }

        setOrbitOption();

    }

    public void openCustomPaletteEditor() {

        customPaletteEditor(options_menu.getPalette().length - 1);

    }

    public void showCHMHelpFile() {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/help/Fractal_Zoomer_Help.chm").openStream();
            File TempFile = File.createTempFile("temp", ".chm");
            FileOutputStream out = new FileOutputStream(TempFile);
            byte[] temp = new byte[32768];
            int rc;

            while((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();
            TempFile.deleteOnExit();
            Desktop.getDesktop().open(TempFile);
        }
        catch(Exception ex) {
        }

    }

    private void exportCodeFiles(boolean libExists) {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/parser/code/Complex.javacode").openStream();
            File complexFile = new File("Complex.java");

            FileOutputStream out = new FileOutputStream(complexFile);
            byte[] temp = new byte[32768];
            int rc;

            while((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();

            src = getClass().getResource("/fractalzoomer/parser/code/UserDefinedFunctions.javacode").openStream();
            File UserDefinedFunctionsFile = new File("UserDefinedFunctions.java");

            if(!UserDefinedFunctionsFile.exists()) {
                out = new FileOutputStream(UserDefinedFunctionsFile);
                temp = new byte[32768];

                while((rc = src.read(temp)) > 0) {
                    out.write(temp, 0, rc);
                }

                src.close();
                out.close();
            }

            if(libExists) {
                compileCode(false);
            }
        }
        catch(Exception ex) {
        }

    }

    public void randomPalette() {

        CustomPaletteEditorFrame.randomPalette(custom_palette, CustomPaletteEditorFrame.getRandomPaletteAlgorithm(), CustomPaletteEditorFrame.getEqualHues(), color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

        setPalette(options_menu.getPalette().length - 1);

    }

    public void setToolbar() {

        if(!options_menu.getToolbar().isSelected()) {
            toolbar.setVisible(false);
        }
        else {
            toolbar.setVisible(true);
        }

    }

    public void setStatubar() {

        if(!options_menu.getStatusbar().isSelected()) {
            statusbar.setVisible(false);
        }
        else {
            statusbar.setVisible(true);
        }

    }

    public void setInfobar() {

        if(!options_menu.getInfobar().isSelected()) {
            infobar.setVisible(false);
        }
        else {
            infobar.setVisible(true);
        }

    }

    public int getNumberOfThreads() {

        return n * n;

    }

    public int getJuliaMapSlices() {

        return julia_grid_first_dimension * julia_grid_first_dimension;

    }

    public void filtersOptions() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new FiltersOptionsFrame(ptr, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_opt, filters_order, filters);

    }

    public void setPolarProjection() {

        if(!tools_menu.getPolarProjection().isSelected()) {
            polar_projection = false;
            toolbar.getPolarProjectionButton().setSelected(false);

            options_menu.getPolarProjectionOptions().setEnabled(false);

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                fiX = 0.64;
                fiY = 0.82;
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            JTextField field_real = new JTextField();

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

            if(p.x == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + p.x);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            if(p.y == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + p.y);
            }

            JTextField field_size = new JTextField();
            field_size.setText("" + size);

            JTextField field_circle_period = new JTextField();
            field_circle_period.setText("" + circle_period);

            Object[] message3 = {
                " ",
                "Set the real and imaginary part of the polar projection center",
                "and the size.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                "Size:", field_size,
                " ",
                "Set the circle periods number.",
                "Circle Periods:",
                field_circle_period,
                ""};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Polar Projection", JOptionPane.OK_CANCEL_OPTION);

            double tempReal, tempImaginary, tempSize, temp_circle_period;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText()) - rotation_center[0];
                    tempImaginary = Double.parseDouble(field_imaginary.getText()) - rotation_center[1];
                    tempSize = Double.parseDouble(field_size.getText());
                    temp_circle_period = Double.parseDouble(field_circle_period.getText());

                    if(temp_circle_period <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The circle periods must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.getPolarProjection().setSelected(false);
                        toolbar.getPolarProjectionButton().setSelected(false);
                        return;
                    }

                    size = tempSize;
                    xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
                    yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];
                    circle_period = temp_circle_period;

                    polar_projection = true;
                    toolbar.getPolarProjectionButton().setSelected(true);

                    tools_menu.getGrid().setEnabled(false);
                    tools_menu.getZoomWindow().setEnabled(false);

                    options_menu.getPolarProjectionOptions().setEnabled(true);

                    setOptions(false);

                    progress.setValue(0);

                    scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                    scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                    resetImage();

                    if(d3) {
                        fiX = 0.64;
                        fiY = 0.82;
                        Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                    }

                    backup_orbit = null;

                    whole_image_done = false;

                    if(julia_map) {
                        createThreadsJuliaMap();
                    }
                    else {
                        createThreads(false);
                    }

                    calculation_time = System.currentTimeMillis();

                    if(julia_map) {
                        startThreads(julia_grid_first_dimension);
                    }
                    else {
                        startThreads(n);
                    }
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getPolarProjection().setSelected(false);
                    toolbar.getPolarProjectionButton().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                tools_menu.getPolarProjection().setSelected(false);
                toolbar.getPolarProjectionButton().setSelected(false);
                main_panel.repaint();
                return;
            }
        }
    }

    public void setPolarProjectionOptions() {
        JTextField field_real = new JTextField();

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

        if(p.x == 0) {
            field_real.setText("" + 0.0);
        }
        else {
            field_real.setText("" + p.x);
        }

        field_real.addAncestorListener(new RequestFocusListener());

        JTextField field_imaginary = new JTextField();

        if(p.y == 0) {
            field_imaginary.setText("" + 0.0);
        }
        else {
            field_imaginary.setText("" + p.y);
        }

        JTextField field_size = new JTextField();
        field_size.setText("" + size);

        JTextField field_circle_period = new JTextField();
        field_circle_period.setText("" + circle_period);

        Object[] message3 = {
            " ",
            "Set the real and imaginary part of the polar projection center",
            "and the size.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            "Size:", field_size,
            " ",
            "Set the circle periods number.",
            "Circle Periods:",
            field_circle_period,
            ""};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Polar Projection", JOptionPane.OK_CANCEL_OPTION);

        double tempReal, tempImaginary, tempSize, temp_circle_period;

        if(res == JOptionPane.OK_OPTION) {
            try {
                tempReal = Double.parseDouble(field_real.getText()) - rotation_center[0];
                tempImaginary = Double.parseDouble(field_imaginary.getText()) - rotation_center[1];
                tempSize = Double.parseDouble(field_size.getText());
                temp_circle_period = Double.parseDouble(field_circle_period.getText());

                if(temp_circle_period <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The circle periods must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                size = tempSize;
                xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
                yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];
                circle_period = temp_circle_period;

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                backup_orbit = null;

                whole_image_done = false;

                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }
    }

    public void setDomainColoring() {

        if(!tools_menu.getDomainColoring().isSelected()) {
            domain_coloring = false;

            toolbar.getDomainColoringButton().setSelected(false);
            options_menu.getDomainColoringOptions().setEnabled(false);

            if(!use_palette_domain_coloring) {
                infobar.getPalettePreview().setVisible(true);
                infobar.getPalettePreviewLabel().setVisible(true);
            }

            infobar.getMaxIterationsColorPreview().setVisible(true);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(true);

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                fiX = 0.64;
                fiY = 0.82;
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }
        else {
            JTextField field_iterations = new JTextField();

            field_iterations.setText("" + max_iterations);

            field_iterations.addAncestorListener(new RequestFocusListener());

            String[] algs = {"Black Grid, White Circles log2", "White Grid, Black Circles log2", "Black Grid", "White Grid", "Black Grid, Bright Contours log2", "White Grid, Dark Contours log2", "Norm, Black Grid, White Circles log2", "Norm, White Grid, Black Circles log2", "Norm, Black Grid", "Norm, White Grid", "Norm, Black Grid, Bright Contours log2", "Norm, White Grid, Dark Contours log2", "White Circles log2", "Black Circles log2", "Bright Contours log2", "Dark Contours log2", "Norm, White Circles log2", "Norm, Black Circles log2", "Norm, Bright Contours log2", "Norm, Dark Contours log2"};
            final JComboBox color_domain_algs_opt = new JComboBox(algs);
            color_domain_algs_opt.setSelectedIndex(domain_coloring_alg);
            color_domain_algs_opt.setFocusable(false);
            color_domain_algs_opt.setToolTipText("Sets the domain coloring algorithm.");

            final JCheckBox use_palette_dc = new JCheckBox("Use Current Palette");
            use_palette_dc.setFocusable(false);
            use_palette_dc.setSelected(use_palette_domain_coloring);
            use_palette_dc.setToolTipText("Enables the use of the current palette.");

            Object[] message3 = {
                " ",
                "Set the maximum number of iterations.",
                "Maximum Iterations:", field_iterations,
                " ",
                "Select the current palette or the default coloring.",
                use_palette_dc,
                " ",
                "Set the domain coloring algorithm.",
                "Domain Coloring Algorithm:", color_domain_algs_opt,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Domain Coloring", JOptionPane.OK_CANCEL_OPTION);

            int tempIterations;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempIterations = Integer.parseInt(field_iterations.getText());

                    if(tempIterations <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.getDomainColoring().setSelected(false);
                        toolbar.getDomainColoringButton().setSelected(false);
                        return;
                    }

                    max_iterations = tempIterations;
                    domain_coloring_alg = color_domain_algs_opt.getSelectedIndex();
                    use_palette_domain_coloring = use_palette_dc.isSelected();

                    domain_coloring = true;

                    toolbar.getDomainColoringButton().setSelected(true);

                    options_menu.getDomainColoringOptions().setEnabled(true);

                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    tools_menu.getJulia().setEnabled(false);
                    toolbar.getJuliaButton().setEnabled(false);
                    tools_menu.getColorCyling().setEnabled(false);
                    toolbar.getColorCyclingButton().setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        options_menu.getColorsMenu().setEnabled(false);
                        toolbar.getCustomPaletteButton().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(false);
                    }
                    else {
                        options_menu.getDistanceEstimation().setEnabled(false);
                        options_menu.getBumpMap().setEnabled(false);

                        options_menu.getFakeDistanceEstimation().setEnabled(false);

                        options_menu.getEntropyColoring().setEnabled(false);
                        options_menu.getOffsetColoring().setEnabled(false);
                        options_menu.getGreyScaleColoring().setEnabled(false);

                        options_menu.getRainbowPalette().setEnabled(false);
                        options_menu.getOutColoringMenu().setEnabled(false);
                        options_menu.getInColoringMenu().setEnabled(false);
                        options_menu.getFractalColor().setEnabled(false);
                    }

                    options_menu.getGreedyAlgorithm().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutTestMenu().setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        infobar.getPalettePreview().setVisible(false);
                        infobar.getPalettePreviewLabel().setVisible(false);
                    }
                    else {
                        infobar.getPalettePreview().setVisible(true);
                        infobar.getPalettePreviewLabel().setVisible(true);
                    }

                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

                    setOptions(false);

                    progress.setValue(0);

                    scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                    scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                    resetImage();

                    if(d3) {
                        fiX = 0.64;
                        fiY = 0.82;
                        Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                    }

                    backup_orbit = null;

                    whole_image_done = false;

                    createThreads(false);

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);

                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getDomainColoring().setSelected(false);
                    toolbar.getDomainColoringButton().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                tools_menu.getDomainColoring().setSelected(false);
                toolbar.getDomainColoringButton().setSelected(false);
                main_panel.repaint();
                return;
            }
        }
    }

    public void setDomainColoringOptions() {
        JTextField field_iterations = new JTextField();

        field_iterations.setText("" + max_iterations);

        field_iterations.addAncestorListener(new RequestFocusListener());

        String[] algs = {"Black Grid, White Circles log2", "White Grid, Black Circles log2", "Black Grid", "White Grid", "Black Grid, Bright Contours log2", "White Grid, Dark Contours log2", "Norm, Black Grid, White Circles log2", "Norm, White Grid, Black Circles log2", "Norm, Black Grid", "Norm, White Grid", "Norm, Black Grid, Bright Contours log2", "Norm, White Grid, Dark Contours log2", "White Circles log2", "Black Circles log2", "Bright Contours log2", "Dark Contours log2", "Norm, White Circles log2", "Norm, Black Circles log2", "Norm, Bright Contours log2", "Norm, Dark Contours log2"};
        final JComboBox color_domain_algs_opt = new JComboBox(algs);
        color_domain_algs_opt.setSelectedIndex(domain_coloring_alg);
        color_domain_algs_opt.setFocusable(false);
        color_domain_algs_opt.setToolTipText("Sets the domain coloring algorithm.");

        final JCheckBox use_palette_dc = new JCheckBox("Use Current Palette");
        use_palette_dc.setFocusable(false);
        use_palette_dc.setSelected(use_palette_domain_coloring);
        use_palette_dc.setToolTipText("Enables the use of the current palette.");

        Object[] message3 = {
            " ",
            "Set the maximum number of iterations.",
            "Maximum Iterations:", field_iterations,
            " ",
            "Select the current palette or the default coloring.",
            use_palette_dc,
            " ",
            "Set the domain coloring algorithm.",
            "Domain Coloring Algorithm:", color_domain_algs_opt,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Domain Coloring", JOptionPane.OK_CANCEL_OPTION);

        int tempIterations;

        if(res == JOptionPane.OK_OPTION) {
            try {
                tempIterations = Integer.parseInt(field_iterations.getText());

                if(tempIterations <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                max_iterations = tempIterations;
                domain_coloring_alg = color_domain_algs_opt.getSelectedIndex();
                use_palette_domain_coloring = use_palette_dc.isSelected();

                if(!use_palette_domain_coloring) {
                    options_menu.getColorsMenu().setEnabled(false);
                    toolbar.getCustomPaletteButton().setEnabled(false);
                    toolbar.getRandomPaletteButton().setEnabled(false);
                }
                else {
                    options_menu.getDistanceEstimation().setEnabled(false);
                    options_menu.getBumpMap().setEnabled(false);

                    options_menu.getFakeDistanceEstimation().setEnabled(false);

                    options_menu.getEntropyColoring().setEnabled(false);
                    options_menu.getOffsetColoring().setEnabled(false);
                    options_menu.getGreyScaleColoring().setEnabled(false);

                    options_menu.getRainbowPalette().setEnabled(false);
                    options_menu.getOutColoringMenu().setEnabled(false);
                    options_menu.getInColoringMenu().setEnabled(false);
                    options_menu.getFractalColor().setEnabled(false);
                }

                if(!use_palette_domain_coloring) {
                    infobar.getPalettePreview().setVisible(false);
                    infobar.getPalettePreviewLabel().setVisible(false);
                }
                else {
                    infobar.getPalettePreview().setVisible(true);
                    infobar.getPalettePreviewLabel().setVisible(true);
                }

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                backup_orbit = null;

                whole_image_done = false;

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);

            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }
    }

    public void set3DOption() {

        if(!tools_menu.get3D().isSelected()) {
            d3 = false;
            setOptions(false);

            toolbar.get3DButton().setSelected(false);

            options_menu.get3DOptions().setEnabled(false);

            ThreadDraw.setArrays(image_size);

            progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }
        else {
            JTextField field = new JTextField();
            field.addAncestorListener(new RequestFocusListener());
            field.setText("" + detail);

            JTextField size_opt = new JTextField();
            size_opt.setText("" + d3_size_scale);

            JTextField field2 = new JTextField();
            field2.setText("" + d3_height_scale);

            JSlider scale_min_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, min_to_max_scaling);
            scale_min_val_opt.setPreferredSize(new Dimension(150, 35));
            scale_min_val_opt.setMajorTickSpacing(25);
            scale_min_val_opt.setMinorTickSpacing(1);
            scale_min_val_opt.setToolTipText("Sets the minimum value scaling percentage.");
            //scale_min_val_opt.setPaintLabels(true);
            //scale_min_val_opt.setSnapToTicks(true);
            scale_min_val_opt.setFocusable(false);

            JSlider scale_max_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 200, max_scaling);
            scale_max_val_opt.setPreferredSize(new Dimension(150, 35));
            scale_max_val_opt.setMajorTickSpacing(25);
            scale_max_val_opt.setMinorTickSpacing(1);
            scale_max_val_opt.setToolTipText("Sets the maximum value scaling percentage.");
            //scale_max_val_opt.setPaintLabels(true);
            //scale_min_val_opt.setSnapToTicks(true);
            scale_max_val_opt.setFocusable(false);

            JPanel temp_p3 = new JPanel();
            temp_p3.setLayout(new GridLayout(2, 2));
            temp_p3.add(new JLabel("Minimum Value Scaling:", SwingConstants.HORIZONTAL));
            temp_p3.add(new JLabel("Maximum Value Scaling:", SwingConstants.HORIZONTAL));
            temp_p3.add(scale_min_val_opt);
            temp_p3.add(scale_max_val_opt);

            String[] height_options = {"120 / (x + 1)", "e^(-x + 5)", "40 * log(x + 1)", "150 - e^(-x + 5)", "40 * sin(x)", "40 * cos(x)", "150 / (1 + e^(-3*x+3))"};

            JComboBox height_algorithm_opt = new JComboBox(height_options);
            height_algorithm_opt.setSelectedIndex(height_algorithm);
            height_algorithm_opt.setFocusable(false);
            height_algorithm_opt.setToolTipText("Sets the height algorithm.");

            final JCheckBox gaussian_scaling_opt = new JCheckBox("Gaussian Normalization");
            gaussian_scaling_opt.setSelected(gaussian_scaling);
            gaussian_scaling_opt.setFocusable(false);
            gaussian_scaling_opt.setToolTipText("Enables the gaussian normalization.");

            final JTextField field3 = new JTextField();
            field3.setText("" + gaussian_weight);
            field3.setEnabled(gaussian_scaling);

            String[] kernels = {"3", "5", "7", "9", "11"};
            final JComboBox kernels_size_opt = new JComboBox(kernels);
            kernels_size_opt.setSelectedIndex(gaussian_kernel);
            kernels_size_opt.setFocusable(false);
            kernels_size_opt.setEnabled(gaussian_scaling);
            kernels_size_opt.setToolTipText("Sets the radius of the gaussian normalization.");

            gaussian_scaling_opt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if(gaussian_scaling_opt.isSelected()) {
                        field3.setEnabled(true);
                        kernels_size_opt.setEnabled(true);
                    }
                    else {
                        field3.setEnabled(false);
                        kernels_size_opt.setEnabled(false);
                    }
                }

            });

            JSlider color_blend = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int)(color_3d_blending * 100)));
            color_blend.setPreferredSize(new Dimension(270, 35));
            color_blend.setMajorTickSpacing(25);
            color_blend.setMinorTickSpacing(1);
            color_blend.setToolTipText("Sets the color blending percentage.");
            color_blend.setPaintLabels(true);
            color_blend.setFocusable(false);

            JPanel cblend_panel = new JPanel();
            cblend_panel.setLayout(new GridLayout(3, 1));
            cblend_panel.add(new JLabel("Set the color blending percentage."));
            cblend_panel.add(new JLabel("Blending Percent:"));
            cblend_panel.add(color_blend);

            final JCheckBox height_shading_opt = new JCheckBox("Height Shading");
            height_shading_opt.setSelected(shade_height);
            height_shading_opt.setFocusable(false);
            height_shading_opt.setToolTipText("Enables the height shading.");

            JPanel height_color_panel = new JPanel();
            height_color_panel.setLayout(new GridLayout(3, 1));
            height_color_panel.add(new JLabel("Set the height shading."));

            String[] shades = {"White & Black", "White", "Black"};

            final JComboBox shade_choice_box = new JComboBox(shades);
            shade_choice_box.setSelectedIndex(shade_choice);
            shade_choice_box.setToolTipText("Selects the shade colors.");
            shade_choice_box.setFocusable(false);

            String[] shade_algorithms = {"Linear Interpolation", "Cosine Interpolation", "<10% and >90% Lin. Int.", "<20% and >80% Lin. Int.", "<30% and >70% Lin. Int.", "<40% and >60% Lin. Int."};

            final JComboBox shade_algorithm_box = new JComboBox(shade_algorithms);
            shade_algorithm_box.setSelectedIndex(shade_algorithm);
            shade_algorithm_box.setToolTipText("Selects the shade algorithm.");
            shade_algorithm_box.setFocusable(false);

            final JCheckBox shade_invert_opt = new JCheckBox("Invert Shading");
            shade_invert_opt.setSelected(shade_invert);
            shade_invert_opt.setFocusable(false);
            shade_invert_opt.setToolTipText("Inverts the height shading.");

            if(!height_shading_opt.isSelected()) {
                shade_choice_box.setEnabled(false);
                shade_algorithm_box.setEnabled(false);
                shade_invert_opt.setEnabled(false);
            }
            else {
                shade_choice_box.setEnabled(true);
                shade_algorithm_box.setEnabled(true);
                shade_invert_opt.setEnabled(true);
            }

            height_shading_opt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!height_shading_opt.isSelected()) {
                        shade_choice_box.setEnabled(false);
                        shade_algorithm_box.setEnabled(false);
                        shade_invert_opt.setEnabled(false);
                    }
                    else {
                        shade_choice_box.setEnabled(true);
                        shade_algorithm_box.setEnabled(true);
                        shade_invert_opt.setEnabled(true);
                    }
                }

            });

            JPanel temp_height_color_panel = new JPanel();

            temp_height_color_panel.add(shade_choice_box);
            temp_height_color_panel.add(shade_algorithm_box);
            temp_height_color_panel.add(shade_invert_opt);

            height_color_panel.add(height_shading_opt);

            height_color_panel.add(temp_height_color_panel);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setFocusable(false);

            tabbedPane.addTab("Color Blending", cblend_panel);
            tabbedPane.addTab("Height Shading", height_color_panel);

            JPanel temp_p2 = new JPanel();
            temp_p2.setLayout(new GridLayout(2, 2));
            temp_p2.add(new JLabel("3D Detail:", SwingConstants.HORIZONTAL));
            temp_p2.add(new JLabel("Size:", SwingConstants.HORIZONTAL));
            temp_p2.add(field);
            temp_p2.add(size_opt);

            JPanel temp_p = new JPanel();
            temp_p.setLayout(new GridLayout(2, 2));
            temp_p.add(new JLabel("Weight:", SwingConstants.HORIZONTAL));
            temp_p.add(new JLabel("Radius:", SwingConstants.HORIZONTAL));
            temp_p.add(field3);
            temp_p.add(kernels_size_opt);

            Object[] message3 = {
                "Set the 3D detail level and size.",
                temp_p2,
                " ",
                "Set the scale of the height.",
                "Scale:",
                field2,
                temp_p3,
                " ",
                "Set the height algorithm.",
                "Height Algorithm:",
                height_algorithm_opt,
                " ",
                "Select the gaussian normalization weight and radius.",
                gaussian_scaling_opt,
                temp_p,
                " ",
                tabbedPane,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "3D", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.OK_OPTION) {
                try {
                    int temp = Integer.parseInt(field.getText());
                    double temp2 = Double.parseDouble(field2.getText());
                    double temp3 = Double.parseDouble(field3.getText());
                    double temp4 = Double.parseDouble(size_opt.getText());

                    if(temp < 10) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }
                    else {
                        if(temp > 2000) {
                            main_panel.repaint();
                            JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be less than 2001.", "Error!", JOptionPane.ERROR_MESSAGE);
                            tools_menu.get3D().setSelected(false);
                            toolbar.get3DButton().setSelected(false);
                            return;
                        }
                    }

                    if(temp2 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    if(temp3 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    if(temp4 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    detail = temp;
                    d3_height_scale = temp2;
                    d3_size_scale = temp4;
                    height_algorithm = height_algorithm_opt.getSelectedIndex();
                    gaussian_scaling = gaussian_scaling_opt.isSelected();
                    gaussian_weight = temp3;
                    gaussian_kernel = kernels_size_opt.getSelectedIndex();
                    min_to_max_scaling = scale_min_val_opt.getValue();
                    max_scaling = scale_max_val_opt.getValue();

                    shade_height = height_shading_opt.isSelected();
                    shade_choice = shade_choice_box.getSelectedIndex();
                    shade_algorithm = shade_algorithm_box.getSelectedIndex();
                    shade_invert = shade_invert_opt.isSelected();

                    //d3_draw_method = draw_choice.getSelectedIndex();
                    color_3d_blending = color_blend.getValue() / 100.0;

                    fiX = 0.64;
                    fiY = 0.82;

                    setOptions(false);

                    ThreadDraw.set3DArrays(detail);

                    progress.setMaximum((detail * detail) + (detail * detail / 100));

                    options_menu.getGreedyAlgorithm().setEnabled(false);

                    options_menu.get3DOptions().setEnabled(true);

                    d3 = true;

                    toolbar.get3DButton().setSelected(true);

                    progress.setValue(0);

                    scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                    scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                    resetImage();

                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());

                    backup_orbit = null;

                    whole_image_done = false;

                    createThreads(false);

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.get3D().setSelected(false);
                    toolbar.get3DButton().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                tools_menu.get3D().setSelected(false);
                toolbar.get3DButton().setSelected(false);
                main_panel.repaint();
                return;
            }
        }

    }

    public void set3DDetails() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + detail);

        JTextField size_opt = new JTextField();
        size_opt.setText("" + d3_size_scale);

        JTextField field2 = new JTextField();
        field2.setText("" + d3_height_scale);

        JSlider scale_min_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, min_to_max_scaling);
        scale_min_val_opt.setPreferredSize(new Dimension(150, 35));
        scale_min_val_opt.setMajorTickSpacing(25);
        scale_min_val_opt.setMinorTickSpacing(1);
        scale_min_val_opt.setToolTipText("Sets the minimum value scaling percentage.");
        //scale_min_val_opt.setPaintLabels(true);
        //scale_min_val_opt.setSnapToTicks(true);
        scale_min_val_opt.setFocusable(false);

        JSlider scale_max_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 200, max_scaling);
        scale_max_val_opt.setPreferredSize(new Dimension(150, 35));
        scale_max_val_opt.setMajorTickSpacing(25);
        scale_max_val_opt.setMinorTickSpacing(1);
        scale_max_val_opt.setToolTipText("Sets the maximum value scaling percentage.");
        //scale_max_val_opt.setPaintLabels(true);
        //scale_min_val_opt.setSnapToTicks(true);
        scale_max_val_opt.setFocusable(false);

        JPanel temp_p3 = new JPanel();
        temp_p3.setLayout(new GridLayout(2, 2));
        temp_p3.add(new JLabel("Minimum Value Scaling:", SwingConstants.HORIZONTAL));
        temp_p3.add(new JLabel("Maximum Value Scaling:", SwingConstants.HORIZONTAL));
        temp_p3.add(scale_min_val_opt);
        temp_p3.add(scale_max_val_opt);

        String[] height_options = {"120 / (x + 1)", "e^(-x + 5)", "40 * log(x + 1)", "150 - e^(-x + 5)", "40 * sin(x)", "40 * cos(x)", "150 / (1 + e^(-3*x+3))"};

        JComboBox height_algorithm_opt = new JComboBox(height_options);
        height_algorithm_opt.setSelectedIndex(height_algorithm);
        height_algorithm_opt.setFocusable(false);
        height_algorithm_opt.setToolTipText("Sets the height algorithm.");

        final JCheckBox gaussian_scaling_opt = new JCheckBox("Gaussian Normalization");
        gaussian_scaling_opt.setSelected(gaussian_scaling);
        gaussian_scaling_opt.setFocusable(false);
        gaussian_scaling_opt.setToolTipText("Enables the gaussian normalization.");

        final JTextField field3 = new JTextField();
        field3.setText("" + gaussian_weight);
        field3.setEnabled(gaussian_scaling);

        String[] kernels = {"3", "5", "7", "9", "11"};
        final JComboBox kernels_size_opt = new JComboBox(kernels);
        kernels_size_opt.setSelectedIndex(gaussian_kernel);
        kernels_size_opt.setFocusable(false);
        kernels_size_opt.setEnabled(gaussian_scaling);
        kernels_size_opt.setToolTipText("Sets the radius of the gaussian normalization.");

        gaussian_scaling_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(gaussian_scaling_opt.isSelected()) {
                    field3.setEnabled(true);
                    kernels_size_opt.setEnabled(true);
                }
                else {
                    field3.setEnabled(false);
                    kernels_size_opt.setEnabled(false);
                }
            }

        });

        JSlider color_blend = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int)(color_3d_blending * 100)));
        color_blend.setPreferredSize(new Dimension(270, 35));
        color_blend.setMajorTickSpacing(25);
        color_blend.setMinorTickSpacing(1);
        color_blend.setToolTipText("Sets the color blending percentage.");
        color_blend.setPaintLabels(true);
        color_blend.setFocusable(false);

        JPanel cblend_panel = new JPanel();
        cblend_panel.setLayout(new GridLayout(3, 1));
        cblend_panel.add(new JLabel("Set the color blending percentage."));
        cblend_panel.add(new JLabel("Blending Percent:"));
        cblend_panel.add(color_blend);

        final JCheckBox height_shading_opt = new JCheckBox("Height Shading");
        height_shading_opt.setSelected(shade_height);
        height_shading_opt.setFocusable(false);
        height_shading_opt.setToolTipText("Enables the height shading.");

        JPanel height_color_panel = new JPanel();
        height_color_panel.setLayout(new GridLayout(3, 1));
        height_color_panel.add(new JLabel("Set the height shading."));

        String[] shades = {"White & Black", "White", "Black"};

        final JComboBox shade_choice_box = new JComboBox(shades);
        shade_choice_box.setSelectedIndex(shade_choice);
        shade_choice_box.setToolTipText("Selects the shade colors.");
        shade_choice_box.setFocusable(false);

        String[] shade_algorithms = {"Linear Interpolation", "Cosine Interpolation", "<10% and >90% Lin. Int.", "<20% and >80% Lin. Int.", "<30% and >70% Lin. Int.", "<40% and >60% Lin. Int."};

        final JComboBox shade_algorithm_box = new JComboBox(shade_algorithms);
        shade_algorithm_box.setSelectedIndex(shade_algorithm);
        shade_algorithm_box.setToolTipText("Selects the shade algorithm.");
        shade_algorithm_box.setFocusable(false);

        final JCheckBox shade_invert_opt = new JCheckBox("Invert Shading");
        shade_invert_opt.setSelected(shade_invert);
        shade_invert_opt.setFocusable(false);
        shade_invert_opt.setToolTipText("Inverts the height shading.");

        if(!height_shading_opt.isSelected()) {
            shade_choice_box.setEnabled(false);
            shade_algorithm_box.setEnabled(false);
            shade_invert_opt.setEnabled(false);
        }
        else {
            shade_choice_box.setEnabled(true);
            shade_algorithm_box.setEnabled(true);
            shade_invert_opt.setEnabled(true);
        }

        height_shading_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!height_shading_opt.isSelected()) {
                    shade_choice_box.setEnabled(false);
                    shade_algorithm_box.setEnabled(false);
                    shade_invert_opt.setEnabled(false);
                }
                else {
                    shade_choice_box.setEnabled(true);
                    shade_algorithm_box.setEnabled(true);
                    shade_invert_opt.setEnabled(true);
                }
            }

        });

        JPanel temp_height_color_panel = new JPanel();

        temp_height_color_panel.add(shade_choice_box);
        temp_height_color_panel.add(shade_algorithm_box);
        temp_height_color_panel.add(shade_invert_opt);

        height_color_panel.add(height_shading_opt);

        height_color_panel.add(temp_height_color_panel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);

        tabbedPane.addTab("Color Blending", cblend_panel);
        tabbedPane.addTab("Height Shading", height_color_panel);

        JPanel temp_p2 = new JPanel();
        temp_p2.setLayout(new GridLayout(2, 2));
        temp_p2.add(new JLabel("3D Detail:", SwingConstants.HORIZONTAL));
        temp_p2.add(new JLabel("Size:", SwingConstants.HORIZONTAL));
        temp_p2.add(field);
        temp_p2.add(size_opt);

        JPanel temp_p = new JPanel();
        temp_p.setLayout(new GridLayout(2, 2));
        temp_p.add(new JLabel("Weight:", SwingConstants.HORIZONTAL));
        temp_p.add(new JLabel("Radius:", SwingConstants.HORIZONTAL));
        temp_p.add(field3);
        temp_p.add(kernels_size_opt);

        Object[] message3 = {
            "Set the 3D detail level and size.",
            temp_p2,
            " ",
            "Set the scale of the height.",
            "Scale:",
            field2,
            temp_p3,
            " ",
            "Set the height algorithm.",
            "Height Algorithm:",
            height_algorithm_opt,
            " ",
            "Select the gaussian normalization weight and radius.",
            gaussian_scaling_opt,
            temp_p,
            " ",
            tabbedPane,};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "3D", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            try {
                int temp = Integer.parseInt(field.getText());
                double temp2 = Double.parseDouble(field2.getText());
                double temp3 = Double.parseDouble(field3.getText());
                double temp4 = Double.parseDouble(size_opt.getText());

                if(temp < 10) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if(temp > 2000) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be less than 2001.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if(temp2 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp3 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp4 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                detail = temp;
                d3_height_scale = temp2;
                d3_size_scale = temp4;
                height_algorithm = height_algorithm_opt.getSelectedIndex();
                gaussian_scaling = gaussian_scaling_opt.isSelected();
                gaussian_weight = temp3;
                gaussian_kernel = kernels_size_opt.getSelectedIndex();
                min_to_max_scaling = scale_min_val_opt.getValue();
                max_scaling = scale_max_val_opt.getValue();

                shade_height = height_shading_opt.isSelected();
                shade_choice = shade_choice_box.getSelectedIndex();
                shade_algorithm = shade_algorithm_box.getSelectedIndex();
                shade_invert = shade_invert_opt.isSelected();

                //d3_draw_method = draw_choice.getSelectedIndex();
                color_3d_blending = color_blend.getValue() / 100.0;

                ThreadDraw.set3DArrays(detail);

                progress.setMaximum((detail * detail) + (detail * detail / 100));

                setOptions(false);

                progress.setValue(0);

                resetImage();

                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());

                backup_orbit = null;

                whole_image_done = false;

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

    }

    public void setBoundariesNumber() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + boundaries_num);

        String[] method = {"Power of two", "Increment of one"};

        JComboBox draw_choice = new JComboBox(method);
        draw_choice.setSelectedIndex(boundaries_spacing_method);
        draw_choice.setToolTipText("Selects the spacing method.");
        draw_choice.setFocusable(false);

        String[] method2 = {"Circle", "Square", "Rhombus"};

        JComboBox draw_choice2 = new JComboBox(method2);
        draw_choice2.setSelectedIndex(boundaries_type);
        draw_choice2.setToolTipText("Selects the type of boundary.");
        draw_choice2.setFocusable(false);

        Object[] message3 = {
            " ",
            "You are using " + boundaries_num + " as the number of boundaries.\nEnter the new boundaries number.",
            field,
            " ",
            "Select the spacing method.",
            draw_choice,
            " ",
            "Select the boundary type.",
            draw_choice2,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Boundaries Options", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            try {
                int temp = Integer.parseInt(field.getText());

                if(temp < 1) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Boundaries number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if(temp > 64) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "Boundaries number must be less than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                boundaries_num = temp;

                boundaries_spacing_method = draw_choice.getSelectedIndex();

                boundaries_type = draw_choice2.getSelectedIndex();

            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

    }

    public void setGridTiles() {
        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + grid_tiles + " grid tiles\nin each dimension.\nEnter the new grid tiles number.", "Grid Tiles", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if(temp < 2) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Grid tiles number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                if(temp > 64) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Grid tiles number must be less than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            grid_tiles = temp;

            main_panel.repaint();
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setColorIntensity() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + color_intensity + " for color intensity.\nEnter the new color intensity number.", "Color Intensity", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if(temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Color intensity value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            color_intensity = temp;

            setOptions(false);

            progress.setValue(0);

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(filters[ANTIALIASING] || (domain_coloring && use_palette_domain_coloring)) {
                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if(julia_map) {
                    startThreads(julia_grid_first_dimension);
                }
                else {
                    startThreads(n);
                }
            }
            else {
                if(d3) {
                    createThreads(false);
                }
                else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }

        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    private void rootFindingMethodsSetEnabled(boolean option) {

        fractal_functions[NEWTON3].setEnabled(option);
        fractal_functions[NEWTON4].setEnabled(option);
        fractal_functions[NEWTONGENERALIZED3].setEnabled(option);
        fractal_functions[NEWTONGENERALIZED8].setEnabled(option);
        fractal_functions[NEWTONSIN].setEnabled(option);
        fractal_functions[NEWTONCOS].setEnabled(option);
        fractal_functions[NEWTONPOLY].setEnabled(option);
        fractal_functions[NEWTONFORMULA].setEnabled(option);
        fractal_functions[HALLEY3].setEnabled(option);
        fractal_functions[HALLEY4].setEnabled(option);
        fractal_functions[HALLEYGENERALIZED3].setEnabled(option);
        fractal_functions[HALLEYGENERALIZED8].setEnabled(option);
        fractal_functions[HALLEYSIN].setEnabled(option);
        fractal_functions[HALLEYCOS].setEnabled(option);
        fractal_functions[HALLEYPOLY].setEnabled(option);
        fractal_functions[HALLEYFORMULA].setEnabled(option);
        fractal_functions[SCHRODER3].setEnabled(option);
        fractal_functions[SCHRODER4].setEnabled(option);
        fractal_functions[SCHRODERGENERALIZED3].setEnabled(option);
        fractal_functions[SCHRODERGENERALIZED8].setEnabled(option);
        fractal_functions[SCHRODERSIN].setEnabled(option);
        fractal_functions[SCHRODERCOS].setEnabled(option);
        fractal_functions[SCHRODERPOLY].setEnabled(option);
        fractal_functions[SCHRODERFORMULA].setEnabled(option);
        fractal_functions[HOUSEHOLDER3].setEnabled(option);
        fractal_functions[HOUSEHOLDER4].setEnabled(option);
        fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(option);
        fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(option);
        fractal_functions[HOUSEHOLDERSIN].setEnabled(option);
        fractal_functions[HOUSEHOLDERCOS].setEnabled(option);
        fractal_functions[HOUSEHOLDERPOLY].setEnabled(option);
        fractal_functions[HOUSEHOLDERFORMULA].setEnabled(option);
        fractal_functions[SECANT3].setEnabled(option);
        fractal_functions[SECANT4].setEnabled(option);
        fractal_functions[SECANTGENERALIZED3].setEnabled(option);
        fractal_functions[SECANTGENERALIZED8].setEnabled(option);
        fractal_functions[SECANTCOS].setEnabled(option);
        fractal_functions[SECANTPOLY].setEnabled(option);
        fractal_functions[SECANTFORMULA].setEnabled(option);
        fractal_functions[STEFFENSEN3].setEnabled(option);
        fractal_functions[STEFFENSEN4].setEnabled(option);
        fractal_functions[STEFFENSENGENERALIZED3].setEnabled(option);
        fractal_functions[STEFFENSENPOLY].setEnabled(option);
        fractal_functions[STEFFENSENFORMULA].setEnabled(option);
        fractal_functions[MULLER3].setEnabled(option);
        fractal_functions[MULLER4].setEnabled(option);
        fractal_functions[MULLERGENERALIZED3].setEnabled(option);
        fractal_functions[MULLERGENERALIZED8].setEnabled(option);
        fractal_functions[MULLERSIN].setEnabled(option);
        fractal_functions[MULLERCOS].setEnabled(option);
        fractal_functions[MULLERPOLY].setEnabled(option);
        fractal_functions[MULLERFORMULA].setEnabled(option);
    }

    private void optionsEnableShortcut() {
        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        options_menu.getDistanceEstimation().setEnabled(false);
        if(out_coloring_algorithm != BIOMORPH) {
            out_coloring_modes[BIOMORPH].setEnabled(true);
        }
        if(out_coloring_algorithm != BANDED) {
            out_coloring_modes[BANDED].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
        }
        if(out_coloring_algorithm != ITERATIONS_PLUS_RE) {
            out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
        }
        if(out_coloring_algorithm != ITERATIONS_PLUS_IM) {
            out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
        }
        if(out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if(out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
        }
        if(out_coloring_algorithm != ESCAPE_TIME_GRID) {
            out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
        }
    }

    private void optionsEnableShortcut2() {
        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        options_menu.getDistanceEstimation().setEnabled(false);
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        out_coloring_modes[BIOMORPH].setEnabled(false);
        out_coloring_modes[BANDED].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(false);
        out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(false);
        out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(false);
        out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(false);
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutTestMenu().setEnabled(false);
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));
    }

    public void updateValues(String mode) {

        old_xCenter = xCenter;
        old_yCenter = yCenter;
        old_size = size;
        old_height_ratio = height_ratio;

        old_rotation_vals[0] = rotation_vals[0];
        old_rotation_vals[1] = rotation_vals[1];

        old_rotation_center[0] = rotation_center[0];
        old_rotation_center[1] = rotation_center[1];

        old_d3 = d3;
        old_polar_projection = polar_projection;
        old_grid = grid;
        old_boundaries = boundaries;

        statusbar.getMode().setText(mode);

    }

    public void setColorCyclingOptions() {
        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        final JSlider speed_slid = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000 - color_cycling_speed);

        speed_slid.setPreferredSize(new Dimension(200, 35));

        speed_slid.setToolTipText("Sets the color cycling speed.");

        speed_slid.setPaintLabels(true);
        speed_slid.setFocusable(false);

        Hashtable labelTable = new Hashtable();

        labelTable.put(speed_slid.getMinimum(), new JLabel("Slow"));
        labelTable.put(speed_slid.getMaximum(), new JLabel("Fast"));
        speed_slid.setLabelTable(labelTable);

        Object[] message3 = {
            " ",
            "Set the color cycling speed.",
            speed_slid,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Color Cycling Options", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            color_cycling_speed = speed_slid.getMaximum() - speed_slid.getValue();
        }
        else {
            if(!color_cycling) {
                main_panel.repaint();
            }
            return;
        }
    }

    private void resetImage() {

        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, 0);

        //OLD
        //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        //image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
    }

    public void setApplyPlaneOnWholeJulia() {

        if(!options_menu.getApplyPlaneOnWholeJuliaOpt().isSelected()) {
            apply_plane_on_julia = false;
        }
        else {
            apply_plane_on_julia = true;
        }

        if(julia || julia_map) {
            defaultFractalSettings();
        }

    }

    public void setApplyPlaneOnJuliaSeed() {

        if(!options_menu.getApplyPlaneOnJuliaSeedOpt().isSelected()) {
            apply_plane_on_julia_seed = false;
        }
        else {
            apply_plane_on_julia_seed = true;
        }

        if(julia || julia_map) {
            defaultFractalSettings();
        }

    }

    public void Overview() {

        //JTextArea textArea = new JTextArea(32, 55); // 60
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(800, 500));
        //textArea.setLineWrap(false);
        //textArea.setWrapStyleWord(false);

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

        String keyword_color = "#008080";
        String condition_color = "#800000";

        String tab = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";
        String tab2 = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";

        String overview = "<html><center><b><u><font size='5' face='arial' color='blue'>Active Fractal Options</font></u></b></center><br><br><font  face='arial' size='3'>";

        overview += "<b><font color='red'>Center:</font></b> " + Complex.toString2(p.x, p.y) + "<br><br>";
        overview += "<b><font color='red'>Size:</font></b> " + size + "<br><br>";

        if(polar_projection) {
            overview += "<b><font color='red'>Polar Projection:</font></b> " + "<br>" + tab + "Circle Periods = " + circle_period + "<br><br>";
        }

        overview += "<b><font color='red'>Function:</font></b> " + fractal_functions[function].getText() + "<br>";

        if(function == MANDELPOLY || function == NEWTONPOLY || function == HALLEYPOLY || function == SCHRODERPOLY || function == HOUSEHOLDERPOLY || function == SECANTPOLY || function == STEFFENSENPOLY || function == MULLERPOLY) {
            overview += tab + poly + "<br>";
        }
        else if(function == NEWTON3 || function == HALLEY3 || function == HOUSEHOLDER3 || function == SCHRODER3 || function == SECANT3 || function == STEFFENSEN3 || function == MULLER3) {
            overview += tab + "p(z) = z^3 - 1" + "<br>";
        }
        else if(function == NEWTON4 || function == HALLEY4 || function == HOUSEHOLDER4 || function == SCHRODER4 || function == SECANT4 || function == STEFFENSEN4 || function == MULLER4) {
            overview += tab + "p(z) = z^4 - 1" + "<br>";
        }
        else if(function == NEWTONGENERALIZED3 || function == HALLEYGENERALIZED3 || function == HOUSEHOLDERGENERALIZED3 || function == SCHRODERGENERALIZED3 || function == SECANTGENERALIZED3 || function == STEFFENSENGENERALIZED3 || function == MULLERGENERALIZED3) {
            overview += tab + "p(z) = z^3 - 2z + 2" + "<br>";
        }
        else if(function == NEWTONGENERALIZED8 || function == HALLEYGENERALIZED8 || function == HOUSEHOLDERGENERALIZED8 || function == SCHRODERGENERALIZED8 || function == SECANTGENERALIZED8 || function == MULLERGENERALIZED8) {
            overview += tab + "p(z) = z^8 + 15z^4 - 16" + "<br>";
        }
        else if(function == NEWTONCOS || function == HALLEYCOS || function == HOUSEHOLDERCOS || function == SCHRODERCOS || function == SECANTCOS || function == MULLERCOS) {
            overview += tab + "f(z) = cos(z)" + "<br>";
        }
        else if(function == NEWTONSIN || function == HALLEYSIN || function == HOUSEHOLDERSIN || function == SCHRODERSIN || function == MULLERSIN) {
            overview += tab + "f(z) = sin(z)" + "<br>";
        }
        else if(function == MANDELBROTNTH) {
            overview += tab + "z = z^" + z_exponent + " + c<br>";
        }
        else if(function == MANDELBROTWTH) {
            overview += tab + "z = z^(" + Complex.toString2(z_exponent_complex[0], z_exponent_complex[1]) + ") + c<br>";
        }
        else if(function == LAMBDA) {
            overview += tab + "z = cz(1 - z)" + "<br>";
        }
        else if(function == MAGNET1) {
            overview += tab + "z = ((z^2 + c - 1)/(2z + c - 2))^2" + "<br>";
        }
        else if(function == MAGNET2) {
            overview += tab + "z = ((z^3 + 3(c - 1)z + (c - 1)(c - 2))/(3z^2 + 3(c - 2)z + (c - 1)(c - 2))))^2" + "<br>";
        }
        else if(function == FROTHY_BASIN) {
            overview += tab + "z = z^2 - (1 + 1.028713768218725i)conj(z) + c<br>";
        }
        else if(function == SPIDER) {
            overview += tab + "z = z^2 + c<br>";
            overview += tab + "c = c/2 + z<br>";
        }
        else if(function == PHOENIX) {
            overview += tab + "t = z^2 + (im(c)re(s) + re(c)) + (im(c)im(s))i<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        }
        else if(function == SIERPINSKI_GASKET) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[im(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) + (2im(z) - 1)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else if</font> <font color='" + condition_color + "'>[re(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) - 1 + 2im(z)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = 2z<br>";
        }
        else if(function == BARNSLEY1) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z - 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z + 1)c<br>";
        }
        else if(function == BARNSLEY2) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z)im(c) + re(c)im(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z + 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z - 1)c<br>";
        }
        else if(function == BARNSLEY3) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) > 0]</font> <font color='" + keyword_color + "'>then</font> z = z^2 - 1<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = z^2 + re(c)re(z) + im(c)re(z) - 1<br>";
        }
        else if(function == SZEGEDI_BUTTERFLY1) {
            overview += tab + "z = (im(z)^2 - sqrt(abs(re(z)))) + (re(z)^2 - sqrt(abs(im(z))))i + c<br>";
        }
        else if(function == SZEGEDI_BUTTERFLY2) {
            overview += tab + "z = (re(z)^2 - sqrt(abs(im(z)))) + (im(z)^2 - sqrt(abs(re(z))))i + c<br>";
        }
        else if(function == MANOWAR) {
            overview += tab + "t = z^2 + s + c<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        }
        else if(function == MANDELBAR) {
            overview += tab + "z = conj(z)^2 + c<br>";
        }
        else if(function == COUPLED_MANDELBROT) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = w^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = c<br>";
        }
        else if(function == COUPLED_MANDELBROT_BURNING_SHIP) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = abs(w)^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = 0.0 + 0.0i<br>";
        }
        else if(function == NOVA) {
            switch (nova_method) {
                case NOVA_NEWTON:
                    overview += tab + "Newton Method<br>";
                    break;
                case NOVA_HALLEY:
                    overview += tab + "Halley Method<br>";
                    break;
                case NOVA_SCHRODER:
                    overview += tab + "Schroder Method<br>";
                    break;
                case NOVA_HOUSEHOLDER:
                    overview += tab + "Householder Method<br>";
                    break;
                case NOVA_SECANT:
                    overview += tab + "Secant Method<br>";
                    break;
                case NOVA_STEFFENSEN:
                    overview += tab + "Steffensen Method<br>";
                    break;
                case NOVA_MULLER:
                    overview += tab + "Muller Method<br>";
                    break;
            }

            overview += tab + "p(z) = z^(" + Complex.toString2(z_exponent_nova[0], z_exponent_nova[1]) + ") - 1<br>";
            overview += tab + "Relaxation = " + Complex.toString2(relaxation[0], relaxation[1]) + "<br>";
        }
        else if(function == NEWTONFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + user_dfz_formula + "<br>";
        }
        else if(function == HALLEYFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + user_ddfz_formula + "<br>";
        }
        else if(function == SCHRODERFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + user_ddfz_formula + "<br>";
        }
        else if(function == HOUSEHOLDERFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + user_ddfz_formula + "<br>";
        }
        else if(function == SECANTFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
        }
        else if(function == STEFFENSENFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
        }
        else if(function == MULLERFORMULA) {
            overview += tab + "f(z) = " + user_fz_formula + "<br>";
        }
        else if(function == USER_FORMULA) {
            overview += tab + "z = " + user_formula + "<br>";
            overview += tab + "c = " + user_formula2 + "<br>";
            if(!domain_coloring) {
                if(bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(function == USER_FORMULA_ITERATION_BASED) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 0]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_iteration_based[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 1]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_iteration_based[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 2]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_iteration_based[2] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 3]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_iteration_based[3] + "<br>";
            if(!domain_coloring) {
                if(bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(function == USER_FORMULA_CONDITIONAL) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_formula_conditions[0] + " > " + user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_condition_formula[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_formula_conditions[0] + " &#60; " + user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_condition_formula[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_formula_conditions[0] + " = " + user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_formula_condition_formula[2] + "<br>";
            if(!domain_coloring) {
                if(bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(function == USER_FORMULA_COUPLED) {
            overview += tab + "a = " + user_formula_coupled[0] + "<br>";
            overview += tab + "b = " + user_formula_coupled[1] + "<br>";

            overview += tab + "Coupling = " + coupling + "<br>";

            if(coupling_method == 0) {
                overview += tab + "Simple Coupling<br>";
                overview += tab2 + "Final_Coupling = Coupling<br>";
            }
            else if(coupling_method == 1) {
                overview += tab + "Cosine Coupling<br>";
                overview += tab2 + "Amplitude = " + coupling_amplitude + "<br>";
                overview += tab2 + "Frequency = " + coupling_frequency + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * cos(Current_Iteration * Frequency))<br>";
            }
            else {
                overview += tab + "Random Coupling<br>";
                overview += tab2 + "Amplitude = " + coupling_amplitude + "<br>";
                overview += tab2 + "Seed = " + coupling_seed + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * (Random_Sequence_Number - 0.5) * 2)<br>";
            }

            overview += tab + "z = Final_Coupling * (b - a) + a<br>";
            overview += tab + "z2 = Final_Coupling * (a - b) + b<br>";

            overview += tab + "z2(0) = " + user_formula_coupled[2] + "<br>";

            if(!domain_coloring) {
                if(bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }

        if((function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) && burning_ship) {
            overview += tab + "Burning Ship<br>";
            overview += tab2 + "z = abs(z), applied before the function evaluation.<br>";
        }
        if((function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) && mandel_grass) {
            overview += tab + "Mandel Grass = " + Complex.toString2(mandel_grass_vals[0], mandel_grass_vals[1]) + "<br>";
            overview += tab2 + "z = z + (MG * z)/(norm(z)), applied after the function evaluation.<br>";
        }
        overview += "<br>";

        if(julia) {
            overview += "<b><font color='red'>Julia Seed:</font></b> " + Complex.toString2(xJuliaCenter, yJuliaCenter) + " is replacing the c constant in the formula.<br><br>";
        }

        overview += "<b><font color='red'>Plane Transformation:</font></b> Applies the transformation \"" + planes[plane_type].getText() + "\" to every plane point c.<br>";

        if(plane_type == CIRCLEINVERSION_PLANE) {
            overview += tab + "Center = " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br>";
            overview += tab + "Radius = " + plane_transform_radius + "<br>";
        }
        else if(plane_type == INFLECTION_PLANE || plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE) {
            overview += tab + "Center = " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br>";
        }
        else if(plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {
            overview += tab + "Radius = " + plane_transform_radius + "<br>";
        }
        else if(plane_type == PINCH_PLANE) {
            overview += tab + "Center = " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + plane_transform_radius + "<br>";
            overview += tab + "Amount = " + plane_transform_amount + "<br>";
        }
        else if(plane_type == SHEAR_PLANE) {
            overview += tab + "Scale Real = " + plane_transform_scales[0] + "<br>";
            overview += tab + "Scale Imaginary = " + plane_transform_scales[1] + "<br>";
        }
        else if(plane_type == TWIRL_PLANE) {
            overview += tab + "Center = " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + plane_transform_radius + "<br>";
        }
        else if(plane_type == KALEIDOSCOPE_PLANE) {
            overview += tab + "Center = " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + plane_transform_angle + " degrees<br>";
            overview += tab + "Angle 2 = " + plane_transform_angle2 + " degrees<br>";
            overview += tab + "Radius = " + plane_transform_radius + "<br>";
            overview += tab + "Sides = " + plane_transform_sides + "<br>";
        }
        else if(plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {
            overview += tab + "Foci = (" + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + ") and (" + Complex.toString2(-plane_transform_center[0], -plane_transform_center[1]) + ")<br>";
        }
        else if(plane_type == USER_PLANE) {
            if(user_plane_algorithm == 0) {
                overview += tab + "z = " + user_plane + "<br>";
            }
            else {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_plane_conditions[0] + " > " + user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_plane_condition_formula[0] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_plane_conditions[0] + " &#60; " + user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_plane_condition_formula[1] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_plane_conditions[0] + " = " + user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + user_plane_condition_formula[2] + "<br>";
            }
        }

        overview += "<br>";

        if(!perturbation && !init_val && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            if(apply_plane_on_julia && apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed and the Whole Plane (c).<br><br>";
            }
            else if(apply_plane_on_julia) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to Whole Plane (c).<br><br>";
            }
            else if(apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed.<br><br>";
            }
        }

        if(!init_val) {
            String res = ThreadDraw.getDefaultInitialValue();

            if(!res.equals("")) {
                overview += "<b><font color='red'>Initial Value:</font></b> Default Value<br>";
                overview += tab + "z(0) = " + res + "<br>";
                overview += "<br>";
            }
        }
        else {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    overview += "<b><font color='red'>Initial Value:</font></b> Variable Value<br>";
                    overview += tab + "z(0) = " + initial_value_user_formula + "<br>";
                }
                else {
                    overview += "<b><font color='red'>Initial Value:</font></b> Variable Value Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_initial_value_conditions[0] + " > " + user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + user_initial_value_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_initial_value_conditions[0] + " &#60; " + user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + user_initial_value_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_initial_value_conditions[0] + " = " + user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + user_initial_value_condition_formula[2] + "<br>";
                }
            }
            else {
                overview += "<b><font color='red'>Initial Value:</font></b> Static Value<br>";
                overview += tab + "z(0) = " + Complex.toString2(initial_vals[0], initial_vals[1]) + "<br>";
            }
            overview += "<br>";
        }

        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    overview += "<b><font color='red'>Perturbation:</font></b> Variable Value<br>";
                    overview += tab + "z(0) = z(0) + " + perturbation_user_formula + "<br>";
                }
                else {
                    overview += "<b><font color='red'>Perturbation:</font></b> Variable Value Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_perturbation_conditions[0] + " > " + user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + user_perturbation_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_perturbation_conditions[0] + " &#60; " + user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + user_perturbation_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_perturbation_conditions[0] + " = " + user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + user_perturbation_condition_formula[2] + "<br>";
                }
            }
            else {
                overview += "<b><font color='red'>Perturbation:</font></b> Static Value<br>";
                overview += tab + "z(0) = z(0) + " + Complex.toString2(perturbation_vals[0], perturbation_vals[1]) + "<br>";
            }
            overview += "<br>";
        }

        overview += "<b><font color='red'>User Point:</font></b> " + Complex.toString2(plane_transform_center[0], plane_transform_center[1]) + "<br><br>";

        overview += "<b><font color='red'>Maximum Iterations:</font></b> " + max_iterations + "<br><br>";

        if(options_menu.getBailout().isEnabled()) {

            overview += "<b><font color='red'>Bailout Test:</font></b> Escaping when the criterion defined by the test \"" + bailout_tests[bailout_test_algorithm].getText() + "\" is met.<br>";
            if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z))^" + n_norm + " + abs(im(z))^" + n_norm + ")^(1/" + n_norm + ") >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_USER) {
                String greater = "", equal = "", lower = "";

                if(bailout_test_comparison == 0) { // >
                    greater = "Escaped";
                    equal = "Not Escaped";
                    lower = "Not Escaped";
                }
                else if(bailout_test_comparison == 1) { // >=
                    greater = "Escaped";
                    equal = "Escaped";
                    lower = "Not Escaped";
                }
                else if(bailout_test_comparison == 2) { // <
                    greater = "Not Escaped";
                    equal = "Not Escaped";
                    lower = "Escaped";
                }
                else if(bailout_test_comparison == 3) { // <=
                    greater = "Not Escaped";
                    equal = "Escaped";
                    lower = "Escaped";
                }
                else if(bailout_test_comparison == 4) { // !=
                    greater = "Not Escaped";
                    equal = "Escaped";
                    lower = "Not Escaped";
                }
                else if(bailout_test_comparison == 5) { // ! !=
                    greater = "Escaped";
                    equal = "Not Escaped";
                    lower = "Escaped";
                }

                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + bailout_test_user_formula + " > " + bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + bailout_test_user_formula + " &#60; " + bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + bailout_test_user_formula + " = " + bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_CIRCLE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_SQUARE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout or abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_RHOMBUS) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) + abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_STRIP) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_HALFPLANE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            overview += "<br>";

            if(function == MAGNET1 || function == MAGNET2) {
                overview += "<b><font color='red'>Bailout Test 2:</font></b> Escaping when a complex value almost reaches 1 + 0i (convergence).<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - 1 - 0i) &#60;= small value]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br><br>";
            }

            overview += "<b><font color='red'>Bailout:</font></b> " + bailout + "<br><br>";
        }
        else if(!domain_coloring) {
            overview += "<b><font color='red'>Bailout Test:</font></b> Escaping when two consecutive complex values are almost the same (convergence).<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - p) &#60;= small value]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br><br>";
        }
        overview += "<b><font color='red'>Rotation:</font></b> " + rotation + " <font color='" + keyword_color + "'>degrees about</font> " + Complex.toString2(rotation_center[0], rotation_center[1]) + "<br><br>";

        overview += "<b><font color='red'>Stretch Factor:</font></b> " + height_ratio + "<br><br>";

        if(!domain_coloring) {
            overview += "<b><font color='red'>Out Coloring Method:</font></b> " + out_coloring_modes[out_coloring_algorithm].getText() + "<br>";

            if(out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
                if(user_out_coloring_algorithm == 0) {
                    overview += tab + "out = " + outcoloring_formula + "<br>";
                }
                else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_outcoloring_conditions[0] + " > " + user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + user_outcoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_outcoloring_conditions[0] + " &#60; " + user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + user_outcoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_outcoloring_conditions[0] + " = " + user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + user_outcoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";

            overview += "<b><font color='red'>In Coloring Method:</font></b> " + in_coloring_modes[in_coloring_algorithm].getText() + "<br>";
            if(in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                if(user_in_coloring_algorithm == 0) {
                    overview += tab + "in = " + incoloring_formula + "<br>";
                }
                else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_incoloring_conditions[0] + " > " + user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + user_incoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_incoloring_conditions[0] + " &#60;" + user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + user_incoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + user_incoloring_conditions[0] + " = " + user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + user_incoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";
        }

        if(!domain_coloring || (domain_coloring && use_palette_domain_coloring)) {
            overview += "<b><font color='red'>Palette:</font></b> " + options_menu.getPalette()[color_choice].getText() + "<br><br>";
            overview += "<b><font color='red'>Palette Offset:</font></b> " + color_cycling_location + "<br><br>";
            overview += "<b><font color='red'>Color Intensity:</font></b> " + color_intensity + "<br><br>";

            if(smoothing) {
                overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                if(escaping_smooth_algorithm == 0) {
                    overview += tab + "Escaping Smooth Algorithm 1<br>";
                }
                else {
                    overview += tab + "Escaping Smooth Algorithm 2<br>";
                }
                if(converging_smooth_algorithm == 0) {
                    overview += tab + "Converging Smooth Algorithm 1<br><br>";
                }
                else {
                    overview += tab + "Converging Smooth Algorithm 2<br><br>";
                }
            }
        }

        if(!domain_coloring) {
            if(bump_map) {
                overview += "<b><font color='red'>Bump Mapping:</font></b><br>";
                overview += tab + "Light Direction = " + lightDirectionDegrees + " degrees<br>";
                overview += tab + "Depth = " + bumpMappingDepth + "<br>";
                overview += tab + "Strength = " + bumpMappingStrength + "<br>";
                overview += tab + "Noise Reduction Factor = " + bm_noise_reducing_factor + "<br><br>";
            }

            if(entropy_coloring) {
                overview += "<b><font color='red'>Entropy Coloring:</font></b><br>";
                overview += tab + "Factor = " + entropy_palette_factor + "<br>";
                overview += tab + "Offset = " + entropy_offset + "<br>";
                overview += tab + "Color Blending = " + en_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + en_noise_reducing_factor + "<br><br>";
            }

            if(offset_coloring) {
                overview += "<b><font color='red'>Offset Coloring:</font></b><br>";
                overview += tab + "Offset = " + post_process_offset + "<br>";
                overview += tab + "Color Blending = " + of_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + of_noise_reducing_factor + "<br><br>";
            }

            if(rainbow_palette) {
                overview += "<b><font color='red'>Rainbow Palette:</font></b><br>";
                overview += tab + "Factor = " + rainbow_palette_factor + "<br>";
                overview += tab + "Offset = " + rainbow_offset + "<br>";
                overview += tab + "Color Blending = " + rp_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + rp_noise_reducing_factor + "<br><br>";
            }
            
            if(greyscale_coloring) {
                overview += "<b><font color='red'>Greyscale Coloring:</font></b><br>";
                overview += tab + "Noise Reduction Factor = " + gs_noise_reducing_factor + "<br><br>";
            }

            if(exterior_de) {
                overview += "<b><font color='red'>Distance Estimation:</font></b><br>";

                if(inverse_dem) {
                    overview += tab + "Factor = " + exterior_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                }
                else {
                    overview += tab + "Factor = " + exterior_de_factor + "<br><br>";
                }
            }

            if(fake_de) {
                overview += "<b><font color='red'>Fake Distance Estimation:</font></b><br>";

                if(inverse_fake_dem) {
                    overview += tab + "Factor = " + fake_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                }
                else {
                    overview += tab + "Factor = " + fake_de_factor + "<br><br>";
                }
            }
        }

        overview += "</font></html>";

        textArea.setText(overview);

        Object[] message = {
            scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(scroll_pane, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
        main_panel.repaint();

    }

    private void showUserFormulaHelp() {

        new UserFormulasHelpDialog(main_panel, scroll_pane);

    }

    public void checkForUpdate(boolean mode) {

        String[] res = AppUpdater.checkVersion(VERSION);

        if(res[1].equals("error")) {
            if(mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(scroll_pane, message, "Software Update", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }
        else if(res[1].equals("up to date")) {

            if(mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(scroll_pane, message, "Software Update", JOptionPane.PLAIN_MESSAGE, getIcon("/fractalzoomer/icons/checkmark.png"));
                main_panel.repaint();
            }
        }
        else {
            Object[] message = {
                new LinkLabel(res[0], res[1])};

            JOptionPane.showMessageDialog(scroll_pane, message, "Software Update", JOptionPane.PLAIN_MESSAGE, getIcon("/fractalzoomer/icons/update_larger.png"));
            main_panel.repaint();
        }

    }

    public void showUsefulLinks() {

        Object[] message = {
            new LinkLabel("Fractal", "https://en.wikipedia.org/wiki/Fractal"),
            new LinkLabel("Mandelbrot Set", "https://en.wikipedia.org/wiki/Mandelbrot_set"),
            new LinkLabel("Multibrot Set", "https://en.wikipedia.org/wiki/Multibrot_set"),
            new LinkLabel("Burning Ship Fractal", "https://en.wikipedia.org/wiki/Burning_Ship_fractal"),
            new LinkLabel("Newton Fractal", "https://en.wikipedia.org/wiki/Newton_fractal"),
            new LinkLabel("Julia Set", "https://en.wikipedia.org/wiki/Julia_set"),
            new LinkLabel("Fractal Forums", "http://www.fractalforums.com"),
            new LinkLabel("New Fractal Forums", "http://www.fractalforums.org"),
            new LinkLabel("Fractal Zoomer Wiki", "https://en.wikibooks.org/wiki/Fractals/fractalzoomer")};

        JOptionPane.showMessageDialog(scroll_pane, message, "Useful Links", JOptionPane.INFORMATION_MESSAGE);
        main_panel.repaint();

    }

    private void selectPoint3D(MouseEvent e) {

        if(e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        mx0 = (int)main_panel.getMousePosition().getX();
        my0 = (int)main_panel.getMousePosition().getY();
    }

    public void defaultFilters(boolean reset_checked) {

        for(int k = 0; k < filters_options_vals.length; k++) {

            if(reset_checked) {
                filters[k] = false;
            }

            if(k == SPARKLE || k == COLOR_CHANNEL_MIXING) {
                filters_colors[k] = Color.WHITE;
            }
            else {
                filters_colors[k] = Color.BLACK;
            }

            if(k == POSTERIZE) {
                filters_options_vals[k] = 128;
            }
            else if(k == COLOR_TEMPERATURE) {
                filters_options_vals[k] = 2000;
            }
            else if(k == CONTRAST_BRIGHTNESS) {
                filters_options_vals[k] = 100100;
            }
            else if(k == COLOR_CHANNEL_ADJUSTING) {
                filters_options_vals[k] = 50050050;
            }
            else if(k == COLOR_CHANNEL_HSB_ADJUSTING) {
                filters_options_vals[k] = 50050050;
            }
            else if(k == GAMMA) {
                filters_options_vals[k] = 33;
            }
            else if(k == EXPOSURE) {
                filters_options_vals[k] = 20;
            }
            else if(k == GAIN) {
                filters_options_vals[k] = 70070;
            }
            else if(k == DITHER) {
                filters_options_vals[k] = 4;
            }
            else if(k == BLURRING) {
                filters_options_vals[k] = 5;
            }
            else if(k == COLOR_CHANNEL_SWIZZLING) {
                filters_options_vals[k] = 1057;
            }
            else if(k == CRYSTALLIZE) {
                filters_options_vals[k] = 2300015;
            }
            else if(k == GLOW) {
                filters_options_vals[k] = 10020;
            }
            else if(k == COLOR_CHANNEL_SCALING) {
                filters_options_vals[k] = 20;
            }
            else if(k == POINTILLIZE) {
                filters_options_vals[k] = 230100015;
            }
            else if(k == MARBLE) {
                filters_options_vals[k] = 401010010;
            }
            else if(k == WEAVE) {
                filters_options_vals[k] = 1002020505;
            }
            else if(k == SPARKLE) {
                filters_options_vals[k] = 40400613;
            }
            else if(k == OIL) {
                filters_options_vals[k] = 25603;
            }
            else if(k == NOISE) {
                filters_options_vals[k] = 100025;
            }
            else if(k == EMBOSS) {
                filters_options_vals[k] = 8026300;
            }
            else if(k == LIGHT_EFFECTS) {
                filters_options_vals[k] = 60260880;
                filters_options_extra_vals[0][k] = 40402640;
                filters_options_extra_vals[1][k] = 5404804;
                filters_colors[k] = Color.WHITE;
                filters_extra_colors[0][k] = new Color(0xff888888);
                filters_extra_colors[1][k] = Color.WHITE;
            }
            else {
                filters_options_vals[k] = 0;
            }
        }

        FilterOrderSelectionPanel.createDefaultFilterOrder(filters_order);
    }

    private void updateColorPalettesMenu() {

        for(i = 0; i < options_menu.getPalette().length; i++) {

            Color[] c = null;

            if(i == color_choice) { // the current activated palette
                if(i < options_menu.getPalette().length - 1) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
                }
                else {
                    c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                }
            }
            else { // the remaining palettes
                if(i < options_menu.getPalette().length - 1) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                }
                else {
                    c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg); // temp color cycling loc
                }
            }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for(int j = 0; j < c.length; j++) {
                if(smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
                }
                else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            options_menu.getPalette()[i].setIcon(new ImageIcon(palette_preview));
        }

        updatePalettePreview(color_cycling_location);

    }

    public void updatePalettePreview(int color_cycling_location) {

        Color[] c = null;
        if(color_choice < options_menu.getPalette().length - 1) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
        }
        else {
            c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
        }

        BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();
        for(int j = 0; j < c.length; j++) {
            if(smoothing) {
                GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
            }
            else {
                g.setColor(c[j]);
                g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
            }
        }

        infobar.getPalettePreview().setIcon(new ImageIcon(palette_preview));

    }

    private void updateMaxIterColorPreview() {

        BufferedImage max_it_preview = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        infobar.getMaxIterationsColorPreview().setIcon(new ImageIcon(max_it_preview));

    }

    public void savePreferences() {

        PrintWriter writer;
        try {
            writer = new PrintWriter(new File("preferences.ini"));

            writer.println("//Fractal Zoomer preferences.");
            writer.println("//This file contains all the preferences of the user and it is updated,");
            writer.println("//every time the application terminates. Settings that wont have the");
            writer.println("//correct name/number of arguments/argument value, will be ignored");
            writer.println("//and the default values will be used instead.");

            writer.println();
            writer.println();

            writer.println("[Optimizations]");
            writer.println("thread_dim " + n);
            writer.println("greedy_drawing_algorithm " + greedy_algorithm);
            writer.println("greedy_drawing_algorithm_id " + greedy_algorithm_selection);
            writer.println("skipped_pixels_coloring " + ThreadDraw.getSkippedPixelsAlgorithm());
            int color = ThreadDraw.getSkippedPixelsColor();
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("periodicity_checking " + periodicity_checking);

            writer.println();
            writer.println("[Window]");
            writer.println("image_size " + image_size);
            writer.println("window_maximized " + (getExtendedState() == JFrame.MAXIMIZED_BOTH));
            writer.println("window_size " + (int)getSize().getWidth() + " " + (int)getSize().getHeight());
            writer.println("window_location " + (int)getLocation().getX() + " " + (int)getLocation().getY());
            writer.println("window_toolbar " + options_menu.getToolbar().isSelected());
            writer.println("window_infobar " + options_menu.getInfobar().isSelected());
            writer.println("window_statusbar " + options_menu.getStatusbar().isSelected());

            writer.println();
            writer.println("[Orbit]");
            writer.println("orbit_style " + orbit_style);
            writer.println("orbit_show_root " + show_orbit_converging_point);
            writer.println("orbit_color " + orbit_color.getRed() + " " + orbit_color.getGreen() + " " + orbit_color.getBlue());

            writer.println();
            writer.println("[Boundaries]");
            writer.println("boundaries_type " + boundaries_type);
            writer.println("boundaries_number " + boundaries_num);
            writer.println("boundaries_spacing_method " + boundaries_spacing_method);
            writer.println("boundaries_color " + boundaries_color.getRed() + " " + boundaries_color.getGreen() + " " + boundaries_color.getBlue());

            writer.println();
            writer.println("[Grid]");
            writer.println("grid_tiles " + grid_tiles);
            writer.println("grid_color " + grid_color.getRed() + " " + grid_color.getGreen() + " " + grid_color.getBlue());

            writer.println();
            writer.println("[Zoom Window]");
            writer.println("zoom_window_style " + zoom_window_style);
            writer.println("zoom_window_color " + zoom_window_color.getRed() + " " + zoom_window_color.getGreen() + " " + zoom_window_color.getBlue());

            writer.println();
            writer.println("[Julia]");
            writer.println("julia_preview_filters " + fast_julia_filters);

            writer.println();
            writer.println("[Color Cycling]");
            writer.println("color_cycling_speed " + color_cycling_speed);

            writer.println();
            writer.println("[Zoom]");
            writer.println("zoom_factor " + zoom_factor);

            writer.println();
            writer.println("[Random Palette]");
            writer.println("random_palette_algorithm " + CustomPaletteEditorFrame.getRandomPaletteAlgorithm());
            writer.println("equal_hues " + CustomPaletteEditorFrame.getEqualHues());

            writer.println();
            writer.println("[Quick Draw]");
            writer.println("tiles " + ThreadDraw.getQuickDrawTileSize());

            writer.close();
        }
        catch(FileNotFoundException ex) {

        }
    }

    private void loadPreferences() {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("preferences.ini"));
            String str_line;

            while((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if(tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();

                    if(token.equals("thread_dim") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 1 && temp <= 100) {
                                n = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            greedy_algorithm = false;
                        }
                        else if(token.equals("true")) {
                            greedy_algorithm = true;
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm_id") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp == BOUNDARY_TRACING || temp == DIVIDE_AND_CONQUER) {
                                greedy_algorithm_selection = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("skipped_pixels_coloring") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 0 && temp <= 4) {
                                ThreadDraw.setSkippedPixelsAlgorithm(temp);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("skipped_pixels_user_color") && tokenizer.countTokens() == 3) {

                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                ThreadDraw.setSkippedPixelsColor(new Color(red, green, blue).getRGB());
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("periodicity_checking") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getPeriodicityChecking().isSelected()) {
                            options_menu.getPeriodicityChecking().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getPeriodicityChecking().isSelected()) {
                            options_menu.getPeriodicityChecking().doClick();
                        }
                    }
                    else if(token.equals("image_size") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 209 && temp <= 5000) {
                                image_size = temp;

                                if(image_size > 4000) {
                                    tools_menu.getOrbit().setEnabled(false);
                                    toolbar.getOrbitButton().setEnabled(false);
                                    toolbar.getOrbitButton().setSelected(false);
                                    tools_menu.getOrbit().setSelected(false);
                                    setOrbitOption();
                                }
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("window_maximized") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("true")) {
                            setExtendedState(JFrame.MAXIMIZED_BOTH);
                        }
                        else if(token.equals("false")) {
                            setExtendedState(JFrame.NORMAL);
                        }
                    }
                    else if(token.equals("window_size") && tokenizer.countTokens() == 2) {
                        try {
                            int width = Integer.parseInt(tokenizer.nextToken());
                            int height = Integer.parseInt(tokenizer.nextToken());

                            if(width > 0 && width < 5000 && height > 0 && height < 5000) {
                                setSize(width, height);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("window_location") && tokenizer.countTokens() == 2) {
                        try {
                            int x = Integer.parseInt(tokenizer.nextToken());
                            int y = Integer.parseInt(tokenizer.nextToken());

                            setLocation(x, y);
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("window_toolbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getToolbar().isSelected()) {
                            options_menu.getToolbar().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getToolbar().isSelected()) {
                            options_menu.getToolbar().doClick();
                        }
                    }
                    else if(token.equals("window_infobar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getInfobar().isSelected()) {
                            options_menu.getInfobar().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getInfobar().isSelected()) {
                            options_menu.getInfobar().doClick();
                        }
                    }
                    else if(token.equals("window_statusbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getStatusbar().isSelected()) {
                            options_menu.getStatusbar().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getStatusbar().isSelected()) {
                            options_menu.getStatusbar().doClick();
                        }
                    }
                    else if(token.equals("orbit_style") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("0")) {
                            options_menu.getLine().doClick();
                        }
                        else if(token.equals("1")) {
                            options_menu.getDot().doClick();
                        }
                    }
                    else if(token.equals("orbit_show_root") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getShowOrbitConvergingPoint().isSelected()) {
                            options_menu.getShowOrbitConvergingPoint().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getShowOrbitConvergingPoint().isSelected()) {
                            options_menu.getShowOrbitConvergingPoint().doClick();
                        }
                    }
                    else if(token.equals("orbit_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                orbit_color = new Color(red, green, blue);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("boundaries_type") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp == 0 || temp == 1 || temp == 2) {
                                boundaries_type = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("boundaries_number") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 1 && temp <= 64) {
                                boundaries_num = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("boundaries_spacing_method") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp == 0 || temp == 1) {
                                boundaries_spacing_method = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("boundaries_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                boundaries_color = new Color(red, green, blue);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("grid_tiles") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 2 && temp <= 64) {
                                grid_tiles = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("grid_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                grid_color = new Color(red, green, blue);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("zoom_window_style") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("0")) {
                            options_menu.getZoomWindowDashedLine().doClick();
                        }
                        else if(token.equals("1")) {
                            options_menu.getZoomWindowLine().doClick();
                        }
                    }
                    else if(token.equals("zoom_window_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if(red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                zoom_window_color = new Color(red, green, blue);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("julia_preview_filters") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && options_menu.getFastJuliaFiltersOptions().isSelected()) {
                            options_menu.getFastJuliaFiltersOptions().doClick();
                        }
                        else if(token.equals("true") && !options_menu.getFastJuliaFiltersOptions().isSelected()) {
                            options_menu.getFastJuliaFiltersOptions().doClick();
                        }
                    }
                    else if(token.equals("zoom_factor") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if(temp > 1.05 && temp <= 32) {
                                zoom_factor = temp;
                            }

                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("color_cycling_speed") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 0 && temp <= 1000) {
                                color_cycling_speed = temp;
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("random_palette_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 0 && temp <= 5) {
                                CustomPaletteEditorFrame.setRandomPaletteAlgorithm(temp);
                            }
                        }
                        catch(Exception ex) {
                        }
                    }
                    else if(token.equals("equal_hues") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("true")) {
                            CustomPaletteEditorFrame.setEqualHues(true);
                        }
                        else if(token.equals("false")) {
                            CustomPaletteEditorFrame.setEqualHues(false);
                        }
                    }
                    else if(token.equals("tiles") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 2 && temp <= 12) {
                                ThreadDraw.setQuickDrawTileSize(temp);
                            }
                        }
                        catch(Exception ex) {
                        }

                    }
                    else {
                        continue;
                    }
                }

            }

        }
        catch(FileNotFoundException ex) {

        }
        catch(IOException ex) {

        }
    }

    public void filtersOptionsChanged(int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean[] activeFilters) {

        if(filters_options_vals != null) {
            for(k = 0; k < filters_options_vals.length; k++) {
                this.filters_options_vals[k] = filters_options_vals[k];
                this.filters_options_extra_vals[0][k] = filters_options_extra_vals[0][k];
                this.filters_options_extra_vals[1][k] = filters_options_extra_vals[1][k];
                this.filters_colors[k] = filters_colors[k];
                this.filters_extra_colors[0][k] = filters_extra_colors[0][k];
                this.filters_extra_colors[1][k] = filters_extra_colors[1][k];
                filters[k] = activeFilters[k];
                filters_opt[k].setSelected(filters[k]);
            }

            for(k = 0; k < filters_order.length; k++) {
                this.filters_order[k] = filters_order[k];
            }
        }
        else {
            defaultFilters(false);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        if(filters[MainWindow.ANTIALIASING]) {

            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
            }

            if(julia_map) {
                createThreadsJuliaMap();
            }
            else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if(julia_map) {
                startThreads(julia_grid_first_dimension);
            }
            else {
                startThreads(n);
            }
        }
        else {
            if(d3) {
                Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                createThreadsPaletteAndFilter3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    public void customPaletteChanged(int[][] temp_custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int temp_color_cycling_location, double scale_factor_palette_val, int processing_alg) {

        for(k = 0; k < custom_palette.length; k++) {
            for(int j = 0; j < custom_palette[0].length; j++) {
                custom_palette[k][j] = temp_custom_palette[k][j];
            }
        }

        this.color_interpolation = color_interpolation;
        this.color_space = color_space;
        this.reversed_palette = reversed_palette;
        this.temp_color_cycling_location = color_cycling_location = temp_color_cycling_location;
        this.scale_factor_palette_val = scale_factor_palette_val;
        this.processing_alg = processing_alg;

    }

    private void setUserFormulaOptions() {
        if(julia) {
            julia = false;
            toolbar.getJuliaButton().setSelected(false);
            tools_menu.getJulia().setSelected(false);
            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(init_val) {
            init_val = false;
            options_menu.getInitialValue().setSelected(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(perturbation) {
            perturbation = false;
            options_menu.getPerturbation().setSelected(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(julia_map) {
            julia_map = false;
            tools_menu.getJuliaMap().setSelected(false);
            toolbar.getJuliaMapButton().setSelected(false);
            options_menu.getJuliaMapOptions().setEnabled(false);
            setOptions(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }

        }

        if(!user_formula_c) {
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
        }

        if(bail_technique == 1) {
            options_menu.getBailout().setEnabled(false);
            options_menu.getBailoutTestMenu().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }
    }

    public boolean getDomainColoring() {

        return domain_coloring;

    }

    private Object[] createUserFormulaLabels(String supported_vars) {

        JLabel variables = new JLabel("Variables:");
        variables.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel operations = new JLabel("Operations/Symbols:");
        operations.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel constants = new JLabel("Constants:");
        constants.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel complex_numbers = new JLabel("Complex Numbers:");
        complex_numbers.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel trig = new JLabel("Trigonometric Functions: f(z)");
        trig.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel other = new JLabel("Other Functions: f(z)");
        other.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel two_arg = new JLabel("Two Argument Functions: f(z, w)");
        two_arg.setFont(new Font("Arial", Font.BOLD, 11));

        JLabel multi_arg = new JLabel("Multiple Argument User Functions: f(z1, ... z10)");
        multi_arg.setFont(new Font("Arial", Font.BOLD, 11));

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showUserFormulaHelp();

            }

        });

        JButton code_editor = new JButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(getIcon("/fractalzoomer/icons/code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                codeEditor();

            }

        });

        JButton compile_code = new JButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(getIcon("/fractalzoomer/icons/compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                compileCode(true);

            }

        });

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);
        info_panel.add(code_editor);
        info_panel.add(compile_code);

        Object[] labels = {info_panel,
            variables,
            supported_vars,
            operations,
            "+ - * / % ^ ( ) ,",
            constants,
            "pi, e, phi, c10, alpha, delta",
            complex_numbers,
            "a + bi",
            trig,
            "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,",
            "acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,",
            "hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc",
            other,
            "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec,",
            "flip, round, ceil, floor, trunc, f1, ... f40",
            two_arg,
            "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, g1, ... g40",
            multi_arg,
            "m1, ... m40"
        };

        return labels;

    }

    private JLabel createUserFormulaHtmlLabels(String supported_vars, boolean mode) {

        String txt = "perturbation";
        if(mode) {
            txt = "initial value";
        }
        return new JLabel("<html><br><b>Variables:</b>"
                + "<br>" + supported_vars + "<br>"
                + "<b>Operations:</b><br>"
                + "+ - * / % ^ ( ) ,<br>"
                + "<b>Constants:</b><br>"
                + "pi, e, phi, c10, alpha, delta<br>"
                + "<b>Complex Numbers:</b><br>"
                + "a + bi<br>"
                + "<b>Trigonometric Functions: f(z)</b><br>"
                + "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,<br>"
                + "acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,<br>"
                + "hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc<br>"
                + "<b>Other Functions: f(z)</b><br>"
                + "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec,<br>"
                + "flip, round, ceil, floor, trunc, f1, ... f40<br>"
                + "<b>Two Argument Functions: f(z, w)</b><br>"
                + "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, g1, ... g40<br>"
                + "<b>Multiple Argument User Functions: f(z1, ... z10)</b><br>"
                + "m1, ... m40<br><br>"
                + "Set the variable " + txt + "."
                + "</html>");

    }

    public void compileCode(boolean show_success) {

        try {
            Parser.compileUserFunctions();
            if(show_success) {
                JOptionPane.showMessageDialog(scroll_pane, "Compilation was successful!", "Success!", JOptionPane.INFORMATION_MESSAGE, getIcon("/fractalzoomer/icons/compile_sucess.png"));
            }
        }
        catch(ParserException ex) {
            JOptionPane.showMessageDialog(scroll_pane, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, getIcon("/fractalzoomer/icons/compile_error.png"));
        }

    }

    public void codeEditor() {
        try {
            File UserDefinedFunctionsFile = new File("UserDefinedFunctions.java");

            if(UserDefinedFunctionsFile.exists()) {
                Desktop.getDesktop().open(UserDefinedFunctionsFile);
            }

        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Could not open UserDefinedFunctions.java for editing.\nMake sure that the file exists.\nIf not please restart the application.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean copyLib() {

        if(!runsOnWindows) {
            return true;
        }

        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");

        if(!path.isEmpty() && !file_separator.isEmpty()) {
            path += file_separator + "lib" + file_separator;

            if(!path.contains(file_separator + "jdk")) {
                File lib_file = new File(path + "tools.jar");

                if(!lib_file.exists()) {
                    try {
                        InputStream src = getClass().getResource("/fractalzoomer/lib/tools.jar").openStream();

                        FileOutputStream out = new FileOutputStream(lib_file);
                        byte[] temp = new byte[32768];
                        int rc;

                        while((rc = src.read(temp)) > 0) {
                            out.write(temp, 0, rc);
                        }

                        src.close();
                        out.close();
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Unable to copy tools.jar to " + path + ".\nMake sure you have administrative rights.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(scroll_pane, "Unable to copy tools.jar to the JRE lib folder.\nThe JRE installation path was not found.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public void createCompleteImage(int delay) {

        if(timer == null) {
            timer = new Timer();
            timer.schedule(new CompleteImageTask(ptr), delay);
        }

    }

    public void taskCompleteImage() {

        if(!threadsAvailable()) {
            timer.cancel();
            timer = null;

            createCompleteImage(50);
            return;
        }

        timer.cancel();
        timer = null;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);
    }

    public void setQuickDrawTiles() {
        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        final JSlider tiles_slid = new JSlider(JSlider.HORIZONTAL, 2, 12, ThreadDraw.getQuickDrawTileSize());

        tiles_slid.setPreferredSize(new Dimension(200, 35));

        tiles_slid.setToolTipText("Sets the size of the tiles.");

        tiles_slid.setPaintLabels(true);
        tiles_slid.setFocusable(false);
        tiles_slid.setMajorTickSpacing(1);

        Object[] message3 = {
            " ",
            "Set the quick draw tile size.",
            tiles_slid,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Quick Draw Tile Size", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            ThreadDraw.setQuickDrawTileSize(tiles_slid.getValue());
        }
        else {
            if(!color_cycling) {
                main_panel.repaint();
            }
            return;
        }
    }

    public void boundaryTracingOptionsChanged(boolean greedy_algorithm, int algorithm) {

        this.greedy_algorithm = greedy_algorithm;
        greedy_algorithm_selection = algorithm;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void setPoint() {

        final JTextField field_real = new JTextField();
        field_real.addAncestorListener(new RequestFocusListener());

        if(plane_transform_center[0] == 0) {
            field_real.setText("" + 0.0);
        }
        else {
            field_real.setText("" + plane_transform_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if(plane_transform_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        }
        else {
            field_imaginary.setText("" + plane_transform_center[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!current_center.isSelected()) {
                    if(plane_transform_center[0] == 0) {
                        field_real.setText("" + 0.0);
                    }
                    else {
                        field_real.setText("" + plane_transform_center[0]);
                    }

                    field_real.setEditable(true);

                    if(plane_transform_center[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    }
                    else {
                        field_imaginary.setText("" + plane_transform_center[1]);
                    }
                    field_imaginary.setEditable(true);
                }
                else {
                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(xCenter, yCenter, rotation_vals, rotation_center);

                    field_real.setText("" + p.x);
                    field_real.setEditable(false);
                    field_imaginary.setText("" + p.y);
                    field_imaginary.setEditable(false);
                }
            }
        });

        Object[] message = {
            " ",
            "Set the user point.",
            "Real:", field_real,
            "Imaginary:", field_imaginary,
            current_center, " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "User Point", JOptionPane.OK_CANCEL_OPTION);

        double tempReal, tempImaginary;

        if(res == JOptionPane.OK_OPTION) {
            try {
                tempReal = Double.parseDouble(field_real.getText());
                tempImaginary = Double.parseDouble(field_imaginary.getText());
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        else {
            main_panel.repaint();
            return;
        }

        plane_transform_center[0] = tempReal;
        plane_transform_center[1] = tempImaginary;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if(d3) {
            Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    public void setPlaneVizualization() {

        new PlaneVisualizationFrame(ptr, xCenter, yCenter, plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, max_iterations, zoom_factor);

    }

    public void displayAboutInfo() {

        if(!color_cycling) {
            main_panel.repaint();
        }

        String temp2 = "" + VERSION;
        String versionStr = "";

        int i;
        for(i = 0; i < temp2.length() - 1; i++) {
            versionStr += temp2.charAt(i) + ".";
        }
        versionStr += temp2.charAt(i);

        JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br><font size='4' face='arial'><img src=\"" + getClass().getResource("/fractalzoomer/icons/mandel2.png") + "\"><br><br>Version: <b>" + versionStr + "</b><br><br>Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE);

    }

    public void setGreedyAlgorithms() {

        new GreedyAlgorithmsFrame(ptr, greedy_algorithm, greedy_algorithm_selection);

    }

    public void openCustomPaletteEditor(int temp) {

        options_menu.getPalette()[color_choice].setSelected(true); // reselect the old palette
        customPaletteEditor(temp);

    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value != null && value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SplashFrame sf = new SplashFrame(VERSION);
        sf.setVisible(true);

        while(sf.isAnimating()) {
        }

        sf.dispose();

        MainWindow mw = new MainWindow();
        mw.setVisible(true);

        boolean actionOk = mw.copyLib();
        mw.checkForUpdate(false);
        mw.exportCodeFiles(actionOk);

    }
}
