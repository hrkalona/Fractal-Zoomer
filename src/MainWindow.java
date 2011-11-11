import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
  private boolean anti_aliasing;
  private boolean image_buffering;
  private boolean edges;
  private boolean emboss;
  private boolean orbit;
  private boolean julia;
  private boolean inverse_plane;
  private boolean orbit_style;
  private boolean burning_ship;
  private boolean whole_image_done;
  private boolean fast_julia_filters;
  private boolean periodicity_checking;
  private boolean color_cycling;
  private boolean grid;
  private ThreadPaint[][] threads;
  private PaintOrbit pixels_orbit;
  private double[][] image_iterations;
  private double xCenter;
  private double yCenter;
  private double xJuliaCenter;
  private double yJuliaCenter;
  private int function;
  private boolean first_seed;
  private double size;
  private int max_iterations;
  private int n;
  private int color_choice;
  private int color_cycling_location;
  private int bailout;
  private double z_exponent;
  private double zoom_factor;
  private double color_intensity;
  private int out_coloring_algorithm;
  private int image_size;
  private String[] coloring_option;
  private Color fractal_color;
  private Color orbit_color;
  private Color grid_color;
  private BufferedImage image;
  private BufferedImage fast_julia_image;
  private BufferedImage backup_orbit;
  private BufferedImage last_used;
  private MainWindow ptr;
  private JPanel main_panel;
  private JScrollPane scroll_pane;
  private JMenuBar menubar;
  private JMenu file_menu;
  private JMenu options_menu;
  private JMenu colors_menu;
  private JMenu palette_menu;
  private JMenu filters_menu;
  private JMenu tools_options_menu;
  private JMenu orbit_menu;
  private JMenu orbit_style_menu;
  private JMenu tools_menu;
  private JMenu out_coloring_mode_menu;
  private JMenu help_menu;
  private JMenu fractal_functions_menu;
  private JMenu help_contents;
  private JMenuItem size_of_image;
  private JMenuItem iterations;
  private JMenuItem thread_number;
  private JMenuItem bailout_number;
  private JMenuItem fract_color;
  private JMenuItem color_intens;
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
  private JMenuItem help_file;
  private JMenuItem help_options;
  private JMenuItem help_tools_options;
  private JMenuItem help_functions;
  private JMenuItem help_colors;
  private JMenuItem help_palette;
  private JMenuItem help_out_coloring_mode;
  private JMenuItem help_orbit;
  private JMenuItem help_tools;
  private JMenuItem help_filters;
  private JMenuItem help_mouse_clicks;
  private JCheckBoxMenuItem anti_aliasing_opt;  
  private JCheckBoxMenuItem image_buffering_opt;
  private JCheckBoxMenuItem edges_opt;
  private JCheckBoxMenuItem emboss_opt;
  private JCheckBoxMenuItem orbit_opt;
  private JCheckBoxMenuItem julia_opt;
  private JCheckBoxMenuItem fast_julia_filters_opt;
  private JCheckBoxMenuItem periodicity_checking_opt;
  private JCheckBoxMenuItem burning_ship_opt;
  private JCheckBoxMenuItem color_cycling_opt;
  private JCheckBoxMenuItem grid_opt;
  private JCheckBoxMenuItem inverse_plane_opt;
  private JRadioButtonMenuItem line;
  private JRadioButtonMenuItem dot;
  private JRadioButtonMenuItem fractal_functions[];
  private JRadioButtonMenuItem[] palette;
  private JRadioButtonMenuItem normal_color_opt;
  private JRadioButtonMenuItem smooth_color_opt;
  private JRadioButtonMenuItem binary_decomposition_opt;
  private JRadioButtonMenuItem iterations_plus_re_plus_im_plus_re_divide_im_opt;
  private JRadioButtonMenuItem biomorph_opt;
  private JRadioButtonMenuItem cross_orbit_traps_opt;
  private JFrame fract_color_frame;
  private JFrame orbit_color_frame;
  private JFrame grid_color_frame;
  private JColorChooser color_chooser;
  private JProgressBar progress;
  private JTextField real;
  private JTextField imaginary;
  private SettingsFractals settings;
  private int i;
  public static final int FAST_JULIA_IMAGE_SIZE = 250;
  public static final int MANDELBROTNTH = 9;
  public static final int LAMBDA = 10;
  public static final int MAGNET1 = 11;
  public static final int MAGNET2 = 12;
  public static final int NEWTON3 = 13;
  public static final int NEWTON4 = 14;
  public static final int NEWTONGENERALIZED3 = 15;
  public static final int NEWTONGENERALIZED8 = 16;
  public static final int BARNSLEY1 = 17;
  public static final int BARNSLEY2 = 18;
  public static final int BARNSLEY3 = 19;
  public static final int MANDELBAR = 20;
  public static final int SPIDER = 21;
  public static final int PHOENIX = 22;
  public static final int NORMAL_COLOR = 0;
  public static final int SMOOTH_COLOR = 1;
  public static final int BINARY_DECOMPOSITION = 2;
  public static final int ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM = 3;
  public static final int BIOMORPH = 4;
  public static final int CROSS_ORBIT_TRAPS = 5;


    public MainWindow() {

        super();
  
        ptr = this;

        xCenter = 0;
        yCenter = 0;
        zoom_factor = 2;
        size = 6;

        n = 2;

        max_iterations = 500;

        color_choice = 0;

        bailout = 2;

        color_cycling_location = 0;
        color_intensity = 1;

        anti_aliasing = true;
        first_paint = false;
        image_buffering = false;
        inverse_plane = false;
        out_coloring_algorithm = NORMAL_COLOR;
        edges = false;
        emboss = false;
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

        fractal_color = Color.BLACK;
        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;

        function = 0;
        
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
                setSize(798, 845); //not tested
            }
        }

        image_iterations = new double[image_size][image_size];
 
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
	Dimension scrnsize = toolkit.getScreenSize();
        
        if(scrnsize.getHeight() > getHeight()) {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), (int)((scrnsize.getHeight() / 2) - (getHeight() / 2)) - 23);
        }
        else {
            setLocation((int)((scrnsize.getWidth() / 2) - (getWidth() / 2)), 0);
        }

        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        reloadTitle();
        setLayout(new FlowLayout());

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
       
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                repaint();
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
        
        burning_ship_opt = new JCheckBoxMenuItem("Burning Ship");
        inverse_plane_opt = new JCheckBoxMenuItem("Inverse Plane");
        
        imageURL = getClass().getResource("/icons/image_size.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        size_of_image = new JMenuItem("Image Size", icon);
                     
        imageURL = getClass().getResource("/icons/iterations.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        iterations = new JMenuItem("Iterations", icon);        
                
        imageURL = getClass().getResource("/icons/bailout.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        bailout_number = new JMenuItem("Bailout", icon);
        
        imageURL = getClass().getResource("/icons/zooming_factor.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        change_zooming_factor = new JMenuItem("Zooming Factor", icon);
        
        imageURL = getClass().getResource("/icons/threads.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        thread_number = new JMenuItem("Threads", icon);
        
        periodicity_checking_opt = new JCheckBoxMenuItem("Periodicity Checking");
        image_buffering_opt = new JCheckBoxMenuItem("Image Buffering");
        
        
        
        
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
        emboss_opt = new JCheckBoxMenuItem("Emboss");
        
        
        
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
               
        imageURL = getClass().getResource("/icons/color_intensity.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        color_intens = new JMenuItem("Color Intensity", icon);
        
        
        normal_color_opt = new JRadioButtonMenuItem("Iterations");
        smooth_color_opt = new JRadioButtonMenuItem("Smooth");
        binary_decomposition_opt = new JRadioButtonMenuItem("Binary Decomposition");
        iterations_plus_re_plus_im_plus_re_divide_im_opt = new JRadioButtonMenuItem("Iterations + Re + Im + Re / Im");
        biomorph_opt = new JRadioButtonMenuItem("Biomorph");
        cross_orbit_traps_opt = new JRadioButtonMenuItem("Cross Orbit Traps");
         
        tools_menu = new JMenu("Tools");
        orbit_opt = new JCheckBoxMenuItem("Orbit");
        julia_opt = new JCheckBoxMenuItem("Julia");
        color_cycling_opt = new JCheckBoxMenuItem("Color Cycling");
        grid_opt = new JCheckBoxMenuItem("Show Grid");
        //julia_made_image_opt = new JCheckBoxMenuItem("Julia Made Image");

        filters_menu = new JMenu("Filters");

        help_menu = new JMenu("Help");
        
        imageURL = getClass().getResource("/icons/help.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        help_contents = new JMenu("Help Contents");
        help_contents.setIcon(icon); 
        
        imageURL = getClass().getResource("/icons/about.png");
        image2 = Toolkit.getDefaultToolkit().getImage(imageURL);
        icon = new ImageIcon(image2);
        about = new JMenuItem("About", icon); 

        help_file = new JMenuItem("File Menu Help");
        help_options = new JMenuItem("Options Menu Help");
        help_functions = new JMenuItem("Functions Menu Help");
        help_colors = new JMenuItem("Colors Menu Help");
        help_palette = new JMenuItem("Palette Menu Help");
        help_out_coloring_mode = new JMenuItem("Out Coloring Mode Menu Help");
        help_tools_options = new JMenuItem("Tools Options Menu Help");
        help_orbit = new JMenuItem("Orbit Menu Help");
        help_tools = new JMenuItem("Tools Menu Help");
        help_filters = new JMenuItem("Filters Menu Help");
        help_mouse_clicks = new JMenuItem("Mouse Clicks Help");
         

        starting_position.setToolTipText("Resets the fractal to the default position.");
        go_to.setToolTipText("Sets the center and size of the fractal, or the julia seed.");
        zoom_in.setToolTipText("Zooms in with a fixed rate to the current center.");
        zoom_out.setToolTipText("Zooms out with a fixed rate to the current center.");
        save_settings.setToolTipText("Saves the function, center, size, color options, iterations, and bailout of the fractal.");
        load_settings.setToolTipText("Loads the function, center, size, color options, iterations, and bailout of the fractal.");
        save_image.setToolTipText("Saves a bmp, jpg, or png image.");
        exit.setToolTipText("Exits the application.");

        inverse_plane_opt.setToolTipText("Inverses the plane to 1/mu.");
        size_of_image.setToolTipText("Sets the image size.");
        iterations.setToolTipText("Sets the maximum number of iterations.");
        bailout_number.setToolTipText("Sets the magnitude. Above this number the complex numbers are not bounded.");
        change_zooming_factor.setToolTipText("Sets the rate of each zoom.");
        thread_number.setToolTipText("Sets the number of parallel drawing threads.");
        periodicity_checking_opt.setToolTipText("Renders the image faster when containing alot of bounded areas.");
        image_buffering_opt.setToolTipText("Sets the drawing on a background image.");

        grid_color_opt.setToolTipText("Sets the color of the grid.");
        orbit_color_opt.setToolTipText("Sets the color of the orbit.");

        fract_color.setToolTipText("Sets a color corresponding to the maximum iterations.");
        color_intens.setToolTipText("Sets the intensity of the palette.");

        normal_color_opt.setToolTipText("Sets the out coloring method, using the iterations.");
        smooth_color_opt.setToolTipText("Sets the out coloring method, using smoothing.");
        binary_decomposition_opt.setToolTipText("Sets the out coloring method, using binary decomposition.");
        iterations_plus_re_plus_im_plus_re_divide_im_opt.setToolTipText("Sets the out coloring method, using the iterations + Re(z) + Im(z) + Re(z)/Im(z).");
        biomorph_opt.setToolTipText("Sets the out coloring method, using biomorphs.");

        fast_julia_filters_opt.setToolTipText("Activates the filters for the julia preview.");

        orbit_opt.setToolTipText("Displays the orbit of a complex number.");
        julia_opt.setToolTipText("Generates an image based on a seed (chosen pixel).");
        color_cycling_opt.setToolTipText("Animates the image, cycling through the palette.");
        grid_opt.setToolTipText("Draws a cordinated grid.");

        anti_aliasing_opt.setToolTipText("Smooths the image.");
        edges_opt.setToolTipText("Detects the edges of the image.");
        emboss_opt.setToolTipText("Raises the light colored areas and carves the dark ones.");
        
        starting_position.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, ActionEvent.CTRL_MASK));
        go_to.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, ActionEvent.CTRL_MASK));
        zoom_in.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        zoom_out.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        save_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        load_settings.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        save_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, ActionEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));

        burning_ship_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));

        inverse_plane_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        change_zooming_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        thread_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        periodicity_checking_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.CTRL_MASK));
        image_buffering_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        color_intens.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));

        fast_julia_filters_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));

        orbit_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));
        color_cycling_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        grid_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
                    
        anti_aliasing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        edges_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        emboss_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));

        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
              
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

                repaint();
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

        fractal_functions = new JRadioButtonMenuItem[23];

        for(i = 0; i < 9; i++) {
            fractal_functions[i] = new JRadioButtonMenuItem("Mandelbrot z = z^" + (i + 2) + " + c");
            fractal_functions[i].addActionListener(new ActionListener() {
                int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
            });
            fractal_functions_menu.add(fractal_functions[i]);
        }

        fractal_functions[i] = new JRadioButtonMenuItem("Mandelbrot z = z^N + c");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        fractal_functions_menu.addSeparator();
        fractal_functions_menu.add(burning_ship_opt);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Lambda");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Magnet 1");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Magnet 2");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Newton 3");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Newton 4");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

         i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Newton Generalized 3");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Newton Generalized 8");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Barnsley 1");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Barnsley 2");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Barnsley 3");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Mandelbar");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Spider");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);

        fractal_functions_menu.addSeparator();

        i++;
        fractal_functions[i] = new JRadioButtonMenuItem("Phoenix");
        fractal_functions[i].addActionListener(new ActionListener() {
            int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setFunction(temp);

                }
        });
        fractal_functions_menu.add(fractal_functions[i]);
      
        fractal_functions[function].setSelected(true);
        fractal_functions[function].setEnabled(false);

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

        bailout_number.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                setBailout();

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

        coloring_option = new String[13];
        coloring_option[0] = "Default";
        coloring_option[1] = "Alternative";
        coloring_option[2] = "Alternative2";
        coloring_option[3] = "GreenWhite";
        coloring_option[4] = "Blue";
        coloring_option[5] = "Spectrum";
        coloring_option[6] = "Pale Spectrum";
        coloring_option[7] = "Gray Scale";
        coloring_option[8] = "Cyclic Gray Scale";
        coloring_option[9] = "Cyclic Red Cyan";
        coloring_option[10] = "Earth Sky";
        coloring_option[11] = "Hot Cold";
        coloring_option[12] = "Fire";

        palette = new JRadioButtonMenuItem[coloring_option.length];

        for(i = 0; i < palette.length; i++) {
            palette[i] = new JRadioButtonMenuItem(coloring_option[i]);
            palette[i].addActionListener(new ActionListener() {
                int temp = i;
                public void actionPerformed(ActionEvent e) {

                    setPalette(temp);

                }
            });
            palette_menu.add(palette[i]);
        }

        palette[color_choice].setSelected(true);
        palette[color_choice].setEnabled(false);

        palette[0].setToolTipText("The default palette (spectrum based).");
        palette[1].setToolTipText("An alternative palette.");
        palette[2].setToolTipText("An alternative palette.");
        palette[3].setToolTipText("A palette based on green and white.");
        palette[4].setToolTipText("A palette based on blue.");
        palette[5].setToolTipText("A palette based on spectrum.");
        palette[6].setToolTipText("A palette based on pale spectrum.");
        palette[7].setToolTipText("A palette based on gray scale.");
        palette[8].setToolTipText("A palette based on a cycling gray scale.");
        palette[9].setToolTipText("A palette based on red and blue.");
        palette[10].setToolTipText("A palette based on colors of earth and sky.");
        palette[11].setToolTipText("A palette based on colors of hot and cold.");
        palette[12].setToolTipText("A palette based on colors of fire.");


        normal_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setNormalColor();

            }

        });

        normal_color_opt.setSelected(true);
        normal_color_opt.setEnabled(false);

        smooth_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setSmoothColor();

            }

        });

        binary_decomposition_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBinaryDecomposition();

            }

        });

        iterations_plus_re_plus_im_plus_re_divide_im_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setIterationsPlusRePlusImPlusReDivideIm();

            }

        });

        biomorph_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setBiomorph();

            }

        });
        
        cross_orbit_traps_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setCrossOrbitTraps();

            }

        });

        color_intens.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setColorIntensity();

            }

        });

        anti_aliasing_opt.setSelected(true);

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

        emboss_opt.addActionListener(new ActionListener () {

            @Override
            public void actionPerformed(ActionEvent e) {

                setEmboss();

            }

        });


        image_buffering_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setImageBuffering();
                
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

        inverse_plane_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setInversePlaneOption();

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
        
        /*julia_made_image_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                setJuliaMadeImage();

            }
        });*/

        help_file.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showFileHelp();

            }

        });

        help_options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showOptionsHelp();

            }

        });

        help_functions.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showFunctionsHelp();

            }

        });

        help_colors.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showColorsHelp();

            }

        });

        help_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showPaletteHelp();

            }

        });

        help_out_coloring_mode.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showOutColoringModeHelp();

            }

        });

        help_tools_options.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showToolsOptionsHelp();

            }

        });

        help_orbit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showOrbitHelp();

            }

        });

        help_tools.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showToolsHelp();

            }

        });

        help_filters.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showFiltersHelp();

            }

        });

        help_mouse_clicks.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                showMouseClicksHelp();

            }

        });

        about.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                 
                repaint();
                JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br>made by Christos Kalonakis<br><br>Contact:<br><br><font color='red'><b>hrkalona</b></font>@uth.gr</center>", "About", JOptionPane.INFORMATION_MESSAGE);

            }

        });


        real = new JTextField("Real");
        real.setHorizontalAlignment(JTextField.RIGHT);
        real.setPreferredSize(new Dimension(190, 0));
        real.setEditable(false);
        real.setToolTipText("Displays the Real part of the complex number.");


        imaginary = new JTextField("Imaginary");
        imaginary.setPreferredSize(new Dimension(190, 0));
        imaginary.setHorizontalAlignment(JTextField.RIGHT);
        imaginary.setEditable(false);
        imaginary.setToolTipText("Displays the Imaginary part of the complex number.");


        main_panel = new JPanel();
        main_panel.setPreferredSize(new Dimension(image_size, image_size)); 
        scroll_pane = new JScrollPane(main_panel);
        
        setContentPane(scroll_pane);

        
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(240, 0));
        progress.setStringPainted(true);
        progress.setValue(0);
        progress.setToolTipText("Displays the progress of the current calculation.");

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
        colors_menu.add(palette_menu);
        colors_menu.add(out_coloring_mode_menu);
        colors_menu.add(color_intens);

        tools_options_menu.add(orbit_menu);
        tools_options_menu.addSeparator();
        tools_options_menu.add(grid_color_opt);
        tools_options_menu.addSeparator();
        tools_options_menu.add(fast_julia_filters_opt);

        options_menu.add(fractal_functions_menu);
        options_menu.add(inverse_plane_opt);
        options_menu.addSeparator();
        options_menu.add(colors_menu);
        options_menu.addSeparator();
        options_menu.add(size_of_image);
        options_menu.addSeparator();
        options_menu.add(iterations);
        options_menu.addSeparator();
        options_menu.add(bailout_number);
        options_menu.addSeparator();
        options_menu.add(change_zooming_factor);
        options_menu.addSeparator();
        options_menu.add(thread_number);
        options_menu.addSeparator();
        options_menu.add(periodicity_checking_opt);
        options_menu.addSeparator();
        options_menu.add(tools_options_menu);
        options_menu.addSeparator();
        options_menu.add(image_buffering_opt);
        
               

        out_coloring_mode_menu.add(normal_color_opt);
        out_coloring_mode_menu.add(smooth_color_opt);
        out_coloring_mode_menu.add(binary_decomposition_opt);
        out_coloring_mode_menu.add(iterations_plus_re_plus_im_plus_re_divide_im_opt);
        out_coloring_mode_menu.add(biomorph_opt);
        out_coloring_mode_menu.add(cross_orbit_traps_opt);
               
        tools_menu.add(orbit_opt);
        tools_menu.addSeparator();
        tools_menu.add(julia_opt);
        tools_menu.addSeparator();
        tools_menu.add(color_cycling_opt);
        tools_menu.addSeparator();
        tools_menu.add(grid_opt);
        //tools_menu.addSeparator();
        //tools_menu.add(julia_made_image_opt);
        
        filters_menu.add(anti_aliasing_opt);
        filters_menu.addSeparator();
        filters_menu.add(edges_opt);
        filters_menu.addSeparator();
        filters_menu.add(emboss_opt);

        help_menu.add(help_contents);
        help_menu.addSeparator();
        help_menu.add(about);

        help_contents.add(help_file);
        help_contents.add(help_options);
        help_contents.add(help_functions);
        help_contents.add(help_colors);
        help_contents.add(help_palette);
        help_contents.add(help_tools_options);
        help_contents.add(help_out_coloring_mode);
        help_contents.add(help_orbit);
        help_contents.add(help_tools);
        help_contents.add(help_filters);
        help_contents.add(help_mouse_clicks);

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
    
        
        threads = new ThreadPaint[n][n];

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
                   image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                   Graphics2D graphics = image.createGraphics();
                   graphics.drawImage(backup_orbit, 0, 0, image_size ,image_size, null);
               }
               if(!grid || orbit) {
                    if(orbit) {
                        while(pixels_orbit.isAlive()) {}
                    }
                    repaint();
               }
               
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if(backup_orbit != null && orbit) {
                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = image.createGraphics();
                    graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                }
                repaint();

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if(backup_orbit != null && orbit) {
                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = image.createGraphics();
                    graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                }
                repaint();

            }

        });

        addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent e) {

                repaint();
                
            }

        });

        scroll_pane.getHorizontalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(!image_buffering && !isDrawingDone() && !color_cycling) {
                    ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
                    if(grid && julia && first_seed) {
                        drawGrid(main_panel.getGraphics());
                    }
                }
                else {
                    if(whole_image_done) {
                        if(backup_orbit != null && orbit) {
                            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                            Graphics2D graphics = image.createGraphics();
                            graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                        }
                        ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
                        if(grid) {
                            drawGrid(main_panel.getGraphics());
                        }
                    }
                    else {
                        if(image_buffering && !color_cycling) {
                            ((Graphics2D)main_panel.getGraphics()).drawImage(last_used, 0, 0, ptr);
                            if(grid && last_used != null) {
                                drawGrid(main_panel.getGraphics());
                            }
                        }
                    }
                }
            }

        });

        scroll_pane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {

            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if(!image_buffering && !isDrawingDone() && !color_cycling) {
                    ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
                    if(grid && julia && first_seed) {
                        drawGrid(main_panel.getGraphics());
                    }
                }
                else {
                    if(whole_image_done) {
                        if(backup_orbit != null && orbit) {
                            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                            Graphics2D graphics = image.createGraphics();
                            graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
                        }
                        ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
                        if(grid) {
                            drawGrid(main_panel.getGraphics());
                        }
                    }
                    else {
                        if(image_buffering && !color_cycling) {
                            ((Graphics2D)main_panel.getGraphics()).drawImage(last_used, 0, 0, ptr);
                            if(grid && last_used != null) {
                                drawGrid(main_panel.getGraphics());
                            }
                        }
                    }
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
                        repaint();
                        return;
                    }
                 
                    double temp_xcenter_size = xCenter - size / 2;
                    double temp_ycenter_size = yCenter - size / 2;
                    double temp_size_image_size = size / image_size;
                    
                    real.setText("" + (temp_xcenter_size + temp_size_image_size * main_panel.getMousePosition().getX()));
                    double temp = temp_ycenter_size + temp_size_image_size * main_panel.getMousePosition().getY();
                    if(temp == 0) {
                        imaginary.setText("" + 0.0);
                    }
                    else {
                        imaginary.setText("" + (-temp));
                    }
                }
                catch(NullPointerException ex) {}

                if(julia && first_seed) {
                    fastJulia();
                }

            }

        });
    
        requestFocus();

    }

     
   @Override
   public void paint(Graphics brush) {


       SwingUtilities.updateComponentTreeUI(menubar);
       SwingUtilities.updateComponentTreeUI(scroll_pane.getHorizontalScrollBar());
       SwingUtilities.updateComponentTreeUI(scroll_pane.getVerticalScrollBar());
       
       
       if(!threadsAvailable()) {
           if(!image_buffering && !isDrawingDone() && !color_cycling) {
               ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
               if(grid && julia && first_seed) {
                   drawGrid(main_panel.getGraphics());
               }
           }
           else {
               if(whole_image_done) {
                   ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
                    if(grid) {
                        drawGrid(main_panel.getGraphics());
                    }
               }
               else {
                   if(image_buffering && !color_cycling) {
                       ((Graphics2D)main_panel.getGraphics()).drawImage(last_used, 0, 0, ptr);
                       if(grid && last_used != null) {
                           drawGrid(main_panel.getGraphics());
                       }
                   }
               }
           }
           return;
       }
       else {
           if(first_paint) {
               ((Graphics2D)main_panel.getGraphics()).drawImage(image, 0, 0, ptr);
               if(grid) {
                   drawGrid(main_panel.getGraphics());
               }
               return;
           }
       }

       progress.setMaximum((image_size * image_size) + (image_size *  image_size / 100));

       setOptions(false);      

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();
       
       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       
       if(!image_buffering) {
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);
       }
       
       backup_orbit = null;

       whole_image_done = false;
        
       createThreads();

       startThreads(n);

       first_paint = true;
                  
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

   
   private boolean threadsAvailable() {

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

   public JPanel getMainPanel() {

       return main_panel;

   }

   public ThreadPaint[][] getThreads() {

       return threads;

   }

   public JProgressBar getProgressBar() {

       return progress;

   }
 
   private void reloadTitle() {


       
       if(-yCenter > 0) {
           setTitle("Fractal Zoomer,  Center: " + xCenter + "+" + (-yCenter) + "i" + "  Size: " + size);
       }
       else {
           if(yCenter == 0) {
               setTitle("Fractal Zoomer,  Center: " + xCenter + "+" + (0.0) + "i" + "  Size: " + size);
           }
           else {
               setTitle("Fractal Zoomer,  Center: " + xCenter + "" + (-yCenter) + "i" + "  Size: " + size);
           }
       }     
      
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

        for(int i = 0; i < n; i++) {
           for(int j = 0; j < n; j++) {

               switch (color_choice) {
                   case 0:
                       if(julia) {
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 1:
                       if(julia) {
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 2:
                       if(julia) {
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 3:
                       if(julia) {
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 4:
                       if(julia) {
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 5:
                       if(julia) {
                           threads[i][j] = new Spectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Spectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 6:
                       if(julia) {
                           threads[i][j] = new PaleSpectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new PaleSpectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 7:
                       if(julia) {
                           threads[i][j] = new Grayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Grayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 8:
                       if(julia) {
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 9:
                       if(julia) {
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 10:
                       if(julia) {
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 11:
                       if(julia) {
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                       }
                       break;
                   case 12:
                       if(julia) {
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
                       }
                       else {
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
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
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       Calendar calendar = new GregorianCalendar();
       file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".dat"));

       int returnVal = file_chooser.showDialog(ptr, "Save");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();

           ObjectOutputStream file_temp = null;

           try {
               file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
               if(julia) {
                   settings = new SettingsJulia(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, color_intensity, function, bailout, inverse_plane, burning_ship, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
               }
               else {
                   settings = new SettingsFractals(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, color_intensity, function, bailout, inverse_plane, burning_ship, z_exponent, color_cycling_location);
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

       repaint();

   }

   private void loadSettings() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       int returnVal = file_chooser.showDialog(ptr, "Load");

       if(returnVal == JFileChooser.APPROVE_OPTION) {
           File file = file_chooser.getSelectedFile();
           ObjectInputStream file_temp = null;
           try {
               file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
               settings = (SettingsFractals) file_temp.readObject();
               
               String temp = "" + settings.getClass();
               temp = temp.substring(6, temp.length()); 
               
               palette[color_choice].setSelected(false);
               palette[color_choice].setEnabled(true);

               fractal_functions[function].setSelected(false);
               fractal_functions[function].setEnabled(true);
               
               if(temp.equals("SettingsJulia")) {
                   xJuliaCenter = ((SettingsJulia) settings).getXJuliaCenter();
                   yJuliaCenter = ((SettingsJulia) settings).getYJuliaCenter();
                   julia = true;
                   first_seed = false;
                   julia_opt.setSelected(true);
                   fractal_functions[NEWTON3].setEnabled(false);
                   fractal_functions[NEWTON4].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
               }
               else {
                   julia = false;
                   first_seed = true;
                   julia_opt.setSelected(false);
                   fractal_functions[NEWTON3].setEnabled(true);
                   fractal_functions[NEWTON4].setEnabled(true);
                   fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                   fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
               }
               
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
               inverse_plane = settings.getInversePlane();

               out_coloring_algorithm = settings.getOutColoringAlgorithm();

               color_intensity = settings.getColorIntensity();

               switch (out_coloring_algorithm) {

                   case NORMAL_COLOR:
                       normal_color_opt.setEnabled(false);
                       normal_color_opt.setSelected(true);
                       smooth_color_opt.setEnabled(true);
                       smooth_color_opt.setSelected(false);
                       binary_decomposition_opt.setEnabled(true);
                       binary_decomposition_opt.setSelected(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
                       biomorph_opt.setEnabled(true);
                       biomorph_opt.setSelected(false);
                       cross_orbit_traps_opt.setEnabled(true);
                       cross_orbit_traps_opt.setSelected(false);
                       
                       if(!burning_ship && !julia) {
                           fractal_functions[NEWTON3].setEnabled(true);
                           fractal_functions[NEWTON4].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       }
                       break;
                   case SMOOTH_COLOR:
                       smooth_color_opt.setEnabled(false);
                       smooth_color_opt.setSelected(true);
                       normal_color_opt.setEnabled(true);
                       normal_color_opt.setSelected(false);
                       binary_decomposition_opt.setEnabled(true);
                       binary_decomposition_opt.setSelected(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
                       biomorph_opt.setEnabled(true);
                       biomorph_opt.setSelected(false);
                       cross_orbit_traps_opt.setEnabled(true);
                       cross_orbit_traps_opt.setSelected(false);
                       
                       if(!burning_ship && !julia) {
                           fractal_functions[NEWTON3].setEnabled(true);
                           fractal_functions[NEWTON4].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       }
                       break;
                   case BINARY_DECOMPOSITION:
                       binary_decomposition_opt.setEnabled(false);
                       binary_decomposition_opt.setSelected(true);
                       normal_color_opt.setEnabled(true);
                       normal_color_opt.setSelected(false);
                       smooth_color_opt.setEnabled(true);
                       smooth_color_opt.setSelected(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
                       biomorph_opt.setEnabled(true);
                       biomorph_opt.setSelected(false);
                       cross_orbit_traps_opt.setEnabled(true);
                       cross_orbit_traps_opt.setSelected(false);
                       
                       if(!burning_ship && !julia) {
                           fractal_functions[NEWTON3].setEnabled(true);
                           fractal_functions[NEWTON4].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       }
                       break;
                   case ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(true);
                       normal_color_opt.setEnabled(true);
                       normal_color_opt.setSelected(false);
                       smooth_color_opt.setEnabled(true);
                       smooth_color_opt.setSelected(false);
                       binary_decomposition_opt.setEnabled(true);
                       binary_decomposition_opt.setSelected(false);
                       biomorph_opt.setEnabled(true);
                       biomorph_opt.setSelected(false);
                       cross_orbit_traps_opt.setEnabled(true);
                       cross_orbit_traps_opt.setSelected(false);
                       
                       if(!burning_ship && !julia) {
                           fractal_functions[NEWTON3].setEnabled(true);
                           fractal_functions[NEWTON4].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       }
                       break;
                   case BIOMORPH:
                       biomorph_opt.setEnabled(false);
                       biomorph_opt.setSelected(true);
                       normal_color_opt.setEnabled(true);
                       normal_color_opt.setSelected(false);
                       smooth_color_opt.setEnabled(true);
                       smooth_color_opt.setSelected(false);
                       binary_decomposition_opt.setEnabled(true);
                       binary_decomposition_opt.setSelected(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
                       cross_orbit_traps_opt.setEnabled(true);
                       cross_orbit_traps_opt.setSelected(false);
                       fractal_functions[NEWTON3].setEnabled(false);
                       fractal_functions[NEWTON4].setEnabled(false);
                       fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                       fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
                       break;
                   case CROSS_ORBIT_TRAPS:
                       cross_orbit_traps_opt.setEnabled(false);
                       cross_orbit_traps_opt.setSelected(true);
                       normal_color_opt.setEnabled(true);
                       normal_color_opt.setSelected(false);
                       smooth_color_opt.setEnabled(true);
                       smooth_color_opt.setSelected(false);
                       binary_decomposition_opt.setEnabled(true);
                       binary_decomposition_opt.setSelected(false);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
                       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
                       biomorph_opt.setEnabled(true);
                       biomorph_opt.setSelected(false);
                       
                       periodicity_checking_opt.setEnabled(false);
                       periodicity_checking_opt.setSelected(false);
                       periodicity_checking = false;
                       
                       if(!burning_ship && !julia) {
                           fractal_functions[NEWTON3].setEnabled(true);
                           fractal_functions[NEWTON4].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
                           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
                       }
                       
                       break;

               }
            
               if(burning_ship) {
                   fractal_functions[LAMBDA].setEnabled(false);
                   fractal_functions[MAGNET1].setEnabled(false);
                   fractal_functions[MAGNET2].setEnabled(false);
                   fractal_functions[NEWTON3].setEnabled(false);
                   fractal_functions[NEWTON4].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
                   fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
                   fractal_functions[BARNSLEY1].setEnabled(false);
                   fractal_functions[BARNSLEY2].setEnabled(false);
                   fractal_functions[BARNSLEY3].setEnabled(false);
                   fractal_functions[MANDELBAR].setEnabled(false);
                   fractal_functions[SPIDER].setEnabled(false);
                   fractal_functions[PHOENIX].setEnabled(false);
               }


               switch (function) {
                   case MANDELBROTNTH:
                       z_exponent = settings.getZExponent();
                       fractal_functions[function].setSelected(true);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       burning_ship_opt.setEnabled(true);
                       break;
                   case LAMBDA:
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MAGNET1:
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MAGNET2:
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case NEWTON3:
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       burning_ship_opt.setEnabled(false);
                       julia_opt.setEnabled(false);
                       biomorph_opt.setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       break;
                   case NEWTON4:
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       burning_ship_opt.setEnabled(false);
                       julia_opt.setEnabled(false);
                       biomorph_opt.setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       break;
                   case NEWTONGENERALIZED3:
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       burning_ship_opt.setEnabled(false);
                       julia_opt.setEnabled(false);
                       biomorph_opt.setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       break;
                   case NEWTONGENERALIZED8:
                       burning_ship_opt.setEnabled(false);
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       burning_ship_opt.setEnabled(false);
                       julia_opt.setEnabled(false);
                       biomorph_opt.setEnabled(false);
                       periodicity_checking_opt.setEnabled(false);
                       bailout_number.setEnabled(false);
                       break;
                   case BARNSLEY1:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case BARNSLEY2:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case BARNSLEY3:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case MANDELBAR:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case SPIDER:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   case PHOENIX:
                       burning_ship_opt.setEnabled(false);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
                   default:
                       burning_ship_opt.setEnabled(true);
                       if(out_coloring_algorithm != BIOMORPH) {
                           biomorph_opt.setEnabled(true);
                       }
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(false);
                       break;
               }

               
               palette[color_choice].setSelected(true);
               palette[color_choice].setEnabled(false);
                        
               burning_ship_opt.setSelected(burning_ship);

               inverse_plane_opt.setSelected(inverse_plane);

               setOptions(false);

               reloadTitle();

               progress.setValue(0);

               scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
               scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

               superPaint();

               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics g = image.getGraphics();
               g.setColor(Color.WHITE);
               g.fillRect(0, 0, image_size, image_size);

               backup_orbit = null;

               whole_image_done = false;

               createThreads();

               startThreads(n);
           }
           catch(IOException ex) {
               JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
           catch(ClassNotFoundException ex) {
               JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }

           try {
               file_temp.close();
           }
           catch(Exception ex) {}
       }

   }

   private void saveImage() {

       repaint();

       JFileChooser file_chooser = new JFileChooser(".");

       Calendar calendar = new GregorianCalendar();
       file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".bmp"));

       int returnVal = file_chooser.showDialog(ptr, "Save Image");

       last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics2D graphics = last_used.createGraphics();
       graphics.drawImage(image, 0, 0, null);

       if(grid) {
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
               else {
                   if(temp.equalsIgnoreCase("png")) {
                       ImageIO.write(last_used, "png", file);
                   }
                   else {
                       if(temp.equalsIgnoreCase("jpg")) {
                           ImageIO.write(last_used, "jpg", file);
                       }
                       else {
                           JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                       }
                   }
               }
           }
           catch (IOException ex) {}
                     
       }

       last_used = null;

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       
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
            case PHOENIX:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
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

        superPaint();

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image_size, image_size);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();
       
        startThreads(n);

   }

   private void goToFractal() {

        if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
        }

        repaint();


        String ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Real: " + xCenter + "\nSet the Real part of the new center.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, xCenter);

        double tempReal = xCenter;
        double tempImaginary = yCenter;

        try {
            xCenter = Double.parseDouble(ans);
        }
        catch(Exception ex) {
            if(ans == null) {
                repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
                return;
            }
        }

        repaint();
        if(yCenter == 0) {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Imaginary: " + 0.0 + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
        }
        else {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Imaginary: " + (-yCenter) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -yCenter);
        }

        try {
            yCenter = -Double.parseDouble(ans); //reversed axis
        }
        catch(Exception ex) {
            if(ans == null) {
                xCenter = tempReal;
                repaint();
                return;
            }
            else {
                xCenter = tempReal;
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
                return;
            }
        }

        repaint();
        ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Size: " + size + "\nSet the new size.", "Size", JOptionPane.QUESTION_MESSAGE, null, null, size);

        try {
            size = Double.parseDouble(ans);
            
            setOptions(false);

            reloadTitle();

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            superPaint();

            image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, image_size, image_size);

            backup_orbit = null;

            whole_image_done = false;

            createThreads();
 
            startThreads(n);
        }
        catch(Exception ex) {
            if(ans == null) {
                xCenter = tempReal;
                yCenter = tempImaginary;
                repaint();
            }
            else {
                xCenter = tempReal;
                yCenter = tempImaginary;
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
            }
        }                

   }

   private void goToJulia() {

        if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
        }

        repaint();
        String ans;

        if(first_seed) {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Real part of the Julia seed.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, xCenter);
        }
        else {
            ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Real seed: " + xJuliaCenter + "\nCurrent Real: " + xCenter + "\nSet the Real part of the new center.", "Real", JOptionPane.QUESTION_MESSAGE, null, null, xCenter);
        }

        double tempReal = xCenter;
        double tempImaginary = yCenter;

        try {
            if(first_seed) { 
                xJuliaCenter = Double.parseDouble(ans);                            
            }
            else {
                xCenter = Double.parseDouble(ans);
            }
        }
        catch(Exception ex) {
            if(ans == null) {
                repaint();
                return;
            }
            else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
                return;
            }
        }

        repaint();
        if(first_seed) {
            if(yCenter == 0) {
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Imaginary part of the Julia seed.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
            }
            else {
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Set the Imaginary part of the Julia seed.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -yCenter);
            }
        }
        else {
            if(yCenter == 0) {
                if(yJuliaCenter == 0) {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (0.0) + "\nCurrent Imaginary: " + (0.0) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
                }
                else {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (-yJuliaCenter) + "\nCurrent Imaginary: " + (0.0) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, 0.0);
                }
            }
            else {
                if(yJuliaCenter == 0) {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (0.0) + "\nCurrent Imaginary: " + (-yCenter) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -yCenter);
                }
                else {
                    ans = (String) JOptionPane.showInputDialog(scroll_pane, "Julia Imaginary seed: " + (-yJuliaCenter) + "\nCurrent Imaginary: " + (-yCenter) + "\nSet the Imaginary part of the new center.", "Imaginary", JOptionPane.QUESTION_MESSAGE, null, null, -yCenter);
                }
            }
        }

        try {
            if(first_seed) {
                yJuliaCenter = -Double.parseDouble(ans);
            }
            else {
                yCenter = -Double.parseDouble(ans); //reversed axis
            }
        }
        catch(Exception ex) {
            if(ans == null) {
                xCenter = tempReal;
                repaint();
                return;
            }
            else {
                xCenter = tempReal;
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
                return;
            }
        }
        
        try {
            if(!first_seed) {
                repaint();
                ans = (String) JOptionPane.showInputDialog(scroll_pane, "Current Size: " + size + "\nSet the new size.", "Size", JOptionPane.QUESTION_MESSAGE, null, null, size);

                size = Double.parseDouble(ans);

                setOptions(false);

                reloadTitle();

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                superPaint();

                image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
                Graphics g = image.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, image_size, image_size);

                backup_orbit = null;

                whole_image_done = false;

                createThreads();

                startThreads(n);
            }
            else {
                first_seed = false;
                defaultFractalSettings();
            }
        }
        catch(Exception ex) {
            if(ans == null) {
                xCenter = tempReal;
                yCenter = tempImaginary;
                repaint();
            }
            else {
                xCenter = tempReal;
                yCenter = tempImaginary;
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                repaint();
            }
        }

   }

   private void setFractalColor() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       setEnabled(false);
       int color_window_width = 440;
       int color_window_height = 415;
       fract_color_frame = new JFrame("Fractal Color");
       fract_color_frame.setLayout(new FlowLayout());
       fract_color_frame.setSize(color_window_width, color_window_height);
       fract_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       fract_color_frame.setResizable(false);
       color_chooser = new JColorChooser();

       fract_color_frame.add(color_chooser);

       fract_color_frame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {

               setEnabled(true);
               fract_color_frame.dispose();
               repaint();

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

               scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
               scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

               superPaint();

               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics g = image.getGraphics();
               g.setColor(Color.WHITE);
               g.fillRect(0, 0, image_size, image_size);

               backup_orbit = null;

               whole_image_done = false;

               createThreadsPaletteAndFilter();

               startThreads(n);

           }
       });

       JButton close = new JButton("Cancel");
       close.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {

               setEnabled(true);
               fract_color_frame.dispose();
               repaint();

           }
       });

       fract_color_frame.add(ok);
       fract_color_frame.add(close);

       fract_color_frame.setVisible(true);
       repaint();

   }

   private void setIterations() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using maximum " + max_iterations + " iterations.\nEnter the new maximum Iterations number.", "Iterations Number", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 1) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Iterations number need to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           max_iterations = temp;

           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new maximum Iterations number is " + max_iterations + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreads();

           startThreads(n);

       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }
       
   }

   private void setZoomingFactor() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + zoom_factor + " for Zooming Factor.\nEnter the new Zooming Factor.", "Zooming Factor", JOptionPane.QUESTION_MESSAGE);

       try {
           Double temp = Double.parseDouble(ans);

           if(temp <= 1.05) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Zooming Factor needs to be greater than 1.05.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           zoom_factor = temp;

           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Zooming Factor is " + zoom_factor + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }
       
   }

   private void setThreadsNumber() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + n * n + " Threads.\nEnter the new Number of Threads, n \n(nxn)", "Threads Number", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 1) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Threads Number needs to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           n = temp;
           threads = new ThreadPaint[n][n];
           
           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Threads Number is " + n + "x" + n + " = " + n * n + " .", "Info", JOptionPane.INFORMATION_MESSAGE);
       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }
       
   }

   private void setPalette(int temp) {
     
       color_cycling_location = 0;

       palette[color_choice].setSelected(false);
       palette[color_choice].setEnabled(true);

       color_choice =  temp;
       palette[color_choice].setEnabled(false);

      
       if(color_choice == 0 || color_choice == 1 || color_choice == 2 || color_choice == 3 || color_choice == 4) {
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

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreadsPaletteAndFilter();

       startThreads(n);

   }

   private void setAntiAliasing() {

       if(!anti_aliasing_opt.isSelected()) {
           anti_aliasing = false;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();
 
           startThreads(n);
       }
       else {
           anti_aliasing = true;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           startThreads(n);

       }
       
   }

   private void setEdges() {

       if(!edges_opt.isSelected()) {
           edges = false;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();
   
           startThreads(n);

       }
       else {
           edges = true;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           startThreads(n);

       }

   }

   private void setEmboss() {

       if(!emboss_opt.isSelected()) {
           emboss = false;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           startThreads(n);
       }
       else {
           emboss = true;

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           startThreads(n);

       }

   }

   private void setGrid() {

       if(!grid_opt.isSelected()) {
           grid = false;
           repaint();
       }
       else {
           grid = true;
           repaint();          
       }

   }

   private void setImageBuffering() {

       if(!image_buffering_opt.isSelected()) {
           image_buffering = false;
           repaint();
       }
       else {
           image_buffering = true;
           repaint();
       }
       
   }

   private void setPeriodicityChecking() {

       if(!periodicity_checking_opt.isSelected()) {
           periodicity_checking = false;
           cross_orbit_traps_opt.setEnabled(true);
           repaint();
       }
       else {
           periodicity_checking = true;
           cross_orbit_traps_opt.setEnabled(false);
           repaint();
       }

   }


   private void setSettingsFractal(MouseEvent e) {

       if(!threadsAvailable()) {
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

       if(!image_buffering) {
           superPaint();
       }

       last_used = image;

       
       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);

       if(!image_buffering) {
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);
       }
       
       backup_orbit = null;

       whole_image_done = false;

       createThreads();

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
           xJuliaCenter = xCenter - size / 2 + size * main_panel.getMousePosition().getX() / image_size;
           yJuliaCenter = yCenter - size / 2 + size * main_panel.getMousePosition().getY() / image_size;
           first_seed = false;
           orbit_opt.setEnabled(true);
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
           
           if(!image_buffering) {
               superPaint();
           }

           last_used = image;

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);

           if(!image_buffering) {
               Graphics g = image.getGraphics();
               g.setColor(Color.WHITE);
               g.fillRect(0, 0, image_size, image_size);
           }

           backup_orbit = null;

           whole_image_done = false;

           createThreads();

           startThreads(n);

       }

   }

   private void setJuliaOption() {

       if(!julia_opt.isSelected()) {
           julia = false;
           fast_julia_image = null;
           if(!burning_ship && out_coloring_algorithm != BIOMORPH) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
           }
           if(!first_seed) {
               defaultFractalSettings();
           }
           else {
               setOptions(true);
               if(image_size < FAST_JULIA_IMAGE_SIZE) {
                   superPaint();
               }
               repaint();
           }
       }
       else {
           julia = true;
           first_seed = true;
           fast_julia_image = null;
           orbit_opt.setEnabled(false);
           fractal_functions[NEWTON3].setEnabled(false);
           fractal_functions[NEWTON4].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
           setOptions(false);
       }

   }

   private void setOrbitOption() {

       if(!orbit_opt.isSelected()) {
           orbit = false;
           if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
               julia_opt.setEnabled(true);
           }
           color_cycling_opt.setEnabled(true);
           if(backup_orbit != null) {
               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics2D graphics = image.createGraphics();
               graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
           }
           backup_orbit = null;
           last_used = null;
       }
       else {
           orbit = true;
           julia_opt.setEnabled(false);
           color_cycling_opt.setEnabled(false);
           backup_orbit = null;
           last_used = null;
       }

       repaint();

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
               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics2D graphics = image.createGraphics();
               graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
           }
           else {
               backup_orbit = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics2D graphics = backup_orbit.createGraphics();
               graphics.drawImage(image, 0, 0, image_size, image_size, null);
           }
           try {
               if(julia) {
                   pixels_orbit = new PaintOrbit(xCenter, yCenter, size, max_iterations, (int)main_panel.getMousePosition().getX(), (int)main_panel.getMousePosition().getY(), image, ptr, orbit_color, orbit_style, inverse_plane, burning_ship, grid, function , z_exponent, xJuliaCenter, yJuliaCenter);
                   pixels_orbit.start();
               }
               else {
                   pixels_orbit = new PaintOrbit(xCenter, yCenter, size, max_iterations, (int)main_panel.getMousePosition().getX(), (int)main_panel.getMousePosition().getY(), image, ptr, orbit_color, orbit_style, inverse_plane, burning_ship, grid, function , z_exponent);
                   pixels_orbit.start();
               }            
           }
           catch(Exception ex) {}
       }

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

   private void zoomIn() {
     
       size /= zoom_factor;

       setOptions(false);

       progress.setValue(0);

       reloadTitle();

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       if(!image_buffering) {
           superPaint();
       }

       last_used = image;

       if(!grid) {
           repaint();
       }

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);

       if(!image_buffering) {
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);
       }

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);

   }

   private void zoomOut() {
      
       size *= zoom_factor;

       setOptions(false);

       progress.setValue(0);

       reloadTitle();

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       if(!image_buffering) {
           superPaint();
       }

       last_used = image;

       if(!grid) {
           repaint();
       }

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       
       if(!image_buffering) {
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);
       }

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);
       
   }

   public void setOptions(Boolean option) {

       starting_position.setEnabled(option);
       load_settings.setEnabled(option);
       save_image.setEnabled(option);

       if(!julia || !first_seed) {
           go_to.setEnabled(option);
       }

       fractal_functions_menu.setEnabled(option);
       colors_menu.setEnabled(option);
       iterations.setEnabled(option);
       size_of_image.setEnabled(option);

       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           bailout_number.setEnabled(option);          
       }
       
       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8 && out_coloring_algorithm != CROSS_ORBIT_TRAPS) {
           periodicity_checking_opt.setEnabled(option); 
       }

       thread_number.setEnabled(option);

       if(((!julia && !orbit) || (!first_seed && !orbit)) && (function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8)) {
           julia_opt.setEnabled(option);
       }

       if(!orbit && !color_cycling) {
           color_cycling_opt.setEnabled(option);
       }

       image_buffering_opt.setEnabled(option);
       anti_aliasing_opt.setEnabled(option);
       edges_opt.setEnabled(option);
       emboss_opt.setEnabled(option);
       grid_opt.setEnabled(option);
       zoom_in.setEnabled(option);
       zoom_out.setEnabled(option);
       orbit_opt.setEnabled(option);
       inverse_plane_opt.setEnabled(option);

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

   private void showFileHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>File Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Starting Position</b></font>, restores the default position and size for each fractal.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Go To</b></font>, lets you, accurate, choose the center and the size.<br>"
                        + "When Julia option is selected for the first time, Go To lets you set the Julia's seed.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Zoom In</b></font>, lets you zoom in with a fixed zooming factor.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Zoom Out</b></font>, lets you zoom out with a fixed zooming factor.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Save As</b></font>, saves the center, size, fractal color, palette, out coloring mode,<br>"
                        + "color intensity, iterations, bailout, fractal function, and julia settings.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Load</b></font>, loads the center, size, fractal color, palette, out coloring mode,<br>"
                        + "color intensity, iterations, bailout, fractal function, and julia settings.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Save Image As</b></font>, lets you save a *.bmp, *.jpg, *.png image.<br>"
                        + "You just need to type the right extention for each format on the displayed window.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Exit</b></font>, exits the application.</html>", "File Menu Help", JOptionPane.INFORMATION_MESSAGE);
       
   }

   private void showOptionsHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Options Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Fractal Functions Menu</b></font>, lets you choose the function generating the fractal.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Inverse Plane</b></font>, lets you change the plane to 1/mu.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Colors Menu</b></font>, lets you set the color options.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Image Size</b></font>, lets you change the size of the image, both dimensions will be the same.<br>"
                        + "Increasing the size of the image will improve the image's quality,<br>"
                        + "but its going to be needed more time, for the image, to draw.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Iterations</b></font>, lets you set the maximun iterations.<br>"
                        + "Increasing the iterations will slow down the drawing process, but in some cases<br>"
                        + "will increase the quality of the produced image.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Bailout</b></font>, lets you set the value in which the magnitude of the complex number<br>"
                        + "is not considered bounded anymore. Bailout will reset to default every time use choose a new<br>"
                        + "function, julia set or inversed plane.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Zooming Factor</b></font>, lets you change the rate of each zoom in or zoom out.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Threads</b></font>, lets you set the number of threads (nxn).<br>"
                        + "The drawing process will split in parallel drawing pieces.<br>"
                        + "For instance typing 2 will create 2x2 = 4 threads and so on and so forth.<br>"
                        + "Increasing the threads will make the drawing go faster, especially if the generated image contains<br>"
                        + "alot of bounded areas, since the threads that draw areas that are not bounded will finish very quickly.<br>"
                        + "Ofcourse there is a drawback, your processor! At some point the 'context switches' of that many threads<br>"
                        + "will slow you down, so you need to find the perfect balance.<br>"
                        + "You need to use the same or more threads than your computer's processors.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Periodicity Checking</b></font>, detects periodical orbits, so the iterating process can escape from them<br>"
                        + "in fewer iterations, speeding up the drawing. It is recommended to use this option when the image<br>"
                        + "contains alot of bounded areas. In the opposite case the drawing will be slowed down.<br>"
                        + "While using this option, Cross Orbit Traps will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Tools Options Menu</b></font>, lets you change the options for the tools.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Image Buffering</b></font>, will only display the fully generated image, instead of<br>"
                        + "displaying the drawing process. Enabling the option will also speed up the process.</html>", "Options Menu Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void showFunctionsHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Functions Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Mandelbrot z = z^2 + c</b></font>, is the basic Mandelbrot function.<br>"
                        + "You can choose some variations of the function, choosing some of the default exponents 3 - 10.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Mandelbrot z = z^N + c</b></font>, lets you manually choose the exponent of z, to any real number.<br>"
                        + "Using this function will will increase the image's drawing time.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Burning Ship</b></font>, lets you change the function to z = (|Re(z)| + i|Im(z)|)^k + c, where k is any of the default or real exponents.<br>"
                        + "While using this function, Lambda, Magnet, Newton, Barnsley, Mandelbar, Spider, and Phoenix functions will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Lambda</b></font>, lets you change the function to z = lambda*z*(1 - z).<br>"
                        + "While using this function, Burning Ship function will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Magnet 1</b></font>, lets you change the function to z = [(z^2 + c - 1) / (2*z + c - 2)]^2.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Magnet 2</b></font>, lets you change the function to z = [(z^3 + 3*(c - 1)*z + (c - 1)*(c - 2)) / (3*z^2 + 3*(c - 2)*z + (c - 1)*(c - 2) + 1)]^2.<br>"
                        + "While using the Magnet functions, Burning Ship function will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Newton 3</b></font>, lets you change the function to z = z - p(z) / p'(z), where p(z) = z^3 - 1.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Newton 4</b></font>, lets you change the function to z = z - p(z) / p'(z), where p(z) = z^4 - 1.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Newton Generalized 3</b></font>, lets you change the function to z = z - p(z) / p'(z), where p(z) = z^3 - 2*z + 2.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Newton Generalized 8</b></font>, lets you change the function to z = z - p(z) / p'(z), where p(z) = z^8 + 15*z^4 - 16.<br>"
                        + "While using the Newton functions, Bailout option, Burning Ship function, Periodicity Checking, Biomorph, and Julia option will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Barnsley 1</b></font>, lets you change the function to if(Re(z) >= 0) then z = (z - 1)*c else z = (z + 1)*c.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Barnsley 2</b></font>, lets you change the function to if(Re(z)*Im(c) + Re(c)*Im(z) >= 0) then z = (z + 1)*c else z = (z - 1)*c.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Barnsley 3</b></font>, lets you change the function to if(Re(z) > 0) then z = z^2 - 1 else z = z^2 + Re(c)*Re(z) + Im(c)*Re(z) - 1.<br>"
                        + "While using the Barnsley functions, Burning Ship function will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Mandelbar</b></font>, lets you change the function to z = (z')^2 + c, where z' is the conjugate of the complex number.<br>"
                        + "While using this function, Burning Ship function will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Spider</b></font>, lets you change the function to z = z^2 + c, c = c/2 + z.<br>"
                        + "While using this function, Burning Ship function will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Phoenix</b></font>, lets you change the function to temp = z^2 + k, s = z, z = temp, where k = (Im(c) * Re(s) + Re(c)) +i(Im(c) * Im(s)).<br>"
                        + "While using this function, Burning Ship function will be disabled.</html>", "Functions Menu Help", JOptionPane.INFORMATION_MESSAGE);


   }

   private void showColorsHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Colors Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Fractal Color</b></font>, lets you set a color corresponding to the maximum iterations.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Color Palette Menu</b></font>, lets you set a palette corresponding to how fast the magnitude of the<br>"
                        + "complex number is getting greater than the bailout value, or how fast it converges to some value.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Out Coloring Mode Menu</b></font>, lets you set how to display the areas outside the set.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Color Intensity</b></font>, lets you set the intensity of the colors in the current palette.</html>", "Colors Menu Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void showPaletteHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Palette Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Default</b></font>, is the Default coloring palette based on spectrum.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Alternative</b></font>, is an alternative coloring palette.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Alternative2</b></font>, is an alternative coloring palette.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>GreenWhite</b></font>, is a palette based on the colors green and white.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Blue</b></font>, is a palette based on the blue color.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Spectrum</b></font>, is a palette based on the spectrum of colors.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Pale Spectrum</b></font>, is a palette based on paled version of the spectrum of colors.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Gray Scale</b></font>, is a palette based on gray scale.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Cyclic Gray Scale</b></font>, is a palette based on gray scale.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Cyclic Red Cyan</b></font>, is a palette based on red and blue.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Earth Sky</b></font>, is a palette based on the colors of earth and sky.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Hot Cold</b></font>, is a palette based on the colors of hot and cold.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Fire</b></font>, is a palette based on the colors of fire.</html>", "Palette Menu Help", JOptionPane.INFORMATION_MESSAGE);
                        

   }

   private void showOutColoringModeHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Out Coloring Mode Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Iterations</b></font>, is the default way of coloring areas outside the set. This means that<br>"
                        + "the number of iterations needed, for a complex number to be greater than the bailout, is used.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Smooth Color</b></font>, smooths the image, so the color bands are not easily visible.<br>"
                        + "Increasing the bailout will also increase the color smoothing.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Binary Decomposition</b></font>, uses the number of iterations if Im(z) >= 0 else iterations + 50.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Iterations + Re + Im + Re/Im</b></font>, uses the iterations needed for the<br>"
                        + "complex number to be greater than the bailout + Re(z) + Im(z) + Re(z)/Im(z)."
                        + "<li><font size='4' face='arial' color='red'><b>Biomorph</b></font>, uses the iterations needed for the complex number to be greater<br>"
                        + "than the bailout if the Re(z) or the Im(z) is inside the boundaries of bailout else iterations + 50.<br>"
                        + "While using this option, Newton functions will be disabled.<br>"
                        + "<li><font size='4' face='arial' color='red'><b>Cross Orbit Traps</b></font>, checks if the orbit of the complex number is trapped and paints<br>"
                        + "accordingly. While using this option, periodicity checking will be disabled.</html>", "Out Coloring Mode Menu Help", JOptionPane.INFORMATION_MESSAGE);


   }

   private void showToolsOptionsHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Tools Options Menu</u></b></center></font><br><br>"
               + "<li><font size='4' face='arial' color='red'><b>Orbit Menu</b></font>, lets you change the options about orbit.<br><br>"
               + "<li><font size='4' face='arial' color='red'><b>Julia Preview Image Filters</b></font>, will enable the image filters that you use<br>"
               + "on the normal image, so Julia's preview will also be filtered by them.<br>"
               + "Using the filters will make the preview slower.<br><br>"
               + "<li><font size='4' face='arial' color='red'><b>Grid Color</b></font>, lets you change the color of the drawn grid.</html>", "Tools Options Menu Help", JOptionPane.INFORMATION_MESSAGE);


   }

   private void showOrbitHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Orbit Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Orbit Color</b></font>, lets you change the color of the drawn orbit.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Orbit Style</b></font>, lets you change the style of the drawing orbit to dots or lines.</html>", "Orbit Menu Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void showToolsHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Tools Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Orbit</b></font>, lets you draw the orbit of a complex number.<br>"
                        + "If the orbit option is selected, then pressing left mouse click or dragging<br>"
                        + "on the image will generate the orbit of the complex number corresponding to the<br>"
                        + "mouse's location on the image. Since there are 2 types of sets, the orbit of<br>"
                        + "a complex number could be either escaping or staying bounded following the same pattern.<br>"
                        + "In some cases the complex number will converge to a fixed value,<br>"
                        + "like Magnet functions or Newton functions.<br>"
                        + "The Julia option will be disabled while the orbit option is selected.<br>"
                        + "You need to have a drawn Julia set to generate its orbit.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Julia</b></font>, lets you draw an image based on a complex number (seed).<br>"
                        + "When Julia option is selected, you can preview the Julia set corresponding<br>"
                        + "to the seed generated from your mouse's location, by moving the mouse. If you left click to<br>"
                        + "that location or set the seed from Go To in the File Menu, the Julia set will be drawn, more detailed.<br>"
                        + "The previews are generated, using only 150 iterations.<br>"
                        + "All the basic options will be disabled while waiting for you to pick a seed.<br>"
                        + "Deselecting the option will enable them.<br>"
                        + "While Using the Julia Option all the Newton functions will be disabled.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Color Cycling</b></font>, lets you animate your image.<br>"
                        + "It can also let you 'move' on the current palette's colors.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Show Grid</b></font>, will draw a cordinated grid.<br>"
                        + "While using this option, it is recommended to use image size greater than 600 or the grid<br>"
                        + "numbers will not be easily distinguishable.</html>", "Tools Menu Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void showFiltersHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Filters Menu</u></b></center></font><br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Anti-Aliasing</b></font>, is a filter that smooths the image.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Edge Detection</b></font>, is a filter that detects edges.<br><br>"
                        + "<li><font size='4' face='arial' color='red'><b>Emboss</b></font>, is a filter that edits the image, so bright areas are raised and dark ones are carved.</html>", "Filters Menu Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void showMouseClicksHelp() {

       if(image_size < 426) {
           super.paint(getGraphics());
       }
       repaint();
       JOptionPane.showMessageDialog(scroll_pane, "<html><font size='5' face='arial' color='blue'><center><b><u>Mouse Clicks</u></b></center></font><br><br>"
               + "<li><font size='4' face='arial' color='red'><b>Left Mouse Click</b></font>, lets you zoom in with a fixed zooming factor.<br>"
               + "If Julia option is selected for the first time, left mouse click lets you choose the seed.<br>"
               + "If Orbit option is selected, left mouse click or drag lets you draw the selected<br>"
               + "complex number orbit.<br><br>"
               + "<li><font size='4' face='arial' color='red'><b>Right Mouse Click</b></font>, lets you zoom out with a fixed zooming factor.<br><br>"
               + "While zooming in or out, the image will recenter to the chosen position.</html>", "Mouse Clicks Help", JOptionPane.INFORMATION_MESSAGE);

   }

   private void setOrbitColor() {

       setEnabled(false);
       int color_window_width = 440;
       int color_window_height = 415;
       orbit_color_frame = new JFrame("Orbit Color");
       orbit_color_frame.setLayout(new FlowLayout());
       orbit_color_frame.setSize(color_window_width, color_window_height);
       orbit_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       orbit_color_frame.setResizable(false);
       color_chooser = new JColorChooser();

       orbit_color_frame.add(color_chooser);

       orbit_color_frame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {

               setEnabled(true);
               orbit_color_frame.dispose();
               repaint();

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
               repaint();

           }
       });

       orbit_color_frame.add(ok);
       orbit_color_frame.add(close);

       orbit_color_frame.setVisible(true);
       repaint();

   }


   private void setGridColor() {

       setEnabled(false);
       int color_window_width = 440;
       int color_window_height = 415;
       grid_color_frame = new JFrame("Orbit Color");
       grid_color_frame.setLayout(new FlowLayout());
       grid_color_frame.setSize(color_window_width, color_window_height);
       grid_color_frame.setLocation((int)(getLocation().getX() + getSize().getWidth() / 2) - (color_window_width / 2), (int)(getLocation().getY() + getSize().getHeight() / 2) - (color_window_height / 2));
       grid_color_frame.setResizable(false);
       color_chooser = new JColorChooser();

       grid_color_frame.add(color_chooser);

       grid_color_frame.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {

               setEnabled(true);
               grid_color_frame.dispose();
               repaint();

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
               repaint();

           }
       });

       grid_color_frame.add(ok);
       grid_color_frame.add(close);

       grid_color_frame.setVisible(true);
       repaint();

   }

   private void setFunction(int temp) {

       fractal_functions[function].setSelected(false);
       fractal_functions[function].setEnabled(true);

       int temp2 = function;
       function =  temp;

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       switch (function) {
           case MANDELBROTNTH:
               repaint();
               String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the exponent of z.\nThe exponent can be a real number.", "Exponent", JOptionPane.QUESTION_MESSAGE);

               try {
                   z_exponent = Double.parseDouble(ans);
               }
               catch(Exception ex) {
                   if(ans == null) {
                       repaint();
                   }
                   else {
                       JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                       repaint();
                   }
                   fractal_functions[function].setSelected(false);

                   if(function != temp2) {
                       fractal_functions[temp2].setSelected(true);
                       fractal_functions[temp2].setEnabled(false);
                       function = temp2;
                   }
                   else {
                       fractal_functions[function].setSelected(true);
                       fractal_functions[function].setEnabled(true);
                   }
                          
                   return;
               }
           burning_ship_opt.setEnabled(true);
           fractal_functions[function].setSelected(true);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case LAMBDA:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case MAGNET1:         
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case MAGNET2:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case NEWTON3:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           julia_opt.setEnabled(false);
           biomorph_opt.setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           break;
       case NEWTON4:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           julia_opt.setEnabled(false);
           biomorph_opt.setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           break;
       case NEWTONGENERALIZED3:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           julia_opt.setEnabled(false);
           biomorph_opt.setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           break;
       case NEWTONGENERALIZED8:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           julia_opt.setEnabled(false);
           biomorph_opt.setEnabled(false);
           periodicity_checking_opt.setEnabled(false);
           bailout_number.setEnabled(false);
           break;
       case BARNSLEY1:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case BARNSLEY2:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case BARNSLEY3:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case MANDELBAR:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           biomorph_opt.setEnabled(true);
           break;
       case SPIDER:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       case PHOENIX:
           fractal_functions[function].setEnabled(false);
           burning_ship_opt.setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       default:
           burning_ship_opt.setEnabled(true);
           fractal_functions[function].setEnabled(false);
           if(out_coloring_algorithm != BIOMORPH) {
               biomorph_opt.setEnabled(true);
           }
           break;
       }

       defaultFractalSettings();

   }

   private void setBailout() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + bailout + " for Bailout number.\nEnter the new Bailout number", "Bailout Number", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);
           
           if(temp < 2) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Bailout value needs to be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }
           else {
               if(temp > 46341) {
                   repaint();
                   JOptionPane.showMessageDialog(scroll_pane, "Bailout value needs to be lower than 46341.", "Error!", JOptionPane.ERROR_MESSAGE);
                   return;
               }
           }
           bailout = temp;

           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Bailout number is " + bailout + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreads();

           startThreads(n);

       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }

   }

   private void setBurningShipOption() {

       if(!burning_ship_opt.isSelected()) {
           burning_ship = false;

           fractal_functions[LAMBDA].setEnabled(true);
           fractal_functions[MAGNET1].setEnabled(true);
           fractal_functions[MAGNET2].setEnabled(true);
           if(!julia && out_coloring_algorithm != BIOMORPH) {
               fractal_functions[NEWTON3].setEnabled(true);
               fractal_functions[NEWTON4].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
               fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
           }
           fractal_functions[BARNSLEY1].setEnabled(true);
           fractal_functions[BARNSLEY2].setEnabled(true);
           fractal_functions[BARNSLEY3].setEnabled(true);
           fractal_functions[MANDELBAR].setEnabled(true);
           fractal_functions[SPIDER].setEnabled(true);
           fractal_functions[PHOENIX].setEnabled(true);

           defaultFractalSettings();

       }
       else {
           burning_ship = true;

           fractal_functions[LAMBDA].setEnabled(false);
           fractal_functions[MAGNET1].setEnabled(false);
           fractal_functions[MAGNET2].setEnabled(false);
           fractal_functions[NEWTON3].setEnabled(false);
           fractal_functions[NEWTON4].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
           fractal_functions[BARNSLEY1].setEnabled(false);
           fractal_functions[BARNSLEY2].setEnabled(false);
           fractal_functions[BARNSLEY3].setEnabled(false);
           fractal_functions[MANDELBAR].setEnabled(false);
           fractal_functions[SPIDER].setEnabled(false);
           fractal_functions[PHOENIX].setEnabled(false);

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
            case PHOENIX:
                xCenter = 0;
                yCenter = 0;
                size = 6;
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

        superPaint();

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image_size, image_size);

        backup_orbit = null;

        whole_image_done = false;

        createThreads();

        startThreads(n);

   }

   private void setNormalColor() {

       out_coloring_algorithm = NORMAL_COLOR;

       color_intensity = 1;

       normal_color_opt.setEnabled(false);

       smooth_color_opt.setEnabled(true);
       smooth_color_opt.setSelected(false);

       binary_decomposition_opt.setEnabled(true);
       binary_decomposition_opt.setSelected(false);

       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);

       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           biomorph_opt.setEnabled(true);
       }
       biomorph_opt.setSelected(false);
       
       if(!periodicity_checking) {
           cross_orbit_traps_opt.setEnabled(true);
       }
       cross_orbit_traps_opt.setSelected(false);

       if(!burning_ship && !julia && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           fractal_functions[NEWTON3].setEnabled(true);
           fractal_functions[NEWTON4].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
       }

       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);
       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);

   }

   private void setSmoothColor() {
       
       out_coloring_algorithm = SMOOTH_COLOR;

       if(color_choice == 0 || color_choice == 1 || color_choice == 2 || color_choice == 3 || color_choice == 4) {
           color_intensity = 1;
       }
       else {
           color_intensity = 0.02;
       }

       smooth_color_opt.setEnabled(false);
               
       normal_color_opt.setEnabled(true);
       normal_color_opt.setSelected(false);

       binary_decomposition_opt.setEnabled(true);
       binary_decomposition_opt.setSelected(false);

       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);

       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           biomorph_opt.setEnabled(true);
       }
       biomorph_opt.setSelected(false);
       
       if(!periodicity_checking) {
           cross_orbit_traps_opt.setEnabled(true);
       }
       cross_orbit_traps_opt.setSelected(false);

       if(!burning_ship && !julia && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           fractal_functions[NEWTON3].setEnabled(true);
           fractal_functions[NEWTON4].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
       }

       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);
       
   }
   
   private void setBinaryDecomposition() {   
       
       out_coloring_algorithm = BINARY_DECOMPOSITION;

       color_intensity = 1;

       binary_decomposition_opt.setEnabled(false);

       normal_color_opt.setEnabled(true);
       normal_color_opt.setSelected(false);

       smooth_color_opt.setEnabled(true);
       smooth_color_opt.setSelected(false);

       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);

       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           biomorph_opt.setEnabled(true);
       }
       biomorph_opt.setSelected(false);
       
       if(!periodicity_checking) {
           cross_orbit_traps_opt.setEnabled(true);
       }
       cross_orbit_traps_opt.setSelected(false);

       if(!burning_ship && !julia && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           fractal_functions[NEWTON3].setEnabled(true);
           fractal_functions[NEWTON4].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
       }

       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);
         
   }

   private void setIterationsPlusRePlusImPlusReDivideIm() {

       out_coloring_algorithm = ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM;

       color_intensity = 1;

       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(false);

       normal_color_opt.setEnabled(true);
       normal_color_opt.setSelected(false);

       smooth_color_opt.setEnabled(true);
       smooth_color_opt.setSelected(false);

       binary_decomposition_opt.setEnabled(true);
       binary_decomposition_opt.setSelected(false);
           
       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           biomorph_opt.setEnabled(true);
       }
       biomorph_opt.setSelected(false);
       
       if(!periodicity_checking) {
           cross_orbit_traps_opt.setEnabled(true);
       }
       cross_orbit_traps_opt.setSelected(false);
       
       if(!burning_ship && !julia && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           fractal_functions[NEWTON3].setEnabled(true);
           fractal_functions[NEWTON4].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
       }

       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);

   }

   private void setBiomorph() {
       
       out_coloring_algorithm = BIOMORPH;

       color_intensity = 1;

       biomorph_opt.setEnabled(false);

       normal_color_opt.setEnabled(true);
       normal_color_opt.setSelected(false);

       smooth_color_opt.setEnabled(true);
       smooth_color_opt.setSelected(false);

       binary_decomposition_opt.setEnabled(true);
       binary_decomposition_opt.setSelected(false);

       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
       
       if(!periodicity_checking) {
           cross_orbit_traps_opt.setEnabled(true);
       }
       cross_orbit_traps_opt.setSelected(false);

       fractal_functions[NEWTON3].setEnabled(false);
       fractal_functions[NEWTON4].setEnabled(false);
       fractal_functions[NEWTONGENERALIZED3].setEnabled(false);
       fractal_functions[NEWTONGENERALIZED8].setEnabled(false);
       
       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);
      
   }
   
   private void setCrossOrbitTraps() {
       
       out_coloring_algorithm = CROSS_ORBIT_TRAPS;

       color_intensity = 1;
       
       cross_orbit_traps_opt.setEnabled(false);
       
       normal_color_opt.setEnabled(true);
       normal_color_opt.setSelected(false);

       smooth_color_opt.setEnabled(true);
       smooth_color_opt.setSelected(false);

       binary_decomposition_opt.setEnabled(true);
       binary_decomposition_opt.setSelected(false);
       
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setEnabled(true);
       iterations_plus_re_plus_im_plus_re_divide_im_opt.setSelected(false);
           
       if(function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           biomorph_opt.setEnabled(true);
       }
       biomorph_opt.setSelected(false);
       
       
       if(!burning_ship && !julia && function != NEWTON3 && function != NEWTON4 && function != NEWTONGENERALIZED3 && function != NEWTONGENERALIZED8) {
           fractal_functions[NEWTON3].setEnabled(true);
           fractal_functions[NEWTON4].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED3].setEnabled(true);
           fractal_functions[NEWTONGENERALIZED8].setEnabled(true);
       }
       
       periodicity_checking_opt.setEnabled(false);
       
       setOptions(false);

       progress.setValue(0);

       scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
       scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

       superPaint();

       image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
       Graphics g = image.getGraphics();
       g.setColor(Color.WHITE);
       g.fillRect(0, 0, image_size, image_size);

       backup_orbit = null;

       whole_image_done = false;

       createThreads();

       startThreads(n);
       
   }


   public void setWholeImageDone(Boolean temp) {

       whole_image_done = temp;
       
   }

   public int getImageSize() {

       return image_size;
       
   }

   private void setSizeOfImage() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       repaint();
       String ans = JOptionPane.showInputDialog(scroll_pane, "Your Image Size is " + image_size + "x" + image_size +" .\nEnter the new Image Size.\nOnly one Dimension is required.", "Image Size", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 31) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Image Size needs to be greater than 31.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           if(temp > 7000) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Image Size needs to be lower than than 7001.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           whole_image_done = false; 

           boolean temp2 = grid;
           grid = false;
           
           image_size = temp;

           image_iterations = new double[image_size][image_size];

           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Image Size is " + image_size + "x" + image_size + " .", "Info", JOptionPane.INFORMATION_MESSAGE);
           
           main_panel.setPreferredSize(new Dimension(image_size, image_size));
           
           setOptions(false);

           progress.setMaximum((image_size * image_size) + (image_size *  image_size / 100));
           progress.setValue(0);

           superPaint();
                     
           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();
           
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;
     
           grid = temp2;
           
           createThreads();

           startThreads(n);

       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }

   }

   public JScrollPane getScrollPane() {

       return scroll_pane;
       
   }

   private void setFastJuliaFilters() {

       if(!fast_julia_filters_opt.isSelected()) {
           fast_julia_filters = false;
           repaint();
       }
       else {
           fast_julia_filters = true;
           repaint();
       }
       

   }

   private void fastJulia() {

        if(!threadsAvailable()) {
           return;
        }
       
        double temp_xCenter, temp_yCenter, temp_size, temp_xJuliaCenter, temp_yJuliaCenter;
        int temp_bailout;
        int temp_max_iterations = 150;
        int temp_image_size = FAST_JULIA_IMAGE_SIZE;

        double temp_xcenter_size = xCenter - size / 2;
        double temp_ycenter_size = yCenter - size / 2;
        double temp_size_image_size = size / image_size;

        try {
            temp_xJuliaCenter = temp_xcenter_size + temp_size_image_size * main_panel.getMousePosition().getX();
            temp_yJuliaCenter = temp_ycenter_size + temp_size_image_size * main_panel.getMousePosition().getY();
        }
        catch(Exception ex) {
            return;
        }

        fast_julia_image = new BufferedImage(temp_image_size, temp_image_size, BufferedImage.TYPE_INT_RGB);

        switch (function) {
            case MANDELBROTNTH:
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
                       threads[i][j] = new Default(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 1:
                       threads[i][j] = new Alternative(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 2:
                       threads[i][j] = new Alternative2(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;    
                   case 3:
                       threads[i][j] = new GreenWhite(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 4:
                       threads[i][j] = new Blue(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 5:
                       threads[i][j] = new Spectrum(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 6:
                       threads[i][j] = new PaleSpectrum(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 7:
                       threads[i][j] = new Grayscale(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 8:
                       threads[i][j] = new CyclicGrayscale(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 9:
                       threads[i][j] = new CyclicRedCyan(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 10:
                       threads[i][j] = new EarthSky(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 11:
                       threads[i][j] = new HotCold(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
                       break;
                   case 12:
                       threads[i][j] = new Fire(j * temp_image_size / n, (j + 1) * temp_image_size / n, i * temp_image_size / n, (i + 1) * temp_image_size / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, temp_bailout, ptr, fractal_color, fast_julia_filters, fast_julia_image, periodicity_checking, inverse_plane, anti_aliasing, edges, emboss, out_coloring_algorithm, color_intensity, burning_ship, function, z_exponent, color_cycling_location, temp_xJuliaCenter, temp_yJuliaCenter);
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
          
           if(anti_aliasing || edges || emboss) {
               progress.setValue(0);

               scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
               scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

               superPaint();

               image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
               Graphics g = image.getGraphics();
               g.setColor(Color.WHITE);
               g.fillRect(0, 0, image_size, image_size);

               backup_orbit = null;

               whole_image_done = false;

               createThreadsPaletteAndFilter();

               startThreads(n);

           }
           else {
               last_used = null;
               setOptions(true);
           }
       }
       else {
                     
           color_cycling = true;

           setOptions(false);

           if(image_size < 220) {
               superPaint();
           }

           switch (color_choice) {

               case 0:
                   threads[0][0] = new Default(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 1:
                   threads[0][0] = new Alternative(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 2:
                   threads[0][0] = new Alternative2(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;     
               case 3:
                   threads[0][0] = new GreenWhite(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 4:
                   threads[0][0] = new Blue(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, out_coloring_algorithm, color_intensity, image, color_cycling_location);
                   break;
               case 5:
                   threads[0][0] = new Spectrum(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 6:
                   threads[0][0] = new PaleSpectrum(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 7:
                   threads[0][0] = new Grayscale(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 8:
                   threads[0][0] = new CyclicGrayscale(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 9:
                   threads[0][0] = new CyclicRedCyan(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 10:
                   threads[0][0] = new EarthSky(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 11:
                   threads[0][0] = new HotCold(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
                   break;
               case 12:
                   threads[0][0] = new Fire(0, image_size, 0, image_size, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_intensity, color_cycling_location);
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
                       threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, color_cycling_location, image_buffering, out_coloring_algorithm, color_intensity, anti_aliasing, edges, emboss);
                       break;
                   case 1:
                       threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, color_cycling_location, image_buffering, out_coloring_algorithm, color_intensity, anti_aliasing, edges, emboss);
                       break;
                   case 2:
                       threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, color_cycling_location, image_buffering, out_coloring_algorithm, color_intensity, anti_aliasing, edges, emboss);
                       break;
                   case 3:
                       threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, color_cycling_location, image_buffering, out_coloring_algorithm, color_intensity, anti_aliasing, edges, emboss);
                       break;
                   case 4:
                       threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, color_cycling_location, image_buffering, out_coloring_algorithm, color_intensity, anti_aliasing, edges, emboss);
                       break;
                   case 5:
                       threads[i][j] = new Spectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 6:
                       threads[i][j] = new PaleSpectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 7:
                       threads[i][j] = new Grayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 8:
                       threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 9:
                       threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 10:
                       threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 11:
                       threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
                   case 12:
                       threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_intensity, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
                       break;
               }

           }
       }

   }

   private void setColorIntensity() {

       if(backup_orbit != null && orbit) {
           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics2D graphics = image.createGraphics();
           graphics.drawImage(backup_orbit, 0, 0, image_size, image_size, null);
       }

       double default_color_intensity;
       if(color_choice == 0 || color_choice == 1 || color_choice == 2 || color_choice == 3 || color_choice == 4) {
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

       repaint();
       String ans = (String) JOptionPane.showInputDialog(scroll_pane, "The default Color Intensity for this mode is " + default_color_intensity + ".\nEnter the new Color Intensity.", "Color Intensity", JOptionPane.QUESTION_MESSAGE, null, null, color_intensity);

       try {
           double temp = Double.parseDouble(ans);

           if(temp <= 0) {
               repaint();
               smooth_color_opt.setSelected(false);
               JOptionPane.showMessageDialog(scroll_pane, "Color Intensity value needs to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           color_intensity = temp;

           repaint();
           JOptionPane.showMessageDialog(scroll_pane, "The new Color Intensity is " + color_intensity + " .", "Info", JOptionPane.INFORMATION_MESSAGE);

           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           createThreadsPaletteAndFilter();

           startThreads(n);

       }
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }

   }

   private void setInversePlaneOption() {

       if(!inverse_plane_opt.isSelected()) {
           inverse_plane = false;

           defaultFractalSettings();

       }
       else {
           inverse_plane = true;
           
           defaultFractalSettings();

       }

   }
   
   /*private void setJuliaMadeImage() {
       
       if(!julia_made_image_opt.isSelected()) {
           julia_made_image = false;

           defaultFractalSettings();

       }
       else {
           repaint();
           String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the Julia slices, n, \n(nxn)", "Julia slices", JOptionPane.QUESTION_MESSAGE);

       try {
           int temp = Integer.parseInt(ans);

           if(temp < 1) {
               repaint();
               JOptionPane.showMessageDialog(scroll_pane, "Julia slices number need to be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
               return;
           }

           int n = temp;
           threads = new ThreadPaint[n][n];
           
           julia_made_image = true;
           
           switch (function) {
            case MANDELBROTNTH:
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
            case PHOENIX:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
            default:
                xCenter = 0;
                yCenter = 0;
                size = 6;
                bailout = 2;
                break;
        }   
           
           setOptions(false);

           progress.setValue(0);

           scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
           scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

           superPaint();

           image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_RGB);
           Graphics g = image.getGraphics();
           g.setColor(Color.WHITE);
           g.fillRect(0, 0, image_size, image_size);

           backup_orbit = null;

           whole_image_done = false;

           for(int i = 0; i < n; i++) {
               for(int j = 0; j < n; j++) {

                   switch (color_choice) {
                       case 0:
                           threads[i][j] = new Default(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       /*case 1:
                           threads[i][j] = new Alternative(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 2:
                           threads[i][j] = new Alternative2(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location); 
                           break;
                       case 3:
                           threads[i][j] = new GreenWhite(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 4:
                           threads[i][j] = new Blue(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 5:
                           threads[i][j] = new Spectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 6:
                           threads[i][j] = new PaleSpectrum(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 7:
                           threads[i][j] = new Grayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 8:
                           threads[i][j] = new CyclicGrayscale(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 9:
                           threads[i][j] = new CyclicRedCyan(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 10:
                           threads[i][j] = new EarthSky(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 11:
                           threads[i][j] = new HotCold(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                       case 12:
                           threads[i][j] = new Fire(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, color_intensity, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
                           break;
                   }

               }
           }
           
           startThreads(n);
       }
           
       catch(Exception ex) {
           if(ans == null) {
               repaint();
           }
           else {
               JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
               repaint();
           }
       }

       }
       
   }*/

   public void drawGrid(Graphics brush) {

        Graphics2D full_image_g = (Graphics2D) brush;

        double temp_xcenter_size = xCenter - size / 2;
        double temp_ycenter_size = yCenter - size / 2;
        double temp_size_image_size = size / image_size;

        full_image_g.setColor(grid_color);
        full_image_g.setFont(new Font("Arial", 1 , 9));

        double temp;
        for(int i = 1; i < 6; i++) {
           full_image_g.drawLine(i * image_size / 6, 0, i * image_size / 6, image_size);
           temp = temp_xcenter_size + temp_size_image_size * i * image_size / 6;
           full_image_g.drawString("" + temp, i * image_size / 6 + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
           temp = temp_ycenter_size + temp_size_image_size * i * image_size / 6;
           temp = temp == 0 ? temp : -temp;
           full_image_g.drawLine(0, i * image_size / 6, image_size, i * image_size / 6);
           full_image_g.drawString("" + temp, 6 + scroll_pane.getHorizontalScrollBar().getValue(), i * image_size / 6 + 12);
       }

   }
  
   private void superPaint() {

       last_used = null;
       SwingUtilities.updateComponentTreeUI(this);
       super.paint(getGraphics());
       
   }

   public static void main(String[] args) throws InterruptedException {

       MainWindow fractals = new MainWindow();
       Thread.currentThread().sleep(1300);
       fractals.setVisible(true);

   }

}
