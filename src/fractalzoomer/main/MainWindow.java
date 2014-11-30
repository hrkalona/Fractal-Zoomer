package fractalzoomer.main;

/* Made by Kalonakis Chris, hrkalona@gmail.com */
/* Its obviously not a real time fractal zoomer, nor using high presicion (java are you kidding me?), but its a complete application */
/* Thanks to Josef Jelinek for the Supersampling code, and some of the palettes used. */
/* Thanks to Joel Yliluoma for the boundary tracing algorithm (old version). */
/* Thanks to Evgeny Demidov for the boundary tracing algorithm (currently used). */
/* Thanks to David J. Eck for some of the palettes and the orbit concept */
/* David E. Joyce (is he David J. Eck?) for the escape algorithms */
/* Thanks to Evgeny Demidov for the 3D heightmap algorithm. */
/* Thanks to Cogpar for parsing the user's formula */
/* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
/* Sorry for the absence of comments, this project was never supposed to reach this level! */
/* Also forgive me for the huge-packed main class, read above! */
import fractalzoomer.utils.ColorGenerator;
import fractalzoomer.core.DrawOrbit;
import fractalzoomer.utils.MainPanel;
import fractalzoomer.utils.RequestFocusListener;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.settings.SettingsPalette;
import fractalzoomer.settings.SettingsJulia;
import fractalzoomer.settings.SettingsFractals;
import fractalzoomer.palettes.Palette;
import fractalzoomer.parser.EvaluationException;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.settings.SettingsFractals1049;
import fractalzoomer.settings.SettingsFractals1050;
import fractalzoomer.settings.SettingsFractals1053;
import fractalzoomer.settings.SettingsJulia1049;
import fractalzoomer.settings.SettingsJulia1050;
import fractalzoomer.settings.SettingsJulia1053;
import fractalzoomer.utils.ColorSpaceConverter;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
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
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author hrkalona
 */
public class MainWindow extends JFrame {

    private boolean first_paint;
    private boolean[] filters;
    private int[] filters_options_vals;
    private boolean orbit;
    private boolean julia;
    private boolean smoothing;
    private boolean exterior_de;
    private double exterior_de_factor;
    private int plane_type;
    private boolean orbit_style;
    private boolean zoom_window_style;
    private boolean burning_ship;
    private boolean mandel_grass;
    private boolean whole_image_done;
    private boolean fast_julia_filters;
    private boolean periodicity_checking;
    private boolean color_cycling;
    private boolean grid;
    private boolean old_grid;
    private boolean boundaries;
    private boolean old_boundaries;
    private boolean zoom_window;
    private boolean julia_map;
    private boolean equal_hues;
    private boolean d3;
    private boolean old_d3;
    private boolean perturbation;
    private boolean init_val;
    private boolean boundary_tracing;
    private boolean reversed_palette;
    private double[] coefficients;
    private ThreadDraw[][] threads;
    private DrawOrbit pixels_orbit;
    private double[] perturbation_vals;
    private double[] initial_vals;
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
    private int mx0;
    private int my0;
    private double d3_height_scale;
    private double d3_height_offset;
    private int d3_draw_method;
    private int boundaries_spacing_method;
    private int boundaries_type;
    private int detail;
    private int function;
    private int nova_method;
    private int color_interpolation;
    private int color_space;
    private int random_palette_algorithm;
    private boolean first_seed;
    private double size;
    private double height_ratio;
    private int max_iterations;
    private int n;
    private int julia_grid_first_dimension;
    private int color_choice;
    private int color_cycling_location;
    private int bailout_test_algorithm;
    private double n_norm;
    private double bailout;
    private double plane_transform_angle;
    private double plane_transform_angle2;
    private double plane_transform_radius;
    private double plane_transform_amount;
    private int plane_transform_sides;
    private double z_exponent;
    private double[] z_exponent_complex;
    private double[] z_exponent_nova;
    private double[] relaxation;
    private double zoom_factor;
    private int[] combo_box_choices_filters;
    private int out_coloring_algorithm;
    private int in_coloring_algorithm;
    public static int image_size;
    private long calculation_time;
    private String poly;
    private String[] coloring_option;
    private Color fractal_color;
    private Color orbit_color;
    private Color grid_color;
    private Color boundaries_color;
    private Color zoom_window_color;
    private Parser parser;
    private String user_formula;
    private String user_formula2;
    private String[] user_formula_iteration_based;
    private String[] user_formula_conditions;
    private String[] user_formula_condition_formula;
    private boolean user_formula_c;
    private String user_plane;
    private int bail_technique;
    private BufferedImage image;
    private BufferedImage fast_julia_image;
    private BufferedImage backup_orbit;
    private BufferedImage last_used;
    private BufferedImage colors;
    private BufferedImage colors2;
    private JFileChooser file_chooser;
    private MainWindow ptr;
    private MainPanel main_panel;
    private JLabel mode;
    private JScrollPane scroll_pane;
    private JComboBox[] combo_boxes_filters;
    private JComboBox combo_box_color_space;
    private JComboBox combo_box_color_interp;
    private JComboBox combo_box_random_palette_alg;
    private JCheckBox check_box_reveres_palette;
    private JCheckBox same_hues;
    private JToolBar toolbar;
    private JToolBar statusbar;
    private JMenuBar menubar;
    private JMenu file_menu;
    private JMenu options_menu;
    private JMenu colors_menu;
    private JMenu palette_menu;
    private JMenu roll_palette_menu;
    private JMenu iterations_menu;
    private JMenu bailout_test_menu;
    private JMenu rotation_menu;
    private JMenu filters_menu;
    private JMenu planes_menu;
    private JMenu planes_general_menu;
    private JMenu planes_fold_menu;
    private JMenu planes_distort_menu;
    private JMenu planes_newton_menu;
    private JMenu planes_math_menu;
    private JMenu planes_math_trigonometric_menu;
    private JMenu planes_math_inverse_trigonometric_menu;
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
    private JMenu out_coloring_mode_menu;
    private JMenu in_coloring_mode_menu;
    private JMenu help_menu;
    private JMenu fractal_functions_menu;
    private JMenu mandelbrot_type_functions;
    private JMenu magnet_type_functions;
    private JMenu root_finding_functions;
    private JMenu newton_type_functions;
    private JMenu halley_type_functions;
    private JMenu schroder_type_functions;
    private JMenu householder_type_functions;
    private JMenu secant_type_functions;
    private JMenu steffensen_type_functions;
    private JMenu barnsley_type_functions;
    private JMenu szegedi_butterfly_type_functions;
    private JMenu math_type_functions;
    private JMenu formulas_type_functions;
    private JMenu kaliset_type_functions;
    private JMenu general_type_functions;
    private JMenu general_math_type_functions;
    private JMenu general_newton_variant_functions;
    private JMenu m_like_generalizations_type_functions;
    private JMenu c_azb_dze_type_functions;
    private JMenu c_azb_dze_f_g_type_functions;
    private JMenu zab_zde_fg_type_functions;
    private JMenu user_formulas_type_functions;
    private JMenuItem[] editor_palettes;
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
    private JMenuItem exit;
    private JMenuItem save_image;
    private JMenuItem about;
    private JMenuItem go_to;
    private JMenuItem save_settings;
    private JMenuItem load_settings;
    private JMenuItem change_zooming_factor;
    private JMenuItem zoom_in;
    private JMenuItem zoom_out;
    private JMenuItem orbit_color_opt;
    private JMenuItem grid_tiles_opt;
    private JMenuItem grid_color_opt;
    private JMenuItem boundaries_number_opt;
    private JMenuItem boundaries_color_opt;
    private JMenuItem zoom_window_color_opt;
    private JMenuItem d3_details_opt;
    private JCheckBoxMenuItem d3_opt;
    private JCheckBoxMenuItem boundary_tracing_opt;
    private JCheckBoxMenuItem anti_aliasing_opt;
    private JCheckBoxMenuItem edges_opt;
    private JCheckBoxMenuItem emboss_opt;
    private JCheckBoxMenuItem invert_colors_opt;
    private JCheckBoxMenuItem sharpness_opt;
    private JCheckBoxMenuItem blurring_opt;
    private JCheckBoxMenuItem mask_color_channel_opt;
    private JCheckBoxMenuItem fade_out_opt;
    private JCheckBoxMenuItem histogram_eq_opt;
    private JCheckBoxMenuItem color_channel_swapping_opt;
    private JCheckBoxMenuItem contrast_brightness_opt;
    private JCheckBoxMenuItem grayscale_opt;
    private JCheckBoxMenuItem color_temperature_opt;
    private JCheckBoxMenuItem color_channel_mixing_opt;
    private JCheckBoxMenuItem orbit_opt;
    private JCheckBoxMenuItem julia_opt;
    private JCheckBoxMenuItem fast_julia_filters_opt;
    private JCheckBoxMenuItem periodicity_checking_opt;
    private JCheckBoxMenuItem burning_ship_opt;
    private JCheckBoxMenuItem mandel_grass_opt;
    private JCheckBoxMenuItem color_cycling_opt;
    private JCheckBoxMenuItem julia_map_opt;
    private JCheckBoxMenuItem grid_opt;
    private JCheckBoxMenuItem boundaries_opt;
    private JCheckBoxMenuItem zoom_window_opt;
    private JCheckBoxMenuItem perturbation_opt;
    private JCheckBoxMenuItem init_val_opt;
    private JCheckBoxMenuItem toolbar_opt;
    private JCheckBoxMenuItem statusbar_opt;
    private JCheckBoxMenuItem smoothing_opt;
    private JCheckBoxMenuItem exterior_de_opt;
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
    private JTextField offset_textfield;
    private JButton starting_position_button;
    private JButton zoom_in_button;
    private JButton zoom_out_button;
    private JButton save_image_button;
    private JButton custom_palette_button;
    private JButton random_palette_button;
    private JButton iterations_button;
    private JButton rotation_button;
    private JButton orbit_button;
    private JButton julia_button;
    private JButton color_cycling_button;
    private JButton d3_button;
    private JButton help_button;
    private JFrame fract_color_frame;
    private JFrame orbit_color_frame;
    private JFrame grid_color_frame;
    private JFrame zoom_window_color_frame;
    private JFrame boundaries_color_frame;
    private JFrame custom_palette_editor;
    private JFrame choose_color_frame;
    private JFrame filters_options_frame;
    private JColorChooser color_chooser;
    private JProgressBar progress;
    private JTextField real;
    private JTextField imaginary;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JLabel gradient;
    private JLabel graph;
    private int[][] temp_custom_palette;
    private int temp_color_cycling_location;
    private int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    private static final int[][][] editor_default_palettes = {{{12, 0, 10, 20}, {12, 50, 100, 240}, {12, 20, 3, 26}, {12, 230, 60, 20}, {12, 25, 10, 9}, {12, 230, 170, 0}, {12, 20, 40, 10}, {12, 0, 100, 0}, {12, 5, 10, 10}, {12, 210, 70, 30}, {12, 90, 0, 50}, {12, 180, 90, 120}, {12, 0, 20, 40}, {12, 30, 70, 200}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{22, 0, 24, 255}, {21, 202, 0, 255}, {21, 255, 0, 82}, {22, 255, 133, 0}, {21, 151, 255, 0}, {21, 0, 255, 75}, {22, 0, 209, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{15, 0, 0, 191}, {8, 123, 0, 255}, {6, 165, 0, 139}, {5, 214, 109, 0}, {5, 255, 200, 0}, {10, 243, 255, 0}, {7, 201, 0, 0}, {8, 172, 0, 112}, {5, 204, 0, 239}, {12, 208, 11, 255}, {5, 255, 138, 193}, {8, 200, 170, 178}, {5, 55, 255, 217}, {5, 0, 206, 231}, {6, 0, 124, 255}, {9, 0, 26, 173}, {12, 0, 116, 51}, {5, 0, 235, 226}, {7, 30, 255, 255}, {8, 134, 191, 255}, {5, 254, 186, 255}, {5, 242, 185, 255}, {17, 235, 184, 244}, {5, 130, 42, 57}, {8, 99, 0, 116}, {8, 50, 64, 210}, {12, 0, 128, 167}, {4, 64, 223, 102}, {9, 86, 255, 137}, {13, 134, 191, 217}, {6, 204, 99, 140}, {10, 117, 57, 105}},
        {{8, 0, 0, 0}, {8, 0, 127, 254}, {8, 0, 2, 2}, {8, 0, 252, 127}, {8, 0, 4, 0}, {8, 124, 250, 0}, {8, 7, 7, 0}, {8, 247, 124, 0}, {8, 9, 0, 0}, {8, 245, 0, 122}, {8, 11, 0, 11}, {8, 243, 121, 243}, {8, 13, 13, 13}, {8, 121, 121, 241}, {8, 0, 0, 16}, {8, 0, 119, 238}, {8, 0, 18, 18}, {8, 0, 236, 119}, {8, 0, 20, 0}, {8, 117, 234, 0}, {8, 22, 22, 0}, {8, 232, 117, 0}, {8, 25, 0, 0}, {8, 229, 0, 114}, {8, 27, 0, 27}, {8, 227, 113, 227}, {8, 29, 29, 29}, {8, 113, 113, 225}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{8, 116, 0, 7}, {8, 255, 173, 0}, {8, 160, 0, 0}, {8, 0, 0, 0}, {8, 27, 0, 0}, {8, 14, 0, 0}, {8, 0, 9, 0}, {8, 0, 112, 255}, {8, 0, 218, 255}, {8, 0, 0, 0}, {8, 0, 255, 0}, {8, 66, 70, 0}, {8, 255, 255, 32}, {8, 0, 123, 0}, {8, 0, 20, 0}, {8, 0, 0, 0}, {8, 239, 33, 0}, {8, 255, 119, 0}, {8, 0, 0, 0}, {8, 152, 0, 0}, {8, 0, 0, 0}, {8, 12, 136, 255}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 255, 255, 115}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 255, 255, 123}, {8, 0, 0, 0}, {8, 255, 144, 0}},
        {{16, 0, 0, 0}, {16, 66, 24, 11}, {16, 148, 49, 28}, {16, 247, 101, 62}, {16, 240, 202, 112}, {16, 206, 252, 145}, {16, 179, 252, 195}, {16, 131, 189, 253}, {16, 56, 85, 137}, {16, 4, 7, 15}, {16, 27, 8, 8}, {16, 86, 28, 16}, {16, 172, 64, 27}, {16, 250, 123, 37}, {16, 224, 209, 38}, {16, 141, 253, 37}, {16, 38, 134, 18}, {16, 4, 22, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},},
        {{8, 0, 0, 0}, {8, 0, 0, 22}, {8, 0, 0, 255}, {8, 0, 0, 44}, {8, 0, 0, 255}, {8, 0, 255, 255}, {8, 0, 0, 67}, {8, 0, 255, 0}, {8, 0, 0, 89}, {8, 255, 255, 0}, {8, 0, 0, 111}, {8, 255, 0, 0}, {8, 0, 0, 133}, {8, 255, 0, 255}, {8, 0, 0, 155}, {8, 255, 255, 255}, {8, 0, 0, 177}, {8, 0, 0, 0}, {8, 0, 0, 200}, {8, 0, 0, 255}, {8, 0, 0, 222}, {8, 255, 0, 255}, {8, 0, 0, 244}, {8, 255, 0, 0}, {8, 0, 11, 255}, {8, 255, 255, 0}, {8, 0, 33, 255}, {8, 0, 255, 0}, {8, 0, 55, 255}, {8, 0, 255, 255}, {8, 0, 78, 255}, {8, 255, 255, 255}},
        {{8, 117, 73, 77}, {8, 0, 0, 0}, {8, 101, 127, 111}, {8, 1, 1, 1}, {8, 98, 29, 82}, {8, 1, 1, 1}, {8, 90, 56, 99}, {8, 2, 2, 2}, {8, 99, 62, 6}, {8, 2, 2, 2}, {8, 125, 46, 60}, {8, 3, 3, 3}, {8, 83, 116, 40}, {8, 3, 3, 3}, {8, 91, 89, 23}, {8, 4, 4, 4}, {8, 53, 1, 32}, {8, 4, 4, 4}, {8, 18, 18, 18}, {8, 5, 5, 5}, {8, 3, 98, 29}, {8, 6, 6, 6}, {8, 11, 71, 12}, {8, 6, 6, 6}, {8, 21, 109, 100}, {8, 7, 7, 7}, {8, 48, 126, 79}, {8, 7, 7, 7}, {8, 37, 55, 66}, {8, 8, 8, 8}, {8, 10, 38, 88}, {8, 8, 8, 8}},
        {{12, 37, 215, 240}, {9, 214, 16, 108}, {7, 228, 121, 33}, {9, 184, 46, 7}, {8, 1, 28, 18}, {8, 20, 114, 217}, {6, 0, 153, 51}, {11, 0, 51, 51}, {12, 165, 221, 35}, {10, 153, 102, 255}, {10, 166, 195, 162}, {7, 193, 222, 227}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{22, 0, 0, 0}, {22, 98, 90, 88}, {22, 255, 255, 255}, {22, 220, 175, 121}, {22, 145, 90, 33}, {22, 37, 22, 8}, {22, 0, 0, 0}, {22, 4, 50, 71}, {22, 17, 121, 169}, {22, 33, 48, 51}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{8, 40, 70, 10}, {9, 40, 170, 10}, {6, 100, 255, 70}, {8, 255, 255, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{16, 0, 0, 0}, {16, 19, 26, 49}, {16, 128, 141, 136}, {16, 215, 203, 143}, {16, 247, 205, 166}, {16, 191, 153, 172}, {16, 80, 62, 94}, {16, 48, 33, 33}, {16, 103, 40, 39}, {16, 237, 73, 64}, {16, 255, 151, 103}, {16, 254, 207, 87}, {16, 255, 195, 100}, {16, 255, 116, 58}, {16, 151, 36, 17}, {16, 41, 8, 4}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{22, 255, 255, 255}, {21, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{8, 255, 255, 255}, {8, 255, 206, 12}, {8, 165, 73, 14}, {8, 96, 22, 58}, {8, 39, 9, 114}, {8, 0, 25, 178}, {8, 12, 109, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{8, 255, 255, 255}, {8, 27, 118, 255}, {8, 19, 83, 180}, {8, 40, 62, 94}, {8, 94, 40, 83}, {8, 180, 19, 147}, {8, 255, 27, 209}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{22, 255, 56, 0}, {22, 255, 180, 107}, {22, 255, 228, 206}, {22, 245, 243, 255}, {22, 214, 225, 255}, {22, 186, 208, 255}, {22, 155, 188, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{8, 0, 0, 0}, {8, 214, 0, 0}, {8, 255, 45, 0}, {8, 255, 100, 0}, {8, 255, 155, 0}, {8, 255, 210, 0}, {8, 255, 255, 41}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
        {{12, 0, 0, 189}, {12, 0, 0, 255}, {12, 0, 66, 255}, {12, 0, 132, 255}, {12, 0, 189, 255}, {12, 0, 255, 255}, {12, 66, 255, 189}, {12, 132, 255, 132}, {12, 189, 255, 66}, {12, 255, 255, 0}, {12, 255, 189, 0}, {12, 255, 132, 0}, {12, 255, 66, 0}, {12, 255, 0, 0}, {12, 189, 0, 0}, {12, 132, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}};
    private int i, k;
    public static final int FAST_JULIA_IMAGE_SIZE = 252;
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
    public static final int NOVA_NEWTON = 0;
    public static final int NOVA_HALLEY = 1;
    public static final int NOVA_SCHRODER = 2;
    public static final int NOVA_HOUSEHOLDER = 3;
    public static final int NOVA_SECANT = 4;
    public static final int NOVA_STEFFENSEN = 5;
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
    public static final int BAILOUT_TEST_CIRCLE = 0;
    public static final int BAILOUT_TEST_SQUARE = 1;
    public static final int BAILOUT_TEST_RHOMBUS = 2;
    public static final int BAILOUT_TEST_NNORM = 3;
    public static final int BAILOUT_TEST_STRIP = 4;
    public static final int BAILOUT_TEST_HALFPLANE = 5;
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
    public static final int COLOR_CHANNEL_MIXING = 12;
    public static final int HISTOGRAM_EQUALIZATION = 13;
    public static final int COLOR_SPACE_RGB = 0;
    public static final int COLOR_SPACE_HSB = 1;
    public static final int COLOR_SPACE_EXP = 2;
    public static final int COLOR_SPACE_SQUARE = 3;
    public static final int COLOR_SPACE_SQRT = 4;
    public static final int COLOR_SPACE_RYB = 5;
    public static final int COLOR_SPACE_LAB = 6;
    public static final int COLOR_SPACE_XYZ = 7;
    public static final int COLOR_SPACE_LCH = 8;
    public static final int INTERPOLATION_LINEAR = 0;
    public static final int INTERPOLATION_COSINE = 1;
    public static final int INTERPOLATION_ACCELERATION = 2;
    public static final int INTERPOLATION_DECELERATION = 3;
    public static final int INTERPOLATION_EXPONENTIAL = 4;
    public static final int INTERPOLATION_CATMULLROM = 5;
    public static final int INTERPOLATION_CATMULLROM2 = 6;

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

        filters = new boolean[14];

        filters[ANTIALIASING] = false;
        filters[EDGE_DETECTION] = false;
        filters[EMBOSS] = false;
        filters[SHARPNESS] = false;
        filters[INVERT_COLORS] = false;
        filters[BLURRING] = false;
        filters[COLOR_CHANNEL_MASKING] = false;
        filters[FADE_OUT] = false;
        filters[COLOR_CHANNEL_SWAPPING] = false;
        filters[CONTRAST_BRIGHTNESS] = false;
        filters[GRAYSCALE] = false;
        filters[COLOR_TEMPERATURE] = false;
        filters[COLOR_CHANNEL_MIXING] = false;
        filters[HISTOGRAM_EQUALIZATION] = false;


        filters_options_vals = new int[filters.length];

        filters_options_vals[ANTIALIASING] = 4;
        filters_options_vals[EDGE_DETECTION] = 0;
        filters_options_vals[EMBOSS] = 0;
        filters_options_vals[SHARPNESS] = 0;
        filters_options_vals[COLOR_CHANNEL_MASKING] = 0;
        filters_options_vals[COLOR_CHANNEL_SWAPPING] = 0;
        filters_options_vals[CONTRAST_BRIGHTNESS] = 0;
        filters_options_vals[GRAYSCALE] = 0;
        filters_options_vals[COLOR_TEMPERATURE] = 2000;
        filters_options_vals[COLOR_CHANNEL_MIXING] = 1;

        fiX = 0.64;
        fiY = 0.82;
        dfi = 0.01;

        d3_height_scale = 1;
        d3_height_offset = 0;

        d3_draw_method = 0;
        boundaries_spacing_method = 0;
        boundaries_type = 0;

        color_choice = 0;

        boundary_tracing = true;

        color_interpolation = 0;
        color_space = 0;
        random_palette_algorithm = 0;

        z_exponent = 2;

        nova_method = NOVA_NEWTON;

        bailout = 2;
        bailout_test_algorithm = 0;

        rotation_vals = new double[2];
        old_rotation_vals = new double[2];

        rotation = 0;

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

        first_paint = false;

        plane_type = MU_PLANE;
        out_coloring_algorithm = ESCAPE_TIME;
        in_coloring_algorithm = MAXIMUM_ITERATIONS;

        detail = 0;

        user_formula = "z^2 + c";
        user_formula2 = "c";
        user_plane = "z";

        user_formula_iteration_based = new String[4];
        user_formula_iteration_based[0] = "z^2 + c";
        user_formula_iteration_based[1] = "z^3 + c";
        user_formula_iteration_based[2] = "z^4 + c";
        user_formula_iteration_based[3] = "z^5 + c";


        user_formula_conditions = new String[2];
        user_formula_conditions[0] = "re(z)";
        user_formula_conditions[1] = "0";

        user_formula_condition_formula = new String[3];
        user_formula_condition_formula[0] = "(z - 1) * c";
        user_formula_condition_formula[1] = "(z + 1) * c";
        user_formula_condition_formula[2] = "(z - 1) * c";

        d3 = false;
        old_d3 = d3;
        orbit = false;
        orbit_style = true;
        zoom_window_style = true;
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

        equal_hues = false;

        smoothing = false;

        fractal_color = Color.BLACK;
        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;
        boundaries_color = new Color(0, 204, 102);
        zoom_window_color = Color.WHITE;

        parser = new Parser();
        user_formula_c = true;
        bail_technique = 0;

        function = 0;

        coefficients = new double[11];

        combo_box_choices_filters = new int[filters_options_vals.length];

        combo_boxes_filters = new JComboBox[combo_box_choices_filters.length];

        for(int k = 0; k < combo_box_choices_filters.length; k++) {
            combo_box_choices_filters[k] = 0;
        }

        Locale.setDefault(Locale.US);

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
                    System.exit(0);
                }
                else {
                    if(ans == JOptionPane.NO_OPTION) {
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

        save_settings = new JMenuItem("Save As...", getIcon("/fractalzoomer/icons/save.png"));

        load_settings = new JMenuItem("Load", getIcon("/fractalzoomer/icons/load.png"));

        save_image = new JMenuItem("Save Image As...", getIcon("/fractalzoomer/icons/save_image.png"));

        exit = new JMenuItem("Exit", getIcon("/fractalzoomer/icons/exit.png"));



        options_menu = new JMenu("Options");

        fractal_functions_menu = new JMenu("Fractal Functions");
        fractal_functions_menu.setIcon(getIcon("/fractalzoomer/icons/functions.png"));

        mandelbrot_type_functions = new JMenu("Mandelbrot Type");
        magnet_type_functions = new JMenu("Magnet Type");

        root_finding_functions = new JMenu("Root Finding Methods");
        newton_type_functions = new JMenu("Newton Method");
        halley_type_functions = new JMenu("Halley Method");
        schroder_type_functions = new JMenu("Schroder Method");
        householder_type_functions = new JMenu("Householder Method");
        secant_type_functions = new JMenu("Secant Method");
        steffensen_type_functions = new JMenu("Steffensen Method");

        barnsley_type_functions = new JMenu("Barnsley Type");

        szegedi_butterfly_type_functions = new JMenu("Szegedi Butterfly Type");

        math_type_functions = new JMenu("Math Library Type");

        formulas_type_functions = new JMenu("Formulas");

        kaliset_type_functions = new JMenu("Kaliset Type");
        m_like_generalizations_type_functions = new JMenu("M-Like Type");
        general_type_functions = new JMenu("General Type");
        general_math_type_functions = new JMenu("Math Library Type");
        general_newton_variant_functions = new JMenu("z = z - (z^n + c)/(nz^(n - 2))");
        c_azb_dze_type_functions = new JMenu("z = c(az^b + dz^e)");
        c_azb_dze_f_g_type_functions = new JMenu("z = (c(az^b + dz^e) + f)^g");

        zab_zde_fg_type_functions = new JMenu("z = (z^a + b)/(z^d + e) + f + g");


        user_formulas_type_functions = new JMenu("User Formulas");

        planes_menu = new JMenu("Planes");
        planes_menu.setIcon(getIcon("/fractalzoomer/icons/planes.png"));

        planes_general_menu = new JMenu("General Planes");
        planes_fold_menu = new JMenu("Fold Planes");
        planes_distort_menu = new JMenu("Distort Planes");
        planes_newton_menu = new JMenu("Newton Planes");
        planes_math_menu = new JMenu("Math Planes");
        planes_math_trigonometric_menu = new JMenu("Trigonometric Planes");
        planes_math_inverse_trigonometric_menu = new JMenu("Inverse Trigonometric Planes");

        burning_ship_opt = new JCheckBoxMenuItem("Burning Ship");
        mandel_grass_opt = new JCheckBoxMenuItem("Mandel Grass");


        size_of_image = new JMenuItem("Image Size", getIcon("/fractalzoomer/icons/image_size.png"));

        perturbation_opt = new JCheckBoxMenuItem("Perturbation...");
        init_val_opt = new JCheckBoxMenuItem("Initial Value...");

        iterations_menu = new JMenu("Iterations");
        iterations_menu.setIcon(getIcon("/fractalzoomer/icons/iterations.png"));

        increase_iterations = new JMenuItem("Increase Iterations", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_iterations = new JMenuItem("Decrease Iterations", getIcon("/fractalzoomer/icons/minus.png"));

        iterations = new JMenuItem("Set Iterations", getIcon("/fractalzoomer/icons/iterations.png"));

        bailout_test_menu = new JMenu("Bailout Test");
        bailout_test_menu.setIcon(getIcon("/fractalzoomer/icons/bailout_tests.png"));

        bailout_number = new JMenuItem("Bailout", getIcon("/fractalzoomer/icons/bailout.png"));

        rotation_menu = new JMenu("Rotation");
        rotation_menu.setIcon(getIcon("/fractalzoomer/icons/rotate.png"));

        set_rotation = new JMenuItem("Set Rotation", getIcon("/fractalzoomer/icons/rotate.png"));

        increase_rotation = new JMenuItem("Increase Rotation", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_rotation = new JMenuItem("Decrease Rotation", getIcon("/fractalzoomer/icons/minus.png"));

        change_zooming_factor = new JMenuItem("Zooming Factor", getIcon("/fractalzoomer/icons/zooming_factor.png"));

        height_ratio_number = new JMenuItem("Height Ratio", getIcon("/fractalzoomer/icons/height_ratio.png"));

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

        orbit_menu = new JMenu("Orbit");
        orbit_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_options.png"));

        grid_menu = new JMenu("Grid");
        grid_menu.setIcon(getIcon("/fractalzoomer/icons/grid_options.png"));

        boundaries_menu = new JMenu("Boundaries");
        boundaries_menu.setIcon(getIcon("/fractalzoomer/icons/boundaries_options.png"));

        orbit_color_opt = new JMenuItem("Orbit Color", getIcon("/fractalzoomer/icons/color.png"));

        orbit_style_menu = new JMenu("Orbit Style");
        orbit_style_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_style.png"));

        grid_color_opt = new JMenuItem("Grid Color", getIcon("/fractalzoomer/icons/color.png"));

        grid_tiles_opt = new JMenuItem("Grid Tiles", getIcon("/fractalzoomer/icons/grid.png"));

        zoom_window_color_opt = new JMenuItem("Zoom Window Color", getIcon("/fractalzoomer/icons/color.png"));

        zoom_window_menu = new JMenu("Zoom Window");
        zoom_window_menu.setIcon(getIcon("/fractalzoomer/icons/zoom_window_options.png"));

        zoom_window_style_menu = new JMenu("Zoom Window Style");
        zoom_window_style_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_style.png"));

        boundaries_color_opt = new JMenuItem("Boundaries Color", getIcon("/fractalzoomer/icons/color.png"));

        boundaries_number_opt = new JMenuItem("Boundaries Options", getIcon("/fractalzoomer/icons/boundaries.png"));


        fast_julia_filters_opt = new JCheckBoxMenuItem("Julia Preview Image Filters");

        d3_details_opt = new JMenuItem("3D Options", getIcon("/fractalzoomer/icons/3d_options.png"));

        anti_aliasing_opt = new JCheckBoxMenuItem("Anti-Aliasing");
        edges_opt = new JCheckBoxMenuItem("Edge Detection");
        sharpness_opt = new JCheckBoxMenuItem("Sharpness");
        blurring_opt = new JCheckBoxMenuItem("Blurring");
        emboss_opt = new JCheckBoxMenuItem("Emboss");
        histogram_eq_opt = new JCheckBoxMenuItem("Histogram Equalization");
        contrast_brightness_opt = new JCheckBoxMenuItem("Contrast/Brightness");
        color_temperature_opt = new JCheckBoxMenuItem("Color Temperature");
        fade_out_opt = new JCheckBoxMenuItem("Fade Out");
        invert_colors_opt = new JCheckBoxMenuItem("Inverted Colors");
        mask_color_channel_opt = new JCheckBoxMenuItem("Mask Color Channel");
        color_channel_swapping_opt = new JCheckBoxMenuItem("Color Channel Swapping");
        color_channel_mixing_opt = new JCheckBoxMenuItem("Color Channel Mixing");
        grayscale_opt = new JCheckBoxMenuItem("Grayscale");



        colors_menu = new JMenu("Colors");
        colors_menu.setIcon(getIcon("/fractalzoomer/icons/colors_menu.png"));

        fract_color = new JMenuItem("Fractal Color", getIcon("/fractalzoomer/icons/color.png"));

        palette_menu = new JMenu("Palette");
        palette_menu.setIcon(getIcon("/fractalzoomer/icons/palette.png"));

        random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette2.png"));

        out_coloring_mode_menu = new JMenu("Out Coloring Mode");
        out_coloring_mode_menu.setIcon(getIcon("/fractalzoomer/icons/out_coloring_mode.png"));

        in_coloring_mode_menu = new JMenu("In Coloring Mode");
        in_coloring_mode_menu.setIcon(getIcon("/fractalzoomer/icons/out_coloring_mode.png"));

        smoothing_opt = new JCheckBoxMenuItem("Smoothing");
        exterior_de_opt = new JCheckBoxMenuItem("Distance Estimation");

        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(getIcon("/fractalzoomer/icons/shift_palette.png"));

        roll_palette = new JMenuItem("Shift Palette", getIcon("/fractalzoomer/icons/shift_palette.png"));

        increase_roll_palette = new JMenuItem("Shift Palette Forward", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_roll_palette = new JMenuItem("Shift Palette Backward", getIcon("/fractalzoomer/icons/minus.png"));

        tools_menu = new JMenu("Tools");
        orbit_opt = new JCheckBoxMenuItem("Orbit");
        julia_opt = new JCheckBoxMenuItem("Julia");
        color_cycling_opt = new JCheckBoxMenuItem("Color Cycling");
        d3_opt = new JCheckBoxMenuItem("3D");
        julia_map_opt = new JCheckBoxMenuItem("Julia Map");
        grid_opt = new JCheckBoxMenuItem("Show Grid");
        zoom_window_opt = new JCheckBoxMenuItem("Show Zoom Window");
        boundaries_opt = new JCheckBoxMenuItem("Show Boundaries");


        filters_menu = new JMenu("Filters");

        help_menu = new JMenu("Help");


        help_contents = new JMenuItem("Help Contents", getIcon("/fractalzoomer/icons/help.png"));

        about = new JMenuItem("About", getIcon("/fractalzoomer/icons/about.png"));


        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        save_settings.setToolTipText("<html>Saves the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, and bailout of the fractal.</html>");
        load_settings.setToolTipText("<html>Loads the function, plane, center, size, color options, iterations,<br> rotation, perturbation, initial value, and bailout of the fractal.</html>");
        save_image.setToolTipText("Saves a bmp, jpg, or png image.");
        exit.setToolTipText("Exits the application.");

        burning_ship_opt.setToolTipText("Enables the burning ship variation.");
        mandel_grass_opt.setToolTipText("Enables the mandel grass variation.");

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
        height_ratio_number.setToolTipText("Changes the size ratio of the image's height.");
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing alot of bounded areas.");
        boundary_tracing_opt.setToolTipText("Calculates only the boundaries of the image.");
        filters_options.setToolTipText("Sets the options of the image filters.");
        smoothing_opt.setToolTipText("Smooths the image's color transitions.");
        exterior_de_opt.setToolTipText("<html>Sets some points near the boundary of<br>the set to the maximum iterations value.</html>");

        grid_color_opt.setToolTipText("Sets the color of the grid.");
        grid_tiles_opt.setToolTipText("Sets the number of the grid tiles.");
        boundaries_color_opt.setToolTipText("Sets the color of the boundaries.");
        boundaries_number_opt.setToolTipText("Sets the boundaries options.");
        toolbar_opt.setToolTipText("Activates the tool bar.");
        statusbar_opt.setToolTipText("Activates the status bar.");
        orbit_color_opt.setToolTipText("Sets the color of the orbit.");
        zoom_window_color_opt.setToolTipText("Sets the color of zooming window.");

        fract_color.setToolTipText("Sets a color corresponding to the maximum iterations.");
        random_palette.setToolTipText("Randomizes the palette.");
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");


        fast_julia_filters_opt.setToolTipText("Activates the filters for the julia preview.");
        d3_details_opt.setToolTipText("Changes the 3D options.");

        orbit_opt.setToolTipText("Displays the orbit of a complex number.");
        julia_opt.setToolTipText("Generates an image based on a seed (chosen pixel).");
        julia_map_opt.setToolTipText("Creates an image of julia sets.");
        d3_opt.setToolTipText("Creates a 3D version of the image.");
        color_cycling_opt.setToolTipText("Animates the image, cycling through the palette.");
        grid_opt.setToolTipText("Draws a cordinated grid.");
        zoom_window_opt.setToolTipText("Displays the zooming window.");
        boundaries_opt.setToolTipText("Draws the plane boundaries.");

        anti_aliasing_opt.setToolTipText("Smooths the image.");
        edges_opt.setToolTipText("Detects the edges of the image (Thick Edges).");
        sharpness_opt.setToolTipText("Makes the edges of the image more sharp.");
        emboss_opt.setToolTipText("Raises the light colored areas and carves the dark ones, using gray scale.");
        invert_colors_opt.setToolTipText("Inverts the colors of the image.");
        blurring_opt.setToolTipText("Blurs the image.");
        histogram_eq_opt.setToolTipText("Performs histogram equalization on the image.");
        mask_color_channel_opt.setToolTipText("Removes a color channel of the image.");
        fade_out_opt.setToolTipText("Applies a fade out effect to the image.");
        color_channel_swapping_opt.setToolTipText("Swaps the color channels of the image.");
        contrast_brightness_opt.setToolTipText("Changes the contrast/brightness of the image.");
        grayscale_opt.setToolTipText("Converts the image to grayscale.");
        color_temperature_opt.setToolTipText("Changes the color temperature of the image.");
        color_channel_mixing_opt.setToolTipText("Mixes the color channels of the image.");

        help_contents.setToolTipText("Loads the help file.");

        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

        burning_ship_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        mandel_grass_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.SHIFT_MASK));

        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
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
        smoothing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.SHIFT_MASK));
        exterior_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.SHIFT_MASK));

        fast_julia_filters_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        d3_details_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));

        orbit_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        toolbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        statusbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        julia_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
        d3_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
        color_cycling_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        grid_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        zoom_window_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.SHIFT_MASK));
        boundaries_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK));

        anti_aliasing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        edges_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        sharpness_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        emboss_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        invert_colors_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        blurring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        mask_color_channel_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        fade_out_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        color_channel_swapping_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK));
        contrast_brightness_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));
        grayscale_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.SHIFT_MASK));
        color_temperature_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        color_channel_mixing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        histogram_eq_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        help_contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));

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

        exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!color_cycling) {
                    main_panel.repaint();
                }
                int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
                if(ans == JOptionPane.YES_OPTION) {
                    saveSettings();
                    System.exit(0);
                }
                else {
                    if(ans == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                }

            }
        });

        fractal_functions = new JRadioButtonMenuItem[122];

        fractal_functions[0] = new JRadioButtonMenuItem("Mandelbrot z = z^2 + c");
        fractal_functions[0].addActionListener(new ActionListener() {

            int temp = 0;

            public void actionPerformed(ActionEvent e) {

                setFunction(temp);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[0]);

        for(i = 1; i < 9; i++) {
            fractal_functions[i] = new JRadioButtonMenuItem("Multibrot z = z^" + (i + 2) + " + c");
            fractal_functions[i].addActionListener(new ActionListener() {

                int temp = i;

                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
            });
            mandelbrot_type_functions.add(fractal_functions[i]);
        }

        fractal_functions[MANDELBROTNTH] = new JRadioButtonMenuItem("Multibrot z = z^n + c");
        fractal_functions[MANDELBROTNTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MANDELBROTNTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MANDELBROTNTH]);


        fractal_functions[MANDELBROTWTH] = new JRadioButtonMenuItem("Multibrot z = z^w + c");
        fractal_functions[MANDELBROTWTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MANDELBROTWTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MANDELBROTWTH]);

        fractal_functions[MANDELPOLY] = new JRadioButtonMenuItem("Multibrot Polynomial");
        fractal_functions[MANDELPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MANDELPOLY);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MANDELPOLY]);

        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(burning_ship_opt);
        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(mandel_grass_opt);
        mandelbrot_type_functions.addSeparator();

        fractal_functions_menu.add(mandelbrot_type_functions);

        fractal_functions_menu.addSeparator();

        fractal_functions[FORMULA1] = new JRadioButtonMenuItem("z = 0.25z^2 + c + (z^2 + c)^-2");
        fractal_functions[FORMULA1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA1);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[FORMULA1]);
        m_like_generalizations_type_functions.addSeparator();


        fractal_functions[FORMULA28] = new JRadioButtonMenuItem("z = cz^2 + (cz^2)^-1");
        fractal_functions[FORMULA28].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA28);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[FORMULA28]);
        m_like_generalizations_type_functions.addSeparator();


        fractal_functions[FORMULA29] = new JRadioButtonMenuItem("z = cz^2 + 1 + (cz^2 + 1)^-1");
        fractal_functions[FORMULA29].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA29);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[FORMULA29]);
        m_like_generalizations_type_functions.addSeparator();


        fractal_functions[FORMULA38] = new JRadioButtonMenuItem("z = z^2 + c/z^2");
        fractal_functions[FORMULA38].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA38);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[FORMULA38]);
        m_like_generalizations_type_functions.addSeparator();


        fractal_functions[FORMULA39] = new JRadioButtonMenuItem("z = (z^2)exp(1/z) + c");
        fractal_functions[FORMULA39].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA39);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[FORMULA39]);
        m_like_generalizations_type_functions.addSeparator();



        fractal_functions[FORMULA2] = new JRadioButtonMenuItem("z = c(z + z^-1)");
        fractal_functions[FORMULA2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA2);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA2]);


        fractal_functions[FORMULA3] = new JRadioButtonMenuItem("z = c(z^3 + z^-3)");
        fractal_functions[FORMULA3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA3);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA3]);


        fractal_functions[FORMULA4] = new JRadioButtonMenuItem("z = c(5z^-1 + z^5)");
        fractal_functions[FORMULA4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA4);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA4]);


        fractal_functions[FORMULA5] = new JRadioButtonMenuItem("z = c(z^5 - 5z)");
        fractal_functions[FORMULA5].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA5);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA5]);


        fractal_functions[FORMULA6] = new JRadioButtonMenuItem("z = c(2z^2 - z^4)");
        fractal_functions[FORMULA6].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA6);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA6]);


        fractal_functions[FORMULA7] = new JRadioButtonMenuItem("z = c(2z^-2 - z^-4)");
        fractal_functions[FORMULA7].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA7);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA7]);


        fractal_functions[FORMULA8] = new JRadioButtonMenuItem("z = c(5z^-1 - z^-5)");
        fractal_functions[FORMULA8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA8);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA8]);


        fractal_functions[FORMULA9] = new JRadioButtonMenuItem("z = c(-3z^-3 + z^-9)");
        fractal_functions[FORMULA9].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA9);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA9]);


        fractal_functions[FORMULA10] = new JRadioButtonMenuItem("z = c(z^2.5 + z^-2.5)");
        fractal_functions[FORMULA10].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA10);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA10]);



        fractal_functions[FORMULA11] = new JRadioButtonMenuItem("z = c(z^2i + z^-2)");
        fractal_functions[FORMULA11].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA11);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA11]);


        fractal_functions[FORMULA12] = new JRadioButtonMenuItem("z = c(-3z^-2 + 2z^-3)");
        fractal_functions[FORMULA12].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA12);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA12]);

        fractal_functions[FORMULA27] = new JRadioButtonMenuItem("z = c((z + 2)^6 + (z + 2)^-6)");
        fractal_functions[FORMULA27].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA27);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[FORMULA27]);


        m_like_generalizations_type_functions.add(c_azb_dze_type_functions);

        m_like_generalizations_type_functions.addSeparator();


        fractal_functions[FORMULA13] = new JRadioButtonMenuItem("z = (c(2z - z^2) + 1)^2");
        fractal_functions[FORMULA13].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA13);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA13]);


        fractal_functions[FORMULA14] = new JRadioButtonMenuItem("z = (c(2z - z^2) + 1)^3");
        fractal_functions[FORMULA14].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA14);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA14]);


        fractal_functions[FORMULA15] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^2");
        fractal_functions[FORMULA15].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA15);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA15]);


        fractal_functions[FORMULA16] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^3");
        fractal_functions[FORMULA16].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA16);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA16]);


        fractal_functions[FORMULA17] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^4");
        fractal_functions[FORMULA17].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA17);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA17]);


        fractal_functions[FORMULA18] = new JRadioButtonMenuItem("z = (c(z^3 - z^4) + 1)^5");
        fractal_functions[FORMULA18].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA18);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[FORMULA18]);

        m_like_generalizations_type_functions.add(c_azb_dze_f_g_type_functions);


        fractal_functions[FORMULA40] = new JRadioButtonMenuItem("z = (z^10 + c)/(z^8 + c) + c - 2");
        fractal_functions[FORMULA40].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA40);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[FORMULA40]);


        fractal_functions[FORMULA41] = new JRadioButtonMenuItem("z = (z^16 - 10)/(z^14 - c) + c - 6");
        fractal_functions[FORMULA41].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA41);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[FORMULA41]);


        m_like_generalizations_type_functions.addSeparator();
        m_like_generalizations_type_functions.add(zab_zde_fg_type_functions);


        fractal_functions[FORMULA19] = new JRadioButtonMenuItem("z = abs(z^-1) + c");
        fractal_functions[FORMULA19].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA19);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA19]);


        fractal_functions[FORMULA20] = new JRadioButtonMenuItem("z = abs(z^2) + c");
        fractal_functions[FORMULA20].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA20);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA20]);


        fractal_functions[FORMULA21] = new JRadioButtonMenuItem("z = abs(z)/abs(c) + c");
        fractal_functions[FORMULA21].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA21);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA21]);


        fractal_functions[FORMULA22] = new JRadioButtonMenuItem("z = abs(z/c) + c");
        fractal_functions[FORMULA22].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA22);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA22]);


        fractal_functions[FORMULA23] = new JRadioButtonMenuItem("z = abs(z/(0.5 + 0.5i)) + c");
        fractal_functions[FORMULA23].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA23);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA23]);


        fractal_functions[FORMULA24] = new JRadioButtonMenuItem("z = abs(z)/c + c");
        fractal_functions[FORMULA24].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA24);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA24]);


        fractal_functions[FORMULA25] = new JRadioButtonMenuItem("z = abs(z)^-3 + c");
        fractal_functions[FORMULA25].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA25);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA25]);


        fractal_functions[FORMULA26] = new JRadioButtonMenuItem("z = abs(z^-3) + c");
        fractal_functions[FORMULA26].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA26);

            }
        });
        kaliset_type_functions.add(fractal_functions[FORMULA26]);

        formulas_type_functions.add(m_like_generalizations_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(kaliset_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(general_type_functions);

        general_type_functions.add(general_math_type_functions);
        general_type_functions.addSeparator();
        general_type_functions.add(general_newton_variant_functions);


        fractal_functions_menu.add(formulas_type_functions);

        fractal_functions_menu.addSeparator();

        fractal_functions[LAMBDA] = new JRadioButtonMenuItem("Lambda");
        fractal_functions[LAMBDA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(LAMBDA);

            }
        });
        fractal_functions_menu.add(fractal_functions[LAMBDA]);
        fractal_functions_menu.addSeparator();

        fractal_functions[MAGNET1] = new JRadioButtonMenuItem("Magnet 1");
        fractal_functions[MAGNET1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MAGNET1);

            }
        });
        magnet_type_functions.add(fractal_functions[MAGNET1]);


        fractal_functions[MAGNET2] = new JRadioButtonMenuItem("Magnet 2");
        fractal_functions[MAGNET2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MAGNET2);

            }
        });
        magnet_type_functions.add(fractal_functions[MAGNET2]);
        fractal_functions_menu.add(magnet_type_functions);
        fractal_functions_menu.addSeparator();


        fractal_functions[NEWTON3] = new JRadioButtonMenuItem("Newton 3");
        fractal_functions[NEWTON3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTON3);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTON3]);


        fractal_functions[NEWTON4] = new JRadioButtonMenuItem("Newton 4");
        fractal_functions[NEWTON4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTON4);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTON4]);


        fractal_functions[NEWTONGENERALIZED3] = new JRadioButtonMenuItem("Newton Generalized 3");
        fractal_functions[NEWTONGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTONGENERALIZED3);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTONGENERALIZED3]);


        fractal_functions[NEWTONGENERALIZED8] = new JRadioButtonMenuItem("Newton Generalized 8");
        fractal_functions[NEWTONGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTONGENERALIZED8);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTONGENERALIZED8]);


        fractal_functions[NEWTONSIN] = new JRadioButtonMenuItem("Newton Sin");
        fractal_functions[NEWTONSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTONSIN);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTONSIN]);



        fractal_functions[NEWTONCOS] = new JRadioButtonMenuItem("Newton Cos");
        fractal_functions[NEWTONCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTONCOS);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTONCOS]);


        fractal_functions[NEWTONPOLY] = new JRadioButtonMenuItem("Newton Polynomial");
        fractal_functions[NEWTONPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NEWTONPOLY);

            }
        });
        newton_type_functions.add(fractal_functions[NEWTONPOLY]);

        root_finding_functions.add(newton_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(halley_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(schroder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(householder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(secant_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(steffensen_type_functions);

        fractal_functions_menu.add(root_finding_functions);
        fractal_functions_menu.addSeparator();


        fractal_functions[BARNSLEY1] = new JRadioButtonMenuItem("Barnsley 1");
        fractal_functions[BARNSLEY1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(BARNSLEY1);

            }
        });
        barnsley_type_functions.add(fractal_functions[BARNSLEY1]);


        fractal_functions[BARNSLEY2] = new JRadioButtonMenuItem("Barnsley 2");
        fractal_functions[BARNSLEY2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(BARNSLEY2);

            }
        });
        barnsley_type_functions.add(fractal_functions[BARNSLEY2]);


        fractal_functions[BARNSLEY3] = new JRadioButtonMenuItem("Barnsley 3");
        fractal_functions[BARNSLEY3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(BARNSLEY3);

            }
        });
        barnsley_type_functions.add(fractal_functions[BARNSLEY3]);
        fractal_functions_menu.add(barnsley_type_functions);
        fractal_functions_menu.addSeparator();


        fractal_functions[SZEGEDI_BUTTERFLY1] = new JRadioButtonMenuItem("Szegedi Butterfly 1");
        fractal_functions[SZEGEDI_BUTTERFLY1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SZEGEDI_BUTTERFLY1);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[SZEGEDI_BUTTERFLY1]);


        fractal_functions[SZEGEDI_BUTTERFLY2] = new JRadioButtonMenuItem("Szegedi Butterfly 2");
        fractal_functions[SZEGEDI_BUTTERFLY2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SZEGEDI_BUTTERFLY2);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[SZEGEDI_BUTTERFLY2]);

        fractal_functions_menu.add(szegedi_butterfly_type_functions);
        fractal_functions_menu.addSeparator();


        fractal_functions[NOVA] = new JRadioButtonMenuItem("Nova");
        fractal_functions[NOVA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(NOVA);

            }
        });
        fractal_functions_menu.add(fractal_functions[NOVA]);
        fractal_functions_menu.addSeparator();


        fractal_functions[MANDELBAR] = new JRadioButtonMenuItem("Mandelbar");
        fractal_functions[MANDELBAR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MANDELBAR);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MANDELBAR]);


        fractal_functions[SPIDER] = new JRadioButtonMenuItem("Spider");
        fractal_functions[SPIDER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SPIDER);

            }
        });
        fractal_functions_menu.add(fractal_functions[SPIDER]);

        fractal_functions_menu.addSeparator();

        fractal_functions[MANOWAR] = new JRadioButtonMenuItem("Manowar");
        fractal_functions[MANOWAR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(MANOWAR);

            }
        });
        fractal_functions_menu.add(fractal_functions[MANOWAR]);

        fractal_functions_menu.addSeparator();


        fractal_functions[PHOENIX] = new JRadioButtonMenuItem("Phoenix");
        fractal_functions[PHOENIX].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(PHOENIX);

            }
        });
        fractal_functions_menu.add(fractal_functions[PHOENIX]);

        fractal_functions_menu.addSeparator();


        fractal_functions[SIERPINSKI_GASKET] = new JRadioButtonMenuItem("Sierpinski Gasket");
        fractal_functions[SIERPINSKI_GASKET].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SIERPINSKI_GASKET);

            }
        });
        fractal_functions_menu.add(fractal_functions[SIERPINSKI_GASKET]);
        fractal_functions_menu.addSeparator();


        fractal_functions[FROTHY_BASIN] = new JRadioButtonMenuItem("Frothy Basin");
        fractal_functions[FROTHY_BASIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FROTHY_BASIN);

            }
        });
        fractal_functions_menu.add(fractal_functions[FROTHY_BASIN]);
        fractal_functions_menu.addSeparator();


        fractal_functions[EXP] = new JRadioButtonMenuItem("z = exp(z) + c");
        fractal_functions[EXP].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(EXP);

            }
        });
        math_type_functions.add(fractal_functions[EXP]);


        fractal_functions[LOG] = new JRadioButtonMenuItem("z = log(z) + c");
        fractal_functions[LOG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(LOG);

            }
        });
        math_type_functions.add(fractal_functions[LOG]);


        fractal_functions[SIN] = new JRadioButtonMenuItem("z = sin(z) + c");
        fractal_functions[SIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SIN);

            }
        });
        math_type_functions.add(fractal_functions[SIN]);


        fractal_functions[COS] = new JRadioButtonMenuItem("z = cos(z) + c");
        fractal_functions[COS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(COS);

            }
        });
        math_type_functions.add(fractal_functions[COS]);


        fractal_functions[TAN] = new JRadioButtonMenuItem("z = tan(z) + c");
        fractal_functions[TAN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(TAN);

            }
        });
        math_type_functions.add(fractal_functions[TAN]);


        fractal_functions[COT] = new JRadioButtonMenuItem("z = cot(z) + c");
        fractal_functions[COT].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(COT);

            }
        });
        math_type_functions.add(fractal_functions[COT]);


        fractal_functions[SINH] = new JRadioButtonMenuItem("z = sinh(z) + c");
        fractal_functions[SINH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SINH);

            }
        });
        math_type_functions.add(fractal_functions[SINH]);


        fractal_functions[COSH] = new JRadioButtonMenuItem("z = cosh(z) + c");
        fractal_functions[COSH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(COSH);

            }
        });
        math_type_functions.add(fractal_functions[COSH]);


        fractal_functions[TANH] = new JRadioButtonMenuItem("z = tanh(z) + c");
        fractal_functions[TANH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(TANH);

            }
        });
        math_type_functions.add(fractal_functions[TANH]);


        fractal_functions[COTH] = new JRadioButtonMenuItem("z = coth(z) + c");
        fractal_functions[COTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(COTH);

            }
        });
        math_type_functions.add(fractal_functions[COTH]);


        fractal_functions[FORMULA30] = new JRadioButtonMenuItem("z = sin(z)c");
        fractal_functions[FORMULA30].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA30);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA30]);


        fractal_functions[FORMULA31] = new JRadioButtonMenuItem("z = cos(z)c");
        fractal_functions[FORMULA31].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA31);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA31]);

        fractal_functions[FORMULA32] = new JRadioButtonMenuItem("z = zlog(z + 1) + c");
        fractal_functions[FORMULA32].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA32);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA32]);

        fractal_functions[FORMULA33] = new JRadioButtonMenuItem("z = zsin(z) + c");
        fractal_functions[FORMULA33].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA33);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA33]);

        fractal_functions[FORMULA34] = new JRadioButtonMenuItem("z = zsinh(z) + c");
        fractal_functions[FORMULA34].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA34);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA34]);


        fractal_functions[FORMULA35] = new JRadioButtonMenuItem("z = 2 - 2cos(z) + c");
        fractal_functions[FORMULA35].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA35);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA35]);

        fractal_functions[FORMULA36] = new JRadioButtonMenuItem("z = 2cosh(z) - 2 + c");
        fractal_functions[FORMULA36].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA36);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA36]);

        fractal_functions[FORMULA37] = new JRadioButtonMenuItem("z = zsin(z) - c^2");
        fractal_functions[FORMULA37].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA37);

            }
        });
        general_math_type_functions.add(fractal_functions[FORMULA37]);



        fractal_functions[FORMULA42] = new JRadioButtonMenuItem("z = z - (z^3 + c)/(3z)");
        fractal_functions[FORMULA42].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA42);

            }
        });
        general_newton_variant_functions.add(fractal_functions[FORMULA42]);

        fractal_functions[FORMULA43] = new JRadioButtonMenuItem("z = z - (z^4 + c)/(4z^2)");
        fractal_functions[FORMULA43].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA43);

            }
        });
        general_newton_variant_functions.add(fractal_functions[FORMULA43]);

        fractal_functions[FORMULA44] = new JRadioButtonMenuItem("z = z - (z^5 + c)/(5z^3)");
        fractal_functions[FORMULA44].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA44);

            }
        });
        general_newton_variant_functions.add(fractal_functions[FORMULA44]);

        fractal_functions[FORMULA45] = new JRadioButtonMenuItem("z = z - (z^8 + c)/(8z^6)");
        fractal_functions[FORMULA45].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(FORMULA45);

            }
        });
        general_newton_variant_functions.add(fractal_functions[FORMULA45]);


        fractal_functions_menu.add(math_type_functions);
        fractal_functions_menu.addSeparator();


        fractal_functions[USER_FORMULA] = new JRadioButtonMenuItem("User Formula");
        fractal_functions[USER_FORMULA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(USER_FORMULA);

            }
        });
        user_formulas_type_functions.add(fractal_functions[USER_FORMULA]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[USER_FORMULA_ITERATION_BASED] = new JRadioButtonMenuItem("User Formula Iteration Based");
        fractal_functions[USER_FORMULA_ITERATION_BASED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(USER_FORMULA_ITERATION_BASED);

            }
        });
        user_formulas_type_functions.add(fractal_functions[USER_FORMULA_ITERATION_BASED]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[USER_FORMULA_CONDITIONAL] = new JRadioButtonMenuItem("User Formula Conditional");
        fractal_functions[USER_FORMULA_CONDITIONAL].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(USER_FORMULA_CONDITIONAL);

            }
        });
        user_formulas_type_functions.add(fractal_functions[USER_FORMULA_CONDITIONAL]);

        fractal_functions_menu.add(user_formulas_type_functions);


        fractal_functions[HALLEY3] = new JRadioButtonMenuItem("Halley 3");
        fractal_functions[HALLEY3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEY3);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEY3]);


        fractal_functions[HALLEY4] = new JRadioButtonMenuItem("Halley 4");
        fractal_functions[HALLEY4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEY4);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEY4]);


        fractal_functions[HALLEYGENERALIZED3] = new JRadioButtonMenuItem("Halley Generalized 3");
        fractal_functions[HALLEYGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEYGENERALIZED3);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEYGENERALIZED3]);


        fractal_functions[HALLEYGENERALIZED8] = new JRadioButtonMenuItem("Halley Generalized 8");
        fractal_functions[HALLEYGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEYGENERALIZED8);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEYGENERALIZED8]);


        fractal_functions[HALLEYSIN] = new JRadioButtonMenuItem("Halley Sin");
        fractal_functions[HALLEYSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEYSIN);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEYSIN]);


        fractal_functions[HALLEYCOS] = new JRadioButtonMenuItem("Halley Cos");
        fractal_functions[HALLEYCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEYCOS);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEYCOS]);


        fractal_functions[HALLEYPOLY] = new JRadioButtonMenuItem("Halley Polynomial");
        fractal_functions[HALLEYPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HALLEYPOLY);

            }
        });
        halley_type_functions.add(fractal_functions[HALLEYPOLY]);


        fractal_functions[SCHRODER3] = new JRadioButtonMenuItem("Schroder 3");
        fractal_functions[SCHRODER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODER3);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODER3]);


        fractal_functions[SCHRODER4] = new JRadioButtonMenuItem("Schroder 4");
        fractal_functions[SCHRODER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODER4);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODER4]);


        fractal_functions[SCHRODERGENERALIZED3] = new JRadioButtonMenuItem("Schroder Generalized 3");
        fractal_functions[SCHRODERGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODERGENERALIZED3);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODERGENERALIZED3]);


        fractal_functions[SCHRODERGENERALIZED8] = new JRadioButtonMenuItem("Schroder Generalized 8");
        fractal_functions[SCHRODERGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODERGENERALIZED8);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODERGENERALIZED8]);


        fractal_functions[SCHRODERSIN] = new JRadioButtonMenuItem("Schroder Sin");
        fractal_functions[SCHRODERSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODERSIN);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODERSIN]);


        fractal_functions[SCHRODERCOS] = new JRadioButtonMenuItem("Schroder Cos");
        fractal_functions[SCHRODERCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODERCOS);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODERCOS]);


        fractal_functions[SCHRODERPOLY] = new JRadioButtonMenuItem("Schroder Polynomial");
        fractal_functions[SCHRODERPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SCHRODERPOLY);

            }
        });
        schroder_type_functions.add(fractal_functions[SCHRODERPOLY]);


        fractal_functions[HOUSEHOLDER3] = new JRadioButtonMenuItem("Householder 3");
        fractal_functions[HOUSEHOLDER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDER3);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDER3]);


        fractal_functions[HOUSEHOLDER4] = new JRadioButtonMenuItem("Householder 4");
        fractal_functions[HOUSEHOLDER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDER4);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDER4]);


        fractal_functions[HOUSEHOLDERGENERALIZED3] = new JRadioButtonMenuItem("Householder Generalized 3");
        fractal_functions[HOUSEHOLDERGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDERGENERALIZED3);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERGENERALIZED3]);


        fractal_functions[HOUSEHOLDERGENERALIZED8] = new JRadioButtonMenuItem("Householder Generalized 8");
        fractal_functions[HOUSEHOLDERGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDERGENERALIZED8);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERGENERALIZED8]);


        fractal_functions[HOUSEHOLDERSIN] = new JRadioButtonMenuItem("Householder Sin");
        fractal_functions[HOUSEHOLDERSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDERSIN);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERSIN]);


        fractal_functions[HOUSEHOLDERCOS] = new JRadioButtonMenuItem("Householder Cos");
        fractal_functions[HOUSEHOLDERCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDERCOS);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERCOS]);


        fractal_functions[HOUSEHOLDERPOLY] = new JRadioButtonMenuItem("Householder Polynomial");
        fractal_functions[HOUSEHOLDERPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(HOUSEHOLDERPOLY);

            }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERPOLY]);


        fractal_functions[SECANT3] = new JRadioButtonMenuItem("Secant 3");
        fractal_functions[SECANT3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANT3);

            }
        });
        secant_type_functions.add(fractal_functions[SECANT3]);


        fractal_functions[SECANT4] = new JRadioButtonMenuItem("Secant 4");
        fractal_functions[SECANT4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANT4);

            }
        });
        secant_type_functions.add(fractal_functions[SECANT4]);


        fractal_functions[SECANTGENERALIZED3] = new JRadioButtonMenuItem("Secant Generalized 3");
        fractal_functions[SECANTGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANTGENERALIZED3);

            }
        });
        secant_type_functions.add(fractal_functions[SECANTGENERALIZED3]);


        fractal_functions[SECANTGENERALIZED8] = new JRadioButtonMenuItem("Secant Generalized 8");
        fractal_functions[SECANTGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANTGENERALIZED8);

            }
        });
        secant_type_functions.add(fractal_functions[SECANTGENERALIZED8]);


        fractal_functions[SECANTCOS] = new JRadioButtonMenuItem("Secant Cos");
        fractal_functions[SECANTCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANTCOS);

            }
        });
        secant_type_functions.add(fractal_functions[SECANTCOS]);


        fractal_functions[SECANTPOLY] = new JRadioButtonMenuItem("Secant Polynomial");
        fractal_functions[SECANTPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(SECANTPOLY);

            }
        });
        secant_type_functions.add(fractal_functions[SECANTPOLY]);


        fractal_functions[STEFFENSEN3] = new JRadioButtonMenuItem("Steffensen 3");
        fractal_functions[STEFFENSEN3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(STEFFENSEN3);

            }
        });
        steffensen_type_functions.add(fractal_functions[STEFFENSEN3]);


        fractal_functions[STEFFENSEN4] = new JRadioButtonMenuItem("Steffensen 4");
        fractal_functions[STEFFENSEN4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(STEFFENSEN4);

            }
        });
        steffensen_type_functions.add(fractal_functions[STEFFENSEN4]);


        fractal_functions[STEFFENSENGENERALIZED3] = new JRadioButtonMenuItem("Steffensen Generalized 3");
        fractal_functions[STEFFENSENGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setFunction(STEFFENSENGENERALIZED3);

            }
        });
        steffensen_type_functions.add(fractal_functions[STEFFENSENGENERALIZED3]);


        fractal_functions[function].setSelected(true);
        fractal_functions[function].setEnabled(false);

        planes_menu.add(planes_general_menu);
        planes_menu.addSeparator();
        planes_menu.add(planes_fold_menu);
        planes_menu.addSeparator();
        planes_menu.add(planes_distort_menu);
        planes_menu.addSeparator();
        planes_menu.add(planes_newton_menu);
        planes_menu.addSeparator();
        planes_menu.add(planes_math_menu);


        planes = new JRadioButtonMenuItem[58];

        planes[MU_PLANE] = new JRadioButtonMenuItem("mu");
        planes[MU_PLANE].setToolTipText("The default plane.");
        planes[MU_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(MU_PLANE);

            }
        });
        planes_general_menu.add(planes[MU_PLANE]);


        planes[MU_SQUARED_PLANE] = new JRadioButtonMenuItem("mu^2");
        planes[MU_SQUARED_PLANE].setToolTipText("The mu squared plane.");
        planes[MU_SQUARED_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(MU_SQUARED_PLANE);

            }
        });
        planes_general_menu.add(planes[MU_SQUARED_PLANE]);


        planes[MU_SQUARED_IMAGINARY_PLANE] = new JRadioButtonMenuItem("mu^2i");
        planes[MU_SQUARED_IMAGINARY_PLANE].setToolTipText("The mu squared imaginary plane.");
        planes[MU_SQUARED_IMAGINARY_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(MU_SQUARED_IMAGINARY_PLANE);

            }
        });
        planes_general_menu.add(planes[MU_SQUARED_IMAGINARY_PLANE]);


        planes[INVERSED_MU_PLANE] = new JRadioButtonMenuItem("1 / mu");
        planes[INVERSED_MU_PLANE].setToolTipText("The inversed mu plane.");
        planes[INVERSED_MU_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_MU_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_MU_PLANE]);

        planes[INVERSED_MU2_PLANE] = new JRadioButtonMenuItem("1 / (mu + 0.25)");
        planes[INVERSED_MU2_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU2_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_MU2_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_MU2_PLANE]);


        planes[INVERSED_MU3_PLANE] = new JRadioButtonMenuItem("1 / (mu - 1.40115)");
        planes[INVERSED_MU3_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_MU3_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_MU3_PLANE]);

        planes[INVERSED_MU4_PLANE] = new JRadioButtonMenuItem("1 / (mu - 2)");
        planes[INVERSED_MU4_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU4_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_MU4_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_MU4_PLANE]);

        planes[LAMBDA_PLANE] = new JRadioButtonMenuItem("lambda");
        planes[LAMBDA_PLANE].setToolTipText("The lambda plane.");
        planes[LAMBDA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(LAMBDA_PLANE);

            }
        });
        planes_general_menu.add(planes[LAMBDA_PLANE]);

        planes[INVERSED_LAMBDA_PLANE] = new JRadioButtonMenuItem("1 / lambda");
        planes[INVERSED_LAMBDA_PLANE].setToolTipText("The inversed lambda plane.");
        planes[INVERSED_LAMBDA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_LAMBDA_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_LAMBDA_PLANE]);


        planes[INVERSED_LAMBDA2_PLANE] = new JRadioButtonMenuItem("1 / (lambda - 1)");
        planes[INVERSED_LAMBDA2_PLANE].setToolTipText("An inversed lambda plane variation.");
        planes[INVERSED_LAMBDA2_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_LAMBDA2_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_LAMBDA2_PLANE]);

        planes[BIPOLAR_PLANE] = new JRadioButtonMenuItem("Bipolar");
        planes[BIPOLAR_PLANE].setToolTipText("The bipolar plane.");
        planes[BIPOLAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(BIPOLAR_PLANE);

            }
        });
        planes_general_menu.add(planes[BIPOLAR_PLANE]);

        planes[INVERSED_BIPOLAR_PLANE] = new JRadioButtonMenuItem("Inversed Bipolar");
        planes[INVERSED_BIPOLAR_PLANE].setToolTipText("The inversed bipolar plane.");
        planes[INVERSED_BIPOLAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(INVERSED_BIPOLAR_PLANE);

            }
        });
        planes_general_menu.add(planes[INVERSED_BIPOLAR_PLANE]);

        planes[CIRCLEINVERSION_PLANE] = new JRadioButtonMenuItem("Circle Inversion");
        planes[CIRCLEINVERSION_PLANE].setToolTipText("The circle inversion plane.");
        planes[CIRCLEINVERSION_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(CIRCLEINVERSION_PLANE);

            }
        });
        planes_general_menu.add(planes[CIRCLEINVERSION_PLANE]);


        planes[FOLDUP_PLANE] = new JRadioButtonMenuItem("Fold up");
        planes[FOLDUP_PLANE].setToolTipText("The fold up plane.");
        planes[FOLDUP_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDUP_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDUP_PLANE]);


        planes[FOLDDOWN_PLANE] = new JRadioButtonMenuItem("Fold down");
        planes[FOLDDOWN_PLANE].setToolTipText("The fold down plane.");
        planes[FOLDDOWN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDDOWN_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDDOWN_PLANE]);


        planes[FOLDRIGHT_PLANE] = new JRadioButtonMenuItem("Fold right");
        planes[FOLDRIGHT_PLANE].setToolTipText("The fold right plane.");
        planes[FOLDRIGHT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDRIGHT_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDRIGHT_PLANE]);

        planes[FOLDLEFT_PLANE] = new JRadioButtonMenuItem("Fold left");
        planes[FOLDLEFT_PLANE].setToolTipText("The fold left plane.");
        planes[FOLDLEFT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDLEFT_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDLEFT_PLANE]);


        planes[FOLDIN_PLANE] = new JRadioButtonMenuItem("Fold in");
        planes[FOLDIN_PLANE].setToolTipText("The fold in plane.");
        planes[FOLDIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDIN_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDIN_PLANE]);


        planes[FOLDOUT_PLANE] = new JRadioButtonMenuItem("Fold out");
        planes[FOLDOUT_PLANE].setToolTipText("The fold out plane.");
        planes[FOLDOUT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FOLDOUT_PLANE);

            }
        });
        planes_fold_menu.add(planes[FOLDOUT_PLANE]);



        planes[TWIRL_PLANE] = new JRadioButtonMenuItem("Twirl");
        planes[TWIRL_PLANE].setToolTipText("The twirl plane.");
        planes[TWIRL_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(TWIRL_PLANE);

            }
        });
        planes_distort_menu.add(planes[TWIRL_PLANE]);


        planes[SHEAR_PLANE] = new JRadioButtonMenuItem("Shear");
        planes[SHEAR_PLANE].setToolTipText("The shear plane.");
        planes[SHEAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SHEAR_PLANE);

            }
        });
        planes_distort_menu.add(planes[SHEAR_PLANE]);


        planes[KALEIDOSCOPE_PLANE] = new JRadioButtonMenuItem("Kaleidoscope");
        planes[KALEIDOSCOPE_PLANE].setToolTipText("The kaleidoscope plane.");
        planes[KALEIDOSCOPE_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(KALEIDOSCOPE_PLANE);

            }
        });
        planes_distort_menu.add(planes[KALEIDOSCOPE_PLANE]);

        planes[PINCH_PLANE] = new JRadioButtonMenuItem("Pinch");
        planes[PINCH_PLANE].setToolTipText("The pinch plane.");
        planes[PINCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(PINCH_PLANE);

            }
        });
        planes_distort_menu.add(planes[PINCH_PLANE]);


        planes[NEWTON3_PLANE] = new JRadioButtonMenuItem("Newton 3");
        planes[NEWTON3_PLANE].setToolTipText("The Newton 3 plane.");
        planes[NEWTON3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(NEWTON3_PLANE);

            }
        });
        planes_newton_menu.add(planes[NEWTON3_PLANE]);


        planes[NEWTON4_PLANE] = new JRadioButtonMenuItem("Newton 4");
        planes[NEWTON4_PLANE].setToolTipText("The Newton 4 plane.");
        planes[NEWTON4_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(NEWTON4_PLANE);

            }
        });
        planes_newton_menu.add(planes[NEWTON4_PLANE]);


        planes[NEWTONGENERALIZED3_PLANE] = new JRadioButtonMenuItem("Newton Generalized 3");
        planes[NEWTONGENERALIZED3_PLANE].setToolTipText("The Newton Generalized 3 plane.");
        planes[NEWTONGENERALIZED3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(NEWTONGENERALIZED3_PLANE);

            }
        });
        planes_newton_menu.add(planes[NEWTONGENERALIZED3_PLANE]);


        planes[NEWTONGENERALIZED8_PLANE] = new JRadioButtonMenuItem("Newton Generalized 8");
        planes[NEWTONGENERALIZED8_PLANE].setToolTipText("The Newton Generalized 8 plane.");
        planes[NEWTONGENERALIZED8_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(NEWTONGENERALIZED8_PLANE);

            }
        });
        planes_newton_menu.add(planes[NEWTONGENERALIZED8_PLANE]);



        planes[EXP_PLANE] = new JRadioButtonMenuItem("exp");
        planes[EXP_PLANE].setToolTipText("The exponential plane.");
        planes[EXP_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(EXP_PLANE);

            }
        });
        planes_math_menu.add(planes[EXP_PLANE]);


        planes[LOG_PLANE] = new JRadioButtonMenuItem("log");
        planes[LOG_PLANE].setToolTipText("The logarithmic plane.");
        planes[LOG_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(LOG_PLANE);

            }
        });
        planes_math_menu.add(planes[LOG_PLANE]);


        planes[SQRT_PLANE] = new JRadioButtonMenuItem("sqrt");
        planes[SQRT_PLANE].setToolTipText("The square root plane.");
        planes[SQRT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SQRT_PLANE);

            }
        });
        planes_math_menu.add(planes[SQRT_PLANE]);


        planes[ABS_PLANE] = new JRadioButtonMenuItem("abs");
        planes[ABS_PLANE].setToolTipText("The absolute value plane.");
        planes[ABS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ABS_PLANE);

            }
        });
        planes_math_menu.add(planes[ABS_PLANE]);

        planes[GAMMA_PLANE] = new JRadioButtonMenuItem("gamma");
        planes[GAMMA_PLANE].setToolTipText("The gamma function plane.");
        planes[GAMMA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(GAMMA_PLANE);

            }
        });
        planes_math_menu.add(planes[GAMMA_PLANE]);

        planes[FACT_PLANE] = new JRadioButtonMenuItem("factorial");
        planes[FACT_PLANE].setToolTipText("The factorial plane.");
        planes[FACT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(FACT_PLANE);

            }
        });
        planes_math_menu.add(planes[FACT_PLANE]);


        planes[SIN_PLANE] = new JRadioButtonMenuItem("sin");
        planes[SIN_PLANE].setToolTipText("The sin plane.");
        planes[SIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SIN_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[SIN_PLANE]);


        planes[COS_PLANE] = new JRadioButtonMenuItem("cos");
        planes[COS_PLANE].setToolTipText("The cos plane.");
        planes[COS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(COS_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[COS_PLANE]);


        planes[TAN_PLANE] = new JRadioButtonMenuItem("tan");
        planes[TAN_PLANE].setToolTipText("The tan plane.");
        planes[TAN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(TAN_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[TAN_PLANE]);


        planes[COT_PLANE] = new JRadioButtonMenuItem("cot");
        planes[COT_PLANE].setToolTipText("The cot plane.");
        planes[COT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(COT_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[COT_PLANE]);


        planes[SINH_PLANE] = new JRadioButtonMenuItem("sinh");
        planes[SINH_PLANE].setToolTipText("The hyperbolic sin plane.");
        planes[SINH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SINH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[SINH_PLANE]);


        planes[COSH_PLANE] = new JRadioButtonMenuItem("cosh");
        planes[COSH_PLANE].setToolTipText("The hyperbolic cos plane.");
        planes[COSH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(COSH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[COSH_PLANE]);


        planes[TANH_PLANE] = new JRadioButtonMenuItem("tanh");
        planes[TANH_PLANE].setToolTipText("The hyperbolic tan plane.");
        planes[TANH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(TANH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[TANH_PLANE]);


        planes[COTH_PLANE] = new JRadioButtonMenuItem("coth");
        planes[COTH_PLANE].setToolTipText("The hyperbolic cot plane.");
        planes[COTH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(COTH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[COTH_PLANE]);


        planes[SEC_PLANE] = new JRadioButtonMenuItem("sec");
        planes[SEC_PLANE].setToolTipText("The sec plane.");
        planes[SEC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SEC_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[SEC_PLANE]);


        planes[CSC_PLANE] = new JRadioButtonMenuItem("csc");
        planes[CSC_PLANE].setToolTipText("The csc plane.");
        planes[CSC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(CSC_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[CSC_PLANE]);


        planes[SECH_PLANE] = new JRadioButtonMenuItem("sech");
        planes[SECH_PLANE].setToolTipText("The hyperbolic sec plane.");
        planes[SECH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(SECH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[SECH_PLANE]);


        planes[CSCH_PLANE] = new JRadioButtonMenuItem("csch");
        planes[CSCH_PLANE].setToolTipText("The hyperbolic csc plane.");
        planes[CSCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(CSCH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[CSCH_PLANE]);


        planes[ASIN_PLANE] = new JRadioButtonMenuItem("asin");
        planes[ASIN_PLANE].setToolTipText("The inverse sin plane.");
        planes[ASIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ASIN_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ASIN_PLANE]);


        planes[ACOS_PLANE] = new JRadioButtonMenuItem("acos");
        planes[ACOS_PLANE].setToolTipText("The inverse cos plane.");
        planes[ACOS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACOS_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACOS_PLANE]);


        planes[ATAN_PLANE] = new JRadioButtonMenuItem("atan");
        planes[ATAN_PLANE].setToolTipText("The inverse tan plane.");
        planes[ATAN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ATAN_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ATAN_PLANE]);


        planes[ACOT_PLANE] = new JRadioButtonMenuItem("acot");
        planes[ACOT_PLANE].setToolTipText("The inverse cot plane.");
        planes[ACOT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACOT_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACOT_PLANE]);


        planes[ASINH_PLANE] = new JRadioButtonMenuItem("asinh");
        planes[ASINH_PLANE].setToolTipText("The inverse hyperbolic sin plane.");
        planes[ASINH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ASINH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ASINH_PLANE]);


        planes[ACOSH_PLANE] = new JRadioButtonMenuItem("acosh");
        planes[ACOSH_PLANE].setToolTipText("The inverse hyperbolic cos plane.");
        planes[ACOSH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACOSH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACOSH_PLANE]);


        planes[ATANH_PLANE] = new JRadioButtonMenuItem("atanh");
        planes[ATANH_PLANE].setToolTipText("The inverse hyperbolic tan plane.");
        planes[ATANH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ATANH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ATANH_PLANE]);


        planes[ACOTH_PLANE] = new JRadioButtonMenuItem("acoth");
        planes[ACOTH_PLANE].setToolTipText("The inverse hyperbolic cot plane.");
        planes[ACOTH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACOTH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACOTH_PLANE]);


        planes[ASEC_PLANE] = new JRadioButtonMenuItem("asec");
        planes[ASEC_PLANE].setToolTipText("The inverse sec plane.");
        planes[ASEC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ASEC_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ASEC_PLANE]);


        planes[ACSC_PLANE] = new JRadioButtonMenuItem("acsc");
        planes[ACSC_PLANE].setToolTipText("The inverse csc plane.");
        planes[ACSC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACSC_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACSC_PLANE]);


        planes[ASECH_PLANE] = new JRadioButtonMenuItem("asech");
        planes[ASECH_PLANE].setToolTipText("The inverse hyperbolic sec plane.");
        planes[ASECH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ASECH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ASECH_PLANE]);


        planes[ACSCH_PLANE] = new JRadioButtonMenuItem("acsch");
        planes[ACSCH_PLANE].setToolTipText("The inverse hyperbolic csc plane.");
        planes[ACSCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(ACSCH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[ACSCH_PLANE]);

        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_trigonometric_menu);
        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_inverse_trigonometric_menu);


        planes[USER_PLANE] = new JRadioButtonMenuItem("User Plane");
        planes[USER_PLANE].setToolTipText("A plane defined by the user.");
        planes[USER_PLANE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.SHIFT_MASK));
        planes[USER_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setPlane(USER_PLANE);

            }
        });
        planes_menu.addSeparator();
        planes_menu.add(planes[USER_PLANE]);

        planes[plane_type].setSelected(true);
        planes[plane_type].setEnabled(false);


        out_coloring_modes = new JRadioButtonMenuItem[21];

        out_coloring_modes[ESCAPE_TIME] = new JRadioButtonMenuItem("Escape Time");
        out_coloring_modes[ESCAPE_TIME].setToolTipText("Sets the out-coloring method, using the iterations.");
        out_coloring_modes[ESCAPE_TIME].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME]);


        out_coloring_modes[BINARY_DECOMPOSITION] = new JRadioButtonMenuItem("Binary Decomposition");
        out_coloring_modes[BINARY_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using binary decomposition.");
        out_coloring_modes[BINARY_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(BINARY_DECOMPOSITION);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BINARY_DECOMPOSITION]);


        out_coloring_modes[BINARY_DECOMPOSITION2] = new JRadioButtonMenuItem("Binary Decomposition 2");
        out_coloring_modes[BINARY_DECOMPOSITION2].setToolTipText("Sets the out-coloring method, using binary decomposition 2.");
        out_coloring_modes[BINARY_DECOMPOSITION2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(BINARY_DECOMPOSITION2);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BINARY_DECOMPOSITION2]);


        out_coloring_modes[ITERATIONS_PLUS_RE] = new JRadioButtonMenuItem("Escape Time + Re");
        out_coloring_modes[ITERATIONS_PLUS_RE].setToolTipText("Sets the out-coloring method, using the iterations + Re(z).");
        out_coloring_modes[ITERATIONS_PLUS_RE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ITERATIONS_PLUS_RE);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_RE]);


        out_coloring_modes[ITERATIONS_PLUS_IM] = new JRadioButtonMenuItem("Escape Time + Im");
        out_coloring_modes[ITERATIONS_PLUS_IM].setToolTipText("Sets the out-coloring method, using the iterations + Im(z).");
        out_coloring_modes[ITERATIONS_PLUS_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ITERATIONS_PLUS_IM);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_IM]);


        out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem("Escape Time + Re / Im");
        out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z)/Im(z).");
        out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ITERATIONS_PLUS_RE_DIVIDE_IM);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM]);


        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem("Escape Time + Re + Im + Re / Im");
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out-coloring method, using the iterations + Re(z) + Im(z) + Re(z)/Im(z).");
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);


        out_coloring_modes[BIOMORPH] = new JRadioButtonMenuItem("Biomorph");
        out_coloring_modes[BIOMORPH].setToolTipText("Sets the out-coloring method, using biomorph.");
        out_coloring_modes[BIOMORPH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(BIOMORPH);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BIOMORPH]);


        out_coloring_modes[COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Color Decomposition");
        out_coloring_modes[COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using color decomposition.");
        out_coloring_modes[COLOR_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(COLOR_DECOMPOSITION);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[COLOR_DECOMPOSITION]);


        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Escape Time + Color Decomposition");
        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION].setToolTipText("Sets the out-coloring method, using iterations + color decomposition.");
        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_COLOR_DECOMPOSITION);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION]);


        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer.");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GAUSSIAN_INTEGER);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER]);


        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 2");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 2.");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GAUSSIAN_INTEGER2);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2]);


        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 3");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 3.");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GAUSSIAN_INTEGER3);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3]);


        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 4");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 4.");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GAUSSIAN_INTEGER4);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4]);


        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5] = new JRadioButtonMenuItem("Escape Time + Gaussian Integer 5");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setToolTipText("Sets the out-coloring method, using Escape Time + Gaussian Integer 5.");
        out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GAUSSIAN_INTEGER5);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5]);


        out_coloring_modes[ESCAPE_TIME_ALGORITHM] = new JRadioButtonMenuItem("Escape Time + Algorithm");
        out_coloring_modes[ESCAPE_TIME_ALGORITHM].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm.");
        out_coloring_modes[ESCAPE_TIME_ALGORITHM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_ALGORITHM);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_ALGORITHM]);


        out_coloring_modes[ESCAPE_TIME_ALGORITHM2] = new JRadioButtonMenuItem("Escape Time + Algorithm 2");
        out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setToolTipText("Sets the out-coloring method, using Escape Time + Algorithm 2.");
        out_coloring_modes[ESCAPE_TIME_ALGORITHM2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_ALGORITHM2);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_ALGORITHM2]);


        out_coloring_modes[DISTANCE_ESTIMATOR] = new JRadioButtonMenuItem("Distance Estimator");
        out_coloring_modes[DISTANCE_ESTIMATOR].setToolTipText("Sets the out-coloring method, using Distance Estimator.");
        out_coloring_modes[DISTANCE_ESTIMATOR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(DISTANCE_ESTIMATOR);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[DISTANCE_ESTIMATOR]);


        out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS] = new JRadioButtonMenuItem("Escape Time + Escape Radius");
        out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setToolTipText("Sets the out-coloring method, using Escape Time + Escape Radius.");
        out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_ESCAPE_RADIUS);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS]);


        out_coloring_modes[ESCAPE_TIME_GRID] = new JRadioButtonMenuItem("Escape Time + Grid");
        out_coloring_modes[ESCAPE_TIME_GRID].setToolTipText("Sets the out-coloring method, using Escape Time + Grid.");
        out_coloring_modes[ESCAPE_TIME_GRID].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ESCAPE_TIME_GRID);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_GRID]);

        out_coloring_modes[ATOM_DOMAIN] = new JRadioButtonMenuItem("Atom Domain");
        out_coloring_modes[ATOM_DOMAIN].setToolTipText("Sets the out-coloring method, using atom domain.");
        out_coloring_modes[ATOM_DOMAIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setOutColoringMode(ATOM_DOMAIN);

            }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ATOM_DOMAIN]);


        out_coloring_modes[out_coloring_algorithm].setSelected(true);
        out_coloring_modes[out_coloring_algorithm].setEnabled(false);



        in_coloring_modes = new JRadioButtonMenuItem[10];

        in_coloring_modes[MAXIMUM_ITERATIONS] = new JRadioButtonMenuItem("Maximum Iterations");
        in_coloring_modes[MAXIMUM_ITERATIONS].setToolTipText("Sets the in-coloring method, using the maximum iterations.");
        in_coloring_modes[MAXIMUM_ITERATIONS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(MAXIMUM_ITERATIONS);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[MAXIMUM_ITERATIONS]);


        in_coloring_modes[Z_MAG] = new JRadioButtonMenuItem("norm(z)");
        in_coloring_modes[Z_MAG].setToolTipText("Sets the in-coloring method, using the norm of z.");
        in_coloring_modes[Z_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(Z_MAG);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[Z_MAG]);


        in_coloring_modes[DECOMPOSITION_LIKE] = new JRadioButtonMenuItem("Decomposition Like");
        in_coloring_modes[DECOMPOSITION_LIKE].setToolTipText("Sets the in-coloring method, using decomposition.");
        in_coloring_modes[DECOMPOSITION_LIKE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(DECOMPOSITION_LIKE);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[DECOMPOSITION_LIKE]);


        in_coloring_modes[RE_DIVIDE_IM] = new JRadioButtonMenuItem("Re / Im");
        in_coloring_modes[RE_DIVIDE_IM].setToolTipText("Sets the in-coloring method, using Re(z) / Im(z).");
        in_coloring_modes[RE_DIVIDE_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(RE_DIVIDE_IM);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[RE_DIVIDE_IM]);


        in_coloring_modes[COS_MAG] = new JRadioButtonMenuItem("cos(norm(z))");
        in_coloring_modes[COS_MAG].setToolTipText("Sets the in-coloring method, using the cos of the norm(z).");
        in_coloring_modes[COS_MAG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(COS_MAG);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[COS_MAG]);


        in_coloring_modes[MAG_TIMES_COS_RE_SQUARED] = new JRadioButtonMenuItem("norm(z) * cos(Re^2)");
        in_coloring_modes[MAG_TIMES_COS_RE_SQUARED].setToolTipText("Sets the in-coloring method, using norm(z) * cos(Re(z)^2).");
        in_coloring_modes[MAG_TIMES_COS_RE_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(MAG_TIMES_COS_RE_SQUARED);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[MAG_TIMES_COS_RE_SQUARED]);

        in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED] = new JRadioButtonMenuItem("sin(Re^2 - Im^2)");
        in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED].setToolTipText("Sets the in-coloring method, using sin(Re(z)^2 - Im(z)^2).");
        in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(SIN_RE_SQUARED_MINUS_IM_SQUARED);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[SIN_RE_SQUARED_MINUS_IM_SQUARED]);


        in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM] = new JRadioButtonMenuItem("atan(Re * Im * |Re| * |Im|)");
        in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].setToolTipText("Sets the in-coloring method, using atan(Re(z) * Im(z) * |Re(z)| * |Im(z)|).");
        in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM]);


        in_coloring_modes[SQUARES] = new JRadioButtonMenuItem("Squares");
        in_coloring_modes[SQUARES].setToolTipText("Sets the in-coloring method, using squares.");
        in_coloring_modes[SQUARES].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(SQUARES);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[SQUARES]);

        in_coloring_modes[SQUARES2] = new JRadioButtonMenuItem("Squares 2");
        in_coloring_modes[SQUARES2].setToolTipText("Sets the in-coloring method, using squares 2.");
        in_coloring_modes[SQUARES2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setInColoringMode(SQUARES2);

            }
        });
        in_coloring_mode_menu.add(in_coloring_modes[SQUARES2]);

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


        bailout_tests = new JRadioButtonMenuItem[6];

        bailout_tests[BAILOUT_TEST_CIRCLE] = new JRadioButtonMenuItem("Circle (Euclidean norm)");
        bailout_tests[BAILOUT_TEST_CIRCLE].setToolTipText("The default bailout test.");
        bailout_tests[BAILOUT_TEST_CIRCLE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_CIRCLE);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_CIRCLE]);


        bailout_tests[BAILOUT_TEST_SQUARE] = new JRadioButtonMenuItem("Square (Infinity norm)");
        bailout_tests[BAILOUT_TEST_SQUARE].setToolTipText("The square bailout test.");
        bailout_tests[BAILOUT_TEST_SQUARE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_SQUARE);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_SQUARE]);


        bailout_tests[BAILOUT_TEST_RHOMBUS] = new JRadioButtonMenuItem("Rhombus (One norm)");
        bailout_tests[BAILOUT_TEST_RHOMBUS].setToolTipText("The rhombus bailout test.");
        bailout_tests[BAILOUT_TEST_RHOMBUS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_RHOMBUS);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_RHOMBUS]);

        bailout_tests[BAILOUT_TEST_NNORM] = new JRadioButtonMenuItem("N-Norm");
        bailout_tests[BAILOUT_TEST_NNORM].setToolTipText("The Nth norm test.");
        bailout_tests[BAILOUT_TEST_NNORM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_NNORM);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_NNORM]);

        bailout_tests[BAILOUT_TEST_STRIP] = new JRadioButtonMenuItem("Strip");
        bailout_tests[BAILOUT_TEST_STRIP].setToolTipText("The strip bailout test.");
        bailout_tests[BAILOUT_TEST_STRIP].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_STRIP);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_STRIP]);


        bailout_tests[BAILOUT_TEST_HALFPLANE] = new JRadioButtonMenuItem("Halfplane");
        bailout_tests[BAILOUT_TEST_HALFPLANE].setToolTipText("The halfplane bailout test.");
        bailout_tests[BAILOUT_TEST_HALFPLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailoutTest(BAILOUT_TEST_HALFPLANE);

            }
        });
        bailout_test_menu.add(bailout_tests[BAILOUT_TEST_HALFPLANE]);


        bailout_tests[bailout_test_algorithm].setSelected(true);
        bailout_tests[bailout_test_algorithm].setEnabled(false);

        burning_ship_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBurningShip();

            }
        });

        mandel_grass_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setMandelGrass();

            }
        });

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
        coloring_option[10] = "Green White";
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
            palette[i] = new JRadioButtonMenuItem(coloring_option[i]);
            palette[i].addActionListener(new ActionListener() {

                int temp = i;

                public void actionPerformed(ActionEvent e) {

                    setPalette(temp);

                }
            });
            palette_menu.add(palette[i]);
        }

        palette_menu.addSeparator();

        palette[i] = new JRadioButtonMenuItem(coloring_option[i]);
        palette[i].addActionListener(new ActionListener() {

            int temp = i;

            public void actionPerformed(ActionEvent e) {

                customPaletteEditor(temp);

            }
        });
        palette_menu.add(palette[i]);
        palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));

        palette[color_choice].setSelected(true);
        palette[color_choice].setEnabled(false);

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
        palette[10].setToolTipText("A palette based on green and white.");
        palette[11].setToolTipText("A palette based on colors of dusk.");
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


        anti_aliasing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setAntiAliasing();

            }
        });

        edges_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEdgeDetection();

            }
        });


        invert_colors_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setInvertColors();

            }
        });

        emboss_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEmboss();

            }
        });

        histogram_eq_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setHistogramEqualization();

            }
        });

        sharpness_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setSharpness();

            }
        });

        blurring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBlurring();

            }
        });

        mask_color_channel_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setMaskColorChannel();

            }
        });

        fade_out_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setFadeOut();

            }
        });

        color_channel_swapping_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorChannelSwapping();

            }
        });

        contrast_brightness_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setContrastBrightness();

            }
        });

        grayscale_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setGrayscale();

            }
        });

        color_temperature_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorTemperature();

            }
        });

        color_channel_mixing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorChannelMixing();

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

        orbit_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setOrbitColor();

            }
        });

        d3_details_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                set3DDetails();

            }
        });

        d3_details_opt.setEnabled(false);

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

        window_menu.add(toolbar_opt);
        window_menu.add(statusbar_opt);
        toolbar_opt.setSelected(true);
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

        line.setSelected(true);
        line.setEnabled(false);

        orbit_style_menu.add(line);
        orbit_style_menu.add(dot);

        orbit_menu.add(orbit_color_opt);
        orbit_menu.addSeparator();
        orbit_menu.add(orbit_style_menu);


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

        d3_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                set3DOption();

            }
        });

        help_contents.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showCHMHelpFile();

            }
        });


        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if(!color_cycling) {
                    main_panel.repaint();
                }
                JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br><font size='4'><img src=\"" + getClass().getResource("/fractalzoomer/icons/mandel2.png") + "\"><br><br>Version: <b>1.0.5.3</b><br><br>Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></font></center></html>", "About", JOptionPane.INFORMATION_MESSAGE);

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
        random_palette_button.setIcon(getIcon("/fractalzoomer/icons/palette2.png"));
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


        statusbar = new JToolBar();
        statusbar.setFloatable(false);
        add(statusbar, BorderLayout.PAGE_END);
        statusbar.setBorderPainted(true);
        statusbar.setPreferredSize(new Dimension(0, 20));

        JLabel label = new JLabel(" Re: ");
        // label.setFont(new Font("default", Font.PLAIN, 11));
        statusbar.add(label);

        real = new JTextField("Real");
        real.setHorizontalAlignment(JTextField.RIGHT);
        real.setPreferredSize(new Dimension(100, 0));
        real.setEditable(false);
        real.setForeground(new Color(16, 78, 139));
        real.setToolTipText("Displays the Real part of the complex number.");

        statusbar.add(real);
        statusbar.add(new JLabel("   "));

        label = new JLabel("Im: ");
        //label.setFont(new Font("default", Font.PLAIN, 11));
        statusbar.add(label);

        imaginary = new JTextField("Imaginary");
        imaginary.setPreferredSize(new Dimension(100, 0));
        imaginary.setHorizontalAlignment(JTextField.RIGHT);
        imaginary.setEditable(false);
        imaginary.setForeground(new Color(0, 139, 69));
        imaginary.setToolTipText("Displays the Imaginary part of the complex number.");

        statusbar.add(imaginary);
        statusbar.add(new JLabel(" "));

        statusbar.addSeparator();

        main_panel = new MainPanel(this);
        main_panel.setPreferredSize(new Dimension(image_size, image_size));
        scroll_pane = new JScrollPane(main_panel);

        add(scroll_pane);

        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(220, 0));
        progress.setStringPainted(true);
        progress.setForeground(new Color(255, 185, 15));
        progress.setValue(0);

        statusbar.add(progress);
        statusbar.addSeparator();
        mode = new JLabel("Normal mode");
        mode.setPreferredSize(new Dimension(75, 0));
        mode.setToolTipText("Displays the active mode.");
        statusbar.add(mode);


        file_menu.add(starting_position);
        file_menu.add(go_to);
        file_menu.add(zoom_in);
        file_menu.add(zoom_out);
        file_menu.addSeparator();
        file_menu.add(save_settings);
        file_menu.add(load_settings);
        file_menu.addSeparator();
        file_menu.add(save_image);
        file_menu.addSeparator();
        file_menu.add(exit);


        colors_menu.add(out_coloring_mode_menu);
        colors_menu.add(in_coloring_mode_menu);
        colors_menu.add(smoothing_opt);
        colors_menu.add(exterior_de_opt);
        colors_menu.addSeparator();
        colors_menu.add(fract_color);
        colors_menu.addSeparator();
        colors_menu.add(palette_menu);
        colors_menu.add(random_palette);
        colors_menu.add(roll_palette_menu);


        tools_options_menu.add(orbit_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(fast_julia_filters_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(d3_details_opt);
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

        options_menu.add(fractal_functions_menu);
        options_menu.addSeparator();
        options_menu.add(planes_menu);
        options_menu.addSeparator();
        options_menu.add(init_val_opt);
        options_menu.addSeparator();
        options_menu.add(perturbation_opt);
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
        options_menu.add(window_menu);



        tools_menu.add(orbit_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_map_opt);
        tools_menu.addSeparator();
        tools_menu.add(d3_opt);
        tools_menu.addSeparator();
        tools_menu.add(color_cycling_opt);
        tools_menu.addSeparator();
        tools_menu.add(grid_opt);
        tools_menu.addSeparator();
        tools_menu.add(zoom_window_opt);
        tools_menu.addSeparator();
        tools_menu.add(boundaries_opt);


        filters_menu.add(anti_aliasing_opt);
        filters_menu.addSeparator();
        filters_menu.add(edges_opt);
        filters_menu.addSeparator();
        filters_menu.add(sharpness_opt);
        filters_menu.addSeparator();
        filters_menu.add(blurring_opt);
        filters_menu.addSeparator();
        filters_menu.add(emboss_opt);
        filters_menu.addSeparator();
        filters_menu.add(histogram_eq_opt);
        filters_menu.addSeparator();
        filters_menu.add(contrast_brightness_opt);
        filters_menu.addSeparator();
        filters_menu.add(color_temperature_opt);
        filters_menu.addSeparator();
        filters_menu.add(fade_out_opt);
        filters_menu.addSeparator();
        filters_menu.add(invert_colors_opt);
        filters_menu.addSeparator();
        filters_menu.add(mask_color_channel_opt);
        filters_menu.addSeparator();
        filters_menu.add(color_channel_swapping_opt);
        filters_menu.addSeparator();
        filters_menu.add(color_channel_mixing_opt);
        filters_menu.addSeparator();
        filters_menu.add(grayscale_opt);





        help_menu.add(help_contents);
        help_menu.addSeparator();
        help_menu.add(about);

        menubar.add(file_menu);
        menubar.add(options_menu);
        menubar.add(tools_menu);
        menubar.add(filters_menu);
        menubar.add(help_menu);


        setJMenuBar(menubar);

        threads = new ThreadDraw[n][n];

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
                        if(!julia) {
                            setSettingsFractal(e);
                        }
                        else {
                            setSettingsJulia(e);
                        }
                    }
                    else {
                        mx0 = (int)main_panel.getMousePosition().getX();
                        my0 = (int)main_panel.getMousePosition().getY();
                    }
                }
                else {
                    setOrbit(e);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                if(whole_image_done) {
                    if(backup_orbit != null && orbit) {
                        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                        System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    }

                    if(!grid || orbit || !boundaries) {
                        if(orbit) {
                            while(pixels_orbit.isAlive()) {
                            }
                            main_panel.repaint();
                        }

                    }
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(whole_image_done) {
                    //if(backup_orbit != null && orbit) {
                    //image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    //System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    //}
                    if(!color_cycling) {
                        main_panel.repaint();
                    }
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(whole_image_done) {
                    if(backup_orbit != null && orbit && e.getModifiers() == InputEvent.BUTTON1_MASK) {
                        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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

                if(image_size > 2000) {
                    d3_opt.setEnabled(false);
                    d3_button.setEnabled(false);
                    d3_button.setSelected(false);
                    d3_opt.setSelected(false);
                    d3 = false;
                    d3_details_opt.setEnabled(false);
                    boundary_tracing_opt.setEnabled(true);
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
                last_used = null;

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                backup_orbit = null;

                if(d3) {
                    Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());
                }

                if(julia && first_seed) {
                    julia = false;
                    julia_opt.setSelected(false);
                    julia_button.setSelected(false);


                    if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
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
                    //if(backup_orbit != null && orbit) {
                    //image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    //System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    //}
                    main_panel.repaint();
                }
            }
        });

        scroll_pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(whole_image_done) {
                    //if(backup_orbit != null && orbit) {
                    //image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    //System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
                    //}
                    main_panel.repaint();
                }

            }
        });

        scroll_pane.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if(orbit) {
                    setOrbit(e);
                }
                else if(d3) {
                    rotate3DModel(e);
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                try {
                    if(old_d3) {
                        real.setText("Real");
                        imaginary.setText("Imaginary");
                        return;
                    }

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
                    double temp_ycenter_size = old_yCenter - size_2_y;
                    double temp_size_image_size_x = old_size / image_size;
                    double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

                    double temp2 = temp_xcenter_size + temp_size_image_size_x * x1 - old_rotation_center[0];
                    double temp = temp_ycenter_size + temp_size_image_size_y * y1 - old_rotation_center[1];

                    double temp3 = temp2 * old_rotation_vals[0] - temp * old_rotation_vals[1] + old_rotation_center[0];

                    temp3 = temp3 == 0 ? 0.0 : temp3;

                    real.setText("" + temp3);

                    temp3 = temp2 * old_rotation_vals[1] + temp * old_rotation_vals[0] + old_rotation_center[1];

                    temp3 = temp3 == 0 ? 0.0 : -temp3;

                    imaginary.setText("" + temp3);

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

        requestFocus();

        progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

        setOptions(false);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            case 0:
                temp += "   Mandelbrot";
                break;
            case 1:
                temp += "   Mandelbrot Cubed";
                break;
            case 2:
                temp += "   Mandelbrot Fourth";
                break;
            case 3:
                temp += "   Mandelbrot Fifth";
                break;
            case 4:
                temp += "   Mandelbrot Sixth";
                break;
            case 5:
                temp += "   Mandelbrot Seventh";
                break;
            case 6:
                temp += "   Mandelbrot Eighth";
                break;
            case 7:
                temp += "   Mandelbrot Ninth";
                break;
            case 8:
                temp += "   Mandelbrot Tenth";
                break;
            case MANDELBROTNTH:
                temp += "  z = z^" + z_exponent + " + c";
                break;
            case MANDELBROTWTH:
                z_exponent_complex[0] = z_exponent_complex[0] == 0 ? 0.0 : z_exponent_complex[0];

                String temp3 = "";

                if(z_exponent_complex[1] > 0) {
                    temp3 = z_exponent_complex[0] + "+" + (z_exponent_complex[1]) + "i";
                }
                else {
                    if(z_exponent_complex[1] == 0) {
                        temp3 = z_exponent_complex[0] + "+" + (0.0) + "i";
                    }
                    else {
                        temp3 = z_exponent_complex[0] + "" + (z_exponent_complex[1]) + "i";
                    }
                }
                temp += "  z = z^(" + temp3 + ") + c";
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
            case STEFFENSEN3:
                temp += "   Steffensen p(z) = z^3 -1";
                break;
            case STEFFENSEN4:
                temp += "   Steffensen p(z) = z^4 -1";
                break;
            case STEFFENSENGENERALIZED3:
                temp += "   Steffensen p(z) = z^3 -2z +2";
                break;
            case NOVA:

                z_exponent_nova[0] = z_exponent_nova[0] == 0 ? 0.0 : z_exponent_nova[0];

                temp3 = "";

                if(z_exponent_nova[1] > 0) {
                    temp3 = z_exponent_nova[0] + "+" + (z_exponent_nova[1]) + "i";
                }
                else {
                    if(z_exponent_nova[1] == 0) {
                        temp3 = z_exponent_nova[0] + "+" + (0.0) + "i";
                    }
                    else {
                        temp3 = z_exponent_nova[0] + "" + (z_exponent_nova[1]) + "i";
                    }
                }

                relaxation[0] = relaxation[0] == 0 ? 0.0 : relaxation[0];

                String temp4 = "";

                if(relaxation[1] > 0) {
                    temp4 = relaxation[0] + "+" + (relaxation[1]) + "i";
                }
                else {
                    if(relaxation[1] == 0) {
                        temp4 = relaxation[0] + "+" + (0.0) + "i";
                    }
                    else {
                        temp4 = relaxation[0] + "" + (relaxation[1]) + "i";
                    }
                }

                switch (nova_method) {

                    case MainWindow.NOVA_NEWTON:
                        temp += "   Nova-Newton, e: " + temp3 + ", r: " + temp4;
                        break;
                    case MainWindow.NOVA_HALLEY:
                        temp += "   Nova-Halley, e: " + temp3 + ", r: " + temp4;
                        break;
                    case MainWindow.NOVA_SCHRODER:
                        temp += "   Nova-Schroder, e: " + temp3 + ", r: " + temp4;
                        break;
                    case MainWindow.NOVA_HOUSEHOLDER:
                        temp += "   Nova-Householder, e: " + temp3 + ", r: " + temp4;
                        break;
                    case MainWindow.NOVA_SECANT:
                        temp += "   Nova-Secant, e: " + temp3 + ", r: " + temp4;
                        break;
                    case MainWindow.NOVA_STEFFENSEN:
                        temp += "   Nova-Steffensen, e: " + temp3 + ", r: " + temp4;
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
            case USER_FORMULA:
                temp += "   User Formula";
                break;
            case USER_FORMULA_ITERATION_BASED:
                temp += "   User Formula Iteration Based";
                break;
            case USER_FORMULA_CONDITIONAL:
                temp += "   User Formula Conditional";
                break;
            case SZEGEDI_BUTTERFLY1:
                temp += "   Szegedi Butterfly 1";
                break;
            case SZEGEDI_BUTTERFLY2:
                temp += "   Szegedi Butterfly 2";
                break;

        }

        temp1 = temp1 == 0.0 ? 0.0 : temp1;

        if(-temp2 > 0) {
            temp += "   Center: " + temp1 + "+" + (-temp2) + "i" + "   Size: " + size;
        }
        else {
            if(temp2 == 0) {
                temp += "   Center: " + temp1 + "+" + (0.0) + "i" + "   Size: " + size;
            }
            else {
                temp += "   Center: " + temp1 + "" + (-temp2) + "i" + "   Size: " + size;
            }
        }



        setTitle(temp);

        if(!d3) {
            try {
                int x1 = (int)main_panel.getMousePosition().getX();
                int y1 = (int)main_panel.getMousePosition().getY();


                double size_2_x = size * 0.5;
                double size_2_y = (size * height_ratio) * 0.5;
                double temp_xcenter_size = xCenter - size_2_x;
                double temp_ycenter_size = yCenter - size_2_y;
                double temp_size_image_size_x = size / image_size;
                double temp_size_image_size_y = (size * height_ratio) / image_size;

                double temp4 = temp_xcenter_size + temp_size_image_size_x * x1 - rotation_center[0];
                double temp5 = temp_ycenter_size + temp_size_image_size_y * y1 - rotation_center[1];

                double temp6 = temp4 * rotation_vals[0] - temp5 * rotation_vals[1] + rotation_center[0];

                temp6 = temp6 == 0 ? 0.0 : temp6;

                real.setText("" + temp6);

                temp6 = temp4 * rotation_vals[1] + temp5 * rotation_vals[0] + rotation_center[1];

                temp6 = temp6 == 0 ? 0.0 : -temp6;

                imaginary.setText("" + temp6);


                main_panel.repaint();
            }
            catch(Exception ex) {
            }
        }
        else {
            real.setText("Real");
            imaginary.setText("Imaginary");
        }


    }

    private void createThreads() {

        ThreadDraw.resetAtomics();


        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    if(julia) {
                        if(d3) {
                            threads[i][j] = new Palette(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset, xJuliaCenter, yJuliaCenter);
                        }
                        else {
                            threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset, xJuliaCenter, yJuliaCenter);
                        }
                    }
                    else {
                        if(d3) {
                            threads[i][j] = new Palette(color_choice, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset);
                        }
                        else {
                            threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset);
                        }
                    }
                }
                else {
                    if(julia) {
                        if(d3) {
                            threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset, xJuliaCenter, yJuliaCenter);
                        }
                        else {
                            threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset, xJuliaCenter, yJuliaCenter);
                        }
                    }
                    else {
                        if(d3) {
                            threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset);

                        }
                        else {
                            threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, d3_height_offset);
                        }
                    }
                }
            }
        }

    }

    private void startThreads(int n) {

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                threads[i][j].start();
            }
        }

    }

    private void saveSettings() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    settings = new SettingsJulia1053(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                else {
                    int temp_bailout_test_algorithm = 0;

                    if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                        temp_bailout_test_algorithm = bailout_test_algorithm;
                    }

                    settings = new SettingsFractals1053(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, temp_bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
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

        return temp.equals("SettingsJulia") || temp.equals("SettingsJulia1049") || temp.equals("SettingsJulia1050") || temp.equals("SettingsJulia1053");

    }

    private String getSettingsVersion(String class_name) {

        String temp = class_name;
        temp = temp.substring(29, temp.length());

        if(temp.equals("SettingsJulia") || temp.equals("SettingsFractals")) {
            return "1.0.4.8";
        }
        else if(temp.equals("SettingsJulia1049") || temp.equals("SettingsFractals1049")) {
            return "1.0.4.9";
        }
        else if(temp.equals("SettingsJulia1050") || temp.equals("SettingsFractals1050")) {
            return "1.0.5.0";
        }
        else if(temp.equals("SettingsJulia1053") || temp.equals("SettingsFractals1053")) {
            return "1.0.5.3";
        }

        return "";

    }

    private void loadSettings() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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

                palette[color_choice].setSelected(false);
                palette[color_choice].setEnabled(true);

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

                zoom_window = false;
                zoom_window_opt.setSelected(false);

                if(isSettingsJulia("" + settings.getClass())) {
                    if(getSettingsVersion("" + settings.getClass()).equals("1.0.4.8")) {
                        xJuliaCenter = ((SettingsJulia)settings).getXJuliaCenter();
                        yJuliaCenter = ((SettingsJulia)settings).getYJuliaCenter();
                    }
                    else if(getSettingsVersion("" + settings.getClass()).equals("1.0.4.9")) {
                        xJuliaCenter = ((SettingsJulia1049)settings).getXJuliaCenter();
                        yJuliaCenter = ((SettingsJulia1049)settings).getYJuliaCenter();
                    }
                    else if(getSettingsVersion("" + settings.getClass()).equals("1.0.5.0")) {
                        xJuliaCenter = ((SettingsJulia1050)settings).getXJuliaCenter();
                        yJuliaCenter = ((SettingsJulia1050)settings).getYJuliaCenter();
                    }
                    else if(getSettingsVersion("" + settings.getClass()).equals("1.0.5.3")) {
                        xJuliaCenter = ((SettingsJulia1053)settings).getXJuliaCenter();
                        yJuliaCenter = ((SettingsJulia1053)settings).getYJuliaCenter();
                    }

                    julia = true;
                    first_seed = false;
                    julia_opt.setSelected(true);
                    julia_button.setSelected(true);
                    julia_map_opt.setEnabled(false);
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
                    mandel_grass = settings.getMandelGrass();

                    if(perturbation) {
                        perturbation_vals = settings.getPerturbationVals();
                    }

                    if(init_val) {
                        initial_vals = settings.getInitialVals();
                    }

                    if(mandel_grass) {
                        mandel_grass_vals = settings.getMandelGrassVals();
                    }

                    if(!perturbation && !init_val) {
                        rootFindingMethodsSetEnabled(true);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                    }
                    else {
                        julia_opt.setEnabled(false);
                        julia_button.setEnabled(false);
                        julia_map_opt.setEnabled(false);
                        rootFindingMethodsSetEnabled(false);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
                        out_coloring_modes[ATOM_DOMAIN].setEnabled(false);
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
                function = settings.getFunction();
                bailout = settings.getBailout();
                burning_ship = settings.getBurningShip();
                color_cycling_location = settings.getColorCyclingLocation();
                plane_type = settings.getPlaneType();

                if(getSettingsVersion("" + settings.getClass()).equals("1.0.4.8") || getSettingsVersion("" + settings.getClass()).equals("1.0.4.9") || getSettingsVersion("" + settings.getClass()).equals("1.0.5.0")) {
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
                    else if(plane_type == FOLDIN_PLANE) {
                        plane_transform_center[0] = 1;
                        plane_transform_center[1] = 0;
                    }
                    else if(plane_type == FOLDOUT_PLANE) {
                        plane_transform_center[0] = 1;
                        plane_transform_center[1] = 0;
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
                    else if(plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE || plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {
                        plane_transform_center = ((SettingsFractals1053)settings).getPlaneTransformCenter();
                    }
                }

                if(size < 0.05) {
                    boundaries_opt.setEnabled(false);
                    boundaries = false;
                    boundaries_opt.setSelected(false);
                }

                if(plane_type == USER_PLANE) {
                    user_plane = settings.getUserPlane();
                }

                if(color_choice == palette.length - 1) {
                    custom_palette = settings.getCustomPalette();
                    color_interpolation = settings.getColorInterpolation();
                    color_space = settings.getColorSpace();
                    reversed_palette = settings.getReveresedPalette();
                    temp_color_cycling_location = color_cycling_location;
                }

                rotation = settings.getRotation();
                rotation_center = settings.getRotationCenter();

                bailout_test_algorithm = settings.getBailoutTestAlgorithm();

                if(bailout_test_algorithm == BAILOUT_TEST_NNORM) {
                    n_norm = settings.getNNorm();
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
                in_coloring_algorithm = settings.getInColoringAlgorithm();
                smoothing = settings.getSmoothing();

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
                }

                for(int k = 0; k < fractal_functions.length; k++) {
                    //if(function != k) {
                    if(k != SIERPINSKI_GASKET && k != NEWTON3 && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                            && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                            && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                            && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                            && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                            && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3) {
                        fractal_functions[k].setEnabled(true);
                    }

                    if(k == SIERPINSKI_GASKET && !julia && !perturbation && !init_val) {
                        fractal_functions[k].setEnabled(true);
                    }
                    // }
                }

                burning_ship_opt.setEnabled(true);
                mandel_grass_opt.setEnabled(true);

                if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de || out_coloring_algorithm == ATOM_DOMAIN) {
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
                else if(out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || out_coloring_algorithm == ESCAPE_TIME_GRID || out_coloring_algorithm == BIOMORPH || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || out_coloring_algorithm == ITERATIONS_PLUS_RE || out_coloring_algorithm == ITERATIONS_PLUS_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2) {
                    rootFindingMethodsSetEnabled(false);
                }
                else {
                    if(!julia && !perturbation && !init_val) {
                        rootFindingMethodsSetEnabled(true);
                    }
                }

                out_coloring_modes[out_coloring_algorithm].setSelected(true);
                out_coloring_modes[out_coloring_algorithm].setEnabled(false);

                in_coloring_modes[in_coloring_algorithm].setSelected(true);
                in_coloring_modes[in_coloring_algorithm].setEnabled(false);

                bailout_tests[bailout_test_algorithm].setSelected(true);
                if(bailout_test_algorithm != BAILOUT_TEST_NNORM) {
                    bailout_tests[bailout_test_algorithm].setEnabled(false);
                }

                smoothing_opt.setSelected(smoothing);

                exterior_de_opt.setSelected(exterior_de);

                if(burning_ship || mandel_grass || init_val || perturbation) {
                    exterior_de_opt.setEnabled(false);
                }

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
                    case MANDELPOLY:
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
                        optionsEnableShortcut();
                        break;
                    case NEWTON3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTON4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTONGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTONGENERALIZED8:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTONSIN:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTONCOS:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case NEWTONPOLY:
                        coefficients = settings.getCoefficients();

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
                        optionsEnableShortcut2();
                        break;
                    case HALLEY3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEY4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYGENERALIZED8:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYSIN:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYCOS:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HALLEYPOLY:
                        coefficients = settings.getCoefficients();

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
                        optionsEnableShortcut2();
                        break;
                    case SCHRODER3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODER4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERGENERALIZED8:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERSIN:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERCOS:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SCHRODERPOLY:
                        coefficients = settings.getCoefficients();

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
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDER3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDER4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERGENERALIZED8:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERSIN:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERCOS:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case HOUSEHOLDERPOLY:
                        coefficients = settings.getCoefficients();

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
                        optionsEnableShortcut2();
                        break;
                    case SECANT3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SECANT4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SECANTGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SECANTGENERALIZED8:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SECANTCOS:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case SECANTPOLY:
                        coefficients = settings.getCoefficients();

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
                        optionsEnableShortcut2();
                        break;
                    case STEFFENSEN3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case STEFFENSEN4:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
                        optionsEnableShortcut2();
                        break;
                    case STEFFENSENGENERALIZED3:
                        fractal_functions[function].setSelected(true);
                        fractal_functions[function].setEnabled(false);
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
                        periodicity_checking_opt.setEnabled(false);
                        perturbation_opt.setEnabled(false);
                        init_val_opt.setEnabled(false);
                        break;
                    case 0:
                        if(!burning_ship && !mandel_grass && !init_val && !perturbation) {
                            exterior_de_opt.setEnabled(true);
                        }
                        if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !init_val && !perturbation) {
                            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                        }
                        if(out_coloring_algorithm != ATOM_DOMAIN && !init_val && !perturbation) {
                            out_coloring_modes[ATOM_DOMAIN].setEnabled(true);
                        }
                        if(out_coloring_algorithm != BIOMORPH) {
                            out_coloring_modes[BIOMORPH].setEnabled(true);
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

                        if(getSettingsVersion("" + settings.getClass()).equals("1.0.4.8")) {
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
                if(color_choice != palette.length - 1) {
                    palette[color_choice].setEnabled(false);
                }

                burning_ship_opt.setSelected(burning_ship);
                mandel_grass_opt.setSelected(mandel_grass);

                if(plane_type == USER_PLANE || plane_type == TWIRL_PLANE || plane_type == SHEAR_PLANE || plane_type == KALEIDOSCOPE_PLANE || plane_type == PINCH_PLANE || plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE || plane_type == FOLDIN_PLANE || plane_type == FOLDOUT_PLANE) {
                    planes[plane_type].setSelected(true);
                }
                else {
                    planes[plane_type].setSelected(true);
                    planes[plane_type].setEnabled(false);
                }


                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
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

        if(boundaries && !orbit) {
            drawBoundaries(graphics, false);
        }

        if(grid && !orbit) {
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

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }

        main_panel.repaint();

    }

    private void defaultFractalSettings() {

        setOptions(false);

        switch (function) {
            case MANDELBROTNTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case MANDELBROTWTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case MANDELPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
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
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTON4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEY3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEY4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODER3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODER4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDER3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDER4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANT3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANT4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSEN3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSEN4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSENGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NOVA:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case BARNSLEY1:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                bailout = 2;
                break;
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                bailout = 2;
                break;
            case BARNSLEY3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case MANDELBAR:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case SPIDER:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case MANOWAR:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case PHOENIX:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                bailout = 100;
                break;
            case EXP:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case LOG:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case SIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case COS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case TAN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case COT:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case SINH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case COSH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case TANH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case COTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case FORMULA30:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case FORMULA31:
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
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA3:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = 4;
                break;
            case FORMULA4:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = 4;
                break;
            case FORMULA5:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = 4;
                break;
            case FORMULA6:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA7:
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
            case FORMULA9:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = 4;
                break;
            case FORMULA10:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                bailout = 4;
                break;
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                bailout = 100;
                break;
            case FORMULA12:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = 8;
                break;
            case FORMULA13:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA14:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA15:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA16:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA17:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA18:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 8;
                break;
            case FORMULA19:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 4;
                break;
            case FORMULA20:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA21:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA22:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA23:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA24:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA25:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case FORMULA26:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
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
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                break;
            case FORMULA33:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                break;
            case FORMULA34:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                break;
            case FORMULA35:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                break;
            case FORMULA36:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                break;
            case FORMULA37:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 16;
                }
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
            case FORMULA39:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                break;
            case FORMULA40:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                break;
            case FORMULA41:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 8;
                }
                break;
            case FORMULA42:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                    bailout = 12;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                    bailout = 12;
                }
                break;
            case FORMULA43:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 12;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 12;
                }
                break;
            case FORMULA44:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 16;
                }
                break;
            case FORMULA45:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 16;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                    bailout = 16;
                }
                break;
            case USER_FORMULA:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                break;
            case USER_FORMULA_ITERATION_BASED:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                break;
            case USER_FORMULA_CONDITIONAL:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                    bailout = 2;
                }
                break;
            case FROTHY_BASIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            case SZEGEDI_BUTTERFLY1:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                bailout = 4;
                break;
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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                field_imaginary.setText("" + (-temp3));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText()) - rotation_center[1];  //Reveresed Axis
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

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            backup_orbit = null;

            whole_image_done = false;

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads(n);
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
                field_imaginary.setText("" + (-orbit_vals[1]));
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-orbit_vals[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis
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

            while(pixels_orbit != null && pixels_orbit.isAlive()) {
            }

            if(backup_orbit != null) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                if(julia) {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                    pixels_orbit.start();
                }
                else {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                    pixels_orbit.start();
                }
            }
            catch(Exception ex) {
            }


        }

    }

    private void goToJulia() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    field_imaginary.setText("" + (-temp3));
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
                        yJuliaCenter = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis
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
                    field_imaginary.setText("" + (-temp3));
                }

                JTextField field_size = new JTextField();
                field_size.setText("" + size);

                xJuliaCenter = xJuliaCenter == 0.0 ? 0.0 : xJuliaCenter;

                JTextField real_seed = new JTextField(16);
                real_seed.setEditable(false);
                JTextField imag_seed = new JTextField(16);
                imag_seed.setEditable(false);

                if(-yJuliaCenter > 0) {
                    real_seed.setText("" + xJuliaCenter);
                    imag_seed.setText("" + (-yJuliaCenter));
                }
                else {
                    if(yJuliaCenter == 0) {
                        real_seed.setText("" + xJuliaCenter);
                        imag_seed.setText("" + 0.0);
                    }
                    else {
                        real_seed.setText("" + xJuliaCenter);
                        imag_seed.setText("" + (-yJuliaCenter));
                    }
                }

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
                        tempImaginary = -Double.parseDouble(field_imaginary.getText()) - rotation_center[1];  //Reveresed Axis
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

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                field_imaginary.setText("" + (-orbit_vals[1]));
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-orbit_vals[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis
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

            while(pixels_orbit != null && pixels_orbit.isAlive()) {
            }

            if(backup_orbit != null) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                if(julia) {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                    pixels_orbit.start();
                }
                else {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, seq_points, x_real, y_imag, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                    pixels_orbit.start();
                }
            }
            catch(Exception ex) {
            }
        }

    }

    private void setFractalColor() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 630;
        int color_window_height = 400;
        fract_color_frame = new JFrame("Fractal Color");
        fract_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        fract_color_frame.setLayout(new FlowLayout());
        fract_color_frame.setSize(color_window_width, color_window_height);
        fract_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
        fract_color_frame.setResizable(false);
        color_chooser = new JColorChooser();
        color_chooser.setColor(fractal_color);
        color_chooser.setPreferredSize(new Dimension(600, 320));

        fract_color_frame.add(color_chooser);


        fract_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                fract_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                fractal_color = color_chooser.getColor();

                //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
                if(color_choice < palette.length - 1) {
                    Color[] c = CustomPalette.getPalette(editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location);

                    boolean flag = true;

                    while(flag) {
                        flag = false;
                        for(int j = 0; j < c.length; j++) {
                            if(c[j].getRGB() == fractal_color.getRGB()) {
                                if(fractal_color.getBlue() == 255) {
                                    fractal_color = new Color(fractal_color.getRGB() - 1);
                                }
                                else {
                                    fractal_color = new Color(fractal_color.getRGB() + 1);
                                }
                                flag = true;
                                break;
                            }
                        }
                    }

                }
                else {
                    Color[] c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location);

                    boolean flag = true;

                    while(flag) {
                        flag = false;
                        for(int j = 0; j < c.length; j++) {
                            if(c[j].getRGB() == fractal_color.getRGB()) {
                                if(fractal_color.getBlue() == 255) {
                                    fractal_color = new Color(fractal_color.getRGB() - 1);
                                }
                                else {
                                    fractal_color = new Color(fractal_color.getRGB() + 1);
                                }
                                flag = true;
                                break;
                            }
                        }
                    }

                }

                setOptions(false);

                progress.setValue(0);

                setEnabled(true);
                fract_color_frame.dispose();

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                fract_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JPanel buttons = new JPanel();

        buttons.add(ok);
        buttons.add(close);

        fract_color_frame.add(buttons);

        fract_color_frame.setVisible(true);
        main_panel.repaint();

    }

    private void setIterations() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be lower than 100001.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            max_iterations = temp;

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new maximum iterations number is " + max_iterations + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

            setOptions(false);

            progress.setValue(0);

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + height_ratio + " as height ratio.\nEnter the new height ratio.", "Height Ratio", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if(temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Height ratio number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            height_ratio = temp;

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new height ratio number is " + height_ratio + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

            setOptions(false);

            progress.setValue(0);

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    JOptionPane.showMessageDialog(scroll_pane, "Zooming factor must be lower than 33.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            zoom_factor = temp;

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new zooming factor is " + zoom_factor + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new threads number is " + n * n + " in a " + n + "x" + n + " 2D grid.", "Info", JOptionPane.INFORMATION_MESSAGE);
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

    private void setPalette(int temp) {

        palette[color_choice].setSelected(false);
        palette[color_choice].setEnabled(true);

        color_choice = temp;
        if(color_choice != palette.length - 1) {
            palette[color_choice].setEnabled(false);
            color_cycling_location = 0;
        }
        else {
            palette[color_choice].setSelected(true);
        }


        //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
        if(color_choice < palette.length - 1) {
            Color[] c = CustomPalette.getPalette(editor_default_palettes[color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location);

            boolean flag = true;

            while(flag) {
                flag = false;
                for(int j = 0; j < c.length; j++) {
                    if(c[j].getRGB() == fractal_color.getRGB()) {
                        if(fractal_color.getBlue() == 255) {
                            fractal_color = new Color(fractal_color.getRGB() - 1);
                        }
                        else {
                            fractal_color = new Color(fractal_color.getRGB() + 1);
                        }
                        flag = true;
                        break;
                    }
                }
            }

        }
        else {
            Color[] c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location);

            boolean flag = true;

            while(flag) {
                flag = false;
                for(int j = 0; j < c.length; j++) {
                    if(c[j].getRGB() == fractal_color.getRGB()) {
                        if(fractal_color.getBlue() == 255) {
                            fractal_color = new Color(fractal_color.getRGB() - 1);
                        }
                        else {
                            fractal_color = new Color(fractal_color.getRGB() + 1);
                        }
                        flag = true;
                        break;
                    }
                }
            }

        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setAntiAliasing() {

        if(!anti_aliasing_opt.isSelected()) {
            filters[ANTIALIASING] = false;

            setOptions(false);

            progress.setValue(0);

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setEdgeDetection() {

        if(!edges_opt.isSelected()) {
            filters[EDGE_DETECTION] = false;
        }
        else {
            filters[EDGE_DETECTION] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setHistogramEqualization() {

        if(!histogram_eq_opt.isSelected()) {
            filters[HISTOGRAM_EQUALIZATION] = false;
        }
        else {
            filters[HISTOGRAM_EQUALIZATION] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setEmboss() {

        if(!emboss_opt.isSelected()) {
            filters[EMBOSS] = false;
        }
        else {
            filters[EMBOSS] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setInvertColors() {

        if(!invert_colors_opt.isSelected()) {
            filters[INVERT_COLORS] = false;
        }
        else {
            filters[INVERT_COLORS] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setBlurring() {

        if(!blurring_opt.isSelected()) {
            filters[BLURRING] = false;
        }
        else {
            filters[BLURRING] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setSharpness() {

        if(!sharpness_opt.isSelected()) {
            filters[SHARPNESS] = false;
        }
        else {
            filters[SHARPNESS] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setMaskColorChannel() {

        if(!mask_color_channel_opt.isSelected()) {
            filters[COLOR_CHANNEL_MASKING] = false;
        }
        else {
            filters[COLOR_CHANNEL_MASKING] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setFadeOut() {

        if(!fade_out_opt.isSelected()) {
            filters[FADE_OUT] = false;
        }
        else {
            filters[FADE_OUT] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setColorChannelSwapping() {

        if(!color_channel_swapping_opt.isSelected()) {
            filters[COLOR_CHANNEL_SWAPPING] = false;
        }
        else {
            filters[COLOR_CHANNEL_SWAPPING] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setContrastBrightness() {

        if(!contrast_brightness_opt.isSelected()) {
            filters[CONTRAST_BRIGHTNESS] = false;
        }
        else {
            filters[CONTRAST_BRIGHTNESS] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setGrayscale() {

        if(!grayscale_opt.isSelected()) {
            filters[GRAYSCALE] = false;
        }
        else {
            filters[GRAYSCALE] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setColorTemperature() {

        if(!color_temperature_opt.isSelected()) {
            filters[COLOR_TEMPERATURE] = false;
        }
        else {
            filters[COLOR_TEMPERATURE] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setColorChannelMixing() {

        if(!color_channel_mixing_opt.isSelected()) {
            filters[COLOR_CHANNEL_MIXING] = false;
        }
        else {
            filters[COLOR_CHANNEL_MIXING] = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                createThreadsRotate3DModel();
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
                createThreadsRotate3DModel();
            }
            else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }

    }

    private void setGrid() {

        if(!grid_opt.isSelected()) {
            grid = false;
            old_grid = grid;
            if(!zoom_window && !julia_map && !orbit && image_size < 2001 && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }
            main_panel.repaint();
        }
        else {
            grid = true;
            old_grid = grid;
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            main_panel.repaint();
        }

    }

    private void setBoundaries() {

        if(!boundaries_opt.isSelected()) {
            boundaries = false;
            old_boundaries = boundaries;
            if(!zoom_window && !julia_map && !orbit && image_size < 2001 && !grid) {
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
            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true))) {
                julia_opt.setEnabled(true);
                julia_button.setEnabled(true);
                if(!julia) {
                    julia_map_opt.setEnabled(true);
                }
            }

            if(image_size < 2001 && !grid && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }

            if(image_size < 4001) {
                orbit_opt.setEnabled(true);
                orbit_button.setEnabled(true);
            }

            color_cycling_opt.setEnabled(true);
            color_cycling_button.setEnabled(true);
            main_panel.repaint();
        }
        else {
            zoom_window = true;
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            orbit_opt.setEnabled(false);
            orbit_button.setEnabled(false);
            color_cycling_opt.setEnabled(false);
            color_cycling_button.setEnabled(false);
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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            main_panel.repaint();
        }

    }

    private void setSettingsFractal(MouseEvent e) {

        if(!threadsAvailable() || julia_map || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON2_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
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
        yCenter = yCenter - size_2_y + temp_size_image_size_y * main_panel.getMousePosition().getY();



        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                size = size / zoom_factor;
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                size = size * zoom_factor;
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


        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void setSettingsJulia(MouseEvent e) {

        if(!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON2_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
            return;
        }

        if(main_panel.getMousePosition().getX() < 0 || main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 || main_panel.getMousePosition().getY() > image_size) {
            return;
        }

        setOptions(false);


        if(first_seed) {
            if(e.getModifiers() == InputEvent.BUTTON1_MASK) {
                double size_2_x = size * 0.5;
                double size_2_y = (size * height_ratio) * 0.5;
                double temp_size_image_size_x = size / image_size;
                double temp_size_image_size_y = (size * height_ratio) / image_size;

                double temp = xCenter - size_2_x + temp_size_image_size_x * main_panel.getMousePosition().getX() - rotation_center[0];
                double temp2 = yCenter - size_2_y + temp_size_image_size_y * main_panel.getMousePosition().getY() - rotation_center[1];

                xJuliaCenter = temp * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
                yJuliaCenter = temp * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];

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
            yCenter = yCenter - size_2_y + temp_size_image_size_y * main_panel.getMousePosition().getY();

            switch (e.getModifiers()) {
                case InputEvent.BUTTON1_MASK:
                    size /= zoom_factor;
                    break;

                case InputEvent.BUTTON3_MASK:
                    size *= zoom_factor;
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


            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            backup_orbit = null;

            whole_image_done = false;

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }

    }

    private void setJuliaOption() {

        if(!julia_opt.isSelected()) {
            julia = false;
            julia_button.setSelected(false);
            fast_julia_image = null;
            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                rootFindingMethodsSetEnabled(true);
            }
            if(out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de) {
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
            fast_julia_image = null;
            orbit_opt.setEnabled(false);
            orbit_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
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
            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && !init_val && !perturbation && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true))) {
                julia_opt.setEnabled(true);
                julia_button.setEnabled(true);
                if(!julia) {
                    julia_map_opt.setEnabled(true);
                }
            }

            if(image_size < 2001 && !grid && !boundaries) {
                d3_opt.setEnabled(true);
                d3_button.setEnabled(true);
            }
            zoom_window_opt.setEnabled(true);
            color_cycling_opt.setEnabled(true);
            color_cycling_button.setEnabled(true);
            if(backup_orbit != null) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            backup_orbit = null;
            last_used = null;
        }
        else {
            orbit = true;
            orbit_button.setSelected(true);
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            d3_opt.setEnabled(false);
            d3_button.setEnabled(false);
            color_cycling_opt.setEnabled(false);
            color_cycling_button.setEnabled(false);
            zoom_window_opt.setEnabled(false);
            backup_orbit = null;
            last_used = null;
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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        Arrays.fill(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size, Color.BLACK.getRGB());

        backup_orbit = null;

        whole_image_done = false;

        createThreadsRotate3DModel();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void setOrbit(MouseEvent e) {

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
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            else {
                backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }

            try {
                if(julia) {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 400 ? 400 : max_iterations, x1, y1, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                    pixels_orbit.start();
                }
                else {
                    pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 400 ? 400 : max_iterations, x1, y1, image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, mandel_grass, mandel_grass_vals, grid, boundaries, function, z_exponent, z_exponent_complex, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                    pixels_orbit.start();
                }
            }
            catch(Exception ex) {
            }
        }
        else {
            if(backup_orbit != null) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();
        }


        try {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_xcenter_size = xCenter - size_2_x;
            double temp_ycenter_size = yCenter - size_2_y;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;

            double temp2 = temp_xcenter_size + temp_size_image_size_x * main_panel.getMousePosition().getX() - rotation_center[0];
            double temp = temp_ycenter_size + temp_size_image_size_y * main_panel.getMousePosition().getY() - rotation_center[1];

            double temp3 = temp2 * rotation_vals[0] - temp * rotation_vals[1] + rotation_center[0];

            temp3 = temp3 == 0 ? 0.0 : temp3;

            real.setText("" + temp3);

            temp3 = temp2 * rotation_vals[1] + temp * rotation_vals[0] + rotation_center[1];

            temp3 = temp3 == 0 ? 0.0 : -temp3;

            imaginary.setText("" + temp3);
        }
        catch(NullPointerException ex) {
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
    
    
    last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
    System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
    
    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
    
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


        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

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


        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void setOptions(Boolean option) {

        if(!julia_map && !d3) {
            starting_position.setEnabled(option);
            starting_position_button.setEnabled(option);
        }
        save_settings.setEnabled(option);
        load_settings.setEnabled(option);
        save_image.setEnabled(option);

        if((!julia || !first_seed) && !julia_map && !d3) {
            go_to.setEnabled(option);
        }

        fractal_functions_menu.setEnabled(option);
        colors_menu.setEnabled(option);
        iterations_menu.setEnabled(option);
        size_of_image.setEnabled(option);

        height_ratio_number.setEnabled(option);

        custom_palette_button.setEnabled(option);
        random_palette_button.setEnabled(option);
        iterations_button.setEnabled(option);
        rotation_button.setEnabled(option);
        save_image_button.setEnabled(option);


        if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
            bailout_number.setEnabled(option);
            bailout_test_menu.setEnabled(option);
        }

        optimizations_menu.setEnabled(option);

        if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && in_coloring_algorithm == MAXIMUM_ITERATIONS && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
            periodicity_checking_opt.setEnabled(option);
        }


        if(((!julia && !orbit) || (!first_seed && !orbit)) && !zoom_window && !julia_map && !d3 && !perturbation && !init_val && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true))) {
            julia_opt.setEnabled(option);
            julia_button.setEnabled(option);
        }

        if(!zoom_window && !orbit && !color_cycling && !d3) {
            color_cycling_opt.setEnabled(option);
            color_cycling_button.setEnabled(option);
        }

        anti_aliasing_opt.setEnabled(option);
        edges_opt.setEnabled(option);
        emboss_opt.setEnabled(option);
        sharpness_opt.setEnabled(option);
        invert_colors_opt.setEnabled(option);
        blurring_opt.setEnabled(option);
        histogram_eq_opt.setEnabled(option);
        mask_color_channel_opt.setEnabled(option);
        fade_out_opt.setEnabled(option);
        color_channel_swapping_opt.setEnabled(option);
        contrast_brightness_opt.setEnabled(option);
        grayscale_opt.setEnabled(option);
        color_channel_mixing_opt.setEnabled(option);
        color_temperature_opt.setEnabled(option);

        filters_options.setEnabled(option);

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3) {
            grid_opt.setEnabled(option);
        }

        if((rotation == 0 || rotation == 360 || rotation == -360) && !d3 && size >= 0.05) {
            boundaries_opt.setEnabled(option);
        }

        if(!d3 && !orbit && !julia_map) {
            zoom_window_opt.setEnabled(option);
        }

        if(!julia_map && !d3) {
            zoom_in.setEnabled(option);
            zoom_in_button.setEnabled(option);
        }
        if(!julia_map && !d3) {
            zoom_out.setEnabled(option);
            zoom_out_button.setEnabled(option);
        }
        if(!julia_map && !d3 && image_size < 4001 && !zoom_window) {
            orbit_opt.setEnabled(option);
            orbit_button.setEnabled(option);
        }
        planes_menu.setEnabled(option);

        if(!zoom_window && !julia && !perturbation && !init_val && !orbit && !d3 && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true))) {
            julia_map_opt.setEnabled(option);
        }

        if(!zoom_window && !orbit && !julia_map && !grid && image_size < 2001 && !boundaries) {
            d3_opt.setEnabled(option);
            d3_button.setEnabled(option);
        }

        rotation_menu.setEnabled(option);

        if(!exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != DISTANCE_ESTIMATOR && !julia && !julia_map && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) && (function != USER_FORMULA || (function == USER_FORMULA && user_formula_c == true)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && user_formula_c == true)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && user_formula_c == true))) {
            perturbation_opt.setEnabled(option);
            init_val_opt.setEnabled(option);
        }

    }

    private void setLine() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = true;
        dot.setSelected(false);
        dot.setEnabled(true);
        line.setSelected(true);
        line.setEnabled(false);

    }

    private void setDot() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        orbit_style = false;
        line.setSelected(false);
        line.setEnabled(true);
        dot.setSelected(true);
        dot.setEnabled(false);

    }

    private void setZoomWindowDashedLine() {

        zoom_window_style = true;
        zoom_window_line.setSelected(false);
        zoom_window_line.setEnabled(true);
        zoom_window_dashed_line.setSelected(true);
        zoom_window_dashed_line.setEnabled(false);

    }

    private void setZoomWindowLine() {

        zoom_window_style = false;
        zoom_window_dashed_line.setSelected(false);
        zoom_window_dashed_line.setEnabled(true);
        zoom_window_line.setSelected(true);
        zoom_window_line.setEnabled(false);

    }

    private void setOrbitColor() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 630;
        int color_window_height = 400;
        orbit_color_frame = new JFrame("Orbit Color");
        orbit_color_frame.setLayout(new FlowLayout());
        orbit_color_frame.setSize(color_window_width, color_window_height);
        orbit_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        orbit_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
        orbit_color_frame.setResizable(false);
        color_chooser = new JColorChooser();

        color_chooser.setColor(orbit_color);
        color_chooser.setPreferredSize(new Dimension(600, 320));

        orbit_color_frame.add(color_chooser);


        orbit_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                orbit_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                orbit_color = color_chooser.getColor();

                setEnabled(true);
                orbit_color_frame.dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                orbit_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JPanel buttons = new JPanel();

        buttons.add(ok);
        buttons.add(close);

        orbit_color_frame.add(buttons);

        orbit_color_frame.setVisible(true);
        main_panel.repaint();

    }

    private void setGridColor() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 630;
        int color_window_height = 400;
        grid_color_frame = new JFrame("Grid Color");
        grid_color_frame.setLayout(new FlowLayout());
        grid_color_frame.setSize(color_window_width, color_window_height);
        grid_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        grid_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
        grid_color_frame.setResizable(false);
        color_chooser = new JColorChooser();

        color_chooser.setColor(grid_color);
        color_chooser.setPreferredSize(new Dimension(600, 320));

        grid_color_frame.add(color_chooser);

        grid_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                grid_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                grid_color = color_chooser.getColor();

                setEnabled(true);
                grid_color_frame.dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                grid_color_frame.dispose();
                main_panel.repaint();

            }
        });


        JPanel buttons = new JPanel();

        buttons.add(ok);
        buttons.add(close);

        grid_color_frame.add(buttons);

        grid_color_frame.setVisible(true);
        main_panel.repaint();

    }

    private void setZoomWindowColor() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 630;
        int color_window_height = 400;
        zoom_window_color_frame = new JFrame("Zoom Window Color");
        zoom_window_color_frame.setLayout(new FlowLayout());
        zoom_window_color_frame.setSize(color_window_width, color_window_height);
        zoom_window_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        zoom_window_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
        zoom_window_color_frame.setResizable(false);
        color_chooser = new JColorChooser();

        color_chooser.setColor(zoom_window_color);
        color_chooser.setPreferredSize(new Dimension(600, 320));

        zoom_window_color_frame.add(color_chooser);

        zoom_window_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                zoom_window_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                zoom_window_color = color_chooser.getColor();

                setEnabled(true);
                zoom_window_color_frame.dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                zoom_window_color_frame.dispose();
                main_panel.repaint();

            }
        });


        JPanel buttons = new JPanel();

        buttons.add(ok);
        buttons.add(close);

        zoom_window_color_frame.add(buttons);

        zoom_window_color_frame.setVisible(true);
        main_panel.repaint();

    }

    private void setBoundariesColor() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int color_window_width = 630;
        int color_window_height = 400;
        boundaries_color_frame = new JFrame("Boundaries Color");
        boundaries_color_frame.setLayout(new FlowLayout());
        boundaries_color_frame.setSize(color_window_width, color_window_height);
        boundaries_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
        boundaries_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
        boundaries_color_frame.setResizable(false);
        color_chooser = new JColorChooser();

        color_chooser.setColor(boundaries_color);
        color_chooser.setPreferredSize(new Dimension(600, 320));

        boundaries_color_frame.add(color_chooser);

        boundaries_color_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                boundaries_color_frame.dispose();
                main_panel.repaint();

            }
        });

        JButton ok = new JButton("Ok");

        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                boundaries_color = color_chooser.getColor();

                setEnabled(true);
                boundaries_color_frame.dispose();

            }
        });

        JButton close = new JButton("Cancel");
        close.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                boundaries_color_frame.dispose();
                main_panel.repaint();

            }
        });


        JPanel buttons = new JPanel();

        buttons.add(ok);
        buttons.add(close);

        boundaries_color_frame.add(buttons);

        boundaries_color_frame.setVisible(true);
        main_panel.repaint();
    }

    private void setFunction(int temp) {

        fractal_functions[function].setSelected(false);
        fractal_functions[function].setEnabled(true);

        int temp2 = function;
        function = temp;
        int l;

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
            case MANDELPOLY:
                JLabel polynomial = new JLabel();

                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel mandel1 = new JPanel();
                JLabel mandel_label_1 = new JLabel();
                mandel_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField mandel_coef_1 = new JTextField(30);
                mandel_coef_1.addAncestorListener(new RequestFocusListener());
                mandel_coef_1.setText("" + coefficients[0]);
                mandel1.setLayout(new FlowLayout());
                mandel1.add(mandel_label_1);
                mandel1.add(new JLabel(""));
                mandel1.add(mandel_coef_1);

                JPanel mandel2 = new JPanel();
                JLabel mandel_label_2 = new JLabel();
                mandel_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField mandel_coef_2 = new JTextField(30);
                mandel_coef_2.setText("" + coefficients[1]);
                mandel2.setLayout(new FlowLayout());
                mandel2.add(mandel_label_2);
                mandel2.add(new JLabel(" "));
                mandel2.add(mandel_coef_2);

                JPanel mandel3 = new JPanel();
                JLabel mandel_label_3 = new JLabel();
                mandel_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField mandel_coef_3 = new JTextField(30);
                mandel_coef_3.setText("" + coefficients[2]);
                mandel3.setLayout(new FlowLayout());
                mandel3.add(mandel_label_3);
                mandel3.add(new JLabel(" "));
                mandel3.add(mandel_coef_3);

                JPanel mandel4 = new JPanel();
                JLabel mandel_label_4 = new JLabel();
                mandel_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField mandel_coef_4 = new JTextField(30);
                mandel_coef_4.setText("" + coefficients[3]);
                mandel4.setLayout(new FlowLayout());
                mandel4.add(mandel_label_4);
                mandel4.add(new JLabel(" "));
                mandel4.add(mandel_coef_4);

                JPanel mandel5 = new JPanel();
                JLabel mandel_label_5 = new JLabel();
                mandel_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField mandel_coef_5 = new JTextField(30);
                mandel_coef_5.setText("" + coefficients[4]);
                mandel5.setLayout(new FlowLayout());
                mandel5.add(mandel_label_5);
                mandel5.add(new JLabel(" "));
                mandel5.add(mandel_coef_5);

                JPanel mandel6 = new JPanel();
                JLabel mandel_label_6 = new JLabel();
                mandel_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField mandel_coef_6 = new JTextField(30);
                mandel_coef_6.setText("" + coefficients[5]);
                mandel6.setLayout(new FlowLayout());
                mandel6.add(mandel_label_6);
                mandel6.add(new JLabel(" "));
                mandel6.add(mandel_coef_6);

                JPanel mandel7 = new JPanel();
                JLabel mandel_label_7 = new JLabel();
                mandel_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField mandel_coef_7 = new JTextField(30);
                mandel_coef_7.setText("" + coefficients[6]);
                mandel7.setLayout(new FlowLayout());
                mandel7.add(mandel_label_7);
                mandel7.add(new JLabel(" "));
                mandel7.add(mandel_coef_7);

                JPanel mandel8 = new JPanel();
                JLabel mandel_label_8 = new JLabel();
                mandel_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField mandel_coef_8 = new JTextField(30);
                mandel_coef_8.setText("" + coefficients[7]);
                mandel8.setLayout(new FlowLayout());
                mandel8.add(mandel_label_8);
                mandel8.add(new JLabel(" "));
                mandel8.add(mandel_coef_8);

                JPanel mandel9 = new JPanel();
                JLabel mandel_label_9 = new JLabel();
                mandel_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField mandel_coef_9 = new JTextField(30);
                mandel_coef_9.setText("" + coefficients[8]);
                mandel9.setLayout(new FlowLayout());
                mandel9.add(mandel_label_9);
                mandel9.add(new JLabel(" "));
                mandel9.add(mandel_coef_9);

                JPanel mandel10 = new JPanel();
                JLabel mandel_label_10 = new JLabel();
                mandel_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField mandel_coef_10 = new JTextField(30);
                mandel_coef_10.setText("" + coefficients[9]);
                mandel10.setLayout(new FlowLayout());
                mandel10.add(mandel_label_10);
                mandel10.add(new JLabel(" "));
                mandel10.add(mandel_coef_10);

                JPanel mandel11 = new JPanel();
                JLabel mandel_label_11 = new JLabel();
                mandel_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField mandel_coef_11 = new JTextField(30);
                mandel_coef_11.setText("" + coefficients[10]);
                mandel11.setLayout(new FlowLayout());
                mandel11.add(mandel_label_11);
                mandel11.add(new JLabel(" "));
                mandel11.add(mandel_coef_11);

                Object[] mandel_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    mandel1,
                    mandel2,
                    mandel3,
                    mandel4,
                    mandel5,
                    mandel6,
                    mandel7,
                    mandel8,
                    mandel9,
                    mandel10,
                    mandel11,};


                int mandel_poly_res = JOptionPane.showConfirmDialog(scroll_pane, mandel_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                double[] temp_coef = new double[11];

                if(mandel_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(mandel_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(mandel_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(mandel_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(mandel_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(mandel_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(mandel_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(mandel_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(mandel_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(mandel_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(mandel_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(mandel_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                fractal_functions[function].setSelected(true);
                optionsEnableShortcut();
                break;
            case NEWTON3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTON4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTONGENERALIZED3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTONGENERALIZED8:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTONSIN:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTONCOS:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case NEWTONPOLY:
                polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel newton1 = new JPanel();
                JLabel newton_label_1 = new JLabel();
                newton_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField newton_coef_1 = new JTextField(30);
                newton_coef_1.addAncestorListener(new RequestFocusListener());
                newton_coef_1.setText("" + coefficients[0]);
                newton1.setLayout(new FlowLayout());
                newton1.add(newton_label_1);
                newton1.add(new JLabel(""));
                newton1.add(newton_coef_1);

                JPanel newton2 = new JPanel();
                JLabel newton_label_2 = new JLabel();
                newton_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField newton_coef_2 = new JTextField(30);
                newton_coef_2.setText("" + coefficients[1]);
                newton2.setLayout(new FlowLayout());
                newton2.add(newton_label_2);
                newton2.add(new JLabel(" "));
                newton2.add(newton_coef_2);

                JPanel newton3 = new JPanel();
                JLabel newton_label_3 = new JLabel();
                newton_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField newton_coef_3 = new JTextField(30);
                newton_coef_3.setText("" + coefficients[2]);
                newton3.setLayout(new FlowLayout());
                newton3.add(newton_label_3);
                newton3.add(new JLabel(" "));
                newton3.add(newton_coef_3);

                JPanel newton4 = new JPanel();
                JLabel newton_label_4 = new JLabel();
                newton_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField newton_coef_4 = new JTextField(30);
                newton_coef_4.setText("" + coefficients[3]);
                newton4.setLayout(new FlowLayout());
                newton4.add(newton_label_4);
                newton4.add(new JLabel(" "));
                newton4.add(newton_coef_4);

                JPanel newton5 = new JPanel();
                JLabel newton_label_5 = new JLabel();
                newton_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField newton_coef_5 = new JTextField(30);
                newton_coef_5.setText("" + coefficients[4]);
                newton5.setLayout(new FlowLayout());
                newton5.add(newton_label_5);
                newton5.add(new JLabel(" "));
                newton5.add(newton_coef_5);

                JPanel newton6 = new JPanel();
                JLabel newton_label_6 = new JLabel();
                newton_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField newton_coef_6 = new JTextField(30);
                newton_coef_6.setText("" + coefficients[5]);
                newton6.setLayout(new FlowLayout());
                newton6.add(newton_label_6);
                newton6.add(new JLabel(" "));
                newton6.add(newton_coef_6);

                JPanel newton7 = new JPanel();
                JLabel newton_label_7 = new JLabel();
                newton_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField newton_coef_7 = new JTextField(30);
                newton_coef_7.setText("" + coefficients[6]);
                newton7.setLayout(new FlowLayout());
                newton7.add(newton_label_7);
                newton7.add(new JLabel(" "));
                newton7.add(newton_coef_7);

                JPanel newton8 = new JPanel();
                JLabel newton_label_8 = new JLabel();
                newton_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField newton_coef_8 = new JTextField(30);
                newton_coef_8.setText("" + coefficients[7]);
                newton8.setLayout(new FlowLayout());
                newton8.add(newton_label_8);
                newton8.add(new JLabel(" "));
                newton8.add(newton_coef_8);

                JPanel newton9 = new JPanel();
                JLabel newton_label_9 = new JLabel();
                newton_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField newton_coef_9 = new JTextField(30);
                newton_coef_9.setText("" + coefficients[8]);
                newton9.setLayout(new FlowLayout());
                newton9.add(newton_label_9);
                newton9.add(new JLabel(" "));
                newton9.add(newton_coef_9);

                JPanel newton10 = new JPanel();
                JLabel newton_label_10 = new JLabel();
                newton_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField newton_coef_10 = new JTextField(30);
                newton_coef_10.setText("" + coefficients[9]);
                newton10.setLayout(new FlowLayout());
                newton10.add(newton_label_10);
                newton10.add(new JLabel(" "));
                newton10.add(newton_coef_10);

                JPanel newton11 = new JPanel();
                JLabel newton_label_11 = new JLabel();
                newton_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField newton_coef_11 = new JTextField(30);
                newton_coef_11.setText("" + coefficients[10]);
                newton11.setLayout(new FlowLayout());
                newton11.add(newton_label_11);
                newton11.add(new JLabel(" "));
                newton11.add(newton_coef_11);

                Object[] newton_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    newton1,
                    newton2,
                    newton3,
                    newton4,
                    newton5,
                    newton6,
                    newton7,
                    newton8,
                    newton9,
                    newton10,
                    newton11,};


                int newton_poly_res = JOptionPane.showConfirmDialog(scroll_pane, newton_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                temp_coef = new double[11];

                if(newton_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(newton_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(newton_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(newton_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(newton_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(newton_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(newton_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(newton_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(newton_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(newton_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(newton_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(newton_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                non_zero = false;
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                optionsEnableShortcut2();
                fractal_functions[function].setSelected(true);
                break;
            case HALLEY3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEY4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEYGENERALIZED3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEYGENERALIZED8:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEYSIN:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEYCOS:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HALLEYPOLY:
                polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel halley1 = new JPanel();
                JLabel halley_label_1 = new JLabel();
                halley_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField halley_coef_1 = new JTextField(30);
                halley_coef_1.addAncestorListener(new RequestFocusListener());
                halley_coef_1.setText("" + coefficients[0]);
                halley1.setLayout(new FlowLayout());
                halley1.add(halley_label_1);
                halley1.add(new JLabel(""));
                halley1.add(halley_coef_1);

                JPanel halley2 = new JPanel();
                JLabel halley_label_2 = new JLabel();
                halley_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField halley_coef_2 = new JTextField(30);
                halley_coef_2.setText("" + coefficients[1]);
                halley2.setLayout(new FlowLayout());
                halley2.add(halley_label_2);
                halley2.add(new JLabel(" "));
                halley2.add(halley_coef_2);

                JPanel halley3 = new JPanel();
                JLabel halley_label_3 = new JLabel();
                halley_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField halley_coef_3 = new JTextField(30);
                halley_coef_3.setText("" + coefficients[2]);
                halley3.setLayout(new FlowLayout());
                halley3.add(halley_label_3);
                halley3.add(new JLabel(" "));
                halley3.add(halley_coef_3);

                JPanel halley4 = new JPanel();
                JLabel halley_label_4 = new JLabel();
                halley_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField halley_coef_4 = new JTextField(30);
                halley_coef_4.setText("" + coefficients[3]);
                halley4.setLayout(new FlowLayout());
                halley4.add(halley_label_4);
                halley4.add(new JLabel(" "));
                halley4.add(halley_coef_4);

                JPanel halley5 = new JPanel();
                JLabel halley_label_5 = new JLabel();
                halley_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField halley_coef_5 = new JTextField(30);
                halley_coef_5.setText("" + coefficients[4]);
                halley5.setLayout(new FlowLayout());
                halley5.add(halley_label_5);
                halley5.add(new JLabel(" "));
                halley5.add(halley_coef_5);

                JPanel halley6 = new JPanel();
                JLabel halley_label_6 = new JLabel();
                halley_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField halley_coef_6 = new JTextField(30);
                halley_coef_6.setText("" + coefficients[5]);
                halley6.setLayout(new FlowLayout());
                halley6.add(halley_label_6);
                halley6.add(new JLabel(" "));
                halley6.add(halley_coef_6);

                JPanel halley7 = new JPanel();
                JLabel halley_label_7 = new JLabel();
                halley_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField halley_coef_7 = new JTextField(30);
                halley_coef_7.setText("" + coefficients[6]);
                halley7.setLayout(new FlowLayout());
                halley7.add(halley_label_7);
                halley7.add(new JLabel(" "));
                halley7.add(halley_coef_7);

                JPanel halley8 = new JPanel();
                JLabel halley_label_8 = new JLabel();
                halley_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField halley_coef_8 = new JTextField(30);
                halley_coef_8.setText("" + coefficients[7]);
                halley8.setLayout(new FlowLayout());
                halley8.add(halley_label_8);
                halley8.add(new JLabel(" "));
                halley8.add(halley_coef_8);

                JPanel halley9 = new JPanel();
                JLabel halley_label_9 = new JLabel();
                halley_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField halley_coef_9 = new JTextField(30);
                halley_coef_9.setText("" + coefficients[8]);
                halley9.setLayout(new FlowLayout());
                halley9.add(halley_label_9);
                halley9.add(new JLabel(" "));
                halley9.add(halley_coef_9);

                JPanel halley10 = new JPanel();
                JLabel halley_label_10 = new JLabel();
                halley_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField halley_coef_10 = new JTextField(30);
                halley_coef_10.setText("" + coefficients[9]);
                halley10.setLayout(new FlowLayout());
                halley10.add(halley_label_10);
                halley10.add(new JLabel(" "));
                halley10.add(halley_coef_10);

                JPanel halley11 = new JPanel();
                JLabel halley_label_11 = new JLabel();
                halley_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField halley_coef_11 = new JTextField(30);
                halley_coef_11.setText("" + coefficients[10]);
                halley11.setLayout(new FlowLayout());
                halley11.add(halley_label_11);
                halley11.add(new JLabel(" "));
                halley11.add(halley_coef_11);

                Object[] halley_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    halley1,
                    halley2,
                    halley3,
                    halley4,
                    halley5,
                    halley6,
                    halley7,
                    halley8,
                    halley9,
                    halley10,
                    halley11,};


                int halley_poly_res = JOptionPane.showConfirmDialog(scroll_pane, halley_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                temp_coef = new double[11];

                if(halley_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(halley_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(halley_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(halley_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(halley_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(halley_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(halley_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(halley_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(halley_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(halley_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(halley_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(halley_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                non_zero = false;
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                optionsEnableShortcut2();
                fractal_functions[function].setSelected(true);
                break;
            case SCHRODER3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODER4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODERGENERALIZED3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODERGENERALIZED8:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODERSIN:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODERCOS:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SCHRODERPOLY:
                polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel schroder1 = new JPanel();
                JLabel schroder_label_1 = new JLabel();
                schroder_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField schroder_coef_1 = new JTextField(30);
                schroder_coef_1.addAncestorListener(new RequestFocusListener());
                schroder_coef_1.setText("" + coefficients[0]);
                schroder1.setLayout(new FlowLayout());
                schroder1.add(schroder_label_1);
                schroder1.add(new JLabel(""));
                schroder1.add(schroder_coef_1);

                JPanel schroder2 = new JPanel();
                JLabel schroder_label_2 = new JLabel();
                schroder_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField schroder_coef_2 = new JTextField(30);
                schroder_coef_2.setText("" + coefficients[1]);
                schroder2.setLayout(new FlowLayout());
                schroder2.add(schroder_label_2);
                schroder2.add(new JLabel(" "));
                schroder2.add(schroder_coef_2);

                JPanel schroder3 = new JPanel();
                JLabel schroder_label_3 = new JLabel();
                schroder_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField schroder_coef_3 = new JTextField(30);
                schroder_coef_3.setText("" + coefficients[2]);
                schroder3.setLayout(new FlowLayout());
                schroder3.add(schroder_label_3);
                schroder3.add(new JLabel(" "));
                schroder3.add(schroder_coef_3);

                JPanel schroder4 = new JPanel();
                JLabel schroder_label_4 = new JLabel();
                schroder_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField schroder_coef_4 = new JTextField(30);
                schroder_coef_4.setText("" + coefficients[3]);
                schroder4.setLayout(new FlowLayout());
                schroder4.add(schroder_label_4);
                schroder4.add(new JLabel(" "));
                schroder4.add(schroder_coef_4);

                JPanel schroder5 = new JPanel();
                JLabel schroder_label_5 = new JLabel();
                schroder_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField schroder_coef_5 = new JTextField(30);
                schroder_coef_5.setText("" + coefficients[4]);
                schroder5.setLayout(new FlowLayout());
                schroder5.add(schroder_label_5);
                schroder5.add(new JLabel(" "));
                schroder5.add(schroder_coef_5);

                JPanel schroder6 = new JPanel();
                JLabel schroder_label_6 = new JLabel();
                schroder_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField schroder_coef_6 = new JTextField(30);
                schroder_coef_6.setText("" + coefficients[5]);
                schroder6.setLayout(new FlowLayout());
                schroder6.add(schroder_label_6);
                schroder6.add(new JLabel(" "));
                schroder6.add(schroder_coef_6);

                JPanel schroder7 = new JPanel();
                JLabel schroder_label_7 = new JLabel();
                schroder_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField schroder_coef_7 = new JTextField(30);
                schroder_coef_7.setText("" + coefficients[6]);
                schroder7.setLayout(new FlowLayout());
                schroder7.add(schroder_label_7);
                schroder7.add(new JLabel(" "));
                schroder7.add(schroder_coef_7);

                JPanel schroder8 = new JPanel();
                JLabel schroder_label_8 = new JLabel();
                schroder_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField schroder_coef_8 = new JTextField(30);
                schroder_coef_8.setText("" + coefficients[7]);
                schroder8.setLayout(new FlowLayout());
                schroder8.add(schroder_label_8);
                schroder8.add(new JLabel(" "));
                schroder8.add(schroder_coef_8);

                JPanel schroder9 = new JPanel();
                JLabel schroder_label_9 = new JLabel();
                schroder_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField schroder_coef_9 = new JTextField(30);
                schroder_coef_9.setText("" + coefficients[8]);
                schroder9.setLayout(new FlowLayout());
                schroder9.add(schroder_label_9);
                schroder9.add(new JLabel(" "));
                schroder9.add(schroder_coef_9);

                JPanel schroder10 = new JPanel();
                JLabel schroder_label_10 = new JLabel();
                schroder_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField schroder_coef_10 = new JTextField(30);
                schroder_coef_10.setText("" + coefficients[9]);
                schroder10.setLayout(new FlowLayout());
                schroder10.add(schroder_label_10);
                schroder10.add(new JLabel(" "));
                schroder10.add(schroder_coef_10);

                JPanel schroder11 = new JPanel();
                JLabel schroder_label_11 = new JLabel();
                schroder_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField schroder_coef_11 = new JTextField(30);
                schroder_coef_11.setText("" + coefficients[10]);
                schroder11.setLayout(new FlowLayout());
                schroder11.add(schroder_label_11);
                schroder11.add(new JLabel(" "));
                schroder11.add(schroder_coef_11);

                Object[] schroder_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    schroder1,
                    schroder2,
                    schroder3,
                    schroder4,
                    schroder5,
                    schroder6,
                    schroder7,
                    schroder8,
                    schroder9,
                    schroder10,
                    schroder11,};


                int schroder_poly_res = JOptionPane.showConfirmDialog(scroll_pane, schroder_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                temp_coef = new double[11];

                if(schroder_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(schroder_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(schroder_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(schroder_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(schroder_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(schroder_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(schroder_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(schroder_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(schroder_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(schroder_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(schroder_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(schroder_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                non_zero = false;
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                optionsEnableShortcut2();
                fractal_functions[function].setSelected(true);
                break;
            case HOUSEHOLDER3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDER4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERGENERALIZED3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERGENERALIZED8:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERSIN:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERCOS:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case HOUSEHOLDERPOLY:
                polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel householder1 = new JPanel();
                JLabel householder_label_1 = new JLabel();
                householder_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField householder_coef_1 = new JTextField(30);
                householder_coef_1.addAncestorListener(new RequestFocusListener());
                householder_coef_1.setText("" + coefficients[0]);
                householder1.setLayout(new FlowLayout());
                householder1.add(householder_label_1);
                householder1.add(new JLabel(""));
                householder1.add(householder_coef_1);

                JPanel householder2 = new JPanel();
                JLabel householder_label_2 = new JLabel();
                householder_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField householder_coef_2 = new JTextField(30);
                householder_coef_2.setText("" + coefficients[1]);
                householder2.setLayout(new FlowLayout());
                householder2.add(householder_label_2);
                householder2.add(new JLabel(" "));
                householder2.add(householder_coef_2);

                JPanel householder3 = new JPanel();
                JLabel householder_label_3 = new JLabel();
                householder_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField householder_coef_3 = new JTextField(30);
                householder_coef_3.setText("" + coefficients[2]);
                householder3.setLayout(new FlowLayout());
                householder3.add(householder_label_3);
                householder3.add(new JLabel(" "));
                householder3.add(householder_coef_3);

                JPanel householder4 = new JPanel();
                JLabel householder_label_4 = new JLabel();
                householder_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField householder_coef_4 = new JTextField(30);
                householder_coef_4.setText("" + coefficients[3]);
                householder4.setLayout(new FlowLayout());
                householder4.add(householder_label_4);
                householder4.add(new JLabel(" "));
                householder4.add(householder_coef_4);

                JPanel householder5 = new JPanel();
                JLabel householder_label_5 = new JLabel();
                householder_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField householder_coef_5 = new JTextField(30);
                householder_coef_5.setText("" + coefficients[4]);
                householder5.setLayout(new FlowLayout());
                householder5.add(householder_label_5);
                householder5.add(new JLabel(" "));
                householder5.add(householder_coef_5);

                JPanel householder6 = new JPanel();
                JLabel householder_label_6 = new JLabel();
                householder_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField householder_coef_6 = new JTextField(30);
                householder_coef_6.setText("" + coefficients[5]);
                householder6.setLayout(new FlowLayout());
                householder6.add(householder_label_6);
                householder6.add(new JLabel(" "));
                householder6.add(householder_coef_6);

                JPanel householder7 = new JPanel();
                JLabel householder_label_7 = new JLabel();
                householder_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField householder_coef_7 = new JTextField(30);
                householder_coef_7.setText("" + coefficients[6]);
                householder7.setLayout(new FlowLayout());
                householder7.add(householder_label_7);
                householder7.add(new JLabel(" "));
                householder7.add(householder_coef_7);

                JPanel householder8 = new JPanel();
                JLabel householder_label_8 = new JLabel();
                householder_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField householder_coef_8 = new JTextField(30);
                householder_coef_8.setText("" + coefficients[7]);
                householder8.setLayout(new FlowLayout());
                householder8.add(householder_label_8);
                householder8.add(new JLabel(" "));
                householder8.add(householder_coef_8);

                JPanel householder9 = new JPanel();
                JLabel householder_label_9 = new JLabel();
                householder_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField householder_coef_9 = new JTextField(30);
                householder_coef_9.setText("" + coefficients[8]);
                householder9.setLayout(new FlowLayout());
                householder9.add(householder_label_9);
                householder9.add(new JLabel(" "));
                householder9.add(householder_coef_9);

                JPanel householder10 = new JPanel();
                JLabel householder_label_10 = new JLabel();
                householder_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField householder_coef_10 = new JTextField(30);
                householder_coef_10.setText("" + coefficients[9]);
                householder10.setLayout(new FlowLayout());
                householder10.add(householder_label_10);
                householder10.add(new JLabel(" "));
                householder10.add(householder_coef_10);

                JPanel householder11 = new JPanel();
                JLabel householder_label_11 = new JLabel();
                householder_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField householder_coef_11 = new JTextField(30);
                householder_coef_11.setText("" + coefficients[10]);
                householder11.setLayout(new FlowLayout());
                householder11.add(householder_label_11);
                householder11.add(new JLabel(" "));
                householder11.add(householder_coef_11);

                Object[] householder_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    householder1,
                    householder2,
                    householder3,
                    householder4,
                    householder5,
                    householder6,
                    householder7,
                    householder8,
                    householder9,
                    householder10,
                    householder11,};


                int householder_poly_res = JOptionPane.showConfirmDialog(scroll_pane, householder_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                temp_coef = new double[11];

                if(householder_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(householder_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(householder_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(householder_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(householder_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(householder_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(householder_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(householder_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(householder_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(householder_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(householder_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(householder_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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

                non_zero = false;
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                optionsEnableShortcut2();
                fractal_functions[function].setSelected(true);
                break;
            case SECANT3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SECANT4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SECANTGENERALIZED3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SECANTGENERALIZED8:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SECANTCOS:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case SECANTPOLY:
                polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));


                JPanel secant1 = new JPanel();
                JLabel secant_label_1 = new JLabel();
                secant_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField secant_coef_1 = new JTextField(30);
                secant_coef_1.addAncestorListener(new RequestFocusListener());
                secant_coef_1.setText("" + coefficients[0]);
                secant1.setLayout(new FlowLayout());
                secant1.add(secant_label_1);
                secant1.add(new JLabel(""));
                secant1.add(secant_coef_1);

                JPanel secant2 = new JPanel();
                JLabel secant_label_2 = new JLabel();
                secant_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField secant_coef_2 = new JTextField(30);
                secant_coef_2.setText("" + coefficients[1]);
                secant2.setLayout(new FlowLayout());
                secant2.add(secant_label_2);
                secant2.add(new JLabel(" "));
                secant2.add(secant_coef_2);

                JPanel secant3 = new JPanel();
                JLabel secant_label_3 = new JLabel();
                secant_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField secant_coef_3 = new JTextField(30);
                secant_coef_3.setText("" + coefficients[2]);
                secant3.setLayout(new FlowLayout());
                secant3.add(secant_label_3);
                secant3.add(new JLabel(" "));
                secant3.add(secant_coef_3);

                JPanel secant4 = new JPanel();
                JLabel secant_label_4 = new JLabel();
                secant_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField secant_coef_4 = new JTextField(30);
                secant_coef_4.setText("" + coefficients[3]);
                secant4.setLayout(new FlowLayout());
                secant4.add(secant_label_4);
                secant4.add(new JLabel(" "));
                secant4.add(secant_coef_4);

                JPanel secant5 = new JPanel();
                JLabel secant_label_5 = new JLabel();
                secant_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField secant_coef_5 = new JTextField(30);
                secant_coef_5.setText("" + coefficients[4]);
                secant5.setLayout(new FlowLayout());
                secant5.add(secant_label_5);
                secant5.add(new JLabel(" "));
                secant5.add(secant_coef_5);

                JPanel secant6 = new JPanel();
                JLabel secant_label_6 = new JLabel();
                secant_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField secant_coef_6 = new JTextField(30);
                secant_coef_6.setText("" + coefficients[5]);
                secant6.setLayout(new FlowLayout());
                secant6.add(secant_label_6);
                secant6.add(new JLabel(" "));
                secant6.add(secant_coef_6);

                JPanel secant7 = new JPanel();
                JLabel secant_label_7 = new JLabel();
                secant_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField secant_coef_7 = new JTextField(30);
                secant_coef_7.setText("" + coefficients[6]);
                secant7.setLayout(new FlowLayout());
                secant7.add(secant_label_7);
                secant7.add(new JLabel(" "));
                secant7.add(secant_coef_7);

                JPanel secant8 = new JPanel();
                JLabel secant_label_8 = new JLabel();
                secant_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField secant_coef_8 = new JTextField(30);
                secant_coef_8.setText("" + coefficients[7]);
                secant8.setLayout(new FlowLayout());
                secant8.add(secant_label_8);
                secant8.add(new JLabel(" "));
                secant8.add(secant_coef_8);

                JPanel secant9 = new JPanel();
                JLabel secant_label_9 = new JLabel();
                secant_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField secant_coef_9 = new JTextField(30);
                secant_coef_9.setText("" + coefficients[8]);
                secant9.setLayout(new FlowLayout());
                secant9.add(secant_label_9);
                secant9.add(new JLabel(" "));
                secant9.add(secant_coef_9);

                JPanel secant10 = new JPanel();
                JLabel secant_label_10 = new JLabel();
                secant_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField secant_coef_10 = new JTextField(30);
                secant_coef_10.setText("" + coefficients[9]);
                secant10.setLayout(new FlowLayout());
                secant10.add(secant_label_10);
                secant10.add(new JLabel(" "));
                secant10.add(secant_coef_10);

                JPanel secant11 = new JPanel();
                JLabel secant_label_11 = new JLabel();
                secant_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField secant_coef_11 = new JTextField(30);
                secant_coef_11.setText("" + coefficients[10]);
                secant11.setLayout(new FlowLayout());
                secant11.add(secant_label_11);
                secant11.add(new JLabel(" "));
                secant11.add(secant_coef_11);

                Object[] secant_poly = {
                    " ",
                    "Enter the coefficients of the polynomial,",
                    polynomial,
                    " ",
                    secant1,
                    secant2,
                    secant3,
                    secant4,
                    secant5,
                    secant6,
                    secant7,
                    secant8,
                    secant9,
                    secant10,
                    secant11,};


                int secant_poly_res = JOptionPane.showConfirmDialog(scroll_pane, secant_poly, "Polynomial coefficients", JOptionPane.OK_CANCEL_OPTION);

                temp_coef = new double[11];

                if(secant_poly_res == JOptionPane.OK_OPTION) {
                    try {

                        temp_coef[0] = Double.parseDouble(secant_coef_1.getText());
                        temp_coef[1] = Double.parseDouble(secant_coef_2.getText());
                        temp_coef[2] = Double.parseDouble(secant_coef_3.getText());
                        temp_coef[3] = Double.parseDouble(secant_coef_4.getText());
                        temp_coef[4] = Double.parseDouble(secant_coef_5.getText());
                        temp_coef[5] = Double.parseDouble(secant_coef_6.getText());
                        temp_coef[6] = Double.parseDouble(secant_coef_7.getText());
                        temp_coef[7] = Double.parseDouble(secant_coef_8.getText());
                        temp_coef[8] = Double.parseDouble(secant_coef_9.getText());
                        temp_coef[9] = Double.parseDouble(secant_coef_10.getText());
                        temp_coef[10] = Double.parseDouble(secant_coef_11.getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == HOUSEHOLDERPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == HOUSEHOLDERPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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

                non_zero = false;
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == HOUSEHOLDERPOLY || temp2 == NOVA || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
                JOptionPane.showMessageDialog(scroll_pane, poly, "Polynomial", JOptionPane.INFORMATION_MESSAGE);
                main_panel.repaint();

                optionsEnableShortcut2();
                fractal_functions[function].setSelected(true);
                break;
            case STEFFENSEN3:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case STEFFENSEN4:
                fractal_functions[function].setEnabled(false);
                optionsEnableShortcut2();
                break;
            case STEFFENSENGENERALIZED3:
                fractal_functions[function].setEnabled(false);
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


                String[] method = {"Newton", "Halley", "Schroder", "Householder", "Secant", "Steffensen"};

                JComboBox method_choice = new JComboBox(method);
                method_choice.setSelectedIndex(nova_method);
                method_choice.setToolTipText("Selects the root finding method for the Nova function.");

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
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                periodicity_checking_opt.setEnabled(false);
                perturbation_opt.setEnabled(false);
                init_val_opt.setEnabled(false);
                break;
            case USER_FORMULA_ITERATION_BASED:

                JTextField field_formula_it_based1 = new JTextField(35);
                field_formula_it_based1.setText(user_formula_iteration_based[0]);
                field_formula_it_based1.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_it_based2 = new JTextField(35);
                field_formula_it_based2.setText(user_formula_iteration_based[1]);

                JTextField field_formula_it_based3 = new JTextField(35);
                field_formula_it_based3.setText(user_formula_iteration_based[2]);

                JTextField field_formula_it_based4 = new JTextField(35);
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


                JLabel variables2 = new JLabel("Variables:");
                variables2.setFont(new Font("default", Font.BOLD, 11));

                JLabel operations2 = new JLabel("Operations:");
                operations2.setFont(new Font("default", Font.BOLD, 11));

                JLabel constants2 = new JLabel("Constants:");
                constants2.setFont(new Font("default", Font.BOLD, 11));

                JLabel complex_numbers2 = new JLabel("Complex numbers:");
                complex_numbers2.setFont(new Font("default", Font.BOLD, 11));

                JLabel exp_log2 = new JLabel("Exponential and logarithmic functions:");
                exp_log2.setFont(new Font("default", Font.BOLD, 11));

                JLabel trig2 = new JLabel("Trigonometric functions:");
                trig2.setFont(new Font("default", Font.BOLD, 11));


                JLabel other2 = new JLabel("Other functions:");
                other2.setFont(new Font("default", Font.BOLD, 11));

                JLabel bail2 = new JLabel("Bailout technique:");
                bail2.setFont(new Font("default", Font.BOLD, 11));

                String[] method42 = {"Escape Algorithm", "Converging Algorithm"};

                JComboBox method42_choice = new JComboBox(method42);
                method42_choice.setSelectedIndex(bail_technique);
                method42_choice.setToolTipText("Selects the bailout technique.");


                Object[] message32 = {
                    variables2,
                    "z (current sequence point), c (plane point), n (current iteration),",
                    "p (previous sequence point), s (starting sequence point)",
                    operations2,
                    "+, -, *, /, ^",
                    constants2,
                    "pi, e, phi, c10, alpha, delta",
                    complex_numbers2,
                    "a + bi",
                    exp_log2,
                    "exp, log, log10, log2",
                    trig2,
                    "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch",
                    "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch",
                    other2,
                    "sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, bipol, ibipol",
                    " ",
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2
                                        == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2
                                        == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                        if(julia) {
                            julia = false;
                            julia_button.setSelected(false);
                            julia_opt.setSelected(false);
                            fast_julia_image = null;
                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE
                                    && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm
                                    != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm
                                    != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                rootFindingMethodsSetEnabled(true);
                            }
                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }

                        }

                        if(init_val) {
                            init_val = false;
                            init_val_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !perturbation) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(perturbation) {
                            perturbation = false;
                            perturbation_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !init_val) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(julia_map) {
                            julia_map = false;
                            julia_map_opt.setSelected(false);
                            setOptions(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }

                            boundary_tracing_opt.setEnabled(true);
                        }

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                    catch(EvaluationException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                JTextField field_condition = new JTextField(16);
                field_condition.setText(user_formula_conditions[0]);
                field_condition.addAncestorListener(new RequestFocusListener());

                JTextField field_condition2 = new JTextField(16);
                field_condition2.setText(user_formula_conditions[1]);

                formula_panel_cond1.add(new JLabel("Left operand", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(new JLabel("Right operand", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(field_condition);
                formula_panel_cond1.add(field_condition2);

                JTextField field_formula_cond1 = new JTextField(35);
                field_formula_cond1.setText(user_formula_condition_formula[0]);


                JTextField field_formula_cond2 = new JTextField(35);
                field_formula_cond2.setText(user_formula_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(35);
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


                JLabel variables3 = new JLabel("Variables:");
                variables3.setFont(new Font("default", Font.BOLD, 11));

                JLabel operations3 = new JLabel("Operations:");
                operations3.setFont(new Font("default", Font.BOLD, 11));

                JLabel constants3 = new JLabel("Constants:");
                constants3.setFont(new Font("default", Font.BOLD, 11));

                JLabel complex_numbers3 = new JLabel("Complex numbers:");
                complex_numbers3.setFont(new Font("default", Font.BOLD, 11));

                JLabel exp_log3 = new JLabel("Exponential and logarithmic functions:");
                exp_log3.setFont(new Font("default", Font.BOLD, 11));

                JLabel trig3 = new JLabel("Trigonometric functions:");
                trig3.setFont(new Font("default", Font.BOLD, 11));


                JLabel other3 = new JLabel("Other functions:");
                other3.setFont(new Font("default", Font.BOLD, 11));

                JLabel bail3 = new JLabel("Bailout technique:");
                bail3.setFont(new Font("default", Font.BOLD, 11));

                String[] method43 = {"Escape Algorithm", "Converging Algorithm"};

                JComboBox method43_choice = new JComboBox(method43);
                method43_choice.setSelectedIndex(bail_technique);
                method43_choice.setToolTipText("Selects the bailout technique.");


                Object[] message33 = {
                    variables3,
                    "z (current sequence point), c (plane point), n (current iteration),",
                    "p (previous sequence point), s (starting sequence point)",
                    operations3,
                    "+, -, *, /, ^",
                    constants3,
                    "pi, e, phi, c10, alpha, delta",
                    complex_numbers3,
                    "a + bi",
                    exp_log3,
                    "exp, log, log10, log2",
                    trig3,
                    "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch",
                    "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch",
                    other3,
                    "sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, bipol, ibipol",
                    " ",
                    bail3,
                    method43_choice,
                    " ",
                    formula_panel_cond1,
                    formula_panel_cond11,
                    formula_panel_cond12,
                    formula_panel_cond13,};


                res = JOptionPane.showConfirmDialog(scroll_pane, message33, "User Formula Conditional", JOptionPane.OK_CANCEL_OPTION);


                if(res == JOptionPane.OK_OPTION) {

                    boolean temp_bool = false;

                    try {
                        parser.parse(field_condition.getText());
                        parser.parse(field_condition2.getText());

                        parser.parse(field_formula_cond1.getText());
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "Every expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2
                                        == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                        if(julia) {
                            julia = false;
                            julia_button.setSelected(false);
                            julia_opt.setSelected(false);
                            fast_julia_image = null;
                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE
                                    && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm
                                    != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm
                                    != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                rootFindingMethodsSetEnabled(true);
                            }
                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(init_val) {
                            init_val = false;
                            init_val_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !perturbation) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(perturbation) {
                            perturbation = false;
                            perturbation_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !init_val) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(julia_map) {
                            julia_map = false;
                            julia_map_opt.setSelected(false);
                            setOptions(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID
                                    && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH
                                    && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm
                                    != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM
                                    && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM
                                    && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }

                            boundary_tracing_opt.setEnabled(true);
                        }

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                    catch(EvaluationException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                    || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_ITERATION_BASED || temp2 == USER_FORMULA || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH
                                || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
            case 0:
                fractal_functions[function].setEnabled(false);
                if(!burning_ship && !mandel_grass && !init_val && !perturbation) {
                    exterior_de_opt.setEnabled(true);
                }
                if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !init_val && !perturbation) {
                    out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                }
                if(out_coloring_algorithm != ATOM_DOMAIN && !init_val && !perturbation) {
                    out_coloring_modes[ATOM_DOMAIN].setEnabled(true);
                }
                if(out_coloring_algorithm != BIOMORPH) {
                    out_coloring_modes[BIOMORPH].setEnabled(true);
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

                JTextField field_formula = new JTextField(35);
                field_formula.setText(user_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_formula2 = new JTextField(35);
                field_formula2.setText(user_formula2);

                JPanel formula_panel = new JPanel();

                formula_panel.add(new JLabel("z ="));
                formula_panel.add(field_formula);

                JPanel formula_panel2 = new JPanel();

                formula_panel2.add(new JLabel("c ="));
                formula_panel2.add(field_formula2);


                JLabel variables = new JLabel("Variables:");
                variables.setFont(new Font("default", Font.BOLD, 11));

                JLabel operations = new JLabel("Operations:");
                operations.setFont(new Font("default", Font.BOLD, 11));

                JLabel constants = new JLabel("Constants:");
                constants.setFont(new Font("default", Font.BOLD, 11));

                JLabel complex_numbers = new JLabel("Complex numbers:");
                complex_numbers.setFont(new Font("default", Font.BOLD, 11));

                JLabel exp_log = new JLabel("Exponential and logarithmic functions:");
                exp_log.setFont(new Font("default", Font.BOLD, 11));

                JLabel trig = new JLabel("Trigonometric functions:");
                trig.setFont(new Font("default", Font.BOLD, 11));

                JLabel other = new JLabel("Other functions:");
                other.setFont(new Font("default", Font.BOLD, 11));

                JLabel bail = new JLabel("Bailout technique:");
                bail.setFont(new Font("default", Font.BOLD, 11));

                String[] method4 = {"Escape Algorithm", "Converging Algorithm"};

                JComboBox method4_choice = new JComboBox(method4);
                method4_choice.setSelectedIndex(bail_technique);
                method4_choice.setToolTipText("Selects the bailout technique.");

                Object[] message3 = {
                    variables,
                    "z (current sequence point), c (plane point), n (current iteration),",
                    "p (previous sequence point), s (starting sequence point)",
                    operations,
                    "+, -, *, /, ^",
                    constants,
                    "pi, e, phi, c10, alpha, delta",
                    complex_numbers,
                    "a + bi",
                    exp_log,
                    "exp, log, log10, log2",
                    trig,
                    "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch",
                    "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch",
                    other,
                    "sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, bipol, ibipol",
                    " ",
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
                        if(!parser.foundZ()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The expression must contain at least the variable z.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[function].setSelected(false);

                            if(function != temp2) {
                                if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

                        user_formula = field_formula.getText();
                        user_formula2 = field_formula2.getText();
                        user_formula_c = temp_bool;
                        bail_technique = method4_choice.getSelectedIndex();

                        if(julia) {
                            julia = false;
                            julia_button.setSelected(false);
                            julia_opt.setSelected(false);
                            fast_julia_image = null;
                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                rootFindingMethodsSetEnabled(true);
                            }
                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(init_val) {
                            init_val = false;
                            init_val_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !perturbation) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(perturbation) {
                            perturbation = false;
                            perturbation_opt.setSelected(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !init_val) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }
                        }

                        if(julia_map) {
                            julia_map = false;
                            julia_map_opt.setSelected(false);
                            setOptions(false);

                            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                                rootFindingMethodsSetEnabled(true);
                            }

                            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                            }

                            boundary_tracing_opt.setEnabled(true);
                        }

                        if(!user_formula_c) {
                            julia_opt.setEnabled(false);
                            julia_button.setEnabled(false);
                            julia_map_opt.setEnabled(false);
                            perturbation_opt.setEnabled(false);
                            init_val_opt.setEnabled(false);
                        }

                        if(bail_technique == 1) {
                            bailout_number.setEnabled(false);
                            bailout_test_menu.setEnabled(false);
                            periodicity_checking_opt.setEnabled(false);
                        }
                    }
                    catch(ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                    catch(EvaluationException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[function].setSelected(false);

                        if(function != temp2) {
                            if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
                        if(temp2 == USER_FORMULA_CONDITIONAL || temp2 == USER_FORMULA_ITERATION_BASED || temp2 == SECANTPOLY || temp2 == MANDELBROTWTH || temp2 == NOVA || temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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

        defaultFractalSettings();

    }

    private void setBailout() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    JOptionPane.showMessageDialog(scroll_pane, "Bailout value must be lower than 1e70.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            bailout = temp;

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new bailout number is " + bailout + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

            setOptions(false);

            progress.setValue(0);

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();


        JTextField field_rotation = new JTextField();
        field_rotation.setText("" + rotation);

        field_rotation.addAncestorListener(new RequestFocusListener());

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
            field_imaginary.setText("" + (-rotation_center[1]));
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);

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
                        field_imaginary.setText("" + (-rotation_center[1]));
                    }
                    field_imaginary.setEditable(true);
                }
                else {

                    double temp_xcenter = xCenter - rotation_center[0];
                    double temp_ycenter = yCenter - rotation_center[1];

                    double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                    double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                    temp1 = temp1 == 0.0 ? 0.0 : temp1;
                    temp2 = temp2 == 0.0 ? 0.0 : -temp2;

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
            "Rotation:", field_rotation,
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
                tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis                
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
                JOptionPane.showMessageDialog(scroll_pane, "Rotation angle must be lower than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
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

        main_panel.repaint();

        String temp_str = "" + rotation_center[0];

        if(-rotation_center[1] > 0) {
            temp_str += "+" + (-rotation_center[1]) + "i";
        }
        else {
            if(rotation_center[1] == 0) {
                temp_str += "+" + (0.0) + "i";
            }
            else {
                temp_str += "" + (-rotation_center[1]) + "i";
            }
        }

        JOptionPane.showMessageDialog(scroll_pane, "The new rotation angle is " + rotation + " degrees,\nabout the point " + temp_str + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
        }

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        if(rotation != 0 && rotation != 360 && rotation != -360) {
            grid_opt.setEnabled(false);
            grid = false;
            grid_opt.setSelected(false);

            boundaries_opt.setEnabled(false);
            boundaries = false;
            boundaries_opt.setSelected(false);
        }

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setBurningShip() {

        if(!burning_ship_opt.isSelected()) {
            burning_ship = false;
            if(function == 0 && !mandel_grass && !init_val && !perturbation) {
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

    private void setMandelGrass() {

        if(!mandel_grass_opt.isSelected()) {
            mandel_grass = false;
            if(function == 0 && !burning_ship && !init_val && !perturbation) {
                exterior_de_opt.setEnabled(true);
            }
        }
        else {

            if(backup_orbit != null && orbit) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
            case MANDELBROTNTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case MANDELBROTWTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case MANDELPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
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
            case NEWTON3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTON4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NEWTONPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEY3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEY4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HALLEYPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODER3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODER4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SCHRODERPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDER3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDER4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERSIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case HOUSEHOLDERPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANT3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANT4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTGENERALIZED8:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTCOS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SECANTPOLY:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSEN3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSEN4:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case STEFFENSENGENERALIZED3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case NOVA:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case BARNSLEY1:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                break;
            case BARNSLEY2:
                xCenter = 0;
                yCenter = 0;
                size = 7;
                break;
            case BARNSLEY3:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case MANDELBAR:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SPIDER:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case MANOWAR:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case PHOENIX:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SIERPINSKI_GASKET:
                xCenter = 0.5;
                yCenter = 0.5;
                size = 3;
                break;
            case EXP:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case LOG:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case COS:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case TAN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case COT:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SINH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case COSH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case TANH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case COTH:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA30:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA31:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA1:
                xCenter = 0;
                yCenter = 0;
                size = 24;
                break;
            case FORMULA2:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA3:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA4:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                break;
            case FORMULA5:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                break;
            case FORMULA6:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA7:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case FORMULA8:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA9:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA10:
                xCenter = 0;
                yCenter = 0;
                size = 3;
                break;
            case FORMULA11:
                xCenter = 0;
                yCenter = 0;
                size = 1.5;
                break;
            case FORMULA12:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case FORMULA13:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA14:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA15:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA16:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA17:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA18:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA19:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA20:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA21:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA22:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA23:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA24:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA25:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case FORMULA26:
                xCenter = 0;
                yCenter = 0;
                size = 6;
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
            case FORMULA32:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA33:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA34:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA35:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA36:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA37:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
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
            case FORMULA39:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA40:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA41:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FORMULA42:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 12;
                }
                break;
            case FORMULA43:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                break;
            case FORMULA44:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                break;
            case FORMULA45:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 24;
                }
                break;
            case USER_FORMULA:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case USER_FORMULA_ITERATION_BASED:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case USER_FORMULA_CONDITIONAL:
                if(julia) {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                else {
                    xCenter = 0;
                    yCenter = 0;
                    size = 6;
                }
                break;
            case FROTHY_BASIN:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                break;
            case SZEGEDI_BUTTERFLY1:
                xCenter = 0;
                yCenter = 0;
                size = 12;
                break;
            case SZEGEDI_BUTTERFLY2:
                xCenter = 0;
                yCenter = 0;
                size = 12;
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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void setWholeImageDone(Boolean temp) {

        whole_image_done = temp;

    }

    private void setSizeOfImage() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be lower than than 6501.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            whole_image_done = false;

            old_grid = false;

            old_boundaries = false;

            image_size = temp;

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new image size is " + image_size + "x" + image_size + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

            if(image_size > 4000) {
                orbit_opt.setEnabled(false);
                orbit_button.setEnabled(false);
                orbit_button.setSelected(false);
                orbit_opt.setSelected(false);
                setOrbitOption();
            }

            if(image_size > 2000) {
                d3_opt.setEnabled(false);
                d3_button.setEnabled(false);
                d3_button.setSelected(false);
                d3_opt.setSelected(false);
                d3 = false;
                d3_details_opt.setEnabled(false);
                boundary_tracing_opt.setEnabled(true);
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

            last_used = null;
            //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            //Graphics2D graphics = last_used.createGraphics();
            //graphics.drawImage(image, 0, 0, image_size, image_size, null);

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

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_xcenter_size = xCenter - size_2_x;
        double temp_ycenter_size = yCenter - size_2_y;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        try {
            double temp1 = temp_xcenter_size + temp_size_image_size_x * main_panel.getMousePosition().getX() - rotation_center[0];
            double temp2 = temp_ycenter_size + temp_size_image_size_y * main_panel.getMousePosition().getY() - rotation_center[1];

            temp_xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1] + rotation_center[0];
            temp_yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0] + rotation_center[1];
        }
        catch(Exception ex) {
            return;
        }

        fast_julia_image = new BufferedImage(FAST_JULIA_IMAGE_SIZE, FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

        switch (function) {
            case MANDELBROTNTH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case MANDELBROTWTH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case MANDELPOLY:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
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
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 7;
                temp_bailout = 2;
                break;
            case BARNSLEY2:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 7;
                temp_bailout = 2;
                break;
            case BARNSLEY3:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case MANDELBAR:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case SPIDER:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case MANOWAR:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case PHOENIX:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case EXP:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case LOG:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case SIN:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case COS:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case TAN:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case COT:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case SINH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case COSH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case TANH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case COTH:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA30:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA31:
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
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA3:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                temp_bailout = 4;
                break;
            case FORMULA4:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                temp_bailout = 4;
                break;
            case FORMULA5:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                temp_bailout = 4;
                break;
            case FORMULA6:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA7:
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
            case FORMULA9:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                temp_bailout = 4;
                break;
            case FORMULA10:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 3;
                temp_bailout = 4;
                break;
            case FORMULA11:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 1.5;
                temp_bailout = 100;
                break;
            case FORMULA12:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 8;
                break;
            case FORMULA13:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA14:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA15:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA16:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA17:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA18:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA19:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 4;
                break;
            case FORMULA20:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA21:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA22:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA23:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA24:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA25:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA26:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA27:
                temp_xCenter = -2;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA28:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 16;
                break;
            case FORMULA29:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 16;
                break;
            case FORMULA32:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA33:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA34:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA35:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA36:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA37:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 16;
                break;
            case FORMULA38:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FORMULA39:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA40:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
                break;
            case FORMULA41:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 8;
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
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                temp_bailout = 16;
                break;
            case FORMULA45:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 24;
                temp_bailout = 16;
                break;
            case USER_FORMULA:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case USER_FORMULA_ITERATION_BASED:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case USER_FORMULA_CONDITIONAL:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case FROTHY_BASIN:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
            case SZEGEDI_BUTTERFLY1:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 12;
                temp_bailout = 4;
                break;
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

        ThreadDraw.resetAtomics();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(color_choice, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, temp_bailout, n_norm, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, temp_xJuliaCenter, temp_yJuliaCenter);
                }
                else {
                    threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, bailout_test_algorithm, temp_bailout, n_norm, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, temp_xJuliaCenter, temp_yJuliaCenter);
                }
            }
        }

        startThreads(n);

    }

    private void setColorCycling() {

        if(!color_cycling_opt.isSelected()) {

            color_cycling = false;
            color_cycling_button.setSelected(false);
            threads[0][0].setColorCycling(false);
            while(!threadsAvailable()) {
            }
            color_cycling_location = threads[0][0].getColorCyclingLocation();

            if(color_choice == palette.length - 1) {
                temp_color_cycling_location = color_cycling_location;
            }

            if(filters[ANTIALIASING] || filters[EDGE_DETECTION] || filters[EMBOSS] || filters[SHARPNESS] || filters[INVERT_COLORS] || filters[BLURRING] || filters[COLOR_CHANNEL_MASKING] || filters[FADE_OUT] || filters[COLOR_CHANNEL_SWAPPING] || filters[CONTRAST_BRIGHTNESS] || filters[GRAYSCALE] || filters[COLOR_TEMPERATURE] || filters[COLOR_CHANNEL_MIXING]) {
                setOptions(false);

                progress.setValue(0);

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                last_used = null;
                setOptions(true);
            }
        }
        else {

            color_cycling = true;
            color_cycling_button.setSelected(true);
            setOptions(false);

            if(color_choice != palette.length - 1) {
                threads[0][0] = new Palette(color_choice, 0, image_size, 0, image_size, max_iterations, ptr, fractal_color, smoothing, image, color_cycling_location);
            }
            else {
                threads[0][0] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, 0, image_size, 0, image_size, max_iterations, ptr, fractal_color, smoothing, image, color_cycling_location);
            }

            whole_image_done = false;

            startThreads(1);

        }

    }

    private void createThreadsPaletteAndFilter() {

        ThreadDraw.resetAtomics();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, color_cycling_location, smoothing, filters, filters_options_vals);
                }
                else {
                    threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, color_cycling_location, smoothing, filters, filters_options_vals);
                }
            }
        }

    }

    private void createThreadsRotate3DModel() {

        ThreadDraw.resetAtomics();

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, fiX, fiY, d3_draw_method, ptr, image, filters, filters_options_vals);
                }
                else {
                    threads[i][j] = new CustomPalette(j * detail / n, (j + 1) * detail / n, i * detail / n, (i + 1) * detail / n, detail, fiX, fiY, d3_draw_method, ptr, image, filters, filters_options_vals);
                }
            }
        }

    }

    private void shiftPalette() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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

            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The new palette shift value is " + color_cycling_location + ".", "Info", JOptionPane.INFORMATION_MESSAGE);

            setOptions(false);

            progress.setValue(0);

            //scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            //scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setPlane(int temp) {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        planes[plane_type].setSelected(false);
        planes[plane_type].setEnabled(true);

        int temp2 = plane_type;
        plane_type = temp;

        if(plane_type == USER_PLANE) {
            JTextField field_formula = new JTextField(35);
            field_formula.setText(user_plane);
            field_formula.addAncestorListener(new RequestFocusListener());

            JPanel formula_panel = new JPanel();

            formula_panel.add(new JLabel("z ="));
            formula_panel.add(field_formula);

            JLabel variables = new JLabel("Variables:");
            variables.setFont(new Font("default", Font.BOLD, 11));

            JLabel operations = new JLabel("Operations:");
            operations.setFont(new Font("default", Font.BOLD, 11));

            JLabel constants = new JLabel("Constants:");
            constants.setFont(new Font("default", Font.BOLD, 11));

            JLabel complex_numbers = new JLabel("Complex numbers:");
            complex_numbers.setFont(new Font("default", Font.BOLD, 11));

            JLabel exp_log = new JLabel("Exponential and logarithmic functions:");
            exp_log.setFont(new Font("default", Font.BOLD, 11));

            JLabel trig = new JLabel("Trigonometric functions:");
            trig.setFont(new Font("default", Font.BOLD, 11));

            JLabel other = new JLabel("Other functions:");
            other.setFont(new Font("default", Font.BOLD, 11));

            Object[] message3 = {
                variables,
                "z (current sequence point)",
                operations,
                "+, -, *, /, ^",
                constants,
                "pi, e, phi, c10, alpha, delta",
                complex_numbers,
                "a + bi",
                exp_log,
                "exp, log, log10, log2",
                trig,
                "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch",
                "asin, acos, atan, acot, asec, acsc, asinh, acosh, atanh, acoth, asech, acsch",
                other,
                "sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, bipol, ibipol",
                " ",
                "Insert your plane transformation.",
                formula_panel,};


            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Plane", JOptionPane.OK_CANCEL_OPTION);


            if(res == JOptionPane.OK_OPTION) {
                try {
                    parser.parse(field_formula.getText());
                    if(!parser.foundZ() || parser.foundC() || parser.foundN() || parser.foundP()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The expression must contain the variable z only.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();

                        planes[plane_type].setSelected(false);

                        if(plane_type != temp2) {
                            if(temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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

                    user_plane = field_formula.getText();

                    planes[plane_type].setSelected(true);

                    defaultFractalSettings();

                }
                catch(ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();

                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                catch(EvaluationException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();

                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == TWIRL_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                field_imaginary.setText("" + (-plane_transform_center[1]));
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-plane_transform_center[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis                
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                field_imaginary.setText("" + (-plane_transform_center[1]));
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            JTextField field_sides = new JTextField();
            field_sides.setText("" + plane_transform_sides);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-plane_transform_center[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis
                    temp6 = Integer.parseInt(field_sides.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                field_imaginary.setText("" + (-plane_transform_center[1]));
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            JTextField field_amount = new JTextField();
            field_amount.setText("" + plane_transform_amount);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-plane_transform_center[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis                
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
        else if(plane_type == FOLDUP_PLANE || plane_type == FOLDDOWN_PLANE || plane_type == FOLDRIGHT_PLANE || plane_type == FOLDLEFT_PLANE) {

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
                field_imaginary.setText("" + (-plane_transform_center[1]));
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-plane_transform_center[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
                        field_imaginary.setEditable(false);
                    }
                }
            });


            Object[] message = {
                " ",
                "Set the point about the fold.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                current_center, " "};


            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Fold", JOptionPane.OK_CANCEL_OPTION);


            double tempReal, tempImaginary;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis  
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                        if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == CIRCLEINVERSION_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                field_imaginary.setText("" + (-plane_transform_center[1]));
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);

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
                            field_imaginary.setText("" + (-plane_transform_center[1]));
                        }
                        field_imaginary.setEditable(true);
                    }
                    else {

                        double temp_xcenter = xCenter - rotation_center[0];
                        double temp_ycenter = yCenter - rotation_center[1];

                        double temp1 = temp_xcenter * rotation_vals[0] - temp_ycenter * rotation_vals[1] + rotation_center[0];
                        double temp2 = temp_xcenter * rotation_vals[1] + temp_ycenter * rotation_vals[0] + rotation_center[1];

                        temp1 = temp1 == 0.0 ? 0.0 : temp1;
                        temp2 = temp2 == 0.0 ? 0.0 : -temp2;

                        field_real.setText("" + temp1);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + (temp2));
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
                    tempImaginary = -Double.parseDouble(field_imaginary.getText());  //Reveresed Axis                
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[plane_type].setSelected(false);

                    if(plane_type != temp2) {
                        if(temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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
                    if(temp2 == USER_PLANE || temp2 == TWIRL_PLANE || temp2 == SHEAR_PLANE || temp2 == KALEIDOSCOPE_PLANE || temp2 == PINCH_PLANE || temp2 == FOLDUP_PLANE || temp2 == FOLDDOWN_PLANE || temp2 == FOLDRIGHT_PLANE || temp2 == FOLDLEFT_PLANE || temp2 == FOLDIN_PLANE || temp2 == FOLDOUT_PLANE) {
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

    private void setBailoutTest(int temp) {

        if(temp == BAILOUT_TEST_NNORM) {
            if(backup_orbit != null && orbit) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the new N-Norm number.", "N-Norm", JOptionPane.QUESTION_MESSAGE);

            try {
                double temp2 = Double.parseDouble(ans);

                n_norm = temp2;

                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The new N-Norm number is " + n_norm + ".", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            catch(Exception ex) {
                if(bailout_test_algorithm != temp) {
                    bailout_tests[temp].setSelected(false);
                }
                else {
                    bailout_tests[temp].setSelected(true);
                }

                if(ans == null) {
                    main_panel.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                }
                return;
            }

            if(bailout_test_algorithm == temp) {
                bailout_test_algorithm = temp;
                bailout_tests[bailout_test_algorithm].setSelected(true);
            }
            else {
                bailout_tests[bailout_test_algorithm].setSelected(false);
                bailout_tests[bailout_test_algorithm].setEnabled(true);

                bailout_test_algorithm = temp;
            }

        }
        else {
            bailout_tests[bailout_test_algorithm].setSelected(false);
            bailout_tests[bailout_test_algorithm].setEnabled(true);

            bailout_test_algorithm = temp;

            bailout_tests[temp].setEnabled(false);
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setOutColoringMode(int temp) {

        out_coloring_modes[out_coloring_algorithm].setSelected(false);
        out_coloring_modes[out_coloring_algorithm].setEnabled(true);

        out_coloring_algorithm = temp;

        out_coloring_modes[temp].setEnabled(false);

        for(int k = 0; k < fractal_functions.length; k++) {
            if(function != k) {
                if(k != SIERPINSKI_GASKET && k != NEWTON3 && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                        && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                        && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                        && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                        && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                        && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3) {
                    fractal_functions[k].setEnabled(true);
                }

                if(k == SIERPINSKI_GASKET && !julia && !julia_map && !perturbation && !init_val) {
                    fractal_functions[k].setEnabled(true);
                }
            }
        }

        if(out_coloring_algorithm == DISTANCE_ESTIMATOR || exterior_de || out_coloring_algorithm == ATOM_DOMAIN) {
            for(int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
            init_val_opt.setEnabled(false);
            perturbation_opt.setEnabled(false);
        }
        else if(out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || out_coloring_algorithm == ESCAPE_TIME_GRID || out_coloring_algorithm == BIOMORPH || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || out_coloring_algorithm == ITERATIONS_PLUS_RE || out_coloring_algorithm == ITERATIONS_PLUS_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2) {
            rootFindingMethodsSetEnabled(false);
        }
        else {
            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
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


            if(!julia_map && !julia && !perturbation && !init_val && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3) {
                rootFindingMethodsSetEnabled(true);
            }
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setInColoringMode(int temp) {

        in_coloring_modes[in_coloring_algorithm].setSelected(false);
        in_coloring_modes[in_coloring_algorithm].setEnabled(true);

        in_coloring_algorithm = temp;

        in_coloring_modes[temp].setEnabled(false);


        if(in_coloring_algorithm == MAXIMUM_ITERATIONS) {
            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                periodicity_checking_opt.setEnabled(true);
            }
        }
        else {
            periodicity_checking_opt.setEnabled(false);
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        if(!smoothing_opt.isSelected()) {
            smoothing = false;
        }
        else {
            smoothing = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setExteriorDistanceEstimation() {

        if(!exterior_de_opt.isSelected()) {
            exterior_de = false;

            for(int k = 0; k < fractal_functions.length; k++) {
                if(function != k) {
                    if(k != SIERPINSKI_GASKET && k != NEWTON3 && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                            && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                            && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                            && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                            && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                            && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3) {
                        fractal_functions[k].setEnabled(true);
                    }

                    if(k == SIERPINSKI_GASKET && !julia && !julia_map && !perturbation && !init_val) {
                        fractal_functions[k].setEnabled(true);
                    }
                }
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !julia && !julia_map && !perturbation && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            burning_ship_opt.setEnabled(true);
            mandel_grass_opt.setEnabled(true);

        }
        else {
            main_panel.repaint();
            String ans = (String)JOptionPane.showInputDialog(scroll_pane, "Enter the exterior distance estimation factor.", "Distance Estimation", JOptionPane.QUESTION_MESSAGE, null, null, "" + exterior_de_factor);
            try {
                double temp = Double.parseDouble(ans);

                if(temp <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The distance estimation factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    exterior_de_opt.setSelected(false);
                    return;
                }

                exterior_de_factor = temp;
            }
            catch(Exception ex) {
                if(ans == null) {
                    exterior_de_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    exterior_de_opt.setSelected(false);
                    main_panel.repaint();
                    return;
                }
            }

            for(int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }

            burning_ship_opt.setEnabled(false);
            mandel_grass_opt.setEnabled(false);
            init_val_opt.setEnabled(false);
            perturbation_opt.setEnabled(false);

            exterior_de = true;
        }

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            setOptions(false);

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && out_coloring_algorithm != ATOM_DOMAIN && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                rootFindingMethodsSetEnabled(true);
            }

            if(out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }

            boundary_tracing_opt.setEnabled(true);

            threads = new ThreadDraw[n][n];

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
                    return;
                }
                else {
                    if(temp > 200) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be lower than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
                        julia_map_opt.setSelected(false);
                        return;
                    }
                }


                julia_grid_first_dimension = temp;
                threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The image will be created using " + julia_grid_first_dimension + "x" + julia_grid_first_dimension + "\nJulia images in a 2D grid.", "Info", JOptionPane.INFORMATION_MESSAGE);

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

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                backup_orbit = null;

                whole_image_done = false;

                createThreadsJuliaMap();

                calculation_time = System.currentTimeMillis();

                startThreads(julia_grid_first_dimension);
            }
            catch(Exception ex) {
                if(ans == null) {
                    julia_map_opt.setSelected(false);
                    main_panel.repaint();
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    julia_map_opt.setSelected(false);
                    main_panel.repaint();
                }
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

        if(zoom_window_style) {
            BasicStroke old_stroke = (BasicStroke)brush.getStroke();

            float dash1[] = {5.0f};
            BasicStroke dashed =
                    new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
            brush.setStroke(dashed);

            brush.setColor(zoom_window_color);
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            brush.drawRect(x1 - new_size / 2, y1 - new_size / 2, new_size, new_size);
            brush.setFont(new Font("default", Font.BOLD, 16));
            brush.drawString("x" + String.format("%4.2f", zoom_factor), x1 - new_size / 2 + 5, y1 - new_size / 2 - 5);

            brush.setStroke(old_stroke);
        }
        else {
            brush.setColor(zoom_window_color);
            brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            brush.drawRect(x1 - new_size / 2, y1 - new_size / 2, new_size, new_size);
            brush.setFont(new Font("default", Font.BOLD, 16));
            brush.drawString("x" + String.format("%4.2f", zoom_factor), x1 - new_size / 2 + 5, y1 - new_size / 2 - 5);
        }

    }

    public void drawBoundaries(Graphics2D brush, boolean mode) {

        double size_2_x = old_size * 0.5;
        double size_2_y = (old_size * old_height_ratio) * 0.5;
        double temp_xcenter_size = old_xCenter - size_2_x;
        double temp_ycenter_size = old_yCenter - size_2_y;
        double temp_size_image_size_x = old_size / image_size;
        double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

        brush.setColor(boundaries_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x0 = (int)((0 - temp_xcenter_size) / temp_size_image_size_x + 0.5);
        int y0 = (int)((0 - temp_ycenter_size) / temp_size_image_size_y + 0.5);


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

                if(num == bailout && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                    continue;
                }

                int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((num - temp_ycenter_size) / temp_size_image_size_y + 0.5));
                brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                brush.drawString("" + num, (int)(x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int)(y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);
            }

            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((bailout - temp_ycenter_size) / temp_size_image_size_y + 0.5));

                BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                float dash1[] = {5.0f};
                BasicStroke dashed =
                        new BasicStroke(1.0f,
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

                if(num == bailout && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                    continue;
                }

                int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((num - temp_ycenter_size) / temp_size_image_size_y + 0.5));

                brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                brush.drawString("" + num, x0 - radius_x - 10, y0 - radius_y - 5);
            }

            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {

                int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((bailout - temp_ycenter_size) / temp_size_image_size_y + 0.5));

                BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                float dash1[] = {5.0f};
                BasicStroke dashed =
                        new BasicStroke(1.0f,
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

                if(num == bailout && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {
                    continue;
                }

                int radius_x = Math.abs(x0 - (int)((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((num - temp_ycenter_size) / temp_size_image_size_y + 0.5));

                brush.drawLine(x0 - radius_x, y0, x0, y0 - radius_y);

                brush.drawLine(x0 + radius_x, y0, x0, y0 + radius_y);

                brush.drawLine(x0 + radius_x, y0, x0, y0 - radius_y);

                brush.drawLine(x0 - radius_x, y0, x0, y0 + radius_y);

                brush.drawString("" + num, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);
            }

            if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONSIN && function != NEWTONCOS && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYSIN && function != HALLEYCOS && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERSIN && function != SCHRODERCOS && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERSIN && function != HOUSEHOLDERCOS && function != HOUSEHOLDERPOLY && function != NOVA && function != SECANT3 && function != SECANT4 && function != SECANTGENERALIZED3 && function != SECANTGENERALIZED8 && function != SECANTCOS && function != SECANTPOLY && function != STEFFENSEN3 && function != STEFFENSEN4 && function != STEFFENSENGENERALIZED3 && (function != USER_FORMULA || (function == USER_FORMULA && bail_technique == 0)) && (function != USER_FORMULA_ITERATION_BASED || (function == USER_FORMULA_ITERATION_BASED && bail_technique == 0)) && (function != USER_FORMULA_CONDITIONAL || (function == USER_FORMULA_CONDITIONAL && bail_technique == 0))) {

                int radius_x = Math.abs(x0 - (int)((bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                int radius_y = Math.abs(y0 - (int)((bailout - temp_ycenter_size) / temp_size_image_size_y + 0.5));

                BasicStroke old_stroke = (BasicStroke)brush.getStroke();

                float dash1[] = {5.0f};
                BasicStroke dashed =
                        new BasicStroke(1.0f,
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

    public void drawGrid(Graphics2D brush, boolean mode) {

        double size_2_x = old_size * 0.5;
        double size_2_y = (old_size * old_height_ratio) * 0.5;
        double temp_xcenter_size = old_xCenter - size_2_x;
        double temp_ycenter_size = old_yCenter - size_2_y;
        double temp_size_image_size_x = old_size / image_size;
        double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

        brush.setColor(grid_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        double temp, temp2;
        for(int i = 1; i < grid_tiles; i++) {
            brush.drawLine(i * image_size / grid_tiles, 0, i * image_size / grid_tiles, image_size);
            brush.drawLine(0, i * image_size / grid_tiles, image_size, i * image_size / grid_tiles);


            temp = temp_xcenter_size + temp_size_image_size_x * i * image_size / grid_tiles;
            temp2 = temp_ycenter_size + temp_size_image_size_y * i * image_size / grid_tiles;

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

            temp2 = temp2 == 0 ? temp2 : -temp2;

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int custom_palette_window_width = 800;
        int custom_palette_window_height = 508;
        custom_palette_editor = new JFrame("Custom Palette Editor");
        custom_palette_editor.setIconImage(getIcon("/fractalzoomer/icons/palette.png").getImage());
        custom_palette_editor.setLayout(new FlowLayout());
        custom_palette_editor.setSize(custom_palette_window_width, custom_palette_window_height);
        custom_palette_editor.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (custom_palette_window_height / 2));
        custom_palette_editor.setResizable(false);

        final int temp_color_cycling_location_local = temp_color_cycling_location;

        custom_palette_editor.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);
                if(color_choice != number) {
                    palette[number].setSelected(false);
                }
                else {
                    palette[number].setSelected(true);
                }
                custom_palette_editor.dispose();

                temp_color_cycling_location = temp_color_cycling_location_local;

            }
        });

        temp_custom_palette = new int[custom_palette.length][custom_palette[0].length];
        for(k = 0; k < custom_palette.length; k++) {
            for(int j = 0; j < custom_palette[0].length; j++) {
                temp_custom_palette[k][j] = custom_palette[k][j];
            }
        }

        JPanel editor_panel = new JPanel();
        JPanel palette_colors = new JPanel();
        JPanel hues = new JPanel();
        JPanel buttons = new JPanel();

        editor_panel.setPreferredSize(new Dimension(780, 408));
        editor_panel.setLayout(new FlowLayout());
        palette_colors.setLayout(new FlowLayout());
        palette_colors.setPreferredSize(new Dimension(760, 60));
        hues.setLayout(new FlowLayout());
        hues.setPreferredSize(new Dimension(760, 60));
        buttons.setLayout(new FlowLayout());

        labels = new JLabel[32];
        textfields = new JTextField[32];

        for(k = 0; k < labels.length; k++) {
            labels[k] = new JLabel("");
            labels[k].setPreferredSize(new Dimension(18, 18));
            labels[k].setOpaque(true);
            labels[k].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            labels[k].setBackground(new Color(temp_custom_palette[k][1], temp_custom_palette[k][2], temp_custom_palette[k][3]));
            labels[k].setToolTipText("Left click to change this color.");
            labels[k].addMouseListener(new MouseListener() {

                int temp = k;

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    custom_palette_editor.setEnabled(false);
                    int color_window_width = 630;
                    int color_window_height = 400;
                    choose_color_frame = new JFrame("Choose Color");
                    choose_color_frame.setLayout(new FlowLayout());
                    choose_color_frame.setSize(color_window_width, color_window_height);
                    choose_color_frame.setIconImage(getIcon("/fractalzoomer/icons/color.png").getImage());
                    choose_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
                    choose_color_frame.setResizable(false);
                    color_chooser = new JColorChooser();
                    color_chooser.setColor(labels[temp].getBackground());
                    color_chooser.setPreferredSize(new Dimension(600, 320));

                    choose_color_frame.add(color_chooser);

                    choose_color_frame.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {

                            custom_palette_editor.setEnabled(true);
                            choose_color_frame.dispose();

                        }
                    });

                    JButton ok = new JButton("Ok");

                    ok.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            temp_custom_palette[temp][1] = color_chooser.getColor().getRed();
                            temp_custom_palette[temp][2] = color_chooser.getColor().getGreen();
                            temp_custom_palette[temp][3] = color_chooser.getColor().getBlue();
                            labels[temp].setBackground(new Color(temp_custom_palette[temp][1], temp_custom_palette[temp][2], temp_custom_palette[temp][3]));

                            custom_palette_editor.setEnabled(true);
                            choose_color_frame.dispose();

                            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                            paintGradientAndGraph(c);

                        }
                    });

                    JButton close = new JButton("Cancel");
                    close.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {

                            custom_palette_editor.setEnabled(true);
                            choose_color_frame.dispose();

                        }
                    });

                    JPanel buttons = new JPanel();

                    buttons.add(ok);
                    buttons.add(close);

                    choose_color_frame.add(buttons);

                    choose_color_frame.setVisible(true);

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
            palette_colors.add(labels[k]);

            textfields[k] = new JTextField();
            textfields[k].setPreferredSize(new Dimension(18, 18));
            textfields[k].setText("" + temp_custom_palette[k][0]);
            textfields[k].getDocument().addDocumentListener(new DocumentListener() {

                int temp2 = k;

                @Override
                public void insertUpdate(DocumentEvent e) {

                    try {

                        int temp3 = Integer.parseInt(textfields[temp2].getText());

                        if(temp3 < 0 || temp3 > 22) {
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                            return;
                        }

                        temp_custom_palette[temp2][0] = temp3;


                        temp_custom_palette[temp2][1] = labels[temp2].getBackground().getRed();
                        temp_custom_palette[temp2][2] = labels[temp2].getBackground().getGreen();
                        temp_custom_palette[temp2][3] = labels[temp2].getBackground().getBlue();

                        Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                        paintGradientAndGraph(c);
                    }
                    catch(ArithmeticException ex) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                    catch(NumberFormatException ex) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    try {

                        int temp3 = Integer.parseInt(textfields[temp2].getText());

                        if(temp3 < 0 || temp3 > 22) {
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                            return;
                        }

                        temp_custom_palette[temp2][0] = temp3;

                        temp_custom_palette[temp2][1] = labels[temp2].getBackground().getRed();
                        temp_custom_palette[temp2][2] = labels[temp2].getBackground().getGreen();
                        temp_custom_palette[temp2][3] = labels[temp2].getBackground().getBlue();

                        Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                        paintGradientAndGraph(c);
                    }
                    catch(ArithmeticException ex) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                    catch(NumberFormatException ex) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
            hues.add(textfields[k]);
        }

        JButton palette_ok = new JButton("Ok");
        palette_ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int temp2 = 0;
                for(int m = 0; m < textfields.length; m++) {
                    try {
                        temp2 = Integer.parseInt(textfields[m].getText());
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(custom_palette_editor, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(temp2 < 0 || temp2 > 22) {
                        JOptionPane.showMessageDialog(custom_palette_editor, "The hues values must between 1 and 22\n for that color to be included in the palette,\n or 0 for that color not to be included.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    temp_custom_palette[m][0] = temp2;

                }

                try {
                    temp2 = Integer.parseInt(offset_textfield.getText());
                }
                catch(Exception ex) {
                    JOptionPane.showMessageDialog(custom_palette_editor, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(temp2 < 0) {
                    JOptionPane.showMessageDialog(custom_palette_editor, "Offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                boolean same_colors = false;

                for(int m = 0; m < c.length; m++) {
                    if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                        same_colors = true;
                        break;
                    }
                }

                if(same_colors) {
                    int reply = JOptionPane.showConfirmDialog(custom_palette_editor, "The palette contains same adjacent colors.\nThis might cause errors in the created images if you are using \nthe boundary tracing algorithm along with color cycling,\nor if you want to apply a new palette later.", "Warning!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                    if(reply == JOptionPane.CANCEL_OPTION) {
                        return;
                    }
                }


                for(k = 0; k < custom_palette.length; k++) {
                    for(int j = 0; j < custom_palette[0].length; j++) {
                        custom_palette[k][j] = temp_custom_palette[k][j];
                    }
                }

                color_interpolation = combo_box_color_interp.getSelectedIndex();
                color_space = combo_box_color_space.getSelectedIndex();
                reversed_palette = check_box_reveres_palette.isSelected();
                temp_color_cycling_location = color_cycling_location = temp2;
                random_palette_algorithm = combo_box_random_palette_alg.getSelectedIndex();
                equal_hues = same_hues.isSelected();

                setEnabled(true);
                setPalette(number);
                custom_palette_editor.dispose();

            }
        });

        buttons.add(palette_ok);

        JButton palette_cancel = new JButton("Cancel");
        palette_cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);
                if(color_choice != number) {
                    palette[number].setSelected(false);
                }
                else {
                    palette[number].setSelected(true);
                }
                custom_palette_editor.dispose();

                temp_color_cycling_location = temp_color_cycling_location_local;

            }
        });

        buttons.add(palette_cancel);

        JMenuBar menubar2 = new JMenuBar();
        JMenu file2 = new JMenu("File");

        JMenuItem reset_palette = new JMenuItem("Reset Palette", getIcon("/fractalzoomer/icons/palette_reset.png"));

        JMenu default_palettes = new JMenu("Default Palettes");
        default_palettes.setIcon(getIcon("/fractalzoomer/icons/palette_default.png"));

        JMenuItem random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette_random.png"));

        JMenuItem save_palette = new JMenuItem("Save Palette As...", getIcon("/fractalzoomer/icons/palette_save.png"));

        JMenuItem load_palette = new JMenuItem("Load Palette", getIcon("/fractalzoomer/icons/palette_load.png"));

        reset_palette.setToolTipText("Resets the palette.");
        random_palette.setToolTipText("Randomizes the palette.");
        save_palette.setToolTipText("Saves a user made palette.");
        load_palette.setToolTipText("Loads a user made palette.");

        reset_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                int[][] temp_custom_palette1 = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

                for(int m = 0; m < labels.length; m++) {
                    temp_custom_palette[m][0] = temp_custom_palette1[m][0];
                    temp_custom_palette[m][1] = temp_custom_palette1[m][1];
                    temp_custom_palette[m][2] = temp_custom_palette1[m][2];
                    temp_custom_palette[m][3] = temp_custom_palette1[m][3];
                    labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                    textfields[m].setText("" + temp_custom_palette[m][0]);
                }

                combo_box_color_interp.setSelectedIndex(INTERPOLATION_LINEAR);
                combo_box_color_space.setSelectedIndex(COLOR_SPACE_RGB);
                check_box_reveres_palette.setSelected(false);
                temp_color_cycling_location = 0;
                offset_textfield.setText("" + temp_color_cycling_location);

                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                paintGradientAndGraph(c);
            }
        });


        random_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Random generator = new Random(System.currentTimeMillis());
                Color[] c;

                double golden_ratio_conjugate = 0.6180339887498949;//(1 + Math.sqrt(5)) / 2.0 - 1;

                boolean same_colors;

                int hues = generator.nextInt(12) + 7;

                if(combo_box_random_palette_alg.getSelectedIndex() == 0) {
                    //float hue = generator.nextFloat();
                    double brightness = generator.nextFloat();

                    int counter = 0;

                    ColorSpaceConverter con = new ColorSpaceConverter();

                    do {
                        for(int m = 0; m < temp_custom_palette.length; m++) {
                            //hue += golden_ratio_conjugate;
                            //hue %= 1;
                            brightness += golden_ratio_conjugate;
                            brightness %= 1;

                            int[] res = null;

                            if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_LCH) {
                                res = con.LCHtoRGB(brightness * 100.0, generator.nextDouble() * 140.0, generator.nextDouble() * 360.0);
                            }
                            else if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_LAB) {
                                res = con.LABtoRGB(brightness * 100.0, (2 * generator.nextDouble() - 1) * 100, (2 * generator.nextDouble() - 1) * 100);
                            }
                            else {
                                res = con.HSBtoRGB(generator.nextDouble(), generator.nextDouble(), brightness);
                            }

                            temp_custom_palette[m][0] = same_hues.isSelected() ? hues : generator.nextInt(12) + 7;
                            temp_custom_palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                            temp_custom_palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                            temp_custom_palette[m][3] = ColorSpaceConverter.clamp(res[2]);
                        }

                        same_colors = false;

                        c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                        for(int m = 0; m < c.length; m++) {
                            if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                                same_colors = true;
                                break;
                            }
                        }

                        if(counter == 300) {
                            break;
                        }

                        counter++;
                    } while(same_colors);
                }
                else if(combo_box_random_palette_alg.getSelectedIndex() == 1) {
                    int counter = 0;

                    double random_a = generator.nextDouble() * 1000;
                    double random_b = generator.nextDouble() * 1000;
                    double random_c = generator.nextDouble() * 1000;

                    double a_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
                    double b_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
                    double c_coeff = generator.nextInt(11) + generator.nextDouble() + 1;

                    ColorSpaceConverter con = new ColorSpaceConverter();

                    do {
                        for(int m = 0; m < temp_custom_palette.length; m++) {
                            temp_custom_palette[m][0] = same_hues.isSelected() ? hues : generator.nextInt(12) + 7;

                            int[] res = null;

                            if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_HSB) {
                                res = con.HSBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                            }
                            else if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_RYB) {
                                res = con.RYBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                            }
                            else if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_LAB) {
                                res = con.LABtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (100 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b))), (100 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c))));
                            }
                            else if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_XYZ) {
                                res = con.XYZtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (50 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (50 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                            }
                            else if(combo_box_color_space.getSelectedIndex() == COLOR_SPACE_LCH) {
                                res = con.LCHtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (70 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (180 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                            }
                            else {
                                res = new int[3];
                                res[0] = (int)(127.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1) + 0.5);
                                res[1] = (int)(127.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1) + 0.5);
                                res[2] = (int)(127.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1) + 0.5);
                            }

                            temp_custom_palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                            temp_custom_palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                            temp_custom_palette[m][3] = ColorSpaceConverter.clamp(res[2]);
                        }

                        same_colors = false;

                        c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                        for(int m = 0; m < c.length; m++) {
                            if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                                same_colors = true;
                                break;
                            }
                        }

                        if(counter == 300) {
                            break;
                        }

                        counter++;
                    } while(same_colors);
                }
                else {
                    int counter = 0;

                    do {

                        List<Color> list = ColorGenerator.generate(600, generator.nextInt(600), 0);
                        for(int m = 0; m < temp_custom_palette.length; m++) {
                            temp_custom_palette[m][0] = same_hues.isSelected() ? hues : generator.nextInt(12) + 7;
                            temp_custom_palette[m][1] = list.get(m).getRed();
                            temp_custom_palette[m][2] = list.get(m).getGreen();
                            temp_custom_palette[m][3] = list.get(m).getBlue();
                        }

                        same_colors = false;

                        c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                        for(int m = 0; m < c.length; m++) {
                            if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                                same_colors = true;
                                break;
                            }
                        }

                        if(counter == 300) {
                            break;
                        }

                        counter++;
                    } while(same_colors);
                }

                for(int m = 0; m < labels.length; m++) {
                    labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                    textfields[m].setText("" + temp_custom_palette[m][0]);
                }

                paintGradientAndGraph(c);
            }
        });

        save_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for(int m = 0; m < textfields.length; m++) {
                    int temp2;
                    try {
                        temp2 = Integer.parseInt(textfields[m].getText());

                        if(temp2 > 0 && temp2 < 23) {
                            temp_custom_palette[m][0] = temp2;
                        }
                        else {
                            temp_custom_palette[m][0] = 0;
                        }
                    }
                    catch(Exception ex) {
                        temp_custom_palette[m][0] = 0;
                    }
                }
                savePalette();

            }
        });

        load_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                loadPalette();

                for(int m = 0; m < labels.length; m++) {
                    labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                    textfields[m].setText("" + temp_custom_palette[m][0]);
                }

                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                paintGradientAndGraph(c);

            }
        });


        editor_palettes = new JMenuItem[coloring_option.length - 1];

        for(k = 0; k < editor_palettes.length; k++) {
            editor_palettes[k] = new JMenuItem(coloring_option[k]);
            editor_palettes[k].addActionListener(new ActionListener() {

                int temp = k;

                public void actionPerformed(ActionEvent e) {

                    for(int m = 0; m < labels.length; m++) {
                        temp_custom_palette[m][0] = editor_default_palettes[temp][m][0];
                        temp_custom_palette[m][1] = editor_default_palettes[temp][m][1];
                        temp_custom_palette[m][2] = editor_default_palettes[temp][m][2];
                        temp_custom_palette[m][3] = editor_default_palettes[temp][m][3];
                        labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                        textfields[m].setText("" + temp_custom_palette[m][0]);
                    }

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                    paintGradientAndGraph(c);

                }
            });
            default_palettes.add(editor_palettes[k]);
        }

        file2.add(reset_palette);
        file2.add(default_palettes);
        file2.add(random_palette);
        file2.add(save_palette);
        file2.add(load_palette);
        menubar2.add(file2);

        custom_palette_editor.setJMenuBar(menubar2);

        colors = new BufferedImage(732, 36, BufferedImage.TYPE_INT_ARGB);

        colors2 = new BufferedImage(732, 100, BufferedImage.TYPE_INT_ARGB);

        Color[] c = CustomPalette.getPalette(temp_custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location);

        try {
            Graphics2D g = colors.createGraphics();
            for(int i = 0; i < c.length; i++) {
                g.setColor(c[i]);
                g.fillRect(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight());
            }
        }
        catch(Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        try {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(int i = 0; i < c.length; i++) {
                g.setColor(Color.RED);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getRed()), (i + 1) * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getRed()));

                g.setColor(Color.GREEN);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 20 + 2 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getGreen()), (i + 1) * colors2.getWidth() / c.length, 20 + 2 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getGreen()));

                g.setColor(Color.BLUE);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 30 + 3 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getBlue()), (i + 1) * colors2.getWidth() / c.length, 30 + 3 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getBlue()));
            }
        }
        catch(Exception ex) {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
        }


        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(290, 60));
        options_panel.setLayout(new FlowLayout());

        check_box_reveres_palette = new JCheckBox("Reverse Palette");
        check_box_reveres_palette.setSelected(reversed_palette);
        check_box_reveres_palette.setFocusable(false);
        check_box_reveres_palette.setToolTipText("Reverses the current palette.");

        check_box_reveres_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                paintGradientAndGraph(c);
            }
        });

        options_panel.add(check_box_reveres_palette);


        JLabel offset_label = new JLabel(" Palette Offset");

        options_panel.add(offset_label);

        offset_textfield = new JTextField();
        offset_textfield.setPreferredSize(new Dimension(60, 18));
        offset_textfield.setText("" + temp_color_cycling_location);
        offset_textfield.setToolTipText("Adds an offset to the current palette.");
        offset_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                try {

                    int temp3 = Integer.parseInt(offset_textfield.getText());

                    if(temp3 < 0) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                        return;
                    }

                    temp_color_cycling_location = temp3;

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                    paintGradientAndGraph(c);
                }
                catch(ArithmeticException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
                catch(NumberFormatException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {

                    int temp3 = Integer.parseInt(offset_textfield.getText());

                    if(temp3 < 0) {
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                        return;
                    }

                    temp_color_cycling_location = temp3;

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                    paintGradientAndGraph(c);
                }
                catch(ArithmeticException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
                catch(NumberFormatException ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


        options_panel.add(offset_textfield);


        JPanel color_space_panel = new JPanel();
        color_space_panel.setPreferredSize(new Dimension(110, 60));
        color_space_panel.setLayout(new FlowLayout());


        String[] color_space_str = {"RGB", "HSB", "Exp", "Square", "Sqrt", "RYB", "LAB", "XYZ", "LCH"};

        combo_box_color_space = new JComboBox(color_space_str);
        combo_box_color_space.setSelectedIndex(color_space);
        combo_box_color_space.setFocusable(false);
        combo_box_color_space.setToolTipText("Sets the color space option.");

        combo_box_color_space.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                paintGradientAndGraph(c);
            }
        });

        color_space_panel.add(combo_box_color_space);

        JPanel color_interp_panel = new JPanel();
        color_interp_panel.setPreferredSize(new Dimension(135, 60));
        color_interp_panel.setLayout(new FlowLayout());

        String[] color_interp_str = {"Linear", "Cosine", "Acceleration", "Deceleration", "Exponential", "Catmull-Rom", "Catmull-Rom 2"};

        combo_box_color_interp = new JComboBox(color_interp_str);
        combo_box_color_interp.setSelectedIndex(color_interpolation);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the color interpolation option.");


        combo_box_color_interp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);

                paintGradientAndGraph(c);
            }
        });

        color_interp_panel.add(combo_box_color_interp);


        JPanel random_palette_alg = new JPanel();
        random_palette_alg.setPreferredSize(new Dimension(209, 60));
        random_palette_alg.setLayout(new FlowLayout());

        String[] random_palette_alg_str = {"Golden Ratio", "Waves", "Euclidean Distance"};

        combo_box_random_palette_alg = new JComboBox(random_palette_alg_str);
        combo_box_random_palette_alg.setSelectedIndex(random_palette_algorithm);
        combo_box_random_palette_alg.setFocusable(false);
        combo_box_random_palette_alg.setToolTipText("Sets the random palette algorithm.");


        same_hues = new JCheckBox("Equal");
        same_hues.setSelected(equal_hues);
        same_hues.setFocusable(false);
        same_hues.setToolTipText("Every color will have the same numbers of hues.");

        random_palette_alg.add(combo_box_random_palette_alg);
        random_palette_alg.add(same_hues);

        gradient = new JLabel(new ImageIcon(colors));
        gradient.setPreferredSize(new Dimension(colors.getWidth(), colors.getHeight()));
        gradient.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        gradient.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    Color temp_color = new Color(colors.getRGB((int)gradient.getMousePosition().getX(), (int)gradient.getMousePosition().getY()));
                    String rr = Integer.toHexString(temp_color.getRed());
                    String bb = Integer.toHexString(temp_color.getBlue());
                    String gg = Integer.toHexString(temp_color.getGreen());

                    rr = rr.length() == 1 ? "0" + rr : rr;
                    gg = gg.length() == 1 ? "0" + gg : gg;
                    bb = bb.length() == 1 ? "0" + bb : bb;


                    gradient.setToolTipText("<html>R: " + temp_color.getRed() + " G: " + temp_color.getGreen() + " B: " + temp_color.getBlue() + "<br>"
                            + "#" + rr + gg + bb + "</html>");


                }
                catch(Exception ex) {
                }
            }
        });


        graph = new JLabel(new ImageIcon(colors2));
        graph.setPreferredSize(new Dimension(colors2.getWidth(), colors2.getHeight()));
        graph.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JPanel palette_panel = new JPanel();
        palette_panel.setPreferredSize(new Dimension(760, 178));

        palette_panel.add(gradient);
        palette_panel.add(graph);

        editor_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Custom Palette Editor", TitledBorder.CENTER, TitledBorder.CENTER));
        palette_colors.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Colors", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        hues.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Hues", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        color_space_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Space", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        color_interp_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Interpolation", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        random_palette_alg.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Random Algorithm", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        palette_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Palette", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        options_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        editor_panel.add(palette_colors);
        editor_panel.add(hues);
        editor_panel.add(options_panel);
        editor_panel.add(color_space_panel);
        editor_panel.add(color_interp_panel);
        editor_panel.add(random_palette_alg);
        editor_panel.add(palette_panel);
        custom_palette_editor.add(editor_panel);
        custom_palette_editor.add(buttons);
        custom_palette_editor.setVisible(true);

    }

    private void savePalette() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Palette (*.fzp)", "fzp"));

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("palette " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".fzp"));

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

        int returnVal = file_chooser.showDialog(ptr, "Save Palette");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            ObjectOutputStream file_temp = null;

            try {
                file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
                SettingsPalette settings_palette = new SettingsPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location);
                file_temp.writeObject(settings_palette);
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

    private void loadPalette() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Palette (*.fzp)", "fzp"));

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

        int returnVal = file_chooser.showDialog(ptr, "Load Palette");

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            ObjectInputStream file_temp = null;
            try {
                file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
                SettingsPalette settings_palette = (SettingsPalette)file_temp.readObject();

                temp_custom_palette = settings_palette.getCustomPalette();
                combo_box_color_interp.setSelectedIndex(settings_palette.getColorInterpolation());
                combo_box_color_space.setSelectedIndex(settings_palette.getColorSpace());
                check_box_reveres_palette.setSelected(settings_palette.getReveresedPalette());
                temp_color_cycling_location = settings_palette.getOffset();
                offset_textfield.setText("" + temp_color_cycling_location);
            }
            catch(IOException ex) {
                JOptionPane.showMessageDialog(custom_palette_editor, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
            catch(ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(custom_palette_editor, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }

            try {
                file_temp.close();
            }
            catch(Exception ex) {
            }
        }

    }

    private void paintGradientAndGraph(Color[] c) {

        try {
            Graphics2D g = colors.createGraphics();
            for(int i = 0; i < c.length; i++) {
                g.setColor(c[i]);
                g.fillRect(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight());
            }
        }
        catch(Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient.repaint();

        try {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for(int i = 0; i < c.length; i++) {
                g.setColor(Color.RED);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getRed()), (i + 1) * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getRed()));

                g.setColor(Color.GREEN);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 20 + 2 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getGreen()), (i + 1) * colors2.getWidth() / c.length, 20 + 2 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getGreen()));

                g.setColor(Color.BLUE);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 30 + 3 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[i].getBlue()), (i + 1) * colors2.getWidth() / c.length, 30 + 3 * colors2.getHeight() / 5 - (int)(((colors2.getHeight() / 5) / 255.0) * c[(i + 1) % c.length].getBlue()));
            }
        }
        catch(Exception ex) {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
        }

        graph.repaint();
    }

    public boolean getJuliaMap() {

        return julia_map;

    }

    private void createThreadsJuliaMap() {

        ThreadDraw.resetAtomics();

        int n = julia_grid_first_dimension;

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(color_choice != palette.length - 1) {
                    threads[i][j] = new Palette(color_choice, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                else {
                    threads[i][j] = new CustomPalette(custom_palette, color_interpolation, color_space, reversed_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
            }
        }

    }

    private void increaseIterations() {

        if(max_iterations >= 100000) {
            return;
        }
        max_iterations++;

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

        setOptions(false);

        progress.setValue(0);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

    private void setPerturbation() {

        if(!perturbation_opt.isSelected()) {
            perturbation = false;

            if(function == 0 && !burning_ship && !init_val && !mandel_grass) {
                exterior_de_opt.setEnabled(true);
            }

            if(function == 0 && !init_val) {
                out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                out_coloring_modes[ATOM_DOMAIN].setEnabled(true);
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!init_val && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }
        else {
            if(backup_orbit != null && orbit) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();


            JTextField field_real = new JTextField();
            field_real.setText("" + perturbation_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();
            field_imaginary.setText("" + perturbation_vals[1]);

            Object[] message = {
                " ",
                "Set the real and imaginary part of the perturbation.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                " ",};


            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Perturbation", JOptionPane.OK_CANCEL_OPTION);

            double temp, temp2;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp = Double.parseDouble(field_real.getText());
                    temp2 = Double.parseDouble(field_imaginary.getText());
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

            perturbation_vals[0] = temp == 0.0 ? 0.0 : temp;
            perturbation_vals[1] = temp2 == 0.0 ? 0.0 : temp2;

            perturbation = true;
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            exterior_de_opt.setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
            out_coloring_modes[ATOM_DOMAIN].setEnabled(false);
        }

        defaultFractalSettings();

    }

    private void setInitialValue() {

        if(!init_val_opt.isSelected()) {
            init_val = false;

            if(function == 0 && !burning_ship && !mandel_grass && !perturbation) {
                exterior_de_opt.setEnabled(true);
            }

            if(function == 0 && !perturbation) {
                out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                out_coloring_modes[ATOM_DOMAIN].setEnabled(true);
            }

            if(out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && out_coloring_algorithm != ESCAPE_TIME_GRID && out_coloring_algorithm != DISTANCE_ESTIMATOR && out_coloring_algorithm != ATOM_DOMAIN && !exterior_de && out_coloring_algorithm != BIOMORPH && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && out_coloring_algorithm != ITERATIONS_PLUS_RE && out_coloring_algorithm != ITERATIONS_PLUS_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && !perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if(!perturbation && out_coloring_algorithm != DISTANCE_ESTIMATOR && !exterior_de && out_coloring_algorithm != ATOM_DOMAIN) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }
        else {
            if(backup_orbit != null && orbit) {
                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
            }
            main_panel.repaint();

            JTextField field_real = new JTextField();
            field_real.setText("" + initial_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();
            field_imaginary.setText("" + initial_vals[1]);

            Object[] message = {
                " ",
                "Set the real and imaginary part of the initial value.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                " ",};


            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Initial Value", JOptionPane.OK_CANCEL_OPTION);

            double temp, temp2;

            if(res == JOptionPane.OK_OPTION) {
                try {
                    temp = Double.parseDouble(field_real.getText());
                    temp2 = Double.parseDouble(field_imaginary.getText());
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

            initial_vals[0] = temp == 0.0 ? 0.0 : temp;
            initial_vals[1] = temp2 == 0.0 ? 0.0 : temp2;

            init_val = true;
            julia_opt.setEnabled(false);
            julia_button.setEnabled(false);
            julia_map_opt.setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            exterior_de_opt.setEnabled(false);
            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
            out_coloring_modes[ATOM_DOMAIN].setEnabled(false);
        }

        defaultFractalSettings();

    }

    private void showCHMHelpFile() {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/help/Fractal_Zoomer_Help.chm").openStream();
            File exeTempFile = File.createTempFile("temp", ".chm");
            FileOutputStream out = new FileOutputStream(exeTempFile);
            byte[] temp = new byte[32768];
            int rc;

            while((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();
            exeTempFile.deleteOnExit();
            Desktop.getDesktop().open(exeTempFile);
        }
        catch(Exception ex) {
        }

    }

    private void randomPalette() {

        Random generator = new Random(System.currentTimeMillis());

        Color[] c;

        boolean same_colors;

        double golden_ratio_conjugate = 0.6180339887498949;//(1 + Math.sqrt(5)) / 2.0 - 1;

        int hues = generator.nextInt(12) + 7;

        if(random_palette_algorithm == 0) {
            //float hue = generator.nextFloat();
            double brightness = generator.nextDouble();

            int counter = 0;

            ColorSpaceConverter con = new ColorSpaceConverter();

            do {
                for(int m = 0; m < custom_palette.length; m++) {
                    //hue += golden_ratio_conjugate;
                    //hue %= 1;
                    brightness += golden_ratio_conjugate;
                    brightness %= 1;

                    int[] res = null;

                    if(color_space == COLOR_SPACE_LCH) {
                        res = con.LCHtoRGB(brightness * 100.0, generator.nextDouble() * 140.0, generator.nextDouble() * 360.0);
                    }
                    else if(color_space == COLOR_SPACE_LAB) {
                        res = con.LABtoRGB(brightness * 100.0, (2 * generator.nextDouble() - 1) * 100, (2 * generator.nextDouble() - 1) * 100);
                    }
                    else {
                        res = con.HSBtoRGB(generator.nextDouble(), generator.nextDouble(), brightness);
                    }

                    custom_palette[m][0] = equal_hues ? hues : generator.nextInt(12) + 7;
                    custom_palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                    custom_palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                    custom_palette[m][3] = ColorSpaceConverter.clamp(res[2]);
                }

                same_colors = false;

                c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location);

                for(int m = 0; m < c.length; m++) {
                    if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                        same_colors = true;
                        break;
                    }
                }

                if(counter == 300) {
                    break;
                }

                counter++;
            } while(same_colors);
        }
        else if(random_palette_algorithm == 1) {
            int counter = 0;

            double random_a = generator.nextDouble() * 1000;
            double random_b = generator.nextDouble() * 1000;
            double random_c = generator.nextDouble() * 1000;

            double a_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
            double b_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
            double c_coeff = generator.nextInt(11) + generator.nextDouble() + 1;

            ColorSpaceConverter con = new ColorSpaceConverter();

            do {
                for(int m = 0; m < custom_palette.length; m++) {
                    custom_palette[m][0] = equal_hues ? hues : generator.nextInt(12) + 7;

                    int[] res = null;

                    if(color_space == COLOR_SPACE_HSB) {
                        res = con.HSBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                    }
                    else if(color_space == COLOR_SPACE_RYB) {
                        res = con.RYBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                    }
                    else if(color_space == COLOR_SPACE_LAB) {
                        res = con.LABtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (100 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b))), (100 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c))));
                    }
                    else if(color_space == COLOR_SPACE_XYZ) {
                        res = con.XYZtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (50 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (50 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                    }
                    else if(color_space == COLOR_SPACE_LCH) {
                        res = con.LCHtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (70 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (180 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                    }
                    else {
                        res = new int[3];

                        res[0] = (int)(127.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1) + 0.5);
                        res[1] = (int)(127.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1) + 0.5);
                        res[2] = (int)(127.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1) + 0.5);
                    }

                    custom_palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                    custom_palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                    custom_palette[m][3] = ColorSpaceConverter.clamp(res[2]);
                }

                same_colors = false;

                c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location);

                for(int m = 0; m < c.length; m++) {
                    if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                        same_colors = true;
                        break;
                    }
                }

                if(counter == 300) {
                    break;
                }

                counter++;
            } while(same_colors);
        }
        else {
            int counter = 0;

            do {

                List<Color> list = ColorGenerator.generate(600, generator.nextInt(600), 0);
                for(int m = 0; m < custom_palette.length; m++) {
                    custom_palette[m][0] = equal_hues ? hues : generator.nextInt(12) + 7;
                    custom_palette[m][1] = list.get(m).getRed();
                    custom_palette[m][2] = list.get(m).getGreen();
                    custom_palette[m][3] = list.get(m).getBlue();
                }

                same_colors = false;

                c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location);

                for(int m = 0; m < c.length; m++) {
                    if(c[m].getRGB() == c[(m + 1) % c.length].getRGB()) {
                        same_colors = true;
                        break;
                    }
                }

                if(counter == 300) {
                    break;
                }

                counter++;
            } while(same_colors);
        }

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

    public int getNumberOfThreads() {

        return n * n;

    }

    public int getJuliaMapSlices() {

        return julia_grid_first_dimension * julia_grid_first_dimension;

    }

    private void filtersOptions() {

        if(backup_orbit != null && orbit) {
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        setEnabled(false);
        int filters_options_window_width = 430;
        int filters_options_window_height = 410;
        filters_options_frame = new JFrame("Filters Options");
        filters_options_frame.setIconImage(getIcon("/fractalzoomer/icons/filter_options.png").getImage());
        filters_options_frame.setLayout(new FlowLayout());
        filters_options_frame.setSize(filters_options_window_width, filters_options_window_height);
        filters_options_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (filters_options_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (filters_options_window_height / 2));
        filters_options_frame.setResizable(false);

        filters_options_frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                setEnabled(true);

                filters_options_frame.dispose();

            }
        });


        JPanel panel = new JPanel();

        panel.setPreferredSize(new Dimension(400, 330));

        JPanel[] panels = new JPanel[combo_boxes_filters.length];

        panels[ANTIALIASING] = new JPanel();
        String[] antialiasing_str = {"4x Samples", "8x Samples", "16x Samples", "24x Samples"};

        combo_boxes_filters[ANTIALIASING] = new JComboBox(antialiasing_str);
        combo_boxes_filters[ANTIALIASING].setSelectedIndex(combo_box_choices_filters[ANTIALIASING]);
        combo_boxes_filters[ANTIALIASING].setFocusable(false);
        combo_boxes_filters[ANTIALIASING].setToolTipText("Sets the sampled pixels number for the anti-aliasing.");
        panels[ANTIALIASING].add(combo_boxes_filters[ANTIALIASING]);
        panels[ANTIALIASING].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Anti-Aliasing", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[EDGE_DETECTION] = new JPanel();
        String[] edge_detection_str = {"Thin Edges", "Thick Edges"};

        combo_boxes_filters[EDGE_DETECTION] = new JComboBox(edge_detection_str);
        combo_boxes_filters[EDGE_DETECTION].setSelectedIndex(combo_box_choices_filters[EDGE_DETECTION]);
        combo_boxes_filters[EDGE_DETECTION].setFocusable(false);
        combo_boxes_filters[EDGE_DETECTION].setToolTipText("Sets the thickness of the edge.");
        panels[EDGE_DETECTION].add(combo_boxes_filters[EDGE_DETECTION]);
        panels[EDGE_DETECTION].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Edge Detection", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[SHARPNESS] = new JPanel();
        String[] sharpness_str = {"Sharpness Low", "Sharpness High"};

        combo_boxes_filters[SHARPNESS] = new JComboBox(sharpness_str);
        combo_boxes_filters[SHARPNESS].setSelectedIndex(combo_box_choices_filters[SHARPNESS]);
        combo_boxes_filters[SHARPNESS].setFocusable(false);
        combo_boxes_filters[SHARPNESS].setToolTipText("Sets the intensity of the sharpness.");
        panels[SHARPNESS].add(combo_boxes_filters[SHARPNESS]);
        panels[SHARPNESS].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Sharpness", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[EMBOSS] = new JPanel();
        String[] emboss_str = {"Emboss Grayscale", "Emboss Colored"};

        combo_boxes_filters[EMBOSS] = new JComboBox(emboss_str);
        combo_boxes_filters[EMBOSS].setSelectedIndex(combo_box_choices_filters[EMBOSS]);
        combo_boxes_filters[EMBOSS].setFocusable(false);
        combo_boxes_filters[EMBOSS].setToolTipText("Sets the color format of the emboss.");
        panels[EMBOSS].add(combo_boxes_filters[EMBOSS]);
        panels[EMBOSS].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Emboss", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[CONTRAST_BRIGHTNESS] = new JPanel();
        String[] constrast_brightness_str = {"Contrast", "Brightness", "Contrast & Brightness"};

        combo_boxes_filters[CONTRAST_BRIGHTNESS] = new JComboBox(constrast_brightness_str);
        combo_boxes_filters[CONTRAST_BRIGHTNESS].setSelectedIndex(combo_box_choices_filters[CONTRAST_BRIGHTNESS]);
        combo_boxes_filters[CONTRAST_BRIGHTNESS].setFocusable(false);
        combo_boxes_filters[CONTRAST_BRIGHTNESS].setToolTipText("Sets contrast and brightness options.");
        panels[CONTRAST_BRIGHTNESS].add(combo_boxes_filters[CONTRAST_BRIGHTNESS]);
        panels[CONTRAST_BRIGHTNESS].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Contrast/Brightness", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        panels[COLOR_TEMPERATURE] = new JPanel();
        String[] color_temperature_str = {"2000K", "3000K", "4000K", "5000K", "10000K", "30000K"};

        combo_boxes_filters[COLOR_TEMPERATURE] = new JComboBox(color_temperature_str);
        combo_boxes_filters[COLOR_TEMPERATURE].setSelectedIndex(combo_box_choices_filters[COLOR_TEMPERATURE]);
        combo_boxes_filters[COLOR_TEMPERATURE].setFocusable(false);
        combo_boxes_filters[COLOR_TEMPERATURE].setToolTipText("Sets the temperature of the colors in Kelvin.");
        panels[COLOR_TEMPERATURE].add(combo_boxes_filters[COLOR_TEMPERATURE]);
        panels[COLOR_TEMPERATURE].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Temperature", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        panels[COLOR_CHANNEL_MASKING] = new JPanel();
        String[] mask_color_channel_str = {"Mask Red Channel", "Mask Green Channel", "Mask Blue Channel"};

        combo_boxes_filters[COLOR_CHANNEL_MASKING] = new JComboBox(mask_color_channel_str);
        combo_boxes_filters[COLOR_CHANNEL_MASKING].setSelectedIndex(combo_box_choices_filters[COLOR_CHANNEL_MASKING]);
        combo_boxes_filters[COLOR_CHANNEL_MASKING].setFocusable(false);
        combo_boxes_filters[COLOR_CHANNEL_MASKING].setToolTipText("Sets the color channel that will be removed.");
        panels[COLOR_CHANNEL_MASKING].add(combo_boxes_filters[COLOR_CHANNEL_MASKING]);
        panels[COLOR_CHANNEL_MASKING].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Mask Color Channel", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[COLOR_CHANNEL_SWAPPING] = new JPanel();
        String[] color_channel_swapping_str = {"Color Channel Swapping RBG", "Color Channel Swapping GRB", "Color Channel Swapping GBR", "Color Channel Swapping BGR", "Color Channel Swapping BRG"};

        combo_boxes_filters[COLOR_CHANNEL_SWAPPING] = new JComboBox(color_channel_swapping_str);
        combo_boxes_filters[COLOR_CHANNEL_SWAPPING].setSelectedIndex(combo_box_choices_filters[COLOR_CHANNEL_SWAPPING]);
        combo_boxes_filters[COLOR_CHANNEL_SWAPPING].setFocusable(false);
        combo_boxes_filters[COLOR_CHANNEL_SWAPPING].setToolTipText("Sets the color swapping format.");
        panels[COLOR_CHANNEL_SWAPPING].add(combo_boxes_filters[COLOR_CHANNEL_SWAPPING]);
        panels[COLOR_CHANNEL_SWAPPING].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Channel Swapping", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[COLOR_CHANNEL_MIXING] = new JPanel();
        String[] color_channel_mixing_str = {"Mix Green to Red", "Mix Blue to Red", "Mix Red to Green", "Mix Blue to Green", "Mix Red to Blue", "Mix Green to Blue"};

        combo_boxes_filters[COLOR_CHANNEL_MIXING] = new JComboBox(color_channel_mixing_str);
        combo_boxes_filters[COLOR_CHANNEL_MIXING].setSelectedIndex(combo_box_choices_filters[COLOR_CHANNEL_MIXING]);
        combo_boxes_filters[COLOR_CHANNEL_MIXING].setFocusable(false);
        combo_boxes_filters[COLOR_CHANNEL_MIXING].setToolTipText("Sets the color mixing options.");
        panels[COLOR_CHANNEL_MIXING].add(combo_boxes_filters[COLOR_CHANNEL_MIXING]);
        panels[COLOR_CHANNEL_MIXING].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Channel Mixing", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panels[GRAYSCALE] = new JPanel();
        String[] grayscale_str = {"Grayscale Luminance NTSC", "Grayscale Luminance HDTV", "Grayscale Average", "Grayscale Lightness"};

        combo_boxes_filters[GRAYSCALE] = new JComboBox(grayscale_str);
        combo_boxes_filters[GRAYSCALE].setSelectedIndex(combo_box_choices_filters[GRAYSCALE]);
        combo_boxes_filters[GRAYSCALE].setFocusable(false);
        combo_boxes_filters[GRAYSCALE].setToolTipText("Sets the grayscale format.");
        panels[GRAYSCALE].add(combo_boxes_filters[GRAYSCALE]);
        panels[GRAYSCALE].setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Grayscale", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        panel.setLayout(new GridLayout(5, 2));

        panel.add(panels[ANTIALIASING]);
        panel.add(panels[EDGE_DETECTION]);
        panel.add(panels[SHARPNESS]);
        panel.add(panels[EMBOSS]);
        panel.add(panels[CONTRAST_BRIGHTNESS]);
        panel.add(panels[COLOR_TEMPERATURE]);
        panel.add(panels[COLOR_CHANNEL_MASKING]);
        panel.add(panels[COLOR_CHANNEL_SWAPPING]);
        panel.add(panels[COLOR_CHANNEL_MIXING]);
        panel.add(panels[GRAYSCALE]);

        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Filters", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));


        JPanel buttons = new JPanel();

        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                for(int k = 0; k < combo_box_choices_filters.length; k++) {
                    if(combo_boxes_filters[k] != null) {
                        combo_box_choices_filters[k] = combo_boxes_filters[k].getSelectedIndex();
                    }
                }


                switch (combo_box_choices_filters[ANTIALIASING]) {
                    case 0:
                        filters_options_vals[ANTIALIASING] = 4;
                        break;
                    case 1:
                        filters_options_vals[ANTIALIASING] = 8;
                        break;
                    case 2:
                        filters_options_vals[ANTIALIASING] = 16;
                        break;
                    case 3:
                        filters_options_vals[ANTIALIASING] = 24;
                        break;
                }

                filters_options_vals[EDGE_DETECTION] = combo_box_choices_filters[EDGE_DETECTION];
                filters_options_vals[SHARPNESS] = combo_box_choices_filters[SHARPNESS];
                filters_options_vals[EMBOSS] = combo_box_choices_filters[EMBOSS];
                filters_options_vals[CONTRAST_BRIGHTNESS] = combo_box_choices_filters[CONTRAST_BRIGHTNESS];

                switch (combo_box_choices_filters[COLOR_TEMPERATURE]) {
                    case 0:
                        filters_options_vals[COLOR_TEMPERATURE] = 2000;
                        break;
                    case 1:
                        filters_options_vals[COLOR_TEMPERATURE] = 3000;
                        break;
                    case 2:
                        filters_options_vals[COLOR_TEMPERATURE] = 4000;
                        break;
                    case 3:
                        filters_options_vals[COLOR_TEMPERATURE] = 5000;
                        break;
                    case 4:
                        filters_options_vals[COLOR_TEMPERATURE] = 10000;
                        break;
                    case 5:
                        filters_options_vals[COLOR_TEMPERATURE] = 30000;
                        break;
                }


                filters_options_vals[COLOR_CHANNEL_MASKING] = combo_box_choices_filters[COLOR_CHANNEL_MASKING];
                filters_options_vals[COLOR_CHANNEL_SWAPPING] = combo_box_choices_filters[COLOR_CHANNEL_SWAPPING];

                filters_options_vals[COLOR_CHANNEL_MIXING] = combo_box_choices_filters[COLOR_CHANNEL_MIXING] + 1 + combo_box_choices_filters[COLOR_CHANNEL_MIXING] / 3;

                filters_options_vals[GRAYSCALE] = combo_box_choices_filters[GRAYSCALE];

                if(filters[ANTIALIASING] || filters[EDGE_DETECTION] || filters[SHARPNESS] || filters[EMBOSS] || filters[COLOR_CHANNEL_MASKING] || filters[COLOR_CHANNEL_SWAPPING] || filters[CONTRAST_BRIGHTNESS] || filters[GRAYSCALE] || filters[COLOR_TEMPERATURE] || filters[COLOR_CHANNEL_MIXING]) {
                    setOptions(false);

                    progress.setValue(0);

                    last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                    backup_orbit = null;

                    whole_image_done = false;

                    if(filters[ANTIALIASING]) {

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
                            createThreadsRotate3DModel();
                        }
                        else {
                            createThreadsPaletteAndFilter();
                        }

                        calculation_time = System.currentTimeMillis();

                        startThreads(n);
                    }
                }


                setEnabled(true);

                filters_options_frame.dispose();

            }
        });

        buttons.add(ok);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setEnabled(true);

                filters_options_frame.dispose();

            }
        });

        buttons.add(cancel);

        filters_options_frame.add(panel);
        filters_options_frame.add(buttons);

        filters_options_frame.requestFocus();

        filters_options_frame.setVisible(true);
    }

    private void set3DOption() {

        if(!d3_opt.isSelected()) {
            d3 = false;
            setOptions(false);

            d3_button.setSelected(false);

            d3_details_opt.setEnabled(false);

            boundary_tracing_opt.setEnabled(true);

            ThreadDraw.setArrays(image_size);

            progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            backup_orbit = null;

            whole_image_done = false;

            createThreads();

            calculation_time = System.currentTimeMillis();

            startThreads(n);


        }
        else {
            JTextField field = new JTextField();
            field.addAncestorListener(new RequestFocusListener());

            JTextField field2 = new JTextField();
            field2.setText("" + d3_height_scale);

            JTextField field3 = new JTextField();
            field3.setText("" + d3_height_offset);

            String[] method = {"Normal", "Wireframe", "Grid"};

            JComboBox draw_choice = new JComboBox(method);
            draw_choice.setSelectedIndex(d3_draw_method);
            draw_choice.setToolTipText("Selects the drawing technique.");

            Object[] message3 = {
                " ",
                "Set the 3D detail level.",
                "3D Detail:",
                field,
                " ",
                "Set the scale of the height.",
                "Scale:",
                field2,
                " ",
                "Set the offset of the height.",
                "Offset:",
                field3,
                " ",
                "Select the drawing method.",
                draw_choice,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "3D", JOptionPane.OK_CANCEL_OPTION);

            if(res == JOptionPane.OK_OPTION) {
                try {
                    int temp = Integer.parseInt(field.getText());
                    double temp2 = Double.parseDouble(field2.getText());
                    double temp3 = Double.parseDouble(field3.getText());

                    if(temp < 10) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                        d3_opt.setSelected(false);
                        d3_button.setSelected(false);
                        return;
                    }
                    else {
                        if(temp > image_size) {
                            main_panel.repaint();
                            JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be lower than " + (image_size + 1) + ".", "Error!", JOptionPane.ERROR_MESSAGE);
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

                    detail = temp;
                    d3_height_scale = temp2;
                    d3_height_offset = temp3;

                    d3_draw_method = draw_choice.getSelectedIndex();

                    fiX = 0.64;
                    fiY = 0.82;

                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The 3D image will be created using " + detail + "x" + detail + " points.", "Info", JOptionPane.INFORMATION_MESSAGE);

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

                    last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            System.arraycopy(((DataBufferInt)backup_orbit.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);
        }
        main_panel.repaint();

        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + detail);

        JTextField field2 = new JTextField();
        field2.setText("" + d3_height_scale);

        JTextField field3 = new JTextField();
        field3.setText("" + d3_height_offset);

        String[] method = {"Normal", "Wireframe", "Grid"};

        JComboBox draw_choice = new JComboBox(method);
        draw_choice.setSelectedIndex(d3_draw_method);
        draw_choice.setToolTipText("Selects the drawing technique.");

        Object[] message3 = {
            " ",
            "Set the 3D detail level.",
            "3D Detail:",
            field,
            " ",
            "Set the scale of the height.",
            "Scale:",
            field2,
            " ",
            "Set the offset of the height.",
            "Offset:",
            field3,
            " ",
            "Select the drawing method.",
            draw_choice,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "3D", JOptionPane.OK_CANCEL_OPTION);

        if(res == JOptionPane.OK_OPTION) {
            try {
                int temp = Integer.parseInt(field.getText());
                double temp2 = Double.parseDouble(field2.getText());
                double temp3 = Double.parseDouble(field3.getText());

                if(temp < 10) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                else {
                    if(temp > image_size) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be lower than " + (image_size + 1) + ".", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if(temp2 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                detail = temp;
                d3_height_scale = temp2;
                d3_height_offset = temp3;

                d3_draw_method = draw_choice.getSelectedIndex();

                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The 3D image will be created using " + detail + "x" + detail + " points.", "Info", JOptionPane.INFORMATION_MESSAGE);

                ThreadDraw.set3DArrays(detail);

                progress.setMaximum((detail * detail) + (detail * detail / 100));

                setOptions(false);

                progress.setValue(0);

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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

        String[] method2 = {"Circle", "Square", "Rhombus"};

        JComboBox draw_choice2 = new JComboBox(method2);
        draw_choice2.setSelectedIndex(boundaries_type);
        draw_choice2.setToolTipText("Selects the type of boundary.");

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
                        JOptionPane.showMessageDialog(scroll_pane, "Boundaries number must be lower than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
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
            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
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
                    JOptionPane.showMessageDialog(scroll_pane, "Grid tiles number must be lower than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
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

    private void rootFindingMethodsSetEnabled(boolean option) {

        fractal_functions[NEWTON3].setEnabled(option);
        fractal_functions[NEWTON4].setEnabled(option);
        fractal_functions[NEWTONGENERALIZED3].setEnabled(option);
        fractal_functions[NEWTONGENERALIZED8].setEnabled(option);
        fractal_functions[NEWTONSIN].setEnabled(option);
        fractal_functions[NEWTONCOS].setEnabled(option);
        fractal_functions[NEWTONPOLY].setEnabled(option);
        fractal_functions[HALLEY3].setEnabled(option);
        fractal_functions[HALLEY4].setEnabled(option);
        fractal_functions[HALLEYGENERALIZED3].setEnabled(option);
        fractal_functions[HALLEYGENERALIZED8].setEnabled(option);
        fractal_functions[HALLEYSIN].setEnabled(option);
        fractal_functions[HALLEYCOS].setEnabled(option);
        fractal_functions[HALLEYPOLY].setEnabled(option);
        fractal_functions[SCHRODER3].setEnabled(option);
        fractal_functions[SCHRODER4].setEnabled(option);
        fractal_functions[SCHRODERGENERALIZED3].setEnabled(option);
        fractal_functions[SCHRODERGENERALIZED8].setEnabled(option);
        fractal_functions[SCHRODERSIN].setEnabled(option);
        fractal_functions[SCHRODERCOS].setEnabled(option);
        fractal_functions[SCHRODERPOLY].setEnabled(option);
        fractal_functions[HOUSEHOLDER3].setEnabled(option);
        fractal_functions[HOUSEHOLDER4].setEnabled(option);
        fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(option);
        fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(option);
        fractal_functions[HOUSEHOLDERSIN].setEnabled(option);
        fractal_functions[HOUSEHOLDERCOS].setEnabled(option);
        fractal_functions[HOUSEHOLDERPOLY].setEnabled(option);
        fractal_functions[SECANT3].setEnabled(option);
        fractal_functions[SECANT4].setEnabled(option);
        fractal_functions[SECANTGENERALIZED3].setEnabled(option);
        fractal_functions[SECANTGENERALIZED8].setEnabled(option);
        fractal_functions[SECANTCOS].setEnabled(option);
        fractal_functions[SECANTPOLY].setEnabled(option);
        fractal_functions[STEFFENSEN3].setEnabled(option);
        fractal_functions[STEFFENSEN4].setEnabled(option);
        fractal_functions[STEFFENSENGENERALIZED3].setEnabled(option);

    }

    private void optionsEnableShortcut() {
        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        out_coloring_modes[ATOM_DOMAIN].setEnabled(false);
        exterior_de_opt.setEnabled(false);
        if(out_coloring_algorithm != BIOMORPH) {
            out_coloring_modes[BIOMORPH].setEnabled(true);
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
        out_coloring_modes[ATOM_DOMAIN].setEnabled(false);
        exterior_de_opt.setEnabled(false);
        julia_opt.setEnabled(false);
        julia_button.setEnabled(false);
        julia_map_opt.setEnabled(false);
        out_coloring_modes[BIOMORPH].setEnabled(false);
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
        old_grid = grid;
        old_boundaries = boundaries;

        this.mode.setText(mode);

    }

    public static void main(String[] args) throws InterruptedException {

        MainWindow fractals = new MainWindow();
        Thread.currentThread().sleep(1300);
        fractals.setVisible(true);

    }
}
