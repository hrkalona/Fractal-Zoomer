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

public class PeanoCurve {

    public static int defaultCurve(int x, int y) {
        return xyToPeano(10, x, y, false);
    }


    // Function to get the Peano curve index for given (x, y)
    public static int xyToPeano(int exponent, int x, int y, boolean horizontal_orientation) {

        if(horizontal_orientation) {
            int temp = x;
            x = y;
            y = temp;
        }

        int[][] a = new int[2][exponent];
        int sum0 = 0, sum1 = 0;
        int ptr = exponent-1;
        while (x != 0) {
            if(ptr < 0) {
                return 0;
            }
            a[0][ptr] = x%3;
            sum0 += a[0][ptr];
            ptr--;
            x /= 3;
        }
        ptr = exponent-1;
        while (y != 0) {
            if(ptr < 0) {
                return 0;
            }
            a[1][ptr] = y%3;
            sum1 += a[1][ptr];
            ptr--;
            y /= 3;
        }

        for (int i = exponent-1; i >= 0; i--) {
            sum1 -= a[1][i];
            if ((sum0 & 1) != 0) {
                a[1][i] = 2 - a[1][i];
            }
            sum0 -= a[0][i];
            if ((sum1 & 1) != 0) {
                a[0][i] = 2 - a[0][i];
            }
        }

        int num = 0, base = 1;
        for (int j = exponent-1; j >= 0; j--) {
            num += base * a[1][j];
            base *= 3;
            num += base * a[0][j];
            base *= 3;
        }
        return num;
    }

    public static void main(String[] args) throws IOException {
        int n = 2; // Size of the grid (must be a power of 3)
        int x = 1; // X coordinate
        int y = 3; // Y coordinate

        int index = xyToPeano(n, x, y, false);
        System.out.println("Peano Curve Index for (" + x + ", " + y + "): " + index);

        BufferedImage a = new BufferedImage(1700 * 3, 1700 * 3, BufferedImage.TYPE_INT_ARGB);

        RenderFrame frame = new RenderFrame(a);

        boolean horizontal_orientation = true;
        int size = 27 * 3;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = xyToPeano(19, i, j, horizontal_orientation);
                p.add(c);
            }
        }

        Collections.sort(p);

        HashSet<Long> set = new LinkedHashSet<>();
        for(CurveData c : p) {
            set.add(c.order);
        }

        if(set.size() != p.size()) {
            System.err.println("Non Unique ids");
            System.exit(-1);
        }

        int total = size * size;

        Graphics2D g2d = a.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, a.getWidth(), a.getHeight());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int vertical_edges = 0;
        int horizontal_edges = 0;

        for(int i = 0; i < p.size() - 1; i++) {
            if(drawColor) {
                g2d.setColor(new Color(palette[(int) ((i / (double) total) * (palette.length - 1))]));
            }
            else {
                g2d.setColor(Color.BLACK);
            }

            int x1 = p.get(i).x;
            int y1 = p.get(i).y;
            int x2 = p.get(i + 1).x;
            int y2 = p.get(i + 1).y;

            int p1x = x1 * offset + offset;
            int p1y = y1 * offset + offset;
            int p2x = x2 * offset + offset;
            int p2y = y2 * offset + offset;

            if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) {
                System.err.println("Non consecutive");
                System.exit(-1);
            }

            if(x1 == x2) {
                vertical_edges++;
            }
            else {
                horizontal_edges++;
            }

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

            frame.repaint();
        }

        g2d.dispose();

        System.out.println("Vertical Edges: " + vertical_edges);
        System.out.println("Horizontal Edges: " + horizontal_edges);

        if(horizontal_orientation) {
            ImageIO.write(a, "png", new File("peano-horizontal.png"));
        }
        else {
            ImageIO.write(a, "png", new File("peano-vertical.png"));
        }
    }
}
