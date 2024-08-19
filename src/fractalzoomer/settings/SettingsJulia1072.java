
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsJulia1072 extends SettingsFractals1072 implements Serializable {
    private static final long serialVersionUID = 1232353295L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1072(Settings s) {
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
