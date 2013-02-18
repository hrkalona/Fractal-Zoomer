


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
public class Alternative3 extends ThreadDraw {
  private static Color[] palette = {new Color(161, 36, 32),new Color(149, 34, 29),new Color(137, 32, 27),new Color(125, 30, 25),new Color(114, 28, 23),new Color(102, 26, 21),new Color(90, 24, 18),new Color(78, 22, 16),new Color(67, 20, 14),new Color(55, 18, 12),new Color(43, 16, 10),new Color(32, 15, 8),new Color(32, 15, 8),new Color(48, 32, 24),new Color(65, 49, 41),new Color(81, 67, 57),new Color(98, 84, 74),new Color(114, 102, 91),new Color(131, 119, 107),new Color(147, 137, 124),new Color(164, 154, 141),new Color(180, 172, 157),new Color(197, 189, 174),new Color(214, 207, 191),new Color(214, 207, 191),new Color(213, 204, 185),new Color(213, 202, 179),new Color(212, 200, 173),new Color(212, 198, 167),new Color(211, 196, 161),new Color(211, 194, 156),new Color(210, 192, 150),new Color(210, 190, 144),new Color(209, 188, 138),new Color(209, 186, 132),new Color(209, 184, 127),new Color(209, 184, 127),new Color(204, 177, 119),new Color(200, 171, 112),new Color(196, 165, 105),new Color(192, 159, 98),new Color(188, 153, 91),new Color(184, 147, 84),new Color(180, 141, 77),new Color(176, 135, 70),new Color(172, 129, 63),new Color(168, 123, 56),new Color(164, 117, 49),new Color(164, 117, 49),new Color(163, 109, 47),new Color(163, 102, 45),new Color(163, 94, 44),new Color(162, 87, 42),new Color(162, 80, 41),new Color(162, 72, 39),new Color(162, 65, 38),new Color(161, 58, 36),new Color(161, 50, 35),new Color(161, 43, 33),new Color(161, 36, 32),};
  private double color_intensity;

     public Alternative3(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

        /*int[][] colors = {
            {12, 161, 36, 32},
            {12, 32, 15, 8},
            {12, 214, 207, 191},
            {12, 209, 184, 127},
            {12, 164, 117, 49},};*/
 
        this.color_intensity = color_intensity;

    }

    public Alternative3(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Alternative3(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Alternative3(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Alternative3(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        this.color_intensity = color_intensity;

    }

    public Alternative3(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

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
