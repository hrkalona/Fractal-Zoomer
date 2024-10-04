
package fractalzoomer.palettes;

import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.interpolation.LinearInterpolation;
import fractalzoomer.main.app_settings.CosinePaletteSettings;

import java.awt.*;

public class PaletteColorSmooth extends PaletteColor {
    private InterpolationMethod interpolator;
    private int fractional_transfer_method;

    public static int SMOOTHING_COLOR_SELECTION = 0;

    public PaletteColorSmooth(int[] palette, Color special_color, int color_smoothing_method, boolean special_use_palette_color, int fractional_transfer_method, int color_space, boolean color_smoothing) {

        super(palette, special_color, special_use_palette_color);

        interpolator = InterpolationMethod.create(color_smoothing_method);
        interpolator.setColorSpace(color_space);
        this.fractional_transfer_method = fractional_transfer_method;
        this.color_smoothing = color_smoothing;

    }

    @Override
    public int getPaletteColor(double result) {

        if(result < 0) {
            if(!special_use_palette_color) {
                return special_colors[(int)(((long)(result * (-1))) % special_colors.length)];
            }
            else {
                return calculateSmoothColor(-result);
            }
        }
        else {
            return calculateSmoothColor(result);
        }
    }

    private double fractional_transfer(double result, int transfer_method) {

        switch (transfer_method) {
            case 0:
                return result;
            case 1:
                double fract_part = result - (long)result;
                fract_part = 1 - fract_part;
                return (long)result + fract_part;
            case 2:
                fract_part = result - (long)result;
                double temp = 2*fract_part-1;
                fract_part = 1 - temp * temp;
                return (long)result + fract_part;
            case 3:
                fract_part = result - (long)result;
                temp = 2*fract_part-1;
                fract_part = temp * temp;
                return (long)result + fract_part;
            case 4:
                fract_part = result - (long)result;
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = 1 - temp * temp;
                return (long)result + fract_part;
            case 5:
                fract_part = result - (long)result;
                temp = 2*fract_part-1;
                temp *= temp;
                fract_part = temp * temp;
                return (long)result + fract_part;
            case 6:
                fract_part = result - (long)result;
                fract_part = Math.sin(fract_part * Math.PI);
                return (long)result + fract_part;
            case 7:
                fract_part = result - (long)result;
                fract_part = 1 - Math.sin(fract_part * Math.PI);
                return (long)result + fract_part;
            case 8:
                fract_part = result - (long)result;
                if(fract_part < 0.5) {
                    fract_part = 2 * fract_part;
                }
                else {
                    fract_part = 2 - 2 *fract_part;
                }
                return (long)result + fract_part;
            case 9:
                fract_part = result - (long)result;
                if(fract_part < 0.5) {
                    fract_part = 1 - 2 * fract_part;
                }
                else {
                    fract_part = 1 - (2 - 2 *fract_part);
                }
                return (long)result + fract_part;
            case 10:
                fract_part = result - (long)result;
                fract_part = 0.5 - 0.5 * Math.cos(2 * fract_part * Math.PI);
                return (long)result + fract_part;
            case 11:
                fract_part = result - (long)result;
                fract_part = 0.5 + 0.5 * Math.cos(2 * fract_part * Math.PI);
                return (long)result + fract_part;
            case 12:
                fract_part = result - (long)result;
                if(fract_part < 0.5) {
                    fract_part = Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = Math.sqrt(2 - 2 *fract_part);
                }
                return (long)result + fract_part;
            case 13:
                fract_part = result - (long)result;
                if(fract_part < 0.5) {
                    fract_part = 1 - Math.sqrt(2 * fract_part);
                }
                else {
                    fract_part = 1 - Math.sqrt(2 - 2 *fract_part);
                }
                return (long)result + fract_part;
        }

        return result;
    }

    private int calculateSmoothColor(double result) {

        if(!color_smoothing) {
            return palette[(int)(((long)result) % palette.length)];
        }

        result = fractional_transfer(result, fractional_transfer_method);
        int color2 = 0;
        int color = 0;

        if(SMOOTHING_COLOR_SELECTION == 0) {
            color2 = palette[(int)(((long)(result)) % palette.length)];
            color = palette[(int)(((long)((result - 1 + palette.length))) % palette.length)];
        }
        else {
            color2 = palette[(int)(((long)(result + 1)) % palette.length)];
            color = palette[(int)(((long)((result))) % palette.length)];
        }

        int color_red = (color >> 16) & 0xff;
        int color_green = (color >> 8) & 0xff;
        int color_blue = color & 0xff;

        int color2_red = (color2 >> 16) & 0xff;
        int color2_green = (color2 >> 8) & 0xff;
        int color2_blue = color2 & 0xff;

        double coef = result - (long)result; //fractional part
        
        return interpolator.interpolateColors(color_red, color_green, color_blue, color2_red, color2_green, color2_blue, coef, true);

    }

    @Override
    public int calculateColor(double result, int paletteId,  int color_cycling_location, int extra_offset, int cycle, double factor, CosinePaletteSettings iqps, boolean outcoloring) {

        if(!color_smoothing) {
            return getGeneratedColor((long)result, paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
        }

        result = fractional_transfer(result, fractional_transfer_method);

        if((paletteId == 3) && interpolator instanceof LinearInterpolation) {
            return getGeneratedColor(result, paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
        }

        int color;
        int color2;

        if(result == 0) {
            if(SMOOTHING_COLOR_SELECTION == 0) {
                color = color2 = getGeneratedColor(0, paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
            }
            else {
                color = getGeneratedColor(0, paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
                color2 = getGeneratedColor(1, paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
            }
        }
        else {
            if(SMOOTHING_COLOR_SELECTION == 0) {
                color = getGeneratedColor(((long)result - 1), paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
                color2 = getGeneratedColor(((long)result), paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
            }
            else {
                color = getGeneratedColor(((long)result), paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
                color2 = getGeneratedColor(((long)result + 1), paletteId, color_cycling_location, extra_offset, cycle, factor, iqps, outcoloring, null, null, null);
            }
        }

        int color_red = (color >> 16) & 0xff;
        int color_green = (color >> 8) & 0xff;
        int color_blue = color & 0xff;

        int color2_red = (color2 >> 16) & 0xff;
        int color2_green = (color2 >> 8) & 0xff;
        int color2_blue = color2 & 0xff;

        double coef = result - (long)result; //fractional part

        return interpolator.interpolateColors(color_red, color_green, color_blue, color2_red, color2_green, color2_blue, coef, true);
    }
}
