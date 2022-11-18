package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1084 extends SettingsFractals1083 implements Serializable {
    private static final long serialVersionUID = 743452034712L;

    private boolean useGeneratedPaletteOutColoring;
    private int generatedPaletteOutColoringId;
    private boolean useGeneratedPaletteInColoring;
    private int generatedPaletteInColoringId;
    private int restartGeneratedOutColoringPaletteAt;
    private int restartGeneratedInColoringPaletteAt;
    private boolean enableJitter;
    private int jitterSeed;
    private int jitterShape;
    private double jitterScale;

    public SettingsFractals1084(Settings s) {
        super(s);
        enableJitter = s.js.enableJitter;
        jitterSeed = s.js.jitterSeed;
        jitterShape = s.js.jitterShape;
        jitterScale = s.js.jitterScale;

        restartGeneratedInColoringPaletteAt = s.gps.restartGeneratedInColoringPaletteAt;
        restartGeneratedOutColoringPaletteAt = s.gps.restartGeneratedOutColoringPaletteAt;
        generatedPaletteInColoringId = s.gps.generatedPaletteInColoringId;
        useGeneratedPaletteInColoring = s.gps.useGeneratedPaletteInColoring;
        generatedPaletteOutColoringId = s.gps.generatedPaletteOutColoringId;
        useGeneratedPaletteOutColoring = s.gps.useGeneratedPaletteOutColoring;

    }

    @Override
    public int getVersion() {

        return 1084;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public boolean getUseGeneratedPaletteOutColoring() {
        return useGeneratedPaletteOutColoring;
    }

    public int getGeneratedPaletteOutColoringId() {
        return generatedPaletteOutColoringId;
    }

    public boolean getUseGeneratedPaletteInColoring() {
        return useGeneratedPaletteInColoring;
    }

    public int getGeneratedPaletteInColoringId() {
        return generatedPaletteInColoringId;
    }

    public int getRestartGeneratedOutColoringPaletteAt() {
        return restartGeneratedOutColoringPaletteAt;
    }

    public int getRestartGeneratedInColoringPaletteAt() {
        return restartGeneratedInColoringPaletteAt;
    }

    public boolean getEnableJitter() {
        return enableJitter;
    }

    public int getJitterSeed() {
        return jitterSeed;
    }

    public int getJitterShape() {
        return jitterShape;
    }

    public double getJitterScale() {
        return jitterScale;
    }
}
