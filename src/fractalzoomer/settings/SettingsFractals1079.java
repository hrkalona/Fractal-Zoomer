package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1079 extends SettingsFractals1078 implements Serializable {
    private static final long serialVersionUID = 732194L;

    private boolean useNormalMap;
    private int normalMapColorMode;
    private boolean normalMapOverrideColoring;
    private double normalMapLightFactor;
    private double normalMapBlending;
    private double normalMapHeight;
    private double normalMapAngle;
    private boolean normalMapUseSecondDerivative;
    private boolean normalMapUseDE;
    private double normalMapDEfactor;
    private boolean normalMapInvertDE;
    private int normalMapColoring;
    private boolean defaultNovaInitialValue;
    private double contourFactor;

    public SettingsFractals1079(Settings s) {
        super(s);
        useNormalMap = s.sts.useNormalMap;
        normalMapColorMode = s.sts.normalMapColorMode;
        normalMapOverrideColoring = s.sts.normalMapOverrideColoring;
        normalMapLightFactor = s.sts.normalMapLightFactor;
        normalMapBlending = s.sts.normalMapBlending;
        normalMapHeight = s.sts.normalMapHeight;
        normalMapAngle = s.sts.normalMapAngle;
        normalMapUseSecondDerivative = s.sts.normalMapUseSecondDerivative;
        normalMapUseDE = s.sts.normalMapUseDE;
        normalMapDEfactor = s.sts.normalMapDEfactor;
        normalMapInvertDE = s.sts.normalMapInvertDE;
        normalMapColoring = s.sts.normalMapColoring;
        defaultNovaInitialValue = s.fns.defaultNovaInitialValue;
        contourFactor = s.contourFactor;
    }

    @Override
    public int getVersion() {

        return 1079;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public double getContourFactor() {
        return contourFactor;
    }

    public boolean getUseNormalMap() {
        return useNormalMap;
    }

    public int getNormalMapColorMode() {
        return normalMapColorMode;
    }

    public boolean getNormalMapOverrideColoring() {
        return normalMapOverrideColoring;
    }

    public double getNormalMapLightFactor() {
        return normalMapLightFactor;
    }

    public double getNormalMapBlending() {
        return normalMapBlending;
    }

    public double getNormalMapHeight() {
        return normalMapHeight;
    }

    public double getNormalMapAngle() {
        return normalMapAngle;
    }

    public boolean getNormalMapUseSecondDerivative() {
        return normalMapUseSecondDerivative;
    }

    public boolean getNormalMapUseDE() {
        return normalMapUseDE;
    }

    public double getNormalMapDEfactor() {
        return normalMapDEfactor;
    }

    public boolean getNormalMapInvertDE() {
        return normalMapInvertDE;
    }

    public int getNormalMapColoring() {
        return normalMapColoring;
    }

    public boolean getDefaultNovaInitialValue() {
        return defaultNovaInitialValue;
    }

}
