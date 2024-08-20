package fractalzoomer.utils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Mipmap {
    int levels;
    BufferedImage []images;

    public int m_pixel_rgb(double r, double g, double b) {
        int ri = (int)Math.min(Math.max(255 * r + 0.5, 0), 255);
        int gi = (int)Math.min(Math.max(255 * g + 0.5, 0), 255);
        int bi = (int)Math.min(Math.max(255 * b + 0.5, 0), 255);
        return 0xff000000 | (ri << 16) | (gi << 8) | bi;
    }

    double m_pixel_red(int p) {
        return ((p >> 16) & 0xff) / 255.0;
    }

    double m_pixel_green(int p) {
        return ((p >> 8) & 0xff) / 255.0;
    }

    double m_pixel_blue(int p) {
        return (p & 0xff) / 255.0;
    }

    public int m_pixel_mix(int a, int b, double x) {
        return m_pixel_rgb
                ( mix(m_pixel_red(a), m_pixel_red(b), x)
                        , mix(m_pixel_green(a), m_pixel_green(b), x)
                        , mix(m_pixel_blue(a), m_pixel_blue(b), x)
                );
    }

    public void m_mipmap_new(BufferedImage img) {

        int levels = (int)Math.floor(Math.log(Math.max(img.getWidth(), img.getHeight())) / Math.log(2)) + 1;

//        if(levels == 0) {
//            levels = 1;
//        }
//
        if( levels > 6) {
            levels -= 6;
        }
        //int size = 1 << (levels - 1);
//        if (! (img.getWidth() == size && img.getHeight() == size)) {
//            return;
//        }
        //m_mipmap *m = malloc(sizeof(*m));
        try {
            this.levels = levels;
            images = new BufferedImage[levels];

            ColorModel cm = img.getColorModel();
            boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
            WritableRaster raster = img.copyData(null);
            images[0] = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
            for (int l = 1; l < levels; ++l) {
                int w = images[l - 1].getWidth() / 2;
                int h = images[l - 1].getHeight() / 2;
                images[l] = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                // #pragma omp parallel for
                for (int j = 0; j < h; ++j) {
                    for (int i = 0; i < w; ++i) {
                        int p00 = images[l - 1].getRGB(2 * i + 0, 2 * j + 0);//m_image_peek(m->images[l-1], 2 * i + 0, 2 * j + 0);
                        int p10 = images[l - 1].getRGB(2 * i + 1, 2 * j + 0);//m_image_peek(m->images[l-1], 2 * i + 1, 2 * j + 0);
                        int p01 = images[l - 1].getRGB(2 * i + 0, 2 * j + 1);//m_image_peek(m->images[l-1], 2 * i + 0, 2 * j + 1);
                        int p11 = images[l - 1].getRGB(2 * i + 1, 2 * j + 1);//m_image_peek(m->images[l-1], 2 * i + 1, 2 * j + 1);
                        int p0 = m_pixel_mix(p00, p01, 0.5);
                        int p1 = m_pixel_mix(p10, p11, 0.5);
                        int p = m_pixel_mix(p0, p1, 0.5);
                        images[l].setRGB(i, j, p);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public int m_mipmap_get_levels() {
        return levels;
    }

    static double mix(double a, double b, double x) {
        return (1 - x) * a + x * b;
    }

    static double clamp(double x, double lo, double hi) {
        return Math.min(Math.max(x, lo), hi);
    }

    private int closest(BufferedImage mipmap, double u, double v) {
        int width = mipmap.getWidth();
        int height = mipmap.getHeight();

        int x1 = (int) (u * width);
        int y1 = (int) (v * height);

        x1 = Math.max(0, Math.min(x1, width - 1));
        y1 = Math.max(0, Math.min(y1, height - 1));

        return mipmap.getRGB(x1, y1);
    }

    private int bilinearInterpolate(BufferedImage mipmap, double u, double v) {
        int w = mipmap.getWidth();
        int h = mipmap.getHeight();

        double i = (u) * w;
        double j = (v) * h;
        int i0 = (int)Math.floor(i);
        int j0 = (int)Math.floor(j);
        int i1 = i0 + 1;
        int j1 = j0 + 1;
        double ix = i - i0;
        double jx = j - j0;
        i0 = (int)clamp(i0, 0, w - 1);
        j0 = (int)clamp(j0, 0, h - 1);
        i1 = (int)clamp(i1, 0, w - 1);
        j1 = (int)clamp(j1, 0, h - 1);
        int p00 = mipmap.getRGB(i0, j0);
        int p10 = mipmap.getRGB( i1, j0);
        int p01 = mipmap.getRGB( i0, j1);
        int p11 = mipmap.getRGB( i1, j1);
        int p0 = m_pixel_mix(p00, p01, jx);
        int p1 = m_pixel_mix(p10, p11, jx);

        return m_pixel_mix(p0, p1, ix);
    }

    public int getPixelColor(double u, double v, double d) {
        // Determine the level of detail based on texture coordinates
        double level = d * (levels - 1);

        // Calculate the mipmap levels to sample from
        int level1 = (int) Math.floor(level);
        int level2 = Math.min(levels - 1, level1 + 1);

        // Get the two mipmaps to sample from
        BufferedImage mipmap1 = images[level1];
        BufferedImage mipmap2 = images[level2];

        // Calculate the amount to blend the two mipmaps
        double blend = level - level1;

        // Sample the colors from the two mipmaps using bilinear interpolation
        int color1 = bilinearInterpolate(mipmap1, u, v);
        int color2 = bilinearInterpolate(mipmap2, u, v);
        //int color1 = closest(mipmap1, u, v);
       // int color2 = closest(mipmap2, u, v);

        // Blend the colors using trilinear interpolation
        return m_pixel_mix(color1, color2, blend);
    }

    /*
     Mipmap m = new Mipmap();
        m.m_mipmap_new(image);


        int loc = 0;
        int x;
        for(int y = FROMy; y < TOy; y++) {
            for (x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {
                rgbs[loc] = m.getPixelColor(x / ((double)(image_size - 1)), y / ((double)(image_size - 1)), 1 - (ColorAlgorithm.transformResultToHeight(image_iterations[loc], max_iterations) - Fractal.total_min_iterations.get()) / (Fractal.total_max_iterations.get() - Fractal.total_min_iterations.get()));
            }
        }


     */
}
