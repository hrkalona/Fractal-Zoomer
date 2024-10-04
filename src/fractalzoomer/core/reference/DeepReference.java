package fractalzoomer.core.reference;

import fractalzoomer.core.TaskRender;

import java.util.Arrays;

import static fractalzoomer.core.reference.DoubleReference.*;

public class DeepReference {
    public double[] mantsRe;
    public double[] mantsIm;
    public long[] exps;

    public long[] expsIm;

    protected int lengthOverride;
    protected int length;

    public boolean saveMemory;
    public int id;
    public boolean compressed;

    public DeepReference() {
        id = -1;
        compressed = false;
    }
    public DeepReference(int length) {

        int actualLength = getCreationLength(length);

        mantsRe = new double[actualLength];
        mantsIm = new double[actualLength];
        exps = new long[actualLength];
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            expsIm = new long[actualLength];
        }
        saveMemory = length != actualLength;

        this.length = length;
        id = -1;
        compressed = false;

    }

    public DeepReference(int length, int lengthOverride) {
        mantsRe = new double[length];
        mantsIm = new double[length];
        exps = new long[length];

        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            expsIm = new long[length];
        }
        saveMemory = false;
        this.lengthOverride = lengthOverride;
        this.length = length;
        id = -1;
        compressed = false;
    }

    private int getCreationLength(int length) {
        return SHOULD_SAVE_MEMORY && length > SAVE_MEMORY_THRESHOLD ? SAVE_MEMORY_THRESHOLD : length;
    }

    public boolean shouldCreateNew(int length) {
        int actualLength = getCreationLength(length);

        boolean res = mantsRe == null || mantsIm == null || exps == null
                || mantsRe.length != actualLength || mantsIm.length != actualLength || exps.length != actualLength;

        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            res = res || expsIm == null || expsIm.length != actualLength;
        }

        return res;
    }

    public void reset() {
        Arrays.fill(mantsRe, 0);
        Arrays.fill(mantsIm, 0);
        Arrays.fill(exps,  0);
        if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
            Arrays.fill(expsIm, 0);
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

            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                expsIm = Arrays.copyOf(expsIm, newLength);
            }
            saveMemory = true;
        }
        else {
            mantsRe = Arrays.copyOf(mantsRe, length);
            mantsIm = Arrays.copyOf(mantsIm, length);
            exps = Arrays.copyOf(exps, length);

            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
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
            if(TaskRender.MANTEXPCOMPLEX_FORMAT == 1) {
                expsIm = Arrays.copyOf(expsIm, newLength);
            }
        }

    }
}

