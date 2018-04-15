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
import java.awt.Color;

/**
 *
 * @author hrkalona2
 */
public abstract class DomainColoring {
    public final int[] blackToWhite = {-16777216, -16711423, -16645630, -16579837, -16514044, -16448251, -16382458, -16316665, -16250872, -16185079, -16119286, -16053493, -15987700, -15921907, -15856114, -15790321, -15724528, -15658735, -15592942, -15527149, -15461356, -15395563, -15329770, -15263977, -15198184, -15132391, -15066598, -15000805, -14935012, -14869219, -14803426, -14737633, -14671840, -14606047, -14540254, -14474461, -14408668, -14342875, -14277082, -14211289, -14145496, -14079703, -14013910, -13948117, -13882324, -13816531, -13750738, -13684945, -13619152, -13553359, -13487566, -13421773, -13355980, -13290187, -13224394, -13158601, -13092808, -13027015, -12961222, -12895429, -12829636, -12763843, -12698050, -12632257, -12566464, -12500671, -12434878, -12369085, -12303292, -12237499, -12171706, -12105913, -12040120, -11974327, -11908534, -11842741, -11776948, -11711155, -11645362, -11579569, -11513776, -11447983, -11382190, -11316397, -11250604, -11184811, -11119018, -11053225, -10987432, -10921639, -10855846, -10790053, -10724260, -10658467, -10592674, -10526881, -10461088, -10395295, -10329502, -10263709, -10197916, -10132123, -10066330, -10000537, -9934744, -9868951, -9803158, -9737365, -9671572, -9605779, -9539986, -9474193, -9408400, -9342607, -9276814, -9211021, -9145228, -9079435, -9013642, -8947849, -8882056, -8816263, -8750470, -8684677, -8618884, -8553091, -8487298, -8421505, -8355712, -8289919, -8224126, -8158333, -8092540, -8026747, -7960954, -7895161, -7829368, -7763575, -7697782, -7631989, -7566196, -7500403, -7434610, -7368817, -7303024, -7237231, -7171438, -7105645, -7039852, -6974059, -6908266, -6842473, -6776680, -6710887, -6645094, -6579301, -6513508, -6447715, -6381922, -6316129, -6250336, -6184543, -6118750, -6052957, -5987164, -5921371, -5855578, -5789785, -5723992, -5658199, -5592406, -5526613, -5460820, -5395027, -5329234, -5263441, -5197648, -5131855, -5066062, -5000269, -4934476, -4868683, -4802890, -4737097, -4671304, -4605511, -4539718, -4473925, -4408132, -4342339, -4276546, -4210753, -4144960, -4079167, -4013374, -3947581, -3881788, -3815995, -3750202, -3684409, -3618616, -3552823, -3487030, -3421237, -3355444, -3289651, -3223858, -3158065, -3092272, -3026479, -2960686, -2894893, -2829100, -2763307, -2697514, -2631721, -2565928, -2500135, -2434342, -2368549, -2302756, -2236963, -2171170, -2105377, -2039584, -1973791, -1907998, -1842205, -1776412, -1710619, -1644826, -1579033, -1513240, -1447447, -1381654, -1315861, -1250068, -1184275, -1118482, -1052689, -986896, -921103, -855310, -789517, -723724, -657931, -592138, -526345, -460552, -394759, -328966, -263173, -197380, -131587, -65794, -1};
    public final int[] whiteToBlack = {-1, -65794, -131587, -197380, -263173, -328966, -394759, -460552, -526345, -592138, -657931, -723724, -789517, -855310, -921103, -986896, -1052689, -1118482, -1184275, -1250068, -1315861, -1381654, -1447447, -1513240, -1579033, -1644826, -1710619, -1776412, -1842205, -1907998, -1973791, -2039584, -2105377, -2171170, -2236963, -2302756, -2368549, -2434342, -2500135, -2565928, -2631721, -2697514, -2763307, -2829100, -2894893, -2960686, -3026479, -3092272, -3158065, -3223858, -3289651, -3355444, -3421237, -3487030, -3552823, -3618616, -3684409, -3750202, -3815995, -3881788, -3947581, -4013374, -4079167, -4144960, -4210753, -4276546, -4342339, -4408132, -4473925, -4539718, -4605511, -4671304, -4737097, -4802890, -4868683, -4934476, -5000269, -5066062, -5131855, -5197648, -5263441, -5329234, -5395027, -5460820, -5526613, -5592406, -5658199, -5723992, -5789785, -5855578, -5921371, -5987164, -6052957, -6118750, -6184543, -6250336, -6316129, -6381922, -6447715, -6513508, -6579301, -6645094, -6710887, -6776680, -6842473, -6908266, -6974059, -7039852, -7105645, -7171438, -7237231, -7303024, -7368817, -7434610, -7500403, -7566196, -7631989, -7697782, -7763575, -7829368, -7895161, -7960954, -8026747, -8092540, -8158333, -8224126, -8289919, -8355712, -8421505, -8487298, -8553091, -8618884, -8684677, -8750470, -8816263, -8882056, -8947849, -9013642, -9079435, -9145228, -9211021, -9276814, -9342607, -9408400, -9474193, -9539986, -9605779, -9671572, -9737365, -9803158, -9868951, -9934744, -10000537, -10066330, -10132123, -10197916, -10263709, -10329502, -10395295, -10461088, -10526881, -10592674, -10658467, -10724260, -10790053, -10855846, -10921639, -10987432, -11053225, -11119018, -11184811, -11250604, -11316397, -11382190, -11447983, -11513776, -11579569, -11645362, -11711155, -11776948, -11842741, -11908534, -11974327, -12040120, -12105913, -12171706, -12237499, -12303292, -12369085, -12434878, -12500671, -12566464, -12632257, -12698050, -12763843, -12829636, -12895429, -12961222, -13027015, -13092808, -13158601, -13224394, -13290187, -13355980, -13421773, -13487566, -13553359, -13619152, -13684945, -13750738, -13816531, -13882324, -13948117, -14013910, -14079703, -14145496, -14211289, -14277082, -14342875, -14408668, -14474461, -14540254, -14606047, -14671840, -14737633, -14803426, -14869219, -14935012, -15000805, -15066598, -15132391, -15198184, -15263977, -15329770, -15395563, -15461356, -15527149, -15592942, -15658735, -15724528, -15790321, -15856114, -15921907, -15987700, -16053493, -16119286, -16185079, -16250872, -16316665, -16382458, -16448251, -16514044, -16579837, -16645630, -16711423, -16777216};
    
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
    
    protected int[] gradient;

    protected double isoLinesBlendingFactor;

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
            return getColor(h * palette.getPaletteLength());
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }

    protected int applyNormColor(double norm) {

        double h = norm / max_norm;

        if (use_palette_domain_coloring) {
            return getColor(h * palette.getPaletteLength());
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }
    
    protected int applyReColor(double re) {

        double h = Math.abs(re) / max_re;

        if (use_palette_domain_coloring) {
            return getColor(h * palette.getPaletteLength());
        } else {
            return Color.HSBtoRGB((float) h, 1, 1);
        }

    }
    
    protected int applyImColor(double im) {

        double h = Math.abs(im) / max_im;

        if (use_palette_domain_coloring) {
            return getColor(h * palette.getPaletteLength());
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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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
     
        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

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

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }
    
    protected int applyGridLinesContours(int color, double re, double im) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double mod = 1 - Math.cbrt(Math.abs(Math.sin(im * gridFactor) * Math.sin(re * gridFactor)));

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }
    
    protected int applyCirclesLinesContours(int color, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));

        double mod = 1 - Math.sqrt(s);

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }
    
    protected int applyIsoLinesContours(int color, double arg) {
        
        arg = arg / Math.PI * 0.5;

        arg = arg < 0 ? arg + 1 : arg;

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double iso = arg % iso_distance;

        double factor = iso_factor * max_iso_width;
        
        double mod = 0;   

        if(iso <= factor) {
            mod = 1 - iso / factor;
        }
        else if(iso >= iso_distance - factor) {
            mod = (iso - (iso_distance - factor)) / (iso_distance - (iso_distance - factor));
        }

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }
    
    protected int applyGridLinesCirclesLinesContours(int color, double re, double im, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double mod = 1 - Math.cbrt(Math.abs(Math.sin(im * gridFactor) * Math.sin(re * gridFactor)));

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));

        double mod2 = 1 - Math.sqrt(s);
        
        mod = mod * mod2;

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }
    
    protected int applyCirclesLinesIsoLinesContours(int color, double norm, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));

        double mod = 1 - Math.sqrt(s);
        
        arg = arg / Math.PI * 0.5;

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        double factor = iso_factor * max_iso_width;
        
        double mod2 = 0;   

        if(iso <= factor) {
            mod2 = 1 - iso / factor;
        }
        else if(iso >= iso_distance - factor) {
            mod2 = (iso - (iso_distance - factor)) / (iso_distance - (iso_distance - factor));
        }
        
        mod = mod * mod2;

        int index = (int)(mod * (gradient.length - 1) + 0.5);
        
        int temp_red = (gradient[index] >> 16) & 0xff;;
        int temp_green = (gradient[index] >> 8) & 0xff;
        int temp_blue = gradient[index] & 0xff;

        return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);

    }

}

