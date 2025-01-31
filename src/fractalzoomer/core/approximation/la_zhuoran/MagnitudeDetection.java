package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.main.app_settings.ApproximationDefaultSettings;

public class MagnitudeDetection extends MagnitudeDetectionBase {
    public static double Stage0DipDetectionThreshold = ApproximationDefaultSettings.Stage0DipDetectionThreshold3;
    public static double DipDetectionThreshold = ApproximationDefaultSettings.DipDetectionThreshold3;
    public double magnitude;

    public MagnitudeDetection(double magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public boolean lessThan(MagnitudeDetectionBase other) {
        return magnitude < ((MagnitudeDetection)other).magnitude;
    }

    @Override
    public boolean lessThanWithThreshold(MagnitudeDetectionBase other) {
        return magnitude < ((MagnitudeDetection)other).magnitude * DipDetectionThreshold;
    }
    @Override
    public boolean lessThanWithThresholdStage0(MagnitudeDetectionBase other) {
        return magnitude < ((MagnitudeDetection)other).magnitude * Stage0DipDetectionThreshold;
    }
}
