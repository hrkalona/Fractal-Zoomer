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
public class OffsetColoringSettings {
    public boolean offset_coloring;
    public int post_process_offset;
    public double of_noise_reducing_factor;
    public double of_blending; 
    
    public OffsetColoringSettings() {
        offset_coloring = false;
        post_process_offset = 300;
        of_noise_reducing_factor = 0.4;
        of_blending = 0.7; 
    }
}
