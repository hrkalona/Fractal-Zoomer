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

import fractalzoomer.core.TaskDraw;
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
public class ColorsMenu extends MyMenu {
	private static final long serialVersionUID = -6910654944774874305L;
	private MainWindow ptr;
    private JMenuItem fract_color;
    private JMenuItem contour_factor;

    private JMenuItem color_space_params;
    private JMenuItem gradient;
    private JMenuItem random_palette;
    private JMenuItem blend_palette_opt;
    private JCheckBoxMenuItem direct_color_opt;
    private ProcessingMenu processing;
    private OutColoringModesMenu out_coloring_mode_menu;
    private InColoringModesMenu in_coloring_mode_menu;
    private ColorBlendingMenu color_blending_menu;
    private OutColoringPaletteMenu outcolor_palette_menu;
    private InColoringPaletteMenu incolor_palette_menu;
    private JMenuItem out_true_color_opt;
    private JMenuItem in_true_color_opt;
    
    public ColorsMenu(MainWindow ptr2, String name, PaletteSettings ps, PaletteSettings ps2, boolean smoothing, int out_coloring_algorithm, int in_coloring_algorithm, int color_blending, boolean color_blending_reverse_order, int temp_color_cycling_location, int temp_color_cycling_location2) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("colors_menu.png"));
        
        fract_color = new MyMenuItem("Fractal Colors", MainWindow.getIcon("color.png"));
        
        outcolor_palette_menu = new OutColoringPaletteMenu(ptr, "Out Coloring Palette", ps, smoothing, temp_color_cycling_location);
        incolor_palette_menu = new InColoringPaletteMenu(ptr, "In Coloring Palette", ps2, smoothing, temp_color_cycling_location2);
        
        

        random_palette = new MyMenuItem("Random Palette", MainWindow.getIcon("palette_random.png"));
        blend_palette_opt = new MyMenuItem("Palette/Gradient Merging", MainWindow.getIcon("palette_blending.png"));

        out_coloring_mode_menu = new OutColoringModesMenu(ptr, "Out Coloring Mode", out_coloring_algorithm);
        
        in_coloring_mode_menu = new InColoringModesMenu(ptr, "In Coloring Mode", in_coloring_algorithm);
        
        direct_color_opt = new MyCheckBoxMenuItem("Direct Color");
        
        out_true_color_opt = new MyMenuItem("Out True Coloring Mode", MainWindow.getIcon("true_color_out.png"));
        in_true_color_opt = new MyMenuItem("In True Coloring Mode", MainWindow.getIcon("true_color_in.png"));
               
        processing = new ProcessingMenu(ptr, "Processing");      
        
        color_blending_menu = new ColorBlendingMenu(ptr, "Blending", color_blending, color_blending_reverse_order);
        
        gradient = new MyMenuItem("Gradient", MainWindow.getIcon("gradient.png"));

        contour_factor = new MyMenuItem("Contour Factor", MainWindow.getIcon("contour.png"));
        color_space_params = new MyMenuItem("Hue Generated Palettes", MainWindow.getIcon("color_spaces_params.png"));
     
        fract_color.setToolTipText("Sets the colors for maximum iterations, distance estimation and some color algorithms.");
        random_palette.setToolTipText("Randomizes the palette.");        
        gradient.setToolTipText("Sets the gradient for color blending.");
        direct_color_opt.setToolTipText("Enables the use of direct color, via the use of user code, in user out-coloring and in-coloring modes.");
        blend_palette_opt.setToolTipText("The coloring algorithm is mapped through the gradient and then it is merged with the palette.");        
        out_true_color_opt.setToolTipText("Overrides the palette coloring for out coloring mode, and uses a direct color space coloring."); 
        in_true_color_opt.setToolTipText("Overrides the palette coloring for in coloring mode, and uses a direct color space coloring.");
        contour_factor.setToolTipText("Changes the contour factor in all color algorithms that use it.");
        color_space_params.setToolTipText("Sets the rest of the color space parameters for every Hue generated palette.");
                
        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        gradient.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0));
        direct_color_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0));
        blend_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0));
        out_true_color_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.SHIFT_MASK));
        in_true_color_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
        contour_factor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.SHIFT_MASK));
        color_space_params.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK));

        fract_color.addActionListener(e -> ptr.setFractalColor());
        
        direct_color_opt.addActionListener(e -> ptr.setDirectColor());
        
        out_true_color_opt.addActionListener(e -> ptr.setOutTrueColor());
        
        in_true_color_opt.addActionListener(e -> ptr.setInTrueColor());
        
        random_palette.addActionListener(e -> ptr.randomPalette());
        
        blend_palette_opt.addActionListener(e -> ptr.setPaletteGradientMerging());
        
        gradient.addActionListener(e -> ptr.setGradient());

        contour_factor.addActionListener(e -> ptr.setContourFactor());

        color_space_params.addActionListener(e -> ptr.setHueGeneratedPaletteParams());

        direct_color_opt.setSelected(TaskDraw.USE_DIRECT_COLOR);
        
        add(out_coloring_mode_menu);
        add(in_coloring_mode_menu);
        add(out_true_color_opt);
        add(in_true_color_opt);
        add(direct_color_opt);
        addSeparator();
        add(processing);
        addSeparator();
        add(color_blending_menu); 
        add(gradient);
        addSeparator();                
        add(fract_color);
        addSeparator();
        add(contour_factor);
        addSeparator();
        add(color_space_params);
        addSeparator();
        add(outcolor_palette_menu);
        add(incolor_palette_menu);
        add(blend_palette_opt);
        add(random_palette);
        
    }
    
    public JRadioButtonMenuItem[] getOutColoringPalette() {
        
        return outcolor_palette_menu.getPalette();
        
    }
    
    public JRadioButtonMenuItem[] getInColoringPalette() {
        
        return incolor_palette_menu.getPalette();
        
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
    
    public JMenuItem getLight() {
        
        return processing.getLight();
        
    }

    public JMenuItem getSlopes() {

        return processing.getSlopes();

    }

    public JMenuItem getColorSpaceParams() {

        return color_space_params;

    }
    
    public JMenuItem getHistogramColoring() {
        
        return processing.getHistogramColoring();
        
    }
    
    public JMenuItem getFakeDistanceEstimation() {
        
        return processing.getFakeDistanceEstimation();
        
    }

    public JMenuItem getNumericalDistanceEstimator() {

        return processing.getNumericalDistanceEstimator();

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
    
    public JRadioButtonMenuItem[] getOutColoringTranferFunctions() {
        
        return outcolor_palette_menu.getOutColoringTranferFunctions();
        
    }
    
    public JRadioButtonMenuItem[] getInColoringTranferFunctions() {
        
        return incolor_palette_menu.getInColoringTranferFunctions();
        
    }
    
    public OutColoringModesMenu getOutColoringMenu() {
        
        return out_coloring_mode_menu;
        
    }
    
    public InColoringModesMenu getInColoringMenu() {
        
        return in_coloring_mode_menu;
        
    }
     
    public JMenuItem getRandomPalette() {
        
        return random_palette;
        
    }
   
    
    public OutColoringPaletteMenu getOutColoringPaletteMenu() {
        
        return outcolor_palette_menu;
        
    }
  
    
    public InColoringPaletteMenu getInColoringPaletteMenu() {
        
        return incolor_palette_menu;
        
    }
    
    public ProcessingMenu getProcessing() {
        
        return processing;
        
    }
    
    public ColorBlendingMenu getColorBlending() {
        
        return color_blending_menu;
        
    }
    
    public JMenuItem getGradient() {
        
        return gradient;
                
    }
    
    public JMenuItem getOrbitTraps() {
        
        return processing.getOrbitTraps();
        
    }
    
    public JCheckBoxMenuItem getDirectColor() {
        
        return direct_color_opt;
        
    }
    
    public JMenuItem getContourColoring() {
        
        return processing.getContourColoring();
        
    }
    
    public JMenuItem getProcessingOrder() {
        
        return processing.getProcessingOrder();
        
    }
    
    public JMenuItem getPaletteGradientMerging() {
        return blend_palette_opt;
    }
    
    public JCheckBoxMenuItem getUsePaletteForInColoring() {
        
        return incolor_palette_menu.getUsePaletteForInColoring();
        
    }
    
    public JMenuItem getStatisticsColoring() {
        
        return processing.getStatisticsColoring();
        
    }
    
    public JMenuItem getOutTrueColoring() {
        return out_true_color_opt;
    }
    
    public JMenuItem getInTrueColoring() {
        return in_true_color_opt;
    }

    public JMenuItem getContourFactor() {

        return contour_factor;

    }
    
    public void updateIcons(Settings s) {

        if(s.pbs.palette_gradient_merge) {
            blend_palette_opt.setIcon(MainWindow.getIcon("palette_blending_enabled.png"));
        }
        else {
            blend_palette_opt.setIcon(MainWindow.getIcon("palette_blending.png"));
        }
        
        if(s.fns.tcs.trueColorOut) {
            out_true_color_opt.setIcon(MainWindow.getIcon("true_color_out_enabled.png"));
        }
        else {
            out_true_color_opt.setIcon(MainWindow.getIcon("true_color_out.png"));
        }
        
        if(s.fns.tcs.trueColorIn) {
            in_true_color_opt.setIcon(MainWindow.getIcon("true_color_in_enabled.png"));
        }
        else {
            in_true_color_opt.setIcon(MainWindow.getIcon("true_color_in.png"));
        }
  
    }
}
