
package fractalzoomer.palettes;

import fractalzoomer.core.interpolation.ColorSpaceInterpolation;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.utils.ColorSpaceConverter;

import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public class CustomPalette extends Palette {

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean smoothing, Color special_color, int color_smoothing_method, boolean special_use_palette_color, int fractional_transfer_method, int smoothing_color_space, boolean color_smoothing) {
        super();

        int[] palette = createPalette(custom_palette, reverse, processing_alg, scale_factor_palette_val, color_interpolation, color_space, 0);

        if (!smoothing) {
            palette_color = new PaletteColorNormal(palette, special_color, special_use_palette_color);
        } else {
            palette_color = new PaletteColorSmooth(palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
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
            ColorSpaceInterpolation.calculateBezierControlPoints(colors);
        }
        else if (color_space == MainWindow.COLOR_SPACE_BASIS_SPLINE_RGB) {
            ColorSpaceInterpolation.calculateBasisSplinePoints(colors);
        }

        InterpolationMethod method = InterpolationMethod.create(color_interpolation);

        int n = length - offset % length;

        for (int i = 0; i < colors.length - 1; i++) { // interpolate first color only
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double coef;
            double step = 1.0 / c1[0];
            for (k = 0, coef = 0; k < c1[0]; coef += step, k++) {
                palette[(n + k) % palette.length] = InterpolationMethod.interpolateColors(color_space, method, coef, i, c1[1], c1[2], c1[3], c2[1], c2[2], c2[3]);
            }
        }

        return palette;
    }

    public static int[] createPalette(int[][] custom_palette, boolean reverse, int processing_alg, double scale_factor_palette_val, int color_interpolation, int color_space, int color_cycling_location) {

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

        if (processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS3) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2 || processing_alg == MainWindow.PROCESSING_HUE3) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2 || processing_alg == MainWindow.PROCESSING_SATURATION3) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2 || processing_alg == MainWindow.PROCESSING_RED3) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2 || processing_alg == MainWindow.PROCESSING_GREEN3) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2 || processing_alg == MainWindow.PROCESSING_BLUE3) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2 || processing_alg == MainWindow.PROCESSING_RGB3) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2 || processing_alg == MainWindow.PROCESSING_HSB3) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        } else if (processing_alg == MainWindow.PROCESSING_RYB1 || processing_alg == MainWindow.PROCESSING_RYB2 || processing_alg == MainWindow.PROCESSING_RYB3) {
            cycleRYB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        if (color_space == MainWindow.COLOR_SPACE_BEZIER_RGB) {
            ColorSpaceInterpolation.calculateBezierControlPoints(colors);
        }
        else if (color_space == MainWindow.COLOR_SPACE_BASIS_SPLINE_RGB) {
            ColorSpaceInterpolation.calculateBasisSplinePoints(colors);
        }

        InterpolationMethod method = InterpolationMethod.create(color_interpolation);

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
            double coef;
            double step = 1.0 / c1[0];
            for (k = 0, coef = 0; k < c1[0]; coef += step, k++) {
                //(c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));


                palette[(n + k) % palette.length] = InterpolationMethod.interpolateColors(color_space, method, coef, i, c1[1], c1[2], c1[3], c2[1], c2[2], c2[3]);
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
    private static void cycleBrightness(int[][] colors, double number, int alg) {

        int r, g, b;

        for (int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float[] res = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[2] + number;

            if (alg == MainWindow.PROCESSING_BRIGHTNESS1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            }
            else if(alg == MainWindow.PROCESSING_BRIGHTNESS2) {
                if (val > 1) {
                    val = 1 - (val - 1);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 1) {
                    val = 1;
                }
                else if (val < 0) {
                    val = 0;
                }
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

            float[] res = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[1] + number;

            if (alg == MainWindow.PROCESSING_SATURATION1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            } else if(alg == MainWindow.PROCESSING_SATURATION2) {
                if (val > 1) {
                    val = 1 - (val - 1);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 1) {
                    val = 1;
                } else if (val < 0) {
                    val = 0;
                }
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

            float[] res = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[0] + number;

            if (alg == MainWindow.PROCESSING_HUE1) {
                if (val > 1) {
                    val -= 1;
                } else if (val < 0) {
                    val += 1;
                }
            } else if(alg == MainWindow.PROCESSING_HUE2) {
                if (val > 1) {
                    val = 1 - (val - 1);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 1) {
                    val = 1;
                } else if (val < 0) {
                    val = 0;
                }
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

            float[] res = new float[3];

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
            } else if (alg == MainWindow.PROCESSING_HSB2) {
                if (valh > 1) {
                    valh = 1 - (valh - 1);
                } else if (valh < 0) {
                    valh *= -1;
                }
            }
            else {
                if (valh > 1) {
                    valh = 1;
                } else if (valh < 0) {
                    valh = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_HSB1) {
                if (vals > 1) {
                    vals -= 1;
                } else if (vals < 0) {
                    vals += 1;
                }
            } else if (alg == MainWindow.PROCESSING_HSB2) {
                if (vals > 1) {
                    vals = 1 - (vals - 1);
                } else if (vals < 0) {
                    vals *= -1;
                }
            }
            else {
                if (vals > 1) {
                    vals = 1;
                } else if (vals < 0) {
                    vals = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_HSB1) {
                if (valb > 1) {
                    valb -= 1;
                } else if (valb < 0) {
                    valb += 1;
                }
            } else if (alg == MainWindow.PROCESSING_HSB2) {
                if (valb > 1) {
                    valb = 1 - (valb - 1);
                } else if (valb < 0) {
                    valb *= -1;
                }
            }
            else {
                if (valb > 1) {
                    valb = 1;
                } else if (valb < 0) {
                    valb = 0;
                }
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

            double[] res = ColorSpaceConverter.RGBtoRYB(r, g, b);

            double valr = res[0] + number;
            double valy = res[1] + number;
            double valb = res[2] + number;

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valr > 1) {
                    valr -= 1;
                } else if (valr < 0) {
                    valr += 1;
                }
            } else if (alg == MainWindow.PROCESSING_RYB2) {
                if (valr > 1) {
                    valr = 1 - (valr - 1);
                } else if (valr < 0) {
                    valr *= -1;
                }
            }
            else {
                if (valr > 1) {
                    valr = 1;
                } else if (valr < 0) {
                    valr = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valy > 1) {
                    valy -= 1;
                } else if (valy < 0) {
                    valy += 1;
                }
            } else if (alg == MainWindow.PROCESSING_RYB2) {
                if (valy > 1) {
                    valy = 1 - (valy - 1);
                } else if (valy < 0) {
                    valy *= -1;
                }
            }
            else {
                if (valy > 1) {
                    valy = 1;
                } else if (valy < 0) {
                    valy = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_RYB1) {
                if (valb > 1) {
                    valb -= 1;
                } else if (valb < 0) {
                    valb += 1;
                }
            } else if (alg == MainWindow.PROCESSING_RYB2) {
                if (valb > 1) {
                    valb = 1 - (valb - 1);
                } else if (valb < 0) {
                    valb *= -1;
                }
            }
            else {
                if (valb > 1) {
                    valb = 1;
                } else if (valb < 0) {
                    valb  = 0;
                }
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
            } else if (alg == MainWindow.PROCESSING_RED2) {
                if (val > 255) {
                    val = 255 - (val - 255);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 255) {
                    val = 255;
                } else if (val < 0) {
                    val = 0;
                }
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
            } else if (alg == MainWindow.PROCESSING_GREEN2) {
                if (val > 255) {
                    val = 255 - (val - 255);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 255) {
                    val = 255;
                } else if (val < 0) {
                    val = 0;
                }
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
            } else if (alg == MainWindow.PROCESSING_BLUE2) {
                if (val > 255) {
                    val = 255 - (val - 255);
                } else if (val < 0) {
                    val *= -1;
                }
            }
            else {
                if (val > 255) {
                    val = 255;
                } else if (val < 0) {
                    val = 0;
                }
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
            } else if (alg == MainWindow.PROCESSING_RGB2) {
                if (valr > 255) {
                    valr = 255 - (valr - 255);
                } else if (valr < 0) {
                    valr *= -1;
                }
            }
            else {
                if (valr > 255) {
                    valr = 255;
                } else if (valr < 0) {
                    valr = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_RGB1) {
                if (valg > 255) {
                    valg -= 255;
                } else if (valg < 0) {
                    valg += 255;
                }
            } else if (alg == MainWindow.PROCESSING_RGB2) {
                if (valg > 255) {
                    valg = 255 - (valg - 255);
                } else if (valg < 0) {
                    valg *= -1;
                }
            }
            else {
                if (valg > 255) {
                    valg = 255;
                } else if (valg < 0) {
                    valg = 0;
                }
            }

            if (alg == MainWindow.PROCESSING_RGB1) {
                if (valb > 255) {
                    valb -= 255;
                } else if (valb < 0) {
                    valb += 255;
                }
            } else if (alg == MainWindow.PROCESSING_RGB2) {
                if (valb > 255) {
                    valb = 255 - (valb - 255);
                } else if (valb < 0) {
                    valb *= -1;
                }
            }
            else {
                if (valb > 255) {
                    valb = 255;
                } else if (valb < 0) {
                    valb = 0;
                }
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

        int[] hist = new int[1025];

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
                float[] res = new float[3];
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
                float[] res = new float[3];

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
