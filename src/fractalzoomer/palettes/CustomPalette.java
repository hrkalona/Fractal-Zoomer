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
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.MathUtils.BezierSpline;
import java.awt.Color;
import java.awt.geom.Point2D;

/**
 *
 * @author hrkalona2
 */
public class CustomPalette extends Palette {

    private static Point2D.Double[][] bezierControlPoints_red;
    private static Point2D.Double[][] bezierControlPoints_green;
    private static Point2D.Double[][] bezierControlPoints_blue;

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean smoothing, Color special_color, int color_smoothing_method, boolean special_use_palette_color) {
        super();

        int[] palette = createPalette(custom_palette, reverse, processing_alg, scale_factor_palette_val, color_interpolation, color_space, 0);

        if (!smoothing) {
            palette_color = new PaletteColorNormal(palette, special_color, special_use_palette_color);
        } else {
            palette_color = new PaletteColorSmooth(palette, special_color, color_smoothing_method, special_use_palette_color);
        }
    }

    public static Color[] getPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int color_cycling_location, double scale_factor_palette_val, int processing_alg) {

        if (color_cycling_location < 0) {
            throw new ArithmeticException();
        }

        int[] palette = createPalette(custom_palette, reverse, processing_alg, scale_factor_palette_val, color_interpolation, color_space, color_cycling_location);

        Color[] palette2 = new Color[palette.length];

        for (int i = 0; i < palette2.length; i++) {
            palette2[i] = new Color(palette[i]);
        }

        return palette2;

    }

    public static Color[] getGradient(int colorA, int colorB, int length, int color_interpolation, int color_space, boolean reversed, int offset) {

        int[] palette = createGradient(colorA, colorB, length, color_interpolation, color_space, reversed, offset);

        Color[] palette2 = new Color[palette.length];

        for (int i = 0; i < palette2.length; i++) {
            palette2[i] = new Color(palette[i]);
        }

        return palette2;

    }

    public static int[] createGradient(int colorA, int colorB, int length, int color_interpolation, int color_space, boolean reversed, int offset) {

        int[] palette = new int[length]; // allocate pallete

        int color1 = colorA;
        int color2 = colorB;

        if (reversed) {
            color1 = colorB;
            color2 = colorA;
        }

        int[] colors1 = {length, (color1 >> 16) & 0xff, (color1 >> 8) & 0xff, color1 & 0xff}; // first referential color
        int[] colors2 = {0, (color2 >> 16) & 0xff, (color2 >> 8) & 0xff, color2 & 0xff}; // second ref. color
        int[][] colors = {colors1, colors2};

        if (color_space == MainWindow.COLOR_SPACE_BEZIER_RGB) {
            calculateBezierControlPoints(colors);
        }

        InterpolationMethod method = null;

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

        int n = length - offset % length;
        int red, green, blue;

        for (int i = 0; i < colors.length - 1; i++) { // interpolate first color only
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                if (color_space == MainWindow.COLOR_SPACE_HSB) {
                    double[] c1_hsb = ColorSpaceConverter.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = ColorSpaceConverter.RGBtoHSB(c2[1], c2[2], c2[3]);

                    double h;
                    double s = method.interpolate(c1_hsb[1], c2_hsb[1], coef);
                    double b = method.interpolate(c1_hsb[2], c2_hsb[2], coef);

                    double d = c2_hsb[0] - c1_hsb[0];

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if (d > 0.5) {
                        c1_hsb[0] += 1;
                        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef) % 1.0;
                    } else {
                        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef);
                    }

                    int[] res = ColorSpaceConverter.HSBtoRGB(h, s, b);

                    palette[(n + k) % palette.length] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                } else if (color_space == MainWindow.COLOR_SPACE_HSL) {
                    double[] c1_hsl = ColorSpaceConverter.RGBtoHSL(c1[1], c1[2], c1[3]);
                    double[] c2_hsl = ColorSpaceConverter.RGBtoHSL(c2[1], c2[2], c2[3]);

                    double h;
                    double s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
                    double l = method.interpolate(c1_hsl[2], c2_hsl[2], coef);

                    double d = c2_hsl[0] - c1_hsl[0];

                    double temp;
                    if (c1_hsl[0] > c2_hsl[0]) {
                        temp = c1_hsl[0];
                        c1_hsl[0] = c2_hsl[0];
                        c2_hsl[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if (d > 0.5) {
                        c1_hsl[0] += 1;
                        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 1.0;
                    } else {
                        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef);
                    }

                    int[] res = ColorSpaceConverter.HSLtoRGB(h, s, l);

                    palette[(n + k) % palette.length] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                } else if (color_space == MainWindow.COLOR_SPACE_RGB) {
                    palette[(n + k) % palette.length] = method.interpolate(c1[1], c1[2], c1[3], c2[1], c2[2], c2[3], coef);//0xff000000 | (red << 16) | (green << 8) | blue;
                    //System.out.print((0xff000000 | (red << 16) | (green << 8) | blue) + ", ");
                } else if (color_space == MainWindow.COLOR_SPACE_EXP) {
                    double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                    double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                    double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];

                    double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                    double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                    double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];

                    double to_r = Math.log(c2_1);
                    double from_r = Math.log(c1_1);

                    double to_g = Math.log(c2_2);
                    double from_g = Math.log(c1_2);

                    double to_b = Math.log(c2_3);
                    double from_b = Math.log(c1_3);

                    red = (int) (Math.exp(method.interpolate(from_r, to_r, coef)));
                    green = (int) (Math.exp(method.interpolate(from_g, to_g, coef)));
                    blue = (int) (Math.exp(method.interpolate(from_b, to_b, coef)));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);

                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);

                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);

                    red = (int) (Math.pow(method.interpolate(from_r, to_r, coef), 2));
                    green = (int) (Math.pow(method.interpolate(from_g, to_g, coef), 2));
                    blue = (int) (Math.pow(method.interpolate(from_b, to_b, coef), 2));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];

                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];

                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];

                    red = (int) (Math.sqrt(method.interpolate(from_r, to_r, coef)));
                    green = (int) (Math.sqrt(method.interpolate(from_g, to_g, coef)));
                    blue = (int) (Math.sqrt(method.interpolate(from_b, to_b, coef)));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_RYB) {
                    double[] ryb_from = ColorSpaceConverter.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = ColorSpaceConverter.RGBtoRYB(c2[1], c2[2], c2[3]);

                    double r = method.interpolate(ryb_from[0], ryb_to[0], coef);
                    double y = method.interpolate(ryb_from[1], ryb_to[1], coef);
                    double b = method.interpolate(ryb_from[2], ryb_to[2], coef);

                    int[] rgb = ColorSpaceConverter.RYBtoRGB(r, y, b);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (rgb[0]) << 16) | ((int) (rgb[1]) << 8) | (int) (rgb[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_LAB) {
                    double[] from = ColorSpaceConverter.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = method.interpolate(from[0], to[0], coef);
                    double a = method.interpolate(from[1], to[1], coef);
                    double b = method.interpolate(from[2], to[2], coef);

                    int[] res = ColorSpaceConverter.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_XYZ) {
                    double[] from = ColorSpaceConverter.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = method.interpolate(from[0], to[0], coef);
                    double Y = method.interpolate(from[1], to[1], coef);
                    double Z = method.interpolate(from[2], to[2], coef);

                    int[] res = ColorSpaceConverter.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_LCH) {
                    double[] from = ColorSpaceConverter.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = method.interpolate(from[0], to[0], coef);
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

                    if (d > 180) {
                        from[2] += 360;
                        H = method.interpolate(from[2], to[2], coef) % 360.0;
                    } else {
                        H = method.interpolate(from[2], to[2], coef);
                    }

                    int[] res = ColorSpaceConverter.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_BEZIER_RGB) {
                    double a = method.getCoef(coef);
                    red = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[1], bezierControlPoints_red[0][i].y, bezierControlPoints_red[1][i].y, c2[1]));
                    green = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[2], bezierControlPoints_green[0][i].y, bezierControlPoints_green[1][i].y, c2[2]));
                    blue = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[3], bezierControlPoints_blue[0][i].y, bezierControlPoints_blue[1][i].y, c2[3]));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
            }
        }

        return palette;
    }

    private static int[] createPalette(int[][] custom_palette, boolean reverse, int processing_alg, double scale_factor_palette_val, int color_interpolation, int color_space, int color_cycling_location) {

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if (custom_palette[i][0] > 0) {
                counter++;
            } else if (custom_palette[i][0] < 0) {
                throw new ArithmeticException();
            }
        }

        int[][] colors = new int[counter][4];

        if (reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if (custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        } else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if (custom_palette[i][0] != 0) {
                    //System.out.print("{" + custom_palette[i][0] + ", " + custom_palette[i][1] + ", " + custom_palette[i][2] + ", " + custom_palette[i][3] + "},");
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if (processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RYB1 || processing_alg == MainWindow.PROCESSING_RYB2) {
            cycleRYB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        if (color_space == MainWindow.COLOR_SPACE_BEZIER_RGB) {
            calculateBezierControlPoints(colors);
        }

        InterpolationMethod method = null;

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

        n = n - color_cycling_location % n;
        //System.out.print("{");
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            //if(c1[0] == 1) {
            //  palette[n] = new Color(c1[1], c1[2], c1[3]);   
            //} 
            // else {
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                //(c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
                double coef = j;

                if (color_space == MainWindow.COLOR_SPACE_HSB) {
                    double[] c1_hsb = ColorSpaceConverter.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = ColorSpaceConverter.RGBtoHSB(c2[1], c2[2], c2[3]);

                    double h;
                    double s = method.interpolate(c1_hsb[1], c2_hsb[1], coef);
                    double b = method.interpolate(c1_hsb[2], c2_hsb[2], coef);

                    double d = c2_hsb[0] - c1_hsb[0];

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if (d > 0.5) {
                        c1_hsb[0] += 1;
                        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef) % 1.0;
                    } else {
                        h = method.interpolate(c1_hsb[0], c2_hsb[0], coef);
                    }

                    int[] res = ColorSpaceConverter.HSBtoRGB(h, s, b);

                    palette[(n + k) % palette.length] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                } else if (color_space == MainWindow.COLOR_SPACE_HSL) {
                    double[] c1_hsl = ColorSpaceConverter.RGBtoHSL(c1[1], c1[2], c1[3]);
                    double[] c2_hsl = ColorSpaceConverter.RGBtoHSL(c2[1], c2[2], c2[3]);

                    double h;
                    double s = method.interpolate(c1_hsl[1], c2_hsl[1], coef);
                    double l = method.interpolate(c1_hsl[2], c2_hsl[2], coef);

                    double d = c2_hsl[0] - c1_hsl[0];

                    double temp;
                    if (c1_hsl[0] > c2_hsl[0]) {
                        temp = c1_hsl[0];
                        c1_hsl[0] = c2_hsl[0];
                        c2_hsl[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if (d > 0.5) {
                        c1_hsl[0] += 1;
                        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef) % 1.0;
                    } else {
                        h = method.interpolate(c1_hsl[0], c2_hsl[0], coef);
                    }

                    int[] res = ColorSpaceConverter.HSLtoRGB(h, s, l);

                    palette[(n + k) % palette.length] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                } else if (color_space == MainWindow.COLOR_SPACE_RGB) {
                    palette[(n + k) % palette.length] = method.interpolate(c1[1], c1[2], c1[3], c2[1], c2[2], c2[3], coef);//0xff000000 | (red << 16) | (green << 8) | blue;
                    //System.out.print((0xff000000 | (red << 16) | (green << 8) | blue) + ", ");
                } else if (color_space == MainWindow.COLOR_SPACE_EXP) {
                    double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                    double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                    double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];

                    double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                    double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                    double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];

                    double to_r = Math.log(c2_1);
                    double from_r = Math.log(c1_1);

                    double to_g = Math.log(c2_2);
                    double from_g = Math.log(c1_2);

                    double to_b = Math.log(c2_3);
                    double from_b = Math.log(c1_3);

                    red = (int) (Math.exp(method.interpolate(from_r, to_r, coef)));
                    green = (int) (Math.exp(method.interpolate(from_g, to_g, coef)));
                    blue = (int) (Math.exp(method.interpolate(from_b, to_b, coef)));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);

                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);

                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);

                    red = (int) (Math.pow(method.interpolate(from_r, to_r, coef), 2));
                    green = (int) (Math.pow(method.interpolate(from_g, to_g, coef), 2));
                    blue = (int) (Math.pow(method.interpolate(from_b, to_b, coef), 2));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];

                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];

                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];

                    red = (int) (Math.sqrt(method.interpolate(from_r, to_r, coef)));
                    green = (int) (Math.sqrt(method.interpolate(from_g, to_g, coef)));
                    blue = (int) (Math.sqrt(method.interpolate(from_b, to_b, coef)));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                } else if (color_space == MainWindow.COLOR_SPACE_RYB) {
                    double[] ryb_from = ColorSpaceConverter.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = ColorSpaceConverter.RGBtoRYB(c2[1], c2[2], c2[3]);

                    double r = method.interpolate(ryb_from[0], ryb_to[0], coef);
                    double y = method.interpolate(ryb_from[1], ryb_to[1], coef);
                    double b = method.interpolate(ryb_from[2], ryb_to[2], coef);

                    int[] rgb = ColorSpaceConverter.RYBtoRGB(r, y, b);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (rgb[0]) << 16) | ((int) (rgb[1]) << 8) | (int) (rgb[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_LAB) {
                    double[] from = ColorSpaceConverter.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = method.interpolate(from[0], to[0], coef);
                    double a = method.interpolate(from[1], to[1], coef);
                    double b = method.interpolate(from[2], to[2], coef);

                    int[] res = ColorSpaceConverter.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_XYZ) {
                    double[] from = ColorSpaceConverter.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = method.interpolate(from[0], to[0], coef);
                    double Y = method.interpolate(from[1], to[1], coef);
                    double Z = method.interpolate(from[2], to[2], coef);

                    int[] res = ColorSpaceConverter.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_LCH) {
                    double[] from = ColorSpaceConverter.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = ColorSpaceConverter.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = method.interpolate(from[0], to[0], coef);
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

                    if (d > 180) {
                        from[2] += 360;
                        H = method.interpolate(from[2], to[2], coef) % 360.0;
                    } else {
                        H = method.interpolate(from[2], to[2], coef);
                    }

                    int[] res = ColorSpaceConverter.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = 0xff000000 | ((int) (res[0]) << 16) | ((int) (res[1]) << 8) | (int) (res[2]);
                } else if (color_space == MainWindow.COLOR_SPACE_BEZIER_RGB) {
                    double a = method.getCoef(coef);
                    red = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[1], bezierControlPoints_red[0][i].y, bezierControlPoints_red[1][i].y, c2[1]));
                    green = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[2], bezierControlPoints_green[0][i].y, bezierControlPoints_green[1][i].y, c2[2]));
                    blue = ColorSpaceConverter.clamp((int) evaluateBezier(a, c1[3], bezierControlPoints_blue[0][i].y, bezierControlPoints_blue[1][i].y, c2[3]));

                    palette[(n + k) % palette.length] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                //System.out.print(palette[(n + k) % palette.length] + ", ");
                //System.out.println(red + " " + green + " " + blue);
                //System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");

            }
            //}

            n += c1[0];
        }
        //System.out.println("}");

        if (processing_alg == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS || processing_alg == MainWindow.PROCESSING_HISTOGRAM_LIGHTNESS_LAB) {
            histogramEqualization(palette, processing_alg);
        }

        return palette;

    }

    private static void calculateBezierControlPoints(int[][] colors) {

        Point2D.Double[] knots_red = new Point2D.Double[colors.length + 1];
        Point2D.Double[] knots_green = new Point2D.Double[colors.length + 1];
        Point2D.Double[] knots_blue = new Point2D.Double[colors.length + 1]; // +1 to cycle the palette

        for (int i = 0; i < knots_red.length; i++) {
            knots_red[i] = new Point2D.Double(i, colors[i % colors.length][1]);
            knots_green[i] = new Point2D.Double(i, colors[i % colors.length][2]);
            knots_blue[i] = new Point2D.Double(i, colors[i % colors.length][3]);
        }

        bezierControlPoints_red = BezierSpline.GetCurveControlPoints(knots_red);
        bezierControlPoints_green = BezierSpline.GetCurveControlPoints(knots_green);
        bezierControlPoints_blue = BezierSpline.GetCurveControlPoints(knots_blue);

    }

    private static double evaluateBezier(double t, double p0, double p1, double p2, double p3) {

        return (1 - t) * (1 - t) * (1 - t) * p0 + 3 * (1 - t) * (1 - t) * t * p1 + 3 * (1 - t) * t * t * p2 + t * t * t * p3;

    }

    private static void cycleBrightness(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[2] + number;

            if (alg == MainWindow.PROCESSING_BRIGHTNESS1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            } else if (val > 1) {
                val = 1 - (val - 1);
            } else if (val < 0) {
                val *= -1;
            }

            Color temp = new Color(Color.HSBtoRGB(res[0], res[1], (float) val));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }

    private static void cycleSaturation(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[1] + number;

            if (alg == MainWindow.PROCESSING_SATURATION1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            } else if (val > 1) {
                val = 1 - (val - 1);
            } else if (val < 0) {
                val *= -1;
            }

            Color temp = new Color(Color.HSBtoRGB(res[0], (float) val, res[2]));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }

    private static void cycleHue(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[0] + number;

            if (alg == MainWindow.PROCESSING_HUE1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            } else if (val > 1) {
                val = 1 - (val - 1);
            } else if (val < 0) {
                val *= -1;
            }

            Color temp = new Color(Color.HSBtoRGB((float) val, res[1], res[2]));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }

    private static void cycleHSB(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double valh = res[0] + number;
            double vals = res[1] + number;
            double valb = res[2] + number;

            if (alg == MainWindow.PROCESSING_HSB1) {
                if (valh > 1) {
                    valh -= 1;
                } else if (valh < 0) {
                    valh += 1;
                }
            } else if (valh > 1) {
                valh = 1 - (valh - 1);
            } else if (valh < 0) {
                valh *= -1;
            }

            if (alg == MainWindow.PROCESSING_HSB1) {
                if (vals > 1) {
                    vals -= 1;
                } else if (vals < 0) {
                    vals += 1;
                }
            } else if (vals > 1) {
                vals = 1 - (vals - 1);
            } else if (vals < 0) {
                vals *= -1;
            }

            if (alg == MainWindow.PROCESSING_HSB1) {
                if (valb > 1) {
                    valb -= 1;
                } else if (valb < 0) {
                    valb += 1;
                }
            } else if (valb > 1) {
                valb = 1 - (valb - 1);
            } else if (valb < 0) {
                valb *= -1;
            }

            Color temp = new Color(Color.HSBtoRGB((float) valh, (float) vals, (float) valb));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }

    private static void cycleRYB(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double res[] = ColorSpaceConverter.RGBtoRYB(r, g, b);

            double valr = res[0] + number;
            double valy = res[1] + number;
            double valb = res[2] + number;

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valr > 1) {
                    valr -= 1;
                } else if (valr < 0) {
                    valr += 1;
                }
            } else if (valr > 1) {
                valr = 1 - (valr - 1);
            } else if (valr < 0) {
                valr *= -1;
            }

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valy > 1) {
                    valy -= 1;
                } else if (valy < 0) {
                    valy += 1;
                }
            } else if (valy > 1) {
                valy = 1 - (valy - 1);
            } else if (valy < 0) {
                valy *= -1;
            }

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valb > 1) {
                    valb -= 1;
                } else if (valb < 0) {
                    valb += 1;
                }
            } else if (valb > 1) {
                valb = 1 - (valb - 1);
            } else if (valb < 0) {
                valb *= -1;
            }

            int[] temp = ColorSpaceConverter.RYBtoRGB(valr, valy, valb);

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp[0];
            vec[2] = temp[1];
            vec[3] = temp[2];

            colors[p] = vec;
        }

    }

    private static void cycleRed(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = r + number * 255;

            if (alg == MainWindow.PROCESSING_RED1) {
                if (val > 255) {
                    val -= 255;
                } else if (val < 0) {
                    val += 255;
                }
            } else if (val > 255) {
                val = 255 - (val - 255);
            } else if (val < 0) {
                val *= -1;
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = (int) val;
            vec[2] = g;
            vec[3] = b;

            colors[p] = vec;
        }

    }

    private static void cycleGreen(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = g + number * 255;

            if (alg == MainWindow.PROCESSING_GREEN1) {
                if (val > 255) {
                    val -= 255;
                } else if (val < 0) {
                    val += 255;
                }
            } else if (val > 255) {
                val = 255 - (val - 255);
            } else if (val < 0) {
                val *= -1;
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = r;
            vec[2] = (int) val;
            vec[3] = b;

            colors[p] = vec;
        }

    }

    private static void cycleBlue(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = b + number * 255;

            if (alg == MainWindow.PROCESSING_BLUE1) {
                if (val > 255) {
                    val -= 255;
                } else if (val < 0) {
                    val += 255;
                }
            } else if (val > 255) {
                val = 255 - (val - 255);
            } else if (val < 0) {
                val *= -1;
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = r;
            vec[2] = g;
            vec[3] = (int) val;

            colors[p] = vec;
        }

    }

    private static void cycleRGB(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double valr = r + number * 255;
            double valg = g + number * 255;
            double valb = b + number * 255;

            if (alg == MainWindow.PROCESSING_RGB1) {
                if (valr > 255) {
                    valr -= 255;
                } else if (valr < 0) {
                    valr += 255;
                }
            } else if (valr > 255) {
                valr = 255 - (valr - 255);
            } else if (valr < 0) {
                valr *= -1;
            }

            if (alg == MainWindow.PROCESSING_RGB1) {
                if (valg > 255) {
                    valg -= 255;
                } else if (valg < 0) {
                    valg += 255;
                }
            } else if (valg > 255) {
                valg = 255 - (valg - 255);
            } else if (valg < 0) {
                valg *= -1;
            }

            if (alg == MainWindow.PROCESSING_RGB1) {
                if (valb > 255) {
                    valb -= 255;
                } else if (valb < 0) {
                    valb += 255;
                }
            } else if (valb > 255) {
                valb = 255 - (valb - 255);
            } else if (valb < 0) {
                valb *= -1;
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = (int) valr;
            vec[2] = (int) valg;
            vec[3] = (int) valb;

            colors[p] = vec;
        }

    }

    private static void histogramEqualization(int[] palette, int processing_alg) {

        int hist[] = new int[1025];

        int i;
        double mult, count, percentage, next_percentage;
        int low = 0, high = 0;

        int hist_len = hist.length - 1;

        int r, g, b;

        // Fill the histogram by counting the number of pixels with each value
        for (int p = 0; p < palette.length; p++) {
            r = (palette[p] >> 16) & 0xff;
            g = (palette[p] >> 8) & 0xff;
            b = palette[p] & 0xff;

            if (processing_alg == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS) {
                float res[] = new float[3];
                Color.RGBtoHSB(r, g, b, res);
                hist[(int) (res[2] * hist_len + 0.5)]++;
            } else {
                double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
                hist[(int) (res[0] / 100.0 * hist_len + 0.5)]++;
            }
        }

        count = palette.length;

        // Determine the low input value
        next_percentage = hist[0] / count;
        for (i = 0; i < hist_len; i++) {
            percentage = next_percentage;
            next_percentage += hist[i + 1] / count;
            if (Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //low = i;//i+1; This is a deviation from the way The GIMP does it
                low = i + 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }
        // Determine the high input value
        next_percentage = hist[hist_len] / count;
        for (i = hist_len; i > 0; i--) {
            percentage = next_percentage;
            next_percentage += hist[i - 1] / count;
            if (Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //high = i;//i-1; This is a deviation from the way The GIMP does it
                high = i - 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }

        // Turn the histogram into a look up table to stretch the values
        mult = ((double) hist_len) / (high - low);

        for (i = 0; i < low; i++) {
            hist[i] = 0;
        }

        for (i = hist_len; i > high; i--) {
            hist[i] = hist_len;
        }

        double base = 0.0;

        for (i = low; i <= high; i++) {
            hist[i] = (int) (base + 0.5);
            base += mult;
        }

        // Now apply the changes (stretch the values)
        for (int p = 0; p < palette.length; p++) {
            r = (palette[p] >> 16) & 0xff;
            g = (palette[p] >> 8) & 0xff;
            b = palette[p] & 0xff;

            if (processing_alg == MainWindow.PROCESSING_HISTOGRAM_BRIGHTNESS) {
                float res[] = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                double temp = hist[(int) (res[2] * hist_len + 0.5)] / ((double) hist_len);

                palette[p] = new Color(Color.HSBtoRGB(res[0], res[1], (float) temp)).getRGB();
            } else {
                double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
                double temp = hist[(int) (res[0] / 100.0 * hist_len + 0.5)] / ((double) hist_len);
                int[] col = ColorSpaceConverter.LABtoRGB(temp * 100, res[1], res[2]);
                palette[p] = 0xFF000000 | col[0] << 16 | col[1] << 8 | col[2];
            }
        }

    }
}
