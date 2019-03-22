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
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class MagneticPendulumSettings {
    public double height;
    public double[][] magnetLocation;
    public double[][] magnetStrength;
    public double[] pendulum;
    public double stepsize;
    public double[] friction;
    public double[] gravity;
    public int magnetPendVariableId;
    
    public MagneticPendulumSettings() {
        
        stepsize = 0.05;
        
        friction = new double[2];
        friction[0] = 0.07;
        friction[1] = 0;
        
        gravity = new double[2];
        gravity[0] = 0.2;
        gravity[1] = 0;
        
        height = 0.25;
        
        pendulum = new double[2];
        pendulum[0] = 0;
        pendulum[1] = 0;
        
        magnetLocation = new double[10][2];
        magnetLocation[0][0] = 2;
        magnetLocation[0][1] = 0;
        magnetLocation[1][0] = -1;
        magnetLocation[1][1] = 1.73205081;
        magnetLocation[2][0] = -1;
        magnetLocation[2][1] = -1.73205081;

        magnetStrength = new double[10][2];
        magnetStrength[0][0] = 1;
        magnetStrength[1][0] = 1;
        magnetStrength[2][0] = 1;
        
        magnetPendVariableId = 0;
    }
}
