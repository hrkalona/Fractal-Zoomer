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
package fractalzoomer.core.blending;

/**
 *
 * @author hrkalona2
 */
public class SubtractionBlending extends Blending {

    public SubtractionBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = Math.max(redB - redA, 0);
        int temp_green = Math.max(greenB - greenA, 0);
        int temp_blue = Math.max(blueB - blueA, 0);
        
        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }
    
}
