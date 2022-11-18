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
package fractalzoomer.palettes;

import fractalzoomer.core.interpolation.InterpolationMethod;

import java.awt.*;

public class PaletteColorSmooth extends PaletteColor {
    private InterpolationMethod interpolator;

    public PaletteColorSmooth(int[] palette, Color special_color, int color_smoothing_method, boolean special_use_palette_color) {

        super(palette, special_color, special_use_palette_color);

        interpolator = InterpolationMethod.create(color_smoothing_method);

    }

    @Override
    public int getPaletteColor(double result) {

        if(result < 0) {
            if(!special_use_palette_color) {
                return special_colors[((int)(result * (-1))) % special_colors.length];
            }
            else {
                return calculateSmoothColor(-result);
            }
        }
        else {
            return calculateSmoothColor(result);
        }
    }

    private int calculateSmoothColor(double result) {

        int color2 = palette[((int)(result)) % palette.length];
        int color = palette[((int)((result - 1 + palette.length))) % palette.length];

        int color_red = (color >> 16) & 0xff;
        int color_green = (color >> 8) & 0xff;
        int color_blue = color & 0xff;

        int color2_red = (color2 >> 16) & 0xff;
        int color2_green = (color2 >> 8) & 0xff;
        int color2_blue = color2 & 0xff;

        double coef = result - (int)result; //fractional part
        
        return interpolator.interpolate(color_red, color_green, color_blue, color2_red, color2_green, color2_blue, coef);

    }

    @Override
    public int calculateColor(double result, int paletteId,  int color_cycling_location, int cycle) {

        int color;
        int color2;

        if(result == 0) {
            color = color2 = getGeneratedColor(((int)result), paletteId, color_cycling_location, cycle);
        }
        else {
            color = getGeneratedColor(((int)result - 1), paletteId, color_cycling_location, cycle);
            color2 = getGeneratedColor(((int)result), paletteId, color_cycling_location, cycle);
        }

        int color_red = (color >> 16) & 0xff;
        int color_green = (color >> 8) & 0xff;
        int color_blue = color & 0xff;

        int color2_red = (color2 >> 16) & 0xff;
        int color2_green = (color2 >> 8) & 0xff;
        int color2_blue = color2 & 0xff;

        double coef = result - (int)result; //fractional part

        return interpolator.interpolate(color_red, color_green, color_blue, color2_red, color2_green, color2_blue, coef);
    }
}
