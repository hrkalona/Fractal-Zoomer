


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
  private static Color[] palette = {new Color(12, 0, 0),new Color(17, 4, 4),new Color(22, 8, 8),new Color(27, 12, 12),new Color(32, 17, 17),new Color(37, 21, 21),new Color(42, 25, 25),new Color(47, 30, 30),new Color(52, 34, 34),new Color(57, 38, 38),new Color(62, 43, 43),new Color(67, 47, 47),new Color(72, 51, 51),new Color(77, 56, 56),new Color(77, 56, 56),new Color(76, 52, 52),new Color(75, 48, 48),new Color(75, 44, 44),new Color(74, 40, 40),new Color(73, 36, 36),new Color(73, 32, 32),new Color(72, 29, 29),new Color(72, 25, 25),new Color(71, 21, 21),new Color(70, 17, 17),new Color(70, 13, 13),new Color(69, 9, 9),new Color(69, 6, 6),new Color(69, 6, 6),new Color(75, 9, 9),new Color(81, 13, 13),new Color(87, 17, 17),new Color(93, 21, 21),new Color(99, 24, 25),new Color(105, 28, 29),new Color(111, 32, 32),new Color(117, 36, 36),new Color(123, 39, 40),new Color(129, 43, 44),new Color(135, 47, 48),new Color(141, 51, 52),new Color(148, 55, 56),new Color(148, 55, 56),new Color(155, 65, 67),new Color(163, 76, 78),new Color(171, 87, 89),new Color(179, 98, 100),new Color(187, 108, 111),new Color(195, 119, 122),new Color(203, 130, 133),new Color(211, 141, 144),new Color(219, 151, 155),new Color(227, 162, 166),new Color(235, 173, 177),new Color(243, 184, 188),new Color(251, 195, 199),new Color(251, 195, 199),new Color(232, 180, 183),new Color(214, 165, 168),new Color(195, 150, 153),new Color(177, 135, 137),new Color(159, 120, 122),new Color(140, 105, 107),new Color(122, 90, 91),new Color(103, 75, 76),new Color(85, 60, 61),new Color(67, 45, 45),new Color(48, 30, 30),new Color(30, 15, 15),new Color(12, 0, 0),};
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
