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

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrkalona2
 */
public class FiltersMenu extends MyMenu {
	private static final long serialVersionUID = 2385174283291846647L;
	private JMenu detail_filters_menu;
    private JMenu color_filters_menu;
    private JMenu texture_filters_menu;
    private JMenu light_filters_menu;
    private JCheckBoxMenuItem[] filters_opt;
    private MainWindow ptr;
    private static ArrayList<String> detail_list;
    private static ArrayList<String> color_list;
    private static ArrayList<String> texture_list;
    private static ArrayList<String> lighting_list;

    public static final String[] filterNames;

    static {
        filterNames = new String[MainWindow.TOTAL_FILTERS];
        filterNames[MainWindow.ANTIALIASING] = "Anti-Aliasing";
        filterNames[MainWindow.EDGE_DETECTION] = "Edge Detection";
        filterNames[MainWindow.EMBOSS] = "Emboss";
        filterNames[MainWindow.SHARPNESS] = "Sharpness";
        filterNames[MainWindow.INVERT_COLORS] = "Inverted Colors";
        filterNames[MainWindow.BLURRING] = "Blurring";
        filterNames[MainWindow.COLOR_CHANNEL_MASKING] = "Mask Color Channel";
        filterNames[MainWindow.FADE_OUT] = "Fade Out";
        filterNames[MainWindow.COLOR_CHANNEL_SWAPPING] = "Color Channel Swapping";
        filterNames[MainWindow.CONTRAST_BRIGHTNESS] = "Contrast/Brightness";
        filterNames[MainWindow.GRAYSCALE] = "Grayscale";
        filterNames[MainWindow.COLOR_TEMPERATURE] = "Color Temperature";
        filterNames[MainWindow.COLOR_CHANNEL_SWIZZLING] = "Color Channel Swizzling";
        filterNames[MainWindow.HISTOGRAM_EQUALIZATION] = "Histogram Equalization";
        filterNames[MainWindow.POSTERIZE] = "Posterization";
        filterNames[MainWindow.SOLARIZE] = "Solarization";
        filterNames[MainWindow.COLOR_CHANNEL_ADJUSTING] = "Color Channel Adjusting";
        filterNames[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] = "Color Channel HSB Adjusting";
        filterNames[MainWindow.DITHER] = "Dither";
        filterNames[MainWindow.GAIN] = "Gain/Bias";
        filterNames[MainWindow.GAMMA] = "Gamma";
        filterNames[MainWindow.EXPOSURE] = "Exposure";
        filterNames[MainWindow.CRYSTALLIZE] = "Crystallize";
        filterNames[MainWindow.POINTILLIZE] = "Pointillize";
        filterNames[MainWindow.OIL] = "Oil";
        filterNames[MainWindow.MARBLE] = "Marble";
        filterNames[MainWindow.WEAVE] = "Weave";
        filterNames[MainWindow.SPARKLE] = "Sparkle";
        filterNames[MainWindow.GLOW] = "Glow";
        filterNames[MainWindow.COLOR_CHANNEL_SCALING] = "Color Channel Scaling";
        filterNames[MainWindow.NOISE] = "Noise";
        filterNames[MainWindow.COLOR_CHANNEL_MIXING] = "Color Channel Mixing";
        filterNames[MainWindow.LIGHT_EFFECTS] = "Light Effects";
        filterNames[MainWindow.EDGE_DETECTION2] = "Edge Detection 2";
        filterNames[MainWindow.MIRROR] = "Mirror";
    }

    public FiltersMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;

        detail_filters_menu = new MyMenu("Details");
        detail_filters_menu.setIcon(MainWindow.getIcon("filter_details.png"));
        color_filters_menu = new MyMenu("Colors");
        color_filters_menu.setIcon(MainWindow.getIcon("filter_colors.png"));
        texture_filters_menu = new MyMenu("Texture");
        texture_filters_menu.setIcon(MainWindow.getIcon("filter_texture.png"));
        light_filters_menu = new MyMenu("Lighting");
        light_filters_menu.setIcon(MainWindow.getIcon("filter_lighting.png"));

        filters_opt = new MyCheckBoxMenuItem[filterNames.length];

        filters_opt[MainWindow.ANTIALIASING] = new MyCheckBoxMenuItem(filterNames[MainWindow.ANTIALIASING]);
        filters_opt[MainWindow.EDGE_DETECTION] = new MyCheckBoxMenuItem(filterNames[MainWindow.EDGE_DETECTION]);
        filters_opt[MainWindow.EDGE_DETECTION2] = new MyCheckBoxMenuItem(filterNames[MainWindow.EDGE_DETECTION2]);
        filters_opt[MainWindow.SHARPNESS] = new MyCheckBoxMenuItem(filterNames[MainWindow.SHARPNESS]);
        filters_opt[MainWindow.BLURRING] = new MyCheckBoxMenuItem(filterNames[MainWindow.BLURRING]);
        filters_opt[MainWindow.EMBOSS] = new MyCheckBoxMenuItem(filterNames[MainWindow.EMBOSS]);
        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION] = new MyCheckBoxMenuItem(filterNames[MainWindow.HISTOGRAM_EQUALIZATION]);
        filters_opt[MainWindow.CONTRAST_BRIGHTNESS] = new MyCheckBoxMenuItem(filterNames[MainWindow.CONTRAST_BRIGHTNESS]);
        filters_opt[MainWindow.COLOR_TEMPERATURE] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_TEMPERATURE]);
        filters_opt[MainWindow.POSTERIZE] = new MyCheckBoxMenuItem(filterNames[MainWindow.POSTERIZE]);
        filters_opt[MainWindow.SOLARIZE] = new MyCheckBoxMenuItem(filterNames[MainWindow.SOLARIZE]);
        filters_opt[MainWindow.FADE_OUT] = new MyCheckBoxMenuItem(filterNames[MainWindow.FADE_OUT]);
        filters_opt[MainWindow.INVERT_COLORS] = new MyCheckBoxMenuItem(filterNames[MainWindow.INVERT_COLORS]);
        filters_opt[MainWindow.COLOR_CHANNEL_MASKING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_MASKING]);
        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_SWAPPING]);
        filters_opt[MainWindow.COLOR_CHANNEL_SWIZZLING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_SWIZZLING]);
        filters_opt[MainWindow.COLOR_CHANNEL_ADJUSTING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_ADJUSTING]);
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]);
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_MIXING]);
        filters_opt[MainWindow.GRAYSCALE] = new MyCheckBoxMenuItem(filterNames[MainWindow.GRAYSCALE]);
        filters_opt[MainWindow.DITHER] = new MyCheckBoxMenuItem(filterNames[MainWindow.DITHER]);
        filters_opt[MainWindow.GAIN] = new MyCheckBoxMenuItem(filterNames[MainWindow.GAIN]);
        filters_opt[MainWindow.GAMMA] = new MyCheckBoxMenuItem(filterNames[MainWindow.GAMMA]);
        filters_opt[MainWindow.EXPOSURE] = new MyCheckBoxMenuItem(filterNames[MainWindow.EXPOSURE]);
        filters_opt[MainWindow.CRYSTALLIZE] = new MyCheckBoxMenuItem(filterNames[MainWindow.CRYSTALLIZE]);
        filters_opt[MainWindow.POINTILLIZE] = new MyCheckBoxMenuItem(filterNames[MainWindow.POINTILLIZE]);
        filters_opt[MainWindow.OIL] = new MyCheckBoxMenuItem(filterNames[MainWindow.OIL]);
        filters_opt[MainWindow.MARBLE] = new MyCheckBoxMenuItem(filterNames[MainWindow.MARBLE]);
        filters_opt[MainWindow.WEAVE] = new MyCheckBoxMenuItem(filterNames[MainWindow.WEAVE]);
        filters_opt[MainWindow.SPARKLE] = new MyCheckBoxMenuItem(filterNames[MainWindow.SPARKLE]);
        filters_opt[MainWindow.GLOW] = new MyCheckBoxMenuItem(filterNames[MainWindow.GLOW]);
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING] = new MyCheckBoxMenuItem(filterNames[MainWindow.COLOR_CHANNEL_SCALING]);
        filters_opt[MainWindow.NOISE] = new MyCheckBoxMenuItem(filterNames[MainWindow.NOISE]);
        filters_opt[MainWindow.LIGHT_EFFECTS] = new MyCheckBoxMenuItem(filterNames[MainWindow.LIGHT_EFFECTS]);
        filters_opt[MainWindow.MIRROR] = new MyCheckBoxMenuItem(filterNames[MainWindow.MIRROR]);

        filters_opt[MainWindow.ANTIALIASING].setToolTipText("Smooths the jagged look of the image.");
        filters_opt[MainWindow.EDGE_DETECTION].setToolTipText("Detects the edges of the image.");
        filters_opt[MainWindow.EDGE_DETECTION2].setToolTipText("Detects the edges of the image.");
        filters_opt[MainWindow.SHARPNESS].setToolTipText("Makes the edges of the image more sharp.");
        filters_opt[MainWindow.EMBOSS].setToolTipText("Raises the light colored areas and carves the dark ones, using gray scale.");
        filters_opt[MainWindow.INVERT_COLORS].setToolTipText("Inverts the colors of the image.");
        filters_opt[MainWindow.BLURRING].setToolTipText("Blurs the image.");
        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION].setToolTipText("Performs histogram equalization on the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_MASKING].setToolTipText("Removes a color channel of the image.");
        filters_opt[MainWindow.FADE_OUT].setToolTipText("Applies a fade out effect to the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING].setToolTipText("Swaps the color channels of the image.");
        filters_opt[MainWindow.CONTRAST_BRIGHTNESS].setToolTipText("Changes the contrast/brightness of the image.");
        filters_opt[MainWindow.GRAYSCALE].setToolTipText("Converts the image to grayscale.");
        filters_opt[MainWindow.COLOR_TEMPERATURE].setToolTipText("Changes the color temperature of the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_SWIZZLING].setToolTipText("Swizzles the color channels of the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].setToolTipText("Adjusts the HSB color channels of the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_ADJUSTING].setToolTipText("Adjusts the color channels of the image.");
        filters_opt[MainWindow.POSTERIZE].setToolTipText("Changes the color tones of the image.");
        filters_opt[MainWindow.SOLARIZE].setToolTipText("Creates a solarized effect.");
        filters_opt[MainWindow.DITHER].setToolTipText("Creates a dithering effect on the image.");
        filters_opt[MainWindow.GAIN].setToolTipText("Changes the gain/bias of the image.");
        filters_opt[MainWindow.GAMMA].setToolTipText("Changes the gamma of the image.");
        filters_opt[MainWindow.EXPOSURE].setToolTipText("Changes the exposure of the image.");
        filters_opt[MainWindow.CRYSTALLIZE].setToolTipText("Creates a crystallization effect.");
        filters_opt[MainWindow.POINTILLIZE].setToolTipText("Renders the image with points.");
        filters_opt[MainWindow.OIL].setToolTipText("Creates an oil painting effect.");
        filters_opt[MainWindow.MARBLE].setToolTipText("Creates a marble effect.");
        filters_opt[MainWindow.WEAVE].setToolTipText("Creates a weave effect.");
        filters_opt[MainWindow.OIL].setToolTipText("Creates a sparkle effect.");
        filters_opt[MainWindow.GLOW].setToolTipText("Creates a glow effect.");
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].setToolTipText("Scales the color channels of the image.");
        filters_opt[MainWindow.NOISE].setToolTipText("Adds noise to the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].setToolTipText("Mixes the color channels of the image.");
        filters_opt[MainWindow.LIGHT_EFFECTS].setToolTipText("Adds light effects to the image.");
        filters_opt[MainWindow.MIRROR].setToolTipText("Creates a mirror effect with transparency.");
        
        filters_opt[MainWindow.ANTIALIASING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
        filters_opt[MainWindow.EDGE_DETECTION].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.EDGE_DETECTION2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.SHARPNESS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.EMBOSS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.INVERT_COLORS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.BLURRING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_MASKING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.FADE_OUT].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_ADJUSTING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.CONTRAST_BRIGHTNESS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.GRAYSCALE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.COLOR_TEMPERATURE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_SWIZZLING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.POSTERIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.SOLARIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.DITHER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.GAIN].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.GAMMA].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.EXPOSURE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.CRYSTALLIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.POINTILLIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.OIL].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.MARBLE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.WEAVE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.SPARKLE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.GLOW].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.NOISE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, 0));
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
        filters_opt[MainWindow.LIGHT_EFFECTS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.MIRROR].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
        
        filters_opt[MainWindow.ANTIALIASING].addActionListener(e -> ptr.setFilter(MainWindow.ANTIALIASING));

        filters_opt[MainWindow.EDGE_DETECTION].addActionListener(e -> ptr.setFilter(MainWindow.EDGE_DETECTION));

        filters_opt[MainWindow.EDGE_DETECTION2].addActionListener(e -> ptr.setFilter(MainWindow.EDGE_DETECTION2));

        filters_opt[MainWindow.INVERT_COLORS].addActionListener(e -> ptr.setFilter(MainWindow.INVERT_COLORS));

        filters_opt[MainWindow.EMBOSS].addActionListener(e -> ptr.setFilter(MainWindow.EMBOSS));

        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION].addActionListener(e -> ptr.setFilter(MainWindow.HISTOGRAM_EQUALIZATION));

        filters_opt[MainWindow.SHARPNESS].addActionListener(e -> ptr.setFilter(MainWindow.SHARPNESS));

        filters_opt[MainWindow.BLURRING].addActionListener(e -> ptr.setFilter(MainWindow.BLURRING));

        filters_opt[MainWindow.COLOR_CHANNEL_MASKING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_MASKING));

        filters_opt[MainWindow.FADE_OUT].addActionListener(e -> ptr.setFilter(MainWindow.FADE_OUT));

        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_SWAPPING));

        filters_opt[MainWindow.CONTRAST_BRIGHTNESS].addActionListener(e -> ptr.setFilter(MainWindow.CONTRAST_BRIGHTNESS));

        filters_opt[MainWindow.GAIN].addActionListener(e -> ptr.setFilter(MainWindow.GAIN));

        filters_opt[MainWindow.GAMMA].addActionListener(e -> ptr.setFilter(MainWindow.GAMMA));

        filters_opt[MainWindow.EXPOSURE].addActionListener(e -> ptr.setFilter(MainWindow.EXPOSURE));

        filters_opt[MainWindow.GRAYSCALE].addActionListener(e -> ptr.setFilter(MainWindow.GRAYSCALE));

        filters_opt[MainWindow.COLOR_TEMPERATURE].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_TEMPERATURE));

        filters_opt[MainWindow.COLOR_CHANNEL_SWIZZLING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_SWIZZLING));

        filters_opt[MainWindow.POSTERIZE].addActionListener(e -> ptr.setFilter(MainWindow.POSTERIZE));

        filters_opt[MainWindow.DITHER].addActionListener(e -> ptr.setFilter(MainWindow.DITHER));

        filters_opt[MainWindow.SOLARIZE].addActionListener(e -> ptr.setFilter(MainWindow.SOLARIZE));

        filters_opt[MainWindow.COLOR_CHANNEL_ADJUSTING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_ADJUSTING));

        filters_opt[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_HSB_ADJUSTING));

        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_MIXING));

        filters_opt[MainWindow.CRYSTALLIZE].addActionListener(e -> ptr.setFilter(MainWindow.CRYSTALLIZE));

        filters_opt[MainWindow.POINTILLIZE].addActionListener(e -> ptr.setFilter(MainWindow.POINTILLIZE));

        filters_opt[MainWindow.OIL].addActionListener(e -> ptr.setFilter(MainWindow.OIL));

        filters_opt[MainWindow.MARBLE].addActionListener(e -> ptr.setFilter(MainWindow.MARBLE));

        filters_opt[MainWindow.WEAVE].addActionListener(e -> ptr.setFilter(MainWindow.WEAVE));

        filters_opt[MainWindow.SPARKLE].addActionListener(e -> ptr.setFilter(MainWindow.SPARKLE));

        filters_opt[MainWindow.GLOW].addActionListener(e -> ptr.setFilter(MainWindow.GLOW));

        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].addActionListener(e -> ptr.setFilter(MainWindow.COLOR_CHANNEL_SCALING));

        filters_opt[MainWindow.NOISE].addActionListener(e -> ptr.setFilter(MainWindow.NOISE));
        
        filters_opt[MainWindow.MIRROR].addActionListener(e -> ptr.setFilter(MainWindow.MIRROR));

        filters_opt[MainWindow.LIGHT_EFFECTS].addActionListener(e -> ptr.setFilter(MainWindow.LIGHT_EFFECTS));

        detail_filters_menu.add(filters_opt[MainWindow.ANTIALIASING]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.EDGE_DETECTION]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.EDGE_DETECTION2]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.SHARPNESS]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.BLURRING]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.EMBOSS]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.GLOW]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.NOISE]);

        color_filters_menu.add(filters_opt[MainWindow.HISTOGRAM_EQUALIZATION]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.POSTERIZE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.CONTRAST_BRIGHTNESS]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.GAIN]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.GAMMA]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.EXPOSURE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_TEMPERATURE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.INVERT_COLORS]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.SOLARIZE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_MASKING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_SWIZZLING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_MIXING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_ADJUSTING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_SCALING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.GRAYSCALE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.FADE_OUT]);

        texture_filters_menu.add(filters_opt[MainWindow.DITHER]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.CRYSTALLIZE]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.POINTILLIZE]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.OIL]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.MARBLE]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.WEAVE]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.SPARKLE]);
        texture_filters_menu.addSeparator();
        texture_filters_menu.add(filters_opt[MainWindow.MIRROR]);

        light_filters_menu.add(filters_opt[MainWindow.LIGHT_EFFECTS]);

        add(detail_filters_menu);
        addSeparator();
        add(color_filters_menu);
        addSeparator();
        add(texture_filters_menu);
        addSeparator();
        add(light_filters_menu);

        createDetailFilterNames();
        createColorFilterNames();
        createTextureFilterNames();
        createLightingFilterNames();

    }

    public JCheckBoxMenuItem[] getFilters() {

        return filters_opt;

    }

    public void setCheckedFilters(boolean[] filters) {

        for(int k = 0; k < filters_opt.length; k++) {
            filters_opt[k].setSelected(filters[k]);
        }

    }

    public JMenu getColorsFiltersMenu() {

        return color_filters_menu;

    }

    public JMenu getDetailsFiltersMenu() {

        return detail_filters_menu;

    }

    public JMenu getTextureFiltersMenu() {

        return texture_filters_menu;

    }

    public JMenu getLightingFiltersMenu() {

        return light_filters_menu;

    }

    private void createDetailFilterNames() {

        detail_list = new ArrayList<>();

        for(int i = 0; i < detail_filters_menu.getMenuComponentCount(); i++) {
            if(detail_filters_menu.getMenuComponent(i) instanceof JCheckBoxMenuItem) {
                detail_list.add(((JCheckBoxMenuItem)(detail_filters_menu.getMenuComponent(i))).getText());
            }
        }
    }

    private void createColorFilterNames() {

        color_list = new ArrayList<>();

        for(int i = 0; i < color_filters_menu.getMenuComponentCount(); i++) {
            if(color_filters_menu.getMenuComponent(i) instanceof JCheckBoxMenuItem) {
                color_list.add(((JCheckBoxMenuItem)(color_filters_menu.getMenuComponent(i))).getText());
            }
        }
    }

    private void createTextureFilterNames() {

        texture_list = new ArrayList<>();

        for(int i = 0; i < texture_filters_menu.getMenuComponentCount(); i++) {
            if(texture_filters_menu.getMenuComponent(i) instanceof JCheckBoxMenuItem) {
                texture_list.add(((JCheckBoxMenuItem)(texture_filters_menu.getMenuComponent(i))).getText());
            }
        }
    }

    private void createLightingFilterNames() {

        lighting_list = new ArrayList<>();

        for(int i = 0; i < light_filters_menu.getMenuComponentCount(); i++) {
            if(light_filters_menu.getMenuComponent(i) instanceof JCheckBoxMenuItem) {
                lighting_list.add(((JCheckBoxMenuItem)(light_filters_menu.getMenuComponent(i))).getText());
            }
        }
    }

    public static List<String> getDetailNamesList() {

        return detail_list;

    }

    public static List<String> getColorNamesList() {

        return color_list;

    }

    public static List<String> getTextureNamesList() {

        return texture_list;

    }

    public static List<String> getLightingNamesList() {

        return lighting_list;

    }

}
