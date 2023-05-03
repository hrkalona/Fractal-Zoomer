package fractalzoomer.core;

import fractalzoomer.main.Constants;

import java.util.Arrays;

public class PixelExtraData {
    public double[] values;
    public int[] rgb_values;
    public boolean[] escaped;

    public PixelExtraData() {

    }
    public PixelExtraData(PixelExtraData other, int skippedColor) {
        values = new double[other.values.length];
        rgb_values = new int[other.rgb_values.length];
        escaped = new boolean[other.escaped.length];
        for(int i = 0; i < values.length; i++) {
            values[i] = other.values[i];
            rgb_values[i] = skippedColor;
            escaped[i] = other.escaped[i];
        }
    }

    private void initialize(int length) {

        if(values == null || values.length != length) {
            values = new double[length];
            rgb_values = new int[length];
            escaped = new boolean[length];
        }

        Arrays.fill(rgb_values, 0, length, Constants.EMPTY_COLOR);
    }

    public void set(int index, int rgb, double value, boolean esc, int totalSamples) {
        if(index == 0) {
            initialize(totalSamples);
        }
        rgb_values[index] = rgb;
        values[index] = value;
        escaped[index] = esc;
    }

}
