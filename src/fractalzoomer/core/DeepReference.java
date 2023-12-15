package fractalzoomer.core;

import java.util.Arrays;

import static fractalzoomer.core.DoubleReference.*;

public class DeepReference {
    public double[] mantsRe;
    public double[] mantsIm;
    public long[] exps;

    public long[] expsIm;

    private int lengthOverride;
    public int length;

    public boolean saveMemory;
    public DeepReference(int length) {

        int actualLength = getCreationLength(length);

        mantsRe = new double[actualLength];
        mantsIm = new double[actualLength];
        exps = new long[actualLength];
        if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
            expsIm = new long[actualLength];
        }
        saveMemory = length != actualLength;

        this.length = length;

    }

    public DeepReference(int length, int lengthOverride) {
        mantsRe = new double[length];
        mantsIm = new double[length];
        exps = new long[length];

        if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
            expsIm = new long[length];
        }
        saveMemory = false;
        this.lengthOverride = lengthOverride;
        this.length = length;
    }

    private int getCreationLength(int length) {
        return SHOULD_SAVE_MEMORY && length > SAVE_MEMORY_THRESHOLD ? SAVE_MEMORY_THRESHOLD : length;
    }

    public boolean shouldCreateNew(int length) {
        int actualLength = getCreationLength(length);

        boolean res = mantsRe == null || mantsIm == null || exps == null
                || mantsRe.length != actualLength || mantsIm.length != actualLength || exps.length != actualLength;

        if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
            res = res || expsIm == null || expsIm.length != actualLength;
        }

        return res;
    }

    public void reset() {
        Arrays.fill(mantsRe, 0, mantsRe.length, 0);
        Arrays.fill(mantsIm, 0, mantsIm.length, 0);
        Arrays.fill(exps, 0, exps.length, 0);
        if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
            Arrays.fill(expsIm, 0, expsIm.length, 0);
        }

    }

    public void resize(int length) {

        if(SHOULD_SAVE_MEMORY && length > SAVE_MEMORY_THRESHOLD) {

            int newLength = mantsRe.length + SAVE_MEMORY_THRESHOLD;
            int maxLength = length;
            newLength = newLength > maxLength ? maxLength : newLength;

            mantsRe = Arrays.copyOf(mantsRe, newLength);
            mantsIm = Arrays.copyOf(mantsIm, newLength);
            exps = Arrays.copyOf(exps, newLength);

            if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                expsIm = Arrays.copyOf(expsIm, newLength);
            }
            saveMemory = true;
        }
        else {
            mantsRe = Arrays.copyOf(mantsRe, length);
            mantsIm = Arrays.copyOf(mantsIm, length);
            exps = Arrays.copyOf(exps, length);

            if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                expsIm = Arrays.copyOf(expsIm, length);
            }
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
            int newLength = currentLength + SAVE_MEMORY_THRESHOLD2;
            newLength = newLength > length ? length : newLength;
            mantsRe = Arrays.copyOf(mantsRe, newLength);
            mantsIm = Arrays.copyOf(mantsIm, newLength);
            exps = Arrays.copyOf(exps, newLength);
            if(TaskDraw.MANTEXPCOMPLEX_FORMAT == 1) {
                expsIm = Arrays.copyOf(expsIm, newLength);
            }
        }

    }
}

