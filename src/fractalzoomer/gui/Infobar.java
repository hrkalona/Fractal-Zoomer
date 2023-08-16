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

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

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
    private JButton stats_button;

    private JButton thread_stats_button;

    private JButton cancel_button;
    private boolean listenerEnabled;
    public static int PALETTE_PREVIEW_WIDTH = 175;
    public static int PALETTE_PREVIEW_HEIGHT = 24;
    public static int GRADIENT_PREVIEW_WIDTH = 80;
    public static int GRADIENT_PREVIEW_HEIGHT = 24;
    public static int SQUARE_TILE_SIZE = 24;
    private Infobar this_toolbar;

    public Infobar(MainWindow ptr2, Settings s) {
        super();

        this.ptr = ptr2;
        this_toolbar = this;

        listenerEnabled = true;

        setFloatable(false);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setAlignmentY(Component.CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(0, 30));
        setBorderPainted(true);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        BufferedImage palette_preview = CommonFunctions.getOutColoringPalettePreview(s, s.ps.color_cycling_location, PALETTE_PREVIEW_WIDTH, PALETTE_PREVIEW_HEIGHT);

        BufferedImage palette_in_preview = CommonFunctions.getInColoringPalettePreview(s, s.ps2.color_cycling_location, PALETTE_PREVIEW_WIDTH, PALETTE_PREVIEW_HEIGHT);

        BufferedImage gradient_preview = CommonFunctions.getGradientPreview(s.gs, s.gs.gradient_offset, GRADIENT_PREVIEW_WIDTH, GRADIENT_PREVIEW_HEIGHT);

        BufferedImage max_it_preview = new BufferedImage(SQUARE_TILE_SIZE, SQUARE_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = max_it_preview.createGraphics();

        g.setColor(s.fractal_color);
        g.fillRect(0, 0, max_it_preview.getWidth(), max_it_preview.getHeight());

        outcoloring_palette_toolbar_preview = new ImageLabel();
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

        incoloring_palette_toolbar_preview = new ImageLabel();
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

        gradient_toolbar_preview = new ImageLabel();
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

        max_it_color_preview = new ImageLabel();
        max_it_color_preview.setToolTipText("Displays the color corresponding to the max iterations.");
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

        overview_button = new MyButton();
        overview_button.setIcon(MainWindow.getIcon("overview.png"));
        overview_button.setFocusable(false);
        overview_button.setToolTipText("Creates a report of all the active fractal options.");

        overview_button.addActionListener(e -> ptr.Overview());

        stats_button = new MyButton();
        stats_button.setIcon(MainWindow.getIcon("stats.png"));
        stats_button.setFocusable(false);
        stats_button.setToolTipText("Displays the statistics of last rendered fractal.");

        stats_button.addActionListener(e -> ptr.Stats());


        thread_stats_button = new MyButton();
        thread_stats_button.setIcon(MainWindow.getIcon("stats_tasks.png"));
        thread_stats_button.setFocusable(false);
        thread_stats_button.setToolTipText("Displays the task statistics of last rendered fractal.");

        thread_stats_button.addActionListener(e -> ptr.ThreadStats());

        cancel_button= new MyButton();
        cancel_button.setIcon(MainWindow.getIcon("abort.png"));
        cancel_button.setFocusable(false);
        cancel_button.setToolTipText("Cancels the current rendering operation and resets.");

        //cancel_button.addActionListener(e -> ptr.cancelOperation());


        add(Box.createHorizontalGlue());
        addSeparator();

//        add(cancel_button);
//        addSeparator();

        add(overview_button);
        addSeparator();
        add(stats_button);
        add(thread_stats_button);

        palette_toolbar_preview_lbl2.setVisible(false);
        incoloring_palette_toolbar_preview.setVisible(false);
    }

    public JButton getOverview() {

        return overview_button;

    }

    public JButton getStats() {

        return stats_button;

    }

    public JButton getStatsTasks() {

        return thread_stats_button;

    }

    public JButton getCancelButton() {
        return cancel_button;
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
        
        JMenu paletteLegacyFractintMen = new MyMenu("Other Palettes/Maps");
        paletteLegacyFractintMen.setIcon(MainWindow.getIcon("palette.png"));

        JMenu p1 = new MyMenu("(1)");
        p1.setIcon(MainWindow.getIcon("palette.png"));

        JMenu p2 = new MyMenu("(2)");
        p2.setIcon(MainWindow.getIcon("palette.png"));

        paletteLegacyFractintMen.add(p1);
        paletteLegacyFractintMen.add(p2);

        int count = 0;

        for (int i = 0; i < palette.length; i++) {

            if (i != MainWindow.DIRECT_PALETTE_ID) {
                Color[] c = null;
                if (i == color_choice) { // the current activated palette
                    if (i < MainWindow.CUSTOM_PALETTE_ID) {
                        c = PresetPalette.getPalette(i, color_cycling_location);
                    } else if (i == MainWindow.CUSTOM_PALETTE_ID) {
                        c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                    }
                    else {
                        c = PresetPalette.getPalette(i, color_cycling_location);
                    }
                } else {// the remaining palettes
                    if (i < MainWindow.CUSTOM_PALETTE_ID) {
                        c = PresetPalette.getPalette(i, 0);
                    } else if (i == MainWindow.CUSTOM_PALETTE_ID) {
                        c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg); // temp color cycling loc
                    }
                    else {
                        c = PresetPalette.getPalette(i, 0);
                    }
                }

                BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = palette_preview.createGraphics();

                for (int j = 0; j < c.length; j++) {
                    if (smoothing) {
                        GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                        g.setPaint(gp);
                        g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
                    } else {
                        g.setColor(c[j]);
                        g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                    }
                }

                palette[i] = new ImageRadioButtonMenuItem(PaletteMenu.paletteNames[i], new ImageIcon(palette_preview));
            }
            else {
                palette[i] = new JRadioButtonMenuItem(PaletteMenu.paletteNames[i], MainWindow.getIcon("palette_load.png"));
            }

            final int temp = i;

            if (i == MainWindow.DIRECT_PALETTE_ID) {
                palette[i].addActionListener(e1 -> ptr.chooseDirectPalette(temp, outcoloring_mode));
                
                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.CTRL_MASK));
                }
                
                popup.addSeparator();
                popup.add(palette[i]);        
            }
            else if (i < MainWindow.CUSTOM_PALETTE_ID) {
                palette[i].addActionListener(e12 -> {

                    if (!this_toolbar.isVisible()) {
                        return;
                    }
                    ptr.setPalette(temp, null, outcoloring_mode ? 0 : 1);

                });
                
                popup.add(palette[i]);
            }
            else if (i == MainWindow.CUSTOM_PALETTE_ID) {                
                popup.addSeparator();
                popup.add(paletteLegacyFractintMen);
                
                palette[i].addActionListener(e13 -> {

                    if (!this_toolbar.isVisible()) {
                        return;
                    }
                    ptr.openCustomPaletteEditor(temp, outcoloring_mode);

                });

                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0));
                }

                popup.addSeparator();
                popup.add(palette[i]);
            }
            else {
                palette[i].addActionListener(e14 -> {

                    if (!this_toolbar.isVisible()) {
                        return;
                    }
                    ptr.setPalette(temp, null, outcoloring_mode ? 0 : 1);

                });

                count++;

                if(count < 13) {
                    p1.add(palette[i]);
                }
                else {
                    p2.add(palette[i]);
                }
            }

            palettes_group.add(palette[i]);

        }

        JMenuItem colorMapframe = new MyMenuItem("Direct Palette Loader", MainWindow.getIcon("palette_load.png"));
        if (outcoloring_mode) {
            colorMapframe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        } else {
            colorMapframe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        }
        colorMapframe.setToolTipText("Loads all color maps from the " + ColorMapFrame.DirName + " directory.");
        colorMapframe.addActionListener(ev -> {ptr.setColorMap(outcoloring_mode);});

        popup.addSeparator();
        popup.add(colorMapframe);

        JMenuItem alternativeCustomDirectPalette  = new MyMenuItem("Custom Direct Palette", MainWindow.getIcon("palette.png"));
        alternativeCustomDirectPalette.setToolTipText("Creates a custom direct palette.");
        if (outcoloring_mode) {
            alternativeCustomDirectPalette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        } else {
            alternativeCustomDirectPalette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        }
        alternativeCustomDirectPalette.addActionListener(ev -> {ptr.setCustomDirectPalette(outcoloring_mode);});

        popup.addSeparator();
        popup.add(alternativeCustomDirectPalette);

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

        palette[20].setToolTipText("A legacy FractInt palette.");
        palette[21].setToolTipText("A legacy FractInt palette.");
        palette[22].setToolTipText("A legacy FractInt palette.");
        palette[23].setToolTipText("A legacy FractInt palette.");
        palette[24].setToolTipText("A legacy FractInt palette.");
        palette[25].setToolTipText("A legacy FractInt palette.");
        palette[26].setToolTipText("A legacy FractInt palette.");
        palette[27].setToolTipText("A legacy FractInt palette.");
        palette[28].setToolTipText("A legacy FractInt palette.");
        palette[29].setToolTipText("A legacy FractInt palette.");
        palette[30].setToolTipText("A legacy FractInt palette.");
        palette[31].setToolTipText("A legacy FractInt palette.");
        palette[32].setToolTipText("A legacy FractInt palette.");
        palette[33].setToolTipText("A legacy FractInt palette.");
        palette[34].setToolTipText("A legacy FractInt palette.");
        palette[35].setToolTipText("A legacy FractInt palette.");
        palette[36].setToolTipText("A palette from QFractal.");
        palette[37].setToolTipText("A palette from QFractal.");
        palette[38].setToolTipText("A palette from QFractal.");
        palette[39].setToolTipText("A palette from QFractal.");
        palette[40].setToolTipText("A palette from QFractal.");
        palette[41].setToolTipText("A palette from Fractal Extreme.");
        palette[42].setToolTipText("A palette from Fractal Extreme.");
        palette[43].setToolTipText("A palette from Fractal Extreme.");
        palette[44].setToolTipText("A palette from Fractal Extreme.");
        
        palette[color_choice].setSelected(true);
        popup.show(e.getComponent(), e.getX(), e.getY());

    }

    public void setListenersEnabled(boolean option) {

        listenerEnabled = option;

    }

}
