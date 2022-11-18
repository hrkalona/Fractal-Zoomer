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

import fractalzoomer.core.interpolation.InterpolationMethod;

/**
 *
 * @author hrkalona2
 */
public abstract class Blending {
    protected InterpolationMethod method;
    protected boolean reverseColors;
    
    protected Blending(int color_interpolation) {
        
        method = InterpolationMethod.create(color_interpolation);
        reverseColors = false;

    }

    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        if(reverseColors) {
            int tempRed = redA;
            int tempGreen = greenA;
            int tempBlue = blueA;

            redA = redB;
            greenA = greenB;
            blueA = blueB;

            redB = tempRed;
            greenB = tempGreen;
            blueB = tempBlue;
        }

        return blendInternal(redA, greenA, blueA, redB, greenB, blueB, coef);
    }
    
    protected abstract int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef);
}
