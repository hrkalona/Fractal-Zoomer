
package fractalzoomer.functions.user_formulas;

import fractalzoomer.core.Complex;
import fractalzoomer.core.Derivative;
import fractalzoomer.core.TaskRender;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.functions.root_finding_methods.abbasbandy.AbbasbandyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.abbasbandy2.Abbasbandy2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.abbasbandy3.Abbasbandy3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.changbum_chun1.ChangBumChun1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.changbum_chun2.ChangBumChun2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.changbum_chun3.ChangBumChun3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.chun_ham.ChunHamRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.chun_kim.ChunKimRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.contra_harmonic_newton.ContraHarmonicNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.euler_chebyshev.EulerChebyshevRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.ezzati_saleki1.EzzatiSaleki1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.ezzati_saleki2.EzzatiSaleki2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.feng.FengRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.halley.HalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.harmonic_simpson_newton.HarmonicSimpsonNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.homeier1.Homeier1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.homeier2.Homeier2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder3.Householder3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt.JarattRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt2.Jaratt2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.kim_chun.KimChunRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.king1.King1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.king3.King3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.kou_li_wang1.KouLiWang1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.maheshweri.MaheshweriRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.midpoint.MidpointRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.muller.MullerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.nedzhibov.NedzhibovRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton_hines.NewtonHinesRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.noor_gupta.NoorGuptaRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.popovski1.Popovski1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.rafis_rafiullah.RafisRafiullahRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.rafiullah1.Rafiullah1RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.secant.SecantRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.simpson_newton.SimpsonNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.stirling.StirlingRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.super_halley.SuperHalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.third_order_newton.ThirdOrderNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.TraubOstrowskiRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.WeerakoonFernandoRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker.WhittakerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.WhittakerDoubleConvexRootFindingMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import org.apfloat.Apfloat;

import java.util.ArrayList;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class UserFormulaNova extends ExtendedConvergentType {

    private int nova_method;
    private ExpressionNode expr;
    private Parser parser;
    private ExpressionNode expr2;
    private Parser parser2;
    private ExpressionNode expr3;
    private Parser parser3;
    private ExpressionNode expr4;
    private Parser parser4;
    private ExpressionNode exprRelaxation;
    private Parser parserRelaxation;
    private ExpressionNode exprAddend;
    private Parser parserAddend;
    private Complex point;
    private Complex laguerreDeg;
    private Complex newtonHinesK;
    private boolean useGlobalMethod;
    private Complex globalMethodFactor;

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, boolean defaultNovaInitialValue, boolean useGlobalMethod, double[] globalMethodFactor) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        //Todo: Check which other methods need this
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            setConvergentBailout(1E-8);
        }

        this.nova_method = nova_method;

        laguerreDeg = new Complex(laguerre_deg[0], laguerre_deg[1]);
        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        parser4 = new Parser();
        expr4 = parser4.parse(user_dddfz_formula);

        parserRelaxation = new Parser();
        exprRelaxation = parserRelaxation.parse(user_relaxation_formula);

        parserAddend = new Parser();
        exprAddend = parserAddend.parse(user_nova_addend_formula);

        defaultInitVal = new InitialValue(1, 0);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if (init_value) {
            if (variable_init_value) {
                if (user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            if(defaultNovaInitialValue) {
                init_val = defaultInitVal;
            }
            else {
                init_val = new DefaultInitialValue();
            }
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        this.useGlobalMethod = useGlobalMethod;
        this.globalMethodFactor = new Complex(globalMethodFactor[0], globalMethodFactor[1]);
    }

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, boolean defaultNovaInitialValue, boolean useGlobalMethod, double[] globalMethodFactor, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots, xJuliaCenter, yJuliaCenter);
        
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            setConvergentBailout(1E-8);
        }

        this.nova_method = nova_method;

        laguerreDeg = new Complex(laguerre_deg[0], laguerre_deg[1]);
        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        parser4 = new Parser();
        expr4 = parser4.parse(user_dddfz_formula);

        parserRelaxation = new Parser();
        exprRelaxation = parserRelaxation.parse(user_relaxation_formula);

        parserAddend = new Parser();
        exprAddend = parserAddend.parse(user_nova_addend_formula);

        defaultInitVal = new InitialValue(1, 0);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
            case MainWindow.BINARY_DECOMPOSITION2:
            case MainWindow.BANDED:
                if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER || nova_method == MainWindow.NOVA_WHITTAKER || nova_method == MainWindow.NOVA_WHITTAKER_DOUBLE_CONVEX || nova_method == MainWindow.NOVA_SUPER_HALLEY) {
                    setConvergentBailout(1E-4);
                } else if (nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                    setConvergentBailout(1E-9);
                } else if (nova_method == MainWindow.NOVA_SCHRODER) {
                    setConvergentBailout(1E-6);
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                setConvergentBailout(1E-7);
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        pertur_val = new DefaultPerturbation();
        if(defaultNovaInitialValue) {
            init_val = defaultInitVal;
        }
        else {
            init_val = new DefaultInitialValue();
        }

        this.useGlobalMethod = useGlobalMethod;
        this.globalMethodFactor = new Complex(globalMethodFactor[0], globalMethodFactor[1]);
    }

    //orbit
    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, boolean defaultNovaInitialValue, boolean useGlobalMethod, double[] globalMethodFactor) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        this.nova_method = nova_method;

        laguerreDeg = new Complex(laguerre_deg[0], laguerre_deg[1]);
        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        parser4 = new Parser();
        expr4 = parser4.parse(user_dddfz_formula);

        parserRelaxation = new Parser();
        exprRelaxation = parserRelaxation.parse(user_relaxation_formula);

        parserAddend = new Parser();
        exprAddend = parserAddend.parse(user_nova_addend_formula);

        defaultInitVal = new InitialValue(1, 0);

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

        if (init_value) {
            if (variable_init_value) {
                if (user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            if(defaultNovaInitialValue) {
                init_val = defaultInitVal;
            }
            else {
                init_val = new DefaultInitialValue();
            }
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        this.useGlobalMethod = useGlobalMethod;
        this.globalMethodFactor = new Complex(globalMethodFactor[0], globalMethodFactor[1]);

    }

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, boolean defaultNovaInitialValue, boolean useGlobalMethod, double[] globalMethodFactor, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, xJuliaCenter, yJuliaCenter);

        this.nova_method = nova_method;

        laguerreDeg = new Complex(laguerre_deg[0], laguerre_deg[1]);
        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        parser4 = new Parser();
        expr4 = parser4.parse(user_dddfz_formula);

        parserRelaxation = new Parser();
        exprRelaxation = parserRelaxation.parse(user_relaxation_formula);

        parserAddend = new Parser();
        exprAddend = parserAddend.parse(user_nova_addend_formula);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        defaultInitVal = new InitialValue(1, 0);

        pertur_val = new DefaultPerturbation();
        if(defaultNovaInitialValue) {
            init_val = defaultInitVal;
        }
        else {
            init_val = new DefaultInitialValue();
        }

        this.useGlobalMethod = useGlobalMethod;
        this.globalMethodFactor = new Complex(globalMethodFactor[0], globalMethodFactor[1]);
    }

    private Complex combinedFFZ(Complex z, Complex fz, Complex dfz) {

        Complex temp = null;

        if(nova_method == MainWindow.NOVA_STEFFENSEN) {
            temp = SteffensenRootFindingMethod.getFunctionArgument(z, fz);
        }
        else if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            temp = TraubOstrowskiRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_THIRD_ORDER_NEWTON) {
            temp = ThirdOrderNewtonRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHUN_HAM) {
            temp = ChunHamRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_EZZATI_SALEKI2) {
            temp = EzzatiSaleki2RootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHANGBUM_CHUN1) {
            temp = ChangBumChun1RootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHANGBUM_CHUN2) {
            temp = ChangBumChun2RootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_KING3) {
            temp = King3RootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_KOU_LI_WANG1) {
            temp = KouLiWang1RootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_MAHESHWERI) {
            temp = MaheshweriRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHANGBUM_CHUN3) {
            temp = ChangBumChun3RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_EZZATI_SALEKI1) {
            temp = EzzatiSaleki1RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_FENG) {
            temp = FengRootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_KING1) {
            temp = King1RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_NOOR_GUPTA) {
            temp = NoorGuptaRootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }

        if (parser.foundZ()) {
            parser.setZvalue(temp);
        }

        return expr.getValue();

    }

    private Complex[] combinedDFZ(Complex z, Complex fz, Complex dfz) {
        Complex temp = null, combined_dfz, combined_dfz2 = null;

        if(nova_method == MainWindow.NOVA_MIDPOINT) {
            temp = MidpointRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if (nova_method == MainWindow.NOVA_STIRLING){
            temp = StirlingRootFindingMethod.getDerivativeArgument(z, fz);
        }
        else if (nova_method == MainWindow.NOVA_JARATT){
            temp = JarattRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if (nova_method == MainWindow.NOVA_JARATT2){
            temp = Jaratt2RootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_WEERAKOON_FERNANDO) {
            temp = WeerakoonFernandoRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CONTRA_HARMONIC_NEWTON) {
            temp = ContraHarmonicNewtonRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHUN_KIM) {
            temp = ChunKimRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_HOMEIER1) {
            temp = Homeier1RootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_HOMEIER2) {
            temp = Homeier2RootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_KIM_CHUN) {
            temp = KimChunRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_RAFIULLAH1) {
            temp = Rafiullah1RootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_CHANGBUM_CHUN3) {
            temp = ChangBumChun3RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_EZZATI_SALEKI1) {
            temp = EzzatiSaleki1RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_FENG) {
            temp = FengRootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_KING1) {
            temp = King1RootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_NOOR_GUPTA) {
            temp = NoorGuptaRootFindingMethod.getFunctionAndDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == NOVA_HARMONIC_SIMPSON_NEWTON) {
            temp = HarmonicSimpsonNewtonRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == NOVA_NEDZHIBOV) {
            temp = NedzhibovRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }
        else if(nova_method == NOVA_SIMPSON_NEWTON) {
            temp = SimpsonNewtonRootFindingMethod.getDerivativeArgument(z, fz, dfz);
        }

        if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
            if (parser2.foundZ()) {
                parser2.setZvalue(temp);
            }

            combined_dfz = expr2.getValue();
        } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
            if (parser.foundZ()) {
                parser.setZvalue(temp.plus(Derivative.DZ));
            }

            Complex cmb_fzdz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.sub(Derivative.DZ));
            }

            Complex cmb_fzmdz = expr.getValue();

            combined_dfz = Derivative.numericalCentralDerivativeFirstOrder(cmb_fzdz, cmb_fzmdz);
        } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
            if (parser.foundZ()) {
                parser.setZvalue(temp);
            }

            Complex cmb_fz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.plus(Derivative.DZ));
            }

            Complex cmb_fzdz = expr.getValue();

            combined_dfz = Derivative.numericalForwardDerivativeFirstOrder(cmb_fz, cmb_fzdz);
        } else {
            if (parser.foundZ()) {
                parser.setZvalue(temp);
            }

            Complex cmb_fz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.sub(Derivative.DZ));
            }

            Complex cmb_fzmdz = expr.getValue();

            combined_dfz = Derivative.numericalBackwardDerivativeFirstOrder(cmb_fz, cmb_fzmdz);
        }

        if(nova_method == MainWindow.NOVA_HARMONIC_SIMPSON_NEWTON || nova_method == NOVA_NEDZHIBOV || nova_method == NOVA_SIMPSON_NEWTON) {
            Complex temp2 = z.plus(temp).times_mutable(0.5);

            if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
                if (parser2.foundZ()) {
                    parser2.setZvalue(temp2);
                }

                combined_dfz2 = expr2.getValue();
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                if (parser.foundZ()) {
                    parser.setZvalue(temp2.plus(Derivative.DZ));
                }

                Complex cmb_fzdz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(temp2.sub(Derivative.DZ));
                }

                Complex cmb_fzmdz = expr.getValue();

                combined_dfz2 = Derivative.numericalCentralDerivativeFirstOrder(cmb_fzdz, cmb_fzmdz);
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                if (parser.foundZ()) {
                    parser.setZvalue(temp2);
                }

                Complex cmb_fz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(temp2.plus(Derivative.DZ));
                }

                Complex cmb_fzdz = expr.getValue();

                combined_dfz2 = Derivative.numericalForwardDerivativeFirstOrder(cmb_fz, cmb_fzdz);
            } else {
                if (parser.foundZ()) {
                    parser.setZvalue(temp2);
                }

                Complex cmb_fz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(temp2.sub(Derivative.DZ));
                }

                Complex cmb_fzmdz = expr.getValue();

                combined_dfz2 = Derivative.numericalBackwardDerivativeFirstOrder(cmb_fz, cmb_fzmdz);
            }
        }

        return new Complex[] {combined_dfz, combined_dfz2};
    }

    private Complex combinedDDFZ(Complex z, Complex fz, Complex dfz) {
        Complex temp = null, combined_ddfz;

        if(nova_method == MainWindow.NOVA_RAFIS_RAFIULLAH) {
            temp = RafisRafiullahRootFindingMethod.getSecondDerivativeArgument(z, fz, dfz);
        }

        if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
            if (parser3.foundZ()) {
                parser3.setZvalue(temp);
            }

            combined_ddfz = expr3.getValue();
        } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
            if (parser.foundZ()) {
                parser.setZvalue(temp);
            }

            Complex cmb_fz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.plus(Derivative.DZ));
            }

            Complex cmb_fzdz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.sub(Derivative.DZ));
            }

            Complex cmb_fzmdz = expr.getValue();

            combined_ddfz = Derivative.numericalCentralDerivativeSecondOrder(cmb_fz, cmb_fzdz, cmb_fzmdz);
        } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
            if (parser.foundZ()) {
                parser.setZvalue(temp);
            }

            Complex cmb_fz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.plus(Derivative.DZ));
            }

            Complex cmb_fzdz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.plus(Derivative.DZ_2));
            }

            Complex cmb_fz2dz = expr.getValue();

            combined_ddfz = Derivative.numericalForwardDerivativeSecondOrder(cmb_fz, cmb_fzdz, cmb_fz2dz);
        } else {
            if (parser.foundZ()) {
                parser.setZvalue(temp);
            }

            Complex cmb_fz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.sub(Derivative.DZ));
            }

            Complex cmb_fzmdz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(temp.sub(Derivative.DZ_2));
            }

            Complex cmb_fzm2dz = expr.getValue();

            combined_ddfz = Derivative.numericalBackwardDerivativeSecondOrder(cmb_fz, cmb_fzmdz, cmb_fzm2dz);
        }

        return combined_ddfz;
    }

    @Override
    public void function(Complex[] complex) {

        Complex dfz = null;
        Complex ddfz = null;
        Complex dddfz = null;
        Complex ffz = null;
        Complex combined_dfz = null;
        Complex combined_ddfz = null;
        Complex combined_dfz2 = null;

        if (parserAddend.foundZ()) {
            parserAddend.setZvalue(complex[0]);
        }

        if (parserAddend.foundN()) {
            parserAddend.setNvalue(new Complex(iterations, 0));
        }

        if (parserAddend.foundC()) {
            parserAddend.setCvalue(complex[1]);
        }

        if(parserAddend.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parserAddend.foundVar(i)) {
                    parserAddend.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        Complex addend = exprAddend.getValue();

        //-----------------------------------
        if (parserRelaxation.foundZ()) {
            parserRelaxation.setZvalue(complex[0]);
        }

        if (parserRelaxation.foundN()) {
            parserRelaxation.setNvalue(new Complex(iterations, 0));
        }

        if (parserRelaxation.foundC()) {
            parserRelaxation.setCvalue(complex[1]);
        }

        if(parserRelaxation.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parserRelaxation.foundVar(i)) {
                    parserRelaxation.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        Complex relaxation = exprRelaxation.getValue();

        //-----------------------------------
        if (parser.foundZ()) {
            parser.setZvalue(complex[0]);
        }

        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        if (parser.foundC()) {
            parser.setCvalue(complex[1]);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        Complex fz = expr.getValue();

        Complex fzdz = null;
        Complex fzmdz = null;

        //-----------------------------------
        if (!Settings.isOneFunctionsNovaFormula(nova_method) && nova_method != MainWindow.NOVA_STIRLING) {

            if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
                if (parser2.foundZ()) {
                    parser2.setZvalue(complex[0]);
                }

                if (parser2.foundN()) {
                    parser2.setNvalue(new Complex(iterations, 0));
                }

                if (parser2.foundC()) {
                    parser2.setCvalue(complex[1]);
                }

                if(parser2.foundAnyVar()) {
                    for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                        if (parser2.foundVar(i)) {
                            parser2.setVarsvalue(i, globalVars[i]);
                        }
                    }
                }

                dfz = expr2.getValue();
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ));
                }

                fzdz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ));
                }

                fzmdz = expr.getValue();

                dfz = Derivative.numericalCentralDerivativeFirstOrder(fzdz, fzmdz);
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ));
                }

                fzdz = expr.getValue();

                dfz = Derivative.numericalForwardDerivativeFirstOrder(fz, fzdz);
            } else {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ));
                }

                fzmdz = expr.getValue();

                dfz = Derivative.numericalBackwardDerivativeFirstOrder(fz, fzmdz);
            }
        }

        if (Settings.isThreeFunctionsNovaFormula(nova_method) || Settings.isFourFunctionsNovaFormula(nova_method)) {

            if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
                if (parser3.foundZ()) {
                    parser3.setZvalue(complex[0]);
                }

                if (parser3.foundN()) {
                    parser3.setNvalue(new Complex(iterations, 0));
                }

                if (parser3.foundC()) {
                    parser3.setCvalue(complex[1]);
                }

                if(parser3.foundAnyVar()) {
                    for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                        if (parser3.foundVar(i)) {
                            parser3.setVarsvalue(i, globalVars[i]);
                        }
                    }
                }

                ddfz = expr3.getValue();
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                ddfz = Derivative.numericalCentralDerivativeSecondOrder(fz, fzdz, fzmdz);
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ_2));
                }

                Complex fz2dz = expr.getValue();

                ddfz = Derivative.numericalForwardDerivativeSecondOrder(fz, fzdz, fz2dz);

            } else {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ_2));
                }

                Complex fzm2dz = expr.getValue();

                ddfz = Derivative.numericalBackwardDerivativeSecondOrder(fz, fzmdz, fzm2dz);
            }
        }

        if(Settings.isFourFunctionsNovaFormula(nova_method)) {
            if (Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
                if (parser4.foundZ()) {
                    parser4.setZvalue(complex[0]);
                }

                if (parser4.foundN()) {
                    parser4.setNvalue(new Complex(iterations, 0));
                }

                if (parser4.foundC()) {
                    parser4.setCvalue(complex[1]);
                }

                if(parser4.foundAnyVar()) {
                    for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                        if (parser4.foundVar(i)) {
                            parser4.setVarsvalue(i, globalVars[i]);
                        }
                    }
                }

                dddfz = expr4.getValue();
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ_2));
                }

                Complex fz2dz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ_2));
                }

                Complex fzm2dz = expr.getValue();

                dddfz = Derivative.numericalCentralDerivativeThirdOrder(fzdz, fz2dz, fzmdz, fzm2dz);
            } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ_2));
                }

                Complex fz2dz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].plus(Derivative.DZ_3));
                }

                Complex fz3dz = expr.getValue();

                dddfz = Derivative.numericalForwardDerivativeThirdOrder(fz, fzdz, fz2dz, fz3dz);
            } else {
                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ_2));
                }

                Complex fzm2dz = expr.getValue();

                if (parser.foundZ()) {
                    parser.setZvalue(complex[0].sub(Derivative.DZ_3));
                }

                Complex fzm3dz = expr.getValue();

                dddfz = Derivative.numericalBackwardDerivativeThirdOrder(fz, fzmdz, fzm2dz, fzm3dz);
            }
        }

        if (Settings.hasNovaCombinedFFZ(nova_method)) {
            ffz = combinedFFZ(complex[0], fz, dfz);
        }

        if(Settings.hasNovaCombinedDFZ(nova_method)) {
            Complex[] res = combinedDFZ(complex[0], fz, dfz);
            combined_dfz = res[0];
            combined_dfz2 = res[1];
        }
        else if(Settings.hasNovaCombinedDDFZ(nova_method)) {
            combined_ddfz = combinedDDFZ(complex[0], fz, dfz);
        }

        Complex z = new Complex(complex[0]);
        Complex Fz = new Complex(fz);
        Complex step = new Complex();

        switch (nova_method) {

            case MainWindow.NOVA_NEWTON:
                step = NewtonRootFindingMethod.newtonStep(fz, dfz);
                break;
            case MainWindow.NOVA_HALLEY:
                step = HalleyRootFindingMethod.halleyStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_SCHRODER:
                step = SchroderRootFindingMethod.schroderStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                step = HouseholderRootFindingMethod.householderStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_SECANT:
                step = SecantRootFindingMethod.secantStep(complex[0], fz, complex[2], complex[3]);
                break;
            case MainWindow.NOVA_STEFFENSEN:
                step = SteffensenRootFindingMethod.steffensenStep(fz, ffz);
                break;
            case MainWindow.NOVA_MULLER:
                step = MullerRootFindingMethod.mullerStep(complex[0], complex[4], complex[2], fz, complex[5], complex[3]);
                break;
            case MainWindow.NOVA_PARHALLEY:
                step = ParhalleyRootFindingMethod.parhalleyStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_LAGUERRE:
                step = LaguerreRootFindingMethod.laguerreStep(fz, dfz, ddfz, laguerreDeg);
                break;
            case MainWindow.NOVA_NEWTON_HINES:
                step = NewtonHinesRootFindingMethod.newtonHinesStep(complex[0], fz, dfz, newtonHinesK);
                break;
            case MainWindow.NOVA_WHITTAKER:
                step = WhittakerRootFindingMethod.whittakerStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_WHITTAKER_DOUBLE_CONVEX:
                step = WhittakerDoubleConvexRootFindingMethod.whittakerDoubleConvexStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_SUPER_HALLEY:
                step = SuperHalleyRootFindingMethod.superHalleyStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_MIDPOINT:
                step = MidpointRootFindingMethod.midpointStep(fz, combined_dfz);
                break;
            case MainWindow.NOVA_TRAUB_OSTROWSKI:
                step = TraubOstrowskiRootFindingMethod.traubOstrowskiStep(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_STIRLING:
                step = StirlingRootFindingMethod.stirlingStep(fz, combined_dfz);
                break;
            case MainWindow.NOVA_JARATT:
                step = JarattRootFindingMethod.jarattStep(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_JARATT2:
                step = Jaratt2RootFindingMethod.jaratt2Step(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_WEERAKOON_FERNANDO:
                step = WeerakoonFernandoRootFindingMethod.weerakoonFernandoStep(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_THIRD_ORDER_NEWTON:
                step = ThirdOrderNewtonRootFindingMethod.thirdOrderNewtonStep(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_ABBASBANDY:
                step = AbbasbandyRootFindingMethod.abbasbandyStep(fz, dfz, ddfz, dddfz);
                break;
            case MainWindow.NOVA_HOUSEHOLDER3:
                step = Householder3RootFindingMethod.householder3Step(fz, dfz, ddfz, dddfz);
                break;
            case MainWindow.NOVA_CONTRA_HARMONIC_NEWTON:
                step = ContraHarmonicNewtonRootFindingMethod.chnStep(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_CHUN_HAM:
                step = ChunHamRootFindingMethod.chunHamStep(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_CHUN_KIM:
                step = ChunKimRootFindingMethod.chunKimStep(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_EULER_CHEBYSHEV:
                step = EulerChebyshevRootFindingMethod.eulerChebyshevStep(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_EZZATI_SALEKI2:
                step = EzzatiSaleki2RootFindingMethod.ezzatiSaleki2Step(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_HOMEIER1:
                step = Homeier1RootFindingMethod.homeier1Step(fz, combined_dfz);
                break;
            case MainWindow.NOVA_ABBASBANDY2:
                step = Abbasbandy2RootFindingMethod.abbasbandy2Step(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_ABBASBANDY3:
                step = Abbasbandy3RootFindingMethod.abbasbandy3Step(fz, dfz, ddfz, dddfz);
                break;
            case MainWindow.NOVA_POPOVSKI1:
                step = Popovski1RootFindingMethod.popovski1Step(fz, dfz, ddfz);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN1:
                step = ChangBumChun1RootFindingMethod.changbumChun1Step(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN2:
                step = ChangBumChun2RootFindingMethod.changbumChun2Step(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_KING3:
                step = King3RootFindingMethod.king3Step(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_HOMEIER2:
                step = Homeier2RootFindingMethod.homeier2Step(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_KIM_CHUN:
                step = KimChunRootFindingMethod.kimChunStep(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_KOU_LI_WANG1:
                step = KouLiWang1RootFindingMethod.kouLiWang1Step(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_MAHESHWERI:
                step = MaheshweriRootFindingMethod.maheshweriStep(fz, dfz, ffz);
                break;
            case MainWindow.NOVA_RAFIULLAH1:
                step = Rafiullah1RootFindingMethod.rafiullah1Step(fz, dfz, combined_dfz);
                break;
            case MainWindow.NOVA_RAFIS_RAFIULLAH:
                step = RafisRafiullahRootFindingMethod.rafisRafiullahStep(fz, dfz, ddfz, combined_ddfz);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN3:
                step = ChangBumChun3RootFindingMethod.changbumChun3Step(fz, dfz, ffz, combined_dfz);
                break;
            case MainWindow.NOVA_EZZATI_SALEKI1:
                step = EzzatiSaleki1RootFindingMethod.ezzatiSaleki1Step(fz, dfz, ffz, combined_dfz);
                break;
            case MainWindow.NOVA_FENG:
                step = FengRootFindingMethod.fengStep(fz, dfz, ffz, combined_dfz);
                break;
            case MainWindow.NOVA_KING1:
                step = King1RootFindingMethod.king1Step(fz, dfz, ffz, combined_dfz);
                break;
            case MainWindow.NOVA_NOOR_GUPTA:
                step = NoorGuptaRootFindingMethod.noorGuptaStep(fz, dfz, ffz, combined_dfz);
                break;
            case MainWindow.NOVA_HARMONIC_SIMPSON_NEWTON:
                step = HarmonicSimpsonNewtonRootFindingMethod.hsnStep(fz, dfz, combined_dfz, combined_dfz2);
                break;
            case MainWindow.NOVA_NEDZHIBOV:
                step = NedzhibovRootFindingMethod.nedzhibovStep(fz, dfz, combined_dfz, combined_dfz2);
                break;
            case MainWindow.NOVA_SIMPSON_NEWTON:
                step = SimpsonNewtonRootFindingMethod.simpsonNewtonStep(fz, dfz, combined_dfz, combined_dfz2);
                break;

        }

        do {
            complex[0] = RootFindingMethods.applyStep(z, step, relaxation).plus_mutable(addend);
            if(!useGlobalMethod) {
                break;
            }

            if(complex[0].isNaN() || complex[0].isInfinite()) {
                break;
            }

            if(relaxation.norm_squared() < 1e-4) {
                break;
            }

            if (parser.foundZ()) {
                parser.setZvalue(complex[0]);
            }

            Complex newFz = expr.getValue();

            if(newFz.norm_squared() < Fz.norm_squared()) {
                break;
            }

            relaxation = relaxation.times_mutable(globalMethodFactor);
        } while (true);

        setVariables(zold, zold2);

    }

    private void initExtra(Complex[] complex) {

        if (nova_method == MainWindow.NOVA_SECANT || nova_method == MainWindow.NOVA_MULLER) {
            if (parser.foundZ()) {
                parser.setZvalue(complex[2]);
            }

            if (parser.foundN()) {
                parser.setNvalue(new Complex(iterations, 0));
            }

            if (parser.foundC()) {
                parser.setCvalue(complex[1]);
            }

            if(parser.foundAnyVar()) {
                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (parser.foundVar(i)) {
                        parser.setVarsvalue(i, globalVars[i]);
                    }
                }
            }

            complex[3] = expr.getValue();
        }

        if (nova_method == MainWindow.NOVA_MULLER) {
            if (parser.foundZ()) {
                parser.setZvalue(complex[4]);
            }

            if (parser.foundN()) {
                parser.setNvalue(new Complex(iterations, 0));
            }

            if (parser.foundC()) {
                parser.setCvalue(complex[1]);
            }

            if(parser.foundAnyVar()) {
                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (parser.foundVar(i)) {
                        parser.setVarsvalue(i, globalVars[i]);
                    }
                }
            }

            complex[5] = expr.getValue();
        }
    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();
        complex[4] = new Complex(1e-10, 0);

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        setInitVariables(start, zold, zold2, c0, pixel);

        initExtra(complex);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[4] = new Complex(1e-10, 0);

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        setInitVariables(start, zold, zold2, c0, pixel);

        initExtra(complex);

        return complex;

    }

    private void setVariables(Complex zold, Complex zold2) {

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser2.foundP()) {
            parser2.setPvalue(zold);
        }

        if (parser3.foundP()) {
            parser3.setPvalue(zold);
        }

        if (parserRelaxation.foundP()) {
            parserRelaxation.setPvalue(zold);
        }

        if (parserAddend.foundP()) {
            parserAddend.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser2.foundPP()) {
            parser2.setPPvalue(zold2);
        }

        if (parser3.foundPP()) {
            parser3.setPPvalue(zold2);
        }

        if (parserRelaxation.foundPP()) {
            parserRelaxation.setPPvalue(zold2);
        }

        if (parserAddend.foundPP()) {
            parserAddend.setPPvalue(zold2);
        }

    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2, Complex c0, Complex pixel) {

        if (parser.foundPixel()) {
            parser.setPixelvalue(pixel);
        }

        if (parser2.foundPixel()) {
            parser2.setPixelvalue(pixel);
        }

        if (parser3.foundPixel()) {
            parser3.setPixelvalue(pixel);
        }

        if (parserRelaxation.foundPixel()) {
            parserRelaxation.setPixelvalue(pixel);
        }

        if (parserAddend.foundPixel()) {
            parserAddend.setPixelvalue(pixel);
        }

        if (parser.foundS()) {
            parser.setSvalue(start);
        }

        if (parser2.foundS()) {
            parser2.setSvalue(start);
        }

        if (parser3.foundS()) {
            parser3.setSvalue(start);
        }

        if (parserRelaxation.foundS()) {
            parserRelaxation.setSvalue(start);
        }

        if (parserAddend.foundS()) {
            parserAddend.setSvalue(start);
        }

        if (parser.foundC0()) {
            parser.setC0value(c0);
        }

        if (parser2.foundC0()) {
            parser2.setC0value(c0);
        }

        if (parser3.foundC0()) {
            parser3.setC0value(c0);
        }

        if (parserRelaxation.foundC0()) {
            parserRelaxation.setC0value(c0);
        }

        if (parserAddend.foundC0()) {
            parserAddend.setC0value(c0);
        }

        Complex c_maxn = new Complex(max_iterations, 0);

        if (parser.foundMaxn()) {
            parser.setMaxnvalue(c_maxn);
        }

        if (parser2.foundMaxn()) {
            parser2.setMaxnvalue(c_maxn);
        }

        if (parser3.foundMaxn()) {
            parser3.setMaxnvalue(c_maxn);
        }

        if (parserRelaxation.foundMaxn()) {
            parserRelaxation.setMaxnvalue(c_maxn);
        }

        if (parserAddend.foundMaxn()) {
            parserAddend.setMaxnvalue(c_maxn);
        }

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser2.foundP()) {
            parser2.setPvalue(zold);
        }

        if (parser3.foundP()) {
            parser3.setPvalue(zold);
        }

        if (parserRelaxation.foundP()) {
            parserRelaxation.setPvalue(zold);
        }

        if (parserAddend.foundP()) {
            parserAddend.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser2.foundPP()) {
            parser2.setPPvalue(zold2);
        }

        if (parser3.foundPP()) {
            parser3.setPPvalue(zold2);
        }

        if (parserRelaxation.foundPP()) {
            parserRelaxation.setPPvalue(zold2);
        }

        if (parserAddend.foundPP()) {
            parserAddend.setPPvalue(zold2);
        }

        Complex c_center = new Complex(xCenter, yCenter);

        if (parser.foundCenter()) {
            parser.setCentervalue(c_center);
        }

        if (parser2.foundCenter()) {
            parser2.setCentervalue(c_center);
        }

        if (parser3.foundCenter()) {
            parser3.setCentervalue(c_center);
        }

        if (parserRelaxation.foundCenter()) {
            parserRelaxation.setCentervalue(c_center);
        }

        if (parserAddend.foundCenter()) {
            parserAddend.setCentervalue(c_center);
        }

        Complex c_size = new Complex(size, 0);

        if (parser.foundSize()) {
            parser.setSizevalue(c_size);
        }

        if (parser2.foundSize()) {
            parser2.setSizevalue(c_size);
        }

        if (parser3.foundSize()) {
            parser3.setSizevalue(c_size);
        }

        if (parserRelaxation.foundSize()) {
            parserRelaxation.setSizevalue(c_size);
        }

        if (parserAddend.foundSize()) {
            parserAddend.setSizevalue(c_size);
        }

        Complex c_isize = new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0);
        if (parser.foundISize()) {
            parser.setISizevalue(c_isize);
        }

        if (parser2.foundISize()) {
            parser2.setISizevalue(c_isize);
        }

        if (parser3.foundISize()) {
            parser3.setISizevalue(c_isize);
        }

        if (parserRelaxation.foundISize()) {
            parserRelaxation.setISizevalue(c_isize);
        }

        if (parserAddend.foundISize()) {
            parserAddend.setISizevalue(c_isize);
        }

        Complex c_width = new Complex(TaskRender.WIDTH, 0);

        if (parser.foundWidth()) {
            parser.setWidthvalue(c_width);
        }

        if (parser2.foundWidth()) {
            parser2.setWidthvalue(c_width);
        }

        if (parser3.foundWidth()) {
            parser3.setWidthvalue(c_width);
        }

        if (parserRelaxation.foundWidth()) {
            parserRelaxation.setWidthvalue(c_width);
        }

        if (parserAddend.foundWidth()) {
            parserAddend.setWidthvalue(c_width);
        }

        Complex c_height = new Complex(TaskRender.HEIGHT, 0);

        if (parser.foundHeight()) {
            parser.setHeightvalue(c_height);
        }

        if (parser2.foundHeight()) {
            parser2.setHeightvalue(c_height);
        }

        if (parser3.foundHeight()) {
            parser3.setHeightvalue(c_height);
        }

        if (parserRelaxation.foundHeight()) {
            parserRelaxation.setHeightvalue(c_height);
        }

        if (parserAddend.foundHeight()) {
            parserAddend.setHeightvalue(c_height);
        }

        if (parser.foundPoint()) {
            parser.setPointvalue(point);
        }

        if (parser2.foundPoint()) {
            parser2.setPointvalue(point);
        }

        if (parser3.foundPoint()) {
            parser3.setPointvalue(point);
        }

        if (parserRelaxation.foundPoint()) {
            parserRelaxation.setPointvalue(point);
        }

        if (parserAddend.foundPoint()) {
            parserAddend.setPointvalue(point);
        }
    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {

        if(!isJulia) {
            return null;
        }

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser.foundZ()) {
            parser.setZvalue(z);
        }

        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        if (parser.foundC()) {
            parser.setCvalue(c);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        return expr.getValue();

    }

}
