package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsJulia1076 extends SettingsFractals1076 implements Serializable {
    private static final long serialVersionUID = 998342831L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1076(Settings s) {
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
