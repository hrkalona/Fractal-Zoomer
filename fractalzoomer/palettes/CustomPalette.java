package fractalzoomer.palettes;


import fractalzoomer.main.MainWindow;
import fractalzoomer.core.ThreadDraw;
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
public class CustomPalette extends ThreadDraw {
    
     public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method);

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                //System.out.println("{" + custom_palette[i][0] + ", " + custom_palette[i][1] + ", " + custom_palette[i][2] + ", " + custom_palette[i][3] + "},");
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        //System.out.print("{");
        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
            //if(c1[0] == 1) {
              //  palette[n] = new Color(c1[1], c1[2], c1[3]);   
            //} 
           // else {
                int k;
                double j;
                for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                    //(c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
                    red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                    green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                    blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                    //System.out.println(red + " " + green + " " + blue);
                    //System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");
                    //System.out.print((0xff000000 | (red << 16) | (green << 8) | blue) + ", ");
                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;        
               }
            //}
                
            n += c1[0];
        }
        //System.out.print("}");
        
        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, coefficients, z_exponent_nova, relaxation, nova_method, xJuliaCenter, yJuliaCenter);

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }
            n += c1[0];
        }


        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }
    
    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout,  ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, coefficients, z_exponent_nova, relaxation, nova_method);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }
            n += c1[0];
        }
        
        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, in_coloring_algorithm, smoothing, filters, filters_options_vals, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, coefficients, z_exponent_nova, relaxation, nova_method, xJuliaCenter, yJuliaCenter);

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }
            n += c1[0];
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, boolean smoothing, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, color_cycling_location);

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }
            n += c1[0];
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean smoothing, boolean[] filters, int[] filters_options_vals) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, image, fractal_color, color_cycling_location, filters, filters_options_vals);
        
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {// linear interpolation of RGB values
                red = (int)(c1[1] + (c2[1] - c1[1]) * j);
                green = (int)(c1[2] + (c2[2] - c1[2]) * j);
                blue = (int)(c1[3] + (c2[3] - c1[3]) * j);
                palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
            }
            n += c1[0];
        }
        
        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette);
        }
        else {
            palette_color = new PaletteColorSmooth(palette);
        }

    }
    
    
     public static Color[] getPalette(int[][] custom_palette) {

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
            if(custom_palette[i][0] != 0) {
                colors[j] = custom_palette[i];
                j++;
            }
        }
        Color[] palette = new Color[n]; // allocate pallete

        n = 0;
        int red, green, blue;
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
            }
            n += c1[0];
        }

        return palette;

    }
 

}