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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author kaloch
 */
public class Toolbar extends JToolBar {
	private static final long serialVersionUID = -2311258550706901828L;
	private MainWindow ptr;
    private JButton starting_position_button;
    private JButton zoom_in_button;
    private JButton zoom_out_button;
    private JButton go_to_button;
    private JButton save_image_button;
    private JButton save_image_and_settings_button;
    private JButton custom_palette_button_out;
    private JButton custom_palette_button_in;
    private JButton random_palette_button;
    private JButton iterations_button;
    private JButton rotation_button;
    private JButton filters_options_button;
    private JButton orbit_button;
    private JButton julia_button;
    private JButton polar_projection_button;
    private JButton color_cycling_button;
    private JButton d3_button;
    private JButton julia_map_button;
    private JButton domain_coloring_button;
    private JButton help_button;
    
    public Toolbar(MainWindow ptr2) {
        super();
        
        this.ptr = ptr2;
        
        setBorderPainted(true);
        
        starting_position_button = new JButton();
        starting_position_button.setIcon(getIcon("/fractalzoomer/icons/starting_position.png"));
        starting_position_button.setFocusable(false);
        starting_position_button.setToolTipText("Resets the fractal to the default position.");

        starting_position_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.startingPosition();

            }
        });

        add(starting_position_button);
        
        go_to_button = new JButton();
        go_to_button.setIcon(getIcon("/fractalzoomer/icons/go_to.png"));
        go_to_button.setFocusable(false);
        go_to_button.setToolTipText("Sets the center and size of the fractal, or the julia seed.");

        go_to_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.goTo();

            }
        });

        add(go_to_button);
             
        zoom_in_button = new JButton();
        zoom_in_button.setIcon(getIcon("/fractalzoomer/icons/zoom_in.png"));
        zoom_in_button.setFocusable(false);
        zoom_in_button.setToolTipText("Zooms in with a fixed rate to the current center.");

        zoom_in_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.zoomIn();

            }
        });

        add(zoom_in_button);

        zoom_out_button = new JButton();
        zoom_out_button.setIcon(getIcon("/fractalzoomer/icons/zoom_out.png"));
        zoom_out_button.setFocusable(false);
        zoom_out_button.setToolTipText("Zooms out with a fixed rate to the current center.");

        zoom_out_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.zoomOut();

            }
        });

        add(zoom_out_button);
        
        addSeparator();

        save_image_button = new JButton();
        save_image_button.setIcon(getIcon("/fractalzoomer/icons/save_image.png"));
        save_image_button.setFocusable(false);
        save_image_button.setToolTipText("Saves a png image.");

        save_image_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.saveImage();

            }
        });

        add(save_image_button);
        
        save_image_and_settings_button = new JButton();
        save_image_and_settings_button.setIcon(getIcon("/fractalzoomer/icons/save_image_settings.png"));
        save_image_and_settings_button.setFocusable(false);
        save_image_and_settings_button.setToolTipText("Saves the current settings and a png image.");

        save_image_and_settings_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.saveSettingsAndImage();

            }
        });

        add(save_image_and_settings_button);

        addSeparator();

        custom_palette_button_out = new JButton();
        custom_palette_button_out.setIcon(getIcon("/fractalzoomer/icons/palette_outcoloring.png"));
        custom_palette_button_out.setFocusable(false);
        custom_palette_button_out.setToolTipText("Loads the custom palette editor for the out-coloring palette.");

        custom_palette_button_out.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ptr.openCustomPaletteEditor(true);
                
            }
        });

        add(custom_palette_button_out);
        
        custom_palette_button_in = new JButton();
        custom_palette_button_in.setIcon(getIcon("/fractalzoomer/icons/palette_incoloring.png"));
        custom_palette_button_in.setFocusable(false);
        custom_palette_button_in.setToolTipText("Loads the custom palette editor for the in-coloring palette.");

        custom_palette_button_in.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                ptr.openCustomPaletteEditor(false);
                
            }
        });

        add(custom_palette_button_in);

        random_palette_button = new JButton();
        random_palette_button.setIcon(getIcon("/fractalzoomer/icons/palette_random.png"));
        random_palette_button.setFocusable(false);
        random_palette_button.setToolTipText("Randomizes the palette.");

        random_palette_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.randomPalette();

            }
        });

        add(random_palette_button);

        addSeparator();

        iterations_button = new JButton();
        iterations_button.setIcon(getIcon("/fractalzoomer/icons/iterations.png"));
        iterations_button.setFocusable(false);
        iterations_button.setToolTipText("Sets the maximum number of iterations.");

        iterations_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setIterations();

            }
        });

        add(iterations_button);

        rotation_button = new JButton();
        rotation_button.setIcon(getIcon("/fractalzoomer/icons/rotate.png"));
        rotation_button.setFocusable(false);
        rotation_button.setToolTipText("Sets the rotation in degrees.");

        rotation_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setRotation();

            }
        });

        add(rotation_button);

        addSeparator();

        filters_options_button = new JButton();
        filters_options_button.setIcon(getIcon("/fractalzoomer/icons/filter_options.png"));
        filters_options_button.setFocusable(false);
        filters_options_button.setToolTipText("Sets the options of the image filters.");

        filters_options_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.filtersOptions();

            }
        });

        add(filters_options_button);

        addSeparator();

        orbit_button = new JButton();
        orbit_button.setIcon(getIcon("/fractalzoomer/icons/orbit.png"));
        orbit_button.setToolTipText("Displays the orbit of a complex number.");
        orbit_button.setFocusable(false);

        orbit_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setOrbitButton();

            }
        });

        add(orbit_button);

        julia_button = new JButton();
        julia_button.setIcon(getIcon("/fractalzoomer/icons/julia.png"));
        julia_button.setToolTipText("Generates an image based on a seed (chosen pixel).");
        julia_button.setFocusable(false);

        julia_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setJuliaButton();

            }
        });

        add(julia_button);

        julia_map_button = new JButton();
        julia_map_button.setIcon(getIcon("/fractalzoomer/icons/julia_map.png"));
        julia_map_button.setToolTipText("Creates an image of julia sets.");
        julia_map_button.setFocusable(false);

        julia_map_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setJuliaMapButton();
            }
        });

        add(julia_map_button);

        d3_button = new JButton();
        d3_button.setIcon(getIcon("/fractalzoomer/icons/3d.png"));
        d3_button.setToolTipText("Creates a 3D version of the image.");
        d3_button.setFocusable(false);

        d3_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.set3DButton();

            }
        });

        add(d3_button);

        polar_projection_button = new JButton();
        polar_projection_button.setIcon(getIcon("/fractalzoomer/icons/polar_projection.png"));
        polar_projection_button.setToolTipText("Projects the image into polar coordinates.");
        polar_projection_button.setFocusable(false);

        polar_projection_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

               ptr.setPolarProjectionButton();

            }
        });

        add(polar_projection_button);

        domain_coloring_button = new JButton();
        domain_coloring_button.setIcon(getIcon("/fractalzoomer/icons/domain_coloring.png"));
        domain_coloring_button.setToolTipText("Creates a complex plane domain coloring visualization.");
        domain_coloring_button.setFocusable(false);

        domain_coloring_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setDomainColoringButton();

            }
        });

        add(domain_coloring_button);

        color_cycling_button = new JButton();
        color_cycling_button.setIcon(getIcon("/fractalzoomer/icons/color_cycling.png"));
        color_cycling_button.setToolTipText("Animates the image, cycling through the palette.");
        color_cycling_button.setFocusable(false);

        color_cycling_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorCyclingButton();

            }
        });

        add(color_cycling_button);

        addSeparator();

        help_button = new JButton();
        help_button.setIcon(getIcon("/fractalzoomer/icons/help.png"));
        help_button.setFocusable(false);
        help_button.setToolTipText("Loads the help file.");

        help_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.showCHMHelpFile();

            }
        });

        add(help_button);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JButton getStartingPositionButton() {
        
        return starting_position_button;
        
    }
    
    public JButton getZoomInButton() {
        
        return zoom_in_button;
        
    }
    
    public JButton getZoomOutButton() {
        
        return zoom_out_button;
        
    }
    
    public JButton getJuliaButton() {
        
        return julia_button;
        
    }
    
    public JButton getJuliaMapButton() {
        
        return julia_map_button;
        
    }
    
    public JButton getOrbitButton() {
        
        return orbit_button;
        
    }
    
    public JButton getPolarProjectionButton() {
        
        return polar_projection_button;
        
    }
    
    public JButton getColorCyclingButton() {
        
        return color_cycling_button;
        
    }
    
    public JButton getDomainColoringButton() {
        
        return domain_coloring_button;
        
    }
    
    public JButton get3DButton() {
        
        return d3_button;
        
    }
    
    public JButton getOutCustomPaletteButton() {
        
        return custom_palette_button_out;
        
    }
    
    public JButton getInCustomPaletteButton() {
        
        return custom_palette_button_in;
        
    }
    
    public JButton getRandomPaletteButton() {
        
        return random_palette_button;
        
    }
    
    public JButton getIterationsButton() {
        
        return iterations_button;
        
    }
    
    public JButton getRotationButton() {
        
        return rotation_button;
        
    }
    
    public JButton getSaveImageButton() {
        
        return save_image_button;
        
    }
    
    public JButton getSaveImageAndSettignsButton() {
        
        return save_image_and_settings_button;
        
    }
    
    public JButton getFiltersOptionsButton() {
        
        return filters_options_button;
        
    }
    
    public JButton getGoTo() {
        
        return go_to_button;
        
    }
}
