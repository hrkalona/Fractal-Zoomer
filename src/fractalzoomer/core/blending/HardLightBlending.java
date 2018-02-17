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
 * @author hrkalona2
 */
public class HardLightBlending extends Blending {

    public HardLightBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        int temp_red = 0;
        int temp_green = 0;
        int temp_blue = 0;
        
        if(redA > 128) {
            temp_red = (int)(255 - (255.0 -  2 * (redA - 128)) * (255 - redB) / 256.0 + 0.5);
        }
        else {
            temp_red = (int)(2 * redA * redB / 256.0 + 0.5);
        }
        
        if(greenA > 128) {
            temp_green = (int)(255 - (255.0 -  2 * (greenA - 128)) * (255 - greenB) / 256.0 + 0.5);
        }
        else {
            temp_green = (int)(2 * greenA * greenB / 256.0 + 0.5);
        }
        
        if(blueA > 128) {
            temp_blue = (int)(255 - (255.0 -  2 * (blueA - 128)) * (255 - blueB) / 256.0 + 0.5);
        }
        else {
            temp_blue = (int)(2 * blueA * blueB / 256.0 + 0.5);
        }
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
