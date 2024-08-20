package fractalzoomer.utils.space_filling_curves;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;

import static fractalzoomer.utils.space_filling_curves.CurveData.*;

public class ZCurve {

    /**
     * Interleaves the bits of x and y, effectively calculating the Morton code.
     *
     * @param x The x coordinate (should fit within 32 bits).
     * @param y The y coordinate (should fit within 32 bits).
     * @return The Morton code (z-value).
     */
    public static long interleaveBits(int x, int y) {
        return (expandBits(x) | (expandBits(y) << 1));
    }

    /**
     * Expands the bits of an integer by inserting 0 bits between each bit.
     *
     * @param x The integer to expand.
     * @return The expanded integer with 0s interleaved.
     */
    private static long expandBits(int x) {
        long z = x & 0xFFFF;
        z = (z | z << 16) & 0x0000FFFF0000FFFFL;
        z = (z | z << 8) & 0x00FF00FF00FF00FFL;
        z = (z | z << 4) & 0x0F0F0F0F0F0F0F0FL;
        z = (z | z << 2) & 0x3333333333333333L;
        z = (z | z << 1) & 0x5555555555555555L;
        return z;
    }

    /**
     * Deinterleaves the bits of the z-value to extract the original x or y coordinate.
     *
     * @param z The Morton code (z-value).
     * @return The original x or y coordinate.
     */
    public static int compactBits(long z) {
        z &= 0x5555555555555555L;
        z = (z | z >> 1) & 0x3333333333333333L;
        z = (z | z >> 2) & 0x0F0F0F0F0F0F0F0FL;
        z = (z | z >> 4) & 0x00FF00FF00FF00FFL;
        z = (z | z >> 8) & 0x0000FFFF0000FFFFL;
        z = (z | z >> 16) & 0x00000000FFFFFFFFL;
        return (int) z;
    }

    public static void main(String[] args) throws IOException {
        int x = 1;  // Example x coordinate
        int y = 0;  // Example y coordinate

        // Calculate the Morton code
        long z = interleaveBits(x, y);
        System.out.println("Morton Z-value: " + z);

        // Decompose the Morton code back into coordinates
        int extractedX = compactBits(z);
        int extractedY = compactBits(z >> 1);

        System.out.println("Extracted X: " + extractedX);
        System.out.println("Extracted Y: " + extractedY);

        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);


        int size = 32;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = ZCurve.interleaveBits(i, j);
                p.add(c);
            }
        }

        Collections.sort(p);

        int total = size * size;

        Graphics2D g2d = a.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, a.getWidth(), a.getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        HashSet<Long> set = new LinkedHashSet<>();
        for(CurveData c : p) {
            set.add(c.order);
        }

        if(set.size() != p.size()) {
            System.err.println("Non Unique ids");
            System.exit(-1);
        }

        for(int i = 0; i < p.size() - 1; i++) {
            if(drawColor) {
                g2d.setColor(new Color(palette[(int) ((i / (double) total) * (palette.length - 1))]));
            }
            else {
                g2d.setColor(Color.BLACK);
            }
            int p1x = p.get(i).x * offset + offset;
            int p1y = p.get(i).y * offset + offset;
            int p2x = p.get(i + 1).x * offset + offset;
            int p2y = p.get(i + 1).y * offset + offset;
            int radius = offset / 2;
            if(radius % 2 == 0 ) {
                radius--;
            }
            if(drawCircles) {
                g2d.fillOval(p1x - radius / 2, p1y - radius / 2, radius, radius);
                g2d.fillOval(p2x - radius / 2, p2y - radius / 2, radius, radius);
            }
            g2d.drawLine(p1x, p1y, p2x, p2y);

            if(drawNumbers) {
                g2d.setColor(Color.BLACK);
                g2d.drawString("" + p.get(i).order, p1x - radius / 2, p1y - radius / 2);
                g2d.drawString("" + p.get(i + 1).order, p2x - radius / 2, p2y - radius / 2);
            }
        }

        g2d.dispose();

        ImageIO.write(a, "png", new File("z.png"));

    }
}
