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

public class PeanoGenericCurve {
    // | |‾|
    // | | |  : 0
    // |_| |

    // ‾‾‾|
    // |‾‾‾    : 1
    //  ‾‾‾
    public static int[][] MEANDER_SMALL_PATTERN = {{1,0,1},
                                             {0,1,0},
                                             {1,0,1}};
    public static int[][] SMALL_PATTERN2 = {{0,1,0},
                                                {1,0,1},
                                                {0,1,0}};
    public static int[][] SMALL_PATTERN3 = {{0,1,1},
                                            {1,0,1},
                                            {1,1,0}};

    //------------------------------------------------------------------------

    //These patterns should be used every large_pattern_scale pixels
    public static int[][] LARGE_PATTERN = {{1,1,1},
                                        {1,1,1},
                                        {1,1,1}};
    public static int[][] LARGE_PATTERN_CHECKERS = {{1,0,1,0},
                                                    {0,1,0,1},
                                                    {1,0,1,0},
                                                    {0,1,0,1}};

    public static int[][] LARGE_PATTERN_COLUMNS =  {{1,0,1,0},
                                                    {1,0,1,0},
                                                    {1,0,1,0},
                                                    {1,0,1,0}};

    //----------------------------------------------------------------

//    public static int[][] GLOBAL_FLIP_PATTERN_MASK =    {{1,0,1,0},
//                                                    {0,1,0,1},
//                                                    {1,0,1,0},
//                                                    {0,1,0,1}};

    public static int[][] GLOBAL_FLIP_PATTERN_MASK =  {{1,0,1},
                                                    {0,1,0},
                                                    {1,0,1},
                                                    };

    public static boolean revert_large_pattern = false;
    public static boolean revert_flip_pattern = false;
    public static int MIN_LEVEL = 9;

    public static int defaultCurve(int x, int y) {
        return xyToGenericPeanoRecursive(59049, 10,x, y, false, GLOBAL_FLIP_PATTERN_MASK, MEANDER_SMALL_PATTERN, LARGE_PATTERN_CHECKERS, 9);
    }

    public static int xyToGenericPeanoRecursive(int level, int exponent, int x, int y, boolean global_horizontal_orientation, int[][] global_flip_pattern_mask, int[][] small_pattern, int[][] large_pattern, int large_pattern_scale) {

        if(level <= MIN_LEVEL || global_flip_pattern_mask == null) {
            return xyToGenericPeano(exponent, x, y, global_horizontal_orientation, global_flip_pattern_mask, small_pattern, large_pattern, large_pattern_scale);
        }

        GenerationData data = getCurrentGenerationData(level, x, y, global_horizontal_orientation, global_flip_pattern_mask);

        return xyToGenericPeanoRecursive(level / 3, exponent, data.x, data.y, data.global_horizontal_orientation, global_flip_pattern_mask, small_pattern, large_pattern, large_pattern_scale);

    }

    static class GenerationData {
        public int x;
        public int y;
        public boolean global_horizontal_orientation;
    }

    private static GenerationData getCurrentGenerationData(int number, int x, int y, boolean global_horizontal_orientation, int[][] global_flip_pattern_mask) {

        int x_div = x / number;
        int y_div = y / number;

        GenerationData data = new GenerationData();

        boolean condition = global_flip_pattern_mask[y_div % global_flip_pattern_mask.length][x_div % global_flip_pattern_mask[0].length] == 1;
        condition = revert_flip_pattern != condition;
        data.global_horizontal_orientation = condition != global_horizontal_orientation;

        int offsetx = 0;
        int offsety = 0;
        if(condition) {
            int sum = x_div + y_div;
            if(sum % 2 == 1) {
                int factor = sum / 2 + 1;
                offsetx = - (2 * (x - number * factor) + 1);
                offsety = - (2 * (y - number * factor) + 1);
            }
            else {
                offsetx = Math.abs(x_div * number - y_div * number);
                offsetx = y < x ? -offsetx : offsetx;
                offsety = -offsetx;
            }
        }

        data.x = x + offsetx;
        data.y = y + offsety;

        return data;
    }

    public static int xyToGenericPeano(int exponent, int x, int y, boolean global_horizontal_orientation, int[][] global_flip_pattern_mask, int[][] small_pattern, int[][] large_pattern, int large_pattern_scale) {
        if(global_flip_pattern_mask == null) {
            return xyToGenericPeanoInternal(exponent, x, y, global_horizontal_orientation, small_pattern, large_pattern, large_pattern_scale);
        }

        GenerationData data = getCurrentGenerationData(MIN_LEVEL, x, y, global_horizontal_orientation, global_flip_pattern_mask);

        return xyToGenericPeanoInternal(exponent, data.x, data.y, data.global_horizontal_orientation, small_pattern, large_pattern, large_pattern_scale);
    }

    //large_pattern_scale should be power of 3
    //m is the exponent of 3, so 3^m
    private static int xyToGenericPeanoInternal(int exponent, int x, int y, boolean global_horizontal_orientation, int[][] small_pattern, int[][] large_pattern, int large_pattern_scale) {

        if(small_pattern == null || large_pattern == null) {
            large_pattern_scale = 1;
        }

        boolean internal_orientation = false;
        if(large_pattern_scale > 1) {
            int x_div3 = x / 3;
            int y_div3 = y / 3;

            int xm_mod3 = x_div3 % 3;
            int ym_mod3 = y_div3 % 3;

            int pattern_condition = large_pattern[(y / large_pattern_scale) % large_pattern.length][(x / large_pattern_scale) % large_pattern[0].length];
            internal_orientation = small_pattern[ym_mod3][xm_mod3] == pattern_condition;
            internal_orientation = revert_large_pattern != internal_orientation;
        }

        internal_orientation = global_horizontal_orientation != internal_orientation;

        if(global_horizontal_orientation) {
            int temp = x;
            x = y;
            y = temp;
        }

        int x_div3 = x / 3;
        int y_div3 = y / 3;

        int xm_mod3 = x_div3 % 3;
        int ym_mod3 = y_div3 % 3;

        if(internal_orientation) {
            int x_corner = x_div3 * 3;
            int y_corner = y_div3 * 3;

            boolean flip_symbol = ((xm_mod3 + ym_mod3) % 2 == ((x / 9 + y / 9) % 2 == 0 ? 1 : 0));
            int diffx = flip_symbol ? 2 - (x - x_corner) : x - x_corner;
            int diffy = flip_symbol ? 2 - (y - y_corner) : y - y_corner;

            return PeanoCurve.xyToPeano(exponent, y_corner + diffx, x_corner + diffy, true);

        }

        return PeanoCurve.xyToPeano(exponent, x, y, false);

    }

    public static void main(String[] args) throws IOException {
        BufferedImage a = new BufferedImage(1700, 1700, BufferedImage.TYPE_INT_ARGB);

        RenderFrame frame = new RenderFrame(a);

        boolean horizontal_orientation = false;
        int size = 27 * 3;
        ArrayList<CurveData> p = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                CurveData c = new CurveData();
                c.x = i;
                c.y = j;
                c.order = xyToGenericPeanoRecursive(27,10, i, j, horizontal_orientation, GLOBAL_FLIP_PATTERN_MASK, MEANDER_SMALL_PATTERN, LARGE_PATTERN_CHECKERS, 9);
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

        System.out.println("Vertical Edges: " + vertical_edges);
        System.out.println("Horizontal Edges: " + horizontal_edges);

        g2d.dispose();

        if(horizontal_orientation) {
            ImageIO.write(a, "png", new File("peano-generic-horizontal.png"));
        }
        else {
            ImageIO.write(a, "png", new File("peano-generic-vertical.png"));
        }
    }
}
