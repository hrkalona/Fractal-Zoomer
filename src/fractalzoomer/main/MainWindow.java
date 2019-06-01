/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
 /* Thanks to FractView's developer for the lighting algorithms and some of the branching techniques */
 /* Thanks to Lutz Lehmann for the parhalley and laguerre implementation */
 /* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
 /* Sorry for the absence of comments, this project was never supposed to reach this level! */
 /* Also forgive me for the huge-packed main class, read above! */
package fractalzoomer.main;

//import com.alee.laf.WebLookAndFeel;
import fractalzoomer.bailout_conditions.SkipBailoutCondition;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.core.drawing_algorithms.BoundaryTracingDraw;
import fractalzoomer.core.drawing_algorithms.BruteForceDraw;
import fractalzoomer.core.Complex;
import fractalzoomer.core.drawing_algorithms.DivideAndConquerDraw;
import fractalzoomer.core.DrawOrbit;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.parser.ParserException;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.BumpMapSettings;
import fractalzoomer.main.app_settings.LightSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;
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
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;

/**
 *
 * @author hrkalona
 */
public class MainWindow extends JFrame implements Constants {

    private static final long serialVersionUID = -6314723558420412681L;
    private Settings s;
    private boolean first_paint;
    private boolean orbit;
    private boolean show_orbit_converging_point;
    private int orbit_style;
    private int zoom_window_style;
    private boolean whole_image_done;
    private boolean fast_julia_filters;
    private boolean periodicity_checking;
    private boolean color_cycling;
    private int color_cycling_speed;
    private boolean cycle_colors;
    private boolean cycle_lights;
    private boolean cycle_gradient;
    public static boolean runsOnWindows;
    private boolean grid;
    private boolean old_grid;
    private boolean boundaries;
    private boolean old_boundaries;
    private boolean zoom_window;
    private boolean julia_map;
    private boolean old_polar_projection;
    private boolean old_d3;
    private boolean greedy_algorithm;
    private ThreadDraw[][] threads;
    private DrawOrbit pixels_orbit;
    private double old_xCenter;
    private double old_yCenter;
    private double old_size;
    private double old_height_ratio;
    private int grid_tiles;
    private int boundaries_num;
    private double[] old_rotation_vals;
    private double[] old_rotation_center;
    private double[] orbit_vals;
    private double dfi;
    private int mx0;
    private int my0;
    //private int d3_draw_method;
    private int boundaries_spacing_method;
    private int boundaries_type;
    private boolean first_seed;
    private int n;
    private int greedy_algorithm_selection;
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
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private Cursor rotate_cursor;
    private CommonFunctions common;
    private int i;
    private Object[] compilationStatus;

    /**
     * *****************************
     */
    public MainWindow() {

        super();

        s = new Settings();
        s.applyStaticSettings();

        runsOnWindows = false;

        ptr = this;

        zoom_factor = 2;

        old_xCenter = s.xCenter;
        old_yCenter = s.yCenter;
        old_size = s.size;
        old_height_ratio = s.height_ratio;

        n = 2;
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

        greedy_algorithm = true;

        color_cycling_speed = 140;
        cycle_colors = true;
        cycle_lights = false;
        cycle_gradient = false;

        old_rotation_vals = new double[2];

        old_rotation_vals[0] = s.fns.rotation_vals[0];
        old_rotation_vals[1] = s.fns.rotation_vals[1];

        old_rotation_center = new double[2];

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
        show_orbit_converging_point = true;

        greedy_algorithm_selection = BOUNDARY_TRACING;

        orbit_color = Color.WHITE;
        grid_color = Color.WHITE;
        boundaries_color = new Color(0, 204, 102);
        zoom_window_color = Color.WHITE;

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

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
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
            common.setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.PLAIN, 11));
        }

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Get the current screen size
        Dimension scrnsize = toolkit.getScreenSize();

        if (scrnsize.getHeight() > getHeight()) {
            setLocation((int) ((scrnsize.getWidth() / 2) - (getWidth() / 2)), (int) ((scrnsize.getHeight() / 2) - (getHeight() / 2)) - 23);
        } else {
            setLocation((int) ((scrnsize.getWidth() / 2) - (getWidth() / 2)), 0);
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

        options_menu = new OptionsMenu(ptr, "Options", s.ps, s.ps2, s.fns.smoothing, show_orbit_converging_point, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.out_coloring_algorithm, s.fns.in_coloring_algorithm, s.fns.function, s.fns.plane_type, s.fns.bailout_test_algorithm, s.color_blending, s.temp_color_cycling_location, s.temp_color_cycling_location_second_palette);

        fractal_functions = options_menu.getFractalFunctions();

        planes = options_menu.getPlanes();

        bailout_tests = options_menu.getBailoutConditions();

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
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                        ctrlKeyPressed = true;
                        scroll_pane.setCursor(grab_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                        shiftKeyPressed = true;
                        scroll_pane.setCursor(rotate_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                        altKeyPressed = true;
                        scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && ctrlKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                        ctrlKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && shiftKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                        shiftKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
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

        scroll_pane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (!orbit && !s.d3s.d3 && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {

                    if (!s.polar_projection) {
                        scrollPoint(e);
                    } else {
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

                if (ctrlKeyPressed || shiftKeyPressed) {
                    double curX = main_panel.getMousePosition().getX();
                    double curY = main_panel.getMousePosition().getY();

                    if (!(curX < 0 || curX > image_size || curY < 0 || curY > image_size)) {
                        oldDragX = curX;
                        oldDragY = curY;
                    }
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
                         if (s.fns.julia && first_seed) {
                                selectPointJulia(e);
                            } else {
                                selectPointPolar(e);
                            }
                    } else { // 3D
                        selectPoint3D(e);
                    }
                } else { //orbit
                    selectPointOrbit(e);
                }

                if (julia_map || (s.fns.julia && first_seed)) {
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

        addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent e) {

                try {
                    resetOrbit();

                    if (!threadsAvailable()) {
                        return;
                    }

                    if (e.getOldState() == NORMAL && e.getNewState() == MAXIMIZED_BOTH) {
                        image_size = getWidth() - 35;
                    } else if (e.getNewState() == NORMAL && e.getOldState() == MAXIMIZED_BOTH) {
                        if (getHeight() > getWidth()) {
                            image_size = getHeight() - 61;
                        } else {
                            image_size = getWidth() - 35;
                        }
                    } else {
                        return;
                    }

                    if (image_size > 6000) {
                        image_size = 6000;
                    }

                    whole_image_done = false;

                    old_grid = false;

                    old_boundaries = false;

                    if (!s.d3s.d3) {
                        ThreadDraw.setArrays(image_size, s.ds.domain_coloring);
                    }

                    main_panel.setPreferredSize(new Dimension(image_size, image_size));

                    setOptions(false);

                    if (!s.d3s.d3) {
                        progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));
                    }

                    progress.setValue(0);

                    SwingUtilities.updateComponentTreeUI(ptr);

                    scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                    scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                    last_used = null;

                    image = null;

                    clearThreads();

                    System.gc();

                    last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                    image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

                    if (s.d3s.d3) {
                        Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
                    }

                    if (s.fns.julia && first_seed) {
                        s.fns.julia = false;
                        tools_menu.getJulia().setSelected(false);
                        toolbar.getJuliaButton().setSelected(false);

                        if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED) {
                            rootFindingMethodsSetEnabled(true);
                        }
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                        fractal_functions[INERTIA_GRAVITY].setEnabled(true);
                        fractal_functions[KLEINIAN].setEnabled(true);
                    }

                    if (julia_map) {
                        createThreadsJuliaMap();
                    } else {
                        createThreads(false);
                    }

                    calculation_time = System.currentTimeMillis();

                    if (julia_map) {
                        startThreads(julia_grid_first_dimension);
                    } else {
                        startThreads(n);
                    }
                } catch (OutOfMemoryError er) {
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                    savePreferences();
                    System.exit(-1);
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
                } else if (ctrlKeyPressed && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                    dragPoint(e);
                } else if (shiftKeyPressed && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                    rotatePoint(e);
                } else if (altKeyPressed && !julia_map && (!s.fns.julia || s.fns.julia && !first_seed)) {
                    selectPointForPlane(e);
                }

                if ((julia_map || (s.fns.julia && first_seed)) && scroll_pane.getCursor().getType() != Cursor.CROSSHAIR_CURSOR) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if ((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
                } else if ((old_d3 && e.getModifiers() != InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grab_cursor);
                } else if (shiftKeyPressed) {
                    scroll_pane.setCursor(rotate_cursor);
                } else if (altKeyPressed && scroll_pane.getCursor().getType() != Cursor.HAND_CURSOR) {
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

                        double start;
                        double center = Math.log(old_size);

                        double f, sf, cf, r;
                        double muly = (2 * s.circle_period * Math.PI) / image_size;

                        double mulx = muly * old_height_ratio;

                        start = center - mulx * image_size * 0.5;

                        f = y1 * muly;
                        sf = Math.sin(f);
                        cf = Math.cos(f);

                        r = Math.exp(x1 * mulx + start);

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(old_xCenter + r * cf, old_yCenter + r * sf, old_rotation_vals, old_rotation_center);

                        statusbar.getReal().setText("" + p.x);

                        statusbar.getImaginary().setText("" + p.y);

                    } else {
                        int x1 = (int) main_panel.getMousePosition().getX();
                        int y1 = (int) main_panel.getMousePosition().getY();

                        if (x1 < 0 || x1 > image_size || y1 < 0 || y1 > image_size) {
                            if (!color_cycling) {
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

        ThreadDraw.setArrays(image_size, s.ds.domain_coloring);

        threads = new ThreadDraw[n][n];

        main_panel.setPreferredSize(new Dimension(image_size, image_size));

        requestFocus();

        progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));

        setOptions(false);

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //System.arraycopy(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt)last_used.getRaster().getDataBuffer()).getData(), 0, image_size * image_size);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        fast_julia_image = new BufferedImage(FAST_JULIA_IMAGE_SIZE, FAST_JULIA_IMAGE_SIZE, BufferedImage.TYPE_INT_ARGB);

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void checkCompilationStatus() {

        if (((Integer) compilationStatus[0]) == 0) {
            return;
        } else if (((Integer) compilationStatus[0]) == 1) {
            JOptionPane.showMessageDialog(ptr, ((String) compilationStatus[1]), "Warning!", JOptionPane.WARNING_MESSAGE);
        } else if (((Integer) compilationStatus[0]) == 2) {
            JOptionPane.showMessageDialog(ptr, ((String) compilationStatus[1]), "Error!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public boolean threadsAvailable() {

        try {
            for (int i = 0; i < threads.length; i++) {
                for (int j = 0; j < threads[i].length; j++) {
                    if (threads[i][j].isAlive()) {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
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

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.fns.rotation_vals, s.fns.rotation_center);

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
                temp += "   Newton " + s.poly;
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
                temp += "   Halley " + s.poly;
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
                temp += "   Schroder " + s.poly;
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
                temp += "   Householder " + s.poly;
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
                temp += "   Secant " + s.poly;
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
                temp += "   Steffensen " + s.poly;
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
                temp += "   Muller " + s.poly;
                break;
            case MULLERFORMULA:
                temp += "   Muller Formula";
                break;
            case PARHALLEY3:
                temp += "   Parhalley p(z) = z^3 -1";
                break;
            case PARHALLEY4:
                temp += "   Parhalley p(z) = z^4 -1";
                break;
            case PARHALLEYGENERALIZED3:
                temp += "   Parhalley p(z) = z^3 -2z +2";
                break;
            case PARHALLEYGENERALIZED8:
                temp += "   Parhalley p(z) = z^8 +15z^4 -16";
                break;
            case PARHALLEYSIN:
                temp += "   Parhalley f(z) = sin(z)";
                break;
            case PARHALLEYCOS:
                temp += "   Parhalley f(z) = cos(z)";
                break;
            case PARHALLEYPOLY:
                temp += "   Parhalley " + s.poly;
                break;
            case PARHALLEYFORMULA:
                temp += "   Parhalley Formula";
                break;
            case LAGUERRE3:
                temp += "   Laguerre p(z) = z^3 -1";
                break;
            case LAGUERRE4:
                temp += "   Laguerre p(z) = z^4 -1";
                break;
            case LAGUERREGENERALIZED3:
                temp += "   Laguerre p(z) = z^3 -2z +2";
                break;
            case LAGUERREGENERALIZED8:
                temp += "   Laguerre p(z) = z^8 +15z^4 -16";
                break;
            case LAGUERRESIN:
                temp += "   Laguerre f(z) = sin(z)";
                break;
            case LAGUERRECOS:
                temp += "   Laguerre f(z) = cos(z)";
                break;
            case LAGUERREPOLY:
                temp += "   Laguerre " + s.poly;
                break;
            case LAGUERREFORMULA:
                temp += "   Laguerre Formula";
                break;
            case DURAND_KERNER3:
                temp += "   Durand/Kerner p(z) = z^3 -1";
                break;
            case DURAND_KERNER4:
                temp += "   Durand/Kerner p(z) = z^4 -1";
                break;
            case DURAND_KERNERGENERALIZED3:
                temp += "   Durand/Kerner p(z) = z^3 -2z +2";
                break;
            case DURAND_KERNERGENERALIZED8:
                temp += "   Durand/Kerner p(z) = z^8 +15z^4 -16";
                break;
            case DURAND_KERNERPOLY:
                temp += "   Durand/Kerner " + s.poly;
                break;
            case BAIRSTOW3:
                temp += "   Bairstow p(z) = z^3 -1";
                break;
            case BAIRSTOW4:
                temp += "   Bairstow p(z) = z^4 -1";
                break;
            case BAIRSTOWGENERALIZED3:
                temp += "   Bairstow p(z) = z^3 -2z +2";
                break;
            case BAIRSTOWGENERALIZED8:
                temp += "   Bairstow p(z) = z^8 +15z^4 -16";
                break;
            case BAIRSTOWPOLY:
                temp += "   Bairstow " + s.poly;
                break;
            case NEWTON_HINES3:
                temp += "   Newton-Hines p(z) = z^3 -1";
                break;
            case NEWTON_HINES4:
                temp += "   Newton-Hines p(z) = z^4 -1";
                break;
            case NEWTON_HINESGENERALIZED3:
                temp += "   Newton-Hines p(z) = z^3 -2z +2";
                break;
            case NEWTON_HINESGENERALIZED8:
                temp += "   Newton-Hines p(z) = z^8 +15z^4 -16";
                break;
            case NEWTON_HINESSIN:
                temp += "   Newton-Hines f(z) = sin(z)";
                break;
            case NEWTON_HINESCOS:
                temp += "   Newton-Hines f(z) = cos(z)";
                break;
            case NEWTON_HINESPOLY:
                temp += "   Newton-Hines " + s.poly;
                break;
            case NEWTON_HINESFORMULA:
                temp += "   Newton-Hines Formula";
                break;
            case MANDEL_NEWTON:
                temp += "   Mandel Newton Variation";
                break;
            case LAMBERT_W_VARIATION:
                temp += "   Lambert W Variation";
                break;
            case NOVA:
                switch (s.fns.nova_method) {

                    case MainWindow.NOVA_NEWTON:
                        temp += "   Nova-Newton, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_HALLEY:
                        temp += "   Nova-Halley, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_SCHRODER:
                        temp += "   Nova-Schroder, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_HOUSEHOLDER:
                        temp += "   Nova-Householder, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_SECANT:
                        temp += "   Nova-Secant, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_STEFFENSEN:
                        temp += "   Nova-Steffensen, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_MULLER:
                        temp += "   Nova-Muller, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_PARHALLEY:
                        temp += "   Nova-Parhallley, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
                        break;
                    case MainWindow.NOVA_LAGUERRE:
                        temp += "   Nova-Laguerre, e: " + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]);
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

        }

        temp += "   Center: " + Complex.toString2(p.x, p.y) + "   Size: " + s.size;

        setTitle(temp);

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

                double start;
                double center = Math.log(s.size);

                double f, sf, cf, r;
                double muly = (2 * s.circle_period * Math.PI) / image_size;

                double mulx = muly * s.height_ratio;

                start = center - mulx * image_size * 0.5;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + p2.x);

                statusbar.getImaginary().setText("" + p2.y);
            } catch (Exception ex) {
            }
        } else {
            try {
                int x1 = (int) main_panel.getMousePosition().getX();
                int y1 = (int) main_panel.getMousePosition().getY();

                double size_2_x = s.size * 0.5;
                double size_2_y = (s.size * s.height_ratio) * 0.5;
                double temp_xcenter_size = s.xCenter - size_2_x;
                double temp_ycenter_size = s.yCenter + size_2_y;
                double temp_size_image_size_x = s.size / image_size;
                double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + p2.x);

                statusbar.getImaginary().setText("" + p2.y);
            } catch (Exception ex) {
            }
        }
    }

    private void createThreads(boolean quickDraw) {

        ThreadDraw.resetThreadData(n * n);
        Parser.usesUserCode = false;

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {

                    int FROMx = 0, TOx = 0, FROMy = 0, TOy = 0;

                    if (s.d3s.d3) {
                        FROMx = j * s.d3s.detail / n;
                        TOx = (j + 1) * s.d3s.detail / n;
                        FROMy = i * s.d3s.detail / n;
                        TOy = (i + 1) * s.d3s.detail / n;
                    } else {
                        FROMx = j * image_size / n;
                        TOx = (j + 1) * image_size / n;
                        FROMy = i * image_size / n;
                        TOy = (i + 1) * image_size / n;
                    }

                    if (s.fns.julia) {
                        if (greedy_algorithm) {
                            if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                                threads[i][j] = new BoundaryTracingDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.xJuliaCenter, s.yJuliaCenter);
                            } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                threads[i][j] = new DivideAndConquerDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        } else {
                            threads[i][j] = new BruteForceDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, s.xJuliaCenter, s.yJuliaCenter);
                        }
                    } else if (greedy_algorithm) {
                        if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                            threads[i][j] = new BoundaryTracingDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset);
                        } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                            threads[i][j] = new DivideAndConquerDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset);
                        }
                    } else {
                        threads[i][j] = new BruteForceDraw(FROMx, TOx, FROMy, TOy, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.ds, s.inverse_dem, quickDraw, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset);
                    }
                    threads[i][j].setThreadId(i * n + j);
                }
            }
        } catch (ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }
    }

    private void startThreads(int t) {

        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                threads[i][j].start();
            }
        }
    }

    public void saveSettings() {

        resetOrbit();
        file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".fzs";

        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if (index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Settings");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            s.save(file.toString());

            if (Parser.usesUserCode) {
                JOptionPane.showMessageDialog(scroll_pane, "The saved settings use functions that are included inside the file UserDefinedFunctions.java.\nYou must also save this file if you want recreate the saved settings.", "Information!", JOptionPane.INFORMATION_MESSAGE);
            }
        }

        main_panel.repaint();

    }

    public void prepareUI() {

        julia_map = false;
        tools_menu.getJuliaMap().setSelected(false);
        toolbar.getJuliaMapButton().setSelected(false);
        options_menu.getJuliaMapOptions().setEnabled(false);

        zoom_window = false;
        tools_menu.getZoomWindow().setSelected(false);

        if (s.fns.julia) {
            first_seed = false;
            tools_menu.getJulia().setSelected(true);
            toolbar.getJuliaButton().setSelected(true);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
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
                tools_menu.getJulia().setEnabled(false);
                toolbar.getJuliaButton().setEnabled(false);
                tools_menu.getJuliaMap().setEnabled(false);
                toolbar.getJuliaMapButton().setEnabled(false);
                rootFindingMethodsSetEnabled(false);
                fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                fractal_functions[INERTIA_GRAVITY].setEnabled(false);
                fractal_functions[KLEINIAN].setEnabled(false);
            }
        }

        options_menu.getPerturbation().setSelected(s.fns.perturbation);
        options_menu.getInitialValue().setSelected(s.fns.init_val);

        if (s.size < 0.05) {
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
            periodicity_checking = false;
            options_menu.getPeriodicityChecking().setSelected(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
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
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        }

        if (!s.useDirectColor) {
            options_menu.getDirectColor().setSelected(false);

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        } else {
            options_menu.getDirectColor().setSelected(true);

            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);

            tools_menu.getDomainColoring().setEnabled(false);
            toolbar.getDomainColoringButton().setEnabled(false);

            toolbar.getOutCustomPaletteButton().setEnabled(false);
            toolbar.getInCustomPaletteButton().setEnabled(false);
            toolbar.getRandomPaletteButton().setEnabled(false);

            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);

            options_menu.getFractalColor().setEnabled(false);
            options_menu.getRandomPalette().setEnabled(false);
            options_menu.getOutColoringPaletteMenu().setEnabled(false);
            options_menu.getPaletteGradientMerging().setEnabled(false);
            options_menu.getInColoringPaletteMenu().setEnabled(false);
            options_menu.getStatisticsColoring().setEnabled(false);
            options_menu.getProcessing().setEnabled(false);
            options_menu.getGradient().setEnabled(false);
            options_menu.getColorBlending().setEnabled(false);

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

        options_menu.getBlendingModes()[s.color_blending].setSelected(true);
        options_menu.getOutColoringTranferFunctions()[s.ps.transfer_function].setSelected(true);
        options_menu.getInColoringTranferFunctions()[s.ps2.transfer_function].setSelected(true);

        options_menu.getUsePaletteForInColoring().setSelected(s.usePaletteForInColoring);

        bailout_tests[s.fns.bailout_test_algorithm].setSelected(true);

        if (s.polar_projection) {
            tools_menu.getGrid().setEnabled(false);
            tools_menu.getZoomWindow().setEnabled(false);

            grid = false;
            boundaries = false;

            tools_menu.getGrid().setSelected(false);
            tools_menu.getBoundaries().setSelected(false);

            options_menu.getPolarProjectionOptions().setEnabled(true);

        } else {
            options_menu.getPolarProjectionOptions().setEnabled(false);
        }

        tools_menu.getPolarProjection().setSelected(s.polar_projection);
        toolbar.getPolarProjectionButton().setSelected(s.polar_projection);

        if (s.ds.domain_coloring) {
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);

            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);

            if (s.ds.domain_coloring_mode != 1) {
                toolbar.getOutCustomPaletteButton().setEnabled(false);
                toolbar.getRandomPaletteButton().setEnabled(false);

                options_menu.getRandomPalette().setEnabled(false);
                options_menu.getOutColoringPaletteMenu().setEnabled(false);
            }

            options_menu.getDistanceEstimation().setEnabled(false);

            options_menu.getFakeDistanceEstimation().setEnabled(false);
            options_menu.getContourColoring().setEnabled(false);

            options_menu.getEntropyColoring().setEnabled(false);
            options_menu.getOffsetColoring().setEnabled(false);
            options_menu.getGreyScaleColoring().setEnabled(false);

            options_menu.getRainbowPalette().setEnabled(false);
            options_menu.getOrbitTraps().setEnabled(false);

            toolbar.getInCustomPaletteButton().setEnabled(false);
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

            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);

            options_menu.getDomainColoringOptions().setEnabled(true);
        } else {
            if (!s.useDirectColor) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }

            options_menu.getDomainColoringOptions().setEnabled(false);
        }

        tools_menu.getDomainColoring().setSelected(s.ds.domain_coloring);
        toolbar.getDomainColoringButton().setSelected(s.ds.domain_coloring);

        if (s.ots.useTraps || s.fns.tcs.trueColorIn) {
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

        switch (s.fns.function) {
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
            case PARHALLEY3:
            case PARHALLEY4:
            case PARHALLEYGENERALIZED3:
            case PARHALLEYGENERALIZED8:
            case PARHALLEYSIN:
            case PARHALLEYCOS:
            case LAGUERRE3:
            case LAGUERRE4:
            case LAGUERREGENERALIZED3:
            case LAGUERREGENERALIZED8:
            case LAGUERRESIN:
            case LAGUERRECOS:
            case DURAND_KERNER3:
            case DURAND_KERNER4:
            case DURAND_KERNERGENERALIZED3:
            case DURAND_KERNERGENERALIZED8:
            case BAIRSTOW3:
            case BAIRSTOW4:
            case BAIRSTOWGENERALIZED3:
            case BAIRSTOWGENERALIZED8:
            case NEWTONFORMULA:
            case HALLEYFORMULA:
            case SCHRODERFORMULA:
            case HOUSEHOLDERFORMULA:
            case SECANTFORMULA:
            case STEFFENSENFORMULA:
            case MULLERFORMULA:
            case PARHALLEYFORMULA:
            case LAGUERREFORMULA:
            case MAGNETIC_PENDULUM:
            case LAMBERT_W_VARIATION:
            case NEWTON_HINES3:
            case NEWTON_HINES4:
            case NEWTON_HINESGENERALIZED3:
            case NEWTON_HINESGENERALIZED8:
            case NEWTON_HINESSIN:
            case NEWTON_HINESCOS:
            case NEWTON_HINESFORMULA:
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
            case PARHALLEYPOLY:
            case LAGUERREPOLY:
            case DURAND_KERNERPOLY:
            case BAIRSTOWPOLY:
            case NEWTON_HINESPOLY:
                if (s.fns.function == MANDELPOLY) {
                    optionsEnableShortcut();
                } else {
                    optionsEnableShortcut2();
                }
                break;
            case NOVA:
                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutConditionMenu().setEnabled(false);
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
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutConditionMenu().setEnabled(false);
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
                break;
            case MANDELBROT:
                if (!s.ds.domain_coloring) {
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
                }

                if (s.fns.bail_technique == 1 || s.fns.function == USER_FORMULA_NOVA) {
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutConditionMenu().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                }

                optionsEnableShortcut();
                break;
            default:
                optionsEnableShortcut();
                break;
        }

        options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true);
        options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true);
        updateColorPalettesMenu();
        updateMaxIterColorPreview();
        updateGradientPreview(s.gs.gradient_offset);

        options_menu.getBurningShipOpt().setSelected(s.fns.burning_ship);
        options_menu.getMandelGrassOpt().setSelected(s.fns.mandel_grass);

        planes[s.fns.plane_type].setSelected(true);
    }

    public void loadSettings() {

        resetOrbit();
        file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Settings (*.fzs)", "fzs"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if (index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Load Settings");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            String filename = file.toString();
            try {
                s.readSettings(filename, scroll_pane, false);

                ThreadDraw.setDomainImageData(image_size, s.ds.domain_coloring);

                prepareUI();

                setOptions(false);

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                whole_image_done = false;

                if (s.d3s.d3) {
                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;
                    Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
                }

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.", "Error!", JOptionPane.ERROR_MESSAGE);;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the " + filename + " file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            }
        }

    }

    public void saveSettingsAndImage() {

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";

        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if (index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Settings and Image");

        Graphics2D graphics = last_used.createGraphics();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) last_used.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        } else {
            Arrays.fill(((DataBufferInt) last_used.getRaster().getDataBuffer()).getData(), 0);
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

                    if (Parser.usesUserCode) {
                        JOptionPane.showMessageDialog(scroll_pane, "The saved settings use functions that are included inside the file UserDefinedFunctions.java.\nYou must also save this file if you want recreate the saved settings.", "Information!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
            }

        }

        resetOrbit();
    }

    public void saveImage() {

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        String name = "fractal " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".png";
        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                String extension = filter.getExtensions()[0];

                String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                int index = file_name.lastIndexOf(".");

                if (index != -1) {
                    file_name = file_name.substring(0, index);
                }

                file_chooser.setSelectedFile(new File(file_name + "." + extension));
            }
        });

        int returnVal = file_chooser.showDialog(ptr, "Save Image");

        Graphics2D graphics = last_used.createGraphics();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) last_used.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        } else {
            Arrays.fill(((DataBufferInt) last_used.getRaster().getDataBuffer()).getData(), 0);
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

            } catch (IOException ex) {
            }

        }

        resetOrbit();

    }

    public void defaultFractalSettings() {

        resetOrbit();
        setOptions(false);

        s.defaultFractalSettings();

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            s.d3s.fiX = 0.64;
            s.d3s.fiY = 0.82;
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setCenterSizePost() {
        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
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

        if (pixels_orbit != null) {
            try {
                pixels_orbit.join();

            } catch (InterruptedException ex) {

            }
        }

        try {
            pixels_orbit = new DrawOrbit(s, x_real, y_imag, seq_points, image_size, ptr, orbit_color, orbit_style, show_orbit_converging_point);
            pixels_orbit.start();
        } catch (ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        } catch (Exception ex) {
        }
    }

    public void setJuliaSeedPost() {
        first_seed = false;
        defaultFractalSettings();
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
        new FractalColorsFrame(ptr, s.fractal_color, s.dem_color, s.special_color, s.special_use_palette_color, s.globalIncrementBypass);

    }

    public void displaySpecialHelp() {

        common.displaySpecialHelp(ptr);

    }

    public void fractalColorsChanged(Color max_it_color, Color dem_color, Color special_color, boolean use_palette_color, boolean special_bypass) {

        s.fractal_color = max_it_color;
        s.dem_color = dem_color;
        s.special_color = special_color;
        s.special_use_palette_color = use_palette_color;
        ColorAlgorithm.GlobalIncrementBypass = s.globalIncrementBypass = special_bypass;

        if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        }

        if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        }

        updateMaxIterColorPreview();

        updateColors();
    }

    public void setIterationsPost() {

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setSkipBailoutIterations() {

        resetOrbit();
        new SkipBailoutDialog(ptr, s);

    }

    public void setSkipBailoutIterationsPost() {

        SkipBailoutCondition.SKIPPED_ITERATION_COUNT = s.fns.skip_bailout_iterations;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setIterations() {

        resetOrbit();
        new IterationDialog(ptr, s);

    }

    public void setHeightRatioPost() {
        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
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

    public void setThreadsNumberPost(int n) {
        this.n = n;
        threads = new ThreadDraw[n][n];
    }

    public void setThreadsNumber() {

        resetOrbit();
        new ThreadsDialog(ptr, n);

    }

    public void chooseDirectPalette(int temp, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            resetOrbit();
            options_menu.getOutColoringPalette()[s.ps.color_choice].setSelected(true); // reselect the old palette

            int[] res = PaletteMenu.choosePaletteFiler(ptr);
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

            int[] res = PaletteMenu.choosePaletteFiler(ptr);
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
                ThreadDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            } else {
                ThreadDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            }

            s.ps2.color_choice = temp2;
            if (s.ps2.color_choice != CUSTOM_PALETTE_ID) {
                s.ps2.color_cycling_location = 0;
            }

            options_menu.getInColoringPalette()[s.ps2.color_choice].setSelected(true);

            if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
                ThreadDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            } else {
                ThreadDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
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
                ThreadDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            } else {
                ThreadDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
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
                ThreadDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            } else {
                ThreadDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
            }
        }

        updateColorPalettesMenu();
        updateMaxIterColorPreview();

        updateColors();

    }

    public void setFilter(int temp) {

        resetOrbit();
        if (temp == ANTIALIASING) {
            if (!filters_opt[ANTIALIASING].isSelected()) {
                s.fs.filters[ANTIALIASING] = false;

                setOptions(false);

                progress.setValue(0);

                resetImage();

                whole_image_done = false;

                if (s.d3s.d3) {
                    Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
                }

                if (s.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn) {
                    if (julia_map) {
                        createThreadsJuliaMap();
                    } else if (s.d3s.d3) {
                        createThreadsPaletteAndFilter3DModel();
                    } else {
                        createThreads(false);
                    }

                    calculation_time = System.currentTimeMillis();

                    if (julia_map) {
                        startThreads(julia_grid_first_dimension);
                    } else {
                        startThreads(n);
                    }
                } else {
                    if (s.d3s.d3 || s.ds.domain_coloring) {
                        createThreads(false);
                    } else {
                        createThreadsPaletteAndFilter();
                    }

                    calculation_time = System.currentTimeMillis();

                    startThreads(n);
                }
            } else {
                s.fs.filters[ANTIALIASING] = true;

                setOptions(false);

                progress.setValue(0);

                resetImage();

                whole_image_done = false;

                if (s.d3s.d3) {
                    Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
                }

                if (julia_map) {
                    createThreadsJuliaMap();
                } else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if (julia_map) {
                    startThreads(julia_grid_first_dimension);
                } else {
                    startThreads(n);
                }

            }
        } else {
            if (!filters_opt[temp].isSelected()) {
                s.fs.filters[temp] = false;
            } else {
                s.fs.filters[temp] = true;
            }

            setOptions(false);

            progress.setValue(0);

            resetImage();

            whole_image_done = false;

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            if (s.fs.filters[ANTIALIASING] || s.ots.useTraps || s.ds.domain_coloring || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn) {
                if (julia_map) {
                    createThreadsJuliaMap();
                } else if (s.d3s.d3) {
                    createThreadsPaletteAndFilter3DModel();
                } else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if (julia_map) {
                    startThreads(julia_grid_first_dimension);
                } else {
                    startThreads(n);
                }
            } else {
                if (s.d3s.d3) {
                    createThreadsPaletteAndFilter3DModel();
                } else {
                    createThreadsPaletteAndFilter();
                }

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
        }
    }

    public void setGrid() {

        resetOrbit();
        if (!tools_menu.getGrid().isSelected()) {
            grid = false;
            old_grid = grid;
            if (!zoom_window && !julia_map && !orbit && !boundaries && !s.useDirectColor) {
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
            if (!zoom_window && !julia_map && !orbit && !grid && !s.useDirectColor) {
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
            if (!s.ds.domain_coloring && s.functionSupportsC() && !s.fns.init_val && !s.fns.perturbation) {
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
            periodicity_checking = false;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
            if (!s.useDirectColor) {
                options_menu.getOrbitTraps().setEnabled(true);
                options_menu.getInTrueColoring().setEnabled(true);
            }
            main_panel.repaint();
        } else {
            periodicity_checking = true;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(false);
            options_menu.getOrbitTraps().setEnabled(false);
            options_menu.getInTrueColoring().setEnabled(false);
            main_panel.repaint();
        }

    }

    private void selectPointFractal(MouseEvent e) {

        resetOrbit();

        if (!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = s.size * 0.5;
        double size_2_y = (s.size * s.height_ratio) * 0.5;
        double temp_size_image_size_x = s.size / image_size;
        double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

        s.xCenter = s.xCenter - size_2_x + temp_size_image_size_x * curX;
        s.yCenter = s.yCenter + size_2_y - temp_size_image_size_y * curY;

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                s.size /= zoom_factor;
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                s.size *= zoom_factor;
                break;
            }
        }

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    private void scrollPoint(MouseWheelEvent e) {

        resetOrbit();

        if (!threadsAvailable() || ctrlKeyPressed || shiftKeyPressed || altKeyPressed) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = s.size * 0.5;
        double size_2_y = (s.size * s.height_ratio) * 0.5;
        double temp_size_image_size_x = s.size / image_size;
        double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

        s.xCenter = s.xCenter - size_2_x + temp_size_image_size_x * curX;
        s.yCenter = s.yCenter + size_2_y - temp_size_image_size_y * curY;

        int notches = e.getWheelRotation();
        if (notches < 0) {
            s.size /= zoom_factor;
        } else {
            s.size *= zoom_factor;
        }

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void dragPoint(MouseEvent e) {

        resetOrbit();

        if (!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double temp_size_image_size_x = s.size / image_size;
        double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

        s.xCenter -= temp_size_image_size_x * (curX - oldDragX);
        s.yCenter += temp_size_image_size_y * (curY - oldDragY);

        oldDragX = curX;
        oldDragY = curY;

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointForPlane(MouseEvent e) {

        resetOrbit();

        if (!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.ALT_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double size_2_x = s.size * 0.5;
        double size_2_y = (s.size * s.height_ratio) * 0.5;
        double temp_size_image_size_x = s.size / image_size;
        double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

        double temp_x = s.xCenter - size_2_x + temp_size_image_size_x * curX;
        double temp_y = s.yCenter + size_2_y - temp_size_image_size_y * curY;

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_x, temp_y, s.fns.rotation_vals, s.fns.rotation_center);

        s.fns.plane_transform_center[0] = p.x;
        s.fns.plane_transform_center[1] = p.y;

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void rotatePoint(MouseEvent e) {

        resetOrbit();

        if (!threadsAvailable() || e.getModifiers() != (InputEvent.BUTTON1_MASK | InputEvent.SHIFT_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        double new_angle = MathUtils.angleBetween2PointsDegrees(curX, curY, image_size / 2, image_size / 2);
        double old_angle = MathUtils.angleBetween2PointsDegrees(oldDragX, oldDragY, image_size / 2, image_size / 2);
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

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.fns.rotation_vals, s.fns.rotation_center);

        s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
        s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));

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

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void scrollPointPolar(MouseWheelEvent e) {

        resetOrbit();

        if (!threadsAvailable()) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        int notches = e.getWheelRotation();
        if (notches < 0) {
            s.size /= zoom_factor;
        } else {
            s.size *= zoom_factor;
        }

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreads(true);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointJulia(MouseEvent e) {

        resetOrbit();

        if (!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        setOptions(false);

        if (first_seed) {
            if (e.getModifiers() == InputEvent.BUTTON1_MASK) {

                int x1 = (int) curX;
                int y1 = (int) curY;

                if (s.polar_projection) {
                    double start;
                    double center = Math.log(s.size);

                    double f, sf, cf, r;
                    double muly = (2 * s.circle_period * Math.PI) / image_size;

                    double mulx = muly * s.height_ratio;

                    start = center - mulx * image_size * 0.5;

                    f = y1 * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x1 * mulx + start);

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.fns.rotation_vals, s.fns.rotation_center);

                    s.xJuliaCenter = p.x;
                    s.yJuliaCenter = p.y;
                } else {
                    double size_2_x = s.size * 0.5;
                    double size_2_y = (s.size * s.height_ratio) * 0.5;
                    double temp_size_image_size_x = s.size / image_size;
                    double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter - size_2_x + temp_size_image_size_x * x1, s.yCenter + size_2_y - temp_size_image_size_y * y1, s.fns.rotation_vals, s.fns.rotation_center);

                    s.xJuliaCenter = p.x;
                    s.yJuliaCenter = p.y;
                }

                first_seed = false;

                main_panel.repaint();

                defaultFractalSettings();
                return;
            }
        } else {
            double size_2_x = s.size * 0.5;
            double size_2_y = (s.size * s.height_ratio) * 0.5;
            double temp_size_image_size_x = s.size / image_size;
            double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

            s.xCenter = s.xCenter - size_2_x + temp_size_image_size_x * curX;
            s.yCenter = s.yCenter + size_2_y - temp_size_image_size_y * curY;

            switch (e.getModifiers()) {
                case InputEvent.BUTTON1_MASK: {
                    s.size /= zoom_factor;
                    break;
                }
                case InputEvent.BUTTON3_MASK: {
                    s.size *= zoom_factor;
                    break;
                }
            }

            if (s.size < 0.05) {
                tools_menu.getBoundaries().setEnabled(false);
                boundaries = false;
                tools_menu.getBoundaries().setSelected(false);
            }

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        }

    }

    private void selectPointPolar(MouseEvent e) {
        resetOrbit();
        if (!threadsAvailable() || (e.getModifiers() != InputEvent.BUTTON1_MASK && e.getModifiers() != InputEvent.BUTTON3_MASK)) {
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

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        switch (e.getModifiers()) {
            case InputEvent.BUTTON1_MASK: {
                s.size /= zoom_factor;
                break;
            }
            case InputEvent.BUTTON3_MASK: {
                s.size *= zoom_factor;
                break;
            }
        }

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
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
                defaultFractalSettings();
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
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
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
            if (!s.ds.domain_coloring && s.functionSupportsC() && !s.fns.init_val && !s.fns.perturbation) {
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

        if (!threadsAvailable() || e.getModifiers() != InputEvent.BUTTON1_MASK) {
            return;
        }

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

        setOptions(false);

        //progress.setValue(0);
        resetImage();

        Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());

        whole_image_done = false;

        createThreadsRotate3DModel();

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    private void selectPointOrbit(MouseEvent e) {

        if (!threadsAvailable() || (pixels_orbit != null && pixels_orbit.isAlive())) {
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

        if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
            try {
                pixels_orbit = new DrawOrbit(s, x1, y1, image_size, ptr, orbit_color, orbit_style, show_orbit_converging_point);
                pixels_orbit.start();
            } catch (ParserException ex) {
                JOptionPane.showMessageDialog(scroll_pane, ex.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            } catch (Exception ex) {
            }
        }

        if (s.polar_projection) {
            try {
                double start;
                double center = Math.log(s.size);

                double f, sf, cf, r;
                double muly = (2 * s.circle_period * Math.PI) / image_size;

                double mulx = muly * s.height_ratio;

                start = center - mulx * image_size * 0.5;

                f = y1 * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x1 * mulx + start);

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + p.x);

                statusbar.getImaginary().setText("" + p.y);
            } catch (NullPointerException ex) {
            }
        } else {
            try {
                double size_2_x = s.size * 0.5;
                double size_2_y = (s.size * s.height_ratio) * 0.5;
                double temp_xcenter_size = s.xCenter - size_2_x;
                double temp_ycenter_size = s.yCenter + size_2_y;
                double temp_size_image_size_x = s.size / image_size;
                double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.fns.rotation_vals, s.fns.rotation_center);

                statusbar.getReal().setText("" + p.x);

                statusbar.getImaginary().setText("" + p.y);
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
    
    
     scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
     scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
    
    
     resetImage();
    
     whole_image_done = false;
    
     createThreads(false);
    
     calculation_time = System.currentTimeMillis();
    
     startThreads(n);
    
     } while( true);
     }
     }.start();  
     }*/
    public void zoomIn() {

        resetOrbit();
        s.size /= zoom_factor;

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void zoomOut() {

        resetOrbit();
        s.size *= zoom_factor;

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void moveTo(int where) {

        resetOrbit();
        if (where == UP) {
            if (s.polar_projection) {
                s.fns.rotation -= s.circle_period * 360;

                if (s.fns.rotation < -360) {
                    s.fns.rotation = ((int) (-s.fns.rotation / 360) + 1) * 360 + s.fns.rotation;
                }

                s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
                s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));
            } else {
                s.yCenter += (s.size * s.height_ratio);
            }
        } else if (where == DOWN) {
            if (s.polar_projection) {
                s.fns.rotation += s.circle_period * 360;

                if (s.fns.rotation > 360) {
                    s.fns.rotation = ((int) (-s.fns.rotation / 360) - 1) * 360 + s.fns.rotation;
                }

                s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
                s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));
            } else {
                s.yCenter -= (s.size * s.height_ratio);
            }
        } else if (where == LEFT) {

            if (s.polar_projection) {
                double start;
                double end = Math.log(s.size);

                double muly = (2 * s.circle_period * Math.PI) / image_size;

                double mulx = muly * s.height_ratio;

                start = -mulx * image_size + end;

                s.size = Math.exp(start);
            } else {
                s.xCenter -= s.size;
            }
        } else if (s.polar_projection) {
            double start = Math.log(s.size);
            double end;

            double muly = (2 * s.circle_period * Math.PI) / image_size;

            double mulx = muly * s.height_ratio;

            end = mulx * image_size + start;

            s.size = Math.exp(end);
        } else {
            s.xCenter += s.size;
        }

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setOptions(Boolean option) {

        file_menu.getStartingPosition().setEnabled(option);
        toolbar.getStartingPositionButton().setEnabled(option);

        file_menu.getSaveSettings().setEnabled(option);
        file_menu.getLoadSettings().setEnabled(option);
        file_menu.getSaveImage().setEnabled(option);
        file_menu.getSaveImageAndSettings().setEnabled(option);
        file_menu.getSetInitialSettings().setEnabled(option);

        file_menu.getCodeEditor().setEnabled(option);
        file_menu.getCompileCode().setEnabled(option);

        if ((!s.fns.julia || !first_seed)) {
            file_menu.getGoTo().setEnabled(option);
            toolbar.getGoTo().setEnabled(option);
        }

        options_menu.getFractalFunctionsMenu().setEnabled(option);

        options_menu.getColorsMenu().setEnabled(option);

        file_menu.getDefaults().setEnabled(option);

        file_menu.getRepaint().setEnabled(option);

        options_menu.getIterationsMenu().setEnabled(option);
        options_menu.getSizeOfImage().setEnabled(option);

        options_menu.getHeightRatio().setEnabled(option);

        if ((!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1)) && !s.useDirectColor) {
            toolbar.getOutCustomPaletteButton().setEnabled(option);
            toolbar.getRandomPaletteButton().setEnabled(option);
        }

        toolbar.getIterationsButton().setEnabled(option);
        toolbar.getRotationButton().setEnabled(option);
        toolbar.getSaveImageButton().setEnabled(option);
        toolbar.getSaveImageAndSettignsButton().setEnabled(option);
        options_menu.getPoint().setEnabled(option);

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN) {
            options_menu.getBailout().setEnabled(option);
            options_menu.getBailoutConditionMenu().setEnabled(option);
        }

        options_menu.getOptimizationsMenu().setEnabled(option);
        options_menu.getToolsOptionsMenu().setEnabled(option);

        if (!s.d3s.d3 && (s.fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM || s.fns.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) && !s.ds.domain_coloring) {
            options_menu.getDirectColor().setEnabled(option);
        }

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.ots.useTraps && !s.fns.tcs.trueColorIn) {
            options_menu.getPeriodicityChecking().setEnabled(option);
        }

        if (!s.ds.domain_coloring && !s.d3s.d3 && !julia_map) {
            options_menu.getGreedyAlgorithm().setEnabled(option);
        }

        if (((!s.fns.julia && !orbit) || (!first_seed && !orbit)) && !s.ds.domain_coloring && !zoom_window && !julia_map && !s.d3s.d3 && !s.fns.perturbation && !s.fns.init_val && s.functionSupportsC()) {
            tools_menu.getJulia().setEnabled(option);
            toolbar.getJuliaButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !color_cycling && !s.d3s.d3 && !s.ots.useTraps && !s.useDirectColor && !s.fns.tcs.trueColorOut && !s.fns.tcs.trueColorIn) {
            tools_menu.getColorCycling().setEnabled(option);
            toolbar.getColorCyclingButton().setEnabled(option);
        }

        if (!julia_map && !s.useDirectColor) {
            tools_menu.getDomainColoring().setEnabled(option);
            toolbar.getDomainColoringButton().setEnabled(option);
        }

        tools_menu.getPlaneVizualization().setEnabled(option);

        if (!s.ds.domain_coloring) {
            if (s.fns.function == MANDELBROT) {
                options_menu.getDistanceEstimation().setEnabled(option);
            }

            options_menu.getContourColoring().setEnabled(option);

            options_menu.getFakeDistanceEstimation().setEnabled(option);

            options_menu.getEntropyColoring().setEnabled(option);

            options_menu.getGreyScaleColoring().setEnabled(option);

            options_menu.getRainbowPalette().setEnabled(option);

            options_menu.getOffsetColoring().setEnabled(option);

            if (!periodicity_checking) {
                options_menu.getOrbitTraps().setEnabled(option);
            }

            options_menu.getOutColoringMenu().setEnabled(option);
            options_menu.getInColoringMenu().setEnabled(option);
            if (!s.useDirectColor) {
                options_menu.getFractalColor().setEnabled(option);
                options_menu.getStatisticsColoring().setEnabled(option);
                options_menu.getInColoringPaletteMenu().setEnabled(option);
                options_menu.getPaletteGradientMerging().setEnabled(option);
                toolbar.getInCustomPaletteButton().setEnabled(option);
                options_menu.getOutTrueColoring().setEnabled(option);

                if (!periodicity_checking) {
                    options_menu.getInTrueColoring().setEnabled(option);
                }
            }
        }

        if ((!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1)) && !s.useDirectColor) {
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

        if ((s.fns.rotation == 0 || s.fns.rotation == 360 || s.fns.rotation == -360) && !s.d3s.d3 && s.size >= 0.05) {
            tools_menu.getBoundaries().setEnabled(option);
        }

        if (!s.d3s.d3 && !orbit && !julia_map && !s.polar_projection) {
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

        if (!julia_map && !s.d3s.d3 && !zoom_window) {
            tools_menu.getOrbit().setEnabled(option);
            toolbar.getOrbitButton().setEnabled(option);
        }
        options_menu.getPlanesMenu().setEnabled(option);

        if (!s.ds.domain_coloring && !zoom_window && !s.fns.julia && !s.fns.perturbation && !s.fns.init_val && !orbit && !s.d3s.d3 && s.functionSupportsC()) {
            tools_menu.getJuliaMap().setEnabled(option);
            toolbar.getJuliaMapButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !julia_map && !grid && !boundaries && !s.useDirectColor) {
            tools_menu.get3D().setEnabled(option);
            toolbar.get3DButton().setEnabled(option);
        }

        options_menu.getRotationMenu().setEnabled(option);

        if (!s.fns.julia && !julia_map && s.functionSupportsC()) {
            options_menu.getPerturbation().setEnabled(option);
            options_menu.getInitialValue().setEnabled(option);
        }

        options_menu.getOverview().setEnabled(option);
        infobar.getOverview().setEnabled(option);

        if (!s.useDirectColor) {
            options_menu.getGradient().setEnabled(option);
            options_menu.getColorBlending().setEnabled(option);
        }

        infobar.setListenersEnabled(option);

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

        new ColorChooserFrame(ptr, "Orbit Color", orbit_color, 0);

        main_panel.repaint();

    }

    public void setGridColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Grid Color", grid_color, 1);

        main_panel.repaint();

    }

    public void setZoomWindowColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Zoom Window Color", zoom_window_color, 2);

        main_panel.repaint();

    }

    public void setBoundariesColor() {

        resetOrbit();

        new ColorChooserFrame(ptr, "Boundaries Color", boundaries_color, 3);

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
        boolean wasMagneticPendulumType = s.fns.function == MAGNETIC_PENDULUM;
        boolean wasSimpleType = !wasConvergingType && !wasMagnetType && !wasMagneticPendulumType;

        resetOrbit();
        int oldSelected = s.fns.function;
        s.fns.function = temp;

        switch (s.fns.function) {
            case LYAPUNOV:
                new LyapunovDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case MAGNETIC_PENDULUM:
                new MagneticPendulumDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case MANDELBROTNTH:
                new MandelbrotNthDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case MANDELBROTWTH:
                new MandelbrotWthDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
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
            case PARHALLEY3:
            case PARHALLEY4:
            case PARHALLEYGENERALIZED3:
            case PARHALLEYGENERALIZED8:
            case PARHALLEYSIN:
            case PARHALLEYCOS:
            case LAGUERRE3:
            case LAGUERRE4:
            case LAGUERREGENERALIZED3:
            case LAGUERREGENERALIZED8:
            case LAGUERRESIN:
            case LAGUERRECOS:
            case DURAND_KERNER3:
            case DURAND_KERNER4:
            case DURAND_KERNERGENERALIZED3:
            case DURAND_KERNERGENERALIZED8:
            case BAIRSTOW3:
            case BAIRSTOW4:
            case BAIRSTOWGENERALIZED3:
            case BAIRSTOWGENERALIZED8:
            case LAMBERT_W_VARIATION:
            case NEWTON_HINES3:
            case NEWTON_HINES4:
            case NEWTON_HINESGENERALIZED3:
            case NEWTON_HINESGENERALIZED8:
            case NEWTON_HINESSIN:
            case NEWTON_HINESCOS:
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
            case PARHALLEYPOLY:
            case LAGUERREPOLY:
            case DURAND_KERNERPOLY:
            case BAIRSTOWPOLY:
            case NEWTON_HINESPOLY:
                new PolynomialDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case GENERIC_CaZbdZe:
                new GenericCaZbdZeDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case GENERIC_CpAZpBC:
                new GenericCpAZpBCDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case NEWTONFORMULA:
            case NEWTON_HINESFORMULA:
                new RootFindingTwoFunctionsDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case HALLEYFORMULA:
            case SCHRODERFORMULA:
            case HOUSEHOLDERFORMULA:
            case PARHALLEYFORMULA:
                new RootFindingThreeFunctionsDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case SECANTFORMULA:
            case STEFFENSENFORMULA:
                new RootFindingOneFunctionDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case MULLERFORMULA:
                new MullerFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case LAGUERREFORMULA:
                new LaguerreFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case NOVA:
                new NovaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case KLEINIAN:
                new KleinianDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case INERTIA_GRAVITY:
                new InertiaGravityDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case SIERPINSKI_GASKET:
                setInertiaGravitySierpinskiPost();
                break;
            case USER_FORMULA_ITERATION_BASED:
                new UserFormulaIterationBasedDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case USER_FORMULA_CONDITIONAL:
                new UserFormulaConditionalDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case LAMBDA_FN_FN:
                new LambdaFnFnDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case MANDELBROT:
                if (!s.ds.domain_coloring) {
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
                break;
            case USER_FORMULA:
                new UserFormulaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case USER_FORMULA_COUPLED:
                new UserFormulaCoupledDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            case USER_FORMULA_NOVA:
                new UserFormulaNovaDialog(ptr, s, oldSelected, fractal_functions, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);
                return;
            default:
                optionsEnableShortcut();
                break;
        }

        setFunctionPost(wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType);

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
    }

    public void setFunctionPost(boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType) {

        boolean isConvergingType = s.isConvergingType();
        boolean isMagnetType = s.isMagnetType();
        boolean isMagneticPendulumType = s.fns.function == MAGNETIC_PENDULUM;
        boolean isSimpleType = !isConvergingType && !isMagnetType && !isMagneticPendulumType;

        if (isConvergingType != wasConvergingType || isMagnetType != wasMagnetType || isSimpleType != wasSimpleType || isMagneticPendulumType != wasMagneticPendulumType) {
            s.resetUserOutColoringFormulas();
            s.resetStatisticalColoringFormulas();
        }

        if (s.fns.function != MANDELBROT && s.sts.statistic_type == TRIANGLE_INEQUALITY_AVERAGE) {
            s.sts.statistic_type = STRIPE_AVERAGE;
        }

        if (s.isConvergingType()) {
            s.sts.statistic_type = COS_ARG_DIVIDE_INVERSE_NORM;
        } else if (s.sts.statistic_type == COS_ARG_DIVIDE_INVERSE_NORM && !s.isMagnetType()) {
            s.sts.statistic_type = STRIPE_AVERAGE;
        }

        defaultFractalSettings();

    }

    public void setBailoutPost() {
        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
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

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fns.rotation != 0 && s.fns.rotation != 360 && s.fns.rotation != -360) {
            tools_menu.getGrid().setEnabled(false);
            grid = false;
            tools_menu.getGrid().setSelected(false);

            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void increaseRotation() {

        resetOrbit();
        if (s.fns.rotation == 360) {
            s.fns.rotation = 0;
        }
        s.fns.rotation++;

        s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
        s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));

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

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void decreaseRotation() {

        resetOrbit();
        if (s.fns.rotation == -360) {
            s.fns.rotation = 0;
        }
        s.fns.rotation--;

        s.fns.rotation_vals[0] = Math.cos(Math.toRadians(s.fns.rotation));
        s.fns.rotation_vals[1] = Math.sin(Math.toRadians(s.fns.rotation));

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

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setBurningShip() {

        resetOrbit();
        if (!options_menu.getBurningShipOpt().isSelected()) {
            s.fns.burning_ship = false;
        } else {
            s.fns.burning_ship = true;
        }

        if (s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    public void setMandelGrass() {

        resetOrbit();
        if (!options_menu.getMandelGrassOpt().isSelected()) {
            s.fns.mandel_grass = false;

            if (s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) {
                defaultFractalSettings();
            }
        } else {
            new MandelgrassDialog(ptr, s, options_menu.getMandelGrassOpt());
        }

    }

    public void startingPosition() {

        resetOrbit();
        setOptions(false);

        s.startingPosition();

        if (s.size < 0.05) {
            tools_menu.getBoundaries().setEnabled(false);
            boundaries = false;
            tools_menu.getBoundaries().setSelected(false);
        }

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
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
            ThreadDraw.setArrays(image_size, s.ds.domain_coloring);
        }

        main_panel.setPreferredSize(new Dimension(image_size, image_size));

        setOptions(false);

        if (!s.d3s.d3) {
            progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));
        }

        progress.setValue(0);

        SwingUtilities.updateComponentTreeUI(this);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        //last_used = null;
        //last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        //Graphics2D graphics = last_used.createGraphics();
        //graphics.drawImage(image, 0, 0, image_size, image_size, null);
        last_used = null;

        image = null;

        clearThreads();

        System.gc();

        last_used = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

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
            fast_julia_filters = false;
            main_panel.repaint();
        } else {
            fast_julia_filters = true;
            main_panel.repaint();
        }

    }

    private void fastJulia() {

        resetOrbit();

        if (!threadsAvailable()) {
            return;
        }

        double temp_xCenter, temp_yCenter, temp_size, temp_xJuliaCenter, temp_yJuliaCenter;

        int temp_max_iterations = s.max_iterations > 250 ? 250 : s.max_iterations;

        int x1, y1;

        try {
            x1 = (int) main_panel.getMousePosition().getX();
            y1 = (int) main_panel.getMousePosition().getY();
        } catch (Exception ex) {
            return;
        }

        if (s.polar_projection) {
            double start;
            double center = Math.log(s.size);

            double f, sf, cf, r;
            double muly = (2 * s.circle_period * Math.PI) / image_size;

            double mulx = muly * s.height_ratio;

            start = center - mulx * image_size * 0.5;

            f = y1 * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);

            r = Math.exp(x1 * mulx + start);

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.fns.rotation_vals, s.fns.rotation_center);

            temp_xJuliaCenter = p.x;

            temp_yJuliaCenter = p.y;
        } else {
            double size_2_x = s.size * 0.5;
            double size_2_y = (s.size * s.height_ratio) * 0.5;
            double temp_xcenter_size = s.xCenter - size_2_x;
            double temp_ycenter_size = s.yCenter + size_2_y;
            double temp_size_image_size_x = s.size / image_size;
            double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.fns.rotation_vals, s.fns.rotation_center);

            temp_xJuliaCenter = p.x;
            temp_yJuliaCenter = p.y;

        }

        Arrays.fill(((DataBufferInt) fast_julia_image.getRaster().getDataBuffer()).getData(), 0);

        switch (s.fns.function) {
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
        Parser.usesUserCode = false;

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (greedy_algorithm) {
                        if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                            threads[i][j] = new BoundaryTracingDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, temp_xJuliaCenter, temp_yJuliaCenter);
                        } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                            threads[i][j] = new DivideAndConquerDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, temp_xJuliaCenter, temp_yJuliaCenter);
                        }
                    } else {
                        threads[i][j] = new BruteForceDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.fs, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset, temp_xJuliaCenter, temp_yJuliaCenter);
                    }
                    threads[i][j].setThreadId(i * n + j);
                }
            }
        } catch (ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }

        startThreads(n);

    }

    public void setColorCycling() {

        resetOrbit();
        if (!tools_menu.getColorCycling().isSelected()) {

            color_cycling = false;
            toolbar.getColorCyclingButton().setSelected(false);

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    threads[i][j].terminateColorCycling();
                }
            }

            try {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        threads[i][j].join();
                    }
                }
            } catch (InterruptedException ex) {
            }

            s.ps.color_cycling_location = threads[0][0].getColorCyclingLocationOutColoring();
            s.ps2.color_cycling_location = threads[0][0].getColorCyclingLocationInColoring();
            s.bms = new BumpMapSettings(threads[0][0].getBumpMapSettings());
            s.ls = new LightSettings(threads[0][0].getLightSettings());
            s.gs.gradient_offset = threads[0][0].getGradientOffset();

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

                resetImage();

                whole_image_done = false;

                if (julia_map) {
                    createThreadsJuliaMap();
                } else {
                    createThreads(false);
                }

                calculation_time = System.currentTimeMillis();

                if (julia_map) {
                    startThreads(julia_grid_first_dimension);
                } else {
                    startThreads(n);
                }
            } else {
                //last_used = null;
                setOptions(true);
            }
        } else {
            color_cycling = true;
            toolbar.getColorCyclingButton().setSelected(true);

            setOptions(false);

            whole_image_done = false;

            createThreadsColorCycling();

            startThreads(n);
        }

    }

    private void createThreadsPaletteAndFilter() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.fs, s.bms, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.fdes, s.rps, s.ens, s.ofs, s.gss, s.color_blending, s.cns, s.post_processing_order, s.ls, s.pbs, s.ots, s.ds, s.gs.gradient_offset);
                threads[i][j].setThreadId(i * n + j);
            }
        }
    }

    private void createThreadsColorCycling() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.max_iterations, ptr, s.fractal_color, s.dem_color, image, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.bms, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.fdes, s.rps, color_cycling_speed, s.fs, s.ens, s.ofs, s.gss, s.color_blending, s.cns, s.post_processing_order, s.ls, s.pbs, s.ots, cycle_colors, cycle_lights, cycle_gradient, s.ds, s.gs.gradient_offset);
                threads[i][j].setThreadId(i * n + j);
            }
        }
    }

    private void createThreadsRotate3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.d3s, true, ptr, image, s.fs, s.color_blending);
                threads[i][j].setThreadId(i * n + j);
            }
        }

    }

    private void createThreadsPaletteAndFilter3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.d3s, false, ptr, image, s.fs, s.color_blending);
                threads[i][j].setThreadId(i * n + j);
            }
        }

    }

    public void shiftPalettePost() {
        updateColorPalettesMenu();
        updateColors();
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
        } else {
            defaultFractalSettings();
        }
    }

    public void setBailoutTest(int temp) {

        resetOrbit();
        int oldSelection = s.fns.bailout_test_algorithm;
        s.fns.bailout_test_algorithm = temp;

        if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {
            new BailoutNNormDialog(ptr, s, oldSelection, bailout_tests);
            return;
        } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
            new BailoutConditionDialog(ptr, s, oldSelection, bailout_tests);
            return;
        }

        setBailoutTestPost();

    }

    public void setBailoutTestPost() {

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void resetFunctions() {

        for (int k = 0; k < fractal_functions.length; k++) {
            if (k != PARHALLEY3 && k != PARHALLEY4 && k != PARHALLEYGENERALIZED3 && k != PARHALLEYGENERALIZED8 && k != PARHALLEYSIN && k != PARHALLEYCOS && k != PARHALLEYPOLY && k != PARHALLEYFORMULA
                    && k != BAIRSTOW3 && k != BAIRSTOW4 && k != BAIRSTOWGENERALIZED3 && k != BAIRSTOWGENERALIZED8 && k != BAIRSTOWPOLY
                    && k != DURAND_KERNER3 && k != DURAND_KERNER4 && k != DURAND_KERNERGENERALIZED3 && k != DURAND_KERNERGENERALIZED8 && k != DURAND_KERNERPOLY
                    && k != LAGUERRE3 && k != LAGUERRE4 && k != LAGUERREGENERALIZED3 && k != LAGUERREGENERALIZED8 && k != LAGUERRESIN && k != LAGUERRECOS && k != LAGUERREPOLY && k != LAGUERREFORMULA
                    && k != NEWTON_HINES3 && k != NEWTON_HINES4 && k != NEWTON_HINESGENERALIZED3 && k != NEWTON_HINESGENERALIZED8 && k != NEWTON_HINESSIN && k != NEWTON_HINESCOS && k != NEWTON_HINESPOLY && k != NEWTON_HINESFORMULA
                    && k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                    && k != KLEINIAN && k != MAGNETIC_PENDULUM && k != INERTIA_GRAVITY && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                    && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                    && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                    && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                    && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                    && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                fractal_functions[k].setEnabled(true);
            }

            if ((k == KLEINIAN || k == INERTIA_GRAVITY || k == SIERPINSKI_GASKET || k == MAGNETIC_PENDULUM) && !s.fns.julia && !julia_map && !s.fns.perturbation && !s.fns.init_val) {
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

        if (s.fns.out_coloring_algorithm == DISTANCE_ESTIMATOR || s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else if (s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES || s.fns.out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || s.fns.out_coloring_algorithm == ESCAPE_TIME_GRID || s.fns.out_coloring_algorithm == BIOMORPH || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || s.fns.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || s.fns.out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || s.fns.out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);
        } else if (s.fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
            new OutColoringFormulaDialog(ptr, s, oldSelected, out_coloring_modes);
            return;
        } else if (!julia_map && !s.fns.julia && !s.fns.perturbation && !s.fns.init_val && !s.isRootFindingMethod()) {
            rootFindingMethodsSetEnabled(true);
        }

        setOutColoringModePost();

    }

    public void setUserOutColoringModePost() {
        if (!julia_map && !s.fns.julia && !s.fns.perturbation && !s.fns.init_val && !s.isRootFindingMethod()) {
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
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setInColoringMode(int temp) {

        resetOrbit();
        int oldSelected = s.fns.in_coloring_algorithm;

        s.fns.in_coloring_algorithm = temp;

        if (s.fns.in_coloring_algorithm == MAX_ITERATIONS) {
            if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && s.fns.function != SIERPINSKI_GASKET && s.fns.function != INERTIA_GRAVITY && !s.ots.useTraps && !s.fns.tcs.trueColorIn) {
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
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setSmoothingPost() {
        if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_outcoloring = new CustomPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_outcoloring = new PresetPalette(s.ps.color_choice, s.ps.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        }

        if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette_incoloring = new CustomPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        } else {
            ThreadDraw.palette_incoloring = new PresetPalette(s.ps2.color_choice, s.ps2.direct_palette, s.fns.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color).getRawPalette();
        }

        ThreadDraw.COLOR_SMOOTHING_METHOD = s.color_smoothing_method;

        updateColorPalettesMenu();
        options_menu.getProcessing().updateIcons(s);

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setSmoothing() {

        resetOrbit();
        new SmoothingDialog(ptr, s);

    }

    public void setBumpMap() {

        resetOrbit();
        new BumpMappingDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setRainbowPalette() {

        resetOrbit();
        new RainbowPaletteDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setFakeDistanceEstimation() {

        resetOrbit();
        new FakeDistanceEstimationDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setGreyScaleColoring() {

        resetOrbit();
        new GreyscaleColoringDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setEntropyColoring() {

        resetOrbit();
        new EntropyColoringDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setOffsetColoring() {

        resetOrbit();
        new OffsetColoringDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setExteriorDistanceEstimation() {

        resetOrbit();
        new ExteriorDistanceEstimationDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setExteriorDistanceEstimationPost() {

        if (s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else {
            resetFunctions();

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.julia && !julia_map && !s.fns.perturbation && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }
        }

        options_menu.getProcessing().updateIcons(s);

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setJuliaMapPost(int julia_grid_first_dimension) {
        this.julia_grid_first_dimension = julia_grid_first_dimension;
        threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

        toolbar.getJuliaMapButton().setSelected(true);

        options_menu.getJuliaMapOptions().setEnabled(true);

        setOptions(false);

        julia_map = true;

        rootFindingMethodsSetEnabled(false);
        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
        fractal_functions[KLEINIAN].setEnabled(false);
        fractal_functions[INERTIA_GRAVITY].setEnabled(false);

        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);

        options_menu.getGreedyAlgorithm().setEnabled(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreadsJuliaMap();

        calculation_time = System.currentTimeMillis();

        startThreads(julia_grid_first_dimension);
    }

    public void setJuliaMap() {

        resetOrbit();
        if (!tools_menu.getJuliaMap().isSelected()) {
            julia_map = false;
            toolbar.getJuliaMapButton().setSelected(false);

            options_menu.getJuliaMapOptions().setEnabled(false);

            setOptions(false);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if (s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }

            threads = new ThreadDraw[n][n];

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        } else {
            new JuliaMapDialog(ptr, julia_grid_first_dimension, toolbar.getJuliaMapButton(), tools_menu.getJuliaMap(), true);
        }

    }

    public void setJuliaMapOptionsPost(int julia_grid_first_dimension) {
        this.julia_grid_first_dimension = julia_grid_first_dimension;
        threads = new ThreadDraw[julia_grid_first_dimension][julia_grid_first_dimension];

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        whole_image_done = false;

        createThreadsJuliaMap();

        calculation_time = System.currentTimeMillis();

        startThreads(julia_grid_first_dimension);
    }

    public void setJuliaMapOptions() {

        resetOrbit();
        new JuliaMapDialog(ptr, julia_grid_first_dimension, toolbar.getJuliaMapButton(), tools_menu.getJuliaMap(), false);

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

        if (zoom_window_style == 0) {
            BasicStroke old_stroke = (BasicStroke) brush.getStroke();

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
        } else {
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

        if (old_polar_projection) {
            double start;
            double center = Math.log(old_size);
            double muly = (2 * s.circle_period * Math.PI) / image_size;

            double mulx = muly * old_height_ratio;

            start = center - mulx * image_size * 0.5;

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

                float dash1[] = {5.0f};
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
            double size_2_x = old_size * 0.5;
            double size_2_y = (old_size * old_height_ratio) * 0.5;
            double temp_xcenter_size = old_xCenter - size_2_x;
            double temp_ycenter_size = old_yCenter + size_2_y;
            double temp_size_image_size_x = old_size / image_size;
            double temp_size_image_size_y = (old_size * old_height_ratio) / image_size;

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

                    float dash1[] = {5.0f};
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
                        num = Math.pow(2, i - 1);
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

                    float dash1[] = {5.0f};
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
                        num = Math.pow(2, i - 1);
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

                    brush.drawString("" + s.fns.bailout, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);

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
        for (int i = 1; i < grid_tiles; i++) {
            brush.drawLine(i * image_size / grid_tiles, 0, i * image_size / grid_tiles, image_size);
            brush.drawLine(0, i * image_size / grid_tiles, image_size, i * image_size / grid_tiles);

            temp = temp_xcenter_size + temp_size_image_size_x * i * image_size / grid_tiles;
            temp2 = temp_ycenter_size - temp_size_image_size_y * i * image_size / grid_tiles;

            if (temp >= 1e-8) {
                temp = Math.floor(1000000000 * temp + 0.5) / 1000000000;
            } else if (Math.abs(temp) < 1e-15) {
                temp = 0;
            }

            if (temp2 >= 1e-8) {
                temp2 = Math.floor(1000000000 * temp2 + 0.5) / 1000000000;
            } else if (Math.abs(temp2) < 1e-15) {
                temp2 = 0;
            }

            if (mode) {
                brush.drawString("" + temp, i * image_size / grid_tiles + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                brush.drawString("" + temp2, 6 + scroll_pane.getHorizontalScrollBar().getValue(), i * image_size / grid_tiles + 12);
            } else {
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

    private void customPaletteEditor(final int palette_id, boolean outcoloring_mode) {

        if (outcoloring_mode) {
            resetOrbit();
            new CustomPaletteEditorFrame(ptr, options_menu.getOutColoringPalette(), s.fns.smoothing, palette_id, s.ps.color_choice, s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.temp_color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg, outcoloring_mode);
        } else {
            resetOrbit();
            new CustomPaletteEditorFrame(ptr, options_menu.getInColoringPalette(), s.fns.smoothing, palette_id, s.ps2.color_choice, s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.temp_color_cycling_location_second_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg, outcoloring_mode);
        }

    }

    public boolean getJuliaMap() {

        return julia_map;

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
            ptr.saveSettings();
            ptr.savePreferences();
            System.exit(0);
        } else if (ans == JOptionPane.NO_OPTION) {
            ptr.savePreferences();
            System.exit(0);
        }
    }

    private void createThreadsJuliaMap() {

        ThreadDraw.resetThreadData(julia_grid_first_dimension * julia_grid_first_dimension);
        Parser.usesUserCode = false;

        int n = julia_grid_first_dimension;

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.fns, ptr, s.fractal_color, s.dem_color, image, s.fs, periodicity_checking, s.ps.color_cycling_location, s.ps2.color_cycling_location, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.bms, s.polar_projection, s.circle_period, s.fdes, s.rps, s.inverse_dem, s.ps.color_intensity, s.ps.transfer_function, s.ps2.color_intensity, s.ps2.transfer_function, s.usePaletteForInColoring, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.cns, s.post_processing_order, s.ls, s.pbs, s.sts, s.gs.gradient_offset);
                    threads[i][j].setThreadId(i * n + j);
                }
            }
        } catch (ParserException e) {
            JOptionPane.showMessageDialog(scroll_pane, e.getMessage() + "\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }

    }

    public void increaseIterations() {

        resetOrbit();
        if (s.max_iterations >= 100000) {
            return;
        }
        s.max_iterations++;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

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

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

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

        updateColors();
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

        updateColors();
    }

    public void setUsePaletteForInColoring() {

        resetOrbit();

        if (!options_menu.getUsePaletteForInColoring().isSelected()) {
            s.usePaletteForInColoring = false;
            infobar.getInColoringPalettePreview().setVisible(false);
            infobar.getInColoringPalettePreviewLabel().setVisible(false);
            infobar.getMaxIterationsColorPreview().setVisible(true);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
        } else {
            s.usePaletteForInColoring = true;
            infobar.getInColoringPalettePreview().setVisible(true);
            infobar.getInColoringPalettePreviewLabel().setVisible(true);
            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
        }

        updateColors();
    }

    public void setPerturbation() {

        resetOrbit();
        if (!options_menu.getPerturbation().isSelected()) {
            s.fns.perturbation = false;

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.init_val && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        } else {
            new PerturbationDialog(ptr, s, options_menu.getPerturbation());
            return;
        }

        defaultFractalSettings();

    }

    public void setPerturbationPost() {
        s.fns.perturbation = true;
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        rootFindingMethodsSetEnabled(false);
        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
        fractal_functions[KLEINIAN].setEnabled(false);
        fractal_functions[INERTIA_GRAVITY].setEnabled(false);
    }

    public void setInitialValue() {

        resetOrbit();
        if (!options_menu.getInitialValue().isSelected()) {
            s.fns.init_val = false;

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.perturbation && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        } else {
            new InitialValueDialog(ptr, s, options_menu.getInitialValue());
            return;
        }

        defaultFractalSettings();

    }

    public void setInitialValuePost() {
        s.fns.init_val = true;
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        rootFindingMethodsSetEnabled(false);
        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
        fractal_functions[KLEINIAN].setEnabled(false);
        fractal_functions[INERTIA_GRAVITY].setEnabled(false);
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

    public void setDomainColoringButton() {

        resetOrbit();
        if (!toolbar.getDomainColoringButton().isSelected()) {
            toolbar.getDomainColoringButton().setSelected(true);
            tools_menu.getDomainColoring().setSelected(true);
        } else {
            toolbar.getDomainColoringButton().setSelected(false);
            tools_menu.getDomainColoring().setSelected(false);
        }

        setDomainColoring();

    }

    public void setPolarProjectionButton() {

        resetOrbit();
        if (!toolbar.getPolarProjectionButton().isSelected()) {
            toolbar.getPolarProjectionButton().setSelected(true);
            tools_menu.getPolarProjection().setSelected(true);
        } else {
            toolbar.getPolarProjectionButton().setSelected(false);
            tools_menu.getPolarProjection().setSelected(false);
        }

        setPolarProjection();

    }

    public void set3DButton() {

        resetOrbit();
        if (!toolbar.get3DButton().isSelected()) {
            toolbar.get3DButton().setSelected(true);
            tools_menu.get3D().setSelected(true);
        } else {
            toolbar.get3DButton().setSelected(false);
            tools_menu.get3D().setSelected(false);
        }

        set3DOption();

    }

    public void setJuliaMapButton() {

        resetOrbit();
        if (!toolbar.getJuliaMapButton().isSelected()) {
            toolbar.getJuliaMapButton().setSelected(true);
            tools_menu.getJuliaMap().setSelected(true);
        } else {
            toolbar.getJuliaMapButton().setSelected(false);
            tools_menu.getJuliaMap().setSelected(false);
        }

        setJuliaMap();

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
            File TempFile = File.createTempFile("temp", ".chm");
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
        CustomPaletteEditorFrame.randomPalette(s.ps.custom_palette, CustomPaletteEditorFrame.getRandomPaletteAlgorithm(), CustomPaletteEditorFrame.getEqualHues(), s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg);
        CustomPaletteEditorFrame.randomPalette(s.ps2.custom_palette, CustomPaletteEditorFrame.getRandomPaletteAlgorithm(), CustomPaletteEditorFrame.getEqualHues(), s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.color_cycling_location, s.ps2.scale_factor_palette_val, s.ps2.processing_alg);

        setPalette(CUSTOM_PALETTE_ID, null, 2);

    }

    public void setToolbar() {

        resetOrbit();
        if (!options_menu.getToolbar().isSelected()) {
            toolbar.setVisible(false);
        } else {
            toolbar.setVisible(true);
        }

    }

    public void setStatubar() {

        resetOrbit();
        if (!options_menu.getStatusbar().isSelected()) {
            statusbar.setVisible(false);
        } else {
            statusbar.setVisible(true);
        }

    }

    public void setInfobar() {

        resetOrbit();
        if (!options_menu.getInfobar().isSelected()) {
            infobar.setVisible(false);
        } else {
            infobar.setVisible(true);
        }

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

        return n * n;

    }

    public int getJuliaMapSlices() {

        return julia_grid_first_dimension * julia_grid_first_dimension;

    }

    public void filtersOptions() {

        resetOrbit();
        new FiltersOptionsFrame(ptr, s.fs.filters_options_vals, s.fs.filters_options_extra_vals, s.fs.filters_colors, s.fs.filters_extra_colors, filters_opt, s.fs.filters_order, s.fs.filters);

    }

    public void setPolarProjectionPost() {
        s.polar_projection = true;
        toolbar.getPolarProjectionButton().setSelected(true);

        tools_menu.getGrid().setEnabled(false);
        tools_menu.getZoomWindow().setEnabled(false);

        options_menu.getPolarProjectionOptions().setEnabled(true);

        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            s.d3s.fiX = 0.64;
            s.d3s.fiY = 0.82;
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setPolarProjection() {

        resetOrbit();
        if (!tools_menu.getPolarProjection().isSelected()) {
            s.polar_projection = false;
            toolbar.getPolarProjectionButton().setSelected(false);

            options_menu.getPolarProjectionOptions().setEnabled(false);

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            whole_image_done = false;

            if (s.d3s.d3) {
                s.d3s.fiX = 0.64;
                s.d3s.fiY = 0.82;
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            if (julia_map) {
                createThreadsJuliaMap();
            } else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if (julia_map) {
                startThreads(julia_grid_first_dimension);
            } else {
                startThreads(n);
            }
        } else {
            new PolarProjectionDialog(ptr, s, toolbar.getPolarProjectionButton(), tools_menu.getPolarProjection(), true);
        }
    }

    public void setPolarProjectionOptionsPost() {
        setOptions(false);

        progress.setValue(0);

        scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
        scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setPolarProjectionOptions() {
        resetOrbit();
        new PolarProjectionDialog(ptr, s, toolbar.getPolarProjectionButton(), tools_menu.getPolarProjection(), false);
    }

    public void setDomainColoringSettings() {
        try {
            toolbar.getDomainColoringButton().setSelected(true);

            options_menu.getDomainColoringOptions().setEnabled(true);

            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);

            if (s.ds.domain_coloring_mode != 1) {
                toolbar.getOutCustomPaletteButton().setEnabled(false);
                toolbar.getRandomPaletteButton().setEnabled(false);
                options_menu.getRandomPalette().setEnabled(false);
                options_menu.getOutColoringPaletteMenu().setEnabled(false);
            }

            options_menu.getDistanceEstimation().setEnabled(false);

            options_menu.getFakeDistanceEstimation().setEnabled(false);
            options_menu.getContourColoring().setEnabled(false);

            options_menu.getEntropyColoring().setEnabled(false);
            options_menu.getOffsetColoring().setEnabled(false);
            options_menu.getGreyScaleColoring().setEnabled(false);

            options_menu.getRainbowPalette().setEnabled(false);

            options_menu.getOrbitTraps().setEnabled(false);

            options_menu.getPaletteGradientMerging().setEnabled(false);
            options_menu.getInColoringPaletteMenu().setEnabled(false);
            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            toolbar.getInCustomPaletteButton().setEnabled(false);

            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
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

            infobar.getMaxIterationsColorPreview().setVisible(false);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

            updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);

            ThreadDraw.setDomainImageData(image_size, true);

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }
    }

    public void cancelDomainColoring() {

        tools_menu.getDomainColoring().setSelected(false);
        toolbar.getDomainColoringButton().setSelected(false);

    }

    public void setDomainColoring() {

        resetOrbit();
        if (!tools_menu.getDomainColoring().isSelected()) {
            s.ds.domain_coloring = false;

            ThreadDraw.setDomainImageData(image_size, false);

            toolbar.getDomainColoringButton().setSelected(false);
            options_menu.getDomainColoringOptions().setEnabled(false);

            if (s.usePaletteForInColoring) {
                infobar.getInColoringPalettePreview().setVisible(true);
                infobar.getInColoringPalettePreviewLabel().setVisible(true);
                infobar.getMaxIterationsColorPreview().setVisible(false);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
            } else {
                infobar.getMaxIterationsColorPreview().setVisible(true);
                infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                infobar.getInColoringPalettePreview().setVisible(false);
                infobar.getInColoringPalettePreviewLabel().setVisible(false);
            }

            updatePalettePreview(s.ps.color_cycling_location, s.ps2.color_cycling_location);

            setOptions(false);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            whole_image_done = false;

            if (s.d3s.d3) {
                s.d3s.fiX = 0.64;
                s.d3s.fiY = 0.82;
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);

        } else {

            new DomainColoringFrame(ptr, s, false);

        }
    }

    public void setDomainColoringOptions() {

        resetOrbit();
        new DomainColoringFrame(ptr, s, true);

    }

    public void set3DOption() {

        resetOrbit();
        if (!tools_menu.get3D().isSelected()) {
            try {
                s.d3s.d3 = false;
                setOptions(false);

                toolbar.get3DButton().setSelected(false);

                options_menu.get3DOptions().setEnabled(false);

                ThreadDraw.setArrays(image_size, s.ds.domain_coloring);

                progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));

                progress.setValue(0);

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                whole_image_done = false;

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            }

        } else {
            new D3Dialog(ptr, s, toolbar.get3DButton(), tools_menu.get3D(), true);
        }

    }

    public void set3DOptionPost() {
        try {
            options_menu.getDirectColor().setEnabled(false);
            options_menu.getDirectColor().setSelected(false);
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);

            setOptions(false);

            ThreadDraw.set3DArrays(s.d3s.detail);

            progress.setMaximum((s.d3s.detail * s.d3s.detail) + (s.d3s.detail * s.d3s.detail / 100));

            options_menu.getGreedyAlgorithm().setEnabled(false);

            options_menu.get3DOptions().setEnabled(true);

            s.d3s.d3 = true;

            toolbar.get3DButton().setSelected(true);

            progress.setValue(0);

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            resetImage();

            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }
    }

    public void set3DDetailsPost() {
        try {
            ThreadDraw.set3DArrays(s.d3s.detail);

            progress.setMaximum((s.d3s.detail * s.d3s.detail) + (s.d3s.detail * s.d3s.detail / 100));

            setOptions(false);

            progress.setValue(0);

            resetImage();

            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());

            whole_image_done = false;

            createThreads(false);

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }
    }

    public void set3DDetails() {

        resetOrbit();
        new D3Dialog(ptr, s, toolbar.get3DButton(), tools_menu.get3D(), false);

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

    private void rootFindingMethodsSetEnabled(boolean option) {

        fractal_functions[MAGNETIC_PENDULUM].setEnabled(option);
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
        fractal_functions[PARHALLEY3].setEnabled(option);
        fractal_functions[PARHALLEY4].setEnabled(option);
        fractal_functions[PARHALLEYGENERALIZED3].setEnabled(option);
        fractal_functions[PARHALLEYGENERALIZED8].setEnabled(option);
        fractal_functions[PARHALLEYSIN].setEnabled(option);
        fractal_functions[PARHALLEYCOS].setEnabled(option);
        fractal_functions[PARHALLEYPOLY].setEnabled(option);
        fractal_functions[PARHALLEYFORMULA].setEnabled(option);
        fractal_functions[LAGUERRE3].setEnabled(option);
        fractal_functions[LAGUERRE4].setEnabled(option);
        fractal_functions[LAGUERREGENERALIZED3].setEnabled(option);
        fractal_functions[LAGUERREGENERALIZED8].setEnabled(option);
        fractal_functions[LAGUERRESIN].setEnabled(option);
        fractal_functions[LAGUERRECOS].setEnabled(option);
        fractal_functions[LAGUERREPOLY].setEnabled(option);
        fractal_functions[LAGUERREFORMULA].setEnabled(option);
        fractal_functions[DURAND_KERNER3].setEnabled(option);
        fractal_functions[DURAND_KERNER4].setEnabled(option);
        fractal_functions[DURAND_KERNERGENERALIZED3].setEnabled(option);
        fractal_functions[DURAND_KERNERGENERALIZED8].setEnabled(option);
        fractal_functions[DURAND_KERNERPOLY].setEnabled(option);
        fractal_functions[BAIRSTOW3].setEnabled(option);
        fractal_functions[BAIRSTOW4].setEnabled(option);
        fractal_functions[BAIRSTOWGENERALIZED3].setEnabled(option);
        fractal_functions[BAIRSTOWGENERALIZED8].setEnabled(option);
        fractal_functions[BAIRSTOWPOLY].setEnabled(option);
        fractal_functions[NEWTON_HINES3].setEnabled(option);
        fractal_functions[NEWTON_HINES4].setEnabled(option);
        fractal_functions[NEWTON_HINESGENERALIZED3].setEnabled(option);
        fractal_functions[NEWTON_HINESGENERALIZED8].setEnabled(option);
        fractal_functions[NEWTON_HINESSIN].setEnabled(option);
        fractal_functions[NEWTON_HINESCOS].setEnabled(option);
        fractal_functions[NEWTON_HINESPOLY].setEnabled(option);
        fractal_functions[NEWTON_HINESFORMULA].setEnabled(option);
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
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));
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

    public void setColorCyclingOptionsPost(boolean cycle_colors, boolean cycle_gradient, boolean cycle_lights, int color_cycling_speed) {
        this.cycle_colors = cycle_colors;
        this.cycle_gradient = cycle_gradient;
        this.cycle_lights = cycle_lights;
        this.color_cycling_speed = color_cycling_speed;
    }

    public void setColorCyclingOptions() {

        resetOrbit();
        new ColorCyclingDialog(ptr, cycle_colors, cycle_gradient, cycle_lights, color_cycling_speed);

    }

    public void setApplyPlaneOnWholeJulia() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnWholeJuliaOpt().isSelected()) {
            s.fns.apply_plane_on_julia = false;
        } else {
            s.fns.apply_plane_on_julia = true;
        }

        if (s.fns.julia || julia_map) {
            defaultFractalSettings();
        }

    }

    public void setApplyPlaneOnJuliaSeed() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnJuliaSeedOpt().isSelected()) {
            s.fns.apply_plane_on_julia_seed = false;
        } else {
            s.fns.apply_plane_on_julia_seed = true;
        }

        if (s.fns.julia || julia_map) {
            defaultFractalSettings();
        }

    }

    public void Overview() {

        resetOrbit();
        try {
            common.overview(s);
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

        mx0 = (int) main_panel.getMousePosition().getX();
        my0 = (int) main_panel.getMousePosition().getY();
    }

    private void updateColorPalettesMenu() {

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
            {
                if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.temp_color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg); // temp color cycling loc
                } else {
                    c = PresetPalette.getPalette(i, 0);
                }
            }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (s.fns.smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
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
            {
                if (i < CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                } else if (i == CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.temp_color_cycling_location_second_palette, s.ps2.scale_factor_palette_val, s.ps2.processing_alg); // temp color cycling loc
                } else {
                    c = PresetPalette.getPalette(i, 0);
                }
            }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (s.fns.smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
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
            writer = new PrintWriter(new File("preferences.ini"));

            writer.println("#Fractal Zoomer preferences.");
            writer.println("#This file contains all the preferences of the user and it is updated,");
            writer.println("#every time the application terminates. Settings that wont have the");
            writer.println("#correct name/number of arguments/argument value, will be ignored");
            writer.println("#and the default values will be used instead.");

            writer.println();
            writer.println();

            writer.println("[Optimizations]");
            writer.println("thread_dim " + n);
            writer.println("greedy_drawing_algorithm " + greedy_algorithm);
            writer.println("greedy_drawing_algorithm_id " + greedy_algorithm_selection);
            writer.println("skipped_pixels_coloring " + ThreadDraw.SKIPPED_PIXELS_ALG);
            int color = ThreadDraw.SKIPPED_PIXELS_COLOR;
            writer.println("skipped_pixels_user_color " + ((color >> 16) & 0xff) + " " + ((color >> 8) & 0xff) + " " + (color & 0xff));
            writer.println("periodicity_checking " + periodicity_checking);

            writer.println();
            writer.println("[Window]");
            writer.println("image_size " + image_size);
            writer.println("window_maximized " + (getExtendedState() == JFrame.MAXIMIZED_BOTH));
            writer.println("window_size " + (int) getSize().getWidth() + " " + (int) getSize().getHeight());
            writer.println("window_location " + (int) getLocation().getX() + " " + (int) getLocation().getY());
            writer.println("window_toolbar " + options_menu.getToolbar().isSelected());
            writer.println("window_infobar " + options_menu.getInfobar().isSelected());
            writer.println("window_statusbar " + options_menu.getStatusbar().isSelected());
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
            writer.println("julia_preview_filters " + fast_julia_filters);

            writer.println();
            writer.println("[Color Cycling]");
            writer.println("color_cycling_speed " + color_cycling_speed);
            writer.println("cycle_colors " + cycle_colors);
            writer.println("cycle_lights " + cycle_lights);
            writer.println("cycle_gradient " + cycle_gradient);

            writer.println();
            writer.println("[Zoom]");
            writer.println("zoom_factor " + zoom_factor);

            writer.println();
            writer.println("[Random Palette]");
            writer.println("random_palette_algorithm " + CustomPaletteEditorFrame.getRandomPaletteAlgorithm());
            writer.println("equal_hues " + CustomPaletteEditorFrame.getEqualHues());

            writer.println();
            writer.println("[Quick Draw]");
            writer.println("tiles " + ThreadDraw.TILE_SIZE);

            writer.close();
        } catch (FileNotFoundException ex) {

        }
    }

    private void loadPreferences() {
        BufferedReader br;

        try {
            br = new BufferedReader(new FileReader("preferences.ini"));
            String str_line;

            while ((str_line = br.readLine()) != null) {

                StringTokenizer tokenizer = new StringTokenizer(str_line, " ");

                if (tokenizer.hasMoreTokens()) {

                    String token = tokenizer.nextToken();

                    if (token.equals("thread_dim") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 1 && temp <= 100) {
                                n = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("greedy_drawing_algorithm") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("false")) {
                            greedy_algorithm = false;
                        } else if (token.equals("true")) {
                            greedy_algorithm = true;
                        }
                    } else if (token.equals("greedy_drawing_algorithm_id") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp == BOUNDARY_TRACING || temp == DIVIDE_AND_CONQUER) {
                                greedy_algorithm_selection = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("skipped_pixels_coloring") && tokenizer.countTokens() == 1) {

                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 0 && temp <= 4) {
                                ThreadDraw.SKIPPED_PIXELS_ALG = temp;
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("skipped_pixels_user_color") && tokenizer.countTokens() == 3) {

                        try {
                            int red = Integer.parseInt(tokenizer.nextToken());
                            int green = Integer.parseInt(tokenizer.nextToken());
                            int blue = Integer.parseInt(tokenizer.nextToken());

                            if (red >= 0 && red <= 255 && green >= 0 && green <= 255 && blue >= 0 && blue <= 255) {
                                ThreadDraw.SKIPPED_PIXELS_COLOR = new Color(red, green, blue).getRGB();
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
                    } else if (token.equals("image_size") && tokenizer.countTokens() == 1) {
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
                    } else if (token.equals("orbit_style") && tokenizer.countTokens() == 1) {

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
                    } else if (token.equals("zoom_window_style") && tokenizer.countTokens() == 1) {

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
                    } else if (token.equals("zoom_factor") && tokenizer.countTokens() == 1) {
                        try {
                            double temp = Double.parseDouble(tokenizer.nextToken());

                            if (temp > 1.05 && temp <= 32) {
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

                            if (temp >= 0 && temp <= 8) {
                                CustomPaletteEditorFrame.setRandomPaletteAlgorithm(temp);
                            }
                        } catch (Exception ex) {
                        }
                    } else if (token.equals("equal_hues") && tokenizer.countTokens() == 1) {

                        token = tokenizer.nextToken();

                        if (token.equals("true")) {
                            CustomPaletteEditorFrame.setEqualHues(true);
                        } else if (token.equals("false")) {
                            CustomPaletteEditorFrame.setEqualHues(false);
                        }
                    } else if (token.equals("cycle_colors") && tokenizer.countTokens() == 1) {

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
                    } else if (token.equals("tiles") && tokenizer.countTokens() == 1) {
                        try {
                            int temp = Integer.parseInt(tokenizer.nextToken());

                            if (temp >= 2 && temp <= 20) {
                                ThreadDraw.TILE_SIZE = temp;
                            }
                        } catch (Exception ex) {
                        }

                    } else {
                        continue;
                    }
                }

            }

        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        }
    }

    public void filtersOptionsChanged(int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean[] activeFilters) {

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

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.fs.filters[MainWindow.ANTIALIASING] || s.ds.domain_coloring || s.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn) {

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            if (julia_map) {
                createThreadsJuliaMap();
            } else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if (julia_map) {
                startThreads(julia_grid_first_dimension);
            } else {
                startThreads(n);
            }
        } else {
            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
                createThreadsPaletteAndFilter3DModel();
            } else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
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
        if (s.fns.julia) {
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

        if (s.fns.init_val) {
            s.fns.init_val = false;
            options_menu.getInitialValue().setSelected(false);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.perturbation && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        }

        if (s.fns.perturbation) {
            s.fns.perturbation = false;
            options_menu.getPerturbation().setSelected(false);

            if (s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.fns.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.fns.out_coloring_algorithm != ESCAPE_TIME_GRID && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.fns.out_coloring_algorithm != BIOMORPH && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.fns.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.fns.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.fns.out_coloring_algorithm != BANDED && !s.fns.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.fns.init_val && s.fns.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                fractal_functions[KLEINIAN].setEnabled(true);
                fractal_functions[INERTIA_GRAVITY].setEnabled(true);
            }
        }

        if (julia_map) {
            julia_map = false;
            tools_menu.getJuliaMap().setSelected(false);
            toolbar.getJuliaMapButton().setSelected(false);
            options_menu.getJuliaMapOptions().setEnabled(false);
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
        }

        if (mode && (s.fns.bail_technique == 1 || s.fns.function == USER_FORMULA_NOVA)) {
            options_menu.getBailout().setEnabled(false);
            options_menu.getBailoutConditionMenu().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
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
            new JLabel(supported_vars),
            operations,
            new JLabel("+ - * / % ^ ( ) ,"),
            constants,
            new JLabel("pi, e, phi, c10, alpha, delta"),
            complex_numbers,
            new JLabel("a + bi"),
            trig,
            new JLabel("sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,"),
            new JLabel("acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,"),
            new JLabel("hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc"),
            other,
            new JLabel("exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec, flip, round,"),
            new JLabel("ceil, floor, trunc, deta, snorm, f1, ... f60"),
            two_arg,
            new JLabel("logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, dist, sdist, g1, ... g60"),
            multi_arg,
            new JLabel("m1, ... m60, k1, ... k60")
        };

        return labels;

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
            File UserDefinedFunctionsFile = new File("UserDefinedFunctions.java");

            if (UserDefinedFunctionsFile.exists()) {
                Desktop.getDesktop().open(UserDefinedFunctionsFile);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(scroll_pane, "Could not open UserDefinedFunctions.java for editing.\nMake sure that the file exists.\nIf not please restart the application.", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createCompleteImage(int delay) {

        if (timer == null) {
            timer = new Timer();
            timer.schedule(new CompleteImageTask(ptr), delay);
        }

    }

    public void taskCompleteImage() {

        if (!threadsAvailable()) {
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

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);
    }

    public void setQuickDrawTiles() {

        resetOrbit();
        new QuickDrawTilesDialog(ptr);

    }

    public void boundaryTracingOptionsChanged(boolean greedy_algorithm, int algorithm) {

        this.greedy_algorithm = greedy_algorithm;
        greedy_algorithm_selection = algorithm;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void setPointPost() {

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setPoint() {

        resetOrbit();
        new UserPointDialog(ptr, s);

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

        JOptionPane.showMessageDialog(scroll_pane, "<html><center><font size='5' face='arial' color='blue'><b><u>Fractal Zoomer</u></b></font><br><br><font size='4' face='arial'><img src=\"" + getClass().getResource("/fractalzoomer/icons/mandel2.png") + "\"><br><br>Version: <b>" + versionStr + "</b><br><br>Author: <b>Christos Kalonakis</b><br><br>Contact: <a href=\"mailto:hrkalona@gmail.com\">hrkalona@gmail.com</a><br><br></center></font></html>", "About", JOptionPane.INFORMATION_MESSAGE);

    }

    public void setGreedyAlgorithms() {

        resetOrbit();
        new GreedyAlgorithmsFrame(ptr, greedy_algorithm, greedy_algorithm_selection);

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
        Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), 0);

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
        if (orbit && pixels_orbit != null) {
            try {
                pixels_orbit.join();

            } catch (InterruptedException ex) {

            }

            pixels_orbit = null;

            main_panel.repaint();
        }
    }

    private void clearThreads() {

        if (threads == null) {
            return;
        }

        for (int i = 0; i < threads.length; i++) {
            for (int j = 0; j < threads[0].length; j++) {
                threads[i][j] = null;
            }
        }

    }

    public void setColorBlending(int val) {

        resetOrbit();

        s.color_blending = val;

        updateColors();
    }

    public void setColorTransfer(int val, boolean outcoloring) {
        resetOrbit();

        if (outcoloring) {
            s.ps.transfer_function = val;
        } else {
            s.ps2.transfer_function = val;
        }

        updateColors();

    }

    public CommonFunctions getCommonFunctions() {

        return common;

    }

    public void setGradient() {

        resetOrbit();
        new GradientFrame(ptr, s.gs);

    }

    public void gradientChanged(Color colorA, Color colorB, int interpolation, int color_space, boolean reverse, int gradient_offset) {

        resetOrbit();

        s.gs.colorA = colorA;
        s.gs.colorB = colorB;
        s.gs.gradient_interpolation = interpolation;
        s.gs.gradient_color_space = color_space;
        s.gs.gradient_reversed = reverse;
        s.gs.gradient_offset = gradient_offset;

        ThreadDraw.gradient = CustomPalette.createGradient(colorA.getRGB(), colorB.getRGB(), Constants.GRADIENT_LENGTH, interpolation, color_space, reverse, 0);

        updateGradientPreview(s.gs.gradient_offset);

        updateColors();
    }

    public void setOrbitTraps() {

        resetOrbit();
        new OrbitTrapsFrame(ptr, s.ots);

    }

    public void setOrbitTrapSettings() {

        setOptions(false);

        options_menu.getProcessing().updateIcons(s);

        tools_menu.getColorCycling().setEnabled(false);
        toolbar.getColorCyclingButton().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }

    public void setDirectColor() {

        if (!options_menu.getDirectColor().isSelected()) {
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = false;

            if (!s.ds.domain_coloring) {
                if (s.usePaletteForInColoring) {
                    infobar.getInColoringPalettePreview().setVisible(true);
                    infobar.getInColoringPalettePreviewLabel().setVisible(true);
                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);
                    infobar.getInColoringPalettePreview().setVisible(false);
                    infobar.getInColoringPalettePreviewLabel().setVisible(false);
                }
            }
            infobar.getGradientPreviewLabel().setVisible(true);
            infobar.getGradientPreview().setVisible(true);
            infobar.getOutColoringPalettePreview().setVisible(true);
            infobar.getOutColoringPalettePreviewLabel().setVisible(true);
        } else {
            ThreadDraw.USE_DIRECT_COLOR = s.useDirectColor = true;
            s.ots.useTraps = false;
            s.fns.tcs.trueColorOut = false;
            s.fns.tcs.trueColorIn = false;

            options_menu.getProcessing().updateIcons(s);
            options_menu.getColorsMenu().updateIcons(s);

            tools_menu.getColorCycling().setEnabled(false);
            toolbar.getColorCyclingButton().setEnabled(false);

            tools_menu.getDomainColoring().setEnabled(false);
            toolbar.getDomainColoringButton().setEnabled(false);

            toolbar.getOutCustomPaletteButton().setEnabled(false);
            toolbar.getInCustomPaletteButton().setEnabled(false);
            toolbar.getRandomPaletteButton().setEnabled(false);

            tools_menu.get3D().setEnabled(false);
            toolbar.get3DButton().setEnabled(false);

            options_menu.getFractalColor().setEnabled(false);
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

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setContourColoring() {

        resetOrbit();
        new ContourColoringDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void defaultSettings() {
        try {
            resetOrbit();

            s.defaultValues();
            s.applyStaticSettings();

            if (!s.d3s.d3) {
                ThreadDraw.setArrays(image_size, s.ds.domain_coloring);
                progress.setMaximum((image_size * image_size) + ((image_size * image_size) / 100));
                toolbar.get3DButton().setSelected(false);
                tools_menu.get3D().setSelected(false);
            }

            if (!loadAutoSave()) {
                prepareUI();
            }

            ThreadDraw.setDomainImageData(image_size, s.ds.domain_coloring);

            setOptions(false);

            progress.setValue(0);

            resetImage();

            scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

            if (s.d3s.d3) {
                s.d3s.fiX = 0.64;
                s.d3s.fiY = 0.82;
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            whole_image_done = false;

            if (julia_map) {
                createThreadsJuliaMap();
            } else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if (julia_map) {
                startThreads(julia_grid_first_dimension);
            } else {
                startThreads(n);
            }
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }
    }

    public void setProcessingOrder() {

        resetOrbit();
        new ProcessingOrderingFrame(ptr, s.post_processing_order, s.fdes.fake_de, s.ens.entropy_coloring, s.ofs.offset_coloring, s.rps.rainbow_palette, s.gss.greyscale_coloring, s.cns.contour_coloring, s.bms.bump_map, s.ls.lighting);

    }

    public void setProcessingOrder(int[] processing_order) {
        s.post_processing_order = processing_order;
        updateColors();
    }

    public void redraw() {

        resetOrbit();
        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    public void setLighting() {

        resetOrbit();

        new LightDialog(ptr, s, greedy_algorithm, julia_map);

    }

    public void setPostProcessingPost() {
        options_menu.getProcessing().updateIcons(s);
        updateColors();
    }

    public void setPaletteGradientMergingPost() {
        options_menu.getColorsMenu().updateIcons(s);
        updateColors();
    }

    public void setPaletteGradientMerging() {

        resetOrbit();
        new PaletteGradientMergingDialog(ptr, s);

    }

    public void updateColors() {

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps || s.fns.tcs.trueColorOut || s.fns.tcs.trueColorIn) {
            if (julia_map) {
                createThreadsJuliaMap();
            } else {
                createThreads(false);
            }

            calculation_time = System.currentTimeMillis();

            if (julia_map) {
                startThreads(julia_grid_first_dimension);
            } else {
                startThreads(n);
            }
        } else {
            if (s.d3s.d3) {
                createThreads(false);
            } else {
                createThreadsPaletteAndFilter();
            }

            calculation_time = System.currentTimeMillis();

            startThreads(n);
        }
    }

    public void openStatisticsColoringFrame() {
        resetOrbit();
        new StatisticsColoringFrame(ptr, s.sts, s);
    }

    public void statisticsColorAlgorithmChanged(StatisticsSettings sts) {
        s.sts = new StatisticsSettings(sts);

        options_menu.getProcessing().updateIcons(s);

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }

    }

    private boolean loadAutoSave() {

        String filename = "autoload.fzs";
        try {
            if (new File(filename).exists()) {
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
            savePreferences();
            System.exit(-1);
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
    }

    public void setFunctionPostKleinian() {
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getPerturbation().setEnabled(false);
        options_menu.getInitialValue().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutConditionMenu().setEnabled(false);
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

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (julia_map) {
            createThreadsJuliaMap();
        } else {
            createThreads(false);
        }

        calculation_time = System.currentTimeMillis();

        if (julia_map) {
            startThreads(julia_grid_first_dimension);
        } else {
            startThreads(n);
        }
    }
    
    public void setInTrueColorModePost() {
        
        options_menu.getPeriodicityChecking().setEnabled(false);
        setOutTrueColorModePost();
        
    }

    public static void main(String[] args) throws InterruptedException, Exception {

        SplashFrame sf = new SplashFrame(VERSION);
        sf.setVisible(true);

        while (sf.isAnimating()) {
        }

        sf.dispose();

        MainWindow mw = new MainWindow();
        mw.setVisible(true);

        mw.checkCompilationStatus();
        mw.getCommonFunctions().checkForUpdate(false);

    }
}
