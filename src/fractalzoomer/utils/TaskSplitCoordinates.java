package fractalzoomer.utils;

public class TaskSplitCoordinates {
    public int FROMx;
    public int TOx;
    public int FROMy;
    public int TOy;

    public TaskSplitCoordinates(int FROMx, int TOx, int FROMy, int TOy) {
        this.FROMx = FROMx;
        this.TOx = TOx;
        this.FROMy = FROMy;
        this.TOy = TOy;
    }

    public static TaskSplitCoordinates get(int j, int i, int task_grouping, int n, int m, int image_size) {

        switch (task_grouping) {
            case 0:
                return new TaskSplitCoordinates(j * image_size / n, (j + 1) * image_size / n, i * image_size / n, (i + 1) * image_size / n);
            case 1:
                return new TaskSplitCoordinates(0, image_size, j * image_size / n, (j + 1) * image_size / n);
            case 2:
                return new TaskSplitCoordinates(j * image_size / n, (j + 1) * image_size / n, 0, image_size);
            case 3:
                return new TaskSplitCoordinates(i * image_size / m, (i + 1) * image_size / m, j * image_size / n, (j + 1) * image_size / n);
            case 4:
            case 5:
                return new TaskSplitCoordinates(j * image_size / n, (j + 1) * image_size / n, i * image_size / m, (i + 1) * image_size / m);
        }

        return null;

    }

}
