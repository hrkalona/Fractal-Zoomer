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
public class SettingsJulia1056 extends SettingsFractals1056 implements Serializable {
    private static final long serialVersionUID = -6211061697837730555L;
    private double xJuliaCenter;
    private double yJuliaCenter;
    
    public SettingsJulia1056(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, int bailout_test_comparison, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double color_intensity, int escaping_smooth_algorithm, int converging_smooth_algorithm, boolean bump_map, double bumpMappingStrength, double bumpMappingDepth, double lightDirectionDegrees, boolean polar_projection, double circle_period, boolean fake_de, double fake_de_factor, double xJuliaCenter, double yJuliaCenter) {
        
        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_comparison, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, false, null, false, "", false, null, false, "", mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, exterior_de, exterior_de_factor, height_ratio, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_angle2, plane_transform_sides, plane_transform_amount, color_intensity, escaping_smooth_algorithm, converging_smooth_algorithm, bump_map, bumpMappingStrength, bumpMappingDepth, lightDirectionDegrees, polar_projection, circle_period, fake_de, fake_de_factor);
        
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
