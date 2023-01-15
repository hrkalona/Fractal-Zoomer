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
public class FakeDistanceEstimationSettings {
    public boolean fake_de;
    public boolean inverse_fake_dem;
    public double fake_de_factor;
    public int fade_algorithm;
    
    public FakeDistanceEstimationSettings() {
        
        fake_de = false;
        fake_de_factor = 1;
        inverse_fake_dem = false;
        fade_algorithm = 0;
        
    }
}
