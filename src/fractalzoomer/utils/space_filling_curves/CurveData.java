package fractalzoomer.utils.space_filling_curves;

import fractalzoomer.main.Constants;
import fractalzoomer.palettes.CustomPalette;

public class CurveData implements Comparable<CurveData> {
    public int x, y;
    public long order;
    static int[][] custom_palette = new int[][] {
            {12, 116, 59, 174},
            {12, 174, 239, 75},
            {1, 109, 64, 180}
    };
    public static final int[] palette = CustomPalette.createPalette(custom_palette, false, 0, 0, Constants.INTERPOLATION_LINEAR, Constants.COLOR_SPACE_CUBEHELIX, 0);//PresetsPalettes.spectrum_palette;
    public static int offset = 10;

    public static boolean drawCircles = false;
    public static boolean drawColor = true;
    public static boolean drawNumbers = false;
    public static boolean changeStroke = false;

    @Override
    public int compareTo(CurveData o) {
        return Long.compare(order, o.order);
    }
}
