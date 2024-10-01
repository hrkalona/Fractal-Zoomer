
package fractalzoomer.core;

import fractalzoomer.filters_utils.image.GrayFilter;
import fractalzoomer.filters_utils.image.*;
import fractalzoomer.filters_utils.image.LightFilter.Light;
import fractalzoomer.filters_utils.image.LightFilter.Material;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.QuadTree;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.util.stream.IntStream;

/**
 *
 * @author hrkalona
 */
public class ImageFilters {

    private static final float[] thick_edges = {-1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -2.0f, 32.0f, -2.0f, -1.0f, -1.0f, -2.0f, -2.0f, -2.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    private static final float[] thin_edges = {-1.0f, -1.0f, -1.0f, -1.0f, 8.0f, -1.0f, -1.0f, -1.0f, -1.0f};
    private static final float[] sharpness_high = {-0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, 3.4f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f};
    private static final float[] EMBOSS = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, -1.0f};

    private static void filterEmboss(BufferedImage image, int filter_value) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int algorithm = (int) (((int) (((int) (filter_value % 100000.0)) % 1000.0)) % 10.0);

        if (algorithm == 2) {
            int kernelWidth = (int) Math.sqrt(EMBOSS.length);
            int kernelHeight = kernelWidth;
            int xOffset = (kernelWidth - 1) / 2;
            int yOffset = xOffset;

            BufferedImage newSource = new BufferedImage(image_width + kernelWidth - 1, image_height + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, xOffset, yOffset, null);

            Kernel kernel = new Kernel(kernelWidth, kernelHeight, EMBOSS);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(newSource, image);

            graphics.dispose();
            graphics = null;
            kernel = null;
            cop = null;
            newSource = null;
        } else if (algorithm == 3) {
            BumpFilter f = new BumpFilter();

            f.filter(image, image);
        } else {
            double direction = (((int) (((int) (((int) (filter_value % 100000.0)) % 1000.0)) / 10.0)) / 80.0 * 360) * Math.PI / 180.0;
            double elevation = (((int) (((int) (filter_value % 100000.0)) / 1000.0)) / 80.0 * 90) * Math.PI / 180.0;
            double bump_height = ((int) (filter_value / 100000.0)) / 80.0;

            EmbossFilter f = new EmbossFilter();

            f.setAzimuth((float) direction);
            f.setElevation((float) elevation);
            f.setBumpHeight((float) bump_height);

            f.setEmboss(algorithm != 0);

            f.filter(image, image);
        }

    }

    private static void filterEdgeDetection(BufferedImage image, int filter_value, Color filter_color) {

        /*float[] EDGES = {1.0f,   1.0f,  1.0f,
         1.0f, -8.0f,  1.0f,
         1.0f,   1.0f,  1.0f};*/

 /*float[] EDGES = {-7.0f,   -7.0f,  -7.0f,
         -7.0f, 56.0f,  -7.0f,
         -7.0f,   -7.0f,  -7.0f};*/

 /*float[] EDGES = {-1.0f, -1.0f, -2.0f, -1.0f, -1.0f,
         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
         -2.0f, -4.0f, 44.0f, -4.0f, -2.0f,
         -1.0f, -2.0f, -4.0f, -2.0f, -1.0f,
         -1.0f, -1.0f, -2.0f, -1.0f, -1.0f};*/
        int sensitivity = (int) (filter_value / 100.0);
        int thickness = (int) (filter_value % 100.0);

        float[] EDGES = null;

        if (thickness == 1) {
            EDGES = thick_edges;
        } else {
            EDGES = thin_edges;
        }

        int kernelWidth = (int) Math.sqrt(EDGES.length);

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        Kernel kernel = new Kernel(kernelWidth, kernelWidth, EDGES);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        BufferedImage newSource = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);
        BufferedImage newSource2 = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);

        System.arraycopy(((DataBufferInt) image.getRaster().getDataBuffer()).getData(), 0, ((DataBufferInt) newSource.getRaster().getDataBuffer()).getData(), 0, image_width * image_height);

        cop.filter(newSource, newSource2);

        int[] raster = ((DataBufferInt) newSource2.getRaster().getDataBuffer()).getData();
        int[] rgbs = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        int condition = image_width * image_height;

        IntStream.range(0, condition)
                .parallel().forEach(p -> {
        //for (int p = 0; p < condition; p++) {

            int r = (raster[p] >> 16) & 0xff;
            int g = (raster[p] >> 8) & 0xff;
            int b = raster[p] & 0xff;
            if (r <= sensitivity && g <= sensitivity && b <= sensitivity) {//(0xff000000 | raster[p]) == black
                rgbs[p] = filter_color.getRGB();
            }
        });

        kernel = null;
        cop = null;
        newSource = null;
        newSource2 = null;

    }

    private static void filterSharpness(BufferedImage image, int filter_value) {

        if(filter_value == 1) {
            int image_width = image.getWidth();
            int image_height = image.getHeight();

            float[] SHARPNESS = sharpness_high;

            int kernelWidth = (int) Math.sqrt(SHARPNESS.length);
            int kernelHeight = kernelWidth;
            int xOffset = (kernelWidth - 1) / 2;
            int yOffset = xOffset;

            BufferedImage newSource = new BufferedImage(image_width + kernelWidth - 1, image_height + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, xOffset, yOffset, null);

            Kernel kernel = new Kernel(kernelWidth, kernelHeight, SHARPNESS);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(newSource, image);

            graphics.dispose();
            graphics = null;
            kernel = null;
            cop = null;
            newSource = null;
        } else if (filter_value == 0) {
            SharpenFilter f = new SharpenFilter();
            f.filter(image, image);
        } else {
            UnsharpFilter f = new UnsharpFilter();
            f.filter(image, image);
        }

    }

    private static void filterBlurring(BufferedImage image, int filter_value, double sigmaR, double sigmaS, int kernel_size) { //OLD antialiasing method (blurring)

        int alg = ((int) (filter_value / 1000.0));

        if (alg >= 0 && alg < 6) {
            /*     float b = 0.05f;
             float a = 1.0f - (8.0f * b);
        
        
             float[] AA = {b, b, b,    // low-pass filter
             b, a, b,
             b, b, b};
        
        
             /* float c = 0.00390625f;
             float b = 0.046875f;
             float a = 1.0f - (16.0f * c + 8.0f * b);
        
             float[] AA = {c, c, c, c, c,    // low-pass filter
             c, b, b, b, c,
             c, b, a, b, c,
             c, b, b, b, c,
             c, c, c, c, c};*/
 /*  float d = 1.0f / 3096.0f;
             float c = 12.0f * d;
             float b = 12.0f * c;
             float a = 1.0f - (24.0f * d + 16.0f * c + 8.0f * b);
        
             float[] AA = {d, d, d, d, d, d, d,    // low-pass filter
             d, c, c, c, c, c, d,
             d, c, b, b, b, c, d,
             d, c, b, a, b, c, d,
             d, c, b, b, b, c, d,
             d, c, c, c, c, c, d,
             d, d, d, d, d, d, d};*/

 /*if(blurring == 1) {
             float[] MOTION_BLUR = {0.2f,  0.0f,  0.0f, 0.0f,  0.0f,
             0.0f,   0.2f,  0.0f, 0.0f,  0.0f,
             0.0f,   0.0f,  0.2f, 0.0f,  0.0f,
             0.0f,   0.0f,  0.0f, 0.2f,  0.0f,
             0.0f,   0.0f,  0.0f, 0.0f,  0.2f};
        
             blur = MOTION_BLUR;
             }*/
            //else {
            float[] blur = null;

            if (alg == 0) {
                float e = 1.0f / 37184.0f;
                float d = 12.0f * e;
                float c = 12.0f * d;
                float b = 12.0f * c;
                float a = 1.0f - (32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

                float[] NORMAL_BLUR = {e, e, e, e, e, e, e, e, e, // low-pass filter
                    e, d, d, d, d, d, d, d, e,
                    e, d, c, c, c, c, c, d, e,
                    e, d, c, b, b, b, c, d, e,
                    e, d, c, b, a, b, c, d, e,
                    e, d, c, b, b, b, c, d, e,
                    e, d, c, c, c, c, c, d, e,
                    e, d, d, d, d, d, d, d, e,
                    e, e, e, e, e, e, e, e, e};

                blur = NORMAL_BLUR;
            } else {
                int radius = alg;

                int kernelSize = (radius - 1) * 2 + 3;
                if(sigmaR == 0) {
                    sigmaR = 0.3* ((kernelSize - 1) * 0.5 - 1) + 0.8;
                }
                blur = createGaussianKernel(kernelSize, (float) sigmaR);
            }

            // }
            /*float h = 1.0f / 64260344.0f;
             float g = 12.0f * h;
             float f = 12.0f * g;
             float e = 12.0f * f;
             float d = 12.0f * e;
             float c = 12.0f * d;
             float b = 12.0f * c;
             float a = 1.0f - (56.0f * h + 48.0f * g + 40.0f * f + 32.0f * e + 24.0f * d + 16.0f * c + 8.0f * b);

             float[] AA  = {h, h, h, h, h, h, h, h, h, h, h, h, h, h, h,    // low-pass filter
             h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
             h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
             h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
             h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
             h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
             h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
             h, g, f, e, d, c, b, a, b, c, d, e, f, g, h,
             h, g, f, e, d, c, b, b, b, c, d, e, f, g, h,
             h, g, f, e, d, c, c, c, c, c, d, e, f, g, h,
             h, g, f, e, d, d, d, d, d, d, d, e, f, g, h,
             h, g, f, e, e, e, e, e, e, e, e, e, f, g, h,
             h, g, f, f, f, f, f, f, f, f, f, f, f, g, h,
             h, g, g, g, g, g, g, g, g, g, g, g, g, g, h,
             h, h, h, h, h, h, h, h, h, h, h, h, h, h, h};*/
            //resize the picture to cover the image edges
            int kernelWidth = (int) Math.sqrt(blur.length);
            int kernelHeight = kernelWidth;
            int xOffset = (kernelWidth - 1) / 2;
            int yOffset = xOffset;

            int image_width = image.getWidth();
            int image_height = image.getHeight();

            BufferedImage newSource = new BufferedImage(image_width + kernelWidth - 1, image_height + kernelHeight - 1, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, xOffset, yOffset, null);

            Kernel kernel = new Kernel(kernelWidth, kernelHeight, blur);
            ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
            cop.filter(newSource, image);

            graphics.dispose();
            graphics = null;
            blur = null;
            kernel = null;
            cop = null;
            newSource = null;
        } else if (alg == 6) {
            MaximumFilter f = new MaximumFilter();

            f.filter(image, image);
        } else if (alg == 7) {
            MedianFilter f = new MedianFilter();

            f.filter(image, image);
        } else if (alg == 8) {
            MinimumFilter f = new MinimumFilter();

            f.filter(image, image);
        } else if (alg == 9) {
            double radius = ((int) (filter_value % 1000.0));

            HighPassFilter f = new HighPassFilter();

            f.setRadius((float) radius);

            f.filter(image, image);
        }
        else if (alg == 10) {
            BlurFilter f = new BlurFilter();
            f.filter(image, image);
        }
        else if (alg == 11) {
            DespeckleFilter f = new DespeckleFilter();
            f.filter(image, image);
        }
        else if (alg == 12) {
            LensBlurFilter f = new LensBlurFilter();
            f.filter(image, image);
        }
        else if (alg == 13) {
            ReduceNoiseFilter f = new ReduceNoiseFilter();
            f.filter(image, image);
        }
        else if (alg == 14) {
            SmartBlurFilter f = new SmartBlurFilter();
            f.filter(image, image);
        }
        else if(alg == 15) {
            GaussianFilter f = new GaussianFilter();
            double radius = ((int) (filter_value % 1000.0));
            f.setRadius((float) radius);
            f.filter(image, image);
        }
        else if(alg == 16) {
            int image_width = image.getWidth();
            int image_height = image.getHeight();
            BufferedImage newSource = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = newSource.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            BilateralFilter f = new BilateralFilter(kernel_size, sigmaR, sigmaS);
            f.filter(newSource, image);
        }
    }

    private static void filterInvertColors(BufferedImage image, int filter_value) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int[] raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        int condition = image_width * image_height;


        IntStream.range(0, condition)
                .parallel().forEach(p -> {
        //for (int p = 0; p < condition; p++) {

            int r, g, b;
            if (filter_value == 0) {
                raster[p] = ~(raster[p] & 0x00ffffff);
            } else if (filter_value == 1) { // Brightness
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float[] res = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(res[0], res[1], 1.0f - res[2]);
            } else if (filter_value == 2) { // Hue
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float[] res = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(1.0f - res[0], res[1], res[2]);
            } else if (filter_value == 3) { // Saturation
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                float[] res = new float[3];

                Color.RGBtoHSB(r, g, b, res);

                raster[p] = Color.HSBtoRGB(res[0], 1.0f - res[1], res[2]);
            } else if (filter_value == 4) { // Red
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                raster[p] = 0xff000000 | (255 - r) << 16 | g << 8 | b;
            } else if (filter_value == 5) { // Green
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                raster[p] = 0xff000000 | r << 16 | (255 - g) << 8 | b;
            } else if (filter_value == 6) { // Blue
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                raster[p] = 0xff000000 | r << 16 | g << 8 | (255 - b);
            }

        });

    }

    private static void filterMaskColors(BufferedImage image, int filter_value) {

        int mask = 0;

        if (filter_value == 0) {
            mask = 0xff00ffff;  //RED
        } else if (filter_value == 1) {
            mask = 0xffff00ff;  //GREEN
        } else {
            mask = 0xffffff00;  //BLUE
        }

        MaskFilter f = new MaskFilter();
        f.setMask(mask);

        f.filter(image, image);

    }

    private static void filterFadeOut(BufferedImage image) {

        GrayFilter f = new GrayFilter();

        f.filter(image, image);

    }

    private static void filterColorChannelSwapping(BufferedImage image, int filter_value) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int[] raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


        int condition = image_width * image_height;

        IntStream.range(0, condition)
                .parallel().forEach(p -> {
        //for (int p = 0; p < condition; p++) {

             int r, g, b;
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if (filter_value == 0) {
                raster[p] = 0xff000000 | (r << 16) | (b << 8) | g;
            } else if (filter_value == 1) {
                raster[p] = 0xff000000 | (g << 16) | (r << 8) | b;
            } else if (filter_value == 2) {
                raster[p] = 0xff000000 | (g << 16) | (b << 8) | r;
            } else if (filter_value == 3) {
                raster[p] = 0xff000000 | (b << 16) | (g << 8) | r;
            } else {
                raster[p] = 0xff000000 | (b << 16) | (r << 8) | g;
            }
        });

    }

    private static void filterContrastBrightness(BufferedImage image, int filter_value) {

        double contrast = (((int) (filter_value / 1000.0)) / 100.0) * 2.0;
        double brightness = (((int) (filter_value % 1000.0)) / 100.0) * 2.0;

        ContrastFilter f = new ContrastFilter();
        f.setContrast((float) contrast);
        f.setBrightness((float) brightness);

        f.filter(image, image);

    }

    private static void filterColorTemperature(BufferedImage image, int filter_value) {

        TemperatureFilter f = new TemperatureFilter();

        f.setTemperature(filter_value);

        f.filter(image, image);
    }

    private static void filterGrayscale(BufferedImage image, int filter_value) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int[] raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();


        int condition = image_width * image_height;

        //for (int p = 0; p < condition; p++) {
        IntStream.range(0, condition)
                .parallel().forEach(p -> {

            int r, g, b, rgb;
            r = (raster[p] >> 16) & 0xff;
            g = (raster[p] >> 8) & 0xff;
            b = raster[p] & 0xff;

            if (filter_value == 0) {
                rgb = (r * 612 + g * 1202 + b * 233) >> 11;	// NTSC luma
                //rgb = (int)(r * 0.299F + g * 0.587F + b * 0.114F);	// NTSC luma
            } else if (filter_value == 1) {
                rgb = (r * 435 + g * 1464 + b * 147) >> 11;	// HDTV luma
                //rgb = (int)(r * 0.2126F + g * 0.7152F + b * 0.0722F);	// HDTV luma
            } else if (filter_value == 2) {
                rgb = (int) ((r + g + b) / 3.0 + 0.5);	// simple average  
            } else if (filter_value == 3) {
                rgb = (int) ((Math.max(Math.max(r, g), b) + Math.min(Math.min(r, g), b)) / 2.0 + 0.5);
            } else if (filter_value == 4) {
                double[] lab = ColorSpaceConverter.RGBtoLAB(r, g, b);
                rgb = (int) ((lab[0] / 100.0) * 255 + 0.5);
            }
            else {
                rgb = (int)(r * 0.2627 + g * 0.6780 + b * 0.0593 + 0.5);	// HDR luma
            }

            raster[p] = 0xff000000 | (rgb << 16) | (rgb << 8) | rgb;
        });

    }

    private static void filterHistogramEqualization(BufferedImage image, int filter_value) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int[] raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        int condition = image_width * image_height;

        /*int r, g, b;
        
         int cdf[] = new int[100001];
        
         for(int p = 0; p < condition; p++) {
         r = (raster[p] >> 16) & 0xff;
         g = (raster[p] >> 8) & 0xff;
         b = raster[p] & 0xff;
        
         float res[] = new float[3];
        
         Color.RGBtoHSB(r, g, b, res);
        
         cdf[(int)(res[2] * 100000 + 0.5)]++;
         }
        
         int min = 0;
         int j = 0;
         while(min == 0) {
         min = cdf[j++];
         }
         int d = condition - min;
        
         for(int i = 1; i < cdf.length; i++) {
         cdf[i] += cdf[i - 1];
         }
        
         for(int p = 0; p < condition; p++) {
         r = (raster[p] >> 16) & 0xff;
         g = (raster[p] >> 8) & 0xff;
         b = raster[p] & 0xff;
        
         float res[] = new float[3];
        
         Color.RGBtoHSB(r, g, b, res);
        
         double temp = ((double)(cdf[(int)(res[2] * 100000 + 0.5)] - min)) / d;
        
         raster[p] = Color.HSBtoRGB(res[0], res[1], (float)(temp < 0 ? 0 : temp));
        
        
         }*/
        if (filter_value == 1) { //gimp levels

            int[][] hist = new int[3][256];

            int i, c;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for (int p = 0; p < condition; p++) {
                hist[0][(raster[p] >> 16) & 0xff]++;
                hist[1][(raster[p] >> 8) & 0xff]++;
                hist[2][raster[p] & 0xff]++;
            }

            count = image_width * image_height;
            // Fore each channel
            for (c = 0; c < hist.length; c++) {
                // Determine the low input value
                next_percentage = hist[c][0] / count;
                for (i = 0; i < 255; i++) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i + 1] / count;
                    if (Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //low = i;//i+1; This is a deviation from the way The GIMP does it
                        low = i + 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }
                // Determine the high input value
                next_percentage = hist[c][255] / count;
                for (i = 255; i > 0; i--) {
                    percentage = next_percentage;
                    next_percentage += hist[c][i - 1] / count;
                    if (Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                        //high = i;//i-1; This is a deviation from the way The GIMP does it
                        high = i - 1;
                        // that prevents any change in the image if it's
                        // already optimal
                        break;
                    }
                }

                // Turn the histogram into a look up table to stretch the values
                mult = 255.0 / (high - low);

                for (i = 0; i < low; i++) {
                    hist[c][i] = 0;
                }

                for (i = 255; i > high; i--) {
                    hist[c][i] = 255;
                }

                double base = 0.0;

                for (i = low; i <= high; i++) {
                    hist[c][i] = (int) (base + 0.5);
                    base += mult;
                }
            }

            // Now apply the changes (stretch the values)


            IntStream.range(0, condition)
                    .parallel().forEach(p -> {
            //for (int p = 0; p < condition; p++) {
                        int r, g, b;
                r = hist[0][(raster[p] >> 16) & 0xff];
                g = hist[1][(raster[p] >> 8) & 0xff];
                b = hist[2][raster[p] & 0xff];

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            });
        } else if (filter_value == 0 || filter_value == 5 || filter_value == 6 || filter_value == 7) { //brightness, hue ,saturation, lightness
            int[] hist = new int[1025];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            int hist_len = hist.length - 1;

            // Fill the histogram by counting the number of pixels with each value
            for (int p = 0; p < condition; p++) {
                int r, g, b;
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                if (filter_value == 0) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    hist[(int) (res[2] * hist_len + 0.5)]++;
                } else if (filter_value == 5) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    hist[(int) (res[0] * hist_len + 0.5)]++;
                } else if (filter_value == 6) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    hist[(int) (res[1] * hist_len + 0.5)]++;
                } else {
                    double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
                    hist[(int) (res[0] / 100.0 * hist_len + 0.5)]++;
                }
            }

            count = image_width * image_height;

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
            IntStream.range(0, condition)
                    .parallel().forEach(p -> {
            //for (int p = 0; p < condition; p++) {
                int r, g, b;
                r = (raster[p] >> 16) & 0xff;
                g = (raster[p] >> 8) & 0xff;
                b = raster[p] & 0xff;

                if (filter_value == 0) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    double temp = hist[(int) (res[2] * hist_len + 0.5)] / ((double) hist_len);
                    raster[p] = Color.HSBtoRGB(res[0], res[1], (float) temp);
                } else if (filter_value == 5) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    double temp = hist[(int) (res[0] * hist_len + 0.5)] / ((double) hist_len);
                    raster[p] = Color.HSBtoRGB((float) temp, res[1], res[2]);
                } else if (filter_value == 6) {
                    float[] res = new float[3];

                    Color.RGBtoHSB(r, g, b, res);
                    double temp = hist[(int) (res[1] * hist_len + 0.5)] / ((double) hist_len);
                    raster[p] = Color.HSBtoRGB(res[0], (float) temp, res[2]);
                } else {
                    double[] res = ColorSpaceConverter.RGBtoLAB(r, g, b);
                    double temp = hist[(int) (res[0] / 100.0 * hist_len + 0.5)] / ((double) hist_len);
                    int[] col = ColorSpaceConverter.LABtoRGB(temp * 100, res[1], res[2]);
                    raster[p] = 0xFF000000 | col[0] << 16 | col[1] << 8 | col[2];
                }
            });

        } else if (filter_value == 2 || filter_value == 3 || filter_value == 4) { //red, green, blue
            int[] hist = new int[256];

            int i;
            double mult, count, percentage, next_percentage;
            int low = 0, high = 0;

            // Fill the histogram by counting the number of pixels with each value
            for (int p = 0; p < condition; p++) {
                if (filter_value == 2) {
                    hist[(raster[p] >> 16) & 0xff]++;
                } else if (filter_value == 3) {
                    hist[(raster[p] >> 8) & 0xff]++;
                } else {
                    hist[raster[p] & 0xff]++;
                }
            }

            count = image_width * image_height;
            // Determine the low input value
            next_percentage = hist[0] / count;
            for (i = 0; i < 255; i++) {
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
            next_percentage = hist[255] / count;
            for (i = 255; i > 0; i--) {
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
            mult = 255.0 / (high - low);

            for (i = 0; i < low; i++) {
                hist[i] = 0;
            }

            for (i = 255; i > high; i--) {
                hist[i] = 255;
            }

            double base = 0.0;

            for (i = low; i <= high; i++) {
                hist[i] = (int) (base + 0.5);
                base += mult;
            }

            // Now apply the changes (stretch the values)


            IntStream.range(0, condition)
                    .parallel().forEach(p -> {
            //for (int p = 0; p < condition; p++) {

                 int r, g, b;
                if (filter_value == 2) {
                    r = hist[(raster[p] >> 16) & 0xff];
                    g = (raster[p] >> 8) & 0xff;
                    b = raster[p] & 0xff;
                } else if (filter_value == 3) {
                    r = (raster[p] >> 16) & 0xff;
                    g = hist[(raster[p] >> 8) & 0xff];
                    b = raster[p] & 0xff;
                } else {
                    r = (raster[p] >> 16) & 0xff;
                    g = (raster[p] >> 8) & 0xff;
                    b = hist[raster[p] & 0xff];
                }

                raster[p] = 0xff000000 | (r << 16) | (g << 8) | b;
            });
        }

    }

    private static void filterColorChannelSwizzling(BufferedImage image, int filter_value) {

        int[] matrix = {
            1, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0,
            0, 0, 0, 0, 0
        };

        for (int j = 0; j < 12; j++) {

            if (((filter_value >> j) & 0x1) == 1) {
                matrix[j + 6 + j / 4] = 1;
            }

        }

        SwizzleFilter f = new SwizzleFilter();
        f.setMatrix(matrix);

        f.filter(image, image);

    }

    private static void filterColorChannelAdjusting(BufferedImage image, int filter_value) {

        double rFactor = 2 * ((int) (filter_value / 1000000.0)) / 100.0;
        double gFactor = 2 * ((int) (((int) (filter_value % 1000000.0)) / 1000)) / 100.0;
        double bFactor = 2 * ((int) (((int) (filter_value % 1000000.0)) % 1000)) / 100.0;

        RGBAdjustFilter f = new RGBAdjustFilter();

        f.setRFactor((float) rFactor - 1);
        f.setGFactor((float) gFactor - 1);
        f.setBFactor((float) bFactor - 1);

        f.filter(image, image);

    }

    private static void filterDither(BufferedImage image, int filter_value) {

        int levels = (int) (((int) (((int) (filter_value % 100000.0)) % 10000.0)) % 1000.0);
        int dither_mat_number = (int) (((int) (((int) (filter_value % 100000.0)) % 10000.0)) / 1000.0);
        int diffusion = (int) (((int) (filter_value % 100000.0)) / 10000.0);
        int serpentine = ((int) (filter_value / 100000.0));

        if (diffusion == 0) {
            DitherFilter f = new DitherFilter();

            switch (dither_mat_number) {
                case 0:
                    f.setMatrix(DitherFilter.ditherMagic2x2Matrix);
                    break;
                case 1:
                    f.setMatrix(DitherFilter.ditherOrdered4x4Matrix);
                    break;
                case 2:
                    f.setMatrix(DitherFilter.ditherOrdered6x6Matrix);
                    break;
                case 3:
                    f.setMatrix(DitherFilter.ditherOrdered8x8Matrix);
                    break;
                case 4:
                    f.setMatrix(DitherFilter.ditherMagic4x4Matrix);
                    break;
                case 5:
                    f.setMatrix(DitherFilter.ditherCluster3Matrix);
                    break;
                case 6:
                    f.setMatrix(DitherFilter.ditherCluster4Matrix);
                    break;
                case 7:
                    f.setMatrix(DitherFilter.ditherCluster8Matrix);
                    break;
                case 8:
                    f.setMatrix(DitherFilter.ditherLines4x4Matrix);
                    break;
                case 9:
                    f.setMatrix(DitherFilter.dither90Halftone6x6Matrix);
                    break;
            }

            f.setLevels(levels);

            f.filter(image, image);
        } else {
            DiffusionFilter f = new DiffusionFilter();

            f.setSerpentine(serpentine == 1);
            f.setLevels(levels);
            f.filter(image, image);
        }

    }

    private static void filterHSBcolorChannelAdjusting(BufferedImage image, int filter_value) {

        double hFactor = 2 * ((((int) (filter_value / 1000000.0)) / 100.0) - 0.5);
        double sFactor = 2 * ((((int) (((int) (filter_value % 1000000.0)) / 1000)) / 100.0) - 0.5);
        double bFactor = 2 * ((((int) (((int) (filter_value % 1000000.0)) % 1000)) / 100.0) - 0.5);

        HSBAdjustFilter f = new HSBAdjustFilter();
        f.setHFactor((float) hFactor);
        f.setSFactor((float) sFactor);
        f.setBFactor((float) bFactor);

        f.filter(image, image);
    }

    private static void filterPosterize(BufferedImage image, int filter_value) {

        int numLevels = filter_value % 1000;
        int mode = filter_value / 1000;

        PosterizeFilter f = new PosterizeFilter();
        f.setNumLevels(numLevels);
        f.setMode(mode);

        f.filter(image, image);

    }

    private static void filterSolarize(BufferedImage image) {

        SolarizeFilter f = new SolarizeFilter();

        f.filter(image, image);

    }

    private static void filterGain(BufferedImage image, int filter_value) {

        double gain = (((int) (filter_value / 1000.0)) / 100.0);
        double bias = (((int) (filter_value % 1000.0)) / 100.0);

        GainFilter f = new GainFilter();
        f.setGain((float) gain);
        f.setBias((float) bias);

        f.filter(image, image);

    }

    private static void filterGamma(BufferedImage image, int filter_value) {

        double gamma = filter_value / 100.0 * 3.0;

        GammaFilter f = new GammaFilter();
        f.setGamma((float) gamma);

        f.filter(image, image);

    }

    private static void filterExposure(BufferedImage image, int filter_value) {

        double exposure = filter_value / 100.0 * 5.0;

        ExposureFilter f = new ExposureFilter();
        f.setExposure((float) exposure);

        f.filter(image, image);

    }

    private static void filterCrystallize(BufferedImage image, int filter_value, int filter_value2, Color filter_color) {

        double size = (((int) (((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) % 10000.0)) % 100.0)) / 80.0) * 100;
        double randomness = ((int) (((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) % 10000.0)) / 100.0)) / 80.0;
        double edge_size = ((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) / 10000.0)) / 80.0;
        int shape = ((int) (((int) (filter_value % 10000000.0)) / 1000000.0));
        int fade_edges = (int) (filter_value / 10000000.0);
        int original = filter_value2;

        CrystallizeFilter f = new CrystallizeFilter();

        f.setFadeEdges(fade_edges == 1);
        f.setUseOriginalColor(original == 1);

        f.setGridType(shape);
        f.setEdgeThickness((float) edge_size);
        f.setRandomness((float) randomness);
        f.setScale((float) size);
        f.setEdgeColor(filter_color.getRGB());

        f.filter(image, image);

    }

    private static void filterMarble(BufferedImage image, int filter_value) {

        MarbleTexFilter f = new MarbleTexFilter();

        double turbulence_factor = ((int) (filter_value / 10000000.0)) / 80.0;
        double turbulence = ((int) (((int) (filter_value % 10000000.0)) / 1000000.0)) / 8.0 * 8;
        double stretch = ((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) / 10000.0)) / 80.0 * 50;
        double angle = ((int) (((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) % 10000.0)) / 100.0)) / 80.0 * 360;
        double scale = ((int) (((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) % 10000.0)) % 100.0)) / 80.0 * 300;

        f.setTurbulenceFactor((float) turbulence_factor);
        f.setTurbulence((float) turbulence);
        f.setStretch((float) stretch);
        f.setAngle((float) Math.toRadians(angle));
        f.setScale((float) scale);

        f.filter(image, image);


    }

    private static void filterPointillize(BufferedImage image, int filter_value, int filter_value2, Color filter_color) {

        PointillizeFilter f = new PointillizeFilter();

        int fill = (int) (filter_value / 1000000000.0);
        int shape = (int) (((int) (filter_value % 1000000000.0)) / 100000000.0);
        double point_size = ((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) / 1000000.0)) / 80.0;
        double fuzziness = ((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) / 10000.0)) / 80.0;
        double randomness = ((int) (((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) / 100.0)) / 80.0;
        double grid_size = ((int) (((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) % 100.0)) / 80.0 * 100;
        int original = filter_value2;

        f.setFadeEdges(fill == 1);
        f.setUseOriginalColor(original == 1);

        f.setScale((float) grid_size);
        f.setFuzziness((float) fuzziness);
        f.setEdgeColor(filter_color.getRGB());
        f.setRandomness((float) randomness);
        f.setGridType(shape);
        f.setEdgeThickness((float) point_size);

        f.filter(image, image);

    }

    private static void filterOil(BufferedImage image, int filter_value) {

        OilFilter f = new OilFilter();

        int levels = (int) (filter_value / 100.0);
        int range = (int) (filter_value % 100.0);

        f.setRange(range);
        f.setLevels(levels);

        f.filter(image, image);

    }

    private static void filterWeave(BufferedImage image, int filter_value, Color filter_color) {

        WeaveFilter f = new WeaveFilter();

        double x_width = ((int) (((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) % 100.0)) / 80.0 * 256;
        double y_width = ((int) (((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) % 10000.0)) / 100.0)) / 80.0 * 256;
        double x_gap = ((int) (((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) % 1000000.0)) / 10000.0)) / 80.0 * 256;
        double y_gap = ((int) (((int) (((int) (filter_value % 1000000000.0)) % 100000000.0)) / 1000000.0)) / 80.0 * 256;

        int round_threads = (int) (((int) (filter_value % 1000000000.0)) / 100000000.0);
        int shade_crossings = (int) (filter_value / 1000000000.0);

        f.setRoundThreads(round_threads == 1);
        f.setShadeCrossings(shade_crossings == 1);

        f.setXWidth((float) x_width);
        f.setYWidth((float) y_width);
        f.setXGap((float) x_gap);
        f.setYGap((float) y_gap);

        f.setColor(filter_color.getRGB());

        f.setUseImageColors(true);

        f.filter(image, image);

    }

    private static void filterSparkle(BufferedImage image, int filter_value, Color filter_color) {

        SparkleFilter f = new SparkleFilter();

        double rays = (int) (((int) (((int) (filter_value % 1000000.0)) % 10000.0)) % 100.0) / 80.0 * 300;
        double radius = (int) (((int) (((int) (filter_value % 1000000.0)) % 10000.0)) / 100.0) / 80.0 * 300;
        double shine = (int) (((int) (filter_value % 1000000.0)) / 10000.0) / 80.0 * 100;
        double randomness = (int) (filter_value / 1000000.0) / 80.0 * 50;

        f.setRays((int) rays);
        f.setRadius((int) radius);
        f.setAmount((int) shine);
        f.setRandomness((int) randomness);
        f.setColor(filter_color.getRGB());

        f.filter(image, image);

    }

    private static void filterGlow(BufferedImage image, int filter_value) {

        double softness = ((int) (filter_value / 1000.0));
        double amount = ((int) (filter_value % 1000.0)) / 100.0;

        GlowFilter f = new GlowFilter();

        f.setAmount((float) amount);
        f.setRadius((float) softness);

        f.filter(image, image);

    }

    private static void filterNoise(BufferedImage image, int filter_value) {

        NoiseFilter f = new NoiseFilter();

        double density = ((int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) / 1000.0)) / 100.0;
        int amount = (int) (((int) (((int) (filter_value % 10000000.0)) % 1000000.0)) / 1000.0);
        int distribution = (int) (((int) (filter_value % 10000000.0)) / 1000000.0);
        int monochrome = (int) (filter_value / 10000000.0);

        f.setMonochrome(monochrome == 1);

        f.setDistribution(distribution);
        f.setAmount(amount);
        f.setDensity((float) density);

        f.filter(image, image);

    }

    private static void filterColorChannelScaling(BufferedImage image, int filter_value) {

        double scale = filter_value / 100.0 * 5;

        RescaleFilter f = new RescaleFilter();
        f.setScale((float) scale);

        f.filter(image, image);

    }

    private static void filterMirror(BufferedImage image, int filter_value) {

        int opacity = (int) (filter_value / 1000000.0);
        int mirrory = (int) (((int) (filter_value % 1000000.0)) / 1000.0);
        int gap = (int) (((int) (filter_value % 1000000.0)) % 1000.0);

        MirrorFilter f = new MirrorFilter();

        f.setOpacity(opacity / 100.0f);
        f.setGap(gap / 100.0f);
        f.setCentreY(mirrory / 100.0f);

        f.filter(image, image);

    }

    private static void filterLightEffects(BufferedImage image, int filter_value, int filter_value2, int filter_value3, Color filter_color, Color filter_color2, Color filter_color3) {

        LightFilter f = new LightFilter();

        int light_type = (int) (filter_value / 100000000.0);
        double direction = (int) (((int) (filter_value % 100000000.0)) / 1000000.0) / 80.0 * 360.0 * Math.PI / 180.0;
        double elevation = (int) (((int) (((int) (filter_value % 100000000.0)) % 1000000.0)) / 10000.0) / 80.0 * Math.PI / 2;
        double distance = (((int) (((int) (((int) (filter_value % 100000000.0)) % 1000000.0)) % 10000.0)) / 100) / 80.0 * 1000;
        double intensity = (((int) (((int) (((int) (filter_value % 100000000.0)) % 1000000.0)) % 10000.0)) % 100) / 80.0;

        double x = (int) (filter_value2 / 1000000.0) / 80.0;
        double y = (int) (((int) (filter_value2 % 1000000.0)) / 10000.0) / 80.0;
        double cone_angle = (int) (((int) (((int) (filter_value2 % 1000000.0)) % 10000.0)) / 100.0) / 80.0 * Math.PI / 2;
        double focus = (int) (((int) (((int) (filter_value2 % 1000000.0)) % 10000.0)) % 100.0) / 80.0;

        int source = (int) (filter_value3 / 10000000.0);
        double shininess = 10 - (int) (((int) (filter_value3 % 10000000.0)) / 100000.0) / 80.0 * 10.0;
        int bump_shape = (int) (((int) (((int) (filter_value3 % 10000000.0)) % 100000.0)) / 10000.0);
        double bump_height = (int) (((int) (((int) (((int) (filter_value3 % 10000000.0)) % 100000.0)) % 10000.0)) / 100.0) / 80.0 * 10 - 5;
        double bump_softness = (int) (((int) (((int) (((int) (filter_value3 % 10000000.0)) % 100000.0)) % 10000.0)) % 100.0) / 80.0 * 100.0;

        f.removeAllLights();

        Light l1 = f.addLight(light_type);
        l1.setFocus((float) focus);
        l1.setConeAngle((float) cone_angle);
        l1.setElevation((float) elevation);
        l1.setDistance((float) distance);
        l1.setIntensity((float) intensity);
        l1.setAzimuth((float) direction);
        l1.setColor(filter_color.getRGB());
        l1.setCentreX((float) x);
        l1.setCentreY((float) y);

        Material m = f.getMaterial();
        m.setShininess((float) shininess);
        m.setDiffuseColor(filter_color2.getRGB());
        m.setSpecularColor(filter_color3.getRGB());

        f.setBumpShape(bump_shape);
        f.setBumpHeight((float) bump_height);
        f.setBumpSoftness((float) bump_softness);

        f.setColorSource(source);

        f.filter(image, image);

    }

    private static void filterColorChannelMixing(BufferedImage image, int filter_value, Color filter_color) {

        ChannelMixFilter f = new ChannelMixFilter();

        int intoRed = filter_color.getRed();
        int intoGreen = filter_color.getGreen();
        int intoBlue = filter_color.getBlue();

        int blueGreen = (int) (((int) (filter_value % 1000000.0)) % 1000.0);
        int redBlue = (int) (((int) (filter_value % 1000000.0)) / 1000.0);
        int greenRed = (int) (filter_value / 1000000.0);

        f.setBlueGreen(blueGreen);
        f.setRedBlue(redBlue);
        f.setGreenRed(greenRed);
        f.setIntoR(intoRed);
        f.setIntoG(intoGreen);
        f.setIntoB(intoBlue);

        f.filter(image, image);

    }

    private static void filterEdgeDetection2(BufferedImage image, int filter_value) {
        int horizontal = (int) (filter_value / 10.0);
        int vertical = (int) (filter_value % 10.0);

        EdgeFilter f = new EdgeFilter();
        f.setHorizontalAlgorithm(horizontal);
        f.setVerticalAlgorithm(vertical);

        f.filter(image, image);

    }

    private static void filterQuadTree(BufferedImage image, double std_threshold, boolean showDivision, int fillAlgorithm, int fillColor, int divisionColor, int level_of_detail, int treeAlgorithm, boolean dynamicThreshold, int dynamicThresholdCurve, int threshold_calculation, boolean merge_nodes) {

        int image_width = image.getWidth();
        int image_height = image.getHeight();

        int[] raster = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        QuadTree t = new QuadTree(raster, image_width, image_height, std_threshold, treeAlgorithm, dynamicThreshold, dynamicThresholdCurve, threshold_calculation, merge_nodes);

        int tree_height = t.maxTreeHeight() - 1;
        tree_height = tree_height - (QuadTree.MAX_LEVEL - level_of_detail);
        tree_height = Math.max(0, tree_height);

        t.paintQuadImage(raster, image_width, image_height, tree_height, showDivision, fillAlgorithm, divisionColor, fillColor);
    }

    public static double[] createGaussianKernel(int length, double weight) {
        double[] gaussian_kernel = new double[length * length];
        double sumTotal = 0;

        //OpenCv
        //weight=0.3*((length-1)*0.5 - 1) + 0.8;
        int kernelRadius = length / 2;
        double distance = 0;

        double weightSqr2 =  2.0 * weight * weight;
        double calculatedEuler = 1.0 / (weightSqr2 * Math.PI);


        double temp;
        for (int filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for (int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (weightSqr2);
                temp = gaussian_kernel[(filterY + kernelRadius) * length + filterX + kernelRadius] = (calculatedEuler * Math.exp(-distance));
                sumTotal += temp;
            }
        }

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                gaussian_kernel[y * length + x] = (gaussian_kernel[y * length + x] / sumTotal);
            }
        }

        return gaussian_kernel;

    }

    public static float[] createGaussianKernel(int length, float weight) {
        float[] gaussian_kernel = new float[length * length];
        double sumTotal = 0;

        //OpenCv
        //weight=0.3*((length-1)*0.5 - 1) + 0.8;
        int kernelRadius = length / 2;
        double distance = 0;

        double weightSqr2 =  2.0 * weight * weight;
        double calculatedEuler = 1.0 / (weightSqr2 * Math.PI);


        float temp;
        for (int filterY = -kernelRadius; filterY <= kernelRadius; filterY++) {
            for (int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (weightSqr2);
                temp = gaussian_kernel[(filterY + kernelRadius) * length + filterX + kernelRadius] = (float) (calculatedEuler * Math.exp(-distance));
                sumTotal += temp;
            }
        }

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                gaussian_kernel[y * length + x] = (float) (gaussian_kernel[y * length + x] / sumTotal);
            }
        }

        return gaussian_kernel;

    }

    private static void setProgress(JProgressBar progressbar, int count) {

        progressbar.setValue(count);
        progressbar.setString("Image Filters: " + count + "/" + progressbar.getMaximum());

    }

    public static void filter(BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, FiltersSettings fs, JProgressBar progressbar) {

        int active = 0;
        if (filters[MainWindow.ANTIALIASING] && progressbar != null) {
            active++;
            setProgress(progressbar, active);
        }

        for (int i = 0; i < filters_order.length; i++) {
            switch (filters_order[i]) {
                case MainWindow.CRYSTALLIZE:
                    if (filters[MainWindow.CRYSTALLIZE]) {
                        filterCrystallize(image, filters_options_vals[MainWindow.CRYSTALLIZE], filters_options_extra_vals[0][MainWindow.CRYSTALLIZE], filters_colors[MainWindow.CRYSTALLIZE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.POINTILLIZE:
                    if (filters[MainWindow.POINTILLIZE]) {
                        filterPointillize(image, filters_options_vals[MainWindow.POINTILLIZE], filters_options_extra_vals[0][MainWindow.POINTILLIZE], filters_colors[MainWindow.POINTILLIZE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.LIGHT_EFFECTS:
                    if (filters[MainWindow.LIGHT_EFFECTS]) {
                        filterLightEffects(image, filters_options_vals[MainWindow.LIGHT_EFFECTS], filters_options_extra_vals[0][MainWindow.LIGHT_EFFECTS], filters_options_extra_vals[1][MainWindow.LIGHT_EFFECTS], filters_colors[MainWindow.LIGHT_EFFECTS], filters_extra_colors[0][MainWindow.LIGHT_EFFECTS], filters_extra_colors[1][MainWindow.LIGHT_EFFECTS]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.OIL:
                    if (filters[MainWindow.OIL]) {
                        filterOil(image, filters_options_vals[MainWindow.OIL]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.MARBLE:
                    if (filters[MainWindow.MARBLE]) {
                        filterMarble(image, filters_options_vals[MainWindow.MARBLE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.WEAVE:
                    if (filters[MainWindow.WEAVE]) {
                        filterWeave(image, filters_options_vals[MainWindow.WEAVE], filters_colors[MainWindow.WEAVE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.SPARKLE:
                    if (filters[MainWindow.SPARKLE]) {
                        filterSparkle(image, filters_options_vals[MainWindow.SPARKLE], filters_colors[MainWindow.SPARKLE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_SWAPPING:
                    if (filters[MainWindow.COLOR_CHANNEL_SWAPPING]) {
                        filterColorChannelSwapping(image, filters_options_vals[MainWindow.COLOR_CHANNEL_SWAPPING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_SWIZZLING:
                    if (filters[MainWindow.COLOR_CHANNEL_SWIZZLING]) {
                        filterColorChannelSwizzling(image, filters_options_vals[MainWindow.COLOR_CHANNEL_SWIZZLING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_MIXING:
                    if (filters[MainWindow.COLOR_CHANNEL_MIXING]) {
                        filterColorChannelMixing(image, filters_options_vals[MainWindow.COLOR_CHANNEL_MIXING], filters_colors[MainWindow.COLOR_CHANNEL_MIXING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_ADJUSTING:
                    if (filters[MainWindow.COLOR_CHANNEL_ADJUSTING]) {
                        filterColorChannelAdjusting(image, filters_options_vals[MainWindow.COLOR_CHANNEL_ADJUSTING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_HSB_ADJUSTING:
                    if (filters[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]) {
                        filterHSBcolorChannelAdjusting(image, filters_options_vals[MainWindow.COLOR_CHANNEL_HSB_ADJUSTING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_SCALING:
                    if (filters[MainWindow.COLOR_CHANNEL_SCALING]) {
                        filterColorChannelScaling(image, filters_options_vals[MainWindow.COLOR_CHANNEL_SCALING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.GRAYSCALE:
                    if (filters[MainWindow.GRAYSCALE]) {
                        filterGrayscale(image, filters_options_vals[MainWindow.GRAYSCALE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.POSTERIZE:
                    if (filters[MainWindow.POSTERIZE]) {
                        filterPosterize(image, filters_options_vals[MainWindow.POSTERIZE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.DITHER:
                    if (filters[MainWindow.DITHER]) {
                        filterDither(image, filters_options_vals[MainWindow.DITHER]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.INVERT_COLORS:
                    if (filters[MainWindow.INVERT_COLORS]) {
                        filterInvertColors(image, filters_options_vals[MainWindow.INVERT_COLORS]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.SOLARIZE:
                    if (filters[MainWindow.SOLARIZE]) {
                        filterSolarize(image);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_TEMPERATURE:
                    if (filters[MainWindow.COLOR_TEMPERATURE]) {
                        filterColorTemperature(image, filters_options_vals[MainWindow.COLOR_TEMPERATURE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.CONTRAST_BRIGHTNESS:
                    if (filters[MainWindow.CONTRAST_BRIGHTNESS]) {
                        filterContrastBrightness(image, filters_options_vals[MainWindow.CONTRAST_BRIGHTNESS]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.GAIN:
                    if (filters[MainWindow.GAIN]) {
                        filterGain(image, filters_options_vals[MainWindow.GAIN]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.GAMMA:
                    if (filters[MainWindow.GAMMA]) {
                        filterGamma(image, filters_options_vals[MainWindow.GAMMA]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.EXPOSURE:
                    if (filters[MainWindow.EXPOSURE]) {
                        filterExposure(image, filters_options_vals[MainWindow.EXPOSURE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.HISTOGRAM_EQUALIZATION:
                    if (filters[MainWindow.HISTOGRAM_EQUALIZATION]) {
                        filterHistogramEqualization(image, filters_options_vals[MainWindow.HISTOGRAM_EQUALIZATION]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.COLOR_CHANNEL_MASKING:
                    if (filters[MainWindow.COLOR_CHANNEL_MASKING]) {
                        filterMaskColors(image, filters_options_vals[MainWindow.COLOR_CHANNEL_MASKING]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.NOISE:
                    if (filters[MainWindow.NOISE]) {
                        filterNoise(image, filters_options_vals[MainWindow.NOISE]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.SHARPNESS:
                    if (filters[MainWindow.SHARPNESS]) {
                        filterSharpness(image, filters_options_vals[MainWindow.SHARPNESS]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.BLURRING:
                    if (filters[MainWindow.BLURRING]) {
                        filterBlurring(image, filters_options_vals[MainWindow.BLURRING], fs.bluringSigmaR, fs.bluringSigmaS, fs.blurringKernelSelection * 2 + 3);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.GLOW:
                    if (filters[MainWindow.GLOW]) {
                        filterGlow(image, filters_options_vals[MainWindow.GLOW]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.EMBOSS:
                    if (filters[MainWindow.EMBOSS]) {
                        filterEmboss(image, filters_options_vals[MainWindow.EMBOSS]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.EDGE_DETECTION:
                    if (filters[MainWindow.EDGE_DETECTION]) {
                        filterEdgeDetection(image, filters_options_vals[MainWindow.EDGE_DETECTION], filters_colors[MainWindow.EDGE_DETECTION]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.EDGE_DETECTION2:
                    if (filters[MainWindow.EDGE_DETECTION2]) {
                        filterEdgeDetection2(image, filters_options_vals[MainWindow.EDGE_DETECTION2]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.FADE_OUT:
                    if (filters[MainWindow.FADE_OUT]) {
                        filterFadeOut(image);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.MIRROR:
                    if (filters[MainWindow.MIRROR]) {
                        filterMirror(image, filters_options_vals[MainWindow.MIRROR]);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
                case MainWindow.QUAD_TREE_COMPRESSION:
                    if (filters[MainWindow.QUAD_TREE_COMPRESSION]) {
                        filterQuadTree(image, fs.quadtree_threshold, fs.quadtree_show_division, fs.quadtree_fill_algorithm, filters_colors[MainWindow.QUAD_TREE_COMPRESSION].getRGB(), filters_extra_colors[0][MainWindow.QUAD_TREE_COMPRESSION].getRGB(), fs.quadtree_lod, fs.quadtree_algorithm, fs.quadtree_dynamic_threshold, fs.quadtree_dynamic_threshold_curve, fs.quadtree_threshold_calculation, fs.quadtree_merge_nodes);
                        if (progressbar != null) {
                            active++;
                            setProgress(progressbar, active);
                        }
                    }
                    break;
            }
        }
    }

}
