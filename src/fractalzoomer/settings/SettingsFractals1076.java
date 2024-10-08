
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsFractals1076 extends SettingsFractals1075 implements Serializable {
    private static final long serialVersionUID = 312095323421L;
    private boolean histogramColoring;
    private double histogramDensity;
    private double hs_noise_reducing_factor;
    private int histogramBinGranularity;
    private double hs_blending;
    private double histogramScaleMin;
    private double histogramScaleMax;
    private int lyapunovInitializationIteratons;
    private boolean lyapunovskipBailoutCheck;
    private boolean trapCellularStructure;
    private boolean trapCellularInverseColor;
    private Color trapCellularColor;
    private boolean countTrapIterations;
    private double trapCellularSize;
    private int combineType;
    private boolean invertTrapHeight;
    private int trapHeightFunction;
    
     public SettingsFractals1076(Settings s) {
        
        super(s);
        histogramColoring = s.pps.hss.histogramColoring;
        histogramDensity = s.pps.hss.histogramDensity;
        hs_noise_reducing_factor = s.pps.hss.hs_noise_reducing_factor;
        histogramBinGranularity = s.pps.hss.histogramBinGranularity;
        hs_blending = s.pps.hss.hs_blending;
        histogramScaleMin = s.pps.hss.histogramScaleMin;
        histogramScaleMax = s.pps.hss.histogramScaleMax;
        lyapunovInitializationIteratons = s.fns.lpns.lyapunovInitializationIteratons;
        lyapunovskipBailoutCheck = s.fns.lpns.lyapunovskipBailoutCheck;
        trapCellularStructure = s.pps.ots.trapCellularStructure;
        trapCellularInverseColor = s.pps.ots.trapCellularInverseColor;
        trapCellularColor = s.pps.ots.trapCellularColor;
        countTrapIterations = s.pps.ots.countTrapIterations;
        trapCellularSize = s.pps.ots.trapCellularSize;
        combineType = s.ds.combineType;
        invertTrapHeight = s.pps.ots.invertTrapHeight;
        trapHeightFunction = s.pps.ots.trapHeightFunction;
                
     }
     
    @Override
    public int getVersion() {

        return 1076;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    /**
     * @return the histogramColoring
     */
    public boolean getHistogramColoring() {
        return histogramColoring;
    }

    /**
     * @return the histogramDensity
     */
    public double getHistogramDensity() {
        return histogramDensity;
    }

    /**
     * @return the hs_noise_reducing_factor
     */
    public double getHsNoiseReducingFactor() {
        return hs_noise_reducing_factor;
    }

    /**
     * @return the histogramBinGranularity
     */
    public int getHistogramBinGranularity() {
        return histogramBinGranularity;
    }

    /**
     * @return the hs_blending
     */
    public double getHsBlending() {
        return hs_blending;
    }

    /**
     * @return the histogramScaleMin
     */
    public double getHistogramScaleMin() {
        return histogramScaleMin;
    }

    /**
     * @return the histogramScaleMax
     */
    public double getHistogramScaleMax() {
        return histogramScaleMax;
    }

    /**
     * @return the lyapunovInitializationIteratons
     */
    public int getLyapunovInitializationIteratons() {
        return lyapunovInitializationIteratons;
    }

    /**
     * @return the lyapunovskipBailoutCheck
     */
    public boolean getLyapunovskipBailoutCheck() {
        return lyapunovskipBailoutCheck;
    }

    /**
     * @return the trapCellularStructure
     */
    public boolean getTrapCellularStructure() {
        return trapCellularStructure;
    }

    /**
     * @return the trapCellularInverseColor
     */
    public boolean getTrapCellularInverseColor() {
        return trapCellularInverseColor;
    }

    /**
     * @return the trapCellularColor
     */
    public Color getTrapCellularColor() {
        return trapCellularColor;
    }

    /**
     * @return the countTrapIterations
     */
    public boolean getCountTrapIterations() {
        return countTrapIterations;
    }

    /**
     * @return the trapCellularSize
     */
    public double getTrapCellularSize() {
        return trapCellularSize;
    }

    /**
     * @return the combineType
     */
    public int getCombineType() {
        return combineType;
    }

    /**
     * @return the invertTrapHeight
     */
    public boolean getInvertTrapHeight() {
        return invertTrapHeight;
    }

    /**
     * @return the trapHeightFunction
     */
    public int getTrapHeightFunction() {
        return trapHeightFunction;
    }
    
}
