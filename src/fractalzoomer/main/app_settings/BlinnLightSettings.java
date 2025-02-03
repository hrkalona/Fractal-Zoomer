package fractalzoomer.main.app_settings;

public class BlinnLightSettings {
    public double ambient;
    public double[] colorAmbient;
    public double[] diffuse;
    public double[] specular;
    public double shininess;
    public double polarAngle;
    public double azimuthAngle;
    public double[] color;
    public Double[] materialSpecularColor;
    public double heightTransferFactor;
    public int heightTransfer;
    public int fractionalTransfer;
    public int fractionalSmoothing;
    public int fractionalTransferMode;
    public double fractionalTransferScale;
    public double bls_noise_reducing_factor;
    public boolean lighting;

    public BlinnLightSettings(BlinnLightSettings other) {
        lighting = other.lighting;
        ambient = other.ambient;
        colorAmbient = other.colorAmbient;
        diffuse = other.diffuse;
        specular = other.specular;
        shininess = other.shininess;
        polarAngle = other.polarAngle;
        azimuthAngle = other.azimuthAngle;
        color = other.color;
        heightTransferFactor = other.heightTransferFactor;
        heightTransfer = other.heightTransfer;
        fractionalTransfer = other.fractionalTransfer;
        fractionalSmoothing = other.fractionalSmoothing;
        fractionalTransferMode = other.fractionalTransferMode;
        fractionalTransferScale = other.fractionalTransferScale;
        bls_noise_reducing_factor = other.bls_noise_reducing_factor;
        materialSpecularColor = other.materialSpecularColor;
    }

    public BlinnLightSettings() {
        lighting = false;
        ambient = 0.7;
        colorAmbient = new double[] {1, 1, 1};
        diffuse = new double[] {3, 3, 3};
        specular = new double[] {50, 50, 50};
        shininess = 400;
        polarAngle = 225;
        azimuthAngle = 30;
        color = new double[] {2, 2, 2};
        heightTransferFactor = 10;
        heightTransfer = 0;
        fractionalTransfer = 0;
        fractionalSmoothing = 0;
        fractionalTransferMode = 1;
        fractionalTransferScale = 1;
        bls_noise_reducing_factor = 1e-10;
        materialSpecularColor = new Double[] {null, null, null};
    }
}
