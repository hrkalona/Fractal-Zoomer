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
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class ToolsOptionsMenu extends JMenu {
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
    private JRadioButtonMenuItem zoom_window_dashed_line;
    private JRadioButtonMenuItem zoom_window_line;
    private JCheckBoxMenuItem fast_julia_filters_opt;
    private JCheckBoxMenuItem show_orbit_converging_point_opt;
    private JMenuItem color_cycling_options_opt;
    
    public ToolsOptionsMenu(MainWindow ptr2, String name, boolean show_orbit_converging_point) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/tools_options.png"));
        
        show_orbit_converging_point_opt = new JCheckBoxMenuItem("Show Converging Point");

        fast_julia_filters_opt = new JCheckBoxMenuItem("Julia Preview Image Filters");

        color_cycling_options_opt = new JMenuItem("Color Cycling Options", getIcon("/fractalzoomer/icons/color_cycling_options.png"));
        
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

        grid_tiles_opt = new JMenuItem("Grid Tiles", getIcon("/fractalzoomer/icons/grid_tiles.png"));

        zoom_window_color_opt = new JMenuItem("Zoom Window Color", getIcon("/fractalzoomer/icons/color.png"));

        zoom_window_menu = new JMenu("Zoom Window");
        zoom_window_menu.setIcon(getIcon("/fractalzoomer/icons/zoom_window_options.png"));

        zoom_window_style_menu = new JMenu("Zoom Window Style");
        zoom_window_style_menu.setIcon(getIcon("/fractalzoomer/icons/orbit_style.png"));

        boundaries_color_opt = new JMenuItem("Boundaries Color", getIcon("/fractalzoomer/icons/color.png"));

        boundaries_number_opt = new JMenuItem("Boundaries Options", getIcon("/fractalzoomer/icons/boundaries_settings.png"));
        
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
        
        fast_julia_filters_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFastJuliaFilters();

            }
        });
        
        orbit_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setOrbitColor();

            }
        });

        color_cycling_options_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorCyclingOptions();

            }
        });
        
        zoom_window_dashed_line = new JRadioButtonMenuItem("Dashed Line");
        zoom_window_dashed_line.setToolTipText("Sets the zooming window style to dashed line.");

        zoom_window_line = new JRadioButtonMenuItem("Solid Line");
        zoom_window_line.setToolTipText("Sets the zooming window style to solid line.");
        
        zoom_window_dashed_line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setZoomWindowDashedLine();

            }
        });

        zoom_window_line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setZoomWindowLine();

            }
        });

        zoom_window_dashed_line.setSelected(true);

        zoom_window_style_menu.add(zoom_window_dashed_line);
        zoom_window_style_menu.add(zoom_window_line);

        ButtonGroup zoom_window_line_button_group = new ButtonGroup();
        zoom_window_line_button_group.add(zoom_window_dashed_line);
        zoom_window_line_button_group.add(zoom_window_line);

        line = new JRadioButtonMenuItem("Line");
        line.setToolTipText("Sets the orbit style to line.");
        dot = new JRadioButtonMenuItem("Dot");
        dot.setToolTipText("Sets the orbit style to dot.");

        line.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setLine();

            }
        });

        dot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setDot();

            }
        });
        
        show_orbit_converging_point_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setShowOrbitConvergingPoint();

            }
        });
        
        orbit_style_menu.add(line);
        orbit_style_menu.add(dot);

        ButtonGroup orbit_style_button_group = new ButtonGroup();
        orbit_style_button_group.add(line);
        orbit_style_button_group.add(dot);
        
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

        grid_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

               ptr.setGridColor();

            }
        });

        zoom_window_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setZoomWindowColor();

            }
        });

        boundaries_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBoundariesColor();

            }
        });

        boundaries_number_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBoundariesNumber();

            }
        });

        grid_tiles_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setGridTiles();

            }
        });
        
        
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
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

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
    
    public JRadioButtonMenuItem getZoomWindowLine() {
        
        return zoom_window_line;
        
    }
    
    public JRadioButtonMenuItem getZoomWindowDashedLine() {
        
        return zoom_window_dashed_line;
        
    }
    
}
