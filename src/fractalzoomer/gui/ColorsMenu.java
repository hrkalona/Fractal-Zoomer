/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author kaloch
 */
public class ColorsMenu extends JMenu {
    private MainWindow ptr;
    private PaletteMenu palette_menu;
    private JMenu roll_palette_menu;
    private JMenuItem fract_color;
    private JMenuItem random_palette;
    private JMenuItem roll_palette;
    private JMenuItem increase_roll_palette;
    private JMenuItem decrease_roll_palette;
    private JMenuItem color_intensity_opt;
    private JMenuItem bump_map_opt;
    private JMenuItem rainbow_palette_opt;
    private JMenuItem fake_de_opt;
    private JMenuItem entropy_coloring_opt;
    private JMenuItem offset_coloring_opt;
    private JMenuItem greyscale_coloring_opt;
    private JMenuItem smoothing_opt;
    private JMenuItem exterior_de_opt;
    private OutColoringModesMenu out_coloring_mode_menu;
    private InColoringModesMenu in_coloring_mode_menu;   
    
    public ColorsMenu(MainWindow ptr2, String name, int color_choice, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg, int out_coloring_algorithm, int in_coloring_algorithm) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/colors_menu.png"));
        
        fract_color = new JMenuItem("Fractal Colors", getIcon("/fractalzoomer/icons/color.png"));

        palette_menu = new PaletteMenu(ptr, "Palette", color_choice, smoothing, custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

        color_intensity_opt = new JMenuItem("Color Intensity", getIcon("/fractalzoomer/icons/color_intensity.png"));

        random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette_random.png"));

        out_coloring_mode_menu = new OutColoringModesMenu(ptr, "Out Coloring Mode", out_coloring_algorithm);
        
        in_coloring_mode_menu = new InColoringModesMenu(ptr, "In Coloring Mode", in_coloring_algorithm);
        
        smoothing_opt = new JMenuItem("Smoothing", getIcon("/fractalzoomer/icons/smoothing.png"));
        exterior_de_opt = new JMenuItem("Distance Estimation", getIcon("/fractalzoomer/icons/distance_estimation.png"));
        bump_map_opt = new JMenuItem("Bump Mapping", getIcon("/fractalzoomer/icons/bump_map.png"));
        fake_de_opt = new JMenuItem("Fake Distance Estimation", getIcon("/fractalzoomer/icons/fake_distance_estimation.png"));
        entropy_coloring_opt = new JMenuItem("Entropy Coloring", getIcon("/fractalzoomer/icons/entropy_coloring.png"));
        offset_coloring_opt = new JMenuItem("Offset Coloring", getIcon("/fractalzoomer/icons/offset_coloring.png"));
        greyscale_coloring_opt = new JMenuItem("Greyscale Coloring", getIcon("/fractalzoomer/icons/greyscale_coloring.png"));
        rainbow_palette_opt = new JMenuItem("Rainbow Palette", getIcon("/fractalzoomer/icons/rainbow_palette.png"));
        
                
        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(getIcon("/fractalzoomer/icons/shift_palette.png"));

        roll_palette = new JMenuItem("Shift Palette", getIcon("/fractalzoomer/icons/shift_palette.png"));

        increase_roll_palette = new JMenuItem("Shift Palette Forward", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_roll_palette = new JMenuItem("Shift Palette Backward", getIcon("/fractalzoomer/icons/minus.png"));
        
        smoothing_opt.setToolTipText("Smooths the image's color transitions.");
        exterior_de_opt.setToolTipText("<html>Sets some points near the boundary of<br>the set to the maximum iterations value.</html>");
        bump_map_opt.setToolTipText("Emulates a light source to create a pseudo 3d image.");
        fake_de_opt.setToolTipText("Emulates the effect of distance estimation.");
        entropy_coloring_opt.setToolTipText("Calculates the entropy of neaby points to create a coloring effect.");
        offset_coloring_opt.setToolTipText("Adds a palette offset, into areas with smooth iteration gradients.");
        greyscale_coloring_opt.setToolTipText("Converts the areas with smooth iteration gradients, to greyscale.");
        rainbow_palette_opt.setToolTipText("Creates a pseudo 3d image, by applying the palette colors as a rainbow.");
        color_intensity_opt.setToolTipText("Changes the color intensity of the palette.");
        
        fract_color.setToolTipText("Sets the colors for maximum iterations, distance estimation and some color algorithms.");
        random_palette.setToolTipText("Randomizes the palette.");
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");
        
        smoothing_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        exterior_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0));
        bump_map_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
        fake_de_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0));
        entropy_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, 0));
        offset_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, 0));
        greyscale_coloring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, 0));
        rainbow_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        color_intensity_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.SHIFT_MASK));
        
        smoothing_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setSmoothing();
            }
        });

        exterior_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setExteriorDistanceEstimation();
            }
        });

        bump_map_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setBumpMap();
            }
        });

        rainbow_palette_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setRainbowPalette();
            }
        });

        fake_de_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setFakeDistanceEstimation();
            }
        });
        
        greyscale_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setGreyScaleColoring();
            }
        });

        entropy_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setEntropyColoring();
            }
        });

        offset_coloring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ptr.setOffsetColoring();
            }
        });
        
        fract_color.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFractalColor();

            }
        });
        
        random_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.randomPalette();

            }
        });

        roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPalette();

            }
        });

        increase_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPaletteForward();

            }
        });

        decrease_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPaletteBackward();

            }
        });
        
        color_intensity_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorIntensity();

            }
        });
        
        roll_palette_menu.add(roll_palette);
        roll_palette_menu.add(increase_roll_palette);
        roll_palette_menu.add(decrease_roll_palette);
        
        add(out_coloring_mode_menu);
        add(in_coloring_mode_menu);
        addSeparator();
        add(smoothing_opt);
        add(exterior_de_opt);
        add(fake_de_opt);
        add(entropy_coloring_opt);
        add(offset_coloring_opt);
        add(rainbow_palette_opt);
        add(greyscale_coloring_opt);
        add(bump_map_opt);
        addSeparator();
        add(fract_color);
        addSeparator();
        add(palette_menu);
        add(random_palette);
        add(roll_palette_menu);
        addSeparator();
        add(color_intensity_opt);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JRadioButtonMenuItem[] getPalette() {
        
        return palette_menu.getPalette();
        
    }
    
    public JMenuItem getEntropyColoring() {
        
        return entropy_coloring_opt;
        
    }
    
    public JMenuItem getOffsetColoring() {
        
        return offset_coloring_opt;
        
    }
    
    public JMenuItem getGreyScaleColoring() {
        
        return greyscale_coloring_opt;
        
    }
    
    public JMenuItem getRainbowPalette() {
        
        return rainbow_palette_opt;
        
    }
    
    public JMenuItem getBumpMap() {
        
        return bump_map_opt;
        
    }
    
    public JMenuItem getFakeDistanceEstimation() {
        
        return fake_de_opt;
        
    }
    
    public JMenuItem getDistanceEstimation() {
        
        return exterior_de_opt;
        
    }
    
    public JMenuItem getFractalColor() {
        
        return fract_color;
        
    }
    
    public JRadioButtonMenuItem[] getOutColoringModes() {
        
        return out_coloring_mode_menu.getOutColoringModes();
        
    }
    
    public JRadioButtonMenuItem[] getInColoringModes() {
        
        return in_coloring_mode_menu.getInColoringModes();
        
    }
    
    public OutColoringModesMenu getOutColoringMenu() {
        
        return out_coloring_mode_menu;
        
    }
    
    public InColoringModesMenu getInColoringMenu() {
        
        return in_coloring_mode_menu;
        
    }
    
}
