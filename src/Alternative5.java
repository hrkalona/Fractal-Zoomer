


import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Alternative5 extends ThreadDraw {
  private static Color[] palette = {new Color(12, 0, 0),new Color(16, 4, 4),new Color(21, 8, 8),new Color(25, 12, 12),new Color(30, 16, 16),new Color(35, 19, 19),new Color(39, 23, 23),new Color(44, 27, 27),new Color(49, 31, 31),new Color(53, 35, 35),new Color(58, 39, 39),new Color(63, 43, 43),new Color(67, 47, 47),new Color(72, 51, 51),new Color(77, 56, 56),new Color(76, 52, 52),new Color(75, 48, 48),new Color(75, 45, 45),new Color(74, 41, 41),new Color(74, 38, 38),new Color(73, 34, 34),new Color(73, 31, 31),new Color(72, 27, 27),new Color(71, 23, 23),new Color(71, 20, 20),new Color(70, 16, 16),new Color(70, 13, 13),new Color(69, 9, 9),new Color(69, 6, 6),new Color(74, 9, 9),new Color(80, 13, 13),new Color(85, 16, 16),new Color(91, 20, 20),new Color(97, 23, 23),new Color(102, 26, 27),new Color(108, 30, 30),new Color(114, 33, 34),new Color(119, 37, 38),new Color(125, 40, 41),new Color(131, 44, 45),new Color(136, 47, 48),new Color(142, 51, 52),new Color(148, 55, 56),new Color(155, 65, 66),new Color(162, 75, 76),new Color(170, 85, 86),new Color(177, 95, 96),new Color(184, 105, 107),new Color(192, 114, 117),new Color(199, 124, 127),new Color(206, 135, 137),new Color(214, 144, 147),new Color(221, 154, 158),new Color(228, 164, 168),new Color(236, 174, 178),new Color(243, 184, 188),new Color(251, 195, 199),new Color(233, 181, 184),new Color(216, 167, 170),new Color(199, 153, 156),new Color(182, 139, 142),new Color(165, 125, 127),new Color(148, 111, 113),new Color(131, 97, 99),new Color(114, 83, 85),new Color(97, 69, 71),new Color(80, 55, 56),new Color(63, 41, 42),new Color(46, 27, 28),new Color(29, 13, 14),};
  private double color_intensity;

     public Alternative5(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

        /*int[][] colors = {
            {14, 12, 0, 0},
            {14, 77, 56, 56},
            {14, 69, 6, 6},
            {14, 148, 55, 56},
            {14, 251, 195, 199},};*/
        

        this.color_intensity = color_intensity;

    }

    public Alternative5(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Alternative5(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Alternative5(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Alternative5(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        this.color_intensity = color_intensity;

    }

    public Alternative5(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

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
