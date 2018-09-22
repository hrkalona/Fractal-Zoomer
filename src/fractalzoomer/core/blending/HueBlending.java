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

import java.awt.Color;

/**
 *
 * @author kaloch
 */
public class HueBlending extends Blending {

    public HueBlending(int color_interpolation) {
        super(color_interpolation);
    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        float[] hsbvalsContours = new float[3];
        Color.RGBtoHSB(redA, greenA, blueA, hsbvalsContours);

        float[] hsbvals = new float[3];
        Color.RGBtoHSB(redB, greenB, blueB, hsbvals);
        
        int temp_red = 0, temp_green = 0, temp_blue = 0;
        if(hsbvalsContours[1] == 0) {
            temp_red = redB;
            temp_green = greenB;
            temp_blue = blueB;
        }
        else {
            int temp_color = Color.HSBtoRGB(hsbvalsContours[0], hsbvals[1], hsbvals[2]);
            
            temp_red = (temp_color >> 16) & 0xFF;
            temp_green = (temp_color >> 8) & 0xFF;
            temp_blue = temp_color & 0xFF;
        }
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
