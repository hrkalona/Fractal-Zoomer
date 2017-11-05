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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author hrkalona2
 */
public class Infobar extends JToolBar {
    private MainWindow ptr;
    private JLabel palette_toolbar_preview;
    private JLabel max_it_color_preview;
    private JLabel palette_toolbar_preview_lbl;
    private JLabel max_it_color_preview_lbl;
    private JButton overview_button;
    
    public Infobar(MainWindow ptr2, int color_choice, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg, Color fractal_color) {
        super();
        
        this.ptr = ptr2;
        
        setFloatable(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(0, 28));
        setBorderPainted(true);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        Color[] c = null;
        if (color_choice < CustomPaletteEditorFrame.editor_default_palettes.length) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[color_choice], MainWindow.INTERPOLATION_LINEAR, MainWindow.COLOR_SPACE_RGB, false, color_cycling_location, 0, MainWindow.PROCESSING_NONE);
        } else {
            c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
        }

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

        BufferedImage max_it_preview = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
        g = max_it_preview.createGraphics();

        g.setColor(fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        palette_toolbar_preview = new JLabel();
        palette_toolbar_preview.setToolTipText("Displays the active palette.");
        palette_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        palette_toolbar_preview.setIcon(new ImageIcon(palette_preview));
        palette_toolbar_preview.setMaximumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));
        palette_toolbar_preview.setMinimumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));

        max_it_color_preview = new JLabel();
        max_it_color_preview.setToolTipText("Displays the color coresponding to the max iterations.");
        max_it_color_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        max_it_color_preview.setIcon(new ImageIcon(max_it_preview));
        max_it_color_preview.setMaximumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));
        max_it_color_preview.setMinimumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));

        palette_toolbar_preview_lbl = new JLabel(" Palette: ");
        add(palette_toolbar_preview_lbl);
        add(palette_toolbar_preview);
        max_it_color_preview_lbl = new JLabel("  Max It. Color: ");
        add(max_it_color_preview_lbl);
        add(max_it_color_preview);

        overview_button = new JButton();
        overview_button.setIcon(getIcon("/fractalzoomer/icons/overview.png"));
        overview_button.setFocusable(false);
        overview_button.setToolTipText("Creates a report of all the active fractal options.");

        overview_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.Overview();

            }
        });

        add(Box.createHorizontalGlue());
        addSeparator();
        add(overview_button);
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JButton getOverview() {
        
        return overview_button;
        
    }
    
    public JLabel getPalettePreview() {
        
        return palette_toolbar_preview;
        
    }
    
    public JLabel getPalettePreviewLabel() {
        
        return palette_toolbar_preview_lbl;
        
    }
    
    public JLabel getMaxIterationsColorPreview() {
        
        return max_it_color_preview;
        
    }
    
    public JLabel getMaxIterationsColorPreviewLabel() {
        
        return max_it_color_preview_lbl;
        
    }
    
}
