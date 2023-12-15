package fractalzoomer.utils;

import java.lang.reflect.Array;

public class BigArray<T> {
    //private static final long MAX_VALUE = Long.MAX_VALUE >> 10 // for doubles;
    public static final int SHIFT = 30;
    public static final int MAX_VALUE = 1 << SHIFT; //Needs to be power of 2
    public static final int MAX_VALUE_M1 = MAX_VALUE - 1;
    public long length;

    private Class<T> type;

    private T [][] array;

    public BigArray(Class<T> type, long length) {

        this.type = type;
        this.length = length;
        int chucks = (int)(length >> SHIFT) + 1;
        int remaining = (int)(length & MAX_VALUE_M1);

        array = (T [][]) Array.newInstance(type, chucks, 0);

        if(chucks == 1) {
            array[0] = (T []) Array.newInstance(type, remaining);
        }
        else {
            int i = 0;
            for (; i < array.length - 1; i++) {
                array[i] = (T []) Array.newInstance(type, MAX_VALUE);
            }
            array[i] = (T []) Array.newInstance(type, remaining);
        }
    }

    public T get(long index) {
        int i = (int)(index >> SHIFT);
        int j = (int)(index & MAX_VALUE_M1);
        return array[i][j];
    }

    public void set(long index, T val) {
        int i = (int)(index >> SHIFT);
        int j = (int)(index & MAX_VALUE_M1);
        array[i][j] = val;
    }

    public void resize(long newLength) {

        if(length == newLength) {
            return;
        }

        this.length = newLength;
        int chucks = (int)(length >> BigArray.SHIFT) + 1;
        int remaining = (int)(length & BigArray.MAX_VALUE_M1);

        T[][] old_array = array;

        array = (T [][]) Array.newInstance(type, chucks, 0);

        int i = 0;
        for (; i < array.length - 1; i++) {
            array[i] = (T []) Array.newInstance(type, MAX_VALUE);
            System.arraycopy(old_array[i], 0, array[i], 0, Math.min(old_array[i].length, array[i].length));
        }
        array[i] = (T []) Array.newInstance(type, remaining);
        System.arraycopy(old_array[i], 0, array[i], 0, Math.min(old_array[i].length, array[i].length));
    }

    public static void main(String[] args) {
//        double a = (Long.MAX_VALUE >> 27);
//        double b = (Long.MAX_VALUE >> 27) - 1;
//
//        System.out.println(a + 0.00001);
//        System.out.println(b + 0.00002);
//        System.out.println(Long.MAX_VALUE >> 27);
//
//        System.out.println(Integer.MAX_VALUE + 0.00001);
//        System.out.println(Integer.MAX_VALUE - 1 + 0.00001);

        BigArray<Double> b = new BigArray<>(Double.class, 400000000L);

        for(int i = 0; i < b.length; i++) {
            b.set(i, new Double(i + 1));
        }

        b.resize(10);

        for(int i = 0; i < b.length; i++) {
            System.out.println(b.get(i));
        }


    }
}
