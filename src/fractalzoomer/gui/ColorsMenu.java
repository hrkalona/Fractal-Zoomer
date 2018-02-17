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
    private ProcessingMenu processing;
    private OutColoringModesMenu out_coloring_mode_menu;
    private InColoringModesMenu in_coloring_mode_menu;
    private ColorTransferMenu color_transfer_menu;
    private ColorBlendingMenu color_blending_menu;
    
    public ColorsMenu(MainWindow ptr2, String name, int color_choice, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg, int out_coloring_algorithm, int in_coloring_algorithm, int transfer_function, int color_blending) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/colors_menu.png"));
        
        fract_color = new JMenuItem("Fractal Colors", getIcon("/fractalzoomer/icons/color.png"));

        palette_menu = new PaletteMenu(ptr, "Palette", color_choice, smoothing, custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

        color_intensity_opt = new JMenuItem("Color Intensity", getIcon("/fractalzoomer/icons/color_intensity.png"));

        random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette_random.png"));

        out_coloring_mode_menu = new OutColoringModesMenu(ptr, "Out Coloring Mode", out_coloring_algorithm);
        
        in_coloring_mode_menu = new InColoringModesMenu(ptr, "In Coloring Mode", in_coloring_algorithm);
        
        color_transfer_menu = new ColorTransferMenu(ptr, "Transfer Functions", transfer_function);
        
        processing = new ProcessingMenu(ptr, "Processing");      
        
        color_blending_menu = new ColorBlendingMenu(ptr, "Blending", color_blending);
        
        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(getIcon("/fractalzoomer/icons/shift_palette.png"));

        roll_palette = new JMenuItem("Shift Palette", getIcon("/fractalzoomer/icons/shift_palette.png"));

        increase_roll_palette = new JMenuItem("Shift Palette Forward", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_roll_palette = new JMenuItem("Shift Palette Backward", getIcon("/fractalzoomer/icons/minus.png"));
     
        color_intensity_opt.setToolTipText("Changes the color intensity of the palette.");
        
        fract_color.setToolTipText("Sets the colors for maximum iterations, distance estimation and some color algorithms.");
        random_palette.setToolTipText("Randomizes the palette.");
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");
     
        color_intensity_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));

        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.SHIFT_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, ActionEvent.SHIFT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.SHIFT_MASK));

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
        add(processing);
        addSeparator();                
        add(color_blending_menu); 
        addSeparator();
        add(fract_color);
        addSeparator();
        add(palette_menu);
        add(random_palette);
        add(roll_palette_menu);
        addSeparator();
        add(color_transfer_menu);    
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
        
        return processing.getEntropyColoring();
        
    }
    
    public JMenuItem getOffsetColoring() {
        
        return processing.getOffsetColoring();
        
    }
    
    public JMenuItem getGreyScaleColoring() {
        
        return processing.getGreyScaleColoring();
        
    }
    
    public JMenuItem getRainbowPalette() {
        
        return processing.getRainbowPalette();
        
    }
    
    public JMenuItem getBumpMap() {
        
        return processing.getBumpMap();
        
    }
    
    public JMenuItem getFakeDistanceEstimation() {
        
        return processing.getFakeDistanceEstimation();
        
    }
    
    public JMenuItem getDistanceEstimation() {
        
        return processing.getDistanceEstimation();
        
    }
    
    public JMenuItem getSmoothing() {
        
        return processing.getSmoothing();
        
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
    
    public JRadioButtonMenuItem[] getBlendingModes() {
        
        return color_blending_menu.getBlendingModes();
        
    }
    
    public JRadioButtonMenuItem[] getTranferFunctions() {
        
        return color_transfer_menu.getTranferFunctions();
        
    }
    
    public OutColoringModesMenu getOutColoringMenu() {
        
        return out_coloring_mode_menu;
        
    }
    
    public InColoringModesMenu getInColoringMenu() {
        
        return in_coloring_mode_menu;
        
    }
    
    public ColorTransferMenu getColorTransferMenu() {
        
        return color_transfer_menu;
        
    }
    
    public JMenuItem getRandomPalette() {
        
        return random_palette;
        
    }
    
    public JMenu getRollPaletteMenu() {
        
        return roll_palette_menu;
        
    }
    
    public PaletteMenu getPaletteMenu() {
        
        return palette_menu;
        
    }
    
    public JMenuItem getColorIntensity() {
        
        return color_intensity_opt;
        
    } 
    
    public ProcessingMenu getProcessing() {
        
        return processing;
        
    }
    
    public ColorBlendingMenu getColorBlending() {
        
        return color_blending_menu;
        
    }
}
