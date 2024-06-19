

package fractalzoomer.settings;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1056 extends SettingsFractals1055 implements Serializable {
    private static final long serialVersionUID = 8315043516173705628L;
    private String bailout_test_user_formula;
    private int bailout_test_comparison;
    private boolean variable_perturbation;
    private String perturbation_user_formula;
    private boolean variable_init_value; 
    private String initial_value_user_formula;
    
    public SettingsFractals1056(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, int bailout_test_comparison, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, String initial_value_user_formula, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor) {
        
        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor);
        
        this.bailout_test_comparison = bailout_test_comparison;
        this.bailout_test_user_formula = bailout_test_user_formula;
        this.variable_init_value = variable_init_value;
        this.initial_value_user_formula = initial_value_user_formula;
        this.variable_perturbation = variable_perturbation;
        this.perturbation_user_formula = perturbation_user_formula;
        
    }
    
    public String getBailoutTestUserFormula() {
        
        return bailout_test_user_formula;
        
    }
    
    public int getBailoutTestComparison() {
        
        return bailout_test_comparison;
        
    }
    
    public String getInitialValueUserFormula() {
        
        return initial_value_user_formula;
        
    }
    
    public boolean getVariableInitValue() {
        
        return variable_init_value;
        
    }
    
    public String getPerturbationUserFormula() {
        
        return perturbation_user_formula;
        
    }
    
    public boolean getVariablePerturbation() {
        
        return variable_perturbation;
        
    }
    
    @Override
    public int getVersion() {
        
        return 1056;
        
    }
    
    @Override
    public boolean isJulia() {
        
        return false;
        
    }
    
}
