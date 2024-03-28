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
import fractalzoomer.utils.ColorSpaceConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author hrkalona2
 */
public class CpuLabel extends JLabel {
	private static final long serialVersionUID = 364504299182837311L;

    private double cpuLoad;

    public static final int CPU_DELAY = 2000;
    private int arcWidth = 5;
    private int arcHeight = 5;

    private static final int PANEL_HEIGHT = 20;

    public CpuLabel(int width) {
        if(!MainWindow.useCustomLaf) {
            setBorder(BorderFactory.createLoweredBevelBorder());
        }
        //setBorder(MyBorder.LOWERED);

        setPreferredSize(new Dimension(width, PANEL_HEIGHT));

        setHorizontalAlignment(JLabel.CENTER);
        refresh();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                refresh();
            }
        });

        setToolTipText("<html>CPU Usage<br>&bull; <b>" + Runtime.getRuntime().availableProcessors() +"</b> Logical Processors</html>");
    }

    @Override
    public void paint(Graphics g) {
        Insets insets = getInsets();
        Dimension size = getSize();
        int height = size.height - insets.top - insets.bottom;
        int width = size.width - insets.left - insets.right;

        Color finalColor;
        if(cpuLoad < 0) {
            finalColor = Color.GRAY;
        }
        else {

            int r1, g1, b1, r2, g2, b2;

            double factor = (1 - cpuLoad) * 100;

            if (factor >= 0 && factor < 33) {
                r1 = 255;
                g1 = 0;
                b1 = 0;

                r2 = 255;
                g2 = 140;
                b2 = 0;

                factor = factor / 33.0;
            } else if (factor >= 33 && factor < 66) {

                r1 = 255;
                g1 = 140;
                b1 = 0;

                r2 = 255;
                g2 = 215;
                b2 = 0;

                factor = (factor - 33) / (66.0 - 33.0);
            } else {

                r1 = 255;
                g1 = 215;
                b1 = 0;

                r2 = 0;
                g2 = 153;
                b2 = 0;

                factor = (factor - 66) / (100.0 - 66.0);
            }

            int red = (int) (r1 + (r2 - r1) * factor + 0.5);
            int green = (int) (g1 + (g2 - g1) * factor + 0.5);
            int blue = (int) (b1 + (b2 - b1) * factor + 0.5);

            red = ColorSpaceConverter.pastel(red, 255, 0.85);
            green = ColorSpaceConverter.pastel(green, 255, 0.85);
            blue = ColorSpaceConverter.pastel(blue, 255, 0.85);
            finalColor = new Color(red, green, blue);
        }


        g.setColor(finalColor);

        if(!MainWindow.useCustomLaf) {
            g.fillRect(insets.left, insets.top, width, height);
        }
        else {
            ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.fillRoundRect(insets.left, insets.top, width, height, arcWidth, arcHeight);
        }

        super.paint(g);
    }

    public void refresh() {
        cpuLoad = ManagementFactory.getPlatformMXBean(
                com.sun.management.OperatingSystemMXBean.class).getProcessCpuLoad();

        if(cpuLoad >= 0) {
            setText(String.format("%3.2f", cpuLoad * 100) + "%");
        }
        else {
            setText("N/A");
        }

        repaint();
    }

    public static double getCpuLoad() {
        return ManagementFactory.getPlatformMXBean(
                com.sun.management.OperatingSystemMXBean.class).getProcessCpuLoad();
    }

    public static double getSystemCpuLoad() {
        return ManagementFactory.getPlatformMXBean(
                com.sun.management.OperatingSystemMXBean.class).getSystemCpuLoad();
    }

}
