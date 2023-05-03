package fractalzoomer.main.app_settings;

public class ApproximationDefaultSettings {
    public static int DETECTION_METHOD = 1;
    public static double Stage0PeriodDetectionThreshold = 0x1.0p-10;
    public static double PeriodDetectionThreshold = 0x1.0p-10;
    public static double Stage0PeriodDetectionThreshold2 = 0x1.0p-6;
    public static double PeriodDetectionThreshold2 = 0x1.0p-3;
    public static double LAThresholdScale = 0x1.0p-24;
    public static double LAThresholdCScale = 0x1.0p-24;

    public static int SERIES_APPROXIMATION_TERMS = 5;
    public static long SERIES_APPROXIMATION_OOM_DIFFERENCE = 2;
    public static int SERIES_APPROXIMATION_MAX_SKIP_ITER = Integer.MAX_VALUE;

    public static int BLA_BITS = 23;
    public static int BLA_STARTING_LEVEL = 2;

    public static int NANOMB1_N = 8;
    public static int NANOMB1_M = 16;
}
