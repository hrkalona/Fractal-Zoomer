package fractalzoomer.core.approximation.la_zhuoran;

import fractalzoomer.core.MantExp;

public class MagnitudeDetectionDeep extends MagnitudeDetectionBase {
    public static MantExp DipDetectionThreshold = new MantExp(MagnitudeDetection.DipDetectionThreshold);
    public static MantExp Stage0DipDetectionThreshold = new MantExp(MagnitudeDetection.Stage0DipDetectionThreshold);
    public MantExp magnitude;

    public MagnitudeDetectionDeep(MantExp magnitude) {
        this.magnitude = magnitude;
        this.magnitude.Normalize();
    }

    @Override
    public boolean lessThan(MagnitudeDetectionBase other) {
        return magnitude.compareToBothPositive(((MagnitudeDetectionDeep)other).magnitude) < 0;
    }

    @Override
    public boolean lessThanWithThreshold(MagnitudeDetectionBase other) {
        return magnitude.compareToBothPositive(((MagnitudeDetectionDeep)other).magnitude.multiply(DipDetectionThreshold)) < 0;
    }

    @Override
    public boolean lessThanWithThresholdStage0(MagnitudeDetectionBase other) {
        return magnitude.compareToBothPositive(((MagnitudeDetectionDeep)other).magnitude.multiply(Stage0DipDetectionThreshold)) < 0;
    }
}
