/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
public class EntropyColoringSettings {
    public boolean entropy_coloring;
    public double entropy_palette_factor;
    public double en_noise_reducing_factor;
    public int entropy_offset;
    public double en_blending;
    public int entropy_algorithm;
    
    public EntropyColoringSettings() {
        entropy_coloring = false;
        entropy_palette_factor = 50;
        en_noise_reducing_factor = 0.4;
        en_blending = 0.7;
        entropy_offset = 0;
        entropy_algorithm = 0;
    }
}
