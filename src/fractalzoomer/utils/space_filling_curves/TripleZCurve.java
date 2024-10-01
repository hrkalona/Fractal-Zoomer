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

public class TripleZCurve {

    // Returns the Peano curve index for a given (x, y) coordinate
    public static int xyToTripleZ(int n, int x, int y) {
        if (n == 1) {
            return 0;
        }

        int segmentSize = n / 3;
        int segmentX = x / segmentSize;
        int segmentY = y / segmentSize;

        // Determine which of the 9 segments (0 to 8) the (x, y) point lies in
        int segment = 3 * segmentY + segmentX;

        // Find the Peano curve index within the sub-square
        int subIndex = xyToTripleZ(segmentSize, x % segmentSize, y % segmentSize);

        // The Peano index is the combination of the segment and the sub-index
        return segment * (segmentSize * segmentSize) + subIndex;
    }

    public static void main(String[] args) throws IOException {
        int n = 9; // Grid size (must be a power of 3)
        int x = 2; // X coordinate
        int y = 1; // Y coordinate

        int peanoIndex = xyToTripleZ(n, x, y);
        System.out.println("Triple Z Index: " + peanoIndex);


        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);


        int size = 27;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = xyToTripleZ(size, i, j);
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

        ImageIO.write(a, "png", new File("triplez.png"));
    }
}