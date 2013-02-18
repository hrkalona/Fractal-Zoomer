


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
public class Blue extends ThreadDraw {
  private static Color[] palette = {new Color(0, 0, 64),new Color(0, 0, 81),new Color(0, 0, 98),new Color(0, 0, 116),new Color(0, 0, 133),new Color(0, 0, 150),new Color(0, 0, 168),new Color(0, 0, 185),new Color(0, 0, 202),new Color(0, 0, 220),new Color(0, 0, 237),new Color(0, 0, 255),new Color(0, 0, 255),new Color(0, 23, 255),new Color(0, 46, 255),new Color(0, 69, 255),new Color(0, 92, 255),new Color(0, 115, 255),new Color(0, 139, 255),new Color(0, 162, 255),new Color(0, 185, 255),new Color(0, 208, 255),new Color(0, 231, 255),new Color(0, 255, 255),new Color(0, 255, 255),new Color(14, 255, 255),new Color(28, 255, 255),new Color(42, 255, 255),new Color(56, 255, 255),new Color(71, 255, 255),new Color(85, 255, 255),new Color(99, 255, 255),new Color(113, 255, 255),new Color(128, 255, 255),new Color(128, 255, 255),new Color(122, 243, 255),new Color(116, 231, 255),new Color(110, 220, 255),new Color(104, 208, 255),new Color(98, 197, 255),new Color(93, 185, 255),new Color(87, 174, 255),new Color(81, 162, 255),new Color(75, 151, 255),new Color(69, 139, 255),new Color(64, 128, 255),new Color(64, 128, 255),new Color(59, 118, 240),new Color(54, 108, 225),new Color(49, 98, 210),new Color(44, 88, 196),new Color(39, 78, 181),new Color(34, 68, 166),new Color(29, 59, 152),new Color(24, 49, 137),new Color(19, 39, 122),new Color(14, 29, 108),new Color(9, 19, 93),new Color(4, 9, 78),new Color(0, 0, 64),};
  private double color_intensity;

     public Blue(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

       /* int[][] colors = {
            {12,   0,   0,  64},
            {12,   0,   0, 255},
            {10,   0, 255, 255},
            {12, 128, 255, 255},
            {14,  64, 128, 255},};*/

        this.color_intensity = color_intensity;

    }

    public Blue(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Blue(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Blue(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Blue(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        this.color_intensity = color_intensity;

    }

    public Blue(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);

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

        try {
            return new Color(red, green, blue);
        }
        catch(Exception ex) {
            return new Color(red > 255 ? red % 256 : 0, green > 255 ? green % 256 : 0, blue > 255 ? blue % 256 : 0);
        }

    }

    private Color getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return palette[((int)((i + palette.length) * color_intensity)) % palette.length];

    }

}