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
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.core.interpolation.AccelerationInterpolation;
import fractalzoomer.core.interpolation.CatmullRom2Interpolation;
import fractalzoomer.core.interpolation.CatmullRomInterpolation;
import fractalzoomer.core.interpolation.CosineInterpolation;
import fractalzoomer.core.interpolation.DecelerationInterpolation;
import fractalzoomer.core.interpolation.ExponentialInterpolation;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.interpolation.LinearInterpolation;
import fractalzoomer.core.interpolation.SigmoidInterpolation;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.Palette;
import fractalzoomer.palettes.transfer_functions.TransferFunction;
import fractalzoomer.utils.ColorAlgorithm;
import java.awt.Color;

/**
 *
 * @author hrkalona2
 */
public abstract class DomainColoring {

    protected boolean use_palette_domain_coloring;
    protected Palette palette;
    protected TransferFunction color_transfer;
    protected int color_cycling_location;
    protected double max_norm;
    protected double max_re;
    protected double max_im;
    protected double max_iso_width;

    protected double iso_distance;
    protected double iso_factor;

    protected double logBaseFinal;
    protected double circlesBlending;

    protected Blending blending;

    protected int gridColorRed;
    protected int gridColorGreen;
    protected int gridColorBlue;

    protected double gridFactor;
    protected double gridBlending;

    protected int circlesColorRed;
    protected int circlesColorGreen;
    protected int circlesColorBlue;

    protected int isoLinesColorRed;
    protected int isoLinesColorGreen;
    protected int isoLinesColorBlue;

    protected double isoLinesBlendingFactor;

    protected int contourColorARed;
    protected int contourColorAGreen;
    protected int contourColorABlue;

    protected int contourColorBRed;
    protected int contourColorBGreen;
    protected int contourColorBBlue;

    protected double contourBlending;

    protected InterpolationMethod method;

    public DomainColoring(boolean use_palette_domain_coloring, Palette palette, TransferFunction color_transfer, int color_cycling_location, int color_interpolation, Blending blending) {

        this.use_palette_domain_coloring = use_palette_domain_coloring;
        this.palette = palette;
        this.color_transfer = color_transfer;
        this.color_cycling_location = color_cycling_location;
        
        max_norm = 20.0;
        max_re = 20;
        max_im = 20;
        max_iso_width = 0.0166666666667;
        
        this.blending = blending;

        switch (color_interpolation) {
            case MainWindow.INTERPOLATION_LINEAR:
                method = new LinearInterpolation();
                break;
            case MainWindow.INTERPOLATION_COSINE:
                method = new CosineInterpolation();
                break;
            case MainWindow.INTERPOLATION_ACCELERATION:
                method = new AccelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_DECELERATION:
                method = new DecelerationInterpolation();
                break;
            case MainWindow.INTERPOLATION_EXPONENTIAL:
                method = new ExponentialInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM:
                method = new CatmullRomInterpolation();
                break;
            case MainWindow.INTERPOLATION_CATMULLROM2:
                method = new CatmullRom2Interpolation();
                break;
            case MainWindow.INTERPOLATION_SIGMOID:
                method = new SigmoidInterpolation();
                break;
        }

    }

    public abstract int getDomainColor(Complex res);

    protected int getColor(double result) {

        result = color_transfer.transfer(result);

        return palette.getPaletteColor(result + color_cycling_location);

    }

    protected int applyArgColor(double arg) {

        double h = arg / Math.PI * 0.5;

        h = h < 0 ? h + 1 : h;

        if (use_palette_domain_coloring) {
            return getColor(ColorAlgorithm.transformResultToColor(h * palette.getPaletteLength()));
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }

    protected int applyNormColor(double norm) {

        double h = norm / max_norm;

        if (use_palette_domain_coloring) {
            return getColor(ColorAlgorithm.transformResultToColor(h * palette.getPaletteLength()));
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }
    
    protected int applyReColor(double re) {

        double h = Math.abs(re) / max_re;

        if (use_palette_domain_coloring) {
            return getColor(ColorAlgorithm.transformResultToColor(h * palette.getPaletteLength()));
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }
    
    protected int applyImColor(double im) {

        double h = Math.abs(im) / max_im;

        if (use_palette_domain_coloring) {
            return getColor(ColorAlgorithm.transformResultToColor(h * palette.getPaletteLength()));
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }

    protected int applyGrid(int color, double re, double im) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double b = 1 - Math.cbrt(Math.abs(Math.sin(im * gridFactor) * Math.sin(re * gridFactor)));

        b = b * gridBlending;

        return method.interpolate(red, green, blue, gridColorRed, gridColorGreen, gridColorBlue, b);

    }

    protected int applyCircles(int color, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));

        double b = 1 - Math.sqrt(s);

        b = b * circlesBlending;

        return method.interpolate(red, green, blue, circlesColorRed, circlesColorGreen, circlesColorBlue, b);

    }

    protected int applyIsoLines(int color, double arg) {

        arg = arg / Math.PI * 0.5;

        arg = arg < 0 ? arg + 1 : arg;

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double iso = arg % iso_distance;

        double factor = iso_factor * max_iso_width;
        
        double coef = 0;   

        if(iso <= factor) {
            coef = 1 - iso / factor;
        }
        else if(iso >= iso_distance - factor) {
            coef = (iso - (iso_distance - factor)) / (iso_distance - (iso_distance - factor));
        }

        coef = coef * isoLinesBlendingFactor;

        return method.interpolate(red, green, blue, isoLinesColorRed, isoLinesColorGreen, isoLinesColorBlue, coef);
    }

    protected int applyNormContours(int color, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double mod = (Math.log(norm) / logBaseFinal) % 1.0;

        mod = mod < 0 ? 1 + mod : mod;

        if (Double.isNaN(mod) || Double.isInfinite(mod)) {
            mod = 0;
        }

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyArgContours(int color, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        double mod = iso / iso_distance;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyNormArgContours(int color, double norm, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double mod = (Math.log(norm) / logBaseFinal) % 1.0;

        mod = mod < 0 ? 1 + mod : mod;

        if (Double.isNaN(mod) || Double.isInfinite(mod)) {
            mod = 0;
        }

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        iso = iso / iso_distance;

        mod = iso * mod;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyGridContours(int color, double re, double im) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double factor = Math.PI / gridFactor;

        double mod = (im % factor) / factor;

        mod = mod < 0 ? 1 + mod : mod;

        double mod2 = (re % factor) / factor;

        mod2 = mod2 < 0 ? 1 + mod2 : mod2;

        mod = mod * mod2;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyGridNormContours(int color, double re, double im, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double factor = Math.PI / gridFactor;

        double mod = (im % factor) / factor;

        mod = mod < 0 ? 1 + mod : mod;

        double mod2 = (re % factor) / factor;

        mod2 = mod2 < 0 ? 1 + mod2 : mod2;

        mod = mod * mod2;

        double mod3 = (Math.log(norm) / logBaseFinal) % 1.0;

        mod3 = mod3 < 0 ? 1 + mod3 : mod3;

        if (Double.isNaN(mod3) || Double.isInfinite(mod3)) {
            mod3 = 0;
        }

        mod = mod * mod3;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyGridArgContours(int color, double re, double im, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double factor = Math.PI / gridFactor;

        double mod = (im % factor) / factor;

        mod = mod < 0 ? 1 + mod : mod;

        double mod2 = (re % factor) / factor;

        mod2 = mod2 < 0 ? 1 + mod2 : mod2;

        mod = mod * mod2;

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        iso = iso / iso_distance;

        mod = mod * iso;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

    protected int applyGridNormArgContours(int color, double re, double im, double norm, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double factor = Math.PI / gridFactor;

        double mod = (im % factor) / factor;

        mod = mod < 0 ? 1 + mod : mod;

        double mod2 = (re % factor) / factor;

        mod2 = mod2 < 0 ? 1 + mod2 : mod2;

        mod = mod * mod2;

        double mod3 = (Math.log(norm) / logBaseFinal) % 1.0;

        mod3 = mod3 < 0 ? 1 + mod3 : mod3;

        if (Double.isNaN(mod3) || Double.isInfinite(mod3)) {
            mod3 = 0;
        }

        mod = mod * mod3;

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        iso = iso / iso_distance;

        mod = mod * iso;

        int temp_red = method.interpolate(contourColorARed, contourColorBRed, mod);
        int temp_green = method.interpolate(contourColorAGreen, contourColorBGreen, mod);
        int temp_blue = method.interpolate(contourColorABlue, contourColorBBlue, mod);

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

}
