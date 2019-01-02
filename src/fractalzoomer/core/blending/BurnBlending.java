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
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class BurnBlending extends Blending {

    public BurnBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        if(redB == 255 && greenB == 255 && blueB == 255) {
            redB = 254;
            greenB = 254;
            blueB = 254;
        }
        
        int temp_red = (int)(255 - (256.0 * (255 - redB)) / (redA + 1));
        int temp_green = (int)(255 - (256.0 * (255 - greenB)) / (greenA + 1));
        int temp_blue = (int)(255 - (256.0 * (255 - blueB)) / (blueA + 1));
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
