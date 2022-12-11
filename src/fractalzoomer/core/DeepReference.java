package fractalzoomer.core;

import java.util.Arrays;

public class DeepReference {
    public double[] mantsRe;
    public double[] mantsIm;
    public long[] exps;

    private int lengthOverride;

    public DeepReference(int length) {
        mantsRe = new double[length];
        mantsIm = new double[length];
        exps = new long[length];
    }

    public DeepReference(int length, int lengthOverride) {
        mantsRe = new double[length];
        mantsIm = new double[length];
        exps = new long[length];
        this.lengthOverride = lengthOverride;
    }

    public void resize(int length) {
        mantsRe = Arrays.copyOf(mantsRe, length);
        mantsIm = Arrays.copyOf(mantsIm, length);
        exps = Arrays.copyOf(exps, length);
    }

    public int length() {
        return lengthOverride != 0 ? lengthOverride : mantsRe.length;
    }
}
