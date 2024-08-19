
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static fractalzoomer.main.CommonFunctions.makeRoundedCorner;

/**
 *
 * @author hrkalona2
 */
public class MemoryLabel extends JLabel {
	private static final long serialVersionUID = 3645023799182837311L;
	private final double MiB = 1024.0 * 1024.0;
    private double maxMemory;
    private final double installedMemory = getInstalledMemory() / MiB;
    private double allocatedMemory, usedMemory;

    public static final int MEMORY_DELAY = 10000;
//    private static final Color USED_MEMORY_COLOR = new Color(76, 139, 245);
//    private static final Color ALLOCATED_MEMORY_COLOR = new Color(221, 80, 68);
//    private static final Color FREE_MEMORY_COLOR = new Color(26, 162, 96);

    private static final Color FREE_MEMORY_COLOR = new Color(0xAABE7D);
    private static final Color ALLOCATED_MEMORY_COLOR = new Color(0xFFBE7D);
    private static final Color USED_MEMORY_COLOR = new Color(0xFFFFD1);
    private static final String HASHES = "##############################";
    private static final String ZEROS = "0000000000000000000000000000000000000000000000000000000000000000";

    public MemoryLabel(int width, int height) {
        super();
        if(!MainWindow.useCustomLaf) {
            setBorder(BorderFactory.createLoweredBevelBorder());
        }
        //setBorder(MyBorder.LOWERED);

        if(height == 0) {
            setPreferredSize(new Dimension(width, getFont().getSize() + 4));
        }
        else {
            setPreferredSize(new Dimension(width, 20));
        }

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

        if(MainWindow.useCustomLaf) {
            BufferedImage image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

            Graphics g2 = image.createGraphics();
            g2.setColor(FREE_MEMORY_COLOR);
            g2.fillRect(0, 0, width, height);

            g2.setColor(ALLOCATED_MEMORY_COLOR);
            g2.fillRect(0, 0, (int) Math.round(width * allocatedMemory / maxMemory), height);

            g2.setColor(USED_MEMORY_COLOR);
            g2.fillRect(0, 0, (int) Math.round(width * usedMemory / maxMemory), height);
            g2.dispose();

            image = makeRoundedCorner(image, 5);
            g.drawImage(image, insets.left, insets.top, null);
        }
        else {
            g.setColor(FREE_MEMORY_COLOR);
            g.fillRect(insets.left, insets.top, width, height);

            g.setColor(ALLOCATED_MEMORY_COLOR);
            g.fillRect(insets.left, insets.top, (int)Math.round(width * allocatedMemory / maxMemory), height);

            g.setColor(USED_MEMORY_COLOR);
            g.fillRect(insets.left, insets.top, (int)Math.round(width * usedMemory / maxMemory), height);
        }

        super.paint(g);
    }

    public void refresh() {
        allocatedMemory = getAllocatedMemory() / MiB;
        usedMemory = getUsedMemory() / MiB;
        maxMemory = getMaxMemory() / MiB;

        setText(Math.round(usedMemory) + "M / "
                + Math.round(allocatedMemory) + "M / "
                + Math.round(maxMemory) + "M");
        setToolTipText("<html>Java heap space:<br>&bull; <b>" + formatDouble(usedMemory, 1)
                + " MB</b> used<br>&bull; <b>" + formatDouble(allocatedMemory, 1)
                + " MB</b> allocated<br>&bull; <b>" + formatDouble(maxMemory, 1) + " MB</b> maximum"
                + "<br>Installed RAM: <b>" + formatDouble(installedMemory) + " MB</b></html>");
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

    public static long getAllocatedMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public static long getUsedMemory() {
        return getAllocatedMemory() - Runtime.getRuntime().freeMemory();
    }

    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    private long getInstalledMemory() {
        return ((com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
    }

}
