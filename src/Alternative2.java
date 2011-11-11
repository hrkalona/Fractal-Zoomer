
import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Alternative2 extends ThreadPaint {
  private Color[] palette;
  private double color_intensity;

     public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);

        int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};

        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
            }
            n += c1[0];
        }

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);

        int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};

        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
            }
            n += c1[0];
        }

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, boolean inverse_plane, boolean anti_aliasing, boolean edges, boolean emboss, int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, periodicity_checking, inverse_plane, out_coloring_algorithm, anti_aliasing, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);

        int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};

        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
            }
            n += c1[0];
        }

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};

        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
            }
            n += c1[0];
        }

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean image_buffering, int out_coloring_algorithm, double color_intensity, boolean anti_aliasing, boolean edges, boolean emboss) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);

        int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};

        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
            }
            n += c1[0];
        }

        this.color_intensity = color_intensity;

    }

    @Override
    protected Color getDrawingColor(double result) {

        return palette[((int)(result * color_intensity)) % palette.length];

    }

    @Override
    protected Color getDrawingColorSmooth(double result) {

        result *= 256;
        Color color = getColor((int)result / 256);
        Color color2 = getColor((int)result / 256 - 1);
        int k1 = (int)result % 256;
        int k2 = 255 - k1;
        int red = (k1 * color.getRed() + k2 * color2.getRed()) / 255;
        int green = (k1 * color.getGreen() + k2 * color2.getGreen()) / 255;
        int blue = (k1 * color.getBlue() + k2 * color2.getBlue()) / 255;

        return new Color(red, green, blue);

    }

    private Color getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return palette[((int)((i + palette.length) * color_intensity)) % palette.length];

    }

}

