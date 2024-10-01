package fractalzoomer.utils.space_filling_curves;

import fractalzoomer.palettes.PresetsPalettes;

public class CurveData implements Comparable<CurveData> {
    public int x, y;
    public long order;
    public static final int[] palette = PresetsPalettes.spectrum_palette;
    public static int offset = 20;

    public static boolean drawCircles = false;
    public static boolean drawColor = true;
    public static boolean drawNumbers = false;
    public static boolean changeStroke = false;

    @Override
    public int compareTo(CurveData o) {
        return Long.compare(order, o.order);
    }
}
