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

import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;

import javax.swing.*;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 *
 * @author hrkalona2
 */
public class PaletteMenu extends MyMenu {

    private static final long serialVersionUID = 3271849856447452259L;
    private MainWindow ptr;
    private JRadioButtonMenuItem[] palette;
    public static final String[] paletteNames;
    private JMenu paletteLegacyFractintMenu;

    private JMenuItem colorMapframe;
    private JMenuItem alternativeCustomDirectPalette;

    static {
        paletteNames = new String[MainWindow.TOTAL_PALETTES];
        paletteNames[0] = "Default";
        paletteNames[1] = "Spectrum";
        paletteNames[2] = "Alternative";
        paletteNames[3] = "Alternative 2";
        paletteNames[4] = "Alternative 3";
        paletteNames[5] = "Alternative 4";
        paletteNames[6] = "Alternative 5";
        paletteNames[7] = "Alternative 6";
        paletteNames[8] = "Alternative 7";
        paletteNames[9] = "Alternative 8";
        paletteNames[10] = "Alternative 9";
        paletteNames[11] = "Dusk";
        paletteNames[12] = "Gray Scale";
        paletteNames[13] = "Earth Sky";
        paletteNames[14] = "Hot Cold";
        paletteNames[15] = "Hot Cold 2";
        paletteNames[16] = "Fire";
        paletteNames[17] = "Jet";
        
        paletteNames[MainWindow.CUSTOM_PALETTE_ID] = "Custom Palette";
        paletteNames[MainWindow.DIRECT_PALETTE_ID] = "Direct Palette";
        
        //Legacy FractInt
        paletteNames[20] = "Default";
        paletteNames[21] = "Arriw";
        paletteNames[22] = "Atomic";
        paletteNames[23] = "Blue";
        paletteNames[24] = "Blues";
        paletteNames[25] = "Chroma";
        paletteNames[26] = "JFestival";
        paletteNames[27] = "Neon";
        paletteNames[28] = "Rich8z3";
        paletteNames[29] = "Skydye11";
        paletteNames[30] = "Skydye12";
        paletteNames[31] = "Spiral";
        paletteNames[32] = "Volcano";
        paletteNames[33] = "Wizzl014";
        paletteNames[34] = "Wizzl017";
        paletteNames[35] = "Wizzl018";
        paletteNames[36] = "Q Fractal";
        paletteNames[37] = "Q Fractal 2";
        paletteNames[38] = "Q Fractal 3";
        paletteNames[39] = "Q Fractal 4";
        paletteNames[40] = "Q Fractal 5";
    }

    public PaletteMenu(MainWindow ptr2, String name, int color_choice, boolean smoothing, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int color_cycling_location, double scale_factor_palette_val, int processing_alg, final boolean outcoloring_mode, int temp_color_cycling_location) {

        super(name);

        this.ptr = ptr2;

        setIcon(MainWindow.getIcon("palette.png"));
        
        paletteLegacyFractintMenu = new MyMenu("Other Paletttes/Maps");
        paletteLegacyFractintMenu.setIcon(MainWindow.getIcon("palette.png"));
        
        palette = new JRadioButtonMenuItem[paletteNames.length];

        ButtonGroup palettes_group = new ButtonGroup();

        for (int i = 0; i < palette.length; i++) {

            if (i != MainWindow.DIRECT_PALETTE_ID) {
                Color[] c = null;
                if (i == color_choice) { // the current activated palette
                    if (i < MainWindow.CUSTOM_PALETTE_ID) {                      
                        c = PresetPalette.getPalette(i, color_cycling_location);
                    } else if(i == MainWindow.CUSTOM_PALETTE_ID) {
                        c = CustomPalette.getPalette(custom_palette, color_interpolation, color_space, reversed_palette, color_cycling_location, scale_factor_palette_val, processing_alg);
                    }
                    else {
                        c = PresetPalette.getPalette(i, color_cycling_location);
                    }
                } else {// the remaining palettes
                    if (i < MainWindow.CUSTOM_PALETTE_ID) {                        
                        c = PresetPalette.getPalette(i, 0);
                    } else if(i == MainWindow.CUSTOM_PALETTE_ID) {
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

                palette[i] = new ImageRadioButtonMenuItem(paletteNames[i], new ImageIcon(palette_preview));
            } else {
                palette[i] = new JRadioButtonMenuItem(paletteNames[i], MainWindow.getIcon("palette_load.png"));
            }

            final int temp = i;

            if (i == MainWindow.DIRECT_PALETTE_ID) {
                palette[i].addActionListener(e -> ptr.chooseDirectPalette(temp, outcoloring_mode));
                
                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, ActionEvent.CTRL_MASK));
                }
                
                addSeparator();
                
                add(palette[i]);               
                
            }
            else if (i < MainWindow.CUSTOM_PALETTE_ID) {
                palette[i].addActionListener(e -> ptr.setPalette(temp, null, outcoloring_mode ? 0 : 1));
                
                add(palette[i]);
            } else if (i == MainWindow.CUSTOM_PALETTE_ID){
                
                addSeparator();
                
                add(paletteLegacyFractintMenu);
                
                palette[i].addActionListener(e -> ptr.openCustomPaletteEditor(temp, outcoloring_mode));

                if (outcoloring_mode) {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
                } else {
                    palette[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, 0));
                }

                addSeparator();
                
                add(palette[i]);
            }
            else {
                palette[i].addActionListener(e -> ptr.setPalette(temp, null, outcoloring_mode ? 0 : 1));
                
                paletteLegacyFractintMenu.add(palette[i]);
            }

            
            palettes_group.add(palette[i]);
        }

        colorMapframe = new MyMenuItem("Direct Palette Loader", MainWindow.getIcon("palette_load.png"));
        colorMapframe.setToolTipText("Loads all color maps from the " + ColorMapFrame.DirName + " directory.");
        if (outcoloring_mode) {
            colorMapframe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        } else {
            colorMapframe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SLASH, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        }
        colorMapframe.addActionListener(e -> {ptr.setColorMap(outcoloring_mode);});

        addSeparator();
        add(colorMapframe);

        alternativeCustomDirectPalette  = new MyMenuItem("Custom Direct Palette", MainWindow.getIcon("palette.png"));
        alternativeCustomDirectPalette.setToolTipText("Creates a custom direct palette.");
        if (outcoloring_mode) {
            alternativeCustomDirectPalette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        } else {
            alternativeCustomDirectPalette.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        }
        alternativeCustomDirectPalette.addActionListener(e -> {ptr.setCustomDirectPalette(outcoloring_mode);});

        addSeparator();
        add(alternativeCustomDirectPalette);

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
    }

    public JRadioButtonMenuItem[] getPalette() {

        return palette;

    }

    public static int[] loadDirectPalette(String fileName, Component parent, boolean showImportDialog) {
        int[] palette = null;

        java.util.List<Integer> rgbs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(sCurrentLine);
                
                sCurrentLine = sCurrentLine.trim();
                
                if(st.countTokens() == 0) continue;
                
                if(sCurrentLine.startsWith("#") || sCurrentLine.startsWith("//")) continue;
     
                if(st.countTokens() < 3) {
                    return null;
                }
                
                int r = Integer.parseInt(st.nextToken());
                int g = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                
                r = r < 0 ? 0 : r;
                g = g < 0 ? 0 : g;
                b = b < 0 ? 0 : b;
                
                r = r > 255 ? 255 : r;
                g = g > 255 ? 255 : g;
                b = b > 255 ? 255 : b;
                
                rgbs.add(0xFF000000 | r << 16 | g << 8 | b);
            }
        } catch (Exception ex) {
            return null;
        }
        
        if(!rgbs.isEmpty()) {
            if(showImportDialog) {
                rgbs = importDialog(rgbs, parent);
            }
            palette = rgbs.stream().mapToInt(Integer::valueOf).toArray();
        }

        return palette;
    }
    
    public static java.util.List<Integer> importDialog(java.util.List<Integer> colors, Component parent) {
        final JCheckBox invert = new JCheckBox("Reverse Palette");
        invert.setSelected(false);
        invert.setFocusable(false);
        invert.setToolTipText("Imports the palette in reversed order.");

        Object[] message = {
            "Successfully imported a palette that contains " + colors.size() + " colors.",
            invert};

        JOptionPane.showMessageDialog(parent, message, "Direct Palette", JOptionPane.INFORMATION_MESSAGE);

        if(invert.isSelected()) {
            Collections.reverse(colors);
        }
        
        return colors;
  
    }

    public static int[] choosePaletteFile(Component parent) {

        JFileChooser file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(parent, "Load Direct Palette");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            int [] res = loadDirectPalette(file_chooser.getSelectedFile().getPath(), parent, true);
            
            if(res == null) {
                JOptionPane.showMessageDialog(parent, "Failed to directly load the palette.", "Error!", JOptionPane.ERROR_MESSAGE);
            }

            MainWindow.SaveSettingsPath = file_chooser.getSelectedFile().getParent();
            return res;
        }
        
        return null;
    }

}
