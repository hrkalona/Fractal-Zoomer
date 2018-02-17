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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 * @author hrkalona2
 */
public class MemoryLabel extends JLabel {

    private final double MiB = 1024 * 1024;
    private double maxMemory;
    private final double installedMemory = getInstalledMemory() / MiB;
    private double allocatedMemory, usedMemory;
    private static final Color USED_MEMORY_COLOR = new Color(76, 139, 245);
    private static final Color ALLOCATED_MEMORY_COLOR = new Color(221, 80, 68);
    private static final Color FREE_MEMORY_COLOR = new Color(26, 162, 96);
    private static final int PANEL_HEIGHT = 23;
    private static final String HASHES = "##############################";
    private static final String ZEROS = "0000000000000000000000000000000000000000000000000000000000000000";

    public MemoryLabel(int width) {
        setBorder(BorderFactory.createLoweredBevelBorder());
        //setBorder(MyBorder.LOWERED);
        setPreferredSize(new Dimension(width, PANEL_HEIGHT));
        setHorizontalAlignment(JLabel.CENTER);
        refresh();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.gc();
                refresh();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Insets insets = getInsets();
        Dimension size = getSize();
        int height = size.height - insets.top - insets.bottom;
        int width = size.width - insets.left - insets.right;
        
        g.setColor(FREE_MEMORY_COLOR);
        g.fillRect(insets.left, insets.top, width, height);

        g.setColor(ALLOCATED_MEMORY_COLOR);
        g.fillRect(insets.left, insets.top, (int)Math.round(width * allocatedMemory / maxMemory), height);

        g.setColor(USED_MEMORY_COLOR);
        g.fillRect(insets.left, insets.top, (int)Math.round(width * usedMemory / maxMemory), height);

        super.paint(g);
    }

    public void refresh() {
        allocatedMemory = getAllocatedMemory() / MiB;
        usedMemory = getUsedMemory() / MiB;
        maxMemory = getMaxMemory() / MiB;

        setText(Math.round(usedMemory) + "M / "
                + Math.round(allocatedMemory) + "M / "
                + Math.round(maxMemory) + "M");
        setToolTipText("<html>Java heap space:<br>&bull; " + formatDouble(usedMemory, 1)
                + " MiB used<br>&bull; " + formatDouble(allocatedMemory, 1)
                + " MiB allocated<br>&bull; " + formatDouble(maxMemory, 1) + " MiB maximum"
                + "<br>Installed RAM: " + formatDouble(installedMemory) + " MiB</html>");
        repaint();
    }

    private String formatDouble(double d, int precision) {
        return formatDouble(d, precision, true, '.');
    }

    private String formatDouble(double d) {
        return formatDouble(d, 0, true, '.');
    }

    private String formatDouble(double d, int precision, boolean fillZeros, char decimalSeparator) {
        if(Double.isInfinite(d)) {
            return "infinity";
        }
        if(Double.isNaN(d)) {
            return "NaN";
        }
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(decimalSeparator);
        DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
        df.setDecimalFormatSymbols(symbols);
        String fractionPattern = fillZeros ? ZEROS : HASHES;
        df.applyPattern("#,##0" + (precision > 0 ? "." + fractionPattern.substring(0, precision) : ""));
        return df.format(d);
    }

    private long getAllocatedMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    private long getUsedMemory() {
        return getAllocatedMemory() - Runtime.getRuntime().freeMemory();
    }

    private long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    private long getInstalledMemory() {
        return ((com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }

}
