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
    
     public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, boolean perturbation, double[] perturbation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, perturbation, perturbation_vals, coefficients);

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
        Color[] palette = new Color[n]; // allocate pallete

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
                   
                    palette[n + k] = new Color(red, green, blue);        
               }
            //}
            n += c1[0];
        }

        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, boundary_tracing, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

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


        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
        }

    }
    
    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int out_coloring_algorithm, int in_coloring_algorithm, boolean periodicity_checking, int plane_type,  boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout,  ptr, fractal_color, image, filters, out_coloring_algorithm, in_coloring_algorithm, periodicity_checking, plane_type,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients);
        
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
        
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters,  int out_coloring_algorithm, int in_coloring_algorithm, boolean burning_ship, int function, double z_exponent, int color_cycling_location, double[] rotation_vals, double[] coefficients, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, in_coloring_algorithm, filters,  burning_ship, function, z_exponent, color_cycling_location, rotation_vals, coefficients, xJuliaCenter, yJuliaCenter);

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

        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, int out_coloring_algorithm, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, out_coloring_algorithm, color_cycling_location);

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

        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
        }

    }

    public CustomPalette(int[][] custom_palette, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, int out_coloring_algorithm, boolean[] filters) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, image, fractal_color, out_coloring_algorithm, color_cycling_location, filters);
        
        
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
        
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            palette_color = new PaletteColorSmooth(palette);
        }
        else {
            palette_color = new PaletteColorNormal(palette);
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
