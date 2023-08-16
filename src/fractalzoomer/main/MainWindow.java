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

 /* Made by Kalonakis Chris, hrkalona@gmail.com */
 /* Thanks to Josef Jelinek for the Supersampling code, and some of the palettes used. */
 /* Thanks to Joel Yliluoma for the boundary tracing algorithm (old version). */
 /* Thanks to Evgeny Demidov for the boundary tracing algorithm (currently used). */
 /* Thanks to David J. Eck for some of the palettes and the orbit concept */
 /* David E. Joyce (is he David J. Eck?) for the escape algorithms */
 /* Thanks to Evgeny Demidov for the 3D heightmap algorithm. */
 /* Thanks to Cogpar for parsing the user's formula */
 /* Thanks to Botond Kosa for the bump mapping algorithm */
 /* Thanks to claude for Fake Distance Estimation method, BLA and many more */
 /* Thanks to Mika Seppa for the polar plane projection */
 /* Thanks to Jerry Huxtable for many image processing algorithms */
 /* Thanks to FractView's developer for the lighting algorithms and some of the branching techniques */
 /* Thanks to Lutz Lehmann for the parhalley and laguerre implementation */
 /* Many thanks to K. I. Martin for inventing Perturbation Theory and series approximation */
 /* Many thanks to Zhuoran for inventing the one Reference optimization and BLA */
 /* Many thanks to claude for making the BLA algorithm available */
 /* Many thanks Michael Condron and claude for explaining BigNum */
 /* Many thanks to Botond Kosa for series approximation algorithm */
 /* Many thanks Michael Condron for the Supersampling examples */
 /* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
 /* Sorry for the absence of comments, this project was never supposed to reach this level! */
 /* Also forgive me for the huge-packed main class, read above! */

//Todo known issues, some mpfr code paths (plane transformations and some fractals Mandelbrot power 4-5,Magnet,Nova,Newton,Newton Param space) are not optimized at all and allocate memory on all operations
package fractalzoomer.main;

//import com.alee.laf.WebLookAndFeel;

import com.sun.jna.Platform;
import fractalzoomer.bailout_conditions.SkipBailoutCondition;
import fractalzoomer.convergent_bailout_conditions.SkipConvergentBailoutCondition;
import fractalzoomer.core.*;
import fractalzoomer.core.drawing_algorithms.*;
import fractalzoomer.core.la.impl.LAInfo;
import fractalzoomer.core.la.impl.LAInfoDeep;
import fractalzoomer.core.la.LAReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.location.normal.CartesianLocationNormalApfloatArbitrary;
import fractalzoomer.core.location.normal.PolarLocationNormalApfloatArbitrary;
import fractalzoomer.functions.Fractal;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.*;
import org.apfloat.Apfloat;
import processing.core.PApplet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import static fractalzoomer.gui.CpuLabel.CPU_DELAY;
import static fractalzoomer.gui.MemoryLabel.MEMORY_DELAY;

/**
 *
 * @author hrkalona
 */
public class MainWindow extends JFrame implements Constants {

    static {
        try {
            URI uri = MainWindow.class.getResource("/fractalzoomer/color_maps").toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap());
                myPath = fileSystem.getPath("/fractalzoomer/color_maps");
            } else {
                myPath = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(myPath, 2);

            Path outputPath = Paths.get(ColorMapFrame.DirName);
            if(!outputPath.toFile().exists()) {
                Files.createDirectory(outputPath);
            }

            if(outputPath.toFile().exists() && outputPath.toFile().isDirectory()) {
                for (Iterator<Path> it = walk.iterator(); it.hasNext(); ) {
                    Path inputPath = it.next();
                    if (inputPath.toString().toLowerCase().endsWith(".map")) {
                        String temp = inputPath.toString();
                        temp = temp.replaceAll("\\\\", "/");
                        temp = temp.substring(temp.lastIndexOf("color_maps/"), temp.length());
                        temp = temp.replace("color_maps/", "");

                        Path path = outputPath.resolve(temp);

                        Path parent = path.getParent();
                        if(parent != null && Files.notExists(parent)) {
                            Files.createDirectories(parent);
                        }

                        if (!path.toFile().exists()) {
                            String resourcePath = inputPath.toString();
                            resourcePath = resourcePath.replaceAll("\\\\", "/");
                            resourcePath = resourcePath.substring(resourcePath.lastIndexOf("/fractalzoomer"));
                            InputStream in = MainWindow.class.getResourceAsStream(resourcePath);

                            Files.copy(in, path);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean ZOOM_ON_THE_CURSOR = true;
    public static boolean QUICK_DRAW_ZOOM_TO_CURRENT_CENTER = false;
    public static boolean CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR = false;

    public static String SaveSettingsPath = "";
    public static String SaveImagesPath = "";
    private static final long serialVersionUID = -6314723558420412681L;
    private Settings s;
    private boolean first_paint;
    private boolean orbit;
    public static boolean show_orbit_converging_point = true;
    private int orbit_style;
    private int zoom_window_style;
    private boolean whole_image_done;
    public static boolean FAST_JULIA_FILTERS = false;
    public static boolean PERIODICITY_CHECKING = false;
    private boolean color_cycling;
    private int color_cycling_speed;
    private boolean cycle_colors;
    private boolean cycle_lights;
    private boolean cycle_gradient;

    private ImageRepainter ir;

    private int color_cycling_adjusting_value;

    public static boolean runsOnWindows;
    private boolean grid;
    private boolean old_grid;
    private boolean boundaries;
    private boolean old_boundaries;
    private boolean zoom_window;
    private boolean old_polar_projection;
    private boolean old_d3;
    private TaskDraw[][] tasks;
    private ArrayList<Future<?>> futures = new ArrayList<>();
    private Future<?> image_repainter_future;
    private Future<?> orbit_future;
    private DrawOrbit pixels_orbit;
    private Apfloat old_xCenter;
    private Apfloat old_yCenter;
    private Apfloat old_size;
    private double old_height_ratio;
    private int grid_tiles;
    private int boundaries_num;
    private Apfloat[] old_rotation_vals;
    private Apfloat[] old_rotation_center;
    private double[] orbit_vals;
    private double dfi;
    private int mx0;
    private int my0;
    //private int d3_draw_method;
    private int boundaries_spacing_method;
    private int boundaries_type;
    private boolean first_seed;
    private int n;
    private int m;
    private int thread_grouping;
    private int julia_grid_first_dimension;
    private double zoom_factor;
    private int image_size;
    private long calculation_time;
    private Color orbit_color;
    private Color grid_color;
    private Color boundaries_color;
    private Color zoom_window_color;
    private boolean ctrlKeyPressed;
    private boolean shiftKeyPressed;
    private boolean altKeyPressed;
    private double oldDragX;
    private double oldDragY;
    private BufferedImage image;
    private BufferedImage fast_julia_image;
    private BufferedImage last_used;
    private JFileChooser file_chooser;
    private MainWindow ptr;
    private MainPanel main_panel;
    private Timer timer;
    private JScrollPane scroll_pane;
    private JProgressBar progress;
    private Toolbar toolbar;
    private Statusbar statusbar;
    private Debugbar debugbar;
    private Infobar infobar;
    private JMenuBar menubar;
    private FileMenu file_menu;
    private OptionsMenu options_menu;
    private FiltersMenu filters_menu;
    private ToolsMenu tools_menu;
    private HelpMenu help_menu;
    private JCheckBoxMenuItem[] filters_opt;
    private JRadioButtonMenuItem[] fractal_functions;
    private JRadioButtonMenuItem[] planes;
    private JRadioButtonMenuItem[] out_coloring_modes;
    private JRadioButtonMenuItem[] in_coloring_modes;
    private JRadioButtonMenuItem[] bailout_tests;
    private JRadioButtonMenuItem[] convergent_bailout_tests;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private Cursor rotate_cursor;
    private CommonFunctions common;

    private List<P3DHeightMap> heightMapFrames = new ArrayList<>();

    private int i;
    private Object[] compilationStatus;
    public static boolean AUTO_REPAINT_IMAGE = true;

    private boolean P3D_AA = true;
    private int P3D_AA_SAMPLES = 16;

    private boolean initialized;

    Lock successive_refinement_mutex = new ReentrantLock();
    Object new_calculation_mutex = new Object();

    /**
     * *****************************
     */
    public MainWindow() {

        super();

        preloadPreferences();

        NativeLoader.init();

        s = new Settings();
        s.applyStaticSettings();

        TaskDraw.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA = true;

        runsOnWindows = false;

        ptr = this;

        zoom_factor = 2;

        old_xCenter = s.xCenter;
        old_yCenter = s.yCenter;
        old_size = s.size;
        old_height_ratio = s.height_ratio;

        int procs = Runtime.getRuntime().availableProcessors();

        ArrayList<Integer> factors = CommonFunctions.getAllFactors(procs);
        int index = factors.size() / 2 - 1;
        m = factors.get(index);
        n = factors.get(index + 1);

        TaskDraw.thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(m * n);
        TaskDraw.single_thread_executor = Executors.newSingleThreadExecutor();
        TaskDraw.action_thread_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);

        thread_grouping = 3;
        julia_grid_first_dimension = 2;

        grid_tiles = 6;
        boundaries_num = 6;

        dfi = 0.01;

        ctrlKeyPressed = false;
        shiftKeyPressed = false;
        altKeyPressed = false;

        //d3_draw_method = 0;
        boundaries_spacing_method = 0;
        boundaries_type = 0;

        color_cycling_speed = 140;
        cycle_colors = true;
        cycle_lights = false;
        cycle_gradient = false;
        color_cycling_adjusting_value = 1;

        old_rotation_vals = new Apfloat[2];

        old_rotation_vals[0] = s.fns.rotation_vals[0];
        old_rotation_vals[1] = s.fns.rotation_vals[1];

        old_rotation_center = new Apfloat[2];

        old_rotation_center[0] = s.fns.rotation_center[0];
        old_rotation_center[1] = s.fns.rotation_center[1];

        orbit_vals = new double[2];
        orbit_vals[0] = 0;
        orbit_vals[1] = 0;

        old_polar_projection = s.polar_projection;

        first_paint = false;

        old_d3 = s.d3s.d3;
        orbit = false;
        orbit_style = 0;
        zoom_window_style = 0;
        first_seed = true;
        whole_image_done = false;
        color_cycling = false;
        grid = false;
        old_grid = grid;
        boundaries = false;
        old_boundaries = boundaries;
        zoom_window = false;

        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;
        boundaries_color = new Color(0, 204, 102);
        zoom_window_color = Color.WHITE;

        Locale.setDefault(Locale.US);

        grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("cursor_grab.gif").getImage(), new Point(16, 16), "grab");
        grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");
        rotate_cursor = Toolkit.getDefaultToolkit().createCustomCursor(getIcon("rotate.gif").getImage(), new Point(16, 16), "rotate");

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

        if (Platform.isWindows()) {
            runsOnWindows = true;

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException ex) {
            } catch (InstantiationException ex) {
            } catch (IllegalAccessException ex) {
            } catch (UnsupportedLookAndFeelException ex) {
            }
        } else {
            runsOnWindows = false;
            CommonFunctions.setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 11));
        }


        UIManager.put("OptionPane.errorIcon", getIcon("error_64.png"));
        UIManager.put("OptionPane.informationIcon", getIcon("info_64.png"));
        UIManager.put("OptionPane.warningIcon", getIcon("warning_64.png"));
        UIManager.put("OptionPane.questionIcon", getIcon("question_64.png"));

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();

        if (scrnsize.getHeight() > getHeight()) {
            setLocation((int) ((scrnsize.getWidth() / 2.0) - (getWidth() / 2.0)), (int) ((scrnsize.getHeight() / 2.0) - (getHeight() / 2.0)) - 23);
        } else {
            setLocation((int) ((scrnsize.getWidth() / 2.0) - (getWidth() / 2.0)), 0);
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

        setIconImage(getIcon("mandel2.png").getImage());

        menubar = new JMenuBar();

        file_menu = new FileMenu(ptr, "File");

        options_menu = new OptionsMenu(ptr, "Options", s.ps, s.ps2, s.fns.smoothing, show_orbit_converging_point, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.out_coloring_algorithm, s.fns.in_coloring_algorithm, s.fns.function, s.fns.plane_type, s.fns.bailout_test_algorithm, s.color_blending.color_blending, s.color_blending.blending_reversed_colors, s.temp_color_cycling_location, s.temp_color_cycling_location_second_palette, s.fns.preffs.functionFilter, s.fns.postffs.functionFilter, s.fns.ips.influencePlane, s.fns.cbs.convergent_bailout_test_algorithm);

        fractal_functions = options_menu.getFractalFunctions();

        planes = options_menu.getPlanes();

        bailout_tests = options_menu.getBailoutConditions();
        convergent_bailout_tests = options_menu.getConvergentBailoutConditions();

        out_coloring_modes = options_menu.getOutColoringModes();
        in_coloring_modes = options_menu.getInColoringModes();

        tools_menu = new ToolsMenu(ptr, "Tools");

        filters_menu = new FiltersMenu(ptr, "Filters");
        filters_opt = filters_menu.getFilters();

        filters_menu.setCheckedFilters(s.fs.filters);

        help_menu = new HelpMenu(ptr, "Help");

        toolbar = new Toolbar(ptr);
        add(toolbar, BorderLayout.PAGE_START);

        JPanel status_bars = new JPanel();
        status_bars.setLayout(new BoxLayout(status_bars, BoxLayout.PAGE_AXIS));

        infobar = new Infobar(ptr, s);

        status_bars.add(infobar);

        statusbar = new Statusbar();
        debugbar = new Debugbar();
        progress = statusbar.getProgress();

        debugbar.setVisible(false);

        status_bars.add(debugbar);

        status_bars.add(statusbar);

        add(status_bars, BorderLayout.PAGE_END);

        main_panel = new MainPanel(this);
        scroll_pane = new JScrollPane(main_panel);
        scroll_pane.setWheelScrollingEnabled(false);

        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        add(scroll_pane);

        menubar.add(file_menu);
        menubar.add(options_menu);
        menubar.add(tools_menu);
        menubar.add(filters_menu);
        menubar.add(help_menu);

        setJMenuBar(menubar);

        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                for(P3DHeightMap heightMapFrame : heightMapFrames) {
                    if (heightMapFrame.isValid()) {
                        heightMapFrame.bringToBack();
                    }
                }
            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });

        scroll_pane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        ctrlKeyPressed = true;
                        scroll_pane.setCursor(grab_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        shiftKeyPressed = true;
                        scroll_pane.setCursor(rotate_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        altKeyPressed = true;
                        scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && ctrlKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        ctrlKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && shiftKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        shiftKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {
                        altKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }
            }

        });

        scroll_pane.setFocusable(true);
        scroll_pane.requestFocusInWindow();

        for (int j = 0; j < scroll_pane.getMouseWheelListeners().length; j++) {
            scroll_pane.removeMouseWheelListener(scroll_pane.getMouseWheelListeners()[j]); // remove the default listeners
        }

        scroll_pane.addMouseWheelListener(e -> {
            if (!orbit && !s.d3s.d3 && !s.julia_map && (!s.fns.julia || !first_seed)) {

                if (!s.polar_projection) {
                    scrollPoint(e);
                } else {
                    scrollPointPolar(e);
                }
            }
        });

        scroll_pane.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (ctrlKeyPressed || shiftKeyPressed) {
                    try {
                        double curX = main_panel.getMousePosition().getX();
                        double curY = main_panel.getMousePosition().getY();

                        if (!(curX < 0 || curX > image_size || curY < 0 || curY > image_size)) {
                            oldDragX = curX;
                            oldDragY = curY;
                        }
                    }
                    catch(Exception ex) {}
                }

                if (altKeyPressed) {
                    selectPointForPlane(e);
                } else if (!orbit) {
                    if (!s.d3s.d3) {
                        if (!s.polar_projection) {
                            if (!s.fns.julia) { //Regular
                                selectPointFractal(e);
                            } else {
                                selectPointJulia(e);
                            }
                        } else //Polar
                        {
                            if (s.fns.julia && first_seed) {
                                selectPointJulia(e);
                            } else {
                                selectPointPolar(e);
                            }
                        }
                    } else { // 3D
                        selectPoint3D(e);
                    }
                } else { //orbit
                    selectPointOrbit(e);
                }

                if (s.julia_map || (s.fns.julia && first_seed)) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if ((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
                } else if ((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                } else if (altKeyPressed) {
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else if (shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                } else {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {

                resetOrbit();
                if ((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                } else if ((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                } else if (shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                } else if (altKeyPressed) {
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if ((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

                if (!color_cycling) {
                    main_panel.repaint();
                }

            }

            @Override
            public void mouseExited(MouseEvent e) {

                if ((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

                if (!color_cycling) {
                    main_panel.repaint();
                }

            }
        });

        scroll_pane.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {

                if (orbit) {
                    selectPointOrbit(e);
                } else if (s.d3s.d3) {
                    rotate3DModel(e);
                } else if (ctrlKeyPressed && !s.julia_map && (!s.fns.julia || !first_seed)) {
                    dragPoint(e);
                } else if (shiftKeyPressed && !s.julia_map && (!s.fns.julia || !first_seed)) {
                    rotatePoint(e);
                } else if (altKeyPressed && !s.julia_map && (!s.fns.julia || !first_seed)) {
                    selectPointForPlane(e);
                }

                if ((s.julia_map || (s.fns.julia && first_seed)) && scroll_pane.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if ((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
                } else if ((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                } else if (shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                } else if (altKeyPressed) { // && scroll_pane.getCursor().getType() != Cursor.HAND_CURSOR
                    scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                } else if (scroll_pane.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {

                if ((e.getModifiers() & InputEvent.CTRL_MASK) != InputEvent.CTRL_MASK && ctrlKeyPressed) {
                    ctrlKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.SHIFT_MASK) != InputEvent.SHIFT_MASK && shiftKeyPressed) {
                    shiftKeyPressed = false;
                }

                if ((e.getModifiers() & InputEvent.ALT_MASK) != InputEvent.ALT_MASK && altKeyPressed) {
                    altKeyPressed = false;
                }

                if(options_menu.getDebugbar().isSelected()) {

                    try {
                        int x1 = (int) main_panel.getMousePosition().getX();
                        int y1 = (int) main_panel.getMousePosition().getY();

                        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if (!color_cycling) {
                                main_panel.repaint();
                            }
                            return;
                        }

                        debugbar.getXTf().setText("" + x1);
                        debugbar.getYTf().setText("" + y1);

                        Color c = main_panel.getColor(x1, y1);
                        debugbar.getR().setText("" + c.getRed());
                        debugbar.getG().setText("" + c.getGreen());
                        debugbar.getB().setText("" + c.getBlue());

                        debugbar.getIterData().setText(main_panel.getIterationData(x1, y1, image_size, s.ds.domain_coloring, s.d3s.d3));
                    }
                    catch (Exception ex) {

                    }
                }

                try {
                    if (old_d3) {
                        statusbar.getReal().setText("Real");
                        statusbar.getImaginary().setText("Imaginary");
                        scroll_pane.setCursor(grab_cursor);
                        return;
                    } else if (old_polar_projection) {

                        int x1 = (int) main_panel.getMousePosition().getX();
                        int y1 = (int) main_panel.getMousePosition().getY();

                        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if (!color_cycling) {
                                main_panel.repaint();
                            }
                            return;
                        }

                        PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(old_xCenter, old_yCenter, old_size, old_height_ratio, image_size, s.circle_period);

                        BigPoint p = location.getPoint(x1, y1);

                        p = MathUtils.rotatePointRelativeToPoint(p, old_rotation_vals, old_rotation_center);

                        statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p.x, old_size));

                        statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p.y, old_size));

                    } else {
                        int x1 = (int) main_panel.getMousePosition().getX();
                        int y1 = (int) main_panel.getMousePosition().getY();

                        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if (!color_cycling) {
                                main_panel.repaint();
                            }
                            return;
                        }

                        CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(old_xCenter, old_yCenter, old_size, old_height_ratio, image_size);

                        BigPoint p = MathUtils.rotatePointRelativeToPoint(location.getPoint(x1, y1), old_rotation_vals, old_rotation_center);

                        statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p.x, old_size));

                        statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p.y, old_size));
                    }

                    if (!ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed && scroll_pane.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }

                } catch (NullPointerException ex) {
                }

                if (s.fns.julia && first_seed) {
                    fastJulia();
                }

                if (zoom_window) {
                    main_panel.repaint();
                }

            }
        });

        options_menu.getDirectColor().setEnabled(false);

        common = new CommonFunctions(scroll_pane, runsOnWindows);

        compilationStatus = common.copyLibNoUI();

        common.exportCodeFilesNoUi();

        if (((Integer) compilationStatus[0]) == 0) {
            compilationStatus = common.compileCodeNoUi();
        }

        loadPreferences();
        loadAutoSave();

        if(!Settings.hasFunctionParameterization(s.fns.function)) {
            toolbar.getCurrentFunction().setEnabled(false);
        }

        if(!Settings.hasPlaneParameterization(s.fns.plane_type)) {
            toolbar.getCurrentPlane().setEnabled(false);
        }

        TaskDraw.setArrays(image_size, s.ds.domain_coloring, s.needsExtraData());

        main_panel.setPreferredSize(new Dimension(image_size, image_size));

        requestFocus();

        progress.setMaximum(image_size * image_size + 1);

        setOptions(false);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        ArraysFillColor(image, EMPTY_COLOR);

        fast_julia_image = new BufferedImage(TaskDraw.FAST_JULIA_IMAGE_SIZE, TaskDraw.FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

        whole_image_done = false;

        createTasks(false, false, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

        initialized = true;

    }

    public void checkCompilationStatus() {

        if (((Integer) compilationStatus[0]) == 0) {
            return;
        } else if (((Integer) compilationStatus[0]) == 1) {
            JOptionPane.showMessageDialog(ptr, compilationStatus[1], "Warning!", JOptionPane.WARNING_MESSAGE);
        } else if (((Integer) compilationStatus[0]) == 2) {
            JOptionPane.showMessageDialog(ptr, compilationStatus[1], "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean tasksCompleted() {

        synchronized (this) {
            for(Future<?> future : futures) {
                if(!future.isDone()) {
                    return false;
                }
            }

            return true;
        }

    }

    public MainPanel getMainPanel() {

        return main_panel;

    }

    public TaskDraw[][] getTasks() {

        return tasks;

    }

    public JProgressBar getProgressBar() {

        return progress;

    }

    public void reloadTitle() {

        String temp = "";

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        temp = "Fractal Zoomer   #";

        switch (s.fns.function) {
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
                temp += "  z = z^" + s.fns.z_exponent + " + c";
                break;
            case MANDELBROTWTH:
                temp += "  z = z^(" + Complex.toString2(s.fns.z_exponent_complex[0], s.fns.z_exponent_complex[1]) + ") + c";
                break;
            case MANDELPOLY:
                temp += "   Multibrot " + s.poly + " + c";
                break;
            case LAMBDA:
                temp += "   Lambda";
                break;
            case LAMBDA2:
                temp += "   Lambda 2";
                break;
            case LAMBDA3:
                temp += "   Lambda 3";
                break;
            case LAMBDA_FN_FN:
                temp += "   Lambda(Fn || Fn)";
                break;
            case MAGNET1:
                temp += "   Magnet 1";
                break;
            case MAGNET13:
                temp += "   Magnet 1 Cubed";
                break;
            case MAGNET14:
                temp += "   Magnet 1 Fourth";
                break;
            case MAGNET2:
                temp += "   Magnet 2";
                break;
            case MAGNET23:
                temp += "   Magnet 2 Cubed";
                break;
            case MAGNET24:
                temp += "   Magnet 2 Fourth";
                break;
            case MAGNET_PATAKI2:
                temp += "   Magnet Pataki";
                break;
            case MAGNET_PATAKI3:
                temp += "   Magnet Pataki 3";
                break;
            case MAGNET_PATAKI4:
                temp += "   Magnet Pataki 4";
                break;
            case MAGNET_PATAKI5:
                temp += "   Magnet Pataki 5";
                break;
            case MAGNET_PATAKIK:
                temp += "   Magnet Pataki " + s.fns.z_exponent;
                break;
            case MANDEL_NEWTON:
                temp += "   Mandel Newton Variation";
                break;
            case LAMBERT_W_VARIATION:
                temp += "   Lambert W Variation";
                break;
            case NEWTON_THIRD_DEGREE_PARAMETER_SPACE:
                temp += "   Newton Third Degree Parameter Space";
                break;
            case NOVA:
                temp += "   Nova-" + Constants.novaMethods[s.fns.nova_method] + ", e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
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
            case INERTIA_GRAVITY:
                temp += "   Modified Inertia/Gravity";
                break;
            case KLEINIAN:
                temp += "   Kleinian";
                break;
            case MAGNETIC_PENDULUM:
                temp += "   Magnetic Pendulum";
                break;
            case LYAPUNOV:
                temp += "   Lyapunov";
                break;
            case GENERIC_CaZbdZe:
                temp += "   z = c*(alpha*z^beta + delta*z^epsilon)";
                break;
            case GENERIC_CpAZpBC:
                temp += "   z = (c^alpha) * (z^beta) + c";
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
            case FORMULA47:
                temp += "   z = z^2 + c^2";
                break;
            case PERPENDICULAR_MANDELBROT:
                temp += "   Perpendicular Mandelbrot";
                break;
            case BUFFALO_MANDELBROT:
                temp += "   Buffalo Mandelbrot";
                break;
            case CELTIC_MANDELBROT:
                temp += "   Celtic Mandelbrot";
                break;
            case PERPENDICULAR_BURNING_SHIP:
                temp += "   Perpendicular Burning Ship";
                break;
            case PERPENDICULAR_BUFFALO_MANDELBROT:
                temp += "   Perpendicular Buffalo Mandelbrot";
                break;
            case PERPENDICULAR_CELTIC_MANDELBROT:
                temp += "   Perpendicular Celtic Mandelbrot";
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
            case USER_FORMULA_NOVA:
                temp += "   User Formula Nova";
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
             default:

                 if(Settings.isRootSolvingMethod(s.fns.function)) {
                     String methodName = FractalFunctionsMenu.functionNames[s.fns.function];

                     if (methodName.endsWith(" Generalized 3")) {
                         methodName = methodName.replace(" Generalized 3", "");
                     } else if (methodName.endsWith(" Generalized 8")) {
                         methodName = methodName.replace(" Generalized 8", "");
                     } else if (methodName.endsWith(" 3")) {
                         methodName = methodName.replace(" 3", "");
                     } else if (methodName.endsWith(" 4")) {
                         methodName = methodName.replace(" 4", "");
                     } else if (methodName.endsWith(" Sin")) {
                         methodName = methodName.replace(" Sin", "");
                     } else if (methodName.endsWith(" Cos")) {
                         methodName = methodName.replace(" Cos", "");
                     } else if (methodName.endsWith(" Polynomial")) {
                         methodName = methodName.replace(" Polynomial", "");
                     }

                     if (Settings.isRoot3Function(s.fns.function)) {
                         temp += "   " + methodName + " " + "p(z) = z^3 -1";
                     } else if (Settings.isRoot4Function(s.fns.function)) {
                         temp += "   " + methodName + " " + "p(z) = z^4 -1";
                     } else if (Settings.isRootGeneralized3Function(s.fns.function)) {
                         temp += "   " + methodName + " " + "p(z) = z^3 -2z +2";
                     } else if (Settings.isRootGeneralized8Function(s.fns.function)) {
                         temp += "   " + methodName + " " + "p(z) = z^8 +15z^4 -16";
                     } else if (Settings.isRootSinFunction(s.fns.function)) {
                         temp += "   " + methodName + " " + "f(z) = sin(z)";
                     } else if (Settings.isRootCosFunction(s.fns.function)) {
                         temp += "   " + methodName + " " + "f(z) = cos(z)";
                     } else if (Settings.isRootPolynomialFunction(s.fns.function)) {
                         temp += "   " + methodName + " " + s.poly;
                     } else if (Settings.isRootFormulaFunction(s.fns.function)) {
                         temp += "   " + methodName;
                     }
                 }
                 break;

        }

        temp += "   Center: " + BigComplex.toString2Truncated(p.x, p.y, s.size) + "   Size: " + MyApfloat.toStringTruncated(s.size, 13, 20);

        setTitle(temp);


        try {
            int x1 = (int) main_panel.getMousePosition().getX();
            int y1 = (int) main_panel.getMousePosition().getY();
            if (options_menu.getDebugbar().isSelected()) {
                debugbar.getXTf().setText("" + x1);
                debugbar.getYTf().setText("" + y1);

                Color c = main_panel.getColor(x1, y1);
                debugbar.getR().setText("" + c.getRed());
                debugbar.getG().setText("" + c.getGreen());
                debugbar.getB().setText("" + c.getBlue());

                debugbar.getIterData().setText(main_panel.getIterationData(x1, y1, image_size, s.ds.domain_coloring, s.d3s.d3));
            }
        }
        catch (Exception ex) {}

        if (s.d3s.d3) {
            statusbar.getReal().setText("Real");
            statusbar.getImaginary().setText("Imaginary");
        } else if (s.polar_projection) {
            try {
                int x1 = (int) main_panel.getMousePosition().getX();
                int y1 = (int) main_panel.getMousePosition().getY();

                if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                    if (!color_cycling) {
                        main_panel.repaint();
                    }
                    return;
                }

                PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size, s.circle_period);
                BigPoint p2 = location.getPoint(x1, y1);
                p2 =  MathUtils.rotatePointRelativeToPoint(p2, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p2.x, s.size));

                statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p2.y, s.size));
            } catch (Exception ex) {
            }
        } else {
            try {
                int x1 = (int) main_panel.getMousePosition().getX();
                int y1 = (int) main_panel.getMousePosition().getY();

                CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

                BigPoint p2 = location.getPoint(x1, y1);
                p2 = MathUtils.rotatePointRelativeToPoint(p2, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p2.x, s.size));

                statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p2.y, s.size));
            } catch (Exception ex) {
            }
        }
    }

    private void createTasks(boolean doQuickDraw, boolean doPreview, boolean zoom_to_cursor, boolean createFullImageAfterPreview) {

        synchronized (this) {
            boolean createPreview = false;

            if(s.d3s.d3) {
                doPreview = false;
            }

            if (doPreview && !doQuickDraw) {
                if(!(TaskDraw.GREEDY_ALGORITHM && (TaskDraw.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT || TaskDraw.GREEDY_ALGORITHM_SELECTION == CIRCULAR_SUCCESSIVE_REFINEMENT))) {
                    doQuickDraw = TaskDraw.DRAW_IMAGE_PREVIEW;
                    createPreview = doQuickDraw;
                }
            }

            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, createFullImageAfterPreview);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, createFullImageAfterPreview);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, createFullImageAfterPreview);
            }


            Parser.usesUserCode = false;

            try {
                int taskId = 0;
                for (int i = 0; i < tasks.length; i++) {
                    for (int j = 0; j < tasks[i].length; j++, taskId++) {

                        TaskSplitCoordinates tsc;

                        if (s.d3s.d3) {
                            tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, s.d3s.detail);
                        } else {
                            tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, image_size);
                        }

                        if (s.fns.julia) {
                            if (TaskDraw.GREEDY_ALGORITHM) {
                                if (TaskDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                    if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new BoundaryTracingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                } else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) {
                                    if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new MarianiSilverColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new MarianiSilverDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                                else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT) {
                                    if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new SuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new SuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                                else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == CIRCULAR_SUCCESSIVE_REFINEMENT) {
                                    if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                        tasks[i][j] = new CircularSuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    } else {
                                        tasks[i][j] = new CircularSuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                    }
                                }
                            } else {
                                if (TaskDraw.BRUTE_FORCE_ALG == 0) {
                                    tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                } else if (TaskDraw.BRUTE_FORCE_ALG == 1){
                                    tasks[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                }
                                else {
                                    tasks[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            }
                        } else if (TaskDraw.GREEDY_ALGORITHM) {
                            if (TaskDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new BoundaryTracingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                } else {
                                    tasks[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                }
                            } else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new MarianiSilverColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                } else {
                                    tasks[i][j] = new MarianiSilverDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                }
                            }
                            else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new SuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                } else {
                                    tasks[i][j] = new SuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                }
                            }
                            else if(TaskDraw.GREEDY_ALGORITHM_SELECTION == CIRCULAR_SUCCESSIVE_REFINEMENT) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new CircularSuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                } else {
                                    tasks[i][j] = new CircularSuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                                }
                            }
                        } else {
                            if (TaskDraw.BRUTE_FORCE_ALG == 0) {
                                tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            } else if (TaskDraw.BRUTE_FORCE_ALG == 1) {
                                tasks[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                            else {
                                tasks[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.ds, s.inverse_dem, doQuickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                            }
                        }
                        tasks[i][j].setTaskId(taskId);
                        tasks[i][j].setCreatePreview(createPreview);
                        tasks[i][j].setZoomToCursor(zoom_to_cursor);
                        tasks[i][j].setCreateFullImageAfterPreview(createFullImageAfterPreview);
                        tasks[i][j].setUsesSquareChunks(thread_grouping == 0 || ((thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) && m == n));
                    }
                }

                if(tasks[0][0].hasCircularLogic()) {
                    CircularBruteForceDraw.initCoordinates(image_size, zoom_to_cursor && CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR, !doQuickDraw || TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT, tasks[0][0].usesSuccessiveRefinement());
                    if(tasks[0][0].usesSuccessiveRefinement()) {
                        CircularSuccessiveRefinementGuessingDraw.initCoordinates(image_size, false);
                    }
                }
                else {
                    CircularBruteForceDraw.clear();
                }

                if(tasks[0][0].usesSuccessiveRefinement()) {
                    SuccessiveRefinementGuessingDraw.examined = new boolean[image_size * image_size];
                    SuccessiveRefinementGuessingDraw.filled = new boolean[image_size * image_size];
                    if(TaskDraw.SKIPPED_PIXELS_ALG != 0) {
                        SuccessiveRefinementGuessingDraw.skipped_colors = new int[image_size * image_size];
                    }
                }
                else {
                    SuccessiveRefinementGuessingDraw.examined = null;
                    SuccessiveRefinementGuessingDraw.filled = null;
                    SuccessiveRefinementGuessingDraw.skipped_colors = null;
                }
            } catch (ParserException e) {
                JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }

            if(s.d3s.d3) {
                setP3DDraw(false);
            }
        }
    }

    private void startTasks() {

        boolean isQuickDraw = false;
        boolean isFastJulia = false;

        synchronized (this) {

            isQuickDraw = tasks[0][0].isQuickDraw();
            isFastJulia = tasks[0][0].isFastJulia();
            boolean isJuliaMap = tasks[0][0].isJuliaMap();
            futures.clear();
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    if(isJuliaMap) {
                        futures.add(TaskDraw.julia_map_thread_calculation_executor.submit(tasks[i][j]));
                    }
                    else {
                        futures.add(TaskDraw.thread_calculation_executor.submit(tasks[i][j]));
                    }
                }
            }
        }

        if (initialized && (AUTO_REPAINT_IMAGE && (!isQuickDraw || TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT)) && !s.d3s.d3 && !isFastJulia && !color_cycling) {
            if(ir == null) {
                ir = new ImageRepainter(ptr, tasks);
                ir.init((isQuickDraw && TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT) || tasks[0][0].usesSuccessiveRefinement());
                image_repainter_future = TaskDraw.single_thread_executor.submit(ir);
            }
            else {
                ir.stopIt();
                ir = new ImageRepainter(ptr, tasks);
                ir.init((isQuickDraw && TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT) || tasks[0][0].usesSuccessiveRefinement());
                image_repainter_future = TaskDraw.single_thread_executor.submit(ir);
            }
        }
    }

    public void saveSettings() {

        resetOrbit();


        file_chooser = new JFileChooser(SaveSettingsPath.isEmpty() ? "." : SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".fzs";

        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Settings");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            SaveSettingsPath = file.getParent();

            s.save(file.toString());
        }

        main_panel.repaint();

    }

    public void prepareUI() {

        if(!s.d3s.d3) {
            closeP3D();
        }

        s.julia_map = false;
        tools_menu.getJuliaMap().setIcon(getIcon("julia_map.png"));
        toolbar.getJuliaMapButton().setSelected(false);

        zoom_window = false;
        tools_menu.getZoomWindow().setSelected(false);

        if (s.fns.julia) {
            first_seed = false;
            tools_menu.getJulia().setSelected(true);
            toolbar.getJuliaButton().setSelected(true);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            fractal_functions[INERTIA_GRAVITY].setEnabled(false);
            fractal_functions[KLEINIAN].setEnabled(false);

        } else {
            first_seed = true;
            tools_menu.getJulia().setSelected(false);
            toolbar.getJuliaButton().setSelected(false);

            if (!s.fns.perturbation && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
            } else {
                rootFindingMethodsSetEnabled(false);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                fractal_functions[INERTIA_GRAVITY].setEnabled(false);
                fractal_functions[KLEINIAN].setEnabled(false);
            }
        }

        if(s.fns.juliter) {
            tools_menu.getJuliter().setIcon(getIcon("juliter_enabled.png"));
        }
        else {
            tools_menu.getJuliter().setIcon(getIcon("juliter.png"));
        }

        if(s.fns.init_val) {
            options_menu.getInitialValue().setIcon(getIcon("check.png"));
        }
        else {
            options_menu.getInitialValue().setIcon(null);
        }

        if(s.fns.perturbation) {
            options_menu.getPerturbation().setIcon(getIcon("check.png"));
        }
        else {
            options_menu.getPerturbation().setIcon(null);
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        if (s.fns.in_coloring_algorithm != MAX_ITERATIONS) {
            PERIODICITY_CHECKING = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
        }

        if (s.pps.ots.useTraps  || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
            PERIODICITY_CHECKING = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }

        resetFunctions();

        options_menu.getBurningShipOpt().setEnabled(true);
        options_menu.getMandelGrassOpt().setEnabled(true);

        if (s.fns.out_coloring_algorithm == DISTANCE_ESTIMATOR || s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else if (s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES || s.fns.out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || s.fns.out_coloring_algorithm == ESCAPE_TIME_GRID || s.fns.out_coloring_algorithm == BIOMORPH || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || s.fns.out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);
        } else if (!s.fns.julia && !s.fns.perturbation && !s.fns.init_val) {
            rootFindingMethodsSetEnabled(true);
        }

        options_menu.getApplyPlaneOnWholeJuliaOpt().setSelected(s.fns.apply_plane_on_julia);
        options_menu.getApplyPlaneOnJuliaSeedOpt().setSelected(s.fns.apply_plane_on_julia_seed);

        out_coloring_modes[s.fns.out_coloring_algorithm].setSelected(true);

        in_coloring_modes[s.fns.in_coloring_algorithm].setSelected(true);

        if (s.fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM && s.fns.in_coloring_algorithm != USER_INCOLORING_ALGORITHM) {
            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)){
                setPalettePreviewsVisible();
            }

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
        }

        if (!s.useDirectColor) {
            options_menu.getDirectColor().setSelected(false);

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                setPalettePreviewsVisible();
            }

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
        } else {
            options_menu.getDirectColor().setSelected(true);

            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);

            tools_menu.getDomainColoring().setEnabled(false);
            toolbar.getDomainColoringButton().setEnabled(false);

            toolbar.getOutCustomPaletteButton().setEnabled(false);
            toolbar.getOutCustomPalette2Button().setEnabled(false);
            toolbar.getInCustomPaletteButton().setEnabled(false);
            toolbar.getInCustomPalette2Button().setEnabled(false);
            toolbar.getRandomPaletteButton().setEnabled(false);

            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);

            options_menu.getFractalColor().setEnabled(false);
            options_menu.getContourFactor().setEnabled(false);
            options_menu.getRandomPalette().setEnabled(false);
            options_menu.getOutColoringPaletteMenu().setEnabled(false);
            options_menu.getPaletteGradientMerging().setEnabled(false);
            options_menu.getInColoringPaletteMenu().setEnabled(false);
            options_menu.getStatisticsColoring().setEnabled(false);
            options_menu.getProcessing().setEnabled(false);
            options_menu.getGradient().setEnabled(false);
            options_menu.getColorBlending().setEnabled(false);
            options_menu.getColorSpaceParams().setEnabled(false);

            options_menu.getOutTrueColoring().setEnabled(false);
            options_menu.getInTrueColoring().setEnabled(false);

            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            infobar.getGradientPreviewLabel().setVisible(false);
            infobar.getGradientPreview().setVisible(false);
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);
        }

        options_menu.getBlendingModes()[s.color_blending.color_blending].setSelected(true);
        options_menu.getOutColoringTranferFunctions()[s.ps.transfer_function].setSelected(true);
        options_menu.getInColoringTranferFunctions()[s.ps2.transfer_function].setSelected(true);

        options_menu.getUsePaletteForInColoring().setSelected(s.usePaletteForInColoring);

        bailout_tests[s.fns.bailout_test_algorithm].setSelected(true);
        convergent_bailout_tests[s.fns.cbs.convergent_bailout_test_algorithm].setSelected(true);

        if(s.gps.useGeneratedPaletteOutColoring) {
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
        }

        if(s.gps.useGeneratedPaletteInColoring && s.usePaletteForInColoring) {
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);
        }

        if (s.polar_projection) {
            tools_menu.getGrid().setEnabled(false);
            tools_menu.getZoomWindow().setEnabled(false);

            file_menu.getUp().setEnabled(false);
            file_menu.getDown().setEnabled(false);

            grid = false;
            boundaries = false;

            tools_menu.getGrid().setSelected(false);
            tools_menu.getBoundaries().setSelected(false);

        } else {
            file_menu.getUp().setEnabled(true);
            file_menu.getDown().setEnabled(true);
        }

        if(s.polar_projection) {
            tools_menu.getPolarProjection().setIcon(getIcon("polar_projection_enabled.png"));
        }
        else {
            tools_menu.getPolarProjection().setIcon(getIcon("polar_projection.png"));
        }

        toolbar.getPolarProjectionButton().setSelected(s.polar_projection);

        if (s.ds.domain_coloring) {
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);

            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);

            infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
            infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));

            if (s.ds.domain_coloring_mode != 1) {
                toolbar.getOutCustomPaletteButton().setEnabled(false);
                toolbar.getOutCustomPalette2Button().setEnabled(false);
                toolbar.getRandomPaletteButton().setEnabled(false);

                options_menu.getRandomPalette().setEnabled(false);
                options_menu.getOutColoringPaletteMenu().setEnabled(false);
            }

            options_menu.getDistanceEstimation().setEnabled(false);

            options_menu.getFakeDistanceEstimation().setEnabled(false);
            options_menu.getNumericalDistanceEstimator().setEnabled(false);
            options_menu.getContourColoring().setEnabled(false);

            options_menu.getEntropyColoring().setEnabled(false);
            options_menu.getOffsetColoring().setEnabled(false);
            options_menu.getGreyScaleColoring().setEnabled(false);
            options_menu.getHistogramColoring().setEnabled(false);

            options_menu.getRainbowPalette().setEnabled(false);
            options_menu.getOrbitTraps().setEnabled(false);

            toolbar.getInCustomPaletteButton().setEnabled(false);
            toolbar.getInCustomPalette2Button().setEnabled(false);
            options_menu.getPaletteGradientMerging().setEnabled(false);

            options_menu.getInColoringPaletteMenu().setEnabled(false);

            options_menu.getOutColoringMenu().setEnabled(false);
            options_menu.getInColoringMenu().setEnabled(false);
            options_menu.getStatisticsColoring().setEnabled(false);
            options_menu.getFractalColor().setEnabled(false);

            options_menu.getOutTrueColoring().setEnabled(false);
            options_menu.getInTrueColoring().setEnabled(false);

            options_menu.getGreedyAlgorithm().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
            options_menu.getBailout().setEnabled(false);
            options_menu.getBailoutConditionMenu().setEnabled(false);
            options_menu.getConvergentBailoutConditionMenu().setEnabled(false);

            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);

        } else {
            if (!s.useDirectColor && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                if (s.usePaletteForInColoring) {
                    if(!s.gps.useGeneratedPaletteInColoring) {
                        infobar.getInColoringPalettePreview().setVisible(true);
                        infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    }
                    else {
                        infobar.getInColoringPalettePreview().setVisible(false);
                        infobar.getInColoringPalettePreviewLabel().setVisible(false);
                    }
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
        }

        if(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) {
            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);

            if (!s.useDirectColor) {
                infobar.getGradientPreviewLabel().setVisible(true);
                infobar.getGradientPreview().setVisible(true);
            }
        }
        else {
            if (!s.useDirectColor) {
                setPalettePreviewsVisible();
                infobar.getGradientPreviewLabel().setVisible(true);
                infobar.getGradientPreview().setVisible(true);
            }
        }

        if(s.ds.domain_coloring) {
            tools_menu.getDomainColoring().setIcon(getIcon("domain_coloring_enabled.png"));
        }
        else {
            tools_menu.getDomainColoring().setIcon(getIcon("domain_coloring.png"));
        }

        toolbar.getDomainColoringButton().setSelected(s.ds.domain_coloring);

        if (s.pps.ots.useTraps || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
         || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)
        ) {
            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }

        if (s.fns.tcs.trueColorOut) {
            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
        }

        filters_menu.setCheckedFilters(s.fs.filters);

        fractal_functions[s.fns.function].setSelected(true);

        options_menu.getProcessing().updateIcons(s);
        options_menu.getColorsMenu().updateIcons(s);
        options_menu.updateIcons(s);

        options_menu.getColorsMenu().getOutColoringPaletteMenu().updateIcons(s);
        options_menu.getColorsMenu().getInColoringPaletteMenu().updateIcons(s);

        switch (s.fns.function) {
            case NOVA:
                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutConditionMenu().setEnabled(false);
                options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
                optionsEnableShortcut();
                break;
            case KLEINIAN:
                optionsEnableShortcut();
                tools_menu.getJulia().setEnabled(false);
                toolbar.getJuliaButton().setEnabled(false);
                tools_menu.getJuliaMap().setEnabled(false);
                toolbar.getJuliaMapButton().setEnabled(false);
                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getPerturbation().setEnabled(false);
                options_menu.getInitialValue().setEnabled(false);
                options_menu.getPlaneInfluenceMenu().setEnabled(false);
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutConditionMenu().setEnabled(false);
                options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                break;
            case SIERPINSKI_GASKET:
            case INERTIA_GRAVITY:
                optionsEnableShortcut();
                tools_menu.getJulia().setEnabled(false);
                toolbar.getJuliaButton().setEnabled(false);
                tools_menu.getJuliaMap().setEnabled(false);
                toolbar.getJuliaMapButton().setEnabled(false);
                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getPerturbation().setEnabled(false);
                options_menu.getInitialValue().setEnabled(false);
                options_menu.getPlaneInfluenceMenu().setEnabled(false);
                options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                break;
            case MANDELBROT:
                if (!s.ds.domain_coloring && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
                    options_menu.getDistanceEstimation().setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR) {
                    out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != BIOMORPH) {
                    out_coloring_modes[BIOMORPH].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != BANDED) {
                    out_coloring_modes[BANDED].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                    out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                    out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                    out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID) {
                    out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
                }
                options_menu.getConvergentBailoutConditionMenu().setEnabled(false);

                break;
            case USER_FORMULA:
            case USER_FORMULA_ITERATION_BASED:
            case USER_FORMULA_CONDITIONAL:
            case USER_FORMULA_COUPLED:
            case USER_FORMULA_NOVA:

                if (!s.userFormulaHasC) {
                    tools_menu.getJulia().setEnabled(false);
                    toolbar.getJuliaButton().setEnabled(false);
                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    options_menu.getPerturbation().setEnabled(false);
                    options_menu.getInitialValue().setEnabled(false);
                    options_menu.getPlaneInfluenceMenu().setEnabled(false);
                }

                if (s.fns.bail_technique == 1 || s.fns.function == USER_FORMULA_NOVA) {
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutConditionMenu().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
                }
                else {
                    options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                }

                optionsEnableShortcut();
                break;
            default:
                if(Settings.isPolynomialFunction(s.fns.function)) {
                    if (s.fns.function == MANDELPOLY) {
                        options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                        optionsEnableShortcut();
                    } else {
                        optionsEnableShortcut2();
                    }
                }
                else if(Settings.isRootSolvingMethod(s.fns.function)) {
                    optionsEnableShortcut2();
                }
                else {
                    options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                    optionsEnableShortcut();
                }
                break;
        }

        if(s.isMagnetType() || s.isEscapingOrConvergingType()) {
            options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
        }

        options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true);
        options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true);
        updateColorPalettesMenu();
        updateMaxIterColorPreview();
        updateGradientPreview(s.gs.gradient_offset);

        options_menu.getBurningShipOpt().setSelected(s.fns.burning_ship);

        if(s.fns.mandel_grass) {
            options_menu.getMandelGrassOpt().setIcon(getIcon("check.png"));
        }
        else {
            options_menu.getMandelGrassOpt().setIcon(null);
        }

        planes[s.fns.plane_type].setSelected(true);
        options_menu.getPreFunctionFilters()[s.fns.preffs.functionFilter].setSelected(true);
        options_menu.getPostFunctionFilters()[s.fns.postffs.functionFilter].setSelected(true);
        options_menu.getPlaneInfluences()[s.fns.ips.influencePlane].setSelected(true);

        if(!Settings.hasFunctionParameterization(s.fns.function)) {
            toolbar.getCurrentFunction().setEnabled(false);
        }

        if(!Settings.hasPlaneParameterization(s.fns.plane_type)) {
            toolbar.getCurrentPlane().setEnabled(false);
        }
    }

    public void loadSettings() {

        resetOrbit();
        file_chooser = new JFileChooser(SaveSettingsPath.isEmpty() ? "." : SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(ptr, "Load Settings");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            String filename = file.toString();
            try {
                s.readSettings(filename, scroll_pane, false);

                TaskDraw.setDomainImageData(image_size, s.ds.domain_coloring);

                prepareUI();

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

                whole_image_done = false;

                resetImage();

                if (s.d3s.d3) {
                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;
                    ArraysFillColor(image, Color.BLACK.getRGB());
                }

                createTasks(false, true, false, false);

                calculation_time = System.currentTimeMillis();

                startTasks();

                SaveSettingsPath = file.getParent();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }

        }

    }

    public void saveSettingsAndImage() {

        file_chooser = new JFileChooser(SaveSettingsPath.isEmpty() ? "." : SaveSettingsPath);
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";

        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Settings and Image");

        Graphics2D graphics = last_used.createGraphics();

        if (s.d3s.d3) {
            ArraysFillColor(last_used, Color.BLACK.getRGB());
        } else {
            ArraysFillColor(last_used, 0);
        }

        graphics.drawImage(image, 0, 0, null);

        if (getOrbit()) {
            drawOrbit(graphics);
        }

        if (boundaries) {
            drawBoundaries(graphics, false);
        }

        if (grid) {
            drawGrid(graphics, false);
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = file_chooser.getSelectedFile();

                FileNameExtensionFilter filter = (FileNameExtensionFilter) file_chooser.getFileFilter();

                String extension = filter.getExtensions()[0];

                if (!file.getAbsolutePath().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }

                if (extension.equalsIgnoreCase("png")) {
                    ImageIO.write(last_used, "png", file);

                    String temp = file.getAbsolutePath();
                    temp = temp.substring(0, temp.lastIndexOf(".png"));
                    File file2 = new File(temp + ".fzs");

                    s.save(file2.toString());

                    SaveSettingsPath = file.getParent();
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
            }

        }

        resetOrbit();
    }

    public void saveImage() {

        file_chooser = new JFileChooser(SaveImagesPath.isEmpty() ? "." : SaveImagesPath);
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";
        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Image");

        Graphics2D graphics = last_used.createGraphics();

        if (s.d3s.d3) {
            ArraysFillColor(last_used, Color.BLACK.getRGB());
        } else {
            ArraysFillColor(last_used, 0);
        }

        graphics.drawImage(image, 0, 0, null);

        if (getOrbit()) {
            drawOrbit(graphics);
        }

        if (boundaries) {
            drawBoundaries(graphics, false);
        }

        if (grid) {
            drawGrid(graphics, false);
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File file = file_chooser.getSelectedFile();

                FileNameExtensionFilter filter = (FileNameExtensionFilter) file_chooser.getFileFilter();

                String extension = filter.getExtensions()[0];

                if (!file.getAbsolutePath().endsWith(".png")) {
                    file = new File(file.getAbsolutePath() + ".png");
                }

                if (extension.equalsIgnoreCase("png")) {
                    ImageIO.write(last_used, "png", file);
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                }

                SaveImagesPath = file.getParent();

            } catch (IOException ex) {
            }

        }

        resetOrbit();

    }

    public void defaultFractalSettings(boolean resetImage) {

        if(!Settings.hasFunctionParameterization(s.fns.function)) {
            toolbar.getCurrentFunction().setEnabled(false);
        }

        if(!Settings.hasPlaneParameterization(s.fns.plane_type)) {
            toolbar.getCurrentPlane().setEnabled(false);
        }

        resetOrbit();
        setOptions(false);

        Fractal.clearReferences(true, true);

        s.defaultFractalSettings();

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        if(resetImage) {
            resetImage();
        }
        else {
            ArraysFillColor(image, EMPTY_COLOR);
        }

        if (s.d3s.d3) {
            s.d3s.fiX = 0.64;
            s.d3s.fiY = 0.82;
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if (s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setCenterSizePost() {
        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void goToFractal() {

        resetOrbit();

        if (!orbit) {
            new CenterSizeDialog(ptr, s);
        } else {
            new OrbitDialog(ptr, s, orbit_vals);
        }

    }

    public void setOrbitPointPost(double x_real, double y_imag, int seq_points) {

        if (pixels_orbit != null && orbit_future != null) {
            try {
                orbit_future.get();

            } catch (InterruptedException ex) {

            }
            catch (ExecutionException ex) {

            }
        }

        try {
            pixels_orbit = new DrawOrbit(s, x_real, y_imag, seq_points, image_size, ptr, orbit_color, orbit_style, show_orbit_converging_point);
            orbit_future = TaskDraw.single_thread_executor.submit(pixels_orbit);
        } catch (ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        } catch (Exception ex) {
        }
    }

    public void setJuliaSeedPost() {
        first_seed = false;
        defaultFractalSettings(true);
    }

    public void goToJulia() {

        resetOrbit();

        if (!orbit) {
            if (first_seed) {
                new JuliaSeedDialog(ptr, s);
            } else {
                new CenterSizeJuliaDialog(ptr, s);
            }
        } else {
            new OrbitDialog(ptr, s, orbit_vals);
        }

    }

    public void setFractalColor() {

        resetOrbit();
        new FractalColorsFrame(ptr, s.fractal_color, s.dem_color, s.special_color, s.special_use_palette_color, s.globalIncrementBypass, s.MagnetColorOffset);

    }

    public void displaySpecialHelp() {

        common.displaySpecialHelp(ptr);

    }

    public void fractalColorsChanged(Color max_it_color, Color dem_color, Color special_color, boolean use_palette_color, boolean special_bypass, int magnetOffset) {

        s.fractal_color = max_it_color;
        s.dem_color = dem_color;
        s.special_color = special_color;
        s.special_use_palette_color = use_palette_color;
        ColorAlgorithm.GlobalUsingIncrement = s.globalIncrementBypass = special_bypass;

        boolean recalculate = false;
        if(ColorAlgorithm.MAGNET_INCREMENT != magnetOffset) {
            ColorAlgorithm.MAGNET_INCREMENT = magnetOffset;
            recalculate = true;
        }

        if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        TaskDraw.palette_outcoloring.setGeneratedPaletteSettings(true, s.gps);
        TaskDraw.palette_incoloring.setGeneratedPaletteSettings(false, s.gps);

        updateMaxIterColorPreview();

        if(!recalculate) {
            updateColors(false);
        }
        else {
            redraw();
        }
    }

    public void setSkipBailoutIterations() {

        resetOrbit();
        new SkipBailoutDialog(ptr, s, true);

    }

    public void setSkipConvergentBailoutIterations() {

        resetOrbit();
        new SkipBailoutDialog(ptr, s, false);

    }

    public void setSkipBailoutIterationsPost() {

        SkipBailoutCondition.SKIPPED_ITERATION_COUNT = s.fns.skip_bailout_iterations;
        SkipConvergentBailoutCondition.SKIPPED_ITERATION_COUNT = s.fns.skip_convergent_bailout_iterations;

        redraw();

    }

    public void setIterations() {

        resetOrbit();
        new IterationDialog(ptr, s);

    }

    public void setPeriod() {

        resetOrbit();
        new PeriodDialog(ptr, s);

    }

    public void setHeightRatioPost() {
        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void setHeightRatio() {

        resetOrbit();
        new HeightRatioDialog(ptr, s);

    }

    public void setZoomingFactorPost(double zoom_factor) {
        this.zoom_factor = zoom_factor;
    }

    public void setZoomingFactor() {

        resetOrbit();
        new ZoomingFactorDialog(ptr, zoom_factor);

    }

    public void setThreadsNumberPost(int grouping, int val, int val2) {
        if(grouping == 3 || grouping == 4) {
            ArrayList<Integer> factors = CommonFunctions.getAllFactors(val);

            int index = factors.size() / 2 - 1;

            m = factors.get(index);
            this.n = factors.get(index + 1);
        } else if (grouping == 5) {
            m = val2;
            n = val;
        } else {
            this.n = val;
        }
        this.thread_grouping = grouping;

        TaskDraw.thread_calculation_executor.shutdown();
        TaskDraw.thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(getNumberOfThreads());
    }

    public void setThreadsNumber() {

        resetOrbit();
        new ThreadsDialog(ptr, n, m, thread_grouping);

    }

    public void chooseDirectPalette(int temp, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            resetOrbit();
            options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true); // reselect the old palette

            int[] res = PaletteMenu.choosePaletteFile(ptr);
            if (res != null) {
                ptr.setPalette(temp, res, outcoloring_mode ? 0 : 1);
            } else if (s.ps.color_choice != temp) {
                options_menu.getOutColoringPalette()[temp].setSelected(false);
            } else {
                options_menu.getOutColoringPalette()[temp].setSelected(true);
            }
        } else {
            resetOrbit();
            options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true); // reselect the old palette

            int[] res = PaletteMenu.choosePaletteFile(ptr);
            if (res != null) {
                ptr.setPalette(temp, res, outcoloring_mode ? 0 : 1);
            } else if (s.ps2.color_choice != temp) {
                options_menu.getInColoringPalette()[temp].setSelected(false);
            } else {
                options_menu.getInColoringPalette()[temp].setSelected(true);
            }
        }

    }

    public void setPalette(int temp2, int[] palette, int mode) {

        resetOrbit();

        if (mode == 2) { //set both
            s.ps.color_choice = temp2;
            if (s.ps.color_choice != CUSTOM_PALETTE_ID) {
                s.ps.color_cycling_location = 0;
            }

            options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true);

            if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                TaskDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            } else {
                TaskDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            }

            s.ps2.color_choice = temp2;
            if (s.ps2.color_choice != CUSTOM_PALETTE_ID) {
                s.ps2.color_cycling_location = 0;
            }

            options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true);

            if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                TaskDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            } else {
                TaskDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            }
        } else if (mode == 0) {
            s.ps.color_choice = temp2;
            if (s.ps.color_choice != CUSTOM_PALETTE_ID) {
                s.ps.color_cycling_location = 0;
            }

            if (s.ps.color_choice == DIRECT_PALETTE_ID) {
                s.ps.direct_palette = palette;
            }

            options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true);

            if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                TaskDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            } else {
                TaskDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            }
        } else {
            s.ps2.color_choice = temp2;
            if (s.ps2.color_choice != CUSTOM_PALETTE_ID) {
                s.ps2.color_cycling_location = 0;
            }

            if (s.ps2.color_choice == DIRECT_PALETTE_ID) {
                s.ps2.direct_palette = palette;
            }

            options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true);

            if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                TaskDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            } else {
                TaskDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
            }
        }

        TaskDraw.palette_outcoloring.setGeneratedPaletteSettings(true, s.gps);
        TaskDraw.palette_incoloring.setGeneratedPaletteSettings(false, s.gps);

        updateColorPalettesMenu();
        updateMaxIterColorPreview();

        updateColors(false);

    }

    public void setFilter(int temp) {

        resetOrbit();
        if (temp == ANTIALIASING) {
            if (!filters_opt[ANTIALIASING].isSelected()) {
                s.fs.filters[ANTIALIASING] = false;

                if(s.d3s.d3) {
                    TaskDraw.setExtraDataArrays(s.needsExtraData(), s.d3s.detail);
                }
                else {
                    TaskDraw.setExtraDataArrays(s.needsExtraData(), image_size);
                }

                setOptions(false);

                progress.setValue(0);

                whole_image_done = false;

                resetImageAndCopy(s.d3s.d3, false);

                if ((TaskDraw.GREEDY_ALGORITHM && TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) || s.pps.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                        || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                    if (s.d3s.d3) {
                        createTasksPaletteAndFilter3DModel();
                    } else {
                        createTasks(false, false, false, false);
                    }

                    calculation_time = System.currentTimeMillis();
                    startTasks();
                } else {
                    if (s.d3s.d3 || s.ds.domain_coloring) {
                        createTasks(false, false, false, false);
                    } else {
                        createTasksPaletteAndFilter();
                    }

                    calculation_time = System.currentTimeMillis();

                    startTasks();
                }
            } else {
                s.fs.filters[ANTIALIASING] = true;

                if(s.d3s.d3) {
                    TaskDraw.setExtraDataArrays(s.needsExtraData(), s.d3s.detail);
                }
                else {
                    TaskDraw.setExtraDataArrays(s.needsExtraData(), image_size);
                }

                setOptions(false);

                progress.setValue(0);

                whole_image_done = false;

                resetImageAndCopy(s.d3s.d3, false);

                if(s.julia_map) {
                    createTasksJuliaMap();
                } else {
                    createTasks(false, false, false, false);
                }

                calculation_time = System.currentTimeMillis();

                startTasks();

            }
        } else {
            if (!filters_opt[temp].isSelected()) {
                s.fs.filters[temp] = false;
            } else {
                s.fs.filters[temp] = true;
            }

            setOptions(false);

            progress.setValue(0);

            whole_image_done = false;

            resetImageAndCopy(s.d3s.d3, false);

            if (s.fs.filters[ANTIALIASING] || s.pps.ots.useTraps || s.ds.domain_coloring || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                    || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {

                if(!TaskDraw.hasExtraData(image_size) || s.d3s.d3) {
                    if(s.julia_map) {
                        createTasksJuliaMap();
                    }
                    else if (s.d3s.d3) {
                        createTasksPaletteAndFilter3DModel();
                    }
                    else  {
                        createTasks(false, false, false, false);
                    }
                }
                else {
                    createTasksPalettePostProcessAndFilterWithAAData(TaskDraw.POST_PROCESSING_WITH_AA_AND_FILTER);
                }

                calculation_time = System.currentTimeMillis();

                startTasks();
            } else {
                if (s.d3s.d3) {
                    createTasksPaletteAndFilter3DModel();
                } else {
                    createTasksPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startTasks();
            }
        }
    }

    public void setGrid() {

        resetOrbit();
        if (!tools_menu.getGrid().isSelected()) {
            grid = false;
            old_grid = grid;
            if (!zoom_window && !s.julia_map && !orbit && !boundaries && !s.useDirectColor) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if (!zoom_window) {
                tools_menu.getPolarProjection().setEnabled(true);
                toolbar.getPolarProjectionButton().setEnabled(true);
            }

            main_panel.repaint();
        } else {
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

        resetOrbit();
        if (!tools_menu.getBoundaries().isSelected()) {
            boundaries = false;
            old_boundaries = boundaries;
            if (!zoom_window && !s.julia_map && !orbit && !grid && !s.useDirectColor) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }
            main_panel.repaint();
        } else {
            boundaries = true;
            old_boundaries = boundaries;
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setZoomWindow() {

        resetOrbit();
        if (!tools_menu.getZoomWindow().isSelected()) {
            zoom_window = false;
            if (!s.ds.domain_coloring && s.functionSupportsC()) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if (!s.fns.julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if (!grid && !boundaries && !s.useDirectColor) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if (!grid) {
                tools_menu.getPolarProjection().setEnabled(true);
                toolbar.getPolarProjectionButton().setEnabled(true);
            }

            if (!s.ds.domain_coloring && !s.useDirectColor) {
                tools_menu.getColorCycling().setEnabled(true);
                toolbar.getColorCyclingButton().setEnabled(true);
            }
            main_panel.repaint();
        } else {
            zoom_window = true;
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.getOrbit().setEnabled(false);
            toolbar.getOrbitButton().setEnabled(false);
            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            tools_menu.getPolarProjection().setEnabled(false);
            toolbar.getPolarProjectionButton().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setPeriodicityChecking() {

        resetOrbit();
        if (!options_menu.getPeriodicityChecking().isSelected()) {
            PERIODICITY_CHECKING = false;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
            if (!s.useDirectColor) {
                options_menu.getOrbitTraps().setEnabled(true);
                options_menu.getInTrueColoring().setEnabled(true);
            }
            main_panel.repaint();
        } else {
            PERIODICITY_CHECKING = true;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(false);
            options_menu.getOrbitTraps().setEnabled(false);
            options_menu.getInTrueColoring().setEnabled(false);
            main_panel.repaint();
        }

    }

    public void setAutoRepaintImage() {
        resetOrbit();
        if (!options_menu.getAutoRepaintImage().isSelected()) {
            AUTO_REPAINT_IMAGE = false;
            main_panel.repaint();
        } else {
            AUTO_REPAINT_IMAGE = true;
            main_panel.repaint();
        }
    }

    private void selectPointFractalInternal(MouseEvent e, double curX, double curY) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);

        BigPoint refPoint = null;
        if(ZOOM_ON_THE_CURSOR) {
            refPoint = location.getPoint((int) curX, (int) curY);
            Pixel.customX = -(int) curX;
            Pixel.customY = -(int) curY;
        }else {
            BigPoint p = location.getPoint((int) curX, (int) curY);

            s.xCenter = p.x;
            s.yCenter = p.y;
        }

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                if(MyApfloat.setAutomaticPrecision) {
                    if (MyApfloat.shouldIncreasePrecision(s.size)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.increasePrecision(s);
                    }
                }

                s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
                break;
            }
        }

        if(ZOOM_ON_THE_CURSOR) {
            location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);
            BigPoint p = location.getPointRelativeToPoint((int) curX, (int) curY, refPoint);
            s.xCenter = p.x;
            s.yCenter = p.y;
            Pixel.customX = -(int) curX;
            Pixel.customY = -(int) curY;
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        if(!ZOOM_ON_THE_CURSOR) {
            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));
        }

        whole_image_done = false;

        resetImage();

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, ZOOM_ON_THE_CURSOR, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private boolean canSelectPointInstantly() {
        return !(s.fns.julia && first_seed) && !color_cycling && !s.julia_map && !s.d3s.d3 && TaskDraw.USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT && TaskDraw.GREEDY_ALGORITHM && (TaskDraw.GREEDY_ALGORITHM_SELECTION == Constants.SUCCESSIVE_REFINEMENT || TaskDraw.GREEDY_ALGORITHM_SELECTION == Constants.CIRCULAR_SUCCESSIVE_REFINEMENT);
    }

    private void selectPointFractal(MouseEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if ((e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
                return;
            }

            double curX, curY;
            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }


            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            selectPointFractalInternal(e, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            selectPointFractalInternal(e, curX, curY);
        }
    }

    private void scrollPointInternal(MouseWheelEvent e, boolean doQuickDraw, double curX, double curY) {


        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        BigPoint refPoint = null;
        if(!QUICK_DRAW_ZOOM_TO_CURRENT_CENTER) {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

            if(ZOOM_ON_THE_CURSOR) {
                refPoint = location.getPoint((int) curX, (int) curY);
                Pixel.customX = -(int) curX;
                Pixel.customY = -(int) curY;
            }else {
                BigPoint p = location.getPoint((int) curX, (int) curY);

                s.xCenter = p.x;
                s.yCenter = p.y;
            }
        }

        int notches = e.getWheelRotation();
        if (notches < 0) {
            if(MyApfloat.setAutomaticPrecision) {
                if (MyApfloat.shouldIncreasePrecision(s.size)) {
                    Fractal.clearReferences(true, true);
                    MyApfloat.increasePrecision(s);
                }
            }
            s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
        } else {
            s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
        }

        if(!QUICK_DRAW_ZOOM_TO_CURRENT_CENTER && ZOOM_ON_THE_CURSOR) {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);
            BigPoint p = location.getPointRelativeToPoint((int) curX, (int) curY, refPoint);
            s.xCenter = p.x;
            s.yCenter = p.y;
            Pixel.customX = -(int) curX;
            Pixel.customY = -(int) curY;
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        if(!doQuickDraw) {
            progress.setValue(0);
        }

        if(QUICK_DRAW_ZOOM_TO_CURRENT_CENTER || !ZOOM_ON_THE_CURSOR) {
            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));
        }

        whole_image_done = false;

        resetImage();

        createTasks(doQuickDraw, true, ZOOM_ON_THE_CURSOR, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void scrollPoint(MouseWheelEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if (ctrlKeyPressed || shiftKeyPressed || altKeyPressed) {
                return;
            }

            double curX, curY;

            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            scrollPointInternal(e, false, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            scrollPointInternal(e, true, curX, curY);
        }
    }

    private void dragPointInternal(MouseEvent e, boolean doQuickDraw, double curX, double curY) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);

        BigPoint p = location.getDragPoint((int)(curX - oldDragX), (int)(curY - oldDragY));

        s.xCenter = p.x;
        s.yCenter = p.y;

        oldDragX = curX;
        oldDragY = curY;

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        if(!doQuickDraw) {
            progress.setValue(0);
        }

        whole_image_done = false;

        resetImage();

        createTasks(doQuickDraw, true, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void dragPoint(MouseEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if (e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK)) {
                return;
            }

            double curX, curY;

            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            dragPointInternal(e, false, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            dragPointInternal(e, true, curX, curY);
        }

    }

    private void selectPointForPlaneInternal(MouseEvent e, boolean doQuickDraw, double curX, double curY) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);

        BigPoint p = location.getPoint((int)curX, (int)curY);

        p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

        s.fns.plane_transform_center[0] = p.x.doubleValue();
        s.fns.plane_transform_center[1] = p.y.doubleValue();

        if(s.fns.plane_type != MU_PLANE) {
            Fractal.clearReferences(true, true);
        }

        if(s.fns.plane_type == INFLECTIONS_PLANE) {
            if(e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.ALT_MASK)) {
                s.fns.inflections_re.add(s.fns.plane_transform_center[0]);
                s.fns.inflections_im.add(s.fns.plane_transform_center[1]);
            }
            else if (e.getModifiers() == (InputEvent.BUTTON3_MASK | InputEvent.ALT_MASK)) {
                if(!s.fns.inflections_re.isEmpty()) {
                    s.fns.inflections_re.remove(s.fns.inflections_re.size() - 1);
                    s.fns.inflections_im.remove(s.fns.inflections_im.size() - 1);
                }
            }
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        createTasks(doQuickDraw, true, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void selectPointForPlane(MouseEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if (e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.ALT_MASK) && e.getModifiers() != (InputEvent.BUTTON3_MASK | InputEvent.ALT_MASK)) {
                return;
            }

            double curX, curY;
            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }


            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            selectPointForPlaneInternal(e, false, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            selectPointForPlaneInternal(e, true, curX, curY);
        }

    }

    private void rotatePointInternal(MouseEvent e, boolean doQuickDraw, double curX, double curY) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double new_angle = MathUtils.angleBetween2PointsDegrees(curX, curY, image_size / 2.0, image_size / 2.0);
        double old_angle = MathUtils.angleBetween2PointsDegrees(oldDragX, oldDragY, image_size / 2.0, image_size / 2.0);
        if (Math.abs(new_angle - old_angle) > 350 && new_angle > old_angle) {
            old_angle += 360;
        } else if (Math.abs(new_angle - old_angle) > 350 && new_angle < old_angle) {
            new_angle += 360;
        }
        s.fns.rotation += new_angle - old_angle;

        if (s.fns.rotation > 360) {
            s.fns.rotation -= 2 * 360;
        } else if (s.fns.rotation < -360) {
            s.fns.rotation += 2 * 360;
        }

        oldDragX = curX;
        oldDragY = curY;

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
        s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
        s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

        s.fns.rotation_center[0] = p.x;
        s.fns.rotation_center[1] = p.y;
        s.xCenter = s.fns.rotation_center[0];
        s.yCenter = s.fns.rotation_center[1];

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        if(!doQuickDraw) {
            progress.setValue(0);
        }

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        createTasks(doQuickDraw, true, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void rotatePoint(MouseEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if (e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK)) {
                return;
            }

            double curX, curY;
            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            rotatePointInternal(e, false, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            rotatePointInternal(e, true, curX, curY);
        }
    }

    private void scrollPointPolarInternal(MouseWheelEvent e, boolean doQuickDraw, double curX, double curY) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        int notches = e.getWheelRotation();
        if (notches < 0) {
            if(MyApfloat.setAutomaticPrecision) {
                if (MyApfloat.shouldIncreasePrecision(s.size)) {
                    Fractal.clearReferences(true, true);
                    MyApfloat.increasePrecision(s);
                }
            }
            s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
        } else {
            s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        if(!doQuickDraw) {
            progress.setValue(0);
        }

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        createTasks(doQuickDraw, true, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void scrollPointPolar(MouseWheelEvent e) {

        synchronized (new_calculation_mutex) {
            if (ctrlKeyPressed || shiftKeyPressed || altKeyPressed) {
                return;
            }

            double curX, curY;

            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            scrollPointPolarInternal(e, false, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            scrollPointPolarInternal(e, true, curX, curY);
        }


    }

    private void selectPointJuliaInternal(MouseEvent e, double curX, double curY) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        setOptions(false);

        if (first_seed) {
            if (e.getModifiers() == InputEvent.BUTTON1_MASK) {

                int x1 = (int) curX;
                int y1 = (int) curY;

                Fractal.clearReferences(true, true);

                if (s.polar_projection) {

                    PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size, s.circle_period);

                    BigPoint p = location.getPoint(x1, y1);

                    p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

                    s.xJuliaCenter = p.x;
                    s.yJuliaCenter = p.y;
                } else {
                    CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

                    BigPoint p = location.getPoint(x1, y1);

                    p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

                    s.xJuliaCenter = p.x;
                    s.yJuliaCenter = p.y;
                }

                first_seed = false;

                main_panel.repaint();

                defaultFractalSettings(true);
                return;
            }
        } else {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

            BigPoint refPoint = null;
            if(ZOOM_ON_THE_CURSOR) {
                refPoint = location.getPoint((int) curX, (int) curY);
                Pixel.customX = -(int) curX;
                Pixel.customY = -(int) curY;
            }else {
                BigPoint p = location.getPoint((int) curX, (int) curY);

                s.xCenter = p.x;
                s.yCenter = p.y;
            }

            switch (e.getModifiers()) {
                case InputEvent.BUTTON1_MASK: {
                    if(MyApfloat.setAutomaticPrecision) {
                        if (MyApfloat.shouldIncreasePrecision(s.size)) {
                            Fractal.clearReferences(true, true);
                            MyApfloat.increasePrecision(s);
                        }
                    }
                    s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
                    break;
                }
                case InputEvent.BUTTON3_MASK: {
                    s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
                    break;
                }
            }

            if(ZOOM_ON_THE_CURSOR) {
                location = new CartesianLocationNormalApfloatArbitrary(s.xCenter,  s.yCenter, s.size, s.height_ratio, image_size);
                BigPoint p = location.getPointRelativeToPoint((int) curX, (int) curY, refPoint);
                s.xCenter = p.x;
                s.yCenter = p.y;
                Pixel.customX = -(int) curX;
                Pixel.customY = -(int) curY;
            }

            if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
                tools_menu.getBoundaries().setEnabled(false);
                boundaries = false;
                tools_menu.getBoundaries().setSelected(false);
            }

            progress.setValue(0);

            if(!ZOOM_ON_THE_CURSOR) {
                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));
            }

            whole_image_done = false;

            resetImage();

            createTasks(false, true, ZOOM_ON_THE_CURSOR, false);

            calculation_time = System.currentTimeMillis();

            startTasks();

        }
    }
    private void selectPointJulia(MouseEvent e) {

        synchronized (new_calculation_mutex) {
            resetOrbit();

            if ((e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
                return;
            }

            double curX, curY;
            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            selectPointJuliaInternal(e, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            selectPointJuliaInternal(e, curX, curY);
        }

    }

    private void selectPointPolarInternal(MouseEvent e, double curX, double curY) {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                if(MyApfloat.setAutomaticPrecision) {
                    if (MyApfloat.shouldIncreasePrecision(s.size)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.increasePrecision(s);
                    }
                }
                s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));
                break;
            }
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    private void stopSuccessiveRefinement() {
        TaskDraw.stopSuccessiveRefinement();

       // synchronized (this) {
            try {
                for(Future<?> future : futures) {
                    future.get();
                }
            } catch (InterruptedException ex) {
            }
            catch (ExecutionException ex) {
            }
        //}

        if(ir != null && image_repainter_future != null) {
            ir.stopIt();
            try {
                image_repainter_future.get();
            }
            catch (InterruptedException ex) {

            }
            catch (ExecutionException ex) {

            }
        }
    }

    private void selectPointPolar(MouseEvent e) {
        synchronized (new_calculation_mutex) {
            resetOrbit();

            if ((e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
                return;
            }

            double curX, curY;
            try {
                curX = main_panel.getMousePosition().getX();
                curY = main_panel.getMousePosition().getY();
            } catch (Exception ex) {
                return;
            }

            if (curX < 0 || curX > image_size || curY < 0 || curY > image_size) {
                return;
            }

            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            selectPointPolarInternal(e, curX, curY);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            } else if (!tasksCompleted()) {
                return;
            }

            selectPointPolarInternal(e, curX, curY);
        }
    }

    public void setJuliaOption() {

        resetOrbit();
        if (!tools_menu.getJulia().isSelected()) {
            s.fns.julia = false;
            toolbar.getJuliaButton().setSelected(false);
            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
            if (!first_seed) {
                defaultFractalSettings(true);
                main_panel.repaint();
            } else {
                setOptions(true);
                main_panel.repaint();
            }
        } else {
            s.fns.julia = true;

            toolbar.getJuliaButton().setSelected(true);
            first_seed = true;
            tools_menu.getOrbit().setEnabled(false);
            toolbar.getOrbitButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            fractal_functions[KLEINIAN].setEnabled(false);
            fractal_functions[INERTIA_GRAVITY].setEnabled(false);
            setOptions(false);
        }

    }

    public void setOrbitOption() {

        if (!tools_menu.getOrbit().isSelected()) {
            resetOrbit();
            orbit = false;

            toolbar.getOrbitButton().setSelected(false);
            if (!s.ds.domain_coloring && s.functionSupportsC()) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if (!s.fns.julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if (!grid && !boundaries && !s.useDirectColor) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if (!s.polar_projection) {
                tools_menu.getZoomWindow().setEnabled(true);
            }

            if (!s.ds.domain_coloring && !s.useDirectColor) {
                tools_menu.getColorCycling().setEnabled(true);
                toolbar.getColorCyclingButton().setEnabled(true);
            }
        } else {
            orbit = true;
            toolbar.getOrbitButton().setSelected(true);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);
            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            tools_menu.getZoomWindow().setEnabled(false);
        }

        main_panel.repaint();

    }

    private void rotate3DModel(MouseEvent e) {

        resetOrbit();

        if (!tasksCompleted() || e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        try {
            int x1 = (int) main_panel.getMousePosition().getX();
            int y1 = (int) main_panel.getMousePosition().getY();

            if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                return;
            }

            statusbar.getReal().setText("Real");
            statusbar.getImaginary().setText("Imaginary");

            s.d3s.fiX += dfi * (y1 - my0);
            s.d3s.fiY += dfi * (x1 - mx0);
            mx0 = x1;
            my0 = y1;

            if (timer != null) {
                timer.cancel();
                timer = null;
            }

        } catch (Exception ex) {
            return;
        }

        setOptions(false);

        //progress.setValue(0);
        whole_image_done = false;

        resetImage();

        ArraysFillColor(image, Color.BLACK.getRGB());

        createTasksRotate3DModel();

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void selectPointOrbit(MouseEvent e) {

        if (!tasksCompleted() || (pixels_orbit != null && orbit_future != null && !orbit_future.isDone())) {
            return;
        }

        int x1, y1;

        try {
            x1 = (int) main_panel.getMousePosition().getX();
            y1 = (int) main_panel.getMousePosition().getY();
        } catch (Exception ex) {
            return;
        }

        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
            return;
        }

        if(options_menu.getDebugbar().isSelected()) {
            debugbar.getXTf().setText("" + x1);
            debugbar.getYTf().setText("" + y1);

            Color c = main_panel.getColor(x1, y1);
            debugbar.getR().setText("" + c.getRed());
            debugbar.getG().setText("" + c.getGreen());
            debugbar.getB().setText("" + c.getBlue());

            debugbar.getIterData().setText(main_panel.getIterationData(x1, y1, image_size, s.ds.domain_coloring, s.d3s.d3));
        }

        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
            try {
                pixels_orbit = new DrawOrbit(s, x1, y1, image_size, ptr, orbit_color, orbit_style, show_orbit_converging_point);
                orbit_future = TaskDraw.single_thread_executor.submit(pixels_orbit);
            } catch (ParserException ex) {
                JOptionPane.showMessageDialog(scroll_pane, ex.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            } catch (Exception ex) {
            }
        }

        if (s.polar_projection) {
            try {
                PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size, s.circle_period);

                BigPoint p = location.getPoint(x1, y1);

                p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p.x, s.size));

                statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p.y, s.size));
            } catch (NullPointerException ex) {
            }
        } else {
            try {
                CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

                BigPoint p = location.getPoint(x1, y1);

                p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + MyApfloat.toStringTruncated(p.x, s.size));

                statusbar.getImaginary().setText("" + MyApfloat.toStringTruncated(p.y, s.size));
            } catch (NullPointerException ex) {
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
    
    
     scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
     scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));
    
    
     resetImage();
    
     whole_image_done = false;
    
     createThreads(false, false, false, false);
    
     calculation_time = System.currentTimeMillis();
    
     startThreads();
    
     } while( true);
     }
     }.start();  
     }*/

    private void zoomInInternal() {
        resetOrbit();
        if(MyApfloat.setAutomaticPrecision) {
            if (MyApfloat.shouldIncreasePrecision(s.size)) {
                Fractal.clearReferences(true, true);
                MyApfloat.increasePrecision(s);
            }
        }
        s.size = MyApfloat.fp.divide(s.size, new MyApfloat(zoom_factor));

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }
    public void zoomIn() {

        synchronized (new_calculation_mutex) {
            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            zoomInInternal();
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            }

            zoomInInternal();
        }
    }

    public void zoomOut() {

        synchronized (new_calculation_mutex) {
            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            zoomOutInternal();
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            }

            zoomOutInternal();
        }
    }

    private void zoomOutInternal() {

        resetOrbit();
        s.size = MyApfloat.fp.multiply(s.size, new MyApfloat(zoom_factor));

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private void moveToInternal(int where) {
        resetOrbit();
        if (where == UP) {
            if (!s.polar_projection) {
                s.yCenter = MyApfloat.fp.add(s.yCenter, MyApfloat.fp.multiply(s.size, new MyApfloat(s.height_ratio)));
            }
        } else if (where == DOWN) {
            if (!s.polar_projection) {
                s.yCenter = MyApfloat.fp.subtract(s.yCenter, MyApfloat.fp.multiply(s.size, new MyApfloat(s.height_ratio)));
            }
        } else if (where == LEFT) {

            if (s.polar_projection) {
                Apfloat end = MyApfloat.fp.log(s.size);

                Apfloat ddimage_size = new MyApfloat(image_size);

                Apfloat ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(s.circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

                Apfloat ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(s.height_ratio));

                Apfloat start = MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx.negate(), ddimage_size), end);

                s.size = MyApfloat.exp(start);
            } else {
                s.xCenter = MyApfloat.fp.subtract(s.xCenter, s.size);
            }
        } else if (s.polar_projection) {
            Apfloat start =  MyApfloat.fp.log(s.size);

            Apfloat ddimage_size = new MyApfloat(image_size);

            Apfloat ddmuly = MyApfloat.fp.divide(MyApfloat.fp.multiply(MyApfloat.fp.multiply(new MyApfloat(s.circle_period), MyApfloat.TWO), MyApfloat.getPi()), ddimage_size);

            Apfloat ddmulx = MyApfloat.fp.multiply(ddmuly, new MyApfloat(s.height_ratio));

            Apfloat end = MyApfloat.fp.add(MyApfloat.fp.multiply(ddmulx, ddimage_size), start);

            s.size = MyApfloat.exp(end);
        } else {
            s.xCenter = MyApfloat.fp.add(s.xCenter, s.size);
        }

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void moveTo(int where) {
        synchronized (new_calculation_mutex) {
            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            moveToInternal(where);
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            }

            moveToInternal(where);
        }

    }

    public Settings getSettings() {
        return s;
    }

    public void setOptions(boolean option) {

        if(!canSelectPointInstantly()) {
            file_menu.getStartingPosition().setEnabled(option);
            toolbar.getStartingPositionButton().setEnabled(option);

            file_menu.getZoomIn().setEnabled(option);
            toolbar.getZoomInButton().setEnabled(option);

            file_menu.getZoomOut().setEnabled(option);
            toolbar.getZoomOutButton().setEnabled(option);

            file_menu.getLeft().setEnabled(option);
            file_menu.getRight().setEnabled(option);

            if(!s.polar_projection) {
                file_menu.getUp().setEnabled(option);
                file_menu.getDown().setEnabled(option);
            }
        }
        else {
            file_menu.getStartingPosition().setEnabled(true);
            toolbar.getStartingPositionButton().setEnabled(true);

            file_menu.getZoomIn().setEnabled(true);
            toolbar.getZoomInButton().setEnabled(true);

            file_menu.getZoomOut().setEnabled(true);
            toolbar.getZoomOutButton().setEnabled(true);

            file_menu.getLeft().setEnabled(true);
            file_menu.getRight().setEnabled(true);

            if(!s.polar_projection) {
                file_menu.getUp().setEnabled(true);
                file_menu.getDown().setEnabled(true);
            }
        }

        file_menu.getSaveSettings().setEnabled(option);
        file_menu.getLoadSettings().setEnabled(option);
        file_menu.getSaveImage().setEnabled(option);
        file_menu.getSaveImageAndSettings().setEnabled(option);
        file_menu.getSetInitialSettings().setEnabled(option);

        file_menu.getCodeEditor().setEnabled(option);
        file_menu.getCompileCode().setEnabled(option);
        file_menu.getLibraryCode().setEnabled(option);

        if ((!s.fns.julia || !first_seed)) {
            file_menu.getGoTo().setEnabled(option);
            toolbar.getGoTo().setEnabled(option);
        }

        options_menu.getFractalFunctionsMenu().setEnabled(option);

        if(s.isPertubationTheoryInUse() || s.isHighPrecisionInUse()) {
            options_menu.getPostFunctionFiltersMenu().setEnabled(false);
            options_menu.getPreFunctionFiltersMenu().setEnabled(false);
            options_menu.getMandelGrassOpt().setEnabled(false);
        }
        else {
            options_menu.getPostFunctionFiltersMenu().setEnabled(option);
            options_menu.getPreFunctionFiltersMenu().setEnabled(option);
            options_menu.getMandelGrassOpt().setEnabled(option);
        }


        options_menu.getColorsMenu().setEnabled(option);

        file_menu.getDefaults().setEnabled(option);

        file_menu.getRepaint().setEnabled(option);

        options_menu.getIterationsMenu().setEnabled(option);
        options_menu.getSizeOfImage().setEnabled(option);

        options_menu.getHeightRatio().setEnabled(option);

        options_menu.getJitter().setEnabled(option);

        options_menu.getZoomFactor().setEnabled(option);

        if ((!s.ds.domain_coloring || s.ds.domain_coloring_mode == 1) && !s.useDirectColor) {
            toolbar.getOutCustomPaletteButton().setEnabled(option);
            toolbar.getOutCustomPalette2Button().setEnabled(option);
            toolbar.getRandomPaletteButton().setEnabled(option);
        }

        toolbar.getIterationsButton().setEnabled(option);
        toolbar.getRotationButton().setEnabled(option);
        toolbar.getSaveImageButton().setEnabled(option);
        toolbar.getSaveImageAndSettignsButton().setEnabled(option);

        if(Settings.hasFunctionParameterization(s.fns.function)) {
            toolbar.getCurrentFunction().setEnabled(option);
        }

        if(Settings.hasPlaneParameterization(s.fns.plane_type)) {
            toolbar.getCurrentPlane().setEnabled(option);
        }

        options_menu.getPoint().setEnabled(option);
        options_menu.getVariables().setEnabled(option);

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN) {
            options_menu.getBailout().setEnabled(option);
            options_menu.getBailoutConditionMenu().setEnabled(option);
        }

        if (!s.ds.domain_coloring && s.hasConvergentBailoutCondition()) {
            options_menu.getConvergentBailoutConditionMenu().setEnabled(option);
        }

        options_menu.getOptimizationsMenu().setEnabled(option);
        options_menu.getToolsOptionsMenu().setEnabled(option);
        options_menu.getGeneralOptionsMenu().setEnabled(option);

        if (!s.d3s.d3 && (s.fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM || s.fns.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) && !s.ds.domain_coloring) {
            options_menu.getDirectColor().setEnabled(option);
        }

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.pps.ots.useTraps && !s.fns.tcs.trueColorIn && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring) && !(s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) && !TaskDraw.PERTURBATION_THEORY && !TaskDraw.HIGH_PRECISION_CALCULATION) {
            options_menu.getPeriodicityChecking().setEnabled(option);
        }

        if (!s.ds.domain_coloring && !s.d3s.d3 && !s.julia_map) {
            options_menu.getGreedyAlgorithm().setEnabled(option);
        }

        if (((!s.fns.julia && !orbit) || (!first_seed && !orbit)) && !s.ds.domain_coloring && !zoom_window && !s.julia_map && !s.d3s.d3  && s.functionSupportsC()) {
            tools_menu.getJulia().setEnabled(option);
            toolbar.getJuliaButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !color_cycling && !s.d3s.d3 && !s.pps.ots.useTraps && !s.useDirectColor && !s.fns.tcs.trueColorOut && !s.fns.tcs.trueColorIn && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                && !(s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
            tools_menu.getColorCycling().setEnabled(option);
            toolbar.getColorCyclingButton().setEnabled(option);
        }

        if (!s.julia_map && !s.useDirectColor) {
            tools_menu.getDomainColoring().setEnabled(option);
            toolbar.getDomainColoringButton().setEnabled(option);
        }

        tools_menu.getPlaneVizualization().setEnabled(option);

        if (!s.ds.domain_coloring) {
            if (s.fns.function == MANDELBROT) {
                if(s.isPertubationTheoryInUse() || s.isHighPrecisionInUse()) {
                    options_menu.getDistanceEstimation().setEnabled(false);
                }
                else {
                    options_menu.getDistanceEstimation().setEnabled(option);
                }
            }

            options_menu.getContourColoring().setEnabled(option);

            options_menu.getFakeDistanceEstimation().setEnabled(option);

            options_menu.getNumericalDistanceEstimator().setEnabled(option);

            options_menu.getEntropyColoring().setEnabled(option);

            options_menu.getGreyScaleColoring().setEnabled(option);

            options_menu.getRainbowPalette().setEnabled(option);

            options_menu.getOffsetColoring().setEnabled(option);

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                options_menu.getHistogramColoring().setEnabled(option);
            }

            if (!PERIODICITY_CHECKING) {
                options_menu.getOrbitTraps().setEnabled(option);
            }

            options_menu.getOutColoringMenu().setEnabled(option);
            options_menu.getInColoringMenu().setEnabled(option);
            if (!s.useDirectColor) {
                options_menu.getFractalColor().setEnabled(option);
                options_menu.getContourFactor().setEnabled(option);
                options_menu.getStatisticsColoring().setEnabled(option);
                options_menu.getInColoringPaletteMenu().setEnabled(option);
                options_menu.getPaletteGradientMerging().setEnabled(option);
                options_menu.getColorSpaceParams().setEnabled(option);

                if(!s.ds.domain_coloring) {
                    toolbar.getInCustomPaletteButton().setEnabled(option);
                    toolbar.getInCustomPalette2Button().setEnabled(option);
                }
                options_menu.getOutTrueColoring().setEnabled(option);

                if (!PERIODICITY_CHECKING) {
                    options_menu.getInTrueColoring().setEnabled(option);
                }
            }
        }

        if ((!s.ds.domain_coloring || s.ds.domain_coloring_mode == 1) && !s.useDirectColor) {
            options_menu.getRandomPalette().setEnabled(option);
            options_menu.getOutColoringPaletteMenu().setEnabled(option);
        }

        if (!s.useDirectColor) {
            options_menu.getProcessing().setEnabled(option);
        }

        if (!zoom_window && !grid) {
            tools_menu.getPolarProjection().setEnabled(option);
            toolbar.getPolarProjectionButton().setEnabled(option);
        }

        tools_menu.getJuliter().setEnabled(option);

        options_menu.getFractalOptionsMenu().setEnabled(option);

        filters_menu.getColorsFiltersMenu().setEnabled(option);
        filters_menu.getDetailsFiltersMenu().setEnabled(option);
        filters_menu.getTextureFiltersMenu().setEnabled(option);
        filters_menu.getLightingFiltersMenu().setEnabled(option);

        options_menu.getFiltersOptions().setEnabled(option);

        toolbar.getFiltersOptionsButton().setEnabled(option);

        if ((s.fns.rotation == 0 || s.fns.rotation == 360 || s.fns.rotation == -360) && !s.d3s.d3 && !s.polar_projection) {
            tools_menu.getGrid().setEnabled(option);
        }

        if ((s.fns.rotation == 0 || s.fns.rotation == 360 || s.fns.rotation == -360) && !s.d3s.d3 && s.size.compareTo(new MyApfloat(0.05)) >= 0) {
            tools_menu.getBoundaries().setEnabled(option);
        }

        if (!s.d3s.d3 && !orbit && !s.julia_map && !s.polar_projection) {
            tools_menu.getZoomWindow().setEnabled(option);
        }

        if (!s.julia_map && !s.d3s.d3 && !zoom_window) {
            tools_menu.getOrbit().setEnabled(option);
            toolbar.getOrbitButton().setEnabled(option);
        }
        options_menu.getPlanesMenu().setEnabled(option);

        if (!s.ds.domain_coloring && !zoom_window && !s.fns.julia && !orbit && !s.d3s.d3 && s.functionSupportsC()) {
            tools_menu.getJuliaMap().setEnabled(option);
            toolbar.getJuliaMapButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !s.julia_map && !grid && !boundaries && !s.useDirectColor) {
            tools_menu.get3D().setEnabled(option);
            toolbar.get3DButton().setEnabled(option);
        }

        options_menu.getRotationMenu().setEnabled(option);

        options_menu.getPeriod().setEnabled(option);

        if (s.functionSupportsC()) {
            boolean new_opt = option;
            if(s.isPertubationTheoryInUse()  || s.isHighPrecisionInUse()) {
                new_opt = false;
            }
            options_menu.getPerturbation().setEnabled(new_opt);
            options_menu.getInitialValue().setEnabled(new_opt);
            options_menu.getPlaneInfluenceMenu().setEnabled(new_opt);
        }

        options_menu.getOverview().setEnabled(option);
        options_menu.getStats().setEnabled(option);
        options_menu.getStatsTasks().setEnabled(option);
        infobar.getOverview().setEnabled(option);
        infobar.getStats().setEnabled(option);
        infobar.getStatsTasks().setEnabled(option);

        if (!s.useDirectColor) {
            options_menu.getGradient().setEnabled(option);
            options_menu.getColorBlending().setEnabled(option);
        }

        infobar.setListenersEnabled(option);

        if(!s.fns.julia || !first_seed) {
            file_menu.getCancelOperation().setEnabled(!option);
            infobar.getCancelButton().setEnabled(!option);
        }

    }

    public void setLine() {

        resetOrbit();
        orbit_style = 0;

    }

    public void setDot() {

        resetOrbit();
        orbit_style = 1;

    }

    public void setShowOrbitConvergingPoint() {

        resetOrbit();
        if (!options_menu.getShowOrbitConvergingPoint().isSelected()) {
            show_orbit_converging_point = false;
        } else {
            show_orbit_converging_point = true;
        }
    }

    public void setZoomWindowDashedLine() {

        resetOrbit();
        zoom_window_style = 0;

    }

    public void setZoomWindowLine() {

        resetOrbit();
        zoom_window_style = 1;

    }

    public void setOrbitColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Orbit Color", orbit_color, 0, -1);

        main_panel.repaint();

    }

    public void setGridColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Grid Color", grid_color, 1, -1);

        main_panel.repaint();

    }

    public void setZoomWindowColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Zoom Window Color", zoom_window_color, 2, -1);

        main_panel.repaint();

    }

    public void setBoundariesColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Boundaries Color", boundaries_color, 3, -1);

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

        boolean wasConvergingType = s.isConvergingType();
        boolean wasMagnetType = s.isMagnetType();
        boolean wasEscapingOrConvergingType = s.isEscapingOrConvergingType();
        boolean wasMagneticPendulumType = s.fns.function == MAGNETIC_PENDULUM;
        boolean wasMagnetPatakiType = Settings.isMagnetPataki(s.fns.function);
        boolean wasSimpleType = !wasConvergingType && !wasMagnetType && !wasMagneticPendulumType && !wasEscapingOrConvergingType && !wasMagnetPatakiType;

        resetOrbit();
        int oldSelected = s.fns.function;
        s.fns.function = temp;

        switch (s.fns.function) {
            case LYAPUNOV:
                new LyapunovDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MAGNETIC_PENDULUM:
                new MagneticPendulumDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MANDELBROTNTH:
                new MandelbrotNthDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MAGNET_PATAKIK:
                new MagnetPatakiNthDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MANDELBROTWTH:
                new MandelbrotWthDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case GENERIC_CaZbdZe:
                new GenericCaZbdZeDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case GENERIC_CpAZpBC:
                new GenericCpAZpBCDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MULLERFORMULA:
                new MullerFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case LAGUERREFORMULA:
                new LaguerreFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case NOVA:
                new NovaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case KLEINIAN:
                new KleinianDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case INERTIA_GRAVITY:
                new InertiaGravityDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case SIERPINSKI_GASKET:
                setInertiaGravitySierpinskiPost();
                break;
            case USER_FORMULA_ITERATION_BASED:
                new UserFormulaIterationBasedDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case USER_FORMULA_CONDITIONAL:
                new UserFormulaConditionalDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case LAMBDA_FN_FN:
                new LambdaFnFnDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case MANDELBROT:
                if (!s.ds.domain_coloring && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
                    options_menu.getDistanceEstimation().setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR) {
                    out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != BIOMORPH) {
                    out_coloring_modes[BIOMORPH].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != BANDED) {
                    out_coloring_modes[BANDED].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                    out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                    out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                    out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID) {
                    out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
                }
                if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
                }

                options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                break;
            case USER_FORMULA:
                new UserFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case USER_FORMULA_COUPLED:
                new UserFormulaCoupledDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            case USER_FORMULA_NOVA:
                new UserFormulaNovaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                return;
            default:
                if(Settings.isTwoFunctionsRootFindingMethodFormula(s.fns.function)) {
                    new RootFindingTwoFunctionsDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    return;
                }
                else if(Settings.isThreeFunctionsRootFindingMethodFormula(s.fns.function)) {
                    new RootFindingThreeFunctionsDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    return;
                }
                else if(Settings.isFourFunctionsRootFindingMethodFormula(s.fns.function)) {
                    new RootFindingFourFunctionsDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    return;
                }
                else if(Settings.isOneFunctionsRootFindingMethodFormula(s.fns.function)) {
                    new RootFindingOneFunctionDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    return;
                }
                else if(Settings.isPolynomialFunction(s.fns.function)) {
                    new PolynomialDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    return;
                }
                else if(Settings.isRootSolvingMethod(s.fns.function)) {
                    optionsEnableShortcut2();
                }
                else {
                    options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
                    optionsEnableShortcut();
                }
                break;
        }

        if(s.isMagnetType() || s.isEscapingOrConvergingType()) {
            options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
        }

        setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);

    }

    public void setInertiaGravitySierpinskiPost() {
        optionsEnableShortcut();
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
        options_menu.getPlaneInfluenceMenu().setEnabled(false);
    }

    public void setFunctionPost(int oldFunction, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        boolean isConvergingType = s.isConvergingType();
        boolean isMagnetType = s.isMagnetType();
        boolean isMagneticPendulumType = s.fns.function == MAGNETIC_PENDULUM;
        boolean isEscapingOrConvergingType = s.isEscapingOrConvergingType();
        boolean isMagnetPatakiType = Settings.isMagnetPataki(s.fns.function);
        boolean isSimpleType = !isConvergingType && !isMagnetType && !isMagneticPendulumType && !isEscapingOrConvergingType && !isMagnetPatakiType;

        if (isConvergingType != wasConvergingType || isMagnetType != wasMagnetType || isSimpleType != wasSimpleType
                || isMagneticPendulumType != wasMagneticPendulumType || isEscapingOrConvergingType != wasEscapingOrConvergingType
        || isMagnetPatakiType != wasMagnetPatakiType) {
            s.resetUserOutColoringFormulas();
            s.resetStatisticalColoringFormulas();
            s.resetConvergentBailoutFormulas();
        }

        if(wasMagnetPatakiType && isMagnetPatakiType && s.fns.function != oldFunction) {
            s.resetUserOutColoringFormulas();
        }

        if (s.isConvergingType()) {
            s.pps.sts.statistic_type = COS_ARG_DIVIDE_INVERSE_NORM;
        } else if (s.pps.sts.statistic_type == COS_ARG_DIVIDE_INVERSE_NORM && !s.isMagnetType() && !s.isEscapingOrConvergingType()) {
            s.pps.sts.statistic_type = STRIPE_AVERAGE;
        }

        if(!((s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH && !s.fns.burning_ship) || s.fns.function == MainWindow.LAMBDA) && s.pps.sts.statisticGroup == 3) {
            s.pps.sts.statisticGroup = 0;
        }
        else if(s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH && s.fns.burning_ship) {
            s.pps.sts.useNormalMap = false;
            s.pps.sts.normalMapOverrideColoring = false;
            s.pps.sts.normalMapColoring = 0;
        }

        if(!(s.isConvergingType() && s.fns.function != MainWindow.MAGNETIC_PENDULUM) && s.pps.sts.statisticGroup == 4) {
            s.pps.sts.statisticGroup = 0;
        }

        defaultFractalSettings(true);

    }

    public void setBailoutPost() {

        Fractal.clearReferences(true, true);

        redraw();
    }

    public void setBailout() {

        resetOrbit();
        new BailoutDialog(ptr, s);

    }

    public void setRotation() {

        resetOrbit();
        new RotationDialog(ptr, s);

    }

    public void setRotationPost() {
        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void increaseRotation() {

        resetOrbit();
        if (s.fns.rotation == 360) {
            s.fns.rotation = 0;
        }
        s.fns.rotation++;

        Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
        s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
        s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

        setOptions(false);

        progress.setValue(0);

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void decreaseRotation() {

        resetOrbit();
        if (s.fns.rotation == -360) {
            s.fns.rotation = 0;
        }
        s.fns.rotation--;

        Apfloat tempRadians =  MyApfloat.fp.toRadians(new MyApfloat(s.fns.rotation));
        s.fns.rotation_vals[0] = MyApfloat.cos(tempRadians);
        s.fns.rotation_vals[1] = MyApfloat.sin(tempRadians);

        setOptions(false);

        progress.setValue(0);

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setBurningShip() {

        resetOrbit();
        if (!options_menu.getBurningShipOpt().isSelected()) {
            s.fns.burning_ship = false;
        } else {
            s.fns.burning_ship = true;
        }

        if(s.fns.function >= MainWindow.MANDELBROT && s.fns.function <= MainWindow.MANDELBROTNTH && s.fns.burning_ship) {
            s.pps.sts.useNormalMap = false;
            s.pps.sts.normalMapOverrideColoring = false;
            s.pps.sts.normalMapColoring = 0;
        }

        if (s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) {
            defaultFractalSettings(true);
        }

    }

    public void setMandelGrass() {

        resetOrbit();
        new MandelgrassDialog(ptr, s, options_menu.getMandelGrassOpt());

    }

    private void startingPositionInternal() {
        resetOrbit();
        setOptions(false);

        if(MyApfloat.setAutomaticPrecision) {
            long precision = MyApfloat.getAutomaticPrecision(new String[]{s.size.toString(true)}, new boolean[] {true});

            if (MyApfloat.shouldSetPrecision(precision, true)) {
                MyApfloat.setPrecision(precision, s);
            }
        }

        s.startingPosition();

        if (s.size.compareTo(new MyApfloat(0.05)) < 0) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void startingPosition() {

        synchronized (new_calculation_mutex) {
            if (canSelectPointInstantly()) {
                TaskDraw.action_thread_executor.submit(() -> {
                    if (successive_refinement_mutex.tryLock()) {
                        try {
                            stopSuccessiveRefinement();
                            startingPositionInternal();
                        } finally {
                            successive_refinement_mutex.unlock();
                        }
                    }
                });
                return;
            }

            startingPositionInternal();
        }

    }

    public void setWholeImageDone(Boolean temp) {

        whole_image_done = temp;

    }

    public void setSizeOfImagePost(int image_size) {
        whole_image_done = false;

        old_grid = false;

        old_boundaries = false;

        this.image_size = image_size;

        if (!s.d3s.d3) {
            TaskDraw.setArrays(image_size, s.ds.domain_coloring, s.needsExtraData());
        }

        main_panel.setPreferredSize(new Dimension(image_size, image_size));

        setOptions(false);

        if (!s.d3s.d3) {
            progress.setMaximum(image_size * image_size + 1);
        }

        updateP3D();

        progress.setValue(0);

        SwingUtilities.updateComponentTreeUI(this);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

        //last_used = null;
        //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //Graphics2D graphics = last_used.createGraphics();
        //graphics.drawImage(image, 0, 0, image_size, image_size, null);
        last_used = null;

        image = null;

        clearThreads();

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }
        else {
            ArraysFillColor(image, EMPTY_COLOR);
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setSizeOfImage() {

        resetOrbit();
        new ImageSizeDialog(ptr, image_size);

    }

    public JScrollPane getScrollPane() {

        return scroll_pane;

    }

    public void setFastJuliaFilters() {

        resetOrbit();
        if (!options_menu.getFastJuliaFiltersOptions().isSelected()) {
            FAST_JULIA_FILTERS = false;
            main_panel.repaint();
        } else {
            FAST_JULIA_FILTERS = true;
            main_panel.repaint();
        }

    }

    private void fastJulia() {

        resetOrbit();

        if (!tasksCompleted()) {
            return;
        }

        Apfloat temp_xCenter, temp_yCenter, temp_size;
        Apfloat temp_xJuliaCenter, temp_yJuliaCenter;

        int temp_max_iterations = s.max_iterations > 250 ? 250 : s.max_iterations;

        int x1, y1;

        try {
            x1 = (int) main_panel.getMousePosition().getX();
            y1 = (int) main_panel.getMousePosition().getY();
        } catch (Exception ex) {
            return;
        }

        if (s.polar_projection) {

            PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size, s.circle_period);

            BigPoint p = location.getPoint(x1, y1);

            p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

            temp_xJuliaCenter = p.x;

            temp_yJuliaCenter = p.y;
        } else {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(s.xCenter, s.yCenter, s.size, s.height_ratio, image_size);

            BigPoint p = location.getPoint(x1, y1);

            p = MathUtils.rotatePointRelativeToPoint(p, s.fns.rotation_vals, s.fns.rotation_center);

            temp_xJuliaCenter = p.x;

            temp_yJuliaCenter = p.y;

        }

        ArraysFillColor(fast_julia_image, EMPTY_COLOR);

        switch (s.fns.function) {
            case MAGNET1:
            case MAGNET13:
            case MAGNET14:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(28);
                break;
            case MAGNET2:
            case MAGNET23:
            case MAGNET24:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(56);
                break;
            case BARNSLEY1:
            case BARNSLEY2:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(7);
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
            case FORMULA47:
            case PERPENDICULAR_MANDELBROT:
            case BUFFALO_MANDELBROT:
            case CELTIC_MANDELBROT:
            case PERPENDICULAR_BURNING_SHIP:
            case PERPENDICULAR_BUFFALO_MANDELBROT:
            case PERPENDICULAR_CELTIC_MANDELBROT:
            case FORMULA32:
            case FORMULA33:
            case FORMULA35:
            case FORMULA36:
            case FORMULA37:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(6);
                break;
            case FORMULA1:
            case FORMULA43:
            case FORMULA44:
            case FORMULA45:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(24);
                break;
            case FORMULA3:
            case FORMULA9:
            case FORMULA10:
            case FORMULA8:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(3);
                break;
            case FORMULA4:
            case FORMULA5:
            case FORMULA11:
            case FORMULA38:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(1.5);
                break;
            case FORMULA7:
            case FORMULA12:
            case FORMULA28:
            case FORMULA29:
            case FORMULA42:
            case SZEGEDI_BUTTERFLY1:
            case SZEGEDI_BUTTERFLY2:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(12);
                break;
            case FORMULA27:
                temp_xCenter = new MyApfloat(-2);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(6);
                break;
            default:
                temp_xCenter = new MyApfloat(0.0);
                temp_yCenter = new MyApfloat(0.0);
                temp_size = new MyApfloat(6);
                break;
        }

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            Parser.usesUserCode = false;

            try {

                int taskId = 0;
                for (int i = 0; i < tasks.length; i++) {
                    for (int j = 0; j < tasks[i].length; j++, taskId++) {

                        TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, TaskDraw.FAST_JULIA_IMAGE_SIZE);

                        if (TaskDraw.GREEDY_ALGORITHM) {
                            if (TaskDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new BoundaryTracingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                } else {
                                    tasks[i][j] = new BoundaryTracingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                }
                            } else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new MarianiSilverColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                } else {
                                    tasks[i][j] = new MarianiSilverDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                }
                            }
                            else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new SuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                } else {
                                    tasks[i][j] = new SuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                }
                            }
                            else if (TaskDraw.GREEDY_ALGORITHM_SELECTION == CIRCULAR_SUCCESSIVE_REFINEMENT) {
                                if (TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA) {
                                    tasks[i][j] = new CircularSuccessiveRefinementGuessingColorsAndIterationDataDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                } else {
                                    tasks[i][j] = new CircularSuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                                }
                            }
                        } else {
                            if (TaskDraw.BRUTE_FORCE_ALG == 0) {
                                tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                            } else if (TaskDraw.BRUTE_FORCE_ALG == 1) {
                                tasks[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                            }
                            else {
                                tasks[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, FAST_JULIA_FILTERS, fast_julia_image, PERIODICITY_CHECKING, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps, temp_xJuliaCenter, temp_yJuliaCenter);
                            }
                        }
                        tasks[i][j].setTaskId(taskId);
                        tasks[i][j].setUsesSquareChunks(thread_grouping == 0 || ((thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) && m == n));
                    }
                }

                if(tasks[0][0].hasCircularLogic()) {
                    CircularBruteForceDraw.initCoordinatesFastJulia(TaskDraw.FAST_JULIA_IMAGE_SIZE, tasks[0][0].usesSuccessiveRefinement());
                    if(tasks[0][0].usesSuccessiveRefinement()) {
                        CircularSuccessiveRefinementGuessingDraw.initCoordinatesFastJulia(TaskDraw.FAST_JULIA_IMAGE_SIZE, false);
                    }
                }
                else {
                    CircularBruteForceDraw.clearFastJulia();
                }

                if(tasks[0][0].usesSuccessiveRefinement()) {
                    SuccessiveRefinementGuessingDraw.filled_fast_julia = new boolean[TaskDraw.FAST_JULIA_IMAGE_SIZE * TaskDraw.FAST_JULIA_IMAGE_SIZE];
                    SuccessiveRefinementGuessingDraw.examined_fast_julia = new boolean[TaskDraw.FAST_JULIA_IMAGE_SIZE * TaskDraw.FAST_JULIA_IMAGE_SIZE];
                    if(TaskDraw.SKIPPED_PIXELS_ALG != 0) {
                        SuccessiveRefinementGuessingDraw.skipped_colors_fast_julia = new int[TaskDraw.FAST_JULIA_IMAGE_SIZE * TaskDraw.FAST_JULIA_IMAGE_SIZE];
                    }
                }
                else {
                    SuccessiveRefinementGuessingDraw.examined_fast_julia = null;
                    SuccessiveRefinementGuessingDraw.filled_fast_julia = null;
                    SuccessiveRefinementGuessingDraw.skipped_colors_fast_julia = null;
                }
            } catch (ParserException e) {
                JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }
        }

        startTasks();

    }

    public void setColorCycling() {

        resetOrbit();
        if (!tools_menu.getColorCycling().isSelected()) {


            TaskDraw.action_thread_executor.submit(() -> {
                color_cycling = false;
                toolbar.getColorCyclingButton().setSelected(false);

                TaskDraw.terminateColorCycling();

                try {
                    for(Future<?> future : futures) {
                        future.get();
                    }
                } catch (InterruptedException ex) {
                }
                catch (ExecutionException ex) {
                }

                s.ps.color_cycling_location = tasks[0][0].getColorCyclingLocationOutColoring();
                s.ps2.color_cycling_location = tasks[0][0].getColorCyclingLocationInColoring();
                s.pps.bms = new BumpMapSettings(tasks[0][0].getBumpMapSettings());
                s.pps.ls = new LightSettings(tasks[0][0].getLightSettings());
                s.pps.ss = new SlopeSettings(tasks[0][0].getSlopeSettings());
                s.gs.gradient_offset = tasks[0][0].getGradientOffset();

                if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                    s.temp_color_cycling_location = s.ps.color_cycling_location;
                }

                if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                    s.temp_color_cycling_location_second_palette = s.ps2.color_cycling_location;
                }

                updateColorPalettesMenu();

                if (s.fs.filters[ANTIALIASING]) {
                    setOptions(false);

                    progress.setValue(0);

                    whole_image_done = false;

                    resetImageAndCopy(s.d3s.d3, false);

                    if(!TaskDraw.hasExtraData(image_size) || s.ds.domain_coloring) {
                        if (s.julia_map) {
                            createTasksJuliaMap();
                        }else {
                            createTasks(false, false, false, false);
                        }
                    }
                    else {
                        createTasksPalettePostProcessAndFilterWithAAData(TaskDraw.APPLY_PALETTE_AND_POST_PROCESSING_WITH_AA_AND_FILTER);
                    }

                    calculation_time = System.currentTimeMillis();

                    startTasks();
                } else {
                    //last_used = null;
                    setOptions(true);
                }
            });
        } else {
            color_cycling = true;
            toolbar.getColorCyclingButton().setSelected(true);

            setOptions(false);

            whole_image_done = false;

            createTasksColorCycling();

            TaskDraw.initializeColorCycling();

            startTasks();
        }

    }

    private void createTasksPaletteAndFilter() {

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            int taskId = 0;
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++, taskId++) {
                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, image_size);

                    if(TaskDraw.GREEDY_ALGORITHM && TaskDraw.GREEDY_ALGORITHM_SELECTION == Constants.CIRCULAR_SUCCESSIVE_REFINEMENT) {
                        tasks[i][j] = new CircularSuccessiveRefinementGuessingDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,      s.color_blending,  s.post_processing_order,   s.pbs,  s.ds, s.gs.gradient_offset,  s.contourFactor, s.fns.smoothing, s.gps, s.pps);
                    }
                    else if (!TaskDraw.GREEDY_ALGORITHM && TaskDraw.BRUTE_FORCE_ALG == 2) {
                        tasks[i][j] = new CircularBruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,      s.color_blending,  s.post_processing_order,   s.pbs,  s.ds, s.gs.gradient_offset,  s.contourFactor, s.fns.smoothing, s.gps, s.pps);
                    }
                    else if (!TaskDraw.GREEDY_ALGORITHM && TaskDraw.BRUTE_FORCE_ALG == 0) {
                        tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,      s.color_blending,  s.post_processing_order,   s.pbs,  s.ds, s.gs.gradient_offset,  s.contourFactor, s.fns.smoothing, s.gps, s.pps);
                    }
                    else {
                        tasks[i][j] = new BruteForce2Draw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,      s.color_blending,  s.post_processing_order,   s.pbs,  s.ds, s.gs.gradient_offset,  s.contourFactor, s.fns.smoothing, s.gps, s.pps);
                    }

                    tasks[i][j].setTaskId(taskId);
                }
            }

            if(tasks[0][0].hasCircularLogic()) {
                CircularBruteForceDraw.initCoordinates(image_size, false, true, tasks[0][0].usesSuccessiveRefinement());
                if(tasks[0][0].usesSuccessiveRefinement()) {
                    CircularSuccessiveRefinementGuessingDraw.initCoordinates(image_size, false);
                }
            }
            else {
                CircularBruteForceDraw.clear();
            }
        }
    }

    private void createTasksPalettePostProcessAndFilterWithAAData(int action) {

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            int taskId = 0;
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++, taskId++) {
                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, image_size);

                    if(TaskDraw.GREEDY_ALGORITHM && TaskDraw.GREEDY_ALGORITHM_SELECTION == Constants.CIRCULAR_SUCCESSIVE_REFINEMENT) {
                        tasks[i][j] = new CircularSuccessiveRefinementGuessingDraw(action, tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending,  s.post_processing_order, s.pbs, s.gs.gradient_offset,  s.contourFactor, s.gps, s.pps, s.ds);
                    }
                    else if (!TaskDraw.GREEDY_ALGORITHM && TaskDraw.BRUTE_FORCE_ALG == 2) {
                        tasks[i][j] = new CircularBruteForceDraw(action, tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending,  s.post_processing_order, s.pbs, s.gs.gradient_offset,  s.contourFactor, s.gps, s.pps, s.ds);
                    }
                    else if (!TaskDraw.GREEDY_ALGORITHM && TaskDraw.BRUTE_FORCE_ALG == 0) {
                        tasks[i][j] = new BruteForceDraw(action, tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending,  s.post_processing_order, s.pbs, s.gs.gradient_offset,  s.contourFactor, s.gps, s.pps, s.ds);
                    }
                    else {
                        tasks[i][j] = new BruteForce2Draw(action, tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring, s.color_blending,  s.post_processing_order, s.pbs, s.gs.gradient_offset,  s.contourFactor, s.gps, s.pps, s.ds);
                    }

                    tasks[i][j].setTaskId(taskId);
                }
            }

            if(tasks[0][0].hasCircularLogic()) {
                CircularBruteForceDraw.initCoordinates(image_size, false, true, tasks[0][0].usesSuccessiveRefinement());
                if(tasks[0][0].usesSuccessiveRefinement()) {
                    CircularSuccessiveRefinementGuessingDraw.initCoordinates(image_size, false);
                }
            }
            else {
                CircularBruteForceDraw.clear();
            }
        }
    }

    private void createTasksColorCycling() {

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            int taskId = 0;
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++, taskId++) {
                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, image_size);
                    tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.max_iterations, ptr, s.fractal_color, s.dem_color, image, s.ps.color_cycling_location, s.ps2.color_cycling_location,  s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,   color_cycling_speed, s.fs,    s.color_blending,  s.post_processing_order,   s.pbs,  cycle_colors, cycle_lights, cycle_gradient, color_cycling_adjusting_value, s.ds, s.gs.gradient_offset,  s.contourFactor, s.fns.smoothing, s.gps, s.pps);
                    tasks[i][j].setTaskId(taskId);
                }
            }
        }
    }

    private void createTasksRotate3DModel() {

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2){
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            int tile = s.d3s.detail <= 60 ? 1 : TaskDraw.TILE_SIZE;

            int taskId = 0;
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++, taskId++) {
                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m,s.d3s.detail / tile);
                    tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.d3s, true, ptr, image, s.fs, s.color_blending, s.contourFactor, s.fns.smoothing, s.gps);
                    tasks[i][j].setTaskId(taskId);
                }
            }
        }

    }

    private void createTasksPaletteAndFilter3DModel() {

        synchronized (this) {
            if (thread_grouping == 0) {
                tasks = new TaskDraw[n][n];
                TaskDraw.resetTaskData(n * n, false);
            } else if (thread_grouping == 1 || thread_grouping == 2) {
                tasks = new TaskDraw[1][n];
                TaskDraw.resetTaskData(n, false);
            }
            else if(thread_grouping == 3 || thread_grouping == 4 || thread_grouping == 5) {
                tasks = new TaskDraw[m][n];
                TaskDraw.resetTaskData(m * n, false);
            }

            int taskId = 0;
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++, taskId++) {
                    TaskSplitCoordinates tsc = TaskSplitCoordinates.get(j, i, thread_grouping, n, m, s.d3s.detail);
                    tasks[i][j] = new BruteForceDraw(tsc.FROMx, tsc.TOx, tsc.FROMy, tsc.TOy, s.d3s, false, ptr, image, s.fs, s.color_blending, s.contourFactor, s.fns.smoothing, s.gps);
                    tasks[i][j].setTaskId(taskId);
                }
            }

            if(s.d3s.d3) {
                setP3DDraw(false);
            }
        }

    }

    public void shiftPalettePost() {
        updateColorPalettesMenu();
        updateColors(false);
    }

    public void shiftPalette(boolean outcoloring) {

        resetOrbit();
        new ShiftPaletteDialog(ptr, s, outcoloring);

    }

    public void setPlane(int temp) {

        resetOrbit();
        int oldSelected = s.fns.plane_type;
        s.fns.plane_type = temp;

        if (s.fns.plane_type == USER_PLANE) {
            new PlaneFormulaDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == TWIRL_PLANE) {
            new TwirlPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == SHEAR_PLANE) {
            new ShearPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == RIPPLES_PLANE) {
            new RipplesPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == KALEIDOSCOPE_PLANE) {
            new KaleidoscopePlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == PINCH_PLANE) {
            new PinchPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == FOLDUP_PLANE || s.fns.plane_type == FOLDDOWN_PLANE || s.fns.plane_type == FOLDRIGHT_PLANE || s.fns.plane_type == FOLDLEFT_PLANE || s.fns.plane_type == INFLECTION_PLANE || s.fns.plane_type == BIPOLAR_PLANE || s.fns.plane_type == INVERSED_BIPOLAR_PLANE) {
            new GenericPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == FOLDIN_PLANE || s.fns.plane_type == FOLDOUT_PLANE) {
            new FoldInOutPlaneDialog(ptr, s, oldSelected, planes);
            return;
        } else if (s.fns.plane_type == CIRCLEINVERSION_PLANE) {
            new CircleInversionPlaneDialog(ptr, s, oldSelected, planes);
            return;

        } else if (s.fns.plane_type == SKEW_PLANE) {
            new SkewPlaneDialog(ptr, s, oldSelected, planes);
            return;
        }
        else if (s.fns.plane_type == INFLECTIONS_PLANE) {
            new InflectionsPlaneDialog(ptr, s, oldSelected, planes);
            return;
        }
        else {
            defaultFractalSettings(true);
        }
    }

    public void setBailoutTest(int temp) {

        resetOrbit();
        int oldSelection = s.fns.bailout_test_algorithm;
        s.fns.bailout_test_algorithm = temp;

        if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {
            new BailoutNNormDialog(ptr, s, oldSelection, bailout_tests, true);
            return;
        } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
            new UserBailoutConditionDialog(ptr, s, oldSelection, bailout_tests, true);
            return;
        }

        setBailoutTestPost();

    }

    public void setConvergentBailoutTest(int temp) {

        resetOrbit();
        int oldSelection = s.fns.cbs.convergent_bailout_test_algorithm;
        s.fns.cbs.convergent_bailout_test_algorithm = temp;

        if (s.fns.cbs.convergent_bailout_test_algorithm == CONVERGENT_BAILOUT_CONDITION_NNORM) {
            new BailoutNNormDialog(ptr, s, oldSelection, convergent_bailout_tests, false);
            return;
        } else if (s.fns.cbs.convergent_bailout_test_algorithm == CONVERGENT_BAILOUT_CONDITION_USER) {
            new UserBailoutConditionDialog(ptr, s, oldSelection, convergent_bailout_tests, false);
            return;
        }

        setBailoutTestPost();

    }

    public void setBailoutTestPost() {

        Fractal.clearReferences(true, true);

        redraw();

    }

    public void resetFunctions() {

        for (int k = 0; k < fractal_functions.length; k++) {

            if (!Settings.isRootSolvingMethod(k) && k != KLEINIAN && k != MAGNETIC_PENDULUM && k != INERTIA_GRAVITY && k != SIERPINSKI_GASKET && !s.exterior_de) {
                fractal_functions[k].setEnabled(true);
            }

            if ((k == KLEINIAN || k == INERTIA_GRAVITY || k == SIERPINSKI_GASKET || k == MAGNETIC_PENDULUM) && !s.fns.julia && !s.julia_map && !s.fns.perturbation && !s.fns.init_val && !s.exterior_de) {
                fractal_functions[k].setEnabled(true);
            }
        }

    }

    public void setOutColoringMode(int temp) {

        resetOrbit();
        int oldSelected = s.fns.out_coloring_algorithm;

        s.fns.out_coloring_algorithm = temp;

        if (s.fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM) {
            resetFunctions();
        }

        if (s.fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
            new OutColoringFormulaDialog(ptr, s, oldSelected, out_coloring_modes);
            return;
        }
        else if (s.fns.out_coloring_algorithm == DISTANCE_ESTIMATOR || s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else if (s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES || s.fns.out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || s.fns.out_coloring_algorithm == ESCAPE_TIME_GRID || s.fns.out_coloring_algorithm == BIOMORPH || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || s.fns.out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);
        } else if (!s.julia_map && !s.fns.julia && !s.fns.perturbation && !s.fns.init_val && !Settings.isRootFindingMethod(s.fns.function)) {
            rootFindingMethodsSetEnabled(true);
        }

        setOutColoringModePost();

    }

    public void setUserOutColoringModePost() {
        if (!s.julia_map && !s.fns.julia && !s.fns.perturbation && !s.fns.init_val && !Settings.isRootFindingMethod(s.fns.function) && !s.exterior_de) {
            rootFindingMethodsSetEnabled(true);
        }
    }

    public void setUserInColoringModePost() {
        options_menu.getPeriodicityChecking().setEnabled(false);
    }

    public void setOutColoringModePost() {

        if (s.fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM && s.fns.in_coloring_algorithm != USER_INCOLORING_ALGORITHM) {
            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                setPalettePreviewsVisible();
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
        }

        redraw();
    }

    public void setInColoringMode(int temp) {

        resetOrbit();
        int oldSelected = s.fns.in_coloring_algorithm;

        s.fns.in_coloring_algorithm = temp;

        if (s.fns.in_coloring_algorithm == MAX_ITERATIONS) {
            if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.pps.ots.useTraps && !s.fns.tcs.trueColorIn && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                    && !(s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) && !TaskDraw.PERTURBATION_THEORY && !TaskDraw.HIGH_PRECISION_CALCULATION) {
                options_menu.getPeriodicityChecking().setEnabled(true);
            }
        } else if (s.fns.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
            new InColoringFormulaDialog(ptr, s, oldSelected, in_coloring_modes);
            return;
        } else {
            options_menu.getPeriodicityChecking().setEnabled(false);
        }

        setInColoringModePost();
    }

    public void setInColoringModePost() {

        if (s.fns.out_coloring_algorithm != USER_OUTCOLORING_ALGORITHM && s.fns.in_coloring_algorithm != USER_INCOLORING_ALGORITHM) {
            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                setPalettePreviewsVisible();
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
        }

        redraw();
    }

    public void setSmoothingPost(boolean recalculate) {
        if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
            TaskDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        } else {
            TaskDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color, s.fns.smoothing_fractional_transfer_method).getRawPalette();
        }

        TaskDraw.palette_outcoloring.setGeneratedPaletteSettings(true, s.gps);
        TaskDraw.palette_incoloring.setGeneratedPaletteSettings(false, s.gps);

        TaskDraw.COLOR_SMOOTHING_METHOD = s.color_smoothing_method;

        updateColorPalettesMenu();
        options_menu.getProcessing().updateIcons(s);

        if(s.needsChangeOfSmoothing() || recalculate) {
            redraw();
        }
        else {
            updateColors(false);
        }
    }

    public void setSmoothing() {

        resetOrbit();
        new SmoothingDialog(ptr, s);

    }

    public void setBumpMap() {

        resetOrbit();
        new BumpMappingDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setRainbowPalette() {

        resetOrbit();
        new RainbowPaletteDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setFakeDistanceEstimation() {

        resetOrbit();
        new FakeDistanceEstimationDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setGreyScaleColoring() {

        resetOrbit();
        new GreyscaleColoringDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setEntropyColoring() {

        resetOrbit();
        new EntropyColoringDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setOffsetColoring() {

        resetOrbit();
        new OffsetColoringDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setExteriorDistanceEstimation() {

        resetOrbit();
        new ExteriorDistanceEstimationDialog(ptr, s);

    }

    public void setExteriorDistanceEstimationPost() {

        if (s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else {
            resetFunctions();

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.julia && !s.julia_map && !s.fns.perturbation && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }
        }

        options_menu.getProcessing().updateIcons(s);

        redraw();
    }

    public void setJuliaMapPost(boolean julia_map, int julia_grid_first_dimension) {

        boolean oldJM = s.julia_map;

        if(!oldJM && !julia_map){
            return;
        }

        s.julia_map = julia_map;

        if(s.julia_map) {
            if(!oldJM) {
                tools_menu.getJuliaMap().setIcon(getIcon("julia_map_enabled.png"));
                toolbar.getJuliaMapButton().setSelected(true);
                rootFindingMethodsSetEnabled(false);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                fractal_functions[KLEINIAN].setEnabled(false);
                fractal_functions[INERTIA_GRAVITY].setEnabled(false);

                options_menu.getGreedyAlgorithm().setEnabled(false);

                tools_menu.getJulia().setEnabled(false);
                toolbar.getJuliaButton().setEnabled(false);

                tools_menu.getOrbit().setEnabled(false);
                toolbar.getOrbitButton().setEnabled(false);

                tools_menu.getDomainColoring().setEnabled(false);
                toolbar.getDomainColoringButton().setEnabled(false);

                tools_menu.get3D().setEnabled(false);
                toolbar.get3DButton().setEnabled(false);
            }

            this.julia_grid_first_dimension = julia_grid_first_dimension;

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            whole_image_done = false;

            resetImage();

            createTasksJuliaMap();

            calculation_time = System.currentTimeMillis();

            startTasks();
        }
        else {
            tools_menu.getJuliaMap().setIcon(getIcon("julia_map.png"));
            toolbar.getJuliaMapButton().setSelected(false);

            setOptions(false);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            whole_image_done = false;

            resetImage();

            createTasks(false, true, false, false);

            calculation_time = System.currentTimeMillis();

            startTasks();

        }


    }

    public void setJuliaMap() {

        resetOrbit();
        new JuliaMapDialog(ptr, julia_grid_first_dimension, s);

    }

    public void setJuliter() {

        resetOrbit();
        new JuliterDialog(ptr, s.fns.juliterIterations, s.fns.juliterIncludeInitialIterations, s);

    }

    public void setJuliterPost(boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations) {

        boolean oldJuliter = s.fns.juliter;

        if(!oldJuliter && !juliter){
            return;
        }

        s.fns.juliter = juliter;

        if(s.fns.juliter) {

            if(!oldJuliter) {
                tools_menu.getJuliter().setIcon(getIcon("juliter_enabled.png"));
            }
            s.fns.juliter = true;
            s.fns.juliterIterations = juliterIterations;
            s.fns.juliterIncludeInitialIterations = juliterIncludeInitialIterations;

            setOptions(false);

            progress.setValue(0);

            whole_image_done = false;

            resetImage();

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));


            if (s.d3s.d3) {
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            if(s.julia_map) {
                createTasksJuliaMap();
            } else {
                createTasks(false, true, false, false);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();

        }
        else {
            tools_menu.getJuliter().setIcon(getIcon("juliter.png"));
            setOptions(false);

            progress.setValue(0);

            whole_image_done = false;

            resetImage();

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            if (s.d3s.d3) {
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            if(s.julia_map) {
                createTasksJuliaMap();
            } else {
                createTasks(false, true, false, false);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }

    }

    public void drawZoomWindow(Graphics2D brush) {

        int x1, y1;
        try {
            x1 = (int) main_panel.getMousePosition().getX();
            y1 = (int) main_panel.getMousePosition().getY();
        } catch (Exception ex) {
            return;
        }

        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
            return;
        }

        int new_size = (int) (image_size / zoom_factor);

        BasicStroke old_stroke = (BasicStroke) brush.getStroke();
        if (zoom_window_style == 0) {
            float[] dash1 = {5.0f};
            BasicStroke dashed
                    = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);
            brush.setStroke(dashed);
        }

        brush.setColor(zoom_window_color);
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        brush.setFont(new Font("Arial", Font.BOLD, 16));

        if(ZOOM_ON_THE_CURSOR) {
            double xPercentage = ((double)x1) / image_size;
            double yPercentage = ((double)y1) / image_size;
            int drawX = (int)(x1 - xPercentage * new_size);
            int drawY = (int)(y1 - yPercentage * new_size);
            brush.drawRect(drawX, drawY, new_size, new_size);
            brush.drawString("x" + String.format("%4.2f", zoom_factor), drawX + 5, drawY - 5);
        }
        else {
            int drawX = x1 - new_size / 2;
            int drawY = y1 - new_size / 2;
            brush.drawRect(drawX, drawY, new_size, new_size);
            brush.drawString("x" + String.format("%4.2f", zoom_factor), drawX + 5, drawY - 5);
        }

        if (zoom_window_style == 0) {
            brush.setStroke(old_stroke);
        }

    }

    public void drawBoundaries(Graphics2D brush, boolean mode) {

        brush.setColor(boundaries_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        if (old_polar_projection) {
            double start;
            double center = Math.log(old_size.doubleValue());
            double muly = (2 * s.circle_period * Math.PI) / image_size;

            double mulx = muly * old_height_ratio;

            start = center - mulx * image_size * 0.5;

            for (int i = 1; i <= boundaries_num; i++) {

                double num;
                if (boundaries_spacing_method == 0) {
                    num = Math.pow(2, i - 1.0);
                } else {
                    num = i;
                }

                if (num == s.fns.bailout && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                    continue;
                }

                int radius_x = (int) ((Math.log(num) - start) / mulx + 0.5);
                brush.drawLine(radius_x, 0, radius_x, image_size);

                if (mode) {
                    brush.drawString("" + num, radius_x + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                } else {
                    brush.drawString("" + num, radius_x + 6, 12);
                }
            }

            if (s.fns.bailout < 100 && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                int radius_x = (int) ((Math.log(s.fns.bailout) - start) / mulx + 0.5);

                BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                float[] dash1 = {5.0f};
                BasicStroke dashed
                        = new BasicStroke(1.0f,
                                BasicStroke.CAP_BUTT,
                                BasicStroke.JOIN_MITER,
                                5.0f, dash1, 0.0f);
                brush.setStroke(dashed);

                brush.drawLine(radius_x, 0, radius_x, image_size);

                if (mode) {
                    brush.drawString("" + s.fns.bailout, radius_x + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                } else {
                    brush.drawString("" + s.fns.bailout, radius_x + 6, 12);
                }
                brush.setStroke(old_stroke);
            }

        } else {
            double size_2_x = old_size.doubleValue() * 0.5;
            double size_2_y = (old_size.doubleValue() * old_height_ratio) * 0.5;
            double temp_xcenter_size = old_xCenter.doubleValue() - size_2_x;
            double temp_ycenter_size = old_yCenter.doubleValue() + size_2_y;
            double temp_size_image_size_x = old_size.doubleValue() / image_size;
            double temp_size_image_size_y = (old_size.doubleValue() * old_height_ratio) / image_size;

            int x0 = (int) ((0 - temp_xcenter_size) / temp_size_image_size_x + 0.5);
            int y0 = (int) ((-0 + temp_ycenter_size) / temp_size_image_size_y + 0.5);

            brush.drawLine(x0, 0, x0, image_size);
            brush.drawLine(0, y0, image_size, y0);

            if (mode) {
                brush.drawString("0.0", x0 + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                brush.drawString("0.0", 6 + scroll_pane.getHorizontalScrollBar().getValue(), y0 + 12);
            } else {
                brush.drawString("0.0", x0 + 6, 12);
                brush.drawString("0.0", 6, y0 + 12);
            }

            if (boundaries_type == 0) {
                for (int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if (boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1);
                    } else {
                        num = i;
                    }

                    if (num == s.fns.bailout && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int) ((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));
                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, (int) (x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int) (y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);
                }

                if (s.fns.bailout < 100 && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                    int radius_x = Math.abs(x0 - (int) ((s.fns.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.fns.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                    float[] dash1 = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + s.fns.bailout, (int) (x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int) (y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);

                    brush.setStroke(old_stroke);
                }

            } else if (boundaries_type == 1) {
                for (int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if (boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1.0);
                    } else {
                        num = i;
                    }

                    if (num == s.fns.bailout && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int) ((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, x0 - radius_x - 10, y0 - radius_y - 5);
                }

                if (s.fns.bailout < 100 && !s.isConvergingType() && s.fns.function != KLEINIAN) {

                    int radius_x = Math.abs(x0 - (int) ((s.fns.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.fns.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                    float[] dash1 = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + s.fns.bailout, x0 - radius_x - 10, y0 - radius_y - 5);

                    brush.setStroke(old_stroke);

                }
            } else {
                for (int i = 1; i <= boundaries_num; i++) {

                    double num;
                    if (boundaries_spacing_method == 0) {
                        num = Math.pow(2, i - 1.0);
                    } else {
                        num = i;
                    }

                    if (num == s.fns.bailout && !s.isConvergingType() && s.fns.function != KLEINIAN) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int) ((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    brush.drawLine(x0 - radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 + radius_y);

                    brush.drawLine(x0 + radius_x, y0, x0, y0 - radius_y);

                    brush.drawLine(x0 - radius_x, y0, x0, y0 + radius_y);

                    brush.drawString("" + num, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);
                }

                if (s.fns.bailout < 100 && !s.isConvergingType() && s.fns.function != KLEINIAN) {

                    int radius_x = Math.abs(x0 - (int) ((s.fns.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.fns.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                    float[] dash1 = {5.0f};
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

                    brush.drawString("" + s.fns.bailout, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);

                    brush.setStroke(old_stroke);

                }
            }
        }

    }

    public void drawGrid(Graphics2D brush, boolean mode) {

        CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(old_xCenter, old_yCenter, old_size, old_height_ratio, image_size);


        brush.setColor(grid_color);
        brush.setFont(new Font("Arial", Font.BOLD, 9));
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        for (int i = 1; i < grid_tiles; i++) {
            brush.drawLine(i * image_size / grid_tiles, 0, i * image_size / grid_tiles, image_size);
            brush.drawLine(0, i * image_size / grid_tiles, image_size, i * image_size / grid_tiles);

            BigPoint p = location.getPoint(i * image_size / grid_tiles, i * image_size / grid_tiles);

            String tempStr = "", temp2Str = "";

            if(old_size.compareTo(MyApfloat.MIN_DOUBLE_SIZE) > 0) {
                double temp = p.x.doubleValue();
                if (Math.abs(temp) < 1e-15) {
                    temp = 0;
                }

                double temp2 = p.y.doubleValue();
                if(Math.abs(temp2) < 1e-15) {
                    temp2 = 0;
                }

                tempStr = "" + temp;
                temp2Str = "" + temp2;
            }
            else {
                tempStr = MyApfloat.toStringTruncatedPretty(p.x, 8, 8);
                temp2Str = MyApfloat.toStringTruncatedPretty(p.y, 8, 8);
            }

            if (mode) {
                brush.drawString(tempStr, i * image_size / grid_tiles + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                brush.drawString(temp2Str, 6 + scroll_pane.getHorizontalScrollBar().getValue(), i * image_size / grid_tiles + 12);
            } else {
                brush.drawString(tempStr, i * image_size / grid_tiles + 6, 12);
                brush.drawString(temp2Str, 6, i * image_size / grid_tiles + 12);
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

    private void customPaletteEditor(final int palette_id, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            resetOrbit();
            new CustomPaletteEditorFrame(ptr, options_menu.getOutColoringPalette(), s.fns.smoothing, palette_id, s.ps.color_choice, s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.temp_color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg, outcoloring_mode);
        } else {
            resetOrbit();
            new CustomPaletteEditorFrame(ptr, options_menu.getInColoringPalette(), s.fns.smoothing, palette_id, s.ps2.color_choice, s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.temp_color_cycling_location_second_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, outcoloring_mode);
        }

    }

    public void goTo() {

        resetOrbit();
        if (s.fns.julia) {
            goToJulia();
        } else {
            goToFractal();
        }
    }

    public void exit() {

        int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            saveSettings();
            exit(0);
        } else if (ans == JOptionPane.NO_OPTION) {
            exit(0);
        }
    }

    private void createTasksJuliaMap() {

        synchronized (this) {
            tasks = new TaskDraw[julia_grid_first_dimension][julia_grid_first_dimension];

            TaskDraw.resetTaskData(julia_grid_first_dimension * julia_grid_first_dimension, false);
            Parser.usesUserCode = false;

            int n = julia_grid_first_dimension;

            if(s.julia_map) {
                if(TaskDraw.julia_map_thread_calculation_executor == null || TaskDraw.julia_map_thread_calculation_executor.getPoolSize() != julia_grid_first_dimension * julia_grid_first_dimension) {
                    if(TaskDraw.julia_map_thread_calculation_executor != null) {
                        TaskDraw.julia_map_thread_calculation_executor.shutdown();
                    }
                    TaskDraw.julia_map_thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(julia_grid_first_dimension * julia_grid_first_dimension);
                }
            }

            try {
                int taskId = 0;
                for (int i = 0; i < tasks.length; i++) {
                    for (int j = 0; j < tasks[i].length; j++, taskId++) {
                        tasks[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, PERIODICITY_CHECKING, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio,  s.polar_projection, s.circle_period,   s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps.color_density, s.ps2.color_intensity, s.ps2.transfer_function, s.ps2.color_density, s.usePaletteForInColoring,    s.color_blending,   s.post_processing_order,   s.pbs,  s.gs.gradient_offset,  s.contourFactor, s.gps, s.js, s.pps);
                        tasks[i][j].setTaskId(taskId);
                    }
                }
            } catch (ParserException e) {
                JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }
        }

    }

    public void increaseIterations() {

        resetOrbit();
        if (s.max_iterations == MAX_ITERATIONS_NUMBER) {
            return;
        }
        s.max_iterations++;

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void doubleIterations() {

        resetOrbit();
        if (s.max_iterations * 2 <= 0) {
            return;
        }
        s.max_iterations *= 2;

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void decreaseIterations() {

        resetOrbit();
        if (s.max_iterations > 1) {
            s.max_iterations--;
        } else {
            return;
        }

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void shiftPaletteForward(boolean outcoloring) {

        resetOrbit();

        if (outcoloring) {
            s.ps.color_cycling_location++;

            s.ps.color_cycling_location = s.ps.color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : s.ps.color_cycling_location;

            if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location = s.ps.color_cycling_location;
            }
        } else {
            s.ps2.color_cycling_location++;

            s.ps2.color_cycling_location = s.ps2.color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : s.ps2.color_cycling_location;

            if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location_second_palette = s.ps2.color_cycling_location;
            }
        }

        updateColorPalettesMenu();

        updateColors(false);
    }

    public void shiftPaletteBackward(boolean outcoloring) {

        resetOrbit();
        if (outcoloring) {
            if (s.ps.color_cycling_location > 0) {
                s.ps.color_cycling_location--;

                if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
                    s.temp_color_cycling_location = s.ps.color_cycling_location;
                }
            } else {
                return;
            }
        } else if (s.ps2.color_cycling_location > 0) {
            s.ps2.color_cycling_location--;

            if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location_second_palette = s.ps2.color_cycling_location;
            }
        } else {
            return;
        }

        updateColorPalettesMenu();

        updateColors(false);
    }

    public void setUsePaletteForInColoring() {

        resetOrbit();

        if (!options_menu.getUsePaletteForInColoring().isSelected()) {
            s.usePaletteForInColoring = false;
            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)){
                infobar.getInColoringPalettePreview().setVisible(false);
                infobar.getInColoringPalettePreviewLabel().setVisible(false);
                infobar.getMaxIterationsColorPreview().setVisible(true);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
            }
        } else {
            s.usePaletteForInColoring = true;

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                if(!s.gps.useGeneratedPaletteInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                }
                else {
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
                infobar.getMaxIterationsColorPreview().setVisible(false);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            }
        }

        updateColors(false);
    }

    public void setPerturbation() {

        resetOrbit();
        new PerturbationDialog(ptr, s);

    }

    public void setPerturbationPost(boolean pertubation) {

        boolean oldPerturbation = s.fns.perturbation;

        if(!oldPerturbation && !pertubation) {
            return;
        }

        s.fns.perturbation = pertubation;

        if(s.fns.perturbation) {
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            fractal_functions[KLEINIAN].setEnabled(false);
            fractal_functions[INERTIA_GRAVITY].setEnabled(false);
            options_menu.getPerturbation().setIcon(getIcon("check.png"));
        }
        else {
            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.init_val && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
                options_menu.getPerturbation().setIcon(null);
            }
        }

        defaultFractalSettings(true);

    }

    public void setInitialValue() {

        resetOrbit();
        new InitialValueDialog(ptr, s);

    }

    public void setInitialValuePost(boolean initVal) {

        boolean oldInitVal = s.fns.init_val;

        if(!oldInitVal && !initVal) {
            return;
        }

        s.fns.init_val = initVal;

        if(s.fns.init_val) {
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
            fractal_functions[KLEINIAN].setEnabled(false);
            fractal_functions[INERTIA_GRAVITY].setEnabled(false);
            options_menu.getInitialValue().setIcon(getIcon("check.png"));
        }
        else {
            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.perturbation && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
                options_menu.getInitialValue().setIcon(null);
            }
        }

        defaultFractalSettings(true);

    }

    public void setColorCyclingButton() {

        resetOrbit();
        if (!toolbar.getColorCyclingButton().isSelected()) {
            toolbar.getColorCyclingButton().setSelected(true);
            tools_menu.getColorCycling().setSelected(true);
        } else {
            toolbar.getColorCyclingButton().setSelected(false);
            tools_menu.getColorCycling().setSelected(false);
        }

        setColorCycling();

    }

    public void setJuliaButton() {

        resetOrbit();
        if (!toolbar.getJuliaButton().isSelected()) {
            toolbar.getJuliaButton().setSelected(true);
            tools_menu.getJulia().setSelected(true);
        } else {
            toolbar.getJuliaButton().setSelected(false);
            tools_menu.getJulia().setSelected(false);
        }

        setJuliaOption();

    }

    public void setOrbitButton() {

        resetOrbit();
        if (!toolbar.getOrbitButton().isSelected()) {
            toolbar.getOrbitButton().setSelected(true);
            tools_menu.getOrbit().setSelected(true);
        } else {
            toolbar.getOrbitButton().setSelected(false);
            tools_menu.getOrbit().setSelected(false);
        }

        setOrbitOption();

    }

    public void openCustomPaletteEditor(boolean outcoloring_mode) {

        resetOrbit();
        customPaletteEditor(CUSTOM_PALETTE_ID, outcoloring_mode);

    }

    public void showCHMHelpFile() {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/help/Fractal_Zoomer_Help.chm").openStream();
            File TempFile = File.createTempFile("fzhelp", ".chm");
            FileOutputStream out = new FileOutputStream(TempFile);
            byte[] temp = new byte[32768];
            int rc;

            while ((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();
            TempFile.deleteOnExit();
            Desktop.getDesktop().open(TempFile);
        } catch (Exception ex) {
        }

    }

    public void showFractIntHelpFile() {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/help/FractInt.pdf").openStream();
            File TempFile = File.createTempFile("fractint", ".pdf");
            FileOutputStream out = new FileOutputStream(TempFile);
            byte[] temp = new byte[32768];
            int rc;

            while ((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();
            TempFile.deleteOnExit();
            Desktop.getDesktop().open(TempFile);
        } catch (Exception ex) {
        }

    }

    public void randomPalette() {

        resetOrbit();
        CustomPaletteEditorFrame.randomPalette(CustomPaletteEditorFrame.generator, true, s.ps.custom_palette, CustomPaletteEditorFrame.random_palette_algorithm, CustomPaletteEditorFrame.equal_hues, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg, CustomPaletteEditorFrame.pastel);
        CustomPaletteEditorFrame.randomPalette(CustomPaletteEditorFrame.generator, true, s.ps2.custom_palette, CustomPaletteEditorFrame.random_palette_algorithm, CustomPaletteEditorFrame.equal_hues, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.color_cycling_location, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, CustomPaletteEditorFrame.pastel);

        setPalette(CUSTOM_PALETTE_ID, null, 2);

    }

    public void setToolbar() {

        resetOrbit();
        toolbar.setVisible(options_menu.getToolbar().isSelected());

    }

    public void setStatubar() {

        resetOrbit();
        statusbar.setVisible(options_menu.getStatusbar().isSelected());

    }

    public void setDebugbar() {

        resetOrbit();
        debugbar.setVisible(options_menu.getDebugbar().isSelected());

    }

    public void setInfobar() {

        resetOrbit();
        infobar.setVisible(options_menu.getInfobar().isSelected());

    }

    public void setFullScreen() {

        resetOrbit();
        if (!options_menu.getFullscreen().isSelected()) {
            if (isVisible()) {
                dispose();
                setExtendedState(JFrame.NORMAL);
                setUndecorated(false);
                setVisible(true);
            }
        } else if (isVisible()) {
            dispose();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
            setVisible(true);
        } else {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }

    }

    public int getNumberOfThreads() {

        if(thread_grouping == 0) {
            return n * n;
        }
        else if(thread_grouping == 1 || thread_grouping == 2) {
            return n;
        }
        else {
            return m * n;
        }

    }

    public int getJuliaMapSlices() {

        return julia_grid_first_dimension * julia_grid_first_dimension;

    }

    public void filtersOptions() {

        resetOrbit();
        new FiltersOptionsFrame(ptr, s.fs.filters_options_vals, s.fs.filters_options_extra_vals, s.fs.filters_colors, s.fs.filters_extra_colors, filters_opt, s.fs.filters_order, s.fs.filters, s.fs);

    }

    public void setPolarProjection() {

        resetOrbit();
        new PolarProjectionDialog(ptr, s);
    }

    public void setPolarProjectionPost(boolean polar) {

        boolean oldPolar = s.polar_projection;

        s.polar_projection = polar;

        if(s.polar_projection) {
            if(!oldPolar) {
                tools_menu.getPolarProjection().setIcon(getIcon("polar_projection_enabled.png"));
                toolbar.getPolarProjectionButton().setSelected(true);

                file_menu.getUp().setEnabled(false);
                file_menu.getDown().setEnabled(false);

                tools_menu.getGrid().setEnabled(false);
                tools_menu.getZoomWindow().setEnabled(false);

                if (s.d3s.d3) {
                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;
                }
            }

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            whole_image_done = false;

            resetImage();

            if (s.d3s.d3) {
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            if(s.julia_map) {
                createTasksJuliaMap();
            } else {
                createTasks(false, true, false, false);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }else {

            if(oldPolar){
                tools_menu.getPolarProjection().setIcon(getIcon("polar_projection.png"));
                toolbar.getPolarProjectionButton().setSelected(false);

                file_menu.getUp().setEnabled(true);
                file_menu.getDown().setEnabled(true);

                if (s.d3s.d3) {
                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;
                }

            }

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            whole_image_done = false;

            resetImage();

            if (s.d3s.d3) {
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            if(s.julia_map) {
                createTasksJuliaMap();
            } else {
                createTasks(false, true, false, false);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }
    }

    public void setDomainColoringSettings(boolean domain_coloring) {

        boolean oldDomain = s.ds.domain_coloring;

        s.ds.domain_coloring = domain_coloring;

        if(s.ds.domain_coloring) {
            try {

                if(!oldDomain) {
                    tools_menu.getDomainColoring().setIcon(getIcon("domain_coloring_enabled.png"));

                    toolbar.getDomainColoringButton().setSelected(true);

                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    tools_menu.getJulia().setEnabled(false);
                    toolbar.getJuliaButton().setEnabled(false);

                    if (s.ds.domain_coloring_mode != 1) {
                        toolbar.getOutCustomPaletteButton().setEnabled(false);
                        toolbar.getOutCustomPalette2Button().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(false);
                        options_menu.getRandomPalette().setEnabled(false);
                        options_menu.getOutColoringPaletteMenu().setEnabled(false);
                    }

                    options_menu.getDistanceEstimation().setEnabled(false);

                    options_menu.getFakeDistanceEstimation().setEnabled(false);
                    options_menu.getNumericalDistanceEstimator().setEnabled(false);
                    options_menu.getContourColoring().setEnabled(false);

                    options_menu.getEntropyColoring().setEnabled(false);
                    options_menu.getOffsetColoring().setEnabled(false);
                    options_menu.getGreyScaleColoring().setEnabled(false);
                    options_menu.getHistogramColoring().setEnabled(false);

                    options_menu.getRainbowPalette().setEnabled(false);

                    options_menu.getOrbitTraps().setEnabled(false);

                    options_menu.getPaletteGradientMerging().setEnabled(false);
                    options_menu.getInColoringPaletteMenu().setEnabled(false);
                    options_menu.getDirectColor().setEnabled(false);
                    options_menu.getDirectColor().setSelected(false);
                    TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

                    toolbar.getInCustomPaletteButton().setEnabled(false);
                    toolbar.getInCustomPalette2Button().setEnabled(false);

                    infobar.getGradientPreviewLabel().setVisible(true);
                    infobar.getGradientPreview().setVisible(true);
                    infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                    infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);

                    options_menu.getOutColoringMenu().setEnabled(false);
                    options_menu.getInColoringMenu().setEnabled(false);
                    options_menu.getFractalColor().setEnabled(false);
                    options_menu.getStatisticsColoring().setEnabled(false);

                    options_menu.getOutTrueColoring().setEnabled(false);
                    options_menu.getInTrueColoring().setEnabled(false);

                    options_menu.getGreedyAlgorithm().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutConditionMenu().setEnabled(false);
                    options_menu.getConvergentBailoutConditionMenu().setEnabled(false);

                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

                    updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);

                    TaskDraw.setDomainImageData(image_size, true);

                }
                else {
                    if (s.ds.domain_coloring_mode != 1) {
                        toolbar.getOutCustomPaletteButton().setEnabled(false);
                        toolbar.getOutCustomPalette2Button().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(false);
                        options_menu.getRandomPalette().setEnabled(false);
                        options_menu.getOutColoringPaletteMenu().setEnabled(false);
                    }
                    else {
                        toolbar.getOutCustomPaletteButton().setEnabled(true);
                        toolbar.getOutCustomPalette2Button().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(true);
                        options_menu.getRandomPalette().setEnabled(true);
                        options_menu.getOutColoringPaletteMenu().setEnabled(true);
                    }

                    infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                    infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));

                    updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);
                }

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

                whole_image_done = false;

                resetImage();

                if (s.d3s.d3) {
                    ArraysFillColor(image, Color.BLACK.getRGB());
                }

                createTasks(false, true, false, false);

                calculation_time = System.currentTimeMillis();

                startTasks();
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }
        }
        else {

            if(oldDomain){
                tools_menu.getDomainColoring().setIcon(getIcon("domain_coloring.png"));

                TaskDraw.setDomainImageData(image_size, false);

                toolbar.getDomainColoringButton().setSelected(false);

                if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                    if (s.usePaletteForInColoring) {
                        if(!s.gps.useGeneratedPaletteInColoring) {
                            infobar.getInColoringPalettePreview().setVisible(true);
                            infobar.getInColoringPalettePreviewLabel().setVisible(true);
                        }
                        else {
                            infobar.getInColoringPalettePreview().setVisible(false);
                            infobar.getInColoringPalettePreviewLabel().setVisible(false);
                        }
                        infobar.getMaxIterationsColorPreview().setVisible(false);
                        infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                    } else {
                        infobar.getMaxIterationsColorPreview().setVisible(true);
                        infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                        infobar.getInColoringPalettePreview().setVisible(false);
                        infobar.getInColoringPalettePreviewLabel().setVisible(false);
                    }

                    infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                    infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                }
                else {
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                    infobar.getOutColoringPalettePreview().setVisible(false);
                    infobar.getOutColoringPalettePreviewLabel().setVisible(false);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }

                updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);

                if (s.d3s.d3) {
                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;
                }

            }

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            whole_image_done = false;

            resetImage();

            if (s.d3s.d3) {
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            createTasks(false, true, false, false);

            calculation_time = System.currentTimeMillis();

            startTasks();

        }
    }

    public void setDomainColoring() {

        new DomainColoringFrame(ptr, s);

    }

    public void set3DOption() {

        resetOrbit();
        new D3Dialog(ptr, s);

    }

    public void bring3dTofront() {
        for(P3DHeightMap heightMapFrame : heightMapFrames) {
            if (heightMapFrame.isValid()) {
                heightMapFrame.bringToFront();
            }
        }
    }

    public void set3DOptionPost(boolean d3) {

        boolean oldD3 = s.d3s.d3;

        if(!oldD3 && !d3){
            return;
        }

        s.d3s.d3 = d3;

        if(!s.d3s.d3) {
            try {
                tools_menu.get3D().setIcon(getIcon("3d.png"));
                setOptions(false);

                closeP3D();

                toolbar.get3DButton().setSelected(false);

                TaskDraw.setArrays(image_size, s.ds.domain_coloring, s.needsExtraData());

                progress.setMaximum(image_size * image_size + 1);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

                whole_image_done = false;

                resetImage();

                createTasks(false, true, false, false);

                calculation_time = System.currentTimeMillis();

                startTasks();
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }
        }
        else {
            try {

                if(!oldD3) {

                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;

                    tools_menu.get3D().setIcon(getIcon("3d_enabled.png"));

                    tools_menu.getJulia().setEnabled(false);
                    toolbar.getJuliaButton().setEnabled(false);
                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    tools_menu.getOrbit().setEnabled(false);
                    toolbar.getOrbitButton().setEnabled(false);
                    tools_menu.getColorCycling().setEnabled(false);
                    toolbar.getColorCyclingButton().setEnabled(false);
                    tools_menu.getBoundaries().setEnabled(false);
                    tools_menu.getGrid().setEnabled(false);
                    tools_menu.getZoomWindow().setEnabled(false);

                    options_menu.getDirectColor().setEnabled(false);
                    options_menu.getDirectColor().setSelected(false);
                    TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

                    if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                        if (!s.ds.domain_coloring) {
                            if (s.usePaletteForInColoring) {
                                if(!s.gps.useGeneratedPaletteInColoring) {
                                    infobar.getInColoringPalettePreview().setVisible(true);
                                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                                }
                                else {
                                    infobar.getInColoringPalettePreview().setVisible(false);
                                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                                }
                                infobar.getMaxIterationsColorPreview().setVisible(false);
                                infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                            } else {
                                infobar.getMaxIterationsColorPreview().setVisible(true);
                                infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                                infobar.getInColoringPalettePreview().setVisible(false);
                                infobar.getInColoringPalettePreviewLabel().setVisible(false);
                            }
                        }
                        infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                        infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
                    }

                    infobar.getGradientPreviewLabel().setVisible(true);
                    infobar.getGradientPreview().setVisible(true);

                    scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
                    scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

                    options_menu.getGreedyAlgorithm().setEnabled(false);

                    toolbar.get3DButton().setSelected(true);
                }


                updateP3D();

                setOptions(false);

                TaskDraw.set3DArrays(s.d3s.detail, s.needsExtraData());

                progress.setMaximum(s.d3s.detail * s.d3s.detail + 1);

                progress.setValue(0);

                whole_image_done = false;

                resetImage();

                ArraysFillColor(image, Color.BLACK.getRGB());

                createTasks(false, true, false, false);

                calculation_time = System.currentTimeMillis();

                startTasks();
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                exit(-1);
            }
        }
    }

    public void setBoundariesNumberPost(int boundaries_num, int boundaries_spacing_method, int boundaries_type) {
        this.boundaries_num = boundaries_num;
        this.boundaries_spacing_method = boundaries_spacing_method;
        this.boundaries_type = boundaries_type;
    }

    public void setBoundariesNumber() {

        resetOrbit();
        new BoundariesDialog(ptr, boundaries_num, boundaries_spacing_method, boundaries_type);

    }

    public void setGridTiles() {

        resetOrbit();
        new GridTilesDialog(ptr, grid_tiles);

    }

    public void setGridTilesPost(int grid_tiles) {
        this.grid_tiles = grid_tiles;
    }

    public void setColorIntensity(boolean outcoloring) {

        resetOrbit();
        new ColorIntensityDialog(ptr, s, outcoloring);

    }

    public void setColorDensity(boolean outcoloring) {

        resetOrbit();
        new ColorDensityDialog(ptr, s, outcoloring);

    }

    private void rootFindingMethodsSetEnabled(boolean option) {

        for(int i = 0; i < fractal_functions.length; i++) {
            if(Settings.isRootFindingMethod(i)) {
                fractal_functions[i].setEnabled(option);
            }
        }

    }

    public void optionsEnableShortcut() {
        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        options_menu.getDistanceEstimation().setEnabled(false);
        if (s.fns.out_coloring_algorithm != BIOMORPH) {
            out_coloring_modes[BIOMORPH].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != BANDED) {
            out_coloring_modes[BANDED].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
            out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
            out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID) {
            out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
            out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
        }
        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
            out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
        }

    }

    public void optionsEnableShortcut2() {
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
        out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(false);
        out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutConditionMenu().setEnabled(false);
        options_menu.getConvergentBailoutConditionMenu().setEnabled(s.fns.function != MAGNETIC_PENDULUM);
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
        options_menu.getPlaneInfluenceMenu().setEnabled(false);
    }
    public void updateValues(String mode) {

        old_xCenter = s.xCenter;
        old_yCenter = s.yCenter;
        old_size = s.size;
        old_height_ratio = s.height_ratio;

        old_rotation_vals[0] = s.fns.rotation_vals[0];
        old_rotation_vals[1] = s.fns.rotation_vals[1];

        old_rotation_center[0] = s.fns.rotation_center[0];
        old_rotation_center[1] = s.fns.rotation_center[1];

        old_d3 = s.d3s.d3;
        old_polar_projection = s.polar_projection;
        old_grid = grid;
        old_boundaries = boundaries;

        statusbar.getMode().setText(mode);

    }

    public void setColorCyclingOptionsPost(boolean cycle_colors, boolean cycle_gradient, boolean cycle_lights, int color_cycling_speed, int color_cycling_adjusting_value) {
        this.cycle_colors = cycle_colors;
        this.cycle_gradient = cycle_gradient;
        this.cycle_lights = cycle_lights;
        this.color_cycling_speed = color_cycling_speed;
        this.color_cycling_adjusting_value = color_cycling_adjusting_value;
    }

    public void setColorCyclingOptions() {

        resetOrbit();
        new ColorCyclingDialog(ptr, cycle_colors, cycle_gradient, cycle_lights, color_cycling_speed, color_cycling_adjusting_value);

    }

    public void setApplyPlaneOnWholeJulia() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnWholeJuliaOpt().isSelected()) {
            s.fns.apply_plane_on_julia = false;
        } else {
            s.fns.apply_plane_on_julia = true;
        }

        if (s.fns.julia || s.julia_map) {
            defaultFractalSettings(true);
        }

    }

    public void setApplyPlaneOnJuliaSeed() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnJuliaSeedOpt().isSelected()) {
            s.fns.apply_plane_on_julia_seed = false;
        } else {
            s.fns.apply_plane_on_julia_seed = true;
        }

        if (s.fns.julia || s.julia_map) {
            defaultFractalSettings(true);
        }

    }

    public void Overview() {

        resetOrbit();
        try {
            common.overview(s, PERIODICITY_CHECKING);
        } catch (Exception ex) {
        }

    }

    public void Stats() {

        resetOrbit();
        try {

            JEditorPane textArea = new JEditorPane();

            textArea.setEditable(false);
            textArea.setContentType("text/html");
            textArea.setPreferredSize(new Dimension(500, 500));
            //textArea.setLineWrap(false);
            //textArea.setWrapStyleWord(false);

            JScrollPane scroll_pane_2 = new JScrollPane(textArea);
            scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            textArea.setText("<html>" + "<center><b><u><font size='5' face='arial' color='blue'>Statistics</font></u></b></center><br><br>" +
                    "<font size='4' face='arial'>" + getProgressBar().getToolTipText() + "</font></html>");

            MemoryLabel memory_label = new MemoryLabel(250);
            CpuLabel cpuLabel = new CpuLabel(250);

            Timer timer = new Timer();
            timer.schedule(new RefreshMemoryTask(memory_label), MEMORY_DELAY, MEMORY_DELAY);
            Timer timer2 = new Timer();
            timer2.schedule(new RefreshCpuTask(cpuLabel), CPU_DELAY, CPU_DELAY);


            Object[] message = {
                    " ",
                    scroll_pane_2,
                    " ",
                    "Memory:",
                    memory_label,
                    "CPU Usage:",
                    cpuLabel,
            " "};

            textArea.setCaretPosition(0);

            int res = JOptionPane.showOptionDialog(this, message,  "Statistics", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, getIcon("stats_large.png"), null, null);

            timer.cancel();
            timer2.cancel();

        } catch (Exception ex) {
        }

    }

    public void ThreadStats() {

        resetOrbit();
        try {

            JEditorPane textArea = new JEditorPane();

            textArea.setEditable(false);
            textArea.setContentType("text/html");
            textArea.setPreferredSize(new Dimension(500, 500));
            //textArea.setLineWrap(false);
            //textArea.setWrapStyleWord(false);

            JScrollPane scroll_pane_2 = new JScrollPane(textArea);
            scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            ArrayList<TaskStatistic> taskStatistics = new ArrayList<>();
            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    TaskStatistic t = tasks[i][j].getTaskStatistic();
                    if(t != null) {
                        taskStatistics.add(t);
                    }
                }
            }

            Collections.sort(taskStatistics);

            long maxTime = TaskDraw.max_pixel_calculation_time.get();
            long maxTimePostProcessing = TaskDraw.PostProcessingCalculationTime.get();

            HashSet<String> uniqueThreads = new LinkedHashSet<>();

            for(TaskStatistic stat : taskStatistics) {
                uniqueThreads.add(stat.getThreadName());
            }
            String data = "";

            if(!taskStatistics.isEmpty()) {

                data += "<ul><li>Logical Processors: <b>" + Runtime.getRuntime().availableProcessors() + "</b></ul><br>";

                data += "<ul><li>Threads Used: <b>" + uniqueThreads.size() + "</b></ul><br>";

                data += "<ul><li>Number of Tasks: <b>" + taskStatistics.size() + "</b></ul><br>";

                data += "<ul>";
                for (TaskStatistic stat : taskStatistics) {
                    data += "<li>Task: <b>" + stat.getTaskId() + "</b>";
                    data += "<ul>";
                    data += "<li>Thread: <b>" + stat.getThreadName() + "</b> <span style='font-size:20px;color: " + String.format("#%02x%02x%02x", stat.getThreadColor().getRed(), stat.getThreadColor().getGreen(), stat.getThreadColor().getBlue()) + ";'>&#9632;</span></li>";

                    if(maxTime > 0) {
                        data += "<li>Pixel Calculation Elapsed Time: <b>" + stat.getPixelCalculationTime() + " ms</b></li>";
                        data += "<li>Pixel Calculation Thread Utilization: <b>" + String.format("%.2f", ((double) stat.getPixelCalculationTime()) / maxTime * 100) + "%</b></li>";
                    }
                    if(stat.getPixelsCalculated() >= 0) {
                        data += "<li>Pixels Calculated: <b>" + stat.getPixelsCalculated() + " / " + stat.getTotalPixels() + " (" + String.format("%f", (((double) stat.getPixelsCalculated()) / stat.getTotalPixels()) * 100) + "%)</b></li>";
                    }
                    if(stat.getPixelsCompleted() >= 0) {
                        data += "<li>Pixels Completed: <b>" + stat.getPixelsCompleted() + " / " + stat.getTotalPixels() + " (" + String.format("%f", (((double) stat.getPixelsCompleted()) / stat.getTotalPixels()) * 100) + "%)</b></li>";
                    }
                    if(stat.getPixelsCalculated() >= 0 && stat.getPixelsCompleted() >= 0) {
                        data += "<li>Pixels Guessed: <b>" + (stat.getPixelsCompleted() - stat.getPixelsCalculated()) + " / " + stat.getPixelsCompleted() + " (" + String.format("%f", (((double) (stat.getPixelsCompleted() - stat.getPixelsCalculated())) / stat.getPixelsCompleted()) * 100) + "%)</b></li>";
                    }
                    if (maxTimePostProcessing > 0) {
                        data += "<li>Post Processing Elapsed Time: <b>" + stat.getPostProcessingCalculationTime() + " ms</b></li>";
                        data += "<li>Post Processing Thread Utilization: <b>" + String.format("%.2f", ((double) stat.getPostProcessingCalculationTime()) / maxTimePostProcessing * 100) + "%</b></li>";
                    }
                    if(stat.getPixelsPostProcessed() >= 0) {
                        data += "<li>Pixels Post Processed: <b>" + stat.getPixelsPostProcessed() + " / " + stat.getTotalPixels() + " (" + String.format("%f", (((double) stat.getPixelsPostProcessed()) / stat.getTotalPixels()) * 100) + "%)</b></li>";
                    }
                    if (stat.getExtraPixelsCalculated() > 0) {
                        data += "<li>Extra Pixels Calculated: <b>" + stat.getExtraPixelsCalculated() + "</b></li>";
                    }
                    data += "</ul></li>";
                }
                data += "</ul>";
            }

            textArea.setText("<html>" + "<center><b><u><font size='5' face='arial' color='blue'>Task Statistics</font></u></b></center><br><br>" +
                    "<font size='4' face='arial'>" + data + "</font></html>");

            Object[] message = {
                    " ",
                    scroll_pane_2,
                    " "};

            textArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(this, message,  "Task Statistics", JOptionPane.INFORMATION_MESSAGE, getIcon("stats_tasks_large.png"));

        } catch (Exception ex) {
        }

    }

    public void showUserFormulaHelp() {

        resetOrbit();
        new UserFormulasHelpDialog(main_panel, scroll_pane);

    }

    public void showUsefulLinks() {

        resetOrbit();
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

        if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

        try {
            mx0 = (int) main_panel.getMousePosition().getX();
            my0 = (int) main_panel.getMousePosition().getY();
        } catch (Exception ex) {
        }
    }

    public void updateColorPalettesMenu() {

        //update out coloring palettes
        for (i = 0; i < TOTAL_PALETTES; i++) {

            if (i == DIRECT_PALETTE_ID) {
                continue;
            }

            Color[] c = null;

            if (i == s.ps.color_choice) { // the current activated palette
                if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, s.ps.color_cycling_location, 0, PROCESSING_NONE);
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg);
                } else {
                    c = PresetPalette.getPalette(i, s.ps.color_cycling_location);
                }
            } else// the remaining palettes
             if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.temp_color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg); // temp color cycling loc
                } else {
                    c = PresetPalette.getPalette(i, 0);
                }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (s.fns.smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
                } else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            options_menu.getOutColoringPalette()[i].setIcon(new ImageIcon(palette_preview));
        }

        //update in coloring palettes
        for (i = 0; i < TOTAL_PALETTES; i++) {

            if (i == DIRECT_PALETTE_ID) {
                continue;
            }

            Color[] c = null;

            if (i == s.ps2.color_choice) { // the current activated palette
                if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, s.ps2.color_cycling_location, 0, PROCESSING_NONE);
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.color_cycling_location, s.ps2.scale_factor_palette_val, s.ps2.processing_alg);
                } else {
                    c = PresetPalette.getPalette(i, s.ps2.color_cycling_location);
                }
            } else// the remaining palettes 
             if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.temp_color_cycling_location_second_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg); // temp color cycling loc
                } else {
                    c = PresetPalette.getPalette(i, 0);
                }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (s.fns.smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
                } else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            options_menu.getInColoringPalette()[i].setIcon(new ImageIcon(palette_preview));
        }

        updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);

    }

    public void updateGradientPreview(int offset) {

        infobar.getGradientPreview().setIcon(new ImageIcon(CommonFunctions.getGradientPreview(s.gs, offset, Infobar.GRADIENT_PREVIEW_WIDTH, Infobar.GRADIENT_PREVIEW_HEIGHT)));

    }

    public void updatePalettePreview(int color_cycling_location, int color_cycling_location2) {

        infobar.getOutColoringPalettePreview().setIcon(new ImageIcon(CommonFunctions.getOutColoringPalettePreview(s, color_cycling_location, Infobar.PALETTE_PREVIEW_WIDTH, Infobar.PALETTE_PREVIEW_HEIGHT)));
        infobar.getInColoringPalettePreview().setIcon(new ImageIcon(CommonFunctions.getInColoringPalettePreview(s, color_cycling_location2, Infobar.PALETTE_PREVIEW_WIDTH, Infobar.PALETTE_PREVIEW_HEIGHT)));

    }

    private void updateMaxIterColorPreview() {

        BufferedImage max_it_preview = new BufferedImage(Infobar.SQUARE_TILE_SIZE, Infobar.SQUARE_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(s.fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        infobar.getMaxIterationsColorPreview().setIcon(new ImageIcon(max_it_preview));

    }

    public void savePreferences() {

        PrintWriter writer;
        try {
            writer = new PrintWriter(new File("FZpreferences.ini"));

            writer.println("#Fractal Zoomer preferences.");
            writer.println("#This file contains all the preferences of the user and it is updated,");
            writer.println("#every time the application terminates. Settings that wont have the");
            writer.println("#correct name/number of arguments/argument value, will be ignored");
            writer.println("#and the default values will be used instead.");

            writer.println();
            writer.println();

            writer.println("[Optimizations]");
            writer.println("thread_dim " + ((thread_grouping == 3 || thread_grouping == 4) ? m * n : n));
            if(thread_grouping == 5) {
                writer.println("thread_dim2 " + m);
            }
            writer.println("thread_grouping " + thread_grouping);
            writer.println("greedy_drawing_algorithm " + TaskDraw.GREEDY_ALGORITHM);
            writer.println("greedy_drawing_algorithm_id " + TaskDraw.GREEDY_ALGORITHM_SELECTION);
            writer.println("greedy_drawing_algorithm_use_iter_data " + TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA);
            writer.println("skipped_pixels_coloring " + TaskDraw.SKIPPED_PIXELS_ALG);
            writer.println("mariani_silver_use_dfs " + MarianiSilverDraw.RENDER_USING_DFS);
            writer.println("guess_blocks_selection " + TaskDraw.GUESS_BLOCKS_SELECTION);
            int color = TaskDraw.SKIPPED_PIXELS_COLOR;
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("periodicity_checking " + PERIODICITY_CHECKING);
            writer.println("perturbation_theory " + TaskDraw.PERTURBATION_THEORY);
            writer.println("precision " + MyApfloat.precision);
            writer.println("approximation_algorithm " + TaskDraw.APPROXIMATION_ALGORITHM);
            writer.println("series_approximation_terms " + TaskDraw.SERIES_APPROXIMATION_TERMS);
            writer.println("series_approximation_oom_diff " + TaskDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE);
            writer.println("series_approximation_max_skip " + TaskDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER);
            writer.println("use_full_floatexp_for_deep_zoom " + TaskDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM);
            writer.println("use_full_floatexp_for_all_zoom " + TaskDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM);
            writer.println("automatic_bignum_precision " + TaskDraw.BIGNUM_AUTOMATIC_PRECISION);
            writer.println("bignum_precision_bits " + TaskDraw.BIGNUM_PRECISION);
            writer.println("bignum_precision_factor " + TaskDraw.BIGNUM_PRECISION_FACTOR);
            writer.println("use_threads_for_sa " + TaskDraw.USE_THREADS_FOR_SA);
            writer.println("bla_precision_bits " + TaskDraw.BLA_BITS);
            writer.println("bla_starting_level " + TaskDraw.BLA_STARTING_LEVEL);
            writer.println("use_threads_for_bla " + TaskDraw.USE_THREADS_FOR_BLA);
            writer.println("detect_period " + TaskDraw.DETECT_PERIOD);
            writer.println("brute_force_alg " + TaskDraw.BRUTE_FORCE_ALG);
            writer.println("exponent_diff_ignored " + MantExp.EXPONENT_DIFF_IGNORED);
            writer.println("bignum_library " + TaskDraw.BIGNUM_LIBRARY);
            writer.println("automatic_precision " + MyApfloat.setAutomaticPrecision);
            writer.println("nanomb1_n " + TaskDraw.NANOMB1_N);
            writer.println("nanomb1_m " + TaskDraw.NANOMB1_M);
            writer.println("perturbation_pixel_algorithm " + TaskDraw.PERTUBATION_PIXEL_ALGORITHM);
            writer.println("gather_perturbation_statistics " + TaskDraw.GATHER_PERTURBATION_STATISTICS);
            writer.println("check_bailout_during_deep_not_full_floatexp_mode " + TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE);
            writer.println("gather_tiny_ref_indexes " + TaskDraw.GATHER_TINY_REF_INDEXES);
            //writer.println("high_precision " + ThreadDraw.HIGH_PRECISION_CALCULATION);
            writer.println("high_precision_lib " + TaskDraw.HIGH_PRECISION_LIB);
            writer.println("bignum_sqrt_max_iterations " + BigNum.SQRT_MAX_ITERATIONS);
            writer.println("stop_reference_calculation_after_detected_period " + TaskDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD);
            writer.println("use_custom_floatexp_requirement " + TaskDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT);
            writer.println("load_mpfr " + TaskDraw.LOAD_MPFR);
            writer.println("load_mpir " + TaskDraw.LOAD_MPIR);
            writer.println("#available libs: " + String.join(", ", TaskDraw.mpirWinLibs));
            writer.println("mpir_lib " + TaskDraw.MPIR_LIB);
            writer.println("period_detection_algorithm " + TaskDraw.PERIOD_DETECTION_ALGORITHM);
            writer.println("circular_compare_alg " + TaskDraw.CIRCULAR_COMPARE_ALG);
            writer.println("circular_revert_alg " + TaskDraw.CIRCULAR_REVERT_ALG);
            writer.println("circular_repeat_alg " + TaskDraw.CIRCULAR_REPEAT_ALG);
            writer.println("circular_repeat_spacing " + TaskDraw.CIRCULAR_REPEAT_SPACING);
            writer.println("circular_n " + TaskDraw.CIRCULAR_N);
            writer.println("bla2_detection_method " + LAInfo.DETECTION_METHOD);
            writer.println("bla2_stage0_period_detection_threshold " + LAInfo.Stage0PeriodDetectionThreshold);
            writer.println("bla2_stage0_period_detection_threshold2 " + LAInfo.Stage0PeriodDetectionThreshold2);
            writer.println("bla2_period_detection_threshold " + LAInfo.PeriodDetectionThreshold);
            writer.println("bla2_period_detection_threshold2 " + LAInfo.PeriodDetectionThreshold2);
            writer.println("bla2_la_threshold_scale " + LAInfo.LAThresholdScale);
            writer.println("bla2_la_threshold_c_scale " + LAInfo.LAThresholdCScale);
            writer.println("bla2_double_threshold_limit " + LAReference.doubleThresholdLimit.toDouble());
            writer.println("bla2_convert_to_double_when_possible " + LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE);
            writer.println("always_check_for_precision_decrease " + MyApfloat.alwaysCheckForDecrease);
            writer.println("use_threads_in_bignum_libs " + TaskDraw.USE_THREADS_IN_BIGNUM_LIBS);
            writer.println("calculate_period_every_time_from_start " + TaskDraw.CALCULATE_PERIOD_EVERY_TIME_FROM_START);
            writer.println("mantexpcomplex_format " + TaskDraw.MANTEXPCOMPLEX_FORMAT);
            writer.println("use_fast_delta_location " + TaskDraw.USE_FAST_DELTA_LOCATION);
            writer.println("always_save_extra_pixel_data_on_aa " + TaskDraw.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA);

            writer.println();
            writer.println("[General]");
            writer.println("save_settings_path \"" + SaveSettingsPath + "\"");
            writer.println("save_image_path \"" + SaveImagesPath + "\"");
            writer.println("auto_repaint_image " + AUTO_REPAINT_IMAGE);
            writer.println("auto_repaint_image_delay " + ImageRepainter.REPAINT_SLEEP_TIME);
            writer.println("use_smoothing_for_processing_algs " + TaskDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS);
            writer.println("zoom_on_cursor " + ZOOM_ON_THE_CURSOR);
            writer.println("circular_draw_follows_zoom_to_cursor " + CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR);
            writer.println("draw_image_preview " + TaskDraw.DRAW_IMAGE_PREVIEW);
            writer.println("3d_apply_average_to_triangle_colors " + TaskDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS);
            writer.println("load_drawing_algorithm_from_saves " + TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES);
            writer.println("p3d_aa " + P3D_AA);
            writer.println("p3d_aa_samples " + P3D_AA_SAMPLES);
            writer.println("neon_percentage " + ColorComponent.NEON_PERCENTAGE);

            writer.println();
            writer.println("[Window]");
            writer.println("image_size " + image_size);
            writer.println("window_maximized " + (getExtendedState() == JFrame.MAXIMIZED_BOTH));
            writer.println("window_size " + (int) getSize().getWidth() + " " + (int) getSize().getHeight());
            writer.println("window_location " + (int) getLocation().getX() + " " + (int) getLocation().getY());
            writer.println("window_toolbar " + options_menu.getToolbar().isSelected());
            writer.println("window_infobar " + options_menu.getInfobar().isSelected());
            writer.println("window_statusbar " + options_menu.getStatusbar().isSelected());
            writer.println("window_debugbar " + options_menu.getDebugbar().isSelected());
            writer.println("window_fullscreen " + options_menu.getFullscreen().isSelected());

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
            writer.println("julia_preview_filters " + FAST_JULIA_FILTERS);
            writer.println("julia_preview_image_size " + TaskDraw.FAST_JULIA_IMAGE_SIZE);

            writer.println();
            writer.println("[Color Cycling]");
            writer.println("color_cycling_speed " + color_cycling_speed);
            writer.println("cycle_colors " + cycle_colors);
            writer.println("cycle_lights " + cycle_lights);
            writer.println("cycle_gradient " + cycle_gradient);
            writer.println("color_cycling_adjusting_value " + color_cycling_adjusting_value);


            writer.println();
            writer.println("[Zoom]");
            writer.println("zoom_factor " + zoom_factor);

            writer.println();
            writer.println("[Random Palette]");
            writer.println("random_palette_algorithm " + CustomPaletteEditorFrame.random_palette_algorithm);
            writer.println("equal_hues " + CustomPaletteEditorFrame.equal_hues);
            writer.println("pastel " + CustomPaletteEditorFrame.pastel);
            writer.println("pastel_color " + ColorSpaceConverter.PASTEL_RED + " " + ColorSpaceConverter.PASTEL_GREEN + " " + ColorSpaceConverter.PASTEL_BLUE);
            writer.println("seed " + CustomPaletteEditorFrame.seed);

            writer.println();
            writer.println("[Quick Draw]");
            writer.println("tiles " + TaskDraw.TILE_SIZE);
            writer.println("quickdraw_delay " + TaskDraw.QUICK_DRAW_DELAY);
            writer.println("quickdraw_zoom_to_center " + QUICK_DRAW_ZOOM_TO_CURRENT_CENTER);
            writer.println("quickdraw_successive_refinement " + TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT);
            writer.println("use_quick_draw_on_greedy_successive_refinement " + TaskDraw.USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT);

            writer.println();
            writer.println("[Core]");
            writer.println("derivative_step " + Derivative.DZ.getRe());
            writer.println("aa_jitter_size " + Location.AA_JITTER_SIZE);
            writer.println("aa_number_of_jitter_kernels " + Location.NUMBER_OF_AA_JITTER_KERNELS);
            writer.println("whitepoint " + ColorSpaceConverter.whitePointId);

            writer.close();

            Files.deleteIfExists(Paths.get("preferences.ini"));
        } catch (Exception ex) {

        }
    }

    private void loadPreferences() {
        BufferedReader br = null;

        try {
            if(Files.exists(Paths.get("preferences.ini"))) {
                br = new BufferedReader(new FileReader("preferences.ini"));
            }
            else if(Files.exists(Paths.get("FZpreferences.ini"))) {
                br = new BufferedReader(new FileReader("FZpreferences.ini"));
            }
            else {
                return;
            }

            String str_line;

            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer;

                if(str_line.startsWith("save_settings_path") || str_line.startsWith("save_image_path")) {
                    tokenizer = new StringTokenizer(str_line, "\"");
                }
                else {
                    tokenizer = new StringTokenizer(str_line, " ");
                }

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();

                    if (token.equals("auto_repaint_image_delay") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 5000) {
                                ImageRepainter.REPAINT_SLEEP_TIME = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("mantexpcomplex_format") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                TaskDraw.MANTEXPCOMPLEX_FORMAT = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("guess_blocks_selection") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 7) {
                                TaskDraw.GUESS_BLOCKS_SELECTION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("seed") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());
                            CustomPaletteEditorFrame.seed = temp;
                            CustomPaletteEditorFrame.reSeed();
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_sqrt_max_iterations") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                BigNum.SQRT_MAX_ITERATIONS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("whitepoint") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 3) {
                                ColorSpaceConverter.whitePointId = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("stop_reference_calculation_after_detected_period") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.STOP_REFERENCE_CALCULATION_AFTER_DETECTED_PERIOD = true;
                        }
                    }
                    else if(token.equals("always_save_extra_pixel_data_on_aa") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.ALWAYS_SAVE_EXTRA_PIXEL_DATA_ON_AA = true;
                        }
                    }
                    else if(token.equals("use_fast_delta_location") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.USE_FAST_DELTA_LOCATION = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.USE_FAST_DELTA_LOCATION = true;
                        }
                    }
                    else if(token.equals("use_quick_draw_on_greedy_successive_refinement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.USE_QUICKDRAW_ON_GREEDY_SUCCESSIVE_REFINEMENT = true;
                        }
                    }
                    else if(token.equals("bla2_convert_to_double_when_possible") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE = false;
                        }
                        else if(token.equals("true")) {
                            LAReference.CONVERT_TO_DOUBLE_WHEN_POSSIBLE = true;
                        }
                    }
                    else if(token.equals("mariani_silver_use_dfs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            MarianiSilverDraw.RENDER_USING_DFS = false;
                        }
                        else if(token.equals("true")) {
                            MarianiSilverDraw.RENDER_USING_DFS = true;
                        }
                    }
                    else if (token.equals("bla2_double_threshold_limit") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAReference.doubleThresholdLimit = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("circular_draw_follows_zoom_to_cursor") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR = false;
                        }
                        else if(token.equals("true")) {
                            CIRCULAR_DRAW_FOLLOWS_ZOOM_TO_CURSOR = true;
                        }
                    }
                    else if(token.equals("circular_revert_alg") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.CIRCULAR_REVERT_ALG = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.CIRCULAR_REVERT_ALG = true;
                        }
                    }
                    else if (token.equals("circular_repeat_spacing") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskDraw.CIRCULAR_REPEAT_SPACING = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("circular_repeat_alg") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.CIRCULAR_REPEAT_ALG = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.CIRCULAR_REPEAT_ALG = true;
                        }
                    }
                    else if(token.equals("calculate_period_every_time_from_start") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.CALCULATE_PERIOD_EVERY_TIME_FROM_START = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.CALCULATE_PERIOD_EVERY_TIME_FROM_START = true;
                        }
                    }
                    else if(token.equals("use_threads_in_bignum_libs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.USE_THREADS_IN_BIGNUM_LIBS = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.USE_THREADS_IN_BIGNUM_LIBS = true;
                        }
                    }
                    else if(token.equals("quickdraw_successive_refinement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.QUICKDRAW_SUCCESSIVE_REFINEMENT = true;
                        }
                    }
                    else if(token.equals("always_check_for_precision_decrease") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            MyApfloat.alwaysCheckForDecrease = false;
                        }
                        else if(token.equals("true")) {
                            MyApfloat.alwaysCheckForDecrease = true;
                        }
                    }
                    else if(token.equals("3d_apply_average_to_triangle_colors") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                TaskDraw.D3_APPLY_AVERAGE_TO_TRIANGLE_COLORS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("aa_jitter_size") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                Location.AA_JITTER_SIZE = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }

                    else if (token.equals("neon_percentage") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 0.4) {
                                ColorComponent.NEON_PERCENTAGE = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("aa_number_of_jitter_kernels") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 200) {
                                Location.NUMBER_OF_AA_JITTER_KERNELS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("p3d_aa") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            P3D_AA = false;
                        }
                        else if(token.equals("true")) {
                            P3D_AA = true;
                        }
                    }
                    else if (token.equals("p3d_aa_samples") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 1 && temp <= 64) {
                                P3D_AA_SAMPLES = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_library") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 8) {
                                TaskDraw.BIGNUM_LIBRARY = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("use_smoothing_for_processing_algs") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.USE_SMOOTHING_FOR_PROCESSING_ALGS = true;
                        }
                    }
                    else if(token.equals("draw_image_preview") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.DRAW_IMAGE_PREVIEW = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.DRAW_IMAGE_PREVIEW = true;
                        }
                    }
                    else if (token.equals("circular_compare_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 15) {
                                TaskDraw.CIRCULAR_COMPARE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("circular_n") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp != 0) {
                                TaskDraw.CIRCULAR_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("greedy_drawing_algorithm_use_iter_data") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.GREEDY_ALGORITHM_CHECK_ITER_DATA = true;
                        }
                    }
                    else if (token.equals("high_precision_lib") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 6) {
                                TaskDraw.HIGH_PRECISION_LIB = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("quickdraw_delay") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0) {
                                TaskDraw.QUICK_DRAW_DELAY = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_detection_method") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                LAInfo.DETECTION_METHOD = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_period_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.Stage0PeriodDetectionThreshold = temp;
                                LAInfoDeep.Stage0PeriodDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_stage0_period_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.Stage0PeriodDetectionThreshold2 = temp;
                                LAInfoDeep.Stage0PeriodDetectionThreshold2 = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }

                    else if (token.equals("bla2_period_detection_threshold") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.PeriodDetectionThreshold = temp;
                                LAInfoDeep.PeriodDetectionThreshold = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_period_detection_threshold2") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0 && temp <= 10) {
                                LAInfo.PeriodDetectionThreshold2 = temp;
                                LAInfoDeep.PeriodDetectionThreshold2 = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_la_threshold_scale") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAInfo.LAThresholdScale = temp;
                                LAInfoDeep.LAThresholdScale = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla2_la_threshold_c_scale") && tokenizer.countTokens() == 1) {

                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                LAInfo.LAThresholdCScale = temp;
                                LAInfoDeep.LAThresholdCScale = new MantExp(temp);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("julia_preview_image_size") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 100 && temp <= 1000) {
                                TaskDraw.FAST_JULIA_IMAGE_SIZE = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_n") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 48) {
                                TaskDraw.NANOMB1_N = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if(token.equals("check_bailout_during_deep_not_full_floatexp_mode") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.CHECK_BAILOUT_DURING_DEEP_NOT_FULL_FLOATEXP_MODE = true;
                        }
                    }
                    else if(token.equals("use_custom_floatexp_requirement") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.USE_CUSTOM_FLOATEXP_REQUIREMENT = true;
                        }
                    }
                    else if (token.equals("period_detection_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                TaskDraw.PERIOD_DETECTION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("perturbation_pixel_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1) {
                                TaskDraw.PERTUBATION_PIXEL_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("nanomb1_m") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 48) {
                                TaskDraw.NANOMB1_M = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("thread_dim") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(thread_grouping == 0) {
                                if (temp >= 1 && temp <= 100) {
                                    n = temp;
                                }
                            }
                            else if (thread_grouping == 1 || thread_grouping == 2){
                                if (temp >= 1 && temp <= 10000) {
                                    n = temp;
                                }
                            }
                            else {
                                if (temp >= 1 && temp <= 10000) {
                                    ArrayList<Integer> factors = CommonFunctions.getAllFactors(temp);

                                    int index = factors.size() / 2 - 1;

                                    m = factors.get(index);
                                    this.n = factors.get(index + 1);
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("thread_dim2") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if(thread_grouping == 5) {
                                if (temp >= 1 && temp <= 100) {
                                    m = temp;
                                }
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.startsWith("save_settings_path") && tokenizer.countTokens() == 1) {

                        SaveSettingsPath = tokenizer.nextToken();

                        try {
                            Path path = Paths.get(SaveSettingsPath);
                            SaveSettingsPath = Files.notExists(path) || !Files.isDirectory(path) ? "" : SaveSettingsPath;
                        }
                        catch (Exception ex) {
                            SaveSettingsPath = "";
                        }

                    }
                    else if (token.startsWith("save_image_path") && tokenizer.countTokens() == 1) {

                        SaveImagesPath = tokenizer.nextToken();

                        try {
                            Path path = Paths.get(SaveImagesPath);
                            SaveImagesPath = Files.notExists(path) || !Files.isDirectory(path) ? "" : SaveImagesPath;
                        }
                        catch (Exception ex) {
                            SaveImagesPath = "";
                        }

                    }
                    else if (token.equals("greedy_drawing_algorithm") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.GREEDY_ALGORITHM = false;
                        } else if (token.equals("true")) {
                            TaskDraw.GREEDY_ALGORITHM = true;
                        }
                    }
                    else if (token.equals("quickdraw_zoom_to_center") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            QUICK_DRAW_ZOOM_TO_CURRENT_CENTER = false;
                        } else if (token.equals("true")) {
                            QUICK_DRAW_ZOOM_TO_CURRENT_CENTER = true;
                        }
                    }
                    else if(token.equals("gather_perturbation_statistics") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.GATHER_PERTURBATION_STATISTICS = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.GATHER_PERTURBATION_STATISTICS = true;
                        }
                    }
//                    else if(token.equals("high_precision") && tokenizer.countTokens() == 1) {
//
//                        token = tokenizer.nextToken();
//
//                        if(token.equals("false")) {
//                            ThreadDraw.HIGH_PRECISION_CALCULATION = false;
//                        }
//                        else if(token.equals("true")) {
//                            ThreadDraw.HIGH_PRECISION_CALCULATION = true;
//                        }
//                    }
                    else if(token.equals("gather_tiny_ref_indexes") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.GATHER_TINY_REF_INDEXES = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.GATHER_TINY_REF_INDEXES = true;
                        }
                    }
                    else if(token.equals("automatic_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            MyApfloat.setAutomaticPrecision = false;
                        }
                        else if(token.equals("true")) {
                            MyApfloat.setAutomaticPrecision = true;
                        }
                    }
                    else if (token.equals("greedy_drawing_algorithm_id") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp == BOUNDARY_TRACING || temp == DIVIDE_AND_CONQUER || temp == SUCCESSIVE_REFINEMENT || temp == CIRCULAR_SUCCESSIVE_REFINEMENT) {
                                TaskDraw.GREEDY_ALGORITHM_SELECTION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("brute_force_alg") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 2) {
                                TaskDraw.BRUTE_FORCE_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("exponent_diff_ignored") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());

                            if (temp >= 0) {
                                MantExp.EXPONENT_DIFF_IGNORED = temp;
                                MantExp.MINUS_EXPONENT_DIFF_IGNORED = -MantExp.EXPONENT_DIFF_IGNORED;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("skipped_pixels_coloring") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                TaskDraw.SKIPPED_PIXELS_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("skipped_pixels_user_color") && tokenizer.countTokens() == 3) {

                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                TaskDraw.SKIPPED_PIXELS_COLOR = new Color(red, green, blue).getRGB();
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("periodicity_checking") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getPeriodicityChecking().isSelected()) {
                            options_menu.getPeriodicityChecking().doClick();
                        } else if (token.equals("true") && !options_menu.getPeriodicityChecking().isSelected()) {
                            options_menu.getPeriodicityChecking().doClick();
                        }

                        if(token.equals("false")) {
                            PERIODICITY_CHECKING = false;
                        }
                        else {
                            PERIODICITY_CHECKING = true;
                        }
                    }
                    else if (token.equals("auto_repaint_image") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getAutoRepaintImage().isSelected()) {
                            options_menu.getAutoRepaintImage().doClick();
                        } else if (token.equals("true") && !options_menu.getAutoRepaintImage().isSelected()) {
                            options_menu.getAutoRepaintImage().doClick();
                        }

                        if(token.equals("false")) {
                            AUTO_REPAINT_IMAGE = false;
                        }
                        else {
                            AUTO_REPAINT_IMAGE = true;
                        }
                    }
                    else if (token.equals("zoom_on_cursor") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getZoomOnCursor().isSelected()) {
                            options_menu.getZoomOnCursor().doClick();
                        } else if (token.equals("true") && !options_menu.getZoomOnCursor().isSelected()) {
                            options_menu.getZoomOnCursor().doClick();
                        }

                        if(token.equals("false")) {
                            ZOOM_ON_THE_CURSOR = false;
                        }
                        else {
                            ZOOM_ON_THE_CURSOR = true;
                        }
                    }
                    else if (token.equals("perturbation_theory") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                           TaskDraw.PERTURBATION_THEORY = false;
                        } else if (token.equals("true")) {
                            TaskDraw.PERTURBATION_THEORY = true;
                        }
                    }
                    else if (token.equals("detect_period") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.DETECT_PERIOD = false;
                        } else if (token.equals("true")) {
                            TaskDraw.DETECT_PERIOD = true;
                        }
                    }
                    else if (token.equals("use_threads_for_sa") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.USE_THREADS_FOR_SA = false;
                        } else if (token.equals("true")) {
                            TaskDraw.USE_THREADS_FOR_SA = true;
                        }
                    }
                    else if (token.equals("use_threads_for_bla") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.USE_THREADS_FOR_BLA = false;
                        } else if (token.equals("true")) {
                            TaskDraw.USE_THREADS_FOR_BLA = true;
                        }
                    }
                    else if (token.equals("bla_starting_level") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp <= 32) {
                                TaskDraw.BLA_STARTING_LEVEL = temp;
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else if (token.equals("automatic_bignum_precision") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.BIGNUM_AUTOMATIC_PRECISION = false;
                        } else if (token.equals("true")) {
                            TaskDraw.BIGNUM_AUTOMATIC_PRECISION = true;
                        }
                    }
                    else if (token.equals("precision") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());

                            if (temp > 0) {
                                MyApfloat.setPrecision(temp, s);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bla_precision_bits") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0 && temp < 64) {
                                TaskDraw.BLA_BITS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_factor") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskDraw.BIGNUM_PRECISION_FACTOR = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("bignum_precision_bits") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp > 0) {
                                TaskDraw.BIGNUM_PRECISION = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("approximation_algorithm") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                TaskDraw.APPROXIMATION_ALGORITHM = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_deep_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = false;
                        } else if (token.equals("true")) {
                            TaskDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM = true;
                        }
                    }
                    else if (token.equals("use_full_floatexp_for_all_zoom") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            TaskDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = false;
                        } else if (token.equals("true")) {
                            TaskDraw.USE_FULL_FLOATEXP_FOR_ALL_ZOOM = true;
                        }
                    }
                    else if (token.equals("series_approximation_oom_diff") && tokenizer.countTokens() == 1) {

                        try {
                            long temp = Long.parseLong(tokenizer.nextToken());
                            TaskDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE = temp;

                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_terms") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=2  && temp <= 257) {
                                TaskDraw.SERIES_APPROXIMATION_TERMS = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("series_approximation_max_skip") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >=0) {
                                TaskDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("image_size") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 209 && temp <= 6000) {
                                image_size = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("window_maximized") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("true")) {
                            setExtendedState(JFrame.MAXIMIZED_BOTH);
                        } else if (token.equals("false")) {
                            setExtendedState(JFrame.NORMAL);
                        }
                    } else if (token.equals("window_fullscreen") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getFullscreen().isSelected()) {
                            options_menu.getFullscreen().doClick();
                        } else if (token.equals("true") && !options_menu.getFullscreen().isSelected()) {
                            options_menu.getFullscreen().doClick();
                        }
                    } else if (token.equals("window_size") && tokenizer.countTokens() == 2) {
                        try {
                            int width = Integer.parseInt(tokenizer.nextToken());
                            int height = Integer.parseInt(tokenizer.nextToken());

                            if (width > 0 && width < 6000 && height > 0 && height < 6000) {
                                setSize(width, height);
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("window_location") && tokenizer.countTokens() == 2) {
                        try {
                            int x = Integer.parseInt(tokenizer.nextToken());
                            int y = Integer.parseInt(tokenizer.nextToken());

                            setLocation(x, y);
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("window_toolbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getToolbar().isSelected()) {
                            options_menu.getToolbar().doClick();
                        } else if (token.equals("true") && !options_menu.getToolbar().isSelected()) {
                            options_menu.getToolbar().doClick();
                        }
                    } else if (token.equals("window_infobar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getInfobar().isSelected()) {
                            options_menu.getInfobar().doClick();
                        } else if (token.equals("true") && !options_menu.getInfobar().isSelected()) {
                            options_menu.getInfobar().doClick();
                        }
                    } else if (token.equals("window_statusbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getStatusbar().isSelected()) {
                            options_menu.getStatusbar().doClick();
                        } else if (token.equals("true") && !options_menu.getStatusbar().isSelected()) {
                            options_menu.getStatusbar().doClick();
                        }
                    }
                    else if (token.equals("window_debugbar") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getDebugbar().isSelected()) {
                            options_menu.getDebugbar().doClick();
                        } else if (token.equals("true") && !options_menu.getDebugbar().isSelected()) {
                            options_menu.getDebugbar().doClick();
                        }
                    }
                    else if (token.equals("orbit_style") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("0")) {
                            options_menu.getLine().doClick();
                        } else if (token.equals("1")) {
                            options_menu.getDot().doClick();
                        }
                    } else if (token.equals("orbit_show_root") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getShowOrbitConvergingPoint().isSelected()) {
                            options_menu.getShowOrbitConvergingPoint().doClick();
                        } else if (token.equals("true") && !options_menu.getShowOrbitConvergingPoint().isSelected()) {
                            options_menu.getShowOrbitConvergingPoint().doClick();
                        }

                        if (token.equals("false")) {
                            show_orbit_converging_point = false;
                        } else {
                            show_orbit_converging_point = true;
                        }
                    } else if (token.equals("orbit_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                orbit_color = new Color(red, green, blue);
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("boundaries_type") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp == 0 || temp == 1 || temp == 2) {
                                boundaries_type = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("boundaries_number") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 1 && temp <= 64) {
                                boundaries_num = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("boundaries_spacing_method") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp == 0 || temp == 1) {
                                boundaries_spacing_method = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("boundaries_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                boundaries_color = new Color(red, green, blue);
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("grid_tiles") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 64) {
                                grid_tiles = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("grid_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                grid_color = new Color(red, green, blue);
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("pastel_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                ColorSpaceConverter.PASTEL_RED = red;
                                ColorSpaceConverter.PASTEL_GREEN = green;
                                ColorSpaceConverter.PASTEL_BLUE = blue;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("zoom_window_style") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("0")) {
                            options_menu.getZoomWindowDashedLine().doClick();
                        } else if (token.equals("1")) {
                            options_menu.getZoomWindowLine().doClick();
                        }
                    } else if (token.equals("zoom_window_color") && tokenizer.countTokens() == 3) {
                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                zoom_window_color = new Color(red, green, blue);
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("julia_preview_filters") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false") && options_menu.getFastJuliaFiltersOptions().isSelected()) {
                            options_menu.getFastJuliaFiltersOptions().doClick();
                        } else if (token.equals("true") && !options_menu.getFastJuliaFiltersOptions().isSelected()) {
                            options_menu.getFastJuliaFiltersOptions().doClick();
                        }

                        if (token.equals("false")) {
                            FAST_JULIA_FILTERS = false;
                        } else {
                            FAST_JULIA_FILTERS = true;
                        }
                    } else if (token.equals("zoom_factor") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp >= 1.05 && temp <= 32) {
                                zoom_factor = temp;
                            }

                        } catch (Exception ex) {
                        }
                    } else if (token.equals("color_cycling_speed") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 1000) {
                                color_cycling_speed = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("random_palette_algorithm") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 14) {
                                CustomPaletteEditorFrame.random_palette_algorithm = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("equal_hues") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("true")) {
                            CustomPaletteEditorFrame.equal_hues = true;
                        } else if (token.equals("false")) {
                            CustomPaletteEditorFrame.equal_hues = false;
                        }
                    }
                    else if (token.equals("pastel") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("true")) {
                            CustomPaletteEditorFrame.pastel = true;
                        } else if (token.equals("false")) {
                            CustomPaletteEditorFrame.pastel = false;
                        }
                    }
                    else if (token.equals("cycle_colors") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            cycle_colors = false;
                        } else if (token.equals("true")) {
                            cycle_colors = true;
                        }
                    } else if (token.equals("cycle_lights") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            cycle_lights = false;
                        } else if (token.equals("true")) {
                            cycle_lights = true;
                        }
                    } else if (token.equals("cycle_gradient") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            cycle_gradient = false;
                        } else if (token.equals("true")) {
                            cycle_gradient = true;
                        }
                    }
                    else if (token.equals("color_cycling_adjusting_value") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 1 && color_cycling_adjusting_value <= 50) {
                                color_cycling_adjusting_value = temp;
                            }

                        } catch (Exception ex) {
                        }
                    }
                    else if (token.equals("tiles") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= Constants.MIN_QUICK_DRAW_TILES && temp <= Constants.MAX_QUICK_DRAW_TILES) {
                                TaskDraw.TILE_SIZE = temp;
                            }
                        } catch (Exception ex) {
                        }

                    }
                    else if (token.equals("derivative_step") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 0) {
                                Derivative.DZ = new Complex(temp, temp);
                                Derivative.calculateConstants();
                            }
                        } catch (Exception ex) {
                        }

                    }else {
                        continue;
                    }
                }

            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }

        TaskDraw.thread_calculation_executor.shutdown();
        TaskDraw.thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(getNumberOfThreads());

        MyApfloat.setPrecision(MyApfloat.precision, s);

        Location.setJitter(2);

        ColorSpaceConverter.init();

        if(TaskDraw.PERTURBATION_THEORY || TaskDraw.HIGH_PRECISION_CALCULATION) {
            PERIODICITY_CHECKING = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }
    }


    private void preloadPreferences() {
        BufferedReader br = null;

        try {
            if(Files.exists(Paths.get("preferences.ini"))) {
                br = new BufferedReader(new FileReader("preferences.ini"));
            }
            else if(Files.exists(Paths.get("FZpreferences.ini"))) {
                br = new BufferedReader(new FileReader("FZpreferences.ini"));
            }
            else {
                return;
            }

            String str_line;

            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();
                    if(token.equals("load_mpir") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.LOAD_MPIR = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.LOAD_MPIR = true;
                        }
                    }
                    else if(token.equals("load_mpfr") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.LOAD_MPFR = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.LOAD_MPFR = true;
                        }
                    }
                    else if(token.equals("load_drawing_algorithm_from_saves") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(token.equals("false")) {
                            TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES = false;
                        }
                        else if(token.equals("true")) {
                            TaskDraw.LOAD_DRAWING_ALGORITHM_FROM_SAVES = true;
                        }
                    }
                    else if(token.equals("mpir_lib") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if(Arrays.asList(TaskDraw.mpirWinLibs).contains(token)) {
                            TaskDraw.MPIR_LIB = token;
                        }
                    }
                    else if (token.equals("thread_grouping") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 5) {
                                thread_grouping = temp;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    else {
                        continue;
                    }
                }

            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }

    public void filtersOptionsChanged(int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean[] activeFilters, boolean samplesChanged, boolean jitterChanged) {

        boolean oldAAValue = s.fs.filters[ANTIALIASING];

        if (filters_options_vals != null) {
            for (int k = 0; k < filters_options_vals.length; k++) {
                s.fs.filters_options_vals[k] = filters_options_vals[k];
                s.fs.filters_options_extra_vals[0][k] = filters_options_extra_vals[0][k];
                s.fs.filters_options_extra_vals[1][k] = filters_options_extra_vals[1][k];
                s.fs.filters_colors[k] = filters_colors[k];
                s.fs.filters_extra_colors[0][k] = filters_extra_colors[0][k];
                s.fs.filters_extra_colors[1][k] = filters_extra_colors[1][k];
                s.fs.filters[k] = activeFilters[k];
                filters_opt[k].setSelected(s.fs.filters[k]);
            }

            for (int k = 0; k < filters_order.length; k++) {
                s.fs.filters_order[k] = filters_order[k];
            }
        } else {
            s.fs.defaultFilters(false);
        }

        if(s.d3s.d3) {
            TaskDraw.setExtraDataArrays(s.needsExtraData(), s.d3s.detail);
        }
        else {
            TaskDraw.setExtraDataArrays(s.needsExtraData(), image_size);
        }

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(s.d3s.d3, false);

        if ((oldAAValue && TaskDraw.GREEDY_ALGORITHM && TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER) || s.fs.filters[ANTIALIASING] || s.ds.domain_coloring || s.pps.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {

            if(!TaskDraw.hasExtraData(image_size) || s.d3s.d3 || samplesChanged || jitterChanged) {
                if(s.julia_map) {
                    createTasksJuliaMap();
                }
                else {
                    createTasks(false, false, false, false);
                }
            }
            else {
                createTasksPalettePostProcessAndFilterWithAAData(TaskDraw.POST_PROCESSING_WITH_AA_AND_FILTER);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        } else {

            if (s.d3s.d3) {
                createTasksPaletteAndFilter3DModel();
            } else {
                createTasksPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }

    }

    public void customPaletteChanged(int[][] temp_custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int temp_color_cycling_location, double scale_factor_palette_val, int processing_alg, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            for (int k = 0; k < s.ps.custom_palette.length; k++) {
                for (int j = 0; j < s.ps.custom_palette[0].length; j++) {
                    s.ps.custom_palette[k][j] = temp_custom_palette[k][j];
                }
            }

            s.ps.color_interpolation = color_interpolation;
            s.ps.color_space = color_space;
            s.ps.reversed_palette = reversed_palette;
            s.temp_color_cycling_location = s.ps.color_cycling_location = temp_color_cycling_location;
            s.ps.scale_factor_palette_val = scale_factor_palette_val;
            s.ps.processing_alg = processing_alg;
        } else {
            for (int k = 0; k < s.ps2.custom_palette.length; k++) {
                for (int j = 0; j < s.ps2.custom_palette[0].length; j++) {
                    s.ps2.custom_palette[k][j] = temp_custom_palette[k][j];
                }
            }

            s.ps2.color_interpolation = color_interpolation;
            s.ps2.color_space = color_space;
            s.ps2.reversed_palette = reversed_palette;
            s.temp_color_cycling_location_second_palette = s.ps2.color_cycling_location = temp_color_cycling_location;
            s.ps2.scale_factor_palette_val = scale_factor_palette_val;
            s.ps2.processing_alg = processing_alg;
        }

    }

    public void setUserFormulaOptions(boolean mode) {
        if (s.fns.julia && !s.userFormulaHasC) {
            s.fns.julia = false;
            toolbar.getJuliaButton().setSelected(false);
            tools_menu.getJulia().setSelected(false);
            first_seed = true;
            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        }

        if (s.fns.init_val && !s.userFormulaHasC) {
            s.fns.init_val = false;
            options_menu.getInitialValue().setIcon(null);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.perturbation && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        }

        if (s.fns.perturbation && !s.userFormulaHasC) {
            s.fns.perturbation = false;
            options_menu.getPerturbation().setIcon(null);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.init_val && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        }

        if(s.julia_map && !s.userFormulaHasC) {
            s.julia_map = false;
            tools_menu.getJuliaMap().setIcon(getIcon("julia_map.png"));
            toolbar.getJuliaMapButton().setSelected(false);
            setOptions(false);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }

        }

        if (!s.userFormulaHasC) {
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
            options_menu.getPlaneInfluenceMenu().setEnabled(false);
        }

        if (mode && (s.fns.bail_technique == 1 || s.fns.function == USER_FORMULA_NOVA)) {
            options_menu.getBailout().setEnabled(false);
            options_menu.getBailoutConditionMenu().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
            options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
        }
        else {
            options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
        }
    }

    public Object[] createUserFormulaLabels(String supported_vars) {

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

        JLabel multi_arg = new JLabel("Multiple Argument User Functions: f(z1, ... z10) or f(z1, ... z20)");
        multi_arg.setFont(new Font("Arial", Font.BOLD, 11));

        JButton info_user = new MyButton("Help");
        info_user.setToolTipText("Shows the details of the user formulas.");
        info_user.setFocusable(false);
        info_user.setIcon(getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> showUserFormulaHelp());

        JButton code_editor = new MyButton("Edit User Code");
        code_editor.setToolTipText("<html>Opens the java code, containing the user defined functions,<br>with a text editor.</html>");
        code_editor.setFocusable(false);
        code_editor.setIcon(getIcon("code_editor2.png"));
        code_editor.setPreferredSize(new Dimension(160, 23));

        code_editor.addActionListener(e -> codeEditor());

        JButton compile_code = new MyButton("Compile User Code");
        compile_code.setToolTipText("Compiles the java code, containing the user defined functions.");
        compile_code.setFocusable(false);
        compile_code.setIcon(getIcon("compile2.png"));
        compile_code.setPreferredSize(new Dimension(180, 23));

        compile_code.addActionListener(e -> compileCode(true));

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);
        info_panel.add(code_editor);
        info_panel.add(compile_code);

        return new Object[] {info_panel,
            variables,
            new JLabel(supported_vars + ", rand"),
            operations,
            new JLabel("+ - * / % ^ ( ) ,"),
            constants,
            new JLabel("pi, e, phi, c10, alpha, delta, maxnde"),
            complex_numbers,
            new JLabel("a + bi"),
            trig,
            new JLabel("sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,"),
            new JLabel("acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,"),
            new JLabel("hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc"),
            other,
            new JLabel("exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec, flip, round,"),
            new JLabel("ceil, floor, trunc, deta, snorm, fib, hypot, f1, ... f60"),
            two_arg,
            new JLabel("logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, dist, sdist, root, f',"),
            new JLabel("f'', f''', g1, ... g60"),
            multi_arg,
            new JLabel("m1, ... m60, k1, ... k60")
        };

    }

    public void compileCode(boolean opt) {

        resetOrbit();
        common.compileCode(opt);

    }

    public void checkForUpdate(boolean opt) {

        resetOrbit();
        common.checkForUpdate(opt);

    }

    public void codeEditor() {

        resetOrbit();
        try {
            File UserDefinedFunctionsFile = new File(Parser.CURRENT_USER_CODE_FILE);

            if (UserDefinedFunctionsFile.exists()) {
                Desktop.getDesktop().open(UserDefinedFunctionsFile);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Could not open UserDefinedFunctions.java for editing.\nMake sure that the file exists.\nIf not please restart the application.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void libraryCode() {

        resetOrbit();
        try {
            InputStream src = getClass().getResource("/fractalzoomer/parser/code/Library.javacode").openStream();

            File TempFile = File.createTempFile("Library", ".java");
            FileOutputStream out = new FileOutputStream(TempFile);
            byte[] temp = new byte[32768];
            int rc;

            while ((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();
            TempFile.deleteOnExit();
            Desktop.getDesktop().open(TempFile);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Unable to access the library code file.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    /*public void cancelOperation() {

        resetOrbit();

        synchronized (this) {
            for(Future<?> future : futures) {
                future.cancel(true);
            }
        }

        while (!tasksCompleted()) {}

        TaskDraw.thread_calculation_executor.shutdownNow();
        TaskDraw.thread_calculation_executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(getNumberOfThreads());

        if(s.d3s.d3) {
            progress.setMaximum(s.d3s.detail * s.d3s.detail + 1);
        }
        else {
            progress.setMaximum(image_size * image_size + 1);
        }

        progress.setForeground(MainWindow.progress_color);
        progress.setString(null);

        Fractal.clearReferences(true);
        s.max_iterations = s.max_iterations > 500 ? 500 : s.max_iterations;
        defaultFractalSettings(false);

    }*/

    public void createCompleteImage(int delay, boolean d3, boolean preview, boolean zoomToCursor) {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new CompleteImageTask(ptr, d3, preview, zoomToCursor), delay);
        }

    }

    public void taskCompleteImage(boolean d3, boolean preview, boolean zoomToCursor) {

        if (!tasksCompleted()) {
            timer.cancel();
            timer = null;

            createCompleteImage(1, d3, preview, zoomToCursor);
            return;
        }

        timer.cancel();
        timer = null;

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(d3, !s.fs.filters[ANTIALIASING]);

        if(preview) {
            main_panel.repaint();
        }

        if(d3) {
            createTasksPaletteAndFilter3DModel();
        }
        else {
            createTasks(false, false, zoomToCursor, true);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    public void setQuickDrawTiles() {

        resetOrbit();
        new QuickDrawDialog(ptr);

    }

    public void boundaryTracingOptionsChanged(boolean greedy_algorithm, int algorithm, int brute_force_alg) {

        TaskDraw.GREEDY_ALGORITHM = greedy_algorithm;
        TaskDraw.GREEDY_ALGORITHM_SELECTION = algorithm;
        TaskDraw.BRUTE_FORCE_ALG = brute_force_alg;

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(s.d3s.d3, false);

        createTasks(false, false, false, false);

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setPointPost() {

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setPoint() {

        resetOrbit();
        new UserPointDialog(ptr, s);

    }

    public void setVariables() {

        resetOrbit();
        new VariablesDialog(ptr, s);

    }

    public void setPlaneVizualization() {

        resetOrbit();
        new PlaneVisualizationFrame(ptr, s, zoom_factor);

    }

    public void displayAboutInfo() {

        resetOrbit();
        if (!color_cycling) {
            main_panel.repaint();
        }

        String temp2 = "" + VERSION;
        String versionStr = "";

        int i;
        for (i = 0; i < temp2.length() - 1; i++) {
            versionStr += temp2.charAt(i) + ".";
        }
        versionStr += temp2.charAt(i);

        if(Constants.beta) {
            versionStr += " beta";
        }

        String javaVersion = System.getProperty("java.version");

        JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br><font size='4' face='arial'>Version: <b>" + versionStr + "</b><br><br>" +
                "Java Version: <b>" + javaVersion + "</b><br><br>" +
                "Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE, MainWindow.getIcon("mandel2.png"));

    }

    public void setGreedyAlgorithms() {

        resetOrbit();
        new DrawingAlgorithmsFrame(ptr, TaskDraw.GREEDY_ALGORITHM, TaskDraw.GREEDY_ALGORITHM_SELECTION, TaskDraw.BRUTE_FORCE_ALG, TaskDraw.GUESS_BLOCKS_SELECTION);

    }

    public void setPerturbationTheory() {

        resetOrbit();
        new PerturbationTheoryDialog(ptr, s);

    }

    public void setHighPrecision() {
        resetOrbit();
        new HighPrecisionDialog(ptr, s);
    }

    public void setZoomOnCursor() {
        resetOrbit();
        ZOOM_ON_THE_CURSOR = options_menu.getZoomOnCursor().isSelected();
    }

    public void setHighPrecisionPost() {
        if(TaskDraw.HIGH_PRECISION_CALCULATION) {
            PERIODICITY_CHECKING = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }
        else {
            if (s.fns.in_coloring_algorithm == MAX_ITERATIONS) {
                if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.pps.ots.useTraps && !s.fns.tcs.trueColorIn && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                        && !(s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) && !TaskDraw.PERTURBATION_THEORY && !TaskDraw.HIGH_PRECISION_CALCULATION) {
                    options_menu.getPeriodicityChecking().setEnabled(true);
                }
            }
        }
        redraw();
    }

    public void setPerturbationTheoryPost() {

        if(TaskDraw.PERTURBATION_THEORY) {
            PERIODICITY_CHECKING = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }
        else {
            if (s.fns.in_coloring_algorithm == MAX_ITERATIONS) {
                if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.pps.ots.useTraps && !s.fns.tcs.trueColorIn && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                        && !(s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) && !TaskDraw.PERTURBATION_THEORY && !TaskDraw.HIGH_PRECISION_CALCULATION) {
                    options_menu.getPeriodicityChecking().setEnabled(true);
                }
            }
        }
        redraw();

    }

    public void openCustomPaletteEditor(int temp, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            resetOrbit();
            options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true); // reselect the old palette
            customPaletteEditor(temp, outcoloring_mode);
        } else {
            resetOrbit();
            options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true); // reselect the old palette
            customPaletteEditor(temp, outcoloring_mode);
        }
    }

    private void resetImage() {

        BufferedImage temp = image;
        image = last_used;
        last_used = temp;
        ArraysFillColor(image, EMPTY_COLOR);

    }

    private void resetImageAndCopy(boolean d3, boolean afterQuickDraw) {

        BufferedImage temp = image;
        image = last_used;
        last_used = temp;

        if(afterQuickDraw && TaskDraw.GREEDY_ALGORITHM && TaskDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING) {
            BoundaryTracingDraw.examined = new boolean[image_size * image_size];
        }

        if(!d3 && (!TaskDraw.GREEDY_ALGORITHM || (TaskDraw.GREEDY_ALGORITHM_SELECTION == DIVIDE_AND_CONQUER || TaskDraw.GREEDY_ALGORITHM_SELECTION == BOUNDARY_TRACING || TaskDraw.GREEDY_ALGORITHM_SELECTION == SUCCESSIVE_REFINEMENT || TaskDraw.GREEDY_ALGORITHM_SELECTION == CIRCULAR_SUCCESSIVE_REFINEMENT))) {
            int[] dataDest = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
            int[] dataSrc = ((DataBufferInt) last_used.getRaster().getDataBuffer()).getData();
            int total = dataDest.length;

            for(int p = 0; p < total; p++) {
                if(afterQuickDraw) {
                    if((dataSrc[p] >>> 24) == QUICKDRAW_CALCULATED_ALPHA) {
                        dataDest[p] = (dataSrc[p] & 0x00FFFFFF) | NORMAL_ALPHA_OFFSETED;
                    }
                    else {
                        dataDest[p] = (dataSrc[p] & 0x00FFFFFF) | EMPTY_ALPHA_OFFSETED;
                    }
                }
                else {
                    dataDest[p] = (dataSrc[p] & 0x00FFFFFF) | EMPTY_ALPHA_OFFSETED;
                }
            }
        }
        else if (d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }
        else {
            ArraysFillColor(image, EMPTY_COLOR);
        }
    }

    public void drawOrbit(Graphics2D g) {
        if (orbit_style == 0) {
            pixels_orbit.drawLine(g);
        } else {
            pixels_orbit.drawDot(g);
        }
    }

    public boolean getOrbit() {

        return orbit && pixels_orbit != null;

    }

    private void resetOrbit() {
        if (orbit && pixels_orbit != null && orbit_future != null) {
            try {
                orbit_future.get();

            } catch (InterruptedException ex) {

            }
            catch (ExecutionException ex) {

            }

            pixels_orbit = null;

            main_panel.repaint();
        }
    }

    private void clearThreads() {

        synchronized (this) {
            if (tasks == null) {
                return;
            }

            for (int i = 0; i < tasks.length; i++) {
                for (int j = 0; j < tasks[i].length; j++) {
                    tasks[i][j] = null;
                }
            }
        }

    }

    public void setColorBlending(int val) {

        resetOrbit();

        s.color_blending.color_blending = val;

        updateColors(false);
    }

    public void setColorBlendingRevertColors(boolean val) {
        resetOrbit();

        s.color_blending.blending_reversed_colors = val;

        updateColors(false);
    }

    public void setColorTransfer(int val, boolean outcoloring) {
        resetOrbit();

        if (outcoloring) {
            s.ps.transfer_function = val;
        } else {
            s.ps2.transfer_function = val;
        }

        updateColors(false);

    }

    public CommonFunctions getCommonFunctions() {

        return common;

    }

    public void setGradient() {

        resetOrbit();
        new GradientFrame(ptr, s.gs);

    }

    public void setContourFactor() {

        resetOrbit();
        new ContourFactorDialog(ptr, s);

    }

    public void setHueGeneratedPaletteParams() {

        resetOrbit();
        new HueGeneratedPalettes(ptr, s);

    }

    public void gradientChanged(Color colorA, Color colorB, int interpolation, int color_space, boolean reverse, int gradient_offset) {

        resetOrbit();

        s.gs.colorA = colorA;
        s.gs.colorB = colorB;
        s.gs.gradient_interpolation = interpolation;
        s.gs.gradient_color_space = color_space;
        s.gs.gradient_reversed = reverse;
        s.gs.gradient_offset = gradient_offset;

        TaskDraw.gradient = CustomPalette.createGradient(colorA.getRGB(), colorB.getRGB(), Constants.GRADIENT_LENGTH, interpolation, color_space, reverse, 0);

        updateGradientPreview(s.gs.gradient_offset);

        updateColors(false);
    }

    public void setOrbitTraps() {

        resetOrbit();
        new OrbitTrapsFrame(ptr, s.pps.ots, s);

    }

    public void setOrbitTrapSettings() {

        setOptions(false);

        options_menu.getProcessing().updateIcons(s);

        tools_menu.getColorCycling().setEnabled(false);
        toolbar.getColorCyclingButton().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();
    }

    private void setPalettePreviewsVisible() {
        if (!s.ds.domain_coloring) {
            if (s.usePaletteForInColoring) {
                if(!s.gps.useGeneratedPaletteInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                }
                else {
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
                infobar.getMaxIterationsColorPreview().setVisible(false);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            } else {
                infobar.getMaxIterationsColorPreview().setVisible(true);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                infobar.getInColoringPalettePreview().setVisible(false);
                infobar.getInColoringPalettePreviewLabel().setVisible(false);
            }
        }
        infobar.getOutColoringPalettePreview().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
        infobar.getOutColoringPalettePreviewLabel().setVisible(!s.gps.useGeneratedPaletteOutColoring || (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1));
    }

    public void setDirectColor() {

        if (!options_menu.getDirectColor().isSelected()) {
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if(!(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                setPalettePreviewsVisible();
            }

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
        } else {
            TaskDraw.USE_DIRECT_COLOR = s.useDirectColor = true;
            s.pps.ots.useTraps = false;
            s.fns.tcs.trueColorOut = false;
            s.fns.tcs.trueColorIn = false;

            options_menu.getProcessing().updateIcons(s);
            options_menu.getColorsMenu().updateIcons(s);

            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);

            tools_menu.getDomainColoring().setEnabled(false);
            toolbar.getDomainColoringButton().setEnabled(false);

            toolbar.getOutCustomPaletteButton().setEnabled(false);
            toolbar.getOutCustomPalette2Button().setEnabled(false);
            toolbar.getInCustomPaletteButton().setEnabled(false);
            toolbar.getInCustomPalette2Button().setEnabled(false);
            toolbar.getRandomPaletteButton().setEnabled(false);

            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);

            options_menu.getFractalColor().setEnabled(false);
            options_menu.getContourFactor().setEnabled(false);
            options_menu.getRandomPalette().setEnabled(false);
            options_menu.getOutColoringPaletteMenu().setEnabled(false);
            options_menu.getInColoringPaletteMenu().setEnabled(false);
            options_menu.getPaletteGradientMerging().setEnabled(false);
            options_menu.getStatisticsColoring().setEnabled(false);
            options_menu.getProcessing().setEnabled(false);
            options_menu.getGradient().setEnabled(false);
            options_menu.getColorBlending().setEnabled(false);
            options_menu.getOutTrueColoring().setEnabled(false);
            options_menu.getInTrueColoring().setEnabled(false);
            options_menu.getColorSpaceParams().setEnabled(false);

            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            infobar.getGradientPreviewLabel().setVisible(false);
            infobar.getGradientPreview().setVisible(false);
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);
        }

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setContourColoring() {

        resetOrbit();
        new ContourColoringDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void resetSettings() {
        try {
            resetOrbit();

            s.defaultValues();
            s.applyStaticSettings();
            Fractal.clearReferences(true, true);

            if(MyApfloat.setAutomaticPrecision) {
                long precision = MyApfloat.getAutomaticPrecision(new String[]{s.size.toString(true)}, new boolean[] {true});

                if (MyApfloat.shouldSetPrecision(precision, true)) {
                    MyApfloat.setPrecision(precision, s);
                }
            }

            if (!s.d3s.d3) {
                TaskDraw.setArrays(image_size, s.ds.domain_coloring, s.needsExtraData());
                progress.setMaximum(image_size * image_size + 1);
                toolbar.get3DButton().setSelected(false);
                tools_menu.get3D().setIcon(getIcon("3d.png"));
            }

            if (!loadAutoSave()) {
                prepareUI();
            }

            TaskDraw.setDomainImageData(image_size, s.ds.domain_coloring);

            setOptions(false);

            progress.setValue(0);

            whole_image_done = false;

            resetImage();

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2.0 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2.0));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2.0 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2.0));

            if (s.d3s.d3) {
                s.d3s.fiX = 0.64;
                s.d3s.fiY = 0.82;
                ArraysFillColor(image, Color.BLACK.getRGB());
            }

            if(s.julia_map) {
                createTasksJuliaMap();
            } else {
                createTasks(false, true, false, false);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        }
    }

    public void setProcessingOrder() {

        resetOrbit();
        new ProcessingOrderingFrame(ptr, s.post_processing_order, s.pps.fdes.fake_de, s.pps.ens.entropy_coloring, s.pps.ofs.offset_coloring, s.pps.rps.rainbow_palette, s.pps.gss.greyscale_coloring, s.pps.cns.contour_coloring, s.pps.bms.bump_map, s.pps.ls.lighting, s.pps.ss.slopes, s.pps.ndes.useNumericalDem, s.pps.hss.histogramColoring);

    }

    public void setProcessingOrder(int[] processing_order) {
        s.post_processing_order = processing_order;
        updateColorsAfterPostProcessing(true);
    }

    public static void ArraysFillColor(BufferedImage img, int color) {
        int[] data = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        Arrays.fill(data, color);
    }

    public void redraw() {

        resetOrbit();
        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(s.d3s.d3, false);

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    public void setLighting() {

        resetOrbit();

        new LightDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setNumericalDistanceEstimator() {
        resetOrbit();

        new NumericalDistanceEstimatorDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);
    }
    public void setSlopes() {

        resetOrbit();

        new SlopesDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);

    }

    public void setPostProcessingPost() {
        if(s.d3s.d3) {
            TaskDraw.setExtraDataArrays(s.needsExtraData(), s.d3s.detail);
        }
        else {
            TaskDraw.setExtraDataArrays(s.needsExtraData(), image_size);
        }
        options_menu.getProcessing().updateIcons(s);
        updateColorsAfterPostProcessing(true);
    }

    public void setPaletteGradientMergingPost() {
        options_menu.getColorsMenu().updateIcons(s);
        updateColors(false);
    }

    public void setGeneratedPalettePost() {

        TaskDraw.palette_outcoloring.setGeneratedPaletteSettings(true, s.gps);
        TaskDraw.palette_incoloring.setGeneratedPaletteSettings(false, s.gps);

        if(s.gps.useGeneratedPaletteOutColoring && !(s.ds.domain_coloring && s.ds.domain_coloring_mode != 1)) {
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
        } else if (!s.useDirectColor && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                infobar.getOutColoringPalettePreview().setVisible(true);
                infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        }


        if(s.gps.useGeneratedPaletteInColoring) {
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);
        }
        else {
            if (!s.useDirectColor && !s.ds.domain_coloring && !(s.pps.sts.statistic && s.pps.sts.statisticGroup == 4) && s.usePaletteForInColoring) {
                infobar.getInColoringPalettePreview().setVisible(true);
                infobar.getInColoringPalettePreviewLabel().setVisible(true);
            }
        }

        options_menu.getColorsMenu().getOutColoringPaletteMenu().updateIcons(s);
        options_menu.getColorsMenu().getInColoringPaletteMenu().updateIcons(s);

        updateColors(false);
    }

    public void setPaletteGradientMerging() {

        resetOrbit();
        new PaletteGradientMergingDialog(ptr, s);

    }

    public void updateColors(boolean skipPreview) {

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(s.d3s.d3, false);

        if (s.fs.filters[ANTIALIASING] || s.needsChangeOfSmoothing() || s.pps.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 2 && s.pps.sts.equicontinuityOverrideColoring)
                || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {


            if(!TaskDraw.hasExtraData(image_size) || s.d3s.d3 || s.ds.domain_coloring || s.needsChangeOfSmoothing() || s.pps.ots.useTraps || s.pps.sts.statistic || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn) {
                if(s.julia_map) {
                    createTasksJuliaMap();
                } else {
                    createTasks(false, !skipPreview, false, false);
                }
            }
            else {
                createTasksPalettePostProcessAndFilterWithAAData(TaskDraw.APPLY_PALETTE_AND_POST_PROCESSING_WITH_AA_AND_FILTER);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        } else {
            if (s.d3s.d3) {
                createTasks(false, true, false, false);
            } else {
                createTasksPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }
    }

    public void updateColorsAfterPostProcessing(boolean skipPreview) {

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImageAndCopy(s.d3s.d3, false);

        if (s.fs.filters[ANTIALIASING] || s.needsChangeOfSmoothing()) {

            if(!TaskDraw.hasExtraData(image_size) || s.d3s.d3 || s.needsChangeOfSmoothing()) {
                if(s.julia_map) {
                    createTasksJuliaMap();
                } else {
                    createTasks(false, !skipPreview, false, false);
                }
            }
            else {
                createTasksPalettePostProcessAndFilterWithAAData(TaskDraw.POST_PROCESSING_WITH_AA_AND_FILTER);
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        } else {
            if (s.d3s.d3) {
                createTasks(false, true, false, false);
            } else {
                createTasksPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startTasks();
        }
    }

    public void openStatisticsColoringFrame() {
        resetOrbit();
        new StatisticsColoringFrame(ptr, s.pps.sts, s, PERIODICITY_CHECKING);
    }

    public void setHistogramColoring() {
        resetOrbit();
        new HistogramColoringDialog(ptr, s, TaskDraw.GREEDY_ALGORITHM, s.julia_map);
    }

    public void statisticsColorAlgorithmChanged(StatisticsSettings sts) {
        s.pps.sts = new StatisticsSettings(sts);

        options_menu.getProcessing().updateIcons(s);

        if((sts.statistic && sts.statisticGroup == 2 && sts.equicontinuityOverrideColoring) || (s.pps.sts.statistic && (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics)) || (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }

        if(sts.statistic && sts.statisticGroup == 4) {
            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            infobar.getOutColoringPalettePreview().setVisible(false);
            infobar.getOutColoringPalettePreviewLabel().setVisible(false);
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);

            options_menu.getHistogramColoring().setEnabled(false);

            s.pps.hss.histogramColoring = false;
            options_menu.getProcessing().updateIcons(s);
        }
        else {
            if (!s.useDirectColor) {
                setPalettePreviewsVisible();
                infobar.getGradientPreviewLabel().setVisible(true);
                infobar.getGradientPreview().setVisible(true);
            }
        }

        setOptions(false);

        progress.setValue(0);

        whole_image_done = false;

        resetImage();

        if (s.d3s.d3) {
            ArraysFillColor(image, Color.BLACK.getRGB());
        }

        if(s.julia_map) {
            createTasksJuliaMap();
        } else {
            createTasks(false, true, false, false);
        }

        calculation_time = System.currentTimeMillis();

        startTasks();

    }

    private boolean loadAutoSave() {

        String filename = "autoload.fzs";
        try {
            Path path = Paths.get(filename);
            if (Files.exists(path) && Files.isRegularFile(path)) {
                s.readSettings(filename, scroll_pane, true);
                prepareUI();
                return true;
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            exit(-1);
        }

        return false;

    }

    public void setInitialSettings() {
        String filename = "autoload.fzs";
        s.save(filename);
        JOptionPane.showMessageDialog(scroll_pane, filename + " was updated with the current settings.\nThe new settings will be loaded at the start-up of the application.", "Initial Settings", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setFunctionPostNova() {
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutConditionMenu().setEnabled(false);
        options_menu.getConvergentBailoutConditionMenu().setEnabled(true);
    }

    public void setFunctionPostKleinian() {
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
        options_menu.getPlaneInfluenceMenu().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutConditionMenu().setEnabled(false);
        options_menu.getConvergentBailoutConditionMenu().setEnabled(false);
    }

    public void setOutTrueColor() {
        resetOrbit();
        new OutTrueColorDialog(ptr, s);
    }

    public void setInTrueColor() {
        resetOrbit();
        new InTrueColorDialog(ptr, s);
    }

    public void setOutTrueColorModePost() {

        options_menu.getColorsMenu().updateIcons(s);

        tools_menu.getColorCycling().setEnabled(false);
        toolbar.getColorCyclingButton().setEnabled(false);

        redraw();
    }

    public void setInTrueColorModePost() {

        options_menu.getPeriodicityChecking().setEnabled(false);
        setOutTrueColorModePost();

    }

    public void setFunctionFilter(int filter, boolean isPostFilter) {

        resetOrbit();
        int oldSelection;

        if(isPostFilter) {
            oldSelection = s.fns.postffs.functionFilter;
            s.fns.postffs.functionFilter = filter;

            if (s.fns.postffs.functionFilter == USER_FUNCTION_FILTER) {
                new UserFunctionFilterDialog(ptr, s, oldSelection,  "User Post Function Filter", s.fns.postffs, options_menu.getPostFunctionFilters());
                return;
            }
        }
        else {
            oldSelection = s.fns.preffs.functionFilter;
            s.fns.preffs.functionFilter = filter;

            if (s.fns.preffs.functionFilter == USER_FUNCTION_FILTER) {
                new UserFunctionFilterDialog(ptr, s, oldSelection,  "User Pre Function Filter", s.fns.preffs, options_menu.getPreFunctionFilters());
                return;
            }
        }

        ptr.defaultFractalSettings(true);
    }

    public void setPlaneInfluence(int plane_influence) {

        resetOrbit();
        int oldSelection;

        oldSelection = s.fns.ips.influencePlane;
        s.fns.ips.influencePlane = plane_influence;

        if (s.fns.ips.influencePlane == USER_PLANE_INFLUENCE) {
            new UserPlaneInfluenceDialog(ptr, s, oldSelection, s.fns.ips, options_menu.getPlaneInfluences());
            return;
        }

        ptr.defaultFractalSettings(true);
    }

    public void clickCurrentFunction() {

        fractal_functions[s.fns.function].doClick();

    }

    public void clickCurrentPlane() {

        planes[s.fns.plane_type].doClick();

    }

    public static ImageIcon getIcon(String file) {

        return new ImageIcon(MainWindow.class.getResource("/fractalzoomer/icons/" + file)) {
            @Override
            public synchronized void paintIcon(Component c, Graphics g,
                                               int x, int y) {

                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintIcon(c, g, x, y);
            }
        };

    }

    public void setGeneratedPalette(boolean outcoloring) {
        new GeneratedPaletteDialog(ptr, s, outcoloring);
    }

    public void exit(int val) {

        savePreferences();
        TaskDraw.deleteLibs();

        new Timer().schedule(new TimerTask() {
            public void run() {
                Runtime.getRuntime().halt(-1);
            }
        }, 8000);

        TaskDraw.shutdownThreadPools();

        System.exit(val);

    }

    public void setColorMap(boolean outcoloring_mode) {
        new ColorMapFrame(ptr, s.fns.smoothing, outcoloring_mode, outcoloring_mode ? options_menu.getOutColoringPalette() : options_menu.getInColoringPalette());
    }

    public void setCustomDirectPalette(boolean outcoloring_mode) {
       new ColorPaletteEditorFrame(ptr, outcoloring_mode, outcoloring_mode ? options_menu.getOutColoringPalette() : options_menu.getInColoringPalette(), outcoloring_mode ? s.ps.color_space : s.ps2.color_space);
    }

    public void setJitterPost() {

        options_menu.updateIcons(s);

        redraw();
    }

    public void setJitter() {
        new JitterDialog(ptr, s);
    }

    public void showP3D() {
        String[] processingArgs = {"HeightMap"};
        try {
            P3DHeightMap heightMapFrame = new P3DHeightMap(ptr,700, image_size, s.d3s.detail ,P3D_AA, P3D_AA_SAMPLES);
            heightMapFrames.add(heightMapFrame);
            PApplet.runSketch(processingArgs, heightMapFrame);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Cannot initialize Processing.", "Error!", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void closeP3D() {
        for(P3DHeightMap heightMapFrame : heightMapFrames) {
            if (heightMapFrame.isValid()) {
                heightMapFrame.close();
            }
        }
        heightMapFrames.clear();
    }

    private void updateP3D() {
        for(P3DHeightMap heightMapFrame : heightMapFrames) {
            if (heightMapFrame.isValid()) {
                heightMapFrame.update(image_size, s.d3s.detail);
            }
        }
    }

    public void setP3DDraw(boolean val) {
        for(P3DHeightMap heightMapFrame : heightMapFrames) {
            if (heightMapFrame.isValid()) {
                heightMapFrame.setReadyToDraw(val);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        SplashFrame sf = new SplashFrame(VERSION);
        sf.setVisible(true);

        while (sf.isAnimating()) {
        }

        sf.dispose();

        if(args.length > 0 && args[0].equals("l4jini")) {
            CommonFunctions.exportL4jIni("Fractal Zoomer", Constants.FZL4j);
        }

        MainWindow mw = new MainWindow();
        try {
            mw.setVisible(true);
        }
        catch (Exception ex) {}

        mw.checkCompilationStatus();
        mw.getCommonFunctions().checkForUpdate(false);

    }
}
