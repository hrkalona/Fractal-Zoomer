package fractalzoomer.core;

import java.util.Arrays;

public class DeepReference {
    public double[] mantsRe;
    public double[] mantsIm;
    public long[] exps;

    private int lengthOverride;
    public int length;

    public boolean saveMemory;
    private static final int SAVE_MEMORY_THRESHOLD = 100_000_000;

    public DeepReference(int length) {

        if(length > SAVE_MEMORY_THRESHOLD) {
            mantsRe = new double[SAVE_MEMORY_THRESHOLD];
            mantsIm = new double[SAVE_MEMORY_THRESHOLD];
            exps = new long[SAVE_MEMORY_THRESHOLD];
            saveMemory = true;
        }
        else {
            mantsRe = new double[length];
            mantsIm = new double[length];
            exps = new long[length];
            saveMemory = false;
        }
        this.length = length;

    }

    public DeepReference(int length, int lengthOverride) {
        mantsRe = new double[length];
        mantsIm = new double[length];
        exps = new long[length];
        saveMemory = false;
        this.lengthOverride = lengthOverride;
        this.length = length;
    }

    public void resize(int length) {

        if(length > SAVE_MEMORY_THRESHOLD) {

            int newLength = mantsRe.length + SAVE_MEMORY_THRESHOLD;
            int maxLength = length;
            newLength = newLength > maxLength ? maxLength : newLength;

            mantsRe = Arrays.copyOf(mantsRe, newLength);
            mantsIm = Arrays.copyOf(mantsIm, newLength);
            exps = Arrays.copyOf(exps, newLength);
            saveMemory = true;
        }
        else {
            mantsRe = Arrays.copyOf(mantsRe, length);
            mantsIm = Arrays.copyOf(mantsIm, length);
            exps = Arrays.copyOf(exps, length);
            saveMemory = false;
        }
        this.length = length;
    }

    public void setLengthOverride(int lengthOverride) {
        this.lengthOverride = lengthOverride;
    }

    public int length() {
        return lengthOverride != 0 ? lengthOverride : length;
    }

    public void checkAllocation(int index) {

        int currentLength = mantsRe.length;
        if(index >= currentLength) {
            int newLength = currentLength + SAVE_MEMORY_THRESHOLD;
            newLength = newLength > length ? length : newLength;
            mantsRe = Arrays.copyOf(mantsRe, newLength);
            mantsIm = Arrays.copyOf(mantsIm, newLength);
            exps = Arrays.copyOf(exps, newLength);
        }

    }
}
