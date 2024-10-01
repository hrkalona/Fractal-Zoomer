package fractalzoomer.utils.big_arrays;

import java.util.Arrays;

public class BigArrayDouble {
    private double [][] array;
    public long length;

    public BigArrayDouble(long length) {
        this.length = length;
        int chucks = (int)(length >> BigArray.SHIFT) + 1;
        int remaining = (int)(length & BigArray.MAX_VALUE_M1);

        array = new double[chucks][];

        if(chucks == 1) {
            array[0] = new double[remaining];
        }
        else {
            int i = 0;
            for (; i < array.length - 1; i++) {
                array[i] = new double[BigArray.MAX_VALUE];
            }
            array[i] = new double[remaining];
        }

    }

    public double get(long index) {
        int i = (int)(index >> BigArray.SHIFT);
        int j = (int)(index & BigArray.MAX_VALUE_M1);
        return array[i][j];
    }

    public void set(long index, double val) {
        int i = (int)(index >> BigArray.SHIFT);
        int j = (int)(index & BigArray.MAX_VALUE_M1);
        array[i][j] = val;
    }

    public void resize(long newLength) {

        if(length == newLength) {
            return;
        }

        this.length = newLength;
        int chucks = (int)(length >> BigArray.SHIFT) + 1;
        int remaining = (int)(length & BigArray.MAX_VALUE_M1);

        double[][] old_array = array;

        array = new double[chucks][];

        int i = 0;
        for (; i < array.length - 1; i++) {
            array[i] = Arrays.copyOf(old_array[i], BigArray.MAX_VALUE);
        }

        array[i] = Arrays.copyOf(old_array[i], remaining);
    }
}
