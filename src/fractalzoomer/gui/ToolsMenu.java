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
public class ToolsMenu extends JMenu {
	private static final long serialVersionUID = -5712056897405241065L;
	private MainWindow ptr;
    private JCheckBoxMenuItem orbit_opt;
    private JCheckBoxMenuItem julia_opt;
    private JCheckBoxMenuItem polar_projection_opt;
    private JCheckBoxMenuItem d3_opt;
    private JCheckBoxMenuItem color_cycling_opt;
    private JCheckBoxMenuItem julia_map_opt;
    private JCheckBoxMenuItem domain_coloring_opt;
    private JCheckBoxMenuItem grid_opt;
    private JCheckBoxMenuItem boundaries_opt;
    private JCheckBoxMenuItem zoom_window_opt;
    private JMenuItem plane_visualization_opt;
    
    public ToolsMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
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
        
        orbit_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        polar_projection_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.CTRL_MASK));    
        julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, 0));
        julia_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, ActionEvent.ALT_MASK));
        d3_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK));
        color_cycling_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0));
        plane_visualization_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.SHIFT_MASK));
        domain_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
        grid_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        zoom_window_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.SHIFT_MASK));
        boundaries_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.SHIFT_MASK));
        
        orbit_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setOrbitOption();

            }
        });

        julia_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setJuliaOption();

            }
        });

        color_cycling_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorCycling();

            }
        });

        plane_visualization_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPlaneVizualization();

            }
        });

        grid_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setGrid();

            }
        });

        zoom_window_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setZoomWindow();

            }
        });

        boundaries_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBoundaries();

            }
        });

        julia_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setJuliaMap();

            }
        });

        domain_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setDomainColoring();

            }
        });

        d3_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.set3DOption();

            }
        });

        polar_projection_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPolarProjection();

            }
        });
        
        add(orbit_opt);
        addSeparator();
        add(julia_opt);
        addSeparator();
        add(julia_map_opt);
        addSeparator();
        add(d3_opt);
        addSeparator();
        add(polar_projection_opt);
        addSeparator();
        add(domain_coloring_opt);
        addSeparator();
        add(color_cycling_opt);
        addSeparator();
        add(plane_visualization_opt);
        addSeparator();
        add(grid_opt);
        addSeparator();
        add(zoom_window_opt);
        addSeparator();
        add(boundaries_opt);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JCheckBoxMenuItem getOrbit() {
        
        return orbit_opt;
        
    }
    
    public JCheckBoxMenuItem getJulia() {
        
        return julia_opt;
        
    }
    
    public JCheckBoxMenuItem getJuliaMap() {
        
        return julia_map_opt;
        
    }
    
    public JCheckBoxMenuItem getPolarProjection() {
        
        return polar_projection_opt;
        
    }
    
    public JCheckBoxMenuItem getGrid() {
        
        return grid_opt;
        
    }
    
    public JCheckBoxMenuItem getColorCycling() {
        
        return color_cycling_opt;
        
    }
    
    public JCheckBoxMenuItem getDomainColoring() {
        
        return domain_coloring_opt;
        
    }
    
    public JCheckBoxMenuItem getBoundaries() {
        
        return boundaries_opt;
        
    }
    
    public JCheckBoxMenuItem getZoomWindow() {
        
        return zoom_window_opt;
        
    }
    
    public JCheckBoxMenuItem get3D() {
        
        return d3_opt;
        
    }
    
    public JMenuItem getPlaneVizualization() {
        
        return plane_visualization_opt;
        
    }
}
