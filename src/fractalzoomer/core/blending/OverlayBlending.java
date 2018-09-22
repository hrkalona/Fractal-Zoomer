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

/**
 *
 * @author kaloch
 */
public class OverlayBlending extends Blending {
    
    public OverlayBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0;
        if(redB < 128) {
            temp_red = (int)((2.0 * redA * redB) / 255.0 + 0.5);
        }
        else {
            temp_red = (int)(255 - 2.0 *  (255 - redA) * (255 - redB) / 255.0 + 0.5);
        }
        
        int temp_green = 0;
        if(greenB < 128) {
            temp_green = (int)((2.0 * greenA * greenB) / 255.0 + 0.5);
        }
        else {
            temp_green = (int)(255 - 2.0 *  (255 - greenA) * (255 - greenB) / 255.0 + 0.5);
        }
        
        int temp_blue = 0;
        if(blueB < 128) {
            temp_blue = (int)((2.0 * blueA * blueB) / 255.0 + 0.5);
        }
        else {
            temp_blue = (int)(255 - 2.0 *  (255 - blueA) * (255 - blueB) / 255.0 + 0.5);
        }
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);
        
    }
}
