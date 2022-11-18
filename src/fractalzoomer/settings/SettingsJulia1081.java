package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsJulia1081  extends SettingsFractals1081 implements Serializable {
    private static final long serialVersionUID = 80132942389L;
    private double xJuliaCenter;
    private double yJuliaCenter;

    public SettingsJulia1081(Settings s) {
        super(s);
        xJuliaCenter = s.xJuliaCenter.doubleValue();
        yJuliaCenter = s.yJuliaCenter.doubleValue();
    }

    public double getXJuliaCenter() {

        return xJuliaCenter;

    }

    public double getYJuliaCenter() {

        return yJuliaCenter;

    }

    @Override
    public boolean isJulia() {

        return true;

    }
}
