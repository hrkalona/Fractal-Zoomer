/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.BumpMapSettings;
import fractalzoomer.main.app_settings.D3Settings;
import fractalzoomer.main.app_settings.DomainColoringSettings;
import fractalzoomer.main.app_settings.EntropyColoringSettings;
import fractalzoomer.main.app_settings.FakeDistanceEstimationSettings;
import fractalzoomer.main.app_settings.GreyscaleColoringSettings;
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class BruteForce2Draw extends ThreadDraw {

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3s, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, quickDraw, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, ens, ofs, gss, laguerre_deg, color_blending);
        
    }

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3s, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, quickDraw, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, ens, ofs, gss, color_blending, xJuliaCenter, yJuliaCenter);

    }

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, ens, ofs, gss, color_blending);

    }

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, filters, filters_options_vals, filters_options_extra_vals, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, ens, ofs, gss, color_blending, xJuliaCenter, yJuliaCenter);

    }

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, boolean smoothing, BufferedImage image, int color_cycling_location, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bms, fdes, rps, color_cycling_speed, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, smoothing, ens, ofs, gss, color_blending);

    }

    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, int color_cycling_location, boolean smoothing, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, filters, filters_options_vals, filters_options_extra_vals, bms, fdes, rps, filters_colors, filters_extra_colors, filters_order, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, smoothing, ens, ofs, gss, color_blending);

    }

    public BruteForce2Draw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, filters, filters_options_vals, filters_options_extra_vals, filters_colors, filters_extra_colors, filters_order, color_blending);

    }
    
    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending) {
        
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, user_fz_formula, user_dfz_formula, user_ddfz_formula, filters_colors, filters_extra_colors, filters_order, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, custom_palette_choice, custom_palette, color_interpolation, color_space, reverse, scale_factor_palette_val, processing_alg, color_intensity, transfer_function, special_color, color_smoothing_method, color_choice, ens, ofs, gss, laguerre_deg, color_blending);
        
    }
    
    public BruteForce2Draw(int color_choice, int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, Color special_color, int color_smoothing_method, BufferedImage image, boolean[] filters, int[] filters_options_vals, int[][] filters_options_extra_vals, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, Color[] filters_colors, Color[][] filters_extra_colors, int[] filters_order, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean custom_palette_choice, int[][] custom_palette, int color_interpolation, int color_space, boolean reverse, double scale_factor_palette_val, int processing_alg, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, double xJuliaCenter, double yJuliaCenter) {
        
        super(FROMx,  TOx,  FROMy,  TOy,  xCenter,  yCenter,  size,  max_iterations,  bailout_test_algorithm,  bailout,  bailout_test_user_formula,  bailout_test_user_formula2,  bailout_test_comparison,  n_norm, ptr, fractal_color, dem_color, image, filters, filters_options_vals, filters_options_extra_vals,  out_coloring_algorithm,  user_out_coloring_algorithm,  outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula,  in_coloring_algorithm,  user_in_coloring_algorithm,  incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula,  smoothing,  periodicity_checking,  plane_type,  apply_plane_on_julia,  apply_plane_on_julia_seed,  burning_ship,  mandel_grass, mandel_grass_vals,  function,  z_exponent, z_exponent_complex,  color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation,  nova_method,  user_formula,  user_formula2,  bail_technique,  user_plane,  user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula,  exterior_de,  exterior_de_factor,  height_ratio, plane_transform_center,  plane_transform_angle,  plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType,  plane_transform_angle2,  plane_transform_sides,  plane_transform_amount,  escaping_smooth_algorithm,  converging_smooth_algorithm, bms,  polar_projection,  circle_period,  fdes, rps, filters_colors, filters_extra_colors, filters_order,  coupling, user_formula_coupled,  coupling_method,  coupling_amplitude,  coupling_frequency,  coupling_seed,  ds,  inverse_dem,  custom_palette_choice, custom_palette,  color_interpolation,  color_space,  reverse,  scale_factor_palette_val,  processing_alg, color_intensity, transfer_function, special_color,  color_smoothing_method,  color_choice,  ens, ofs, gss, color_blending,  xJuliaCenter,  yJuliaCenter);
                
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

        int x, y, loc;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getColor(image_iterations[loc]);

                drawing_done++;
                thread_calculated++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);
    }

    @Override
    protected void drawIterationsPolar(int image_size) {
        
        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getColor(image_iterations[loc]);

                drawing_done++;
                thread_calculated++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);
        
    }

    @Override
    protected void drawIterationsPolarAntialiased(int image_size) {
        
        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);

        int pixel_percent = image_size * image_size / 100;

        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;
        
        int color;
        
        double temp_result;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);
        
    }

    @Override
    protected void drawIterationsAntialiased(int image_size) {
        
        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int pixel_percent = image_size * image_size / 100;

        int x, y, loc;
        
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }

            if(drawing_done / pixel_percent >= 1) {
                update(drawing_done);
                drawing_done = 0;
            }

        }

        postProcess(image_size);
        
    }

    @Override
    protected void drawFastJulia(int image_size) {
        
        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int x, y, loc;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getColor(image_iterations_fast_julia[loc]);
            }

        }

        postProcessFastJulia(image_size);
        
    }

    @Override
    protected void drawFastJuliaPolar(int image_size) {
        
        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);


        double f, sf, cf, r;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getColor(image_iterations_fast_julia[loc]);
            }

        }

        postProcessFastJulia(image_size);
        
    }

    @Override
    protected void drawFastJuliaAntialiased(int image_size) {
        
        double size = fractal.getSize();

        double size_2_x = size * 0.5;
        double size_2_y = (size * height_ratio) * 0.5;
        double temp_size_image_size_x = size / image_size;
        double temp_size_image_size_y = (size * height_ratio) / image_size;

        double temp_xcenter_size = fractal.getXCenter() - size_2_x;
        double temp_ycenter_size = fractal.getYCenter() + size_2_y;

        int x, y, loc;
        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            }

        }

        postProcessFastJulia(image_size);
        
    }

    @Override
    protected void drawFastJuliaPolarAntialiased(int image_size) {
        
        double size = fractal.getSize();
        double xcenter = fractal.getXCenter();
        double ycenter = fractal.getYCenter();

        double start;
        double center = Math.log(size);


        double f, sf, cf, r, sf2, cf2, r2;
        double muly = (2 * circle_period * Math.PI) / image_size;

        double mulx = muly * height_ratio;

        start = center - mulx * image_size * 0.5;

        int x, y, loc;
        
        int color;
        
        double temp_result;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        for(y = FROMy; y < TOy; y++) {
            for(x = FROMx, loc = y * image_size + x; x < TOx; x++, loc++) {

                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for(int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int)(red / temp_samples + 0.5)) << 16) | (((int)(green / temp_samples + 0.5)) << 8) | ((int)(blue / temp_samples + 0.5));
            }

        }

        postProcessFastJulia(image_size);
        
    }
}
