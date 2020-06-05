/*
 * Copyright (C) 2020 hrkalona
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;
import java.awt.Color;
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
        histogramColoring = s.hss.histogramColoring;
        histogramDensity = s.hss.histogramDensity;
        hs_noise_reducing_factor = s.hss.hs_noise_reducing_factor;
        histogramBinGranularity = s.hss.histogramBinGranularity;
        hs_blending = s.hss.hs_blending;
        histogramScaleMin = s.hss.histogramScaleMin;
        histogramScaleMax = s.hss.histogramScaleMax;
        lyapunovInitializationIteratons = s.fns.lpns.lyapunovInitializationIteratons;
        lyapunovskipBailoutCheck = s.fns.lpns.lyapunovskipBailoutCheck;
        trapCellularStructure = s.ots.trapCellularStructure;
        trapCellularInverseColor = s.ots.trapCellularInverseColor;
        trapCellularColor = s.ots.trapCellularColor;
        countTrapIterations = s.ots.countTrapIterations;
        trapCellularSize = s.ots.trapCellularSize;
        combineType = s.ds.combineType;
        invertTrapHeight = s.ots.invertTrapHeight;
        trapHeightFunction = s.ots.trapHeightFunction;
                
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
