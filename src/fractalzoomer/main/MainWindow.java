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
/* Thanks to Mika Seppa for the polar plane projection */
/* Thanks to Jerry Huxtable for many image processing algorithms */
/* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
/* Sorry for the absence of comments, this project was never supposed to reach this level! */
/* Also forgive me for the huge-packed main class, read above! */
package fractalzoomer.main;

import fractalzoomer.app_updater.AppUpdater;
import fractalzoomer.core.Complex;
import fractalzoomer.core.DrawOrbit;
import fractalzoomer.gui.MainPanel;
import fractalzoomer.gui.RequestFocusListener;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.settings.SettingsJulia;
import fractalzoomer.settings.SettingsFractals;
import fractalzoomer.palettes.Palette;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.settings.SettingsFractals1049;
import fractalzoomer.settings.SettingsFractals1050;
import fractalzoomer.settings.SettingsFractals1053;
import fractalzoomer.settings.SettingsFractals1054;
import fractalzoomer.settings.SettingsFractals1055;
import fractalzoomer.settings.SettingsFractals1056;
import fractalzoomer.settings.SettingsFractals1057;
import fractalzoomer.settings.SettingsJulia1049;
import fractalzoomer.settings.SettingsJulia1050;
import fractalzoomer.settings.SettingsJulia1053;
import fractalzoomer.settings.SettingsJulia1054;
import fractalzoomer.settings.SettingsJulia1055;
import fractalzoomer.settings.SettingsJulia1056;
import fractalzoomer.settings.SettingsJulia1057;
import fractalzoomer.gui.BailoutTestsMenu;
import fractalzoomer.gui.ColorChooserFrame;
import fractalzoomer.gui.CustomPaletteEditorFrame;
import fractalzoomer.gui.FiltersMenu;
import fractalzoomer.gui.FiltersOptionsFrame;
import fractalzoomer.gui.FractalFunctionsMenu;
import fractalzoomer.gui.InColoringModesMenu;
import fractalzoomer.gui.LinkLabel;
import fractalzoomer.gui.OutColoringModesMenu;
import fractalzoomer.gui.PlaneVisualizationFrame;
import fractalzoomer.gui.PlanesMenu;
import fractalzoomer.gui.RoundedPanel;
import fractalzoomer.gui.SplashFrame;
import fractalzoomer.gui.UserFormulasHelpDialog;
import fractalzoomer.settings.SettingsFractals1058;
import fractalzoomer.settings.SettingsFractals1061;
import fractalzoomer.settings.SettingsFractals1062;
import fractalzoomer.settings.SettingsFractals1063;
import fractalzoomer.settings.SettingsFractals1064;
import fractalzoomer.settings.SettingsJulia1058;
import fractalzoomer.settings.SettingsJulia1061;
import fractalzoomer.settings.SettingsJulia1062;
import fractalzoomer.settings.SettingsJulia1063;
import fractalzoomer.settings.SettingsJulia1064;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
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
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
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
public class MainWindow extends JFrame {

    public static final int VERSION = 1064;
    private boolean first_paint;
    private boolean[] filters;
    private int[] filters_options_vals;
    private int[][] filters_options_extra_vals;
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
    private boolean boundary_tracing;
    private boolean reversed_palette;
    private boolean apply_plane_on_julia;
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
    private long calculation_time;
    private String poly;
    private String[] coloring_option;
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
    private int user_in_coloring_algorithm;
    private int user_out_coloring_algorithm;
    private boolean shade_height;
    private int shade_choice;
    private int shade_algorithm;
    private boolean shade_invert;
    private String outcoloring_formula;
    private String[] user_outcoloring_conditions;
    private String[] user_outcoloring_condition_formula;
    private boolean[] user_outcoloring_special_color;
    private String incoloring_formula;
    private String[] user_incoloring_conditions;
    private String[] user_incoloring_condition_formula;
    private boolean[] user_incoloring_special_color;
    private BufferedImage image;
    private BufferedImage fast_julia_image;
    private BufferedImage backup_orbit;
    private BufferedImage last_used;
    private JFileChooser file_chooser;
    private MainWindow ptr;
    private MainPanel main_panel;
    private JLabel mode;
    private JLabel palette_toolbar_preview;
    private JLabel max_it_color_preview;
    private JLabel palette_toolbar_preview_lbl;
    private JLabel max_it_color_preview_lbl;
    private JScrollPane scroll_pane;
    private JToolBar toolbar;
    private JToolBar statusbar;
    private JToolBar infobar;
    private JMenuBar menubar;
    private JMenu file_menu;
    private JMenu options_menu;
    private JMenu fractal_options_menu;
    private JMenu colors_menu;
    private JMenu palette_menu;
    private JMenu roll_palette_menu;
    private JMenu iterations_menu;
    private BailoutTestsMenu bailout_test_menu;
    private JMenu rotation_menu;
    private FiltersMenu filters_menu;
    private PlanesMenu planes_menu;
    private JMenu optimizations_menu;
    private JMenu tools_options_menu;
    private JMenu grid_menu;
    private JMenuItem filters_options;
    private JMenu window_menu;
    private JMenu orbit_menu;
    private JMenu orbit_style_menu;
    private JMenu zoom_window_menu;
    private JMenu zoom_window_style_menu;
    private JMenu tools_menu;
    private JMenu boundaries_menu;
    private OutColoringModesMenu out_coloring_mode_menu;
    private InColoringModesMenu in_coloring_mode_menu;
    private JMenu help_menu;
    private FractalFunctionsMenu fractal_functions_menu;
    private JMenuItem help_contents;
    private JMenuItem size_of_image;
    private JMenuItem iterations;
    private JMenuItem increase_iterations;
    private JMenuItem decrease_iterations;
    private JMenuItem height_ratio_number;
    private JMenuItem set_rotation;
    private JMenuItem increase_rotation;
    private JMenuItem decrease_rotation;
    private JMenuItem thread_number;
    private JMenuItem bailout_number;
    private JMenuItem fract_color;
    private JMenuItem random_palette;
    private JMenuItem roll_palette;
    private JMenuItem increase_roll_palette;
    private JMenuItem decrease_roll_palette;
    private JMenuItem starting_position;
    private JMenuItem color_intensity_opt;
    private JMenuItem exit;
    private JMenuItem save_image;
    private JMenuItem about;
    private JMenuItem update;
    private JMenuItem useful_links;
    private JMenuItem go_to;
    private JMenuItem save_settings;
    private JMenuItem load_settings;
    private JMenuItem code_editor;
    private JMenuItem compile_code;
    private JMenuItem change_zooming_factor;
    private JMenuItem zoom_in;
    private JMenuItem zoom_out;
    private JMenuItem up;
    private JMenuItem down;
    private JMenuItem left;
    private JMenuItem right;
    private JMenuItem orbit_color_opt;
    private JMenuItem grid_tiles_opt;
    private JMenuItem grid_color_opt;
    private JMenuItem boundaries_number_opt;
    private JMenuItem boundaries_color_opt;
    private JMenuItem zoom_window_color_opt;
    private JMenuItem d3_details_opt;
    private JMenuItem domain_coloring_options_opt;
    private JMenuItem julia_map_options_opt;
    private JMenuItem polar_projection_options_opt;
    private JMenuItem bump_map_opt;
    private JMenuItem rainbow_palette_opt;
    private JMenuItem fake_de_opt;
    private JMenuItem overview_opt;
    private JMenuItem color_cycling_options_opt;
    private JMenuItem plane_visualization_opt;
    private JCheckBoxMenuItem burning_ship_opt;
    private JCheckBoxMenuItem mandel_grass_opt;
    private JCheckBoxMenuItem d3_opt;
    private JCheckBoxMenuItem boundary_tracing_opt;
    private JCheckBoxMenuItem[] filters_opt;
    private JCheckBoxMenuItem orbit_opt;
    private JCheckBoxMenuItem julia_opt;
    private JCheckBoxMenuItem polar_projection_opt;
    private JCheckBoxMenuItem fast_julia_filters_opt;
    private JCheckBoxMenuItem periodicity_checking_opt;
    private JCheckBoxMenuItem color_cycling_opt;
    private JCheckBoxMenuItem julia_map_opt;
    private JCheckBoxMenuItem domain_coloring_opt;
    private JCheckBoxMenuItem grid_opt;
    private JCheckBoxMenuItem boundaries_opt;
    private JCheckBoxMenuItem zoom_window_opt;
    private JCheckBoxMenuItem perturbation_opt;
    private JCheckBoxMenuItem init_val_opt;
    private JCheckBoxMenuItem toolbar_opt;
    private JCheckBoxMenuItem statusbar_opt;
    private JCheckBoxMenuItem infobar_opt;
    private JCheckBoxMenuItem apply_plane_on_julia_opt;
    private JCheckBoxMenuItem show_orbit_converging_point_opt;
    private JMenuItem smoothing_opt;
    private JMenuItem exterior_de_opt;
    private JRadioButtonMenuItem line;
    private JRadioButtonMenuItem dot;
    private JRadioButtonMenuItem zoom_window_dashed_line;
    private JRadioButtonMenuItem zoom_window_line;
    private JRadioButtonMenuItem fractal_functions[];
    private JRadioButtonMenuItem[] palette;
    private JRadioButtonMenuItem[] planes;
    private JRadioButtonMenuItem[] out_coloring_modes;
    private JRadioButtonMenuItem[] in_coloring_modes;
    private JRadioButtonMenuItem[] bailout_tests;
    private JButton starting_position_button;
    private JButton zoom_in_button;
    private JButton zoom_out_button;
    private JButton save_image_button;
    private JButton custom_palette_button;
    private JButton random_palette_button;
    private JButton iterations_button;
    private JButton rotation_button;
    private JButton filters_options_button;
    private JButton orbit_button;
    private JButton julia_button;
    private JButton polar_projection_button;
    private JButton color_cycling_button;
    private JButton d3_button;
    private JButton julia_map_button;
    private JButton domain_coloring_button;
    private JButton overview_button;
    private JButton help_button;
    private JFrame fract_color_frame;
    private JProgressBar progress;
    private JTextField real;
    private JTextField imaginary;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private int temp_color_cycling_location;

    private int i, k;
    public static final int FAST_JULIA_IMAGE_SIZE = 252;

    /**
     * ** Custom Palettes ***
     */
    private int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    /**
     * *****************************
     */

    /**
     * ** FUNCTION ***
     */
    public static final int MANDELBROT = 0;
    public static final int MANDELBROTCUBED = 1;
    public static final int MANDELBROTFOURTH = 2;
    public static final int MANDELBROTFIFTH = 3;
    public static final int MANDELBROTSIXTH = 4;
    public static final int MANDELBROTSEVENTH = 5;
    public static final int MANDELBROTEIGHTH = 6;
    public static final int MANDELBROTNINTH = 7;
    public static final int MANDELBROTTENTH = 8;
    public static final int MANDELBROTNTH = 9;
    public static final int LAMBDA = 10;
    public static final int MAGNET1 = 11;
    public static final int MAGNET2 = 12;
    public static final int NEWTON3 = 13;
    public static final int NEWTON4 = 14;
    public static final int NEWTONGENERALIZED3 = 15;
    public static final int NEWTONGENERALIZED8 = 16;
    public static final int NEWTONSIN = 17;
    public static final int NEWTONCOS = 18;
    public static final int NEWTONPOLY = 19;
    public static final int BARNSLEY1 = 20;
    public static final int BARNSLEY2 = 21;
    public static final int BARNSLEY3 = 22;
    public static final int MANDELBAR = 23;
    public static final int SPIDER = 24;
    public static final int PHOENIX = 25;
    public static final int SIERPINSKI_GASKET = 26;
    public static final int HALLEY3 = 27;
    public static final int HALLEY4 = 28;
    public static final int HALLEYGENERALIZED3 = 29;
    public static final int HALLEYGENERALIZED8 = 30;
    public static final int HALLEYSIN = 31;
    public static final int HALLEYCOS = 32;
    public static final int HALLEYPOLY = 33;
    public static final int SCHRODER3 = 34;
    public static final int SCHRODER4 = 35;
    public static final int SCHRODERGENERALIZED3 = 36;
    public static final int SCHRODERGENERALIZED8 = 37;
    public static final int SCHRODERSIN = 38;
    public static final int SCHRODERCOS = 39;
    public static final int SCHRODERPOLY = 40;
    public static final int HOUSEHOLDER3 = 41;
    public static final int HOUSEHOLDER4 = 42;
    public static final int HOUSEHOLDERGENERALIZED3 = 43;
    public static final int HOUSEHOLDERGENERALIZED8 = 44;
    public static final int HOUSEHOLDERSIN = 45;
    public static final int HOUSEHOLDERCOS = 46;
    public static final int HOUSEHOLDERPOLY = 47;
    public static final int SECANT3 = 48;
    public static final int SECANT4 = 49;
    public static final int SECANTGENERALIZED3 = 50;
    public static final int SECANTGENERALIZED8 = 51;
    public static final int SECANTCOS = 52;
    public static final int SECANTPOLY = 53;
    public static final int STEFFENSEN3 = 54;
    public static final int STEFFENSEN4 = 55;
    public static final int STEFFENSENGENERALIZED3 = 56;
    public static final int MANDELPOLY = 57;
    public static final int MANOWAR = 58;
    public static final int FROTHY_BASIN = 59;
    public static final int MANDELBROTWTH = 60;
    public static final int SZEGEDI_BUTTERFLY1 = 61;
    public static final int SZEGEDI_BUTTERFLY2 = 62;
    public static final int NOVA = 63;
    public static final int EXP = 64;
    public static final int LOG = 65;
    public static final int SIN = 66;
    public static final int COS = 67;
    public static final int TAN = 68;
    public static final int COT = 69;
    public static final int SINH = 70;
    public static final int COSH = 71;
    public static final int TANH = 72;
    public static final int COTH = 73;
    public static final int FORMULA1 = 74;
    public static final int FORMULA2 = 75;
    public static final int FORMULA3 = 76;
    public static final int FORMULA4 = 77;
    public static final int FORMULA5 = 78;
    public static final int FORMULA6 = 79;
    public static final int FORMULA7 = 80;
    public static final int FORMULA8 = 81;
    public static final int FORMULA9 = 82;
    public static final int FORMULA10 = 83;
    public static final int FORMULA11 = 84;
    public static final int FORMULA12 = 85;
    public static final int FORMULA13 = 86;
    public static final int FORMULA14 = 87;
    public static final int FORMULA15 = 88;
    public static final int FORMULA16 = 89;
    public static final int FORMULA17 = 90;
    public static final int FORMULA18 = 91;
    public static final int FORMULA19 = 92;
    public static final int FORMULA20 = 93;
    public static final int FORMULA21 = 94;
    public static final int FORMULA22 = 95;
    public static final int FORMULA23 = 96;
    public static final int FORMULA24 = 97;
    public static final int FORMULA25 = 98;
    public static final int FORMULA26 = 99;
    public static final int FORMULA27 = 100;
    public static final int FORMULA28 = 101;
    public static final int FORMULA29 = 102;
    public static final int FORMULA30 = 103;
    public static final int FORMULA31 = 104;
    public static final int FORMULA32 = 105;
    public static final int FORMULA33 = 106;
    public static final int FORMULA34 = 107;
    public static final int FORMULA35 = 108;
    public static final int FORMULA36 = 109;
    public static final int FORMULA37 = 110;
    public static final int FORMULA38 = 111;
    public static final int FORMULA39 = 112;
    public static final int USER_FORMULA = 113;
    public static final int USER_FORMULA_ITERATION_BASED = 114;
    public static final int USER_FORMULA_CONDITIONAL = 115;
    public static final int FORMULA40 = 116;
    public static final int FORMULA41 = 117;
    public static final int FORMULA42 = 118;
    public static final int FORMULA43 = 119;
    public static final int FORMULA44 = 120;
    public static final int FORMULA45 = 121;
    public static final int FORMULA46 = 122;
    public static final int NEWTONFORMULA = 123;
    public static final int HALLEYFORMULA = 124;
    public static final int SCHRODERFORMULA = 125;
    public static final int HOUSEHOLDERFORMULA = 126;
    public static final int SECANTFORMULA = 127;
    public static final int STEFFENSENFORMULA = 128;
    public static final int STEFFENSENPOLY = 129;
    public static final int COUPLED_MANDELBROT = 130;
    public static final int COUPLED_MANDELBROT_BURNING_SHIP = 131;
    public static final int USER_FORMULA_COUPLED = 132;
    public static final int MULLER3 = 133;
    public static final int MULLER4 = 134;
    public static final int MULLERGENERALIZED3 = 135;
    public static final int MULLERGENERALIZED8 = 136;
    public static final int MULLERSIN = 137;
    public static final int MULLERCOS = 138;
    public static final int MULLERPOLY = 139;
    public static final int MULLERFORMULA = 140;
    /**
     * ***************
     */

    /**
     * ** NOVA VARIANT ***
     */
    public static final int NOVA_NEWTON = 0;
    public static final int NOVA_HALLEY = 1;
    public static final int NOVA_SCHRODER = 2;
    public static final int NOVA_HOUSEHOLDER = 3;
    public static final int NOVA_SECANT = 4;
    public static final int NOVA_STEFFENSEN = 5;
    public static final int NOVA_MULLER = 6;
    /**
     * *******************
     */

    /**
     * ** OUT COLORING MODE ***
     */
    public static final int ESCAPE_TIME = 0;
    public static final int BINARY_DECOMPOSITION = 1;
    public static final int BINARY_DECOMPOSITION2 = 2;
    public static final int ITERATIONS_PLUS_RE = 3;
    public static final int ITERATIONS_PLUS_IM = 4;
    public static final int ITERATIONS_PLUS_RE_DIVIDE_IM = 5;
    public static final int ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM = 6;
    public static final int BIOMORPH = 7;
    public static final int COLOR_DECOMPOSITION = 8;
    public static final int ESCAPE_TIME_COLOR_DECOMPOSITION = 9;
    public static final int ESCAPE_TIME_GAUSSIAN_INTEGER = 10;
    public static final int ESCAPE_TIME_GAUSSIAN_INTEGER2 = 11;
    public static final int ESCAPE_TIME_GAUSSIAN_INTEGER3 = 12;
    public static final int ESCAPE_TIME_GAUSSIAN_INTEGER4 = 13;
    public static final int ESCAPE_TIME_GAUSSIAN_INTEGER5 = 14;
    public static final int ESCAPE_TIME_ALGORITHM = 15;
    public static final int ESCAPE_TIME_ALGORITHM2 = 16;
    public static final int DISTANCE_ESTIMATOR = 17;
    public static final int ESCAPE_TIME_ESCAPE_RADIUS = 18;
    public static final int ESCAPE_TIME_GRID = 19;
    public static final int ATOM_DOMAIN = 20;
    public static final int BANDED = 21;
    public static final int USER_OUTCOLORING_ALGORITHM = 22;
    /**
     * ***************************
     */

    /**
     * ** IN COLORING MODE ***
     */
    public static final int MAXIMUM_ITERATIONS = 0;
    public static final int Z_MAG = 1;
    public static final int DECOMPOSITION_LIKE = 2;
    public static final int RE_DIVIDE_IM = 3;
    public static final int COS_MAG = 4;
    public static final int MAG_TIMES_COS_RE_SQUARED = 5;
    public static final int SIN_RE_SQUARED_MINUS_IM_SQUARED = 6;
    public static final int ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM = 7;
    public static final int SQUARES = 8;
    public static final int SQUARES2 = 9;
    public static final int USER_INCOLORING_ALGORITHM = 10;
    /**
     * *************************
     */

    /**
     * ** PLANES ***
     */
    public static final int MU_PLANE = 0;
    public static final int MU_SQUARED_PLANE = 1;
    public static final int MU_SQUARED_IMAGINARY_PLANE = 2;
    public static final int INVERSED_MU_PLANE = 3;
    public static final int INVERSED_MU2_PLANE = 4;
    public static final int INVERSED_MU3_PLANE = 5;
    public static final int INVERSED_MU4_PLANE = 6;
    public static final int LAMBDA_PLANE = 7;
    public static final int INVERSED_LAMBDA_PLANE = 8;
    public static final int INVERSED_LAMBDA2_PLANE = 9;
    public static final int EXP_PLANE = 10;
    public static final int LOG_PLANE = 11;
    public static final int SIN_PLANE = 12;
    public static final int COS_PLANE = 13;
    public static final int TAN_PLANE = 14;
    public static final int COT_PLANE = 15;
    public static final int SINH_PLANE = 16;
    public static final int COSH_PLANE = 17;
    public static final int TANH_PLANE = 18;
    public static final int COTH_PLANE = 19;
    public static final int SQRT_PLANE = 20;
    public static final int ABS_PLANE = 21;
    public static final int FOLDUP_PLANE = 22;
    public static final int FOLDRIGHT_PLANE = 23;
    public static final int FOLDIN_PLANE = 24;
    public static final int FOLDOUT_PLANE = 25;
    public static final int ASIN_PLANE = 26;
    public static final int ACOS_PLANE = 27;
    public static final int ATAN_PLANE = 28;
    public static final int ACOT_PLANE = 29;
    public static final int ASINH_PLANE = 30;
    public static final int ACOSH_PLANE = 31;
    public static final int ATANH_PLANE = 32;
    public static final int ACOTH_PLANE = 33;
    public static final int SEC_PLANE = 34;
    public static final int CSC_PLANE = 35;
    public static final int SECH_PLANE = 36;
    public static final int CSCH_PLANE = 37;
    public static final int ASEC_PLANE = 38;
    public static final int ACSC_PLANE = 39;
    public static final int ASECH_PLANE = 40;
    public static final int ACSCH_PLANE = 41;
    public static final int NEWTON3_PLANE = 42;
    public static final int NEWTON4_PLANE = 43;
    public static final int NEWTONGENERALIZED3_PLANE = 44;
    public static final int NEWTONGENERALIZED8_PLANE = 45;
    public static final int USER_PLANE = 46;
    public static final int GAMMA_PLANE = 47;
    public static final int FACT_PLANE = 48;
    public static final int BIPOLAR_PLANE = 49;
    public static final int INVERSED_BIPOLAR_PLANE = 50;
    public static final int TWIRL_PLANE = 51;
    public static final int SHEAR_PLANE = 52;
    public static final int KALEIDOSCOPE_PLANE = 53;
    public static final int PINCH_PLANE = 54;
    public static final int FOLDDOWN_PLANE = 55;
    public static final int FOLDLEFT_PLANE = 56;
    public static final int CIRCLEINVERSION_PLANE = 57;
    public static final int VARIATION_MU_PLANE = 58;
    public static final int ERF_PLANE = 59;
    public static final int RZETA_PLANE = 60;
    public static final int INFLECTION_PLANE = 61;
    /**
     * **************
     */

    /**
     * ** BAILOUT TESTS ***
     */
    public static final int BAILOUT_TEST_CIRCLE = 0;
    public static final int BAILOUT_TEST_SQUARE = 1;
    public static final int BAILOUT_TEST_RHOMBUS = 2;
    public static final int BAILOUT_TEST_NNORM = 3;
    public static final int BAILOUT_TEST_STRIP = 4;
    public static final int BAILOUT_TEST_HALFPLANE = 5;
    public static final int BAILOUT_TEST_USER = 6;
    /**
     * ********************
     */

    /**
     * ** IMAGE FILTERS ***
     */
    public static final int ANTIALIASING = 0;
    public static final int EDGE_DETECTION = 1;
    public static final int EMBOSS = 2;
    public static final int SHARPNESS = 3;
    public static final int INVERT_COLORS = 4;
    public static final int BLURRING = 5;
    public static final int COLOR_CHANNEL_MASKING = 6;
    public static final int FADE_OUT = 7;
    public static final int COLOR_CHANNEL_SWAPPING = 8;
    public static final int CONTRAST_BRIGHTNESS = 9;
    public static final int GRAYSCALE = 10;
    public static final int COLOR_TEMPERATURE = 11;
    public static final int COLOR_CHANNEL_SWIZZLING = 12;
    public static final int HISTOGRAM_EQUALIZATION = 13;
    public static final int POSTERIZE = 14;
    public static final int SOLARIZE = 15;
    public static final int COLOR_CHANNEL_ADJUSTING = 16;
    public static final int COLOR_CHANNEL_HSB_ADJUSTING = 17;
    public static final int DITHER = 18;
    public static final int GAIN = 19;
    public static final int GAMMA = 20;
    public static final int EXPOSURE = 21;
    public static final int CRYSTALLIZE = 22;
    public static final int POINTILLIZE = 23;
    public static final int OIL = 24;
    public static final int MARBLE = 25;
    public static final int WEAVE = 26;
    public static final int SPARKLE = 27;
    public static final int GLOW = 28;
    public static final int COLOR_CHANNEL_SCALING = 29;
    public static final int NOISE = 30;
    public static final int COLOR_CHANNEL_MIXING = 31;
    public static final int LIGHT_EFFECTS = 32;
    /**
     * *****************
     */

    /**
     * ** COLOR SPACE ***
     */
    public static final int COLOR_SPACE_RGB = 0;
    public static final int COLOR_SPACE_HSB = 1;
    public static final int COLOR_SPACE_EXP = 2;
    public static final int COLOR_SPACE_SQUARE = 3;
    public static final int COLOR_SPACE_SQRT = 4;
    public static final int COLOR_SPACE_RYB = 5;
    public static final int COLOR_SPACE_LAB = 6;
    public static final int COLOR_SPACE_XYZ = 7;
    public static final int COLOR_SPACE_LCH = 8;
    /**
     * *******************
     */

    /**
     * ** COLOR INTERPOLATION ***
     */
    public static final int INTERPOLATION_LINEAR = 0;
    public static final int INTERPOLATION_COSINE = 1;
    public static final int INTERPOLATION_ACCELERATION = 2;
    public static final int INTERPOLATION_DECELERATION = 3;
    public static final int INTERPOLATION_EXPONENTIAL = 4;
    public static final int INTERPOLATION_CATMULLROM = 5;
    public static final int INTERPOLATION_CATMULLROM2 = 6;
    public static final int INTERPOLATION_SIGMOID = 7;
    /**
     * **************************
     */

    /**
     * ** MOVE DIRECTIONS ***
     */
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    /**
     * **********************
     */

    /**
     * ** PALETTE PROCESSING ***
     */
    public static final int PROCESSING_NONE = 0;
    public static final int PROCESSING_HISTOGRAM = 1;
    public static final int PROCESSING_BRIGHTNESS1 = 2;
    public static final int PROCESSING_BRIGHTNESS2 = 3;
    public static final int PROCESSING_HUE1 = 4;
    public static final int PROCESSING_HUE2 = 5;
    public static final int PROCESSING_SATURATION1 = 6;
    public static final int PROCESSING_SATURATION2 = 7;
    public static final int PROCESSING_RED1 = 8;
    public static final int PROCESSING_RED2 = 9;
    public static final int PROCESSING_GREEN1 = 10;
    public static final int PROCESSING_GREEN2 = 11;
    public static final int PROCESSING_BLUE1 = 12;
    public static final int PROCESSING_BLUE2 = 13;
    public static final int PROCESSING_RGB1 = 14;
    public static final int PROCESSING_RGB2 = 15;
    public static final int PROCESSING_HSB1 = 16;
    public static final int PROCESSING_HSB2 = 17;
    public static final int PROCESSING_RYB1 = 18;
    public static final int PROCESSING_RYB2 = 19;
    /**
     * *************************
     */

    public static final Color bg_color = new Color(255, 255, 240);

    public MainWindow() {

        super();

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

        n_norm = 0;

        filters = new boolean[33];

        filters_options_vals = new int[filters.length];
        filters_options_extra_vals = new int[2][filters.length];

        filters_colors = new Color[filters.length];
        filters_extra_colors = new Color[2][filters.length];

        fiX = 0.64;
        fiY = 0.82;
        dfi = 0.01;

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

        boundary_tracing = true;

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

        gaussian_scaling = false;
        gaussian_weight = 2;
        gaussian_kernel = 1;

        fake_de = false;
        fake_de_factor = 1;
        inverse_fake_dem = false;

        rainbow_palette = false;
        rainbow_palette_factor = 1;

        first_paint = false;

        plane_type = MU_PLANE;
        out_coloring_algorithm = ESCAPE_TIME;
        in_coloring_algorithm = MAXIMUM_ITERATIONS;

        shade_height = false;
        shade_choice = 0;
        shade_algorithm = 0;
        shade_invert = false;

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

        user_outcoloring_special_color = new boolean[3];
        user_outcoloring_special_color[0] = false;
        user_outcoloring_special_color[1] = false;
        user_outcoloring_special_color[2] = false;

        incoloring_formula = "norm(sin(z)) * 100";

        user_incoloring_conditions = new String[2];
        user_incoloring_conditions[0] = "im(z)";
        user_incoloring_conditions[1] = "0.0";

        user_incoloring_condition_formula = new String[3];
        user_incoloring_condition_formula[0] = "norm(sin(z)) * 100";
        user_incoloring_condition_formula[1] = "norm(sin(z)) * 100 + 50";
        user_incoloring_condition_formula[2] = "norm(sin(z)) * 100";

        user_incoloring_special_color = new boolean[3];
        user_incoloring_special_color[0] = false;
        user_incoloring_special_color[1] = false;
        user_incoloring_special_color[2] = false;

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

        if(System.getProperty("os.name").equals("Windows XP")) {
            image_size = 788;
            setSize(798, 845);
        }
        else if(System.getProperty("os.name").equals("Windows 7")) {
            image_size = 788;
            setSize(806, 849);
        }
        else {
            image_size = 788;
            setSize(806, 849);
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

                if(!color_cycling) {
                    main_panel.repaint();
                }
                int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
                if(ans == JOptionPane.YES_OPTION) {
                    saveSettings();
                    savePreferences();
                    System.exit(0);
                }
                else {
                    if(ans == JOptionPane.NO_OPTION) {
                        savePreferences();
                        System.exit(0);
                    }
                }

            }
        });

        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        menubar = new JMenuBar();

        file_menu = new JMenu("File");

        starting_position = new JMenuItem("Starting Position", getIcon("/fractalzoomer/icons/starting_position.png"));

        go_to = new JMenuItem("Go To", getIcon("/fractalzoomer/icons/go_to.png"));

        zoom_in = new JMenuItem("Zoom In", getIcon("/fractalzoomer/icons/zoom_in.png"));

        zoom_out = new JMenuItem("Zoom Out", getIcon("/fractalzoomer/icons/zoom_out.png"));

        up = new JMenuItem("Up", getIcon("/fractalzoomer/icons/up.png"));
        down = new JMenuItem("Down", getIcon("/fractalzoomer/icons/down.png"));
        left = new JMenuItem("Left", getIcon("/fractalzoomer/icons/left.png"));
        right = new JMenuItem("Right", getIcon("/fractalzoomer/icons/right.png"));

        save_settings = new JMenuItem("Save As...", getIcon("/fractalzoomer/icons/save.png"));

        load_settings = new JMenuItem("Load", getIcon("/fractalzoomer/icons/load.png"));

        save_image = new JMenuItem("Save Image As...", getIcon("/fractalzoomer/icons/save_image.png"));

        code_editor = new JMenuItem("Edit User Code", getIcon("/fractalzoomer/icons/code_editor.png"));
        compile_code = new JMenuItem("Compile User Code", getIcon("/fractalzoomer/icons/compile.png"));

        exit = new JMenuItem("Exit", getIcon("/fractalzoomer/icons/exit.png"));

        options_menu = new JMenu("Options");

        fractal_options_menu = new JMenu("Fractal Options");
        fractal_options_menu.setIcon(getIcon("/fractalzoomer/icons/fractal_options.png"));

        fractal_functions_menu = new FractalFunctionsMenu(ptr, "Fractal Functions");
        fractal_functions_menu.setIcon(getIcon("/fractalzoomer/icons/functions.png"));
        fractal_functions = fractal_functions_menu.getFractalFunctions();
        burning_ship_opt = fractal_functions_menu.getBurningShipOpt();
        mandel_grass_opt = fractal_functions_menu.getMandelGrassOpt();

        planes_menu = new PlanesMenu(ptr, "Planes");
        planes_menu.setIcon(getIcon("/fractalzoomer/icons/planes.png"));
        planes = planes_menu.getPlanes();
        apply_plane_on_julia_opt = planes_menu.getApplyPlaneOnJuliaOpt();

        size_of_image = new JMenuItem("Image Size", getIcon("/fractalzoomer/icons/image_size.png"));

        perturbation_opt = new JCheckBoxMenuItem("Perturbation");
        init_val_opt = new JCheckBoxMenuItem("Initial Value");

        iterations_menu = new JMenu("Iterations");
        iterations_menu.setIcon(getIcon("/fractalzoomer/icons/iterations.png"));

        increase_iterations = new JMenuItem("Increase Iterations", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_iterations = new JMenuItem("Decrease Iterations", getIcon("/fractalzoomer/icons/minus.png"));

        iterations = new JMenuItem("Set Iterations", getIcon("/fractalzoomer/icons/iterations.png"));

        bailout_test_menu = new BailoutTestsMenu(ptr, "Bailout Test");
        bailout_test_menu.setIcon(getIcon("/fractalzoomer/icons/bailout_tests.png"));
        bailout_tests = bailout_test_menu.getBailoutTests();

        bailout_number = new JMenuItem("Bailout", getIcon("/fractalzoomer/icons/bailout.png"));

        rotation_menu = new JMenu("Rotation");
        rotation_menu.setIcon(getIcon("/fractalzoomer/icons/rotate.png"));

        set_rotation = new JMenuItem("Set Rotation", getIcon("/fractalzoomer/icons/rotate.png"));

        increase_rotation = new JMenuItem("Increase Rotation", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_rotation = new JMenuItem("Decrease Rotation", getIcon("/fractalzoomer/icons/minus.png"));

        change_zooming_factor = new JMenuItem("Zooming Factor", getIcon("/fractalzoomer/icons/zooming_factor.png"));

        height_ratio_number = new JMenuItem("Stretch Factor", getIcon("/fractalzoomer/icons/stretch.png"));

        thread_number = new JMenuItem("Threads", getIcon("/fractalzoomer/icons/threads.png"));

        optimizations_menu = new JMenu("Optimizations");
        optimizations_menu.setIcon(getIcon("/fractalzoomer/icons/optimizations.png"));

        boundary_tracing_opt = new JCheckBoxMenuItem("Boundary Tracing");
        boundary_tracing_opt.setSelected(true);

        periodicity_checking_opt = new JCheckBoxMenuItem("Periodicity Checking");

        tools_options_menu = new JMenu("Tools Options");
        tools_options_menu.setIcon(getIcon("/fractalzoomer/icons/tools_options.png"));

        filters_options = new JMenuItem("Filters Options", getIcon("/fractalzoomer/icons/filter_options.png"));

        window_menu = new JMenu("Window");
        window_menu.setIcon(getIcon("/fractalzoomer/icons/window.png"));

        toolbar_opt = new JCheckBoxMenuItem("Tool Bar");
        statusbar_opt = new JCheckBoxMenuItem("Status Bar");
        infobar_opt = new JCheckBoxMenuItem("Information Bar");

        orbit_menu = new JMenu("Orbit");
        orbit_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_options.png"));

        grid_menu = new JMenu("Grid");
        grid_menu.setIcon(getIcon("/fractalzoomer/icons/grid_options.png"));

        boundaries_menu = new JMenu("Boundaries");
        boundaries_menu.setIcon(getIcon("/fractalzoomer/icons/boundaries_options.png"));

        orbit_color_opt = new JMenuItem("Orbit Color", getIcon("/fractalzoomer/icons/color.png"));

        orbit_style_menu = new JMenu("Orbit Style");
        orbit_style_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_style.png"));

        show_orbit_converging_point_opt = new JCheckBoxMenuItem("Show Converging Point");

        grid_color_opt = new JMenuItem("Grid Color", getIcon("/fractalzoomer/icons/color.png"));

        grid_tiles_opt = new JMenuItem("Grid Tiles", getIcon("/fractalzoomer/icons/grid_tiles.png"));

        zoom_window_color_opt = new JMenuItem("Zoom Window Color", getIcon("/fractalzoomer/icons/color.png"));

        zoom_window_menu = new JMenu("Zoom Window");
        zoom_window_menu.setIcon(getIcon("/fractalzoomer/icons/zoom_window_options.png"));

        zoom_window_style_menu = new JMenu("Zoom Window Style");
        zoom_window_style_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_style.png"));

        boundaries_color_opt = new JMenuItem("Boundaries Color", getIcon("/fractalzoomer/icons/color.png"));

        boundaries_number_opt = new JMenuItem("Boundaries Options", getIcon("/fractalzoomer/icons/boundaries_settings.png"));

        fast_julia_filters_opt = new JCheckBoxMenuItem("Julia Preview Image Filters");

        d3_details_opt = new JMenuItem("3D Options", getIcon("/fractalzoomer/icons/3d_options.png"));

        domain_coloring_options_opt = new JMenuItem("Domain Coloring Options", getIcon("/fractalzoomer/icons/domain_coloring_options.png"));

        julia_map_options_opt = new JMenuItem("Julia Map Options", getIcon("/fractalzoomer/icons/julia_map_options.png"));

        polar_projection_options_opt = new JMenuItem("Polar Projection Options", getIcon("/fractalzoomer/icons/polar_projection_options.png"));

        color_cycling_options_opt = new JMenuItem("Color Cycling Options", getIcon("/fractalzoomer/icons/color_cycling_options.png"));

        colors_menu = new JMenu("Colors");
        colors_menu.setIcon(getIcon("/fractalzoomer/icons/colors_menu.png"));

        fract_color = new JMenuItem("Fractal Colors", getIcon("/fractalzoomer/icons/color.png"));

        palette_menu = new JMenu("Palette");
        palette_menu.setIcon(getIcon("/fractalzoomer/icons/palette.png"));

        color_intensity_opt = new JMenuItem("Color Intensity", getIcon("/fractalzoomer/icons/color_intensity.png"));

        random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette_random.png"));

        out_coloring_mode_menu = new OutColoringModesMenu(ptr, "Out Coloring Mode");
        out_coloring_mode_menu.setIcon(getIcon("/fractalzoomer/icons/out_coloring_mode.png"));
        out_coloring_modes = out_coloring_mode_menu.getOutColoringModes();

        in_coloring_mode_menu = new InColoringModesMenu(ptr, "In Coloring Mode");
        in_coloring_mode_menu.setIcon(getIcon("/fractalzoomer/icons/in_coloring_mode.png"));
        in_coloring_modes = in_coloring_mode_menu.getInColoringModes();

        smoothing_opt = new JMenuItem("Smoothing", getIcon("/fractalzoomer/icons/smoothing.png"));
        exterior_de_opt = new JMenuItem("Distance Estimation", getIcon("/fractalzoomer/icons/distance_estimation.png"));
        bump_map_opt = new JMenuItem("Bump Mapping", getIcon("/fractalzoomer/icons/bump_map.png"));
        fake_de_opt = new JMenuItem("Fake Distance Estimation", getIcon("/fractalzoomer/icons/fake_distance_estimation.png"));
        rainbow_palette_opt = new JMenuItem("Rainbow Palette", getIcon("/fractalzoomer/icons/rainbow_palette.png"));

        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(getIcon("/fractalzoomer/icons/shift_palette.png"));

        roll_palette = new JMenuItem("Shift Palette", getIcon("/fractalzoomer/icons/shift_palette.png"));

        increase_roll_palette = new JMenuItem("Shift Palette Forward", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_roll_palette = new JMenuItem("Shift Palette Backward", getIcon("/fractalzoomer/icons/minus.png"));

        overview_opt = new JMenuItem("Options Overview", getIcon("/fractalzoomer/icons/overview.png"));

        tools_menu = new JMenu("Tools");
        orbit_opt = new JCheckBoxMenuItem("Orbit", getIcon("/fractalzoomer/icons/orbit.png"));
        julia_opt = new JCheckBoxMenuItem("Julia", getIcon("/fractalzoomer/icons/julia.png"));
        color_cycling_opt = new JCheckBoxMenuItem("Color Cycling", getIcon("/fractalzoomer/icons/color_cycling.png"));
        d3_opt = new JCheckBoxMenuItem("3D", getIcon("/fractalzoomer/icons/3d.png"));
        julia_map_opt = new JCheckBoxMenuItem("Julia Map", getIcon("/fractalzoomer/icons/julia_map.png"));
        domain_coloring_opt = new JCheckBoxMenuItem("Domain Coloring", getIcon("/fractalzoomer/icons/domain_coloring.png"));
        polar_projection_opt = new JCheckBoxMenuItem("Polar Projection", getIcon("/fractalzoomer/icons/polar_projection.png"));
        grid_opt = new JCheckBoxMenuItem("Show Grid", getIcon("/fractalzoomer/icons/grid.png"));
        zoom_window_opt = new JCheckBoxMenuItem("Show Zoom Window", getIcon("/fractalzoomer/icons/zoom_window.png"));
        boundaries_opt = new JCheckBoxMenuItem("Show Boundaries", getIcon("/fractalzoomer/icons/boundaries.png"));
        plane_visualization_opt = new JMenuItem("Plane Visualization", getIcon("/fractalzoomer/icons/plane_visualization.png"));

        filters_menu = new FiltersMenu(ptr, "Filters");
        filters_opt = filters_menu.getFilters();
        defaultFilters(true);
        filters_menu.setCheckedFilters(filters);

        help_menu = new JMenu("Help");

        help_contents = new JMenuItem("Help Contents", getIcon("/fractalzoomer/icons/help.png"));

        about = new JMenuItem("About", getIcon("/fractalzoomer/icons/about.png"));

        useful_links = new JMenuItem("Useful Links", getIcon("/fractalzoomer/icons/useful_links.png"));

        update = new JMenuItem("Software Update", getIcon("/fractalzoomer/icons/update.png"));

        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        up.setToolTipText("Moves one screen up.");
        down.setToolTipText("Moves one screen down.");
        left.setToolTipText("Moves one screen left.");
        right.setToolTipText("Moves one screen right.");
        save_settings.setToolTipText("<html>Saves the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");
        load_settings.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, bailout, julia settings,<br>and image filters.</html>");
        save_image.setToolTipText("Saves a bmp, jpg, or png image.");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        exit.setToolTipText("Exits the application.");

        size_of_image.setToolTipText("Sets the image size.");
        iterations.setToolTipText("Sets the maximum number of iterations.");
        increase_iterations.setToolTipText("Increases the maximum iteterations number by one.");
        decrease_iterations.setToolTipText("Decreases the maximum iteterations number by one.");
        bailout_number.setToolTipText("Sets the bailout. Above this number the norm of a complex numbers is not bounded.");
        set_rotation.setToolTipText("Sets the rotation in degrees.");
        increase_rotation.setToolTipText("Increases the rotation by one degree.");
        decrease_rotation.setToolTipText("Decreases the rotation by one degree.");
        perturbation_opt.setToolTipText("Adds a complex number to the initial value of the fractal calculation.");
        init_val_opt.setToolTipText("Changes the initial value of the fractal calculation.");
        change_zooming_factor.setToolTipText("Sets the rate of each zoom.");
        height_ratio_number.setToolTipText("Changes the ratio of the image and creates a stretch.");
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing alot of bounded areas.");
        boundary_tracing_opt.setToolTipText("Calculates only the boundaries of the image.");
        filters_options.setToolTipText("Sets the options of the image filters.");
        smoothing_opt.setToolTipText("Smooths the image's color transitions.");
        exterior_de_opt.setToolTipText("<html>Sets some points near the boundary of<br>the set to the maximum iterations value.</html>");
        bump_map_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        fake_de_opt.setToolTipText("Emulates the effect of distance estimation.");
        rainbow_palette_opt.setToolTipText("Creates a pseudo 3d image, by applying the palette colors as a rainbow.");
        color_intensity_opt.setToolTipText("Changes the color intensity of the palette.");

        grid_color_opt.setToolTipText("Sets the color of the grid.");
        grid_tiles_opt.setToolTipText("Sets the number of the grid tiles.");
        boundaries_color_opt.setToolTipText("Sets the color of the boundaries.");
        boundaries_number_opt.setToolTipText("Sets the boundaries options.");
        toolbar_opt.setToolTipText("Activates the tool bar.");
        statusbar_opt.setToolTipText("Activates the status bar.");
        infobar_opt.setToolTipText("Activates the information bar.");
        orbit_color_opt.setToolTipText("Sets the color of the orbit.");
        show_orbit_converging_point_opt.setToolTipText("Displays the the root of the orbit.");
        zoom_window_color_opt.setToolTipText("Sets the color of zooming window.");

        fract_color.setToolTipText("Sets the colors for maximum iterations, distance estimation and some color algorithms.");
        random_palette.setToolTipText("Randomizes the palette.");
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");

        overview_opt.setToolTipText("Creates a report of all the active fractal options.");

        fast_julia_filters_opt.setToolTipText("Activates the filters for the julia preview.");
        julia_map_options_opt.setToolTipText("Changes the Julia Map options.");
        d3_details_opt.setToolTipText("Changes the 3D options.");
        polar_projection_options_opt.setToolTipText("Changes the Polar Projection options.");
        domain_coloring_options_opt.setToolTipText("Changes the Domain Coloring options.");
        color_cycling_options_opt.setToolTipText("Changes the color cycling options.");

        orbit_opt.setToolTipText("Displays the orbit of a complex number.");
        julia_opt.setToolTipText("Generates an image based on a seed (chosen pixel).");
        julia_map_opt.setToolTipText("Creates an image of julia sets.");
        polar_projection_opt.setToolTipText("Projects the image into polar coordinates.");
        d3_opt.setToolTipText("Creates a 3D version of the image.");
        color_cycling_opt.setToolTipText("Animates the image, cycling through the palette.");
        plane_visualization_opt.setToolTipText("Creates a visualization of the active plane transformation.");
        domain_coloring_opt.setToolTipText("Creates a complex plane domain coloring visualization.");
        grid_opt.setToolTipText("Draws a cordinated grid.");
        zoom_window_opt.setToolTipText("Displays the zooming window.");
        boundaries_opt.setToolTipText("Draws the plane boundaries.");

        help_contents.setToolTipText("Loads the help file.");
        update.setToolTipText("Checks for software update.");
        useful_links.setToolTipText("Provides some useful links about fractals.");

        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        up.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, ActionEvent.ALT_MASK));
        down.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ActionEvent.ALT_MASK));
        left.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, ActionEvent.ALT_MASK));
        right.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, ActionEvent.ALT_MASK));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        compile_code.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        code_editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        increase_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.ALT_MASK));
        decrease_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.ALT_MASK));
        bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        set_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        increase_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        decrease_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        perturbation_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        init_val_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.SHIFT_MASK));
        change_zooming_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        thread_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        height_ratio_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
        periodicity_checking_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        boundary_tracing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        filters_options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.SHIFT_MASK));
        smoothing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        exterior_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
        bump_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
        fake_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
        rainbow_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        color_intensity_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.SHIFT_MASK));

        overview_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.SHIFT_MASK));

        fast_julia_filters_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        julia_map_options_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.SHIFT_MASK));
        d3_details_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        polar_projection_options_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.SHIFT_MASK));
        domain_coloring_options_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        color_cycling_options_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.ALT_MASK));

        orbit_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        show_orbit_converging_point_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        polar_projection_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        toolbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        statusbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        infobar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0));
        julia_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
        d3_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
        color_cycling_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
        plane_visualization_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.SHIFT_MASK));
        domain_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        grid_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        zoom_window_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.SHIFT_MASK));
        boundaries_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK));

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
        help_contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
        update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
        useful_links.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, 0));

        starting_position.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                startingPosition();

            }
        });

        go_to.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(julia) {
                    goToJulia();
                }
                else {
                    goToFractal();
                }

            }
        });

        save_settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                saveSettings();

            }
        });

        load_settings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                loadSettings();

            }
        });

        save_image.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                saveImage();

            }
        });

        compile_code.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                compileCode(true);

            }
        });

        code_editor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                codeEditor();

            }
        });

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!color_cycling) {
                    main_panel.repaint();
                }
                int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
                if(ans == JOptionPane.YES_OPTION) {
                    saveSettings();
                    savePreferences();
                    System.exit(0);
                }
                else {
                    if(ans == JOptionPane.NO_OPTION) {
                        savePreferences();
                        System.exit(0);
                    }
                }

            }
        });

        fractal_functions[function].setSelected(true);
        fractal_functions[function].setEnabled(false);

        planes[plane_type].setSelected(true);
        planes[plane_type].setEnabled(false);

        out_coloring_modes[out_coloring_algorithm].setSelected(true);
        out_coloring_modes[out_coloring_algorithm].setEnabled(false);

        in_coloring_modes[in_coloring_algorithm].setSelected(true);
        in_coloring_modes[in_coloring_algorithm].setEnabled(false);

        smoothing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setSmoothing();
            }
        });

        exterior_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setExteriorDistanceEstimation();
            }
        });

        bump_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setBumpMap();
            }
        });

        rainbow_palette_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setRainwbowPalette();
            }
        });

        fake_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setFakeDistanceEstimation();
            }
        });

        bailout_tests[bailout_test_algorithm].setSelected(true);
        bailout_tests[bailout_test_algorithm].setEnabled(false);

        size_of_image.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setSizeOfImage();

            }
        });

        perturbation_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setPerturbation();

            }
        });

        init_val_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setInitialValue();

            }
        });

        fract_color.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setFractalColor();

            }
        });

        iterations.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setIterations();

            }
        });

        increase_iterations.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                increaseIterations();

            }
        });

        decrease_iterations.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                decreaseIterations();

            }
        });

        bailout_number.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailout();

            }
        });

        set_rotation.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setRotation();

            }
        });

        increase_rotation.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                increaseRotation();

            }
        });

        decrease_rotation.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                decreaseRotation();

            }
        });

        change_zooming_factor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setZoomingFactor();

            }
        });

        height_ratio_number.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setHeightRatio();

            }
        });

        thread_number.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setThreadsNumber();

            }
        });

        coloring_option = new String[19];
        coloring_option[0] = "Default";
        coloring_option[1] = "Spectrum";
        coloring_option[2] = "Alternative";
        coloring_option[3] = "Alternative 2";
        coloring_option[4] = "Alternative 3";
        coloring_option[5] = "Alternative 4";
        coloring_option[6] = "Alternative 5";
        coloring_option[7] = "Alternative 6";
        coloring_option[8] = "Alternative 7";
        coloring_option[9] = "Alternative 8";
        coloring_option[10] = "Alternative 9";
        coloring_option[11] = "Dusk";
        coloring_option[12] = "Gray Scale";
        coloring_option[13] = "Earth Sky";
        coloring_option[14] = "Hot Cold";
        coloring_option[15] = "Hot Cold 2";
        coloring_option[16] = "Fire";
        coloring_option[17] = "Jet";
        coloring_option[18] = "Custom Palette";

        palette = new JRadioButtonMenuItem[coloring_option.length];

        for(i = 0; i < palette.length - 1; i++) {

            Color[] c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);

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

            palette[i] = new JRadioButtonMenuItem(coloring_option[i], new ImageIcon(palette_preview));

            palette[i].addActionListener(new ActionListener() {

                int temp = i;

                public void actionPerformed(ActionEvent e) {

                    setPalette(temp);

                }
            });
            palette_menu.add(palette[i]);
        }

        palette_menu.addSeparator();

        Color[] c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

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

        palette[i] = new JRadioButtonMenuItem(coloring_option[i], new ImageIcon(palette_preview));

        palette[i].addActionListener(new ActionListener() {

            int temp = i;

            public void actionPerformed(ActionEvent e) {

                customPaletteEditor(temp);

            }
        });
        palette_menu.add(palette[i]);
        palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));

        palette[color_choice].setSelected(true);

        palette[0].setToolTipText("The default palette.");
        palette[1].setToolTipText("A palette based on color spectrum based.");
        palette[2].setToolTipText("A palette based on software, Fractal Extreme.");
        palette[3].setToolTipText("An alternative palette.");
        palette[4].setToolTipText("An alternative palette.");
        palette[5].setToolTipText("An alternative palette.");
        palette[6].setToolTipText("An alternative palette.");
        palette[7].setToolTipText("An alternative palette.");
        palette[8].setToolTipText("An alternative palette.");
        palette[9].setToolTipText("An alternative palette.");
        palette[10].setToolTipText("A palette based on software Ultra Fractal.");
        palette[11].setToolTipText("A palette based on the colors of dusk.");
        palette[12].setToolTipText("A palette based on gray scale.");
        palette[13].setToolTipText("A palette based on colors of earth and sky.");
        palette[14].setToolTipText("A palette based on colors of hot and cold.");
        palette[15].setToolTipText("A palette based on color temperature.");
        palette[16].setToolTipText("A palette based on colors of fire.");
        palette[17].setToolTipText("A palette based on matlab's colormap.");
        palette[18].setToolTipText("A palette custom made by the user.");

        random_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                randomPalette();

            }
        });

        roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                shiftPalette();

            }
        });

        increase_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                shiftPaletteForward();

            }
        });

        decrease_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                shiftPaletteBackward();

            }
        });

        overview_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Overview();

            }
        });

        color_intensity_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorIntensity();

            }
        });

        boundary_tracing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBoundaryTracing();

            }
        });

        periodicity_checking_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setPeriodicityChecking();

            }
        });

        fast_julia_filters_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setFastJuliaFilters();

            }
        });

        apply_plane_on_julia_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setApplyPlaneOnJulia();

            }
        });

        orbit_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setOrbitColor();

            }
        });

        julia_map_options_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setJuliaMapOptions();

            }
        });

        polar_projection_options_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setPolarProjectionOptions();

            }
        });

        d3_details_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                set3DDetails();

            }
        });

        domain_coloring_options_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setDomainColoringOptions();

            }
        });

        color_cycling_options_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorCyclingOptions();

            }
        });

        julia_map_options_opt.setEnabled(false);
        d3_details_opt.setEnabled(false);
        polar_projection_options_opt.setEnabled(false);
        domain_coloring_options_opt.setEnabled(false);

        toolbar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setToolbar();

            }
        });

        statusbar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setStatubar();

            }
        });

        infobar_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setInfobar();

            }
        });

        window_menu.add(toolbar_opt);
        window_menu.add(infobar_opt);
        window_menu.add(statusbar_opt);
        toolbar_opt.setSelected(true);
        infobar_opt.setSelected(true);
        statusbar_opt.setSelected(true);

        zoom_window_dashed_line = new JRadioButtonMenuItem("Dashed Line");
        zoom_window_dashed_line.setToolTipText("Sets the zooming window style to dashed line.");

        zoom_window_line = new JRadioButtonMenuItem("Solid Line");
        zoom_window_line.setToolTipText("Sets the zooming window style to solid line.");

        zoom_window_dashed_line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setZoomWindowDashedLine();

            }
        });

        zoom_window_line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setZoomWindowLine();

            }
        });

        zoom_window_dashed_line.setSelected(true);
        zoom_window_dashed_line.setEnabled(false);

        zoom_window_style_menu.add(zoom_window_dashed_line);
        zoom_window_style_menu.add(zoom_window_line);

        line = new JRadioButtonMenuItem("Line");
        line.setToolTipText("Sets the orbit style to line.");
        dot = new JRadioButtonMenuItem("Dot");
        dot.setToolTipText("Sets the orbit style to dot.");

        line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setLine();

            }
        });

        dot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setDot();

            }
        });

        show_orbit_converging_point_opt.setSelected(show_orbit_converging_point);

        show_orbit_converging_point_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setShowOrbitConvergingPoint();

            }
        });

        line.setSelected(true);
        line.setEnabled(false);

        orbit_style_menu.add(line);
        orbit_style_menu.add(dot);

        orbit_menu.add(orbit_color_opt);
        orbit_menu.addSeparator();
        orbit_menu.add(orbit_style_menu);
        orbit_menu.addSeparator();
        orbit_menu.add(show_orbit_converging_point_opt);

        grid_menu.add(grid_color_opt);
        grid_menu.addSeparator();
        grid_menu.add(grid_tiles_opt);

        zoom_window_menu.add(zoom_window_color_opt);
        zoom_window_menu.addSeparator();
        zoom_window_menu.add(zoom_window_style_menu);

        boundaries_menu.add(boundaries_color_opt);
        boundaries_menu.addSeparator();
        boundaries_menu.add(boundaries_number_opt);

        grid_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setGridColor();

            }
        });

        zoom_window_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setZoomWindowColor();

            }
        });

        boundaries_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBoundariesColor();

            }
        });

        boundaries_number_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBoundariesNumber();

            }
        });

        grid_tiles_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setGridTiles();

            }
        });

        zoom_in.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                zoomIn();

            }
        });

        zoom_out.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                zoomOut();

            }
        });

        up.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                moveTo(UP);

            }
        });

        down.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                moveTo(DOWN);

            }
        });

        left.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                moveTo(LEFT);

            }
        });

        right.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                moveTo(RIGHT);

            }
        });

        orbit_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setOrbitOption();

            }
        });

        julia_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setJuliaOption();

            }
        });

        color_cycling_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorCycling();

            }
        });

        plane_visualization_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                new PlaneVisualizationFrame(ptr, plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

            }
        });

        grid_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setGrid();

            }
        });

        zoom_window_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setZoomWindow();

            }
        });

        boundaries_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBoundaries();

            }
        });

        julia_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setJuliaMap();

            }
        });

        domain_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setDomainColoring();

            }
        });

        d3_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                set3DOption();

            }
        });

        polar_projection_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setPolarProjection();

            }
        });

        help_contents.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showCHMHelpFile();

            }
        });

        useful_links.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showUsefulLinks();

            }
        });

        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

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
        });

        update.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                checkForUpdate(true);

            }
        });

        filters_options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                filtersOptions();
            }
        });

        toolbar = new JToolBar();
        add(toolbar, BorderLayout.PAGE_START);
        toolbar.setBorderPainted(true);

        starting_position_button = new JButton();
        starting_position_button.setIcon(getIcon("/fractalzoomer/icons/starting_position.png"));
        starting_position_button.setFocusable(false);
        starting_position_button.setToolTipText("Resets the fractal to the default position.");

        starting_position_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                startingPosition();

            }
        });

        toolbar.add(starting_position_button);

        zoom_in_button = new JButton();
        zoom_in_button.setIcon(getIcon("/fractalzoomer/icons/zoom_in.png"));
        zoom_in_button.setFocusable(false);
        zoom_in_button.setToolTipText("Zooms in with a fixed rate to the current center.");

        zoom_in_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                zoomIn();

            }
        });

        toolbar.add(zoom_in_button);

        zoom_out_button = new JButton();
        zoom_out_button.setIcon(getIcon("/fractalzoomer/icons/zoom_out.png"));
        zoom_out_button.setFocusable(false);
        zoom_out_button.setToolTipText("Zooms out with a fixed rate to the current center.");

        zoom_out_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                zoomOut();

            }
        });

        toolbar.add(zoom_out_button);

        toolbar.addSeparator();

        save_image_button = new JButton();
        save_image_button.setIcon(getIcon("/fractalzoomer/icons/save_image.png"));
        save_image_button.setFocusable(false);
        save_image_button.setToolTipText("Saves a bmp, jpg, or png image.");

        save_image_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                saveImage();

            }
        });

        toolbar.add(save_image_button);

        toolbar.addSeparator();

        custom_palette_button = new JButton();
        custom_palette_button.setIcon(getIcon("/fractalzoomer/icons/palette.png"));
        custom_palette_button.setFocusable(false);
        custom_palette_button.setToolTipText("Loads the custom palette editor.");

        custom_palette_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                customPaletteEditor(palette.length - 1);
            }
        });

        toolbar.add(custom_palette_button);

        random_palette_button = new JButton();
        random_palette_button.setIcon(getIcon("/fractalzoomer/icons/palette_random.png"));
        random_palette_button.setFocusable(false);
        random_palette_button.setToolTipText("Randomizes the palette.");

        random_palette_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                randomPalette();

            }
        });

        toolbar.add(random_palette_button);

        toolbar.addSeparator();

        iterations_button = new JButton();
        iterations_button.setIcon(getIcon("/fractalzoomer/icons/iterations.png"));
        iterations_button.setFocusable(false);
        iterations_button.setToolTipText("Sets the maximum number of iterations.");

        iterations_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setIterations();

            }
        });

        toolbar.add(iterations_button);

        rotation_button = new JButton();
        rotation_button.setIcon(getIcon("/fractalzoomer/icons/rotate.png"));
        rotation_button.setFocusable(false);
        rotation_button.setToolTipText("Sets the rotation in degrees.");

        rotation_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setRotation();

            }
        });

        toolbar.add(rotation_button);

        toolbar.addSeparator();

        filters_options_button = new JButton();
        filters_options_button.setIcon(getIcon("/fractalzoomer/icons/filter_options.png"));
        filters_options_button.setFocusable(false);
        filters_options_button.setToolTipText("Sets the options of the image filters.");

        filters_options_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                filtersOptions();

            }
        });

        toolbar.add(filters_options_button);

        toolbar.addSeparator();

        orbit_button = new JButton();
        orbit_button.setIcon(getIcon("/fractalzoomer/icons/orbit.png"));
        orbit_button.setToolTipText("Displays the orbit of a complex number.");
        orbit_button.setFocusable(false);

        orbit_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!orbit_button.isSelected()) {
                    orbit_button.setSelected(true);
                    orbit_opt.setSelected(true);
                }
                else {
                    orbit_button.setSelected(false);
                    orbit_opt.setSelected(false);
                }

                setOrbitOption();

            }
        });

        toolbar.add(orbit_button);

        julia_button = new JButton();
        julia_button.setIcon(getIcon("/fractalzoomer/icons/julia.png"));
        julia_button.setToolTipText("Generates an image based on a seed (chosen pixel).");
        julia_button.setFocusable(false);

        julia_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!julia_button.isSelected()) {
                    julia_button.setSelected(true);
                    julia_opt.setSelected(true);
                }
                else {
                    julia_button.setSelected(false);
                    julia_opt.setSelected(false);
                }

                setJuliaOption();

            }
        });

        toolbar.add(julia_button);

        julia_map_button = new JButton();
        julia_map_button.setIcon(getIcon("/fractalzoomer/icons/julia_map.png"));
        julia_map_button.setToolTipText("Creates an image of julia sets.");
        julia_map_button.setFocusable(false);

        julia_map_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!julia_map_button.isSelected()) {
                    julia_map_button.setSelected(true);
                    julia_map_opt.setSelected(true);
                }
                else {
                    julia_map_button.setSelected(false);
                    julia_map_opt.setSelected(false);
                }

                setJuliaMap();

            }
        });

        toolbar.add(julia_map_button);

        d3_button = new JButton();
        d3_button.setIcon(getIcon("/fractalzoomer/icons/3d.png"));
        d3_button.setToolTipText("Creates a 3D version of the image.");
        d3_button.setFocusable(false);

        d3_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!d3_button.isSelected()) {
                    d3_button.setSelected(true);
                    d3_opt.setSelected(true);
                }
                else {
                    d3_button.setSelected(false);
                    d3_opt.setSelected(false);
                }

                set3DOption();

            }
        });

        toolbar.add(d3_button);

        polar_projection_button = new JButton();
        polar_projection_button.setIcon(getIcon("/fractalzoomer/icons/polar_projection.png"));
        polar_projection_button.setToolTipText("Projects the image into polar coordinates.");
        polar_projection_button.setFocusable(false);

        polar_projection_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!polar_projection_button.isSelected()) {
                    polar_projection_button.setSelected(true);
                    polar_projection_opt.setSelected(true);
                }
                else {
                    polar_projection_button.setSelected(false);
                    polar_projection_opt.setSelected(false);
                }

                setPolarProjection();

            }
        });

        toolbar.add(polar_projection_button);

        domain_coloring_button = new JButton();
        domain_coloring_button.setIcon(getIcon("/fractalzoomer/icons/domain_coloring.png"));
        domain_coloring_button.setToolTipText("Creates a complex plane domain coloring visualization.");
        domain_coloring_button.setFocusable(false);

        domain_coloring_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!domain_coloring_button.isSelected()) {
                    domain_coloring_button.setSelected(true);
                    domain_coloring_opt.setSelected(true);
                }
                else {
                    domain_coloring_button.setSelected(false);
                    domain_coloring_opt.setSelected(false);
                }

                setDomainColoring();

            }
        });

        toolbar.add(domain_coloring_button);

        color_cycling_button = new JButton();
        color_cycling_button.setIcon(getIcon("/fractalzoomer/icons/color_cycling.png"));
        color_cycling_button.setToolTipText("Animates the image, cycling through the palette.");
        color_cycling_button.setFocusable(false);

        color_cycling_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!color_cycling_opt.isSelected()) {
                    color_cycling_button.setSelected(true);
                    color_cycling_opt.setSelected(true);
                }
                else {
                    color_cycling_button.setSelected(false);
                    color_cycling_opt.setSelected(false);
                }

                setColorCycling();

            }
        });

        toolbar.add(color_cycling_button);

        toolbar.addSeparator();

        help_button = new JButton();
        help_button.setIcon(getIcon("/fractalzoomer/icons/help.png"));
        help_button.setFocusable(false);
        help_button.setToolTipText("Loads the help file.");

        help_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showCHMHelpFile();

            }
        });

        toolbar.add(help_button);

        JPanel status_bars = new JPanel();
        status_bars.setLayout(new BoxLayout(status_bars, BoxLayout.Y_AXIS));

        infobar = new JToolBar();
        infobar.setFloatable(false);
        infobar.setAlignmentX(Component.LEFT_ALIGNMENT);
        infobar.setAlignmentY(Component.CENTER_ALIGNMENT);
        infobar.setPreferredSize(new Dimension(0, 28));
        infobar.setBorderPainted(true);
        status_bars.add(infobar);

        if(color_choice < palette.length - 1) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
        }
        else {
            c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
        }

        palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
        g = palette_preview.createGraphics();
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

        BufferedImage max_it_preview = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        g = max_it_preview.createGraphics();

        g.setColor(fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        palette_toolbar_preview = new JLabel();
        palette_toolbar_preview.setToolTipText("Displays the active palette.");
        palette_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        palette_toolbar_preview.setIcon(new ImageIcon(palette_preview));

        max_it_color_preview = new JLabel();
        max_it_color_preview.setToolTipText("Displays the color coresponding to the max iterations.");
        max_it_color_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        max_it_color_preview.setIcon(new ImageIcon(max_it_preview));

        palette_toolbar_preview_lbl = new JLabel(" Palette: ");
        infobar.add(palette_toolbar_preview_lbl);
        infobar.add(palette_toolbar_preview);
        max_it_color_preview_lbl = new JLabel("  Max It. Color: ");
        infobar.add(max_it_color_preview_lbl);
        infobar.add(max_it_color_preview);

        overview_button = new JButton();
        overview_button.setIcon(getIcon("/fractalzoomer/icons/overview.png"));
        overview_button.setFocusable(false);
        overview_button.setToolTipText("Creates a report of all the active fractal options.");

        overview_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Overview();

            }
        });

        infobar.add(Box.createHorizontalGlue());
        infobar.addSeparator();
        infobar.add(overview_button);

        statusbar = new JToolBar();
        statusbar.setFloatable(false);

        statusbar.setBorderPainted(true);
        statusbar.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusbar.setPreferredSize(new Dimension(0, 20));
        status_bars.add(statusbar);
        JLabel label = new JLabel(" Re: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        statusbar.add(label);

        add(status_bars, BorderLayout.PAGE_END);

        real = new JTextField("Real");
        real.setHorizontalAlignment(JTextField.RIGHT);
        real.setPreferredSize(new Dimension(200, 20));
        real.setMaximumSize(real.getPreferredSize());
        //real.setMinimumSize(real.getPreferredSize());
        real.setEditable(false);
        real.setForeground(new Color(16, 78, 139));
        real.setToolTipText("Displays the Real part of the complex number.");

        statusbar.add(real);
        statusbar.add(new JLabel("   "));

        label = new JLabel("Im: ");
        //label.setFont(new Font("Arial", Font.PLAIN, 11));
        statusbar.add(label);

        imaginary = new JTextField("Imaginary");
        imaginary.setPreferredSize(new Dimension(200, 20));
        imaginary.setMaximumSize(imaginary.getPreferredSize());
        //imaginary.setMinimumSize(imaginary.getPreferredSize());
        imaginary.setHorizontalAlignment(JTextField.RIGHT);
        imaginary.setEditable(false);
        imaginary.setForeground(new Color(0, 139, 69));
        imaginary.setToolTipText("Displays the Imaginary part of the complex number.");

        statusbar.add(imaginary);

        statusbar.add(Box.createHorizontalGlue());
        statusbar.addSeparator();

        main_panel = new MainPanel(this);
        scroll_pane = new JScrollPane(main_panel);

        add(scroll_pane);

        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(220, 20));
        progress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        progress.setStringPainted(true);
        progress.setForeground(new Color(255, 185, 15));
        progress.setValue(0);

        statusbar.add(progress);
        statusbar.addSeparator();
        mode = new JLabel("Normal mode", SwingConstants.RIGHT);
        mode.setPreferredSize(new Dimension(80, 20));
        mode.setMaximumSize(mode.getPreferredSize());
        //mode.setMinimumSize(mode.getPreferredSize());
        mode.setToolTipText("Displays the active mode.");
        statusbar.add(mode);

        file_menu.add(starting_position);
        file_menu.add(go_to);
        file_menu.add(zoom_in);
        file_menu.add(zoom_out);
        file_menu.addSeparator();
        file_menu.add(up);
        file_menu.add(down);
        file_menu.add(left);
        file_menu.add(right);
        file_menu.addSeparator();
        file_menu.add(save_settings);
        file_menu.add(load_settings);
        file_menu.addSeparator();
        file_menu.add(save_image);
        file_menu.addSeparator();
        file_menu.add(code_editor);
        file_menu.add(compile_code);
        file_menu.addSeparator();
        file_menu.add(exit);

        colors_menu.add(out_coloring_mode_menu);
        colors_menu.add(in_coloring_mode_menu);
        colors_menu.addSeparator();
        colors_menu.add(smoothing_opt);
        colors_menu.add(exterior_de_opt);
        colors_menu.add(fake_de_opt);
        colors_menu.add(rainbow_palette_opt);
        colors_menu.add(bump_map_opt);
        colors_menu.addSeparator();
        colors_menu.add(fract_color);
        colors_menu.addSeparator();
        colors_menu.add(palette_menu);
        colors_menu.add(random_palette);
        colors_menu.add(roll_palette_menu);
        colors_menu.addSeparator();
        colors_menu.add(color_intensity_opt);

        tools_options_menu.add(orbit_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(fast_julia_filters_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(julia_map_options_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(d3_details_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(polar_projection_options_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(domain_coloring_options_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(color_cycling_options_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(grid_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(zoom_window_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(boundaries_menu);

        iterations_menu.add(iterations);
        iterations_menu.add(increase_iterations);
        iterations_menu.add(decrease_iterations);

        roll_palette_menu.add(roll_palette);
        roll_palette_menu.add(increase_roll_palette);
        roll_palette_menu.add(decrease_roll_palette);

        rotation_menu.add(set_rotation);
        rotation_menu.add(increase_rotation);
        rotation_menu.add(decrease_rotation);

        optimizations_menu.add(thread_number);
        optimizations_menu.addSeparator();
        optimizations_menu.add(boundary_tracing_opt);
        optimizations_menu.addSeparator();
        optimizations_menu.add(periodicity_checking_opt);

        fractal_options_menu.add(fractal_functions_menu);
        fractal_options_menu.addSeparator();
        fractal_options_menu.add(planes_menu);
        fractal_options_menu.addSeparator();
        fractal_options_menu.add(init_val_opt);
        fractal_options_menu.addSeparator();
        fractal_options_menu.add(perturbation_opt);

        options_menu.add(fractal_options_menu);
        options_menu.addSeparator();
        options_menu.add(colors_menu);
        options_menu.addSeparator();
        options_menu.add(iterations_menu);
        options_menu.addSeparator();
        options_menu.add(bailout_test_menu);
        options_menu.add(bailout_number);
        options_menu.addSeparator();
        options_menu.add(rotation_menu);
        options_menu.addSeparator();
        options_menu.add(size_of_image);
        options_menu.addSeparator();
        options_menu.add(height_ratio_number);
        options_menu.addSeparator();
        options_menu.add(change_zooming_factor);
        options_menu.addSeparator();
        options_menu.add(optimizations_menu);
        options_menu.addSeparator();
        options_menu.add(tools_options_menu);
        options_menu.addSeparator();
        options_menu.add(filters_options);
        options_menu.addSeparator();
        options_menu.add(overview_opt);
        options_menu.addSeparator();
        options_menu.add(window_menu);

        tools_menu.add(orbit_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_map_opt);
        tools_menu.addSeparator();
        tools_menu.add(d3_opt);
        tools_menu.addSeparator();
        tools_menu.add(polar_projection_opt);
        tools_menu.addSeparator();
        tools_menu.add(domain_coloring_opt);
        tools_menu.addSeparator();
        tools_menu.add(color_cycling_opt);
        tools_menu.addSeparator();
        tools_menu.add(plane_visualization_opt);
        tools_menu.addSeparator();
        tools_menu.add(grid_opt);
        tools_menu.addSeparator();
        tools_menu.add(zoom_window_opt);
        tools_menu.addSeparator();
        tools_menu.add(boundaries_opt);

        help_menu.add(help_contents);
        help_menu.addSeparator();
        help_menu.add(useful_links);
        help_menu.addSeparator();
        help_menu.add(update);
        help_menu.addSeparator();
        help_menu.add(about);

        menubar.add(file_menu);
        menubar.add(options_menu);
        menubar.add(tools_menu);
        menubar.add(filters_menu);
        menubar.add(help_menu);

        setJMenuBar(menubar);

        scroll_pane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            }
        });

        scroll_pane.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

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

                if(old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grabbing_cursor);
                }
                else if(old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

                if(old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else if(old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(whole_image_done) {
                    if(!color_cycling) {
                        main_panel.repaint();
                    }
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
                    if(System.getProperty("os.name").equals("Windows XP")) { //Not tested
                        image_size = getWidth() - 27;
                    }
                    else if(System.getProperty("os.name").equals("Windows 7")) {
                        image_size = getWidth() - 35;
                    }
                    else {
                        image_size = getWidth() - 35;//Not tested
                    }
                }
                else if(e.getNewState() == NORMAL && e.getOldState() == MAXIMIZED_BOTH) {
                    if(System.getProperty("os.name").equals("Windows XP")) { //Not tested
                        if(getHeight() > getWidth()) {
                            image_size = getHeight() - 57;
                        }
                        else {
                            image_size = getWidth() - 27;
                        }
                    }
                    else if(System.getProperty("os.name").equals("Windows 7")) {
                        if(getHeight() > getWidth()) {
                            image_size = getHeight() - 61;
                        }
                        else {
                            image_size = getWidth() - 35;
                        }
                    }
                    else {
                        if(getHeight() > getWidth()) {  //not tested
                            image_size = getHeight() - 61;
                        }
                        else {
                            image_size = getWidth() - 35;
                        }
                    }
                }
                else {
                    return;
                }

                if(image_size > 4000) {
                    orbit_opt.setEnabled(false);
                    orbit_button.setEnabled(false);
                    orbit_button.setSelected(false);
                    orbit_opt.setSelected(false);
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
                    julia_opt.setSelected(false);
                    julia_button.setSelected(false);

                    if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED) {
                        rootFindingMethodsSetEnabled(true);
                    }
                    fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                }

                if(julia_map) {
                    createThreadsJuliaMap();
                }
                else {
                    createThreads();
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

                if(old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grabbing_cursor);
                }
                else if(old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    scroll_pane.setCursor(grab_cursor);
                }
                else {
                    scroll_pane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                try {
                    if(old_d3) {
                        real.setText("Real");
                        imaginary.setText("Imaginary");
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

                        start = center - mulx * image_size / 2.0;

                        f = y1 * muly;
                        sf = Math.sin(f);
                        cf = Math.cos(f);

                        r = Math.exp(x1 * mulx + start);

                        double temp2 = old_xCenter + r * cf - old_rotation_center[0];
                        double temp = old_yCenter + r * sf - old_rotation_center[1];

                        double temp3 = temp2 * old_rotation_vals[0] - temp * old_rotation_vals[1] + old_rotation_center[0];

                        temp3 = temp3 == 0 ? 0.0 : temp3;

                        real.setText("" + temp3);

                        temp3 = temp2 * old_rotation_vals[1] + temp * old_rotation_vals[0] + old_rotation_center[1];

                        temp3 = temp3 == 0 ? 0.0 : temp3;

                        imaginary.setText("" + temp3);

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

                        double temp2 = temp_xcenter_size + temp_size_image_size_x * x1 - old_rotation_center[0];
                        double temp = temp_ycenter_size - temp_size_image_size_y * y1 - old_rotation_center[1];

                        double temp3 = temp2 * old_rotation_vals[0] - temp * old_rotation_vals[1] + old_rotation_center[0];

                        temp3 = temp3 == 0 ? 0.0 : temp3;

                        real.setText("" + temp3);

                        temp3 = temp2 * old_rotation_vals[1] + temp * old_rotation_vals[0] + old_rotation_center[1];

                        temp3 = temp3 == 0 ? 0.0 : temp3;

                        imaginary.setText("" + temp3);
                    }

                    scroll_pane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

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

        createThreads();

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

        double temp_xcenter = xCenter - rotation_center[0];
        double temp_ycenter = yCenter - rotation_center[1];

        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

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

        temp += "   Center: " + Complex.toString2(temp1, temp2) + "   Size: " + size;

        setTitle(temp);

        if(d3) {
            real.setText("Real");
            imaginary.setText("Imaginary");
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

                start = center - mulx * image_size / 2.0;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                double temp4 = xCenter + r * cf - rotation_center[0];
                double temp5 = yCenter + r * sf - rotation_center[1];

                double temp6 = temp4 * rotation_vals[0] - temp5 * rotation_vals[1] + rotation_center[0];

                temp6 = temp6 == 0 ? 0.0 : temp6;

                real.setText("" + temp6);

                temp6 = temp4 * rotation_vals[1] + temp5 * rotation_vals[0] + rotation_center[1];

                temp6 = temp6 == 0 ? 0.0 : temp6;

                imaginary.setText("" + temp6);
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

                double temp4 = temp_xcenter_size + temp_size_image_size_x * x1 - rotation_center[0];
                double temp5 = temp_ycenter_size - temp_size_image_size_y * y1 - rotation_center[1];

                double temp6 = temp4 * rotation_vals[0] - temp5 * rotation_vals[1] + rotation_center[0];

                temp6 = temp6 == 0 ? 0.0 : temp6;

                real.setText("" + temp6);

                temp6 = temp4 * rotation_vals[1] + temp5 * rotation_vals[0] + rotation_center[1];

                temp6 = temp6 == 0 ? 0.0 : temp6;

                imaginary.setText("" + temp6);
            }
            catch(Exception ex) {
            }
        }
    }

    private void createThreads() {

        ThreadDraw.resetAtomics(n * n);

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

                    if(color_choice != palette.length - 1) {
                        if(julia) {
                            if(d3) {
                                threads[i][j] = new Palette(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, xJuliaCenter, yJuliaCenter);
                            }
                            else {
                                threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, xJuliaCenter, yJuliaCenter);
                            }
                        }
                        else {
                            if(d3) {
                                threads[i][j] = new Palette(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem);
                            }
                            else {
                                threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem);
                            }
                        }
                    }
                    else {
                        if(julia) {
                            if(d3) {
                                threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, xJuliaCenter, yJuliaCenter);
                            }
                            else {
                                threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, xJuliaCenter, yJuliaCenter);
                            }
                        }
                        else {
                            if(d3) {
                                threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem);

                            }
                            else {
                                threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem);
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

    private void saveSettings() {

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
                    settings = new SettingsJulia1064(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor, dem_color, special_color, special_use_palette_color, rainbow_palette, rainbow_palette_factor, filters, filters_options_vals, scale_factor_palette_val, processing_alg, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, use_palette_domain_coloring, domain_coloring_alg, inverse_dem, inverse_fake_dem, user_incoloring_special_color, user_outcoloring_special_color, filters_options_extra_vals, filters_extra_colors, xJuliaCenter, yJuliaCenter);
                }
                else {
                    int temp_bailout_test_algorithm = 0;

                    if(function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                        temp_bailout_test_algorithm = bailout_test_algorithm;
                    }

                    settings = new SettingsFractals1064(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, temp_bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, dem_color, special_color, special_use_palette_color, rainbow_palette, rainbow_palette_factor, filters, filters_options_vals, scale_factor_palette_val, processing_alg, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, use_palette_domain_coloring, domain_coloring_alg, inverse_dem, inverse_fake_dem, user_incoloring_special_color, user_outcoloring_special_color, filters_options_extra_vals, filters_extra_colors);
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
        temp = temp.substring(29, temp.length());

        return temp.equals("SettingsJulia") || temp.equals("SettingsJulia1049") || temp.equals("SettingsJulia1050") || temp.equals("SettingsJulia1053") || temp.equals("SettingsJulia1054") || temp.equals("SettingsJulia1055") || temp.equals("SettingsJulia1056") || temp.equals("SettingsJulia1057") || temp.equals("SettingsJulia1058") || temp.equals("SettingsJulia1061") || temp.equals("SettingsJulia1062") || temp.equals("SettingsJulia1063") || temp.equals("SettingsJulia1064");

    }

    private int getSettingsVersion(String class_name) {

        String temp = class_name;
        temp = temp.substring(29, temp.length());

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

        return 9999;

    }

    private void loadSettings() {

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

                int version = getSettingsVersion("" + settings.getClass());

                palette[color_choice].setSelected(false);

                fractal_functions[function].setSelected(false);
                fractal_functions[function].setEnabled(true);

                planes[plane_type].setSelected(false);
                planes[plane_type].setEnabled(true);

                out_coloring_modes[out_coloring_algorithm].setSelected(false);
                out_coloring_modes[out_coloring_algorithm].setEnabled(true);

                in_coloring_modes[in_coloring_algorithm].setSelected(false);
                in_coloring_modes[in_coloring_algorithm].setEnabled(true);

                bailout_tests[bailout_test_algorithm].setSelected(false);
                bailout_tests[bailout_test_algorithm].setEnabled(true);

                julia_map = false;
                julia_map_opt.setSelected(false);
                julia_map_button.setSelected(false);
                julia_map_options_opt.setEnabled(false);

                zoom_window = false;
                zoom_window_opt.setSelected(false);

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

                    julia = true;
                    first_seed = false;
                    julia_opt.setSelected(true);
                    julia_button.setSelected(true);
                    julia_map_opt.setEnabled(false);
                    julia_map_button.setEnabled(false);
                    perturbation_opt.setEnabled(false);
                    init_val_opt.setEnabled(false);
                    rootFindingMethodsSetEnabled(false);
                    fractal_functions[SIERPINSKI_GASKET].setEnabled(false);

                }
                else {
                    julia = false;
                    first_seed = true;
                    julia_opt.setSelected(false);
                    julia_button.setSelected(false);

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

                    if(!perturbation && !init_val) {
                        rootFindingMethodsSetEnabled(true);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                    }
                    else {
                        julia_opt.setEnabled(false);
                        julia_button.setEnabled(false);
                        julia_map_opt.setEnabled(false);
                        julia_map_button.setEnabled(false);
                        rootFindingMethodsSetEnabled(false);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
                    }

                }

                perturbation_opt.setSelected(perturbation);
                init_val_opt.setSelected(init_val);

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
                }

                if(version < 1064) {
                    inverse_dem = false;
                    inverse_fake_dem = false;
                    user_outcoloring_special_color[0] = false;
                    user_outcoloring_special_color[1] = false;
                    user_outcoloring_special_color[2] = false;

                    user_incoloring_special_color[0] = false;
                    user_incoloring_special_color[1] = false;
                    user_incoloring_special_color[2] = false;
                }
                else {
                    inverse_dem = ((SettingsFractals1064)settings).getInverseDe();
                    inverse_fake_dem = ((SettingsFractals1064)settings).getInverseFakeDe();
                    user_outcoloring_special_color = ((SettingsFractals1064)settings).getUserOutColoringSpecialColor();
                    user_incoloring_special_color = ((SettingsFractals1064)settings).getUserInColoringSpecialColor();
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

                if(size < 0.05) {
                    boundaries_opt.setEnabled(false);
                    boundaries = false;
                    boundaries_opt.setSelected(false);
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

                if(color_choice == palette.length - 1) {
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

                if(rotation != 0 && rotation != 360 && rotation != -360) {
                    grid_opt.setEnabled(false);
                    grid = false;
                    grid_opt.setSelected(false);

                    boundaries_opt.setEnabled(false);
                    boundaries = false;
                    boundaries_opt.setSelected(false);
                }

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

                smoothing = settings.getSmoothing();

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

                if(in_coloring_algorithm != MAXIMUM_ITERATIONS) {
                    periodicity_checking = false;
                    periodicity_checking_opt.setSelected(false);
                    periodicity_checking_opt.setEnabled(false);
                    in_coloring_modes[Z_MAG].setEnabled(true);
                    in_coloring_modes[DECOMPOSITION_LIKE].setEnabled(true);
                    in_coloring_modes[RE_DIVIDE_IM].setEnabled(true);
                    in_coloring_modes[COS_MAG].setEnabled(true);
                    in_coloring_modes[MAG_TIMES_COS_RE_SQUARED].setEnabled(true);
                    in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED].setEnabled(true);
                    in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setEnabled(true);
                    in_coloring_modes[SQUARES].setEnabled(true);
                    in_coloring_modes[SQUARES2].setEnabled(true);
                    in_coloring_modes[USER_INCOLORING_ALGORITHM].setEnabled(true);
                }

                for(int k = 0; k < fractal_functions.length; k++) {
                    //if(function != k) {
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
                    // }
                }

                burning_ship_opt.setEnabled(true);
                mandel_grass_opt.setEnabled(true);

                if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de) {
                    for(int k = 1; k < fractal_functions.length; k++) {
                        fractal_functions[k].setEnabled(false);
                    }

                    init_val_opt.setEnabled(false);
                    perturbation_opt.setEnabled(false);

                    if(exterior_de) {
                        burning_ship_opt.setEnabled(false);
                        mandel_grass_opt.setEnabled(false);
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

                apply_plane_on_julia_opt.setSelected(apply_plane_on_julia);

                if(out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                }
                else {
                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                    out_coloring_modes[out_coloring_algorithm].setEnabled(false);
                }

                if(in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                    in_coloring_modes[in_coloring_algorithm].setSelected(true);

                }
                else {
                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                    in_coloring_modes[in_coloring_algorithm].setEnabled(false);
                }

                bailout_tests[bailout_test_algorithm].setSelected(true);

                if(bailout_test_algorithm != BAILOUT_TEST_NNORM && bailout_test_algorithm != BAILOUT_TEST_USER) {
                    bailout_tests[bailout_test_algorithm].setEnabled(false);
                }

                if(burning_ship || mandel_grass || init_val || perturbation) {
                    exterior_de_opt.setEnabled(false);
                }

                if(polar_projection) {
                    grid_opt.setEnabled(false);
                    zoom_window_opt.setEnabled(false);

                    grid = false;
                    boundaries = false;

                    grid_opt.setSelected(false);
                    boundaries_opt.setSelected(false);

                    polar_projection_options_opt.setEnabled(true);

                }
                else {
                    polar_projection_options_opt.setEnabled(false);
                }

                polar_projection_opt.setSelected(polar_projection);
                polar_projection_button.setSelected(polar_projection);

                if(domain_coloring) {
                    julia_map_opt.setEnabled(false);
                    julia_map_button.setEnabled(false);
                    julia_opt.setEnabled(false);
                    julia_button.setEnabled(false);
                    color_cycling_opt.setEnabled(false);
                    color_cycling_button.setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        colors_menu.setEnabled(false);
                        custom_palette_button.setEnabled(false);
                        random_palette_button.setEnabled(false);
                    }
                    else {
                        exterior_de_opt.setEnabled(false);
                        bump_map_opt.setEnabled(false);

                        fake_de_opt.setEnabled(false);

                        rainbow_palette_opt.setEnabled(false);
                        out_coloring_mode_menu.setEnabled(false);
                        in_coloring_mode_menu.setEnabled(false);
                        fract_color.setEnabled(false);
                    }

                    boundary_tracing_opt.setEnabled(false);
                    periodicity_checking_opt.setEnabled(false);
                    bailout_number.setEnabled(false);
                    bailout_test_menu.setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        palette_toolbar_preview.setVisible(false);
                        palette_toolbar_preview_lbl.setVisible(false);
                    }
                    else {
                        palette_toolbar_preview.setVisible(true);
                        palette_toolbar_preview_lbl.setVisible(true);
                    }

                    max_it_color_preview.setVisible(false);
                    max_it_color_preview_lbl.setVisible(false);

                    domain_coloring_options_opt.setEnabled(true);
                }
                else {
                    palette_toolbar_preview.setVisible(true);
                    palette_toolbar_preview_lbl.setVisible(true);

                    max_it_color_preview.setVisible(true);
                    max_it_color_preview_lbl.setVisible(true);

                    domain_coloring_options_opt.setEnabled(false);
                }

                domain_coloring_opt.setSelected(domain_coloring);
                domain_coloring_button.setSelected(domain_coloring);

                filters_menu.setCheckedFilters(filters);

                switch (function) {
                    case MANDELBROTNTH:
                        z_exponent = settings.getZExponent();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut();
                        break;
                    case MANDELBROTWTH:
                        z_exponent_complex = settings.getZExponentComplex();
                        fractal_functions[function].setSelected(true);
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
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
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
                        coefficients = settings.getCoefficients();

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

                        fractal_functions[function].setSelected(true);
                        if(function == MANDELPOLY) {
                            optionsEnableShortcut();
                        }
                        else {
                            optionsEnableShortcut2();
                        }
                        break;
                    case NEWTONFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                        user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                        user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        user_dfz_formula = ((SettingsFractals1058)settings).getUserDfzFormula();
                        user_ddfz_formula = ((SettingsFractals1058)settings).getUserDdfzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case SECANTFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case STEFFENSENFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case MULLERFORMULA:
                        user_fz_formula = ((SettingsFractals1058)settings).getUserFzFormula();
                        fractal_functions[function].setSelected(true);
                        optionsEnableShortcut2();
                        break;
                    case NOVA:
                        z_exponent_nova = settings.getZExponentNova();
                        relaxation = settings.getRelaxation();
                        nova_method = settings.getNovaMethod();
                        fractal_functions[function].setSelected(true);
                        periodicity_checking_opt.setEnabled(false);
                        bailout_number.setEnabled(false);
                        bailout_test_menu.setEnabled(false);
                        optionsEnableShortcut();
                        break;
                    case SIERPINSKI_GASKET:
                        optionsEnableShortcut();
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        julia_opt.setEnabled(false);
                        julia_button.setEnabled(false);
                        julia_map_opt.setEnabled(false);
                        julia_map_button.setEnabled(false);
                        periodicity_checking_opt.setEnabled(false);
                        perturbation_opt.setEnabled(false);
                        init_val_opt.setEnabled(false);
                        break;
                    case MANDELBROT:
                        if(!burning_ship && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                            exterior_de_opt.setEnabled(true);
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
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
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

                        parser.parse(user_formula);

                        user_formula_c = parser.foundC();

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            julia_map_button.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }

                        optionsEnableShortcut();

                        fractal_functions[function].setSelected(true);
                        break;
                    case USER_FORMULA_ITERATION_BASED:
                        user_formula_iteration_based = ((SettingsFractals1049)settings).getUserFormulaIterationBased();
                        bail_technique = settings.getBailTechnique();

                        boolean temp_bool = false;

                        for(int m = 0; m < user_formula_iteration_based.length; m++) {
                            parser.parse(user_formula_iteration_based[m]);
                            temp_bool = temp_bool | parser.foundC();
                        }

                        user_formula_c = temp_bool;

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            julia_map_button.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }

                        optionsEnableShortcut();

                        fractal_functions[function].setSelected(true);
                        break;
                    case USER_FORMULA_CONDITIONAL:
                        user_formula_conditions = ((SettingsFractals1050)settings).getUserFormulaConditions();
                        user_formula_condition_formula = ((SettingsFractals1050)settings).getUserFormulaConditionFormula();
                        bail_technique = settings.getBailTechnique();

                        boolean temp_bool2 = false;

                        for(int m = 0; m < user_formula_condition_formula.length; m++) {
                            parser.parse(user_formula_condition_formula[m]);
                            temp_bool2 = temp_bool2 | parser.foundC();
                        }

                        user_formula_c = temp_bool2;

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            julia_map_button.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }

                        optionsEnableShortcut();

                        fractal_functions[function].setSelected(true);
                        break;
                    case USER_FORMULA_COUPLED:
                        user_formula_coupled = ((SettingsFractals1063)settings).getUserFormulaCoupled();
                        coupling = ((SettingsFractals1063)settings).getCoupling();
                        coupling_amplitude = ((SettingsFractals1063)settings).getCouplingAmplitude();
                        coupling_frequency = ((SettingsFractals1063)settings).getCouplingFrequency();
                        coupling_seed = ((SettingsFractals1063)settings).getCouplingSeed();
                        coupling_method = ((SettingsFractals1063)settings).getCouplingMethod();

                        bail_technique = settings.getBailTechnique();

                        temp_bool = false;

                        for(int m = 0; m < user_formula_coupled.length - 1; m++) {
                            parser.parse(user_formula_coupled[m]);
                            temp_bool = temp_bool | parser.foundC();
                        }

                        user_formula_c = temp_bool;

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            julia_map_button.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }

                        optionsEnableShortcut();

                        fractal_functions[function].setSelected(true);
                        break;
                    default:
                        optionsEnableShortcut();
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        break;
                }

                palette[color_choice].setSelected(true);
                updateColorPalettesMenu();
                updateMaxIterColorPreview();

                burning_ship_opt.setSelected(burning_ship);
                mandel_grass_opt.setSelected(mandel_grass);

                if(plane_type == USER_PLANE || plane_type == TWIRL_PLANE || plane_type == SHEAR_PLANE || plane_type == KALEIDOSCOPE_PLANE || plane_type == PINCH_PLANE || plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE || plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE || plane_type == CIRCLEINVERSION_PLANE || plane_type == INFLECTION_PLANE) {
                    planes[plane_type].setSelected(true);
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(false);
                }

                if(out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
                    user_outcoloring_special_color[0] = false;
                    user_outcoloring_special_color[1] = false;
                    user_outcoloring_special_color[2] = false;

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

                createThreads();

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

    private void saveImage() {

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
                    bailout = 8;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 8;
                    bailout = 2;
                }
                break;
            case MAGNET1:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 28;
                    bailout = 13;
                }
                else {
                    xCenter = 1.35;
                    yCenter = 0;
                    size = 8;
                    bailout = 13;
                }
                break;
            case MAGNET2:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 56;
                    bailout = 13;
                }
                else {
                    xCenter = 1;
                    yCenter = 0;
                    size = 7;
                    bailout = 13;
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
                bailout = 2;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                bailout = 100;
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
                bailout = 8;
                break;
            case FORMULA1:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = 8;
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
                bailout = 4;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = 4;
                break;
            case FORMULA4:
            case FORMULA5:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = 4;
                break;
            case FORMULA7:
            case FORMULA12:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = 8;
                break;
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = 16;
                break;
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = 100;
                break;
            case FORMULA27:
                if(julia) {
                    xCenter = -2;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    bailout = 8;
                }
                break;
            case FORMULA28:
            case FORMULA29:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 3;
                    bailout = 16;
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
                bailout = 16;
                break;
            case FORMULA38:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 1.5;
                    bailout = 2;
                }
                break;
            case FORMULA42:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = 12;
                break;
            case FORMULA43:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = 12;
                break;
            case FORMULA44:
            case FORMULA45:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                bailout = 16;
                break;
            case FORMULA46:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 100;
                break;
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = 4;
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
        }

        if(size < 0.05) {
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void goToFractal() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        if(!orbit) {

            JTextField field_real = new JTextField();

            double temp_xcenter = xCenter - rotation_center[0];
            double temp_ycenter = yCenter - rotation_center[1];

            double temp3 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];

            if(temp3 == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + temp3);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            temp3 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

            if(temp3 == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + temp3);
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
                boundaries_opt.setEnabled(false);
                boundaries = false;
                boundaries_opt.setSelected(false);
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
                createThreads();
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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
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

    private void goToJulia() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        if(!orbit) {
            if(first_seed) {
                JTextField field_real = new JTextField();

                double temp_xcenter = xCenter - rotation_center[0];
                double temp_ycenter = yCenter - rotation_center[1];

                double temp3 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];

                if(temp3 == 0) {
                    field_real.setText("" + 0.0);
                }
                else {
                    field_real.setText("" + temp3);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                temp3 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                if(temp3 == 0) {
                    field_imaginary.setText("" + 0.0);
                }
                else {
                    field_imaginary.setText("" + temp3);
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

                double temp_xcenter = xCenter - rotation_center[0];
                double temp_ycenter = yCenter - rotation_center[1];

                double temp3 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];

                if(temp3 == 0) {
                    field_real.setText("" + 0.0);
                }
                else {
                    field_real.setText("" + temp3);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                temp3 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                if(temp3 == 0) {
                    field_imaginary.setText("" + 0.0);
                }
                else {
                    field_imaginary.setText("" + temp3);
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
                    boundaries_opt.setEnabled(false);
                    boundaries = false;
                    boundaries_opt.setSelected(false);
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

                createThreads();

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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
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
                pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
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

    private void setFractalColor() {

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

                if(color_choice < palette.length - 1) {
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
                        createThreads();
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
                        createThreads();
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

    private void setIterations() {

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
                if(temp > 100000) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be less than 100001.", "Error!", JOptionPane.ERROR_MESSAGE);
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
                createThreads();
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

    private void setHeightRatio() {

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
                createThreads();
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

    private void setZoomingFactor() {

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

    private void setThreadsNumber() {

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

        if(temp2 != color_choice) {
            palette[color_choice].setSelected(false);
        }

        color_choice = temp2;
        if(color_choice != palette.length - 1) {
            color_cycling_location = 0;
        }

        palette[color_choice].setSelected(true);

        //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
        Color[] c = null;

        if(color_choice < palette.length - 1) {
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
                createThreads();
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
                createThreads();
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
                    createThreads();
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
                    createThreads();
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
                    createThreads();
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

    private void setGrid() {

        if(!grid_opt.isSelected()) {
            grid = false;
            old_grid = grid;
            if(!zoom_window && !julia_map && !orbit && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }

            if(!zoom_window) {
                polar_projection_opt.setEnabled(true);
                polar_projection_button.setEnabled(true);
            }

            main_panel.repaint();
        }
        else {
            grid = true;
            old_grid = grid;
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            polar_projection_opt.setEnabled(false);
            polar_projection_button.setEnabled(false);
            main_panel.repaint();
        }

    }

    private void setBoundaries() {

        if(!boundaries_opt.isSelected()) {
            boundaries = false;
            old_boundaries = boundaries;
            if(!zoom_window && !julia_map && !orbit && !grid) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }
            main_panel.repaint();
        }
        else {
            boundaries = true;
            old_boundaries = boundaries;
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            main_panel.repaint();
        }

    }

    private void setZoomWindow() {

        if(!zoom_window_opt.isSelected()) {
            zoom_window = false;
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
                julia_opt.setEnabled(true);
                julia_button.setEnabled(true);
                if(!julia) {
                    julia_map_opt.setEnabled(true);
                    julia_map_button.setEnabled(true);
                }
            }

            if(!grid && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }

            if(image_size < 4001) {
                orbit_opt.setEnabled(true);
                orbit_button.setEnabled(true);
            }

            if(!grid) {
                polar_projection_opt.setEnabled(true);
                polar_projection_button.setEnabled(true);
            }

            if(!domain_coloring) {
                color_cycling_opt.setEnabled(true);
                color_cycling_button.setEnabled(true);
            }
            main_panel.repaint();
        }
        else {
            zoom_window = true;
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            orbit_opt.setEnabled(false);
            orbit_button.setEnabled(false);
            color_cycling_opt.setEnabled(false);
            color_cycling_button.setEnabled(false);
            polar_projection_opt.setEnabled(false);
            polar_projection_button.setEnabled(false);
            main_panel.repaint();
        }

    }

    private void setBoundaryTracing() {

        if(!boundary_tracing_opt.isSelected()) {
            boundary_tracing = false;
        }
        else {
            boundary_tracing = true;
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void setPeriodicityChecking() {

        if(!periodicity_checking_opt.isSelected()) {
            periodicity_checking = false;
            in_coloring_modes[Z_MAG].setEnabled(true);
            in_coloring_modes[DECOMPOSITION_LIKE].setEnabled(true);
            in_coloring_modes[RE_DIVIDE_IM].setEnabled(true);
            in_coloring_modes[COS_MAG].setEnabled(true);
            in_coloring_modes[MAG_TIMES_COS_RE_SQUARED].setEnabled(true);
            in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED].setEnabled(true);
            in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setEnabled(true);
            in_coloring_modes[SQUARES].setEnabled(true);
            in_coloring_modes[SQUARES2].setEnabled(true);
            in_coloring_modes[USER_INCOLORING_ALGORITHM].setEnabled(true);
            main_panel.repaint();
        }
        else {
            periodicity_checking = true;
            in_coloring_modes[Z_MAG].setEnabled(false);
            in_coloring_modes[DECOMPOSITION_LIKE].setEnabled(false);
            in_coloring_modes[RE_DIVIDE_IM].setEnabled(false);
            in_coloring_modes[COS_MAG].setEnabled(false);
            in_coloring_modes[MAG_TIMES_COS_RE_SQUARED].setEnabled(false);
            in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED].setEnabled(false);
            in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setEnabled(false);
            in_coloring_modes[SQUARES].setEnabled(false);
            in_coloring_modes[SQUARES2].setEnabled(false);
            in_coloring_modes[USER_INCOLORING_ALGORITHM].setEnabled(false);
            main_panel.repaint();
        }

    }

    private void selectPointFractal(MouseEvent e) {

        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        if(main_panel.getMousePosition().getX() < 0 || main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 || main_panel.getMousePosition().getY() > image_size) {
            return;
        }

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        xCenter = xCenter - size_2_x + temp_size_image_size_x * main_panel.getMousePosition().getX();
        yCenter = yCenter + size_2_y - temp_size_image_size_y * main_panel.getMousePosition().getY();

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
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void selectPointJulia(MouseEvent e) {

        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        if(main_panel.getMousePosition().getX() < 0 || main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 || main_panel.getMousePosition().getY() > image_size) {
            return;
        }

        setOptions(false);

        if(first_seed) {
            if(e.getModifiers() == InputEvent.BUTTON1_MASK) {

                int x1 = (int)main_panel.getMousePosition().getX();
                int y1 = (int)main_panel.getMousePosition().getY();

                if(polar_projection) {
                    double start;
                    double center = Math.log(size);

                    double f, sf, cf, r;
                    double muly = (2 * circle_period * Math.PI) / image_size;

                    double mulx = muly * height_ratio;

                    start = center - mulx * image_size / 2.0;

                    f = y1 * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x1 * mulx + start);

                    double temp = xCenter + r * cf - rotation_center[0];
                    double temp2 = yCenter + r * sf - rotation_center[1];

                    xJuliaCenter = temp * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
                    yJuliaCenter = temp * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
                }
                else {
                    double size_2_x = size * 0.5;
                    double size_2_y = (size * height_ratio) * 0.5;
                    double temp_size_image_size_x = size / image_size;
                    double temp_size_image_size_y = (size * height_ratio) / image_size;

                    double temp = xCenter - size_2_x + temp_size_image_size_x * x1 - rotation_center[0];
                    double temp2 = yCenter + size_2_y - temp_size_image_size_y * y1 - rotation_center[1];

                    xJuliaCenter = temp * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
                    yJuliaCenter = temp * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
                }

                first_seed = false;

                if(image_size < 4001) {
                    orbit_opt.setEnabled(true);
                    orbit_button.setEnabled(true);
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

            xCenter = xCenter - size_2_x + temp_size_image_size_x * main_panel.getMousePosition().getX();
            yCenter = yCenter + size_2_y - temp_size_image_size_y * main_panel.getMousePosition().getY();

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
                boundaries_opt.setEnabled(false);
                boundaries = false;
                boundaries_opt.setSelected(false);
            }

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }

    }

    private void selectPointPolar(MouseEvent e) {
        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        if(main_panel.getMousePosition().getX() < 0 || main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 || main_panel.getMousePosition().getY() > image_size) {
            return;
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
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }
    }

    private void setJuliaOption() {

        if(!julia_opt.isSelected()) {
            julia = false;
            julia_button.setSelected(false);
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
            julia_button.setSelected(true);
            first_seed = true;
            orbit_opt.setEnabled(false);
            orbit_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            perturbation_opt.setEnabled(false);
            init_val_opt.setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            setOptions(false);
        }

    }

    private void setOrbitOption() {

        if(!orbit_opt.isSelected()) {
            orbit = false;
            orbit_button.setSelected(false);
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
                julia_opt.setEnabled(true);
                julia_button.setEnabled(true);
                if(!julia) {
                    julia_map_opt.setEnabled(true);
                    julia_map_button.setEnabled(true);
                }
            }

            if(!grid && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }

            if(!polar_projection) {
                zoom_window_opt.setEnabled(true);
            }

            if(!domain_coloring) {
                color_cycling_opt.setEnabled(true);
                color_cycling_button.setEnabled(true);
            }

            if(backup_orbit != null) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            backup_orbit = null;
            //last_used = null;
        }
        else {
            orbit = true;
            orbit_button.setSelected(true);
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            color_cycling_opt.setEnabled(false);
            color_cycling_button.setEnabled(false);
            zoom_window_opt.setEnabled(false);
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

        real.setText("Real");
        imaginary.setText("Imaginary");

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
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 400 ? 400 : max_iterations, x1, y1, image_size, image, ptr, orbit_color, orbit_style, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, polar_projection, circle_period, show_orbit_converging_point, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
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

                start = center - mulx * image_size / 2.0;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                double temp2 = xCenter + r * cf - rotation_center[0];
                double temp = yCenter + r * sf - rotation_center[1];

                double temp3 = temp2 * rotation_vals[0] - temp * rotation_vals[1] + rotation_center[0];

                temp3 = temp3 == 0 ? 0.0 : temp3;

                real.setText("" + temp3);

                temp3 = temp2 * rotation_vals[1] + temp * rotation_vals[0] + rotation_center[1];

                temp3 = temp3 == 0 ? 0.0 : temp3;

                imaginary.setText("" + temp3);
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

                double temp2 = temp_xcenter_size + temp_size_image_size_x * x1 - rotation_center[0];
                double temp = temp_ycenter_size - temp_size_image_size_y * y1 - rotation_center[1];

                double temp3 = temp2 * rotation_vals[0] - temp * rotation_vals[1] + rotation_center[0];

                temp3 = temp3 == 0 ? 0.0 : temp3;

                real.setText("" + temp3);

                temp3 = temp2 * rotation_vals[1] + temp * rotation_vals[0] + rotation_center[1];

                temp3 = temp3 == 0 ? 0.0 : temp3;

                imaginary.setText("" + temp3);
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
    
     createThreads();
    
     calculation_time = System.currentTimeMillis();
    
     startThreads(n);
    
     } while( true);
     }
     }.start();  
     }*/
    private void zoomIn() {

        size /= zoom_factor;

        if(size < 0.05) {
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void zoomOut() {

        size *= zoom_factor;

        if(size < 0.05) {
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void moveTo(int where) {

        if(where == UP) {
            if(polar_projection) {
                rotation -= circle_period * 360;

                if(rotation < -360) {
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
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
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

        starting_position.setEnabled(option);
        starting_position_button.setEnabled(option);

        save_settings.setEnabled(option);
        load_settings.setEnabled(option);
        save_image.setEnabled(option);

        code_editor.setEnabled(option);
        compile_code.setEnabled(option);

        if((!julia || !first_seed)) {
            go_to.setEnabled(option);
        }

        fractal_functions_menu.setEnabled(option);

        if(!domain_coloring || (domain_coloring && use_palette_domain_coloring)) {
            colors_menu.setEnabled(option);
        }

        iterations_menu.setEnabled(option);
        size_of_image.setEnabled(option);

        height_ratio_number.setEnabled(option);

        if(!domain_coloring || (domain_coloring && use_palette_domain_coloring)) {
            custom_palette_button.setEnabled(option);
            random_palette_button.setEnabled(option);
        }
        iterations_button.setEnabled(option);
        rotation_button.setEnabled(option);
        save_image_button.setEnabled(option);

        if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
            bailout_number.setEnabled(option);
            bailout_test_menu.setEnabled(option);
        }

        optimizations_menu.setEnabled(option);
        tools_options_menu.setEnabled(option);

        if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && in_coloring_algorithm == MAXIMUM_ITERATIONS && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
            periodicity_checking_opt.setEnabled(option);
        }

        if(!domain_coloring && !d3 && !julia_map) {
            boundary_tracing_opt.setEnabled(option);
        }

        if(((!julia && !orbit) || (!first_seed && !orbit)) && !domain_coloring && !zoom_window && !julia_map && !d3 && !perturbation && !init_val && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            julia_opt.setEnabled(option);
            julia_button.setEnabled(option);
        }

        if(!zoom_window && !orbit && !color_cycling && !d3 && !domain_coloring) {
            color_cycling_opt.setEnabled(option);
            color_cycling_button.setEnabled(option);
        }

        if(!color_cycling && !julia_map) {
            domain_coloring_opt.setEnabled(option);
            domain_coloring_button.setEnabled(option);
        }

        plane_visualization_opt.setEnabled(option);

        if(!domain_coloring || (domain_coloring && !use_palette_domain_coloring)) {

            if(function == MANDELBROT && !burning_ship && !mandel_grass && !init_val && !perturbation) {
                exterior_de_opt.setEnabled(option);
            }

            bump_map_opt.setEnabled(option);

            fake_de_opt.setEnabled(option);

            rainbow_palette_opt.setEnabled(option);

            out_coloring_mode_menu.setEnabled(option);
            in_coloring_mode_menu.setEnabled(option);
            fract_color.setEnabled(option);
        }

        if(!zoom_window && !grid) {
            polar_projection_opt.setEnabled(option);
            polar_projection_button.setEnabled(option);
        }

        filters_menu.getColorsFiltersMenu().setEnabled(option);
        filters_menu.getDetailsFiltersMenu().setEnabled(option);
        filters_menu.getTextureFiltersMenu().setEnabled(option);

        filters_options.setEnabled(option);
        filters_options_button.setEnabled(option);

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3 && !polar_projection) {
            grid_opt.setEnabled(option);
        }

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3 && size >= 0.05) {
            boundaries_opt.setEnabled(option);
        }

        if(!d3 && !orbit && !julia_map && !polar_projection) {
            zoom_window_opt.setEnabled(option);
        }

        zoom_in.setEnabled(option);
        zoom_in_button.setEnabled(option);

        zoom_out.setEnabled(option);
        zoom_out_button.setEnabled(option);

        up.setEnabled(option);
        down.setEnabled(option);

        left.setEnabled(option);
        right.setEnabled(option);

        if(!julia_map && !d3 && image_size < 4001 && !zoom_window) {
            orbit_opt.setEnabled(option);
            orbit_button.setEnabled(option);
        }
        planes_menu.setEnabled(option);

        if(!domain_coloring && !zoom_window && !julia && !perturbation && !init_val && !orbit && !d3 && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            julia_map_opt.setEnabled(option);
            julia_map_button.setEnabled(option);
        }

        if(!zoom_window && !orbit && !julia_map && !grid && !boundaries) {
            d3_opt.setEnabled(option);
            d3_button.setEnabled(option);
        }

        rotation_menu.setEnabled(option);

        if(!exterior_de && out_coloring_algorithm != DISTANCE_ESTIMATOR && !julia && !julia_map && (function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && user_formula_c == true))) {
            perturbation_opt.setEnabled(option);
            init_val_opt.setEnabled(option);
        }

        overview_opt.setEnabled(option);
        overview_button.setEnabled(option);

    }

    private void setLine() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = 0;
        dot.setSelected(false);
        dot.setEnabled(true);
        line.setSelected(true);
        line.setEnabled(false);

    }

    private void setDot() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = 1;
        line.setSelected(false);
        line.setEnabled(true);
        dot.setSelected(true);
        dot.setEnabled(false);

    }

    private void setShowOrbitConvergingPoint() {

        if(!show_orbit_converging_point_opt.isSelected()) {
            show_orbit_converging_point = false;
        }
        else {
            show_orbit_converging_point = true;
        }
    }

    private void setZoomWindowDashedLine() {

        zoom_window_style = 0;
        zoom_window_line.setSelected(false);
        zoom_window_line.setEnabled(true);
        zoom_window_dashed_line.setSelected(true);
        zoom_window_dashed_line.setEnabled(false);

    }

    private void setZoomWindowLine() {

        zoom_window_style = 1;
        zoom_window_dashed_line.setSelected(false);
        zoom_window_dashed_line.setEnabled(true);
        zoom_window_line.setSelected(true);
        zoom_window_line.setEnabled(false);

    }

    private void setOrbitColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Orbit Color", orbit_color, 0);

        main_panel.repaint();

    }

    private void setGridColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Grid Color", grid_color, 1);

        main_panel.repaint();

    }

    private void setZoomWindowColor() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new ColorChooserFrame(ptr, ptr, "Zoom Window Color", zoom_window_color, 2);

        main_panel.repaint();

    }

    private void setBoundariesColor() {

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

        fractal_functions[function].setSelected(false);
        fractal_functions[function].setEnabled(true);

        int temp2 = function;
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
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }
                fractal_functions[function].setSelected(true);

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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                z_exponent_complex[0] = temp3 == 0.0 ? 0.0 : temp3;
                z_exponent_complex[1] = temp4 == 0.0 ? 0.0 : temp4;

                fractal_functions[function].setSelected(true);
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
                fractal_functions[function].setEnabled(false);
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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

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
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

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
                fractal_functions[function].setSelected(true);
                break;

            case NEWTONFORMULA:

                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                }

                JTextField field_fz_formula = new JTextField(37);
                field_fz_formula.setText(user_fz_formula);
                field_fz_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula = new JTextField(37);
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

                Object[] labels4 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_dfz_formula.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula.getText();
                        user_dfz_formula = field_dfz_formula.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case HALLEYFORMULA:

                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula2 = new JTextField(37);
                field_fz_formula2.setText(user_fz_formula);
                field_fz_formula2.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula2 = new JTextField(37);
                field_dfz_formula2.setText(user_dfz_formula);

                JTextField field_ddfz_formula2 = new JTextField(37);
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

                Object[] labels41 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_dfz_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_ddfz_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula2.getText();
                        user_dfz_formula = field_dfz_formula2.getText();
                        user_ddfz_formula = field_ddfz_formula2.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case SCHRODERFORMULA:

                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula3 = new JTextField(37);
                field_fz_formula3.setText(user_fz_formula);
                field_fz_formula3.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula3 = new JTextField(37);
                field_dfz_formula3.setText(user_dfz_formula);

                JTextField field_ddfz_formula3 = new JTextField(37);
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

                Object[] labels42 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_dfz_formula3.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_ddfz_formula3.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula3.getText();
                        user_dfz_formula = field_dfz_formula3.getText();
                        user_ddfz_formula = field_ddfz_formula3.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERFORMULA:

                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                    user_dfz_formula = "3*z^2";
                    user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula4 = new JTextField(37);
                field_fz_formula4.setText(user_fz_formula);
                field_fz_formula4.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula4 = new JTextField(37);
                field_dfz_formula4.setText(user_dfz_formula);

                JTextField field_ddfz_formula4 = new JTextField(37);
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

                Object[] labels43 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_dfz_formula4.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_ddfz_formula4.getText());

                        if(parser.foundBail() || parser.foundCbail() || parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula4.getText();
                        user_dfz_formula = field_dfz_formula4.getText();
                        user_ddfz_formula = field_ddfz_formula4.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case SECANTFORMULA:
                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula5 = new JTextField(37);
                field_fz_formula5.setText(user_fz_formula);
                field_fz_formula5.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel5 = new JPanel();

                formula_fz_panel5.add(new JLabel("f(z) ="));
                formula_fz_panel5.add(field_fz_formula5);

                JLabel imagelabel44 = new JLabel();
                imagelabel44.setIcon(getIcon("/fractalzoomer/icons/secant.png"));
                JPanel imagepanel44 = new JPanel();
                imagepanel44.add(imagelabel44);

                Object[] labels44 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula5.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case STEFFENSENFORMULA:
                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula6 = new JTextField(37);
                field_fz_formula6.setText(user_fz_formula);
                field_fz_formula6.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel6 = new JPanel();

                formula_fz_panel6.add(new JLabel("f(z) ="));
                formula_fz_panel6.add(field_fz_formula6);

                JLabel imagelabel45 = new JLabel();
                imagelabel45.setIcon(getIcon("/fractalzoomer/icons/steffensen.png"));
                JPanel imagepanel45 = new JPanel();
                imagepanel45.add(imagelabel45);

                Object[] labels45 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula6.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut2();
                break;
            case MULLERFORMULA:
                if(function != temp2) {
                    user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula7 = new JTextField(37);
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

                Object[] labels46 = createUserFormulaLabels("z, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == STEFFENSENFORMULA || temp2 == MULLERPOLY || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        user_fz_formula = field_fz_formula7.getText();
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == STEFFENSENFORMULA || temp2 == MULLERPOLY || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == STEFFENSENFORMULA || temp2 == MULLERPOLY || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == USER_FORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                z_exponent_nova[0] = temp3 == 0.0 ? 0.0 : temp3;
                z_exponent_nova[1] = temp4 == 0.0 ? 0.0 : temp4;

                relaxation[0] = temp5 == 0.0 ? 0.0 : temp5;
                relaxation[1] = temp6 == 0.0 ? 0.0 : temp6;

                nova_method = temp7;

                optionsEnableShortcut();

                periodicity_checking_opt.setEnabled(false);
                bailout_number.setEnabled(false);
                bailout_test_menu.setEnabled(false);
                fractal_functions[function].setSelected(true);
                break;
            case SIERPINSKI_GASKET:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut();
                julia_opt.setEnabled(false);
                julia_button.setEnabled(false);
                julia_map_opt.setEnabled(false);
                julia_map_button.setEnabled(false);
                periodicity_checking_opt.setEnabled(false);
                perturbation_opt.setEnabled(false);
                init_val_opt.setEnabled(false);
                break;
            case USER_FORMULA_ITERATION_BASED:

                JTextField field_formula_it_based1 = new JTextField(37);
                field_formula_it_based1.setText(user_formula_iteration_based[0]);
                field_formula_it_based1.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_it_based2 = new JTextField(37);
                field_formula_it_based2.setText(user_formula_iteration_based[1]);

                JTextField field_formula_it_based3 = new JTextField(37);
                field_formula_it_based3.setText(user_formula_iteration_based[2]);

                JTextField field_formula_it_based4 = new JTextField(37);
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

                Object[] labels32 = createUserFormulaLabels("z, c, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 2nd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based3.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 3rd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_it_based4.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 4th iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
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

                JTextField field_formula_cond1 = new JTextField(37);
                field_formula_cond1.setText(user_formula_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(37);
                field_formula_cond2.setText(user_formula_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(37);
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

                Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_condition2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_cond2.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_cond3.getText());
                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut();
                break;
            case MANDELBROT:
                fractal_functions[function].setEnabled(false);
                if(!burning_ship && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                    exterior_de_opt.setEnabled(true);
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

                JTextField field_formula = new JTextField(37);
                field_formula.setText(user_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_formula2 = new JTextField(37);
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

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        boolean temp_bool = parser.foundC();

                        parser.parse(field_formula2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the c formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA_COUPLED) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut();
                break;
            case USER_FORMULA_COUPLED:
                JTextField field_formula_coupled = new JTextField(37);
                field_formula_coupled.setText(user_formula_coupled[0]);
                field_formula_coupled.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_coupled2 = new JTextField(37);
                field_formula_coupled2.setText(user_formula_coupled[1]);

                JTextField field_formula_coupled3 = new JTextField(37);
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
                coupling_slid.setPreferredSize(new Dimension(300, 45));
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

                final JTextField field_amplitude = new JTextField(6);
                field_amplitude.setText("" + coupling_amplitude);

                final JTextField field_frequency = new JTextField(6);
                field_frequency.setText("" + coupling_frequency);

                final JTextField field_seed = new JTextField(6);
                field_seed.setText("" + coupling_seed);

                String[] coupling_method_str = {"Simple", "Cosine", "Random"};

                final JComboBox coupling_method_choice = new JComboBox(coupling_method_str);
                coupling_method_choice.setSelectedIndex(coupling_method);
                coupling_method_choice.setToolTipText("Selects the coupling method.");
                coupling_method_choice.setFocusable(false);

                JPanel coupling_panel2 = new JPanel();

                coupling_panel2.setPreferredSize(new Dimension(340, 40));
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

                Object[] labels5 = createUserFormulaLabels("z, c, s, p, pp, n, maxn");

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        boolean temp_bool = parser.foundC();

                        parser.parse(field_formula_coupled2.getText());

                        if(parser.foundBail() || parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z2 formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

                            return;
                        }

                        temp_bool = temp_bool | parser.foundC();

                        parser.parse(field_formula_coupled3.getText());
                        if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the z2(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

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
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(true);
                                }
                                else {
                                    fractal_functions[temp2].setSelected(true);
                                    fractal_functions[temp2].setEnabled(false);
                                }
                                function = temp2;
                            }
                            else {
                                fractal_functions[function].setSelected(true);
                                fractal_functions[function].setEnabled(true);
                            }

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
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(true);
                            }
                            else {
                                fractal_functions[temp2].setSelected(true);
                                fractal_functions[temp2].setEnabled(false);
                            }
                            function = temp2;
                        }
                        else {
                            fractal_functions[function].setSelected(true);
                            fractal_functions[function].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();
                    fractal_functions[function].setSelected(false);

                    if(function != temp2) {
                        if(temp2 == MULLERPOLY || temp2 == MULLERFORMULA || temp2 == STEFFENSENPOLY || temp2 == NEWTONFORMULA || temp2 == HALLEYFORMULA || temp2 == SCHRODERFORMULA || temp2 == HOUSEHOLDERFORMULA || temp2 == SECANTFORMULA || temp2 == STEFFENSENFORMULA || temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY || temp2 == USER_FORMULA) {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(true);
                        }
                        else {
                            fractal_functions[temp2].setSelected(true);
                            fractal_functions[temp2].setEnabled(false);
                        }
                        function = temp2;
                    }
                    else {
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(true);
                    }

                    return;
                }

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut();
                break;
            default:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut();
                break;
        }

        user_outcoloring_special_color[0] = false;
        user_outcoloring_special_color[1] = false;
        user_outcoloring_special_color[2] = false;

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

        defaultFractalSettings();

    }

    private void setBailout() {

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
                createThreads();
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

    private void setRotation() {

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

                    double temp_xcenter = xCenter - rotation_center[0];
                    double temp_ycenter = yCenter - rotation_center[1];

                    double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                    double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                    temp1 = temp1 == 0.0 ? 0.0 : temp1;
                    temp2 = temp2 == 0.0 ? 0.0 : temp2;

                    field_real.setText("" + temp1);
                    field_real.setEditable(false);
                    field_imaginary.setText("" + (temp2));
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
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
        }

        backup_orbit = null;

        whole_image_done = false;

        if(julia_map) {
            createThreadsJuliaMap();
        }
        else {
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void increaseRotation() {

        if(rotation == 360) {
            rotation = 0;
        }
        rotation++;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void decreaseRotation() {

        if(rotation == -360) {
            rotation = 0;
        }
        rotation--;

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        setOptions(false);

        progress.setValue(0);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
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

        if(!burning_ship_opt.isSelected()) {
            burning_ship = false;
            if(function == MANDELBROT && !mandel_grass && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                exterior_de_opt.setEnabled(true);
            }
        }
        else {
            burning_ship = true;
            exterior_de_opt.setEnabled(false);
        }

        if(function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    public void setMandelGrass() {

        if(!mandel_grass_opt.isSelected()) {
            mandel_grass = false;
            if(function == MANDELBROT && !burning_ship && !init_val && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                exterior_de_opt.setEnabled(true);
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
                    mandel_grass_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                mandel_grass_opt.setSelected(false);
                return;
            }

            mandel_grass_vals[0] = temp;
            mandel_grass_vals[1] = temp2;

            mandel_grass = true;
            exterior_de_opt.setEnabled(false);
        }

        if(function <= 9 || function == MANDELPOLY || function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    private void startingPosition() {

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
            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
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
            createThreads();
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

    private void setSizeOfImage() {

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

            if(temp > 6500) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be less than than 6501.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            whole_image_done = false;

            old_grid = false;

            old_boundaries = false;

            image_size = temp;

            if(image_size > 4000) {
                orbit_opt.setEnabled(false);
                orbit_button.setEnabled(false);
                orbit_button.setSelected(false);
                orbit_opt.setSelected(false);
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
                createThreads();
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

    private void setFastJuliaFilters() {

        if(!fast_julia_filters_opt.isSelected()) {
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

            start = center - mulx * image_size / 2.0;

            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x1 * mulx + start);

            double temp1 = xCenter + r * cf - rotation_center[0];
            double temp2 = yCenter + r * sf - rotation_center[1];

            temp_xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];

            temp_yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_xcenter_size = xCenter - size_2_x;
            double temp_ycenter_size = yCenter + size_2_y;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;

            double temp1 = temp_xcenter_size + temp_size_image_size_x * x1 - rotation_center[0];
            double temp2 = temp_ycenter_size - temp_size_image_size_y * y1 - rotation_center[1];

            temp_xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
            temp_yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];

        }

        Arrays.fill(((DataBufferInt)fast_julia_image.getRaster().getDataBuffer()).getData(), 0, FAST_JULIA_IMAGE_SIZE * FAST_JULIA_IMAGE_SIZE, 0);

        switch (function) {
            case LAMBDA:
                temp_xCenter = 0.5;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case MAGNET1:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 28;
                temp_bailout = 13;
                break;
            case MAGNET2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 56;
                temp_bailout = 13;
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 7;
                temp_bailout = 2;
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
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA1:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                temp_bailout = 8;
                break;
            case FORMULA2:
            case FORMULA13:
            case FORMULA14:
            case FORMULA15:
            case FORMULA16:
            case FORMULA17:
            case FORMULA19:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                temp_bailout = 4;
                break;
            case FORMULA4:
            case FORMULA5:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                temp_bailout = 4;
                break;
            case FORMULA7:
            case FORMULA12:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 8;
                break;
            case FORMULA8:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                temp_bailout = 16;
                break;
            case FORMULA11:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                temp_bailout = 100;
                break;
            case FORMULA27:
                temp_xCenter = -2;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA28:
            case FORMULA29:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 16;
                break;
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA42:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 12;
                break;
            case FORMULA43:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                temp_bailout = 12;
                break;
            case FORMULA44:
            case FORMULA45:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                temp_bailout = 16;
                break;
            case FORMULA46:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 100;
                break;
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 4;
                break;
            default:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
        }

        ThreadDraw.resetAtomics(n * n);

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

                    if(color_choice != palette.length - 1) {
                        threads[i][j] = new Palette(color_choice, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, temp_bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, temp_xJuliaCenter, temp_yJuliaCenter);
                    }
                    else {
                        threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, temp_bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, temp_xJuliaCenter, temp_yJuliaCenter);
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

    private void setColorCycling() {

        if(!color_cycling_opt.isSelected()) {

            color_cycling = false;
            color_cycling_button.setSelected(false);

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

            if(color_choice == palette.length - 1) {
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
                        createThreads();
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
            color_cycling_button.setSelected(true);

            domain_coloring_opt.setEnabled(false);
            domain_coloring_button.setEnabled(false);

            setOptions(false);

            whole_image_done = false;

            createThreadsColorCycling();

            startThreads(n);
        }

    }

    private void createThreadsPaletteAndFilter() {

        ThreadDraw.resetAtomics(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, dem_color, temp_special_color, color_cycling_location, smoothing, filters, filters_options_vals, filters_options_extra_vals, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, inverse_fake_dem);
                }
                else {
                    threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, dem_color, temp_special_color, color_cycling_location, smoothing, filters, filters_options_vals, filters_options_extra_vals, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, inverse_fake_dem);
                }
            }
        }
    }

    private void createThreadsColorCycling() {

        ThreadDraw.resetAtomics(n * n);

        Color temp_special_color;

        if(special_use_palette_color) {
            temp_special_color = null;
        }
        else {
            temp_special_color = special_color;
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, fractal_color, dem_color, temp_special_color, smoothing, image, color_cycling_location, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, color_cycling_speed, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, inverse_fake_dem);
                }
                else {
                    threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, fractal_color, dem_color, temp_special_color, smoothing, image, color_cycling_location, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, color_cycling_speed, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, inverse_fake_dem);
                }
            }
        }
    }

    private void createThreadsRotate3DModel() {

        ThreadDraw.resetAtomics(n * n);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, true, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors);
                }
                else {
                    threads[i][j] = new CustomPalette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, true, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors);
                }
            }
        }

    }

    private void createThreadsPaletteAndFilter3DModel() {

        ThreadDraw.resetAtomics(n * n);

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, false, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors);
                }
                else {
                    threads[i][j] = new CustomPalette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, d3_size_scale, fiX, fiY, color_3d_blending, false, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors);
                }
            }
        }

    }

    private void shiftPalette() {

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

            if(color_choice == palette.length - 1) {
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
                    createThreads();
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
                    createThreads();
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

        planes[plane_type].setSelected(false);
        planes[plane_type].setEnabled(true);

        int temp2 = plane_type;
        plane_type = temp;

        if(plane_type == USER_PLANE) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(430, 190));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(37);
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
            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(user_plane_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(user_plane_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
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

            Object[] labels3 = createUserFormulaLabels("z");

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

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }
                            return;
                        }
                    }
                    else {
                        parser.parse(field_condition.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_condition2.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_formula_cond2.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }

                            return;
                        }

                        parser.parse(field_formula_cond3.getText());

                        if(parser.foundC() || parser.foundN() || parser.foundP() || parser.foundS() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail, maxn cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();

                            planes[plane_type].setSelected(false);

                            if(plane_type != temp2) {
                                if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(true);
                                }
                                else {
                                    planes[temp2].setSelected(true);
                                    planes[temp2].setEnabled(false);
                                }
                                plane_type = temp2;
                            }
                            else {
                                planes[plane_type].setSelected(true);
                                planes[plane_type].setEnabled(true);
                            }

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

                    planes[plane_type].setSelected(true);

                    defaultFractalSettings();

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();

                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
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
                "Set the twirl center.",
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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Twirl radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;

            planes[plane_type].setSelected(true);

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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            plane_transform_scales[0] = temp3 == 0.0 ? 0.0 : temp3;
            plane_transform_scales[1] = temp4 == 0.0 ? 0.0 : temp4;

            planes[plane_type].setSelected(true);

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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                "Set the kaleidoscope center.",
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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(temp6 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope sides must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;
            plane_transform_angle2 = temp5;
            plane_transform_sides = temp6;

            planes[plane_type].setSelected(true);

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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
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
                "Set the pinch center.",
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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Pinch radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_angle = temp3;
            plane_transform_radius = temp4;
            plane_transform_amount = temp5 == 0.0 ? 0.0 : temp5;

            planes[plane_type].setSelected(true);

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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            String str = "Set the point about the fold.";
            String title = "Fold";
            if(plane_type == INFLECTION_PLANE) {
                str = "Set the point about the inflection.";
                title = "Inflection";
            }
            else if(plane_type == BIPOLAR_PLANE || plane_type == INVERSED_BIPOLAR_PLANE) {
                str = "Set the focal point.";
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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;

            planes[plane_type].setSelected(true);

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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            if(temp3 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Fold radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_radius = temp3;

            planes[plane_type].setSelected(true);

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

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + temp2);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            Object[] message = {
                " ",
                "Set the circle inversion radius.",
                "Radius:", field_radius,
                " ",
                "Set the circle inversion center.",
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
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(true);
                        }
                        else {
                            planes[temp2].setSelected(true);
                            planes[temp2].setEnabled(false);
                        }
                        plane_type = temp2;
                    }
                    else {
                        planes[plane_type].setSelected(true);
                        planes[plane_type].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                return;
            }

            if(temp4 <= 0) {
                main_panel.repaint();
                planes[plane_type].setSelected(false);

                if(plane_type != temp2) {
                    if(temp2 == INFLECTION_PLANE || temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE || temp2 == BIPOLAR_PLANE || temp2 == INVERSED_BIPOLAR_PLANE) {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(true);
                    }
                    else {
                        planes[temp2].setSelected(true);
                        planes[temp2].setEnabled(false);
                    }
                    plane_type = temp2;
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(true);
                }
                JOptionPane.showMessageDialog(scroll_pane, "Circle inversion radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            plane_transform_center[0] = tempReal;
            plane_transform_center[1] = tempImaginary;
            plane_transform_radius = temp4;

            planes[plane_type].setSelected(true);

            defaultFractalSettings();
        }
        else {
            planes[plane_type].setEnabled(false);

            defaultFractalSettings();
        }
    }

    public void setBailoutTest(int temp) {

        bailout_tests[bailout_test_algorithm].setSelected(false);
        bailout_tests[bailout_test_algorithm].setEnabled(true);

        int temp2 = bailout_test_algorithm;
        bailout_test_algorithm = temp;

        if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
            if(backup_orbit != null && orbit) {
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the new N-Norm number.", "N-Norm", JOptionPane.QUESTION_MESSAGE);

            try {
                double temp3 = Double.parseDouble(ans);

                n_norm = temp3;

            }
            catch(Exception ex) {
                if(ans == null) {
                    main_panel.repaint();

                    bailout_tests[bailout_test_algorithm].setSelected(false);

                    if(bailout_test_algorithm != temp2) {
                        if(temp2 == BAILOUT_TEST_USER) {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(true);
                        }
                        else {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(false);
                        }
                        bailout_test_algorithm = temp2;
                    }
                    else {
                        bailout_tests[bailout_test_algorithm].setSelected(true);
                        bailout_tests[bailout_test_algorithm].setEnabled(true);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();

                    bailout_tests[bailout_test_algorithm].setSelected(false);

                    if(bailout_test_algorithm != temp2) {
                        if(temp2 == BAILOUT_TEST_USER) {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(true);
                        }
                        else {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(false);
                        }
                        bailout_test_algorithm = temp2;
                    }
                    else {
                        bailout_tests[bailout_test_algorithm].setSelected(true);
                        bailout_tests[bailout_test_algorithm].setEnabled(true);
                    }
                }
                return;
            }

            bailout_tests[bailout_test_algorithm].setSelected(true);

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

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail");

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

                        bailout_tests[bailout_test_algorithm].setSelected(false);

                        if(bailout_test_algorithm != temp2) {
                            if(temp2 == BAILOUT_TEST_NNORM) {
                                bailout_tests[temp2].setSelected(true);
                                bailout_tests[temp2].setEnabled(true);
                            }
                            else {
                                bailout_tests[temp2].setSelected(true);
                                bailout_tests[temp2].setEnabled(false);
                            }
                            bailout_test_algorithm = temp2;
                        }
                        else {
                            bailout_tests[bailout_test_algorithm].setSelected(true);
                            bailout_tests[bailout_test_algorithm].setEnabled(true);
                        }
                        return;
                    }

                    parser.parse(field_condition2.getText());

                    if(parser.foundCbail()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();

                        bailout_tests[bailout_test_algorithm].setSelected(false);

                        if(bailout_test_algorithm != temp2) {
                            if(temp2 == BAILOUT_TEST_NNORM) {
                                bailout_tests[temp2].setSelected(true);
                                bailout_tests[temp2].setEnabled(true);
                            }
                            else {
                                bailout_tests[temp2].setSelected(true);
                                bailout_tests[temp2].setEnabled(false);
                            }
                            bailout_test_algorithm = temp2;
                        }
                        else {
                            bailout_tests[bailout_test_algorithm].setSelected(true);
                            bailout_tests[bailout_test_algorithm].setEnabled(true);
                        }
                        return;
                    }

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    bailout_tests[bailout_test_algorithm].setSelected(false);

                    if(bailout_test_algorithm != temp2) {
                        if(temp2 == BAILOUT_TEST_NNORM) {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(true);
                        }
                        else {
                            bailout_tests[temp2].setSelected(true);
                            bailout_tests[temp2].setEnabled(false);
                        }
                        bailout_test_algorithm = temp2;
                    }
                    else {
                        bailout_tests[bailout_test_algorithm].setSelected(true);
                        bailout_tests[bailout_test_algorithm].setEnabled(true);
                    }
                    return;
                }
            }
            else {
                main_panel.repaint();
                bailout_tests[bailout_test_algorithm].setSelected(false);

                if(bailout_test_algorithm != temp2) {
                    if(temp2 == BAILOUT_TEST_NNORM) {
                        bailout_tests[temp2].setSelected(true);
                        bailout_tests[temp2].setEnabled(true);
                    }
                    else {
                        bailout_tests[temp2].setSelected(true);
                        bailout_tests[temp2].setEnabled(false);
                    }
                    bailout_test_algorithm = temp2;
                }
                else {
                    bailout_tests[bailout_test_algorithm].setSelected(true);
                    bailout_tests[bailout_test_algorithm].setEnabled(true);
                }
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

            bailout_tests[bailout_test_algorithm].setSelected(true);
        }
        else {
            bailout_tests[bailout_test_algorithm].setEnabled(false);
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
            createThreads();
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

        out_coloring_modes[out_coloring_algorithm].setSelected(false);
        out_coloring_modes[out_coloring_algorithm].setEnabled(true);

        int temp2 = out_coloring_algorithm;

        out_coloring_algorithm = temp;

        for(int k = 0; k < fractal_functions.length; k++) {
            if(function != k) {
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
        }

        if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de) {
            for(int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
            init_val_opt.setEnabled(false);
            perturbation_opt.setEnabled(false);

            out_coloring_modes[out_coloring_algorithm].setEnabled(false);
        }
        else if(out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || out_coloring_algorithm == ESCAPE_TIME_GRID || out_coloring_algorithm == BIOMORPH || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || out_coloring_algorithm == ITERATIONS_PLUS_RE || out_coloring_algorithm == ITERATIONS_PLUS_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);

            out_coloring_modes[out_coloring_algorithm].setEnabled(false);
        }
        else if(out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(495, 190));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(50);
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

            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(user_outcoloring_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(user_outcoloring_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
            field_formula_cond3.setText(user_outcoloring_condition_formula[2]);

            JPanel formula_panel_cond11 = new JPanel();

            JCheckBox special_alg1 = new JCheckBox("Special");
            special_alg1.setFocusable(false);
            special_alg1.setSelected(user_outcoloring_special_color[0]);
            special_alg1.setToolTipText("Enables the use of the special color.");
            JCheckBox special_alg2 = new JCheckBox("Special");
            special_alg2.setFocusable(false);
            special_alg2.setSelected(user_outcoloring_special_color[1]);
            special_alg2.setToolTipText("Enables the use of the special color.");
            JCheckBox special_alg3 = new JCheckBox("Special");
            special_alg3.setFocusable(false);
            special_alg3.setToolTipText("Enables the use of the special color.");
            special_alg3.setSelected(user_outcoloring_special_color[2]);

            formula_panel_cond11.add(new JLabel("left > right, out ="));
            formula_panel_cond11.add(field_formula_cond1);
            formula_panel_cond11.add(special_alg1);

            JPanel formula_panel_cond12 = new JPanel();

            formula_panel_cond12.add(new JLabel("left < right, out ="));
            formula_panel_cond12.add(field_formula_cond2);
            formula_panel_cond12.add(special_alg2);

            JPanel formula_panel_cond13 = new JPanel();

            formula_panel_cond13.add(new JLabel("left = right, out ="));
            formula_panel_cond13.add(field_formula_cond3);
            formula_panel_cond13.add(special_alg3);

            JPanel panel_cond = new JPanel();
            panel_cond.setLayout(new FlowLayout());

            panel_cond.add(formula_panel_cond1);
            panel_cond.add(formula_panel_cond11);
            panel_cond.add(formula_panel_cond12);
            panel_cond.add(formula_panel_cond13);

            tabbedPane.addTab("Conditional", panel_cond);

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, cbail");

            tabbedPane.setSelectedIndex(user_out_coloring_algorithm);

            Object[] message3 = {
                labels33,
                " ",
                "Set the out coloring formula. The absolute value of the real part will be used.",
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

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

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

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }

                        parser.parse(field_condition2.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }

                        parser.parse(field_formula_cond1.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }

                        parser.parse(field_formula_cond2.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }

                        parser.parse(field_formula_cond3.getText());

                        if(function == MULLER3 || function == MULLER4 || function == MULLERGENERALIZED3 || function == MULLERGENERALIZED8 || function == MULLERSIN || function == MULLERCOS || function == MULLERPOLY || function == MULLERFORMULA || function == NEWTON3 || function == STEFFENSENPOLY || function == NEWTONFORMULA || function == HALLEYFORMULA || function == SCHRODERFORMULA || function == HOUSEHOLDERFORMULA || function == SECANTFORMULA || function == STEFFENSENFORMULA || function == NEWTON4 || function == NEWTONGENERALIZED3 || function == NEWTONGENERALIZED8 || function == NEWTONSIN || function == NEWTONCOS || function == NEWTONPOLY || function == HALLEY3 || function == HALLEY4 || function == HALLEYGENERALIZED3 || function == HALLEYGENERALIZED8 || function == HALLEYSIN || function == HALLEYCOS || function == HALLEYPOLY || function == SCHRODER3 || function == SCHRODER4 || function == SCHRODERGENERALIZED3 || function == SCHRODERGENERALIZED8 || function == SCHRODERSIN || function == SCHRODERCOS || function == SCHRODERPOLY || function == HOUSEHOLDER3 || function == HOUSEHOLDER4 || function == HOUSEHOLDERGENERALIZED3 || function == HOUSEHOLDERGENERALIZED8 || function == HOUSEHOLDERSIN || function == HOUSEHOLDERCOS || function == HOUSEHOLDERPOLY || function == NOVA || function == SECANT3 || function == SECANT4 || function == SECANTGENERALIZED3 || function == SECANTGENERALIZED8 || function == SECANTCOS || function == SECANTPOLY || function == STEFFENSEN3 || function == STEFFENSEN4 || function == STEFFENSENGENERALIZED3 || (function == USER_FORMULA && bail_technique == 1) || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 1) || (function == USER_FORMULA_CONDITIONAL && bail_technique == 1) || (function == USER_FORMULA_COUPLED && bail_technique == 1)) {
                            if(parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            if(parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                                if(out_coloring_algorithm != temp2) {
                                    out_coloring_modes[temp2].setSelected(true);
                                    out_coloring_modes[temp2].setEnabled(false);
                                    out_coloring_algorithm = temp2;
                                }
                                else {
                                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                    }

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();

                    out_coloring_modes[out_coloring_algorithm].setSelected(false);

                    if(out_coloring_algorithm != temp2) {
                        out_coloring_modes[temp2].setSelected(true);
                        out_coloring_modes[temp2].setEnabled(false);
                        out_coloring_algorithm = temp2;
                    }
                    else {
                        out_coloring_modes[out_coloring_algorithm].setSelected(true);
                        out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                    }

                    return;
                }
            }
            else {
                main_panel.repaint();

                out_coloring_modes[out_coloring_algorithm].setSelected(false);

                if(out_coloring_algorithm != temp2) {
                    out_coloring_modes[temp2].setSelected(true);
                    out_coloring_modes[temp2].setEnabled(false);
                    out_coloring_algorithm = temp2;
                }
                else {
                    out_coloring_modes[out_coloring_algorithm].setSelected(true);
                    out_coloring_modes[out_coloring_algorithm].setEnabled(true);
                }

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

                user_outcoloring_special_color[0] = special_alg1.isSelected();
                user_outcoloring_special_color[1] = special_alg2.isSelected();
                user_outcoloring_special_color[2] = special_alg3.isSelected();
            }

            if(!julia_map && !julia && !perturbation && !init_val && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
                rootFindingMethodsSetEnabled(true);
            }

            out_coloring_modes[out_coloring_algorithm].setSelected(true);
        }
        else {
            if(function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
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

            }
            out_coloring_modes[BIOMORPH].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setSelected(false);
            out_coloring_modes[ITERATIONS_PLUS_RE].setSelected(false);
            out_coloring_modes[ITERATIONS_PLUS_IM].setSelected(false);
            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setSelected(false);
            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setSelected(false);
            out_coloring_modes[ESCAPE_TIME_GRID].setSelected(false);
            out_coloring_modes[BANDED].setSelected(false);

            if(!julia_map && !julia && !perturbation && !init_val && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
                rootFindingMethodsSetEnabled(true);
            }

            out_coloring_modes[out_coloring_algorithm].setEnabled(false);
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
            createThreads();
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

        in_coloring_modes[in_coloring_algorithm].setSelected(false);
        in_coloring_modes[in_coloring_algorithm].setEnabled(true);

        int temp2 = in_coloring_algorithm;

        in_coloring_algorithm = temp;

        if(in_coloring_algorithm == MAXIMUM_ITERATIONS) {
            if(!domain_coloring && function != MULLER3 && function != MULLER4 && function != MULLERGENERALIZED3 && function != MULLERGENERALIZED8 && function != MULLERSIN && function != MULLERCOS && function != MULLERPOLY && function != MULLERFORMULA && function != NEWTON3 && function != STEFFENSENPOLY && function != NEWTONFORMULA && function != HALLEYFORMULA && function != SCHRODERFORMULA && function != HOUSEHOLDERFORMULA && function != SECANTFORMULA && function != STEFFENSENFORMULA && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0)) && (function != USER_FORMULA_COUPLED || (function == USER_FORMULA_COUPLED && bail_technique == 0))) {
                periodicity_checking_opt.setEnabled(true);
            }
            in_coloring_modes[in_coloring_algorithm].setEnabled(false);
        }
        else {
            if(in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.setPreferredSize(new Dimension(495, 190));
                tabbedPane.setFocusable(false);

                JTextField field_formula = new JTextField(50);
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

                JTextField field_formula_cond1 = new JTextField(37);
                field_formula_cond1.setText(user_incoloring_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(37);
                field_formula_cond2.setText(user_incoloring_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(37);
                field_formula_cond3.setText(user_incoloring_condition_formula[2]);

                JPanel formula_panel_cond11 = new JPanel();

                JCheckBox special_alg1 = new JCheckBox("Special");
                special_alg1.setFocusable(false);
                special_alg1.setSelected(user_incoloring_special_color[0]);
                special_alg1.setToolTipText("Enables the use of the special color.");
                JCheckBox special_alg2 = new JCheckBox("Special");
                special_alg2.setFocusable(false);
                special_alg2.setSelected(user_incoloring_special_color[1]);
                special_alg2.setToolTipText("Enables the use of the special color.");
                JCheckBox special_alg3 = new JCheckBox("Special");
                special_alg3.setFocusable(false);
                special_alg3.setToolTipText("Enables the use of the special color.");
                special_alg3.setSelected(user_incoloring_special_color[2]);

                formula_panel_cond11.add(new JLabel("left > right, in ="));
                formula_panel_cond11.add(field_formula_cond1);
                formula_panel_cond11.add(special_alg1);

                JPanel formula_panel_cond12 = new JPanel();

                formula_panel_cond12.add(new JLabel("left < right, in ="));
                formula_panel_cond12.add(field_formula_cond2);
                formula_panel_cond12.add(special_alg2);

                JPanel formula_panel_cond13 = new JPanel();

                formula_panel_cond13.add(new JLabel("left = right, in ="));
                formula_panel_cond13.add(field_formula_cond3);
                formula_panel_cond13.add(special_alg3);

                JPanel panel_cond = new JPanel();

                panel_cond.setLayout(new FlowLayout());

                panel_cond.add(formula_panel_cond1);
                panel_cond.add(formula_panel_cond11);
                panel_cond.add(formula_panel_cond12);
                panel_cond.add(formula_panel_cond13);

                tabbedPane.addTab("Conditional", panel_cond);

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, maxn");

                tabbedPane.setSelectedIndex(user_in_coloring_algorithm);

                Object[] message3 = {
                    labels3,
                    " ",
                    "Set the in coloring formula. The absolute value of the real part will be used.",
                    tabbedPane,};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User In Coloring Method", JOptionPane.OK_CANCEL_OPTION);

                if(res == JOptionPane.OK_OPTION) {
                    try {
                        if(tabbedPane.getSelectedIndex() == 0) {
                            parser.parse(field_formula.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left > right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left < right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundBail() || parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: n, bail, cbail cannot be used in the left = right in formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();

                                in_coloring_modes[in_coloring_algorithm].setSelected(false);

                                if(in_coloring_algorithm != temp2) {
                                    in_coloring_modes[temp2].setSelected(true);
                                    in_coloring_modes[temp2].setEnabled(false);
                                    in_coloring_algorithm = temp2;
                                }
                                else {
                                    in_coloring_modes[in_coloring_algorithm].setSelected(true);
                                    in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                                }

                                return;
                            }
                        }

                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();

                        in_coloring_modes[in_coloring_algorithm].setSelected(false);

                        if(in_coloring_algorithm != temp2) {
                            in_coloring_modes[temp2].setSelected(true);
                            in_coloring_modes[temp2].setEnabled(false);
                            in_coloring_algorithm = temp2;
                        }
                        else {
                            in_coloring_modes[in_coloring_algorithm].setSelected(true);
                            in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                        }

                        return;
                    }
                }
                else {
                    main_panel.repaint();

                    in_coloring_modes[in_coloring_algorithm].setSelected(false);

                    if(in_coloring_algorithm != temp2) {
                        in_coloring_modes[temp2].setSelected(true);
                        in_coloring_modes[temp2].setEnabled(false);
                        in_coloring_algorithm = temp2;
                    }
                    else {
                        in_coloring_modes[in_coloring_algorithm].setSelected(true);
                        in_coloring_modes[in_coloring_algorithm].setEnabled(true);
                    }

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

                    user_incoloring_special_color[0] = special_alg1.isSelected();
                    user_incoloring_special_color[1] = special_alg2.isSelected();
                    user_incoloring_special_color[2] = special_alg3.isSelected();
                }

                in_coloring_modes[in_coloring_algorithm].setSelected(true);
            }
            else {
                in_coloring_modes[in_coloring_algorithm].setEnabled(false);
            }
            periodicity_checking_opt.setEnabled(false);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void setSmoothing() {

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

        Object[] message = {
            " ",
            enable_smoothing,
            " ",
            "Set the smoothing algorithm for escaping and converging functions.",
            "Escaping:", escaping_alg_combo,
            "Converging:", converging_alg_combo,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Smoothing", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {

            smoothing = enable_smoothing.isSelected();
            escaping_smooth_algorithm = escaping_alg_combo.getSelectedIndex();
            converging_smooth_algorithm = converging_alg_combo.getSelectedIndex();

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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void setBumpMap() {

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
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Bump Mapping", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            if(boundary_tracing && enable_bump_map.isSelected() && !julia_map && !d3) {
                JOptionPane.showMessageDialog(scroll_pane, "Boundary tracing is enabled, which creates glitches in the image.\nYou should disable boundary tracing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            bump_map = enable_bump_map.isSelected();
            lightDirectionDegrees = direction_of_light.getValue();
            bumpMappingDepth = depth.getValue();
            bumpMappingStrength = strength.getValue();

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
                    createThreads();
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
                    createThreads();
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

    private void setRainwbowPalette() {

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

        Object[] message = {
            " ",
            enable_rainbow_palette,
            " ",
            "Set the rainbow palette factor.",
            "Rainbow Palette factor:", rainbow_palette_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Rainbow Palette", JOptionPane.OK_CANCEL_OPTION);

        double temp;
        if(res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(rainbow_palette_factor_field.getText());
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

        if(boundary_tracing && enable_rainbow_palette.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Boundary tracing is enabled, which creates glitches in the image.\nYou should disable boundary tracing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        rainbow_palette = enable_rainbow_palette.isSelected();
        rainbow_palette_factor = temp;

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
                createThreads();
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
                createThreads();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setFakeDistanceEstimation() {

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

        if(boundary_tracing && enable_fake_de.isSelected() && !julia_map && !d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Boundary tracing is enabled, which creates glitches in the image.\nYou should disable boundary tracing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        fake_de = enable_fake_de.isSelected();
        fake_de_factor = temp;
        inverse_fake_dem = invert_fake_de.isSelected();

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
                createThreads();
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
                createThreads();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setExteriorDistanceEstimation() {

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

            burning_ship_opt.setEnabled(false);
            mandel_grass_opt.setEnabled(false);
            init_val_opt.setEnabled(false);
            perturbation_opt.setEnabled(false);

        }
        else {
            for(int k = 0; k < fractal_functions.length; k++) {
                if(function != k) {
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
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !julia && !julia_map && !perturbation && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            burning_ship_opt.setEnabled(true);
            mandel_grass_opt.setEnabled(true);
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void setJuliaMap() {

        if(!julia_map_opt.isSelected()) {
            julia_map = false;
            julia_map_button.setSelected(false);

            julia_map_options_opt.setEnabled(false);

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

            createThreads();

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
                    julia_map_opt.setSelected(false);
                    julia_map_button.setSelected(false);
                    return;
                }
                else {
                    if(temp > 200) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be less than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
                        julia_map_opt.setSelected(false);
                        julia_map_button.setSelected(false);
                        return;
                    }
                }

                julia_grid_first_dimension = temp;
                threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

                julia_map_button.setSelected(true);

                julia_map_options_opt.setEnabled(true);

                setOptions(false);

                julia_map = true;

                rootFindingMethodsSetEnabled(false);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(false);

                perturbation_opt.setEnabled(false);
                init_val_opt.setEnabled(false);

                boundary_tracing_opt.setEnabled(false);

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
                    julia_map_opt.setSelected(false);
                    julia_map_button.setSelected(false);
                    main_panel.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    julia_map_opt.setSelected(false);
                    julia_map_button.setSelected(false);
                    main_panel.repaint();
                }
            }

        }

    }

    private void setJuliaMapOptions() {

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

            brush.drawLine(x1 - 8, y1, x1 + 8, y1);
            brush.drawLine(x1, y1 - 8, x1, y1 + 8);
        }
        else {
            brush.setColor(zoom_window_color);
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            brush.drawRect(x1 - new_size / 2, y1 - new_size / 2, new_size, new_size);
            brush.setFont(new Font("Arial", Font.BOLD, 16));
            brush.drawString("x" + String.format("%4.2f", zoom_factor), x1 - new_size / 2 + 5, y1 - new_size / 2 - 5);
            brush.drawLine(x1 - 8, y1, x1 + 8, y1);
            brush.drawLine(x1, y1 - 8, x1, y1 + 8);
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

            start = center - mulx * image_size / 2.0;

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

        new CustomPaletteEditorFrame(ptr, palette, smoothing, boundary_tracing, d3, julia_map, number, color_choice, custom_palette, coloring_option, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg);

    }

    public boolean getJuliaMap() {

        return julia_map;

    }

    private void createThreadsJuliaMap() {

        ThreadDraw.resetAtomics(julia_grid_first_dimension * julia_grid_first_dimension);

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
                    if(color_choice != palette.length - 1) {
                        threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem);
                    }
                    else {
                        threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, scale_factor_palette_val, processing_alg, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, temp_special_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, user_outcoloring_special_color, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, user_incoloring_special_color, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, color_intensity, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem);
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

    private void increaseIterations() {

        if(max_iterations >= 100000) {
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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void decreaseIterations() {

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
            createThreads();
        }

        calculation_time = System.currentTimeMillis();

        if(julia_map) {
            startThreads(julia_grid_first_dimension);
        }
        else {
            startThreads(n);
        }

    }

    private void shiftPaletteForward() {

        color_cycling_location++;

        color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;

        if(color_choice == palette.length - 1) {
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
                createThreads();
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
                createThreads();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void shiftPaletteBackward() {

        if(color_cycling_location > 0) {
            color_cycling_location--;

            if(color_choice == palette.length - 1) {
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
                createThreads();
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
                createThreads();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setPerturbation() {

        if(!perturbation_opt.isSelected()) {
            perturbation = false;

            if(function == MANDELBROT && !burning_ship && !init_val && !mandel_grass && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                exterior_de_opt.setEnabled(true);
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

            JTextField field_real = new JTextField(40);
            field_real.setText("" + perturbation_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(40);
            field_imaginary.setText("" + perturbation_vals[1]);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(505, 595));
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

            JLabel html_label = createUserFormulaHemlLabels("c", false);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(37);
            field_formula.setText("" + perturbation_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(460, 190));
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
            info_user.setPreferredSize(new Dimension(75, 23));

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
            code_editor.setPreferredSize(new Dimension(130, 23));

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
            compile_code.setPreferredSize(new Dimension(150, 23));

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
                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                perturbation_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    perturbation_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    perturbation_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                perturbation_opt.setSelected(false);
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
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            exterior_de_opt.setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        }

        defaultFractalSettings();

    }

    private void setInitialValue() {

        if(!init_val_opt.isSelected()) {
            init_val = false;

            if(function == MANDELBROT && !burning_ship && !mandel_grass && !perturbation && (!domain_coloring || domain_coloring && !use_palette_domain_coloring)) {
                exterior_de_opt.setEnabled(true);
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

            JTextField field_real = new JTextField(40);
            field_real.setText("" + initial_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(40);
            field_imaginary.setText("" + initial_vals[1]);

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(490, 595));
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

            JLabel html_label = createUserFormulaHemlLabels("c", true);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(37);
            field_formula.setText("" + initial_value_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(440, 190));
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
            info_user.setPreferredSize(new Dimension(75, 23));

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
            code_editor.setPreferredSize(new Dimension(130, 23));

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
            compile_code.setPreferredSize(new Dimension(150, 23));

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
                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                        else {
                            parser.parse(field_condition.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_condition2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond1.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond2.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            parser.parse(field_formula_cond3.getText());

                            if(parser.foundN() || parser.foundP() || parser.foundS() || parser.foundZ() || parser.foundPP() || parser.foundBail() || parser.foundCbail() || parser.foundMaxn()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail, maxn cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                init_val_opt.setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    init_val_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    init_val_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                main_panel.repaint();
                init_val_opt.setSelected(false);
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
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            exterior_de_opt.setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        }

        defaultFractalSettings();

    }

    private void showCHMHelpFile() {

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

    private void exportCodeFiles() {

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

            compileCode(false);

        }
        catch(Exception ex) {
        }

    }

    private void randomPalette() {

        CustomPaletteEditorFrame.randomPalette(custom_palette, CustomPaletteEditorFrame.getRandomPaletteAlgorithm(), CustomPaletteEditorFrame.getEqualHues(), color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

        setPalette(palette.length - 1);

    }

    private void setToolbar() {

        if(!toolbar_opt.isSelected()) {
            toolbar.setVisible(false);
        }
        else {
            toolbar.setVisible(true);
        }

    }

    private void setStatubar() {

        if(!statusbar_opt.isSelected()) {
            statusbar.setVisible(false);
        }
        else {
            statusbar.setVisible(true);
        }

    }

    private void setInfobar() {

        if(!infobar_opt.isSelected()) {
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

    private void filtersOptions() {

        if(backup_orbit != null && orbit) {
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        new FiltersOptionsFrame(ptr, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors);

    }

    private void setPolarProjection() {

        if(!polar_projection_opt.isSelected()) {
            polar_projection = false;
            polar_projection_button.setSelected(false);

            polar_projection_options_opt.setEnabled(false);

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
                createThreads();
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

            double temp_xcenter = xCenter - rotation_center[0];
            double temp_ycenter = yCenter - rotation_center[1];

            double temp3 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];

            if(temp3 == 0) {
                field_real.setText("" + 0.0);
            }
            else {
                field_real.setText("" + temp3);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            temp3 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

            if(temp3 == 0) {
                field_imaginary.setText("" + 0.0);
            }
            else {
                field_imaginary.setText("" + temp3);
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
                        polar_projection_opt.setSelected(false);
                        polar_projection_button.setSelected(false);
                        return;
                    }

                    size = tempSize;
                    xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1] + rotation_center[0];
                    yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0] + rotation_center[1];
                    circle_period = temp_circle_period;

                    polar_projection = true;
                    polar_projection_button.setSelected(true);

                    grid_opt.setEnabled(false);
                    zoom_window_opt.setEnabled(false);

                    polar_projection_options_opt.setEnabled(true);

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
                        createThreads();
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
                    polar_projection_opt.setSelected(false);
                    polar_projection_button.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                polar_projection_opt.setSelected(false);
                polar_projection_button.setSelected(false);
                main_panel.repaint();
                return;
            }
        }
    }

    private void setPolarProjectionOptions() {
        JTextField field_real = new JTextField();

        double temp_xcenter = xCenter - rotation_center[0];
        double temp_ycenter = yCenter - rotation_center[1];

        double temp3 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];

        if(temp3 == 0) {
            field_real.setText("" + 0.0);
        }
        else {
            field_real.setText("" + temp3);
        }

        field_real.addAncestorListener(new RequestFocusListener());

        JTextField field_imaginary = new JTextField();

        temp3 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

        if(temp3 == 0) {
            field_imaginary.setText("" + 0.0);
        }
        else {
            field_imaginary.setText("" + temp3);
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
                    createThreads();
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

    private void setDomainColoring() {

        if(!domain_coloring_opt.isSelected()) {
            domain_coloring = false;

            domain_coloring_button.setSelected(false);
            domain_coloring_options_opt.setEnabled(false);

            if(!use_palette_domain_coloring) {
                palette_toolbar_preview.setVisible(true);
                palette_toolbar_preview_lbl.setVisible(true);
            }

            max_it_color_preview.setVisible(true);
            max_it_color_preview_lbl.setVisible(true);

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

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }
        else {
            JTextField field_iterations = new JTextField();

            field_iterations.setText("" + max_iterations);

            field_iterations.addAncestorListener(new RequestFocusListener());

            String[] algs = {"Black Grid, White Circles log2", "White Grid, Black Circles log2", "Black Grid, White Circles", "White Grid, Black Circles", "Black Grid", "White Grid", "Black Grid, Contours log2", "White Grid, Contours log2", "Black Grid, Contours", "White Grid, Contours", "Norm, Black Grid, White Circles log2", "Norm, White Grid, Black Circles log2", "Norm, Black Grid, White Circles", "Norm, White Grid, Black Circles", "Norm, Black Grid", "Norm, White Grid"};
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
                        domain_coloring_opt.setSelected(false);
                        domain_coloring_button.setSelected(false);
                        return;
                    }

                    max_iterations = tempIterations;
                    domain_coloring_alg = color_domain_algs_opt.getSelectedIndex();
                    use_palette_domain_coloring = use_palette_dc.isSelected();

                    domain_coloring = true;

                    domain_coloring_button.setSelected(true);

                    domain_coloring_options_opt.setEnabled(true);

                    julia_map_opt.setEnabled(false);
                    julia_map_button.setEnabled(false);
                    julia_opt.setEnabled(false);
                    julia_button.setEnabled(false);
                    color_cycling_opt.setEnabled(false);
                    color_cycling_button.setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        colors_menu.setEnabled(false);
                        custom_palette_button.setEnabled(false);
                        random_palette_button.setEnabled(false);
                    }
                    else {
                        exterior_de_opt.setEnabled(false);
                        bump_map_opt.setEnabled(false);

                        fake_de_opt.setEnabled(false);

                        rainbow_palette_opt.setEnabled(false);
                        out_coloring_mode_menu.setEnabled(false);
                        in_coloring_mode_menu.setEnabled(false);
                        fract_color.setEnabled(false);
                    }

                    boundary_tracing_opt.setEnabled(false);
                    periodicity_checking_opt.setEnabled(false);
                    bailout_number.setEnabled(false);
                    bailout_test_menu.setEnabled(false);

                    if(!use_palette_domain_coloring) {
                        palette_toolbar_preview.setVisible(false);
                        palette_toolbar_preview_lbl.setVisible(false);
                    }
                    else {
                        palette_toolbar_preview.setVisible(true);
                        palette_toolbar_preview_lbl.setVisible(true);
                    }

                    max_it_color_preview.setVisible(false);
                    max_it_color_preview_lbl.setVisible(false);

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

                    createThreads();

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);

                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    domain_coloring_opt.setSelected(false);
                    domain_coloring_button.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                domain_coloring_opt.setSelected(false);
                domain_coloring_button.setSelected(false);
                main_panel.repaint();
                return;
            }
        }
    }

    private void setDomainColoringOptions() {
        JTextField field_iterations = new JTextField();

        field_iterations.setText("" + max_iterations);

        field_iterations.addAncestorListener(new RequestFocusListener());

        String[] algs = {"Black Grid, White Circles log2", "White Grid, Black Circles log2", "Black Grid, White Circles", "White Grid, Black Circles", "Black Grid", "White Grid", "Black Grid, Contours log2", "White Grid, Contours log2", "Black Grid, Contours", "White Grid, Contours", "Norm, Black Grid, White Circles log2", "Norm, White Grid, Black Circles log2", "Norm, Black Grid, White Circles", "Norm, White Grid, Black Circles", "Norm, Black Grid", "Norm, White Grid"};
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
                    colors_menu.setEnabled(false);
                    custom_palette_button.setEnabled(false);
                    random_palette_button.setEnabled(false);
                }
                else {
                    exterior_de_opt.setEnabled(false);
                    bump_map_opt.setEnabled(false);

                    fake_de_opt.setEnabled(false);

                    rainbow_palette_opt.setEnabled(false);
                    out_coloring_mode_menu.setEnabled(false);
                    in_coloring_mode_menu.setEnabled(false);
                    fract_color.setEnabled(false);
                }

                if(!use_palette_domain_coloring) {
                    palette_toolbar_preview.setVisible(false);
                    palette_toolbar_preview_lbl.setVisible(false);
                }
                else {
                    palette_toolbar_preview.setVisible(true);
                    palette_toolbar_preview_lbl.setVisible(true);
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

                createThreads();

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

    private void set3DOption() {

        if(!d3_opt.isSelected()) {
            d3 = false;
            setOptions(false);

            d3_button.setSelected(false);

            d3_details_opt.setEnabled(false);

            ThreadDraw.setArrays(image_size);

            progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            backup_orbit = null;

            whole_image_done = false;

            createThreads();

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
                        d3_opt.setSelected(false);
                        d3_button.setSelected(false);
                        return;
                    }
                    else {
                        if(temp > 2000) {
                            main_panel.repaint();
                            JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be less than 2001.", "Error!", JOptionPane.ERROR_MESSAGE);
                            d3_opt.setSelected(false);
                            d3_button.setSelected(false);
                            return;
                        }
                    }

                    if(temp2 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        d3_opt.setSelected(false);
                        d3_button.setSelected(false);
                        return;
                    }

                    if(temp3 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        d3_opt.setSelected(false);
                        d3_button.setSelected(false);
                        return;
                    }

                    if(temp4 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        d3_opt.setSelected(false);
                        d3_button.setSelected(false);
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

                    boundary_tracing_opt.setEnabled(false);

                    d3_details_opt.setEnabled(true);

                    d3 = true;

                    d3_button.setSelected(true);

                    progress.setValue(0);

                    scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                    scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                    resetImage();

                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());

                    backup_orbit = null;

                    whole_image_done = false;

                    createThreads();

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);
                }
                catch(Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    d3_opt.setSelected(false);
                    d3_button.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }
            else {
                d3_opt.setSelected(false);
                d3_button.setSelected(false);
                main_panel.repaint();
                return;
            }
        }

    }

    private void set3DDetails() {

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

                createThreads();

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

    private void setBoundariesNumber() {

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

    private void setGridTiles() {
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

    private void setColorIntensity() {

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
                    createThreads();
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
                    createThreads();
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
        exterior_de_opt.setEnabled(false);
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
        exterior_de_opt.setEnabled(false);
        julia_opt.setEnabled(false);
        julia_button.setEnabled(false);
        julia_map_opt.setEnabled(false);
        julia_map_button.setEnabled(false);
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
        periodicity_checking_opt.setEnabled(false);
        bailout_number.setEnabled(false);
        bailout_test_menu.setEnabled(false);
        perturbation_opt.setEnabled(false);
        init_val_opt.setEnabled(false);
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

        this.mode.setText(mode);

    }

    private void setColorCyclingOptions() {
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

    private void setApplyPlaneOnJulia() {

        if(!apply_plane_on_julia_opt.isSelected()) {
            apply_plane_on_julia = false;
        }
        else {
            apply_plane_on_julia = true;
        }

        if(julia || julia_map) {
            defaultFractalSettings();
        }

    }

    private void Overview() {

        //JTextArea textArea = new JTextArea(32, 55); // 60
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(800, 500));
        //textArea.setLineWrap(false);
        //textArea.setWrapStyleWord(false);

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        double temp_xcenter = xCenter - rotation_center[0];
        double temp_ycenter = yCenter - rotation_center[1];

        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

        String keyword_color = "#008080";
        String condition_color = "#800000";

        String tab = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";
        String tab2 = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";

        String overview = "<html><center><b><u><font size='5' face='arial' color='blue'>Active Fractal Options</font></u></b></center><br><br><font  face='arial' size='3'>";

        overview += "<b><font color='red'>Center:</font></b> " + Complex.toString2(temp1, temp2) + "<br><br>";
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
            if(apply_plane_on_julia) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed and the Whole Plane (c).<br><br>";
            }
            else {
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

        overview += "<b><font color='red'>Maximum Iterations:</font></b> " + max_iterations + "<br><br>";

        if(bailout_number.isEnabled()) {

            overview += "<b><font color='red'>Bailout Test:</font></b> Escaping when the criterion defined by the test \"" + bailout_tests[bailout_test_algorithm].getText() + "\" is met.<br>";
            if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z))^" + n_norm + " + abs(im(z))^" + n_norm + ")^(1/" + n_norm + ") >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped";
            }
            else if(bailout_test_algorithm == BAILOUT_TEST_USER) {
                String greater, equal, lower;

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
                else { // <=
                    greater = "Not Escaped";
                    equal = "Escaped";
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
            overview += "<b><font color='red'>Palette:</font></b> " + palette[color_choice].getText() + "<br><br>";
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
                overview += tab + "Strength = " + bumpMappingStrength + "<br><br>";
            }

            if(rainbow_palette) {
                overview += "<b><font color='red'>Rainbow Palette:</font></b><br>";
                overview += tab + "Factor = " + rainbow_palette_factor + "<br><br>";
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

    private void showUsefulLinks() {

        Object[] message = {
            new LinkLabel("Fractal", "https://en.wikipedia.org/wiki/Fractal"),
            new LinkLabel("Mandelbrot Set", "https://en.wikipedia.org/wiki/Mandelbrot_set"),
            new LinkLabel("Multibrot Set", "https://en.wikipedia.org/wiki/Multibrot_set"),
            new LinkLabel("Burning Ship Fractal", "https://en.wikipedia.org/wiki/Burning_Ship_fractal"),
            new LinkLabel("Newton Fractal", "https://en.wikipedia.org/wiki/Newton_fractal"),
            new LinkLabel("Julia Set", "https://en.wikipedia.org/wiki/Julia_set"),
            new LinkLabel("Fractal Forums", "http://www.fractalforums.com"),};

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
    }

    private void updateColorPalettesMenu() {

        for(i = 0; i < palette.length; i++) {

            Color[] c = null;

            if(i == color_choice) { // the current activated palette
                if(i < palette.length - 1) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
                }
                else {
                    c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                }
            }
            else { // the remaining palettes
                if(i < palette.length - 1) {
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

            palette[i].setIcon(new ImageIcon(palette_preview));
        }

        updatePalettePreview(color_cycling_location);

    }

    public void updatePalettePreview(int color_cycling_location) {

        Color[] c = null;
        if(color_choice < palette.length - 1) {
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

        palette_toolbar_preview.setIcon(new ImageIcon(palette_preview));

    }

    private void updateMaxIterColorPreview() {

        BufferedImage max_it_preview = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        max_it_color_preview.setIcon(new ImageIcon(max_it_preview));

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
            writer.println("boundary_tracing " + boundary_tracing);
            writer.println("periodicity_checking " + periodicity_checking);

            writer.println();
            writer.println("[Window]");
            writer.println("image_size " + image_size);
            writer.println("window_maximized " + (getExtendedState() == JFrame.MAXIMIZED_BOTH));
            writer.println("window_size " + (int)getSize().getWidth() + " " + (int)getSize().getHeight());
            writer.println("window_location " + (int)getLocation().getX() + " " + (int)getLocation().getY());
            writer.println("window_toolbar " + toolbar_opt.isSelected());
            writer.println("window_infobar " + infobar_opt.isSelected());
            writer.println("window_statusbar " + statusbar_opt.isSelected());

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
                    else if(token.equals("boundary_tracing") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && boundary_tracing_opt.isSelected()) {
                            boundary_tracing_opt.setSelected(false);
                            boundary_tracing = false;
                        }
                        else if(token.equals("true") && !boundary_tracing_opt.isSelected()) {
                            boundary_tracing_opt.setSelected(true);
                            boundary_tracing = true;
                        }
                    }
                    else if(token.equals("periodicity_checking") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && periodicity_checking_opt.isSelected()) {
                            periodicity_checking_opt.doClick();
                        }
                        else if(token.equals("true") && !periodicity_checking_opt.isSelected()) {
                            periodicity_checking_opt.doClick();
                        }
                    }
                    else if(token.equals("image_size") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(temp >= 209 && temp <= 6500) {
                                image_size = temp;

                                if(image_size > 4000) {
                                    orbit_opt.setEnabled(false);
                                    orbit_button.setEnabled(false);
                                    orbit_button.setSelected(false);
                                    orbit_opt.setSelected(false);
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

                        if(token.equals("false") && toolbar_opt.isSelected()) {
                            toolbar_opt.doClick();
                        }
                        else if(token.equals("true") && !toolbar_opt.isSelected()) {
                            toolbar_opt.doClick();
                        }
                    }
                    else if(token.equals("window_infobar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && infobar_opt.isSelected()) {
                            infobar_opt.doClick();
                        }
                        else if(token.equals("true") && !infobar_opt.isSelected()) {
                            infobar_opt.doClick();
                        }
                    }
                    else if(token.equals("window_statusbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && statusbar_opt.isSelected()) {
                            statusbar_opt.doClick();
                        }
                        else if(token.equals("true") && !statusbar_opt.isSelected()) {
                            statusbar_opt.doClick();
                        }
                    }
                    else if(token.equals("orbit_style") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("0")) {
                            line.doClick();
                        }
                        else if(token.equals("1")) {
                            dot.doClick();
                        }
                    }
                    else if(token.equals("orbit_show_root") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false") && show_orbit_converging_point_opt.isSelected()) {
                            show_orbit_converging_point_opt.doClick();
                        }
                        else if(token.equals("true") && !show_orbit_converging_point_opt.isSelected()) {
                            show_orbit_converging_point_opt.doClick();
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
                            zoom_window_dashed_line.doClick();
                        }
                        else if(token.equals("1")) {
                            zoom_window_line.doClick();
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

                        if(token.equals("false") && fast_julia_filters_opt.isSelected()) {
                            fast_julia_filters_opt.doClick();
                        }
                        else if(token.equals("true") && !fast_julia_filters_opt.isSelected()) {
                            fast_julia_filters_opt.doClick();
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

    public void filtersOptionsChanged(int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors) {

        for(k = 0; k < filters_options_vals.length; k++) {
            this.filters_options_vals[k] = filters_options_vals[k];
            this.filters_options_extra_vals[0][k] = filters_options_extra_vals[0][k];
            this.filters_options_extra_vals[1][k] = filters_options_extra_vals[1][k];
            this.filters_colors[k] = filters_colors[k];
            this.filters_extra_colors[0][k] = filters_extra_colors[0][k];
            this.filters_extra_colors[1][k] = filters_extra_colors[1][k];
        }

        if(filters[ANTIALIASING] || filters[EDGE_DETECTION] || filters[SHARPNESS] || filters[EMBOSS] || filters[COLOR_CHANNEL_MASKING] || filters[COLOR_CHANNEL_SWAPPING] || filters[CONTRAST_BRIGHTNESS] || filters[GRAYSCALE] || filters[COLOR_TEMPERATURE] || filters[COLOR_CHANNEL_SWIZZLING] || filters[HISTOGRAM_EQUALIZATION] || filters[INVERT_COLORS] || filters[POSTERIZE] || filters[COLOR_CHANNEL_ADJUSTING] || filters[COLOR_CHANNEL_HSB_ADJUSTING] || filters[DITHER] || filters[GAIN] || filters[GAMMA] || filters[EXPOSURE] || filters[BLURRING] || filters[CRYSTALLIZE] || filters[GLOW] || filters[COLOR_CHANNEL_SCALING] || filters[POINTILLIZE] || filters[MARBLE] || filters[WEAVE] || filters[SPARKLE] || filters[OIL] || filters[NOISE] || filters[COLOR_CHANNEL_MIXING] || filters[LIGHT_EFFECTS]) {
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
                    createThreads();
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
            julia_button.setSelected(false);
            julia_opt.setSelected(false);
            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(init_val) {
            init_val = false;
            init_val_opt.setSelected(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(perturbation) {
            perturbation = false;
            perturbation_opt.setSelected(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if(julia_map) {
            julia_map = false;
            julia_map_opt.setSelected(false);
            julia_map_button.setSelected(false);
            julia_map_options_opt.setEnabled(false);
            setOptions(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }

        }

        if(!user_formula_c) {
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            julia_map_button.setEnabled(false);
            perturbation_opt.setEnabled(false);
            init_val_opt.setEnabled(false);
        }

        if(bail_technique == 1) {
            bailout_number.setEnabled(false);
            bailout_test_menu.setEnabled(false);
            periodicity_checking_opt.setEnabled(false);
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

        JLabel multi_arg = new JLabel("Multiple Argument User Functions: f(z1, z2, ... z10)");
        multi_arg.setFont(new Font("Arial", Font.BOLD, 11));

        JButton info_user = new JButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("/fractalzoomer/icons/help2.png"));
        info_user.setPreferredSize(new Dimension(75, 23));

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
        code_editor.setPreferredSize(new Dimension(130, 23));

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
        compile_code.setPreferredSize(new Dimension(150, 23));

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
            "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch,",
            "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch",
            other,
            "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma,",
            "fact, erf, rzeta, gi, rec, flip, round, ceil, floor, trunc, f1, f2, ... f20",
            two_arg,
            "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp,",
            "g1, g2, ... g20",
            multi_arg,
            "m1, m2, ... m20"
        };

        return labels;

    }

    private JLabel createUserFormulaHemlLabels(String supported_vars, boolean mode) {

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
                + "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch<br>"
                + "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch<br>"
                + "<b>Other Functions: f(z)</b><br>"
                + "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma,<br>"
                + "fact, erf, rzeta, gi, rec, flip, round, ceil, floor, trunc, f1, f2, ... f20<br>"
                + "<b>Two Argument Functions: f(z, w)</b><br>"
                + "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp<br>"
                + "g1, g2, ... g20<br>"
                + "<b>Multiple Argument User Functions: f(z1, z2, ... z10)</b><br>"
                + "m1, m2, ... m20<br><br>"
                + "Set the variable " + txt + "."
                + "</html>");

    }

    private void compileCode(boolean show_success) {

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

    private void codeEditor() {
        try {
            File UserDefinedFunctionsFile = new File("UserDefinedFunctions.java");

            if(UserDefinedFunctionsFile.exists()) {
                Desktop.getDesktop().edit(UserDefinedFunctionsFile);
            }

        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Could not open UserDefinedFunctions.java for editing.\nMake sure that the file exists.\nIf not please restart the application.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void copyLib() {
        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");

        if(!path.isEmpty() && !file_separator.isEmpty()) {
            path += file_separator + "lib" + file_separator;

            if(!path.contains(file_separator + "jdk")) {
                File lib_file = new File(path + "tools.jar");

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
                }
            }

        }
        else {
            JOptionPane.showMessageDialog(scroll_pane, "Unable to copy tools.jar to the JRE lib folder.\nThe JRE installation path was not found.", "Warning!", JOptionPane.WARNING_MESSAGE);
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
        
        mw.copyLib();
        mw.checkForUpdate(false);
        mw.exportCodeFiles();

    }
}
