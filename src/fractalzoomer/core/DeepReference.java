package fractalzoomer.core;

import java.util.Arrays;

public class DeepReference {
    public double[] mants;
    public long[] exps;

    public DeepReference(int length) {
        mants = new double[length << 1];
        exps = new long[length];
    }

    public void resize(int length) {
        mants = Arrays.copyOf(mants, length << 1);
        exps = Arrays.copyOf(exps, length);
    }

    public void clear() {
        mants = null;
        exps = null;
    }

    public int length() {
        return exps.length;
    }
}
