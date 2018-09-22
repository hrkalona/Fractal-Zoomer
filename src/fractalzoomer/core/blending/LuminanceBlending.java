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
package fractalzoomer.core.blending;

import fractalzoomer.utils.ColorSpaceConverter;


/**
 *
 * @author kaloch
 */
public class LuminanceBlending extends Blending {
    private ColorSpaceConverter converter;
    
    public LuminanceBlending(int color_interpolation) {

        super(color_interpolation);
        converter = new ColorSpaceConverter();
    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0, temp_green = 0, temp_blue = 0;
        
        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }
        
        double[] resB = converter.RGBtoLAB(redB, greenB, blueB);
        double[] resA = converter.RGBtoLAB(redA, greenA, blueA);
        
        resB[0] = resB[0] == 0 ? 1e-16 : resB[0];
        
        double ratio = resA[0] / resB[0];
        
        temp_red = (int)(redB * ratio + 0.5);
        temp_green = (int)(greenB * ratio + 0.5);
        temp_blue = (int)(blueB * ratio + 0.5);

        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);
        
    }     
}
