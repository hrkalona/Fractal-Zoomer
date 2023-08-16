package fractalzoomer.utils;

import java.util.Arrays;

public class BigArrayInt {

    private int [][] array;
    public long length;

    public BigArrayInt(long length) {
        this.length = length;
        int chucks = (int)(length >> BigArray.SHIFT) + 1;
        int remaining = (int)(length & BigArray.MAX_VALUE_M1);

        array = new int[chucks][];

        if(chucks == 1) {
            array[0] = new int[remaining];
        }
        else {
            int i = 0;
            for (; i < array.length - 1; i++) {
                array[i] = new int[BigArray.MAX_VALUE];
            }
            array[i] = new int[remaining];
        }

    }

    public int get(long index) {
        int i = (int)(index >> BigArray.SHIFT);
        int j = (int)(index & BigArray.MAX_VALUE_M1);
        return array[i][j];
    }

    public void set(long index, int val) {
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

        int[][] old_array = array;

        array = new int[chucks][];

        int i = 0;
        for (; i < array.length - 1; i++) {
            array[i] = Arrays.copyOf(old_array[i], BigArray.MAX_VALUE);
        }

        array[i] = Arrays.copyOf(old_array[i], remaining);
    }

}
