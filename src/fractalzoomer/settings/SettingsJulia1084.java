package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsJulia1084 extends SettingsFractals1084 implements Serializable {
    private static final long serialVersionUID = 45121012555L;
    private String xJuliaCenter;
    private String yJuliaCenter;

    public SettingsJulia1084(Settings s) {
        super(s);
        xJuliaCenter = s.xJuliaCenter.toString(true);
        yJuliaCenter = s.yJuliaCenter.toString(true);
    }

    public String getXJuliaCenterStr() {

        return xJuliaCenter;

    }

    public String getYJuliaCenterStr() {

        return yJuliaCenter;

    }

    @Override
    public boolean isJulia() {

        return true;

    }
}
