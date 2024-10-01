package fractalzoomer.utils.big_arrays;

import java.util.Arrays;

public class BigArrayLong {
    private long [][] array;
    public long length;

    public BigArrayLong(long length) {
        this.length = length;
        int chucks = (int)(length >> BigArray.SHIFT) + 1;
        int remaining = (int)(length & BigArray.MAX_VALUE_M1);

        array = new long[chucks][];

        if(chucks == 1) {
            array[0] = new long[remaining];
        }
        else {
            int i = 0;
            for (; i < array.length - 1; i++) {
                array[i] = new long[BigArray.MAX_VALUE];
            }
            array[i] = new long[remaining];
        }

    }

    public long get(long index) {
        int i = (int)(index >> BigArray.SHIFT);
        int j = (int)(index & BigArray.MAX_VALUE_M1);
        return array[i][j];
    }

    public void set(long index, long val) {
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

        long[][] old_array = array;

        array = new long[chucks][];

        int i = 0;
        for (; i < array.length - 1; i++) {
            array[i] = Arrays.copyOf(old_array[i], BigArray.MAX_VALUE);
        }

        array[i] = Arrays.copyOf(old_array[i], remaining);
    }
}
