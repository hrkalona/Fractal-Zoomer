
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsJulia1075 extends SettingsFractals1075 implements Serializable {
    private static final long serialVersionUID = 3120548134L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1075(Settings s) {
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
