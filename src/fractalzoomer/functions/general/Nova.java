/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
package fractalzoomer.functions.general;

import fractalzoomer.core.*;
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
import fractalzoomer.functions.root_finding_methods.traub_ostrowski.TraubOstrowskiRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.weerakoon_fernando.WeerakoonFernandoRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker.WhittakerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.whittaker_double_convex.WhittakerDoubleConvexRootFindingMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class Nova extends ExtendedConvergentType {

    private Complex z_exponent;
    private Complex relaxation;
    private int nova_method;
    private Complex newtonHinesK;
    //private boolean supportsPerturbation;

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] newton_hines_k, boolean defaultNovaInitialValue) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            convergent_bailout = 1E-8;
        }

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        /*supportsPerturbation = false;
        if(z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0) {
            power = 3;
            supportsPerturbation = true;
        }*/

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        defaultInitVal = new Complex(1, 0);

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
            if(defaultNovaInitialValue) {
                init_val = new InitialValue(defaultInitVal);
            }
            else {
                init_val = new DefaultInitialValue();
            }
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        //override some algorithms
        /*switch (out_coloring_algorithm) {
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
        }*/

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] newton_hines_k, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        //Todo: Check which other methods need this
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            convergent_bailout = 1E-8;
        }

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        defaultInitVal = new Complex(1, 0);

        /*supportsPerturbation = false;
        if(z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0) {
            power = 3;
        }*/

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

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

        pertur_val = new DefaultPerturbation();

        if(defaultNovaInitialValue) {
            init_val = new InitialValue(defaultInitVal);
        }
        else {
            init_val = new DefaultInitialValue();
        }
    }

    //orbit
    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] newton_hines_k, boolean defaultNovaInitialValue) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        defaultInitVal = new Complex(1, 0);

        /*supportsPerturbation = false;
        if(z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0) {
            power = 3;
        }*/

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
            if(defaultNovaInitialValue) {
                init_val = new InitialValue(defaultInitVal);
            }
            else {
                init_val = new DefaultInitialValue();
            }
        }

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] newton_hines_k, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        defaultInitVal = new Complex(1, 0);

        /*supportsPerturbation = false;
        if(z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0) {
            power = 3;
        }*/

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        pertur_val = new DefaultPerturbation();
        if(defaultNovaInitialValue) {
            init_val = new InitialValue(defaultInitVal);
        }
        else {
            init_val = new DefaultInitialValue();
        }

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

        if (z_exponent.getIm() == 0) {
            if (z_exponent.getRe() == 2) {
                combined_dfz = temp.times(2);
            } else if (z_exponent.getRe() == 3) {
                combined_dfz = temp.square().times_mutable(3);
            } else if (z_exponent.getRe() == 4) {
                combined_dfz = temp.cube().times_mutable(4);
            } else if (z_exponent.getRe() == 5) {
                combined_dfz = temp.fourth().times_mutable(5);
            } else if (z_exponent.getRe() == 6) {
                combined_dfz = temp.fifth().times_mutable(6);
            } else if (z_exponent.getRe() == 7) {
                combined_dfz = temp.sixth().times_mutable(7);
            } else if (z_exponent.getRe() == 8) {
                combined_dfz = temp.seventh().times_mutable(8);
            } else if (z_exponent.getRe() == 9) {
                combined_dfz = temp.eighth().times_mutable(9);
            } else if (z_exponent.getRe() == 10) {
                combined_dfz = temp.ninth().times_mutable(10);
            } else {
                combined_dfz = temp.pow(z_exponent.getRe() - 1).times_mutable(z_exponent.getRe());
            }
        } else {
            combined_dfz = temp.pow(z_exponent.sub(1)).times_mutable(z_exponent);
        }

        return combined_dfz;
    }

    private Complex combinedFFZ(Complex z, Complex fz, Complex dfz) {

        Complex temp = null, ffz;

        if(nova_method == MainWindow.NOVA_STEFFENSEN) {
            temp = SteffensenRootFindingMethod.getFunctionArgument(z, fz);
        }
        else if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            temp = TraubOstrowskiRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }
        else if(nova_method == MainWindow.NOVA_THIRD_ORDER_NEWTON) {
            temp = ThirdOrderNewtonRootFindingMethod.getFunctionArgument(z, fz, dfz);
        }

        if (z_exponent.getIm() == 0) {
            if (z_exponent.getRe() == 2) {
                ffz = temp.square_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 3) {
                ffz = temp.cube_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 4) {
                ffz = temp.fourth_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 5) {
                ffz = temp.fifth_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 6) {
                ffz = temp.sixth_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 7) {
                ffz = temp.seventh_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 8) {
                ffz = temp.eighth_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 9) {
                ffz = temp.ninth_mutable().sub_mutable(1);
            } else if (z_exponent.getRe() == 10) {
                ffz = temp.tenth_mutable().sub_mutable(1);
            } else {
                ffz = temp.pow_mutable(z_exponent.getRe()).sub_mutable(1);
            }
        } else {
            ffz = temp.pow(z_exponent).sub_mutable(1);
        }

        return ffz;
    }

    @Override
    public void function(Complex[] complex) {

        Complex fz = null;
        Complex dfz = null;
        Complex ddfz = null;
        Complex dddfz = null;
        Complex ffz = null;
        Complex combined_dfz = null;

        if (z_exponent.getIm() == 0) {
            if (z_exponent.getRe() == 2) {
                fz = complex[0].square().sub_mutable(1);
            } else if (z_exponent.getRe() == 3) {
                fz = complex[0].cube().sub_mutable(1);
            } else if (z_exponent.getRe() == 4) {
                fz = complex[0].fourth().sub_mutable(1);
            } else if (z_exponent.getRe() == 5) {
                fz = complex[0].fifth().sub_mutable(1);
            } else if (z_exponent.getRe() == 6) {
                fz = complex[0].sixth().sub_mutable(1);
            } else if (z_exponent.getRe() == 7) {
                fz = complex[0].seventh().sub_mutable(1);
            } else if (z_exponent.getRe() == 8) {
                fz = complex[0].eighth().sub_mutable(1);
            } else if (z_exponent.getRe() == 9) {
                fz = complex[0].ninth().sub_mutable(1);
            } else if (z_exponent.getRe() == 10) {
                fz = complex[0].tenth().sub_mutable(1);
            } else {
                fz = complex[0].pow(z_exponent.getRe()).sub_mutable(1);
            }
        } else {
            fz = complex[0].pow(z_exponent).sub_mutable(1);
        }

        if (!Settings.isOneFunctionsNovaFormula(nova_method) && nova_method != MainWindow.NOVA_STIRLING) {
            if (z_exponent.getIm() == 0) {
                if (z_exponent.getRe() == 2) {
                    dfz = complex[0].times(2);
                } else if (z_exponent.getRe() == 3) {
                    dfz = complex[0].square().times_mutable(3);
                } else if (z_exponent.getRe() == 4) {
                    dfz = complex[0].cube().times_mutable(4);
                } else if (z_exponent.getRe() == 5) {
                    dfz = complex[0].fourth().times_mutable(5);
                } else if (z_exponent.getRe() == 6) {
                    dfz = complex[0].fifth().times_mutable(6);
                } else if (z_exponent.getRe() == 7) {
                    dfz = complex[0].sixth().times_mutable(7);
                } else if (z_exponent.getRe() == 8) {
                    dfz = complex[0].seventh().times_mutable(8);
                } else if (z_exponent.getRe() == 9) {
                    dfz = complex[0].eighth().times_mutable(9);
                } else if (z_exponent.getRe() == 10) {
                    dfz = complex[0].ninth().times_mutable(10);
                } else {
                    dfz = complex[0].pow(z_exponent.getRe() - 1).times_mutable(z_exponent.getRe());
                }
            } else {
                dfz = complex[0].pow(z_exponent.sub(1)).times_mutable(z_exponent);
            }
        }

        if (Settings.isThreeFunctionsNovaFormula(nova_method) || Settings.isFourFunctionsNovaFormula(nova_method)) {
            if (z_exponent.getIm() == 0) {
                if (z_exponent.getRe() == 2) {
                    ddfz = new Complex(2, 0);
                } else if (z_exponent.getRe() == 3) {
                    ddfz = complex[0].times(6);
                } else if (z_exponent.getRe() == 4) {
                    ddfz = complex[0].square().times_mutable(12);
                } else if (z_exponent.getRe() == 5) {
                    ddfz = complex[0].cube().times_mutable(20);
                } else if (z_exponent.getRe() == 6) {
                    ddfz = complex[0].fourth().times_mutable(30);
                } else if (z_exponent.getRe() == 7) {
                    ddfz = complex[0].fifth().times_mutable(42);
                } else if (z_exponent.getRe() == 8) {
                    ddfz = complex[0].sixth().times_mutable(56);
                } else if (z_exponent.getRe() == 9) {
                    ddfz = complex[0].seventh().times_mutable(72);
                } else if (z_exponent.getRe() == 10) {
                    ddfz = complex[0].eighth().times_mutable(90);
                } else {
                    ddfz = complex[0].pow(z_exponent.getRe() - 2).times_mutable(z_exponent.getRe() * (z_exponent.getRe() - 1));
                }
            } else {
                ddfz = complex[0].pow(z_exponent.sub(2)).times_mutable(z_exponent.times(z_exponent.sub(1)));
            }
        }

        if (Settings.isFourFunctionsNovaFormula(nova_method)) {
            if (z_exponent.getIm() == 0) {
                if (z_exponent.getRe() == 2) {
                    dddfz = new Complex();
                } else if (z_exponent.getRe() == 3) {
                    dddfz = new Complex(6, 0);
                } else if (z_exponent.getRe() == 4) {
                    dddfz = complex[0].times(24);
                } else if (z_exponent.getRe() == 5) {
                    dddfz = complex[0].square().times_mutable(60);
                } else if (z_exponent.getRe() == 6) {
                    dddfz = complex[0].cube().times_mutable(120);
                } else if (z_exponent.getRe() == 7) {
                    dddfz = complex[0].fourth().times_mutable(210);
                } else if (z_exponent.getRe() == 8) {
                    dddfz = complex[0].fifth().times_mutable(336);
                } else if (z_exponent.getRe() == 9) {
                    dddfz = complex[0].sixth().times_mutable(504);
                } else if (z_exponent.getRe() == 10) {
                    dddfz = complex[0].seventh().times_mutable(720);
                } else {
                    dddfz = complex[0].pow(z_exponent.getRe() - 3).times_mutable(z_exponent.getRe() * (z_exponent.getRe() - 1) * (z_exponent.getRe() - 2));
                }
            } else {
                dddfz = complex[0].pow(z_exponent.sub(3)).times_mutable(z_exponent.times(z_exponent.sub(1)).times(z_exponent.sub(2)));
            }
        }

        if (Settings.hasNovaCombinedFFZ(nova_method)) {
            ffz = combinedFFZ(complex[0], fz, dfz);
        }
        else if (Settings.hasNovaCombinedDFZ(nova_method)) {
            combined_dfz = combinedDFZ(complex[0], fz, dfz);
        }

        switch (nova_method) {

            case MainWindow.NOVA_NEWTON:
                NewtonRootFindingMethod.newtonMethod(complex[0], fz, dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HALLEY:
                HalleyRootFindingMethod.halleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_SCHRODER:
                SchroderRootFindingMethod.schroderMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                HouseholderRootFindingMethod.householderMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_SECANT:
                SecantRootFindingMethod.secantMethod(complex[0], fz, complex[2], complex[3], relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_STEFFENSEN:
                SteffensenRootFindingMethod.steffensenMethod(complex[0], fz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_MULLER:
                MullerRootFindingMethod.mullerMethod(complex[0], complex[4], complex[2], fz, complex[5], complex[3], relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_PARHALLEY:
                ParhalleyRootFindingMethod.parhalleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_LAGUERRE:
                LaguerreRootFindingMethod.laguerreMethod(complex[0], fz, dfz, ddfz, z_exponent, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_NEWTON_HINES:
                NewtonHinesRootFindingMethod.newtonHinesMethod(complex[0], fz, dfz, newtonHinesK, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_WHITTAKER:
                WhittakerRootFindingMethod.whittakerMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_WHITTAKER_DOUBLE_CONVEX:
                WhittakerDoubleConvexRootFindingMethod.whittakerDoubleConvexMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_SUPER_HALLEY:
                SuperHalleyRootFindingMethod.superHalleyMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_MIDPOINT:
                MidpointRootFindingMethod.midpointMethod(complex[0], fz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_TRAUB_OSTROWSKI:
                TraubOstrowskiRootFindingMethod.traubOstrowskiMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_STIRLING:
                StirlingRootFindingMethod.stirlingMethod(complex[0], fz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_JARATT:
                JarattRootFindingMethod.jarattMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_JARATT2:
                Jaratt2RootFindingMethod.jaratt2Method(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_WEERAKOON_FERNANDO:
                WeerakoonFernandoRootFindingMethod.weerakoonFernandoMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_THIRD_ORDER_NEWTON:
                ThirdOrderNewtonRootFindingMethod.thirdOrderNewtonMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_ABBASBANDY:
                AbbasbandyRootFindingMethod.abbasbandyMethod(complex[0], fz, dfz, ddfz, dddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HOUSEHOLDER3:
                Householder3RootFindingMethod.householder3Method(complex[0], fz, dfz, ddfz, dddfz, relaxation).plus_mutable(complex[1]);
                break;

        }

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[6];

        /*if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory() && !isJulia) {
            complex[0] = new Complex();
            if(!isOrbit && !isDomain) {
                        complex[1] = new Complex(pixel.getRe() + refPoint.getRe().doubleValue(), pixel.getIm() + refPoint.getIm().doubleValue());
                  }
                  else {
                      complex[1] = new Complex(pixel);
                   }

        }
        else {*/
            complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
            complex[1] = new Complex(pixel);//c
            complex[2] = new Complex();
            complex[3] = new Complex(-1, 0);
            complex[4] = new Complex(1e-10, 0);
            complex[5] = complex[4].pow(z_exponent).sub_mutable(1);
        //}

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    /*@Override
    public boolean supportsPerturbationTheory() {
        return supportsPerturbation;
    }

    @Override
    public void calculateReferencePoint(BigComplex pixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation) {

        if(iterations == 0) {
            Reference = new Complex[max_iterations];

            if (deepZoom) {
                ReferenceDeep = new MantExpComplex[max_iterations];
            }
        }
        else if (max_iterations > Reference.length){
            Reference = copyReference(Reference, new Complex[max_iterations]);

            if (deepZoom) {
                ReferenceDeep = copyDeepReference(ReferenceDeep,  new MantExpComplex[max_iterations]);
            }
        }

        BigComplex z = iterations == 0 ? new BigComplex() : lastZValue;
        BigComplex c = pixel;
        BigComplex zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;

        refPoint = pixel;

        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;
        RefType = getRefType();
        FullRef = fullReference;

        Location loc = new Location();

        Apfloat twoThirds = new MyApfloat(2.0).divide(new MyApfloat(3.0));
        Apfloat convergentB = new MyApfloat(convergent_bailout);

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
            }

            if (!fullReference && iterations > 0 && z.distance_squared(zold).compareTo(convergentB) <= 0) {
                break;
            }

            zold2 = zold;
            zold = z;

            z = (z.cube().times(twoThirds).sub(z.times(MyApfloat.TWO)).sub(MyApfloat.ONE)).divide(z.plus(MyApfloat.ONE).square()).plus(c).plus(MyApfloat.ONE);

        }

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        skippedIterations = 0;
    }


    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = Reference[RefIteration];

        Complex twoX = X.times(2);
        Complex Xp1 = X.plus(1);
        Complex XA = (twoX.plus(4)).times_mutable(X).plus_mutable(2);
        Complex XB = ((X.times(4).plus_mutable(12)).times_mutable(X).plus_mutable(12)).times_mutable(X).plus_mutable(3);
        Complex XC = (((twoX.plus(8)).times_mutable(X).plus_mutable(12)).times_mutable(X).plus_mutable(6)).times_mutable(X);
        Complex XD = (Xp1.square()).times_mutable(3);


        //XA = (2*X+4)*X+2;
        //XB= ((4*X+12)*X+12)*X+3;
        //XC = (((2*X+8)*X+12)*X+6)*X;
        //XD = 3*((X+1)^2);
        //xn = c + (((XA*x + XB)*x + XC)*x) / (XD*(((X+1)+x)^2));

        return (((XA.times(DeltaSubN).plus_mutable(XB)).times_mutable(DeltaSubN).plus_mutable(XC)).times_mutable(DeltaSubN)).divide_mutable(XD.times((Xp1.plus(DeltaSubN)).square_mutable())).plus_mutable(DeltaSub0);



        //(c, z) |--> c + 1/3*(2*z^3 - 6*z - 3)/(z + 1)^2 + 1
        //(C, Z, c, z) |--> 1/3*(2*(Z^2 + 2*Z + 1)*z^3 + (4*Z^3 + 12*Z^2 + 3*(Z^2 + 2*Z + 1)*c + 12*Z + 3)*z^2 + 3*(Z^4 + 4*Z^3 + 6*Z^2 + 4*Z + 1)*c + 2*(Z^4 + 4*Z^3 + 6*Z^2 + 3*(Z^3 + 3*Z^2 + 3*Z + 1)*c + 3*Z)*z)/(Z^4 + 4*Z^3 + (Z^2 + 2*Z + 1)*z^2 + 6*Z^2 + 2*(Z^3 + 3*Z^2 + 3*Z + 1)*z + 4*Z + 1)

        Complex Z = Reference[RefIteration];
        Complex z = DeltaSubN;
        Complex c = DeltaSub0;

        Complex Zsqr = Z.square();
        Complex Zcube = Z.cube();

        Complex zsqr = z.square();

        Complex temp4 =  Zcube.times(4);

        Complex temp1 = Zsqr.plus(Z.times(2)).plus_mutable(1);
        Complex temp2 = Zcube.plus(Zsqr.times(3)).plus_mutable(Z.times(3)).plus_mutable(1);
        Complex temp3 = Z.fourth().plus_mutable(temp4).plus_mutable(Zsqr.times(6));
        Complex temp5 = Z.times(4).plus_mutable(1);
        Complex temp6 = temp3.plus(temp5);

        Complex z2 = z.times(2);
        Complex c3 = c.times(3);

        Complex A = temp1.times(2).times_mutable(z.cube());
        Complex B = (temp4.plus(Zsqr.times(12)).plus_mutable(temp1.times(c3)).plus_mutable(Z.times(12)).plus_mutable(3)).times_mutable(zsqr);
        Complex C = temp6.times(c3);
        Complex D = (temp3.plus(temp2.times(c3)).plus_mutable(Z.times(3))).times_mutable(z2);
        Complex E = (temp6.plus(temp1.times(zsqr)).plus_mutable(temp2.times(z2))).times_mutable(3);

        return (A.plus_mutable(B).plus_mutable(C).plus_mutable(D)).divide_mutable(E);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = ReferenceDeep[RefIteration];

        return null;
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = Reference[RefIteration];

        Complex twoX = X.times(2);
        Complex Xp1 = X.plus(1);
        Complex XA = (twoX.plus(4)).times_mutable(X).plus_mutable(2);
        Complex XB = ((X.times(4).plus_mutable(12)).times_mutable(X).plus_mutable(12)).times_mutable(X).plus_mutable(3);
        Complex XC = (((twoX.plus(8)).times_mutable(X).plus_mutable(12)).times_mutable(X).plus_mutable(6)).times_mutable(X);
        Complex XD = (Xp1.square()).times_mutable(3);


        //XA = (2*X+4)*X+2;
        //XB= ((4*X+12)*X+12)*X+3;
        //XC = (((2*X+8)*X+12)*X+6)*X;
        //XD = 3*((X+1)^2);
        //xn = c + (((XA*x + XB)*x + XC)*x) / (XD*(((X+1)+x)^2));

        return (((XA.times(DeltaSubN).plus_mutable(XB)).times_mutable(DeltaSubN).plus_mutable(XC)).times_mutable(DeltaSubN)).divide_mutable(XD.times((Xp1.plus(DeltaSubN)).square_mutable()));

    }

    @Override
    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex pixel) {

        iterations = skippedIterations;

        Complex DeltaSubN = new Complex(complex[0]); // Delta z

        if(skippedIterations != 0) {

            DeltaSubN = (Complex) initializeFromSeries(pixel);

        }

        int RefIteration = iterations;

        Complex DeltaSub0 = new Complex(pixel); // Delta c
        double temp = 0;

        Complex zWithoutInitVal = new Complex();

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (iterations > 0 &&  (temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                escaped = true;

                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start, c0};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work

            if(max_iterations > 1){
                zWithoutInitVal = Reference[RefIteration].plus(DeltaSubN);
                complex[0] = zWithoutInitVal.plus(defaultInitVal);
            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            if (zWithoutInitVal.norm_squared() < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = zWithoutInitVal;
                RefIteration = 0;
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0);
        }

        return in;

    }

    @Override
    public String getRefType() {
        return super.getRefType() + "-" + z_exponent.toString();
    }*/

}
