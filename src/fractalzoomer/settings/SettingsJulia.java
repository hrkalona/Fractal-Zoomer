

package fractalzoomer.settings;

import java.awt.*;
import java.io.Serializable;


/**
 *
 * @author hrkalona
 */
public class SettingsJulia extends SettingsFractals implements Serializable {
  private static final long serialVersionUID = -1073457876715829636L;
  private double xJuliaCenter;
  private double yJuliaCenter;

    public SettingsJulia(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, int bail_technique, String user_plane, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, color_interpolation, color_space, reversed_palette, rotation, rotation_center, false, null, false, null, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method, user_formula, bail_technique, user_plane);
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
