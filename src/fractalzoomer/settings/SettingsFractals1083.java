package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.awt.*;
import java.io.Serializable;

public class SettingsFractals1083 extends SettingsFractals1081 implements Serializable {
    private static final long serialVersionUID = 74310534712L;
    private int normalMapDeFadeAlgorithm;
    private double normalMapDEUpperLimitFactor;
    private boolean normalMapDEAAEffect;
    private Color unmmapedRootColor;
    private Color rootShadingColor;

    public SettingsFractals1083(Settings s) {
        super(s);
        normalMapDeFadeAlgorithm = s.pps.sts.normalMapDeFadeAlgorithm;
        normalMapDEUpperLimitFactor = s.pps.sts.normalMapDEUpperLimitFactor;
        normalMapDEAAEffect = s.pps.sts.normalMapDEAAEffect;
        unmmapedRootColor = s.pps.sts.unmmapedRootColor;
        rootShadingColor = s.pps.sts.rootShadingColor;
    }

    @Override
    public int getVersion() {

        return 1083;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public int getNormalMapDeFadeAlgorithm() {
        return normalMapDeFadeAlgorithm;
    }

    public double getNormalMapDEUpperLimitFactor() {
        return normalMapDEUpperLimitFactor;
    }

    public boolean getNormalMapDEAAEffect() {
        return normalMapDEAAEffect;
    }

    public Color getUnmmapedRootColor() {
        return unmmapedRootColor;
    }

    public Color getRootShadingColor() {
        return rootShadingColor;
    }
}
