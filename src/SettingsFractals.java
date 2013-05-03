

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
public class SettingsFractals implements Serializable {
  private boolean burning_ship;
  private int function;
  private double xCenter;
  private double yCenter;
  private double size;
  private double z_exponent;
  private double bailout;
  private int max_iterations;
  private int color_choice;
  private int[][] custom_palette;
  private Color fractal_color;
  private int bailout_test_algorithm;
  private int out_coloring_algorithm;
  private int in_coloring_algorithm;
  private double color_intensity;
  private int color_cycling_location;
  private int plane_type;
  private double[] coefficients;
  private int rotation;
  private boolean perturbation;
  private double[] perturbation_vals;

    public SettingsFractals(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, double color_intensity, int function, int bailout_test_algorithm, double bailout, int plane_type, boolean burning_ship, double z_exponent, int color_cycling_location, double[] coefficients, int[][] custom_palette, int rotation, boolean perturbation, double[] perturbation_vals) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.color_choice = color_choice;
        this.fractal_color = fractal_color;
        this.bailout_test_algorithm = bailout_test_algorithm;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.in_coloring_algorithm = in_coloring_algorithm;
        this.color_intensity = color_intensity;
        this.function = function;
        this.burning_ship = burning_ship;
        this.bailout = bailout;
        this.plane_type = plane_type;
        this.z_exponent = z_exponent;
        this.color_cycling_location = color_cycling_location;
        this.coefficients = coefficients;
        this.custom_palette = custom_palette;
        this.rotation = rotation;
        this.perturbation = perturbation;
        this.perturbation_vals = perturbation_vals;
        
    }
  
    public double getXCenter() {

        return xCenter;

    }

    public double getYCenter() {

        return yCenter;

    }

    public double getSize() {

        return size;

    }

    public int getMaxIterations() {

        return max_iterations;

    }

    public int getColorChoice() {

        return color_choice;

    }

    public Color getFractalColor() {

        return fractal_color;

    }

    public int getOutColoringAlgorithm() {

        return out_coloring_algorithm;

    }
    
    public int getInColoringAlgorithm() {

        return in_coloring_algorithm;

    }

    public double getColorIntensity() {

        return color_intensity;

    }

    public int getFunction() {

        return function;
        
    }

    public boolean getBurningShip() {

        return burning_ship;
        
    }

    public double getZExponent() {

        return z_exponent;
        
    }
    
    public int getBailoutTestAlgorithm() {

        return bailout_test_algorithm;
        
    }

    public double getBailout() {

        return bailout;
        
    }

    public int getColorCyclingLocation() {

        return color_cycling_location;

    }
    
    public int[][] getCustomPalette() {
        
        return custom_palette;
        
    }

    public int getPlaneType() {

        return plane_type;

    }
    
    public double[] getCoefficients() {
        
        return coefficients;
        
    }
    
    public int getRotation() {
        
        return rotation;
        
    }
    
    public boolean getPerturbation() {
        
        return perturbation;
        
    }
    
    public double[] getPerturbationVals() {
        
        return perturbation_vals;
        
    }

}
