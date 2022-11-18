package fractalzoomer.main.app_settings;

public class JitterSettings {
    public boolean enableJitter;
    public int jitterSeed;
    public int jitterShape;
    public double jitterScale;

    public JitterSettings() {
        enableJitter = false;
        jitterSeed = 1;
        jitterShape = 0;
        jitterScale = 1.0;
    }
}
