/* Made by Kalonakis Chris, hrkalona@gmail.com */
/* Its obviously not a real time fractal zoomer, nor using high presicion (java are you kidding me?), but its a complete application */
/* Thanks to Josef Jelinek for the Supersampling code, and some of the palettes used. */
/* Thanks to Joel Yliluoma for the boundary tracing algorithm */
/* Thanks to David J. Eck for some of the palettes and the orbit concept */
/* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, and ofcourse from alot of google search */
/* Sorry for the absence of comments, this project was never supposed to reach this level! */
/* Also forgive me for the huge-packed main class, read above! */
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
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
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


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
  private boolean orbit;
  private boolean julia;
  private int plane_type;
  private boolean orbit_style;
  private boolean burning_ship;
  private boolean whole_image_done;
  private boolean fast_julia_filters;
  private boolean periodicity_checking;
  private boolean color_cycling;
  private boolean grid;
  private boolean julia_map;
  private boolean perturbation;
  private boolean boundary_tracing;
  private double[] coefficients;
  private ThreadDraw[][] threads;
  private DrawOrbit pixels_orbit;
  private double[] perturbation_vals;
  private double calculated;
  private double xCenter;
  private double yCenter;
  private double xJuliaCenter;
  private double yJuliaCenter;
  private int rotation;
  private double[] rotation_vals;
  private int function;
  private boolean first_seed;
  private double size;
  private int max_iterations;
  private int n;
  private int julia_grid_first_dimension;
  private int color_choice;
  private int color_cycling_location;
  private double bailout;
  private double z_exponent;
  private double zoom_factor;
  private double color_intensity;
  private int out_coloring_algorithm;
  public static int image_size;
  private long calculation_time;
  private String poly;
  private String[] coloring_option;
  private Color fractal_color;
  private Color orbit_color;
  private Color grid_color;
  private BufferedImage image;
  private BufferedImage fast_julia_image;
  private BufferedImage backup_orbit;
  private BufferedImage last_used;
  private MainWindow ptr;
  private MainPanel main_panel;
  private JScrollPane scroll_pane;
  private JMenuBar menubar;
  private JMenu file_menu;
  private JMenu options_menu;
  private JMenu colors_menu;
  private JMenu palette_menu;
  private JMenu roll_palette_menu;
  private JMenu iterations_menu;
  private JMenu rotation_menu;
  private JMenu filters_menu;
  private JMenu planes_menu;
  private JMenu optimizations_menu;
  private JMenu tools_options_menu;
  private JMenu orbit_menu;
  private JMenu orbit_style_menu;
  private JMenu tools_menu;
  private JMenu out_coloring_mode_menu;
  private JMenu help_menu;
  private JMenu fractal_functions_menu;
  private JMenu mandelbrot_type_functions;
  private JMenu magnet_type_functions;
  private JMenu root_finding_functions;
  private JMenu newton_type_functions;
  private JMenu halley_type_functions;
  private JMenu schroder_type_functions;
  private JMenu householder_type_functions;
  private JMenu barnsley_type_functions;
  private JMenuItem help_contents;
  private JMenuItem size_of_image;
  private JMenuItem iterations;
  private JMenuItem increase_iterations;
  private JMenuItem decrease_iterations;
  private JMenuItem set_rotation;
  private JMenuItem increase_rotation;
  private JMenuItem decrease_rotation;
  private JMenuItem thread_number;
  private JMenuItem bailout_number;
  private JMenuItem fract_color;
  private JMenuItem color_intens;
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
  private JMenuItem grid_color_opt;
  private JCheckBoxMenuItem boundary_tracing_opt;
  private JCheckBoxMenuItem anti_aliasing_opt;  
  private JCheckBoxMenuItem edges_opt;
  private JCheckBoxMenuItem edges2_opt;
  private JCheckBoxMenuItem emboss_opt;
  private JCheckBoxMenuItem invert_colors_opt;
  private JCheckBoxMenuItem sharpness_opt;
  private JCheckBoxMenuItem embosscolored_opt;
  private JCheckBoxMenuItem orbit_opt;
  private JCheckBoxMenuItem julia_opt;
  private JCheckBoxMenuItem fast_julia_filters_opt;
  private JCheckBoxMenuItem periodicity_checking_opt;
  private JCheckBoxMenuItem burning_ship_opt;
  private JCheckBoxMenuItem color_cycling_opt;
  private JCheckBoxMenuItem julia_map_opt;
  private JCheckBoxMenuItem grid_opt;
  private JCheckBoxMenuItem perturbation_opt; 
  private JRadioButtonMenuItem line;
  private JRadioButtonMenuItem dot;
  private JRadioButtonMenuItem fractal_functions[];
  private JRadioButtonMenuItem[] palette;
  private JRadioButtonMenuItem[] planes;
  private JRadioButtonMenuItem[] out_coloring_modes;
  private JFrame fract_color_frame;
  private JFrame orbit_color_frame;
  private JFrame grid_color_frame;
  private JFrame custom_palette_editor;
  private JFrame choose_color_frame;
  private JColorChooser color_chooser;
  private JProgressBar progress;
  private JTextField real;
  private JTextField imaginary;
  private JLabel[] labels;
  private JTextField[] textfields;
  private int[][] temp_custom_palette;
  private int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
  private int i, k;
  public static final int FAST_JULIA_IMAGE_SIZE = 250;
  public static final int MANDELBROTNTH = 9;
  public static final int LAMBDA = 10;
  public static final int MAGNET1 = 11;
  public static final int MAGNET2 = 12;
  public static final int NEWTON3 = 13;
  public static final int NEWTON4 = 14;
  public static final int NEWTONGENERALIZED3 = 15;
  public static final int NEWTONGENERALIZED8 = 16;
  public static final int NEWTONPOLY = 17;
  public static final int BARNSLEY1 = 18;
  public static final int BARNSLEY2 = 19;
  public static final int BARNSLEY3 = 20;
  public static final int MANDELBAR = 21;
  public static final int SPIDER = 22;
  public static final int PHOENIX = 23;
  public static final int SIERPINSKI_GASKET = 24;
  public static final int HALLEY3 = 25;
  public static final int HALLEY4 = 26;
  public static final int HALLEYGENERALIZED3 = 27;
  public static final int HALLEYGENERALIZED8 = 28;
  public static final int HALLEYPOLY = 29;
  public static final int SCHRODER3 = 30;
  public static final int SCHRODER4 = 31;
  public static final int SCHRODERGENERALIZED3 = 32;
  public static final int SCHRODERGENERALIZED8 = 33;
  public static final int SCHRODERPOLY = 34;
  public static final int HOUSEHOLDER3 = 35;
  public static final int HOUSEHOLDER4 = 36;
  public static final int HOUSEHOLDERGENERALIZED3 = 37;
  public static final int HOUSEHOLDERGENERALIZED8 = 38;
  public static final int HOUSEHOLDERPOLY = 39;
  public static final int MANDELPOLY = 40;
  public static final int MANOWAR = 41;
  public static final int NORMAL_COLOR = 0;
  public static final int SMOOTH_COLOR = 1;
  public static final int BINARY_DECOMPOSITION = 2;
  public static final int BINARY_DECOMPOSITION2 = 3;
  public static final int ITERATIONS_PLUS_RE = 4;
  public static final int ITERATIONS_PLUS_IM = 5;
  public static final int ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM = 6;
  public static final int BIOMORPH = 7;
  public static final int COLOR_DECOMPOSITION = 8;
  public static final int ESCAPE_TIME_COLOR_DECOMPOSITION = 9;
  public static final int MU_PLANE = 0;
  public static final int INVERSED_MU_PLANE = 1;
  public static final int INVERSED_MU2_PLANE = 2;
  public static final int INVERSED_MU3_PLANE = 3;
  public static final int INVERSED_MU4_PLANE = 4;
  public static final int LAMBDA_PLANE = 5;
  public static final int INVERSED_LAMBDA_PLANE = 6;


    public MainWindow() {

        super();
  
        ptr = this;

        xCenter = 0;
        yCenter = 0;
        zoom_factor = 2;
        size = 6;

        n = 2;
        julia_grid_first_dimension = 0;
        max_iterations = 500;

        color_choice = 0;
        
        boundary_tracing = true;

        bailout = 2;
        
        rotation_vals = new double[2];
        rotation = 0;
        
        calculated = 0;
        

        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));
        
        perturbation_vals = new double[2];
        perturbation_vals[0] = 0;
        perturbation_vals[1] = 0;
        
        perturbation = false;

        color_cycling_location = 0;
        color_intensity = 1;
        
        filters = new boolean[7];

        
        first_paint = false;
        plane_type = MU_PLANE;
        out_coloring_algorithm = NORMAL_COLOR;
        
        filters[0] = false;
        filters[1] = false;
        filters[2] = false;
        filters[3] = false;
        filters[4] = false;
        filters[5] = false;
        filters[6] = false;
        
        orbit = false;
        orbit_style = true;
        first_seed = true;
        julia = false;
        burning_ship = false;
        fast_julia_filters = false;
        periodicity_checking = false;
        whole_image_done = false;
        color_cycling = false;
        grid = false;
        julia_map = false;

        fractal_color = Color.BLACK;
        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;

        function = 0;
        
        coefficients = new double[11];
              
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (ClassNotFoundException ex) {} 
        catch (InstantiationException ex) {} 
        catch (IllegalAccessException ex) {} 
        catch (UnsupportedLookAndFeelException ex) {}
        
        if(System.getProperty("os.name").equals("Windows XP")) {
            image_size = 788;
            setSize(798, 845);
        }
        else {
            if(System.getProperty("os.name").equals("Windows 7")) {
                image_size = 788;
                setSize(806, 849);
            }
            else {
                image_size = 788;
                setSize(806, 849);
            }
        }
        
        //image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
 
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
        reloadTitle();
        setLayout(new FlowLayout());

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
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/mandel2.png")));

        menubar = new JMenuBar();

        file_menu = new JMenu("File");

        
        URL imageURL = getClass().getResource("/icons/starting_position.png");
        Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        ImageIcon icon = new ImageIcon(image2);
        starting_position = new JMenuItem("Starting Position", icon);                          
        
        imageURL = getClass().getResource("/icons/go_to.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        go_to = new JMenuItem("Go To", icon);

        imageURL = getClass().getResource("/icons/zoom_in.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        zoom_in = new JMenuItem("Zoom In", icon);
        
        imageURL = getClass().getResource("/icons/zoom_out.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        zoom_out = new JMenuItem("Zoom Out", icon); 

        imageURL = getClass().getResource("/icons/save.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        save_settings = new JMenuItem("Save As...", icon);

        imageURL = getClass().getResource("/icons/load.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        load_settings = new JMenuItem("Load", icon);
        
        imageURL = getClass().getResource("/icons/save_image.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        save_image = new JMenuItem("Save Image As...", icon); 
        
        imageURL = getClass().getResource("/icons/exit.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        exit = new JMenuItem("Exit", icon);
              
        
        
        options_menu = new JMenu("Options");
        
        imageURL = getClass().getResource("/icons/functions.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        fractal_functions_menu = new JMenu("Fractal Functions");
        fractal_functions_menu.setIcon(icon);
        
        mandelbrot_type_functions = new JMenu("Mandelbrot Type");
        magnet_type_functions = new JMenu("Magnet type");
        
        root_finding_functions = new JMenu("Root Finding Methods");
        newton_type_functions = new JMenu("Newton Method");
        halley_type_functions = new JMenu("Halley Method");
        schroder_type_functions = new JMenu("Schroder Method");
        householder_type_functions = new JMenu("Householder Method");
        
        barnsley_type_functions = new JMenu("Barnsley Type");
        
        imageURL = getClass().getResource("/icons/planes.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        planes_menu = new JMenu("Planes");
        planes_menu.setIcon(icon);
        
        burning_ship_opt = new JCheckBoxMenuItem("Burning Ship");
        
        imageURL = getClass().getResource("/icons/image_size.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        size_of_image = new JMenuItem("Image Size", icon);
        
        perturbation_opt = new JCheckBoxMenuItem("Perturbation...");
                     
        imageURL = getClass().getResource("/icons/iterations.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        iterations_menu = new JMenu("Iterations");
        iterations_menu.setIcon(icon);
        
        imageURL = getClass().getResource("/icons/plus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        increase_iterations = new JMenuItem("Increase Iterations", icon);
        
        imageURL = getClass().getResource("/icons/minus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        decrease_iterations = new JMenuItem("Decrease Iterations", icon);
        
        imageURL = getClass().getResource("/icons/iterations.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        iterations = new JMenuItem("Set Iterations", icon);
                
        imageURL = getClass().getResource("/icons/bailout.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        bailout_number = new JMenuItem("Bailout", icon);
        
        
        imageURL = getClass().getResource("/icons/rotate.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        rotation_menu = new JMenu("Rotation");
        rotation_menu.setIcon(icon);
        

        imageURL = getClass().getResource("/icons/rotate.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        set_rotation = new JMenuItem("Set Rotation", icon);
        
        imageURL = getClass().getResource("/icons/plus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        increase_rotation = new JMenuItem("Increase Rotation", icon);
        
        imageURL = getClass().getResource("/icons/minus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        decrease_rotation = new JMenuItem("Decrease Rotation", icon);
        
        
        imageURL = getClass().getResource("/icons/zooming_factor.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        change_zooming_factor = new JMenuItem("Zooming Factor", icon);
        
        imageURL = getClass().getResource("/icons/threads.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        thread_number = new JMenuItem("Threads", icon);
                
        
        imageURL = getClass().getResource("/icons/optimizations.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        optimizations_menu = new JMenu("Optimizations");
        optimizations_menu.setIcon(icon);
        
        boundary_tracing_opt = new JCheckBoxMenuItem("Boundary Tracing");
        boundary_tracing_opt.setSelected(true);
        
        periodicity_checking_opt = new JCheckBoxMenuItem("Periodicity Checking");
      
 
        imageURL = getClass().getResource("/icons/tools_options.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        tools_options_menu = new JMenu("Tools Options");
        tools_options_menu.setIcon(icon);
        
        orbit_menu = new JMenu("Orbit");
        
        imageURL = getClass().getResource("/icons/color.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        orbit_color_opt = new JMenuItem("Orbit Color", icon);
        
        imageURL = getClass().getResource("/icons/orbit_style.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        orbit_style_menu = new JMenu("Orbit Style");
        orbit_style_menu.setIcon(icon);
        
        imageURL = getClass().getResource("/icons/color.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        grid_color_opt = new JMenuItem("Grid Color", icon);
        
        fast_julia_filters_opt = new JCheckBoxMenuItem("Julia Preview Image Filters");
        
        

        anti_aliasing_opt = new JCheckBoxMenuItem("Anti-Aliasing");
        edges_opt = new JCheckBoxMenuItem("Edge Detection");
        edges2_opt = new JCheckBoxMenuItem("Edge Detection 2");
        sharpness_opt = new JCheckBoxMenuItem("Sharpness");
        emboss_opt = new JCheckBoxMenuItem("Emboss");
        embosscolored_opt = new JCheckBoxMenuItem("Emboss Colored");
        invert_colors_opt = new JCheckBoxMenuItem("Inverted Colors");
        

        imageURL = getClass().getResource("/icons/colors_menu.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        colors_menu = new JMenu("Colors");
        colors_menu.setIcon(icon);        
        
        imageURL = getClass().getResource("/icons/color.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        fract_color = new JMenuItem("Fractal Color", icon);
        
        imageURL = getClass().getResource("/icons/palette.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        palette_menu = new JMenu("Color Palette");
        palette_menu.setIcon(icon);
        
        imageURL = getClass().getResource("/icons/out_coloring_mode.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        out_coloring_mode_menu = new JMenu("Out Coloring Mode");
        out_coloring_mode_menu.setIcon(icon); 
        
        
        imageURL = getClass().getResource("/icons/shift_palette.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(icon);
        
        imageURL = getClass().getResource("/icons/shift_palette.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        roll_palette = new JMenuItem("Shift Palette", icon);
        
        imageURL = getClass().getResource("/icons/plus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        increase_roll_palette = new JMenuItem("Shift Palette Forward", icon);
        
        imageURL = getClass().getResource("/icons/minus.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        decrease_roll_palette = new JMenuItem("Shift Palette Backward", icon);
        
        imageURL = getClass().getResource("/icons/color_intensity.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        color_intens = new JMenuItem("Color Intensity", icon);

        
        tools_menu = new JMenu("Tools");
        orbit_opt = new JCheckBoxMenuItem("Orbit");
        julia_opt = new JCheckBoxMenuItem("Julia");
        color_cycling_opt = new JCheckBoxMenuItem("Color Cycling");
        grid_opt = new JCheckBoxMenuItem("Show Grid");
        julia_map_opt = new JCheckBoxMenuItem("Julia Map...");

        filters_menu = new JMenu("Filters");

        help_menu = new JMenu("Help");

        imageURL = getClass().getResource("/icons/help.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        help_contents = new JMenuItem("Help Contents", icon);
      
        imageURL = getClass().getResource("/icons/about.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        about = new JMenuItem("About", icon);
        
 
        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        save_settings.setToolTipText("Saves the function, plane, center, size, color options, iterations, rotation, perturbation, and bailout of the fractal.");
        load_settings.setToolTipText("Loads the function, plane, center, size, color options, iterations, rotation, perturbation, and bailout of the fractal.");
        save_image.setToolTipText("Saves a bmp, jpg, or png image.");
        exit.setToolTipText("Exits the application.");

        size_of_image.setToolTipText("Sets the image size.");
        iterations.setToolTipText("Sets the maximum number of iterations.");
        increase_iterations.setToolTipText("Increases the maximum iteterations number by one.");
        decrease_iterations.setToolTipText("Decreases the maximum iteterations number by one.");
        bailout_number.setToolTipText("Sets the bailout. Above this number the norm of a complex numbers is not bounded.");
        set_rotation.setToolTipText("Sets the rotation in degrees.");
        increase_rotation.setToolTipText("Increases the rotation by one degree.");
        decrease_rotation.setToolTipText("Decreases the rotation by one degree.");
        perturbation_opt.setToolTipText("Changes the initial value of the fractal calculation.");
        change_zooming_factor.setToolTipText("Sets the rate of each zoom.");
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing alot of bounded areas.");
        boundary_tracing_opt.setToolTipText("Calculates only the boundaries of the image.");

        grid_color_opt.setToolTipText("Sets the color of the grid.");
        orbit_color_opt.setToolTipText("Sets the color of the orbit.");

        fract_color.setToolTipText("Sets a color corresponding to the maximum iterations.");
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");
        color_intens.setToolTipText("Sets the intensity of the palette.");

        fast_julia_filters_opt.setToolTipText("Activates the filters for the julia preview.");

        orbit_opt.setToolTipText("Displays the orbit of a complex number.");
        julia_opt.setToolTipText("Generates an image based on a seed (chosen pixel).");
        julia_map_opt.setToolTipText("Creates an image of julia sets.");
        color_cycling_opt.setToolTipText("Animates the image, cycling through the palette.");
        grid_opt.setToolTipText("Draws a cordinated grid.");

        anti_aliasing_opt.setToolTipText("Smooths the image.");
        edges_opt.setToolTipText("Detects the edges of the image (Thick Edges).");
        edges2_opt.setToolTipText("Detects the edges of the image (Thin Edges).");
        sharpness_opt.setToolTipText("Makes the edges of the image more sharp.");
        emboss_opt.setToolTipText("Raises the light colored areas and carves the dark ones, using gray scale.");
        embosscolored_opt.setToolTipText("Raises the light colored areas and carves the dark ones.");
        invert_colors_opt.setToolTipText("Inverts the colors of the image.");
        
        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, 0));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

        burning_ship_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        increase_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.ALT_MASK));
        decrease_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.ALT_MASK));
        bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        set_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        increase_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        decrease_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        perturbation_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        change_zooming_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        thread_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        periodicity_checking_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        boundary_tracing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.SHIFT_MASK));
        color_intens.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        fast_julia_filters_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        orbit_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        julia_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
        color_cycling_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        grid_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
                    
        anti_aliasing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        edges_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        edges2_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
        sharpness_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        emboss_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        embosscolored_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        invert_colors_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));

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

        fractal_functions = new JRadioButtonMenuItem[42];
        
        fractal_functions[0] = new JRadioButtonMenuItem("Mandelbrot z = z^" + 2 + " + c");
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

        fractal_functions[MANDELBROTNTH] = new JRadioButtonMenuItem("Multibrot z = z^N + c");
        fractal_functions[MANDELBROTNTH].addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {

                    setFunction(MANDELBROTNTH);

                }
        });
        mandelbrot_type_functions.add(fractal_functions[MANDELBROTNTH]);
        
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

        fractal_functions_menu.add(mandelbrot_type_functions);
        
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
        

        fractal_functions[NEWTONPOLY] = new JRadioButtonMenuItem("Newton Polynomial");
        fractal_functions[NEWTONPOLY].addActionListener(new ActionListener() {
   
                public void actionPerformed(ActionEvent e) {

                    setFunction(NEWTONPOLY);

                }
        });
        newton_type_functions.add(fractal_functions[NEWTONPOLY]);

        root_finding_functions.add(newton_type_functions);
        root_finding_functions.add(halley_type_functions);
        root_finding_functions.add(schroder_type_functions);
        root_finding_functions.add(householder_type_functions);
        
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
        

        fractal_functions[HOUSEHOLDERPOLY] = new JRadioButtonMenuItem("Householder Polynomial");
        fractal_functions[HOUSEHOLDERPOLY].addActionListener(new ActionListener() {
   
                public void actionPerformed(ActionEvent e) {

                    setFunction(HOUSEHOLDERPOLY);

                }
        });
        householder_type_functions.add(fractal_functions[HOUSEHOLDERPOLY]);

        
        fractal_functions[function].setSelected(true);
        fractal_functions[function].setEnabled(false);
        
        
        planes = new JRadioButtonMenuItem[7];
        
        planes[MU_PLANE] = new JRadioButtonMenuItem("mu");
        planes[MU_PLANE].setToolTipText("The default plane.");
        planes[MU_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(MU_PLANE);

                }
        });
        planes_menu.add(planes[MU_PLANE]);

        
        planes[INVERSED_MU_PLANE] = new JRadioButtonMenuItem("1 / mu");
        planes[INVERSED_MU_PLANE].setToolTipText("The inversed mu plane.");
        planes[INVERSED_MU_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(INVERSED_MU_PLANE);

                }
        });
        planes_menu.add(planes[INVERSED_MU_PLANE]);
        
        planes[INVERSED_MU2_PLANE] = new JRadioButtonMenuItem("1 / (mu + 0.25)");
        planes[INVERSED_MU2_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU2_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(INVERSED_MU2_PLANE);

                }
        });
        planes_menu.add(planes[INVERSED_MU2_PLANE]);

        
        planes[INVERSED_MU3_PLANE] = new JRadioButtonMenuItem("1 / (mu - 1.40115)");
        planes[INVERSED_MU3_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU3_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(INVERSED_MU3_PLANE);

                }
        });
        planes_menu.add(planes[INVERSED_MU3_PLANE]);
        
        planes[INVERSED_MU4_PLANE] = new JRadioButtonMenuItem("1 / (mu - 2)");
        planes[INVERSED_MU4_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[INVERSED_MU4_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(INVERSED_MU4_PLANE);

                }
        });
        planes_menu.add(planes[INVERSED_MU4_PLANE]);
        
        planes[LAMBDA_PLANE] = new JRadioButtonMenuItem("lambda");
        planes[LAMBDA_PLANE].setToolTipText("The lambda plane.");
        planes[LAMBDA_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(LAMBDA_PLANE);

                }
        });
        planes_menu.add(planes[LAMBDA_PLANE]);
        
        planes[INVERSED_LAMBDA_PLANE] = new JRadioButtonMenuItem("1 / lambda");
        planes[INVERSED_LAMBDA_PLANE].setToolTipText("The inversed lambda plane.");
        planes[INVERSED_LAMBDA_PLANE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setPlane(INVERSED_LAMBDA_PLANE);

                }
        });
        planes_menu.add(planes[INVERSED_LAMBDA_PLANE]);

        planes[plane_type].setSelected(true);
        planes[plane_type].setEnabled(false);
        
        
        out_coloring_modes = new JRadioButtonMenuItem[10];
        
        out_coloring_modes[NORMAL_COLOR] = new JRadioButtonMenuItem("Escape Time");
        out_coloring_modes[NORMAL_COLOR].setToolTipText("Sets the out coloring method, using the iterations.");
        out_coloring_modes[NORMAL_COLOR].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(NORMAL_COLOR);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[NORMAL_COLOR]);
        
        
        out_coloring_modes[SMOOTH_COLOR] = new JRadioButtonMenuItem("Smooth");
        out_coloring_modes[SMOOTH_COLOR].setToolTipText("Sets the out coloring method, using smoothing.");
        out_coloring_modes[SMOOTH_COLOR].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(SMOOTH_COLOR);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[SMOOTH_COLOR]);
        
        
        out_coloring_modes[BINARY_DECOMPOSITION] = new JRadioButtonMenuItem("Binary Decomposition");
        out_coloring_modes[BINARY_DECOMPOSITION].setToolTipText("Sets the out coloring method, using binary decomposition.");
        out_coloring_modes[BINARY_DECOMPOSITION].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(BINARY_DECOMPOSITION);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BINARY_DECOMPOSITION]);
        
        
        out_coloring_modes[BINARY_DECOMPOSITION2] = new JRadioButtonMenuItem("Binary Decomposition 2");
        out_coloring_modes[BINARY_DECOMPOSITION2].setToolTipText("Sets the out coloring method, using binary decomposition 2.");
        out_coloring_modes[BINARY_DECOMPOSITION2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(BINARY_DECOMPOSITION2);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BINARY_DECOMPOSITION2]);
        
        
        out_coloring_modes[ITERATIONS_PLUS_RE] = new JRadioButtonMenuItem("Escape Time + Re");
        out_coloring_modes[ITERATIONS_PLUS_RE].setToolTipText("Sets the out coloring method, using the iterations + Re(z).");
        out_coloring_modes[ITERATIONS_PLUS_RE].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(ITERATIONS_PLUS_RE);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_RE]);
        
        
        out_coloring_modes[ITERATIONS_PLUS_IM] = new JRadioButtonMenuItem("Escape Time + Im");
        out_coloring_modes[ITERATIONS_PLUS_IM].setToolTipText("Sets the out coloring method, using the iterations + Im(z).");
        out_coloring_modes[ITERATIONS_PLUS_IM].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(ITERATIONS_PLUS_IM);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_IM]);
        
        
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM] = new JRadioButtonMenuItem("Escape Time + Re + Im + Re / Im");
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setToolTipText("Sets the out coloring method, using the iterations + Re(z) + Im(z) + Re(z)/Im(z).");
        out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM]);
        
        
        out_coloring_modes[BIOMORPH] = new JRadioButtonMenuItem("Biomorph");
        out_coloring_modes[BIOMORPH].setToolTipText("Sets the out coloring method, using biomorphs.");
        out_coloring_modes[BIOMORPH].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(BIOMORPH);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[BIOMORPH]);
        
        
        out_coloring_modes[COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Color Decomposition");
        out_coloring_modes[COLOR_DECOMPOSITION].setToolTipText("Sets the out coloring method, using color decomposition.");
        out_coloring_modes[COLOR_DECOMPOSITION].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(COLOR_DECOMPOSITION);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[COLOR_DECOMPOSITION]);
        
        
        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION] = new JRadioButtonMenuItem("Escape Time + Color Decomposition");
        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION].setToolTipText("Sets the out coloring method, using iterations + color decomposition.");
        out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    setOutColoringMode(ESCAPE_TIME_COLOR_DECOMPOSITION);

                }
        });
        out_coloring_mode_menu.add(out_coloring_modes[ESCAPE_TIME_COLOR_DECOMPOSITION]);
        
        
        out_coloring_modes[out_coloring_algorithm].setSelected(true);
        out_coloring_modes[out_coloring_algorithm].setEnabled(false);

        burning_ship_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBurningShipOption();

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

        thread_number.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                
                setThreadsNumber();

            }
        });

        coloring_option = new String[14];
        coloring_option[0] = "Default";
        coloring_option[1] = "Alternative";
        coloring_option[2] = "Alternative 2";
        coloring_option[3] = "Alternative 3";
        coloring_option[4] = "Alternative 4";
        coloring_option[5] = "Alternative 5";
        coloring_option[6] = "GreenWhite";
        coloring_option[7] = "Blue";
        coloring_option[8] = "Cyclic Gray Scale";
        coloring_option[9] = "Cyclic Red Cyan";
        coloring_option[10] = "Earth Sky";
        coloring_option[11] = "Hot Cold";
        coloring_option[12] = "Fire";
        coloring_option[13] = "Custom Palette";

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

        palette[color_choice].setSelected(true);
        palette[color_choice].setEnabled(false);

        palette[0].setToolTipText("The default palette.");
        palette[1].setToolTipText("An alternative palette (spectrum based).");
        palette[2].setToolTipText("A palette based on software, Fractal Extreme.");
        palette[3].setToolTipText("An alternative palette.");
        palette[4].setToolTipText("An alternative palette.");
        palette[5].setToolTipText("An alternative palette.");
        palette[6].setToolTipText("A palette based on green and white.");
        palette[7].setToolTipText("A palette based on blue.");
        palette[8].setToolTipText("A palette based on a cycling gray scale.");
        palette[9].setToolTipText("A palette based on red and blue.");
        palette[10].setToolTipText("A palette based on colors of earth and sky.");
        palette[11].setToolTipText("A palette based on colors of hot and cold.");
        palette[12].setToolTipText("A palette based on colors of fire.");
        palette[13].setToolTipText("A palette custom made by the user.");
        

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

        color_intens.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorIntensity();

            }

        });


        anti_aliasing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                setAntiAliasing();

            }

        });

        edges_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEdges();

            }

        });
        
        edges2_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEdges2();

            }

        });
        
        invert_colors_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setInvertColors();

            }

        });

        emboss_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEmboss();

            }

        });
        
        sharpness_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setSharpness();

            }

        });
        
        embosscolored_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEmbossColored();

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

        line = new JRadioButtonMenuItem("Line");
        line.setToolTipText("Sets the Orbit style to line.");
        dot = new JRadioButtonMenuItem("Dot");
        dot.setToolTipText("Sets the Orbit style to dot.");

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

        grid_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setGridColor();

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
        
        julia_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setJuliaMap();

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
                JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br><font size='4'><img src=\"" + getClass().getResource("/icons/mandel2.png") + "\"><br><br>made by Christos Kalonakis<br><br>Contact: <font color='red'><b>hrkalona</b></font>@gmail.com<br><br></font></center></html>", "About", JOptionPane.INFORMATION_MESSAGE);

            }

        });


        real = new JTextField("Real");
        real.setHorizontalAlignment(JTextField.RIGHT);
        real.setPreferredSize(new Dimension(130, 0));
        real.setEditable(false);
        real.setToolTipText("Displays the Real part of the complex number.");


        imaginary = new JTextField("Imaginary");
        imaginary.setPreferredSize(new Dimension(130, 0));
        imaginary.setHorizontalAlignment(JTextField.RIGHT);
        imaginary.setEditable(false);
        imaginary.setToolTipText("Displays the Imaginary part of the complex number.");


        main_panel = new MainPanel(this);
        main_panel.setPreferredSize(new Dimension(image_size, image_size)); 
        scroll_pane = new JScrollPane(main_panel);

        setContentPane(scroll_pane);

        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(210, 0));
        progress.setStringPainted(true);
        progress.setValue(0);

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

        colors_menu.add(fract_color);
        colors_menu.addSeparator();
        colors_menu.add(palette_menu);
        colors_menu.add(roll_palette_menu);
        colors_menu.addSeparator();
        colors_menu.add(out_coloring_mode_menu);
        colors_menu.addSeparator();
        colors_menu.add(color_intens);

        tools_options_menu.add(orbit_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(grid_color_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(fast_julia_filters_opt);
        
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
        options_menu.add(colors_menu);
        options_menu.addSeparator();
        options_menu.add(size_of_image);
        options_menu.addSeparator();
        options_menu.add(iterations_menu);
        options_menu.addSeparator();
        options_menu.add(bailout_number);
        options_menu.addSeparator();
        options_menu.add(rotation_menu);
        options_menu.addSeparator();
        options_menu.add(perturbation_opt);
        options_menu.addSeparator();
        options_menu.add(change_zooming_factor);
        options_menu.addSeparator();
        options_menu.add(optimizations_menu);
        options_menu.addSeparator();
        options_menu.add(tools_options_menu);
        
 
               
        tools_menu.add(orbit_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_map_opt);
        tools_menu.addSeparator();
        tools_menu.add(color_cycling_opt);
        tools_menu.addSeparator();
        tools_menu.add(grid_opt);
        
        
        filters_menu.add(anti_aliasing_opt);
        filters_menu.addSeparator();
        filters_menu.add(edges_opt);
        filters_menu.addSeparator();
        filters_menu.add(edges2_opt);
        filters_menu.addSeparator();
        filters_menu.add(sharpness_opt);
        filters_menu.addSeparator();
        filters_menu.add(emboss_opt);
        filters_menu.addSeparator();
        filters_menu.add(embosscolored_opt);
        filters_menu.addSeparator();
        filters_menu.add(invert_colors_opt);
        


        help_menu.add(help_contents);
        help_menu.addSeparator();
        help_menu.add(about);
        
        menubar.add(file_menu);
        menubar.add(options_menu);
        menubar.add(tools_menu);
        menubar.add(filters_menu);
        menubar.add(help_menu);
        menubar.add(new JLabel(" "));
        menubar.add(real);
        menubar.add(new JLabel("  "));
        menubar.add(imaginary);
        menubar.add(new JLabel(" i   "));
        menubar.add(progress);
        menubar.add(new JLabel(" "));
        

        setJMenuBar(menubar);

        threads = new ThreadDraw[n][n];

        scroll_pane.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {

                if(!orbit) {
                    if(!julia) {
                        setSettingsFractal(e);
                    }
                    else {
                        setSettingsJulia(e);
                    }
                }
                else {
                    setOrbit(e);
                }
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {

               if(backup_orbit != null && orbit) {
                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = image.createGraphics();
                    graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
               }
                
               if(!grid || orbit) {
                    if(orbit) {
                        while(pixels_orbit.isAlive()) {}
                        main_panel.repaint();
                    }
                    
               }
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(backup_orbit != null && orbit) {
                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = image.createGraphics();
                    graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                }
                if(!color_cycling) {
                    main_panel.repaint();  
                }
                
            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(backup_orbit != null && orbit) {
                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D graphics = image.createGraphics();
                    graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                }
                if(!color_cycling) {
                    main_panel.repaint();  
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
                    else {
                        if(System.getProperty("os.name").equals("Windows 7")) {
                            image_size = getWidth() - 35;
                        }
                        else {
                            image_size = getWidth() - 35;//Not tested
                        }
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
                    else {
                        if(System.getProperty("os.name").equals("Windows 7")) {
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
                }
                else {
                    return;
                }

                
                whole_image_done = false; 

                boolean temp2 = grid;
                grid = false;

                ThreadDraw.setArrays(image_size);

                main_panel.setPreferredSize(new Dimension(image_size, image_size));

                setOptions(false);

                progress.setMaximum((image_size * image_size) + (image_size *  image_size / 100));
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

                grid = temp2;

                if(julia && first_seed) {
                    julia = false;
                    julia_opt.setSelected(false);
                    
                    if(out_coloring_algorithm != BIOMORPH) {
                        fractal_functions[NEWTON3].setEnabled(true);
                        fractal_functions[NEWTON4].setEnabled(true);
                        fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                        fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                        fractal_functions[NEWTONPOLY].setEnabled(true);
                        fractal_functions[HALLEY3].setEnabled(true);
                        fractal_functions[HALLEY4].setEnabled(true);
                        fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
                        fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
                        fractal_functions[HALLEYPOLY].setEnabled(true);
                        fractal_functions[SCHRODER3].setEnabled(true);
                        fractal_functions[SCHRODER4].setEnabled(true);
                        fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
                        fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
                        fractal_functions[SCHRODERPOLY].setEnabled(true);
                        fractal_functions[HOUSEHOLDER3].setEnabled(true);
                        fractal_functions[HOUSEHOLDER4].setEnabled(true);
                        fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
                        fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
                        fractal_functions[HOUSEHOLDERPOLY].setEnabled(true); 
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
                    if(backup_orbit != null && orbit) {
                        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D graphics = image.createGraphics();
                        graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                    }
                    main_panel.repaint();
                }
            }
         
        });

        scroll_pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(whole_image_done) {
                    if(backup_orbit != null && orbit) {
                        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                        Graphics2D graphics = image.createGraphics();
                        graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                    }
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

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                try {
                    if(main_panel.getMousePosition().getX() < 0 ||  main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 ||  main_panel.getMousePosition().getY() > image_size) {
                        if(!color_cycling) {
                            main_panel.repaint();  
                        }
                        return;
                    }
                 
                    double temp_xcenter_size = xCenter - size / 2;
                    double temp_ycenter_size = yCenter - size / 2;
                    double temp_size_image_size = size / image_size;

                    double temp2 = temp_xcenter_size + temp_size_image_size * main_panel.getMousePosition().getX();
                    double temp = temp_ycenter_size + temp_size_image_size * main_panel.getMousePosition().getY();

                    real.setText("" + (temp2 * rotation_vals[0] - temp * rotation_vals[1]));

                    double temp3 = temp2 * rotation_vals[1] + temp * rotation_vals[0];

                    if(temp3 == 0) {
                        imaginary.setText("" + 0.0);
                    }
                    else {
                        imaginary.setText("" + (-temp3));
                    }
                }
                catch(NullPointerException ex) {}

                if(julia && first_seed) {
                    fastJulia();
                }

            }

        });
    
        requestFocus();
        
        progress.setMaximum((image_size * image_size) + (image_size *  image_size / 100));

        setOptions(false);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = last_used.createGraphics();
        graphics.drawImage(image, 0, 0, image_size, image_size, null);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       
        backup_orbit = null;

        whole_image_done = false;
        
        createThreads();
       
        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }
   

   public boolean isDrawingDone() {

       try {
           for(int i = 0; i < threads.length; i++ ) {
               for(int j = 0; j < threads[i].length; j++) {
                   if(!threads[i][j].isFirstPartDone()) {
                      return false;
                   }
               }
           }
       }
       catch(Exception ex) {}

       return true;

   }

   
   public boolean threadsAvailable() {

       try {
           for(int i = 0; i < threads.length; i++ ) {
               for(int j = 0; j < threads[i].length; j++) {
                   if(threads[i][j].isAlive()) {
                      return false;
                   }
               }
           }
       }
       catch(Exception ex) {}

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
 
   private void reloadTitle() {


       String temp = "";
       
       double temp1 = xCenter * rotation_vals[0] - yCenter * rotation_vals[1];
       double temp2 = xCenter * rotation_vals[1] + yCenter * rotation_vals[0];
       
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
            case MANDELPOLY:
                temp += "   Multibrot " + poly;
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
            case HOUSEHOLDERPOLY:
                temp += "   Householder " + poly;
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
           
       }
       
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
      
       try {
           real.setText("" + (xCenter - size / 2 + size * main_panel.getMousePosition().getX() / image_size));
           if((yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size) == 0) {
               imaginary.setText("" + 0.0);
           }
           else {
               imaginary.setText("" + (-(yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size)));
           }
       }
       catch(NullPointerException ex) {}
       
   }

   private void createThreads() {
 
        calculated = 0;
                
        for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {

               switch (color_choice) {
                   case 0:
                       if(julia) {
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 1:
                       if(julia) {
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 2:
                       if(julia) {
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 3:
                       if(julia) {
                           threads[i][j] = new Alternative3(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative3(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 4:
                       if(julia) {
                           threads[i][j] = new Alternative4(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative4(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 5:
                       if(julia) {
                           threads[i][j] = new Alternative5(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative5(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 6:
                       if(julia) {
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 7:
                       if(julia) {
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 8:
                       if(julia) {
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 9:
                       if(julia) {
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 10:
                       if(julia) {
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 11:
                       if(julia) {
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 12:
                       if(julia) {
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
                   case 13:
                       if(julia) {
                           threads[i][j] = new CustomPalette(custom_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new CustomPalette(custom_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
                       }
                       break;
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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       Calendar calendar = new GregorianCalendar();
       file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".dat"));

       int returnVal = file_chooser.showDialog(ptr, "Save");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();

           ObjectOutputStream file_temp = null;

           try {
               file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
               SettingsFractals settings;
               if(julia) {
                   settings = new SettingsJulia(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, color_intensity, function, bailout, plane_type, burning_ship, z_exponent, color_cycling_location, coefficients, custom_palette, rotation, xJuliaCenter, yJuliaCenter);
               }
               else {
                   settings = new SettingsFractals(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, color_intensity, function, bailout, plane_type, burning_ship, z_exponent, color_cycling_location, coefficients, custom_palette, rotation, perturbation, perturbation_vals);
               }
               file_temp.writeObject(settings);
               file_temp.flush();
           }
           catch(IOException ex) {}

           try {
               file_temp.close();
           }
           catch(Exception ex) {}
       }

       main_panel.repaint();

   }

   private void loadSettings() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       int returnVal = file_chooser.showDialog(ptr, "Load");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();
           ObjectInputStream file_temp = null;
           try {
               file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
               SettingsFractals settings = (SettingsFractals) file_temp.readObject();
               
               String temp = "" + settings.getClass();
               temp = temp.substring(6, temp.length()); 
               
               palette[color_choice].setSelected(false);
               palette[color_choice].setEnabled(true);

               fractal_functions[function].setSelected(false);
               fractal_functions[function].setEnabled(true);
               
               planes[plane_type].setSelected(false);
               planes[plane_type].setEnabled(true);
               
               out_coloring_modes[out_coloring_algorithm].setSelected(false);
               out_coloring_modes[out_coloring_algorithm].setEnabled(true);
               
               julia_map = false;
               julia_map_opt.setSelected(false);
               
               if(temp.equals("SettingsJulia")) {                  
                   xJuliaCenter = ((SettingsJulia) settings).getXJuliaCenter();
                   yJuliaCenter = ((SettingsJulia) settings).getYJuliaCenter();

                   julia = true;
                   first_seed = false;
                   julia_opt.setSelected(true);
                   julia_map_opt.setEnabled(false);
                   perturbation_opt.setEnabled(false);
                   fractal_functions[NEWTON3].setEnabled(false);
                   fractal_functions[NEWTON4].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
                   fractal_functions[NEWTONPOLY].setEnabled(false);
                   fractal_functions[HALLEY3].setEnabled(false);
                   fractal_functions[HALLEY4].setEnabled(false);
                   fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
                   fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
                   fractal_functions[HALLEYPOLY].setEnabled(false);
                   fractal_functions[SCHRODER3].setEnabled(false);
                   fractal_functions[SCHRODER4].setEnabled(false);
                   fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
                   fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
                   fractal_functions[SCHRODERPOLY].setEnabled(false);
                   fractal_functions[HOUSEHOLDER3].setEnabled(false);
                   fractal_functions[HOUSEHOLDER4].setEnabled(false);
                   fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
                   fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
                   fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
                   fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
               }
               else {
                   julia = false;
                   first_seed = true;
                   julia_opt.setSelected(false);
                   
                   perturbation = settings.getPerturbation();
                   
                   if(perturbation) {
                       perturbation_vals = settings.getPerturbationVals();
                   }
                   
                   if(!perturbation) {
                       fractal_functions[NEWTON3].setEnabled(true);
                       fractal_functions[NEWTON4].setEnabled(true);
                       fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                       fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       fractal_functions[NEWTONPOLY].setEnabled(true);
                       fractal_functions[HALLEY3].setEnabled(true);
                       fractal_functions[HALLEY4].setEnabled(true);
                       fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
                       fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
                       fractal_functions[HALLEYPOLY].setEnabled(true);
                       fractal_functions[SCHRODER3].setEnabled(true);
                       fractal_functions[SCHRODER4].setEnabled(true);
                       fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
                       fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
                       fractal_functions[SCHRODERPOLY].setEnabled(true);
                       fractal_functions[HOUSEHOLDER3].setEnabled(true);
                       fractal_functions[HOUSEHOLDER4].setEnabled(true);
                       fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
                       fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
                       fractal_functions[HOUSEHOLDERPOLY].setEnabled(true);
                       fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                   }
                   else {
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       fractal_functions[NEWTON3].setEnabled(false);
                       fractal_functions[NEWTON4].setEnabled(false);
                       fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                       fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
                       fractal_functions[NEWTONPOLY].setEnabled(false);
                       fractal_functions[HALLEY3].setEnabled(false);
                       fractal_functions[HALLEY4].setEnabled(false);
                       fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
                       fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
                       fractal_functions[HALLEYPOLY].setEnabled(false);
                       fractal_functions[SCHRODER3].setEnabled(false);
                       fractal_functions[SCHRODER4].setEnabled(false);
                       fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
                       fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
                       fractal_functions[SCHRODERPOLY].setEnabled(false);
                       fractal_functions[HOUSEHOLDER3].setEnabled(false);
                       fractal_functions[HOUSEHOLDER4].setEnabled(false);
                       fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
                       fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
                       fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
                       fractal_functions[SIERPINSKI_GASKET].setEnabled(false); 
                   }
               }
               
               perturbation_opt.setSelected(perturbation);
               
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
               custom_palette = settings.getCustomPalette();
               rotation = settings.getRotation();
               
               rotation_vals[0] = Math.cos(Math.toRadians(rotation));
               rotation_vals[1] = Math.sin(Math.toRadians(rotation));
               
               if(rotation != 0 && rotation != 360 && rotation != -360) {
                   grid_opt.setEnabled(false);
                   grid = false;
                   grid_opt.setSelected(false);
               }

               out_coloring_algorithm = settings.getOutColoringAlgorithm();

               color_intensity = settings.getColorIntensity();
               
               coefficients = settings.getCoefficients();
               
               
               int l = 0;
               for(; coefficients[l] != 0 && l < coefficients.length; l++) {}

               poly = "p(z) = ";
               for(; l < coefficients.length - 2; l++) {
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

               if(out_coloring_algorithm == BIOMORPH) {
                   fractal_functions[NEWTON3].setEnabled(false);
                   fractal_functions[NEWTON4].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
                   fractal_functions[NEWTONPOLY].setEnabled(false);
                   fractal_functions[HALLEY3].setEnabled(false);
                   fractal_functions[HALLEY4].setEnabled(false);
                   fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
                   fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
                   fractal_functions[HALLEYPOLY].setEnabled(false);
                   fractal_functions[SCHRODER3].setEnabled(false);
                   fractal_functions[SCHRODER4].setEnabled(false);
                   fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
                   fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
                   fractal_functions[SCHRODERPOLY].setEnabled(false);
                   fractal_functions[HOUSEHOLDER3].setEnabled(false);
                   fractal_functions[HOUSEHOLDER4].setEnabled(false);
                   fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
                   fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
                   fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
               }
               else {
                   if(!julia && !perturbation) {
                       fractal_functions[NEWTON3].setEnabled(true);
                       fractal_functions[NEWTON4].setEnabled(true);
                       fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                       fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       fractal_functions[NEWTONPOLY].setEnabled(true);
                       fractal_functions[HALLEY3].setEnabled(true);
                       fractal_functions[HALLEY4].setEnabled(true);
                       fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
                       fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
                       fractal_functions[HALLEYPOLY].setEnabled(true);
                       fractal_functions[SCHRODER3].setEnabled(true);
                       fractal_functions[SCHRODER4].setEnabled(true);
                       fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
                       fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
                       fractal_functions[SCHRODERPOLY].setEnabled(true);
                       fractal_functions[HOUSEHOLDER3].setEnabled(true);
                       fractal_functions[HOUSEHOLDER4].setEnabled(true);
                       fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
                       fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
                       fractal_functions[HOUSEHOLDERPOLY].setEnabled(true);
                   }        
               }
               
               out_coloring_modes[out_coloring_algorithm].setSelected(true);
               out_coloring_modes[out_coloring_algorithm].setEnabled(false);

               switch (function) {
                   case MANDELBROTNTH:
                       z_exponent = settings.getZExponent();
                       fractal_functions[function].setSelected(true);
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       break;
                   case MANDELPOLY:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       break;
                   case LAMBDA:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MAGNET1:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MAGNET2:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case NEWTON3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case NEWTON4:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case NEWTONGENERALIZED3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case NEWTONGENERALIZED8:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case NEWTONPOLY:
                       fractal_functions[function].setSelected(true);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                    case HALLEY3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HALLEY4:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HALLEYGENERALIZED3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HALLEYGENERALIZED8:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HALLEYPOLY:
                       fractal_functions[function].setSelected(true);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case SCHRODER3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case SCHRODER4:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case SCHRODERGENERALIZED3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case SCHRODERGENERALIZED8:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case SCHRODERPOLY:
                       fractal_functions[function].setSelected(true);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HOUSEHOLDER3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HOUSEHOLDER4:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HOUSEHOLDERGENERALIZED3:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HOUSEHOLDERGENERALIZED8:
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case HOUSEHOLDERPOLY:
                       fractal_functions[function].setSelected(true);
                       julia_opt.setEnabled(false);
                       julia_map_opt.setEnabled(false);
                       out_coloring_modes[BIOMORPH].setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   case BARNSLEY1:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case BARNSLEY2:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case BARNSLEY3:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MANDELBAR:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case SPIDER:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MANOWAR:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case PHOENIX:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case SIERPINSKI_GASKET:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       julia_opt.setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       perturbation_opt.setEnabled(false);
                       break;
                   default:
                       if(out_coloring_algorithm != BIOMORPH) {
                           out_coloring_modes[BIOMORPH].setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
               }

               
               palette[color_choice].setSelected(true);
               if(color_choice != 13) {
                   palette[color_choice].setEnabled(false);
               }
                        
               burning_ship_opt.setSelected(burning_ship);

               planes[plane_type].setSelected(true);
               planes[plane_type].setEnabled(false);

               setOptions(false);

               reloadTitle();

               progress.setValue(0);

               scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
               scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

               last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = last_used.createGraphics();
               graphics.drawImage(image, 0, 0, image_size, image_size, null);

               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

               backup_orbit = null;

               whole_image_done = false;

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
           catch(Exception ex) {}
       }

   }

   private void saveImage() {

       main_panel.repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       Calendar calendar = new GregorianCalendar();
       file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".bmp"));

       int returnVal = file_chooser.showDialog(ptr, "Save Image");

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, null);

       if(grid && !orbit) {
           double temp_xcenter_size = xCenter - size / 2;
           double temp_ycenter_size = yCenter - size / 2;
           double temp_size_image_size = size / image_size;


           graphics.setColor(grid_color);
           graphics.setFont(new Font("Arial", 1 , image_size / 6 / 65 * 5));

           double temp;
           for(int i = 1; i < 6; i++) {
               graphics.drawLine(i * image_size / 6, 0, i * image_size / 6, image_size);
               temp = temp_xcenter_size + temp_size_image_size * i * image_size / 6;
               graphics.drawString("" + temp, i * image_size / 6 + 7, graphics.getFont().getSize() + 2);
               temp = temp_ycenter_size + temp_size_image_size * i * image_size / 6;
               temp = temp == 0 ? temp : -temp;
               graphics.drawLine(0, i * image_size / 6, image_size, i * image_size / 6);
               graphics.drawString("" + temp, 7, i * image_size / 6 + graphics.getFont().getSize() + 2);
           }
       }

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           try {
               File file = file_chooser.getSelectedFile();
               String temp = file.getName();

               temp = temp.substring(temp.lastIndexOf('.') + 1, temp.length());      

               if(temp.equalsIgnoreCase("bmp")) {
                   ImageIO.write(last_used, "bmp", file);
               }
               else if(temp.equalsIgnoreCase("png")) {
                   ImageIO.write(last_used, "png", file);
               }
               else if(temp.equalsIgnoreCase("jpg")) {
                   ImageIO.write(last_used, "jpg", file);
               }
               else if(temp.equalsIgnoreCase("jpeg")) {
                   ImageIO.write(last_used, "jpeg", file);
               }
               else {
                   JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
               }
           }
           catch (IOException ex) {}
                     
       }

       last_used = null;

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
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
            case HOUSEHOLDERPOLY:
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
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
        }   
               
        reloadTitle();

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = last_used.createGraphics();
        graphics.drawImage(image, 0, 0, image_size, image_size, null);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

   private void goToFractal() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

        double temp3 = xCenter * rotation_vals[0] - yCenter * rotation_vals[1];
                
        String ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Real: " + temp3 + "\nSet the Real part of the new center.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, temp3);

        double tempReal;
        double tempImaginary;

        try {
            tempReal = Double.parseDouble(ans);
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }

        main_panel.repaint();
        temp3 = xCenter * rotation_vals[1] + yCenter * rotation_vals[0];
        if(temp3 == 0) {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Imaginary: " + 0.0 + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
        }
        else {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Imaginary: " + (-temp3) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -temp3);
        }

        try {
            tempImaginary = -Double.parseDouble(ans); //reversed axis
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }

        main_panel.repaint();
        ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Size: " + size + "\nSet the new size.", "Size", JOptionPane.QUESTION_MESSAGE, null, null, size);

        try {
            size = Double.parseDouble(ans);
                        
            xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1];
            yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0];
            
            setOptions(false);

            reloadTitle();

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = last_used.createGraphics();
            graphics.drawImage(image, 0, 0, image_size, image_size, null);

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

            backup_orbit = null;

            whole_image_done = false;

            createThreads();
            
            calculation_time = System.currentTimeMillis();
 
            startThreads(n);
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

   private void goToJulia() {

        if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
        }
        main_panel.repaint();

        String ans;

        double temp3 = xCenter * rotation_vals[0] - yCenter * rotation_vals[1];
        
        if(first_seed) {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Real part of the Julia seed.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, temp3);
        }
        else {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Real seed: " + xJuliaCenter + "\nCurrent Real: " + temp3 + "\nSet the Real part of the new center.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, temp3);
        }

        double tempReal = 0;
        double tempImaginary = 0;
  
        try {
            if(first_seed) { 
                xJuliaCenter = Double.parseDouble(ans);                            
            }
            else {
                tempReal = Double.parseDouble(ans);
            }
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }

        main_panel.repaint();
        temp3 = xCenter * rotation_vals[1] + yCenter * rotation_vals[0];
        
        if(first_seed) {
            if(yCenter == 0) {
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Imaginary part of the Julia seed.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
            }
            else {
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Imaginary part of the Julia seed.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -temp3);
            }
        }
        else {
            if(temp3 == 0) {
                if(yJuliaCenter == 0) {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (0.0) + "\nCurrent Imaginary: " + (0.0) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
                }
                else {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (-yJuliaCenter) + "\nCurrent Imaginary: " + (0.0) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
                }
            }
            else {
                if(yJuliaCenter == 0) {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (0.0) + "\nCurrent Imaginary: " + (-temp3) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -temp3);
                }
                else {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (-yJuliaCenter) + "\nCurrent Imaginary: " + (-temp3) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -temp3);
                }
            }
        }

        try {
            if(first_seed) {
                yJuliaCenter = -Double.parseDouble(ans);
            }
            else {
                tempImaginary = -Double.parseDouble(ans); //reversed axis
            }
        }
        catch(Exception ex) {
            if(ans == null) {
                main_panel.repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        }
        
        try {
            if(!first_seed) {
                main_panel.repaint();
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Size: " + size + "\nSet the new size.", "Size", JOptionPane.QUESTION_MESSAGE, null, null, size);

                size = Double.parseDouble(ans);
                
                xCenter = tempReal * rotation_vals[0] + tempImaginary * rotation_vals[1];
                yCenter = -tempReal * rotation_vals[1] + tempImaginary * rotation_vals[0];

                setOptions(false);

                reloadTitle();

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
                Graphics2D graphics = last_used.createGraphics();
                graphics.drawImage(image, 0, 0, image_size, image_size, null);

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                backup_orbit = null;

                whole_image_done = false;

                createThreads();
                
                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
            else {
                first_seed = false;              
                defaultFractalSettings();
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

   private void setFractalColor() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       setEnabled(false);
       int color_window_width = 630;
       int color_window_height = 400;
       fract_color_frame = new JFrame("Fractal Color");
       fract_color_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/color.png")));
       fract_color_frame.setLayout(new FlowLayout());
       fract_color_frame.setSize(color_window_width, color_window_height);
       fract_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       fract_color_frame.setResizable(false);
       color_chooser = new JColorChooser();
       
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

               setOptions(false);

               progress.setValue(0);

               setEnabled(true);
               fract_color_frame.dispose();

               last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = last_used.createGraphics();
               graphics.drawImage(image, 0, 0, image_size, image_size, null);

               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

               backup_orbit = null;

               whole_image_done = false;

               if(filters[0]) {
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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using maximum " + max_iterations + " iterations.\nEnter the new maximum Iterations number.", "Iterations Number", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);
           
           if(temp < 1) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Iterations number need to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 100000) {
                    main_panel.repaint();
                   JOptionPane.showMessageDialog(scroll_pane, "Iterations number need to be lower than 100001.", "Error!", JOptionPane.ERROR_MESSAGE);
                   return;
               }
           }

           max_iterations = temp;

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new maximum Iterations number is " + max_iterations + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + zoom_factor + " for Zooming Factor.\nEnter the new Zooming Factor.", "Zooming Factor", JOptionPane.QUESTION_MESSAGE);

       try {
           Double temp = Double.parseDouble(ans);

           if(temp <= 1.05) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Zooming Factor needs to be greater than 1.05.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 32) {
                   main_panel.repaint();
                   JOptionPane.showMessageDialog(scroll_pane, "Zooming Factor needs to be lower than 33.", "Error!", JOptionPane.ERROR_MESSAGE);
                   return;
               }
           }

           zoom_factor = temp;

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Zooming Factor is " + zoom_factor + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + n * n + " Threads in a " + n + "x" + n + " 2D grid.\nEnter the first dimension, n, of the nxn 2D grid.", "Threads Number", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 1) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid needs to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 100) {
                     main_panel.repaint();
                     JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid needs to be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                     return;
               }
           }

           n = temp;
           threads = new ThreadDraw[n][n];
           
           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Threads Number is " + n + "x" + n + " = " + n * n + " .", "Info", JOptionPane.INFORMATION_MESSAGE);
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

   public void setPalette(int temp) {
     
       color_cycling_location = 0;

       palette[color_choice].setSelected(false);
       palette[color_choice].setEnabled(true);

       color_choice =  temp;
       if(color_choice != 13) {
           palette[color_choice].setEnabled(false);   
       }
       else {
           palette[color_choice].setSelected(true);
       }
 
       if((color_choice >= 0 && color_choice < 8) || color_choice == 13) {
           color_intensity = 1;
       }
       else {
           if(out_coloring_algorithm == SMOOTH_COLOR) {
               color_intensity = 0.02;
           }
           else {
               color_intensity = 1;
           }
       }
         
       setOptions(false);

       progress.setValue(0); 

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

       backup_orbit = null;

       whole_image_done = false;
       
       if(filters[0]) {
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

   private void setAntiAliasing() {

       if(!anti_aliasing_opt.isSelected()) {
           filters[0] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           
           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           calculation_time = System.currentTimeMillis();

           startThreads(n);
       }
       else {
           filters[0] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
       
   }

   private void setEdges() {

       if(!edges_opt.isSelected()) {
           filters[1] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[1] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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

   }
   
    private void setEdges2() {

       if(!edges2_opt.isSelected()) {
           filters[6] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[6] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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

   }

   private void setEmboss() {

       if(!emboss_opt.isSelected()) {
           filters[2] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[2] = true;

           setOptions(false);

           progress.setValue(0); 

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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

   }
   
   private void setInvertColors() {

       if(!invert_colors_opt.isSelected()) {
           filters[5] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           
           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[5] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
       
   }
   
   private void setSharpness() {

       if(!sharpness_opt.isSelected()) {
           filters[3] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[3] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
       
   }
   
   private void setEmbossColored() {

       if(!embosscolored_opt.isSelected()) {
           filters[4] = false;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           filters[4] = true;

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
       
   }

   private void setGrid() {

       if(!grid_opt.isSelected()) {
           grid = false;
           main_panel.repaint();
       }
       else {
           grid = true;
           main_panel.repaint();         
       }

   }
   
   private void setBoundaryTracing() {
       
       if(!boundary_tracing_opt.isSelected()) {
           boundary_tracing = false;
           
           setOptions(false);
           
           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           createThreads();

           calculation_time = System.currentTimeMillis();

           startThreads(n);
       }
       else {
           boundary_tracing = true;
           
           setOptions(false);
           
           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           createThreads();

           calculation_time = System.currentTimeMillis();

           startThreads(n);
       }
       
   }
   

   private void setPeriodicityChecking() {

       if(!periodicity_checking_opt.isSelected()) {
           periodicity_checking = false;
           main_panel.repaint();
       }
       else {
           periodicity_checking = true;
           main_panel.repaint();
       }

   }


   private void setSettingsFractal(MouseEvent e) {

       if(!threadsAvailable() || julia_map) {
           return;
       }


       if(main_panel.getMousePosition().getX() < 0 ||  main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 ||  main_panel.getMousePosition().getY() > image_size) {
           return;
       }
      
       xCenter = xCenter - size / 2 + size * main_panel.getMousePosition().getX() / image_size;
       yCenter = yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size;


       switch(e.getModifiers()) {
           case InputEvent.BUTTON1_MASK: {
               size = size / zoom_factor;
               break;
           }
           case InputEvent.BUTTON3_MASK: {
               size = size * zoom_factor;
               break;
           }
       }

       setOptions(false);
    
       reloadTitle();

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));


       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);
              
       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();
       
       calculation_time = System.currentTimeMillis();

       startThreads(n);

   }

   private void setSettingsJulia(MouseEvent e) {

       if(!threadsAvailable()) {
           return;
       }

       if(main_panel.getMousePosition().getX() < 0 ||  main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 ||  main_panel.getMousePosition().getY() > image_size) {
           return;
       }

       setOptions(false);

            
       if(first_seed) {
           double temp = xCenter - size / 2 + size * main_panel.getMousePosition().getX() / image_size;
           double temp2 = yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size;
           
           xJuliaCenter = temp * rotation_vals[0] - temp2 * rotation_vals[1];
           yJuliaCenter = temp * rotation_vals[1] + temp2 * rotation_vals[0];
                   
           first_seed = false;
           orbit_opt.setEnabled(true);
           main_panel.repaint();
           defaultFractalSettings();
           return;
       }
       else {
           xCenter = xCenter - size / 2 + size * main_panel.getMousePosition().getX() / image_size;
           yCenter = yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size;
       
           switch(e.getModifiers()) {
               case InputEvent.BUTTON1_MASK:
                   size /= zoom_factor;
                   break;
           
               case InputEvent.BUTTON3_MASK:
                   size *= zoom_factor;
                   break;
           }
       
           reloadTitle();

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
       

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

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
           fast_julia_image = null;
           if(out_coloring_algorithm != BIOMORPH) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
               fractal_functions[NEWTONPOLY].setEnabled(true);
               fractal_functions[HALLEY3].setEnabled(true);
               fractal_functions[HALLEY4].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
               fractal_functions[HALLEYPOLY].setEnabled(true);
               fractal_functions[SCHRODER3].setEnabled(true);
               fractal_functions[SCHRODER4].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
               fractal_functions[SCHRODERPOLY].setEnabled(true);
               fractal_functions[HOUSEHOLDER3].setEnabled(true);
               fractal_functions[HOUSEHOLDER4].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
               fractal_functions[HOUSEHOLDERPOLY].setEnabled(true); 
           }
           fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
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
           first_seed = true;
           fast_julia_image = null;
           orbit_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           perturbation_opt.setEnabled(false);
           fractal_functions[NEWTON3].setEnabled(false);
           fractal_functions[NEWTON4].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
           fractal_functions[NEWTONPOLY].setEnabled(false);
           fractal_functions[HALLEY3].setEnabled(false);
           fractal_functions[HALLEY4].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
           fractal_functions[HALLEYPOLY].setEnabled(false);
           fractal_functions[SCHRODER3].setEnabled(false);
           fractal_functions[SCHRODER4].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
           fractal_functions[SCHRODERPOLY].setEnabled(false);
           fractal_functions[HOUSEHOLDER3].setEnabled(false);
           fractal_functions[HOUSEHOLDER4].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
           fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
           fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
           setOptions(false);
       }

   }

   private void setOrbitOption() {

       if(!orbit_opt.isSelected()) {
           orbit = false;
           if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET) {
               julia_opt.setEnabled(true);
               if(!julia) {
                   julia_map_opt.setEnabled(true);
               }
           }
           color_cycling_opt.setEnabled(true);
           if(backup_orbit != null) {
               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = image.createGraphics();
               graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
           }
           backup_orbit = null;
           last_used = null;
       }
       else {
           orbit = true;
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           color_cycling_opt.setEnabled(false);
           backup_orbit = null;
           last_used = null;
       }

       main_panel.repaint();

   }

   private void setOrbit(MouseEvent e) {

       if(!threadsAvailable() || (pixels_orbit != null && pixels_orbit.isAlive())) {
           return;
       }

       if(main_panel.getMousePosition().getX() < 0 ||  main_panel.getMousePosition().getX() > image_size || main_panel.getMousePosition().getY() < 0 ||  main_panel.getMousePosition().getY() > image_size) {
           return;
       }

       if(e.getModifiers() == InputEvent.BUTTON1_MASK) {
           if(backup_orbit != null) {
               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = image.createGraphics();
               graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
           }
           else {
               backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = backup_orbit.createGraphics();
               graphics.drawImage(image, 0, 0, image_size, image_size, null);
           }
           try {
               if(julia) {
                   pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 300 ? 300 : max_iterations, (int)main_panel.getMousePosition().getX(), (int)main_panel.getMousePosition().getY(), image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, grid, function, z_exponent, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
                   pixels_orbit.start();
               }
               else {
                   pixels_orbit = new DrawOrbit(xCenter, yCenter, size, max_iterations > 300 ? 300 : max_iterations, (int)main_panel.getMousePosition().getX(), (int)main_panel.getMousePosition().getY(), image_size, image, ptr, orbit_color, orbit_style, plane_type, burning_ship, grid, function, z_exponent, rotation_vals, perturbation, perturbation_vals, coefficients);
                   pixels_orbit.start();
               }            
           }
           catch(Exception ex) {}
       }

       try {
           double temp_xcenter_size = xCenter - size / 2;
           double temp_ycenter_size = yCenter - size / 2;
           double temp_size_image_size = size / image_size;
                    
           double temp2 = temp_xcenter_size + temp_size_image_size * main_panel.getMousePosition().getX();
           double temp = temp_ycenter_size + temp_size_image_size * main_panel.getMousePosition().getY();
                    
                    
           real.setText("" + (temp2 * rotation_vals[0] - temp * rotation_vals[1]));
           
           double temp3 = temp2 * rotation_vals[1] + temp * rotation_vals[0];
                    
           if(temp3 == 0) {
               imaginary.setText("" + 0.0);
           }
           else {
               imaginary.setText("" + (-temp3));
           }
       }
       catch(NullPointerException ex) {}
               
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

       reloadTitle();

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));


       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

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
 
       setOptions(false);

       progress.setValue(0);

       reloadTitle();

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));


       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       
       backup_orbit = null;

       whole_image_done = false;

       createThreads();
       
       calculation_time = System.currentTimeMillis();

       startThreads(n);

   }

   private void zoomOut() {
      
       size *= zoom_factor;

       setOptions(false);

       progress.setValue(0);

       reloadTitle();

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));


       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       
       backup_orbit = null;

       whole_image_done = false;

       createThreads();
       
       calculation_time = System.currentTimeMillis();

       startThreads(n);
       
   }

   public void setOptions(Boolean option) {

       if(!julia_map) {
           starting_position.setEnabled(option);
       }
       load_settings.setEnabled(option);
       save_image.setEnabled(option);

       if((!julia || !first_seed) && !julia_map) {
           go_to.setEnabled(option);
       }

       fractal_functions_menu.setEnabled(option);
       colors_menu.setEnabled(option);
       iterations_menu.setEnabled(option);
       size_of_image.setEnabled(option);

       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY) {
           bailout_number.setEnabled(option);          
       }
       
       optimizations_menu.setEnabled(option);
       
       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET) {
           periodicity_checking_opt.setEnabled(option); 
       }


       if(((!julia && !orbit) || (!first_seed && !orbit)) && !julia_map && !perturbation && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET)) {
           julia_opt.setEnabled(option);
       }

       if(!orbit && !color_cycling) {
           color_cycling_opt.setEnabled(option);
       }

       anti_aliasing_opt.setEnabled(option);
       edges_opt.setEnabled(option);
       edges2_opt.setEnabled(option);
       emboss_opt.setEnabled(option);
       sharpness_opt.setEnabled(option);
       embosscolored_opt.setEnabled(option);
       invert_colors_opt.setEnabled(option);
       
       if(rotation == 0 || rotation == 360 || rotation == -360) {
           grid_opt.setEnabled(option);
       }
       
       if(!julia_map) {
           zoom_in.setEnabled(option);
       }
       if(!julia_map) {
           zoom_out.setEnabled(option);
       }
       if(!julia_map) {
           orbit_opt.setEnabled(option);
       }
       planes_menu.setEnabled(option);
       
       if(!julia && !perturbation && !orbit && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET)) {
           julia_map_opt.setEnabled(option);
       }
       
       rotation_menu.setEnabled(option);
       
       if(!julia && !julia_map && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY && function != SIERPINSKI_GASKET)) {
           perturbation_opt.setEnabled(option);
       }

   }

   private void setLine() {

       orbit_style = true;
       dot.setSelected(false);
       dot.setEnabled(true);
       line.setSelected(true);
       line.setEnabled(false);

   }

    private void setDot() {

       orbit_style = false;
       line.setSelected(false);
       line.setEnabled(true);
       dot.setSelected(true);
       dot.setEnabled(false);

   }

   private void setOrbitColor() {

       setEnabled(false);
       int color_window_width = 630;
       int color_window_height = 400;
       orbit_color_frame = new JFrame("Orbit Color");
       orbit_color_frame.setLayout(new FlowLayout());
       orbit_color_frame.setSize(color_window_width, color_window_height);
       orbit_color_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/color.png")));
       orbit_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       orbit_color_frame.setResizable(false);
       color_chooser = new JColorChooser();

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

       setEnabled(false);
       int color_window_width = 630;
       int color_window_height = 400;
       grid_color_frame = new JFrame("Orbit Color");
       grid_color_frame.setLayout(new FlowLayout());
       grid_color_frame.setSize(color_window_width, color_window_height);
       grid_color_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/color.png")));
       grid_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       grid_color_frame.setResizable(false);
       color_chooser = new JColorChooser();

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

   private void setFunction(int temp) {

       fractal_functions[function].setSelected(false);
       fractal_functions[function].setEnabled(true);

       int temp2 = function;
       function =  temp;
       int l;

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       switch (function) {
           case MANDELBROTNTH:
               main_panel.repaint();
               String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the exponent of z.\nThe exponent can be a real number.", "Exponent", JOptionPane.QUESTION_MESSAGE);

               try {
                   z_exponent = Double.parseDouble(ans);
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
                       if(temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case MANDELPOLY:
           main_panel.repaint();
           ans = JOptionPane.showInputDialog(scroll_pane, "Enter the coefficients of the polynomial,\nin degree descending order.\nMinimum polynomial degree, 1 (2 coefficients).\nMaximum polynomial degree, 10 (11 coefficients).", "Polynomial coeffiecients", JOptionPane.QUESTION_MESSAGE);
          
           try {
               
               StringTokenizer token = new StringTokenizer(ans);
               
               if(ans.equals("")) {
                   JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
           
               if(token.countTokens() > 11 || token.countTokens() < 2) {
                   JOptionPane.showMessageDialog(scroll_pane, "The polynomial must be between 1st and 10th degree.", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
           
               int k = 0;
               l = coefficients.length - token.countTokens();
               for(; k < l; k++) {
                   coefficients[k] = 0;
               }
               
               while(token.hasMoreTokens()) {
                   coefficients[k] = Double.parseDouble(token.nextToken()); 
                   k++;
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
               fractal_functions[function].setSelected(false);

               if(function != temp2) {
                   if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == NEWTONPOLY) {
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
           
           poly = "p(z) = ";
           for(; l < coefficients.length - 2; l++) {
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
 
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case LAMBDA:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case MAGNET1:         
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case MAGNET2:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case NEWTON3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case NEWTON4:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case NEWTONGENERALIZED3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case NEWTONGENERALIZED8:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case NEWTONPOLY:
           main_panel.repaint();
           ans = JOptionPane.showInputDialog(scroll_pane, "Enter the coefficients of the polynomial,\nin degree descending order.\nMinimum polynomial degree, 1 (2 coefficients).\nMaximum polynomial degree, 10 (11 coefficients).", "Polynomial coeffiecients", JOptionPane.QUESTION_MESSAGE);
          
           try {
               
               StringTokenizer token = new StringTokenizer(ans);
               
               if(ans.equals("")) {
                   JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               if(token.countTokens() > 11 || token.countTokens() < 2) {
                   JOptionPane.showMessageDialog(scroll_pane, "The polynomial must be between 1st and 10th degree.", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               int k = 0;
               l = coefficients.length - token.countTokens();
               for(; k < l; k++) {
                   coefficients[k] = 0;
               }
               
               while(token.hasMoreTokens()) {
                   coefficients[k] = Double.parseDouble(token.nextToken()); 
                   k++;
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
               fractal_functions[function].setSelected(false);

               if(function != temp2) {
                   if(temp2 == MANDELBROTNTH || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
           poly = "p(z) = ";
           for(; l < coefficients.length - 2; l++) {
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
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           fractal_functions[function].setSelected(true);
           perturbation_opt.setEnabled(false);
           break;
       case HALLEY3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HALLEY4:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HALLEYGENERALIZED3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HALLEYGENERALIZED8:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HALLEYPOLY:
           main_panel.repaint();
           ans = JOptionPane.showInputDialog(scroll_pane, "Enter the coefficients of the polynomial,\nin degree descending order.\nMinimum polynomial degree, 1 (2 coefficients).\nMaximum polynomial degree, 10 (11 coefficients).", "Polynomial coeffiecients", JOptionPane.QUESTION_MESSAGE);
          
           try {
               
               StringTokenizer token = new StringTokenizer(ans);
               
               if(ans.equals("")) {
                   JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               if(token.countTokens() > 11 || token.countTokens() < 2) {
                   JOptionPane.showMessageDialog(scroll_pane, "The polynomial must be between 1st and 10th degree.", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               int k = 0;
               l = coefficients.length - token.countTokens();
               for(; k < l; k++) {
                   coefficients[k] = 0;
               }
               
               while(token.hasMoreTokens()) {
                   coefficients[k] = Double.parseDouble(token.nextToken()); 
                   k++;
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
               fractal_functions[function].setSelected(false);

               if(function != temp2) {
                   if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == SCHRODERPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
           poly = "p(z) = ";
           for(; l < coefficients.length - 2; l++) {
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
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           fractal_functions[function].setSelected(true);
           perturbation_opt.setEnabled(false);
           break; 
       case SCHRODER3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case SCHRODER4:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case SCHRODERGENERALIZED3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case SCHRODERGENERALIZED8:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case SCHRODERPOLY:
           main_panel.repaint();
           ans = JOptionPane.showInputDialog(scroll_pane, "Enter the coefficients of the polynomial,\nin degree descending order.\nMinimum polynomial degree, 1 (2 coefficients).\nMaximum polynomial degree, 10 (11 coefficients).", "Polynomial coeffiecients", JOptionPane.QUESTION_MESSAGE);
          
           try {
               
               StringTokenizer token = new StringTokenizer(ans);
               
               if(ans.equals("")) {
                   JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               if(token.countTokens() > 11 || token.countTokens() < 2) {
                   JOptionPane.showMessageDialog(scroll_pane, "The polynomial must be between 1st and 10th degree.", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
               int k = 0;
               l = coefficients.length - token.countTokens();
               for(; k < l; k++) {
                   coefficients[k] = 0;
               }
               
               while(token.hasMoreTokens()) {
                   coefficients[k] = Double.parseDouble(token.nextToken()); 
                   k++;
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
               fractal_functions[function].setSelected(false);

               if(function != temp2) {
                   if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == HOUSEHOLDERPOLY || temp2 == MANDELPOLY) {
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
           
           poly = "p(z) = ";
           for(; l < coefficients.length - 2; l++) {
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
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           fractal_functions[function].setSelected(true);
           perturbation_opt.setEnabled(false);
           break;
       case HOUSEHOLDER3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HOUSEHOLDER4:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HOUSEHOLDERGENERALIZED3:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HOUSEHOLDERGENERALIZED8:
           fractal_functions[function].setEnabled(false);
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       case HOUSEHOLDERPOLY:
           main_panel.repaint();
           ans = JOptionPane.showInputDialog(scroll_pane, "Enter the coefficients of the polynomial,\nin degree descending order.\nMinimum polynomial degree, 1 (2 coefficients).\nMaximum polynomial degree, 10 (11 coefficients).", "Polynomial coeffiecients", JOptionPane.QUESTION_MESSAGE);
          
           try {
               
               StringTokenizer token = new StringTokenizer(ans);
               
               if(ans.equals("")) {
                   JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
           
               if(token.countTokens() > 11 || token.countTokens() < 2) {
                   JOptionPane.showMessageDialog(scroll_pane, "The polynomial must be between 1st and 10th degree.", "Error!", JOptionPane.ERROR_MESSAGE);
                   main_panel.repaint();
                   
                   fractal_functions[function].setSelected(false);
                   
                   if(function != temp2) {
                       if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
           
               int k = 0;
               l = coefficients.length - token.countTokens();
               for(; k < l; k++) {
                   coefficients[k] = 0;
               }
               
               while(token.hasMoreTokens()) {
                   coefficients[k] = Double.parseDouble(token.nextToken()); 
                   k++;
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
               fractal_functions[function].setSelected(false);

               if(function != temp2) {
                   if(temp2 == MANDELBROTNTH || temp2 == NEWTONPOLY || temp2 == HALLEYPOLY || temp2 == SCHRODERPOLY || temp2 == MANDELPOLY) {
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
           
           poly = "p(z) = ";
           for(; l < coefficients.length - 2; l++) {
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
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           fractal_functions[function].setSelected(true);
           perturbation_opt.setEnabled(false);
           break;
       case BARNSLEY1:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case BARNSLEY2:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case BARNSLEY3:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case MANDELBAR:
           fractal_functions[function].setEnabled(false);
           out_coloring_modes[BIOMORPH].setEnabled(true);
           break;
       case SPIDER:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case MANOWAR:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case PHOENIX:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       case SIERPINSKI_GASKET:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           julia_opt.setEnabled(false);
           julia_map_opt.setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           perturbation_opt.setEnabled(false);
           break;
       default:
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           break;
       }

       defaultFractalSettings();

   }

   private void setBailout() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + bailout + " for Bailout number.\nEnter the new Bailout number.", "Bailout Number", JOptionPane.QUESTION_MESSAGE);

       try {
           double temp = Double.parseDouble(ans);
           
           if(temp <= 0) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Bailout value needs to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 46341) {
                   main_panel.repaint();
                   JOptionPane.showMessageDialog(scroll_pane, "Bailout value needs to be lower than 46341.", "Error!", JOptionPane.ERROR_MESSAGE);
                   return;
               }
           }
           bailout = temp;

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Bailout number is " + bailout + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + rotation + " for Rotation value.\nEnter the new Rotation value.", "Rotation Value", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);
           
           if(temp < -360) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Rotation value needs to be greater than -361.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 360) {
                   main_panel.repaint();
                   JOptionPane.showMessageDialog(scroll_pane, "Rotation value needs to be lower than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
                   return;
               }
           }
           rotation = temp;
 
           rotation_vals[0] = Math.cos(Math.toRadians(rotation));
           rotation_vals[1] = Math.sin(Math.toRadians(rotation));

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Rotation value is " + rotation + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);
           
           reloadTitle();

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           
           if(rotation != 0 && rotation != 360 && rotation != -360) {
               grid_opt.setEnabled(false);
               grid = false;
               grid_opt.setSelected(false);
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
   
   private void increaseRotation() {

       if(rotation == 360) {
           rotation = 0;
       }
       rotation++;
       
       if(rotation != 0 && rotation != 360 && rotation != -360) {
           grid_opt.setEnabled(false);
           grid = false;
           grid_opt.setSelected(false);
       }
       
       rotation_vals[0] = Math.cos(Math.toRadians(rotation));
       rotation_vals[1] = Math.sin(Math.toRadians(rotation));
       
       setOptions(false);

       progress.setValue(0);
       
       reloadTitle();

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       if(rotation != 0 && rotation != 360 && rotation != -360) {
           grid_opt.setEnabled(false);
           grid = false;
           grid_opt.setSelected(false);
       }

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
   
   private void decreaseRotation() {

       if(rotation == -360) {
           rotation = 0;
       }
       rotation--;

       rotation_vals[0] = Math.cos(Math.toRadians(rotation));
       rotation_vals[1] = Math.sin(Math.toRadians(rotation));
       
       setOptions(false);

       progress.setValue(0);
       
       reloadTitle();

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       if(rotation != 0 && rotation != 360 && rotation != -360) {
           grid_opt.setEnabled(false);
           grid = false;
           grid_opt.setSelected(false);
       }

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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

   private void setBurningShipOption() {

       if(!burning_ship_opt.isSelected()) {
           burning_ship = false;

           if(function <= 9 || function == MANDELPOLY) {
               defaultFractalSettings();
           }

       }
       else {
           burning_ship = true;

           if(function <= 9 || function == MANDELPOLY) {
               defaultFractalSettings();
           }

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
            case HOUSEHOLDERPOLY:
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
            default: 
                xCenter = 0;
                yCenter = 0;
                size = 6;           
                break;
        }

        reloadTitle();

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = last_used.createGraphics();
        graphics.drawImage(image, 0, 0, image_size, image_size, null);

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
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       String ans = JOptionPane.showInputDialog(scroll_pane, "Your Image Size is " + image_size + "x" + image_size +" .\nEnter the new Image Size.\nOnly one Dimension is required.", "Image Size", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 209) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Image Size needs to be greater than 209.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           if(temp > 6000) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Image Size needs to be lower than than 6001.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           whole_image_done = false; 

           boolean temp2 = grid;
           grid = false;
           
           image_size = temp;
           
           ThreadDraw.setArrays(image_size);

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Image Size is " + image_size + "x" + image_size + " .", "Info", JOptionPane.INFORMATION_MESSAGE);
           
           main_panel.setPreferredSize(new Dimension(image_size, image_size));
           
           setOptions(false);

           progress.setMaximum((image_size * image_size) + (image_size *  image_size / 100));
           progress.setValue(0);

           SwingUtilities.updateComponentTreeUI(this);
                     
           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
           
           last_used = null;
           //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           //Graphics2D graphics = last_used.createGraphics();
           //graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
          
           backup_orbit = null;
     
           grid = temp2;
           
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
        int temp_max_iterations = max_iterations > 175 ? 175 : max_iterations;
        int temp_image_size = FAST_JULIA_IMAGE_SIZE;

        double temp_xcenter_size = xCenter - size / 2;
        double temp_ycenter_size = yCenter - size / 2;
        double temp_size_image_size = size / image_size;

        try {
            double temp1 = temp_xcenter_size + temp_size_image_size * main_panel.getMousePosition().getX();
            double temp2 = temp_ycenter_size + temp_size_image_size * main_panel.getMousePosition().getY();
            
            temp_xJuliaCenter = temp1 * rotation_vals[0] - temp2 * rotation_vals[1];
            temp_yJuliaCenter = temp1 * rotation_vals[1] + temp2 * rotation_vals[0];
        }
        catch(Exception ex) {
            return;
        }

        fast_julia_image = new BufferedImage(temp_image_size + 2, temp_image_size + 2, BufferedImage.TYPE_INT_ARGB);

        switch (function) {
            case MANDELBROTNTH:
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
            default:
                temp_xCenter = 0;
                temp_yCenter = 0;
                temp_size = 6;
                temp_bailout = 2;
                break;
        }

        for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {

               switch (color_choice) {
                   case 0:
                       threads[i][j] = new Default(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 1:
                       threads[i][j] = new Alternative(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 2:
                       threads[i][j] = new Alternative2(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break; 
                   case 3:
                       threads[i][j] = new Alternative3(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 4:
                       threads[i][j] = new Alternative4(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break; 
                   case 5:
                       threads[i][j] = new Alternative5(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break; 
                   case 6:
                       threads[i][j] = new GreenWhite(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 7:
                       threads[i][j] = new Blue(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 8:
                       threads[i][j] = new CyclicGrayscale(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 9:
                       threads[i][j] = new CyclicRedCyan(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 10:
                       threads[i][j] = new EarthSky(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 11:
                       threads[i][j] = new HotCold(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 12:
                       threads[i][j] = new Fire(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 13:
                       threads[i][j] = new CustomPalette(custom_palette, j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, boundary_tracing, periodicity_checking, plane_type, filters,  out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, temp_xJuliaCenter, temp_yJuliaCenter);
                       break; 
               }

           }
       }

       startThreads(n);

   }

   private void setColorCycling() {

       if(!color_cycling_opt.isSelected()) {
           
           color_cycling = false;
           threads[0][0].setColorCycling(false);
           while(!threadsAvailable()) {}
           color_cycling_location = threads[0][0].getColorCyclingLocation();
          
           if(filters[0] || filters[1]|| filters[2] || filters[3]|| filters[4] || filters[5] || filters[6]) {
               setOptions(false);
              
               progress.setValue(0);
           
               last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = last_used.createGraphics();
               graphics.drawImage(image, 0, 0, image_size, image_size, null);
           
               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

               backup_orbit = null;

               whole_image_done = false;

               if(filters[0]) {
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

           setOptions(false);

           switch (color_choice) {

               case 0:
                   threads[0][0] = new Default(0, image_size, 0, image_size, max_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 1:
                   threads[0][0] = new Alternative(0, image_size, 0, image_size, max_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 2:
                   threads[0][0] = new Alternative2(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 3:
                   threads[0][0] = new Alternative3(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 4:
                   threads[0][0] = new Alternative4(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 5:
                   threads[0][0] = new Alternative5(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 6:
                   threads[0][0] = new GreenWhite(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 7:
                   threads[0][0] = new Blue(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 8:
                   threads[0][0] = new CyclicGrayscale(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 9:
                   threads[0][0] = new CyclicRedCyan(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 10:
                   threads[0][0] = new EarthSky(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 11:
                   threads[0][0] = new HotCold(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 12:
                   threads[0][0] = new Fire(0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 13:
                   threads[0][0] = new CustomPalette(custom_palette, 0, image_size, 0, image_size, max_iterations,  ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;

           }


           whole_image_done = false;

           startThreads(1);


       }

   }

   private void createThreadsPaletteAndFilter() {

       for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {

               switch (color_choice) {
                   case 0:
                       threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 1:
                       threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 2:
                       threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 3:
                       threads[i][j] = new Alternative3(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 4:
                       threads[i][j] = new Alternative4(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 5:
                       threads[i][j] = new Alternative5(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 6:
                       threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 7:
                       threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
                   case 8:
                       threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, filters);
                       break;
                   case 9:
                       threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, filters);
                       break;
                   case 10:
                       threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, filters);
                       break;
                   case 11:
                       threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, filters);
                       break;
                   case 12:
                       threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, filters);
                       break;
                   case 13:
                       threads[i][j] = new CustomPalette(custom_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations,  ptr, image, fractal_color, color_cycling_location, out_coloring_algorithm, color_intensity, filters);
                       break;
               }

           }
       }

   }
   
   private void shiftPalette() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       main_panel.repaint();
       String ans = (String) JOptionPane.showInputDialog(scroll_pane, "The Palette is shifted by " + color_cycling_location + ".\nEnter a number to shift the Palette.", "Shift Palette", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 0) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Palette shift value needs to be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           color_cycling_location = temp;

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Palette shift value is " + color_cycling_location + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           //scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           //scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       double default_color_intensity;
       if((color_choice >= 0 && color_choice < 8) || color_choice == 13) {
           default_color_intensity = 1;
       }
       else {
           if(out_coloring_algorithm == SMOOTH_COLOR) {
               default_color_intensity = 0.02;
           }
           else {
               default_color_intensity = 1;
           }
       }

       main_panel.repaint();
       String ans = (String) JOptionPane.showInputDialog(scroll_pane, "The default Color Intensity for this mode is " + default_color_intensity + ".\nEnter the new Color Intensity.", "Color Intensity", JOptionPane.QUESTION_MESSAGE, null, null, color_intensity);

       try {
           double temp = Double.parseDouble(ans);

           if(temp <= 0) {
               main_panel.repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Color Intensity value needs to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           color_intensity = temp;

           main_panel.repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Color Intensity is " + color_intensity + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           //scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           //scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

           backup_orbit = null;

           whole_image_done = false;

           if(filters[0]) {
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
       
       planes[plane_type].setSelected(false);
       planes[plane_type].setEnabled(true);

       plane_type =  temp;

       planes[temp].setEnabled(false);
       
       defaultFractalSettings();

   }
   
   private void setOutColoringMode(int temp) {
       
       out_coloring_modes[out_coloring_algorithm].setSelected(false);
       out_coloring_modes[out_coloring_algorithm].setEnabled(true);

       out_coloring_algorithm =  temp;

       out_coloring_modes[temp].setEnabled(false);
       
       if(out_coloring_algorithm == SMOOTH_COLOR) {
           if((color_choice >= 0 && color_choice < 8) || color_choice == 13) {
               color_intensity = 1;
           }
           else {
               color_intensity = 0.02;
           }
       }
       else {
           color_intensity = 1;
       }
       
       if(out_coloring_algorithm == BIOMORPH) {
           fractal_functions[NEWTON3].setEnabled(false);
           fractal_functions[NEWTON4].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
           fractal_functions[NEWTONPOLY].setEnabled(false);
           fractal_functions[HALLEY3].setEnabled(false);
           fractal_functions[HALLEY4].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
           fractal_functions[HALLEYPOLY].setEnabled(false);
           fractal_functions[SCHRODER3].setEnabled(false);
           fractal_functions[SCHRODER4].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
           fractal_functions[SCHRODERPOLY].setEnabled(false);
           fractal_functions[HOUSEHOLDER3].setEnabled(false);
           fractal_functions[HOUSEHOLDER4].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
           fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
       }
       else {
           if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY) {
               out_coloring_modes[BIOMORPH].setEnabled(true);
           }
           out_coloring_modes[BIOMORPH].setSelected(false);

           if(!julia_map && !julia && !perturbation && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && function != NEWTONPOLY && function != HALLEY3 && function != HALLEY4 && function != HALLEYGENERALIZED3 && function != HALLEYGENERALIZED8 && function != HALLEYPOLY && function != SCHRODER3 && function != SCHRODER4 && function != SCHRODERGENERALIZED3 && function != SCHRODERGENERALIZED8 && function != SCHRODERPOLY && function != HOUSEHOLDER3 && function != HOUSEHOLDER4 && function != HOUSEHOLDERGENERALIZED3 && function != HOUSEHOLDERGENERALIZED8 && function != HOUSEHOLDERPOLY) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
               fractal_functions[NEWTONPOLY].setEnabled(true);
               fractal_functions[HALLEY3].setEnabled(true);
               fractal_functions[HALLEY4].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
               fractal_functions[HALLEYPOLY].setEnabled(true);
               fractal_functions[SCHRODER3].setEnabled(true);
               fractal_functions[SCHRODER4].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
               fractal_functions[SCHRODERPOLY].setEnabled(true);
               fractal_functions[HOUSEHOLDER3].setEnabled(true);
               fractal_functions[HOUSEHOLDER4].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
               fractal_functions[HOUSEHOLDERPOLY].setEnabled(true);
           }
       }
  
       setOptions(false);

       progress.setValue(0);
   
       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
   
   private void setJuliaMap() {
       
       if(!julia_map_opt.isSelected()) {
           julia_map = false;
           setOptions(false);
           
           if(out_coloring_algorithm != BIOMORPH) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
               fractal_functions[NEWTONPOLY].setEnabled(true);
               fractal_functions[HALLEY3].setEnabled(true);
               fractal_functions[HALLEY4].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
               fractal_functions[HALLEYPOLY].setEnabled(true);
               fractal_functions[SCHRODER3].setEnabled(true);
               fractal_functions[SCHRODER4].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
               fractal_functions[SCHRODERPOLY].setEnabled(true);
               fractal_functions[HOUSEHOLDER3].setEnabled(true);
               fractal_functions[HOUSEHOLDER4].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
               fractal_functions[HOUSEHOLDERPOLY].setEnabled(true);   
           }
           fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
 
           threads = new ThreadDraw[n][n];

           reloadTitle();

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

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
               JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number needs to be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
               julia_map_opt.setSelected(false);
               return;
           }
           else {
                if(temp > 200) {
                     main_panel.repaint();
                     JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number needs to be lower than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
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
           
           fractal_functions[NEWTON3].setEnabled(false);
           fractal_functions[NEWTON4].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
           fractal_functions[NEWTONPOLY].setEnabled(false);
           fractal_functions[HALLEY3].setEnabled(false);
           fractal_functions[HALLEY4].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
           fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
           fractal_functions[HALLEYPOLY].setEnabled(false);
           fractal_functions[SCHRODER3].setEnabled(false);
           fractal_functions[SCHRODER4].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
           fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
           fractal_functions[SCHRODERPOLY].setEnabled(false);
           fractal_functions[HOUSEHOLDER3].setEnabled(false);
           fractal_functions[HOUSEHOLDER4].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
           fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
           fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
           fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
           
           perturbation_opt.setEnabled(false);
  
           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = last_used.createGraphics();
           graphics.drawImage(image, 0, 0, image_size, image_size, null);

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

   public void drawGrid(Graphics2D brush) {

        double temp_xcenter_size = xCenter - size / 2;
        double temp_ycenter_size = yCenter - size / 2;
        double temp_size_image_size = size / image_size;

        brush.setColor(grid_color);
        brush.setFont(new Font("Arial", Font.BOLD , 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double temp, temp2;
        for(int i = 1; i < 6; i++) {
           brush.drawLine(i * image_size / 6, 0, i * image_size / 6, image_size);
           brush.drawLine(0, i * image_size / 6, image_size, i * image_size / 6);
           
           
           temp = temp_xcenter_size + temp_size_image_size * i * image_size / 6;
           temp2 = temp_ycenter_size + temp_size_image_size * i * image_size / 6;
           
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
           
           brush.drawString("" + temp, i * image_size / 6 + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
           brush.drawString("" + temp2, 6 + scroll_pane.getHorizontalScrollBar().getValue(), i * image_size / 6 + 12);
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
       
       return grid;
       
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
       
       setEnabled(false);
       int custom_palette_window_width = 640;
       int custom_palette_window_height = 212;
       custom_palette_editor = new JFrame("Custom Palette Editor");
       custom_palette_editor.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/palette.png")));
       custom_palette_editor.setLayout(new FlowLayout());
       custom_palette_editor.setSize(custom_palette_window_width, custom_palette_window_height);
       custom_palette_editor.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (custom_palette_window_height / 2));
       custom_palette_editor.setResizable(false);
       
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

            }
        });
        
        temp_custom_palette = new int[custom_palette.length][custom_palette[0].length];
        for(k = 0; k < custom_palette.length; k++) {
            for(int j = 0; j < custom_palette[0].length; j++) {
                temp_custom_palette[k][j] = custom_palette[k][j];
            }
        }

        
        JPanel palettes = new JPanel();
        JPanel hues = new JPanel();
        JPanel buttons = new JPanel();

        palettes.setLayout(new FlowLayout());
        hues.setLayout(new FlowLayout());
        buttons.setLayout(new FlowLayout());

        labels = new JLabel[12];
        textfields = new JTextField[12];
        
        JLabel temp1 = new JLabel("Custom Palette Editor");
        temp1.setFont(new Font("bold", Font.BOLD, 16));
        custom_palette_editor.add(temp1);
        
        temp1 = new JLabel("Palette:");
        temp1.setFont(new Font("bold", Font.BOLD, 12));
        palettes.add(temp1);
        

        temp1 = new JLabel("   Hues:");
        temp1.setFont(new Font("bold", Font.BOLD, 12));
        hues.add(temp1);
        
        for(k = 0; k < labels.length; k++) {
            labels[k] = new JLabel("");
            labels[k].setPreferredSize(new Dimension(40, 40));
            labels[k].setOpaque(true);
            labels[k].setBackground(new Color(temp_custom_palette[k][1], temp_custom_palette[k][2], temp_custom_palette[k][3]));
            labels[k].setToolTipText("Left click to change this color.");
            labels[k].addMouseListener(new MouseListener() {
                int temp = k;
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {
                    custom_palette_editor.setEnabled(false);
                    int color_window_width = 630;
                    int color_window_height = 400;
                    choose_color_frame = new JFrame("Choose Color");
                    choose_color_frame.setLayout(new FlowLayout());
                    choose_color_frame.setSize(color_window_width, color_window_height);
                    choose_color_frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/color.png")));
                    choose_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
                    choose_color_frame.setResizable(false);
                    color_chooser = new JColorChooser();
                    
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
                 public void mouseReleased(MouseEvent e) {}

                 @Override
                 public void mouseEntered(MouseEvent e) {}

                 @Override
                 public void mouseExited(MouseEvent e) {}
            });
            palettes.add(labels[k]);
            
            textfields[k] = new JTextField();
            textfields[k].setPreferredSize(new Dimension(40, 20));
            textfields[k].setText("" + temp_custom_palette[k][0]);
            hues.add(textfields[k]);
        }
        
        JButton palette_ok = new JButton("Ok");
        palette_ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int temp2 = 0;
                boolean all_zeros = true;
                for(int m = 0; m < textfields.length; m++) {
                    try {
                        temp2 = Integer.parseInt(textfields[m].getText());
                    }
                    catch(Exception ex) {
                         JOptionPane.showMessageDialog(custom_palette_editor, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                         return;
                    }
                    
                    if(temp2 < 0 || temp2 > 40) {
                         JOptionPane.showMessageDialog(custom_palette_editor, "The hues values must between 1 and 40\n for that color to be included in the palette,\n or 0 for that color not to be included.", "Error!", JOptionPane.ERROR_MESSAGE);
                         return;
                    }                                        

                    if(temp2 != 0) {
                        all_zeros = false;
                    }
                    
                    temp_custom_palette[m][0] = temp2;
                }
                
                if(all_zeros) {
                     JOptionPane.showMessageDialog(custom_palette_editor, "You need to include at least one color.", "Error!", JOptionPane.ERROR_MESSAGE);
                     return;
                }
                
                for(k = 0; k < custom_palette.length; k++) {
                    for(int j = 0; j < custom_palette[0].length; j++) {
                        custom_palette[k][j] = temp_custom_palette[k][j];
                    } 
                }

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

            }
        });
        
        buttons.add(palette_cancel);

        JMenuBar menubar2 = new JMenuBar();
        JMenu file2 = new JMenu("File");
        
        URL imageURL = getClass().getResource("/icons/palette_reset.png");
        Image image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        ImageIcon icon = new ImageIcon(image2);
        JMenuItem reset_palette = new JMenuItem("Reset Palette", icon);
        
        imageURL = getClass().getResource("/icons/palette_save.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        JMenuItem save_palette = new JMenuItem("Save Palette As...", icon);
        
        imageURL = getClass().getResource("/icons/palette_load.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        JMenuItem load_palette = new JMenuItem("Load Palette", icon);
        
        reset_palette.setToolTipText("Resets the palette.");
        save_palette.setToolTipText("Saves a user made palette.");
        load_palette.setToolTipText("Loads a user made palette.");
        
        reset_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                int[][] temp_custom_palette1 = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
                
                for(int m = 0; m < labels.length; m++) {
                    temp_custom_palette[m][0] = temp_custom_palette1[m][0];
                    temp_custom_palette[m][1] = temp_custom_palette1[m][1];
                    temp_custom_palette[m][2] = temp_custom_palette1[m][2];
                    temp_custom_palette[m][3] = temp_custom_palette1[m][3];
                    labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                    textfields[m].setText("" + temp_custom_palette[m][0]);
                }
            }
        
        });
        
        save_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                for(int m = 0; m < textfields.length; m++) {
                    int temp2;
                    try {
                        temp2 = Integer.parseInt(textfields[m].getText());
         
                        if(temp2 > 0 && temp2 < 41) {
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
                
            }
        
        });
        
        file2.add(reset_palette);
        file2.add(save_palette);
        file2.add(load_palette);
        menubar2.add(file2);
        
        custom_palette_editor.setJMenuBar(menubar2);
        
        
        custom_palette_editor.add(palettes);
        custom_palette_editor.add(hues);
        custom_palette_editor.add(buttons);
        custom_palette_editor.setVisible(true);
   }
   
   private void savePalette() {
       
       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       Calendar calendar = new GregorianCalendar();
       file_chooser.setSelectedFile(new File("palette " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".plt"));

       int returnVal = file_chooser.showDialog(ptr, "Save");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();

           ObjectOutputStream file_temp = null;

           try {
               file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
               SettingsPalette settings_palette = new SettingsPalette(temp_custom_palette);
               file_temp.writeObject(settings_palette);
               file_temp.flush();
           }
           catch(IOException ex) {}

           try {
               file_temp.close();
           }
           catch(Exception ex) {}
       }

       main_panel.repaint();

       
   }
   
   private void loadPalette() {
       
       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }
       main_panel.repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       int returnVal = file_chooser.showDialog(ptr, "Load");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();
           ObjectInputStream file_temp = null;
           try {
               file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
               SettingsPalette settings_palette = (SettingsPalette) file_temp.readObject();
               
               temp_custom_palette = settings_palette.getCustomPalette();
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
           catch(Exception ex) {}
       }
       
   }
   
   public boolean getJuliaMap() {
   
       return julia_map;
       
   }
   
   private void createThreadsJuliaMap() {
           
           int n = julia_grid_first_dimension;
           for(int i = 0; i < n; i++) {
               for(int j = 0; j < n; j++) {

                   switch (color_choice) {
                       case 0:
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 1:
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 2:
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients); 
                           break;
                       case 3:
                           threads[i][j] = new Alternative3(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients); 
                           break;
                       case 4:
                           threads[i][j] = new Alternative4(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients); 
                           break;
                       case 5:
                           threads[i][j] = new Alternative5(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients); 
                           break;
                       case 6:
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 7:
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 8:
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 9:
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 10:
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 11:
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 12:
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
                       case 13:
                           threads[i][j] = new CustomPalette(custom_palette, j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, color_intensity, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
                           break;
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
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

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
   
   private void shiftPaletteForward() {

       color_cycling_location++;
       
       color_cycling_location = color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : color_cycling_location;
 
       setOptions(false);

       progress.setValue(0);

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

       backup_orbit = null;

       whole_image_done = false;

       if(filters[0]) {
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
   
   private void shiftPaletteBackward() {

       if(color_cycling_location > 0) {
           color_cycling_location--;  
       }
       else {
           return;
       }
 
       setOptions(false);

       progress.setValue(0);

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, image_size, image_size, null);
       
       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

       backup_orbit = null;

       whole_image_done = false;

       if(filters[0]) {
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
   
   private void setPerturbation() {
       
       if(!perturbation_opt.isSelected()) {
           perturbation = false;
           
           if(out_coloring_algorithm != BIOMORPH) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
               fractal_functions[NEWTONPOLY].setEnabled(true);
               fractal_functions[HALLEY3].setEnabled(true);
               fractal_functions[HALLEY4].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED3].setEnabled(true);
               fractal_functions[HALLEYGENERALIZED8].setEnabled(true);
               fractal_functions[HALLEYPOLY].setEnabled(true);
               fractal_functions[SCHRODER3].setEnabled(true);
               fractal_functions[SCHRODER4].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED3].setEnabled(true);
               fractal_functions[SCHRODERGENERALIZED8].setEnabled(true);
               fractal_functions[SCHRODERPOLY].setEnabled(true);
               fractal_functions[HOUSEHOLDER3].setEnabled(true);
               fractal_functions[HOUSEHOLDER4].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(true);
               fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(true);
               fractal_functions[HOUSEHOLDERPOLY].setEnabled(true); 
           }
           fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
           
           defaultFractalSettings();
           
       }
       else {
           if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
               Graphics2D graphics = image.createGraphics();
               graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
            }
            main_panel.repaint();
            
            perturbation_vals[0] = 0;
            perturbation_vals[1] = 0;

            String ans;


            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Real part of the Perturbation.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, perturbation_vals[0]);


            try {
                perturbation_vals[0] = Double.parseDouble(ans);
            }
            catch(Exception ex) {
                if(ans == null) {
                    main_panel.repaint();
                    perturbation_opt.setSelected(false);
                    perturbation_vals[0] = 0;
                    perturbation_vals[1] = 0;
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    perturbation_opt.setSelected(false);
                    perturbation_vals[0] = 0;
                    perturbation_vals[1] = 0;
                    main_panel.repaint();
                    return;
                }
            }

            main_panel.repaint();
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Imaginary part of the Perturbation.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, perturbation_vals[1]);


            try { 
                perturbation_vals[1] = -Double.parseDouble(ans); //reversed axis 
            }
            catch(Exception ex) {
                if(ans == null) {
                    perturbation_vals[0] = 0;
                    perturbation_vals[1] = 0;
                    main_panel.repaint();
                    perturbation_opt.setSelected(false);
                    return;
                }
                else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    perturbation_opt.setSelected(false);
                    perturbation_vals[0] = 0;
                    perturbation_vals[1] = 0;
                    main_panel.repaint();
                    return;
                }
            }
            
            perturbation = true;
            julia_opt.setEnabled(false);
            julia_map_opt.setEnabled(false);
            fractal_functions[NEWTON3].setEnabled(false);
            fractal_functions[NEWTON4].setEnabled(false);
            fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
            fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
            fractal_functions[NEWTONPOLY].setEnabled(false);
            fractal_functions[HALLEY3].setEnabled(false);
            fractal_functions[HALLEY4].setEnabled(false);
            fractal_functions[HALLEYGENERALIZED3].setEnabled(false);
            fractal_functions[HALLEYGENERALIZED8].setEnabled(false);
            fractal_functions[HALLEYPOLY].setEnabled(false);
            fractal_functions[SCHRODER3].setEnabled(false);
            fractal_functions[SCHRODER4].setEnabled(false);
            fractal_functions[SCHRODERGENERALIZED3].setEnabled(false);
            fractal_functions[SCHRODERGENERALIZED8].setEnabled(false);
            fractal_functions[SCHRODERPOLY].setEnabled(false);
            fractal_functions[HOUSEHOLDER3].setEnabled(false);
            fractal_functions[HOUSEHOLDER4].setEnabled(false);
            fractal_functions[HOUSEHOLDERGENERALIZED3].setEnabled(false);
            fractal_functions[HOUSEHOLDERGENERALIZED8].setEnabled(false);
            fractal_functions[HOUSEHOLDERPOLY].setEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);

            defaultFractalSettings();

       }
            
   }

   private void showCHMHelpFile() {
       
       try {
            InputStream src = getClass().getResource("/help/Fractal_Zoomer_Help.chm").openStream();
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
        catch(Exception ex) {}
       
   }
   
   public void setCalculated(double value) {
       
       calculated += value;
       
   }
   
   public double getCalculated() {
       
       return calculated;
       
   }

   public static void main(String[] args) throws InterruptedException {

       MainWindow fractals = new MainWindow();
       Thread.currentThread().sleep(1300);
       fractals.setVisible(true);

   }

}
