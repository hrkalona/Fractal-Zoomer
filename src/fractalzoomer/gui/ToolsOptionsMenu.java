
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class ToolsOptionsMenu extends MyMenu {
	private static final long serialVersionUID = 841026734035473828L;
	private MainWindow ptr;
    private JMenu orbit_menu;
    private JMenu orbit_style_menu;
    private JMenu zoom_window_menu;
    private JMenu zoom_window_style_menu;
    private JMenu boundaries_menu;
    private JMenu grid_menu;
    private JMenuItem orbit_color_opt;
    private JMenuItem grid_tiles_opt;
    private JMenuItem grid_color_opt;
    private JMenuItem boundaries_number_opt;
    private JMenuItem boundaries_color_opt;
    private JMenuItem zoom_window_color_opt;
    private JRadioButtonMenuItem line;
    private JRadioButtonMenuItem dot;

    private JRadioButtonMenuItem line2;
    private JRadioButtonMenuItem zoom_window_dashed_line;
    private JRadioButtonMenuItem zoom_window_line;
    private JCheckBoxMenuItem fast_julia_filters_opt;
    private JCheckBoxMenuItem show_orbit_converging_point_opt;
    private JMenuItem color_cycling_options_opt;
    
    public ToolsOptionsMenu(MainWindow ptr2, String name, boolean show_orbit_converging_point) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("tools_options.png"));
        
        show_orbit_converging_point_opt = new MyCheckBoxMenuItem("Show Converging Point");
        show_orbit_converging_point_opt.setSelected(MainWindow.show_orbit_converging_point);

        fast_julia_filters_opt = new MyCheckBoxMenuItem("Julia Preview Image Filters");
        fast_julia_filters_opt.setSelected(MainWindow.FAST_JULIA_FILTERS);

        color_cycling_options_opt = new MyMenuItem("Color Cycling Options", MainWindow.getIcon("color_cycling.png"));
        
        orbit_menu = new MyMenu("Orbit");
        orbit_menu.setIcon(MainWindow.getIcon("orbit.png"));

        grid_menu = new MyMenu("Grid");
        grid_menu.setIcon(MainWindow.getIcon("grid.png"));

        boundaries_menu = new MyMenu("Boundaries");
        boundaries_menu.setIcon(MainWindow.getIcon("boundaries.png"));

        orbit_color_opt = new MyMenuItem("Orbit Color", MainWindow.getIcon("color.png"));

        orbit_style_menu = new MyMenu("Orbit Style");
        orbit_style_menu.setIcon(MainWindow.getIcon("tools_options.png"));
        
        grid_color_opt = new MyMenuItem("Grid Color", MainWindow.getIcon("color.png"));

        grid_tiles_opt = new MyMenuItem("Grid Tiles", MainWindow.getIcon("tools_options.png"));

        zoom_window_color_opt = new MyMenuItem("Zoom Window Color", MainWindow.getIcon("color.png"));

        zoom_window_menu = new MyMenu("Zoom Window");
        zoom_window_menu.setIcon(MainWindow.getIcon("zoom_window.png"));

        zoom_window_style_menu = new MyMenu("Zoom Window Style");
        zoom_window_style_menu.setIcon(MainWindow.getIcon("tools_options.png"));

        boundaries_color_opt = new MyMenuItem("Boundaries Color", MainWindow.getIcon("color.png"));

        boundaries_number_opt = new MyMenuItem("Boundaries Options", MainWindow.getIcon("tools_options.png"));
        
        grid_color_opt.setToolTipText("Sets the color of the grid.");
        grid_tiles_opt.setToolTipText("Sets the number of the grid tiles.");
        boundaries_color_opt.setToolTipText("Sets the color of the boundaries.");
        boundaries_number_opt.setToolTipText("Sets the boundaries options.");
        orbit_color_opt.setToolTipText("Sets the color of the orbit.");
        show_orbit_converging_point_opt.setToolTipText("Displays the the root of the orbit.");
        zoom_window_color_opt.setToolTipText("Sets the color of zooming window.");
        fast_julia_filters_opt.setToolTipText("Activates the filters for the julia preview.");
        color_cycling_options_opt.setToolTipText("Changes the color cycling options.");
        
        fast_julia_filters_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        color_cycling_options_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.ALT_MASK));
        show_orbit_converging_point_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        
        show_orbit_converging_point_opt.setSelected(show_orbit_converging_point);
        
        fast_julia_filters_opt.addActionListener(e -> ptr.setFastJuliaFilters());
        
        orbit_color_opt.addActionListener(e -> ptr.setOrbitColor());

        color_cycling_options_opt.addActionListener(e -> ptr.setColorCyclingOptions());
        
        zoom_window_dashed_line = new JRadioButtonMenuItem("Dashed Line");
        zoom_window_dashed_line.setToolTipText("Sets the zooming window style to dashed line.");

        zoom_window_line = new JRadioButtonMenuItem("Solid Line");
        zoom_window_line.setToolTipText("Sets the zooming window style to solid line.");
        
        zoom_window_dashed_line.addActionListener(e -> ptr.setZoomWindowDashedLine());

        zoom_window_line.addActionListener(e -> ptr.setZoomWindowLine());

        zoom_window_dashed_line.setSelected(true);

        zoom_window_style_menu.add(zoom_window_dashed_line);
        zoom_window_style_menu.add(zoom_window_line);

        ButtonGroup zoom_window_line_button_group = new ButtonGroup();
        zoom_window_line_button_group.add(zoom_window_dashed_line);
        zoom_window_line_button_group.add(zoom_window_line);

        line = new JRadioButtonMenuItem("Line");
        line.setToolTipText("Sets the orbit style to line with dots.");
        dot = new JRadioButtonMenuItem("Dot");
        dot.setToolTipText("Sets the orbit style to dot.");
        line2 = new JRadioButtonMenuItem("Line (No Dots)");
        line2.setToolTipText("Sets the orbit style to line without dots.");

        line.addActionListener(e -> ptr.setOrbitStyle(0));

        dot.addActionListener(e -> ptr.setOrbitStyle(1));

        line2.addActionListener(e -> ptr.setOrbitStyle(2));
        
        show_orbit_converging_point_opt.addActionListener(e -> ptr.setShowOrbitConvergingPoint());
        
        orbit_style_menu.add(line);
        orbit_style_menu.add(dot);
        orbit_style_menu.add(line2);

        ButtonGroup orbit_style_button_group = new ButtonGroup();
        orbit_style_button_group.add(line);
        orbit_style_button_group.add(dot);
        orbit_style_button_group.add(line2);
        
        line.setSelected(true);

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

        grid_color_opt.addActionListener(e -> ptr.setGridColor());

        zoom_window_color_opt.addActionListener(e -> ptr.setZoomWindowColor());

        boundaries_color_opt.addActionListener(e -> ptr.setBoundariesColor());

        boundaries_number_opt.addActionListener(e -> ptr.setBoundariesNumber());

        grid_tiles_opt.addActionListener(e -> ptr.setGridTiles());
        
        
        add(orbit_menu);
        addSeparator();
        add(fast_julia_filters_opt);
        addSeparator();
        add(color_cycling_options_opt);
        addSeparator();
        add(grid_menu);
        addSeparator();
        add(zoom_window_menu);
        addSeparator();
        add(boundaries_menu);
        
    }
    
    public JCheckBoxMenuItem getShowOrbitConvergingPoint() {
    
        return show_orbit_converging_point_opt;
        
    }
    
    public JCheckBoxMenuItem getFastJuliaFiltersOptions() {
    
        return fast_julia_filters_opt;
        
    }
    
    public JRadioButtonMenuItem getLine() {
        
        return line;
        
    }
    
    public JRadioButtonMenuItem getDot() {
        
        return dot;
        
    }

    public JRadioButtonMenuItem getLine2() {

        return line2;

    }
    
    public JRadioButtonMenuItem getZoomWindowLine() {
        
        return zoom_window_line;
        
    }
    
    public JRadioButtonMenuItem getZoomWindowDashedLine() {
        
        return zoom_window_dashed_line;
        
    }
    
}
