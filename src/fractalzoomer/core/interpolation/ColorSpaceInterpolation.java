package fractalzoomer.core.interpolation;

import fractalzoomer.utils.ColorCorrection;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.MathUtils;

import java.awt.geom.Point2D;

public class ColorSpaceInterpolation {

    private static Point2D.Double[][] bezierControlPoints_red;
    private static Point2D.Double[][] bezierControlPoints_green;
    private static Point2D.Double[][] bezierControlPoints_blue;

    private static int[][] basis_spline_red;
    private static int[][] basis_spline_green;
    private static int[][] basis_spline_blue;

    public static int HSBInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsb = ColorSpaceConverter.RGBtoHSB(r1, g1, b1);
        double[] c2_hsb = ColorSpaceConverter.RGBtoHSB(r2, g2, b2);

        double h;
        double s = method.interpolate(c1_hsb[1], c2_hsb[1], coef);
        double b = method.interpolate(c1_hsb[2], c2_hsb[2], ColorCorrection.modifyIntensityCurve(coef));

        double d = c2_hsb[0] - c1_hsb[0];

        double temp;
        if (c1_hsb[0] > c2_hsb[0]) {
            temp = c1_hsb[0];
            c1_hsb[0] = c2_hsb[0];
            c2_hsb[0] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 0.5 : d <= 0.5;

        if (condition) {
            c1_hsb[0] += 1;
        }

        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef) % 1.0;

        int[] res = ColorSpaceConverter.HSBtoRGB(h, s, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int HSLInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsl = ColorSpaceConverter.RGBtoHSL(r1, g1, b1);
        double[] c2_hsl = ColorSpaceConverter.RGBtoHSL(r2, g2, b2);

        double h;
        double s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
        double l = method.interpolate(c1_hsl[2], c2_hsl[2], ColorCorrection.modifyIntensityCurve(coef));

        double d = c2_hsl[0] - c1_hsl[0];

        double temp;
        if (c1_hsl[0] > c2_hsl[0]) {
            temp = c1_hsl[0];
            c1_hsl[0] = c2_hsl[0];
            c2_hsl[0] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 0.5 : d <= 0.5;

        if (condition) {
            c1_hsl[0] += 1;
        }

        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 1.0;

        int[] res = ColorSpaceConverter.HSLtoRGB(h, s, l);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int CubehelixInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsl = ColorSpaceConverter.RGBtoCubehelix(r1, g1, b1);
        double[] c2_hsl = ColorSpaceConverter.RGBtoCubehelix(r2, g2, b2);

        double h = Double.NaN;
        double s = Double.NaN;
        double l = method.interpolate(c1_hsl[2], c2_hsl[2], ColorCorrection.modifyIntensityCurve(coef));

        if(!Double.isNaN(c2_hsl[1]) && !Double.isNaN(c1_hsl[1])) {
            s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
        }
        else if(!Double.isNaN(c2_hsl[1])) {
            s = c2_hsl[1];
        }
        else if(!Double.isNaN(c1_hsl[1])) {
            s = c1_hsl[1];
        }

        if(!Double.isNaN(c2_hsl[0]) && !Double.isNaN(c1_hsl[0])) {
            double d = c2_hsl[0] - c1_hsl[0];

            double temp;
            if (c1_hsl[0] > c2_hsl[0]) {
                temp = c1_hsl[0];
                c1_hsl[0] = c2_hsl[0];
                c2_hsl[0] = temp;
                d = -d;
                coef = 1 - coef;
            }

            boolean condition = shortPath ? d > 180 : d <= 180;

            if (condition) {
                c1_hsl[0] += 360.0;
            }

            h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 360.0;
        }
        else if(!Double.isNaN(c2_hsl[0])) {
            h = c2_hsl[0];
        }
        else if(!Double.isNaN(c1_hsl[0])) {
            h = c1_hsl[0];
        }

        int[] res = ColorSpaceConverter.CubehelixtoRGB(h, s, l);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int RGBInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        return method.interpolateColorsInternal(r1, g1, b1, r2, g2, b2, ColorCorrection.modifyIntensityCurve(coef));
    }

    public static int ExpInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double c2_1 = r2 == 0 ? r2 + 1 : r2;
        double c2_2 = g2 == 0 ? g2 + 1 : g2;
        double c2_3 = b2 == 0 ? b2 + 1 : b2;

        double c1_1 = r1 == 0 ? r1 + 1 : r1;
        double c1_2 = g1 == 0 ? g1 + 1 : g1;
        double c1_3 = b1 == 0 ? b1 + 1 : b1;

        double to_r = Math.log(c2_1);
        double from_r = Math.log(c1_1);

        double to_g = Math.log(c2_2);
        double from_g = Math.log(c1_2);

        double to_b = Math.log(c2_3);
        double from_b = Math.log(c1_3);

        coef = ColorCorrection.modifyIntensityCurve(coef);
        int red = (int) (Math.exp(method.interpolate(from_r, to_r, coef)));
        int green = (int) (Math.exp(method.interpolate(from_g, to_g, coef)));
        int blue = (int) (Math.exp(method.interpolate(from_b, to_b, coef)));

        return  0xff000000 | (red << 16) | (green << 8) | blue;
    }

    public static int SquareInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double to_r = Math.sqrt(r2);
        double from_r = Math.sqrt(r1);

        double to_g = Math.sqrt(g2);
        double from_g = Math.sqrt(g1);

        double to_b = Math.sqrt(b2);
        double from_b = Math.sqrt(b1);

        coef = ColorCorrection.modifyIntensityCurve(coef);
        int red = (int) (Math.pow(method.interpolate(from_r, to_r, coef), 2));
        int green = (int) (Math.pow(method.interpolate(from_g, to_g, coef), 2));
        int blue = (int) (Math.pow(method.interpolate(from_b, to_b, coef), 2));

        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    public static int SqrtInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double to_r = r2 * r2;
        double from_r = r1 * r1;

        double to_g = g2 * g2;
        double from_g = g1 * g1;

        double to_b = b2 * b2;
        double from_b = b1 * b1;

        coef = ColorCorrection.modifyIntensityCurve(coef);
        int red = (int) (Math.sqrt(method.interpolate(from_r, to_r, coef)));
        int green = (int) (Math.sqrt(method.interpolate(from_g, to_g, coef)));
        int blue = (int) (Math.sqrt(method.interpolate(from_b, to_b, coef)));

        return 0xff000000 | (red << 16) | (green << 8) | blue;
    }

    public static int YCbCrInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        int[] ycbcr_from = ColorSpaceConverter.RGBtoYCbCr(r1, g1, b1);
        int[] ycbcr_to = ColorSpaceConverter.RGBtoYCbCr(r2, g2, b2);

        int y = method.interpolate(ycbcr_from[0], ycbcr_to[0], ColorCorrection.modifyIntensityCurve(coef));
        int cb = method.interpolate(ycbcr_from[1], ycbcr_to[1], coef);
        int cr = method.interpolate(ycbcr_from[2], ycbcr_to[2], coef);

        int[] rgb = ColorSpaceConverter.YCbCrtoRGB(y, cb, cr);

        rgb[0] = ColorSpaceConverter.clamp(rgb[0]);
        rgb[1] = ColorSpaceConverter.clamp(rgb[1]);
        rgb[2] = ColorSpaceConverter.clamp(rgb[2]);

        return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    public static int RYBInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] ryb_from = ColorSpaceConverter.RGBtoRYB(r1, g1, b1);
        double[] ryb_to = ColorSpaceConverter.RGBtoRYB(r2, g2, b2);

        coef = ColorCorrection.modifyIntensityCurve(coef);
        double r = method.interpolate(ryb_from[0], ryb_to[0], coef);
        double y = method.interpolate(ryb_from[1], ryb_to[1], coef);
        double b = method.interpolate(ryb_from[2], ryb_to[2], coef);

        int[] rgb = ColorSpaceConverter.RYBtoRGB(r, y, b);

        rgb[0] = ColorSpaceConverter.clamp(rgb[0]);
        rgb[1] = ColorSpaceConverter.clamp(rgb[1]);
        rgb[2] = ColorSpaceConverter.clamp(rgb[2]);

        return 0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    public static int LABInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] from = ColorSpaceConverter.RGBtoLAB(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLAB(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double a = method.interpolate(from[1], to[1], coef);
        double b = method.interpolate(from[2], to[2], coef);

        int[] res = ColorSpaceConverter.LABtoRGB(L, a, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int XYZInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] from = ColorSpaceConverter.RGBtoXYZ(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoXYZ(r2, g2, b2);

        coef = ColorCorrection.modifyIntensityCurve(coef);
        double X = method.interpolate(from[0], to[0], coef);
        double Y = method.interpolate(from[1], to[1], coef);
        double Z = method.interpolate(from[2], to[2], coef);

        int[] res = ColorSpaceConverter.XYZtoRGB(X, Y, Z);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int LCHabInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] from = ColorSpaceConverter.RGBtoLCH_ab(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLCH_ab(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double C = method.interpolate(from[1], to[1], coef);
        double H;

        double d = to[2] - from[2];

        double temp;
        if (from[2] > to[2]) {
            temp = from[2];
            from[2] = to[2];
            to[2] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            from[2] += 360;
        }

        H = method.interpolate(from[2], to[2], coef) % 360.0;

        int[] res = ColorSpaceConverter.LCH_abtoRGB(L, C, H);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static void calculateBezierControlPoints(int[][] colors) {

        Point2D.Double[] knots_red = new Point2D.Double[colors.length + 1];
        Point2D.Double[] knots_green = new Point2D.Double[colors.length + 1];
        Point2D.Double[] knots_blue = new Point2D.Double[colors.length + 1]; // +1 to cycle the palette

        for (int i = 0; i < knots_red.length; i++) {
            knots_red[i] = new Point2D.Double(i, colors[i % colors.length][1]);
            knots_green[i] = new Point2D.Double(i, colors[i % colors.length][2]);
            knots_blue[i] = new Point2D.Double(i, colors[i % colors.length][3]);
        }

        bezierControlPoints_red = MathUtils.BezierSpline.GetCurveControlPoints(knots_red);
        bezierControlPoints_green = MathUtils.BezierSpline.GetCurveControlPoints(knots_green);
        bezierControlPoints_blue = MathUtils.BezierSpline.GetCurveControlPoints(knots_blue);

    }

    public static void calculateBasisSplinePoints(int[][] colors) {

        basis_spline_red = new int[colors.length][2];
        basis_spline_green = new int[colors.length][2];
        basis_spline_blue = new int[colors.length][2];

        int red_index = 1;
        int green_index = 2;
        int blue_index = 3;

        for(int i = 0; i < colors.length; i++) {
            int im1 = (i + colors.length - 1) % colors.length;
            int ip2 = (i + 2) % colors.length;

            basis_spline_red[i][0] = colors[im1][red_index];
            basis_spline_red[i][1] = colors[ip2][red_index];

            basis_spline_green[i][0] = colors[im1][green_index];
            basis_spline_green[i][1] = colors[ip2][green_index];

            basis_spline_blue[i][0] = colors[im1][blue_index];
            basis_spline_blue[i][1] = colors[ip2][blue_index];
        }

    }

    private static double evaluateBezier(double t, double p0, double p1, double p2, double p3) {

        double one_m_t = 1 - t;
        double one_m_t_sqr = one_m_t * one_m_t;
        double one_m_t_cube = one_m_t_sqr * one_m_t;
        double t_sqr = t * t;
        double t_cube = t_sqr * t;
        return one_m_t_cube * p0 + 3 * one_m_t_sqr * t * p1 + 3 * one_m_t * t_sqr * p2 + t_cube * p3;

    }

    private static double evaluateBasisSpline(double t, double v0, double v1, double v2, double v3) {
        double t2 = t * t, t3 = t2 * t;
        return ((1 - 3 * t + 3 * t2 - t3) * v0
                + (4 - 6 * t2 + 3 * t3) * v1
                + (1 + 3 * t + 3 * t2 - 3 * t3) * v2
                + t3 * v3) / 6;
    }

    public static int BezierRGBInterpolation(InterpolationMethod method, int i, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        coef = ColorCorrection.modifyIntensityCurve(coef);
        double a = method.getCoef(coef);
        int red = ColorSpaceConverter.clamp((int) evaluateBezier(a, r1, bezierControlPoints_red[0][i].y, bezierControlPoints_red[1][i].y, r2));
        int green = ColorSpaceConverter.clamp((int) evaluateBezier(a, g1, bezierControlPoints_green[0][i].y, bezierControlPoints_green[1][i].y, g2));
        int blue = ColorSpaceConverter.clamp((int) evaluateBezier(a, b1, bezierControlPoints_blue[0][i].y, bezierControlPoints_blue[1][i].y, b2));

        return  0xff000000 | (red << 16) | (green << 8) | blue;
    }

    public static int BasisSplineRGBInterpolation(InterpolationMethod method, int i, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        coef = ColorCorrection.modifyIntensityCurve(coef);
        double a = method.getCoef(coef);
        int red = ColorSpaceConverter.clamp((int) evaluateBasisSpline(a, basis_spline_red[i][0], r1, r2, basis_spline_red[i][1]));
        int green = ColorSpaceConverter.clamp((int) evaluateBasisSpline(a, basis_spline_green[i][0], g1, g2, basis_spline_green[i][1]));
        int blue = ColorSpaceConverter.clamp((int) evaluateBasisSpline(a, basis_spline_blue[i][0], b1, b2, basis_spline_blue[i][1]));

        return  0xff000000 | (red << 16) | (green << 8) | blue;
    }

    public static int LUVInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] from = ColorSpaceConverter.RGBtoLUV(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLUV(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double a = method.interpolate(from[1], to[1], coef);
        double b = method.interpolate(from[2], to[2], coef);

        int[] res = ColorSpaceConverter.LUVtoRGB(L, a, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int LCHuvInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] from = ColorSpaceConverter.RGBtoLCH_uv(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLCH_uv(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double C = method.interpolate(from[1], to[1], coef);
        double H;

        double d = to[2] - from[2];

        double temp;
        if (from[2] > to[2]) {
            temp = from[2];
            from[2] = to[2];
            to[2] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            from[2] += 360;
        }

        H = method.interpolate(from[2], to[2], coef) % 360.0;

        int[] res = ColorSpaceConverter.LCH_uvtoRGB(L, C, H);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int OKLABInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] from = ColorSpaceConverter.RGBtoOKLAB(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoOKLAB(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double a = method.interpolate(from[1], to[1], coef);
        double b = method.interpolate(from[2], to[2], coef);

        int[] res = ColorSpaceConverter.OKLABtoRGB(L, a, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int LinearRGBInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] rgb_from = ColorSpaceConverter.RGBtoLinearRGB(r1, g1, b1);
        double[] rgb_to = ColorSpaceConverter.RGBtoLinearRGB(r2, g2, b2);

        coef = ColorCorrection.modifyIntensityCurve(coef);
        double r = method.interpolate(rgb_from[0], rgb_to[0], coef);
        double y = method.interpolate(rgb_from[1], rgb_to[1], coef);
        double b = method.interpolate(rgb_from[2], rgb_to[2], coef);

        int[] rgb = ColorSpaceConverter.LinearRGBtoRGB(r, y, b);

        rgb[0] = ColorSpaceConverter.clamp(rgb[0]);
        rgb[1] = ColorSpaceConverter.clamp(rgb[1]);
        rgb[2] = ColorSpaceConverter.clamp(rgb[2]);

        return  0xff000000 | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    public static int HWBInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsb = ColorSpaceConverter.RGBtoHWB(r1, g1, b1);
        double[] c2_hsb = ColorSpaceConverter.RGBtoHWB(r2, g2, b2);

        double h;
        double s = method.interpolate(c1_hsb[1], c2_hsb[1], ColorCorrection.modifyIntensityCurve(coef));
        double b = method.interpolate(c1_hsb[2], c2_hsb[2], ColorCorrection.modifyIntensityCurve(coef));

        double d = c2_hsb[0] - c1_hsb[0];

        double temp;
        if (c1_hsb[0] > c2_hsb[0]) {
            temp = c1_hsb[0];
            c1_hsb[0] = c2_hsb[0];
            c2_hsb[0] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 0.5 : d <= 0.5;

        if (condition) {
            c1_hsb[0] += 1;
        }

        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef) % 1.0;

        int[] res = ColorSpaceConverter.HWBtoRGB(h, s, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int HPLuvInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsl = ColorSpaceConverter.RGBtoHPL_uv(r1, g1, b1);
        double[] c2_hsl = ColorSpaceConverter.RGBtoHPL_uv(r2, g2, b2);

        double h;
        double s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
        double l = method.interpolate(c1_hsl[2], c2_hsl[2], ColorCorrection.modifyIntensityCurve(coef));

        double d = c2_hsl[0] - c1_hsl[0];

        double temp;
        if (c1_hsl[0] > c2_hsl[0]) {
            temp = c1_hsl[0];
            c1_hsl[0] = c2_hsl[0];
            c2_hsl[0] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            c1_hsl[0] += 360;
        }

        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 360.0;

        int[] res = ColorSpaceConverter.HPL_uvtoRGB(h, s, l);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int HSLuvInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] c1_hsl = ColorSpaceConverter.RGBtoHSL_uv(r1, g1, b1);
        double[] c2_hsl = ColorSpaceConverter.RGBtoHSL_uv(r2, g2, b2);

        double h;
        double s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
        double l = method.interpolate(c1_hsl[2], c2_hsl[2], ColorCorrection.modifyIntensityCurve(coef));

        double d = c2_hsl[0] - c1_hsl[0];

        double temp;
        if (c1_hsl[0] > c2_hsl[0]) {
            temp = c1_hsl[0];
            c1_hsl[0] = c2_hsl[0];
            c2_hsl[0] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            c1_hsl[0] += 360;
        }

        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 360.0;

        int[] res = ColorSpaceConverter.HSL_uvtoRGB(h, s, l);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int LCHJzAzBzInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] from = ColorSpaceConverter.RGBtoLCH_JzAzBz(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLCH_JzAzBz(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double C = method.interpolate(from[1], to[1], coef);
        double H;

        double d = to[2] - from[2];

        double temp;
        if (from[2] > to[2]) {
            temp = from[2];
            from[2] = to[2];
            to[2] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            from[2] += 360;
        }

        H = method.interpolate(from[2], to[2], coef) % 360.0;

        int[] res = ColorSpaceConverter.LCH_JzAzBztoRGB(L, C, H);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int JzAzBzInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2) {
        double[] from = ColorSpaceConverter.RGBtoJzAzBz(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoJzAzBz(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double a = method.interpolate(from[1], to[1], coef);
        double b = method.interpolate(from[2], to[2], coef);

        int[] res = ColorSpaceConverter.JzAzBztoRGB(L, a, b);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }

    public static int LCHOklabInterpolation(InterpolationMethod method, double coef, int r1, int g1, int b1, int r2, int g2, int b2, boolean shortPath) {
        double[] from = ColorSpaceConverter.RGBtoLCH_oklab(r1, g1, b1);

        double[] to = ColorSpaceConverter.RGBtoLCH_oklab(r2, g2, b2);

        double L = method.interpolate(from[0], to[0], ColorCorrection.modifyIntensityCurve(coef));
        double C = method.interpolate(from[1], to[1], coef);
        double H;

        double d = to[2] - from[2];

        double temp;
        if (from[2] > to[2]) {
            temp = from[2];
            from[2] = to[2];
            to[2] = temp;
            d = -d;
            coef = 1 - coef;
        }

        boolean condition = shortPath ? d > 180 : d <= 180;

        if (condition) {
            from[2] += 360;
        }

        H = method.interpolate(from[2], to[2], coef) % 360.0;

        int[] res = ColorSpaceConverter.LCH_oklabtoRGB(L, C, H);

        res[0] = ColorSpaceConverter.clamp(res[0]);
        res[1] = ColorSpaceConverter.clamp(res[1]);
        res[2] = ColorSpaceConverter.clamp(res[2]);

        return  0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
    }
}
