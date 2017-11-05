/*
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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
package fractalzoomer.core.drawing_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.MainWindow;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author hrkalona2
 */
public class SolidGuessingDraw extends ThreadDraw {   
    private static final int SOLID_GUESSING_SLICES = 10;

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert, boolean inverse_dem, boolean inverse_fake_dem, boolean quickDraw, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel_size, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);
        
    }

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean d3, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean gaussian_scaling, double gaussian_weight, int gaussian_kernel_size, int min_to_max_scaling, int max_scaling, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double d3_height_scale, int height_algorithm, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, int domain_coloring_alg, boolean use_palette_domain_coloring, boolean shade_height, int shade_choice, int shade_algorithm, boolean shade_invert, boolean inverse_dem, boolean inverse_fake_dem, boolean quickDraw, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3, detail, d3_size_scale, fiX, fiY, color_3d_blending, gaussian_scaling, gaussian_weight, gaussian_kernel_size, min_to_max_scaling, max_scaling, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, d3_height_scale, height_algorithm, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, domain_coloring_alg, use_palette_domain_coloring, shade_height, shade_choice, shade_algorithm, shade_invert, inverse_dem, inverse_fake_dem, quickDraw, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);

    }

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);

    }

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, filters, filters_options_vals, filters_options_extra_vals, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, polar_projection, circle_period, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, xJuliaCenter, yJuliaCenter);

    }

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, boolean smoothing, BufferedImage image, int color_cycling_location, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, int color_cycling_speed, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, color_cycling_speed, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, smoothing, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);

    }

    public SolidGuessingDraw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, int color_cycling_location, boolean smoothing, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, boolean bump_map, double lightDirectionDegrees, double bumpMappingDepth, double bumpMappingStrength, double color_intensity, boolean fake_de, double fake_de_factor, boolean rainbow_palette, double rainbow_palette_factor, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean inverse_fake_dem, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, filters, filters_options_vals, filters_options_extra_vals, bump_map, lightDirectionDegrees, bumpMappingDepth, bumpMappingStrength, fake_de, fake_de_factor, rainbow_palette, rainbow_palette_factor, filters_colors, filters_extra_colors, filters_order, inverse_fake_dem, bm_noise_reducing_factor, rp_noise_reducing_factor, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, special_color, color_smoothing_method, color_choice, smoothing, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor);

    }

    public SolidGuessingDraw(int FROMx, int TOx, int FROMy, int TOy, int detail, double d3_size_scale, double fiX, double fiY, double color_3d_blending, boolean draw_action, MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order) {

        super(FROMx, TOx, FROMy, TOy, detail, d3_size_scale, fiX, fiY, color_3d_blending, draw_action, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order);

    }

    @Override
    protected void drawIterations(int image_size) {

        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x = 0;
        int y = 0;
        boolean whole_area;
        int step;
        double temp_x0 = 0;
        double temp_y0;

        double temp_starting_pixel_cicle;
        int temp_starting_pixel_color;

        int thread_size_width = TOx - FROMx;
        int thread_size_height = TOy - FROMy;

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;
        int loc;
        
        int notCalculated = 0;
        
        int skippedColor;

        for(int i = 0; i < SOLID_GUESSING_SLICES; i++) {
            slice_FROMy = FROMy + i * thread_size_height / SOLID_GUESSING_SLICES;
            slice_TOy = FROMy + (i + 1) * (thread_size_height) / SOLID_GUESSING_SLICES;
            for(int j = 0; j < SOLID_GUESSING_SLICES; j++) {
                slice_FROMx = FROMx + j * (thread_size_width) / SOLID_GUESSING_SLICES;
                slice_TOx = FROMx + (j + 1) * (thread_size_width) / SOLID_GUESSING_SLICES;

                double temp = (slice_TOy - slice_FROMy + 1) / 2;

                for(y = slice_FROMy, whole_area = true, step = 0; step < temp; step++, whole_area = true) {

                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    x = slice_FROMx + step;
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;

                    loc = y * image_size + x;

                    temp_starting_pixel_cicle = image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));

                    rgbs[loc] = temp_starting_pixel_color = getColor(temp_starting_pixel_cicle);

                    drawing_done++;
                    thread_calculated++;

                    for(x++, loc++; x < slice_TOx - step; x++, loc++) {
                        if(rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getColor(image_iterations[loc]);

                            if(rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }

                    }

                    for(x--, y++; y < slice_TOy - step; y++) {
                        loc = y * image_size + x;

                        if(rgbs[loc] == notCalculated) {
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getColor(image_iterations[loc]);

                            if(rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    for(y--, x--, loc = y * image_size + x; x >= slice_FROMx + step; x--, loc--) {
                        if(rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getColor(image_iterations[loc]);

                            if(rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    for(x++, y--; y > slice_FROMy + step; y--) {
                        loc = y * image_size + x;
                        if(rgbs[loc] == notCalculated) {
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;
                            
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            rgbs[loc] = getColor(image_iterations[loc]);

                            if(rgbs[loc] != temp_starting_pixel_color) {
                                whole_area = false;
                            }

                            drawing_done++;
                            thread_calculated++;
                        }
                    }
                    y++;
                    x++;

                    if(drawing_done / pixel_percent >= 1) {
                        update(drawing_done);
                        drawing_done = 0;
                    }

                    if(whole_area) {
                        int temp6 = step + 1;
                        int temp1 = slice_TOx - temp6;
                        int temp2 = slice_TOy - temp6;

                        int temp3;
                        int temp4;

                        int drawCount = 0;
                        int chunk = temp1 - x;
                        
                        skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, randomNumber + step);

                        for(int k = y; k < temp2; k++) {
                            temp3 = k * image_size + x;
                            temp4 = k * image_size + temp1;
                            Arrays.fill(image_iterations, temp3, temp4, temp_starting_pixel_cicle);
                            
                            Arrays.fill(rgbs, temp3, temp4, skippedColor);

                            drawCount += chunk;
                        }

                        update(drawCount);
                        break;
                    }
                }
            }
        }

        postProcess(image_size);

    }

    @Override
    protected void drawIterationsPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawIterationsAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawFastJulia(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void drawFastJuliaPolarAntialiased(int image_size) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
