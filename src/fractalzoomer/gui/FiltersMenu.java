/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

/**
 *
 * @author hrkalona2
 */
public class FiltersMenu extends JMenu {

    private JMenu detail_filters_menu;
    private JMenu color_filters_menu;
    private JCheckBoxMenuItem[] filters_opt;
    private MainWindow ptr;

    public FiltersMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;

        detail_filters_menu = new JMenu("Details");
        detail_filters_menu.setIcon(getIcon("/fractalzoomer/icons/filter_details.png"));
        color_filters_menu = new JMenu("Colors");
        color_filters_menu.setIcon(getIcon("/fractalzoomer/icons/filter_colors.png"));

        filters_opt = new JCheckBoxMenuItem[22];

        filters_opt[MainWindow.ANTIALIASING] = new JCheckBoxMenuItem("Anti-Aliasing");
        filters_opt[MainWindow.EDGE_DETECTION] = new JCheckBoxMenuItem("Edge Detection");
        filters_opt[MainWindow.SHARPNESS] = new JCheckBoxMenuItem("Sharpness");
        filters_opt[MainWindow.BLURRING] = new JCheckBoxMenuItem("Blurring");
        filters_opt[MainWindow.EMBOSS] = new JCheckBoxMenuItem("Emboss");
        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION] = new JCheckBoxMenuItem("Histogram Equalization");
        filters_opt[MainWindow.CONTRAST_BRIGHTNESS] = new JCheckBoxMenuItem("Contrast/Brightness");
        filters_opt[MainWindow.COLOR_TEMPERATURE] = new JCheckBoxMenuItem("Color Temperature");
        filters_opt[MainWindow.POSTERIZE] = new JCheckBoxMenuItem("Posterization");
        filters_opt[MainWindow.SOLARIZE] = new JCheckBoxMenuItem("Solarization");
        filters_opt[MainWindow.FADE_OUT] = new JCheckBoxMenuItem("Fade Out");
        filters_opt[MainWindow.INVERT_COLORS] = new JCheckBoxMenuItem("Inverted Colors");
        filters_opt[MainWindow.COLOR_CHANNEL_MASKING] = new JCheckBoxMenuItem("Mask Color Channel");
        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING] = new JCheckBoxMenuItem("Color Channel Swapping");
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING] = new JCheckBoxMenuItem("Color Channel Mixing");
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING] = new JCheckBoxMenuItem("Color Channel Scaling");
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_SCALING] = new JCheckBoxMenuItem("Color Channel HSB Scaling");
        filters_opt[MainWindow.GRAYSCALE] = new JCheckBoxMenuItem("Grayscale");
        filters_opt[MainWindow.DITHER] = new JCheckBoxMenuItem("Dither");
        filters_opt[MainWindow.GAIN] = new JCheckBoxMenuItem("Gain/Bias");
        filters_opt[MainWindow.GAMMA] = new JCheckBoxMenuItem("Gamma");
        filters_opt[MainWindow.EXPOSURE] = new JCheckBoxMenuItem("Exposure");

        filters_opt[MainWindow.ANTIALIASING].setToolTipText("Smooths the jagged look of the image.");
        filters_opt[MainWindow.EDGE_DETECTION].setToolTipText("Detects the edges of the image.");
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
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].setToolTipText("Mixes the color channels of the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].setToolTipText("Scales the color channels of the image.");
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_SCALING].setToolTipText("Scales the HSB color channels of the image.");
        filters_opt[MainWindow.POSTERIZE].setToolTipText("Changes the color tones of the image.");
        filters_opt[MainWindow.SOLARIZE].setToolTipText("Creates a solarized effect.");
        filters_opt[MainWindow.DITHER].setToolTipText("Creates a dithering effect on the image.");
        filters_opt[MainWindow.GAIN].setToolTipText("Changes the gain/bias of the image.");
        filters_opt[MainWindow.GAMMA].setToolTipText("Changes the gamma of the image.");
        filters_opt[MainWindow.EXPOSURE].setToolTipText("Changes the exposure of the image.");

        filters_opt[MainWindow.ANTIALIASING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0));
        filters_opt[MainWindow.EDGE_DETECTION].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.SHARPNESS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.EMBOSS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.INVERT_COLORS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.BLURRING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_MASKING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.FADE_OUT].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_HSB_SCALING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.CONTRAST_BRIGHTNESS].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.GRAYSCALE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.COLOR_TEMPERATURE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.POSTERIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.SOLARIZE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.DITHER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.SHIFT_MASK));
        filters_opt[MainWindow.GAIN].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        filters_opt[MainWindow.GAMMA].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        filters_opt[MainWindow.EXPOSURE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.SHIFT_MASK));

        filters_opt[MainWindow.ANTIALIASING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.ANTIALIASING);

            }
        });

        filters_opt[MainWindow.EDGE_DETECTION].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.EDGE_DETECTION);

            }
        });

        filters_opt[MainWindow.INVERT_COLORS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.INVERT_COLORS);

            }
        });

        filters_opt[MainWindow.EMBOSS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.EMBOSS);

            }
        });

        filters_opt[MainWindow.HISTOGRAM_EQUALIZATION].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.HISTOGRAM_EQUALIZATION);

            }
        });

        filters_opt[MainWindow.SHARPNESS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.SHARPNESS);

            }
        });

        filters_opt[MainWindow.BLURRING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.BLURRING);

            }
        });

        filters_opt[MainWindow.COLOR_CHANNEL_MASKING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_CHANNEL_MASKING);

            }
        });

        filters_opt[MainWindow.FADE_OUT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.FADE_OUT);

            }
        });

        filters_opt[MainWindow.COLOR_CHANNEL_SWAPPING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_CHANNEL_SWAPPING);

            }
        });

        filters_opt[MainWindow.CONTRAST_BRIGHTNESS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.CONTRAST_BRIGHTNESS);

            }
        });
        
        filters_opt[MainWindow.GAIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.GAIN);

            }
        });
        
        filters_opt[MainWindow.GAMMA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.GAMMA);

            }
        });
        
        filters_opt[MainWindow.EXPOSURE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.EXPOSURE);

            }
        });

        filters_opt[MainWindow.GRAYSCALE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.GRAYSCALE);

            }
        });

        filters_opt[MainWindow.COLOR_TEMPERATURE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_TEMPERATURE);

            }
        });

        filters_opt[MainWindow.COLOR_CHANNEL_MIXING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_CHANNEL_MIXING);

            }
        });

        filters_opt[MainWindow.POSTERIZE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.POSTERIZE);

            }
        });
        
        filters_opt[MainWindow.DITHER].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.DITHER);

            }
        });

        filters_opt[MainWindow.SOLARIZE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.SOLARIZE);

            }
        });

        filters_opt[MainWindow.COLOR_CHANNEL_SCALING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_CHANNEL_SCALING);

            }
        });

        filters_opt[MainWindow.COLOR_CHANNEL_HSB_SCALING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFilter(MainWindow.COLOR_CHANNEL_HSB_SCALING);

            }
        });

        detail_filters_menu.add(filters_opt[MainWindow.ANTIALIASING]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.EDGE_DETECTION]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.SHARPNESS]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.BLURRING]);
        detail_filters_menu.addSeparator();
        detail_filters_menu.add(filters_opt[MainWindow.EMBOSS]);

        color_filters_menu.add(filters_opt[MainWindow.HISTOGRAM_EQUALIZATION]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.POSTERIZE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.DITHER]);
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
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_MIXING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_SCALING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.COLOR_CHANNEL_HSB_SCALING]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.GRAYSCALE]);
        color_filters_menu.addSeparator();
        color_filters_menu.add(filters_opt[MainWindow.FADE_OUT]);

        add(detail_filters_menu);
        addSeparator();
        add(color_filters_menu);

    }

    public JCheckBoxMenuItem[] getFilters() {

        return filters_opt;

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

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

}