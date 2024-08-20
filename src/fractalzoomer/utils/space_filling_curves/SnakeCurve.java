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

public class SnakeCurve {

    public static int direction = 0;

    public static int xyToSnake(int width, int x, int y) {

        if(direction == 0) {
            if ((y & 1) == 1) {
                return y * width + (width - 1 - x);
            } else {
                return y * width + x;
            }
        }
        else if(direction == 1) {
            if ((x & 1) == 1) {
                return x * width + (width - 1 - y);
            } else {
                return x * width + y;
            }
        }
        return 0;
    }

    public static void main(String[] args) throws IOException {
        int x = 1;  // Example x coordinate
        int y = 0;  // Example y coordinate

        // Calculate the Morton code
        long z = xyToSnake(100, x, y);
        System.out.println("Snake value: " + z);

        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);


        int width = 32;
        int height = 32;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = xyToSnake(direction == 0 ? width : height, i, j);
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

        int total = width * height;

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

            if(Math.abs(x1 - x2) > 1 || Math.abs(y1 - y2) > 1) {
                System.err.println("Non consecutive");
                System.exit(-1);
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
        }

        g2d.dispose();

        ImageIO.write(a, "png", new File("snake.png"));

    }
}
