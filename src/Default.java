
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
public class Default extends ThreadPaint {
  private int[] palette;
  private double color_intensity;

    //Fractal
    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Julia
    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double[][] image_iterations, int bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, image_iterations, bailout, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }
    
    /*//Fractal
    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout, double[][] image_iterations, MainWindow ptr, Color fractal_color, BufferedImage image, boolean anti_aliasing, int out_coloring_algorithm, double color_intensity, boolean image_buffering, boolean periodicity_checking, boolean inverse_plane, boolean edges, boolean emboss, boolean burning_ship, int function, double z_exponent, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, image_iterations, ptr, fractal_color, image, anti_aliasing, out_coloring_algorithm, image_buffering, periodicity_checking, inverse_plane, edges, emboss, burning_ship, function, z_exponent, color_cycling_location);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }*/

    //Julia Preview
    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, boolean inverse_plane, boolean anti_aliasing, boolean edges, boolean emboss, int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, periodicity_checking, inverse_plane, out_coloring_algorithm, anti_aliasing, edges, emboss, burning_ship, function, z_exponent, color_cycling_location, xJuliaCenter, yJuliaCenter);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Color Cycling
    public Default(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Apply Filter
    public Default(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, double[][] image_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean image_buffering, int out_coloring_algorithm, double color_intensity, boolean anti_aliasing, boolean edges, boolean emboss) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, image_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, image_buffering, anti_aliasing, edges, emboss);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    @Override
    protected Color getDrawingColor(double result) {
       
        return  new Color(palette[((int)(result * color_intensity)) % palette.length]);
        
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
        return new Color (palette[((int)((i + palette.length) * color_intensity)) % palette.length]);

    }

}
