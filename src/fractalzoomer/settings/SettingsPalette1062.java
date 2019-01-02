/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsPalette1062 extends SettingsPalette implements Serializable {
    private static final long serialVersionUID = -6568200424825652679L;
    private double scale_factor_palette_val;
    private int processing_alg;
    
     public SettingsPalette1062(int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int offset, int processing_alg, double scale_factor_palette_val) {
        
        super(custom_palette, color_interpolation, color_space, reversed_palette, offset);
        this.processing_alg = processing_alg;
        this.scale_factor_palette_val = scale_factor_palette_val;
        
    }
     
    public int getProcessingAlgorithm() {
        
        return processing_alg;
        
    }
    
    public double getScaleFactorPaletteValue() {
        
        return scale_factor_palette_val;
        
    }
    
    @Override
    public int getVersion() {
        
        return 1062;
        
    }
     
}
