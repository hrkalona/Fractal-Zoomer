
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.PaletteSettings;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class OptionsMenu extends MyMenu {

    private static final long serialVersionUID = -7875948962252862696L;
    private MainWindow ptr;
    private ColorsMenu colors_menu;
    private JMenu iterations_menu;
    private BailoutConditionsMenu bailout_condition_menu;
    private ConvergentBailoutConditionsMenu convergent_bailout_condition_menu;
    private JMenu rotation_menu;
    private OptimizationsMenu optimizations_menu;
    private ToolsOptionsMenu tools_options_menu;

    private JMenu general_options_menu;
    private JMenuItem filters_options;
    private JMenuItem size_of_image;
    private JMenuItem iterations;

    private JMenuItem jitter_opt;
    private JMenuItem increase_iterations;
    private JMenuItem decrease_iterations;

    private JMenuItem double_iterations;
    private JMenuItem period;
    private JMenuItem height_ratio_number;
    private JMenuItem set_rotation;
    private JMenuItem increase_rotation;
    private JMenuItem decrease_rotation;
    private JMenuItem bailout_number;
    private JMenuItem convergent_bailout_number;
    private JMenuItem change_zooming_factor;
    private JMenuItem point_opt;
    private JMenuItem variables_opt;
    private JMenuItem overview_opt;
    private JMenuItem stats_opt;
    private JMenuItem metrics_opt;

    private JMenuItem thread_stats_opt;
    private FractalOptionsMenu fractal_options_menu;
    private JMenu window_menu;
    private JCheckBoxMenuItem toolbar_opt;
    private JCheckBoxMenuItem statusbar_opt;
    private JCheckBoxMenuItem infobar_opt;

    private JCheckBoxMenuItem debugbar_opt;
    private JCheckBoxMenuItem fullscreen_opt;

    private JCheckBoxMenuItem always_show_progress_bar_opt;
    private JCheckBoxMenuItem show_minimal_progress_bar_opt;

    private JCheckBoxMenuItem auto_repaint_image_opt;

    private JCheckBoxMenuItem zoom_on_cursor_opt;

    private JCheckBoxMenuItem zoom_to_rectangle_selection_opt;
    private JCheckBoxMenuItem auto_zoom_to_rectangle_selection_opt;

    private JMenuItem quick_render_opt;

    private JMenu bailout_menu;

    public OptionsMenu(MainWindow ptr2, String name, PaletteSettings ps, PaletteSettings ps2, boolean smoothing, boolean show_orbit_converging_point, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int out_coloring_algorithm, int in_coloring_algorithm, int function, int plane_type, int bailout_test_algorithm, int color_blending, boolean color_blending_reverse_order, int temp_color_cycling_location, int temp_color_cycling_location2, int pre_filter, int post_filter, int plane_influence, int convergent_bailout_test_algorithm, boolean flip_re, boolean flip_im) {

        super(name);

        this.ptr = ptr2;

        fractal_options_menu = new FractalOptionsMenu(ptr, "Fractal Options", apply_plane_on_julia, apply_plane_on_julia_seed, function, plane_type, pre_filter, post_filter, plane_influence, flip_re, flip_im);

        iterations_menu = new MyMenu("Iterations");
        iterations_menu.setIcon(MainWindow.getIcon("iterations.png"));

        increase_iterations = new MyMenuItem("Increase Iterations", MainWindow.getIcon("plus.png"));

        decrease_iterations = new MyMenuItem("Decrease Iterations", MainWindow.getIcon("minus.png"));

        double_iterations = new MyMenuItem("Double Iterations", MainWindow.getIcon("x2.png"));

        iterations = new MyMenuItem("Set Iterations", MainWindow.getIcon("iterations.png"));

        period  = new MyMenuItem("Period", MainWindow.getIcon("period.png"));

        jitter_opt = new MyMenuItem("Jitter", MainWindow.getIcon("jitter.png"));

        bailout_condition_menu = new BailoutConditionsMenu(ptr, "Bailout Condition", bailout_test_algorithm);

        convergent_bailout_condition_menu = new ConvergentBailoutConditionsMenu(ptr, "Convergent Bailout Condition", convergent_bailout_test_algorithm);

        bailout_number = new MyMenuItem("Bailout", MainWindow.getIcon("bailout.png"));
        convergent_bailout_number = new MyMenuItem("Convergent Bailout", MainWindow.getIcon("convergent_bailout.png"));

        convergent_bailout_number.setEnabled(false);

        rotation_menu = new MyMenu("Rotation");
        rotation_menu.setIcon(MainWindow.getIcon("rotate.png"));

        set_rotation = new MyMenuItem("Set Rotation", MainWindow.getIcon("rotate.png"));

        increase_rotation = new MyMenuItem("Increase Rotation", MainWindow.getIcon("plus.png"));

        decrease_rotation = new MyMenuItem("Decrease Rotation", MainWindow.getIcon("minus.png"));

        point_opt = new MyMenuItem("User Point", MainWindow.getIcon("user_point.png"));
        variables_opt = new MyMenuItem("User Variables", MainWindow.getIcon("variables.png"));

        change_zooming_factor = new MyMenuItem("Zooming Factor", MainWindow.getIcon("zooming_factor.png"));

        size_of_image = new MyMenuItem("Image Size", MainWindow.getIcon("image_size.png"));

        height_ratio_number = new MyMenuItem("Stretch Factor", MainWindow.getIcon("stretch.png"));

        optimizations_menu = new OptimizationsMenu(ptr, "Optimizations");

        tools_options_menu = new ToolsOptionsMenu(ptr, "Tools Options", show_orbit_converging_point);

        general_options_menu = new MyMenu("General Options");
        general_options_menu.setIcon(MainWindow.getIcon("general_options.png"));

        filters_options = new MyMenuItem("Filters Options", MainWindow.getIcon("filter_options.png"));

        bailout_menu = new MyMenu("Bailout");
        bailout_menu.setIcon(MainWindow.getIcon("bailoutg.png"));

        window_menu = new MyMenu("Window");
        window_menu.setIcon(MainWindow.getIcon("window.png"));

        colors_menu = new ColorsMenu(ptr, "Colors", ps, ps2, smoothing, out_coloring_algorithm, in_coloring_algorithm, color_blending, color_blending_reverse_order, temp_color_cycling_location, temp_color_cycling_location2);

        overview_opt = new MyMenuItem("Options Overview", MainWindow.getIcon("overview.png"));

        stats_opt = new MyMenuItem("Statistics", MainWindow.getIcon("stats.png"));

        thread_stats_opt = new MyMenuItem("Task Statistics", MainWindow.getIcon("stats_tasks.png"));

        metrics_opt = new MyMenuItem("Metrics", MainWindow.getIcon("chart.png"));

        toolbar_opt = new MyCheckBoxMenuItem("Tool Bar");
        statusbar_opt = new MyCheckBoxMenuItem("Status Bar");
        debugbar_opt = new MyCheckBoxMenuItem("Debug Bar");
        infobar_opt = new MyCheckBoxMenuItem("Information Bar");
        fullscreen_opt = new MyCheckBoxMenuItem("Full Screen");
        always_show_progress_bar_opt = new MyCheckBoxMenuItem("Always Show Progress Bar");
        show_minimal_progress_bar_opt = new MyCheckBoxMenuItem("Show Minimal Progress Bar");

        auto_repaint_image_opt  = new MyCheckBoxMenuItem("Show Rendering Progress");
        zoom_on_cursor_opt = new MyCheckBoxMenuItem("Zoom on Mouse Cursor", MainWindow.getIcon("zoom_cursor.png"));
        quick_render_opt = new MyMenuItem("Quick Render", MainWindow.getIcon("quickrender.png"));
        zoom_to_rectangle_selection_opt = new MyCheckBoxMenuItem("Zoom to Rectangle Selection", MainWindow.getIcon("crop.png"));
        auto_zoom_to_rectangle_selection_opt = new MyCheckBoxMenuItem("Automatic Zoom to Rectangle Selection");

        auto_repaint_image_opt.setSelected(MainWindow.AUTO_REPAINT_IMAGE);
        zoom_on_cursor_opt.setSelected(MainWindow.ZOOM_ON_THE_CURSOR);
        always_show_progress_bar_opt.setSelected(MainWindow.alwaysShowProgressBar);
        show_minimal_progress_bar_opt.setSelected(MainWindow.minimalProgressBar);

        size_of_image.setToolTipText("Sets the image size.");
        period.setToolTipText("Sets the period for the reference calculation.");
        iterations.setToolTipText("Sets the maximum number of iterations.");
        increase_iterations.setToolTipText("Increases the maximum iterations number by one.");
        decrease_iterations.setToolTipText("Decreases the maximum iterations number by one.");
        double_iterations.setToolTipText("Doubles the maximum iterations.");
        bailout_number.setToolTipText("Sets the bailout. Above this number the norm of a complex numbers is not bounded.");
        convergent_bailout_number.setToolTipText("Sets the convergent bailout. This defines the acceptable error for converging functions.");
        set_rotation.setToolTipText("Sets the rotation in degrees.");
        point_opt.setToolTipText("A point picked by the user, for the point variable.");
        variables_opt.setToolTipText("Sets the values for the variables v1-v30.");
        increase_rotation.setToolTipText("Increases the rotation by one degree.");
        decrease_rotation.setToolTipText("Decreases the rotation by one degree.");
        change_zooming_factor.setToolTipText("Sets the rate of each zoom.");
        height_ratio_number.setToolTipText("Changes the ratio of the image and creates a stretch.");
        filters_options.setToolTipText("Sets the options of the image filters.");
        overview_opt.setToolTipText("Creates a report of all the active fractal options.");
        stats_opt.setToolTipText("Displays the statistics of last rendered fractal.");
        metrics_opt.setToolTipText("Displays some time-series metrics.");
        thread_stats_opt.setToolTipText("Displays the task statistics of last rendered fractal.");
        toolbar_opt.setToolTipText("Activates the tool bar.");
        statusbar_opt.setToolTipText("Activates the status bar.");
        debugbar_opt.setToolTipText("Activate the debug bar.");
        infobar_opt.setToolTipText("Activates the information bar.");
        fullscreen_opt.setToolTipText("Toggles the application from window mode to full screen.");
        always_show_progress_bar_opt.setToolTipText("Displays the progress bar permanently.");
        show_minimal_progress_bar_opt.setToolTipText("Displays a minimal progress bar.");
        jitter_opt.setToolTipText("Adds jitter to each pixel.");
        zoom_on_cursor_opt.setToolTipText("Changes the zooming mechanism, so the zooming stays around the mouse cursor.");
        auto_repaint_image_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
        quick_render_opt.setToolTipText("Sets the options for the quick render method.");
        zoom_to_rectangle_selection_opt.setToolTipText("Creates a rectangle which indicates the zoom area.");

        size_of_image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, 0));
        period.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0));
        increase_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.ALT_MASK));
        decrease_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.ALT_MASK));
        double_iterations.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
        bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.ALT_MASK));
        convergent_bailout_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        set_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        increase_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.CTRL_MASK));
        decrease_rotation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        point_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0));
        variables_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, ActionEvent.CTRL_MASK));
        change_zooming_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        height_ratio_number.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.SHIFT_MASK));
        filters_options.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.SHIFT_MASK));
        toolbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));
        statusbar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        infobar_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        fullscreen_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
        stats_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
        jitter_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        quick_render_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.SHIFT_MASK));
        thread_stats_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, ActionEvent.CTRL_MASK));
        metrics_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, ActionEvent.SHIFT_MASK));

        overview_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.SHIFT_MASK));

        size_of_image.addActionListener(e -> ptr.setSizeOfImage());

        iterations.addActionListener(e -> ptr.setIterations());

        period.addActionListener(e -> ptr.setPeriod());

        increase_iterations.addActionListener(e -> ptr.increaseIterations());

        decrease_iterations.addActionListener(e -> ptr.decreaseIterations());

        double_iterations.addActionListener(e -> ptr.doubleIterations());

        bailout_number.addActionListener(e -> ptr.setBailout());
        convergent_bailout_number.addActionListener(e -> ptr.setConvergentBailout());

        set_rotation.addActionListener(e -> ptr.setRotation());

        increase_rotation.addActionListener(e -> ptr.increaseRotation());

        decrease_rotation.addActionListener(e -> ptr.decreaseRotation());

        point_opt.addActionListener(e -> ptr.setPoint());

        variables_opt.addActionListener(e -> ptr.setVariables());

        change_zooming_factor.addActionListener(e -> ptr.setZoomingFactor());

        height_ratio_number.addActionListener(e -> ptr.setHeightRatio());

        overview_opt.addActionListener(e -> ptr.Overview());

        stats_opt.addActionListener(e -> ptr.Stats());

        metrics_opt.addActionListener(e ->ptr.Metrics());

        thread_stats_opt.addActionListener(e -> ptr.TaskStats());

        toolbar_opt.addActionListener(e -> ptr.setToolbar());

        statusbar_opt.addActionListener(e -> ptr.setStatubar());

        infobar_opt.addActionListener(e -> ptr.setInfobar());

        debugbar_opt.addActionListener(e -> ptr.setDebugbar());

        fullscreen_opt.addActionListener(e -> ptr.setFullScreen());

        always_show_progress_bar_opt.addActionListener(e -> ptr.setAlwaysShowProgressBar());

        show_minimal_progress_bar_opt.addActionListener(e -> ptr.setMinimalProgressBar());

        jitter_opt.addActionListener(e -> ptr.setJitter());

        auto_repaint_image_opt.addActionListener(e -> ptr.setAutoRepaintImage());
        zoom_on_cursor_opt.addActionListener(e -> ptr.setZoomOnCursor());
        quick_render_opt.addActionListener(e -> ptr.setQuickRenderTiles());
        zoom_to_rectangle_selection_opt.addActionListener(e -> ptr.setZoomToTheSelectedArea());

        window_menu.add(toolbar_opt);
        window_menu.add(infobar_opt);
        window_menu.add(statusbar_opt);
        window_menu.add(debugbar_opt);
        window_menu.add(fullscreen_opt);
        window_menu.add(always_show_progress_bar_opt);
        if(MainWindow.useCustomLaf) {
            window_menu.add(show_minimal_progress_bar_opt);
        }

        toolbar_opt.setSelected(true);
        infobar_opt.setSelected(true);
        statusbar_opt.setSelected(true);
        fullscreen_opt.setSelected(false);
        debugbar_opt.setSelected(false);

        filters_options.addActionListener(e -> ptr.filtersOptions());

        iterations_menu.add(iterations);
        iterations_menu.add(increase_iterations);
        iterations_menu.add(decrease_iterations);
        iterations_menu.add(double_iterations);

        rotation_menu.add(set_rotation);
        rotation_menu.add(increase_rotation);
        rotation_menu.add(decrease_rotation);

        general_options_menu.add(quick_render_opt);
        general_options_menu.addSeparator();
        general_options_menu.add(auto_repaint_image_opt);
        general_options_menu.addSeparator();
        general_options_menu.add(zoom_on_cursor_opt);
        general_options_menu.addSeparator();
        general_options_menu.add(zoom_to_rectangle_selection_opt);
        general_options_menu.addSeparator();
        general_options_menu.add(auto_zoom_to_rectangle_selection_opt);

        bailout_menu.add(bailout_condition_menu);
        bailout_menu.add(bailout_number);
        bailout_menu.add(convergent_bailout_condition_menu);
        bailout_menu.add(convergent_bailout_number);

        add(fractal_options_menu);
        addSeparator();
        add(colors_menu);
        addSeparator();
        add(iterations_menu);
        addSeparator();
        add(bailout_menu);
        addSeparator();
        add(rotation_menu);
        addSeparator();
        add(point_opt);
        add(variables_opt);
        addSeparator();
        add(size_of_image);
        addSeparator();
        add(height_ratio_number);
        addSeparator();
        add(jitter_opt);
        addSeparator();
        add(period);
        addSeparator();
        add(change_zooming_factor);
        addSeparator();
        add(optimizations_menu);
        addSeparator();
        add(tools_options_menu);
        addSeparator();
        add(general_options_menu);
        addSeparator();
        add(filters_options);
        addSeparator();
        add(overview_opt);
        addSeparator();
        add(stats_opt);
        addSeparator();
        add(thread_stats_opt);
        addSeparator();
        add(metrics_opt);
        addSeparator();
        add(window_menu);
    }

    public JCheckBoxMenuItem getPeriodicityChecking() {

        return optimizations_menu.getPeriodicityChecking();

    }

    public JMenuItem getRenderingAlgorithm() {

        return optimizations_menu.getRenderingAlgorithm();

    }

    public JMenu getBailoutMenu() {
        return bailout_menu;
    }

    public JMenuItem getJitter() {
        return jitter_opt;
    }

    public JRadioButtonMenuItem[] getOutColoringPalette() {

        return colors_menu.getOutColoringPalette();

    }

    public JRadioButtonMenuItem[] getInColoringPalette() {

        return colors_menu.getInColoringPalette();

    }

    public JMenuItem getEntropyColoring() {

        return colors_menu.getEntropyColoring();

    }

    public JMenuItem getGreyScaleColoring() {

        return colors_menu.getGreyScaleColoring();

    }

    public JMenuItem getOffsetColoring() {

        return colors_menu.getOffsetColoring();

    }

    public JMenuItem getRainbowPalette() {

        return colors_menu.getRainbowPalette();

    }

    public JMenuItem getBumpMap() {

        return colors_menu.getBumpMap();

    }

    public JMenuItem getColorSpaceParams() {

        return colors_menu.getColorSpaceParams();

    }

    public JMenuItem getFakeDistanceEstimation() {

        return colors_menu.getFakeDistanceEstimation();

    }

    public JMenuItem getNumericalDistanceEstimator() {

        return colors_menu.getNumericalDistanceEstimator();

    }

    public JMenuItem getSmoothing() {

        return colors_menu.getSmoothing();

    }

    public JMenuItem getDistanceEstimation() {

        return colors_menu.getDistanceEstimation();

    }

    public JMenuItem getFractalColor() {

        return colors_menu.getFractalColor();

    }

    public JMenuItem getContourFactor() {

        return colors_menu.getContourFactor();

    }

    public JRadioButtonMenuItem[] getOutColoringModes() {

        return colors_menu.getOutColoringModes();

    }

    public JRadioButtonMenuItem[] getInColoringModes() {

        return colors_menu.getInColoringModes();

    }

    public OutColoringModesMenu getOutColoringMenu() {

        return colors_menu.getOutColoringMenu();

    }

    public InColoringModesMenu getInColoringMenu() {

        return colors_menu.getInColoringMenu();

    }

    public ColorsMenu getColorsMenu() {

        return colors_menu;

    }

    public JCheckBoxMenuItem getShowOrbitConvergingPoint() {

        return tools_options_menu.getShowOrbitConvergingPoint();

    }

    public JCheckBoxMenuItem getFastJuliaFiltersOptions() {

        return tools_options_menu.getFastJuliaFiltersOptions();

    }

    public JRadioButtonMenuItem getLine() {

        return tools_options_menu.getLine();

    }

    public JRadioButtonMenuItem getLine2() {

        return tools_options_menu.getLine2();

    }

    public JRadioButtonMenuItem getDot() {

        return tools_options_menu.getDot();

    }

    public JRadioButtonMenuItem getZoomWindowLine() {

        return tools_options_menu.getZoomWindowLine();

    }

    public JRadioButtonMenuItem getZoomWindowDashedLine() {

        return tools_options_menu.getZoomWindowDashedLine();

    }

    public JMenuItem getInitialValue() {

        return fractal_options_menu.getInitialValue();

    }

    public JMenuItem getPerturbation() {

        return fractal_options_menu.getPerturbation();

    }

    public JMenuItem getBailout() {

        return bailout_number;

    }

    public JMenuItem getConvergentBailout() {

        return convergent_bailout_number;

    }

    public BailoutConditionsMenu getBailoutConditionMenu() {

        return bailout_condition_menu;

    }

    public ConvergentBailoutConditionsMenu getConvergentBailoutConditionMenu() {

        return convergent_bailout_condition_menu;

    }

    public JMenu getRotationMenu() {

        return rotation_menu;

    }

    public JRadioButtonMenuItem[] getPlanes() {

        return fractal_options_menu.getPlanes();

    }

    public PlanesMenu getPlanesMenu() {

        return fractal_options_menu.getPlanesMenu();

    }

    public JCheckBoxMenuItem getApplyPlaneOnWholeJuliaOpt() {

        return fractal_options_menu.getApplyPlaneOnWholeJuliaOpt();

    }

    public JCheckBoxMenuItem getApplyPlaneOnJuliaSeedOpt() {

        return fractal_options_menu.getApplyPlaneOnJuliaSeedOpt();

    }

    public JRadioButtonMenuItem[] getFractalFunctions() {

        return fractal_options_menu.getFractalFunctions();

    }

    public JCheckBoxMenuItem getBurningShipOpt() {

        return fractal_options_menu.getBurningShipOpt();

    }

    public JMenuItem getMandelGrassOpt() {

        return fractal_options_menu.getMandelGrassOpt();

    }

    public JRadioButtonMenuItem[] getBailoutConditions() {

        return bailout_condition_menu.getBailoutConditions();

    }

    public JRadioButtonMenuItem[] getConvergentBailoutConditions() {

        return convergent_bailout_condition_menu.getConvergentBailoutConditions();

    }

    public JMenu getIterationsMenu() {

        return iterations_menu;

    }

    public JMenuItem getSizeOfImage() {

        return size_of_image;

    }

    public JMenuItem getHeightRatio() {

        return height_ratio_number;

    }

    public JMenuItem getPoint() {

        return point_opt;

    }

    public JMenuItem getVariables() {

        return variables_opt;

    }

    public FractalFunctionsMenu getFractalFunctionsMenu() {

        return fractal_options_menu.getFractalFunctionsMenu();

    }

    public JMenuItem getFiltersOptions() {

        return filters_options;

    }

    public FractalOptionsMenu getFractalOptionsMenu() {

        return fractal_options_menu;

    }

    public JMenu getPostFunctionFiltersMenu() {

        return fractal_options_menu.getPostFunctionFiltersMenu();

    }

    public JMenu getPreFunctionFiltersMenu() {

        return fractal_options_menu.getPreFunctionFiltersMenu();

    }

    public JMenuItem getOverview() {

        return overview_opt;

    }

    public JMenuItem getStats() {

        return stats_opt;

    }

    public JMenuItem getStatsTasks() {
        return thread_stats_opt;
    }

    public JCheckBoxMenuItem getToolbar() {

        return toolbar_opt;

    }

    public JCheckBoxMenuItem getInfobar() {

        return infobar_opt;

    }

    public JCheckBoxMenuItem getFullscreen() {

        return fullscreen_opt;

    }

    public JCheckBoxMenuItem getStatusbar() {

        return statusbar_opt;

    }

    public JCheckBoxMenuItem getDebugbar() {

        return debugbar_opt;

    }

    public JCheckBoxMenuItem getAlways_show_progress_bar() {
        return always_show_progress_bar_opt;
    }

    public JCheckBoxMenuItem getShow_minimal_progress_bar() {
        return show_minimal_progress_bar_opt;
    }
    public OptimizationsMenu getOptimizationsMenu() {

        return optimizations_menu;

    }

    public ToolsOptionsMenu getToolsOptionsMenu() {

        return tools_options_menu;

    }

    public JMenu getGeneralOptionsMenu() {

        return general_options_menu;

    }

    public JMenuItem getRandomPalette() {

        return colors_menu.getRandomPalette();

    }

    public OutColoringPaletteMenu getOutColoringPaletteMenu() {

        return colors_menu.getOutColoringPaletteMenu();

    }

    public InColoringPaletteMenu getInColoringPaletteMenu() {

        return colors_menu.getInColoringPaletteMenu();

    }

    public ProcessingMenu getProcessing() {

        return colors_menu.getProcessing();

    }

    public JMenuItem getLight() {

        return colors_menu.getLight();

    }

    public JMenuItem getSlopes() {

        return colors_menu.getSlopes();

    }
    
    public JMenuItem getHistogramColoring() {
        
        return colors_menu.getHistogramColoring();
        
    }

    public ColorBlendingMenu getColorBlending() {

        return colors_menu.getColorBlending();

    }

    public JRadioButtonMenuItem[] getBlendingModes() {

        return colors_menu.getBlendingModes();

    }

    public JRadioButtonMenuItem[] getInColoringTranferFunctions() {

        return colors_menu.getInColoringTranferFunctions();

    }

    public JRadioButtonMenuItem[] getOutColoringTranferFunctions() {

        return colors_menu.getOutColoringTranferFunctions();

    }

    public JMenuItem getGradient() {

        return colors_menu.getGradient();

    }

    public JMenuItem getOrbitTraps() {

        return colors_menu.getOrbitTraps();

    }

    public JCheckBoxMenuItem getDirectColor() {

        return colors_menu.getDirectColor();

    }

    public JMenuItem getContourColoring() {

        return colors_menu.getContourColoring();

    }

    public JMenuItem getProcessingOrder() {

        return colors_menu.getProcessingOrder();

    }

    public JCheckBoxMenuItem getUsePaletteForInColoring() {

        return colors_menu.getUsePaletteForInColoring();

    }

    public JMenuItem getPaletteGradientMerging() {
        return colors_menu.getPaletteGradientMerging();
    }

    public JMenuItem getStatisticsColoring() {

        return colors_menu.getStatisticsColoring();

    }

    public JMenuItem getOutTrueColoring() {
        return colors_menu.getOutTrueColoring();
    }

    public JMenuItem getInTrueColoring() {
        return colors_menu.getInTrueColoring();
    }

    public JMenuItem getPeriod() {
        return period;
    }

    public JRadioButtonMenuItem[] getPreFunctionFilters() {

        return fractal_options_menu.getPreFunctionFilters();

    }

    public JRadioButtonMenuItem[] getPostFunctionFilters() {

        return fractal_options_menu.getPostFunctionFilters();

    }

    public JRadioButtonMenuItem[] getPlaneInfluences() {

        return fractal_options_menu.getPlaneInfluences();

    }

    public JMenu getPlaneInfluenceMenu() {

        return fractal_options_menu.getPlaneInfluenceMenu();

    }

    public JCheckBoxMenuItem getAutoRepaintImage() {

        return auto_repaint_image_opt;

    }

    public JCheckBoxMenuItem getZoomOnCursor() {

        return zoom_on_cursor_opt;

    }

    public JCheckBoxMenuItem getZoomToRectangleSelection() {
        return zoom_to_rectangle_selection_opt;
    }

    public JCheckBoxMenuItem getAutoZoomToRectangleSelection() {
        return auto_zoom_to_rectangle_selection_opt;
    }

    public JMenuItem getZoomFactor() {
        return change_zooming_factor;
    }

    public void updateIcons(Settings s) {

        if(s.js.enableJitter) {
            jitter_opt.setIcon(MainWindow.getIcon("jitter_enabled.png"));
        }
        else {
            jitter_opt.setIcon(MainWindow.getIcon("jitter.png"));
        }

    }

    public JCheckBoxMenuItem getFlipReOpt() {

        return fractal_options_menu.getFlipReOpt();

    }

    public JCheckBoxMenuItem getFlipImOpt() {

        return fractal_options_menu.getFlipImOpt();

    }

}
