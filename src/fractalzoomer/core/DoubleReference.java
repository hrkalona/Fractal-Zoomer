package fractalzoomer.core;

import java.util.Arrays;

public class DoubleReference {
    public double[] re;
    public double[] im;
    public int length;
    private int lengthOverride;
    public boolean saveMemory;
    private static final int SAVE_MEMORY_THRESHOLD = 100_000_000;

    public DoubleReference(int length) {
        if(length > SAVE_MEMORY_THRESHOLD) {
            re = new double[SAVE_MEMORY_THRESHOLD];
            im = new double[SAVE_MEMORY_THRESHOLD];
            saveMemory = true;
        }
        else {
            re = new double[length];
            im = new double[length];
            saveMemory = false;
        }

        this.length = length;
    }

    public DoubleReference(int length, int lengthOverride) {

        this.lengthOverride = lengthOverride;
        re = new double[length];
        im = new double[length];
        saveMemory = false;
        this.length = length;
    }

    public void setLengthOverride(int lengthOverride) {
        this.lengthOverride = lengthOverride;
    }

    public void resize(int length) {
        if(length > SAVE_MEMORY_THRESHOLD) {

            int newLength = re.length + SAVE_MEMORY_THRESHOLD;
            int maxLength = length;
            newLength = newLength > maxLength ? maxLength : newLength;

            re = Arrays.copyOf(re, newLength);
            im = Arrays.copyOf(im, newLength);
            saveMemory = true;
        }
        else {
            re = Arrays.copyOf(re, length);
            im = Arrays.copyOf(im, length);
            saveMemory = false;
        }

        this.length = length;
    }

    public int length() {
        return lengthOverride != 0 ? lengthOverride : length;
    }

    public void checkAllocation(int index) {

        int currentLength = re.length;
        if(index >= currentLength) {
            int newLength = currentLength + SAVE_MEMORY_THRESHOLD;
            newLength = newLength > length ? length : newLength;
            re = Arrays.copyOf(re, newLength);
            im = Arrays.copyOf(im, newLength);
        }

    }
}
