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
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

import java.awt.*;

/**
 *
 * @author kaloch
 */
public class GradientSettings {
    public Color colorA;
    public Color colorB;
    public int gradient_color_space;
    public int gradient_interpolation;
    public boolean gradient_reversed;
    public int gradient_offset;
    
    public GradientSettings() {
        colorA = Color.BLACK;
        colorB = Color.WHITE;
        gradient_color_space = Constants.COLOR_SPACE_RGB;
        gradient_interpolation = Constants.INTERPOLATION_LINEAR;
        gradient_reversed = false;
        gradient_offset = 0;
    }           
}
