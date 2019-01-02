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

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.palettes.CustomPalette;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;

/**
 *
 * @author hrkalona2
 */
public class Infobar extends JToolBar {

    private static final long serialVersionUID = -7006231616288046167L;
    private MainWindow ptr;
    private JLabel outcoloring_palette_toolbar_preview;
    private JLabel incoloring_palette_toolbar_preview;
    private JLabel max_it_color_preview;
    private JLabel palette_toolbar_preview_lbl;
    private JLabel palette_toolbar_preview_lbl2;
    private JLabel max_it_color_preview_lbl;
    private JLabel gradient_toolbar_preview;
    private JLabel gradient_toolbar_preview_lbl;
    private JButton overview_button;
    private boolean listenerEnabled;
    public static int PALETTE_PREVIEW_WIDTH = 175;
    public static int PALETTE_PREVIEW_HEIGHT = 24;
    public static int GRADIENT_PREVIEW_WIDTH = 80;
    public static int GRADIENT_PREVIEW_HEIGHT = 24;
    public static int SQUARE_TILE_SIZE = 24;
    private int i;
    private Infobar this_toolbar;

    public Infobar(MainWindow ptr2, Settings s) {
        super();

        this.ptr = ptr2;
        this_toolbar = this;

        listenerEnabled = true;

        setFloatable(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(0, 28));
        setBorderPainted(true);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        BufferedImage palette_preview = CommonFunctions.getOutColoringPalettePreview(s, s.ps.color_cycling_location, PALETTE_PREVIEW_WIDTH, PALETTE_PREVIEW_HEIGHT);

        BufferedImage palette_in_preview = CommonFunctions.getInColoringPalettePreview(s, s.ps2.color_cycling_location, PALETTE_PREVIEW_WIDTH, PALETTE_PREVIEW_HEIGHT);

        BufferedImage gradient_preview = CommonFunctions.getGradientPreview(s.gs, GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT);

        BufferedImage max_it_preview = new BufferedImage(SQUARE_TILE_SIZE, SQUARE_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(s.fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        outcoloring_palette_toolbar_preview = new JLabel();
        outcoloring_palette_toolbar_preview.setToolTipText("Displays the active out-coloring palette.");
        outcoloring_palette_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        outcoloring_palette_toolbar_preview.setIcon(new ImageIcon(palette_preview));
        outcoloring_palette_toolbar_preview.setMaximumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));
        outcoloring_palette_toolbar_preview.setMinimumSize(new Dimension(palette_preview.getWidth(), palette_preview.getHeight()));

        outcoloring_palette_toolbar_preview.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!listenerEnabled) {
                    return;
                }

                if (s.ds.domain_coloring && s.ds.domain_coloring_mode != 1) {
                    return;
                }
                createPopupMenu(e, s.ps.color_choice, s.temp_color_cycling_location, true, s.fns.smoothing, s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.ps.color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        incoloring_palette_toolbar_preview = new JLabel();
        incoloring_palette_toolbar_preview.setToolTipText("Displays the active in-coloring palette.");
        incoloring_palette_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        incoloring_palette_toolbar_preview.setIcon(new ImageIcon(palette_in_preview));
        incoloring_palette_toolbar_preview.setMaximumSize(new Dimension(palette_in_preview.getWidth(), palette_in_preview.getHeight()));
        incoloring_palette_toolbar_preview.setMinimumSize(new Dimension(palette_in_preview.getWidth(), palette_in_preview.getHeight()));

        incoloring_palette_toolbar_preview.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!listenerEnabled) {
                    return;
                }
                createPopupMenu(e, s.ps2.color_choice, s.temp_color_cycling_location_second_palette, false, s.fns.smoothing, s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, s.ps2.color_cycling_location, s.ps2.scale_factor_palette_val, s.ps2.processing_alg);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        gradient_toolbar_preview = new JLabel();
        gradient_toolbar_preview.setToolTipText("Displays the active gradient.");
        gradient_toolbar_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        gradient_toolbar_preview.setIcon(new ImageIcon(gradient_preview));
        gradient_toolbar_preview.setMaximumSize(new Dimension(gradient_preview.getWidth(), gradient_preview.getHeight()));
        gradient_toolbar_preview.setMinimumSize(new Dimension(gradient_preview.getWidth(), gradient_preview.getHeight()));

        gradient_toolbar_preview.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!listenerEnabled) {
                    return;
                }

                ptr.setGradient();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        max_it_color_preview = new JLabel();
        max_it_color_preview.setToolTipText("Displays the color coresponding to the max iterations.");
        max_it_color_preview.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        max_it_color_preview.setIcon(new ImageIcon(max_it_preview));
        max_it_color_preview.setMaximumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));
        max_it_color_preview.setMinimumSize(new Dimension(max_it_preview.getWidth(), max_it_preview.getHeight()));

        max_it_color_preview.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!listenerEnabled) {
                    return;
                }

                ptr.setFractalColor();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        palette_toolbar_preview_lbl = new JLabel(" Palette(Out): ");

        add(palette_toolbar_preview_lbl);
        add(outcoloring_palette_toolbar_preview);

        palette_toolbar_preview_lbl2 = new JLabel("  Palette(In): ");
        add(palette_toolbar_preview_lbl2);
        add(incoloring_palette_toolbar_preview);

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

        palette_toolbar_preview_lbl2.setVisible(false);
        incoloring_palette_toolbar_preview.setVisible(false);
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

    public JButton getOverview() {

        return overview_button;

    }

    public JLabel getOutColoringPalettePreview() {

        return outcoloring_palette_toolbar_preview;

    }

    public JLabel getInColoringPalettePreview() {

        return incoloring_palette_toolbar_preview;

    }

    public JLabel getGradientPreview() {

        return gradient_toolbar_preview;

    }

    public JLabel getOutColoringPalettePreviewLabel() {

        return palette_toolbar_preview_lbl;

    }

    public JLabel getInColoringPalettePreviewLabel() {

        return palette_toolbar_preview_lbl2;

    }

    public JLabel getGradientPreviewLabel() {

        return gradient_toolbar_preview_lbl;

    }

    public JLabel getMaxIterationsColorPreview() {

        return max_it_color_preview;

    }

    public JLabel getMaxIterationsColorPreviewLabel() {

        return max_it_color_preview_lbl;

    }

    public void createPopupMenu(MouseEvent e, int color_choice, int temp_color_cycling_location, boolean outcoloring_mode, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg) {
        JPopupMenu popup = new JPopupMenu();

        JRadioButtonMenuItem[] palette = new JRadioButtonMenuItem[PaletteMenu.paletteNames.length];

        ButtonGroup palettes_group = new ButtonGroup();

        for (i = 0; i < palette.length; i++) {

            if (i != MainWindow.DIRECT_PALETTE_ID) {
                Color[] c = null;
                if (i == color_choice) { // the current activated palette
                    if (i != MainWindow.CUSTOM_PALETTE_ID) {
                        c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], MainWindow.INTERPOLATION_LINEAR, MainWindow.COLOR_SPACE_RGB, false, color_cycling_location, 0, MainWindow.PROCESSING_NONE);
                    } else {
                        c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                    }
                } else {// the remaining palettes
                    if (i != MainWindow.CUSTOM_PALETTE_ID) {
                        c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[i], MainWindow.INTERPOLATION_LINEAR, MainWindow.COLOR_SPACE_RGB, false, 0, 0, MainWindow.PROCESSING_NONE); // 0 color cycling loc
                    } else {
                        c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg); // temp color cycling loc
                    }
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

                palette[i] = new JRadioButtonMenuItem(PaletteMenu.paletteNames[i], new ImageIcon(palette_preview));
            }
            else {
                palette[i] = new JRadioButtonMenuItem(PaletteMenu.paletteNames[i], getIcon("/fractalzoomer/icons/palette_load.png"));
            }

            if (i == MainWindow.DIRECT_PALETTE_ID) {
                palette[i].addActionListener(new ActionListener() {

                    int temp = i;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        ptr.chooseDirectPalette(temp, outcoloring_mode);

                    }
                });
                
                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.CTRL_MASK));
                }
                
                popup.addSeparator();
            }
            else if (i != MainWindow.CUSTOM_PALETTE_ID) {
                palette[i].addActionListener(new ActionListener() {

                    int temp = i;

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!this_toolbar.isVisible()) {
                            return;
                        }
                        ptr.setPalette(temp, null, outcoloring_mode ? 0 : 1);

                    }
                });            
            }
            else {
                palette[i].addActionListener(new ActionListener() {

                    int temp = i;

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (!this_toolbar.isVisible()) {
                            return;
                        }
                        ptr.openCustomPaletteEditor(temp, outcoloring_mode);

                    }
                });

                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0));
                }

                popup.addSeparator();
            }

            popup.add(palette[i]);
            palettes_group.add(palette[i]);

        }

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
        palette[MainWindow.CUSTOM_PALETTE_ID].setToolTipText("A palette custom made by the user.");
        palette[MainWindow.DIRECT_PALETTE_ID].setToolTipText("A palette loaded directly from a file (RGB: 0-255 0-255 0-255 format).");

        palette[color_choice].setSelected(true);
        popup.show(e.getComponent(), e.getX(), e.getY());

    }

    public void setListenersEnabled(boolean option) {

        listenerEnabled = option;

    }

}
