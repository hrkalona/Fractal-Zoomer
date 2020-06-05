/*
 * Copyright (C) 2020 hrkalona2
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

package fractalzoomer.main.app_settings;

/**
 *
 * @author kaloch
 */
public class HistogramColoringSettings {
    public boolean histogramColoring;
    public double histogramDensity;
    public double hs_noise_reducing_factor;
    public int histogramBinGranularity;
    public double hs_blending;
    public double histogramScaleMin;
    public double histogramScaleMax;

    public HistogramColoringSettings() {
        histogramColoring = false;
        histogramDensity = 3;
        hs_noise_reducing_factor = 0.8;
        histogramBinGranularity = 2;
        hs_blending = 1;
        histogramScaleMin = 0;
        histogramScaleMax = 1;
    }
}
