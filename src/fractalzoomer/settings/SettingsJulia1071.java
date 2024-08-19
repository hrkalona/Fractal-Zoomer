
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author kaloch
 */
public class SettingsJulia1071 extends SettingsFractals1071 implements Serializable {
    private static final long serialVersionUID = 68330612L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1071(Settings s) {
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
