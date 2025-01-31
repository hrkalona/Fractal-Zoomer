package fractalzoomer.main.app_settings;

public class SlopeSettings {
    public boolean slopes;
    public double SlopeAngle;
    public double SlopePower;
    public double SlopeRatio;

    public double[] lightVector;

    public double s_noise_reducing_factor;

    public int colorMode;
    public double slope_blending;

    public int fractionalTransfer;
    public int fractionalSmoothing;
    public int fractionalTransferMode;
    public double fractionalTransferScale;

    public int heightTransfer;
    public double heightTransferFactor;
    public boolean applyWidthScaling;
    public SlopeSettings() {
        slopes = false;
        applyWidthScaling = false;
        SlopeAngle = 135;
        SlopeRatio = 0.2;
        SlopePower = 0.5;

        heightTransfer = 0;
        heightTransferFactor = 10;

        lightVector = new double[2];
        double lightAngleRadians = Math.toRadians(SlopeAngle);
        lightVector[0] = Math.cos(lightAngleRadians);
        lightVector[1] = Math.sin(lightAngleRadians);

        s_noise_reducing_factor = 1e-10;

        colorMode = 0;
        slope_blending = 0.5;

        fractionalTransfer = 0;
        fractionalSmoothing = 0;
        fractionalTransferMode = 1;
        fractionalTransferScale = 1;
    }

    public SlopeSettings(SlopeSettings copy) {
        slopes = copy.slopes;
        SlopeAngle = copy.SlopeAngle;
        SlopeRatio = copy.SlopeRatio;
        SlopePower = copy.SlopePower;

        heightTransfer = copy.heightTransfer;
        heightTransferFactor = copy.heightTransferFactor;

        lightVector = new double[2];
        double lightAngleRadians = Math.toRadians(copy.SlopeAngle);
        lightVector[0] = Math.cos(lightAngleRadians);
        lightVector[1] = Math.sin(lightAngleRadians);

        s_noise_reducing_factor = copy.s_noise_reducing_factor;

        colorMode = copy.colorMode;
        slope_blending = copy.slope_blending;

        fractionalTransfer = copy.fractionalTransfer;
        fractionalSmoothing = copy.fractionalSmoothing;
        fractionalTransferMode = copy.fractionalTransferMode;
        fractionalTransferScale = copy.fractionalTransferScale;
        applyWidthScaling = copy.applyWidthScaling;
    }
}
