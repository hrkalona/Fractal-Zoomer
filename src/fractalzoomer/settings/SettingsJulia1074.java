
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsJulia1074 extends SettingsFractals1074 implements Serializable {
    private static final long serialVersionUID = 10294324582420L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1074(Settings s) {
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
