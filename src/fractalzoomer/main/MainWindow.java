/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
 /* Thanks to Lutz Lehmann for the parhalley and laguerre implementation */
 /* Many of the ideas in this project come from XaoS, Fractal Extreme, FractInt, fractalforums.com and ofcourse from alot of google search */
 /* Sorry for the absence of comments, this project was never supposed to reach this level! */
 /* Also forgive me for the huge-packed main class, read above! */
package fractalzoomer.main;

//import com.alee.laf.WebLookAndFeel;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.core.drawing_algorithms.BoundaryTracingDraw;
import fractalzoomer.core.drawing_algorithms.BruteForceDraw;
import fractalzoomer.core.Complex;
import fractalzoomer.core.drawing_algorithms.DivideAndConquerDraw;
import fractalzoomer.core.DrawOrbit;
import fractalzoomer.gui.MainPanel;
import fractalzoomer.gui.RequestFocusListener;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.parser.ParserException;
import fractalzoomer.gui.GreedyAlgorithmsFrame;
import fractalzoomer.gui.ColorChooserFrame;
import fractalzoomer.gui.CustomPaletteEditorFrame;
import fractalzoomer.gui.DomainColoringFrame;
import fractalzoomer.gui.FileMenu;
import fractalzoomer.gui.FiltersMenu;
import fractalzoomer.gui.FiltersOptionsFrame;
import fractalzoomer.gui.FractalColorsFrame;
import fractalzoomer.gui.GradientFrame;
import fractalzoomer.gui.HelpMenu;
import fractalzoomer.gui.Infobar;
import fractalzoomer.gui.LinkLabel;
import fractalzoomer.gui.OptionsMenu;
import fractalzoomer.gui.OrbitTrapsFrame;
import fractalzoomer.gui.PlaneVisualizationFrame;
import fractalzoomer.gui.RangeSlider;
import fractalzoomer.gui.SplashFrame;
import fractalzoomer.gui.Statusbar;
import fractalzoomer.gui.Toolbar;
import fractalzoomer.gui.ToolsMenu;
import fractalzoomer.gui.UserFormulasHelpDialog;
import static fractalzoomer.main.Constants.CUSTOM_PALETTE_ID;
import fractalzoomer.palettes.PresetPalette;
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
import java.awt.GridLayout;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
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
    private boolean runsOnWindows;
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
    private JRadioButtonMenuItem fractal_functions[];
    private JRadioButtonMenuItem[] planes;
    private JRadioButtonMenuItem[] out_coloring_modes;
    private JRadioButtonMenuItem[] in_coloring_modes;
    private JRadioButtonMenuItem[] bailout_tests;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private Cursor rotate_cursor;
    private CommonFunctions common;
    private int i, k;

    /**
     * *****************************
     */
    public MainWindow() {

        super();

        s = new Settings();

        runsOnWindows = false;

        ptr = this;

        zoom_factor = 2;

        old_xCenter = s.xCenter;
        old_yCenter = s.yCenter;
        old_size = s.size;
        old_height_ratio = s.height_ratio;

        n = 2;
        julia_grid_first_dimension = 0;

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

        old_rotation_vals = new double[2];

        old_rotation_vals[0] = s.rotation_vals[0];
        old_rotation_vals[1] = s.rotation_vals[1];

        old_rotation_center = new double[2];

        old_rotation_center[0] = s.rotation_center[0];
        old_rotation_center[1] = s.rotation_center[1];

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

        options_menu = new OptionsMenu(ptr, "Options", s.color_choice, s.smoothing, s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.color_cycling_location, s.scale_factor_palette_val, s.processing_alg, show_orbit_converging_point, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.out_coloring_algorithm, s.in_coloring_algorithm, s.function, s.plane_type, s.bailout_test_algorithm, s.transfer_function, s.color_blending);

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
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        ctrlKeyPressed = true;
                        scroll_pane.setCursor(grab_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        shiftKeyPressed = true;
                        scroll_pane.setCursor(rotate_cursor);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && !ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        altKeyPressed = true;
                        scroll_pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_CONTROL && ctrlKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        ctrlKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT && shiftKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        shiftKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_ALT && altKeyPressed) {
                    if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {
                        altKeyPressed = false;
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }
            }

        });

        scroll_pane.setFocusable(true);
        scroll_pane.requestFocusInWindow();

        if (scroll_pane.getMouseWheelListeners().length > 0) {
            scroll_pane.removeMouseWheelListener(scroll_pane.getMouseWheelListeners()[0]); // remove the default listener
        }

        scroll_pane.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (!orbit && !s.d3s.d3 && !julia_map && (!s.julia || s.julia && !first_seed)) {

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
                } else {
                    if (!orbit) {
                        if (!s.d3s.d3) {
                            if (!s.polar_projection) {
                                if (!s.julia) { //Regular
                                    selectPointFractal(e);
                                } else {
                                    selectPointJulia(e);
                                }
                            } else { //Polar
                                if (s.julia && first_seed) {
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
                }

                if (julia_map || s.julia && first_seed) {
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
                        ThreadDraw.setArrays(image_size);
                    }

                    main_panel.setPreferredSize(new Dimension(image_size, image_size));

                    setOptions(false);

                    if (!s.d3s.d3) {
                        progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));
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

                    if (s.julia && first_seed) {
                        s.julia = false;
                        tools_menu.getJulia().setSelected(false);
                        toolbar.getJuliaButton().setSelected(false);

                        if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED) {
                            rootFindingMethodsSetEnabled(true);
                        }
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
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
                } else if (ctrlKeyPressed && !julia_map && (!s.julia || s.julia && !first_seed)) {
                    dragPoint(e);
                } else if (shiftKeyPressed && !julia_map && (!s.julia || s.julia && !first_seed)) {
                    rotatePoint(e);
                } else if (altKeyPressed && !julia_map && (!s.julia || s.julia && !first_seed)) {
                    selectPointForPlane(e);
                }

                if (julia_map || s.julia && first_seed) {
                    scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    return;
                }

                if ((old_d3 && e.getModifiers() == InputEvent.BUTTON1_MASK) || (ctrlKeyPressed && e.getModifiers() == (InputEvent.BUTTON1_MASK | InputEvent.CTRL_MASK))) {
                    scroll_pane.setCursor(grabbing_cursor);
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

                    if (!ctrlKeyPressed && !shiftKeyPressed && !altKeyPressed) {
                        scroll_pane.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }

                } catch (NullPointerException ex) {
                }

                if (s.julia && first_seed) {
                    fastJulia();
                }

                if (zoom_window) {
                    main_panel.repaint();
                }

            }
        });

        common = new CommonFunctions(scroll_pane, runsOnWindows);

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

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

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

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

        temp = "Fractal Zoomer   #";

        switch (s.function) {
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
                temp += "  z = z^" + s.z_exponent + " + c";
                break;
            case MANDELBROTWTH:
                temp += "  z = z^(" + Complex.toString2(s.z_exponent_complex[0], s.z_exponent_complex[1]) + ") + c";
                break;
            case MANDELPOLY:
                temp += "   Multibrot " + s.poly + " + c";
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
            case NOVA:
                switch (s.nova_method) {

                    case MainWindow.NOVA_NEWTON:
                        temp += "   Nova-Newton, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_HALLEY:
                        temp += "   Nova-Halley, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_SCHRODER:
                        temp += "   Nova-Schroder, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_HOUSEHOLDER:
                        temp += "   Nova-Householder, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_SECANT:
                        temp += "   Nova-Secant, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_STEFFENSEN:
                        temp += "   Nova-Steffensen, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_MULLER:
                        temp += "   Nova-Muller, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_PARHALLEY:
                        temp += "   Nova-Parhallley, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
                        break;
                    case MainWindow.NOVA_LAGUERRE:
                        temp += "   Nova-Laguerre, e: " + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ", r: " + Complex.toString2(s.relaxation[0], s.relaxation[1]);
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

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.rotation_vals, s.rotation_center);

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

                Point2D.Double p2 = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.rotation_vals, s.rotation_center);

                statusbar.getReal().setText("" + p2.x);

                statusbar.getImaginary().setText("" + p2.y);
            } catch (Exception ex) {
            }
        }
    }

    private void createThreads(boolean quickDraw) {

        ThreadDraw.resetThreadData(n * n);

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (s.julia) {
                        if (s.d3s.d3) {
                            if (greedy_algorithm) {
                                if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                                } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            } else {
                                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        } else {
                            if (greedy_algorithm) {
                                if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                                } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                                }
                            } else {
                                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.color_blending, s.ots, s.xJuliaCenter, s.yJuliaCenter);
                            }
                        }
                    } else {
                        if (s.d3s.d3) {
                            if (greedy_algorithm) {
                                if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                                } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                                }
                            } else {
                                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                            }
                        } else {
                            if (greedy_algorithm) {
                                if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                                    threads[i][j] = new BoundaryTracingDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                                } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                                    threads[i][j] = new DivideAndConquerDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                                }
                            } else {
                                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, s.d3s, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.perturbation, s.perturbation_vals, s.variable_perturbation, s.user_perturbation_algorithm, s.user_perturbation_conditions, s.user_perturbation_condition_formula, s.perturbation_user_formula, s.init_val, s.initial_vals, s.variable_init_value, s.user_initial_value_algorithm, s.user_initial_value_conditions, s.user_initial_value_condition_formula, s.initial_value_user_formula, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.user_fz_formula, s.user_dfz_formula, s.user_ddfz_formula, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.ds, s.inverse_dem, quickDraw, s.ens, s.ofs, s.gss, s.laguerre_deg, s.color_blending, s.ots);
                            }
                        }
                    }
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

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".fzs"));

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
        }

        main_panel.repaint();

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

            try {

                s.readSettings(file.toString(), scroll_pane);

                julia_map = false;
                tools_menu.getJuliaMap().setSelected(false);
                toolbar.getJuliaMapButton().setSelected(false);
                options_menu.getJuliaMapOptions().setEnabled(false);

                zoom_window = false;
                tools_menu.getZoomWindow().setSelected(false);

                if (s.julia) {
                    first_seed = false;
                    tools_menu.getJulia().setSelected(true);
                    toolbar.getJuliaButton().setSelected(true);
                    tools_menu.getJuliaMap().setEnabled(false);
                    toolbar.getJuliaMapButton().setEnabled(false);
                    options_menu.getPerturbation().setEnabled(false);
                    options_menu.getInitialValue().setEnabled(false);
                    rootFindingMethodsSetEnabled(false);
                    fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                } else {
                    first_seed = true;
                    tools_menu.getJulia().setSelected(false);
                    toolbar.getJuliaButton().setSelected(false);

                    if (!s.perturbation && !s.init_val) {
                        rootFindingMethodsSetEnabled(true);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
                    } else {
                        tools_menu.getJulia().setEnabled(false);
                        toolbar.getJuliaButton().setEnabled(false);
                        tools_menu.getJuliaMap().setEnabled(false);
                        toolbar.getJuliaMapButton().setEnabled(false);
                        rootFindingMethodsSetEnabled(false);
                        fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
                    }
                }

                options_menu.getPerturbation().setSelected(s.perturbation);
                options_menu.getInitialValue().setSelected(s.init_val);

                if (s.size < 0.05) {
                    tools_menu.getBoundaries().setEnabled(false);
                    boundaries = false;
                    tools_menu.getBoundaries().setSelected(false);
                }

                if (s.rotation != 0 && s.rotation != 360 && s.rotation != -360) {
                    tools_menu.getGrid().setEnabled(false);
                    grid = false;
                    tools_menu.getGrid().setSelected(false);

                    tools_menu.getBoundaries().setEnabled(false);
                    boundaries = false;
                    tools_menu.getBoundaries().setSelected(false);
                }

                if (s.in_coloring_algorithm != MAX_ITERATIONS) {
                    periodicity_checking = false;
                    options_menu.getPeriodicityChecking().setSelected(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getInColoringMenu().setEnabledAllButMaxIterations(true);
                }

                for (int k = 0; k < fractal_functions.length; k++) {
                    if (k != PARHALLEY3 && k != PARHALLEY4 && k != PARHALLEYGENERALIZED3 && k != PARHALLEYGENERALIZED8 && k != PARHALLEYSIN && k != PARHALLEYCOS && k != PARHALLEYPOLY && k != PARHALLEYFORMULA
                            && k != LAGUERRE3 && k != LAGUERRE4 && k != LAGUERREGENERALIZED3 && k != LAGUERREGENERALIZED8 && k != LAGUERRESIN && k != LAGUERRECOS && k != LAGUERREPOLY && k != LAGUERREFORMULA
                            && k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                            && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                            && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                            && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                            && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                            && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                            && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                        fractal_functions[k].setEnabled(true);
                    }

                    if (k == SIERPINSKI_GASKET && !s.julia && !s.perturbation && !s.init_val) {
                        fractal_functions[k].setEnabled(true);
                    }
                }

                options_menu.getBurningShipOpt().setEnabled(true);
                options_menu.getMandelGrassOpt().setEnabled(true);

                if (s.out_coloring_algorithm == DISTANCE_ESTIMATOR || s.exterior_de) {
                    for (int k = 1; k < fractal_functions.length; k++) {
                        fractal_functions[k].setEnabled(false);
                    }
                } else if (s.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES2 || s.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES || s.out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || s.out_coloring_algorithm == ESCAPE_TIME_GRID || s.out_coloring_algorithm == BIOMORPH || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || s.out_coloring_algorithm == ITERATIONS_PLUS_RE || s.out_coloring_algorithm == ITERATIONS_PLUS_IM || s.out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || s.out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || s.out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || s.out_coloring_algorithm == BANDED) {
                    rootFindingMethodsSetEnabled(false);
                } else {
                    if (!s.julia && !s.perturbation && !s.init_val) {
                        rootFindingMethodsSetEnabled(true);
                    }
                }

                options_menu.getApplyPlaneOnWholeJuliaOpt().setSelected(s.apply_plane_on_julia);
                options_menu.getApplyPlaneOnJuliaSeedOpt().setSelected(s.apply_plane_on_julia_seed);

                out_coloring_modes[s.out_coloring_algorithm].setSelected(true);

                in_coloring_modes[s.in_coloring_algorithm].setSelected(true);

                options_menu.getBlendingModes()[s.color_blending].setSelected(true);
                options_menu.getTranferFunctions()[s.transfer_function].setSelected(true);

                bailout_tests[s.bailout_test_algorithm].setSelected(true);

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
                    tools_menu.getColorCyling().setEnabled(false);
                    toolbar.getColorCyclingButton().setEnabled(false);

                    if (!s.ds.use_palette_domain_coloring) {
                        toolbar.getCustomPaletteButton().setEnabled(false);
                        toolbar.getRandomPaletteButton().setEnabled(false);

                        options_menu.getRandomPalette().setEnabled(false);
                        options_menu.getRollPaletteMenu().setEnabled(false);
                        options_menu.getPaletteMenu().setEnabled(false);
                        options_menu.getColorIntensity().setEnabled(false);
                        options_menu.getColorTransferMenu().setEnabled(false);
                        options_menu.getProcessing().setEnabled(false);                      
                    } else {
                        options_menu.getDistanceEstimation().setEnabled(false);
                        options_menu.getBumpMap().setEnabled(false);

                        options_menu.getFakeDistanceEstimation().setEnabled(false);

                        options_menu.getEntropyColoring().setEnabled(false);
                        options_menu.getOffsetColoring().setEnabled(false);
                        options_menu.getGreyScaleColoring().setEnabled(false);

                        options_menu.getRainbowPalette().setEnabled(false);
                        options_menu.getOrbitTraps().setEnabled(false);
                    }

                    options_menu.getOutColoringMenu().setEnabled(false);
                    options_menu.getInColoringMenu().setEnabled(false);
                    options_menu.getFractalColor().setEnabled(false);

                    options_menu.getGreedyAlgorithm().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                    options_menu.getBailout().setEnabled(false);
                    options_menu.getBailoutConditionMenu().setEnabled(false);

                    infobar.getMaxIterationsColorPreview().setVisible(false);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

                    options_menu.getDomainColoringOptions().setEnabled(true);
                } else {
                    infobar.getMaxIterationsColorPreview().setVisible(true);
                    infobar.getMaxIterationsColorPreviewLabel().setVisible(true);

                    options_menu.getDomainColoringOptions().setEnabled(false);
                }

                tools_menu.getDomainColoring().setSelected(s.ds.domain_coloring);
                toolbar.getDomainColoringButton().setSelected(s.ds.domain_coloring);
                
                if(s.ots.useTraps) {
                    tools_menu.getColorCyling().setEnabled(false);
                    toolbar.getColorCyclingButton().setEnabled(false);
                    options_menu.getPeriodicityChecking().setEnabled(false);
                }
                
                filters_menu.setCheckedFilters(s.fs.filters);

                fractal_functions[s.function].setSelected(true);

                switch (s.function) {
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
                    case NEWTONFORMULA:
                    case HALLEYFORMULA:
                    case SCHRODERFORMULA:
                    case HOUSEHOLDERFORMULA:
                    case SECANTFORMULA:
                    case STEFFENSENFORMULA:
                    case MULLERFORMULA:
                    case PARHALLEYFORMULA:
                    case LAGUERREFORMULA:
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
                        if (s.function == MANDELPOLY) {
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
                        if (!s.ds.domain_coloring) {
                            options_menu.getDistanceEstimation().setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR) {
                            out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != BIOMORPH) {
                            out_coloring_modes[BIOMORPH].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != BANDED) {
                            out_coloring_modes[BANDED].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                            out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_GRID) {
                            out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
                            out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
                        }
                        if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
                            out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
                        }

                        break;
                    case USER_FORMULA:
                    case USER_FORMULA_ITERATION_BASED:
                    case USER_FORMULA_CONDITIONAL:
                    case USER_FORMULA_COUPLED:

                        if (!s.user_formula_c) {
                            tools_menu.getJulia().setEnabled(false);
                            toolbar.getJuliaButton().setEnabled(false);
                            tools_menu.getJuliaMap().setEnabled(false);
                            toolbar.getJuliaMapButton().setEnabled(false);
                            options_menu.getPerturbation().setEnabled(false);
                            options_menu.getInitialValue().setEnabled(false);
                        }

                        if (s.bail_technique == 1) {
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

                options_menu.getPalette()[s.color_choice].setSelected(true);
                updateColorPalettesMenu();
                updateMaxIterColorPreview();
                updateGradientPreview();

                options_menu.getBurningShipOpt().setSelected(s.burning_ship);
                options_menu.getMandelGrassOpt().setSelected(s.mandel_grass);

                planes[s.plane_type].setSelected(true);

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
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Error while loading the file.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            }
        }

    }

    public void saveSettingsAndImage() {
        main_panel.repaint();

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".png"));

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
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Unsupported image format.", "Error!", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
            }

        }

        resetOrbit();
    }

    public void saveImage() {

        main_panel.repaint();

        file_chooser = new JFileChooser(".");
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG (*.png)", "png"));

        Calendar calendar = new GregorianCalendar();
        file_chooser.setSelectedFile(new File("fractal " + String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR) + " " + String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ";" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ";" + String.format("%02d", calendar.get(Calendar.SECOND)) + ".png"));

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

    private void defaultFractalSettings() {

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

    public void goToFractal() {

        resetOrbit();

        if (!orbit) {

            JTextField field_real = new JTextField();

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

            if (p.x == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + p.x);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            if (p.y == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + p.y);
            }

            JTextField field_size = new JTextField();
            field_size.setText("" + s.size);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText()) - s.rotation_center[0];
                    tempImaginary = Double.parseDouble(field_imaginary.getText()) - s.rotation_center[1];
                    tempSize = Double.parseDouble(field_size.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                return;
            }

            if (tempSize <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.size = tempSize;
            /* Inverse rotation */
            s.xCenter = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
            s.yCenter = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];

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
        } else {
            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if (orbit_vals[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + orbit_vals[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (orbit_vals[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + orbit_vals[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (orbit_vals[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + orbit_vals[0]);
                        }

                        field_real.setEditable(true);

                        if (orbit_vals[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + orbit_vals[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            JTextField sequence_points = new JTextField();
            sequence_points.setText("" + (s.max_iterations > 400 ? 400 : s.max_iterations));

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    seq_points = Integer.parseInt(sequence_points.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                return;
            }

            if (seq_points <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            orbit_vals[0] = tempReal;
            orbit_vals[1] = tempImaginary;

            tempReal -= s.rotation_center[0];
            tempImaginary -= s.rotation_center[1];

            /* Inversed Rotation */
            double x_real = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
            double y_imag = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];

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

    }

    public void goToJulia() {

        resetOrbit();

        if (!orbit) {
            if (first_seed) {
                JTextField field_real = new JTextField();

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

                if (p.x == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + p.x);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                if (p.y == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + p.y);
                }

                Object[] message = {
                    " ",
                    "Set the real and imaginary part of the Julia seed.",
                    "Real:", field_real,
                    "Imaginary:", field_imaginary,
                    " ",};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Julia Seed", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.xJuliaCenter = Double.parseDouble(field_real.getText());
                        s.yJuliaCenter = Double.parseDouble(field_imaginary.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        return;
                    }
                } else {
                    main_panel.repaint();
                    return;
                }

                first_seed = false;
                defaultFractalSettings();
            } else {
                JTextField field_real = new JTextField();

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

                if (p.x == 0) {
                    field_real.setText("" + 0.0);
                } else {
                    field_real.setText("" + p.x);
                }

                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();

                if (p.y == 0) {
                    field_imaginary.setText("" + 0.0);
                } else {
                    field_imaginary.setText("" + p.y);
                }

                JTextField field_size = new JTextField();
                field_size.setText("" + s.size);

                s.xJuliaCenter = s.xJuliaCenter == 0.0 ? 0.0 : s.xJuliaCenter;
                s.yJuliaCenter = s.yJuliaCenter == 0.0 ? 0.0 : s.yJuliaCenter;

                JTextField real_seed = new JTextField(16);
                real_seed.setEditable(false);
                JTextField imag_seed = new JTextField(16);
                imag_seed.setEditable(false);

                real_seed.setText("" + s.xJuliaCenter);
                imag_seed.setText("" + s.yJuliaCenter);

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

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        tempReal = Double.parseDouble(field_real.getText()) - s.rotation_center[0];
                        tempImaginary = Double.parseDouble(field_imaginary.getText()) - s.rotation_center[1];
                        tempSize = Double.parseDouble(field_size.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        return;
                    }
                } else {
                    main_panel.repaint();
                    return;
                }

                if (tempSize <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Size number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                s.size = tempSize;
                /* Inverse rotation */
                s.xCenter = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
                s.yCenter = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];

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

                createThreads(false);

                calculation_time = System.currentTimeMillis();

                startThreads(n);
            }
        } else {
            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if (orbit_vals[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + orbit_vals[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (orbit_vals[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + orbit_vals[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (orbit_vals[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + orbit_vals[0]);
                        }

                        field_real.setEditable(true);

                        if (orbit_vals[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + orbit_vals[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {

                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            JTextField sequence_points = new JTextField();
            sequence_points.setText("" + (s.max_iterations > 400 ? 400 : s.max_iterations));

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    seq_points = Integer.parseInt(sequence_points.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                return;
            }

            if (seq_points <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Sequence points number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            orbit_vals[0] = tempReal;
            orbit_vals[1] = tempImaginary;

            tempReal -= s.rotation_center[0];
            tempImaginary -= s.rotation_center[1];

            double x_real = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
            double y_imag = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];

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

    }

    private void updateColors() {

        //Fix, fractal_color should not be included in the palette, boundary tracing algorithm fails some times
        Color[] c = null;

        if (s.color_choice != CUSTOM_PALETTE_ID) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[s.color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, s.color_cycling_location, 0, PROCESSING_NONE);
        } else {
            c = CustomPalette.getPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.color_cycling_location, s.scale_factor_palette_val, s.processing_alg);
        }

        boolean flag = true;

        int count = 0;

        boolean fractal_color_up = true;
        boolean dem_color_up = true;
        boolean special_color_up = true;

        while (flag && count < 10) {
            flag = false;
            for (int j = 0; j < c.length; j++) {
                if (c[j].getRGB() == s.fractal_color.getRGB()) {
                    if (fractal_color_up) {
                        if (s.fractal_color.getBlue() == 255) {
                            s.fractal_color = new Color(s.fractal_color.getRed(), s.fractal_color.getGreen(), s.fractal_color.getBlue() - 1);
                            fractal_color_up = false;
                        } else {
                            s.fractal_color = new Color(s.fractal_color.getRed(), s.fractal_color.getGreen(), s.fractal_color.getBlue() + 1);
                        }
                    } else {
                        if (s.fractal_color.getBlue() == 0) {
                            s.fractal_color = new Color(s.fractal_color.getRed(), s.fractal_color.getGreen(), s.fractal_color.getBlue() + 1);
                            fractal_color_up = true;
                        } else {
                            s.fractal_color = new Color(s.fractal_color.getRed(), s.fractal_color.getGreen(), s.fractal_color.getBlue() - 1);
                        }
                    }
                    flag = true;
                }

                if (c[j].getRGB() == s.dem_color.getRGB()) {
                    if (dem_color_up) {
                        if (s.dem_color.getRed() == 255) {
                            s.dem_color = new Color(s.dem_color.getRed() - 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                            dem_color_up = false;
                        } else {
                            s.dem_color = new Color(s.dem_color.getRed() + 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                        }
                    } else {
                        if (s.dem_color.getRed() == 0) {
                            s.dem_color = new Color(s.dem_color.getRed() + 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                            dem_color_up = true;
                        } else {
                            s.dem_color = new Color(s.dem_color.getRed() - 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                        }
                    }

                    flag = true;
                }

                if (c[j].getRGB() == s.special_color.getRGB()) {
                    if (special_color_up) {
                        if (s.special_color.getGreen() == 255) {
                            s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                            special_color_up = false;
                        } else {
                            s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                        }
                    } else {
                        if (s.special_color.getGreen() == 0) {
                            s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                            special_color_up = true;
                        } else {
                            s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                        }
                    }
                    flag = true;
                }

                if (flag) {
                    break;
                }
            }

            if (s.fractal_color.getRGB() == s.dem_color.getRGB()) {
                if (dem_color_up) {
                    if (s.dem_color.getRed() == 255) {
                        s.dem_color = new Color(s.dem_color.getRed() - 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                        dem_color_up = false;
                    } else {
                        s.dem_color = new Color(s.dem_color.getRed() + 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                    }
                } else {
                    if (s.dem_color.getRed() == 0) {
                        s.dem_color = new Color(s.dem_color.getRed() + 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                        dem_color_up = true;
                    } else {
                        s.dem_color = new Color(s.dem_color.getRed() - 1, s.dem_color.getGreen(), s.dem_color.getBlue());
                    }
                }
                flag = true;
            }

            if (s.dem_color.getRGB() == s.special_color.getRGB()) {
                if (special_color_up) {
                    if (s.special_color.getGreen() == 255) {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                        special_color_up = false;
                    } else {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                    }
                } else {
                    if (s.special_color.getGreen() == 0) {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                        special_color_up = true;
                    } else {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                    }
                }
                flag = true;
            }

            if (s.fractal_color.getRGB() == s.special_color.getRGB()) {
                if (special_color_up) {
                    if (s.special_color.getGreen() == 255) {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                        special_color_up = false;
                    } else {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                    }
                } else {
                    if (s.special_color.getGreen() == 0) {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() + 1, s.special_color.getBlue());
                        special_color_up = true;
                    } else {
                        s.special_color = new Color(s.special_color.getRed(), s.special_color.getGreen() - 1, s.special_color.getBlue());
                    }
                }
                flag = true;
            }

            count++;
        }
    }

    public void setFractalColor() {

        resetOrbit();
        new FractalColorsFrame(ptr, s.fractal_color, s.dem_color, s.special_color, s.special_use_palette_color, ColorAlgorithm.GlobalIncrementBypass);

    }

    public void displaySpecialHelp() {

        common.displaySpecialHelp(ptr);

    }

    public void fractalColorsChanged(Color max_it_color, Color dem_color, Color special_color, boolean use_palette_color, boolean special_bypass) {

        s.fractal_color = max_it_color;
        s.dem_color = dem_color;
        s.special_color = special_color;
        s.special_use_palette_color = use_palette_color;
        ColorAlgorithm.GlobalIncrementBypass = special_bypass;

        if (s.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette = new CustomPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.scale_factor_palette_val, s.processing_alg, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        } else {
            ThreadDraw.palette = new PresetPalette(s.color_choice, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        }

        updateColors();

        updateMaxIterColorPreview();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setIterations() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using maximum " + s.max_iterations + " iterations.\nEnter the new maximum iterations number.", "Maximum Iterations Number", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if (temp < 1) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 80000) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum iterations number must be less than 80001.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            s.max_iterations = temp;

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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setHeightRatio() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + s.height_ratio + " as stretch factor.\nEnter the new stretch.", "Stretch Factor", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if (temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Stretch factor number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.height_ratio = temp;

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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setZoomingFactor() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + zoom_factor + " for zooming factor.\nEnter the new zooming factor.", "Zooming Factor", JOptionPane.QUESTION_MESSAGE);

        try {
            Double temp = Double.parseDouble(ans);

            if (temp <= 1.05) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Zooming factor must be greater than 1.05.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 32) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Zooming factor must be less than 33.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            zoom_factor = temp;

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setThreadsNumber() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "Processor cores: " + Runtime.getRuntime().availableProcessors() + "\nYou are using " + n * n + " threads in a " + n + "x" + n + " 2D grid.\nEnter the first dimension, n, of the nxn 2D grid.", "Threads Number", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if (temp < 1) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 100) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The first dimension number of the 2D threads\ngrid must be lower than 101.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            n = temp;
            threads = new ThreadDraw[n][n];

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setPalette(int temp2) {

        resetOrbit();
        s.color_choice = temp2;
        if (s.color_choice != CUSTOM_PALETTE_ID) {
            s.color_cycling_location = 0;
        }

        options_menu.getPalette()[s.color_choice].setSelected(true);

        if (s.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette = new CustomPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.scale_factor_palette_val, s.processing_alg, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        } else {
            ThreadDraw.palette = new PresetPalette(s.color_choice, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        }

        updateColors();

        updateColorPalettesMenu();
        updateMaxIterColorPreview();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

                if (s.ots.useTraps) {
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

            if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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
            if (!zoom_window && !julia_map && !orbit && !boundaries) {
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
            if (!zoom_window && !julia_map && !orbit && !grid) {
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
            if (!s.ds.domain_coloring && s.functionSupportsC() && !s.init_val && !s.perturbation) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if (!s.julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if (!grid && !boundaries) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if (!grid) {
                tools_menu.getPolarProjection().setEnabled(true);
                toolbar.getPolarProjectionButton().setEnabled(true);
            }

            if (!s.ds.domain_coloring) {
                tools_menu.getColorCyling().setEnabled(true);
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
            tools_menu.getColorCyling().setEnabled(false);
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
            options_menu.getOrbitTraps().setEnabled(true);
            main_panel.repaint();
        } else {
            periodicity_checking = true;
            options_menu.getInColoringMenu().setEnabledAllButMaxIterations(false);
            options_menu.getOrbitTraps().setEnabled(false);
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

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_x, temp_y, s.rotation_vals, s.rotation_center);

        s.plane_transform_center[0] = p.x;
        s.plane_transform_center[1] = p.y;

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
        s.rotation += new_angle - old_angle;

        if (s.rotation > 360) {
            s.rotation -= 2 * 360;
        } else if (s.rotation < -360) {
            s.rotation += 2 * 360;
        }

        oldDragX = curX;
        oldDragY = curY;

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

        s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
        s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));

        s.rotation_center[0] = p.x;
        s.rotation_center[1] = p.y;
        s.xCenter = s.rotation_center[0];
        s.yCenter = s.rotation_center[1];

        if (s.rotation != 0 && s.rotation != 360 && s.rotation != -360) {
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

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.rotation_vals, s.rotation_center);

                    s.xJuliaCenter = p.x;
                    s.yJuliaCenter = p.y;
                } else {
                    double size_2_x = s.size * 0.5;
                    double size_2_y = (s.size * s.height_ratio) * 0.5;
                    double temp_size_image_size_x = s.size / image_size;
                    double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter - size_2_x + temp_size_image_size_x * x1, s.yCenter + size_2_y - temp_size_image_size_y * y1, s.rotation_vals, s.rotation_center);

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
            s.julia = false;
            toolbar.getJuliaButton().setSelected(false);
            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
            if (!first_seed) {
                defaultFractalSettings();
                main_panel.repaint();
            } else {
                setOptions(true);
                main_panel.repaint();
            }
        } else {
            s.julia = true;
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

        if (!tools_menu.getOrbit().isSelected()) {
            resetOrbit();
            orbit = false;

            toolbar.getOrbitButton().setSelected(false);
            if (!s.ds.domain_coloring && s.functionSupportsC() && !s.init_val && !s.perturbation) {
                tools_menu.getJulia().setEnabled(true);
                toolbar.getJuliaButton().setEnabled(true);
                if (!s.julia) {
                    tools_menu.getJuliaMap().setEnabled(true);
                    toolbar.getJuliaMapButton().setEnabled(true);
                }
            }

            if (!grid && !boundaries) {
                tools_menu.get3D().setEnabled(true);
                toolbar.get3DButton().setEnabled(true);
            }

            if (!s.polar_projection) {
                tools_menu.getZoomWindow().setEnabled(true);
            }

            if (!s.ds.domain_coloring) {
                tools_menu.getColorCyling().setEnabled(true);
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
            tools_menu.getColorCyling().setEnabled(false);
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

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.rotation_vals, s.rotation_center);

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

                Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.rotation_vals, s.rotation_center);

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
                s.rotation -= s.circle_period * 360;

                if (s.rotation < -360) {
                    s.rotation = ((int) (-s.rotation / 360) + 1) * 360 + s.rotation;
                }

                s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
                s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));
            } else {
                s.yCenter += (s.size * s.height_ratio);
            }
        } else if (where == DOWN) {
            if (s.polar_projection) {
                s.rotation += s.circle_period * 360;

                if (s.rotation > 360) {
                    s.rotation = ((int) (-s.rotation / 360) - 1) * 360 + s.rotation;
                }

                s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
                s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));
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
        } else {
            if (s.polar_projection) {
                double start = Math.log(s.size);
                double end;

                double muly = (2 * s.circle_period * Math.PI) / image_size;

                double mulx = muly * s.height_ratio;

                end = mulx * image_size + start;

                s.size = Math.exp(end);
            } else {
                s.xCenter += s.size;
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

        file_menu.getCodeEditor().setEnabled(option);
        file_menu.getCompileCode().setEnabled(option);

        if ((!s.julia || !first_seed)) {
            file_menu.getGoTo().setEnabled(option);
        }

        options_menu.getFractalFunctionsMenu().setEnabled(option);

        options_menu.getColorsMenu().setEnabled(option);

        options_menu.getIterationsMenu().setEnabled(option);
        options_menu.getSizeOfImage().setEnabled(option);

        options_menu.getHeightRatio().setEnabled(option);

        if (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring)) {
            toolbar.getCustomPaletteButton().setEnabled(option);
            toolbar.getRandomPaletteButton().setEnabled(option);
        }
        toolbar.getIterationsButton().setEnabled(option);
        toolbar.getRotationButton().setEnabled(option);
        toolbar.getSaveImageButton().setEnabled(option);
        toolbar.getSaveImageAndSettignsButton().setEnabled(option);
        options_menu.getPoint().setEnabled(option);

        if (!s.ds.domain_coloring && !s.isConvergingType()) {
            options_menu.getBailout().setEnabled(option);
            options_menu.getBailoutConditionMenu().setEnabled(option);
        }

        options_menu.getOptimizationsMenu().setEnabled(option);
        options_menu.getToolsOptionsMenu().setEnabled(option);

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.function != SIERPINSKI_GASKET && !s.ots.useTraps) {
            options_menu.getPeriodicityChecking().setEnabled(option);
        }

        if (!s.ds.domain_coloring && !s.d3s.d3 && !julia_map) {
            options_menu.getGreedyAlgorithm().setEnabled(option);
        }

        if (((!s.julia && !orbit) || (!first_seed && !orbit)) && !s.ds.domain_coloring && !zoom_window && !julia_map && !s.d3s.d3 && !s.perturbation && !s.init_val && s.functionSupportsC()) {
            tools_menu.getJulia().setEnabled(option);
            toolbar.getJuliaButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !color_cycling && !s.d3s.d3 && !s.ds.domain_coloring && !s.ots.useTraps) {
            tools_menu.getColorCyling().setEnabled(option);
            toolbar.getColorCyclingButton().setEnabled(option);
        }

        if (!color_cycling && !julia_map) {
            tools_menu.getDomainColoring().setEnabled(option);
            toolbar.getDomainColoringButton().setEnabled(option);
        }

        tools_menu.getPlaneVizualization().setEnabled(option);

        if (!s.ds.domain_coloring) {
            if (s.function == MANDELBROT) {
                options_menu.getDistanceEstimation().setEnabled(option);
            }

            options_menu.getBumpMap().setEnabled(option);

            options_menu.getFakeDistanceEstimation().setEnabled(option);

            options_menu.getEntropyColoring().setEnabled(option);

            options_menu.getGreyScaleColoring().setEnabled(option);

            options_menu.getRainbowPalette().setEnabled(option);

            options_menu.getOffsetColoring().setEnabled(option);
            
            if(!periodicity_checking) {
                options_menu.getOrbitTraps().setEnabled(option);
            }

            options_menu.getOutColoringMenu().setEnabled(option);
            options_menu.getInColoringMenu().setEnabled(option);
            options_menu.getFractalColor().setEnabled(option);
        }

        if (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring)) {
            options_menu.getRandomPalette().setEnabled(option);
            options_menu.getRollPaletteMenu().setEnabled(option);
            options_menu.getPaletteMenu().setEnabled(option);
            options_menu.getColorIntensity().setEnabled(option);
            options_menu.getColorTransferMenu().setEnabled(option);
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

        if ((s.rotation == 0 || s.rotation == 360 || s.rotation == -360) && !s.d3s.d3 && !s.polar_projection) {
            tools_menu.getGrid().setEnabled(option);
        }

        if ((s.rotation == 0 || s.rotation == 360 || s.rotation == -360) && !s.d3s.d3 && s.size >= 0.05) {
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

        if (!s.ds.domain_coloring && !zoom_window && !s.julia && !s.perturbation && !s.init_val && !orbit && !s.d3s.d3 && s.functionSupportsC()) {
            tools_menu.getJuliaMap().setEnabled(option);
            toolbar.getJuliaMapButton().setEnabled(option);
        }

        if (!zoom_window && !orbit && !julia_map && !grid && !boundaries) {
            tools_menu.get3D().setEnabled(option);
            toolbar.get3DButton().setEnabled(option);
        }

        options_menu.getRotationMenu().setEnabled(option);

        if (!s.julia && !julia_map && s.functionSupportsC()) {
            options_menu.getPerturbation().setEnabled(option);
            options_menu.getInitialValue().setEnabled(option);
        }

        options_menu.getOverview().setEnabled(option);
        infobar.getOverview().setEnabled(option);

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

        resetOrbit();
        int oldSelected = s.function;
        s.function = temp;
        int l;

        switch (s.function) {
            case MANDELBROTNTH:
                main_panel.repaint();

                String ans = (String) JOptionPane.showInputDialog(scroll_pane, "Enter the exponent of z.\nThe exponent can be a real number.", "Exponent", JOptionPane.QUESTION_MESSAGE, null, null, s.z_exponent);

                try {
                    s.z_exponent = Double.parseDouble(ans);
                    s.z_exponent = s.z_exponent == 0.0 ? 0.0 : s.z_exponent;
                } catch (Exception ex) {
                    if (ans == null) {
                        main_panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                    }
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }
                optionsEnableShortcut();
                break;
            case MANDELBROTWTH:

                JLabel zw = new JLabel();
                zw.setIcon(getIcon("/fractalzoomer/icons/zw.png"));

                JTextField field_real = new JTextField();
                field_real.setText("" + s.z_exponent_complex[0]);
                field_real.addAncestorListener(new RequestFocusListener());

                JTextField field_imaginary = new JTextField();
                field_imaginary.setText("" + s.z_exponent_complex[1]);

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
                if (res == JOptionPane.OK_OPTION) {
                    try {
                        temp3 = Double.parseDouble(field_real.getText());
                        temp4 = Double.parseDouble(field_imaginary.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                s.z_exponent_complex[0] = temp3 == 0.0 ? 0.0 : temp3;
                s.z_exponent_complex[1] = temp4 == 0.0 ? 0.0 : temp4;

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
                JLabel polynomial = new JLabel();
                polynomial.setIcon(getIcon("/fractalzoomer/icons/polynomial.png"));

                JPanel poly1 = new JPanel();
                JLabel poly_label_1 = new JLabel();
                poly_label_1.setIcon(getIcon("/fractalzoomer/icons/a10.png"));
                JTextField poly_coef_1 = new JTextField(30);
                poly_coef_1.addAncestorListener(new RequestFocusListener());
                poly_coef_1.setText("" + s.coefficients[0]);
                poly1.setLayout(new FlowLayout());
                poly1.add(poly_label_1);
                poly1.add(new JLabel(""));
                poly1.add(poly_coef_1);

                JPanel poly2 = new JPanel();
                JLabel poly_label_2 = new JLabel();
                poly_label_2.setIcon(getIcon("/fractalzoomer/icons/a9.png"));
                JTextField poly_coef_2 = new JTextField(30);
                poly_coef_2.setText("" + s.coefficients[1]);
                poly2.setLayout(new FlowLayout());
                poly2.add(poly_label_2);
                poly2.add(new JLabel(" "));
                poly2.add(poly_coef_2);

                JPanel poly3 = new JPanel();
                JLabel poly_label_3 = new JLabel();
                poly_label_3.setIcon(getIcon("/fractalzoomer/icons/a8.png"));
                JTextField poly_coef_3 = new JTextField(30);
                poly_coef_3.setText("" + s.coefficients[2]);
                poly3.setLayout(new FlowLayout());
                poly3.add(poly_label_3);
                poly3.add(new JLabel(" "));
                poly3.add(poly_coef_3);

                JPanel poly4 = new JPanel();
                JLabel poly_label_4 = new JLabel();
                poly_label_4.setIcon(getIcon("/fractalzoomer/icons/a7.png"));
                JTextField poly_coef_4 = new JTextField(30);
                poly_coef_4.setText("" + s.coefficients[3]);
                poly4.setLayout(new FlowLayout());
                poly4.add(poly_label_4);
                poly4.add(new JLabel(" "));
                poly4.add(poly_coef_4);

                JPanel poly5 = new JPanel();
                JLabel poly_label_5 = new JLabel();
                poly_label_5.setIcon(getIcon("/fractalzoomer/icons/a6.png"));
                JTextField poly_coef_5 = new JTextField(30);
                poly_coef_5.setText("" + s.coefficients[4]);
                poly5.setLayout(new FlowLayout());
                poly5.add(poly_label_5);
                poly5.add(new JLabel(" "));
                poly5.add(poly_coef_5);

                JPanel poly6 = new JPanel();
                JLabel poly_label_6 = new JLabel();
                poly_label_6.setIcon(getIcon("/fractalzoomer/icons/a5.png"));
                JTextField poly_coef_6 = new JTextField(30);
                poly_coef_6.setText("" + s.coefficients[5]);
                poly6.setLayout(new FlowLayout());
                poly6.add(poly_label_6);
                poly6.add(new JLabel(" "));
                poly6.add(poly_coef_6);

                JPanel poly7 = new JPanel();
                JLabel poly_label_7 = new JLabel();
                poly_label_7.setIcon(getIcon("/fractalzoomer/icons/a4.png"));
                JTextField poly_coef_7 = new JTextField(30);
                poly_coef_7.setText("" + s.coefficients[6]);
                poly7.setLayout(new FlowLayout());
                poly7.add(poly_label_7);
                poly7.add(new JLabel(" "));
                poly7.add(poly_coef_7);

                JPanel poly8 = new JPanel();
                JLabel poly_label_8 = new JLabel();
                poly_label_8.setIcon(getIcon("/fractalzoomer/icons/a3.png"));
                JTextField poly_coef_8 = new JTextField(30);
                poly_coef_8.setText("" + s.coefficients[7]);
                poly8.setLayout(new FlowLayout());
                poly8.add(poly_label_8);
                poly8.add(new JLabel(" "));
                poly8.add(poly_coef_8);

                JPanel poly9 = new JPanel();
                JLabel poly_label_9 = new JLabel();
                poly_label_9.setIcon(getIcon("/fractalzoomer/icons/a2.png"));
                JTextField poly_coef_9 = new JTextField(30);
                poly_coef_9.setText("" + s.coefficients[8]);
                poly9.setLayout(new FlowLayout());
                poly9.add(poly_label_9);
                poly9.add(new JLabel(" "));
                poly9.add(poly_coef_9);

                JPanel poly10 = new JPanel();
                JLabel poly_label_10 = new JLabel();
                poly_label_10.setIcon(getIcon("/fractalzoomer/icons/a1.png"));
                JTextField poly_coef_10 = new JTextField(30);
                poly_coef_10.setText("" + s.coefficients[9]);
                poly10.setLayout(new FlowLayout());
                poly10.add(poly_label_10);
                poly10.add(new JLabel(" "));
                poly10.add(poly_coef_10);

                JPanel poly11 = new JPanel();
                JLabel poly_label_11 = new JLabel();
                poly_label_11.setIcon(getIcon("/fractalzoomer/icons/a0.png"));
                JTextField poly_coef_11 = new JTextField(30);
                poly_coef_11.setText("" + s.coefficients[10]);
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

                if (poly_res == JOptionPane.OK_OPTION) {
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
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                boolean non_zero = false;
                for (l = 0; l < s.coefficients.length; l++) {
                    if (temp_coef[l] != 0) {
                        non_zero = true;
                        break;
                    }
                }

                if (!non_zero) {
                    JOptionPane.showMessageDialog(scroll_pane, "At least one coefficient must be non zero!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                for (l = 0; l < s.coefficients.length; l++) {
                    s.coefficients[l] = temp_coef[l] == 0.0 ? 0.0 : temp_coef[l];
                }

                s.createPoly();

                if (s.function == MANDELPOLY) {
                    optionsEnableShortcut();
                } else {
                    optionsEnableShortcut2();
                }
                break;

            case NEWTONFORMULA:

                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                    s.user_dfz_formula = "3*z^2";
                }

                JTextField field_fz_formula = new JTextField(50);
                field_fz_formula.setText(s.user_fz_formula);
                field_fz_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula = new JTextField(50);
                field_dfz_formula.setText(s.user_dfz_formula);

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

                Object[] labels4 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message4 = {
                    labels4,
                    " ",
                    imagepanel4,
                    " ",
                    "Insert the function and its derivative.",
                    formula_fz_panel,
                    formula_dfz_panel,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message4, "Newton Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_fz_formula.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_dfz_formula.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula.getText();
                        s.user_dfz_formula = field_dfz_formula.getText();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case HALLEYFORMULA:
            case SCHRODERFORMULA:
            case HOUSEHOLDERFORMULA:
            case PARHALLEYFORMULA:

                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                    s.user_dfz_formula = "3*z^2";
                    s.user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula2 = new JTextField(50);
                field_fz_formula2.setText(s.user_fz_formula);
                field_fz_formula2.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula2 = new JTextField(50);
                field_dfz_formula2.setText(s.user_dfz_formula);

                JTextField field_ddfz_formula2 = new JTextField(50);
                field_ddfz_formula2.setText(s.user_ddfz_formula);

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

                if (s.function == HALLEYFORMULA) {
                    imagelabel41.setIcon(getIcon("/fractalzoomer/icons/halley.png"));
                } else if (s.function == SCHRODERFORMULA) {
                    imagelabel41.setIcon(getIcon("/fractalzoomer/icons/schroder.png"));
                } else if (s.function == HOUSEHOLDERFORMULA) {
                    imagelabel41.setIcon(getIcon("/fractalzoomer/icons/householder.png"));
                } else if (s.function == PARHALLEYFORMULA) {
                    imagelabel41.setIcon(getIcon("/fractalzoomer/icons/parhalley.png"));
                }

                JPanel imagepanel41 = new JPanel();
                imagepanel41.add(imagelabel41);

                Object[] labels41 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message41 = {
                    labels41,
                    " ",
                    imagepanel41,
                    " ",
                    "Insert the function and its derivatives.",
                    formula_fz_panel2,
                    formula_dfz_panel2,
                    formula_ddfz_panel2,};

                String title = "";

                if (s.function == HALLEYFORMULA) {
                    title = "Halley Formula";
                } else if (s.function == SCHRODERFORMULA) {
                    title = "Schroder Formula";
                } else if (s.function == HOUSEHOLDERFORMULA) {
                    title = "Householder Formula";
                } else if (s.function == PARHALLEYFORMULA) {
                    title = "Parhalley Formula";
                }

                res = JOptionPane.showConfirmDialog(scroll_pane, message41, title, JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_fz_formula2.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_dfz_formula2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_ddfz_formula2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula2.getText();
                        s.user_dfz_formula = field_dfz_formula2.getText();
                        s.user_ddfz_formula = field_ddfz_formula2.getText();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case SECANTFORMULA:
                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula5 = new JTextField(50);
                field_fz_formula5.setText(s.user_fz_formula);
                field_fz_formula5.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel5 = new JPanel();

                formula_fz_panel5.add(new JLabel("f(z) ="));
                formula_fz_panel5.add(field_fz_formula5);

                JLabel imagelabel44 = new JLabel();
                imagelabel44.setIcon(getIcon("/fractalzoomer/icons/secant.png"));
                JPanel imagepanel44 = new JPanel();
                imagepanel44.add(imagelabel44);

                Object[] labels44 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message44 = {
                    labels44,
                    " ",
                    imagepanel44,
                    " ",
                    "Insert the function.",
                    formula_fz_panel5,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message44, "Secant Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_fz_formula5.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula5.getText();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case STEFFENSENFORMULA:
                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula6 = new JTextField(50);
                field_fz_formula6.setText(s.user_fz_formula);
                field_fz_formula6.addAncestorListener(new RequestFocusListener());

                JPanel formula_fz_panel6 = new JPanel();

                formula_fz_panel6.add(new JLabel("f(z) ="));
                formula_fz_panel6.add(field_fz_formula6);

                JLabel imagelabel45 = new JLabel();
                imagelabel45.setIcon(getIcon("/fractalzoomer/icons/steffensen.png"));
                JPanel imagepanel45 = new JPanel();
                imagepanel45.add(imagelabel45);

                Object[] labels45 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message45 = {
                    labels45,
                    " ",
                    imagepanel45,
                    " ",
                    "Insert the function.",
                    formula_fz_panel6,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message45, "Steffensen Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_fz_formula6.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula6.getText();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }
                optionsEnableShortcut2();
                break;
            case MULLERFORMULA:
                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                }

                JTextField field_fz_formula7 = new JTextField(50);
                field_fz_formula7.setText(s.user_fz_formula);
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

                Object[] labels46 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message46 = {
                    labels46,
                    " ",
                    scroll_pane2,
                    "Insert the function.",
                    formula_fz_panel7,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message46, "Muller Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_fz_formula7.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula7.getText();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case LAGUERREFORMULA:

                if (s.function != oldSelected) {
                    s.user_fz_formula = "z^3 - 1";
                    s.user_dfz_formula = "3*z^2";
                    s.user_ddfz_formula = "6*z";
                }

                JTextField field_fz_formula9 = new JTextField(50);
                field_fz_formula9.setText(s.user_fz_formula);
                field_fz_formula9.addAncestorListener(new RequestFocusListener());

                JTextField field_dfz_formula9 = new JTextField(50);
                field_dfz_formula9.setText(s.user_dfz_formula);

                JTextField field_ddfz_formula9 = new JTextField(50);
                field_ddfz_formula9.setText(s.user_ddfz_formula);

                JPanel formula_fz_panel9 = new JPanel();

                formula_fz_panel9.add(new JLabel("f(z) ="));
                formula_fz_panel9.add(field_fz_formula9);

                JPanel formula_dfz_panel9 = new JPanel();

                formula_dfz_panel9.add(new JLabel("f '(z) ="));
                formula_dfz_panel9.add(field_dfz_formula9);

                JPanel formula_ddfz_panel9 = new JPanel();

                formula_ddfz_panel9.add(new JLabel("f ''(z) ="));
                formula_ddfz_panel9.add(field_ddfz_formula9);

                JLabel imagelabel91 = new JLabel();
                imagelabel91.setIcon(getIcon("/fractalzoomer/icons/laguerre.png"));
                JPanel imagepanel91 = new JPanel();
                imagepanel91.add(imagelabel91);

                JPanel degree_panel = new JPanel();

                JTextField field_real8 = new JTextField(20);
                field_real8.setText("" + s.laguerre_deg[0]);

                JTextField field_imaginary8 = new JTextField(20);
                field_imaginary8.setText("" + s.laguerre_deg[1]);

                degree_panel.add(new JLabel("Degree = "));
                degree_panel.add(new JLabel("Real:"));
                degree_panel.add(field_real8);
                degree_panel.add(new JLabel(" Imaginary:"));
                degree_panel.add(field_imaginary8);

                Object[] labels91 = createUserFormulaLabels("z, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message91 = {
                    labels91,
                    " ",
                    imagepanel91,
                    " ",
                    "Insert the function, its derivatives and the degree.",
                    formula_fz_panel9,
                    formula_dfz_panel9,
                    formula_ddfz_panel9,
                    degree_panel,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message91, "Laguerre Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {

                    double temp5 = 0, temp6 = 0;
                    try {
                        temp5 = Double.parseDouble(field_real8.getText());
                        temp6 = Double.parseDouble(field_imaginary8.getText());

                        s.parser.parse(field_fz_formula9.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_dfz_formula9.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f '(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_ddfz_formula9.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail() || s.parser.foundC()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, bail, cbail cannot be used in the f ''(z) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_fz_formula = field_fz_formula9.getText();
                        s.user_dfz_formula = field_dfz_formula9.getText();
                        s.user_ddfz_formula = field_ddfz_formula9.getText();
                        s.laguerre_deg[0] = temp5;
                        s.laguerre_deg[1] = temp6;
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut2();
                break;
            case NOVA:
                JLabel novazw = new JLabel();
                novazw.setIcon(getIcon("/fractalzoomer/icons/novazw.png"));

                field_real = new JTextField();
                field_real.setText("" + s.z_exponent_nova[0]);
                field_real.addAncestorListener(new RequestFocusListener());

                field_imaginary = new JTextField();
                field_imaginary.setText("" + s.z_exponent_nova[1]);

                JTextField field_real2 = new JTextField();
                field_real2.setText("" + s.relaxation[0]);

                JTextField field_imaginary2 = new JTextField();
                field_imaginary2.setText("" + s.relaxation[1]);

                String[] method = {"Newton", "Halley", "Schroder", "Householder", "Secant", "Steffensen", "Muller", "Parhalley", "Laguerre"};

                JComboBox method_choice = new JComboBox(method);
                method_choice.setSelectedIndex(s.nova_method);
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
                if (res == JOptionPane.OK_OPTION) {
                    try {
                        temp7 = method_choice.getSelectedIndex();
                        temp3 = Double.parseDouble(field_real.getText());
                        temp4 = Double.parseDouble(field_imaginary.getText());
                        temp5 = Double.parseDouble(field_real2.getText());
                        temp6 = Double.parseDouble(field_imaginary2.getText());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                s.z_exponent_nova[0] = temp3 == 0.0 ? 0.0 : temp3;
                s.z_exponent_nova[1] = temp4 == 0.0 ? 0.0 : temp4;

                s.relaxation[0] = temp5 == 0.0 ? 0.0 : temp5;
                s.relaxation[1] = temp6 == 0.0 ? 0.0 : temp6;

                s.nova_method = temp7;

                optionsEnableShortcut();

                options_menu.getPeriodicityChecking().setEnabled(false);
                options_menu.getBailout().setEnabled(false);
                options_menu.getBailoutConditionMenu().setEnabled(false);
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
                field_formula_it_based1.setText(s.user_formula_iteration_based[0]);
                field_formula_it_based1.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_it_based2 = new JTextField(50);
                field_formula_it_based2.setText(s.user_formula_iteration_based[1]);

                JTextField field_formula_it_based3 = new JTextField(50);
                field_formula_it_based3.setText(s.user_formula_iteration_based[2]);

                JTextField field_formula_it_based4 = new JTextField(50);
                field_formula_it_based4.setText(s.user_formula_iteration_based[3]);

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
                method42_choice.setSelectedIndex(s.bail_technique);
                method42_choice.setToolTipText("Selects the bailout technique.");
                method42_choice.setFocusable(false);

                Object[] labels32 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

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

                if (res == JOptionPane.OK_OPTION) {

                    boolean temp_bool = false;

                    try {
                        s.parser.parse(field_formula_it_based1.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 1st iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_it_based2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 2nd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_it_based3.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 3rd iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_it_based4.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the 4th iteration formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.user_formula_iteration_based[0] = field_formula_it_based1.getText();
                        s.user_formula_iteration_based[1] = field_formula_it_based2.getText();
                        s.user_formula_iteration_based[2] = field_formula_it_based3.getText();
                        s.user_formula_iteration_based[3] = field_formula_it_based4.getText();
                        s.user_formula_c = temp_bool;
                        s.bail_technique = method42_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case USER_FORMULA_CONDITIONAL:

                JPanel formula_panel_cond1 = new JPanel();
                formula_panel_cond1.setLayout(new GridLayout(2, 2));

                JTextField field_condition = new JTextField(24);
                field_condition.setText(s.user_formula_conditions[0]);
                field_condition.addAncestorListener(new RequestFocusListener());

                JTextField field_condition2 = new JTextField(24);
                field_condition2.setText(s.user_formula_conditions[1]);

                formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(field_condition);
                formula_panel_cond1.add(field_condition2);

                JTextField field_formula_cond1 = new JTextField(50);
                field_formula_cond1.setText(s.user_formula_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(50);
                field_formula_cond2.setText(s.user_formula_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(50);
                field_formula_cond3.setText(s.user_formula_condition_formula[2]);

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
                method43_choice.setSelectedIndex(s.bail_technique);
                method43_choice.setToolTipText("Selects the bailout technique.");
                method43_choice.setFocusable(false);

                Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

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

                if (res == JOptionPane.OK_OPTION) {

                    boolean temp_bool = false;

                    try {
                        s.parser.parse(field_condition.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_condition2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.parser.parse(field_formula_cond1.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_cond2.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_cond3.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.user_formula_condition_formula[0] = field_formula_cond1.getText();
                        s.user_formula_condition_formula[1] = field_formula_cond2.getText();
                        s.user_formula_condition_formula[2] = field_formula_cond3.getText();
                        s.user_formula_conditions[0] = field_condition.getText();
                        s.user_formula_conditions[1] = field_condition2.getText();
                        s.user_formula_c = temp_bool;
                        s.bail_technique = method43_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case MANDELBROT:
                if (!s.ds.domain_coloring) {
                    options_menu.getDistanceEstimation().setEnabled(true);
                }
                if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR) {
                    out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(true);
                }
                if (s.out_coloring_algorithm != BIOMORPH) {
                    out_coloring_modes[BIOMORPH].setEnabled(true);
                }
                if (s.out_coloring_algorithm != BANDED) {
                    out_coloring_modes[BANDED].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
                    out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
                    out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
                    out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
                    out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
                    out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_GRID) {
                    out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
                }
                if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
                    out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
                }
                break;
            case USER_FORMULA:

                JTextField field_formula = new JTextField(50);
                field_formula.setText(s.user_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JTextField field_formula2 = new JTextField(50);
                field_formula2.setText(s.user_formula2);

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
                method4_choice.setSelectedIndex(s.bail_technique);
                method4_choice.setToolTipText("Selects the bailout technique.");
                method4_choice.setFocusable(false);

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message3 = {
                    labels3,
                    bail,
                    method4_choice,
                    " ",
                    "Insert your formula, that will be evaluated in every iteration.",
                    formula_panel,
                    formula_panel2,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Formula", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_formula.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        boolean temp_bool = s.parser.foundC();

                        s.parser.parse(field_formula2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the c formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_formula = field_formula.getText();
                        s.user_formula2 = field_formula2.getText();
                        s.user_formula_c = temp_bool;
                        s.bail_technique = method4_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }

                optionsEnableShortcut();
                break;
            case USER_FORMULA_COUPLED:
                JTextField field_formula_coupled = new JTextField(50);
                field_formula_coupled.setText(s.user_formula_coupled[0]);
                field_formula_coupled.addAncestorListener(new RequestFocusListener());

                JTextField field_formula_coupled2 = new JTextField(50);
                field_formula_coupled2.setText(s.user_formula_coupled[1]);

                JTextField field_formula_coupled3 = new JTextField(50);
                field_formula_coupled3.setText(s.user_formula_coupled[2]);

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
                method5_choice.setSelectedIndex(s.bail_technique);
                method5_choice.setToolTipText("Selects the bailout technique.");
                method5_choice.setFocusable(false);

                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.setPreferredSize(new Dimension(410, 185));
                tabbedPane.setFocusable(false);

                JPanel formula_panel3 = new JPanel();
                JPanel coupling_options_panel = new JPanel();

                tabbedPane.addTab("Formula", formula_panel3);
                tabbedPane.addTab("Coupling", coupling_options_panel);

                JSlider coupling_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.coupling * 100));
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
                field_amplitude.setText("" + s.coupling_amplitude);

                final JTextField field_frequency = new JTextField(8);
                field_frequency.setText("" + s.coupling_frequency);

                final JTextField field_seed = new JTextField(8);
                field_seed.setText("" + s.coupling_seed);

                String[] coupling_method_str = {"Simple", "Cosine", "Random"};

                final JComboBox coupling_method_choice = new JComboBox(coupling_method_str);
                coupling_method_choice.setSelectedIndex(s.coupling_method);
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

                if (s.coupling_method == 0) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(false);
                } else if (s.coupling_method == 1) {
                    field_seed.setEnabled(false);
                    field_frequency.setEnabled(true);
                    field_amplitude.setEnabled(true);
                } else {
                    field_seed.setEnabled(true);
                    field_frequency.setEnabled(false);
                    field_amplitude.setEnabled(true);
                }

                coupling_method_choice.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (coupling_method_choice.getSelectedIndex() == 0) {
                            field_seed.setEnabled(false);
                            field_frequency.setEnabled(false);
                            field_amplitude.setEnabled(false);
                        } else if (coupling_method_choice.getSelectedIndex() == 1) {
                            field_seed.setEnabled(false);
                            field_frequency.setEnabled(true);
                            field_amplitude.setEnabled(true);
                        } else {
                            field_seed.setEnabled(true);
                            field_frequency.setEnabled(false);
                            field_amplitude.setEnabled(true);
                        }
                    }
                });

                Object[] labels5 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, center, size, sizei, v1 - v30, point");

                Object[] message5 = {
                    labels5,
                    bail5,
                    method5_choice,
                    " ",
                    tabbedPane,};

                res = JOptionPane.showConfirmDialog(scroll_pane, message5, "User Formula Coupled", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        s.parser.parse(field_formula_coupled.getText());
                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        boolean temp_bool = s.parser.foundC();

                        s.parser.parse(field_formula_coupled2.getText());

                        if (s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: bail, cbail cannot be used in the z2 formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        temp_bool = temp_bool | s.parser.foundC();

                        s.parser.parse(field_formula_coupled3.getText());

                        if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the z2(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        double temp_amp, temp_freq;
                        int temp_seed;
                        try {
                            temp_amp = Double.parseDouble(field_amplitude.getText());
                            temp_freq = Double.parseDouble(field_frequency.getText());
                            temp_seed = Integer.parseInt(field_seed.getText());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            fractal_functions[oldSelected].setSelected(true);
                            s.function = oldSelected;
                            return;
                        }

                        s.user_formula_coupled[0] = field_formula_coupled.getText();
                        s.user_formula_coupled[1] = field_formula_coupled2.getText();
                        s.user_formula_coupled[2] = field_formula_coupled3.getText();
                        s.user_formula_c = temp_bool;
                        s.bail_technique = method5_choice.getSelectedIndex();
                        s.coupling = coupling_slid.getValue() / 100.0;
                        s.coupling_amplitude = temp_amp;
                        s.coupling_seed = temp_seed;
                        s.coupling_frequency = temp_freq;
                        s.coupling_method = coupling_method_choice.getSelectedIndex();

                        setUserFormulaOptions();
                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        fractal_functions[oldSelected].setSelected(true);
                        s.function = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    fractal_functions[oldSelected].setSelected(true);
                    s.function = oldSelected;
                    return;
                }
                optionsEnableShortcut();
                break;
            default:
                optionsEnableShortcut();
                break;
        }

        if (oldSelected != s.function) {
            s.resetUserOutColoringFormulas();
        }

        defaultFractalSettings();

    }

    public void setBailout() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + s.bailout + " for bailout number.\nEnter the new bailout number.", "Bailout Number", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if (temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Bailout value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 1e70) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Bailout value must be less than 1e70.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            s.bailout = temp;

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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setRotation() {

        resetOrbit();
        final JSlider rotation_slid = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int) (s.rotation)));
        rotation_slid.setPreferredSize(new Dimension(300, 35));
        rotation_slid.setMajorTickSpacing(90);
        rotation_slid.setMinorTickSpacing(1);
        rotation_slid.setToolTipText("Sets the rotation.");
        //color_blend.setPaintTicks(true);
        rotation_slid.setPaintLabels(true);
        //rotation_slid.setSnapToTicks(true);
        rotation_slid.setFocusable(false);

        final JTextField field_rotation = new JTextField();
        field_rotation.setText("" + s.rotation);

        field_rotation.addAncestorListener(new RequestFocusListener());

        rotation_slid.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                field_rotation.setText("" + ((double) rotation_slid.getValue()));
            }

        });

        field_rotation.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    double temp = Double.parseDouble(field_rotation.getText());

                    rotation_slid.setValue(((int) (temp + 0.5)));
                } catch (Exception ex) {

                }
            }

        });

        final JTextField field_real = new JTextField();

        if (s.rotation_center[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + s.rotation_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (s.rotation_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + s.rotation_center[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!current_center.isSelected()) {
                    if (s.rotation_center[0] == 0) {
                        field_real.setText("" + 0.0);
                    } else {
                        field_real.setText("" + s.rotation_center[0]);
                    }

                    field_real.setEditable(true);

                    if (s.rotation_center[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    } else {
                        field_imaginary.setText("" + s.rotation_center[1]);
                    }
                    field_imaginary.setEditable(true);
                } else {
                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(field_rotation.getText());
                tempReal = Double.parseDouble(field_real.getText());
                tempImaginary = Double.parseDouble(field_imaginary.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp < -360) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "Rotation angle must be greater than -361.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            if (temp > 360) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Rotation angle must be less than 361.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        s.rotation = temp;

        s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
        s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));

        s.rotation_center[0] = tempReal;
        s.rotation_center[1] = tempImaginary;

        s.rotation_center[0] = s.rotation_center[0] == 0 ? 0 : s.rotation_center[0];
        s.rotation_center[1] = s.rotation_center[1] == 0 ? 0 : s.rotation_center[1];

        s.xCenter = s.rotation_center[0];
        s.yCenter = s.rotation_center[1];

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.rotation != 0 && s.rotation != 360 && s.rotation != -360) {
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
        if (s.rotation == 360) {
            s.rotation = 0;
        }
        s.rotation++;

        s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
        s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));

        setOptions(false);

        progress.setValue(0);

        if (s.rotation != 0 && s.rotation != 360 && s.rotation != -360) {
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
        if (s.rotation == -360) {
            s.rotation = 0;
        }
        s.rotation--;

        s.rotation_vals[0] = Math.cos(Math.toRadians(s.rotation));
        s.rotation_vals[1] = Math.sin(Math.toRadians(s.rotation));

        setOptions(false);

        progress.setValue(0);

        if (s.rotation != 0 && s.rotation != 360 && s.rotation != -360) {
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
            s.burning_ship = false;
        } else {
            s.burning_ship = true;
        }

        if (s.function <= 9 || s.function == MANDELPOLY || s.function == MANDELBROTWTH) {
            defaultFractalSettings();
        }

    }

    public void setMandelGrass() {

        resetOrbit();
        if (!options_menu.getMandelGrassOpt().isSelected()) {
            s.mandel_grass = false;
        } else {

            JTextField field_real = new JTextField();
            field_real.setText("" + s.mandel_grass_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();
            field_imaginary.setText("" + s.mandel_grass_vals[1]);

            Object[] message = {
                " ",
                "Set the real and imaginary part of the mandel grass.",
                "Real:", field_real,
                "Imaginary:", field_imaginary,
                " ",};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Mandel Grass", JOptionPane.OK_CANCEL_OPTION);

            double temp, temp2;
            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp = Double.parseDouble(field_real.getText());
                    temp2 = Double.parseDouble(field_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getMandelGrassOpt().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                options_menu.getMandelGrassOpt().setSelected(false);
                return;
            }

            s.mandel_grass_vals[0] = temp;
            s.mandel_grass_vals[1] = temp2;

            s.mandel_grass = true;
        }

        if (s.function <= 9 || s.function == MANDELPOLY || s.function == MANDELBROTWTH) {
            defaultFractalSettings();
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

    public void setSizeOfImage() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "Your image size is " + image_size + "x" + image_size + " .\nEnter the new image size.\nOnly one dimension is required.", "Image Size", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if (temp < 209) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be greater than 209.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp > 6000) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Image size must be less than than 6001.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            whole_image_done = false;

            old_grid = false;

            old_boundaries = false;

            image_size = temp;

            if (!s.d3s.d3) {
                ThreadDraw.setArrays(image_size);
            }

            main_panel.setPreferredSize(new Dimension(image_size, image_size));

            setOptions(false);

            if (!s.d3s.d3) {
                progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));
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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
            savePreferences();
            System.exit(-1);
        }

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

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter + r * cf, s.yCenter + r * sf, s.rotation_vals, s.rotation_center);

            temp_xJuliaCenter = p.x;

            temp_yJuliaCenter = p.y;
        } else {
            double size_2_x = s.size * 0.5;
            double size_2_y = (s.size * s.height_ratio) * 0.5;
            double temp_xcenter_size = s.xCenter - size_2_x;
            double temp_ycenter_size = s.yCenter + size_2_y;
            double temp_size_image_size_x = s.size / image_size;
            double temp_size_image_size_y = (s.size * s.height_ratio) / image_size;

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(temp_xcenter_size + temp_size_image_size_x * x1, temp_ycenter_size - temp_size_image_size_y * y1, s.rotation_vals, s.rotation_center);

            temp_xJuliaCenter = p.x;
            temp_yJuliaCenter = p.y;

        }

        Arrays.fill(((DataBufferInt) fast_julia_image.getRaster().getDataBuffer()).getData(), 0);

        switch (s.function) {
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

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (greedy_algorithm) {
                        if (greedy_algorithm_selection == BOUNDARY_TRACING) {
                            threads[i][j] = new BoundaryTracingDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.inverse_dem, s.ens, s.ofs, s.gss, s.color_blending, s.ots, temp_xJuliaCenter, temp_yJuliaCenter);
                        } else if (greedy_algorithm_selection == DIVIDE_AND_CONQUER) {
                            threads[i][j] = new DivideAndConquerDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.inverse_dem, s.ens, s.ofs, s.gss, s.color_blending, s.ots, temp_xJuliaCenter, temp_yJuliaCenter);
                        }
                    } else {
                        threads[i][j] = new BruteForceDraw(j * FAST_JULIA_IMAGE_SIZE / n, (j + 1) * FAST_JULIA_IMAGE_SIZE / n, i * FAST_JULIA_IMAGE_SIZE / n, (i + 1) * FAST_JULIA_IMAGE_SIZE / n, temp_xCenter, temp_yCenter, temp_size, temp_max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, ptr, s.fractal_color, s.dem_color, fast_julia_filters, fast_julia_image, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.inverse_dem, s.ens, s.ofs, s.gss, s.color_blending, s.ots, temp_xJuliaCenter, temp_yJuliaCenter);
                    }
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
        if (!tools_menu.getColorCyling().isSelected()) {

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

            s.color_cycling_location = threads[0][0].getColorCyclingLocation();

            if (s.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location = s.color_cycling_location;
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

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.max_iterations, ptr, image, s.fractal_color, s.dem_color, s.color_cycling_location, s.fs, s.bms, s.color_intensity, s.transfer_function, s.fdes, s.rps, s.ens, s.ofs, s.gss, s.color_blending);
            }
        }
    }

    private void createThreadsColorCycling() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.max_iterations, ptr, s.fractal_color, s.dem_color, image, s.color_cycling_location, s.bms, s.color_intensity, s.transfer_function, s.fdes, s.rps, color_cycling_speed, s.fs, s.ens, s.ofs, s.gss, s.color_blending);
            }
        }
    }

    private void createThreadsRotate3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.d3s, true, ptr, image, s.fs, s.color_blending);
            }
        }

    }

    private void createThreadsPaletteAndFilter3DModel() {

        ThreadDraw.resetThreadData(n * n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                threads[i][j] = new BruteForceDraw(j * s.d3s.detail / n, (j + 1) * s.d3s.detail / n, i * s.d3s.detail / n, (i + 1) * s.d3s.detail / n, s.d3s, false, ptr, image, s.fs, s.color_blending);
            }
        }

    }

    public void shiftPalette() {

        resetOrbit();
        String ans = (String) JOptionPane.showInputDialog(scroll_pane, "The palette is shifted by " + s.color_cycling_location + ".\nEnter a number to shift the palette.", "Shift Palette", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if (temp < 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Palette shift value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.color_cycling_location = temp;

            if (s.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location = s.color_cycling_location;
            }

            updateColorPalettesMenu();

            setOptions(false);

            progress.setValue(0);

            //scroll_pane.getHorizontalScrollBar().setValue((int)(scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
            //scroll_pane.getVerticalScrollBar().setValue((int)(scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));
            resetImage();

            whole_image_done = false;

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setPlane(int temp) {

        resetOrbit();
        int oldSelected = s.plane_type;
        s.plane_type = temp;

        if (s.plane_type == USER_PLANE) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(430, 190));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(runsOnWindows ? 45 : 40);
            field_formula.setText(s.user_plane);
            field_formula.addAncestorListener(new RequestFocusListener());

            JPanel formula_panel = new JPanel();

            formula_panel.add(new JLabel("z ="));
            formula_panel.add(field_formula);

            tabbedPane.addTab("Normal", formula_panel);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(s.user_plane_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(s.user_plane_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);
            JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond1.setText(s.user_plane_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond2.setText(s.user_plane_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);
            field_formula_cond3.setText(s.user_plane_condition_formula[2]);

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

            Object[] labels3 = createUserFormulaLabels("z, maxn, center, size, sizei, v1 - v30, point");

            tabbedPane.setSelectedIndex(s.user_plane_algorithm);

            Object[] message3 = {
                labels3,
                " ",
                "Insert your plane transformation.",
                tabbedPane,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Plane", JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                try {

                    if (tabbedPane.getSelectedIndex() == 0) {
                        s.parser.parse(field_formula.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }
                    } else {
                        s.parser.parse(field_condition.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }

                        s.parser.parse(field_condition2.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }

                        s.parser.parse(field_formula_cond1.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the left > right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }

                        s.parser.parse(field_formula_cond2.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the left < right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }

                        s.parser.parse(field_formula_cond3.getText());

                        if (s.parser.foundC() || s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                            JOptionPane.showMessageDialog(scroll_pane, "The variables: c, n, s, p, pp, bail, cbail cannot be used in the left = right z formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                            main_panel.repaint();
                            planes[oldSelected].setSelected(true);
                            s.plane_type = oldSelected;
                            return;
                        }
                    }

                    s.user_plane_algorithm = tabbedPane.getSelectedIndex();

                    if (s.user_plane_algorithm == 0) {
                        s.user_plane = field_formula.getText();
                    } else {
                        s.user_plane_conditions[0] = field_condition.getText();
                        s.user_plane_conditions[1] = field_condition2.getText();
                        s.user_plane_condition_formula[0] = field_formula_cond1.getText();
                        s.user_plane_condition_formula[1] = field_formula_cond2.getText();
                        s.user_plane_condition_formula[2] = field_formula_cond3.getText();
                    }

                    defaultFractalSettings();

                } catch (ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }
        } else if (s.plane_type == TWIRL_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + s.plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            final JTextField field_real = new JTextField();

            if (s.plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (s.plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + s.plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (s.plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + s.plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if (s.plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + s.plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            if (temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Twirl radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.plane_transform_center[0] = tempReal;
            s.plane_transform_center[1] = tempImaginary;
            s.plane_transform_angle = temp3;
            s.plane_transform_radius = temp4;

            defaultFractalSettings();
        } else if (s.plane_type == SHEAR_PLANE) {
            JTextField field_scale_real = new JTextField();
            field_scale_real.setText("" + s.plane_transform_scales[0]);

            field_scale_real.addAncestorListener(new RequestFocusListener());

            JTextField field_scale_imaginary = new JTextField();
            field_scale_imaginary.setText("" + s.plane_transform_scales[1]);

            Object[] message = {
                " ",
                "Set the shear scaling.",
                "Scale Real:", field_scale_real,
                "Scale Imaginary:", field_scale_imaginary,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Shear", JOptionPane.OK_CANCEL_OPTION);

            double temp3, temp4;

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_scale_real.getText());
                    temp4 = Double.parseDouble(field_scale_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            s.plane_transform_scales[0] = temp3 == 0.0 ? 0.0 : temp3;
            s.plane_transform_scales[1] = temp4 == 0.0 ? 0.0 : temp4;

            defaultFractalSettings();
        } else if (s.plane_type == RIPPLES_PLANE) {
            JTextField field_scale_real = new JTextField();
            field_scale_real.setText("" + s.plane_transform_scales[0]);

            field_scale_real.addAncestorListener(new RequestFocusListener());

            JTextField field_scale_imaginary = new JTextField();
            field_scale_imaginary.setText("" + s.plane_transform_scales[1]);

            JTextField field_wavelength_real = new JTextField();
            field_wavelength_real.setText("" + s.plane_transform_wavelength[0]);

            JTextField field_wavelength_imaginary = new JTextField();
            field_wavelength_imaginary.setText("" + s.plane_transform_wavelength[1]);

            final JComboBox wavetype_combobox = new JComboBox(waveTypes);
            wavetype_combobox.setFocusable(false);
            wavetype_combobox.setToolTipText("Sets type of wave.");
            wavetype_combobox.setSelectedIndex(s.waveType);

            Object[] message = {
                " ",
                "Set the ripple's amplitude.",
                "Amplitude Real:", field_scale_real,
                "Amplitude Imaginary:", field_scale_imaginary,
                " ",
                "Set the ripple's wavelength.",
                "Wavelength Real:", field_wavelength_real,
                "Wavelength Imaginary:", field_wavelength_imaginary,
                " ",
                "Set the ripple's wave type.",
                "Wave Type:",
                wavetype_combobox,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Ripples", JOptionPane.OK_CANCEL_OPTION);

            double temp3, temp4, temp7, temp8;

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_scale_real.getText());
                    temp4 = Double.parseDouble(field_scale_imaginary.getText());
                    temp7 = Double.parseDouble(field_wavelength_real.getText());
                    temp8 = Double.parseDouble(field_wavelength_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            s.plane_transform_scales[0] = temp3 == 0.0 ? 0.0 : temp3;
            s.plane_transform_scales[1] = temp4 == 0.0 ? 0.0 : temp4;
            s.plane_transform_wavelength[0] = temp7 == 0.0 ? 0.0 : temp7;
            s.plane_transform_wavelength[1] = temp8 == 0.0 ? 0.0 : temp8;
            s.waveType = wavetype_combobox.getSelectedIndex();

            defaultFractalSettings();
        } else if (s.plane_type == KALEIDOSCOPE_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + s.plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            JTextField field_rotation2 = new JTextField();
            field_rotation2.setText("" + s.plane_transform_angle2);

            final JTextField field_real = new JTextField();

            if (s.plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (s.plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + s.plane_transform_radius);

            JTextField field_sides = new JTextField();
            field_sides.setText("" + s.plane_transform_sides);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (s.plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + s.plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if (s.plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + s.plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp5 = Double.parseDouble(field_rotation2.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                    temp6 = Integer.parseInt(field_sides.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            if (temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp6 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Kaleidoscope sides must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.plane_transform_center[0] = tempReal;
            s.plane_transform_center[1] = tempImaginary;
            s.plane_transform_angle = temp3;
            s.plane_transform_radius = temp4;
            s.plane_transform_angle2 = temp5;
            s.plane_transform_sides = temp6;

            defaultFractalSettings();
        } else if (s.plane_type == PINCH_PLANE) {
            JTextField field_rotation = new JTextField();
            field_rotation.setText("" + s.plane_transform_angle);

            field_rotation.addAncestorListener(new RequestFocusListener());

            final JTextField field_real = new JTextField();

            if (s.plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (s.plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + s.plane_transform_radius);

            JTextField field_amount = new JTextField();
            field_amount.setText("" + s.plane_transform_amount);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (s.plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + s.plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if (s.plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + s.plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_rotation.getText());
                    temp4 = Double.parseDouble(field_radius.getText());
                    temp5 = Double.parseDouble(field_amount.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            if (temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Pinch radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.plane_transform_center[0] = tempReal;
            s.plane_transform_center[1] = tempImaginary;
            s.plane_transform_angle = temp3;
            s.plane_transform_radius = temp4;
            s.plane_transform_amount = temp5 == 0.0 ? 0.0 : temp5;

            defaultFractalSettings();
        } else if (s.plane_type == FOLDUP_PLANE || s.plane_type == FOLDDOWN_PLANE || s.plane_type == FOLDRIGHT_PLANE || s.plane_type == FOLDLEFT_PLANE || s.plane_type == INFLECTION_PLANE || s.plane_type == BIPOLAR_PLANE || s.plane_type == INVERSED_BIPOLAR_PLANE) {

            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if (s.plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (s.plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.plane_transform_center[1]);
            }

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (s.plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + s.plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if (s.plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + s.plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

                        field_real.setText("" + p.x);
                        field_real.setEditable(false);
                        field_imaginary.setText("" + p.y);
                        field_imaginary.setEditable(false);
                    }
                }
            });

            String str = "Set the point about the fold (User Point).";
            String title = "Fold";
            if (s.plane_type == INFLECTION_PLANE) {
                str = "Set the point about the inflection (User Point).";
                title = "Inflection";
            } else if (s.plane_type == BIPOLAR_PLANE || s.plane_type == INVERSED_BIPOLAR_PLANE) {
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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            s.plane_transform_center[0] = tempReal;
            s.plane_transform_center[1] = tempImaginary;

            defaultFractalSettings();
        } else if (s.plane_type == FOLDIN_PLANE || s.plane_type == FOLDOUT_PLANE) {

            JTextField field_radius = new JTextField();
            field_radius.setText("" + s.plane_transform_radius);
            field_radius.addAncestorListener(new RequestFocusListener());

            Object[] message = {
                " ",
                "Set the radius of the fold.",
                "Radius:", field_radius,
                " "};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Fold", JOptionPane.OK_CANCEL_OPTION);

            double temp3;

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp3 = Double.parseDouble(field_radius.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            if (temp3 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Fold radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.plane_transform_radius = temp3;

            defaultFractalSettings();
        } else if (s.plane_type == CIRCLEINVERSION_PLANE) {

            final JTextField field_real = new JTextField();
            field_real.addAncestorListener(new RequestFocusListener());

            if (s.plane_transform_center[0] == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + s.plane_transform_center[0]);
            }

            final JTextField field_imaginary = new JTextField();

            if (s.plane_transform_center[1] == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + s.plane_transform_center[1]);
            }

            JTextField field_radius = new JTextField();
            field_radius.setText("" + s.plane_transform_radius);

            final JCheckBox current_center = new JCheckBox("Current Center");
            current_center.setSelected(false);
            current_center.setFocusable(false);

            current_center.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    if (!current_center.isSelected()) {
                        if (s.plane_transform_center[0] == 0) {
                            field_real.setText("" + 0.0);
                        } else {
                            field_real.setText("" + s.plane_transform_center[0]);
                        }

                        field_real.setEditable(true);

                        if (s.plane_transform_center[1] == 0) {
                            field_imaginary.setText("" + 0.0);
                        } else {
                            field_imaginary.setText("" + s.plane_transform_center[1]);
                        }
                        field_imaginary.setEditable(true);
                    } else {
                        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    temp4 = Double.parseDouble(field_radius.getText());
                    tempReal = Double.parseDouble(field_real.getText());
                    tempImaginary = Double.parseDouble(field_imaginary.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    planes[oldSelected].setSelected(true);
                    s.plane_type = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                return;
            }

            if (temp4 <= 0) {
                main_panel.repaint();
                planes[oldSelected].setSelected(true);
                s.plane_type = oldSelected;
                JOptionPane.showMessageDialog(scroll_pane, "Circle inversion radius must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.plane_transform_center[0] = tempReal;
            s.plane_transform_center[1] = tempImaginary;
            s.plane_transform_radius = temp4;

            defaultFractalSettings();
        } else {
            defaultFractalSettings();
        }
    }

    public void setBailoutTest(int temp) {

        resetOrbit();
        int oldSelection = s.bailout_test_algorithm;
        s.bailout_test_algorithm = temp;

        if (s.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {

            String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + s.n_norm + " as the N-Norm.\nEnter the new N-Norm number.", "N-Norm", JOptionPane.QUESTION_MESSAGE);

            try {
                double temp3 = Double.parseDouble(ans);

                s.n_norm = temp3;

            } catch (Exception ex) {
                if (ans == null) {
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    s.bailout_test_algorithm = oldSelection;
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    s.bailout_test_algorithm = oldSelection;
                }
                return;
            }
        } else if (s.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(s.bailout_test_user_formula);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(s.bailout_test_user_formula2);

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

            if (s.bailout_test_comparison == GREATER) { // >
                combo_box_greater.setSelectedIndex(0);
                combo_box_equal.setSelectedIndex(1);
                combo_box_less.setSelectedIndex(1);
            } else if (s.bailout_test_comparison == GREATER_EQUAL) { // >=
                combo_box_greater.setSelectedIndex(0);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(1);
            } else if (s.bailout_test_comparison == LOWER) { // <
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(1);
                combo_box_less.setSelectedIndex(0);
            } else if (s.bailout_test_comparison == LOWER_EQUAL) { // <=
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(0);
            } else if (s.bailout_test_comparison == EQUAL) { // ==
                combo_box_greater.setSelectedIndex(1);
                combo_box_equal.setSelectedIndex(0);
                combo_box_less.setSelectedIndex(1);
            } else if (s.bailout_test_comparison == NOT_EQUAL) { // !=
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

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, center, size, sizei, v1 - v30, point");

            Object[] message33 = {
                labels33,
                " ",
                "Set the bailout condition.",
                formula_panel_cond1,
                formula_panel_cond11,
                formula_panel_cond12,
                formula_panel_cond13,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message33, "User Bailout Condition", JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                try {
                    s.parser.parse(field_condition.getText());

                    if (s.parser.foundCbail()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail cannot be used in left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        bailout_tests[oldSelection].setSelected(true);
                        s.bailout_test_algorithm = oldSelection;
                        return;
                    }

                    s.parser.parse(field_condition2.getText());

                    if (s.parser.foundCbail()) {
                        JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        bailout_tests[oldSelection].setSelected(true);
                        s.bailout_test_algorithm = oldSelection;
                        return;
                    }

                } catch (ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    bailout_tests[oldSelection].setSelected(true);
                    s.bailout_test_algorithm = oldSelection;
                    return;
                }
            } else {
                main_panel.repaint();
                bailout_tests[oldSelection].setSelected(true);
                s.bailout_test_algorithm = oldSelection;
                return;
            }

            if ((combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0)
                    || (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1)) {

                JOptionPane.showMessageDialog(scroll_pane, "You cannot set all the outcomes to Escaped or Not Escaped.", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                bailout_tests[oldSelection].setSelected(true);
                s.bailout_test_algorithm = oldSelection;
                return;
            }

            s.bailout_test_user_formula = field_condition.getText();
            s.bailout_test_user_formula2 = field_condition2.getText();

            if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 1) { // >
                s.bailout_test_comparison = GREATER;
            } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // >=
                s.bailout_test_comparison = GREATER_EQUAL;
            } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // <
                s.bailout_test_comparison = LOWER;
            } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 0) { // <=
                s.bailout_test_comparison = LOWER_EQUAL;
            } else if (combo_box_greater.getSelectedIndex() == 1 && combo_box_equal.getSelectedIndex() == 0 && combo_box_less.getSelectedIndex() == 1) { // ==
                s.bailout_test_comparison = EQUAL;
            } else if (combo_box_greater.getSelectedIndex() == 0 && combo_box_equal.getSelectedIndex() == 1 && combo_box_less.getSelectedIndex() == 0) { // !=
                s.bailout_test_comparison = NOT_EQUAL;
            }
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

    public void setOutColoringMode(int temp) {

        resetOrbit();
        int oldSelected = s.out_coloring_algorithm;

        s.out_coloring_algorithm = temp;

        for (int k = 0; k < fractal_functions.length; k++) {
            if (k != PARHALLEY3 && k != PARHALLEY4 && k != PARHALLEYGENERALIZED3 && k != PARHALLEYGENERALIZED8 && k != PARHALLEYSIN && k != PARHALLEYCOS && k != PARHALLEYPOLY && k != PARHALLEYFORMULA
                    && k != LAGUERRE3 && k != LAGUERRE4 && k != LAGUERREGENERALIZED3 && k != LAGUERREGENERALIZED8 && k != LAGUERRESIN && k != LAGUERRECOS && k != LAGUERREPOLY && k != LAGUERREFORMULA
                    && k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                    && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                    && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                    && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                    && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                    && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                    && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                fractal_functions[k].setEnabled(true);
            }

            if (k == SIERPINSKI_GASKET && !s.julia && !julia_map && !s.perturbation && !s.init_val) {
                fractal_functions[k].setEnabled(true);
            }
        }

        if (s.out_coloring_algorithm == DISTANCE_ESTIMATOR || s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else if (s.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES2 || s.out_coloring_algorithm == ESCAPE_TIME_FIELD_LINES || s.out_coloring_algorithm == ESCAPE_TIME_ESCAPE_RADIUS || s.out_coloring_algorithm == ESCAPE_TIME_GRID || s.out_coloring_algorithm == BIOMORPH || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER2 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER3 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER4 || s.out_coloring_algorithm == ESCAPE_TIME_GAUSSIAN_INTEGER5 || s.out_coloring_algorithm == ITERATIONS_PLUS_RE || s.out_coloring_algorithm == ITERATIONS_PLUS_IM || s.out_coloring_algorithm == ITERATIONS_PLUS_RE_DIVIDE_IM || s.out_coloring_algorithm == ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM || s.out_coloring_algorithm == ESCAPE_TIME_ALGORITHM2 || s.out_coloring_algorithm == BANDED) {
            rootFindingMethodsSetEnabled(false);
        } else if (s.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {

            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.setPreferredSize(new Dimension(495, 190));
            //tabbedPane.setPreferredSize(new Dimension(550, 210));
            tabbedPane.setFocusable(false);

            JTextField field_formula = new JTextField(runsOnWindows ? 50 : 45);//48
            field_formula.setText(s.outcoloring_formula);
            field_formula.addAncestorListener(new RequestFocusListener());

            JPanel formula_panel = new JPanel();
            formula_panel.setLayout(new FlowLayout());

            formula_panel.add(new JLabel("out ="));
            formula_panel.add(field_formula);

            tabbedPane.addTab("Normal", formula_panel);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(s.user_outcoloring_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(s.user_outcoloring_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond1.setText(s.user_outcoloring_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond2.setText(s.user_outcoloring_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);//35
            field_formula_cond3.setText(s.user_outcoloring_condition_formula[2]);

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

            Object[] labels33 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point");

            tabbedPane.setSelectedIndex(s.user_out_coloring_algorithm);

            Object[] message3 = {
                labels33,
                " ",
                "Set the out coloring formula. Only the real component of the complex number will be used.",
                tabbedPane,};

            int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User Out Coloring Method", JOptionPane.OK_CANCEL_OPTION);

            if (res == JOptionPane.OK_OPTION) {
                try {
                    if (tabbedPane.getSelectedIndex() == 0) {
                        s.parser.parse(field_formula.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                    } else {
                        s.parser.parse(field_condition.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        s.parser.parse(field_condition2.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        s.parser.parse(field_formula_cond1.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        s.parser.parse(field_formula_cond2.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }

                        s.parser.parse(field_formula_cond3.getText());

                        if (s.isConvergingType()) {
                            if (s.parser.foundBail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        } else {
                            if (s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                main_panel.repaint();
                                out_coloring_modes[oldSelected].setSelected(true);
                                s.out_coloring_algorithm = oldSelected;
                                return;
                            }
                        }
                    }

                } catch (ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    main_panel.repaint();
                    out_coloring_modes[oldSelected].setSelected(true);
                    s.out_coloring_algorithm = oldSelected;
                    return;
                }
            } else {
                main_panel.repaint();
                out_coloring_modes[oldSelected].setSelected(true);
                s.out_coloring_algorithm = oldSelected;
                return;
            }

            s.user_out_coloring_algorithm = tabbedPane.getSelectedIndex();

            if (s.user_out_coloring_algorithm == 0) {
                s.outcoloring_formula = field_formula.getText();
            } else {
                s.user_outcoloring_conditions[0] = field_condition.getText();
                s.user_outcoloring_conditions[1] = field_condition2.getText();
                s.user_outcoloring_condition_formula[0] = field_formula_cond1.getText();
                s.user_outcoloring_condition_formula[1] = field_formula_cond2.getText();
                s.user_outcoloring_condition_formula[2] = field_formula_cond3.getText();
            }

            if (!julia_map && !s.julia && !s.perturbation && !s.init_val && !s.isRootFindingMethod()) {
                rootFindingMethodsSetEnabled(true);
            }
        } else {
            if (!julia_map && !s.julia && !s.perturbation && !s.init_val && !s.isRootFindingMethod()) {
                rootFindingMethodsSetEnabled(true);
            }
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
        int oldSelected = s.in_coloring_algorithm;

        s.in_coloring_algorithm = temp;

        if (s.in_coloring_algorithm == MAX_ITERATIONS) {
            if (!s.ds.domain_coloring && !s.isConvergingType() && s.function != SIERPINSKI_GASKET && !s.ots.useTraps) {
                options_menu.getPeriodicityChecking().setEnabled(true);
            }
        } else {
            if (s.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                JTabbedPane tabbedPane = new JTabbedPane();
                //tabbedPane.setPreferredSize(new Dimension(550, 210));
                tabbedPane.setPreferredSize(new Dimension(495, 190));
                tabbedPane.setFocusable(false);

                JTextField field_formula = new JTextField(runsOnWindows ? 50 : 45);//48
                field_formula.setText(s.incoloring_formula);
                field_formula.addAncestorListener(new RequestFocusListener());

                JPanel formula_panel = new JPanel();
                formula_panel.setLayout(new FlowLayout());

                formula_panel.add(new JLabel("in ="));
                formula_panel.add(field_formula);

                tabbedPane.addTab("Normal", formula_panel);

                JPanel formula_panel_cond1 = new JPanel();
                formula_panel_cond1.setLayout(new GridLayout(2, 2));

                JTextField field_condition = new JTextField(24);
                field_condition.setText(s.user_incoloring_conditions[0]);
                field_condition.addAncestorListener(new RequestFocusListener());

                JTextField field_condition2 = new JTextField(24);
                field_condition2.setText(s.user_incoloring_conditions[1]);

                formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
                formula_panel_cond1.add(field_condition);
                formula_panel_cond1.add(field_condition2);

                JTextField field_formula_cond1 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond1.setText(s.user_incoloring_condition_formula[0]);

                JTextField field_formula_cond2 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond2.setText(s.user_incoloring_condition_formula[1]);

                JTextField field_formula_cond3 = new JTextField(runsOnWindows ? 45 : 40);//35
                field_formula_cond3.setText(s.user_incoloring_condition_formula[2]);

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

                Object[] labels3 = createUserFormulaLabels("z, c, s, p, pp, n, maxn, bail, cbail, center, size, sizei, v1 - v30, point");

                tabbedPane.setSelectedIndex(s.user_in_coloring_algorithm);

                Object[] message3 = {
                    labels3,
                    " ",
                    "Set the in coloring formula. Only the real component of the complex number will be used.",
                    tabbedPane,};

                int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "User In Coloring Method", JOptionPane.OK_CANCEL_OPTION);

                if (res == JOptionPane.OK_OPTION) {
                    try {
                        if (tabbedPane.getSelectedIndex() == 0) {
                            s.parser.parse(field_formula.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }
                        } else {
                            s.parser.parse(field_condition.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }

                            s.parser.parse(field_condition2.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }

                            s.parser.parse(field_formula_cond1.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }

                            s.parser.parse(field_formula_cond2.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }

                            s.parser.parse(field_formula_cond3.getText());

                            if (s.isConvergingType()) {
                                if (s.parser.foundBail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: bail can only be used in escaping type fractals.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            } else {
                                if (s.parser.foundCbail()) {
                                    JOptionPane.showMessageDialog(scroll_pane, "The variable: cbail can only be used in converging type fractals\n(Root finding methods, Nova, User converging formulas).", "Error!", JOptionPane.ERROR_MESSAGE);
                                    main_panel.repaint();
                                    in_coloring_modes[oldSelected].setSelected(true);
                                    s.in_coloring_algorithm = oldSelected;
                                    return;
                                }
                            }
                        }

                    } catch (ParserException e) {
                        JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                        main_panel.repaint();
                        in_coloring_modes[oldSelected].setSelected(true);
                        s.in_coloring_algorithm = oldSelected;
                        return;
                    }
                } else {
                    main_panel.repaint();
                    in_coloring_modes[oldSelected].setSelected(true);
                    s.in_coloring_algorithm = oldSelected;
                    return;
                }

                s.user_in_coloring_algorithm = tabbedPane.getSelectedIndex();

                if (s.user_in_coloring_algorithm == 0) {
                    s.incoloring_formula = field_formula.getText();
                } else {
                    s.user_incoloring_conditions[0] = field_condition.getText();
                    s.user_incoloring_conditions[1] = field_condition2.getText();
                    s.user_incoloring_condition_formula[0] = field_formula_cond1.getText();
                    s.user_incoloring_condition_formula[1] = field_formula_cond2.getText();
                    s.user_incoloring_condition_formula[2] = field_formula_cond3.getText();
                }
            }
            options_menu.getPeriodicityChecking().setEnabled(false);
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

    public void setSmoothing() {

        resetOrbit();
        final JCheckBox enable_smoothing = new JCheckBox("Smoothing");
        enable_smoothing.setSelected(s.smoothing);
        enable_smoothing.setFocusable(false);

        String[] escaping_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox escaping_alg_combo = new JComboBox(escaping_algorithm_str);
        escaping_alg_combo.setSelectedIndex(s.escaping_smooth_algorithm);
        escaping_alg_combo.setFocusable(false);
        escaping_alg_combo.setToolTipText("Sets the smooting algorithm for escaping functions.");

        String[] converging_algorithm_str = {"Algorithm 1", "Algorithm 2"};

        JComboBox converging_alg_combo = new JComboBox(converging_algorithm_str);
        converging_alg_combo.setSelectedIndex(s.converging_smooth_algorithm);
        converging_alg_combo.setFocusable(false);
        converging_alg_combo.setToolTipText("Sets the smooting algorithm for converging functions.");

        final JComboBox combo_box_color_interp = new JComboBox(color_interp_str);
        combo_box_color_interp.setSelectedIndex(s.color_smoothing_method);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the color interpolation method.");

        if (s.ds.domain_coloring) {
            escaping_alg_combo.setEnabled(false);
            converging_alg_combo.setEnabled(false);
        }

        Object[] message = {
            " ",
            enable_smoothing,
            " ",
            "Set the smoothing algorithm for escaping and converging functions.",
            "Escaping:", escaping_alg_combo,
            "Converging:", converging_alg_combo,
            " ",
            "Set the color interpolation method.",
            combo_box_color_interp,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Smoothing", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {

            s.smoothing = enable_smoothing.isSelected();
            s.escaping_smooth_algorithm = escaping_alg_combo.getSelectedIndex();
            s.converging_smooth_algorithm = converging_alg_combo.getSelectedIndex();
            s.color_smoothing_method = combo_box_color_interp.getSelectedIndex();
        } else {
            main_panel.repaint();
            return;
        }

        if (s.color_choice == CUSTOM_PALETTE_ID) {
            ThreadDraw.palette = new CustomPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.scale_factor_palette_val, s.processing_alg, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        } else {
            ThreadDraw.palette = new PresetPalette(s.color_choice, s.smoothing, s.special_color, s.color_smoothing_method, s.special_use_palette_color);
        }

        updateColorPalettesMenu();

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

    public void setBumpMap() {

        resetOrbit();
        final JCheckBox enable_bump_map = new JCheckBox("Bump Mapping");
        enable_bump_map.setSelected(s.bms.bump_map);
        enable_bump_map.setFocusable(false);

        JSlider direction_of_light = new JSlider(JSlider.HORIZONTAL, -360, 360, ((int) (s.bms.lightDirectionDegrees)));
        direction_of_light.setPreferredSize(new Dimension(300, 40));
        direction_of_light.setMajorTickSpacing(90);
        direction_of_light.setMinorTickSpacing(1);
        direction_of_light.setToolTipText("Sets the direction of light.");
        //color_blend.setPaintTicks(true);
        direction_of_light.setPaintLabels(true);
        //direction_of_light.setSnapToTicks(true);
        direction_of_light.setFocusable(false);

        JSlider depth = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.bms.bumpMappingDepth)));
        depth.setPreferredSize(new Dimension(300, 40));
        depth.setMajorTickSpacing(25);
        depth.setMinorTickSpacing(1);
        depth.setToolTipText("Sets the depth of the effect.");
        //color_blend.setPaintTicks(true);
        depth.setPaintLabels(true);
        //depth.setSnapToTicks(true);
        depth.setFocusable(false);

        JSlider strength = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.bms.bumpMappingStrength)));
        strength.setPreferredSize(new Dimension(300, 40));
        strength.setMajorTickSpacing(25);
        strength.setMinorTickSpacing(1);
        strength.setToolTipText("Sets the strength of the effect.");
        //color_blend.setPaintTicks(true);
        strength.setPaintLabels(true);
        //strength.setSnapToTicks(true);
        strength.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.bms.bm_noise_reducing_factor);

        final JComboBox bump_transfer_functions_opt = new JComboBox(bumpTransferNames);
        bump_transfer_functions_opt.setSelectedIndex(s.bms.bump_transfer_function);
        bump_transfer_functions_opt.setFocusable(false);
        bump_transfer_functions_opt.setToolTipText("Sets the transfer function.");

        JTextField bump_transfer_factor_field = new JTextField(20);
        bump_transfer_factor_field.setText("" + s.bms.bump_transfer_factor);

        bump_transfer_factor_field.addAncestorListener(new RequestFocusListener());

        JPanel panel = new JPanel();
        panel.add(bump_transfer_functions_opt);
        panel.add(bump_transfer_factor_field);

        final JComboBox bump_processing_method_opt = new JComboBox(bumpProcessingMethod);
        bump_processing_method_opt.setSelectedIndex(s.bms.bumpProcessing);
        bump_processing_method_opt.setFocusable(false);
        bump_processing_method_opt.setToolTipText("Sets the image processing method.");
        bump_processing_method_opt.setPreferredSize(new Dimension(150, 20));

        final JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.bms.bump_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        if (s.bms.bumpProcessing == 0) {
            color_blend_opt.setEnabled(false);
        }

        bump_processing_method_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (bump_processing_method_opt.getSelectedIndex() == 0) {
                    color_blend_opt.setEnabled(false);
                } else {
                    color_blend_opt.setEnabled(true);
                }
            }

        });

        JPanel p2 = new JPanel();
        p2.add(bump_processing_method_opt);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 2));
        p1.add(new JLabel("Image Processing Method:", SwingConstants.HORIZONTAL));
        p1.add(new JLabel("Color Blending:", SwingConstants.HORIZONTAL));
        p1.add(p2);
        p1.add(color_blend_opt);

        Object[] message = {
            " ",
            enable_bump_map,
            " ",
            "Set the direction of light in degrees, depth, and strength.",
            "Direction of light:", direction_of_light,
            " ",
            "Depth:", depth,
            " ",
            "Strength:", strength,
            " ",
            "Set the tranfer function and the factor.",
            "Transfer Function and Factor:",
            panel,
            " ",
            "Set the image processing method.",
            p1,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Bump Mapping", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            double temp = 0, temp2 = 0;
            try {
                temp = Double.parseDouble(noise_factor_field.getText());
                temp2 = Double.parseDouble(bump_transfer_factor_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }

            if (temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp2 <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The transfer factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (greedy_algorithm && enable_bump_map.isSelected() && !julia_map && !s.d3s.d3) {
                JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            s.bms.bump_map = enable_bump_map.isSelected();
            s.bms.lightDirectionDegrees = direction_of_light.getValue();
            s.bms.bumpMappingDepth = depth.getValue();
            s.bms.bumpMappingStrength = strength.getValue();
            s.bms.bm_noise_reducing_factor = temp;
            s.bms.bump_transfer_function = bump_transfer_functions_opt.getSelectedIndex();
            s.bms.bump_transfer_factor = temp2;
            s.bms.bumpProcessing = bump_processing_method_opt.getSelectedIndex();
            s.bms.bump_blending = color_blend_opt.getValue() / 100.0;

            if (!s.smoothing && s.bms.bump_map) {
                JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
            }

            setOptions(false);

            progress.setValue(0);

            resetImage();

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            whole_image_done = false;

            if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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
        } else {
            main_panel.repaint();
            return;
        }

    }

    public void setRainbowPalette() {

        resetOrbit();
        JTextField rainbow_palette_factor_field = new JTextField();
        rainbow_palette_factor_field.setText("" + s.rps.rainbow_palette_factor);

        rainbow_palette_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_rainbow_palette = new JCheckBox("Rainbow Palette");
        enable_rainbow_palette.setSelected(s.rps.rainbow_palette);
        enable_rainbow_palette.setFocusable(false);

        JTextField rainbow_offset_field = new JTextField();
        rainbow_offset_field.setText("" + s.rps.rainbow_offset);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.rps.rp_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.rps.rp_noise_reducing_factor);

        final JComboBox rainbow_coloring_method_opt = new JComboBox(rainbowMethod);
        rainbow_coloring_method_opt.setSelectedIndex(s.rps.rainbow_algorithm);
        rainbow_coloring_method_opt.setFocusable(false);
        rainbow_coloring_method_opt.setToolTipText("Sets the color transfer method.");

        if (s.rps.rainbow_algorithm != 0) {
            rainbow_offset_field.setEnabled(false);
        }

        rainbow_coloring_method_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rainbow_coloring_method_opt.getSelectedIndex() != 0) {
                    rainbow_offset_field.setEnabled(false);
                } else {
                    rainbow_offset_field.setEnabled(true);
                }
            }

        });

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
            "Set the color transfer method.",
            "Color Transfer Method:", rainbow_coloring_method_opt,
            " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Rainbow Palette", JOptionPane.OK_CANCEL_OPTION);

        double temp;
        double temp2;
        int temp3;
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(rainbow_palette_factor_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
                temp3 = Integer.parseInt(rainbow_offset_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }

        } else {
            main_panel.repaint();
            return;
        }

        if (temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The rainbow palette factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temp3 < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (greedy_algorithm && enable_rainbow_palette.isSelected() && !julia_map && !s.d3s.d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        s.rps.rainbow_palette = enable_rainbow_palette.isSelected();
        s.rps.rainbow_palette_factor = temp;
        s.rps.rp_noise_reducing_factor = temp2;
        s.rps.rainbow_offset = temp3;
        s.rps.rp_blending = color_blend_opt.getValue() / 100.0;
        s.rps.rainbow_algorithm = rainbow_coloring_method_opt.getSelectedIndex();

        if (!s.smoothing && s.rps.rainbow_palette) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setFakeDistanceEstimation() {

        resetOrbit();
        JTextField fake_de_factor_field = new JTextField();
        fake_de_factor_field.setText("" + s.fdes.fake_de_factor);

        fake_de_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_fake_de = new JCheckBox("Fake Distance Estimation");
        enable_fake_de.setSelected(s.fdes.fake_de);
        enable_fake_de.setFocusable(false);

        final JCheckBox invert_fake_de = new JCheckBox("Invert Coloring");
        invert_fake_de.setSelected(s.fdes.inverse_fake_dem);
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
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(fake_de_factor_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The fake distance estimation factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (greedy_algorithm && enable_fake_de.isSelected() && !julia_map && !s.d3s.d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        s.fdes.fake_de = enable_fake_de.isSelected();
        s.fdes.fake_de_factor = temp;
        s.fdes.inverse_fake_dem = invert_fake_de.isSelected();

        if (!s.smoothing && s.fdes.fake_de) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setGreyScaleColoring() {

        resetOrbit();
        final JCheckBox enable_greyscale_coloring = new JCheckBox("Greyscale Coloring");
        enable_greyscale_coloring.setSelected(s.gss.greyscale_coloring);
        enable_greyscale_coloring.setFocusable(false);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.gss.gs_noise_reducing_factor);

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
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp2 = Double.parseDouble(noise_factor_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (greedy_algorithm && enable_greyscale_coloring.isSelected() && !julia_map && !s.d3s.d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        s.gss.greyscale_coloring = enable_greyscale_coloring.isSelected();
        s.gss.gs_noise_reducing_factor = temp2;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setEntropyColoring() {

        resetOrbit();
        JTextField entropy_factor_field = new JTextField();
        entropy_factor_field.setText("" + s.ens.entropy_palette_factor);

        entropy_factor_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_entropy_coloring = new JCheckBox("Entropy Coloring");
        enable_entropy_coloring.setSelected(s.ens.entropy_coloring);
        enable_entropy_coloring.setFocusable(false);

        JTextField entropy_offset_field = new JTextField();
        entropy_offset_field.setText("" + s.ens.entropy_offset);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.ens.en_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.ens.en_noise_reducing_factor);

        final JComboBox entropy_coloring_method_opt = new JComboBox(entropyMethod);
        entropy_coloring_method_opt.setSelectedIndex(s.ens.entropy_algorithm);
        entropy_coloring_method_opt.setFocusable(false);
        entropy_coloring_method_opt.setToolTipText("Sets the color transfer method.");

        if (s.ens.entropy_algorithm != 0) {
            entropy_offset_field.setEnabled(false);
        }

        entropy_coloring_method_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (entropy_coloring_method_opt.getSelectedIndex() != 0) {
                    entropy_offset_field.setEnabled(false);
                } else {
                    entropy_offset_field.setEnabled(true);
                }
            }

        });

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
            "Set the color transfer method.",
            "Color Transfer Method:", entropy_coloring_method_opt,
            " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Entropy Coloring", JOptionPane.OK_CANCEL_OPTION);

        double temp, temp2;
        int temp3;
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(entropy_factor_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
                temp3 = Integer.parseInt(entropy_offset_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The entropy coloring factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temp3 < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (greedy_algorithm && enable_entropy_coloring.isSelected() && !julia_map && !s.d3s.d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        s.ens.entropy_coloring = enable_entropy_coloring.isSelected();
        s.ens.entropy_palette_factor = temp;
        s.ens.en_noise_reducing_factor = temp2;
        s.ens.entropy_offset = temp3;
        s.ens.en_blending = color_blend_opt.getValue() / 100.0;
        s.ens.entropy_algorithm = entropy_coloring_method_opt.getSelectedIndex();

        if (!s.smoothing && s.ens.entropy_coloring) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setOffsetColoring() {

        resetOrbit();
        JTextField offset_field = new JTextField();
        offset_field.setText("" + s.ofs.post_process_offset);

        offset_field.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_offset_coloring = new JCheckBox("Offset Coloring");
        enable_offset_coloring.setSelected(s.ofs.offset_coloring);
        enable_offset_coloring.setFocusable(false);

        JSlider color_blend_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (s.ofs.of_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.ofs.of_noise_reducing_factor);

        Object[] message = {
            " ",
            enable_offset_coloring,
            " ",
            "Set the coloring offset.",
            "Coloring Offset:", offset_field,
            " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the image noise reducing factor.",
            "Noise reducing factor:",
            noise_factor_field,
            " "};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Offset Coloring", JOptionPane.OK_CANCEL_OPTION);

        int temp;
        double temp2;
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Integer.parseInt(offset_field.getText());
                temp2 = Double.parseDouble(noise_factor_field.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp < 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (temp2 <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The noise reducing factor must be greater that 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (greedy_algorithm && enable_offset_coloring.isSelected() && !julia_map && !s.d3s.d3) {
            JOptionPane.showMessageDialog(scroll_pane, "Greedy Drawing Algorithm is enabled, which creates glitches in the image.\nYou should disable it for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        s.ofs.offset_coloring = enable_offset_coloring.isSelected();
        s.ofs.post_process_offset = temp;
        s.ofs.of_noise_reducing_factor = temp2;
        s.ofs.of_blending = color_blend_opt.getValue() / 100.0;

        if (!s.smoothing && s.ofs.offset_coloring) {
            JOptionPane.showMessageDialog(scroll_pane, "Smoothing is disabled.\nYou should enable smoothing for a better result.", "Warning!", JOptionPane.WARNING_MESSAGE);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        whole_image_done = false;

        if (s.fs.filters[ANTIALIASING] || s.ots.useTraps) {
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

    public void setExteriorDistanceEstimation() {

        resetOrbit();
        JTextField dem_factor = new JTextField();
        dem_factor.setText("" + s.exterior_de_factor);

        dem_factor.addAncestorListener(new RequestFocusListener());

        final JCheckBox enable_dem = new JCheckBox("Distance Estimation");
        enable_dem.setSelected(s.exterior_de);
        enable_dem.setFocusable(false);

        final JCheckBox invert_de = new JCheckBox("Invert Coloring");
        invert_de.setSelected(s.inverse_dem);
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
        if (res == JOptionPane.OK_OPTION) {
            try {
                temp = Double.parseDouble(dem_factor.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        if (temp <= 0) {
            main_panel.repaint();
            JOptionPane.showMessageDialog(scroll_pane, "The distance estimation factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        s.exterior_de = enable_dem.isSelected();
        s.exterior_de_factor = temp;
        s.inverse_dem = invert_de.isSelected();

        if (s.exterior_de) {
            for (int k = 1; k < fractal_functions.length; k++) {
                fractal_functions[k].setEnabled(false);
            }
        } else {
            for (int k = 0; k < fractal_functions.length; k++) {
                if (k != PARHALLEY3 && k != PARHALLEY4 && k != PARHALLEYGENERALIZED3 && k != PARHALLEYGENERALIZED8 && k != PARHALLEYSIN && k != PARHALLEYCOS && k != PARHALLEYPOLY && k != PARHALLEYFORMULA
                        && k != LAGUERRE3 && k != LAGUERRE4 && k != LAGUERREGENERALIZED3 && k != LAGUERREGENERALIZED8 && k != LAGUERRESIN && k != LAGUERRECOS && k != LAGUERREPOLY && k != LAGUERREFORMULA
                        && k != MULLER3 && k != MULLER4 && k != MULLERGENERALIZED3 && k != MULLERGENERALIZED8 && k != MULLERSIN && k != MULLERCOS && k != MULLERPOLY && k != MULLERFORMULA
                        && k != SIERPINSKI_GASKET && k != NEWTON3 && k != STEFFENSENPOLY && k != NEWTON4 && k != NEWTONGENERALIZED3 && k != NEWTONGENERALIZED8 && k != NEWTONSIN && k != NEWTONCOS && k != NEWTONPOLY
                        && k != HALLEY3 && k != HALLEY4 && k != HALLEYGENERALIZED3 && k != HALLEYGENERALIZED8 && k != HALLEYSIN && k != HALLEYCOS && k != HALLEYPOLY
                        && k != SCHRODER3 && k != SCHRODER4 && k != SCHRODERGENERALIZED3 && k != SCHRODERGENERALIZED8 && k != SCHRODERSIN && k != SCHRODERCOS && k != SCHRODERPOLY
                        && k != HOUSEHOLDER3 && k != HOUSEHOLDER4 && k != HOUSEHOLDERGENERALIZED3 && k != HOUSEHOLDERGENERALIZED8 && k != HOUSEHOLDERSIN && k != HOUSEHOLDERCOS && k != HOUSEHOLDERPOLY
                        && k != SECANT3 && k != SECANT4 && k != SECANTGENERALIZED3 && k != SECANTGENERALIZED8 && k != SECANTCOS && k != SECANTPOLY
                        && k != STEFFENSEN3 && k != STEFFENSEN4 && k != STEFFENSENGENERALIZED3 && k != NEWTONFORMULA && k != HALLEYFORMULA && k != SCHRODERFORMULA && k != HOUSEHOLDERFORMULA && k != SECANTFORMULA && k != STEFFENSENFORMULA) {
                    fractal_functions[k].setEnabled(true);
                }

                if (k == SIERPINSKI_GASKET && !s.julia && !julia_map && !s.perturbation && !s.init_val) {
                    fractal_functions[k].setEnabled(true);
                }
            }

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED && !s.julia && !julia_map && !s.perturbation && !s.init_val) {
                rootFindingMethodsSetEnabled(true);
            }
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

    public void setJuliaMap() {

        resetOrbit();
        if (!tools_menu.getJuliaMap().isSelected()) {
            julia_map = false;
            toolbar.getJuliaMapButton().setSelected(false);

            options_menu.getJuliaMapOptions().setEnabled(false);

            setOptions(false);

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
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

            String ans = JOptionPane.showInputDialog(scroll_pane, "Enter the first dimension, n,\nof the nxn 2D grid.", "Julia Map (2D Grid)", JOptionPane.QUESTION_MESSAGE);
            try {
                int temp = Integer.parseInt(ans);

                if (temp < 2) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    return;
                } else {
                    if (temp > 200) {
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

                scroll_pane.getHorizontalScrollBar().setValue((int) (scroll_pane.getHorizontalScrollBar().getMaximum() / 2 - scroll_pane.getHorizontalScrollBar().getSize().getWidth() / 2));
                scroll_pane.getVerticalScrollBar().setValue((int) (scroll_pane.getVerticalScrollBar().getMaximum() / 2 - scroll_pane.getVerticalScrollBar().getSize().getHeight() / 2));

                resetImage();

                whole_image_done = false;

                createThreadsJuliaMap();

                calculation_time = System.currentTimeMillis();

                startThreads(julia_grid_first_dimension);
            } catch (Exception ex) {
                if (ans == null) {
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    main_panel.repaint();
                } else {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getJuliaMap().setSelected(false);
                    toolbar.getJuliaMapButton().setSelected(false);
                    main_panel.repaint();
                }
            }

        }

    }

    public void setJuliaMapOptions() {

        resetOrbit();

        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using a " + julia_grid_first_dimension + "x" + julia_grid_first_dimension + " grid.\nEnter the first dimension, n,\nof the nxn 2D grid.", "Julia Map (2D Grid)", JOptionPane.QUESTION_MESSAGE);
        try {
            int temp = Integer.parseInt(ans);

            if (temp < 2) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 200) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The grid's first dimension number must be less than 201.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            julia_grid_first_dimension = temp;
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
        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
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

                if (num == s.bailout && !s.isConvergingType()) {
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

            if (s.bailout < 100 && !s.isConvergingType()) {
                int radius_x = (int) ((Math.log(s.bailout) - start) / mulx + 0.5);

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
                    brush.drawString("" + s.bailout, radius_x + 6, 12 + scroll_pane.getVerticalScrollBar().getValue());
                } else {
                    brush.drawString("" + s.bailout, radius_x + 6, 12);
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

                    if (num == s.bailout && !s.isConvergingType()) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int) ((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));
                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, (int) (x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int) (y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);
                }

                if (s.bailout < 100 && !s.isConvergingType()) {
                    int radius_x = Math.abs(x0 - (int) ((s.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                    float dash1[] = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawOval(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + s.bailout, (int) (x0 - (radius_x * Math.sqrt(2.0) / 2.0)) - 15, (int) (y0 - (radius_y * Math.sqrt(2.0) / 2.0)) - 10);

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

                    if (num == s.bailout && !s.isConvergingType()) {
                        continue;
                    }

                    int radius_x = Math.abs(x0 - (int) ((num - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-num + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + num, x0 - radius_x - 10, y0 - radius_y - 5);
                }

                if (s.bailout < 100 && !s.isConvergingType()) {

                    int radius_x = Math.abs(x0 - (int) ((s.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

                    BasicStroke old_stroke = (BasicStroke) brush.getStroke();

                    float dash1[] = {5.0f};
                    BasicStroke dashed
                            = new BasicStroke(1.0f,
                                    BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_MITER,
                                    5.0f, dash1, 0.0f);
                    brush.setStroke(dashed);

                    brush.drawRect(x0 - radius_x, y0 - radius_y, 2 * radius_x, 2 * radius_y);

                    brush.drawString("" + s.bailout, x0 - radius_x - 10, y0 - radius_y - 5);

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

                    if (num == s.bailout && !s.isConvergingType()) {
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

                if (s.bailout < 100 && !s.isConvergingType()) {

                    int radius_x = Math.abs(x0 - (int) ((s.bailout - temp_xcenter_size) / temp_size_image_size_x + 0.5));
                    int radius_y = Math.abs(y0 - (int) ((-s.bailout + temp_ycenter_size) / temp_size_image_size_y + 0.5));

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

                    brush.drawString("" + s.bailout, x0 - radius_x / 2 - 15, y0 - radius_y / 2 - 10);

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

    private void customPaletteEditor(final int number) {

        resetOrbit();
        new CustomPaletteEditorFrame(ptr, options_menu.getPalette(), s.smoothing, greedy_algorithm, s.d3s.d3, julia_map, number, s.color_choice, s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.temp_color_cycling_location, s.scale_factor_palette_val, s.processing_alg);

    }

    public boolean getJuliaMap() {

        return julia_map;

    }

    public void goTo() {

        resetOrbit();
        if (s.julia) {
            goToJulia();
        } else {
            goToFractal();
        }
    }

    public void exit() {

        if (!color_cycling) {
            main_panel.repaint();
        }
        int ans = JOptionPane.showConfirmDialog(scroll_pane, "Save before exiting?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            ptr.saveSettings();
            ptr.savePreferences();
            System.exit(0);
        } else {
            if (ans == JOptionPane.NO_OPTION) {
                ptr.savePreferences();
                System.exit(0);
            }
        }
    }

    private void createThreadsJuliaMap() {

        ThreadDraw.resetThreadData(julia_grid_first_dimension * julia_grid_first_dimension);

        int n = julia_grid_first_dimension;

        try {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    threads[i][j] = new BruteForceDraw(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n, s.xCenter, s.yCenter, s.size, s.max_iterations, s.bailout_test_algorithm, s.bailout, s.bailout_test_user_formula, s.bailout_test_user_formula2, s.bailout_test_comparison, s.n_norm, ptr, s.fractal_color, s.dem_color, image, s.fs, s.out_coloring_algorithm, s.user_out_coloring_algorithm, s.outcoloring_formula, s.user_outcoloring_conditions, s.user_outcoloring_condition_formula, s.in_coloring_algorithm, s.user_in_coloring_algorithm, s.incoloring_formula, s.user_incoloring_conditions, s.user_incoloring_condition_formula, s.smoothing, periodicity_checking, s.plane_type, s.apply_plane_on_julia, s.apply_plane_on_julia_seed, s.burning_ship, s.mandel_grass, s.mandel_grass_vals, s.function, s.z_exponent, s.z_exponent_complex, s.color_cycling_location, s.rotation_vals, s.rotation_center, s.coefficients, s.z_exponent_nova, s.relaxation, s.nova_method, s.user_formula, s.user_formula2, s.bail_technique, s.user_plane, s.user_plane_algorithm, s.user_plane_conditions, s.user_plane_condition_formula, s.user_formula_iteration_based, s.user_formula_conditions, s.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.plane_transform_center, s.plane_transform_angle, s.plane_transform_radius, s.plane_transform_scales, s.plane_transform_wavelength, s.waveType, s.plane_transform_angle2, s.plane_transform_sides, s.plane_transform_amount, s.escaping_smooth_algorithm, s.converging_smooth_algorithm, s.bms, s.color_intensity, s.transfer_function, s.polar_projection, s.circle_period, s.fdes, s.rps, s.coupling, s.user_formula_coupled, s.coupling_method, s.coupling_amplitude, s.coupling_frequency, s.coupling_seed, s.inverse_dem, s.ens, s.ofs, s.gss, s.color_blending, s.ots);
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
        if (s.max_iterations >= 80000) {
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

    public void shiftPaletteForward() {

        resetOrbit();
        s.color_cycling_location++;

        s.color_cycling_location = s.color_cycling_location > Integer.MAX_VALUE - 1 ? 0 : s.color_cycling_location;

        if (s.color_choice == CUSTOM_PALETTE_ID) {
            s.temp_color_cycling_location = s.color_cycling_location;
        }

        updateColorPalettesMenu();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

    public void shiftPaletteBackward() {

        resetOrbit();
        if (s.color_cycling_location > 0) {
            s.color_cycling_location--;

            if (s.color_choice == CUSTOM_PALETTE_ID) {
                s.temp_color_cycling_location = s.color_cycling_location;
            }
        } else {
            return;
        }

        updateColorPalettesMenu();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

    public void setPerturbation() {

        resetOrbit();
        if (!options_menu.getPerturbation().isSelected()) {
            s.perturbation = false;

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED && !s.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.init_val && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        } else {
            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            panel1.setLayout(new FlowLayout());
            panel2.setLayout(new FlowLayout());

            JTextField field_real = new JTextField(45);
            field_real.setText("" + s.perturbation_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(45);
            field_imaginary.setText("" + s.perturbation_vals[1]);

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

            JLabel html_label = createUserFormulaHtmlLabels("c, maxn, center, size, sizei, v1 - v30, point", false);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(45);
            field_formula.setText("" + s.perturbation_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(runsOnWindows ? 500 : 580, 190));
            tabbedPane2.setFocusable(false);

            panel22.add(new JLabel("z(0) = z(0) +"));
            panel22.add(field_formula);

            tabbedPane2.addTab("Normal", panel22);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(s.user_perturbation_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(s.user_perturbation_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(s.user_perturbation_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(s.user_perturbation_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
            field_formula_cond3.setText(s.user_perturbation_condition_formula[2]);

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

            if (s.variable_perturbation) {
                tabbedPane.setSelectedIndex(1);
                tabbedPane2.setSelectedIndex(s.user_perturbation_algorithm);
            } else {
                tabbedPane.setSelectedIndex(0);
            }

            Object[] message = {
                tabbedPane
            };

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Perturbation", JOptionPane.OK_CANCEL_OPTION);

            double temp = 0, temp2 = 0;

            if (res == JOptionPane.OK_OPTION) {
                try {
                    if (tabbedPane.getSelectedIndex() == 0) {
                        temp = Double.parseDouble(field_real.getText());
                        temp2 = Double.parseDouble(field_imaginary.getText());
                    } else {
                        if (tabbedPane2.getSelectedIndex() == 0) {
                            s.parser.parse(field_formula.getText());
                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        } else {
                            s.parser.parse(field_condition.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_condition2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond1.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond3.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getPerturbation().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                } catch (ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getPerturbation().setSelected(false);
                    main_panel.repaint();
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getPerturbation().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                options_menu.getPerturbation().setSelected(false);
                return;
            }

            if (tabbedPane.getSelectedIndex() == 0) {
                s.perturbation_vals[0] = temp == 0.0 ? 0.0 : temp;
                s.perturbation_vals[1] = temp2 == 0.0 ? 0.0 : temp2;
                s.variable_perturbation = false;
            } else {
                s.variable_perturbation = true;

                s.user_perturbation_algorithm = tabbedPane2.getSelectedIndex();

                if (s.user_perturbation_algorithm == 0) {
                    s.perturbation_user_formula = field_formula.getText();
                } else {
                    s.user_perturbation_conditions[0] = field_condition.getText();
                    s.user_perturbation_conditions[1] = field_condition2.getText();
                    s.user_perturbation_condition_formula[0] = field_formula_cond1.getText();
                    s.user_perturbation_condition_formula[1] = field_formula_cond2.getText();
                    s.user_perturbation_condition_formula[2] = field_formula_cond3.getText();
                }
            }

            s.perturbation = true;
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
        }

        defaultFractalSettings();

    }

    public void setInitialValue() {

        resetOrbit();
        if (!options_menu.getInitialValue().isSelected()) {
            s.init_val = false;

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED && !s.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.perturbation && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        } else {

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();

            panel1.setLayout(new FlowLayout());
            panel2.setLayout(new FlowLayout());

            JTextField field_real = new JTextField(45);
            field_real.setText("" + s.initial_vals[0]);
            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField(45);
            field_imaginary.setText("" + s.initial_vals[1]);

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

            JLabel html_label = createUserFormulaHtmlLabels("c, maxn, center, size, sizei, v1 - v30, point", true);
            panel21.add(html_label);

            JTextField field_formula = new JTextField(45);
            field_formula.setText("" + s.initial_value_user_formula);

            JTabbedPane tabbedPane2 = new JTabbedPane();
            tabbedPane2.setPreferredSize(new Dimension(runsOnWindows ? 500 : 580, 190));
            tabbedPane2.setFocusable(false);

            panel22.add(new JLabel("z(0) ="));
            panel22.add(field_formula);

            tabbedPane2.addTab("Normal", panel22);

            JPanel formula_panel_cond1 = new JPanel();
            formula_panel_cond1.setLayout(new GridLayout(2, 2));

            JTextField field_condition = new JTextField(24);
            field_condition.setText(s.user_initial_value_conditions[0]);
            field_condition.addAncestorListener(new RequestFocusListener());

            JTextField field_condition2 = new JTextField(24);
            field_condition2.setText(s.user_initial_value_conditions[1]);

            formula_panel_cond1.add(new JLabel("Left operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(new JLabel("Right operand:", SwingConstants.HORIZONTAL));
            formula_panel_cond1.add(field_condition);
            formula_panel_cond1.add(field_condition2);

            JTextField field_formula_cond1 = new JTextField(37);
            field_formula_cond1.setText(s.user_initial_value_condition_formula[0]);

            JTextField field_formula_cond2 = new JTextField(37);
            field_formula_cond2.setText(s.user_initial_value_condition_formula[1]);

            JTextField field_formula_cond3 = new JTextField(37);
            field_formula_cond3.setText(s.user_initial_value_condition_formula[2]);

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

            if (s.variable_init_value) {
                tabbedPane.setSelectedIndex(1);
                tabbedPane2.setSelectedIndex(s.user_initial_value_algorithm);
            } else {
                tabbedPane.setSelectedIndex(0);
            }

            Object[] message = {
                tabbedPane
            };

            int res = JOptionPane.showConfirmDialog(scroll_pane, message, "Initial Value", JOptionPane.OK_CANCEL_OPTION);

            double temp = 0, temp2 = 0;

            if (res == JOptionPane.OK_OPTION) {
                try {
                    if (tabbedPane.getSelectedIndex() == 0) {
                        temp = Double.parseDouble(field_real.getText());
                        temp2 = Double.parseDouble(field_imaginary.getText());
                    } else {
                        if (tabbedPane2.getSelectedIndex() == 0) {
                            s.parser.parse(field_formula.getText());
                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        } else {
                            s.parser.parse(field_condition.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_condition2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the right condition formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond1.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left > right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond2.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left < right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }

                            s.parser.parse(field_formula_cond3.getText());

                            if (s.parser.foundN() || s.parser.foundP() || s.parser.foundS() || s.parser.foundZ() || s.parser.foundPP() || s.parser.foundBail() || s.parser.foundCbail()) {
                                JOptionPane.showMessageDialog(scroll_pane, "The variables: z, n, s, p, pp, bail, cbail cannot be used in the left = right z(0) formula.", "Error!", JOptionPane.ERROR_MESSAGE);
                                options_menu.getInitialValue().setSelected(false);
                                main_panel.repaint();
                                return;
                            }
                        }
                    }
                } catch (ParserException e) {
                    JOptionPane.showMessageDialog(scroll_pane, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getInitialValue().setSelected(false);
                    main_panel.repaint();
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    options_menu.getInitialValue().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            } else {
                main_panel.repaint();
                options_menu.getInitialValue().setSelected(false);
                return;
            }

            if (tabbedPane.getSelectedIndex() == 0) {
                s.initial_vals[0] = temp == 0.0 ? 0.0 : temp;
                s.initial_vals[1] = temp2 == 0.0 ? 0.0 : temp2;
                s.variable_init_value = false;
            } else {
                s.variable_init_value = true;
                s.user_initial_value_algorithm = tabbedPane2.getSelectedIndex();

                if (s.user_initial_value_algorithm == 0) {
                    s.initial_value_user_formula = field_formula.getText();
                } else {
                    s.user_initial_value_conditions[0] = field_condition.getText();
                    s.user_initial_value_conditions[1] = field_condition2.getText();
                    s.user_initial_value_condition_formula[0] = field_formula_cond1.getText();
                    s.user_initial_value_condition_formula[1] = field_formula_cond2.getText();
                    s.user_initial_value_condition_formula[2] = field_formula_cond3.getText();
                }
            }

            s.init_val = true;
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            rootFindingMethodsSetEnabled(false);
            fractal_functions[SIERPINSKI_GASKET].setEnabled(false);
        }

        defaultFractalSettings();

    }

    public void setColorCyclingButton() {

        resetOrbit();
        if (!toolbar.getColorCyclingButton().isSelected()) {
            toolbar.getColorCyclingButton().setSelected(true);
            tools_menu.getColorCyling().setSelected(true);
        } else {
            toolbar.getColorCyclingButton().setSelected(false);
            tools_menu.getColorCyling().setSelected(false);
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

    public void openCustomPaletteEditor() {

        resetOrbit();
        customPaletteEditor(CUSTOM_PALETTE_ID);

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
        CustomPaletteEditorFrame.randomPalette(s.custom_palette, CustomPaletteEditorFrame.getRandomPaletteAlgorithm(), CustomPaletteEditorFrame.getEqualHues(), s.color_interpolation, s.color_space, s.reversed_palette, s.color_cycling_location, s.scale_factor_palette_val, s.processing_alg);

        setPalette(CUSTOM_PALETTE_ID);

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
            JTextField field_real = new JTextField();

            Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

            if (p.x == 0) {
                field_real.setText("" + 0.0);
            } else {
                field_real.setText("" + p.x);
            }

            field_real.addAncestorListener(new RequestFocusListener());

            JTextField field_imaginary = new JTextField();

            if (p.y == 0) {
                field_imaginary.setText("" + 0.0);
            } else {
                field_imaginary.setText("" + p.y);
            }

            JTextField field_size = new JTextField();
            field_size.setText("" + s.size);

            JTextField field_circle_period = new JTextField();
            field_circle_period.setText("" + s.circle_period);

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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    tempReal = Double.parseDouble(field_real.getText()) - s.rotation_center[0];
                    tempImaginary = Double.parseDouble(field_imaginary.getText()) - s.rotation_center[1];
                    tempSize = Double.parseDouble(field_size.getText());
                    temp_circle_period = Double.parseDouble(field_circle_period.getText());

                    if (temp_circle_period <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The circle periods must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.getPolarProjection().setSelected(false);
                        toolbar.getPolarProjectionButton().setSelected(false);
                        return;
                    }

                    s.size = tempSize;
                    s.xCenter = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
                    s.yCenter = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];
                    s.circle_period = temp_circle_period;

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
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.getPolarProjection().setSelected(false);
                    toolbar.getPolarProjectionButton().setSelected(false);
                    main_panel.repaint();
                    return;
                }
            } else {
                tools_menu.getPolarProjection().setSelected(false);
                toolbar.getPolarProjectionButton().setSelected(false);
                main_panel.repaint();
                return;
            }
        }
    }

    public void setPolarProjectionOptions() {
        resetOrbit();
        JTextField field_real = new JTextField();

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

        if (p.x == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + p.x);
        }

        field_real.addAncestorListener(new RequestFocusListener());

        JTextField field_imaginary = new JTextField();

        if (p.y == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + p.y);
        }

        JTextField field_size = new JTextField();
        field_size.setText("" + s.size);

        JTextField field_circle_period = new JTextField();
        field_circle_period.setText("" + s.circle_period);

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

        if (res == JOptionPane.OK_OPTION) {
            try {
                tempReal = Double.parseDouble(field_real.getText()) - s.rotation_center[0];
                tempImaginary = Double.parseDouble(field_imaginary.getText()) - s.rotation_center[1];
                tempSize = Double.parseDouble(field_size.getText());
                temp_circle_period = Double.parseDouble(field_circle_period.getText());

                if (temp_circle_period <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The circle periods must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                s.size = tempSize;
                s.xCenter = tempReal * s.rotation_vals[0] + tempImaginary * s.rotation_vals[1] + s.rotation_center[0];
                s.yCenter = -tempReal * s.rotation_vals[1] + tempImaginary * s.rotation_vals[0] + s.rotation_center[1];
                s.circle_period = temp_circle_period;

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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }
    }

    public void setDomainColoringSettings() {
        toolbar.getDomainColoringButton().setSelected(true);

        options_menu.getDomainColoringOptions().setEnabled(true);

        tools_menu.getJuliaMap().setEnabled(false);
        toolbar.getJuliaMapButton().setEnabled(false);
        tools_menu.getJulia().setEnabled(false);
        toolbar.getJuliaButton().setEnabled(false);
        tools_menu.getColorCyling().setEnabled(false);
        toolbar.getColorCyclingButton().setEnabled(false);

        if (!s.ds.use_palette_domain_coloring) {
            toolbar.getCustomPaletteButton().setEnabled(false);
            toolbar.getRandomPaletteButton().setEnabled(false);

            options_menu.getRandomPalette().setEnabled(false);
            options_menu.getRollPaletteMenu().setEnabled(false);
            options_menu.getPaletteMenu().setEnabled(false);
            options_menu.getColorIntensity().setEnabled(false);
            options_menu.getColorTransferMenu().setEnabled(false);
            options_menu.getProcessing().setEnabled(false);
        } else {
            options_menu.getDistanceEstimation().setEnabled(false);
            options_menu.getBumpMap().setEnabled(false);

            options_menu.getFakeDistanceEstimation().setEnabled(false);

            options_menu.getEntropyColoring().setEnabled(false);
            options_menu.getOffsetColoring().setEnabled(false);
            options_menu.getGreyScaleColoring().setEnabled(false);

            options_menu.getRainbowPalette().setEnabled(false);
            
            options_menu.getOrbitTraps().setEnabled(false);
        }

        options_menu.getOutColoringMenu().setEnabled(false);
        options_menu.getInColoringMenu().setEnabled(false);
        options_menu.getFractalColor().setEnabled(false);

        options_menu.getGreedyAlgorithm().setEnabled(false);
        options_menu.getPeriodicityChecking().setEnabled(false);
        options_menu.getBailout().setEnabled(false);
        options_menu.getBailoutConditionMenu().setEnabled(false);

        infobar.getMaxIterationsColorPreview().setVisible(false);
        infobar.getMaxIterationsColorPreviewLabel().setVisible(false);

        updatePalettePreview(s.color_cycling_location);

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

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);
    }

    public void cancelDomainColoring() {

        tools_menu.getDomainColoring().setSelected(false);
        toolbar.getDomainColoringButton().setSelected(false);

    }

    public void setDomainColoring() {

        resetOrbit();
        if (!tools_menu.getDomainColoring().isSelected()) {
            s.ds.domain_coloring = false;

            toolbar.getDomainColoringButton().setSelected(false);
            options_menu.getDomainColoringOptions().setEnabled(false);

            infobar.getMaxIterationsColorPreview().setVisible(true);
            infobar.getMaxIterationsColorPreviewLabel().setVisible(true);

            updatePalettePreview(s.color_cycling_location);

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

                ThreadDraw.setArrays(image_size);

                progress.setMaximum((image_size * image_size) + (image_size * image_size / 100));

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
            JTextField field = new JTextField();
            field.addAncestorListener(new RequestFocusListener());
            field.setText("" + s.d3s.detail);

            JTextField size_opt = new JTextField();
            size_opt.setText("" + s.d3s.d3_size_scale);

            JTextField field2 = new JTextField();
            field2.setText("" + s.d3s.d3_height_scale);

            RangeSlider scale_range = new RangeSlider(0, 100);
            scale_range.setValue(s.d3s.min_range);
            scale_range.setUpperValue(s.d3s.max_range);
            scale_range.setPreferredSize(new Dimension(150, 35));
            scale_range.setMajorTickSpacing(25);
            scale_range.setMinorTickSpacing(1);
            scale_range.setToolTipText("Sets the scaling range.");
            scale_range.setFocusable(false);
            scale_range.setPaintLabels(true);

            JSlider scale_max_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 200, s.d3s.max_scaling);
            scale_max_val_opt.setPreferredSize(new Dimension(150, 35));
            scale_max_val_opt.setMajorTickSpacing(50);
            scale_max_val_opt.setMinorTickSpacing(1);
            scale_max_val_opt.setToolTipText("Sets the scaling value.");
            scale_max_val_opt.setPaintLabels(true);
            scale_max_val_opt.setFocusable(false);

            JPanel temp_p3 = new JPanel();
            temp_p3.setLayout(new GridLayout(2, 2));
            temp_p3.add(new JLabel("Scaling Value:", SwingConstants.HORIZONTAL));
            temp_p3.add(new JLabel("Scaling Range:", SwingConstants.HORIZONTAL));
            temp_p3.add(scale_max_val_opt);
            temp_p3.add(scale_range);

            String[] height_options = {"120 / (x + 1)", "e^(-x + 5)", "40 * log(x + 1)", "150 - e^(-x + 5)", "40 * log(log(x + 1) + 1)", "150 / (1 + e^(-3*x+3))"};

            JComboBox height_algorithm_opt = new JComboBox(height_options);
            height_algorithm_opt.setSelectedIndex(s.d3s.height_algorithm);
            height_algorithm_opt.setFocusable(false);
            height_algorithm_opt.setToolTipText("Sets the height algorithm.");

            final JCheckBox gaussian_scaling_opt = new JCheckBox("Gaussian Normalization");
            gaussian_scaling_opt.setSelected(s.d3s.gaussian_scaling);
            gaussian_scaling_opt.setFocusable(false);
            gaussian_scaling_opt.setToolTipText("Enables the gaussian normalization.");

            final JTextField field3 = new JTextField();
            field3.setText("" + s.d3s.gaussian_weight);
            field3.setEnabled(s.d3s.gaussian_scaling);

            String[] kernels = {"3", "5", "7", "9", "11"};
            final JComboBox kernels_size_opt = new JComboBox(kernels);
            kernels_size_opt.setSelectedIndex(s.d3s.gaussian_kernel);
            kernels_size_opt.setFocusable(false);
            kernels_size_opt.setEnabled(s.d3s.gaussian_scaling);
            kernels_size_opt.setToolTipText("Sets the radius of the gaussian normalization.");

            gaussian_scaling_opt.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (gaussian_scaling_opt.isSelected()) {
                        field3.setEnabled(true);
                        kernels_size_opt.setEnabled(true);
                    } else {
                        field3.setEnabled(false);
                        kernels_size_opt.setEnabled(false);
                    }
                }

            });

            JSlider color_blend = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.d3s.color_3d_blending * 100)));
            color_blend.setPreferredSize(new Dimension(270, 35));
            color_blend.setMajorTickSpacing(25);
            color_blend.setMinorTickSpacing(1);
            color_blend.setToolTipText("Sets the color blending percentage.");
            color_blend.setPaintLabels(true);
            color_blend.setFocusable(false);

            JPanel cblend_panel = new JPanel();
            cblend_panel.setLayout(new GridLayout(3, 1));
            cblend_panel.add(new JLabel("Set the color blending percentage."));
            cblend_panel.add(new JLabel("Color Blending:"));
            cblend_panel.add(color_blend);

            final JCheckBox height_shading_opt = new JCheckBox("Height Shading");
            height_shading_opt.setSelected(s.d3s.shade_height);
            height_shading_opt.setFocusable(false);
            height_shading_opt.setToolTipText("Enables the height shading.");

            JPanel height_color_panel = new JPanel();
            height_color_panel.setLayout(new GridLayout(3, 1));
            height_color_panel.add(new JLabel("Set the height shading."));

            String[] shades = {"White & Black", "White", "Black"};

            final JComboBox shade_choice_box = new JComboBox(shades);
            shade_choice_box.setSelectedIndex(s.d3s.shade_choice);
            shade_choice_box.setToolTipText("Selects the shade colors.");
            shade_choice_box.setFocusable(false);

            String[] shade_algorithms = {"Linear Interpolation", "Cosine Interpolation", "<10% and >90% Lin. Int.", "<20% and >80% Lin. Int.", "<30% and >70% Lin. Int.", "<40% and >60% Lin. Int."};

            final JComboBox shade_algorithm_box = new JComboBox(shade_algorithms);
            shade_algorithm_box.setSelectedIndex(s.d3s.shade_algorithm);
            shade_algorithm_box.setToolTipText("Selects the shade algorithm.");
            shade_algorithm_box.setFocusable(false);

            final JCheckBox shade_invert_opt = new JCheckBox("Invert Shading");
            shade_invert_opt.setSelected(s.d3s.shade_invert);
            shade_invert_opt.setFocusable(false);
            shade_invert_opt.setToolTipText("Inverts the height shading.");

            if (!height_shading_opt.isSelected()) {
                shade_choice_box.setEnabled(false);
                shade_algorithm_box.setEnabled(false);
                shade_invert_opt.setEnabled(false);
            } else {
                shade_choice_box.setEnabled(true);
                shade_algorithm_box.setEnabled(true);
                shade_invert_opt.setEnabled(true);
            }

            height_shading_opt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!height_shading_opt.isSelected()) {
                        shade_choice_box.setEnabled(false);
                        shade_algorithm_box.setEnabled(false);
                        shade_invert_opt.setEnabled(false);
                    } else {
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

            if (res == JOptionPane.OK_OPTION) {
                try {
                    int temp = Integer.parseInt(field.getText());
                    double temp2 = Double.parseDouble(field2.getText());
                    double temp3 = Double.parseDouble(field3.getText());
                    double temp4 = Double.parseDouble(size_opt.getText());

                    if (temp < 10) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    } else {
                        if (temp > 2000) {
                            main_panel.repaint();
                            JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be less than 2001.", "Error!", JOptionPane.ERROR_MESSAGE);
                            tools_menu.get3D().setSelected(false);
                            toolbar.get3DButton().setSelected(false);
                            return;
                        }
                    }

                    if (temp2 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    if (temp3 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    if (temp4 <= 0) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                        tools_menu.get3D().setSelected(false);
                        toolbar.get3DButton().setSelected(false);
                        return;
                    }

                    s.d3s.detail = temp;
                    s.d3s.d3_height_scale = temp2;
                    s.d3s.d3_size_scale = temp4;
                    s.d3s.height_algorithm = height_algorithm_opt.getSelectedIndex();
                    s.d3s.gaussian_scaling = gaussian_scaling_opt.isSelected();
                    s.d3s.gaussian_weight = temp3;
                    s.d3s.gaussian_kernel = kernels_size_opt.getSelectedIndex();
                    s.d3s.max_range = scale_range.getUpperValue();
                    s.d3s.min_range = scale_range.getValue();
                    s.d3s.max_scaling = scale_max_val_opt.getValue();

                    s.d3s.shade_height = height_shading_opt.isSelected();
                    s.d3s.shade_choice = shade_choice_box.getSelectedIndex();
                    s.d3s.shade_algorithm = shade_algorithm_box.getSelectedIndex();
                    s.d3s.shade_invert = shade_invert_opt.isSelected();

                    //d3_draw_method = draw_choice.getSelectedIndex();
                    s.d3s.color_3d_blending = color_blend.getValue() / 100.0;

                    s.d3s.fiX = 0.64;
                    s.d3s.fiY = 0.82;

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
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    tools_menu.get3D().setSelected(false);
                    toolbar.get3DButton().setSelected(false);
                    main_panel.repaint();
                    return;
                } catch (OutOfMemoryError e) {
                    JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                    savePreferences();
                    System.exit(-1);
                }
            } else {
                tools_menu.get3D().setSelected(false);
                toolbar.get3DButton().setSelected(false);
                main_panel.repaint();
                return;
            }
        }

    }

    public void set3DDetails() {

        resetOrbit();
        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + s.d3s.detail);

        JTextField size_opt = new JTextField();
        size_opt.setText("" + s.d3s.d3_size_scale);

        JTextField field2 = new JTextField();
        field2.setText("" + s.d3s.d3_height_scale);

        RangeSlider scale_range = new RangeSlider(0, 100);
        scale_range.setValue(s.d3s.min_range);
        scale_range.setUpperValue(s.d3s.max_range);
        scale_range.setPreferredSize(new Dimension(150, 35));
        scale_range.setMajorTickSpacing(25);
        scale_range.setMinorTickSpacing(1);
        scale_range.setToolTipText("Sets the scaling range.");
        scale_range.setFocusable(false);
        scale_range.setPaintLabels(true);

        JSlider scale_max_val_opt = new JSlider(JSlider.HORIZONTAL, 0, 200, s.d3s.max_scaling);
        scale_max_val_opt.setPreferredSize(new Dimension(150, 35));
        scale_max_val_opt.setMajorTickSpacing(50);
        scale_max_val_opt.setMinorTickSpacing(1);
        scale_max_val_opt.setToolTipText("Sets the scaling value.");
        scale_max_val_opt.setPaintLabels(true);
        scale_max_val_opt.setFocusable(false);

        JPanel temp_p3 = new JPanel();
        temp_p3.setLayout(new GridLayout(2, 2));
        temp_p3.add(new JLabel("Scaling Value:", SwingConstants.HORIZONTAL));
        temp_p3.add(new JLabel("Scaling Range:", SwingConstants.HORIZONTAL));
        temp_p3.add(scale_max_val_opt);
        temp_p3.add(scale_range);

        String[] height_options = {"120 / (x + 1)", "e^(-x + 5)", "40 * log(x + 1)", "150 - e^(-x + 5)", "40 * log(log(x + 1) + 1)", "150 / (1 + e^(-3*x+3))"};

        JComboBox height_algorithm_opt = new JComboBox(height_options);
        height_algorithm_opt.setSelectedIndex(s.d3s.height_algorithm);
        height_algorithm_opt.setFocusable(false);
        height_algorithm_opt.setToolTipText("Sets the height algorithm.");

        final JCheckBox gaussian_scaling_opt = new JCheckBox("Gaussian Normalization");
        gaussian_scaling_opt.setSelected(s.d3s.gaussian_scaling);
        gaussian_scaling_opt.setFocusable(false);
        gaussian_scaling_opt.setToolTipText("Enables the gaussian normalization.");

        final JTextField field3 = new JTextField();
        field3.setText("" + s.d3s.gaussian_weight);
        field3.setEnabled(s.d3s.gaussian_scaling);

        String[] kernels = {"3", "5", "7", "9", "11"};
        final JComboBox kernels_size_opt = new JComboBox(kernels);
        kernels_size_opt.setSelectedIndex(s.d3s.gaussian_kernel);
        kernels_size_opt.setFocusable(false);
        kernels_size_opt.setEnabled(s.d3s.gaussian_scaling);
        kernels_size_opt.setToolTipText("Sets the radius of the gaussian normalization.");

        gaussian_scaling_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (gaussian_scaling_opt.isSelected()) {
                    field3.setEnabled(true);
                    kernels_size_opt.setEnabled(true);
                } else {
                    field3.setEnabled(false);
                    kernels_size_opt.setEnabled(false);
                }
            }

        });

        JSlider color_blend = new JSlider(JSlider.HORIZONTAL, 0, 100, ((int) (s.d3s.color_3d_blending * 100)));
        color_blend.setPreferredSize(new Dimension(270, 35));
        color_blend.setMajorTickSpacing(25);
        color_blend.setMinorTickSpacing(1);
        color_blend.setToolTipText("Sets the color blending percentage.");
        color_blend.setPaintLabels(true);
        color_blend.setFocusable(false);

        JPanel cblend_panel = new JPanel();
        cblend_panel.setLayout(new GridLayout(3, 1));
        cblend_panel.add(new JLabel("Set the color blending percentage."));
        cblend_panel.add(new JLabel("Color Blending:"));
        cblend_panel.add(color_blend);

        final JCheckBox height_shading_opt = new JCheckBox("Height Shading");
        height_shading_opt.setSelected(s.d3s.shade_height);
        height_shading_opt.setFocusable(false);
        height_shading_opt.setToolTipText("Enables the height shading.");

        JPanel height_color_panel = new JPanel();
        height_color_panel.setLayout(new GridLayout(3, 1));
        height_color_panel.add(new JLabel("Set the height shading."));

        String[] shades = {"White & Black", "White", "Black"};

        final JComboBox shade_choice_box = new JComboBox(shades);
        shade_choice_box.setSelectedIndex(s.d3s.shade_choice);
        shade_choice_box.setToolTipText("Selects the shade colors.");
        shade_choice_box.setFocusable(false);

        String[] shade_algorithms = {"Linear Interpolation", "Cosine Interpolation", "<10% and >90% Lin. Int.", "<20% and >80% Lin. Int.", "<30% and >70% Lin. Int.", "<40% and >60% Lin. Int."};

        final JComboBox shade_algorithm_box = new JComboBox(shade_algorithms);
        shade_algorithm_box.setSelectedIndex(s.d3s.shade_algorithm);
        shade_algorithm_box.setToolTipText("Selects the shade algorithm.");
        shade_algorithm_box.setFocusable(false);

        final JCheckBox shade_invert_opt = new JCheckBox("Invert Shading");
        shade_invert_opt.setSelected(s.d3s.shade_invert);
        shade_invert_opt.setFocusable(false);
        shade_invert_opt.setToolTipText("Inverts the height shading.");

        if (!height_shading_opt.isSelected()) {
            shade_choice_box.setEnabled(false);
            shade_algorithm_box.setEnabled(false);
            shade_invert_opt.setEnabled(false);
        } else {
            shade_choice_box.setEnabled(true);
            shade_algorithm_box.setEnabled(true);
            shade_invert_opt.setEnabled(true);
        }

        height_shading_opt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!height_shading_opt.isSelected()) {
                    shade_choice_box.setEnabled(false);
                    shade_algorithm_box.setEnabled(false);
                    shade_invert_opt.setEnabled(false);
                } else {
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

        if (res == JOptionPane.OK_OPTION) {
            try {
                int temp = Integer.parseInt(field.getText());
                double temp2 = Double.parseDouble(field2.getText());
                double temp3 = Double.parseDouble(field3.getText());
                double temp4 = Double.parseDouble(size_opt.getText());

                if (temp < 10) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be greater than 9.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (temp > 2000) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "The 3D detail level must be less than 2001.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (temp2 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The height scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp3 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The gaussian normalization weight must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp4 <= 0) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "The size must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                s.d3s.detail = temp;
                s.d3s.d3_height_scale = temp2;
                s.d3s.d3_size_scale = temp4;
                s.d3s.height_algorithm = height_algorithm_opt.getSelectedIndex();
                s.d3s.gaussian_scaling = gaussian_scaling_opt.isSelected();
                s.d3s.gaussian_weight = temp3;
                s.d3s.gaussian_kernel = kernels_size_opt.getSelectedIndex();
                s.d3s.max_range = scale_range.getUpperValue();
                s.d3s.min_range = scale_range.getValue();
                s.d3s.max_scaling = scale_max_val_opt.getValue();

                s.d3s.shade_height = height_shading_opt.isSelected();
                s.d3s.shade_choice = shade_choice_box.getSelectedIndex();
                s.d3s.shade_algorithm = shade_algorithm_box.getSelectedIndex();
                s.d3s.shade_invert = shade_invert_opt.isSelected();

                //d3_draw_method = draw_choice.getSelectedIndex();
                s.d3s.color_3d_blending = color_blend.getValue() / 100.0;

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
            } catch (Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            } catch (OutOfMemoryError e) {
                JOptionPane.showMessageDialog(scroll_pane, "Maximum Heap size was reached.\nThe application will terminate.", "Error!", JOptionPane.ERROR_MESSAGE);
                savePreferences();
                System.exit(-1);
            }
        } else {
            main_panel.repaint();
            return;
        }

    }

    public void setBoundariesNumber() {

        resetOrbit();
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

        if (res == JOptionPane.OK_OPTION) {
            try {
                int temp = Integer.parseInt(field.getText());

                if (temp < 1) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Boundaries number must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (temp > 64) {
                        main_panel.repaint();
                        JOptionPane.showMessageDialog(scroll_pane, "Boundaries number must be less than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                boundaries_num = temp;

                boundaries_spacing_method = draw_choice.getSelectedIndex();

                boundaries_type = draw_choice2.getSelectedIndex();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

    }

    public void setGridTiles() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + grid_tiles + " grid tiles\nin each dimension.\nEnter the new grid tiles number.", "Grid Tiles", JOptionPane.QUESTION_MESSAGE);

        try {
            int temp = Integer.parseInt(ans);

            if (temp < 2) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Grid tiles number must be greater than 1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (temp > 64) {
                    main_panel.repaint();
                    JOptionPane.showMessageDialog(scroll_pane, "Grid tiles number must be less than 65.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            grid_tiles = temp;

            main_panel.repaint();
        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
            }
        }

    }

    public void setColorIntensity() {

        resetOrbit();
        String ans = JOptionPane.showInputDialog(scroll_pane, "You are using " + s.color_intensity + " for color intensity.\nEnter the new color intensity number.", "Color Intensity", JOptionPane.QUESTION_MESSAGE);

        try {
            double temp = Double.parseDouble(ans);

            if (temp <= 0) {
                main_panel.repaint();
                JOptionPane.showMessageDialog(scroll_pane, "Color intensity value must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            s.color_intensity = temp;

            setOptions(false);

            progress.setValue(0);

            resetImage();

            whole_image_done = false;

            if (s.d3s.d3) {
                Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
            }

            if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

        } catch (Exception ex) {
            if (ans == null) {
                main_panel.repaint();
            } else {
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
    }

    private void optionsEnableShortcut() {
        out_coloring_modes[DISTANCE_ESTIMATOR].setEnabled(false);
        options_menu.getDistanceEstimation().setEnabled(false);
        if (s.out_coloring_algorithm != BIOMORPH) {
            out_coloring_modes[BIOMORPH].setEnabled(true);
        }
        if (s.out_coloring_algorithm != BANDED) {
            out_coloring_modes[BANDED].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER2].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER3].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER4].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5) {
            out_coloring_modes[ESCAPE_TIME_GAUSSIAN_INTEGER5].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE) {
            out_coloring_modes[ITERATIONS_PLUS_RE].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ITERATIONS_PLUS_IM) {
            out_coloring_modes[ITERATIONS_PLUS_IM].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM) {
            out_coloring_modes[ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2) {
            out_coloring_modes[ESCAPE_TIME_ALGORITHM2].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS) {
            out_coloring_modes[ESCAPE_TIME_ESCAPE_RADIUS].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_GRID) {
            out_coloring_modes[ESCAPE_TIME_GRID].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES) {
            out_coloring_modes[ESCAPE_TIME_FIELD_LINES].setEnabled(true);
        }
        if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2) {
            out_coloring_modes[ESCAPE_TIME_FIELD_LINES2].setEnabled(true);
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

        old_rotation_vals[0] = s.rotation_vals[0];
        old_rotation_vals[1] = s.rotation_vals[1];

        old_rotation_center[0] = s.rotation_center[0];
        old_rotation_center[1] = s.rotation_center[1];

        old_d3 = s.d3s.d3;
        old_polar_projection = s.polar_projection;
        old_grid = grid;
        old_boundaries = boundaries;

        statusbar.getMode().setText(mode);

    }

    public void setColorCyclingOptions() {

        resetOrbit();

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

        if (res == JOptionPane.OK_OPTION) {
            color_cycling_speed = speed_slid.getMaximum() - speed_slid.getValue();
        } else {
            if (!color_cycling) {
                main_panel.repaint();
            }
            return;
        }
    }

    public void setApplyPlaneOnWholeJulia() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnWholeJuliaOpt().isSelected()) {
            s.apply_plane_on_julia = false;
        } else {
            s.apply_plane_on_julia = true;
        }

        if (s.julia || julia_map) {
            defaultFractalSettings();
        }

    }

    public void setApplyPlaneOnJuliaSeed() {

        resetOrbit();

        if (!options_menu.getApplyPlaneOnJuliaSeedOpt().isSelected()) {
            s.apply_plane_on_julia_seed = false;
        } else {
            s.apply_plane_on_julia_seed = true;
        }

        if (s.julia || julia_map) {
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

    private void showUserFormulaHelp() {

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

        for (i = 0; i < TOTAL_PALETTES; i++) {

            Color[] c = null;

            if (i == s.color_choice) { // the current activated palette
                if (i != CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, s.color_cycling_location, 0, PROCESSING_NONE);
                } else {
                    c = CustomPalette.getPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.color_cycling_location, s.scale_factor_palette_val, s.processing_alg);
                }
            } else { // the remaining palettes
                if (i != CUSTOM_PALETTE_ID) {
                    c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, 0, 0, PROCESSING_NONE); // 0 color cycling loc
                } else {
                    c = CustomPalette.getPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, s.temp_color_cycling_location, s.scale_factor_palette_val, s.processing_alg); // temp color cycling loc
                }
            }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (s.smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
                } else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            options_menu.getPalette()[i].setIcon(new ImageIcon(palette_preview));
        }

        updatePalettePreview(s.color_cycling_location);

    }

    private void updateGradientPreview() {

        infobar.getGradientPreview().setIcon(new ImageIcon(CommonFunctions.getGradientPreview(s.gs, Infobar.GRADIENT_PREVIEW_WIDTH, Infobar.GRADIENT_PREVIEW_HEIGHT)));

    }

    public void updatePalettePreview(int color_cycling_location) {

        infobar.getPalettePreview().setIcon(new ImageIcon(CommonFunctions.getPalettePreview(s, color_cycling_location, Infobar.PALETTE_PREVIEW_WIDTH, Infobar.PALETTE_PREVIEW_HEIGHT)));

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
            for (k = 0; k < filters_options_vals.length; k++) {
                s.fs.filters_options_vals[k] = filters_options_vals[k];
                s.fs.filters_options_extra_vals[0][k] = filters_options_extra_vals[0][k];
                s.fs.filters_options_extra_vals[1][k] = filters_options_extra_vals[1][k];
                s.fs.filters_colors[k] = filters_colors[k];
                s.fs.filters_extra_colors[0][k] = filters_extra_colors[0][k];
                s.fs.filters_extra_colors[1][k] = filters_extra_colors[1][k];
                s.fs.filters[k] = activeFilters[k];
                filters_opt[k].setSelected(s.fs.filters[k]);
            }

            for (k = 0; k < filters_order.length; k++) {
                s.fs.filters_order[k] = filters_order[k];
            }
        } else {
            s.fs.defaultFilters(false);
        }

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.fs.filters[MainWindow.ANTIALIASING]) {

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

    public void customPaletteChanged(int[][] temp_custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int temp_color_cycling_location, double scale_factor_palette_val, int processing_alg) {

        for (k = 0; k < s.custom_palette.length; k++) {
            for (int j = 0; j < s.custom_palette[0].length; j++) {
                s.custom_palette[k][j] = temp_custom_palette[k][j];
            }
        }

        s.color_interpolation = color_interpolation;
        s.color_space = color_space;
        s.reversed_palette = reversed_palette;
        s.temp_color_cycling_location = s.color_cycling_location = temp_color_cycling_location;
        s.scale_factor_palette_val = scale_factor_palette_val;
        s.processing_alg = processing_alg;

    }

    private void setUserFormulaOptions() {
        if (s.julia) {
            s.julia = false;
            toolbar.getJuliaButton().setSelected(false);
            tools_menu.getJulia().setSelected(false);
            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }
            if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if (s.init_val) {
            s.init_val = false;
            options_menu.getInitialValue().setSelected(false);

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED && !s.perturbation) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.perturbation && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if (s.perturbation) {
            s.perturbation = false;
            options_menu.getPerturbation().setSelected(false);

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED && !s.init_val) {
                rootFindingMethodsSetEnabled(true);
            }

            if (!s.init_val && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }
        }

        if (julia_map) {
            julia_map = false;
            tools_menu.getJuliaMap().setSelected(false);
            toolbar.getJuliaMapButton().setSelected(false);
            options_menu.getJuliaMapOptions().setEnabled(false);
            setOptions(false);

            if (s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES2 && s.out_coloring_algorithm != ESCAPE_TIME_FIELD_LINES && s.out_coloring_algorithm != ESCAPE_TIME_ESCAPE_RADIUS && s.out_coloring_algorithm != ESCAPE_TIME_GRID && s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de && s.out_coloring_algorithm != BIOMORPH && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER2 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER3 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER4 && s.out_coloring_algorithm != ESCAPE_TIME_GAUSSIAN_INTEGER5 && s.out_coloring_algorithm != ITERATIONS_PLUS_RE && s.out_coloring_algorithm != ITERATIONS_PLUS_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM && s.out_coloring_algorithm != ESCAPE_TIME_ALGORITHM2 && s.out_coloring_algorithm != BANDED) {
                rootFindingMethodsSetEnabled(true);
            }

            if (s.out_coloring_algorithm != DISTANCE_ESTIMATOR && !s.exterior_de) {
                fractal_functions[SIERPINSKI_GASKET].setEnabled(true);
            }

        }

        if (!s.user_formula_c) {
            tools_menu.getJulia().setEnabled(false);
            toolbar.getJuliaButton().setEnabled(false);
            tools_menu.getJuliaMap().setEnabled(false);
            toolbar.getJuliaMapButton().setEnabled(false);
            options_menu.getPerturbation().setEnabled(false);
            options_menu.getInitialValue().setEnabled(false);
        }

        if (s.bail_technique == 1) {
            options_menu.getBailout().setEnabled(false);
            options_menu.getBailoutConditionMenu().setEnabled(false);
            options_menu.getPeriodicityChecking().setEnabled(false);
        }
    }

    public boolean getDomainColoring() {

        return s.ds.domain_coloring;

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
            "flip, round, ceil, floor, trunc, deta, f1, ... f60",
            two_arg,
            "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, g1, ... g60",
            multi_arg,
            "m1, ... m60, k1, ... k60"
        };

        return labels;

    }

    private JLabel createUserFormulaHtmlLabels(String supported_vars, boolean mode) {

        String txt = "perturbation";
        if (mode) {
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
                + "flip, round, ceil, floor, trunc, deta, f1, ... f60<br>"
                + "<b>Two Argument Functions: f(z, w)</b><br>"
                + "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, g1, ... g60<br>"
                + "<b>Multiple Argument User Functions: f(z1, ... z10) or f(z1, ... z20)</b><br>"
                + "m1, ... m60, k1, ... k60<br><br>"
                + "Set the variable " + txt + "."
                + "</html>");

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
        final JSlider tiles_slid = new JSlider(JSlider.HORIZONTAL, 2, 20, ThreadDraw.TILE_SIZE);

        tiles_slid.setPreferredSize(new Dimension(350, 55));

        tiles_slid.setToolTipText("Sets the size of the tiles.");

        tiles_slid.setPaintLabels(true);
        tiles_slid.setFocusable(false);
        tiles_slid.setPaintTicks(true);
        tiles_slid.setMajorTickSpacing(1);

        Object[] message3 = {
            " ",
            "Set the quick draw tile size.",
            tiles_slid,
            " ",};

        int res = JOptionPane.showConfirmDialog(scroll_pane, message3, "Quick Draw Tile Size", JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION) {
            ThreadDraw.TILE_SIZE = tiles_slid.getValue();
        } else {
            if (!color_cycling) {
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

        whole_image_done = false;

        createThreads(false);

        calculation_time = System.currentTimeMillis();

        startThreads(n);

    }

    public void setPoint() {

        resetOrbit();
        final JTextField field_real = new JTextField();
        field_real.addAncestorListener(new RequestFocusListener());

        if (s.plane_transform_center[0] == 0) {
            field_real.setText("" + 0.0);
        } else {
            field_real.setText("" + s.plane_transform_center[0]);
        }

        final JTextField field_imaginary = new JTextField();

        if (s.plane_transform_center[1] == 0) {
            field_imaginary.setText("" + 0.0);
        } else {
            field_imaginary.setText("" + s.plane_transform_center[1]);
        }

        final JCheckBox current_center = new JCheckBox("Current Center");
        current_center.setSelected(false);
        current_center.setFocusable(false);

        current_center.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!current_center.isSelected()) {
                    if (s.plane_transform_center[0] == 0) {
                        field_real.setText("" + 0.0);
                    } else {
                        field_real.setText("" + s.plane_transform_center[0]);
                    }

                    field_real.setEditable(true);

                    if (s.plane_transform_center[1] == 0) {
                        field_imaginary.setText("" + 0.0);
                    } else {
                        field_imaginary.setText("" + s.plane_transform_center[1]);
                    }
                    field_imaginary.setEditable(true);
                } else {
                    Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

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

        if (res == JOptionPane.OK_OPTION) {
            try {
                tempReal = Double.parseDouble(field_real.getText());
                tempImaginary = Double.parseDouble(field_imaginary.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(scroll_pane, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                main_panel.repaint();
                return;
            }
        } else {
            main_panel.repaint();
            return;
        }

        s.plane_transform_center[0] = tempReal;
        s.plane_transform_center[1] = tempImaginary;

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

    public void openCustomPaletteEditor(int temp) {

        resetOrbit();
        options_menu.getPalette()[s.color_choice].setSelected(true); // reselect the old palette
        customPaletteEditor(temp);

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

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || s.ds.domain_coloring || s.ots.useTraps) {
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

    public void setColorTransfer(int val) {
        resetOrbit();

        s.transfer_function = val;

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring) || s.ots.useTraps) {
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

    public CommonFunctions getCommonFunctions() {

        return common;

    }

    public void setGradient() {

        new GradientFrame(ptr, s.gs);

    }

    public void gradientChanged(Color colorA, Color colorB, int interpolation, int color_space, boolean reverse) {

        resetOrbit();

        s.gs.colorA = colorA;
        s.gs.colorB = colorB;
        s.gs.gradient_interpolation = interpolation;
        s.gs.gradient_color_space = color_space;
        s.gs.gradient_reversed = reverse;

        ThreadDraw.gradient = CustomPalette.createGradient(colorA.getRGB(), colorB.getRGB(), 256, interpolation, color_space, reverse);

        updateGradientPreview();

        setOptions(false);

        progress.setValue(0);

        resetImage();

        whole_image_done = false;

        if (s.d3s.d3) {
            Arrays.fill(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), Color.BLACK.getRGB());
        }

        if (s.fs.filters[ANTIALIASING] || s.ds.domain_coloring || s.ots.useTraps) {
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

    public void setOrbitTraps() {

        new OrbitTrapsFrame(ptr, s.ots);

    }

    public void setOrbitTrapSettings() {

        setOptions(false);

        tools_menu.getColorCyling().setEnabled(false);
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

    public static void main(String[] args) throws InterruptedException, Exception {

        SplashFrame sf = new SplashFrame(VERSION);
        sf.setVisible(true);

        while (sf.isAnimating()) {
        }

        sf.dispose();

        MainWindow mw = new MainWindow();
        mw.setVisible(true);

        boolean actionOk = mw.getCommonFunctions().copyLib();
        mw.getCommonFunctions().checkForUpdate(false);
        mw.getCommonFunctions().exportCodeFiles(actionOk);

    }
}
