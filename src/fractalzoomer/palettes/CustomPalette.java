package fractalzoomer.palettes;


import fractalzoomer.main.MainWindow;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.utils.ColorSpaceConverter;
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
    
     public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, double n_norm, boolean d3, int d3_draw_method, int detail, double fiX, double fiY, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula,  String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, coefficients, z_exponent_nova, relaxation, nova_method, user_formula,  user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    //System.out.print("{" + custom_palette[i][0] + ", " + custom_palette[i][1] + ", " + custom_palette[i][2] + ", " + custom_palette[i][3] + "},");
                    colors[j] = custom_palette[i];
                    j++;
                }
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
                for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                    //(c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
                    double coef = j;
                
                    switch(color_interpolation) {
                        case MainWindow.INTERPOLATION_LINEAR:
                            coef = j; // linear
                            break;
                        case MainWindow.INTERPOLATION_COSINE:
                            coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                            break;
                        //case 2:
                            //coef = j * j * (3 - 2 * j); // smooth step
                            //break;
                        case MainWindow.INTERPOLATION_ACCELERATION:
                            coef = j * j; // acceleration
                            break;
                        case MainWindow.INTERPOLATION_DECELERATION:
                            coef = 1 - (1 - j) * (1 - j); // deceleration
                            break;
                        case 5:
                            coef = j < 0.5 ? 0 : 1; // step
                            break;
                    }

                    if(color_space == MainWindow.COLOR_SPACE_HSB) {
                        ColorSpaceConverter con = new ColorSpaceConverter();

                        double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                        double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);

                        double h;
                        double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                        double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                        double d = c2_hsb[0] - c1_hsb[0];


                        double temp;
                        if (c1_hsb[0] > c2_hsb[0])  {
                            temp = c1_hsb[0];
                            c1_hsb[0] = c2_hsb[0];
                            c2_hsb[0] = temp;
                            d = -d;
                            coef = 1 - coef;
                        }

                        if(d > 0.5)  {                      
                           c1_hsb[0] += 1; 
                           h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                        } 
                        else {
                           h = ((c1_hsb[0] + coef * d));
                        }

                        int [] res = con.HSBtoRGB(h, s, b);

                        palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                        red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                        green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                        blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                        palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                        //System.out.print((0xff000000 | (red << 16) | (green << 8) | blue) + ", ");
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                         double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                         double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                         double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];

                         double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                         double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                         double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];

                         double to_r = Math.log(c2_1);
                         double from_r = Math.log(c1_1);

                         double to_g = Math.log(c2_2);
                         double from_g = Math.log(c1_2);

                         double to_b = Math.log(c2_3);
                         double from_b = Math.log(c1_3);
                        
                        red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                        green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                        blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                        palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                        double to_r = Math.sqrt(c2[1]);
                        double from_r = Math.sqrt(c1[1]);
                        
                        double to_g = Math.sqrt(c2[2]);
                        double from_g = Math.sqrt(c1[2]);
                        
                        double to_b = Math.sqrt(c2[3]);
                        double from_b = Math.sqrt(c1[3]);
                        
                        red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                        green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                        blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                        palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                        double to_r = c2[1] * c2[1];
                        double from_r = c1[1] * c1[1];
                        
                        double to_g = c2[2] * c2[2];
                        double from_g = c1[2] * c1[2];
                        
                        double to_b = c2[3] * c2[3];
                        double from_b = c1[3] * c1[3];
                        
                        red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                        green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                        blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                        palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                        ColorSpaceConverter con = new ColorSpaceConverter();
                    
                        double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                        double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);

                        double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                        double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                        double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);

                        int[] rgb = con.RYBtoRGB(r, y, b);

                        palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                        ColorSpaceConverter con = new ColorSpaceConverter();

                        double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                        double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);


                        double L = (from[0] + (to[0] - from[0]) * coef);
                        double a = (from[1] + (to[1] - from[1]) * coef);
                        double b = (from[2] + (to[2] - from[2]) * coef);

                        int [] res = con.LABtoRGB(L, a, b);

                        res[0] = clamp(res[0]);
                        res[1] = clamp(res[1]);
                        res[2] = clamp(res[2]);

                        palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                    }               
                    else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                        ColorSpaceConverter con = new ColorSpaceConverter();

                        double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                        double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);


                        double X = (from[0] + (to[0] - from[0]) * coef);
                        double Y = (from[1] + (to[1] - from[1]) * coef);
                        double Z = (from[2] + (to[2] - from[2]) * coef);

                        int [] res = con.XYZtoRGB(X, Y, Z);

                        res[0] = clamp(res[0]);
                        res[1] = clamp(res[1]);
                        res[2] = clamp(res[2]);

                        palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                    }
                    else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                        ColorSpaceConverter con = new ColorSpaceConverter();

                        double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                        double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                        double L = (from[0] + (to[0] - from[0]) * coef);
                        double C = (from[1] + (to[1] - from[1]) * coef);
                        double H;

                        double d = to[2] - from[2];

                        double temp;
                        if (from[2] > to[2])  {
                            temp = from[2];
                            from[2] = to[2];
                            to[2] = temp;
                            d = -d;
                            coef = 1 - coef;
                        }

                        if(d > 180)  {
                           from[2] += 360; 
                           H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                        } 
                        else {
                           H = ((from[2] + coef * d));
                        }

                        int [] res = con.LCHtoRGB(L, C, H);

                        res[0] = clamp(res[0]);
                        res[1] = clamp(res[1]);
                        res[2] = clamp(res[2]);

                        palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                    }
                    //System.out.println(red + " " + green + " " + blue);
                    //System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");
                    
       
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

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm,  double bailout, double n_norm, boolean d3, int d3_draw_method, int detail, double fiX, double fiY, MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula,  String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm,  bailout, n_norm, d3, d3_draw_method, detail, fiX, fiY, ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, boundary_tracing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula,  user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, xJuliaCenter, yJuliaCenter);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
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
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);

                    palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {                   
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                
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
    
    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm,  MainWindow ptr, Color fractal_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean periodicity_checking, int plane_type,  boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula,  String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm,  ptr, fractal_color, image, filters, filters_options_vals, out_coloring_algorithm, in_coloring_algorithm, smoothing, periodicity_checking, plane_type,  burning_ship, mandel_grass, mandel_grass_vals,  function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula,  user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
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
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);

                    palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }             
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
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

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, double n_norm, MainWindow ptr, Color fractal_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula,  String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, n_norm, ptr, fractal_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, out_coloring_algorithm, in_coloring_algorithm, smoothing, filters, filters_options_vals, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula,  user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, xJuliaCenter, yJuliaCenter);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
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
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);

                    palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
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

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, Color fractal_color, boolean smoothing, BufferedImage image, int color_cycling_location) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, fractal_color, image, color_cycling_location);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
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
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);

                    palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
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

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int FROMx, int TOx, int FROMy, int TOy, int max_iterations,  MainWindow ptr, BufferedImage image, Color fractal_color, int color_cycling_location, boolean smoothing, boolean[] filters, int[] filters_options_vals) {

        super(FROMx, TOx, FROMy, TOy, max_iterations,  ptr, image, fractal_color, color_cycling_location, filters, filters_options_vals);
        
        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
        
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
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
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);

                    palette[n + k] = 0xff000000 | (res[0] << 16) | (res[1] << 8) | res[2];
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {         
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));

                     palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[n + k] = 0xff000000 | (red << 16) | (green << 8) | blue;
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[n + k] = 0xff000000 | ((int)(rgb[0]) << 16) | ((int)(rgb[1]) << 8) | (int)(rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
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
    
     public CustomPalette(int FROMx, int TOx, int FROMy, int TOy, int detail, double fiX, double fiY, int d3_draw_method,  MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals) {
        
        super(FROMx, TOx, FROMy, TOy, detail, fiX, fiY, d3_draw_method, ptr, image, filters, filters_options_vals);
        
    }
      
     public static Color[] getPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int color_cycling_location) {

        int n = 0, counter = 0;
        for (int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }
        
        int[][] colors = new int[counter][4];
         
        if(reverse) {
            for (int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for (int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        
        Color[] palette = new Color[n]; // allocate pallete
        
       

        n = n - color_cycling_location % n;
        
        int red, green, blue;
        for (int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color
            
           
            int k;
            double j;
            for (k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++)  {
                double coef = j;
                
                switch(color_interpolation) {
                    case MainWindow.INTERPOLATION_LINEAR:
                        coef = j; // linear
                        break;
                    case MainWindow.INTERPOLATION_COSINE:
                        coef = -Math.cos(Math.PI * j) / 2 + 0.5; // cosine
                        break;
                    //case 2:
                        //coef = j * j * (3 - 2 * j); // smooth step
                        //break;
                    case MainWindow.INTERPOLATION_ACCELERATION:
                        coef = j * j; // acceleration
                        break;
                    case MainWindow.INTERPOLATION_DECELERATION:
                        coef = 1 - (1 - j) * (1 - j); // deceleration
                        break;
                    case 5:
                        coef = j < 0.5 ? 0 : 1; // step
                        break;
                }
                
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);
                    
                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];
  

                    double temp;
                    if (c1_hsb[0] > c2_hsb[0])  {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5)  {                      
                       c1_hsb[0] += 1; 
                       h = ((c1_hsb[0] + (c2_hsb[0] -  c1_hsb[0]) * coef)) % 1;
                    } 
                    else {
                       h = ((c1_hsb[0] + coef * d));
                    }
                    
                    int [] res = con.HSBtoRGB(h, s, b);
                          
                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_RGB) {    
                     red = (int)(c1[1] + (c2[1] - c1[1]) * coef);
                     green = (int)(c1[2] + (c2[2] - c1[2]) * coef);
                     blue = (int)(c1[3] + (c2[3] - c1[3]) * coef);

                     palette[(n + k) % palette.length] = new Color(red, green, blue);
                }
                else if(color_space == MainWindow.COLOR_SPACE_EXP) {
                     double c2_1 = c2[1] == 0 ? c2[1] + 1 : c2[1];
                     double c2_2 = c2[2] == 0 ? c2[2] + 1 : c2[2];
                     double c2_3 = c2[3] == 0 ? c2[3] + 1 : c2[3];
                     
                     double c1_1 = c1[1] == 0 ? c1[1] + 1 : c1[1];
                     double c1_2 = c1[2] == 0 ? c1[2] + 1 : c1[2];
                     double c1_3 = c1[3] == 0 ? c1[3] + 1 : c1[3];
                     
                     double to_r = Math.log(c2_1);
                     double from_r = Math.log(c1_1);
                        
                     double to_g = Math.log(c2_2);
                     double from_g = Math.log(c1_2);
                        
                     double to_b = Math.log(c2_3);
                     double from_b = Math.log(c1_3);
                        
                     red = (int)(Math.exp(from_r + (to_r - from_r) * coef));
                     green = (int)(Math.exp(from_g + (to_g - from_g) * coef));
                     blue = (int)(Math.exp(from_b + (to_b - from_b) * coef));
                    
                     palette[(n + k) % palette.length] = new Color(red, green, blue);
                     
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQUARE) {
                    double to_r = Math.sqrt(c2[1]);
                    double from_r = Math.sqrt(c1[1]);
                        
                    double to_g = Math.sqrt(c2[2]);
                    double from_g = Math.sqrt(c1[2]);
                        
                    double to_b = Math.sqrt(c2[3]);
                    double from_b = Math.sqrt(c1[3]);
                        
                    red = (int)(Math.pow(from_r + (to_r - from_r) * coef, 2));
                    green = (int)(Math.pow(from_g + (to_g - from_g) * coef, 2));
                    blue = (int)(Math.pow(from_b + (to_b - from_b) * coef, 2));

                    palette[(n + k) % palette.length] = new Color(red, green, blue);
                }
                else if(color_space == MainWindow.COLOR_SPACE_SQRT) {
                    double to_r = c2[1] * c2[1];
                    double from_r = c1[1] * c1[1];
                        
                    double to_g = c2[2] * c2[2];
                    double from_g = c1[2] * c1[2];
                        
                    double to_b = c2[3] * c2[3];
                    double from_b = c1[3] * c1[3];
                        
                    red = (int)(Math.sqrt(from_r + (to_r - from_r) * coef));
                    green = (int)(Math.sqrt(from_g + (to_g - from_g) * coef));
                    blue = (int)(Math.sqrt(from_b + (to_b - from_b) * coef));

                    palette[(n + k) % palette.length] = new Color(red, green, blue);
                }
                else if(color_space == MainWindow.COLOR_SPACE_RYB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                    
                    double[] ryb_from = con.RGBtoRYB(c1[1], c1[2], c1[3]);
                    double[] ryb_to = con.RGBtoRYB(c2[1], c2[2], c2[3]);
                    
                    double r = (ryb_from[0] + (ryb_to[0] - ryb_from[0]) * coef);
                    double y = (ryb_from[1] + (ryb_to[1] - ryb_from[1]) * coef);
                    double b = (ryb_from[2] + (ryb_to[2] - ryb_from[2]) * coef);
                    
                    int[] rgb = con.RYBtoRGB(r, y, b);
                    
                    palette[(n + k) % palette.length] = new Color(rgb[0], rgb[1], rgb[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LAB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);
                     
           
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.LABtoRGB(L, a, b);
                    
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);

                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }               
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);
                     
           
                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);
              
                    int [] res = con.XYZtoRGB(X, Y, Z);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();
                     
                    double [] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);
                     
                    double [] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);
                               
                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;
                    
                    double d = to[2] - from[2];

                    double temp;
                    if (from[2] > to[2])  {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180)  {
                       from[2] += 360; 
                       H = ((from[2] + (to[2] -  from[2]) * coef)) % 360.0;                   
                    } 
                    else {
                       H = ((from[2] + coef * d));
                    }
              
                    int [] res = con.LCHtoRGB(L, C, H);
                                       
                    res[0] = clamp(res[0]);
                    res[1] = clamp(res[1]);
                    res[2] = clamp(res[2]);
                          
                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }  
            }
            
            n += c1[0];
        }


        return palette;

    }
     
    private static int clamp(int c) {
        
        if(c < 0) {
            return 0;
        }
	if(c > 255) {
            return 255;
        }
	return c;
                
    }
 

}
