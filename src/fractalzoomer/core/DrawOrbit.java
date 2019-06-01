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
package fractalzoomer.core;

import fractalzoomer.core.iteration_algorithm.*;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.main.MainWindow;
import fractalzoomer.functions.barnsley.*;
import fractalzoomer.functions.math.*;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.formulas.coupled.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.*;
import fractalzoomer.functions.formulas.kaliset.*;
import fractalzoomer.functions.formulas.m_like_generalization.*;
import fractalzoomer.functions.general.FrothyBasin;
import fractalzoomer.functions.root_finding_methods.halley.*;
import fractalzoomer.functions.root_finding_methods.householder.*;
import fractalzoomer.functions.lambda.*;
import fractalzoomer.functions.magnet.*;
import fractalzoomer.functions.mandelbrot.*;
import fractalzoomer.functions.general.*;
import fractalzoomer.functions.root_finding_methods.newton.*;
import fractalzoomer.functions.root_finding_methods.schroder.*;
import fractalzoomer.functions.formulas.general.mathtype.*;
import fractalzoomer.functions.formulas.general.newtonvariant.*;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.*;
import fractalzoomer.functions.root_finding_methods.bairstow.*;
import fractalzoomer.functions.root_finding_methods.durand_kerner.*;
import fractalzoomer.functions.root_finding_methods.laguerre.*;
import fractalzoomer.functions.root_finding_methods.muller.*;
import fractalzoomer.functions.root_finding_methods.newton_hines.*;
import fractalzoomer.functions.root_finding_methods.parhalley.*;
import fractalzoomer.functions.user_formulas.*;
import fractalzoomer.functions.root_finding_methods.secant.*;
import fractalzoomer.functions.root_finding_methods.steffensen.*;
import fractalzoomer.functions.szegedi_butterfly.*;
import fractalzoomer.main.app_settings.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class DrawOrbit extends Thread {
    
    protected Fractal pixel_orbit;
    protected IterationAlgorithm iteration_algorithm;
    protected ArrayList<Complex> complex_orbit;
    protected MainWindow ptr;
    protected int orbit_style;
    protected int image_size;
    protected Color orbit_color;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    protected int pixel_x;
    protected int pixel_y;
    protected boolean show_converging_point;
    protected double[] rotation_vals;
    protected double[] rotation_center;
    
    public DrawOrbit(Settings s, int pixel_x, int pixel_y, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, boolean show_converging_point) {
        
        if(!s.fns.julia) {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, s.max_iterations > 400 ? 400 : s.max_iterations, pixel_x, pixel_y, image_size, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, s.fns.rotation_vals, s.fns.rotation_center, s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.perturbation_user_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.initial_value_user_formula, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.laguerre_deg, s.fns.kleinianLine, s.fns.kleinianK, s.fns.kleinianM, s.fns.gcs, s.fns.durand_kerner_init_val, s.fns.mps, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.gcps, s.fns.igs, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue);
        }
        else {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, s.max_iterations > 400 ? 400 : s.max_iterations, pixel_x, pixel_y, image_size, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, s.fns.rotation_vals, s.fns.rotation_center, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.gcs, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.laguerre_deg, s.fns.gcps, s.fns.lfns, s.fns.newton_hines_k, s.xJuliaCenter, s.yJuliaCenter);
        }
        
    }
    
    public DrawOrbit(Settings s, double pixel_x, double pixel_y, int sec_points, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, boolean show_converging_point) {
        
        if(!s.fns.julia) {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, sec_points, pixel_x, pixel_y, image_size, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, s.fns.rotation_vals, s.fns.rotation_center, s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.perturbation_user_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.initial_value_user_formula, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.laguerre_deg, s.fns.kleinianLine, s.fns.kleinianK, s.fns.kleinianM, s.fns.gcs, s.fns.durand_kerner_init_val, s.fns.mps, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.gcps, s.fns.igs, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue);
        }
        else {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, sec_points, pixel_x, pixel_y, image_size, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, s.fns.rotation_vals, s.fns.rotation_center, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.gcs, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.laguerre_deg, s.fns.gcps, s.fns.lfns, s.fns.newton_hines_k, s.xJuliaCenter, s.yJuliaCenter);
        }
        
    }
    
    private void InitOrbitSettings(double xCenter, double yCenter, double size, int max_iterations, int pixel_x, int pixel_y, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double[] laguerre_deg, double[] kleinianLine, double kleinianK, double kleinianM, GenericCaZbdZeSettings gcs, double[] durand_kerner_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue) {
        
        this.image_size = image_size;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = pixel_x;
        this.pixel_y = pixel_y;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;        
        
        if(polar_projection) {
            double start;
            double center = Math.log(size);
            
            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image_size;
            
            double mulx = muly * height_ratio;
            
            start = center - mulx * image_size * 0.5;
            
            f = pixel_y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            
            r = Math.exp(pixel_x * mulx + start);
            
            double xPixel = xCenter + r * cf;
            double yPixel = yCenter + r * sf;
            
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(new Complex(xPixel, yPixel));
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;
            
            double xPixel = xCenter - size_2_x + temp_size_image_size_x * pixel_x;
            double yPixel = yCenter + size_2_y - temp_size_image_size_y * pixel_y;
            
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(new Complex(xPixel, yPixel));
        }
        
        orbitFractalFactory(function, xCenter, yCenter, size, max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, user_fz_formula, user_dfz_formula, user_ddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kerner_init_val, mps, lyapunovFunction, lyapunovExponentFunction, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, lyapunovInitialValue);
        
        iteration_algorithm = new FractalIterationAlgorithm(pixel_orbit);
 
        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
        
    }
    
    private void InitOrbitSettings(double xCenter, double yCenter, double size, int max_iterations, double pixel_x, double pixel_y, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double[] laguerre_deg, double[] kleinianLine, double kleinianK, double kleinianM, GenericCaZbdZeSettings gcs, double[] durand_kerner_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue) {
        
        this.image_size = image_size;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = -1;
        this.pixel_y = -1;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;        
        
        complex_orbit = new ArrayList<>(max_iterations + 1);
        complex_orbit.add(new Complex(pixel_x, pixel_y));
        
        orbitFractalFactory(function, xCenter, yCenter, size, max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, user_fz_formula, user_dfz_formula, user_ddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kerner_init_val, mps, lyapunovFunction, lyapunovExponentFunction, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, lyapunovInitialValue);
        
        iteration_algorithm = new FractalIterationAlgorithm(pixel_orbit);

        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
   
    }
    
    private void InitOrbitSettings(double xCenter, double yCenter, double size, int max_iterations, int pixel_x, int pixel_y, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {
        
        this.image_size = image_size;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = pixel_x;
        this.pixel_y = pixel_y;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;        
        
        if(polar_projection) {
            double start;
            double center = Math.log(size);
            
            double f, sf, cf, r;
            double muly = (2 * circle_period * Math.PI) / image_size;
            
            double mulx = muly * height_ratio;
            
            start = center - mulx * image_size * 0.5;
            
            f = pixel_y * muly;
            sf = Math.sin(f);
            cf = Math.cos(f);
            
            r = Math.exp(pixel_x * mulx + start);
            
            double xPixel = xCenter + r * cf;
            double yPixel = yCenter + r * sf;
            
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(new Complex(xPixel, yPixel));
        }
        else {
            double size_2_x = size * 0.5;
            double size_2_y = (size * height_ratio) * 0.5;
            double temp_size_image_size_x = size / image_size;
            double temp_size_image_size_y = (size * height_ratio) / image_size;
            
            double xPixel = xCenter - size_2_x + temp_size_image_size_x * pixel_x;
            double yPixel = yCenter + size_2_y - temp_size_image_size_y * pixel_y;
            
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(new Complex(xPixel, yPixel));
        }
        
        orbitJuliaFactory(function, xCenter, yCenter, size, max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, xJuliaCenter, yJuliaCenter);
        
        iteration_algorithm = new JuliaIterationAlgorithm(pixel_orbit);
        
        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
     
    }
    
    private void InitOrbitSettings(double xCenter, double yCenter, double size, int max_iterations, double pixel_x, double pixel_y, int image_size, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {
        
        this.image_size = image_size;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = -1;
        this.pixel_y = -1;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;        
        
        complex_orbit = new ArrayList<>(max_iterations + 1);
        complex_orbit.add(new Complex(pixel_x, pixel_y));
        
        orbitJuliaFactory(function, xCenter, yCenter, size, max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, xJuliaCenter, yJuliaCenter);
        
        iteration_algorithm = new JuliaIterationAlgorithm(pixel_orbit);

        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
        
    }
    
    @Override
    public void run() {
        draw();
    }
    
    protected void draw() {
        
        iteration_algorithm.calculateOrbit();
        ptr.getMainPanel().repaint();
        
    }
    
    public void drawLine(Graphics2D full_image_g) {
        int x0, y0, x1 = 0, y1 = 0, list_size;
        
        full_image_g.setColor(orbit_color);
        
        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        full_image_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        list_size = complex_orbit.size() - 1;
        
        double size = pixel_orbit.getSize();
        
        double start = 0, center, f0, r0, f1, r1, xcenter = 0, ycenter = 0, mulx = 0, muly = 0;
        
        double size_2_x, size_2_y, temp_xcenter_size = 0, temp_ycenter_size = 0, temp_size_image_size_x = 0, temp_size_image_size_y = 0;
        
        if(polar_projection) {
            center = Math.log(size);
            
            xcenter = pixel_orbit.getXCenter();
            ycenter = pixel_orbit.getYCenter();
            
            muly = (2 * circle_period * Math.PI) / image_size;
            
            mulx = muly * height_ratio;
            
            start = center - mulx * image_size * 0.5;
        }
        else {
            size_2_x = size * 0.5;
            size_2_y = (size * height_ratio) * 0.5;
            temp_xcenter_size = pixel_orbit.getXCenter() - size_2_x;
            temp_ycenter_size = pixel_orbit.getYCenter() + size_2_y;
            temp_size_image_size_x = size / image_size;
            temp_size_image_size_y = (size * height_ratio) / image_size;
        }
        
        full_image_g.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics metrics = full_image_g.getFontMetrics();
        
        Rectangle2D rect = metrics.getStringBounds("*", full_image_g);
        int width = (int)rect.getWidth();
        int height = (int)rect.getHeight();
        
        for(int i = 0; i < list_size; i++) {
            
            if(polar_projection) {
                Complex n0 = complex_orbit.get(i).sub(new Complex(xcenter, ycenter));
                r0 = n0.norm();
                f0 = n0.arg();
                f0 = f0 < 0 ? f0 + 2 * Math.PI : f0;
                
                x0 = (int)((Math.log(r0) - start) / mulx + 0.5);
                y0 = (int)((f0 / muly + 0.5));
                
                Complex n1 = complex_orbit.get(i + 1).sub(new Complex(xcenter, ycenter));
                r1 = n1.norm();
                f1 = n1.arg();
                
                f1 = f1 < 0 ? f1 + 2 * Math.PI : f1;
                x1 = (int)((Math.log(r1) - start) / mulx + 0.5);
                y1 = (int)((f1 / muly + 0.5));
                
            }
            else {
                x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size_x + 0.5);
                y0 = (int)((-complex_orbit.get(i).getIm() + temp_ycenter_size) / temp_size_image_size_y + 0.5);
                x1 = (int)((complex_orbit.get(i + 1).getRe() - temp_xcenter_size) / temp_size_image_size_x + 0.5);
                y1 = (int)((-complex_orbit.get(i + 1).getIm() + temp_ycenter_size) / temp_size_image_size_y + 0.5);
            }
            
            if(x0 == Integer.MIN_VALUE || x0 == Integer.MAX_VALUE || x1 == Integer.MIN_VALUE || x1 == Integer.MAX_VALUE || y0 == Integer.MIN_VALUE || y0 == Integer.MAX_VALUE || y1 == Integer.MIN_VALUE || y1 == Integer.MAX_VALUE) {
                return;
            }
            
            full_image_g.drawLine(x0, y0, x1, y1);
            if(i == 0) {
                if(polar_projection && circle_period > 1 && pixel_x != -1 && pixel_y != -1 && Math.abs(y0 - pixel_y) != 0) {
                    full_image_g.drawLine(pixel_x, pixel_y, x0, y0);
                    full_image_g.drawString("*", pixel_x - width / 2, pixel_y + height / 2);
                    full_image_g.fillOval(x0 - 2, y0 - 2, 5, 5);
                }
                else {
                    full_image_g.drawString("*", x0 - width / 2, y0 + height / 2);
                }
            }
            else {
                full_image_g.fillOval(x0 - 2, y0 - 2, 5, 5);
            }
        }
        
        if(list_size > 0) {
            full_image_g.fillOval(x1 - 2, y1 - 2, 5, 5);
            
            if(show_converging_point) {
                if(complex_orbit.get(complex_orbit.size() - 1).distance(complex_orbit.get(complex_orbit.size() - 2)) < 1e-6) {
                    Complex last = complex_orbit.get(complex_orbit.size() - 1);
                    Rotation rot = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);
                    
                    full_image_g.drawString(rot.rotate(last).toString(), x1 + 15, y1 + 15);
                }
            }
        }
        
    }
    
    public void drawDot(Graphics2D full_image_g) {
        int x0 = 0, y0 = 0, list_size;
        
        full_image_g.setColor(orbit_color);
        
        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        full_image_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
 
        
        list_size = complex_orbit.size();
        
        double size = pixel_orbit.getSize();
        
        double start = 0, center, f0, r0, xcenter = 0, ycenter = 0, mulx = 0, muly = 0;
        
        double size_2_x, size_2_y, temp_xcenter_size = 0, temp_ycenter_size = 0, temp_size_image_size_x = 0, temp_size_image_size_y = 0;
        
        if(polar_projection) {
            center = Math.log(size);
            
            xcenter = pixel_orbit.getXCenter();
            ycenter = pixel_orbit.getYCenter();
            
            muly = (2 * circle_period * Math.PI) / image_size;
            
            mulx = muly * height_ratio;
            
            start = center - mulx * image_size * 0.5;
        }
        else {
            size_2_x = size * 0.5;
            size_2_y = (size * height_ratio) * 0.5;
            temp_xcenter_size = pixel_orbit.getXCenter() - size_2_x;
            temp_ycenter_size = pixel_orbit.getYCenter() + size_2_y;
            temp_size_image_size_x = size / image_size;
            temp_size_image_size_y = (size * height_ratio) / image_size;
        }
        
        full_image_g.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics metrics = full_image_g.getFontMetrics();
        
        Rectangle2D rect = metrics.getStringBounds("*", full_image_g);
        int width = (int)rect.getWidth();
        int height = (int)rect.getHeight();
        
        for(int i = 0; i < list_size; i++) {
            if(polar_projection) {
                Complex n0 = complex_orbit.get(i).sub(new Complex(xcenter, ycenter));
                r0 = n0.norm();
                f0 = n0.arg();
                f0 = f0 < 0 ? f0 + 2 * Math.PI : f0;
                
                x0 = (int)((Math.log(r0) - start) / mulx + 0.5);
                y0 = (int)((f0 / muly + 0.5));
            }
            else {
                x0 = (int)((complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size_x + 0.5);
                y0 = (int)((-complex_orbit.get(i).getIm() + temp_ycenter_size) / temp_size_image_size_y + 0.5);
            }
            
            if(x0 == Integer.MIN_VALUE || x0 == Integer.MAX_VALUE || y0 == Integer.MIN_VALUE || y0 == Integer.MAX_VALUE) {
                return;
            }
            
            if(i == 0) {
                if(polar_projection && circle_period > 1 && pixel_x != -1 && pixel_y != -1 && Math.abs(y0 - pixel_y) != 0) {
                    full_image_g.drawString("*", pixel_x - width / 2, pixel_y + height / 2);
                    full_image_g.fillOval(x0 - 2, y0 - 2, 5, 5);
                }
                else {
                    full_image_g.drawString("*", x0 - width / 2, y0 + height / 2);
                }
            }
            else {
                full_image_g.fillOval(x0 - 2, y0 - 2, 5, 5);
            }
        }
        
        if(list_size > 1) {            
            if(show_converging_point) {
                if(complex_orbit.get(complex_orbit.size() - 1).distance(complex_orbit.get(complex_orbit.size() - 2)) < 1e-6) {
                    Complex last = complex_orbit.get(complex_orbit.size() - 1);
                    Rotation rot = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);
                    
                    full_image_g.drawString(rot.rotate(last).toString(), x0 + 15, y0 + 15);
                }
            }
        }
        
    }
    
    
    private void orbitJuliaFactory(int function, double xCenter, double yCenter, double size, int max_iterations, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double z_exponent, double[] z_exponent_complex, double[] coefficients, double[] coefficients_im, double[] z_exponent_nova, double[] relaxation, int nova_method, int bail_technique, String user_formula, String user_formula2, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {
        
        switch (function) {
            case MainWindow.MANDELBROT:
                pixel_orbit = new Mandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTCUBED:
                pixel_orbit = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFOURTH:
                pixel_orbit = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTFIFTH:
                pixel_orbit = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSIXTH:
                pixel_orbit = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                pixel_orbit = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                pixel_orbit = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNINTH:
                pixel_orbit = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTTENTH:
                pixel_orbit = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTNTH:
                pixel_orbit = new MandelbrotNth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBROTWTH:
                pixel_orbit = new MandelbrotWth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELPOLY:
                pixel_orbit = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA:
                pixel_orbit = new Lambda(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA2:
                pixel_orbit = new Lambda2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA3:
                pixel_orbit = new Lambda3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBDA_FN_FN:
                pixel_orbit = new LambdaFnFn(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, lfns, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET1:
                pixel_orbit = new Magnet1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MAGNET2:
                pixel_orbit = new Magnet2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY1:
                pixel_orbit = new Barnsley1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY2:
                pixel_orbit = new Barnsley2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.BARNSLEY3:
                pixel_orbit = new Barnsley3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDELBAR:
                pixel_orbit = new Mandelbar(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SPIDER:
                pixel_orbit = new Spider(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANOWAR:
                pixel_orbit = new Manowar(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.PHOENIX:
                pixel_orbit = new Phoenix(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.NOVA:
                pixel_orbit = new Nova(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, newton_hines_k, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.EXP:
                pixel_orbit = new Exp(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LOG:
                pixel_orbit = new Log(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SIN:
                pixel_orbit = new Sin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COS:
                pixel_orbit = new Cos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TAN:
                pixel_orbit = new Tan(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COT:
                pixel_orbit = new Cot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SINH:
                pixel_orbit = new Sinh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COSH:
                pixel_orbit = new Cosh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.TANH:
                pixel_orbit = new Tanh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COTH:
                pixel_orbit = new Coth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA30:
                pixel_orbit = new Formula30(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA31:
                pixel_orbit = new Formula31(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA1:
                pixel_orbit = new Formula1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA2:
                pixel_orbit = new Formula2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA3:
                pixel_orbit = new Formula3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA4:
                pixel_orbit = new Formula4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA5:
                pixel_orbit = new Formula5(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA6:
                pixel_orbit = new Formula6(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA7:
                pixel_orbit = new Formula7(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA8:
                pixel_orbit = new Formula8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA9:
                pixel_orbit = new Formula9(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA10:
                pixel_orbit = new Formula10(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA11:
                pixel_orbit = new Formula11(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA12:
                pixel_orbit = new Formula12(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA13:
                pixel_orbit = new Formula13(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA14:
                pixel_orbit = new Formula14(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA15:
                pixel_orbit = new Formula15(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA16:
                pixel_orbit = new Formula16(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA17:
                pixel_orbit = new Formula17(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA18:
                pixel_orbit = new Formula18(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA19:
                pixel_orbit = new Formula19(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA20:
                pixel_orbit = new Formula20(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA21:
                pixel_orbit = new Formula21(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA22:
                pixel_orbit = new Formula22(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA23:
                pixel_orbit = new Formula23(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA24:
                pixel_orbit = new Formula24(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA25:
                pixel_orbit = new Formula25(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA26:
                pixel_orbit = new Formula26(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA27:
                pixel_orbit = new Formula27(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA28:
                pixel_orbit = new Formula28(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA29:
                pixel_orbit = new Formula29(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA32:
                pixel_orbit = new Formula32(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA33:
                pixel_orbit = new Formula33(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA34:
                pixel_orbit = new Formula34(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA35:
                pixel_orbit = new Formula35(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA36:
                pixel_orbit = new Formula36(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA37:
                pixel_orbit = new Formula37(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA38:
                pixel_orbit = new Formula38(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA39:
                pixel_orbit = new Formula39(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA40:
                pixel_orbit = new Formula40(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA41:
                pixel_orbit = new Formula41(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA42:
                pixel_orbit = new Formula42(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA43:
                pixel_orbit = new Formula43(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA44:
                pixel_orbit = new Formula44(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA45:
                pixel_orbit = new Formula45(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.FORMULA46:
                pixel_orbit = new Formula46(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                else {
                    pixel_orbit = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                else {
                    pixel_orbit = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                else {
                    pixel_orbit = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                else {
                    pixel_orbit = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, xJuliaCenter, yJuliaCenter);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                pixel_orbit = new FrothyBasin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                pixel_orbit = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                pixel_orbit = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                pixel_orbit = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                pixel_orbit = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.GENERIC_CaZbdZe:
                pixel_orbit = new GenericCaZbdZe(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, gcs, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LYAPUNOV:
                pixel_orbit = new Lyapunov(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.USER_FORMULA_NOVA:
                pixel_orbit = new UserFormulaNova(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, newton_hines_k, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.GENERIC_CpAZpBC:
                pixel_orbit = new GenericCpAZpBC(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, gcps, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.MANDEL_NEWTON:
                pixel_orbit = new MandelNewton(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            case MainWindow.LAMBERT_W_VARIATION:
                pixel_orbit = new LambertWVariation(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
                break;
            
        }
        
    }
    
    private void orbitFractalFactory(int function, double xCenter, double yCenter, double size, int max_iterations, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int plane_type, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double z_exponent, double[] z_exponent_complex, double[] coefficients, double[] coefficients_im, double[] z_exponent_nova, double[] relaxation, int nova_method, int bail_technique, String user_formula, String user_formula2, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, String[] lyapunovExpression, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, double[] kleinianLine, double kleinianK, double kleinianM, double[] laguerre_deg, double[] durand_kerner_init_val, MagneticPendulumSettings mps, String lyapunovFunction, String lyapunovExponentFunction, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue) {
        
        switch (function) {
            case MainWindow.MANDELBROT:
                pixel_orbit = new Mandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);               
                break;
            case MainWindow.MANDELBROTCUBED:
                pixel_orbit = new MandelbrotCubed(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTFOURTH:
                pixel_orbit = new MandelbrotFourth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTFIFTH:
                pixel_orbit = new MandelbrotFifth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTSIXTH:
                pixel_orbit = new MandelbrotSixth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTSEVENTH:
                pixel_orbit = new MandelbrotSeventh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTEIGHTH:
                pixel_orbit = new MandelbrotEighth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTNINTH:
                pixel_orbit = new MandelbrotNinth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTTENTH:
                pixel_orbit = new MandelbrotTenth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTNTH:
                pixel_orbit = new MandelbrotNth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBROTWTH:
                pixel_orbit = new MandelbrotWth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, z_exponent_complex, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELPOLY:
                pixel_orbit = new MandelbrotPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, burning_ship, mandel_grass, mandel_grass_vals, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.LAMBDA:
                pixel_orbit = new Lambda(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAMBDA2:
                pixel_orbit = new Lambda2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAMBDA3:
                pixel_orbit = new Lambda3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAMBDA_FN_FN:
                pixel_orbit = new LambdaFnFn(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, lfns);
                break;
            case MainWindow.MAGNET1:
                pixel_orbit = new Magnet1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MAGNET2:
                pixel_orbit = new Magnet2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON3:
                pixel_orbit = new Newton3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON4:
                pixel_orbit = new Newton4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTONGENERALIZED3:
                pixel_orbit = new NewtonGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTONGENERALIZED8:
                pixel_orbit = new NewtonGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTONSIN:
                pixel_orbit = new NewtonSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTONCOS:
                pixel_orbit = new NewtonCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTONPOLY:
                pixel_orbit = new NewtonPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.NEWTONFORMULA:
                pixel_orbit = new NewtonFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula);
                break;
            case MainWindow.BARNSLEY1:
                pixel_orbit = new Barnsley1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BARNSLEY2:
                pixel_orbit = new Barnsley2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BARNSLEY3:
                pixel_orbit = new Barnsley3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANDELBAR:
                pixel_orbit = new Mandelbar(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SPIDER:
                pixel_orbit = new Spider(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MANOWAR:
                pixel_orbit = new Manowar(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PHOENIX:
                pixel_orbit = new Phoenix(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SIERPINSKI_GASKET:
                pixel_orbit = new SierpinskiGasket(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.KLEINIAN:
                pixel_orbit = new Kleinian(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, kleinianLine, kleinianK, kleinianM);
                break;
            case MainWindow.HALLEY3:
                pixel_orbit = new Halley3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEY4:
                pixel_orbit = new Halley4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEYGENERALIZED3:
                pixel_orbit = new HalleyGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEYGENERALIZED8:
                pixel_orbit = new HalleyGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEYSIN:
                pixel_orbit = new HalleySin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEYCOS:
                pixel_orbit = new HalleyCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HALLEYPOLY:
                pixel_orbit = new HalleyPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.HALLEYFORMULA:
                pixel_orbit = new HalleyFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.SCHRODER3:
                pixel_orbit = new Schroder3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODER4:
                pixel_orbit = new Schroder4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODERGENERALIZED3:
                pixel_orbit = new SchroderGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODERGENERALIZED8:
                pixel_orbit = new SchroderGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODERSIN:
                pixel_orbit = new SchroderSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODERCOS:
                pixel_orbit = new SchroderCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SCHRODERPOLY:
                pixel_orbit = new SchroderPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.SCHRODERFORMULA:
                pixel_orbit = new SchroderFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.HOUSEHOLDER3:
                pixel_orbit = new Householder3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDER4:
                pixel_orbit = new Householder4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED3:
                pixel_orbit = new HouseholderGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDERGENERALIZED8:
                pixel_orbit = new HouseholderGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDERSIN:
                pixel_orbit = new HouseholderSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDERCOS:
                pixel_orbit = new HouseholderCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.HOUSEHOLDERPOLY:
                pixel_orbit = new HouseholderPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.HOUSEHOLDERFORMULA:
                pixel_orbit = new HouseholderFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.SECANT3:
                pixel_orbit = new Secant3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SECANT4:
                pixel_orbit = new Secant4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SECANTGENERALIZED3:
                pixel_orbit = new SecantGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SECANTGENERALIZED8:
                pixel_orbit = new SecantGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SECANTCOS:
                pixel_orbit = new SecantCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SECANTPOLY:
                pixel_orbit = new SecantPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.SECANTFORMULA:
                pixel_orbit = new SecantFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula);
                break;
            case MainWindow.STEFFENSEN3:
                pixel_orbit = new Steffensen3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.STEFFENSEN4:
                pixel_orbit = new Steffensen4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.STEFFENSENGENERALIZED3:
                pixel_orbit = new SteffensenGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.STEFFENSENPOLY:
                pixel_orbit = new SteffensenPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.STEFFENSENFORMULA:
                pixel_orbit = new SteffensenFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula);
                break;
            case MainWindow.NOVA:
                pixel_orbit = new Nova(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, z_exponent_nova, relaxation, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, newton_hines_k);
                break;
            case MainWindow.EXP:
                pixel_orbit = new Exp(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LOG:
                pixel_orbit = new Log(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SIN:
                pixel_orbit = new Sin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COS:
                pixel_orbit = new Cos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.TAN:
                pixel_orbit = new Tan(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COT:
                pixel_orbit = new Cot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SINH:
                pixel_orbit = new Sinh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COSH:
                pixel_orbit = new Cosh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.TANH:
                pixel_orbit = new Tanh(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COTH:
                pixel_orbit = new Coth(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA30:
                pixel_orbit = new Formula30(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA31:
                pixel_orbit = new Formula31(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA1:
                pixel_orbit = new Formula1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA2:
                pixel_orbit = new Formula2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA3:
                pixel_orbit = new Formula3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA4:
                pixel_orbit = new Formula4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA5:
                pixel_orbit = new Formula5(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA6:
                pixel_orbit = new Formula6(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA7:
                pixel_orbit = new Formula7(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA8:
                pixel_orbit = new Formula8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA9:
                pixel_orbit = new Formula9(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA10:
                pixel_orbit = new Formula10(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA11:
                pixel_orbit = new Formula11(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA12:
                pixel_orbit = new Formula12(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA13:
                pixel_orbit = new Formula13(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA14:
                pixel_orbit = new Formula14(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA15:
                pixel_orbit = new Formula15(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA16:
                pixel_orbit = new Formula16(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA17:
                pixel_orbit = new Formula17(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA18:
                pixel_orbit = new Formula18(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA19:
                pixel_orbit = new Formula19(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA20:
                pixel_orbit = new Formula20(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA21:
                pixel_orbit = new Formula21(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA22:
                pixel_orbit = new Formula22(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA23:
                pixel_orbit = new Formula23(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA24:
                pixel_orbit = new Formula24(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA25:
                pixel_orbit = new Formula25(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA26:
                pixel_orbit = new Formula26(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA27:
                pixel_orbit = new Formula27(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA28:
                pixel_orbit = new Formula28(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA29:
                pixel_orbit = new Formula29(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA32:
                pixel_orbit = new Formula32(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA33:
                pixel_orbit = new Formula33(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA34:
                pixel_orbit = new Formula34(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA35:
                pixel_orbit = new Formula35(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA36:
                pixel_orbit = new Formula36(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA37:
                pixel_orbit = new Formula37(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA38:
                pixel_orbit = new Formula38(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA39:
                pixel_orbit = new Formula39(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA40:
                pixel_orbit = new Formula40(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA41:
                pixel_orbit = new Formula41(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA42:
                pixel_orbit = new Formula42(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA43:
                pixel_orbit = new Formula43(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA44:
                pixel_orbit = new Formula44(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA45:
                pixel_orbit = new Formula45(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.FORMULA46:
                pixel_orbit = new Formula46(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.USER_FORMULA:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                else {
                    pixel_orbit = new UserFormulaConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula, user_formula2, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                break;
            case MainWindow.USER_FORMULA_ITERATION_BASED:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaIterationBasedEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                else {
                    pixel_orbit = new UserFormulaIterationBasedConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_iteration_based, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                break;
            case MainWindow.USER_FORMULA_CONDITIONAL:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaConditionalEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                else {
                    pixel_orbit = new UserFormulaConditionalConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_formula_conditions, user_formula_condition_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                }
                break;
            case MainWindow.USER_FORMULA_COUPLED:
                if(bail_technique == 0) {
                    pixel_orbit = new UserFormulaCoupledEscaping(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                }
                else {
                    pixel_orbit = new UserFormulaCoupledConverging(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed);
                }
                break;
            case MainWindow.FROTHY_BASIN:
                pixel_orbit = new FrothyBasin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY1:
                pixel_orbit = new SzegediButterfly1(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.SZEGEDI_BUTTERFLY2:
                pixel_orbit = new SzegediButterfly2(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COUPLED_MANDELBROT:
                pixel_orbit = new CoupledMandelbrot(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.COUPLED_MANDELBROT_BURNING_SHIP:
                pixel_orbit = new CoupledMandelbrotBurningShip(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLER3:
                pixel_orbit = new Muller3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLER4:
                pixel_orbit = new Muller4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLERGENERALIZED3:
                pixel_orbit = new MullerGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLERGENERALIZED8:
                pixel_orbit = new MullerGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLERSIN:
                pixel_orbit = new MullerSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLERCOS:
                pixel_orbit = new MullerCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.MULLERPOLY:
                pixel_orbit = new MullerPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.MULLERFORMULA:
                pixel_orbit = new MullerFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula);
                break;
            case MainWindow.PARHALLEY3:
                pixel_orbit = new Parhalley3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEY4:
                pixel_orbit = new Parhalley4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEYGENERALIZED3:
                pixel_orbit = new ParhalleyGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEYGENERALIZED8:
                pixel_orbit = new ParhalleyGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEYSIN:
                pixel_orbit = new ParhalleySin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEYCOS:
                pixel_orbit = new ParhalleyCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.PARHALLEYPOLY:
                pixel_orbit = new ParhalleyPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.PARHALLEYFORMULA:
                pixel_orbit = new ParhalleyFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula);
                break;
            case MainWindow.LAGUERRE3:
                pixel_orbit = new Laguerre3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERRE4:
                pixel_orbit = new Laguerre4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERREGENERALIZED3:
                pixel_orbit = new LaguerreGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERREGENERALIZED8:
                pixel_orbit = new LaguerreGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERRESIN:
                pixel_orbit = new LaguerreSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERRECOS:
                pixel_orbit = new LaguerreCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAGUERREPOLY:
                pixel_orbit = new LaguerrePoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im);
                break;
            case MainWindow.LAGUERREFORMULA:
                pixel_orbit = new LaguerreFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula, laguerre_deg);
                break;
            case MainWindow.GENERIC_CaZbdZe:
                pixel_orbit = new GenericCaZbdZe(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, gcs);
                break;
            case MainWindow.DURAND_KERNER3:
                pixel_orbit = new DurandKerner3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.DURAND_KERNER4:
                pixel_orbit = new DurandKerner4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.DURAND_KERNERGENERALIZED3:
                pixel_orbit = new DurandKernerGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.DURAND_KERNERGENERALIZED8:
                pixel_orbit = new DurandKernerGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.DURAND_KERNERPOLY:
                pixel_orbit = new DurandKernerPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, durand_kerner_init_val, coefficients_im);
                break;
            case MainWindow.MAGNETIC_PENDULUM:
                pixel_orbit = new MagneticPendulum(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, mps);
                break;
            case MainWindow.LYAPUNOV:
                pixel_orbit = new Lyapunov(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, lyapunovInitialValue);
                break;
            case MainWindow.BAIRSTOW3:
                pixel_orbit = new Bairstow3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BAIRSTOW4:
                pixel_orbit = new Bairstow4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BAIRSTOWGENERALIZED3:
                pixel_orbit = new BairstowGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BAIRSTOWGENERALIZED8:
                pixel_orbit = new BairstowGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.BAIRSTOWPOLY:
                pixel_orbit = new BairstowPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.USER_FORMULA_NOVA:
                pixel_orbit = new UserFormulaNova(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, nova_method, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, newton_hines_k);
                break;
            case MainWindow.GENERIC_CpAZpBC:
                pixel_orbit = new GenericCpAZpBC(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, gcps);
                break;
            case MainWindow.INERTIA_GRAVITY:
                pixel_orbit = new InertiaGravityFractal(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, igs);
                break;
            case MainWindow.MANDEL_NEWTON:
                pixel_orbit = new MandelNewton(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.LAMBERT_W_VARIATION:
                pixel_orbit = new LambertWVariation(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINES3:
                pixel_orbit = new NewtonHines3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINES4:
                pixel_orbit = new NewtonHines4(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINESGENERALIZED3:
                pixel_orbit = new NewtonHinesGeneralized3(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINESGENERALIZED8:
                pixel_orbit = new NewtonHinesGeneralized8(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINESSIN:
                pixel_orbit = new NewtonHinesSin(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINESCOS:
                pixel_orbit = new NewtonHinesCos(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);
                break;
            case MainWindow.NEWTON_HINESPOLY:
                pixel_orbit = new NewtonHinesPoly(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, coefficients, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, coefficients_im, newton_hines_k);
                break;
            case MainWindow.NEWTON_HINESFORMULA:
                pixel_orbit = new NewtonHinesFormula(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, user_fz_formula, user_dfz_formula, newton_hines_k);
                break;
                
            
        }
        
    }
}
