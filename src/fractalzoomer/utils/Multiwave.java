package fractalzoomer.utils;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Multiwave {
    public static MultiwaveColorParams[] empty = new MultiwaveColorParams[] {new MultiwaveColorParams(), new MultiwaveColorParams()};
    public static MultiwaveColorParams[] simple_params;
    public static MultiwaveColorParams[] g_spdz2_params;
    public static MultiwaveColorParams[] default_params;
    public static MultiwaveColorParams[] g_spdz2_custom_params;
    public static MultiwaveColorParams[] user_params_out = empty;
    public static MultiwaveColorParams[] user_params_in = empty;

    enum Blend {
        HSL_BIAS,
        HSL_BIAS_UFCOMPAT
    }

    enum Mapping {
        NORMAL,
        SQUARE,
        SQRT,
        LOG,
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

        double rec = 1.0 / 255.0;
        double r = rgb[0] * rec;
        double g = rgb[1] * rec;
        double b = rgb[2] * rec;

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

        h = h >= 4.0 ? h - 2.0 : h * 0.5;

        double rec = 1.0 / Math.PI;
        double s_color = Math.atan(hsl[1]) * rec + 0.5;
        double s_grey = 0.5 * (1.0 - s_color);
        double l_color = Math.atan(hsl[2]) * rec;
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
        double rec = 1.0 / Math.PI;
        x = Math.atan(x) * rec + 0.5;
        y = Math.atan(y) * rec + 0.5;

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

        double rec = 1.0 / 255.0;
        double r = rgb[0] * rec;
        double g = rgb[1] * rec;
        double b = rgb[2] * rec;

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

        int[] rgb = new int[3];
        rgb[0] = (int)(Math.floor(r));
        rgb[1] = (int)(Math.floor(g));
        rgb[2] = (int)(Math.floor(b));

        return rgb;
    }

    private static double[] tricubicGradientHslNp(double fval, HSL[] hsls) {

        int[][] colorsrgb = new int[hsls.length][];
        for(int i = 0; i < colorsrgb.length; i++) {
            colorsrgb[i] = hslToRgb2(hsls[i].getHSL());
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

    private static double[] tricubicGradientRgbNp(double fval, RGB[] rgbs) {
        HSL[] hsls = new HSL[rgbs.length];
        for(int i = 0; i < rgbs.length; i++) {
            hsls[i] = new HSL(rgbToHsl(rgbs[i].getRGB()));
        }
        return tricubicGradientHslNp(fval, hsls);
    }

    private static class RGB {

        public RGB() {

        }

        public RGB(int[] rgb) {
            r = rgb[0];
            g = rgb[1];
            b = rgb[2];
        }

        public RGB(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
        public Integer getR() {
            return r;
        }

        public Integer getG() {
            return g;
        }

        public Integer getB() {
            return b;
        }

        @JsonIgnore
        public int[] getRGB() {
            return new int[] {r, g, b};
        }

        @JsonIgnore
        public boolean isValid() {
            return r != null && g != null && b != null
                    && r >= 0 && r <= 255
                    && g >= 0 && g <= 255
                    && b >= 0 && b <= 255;
        }

        public void setR(Integer r) {
            this.r = r;
        }

        public void setG(Integer g) {
            this.g = g;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        @JsonProperty
        Integer r;
        @JsonProperty
        Integer g;
        @JsonProperty
        Integer b;
    }

    private static class LinearRGB {

        public LinearRGB() {

        }
        public void setX(Double x) {
            this.x = x;
        }

        public void setRgb(RGB rgb) {
            this.rgb = rgb;
        }

        @JsonProperty
        Double x;

        public Double getX() {
            return x;
        }

        public RGB getRgb() {
            return rgb;
        }

        @JsonProperty
        RGB rgb;

        public LinearRGB(double x, int r, int g, int b) {
            this.x = x;
            rgb = new RGB(r, g, b);
        }

        public LinearRGB(double x, int[] rgb_in) {
            this.x = x;
            rgb = new RGB(rgb_in[0], rgb_in[1], rgb_in[2]);
        }

        @JsonIgnore
        public boolean isValid() {
            return rgb!= null && x != null && rgb.isValid();
        }
    }

    private static class HSL {

        public HSL() {

        }

        public void setH(Double h) {
            this.h = h;
        }

        public void setS(Double s) {
            this.s = s;
        }

        public void setL(Double l) {
            this.l = l;
        }

        public HSL(double[] hsl) {
            h = hsl[0];
            s = hsl[1];
            l = hsl[2];
        }
        public HSL(double h, double s, double l) {
            this.h = h;
            this.s = s;
            this.l = l;
        }

        @JsonProperty
        Double h;

        @JsonProperty
        Double s;

        @JsonProperty
        Double l;

        public Double getH() {
            return h;
        }

        public Double getS() {
            return s;
        }

        public Double getL() {
            return l;
        }

        @JsonIgnore
        public double[] getHSL() {
            return new double[] {h, s, l};
        }

        @JsonIgnore
        public boolean isValid() {
            return h != null && s != null && l != null;
        }
    }

    private static class LinearHSL {

        public LinearHSL() {

        }

        @JsonProperty
        Double x;

        public HSL getHsl() {
            return hsl;
        }

        @JsonProperty
        HSL hsl;

        public void setX(Double x) {
            this.x = x;
        }

        public void setHsl(HSL hsl) {
            this.hsl = hsl;
        }

        public Double getX() {
            return x;
        }

        public LinearHSL(double x, double h, double s, double l) {
            this.x = x;
            hsl = new HSL(h, s, l);
        }

        @JsonIgnore
        public boolean isValid() {
            return x != null && hsl != null && isValid();
        }
    }

    private static double[] linearGradientHslNp(double fval, LinearHSL[] input) {

        LinearRGB[] converted = new LinearRGB[input.length];

        for(int i = 0; i < converted.length; i++) {
            converted[i] = new LinearRGB(input[i].x, hslToRgb2(input[i].hsl.getHSL()));
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

        if (a == null || b == null) {
            return rgbToHsl2(new int[] {0, 0, 0});
        }
        else {
            if (b.x >= a.x) {
                fval = (fval - a.x) / (b.x - a.x);
            } else {
                fval = (fval - a.x) / (1.0 - a.x);
            }
        }

        double pfval = 1.0 - fval;

        temp[0] = (int)clamp255(a.rgb.r * pfval + b.rgb.r * fval);
        temp[1] = (int)clamp255(a.rgb.g * pfval + b.rgb.g * fval);
        temp[2] = (int)clamp255(a.rgb.b * pfval + b.rgb.b * fval);

        return rgbToHsl2(temp);
    }

    private static double[] metaTricubicGradientRgb(double fval, MetaTricubicRGB o) {
        double period1 = o.period1;
        double period2 = o.period2;
        RGB[][] rgbs = o.rgb_colors;

        double fval1 = (fval % period1) / period1;
        double fval2 = (fval % period2) / period2;

        HSL[] result = new HSL[rgbs.length];

        for(int i = 0; i < rgbs.length; i++) {
            result[i] = new HSL(tricubicGradientRgbNp(fval1, rgbs[i]));
        }

        return tricubicGradientHslNp(fval2, result);

    }

    private static double[] metaTricubicGradientHsl(double fval, MetaTricubicHSL o) {
        double period1 = o.period1;
        double period2 = o.period2;
        HSL[][] hsls = o.hsl_colors;

        double fval1 = (fval % period1) / period1;
        double fval2 = (fval % period2) / period2;

        HSL[] result = new HSL[hsls.length];

        for(int i = 0; i < hsls.length; i++) {
            result[i] = new HSL(tricubicGradientHslNp(fval1, hsls[i]));
        }

        return tricubicGradientHslNp(fval2, result);

    }

    private static double[] blend(Blend blend, double[] hsl1, double[] hsl2) {
        if(blend == Blend.HSL_BIAS) {
            return hslBias(hsl1, hsl2);
        }
        return hslBiasUfcompat(hsl1, hsl2);
    }
    private static double[] multiwaveColor(MultiwaveColor[] waves) {

        if(waves.length == 0) {
            return new double[] {0.0, 0.0, 0.0};
        }

        double[] current_hsl = waves[0].gradient;
        for(int i = 1; i < waves.length; i++) {
            current_hsl = blend(waves[i].blend, current_hsl, waves[i].gradient);
        }

        return current_hsl;

    }

    public static class MetaTricubicRGB {

        public MetaTricubicRGB() {

        }
        public void setRgb_colors(RGB[][] rgb_colors) {
            this.rgb_colors = rgb_colors;
        }

        public void setPeriod1(Double period1) {
            this.period1 = period1;
        }

        public void setPeriod2(Double period2) {
            this.period2 = period2;
        }

        public RGB[][] getRgb_colors() {
            return rgb_colors;
        }

        public Double getPeriod1() {
            return period1;
        }

        public Double getPeriod2() {
            return period2;
        }

        @JsonProperty
        RGB[][] rgb_colors;

        @JsonProperty
        Double period1;

        @JsonProperty
        Double period2;

        public MetaTricubicRGB(int[][][] colors_in, double period1, double period2) {
            rgb_colors = new RGB[colors_in.length][];
            for(int i = 0; i < rgb_colors.length; i++) {
                rgb_colors[i] = new RGB[colors_in[i].length];
                for(int j = 0; j < rgb_colors[i].length; j++) {
                    rgb_colors[i][j] = new RGB(colors_in[i][j]);
                }
            }
            this.period1 = period1;
            this.period2 = period2;
        }

        public MetaTricubicRGB(RGB[][] rgb_colors, double period1, double period2) {
            this.rgb_colors = rgb_colors;
            this.period1 = period1;
            this.period2 = period2;
        }
    }

    public static class MetaTricubicHSL {

        public MetaTricubicHSL() {

        }
        public HSL[][] getHsl_colors() {
            return hsl_colors;
        }

        public Double getPeriod1() {
            return period1;
        }

        public Double getPeriod2() {
            return period2;
        }

        public void setHsl_colors(HSL[][] hsl_colors) {
            this.hsl_colors = hsl_colors;
        }

        public void setPeriod1(Double period1) {
            this.period1 = period1;
        }

        public void setPeriod2(Double period2) {
            this.period2 = period2;
        }

        @JsonProperty
        HSL[][] hsl_colors;
        @JsonProperty
        Double period1;
        @JsonProperty
        Double period2;

        public MetaTricubicHSL(double[][][] colors_in, double period1, double period2) {
            hsl_colors = new HSL[colors_in.length][];
            for(int i = 0; i < hsl_colors.length; i++) {
                hsl_colors[i] = new HSL[colors_in[i].length];
                for(int j = 0; j < hsl_colors[i].length; j++) {
                    hsl_colors[i][j] = new HSL(colors_in[i][j]);
                }
            }
            this.period1 = period1;
            this.period2 = period2;
        }

        public MetaTricubicHSL(HSL[][] hsl_colors, double period1, double period2) {
            this.hsl_colors = hsl_colors;
            this.period1 = period1;
            this.period2 = period2;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MultiwaveColorParams {
        @JsonProperty
        public Double period;
        @JsonProperty
        public Mapping mapping;
        @JsonProperty
        public Double start;
        @JsonProperty
        public Double end;
        @JsonProperty
        public Blend blend;

        public Double getPeriod() {
            return period;
        }

        public Mapping getMapping() {
            return mapping;
        }

        public Double getStart() {
            return start;
        }

        public Double getEnd() {
            return end;
        }

        public Blend getBlend() {
            return blend;
        }

        public RGB[] getTricubic_rgb() {
            return tricubic_rgb;
        }

        public HSL[] getTricubic_hsl() {
            return tricubic_hsl;
        }

        public LinearRGB[] getLinear_rgb() {
            return linear_rgb;
        }

        public LinearHSL[] getLinear_hsl() {
            return linear_hsl;
        }

        public MetaTricubicRGB getMeta_tricubic_rgb() {
            return meta_tricubic_rgb;
        }

        public MetaTricubicHSL getMeta_tricubic_hsl() {
            return meta_tricubic_hsl;
        }

        public void setPeriod(Double period) {
            this.period = period;
        }

        public void setMapping(Mapping mapping) {
            this.mapping = mapping;
        }

        public void setStart(Double start) {
            this.start = start;
        }

        public void setEnd(Double end) {
            this.end = end;
        }

        public void setBlend(Blend blend) {
            this.blend = blend;
        }

        public void setTricubic_rgb(RGB[] tricubic_rgb) {
            this.tricubic_rgb = tricubic_rgb;
        }

        public void setTricubic_hsl(HSL[] tricubic_hsl) {
            this.tricubic_hsl = tricubic_hsl;
        }

        public void setLinear_rgb(LinearRGB[] linear_rgb) {
            this.linear_rgb = linear_rgb;
        }

        public void setLinear_hsl(LinearHSL[] linear_hsl) {
            this.linear_hsl = linear_hsl;
        }

        public void setMeta_tricubic_rgb(MetaTricubicRGB meta_tricubic_rgb) {
            this.meta_tricubic_rgb = meta_tricubic_rgb;
        }

        public void setMeta_tricubic_hsl(MetaTricubicHSL meta_tricubic_hsl) {
            this.meta_tricubic_hsl = meta_tricubic_hsl;
        }

        @JsonProperty("tricubic_rgb")
        public RGB[] tricubic_rgb;

        @JsonProperty("tricubic_hsl")
        public HSL[] tricubic_hsl;
        @JsonProperty("linear_rgb")
        public LinearRGB[] linear_rgb;

        @JsonProperty("linear_hsl")
        public LinearHSL[] linear_hsl;

        @JsonProperty("meta_tricubic_rgb")
        public MetaTricubicRGB meta_tricubic_rgb;

        @JsonProperty("meta_tricubic_hsl")
        public MetaTricubicHSL meta_tricubic_hsl;

        public MultiwaveColorParams() {

        }

        public MultiwaveColorParams(Double period, Mapping mapping, Double start, Double end, Blend blend) {
            this.period = period;
            this.mapping = mapping;
            this.start = start;
            this.end = end;
            this.blend = blend;
        }

        @JsonIgnore
        public void setTricubicRGB(int[][] vals) {
            RGB[] rgbs = new RGB[vals.length];
            for(int i = 0; i < rgbs.length; i++) {
                rgbs[i] = new RGB(vals[i][0], vals[i][1], vals[i][2]);
            }
            tricubic_rgb = rgbs;
        }

        @JsonIgnore
        public void setTricubicRGB(RGB[] vals) {
            tricubic_rgb = vals;
        }

        @JsonIgnore
        public void setTricubicHSL(HSL[] vals) {
            tricubic_hsl = vals;
        }

        @JsonIgnore
        public void setTricubicHSL(double[][] vals) {
            HSL[] hsls = new HSL[vals.length];
            for(int i = 0; i < hsls.length; i++) {
                hsls[i] = new HSL(vals[i][0], vals[i][1], vals[i][2]);
            }
            tricubic_hsl = hsls;
        }

        @JsonIgnore
        public void setLinearRGB(LinearRGB[] vals) {
            linear_rgb = vals;
        }

        @JsonIgnore
        public void setLinearRGB(LinearHSL[] vals) {
            linear_hsl = vals;
        }

        @JsonIgnore
        public void setMetaTricubicRGB(MetaTricubicRGB o) {
            meta_tricubic_rgb = o;
        }

        @JsonIgnore
        public void setMetaTricubicHsl(MetaTricubicHSL o) {
            meta_tricubic_hsl = o;
        }

        public static MultiwaveColor[] build(MultiwaveColorParams[] params, double n) throws Exception {
            MultiwaveColor[] waves = new MultiwaveColor[params.length];
            for(int i = 0; i < waves.length; i++) {
                params[i].validate();
                waves[i] = new MultiwaveColor(params[i], n);
            }
            return waves;
        }

        public void validate() throws Exception {
            if(period != null && period <= 0) {
                throw new Exception("Period must be greater than zero.");
            }
            if(start != null && start < 0) {
                throw new Exception("Start must be greater or equal to zero.");
            }
            if(end != null && end < 0) {
                throw new Exception("End must be greater or equal to zero.");
            }
            if(start != null && end != null && end < start) {
                throw new Exception("End must be greater than start.");
            }

            int definitions = 0;
            if(meta_tricubic_rgb != null) {
                definitions++;
            }
            if(meta_tricubic_hsl != null) {
                definitions++;
            }
            if(tricubic_rgb != null) {
                definitions++;
            }
            if(tricubic_hsl != null) {
                definitions++;
            }
            if(linear_rgb != null) {
                definitions++;
            }
            if(linear_hsl != null) {
                definitions++;
            }
            if(definitions != 1) {
                throw new Exception("Each color wave definition must contain one definition from the list (tricubic_rgb, tricubic_hsl, meta_tricubic_rgb, meta_tricubic_hsl, linear_rgb, linear_hsl)");
            }

            if(linear_rgb != null) {
                if(linear_rgb.length == 0) {
                    throw new Exception("Linear RGB cannot be empty.");
                }
                for(int i = 0; i < linear_rgb.length; i++) {
                    if(linear_rgb[i].x == null) {
                        throw new Exception("The linear coefficient is missing.");
                    }
                    if(linear_rgb[i].x < 0 || linear_rgb[i].x > 1) {
                        throw new Exception("The linear coefficient must be between 0 and 1.");
                    }
                    if(linear_rgb[i].rgb == null) {
                        throw new Exception("Missing the linear rgb values.");
                    }
                    if(!linear_rgb[i].rgb.isValid()) {
                        throw new Exception("The linear r, g, b values must be present and in the range of [0, 255].");
                    }
                }
                for(int i = 0; i < linear_rgb.length - 1; i++) {
                    if(linear_rgb[i + 1].x < linear_rgb[i].x) {
                        throw new Exception("The linear coefficient must in ascending order.");
                    }
                }
            }

            if(linear_hsl != null) {
                if(linear_hsl.length == 0) {
                    throw new Exception("Linear HSL cannot be empty.");
                }
                for(int i = 0; i < linear_hsl.length; i++) {
                    if(linear_hsl[i].x == null) {
                        throw new Exception("The linear coefficient is missing.");
                    }
                    if(linear_hsl[i].x < 0 || linear_hsl[i].x > 1) {
                        throw new Exception("The linear coefficient must be between 0 and 1.");
                    }
                    if(linear_hsl[i].hsl == null) {
                        throw new Exception("Missing the linear hsl values.");
                    }
                    if(!linear_hsl[i].hsl.isValid()) {
                        throw new Exception("The h, s, l values must be present.");
                    }
                }
                for(int i = 0; i < linear_hsl.length - 1; i++) {
                    if(linear_hsl[i + 1].x < linear_hsl[i].x) {
                        throw new Exception("The linear coefficient must in ascending order.");
                    }
                }
            }

            if(meta_tricubic_rgb != null) {
                if(meta_tricubic_rgb.period1 == null || meta_tricubic_rgb.period2 == null) {
                    throw new Exception("Meta tricubic periods must not be present.");
                }
                if(meta_tricubic_rgb.period1 <= 0 || meta_tricubic_rgb.period2 <= 0) {
                    throw new Exception("Meta tricubic periods must be greater than zero.");
                }
                if(meta_tricubic_rgb.rgb_colors == null) {
                    throw new Exception("Meta tricubic rgb colors are missing.");
                }

                if(meta_tricubic_rgb.rgb_colors.length == 0) {
                    throw new Exception("The meta tricubic RGB cannot be empty.");
                }

                for(int i = 0; i < meta_tricubic_rgb.rgb_colors.length; i++) {

                    if(meta_tricubic_rgb.rgb_colors[i].length == 0) {
                        throw new Exception("The meta tricubic RGB cannot be empty.");
                    }

                    for(int j = 0; j < meta_tricubic_rgb.rgb_colors[i].length; j++) {
                        if(!meta_tricubic_rgb.rgb_colors[i][j].isValid()) {
                            throw new Exception("The meta tricubic r, g, b values must be present and in the range of [0, 255].");
                        }
                    }
                }
            }

            if(meta_tricubic_hsl != null) {
                if(meta_tricubic_hsl.period1 == null || meta_tricubic_hsl.period2 == null) {
                    throw new Exception("Meta tricubic periods must not be present.");
                }
                if(meta_tricubic_hsl.period1 <= 0 || meta_tricubic_hsl.period2 <= 0) {
                    throw new Exception("Meta tricubic periods must be greater than zero.");
                }
                if(meta_tricubic_hsl.hsl_colors == null) {
                    throw new Exception("Meta tricubic hsl colors are missing.");
                }

                if(meta_tricubic_hsl.hsl_colors.length == 0) {
                    throw new Exception("The meta tricubic HSL cannot be empty.");
                }

                for(int i = 0; i < meta_tricubic_hsl.hsl_colors.length; i++) {

                    if(meta_tricubic_hsl.hsl_colors[i].length == 0) {
                        throw new Exception("The meta tricubic HSL cannot be empty.");
                    }

                    for(int j = 0; j < meta_tricubic_hsl.hsl_colors[i].length; j++) {
                        if(!meta_tricubic_hsl.hsl_colors[i][j].isValid()) {
                            throw new Exception("The meta tricubic h, s, l values must be present.");
                        }
                    }
                }
            }

            if(tricubic_rgb != null) {
                if(tricubic_rgb.length == 0) {
                    throw new Exception("Tricubic RGB cannot be empty.");
                }
                for(int i = 0; i < tricubic_rgb.length; i++) {
                    if(!tricubic_rgb[i].isValid()) {
                        throw new Exception("The tricubic r, g, b values must be present and in the range of [0, 255].");
                    }
                }
            }

            if(tricubic_hsl != null) {
                if(tricubic_hsl.length == 0) {
                    throw new Exception("Tricubic HSL cannot be empty.");
                }
                for(int i = 0; i < tricubic_hsl.length; i++) {
                    if(!tricubic_hsl[i].isValid()) {
                        throw new Exception("The tricubic h, s, l values must be present.");
                    }
                }
            }
        }

        @JsonIgnore
        public Color[] getTricubicRGBColors() {
            if(tricubic_rgb == null) {
                return null;
            }
            Color[] colors = new Color[tricubic_rgb.length];
            for(int i = 0; i < colors.length; i++) {
                colors[i] = new Color(tricubic_rgb[i].getR(), tricubic_rgb[i].getG(), tricubic_rgb[i].getB());
            }
            return colors;
        }

        @JsonIgnore
        public Color[] getTricubicHSLColors() {
            if(tricubic_hsl == null) {
                return null;
            }
            Color[] colors = new Color[tricubic_hsl.length];
            for(int i = 0; i < colors.length; i++) {
                int[] rgb = hslToRgb2(tricubic_hsl[i].getHSL());
                colors[i] = new Color(rgb[0], rgb[1], rgb[2]);
            }
            return colors;
        }

        @JsonIgnore
        public Color[] getLinearRGBColors() {
            if(linear_rgb == null) {
                return null;
            }
            Color[] colors = new Color[linear_rgb.length];
            for(int i = 0; i < colors.length; i++) {
                colors[i] = new Color(linear_rgb[i].rgb.getR(), linear_rgb[i].rgb.getG(), linear_rgb[i].rgb.getB());
            }
            return colors;
        }

        @JsonIgnore
        public Color[] getLinearHSLColors() {
            if(linear_hsl == null) {
                return null;
            }
            Color[] colors = new Color[linear_hsl.length];
            for(int i = 0; i < colors.length; i++) {
                int[] rgb = hslToRgb2(linear_hsl[i].hsl.getHSL());
                colors[i] = new Color(rgb[0], rgb[1], rgb[2]);
            }
            return colors;
        }

        @JsonIgnore
        public Color[][] getMetaTricubicRGBColors() {
            if(meta_tricubic_rgb == null) {
                return null;
            }
            Color[][] colors = new Color[meta_tricubic_rgb.rgb_colors.length][];
            for(int i = 0; i < colors.length; i++) {
                colors[i] = new Color[meta_tricubic_rgb.rgb_colors[i].length];
                for(int j = 0; j < colors[i].length; j++) {
                    colors[i][j] = new Color(meta_tricubic_rgb.rgb_colors[i][j].getR(), meta_tricubic_rgb.rgb_colors[i][j].getG(), meta_tricubic_rgb.rgb_colors[i][j].getB());
                }
            }
            return colors;
        }

        @JsonIgnore
        public Color[][] getMetaTricubicHSLColors() {
            if(meta_tricubic_hsl == null) {
                return null;
            }
            Color[][] colors = new Color[meta_tricubic_hsl.hsl_colors.length][];
            for(int i = 0; i < colors.length; i++) {
                colors[i] = new Color[meta_tricubic_hsl.hsl_colors[i].length];
                for(int j = 0; j < colors[i].length; j++) {
                    int[] rgb = hslToRgb2(meta_tricubic_hsl.hsl_colors[i][j].getHSL());
                    colors[i][j] = new Color(rgb[0], rgb[1], rgb[2]);
                }
            }
            return colors;
        }

    }


    public static class MultiwaveColor {
        protected double[] gradient;
        protected Blend blend;
        protected Mapping mapping;
        protected Double period;
        protected Double start;
        protected Double start2;
        protected Double end;

        public MultiwaveColor(MultiwaveColorParams params, double n) {
            Mapping mappingIn = params.mapping;
            Double startIn = params.start;
            Double endIn = params.end;
            Double periodIn = params.period;
            Blend blend = params.blend;

            mapping = mappingIn;
            if(mapping == null) {
                mapping = Mapping.NORMAL;
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
            if(this.blend == null) {
                this.blend = Blend.HSL_BIAS;
            }

            if(params.tricubic_rgb != null) {
                gradient = tricubicGradientRgbNp(getFval(n), params.tricubic_rgb);
            }
            else if(params.tricubic_hsl != null) {
                gradient = tricubicGradientHslNp(getFval(n), params.tricubic_hsl);
            }
            else if(params.linear_rgb != null) {
                gradient = linearGradientRgbNp(getFval(n), params.linear_rgb);
            }
            else if(params.linear_hsl != null) {
                gradient = linearGradientHslNp(getFval(n), params.linear_hsl);
            }
            else if(params.meta_tricubic_rgb != null) {
                gradient = metaTricubicGradientRgb(getFval(n), params.meta_tricubic_rgb);
            }
            else if(params.meta_tricubic_hsl != null) {
                gradient = metaTricubicGradientHsl(getFval(n), params.meta_tricubic_hsl);
            }
            else {
                gradient = new double[0];
            }
        }

        public double[] getGradient() {
            return gradient;
        }

        private double getFval(double fval) {
            double fval2 = mapping(fval, mapping);
            fval2 = Math.max(start, Math.min(end, fval2));
            return fastRem(fval2 - start2, period) / period;
        }

        private static double mapping(double x, Mapping mapping) {
            if(mapping == Mapping.NORMAL) {
                return x;
            }
            else if(mapping == Mapping.SQUARE) {
                return x * x;
            }
            else if (mapping == Mapping.SQRT) {
                return Math.sqrt(x);
            } else {
                return Math.log(x);
            }
        }
    }

    public static int multiwave_simple(double n) throws Exception {
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(simple_params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int multiwave_default(double n) throws Exception {
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(default_params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int general_palette(double n, MultiwaveColorParams[] params) throws Exception {
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    static {
        create_simple_params();
        create_default_params();
        create_g_spdz2_params();
        create_g_spdz2_custom_params();
    }

    static void create_simple_params() {
        MultiwaveColorParams p1 = new MultiwaveColorParams(100.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS);
        p1.setTricubicHSL(new double[][] {{0, 0, 0}, {7.5, -5, -5}, {6.5, 0, 0}, {7.5, 5, 5}});

        MultiwaveColorParams[] params = {
                p1
        };
        simple_params = params;
    }

    static void create_default_params() {
        MultiwaveColorParams p1 = new MultiwaveColorParams(1000.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS);
        p1.setTricubicHSL(new double[][] {{0, 0, 0}, {7.5, 0, -3}, {6.5, -3, 0}, {7.5, 0, 3}});

        MultiwaveColorParams p2 = new MultiwaveColorParams(30.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS);
        p2.setTricubicHSL(new double[][] {{0, 0, 0}, {7.5, -2, -2}, {0.5, 2, 2}});

        MultiwaveColorParams p3 = new MultiwaveColorParams(120.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS);
        p3.setTricubicHSL(new double[][] {{0, 0, 0}, {0, -1, -2}, {0, 0, 0}, {0, 1, 2}});

        MultiwaveColorParams p4 = new MultiwaveColorParams(1e6, Mapping.NORMAL, null, null,  Blend.HSL_BIAS);
        p4.setTricubicHSL(new double[][] {{0, 0, 0}, {1, 0, 0}, {2, 0, 0}, {3, 0, 0}, {4, 0, 0}, {5, 0, 0}, {6, 0, 0}, {7, 0, 0},});

        MultiwaveColorParams p5 = new MultiwaveColorParams(3500.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS);
        p5.setTricubicHSL(new double[][] {{0, 0, 0}, {2.5, 3, -5}, {3.5, 5, -2}, {2, -4, 4}, {0.5, 4, 2}});

        MultiwaveColorParams[] params = {
                p1,
                p2,
                p3,
                p4,
                p5
        };
        default_params = params;
    }

    static void create_g_spdz2_custom_params() {
        MultiwaveColorParams p0 = new MultiwaveColorParams(1175000.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p0.setMetaTricubicRGB(new MetaTricubicRGB(new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        MultiwaveColorParams p1 = new MultiwaveColorParams(5000.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p1.setTricubicRGB(new int[][]{{192, 64, 64}, {192, 64, 64}, {81, 71, 71}});

        MultiwaveColorParams p2 = new MultiwaveColorParams(10.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p2.setTricubicRGB(new int[][]{{199, 83, 83}, {192, 64, 64}, {172, 58, 58}, {192, 64, 64}});

        MultiwaveColorParams p3 = new MultiwaveColorParams(17.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p3.setTricubicRGB(new int[][]{{211, 121, 121}, {192, 64, 64}, {135, 45, 45}, {192, 64, 64}});

        MultiwaveColorParams p4 = new MultiwaveColorParams(2544.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p4.setTricubicRGB(new int[][]{{243, 217, 217}, {192, 64, 64}, {39, 13, 13}, {192, 64, 64}});

        MultiwaveColorParams p5 = new MultiwaveColorParams(235.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p5.setTricubicRGB(new int[][]{{192, 64, 64}, {76, 26, 26}, {192, 64, 64}, {231, 179, 179}});

        MultiwaveColorParams p6 = new MultiwaveColorParams(Math.pow(2, 16), Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);

        p6.setLinearRGB(new LinearRGB[] {
                new LinearRGB(0.0, 11, 25, 12),
                new LinearRGB(0.375, 192, 64, 64),
                new LinearRGB(0.5875, 192, 64, 64),
                new LinearRGB(0.6125, 179, 177, 177),
                new LinearRGB(0.69, 128, 237, 19),
                new LinearRGB(0.7, 78, 99, 102),
                new LinearRGB(0.7025, 63, 53, 131),
                new LinearRGB(0.715, 0, 153, 180),
                new LinearRGB(0.74, 4, 154, 184),
                new LinearRGB(0.7475, 204, 34, 190),
                new LinearRGB(0.7875, 216, 194, 195),
                new LinearRGB(0.8325, 183, 154, 61),
                new LinearRGB(1.0, 243, 227, 234),
        });

        MultiwaveColorParams[] params = {
                p0,
                p1,
                p2,
                p3,
                p4,
                p5,
                p6
        };

        g_spdz2_custom_params = params;
    }

    static void create_g_spdz2_params() {
        MultiwaveColorParams p0 = new MultiwaveColorParams(1175000.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p0.setMetaTricubicRGB(new MetaTricubicRGB(new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        MultiwaveColorParams p1 = new MultiwaveColorParams(5000.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p1.setTricubicRGB(new int[][]{{192, 64, 64}, {192, 64, 64}, {81, 71, 71}});

        MultiwaveColorParams p2 = new MultiwaveColorParams(10.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p2.setTricubicRGB(new int[][]{{199, 83, 83}, {192, 64, 64}, {172, 58, 58}, {192, 64, 64}});

        MultiwaveColorParams p3 = new MultiwaveColorParams(17.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p3.setTricubicRGB(new int[][]{{211, 121, 121}, {192, 64, 64}, {135, 45, 45}, {192, 64, 64}});

        MultiwaveColorParams p4 = new MultiwaveColorParams(2544.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p4.setTricubicRGB(new int[][]{{243, 217, 217}, {192, 64, 64}, {39, 13, 13}, {192, 64, 64}});

        MultiwaveColorParams p5 = new MultiwaveColorParams(235.0, Mapping.NORMAL, null, null,  Blend.HSL_BIAS_UFCOMPAT);
        p5.setTricubicRGB(new int[][]{{192, 64, 64}, {76, 26, 26}, {192, 64, 64}, {231, 179, 179}});

        MultiwaveColorParams p6 = new MultiwaveColorParams(null, Mapping.LOG, 1.0, 16777216.0,  Blend.HSL_BIAS_UFCOMPAT);
        p6.setLinearRGB(new LinearRGB[] {
                new LinearRGB(0.0, 11, 25, 12),
                new LinearRGB(0.375, 192, 64, 64),
                new LinearRGB(0.5875, 192, 64, 64),
                new LinearRGB(0.6125, 179, 177, 177),
                new LinearRGB(0.69, 128, 237, 19),
                new LinearRGB(0.7, 78, 99, 102),
                new LinearRGB(0.7025, 63, 53, 131),
                new LinearRGB(0.715, 0, 153, 180),
                new LinearRGB(0.74, 4, 154, 184),
                new LinearRGB(0.7475, 204, 34, 190),
                new LinearRGB(0.7875, 216, 194, 195),
                new LinearRGB(0.8325, 183, 154, 61),
                new LinearRGB(1.0, 243, 227, 234),
        });

        MultiwaveColorParams[] params = {
                p0,
                p1,
                p2,
                p3,
                p4,
                p5,
                p6
        };

        g_spdz2_params = params;
    }
    public static int g_spdz2(double n) throws Exception {
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(g_spdz2_params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int g_spdz2_custom(double n) throws Exception {
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(g_spdz2_custom_params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int linear_only(double n) throws Exception {


        MultiwaveColorParams p1 = new MultiwaveColorParams(Math.pow(2, 16), Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p1.setLinearRGB(new LinearRGB[] {
                new LinearRGB(0.0, 11, 25, 12),
                new LinearRGB(0.375, 192, 64, 64),
                new LinearRGB(0.5875, 192, 64, 64),
                new LinearRGB(0.6125, 179, 177, 177),
                new LinearRGB(0.69, 128, 237, 19),
                new LinearRGB(0.7, 78, 99, 102),
                new LinearRGB(0.7025, 63, 53, 131),
                new LinearRGB(0.715, 0, 153, 180),
                new LinearRGB(0.74, 4, 154, 184),
                new LinearRGB(0.7475, 204, 34, 190),
                new LinearRGB(0.7875, 216, 194, 195),
                new LinearRGB(0.8325, 183, 154, 61),
                new LinearRGB(1.0, 243, 227, 234),

        });

        MultiwaveColorParams[] params = {
                p1
        };
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];
    }

    public static int meta_tricubic_gradient_only(double n) throws Exception {

        MultiwaveColorParams p1 = new MultiwaveColorParams(1175000.0, Mapping.NORMAL, null, null, Blend.HSL_BIAS_UFCOMPAT);
        p1.setMetaTricubicRGB(new MetaTricubicRGB(new int[][][]{
                {{15, 91, 30}, {60, 62, 128}, {71, 37, 95}, {45, 45, 53}, {64, 62, 80}}
                ,{{56, 240, 80}, {187, 141, 199}, {142, 128, 146}, {24, 24, 164}, {135, 155, 171}}
                ,{{74, 186, 77}, {73, 0, 92}, {195, 130, 234}, {151, 149, 189}, {175, 199, 196}}
                ,{{29, 39, 227}, {225, 33, 255}, {9, 95, 233}, {120, 84, 100}, {21, 33, 123}}
        }, 0.02127659574468085, 8E-4));

        MultiwaveColorParams[] params = {
                p1
        };
        int[] rgb = hslToRgb(multiwaveColor(MultiwaveColorParams.build(params, n)));
        return 0xff000000 | rgb[0] << 16 | rgb[1] << 8 | rgb[2];

    }

    public static String paramsToJson(MultiwaveColorParams[] params, boolean includeNulls) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if(!includeNulls) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        String text = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params);
        return text.replace("\r\n", "\n");
    }

    public static MultiwaveColorParams[] jsonToParams(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        return objectMapper.readValue(json, MultiwaveColorParams[].class);
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
