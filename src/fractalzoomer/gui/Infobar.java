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

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel gradient_toolbar_preview;
    private JLabel gradient_toolbar_preview_lbl;
    private JButton overview_button;
    public static int PALETTE_PREVIEW_WIDTH = 250;
    public static int PALETTE_PREVIEW_HEIGHT = 24;
    public static int GRADIENT_PREVIEW_WIDTH = 150;
    public static int GRADIENT_PREVIEW_HEIGHT = 24;
    public static int SQUARE_TILE_SIZE = 24;
    
    public Infobar(MainWindow ptr2, Settings s) {
        super();
        
        this.ptr = ptr2;
        
        setFloatable(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(0, 28));
        setBorderPainted(true);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
      
        BufferedImage palette_preview = CommonFunctions.getPalettePreview(s, s.color_cycling_location, PALETTE_PREVIEW_WIDTH, PALETTE_PREVIEW_HEIGHT);             
        
        BufferedImage gradient_preview = CommonFunctions.getGradientPreview(s.gs, GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT);
 
        BufferedImage max_it_preview = new BufferedImage(SQUARE_TILE_SIZE, SQUARE_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(s.fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        palette_toolbar_preview = new JLabel();
        palette_toolbar_preview.setToolTipText("Displays the active palette.");
        palette_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        palette_toolbar_preview.setIcon(new ImageIcon(palette_preview));
        palette_toolbar_preview.setMaximumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));
        palette_toolbar_preview.setMinimumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));
        
        gradient_toolbar_preview = new JLabel();
        gradient_toolbar_preview.setToolTipText("Displays the active gradient.");
        gradient_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        gradient_toolbar_preview.setIcon(new ImageIcon(gradient_preview));
        gradient_toolbar_preview.setMaximumSize(new Dimension(gradient_preview.getWidth(), gradient_preview.getHeight()));
        gradient_toolbar_preview.setMinimumSize(new Dimension(gradient_preview.getWidth(), gradient_preview.getHeight()));
 
        max_it_color_preview = new JLabel();
        max_it_color_preview.setToolTipText("Displays the color coresponding to the max iterations.");
        max_it_color_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        max_it_color_preview.setIcon(new ImageIcon(max_it_preview));
        max_it_color_preview.setMaximumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));
        max_it_color_preview.setMinimumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));

        palette_toolbar_preview_lbl = new JLabel(" Palette: ");
        
        add(palette_toolbar_preview_lbl);
        add(palette_toolbar_preview);
        
        gradient_toolbar_preview_lbl = new JLabel("  Gradient: ");
        add(gradient_toolbar_preview_lbl);
        add(gradient_toolbar_preview);
        
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
    
    public JLabel getGradientPreview() {
        
        return gradient_toolbar_preview;
        
    }
    
    public JLabel getMaxIterationsColorPreview() {
        
        return max_it_color_preview;
        
    }
    
    public JLabel getMaxIterationsColorPreviewLabel() {
        
        return max_it_color_preview_lbl;
        
    }
    
}
