package fractalzoomer.utils;

import java.awt.*;

public class TaskStatistic implements Comparable<TaskStatistic> {
    private int taskId;
    private String threadName;
    private long pixelCalculationTime;
    private long postProcessingCalculationTime;
    private long pixels_calculated;
    private long pixels_completed;
    private long extra_pixels_calculated;

    private long pixels_post_processed;

    private long total_pixels;

    private int samples;
    private Color threadColor;

    public TaskStatistic(int taskId, String threadName, long pixelCalculationTime, long postProcessingCalculationTime, long pixels_calculated, long extra_pixels_calculated, long pixels_completed, long pixels_post_processed, long total_pixels, int samples, Color threadColor) {
        this.taskId = taskId;
        this.threadName = threadName;
        this.pixelCalculationTime = pixelCalculationTime;
        this.pixels_calculated = pixels_calculated;
        this.postProcessingCalculationTime = postProcessingCalculationTime;
        this.samples = samples;
        this.extra_pixels_calculated = extra_pixels_calculated;
        this.total_pixels = total_pixels;
        this.threadColor = threadColor;
        if(pixels_completed == -1 && pixels_calculated >= 0) {
            this.pixels_completed = pixels_calculated;
        }
        else {
            this.pixels_completed = pixels_completed;
        }
        this.pixels_post_processed = pixels_post_processed;
    }

    @Override
    public int compareTo(TaskStatistic o) {
        return Long.compare(pixelCalculationTime, o.pixelCalculationTime);
    }

    public String getThreadName() {
        return threadName;
    }

    public long getPixelCalculationTime() {
        return pixelCalculationTime;
    }

    public long getPixelsCalculated() {
        return pixels_calculated * samples;
    }

    public int getTaskId() {
        return taskId;
    }
    public long getPostProcessingCalculationTime() {
        return postProcessingCalculationTime;
    }

    public long getExtraPixelsCalculated() {
        return extra_pixels_calculated * samples;
    }

    public long getTotalPixels() { return total_pixels * samples; }

    public long getPixelsCompleted() {return pixels_completed * samples;}

    public long getPixelsPostProcessed() {return pixels_post_processed * samples;}

    public Color getThreadColor() {
        return threadColor;
    }


}
