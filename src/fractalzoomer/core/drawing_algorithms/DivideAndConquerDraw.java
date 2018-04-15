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
import fractalzoomer.main.app_settings.FiltersSettings;
import fractalzoomer.main.app_settings.GreyscaleColoringSettings;
import fractalzoomer.main.app_settings.OffsetColoringSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.RainbowPaletteSettings;
import fractalzoomer.utils.Square;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;

/**
 *
 * @author hrkalona2
 */
public class DivideAndConquerDraw extends ThreadDraw {

    private static final int MAX_TILE_SIZE = 6;

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3s, ptr, fractal_color, dem_color, image, fs, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, user_fz_formula, user_dfz_formula, user_ddfz_formula,  coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, laguerre_deg, color_blending, ots);
        
    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, D3Settings d3s, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, boolean quickDraw, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, d3s, ptr, fractal_color, dem_color, image, fs, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps,  coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, quickDraw, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, xJuliaCenter, yJuliaCenter);

    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, fs, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps,  coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots);

    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, MainWindow ptr, Color fractal_color, Color dem_color, boolean fast_julia_filters, BufferedImage image, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, boolean inverse_dem, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, fast_julia_filters, image, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, fs, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps,  coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, color_blending, ots, xJuliaCenter, yJuliaCenter);

    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, int color_cycling_location, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, int color_cycling_speed, FiltersSettings fs, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, fractal_color, dem_color, image, color_cycling_location, bms, fdes, rps, color_cycling_speed, fs, color_intensity, transfer_function, ens, ofs, gss, color_blending);

    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, int max_iterations, MainWindow ptr, BufferedImage image, Color fractal_color, Color dem_color, int color_cycling_location, FiltersSettings fs, BumpMapSettings bms, double color_intensity, int transfer_function, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending) {

        super(FROMx, TOx, FROMy, TOy, max_iterations, ptr, image, fractal_color, dem_color, color_cycling_location, fs, bms, fdes, rps, color_intensity, transfer_function, ens, ofs, gss, color_blending);

    }

    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, D3Settings d3s, boolean draw_action, MainWindow ptr, BufferedImage image, FiltersSettings fs,  int color_blending) {

        super(FROMx, TOx, FROMy, TOy, d3s, draw_action, ptr, image, fs,  color_blending);

    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, double[] laguerre_deg, int color_blending, OrbitTrapSettings ots) {
        
        super(FROMx, TOx, FROMy, TOy, xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, ptr, fractal_color, dem_color, image, fs, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, periodicity_checking, plane_type, burning_ship, mandel_grass, mandel_grass_vals, function, z_exponent, z_exponent_complex, color_cycling_location, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, coefficients, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, escaping_smooth_algorithm, converging_smooth_algorithm, bms, polar_projection, circle_period, fdes, rps, user_fz_formula, user_dfz_formula, user_ddfz_formula,  coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, ds, inverse_dem, color_intensity, transfer_function, ens, ofs, gss, laguerre_deg, color_blending, ots);
        
    }
    
    public DivideAndConquerDraw(int FROMx, int TOx, int FROMy, int TOy, double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, ImageExpanderWindow ptr, Color fractal_color, Color dem_color, BufferedImage image, FiltersSettings fs, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, BumpMapSettings bms, double color_intensity, int transfer_function, boolean polar_projection, double circle_period, FakeDistanceEstimationSettings fdes, RainbowPaletteSettings rps,  double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, DomainColoringSettings ds, boolean inverse_dem, EntropyColoringSettings ens, OffsetColoringSettings ofs, GreyscaleColoringSettings gss, int color_blending, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {
        
        super(FROMx,  TOx,  FROMy,  TOy,  xCenter,  yCenter,  size,  max_iterations,  bailout_test_algorithm,  bailout,  bailout_test_user_formula,  bailout_test_user_formula2,  bailout_test_comparison,  n_norm, ptr, fractal_color, dem_color, image, fs,  out_coloring_algorithm,  user_out_coloring_algorithm,  outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula,  in_coloring_algorithm,  user_in_coloring_algorithm,  incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula,  smoothing,  periodicity_checking,  plane_type,  apply_plane_on_julia,  apply_plane_on_julia_seed,  burning_ship,  mandel_grass, mandel_grass_vals,  function,  z_exponent, z_exponent_complex,  color_cycling_location, rotation_vals, rotation_center, coefficients, z_exponent_nova, relaxation,  nova_method,  user_formula,  user_formula2,  bail_technique,  user_plane,  user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula,  exterior_de,  exterior_de_factor,  height_ratio, plane_transform_center,  plane_transform_angle,  plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType,  plane_transform_angle2,  plane_transform_sides,  plane_transform_amount,  escaping_smooth_algorithm,  converging_smooth_algorithm, bms,  polar_projection,  circle_period,  fdes, rps,   coupling, user_formula_coupled,  coupling_method,  coupling_amplitude,  coupling_frequency,  coupling_seed,  ds,  inverse_dem, color_intensity, transfer_function,  ens, ofs, gss, color_blending, ots,  xJuliaCenter,  yJuliaCenter);
                
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

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                                rgbs[loc] = getFinalColor(image_iterations[loc]);
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;
                
                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);

                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations[loc]);
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations[loc]);
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations[loc]);
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                rgbs[loc] = getFinalColor(image_iterations[loc]);
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int color;

        int red, green, blue;

        double temp_result;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   
        
        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                color = getFinalColor(image_iterations[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {

                                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                    r2 = r * antialiasing_x[i];

                                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        double temp_x0, temp_y0;
        int color, red, green, blue;
        double temp_result;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {

                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                drawing_done++;
                thread_calculated++;
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                    drawing_done++;
                    thread_calculated++;
                }
            }
        }

        if (drawing_done / pixel_percent >= 1) {
            update(drawing_done);
            drawing_done = 0;
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                            drawing_done++;
                            thread_calculated++;
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                                image_iterations[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                                color = getFinalColor(image_iterations[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {
                                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));

                                drawing_done++;
                                thread_calculated++;
                            }
                        }
                    }
                }

                if (drawing_done / pixel_percent >= 1) {
                    update(drawing_done);
                    drawing_done = 0;
                }
            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                int drawCount = 0;
                int chunk = temp1 - x;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations, k * image_size + x, k * image_size + temp1, temp_starting_value);
                    drawCount += chunk;
                }

                update(drawCount);

            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_xcenter_size + temp_size_image_size_x * x, temp_ycenter_size - temp_size_image_size_y * y));
                                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                }
            }
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int color;

        double temp_result;
        double temp_x0, temp_y0;

        int red, green, blue;

        Object[] ret = createAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y = (double[])(ret[1]);

        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {
                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                    temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {
                        temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                            temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {
                                temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                temp_x0 = temp_xcenter_size + temp_size_image_size_x * x;
                                temp_y0 = temp_ycenter_size - temp_size_image_size_y * y;

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(temp_x0, temp_y0));
                                color = getFinalColor(image_iterations_fast_julia[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {
                                    temp_result = iteration_algorithm.calculate(new Complex(temp_x0 + antialiasing_x[i], temp_y0 + antialiasing_y[i]));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
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

        int loc;

        int notCalculated = 0;

        int color;
        
        double temp_result;

        int red, green, blue;

        Object[] ret = createPolarAntialiasingSteps();
        double[] antialiasing_x = (double[])(ret[0]);
        double[] antialiasing_y_sin = (double[])(ret[1]);
        double[] antialiasing_y_cos = (double[])(ret[2]);   
        
        int supersampling_num = (filters_options_vals[MainWindow.ANTIALIASING] == 0 ? 4 : 8 * filters_options_vals[MainWindow.ANTIALIASING]);
        double temp_samples = supersampling_num + 1;

        int x = FROMx;
        for (int y = FROMy; y < TOy; y++) {
            loc = y * image_size + x;
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                rgbs[loc] = getFinalColor(image_iterations_fast_julia[loc]);
            }
        }

        if (TOx == image_size) {
            x = TOx - 1;
            for (int y = FROMy + 1; y < TOy; y++) {
                loc = y * image_size + x;
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        int y = FROMy;
        for (x = FROMx + 1, loc = y * image_size + x; x < TOx; x++, loc++) {
            if (rgbs[loc] == notCalculated) {
                f = y * muly;
                sf = Math.sin(f);
                cf = Math.cos(f);

                r = Math.exp(x * mulx + start);

                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                color = getFinalColor(image_iterations_fast_julia[loc]);

                red = (color >> 16) & 0xff;
                green = (color >> 8) & 0xff;
                blue = color & 0xff;

                //Supersampling
                for (int i = 0; i < supersampling_num; i++) {

                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                    r2 = r * antialiasing_x[i];

                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                    color = getFinalColor(temp_result);

                    red += (color >> 16) & 0xff;
                    green += (color >> 8) & 0xff;
                    blue += color & 0xff;
                }

                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
            }
        }

        if (TOy == image_size) {
            y = TOy - 1;

            int xLimit = TOx;

            if (TOx == image_size) {
                xLimit--;
            }

            for (x = FROMx + 1, loc = y * image_size + x; x < xLimit; x++, loc++) {
                if (rgbs[loc] == notCalculated) {
                    f = y * muly;
                    sf = Math.sin(f);
                    cf = Math.cos(f);

                    r = Math.exp(x * mulx + start);

                    image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                    color = getFinalColor(image_iterations_fast_julia[loc]);

                    red = (color >> 16) & 0xff;
                    green = (color >> 8) & 0xff;
                    blue = color & 0xff;

                    //Supersampling
                    for (int i = 0; i < supersampling_num; i++) {

                        sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                        cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                        r2 = r * antialiasing_x[i];

                        temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                        color = getFinalColor(temp_result);

                        red += (color >> 16) & 0xff;
                        green += (color >> 8) & 0xff;
                        blue += color & 0xff;
                    }

                    rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                }
            }
        }

        ArrayList<Square> squares = new ArrayList<Square>();

        Square square = new Square();
        square.x1 = FROMx;
        square.y1 = FROMy;
        square.x2 = TOx;
        square.y2 = TOy;
        square.iteration = randomNumber;

        squares.add(square);

        try {
            initialize_jobs_sync.await();
        } catch (InterruptedException ex) {

        } catch (BrokenBarrierException ex) {

        }

        int slice_FROMx;
        int slice_FROMy;
        int slice_TOx;
        int slice_TOy;

        boolean whole_area;
        int temp_starting_pixel_color;
        double temp_starting_value;
        
        int skippedColor;

        do {

            Square currentSquare = null;

            if (squares.isEmpty()) {
                break;
            }

            currentSquare = squares.remove(0);

            whole_area = true;

            slice_FROMx = currentSquare.x1;
            slice_FROMy = currentSquare.y1;
            slice_TOx = currentSquare.x2;
            slice_TOy = currentSquare.y2;

            if (slice_TOy == image_size) {
                slice_TOy--;
            }

            if (slice_TOx == image_size) {
                slice_TOx--;
            }

            y = slice_FROMy;
            x = slice_FROMx;

            loc = y * image_size + x;

            temp_starting_value = image_iterations_fast_julia[loc];
            temp_starting_pixel_color = rgbs[loc];

            for (x++, loc = y * image_size + x; x <= slice_TOx; x++, loc++) {
                if (rgbs[loc] != temp_starting_pixel_color) {
                    whole_area = false;
                    break;
                }
            }

            if (whole_area) {
                for (x--, y++; y <= slice_TOy; y++) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (y--, x--, loc = y * image_size + x; x >= slice_FROMx; x--, loc--) {
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (whole_area) {
                for (x++, y--; y > slice_FROMy; y--) {
                    loc = y * image_size + x;
                    if (rgbs[loc] != temp_starting_pixel_color) {
                        whole_area = false;
                        break;
                    }
                }
            }

            if (!whole_area) {

                int xLength = slice_TOx - slice_FROMx + 1;
                int yLength = slice_TOy - slice_FROMy + 1;

                if (xLength >= MAX_TILE_SIZE && yLength >= MAX_TILE_SIZE) {
                    y = slice_FROMy + yLength / 2;
                    for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    x = slice_FROMx + xLength / 2;
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        loc = y * image_size + x;

                        if (rgbs[loc] == notCalculated) {
                            f = y * muly;
                            sf = Math.sin(f);
                            cf = Math.cos(f);

                            r = Math.exp(x * mulx + start);

                            image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                            color = getFinalColor(image_iterations_fast_julia[loc]);

                            red = (color >> 16) & 0xff;
                            green = (color >> 8) & 0xff;
                            blue = color & 0xff;

                            //Supersampling
                            for (int i = 0; i < supersampling_num; i++) {

                                sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                r2 = r * antialiasing_x[i];

                                temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                color = getFinalColor(temp_result);

                                red += (color >> 16) & 0xff;
                                green += (color >> 8) & 0xff;
                                blue += color & 0xff;
                            }

                            rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                        }
                    }

                    Square square1 = new Square();
                    square1.x1 = slice_FROMx;
                    square1.y1 = slice_FROMy;
                    square1.x2 = slice_FROMx + xLength / 2;
                    square1.y2 = slice_FROMy + yLength / 2;
                    square1.iteration = currentSquare.iteration + 1;

                    squares.add(square1);

                    Square square2 = new Square();
                    square2.x1 = slice_FROMx + xLength / 2;
                    square2.y1 = slice_FROMy;
                    square2.x2 = slice_TOx;
                    square2.y2 = slice_FROMy + yLength / 2;
                    square2.iteration = currentSquare.iteration + 1;

                    squares.add(square2);

                    Square square3 = new Square();
                    square3.x1 = slice_FROMx;
                    square3.y1 = slice_FROMy + yLength / 2;
                    square3.x2 = slice_FROMx + xLength / 2;
                    square3.y2 = slice_TOy;
                    square3.iteration = currentSquare.iteration + 1;

                    squares.add(square3);

                    Square square4 = new Square();
                    square4.x1 = slice_FROMx + xLength / 2;
                    square4.y1 = slice_FROMy + yLength / 2;
                    square4.x2 = slice_TOx;
                    square4.y2 = slice_TOy;
                    square4.iteration = currentSquare.iteration + 1;

                    squares.add(square4);
                } else {
                    //calculate the rest with the normal way
                    for (y = slice_FROMy + 1; y < slice_TOy; y++) {
                        for (x = slice_FROMx + 1, loc = y * image_size + x; x < slice_TOx; x++, loc++) {

                            if (rgbs[loc] == notCalculated) {
                                f = y * muly;
                                sf = Math.sin(f);
                                cf = Math.cos(f);

                                r = Math.exp(x * mulx + start);

                                image_iterations_fast_julia[loc] = iteration_algorithm.calculate(new Complex(xcenter + r * cf, ycenter + r * sf));
                                color = getFinalColor(image_iterations_fast_julia[loc]);

                                red = (color >> 16) & 0xff;
                                green = (color >> 8) & 0xff;
                                blue = color & 0xff;

                                //Supersampling
                                for (int i = 0; i < supersampling_num; i++) {

                                    sf2 = sf * antialiasing_y_cos[i] + cf * antialiasing_y_sin[i];
                                    cf2 = cf * antialiasing_y_cos[i] - sf * antialiasing_y_sin[i];

                                    r2 = r * antialiasing_x[i];

                                    temp_result = iteration_algorithm.calculate(new Complex(xcenter + r2 * cf2, ycenter + r2 * sf2));
                                    color = getFinalColor(temp_result);

                                    red += (color >> 16) & 0xff;
                                    green += (color >> 8) & 0xff;
                                    blue += color & 0xff;
                                }

                                rgbs[loc] = 0xff000000 | (((int) (red / temp_samples + 0.5)) << 16) | (((int) (green / temp_samples + 0.5)) << 8) | ((int) (blue / temp_samples + 0.5));
                            }
                        }
                    }
                }

            } else {
                int temp1 = slice_TOx;
                int temp2 = slice_TOy;

                y = slice_FROMy + 1;
                x = slice_FROMx + 1;

                skippedColor = getColorForSkippedPixels(temp_starting_pixel_color, currentSquare.iteration);
                        
                for (int k = y; k < temp2; k++) {
                    Arrays.fill(rgbs, k * image_size + x, k * image_size + temp1, skippedColor);
                    Arrays.fill(image_iterations_fast_julia, k * image_size + x, k * image_size + temp1, temp_starting_value);
                }
            }

        } while (true);

        if(SKIPPED_PIXELS_ALG == 4) {
            drawSquares(image_size);
        }
        
        postProcessFastJulia(image_size);

    }
}
