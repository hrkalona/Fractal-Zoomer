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
public class SettingsFractals implements Serializable {
  private boolean burning_ship;
  private boolean mandel_grass;
  private double[] mandel_grass_vals;
  private int function;
  private double xCenter;
  private double yCenter;
  private double size;
  private double z_exponent;
  private double[] z_exponent_complex;
  private double[] z_exponent_nova;
  private double[] relaxation;
  private int nova_method;
  private double bailout;
  private double n_norm;
  private int max_iterations;
  private int color_choice;
  private int[][] custom_palette;
  private Color fractal_color;
  private int bailout_test_algorithm;
  private int out_coloring_algorithm;
  private int in_coloring_algorithm;
  private boolean smoothing;
  private int color_cycling_location;
  private int plane_type;
  private double[] coefficients;
  private int rotation;
  private double[] rotation_center;
  private boolean perturbation;
  private double[] perturbation_vals;
  private boolean init_val;
  private double[] initial_vals;

    public SettingsFractals(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.color_choice = color_choice;
        this.fractal_color = fractal_color;
        this.bailout_test_algorithm = bailout_test_algorithm;
        this.out_coloring_algorithm = out_coloring_algorithm;
        this.in_coloring_algorithm = in_coloring_algorithm;
        this.smoothing = smoothing;
        this.function = function;
        this.burning_ship = burning_ship;
        this.bailout = bailout;
        this.n_norm = n_norm;
        this.plane_type = plane_type;
        this.z_exponent = z_exponent;
        this.z_exponent_complex = z_exponent_complex;
        this.color_cycling_location = color_cycling_location;
        this.coefficients = coefficients;
        this.custom_palette = custom_palette;
        this.rotation = rotation;
        this.rotation_center = rotation_center;
        this.perturbation = perturbation;
        this.perturbation_vals = perturbation_vals;
        this.init_val = init_val;
        this.initial_vals = initial_vals;
        this.mandel_grass = mandel_grass;
        this.mandel_grass_vals = mandel_grass_vals;
        this.z_exponent_nova = z_exponent_nova;
        this.relaxation = relaxation;
        this.nova_method = nova_method;
        
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

    public int getFunction() {

        return function;
        
    }

    public boolean getBurningShip() {

        return burning_ship;
        
    }

    public double getZExponent() {

        return z_exponent;
        
    }

    public double[] getZExponentComplex() {

        return z_exponent_complex;
        
    }
    
    public int getBailoutTestAlgorithm() {

        return bailout_test_algorithm;
        
    }

    public double getBailout() {

        return bailout;
        
    }
    
    public double getNNorm() {

        return n_norm;
        
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
    
    public double[] getRotationCenter() {
        
        return rotation_center;
        
    }
    
    public boolean getPerturbation() {
        
        return perturbation;
        
    }
    
    public double[] getPerturbationVals() {
        
        return perturbation_vals;
        
    }
    
    public boolean getInitVal() {
        
        return init_val;
        
    }
    
    public double[] getInitialVals() {
        
        return initial_vals;
        
    }
    
    public boolean getMandelGrass() {
        
        return mandel_grass;
        
    }
    
    public double[] getMandelGrassVals() {
        
        return mandel_grass_vals;
        
    }
    
    public double[] getZExponentNova() {
        
        return z_exponent_nova;
        
    }
    
    public double[] getRelaxation() {
        
        return relaxation;
        
    }
    
    public int getNovaMethod() {
        
        return nova_method;
        
    }
    
    public boolean getSmoothing() {
        
        return smoothing;
        
    }

}
