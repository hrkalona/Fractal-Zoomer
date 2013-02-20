

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
  private static Color[] palette = {new Color(0, 10, 20),new Color(4, 17, 38),new Color(8, 25, 56),new Color(12, 32, 75),new Color(16, 40, 93),new Color(20, 47, 111),new Color(24, 54, 130),new Color(29, 62, 148),new Color(33, 70, 166),new Color(37, 77, 185),new Color(41, 85, 203),new Color(45, 92, 221),new Color(50, 100, 240),new Color(47, 91, 222),new Color(45, 83, 204),new Color(42, 75, 186),new Color(40, 67, 168),new Color(37, 59, 150),new Color(35, 51, 133),new Color(32, 43, 115),new Color(30, 35, 97),new Color(27, 27, 79),new Color(25, 19, 61),new Color(22, 11, 43),new Color(20, 3, 26),new Color(37, 7, 25),new Color(55, 12, 25),new Color(72, 17, 24),new Color(90, 22, 24),new Color(107, 26, 23),new Color(124, 31, 23),new Color(142, 36, 22),new Color(160, 41, 22),new Color(177, 45, 21),new Color(195, 50, 21),new Color(212, 55, 20),new Color(230, 60, 20),new Color(212, 55, 19),new Color(195, 51, 18),new Color(178, 47, 17),new Color(161, 43, 16),new Color(144, 39, 15),new Color(127, 35, 14),new Color(110, 30, 13),new Color(93, 26, 12),new Color(76, 22, 11),new Color(59, 18, 10),new Color(42, 14, 9),new Color(25, 10, 9),new Color(42, 23, 8),new Color(59, 36, 7),new Color(76, 50, 6),new Color(93, 63, 6),new Color(110, 76, 5),new Color(127, 89, 4),new Color(144, 103, 3),new Color(161, 116, 3),new Color(178, 130, 2),new Color(195, 143, 1),new Color(212, 156, 0),new Color(230, 170, 0),new Color(212, 159, 0),new Color(195, 148, 1),new Color(177, 137, 2),new Color(160, 126, 3),new Color(142, 115, 4),new Color(125, 105, 4),new Color(107, 94, 5),new Color(90, 83, 6),new Color(72, 72, 7),new Color(55, 61, 8),new Color(37, 50, 9),new Color(20, 40, 10),new Color(18, 45, 9),new Color(16, 50, 8),new Color(15, 55, 7),new Color(13, 60, 6),new Color(11, 65, 5),new Color(10, 70, 5),new Color(8, 75, 4),new Color(6, 80, 3),new Color(5, 85, 2),new Color(3, 90, 1),new Color(1, 95, 0),new Color(0, 100, 0),new Color(0, 92, 0),new Color(0, 85, 1),new Color(1, 77, 2),new Color(1, 70, 3),new Color(2, 62, 4),new Color(2, 55, 4),new Color(2, 47, 5),new Color(3, 40, 6),new Color(3, 32, 7),new Color(4, 25, 8),new Color(4, 17, 9),new Color(5, 10, 10),new Color(22, 15, 11),new Color(39, 20, 13),new Color(56, 25, 15),new Color(73, 30, 16),new Color(90, 35, 18),new Color(107, 40, 20),new Color(124, 44, 21),new Color(141, 50, 23),new Color(158, 55, 25),new Color(175, 60, 26),new Color(192, 65, 28),new Color(210, 70, 30),new Color(200, 64, 31),new Color(190, 58, 33),new Color(180, 52, 35),new Color(170, 46, 36),new Color(160, 40, 38),new Color(150, 35, 40),new Color(140, 29, 41),new Color(130, 23, 43),new Color(120, 17, 45),new Color(110, 11, 46),new Color(99, 5, 48),new Color(90, 0, 50),new Color(97, 7, 55),new Color(105, 15, 61),new Color(112, 22, 67),new Color(120, 30, 73),new Color(127, 37, 79),new Color(135, 44, 85),new Color(142, 52, 90),new Color(150, 60, 96),new Color(157, 67, 102),new Color(165, 75, 108),new Color(172, 82, 114),new Color(180, 90, 120),new Color(165, 84, 113),new Color(150, 78, 106),new Color(135, 72, 100),new Color(120, 66, 93),new Color(105, 60, 86),new Color(90, 55, 80),new Color(75, 49, 73),new Color(60, 43, 66),new Color(45, 37, 60),new Color(30, 31, 53),new Color(15, 25, 46),new Color(0, 20, 40),new Color(2, 24, 53),new Color(5, 28, 66),new Color(7, 32, 80),new Color(10, 36, 93),new Color(12, 40, 106),new Color(14, 45, 119),new Color(17, 49, 133),new Color(20, 53, 146),new Color(22, 57, 160),new Color(25, 61, 173),new Color(27, 65, 186),new Color(30, 70, 200),new Color(27, 65, 185),new Color(25, 60, 170),new Color(22, 55, 155),new Color(20, 50, 140),new Color(17, 45, 125),new Color(15, 40, 110),new Color(12, 35, 95),new Color(10, 30, 80),new Color(7, 25, 65),new Color(5, 20, 50),new Color(2, 14, 35),};
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
     

  /*
        int n = 0;
        for (int i = 0; i < colors.length; i++) { // get the number of all colors
            n += colors[i][0];
        }
        palette = new Color[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        System.out.print("{");
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = new Color(red, green, blue);
                System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");
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
