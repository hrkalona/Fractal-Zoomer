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
 * @author hrkalona
 */
public class SettingsFractals implements Serializable {
  private static final long serialVersionUID = 6917721625476185938L;

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
  private int color_interpolation;
  private int color_space; 
  private boolean reversed_palette;
  private Color fractal_color;
  private int bailout_test_algorithm;
  private int out_coloring_algorithm;
  private int in_coloring_algorithm;
  private boolean smoothing;
  private int color_cycling_location;
  private int plane_type;
  private double[] coefficients;
  private double rotation;
  private double[] rotation_center;
  private boolean perturbation;
  private double[] perturbation_vals;
  private boolean init_val;
  private double[] initial_vals;
  private String user_formula;
  private String user_plane;
  private int bail_technique;

    public SettingsFractals(double xCenter, double yCenter, double size, int max_iterations, int color_choice, Color fractal_color, int out_coloring_algorithm, int in_coloring_algorithm, boolean smoothing, int function, int bailout_test_algorithm, double bailout, double n_norm, int plane_type, boolean burning_ship, double z_exponent, double[] z_exponent_complex, int color_cycling_location, double[] coefficients, int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, double rotation, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean init_val, double[] initial_vals, boolean mandel_grass, double[] mandel_grass_vals, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, int bail_technique, String user_plane) {

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
        this.color_interpolation = color_interpolation;
        this.color_space = color_space;
        this.reversed_palette = reversed_palette;
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
        this.user_formula = user_formula;
        this.bail_technique = bail_technique;
        this.user_plane = user_plane;
        
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
    
    public double getRotation() {
        
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
    
    public int getColorInterpolation() {
        
        return color_interpolation;
        
    }
    
    public int getColorSpace() {
        
        return color_space;
        
    }
    
    public boolean getReveresedPalette() {
        
        return reversed_palette;
        
    }
    
    public String getUserFormula() {
        
        return user_formula;
        
    }
    
    public String getUserPlane() {
        
        return user_plane;
        
    }
    
    public int getBailTechnique() {
        
        return bail_technique;
        
    }
    
    public int getVersion() {
        
        return 1048;
        
    }
    
    public boolean isJulia() {
        
        return false;
        
    }

}
