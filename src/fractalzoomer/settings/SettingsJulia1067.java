/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
package fractalzoomer.settings;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author kaloch
 */
public class SettingsJulia1067 extends SettingsFractals1067 implements Serializable {
    private static final long serialVersionUID = 7493214133477878749L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1067(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int function, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, Color dem_color, Color special_color, boolean special_use_palette_color, boolean rainbow_palette, double rainbow_palette_factor, boolean[] filters, int[] filters_options_vals, double scale_factor_palette_val, int processing_alg, Color[] filters_colors, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean domain_coloring, boolean use_palette_domain_coloring, int domain_coloring_alg, boolean inverse_dem, boolean inverse_fake_dem, int[][] filters_options_extra_vals, Color[][] filters_extra_colors, int color_smoothing_method, int[] filters_order, double bm_noise_reducing_factor, double rp_noise_reducing_factor, boolean entropy_coloring, double entropy_palette_factor, double en_noise_reducing_factor, boolean apply_plane_on_julia_seed, boolean offset_coloring, int post_process_offset, double of_noise_reducing_factor, double en_blending, double rp_blending, double of_blending, int entropy_offset, int rainbow_offset, boolean greyscale_coloring, double gs_noise_reducing_factor, int transfer_function, int color_blending, int bump_transfer_function, double bump_transfer_factor, double bump_blending, int bumpProcessing, boolean GlobalIncrementBypass, int waveType, double[] plane_transform_wavelength, double[] laguerre_deg, int iso_distance, double iso_factor, double logBase, double normType, double gridFactor, double circlesBlending, double isoLinesBlendingFactor, double gridBlending, Color gridColor, Color circlesColor, Color isoLinesColor, double contourBlending, boolean drawColor, boolean drawContour, boolean drawGrid, boolean drawCircles, boolean drawIsoLines, boolean customDomainColoring, int colorType, int contourType, double xJuliaCenter, double yJuliaCenter) {
        
        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, false, null, false, 0, null, null, "", false, null, false, 0, null, null, "", mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor, "", "", "", dem_color, special_color, special_use_palette_color, rainbow_palette, rainbow_palette_factor, filters, filters_options_vals, scale_factor_palette_val, processing_alg, filters_colors, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, domain_coloring, use_palette_domain_coloring, domain_coloring_alg, inverse_dem, inverse_fake_dem, filters_options_extra_vals, filters_extra_colors, color_smoothing_method, filters_order, bm_noise_reducing_factor, rp_noise_reducing_factor, entropy_coloring, entropy_palette_factor, en_noise_reducing_factor, apply_plane_on_julia_seed, offset_coloring, post_process_offset, of_noise_reducing_factor, en_blending, rp_blending, of_blending, entropy_offset, rainbow_offset, greyscale_coloring, gs_noise_reducing_factor, transfer_function, color_blending, bump_transfer_function, bump_transfer_factor, bump_blending, bumpProcessing, GlobalIncrementBypass, waveType, plane_transform_wavelength, laguerre_deg, iso_distance, iso_factor, logBase, normType, gridFactor, circlesBlending, isoLinesBlendingFactor, gridBlending, gridColor, circlesColor, isoLinesColor, contourBlending, drawColor, drawContour, drawGrid, drawCircles, drawIsoLines, customDomainColoring, colorType, contourType);
        this.xJuliaCenter = xJuliaCenter;
        this.yJuliaCenter = yJuliaCenter;
        
    }
    
    public double getXJuliaCenter() {

        return xJuliaCenter;

    }

    public double getYJuliaCenter() {

        return yJuliaCenter;

    }
    
    @Override
    public boolean isJulia() {
        
        return true;
        
    }
    
}
