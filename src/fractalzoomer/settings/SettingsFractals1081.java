package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.awt.*;
import java.io.Serializable;

public class SettingsFractals1081 extends SettingsFractals1080 implements Serializable {
    private static final long serialVersionUID = 4941275L;
    private int langNormType;
    private double langNNorm;
    private int atomNormType;
    private double atomNNorm;
    private int hmapping;
    private boolean useGlobalMethod;
    private double[] globalMethodFactor;
    private int period;
    private boolean showOnlyTraps;
    private Color trapBgColor;
    private int domain_height_method;


    public SettingsFractals1081(Settings s) {
        super(s);
        period = s.fns.period;
        langNormType = s.sts.langNormType;
        langNNorm = s.sts.langNNorm;

        atomNormType = s.sts.atomNormType;
        atomNNorm = s.sts.atomNNorm;

        hmapping = s.hss.hmapping;

        useGlobalMethod = s.fns.useGlobalMethod;
        globalMethodFactor = s.fns.globalMethodFactor;
        trapBgColor = s.ots.background;
        showOnlyTraps = s.ots.showOnlyTraps;
        domain_height_method = s.ds.domain_height_method;
    }

    @Override
    public int getVersion() {

        return 1081;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public int getLangNormType() {
        return langNormType;
    }

    public double getLangNNorm() {
        return langNNorm;
    }

    public int getAtomNormType() {
        return atomNormType;
    }

    public double getAtomNNorm() {
        return atomNNorm;
    }

    public int getHmapping() {
        return hmapping;
    }

    public boolean getUseGlobalMethod() {
        return useGlobalMethod;
    }

    public double[] getGlobalMethodFactor() {
        return globalMethodFactor;
    }

    public int getPeriod() {
        return period;
    }


    public boolean getShowOnlyTraps() {
        return showOnlyTraps;
    }

    public Color getTrapBgColor() {
        return trapBgColor;
    }

    public int getDomainHeightMethod() {
        return domain_height_method;
    }
}
