package fractalzoomer.utils.lists;

import java.util.Arrays;

public class ArrayListInt {
    private static int INIT_SIZE = 256;
    private int[] data;
    int elements;

    public ArrayListInt(int initial_size) {
        data = new int[initial_size];
        elements = 0;
    }

    public ArrayListInt() {
        data = new int[INIT_SIZE];
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

    public void addAll(ArrayListInt other) {
        int[] otherData = other.data;
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

    public int get(int index) {
        return data[index];
    }

    public int size() {
        return elements;
    }

    public boolean isEmpty() {
        return elements == 0;
    }
}
