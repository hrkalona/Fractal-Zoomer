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
package fractalzoomer.gui;

import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.PaletteSettings;
import fractalzoomer.main.app_settings.Settings;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author kaloch
 */
public class ColorsMenu extends JMenu {
	private static final long serialVersionUID = -6910654944774874305L;
	private MainWindow ptr;
    private JMenuItem fract_color;
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
    
    public ColorsMenu(MainWindow ptr2, String name, PaletteSettings ps, PaletteSettings ps2, boolean smoothing, int out_coloring_algorithm, int in_coloring_algorithm, int color_blending, int temp_color_cycling_location, int temp_color_cycling_location2) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/colors_menu.png"));
        
        fract_color = new JMenuItem("Fractal Colors", getIcon("/fractalzoomer/icons/color.png"));
        
        outcolor_palette_menu = new OutColoringPaletteMenu(ptr, "Out Coloring Palette", ps, smoothing, temp_color_cycling_location);
        incolor_palette_menu = new InColoringPaletteMenu(ptr, "In Coloring Palette", ps2, smoothing, temp_color_cycling_location2);
        
        

        random_palette = new JMenuItem("Random Palette", getIcon("/fractalzoomer/icons/palette_random.png"));
        blend_palette_opt = new JMenuItem("Palette/Gradient Merging", getIcon("/fractalzoomer/icons/palette_blending.png"));

        out_coloring_mode_menu = new OutColoringModesMenu(ptr, "Out Coloring Mode", out_coloring_algorithm);
        
        in_coloring_mode_menu = new InColoringModesMenu(ptr, "In Coloring Mode", in_coloring_algorithm);
        
        direct_color_opt = new JCheckBoxMenuItem("Direct Color");
               
        processing = new ProcessingMenu(ptr, "Processing");      
        
        color_blending_menu = new ColorBlendingMenu(ptr, "Blending", color_blending);
        
        gradient = new JMenuItem("Gradient", getIcon("/fractalzoomer/icons/gradient.png"));
     
        fract_color.setToolTipText("Sets the colors for maximum iterations, distance estimation and some color algorithms.");
        random_palette.setToolTipText("Randomizes the palette.");        
        gradient.setToolTipText("Sets the gradient for color blending.");
        direct_color_opt.setToolTipText("Enables the use of direct color, via the use of user code, in user out-coloring and in-coloring modes.");
        blend_palette_opt.setToolTipText("The coloring algorithm is mapped through the gradient and then it is merged with the palette.");        
                
        fract_color.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
        random_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.SHIFT_MASK));
        gradient.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, 0));
        direct_color_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_7, 0));
        blend_palette_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0));

        fract_color.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFractalColor();

            }
        });
        
        direct_color_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setDirectColor();

            }
        });
        
        random_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.randomPalette();

            }
        });
        
        blend_palette_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setPaletteGradientMerging();

            }
        });
        
        gradient.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setGradient();

            }
        });

        direct_color_opt.setSelected(ThreadDraw.USE_DIRECT_COLOR);
        
        add(out_coloring_mode_menu);
        add(in_coloring_mode_menu);
        add(direct_color_opt);
        addSeparator();
        add(processing);
        addSeparator();
        add(color_blending_menu); 
        add(gradient);
        addSeparator();                
        add(fract_color);
        addSeparator();
        add(outcolor_palette_menu);
        add(incolor_palette_menu);
        add(blend_palette_opt);
        add(random_palette);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

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
    
    public void updateIcons(Settings s) {

        if(s.pbs.palette_gradient_merge) {
            blend_palette_opt.setIcon(getIcon("/fractalzoomer/icons/palette_blending_enabled.png"));
        }
        else {
            blend_palette_opt.setIcon(getIcon("/fractalzoomer/icons/palette_blending.png"));
        }
  
    }
}
