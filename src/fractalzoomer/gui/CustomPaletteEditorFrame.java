/*
 * Copyright (C) 2020 hrkalona
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

import de.articdive.jnoise.core.api.functions.Interpolation;
import de.articdive.jnoise.generators.noise_parameters.fade_functions.FadeFunction;
import de.articdive.jnoise.generators.noisegen.opensimplex.FastSimplexNoiseGenerator;
import de.articdive.jnoise.generators.noisegen.perlin.PerlinNoiseGenerator;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.settings.SettingsPalette;
import fractalzoomer.settings.SettingsPalette1062;
import fractalzoomer.utils.*;

import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static fractalzoomer.main.Constants.color_interp_str;

/**
 *
 * @author hrkalona
 */
public class CustomPaletteEditorFrame extends JFrame {

    private static final long serialVersionUID = -5234702415922634911L;
    private CustomPaletteEditorFrame this_frame;
    private MainWindow ptra2;

    private JComboBox<String> combo_box_random_palette_alg;
    private JCheckBox same_hues;

    private JCheckBox pastel_cb;
    private JLabel[] labels;
    private JTextField[] textfields;
    private JFrame color_picker_frame;
    private JCheckBox check_box_preview_smooth_color;
    private BufferedImage colors;
    private BufferedImage colors2;
    private JLabel gradient;
    private JLabel graph;
    private JComboBox<String> combo_box_color_space;
    private JComboBox<String> combo_box_color_interp;
    private JCheckBox check_box_reveres_palette;
    private JComboBox<String> combo_box_processing;
    private JLabel length_label;
    private JSpinner offset_textfield;
    private JSlider scale_factor_palette_slid;
    private JFileChooser file_chooser;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;
    private int[][] temp_custom_palette;
    private int mouse_color_label_x;
    private JMenuItem[] preset_palettes;
    private int[] palette_indexing;
    private boolean blockUpdate;
    private int selectedIndex;
    private Color oldColor;

    private int color_interpolation;
    private int color_space;
    private boolean reversed_palette;
    private int temp_color_cycling_location;
    private double scale_factor_palette_val;
    private int processing_alg;

    public static int random_palette_algorithm;
    public static boolean equal_hues;
    public static boolean pastel;
    public static Random generator;
    public static long seed = 0;

    public static JsonPalettesContainer extraPalettes;
    
    static {
        generator = new Random(System.currentTimeMillis());
        extraPalettes = new JsonPalettesContainer();
    }

    public static void reSeed() {
        if(seed == 0) {
            generator = new Random(System.currentTimeMillis());
        }
        else {
            generator = new Random(seed);
        }
    }

    public static final int[][][] editor_default_palettes = {{{12, 0, 10, 20}, {12, 50, 100, 240}, {12, 20, 3, 26}, {12, 230, 60, 20}, {12, 25, 10, 9}, {12, 230, 170, 0}, {12, 20, 40, 10}, {12, 0, 100, 0}, {12, 5, 10, 10}, {12, 210, 70, 30}, {12, 90, 0, 50}, {12, 180, 90, 120}, {12, 0, 20, 40}, {12, 30, 70, 200}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{22, 0, 24, 255}, {21, 202, 0, 255}, {21, 255, 0, 82}, {22, 255, 133, 0}, {21, 151, 255, 0}, {21, 0, 255, 75}, {22, 0, 209, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{15, 0, 0, 191}, {8, 123, 0, 255}, {6, 165, 0, 139}, {5, 214, 109, 0}, {5, 255, 200, 0}, {10, 243, 255, 0}, {7, 201, 0, 0}, {8, 172, 0, 112}, {5, 204, 0, 239}, {12, 208, 11, 255}, {5, 255, 138, 193}, {8, 200, 170, 178}, {5, 55, 255, 217}, {5, 0, 206, 231}, {6, 0, 124, 255}, {9, 0, 26, 173}, {12, 0, 116, 51}, {5, 0, 235, 226}, {7, 30, 255, 255}, {8, 134, 191, 255}, {5, 254, 186, 255}, {5, 242, 185, 255}, {17, 235, 184, 244}, {5, 130, 42, 57}, {8, 99, 0, 116}, {8, 50, 64, 210}, {12, 0, 128, 167}, {4, 64, 223, 102}, {9, 86, 255, 137}, {13, 134, 191, 217}, {6, 204, 99, 140}, {10, 117, 57, 105}},
    {{8, 0, 0, 0}, {8, 0, 127, 254}, {8, 0, 2, 2}, {8, 0, 252, 127}, {8, 0, 4, 0}, {8, 124, 250, 0}, {8, 7, 7, 0}, {8, 247, 124, 0}, {8, 9, 0, 0}, {8, 245, 0, 122}, {8, 11, 0, 11}, {8, 243, 121, 243}, {8, 13, 13, 13}, {8, 121, 121, 241}, {8, 0, 0, 16}, {8, 0, 119, 238}, {8, 0, 18, 18}, {8, 0, 236, 119}, {8, 0, 20, 0}, {8, 117, 234, 0}, {8, 22, 22, 0}, {8, 232, 117, 0}, {8, 25, 0, 0}, {8, 229, 0, 114}, {8, 27, 0, 27}, {8, 227, 113, 227}, {8, 29, 29, 29}, {8, 113, 113, 225}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{8, 116, 0, 7}, {8, 255, 173, 0}, {8, 160, 0, 0}, {8, 0, 0, 0}, {8, 27, 0, 0}, {8, 14, 0, 0}, {8, 0, 9, 0}, {8, 0, 112, 255}, {8, 0, 218, 255}, {8, 0, 0, 0}, {8, 0, 255, 0}, {8, 66, 70, 0}, {8, 255, 255, 32}, {8, 0, 123, 0}, {8, 0, 20, 0}, {8, 0, 0, 0}, {8, 239, 33, 0}, {8, 255, 119, 0}, {8, 0, 0, 0}, {8, 152, 0, 0}, {8, 0, 0, 0}, {8, 12, 136, 255}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 255, 255, 115}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 0, 0, 0}, {8, 255, 0, 0}, {8, 255, 255, 123}, {8, 0, 0, 0}, {8, 255, 144, 0}},
    {{16, 0, 0, 0}, {16, 66, 24, 11}, {16, 148, 49, 28}, {16, 247, 101, 62}, {16, 240, 202, 112}, {16, 206, 252, 145}, {16, 179, 252, 195}, {16, 131, 189, 253}, {16, 56, 85, 137}, {16, 4, 7, 15}, {16, 27, 8, 8}, {16, 86, 28, 16}, {16, 172, 64, 27}, {16, 250, 123, 37}, {16, 224, 209, 38}, {16, 141, 253, 37}, {16, 38, 134, 18}, {16, 4, 22, 2}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0},},
    {{8, 0, 0, 0}, {8, 0, 0, 22}, {8, 0, 0, 255}, {8, 0, 0, 44}, {8, 0, 0, 255}, {8, 0, 255, 255}, {8, 0, 0, 67}, {8, 0, 255, 0}, {8, 0, 0, 89}, {8, 255, 255, 0}, {8, 0, 0, 111}, {8, 255, 0, 0}, {8, 0, 0, 133}, {8, 255, 0, 255}, {8, 0, 0, 155}, {8, 255, 255, 255}, {8, 0, 0, 177}, {8, 0, 0, 0}, {8, 0, 0, 200}, {8, 0, 0, 255}, {8, 0, 0, 222}, {8, 255, 0, 255}, {8, 0, 0, 244}, {8, 255, 0, 0}, {8, 0, 11, 255}, {8, 255, 255, 0}, {8, 0, 33, 255}, {8, 0, 255, 0}, {8, 0, 55, 255}, {8, 0, 255, 255}, {8, 0, 78, 255}, {8, 255, 255, 255}},
    {{16, 2, 0, 0}, {16, 159, 0, 0}, {16, 255, 0, 0}, {16, 255, 113, 0}, {16, 255, 252, 0}, {16, 255, 255, 129}, {16, 255, 255, 249}, {16, 147, 255, 255}, {16, 27, 255, 255}, {16, 0, 187, 255}, {16, 0, 11, 255}, {16, 0, 0, 163}, {16, 0, 0, 18}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{16, 90, 91, 185}, {22, 239, 241, 247}, {16, 255, 20, 123}, {16, 168, 0, 33}, {16, 6, 0, 1}, {16, 41, 61, 61}, {16, 56, 130, 84}, {16, 70, 181, 101}, {16, 216, 239, 209}, {16, 252, 248, 252}, {16, 217, 98, 190}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{22, 0, 0, 0}, {22, 98, 90, 88}, {22, 255, 255, 255}, {22, 220, 175, 121}, {22, 145, 90, 33}, {22, 37, 22, 8}, {22, 0, 0, 0}, {22, 4, 50, 71}, {22, 17, 121, 169}, {22, 33, 48, 51}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{16, 159, 234, 115}, {16, 241, 253, 129}, {16, 230, 255, 147}, {16, 238, 199, 60}, {16, 253, 169, 71}, {16, 255, 93, 45}, {16, 225, 0, 0}, {16, 91, 0, 0}, {16, 140, 2, 61}, {16, 87, 0, 23}, {16, 63, 0, 70}, {16, 15, 3, 111}, {16, 10, 37, 114}, {16, 107, 139, 154}, {16, 49, 89, 80}, {16, 21, 90, 35}, {16, 0, 40, 0}, {16, 18, 76, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{16, 0, 0, 0}, {16, 19, 26, 49}, {16, 128, 141, 136}, {16, 215, 203, 143}, {16, 247, 205, 166}, {16, 191, 153, 172}, {16, 80, 62, 94}, {16, 48, 33, 33}, {16, 103, 40, 39}, {16, 237, 73, 64}, {16, 255, 151, 103}, {16, 254, 207, 87}, {16, 255, 195, 100}, {16, 255, 116, 58}, {16, 151, 36, 17}, {16, 41, 8, 4}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{22, 255, 255, 255}, {21, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{16, 0, 10, 115}, {16, 13, 62, 177}, {16, 76, 161, 228}, {16, 192, 238, 252}, {16, 244, 253, 240}, {16, 253, 230, 110}, {16, 255, 179, 2}, {16, 184, 99, 0}, {16, 86, 44, 0}, {16, 0, 2, 7}, {16, 0, 5, 72}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{8, 255, 255, 255}, {8, 27, 118, 255}, {8, 19, 83, 180}, {8, 40, 62, 94}, {8, 94, 40, 83}, {8, 180, 19, 147}, {8, 255, 27, 209}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{22, 255, 56, 0}, {22, 255, 180, 107}, {22, 255, 228, 206}, {22, 245, 243, 255}, {22, 214, 225, 255}, {22, 186, 208, 255}, {22, 155, 188, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{8, 0, 0, 0}, {8, 214, 0, 0}, {8, 255, 45, 0}, {8, 255, 100, 0}, {8, 255, 155, 0}, {8, 255, 210, 0}, {8, 255, 255, 41}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}},
    {{12, 0, 0, 189}, {12, 0, 0, 255}, {12, 0, 66, 255}, {12, 0, 132, 255}, {12, 0, 189, 255}, {12, 0, 255, 255}, {12, 66, 255, 189}, {12, 132, 255, 132}, {12, 189, 255, 66}, {12, 255, 255, 0}, {12, 255, 189, 0}, {12, 255, 132, 0}, {12, 255, 66, 0}, {12, 255, 0, 0}, {12, 189, 0, 0}, {12, 132, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}};

    public static final int[][] default_editor_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    static {
        random_palette_algorithm = 0;
        equal_hues = false;
    }

    private Future<?> future;

    public CustomPaletteEditorFrame(MainWindow ptra, final JRadioButtonMenuItem[] palette, boolean smoothing, final int palette_id, final int color_choice, int[][] custom_palette, int color_interpolation2, int color_space2, boolean reversed_palette2, int temp_color_cycling_location2, double scale_factor_palette_val2, int processing_alg2, final boolean outcoloring_mode) {

        super();

        selectedIndex = -1;
        
        this_frame = this;
        ptra2 = ptra;

        blockUpdate = false;

        color_interpolation = color_interpolation2;
        color_space = color_space2;
        reversed_palette = reversed_palette2;
        temp_color_cycling_location = temp_color_cycling_location2;
        scale_factor_palette_val = scale_factor_palette_val2;
        processing_alg = processing_alg2;

        ptra2.setEnabled(false);
        int custom_palette_window_width = 990;
        int custom_palette_window_height = 655;
        setTitle("Custom Palette Editor");
        setIconImage(MainWindow.getIcon("palette.png").getImage());
        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grab.gif").getImage(), new Point(16, 16), "grab");
        grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");

        final JButton add_palette = new MyButton();
        add_palette.setFocusable(false);
        final JButton minus_palette = new MyButton();
        minus_palette.setFocusable(false);
        final JButton swap_palette = new MyButton();
        swap_palette.setFocusable(false);
        final JButton copy_palette = new MyButton();
        copy_palette.setFocusable(false);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                ptra2.setEnabled(true);
                palette[palette_id].setSelected(color_choice == palette_id);
                dispose();

            }
        });

        temp_custom_palette = new int[custom_palette.length][custom_palette[0].length];
        for (int k = 0; k < custom_palette.length; k++) {
            for (int j = 0; j < custom_palette[0].length; j++) {
                temp_custom_palette[k][j] = custom_palette[k][j];
            }
        }

        JPanel editor_panel = new JPanel();
        JPanel tools = new JPanel();
        JPanel palette_colors = new JPanel();
        JPanel buttons = new JPanel();

        editor_panel.setPreferredSize(new Dimension(910, 495));
        editor_panel.setLayout(new FlowLayout());
        editor_panel.setBackground(MainWindow.bg_color);
        tools.setLayout(new FlowLayout());
        tools.setPreferredSize(new Dimension(890, 95));
        tools.setBackground(MainWindow.bg_color);
        palette_colors.setLayout(new FlowLayout());
        palette_colors.setPreferredSize(new Dimension(890, 80));
        palette_colors.setBackground(MainWindow.bg_color);
        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        labels = new JLabel[32];
        textfields = new JTextField[32];

        for (int k = 0; k < labels.length; k++) {
            labels[k] = new JLabel("");
            labels[k].setPreferredSize(new Dimension(22, 22));
            labels[k].setOpaque(true);
            labels[k].setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
            labels[k].setBackground(new Color(temp_custom_palette[k][1], temp_custom_palette[k][2], temp_custom_palette[k][3]));
            labels[k].setToolTipText("Left click to change this color, add a new slot, remove this color, copy and add a color, or swap this color.");

            final int temp = k;
            labels[k].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {

                    if (!add_palette.isSelected() && !minus_palette.isSelected() && !swap_palette.isSelected() && !copy_palette.isSelected()) {
                        new ColorChooserFrame(this_frame, "Choose Color", labels[temp], temp, -1);
                    } else if (add_palette.isSelected()) {
                        for (int m = temp_custom_palette.length - 1; m > temp; m--) {
                            temp_custom_palette[m][0] = temp_custom_palette[m - 1][0];
                            temp_custom_palette[m][1] = temp_custom_palette[m - 1][1];
                            temp_custom_palette[m][2] = temp_custom_palette[m - 1][2];
                            temp_custom_palette[m][3] = temp_custom_palette[m - 1][3];
                            labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                            textfields[m].setText("" + temp_custom_palette[m][0]);
                        }

                        temp_custom_palette[temp][0] = 0;
                        temp_custom_palette[temp][1] = 0;
                        temp_custom_palette[temp][2] = 0;
                        temp_custom_palette[temp][3] = 0;

                        labels[temp].setBackground(new Color(temp_custom_palette[temp][1], temp_custom_palette[temp][2], temp_custom_palette[temp][3]));
                        textfields[temp].setText("" + temp_custom_palette[temp][0]);

                        try {
                            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                            paintGradientAndGraph(c);
                        } catch (ArithmeticException ex) {
                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                        }
                    } else if (minus_palette.isSelected()){
                        for (int m = temp; m < temp_custom_palette.length - 1; m++) {
                            temp_custom_palette[m][0] = temp_custom_palette[m + 1][0];
                            temp_custom_palette[m][1] = temp_custom_palette[m + 1][1];
                            temp_custom_palette[m][2] = temp_custom_palette[m + 1][2];
                            temp_custom_palette[m][3] = temp_custom_palette[m + 1][3];
                            labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                            textfields[m].setText("" + temp_custom_palette[m][0]);
                        }

                        temp_custom_palette[temp_custom_palette.length - 1][0] = 0;
                        temp_custom_palette[temp_custom_palette.length - 1][1] = 0;
                        temp_custom_palette[temp_custom_palette.length - 1][2] = 0;
                        temp_custom_palette[temp_custom_palette.length - 1][3] = 0;

                        labels[temp_custom_palette.length - 1].setBackground(new Color(temp_custom_palette[temp_custom_palette.length - 1][1], temp_custom_palette[temp_custom_palette.length - 1][2], temp_custom_palette[temp_custom_palette.length - 1][3]));
                        textfields[temp_custom_palette.length - 1].setText("" + temp_custom_palette[temp_custom_palette.length - 1][0]);

                        try {
                            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                            paintGradientAndGraph(c);
                        } catch (ArithmeticException ex) {
                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                        }
                    }
                    else if (swap_palette.isSelected()){
                        if(selectedIndex == -1) {
                            selectedIndex = temp;
                            textfields[selectedIndex].setBackground(new Color(153, 237, 195));
                            return;
                        }
                        
                        int temp_hues = temp_custom_palette[selectedIndex][0];
                        int temp_red = temp_custom_palette[selectedIndex][1];
                        int temp_green = temp_custom_palette[selectedIndex][2];
                        int temp_blue = temp_custom_palette[selectedIndex][3];
                        
                        temp_custom_palette[selectedIndex][0] = temp_custom_palette[temp][0];
                        temp_custom_palette[selectedIndex][1] = temp_custom_palette[temp][1];
                        temp_custom_palette[selectedIndex][2] = temp_custom_palette[temp][2];
                        temp_custom_palette[selectedIndex][3] = temp_custom_palette[temp][3];
                        
                        temp_custom_palette[temp][0] = temp_hues;
                        temp_custom_palette[temp][1] = temp_red;
                        temp_custom_palette[temp][2] = temp_green;
                        temp_custom_palette[temp][3] = temp_blue;
                        

                        labels[selectedIndex].setBackground(new Color(temp_custom_palette[selectedIndex][1], temp_custom_palette[selectedIndex][2], temp_custom_palette[selectedIndex][3]));
                        labels[temp].setBackground(new Color(temp_custom_palette[temp][1], temp_custom_palette[temp][2], temp_custom_palette[temp][3]));
                        
                        textfields[selectedIndex].setText("" + temp_custom_palette[selectedIndex][0]);
                        textfields[temp].setText("" + temp_custom_palette[temp][0]);

                        try {
                            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                            paintGradientAndGraph(c);
                        } catch (ArithmeticException ex) {
                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                        }
                        
                        textfields[selectedIndex].setBackground(oldColor);
                        selectedIndex = -1;
                        
                        
                    }
                    else {
                        if(selectedIndex == -1) {
                            selectedIndex = temp;
                            textfields[selectedIndex].setBackground(new Color(153, 237, 195));
                            return;
                        }
                        
                        int temp_hues = temp_custom_palette[selectedIndex][0];
                        int temp_red = temp_custom_palette[selectedIndex][1];
                        int temp_green = temp_custom_palette[selectedIndex][2];
                        int temp_blue = temp_custom_palette[selectedIndex][3];
                        
                        for (int m = temp_custom_palette.length - 1; m > temp; m--) {
                            temp_custom_palette[m][0] = temp_custom_palette[m - 1][0];
                            temp_custom_palette[m][1] = temp_custom_palette[m - 1][1];
                            temp_custom_palette[m][2] = temp_custom_palette[m - 1][2];
                            temp_custom_palette[m][3] = temp_custom_palette[m - 1][3];
                            labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                            textfields[m].setText("" + temp_custom_palette[m][0]);
                        }

                        temp_custom_palette[temp][0] = temp_hues;
                        temp_custom_palette[temp][1] = temp_red;
                        temp_custom_palette[temp][2] = temp_green;
                        temp_custom_palette[temp][3] = temp_blue;

                        labels[temp].setBackground(new Color(temp_custom_palette[temp][1], temp_custom_palette[temp][2], temp_custom_palette[temp][3]));
                        textfields[temp].setText("" + temp_custom_palette[temp][0]);
                        
                        try {
                            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                            paintGradientAndGraph(c);
                        } catch (ArithmeticException ex) {
                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                        }
                        
                        textfields[selectedIndex].setBackground(oldColor);
                        selectedIndex = -1;
                    }
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
            palette_colors.add(labels[k]);
        }

        for (int k = 0; k < labels.length; k++) {

            textfields[k] = new JTextField();
            textfields[k].setPreferredSize(new Dimension(22, 22));
            textfields[k].setText("" + temp_custom_palette[k][0]);
            textfields[k].setFont(new Font("Arial", Font.PLAIN, 12));
            oldColor = textfields[k].getBackground();

            final int temp2 = k;
            textfields[k].getDocument().addDocumentListener(new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {

                    if (blockUpdate) {
                        return;
                    }

                    try {

                        int temp3 = Integer.parseInt(textfields[temp2].getText());

                        if (temp3 < 0 || temp3 > 99) {
                            temp_custom_palette[temp2][0] = -1;

                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                            return;
                        }

                        temp_custom_palette[temp2][0] = temp3;

                        temp_custom_palette[temp2][1] = labels[temp2].getBackground().getRed();
                        temp_custom_palette[temp2][2] = labels[temp2].getBackground().getGreen();
                        temp_custom_palette[temp2][3] = labels[temp2].getBackground().getBlue();

                        Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                        paintGradientAndGraph(c);
                    } catch (ArithmeticException ex) {
                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    } catch (NumberFormatException ex) {
                        temp_custom_palette[temp2][0] = -1;

                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                }

                @Override
                public void removeUpdate(DocumentEvent e) {

                    if (blockUpdate) {
                        return;
                    }

                    try {

                        int temp3 = Integer.parseInt(textfields[temp2].getText());

                        if (temp3 < 0 || temp3 > 99) {
                            temp_custom_palette[temp2][0] = -1;

                            length_label.setText("0");
                            Graphics2D g = colors.createGraphics();
                            g.setColor(Color.LIGHT_GRAY);
                            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                            gradient.repaint();

                            Graphics2D g2 = colors2.createGraphics();
                            g2.setColor(Color.WHITE);
                            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                            graph.repaint();
                            return;
                        }

                        temp_custom_palette[temp2][0] = temp3;

                        temp_custom_palette[temp2][1] = labels[temp2].getBackground().getRed();
                        temp_custom_palette[temp2][2] = labels[temp2].getBackground().getGreen();
                        temp_custom_palette[temp2][3] = labels[temp2].getBackground().getBlue();

                        Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                        paintGradientAndGraph(c);
                    } catch (ArithmeticException ex) {
                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    } catch (NumberFormatException ex) {
                        length_label.setText("0");
                        temp_custom_palette[temp2][0] = -1;

                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
            });
            palette_colors.add(textfields[k]);
        }

        JButton palette_ok = new MyButton("Ok");
        getRootPane().setDefaultButton(palette_ok);
        palette_ok.setFocusable(false);
        palette_ok.addActionListener(e -> {
            int temp2 = 0;

            int[] temp_array = new int[temp_custom_palette.length];

            int zeroes = 0;
            for (int m = 0; m < textfields.length; m++) {
                try {
                    temp2 = Integer.parseInt(textfields[m].getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (temp2 < 0 || temp2 > 99) {
                    JOptionPane.showMessageDialog(this_frame, "The hues values must between 1 and 99,\nfor that color to be included in the palette,\nor 0 for that color not to be included.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (temp2 == 0) {
                    zeroes++;
                }

                temp_array[m] = temp2;

            }

            if (zeroes == textfields.length) {
                JOptionPane.showMessageDialog(this_frame, "You need to include at least one color.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                temp2 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp2 < 0) {
                JOptionPane.showMessageDialog(this_frame, "Offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int m = 0; m < temp_custom_palette.length; m++) {
                temp_custom_palette[m][0] = temp_array[m];
            }

            Color[] c = null;
            try {
                c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());
            } catch (Exception ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();

                return;
            }

            equal_hues = same_hues.isSelected();
            random_palette_algorithm = combo_box_random_palette_alg.getSelectedIndex();
            pastel = pastel_cb.isSelected();

            ptra2.customPaletteChanged(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex(), outcoloring_mode);

            ptra2.setEnabled(true);
            ptra2.setPalette(palette_id, null, outcoloring_mode ? 0 : 1);
            dispose();

        });


        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ok");
        getRootPane().getActionMap().put("Ok", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
               palette_ok.doClick();
            }
        });

        buttons.add(palette_ok);

        JButton seed_button = new MyButton("Seed");
        seed_button.setFocusable(false);
        seed_button.addActionListener(e -> new RandomPaletteSeedDialog(this_frame));

        buttons.add(seed_button);

        JButton palette_cancel = new MyButton("Cancel");
        palette_cancel.setFocusable(false);
        palette_cancel.addActionListener(e -> {

            ptra2.setEnabled(true);
            palette[palette_id].setSelected(color_choice == palette_id);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                palette_cancel.doClick();
            }
        });

        buttons.add(palette_cancel);


        colors = new BufferedImage(868, 36, BufferedImage.TYPE_INT_ARGB);

        colors2 = new BufferedImage(868, 145, BufferedImage.TYPE_INT_ARGB);

        Color[] c = CustomPalette.getPalette(temp_custom_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg);

        length_label = new JLabel();
        length_label.setText("" + c.length);

        try {
            Graphics2D g = colors.createGraphics();
            for (int i = 0; i < c.length; i++) {
                if (smoothing) {
                    GradientPaint gp = new GradientPaint(i * colors.getWidth() / ((float)c.length), 0, c[i], (i + 1) * colors.getWidth() / ((float)c.length), 0, c[(i + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(i * colors.getWidth() / ((double)c.length), 0, (i + 1) * colors.getWidth() /((double)c.length) - i * colors.getWidth() / ((double)c.length), colors.getHeight()));
                } else {
                    g.setColor(c[i]);
                    g.fillRect(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight());
                }
            }
        } catch (Exception ex) {
            length_label.setText("0");
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        try {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < c.length; i++) {
                g.setColor(Color.RED);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 + 5 - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getRed()), (i + 1) * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 + 5 - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getRed()));

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, 10 + colors2.getHeight() / 5 + 10, colors2.getWidth(), 10 + colors2.getHeight() / 5 + 10);

                g.setColor(Color.GREEN);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 20 + 2 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getGreen()), (i + 1) * colors2.getWidth() / c.length, 20 + 2 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getGreen()));

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, 20 + 2 * (colors2.getHeight() / 5 + 10), colors2.getWidth(), 20 + 2 * (colors2.getHeight() / 5 + 10));

                g.setColor(Color.BLUE);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 30 + 3 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getBlue()), (i + 1) * colors2.getWidth() / c.length, 30 + 3 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getBlue()));
            }
        } catch (Exception ex) {
            length_label.setText("0");
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
        }

        JPanel options_panel = new JPanel();
        options_panel.setPreferredSize(new Dimension(325, 60));
        options_panel.setLayout(new FlowLayout());
        options_panel.setBackground(MainWindow.bg_color);

        check_box_reveres_palette = new JCheckBox("Reverse Palette");
        check_box_reveres_palette.setSelected(reversed_palette);
        check_box_reveres_palette.setFocusable(false);
        check_box_reveres_palette.setToolTipText("Reverses the current palette.");
        check_box_reveres_palette.setBackground(MainWindow.bg_color);

        check_box_reveres_palette.addActionListener(e -> {
            try {
                Color[] c1 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c1);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        options_panel.add(check_box_reveres_palette);

        JLabel offset_label = new JLabel(" Palette Offset");

        options_panel.add(offset_label);

        SpinnerModel spinnerModel = new SpinnerNumberModel(temp_color_cycling_location, //initial value
                0, //min
                Integer.MAX_VALUE, //max
                1);

        offset_textfield = new JSpinner(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(offset_textfield);
        editor.getFormat().setGroupingUsed(false);
        offset_textfield.setEditor(editor);

        offset_textfield.setPreferredSize(new Dimension(70, 26));
        offset_textfield.setToolTipText("Adds an offset to the current palette.");
        ((DefaultEditor) offset_textfield.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                if (blockUpdate) {
                    return;
                }

                try {

                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        temp_color_cycling_location = -1;

                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                        return;
                    }

                    temp_color_cycling_location = temp3;

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                    paintGradientAndGraph(c);
                } catch (ArithmeticException ex) {
                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                } catch (NumberFormatException ex) {
                    temp_color_cycling_location = -1;

                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                if (blockUpdate) {
                    return;
                }

                try {

                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        temp_color_cycling_location = -1;

                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                        return;
                    }

                    temp_color_cycling_location = temp3;

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                    paintGradientAndGraph(c);
                } catch (ArithmeticException ex) {
                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                } catch (NumberFormatException ex) {
                    temp_color_cycling_location = -1;

                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                try {

                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        temp_color_cycling_location = -1;

                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                        return;
                    }

                    temp_color_cycling_location = temp3;

                    Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                    paintGradientAndGraph(c);
                } catch (ArithmeticException ex) {
                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                } catch (NumberFormatException ex) {
                    temp_color_cycling_location = -1;

                    length_label.setText("0");
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }

            }
        });

        options_panel.add(offset_textfield);

        JPanel color_space_panel = new JPanel();
        color_space_panel.setPreferredSize(new Dimension(140, 60));
        color_space_panel.setLayout(new FlowLayout());
        color_space_panel.setBackground(MainWindow.bg_color);

        String[] color_space_str = {"RGB", "HSB", "Exp", "Square", "Sqrt", "RYB", "Lab", "XYZ", "LCH_ab", "Bezier RGB", "HSL", "Luv", "LCH_uv", "OKLab", "LCH_oklab", "JzAzBz", "LCH_JzAzBz", "HSL_uv", "HPL_uv", "HWB", "Linear sRGB"};

        combo_box_color_space = new JComboBox<>(color_space_str);
        combo_box_color_space.setSelectedIndex(color_space);
        combo_box_color_space.setFocusable(false);
        combo_box_color_space.setToolTipText("Sets the color space option.");

        combo_box_color_space.addActionListener(e -> {
            try {
                Color[] c12 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c12);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        color_space_panel.add(combo_box_color_space);

        JPanel color_interp_panel = new JPanel();
        color_interp_panel.setPreferredSize(new Dimension(165, 60));
        color_interp_panel.setLayout(new FlowLayout());
        color_interp_panel.setBackground(MainWindow.bg_color);

        combo_box_color_interp = new JComboBox<>(color_interp_str);
        combo_box_color_interp.setSelectedIndex(color_interpolation);
        combo_box_color_interp.setFocusable(false);
        combo_box_color_interp.setToolTipText("Sets the color interpolation option.");

        combo_box_color_interp.addActionListener(e -> {
            try {
                Color[] c15 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c15);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        color_interp_panel.add(combo_box_color_interp);

        String[] random_palette_alg_str = {"Golden Ratio", "Waves", "Distance", "Triad", "Tetrad", "Google Material", "ColorBrewer 1", "ColorBrewer 2", "Google-ColorBrewer", "Cubehelix", "IQ-Cosines", "Perlin", "Simplex", "Perlin+Simplex", "Random Walk"};

        combo_box_random_palette_alg = new JComboBox<>(random_palette_alg_str);
        combo_box_random_palette_alg.setSelectedIndex(random_palette_algorithm);
        combo_box_random_palette_alg.setFocusable(false);
        combo_box_random_palette_alg.setToolTipText("Sets the random palette algorithm.");

        same_hues = new JCheckBox("Equal Hues");
        same_hues.setSelected(equal_hues);
        same_hues.setFocusable(false);
        same_hues.setToolTipText("Every color will have the same numbers of hues.");
        same_hues.setBackground(MainWindow.bg_color);

        pastel_cb = new JCheckBox("Pastel");
        pastel_cb.setSelected(CustomPaletteEditorFrame.pastel);
        pastel_cb.setFocusable(false);
        pastel_cb.setToolTipText("Create a random pastel palette.");
        pastel_cb.setBackground(MainWindow.bg_color);

        JPanel processing_palette = new JPanel();
        processing_palette.setPreferredSize(new Dimension(244, 60));
        processing_palette.setBackground(MainWindow.bg_color);

        scale_factor_palette_slid = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) (scale_factor_palette_val * 100.0 / 2 + 100 / 2.0));
        scale_factor_palette_slid.setPreferredSize(new Dimension(90, 35));
        scale_factor_palette_slid.setMajorTickSpacing(25);
        scale_factor_palette_slid.setMinorTickSpacing(1);
        scale_factor_palette_slid.setToolTipText("Sets the scale factor.");
        //scale_factor_palette_slid.setPaintLabels(true);
        //scale_factor_palette_slid.setSnapToTicks(true);
        scale_factor_palette_slid.setFocusable(false);
        scale_factor_palette_slid.setBackground(MainWindow.bg_color);

        if (processing_alg == MainWindow.PROCESSING_NONE || processing_alg == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS || processing_alg == MainWindow.PROCESSING_HISTOGRAM_LIGHTNESS_LAB) {
            scale_factor_palette_slid.setEnabled(false);
        }

        String[] processing_str = {"None", "Histogram Bright", "Brightness 1", "Brightness 2", "Hue 1", "Hue 2", "Saturation 1", "Saturation 2", "Red 1", "Red 2", "Green 1", "Green 2", "Blue 1", "Blue 2", "RGB 1", "RGB 2", "HSB 1", "HSB 2", "RYB 1", "RYB 2", "Histogram Light", "Brightness 3", "Hues 3", "Saturation 3", "Red 3", "Green 3", "Blue 3", "RGB 3", "HSB 3", "RYB 3"};

        combo_box_processing = new JComboBox<>(processing_str);
        combo_box_processing.setSelectedIndex(processing_alg);
        combo_box_processing.setFocusable(false);
        combo_box_processing.setToolTipText("Sets the palette processing option.");

        combo_box_processing.addActionListener(e -> {

            scale_factor_palette_slid.setEnabled(!(combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_NONE || combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS || combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_HISTOGRAM_LIGHTNESS_LAB));

            scale_factor_palette_slid.setValue((int)(scale_factor_palette_slid.getMaximum() / 2.0));

            try {
                Color[] c13 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c13);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        scale_factor_palette_slid.addChangeListener(e -> {
            try {
                Color[] c112 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c112);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        processing_palette.add(combo_box_processing);
        processing_palette.add(scale_factor_palette_slid);

        gradient = new ImageLabel(new ImageIcon(colors));
        gradient.setPreferredSize(new Dimension(colors.getWidth(), colors.getHeight()));
        gradient.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        gradient.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouse_color_label_x = (int) gradient.getMousePosition().getX();

                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    gradient.setCursor(grabbing_cursor);
                } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    gradient.setCursor(grab_cursor);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    gradient.setCursor(grab_cursor);
                } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    gradient.setCursor(grab_cursor);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        gradient.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                        gradient.setCursor(grabbing_cursor);
                    } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                        gradient.setCursor(grab_cursor);
                    }

                    int diff = (int) gradient.getMousePosition().getX() - mouse_color_label_x;

                    temp_color_cycling_location -= diff;

                    if (temp_color_cycling_location < 0) {
                        temp_color_cycling_location = 0;
                    }

                    blockUpdate = true;
                    ((DefaultEditor) offset_textfield.getEditor()).getTextField().setText("" + temp_color_cycling_location);
                    blockUpdate = false;

                    try {
                        Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                        paintGradientAndGraph(c);
                    } catch (ArithmeticException ex) {
                        length_label.setText("0");
                        Graphics2D g = colors.createGraphics();
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                        gradient.repaint();

                        Graphics2D g2 = colors2.createGraphics();
                        g2.setColor(Color.WHITE);
                        g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                        graph.repaint();
                    }

                    mouse_color_label_x = (int) gradient.getMousePosition().getX();
                } catch (Exception ex) {
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    gradient.setCursor(grab_cursor);
                    Color temp_color = new Color(colors.getRGB((int) gradient.getMousePosition().getX(), (int) gradient.getMousePosition().getY()));
                    String rr = Integer.toHexString(temp_color.getRed());
                    String bb = Integer.toHexString(temp_color.getBlue());
                    String gg = Integer.toHexString(temp_color.getGreen());

                    rr = rr.length() == 1 ? "0" + rr : rr;
                    gg = gg.length() == 1 ? "0" + gg : gg;
                    bb = bb.length() == 1 ? "0" + bb : bb;

                    gradient.setToolTipText("<html>R: " + temp_color.getRed() + " G: " + temp_color.getGreen() + " B: " + temp_color.getBlue() + "<br>"
                            + "#" + rr + gg + bb + "</html>");

                } catch (Exception ex) {
                }
            }
        });

        JButton reset_palette = new MyButton();
        reset_palette.setIcon(MainWindow.getIcon("palette_reset.png"));
        reset_palette.setFocusable(false);
        reset_palette.setToolTipText("Resets the palette and the options.");
        reset_palette.setPreferredSize(new Dimension(28, 28));

        reset_palette.addActionListener(e -> {

            blockUpdate = true;

            for (int m = 0; m < labels.length; m++) {
                temp_custom_palette[m][0] = default_editor_palette[m][0];
                temp_custom_palette[m][1] = default_editor_palette[m][1];
                temp_custom_palette[m][2] = default_editor_palette[m][2];
                temp_custom_palette[m][3] = default_editor_palette[m][3];
                labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                textfields[m].setText("" + temp_custom_palette[m][0]);
            }
            blockUpdate = false;

            combo_box_color_interp.setSelectedIndex(MainWindow.INTERPOLATION_LINEAR);
            combo_box_color_space.setSelectedIndex(MainWindow.COLOR_SPACE_RGB);
            check_box_reveres_palette.setSelected(false);
            temp_color_cycling_location = 0;

            blockUpdate = true;
            ((DefaultEditor) offset_textfield.getEditor()).getTextField().setText("" + temp_color_cycling_location);
            blockUpdate = false;

            scale_factor_palette_slid.setValue((int) (scale_factor_palette_slid.getMaximum() / 2.0));
            combo_box_processing.setSelectedIndex(MainWindow.PROCESSING_NONE);

            try {
                Color[] c14 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c14);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }

        });

        JPanel buttons_panel = new JPanel();
        buttons_panel.setBackground(MainWindow.bg_color);
        buttons_panel.setLayout(new GridLayout(2, 5));

        buttons_panel.add(reset_palette);

        JButton clear_palette = new MyButton();
        clear_palette.setIcon(MainWindow.getIcon("palette_clear.png"));
        clear_palette.setFocusable(false);
        clear_palette.setToolTipText("Clears the palette.");
        clear_palette.setPreferredSize(new Dimension(28, 28));

        clear_palette.addActionListener(e -> {
            blockUpdate = true;
            for (int m = 0; m < labels.length; m++) {
                temp_custom_palette[m][0] = 0;
                temp_custom_palette[m][1] = 0;
                temp_custom_palette[m][2] = 0;
                temp_custom_palette[m][3] = 0;
                labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                textfields[m].setText("" + temp_custom_palette[m][0]);
            }
            blockUpdate = false;

            try {
                Color[] c16 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c16);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        buttons_panel.add(clear_palette);

        JButton save_palette = new MyButton();
        save_palette.setIcon(MainWindow.getIcon("palette_save.png"));
        save_palette.setFocusable(false);
        save_palette.setToolTipText("Saves a user made palette.");
        save_palette.setPreferredSize(new Dimension(28, 28));

        save_palette.addActionListener(e -> {

            for (int m = 0; m < textfields.length; m++) {
                int temp2;
                try {
                    temp2 = Integer.parseInt(textfields[m].getText());

                    if (temp2 > 0 && temp2 < 100) {
                        temp_custom_palette[m][0] = temp2;
                    } else {
                        temp_custom_palette[m][0] = 0;
                    }
                } catch (Exception ex) {
                    temp_custom_palette[m][0] = 0;
                }
            }

            try {
                temp_color_cycling_location = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
            } catch (Exception ex) {
                temp_color_cycling_location = 0;
            }

            savePalette();

        });

        buttons_panel.add(save_palette);

        JButton load_palette = new MyButton();
        load_palette.setIcon(MainWindow.getIcon("palette_load.png"));
        load_palette.setFocusable(false);
        load_palette.setToolTipText("Loads a user made palette.");
        load_palette.setPreferredSize(new Dimension(28, 28));

        load_palette.addActionListener(e -> {

            loadPalette();
            try {
                Color[] c17 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c17);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }

        });

        buttons_panel.add(load_palette);

        JButton random_palette = new MyButton();
        random_palette.setIcon(MainWindow.getIcon("palette_random.png"));
        random_palette.setFocusable(false);
        random_palette.setToolTipText("Randomizes the palette.");
        random_palette.setPreferredSize(new Dimension(28, 28));

        random_palette.addActionListener(e -> {

            Color[] c18 = randomPalette(generator, true, temp_custom_palette, combo_box_random_palette_alg.getSelectedIndex(), same_hues.isSelected(), combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex(), pastel_cb.isSelected());

            if (c18 == null) {
                JOptionPane.showMessageDialog(this_frame, "Please select a valid palette offset.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            blockUpdate = true;
            for (int m = 0; m < labels.length; m++) {
                labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                textfields[m].setText("" + temp_custom_palette[m][0]);
            }
            blockUpdate = false;

            paintGradientAndGraph(c18);
        });

        buttons_panel.add(random_palette);

        add_palette.setIcon(MainWindow.getIcon("palette_add.png"));
        add_palette.setFocusable(false);
        add_palette.setToolTipText("Inserts an empty slot to the selected location.");
        add_palette.setPreferredSize(new Dimension(28, 28));

        add_palette.addActionListener(e -> {

            if (add_palette.isSelected()) {
                add_palette.setSelected(false);
                minus_palette.setEnabled(true);
                swap_palette.setEnabled(true);
                copy_palette.setEnabled(true);
            } else {
                add_palette.setSelected(true);
                minus_palette.setEnabled(false);
                swap_palette.setEnabled(false);
                copy_palette.setEnabled(false);
            }

        });

        buttons_panel.add(add_palette);

        minus_palette.setIcon(MainWindow.getIcon("palette_minus.png"));
        minus_palette.setFocusable(false);
        minus_palette.setToolTipText("Removes the color in the selected location.");
        minus_palette.setPreferredSize(new Dimension(28, 28));

        minus_palette.addActionListener(e -> {
            if (minus_palette.isSelected()) {
                minus_palette.setSelected(false);
                add_palette.setEnabled(true);
                swap_palette.setEnabled(true);
                copy_palette.setEnabled(true);
            } else {
                minus_palette.setSelected(true);
                add_palette.setEnabled(false);
                swap_palette.setEnabled(false);
                copy_palette.setEnabled(false);
            }
        });

        buttons_panel.add(minus_palette);
        
        copy_palette.setIcon(MainWindow.getIcon("palette_copy.png"));
        copy_palette.setFocusable(false);
        copy_palette.setToolTipText("Copies and inserts a color to the selected location.");
        copy_palette.setPreferredSize(new Dimension(28, 28));

        copy_palette.addActionListener(e -> {

            if (copy_palette.isSelected()) {
                copy_palette.setSelected(false);
                minus_palette.setEnabled(true);
                swap_palette.setEnabled(true);
                add_palette.setEnabled(true);
            } else {
                copy_palette.setSelected(true);
                minus_palette.setEnabled(false);
                swap_palette.setEnabled(false);
                add_palette.setEnabled(false);
            }

            selectedIndex = -1;
            for(int k = 0; k < textfields.length; k++) {
                textfields[k].setBackground(oldColor);
            }

        });

        buttons_panel.add(copy_palette);
        
        swap_palette.setIcon(MainWindow.getIcon("shift_palette.png"));
        swap_palette.setFocusable(false);
        swap_palette.setToolTipText("Swaps two colors.");
        swap_palette.setPreferredSize(new Dimension(28, 28));

        swap_palette.addActionListener(e -> {

            if (swap_palette.isSelected()) {
                swap_palette.setSelected(false);
                add_palette.setEnabled(true);
                minus_palette.setEnabled(true);
                copy_palette.setEnabled(true);
            } else {
                swap_palette.setSelected(true);
                add_palette.setEnabled(false);
                minus_palette.setEnabled(false);
                copy_palette.setEnabled(false);
            }

            selectedIndex = -1;
            for(int k = 0; k < textfields.length; k++) {
                textfields[k].setBackground(oldColor);
            }

        });

        buttons_panel.add(swap_palette);

        JButton color_picker = new MyButton();
        color_picker.setIcon(MainWindow.getIcon("color_picker.png"));
        color_picker.setFocusable(false);
        color_picker.setToolTipText("Picks a color from the screen and imports it to the palette.");
        color_picker.setPreferredSize(new Dimension(28, 28));

        color_picker.addActionListener(e -> {
            setEnabled(false);
            int color_picker_window_width = 440;
            int color_picker_window_height = 250;
            color_picker_frame = new JFrame("Color Picker");
            color_picker_frame.setIconImage(MainWindow.getIcon("color_picker.png").getImage());
            color_picker_frame.setSize(color_picker_window_width, color_picker_window_height);
            color_picker_frame.setLocation((int) (getLocation().getX() + getSize().getWidth() / 2) - (color_picker_window_width / 2), (int) (getLocation().getY() + getSize().getHeight() / 2) - (color_picker_window_height / 2));


            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(8, 1));
            panel1.setBackground(MainWindow.bg_color);
            panel1.add(new JLabel("Move your mouse to the desired color."));
            panel1.add(new JLabel("Press ENTER to capture the color."));
            panel1.add(new JLabel("Press ESC to cancel."));
            panel1.add(new JLabel(""));
            panel1.add(new JLabel("The first empty color slot in the palette"));
            panel1.add(new JLabel("will be used for the imported color."));
            panel1.add(new JLabel(""));

            JLabel label1 = new JLabel();
            panel1.add(label1);

            final RoundedPanel panel2 = new RoundedPanel(true, true, true, 10);
            panel2.setPreferredSize(new Dimension(120, 120));

            RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
            round_panel.setBackground(MainWindow.bg_color);
            round_panel.setPreferredSize(new Dimension(370, 160));
            round_panel.setLayout(new GridBagLayout());

            GridBagConstraints con = new GridBagConstraints();

            con.fill = GridBagConstraints.CENTER;
            con.gridx = 0;
            con.gridy = 0;

            round_panel.add(panel1, con);

            con.fill = GridBagConstraints.CENTER;
            con.gridx = 1;
            con.gridy = 0;

            round_panel.add(new JLabel("   "), con);

            con.fill = GridBagConstraints.CENTER;
            con.gridx = 2;
            con.gridy = 0;

            round_panel.add(panel2, con);

            JPanel main_panel = new JPanel();
            main_panel.setLayout(new GridBagLayout());
            con.fill = GridBagConstraints.CENTER;
            con.gridx = 0;
            con.gridy = 0;
            main_panel.add(round_panel, con);

            JScrollPane scrollPane = new JScrollPane(main_panel);
            color_picker_frame.add(scrollPane);

            final PixelColor color_picker_thread = new PixelColor(panel2, label1);

            color_picker_frame.addWindowListener(new WindowAdapter() {

                @Override
                public void windowClosing(WindowEvent e) {

                    color_picker_thread.terminate();
                    ptra2.setVisible(true);
                    setVisible(true);
                    setEnabled(true);
                    color_picker_frame.dispose();

                }
            });

            color_picker_frame.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ENTER:
                            color_picker_thread.terminate();

                            try {
                                future.get();
                            } catch (InterruptedException ex) {
                            }
                            catch (ExecutionException ex) {}

                            int m;
                            for (m = 0; m < labels.length; m++) {
                                try {
                                    if (Double.parseDouble(textfields[m].getText()) == 0) {
                                        break;
                                    }
                                } catch (Exception ex) {

                                }
                            }

                            if (m != labels.length) {
                                temp_custom_palette[m][0] = 12;
                                temp_custom_palette[m][1] = panel2.getBackground().getRed();
                                temp_custom_palette[m][2] = panel2.getBackground().getGreen();
                                temp_custom_palette[m][3] = panel2.getBackground().getBlue();

                                blockUpdate = true;
                                labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                                textfields[m].setText("" + temp_custom_palette[m][0]);
                                blockUpdate = false;

                                try {
                                    Color[] c19 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                                    paintGradientAndGraph(c19);
                                } catch (ArithmeticException ex) {
                                    length_label.setText("0");
                                    Graphics2D g = colors.createGraphics();
                                    g.setColor(Color.LIGHT_GRAY);
                                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                                    gradient.repaint();

                                    Graphics2D g2 = colors2.createGraphics();
                                    g2.setColor(Color.WHITE);
                                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                                    graph.repaint();
                                }

                                ptra2.setVisible(true);
                                setVisible(true);
                                setEnabled(true);
                                color_picker_frame.dispose();
                            } else {
                                ptra2.setVisible(true);
                                setVisible(true);
                                setEnabled(true);
                                color_picker_frame.dispose();
                                JOptionPane.showMessageDialog(this_frame, "The palette is full.", "Error!", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                        case KeyEvent.VK_ESCAPE:
                            color_picker_thread.terminate();

                            try {
                                future.get();
                            } catch (InterruptedException ex) {
                            }
                            catch (ExecutionException ex) {}

                            ptra2.setVisible(true);
                            setVisible(true);
                            setEnabled(true);
                            color_picker_frame.dispose();
                            break;
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }

            });

            ptra2.setVisible(false);
            setVisible(false);
            color_picker_frame.setVisible(true);

            future = TaskDraw.single_thread_executor.submit(color_picker_thread);
        });

        buttons_panel.add(color_picker);

        tools.add(buttons_panel);

        JMenu presets_menu = new MyMenu("Presets");
        
        int count = 0;
        for(int l = 0; l < palette.length; l++) {
            if(l < MainWindow.CUSTOM_PALETTE_ID && l != MainWindow.CUSTOM_PALETTE_ID && l != MainWindow.DIRECT_PALETTE_ID) {
                count++;
            }
        }

        String[] preset_palettes_str = new String[count + 1];
        
        palette_indexing = new int[preset_palettes_str.length];

        for (int l = 0; l < preset_palettes_str.length; l++) {
            if(l < MainWindow.CUSTOM_PALETTE_ID && l != MainWindow.CUSTOM_PALETTE_ID && l != MainWindow.DIRECT_PALETTE_ID) {
                preset_palettes_str[l + 1] = palette[l].getText();
                palette_indexing[l + 1] = l;
            }
        }

        preset_palettes_str[0] = "Editor Default";
        palette_indexing[0] = -1;

        preset_palettes = new MyMenuItem[preset_palettes_str.length];

        for (int l = 0; l < palette_indexing.length; l++) {

            if (palette_indexing[l] == -1) {
                c = CustomPalette.getPalette(default_editor_palette, color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg);
            } else {
                c = CustomPalette.getPalette(editor_default_palettes[palette_indexing[l]], color_interpolation, color_space, reversed_palette, temp_color_cycling_location, scale_factor_palette_val, processing_alg);
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

            preset_palettes[l] = new MyMenuItem(preset_palettes_str[l], new ImageIcon(palette_preview));

            final int temp = l;
            preset_palettes[l].addActionListener(e -> {
                if (temp == 0) {
                    blockUpdate = true;
                    for (int m = 0; m < labels.length; m++) {
                        temp_custom_palette[m][0] = default_editor_palette[m][0];
                        temp_custom_palette[m][1] = default_editor_palette[m][1];
                        temp_custom_palette[m][2] = default_editor_palette[m][2];
                        temp_custom_palette[m][3] = default_editor_palette[m][3];
                        labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                        textfields[m].setText("" + temp_custom_palette[m][0]);
                    }
                    blockUpdate = false;
                } else {
                    blockUpdate = true;
                    for (int m = 0; m < labels.length; m++) {
                        temp_custom_palette[m][0] = editor_default_palettes[temp - 1][m][0];
                        temp_custom_palette[m][1] = editor_default_palettes[temp - 1][m][1];
                        temp_custom_palette[m][2] = editor_default_palettes[temp - 1][m][2];
                        temp_custom_palette[m][3] = editor_default_palettes[temp - 1][m][3];
                        labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                        textfields[m].setText("" + temp_custom_palette[m][0]);
                    }
                    blockUpdate = false;
                }

                try {
                    Color[] c113 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                    paintGradientAndGraph(c113);
                } catch (ArithmeticException ex) {
                    length_label.setText("0");
                    Graphics2D g1 = colors.createGraphics();
                    g1.setColor(Color.LIGHT_GRAY);
                    g1.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient.repaint();

                    Graphics2D g2 = colors2.createGraphics();
                    g2.setColor(Color.WHITE);
                    g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                    graph.repaint();
                }
            });
            presets_menu.add(preset_palettes[l]);
        }

        JMenuBar menubar = new JMenuBar();
        menubar.add(presets_menu);

        setJMenuBar(menubar);

        check_box_preview_smooth_color = new JCheckBox("Smooth");
        check_box_preview_smooth_color.setSelected(smoothing);
        check_box_preview_smooth_color.setFocusable(false);
        check_box_preview_smooth_color.setToolTipText("Previews the palette for color smoothing.");
        check_box_preview_smooth_color.setBackground(MainWindow.bg_color);

        check_box_preview_smooth_color.addActionListener(e -> {
            try {
                Color[] c111 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c111);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(2, 1));
        p1.setBackground(MainWindow.bg_color);



        JPanel p12 = new JPanel();
        p12.setBackground(MainWindow.bg_color);
        p12.add(combo_box_random_palette_alg);

        p1.add(p12);

        JPanel p11 = new JPanel();
        p11.setBackground(MainWindow.bg_color);
        p11.add(same_hues);
        p11.add(pastel_cb);

        p1.add(p11);

        tools.add(Box.createRigidArea(new Dimension(10, 10)));

        tools.add(new JLabel("Preset: "));

        JComboBox<String> extra_presets_box = new JComboBox<>(extraPalettes.getNames());
        extra_presets_box.setToolTipText("Loads additional presets to the editor.");

        tools.add(extra_presets_box);

        extra_presets_box.addActionListener(e -> {
            blockUpdate = true;

            ArrayList<ArrayList<Integer>> palette1 = extraPalettes.getPalette(extra_presets_box.getSelectedIndex(), labels.length);

            if(palette1.size() > 32) {
                ArrayList<ArrayList<Integer>> scaledPalette = new ArrayList<>();
                double s = palette1.size() / 32.0;
                for(double k = 0; (int)(k + 0.5) < palette1.size(); k+= s) {
                    scaledPalette.add(palette1.get((int)(k + 0.5)));
                }
                palette1 = scaledPalette;
            }

            for (int m = 0; m < labels.length; m++) {
                if(m < palette1.size()) {
                    ArrayList<Integer> rgb = palette1.get(m);
                    if (rgb.size() == 3) {
                        temp_custom_palette[m][0] = 16;
                        temp_custom_palette[m][1] = rgb.get(0);
                        temp_custom_palette[m][2] = rgb.get(1);
                        temp_custom_palette[m][3] = rgb.get(2);

                    }
                    else if(rgb.size() == 4) {
                        temp_custom_palette[m][0] = rgb.get(0);
                        temp_custom_palette[m][1] = rgb.get(1);
                        temp_custom_palette[m][2] = rgb.get(2);
                        temp_custom_palette[m][3] = rgb.get(3);
                    }
                }
                else {
                    temp_custom_palette[m][0] = 0;
                    temp_custom_palette[m][1] = 0;
                    temp_custom_palette[m][2] = 0;
                    temp_custom_palette[m][3] = 0;
                }

                labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                textfields[m].setText("" + temp_custom_palette[m][0]);
            }

            blockUpdate = false;

            try {
                Color[] c110 = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

                paintGradientAndGraph(c110);
            } catch (ArithmeticException ex) {
                length_label.setText("0");
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient.repaint();

                Graphics2D g2 = colors2.createGraphics();
                g2.setColor(Color.WHITE);
                g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
                graph.repaint();
            }
        });

        tools.add(Box.createRigidArea(new Dimension(10, 10)));

        tools.add(new JLabel("Random: "));
        tools.add(p1);

        tools.add(Box.createRigidArea(new Dimension(10, 10)));
        tools.add(check_box_preview_smooth_color);

        graph = new ImageLabel(new ImageIcon(colors2));

        graph.setPreferredSize(new Dimension(colors2.getWidth(), colors2.getHeight()));
        graph.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JPanel palette_panel = new JPanel();
        palette_panel.setBackground(MainWindow.bg_color);
        palette_panel.setPreferredSize(new Dimension(890, 238));

        palette_panel.add(gradient);
        palette_panel.add(graph);
        palette_panel.add(new JLabel("Palette Length: "));
        palette_panel.add(length_label);

        tools.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Tools", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        palette_colors.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Colors/Hues", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        color_space_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Space", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        color_interp_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Color Interpolation", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        palette_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Palette", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));
        processing_palette.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Processing", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        options_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()), "Options", TitledBorder.DEFAULT_POSITION, TitledBorder.DEFAULT_POSITION));

        editor_panel.add(tools);
        editor_panel.add(palette_colors);
        editor_panel.add(options_panel);
        editor_panel.add(color_space_panel);
        editor_panel.add(color_interp_panel);
        editor_panel.add(processing_palette);
        editor_panel.add(palette_panel);

        RoundedPanel round_panel = new RoundedPanel(true, true, true, 15);
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(920, 555));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(editor_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);

    }

    private void paintGradientAndGraph(Color[] c) {

        try {
            length_label.setText("" + c.length);
            Graphics2D g = colors.createGraphics();
            for (int i = 0; i < c.length; i++) {
                if (check_box_preview_smooth_color.isSelected()) {
                    GradientPaint gp = new GradientPaint(i * colors.getWidth() / ((float)c.length), 0, c[i], (i + 1) * colors.getWidth() / ((float)c.length), 0, c[(i + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(i * colors.getWidth() / ((double)c.length), 0, (i + 1) * colors.getWidth() / ((double)c.length) - i * colors.getWidth() / ((double)c.length), colors.getHeight()));
                } else {
                    g.setColor(c[i]);
                    g.fillRect(i * colors.getWidth() / c.length, 0, (i + 1) * colors.getWidth() / c.length - i * colors.getWidth() / c.length, colors.getHeight());
                }
            }
        } catch (Exception ex) {
            length_label.setText("0");
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient.repaint();

        try {
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < c.length; i++) {
                g.setColor(Color.RED);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 + 5 - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getRed()), (i + 1) * colors2.getWidth() / c.length, 10 + colors2.getHeight() / 5 + 5 - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getRed()));

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, 10 + colors2.getHeight() / 5 + 10, colors2.getWidth(), 10 + colors2.getHeight() / 5 + 10);

                g.setColor(Color.GREEN);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 20 + 2 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getGreen()), (i + 1) * colors2.getWidth() / c.length, 20 + 2 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getGreen()));

                g.setColor(Color.LIGHT_GRAY);
                g.drawLine(0, 20 + 2 * (colors2.getHeight() / 5 + 10), colors2.getWidth(), 20 + 2 * (colors2.getHeight() / 5 + 10));

                g.setColor(Color.BLUE);
                g.drawLine(1 + i * colors2.getWidth() / c.length, 30 + 3 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[i].getBlue()), (i + 1) * colors2.getWidth() / c.length, 30 + 3 * (colors2.getHeight() / 5 + 5) - (int) (((colors2.getHeight() / 5.0) / 255.0) * c[(i + 1) % c.length].getBlue()));
            }
        } catch (Exception ex) {
            length_label.setText("0");
            Graphics2D g = colors2.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
        }

        graph.repaint();

        updateColorPalettesMenu();
    }

    private void savePalette() {

        file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);
        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Palette (*.fzp)", "fzp"));
        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("RGB Triplets (*.map)", "map"));

        String name = "palette " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".fzp";
        file_chooser.setSelectedFile(new File(name));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

            String extension = filter.getExtensions()[0];

            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

            int index = file_name.lastIndexOf(".");

            if (index != -1) {
                file_name = file_name.substring(0, index);
            }

            file_chooser.setSelectedFile(new File(file_name + "." + extension));
        });

        int returnVal = file_chooser.showDialog(this_frame, "Save Palette");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            FileNameExtensionFilter filter = (FileNameExtensionFilter) file_chooser.getFileFilter();

            String extension = filter.getExtensions()[0];

            if (extension.equalsIgnoreCase("fzp")) {
                ObjectOutputStream file_temp = null;

                try {
                    file_temp = new ObjectOutputStream(new FileOutputStream(file.toString()));
                    SettingsPalette1062 settings_palette = new SettingsPalette1062(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, combo_box_processing.getSelectedIndex(), (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0));
                    file_temp.writeObject(settings_palette);
                    file_temp.flush();
                } catch (IOException ex) {
                }

                try {
                    if(file_temp != null) {
                        file_temp.close();
                    }
                } catch (Exception ex) {
                }
            } else if (extension.equalsIgnoreCase("map")) {

                Color[] c = null;
                try {
                    c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());
                } catch (ArithmeticException ex) {
                    JOptionPane.showMessageDialog(this_frame, "The palette cannot be empty.", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                PrintWriter writer;
                try {
                    writer = new PrintWriter(file.toString());

                    for (int l = 0; l < c.length; l++) {
                        writer.println(c[l].getRed() + " " + c[l].getGreen() + " " + c[l].getBlue());
                    }

                    writer.close();

                    MainWindow.SaveSettingsPath = file.getParent();
                } catch (FileNotFoundException ex) {

                }

            }

        }

    }

    private void loadPalette() {

        file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Fractal Zoomer Palette (*.fzp)", "fzp"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(this_frame, "Load Palette");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            ObjectInputStream file_temp = null;
            try {
                file_temp = new ObjectInputStream(new FileInputStream(file.toString()));
                SettingsPalette settings_palette = (SettingsPalette) file_temp.readObject();

                int version = settings_palette.getVersion();

                temp_custom_palette = settings_palette.getCustomPalette();
                blockUpdate = true;
                for (int m = 0; m < labels.length; m++) {
                    labels[m].setBackground(new Color(temp_custom_palette[m][1], temp_custom_palette[m][2], temp_custom_palette[m][3]));
                    textfields[m].setText("" + temp_custom_palette[m][0]);
                }
                blockUpdate = false;

                combo_box_color_interp.setSelectedIndex(settings_palette.getColorInterpolation());
                combo_box_color_space.setSelectedIndex(settings_palette.getColorSpace());
                check_box_reveres_palette.setSelected(settings_palette.getReveresedPalette());
                temp_color_cycling_location = settings_palette.getOffset();

                blockUpdate = true;
                ((DefaultEditor) offset_textfield.getEditor()).getTextField().setText("" + temp_color_cycling_location);
                blockUpdate = false;

                if (version < 1062) {
                    scale_factor_palette_slid.setValue((int) (scale_factor_palette_slid.getMaximum() / 2.0));
                    combo_box_processing.setSelectedIndex(MainWindow.PROCESSING_NONE);
                } else {
                    combo_box_processing.setSelectedIndex(((SettingsPalette1062) settings_palette).getProcessingAlgorithm());

                    if (combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_NONE || combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS || combo_box_processing.getSelectedIndex() == MainWindow.PROCESSING_HISTOGRAM_LIGHTNESS_LAB) {
                        scale_factor_palette_slid.setValue((int)(scale_factor_palette_slid.getMaximum() / 2.0));
                    } else {
                        scale_factor_palette_slid.setValue((int) (((SettingsPalette1062) settings_palette).getScaleFactorPaletteValue() * scale_factor_palette_slid.getMaximum() / 2 + scale_factor_palette_slid.getMaximum() / 2.0));
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this_frame, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this_frame, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }

            try {
                if(file_temp != null) {
                    file_temp.close();
                }
            } catch (Exception ex) {
            }
        }

    }

    public static Color[] randomPalette(Random generator, boolean createFullPalette, int[][] palette, int random_palette_alg, boolean same_hues, int color_interpolation, int color_space, boolean reverse_palette, int color_cycling, double processing_val, int processing_alg, boolean pastel) {

        if (color_cycling < 0) {
            return null;
        } 
        
        Color[] c = null;

        // boolean same_colors;
        int hues = generator.nextInt(12) + 7;

        if (random_palette_alg == 0) {
            //float hue = generator.nextFloat();
            double brightness = generator.nextFloat();

            double golden_ratio_conjugate = 0.6180339887498949;//(1 + Math.sqrt(5)) / 2.0 - 1;

            //do {
            for (int m = 0; m < palette.length; m++) {
                //hue += golden_ratio_conjugate;
                //hue %= 1;
                brightness += golden_ratio_conjugate;
                brightness %= 1;

                int[] res = null;

                if (color_space == MainWindow.COLOR_SPACE_LCH_ab) {
                    res = ColorSpaceConverter.LCH_abtoRGB(brightness * 100.0, generator.nextDouble() * 140.0, generator.nextDouble() * 360.0);
                } else if (color_space == MainWindow.COLOR_SPACE_LAB) {
                    res = ColorSpaceConverter.LABtoRGB(brightness * 100.0, (2 * generator.nextDouble() - 1) * 100, (2 * generator.nextDouble() - 1) * 100);
                } else if (color_space == MainWindow.COLOR_SPACE_HSL){
                    res = ColorSpaceConverter.HSLtoRGB(generator.nextDouble(), generator.nextDouble(), brightness);
                }
                else {
                    res = ColorSpaceConverter.HSBtoRGB(generator.nextDouble(), generator.nextDouble(), brightness);
                }

                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                palette[m][3] = ColorSpaceConverter.clamp(res[2]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];

            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 1) {
            double random_a = generator.nextDouble() * 1000;
            double random_b = generator.nextDouble() * 1000;
            double random_c = generator.nextDouble() * 1000;

            double a_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
            double b_coeff = generator.nextInt(11) + generator.nextDouble() + 1;
            double c_coeff = generator.nextInt(11) + generator.nextDouble() + 1;

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;

                int[] res = null;

                if (color_space == MainWindow.COLOR_SPACE_HSB) {
                    res = ColorSpaceConverter.HSBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                } 
                else if (color_space == MainWindow.COLOR_SPACE_HSL) {
                    res = ColorSpaceConverter.HSLtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                } 
                else if (color_space == MainWindow.COLOR_SPACE_RYB) {
                    res = ColorSpaceConverter.RYBtoRGB((0.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (0.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (0.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                } else if (color_space == MainWindow.COLOR_SPACE_LAB) {
                    res = ColorSpaceConverter.LABtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (100 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b))), (100 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c))));
                } else if (color_space == MainWindow.COLOR_SPACE_XYZ) {
                    res = ColorSpaceConverter.XYZtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (50 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (50 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                } else if (color_space == MainWindow.COLOR_SPACE_LCH_ab) {
                    res = ColorSpaceConverter.LCH_abtoRGB((50 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1)), (70 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1)), (180 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1)));
                } else {
                    res = new int[3];
                    res[0] = (int) (127.5 * (Math.sin(Math.PI / a_coeff * (m + 1) + random_a) + 1) + 0.5);
                    res[1] = (int) (127.5 * (Math.sin(Math.PI / b_coeff * (m + 1) + random_b) + 1) + 0.5);
                    res[2] = (int) (127.5 * (Math.sin(Math.PI / c_coeff * (m + 1) + random_c) + 1) + 0.5);
                }

                palette[m][1] = ColorSpaceConverter.clamp(res[0]);
                palette[m][2] = ColorSpaceConverter.clamp(res[1]);
                palette[m][3] = ColorSpaceConverter.clamp(res[2]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 2) {
            List<Color> list = ColorGenerator.generate(600, generator.nextInt(600), 0);
            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[m][1] = list.get(m).getRed();
                palette[m][2] = list.get(m).getGreen();
                palette[m][3] = list.get(m).getBlue();

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 3) {

            double hue, sat, bright;

            hue = generator.nextDouble();
            sat = generator.nextDouble();
            bright = generator.nextDouble();

            int cnt = 0;

            double hue_distance = (generator.nextInt(3) + 3) / 12.0;

            int[] res;

            for (int l = 0; l < 11; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB(hue, (sat + 1.0 / 11 * l) % 1.0, (bright + 1.0 / 11 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for (int l = 0; l < 10; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB((hue + hue_distance) % 1.0, (sat + 1.0 / 10 * l) % 1.0, (bright + 1.0 / 10 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for (int l = 0; l < 11; cnt++, l++) {

                double temp = hue - hue_distance < 0 ? 1 - (hue - hue_distance) : hue - hue_distance;
                res = ColorSpaceConverter.HSBtoRGB((temp) % 1.0, (sat + 1.0 / 11 * l) % 1.0, (bright + 1.0 / 11 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for(int m = 0; m < 32; m++) {
                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 4) {

            double hue, sat, bright;

            hue = generator.nextDouble();
            sat = generator.nextDouble();
            bright = generator.nextDouble();

            int cnt = 0;

            double hue_distance = (generator.nextInt(3) + 1) / 12.0;

            int[] res;

            for (int l = 0; l < 8; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB(hue, (sat + 1.0 / 8 * l) % 1.0, (bright + 1.0 / 8 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for (int l = 0; l < 8; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB((hue + 0.5) % 1.0, (sat + 1.0 / 8 * l) % 1.0, (bright + 1.0 / 8 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for (int l = 0; l < 8; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB((hue + 0.5 + hue_distance) % 1.0, (sat + 1.0 / 8 * l) % 1.0, (bright + 1.0 / 8 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for (int l = 0; l < 8; cnt++, l++) {

                res = ColorSpaceConverter.HSBtoRGB((hue + hue_distance) % 1.0, (sat + 1.0 / 8 * l) % 1.0, (bright + 1.0 / 8 * l) % 1.0);
                palette[cnt][0] = same_hues ? hues : generator.nextInt(12) + 7;
                palette[cnt][1] = res[0];
                palette[cnt][2] = res[1];
                palette[cnt][3] = res[2];
            }

            for(int m = 0; m < 32; m++) {
                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 5) {
            Color[] col = GoogleMaterialDesignPalette.generate(palette.length);

            for (int m = 0; m < palette.length; m++) {

                if (m >= col.length || col[m] == null) {
                    palette[m][0] = 0;
                    palette[m][1] = 0;
                    palette[m][2] = 0;
                    palette[m][3] = 0;
                } else {
                    palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                    palette[m][1] = col[m].getRed();
                    palette[m][2] = col[m].getGreen();
                    palette[m][3] = col[m].getBlue();
                }

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];

            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 6) {

            Color[] col = ColorBrewerPalette.generate2(palette.length);
            for (int m = 0; m < palette.length; m++) {

                if (m >= col.length || col[m] == null) {
                    palette[m][0] = 0;
                    palette[m][1] = 0;
                    palette[m][2] = 0;
                    palette[m][3] = 0;
                } else {
                    palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                    palette[m][1] = col[m].getRed();
                    palette[m][2] = col[m].getGreen();
                    palette[m][3] = col[m].getBlue();
                }

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];

            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 7) {

            Color[] col = ColorBrewerPalette.generate(palette.length);
            for (int m = 0; m < palette.length; m++) {

                if (m >= col.length || col[m] == null) {
                    palette[m][0] = 0;
                    palette[m][1] = 0;
                    palette[m][2] = 0;
                    palette[m][3] = 0;
                } else {
                    palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                    palette[m][1] = col[m].getRed();
                    palette[m][2] = col[m].getGreen();
                    palette[m][3] = col[m].getBlue();
                }

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];

            }

            if(createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 8) {

            Color[] col = MixedGoogleColorBrewerPalette.generate(palette.length);
            for (int m = 0; m < palette.length; m++) {

                if (m >= col.length || col[m] == null) {
                    palette[m][0] = 0;
                    palette[m][1] = 0;
                    palette[m][2] = 0;
                    palette[m][3] = 0;
                } else {
                    palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                    palette[m][1] = col[m].getRed();
                    palette[m][2] = col[m].getGreen();
                    palette[m][3] = col[m].getBlue();
                }

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];

            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }
        else if (random_palette_alg == 9) {

            double start = generator.nextDouble() * 9;
            double rotation = generator.nextDouble() * 10 - 5;

            double gamma = 0;

            if (generator.nextDouble() <= 0.2) {
                gamma = 1;
            } else {
                gamma = 0.1 + generator.nextDouble() * 0.9;
            }

            double min_sat = 0;
            double max_sat = 0;

            if (generator.nextDouble() <= 0.5) {
                double a = generator.nextDouble() * 3;
                double b = generator.nextDouble() * 3;
                min_sat = Math.min(a, b);
                max_sat = Math.max(a, b);
            } else {
                min_sat = max_sat = generator.nextDouble() * 3;
            }

            ArrayList<int[]> colors = Cubehelix.makePalette(start, rotation, gamma, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, min_sat, max_sat, 0.0, 1.0, palette.length, false);

            for (int m = 0; m < colors.size(); m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;
                int[] res = colors.get(m);
                palette[m][1] = res[0];
                palette[m][2] = res[1];
                palette[m][3] = res[2];


                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }
        else if (random_palette_alg == 10) {

            double red_sum = generator.nextDouble() + 0.4;
            double green_sum = generator.nextDouble() + 0.4;
            double blue_sum = generator.nextDouble() + 0.4;

            if(red_sum > 1) {
                red_sum = 1;
            }

            if(green_sum > 1) {
                green_sum = 1;
            }

            if(blue_sum > 1) {
                blue_sum = 1;
            }


            double red_percent = generator.nextDouble();
            double green_percent = generator.nextDouble();
            double blue_percent = generator.nextDouble();

            double a_red = red_sum * red_percent;
            double a_green = green_sum * green_percent;
            double a_blue = blue_sum * blue_percent;

            double b_red = red_sum * (1 - red_percent);
            double b_green = green_sum * (1 - green_percent);
            double b_blue = green_sum * (1 - blue_percent);

            double c_red = generator.nextDouble() * 2;
            double c_green = generator.nextDouble() * 2;
            double c_blue = generator.nextDouble() * 2;

            double d_red = generator.nextDouble() * 2;
            double d_green = generator.nextDouble() * 2;
            double d_blue = generator.nextDouble() * 2;

            double g_red = generator.nextDouble() * 2;
            double g_green = generator.nextDouble() * 2;
            double g_blue = generator.nextDouble() * 2;

            double min_red = Double.MAX_VALUE;
            double min_green = Double.MAX_VALUE;
            double min_blue = Double.MAX_VALUE;

            double max_red = Double.MIN_VALUE;
            double max_green = Double.MIN_VALUE;
            double max_blue = Double.MIN_VALUE;

            double[] red_vals = new double[palette.length];
            double[] green_vals = new double[palette.length];
            double[] blue_vals = new double[palette.length];

            for (int m = 0; m < palette.length; m++) {

                double t = ((double) m / palette.length);
                red_vals[m] = (a_red + b_red * Math.cos(Math.PI * 2 * (c_red * t + d_red) + g_red));
                green_vals[m] = (a_green + b_green * Math.cos(Math.PI * 2 * (c_green * t + d_green) + g_green));
                blue_vals[m] = (a_blue + b_blue * Math.cos(Math.PI * 2 * (c_blue * t + d_blue) + g_blue));
            }

            for (int m = 0; m < palette.length; m++) {
                if(red_vals[m] < min_red) {
                    min_red = red_vals[m];
                }

                if(green_vals[m] < min_green) {
                    min_green = green_vals[m];
                }

                if(blue_vals[m] < min_blue) {
                    min_blue = blue_vals[m];
                }

                if(red_vals[m] > max_red) {
                    max_red = red_vals[m];
                }

                if(green_vals[m] > max_green) {
                    max_green = green_vals[m];
                }

                if(blue_vals[m] > max_blue) {
                    max_blue = blue_vals[m];
                }
            }

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;


                palette[m][1] = (int) (255 * (red_vals[m] - min_red)/(max_red - min_red) + 0.5);
                palette[m][2] = (int) (255 * (green_vals[m] - min_green)/(max_green - min_green)+ 0.5);
                palette[m][3] = (int) (255 * (blue_vals[m] - min_blue)/(max_blue - min_blue) + 0.5);


                palette[m][1] = ColorSpaceConverter.clamp(palette[m][1]);
                palette[m][2] = ColorSpaceConverter.clamp(palette[m][2]);
                palette[m][3] = ColorSpaceConverter.clamp(palette[m][3]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        } else if (random_palette_alg == 11) {

            double a = 0, incr = (Math.PI * 2) / palette.length;

            Random r = new Random();

            PerlinNoiseGenerator gen_red = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();

            PerlinNoiseGenerator gen_green = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();

            PerlinNoiseGenerator gen_blue = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();

            double noise_max_red = r.nextDouble() * 3 + 0.2;
            double noise_max_green = r.nextDouble() * 3 + 0.2;
            double noise_max_blue = r.nextDouble() * 3 + 0.2;

            double phase_red = r.nextDouble() * 10;
            double phase_green = r.nextDouble() * 10;
            double phase_blue = r.nextDouble() * 10;

            double[] noise_red = new double[palette.length];
            double[] noise_green = new double[palette.length];
            double[] noise_blue = new double[palette.length];

            for (int m = 0; m < palette.length; m++, a += incr) {
                double xoffset_red = (Math.cos(a + phase_red) + 1) * noise_max_red;
                double yoffset_red = (Math.sin(a + phase_red) + 1) * noise_max_red;

                double xoffset_green = (Math.cos(a + phase_green) + 1) * noise_max_green;
                double yoffset_green = (Math.sin(a + phase_green) + 1) * noise_max_green;

                double xoffset_blue = (Math.cos(a + phase_blue) + 1) * noise_max_blue;
                double yoffset_blue = (Math.sin(a + phase_blue) + 1) * noise_max_blue;

                noise_red[m] =  gen_red.evaluateNoise( xoffset_red, yoffset_red);
                noise_green[m] = gen_green.evaluateNoise( xoffset_green, yoffset_green);
                noise_blue[m] = gen_blue.evaluateNoise( xoffset_blue, yoffset_blue);
            }

            double min_red = Double.MAX_VALUE;
            double min_green = Double.MAX_VALUE;
            double min_blue = Double.MAX_VALUE;

            double max_red = Double.MIN_VALUE;
            double max_green = Double.MIN_VALUE;
            double max_blue = Double.MIN_VALUE;

            for (int m = 0; m < palette.length; m++) {
                if(noise_red[m] < min_red) {
                    min_red = noise_red[m];
                }

                if(noise_green[m] < min_green) {
                    min_green = noise_green[m];
                }

                if(noise_blue[m] < min_blue) {
                    min_blue = noise_blue[m];
                }

                if(noise_red[m] > max_red) {
                    max_red = noise_red[m];
                }

                if(noise_green[m] > max_green) {
                    max_green = noise_green[m];
                }

                if(noise_blue[m] > max_blue) {
                    max_blue = noise_blue[m];
                }
            }

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;


                palette[m][1] = (int) (255 * (noise_red[m] - min_red) / (max_red - min_red) + 0.5);
                palette[m][2] = (int) (255 * (noise_green[m] - min_green) / (max_green - min_green) + 0.5);
                palette[m][3] = (int) (255 * (noise_blue[m] - min_blue) / (max_blue - min_blue) + 0.5);

                palette[m][1] = ColorSpaceConverter.clamp(palette[m][1]);
                palette[m][2] = ColorSpaceConverter.clamp(palette[m][2]);
                palette[m][3] = ColorSpaceConverter.clamp(palette[m][3]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }
        else if (random_palette_alg == 12) {

            double a = 0, incr = (Math.PI * 2) / palette.length;

            Random r = new Random();

            FastSimplexNoiseGenerator gen_red = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            FastSimplexNoiseGenerator gen_green = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            FastSimplexNoiseGenerator gen_blue = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            double noise_max_red = r.nextDouble() * 3 + 0.2;
            double noise_max_green = r.nextDouble() * 3 + 0.2;
            double noise_max_blue = r.nextDouble() * 3 + 0.2;

            double phase_red = r.nextDouble() * 10;
            double phase_green = r.nextDouble() * 10;
            double phase_blue = r.nextDouble() * 10;

            double[] noise_red = new double[palette.length];
            double[] noise_green = new double[palette.length];
            double[] noise_blue = new double[palette.length];

            for (int m = 0; m < palette.length; m++, a += incr) {
                double xoffset_red = (Math.cos(a + phase_red) + 1) * noise_max_red;
                double yoffset_red = (Math.sin(a + phase_red) + 1) * noise_max_red;

                double xoffset_green = (Math.cos(a + phase_green) + 1) * noise_max_green;
                double yoffset_green = (Math.sin(a + phase_green) + 1) * noise_max_green;

                double xoffset_blue = (Math.cos(a + phase_blue) + 1) * noise_max_blue;
                double yoffset_blue = (Math.sin(a + phase_blue) + 1) * noise_max_blue;

                noise_red[m] =  gen_red.evaluateNoise( xoffset_red, yoffset_red);
                noise_green[m] = gen_green.evaluateNoise( xoffset_green, yoffset_green);
                noise_blue[m] = gen_blue.evaluateNoise( xoffset_blue, yoffset_blue);
            }

            double min_red = Double.MAX_VALUE;
            double min_green = Double.MAX_VALUE;
            double min_blue = Double.MAX_VALUE;

            double max_red = Double.MIN_VALUE;
            double max_green = Double.MIN_VALUE;
            double max_blue = Double.MIN_VALUE;

            for (int m = 0; m < palette.length; m++) {
                if(noise_red[m] < min_red) {
                    min_red = noise_red[m];
                }

                if(noise_green[m] < min_green) {
                    min_green = noise_green[m];
                }

                if(noise_blue[m] < min_blue) {
                    min_blue = noise_blue[m];
                }

                if(noise_red[m] > max_red) {
                    max_red = noise_red[m];
                }

                if(noise_green[m] > max_green) {
                    max_green = noise_green[m];
                }

                if(noise_blue[m] > max_blue) {
                    max_blue = noise_blue[m];
                }
            }

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;


                palette[m][1] = (int) (255 * (noise_red[m] - min_red) / (max_red - min_red) + 0.5);
                palette[m][2] = (int) (255 * (noise_green[m] - min_green) / (max_green - min_green) + 0.5);
                palette[m][3] = (int) (255 * (noise_blue[m] - min_blue) / (max_blue - min_blue) + 0.5);

                palette[m][1] = ColorSpaceConverter.clamp(palette[m][1]);
                palette[m][2] = ColorSpaceConverter.clamp(palette[m][2]);
                palette[m][3] = ColorSpaceConverter.clamp(palette[m][3]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }
        else if (random_palette_alg == 13) {

            double a = 0, incr = (Math.PI * 2) / palette.length;

            Random r = new Random();

            FastSimplexNoiseGenerator gen_red = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            FastSimplexNoiseGenerator gen_green = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            FastSimplexNoiseGenerator gen_blue = FastSimplexNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).build();

            PerlinNoiseGenerator gen_red2 = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();

            PerlinNoiseGenerator gen_green2 = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();

            PerlinNoiseGenerator gen_blue2 = PerlinNoiseGenerator.newBuilder().setSeed(r.nextInt(1000000)).setInterpolation(Interpolation.LINEAR).setFadeFunction(FadeFunction.QUINTIC_POLY).build();


            double noise_max_red = r.nextDouble() * 3 + 0.2;
            double noise_max_green = r.nextDouble() * 3 + 0.2;
            double noise_max_blue = r.nextDouble() * 3 + 0.2;

            double phase_red = r.nextDouble() * 10;
            double phase_green = r.nextDouble() * 10;
            double phase_blue = r.nextDouble() * 10;

            double noise_max_red2 = r.nextDouble() * 3 + 0.2;
            double noise_max_green2 = r.nextDouble() * 3 + 0.2;
            double noise_max_blue2 = r.nextDouble() * 3 + 0.2;

            double phase_red2 = r.nextDouble() * 10;
            double phase_green2 = r.nextDouble() * 10;
            double phase_blue2 = r.nextDouble() * 10;

            double[] noise_red = new double[palette.length];
            double[] noise_green = new double[palette.length];
            double[] noise_blue = new double[palette.length];

            for (int m = 0; m < palette.length; m++, a += incr) {
                double xoffset_red = (Math.cos(a + phase_red) + 1) * noise_max_red;
                double yoffset_red = (Math.sin(a + phase_red) + 1) * noise_max_red;

                double xoffset_green = (Math.cos(a + phase_green) + 1) * noise_max_green;
                double yoffset_green = (Math.sin(a + phase_green) + 1) * noise_max_green;

                double xoffset_blue = (Math.cos(a + phase_blue) + 1) * noise_max_blue;
                double yoffset_blue = (Math.sin(a + phase_blue) + 1) * noise_max_blue;

                double xoffset_red2 = (Math.cos(a + phase_red2) + 1) * noise_max_red2;
                double yoffset_red2 = (Math.sin(a + phase_red2) + 1) * noise_max_red2;

                double xoffset_green2 = (Math.cos(a + phase_green2) + 1) * noise_max_green2;
                double yoffset_green2 = (Math.sin(a + phase_green2) + 1) * noise_max_green2;

                double xoffset_blue2 = (Math.cos(a + phase_blue2) + 1) * noise_max_blue2;
                double yoffset_blue2 = (Math.sin(a + phase_blue2) + 1) * noise_max_blue2;


                noise_red[m] =  (gen_red.evaluateNoise( xoffset_red, yoffset_red) + gen_red2.evaluateNoise( xoffset_red2, yoffset_red2)) / 2;
                noise_green[m] = (gen_green.evaluateNoise( xoffset_green, yoffset_green) + gen_green2.evaluateNoise( xoffset_green2, yoffset_green2)) / 2;
                noise_blue[m] = (gen_blue.evaluateNoise( xoffset_blue, yoffset_blue) + gen_blue2.evaluateNoise( xoffset_blue2, yoffset_blue2)) / 2;
            }

            double min_red = Double.MAX_VALUE;
            double min_green = Double.MAX_VALUE;
            double min_blue = Double.MAX_VALUE;

            double max_red = Double.MIN_VALUE;
            double max_green = Double.MIN_VALUE;
            double max_blue = Double.MIN_VALUE;

            for (int m = 0; m < palette.length; m++) {
                if(noise_red[m] < min_red) {
                    min_red = noise_red[m];
                }

                if(noise_green[m] < min_green) {
                    min_green = noise_green[m];
                }

                if(noise_blue[m] < min_blue) {
                    min_blue = noise_blue[m];
                }

                if(noise_red[m] > max_red) {
                    max_red = noise_red[m];
                }

                if(noise_green[m] > max_green) {
                    max_green = noise_green[m];
                }

                if(noise_blue[m] > max_blue) {
                    max_blue = noise_blue[m];
                }
            }

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;


                palette[m][1] = (int) (255 * (noise_red[m] - min_red) / (max_red - min_red) + 0.5);
                palette[m][2] = (int) (255 * (noise_green[m] - min_green) / (max_green - min_green) + 0.5);
                palette[m][3] = (int) (255 * (noise_blue[m] - min_blue) / (max_blue - min_blue) + 0.5);

                palette[m][1] = ColorSpaceConverter.clamp(palette[m][1]);
                palette[m][2] = ColorSpaceConverter.clamp(palette[m][2]);
                palette[m][3] = ColorSpaceConverter.clamp(palette[m][3]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }
        else if(random_palette_alg == 14) {
            Random r = new Random();
            int red = r.nextInt(256);
            int green = r.nextInt(256);
            int blue = r.nextInt(256);

            int [] vals = new int[] {30, 15, 20, 10, 25};

            for (int m = 0; m < palette.length; m++) {
                palette[m][0] = same_hues ? hues : generator.nextInt(12) + 7;

                int val = vals[r.nextInt(vals.length)];

                red += r.nextBoolean() ? val : -val;
                green += r.nextBoolean() ? val : -val;
                blue += r.nextBoolean() ? val : -val;

                if(red < 0) {
                    red += 2*val;
                }

                if(green < 0) {
                    green += 2*val;
                }

                if(blue < 0) {
                    blue += 2*val;
                }

                if(red > 255) {
                    red -= 2*val;
                }

                if(green > 255) {
                    green -= 2*val;
                }

                if(blue > 255) {
                    blue -= 2*val;
                }

                palette[m][1] = red;
                palette[m][2] = green;
                palette[m][3] = blue;

                palette[m][1] = ColorSpaceConverter.clamp(palette[m][1]);
                palette[m][2] = ColorSpaceConverter.clamp(palette[m][2]);
                palette[m][3] = ColorSpaceConverter.clamp(palette[m][3]);

                palette[m][1] = pastel ? ColorSpaceConverter.pastel(palette[m][1], 0) :  palette[m][1];
                palette[m][2] = pastel ? ColorSpaceConverter.pastel(palette[m][2], 1) :  palette[m][2];
                palette[m][3] = pastel ? ColorSpaceConverter.pastel(palette[m][3], 2) :  palette[m][3];
            }

            if (createFullPalette) {
                c = CustomPalette.getPalette(palette, color_interpolation, color_space, reverse_palette, color_cycling, processing_val, processing_alg);
            }
        }

        return c;

    }

    private void updateColorPalettesMenu() {

        for (int l = 0; l < preset_palettes.length; l++) {

            Color[] c = null;

            if (l == 0) { // the current activated palette
                c = CustomPalette.getPalette(default_editor_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());
            } else {
                c = CustomPalette.getPalette(editor_default_palettes[l - 1], combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());
            }

            BufferedImage palette_preview = new BufferedImage(250, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = palette_preview.createGraphics();
            for (int j = 0; j < c.length; j++) {
                if (check_box_preview_smooth_color.isSelected()) {
                    GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                    g.setPaint(gp);
                    g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
                } else {
                    g.setColor(c[j]);
                    g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
                }
            }

            preset_palettes[l].setIcon(new ImageIcon(palette_preview));
        }

    }

    public void colorChanged(int id) {

        temp_custom_palette[id][1] = labels[id].getBackground().getRed();
        temp_custom_palette[id][2] = labels[id].getBackground().getGreen();
        temp_custom_palette[id][3] = labels[id].getBackground().getBlue();

        try {
            Color[] c = CustomPalette.getPalette(temp_custom_palette, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp_color_cycling_location, (scale_factor_palette_slid.getValue() - scale_factor_palette_slid.getMaximum() / 2.0) / (scale_factor_palette_slid.getMaximum() / 2.0), combo_box_processing.getSelectedIndex());

            paintGradientAndGraph(c);
        } catch (ArithmeticException ex) {
            length_label.setText("0");
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
            gradient.repaint();

            Graphics2D g2 = colors2.createGraphics();
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, colors2.getWidth(), colors2.getHeight());
            graph.repaint();
        }
    }
}
