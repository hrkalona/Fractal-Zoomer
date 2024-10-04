
package fractalzoomer.core;

import fractalzoomer.core.iteration_algorithm.FractalIterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.IterationAlgorithm;
import fractalzoomer.core.iteration_algorithm.JuliaIterationAlgorithm;
import fractalzoomer.core.location.normal.CartesianLocationNormalApfloatArbitrary;
import fractalzoomer.core.location.normal.PolarLocationNormalApfloatArbitrary;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.functions.Fractal;
import fractalzoomer.functions.FractalFactory;
import fractalzoomer.functions.barnsley.Barnsley1;
import fractalzoomer.functions.barnsley.Barnsley2;
import fractalzoomer.functions.barnsley.Barnsley3;
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrot;
import fractalzoomer.functions.formulas.coupled.CoupledMandelbrotBurningShip;
import fractalzoomer.functions.formulas.general.mathtype.*;
import fractalzoomer.functions.formulas.general.newtonvariant.*;
import fractalzoomer.functions.formulas.kaliset.*;
import fractalzoomer.functions.formulas.m_like_generalization.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze.*;
import fractalzoomer.functions.formulas.m_like_generalization.c_azb_dze_f_g.*;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula40;
import fractalzoomer.functions.formulas.m_like_generalization.zab_zde_fg.Formula41;
import fractalzoomer.functions.general.*;
import fractalzoomer.functions.lambda.Lambda;
import fractalzoomer.functions.lambda.Lambda2;
import fractalzoomer.functions.lambda.Lambda3;
import fractalzoomer.functions.lambda.LambdaFnFn;
import fractalzoomer.functions.magnet.*;
import fractalzoomer.functions.mandelbrot.*;
import fractalzoomer.functions.math.*;
import fractalzoomer.functions.root_finding_methods.abbasbandy.*;
import fractalzoomer.functions.root_finding_methods.abbasbandy2.*;
import fractalzoomer.functions.root_finding_methods.abbasbandy3.*;
import fractalzoomer.functions.root_finding_methods.aberth_ehrlich.*;
import fractalzoomer.functions.root_finding_methods.bairstow.*;
import fractalzoomer.functions.root_finding_methods.changbum_chun1.*;
import fractalzoomer.functions.root_finding_methods.changbum_chun2.*;
import fractalzoomer.functions.root_finding_methods.changbum_chun3.*;
import fractalzoomer.functions.root_finding_methods.chun_ham.*;
import fractalzoomer.functions.root_finding_methods.chun_kim.*;
import fractalzoomer.functions.root_finding_methods.contra_harmonic_newton.*;
import fractalzoomer.functions.root_finding_methods.durand_kerner.*;
import fractalzoomer.functions.root_finding_methods.euler_chebyshev.*;
import fractalzoomer.functions.root_finding_methods.ezzati_saleki1.*;
import fractalzoomer.functions.root_finding_methods.ezzati_saleki2.*;
import fractalzoomer.functions.root_finding_methods.feng.*;
import fractalzoomer.functions.root_finding_methods.halley.*;
import fractalzoomer.functions.root_finding_methods.harmonic_simpson_newton.*;
import fractalzoomer.functions.root_finding_methods.homeier1.*;
import fractalzoomer.functions.root_finding_methods.homeier2.*;
import fractalzoomer.functions.root_finding_methods.householder.*;
import fractalzoomer.functions.root_finding_methods.householder3.*;
import fractalzoomer.functions.root_finding_methods.jaratt.*;
import fractalzoomer.functions.root_finding_methods.jaratt2.*;
import fractalzoomer.functions.root_finding_methods.kim_chun.*;
import fractalzoomer.functions.root_finding_methods.king1.*;
import fractalzoomer.functions.root_finding_methods.king3.*;
import fractalzoomer.functions.root_finding_methods.kou_li_wang1.*;
import fractalzoomer.functions.root_finding_methods.laguerre.*;
import fractalzoomer.functions.root_finding_methods.maheshweri.*;
import fractalzoomer.functions.root_finding_methods.midpoint.*;
import fractalzoomer.functions.root_finding_methods.muller.*;
import fractalzoomer.functions.root_finding_methods.nedzhibov.*;
import fractalzoomer.functions.root_finding_methods.newton.*;
import fractalzoomer.functions.root_finding_methods.newton_hines.*;
import fractalzoomer.functions.root_finding_methods.noor_gupta.*;
import fractalzoomer.functions.root_finding_methods.parhalley.*;
import fractalzoomer.functions.root_finding_methods.popovski1.*;
import fractalzoomer.functions.root_finding_methods.rafis_rafiullah.*;
import fractalzoomer.functions.root_finding_methods.rafiullah1.*;
import fractalzoomer.functions.root_finding_methods.schroder.*;
import fractalzoomer.functions.root_finding_methods.secant.*;
import fractalzoomer.functions.root_finding_methods.simpson_newton.*;
import fractalzoomer.functions.root_finding_methods.steffensen.*;
import fractalzoomer.functions.root_finding_methods.stirling.*;
import fractalzoomer.functions.root_finding_methods.super_halley.*;
import fractalzoomer.functions.root_finding_methods.third_order_newton.*;
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.*;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.*;
import fractalzoomer.functions.root_finding_methods.whittaker.*;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.*;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly1;
import fractalzoomer.functions.szegedi_butterfly.SzegediButterfly2;
import fractalzoomer.functions.user_formulas.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.parser.Parser;
import org.apfloat.Apfloat;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class DrawOrbit implements Runnable {

    protected Apfloat xCenter;
    protected Apfloat yCenter;
    protected Apfloat size;
    protected Fractal pixel_orbit;
    protected IterationAlgorithm iteration_algorithm;
    protected ArrayList<Complex> complex_orbit;
    protected MainWindow ptr;
    protected int orbit_style;
    protected int image_width;
    protected int image_height;
    protected Color orbit_color;
    protected double height_ratio;
    protected boolean polar_projection;
    protected double circle_period;
    protected int pixel_x;
    protected int pixel_y;
    protected boolean show_converging_point;
    protected double[] rotation_vals;
    protected double[] rotation_center;
    
    public DrawOrbit(Settings s, int pixel_x, int pixel_y, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, boolean show_converging_point) {
        
        if(!s.fns.julia) {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, s.max_iterations > 400 ? 400 : s.max_iterations, pixel_x, pixel_y, image_width, image_height, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, Settings.fromDDArray(s.fns.rotation_vals), Settings.fromDDArray(s.fns.rotation_center), s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.perturbation_user_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.initial_value_user_formula, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_center_hp, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_dddfz_formula, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.laguerre_deg, s.fns.kleinianLine, s.fns.kleinianK, s.fns.kleinianM, s.fns.gcs, s.fns.durand_kerner_init_val, s.fns.mps, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.gcps, s.fns.igs, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue, s.fns.lpns.lyapunovInitializationIteratons, s.fns.root_initialization_method, s.fns.preffs, s.fns.postffs, s.fns.ips, s.fns.defaultNovaInitialValue, s.fns.useGlobalMethod, s.fns.globalMethodFactor, s.fns.variable_re, s.fns.variable_im, s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, s.toComplex(s.fns.zenex_re, s.fns.zenex_im));
        }
        else {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, s.max_iterations > 400 ? 400 : s.max_iterations, pixel_x, pixel_y, image_width, image_height, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, Settings.fromDDArray(s.fns.rotation_vals), Settings.fromDDArray(s.fns.rotation_center), s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_center_hp, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.gcs, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_dddfz_formula, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.laguerre_deg, s.fns.gcps, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue, s.fns.lpns.lyapunovInitializationIteratons, s.fns.preffs, s.fns.postffs, s.fns.ips, s.fns.juliter, s.fns.juliterIterations, s.fns.juliterIncludeInitialIterations, s.fns.defaultNovaInitialValue, s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.perturbation_user_formula, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.initial_value_user_formula, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.useGlobalMethod, s.fns.globalMethodFactor, s.fns.variable_re, s.fns.variable_im, s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, s.toComplex(s.fns.zenex_re, s.fns.zenex_im), s.xJuliaCenter.doubleValue(), s.yJuliaCenter.doubleValue());
        }
        
    }
    
    public DrawOrbit(Settings s, double pixel_x, double pixel_y, int sec_points, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, boolean show_converging_point) {
        
        if(!s.fns.julia) {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, sec_points, pixel_x, pixel_y, image_width, image_height, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, Settings.fromDDArray(s.fns.rotation_vals), Settings.fromDDArray(s.fns.rotation_center), s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.perturbation_user_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.initial_value_user_formula, s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_center_hp, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_dddfz_formula, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.laguerre_deg, s.fns.kleinianLine, s.fns.kleinianK, s.fns.kleinianM, s.fns.gcs, s.fns.durand_kerner_init_val, s.fns.mps, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.gcps, s.fns.igs, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue, s.fns.lpns.lyapunovInitializationIteratons, s.fns.root_initialization_method, s.fns.preffs, s.fns.postffs, s.fns.ips, s.fns.defaultNovaInitialValue, s.fns.useGlobalMethod, s.fns.globalMethodFactor, s.fns.variable_re, s.fns.variable_im, s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, s.toComplex(s.fns.zenex_re, s.fns.zenex_im));
        }
        else {
            InitOrbitSettings(s.xCenter, s.yCenter, s.size, sec_points, pixel_x, pixel_y, image_width, image_height, ptr, orbit_color, orbit_style, s.fns.plane_type, s.fns.apply_plane_on_julia, s.fns.apply_plane_on_julia_seed, s.fns.burning_ship, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.function, s.fns.z_exponent, s.fns.z_exponent_complex, Settings.fromDDArray(s.fns.rotation_vals), Settings.fromDDArray(s.fns.rotation_center), s.fns.coefficients, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_center_hp, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.polar_projection, s.circle_period, show_converging_point, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.fns.gcs, s.fns.coefficients_im, s.fns.lpns.lyapunovFinalExpression, s.fns.lpns.lyapunovFunction, s.fns.lpns.lyapunovExponentFunction, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.fns.user_dddfz_formula, s.fns.user_relaxation_formula, s.fns.user_nova_addend_formula, s.fns.laguerre_deg, s.fns.gcps, s.fns.lfns, s.fns.newton_hines_k, s.fns.lpns.lyapunovInitialValue, s.fns.lpns.lyapunovInitializationIteratons, s.fns.preffs, s.fns.postffs, s.fns.ips, s.fns.juliter, s.fns.juliterIterations, s.fns.juliterIncludeInitialIterations, s.fns.defaultNovaInitialValue, s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.perturbation_user_formula, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.initial_value_user_formula, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.useGlobalMethod, s.fns.globalMethodFactor, s.fns.variable_re, s.fns.variable_im, s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, s.toComplex(s.fns.zenex_re, s.fns.zenex_im), s.xJuliaCenter.doubleValue(), s.yJuliaCenter.doubleValue());
        }
        
    }
    
    private void InitOrbitSettings(Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int pixel_x, int pixel_y, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double[] laguerre_deg, double[] kleinianLine, double kleinianK, double kleinianM, GenericCaZbdZeSettings gcs, double[] durand_kerner_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue, int lyapunovInitializationIteratons, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.image_width = image_width;
        this.image_height = image_height;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = pixel_x;
        this.pixel_y = pixel_y;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;        
        
        if(polar_projection) {
            PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period);
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(location.getComplexOrbit(pixel_x, pixel_y));
        }
        else {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_width, image_height);
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(location.getComplexOrbit(pixel_x, pixel_y));
        }


        pixel_orbit = new FractalFactory(complex_orbit).orbitFractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kerner_init_val, mps, lyapunovFunction, lyapunovExponentFunction, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, lyapunovInitialValue, lyapunovInitializationIteratons, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue,  useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);

        iteration_algorithm = new FractalIterationAlgorithm(pixel_orbit);

        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;

    }

    private void InitOrbitSettings(Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, double pixel_x, double pixel_y, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_val, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, double[] laguerre_deg, double[] kleinianLine, double kleinianK, double kleinianM, GenericCaZbdZeSettings gcs, double[] durand_kerner_init_val, MagneticPendulumSettings mps, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_relaxation_formula, String user_nova_addend_formula, GenericCpAZpBCSettings gcps, InertiaGravityFractalSettings igs, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue, int lyapunovInitializationIteratons, int root_initialization_method, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean defaultNovaInitialValue,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.image_width = image_width;
        this.image_height = image_height;
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

        pixel_orbit = new FractalFactory(complex_orbit).orbitFractalFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, user_perturbation_conditions, user_perturbation_condition_formula, perturbation_user_formula, init_val, initial_vals, variable_init_value, user_initial_value_algorithm, user_initial_value_conditions, user_initial_value_condition_formula, initial_value_user_formula, plane_type, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, kleinianLine, kleinianK, kleinianM, laguerre_deg, durand_kerner_init_val, mps, lyapunovFunction, lyapunovExponentFunction, user_relaxation_formula, user_nova_addend_formula, gcps, igs, lfns, newton_hines_k, lyapunovInitialValue, lyapunovInitializationIteratons, root_initialization_method, preffs, postffs, ips, defaultNovaInitialValue,  useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs);
        
        iteration_algorithm = new FractalIterationAlgorithm(pixel_orbit);

        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
   
    }
    
    private void InitOrbitSettings(Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, int pixel_x, int pixel_y, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue, int lyapunovInitializationIteratons, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs, double xJuliaCenter, double yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.image_width = image_width;
        this.image_height = image_height;
        this.height_ratio = height_ratio;
        
        this.polar_projection = polar_projection;
        this.circle_period = circle_period;
        
        this.pixel_x = pixel_x;
        this.pixel_y = pixel_y;
        
        this.show_converging_point = show_converging_point;
        this.rotation_vals = rotation_vals;
        this.rotation_center = rotation_center;

        if(polar_projection) {
            PolarLocationNormalApfloatArbitrary location = new PolarLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_width, image_height, circle_period);
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(location.getComplexOrbit(pixel_x, pixel_y));
        }
        else {
            CartesianLocationNormalApfloatArbitrary location = new CartesianLocationNormalApfloatArbitrary(xCenter, yCenter, size, height_ratio, image_width, image_height);
            complex_orbit = new ArrayList<>(max_iterations + 1);
            complex_orbit.add(location.getComplexOrbit(pixel_x, pixel_y));
        }

        pixel_orbit = new FractalFactory(complex_orbit).orbitJuliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, lyapunovInitialValue, lyapunovInitializationIteratons, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
        
        iteration_algorithm = new JuliaIterationAlgorithm(pixel_orbit);
        
        this.ptr = ptr;
        this.orbit_style = orbit_style;
        this.orbit_color = orbit_color;
     
    }
    
    private void InitOrbitSettings(Apfloat xCenter, Apfloat yCenter, Apfloat size, int max_iterations, double pixel_x, double pixel_y, int image_width, int image_height, MainWindow ptr, Color orbit_color, int orbit_style, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, int function, double z_exponent, double[] z_exponent_complex, double[] rotation_vals, double[] rotation_center, double[] coefficients, double[] z_exponent_nova, double[] relaxation, int nova_method, String user_formula, String user_formula2, int bail_technique, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, String[] user_formula_iteration_based, String[] user_formula_conditions, String[] user_formula_condition_formula, double height_ratio, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean polar_projection, double circle_period, boolean show_converging_point, double coupling, String[] user_formula_coupled, int coupling_method, double coupling_amplitude, double coupling_frequency, int coupling_seed, GenericCaZbdZeSettings gcs, double[] coefficients_im, String[] lyapunovExpression, String lyapunovFunction, String lyapunovExponentFunction, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, GenericCpAZpBCSettings gcps, LambdaFnFnSettings lfns, double[] newton_hines_k, String lyapunovInitialValue, int lyapunovInitializationIteratons, FunctionFilterSettings preffs, FunctionFilterSettings postffs, PlaneInfluenceSettings ips, boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations, boolean defaultNovaInitialValue, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula,  boolean useGlobalMethod, double[] globalMethodFactor, double[] variable_re, double[] variable_im, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, Complex[] zenex_coeffs, double xJuliaCenter, double yJuliaCenter) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;

        this.image_width = image_width;
        this.image_height = image_height;
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

        pixel_orbit = new FractalFactory(complex_orbit).orbitJuliaFactory(function, xCenter.doubleValue(), yCenter.doubleValue(), size.doubleValue(), max_iterations, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, burning_ship, mandel_grass, mandel_grass_vals, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, z_exponent, z_exponent_complex, coefficients, coefficients_im, z_exponent_nova, relaxation, nova_method, bail_technique, user_formula, user_formula2, user_formula_iteration_based, user_formula_conditions, user_formula_condition_formula, coupling, user_formula_coupled, coupling_method, coupling_amplitude, coupling_frequency, coupling_seed, gcs, lyapunovExpression, lyapunovFunction, lyapunovExponentFunction, user_fz_formula, user_dfz_formula, user_ddfz_formula, user_dddfz_formula, user_relaxation_formula, user_nova_addend_formula, laguerre_deg, gcps, lfns, newton_hines_k, lyapunovInitialValue, lyapunovInitializationIteratons, preffs, postffs, ips, juliter, juliterIterations, juliterIncludeInitialIterations, defaultNovaInitialValue, perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, init_value, initial_vals, variable_init_value, user_initial_value_algorithm, initial_value_user_formula, user_initial_value_conditions, user_initial_value_condition_formula, useGlobalMethod, globalMethodFactor, variable_re, variable_im, inflections_re, inflections_im, inflectionsPower, zenex_coeffs, xJuliaCenter, yJuliaCenter);
        
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

    static final double EPSILON = 1e-6;

    static boolean liangBarskyClip(double x0, double y0, double x1, double y1,
                                   double xmin, double ymin, double xmax, double ymax, double[] result) {

        if(!Double.isFinite(x0) || !Double.isFinite(y0)
        || !Double.isFinite(x1) || !Double.isFinite(y1)) {
            return false;
        }

        double dx = x1 - x0;
        double dy = y1 - y0;

        double p1 = -dx;
        double p2 = dx;
        double p3 = -dy;
        double p4 = dy;

        double q1 = x0 - xmin;
        double q2 = xmax - x0;
        double q3 = y0 - ymin;
        double q4 = ymax - y0;

        double[] p = {p1, p2, p3, p4};
        double[] q = {q1, q2, q3, q4};

        double t_enter = 0, t_exit = 1;

        for (int i = 0; i < p.length; i++) {
            if (Math.abs(p[i]) < EPSILON) {
                if (q[i] < 0) {
                    return false; // Line is parallel and outside the clipping window
                }
            } else {
                double t = q[i] / p[i];

                if (p[i] < 0 && t > t_enter) {
                    t_enter = t;
                } else if (p[i] > 0 && t < t_exit) {
                    t_exit = t;
                }
            }
        }

        if (t_enter > t_exit) {
            return false; // Line is outside the clipping window
        }

        // Compute the intersection points
        double xIntersection1 = x0 + t_enter * dx;
        double yIntersection1 = y0 + t_enter * dy;

        double xIntersection2 = x0 + t_exit * dx;
        double yIntersection2 = y0 + t_exit * dy;

        // Print the intersection points
        result[0] = xIntersection1;
        result[1] = yIntersection1;
        result[2] = xIntersection2;
        result[3] = yIntersection2;

        return true;
    }

    public void drawLine(Graphics2D full_image_g, boolean drawDot) {
        double x0, y0, x1 = 0, y1 = 0;
        int list_size;
        
        full_image_g.setColor(orbit_color);
        
        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        full_image_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        list_size = complex_orbit.size() - 1;
        
        double size = this.size.doubleValue();
        
        double startx = 0, starty = 0, center, f0, r0, f1, r1, xcenter = 0, ycenter = 0, mulx = 0, muly = 0;
        
        double size_2_x, size_2_y, temp_xcenter_size = 0, temp_ycenter_size = 0, temp_size_image_size_x = 0, temp_size_image_size_y = 0;

        int image_size = Math.min(image_width, image_height);

        double coefx = image_width == image_size ? 0.5 : (1 + (image_width - (double)image_height) / image_height) * 0.5;
        double coefy = image_height == image_size ? 0.5 : (1 + (image_height - (double)image_width) / image_width) * 0.5;

        if(polar_projection) {
            center = Math.log(size);

            xcenter = xCenter.doubleValue();
            ycenter = yCenter.doubleValue();
            
            muly = (2 * circle_period * Math.PI) / image_size;
            
            mulx = muly * height_ratio;

            startx = center - mulx * image_size * coefx;
            starty = muly * image_size * (0.5 - coefy);
        }
        else {
            size_2_x = size * coefx;
            size_2_y = (size * height_ratio) * coefy;
            temp_xcenter_size = xCenter.doubleValue() - size_2_x;
            temp_ycenter_size = yCenter.doubleValue() + size_2_y;
            temp_size_image_size_x = size / image_size;
            temp_size_image_size_y = (size * height_ratio) / image_size;
        }
        
        full_image_g.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics metrics = full_image_g.getFontMetrics();
        
        Rectangle2D rect = metrics.getStringBounds("*", full_image_g);
        int width = (int)rect.getWidth();
        int height = (int)rect.getHeight();

        int x0_ = 0, x1_ = 0, y0_ = 0, y1_ = 0;
        double[] result = new double[4];
        boolean drawLine = true;
        
        for(int i = 0; i < list_size; i++) {
            
            if(polar_projection) {
                Complex n0 = complex_orbit.get(i).sub(new Complex(xcenter, ycenter));
                r0 = n0.norm();
                f0 = n0.arg();
                f0 = f0 < 0 ? f0 + 2 * Math.PI : f0;

                if(r0 < 1e-16) {
                    r0 = 1e-16;
                }
                
                x0 = (Math.log(r0) - startx) / mulx;
                y0 = (f0 - starty) / muly;
                
                Complex n1 = complex_orbit.get(i + 1).sub(new Complex(xcenter, ycenter));
                r1 = n1.norm();
                f1 = n1.arg();
                
                f1 = f1 < 0 ? f1 + 2 * Math.PI : f1;
                x1 = (Math.log(r1) - startx) / mulx;
                y1 = (f1 - starty) / muly;
                
            }
            else {
                x0 = (complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size_x;
                y0 = (-complex_orbit.get(i).getIm() + temp_ycenter_size) / temp_size_image_size_y;
                x1 = (complex_orbit.get(i + 1).getRe() - temp_xcenter_size) / temp_size_image_size_x;
                y1 = (-complex_orbit.get(i + 1).getIm() + temp_ycenter_size) / temp_size_image_size_y;
            }

            drawLine = true;
            if(x0 < 0 || x0 >= image_width || y0 < 0 || y0 >= image_height ||
                    x1 < 0 || x1 >= image_width || y1 < 0 || y1 >= image_height) {

                int limit = 1000000;
                if(!liangBarskyClip(x0, y0, x1, y1, -limit, -limit, image_width + limit, image_height + limit, result)) {
                    drawLine = false;
                }
                else {
                    x0_ = (int) (result[0] + 0.5);
                    y0_ = (int) (result[1] + 0.5);
                    x1_ = (int) (result[2] + 0.5);
                    y1_ = (int) (result[3] + 0.5);
                }
            }
            else {
                x0_ = (int)(x0 + 0.5);
                y0_ = (int)(y0 + 0.5);
                x1_ = (int)(x1 + 0.5);
                y1_ = (int)(y1 + 0.5);
            }

            if(drawLine) {
                full_image_g.drawLine(x0_, y0_, x1_, y1_);

                if(drawDot) {
                    if (i == 0) {
                        if (polar_projection && circle_period > 1 && pixel_x != -1 && pixel_y != -1 && Math.abs(y0_ - pixel_y) != 0) {
                            full_image_g.drawLine(pixel_x, pixel_y, x0_, y0_);
                            full_image_g.drawString("*", pixel_x - width / 2, pixel_y + height / 2);
                            full_image_g.fillOval(x0_ - 2, y0_ - 2, 5, 5);
                        } else {
                            full_image_g.drawString("*", x0_ - width / 2, y0_ + height / 2);
                        }
                    } else {
                        full_image_g.fillOval(x0_ - 2, y0_ - 2, 5, 5);
                    }
                }
            }
        }
        
        if(list_size > 0 && drawLine) {
            if(drawDot) {
                full_image_g.fillOval(x1_ - 2, y1_ - 2, 5, 5);
            }

            if(show_converging_point && complex_orbit.get(complex_orbit.size() - 1).distance(complex_orbit.get(complex_orbit.size() - 2)) < EPSILON) {
                Complex last = complex_orbit.get(complex_orbit.size() - 1);
                Rotation rot = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

                full_image_g.drawString(rot.rotate(last).toStringTruncated(), x1_ + 15, y1_ + 15);
            }
        }
        
    }
    
    public void drawDot(Graphics2D full_image_g) {
        double x0 = 0, y0 = 0;
        int list_size;
        
        full_image_g.setColor(orbit_color);
        
        full_image_g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        full_image_g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
 
        
        list_size = complex_orbit.size();
        
        double size = this.size.doubleValue();
        
        double startx = 0, starty = 0, center, f0, r0, xcenter = 0, ycenter = 0, mulx = 0, muly = 0;
        
        double size_2_x, size_2_y, temp_xcenter_size = 0, temp_ycenter_size = 0, temp_size_image_size_x = 0, temp_size_image_size_y = 0;

        int image_size = Math.min(image_width, image_height);

        double coefx = image_width == image_size ? 0.5 : (1 + (image_width - (double)image_height) / image_height) * 0.5;
        double coefy = image_height == image_size ? 0.5 : (1 + (image_height - (double)image_width) / image_width) * 0.5;

        if(polar_projection) {
            center = Math.log(size);
            
            xcenter = xCenter.doubleValue();
            ycenter = yCenter.doubleValue();
            
            muly = (2 * circle_period * Math.PI) / image_size;
            
            mulx = muly * height_ratio;
            
            startx = center - mulx * image_size * coefx;
            starty = muly * image_size * (0.5 - coefy);
        }
        else {
            size_2_x = size * coefx;
            size_2_y = (size * height_ratio) * coefy;
            temp_xcenter_size = xCenter.doubleValue() - size_2_x;
            temp_ycenter_size = yCenter.doubleValue() + size_2_y;
            temp_size_image_size_x = size / image_size;
            temp_size_image_size_y = (size * height_ratio) / image_size;
        }
        
        full_image_g.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics metrics = full_image_g.getFontMetrics();
        
        Rectangle2D rect = metrics.getStringBounds("*", full_image_g);
        int width = (int)rect.getWidth();
        int height = (int)rect.getHeight();
        int x0_ = 0, y0_ = 0;
        boolean draw = true;

        
        for(int i = 0; i < list_size; i++) {
            if(polar_projection) {
                Complex n0 = complex_orbit.get(i).sub(new Complex(xcenter, ycenter));
                r0 = n0.norm();
                f0 = n0.arg();
                f0 = f0 < 0 ? f0 + 2 * Math.PI : f0;

                if(r0 < 1e-16) {
                    r0 = 1e-16;
                }
                
                x0 = (Math.log(r0) - startx) / mulx;
                y0 = (f0 - starty) / muly;
            }
            else {
                x0 = (complex_orbit.get(i).getRe() - temp_xcenter_size) / temp_size_image_size_x;
                y0 = (-complex_orbit.get(i).getIm() + temp_ycenter_size) / temp_size_image_size_y;
            }

            draw = true;
            if(x0 < 0 || x0 >= image_width || y0 < 0 || y0 >= image_height) {
                draw = false;
            }
            else {
                x0_ = (int)(x0 + 0.5);
                y0_ = (int)(y0 + 0.5);
            }

            if(draw) {
                if (i == 0) {
                    if (polar_projection && circle_period > 1 && pixel_x != -1 && pixel_y != -1 && Math.abs(y0_ - pixel_y) != 0) {
                        full_image_g.drawString("*", pixel_x - width / 2, pixel_y + height / 2);
                        full_image_g.fillOval(x0_ - 2, y0_ - 2, 5, 5);
                    } else {
                        full_image_g.drawString("*", x0_ - width / 2, y0_ + height / 2);
                    }
                } else {
                    full_image_g.fillOval(x0_ - 2, y0_ - 2, 5, 5);
                }
            }
        }

        if(draw && list_size > 1 && show_converging_point && complex_orbit.get(complex_orbit.size() - 1).distance(complex_orbit.get(complex_orbit.size() - 2)) < EPSILON) {
            Complex last = complex_orbit.get(complex_orbit.size() - 1);
            Rotation rot = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

            full_image_g.drawString(rot.rotate(last).toStringTruncated(), x0_ + 15, y0_ + 15);
        }
        
    }

}
