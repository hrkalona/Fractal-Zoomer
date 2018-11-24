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
public class BumpMapSettings {
    public boolean bump_map;
    public double bumpMappingStrength;
    public double bumpMappingDepth;
    public double lightDirectionDegrees;
    public double bm_noise_reducing_factor;
    public int bump_transfer_function;
    public double bump_transfer_factor;
    public int bumpProcessing;
    public double bump_blending;
    
    public BumpMapSettings(BumpMapSettings copy) {
        
        bump_map = copy.bump_map;
        bumpMappingStrength = copy.bumpMappingStrength;
        bumpMappingDepth = copy.bumpMappingDepth;
        lightDirectionDegrees = copy.lightDirectionDegrees;
        bump_transfer_function = copy.bump_transfer_function;
        bump_transfer_factor = copy.bump_transfer_factor;
        bm_noise_reducing_factor = copy.bm_noise_reducing_factor;
        bumpProcessing = copy.bumpProcessing;
        bump_blending = copy.bump_blending;
        
    }
    
    public BumpMapSettings() {
        
        bump_map = false;
        bumpMappingStrength = 50;
        bumpMappingDepth = 50;
        lightDirectionDegrees = 0;
        bump_transfer_function = 0;
        bump_transfer_factor = 1.0;
        bm_noise_reducing_factor = 0.4;
        bumpProcessing = 0;
        bump_blending = 0.5;
        
    }
}
