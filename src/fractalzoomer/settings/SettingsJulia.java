package fractalzoomer.settings;




import java.awt.Color;
import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class SettingsJulia extends SettingsFractals implements Serializable {
  private double xJuliaCenter;
  private double yJuliaCenter;

    public SettingsJulia(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int rotation, double[] rotation_center, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, smoothing, function, bailout_test_algorithm, bailout, n_norm, plane_type, burning_ship, z_exponent, z_exponent_complex, color_cycling_location, coefficients, custom_palette, rotation, rotation_center, false, null, false, null, mandel_grass, mandel_grass_vals, z_exponent_nova, relaxation, nova_method);
        this.xJuliaCenter = xJuliaCenter;
        this.yJuliaCenter = yJuliaCenter;

    }

    public double getXJuliaCenter() {

        return xJuliaCenter;

    }

    public double getYJuliaCenter() {

        return yJuliaCenter;

    }

}
