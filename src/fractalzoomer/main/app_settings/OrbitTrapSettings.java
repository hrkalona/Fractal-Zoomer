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

import fractalzoomer.main.Constants;

/**
 *
 * @author kaloch
 */
public class OrbitTrapSettings {
    public boolean useTraps;
    public double[] trapPoint;
    public double trapLength;
    public double trapWidth;
    public int trapType;
    public double trapBlending;
    public double trapNorm;
    public int lineType;
    public int trapColorMethod;
    public double trapIntensity;
    
    public OrbitTrapSettings() {
        
        trapType = Constants.POINT_TRAP;
        useTraps = false;
        trapPoint = new double[2];
        trapPoint[0] = 0.0;
        trapPoint[1] = 0.0;
        trapLength = 4;
        trapWidth = 0.4;
        trapBlending = 0.5;
        trapNorm = 2;
        lineType = 0;
        trapColorMethod = 3;
        trapIntensity = 1;
        
    }
    
}
