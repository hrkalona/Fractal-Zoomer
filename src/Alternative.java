


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
public class Alternative extends ThreadDraw {
  private int[] palette;
  private double color_intensity;

    //Fractal
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Julia
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }
    
  
    //Julia Image
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Julia Preview
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters, burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Color Cycling
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
        palette = new Palette().makeRGBs(150, 53);///new Palette().makeRGBs(100, 38);//Palette().makeRGBs(5000, 1570);
        this.color_intensity = color_intensity;

    }

    //Apply Filter
    public Alternative(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);
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
        
        try {
            return new Color(red, green, blue);
        }
        catch(Exception ex) {
            return new Color(red > 255 ? red % 256 : 0, green > 255 ? green % 256 : 0, blue > 255 ? blue % 256 : 0);
        }

    }

    private Color getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return new Color (palette[((int)((i + palette.length) * color_intensity)) % palette.length]);

    }

}
