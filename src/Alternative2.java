


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
  private static Color[] palette = {new Color(0, 0, 191),new Color(8, 0, 195),new Color(16, 0, 199),new Color(24, 0, 203),new Color(32, 0, 208),new Color(41, 0, 212),new Color(49, 0, 216),new Color(57, 0, 220),new Color(65, 0, 225),new Color(73, 0, 229),new Color(82, 0, 233),new Color(90, 0, 237),new Color(98, 0, 242),new Color(106, 0, 246),new Color(114, 0, 250),new Color(122, 0, 255),new Color(123, 0, 255),new Color(128, 0, 240),new Color(133, 0, 226),new Color(138, 0, 211),new Color(144, 0, 197),new Color(149, 0, 182),new Color(154, 0, 168),new Color(159, 0, 153),new Color(165, 0, 139),new Color(173, 18, 115),new Color(181, 36, 92),new Color(189, 54, 69),new Color(197, 72, 46),new Color(205, 90, 23),new Color(214, 108, 0),new Color(214, 109, 0),new Color(222, 127, 0),new Color(230, 145, 0),new Color(238, 163, 0),new Color(246, 181, 0),new Color(255, 200, 0),new Color(252, 211, 0),new Color(250, 222, 0),new Color(247, 233, 0),new Color(245, 244, 0),new Color(243, 255, 0),new Color(238, 229, 0),new Color(234, 204, 0),new Color(230, 178, 0),new Color(226, 153, 0),new Color(222, 127, 0),new Color(217, 102, 0),new Color(213, 76, 0),new Color(209, 51, 0),new Color(205, 25, 0),new Color(201, 0, 0),new Color(201, 0, 0),new Color(196, 0, 16),new Color(192, 0, 32),new Color(188, 0, 48),new Color(184, 0, 64),new Color(180, 0, 79),new Color(176, 0, 95),new Color(172, 0, 111),new Color(172, 0, 112),new Color(176, 0, 127),new Color(180, 0, 143),new Color(184, 0, 159),new Color(188, 0, 175),new Color(192, 0, 191),new Color(196, 0, 207),new Color(200, 0, 223),new Color(204, 0, 239),new Color(204, 2, 242),new Color(205, 4, 245),new Color(206, 6, 248),new Color(207, 8, 251),new Color(208, 11, 255),new Color(211, 21, 249),new Color(215, 32, 244),new Color(219, 42, 239),new Color(223, 53, 234),new Color(227, 63, 229),new Color(231, 74, 224),new Color(235, 85, 218),new Color(239, 95, 213),new Color(243, 106, 208),new Color(247, 116, 203),new Color(251, 127, 198),new Color(255, 138, 193),new Color(244, 144, 190),new Color(233, 150, 187),new Color(222, 157, 184),new Color(211, 163, 181),new Color(200, 170, 178),new Color(181, 180, 182),new Color(163, 191, 187),new Color(145, 201, 192),new Color(127, 212, 197),new Color(109, 223, 202),new Color(91, 233, 207),new Color(73, 244, 212),new Color(55, 255, 217),new Color(44, 245, 219),new Color(33, 235, 222),new Color(21, 225, 225),new Color(11, 215, 228),new Color(0, 206, 231),new Color(0, 189, 235),new Color(0, 173, 240),new Color(0, 156, 245),new Color(0, 140, 250),new Color(0, 124, 255),new Color(0, 107, 241),new Color(0, 91, 227),new Color(0, 75, 214),new Color(0, 58, 200),new Color(0, 42, 186),new Color(0, 26, 173),new Color(0, 26, 173),new Color(0, 36, 159),new Color(0, 46, 145),new Color(0, 56, 132),new Color(0, 66, 118),new Color(0, 76, 105),new Color(0, 86, 91),new Color(0, 96, 78),new Color(0, 106, 64),new Color(0, 116, 51),new Color(0, 125, 65),new Color(0, 135, 80),new Color(0, 145, 94),new Color(0, 155, 109),new Color(0, 165, 123),new Color(0, 175, 138),new Color(0, 185, 153),new Color(0, 195, 167),new Color(0, 205, 182),new Color(0, 215, 196),new Color(0, 225, 211),new Color(0, 235, 226),new Color(6, 239, 231),new Color(12, 243, 237),new Color(18, 247, 243),new Color(24, 251, 249),new Color(30, 255, 255),new Color(44, 245, 255),new Color(59, 236, 255),new Color(74, 227, 255),new Color(89, 218, 255),new Color(104, 209, 255),new Color(119, 200, 255),new Color(133, 191, 255),new Color(134, 191, 255),new Color(149, 190, 255),new Color(164, 189, 255),new Color(179, 189, 255),new Color(194, 188, 255),new Color(209, 187, 255),new Color(224, 187, 255),new Color(239, 186, 255),new Color(254, 186, 255),new Color(251, 185, 255),new Color(249, 185, 255),new Color(246, 185, 255),new Color(244, 185, 255),new Color(242, 185, 255),new Color(240, 184, 252),new Color(239, 184, 250),new Color(237, 184, 248),new Color(236, 184, 246),new Color(235, 184, 244),new Color(228, 175, 233),new Color(222, 167, 222),new Color(216, 158, 211),new Color(210, 150, 200),new Color(204, 142, 189),new Color(197, 133, 178),new Color(191, 125, 167),new Color(185, 117, 156),new Color(179, 108, 145),new Color(173, 100, 134),new Color(167, 92, 123),new Color(160, 83, 112),new Color(154, 75, 101),new Color(148, 67, 90),new Color(142, 58, 78),new Color(136, 50, 67),new Color(130, 42, 57),new Color(123, 33, 68),new Color(117, 25, 80),new Color(111, 16, 92),new Color(105, 8, 104),new Color(99, 0, 116),new Color(92, 8, 127),new Color(86, 16, 139),new Color(80, 24, 151),new Color(74, 32, 163),new Color(68, 40, 174),new Color(62, 48, 186),new Color(56, 56, 198),new Color(50, 64, 210),new Color(43, 72, 204),new Color(37, 80, 199),new Color(31, 88, 193),new Color(25, 96, 188),new Color(18, 104, 183),new Color(12, 112, 177),new Color(6, 120, 172),new Color(0, 128, 167),new Color(5, 135, 161),new Color(10, 143, 156),new Color(16, 151, 150),new Color(21, 159, 145),new Color(26, 167, 139),new Color(31, 175, 134),new Color(37, 183, 129),new Color(42, 191, 123),new Color(48, 199, 118),new Color(53, 207, 112),new Color(58, 215, 107),new Color(64, 223, 102),new Color(69, 231, 110),new Color(75, 239, 119),new Color(80, 247, 128),new Color(86, 255, 137),new Color(91, 247, 145),new Color(96, 240, 154),new Color(102, 233, 163),new Color(107, 226, 172),new Color(112, 219, 181),new Color(118, 212, 190),new Color(123, 205, 199),new Color(128, 198, 208),new Color(134, 191, 217),new Color(139, 183, 211),new Color(144, 176, 205),new Color(150, 169, 199),new Color(155, 162, 193),new Color(160, 155, 187),new Color(166, 148, 181),new Color(171, 141, 175),new Color(177, 134, 169),new Color(182, 127, 163),new Color(187, 120, 157),new Color(193, 113, 151),new Color(198, 106, 145),new Color(204, 99, 140),new Color(204, 99, 140),new Color(189, 92, 134),new Color(175, 85, 128),new Color(160, 78, 122),new Color(146, 71, 116),new Color(131, 64, 110),new Color(117, 57, 105),new Color(117, 57, 105),new Color(105, 51, 113),new Color(93, 45, 122),new Color(81, 39, 130),new Color(70, 34, 139),new Color(58, 28, 148),new Color(46, 22, 156),new Color(35, 17, 165),new Color(23, 11, 173),new Color(11, 5, 182),new Color(0, 0, 191),};
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

