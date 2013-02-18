

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
public class Default extends ThreadDraw {
  private static Color[] palette = {new Color(0, 10, 20),new Color(4, 18, 40),new Color(9, 26, 60),new Color(13, 34, 80),new Color(18, 42, 100),new Color(22, 50, 120),new Color(27, 59, 140),new Color(31, 67, 160),new Color(36, 75, 180),new Color(40, 83, 200),new Color(45, 91, 220),new Color(50, 100, 240),new Color(50, 100, 240),new Color(47, 91, 220),new Color(44, 82, 201),new Color(41, 73, 181),new Color(39, 64, 162),new Color(36, 55, 142),new Color(33, 47, 123),new Color(30, 38, 103),new Color(28, 29, 84),new Color(25, 20, 64),new Color(22, 11, 45),new Color(20, 3, 26),new Color(20, 3, 26),new Color(39, 8, 25),new Color(58, 13, 24),new Color(77, 18, 24),new Color(96, 23, 23),new Color(115, 28, 23),new Color(134, 34, 22),new Color(153, 39, 22),new Color(172, 44, 21),new Color(191, 49, 21),new Color(210, 54, 20),new Color(230, 60, 20),new Color(230, 60, 20),new Color(211, 55, 19),new Color(192, 50, 18),new Color(174, 46, 17),new Color(155, 41, 16),new Color(136, 37, 15),new Color(118, 32, 14),new Color(99, 28, 13),new Color(80, 23, 12),new Color(62, 19, 11),new Color(43, 14, 10),new Color(25, 10, 9),new Color(25, 10, 9),new Color(43, 24, 8),new Color(62, 39, 7),new Color(80, 53, 6),new Color(99, 68, 5),new Color(118, 82, 4),new Color(136, 97, 4),new Color(155, 111, 3),new Color(174, 126, 2),new Color(192, 140, 1),new Color(211, 155, 0),new Color(230, 170, 0),new Color(230, 170, 0),new Color(210, 158, 0),new Color(191, 146, 1),new Color(172, 134, 2),new Color(153, 122, 3),new Color(134, 110, 4),new Color(115, 99, 5),new Color(96, 87, 6),new Color(77, 75, 7),new Color(58, 63, 8),new Color(39, 51, 9),new Color(20, 40, 10),new Color(20, 40, 10),new Color(18, 45, 9),new Color(16, 50, 8),new Color(14, 56, 7),new Color(12, 61, 6),new Color(10, 67, 5),new Color(9, 72, 4),new Color(7, 78, 3),new Color(5, 83, 2),new Color(3, 89, 1),new Color(1, 94, 0),new Color(0, 100, 0),new Color(0, 100, 0),new Color(0, 91, 0),new Color(0, 83, 1),new Color(1, 75, 2),new Color(1, 67, 3),new Color(2, 59, 4),new Color(2, 50, 5),new Color(3, 42, 6),new Color(3, 34, 7),new Color(4, 26, 8),new Color(4, 18, 9),new Color(5, 10, 10),new Color(5, 10, 10),new Color(23, 15, 11),new Color(42, 20, 13),new Color(60, 26, 15),new Color(79, 31, 17),new Color(98, 37, 19),new Color(116, 42, 20),new Color(135, 48, 22),new Color(154, 53, 24),new Color(172, 59, 26),new Color(191, 64, 28),new Color(210, 70, 30),new Color(210, 70, 30),new Color(199, 63, 31),new Color(188, 57, 33),new Color(177, 50, 35),new Color(166, 44, 37),new Color(155, 38, 39),new Color(144, 31, 40),new Color(133, 25, 42),new Color(122, 19, 44),new Color(111, 12, 46),new Color(100, 6, 48),new Color(90, 0, 50),new Color(90, 0, 50),new Color(98, 8, 56),new Color(106, 16, 62),new Color(114, 24, 69),new Color(122, 32, 75),new Color(130, 40, 81),new Color(139, 49, 88),new Color(147, 57, 94),new Color(155, 65, 100),new Color(163, 73, 107),new Color(171, 81, 113),new Color(180, 90, 120),new Color(180, 90, 120),new Color(163, 83, 112),new Color(147, 77, 105),new Color(130, 70, 98),new Color(114, 64, 90),new Color(98, 58, 83),new Color(81, 51, 76),new Color(65, 45, 69),new Color(49, 39, 61),new Color(32, 32, 54),new Color(16, 26, 47),new Color(0, 20, 40),new Color(0, 20, 40),new Color(2, 24, 54),new Color(5, 29, 69),new Color(8, 33, 83),new Color(10, 38, 98),new Color(13, 42, 112),new Color(16, 47, 127),new Color(19, 51, 141),new Color(21, 56, 156),new Color(24, 60, 170),new Color(27, 65, 185),new Color(30, 70, 200),new Color(30, 70, 200),new Color(27, 64, 183),new Color(24, 59, 167),new Color(21, 53, 150),new Color(19, 48, 134),new Color(16, 42, 118),new Color(13, 37, 101),new Color(10, 31, 85),new Color(8, 26, 69),new Color(5, 20, 52),new Color(2, 15, 36),new Color(0, 10, 20),};
  private double color_intensity;

     public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

        /*int[][] colors = {
            {12,  0,   10,  20},
            {12,  50, 100, 240},
            {12,  20,   3,  26},
            {12, 230,  60,  20},
            {12,  25,  10,   9},
            {12, 230, 170,   0},
            {12,  20,  40,  10},
            {12,   0, 100,   0},
            {12,   5,  10,  10},
            {12, 210,  70,  30},
            {12,  90,   0,  50},
            {12, 180,  90, 120},
            {12,   0,  20,  40},
            {12,  30,  70, 200},};*/
  
        /*int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        System.out.print("{");
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            for (int j = 0; j < c1[0]; j++)  {// linear interpolation of RGB values
                palette[n + j] = new Color((c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
                System.out.print("new Color(" +palette[n + j].getRed() + ", " + palette[n + j].getGreen() + ", " + palette[n + j].getBlue() + "),");
            }
            n += c1[0];
        }
        System.out.print("}");*/

        this.color_intensity = color_intensity;

    }

    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients,  double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Default(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double [] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Default(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);
      
        this.color_intensity = color_intensity;

    }

    public Default(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);

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
