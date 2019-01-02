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

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.PaletteSettings;
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
public class InColoringPaletteMenu extends JMenu {
    private static final long serialVersionUID = -6910423535L;
    private MainWindow ptr;
    private PaletteMenu palette_menu;
    private JMenu roll_palette_menu;
    private JMenuItem roll_palette;
    private JMenuItem increase_roll_palette;
    private JMenuItem decrease_roll_palette;
    private JMenuItem color_intensity_opt;
    private ColorTransferMenu color_transfer_menu;
    private JCheckBoxMenuItem usePaletteForInColoring_opt;
    
    public InColoringPaletteMenu(MainWindow ptr2, String name, PaletteSettings ps, boolean smoothing, int temp_color_cycling_location) {
        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/palette_incoloring.png"));
        
        palette_menu = new PaletteMenu(ptr, "Palette", ps.color_choice, smoothing, ps.custom_palette, ps.color_interpolation, ps.color_space, ps.reversed_palette, ps.color_cycling_location, ps.scale_factor_palette_val, ps.processing_alg, false, temp_color_cycling_location);
        
        roll_palette_menu = new JMenu("Palette Shifting");
        roll_palette_menu.setIcon(getIcon("/fractalzoomer/icons/shift_palette.png"));
        
        roll_palette = new JMenuItem("Shift Palette", getIcon("/fractalzoomer/icons/shift_palette.png"));

        increase_roll_palette = new JMenuItem("Shift Palette Forward", getIcon("/fractalzoomer/icons/plus.png"));

        decrease_roll_palette = new JMenuItem("Shift Palette Backward", getIcon("/fractalzoomer/icons/minus.png"));
        
        color_transfer_menu = new ColorTransferMenu(ptr, "Transfer Functions", ps.transfer_function, false);
        
        color_intensity_opt = new JMenuItem("Color Intensity", getIcon("/fractalzoomer/icons/color_intensity.png"));
        
        usePaletteForInColoring_opt = new JCheckBoxMenuItem("Use In-Coloring Palette");
        
        roll_palette.setToolTipText("Shifts the chosen palette by a number.");
        increase_roll_palette.setToolTipText("Shifts the chosen palette forward by one.");
        decrease_roll_palette.setToolTipText("Shifts the chosen palette backward by one.");
        
        color_intensity_opt.setToolTipText("Changes the color intensity of the in-coloring palette.");
        usePaletteForInColoring_opt.setToolTipText("Enables the use of a secondary palette for in-coloring.");
        
        roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        increase_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, ActionEvent.ALT_MASK));
        decrease_roll_palette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, ActionEvent.ALT_MASK));
        color_intensity_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.SHIFT_MASK));
        usePaletteForInColoring_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.ALT_MASK));
        
        roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPalette(false);

            }
        });

        increase_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPaletteForward(false);

            }
        });

        decrease_roll_palette.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.shiftPaletteBackward(false);

            }
        });
        
        color_intensity_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorIntensity(false);

            }
        });
        
        usePaletteForInColoring_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setUsePaletteForInColoring();

            }
        });
        
        roll_palette_menu.add(roll_palette);
        roll_palette_menu.add(increase_roll_palette);
        roll_palette_menu.add(decrease_roll_palette);
        
        add(usePaletteForInColoring_opt);
        addSeparator();
        add(palette_menu);
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
    
    public JMenu getRollPaletteMenu() {
        
        return roll_palette_menu;
        
    }
    
    public PaletteMenu getPaletteMenu() {
        
        return palette_menu;
        
    }
    
    public JRadioButtonMenuItem[] getInColoringTranferFunctions() {
        
        return color_transfer_menu.getTranferFunctions();
        
    }
    
    public JCheckBoxMenuItem getUsePaletteForInColoring() {
        
        return usePaletteForInColoring_opt;
        
    }
}
