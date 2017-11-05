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
import fractalzoomer.palettes.CustomPalette;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author hrkalona2
 */
public class PaletteMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] palette;
    private int i;
    
    public PaletteMenu(MainWindow ptr2, String name, int color_choice, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/palette.png"));
        
        String[] coloring_option = new String[19];
        coloring_option[0] = "Default";
        coloring_option[1] = "Spectrum";
        coloring_option[2] = "Alternative";
        coloring_option[3] = "Alternative 2";
        coloring_option[4] = "Alternative 3";
        coloring_option[5] = "Alternative 4";
        coloring_option[6] = "Alternative 5";
        coloring_option[7] = "Alternative 6";
        coloring_option[8] = "Alternative 7";
        coloring_option[9] = "Alternative 8";
        coloring_option[10] = "Alternative 9";
        coloring_option[11] = "Dusk";
        coloring_option[12] = "Gray Scale";
        coloring_option[13] = "Earth Sky";
        coloring_option[14] = "Hot Cold";
        coloring_option[15] = "Hot Cold 2";
        coloring_option[16] = "Fire";
        coloring_option[17] = "Jet";
        coloring_option[18] = "Custom Palette";
        
        palette = new JRadioButtonMenuItem[coloring_option.length];

        ButtonGroup palettes_group = new ButtonGroup();

        for (i = 0; i < palette.length - 1; i++) {

            Color[] c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], MainWindow.INTERPOLATION_LINEAR, MainWindow.COLOR_SPACE_RGB, false, color_cycling_location, 0, MainWindow.PROCESSING_NONE);

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (smoothing) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
                } else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            palette[i] = new JRadioButtonMenuItem(coloring_option[i], new ImageIcon(palette_preview));

            palette[i].addActionListener(new ActionListener() {

                int temp = i;

                public void actionPerformed(ActionEvent e) {

                    ptr.setPalette(temp);

                }
            });
            add(palette[i]);
            palettes_group.add(palette[i]);
        }

        addSeparator();
        
        Color[] c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);

        BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();
        for (int j = 0; j < c.length; j++) {
            if (smoothing) {
                GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
            } else {
                g.setColor(c[j]);
                g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
            }
        }
        
        palette[i] = new JRadioButtonMenuItem(coloring_option[i], new ImageIcon(palette_preview));

        palette[i].addActionListener(new ActionListener() {

            int temp = i;

            public void actionPerformed(ActionEvent e) {

                ptr.openCustomPaletteEditor(temp);

            }
        });
        add(palette[i]);
        palettes_group.add(palette[i]);
        palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));

        palette[color_choice].setSelected(true);

        palette[0].setToolTipText("The default palette.");
        palette[1].setToolTipText("A palette based on color spectrum based.");
        palette[2].setToolTipText("A palette based on software, Fractal Extreme.");
        palette[3].setToolTipText("An alternative palette.");
        palette[4].setToolTipText("An alternative palette.");
        palette[5].setToolTipText("An alternative palette.");
        palette[6].setToolTipText("An alternative palette.");
        palette[7].setToolTipText("An alternative palette.");
        palette[8].setToolTipText("An alternative palette.");
        palette[9].setToolTipText("An alternative palette.");
        palette[10].setToolTipText("A palette based on software Ultra Fractal.");
        palette[11].setToolTipText("A palette based on the colors of dusk.");
        palette[12].setToolTipText("A palette based on gray scale.");
        palette[13].setToolTipText("A palette based on colors of earth and sky.");
        palette[14].setToolTipText("A palette based on colors of hot and cold.");
        palette[15].setToolTipText("A palette based on color temperature.");
        palette[16].setToolTipText("A palette based on colors of fire.");
        palette[17].setToolTipText("A palette based on matlab's colormap.");
        palette[18].setToolTipText("A palette custom made by the user.");
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JRadioButtonMenuItem[] getPalette() {
        
        return palette;
        
    }
    
}
