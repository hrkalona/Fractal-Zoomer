/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.settings;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1057 extends SettingsFractals1056 implements Serializable {

    private boolean apply_plane_on_julia;
    private int user_in_coloring_algorithm;
    private int user_out_coloring_algorithm;
    private String outcoloring_formula;
    private String[] user_outcoloring_conditions;
    private String[] user_outcoloring_condition_formula;
    private String incoloring_formula;
    private String[] user_incoloring_conditions;
    private String[] user_incoloring_condition_formula;

    public SettingsFractals1057(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int function, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, int bailout_test_comparison, double n_norm, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, String initial_value_user_formula, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor);

        this.apply_plane_on_julia = apply_plane_on_julia;
        this.user_in_coloring_algorithm = user_in_coloring_algorithm;
        this.user_out_coloring_algorithm = user_out_coloring_algorithm;
        this.outcoloring_formula = outcoloring_formula;
        this.incoloring_formula = incoloring_formula;
        this.user_outcoloring_conditions = user_outcoloring_conditions;
        this.user_outcoloring_condition_formula = user_outcoloring_condition_formula;
        this.user_incoloring_conditions = user_incoloring_conditions;
        this.user_incoloring_condition_formula = user_incoloring_condition_formula;

    }

    public boolean getApplyPlaneOnJulia() {

        return apply_plane_on_julia;

    }

    public int getUserInColoringAlgorithm() {

        return user_in_coloring_algorithm;

    }

    public int getUserOutColoringAlgorithm() {

        return user_out_coloring_algorithm;

    }

    public String getIncoloringFormula() {

        return incoloring_formula;

    }

    public String getOutcoloringFormula() {

        return outcoloring_formula;

    }

    public String[] getUserOutcoloringConditions() {
        
        return user_outcoloring_conditions;
        
    }
    
    public String[] getUserIncoloringConditions() {
        
        return user_incoloring_conditions;
        
    }
    
    public String[] getUserIncoloringConditionFormula() {
        
        return user_incoloring_condition_formula;
        
    }
    
    public String[] getUserOutcoloringConditionFormula() {
        
        return user_outcoloring_condition_formula;
        
    }

}
