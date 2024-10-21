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

public class Ria2Curve {

    public static int xyToRia2(int n, int x, int y) {
        int index = 0;
        int s = n / 2;

        while (s > 0) {
            int rx = (x & s) > 0 ? 1 : 0;
            int ry = (y & s) > 0 ? 1 : 0;
            index += s * s * ((3 * rx) ^ ry);

            // Rotate/flip quadrant appropriately
            if (ry == 0) {
                if (rx == 1) {
                    x = n - 1 - x;
                    y = n - 1 - y;
                    // Swap x and y
                    int temp = x;
                    x = y;
                    y = temp;
                }

            }
            else {
                if (rx == 0) {
//                    x = n - 1 - x;
 //                   y = n - 1 - y;
                    // Swap x and y
                    int temp = x;
                    x = y;
                    y = temp;
                }
                else {
                    //x = n - 1 - x;
                    y = n - 1 - y;
                }
            }
            s /= 2;
        }
        return index;
    }


    public static void main(String[] args) throws IOException {
        int n = 4; // Grid size (must be a power of 2)
        int x = 2; // X coordinate
        int y = 3; // Y coordinate

        int hilbertIndex = xyToRia2(n, x, y);
        System.out.println("Ria 2 Index: " + hilbertIndex);

        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);

        RenderFrame frame = new RenderFrame(a);

        int size = 32;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = xyToRia2(size, i, j);
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

//            if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) {
//                //System.err.println("Non consecutive");
//                //System.exit(-1);
//            }

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

        ImageIO.write(a, "png", new File("ria2.png"));

    }
}
