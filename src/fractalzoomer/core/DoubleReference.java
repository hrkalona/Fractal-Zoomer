package fractalzoomer.core;

import java.util.Arrays;

public class DoubleReference {
    public double[] re;
    public double[] im;
    private int lengthOverride;

    public DoubleReference(int length) {
        re = new double[length];
        im = new double[length];
    }

    public DoubleReference(int length, int lengthOverride) {
        re = new double[length];
        im = new double[length];
        this.lengthOverride = lengthOverride;
    }

    public void resize(int length) {
        re = Arrays.copyOf(re, length);
        im = Arrays.copyOf(im, length);
    }

    public int length() {
        return lengthOverride != 0 ? lengthOverride : re.length;
    }
}
