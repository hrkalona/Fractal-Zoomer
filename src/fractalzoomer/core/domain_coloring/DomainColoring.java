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
package fractalzoomer.core.domain_coloring;

import fractalzoomer.core.Complex;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.core.interpolation.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.Cubehelix;

import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public abstract class DomainColoring {
    public static final int[] blackToWhite = {-16777216, -16711423, -16645630, -16579837, -16514044, -16448251, -16382458, -16316665, -16250872, -16185079, -16119286, -16053493, -15987700, -15921907, -15856114, -15790321, -15724528, -15658735, -15592942, -15527149, -15461356, -15395563, -15329770, -15263977, -15198184, -15132391, -15066598, -15000805, -14935012, -14869219, -14803426, -14737633, -14671840, -14606047, -14540254, -14474461, -14408668, -14342875, -14277082, -14211289, -14145496, -14079703, -14013910, -13948117, -13882324, -13816531, -13750738, -13684945, -13619152, -13553359, -13487566, -13421773, -13355980, -13290187, -13224394, -13158601, -13092808, -13027015, -12961222, -12895429, -12829636, -12763843, -12698050, -12632257, -12566464, -12500671, -12434878, -12369085, -12303292, -12237499, -12171706, -12105913, -12040120, -11974327, -11908534, -11842741, -11776948, -11711155, -11645362, -11579569, -11513776, -11447983, -11382190, -11316397, -11250604, -11184811, -11119018, -11053225, -10987432, -10921639, -10855846, -10790053, -10724260, -10658467, -10592674, -10526881, -10461088, -10395295, -10329502, -10263709, -10197916, -10132123, -10066330, -10000537, -9934744, -9868951, -9803158, -9737365, -9671572, -9605779, -9539986, -9474193, -9408400, -9342607, -9276814, -9211021, -9145228, -9079435, -9013642, -8947849, -8882056, -8816263, -8750470, -8684677, -8618884, -8553091, -8487298, -8421505, -8355712, -8289919, -8224126, -8158333, -8092540, -8026747, -7960954, -7895161, -7829368, -7763575, -7697782, -7631989, -7566196, -7500403, -7434610, -7368817, -7303024, -7237231, -7171438, -7105645, -7039852, -6974059, -6908266, -6842473, -6776680, -6710887, -6645094, -6579301, -6513508, -6447715, -6381922, -6316129, -6250336, -6184543, -6118750, -6052957, -5987164, -5921371, -5855578, -5789785, -5723992, -5658199, -5592406, -5526613, -5460820, -5395027, -5329234, -5263441, -5197648, -5131855, -5066062, -5000269, -4934476, -4868683, -4802890, -4737097, -4671304, -4605511, -4539718, -4473925, -4408132, -4342339, -4276546, -4210753, -4144960, -4079167, -4013374, -3947581, -3881788, -3815995, -3750202, -3684409, -3618616, -3552823, -3487030, -3421237, -3355444, -3289651, -3223858, -3158065, -3092272, -3026479, -2960686, -2894893, -2829100, -2763307, -2697514, -2631721, -2565928, -2500135, -2434342, -2368549, -2302756, -2236963, -2171170, -2105377, -2039584, -1973791, -1907998, -1842205, -1776412, -1710619, -1644826, -1579033, -1513240, -1447447, -1381654, -1315861, -1250068, -1184275, -1118482, -1052689, -986896, -921103, -855310, -789517, -723724, -657931, -592138, -526345, -460552, -394759, -328966, -263173, -197380, -131587, -65794, -1};
    public static final int[] whiteToBlack = {-1, -65794, -131587, -197380, -263173, -328966, -394759, -460552, -526345, -592138, -657931, -723724, -789517, -855310, -921103, -986896, -1052689, -1118482, -1184275, -1250068, -1315861, -1381654, -1447447, -1513240, -1579033, -1644826, -1710619, -1776412, -1842205, -1907998, -1973791, -2039584, -2105377, -2171170, -2236963, -2302756, -2368549, -2434342, -2500135, -2565928, -2631721, -2697514, -2763307, -2829100, -2894893, -2960686, -3026479, -3092272, -3158065, -3223858, -3289651, -3355444, -3421237, -3487030, -3552823, -3618616, -3684409, -3750202, -3815995, -3881788, -3947581, -4013374, -4079167, -4144960, -4210753, -4276546, -4342339, -4408132, -4473925, -4539718, -4605511, -4671304, -4737097, -4802890, -4868683, -4934476, -5000269, -5066062, -5131855, -5197648, -5263441, -5329234, -5395027, -5460820, -5526613, -5592406, -5658199, -5723992, -5789785, -5855578, -5921371, -5987164, -6052957, -6118750, -6184543, -6250336, -6316129, -6381922, -6447715, -6513508, -6579301, -6645094, -6710887, -6776680, -6842473, -6908266, -6974059, -7039852, -7105645, -7171438, -7237231, -7303024, -7368817, -7434610, -7500403, -7566196, -7631989, -7697782, -7763575, -7829368, -7895161, -7960954, -8026747, -8092540, -8158333, -8224126, -8289919, -8355712, -8421505, -8487298, -8553091, -8618884, -8684677, -8750470, -8816263, -8882056, -8947849, -9013642, -9079435, -9145228, -9211021, -9276814, -9342607, -9408400, -9474193, -9539986, -9605779, -9671572, -9737365, -9803158, -9868951, -9934744, -10000537, -10066330, -10132123, -10197916, -10263709, -10329502, -10395295, -10461088, -10526881, -10592674, -10658467, -10724260, -10790053, -10855846, -10921639, -10987432, -11053225, -11119018, -11184811, -11250604, -11316397, -11382190, -11447983, -11513776, -11579569, -11645362, -11711155, -11776948, -11842741, -11908534, -11974327, -12040120, -12105913, -12171706, -12237499, -12303292, -12369085, -12434878, -12500671, -12566464, -12632257, -12698050, -12763843, -12829636, -12895429, -12961222, -13027015, -13092808, -13158601, -13224394, -13290187, -13355980, -13421773, -13487566, -13553359, -13619152, -13684945, -13750738, -13816531, -13882324, -13948117, -14013910, -14079703, -14145496, -14211289, -14277082, -14342875, -14408668, -14474461, -14540254, -14606047, -14671840, -14737633, -14803426, -14869219, -14935012, -15000805, -15066598, -15132391, -15198184, -15263977, -15329770, -15395563, -15461356, -15527149, -15592942, -15658735, -15724528, -15790321, -15856114, -15921907, -15987700, -16053493, -16119286, -16185079, -16250872, -16316665, -16382458, -16448251, -16514044, -16579837, -16645630, -16711423, -16777216};
    private static final double max_iso_width = 0.0166666666667;
    private static final double gridMaxWidth = 0.15;
    
    protected int coloring_mode;
    protected PaletteColor palette;
    protected TransferFunction color_transfer;
    protected int color_cycling_location;
    protected double max_norm_re_im_value;

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
    protected int gradient_offset;
    
    protected int combineType;

    protected double isoLinesBlendingFactor;

    protected double contourBlending;

    protected InterpolationMethod method;
    
    protected int circleFadeFunction;
    protected int gridFadeFunction;
    
    protected int contourMethod;
    
    protected double circleWidth;
    protected double gridWidth;
    protected int gridAlgorithm;
    protected double countourFactor;

    public DomainColoring(int coloring_mode, PaletteColor palette, TransferFunction color_transfer, int color_cycling_location, int color_interpolation, Blending blending, double countourFactor) {

        this.coloring_mode = coloring_mode;
        this.palette = palette;
        this.color_transfer = color_transfer;
        this.color_cycling_location = color_cycling_location;
        
        circleFadeFunction = 1;
        gridFadeFunction = 0;
        
        max_norm_re_im_value = 20.0;
        
        contourMethod = 3;
        this.countourFactor = countourFactor;
        
        gradient_offset = 0;
        
        circleWidth = 1;
        gridWidth = 1;
        
        gridAlgorithm = 0;
        
        combineType = 0;

        this.blending = blending;

        method = InterpolationMethod.create(color_interpolation);
    }

    public abstract int getDomainColor(Complex res);

    protected Complex round(Complex res) {

        double Re = res.getRe();
        double Im = res.getIm();

        double temp = Math.floor(1e10 * Re + (Re > 0 ? 0.5 : -0.5)) / 1e10;
        double temp2 = Math.floor(1e10 * Im + (Im > 0 ? 0.5 : -0.5)) / 1e10;

        return new Complex(temp, temp2);

    }

    protected int getColor(double result) {

        result = color_transfer.transfer(result);

        return palette.getPaletteColor(result + color_cycling_location);

    }
    
    private int chooseColor(double h) {
        
        if (coloring_mode == 1) {
            return getColor(h * palette.getPaletteLength());
        } else if (coloring_mode == 0){
            return HSBcolor(h, color_cycling_location);
        }
        else if (coloring_mode == 2){
            return LCHcolor(h, color_cycling_location);
        }
        else if (coloring_mode == 3){
            return Cubehelix1(h, color_cycling_location);
        }
        else if (coloring_mode == 4){
            return Cubehelix3(h, color_cycling_location);
        }

        return 0;
        
    }
    
    public static int HSBcolor(double h, int color_cycling_location) {
        
        return Color.HSBtoRGB((float) ((h + color_cycling_location * 0.01) % 1.0), 1, 1);
        
    }
    
    public static int LCHcolor(double h, int color_cycling_location) {
        
        int [] res = ColorSpaceConverter.LCHtoRGB(50, 100, ((h + color_cycling_location * 0.01) % 1.0) * 360);
        return 0xFF000000 | res[0] << 16 | res[1] << 8 | res[2];
        
    }

    public static int Cubehelix1(double h, int color_cycling_location) {

        return Cubehelix.defaultMap[((int)((h * Cubehelix.defaultMap.length - 1) + color_cycling_location + 0.5)) % Cubehelix.defaultMap.length];

    }

    public static int Cubehelix3(double h, int color_cycling_location) {

        return Cubehelix.cubehelix3[((int)((h * Cubehelix.cubehelix3.length - 1) + color_cycling_location + 0.5)) % Cubehelix.cubehelix3.length];

    }

    protected int applyArgColor(double arg) {

        double h = arg / Math.PI * 0.5;

        h = h < 0 ? h + 1 : h;

        return chooseColor(h);

    }

    protected int applyNormColor(double norm) {

        double h = norm / max_norm_re_im_value;

        return chooseColor(h);

    }
    
    protected int applyReColor(double re) {

        double h = Math.abs(re) / max_norm_re_im_value;

        return chooseColor(h);

    }
    
    protected int applyImColor(double im) {

        double h = Math.abs(im) / max_norm_re_im_value;

        return chooseColor(h);

    }
    
    private double gridAlgorithm1(double re, double im) {
        
        double b = Math.abs(Math.sin(im * gridFactor) * Math.sin(re * gridFactor));
        
        b = b <= gridWidth ? b / gridWidth : 1;
        
        return b;
        
    }
    
    private double gridAlgorithm2(double re, double im) {
        
        
        double distance = Math.PI / gridFactor;
        double imLine = Math.abs(im) % distance;

        double factor = gridWidth * gridMaxWidth;
        
        double coef = 0;   

        if(imLine <= factor) {
            coef = imLine / factor;
        }
        else if(imLine >= distance - factor) {
            coef = 1 - (imLine - (distance - factor)) / (distance - (distance - factor));
        }
        else {
            coef = 1;
        }
        
        double reLine = Math.abs(re) % distance;
        
        double coef2 = 0;   

        if(reLine <= factor) {
            coef2 = reLine / factor;
        }
        else if(reLine >= distance - factor) {
            coef2 = 1 - (reLine - (distance - factor)) / (distance - (distance - factor));
        }
        else {
            coef2 = 1;
        }
        
        return combine(coef, coef2);
        
    }

    protected int applyGrid(int color, double re, double im) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double b = 0;
        
        if(gridAlgorithm == 0) {
            b = gridAlgorithm1(re, im);
        }
        else {
            b = gridAlgorithm2(re, im);
        }
        
        b = 1 - gridfade(b);

        b = b * gridBlending;

        return method.interpolate(red, green, blue, gridColorRed, gridColorGreen, gridColorBlue, b);

    }

    protected int applyCircles(int color, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));
        
        s = s <= circleWidth ? s / circleWidth : 1;

        double b = 1 - circlefade(s);

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

        return applyContour(red, green, blue, mod);

    }

    protected int applyArgContours(int color, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        double mod = iso / iso_distance;
        
        return applyContour(red, green, blue, mod);

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

        mod = combine(iso, mod);
     
        return applyContour(red, green, blue, mod);

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

        mod = combine(mod, mod2);

        return applyContour(red, green, blue, mod);

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

        double mod3 = (Math.log(norm) / logBaseFinal) % 1.0;

        mod3 = mod3 < 0 ? 1 + mod3 : mod3;

        if (Double.isNaN(mod3) || Double.isInfinite(mod3)) {
            mod3 = 0;
        }

        mod = combine(mod, mod2, mod3);

        return applyContour(red, green, blue, mod);

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

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        iso = iso / iso_distance;

        mod = combine(mod, mod2, iso);

        return applyContour(red, green, blue, mod);

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

        double mod3 = (Math.log(norm) / logBaseFinal) % 1.0;

        mod3 = mod3 < 0 ? 1 + mod3 : mod3;

        if (Double.isNaN(mod3) || Double.isInfinite(mod3)) {
            mod3 = 0;
        }

        arg = (arg / Math.PI * 0.5);

        arg = arg < 0 ? arg + 1 : arg;

        double iso = arg % iso_distance;

        iso = iso / iso_distance;

        mod = combine(mod, mod2, mod3, iso);

        return applyContour(red, green, blue, mod);

    }
    
    protected int applyGridLinesContours(int color, double re, double im) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        
        double b = 0;
        
        if(gridAlgorithm == 0) {
            b = gridAlgorithm1(re, im);
        }
        else {
            b = gridAlgorithm2(re, im);
        }

        double mod = 1 - gridfade(b);

        return applyContour(red, green, blue, mod);

    }
    
    protected int applyCirclesLinesContours(int color, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));
        
        s = s <= circleWidth ? s / circleWidth : 1;

        double mod = 1 - circlefade(s);

        return applyContour(red, green, blue, mod);

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

        return applyContour(red, green, blue, mod);

    }
    
    protected int applyGridLinesCirclesLinesContours(int color, double re, double im, double norm) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        
        double b = 0;
        
        if(gridAlgorithm == 0) {
            b = gridAlgorithm1(re, im);
        }
        else {
            b = gridAlgorithm2(re, im);
        }

        double mod = 1 - gridfade(b);

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));
        
        s = s <= circleWidth ? s / circleWidth : 1;

        double mod2 = 1 - circlefade(s);
        
        mod = combine(mod, mod2);

        return applyContour(red, green, blue, mod);

    }
    
    protected int applyCirclesLinesIsoLinesContours(int color, double norm, double arg) {

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;

        double s = Math.abs(Math.sin(Math.log(norm) / logBaseFinal * Math.PI));
        
        s = s <= circleWidth ? s / circleWidth : 1;

        double mod = 1 - circlefade(s);
        
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
        
        mod = combine(mod, mod2);

        return applyContour(red, green, blue, mod);

    }
    
    private double combine(double c1, double c2) {
        
        switch(combineType) {
            case 0:
                return c1 * c2;
            case 1:
                return (c1 + c2) * 0.5;
        }
            
        return 0;
    }
    
    private double combine(double c1, double c2, double c3) {
        
        switch(combineType) {
            case 0:
                return c1 * c2 * c3;
            case 1:
                return (c1 + c2 + c3) / 3.0;
        }
            
        return 0;
    }
    
    private double combine(double c1, double c2, double c3, double c4) {
        
        switch(combineType) {
            case 0:
                return c1 * c2 * c3 * c4;
            case 1:
                return (c1 + c2 + c3 + c4) * 0.25;
        }
            
        return 0;
    }
    
    private double circlefade(double value) {

        switch (this.circleFadeFunction) {
            case 0:
                return SqrtInterpolation.getCoefficient(value);
            case 1:
                return CbrtInterpolation.getCoefficient(value);
            case 2:
                return FrthrootInterpolation.getCoefficient(value);
            case 3:
                return value;
            case 4:
                return value != 1 ? 0 : 1;
            case 5:
                return CosineInterpolation.getCoefficient(value);
            case 6:
                return AccelerationInterpolation.getCoefficient(value);
            case 7:
                return SineInterpolation.getCoefficient(value);
            case 8:
                return DecelerationInterpolation.getCoefficient(value);
            case 9:
                return ThirdPolynomialInterpolation.getCoefficient(value);
            case 10:
                return FifthPolynomialInterpolation.getCoefficient(value);
            case 11:
                return Exponential2Interpolation.getCoefficient(value);
            case 12:
                return SmoothTransitionFunctionInterpolation.getCoefficient(value);
        }
        return 0;
    }
    
     private double gridfade(double value) {

        switch (this.gridFadeFunction) {
            case 0:
                return SqrtInterpolation.getCoefficient(value);
            case 1:
                return CbrtInterpolation.getCoefficient(value);
            case 2:
                return FrthrootInterpolation.getCoefficient(value);
            case 3:
                return value;
            case 4:
                return value != 1 ? 0 : 1;
            case 5:
                return CosineInterpolation.getCoefficient(value);
            case 6:
                return AccelerationInterpolation.getCoefficient(value);
            case 7:
                return SineInterpolation.getCoefficient(value);
            case 8:
                return DecelerationInterpolation.getCoefficient(value);
            case 9:
                return ThirdPolynomialInterpolation.getCoefficient(value);
            case 10:
                return FifthPolynomialInterpolation.getCoefficient(value);
            case 11:
                return Exponential2Interpolation.getCoefficient(value);
            case 12:
                return SmoothTransitionFunctionInterpolation.getCoefficient(value);
        }
        
        return 0;
    }
     
     private int applyContour(int red, int green, int blue, double coef) {
         
         if(contourMethod == 0) { //Lab
             double[] res = ColorSpaceConverter.RGBtoLAB(red, green, blue);
             double val = countourFactor * coef * res[0];
             val = val > 100 ? 100 : val;
             int[] rgb = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);   
             return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
         }
         else if(contourMethod == 1) { //HSB
             double[] res = ColorSpaceConverter.RGBtoHSB(red, green, blue);
             double val = countourFactor * coef * res[2];
             val = val > 1 ? 1 : val;
             int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
             return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
         }
         else if(contourMethod == 2) { //HSL
             double[] res = ColorSpaceConverter.RGBtoHSL(red, green, blue);
             double val = countourFactor * coef * res[2];
             val = val > 1 ? 1 : val;
             int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
             return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
         }
         else if(contourMethod == 3) {// blend
             int index = (int)(coef * (gradient.length - 1) + 0.5);
        
             int grad_color = gradient[(index + gradient_offset) % gradient.length];
             
             int temp_red = (grad_color >> 16) & 0xff;
             int temp_green = (grad_color >> 8) & 0xff;
             int temp_blue = grad_color & 0xff;

             return blending.blend(temp_red, temp_green, temp_blue, red, green, blue, 1 - contourBlending);
         }
         else { //scale
            red = (int)(countourFactor * coef * red + 0.5);
            green = (int)(countourFactor * coef * green + 0.5);
            blue = (int)(countourFactor * coef * blue + 0.5);
            red = red > 255 ? 255 : red;
            green = green > 255 ? 255 : green;
            blue = blue > 255 ? 255 : blue;
            return 0xff000000 | (red << 16) | (green << 8) | blue;
         }
      
     }
     
     public void setColorCyclingLocation(int color_cycling_location) {
         
         this.color_cycling_location = color_cycling_location;
         
     }
     
     public void setGradientOffset(int gradient_offset) {
         
         this.gradient_offset = gradient_offset;
         
     }

}

