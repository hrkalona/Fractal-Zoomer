
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsJulia1073 extends SettingsFractals1073 implements Serializable {
    private static final long serialVersionUID = 7451312;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1073(Settings s) {
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
