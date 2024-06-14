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

    public static TaskSplitCoordinates get(int j, int i, int task_grouping, int n, int m, int width, int height) {

        switch (task_grouping) {
            case 0:
                return new TaskSplitCoordinates(j * width / n, (j + 1) * width / n, i * height / n, (i + 1) * height / n);
            case 1:
                return new TaskSplitCoordinates(0, width, j * height / n, (j + 1) * height / n);
            case 2:
                return new TaskSplitCoordinates(j * width / n, (j + 1) * width / n, 0, height);
            case 3:
                return new TaskSplitCoordinates(i * width / m, (i + 1) * width / m, j * height / n, (j + 1) * height / n);
            case 4:
            case 5:
                return new TaskSplitCoordinates(j * width / n, (j + 1) * width / n, i * height / m, (i + 1) * height / m);
        }

        return null;

    }

}
