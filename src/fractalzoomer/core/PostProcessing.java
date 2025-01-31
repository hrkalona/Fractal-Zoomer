package fractalzoomer.core;

import fractalzoomer.core.antialiasing.AntialiasingAlgorithm;
import fractalzoomer.core.blending.Blending;
import fractalzoomer.core.interpolation.InterpolationMethod;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.utils.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.QRDecomposition;
import org.apache.commons.math3.linear.RealVector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.CyclicBarrier;

import static fractalzoomer.core.TaskRender.*;

public class PostProcessing {
    private static double pi2 = Math.PI * 0.5;

    private static BufferedImage window_image;

    public static void loadWindowImage(int id) {
        try {
            if (id == 1) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "windows.png"));
            } else if (id == 2) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "windows2.png"));
            } else if (id == 3) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "arch_windows.png"));
            } else if (id == 4) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "arch_windows2.png"));
            } else if (id == 5) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "gothic_windows.png"));
            } else if (id == 6) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "round_windows.png"));
            } else if (id == 7) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "round_windows2.png"));
            } else if (id == 8) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "round_windows3.png"));
            } else if (id == 9) {
                window_image = ImageIO.read(MainWindow.class.getResource("/fractalzoomer/icons/" + "door_windows.png"));
            } else {
                window_image = null;
            }
        } catch (Exception ex) {

        }
    }

    private static CyclicBarrier normalize_find_ranges_sync;
    private static CyclicBarrier normalize_sync;
    private TaskRender tr;
    private int max_iterations;
    private int gradient_offset;
    private double lz = Double.MAX_VALUE;
    private LightSettings ls;
    private SlopeSettings ss;
    private OffsetColoringSettings ofs;
    private EntropyColoringSettings ens;
    private NumericalDistanceEstimatorSettings ndes;
    private RainbowPaletteSettings rps;
    private ContourColoringSettings cns;
    private FakeDistanceEstimationSettings fdes;
    private BumpMapSettings bms;
    private HistogramColoringSettings hss;

    private static double maxIterationEscaped;
    private static double maxIterationNotEscaped;
    private static double minIterationsNotEscaped;
    private static double minIterationsEscaped;

    private static double upperFenceEscaped;
    private static double lowerFenceEscaped;

    private static double upperFenceNotEscaped;
    private static double lowerFenceNotEscaped;
    private static int[] escapedCounts;
    private static int[] notEscapedCounts;
    private static double[] arrayEscaped;
    private static double[] arraynotEscaped;
    private static int totalEscaped;
    private static int totalNotEscaped;
    private static double denominatorEscaped;
    private static double denominatorNotEscaped;

    public static void init(int num_tasks) {
        normalize_find_ranges_sync = new CyclicBarrier(num_tasks);
        normalize_sync = new CyclicBarrier(num_tasks);
    }

    public PostProcessing(TaskRender tr, int max_iterations, int gradient_offset, PostProcessSettings pps) {
        this.tr = tr;
        this.max_iterations = max_iterations;
        this.ls = pps.ls;
        this.ss = pps.ss;
        this.ofs = pps.ofs;
        this.ens = pps.ens;
        this.ndes = pps.ndes;
        this.rps = pps.rps;
        this.cns = pps.cns;
        this.fdes = pps.fdes;
        this.bms = pps.bms;
        this.hss = pps.hss;
        this.gradient_offset = gradient_offset;
    }

    public int[] light(double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, Location location, AntialiasingAlgorithm aa) {

        int k0 = image_width * i + j;

        int kx = k0 + 1;
        int sx = 1;

        if (location == null && j == image_width - 1) {
            kx -= 2;
            sx = -1;
        }

        int ky = k0 + image_width;
        int sy = 1;

        if (location == null && i == image_height - 1) {
            ky -= 2 * image_width;
            sy = -1;
        }

        int[] output = new int[colors.length];

        PixelExtraData dataK0 = null;
        PixelExtraData dataKx = null;
        PixelExtraData dataKy = null;

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            dataKx = tr.getIterData(i, j + 1, kx, data, image_width, image_height, location, aa, true);
            dataKy = tr.getIterData(i + 1, j, ky, data, image_width, image_height, location, aa, true);
        }

        for (int m = 0; m < output.length; m++) {

            double h00, h10, h01;

            if (data != null && output.length > 1) {
                h00 = ColorAlgorithm.transformResultToHeight(dataK0.values[m], max_iterations);
                h10 = ColorAlgorithm.transformResultToHeight(dataKx.values[m], max_iterations);
                h01 = ColorAlgorithm.transformResultToHeight(dataKy.values[m], max_iterations);
            } else {
                h00 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);
                h10 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, kx, image_iterations, image_width, image_height, location, true), max_iterations);
                h01 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, ky, image_iterations, image_width, image_height, location, true), max_iterations);
            }


            h00 = height_transfer(h00);
            h10 = height_transfer(h10);
            h01 = height_transfer(h01);

            double xz = h10 - h00;
            double yz = h01 - h00;

            double nx = -xz * sy;
            double ny = -sx * yz;
            double nz = sx * (double) sy;

            // normalize nx, ny and nz
            double nlen = Math.sqrt(nx * nx + ny * ny + nz * nz);

            nx = nx / nlen;
            ny = ny / nlen;
            nz = nz / nlen;

            double lx = ls.lightVector[0];
            double ly = -ls.lightVector[1];

            if (lz == Double.MAX_VALUE) {
                lz = Math.sqrt(1 - lx * lx - ly * ly);
            }

            // Lambert's law.
            double NdotL = lx * nx + ly * ny + lz * nz;

            double lreflz = nz * NdotL * 2 - lz;

            int wr = 0, wg = 0, wb = 0;

            if (ls.specularReflectionMethod > 0) {
                double lreflx = nx * NdotL * 2 - lx;
                double lrefly = ny * NdotL * 2 - ly;

                double refx = 0.5 * (1 - Math.atan2(lreflx, lreflz) / Math.PI);

                if (refx > 1) {
                    refx--;
                } else if (refx < 0) {
                    refx++;
                }

                double refy;

                if (lrefly >= 1) {
                    refy = 1;
                } else if (lrefly <= -1) {
                    refy = 0;
                } else {
                    refy = 0.5 + Math.asin(lrefly) / Math.PI;
                }

                int indexx = (int) (refx * (window_image.getWidth() - 1) + 0.5);
                int indexy = (int) (refy * (window_image.getHeight() - 1) + 0.5);

                int window_color = window_image.getRGB(indexx, indexy);

                wr = (window_color >> 16) & 0xFF;
                wg = (window_color >> 8) & 0xFF;
                wb = window_color & 0xFF;
            }


            double coef = 0;

            // if lumen is negative it is behind,
            // but I tweak it a bit for the sake of the looks:
            // NdotL = -1 (which is super-behind) ==> 0
            // NdotL = 0 ==> ambientlight
            // NdotL = 1 ==> lightintensity
            // for a mathematically correct look use the following:
            // if NdotL < 0 then NdotL = 0;
            // color.a = color.a * (ambientlight + lightintensity lumen);
            if (ls.lightMode == 0) {
                double d = ls.lightintensity / 2;
                coef = (((d - ls.ambientlight) * NdotL + d) * NdotL + ls.ambientlight);
            } else if (ls.lightMode == 1) {
                coef = Math.max(0, (ls.ambientlight + ls.lightintensity * NdotL));
            } else if (ls.lightMode == 2) {
                coef = (ls.ambientlight + ls.lightintensity * NdotL);
            }

            // Next, specular reflection. Viewer is always assumed to be in direction (0,0,1)
            // r = 2 n l - l; v = 0:0:1
            double spec_refl = Math.max(0, lreflz);

            double coef2;
            if (ls.specularReflectionMethod > 0) {
                if (wr < 40 && wg < 40 && wb < 40) {
                    coef2 = ls.specularintensity / 3.6 * Math.pow(spec_refl, ls.shininess);

                } else {
                    coef2 = ls.specularintensity * Math.pow(spec_refl, ls.shininess);
                }
            } else {
                coef2 = ls.specularintensity * Math.pow(spec_refl, ls.shininess);
            }

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            output[m] = tr.applyContour(ls.colorMode, r, g, b, coef, coef2, ls.light_blending);
        }

        return output;

    }

    public int[] postProcessingSmoothing(int[] new_colors, double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, double factor, Location location, AntialiasingAlgorithm aa) {

        int k0 = image_width * i + j;
        int kx = k0 + 1;
        int sx = 1;

        int kx2 = k0 - 1;
        int sx2 = -1;

        int ky = k0 + image_width;
        int sy = 1;

        int ky2 = k0 - image_width;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        int[] output = new int[colors.length];

        InterpolationMethod method = tr.getInterpolationMethod();

        PixelExtraData dataK0 = null;
        PixelExtraData dataKx = null;
        PixelExtraData dataKy = null;
        PixelExtraData dataKx2 = null;
        PixelExtraData dataKy2 = null;

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            dataKx = tr.getIterData(i, j + 1, kx, data, image_width, image_height, location, aa, false);
            dataKy = tr.getIterData(i + 1, j, ky, data, image_width, image_height, location, aa, false);
            dataKx2 = tr.getIterData(i, j - 1, kx2, data, image_width, image_height, location, aa, false);
            dataKy2 = tr.getIterData(i - 1, j, ky2, data, image_width, image_height, location, aa, false);
        }

        for (int m = 0; m < output.length; m++) {

            double n0;

            if (data != null && output.length > 1) {
                n0 = ColorAlgorithm.transformResultToHeight(dataK0.values[m], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(dataKx.values[m], max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(dataKy.values[m], max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(dataKx2.values[m], max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(dataKy2.values[m], max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            } else {
                n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, kx, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, ky, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j - 1, kx2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j, ky2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            }

            double zz = 1 / factor;

            double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

            zz /= z;

            double coef = zz;
            coef = 1 - coef;

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int fc_red = (new_colors[m] >> 16) & 0xFF;
            int fc_green = (new_colors[m] >> 8) & 0xFF;
            int fc_blue = new_colors[m] & 0xFF;

            output[m] = method.interpolateColors(fc_red, fc_green, fc_blue, r, g, b, coef, true);
        }
        return output;

    }

    public int[] slopes(double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, Location location, AntialiasingAlgorithm aa) {

        int k0 = image_width * i + j;

        int kx = k0 + 1;

        if (location == null && j == image_width - 1) {
            kx -= 2;
        }

        int ky = k0 + image_width;

        if (location == null && i == image_height - 1) {
            ky -= 2 * image_width;
        }

        int[] output = new int[colors.length];

        Blending blending = tr.getBlending();

        PixelExtraData dataK0 = null;
        PixelExtraData dataKx = null;
        PixelExtraData dataKy = null;

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            dataKx = tr.getIterData(i, j + 1, kx, data, image_width, image_height, location, aa, true);
            dataKy = tr.getIterData(i + 1, j, ky, data, image_width, image_height, location, aa, true);
        }

        for (int m = 0; m < output.length; m++) {

            double h00, h10, h01;

            if (data != null && output.length > 1) {
                h00 = ColorAlgorithm.transformResultToHeight(dataK0.values[m], max_iterations);
                h10 = ColorAlgorithm.transformResultToHeight(dataKx.values[m], max_iterations);
                h01 = ColorAlgorithm.transformResultToHeight(dataKy.values[m], max_iterations);
            } else {
                h00 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);
                h10 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, kx, image_iterations, image_width, image_height, location, true), max_iterations);
                h01 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, ky, image_iterations, image_width, image_height, location, true), max_iterations);
            }


            h00 = height_transfer_slopes(h00);
            h10 = height_transfer_slopes(h10);
            h01 = height_transfer_slopes(h01);

            double diffx = h10 - h00;
            double diffy = h01 - h00;

            double lx = ss.lightVector[0];
            double ly = -ss.lightVector[1];


            double diff = diffx * lx + diffy * ly;
            diff *= ss.SlopePower;

            if (ss.applyWidthScaling) {
                diff *= image_width / 640.0;
            }

            boolean diffWasPositive = true;
            if (diff >= 0) {
                diff = Math.atan(diff) * pi2;
                diff = diff * ss.SlopeRatio;
                diffWasPositive = true;

                diff = ColorSpaceConverter.clamp(diff);
            } else {
                diffWasPositive = false;
                diff = -diff;
                diff = Math.atan(diff) * pi2;
                diff = diff * ss.SlopeRatio;

                diff = ColorSpaceConverter.clamp(diff);

            }

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            if (ss.colorMode == 0) { //Lab
                r = ColorCorrection.gammaToLinear(r);
                g = ColorCorrection.gammaToLinear(g);
                b = ColorCorrection.gammaToLinear(b);
                double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
                double val = diffWasPositive ? (1 - diff) * res[0] : (1 - diff) * res[0] + diff * 100;
                int[] rgb = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);
                output[m] = ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
            } else if (ss.colorMode == 1) { //HSB
                r = ColorCorrection.gammaToLinear(r);
                g = ColorCorrection.gammaToLinear(g);
                b = ColorCorrection.gammaToLinear(b);
                double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);

                double val = diffWasPositive ? (1 - diff) * res[2] : (1 - diff) * res[2] + diff;

                if (val > 1) {
                    val = 1;
                }
                if (val < 0) {
                    val = 0;
                }

                int[] rgb = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
                output[m] = ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
            } else if (ss.colorMode == 2) { //HSL
                r = ColorCorrection.gammaToLinear(r);
                g = ColorCorrection.gammaToLinear(g);
                b = ColorCorrection.gammaToLinear(b);
                double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);

                double val = diffWasPositive ? (1 - diff) * res[2] : (1 - diff) * res[2] + diff;

                if (val > 1) {
                    val = 1;
                }
                if (val < 0) {
                    val = 0;
                }

                int[] rgb = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
                output[m] = ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
            } else if (ss.colorMode == 3) { //Blending

                double val = diffWasPositive ? (1 - diff) : (1 - diff) + diff;

                int index = (int) ((1 - val) * (gradient.length - 1) + 0.5);
                index = gradient.length - 1 - index;

                int grad_color = getGradientColor(index + gradient_offset);

                int temp_red = (grad_color >> 16) & 0xff;
                int temp_green = (grad_color >> 8) & 0xff;
                int temp_blue = grad_color & 0xff;

                output[m] = blending.blend(temp_red, temp_green, temp_blue, r, g, b, 1 - ss.slope_blending);
            } else if (ss.colorMode == 4) { //scaling
                r = ColorCorrection.gammaToLinear(r);
                g = ColorCorrection.gammaToLinear(g);
                b = ColorCorrection.gammaToLinear(b);
                if (diffWasPositive) {
                    r = (int) ((1 - diff) * r + 0.5);
                    g = (int) ((1 - diff) * g + 0.5);
                    b = (int) ((1 - diff) * b + 0.5);
                } else {
                    r = (int) ((1 - diff) * r + diff * 255 + 0.5);
                    g = (int) ((1 - diff) * g + diff * 255 + 0.5);
                    b = (int) ((1 - diff) * b + diff * 255 + 0.5);
                }

                if (r > 255) {
                    r = 255;
                }
                if (g > 255) {
                    g = 255;
                }
                if (b > 255) {
                    b = 255;
                }

                if (r < 0) {
                    r = 0;
                }
                if (g < 0) {
                    g = 0;
                }
                if (b < 0) {
                    b = 0;
                }

                output[m] = ColorCorrection.linearToGamma(r, g, b);
            } else {
                r = ColorCorrection.gammaToLinear(r);
                g = ColorCorrection.gammaToLinear(g);
                b = ColorCorrection.gammaToLinear(b);
                double[] res = ColorSpaceConverter.RGBtoOKLAB(r, g, b);
                double val = diffWasPositive ? (1 - diff) * res[0] : (1 - diff) * res[0] + diff;
                int[] rgb = ColorSpaceConverter.OKLABtoRGB(val, res[1], res[2]);
                output[m] = ColorCorrection.linearToGamma(rgb[0], rgb[1], rgb[2]);
            }

        }

        return output;

    }

    public int[] offsetColoring(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, boolean[] escaped) {

        int loc = i * image_width + j;

        int[] output = new int[colors.length];

        Blending ofs_blending = tr.getOFSBlending();

        for (int m = 0; m < output.length; m++) {

            int color2;
            if (data != null && output.length > 1) {
                if (tr.skipTrapPostProcessing(data[loc].values[m])) {
                    output[m] = tr.getStandardColor(data[loc].values[m], data[loc].escaped[m]);
                    continue;
                }

                double res = Math.abs(ColorAlgorithm.transformResultToHeight(data[loc].values[m], max_iterations));

                color2 = tr.getStandardColor(res + ofs.post_process_offset, data[loc].escaped[m]);
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[loc])) {
                    output[m] = tr.getStandardColor(image_iterations[loc], escaped[loc]);
                    continue;
                }

                double res = Math.abs(ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations));

                color2 = tr.getStandardColor(res + ofs.post_process_offset, escaped[loc]);
            }


            double coef = 1 - ofs.of_blending;

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int fc_red = (color2 >> 16) & 0xFF;
            int fc_green = (color2 >> 8) & 0xFF;
            int fc_blue = color2 & 0xFF;

            output[m] = ofs_blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);
        }
        return output;
    }

    public int[] entropyColoring(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, boolean[] escaped, Location location, AntialiasingAlgorithm aa) {

        int loc = i * image_width + j;


        int[] output = new int[colors.length];

        Blending ens_blending = tr.getENSBlending();

        for (int m = 0; m < output.length; m++) {

            if (data != null && output.length > 1) {
                if (tr.skipTrapPostProcessing(data[loc].values[m])) {
                    output[m] = tr.getStandardColor(data[loc].values[m], data[loc].escaped[m]);
                    continue;
                }
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[loc])) {
                    output[m] = tr.getStandardColor(image_iterations[loc], escaped[loc]);
                    continue;
                }
            }

            int kernel_size = 3;
            int kernel_size2 = kernel_size / 2;

            double min_value = Double.MAX_VALUE;

            double[] values = new double[kernel_size * kernel_size];
            int length = 0;

            PixelExtraData[] tempData = null;

            if (data != null && output.length > 1) {
                tempData = new PixelExtraData[values.length];
            }

            int kernel_loc = 0;
            for (int k = i - kernel_size2, p = 0; p < kernel_size; k++, p++) {
                for (int l = j - kernel_size2, t = 0; t < kernel_size; l++, t++, kernel_loc++) {

                    if (location != null || (k >= 0 && k < image_height && l >= 0 && l < image_width)) {

                        double temp;

                        if (data != null && output.length > 1) {
                            if (tempData[kernel_loc] == null) {
                                tempData[kernel_loc] = tr.getIterData(k, l, k * image_width + l, data, image_width, image_height, location, aa, false);
                            }
                            temp = Math.abs(ColorAlgorithm.transformResultToHeight(tempData[kernel_loc].values[m], max_iterations));
                        } else {
                            temp = Math.abs(ColorAlgorithm.transformResultToHeight(tr.getIterData(k, l, k * image_width + l, image_iterations, image_width, image_height, location, false), max_iterations));
                        }

                        values[p * kernel_size + t] = temp;

                        if (temp < min_value) {
                            min_value = temp;
                        }
                        length++;

                    }

                }
            }

            double sum = 0;
            for (int k = 0; k < length; k++) {
                values[k] -= min_value;
                sum += values[k];
            }

            double sum2 = 0;
            if (sum != 0) {
                for (int k = 0; k < length; k++) {
                    values[k] /= sum;

                    if (values[k] > 0) {
                        sum2 += values[k] * Math.log(values[k]);
                    }

                }
            }
            if (length != 0) {
                sum2 /= length;
            }
            sum2 *= 10;

            double coef = 1 - ens.en_blending;

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int temp_red = 0, temp_green = 0, temp_blue = 0;

            if (ens.entropy_algorithm == 0) {

                int color2;
                if (data != null && output.length > 1) {
                    double res = Math.abs(ColorAlgorithm.transformResultToHeight(data[loc].values[m], max_iterations));
                    color2 = tr.getStandardColor(ens.entropy_offset + res + ens.entropy_palette_factor * Math.abs(sum2), data[loc].escaped[m]);
                } else {
                    double res = Math.abs(ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations));
                    color2 = tr.getStandardColor(ens.entropy_offset + res + ens.entropy_palette_factor * Math.abs(sum2), escaped[loc]);
                }
                temp_red = (color2 >> 16) & 0xFF;
                temp_green = (color2 >> 8) & 0xFF;
                temp_blue = color2 & 0xFF;
            } else {
                double temp = Math.abs(sum2) * ens.entropy_palette_factor;

                if (temp > 1) {
                    temp = (int) temp % 2 == 1 ? 1 - MathUtils.fract(temp) : MathUtils.fract(temp);
                }

                int index = (int) (temp * (gradient.length - 1) + 0.5);
                index = gradient.length - 1 - index;

                int grad_color = getGradientColor(index + gradient_offset);

                temp_red = (grad_color >> 16) & 0xff;
                temp_green = (grad_color >> 8) & 0xff;
                temp_blue = grad_color & 0xff;
            }

            output[m] = ens_blending.blend(r, g, b, temp_red, temp_green, temp_blue, coef);
        }
        return output;
    }

    public int[] numerical_distance_estimator(double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, Location location, AntialiasingAlgorithm aa, boolean[] escaped) {

        int k0 = image_width * i + j;

        int[] output = new int[colors.length];

        PixelExtraData dataK0 = null;

        PixelExtraData[] stencilData = new PixelExtraData[9];

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            stencilData[4] = dataK0;
        }

        Blending ndes_blending = tr.geNDESBlending();

        for (int m = 0; m < output.length; m++) {


            if (data != null && output.length > 1) {

                if (tr.skipTrapPostProcessing(dataK0.values[m])) {
                    output[m] = tr.getStandardColor(dataK0.values[m], dataK0.escaped[m]);
                    continue;
                }
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[k0])) {
                    output[m] = tr.getStandardColor(image_iterations[k0], escaped[k0]);
                    continue;
                }
            }

            double[][][] stencils = create_stencil(i, j, k0, image_width, image_height, image_iterations, location, data, stencilData, data != null && output.length > 1, aa, m);
            double[][] p = stencils[0];
            double[][] px = stencils[1];
            double[][] py = stencils[2];

            double G = 0;

            switch (ndes.differencesMethod) {
                case 6:
                    // gerrit: Laplacian is proportional to g^2: https://fractalforums.org/kalles-fraktaler/15/gaussian-jitter-for-moire-reduction/891/msg5563#msg5563
                    double L = 0;
                    L += 1 * p[0][0];
                    L += 4 * p[0][1];
                    L += 1 * p[0][2];
                    L += 4 * p[1][0];
                    L -= 20 * p[1][1];
                    L += 4 * p[1][2];
                    L += 1 * p[2][0];
                    L += 4 * p[2][1];
                    L += 1 * p[2][2];
                    L /= 6;
                    G = Math.sqrt(Math.abs(L * 1.4426950408889634));
                    G *= 2.8284271247461903;
                    break;
                case 5:
                    double[] result = compute_gradient_3x3(p, px, py);
                    G = hypot1(result[0], result[1]);
                    G *= 2.8284271247461903;
                    break;
                case 4:
                    result = compute_gradient_2x2(p, px, py);
                    G = hypot1(result[0], result[1]);
                    G *= 2.8284271247461903;
                    break;
                case 2:
                    // gerrit's central difference formula
                    double gx = sqr(p[2][1] - p[0][1]) / hypot2(px[2][1] - px[0][1], py[2][1] - py[0][1]);
                    double gy = sqr(p[1][2] - p[1][0]) / hypot2(px[1][2] - px[1][0], py[1][2] - py[1][0]);
                    double g1 = sqr(p[2][2] - p[0][0]) / hypot2(px[2][2] - px[0][0], py[2][2] - py[0][0]);
                    double g2 = sqr(p[0][2] - p[2][0]) / hypot2(px[0][2] - px[2][0], py[0][2] - py[2][0]);
                    G = Math.sqrt(0.5 * (gx + gy + g1 + g2));
                    G *= 2.8284271247461903;
                    break;
                case 1:
                    // forward differencing in 8 directions from the target point
                    double gx0 = sqr(p[0][1] - p[1][1]) / hypot2(px[0][1] - px[1][1], py[0][1] - py[1][1]);
                    double gx2 = sqr(p[2][1] - p[1][1]) / hypot2(px[2][1] - px[1][1], py[2][1] - py[1][1]);
                    double gy0 = sqr(p[1][0] - p[1][1]) / hypot2(px[1][0] - px[1][1], py[1][0] - py[1][1]);
                    double gy2 = sqr(p[1][2] - p[1][1]) / hypot2(px[1][2] - px[1][1], py[1][2] - py[1][1]);
                    double gu0 = sqr(p[0][0] - p[1][1]) / hypot2(px[0][0] - px[1][1], py[0][0] - py[1][1]);
                    double gu2 = sqr(p[2][2] - p[1][1]) / hypot2(px[2][2] - px[1][1], py[2][2] - py[1][1]);
                    double gv0 = sqr(p[2][0] - p[1][1]) / hypot2(px[2][0] - px[1][1], py[2][0] - py[1][1]);
                    double gv2 = sqr(p[0][2] - p[1][1]) / hypot2(px[0][2] - px[1][1], py[0][2] - py[1][1]);
                    G = Math.sqrt(0.25 * (gx0 + gx2 + gy0 + gy2 + gu0 + gu2 + gv0 + gv2));
                    G *= 2.8284271247461903;
                    break;
                case 3:
                    // forward differencing in 2 diagonals of a 2x2 substencil
//                    if(ndes.useJitter) {
//                        // with displacement correction by gerrit
//                        double nux = px[0][0] - px[1][1];
//                        double nuy = py[0][0] - py[1][1];
//                        double nvx = px[1][0] - px[0][1];
//                        double nvy = py[1][0] - py[0][1];
//                        double nu = hypot1(nux, nuy);
//                        double nv = hypot1(nvx, nvy);
//                        nux /= nu;
//                        nuy /= nu;
//                        nvx /= nv;
//                        nvy /= nv;
//                        double u = (p[0][0] - p[1][1]) / nu;
//                        double v = (p[1][0] - p[0][1]) / nv;
//                        double dotnunv = nux * nvx + nuy * nvy;
//                        double crossnunv = nux * nvy - nuy * nvx;
//                        G = Math.sqrt((u * u + v * v - 2 * u * v * dotnunv) / sqr(crossnunv));
//                        G *= 2.8284271247461903;
//                    }
//                    else {
                    double gu = sqr(p[0][0] - p[1][1]) / hypot2(px[0][0] - px[1][1], py[0][0] - py[1][1]);
                    double gv = sqr(p[0][1] - p[1][0]) / hypot2(px[0][1] - px[1][0], py[0][1] - py[1][0]);
                    G = Math.sqrt(gu + gv);
                    G *= 2.8284271247461903;
                    //}
                    break;
                case 0:
                    // traditional method reverse engineered from original code
                    gx = (p[0][1] - p[1][1]) * 1.41421356237 / Math.hypot(px[0][1] - px[1][1], py[0][1] - py[1][1]);
                    gy = (p[1][0] - p[1][1]) * 1.41421356237 / Math.hypot(px[1][0] - px[1][1], py[1][0] - py[1][1]);
                    gu = (p[0][0] - p[1][1]) * 1.41421356237 / Math.hypot(px[0][0] - px[1][1], py[0][0] - py[1][1]);
                    gv = (p[0][2] - p[1][1]) * 1.41421356237 / Math.hypot(px[0][2] - px[1][1], py[0][2] - py[1][1]);
                    G = Math.abs(gx) + Math.abs(gy) + Math.abs(gu) + Math.abs(gv);
                    break;
//                case 7:
//					/* idea from this paper, hackily extended to arbitrary (jittered) mesh
//							Isotropic finite-differences
//							AnandKumar
//							https://doi.org/10.1016/j.jcp.2004.05.005
//					*/
//                    gx0 =     (p[2][0] - p[0][0]) / hypot1(px[2][0] - px[0][0], py[2][0] - py[0][0]);
//                    double gx1 = 4 * (p[2][1] - p[0][1]) / hypot1(px[2][1] - px[0][1], py[2][1] - py[0][1]);
//                    gx2 =     (p[2][2] - p[0][2]) / hypot1(px[2][2] - px[0][2], py[2][2] - py[0][2]);
//                    gy0 =     (p[0][2] - p[0][0]) / hypot1(px[0][2] - px[0][0], py[0][2] - py[0][0]);
//                    double gy1 = 4 * (p[1][2] - p[1][0]) / hypot1(px[1][2] - px[1][0], py[1][2] - py[1][0]);
//                    gy2 =     (p[2][2] - p[2][0]) / hypot1(px[2][2] - px[2][0], py[2][2] - py[2][0]);
//                    gx = (gx0 + gx1 + gx2) / 6;
//                    gy = (gy0 + gy1 + gy2) / 6;
//                    G = hypot1(gx, gy);
//                    break;
            }

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            double finalG = G * ndes.distanceFactor;
            if (ndes.applyWidthScaling) {
                finalG *= image_width / 640.0;
            }

            int temp_color;
            if (data != null && output.length > 1) {

                if (ndes.cap_to_palette_length) {
                    finalG = Math.min(finalG, tr.getPaletteLength(dataK0.escaped[m]) - 1);
                }

                temp_color = tr.getStandardColor(finalG + ndes.distanceOffset, dataK0.escaped[m]);
            } else {

                if (ndes.cap_to_palette_length) {
                    finalG = Math.min(finalG, tr.getPaletteLength(escaped[k0]) - 1);
                }

                temp_color = tr.getStandardColor(finalG + ndes.distanceOffset, escaped[k0]);
            }

            int temp_red = (temp_color >> 16) & 0xFF;
            int temp_green = (temp_color >> 8) & 0xFF;
            int temp_blue = temp_color & 0xFF;

            output[m] = ndes_blending.blend(r, g, b, temp_red, temp_green, temp_blue, 1 - ndes.numerical_blending);

        }

        return output;

    }

    public int[] paletteRainbow(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, boolean[] escaped, Location location, AntialiasingAlgorithm aa) {

        int k0 = image_width * i + j;
        int kx = k0 + 1;
        int sx = 1;

        int kx2 = k0 - 1;
        int sx2 = -1;

        int ky = k0 + image_width;
        int sy = 1;

        int ky2 = k0 - image_width;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        int[] output = new int[colors.length];

        Blending rps_blending = tr.getRPSBlending();

        PixelExtraData dataK0 = null;
        PixelExtraData dataKx = null;
        PixelExtraData dataKy = null;
        PixelExtraData dataKx2 = null;
        PixelExtraData dataKy2 = null;

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            dataKx = tr.getIterData(i, j + 1, kx, data, image_width, image_height, location, aa, false);
            dataKy = tr.getIterData(i + 1, j, ky, data, image_width, image_height, location, aa, false);
            dataKx2 = tr.getIterData(i, j - 1, kx2, data, image_width, image_height, location, aa, false);
            dataKy2 = tr.getIterData(i - 1, j, ky2, data, image_width, image_height, location, aa, false);
        }

        for (int m = 0; m < output.length; m++) {

            double n0;

            if (data != null && output.length > 1) {
                if (tr.skipTrapPostProcessing(dataK0.values[m])) {
                    output[m] = tr.getStandardColor(dataK0.values[m], dataK0.escaped[m]);
                    continue;
                }

                n0 = ColorAlgorithm.transformResultToHeight(dataK0.values[m], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(dataKx.values[m], max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(dataKy.values[m], max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(dataKx2.values[m], max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(dataKy2.values[m], max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[k0])) {
                    output[m] = tr.getStandardColor(image_iterations[k0], escaped[k0]);
                    continue;
                }

                n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, kx, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, ky, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j - 1, kx2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j, ky2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            }

            double zz = 1.0;

            double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);
            //zz /= z;
            zx /= z;
            zy /= z;

            double hue = Math.atan2(zy, zx) / Math.PI * 0.5;

            hue = hue < 0 ? hue + 1 : hue;

            double coef = 1 - rps.rp_blending;

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int temp_red = 0, temp_green = 0, temp_blue = 0;

            if (rps.rainbow_algorithm == 0) {

                int color2;

                if (data != null && output.length > 1) {
                    color2 = tr.getStandardColor(rps.rainbow_offset + hue * tr.getPaletteLength(data[k0].escaped[m]) * rps.rainbow_palette_factor, data[k0].escaped[m]);
                } else {
                    color2 = tr.getStandardColor(rps.rainbow_offset + hue * tr.getPaletteLength(escaped[k0]) * rps.rainbow_palette_factor, escaped[k0]);
                }

                temp_red = (color2 >> 16) & 0xFF;
                temp_green = (color2 >> 8) & 0xFF;
                temp_blue = color2 & 0xFF;
            } else {
                hue *= 2 * rps.rainbow_palette_factor;

                hue = hue % 2.0;

                int index = hue < 1 ? (int) (hue * (gradient.length - 1) + 0.5) : (int) ((1 - (hue - 1)) * (gradient.length - 1) + 0.5);
                index = gradient.length - 1 - index;

                int grad_color = getGradientColor(index + gradient_offset);

                temp_red = (grad_color >> 16) & 0xff;
                temp_green = (grad_color >> 8) & 0xff;
                temp_blue = grad_color & 0xff;
            }

            output[m] = rps_blending.blend(r, g, b, temp_red, temp_green, temp_blue, coef);
        }
        return output;

    }

    public int[] contourColoring(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, boolean[] escaped) {

        int loc = i * image_width + j;

        int[] output = new int[colors.length];

        InterpolationMethod method = tr.getInterpolationMethod();

        for (int m = 0; m < output.length; m++) {

            double res;
            if (data != null && output.length > 1) {
                if (tr.skipTrapPostProcessing(data[loc].values[m])) {
                    output[m] = tr.getStandardColor(data[loc].values[m], data[loc].escaped[m]);
                    continue;
                }

                res = ColorAlgorithm.transformResultToHeight(data[loc].values[m], max_iterations);
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[loc])) {
                    output[m] = tr.getStandardColor(image_iterations[loc], escaped[loc]);
                    continue;
                }

                res = ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations);
            }


            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            res = tr.fractional_transfer(res, cns.fractionalTransfer, cns.fractionalSmoothing, 0, 1);

            if (cns.contour_algorithm == 0) {
                res = MathUtils.fract(res);

                double min_contour = cns.min_contour;
                double max_contour = 1 - min_contour;

                if (res < min_contour || res > max_contour) {
                    double coef = 0;
                    if (res < min_contour) {
                        coef = (res / min_contour) / 2 + 0.5;
                    } else {
                        coef = ((res - max_contour) / min_contour) / 2;
                    }

                    int color1 = tr.getModifiedColor(r, g, b, max_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);
                    int color2 = tr.getModifiedColor(r, g, b, min_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);

                    int temp_red1 = (color1 >> 16) & 0xff;
                    int temp_green1 = (color1 >> 8) & 0xff;
                    int temp_blue1 = color1 & 0xff;

                    int temp_red2 = (color2 >> 16) & 0xff;
                    int temp_green2 = (color2 >> 8) & 0xff;
                    int temp_blue2 = color2 & 0xff;

                    output[m] = method.interpolateColors(temp_red1, temp_green1, temp_blue1, temp_red2, temp_green2, temp_blue2, coef, true);
                } else {

                    output[m] = tr.getModifiedColor(r, g, b, res, cns.contourColorMethod, 1 - cns.cn_blending, false);
                }
            } else if (cns.contour_algorithm == 1) {

                res = 2.0 * MathUtils.fract(res);

                res = res > 1 ? 2.0 - res : res;

                res = Math.abs(res);

                output[m] = tr.getModifiedColor(r, g, b, res, cns.contourColorMethod, 1 - cns.cn_blending, false);
            } else {
                res = MathUtils.fract(res);

                double min_contour = cns.min_contour;
                double max_contour = 1 - min_contour;


                int color = colors[m];

                //Not Needed anymore
//                if (cns.contour_algorithm == 3) {
//                    if (data != null && output.length > 1) {
//                        color = getStandardColor((int) data[loc].values[m], data[loc].escaped[m]);
//                        r = (color >> 16) & 0xFF;
//                        g = (color >> 8) & 0xFF;
//                        b = color & 0xFF;
//                    } else {
//                        color = getStandardColor((int) image_iterations[loc], escaped[loc]);
//                        r = (color >> 16) & 0xFF;
//                        g = (color >> 8) & 0xFF;
//                        b = color & 0xFF;
//                    }
//                }

                if (res < min_contour || res > max_contour) {
                    double coef = 0;
                    if (res < min_contour) {
                        coef = (res / min_contour) / 2 + 0.5;
                    } else {
                        coef = ((res - max_contour) / min_contour) / 2;
                    }

                    coef = 2.0 * coef;

                    coef = 1 - (coef > 1 ? 2.0 - coef : coef);

                    int color1 = tr.getModifiedColor(r, g, b, min_contour, cns.contourColorMethod, 1 - cns.cn_blending, false);

                    int temp_red1 = (color1 >> 16) & 0xff;
                    int temp_green1 = (color1 >> 8) & 0xff;
                    int temp_blue1 = color1 & 0xff;

                    output[m] = method.interpolateColors(temp_red1, temp_green1, temp_blue1, r, g, b, coef, true);
                } else {
                    output[m] = color;
                }
            }
        }
        return output;

    }

    public int[] greyscaleColoring(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, boolean[] escaped) {

        int loc = i * image_width + j;

        int[] output = new int[colors.length];

        for (int m = 0; m < output.length; m++) {

            if (data != null && output.length > 1) {
                if (tr.skipTrapPostProcessing(data[loc].values[m])) {
                    output[m] = tr.getStandardColor(data[loc].values[m], data[loc].escaped[m]);
                    continue;
                }
            } else {
                if (tr.skipTrapPostProcessing(image_iterations[loc])) {
                    output[m] = tr.getStandardColor(image_iterations[loc], escaped[loc]);
                    continue;
                }
            }

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int greyscale = (int) ((r + g + b) / 3.0 + 0.5);

            output[m] = 0xff000000 | (greyscale << 16) | (greyscale << 8) | greyscale;
        }
        return output;

    }

    public int[] pseudoDistanceEstimation(double[] image_iterations, PixelExtraData[] data, int[] colors, int i, int j, int image_width, int image_height, Location location, AntialiasingAlgorithm aa) {

        int k0 = image_width * i + j;
        int kx = k0 + 1;
        int sx = 1;

        int kx2 = k0 - 1;
        int sx2 = -1;

        int ky = k0 + image_width;
        int sy = 1;

        int ky2 = k0 - image_width;
        int sy2 = -1;

        double zx = 0;
        double zy = 0;
        double zx2 = 0;
        double zy2 = 0;

        int[] output = new int[colors.length];

        InterpolationMethod method = tr.getInterpolationMethod();
        int dem_color = tr.getDemColor();

        PixelExtraData dataK0 = null;
        PixelExtraData dataKx = null;
        PixelExtraData dataKy = null;
        PixelExtraData dataKx2 = null;
        PixelExtraData dataKy2 = null;

        if (data != null && output.length > 1) {
            dataK0 = data[k0];
            dataKx = tr.getIterData(i, j + 1, kx, data, image_width, image_height, location, aa, false);
            dataKy = tr.getIterData(i + 1, j, ky, data, image_width, image_height, location, aa, false);
            dataKx2 = tr.getIterData(i, j - 1, kx2, data, image_width, image_height, location, aa, false);
            dataKy2 = tr.getIterData(i - 1, j, ky2, data, image_width, image_height, location, aa, false);
        }

        for (int m = 0; m < output.length; m++) {

            double n0;

            if (data != null && output.length > 1) {
                n0 = ColorAlgorithm.transformResultToHeight(dataK0.values[m], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(dataKx.values[m], max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(dataKy.values[m], max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(dataKx2.values[m], max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(dataKy2.values[m], max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            } else {
                n0 = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);

                if (location != null || j < image_width - 1) {
                    double nx = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, kx, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx = sx * (nx - n0);
                }

                if (location != null || i < image_height - 1) {
                    double ny = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, ky, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy = sy * (ny - n0);
                }

                if (location != null || j > 0) {
                    double nx2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j - 1, kx2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zx2 = sx2 * (nx2 - n0);
                }

                if (location != null || i > 0) {
                    double ny2 = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j, ky2, image_iterations, image_width, image_height, location, false), max_iterations);
                    zy2 = sy2 * (ny2 - n0);
                }
            }

            double zz = 1 / fdes.fake_de_factor;

            double z = Math.sqrt(zx2 * zx2 + zy2 * zy2 + zx * zx + zy * zy + zz * zz);

            zz /= z;

            double coef = fade(fdes.fade_algorithm, zz);

            int r = (colors[m] >> 16) & 0xFF;
            int g = (colors[m] >> 8) & 0xFF;
            int b = colors[m] & 0xFF;

            int fc_red = (dem_color >> 16) & 0xFF;
            int fc_green = (dem_color >> 8) & 0xFF;
            int fc_blue = dem_color & 0xFF;

            if (fdes.inverse_fake_dem) {
                coef = 1 - coef;
            }

            output[m] = method.interpolateColors(fc_red, fc_green, fc_blue, r, g, b, coef, true);
        }
        return output;

    }

    public int[] bumpMapping(double[] image_iterations, PixelExtraData[] data, int i, int j, int image_width, int image_height, int[] colors, double lightx, double lighty, double sizeCorr, Location location, AntialiasingAlgorithm aa) {

        int index = i * image_width + j;

        double gradx, grady, dotp;

        int[] output = new int[colors.length];

        if (data != null && output.length > 1) {
            tempDataXp1 = null;
            tempDataYp1 = null;
            tempDataYm1 = null;
            tempDataXm1 = null;
        }

        for (int m = 0; m < output.length; m++) {

            if (data != null && output.length > 1) {
                double val = ColorAlgorithm.transformResultToHeight(data[index].values[m], max_iterations);
                val = tr.fractional_transfer(val, bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
                gradx = getGradientX(val, data, i, j, index, m, image_width, image_height, location, aa);
                grady = getGradientY(val, data, i, j, index, m, image_width, image_height, location, aa);
            } else {
                double val = ColorAlgorithm.transformResultToHeight(image_iterations[index], max_iterations);
                val = tr.fractional_transfer(val, bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
                gradx = getGradientX(val, image_iterations, i, j, index, image_width, image_height, location);
                grady = getGradientY(val, image_iterations, i, j, index, image_width, image_height, location);
            }

            dotp = gradx * lightx + grady * lighty;

            if (bms.bumpProcessing == 3 || bms.bumpProcessing == 4 || bms.bumpProcessing == 5 || bms.bumpProcessing == 6) {
                if (dotp != 0) {
                    output[m] = changeBrightnessOfColorLabHsbHsl(colors[m], getCosAngleTimesSmoothGrad(gradx, grady, dotp, sizeCorr));
                } else {
                    output[m] = colors[m];
                }
            } else if (bms.bumpProcessing == 0) {
                if (dotp != 0) {
                    output[m] = changeBrightnessOfColorScaling(colors[m], getCosAngleTimesSmoothGrad(gradx, grady, dotp, sizeCorr));
                } else {
                    output[m] = colors[m];
                }
            } else if (dotp != 0 || (dotp == 0 && !isInt(image_iterations[index]))) {
                output[m] = changeBrightnessOfColorBlending(colors[m], getCosAngleTimesSmoothGrad(gradx, grady, dotp, sizeCorr));
            } else {
                output[m] = colors[m];
            }
        }
        return output;


    }

    public void initializeHistogramColoring(PixelExtraData[] data) throws StopExecutionException {

        int mapping = hss.hmapping;
        int histogramGranularity = hss.histogramBinGranularity;
        boolean banded = tr.getBanded();

        if (WaitOnCondition.WaitOnCyclicBarrier(normalize_find_ranges_sync) == 0) {

            lowerFenceEscaped = -Double.MAX_VALUE;
            upperFenceEscaped = Double.MAX_VALUE;


            lowerFenceNotEscaped = -Double.MAX_VALUE;
            upperFenceNotEscaped = Double.MAX_VALUE;

            if(hss.hs_remove_outliers) {
                //Remove outliers first
                double meanEscaped = 0;
                double meanNotEscaped = 0;
                double varianceEscaped = 0;
                double varianceNotEscaped = 0;
                int samples = 0;
                ArrayList<Double> dataEscaped = null;
                ArrayList<Double> dataNotEscaped = null;

                if(hss.hs_outliers_method == 0) {
                    dataEscaped = new ArrayList<>();
                    dataNotEscaped = new ArrayList<>();
                }

                for(int j = 0; j < data.length; j++) {
                    for (int i = 0; i < data[j].values.length; i++) {

                        double val = data[j].values[i];

                        if (tr.isMaximumIterations(val)) {
                            continue;
                        }

                        if (Double.isNaN(val) || Double.isInfinite(val)) {
                            continue;
                        }

                        val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                        samples++;
                        if (data[j].escaped[i]) {

                            if (hss.hs_outliers_method == 0) {
                                dataEscaped.add(val);
                            } else {
                                double delta = val - meanEscaped;
                                meanEscaped += delta / samples;
                                double delta2 = val - meanEscaped;
                                varianceEscaped += delta * delta2;
                            }

                        } else {

                            if (hss.hs_outliers_method == 0) {
                                dataNotEscaped.add(val);
                            } else {
                                double delta = val - meanNotEscaped;
                                meanNotEscaped += delta / samples;
                                double delta2 = val - meanNotEscaped;
                                varianceNotEscaped += delta * delta2;
                            }
                        }
                    }
                }

                if(hss.hs_outliers_method == 0) {
                    double[] res = getFencesDouble(dataEscaped);
                    lowerFenceEscaped = res[0];
                    upperFenceEscaped = res[1];


                    double[] res2 = getFencesDouble(dataNotEscaped);
                    lowerFenceNotEscaped = res2[0];
                    upperFenceNotEscaped = res2[1];
                }
                else {
                    double sigmaEscaped = Math.sqrt(varianceEscaped / samples);
                    double sigmaNotEscaped = Math.sqrt(varianceNotEscaped / samples);

                    upperFenceEscaped = meanEscaped + 3 * sigmaEscaped;
                    lowerFenceEscaped = meanEscaped - 3 * sigmaEscaped;

                    upperFenceNotEscaped = meanNotEscaped + 3 * sigmaNotEscaped;
                    lowerFenceNotEscaped = meanNotEscaped - 3 * sigmaNotEscaped;
                }

                if(hss.hs_outliers_method == 0) {
                    dataEscaped.clear();
                    dataNotEscaped.clear();
                }
            }

            maxIterationEscaped = -Double.MAX_VALUE;
            maxIterationNotEscaped = -Double.MAX_VALUE;
            totalEscaped = 0;
            totalNotEscaped = 0;
            minIterationsEscaped = Double.MAX_VALUE;
            minIterationsNotEscaped = Double.MAX_VALUE;
            denominatorEscaped = 1;
            denominatorNotEscaped = 1;

            if(mapping != 6) {

                for (int j = 0; j < data.length; j++) {
                    for (int i = 0; i < data[j].values.length; i++) {

                        double val = data[j].values[i];

                        if (tr.isMaximumIterations(val)) {
                            continue;
                        }

                        if (Double.isNaN(val) || Double.isInfinite(val)) {
                            continue;
                        }

                        val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                        if (data[j].escaped[i]) {

                            val = capValue(val, upperFenceEscaped, lowerFenceEscaped, banded);

                            maxIterationEscaped = val > maxIterationEscaped ? val : maxIterationEscaped;
                            minIterationsEscaped = val < minIterationsEscaped ? val : minIterationsEscaped;
                        } else {

                            val = capValue(val, upperFenceNotEscaped, lowerFenceNotEscaped, banded);

                            maxIterationNotEscaped = val > maxIterationNotEscaped ? val : maxIterationNotEscaped;
                            minIterationsNotEscaped = val < minIterationsNotEscaped ? val : minIterationsNotEscaped;
                        }
                    }
                }
            }

            if (mapping == 0 || mapping == 6) {

                if(mapping == 0) {
                    if (maxIterationEscaped != -Double.MAX_VALUE && minIterationsEscaped != Double.MAX_VALUE) {
                        double diff = maxIterationEscaped - minIterationsEscaped;
                        long total = ((long)((diff + 1) * histogramGranularity));
                        total = total > Integer.MAX_VALUE ? Integer.MAX_VALUE : total;
                        escapedCounts = new int[(int)total];
                    }

                    if (maxIterationNotEscaped != -Double.MAX_VALUE && minIterationsNotEscaped != Double.MAX_VALUE) {
                        double diff = maxIterationNotEscaped - minIterationsNotEscaped;
                        long total = ((long)((diff + 1) * histogramGranularity));
                        total = total > Integer.MAX_VALUE ? Integer.MAX_VALUE : total;
                        notEscapedCounts = new int[(int)total];
                    }

                    if (maxIterationEscaped < 1 && minIterationsEscaped < 1) {
                        denominatorEscaped = maxIterationEscaped - minIterationsEscaped + 1e-12;
                    }

                    if (maxIterationNotEscaped < 1 && minIterationsNotEscaped < 1) {
                        denominatorNotEscaped = maxIterationNotEscaped - minIterationsNotEscaped + 1e-12;
                    }
                }

                Set<Double> setEscaped = new HashSet<>();
                Set<Double> setnotEscaped = new HashSet<>();
                ArrayList<Double> listEscaped;
                ArrayList<Double> listnotEscaped;

                for(int j = 0; j < data.length; j++) {
                    int length;
                    if(mapping == 6 && INCLUDE_AA_DATA_ON_RANK_ORDER) {
                        length = data[j].values.length;
                    }
                    else {
                        length = mapping == 6 ? Math.min(1, data[j].values.length) : data[j].values.length;
                    }
                    for (int i = 0; i < length; i++) {
                        double val = data[j].values[i];

                        if (tr.isMaximumIterations(val)) {
                            continue;
                        }

                        if (Double.isNaN(val) || Double.isInfinite(val)) {
                            continue;
                        }

                        val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                        if (data[j].escaped[i]) {
                            val = capValue(val, upperFenceEscaped, lowerFenceEscaped, banded);

                            if(mapping == 6) {
                                setEscaped.add(roundForRankOrder(val));
                            }
                            else {
                                double diff = val - minIterationsEscaped;
                                long id = (long) ((diff) / denominatorEscaped * histogramGranularity);
                                id = id >= escapedCounts.length ? escapedCounts.length - 1 : id;
                                escapedCounts[(int)id]++;
                            }
                            totalEscaped++;
                        } else {
                            val = capValue(val, upperFenceNotEscaped, lowerFenceNotEscaped, banded);
                            if(mapping == 6) {
                                setnotEscaped.add(roundForRankOrder(val));
                            }
                            else {
                                double diff = val - minIterationsNotEscaped;
                                long id = (long) ((diff) / denominatorNotEscaped * histogramGranularity);
                                id = id >= notEscapedCounts.length ? notEscapedCounts.length - 1 : id;
                                notEscapedCounts[(int)id]++;
                            }

                            totalNotEscaped++;
                        }
                    }
                }

                if(mapping == 0) {
                    if (escapedCounts != null) {
                        double sum = 0;
                        for (int i = 0; i < escapedCounts.length; i++) {
                            escapedCounts[i] += sum;
                            sum = escapedCounts[i];
                        }
                    }

                    if (notEscapedCounts != null) {
                        double sum = 0;
                        for (int i = 0; i < notEscapedCounts.length; i++) {
                            notEscapedCounts[i] += sum;
                            sum = notEscapedCounts[i];
                        }
                    }
                }
                else if(mapping == 6) {
                    listEscaped = new ArrayList<>(setEscaped);
                    setEscaped.clear();
                    listnotEscaped = new ArrayList<>(setnotEscaped);
                    setnotEscaped.clear();

                    arrayEscaped = listEscaped.stream().mapToDouble(Double::doubleValue).toArray();
                    arraynotEscaped = listnotEscaped.stream().mapToDouble(Double::doubleValue).toArray();

                    Arrays.parallelSort(arrayEscaped);
                    Arrays.parallelSort(arraynotEscaped);

                    listEscaped.clear();
                    listnotEscaped.clear();
                }
            }
        }

        WaitOnCondition.WaitOnCyclicBarrier(normalize_sync);

    }

    public void initializeHistogramColoring(double[] image_iterations, boolean[] escaped) throws StopExecutionException {

        int mapping = hss.hmapping;
        int histogramGranularity = hss.histogramBinGranularity;
        boolean banded = tr.getBanded();

        if (WaitOnCondition.WaitOnCyclicBarrier(normalize_find_ranges_sync) == 0) {

            lowerFenceEscaped = -Double.MAX_VALUE;
            upperFenceEscaped = Double.MAX_VALUE;


            lowerFenceNotEscaped = -Double.MAX_VALUE;
            upperFenceNotEscaped = Double.MAX_VALUE;

            if(hss.hs_remove_outliers) {
                //Remove outliers first
                double meanEscaped = 0;
                double meanNotEscaped = 0;
                double varianceEscaped = 0;
                double varianceNotEscaped = 0;
                int samples = 0;
                ArrayList<Double> dataEscaped = null;
                ArrayList<Double> dataNotEscaped = null;

                if(hss.hs_outliers_method == 0) {
                    dataEscaped = new ArrayList<>();
                    dataNotEscaped = new ArrayList<>();
                }

                for (int i = 0; i < image_iterations.length; i++) {

                    double val = image_iterations[i];

                    if (tr.isMaximumIterations(val)) {
                        continue;
                    }

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        continue;
                    }

                    val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                    samples++;
                    if (escaped[i]) {

                        if(hss.hs_outliers_method == 0) {
                            dataEscaped.add(val);
                        }
                        else {
                            double delta = val - meanEscaped;
                            meanEscaped += delta / samples;
                            double delta2 = val - meanEscaped;
                            varianceEscaped += delta * delta2;
                        }

                    } else {

                        if(hss.hs_outliers_method == 0) {
                            dataNotEscaped.add(val);
                        }
                        else {
                            double delta = val - meanNotEscaped;
                            meanNotEscaped += delta / samples;
                            double delta2 = val - meanNotEscaped;
                            varianceNotEscaped += delta * delta2;
                        }
                    }
                }

                if(hss.hs_outliers_method == 0) {
                    double[] res = getFencesDouble(dataEscaped);
                    lowerFenceEscaped = res[0];
                    upperFenceEscaped = res[1];


                    double[] res2 = getFencesDouble(dataNotEscaped);
                    lowerFenceNotEscaped = res2[0];
                    upperFenceNotEscaped = res2[1];
                }
                else {
                    double sigmaEscaped = Math.sqrt(varianceEscaped / samples);
                    double sigmaNotEscaped = Math.sqrt(varianceNotEscaped / samples);

                    upperFenceEscaped = meanEscaped + 3 * sigmaEscaped;
                    lowerFenceEscaped = meanEscaped - 3 * sigmaEscaped;

                    upperFenceNotEscaped = meanNotEscaped + 3 * sigmaNotEscaped;
                    lowerFenceNotEscaped = meanNotEscaped - 3 * sigmaNotEscaped;
                }

                if(hss.hs_outliers_method == 0) {
                    dataEscaped.clear();
                    dataNotEscaped.clear();
                }
            }

            maxIterationEscaped = -Double.MAX_VALUE;
            maxIterationNotEscaped = -Double.MAX_VALUE;
            totalEscaped = 0;
            totalNotEscaped = 0;
            minIterationsEscaped = Double.MAX_VALUE;
            minIterationsNotEscaped = Double.MAX_VALUE;
            denominatorEscaped = 1;
            denominatorNotEscaped = 1;

            if(mapping != 6) {
                for (int i = 0; i < image_iterations.length; i++) {

                    double val = image_iterations[i];

                    if (tr.isMaximumIterations(val)) {
                        continue;
                    }

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        continue;
                    }

                    val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                    if (escaped[i]) {

                        val = capValue(val, upperFenceEscaped, lowerFenceEscaped, hss.use_integer_iterations || banded);

                        maxIterationEscaped = val > maxIterationEscaped ? val : maxIterationEscaped;
                        minIterationsEscaped = val < minIterationsEscaped ? val : minIterationsEscaped;
                    } else {

                        val = capValue(val, upperFenceNotEscaped, lowerFenceNotEscaped, hss.use_integer_iterations || banded);

                        maxIterationNotEscaped = val > maxIterationNotEscaped ? val : maxIterationNotEscaped;
                        minIterationsNotEscaped = val < minIterationsNotEscaped ? val : minIterationsNotEscaped;
                    }
                }
            }

            if (mapping == 0 || mapping == 6) {

                if(mapping == 0) {
                    if (maxIterationEscaped != -Double.MAX_VALUE && minIterationsEscaped != Double.MAX_VALUE) {
                        double diff = maxIterationEscaped - minIterationsEscaped;
                        long total = ((long)((diff + 1) * histogramGranularity));
                        total = total > Integer.MAX_VALUE ? Integer.MAX_VALUE : total;
                        escapedCounts = new int[(int)total];
                    }

                    if (maxIterationNotEscaped != -Double.MAX_VALUE && minIterationsNotEscaped != Double.MAX_VALUE) {
                        double diff = maxIterationNotEscaped - minIterationsNotEscaped;
                        long total = ((long)((diff + 1) * histogramGranularity));
                        total = total > Integer.MAX_VALUE ? Integer.MAX_VALUE : total;
                        notEscapedCounts = new int[(int)total];
                    }

                    if (maxIterationEscaped < 1 && minIterationsEscaped < 1) {
                        denominatorEscaped = maxIterationEscaped - minIterationsEscaped + 1e-12;
                    }

                    if (maxIterationNotEscaped < 1 && minIterationsNotEscaped < 1) {
                        denominatorNotEscaped = maxIterationNotEscaped - minIterationsNotEscaped + 1e-12;
                    }
                }

                Set<Double> setEscaped = new HashSet<>();
                Set<Double> setnotEscaped = new HashSet<>();
                ArrayList<Double> listEscaped;
                ArrayList<Double> listnotEscaped;


                for (int i = 0; i < image_iterations.length; i++) {
                    double val = image_iterations[i];

                    if (tr.isMaximumIterations(val)) {
                        continue;
                    }

                    if (Double.isNaN(val) || Double.isInfinite(val)) {
                        continue;
                    }

                    val = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

                    if (escaped[i]) {
                        val = capValue(val, upperFenceEscaped, lowerFenceEscaped, banded);

                        if(mapping == 6) {
                            setEscaped.add(roundForRankOrder(val));
                        }
                        else {
                            double diff = val - minIterationsEscaped;
                            long id = (long) ((diff) / denominatorEscaped * histogramGranularity);
                            id = id >= escapedCounts.length ? escapedCounts.length - 1 : id;
                            escapedCounts[(int)id]++;
                        }
                        totalEscaped++;
                    } else {

                        val = capValue(val, upperFenceNotEscaped, lowerFenceNotEscaped, banded);
                        if(mapping == 6) {
                            setnotEscaped.add(roundForRankOrder(val));
                        }
                        else {
                            double diff = val - minIterationsNotEscaped;
                            long id = (long) ((diff) / denominatorNotEscaped * histogramGranularity);
                            id = id >= notEscapedCounts.length ? notEscapedCounts.length - 1 : id;
                            notEscapedCounts[(int)id]++;
                        }
                        totalNotEscaped++;
                    }
                }

                if(mapping == 0) {
                    if (escapedCounts != null) {
                        double sum = 0;
                        for (int i = 0; i < escapedCounts.length; i++) {
                            escapedCounts[i] += sum;
                            sum = escapedCounts[i];
                        }
                    }

                    if (notEscapedCounts != null) {
                        double sum = 0;
                        for (int i = 0; i < notEscapedCounts.length; i++) {
                            notEscapedCounts[i] += sum;
                            sum = notEscapedCounts[i];
                        }
                    }
                }
                else if(mapping == 6) {
                    listEscaped = new ArrayList<>(setEscaped);
                    setEscaped.clear();
                    listnotEscaped = new ArrayList<>(setnotEscaped);
                    setnotEscaped.clear();

                    arrayEscaped = listEscaped.stream().mapToDouble(Double::doubleValue).toArray();
                    arraynotEscaped = listnotEscaped.stream().mapToDouble(Double::doubleValue).toArray();

                    Arrays.parallelSort(arrayEscaped);
                    Arrays.parallelSort(arraynotEscaped);

                    listEscaped.clear();
                    listnotEscaped.clear();
                }
            }
        }

        WaitOnCondition.WaitOnCyclicBarrier(normalize_sync);

    }

    public int[] applyRankOrderMappingToPixel(int index, int[] colors, PixelExtraData[] data, double[] image_iterations, boolean[] escaped) {

        int[] output = new int[colors.length];
        Blending hss_blending = tr.getHSSBlending();
        boolean banded = tr.getBanded();

        for(int j = 0; j < output.length; j++) {

            double val;
            boolean esc;
            if(data != null && output.length > 1) {
                val = data[index].values[j];
                esc = data[index].escaped[j];
            }
            else {
                val = image_iterations[index];
                esc = escaped[index];
            }

            if (Double.isNaN(val) || Double.isInfinite(val) || tr.isMaximumIterations(val)) {
                continue;
            }

            double sign = val >= 0 ? 1 : -1;

            double tempVal = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

            if (esc) {
                tempVal = capValue(tempVal, upperFenceEscaped, lowerFenceEscaped, banded);
            } else {
                tempVal = capValue(tempVal, upperFenceNotEscaped, lowerFenceNotEscaped, banded);
            }

            double originalVal = tempVal;
            tempVal = roundForRankOrder(tempVal);

            double g1;

            if (esc) {
                int i = closestPoint(arrayEscaped, tempVal);

                if(i != -1) {
                    if(arrayEscaped[i] == tempVal) {
                        g1 = (double) i / (arrayEscaped.length - 1);
                    }
                    else if(i + 1 >= arrayEscaped.length) {
                        g1 = 1.0;
                    }
                    else {
                        double in = i + ((originalVal - arrayEscaped[i])/(arrayEscaped[i + 1] - arrayEscaped[i]));
                        g1 = in / (arrayEscaped.length - 1);
                    }
                }
                else {
                    g1 = 0;
                }

            } else {
                int i = closestPoint(arraynotEscaped, tempVal);

                if(i != -1) {
                    if(arraynotEscaped[i] == tempVal) {
                        g1 = (double)i / (arraynotEscaped.length - 1);
                    }
                    else if(i + 1 >= arraynotEscaped.length) {
                        g1 = 1.0;
                    }
                    else {
                        double in = i + ((originalVal - arraynotEscaped[i])/(arraynotEscaped[i + 1] - arraynotEscaped[i]));
                        g1 = in / (arraynotEscaped.length - 1);
                    }
                }
                else {
                    g1 = 0;
                }
            }

            g1 = scaleWithExponent(g1, hss.mapping_exponent);

            g1 = (hss.histogramScaleMax - hss.histogramScaleMin) * g1 + hss.histogramScaleMin;

            val = sign * g1;

            if (Double.isNaN(val) || Double.isInfinite(val)) {
                continue;
            }

            val *= (tr.getPaletteLength(esc) - 1);

            int original_color = colors[j];

            int r = (original_color >> 16) & 0xFF;
            int g = (original_color >> 8) & 0xFF;
            int b = original_color & 0xFF;

            int modified = tr.getStandardColor(val, esc);

            int fc_red = (modified >> 16) & 0xFF;
            int fc_green = (modified >> 8) & 0xFF;
            int fc_blue = modified & 0xFF;

            double coef = 1 - hss.hs_blending;

            output[j] = hss_blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);

        }

        return output;
    }

    public int[] applyHistogramToPixel(int index, int[] colors, PixelExtraData[] data, int histogramGranularity, double histogramDensity, double[] image_iterations, boolean[] escaped) {

        int[] output = new int[colors.length];
        InterpolationMethod method = tr.getInterpolationMethod();
        Blending hss_blending = tr.getHSSBlending();
        boolean banded = tr.getBanded();

        for(int j = 0; j < output.length; j++) {

            double val;
            boolean esc;
            if(data != null && output.length > 1) {
                val = data[index].values[j];
                esc = data[index].escaped[j];
            }
            else {
                val = image_iterations[index];
                esc = escaped[index];
            }


            if (Double.isNaN(val) || Double.isInfinite(val) || tr.isMaximumIterations(val)) {
                continue;
            }

            double sign = val >= 0 ? 1 : -1;

            double tempVal = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));
            double diff = 0;

            long valIndex = 0;
            if (esc) {
                tempVal = capValue(tempVal, upperFenceEscaped, lowerFenceEscaped, banded);
                diff = tempVal - minIterationsEscaped;
                valIndex = (long) ((diff) / denominatorEscaped * histogramGranularity);
            } else {
                tempVal = capValue(tempVal, upperFenceNotEscaped, lowerFenceNotEscaped, banded);
                diff = tempVal - minIterationsNotEscaped;
                valIndex = (long) ((diff) / denominatorNotEscaped * histogramGranularity);
            }
            int[] array = esc ? escapedCounts : notEscapedCounts;


            int valIndexInt = valIndex >= array.length ? array.length - 1 : (int)valIndex;
            double sum = array[valIndexInt];

            double sumNext = sum;

            //Find the next cdf val that is greater from the old
            for (int i = valIndexInt + 1; i < array.length; i++) {
                if (array[i] > sum) {
                    sumNext = array[i];
                    break;
                }
            }

            double g1, g2;

            if (esc) {
                double cdfMinEscaped = escapedCounts[0];
                g1 = 1.0 - Math.pow(1.0 - ((sum - cdfMinEscaped) / (totalEscaped - cdfMinEscaped)), 1.0 / histogramDensity);
                g2 = 1.0 - Math.pow(1.0 - ((sumNext - cdfMinEscaped) / (totalEscaped - cdfMinEscaped)), 1.0 / histogramDensity);
            } else {
                double cdfMinNotEscaped = notEscapedCounts[0];
                g1 = 1.0 - Math.pow(1.0 - ((sum - cdfMinNotEscaped) / (totalNotEscaped - cdfMinNotEscaped)), 1.0 / histogramDensity);
                g2 = 1.0 - Math.pow(1.0 - ((sumNext - cdfMinNotEscaped) / (totalNotEscaped - cdfMinNotEscaped)), 1.0 / histogramDensity);
            }

            double fractionalPart;

            if (esc) {
                fractionalPart = MathUtils.fract((diff) / denominatorEscaped * histogramGranularity);
            } else {
                fractionalPart = MathUtils.fract((diff) / denominatorNotEscaped * histogramGranularity);
            }

            g1 = method.interpolate(g1, g2, fractionalPart);

            g1 = (hss.histogramScaleMax - hss.histogramScaleMin) * g1 + hss.histogramScaleMin;

            val = sign * g1;

            if (Double.isNaN(val) || Double.isInfinite(val)) {
                continue;
            }

            val *= (tr.getPaletteLength(esc) - 1);

            int original_color = colors[j];
            int r = (original_color >> 16) & 0xFF;
            int g = (original_color >> 8) & 0xFF;
            int b = original_color & 0xFF;

            int modified = tr.getStandardColor(val, esc);

            int fc_red = (modified >> 16) & 0xFF;
            int fc_green = (modified >> 8) & 0xFF;
            int fc_blue = modified & 0xFF;

            double coef = 1 - hss.hs_blending;

            output[j] = hss_blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);
        }

        return output;
    }

    public int[] applyScalingToPixel(int index, int[] colors, PixelExtraData[] data, int mapping, double[] image_iterations, boolean[] escaped) {

        Blending hss_blending = tr.getHSSBlending();
        boolean banded = tr.getBanded();
        int[] output = new int[colors.length];

        for(int j = 0; j < output.length; j++) {
            double val;
            boolean esc;
            if(data != null && output.length > 1) {
                val = data[index].values[j];
                esc = data[index].escaped[j];
            }
            else {
                val = image_iterations[index];
                esc = escaped[index];
            }

            if (Double.isNaN(val) || Double.isInfinite(val) || tr.isMaximumIterations(val)) {
                continue;
            }

            double sign = val >= 0 ? 1 : -1;

            double tempVal = Math.abs(ColorAlgorithm.transformResultToHeight(val, max_iterations));

            if (esc) {
                tempVal = capValue(tempVal, upperFenceEscaped, lowerFenceEscaped, banded);
                switch (mapping) {
                    case 1:
                        val = (tempVal - minIterationsEscaped) / (maxIterationEscaped - minIterationsEscaped);
                        break;
                    case 2:
                        val = (Math.sqrt(tempVal) - Math.sqrt(minIterationsEscaped)) / (Math.sqrt(maxIterationEscaped) - Math.sqrt(minIterationsEscaped));
                        break;
                    case 3:
                        val = (Math.cbrt(tempVal) - Math.cbrt(minIterationsEscaped)) / (Math.cbrt(maxIterationEscaped) - Math.cbrt(minIterationsEscaped));
                        break;
                    case 4:
                        val = (Math.sqrt(Math.sqrt(tempVal)) - Math.sqrt(Math.sqrt(minIterationsEscaped))) / (Math.sqrt(Math.sqrt(maxIterationEscaped)) - Math.sqrt(Math.sqrt(minIterationsEscaped)));
                        break;
                    case 5:
                        val = (Math.log(tempVal) - Math.log(minIterationsEscaped)) / (Math.log(maxIterationEscaped) - Math.log(minIterationsEscaped));
                        break;
                }

            } else {
                tempVal = capValue(tempVal, upperFenceNotEscaped, lowerFenceNotEscaped, banded);
                switch (mapping) {
                    case 1:
                        val = (tempVal - minIterationsNotEscaped) / (maxIterationNotEscaped - minIterationsNotEscaped);
                        break;
                    case 2:
                        val = (Math.sqrt(tempVal) - Math.sqrt(minIterationsNotEscaped)) / (Math.sqrt(maxIterationNotEscaped) - Math.sqrt(minIterationsNotEscaped));
                        break;
                    case 3:
                        val = (Math.cbrt(tempVal) - Math.cbrt(minIterationsNotEscaped)) / (Math.cbrt(maxIterationNotEscaped) - Math.cbrt(minIterationsNotEscaped));
                        break;
                    case 4:
                        val = (Math.sqrt(Math.sqrt(tempVal)) - Math.sqrt(Math.sqrt(minIterationsNotEscaped))) / (Math.sqrt(Math.sqrt(maxIterationNotEscaped)) - Math.sqrt(Math.sqrt(minIterationsNotEscaped)));
                        break;
                    case 5:
                        val = (Math.log(tempVal) - Math.log(minIterationsNotEscaped)) / (Math.log(maxIterationNotEscaped) - Math.log(minIterationsNotEscaped));
                        break;
                }
            }

            val = scaleWithExponent(val, hss.mapping_exponent);
            val = (hss.histogramScaleMax - hss.histogramScaleMin) * val + hss.histogramScaleMin;
            val *= sign;

            val *= (tr.getPaletteLength(esc) - 1);

            int original_color = colors[j];

            int r = (original_color >> 16) & 0xFF;
            int g = (original_color >> 8) & 0xFF;
            int b = original_color & 0xFF;

            int modified = tr.getStandardColor(val, esc);

            int fc_red = (modified >> 16) & 0xFF;
            int fc_green = (modified >> 8) & 0xFF;
            int fc_blue = modified & 0xFF;

            double coef = 1 - hss.hs_blending;

            output[j] = hss_blending.blend(r, g, b, fc_red, fc_green, fc_blue, coef);
        }

        return output;
    }

    private double[] getFencesDouble(ArrayList<Double> data) {

        if(data.isEmpty()) {
            return new double[] {-Double.MAX_VALUE, Double.MAX_VALUE};
        }

        Collections.sort(data);

        //double median = calculateMedianDouble(data, 0, data.size());
        double lower_quartile = calculateMedianDouble(data, 0, data.size() / 2);
        double upper_quartile = calculateMedianDouble(data, (data.size() + 1) / 2, data.size());
        double iqr = upper_quartile - lower_quartile;

        if(iqr == 0) {
            double mean = 0;
            double variance = 0;
            int samples = 0;
            for (int i = 0; i < data.size(); i++) {
                samples++;
                double val = data.get(i);
                double delta =  val - mean;
                mean += delta / samples;
                double delta2 = val - mean;
                variance += delta * delta2;
            }
            double sigma = Math.sqrt(variance / samples);
            double temp = 3 * sigma;
            return  new double[] {mean - temp, mean + temp};
        }
        else {
            double temp = 1.5 * iqr;
            return  new double[] {lower_quartile - temp, upper_quartile + temp};
        }
    }

    private double height_transfer(double value) {

        value = tr.fractional_transfer(value, ls.fractionalTransfer, ls.fractionalSmoothing, ls.fractionalTransferMode, ls.fractionalTransferScale);

        switch (ls.heightTransfer) {
            case 0:
                return value * ls.heightTransferFactor;
            case 1:
                return Math.sqrt(value * ls.heightTransferFactor);
            case 2:
                return value * ls.heightTransferFactor * value * ls.heightTransferFactor;
        }

        return 0;

    }

    private double height_transfer_slopes(double value) {

        value = tr.fractional_transfer(value, ss.fractionalTransfer, ss.fractionalSmoothing, ss.fractionalTransferMode, ss.fractionalTransferScale);

        switch (ss.heightTransfer) {
            case 0:
                return value * ss.heightTransferFactor;
            case 1:
                return Math.sqrt(value * ss.heightTransferFactor);
            case 2:
                return value * ss.heightTransferFactor * value * ss.heightTransferFactor;
        }

        return 0;

    }

    double hypot2(double x, double y) {
        return x * x + y * y;
    }

    double hypot1(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    double sqr(double x) {
        return x * x;
    }

    private double[][][] create_stencil(int i, int j, int k0, int image_width, int image_height, double[] image_iterations, Location location, PixelExtraData[] data, PixelExtraData[] stencilData, boolean useData, AntialiasingAlgorithm aa, int dataIndex) {

        int k_m1_0 = k0 - image_width;
        int k_m1_m1 = k_m1_0 - 1;
        int k_m1_p1 = k_m1_0 + 1;

        int k_0_m1 = k0 - 1;
        int k_0_p1 = k0 + 1;

        int k_p1_0 = k0 + image_width;
        int k_p1_m1 = k_p1_0 - 1;
        int k_p1_p1 = k_p1_0 + 1;

        double ninf = 1.0 / 0.0;
        double[][] p = new double[][]{{ninf, ninf, ninf}, {ninf, ninf, ninf}, {ninf, ninf, ninf}};
        double[][] px = new double[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        double[][] py = new double[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        if (useData) {

            if (stencilData[0] == null) {

                stencilData[0] = tr.getIterData(i - 1, j - 1, k_m1_m1, data, image_width, image_height, location, aa, false);
                stencilData[1] = tr.getIterData(i - 1, j, k_m1_0, data, image_width, image_height, location, aa, false);
                stencilData[2] = tr.getIterData(i - 1, j + 1, k_m1_p1, data, image_width, image_height, location, aa, false);

                stencilData[3] = tr.getIterData(i, j - 1, k_0_m1, data, image_width, image_height, location, aa, false);
                //index 4 was preloaded
                stencilData[5] = tr.getIterData(i, j + 1, k_0_p1, data, image_width, image_height, location, aa, false);

                stencilData[6] = tr.getIterData(i + 1, j - 1, k_p1_m1, data, image_width, image_height, location, aa, false);
                stencilData[7] = tr.getIterData(i + 1, j, k_p1_0, data, image_width, image_height, location, aa, false);
                stencilData[8] = tr.getIterData(i + 1, j + 1, k_p1_p1, data, image_width, image_height, location, aa, false);
            }

            if (stencilData[0] != null) {
                p[0][0] = ColorAlgorithm.transformResultToHeight(stencilData[0].values[dataIndex], max_iterations);
            }
            if (stencilData[1] != null) {
                p[0][1] = ColorAlgorithm.transformResultToHeight(stencilData[1].values[dataIndex], max_iterations);
            }
            if (stencilData[2] != null) {
                p[0][2] = ColorAlgorithm.transformResultToHeight(stencilData[2].values[dataIndex], max_iterations);
            }
            if (stencilData[3] != null) {
                p[1][0] = ColorAlgorithm.transformResultToHeight(stencilData[3].values[dataIndex], max_iterations);
            }
            if (stencilData[4] != null) {
                p[1][1] = ColorAlgorithm.transformResultToHeight(stencilData[4].values[dataIndex], max_iterations);
            }
            if (stencilData[5] != null) {
                p[1][2] = ColorAlgorithm.transformResultToHeight(stencilData[5].values[dataIndex], max_iterations);
            }
            if (stencilData[6] != null) {
                p[2][0] = ColorAlgorithm.transformResultToHeight(stencilData[6].values[dataIndex], max_iterations);
            }
            if (stencilData[7] != null) {
                p[2][1] = ColorAlgorithm.transformResultToHeight(stencilData[7].values[dataIndex], max_iterations);
            }
            if (stencilData[8] != null) {
                p[2][2] = ColorAlgorithm.transformResultToHeight(stencilData[8].values[dataIndex], max_iterations);
            }

        } else {
            p[0][0] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j - 1, k_m1_m1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
            p[0][1] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j, k_m1_0, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
            p[0][2] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j + 1, k_m1_p1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);

            p[1][0] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j - 1, k_0_m1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
            p[1][1] = ColorAlgorithm.transformResultToHeight(image_iterations[k0], max_iterations);
            p[1][2] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, k_0_p1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);

            p[2][0] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j - 1, k_p1_m1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
            p[2][1] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, k_p1_0, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
            p[2][2] = ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j + 1, k_p1_p1, image_iterations, image_width, image_height, location, false, ninf), max_iterations);
        }

        // reflect at boundaries if necessary
        // this will break (result is infinite or NaN) for image size of 1 pixel
        if (p[0][0] != ninf) {
            px[0][0] -= 1;
            py[0][0] -= 1;
        }

        if (p[0][1] != ninf) {
            px[0][1] -= 1;
        }
        if (p[0][2] != ninf) {
            px[0][2] -= 1;
            py[0][2] += 1;
        }
        if (p[1][0] != ninf) {
            py[1][0] -= 1;
        }


        if (p[1][2] != ninf) {
            py[1][2] += 1;
        }
        if (p[2][0] != ninf) {
            px[2][0] += 1;
            py[2][0] -= 1;
        }
        if (p[2][1] != ninf) {
            px[2][1] += 1;
        }
        if (p[2][2] != ninf) {
            px[2][2] += 1;
            py[2][2] += 1;
        }

        p[1][1] *= 2.0;
        px[1][1] *= 2.0;
        py[1][1] *= 2.0;

        if (ninf == p[0][0]) {
            p[0][0] = p[1][1] - p[2][2];
            px[0][0] = px[1][1] - px[2][2];
            py[0][0] = py[1][1] - py[2][2];
        }
        if (ninf == p[0][1]) {
            p[0][1] = p[1][1] - p[2][1];
            px[0][1] = px[1][1] - px[2][1];
            py[0][1] = py[1][1] - py[2][1];
        }
        if (ninf == p[0][2]) {
            p[0][2] = p[1][1] - p[2][0];
            px[0][2] = px[1][1] - px[2][0];
            py[0][2] = py[1][1] - py[2][0];
        }
        if (ninf == p[1][0]) {
            p[1][0] = p[1][1] - p[1][2];
            px[1][0] = px[1][1] - px[1][2];
            py[1][0] = py[1][1] - py[1][2];
        }
        if (ninf == p[1][2]) {
            p[1][2] = p[1][1] - p[1][0];
            px[1][2] = px[1][1] - px[1][0];
            py[1][2] = py[1][1] - py[1][0];
        }
        if (ninf == p[2][0]) {
            p[2][0] = p[1][1] - p[0][2];
            px[2][0] = px[1][1] - px[0][2];
            py[2][0] = py[1][1] - py[0][2];
        }
        if (ninf == p[2][1]) {
            p[2][1] = p[1][1] - p[0][1];
            px[2][1] = px[1][1] - px[0][1];
            py[2][1] = py[1][1] - py[0][1];
        }
        if (ninf == p[2][2]) {
            p[2][2] = p[1][1] - p[0][0];
            px[2][2] = px[1][1] - px[0][0];
            py[2][2] = py[1][1] - py[0][0];
        }
        p[1][1] *= 0.5;
        px[1][1] *= 0.5;
        py[1][1] *= 0.5;

        return new double[][][]{p, px, py};

    }

    private double[] compute_gradient_2x2(double[][] p, double[][] px, double[][] py) {
        // find weighted average of function values
        double num = 0, den = 0;
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j) {
                double qx = px[i][j] + 0.5;
                double qy = py[i][j] + 0.5;
                double w = 1 / (qx * qx + qy * qy + 1e-100);
                num += w * p[i][j];
                den += w;
            }
        double p0 = num / den;
        // initialize system A x = b for solving x = [ dF/dx ; dF/dy ]
        double[][] A = new double[4][2];
        double[] b = new double[4];
        int k = 0;
        for (int i = 0; i < 2; ++i)
            for (int j = 0; j < 2; ++j) {
                A[k][0] = px[i][j] + 0.5;
                A[k][1] = py[i][j] + 0.5;
                b[k] = p[i][j] - p0;
                ++k;
            }
        // least-squares solve overdetermined A x = b via QR decomposition

        QRDecomposition decomposition = new QRDecomposition(new Array2DRowRealMatrix(A));
        RealVector result = decomposition.getSolver().solve(new ArrayRealVector(b));
        return new double[]{result.getEntry(0), result.getEntry(1)};
    }

    private double[] compute_gradient_3x3(double[][] p, double[][] px, double[][] py) {
        double p0 = p[1][1];
        // initialize system A x = b for solving x = [ dF/dx ; dF/dy ]
        double[][] A = new double[8][2];
        double[] b = new double[8];
        int k = 0;
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 3; ++j) {
                if (i == 1 && j == 1) continue;
                A[k][0] = px[i][j] - px[1][1];
                A[k][1] = py[i][j] - py[1][1];
                b[k] = p[i][j] - p0;
                ++k;
            }

        QRDecomposition decomposition = new QRDecomposition(new Array2DRowRealMatrix(A));
        RealVector result = decomposition.getSolver().solve(new ArrayRealVector(b));
        return new double[]{result.getEntry(0), result.getEntry(1)};
    }

    private double getGradientX(double val, double[] image_iterations, int i, int j, int index, int image_width, int image_height, Location location) {

        if (location != null) {
            double diffL = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j - 1, index - 1, image_iterations, image_width, image_height, location, false), max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffR = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tr.getIterData(i, j + 1, index + 1, image_iterations, image_width, image_height, location, false), max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

        if (j == 0) {
            return (tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale) - val) * 2;
        } else if (j == image_width - 1) {
            return (val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale)) * 2;
        } else {
            double diffL = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index - 1], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffR = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index + 1], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientX(double val, PixelExtraData[] data, int i, int j, int index, int subindex, int image_width, int image_height, Location location, AntialiasingAlgorithm aa) {

        if (location != null) {

            if (tempDataXm1 == null) {
                tempDataXm1 = tr.getIterData(i, j - 1, index - 1, data, image_width, image_height, location, aa, false);
            }

            if (tempDataXp1 == null) {
                tempDataXp1 = tr.getIterData(i, j + 1, index + 1, data, image_width, image_height, location, aa, false);
            }


            double diffL = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tempDataXm1.values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffR = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tempDataXp1.values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

        if (j == 0) {
            return (tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index + 1].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale) - val) * 2;
        } else if (j == image_width - 1) {
            return (val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index - 1].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale)) * 2;
        } else {
            double diffL = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index - 1].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffR = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index + 1].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffL * diffR >= 0 ? 0 : diffL - diffR;
        }

    }

    private double getGradientY(double val, double[] image_iterations, int i, int j, int index, int image_width, int image_height, Location location) {

        if (location != null) {
            double diffU = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tr.getIterData(i - 1, j, index - image_width, image_iterations, image_width, image_height, location, false), max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffD = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tr.getIterData(i + 1, j, index + image_width, image_iterations, image_width, image_height, location, false), max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

        if (i == 0) {
            return (val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index + image_width], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale)) * 2;
        } else if (i == image_height - 1) {
            return (tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index - image_width], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale) - val) * 2;
        } else {
            double diffU = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index - image_width], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffD = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(image_iterations[index + image_width], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    PixelExtraData tempDataYm1;
    PixelExtraData tempDataYp1;

    PixelExtraData tempDataXm1;
    PixelExtraData tempDataXp1;

    private double getGradientY(double val, PixelExtraData[] data, int i, int j, int index, int subindex, int image_width, int image_height, Location location, AntialiasingAlgorithm aa) {

        if (location != null) {

            if (tempDataYm1 == null) {
                tempDataYm1 = tr.getIterData(i - 1, j, index - image_width, data, image_width, image_height, location, aa, false);
            }

            if (tempDataYp1 == null) {
                tempDataYp1 = tr.getIterData(i + 1, j, index + image_width, data, image_width, image_height, location, aa, false);
            }


            double diffU = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tempDataYm1.values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffD = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(tempDataYp1.values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

        if (i == 0) {
            return (val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index + image_width].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale)) * 2;
        } else if (i == image_height - 1) {
            return (tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index - image_width].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale) - val) * 2;
        } else {
            double diffU = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index - image_width].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            double diffD = val - tr.fractional_transfer(ColorAlgorithm.transformResultToHeight(data[index + image_width].values[subindex], max_iterations), bms.fractionalTransfer, bms.fractionalSmoothing, bms.fractionalTransferMode, bms.fractionalTransferScale);
            return diffD * diffU >= 0 ? 0 : diffD - diffU;
        }

    }

    private double getBumpCoef(double delta) {
        double mul = 0;

        switch (bms.bump_transfer_function) {
            case 0:
                mul = (1.5 / (Math.abs(delta * bms.bump_transfer_factor) + 1.5));
                break;
            case 1:
                mul = 1 / Math.sqrt(Math.abs(delta * bms.bump_transfer_factor) + 1);
                break;
            case 2:
                mul = 1 / Math.cbrt(Math.abs(delta * bms.bump_transfer_factor) + 1);
                break;
            case 3:
                mul = Math.pow(2, -Math.abs(delta * bms.bump_transfer_factor));
                break;
            //case 4:
            //mul = (Math.atan(-Math.abs(delta * bump_transfer_factor))*0.63662+1);
            //break;
        }

        return mul;
    }

    private int changeBrightnessOfColorLabHsbHsl(int rgb, double delta) {

        double mul = getBumpCoef(delta);

        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        if (delta > 0) {
            mul = (2 - mul) / 2;
        } else {
            mul = mul / 2;
        }

        double contourFactor = tr.getContourFactor();

        if (bms.bumpProcessing == 3) {
            double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
            double val = contourFactor * mul * res[0];
            val = val > 100 ? 100 : val;
            int[] rgb2 = ColorSpaceConverter.LABtoRGB(val, res[1], res[2]);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        } else if (bms.bumpProcessing == 4) {
            double[] res = ColorSpaceConverter.RGBtoHSB(r, g, b);
            double val = contourFactor * mul * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb2 = ColorSpaceConverter.HSBtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        } else if (bms.bumpProcessing == 5) {
            double[] res = ColorSpaceConverter.RGBtoHSL(r, g, b);
            double val = contourFactor * mul * res[2];
            val = val > 1 ? 1 : val;
            int[] rgb2 = ColorSpaceConverter.HSLtoRGB(res[0], res[1], val);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        } else {
            double[] res = ColorSpaceConverter.RGBtoOKLAB(r, g, b);
            double val = contourFactor * mul * res[0];
            val = val > 1 ? 1 : val;
            int[] rgb2 = ColorSpaceConverter.OKLABtoRGB(val, res[1], res[2]);
            return 0xff000000 | (rgb2[0] << 16) | (rgb2[1] << 8) | rgb2[2];
        }
    }

    private int changeBrightnessOfColorScaling(int rgb, double delta) {
        int new_color = 0;

        double mul = getBumpCoef(delta);

        if (delta > 0) {
            rgb ^= 0xFFFFFF;
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            int ret = (int) (r * mul + 0.5) & 0xFF0000 | (int) (g * mul + 0.5) & 0x00FF00 | (int) (b * mul + 0.5);
            new_color = 0xff000000 | (ret ^ 0xFFFFFF);
        } else {
            int r = rgb & 0xFF0000;
            int g = rgb & 0x00FF00;
            int b = rgb & 0x0000FF;
            new_color = 0xff000000 | (int) (r * mul + 0.5) & 0xFF0000 | (int) (g * mul + 0.5) & 0x00FF00 | (int) (b * mul + 0.5);
        }

        return new_color;
    }

    private int changeBrightnessOfColorBlending(int rgbIn, double delta) {

        double mul = getBumpCoef(delta);

        int temp_red = 0;
        int temp_green = 0;
        int temp_blue = 0;

        if (delta > 0) {
            int index = bms.bumpProcessing == 1 ? (int) ((mul / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
        } else {
            int index = bms.bumpProcessing == 1 ? (int) (((2 - mul) / 2) * (gradient.length - 1) + 0.5) : (int) ((1 - mul) * (gradient.length - 1) + 0.5);
            index = gradient.length - 1 - index;

            int grad_color = getGradientColor(index + gradient_offset);

            temp_red = (grad_color >> 16) & 0xff;
            temp_green = (grad_color >> 8) & 0xff;
            temp_blue = grad_color & 0xff;
        }

        int old_red = (rgbIn >> 16) & 0xFF;
        int old_green = (rgbIn >> 8) & 0xFF;
        int old_blue = rgbIn & 0xFF;

        return tr.getBlending().blend(temp_red, temp_green, temp_blue, old_red, old_green, old_blue, 1 - bms.bump_blending);

    }

    private double getCosAngleTimesSmoothGrad(double gradx, double grady, double dotp, double sizeCorr) {
        double gradAbs, cosAngle, smoothGrad;
        gradAbs = Math.sqrt(gradx * gradx + grady * grady);
        cosAngle = dotp / gradAbs;
        smoothGrad = -2.3562 / (gradAbs * sizeCorr + 1.5) + 1.57;
        //smoothGrad = Math.atan(gradAbs * sizeCorr);
        return smoothGrad * cosAngle;
    }

    private boolean isInt(double val) {

        return MathUtils.fract(val) == 0;

    }

    private int closestPoint(double[] array, double target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = (int)(((long)left + right) >>> 1);

            if (array[mid] == target) {
                return mid;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return right;
    }

    private double capValue(double val, double upperFence, double lowerFence, boolean intVals) {
        val = val > upperFence ? upperFence : val;
        val = val < lowerFence ? lowerFence : val;
        return intVals ? (long)val : val;
    }

    private double scaleWithExponent(double v, double exponent) {
        return exponent == 1 ? v : Math.pow(v, exponent);
    }

    private double roundForRankOrder(double val) {
        if(hss.rank_order_digits_grouping == 0) {
            return Math.floor(val + 0.5);
        }
        else if(hss.rank_order_digits_grouping == 1) {
            return Math.floor(10 * val + 0.5) / 10;
        }
        else if(hss.rank_order_digits_grouping == 2) {
            return Math.floor(100 * val + 0.5) / 100;
        }
        else if(hss.rank_order_digits_grouping == 3) {
            return Math.floor(1000 * val + 0.5) / 1000;
        }
        else if(hss.rank_order_digits_grouping == 4) {
            return Math.floor(10000 * val + 0.5) / 10000;
        }
        else if(hss.rank_order_digits_grouping == 5) {
            return Math.floor(100000 * val + 0.5) / 100000;
        }
        else if(hss.rank_order_digits_grouping == 6) {
            return Math.floor(1000000 * val + 0.5) / 1000000;
        }

        return Math.floor(10000000 * val + 0.5) / 10000000;
    }

    private double calculateMedianDouble(ArrayList<Double> values, int start, int end) {
        int length = end - start;
        int middle = start + length / 2;

        if (length % 2 == 0) {
            return (values.get(middle) + values.get(middle - 1)) * 0.5;
        }

        return values.get(middle);
    }

    public static void clear() {
        escapedCounts = null;
        notEscapedCounts = null;
        arrayEscaped = null;
        arraynotEscaped = null;
    }
}