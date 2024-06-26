/*
 * Copyright (C) 2020 hrkalona2
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
public class LCHChromaBlending extends Blending {

    public LCHChromaBlending(int color_interpolation) {

        super(color_interpolation);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = 0, temp_green = 0, temp_blue = 0;
        
        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }

        double[] resB = ColorSpaceConverter.RGBtoLAB(redB, greenB, blueB);
        double[] resA = ColorSpaceConverter.RGBtoLAB(redA, greenA, blueA);

        double A1 = resB[1];
        double B1 = resB[2];
        double c1 = Math.hypot(A1, B1);
        
        c1 = c1 == 0 ?  1e-16 : c1;

        double A2 = resA[1];
        double B2 = resA[2];
        double c2 = Math.hypot(A2, B2);

        double A = c2 * A1 / c1;
        double B = c2 * B1 / c1;

        int[] rgb = ColorSpaceConverter.LABtoRGB(resB[0], A, B);

        temp_red = rgb[0];
        temp_green = rgb[1];
        temp_blue = rgb[2];

        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;

        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;

        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }

}
