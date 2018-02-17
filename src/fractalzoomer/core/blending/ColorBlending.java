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
 * @author hrkalona2
 */
public class ColorBlending extends Blending {

    public ColorBlending(int color_interpolation) {
        super(color_interpolation);
    }

    @Override
    public int blend(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        float[] hsbvalsContours = new float[3];
        Color.RGBtoHSB(redA, greenA, blueA, hsbvalsContours);

        float[] hsbvals = new float[3];
        Color.RGBtoHSB(redB, greenB, blueB, hsbvals);

        int temp_color = Color.HSBtoRGB(hsbvalsContours[0], hsbvalsContours[1], hsbvals[2]);

        int temp_red = (temp_color >> 16) & 0xFF;
        int temp_green = (temp_color >> 8) & 0xFF;
        int temp_blue = temp_color & 0xFF;

        return method.interpolate(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef);

    }

}
