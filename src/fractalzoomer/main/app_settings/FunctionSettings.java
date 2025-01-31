
package fractalzoomer.main.app_settings;

import fractalzoomer.core.Derivative;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.main.Constants;
import fractalzoomer.parser.Parser;
import org.apfloat.Apfloat;

import java.util.ArrayList;


/**
 *
 * @author hrkalona
 */
public class FunctionSettings implements Constants {
    public GenericCaZbdZeSettings gcs;
    public double[] kleinianLine;
    public double kleinianK;
    public double kleinianM;
    public int plane_type;
    public boolean burning_ship;
    public boolean mandel_grass;
    public double[] coefficients;
    public double[] coefficients_im;
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
    public Apfloat[] rotation_vals;
    public Apfloat[] rotation_center;
    public double[] mandel_grass_vals;
    public Apfloat[] plane_transform_center_hp;
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
    public double norm_a;
    public double norm_b;
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
    public String user_dddfz_formula;
    public int bail_technique;
    public boolean julia;
    public int bailout_test_algorithm;
    public double bailout;
    public double convergent_bailout;
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
    //public boolean apply_offset_in_smoothing;
    public boolean banded;
    public int smoothing_color_selection;
    public int smoothing_fractional_transfer_method;
    public double[] durand_kerner_init_val;
    public MagneticPendulumSettings mps;
    public LyapunovSettings lpns;
    public String user_relaxation_formula;
    public String user_nova_addend_formula;
    public GenericCpAZpBCSettings gcps;
    public InertiaGravityFractalSettings igs;
    public LambdaFnFnSettings lfns;
    public int skip_bailout_iterations;
    public int skip_convergent_bailout_iterations;
    public double[] newton_hines_k;
    public TrueColorSettings tcs;
    public int derivative_method;
    public int root_initialization_method;
    public FunctionFilterSettings preffs;
    public FunctionFilterSettings postffs;
    public boolean juliter;
    public int juliterIterations;
    public boolean juliterIncludeInitialIterations;
    public PlaneInfluenceSettings ips;
    public boolean defaultNovaInitialValue;
    public ConvergentBailoutConditionSettings cbs;
    public boolean useGlobalMethod;
    public double[] globalMethodFactor;
    public int period;
    public double[] variable_re;
    public double[] variable_im;
    public ArrayList<Double> inflections_re;
    public ArrayList<Double> inflections_im;
    public double inflectionsPower;
    public double[] zenex_re;
    public double[] zenex_im;
    
    public FunctionSettings() {
        period = 0;
        n_norm = 2;
        z_exponent = 2;

        norm_a = 1;
        norm_b = 1;
        
        smoothing = false;
        //apply_offset_in_smoothing = true;
        smoothing_color_selection = 0;
        banded = false;
        smoothing_fractional_transfer_method = 0;
        
        escaping_smooth_algorithm = 1;
        converging_smooth_algorithm = 1;

        nova_method = NOVA_NEWTON;
        
        bailout = 2;
        convergent_bailout = 0;
        bailout_test_algorithm = 0;
        skip_bailout_iterations = 0;
        skip_convergent_bailout_iterations = 0;

        bailout_test_user_formula = "norm(z)";
        bailout_test_user_formula2 = "bail";
        bailout_test_comparison = GREATER_EQUAL;
        
        plane_type = MU_PLANE;
        
        rotation_vals = new Apfloat[2];

        variable_re = new double[Parser.EXTRA_VARS];
        variable_im = new double[Parser.EXTRA_VARS];

        zenex_re = new double[7];
        zenex_im = new double[7];
        zenex_re[0] = 1;
        zenex_re[1] = 7;
        zenex_re[2] = -1;
        zenex_re[3] = -9;
        zenex_re[4] = 9;

        rotation = 0;

        Apfloat tempRadians = MyApfloat.fp.toRadians(new MyApfloat(rotation));
        rotation_vals[0] = MyApfloat.cos(tempRadians);
        rotation_vals[1] = MyApfloat.sin(tempRadians);

        rotation_center = new Apfloat[2];

        rotation_center[0] = new MyApfloat(0.0);
        rotation_center[1] = new MyApfloat(0.0);

        plane_transform_scales = new double[2];
        plane_transform_scales[0] = 0;
        plane_transform_scales[1] = 0;

        plane_transform_center = new double[2];
        plane_transform_center[0] = 0;
        plane_transform_center[1] = 0;

        plane_transform_center_hp = new Apfloat[2];
        plane_transform_center_hp[0] = new MyApfloat(0.0);
        plane_transform_center_hp[1] = new MyApfloat(0.0);


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
        user_dddfz_formula = "6";


        user_relaxation_formula = "1.0";
        user_nova_addend_formula = "c";
        
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
        
        coefficients_im = new double[11];
        
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
        
        gcs = new GenericCaZbdZeSettings();
        
        durand_kerner_init_val = new double[2];
        durand_kerner_init_val[0] = 0.4;
        durand_kerner_init_val[1] = 0.9;
        
        newton_hines_k = new double[2];
        newton_hines_k[0] = -0.5;
        newton_hines_k[1] = 0;
        
        mps = new MagneticPendulumSettings();
        lpns = new LyapunovSettings();
        gcps = new GenericCpAZpBCSettings();
        igs = new InertiaGravityFractalSettings();
        lfns = new LambdaFnFnSettings();
        tcs = new TrueColorSettings();
        
        derivative_method = Derivative.DISABLED;
        root_initialization_method = 0;

        preffs = new FunctionFilterSettings();
        postffs = new FunctionFilterSettings();

        juliter = false;
        juliterIterations = 16;
        juliterIncludeInitialIterations = true;

        ips = new PlaneInfluenceSettings();

        defaultNovaInitialValue = true;

        cbs = new ConvergentBailoutConditionSettings();

        useGlobalMethod = false;
        globalMethodFactor = new double[2];
        globalMethodFactor[0] = 0.5;
        globalMethodFactor[1] = 0;

        inflections_re = new ArrayList<>();
        inflections_im = new ArrayList<>();
        inflectionsPower = 2;
        
    }
    
}
