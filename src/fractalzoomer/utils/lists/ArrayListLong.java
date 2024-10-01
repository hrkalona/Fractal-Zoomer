package fractalzoomer.utils.lists;

import java.util.Arrays;

public class ArrayListLong {
    private static int INIT_SIZE = 256;
    private long[] data;
    int elements;

    public ArrayListLong(int initial_size) {
        data = new long[initial_size];
        elements = 0;
    }

    public ArrayListLong() {
        data = new long[INIT_SIZE];
        elements = 0;
    }

    public void add(int value) {
        data[elements] = value;
        elements++;
        if (elements >= data.length) {
            long newLen  = ((long) data.length) << 1;
            if(newLen > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("No space");
            }
            data = Arrays.copyOf(data, (int)newLen);
        }
    }

    public void addAll(ArrayListLong other) {
        long[] otherData = other.data;
        int otherElements = other.elements;
        int totalElements = elements + otherElements;
        if (totalElements >= data.length) {
            long newLen  = ((long) totalElements) << 1;
            if(newLen > Integer.MAX_VALUE) {
                throw new IndexOutOfBoundsException("No space");
            }
            data = Arrays.copyOf(data, (int)newLen);
        }
        for(int i = 0; i < otherElements; i++) {
            data[i + elements] = otherData[i];
        }
        elements = totalElements;
    }

    public long get(int index) {
        return data[index];
    }

    public int size() {
        return elements;
    }

    public boolean isEmpty() {
        return elements == 0;
    }
}
