package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsJulia1079 extends SettingsFractals1079 implements Serializable {
    private static final long serialVersionUID = 13292718412L;
    private double xJuliaCenter;
    private double yJuliaCenter;

    public SettingsJulia1079(Settings s) {
        super(s);
        xJuliaCenter = s.xJuliaCenter;
        yJuliaCenter = s.yJuliaCenter;
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
