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
package fractalzoomer.settings;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1058 extends SettingsFractals1057 implements Serializable {
    private int user_perturbation_algorithm;
    private String[] user_perturbation_conditions;
    private String[] user_perturbation_condition_formula;

    private int user_initial_value_algorithm;
    private String[] user_initial_value_conditions;
    private String[] user_initial_value_condition_formula;

    private int user_plane_algorithm;
    private String[] user_plane_conditions;
    private String[] user_plane_condition_formula;

    private String bailout_test_user_formula2;
    
    private String user_fz_formula;
    private String user_dfz_formula;
    private String user_ddfz_formula;

    public SettingsFractals1058(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int function, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int plane_type, boolean apply_plane_on_julia, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, plane_type, apply_plane_on_julia, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, variable_perturbation, perturbation_user_formula, init_val, initial_vals, variable_init_value, initial_value_user_formula, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor);
        this.user_perturbation_algorithm = user_perturbation_algorithm;
        this.user_perturbation_conditions = user_perturbation_conditions;
        this.user_perturbation_condition_formula = user_perturbation_condition_formula;
        
        this.user_initial_value_algorithm = user_initial_value_algorithm;
        this.user_initial_value_conditions = user_initial_value_conditions;
        this.user_initial_value_condition_formula = user_initial_value_condition_formula;
        
        this.user_plane_algorithm = user_plane_algorithm;
        this.user_plane_conditions = user_plane_conditions;
        this.user_plane_condition_formula = user_plane_condition_formula;
        
        this.bailout_test_user_formula2 = bailout_test_user_formula2;
        
        this.user_fz_formula = user_fz_formula;
        this.user_dfz_formula = user_dfz_formula;
        this.user_ddfz_formula = user_ddfz_formula;
        
    }
    
    public int getUserPerturbationAlgorithm() {
        
        return user_perturbation_algorithm;
                
    }
    
    public int getUserInitialValueAlgorithm() {
        
        return user_initial_value_algorithm;
                
    }
    
    public int getUserPlaneAlgorithm() {
        
        return user_plane_algorithm;
                
    }
    
    public String[] getUserPerturbationConditions() {
        
        return user_perturbation_conditions;
        
    }
    
    public String[] getUserInitialValueConditions() {
        
        return user_initial_value_conditions;
        
    }
    
    public String[] getUserPlaneConditions() {
        
        return user_plane_conditions;
        
    }
    
    public String[] getUserPerturbationConditionFormula() {
        
        return user_perturbation_condition_formula;
        
    }
    
    public String[] getUserInitialValueConditionFormula() {
        
        return user_initial_value_condition_formula;
        
    }
    
    public String[] getUserPlaneConditionFormula() {
        
        return user_plane_condition_formula;
        
    }
    
    public String getBailoutTestUserFormula2() {
        
        return bailout_test_user_formula2;
        
    }
    
    public String getUserFzFormula() {
        
        return user_fz_formula;
                
    }
    
    public String getUserDfzFormula() {
        
        return user_dfz_formula;
                
    }
    
    public String getUserDdfzFormula() {
        
        return user_ddfz_formula;
                
    }

}
