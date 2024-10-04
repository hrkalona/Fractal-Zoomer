package fractalzoomer.main.app_settings;

public class NumericalDistanceEstimatorSettings {
    public boolean useNumericalDem;
    public double distanceFactor;
    public int distanceOffset;
    public int differencesMethod;

    public double n_noise_reducing_factor;
    public double numerical_blending;
    public boolean cap_to_palette_length;
    //public boolean useJitter;
    public int nde_color_blending;
    public boolean nde_reverse_color_blending;
    public boolean applyWidthScaling;

    public NumericalDistanceEstimatorSettings() {
        useNumericalDem = false;
        applyWidthScaling = false;
        distanceFactor = 2;
        differencesMethod = 0;
        n_noise_reducing_factor = 1e-10;
        numerical_blending = 1;
        //useJitter = false;
        distanceOffset = 0;
        cap_to_palette_length = false;
        nde_color_blending = 0;
        nde_reverse_color_blending = false;
    }
}
