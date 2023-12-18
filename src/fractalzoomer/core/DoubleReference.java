package fractalzoomer.core;

import java.util.Arrays;

public class DoubleReference {
    public double[] re;
    public double[] im;
    protected int length;
    protected int lengthOverride;
    public boolean saveMemory;
    public static final int SAVE_MEMORY_THRESHOLD = 100_000_000;
    public static final int SAVE_MEMORY_THRESHOLD2 = 50_000_000;
    public static boolean SHOULD_SAVE_MEMORY = false;
    public int id;
    public boolean compressed;

    public DoubleReference() {
        id = -1;
        compressed = false;
    }

    public DoubleReference(int length) {

        int actualLength = getCreationLength(length);

        re = new double[actualLength];
        im = new double[actualLength];

        saveMemory = length != actualLength;

        this.length = length;

        id = -1;
        compressed = false;
    }

    private int getCreationLength(int length) {
        return SHOULD_SAVE_MEMORY && length > SAVE_MEMORY_THRESHOLD ? SAVE_MEMORY_THRESHOLD : length;
    }

    public boolean shouldCreateNew(int length) {
        int actualLength = getCreationLength(length);
        return  re == null || im == null || re.length != actualLength || im.length != actualLength;
    }

    public void reset() {
        Arrays.fill(re, 0, re.length, 0);
        Arrays.fill(im, 0, im.length, 0);
    }

    public DoubleReference(int length, int lengthOverride) {

        this.lengthOverride = lengthOverride;
        re = new double[length];
        im = new double[length];
        saveMemory = false;
        this.length = length;
        id = -1;
        compressed = false;
    }

    public void setLengthOverride(int lengthOverride) {
        this.lengthOverride = lengthOverride;
    }

    public void resize(int length) {
        if(SHOULD_SAVE_MEMORY && length > SAVE_MEMORY_THRESHOLD) {

            int newLength = re.length + SAVE_MEMORY_THRESHOLD;
            newLength = Math.min(newLength, length);

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

    public int dataLength() {
        return length;
    }

    public void checkAllocation(int index) {

        int currentLength = re.length;
        if(index >= currentLength) {
            int newLength = currentLength + SAVE_MEMORY_THRESHOLD2;
            newLength = Math.min(newLength, length);
            re = Arrays.copyOf(re, newLength);
            im = Arrays.copyOf(im, newLength);
        }

    }
}
