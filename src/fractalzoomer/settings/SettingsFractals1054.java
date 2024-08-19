

package fractalzoomer.settings;

import java.awt.*;
import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1054 extends SettingsFractals1053 implements Serializable {
    private static final long serialVersionUID = 6735398127077569028L;
    private int escaping_smooth_algorithm;
    private int converging_smooth_algorithm;
    private boolean bump_map;
    private double bumpMappingStrength;
    private double bumpMappingDepth;
    private double lightDirectionDegrees;
    private boolean polar_projection;
    private double circle_period;
    private double color_intensity;

    public SettingsFractals1054(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.escaping_smooth_algorithm = escaping_smooth_algorithm;
        this.converging_smooth_algorithm = converging_smooth_algorithm;
        this.bump_map = bump_map;
        this.bumpMappingDepth = bumpMappingDepth;
        this.bumpMappingStrength = bumpMappingStrength;
        this.lightDirectionDegrees = lightDirectionDegrees;
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        this.color_intensity = color_intensity;

    }

    public int getEscapingSmoothAgorithm() {
        
        return escaping_smooth_algorithm;
        
    }
    
    public int getConvergingSmoothAgorithm() {
        
        return converging_smooth_algorithm;
        
    }
    
    public boolean getBumpMap() {
        
        return bump_map;
        
    }
    
    public double getBumpMapDepth() {
        
        return bumpMappingDepth;
        
    }
    
    public double getBumpMapStrength() {
        
        return bumpMappingStrength;
        
    }
    
    public double getLightDirectionDegrees() {
        
        return lightDirectionDegrees;
        
    }
    
    public boolean getPolarProjection() {
        
        return polar_projection;
        
    }
    
    public double getCirclePeriod() {
        
        return circle_period;
        
    }
    
    public double getColorIntensity() {
        
        return color_intensity;
        
    }
    
    @Override
    public int getVersion() {
        
        return 1054;
        
    }
    
    @Override
    public boolean isJulia() {
        
        return false;
        
    }
}
