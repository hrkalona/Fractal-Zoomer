/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.interpolation.AccelerationInterpolation;
import fractalzoomer.palettes.interpolation.CatmullRom2Interpolation;
import fractalzoomer.palettes.interpolation.CatmullRomInterpolation;
import fractalzoomer.palettes.interpolation.CosineInterpolation;
import fractalzoomer.palettes.interpolation.DecelerationInterpolation;
import fractalzoomer.palettes.interpolation.ExponentialInterpolation;
import fractalzoomer.palettes.interpolation.LinearInterpolation;
import fractalzoomer.palettes.interpolation.SigmoidInterpolation;
import fractalzoomer.palettes.interpolation.SmoothInterpolationMethod;
import java.awt.Color;

public class PaletteColorSmooth extends PaletteColor {
    private SmoothInterpolationMethod interpolator;

    public PaletteColorSmooth(int[] palette, double color_intensity, Color special_color, int color_smoothing_method) {

        super(palette, color_intensity, special_color);
        
        switch(color_smoothing_method) {
            case MainWindow.INTERPOLATION_LINEAR:
                interpolator = new LinearInterpolation();
                break;
            case MainWindow.INTERPOLATION_COSINE:
                interpolator = new CosineInterpolation();
                break;
            case MainWindow.INTERPOLATION_ACCELERATION:
                interpolator = new AccelerationInterpolation();
                break;
             case MainWindow.INTERPOLATION_DECELERATION:
                 interpolator = new DecelerationInterpolation();
                 break;
             case MainWindow.INTERPOLATION_EXPONENTIAL:
                 interpolator = new ExponentialInterpolation();
                 break;
             case MainWindow.INTERPOLATION_CATMULLROM:
                 interpolator = new CatmullRomInterpolation();
                 break;
             case MainWindow.INTERPOLATION_CATMULLROM2:
                 interpolator = new CatmullRom2Interpolation();
                 break;
             case MainWindow.INTERPOLATION_SIGMOID:
                 interpolator = new SigmoidInterpolation();
                 break;
        }     

    }

    @Override
    public int getPaletteColor(double result) {

        if(result < 0) {
            if(special_color != null) {
                return special_color[((int)(result * (-1))) % special_color.length];
            }
            else {
                return calculateFinalColor(-result);
            }
        }
        else {
            return calculateFinalColor(result);
        }
    }

    private int getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return palette[((int)((i + palette.length))) % palette.length];

    }

    private int calculateFinalColor(double result) {
        
        result *= color_intensity;
        int temp = ((int)(result + mod_offset * color_intensity));

        int color2 = getColor(temp);
        int color = getColor(temp - 1);

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
