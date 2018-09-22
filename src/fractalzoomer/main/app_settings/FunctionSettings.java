/*
 * Copyright (C) 2018 hrkalona
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
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;


/**
 *
 * @author hrkalona
 */
public class FunctionSettings implements Constants {
    public double[] kleinianLine;
    public double kleinianK;
    public double kleinianM;
    public int plane_type;
    public boolean burning_ship;
    public boolean mandel_grass;
    public double[] coefficients;
    public double[] perturbation_vals;
    public double[] initial_vals;
    public boolean variable_perturbation;
    public String perturbation_user_formula;
    public String[] user_perturbation_conditions;
    public String[] user_perturbation_condition_formula;
    public int user_perturbation_algorithm;
    public boolean variable_init_value;
    public boolean perturbation;
    public boolean init_val;
    public String initial_value_user_formula;
    public String[] user_initial_value_conditions;
    public String[] user_initial_value_condition_formula;
    public int user_initial_value_algorithm;
    public double rotation;
    public double[] rotation_vals;
    public double[] rotation_center;
    public double[] mandel_grass_vals;
    public double[] plane_transform_center;
    public double[] plane_transform_scales;
    public double[] plane_transform_wavelength;
    public int waveType;
    public int function;
    public int nova_method;
    public boolean apply_plane_on_julia;
    public boolean apply_plane_on_julia_seed; 
    public double coupling;
    public int coupling_method;
    public double coupling_amplitude;
    public double coupling_frequency;
    public int coupling_seed;
    public double plane_transform_angle;
    public double plane_transform_angle2;
    public double plane_transform_radius;
    public double plane_transform_amount;
    public int plane_transform_sides;
    public double n_norm;
    public double z_exponent;
    public double[] z_exponent_complex;
    public double[] z_exponent_nova;
    public double[] laguerre_deg;
    public double[] relaxation;
    public String user_formula;
    public String user_formula2;
    public String[] user_formula_iteration_based;
    public String[] user_formula_conditions;
    public String[] user_formula_condition_formula;
    public String[] user_formula_coupled;
    public int user_plane_algorithm;
    public String user_plane;
    public String[] user_plane_conditions;
    public String[] user_plane_condition_formula;
    public String user_fz_formula;
    public String user_dfz_formula;
    public String user_ddfz_formula;
    public int bail_technique;
    public boolean julia;
    public int bailout_test_algorithm;
    public double bailout;
    public String bailout_test_user_formula;
    public String bailout_test_user_formula2;
    public int bailout_test_comparison;
    public int out_coloring_algorithm;
    public int in_coloring_algorithm;
    public int user_in_coloring_algorithm;
    public int user_out_coloring_algorithm;
    public String outcoloring_formula;
    public String[] user_outcoloring_conditions;
    public String[] user_outcoloring_condition_formula;
    public String incoloring_formula;
    public String[] user_incoloring_conditions;
    public String[] user_incoloring_condition_formula;
    public int escaping_smooth_algorithm;
    public int converging_smooth_algorithm;
    public boolean smoothing;
    
    public FunctionSettings() {
        n_norm = 2;
        z_exponent = 2;
        
        smoothing = false;
        
        escaping_smooth_algorithm = 0;
        converging_smooth_algorithm = 0;

        nova_method = NOVA_NEWTON;
        
        bailout = 2;
        bailout_test_algorithm = 0;

        bailout_test_user_formula = "norm(z)";
        bailout_test_user_formula2 = "bail";
        bailout_test_comparison = GREATER_EQUAL;
        
        plane_type = MU_PLANE;
        
        rotation_vals = new double[2];

        rotation = 0;
        
        rotation_vals[0] = Math.cos(Math.toRadians(rotation));
        rotation_vals[1] = Math.sin(Math.toRadians(rotation));

        rotation_center = new double[2];

        rotation_center[0] = 0;
        rotation_center[1] = 0;

        plane_transform_scales = new double[2];
        plane_transform_scales[0] = 0;
        plane_transform_scales[1] = 0;

        plane_transform_center = new double[2];
        plane_transform_center[0] = 0;
        plane_transform_center[1] = 0;

        plane_transform_wavelength = new double[2];
        plane_transform_wavelength[0] = 0.15;
        plane_transform_wavelength[1] = 0.15;

        waveType = 0;

        plane_transform_angle = 0;
        plane_transform_angle2 = 0;
        plane_transform_radius = 2;
        plane_transform_sides = 3;
        plane_transform_amount = 0;

        perturbation_vals = new double[2];
        perturbation_vals[0] = 0;
        perturbation_vals[1] = 0;

        initial_vals = new double[2];
        initial_vals[0] = 0;
        initial_vals[1] = 0;

        laguerre_deg = new double[2];
        laguerre_deg[0] = 10;
        laguerre_deg[1] = 0;

        apply_plane_on_julia = false;
        apply_plane_on_julia_seed = true;

        variable_perturbation = false;
        perturbation_user_formula = "0.0";
        variable_init_value = false;
        initial_value_user_formula = "c";

        user_perturbation_algorithm = 0;
        user_perturbation_conditions = new String[2];
        user_perturbation_conditions[0] = "im(c)";
        user_perturbation_conditions[1] = "0.0";
        user_perturbation_condition_formula = new String[3];
        user_perturbation_condition_formula[0] = "0.5";
        user_perturbation_condition_formula[1] = "0.0";
        user_perturbation_condition_formula[2] = "0.0";

        user_initial_value_algorithm = 0;
        user_initial_value_conditions = new String[2];
        user_initial_value_conditions[0] = "re(c)";
        user_initial_value_conditions[1] = "0.0";
        user_initial_value_condition_formula = new String[3];
        user_initial_value_condition_formula[0] = "c / 2";
        user_initial_value_condition_formula[1] = "c";
        user_initial_value_condition_formula[2] = "c";

        z_exponent_complex = new double[2];
        z_exponent_complex[0] = 2;
        z_exponent_complex[1] = 0;

        mandel_grass_vals = new double[2];
        mandel_grass_vals[0] = 0.125;
        mandel_grass_vals[1] = 0;

        z_exponent_nova = new double[2];
        z_exponent_nova[0] = 3;
        z_exponent_nova[1] = 0;

        relaxation = new double[2];
        relaxation[0] = 1;
        relaxation[1] = 0;

        perturbation = false;
        init_val = false;
        
        user_fz_formula = "z^3 - 1";
        user_dfz_formula = "3*z^2";
        user_ddfz_formula = "6*z";
        
        user_formula = "z^2 + c";
        user_formula2 = "c";

        user_plane = "z";
        user_plane_algorithm = 0;

        user_plane_conditions = new String[2];
        user_plane_conditions[0] = "im(z)";
        user_plane_conditions[1] = "0.125";

        user_plane_condition_formula = new String[3];
        user_plane_condition_formula[0] = "re(z) +(im(z) - 2 * (im(z) - 0.125)) * i";
        user_plane_condition_formula[1] = "z";
        user_plane_condition_formula[2] = "z";

        user_formula_iteration_based = new String[4];
        user_formula_iteration_based[0] = "z^2 + c";
        user_formula_iteration_based[1] = "z^3 + c";
        user_formula_iteration_based[2] = "z^4 + c";
        user_formula_iteration_based[3] = "z^5 + c";

        user_formula_conditions = new String[2];
        user_formula_conditions[0] = "re(z)";
        user_formula_conditions[1] = "0.0";

        user_formula_condition_formula = new String[3];
        user_formula_condition_formula[0] = "(z - 1) * c";
        user_formula_condition_formula[1] = "(z + 1) * c";
        user_formula_condition_formula[2] = "(z - 1) * c";

        user_formula_coupled = new String[3];
        user_formula_coupled[0] = "z^2 + c";
        user_formula_coupled[1] = "abs(z)^2 + c";
        user_formula_coupled[2] = "c";

        coupling = 0.1;
        coupling_method = 0;
        coupling_amplitude = 0.5;
        coupling_frequency = 0.001;
        coupling_seed = 1;
        
        julia = false;
        burning_ship = false;
        mandel_grass = false;
        
        bail_technique = 0;

        function = 0;

        coefficients = new double[11];
        coefficients[7] = 1;
        coefficients[10] = -1;
        
        kleinianLine = new double[2];
        kleinianLine[0] = 1.93;
        kleinianLine[1] = 0.04;            
        kleinianK = 0.26;
        kleinianM = 4.9;
        
        out_coloring_algorithm = ESCAPE_TIME;
        in_coloring_algorithm = MAX_ITERATIONS;

        user_in_coloring_algorithm = 0;
        user_out_coloring_algorithm = 0;

        outcoloring_formula = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

        user_outcoloring_conditions = new String[2];
        user_outcoloring_conditions[0] = "im(z)";
        user_outcoloring_conditions[1] = "0.0";

        user_outcoloring_condition_formula = new String[3];
        user_outcoloring_condition_formula[0] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";
        user_outcoloring_condition_formula[1] = "-(n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001)) + 50)";
        user_outcoloring_condition_formula[2] = "n + (log(bail) - log(norm(p) + 0.000000001)) / (log(norm(z)) - log(norm(p) + 0.000000001))";

        incoloring_formula = "norm(sin(z)) * 100";

        user_incoloring_conditions = new String[2];
        user_incoloring_conditions[0] = "im(z)";
        user_incoloring_conditions[1] = "0.0";

        user_incoloring_condition_formula = new String[3];
        user_incoloring_condition_formula[0] = "norm(sin(z)) * 100";
        user_incoloring_condition_formula[1] = "-(norm(sin(z)) * 100 + 50)";
        user_incoloring_condition_formula[2] = "norm(sin(z)) * 100";
    }
    
}