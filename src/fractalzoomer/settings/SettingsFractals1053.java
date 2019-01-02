/* 
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
public class SettingsFractals1053 extends SettingsFractals1050 implements Serializable {
    private static final long serialVersionUID = 5850756480708737597L;
    private boolean exterior_de;
    private double exterior_de_factor;
    private double height_ratio;
    private double[] plane_transform_center;
    private double plane_transform_angle;
    private double plane_transform_radius;
    private double[] plane_transform_scales;
    private double plane_transform_angle2;
    private int plane_transform_sides;
    private double plane_transform_amount;

    public SettingsFractals1053(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, boolean exterior_de, double exterior_de_factor, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, perturbation, perturbation_vals, init_val, initial_vals, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, user_formula2, bail_technique, user_plane, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula);
        this.exterior_de = exterior_de;
        this.exterior_de_factor = exterior_de_factor;
        this.height_ratio = height_ratio;
        this.plane_transform_center = plane_transform_center;
        this.plane_transform_angle = plane_transform_angle;
        this.plane_transform_radius = plane_transform_radius;
        this.plane_transform_scales = plane_transform_scales;
        this.plane_transform_angle2 = plane_transform_angle2;
        this.plane_transform_sides = plane_transform_sides;
        this.plane_transform_amount = plane_transform_amount;

    }

    public boolean getExteriorDe() {

        return exterior_de;

    }

    public double getExteriorDeFactor() {

        return exterior_de_factor;

    }

    public double getHeightRatio() {

        return height_ratio;

    }
    
    public double[] getPlaneTransformCenter() {
        
        return plane_transform_center;
        
    }
    
    public double[] getPlaneTransformScales() {
        
        return plane_transform_scales;
        
    }
    
    public double getPlaneTransformAngle() {
        
        return plane_transform_angle;
        
    }
    
    public double getPlaneTransformAngle2() {
        
        return plane_transform_angle2;
        
    }
    
    public double getPlaneTransformAmount() {
        
        return plane_transform_amount;
        
    }
    
    public double getPlaneTransformRadius() {
        
        return plane_transform_radius;
        
    }
    
    public int getPlaneTransformSides() {
        
        return plane_transform_sides;
        
    }
    
    @Override
    public int getVersion() {
        
        return 1053;
        
    }
    
    @Override
    public boolean isJulia() {
        
        return false;
        
    }
}
