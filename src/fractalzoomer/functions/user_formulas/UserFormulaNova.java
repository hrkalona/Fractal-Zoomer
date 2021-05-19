/*
 * Copyright (C) 2020 hrkalona2
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
package fractalzoomer.functions.user_formulas;

import fractalzoomer.core.Complex;
import fractalzoomer.core.Derivative;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.root_finding_methods.abbasbandy.AbbasbandyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.halley.HalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder3.Householder3RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt.JarattRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.jaratt2.Jaratt2RootFindingMethod;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.midpoint.MidpointRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.muller.MullerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton_hines.NewtonHinesRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.secant.SecantRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.stirling.StirlingRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.super_halley.SuperHalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.third_order_newton.ThirdOrderNewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.WeerakoonFernandoRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.TraubOstrowskiRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker.WhittakerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.WhittakerDoubleConvexRootFindingMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

import java.util.ArrayList;

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

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        //Todo: Check which other methods need this
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            convergent_bailout = 1E-8;
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

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        } else {
            pertur_val = new DefaultPerturbation();
        }

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
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        //override some algorithms
        switch (out_coloring_algorithm) {
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
        }

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);
    }

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);
        
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            convergent_bailout = 1E-8;
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

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
            case MainWindow.BINARY_DECOMPOSITION2:
            case MainWindow.BANDED:
                if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER || nova_method == MainWindow.NOVA_WHITTAKER || nova_method == MainWindow.NOVA_WHITTAKER_DOUBLE_CONVEX || nova_method == MainWindow.NOVA_SUPER_HALLEY) {
                    convergent_bailout = 1E-4;
                } else if (nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                    convergent_bailout = 1E-9;
                } else if (nova_method == MainWindow.NOVA_SCHRODER) {
                    convergent_bailout = 1E-6;
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);
    }

    //orbit
    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

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

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            } else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        } else {
            pertur_val = new DefaultPerturbation();
        }

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
            init_val = new DefaultInitialValue();
        }

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

    }

    public UserFormulaNova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, String user_dddfz_formula, String user_relaxation_formula, String user_nova_addend_formula, double[] laguerre_deg, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

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

        if (parser.foundZ()) {
            parser.setZvalue(temp);
        }

        return expr.getValue();

    }

    private Complex combinedDFZ(Complex z, Complex fz, Complex dfz) {
        Complex temp = null, combined_dfz;

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

        return combined_dfz;
    }

    @Override
    public void function(Complex[] complex) {

        Complex dfz = null;
        Complex ddfz = null;
        Complex dddfz = null;
        Complex ffz = null;
        Complex combined_dfz = null;

        if (parserAddend.foundZ()) {
            parserAddend.setZvalue(complex[0]);
        }

        if (parserAddend.foundN()) {
            parserAddend.setNvalue(new Complex(iterations, 0));
        }

        if (parserAddend.foundC()) {
            parserAddend.setCvalue(complex[1]);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parserAddend.foundVar(i)) {
                parserAddend.setVarsvalue(i, globalVars[i]);
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

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parserRelaxation.foundVar(i)) {
                parserRelaxation.setVarsvalue(i, globalVars[i]);
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

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
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

                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (parser2.foundVar(i)) {
                        parser2.setVarsvalue(i, globalVars[i]);
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

                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (parser3.foundVar(i)) {
                        parser3.setVarsvalue(i, globalVars[i]);
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

                for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                    if (parser4.foundVar(i)) {
                        parser4.setVarsvalue(i, globalVars[i]);
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
        else if(Settings.hasNovaCombinedDFZ(nova_method)) {
            combined_dfz = combinedDFZ(complex[0], fz, dfz);
        }

        switch (nova_method) {

            case MainWindow.NOVA_NEWTON:
                NewtonRootFindingMethod.newtonMethod(complex[0], fz, dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_HALLEY:
                HalleyRootFindingMethod.halleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_SCHRODER:
                SchroderRootFindingMethod.schroderMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                HouseholderRootFindingMethod.householderMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_SECANT:
                SecantRootFindingMethod.secantMethod(complex[0], fz, complex[2], complex[3], relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_STEFFENSEN:
                SteffensenRootFindingMethod.steffensenMethod(complex[0], fz, ffz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_MULLER:
                MullerRootFindingMethod.mullerMethod(complex[0], complex[4], complex[2], fz, complex[5], complex[3], relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_PARHALLEY:
                ParhalleyRootFindingMethod.parhalleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_LAGUERRE:
                LaguerreRootFindingMethod.laguerreMethod(complex[0], fz, dfz, ddfz, laguerreDeg, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_NEWTON_HINES:
                NewtonHinesRootFindingMethod.newtonHinesMethod(complex[0], fz, dfz, newtonHinesK, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_WHITTAKER:
                WhittakerRootFindingMethod.whittakerMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_WHITTAKER_DOUBLE_CONVEX:
                WhittakerDoubleConvexRootFindingMethod.whittakerDoubleConvexMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_SUPER_HALLEY:
                SuperHalleyRootFindingMethod.superHalleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_MIDPOINT:
                MidpointRootFindingMethod.midpointMethod(complex[0], fz, combined_dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_TRAUB_OSTROWSKI:
                TraubOstrowskiRootFindingMethod.traubOstrowskiMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_STIRLING:
                StirlingRootFindingMethod.stirlingMethod(complex[0], fz, combined_dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_JARATT:
                JarattRootFindingMethod.jarattMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_JARATT2:
                Jaratt2RootFindingMethod.jaratt2Method(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_WEERAKOON_FERNANDO:
                WeerakoonFernandoRootFindingMethod.weerakoonFernandoMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_THIRD_ORDER_NEWTON:
                ThirdOrderNewtonRootFindingMethod.thirdOrderNewtonMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_ABBASBANDY:
                AbbasbandyRootFindingMethod.abbasbandyMethod(complex[0], fz, dfz, ddfz, dddfz, relaxation).plus_mutable(addend);
                break;
            case MainWindow.NOVA_HOUSEHOLDER3:
                Householder3RootFindingMethod.householder3Method(complex[0], fz, dfz, ddfz, dddfz, relaxation).plus_mutable(addend);
                break;

        }

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

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
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

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
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

        setInitVariables(start, zold, zold2);

        initExtra(complex);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[4] = new Complex(1e-10, 0);

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);

        setInitVariables(start, zold, zold2);

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

    private void setInitVariables(Complex start, Complex zold, Complex zold2) {

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

        Complex c_isize = new Complex(ThreadDraw.IMAGE_SIZE, 0);
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
    public double getFractal3DHeight(double value) {

        if (escaped) {
            double res = out_color_algorithm.getResult3D(iterationData);

            res = getFinalValueOut(res);

            return ColorAlgorithm.transformResultToHeight(res, max_iterations);
        }

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

    }

}
