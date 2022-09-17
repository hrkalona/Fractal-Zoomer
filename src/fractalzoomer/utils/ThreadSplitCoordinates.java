package fractalzoomer.utils;

public class ThreadSplitCoordinates {
    public int FROMx;
    public int TOx;
    public int FROMy;
    public int TOy;

    public ThreadSplitCoordinates(int FROMx, int TOx, int FROMy, int TOy) {
        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
    }

    public static ThreadSplitCoordinates get(int j, int i, int thread_grouping, int n, int image_size) {

        switch (thread_grouping) {
            case 0:
                return new ThreadSplitCoordinates(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n);
            case 1:
                return new ThreadSplitCoordinates(0, image_size, j * image_size / n, (j + 1) * image_size / n);
            case 2:
                return new ThreadSplitCoordinates(j * image_size / n, (j + 1) * image_size / n, 0, image_size);
        }

        return null;

    }

}
