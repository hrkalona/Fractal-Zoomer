/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
 * @author hrkalona2
 */
public class PaletteGradientMergingSettings {
    public boolean palette_gradient_merge;
    public double gradient_intensity;
    public int gradient_offset;
    public double palette_blending;
    public int merging_type;
    
    public PaletteGradientMergingSettings() {
        
        palette_gradient_merge = false;
        gradient_intensity = 10;
        palette_blending = 0.5;
        merging_type = 3;
        gradient_offset = 0;
        
    }   
}
