package fractalzoomer.utils;

import fractalzoomer.core.blending.*;
import fractalzoomer.main.MainWindow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

public class Multiwave {

    public static final int HSL_BIAS = 0;
    public static final int HSL_BIAS_UFCOMPAT = 1;

    public static final int FZ_BLENDING = 3;

    public static final int MAPPING_NORMAL = 0;
    public static final int MAPPING_SQUARE = 1;
    public static final int MAPPING_SQRT = 2;
    public static final int MAPPING_LOG = 3;

    private static Blending blendingFactory(int interpolation, int color_blending) {

        switch (color_blending) {
            case MainWindow.NORMAL_BLENDING:
                return new NormalBlending(interpolation);
            case MainWindow.MULTIPLY_BLENDING:
                return new MultiplyBlending(interpolation);
            case MainWindow.DIVIDE_BLENDING:
                return new DivideBlending(interpolation);
            case MainWindow.ADDITION_BLENDING:
                return new AdditionBlending(interpolation);
            case MainWindow.SUBTRACTION_BLENDING:
                return new SubtractionBlending(interpolation);
            case MainWindow.DIFFERENCE_BLENDING:
                return new DifferenceBlending(interpolation);
            case MainWindow.VALUE_BLENDING:
                return new ValueBlending(interpolation);
            case MainWindow.SOFT_LIGHT_BLENDING:
                return new SoftLightBlending(interpolation);
            case MainWindow.SCREEN_BLENDING:
                return new ScreenBlending(interpolation);
            case MainWindow.DODGE_BLENDING:
                return new DodgeBlending(interpolation);
            case MainWindow.BURN_BLENDING:
                return new BurnBlending(interpolation);
            case MainWindow.DARKEN_ONLY_BLENDING:
                return new DarkenOnlyBlending(interpolation);
            case MainWindow.LIGHTEN_ONLY_BLENDING:
                return new LightenOnlyBlending(interpolation);
            case MainWindow.HARD_LIGHT_BLENDING:
                return new HardLightBlending(interpolation);
            case MainWindow.GRAIN_EXTRACT_BLENDING:
                return new GrainExtractBlending(interpolation);
            case MainWindow.GRAIN_MERGE_BLENDING:
                return new GrainMergeBlending(interpolation);
            case MainWindow.SATURATION_BLENDING:
                return new SaturationBlending(interpolation);
            case MainWindow.COLOR_BLENDING:
                return new ColorBlending(interpolation);
            case MainWindow.HUE_BLENDING:
                return new HueBlending(interpolation);
            case MainWindow.EXCLUSION_BLENDING:
                return new ExclusionBlending(interpolation);
            case MainWindow.PIN_LIGHT_BLENDING:
                return new PinLightBlending(interpolation);
            case MainWindow.LINEAR_LIGHT_BLENDING:
                return new LinearLightBlending(interpolation);
            case MainWindow.VIVID_LIGHT_BLENDING:
                return new VividLightBlending(interpolation);
            case MainWindow.OVERLAY_BLENDING:
                return new OverlayBlending(interpolation);
            case MainWindow.LCH_CHROMA_BLENDING:
                return new LCHChromaBlending(interpolation);
            case MainWindow.LCH_COLOR_BLENDING:
                return new LCHColorBlending(interpolation);
            case MainWindow.LCH_HUE_BLENDING:
                return new LCHHueBlending(interpolation);
            case MainWindow.LCH_LIGHTNESS_BLENDING:
                return new LCHLightnessBlending(interpolation);
            case MainWindow.LUMINANCE_BLENDING:
                return new LuminanceBlending(interpolation);
            case MainWindow.LINEAR_BURN_BLENDING:
                return new LinearBurnBlending(interpolation);

        }

        return null;
    }
    private static double huefrac(double a, double b) {

        if(b == 0) {
            return 0;
        }

        if(b < a) {
            return b / a;
        }

        if(a == 0) {
            return 2.0;
        }

        if(a < b) {
            return 2.0 - a / b;
        }

        return  1.0;

    }

    private static double fastRem(double d, double denom) {
        return d - (denom * Math.floor(d / denom));
    }

    private static double[] rgbToHsl(int[] rgb) {

        double r = rgb[0] / 255.0;
        double g = rgb[1] / 255.0;
        double b = rgb[2] / 255.0;

        double small = Math.min(Math.min(r, g), b);
        double large = Math.max(Math.max(r, g), b);

        double[] hsl = new double[3];

        double hue = 0, sat = 0, light = 0;

        if(large == 0.0) {
            light = Double.NEGATIVE_INFINITY;
        }
        else if(small == 1.0) {
            light = Double.POSITIVE_INFINITY;
        }
        else {
            double l = (small + large) * 0.5;
            double ll = 1.0 - (2.0 * Math.abs(l - 0.5));
            double s = (large - small) / ll;
            s = Math.max(0.0, Math.min(1.0, s));

            double rs = r - small;
            double gs = g - small;
            double bs = b - small;

            sat = Math.tan((s - 0.5) * Math.PI);
            light = Math.tan((l - 0.5) * Math.PI);

            if(small == r) {
                hue = 4.0 + huefrac(gs, bs);
            }
            else if(small == g) {
                hue = 6.0 + huefrac(bs, rs);
            }
            else {
                hue = 2.0 * huefrac(rs, gs);
            }

            hue = fastRem(hue, 8.0);
        }

        hsl[0] = hue;
        hsl[1] = sat;
        hsl[2] = light;
        return hsl;

    }

    private static int[] hslToRgb(double[] hsl) {
        double h = hsl[0];

        int[] rgb = new int[3];

        double r = 0, g = 0, b = 0;

        if(h >= 4.0) {
            h = h - 2.0;
        }
        else {
            h  = h * 0.5;
        }
        double s_color = Math.atan(hsl[1]) / Math.PI + 0.5;
        double s_grey = 0.5 * (1.0 - s_color);
        double l_color = Math.atan(hsl[2]) / Math.PI;
        double lca = Math.abs(l_color);
        double l_white = l_color + lca;
        l_color = 1.0 - 2.0 * lca;

        if(h < 1.0) {
            r = 1.0;
            g = h;
        }
        else if(h < 2.0) {
            r = 2.0 - h;
            g = 1.0;
        }
        else if(h < 3.0) {
            g = 1.0;
            b = h - 2.0;
        }
        else if (h < 4.0) {
            g = 4.0 - h;
            b = 1.0;
        }
        else if (h < 5.0) {
            r = h - 4.0;
            b = 1.0;
        }
        else {
            r = 1.0;
            b = 6.0 - h;
        }

        r = ((((r * s_color) + s_grey) * l_color) + l_white) * 255;
        g = ((((g * s_color) + s_grey) * l_color) + l_white) * 255;
        b = ((((b * s_color) + s_grey) * l_color) + l_white) * 255;

        rgb[0] = (int)(Math.floor(r));
        rgb[1] = (int)(Math.floor(g));
        rgb[2] = (int)(Math.floor(b));

        return rgb;
    }

    private static double[] hslBias(double[] hsl1, double[] hsl2) {
        double[] hsl_res = new double[3];

        hsl_res[0] = fastRem(hsl1[0] + hsl2[0], 8.0);
        hsl_res[1] = hsl1[1] + hsl2[1];
        hsl_res[2] = hsl1[2] + hsl2[2];

        return hsl_res;
    }

    private static double slx(double x, double y) {
        x = Math.atan(x) / Math.PI + 0.5;
        y = Math.atan(y) / Math.PI + 0.5;

        if(x == 0) {
            return  0.0;
        }
        else if(y == 0) {
            return 0.0;
        }
        else if (x == 1.0) {
            return 1.0;
        }
        else if (y == 1.0) {
            return 1.0;
        }
        else {
            double z = 1.0 - 1 / ((1 / (1.0 - x) - 1.0) * (1 / (1.0 - y) - 1.0) + 1.0);
            z = Math.max(0.0, Math.min(1.0, z));
            return Math.tan((z - 0.5) * Math.PI);
        }
    }

    private static double[] hslBiasUfcompat(double[] hsl1, double[] hsl2) {
        double[] hsl_res = new double[3];
        hsl_res[0] = fastRem(hsl1[0] + hsl2[0], 8.0);
        hsl_res[1] = slx(hsl1[1], hsl2[1]);
        hsl_res[2] = slx(hsl1[2], hsl2[2]);
        return hsl_res;
    }

    private static double clamp255(double x) {
        return Math.floor(Math.min(255.0, Math.max(0.0, x)));
    }

    private static double[] rgbToHsl2(int[] rgb) {

        double r = rgb[0] / 255.0;
        double g = rgb[1] / 255.0;
        double b = rgb[2] / 255.0;

        double small = Math.min(Math.min(r, g), b);
        double large = Math.max(Math.max(r, g), b);

        double[] hsl = new double[3];

        double hue = 0, sat = 0, light = 0;

        if(large == 0.0) {
            light = Double.NEGATIVE_INFINITY;
        }
        else if(small == 1.0) {
            light = Double.POSITIVE_INFINITY;
        }
        else {
            double l = (small + large) * 0.5;
            double ll = 1.0 - 2.0 * Math.abs(l - 0.5);
            double s = (large - small) / ll;
            s = Math.max(0.0, Math.min(1.0, s));
            double rs = r - small;
            double gs = g - small;
            double bs = b - small;
            sat = Math.tan((s - 0.5) * Math.PI);
            light = Math.tan((l - 0.5) * Math.PI);

            if(r == small) {
                hue = 2.0 + huefrac(gs, bs);
            }
            else if (g == small) {
                hue = 4.0 + huefrac(bs, rs);
            }
            else {
                hue = huefrac(rs, gs);//0.0  +
            }

            hue = 4.0/3.0 * fastRem(hue, 6.0);
        }


        hsl[0] = hue;
        hsl[1] = sat;
        hsl[2] = light;
        return hsl;
    }

    private static int[] hslToRgb2(double[] hsl) {
        double h = hsl[0] / (4.0 / 3.0);
        double s_color = Math.atan(hsl[1]) / Math.PI + 0.5;
        double s_grey = 0.5 * (1.0 - s_color);
        double l_color = Math.atan(hsl[2]) / Math.PI;
        double lca = Math.abs(l_color);
        double l_white = l_color + lca;
        l_color = 1.0 - 2.0 * lca;

        double r = 0, g = 0, b = 0;

        if(h < 1.0) {
            r = 1.0;
            g = h;
        }
        else if(h < 2.0) {
            r = 2.0 - h;
            g = 1.0;
        }
        else if (h < 3.0) {
            g = 1.0;
            b = h - 2.0;
        }
        else if (h < 4.0) {
            g = 4.0 - h;
            b = 1.0;
        }
        else if (h < 5.0) {
            r = h - 4.0;
            b = 1.0;
        }
        else {
            r = 1.0;
            b = 6.0 - h;
        }

        r = ((((r * s_color) + s_grey) * l_color) + l_white) * 255;
        g = ((((g * s_color) + s_grey) * l_color) + l_white) * 255;
        b = ((((b * s_color) + s_grey) * l_color) + l_white) * 255;

        int rgb[] = new int[3];
        rgb[0] = (int)(Math.floor(r));
        rgb[1] = (int)(Math.floor(g));
        rgb[2] = (int)(Math.floor(b));

        return rgb;
    }

    private static HashMap<String, int[][]> precalculated = new HashMap<>();
    private static double[] tricubicGradientHsl(String key, int resolution, double fvalIn, double[][] hsls) {

        int[][] rgbs_resolution = precalculated.get(key);
        if(rgbs_resolution!= null) {
            return rgbToHsl2(rgbs_resolution[(int)Math.floor(fvalIn * resolution)]);
        }

        int[][] colorsrgb = new int[hsls.length][];
        for (int i = 0; i < colorsrgb.length; i++) {
            colorsrgb[i] = hslToRgb2(hsls[i]);
        }

        int numc = colorsrgb.length;
        double p2 = 1.0 / numc;

        rgbs_resolution = new int[resolution][];

        for (int i = 0; i < resolution; i++) {

            double fval = i / (double) resolution;

            int b = (int) Math.floor(fval / p2);
            int a = (int) fastRem(b - 1, numc);
            int c = (int) fastRem(b + 1, numc);
            int d = (int) fastRem(c + 1, numc);

            fval = fastRem(fval, p2) / p2;

            int[] as = colorsrgb[a];
            int[] bs = colorsrgb[b];
            int[] cs = colorsrgb[c];
            int[] ds = colorsrgb[d];

            double rp0 = bs[0];
            double gp0 = bs[1];
            double bp0 = bs[2];

            double rm0 = (cs[0] - as[0]) * 0.5;
            double gm0 = (cs[1] - as[1]) * 0.5;
            double bm0 = (cs[2] - as[2]) * 0.5;

            double rp1 = cs[0];
            double gp1 = cs[1];
            double bp1 = cs[2];

            double rm1 = (ds[0] - bs[0]) * 0.5;
            double gm1 = (ds[1] - bs[1]) * 0.5;
            double bm1 = (ds[2] - bs[2]) * 0.5;

            double ffval = fval * fval;
            double ffval3 = 3 * ffval;
            double fffval = ffval * fval;
            double fffval2 = fffval * 2;

            double fa = fffval2 - ffval3 + 1;
            double fb = (fffval - 2 * ffval) + fval;
            double fc = ffval3 - fffval2;
            double fd = fffval - ffval;


            int[] temp = new int[3];
            temp[0] = (int) (clamp255(fa * rp0) + (fb * rm0) + (fc * rp1) + (fd * rm1));
            temp[1] = (int) (clamp255(fa * gp0) + (fb * gm0) + (fc * gp1) + (fd * gm1));
            temp[2] = (int) (clamp255(fa * bp0) + (fb * bm0) + (fc * bp1) + (fd * bm1));

            rgbs_resolution[i] = temp;
        }

        precalculated.put(key, rgbs_resolution);
        return rgbToHsl2(rgbs_resolution[(int)Math.floor(fvalIn * resolution)]);
    }

    private static double[] tricubicGradientRgb(String key, int resolution, double fval, int[][] rgbs) {
        double[][] hsls = new double[rgbs.length][3];
        for(int i = 0; i < rgbs.length; i++) {
            hsls[i] = rgbToHsl(rgbs[i]);
        }
        return tricubicGradientHsl(key, resolution, fval, hsls);
    }

    private static double[] tricubicGradientHslNp(double fval, double[][] hsls) {

        int[][] colorsrgb = new int[hsls.length][];
        for(int i = 0; i < colorsrgb.length; i++) {
            colorsrgb[i] = hslToRgb2(hsls[i]);
        }

        int numc = colorsrgb.length;
        double p2 = 1.0 / numc;

        int b =  (int) Math.floor(fval / p2);//fastRem(fval / p2, numc);
        int a  = (int) fastRem(b - 1, numc);
        int c = (int) fastRem(b + 1, numc);
        int d = (int) fastRem(c + 1, numc);

        fval = fastRem(fval, p2) / p2;

        int[] as = colorsrgb[a];
        int[] bs = colorsrgb[b];
        int[] cs = colorsrgb[c];
        int[] ds = colorsrgb[d];

        double rp0 = bs[0];
        double gp0 = bs[1];
        double bp0 = bs[2];

        double rm0 = (cs[0] - as[0]) * 0.5;
        double gm0 = (cs[1] - as[1]) * 0.5;
        double bm0 = (cs[2] - as[2]) * 0.5;

        double rp1 = cs[0];
        double gp1 = cs[1];
        double bp1 = cs[2];

        double rm1 = (ds[0] - bs[0]) * 0.5;
        double gm1 = (ds[1] - bs[1]) * 0.5;
        double bm1 = (ds[2] - bs[2]) * 0.5;

        double ffval = fval * fval;
        double ffval3 = 3 * ffval;
        double fffval = ffval * fval;
        double fffval2 = fffval * 2;

        double fa = fffval2 - ffval3 + 1;
        double fb = (fffval - 2 * ffval) + fval;
        double fc = ffval3 - fffval2;
        double fd = fffval - ffval;

        int[] temp = new int[3];
        temp[0] = (int)(clamp255(fa * rp0) + (fb * rm0) + (fc * rp1) + (fd * rm1));
        temp[1] = (int)(clamp255(fa * gp0) + (fb * gm0) + (fc * gp1) + (fd * gm1));
        temp[2] = (int)(clamp255(fa * bp0) + (fb * bm0) + (fc * bp1) + (fd * bm1));

        return rgbToHsl2(temp);
    }

    private static double[] tricubicGradientRgbNp(double fval, int[][] rgbs) {
        double[][] hsls = new double[rgbs.length][3];
        for(int i = 0; i < rgbs.length; i++) {
            hsls[i] = rgbToHsl(rgbs[i]);
        }
        return tricubicGradientHslNp(fval, hsls);
    }

    private static class LinearRGB {
        double x;
        int[] rgb;

        public LinearRGB(double x, int[] rgb) {
            this.x = x;
            this.rgb = rgb;
        }
    }

    private static class LinearHSL {
        double x;
        double[] hsl;

        public LinearHSL(double x, double[] hsl) {
            this.x = x;
            this.hsl = hsl;
        }
    }

    private static double[] linearGradientHslNp(double fval, LinearHSL[] input) throws Exception {

        LinearRGB[] converted = new LinearRGB[input.length];

        for(int i = 0; i < converted.length; i++) {
            converted[i] = new LinearRGB(input[i].x, hslToRgb2(input[i].hsl));
        }

        return linearGradientRgbNp(fval, converted);

    }

    private static double[] linearGradientRgbNp(double fval, LinearRGB[] input) {

        int[] temp  = new int[3];

//        if(input.length < 1 || input[0].x != 0) {
//            throw new Exception("The first color must begin at 0.");
//        }


        int i;
        for(i = 0; i < input.length - 1; i++) {
            if(fval >= input[i].x && fval <= input[i + 1].x) {
                break;
            }
        }

        LinearRGB a = null;
        LinearRGB b = null;

        if(i < input.length - 1) {
            a = input[i];
            b = input[i + 1];
        }
        else {
            if(i < input.length) {
                a = input[i];
                b = input[0];
            }
        }

        if(b.x >= a.x) {
            fval = (fval - a.x) / (b.x - a.x);
        }
        else {
            fval = (fval - a.x) / (1.0 - a.x);
        }

        double pfval = 1.0 - fval;

        temp[0] = (int)clamp255(a.rgb[0] * pfval + b.rgb[0] * fval);
        temp[1] = (int)clamp255(a.rgb[1] * pfval + b.rgb[1] * fval);
        temp[2] = (int)clamp255(a.rgb[2] * pfval + b.rgb[2] * fval);

        return rgbToHsl2(temp);
    }

    private static double[] metaTricubicGradientRgb(double fval, int[][][] rgbs, double period1, double period2) {

        double fval1 = (fval % period1) / period1;
        double fval2 = (fval % period2) / period2;

        double[][] result = new double[rgbs.length][];

        for(int i = 0; i < rgbs.length; i++) {
            result[i] = tricubicGradientRgbNp(fval1, rgbs[i]);
        }

        return tricubicGradientHslNp(fval2, result);

    }

    private static double[] metaTricubicGradientHsl(double fval, double[][][] hsls, double period1, double period2) {

        double fval1 = (fval % period1) / period1;
        double fval2 = (fval % period2) / period2;

        double[][] result = new double[hsls.length][];

        for(int i = 0; i < hsls.length; i++) {
            result[i] = tricubicGradientHslNp(fval1, hsls[i]);
        }

        return tricubicGradientHslNp(fval2, result);

    }


    private static double mapping(double x, Integer mapping) {
        if(mapping == MAPPING_NORMAL) {
            return x;
        }
        else if(mapping == MAPPING_SQUARE) {
            return x * x;
        }
        else if (mapping == MAPPING_SQRT) {
            return Math.sqrt(x);
        } else {
            return Math.log(x);
        }
    }

    private static double[] blend(int blend, double[] hsl1, double[] hsl2, int fz_blending_method, double alpha, int color_interpolation) {
        if(blend == HSL_BIAS) {
            return hslBias(hsl1, hsl2);
        }
        else if (blend == HSL_BIAS_UFCOMPAT) {
            return hslBiasUfcompat(hsl1, hsl2);
        }
        else {
            Blending blending = blendingFactory(color_interpolation, fz_blending_method);
            blending.setReverseColors(false);

            int[] rgb1 = hslToRgb(hsl1);
            int[] rgb2 = hslToRgb(hsl2);

            int color = blending.blend(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2], alpha);

            int[] rgb3 = {(color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF};
            return rgbToHsl(rgb3);
        }
    }
    private static double[] multiwaveColor(WaveColor[] waves) {

        if(waves.length == 0) {
            return new double[] {0.0, 0.0, 0.0};
        }

        double[] prev_hsl = waves[0].gradient;
        for(int i = 1; i < waves.length; i++) {
            prev_hsl = blend(waves[i].blend, prev_hsl, waves[i].gradient, waves[i].fz_blending, waves[i].alpha, waves[i].interpolation_method);
        }

        return prev_hsl;

    }


    public static class WaveColor {
        protected double[] gradient;
        protected Integer blend;
        protected Integer mapping;
        protected Double period;
        protected Double start;
        protected Double start2;
        protected Double end;

        protected Integer fz_blending;
        protected Double alpha;
        protected Integer interpolation_method;

        public WaveColor(Double periodIn, Integer mappingIn, Double startIn, Double endIn, Integer blend, Integer fz_blending, Double alpha, Integer interpolation_method) {

            mapping = mappingIn;
            if(mapping == null) {
                mapping = MAPPING_NORMAL;
            }

            start = startIn;
            if(start == null) {
                start = Double.NEGATIVE_INFINITY;
            }
            else {
                start = mapping(start, mapping);
            }

            start2 = start > Double.NEGATIVE_INFINITY ? start : 0.0;

            end = endIn;
            if(end == null) {
                end = Double.POSITIVE_INFINITY;
            }
            else {
                end = mapping(end, mapping);
            }

            period = periodIn;
            if(period == null) {
                period = end - start; //Not start2?
            }
            else {
                period = mapping(period, mapping);
            }

            this.blend = blend;
            if(this.blend  == null) {
                this.blend  = HSL_BIAS;
            }

            this.alpha = alpha;
            if(this.alpha == null) {
                this.alpha = 0.0;
            }

            this.fz_blending = fz_blending;
            if(this.fz_blending == null) {
                this.fz_blending = 0;
            }

            this.interpolation_method = interpolation_method;
            if(this.interpolation_method == null) {
                this.interpolation_method = 0;
            }
        }

        public void setGradient(double[] gradient) {
            this.gradient = gradient;
        }

        public double[] getGradient() {
            return gradient;
        }

        public double getFval(double fval) {
            double fval2 = mapping(fval, mapping);
            fval2 = Math.max(start, Math.min(end, fval2));
            return fastRem(fval2 - start2, period) / period;
        }
    }

    public static int multiwave_simple(double n) {


        WaveColor a1 = new WaveColor(100.0, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a1.setGradient(tricubicGradientHslNp( a1.getFval(n), new double[][] {{0, 0, 0}, {7.5, -5, -5}, {6.5, 0, 0}, {7.5, 5, 5}}));

        WaveColor[] waves = {
                a1
        };
        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int multiwave_default(double n) {


        WaveColor a1 = new WaveColor(1000.0, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a1.setGradient(tricubicGradientHslNp( a1.getFval(n), new double[][] {{0, 0, 0}, {7.5, 0, -3}, {6.5, -3, 0}, {7.5, 0, 3}}));

        WaveColor a2 = new WaveColor(30.0, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a2.setGradient(tricubicGradientHslNp( a2.getFval(n), new double[][] {{0, 0, 0}, {7.5, -2, -2}, {0.5, 2, 2}}));

        WaveColor a3 = new WaveColor(120.0, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a3.setGradient(tricubicGradientHslNp( a3.getFval(n), new double[][] {{0, 0, 0}, {0, -1, -2}, {0, 0, 0}, {0, 1, 2}}));

        WaveColor a4 = new WaveColor(1e6, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a4.setGradient(tricubicGradientHslNp( a4.getFval(n), new double[][] {{0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}, {4, 0, 0}, {5, 0, 0}, {6, 0, 0}, {7, 0, 0},}));

        WaveColor a5 = new WaveColor(3500.0, MAPPING_NORMAL, null, null, HSL_BIAS, null, null, null);
        a5.setGradient(tricubicGradientHslNp( a5.getFval(n), new double[][] {{0, 0, 0}, {2.5, 3, -5}, {3.5, 5, -2}, {2, -4, 4}, {0.5, 4, 2}}));

        WaveColor[] waves = {
                a1,
                a2,
                a3,
                a4,
                a5
        };
        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int g_spdz2(double n) {

        WaveColor a0 = new WaveColor(1175000.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a0.setGradient(metaTricubicGradientRgb(a0.getFval(n), new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        WaveColor a1 = new WaveColor(5000.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a1.setGradient(tricubicGradientRgbNp(a1.getFval(n), new int[][]{{192, 64, 64}, {192, 64, 64}, {81, 71, 71}}));

        WaveColor a2 = new WaveColor(10.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a2.setGradient(tricubicGradientRgbNp(a2.getFval(n), new int[][]{{199, 83, 83}, {192, 64, 64}, {172, 58, 58}, {192, 64, 64}}));

        WaveColor a3 = new WaveColor(17.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a3.setGradient(tricubicGradientRgbNp(a3.getFval(n), new int[][]{{211, 121, 121}, {192, 64, 64}, {135, 45, 45}, {192, 64, 64}}));

        WaveColor a4 = new WaveColor(2544.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a4.setGradient(tricubicGradientRgbNp(a4.getFval(n), new int[][]{{243, 217, 217}, {192, 64, 64}, {39, 13, 13}, {192, 64, 64}}));

        WaveColor a5 = new WaveColor(235.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a5.setGradient(tricubicGradientRgbNp(a5.getFval(n), new int[][]{{192, 64, 64}, {76, 26, 26}, {192, 64, 64}, {231, 179, 179}}));

        WaveColor a6 = new WaveColor(null, MAPPING_LOG, 1.0, 16777216.0, HSL_BIAS_UFCOMPAT, null, null, null);

        LinearRGB[] linear_rgbs = new LinearRGB[] {
                new LinearRGB(0.0, new int[] {11, 25, 12}),
                new LinearRGB(0.375, new int[] {192, 64, 64}),
                new LinearRGB(0.5875, new int[] {192, 64, 64}),
                new LinearRGB(0.6125, new int[] {179, 177, 177}),
                new LinearRGB(0.69, new int[] {128, 237, 19}),
                new LinearRGB(0.7, new int[] {78, 99, 102}),
                new LinearRGB(0.7025, new int[] {63, 53, 131}),
                new LinearRGB(0.715, new int[] {0, 153, 180}),
                new LinearRGB(0.74, new int[] {4, 154, 184}),
                new LinearRGB(0.7475, new int[] {204, 34, 190}),
                new LinearRGB(0.7875, new int[] {216, 194, 195}),
                new LinearRGB(0.8325, new int[] {183, 154, 61}),
                new LinearRGB(1.0, new int[] {243, 227, 234}),
        };
        a6.setGradient(linearGradientRgbNp(a6.getFval(n), linear_rgbs));


        WaveColor[] waves = {
                a0,
                a1,
                a2,
                a3,
                a4,
                a5,
                a6
        };

        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int g_spdz2_custom(double n) {

        WaveColor a0 = new WaveColor(1175000.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a0.setGradient(metaTricubicGradientRgb(a0.getFval(n), new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        WaveColor a1 = new WaveColor(5000.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a1.setGradient(tricubicGradientRgbNp(a1.getFval(n), new int[][]{{192, 64, 64}, {192, 64, 64}, {81, 71, 71}}));

        WaveColor a2 = new WaveColor(10.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a2.setGradient(tricubicGradientRgbNp(a2.getFval(n), new int[][]{{199, 83, 83}, {192, 64, 64}, {172, 58, 58}, {192, 64, 64}}));

        WaveColor a3 = new WaveColor(17.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a3.setGradient(tricubicGradientRgbNp(a3.getFval(n), new int[][]{{211, 121, 121}, {192, 64, 64}, {135, 45, 45}, {192, 64, 64}}));

        WaveColor a4 = new WaveColor(2544.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a4.setGradient(tricubicGradientRgbNp(a4.getFval(n), new int[][]{{243, 217, 217}, {192, 64, 64}, {39, 13, 13}, {192, 64, 64}}));

        WaveColor a5 = new WaveColor(235.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a5.setGradient(tricubicGradientRgbNp(a5.getFval(n), new int[][]{{192, 64, 64}, {76, 26, 26}, {192, 64, 64}, {231, 179, 179}}));

        WaveColor a6 = new WaveColor(Math.pow(2, 16), MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);

        LinearRGB[] linear_rgbs = new LinearRGB[] {
                new LinearRGB(0.0, new int[] {11, 25, 12}),
                new LinearRGB(0.375, new int[] {192, 64, 64}),
                new LinearRGB(0.5875, new int[] {192, 64, 64}),
                new LinearRGB(0.6125, new int[] {179, 177, 177}),
                new LinearRGB(0.69, new int[] {128, 237, 19}),
                new LinearRGB(0.7, new int[] {78, 99, 102}),
                new LinearRGB(0.7025, new int[] {63, 53, 131}),
                new LinearRGB(0.715, new int[] {0, 153, 180}),
                new LinearRGB(0.74, new int[] {4, 154, 184}),
                new LinearRGB(0.7475, new int[] {204, 34, 190}),
                new LinearRGB(0.7875, new int[] {216, 194, 195}),
                new LinearRGB(0.8325, new int[] {183, 154, 61}),
                new LinearRGB(1.0, new int[] {243, 227, 234}),
        };
        a6.setGradient(linearGradientRgbNp(a6.getFval(n), linear_rgbs));


        WaveColor[] waves = {
                a0,
                a1,
                a2,
                a3,
                a4,
                a5,
                a6
        };

        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int linear_only(double n) throws Exception {


        WaveColor a1 = new WaveColor(Math.pow(2, 16), MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);

        LinearRGB[] linear_rgbs = new LinearRGB[] {
                new LinearRGB(0.0, new int[] {11, 25, 12}),
                new LinearRGB(0.375, new int[] {192, 64, 64}),
                new LinearRGB(0.5875, new int[] {192, 64, 64}),
                new LinearRGB(0.6125, new int[] {179, 177, 177}),
                new LinearRGB(0.69, new int[] {128, 237, 19}),
                new LinearRGB(0.7, new int[] {78, 99, 102}),
                new LinearRGB(0.7025, new int[] {63, 53, 131}),
                new LinearRGB(0.715, new int[] {0, 153, 180}),
                new LinearRGB(0.74, new int[] {4, 154, 184}),
                new LinearRGB(0.7475, new int[] {204, 34, 190}),
                new LinearRGB(0.7875, new int[] {216, 194, 195}),
                new LinearRGB(0.8325, new int[] {183, 154, 61}),
                new LinearRGB(1.0, new int[] {243, 227, 234}),

        };
        a1.setGradient(linearGradientRgbNp(a1.getFval(n), linear_rgbs));



        WaveColor[] waves = {
                a1
        };
        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int meta_tricubic_gradient_only(double n) {

        WaveColor a1 = new WaveColor(1175000.0, MAPPING_NORMAL, null, null, HSL_BIAS_UFCOMPAT, null, null, null);
        a1.setGradient(metaTricubicGradientRgb(a1.getFval(n), new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        WaveColor[] waves = {
                a1
        };
        int[] rgb = hslToRgb(multiwaveColor(waves));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];

    }

    public static void main(String[] args) throws Exception {


        int image_size = 1024;
        BufferedImage image = new BufferedImage(image_size, image_size, BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < image_size; y++) {
            for (int x = 0; x < image_size; x++) {
                image.setRGB(x, y,  g_spdz2((((image_size-1-y) * image_size + x))));
            }
        }

        File outputfile = new File("multiwave.png");
        try {
            ImageIO.write(image, "png", outputfile);
        }
        catch (Exception ex) {}
    }
}
