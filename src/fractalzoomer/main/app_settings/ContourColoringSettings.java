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
public class ContourColoringSettings {
    public boolean contour_coloring;
    public double cn_noise_reducing_factor;
    public double cn_blending;
    public int contour_algorithm;
    public int contourColorMethod;
    public double min_contour;
    public int fractionalTransfer;
    public int fractionalSmoothing;
 
    public ContourColoringSettings() {
        contour_coloring = false;
        cn_noise_reducing_factor = 1e-10;
        cn_blending = 0.7;
        contour_algorithm = 0;
        contourColorMethod = 3;
        min_contour = 0.06;
        fractionalTransfer = 0;
        fractionalSmoothing = 0;
    }
}
