


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
public class Alternative2 extends ThreadDraw {
  private static Color[] palette = {new Color(0, 0, 191),new Color(8, 0, 195),new Color(17, 0, 200),new Color(26, 0, 204),new Color(35, 0, 209),new Color(43, 0, 213),new Color(52, 0, 218),new Color(61, 0, 223),new Color(70, 0, 227),new Color(79, 0, 232),new Color(87, 0, 236),new Color(96, 0, 241),new Color(105, 0, 245),new Color(114, 0, 250),new Color(123, 0, 255),new Color(123, 0, 255),new Color(129, 0, 238),new Color(135, 0, 221),new Color(141, 0, 205),new Color(147, 0, 188),new Color(153, 0, 172),new Color(159, 0, 155),new Color(165, 0, 139),new Color(165, 0, 139),new Color(174, 21, 111),new Color(184, 43, 83),new Color(194, 65, 55),new Color(204, 87, 27),new Color(214, 109, 0),new Color(214, 109, 0),new Color(224, 131, 0),new Color(234, 154, 0),new Color(244, 177, 0),new Color(255, 200, 0),new Color(255, 200, 0),new Color(252, 213, 0),new Color(249, 227, 0),new Color(246, 241, 0),new Color(243, 255, 0),new Color(243, 255, 0),new Color(238, 226, 0),new Color(233, 198, 0),new Color(229, 170, 0),new Color(224, 141, 0),new Color(219, 113, 0),new Color(215, 85, 0),new Color(210, 56, 0),new Color(205, 28, 0),new Color(201, 0, 0),new Color(201, 0, 0),new Color(196, 0, 18),new Color(191, 0, 37),new Color(186, 0, 56),new Color(181, 0, 74),new Color(176, 0, 93),new Color(172, 0, 112),new Color(172, 0, 112),new Color(176, 0, 130),new Color(181, 0, 148),new Color(185, 0, 166),new Color(190, 0, 184),new Color(194, 0, 202),new Color(199, 0, 220),new Color(204, 0, 239),new Color(204, 0, 239),new Color(205, 2, 243),new Color(206, 5, 247),new Color(207, 8, 251),new Color(208, 11, 255),new Color(208, 11, 255),new Color(212, 22, 249),new Color(216, 34, 243),new Color(220, 45, 238),new Color(225, 57, 232),new Color(229, 68, 226),new Color(233, 80, 221),new Color(237, 91, 215),new Color(242, 103, 209),new Color(246, 114, 204),new Color(250, 126, 198),new Color(255, 138, 193),new Color(255, 138, 193),new Color(241, 146, 189),new Color(227, 154, 185),new Color(213, 162, 181),new Color(200, 170, 178),new Color(200, 170, 178),new Color(179, 182, 183),new Color(158, 194, 189),new Color(137, 206, 194),new Color(117, 218, 200),new Color(96, 230, 205),new Color(75, 242, 211),new Color(55, 255, 217),new Color(55, 255, 217),new Color(41, 242, 220),new Color(27, 230, 224),new Color(13, 218, 227),new Color(0, 206, 231),new Color(0, 206, 231),new Color(0, 185, 237),new Color(0, 165, 243),new Color(0, 144, 249),new Color(0, 124, 255),new Color(0, 124, 255),new Color(0, 104, 238),new Color(0, 84, 222),new Color(0, 65, 205),new Color(0, 45, 189),new Color(0, 26, 173),new Color(0, 26, 173),new Color(0, 37, 157),new Color(0, 48, 142),new Color(0, 59, 127),new Color(0, 71, 112),new Color(0, 82, 96),new Color(0, 93, 81),new Color(0, 104, 66),new Color(0, 116, 51),new Color(0, 116, 51),new Color(0, 126, 66),new Color(0, 137, 82),new Color(0, 148, 98),new Color(0, 159, 114),new Color(0, 170, 130),new Color(0, 180, 146),new Color(0, 191, 162),new Color(0, 202, 178),new Color(0, 213, 194),new Color(0, 224, 210),new Color(0, 235, 226),new Color(0, 235, 226),new Color(7, 240, 233),new Color(15, 245, 240),new Color(22, 250, 247),new Color(30, 255, 255),new Color(30, 255, 255),new Color(47, 244, 255),new Color(64, 233, 255),new Color(82, 223, 255),new Color(99, 212, 255),new Color(116, 201, 255),new Color(134, 191, 255),new Color(134, 191, 255),new Color(151, 190, 255),new Color(168, 189, 255),new Color(185, 188, 255),new Color(202, 188, 255),new Color(219, 187, 255),new Color(236, 186, 255),new Color(254, 186, 255),new Color(254, 186, 255),new Color(251, 185, 255),new Color(248, 185, 255),new Color(245, 185, 255),new Color(242, 185, 255),new Color(242, 185, 255),new Color(240, 184, 252),new Color(238, 184, 249),new Color(236, 184, 246),new Color(235, 184, 244),new Color(235, 184, 244),new Color(228, 175, 232),new Color(221, 166, 220),new Color(215, 157, 208),new Color(208, 148, 197),new Color(202, 139, 185),new Color(195, 130, 173),new Color(189, 121, 162),new Color(182, 113, 150),new Color(175, 104, 138),new Color(169, 95, 127),new Color(162, 86, 115),new Color(156, 77, 103),new Color(149, 68, 92),new Color(143, 59, 80),new Color(136, 50, 68),new Color(130, 42, 57),new Color(130, 42, 57),new Color(122, 31, 71),new Color(114, 21, 86),new Color(106, 10, 101),new Color(99, 0, 116),new Color(99, 0, 116),new Color(92, 9, 129),new Color(85, 18, 142),new Color(78, 27, 156),new Color(71, 36, 169),new Color(64, 45, 183),new Color(57, 54, 196),new Color(50, 64, 210),new Color(50, 64, 210),new Color(42, 73, 203),new Color(35, 82, 197),new Color(28, 91, 191),new Color(21, 100, 185),new Color(14, 109, 179),new Color(7, 118, 173),new Color(0, 128, 167),new Color(0, 128, 167),new Color(5, 136, 161),new Color(11, 145, 155),new Color(17, 153, 149),new Color(23, 162, 143),new Color(29, 171, 137),new Color(34, 179, 131),new Color(40, 188, 125),new Color(46, 197, 119),new Color(52, 205, 113),new Color(58, 214, 107),new Color(64, 223, 102),new Color(64, 223, 102),new Color(71, 233, 113),new Color(78, 244, 125),new Color(86, 255, 137),new Color(86, 255, 137),new Color(92, 247, 147),new Color(98, 239, 157),new Color(104, 231, 167),new Color(110, 223, 177),new Color(116, 215, 187),new Color(122, 207, 197),new Color(128, 199, 207),new Color(134, 191, 217),new Color(134, 191, 217),new Color(139, 183, 210),new Color(145, 175, 204),new Color(151, 168, 197),new Color(157, 160, 191),new Color(163, 152, 184),new Color(169, 145, 178),new Color(174, 137, 172),new Color(180, 129, 165),new Color(186, 122, 159),new Color(192, 114, 152),new Color(198, 106, 146),new Color(204, 99, 140),new Color(204, 99, 140),new Color(186, 90, 133),new Color(169, 82, 126),new Color(151, 73, 119),new Color(134, 65, 112),new Color(117, 57, 105),new Color(117, 57, 105),new Color(104, 50, 114),new Color(91, 44, 124),new Color(78, 38, 133),new Color(65, 31, 143),new Color(52, 25, 152),new Color(39, 19, 162),new Color(26, 12, 171),new Color(13, 6, 181),new Color(0, 0, 191),};
  private double color_intensity;

     public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

        /*int[][] colors = {
            {10,  70,   0,  20},
            {10, 100,   0, 100},
            {14, 255,   0,   0},
            {10, 255, 200,   0},};*/
        
        
        /*int[][] colors = {
            {15,  0,   0,  191},
            {8,  123, 0, 255},
            {6,  165,   0,  139},
            {5, 214,  109,  0},
            {5,  255,  200,   0},
            {10, 243, 255,   0},
            {7,  201,  0,  0},
            {8,  172, 0,   112},
            {5,  204,  0,  239},
            {12, 208,  11,  255},
            {5,  255, 138,  193},
            {8, 200,  170, 178},
            {5,  55,   255,  217},
            {5,  0, 206, 231},
            {6,  0,   124,  255},
            {9, 0,  26,  173},
            {12,  0,  116,   51},
            {5, 0, 235,   226},
            {7,  30,  255,  255},
            {8,   134, 191,   255},
            {5,   254,  186,  255},
            {5, 242,  185,  255},
            {17,  235,   184,  244},
            {5, 130,  42, 57},
            {8,  99,   0,  116},
            {8,  50, 64, 210},
            {12,  0,   128,  167},
            {4, 64,  223,  102},
            {9,  86,  255,   137},
            {13, 134, 191,   217},
            {6,  204,  99,  140},
            {10,   117, 57,   105},};*/
        
        

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }
    
    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, double color_intensity, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, double color_intensity, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, double color_intensity, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

        this.color_intensity = color_intensity;

    }

    public Alternative2(int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, double color_intensity, boolean[] filters) {

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

