package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsJulia1077 extends SettingsFractals1077 implements Serializable {
    private static final long serialVersionUID = 129531414L;
    private double xJuliaCenter;
    private double yJuliaCenter;

    public SettingsJulia1077(Settings s) {
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
