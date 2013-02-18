


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
public class Alternative4 extends ThreadDraw {
  private static Color[] palette = {new Color(214, 198, 146),new Color(216, 200, 151),new Color(218, 202, 156),new Color(221, 205, 161),new Color(223, 207, 166),new Color(226, 210, 171),new Color(228, 212, 176),new Color(231, 215, 181),new Color(233, 217, 186),new Color(236, 220, 191),new Color(238, 222, 196),new Color(241, 225, 202),new Color(241, 225, 202),new Color(227, 214, 189),new Color(214, 203, 176),new Color(201, 192, 163),new Color(188, 181, 151),new Color(175, 170, 138),new Color(161, 159, 125),new Color(148, 148, 112),new Color(135, 137, 100),new Color(122, 126, 87),new Color(109, 115, 74),new Color(96, 105, 62),new Color(96, 105, 62),new Color(99, 110, 65),new Color(102, 116, 69),new Color(105, 122, 72),new Color(108, 128, 76),new Color(111, 134, 80),new Color(114, 140, 83),new Color(117, 146, 87),new Color(120, 152, 91),new Color(123, 158, 94),new Color(126, 164, 98),new Color(129, 170, 102),new Color(129, 170, 102),new Color(125, 160, 96),new Color(121, 151, 90),new Color(117, 142, 85),new Color(113, 133, 79),new Color(109, 124, 74),new Color(105, 114, 68),new Color(101, 105, 63),new Color(97, 96, 57),new Color(93, 87, 52),new Color(89, 78, 46),new Color(86, 69, 41),new Color(86, 69, 41),new Color(97, 80, 50),new Color(109, 92, 60),new Color(120, 104, 69),new Color(132, 115, 79),new Color(144, 127, 88),new Color(155, 139, 98),new Color(167, 151, 107),new Color(179, 162, 117),new Color(190, 174, 126),new Color(202, 186, 136),new Color(214, 198, 146),};
  private double color_intensity;

     public Alternative4(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

        /*int[][] colors = {
            {12, 214, 198, 146},
            {12, 241, 225, 202},
            {12, 96, 105, 62},
            {12, 129, 170, 102},
            {12, 86, 69, 41},};*/

        this.color_intensity = color_intensity;

    }

    public Alternative4(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Alternative4(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Alternative4(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Alternative4(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        this.color_intensity = color_intensity;

    }

    public Alternative4(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

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
