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
 * @author kaloch
 */
public class LightSettings {
    public boolean lighting;
    public double[] lightVector;
    public double lightintensity;
    public double ambientlight;
    public double specularintensity;
    public double shininess;
    public int heightTransfer;
    public double heightTransferFactor;
    public int lightMode;
    public int colorMode;
    public double l_noise_reducing_factor;
    public double light_blending;
    public double light_direction;
    public double light_magnitude;
    
    public LightSettings(LightSettings copy) {
        lighting = copy.lighting;
        lightVector = new double[2];
        
        light_direction = copy.light_direction;
        light_magnitude = copy.light_magnitude;
        
        double lightAngleRadians = Math.toRadians(light_direction);
        lightVector[0] = Math.cos(lightAngleRadians) * light_magnitude;
        lightVector[1] = Math.sin(lightAngleRadians) * light_magnitude;
 
        lightintensity = copy.lightintensity;
        ambientlight = copy.ambientlight;
        specularintensity = copy.specularintensity;
        shininess = copy.shininess;
        heightTransfer = copy.heightTransfer;
        lightMode = copy.lightMode;
        colorMode = copy.colorMode;
        l_noise_reducing_factor = copy.l_noise_reducing_factor;
        light_blending = copy.light_blending;
        heightTransferFactor = copy.heightTransferFactor;
        
    }
    
    public LightSettings() {
        
        lighting = false;
        lightVector = new double[2];
        light_direction = 135;
        light_magnitude = 0.94328;
        
        double lightAngleRadians = Math.toRadians(light_direction);
        lightVector[0] = Math.cos(lightAngleRadians) * light_magnitude;
        lightVector[1] = Math.sin(lightAngleRadians) * light_magnitude;
 
        lightintensity = 1;
        ambientlight = 0.5;
        specularintensity = 1;
        shininess = 8;
        heightTransfer = 0;
        lightMode = 0;
        colorMode = 0;
        l_noise_reducing_factor = 1e-10;
        light_blending = 0.5;
        heightTransferFactor = 10;
        
    }
}
