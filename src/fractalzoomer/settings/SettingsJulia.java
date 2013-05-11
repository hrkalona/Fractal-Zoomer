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

    public SettingsJulia(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, int function, int bailout_test_algorithm, double bailout, int plane_type, boolean burning_ship, double z_exponent, int color_cycling_location, double[] coefficients, int[][] custom_palette, int rotation, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, color_choice, fractal_color, out_coloring_algorithm, in_coloring_algorithm, function, bailout_test_algorithm, bailout, plane_type, burning_ship, z_exponent, color_cycling_location, coefficients, custom_palette, rotation, false, null);
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
