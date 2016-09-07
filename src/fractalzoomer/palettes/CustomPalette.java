/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.palettes;

import fractalzoomer.main.MainWindow;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.utils.ColorSpaceConverter;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class CustomPalette extends ThreadDraw {

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, Color[] filters_colors, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel_size, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, image, filters, filters_options_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, boundary_tracing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    //System.out.print("{" + custom_palette[i][0] + ", " + custom_palette[i][1] + ", " + custom_palette[i][2] + ", " + custom_palette[i][3] + "},");
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        //System.out.print("{");
        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            //if(c1[0] == 1) {
            //  palette[n] = new Color(c1[1], c1[2], c1[3]);   
            //} 
            // else {
            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                //(c1[1] * (c1[0] - 1 - j) + c2[1] * j) / (c1[0] - 1),(c1[2] * (c1[0] - 1 - j) + c2[2] * j) / (c1[0] - 1),(c1[3] * (c1[0] - 1 - j) + c2[3] * j) / (c1[0] - 1));
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                //System.out.println(red + " " + green + " " + blue);
                //System.out.print("new Color(" +red + ", " + green + ", " + blue + "),");

            }
            //}

            n += c1[0];
        }
        //System.out.print("}");

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel_size, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, image, filters, filters_options_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, xJuliaCenter, yJuliaCenter);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }

            }
            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, BufferedImage image, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, filters, filters_options_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
            }
            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, boolean fast_julia_filters, BufferedImage image, boolean boundary_tracing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean[] filters, int[] filters_options_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, boundary_tracing, periodicity_checking, plane_type, apply_plane_on_julia, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, filters, filters_options_vals, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
            }
            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, boolean smoothing, BufferedImage image, int color_cycling_location, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, int color_cycling_speed, boolean[] filters, int[] filters_options_vals, Color[] filters_colors) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, color_cycling_speed, filters, filters_options_vals, filters_colors);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
            }
            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, Color special_color, int color_cycling_location, boolean smoothing, boolean[] filters, int[] filters_options_vals, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, filters, filters_options_vals, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors);

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }

        int[] palette = new int[n]; // allocate pallete

        n = 0;
        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
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
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[n + k] = 0xff000000 | ((int)(res[0]) << 16) | ((int)(res[1]) << 8) | (int)(res[2]);
                }
            }
            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        if(!smoothing) {
            palette_color = new PaletteColorNormal(palette, color_intensity, special_color);
        }
        else {
            palette_color = new PaletteColorSmooth(palette, color_intensity, special_color);
        }

    }

    public CustomPalette(int FROMx, int TOx, int FROMy, int TOy, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean draw_action, MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals, Color[] filters_colors) {

        super(FROMx, TOx, FROMy, TOy, detail, d3_size_scale, fiX, fiY, color_3d_blending, draw_action, ptr, image, filters, filters_options_vals, filters_colors);

    }

    public static Color[] getPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, int color_cycling_location, double scale_factor_palette_val, int processing_alg) {

        int n = 0, counter = 0;
        for(int i = 0; i < custom_palette.length; i++) { // get the number of all colors
            n += custom_palette[i][0];
            if(custom_palette[i][0] != 0) {
                counter++;
            }
        }

        int[][] colors = new int[counter][4];

        if(reverse) {
            for(int i = custom_palette.length - 1, j = 0; i >= 0; i--) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }
        else {
            for(int i = 0, j = 0; i < custom_palette.length; i++) { // get the number of all colors
                if(custom_palette[i][0] != 0) {
                    colors[j] = custom_palette[i];
                    j++;
                }
            }
        }

        if(processing_alg == MainWindow.PROCESSING_BRIGHTNESS1 || processing_alg == MainWindow.PROCESSING_BRIGHTNESS2) {
            cycleBrightness(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HUE1 || processing_alg == MainWindow.PROCESSING_HUE2) {
            cycleHue(colors, scale_factor_palette_val, processing_alg);        
        }
        else if(processing_alg == MainWindow.PROCESSING_SATURATION1 || processing_alg == MainWindow.PROCESSING_SATURATION2) {
            cycleSaturation(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RED1 || processing_alg == MainWindow.PROCESSING_RED2) {
            cycleRed(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_GREEN1 || processing_alg == MainWindow.PROCESSING_GREEN2) {
            cycleGreen(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_BLUE1 || processing_alg == MainWindow.PROCESSING_BLUE2) {
            cycleBlue(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_RGB1 || processing_alg == MainWindow.PROCESSING_RGB2) {
            cycleRGB(colors, scale_factor_palette_val, processing_alg);
        }
        else if(processing_alg == MainWindow.PROCESSING_HSB1 || processing_alg == MainWindow.PROCESSING_HSB2) {
            cycleHSB(colors, scale_factor_palette_val, processing_alg);
        }
        

        Color[] palette = new Color[n]; // allocate pallete

        n = n - color_cycling_location % n;

        int red, green, blue;
        for(int i = 0; i < colors.length; i++) { // interpolate all colors
            int[] c1 = colors[i]; // first referential color
            int[] c2 = colors[(i + 1) % colors.length]; // second ref. color

            int k;
            double j;
            for(k = 0, j = 0; k < c1[0]; j += 1.0 / c1[0], k++) {
                double coef = j;

                switch (color_interpolation) {
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
                    case MainWindow.INTERPOLATION_EXPONENTIAL:
                        coef = 1 - Math.exp(-3.6 * j);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM:
                        coef = catmullrom(j, 0.5, 0, 1, 0.5);
                        break;
                    case MainWindow.INTERPOLATION_CATMULLROM2:
                        coef = catmullrom(j, -3.0, 0, 1, 4.0);
                        break;
                    case MainWindow.INTERPOLATION_SIGMOID:
                        coef = 1 / (1 + Math.exp(-j * 12 + 6));
                        break;
                    //case 5:
                    // coef = j < 0.5 ? 0 : 1; // step
                    //break;
                }

                //double N = 20;
                //coef = ((coef * (N - 1)) + 1) / N; 
                if(color_space == MainWindow.COLOR_SPACE_HSB) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] c1_hsb = con.RGBtoHSB(c1[1], c1[2], c1[3]);
                    double[] c2_hsb = con.RGBtoHSB(c2[1], c2[2], c2[3]);

                    double h;
                    double s = (c1_hsb[1] + (c2_hsb[1] - c1_hsb[1]) * coef);
                    double b = (c1_hsb[2] + (c2_hsb[2] - c1_hsb[2]) * coef);

                    double d = c2_hsb[0] - c1_hsb[0];

                    double temp;
                    if(c1_hsb[0] > c2_hsb[0]) {
                        temp = c1_hsb[0];
                        c1_hsb[0] = c2_hsb[0];
                        c2_hsb[0] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 0.5) {
                        c1_hsb[0] += 1;
                        h = ((c1_hsb[0] + (c2_hsb[0] - c1_hsb[0]) * coef)) % 1;
                    }
                    else {
                        h = ((c1_hsb[0] + coef * d));
                    }

                    int[] res = con.HSBtoRGB(h, s, b);

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

                    double[] from = con.RGBtoLAB(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLAB(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double a = (from[1] + (to[1] - from[1]) * coef);
                    double b = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.LABtoRGB(L, a, b);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_XYZ) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoXYZ(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoXYZ(c2[1], c2[2], c2[3]);

                    double X = (from[0] + (to[0] - from[0]) * coef);
                    double Y = (from[1] + (to[1] - from[1]) * coef);
                    double Z = (from[2] + (to[2] - from[2]) * coef);

                    int[] res = con.XYZtoRGB(X, Y, Z);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }
                else if(color_space == MainWindow.COLOR_SPACE_LCH) {
                    ColorSpaceConverter con = new ColorSpaceConverter();

                    double[] from = con.RGBtoLCH(c1[1], c1[2], c1[3]);

                    double[] to = con.RGBtoLCH(c2[1], c2[2], c2[3]);

                    double L = (from[0] + (to[0] - from[0]) * coef);
                    double C = (from[1] + (to[1] - from[1]) * coef);
                    double H;

                    double d = to[2] - from[2];

                    double temp;
                    if(from[2] > to[2]) {
                        temp = from[2];
                        from[2] = to[2];
                        to[2] = temp;
                        d = -d;
                        coef = 1 - coef;
                    }

                    if(d > 180) {
                        from[2] += 360;
                        H = ((from[2] + (to[2] - from[2]) * coef)) % 360.0;
                    }
                    else {
                        H = ((from[2] + coef * d));
                    }

                    int[] res = con.LCHtoRGB(L, C, H);

                    res[0] = ColorSpaceConverter.clamp(res[0]);
                    res[1] = ColorSpaceConverter.clamp(res[1]);
                    res[2] = ColorSpaceConverter.clamp(res[2]);

                    palette[(n + k) % palette.length] = new Color(res[0], res[1], res[2]);
                }
            }

            n += c1[0];
        }

        if(processing_alg == MainWindow.PROCESSING_HISTOGRAM) {
            histogramEqualization(palette);
        }

        return palette;

    }

    private static double catmullrom(double t, double p0, double p1, double p2, double p3) {
        return 0.5 * ((2 * p1)
                + (-p0 + p2) * t
                + (2 * p0 - 5 * p1 + 4 * p2 - p3) * t * t
                + (-p0 + 3 * p1 - 3 * p2 + p3) * t * t * t);
    }

    private static void cycleBrightness(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[2] + number;

            if(alg == MainWindow.PROCESSING_BRIGHTNESS1) {
                if(val > 1) {
                    val -= 1;
                }
                else if(val < 0) {
                    val += 1;
                }
            }
            else {
                if(val > 1) {
                    val = 1 - (val - 1);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            Color temp = new Color(Color.HSBtoRGB(res[0], res[1], (float)val));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }
    
    private static void cycleSaturation(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[1] + number;

            if(alg == MainWindow.PROCESSING_SATURATION1) {
                if(val > 1) {
                    val -= 1;
                }
                else if(val < 0) {
                    val += 1;
                }
            }
            else {
                if(val > 1) {
                    val = 1 - (val - 1);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            Color temp = new Color(Color.HSBtoRGB(res[0], (float)val, res[2]));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }
    
    private static void cycleHue(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double val = res[0] + number;

            if(alg == MainWindow.PROCESSING_HUE1) {
                if(val > 1) {
                    val -= 1;
                }
                else if(val < 0) {
                    val += 1;
                }
            }
            else {
                if(val > 1) {
                    val = 1 - (val - 1);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            Color temp = new Color(Color.HSBtoRGB((float)val, res[1], res[2]));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }
    
     private static void cycleHSB(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double valh = res[0] + number;
            double vals = res[1] + number;
            double valb = res[2] + number;

            if(alg == MainWindow.PROCESSING_HSB1) {
                if(valh > 1) {
                    valh -= 1;
                }
                else if(valh < 0) {
                    valh += 1;
                }
            }
            else {
                if(valh > 1) {
                    valh = 1 - (valh - 1);
                }
                else if(valh < 0) {
                    valh *= -1;
                }
            }
            
            if(alg == MainWindow.PROCESSING_HSB1) {
                if(vals > 1) {
                    vals -= 1;
                }
                else if(vals < 0) {
                    vals += 1;
                }
            }
            else {
                if(vals > 1) {
                    vals = 1 - (vals - 1);
                }
                else if(vals < 0) {
                    vals *= -1;
                }
            }
            
            if(alg == MainWindow.PROCESSING_HSB1) {
                if(valb > 1) {
                    valb -= 1;
                }
                else if(valb < 0) {
                    valb += 1;
                }
            }
            else {
                if(valb > 1) {
                    valb = 1 - (valb - 1);
                }
                else if(valb < 0) {
                    valb *= -1;
                }
            }

            Color temp = new Color(Color.HSBtoRGB((float)valh, (float)vals, (float)valb));

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = temp.getRed();
            vec[2] = temp.getGreen();
            vec[3] = temp.getBlue();

            colors[p] = vec;
        }

    }
    
    private static void cycleRed(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = r + number * 255;

            if(alg == MainWindow.PROCESSING_RED1) {
                if(val > 255) {
                    val -= 255;
                }
                else if(val < 0) {
                    val += 255;
                }
            }
            else {
                if(val > 255) {
                    val = 255 - (val - 255);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = (int)val;
            vec[2] = g;
            vec[3] = b;

            colors[p] = vec;
        }

    }
    
    private static void cycleGreen(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = g + number * 255;

            if(alg == MainWindow.PROCESSING_GREEN1) {
                if(val > 255) {
                    val -= 255;
                }
                else if(val < 0) {
                    val += 255;
                }
            }
            else {
                if(val > 255) {
                    val = 255 - (val - 255);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = r;
            vec[2] = (int)val;
            vec[3] = b;

            colors[p] = vec;
        }

    }
    
    private static void cycleBlue(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double val = b + number * 255;

            if(alg == MainWindow.PROCESSING_BLUE1) {
                if(val > 255) {
                    val -= 255;
                }
                else if(val < 0) {
                    val += 255;
                }
            }
            else {
                if(val > 255) {
                    val = 255 - (val - 255);
                }
                else if(val < 0) {
                    val *= -1;
                }
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = r;
            vec[2] = g;
            vec[3] = (int)val;

            colors[p] = vec;
        }

    }
    
    private static void cycleRGB(int[][] colors, double number, int alg) {

        int r, g, b;

        for(int p = 0; p < colors.length; p++) {
            r = colors[p][1];
            g = colors[p][2];
            b = colors[p][3];

            double valr = r + number * 255;
            double valg = g + number * 255;
            double valb = b + number * 255;

            if(alg == MainWindow.PROCESSING_RGB1) {
                if(valr > 255) {
                    valr -= 255;
                }
                else if(valr < 0) {
                    valr += 255;
                }
            }
            else {
                if(valr > 255) {
                    valr = 255 - (valr - 255);
                }
                else if(valr < 0) {
                    valr *= -1;
                }
            }
            
            if(alg == MainWindow.PROCESSING_RGB1) {
                if(valg > 255) {
                    valg -= 255;
                }
                else if(valg < 0) {
                    valg += 255;
                }
            }
            else {
                if(valg > 255) {
                    valg = 255 - (valg - 255);
                }
                else if(valg < 0) {
                    valg *= -1;
                }
            }
            
            if(alg == MainWindow.PROCESSING_RGB1) {
                if(valb > 255) {
                    valb -= 255;
                }
                else if(valb < 0) {
                    valb += 255;
                }
            }
            else {
                if(valb > 255) {
                    valb = 255 - (valb - 255);
                }
                else if(valb < 0) {
                    valb *= -1;
                }
            }

            int[] vec = new int[colors[p].length];
            vec[0] = colors[p][0];
            vec[1] = (int)valr;
            vec[2] = (int)valg;
            vec[3] = (int)valb;

            colors[p] = vec;
        }

    }

    private static void histogramEqualization(Color[] palette) {

        int hist[] = new int[1025];

        int i;
        double mult, count, percentage, next_percentage;
        int low = 0, high = 0;

        int hist_len = hist.length - 1;

        int r, g, b;

        // Fill the histogram by counting the number of pixels with each value
        for(int p = 0; p < palette.length; p++) {
            r = palette[p].getRed();
            g = palette[p].getGreen();
            b = palette[p].getBlue();

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            hist[(int)(res[2] * hist_len + 0.5)]++;
        }

        count = palette.length;

        // Determine the low input value
        next_percentage = hist[0] / count;
        for(i = 0; i < hist_len; i++) {
            percentage = next_percentage;
            next_percentage += hist[i + 1] / count;
            if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //low = i;//i+1; This is a deviation from the way The GIMP does it
                low = i + 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }
        // Determine the high input value
        next_percentage = hist[hist_len] / count;
        for(i = hist_len; i > 0; i--) {
            percentage = next_percentage;
            next_percentage += hist[i - 1] / count;
            if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //high = i;//i-1; This is a deviation from the way The GIMP does it
                high = i - 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }

        // Turn the histogram into a look up table to stretch the values
        mult = ((double)hist_len) / (high - low);

        for(i = 0; i < low; i++) {
            hist[i] = 0;
        }

        for(i = hist_len; i > high; i--) {
            hist[i] = hist_len;
        }

        double base = 0.0;

        for(i = low; i <= high; i++) {
            hist[i] = (int)(base + 0.5);
            base += mult;
        }

        // Now apply the changes (stretch the values)
        for(int p = 0; p < palette.length; p++) {
            r = palette[p].getRed();
            g = palette[p].getGreen();
            b = palette[p].getBlue();

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double temp = hist[(int)(res[2] * hist_len + 0.5)] / ((double)hist_len);

            palette[p] = new Color(Color.HSBtoRGB(res[0], res[1], (float)temp));
        }

    }

    private static void histogramEqualization(int[] palette) {

        int hist[] = new int[1025];

        int i;
        double mult, count, percentage, next_percentage;
        int low = 0, high = 0;

        int hist_len = hist.length - 1;

        int r, g, b;

        // Fill the histogram by counting the number of pixels with each value
        for(int p = 0; p < palette.length; p++) {
            r = (palette[p] >> 16) & 0xff;
            g = (palette[p] >> 8) & 0xff;
            b = palette[p] & 0xff;

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            hist[(int)(res[2] * hist_len + 0.5)]++;
        }

        count = palette.length;

        // Determine the low input value
        next_percentage = hist[0] / count;
        for(i = 0; i < hist_len; i++) {
            percentage = next_percentage;
            next_percentage += hist[i + 1] / count;
            if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //low = i;//i+1; This is a deviation from the way The GIMP does it
                low = i + 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }
        // Determine the high input value
        next_percentage = hist[hist_len] / count;
        for(i = hist_len; i > 0; i--) {
            percentage = next_percentage;
            next_percentage += hist[i - 1] / count;
            if(Math.abs(percentage - 0.006) < Math.abs(next_percentage - 0.006)) {
                //high = i;//i-1; This is a deviation from the way The GIMP does it
                high = i - 1;
                // that prevents any change in the image if it's
                // already optimal
                break;
            }
        }

        // Turn the histogram into a look up table to stretch the values
        mult = ((double)hist_len) / (high - low);

        for(i = 0; i < low; i++) {
            hist[i] = 0;
        }

        for(i = hist_len; i > high; i--) {
            hist[i] = hist_len;
        }

        double base = 0.0;

        for(i = low; i <= high; i++) {
            hist[i] = (int)(base + 0.5);
            base += mult;
        }

        // Now apply the changes (stretch the values)
        for(int p = 0; p < palette.length; p++) {
            r = (palette[p] >> 16) & 0xff;
            g = (palette[p] >> 8) & 0xff;
            b = palette[p] & 0xff;

            float res[] = new float[3];

            Color.RGBtoHSB(r, g, b, res);

            double temp = hist[(int)(res[2] * hist_len + 0.5)] / ((double)hist_len);

            palette[p] = new Color(Color.HSBtoRGB(res[0], res[1], (float)temp)).getRGB();
        }

    }
}
