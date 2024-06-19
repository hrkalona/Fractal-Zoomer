
package fractalzoomer.main.app_settings;

import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class TrueColorSettings {
    public boolean trueColorOut;
    public boolean trueColorIn;
    public int trueColorOutMode;
    public int trueColorInMode;
    public int trueColorOutPreset;
    public int trueColorInPreset;
    public String outTcComponent1;
    public String outTcComponent2;
    public String outTcComponent3;
    public String inTcComponent1;
    public String inTcComponent2;
    public String inTcComponent3;
    public int outTcColorSpace;
    public int inTcColorSpace;
    
    public TrueColorSettings() {
        trueColorOut = false;
        trueColorIn = false;
        
        trueColorOutMode = 0;
        trueColorInMode = 0;
        
        trueColorOutPreset = 0;
        trueColorInPreset = 0;

        outTcComponent1 = "(arg(s) + pi) / (2*pi)";
        outTcComponent2 = "1";
        outTcComponent3 = "1";
        
        inTcComponent1 = "1 - abs(2 * ((arg(s) + pi) / (2*pi)) - 1)";
        inTcComponent2 = "1";
        inTcComponent3 = "(arg(s) + pi) / (2*pi)";
        
        outTcColorSpace = ColorSpaceConverter.HSB;
        inTcColorSpace = ColorSpaceConverter.LCH_ab;
    }
    
}
