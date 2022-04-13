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
import fractalzoomer.core.DeepReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
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
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona2
 */
public class Nova extends ExtendedConvergentType {

    private Complex z_exponent;
    private Complex relaxation;
    private int nova_method;
    private Complex newtonHinesK;
    private boolean supportsPerturbation;

    public Nova() {
        super();
    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] newton_hines_k, boolean defaultNovaInitialValue) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        
        if(nova_method == MainWindow.NOVA_TRAUB_OSTROWSKI) {
            convergent_bailout = 1E-8;
        }

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        supportsPerturbation = false;
        if(nova_method == MainWindow.NOVA_NEWTON && z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0 && defaultNovaInitialValue) {
            power = 3;
            supportsPerturbation = true;
        }

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

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

        defaultInitVal = new InitialValue(1, 0);

        supportsPerturbation = false;
        if(nova_method == MainWindow.NOVA_NEWTON && z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0 && defaultNovaInitialValue) {
            power = 3;
            supportsPerturbation = true;
        }

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
            init_val = defaultInitVal;
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

        defaultInitVal = new InitialValue(1, 0);

        supportsPerturbation = false;
        if(nova_method == MainWindow.NOVA_NEWTON && z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0 && defaultNovaInitialValue) {
            power = 3;
            supportsPerturbation = true;
        }

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

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] newton_hines_k, boolean defaultNovaInitialValue, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        defaultInitVal = new InitialValue(1, 0);

        supportsPerturbation = false;
        if(nova_method == MainWindow.NOVA_NEWTON && z_exponent[0] == 3 &&  z_exponent[1] == 0 && relaxation[0] == 1 && relaxation[1] == 0 && defaultNovaInitialValue) {
            power = 3;
            supportsPerturbation = true;
        }

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        pertur_val = new DefaultPerturbation();
        if(defaultNovaInitialValue) {
            init_val = defaultInitVal;
        }
        else {
            init_val = new DefaultInitialValue();
        }

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

        if(nova_method == NOVA_HARMONIC_SIMPSON_NEWTON || nova_method == NOVA_NEDZHIBOV || nova_method == NOVA_SIMPSON_NEWTON) {
            Complex temp2 = z.plus(temp).times_mutable(0.5);

            if (z_exponent.getIm() == 0) {
                if (z_exponent.getRe() == 2) {
                    combined_dfz2 = temp2.times_mutable(2);
                } else if (z_exponent.getRe() == 3) {
                    combined_dfz2 = temp2.square_mutable().times_mutable(3);
                } else if (z_exponent.getRe() == 4) {
                    combined_dfz2 = temp2.cube_mutable().times_mutable(4);
                } else if (z_exponent.getRe() == 5) {
                    combined_dfz2 = temp2.fourth_mutable().times_mutable(5);
                } else if (z_exponent.getRe() == 6) {
                    combined_dfz2 = temp2.fifth_mutable().times_mutable(6);
                } else if (z_exponent.getRe() == 7) {
                    combined_dfz2 = temp2.sixth_mutable().times_mutable(7);
                } else if (z_exponent.getRe() == 8) {
                    combined_dfz2 = temp2.seventh_mutable().times_mutable(8);
                } else if (z_exponent.getRe() == 9) {
                    combined_dfz2 = temp2.eighth_mutable().times_mutable(9);
                } else if (z_exponent.getRe() == 10) {
                    combined_dfz2 = temp2.ninth_mutable().times_mutable(10);
                } else {
                    combined_dfz2 = temp2.pow_mutable(z_exponent.getRe() - 1).times_mutable(z_exponent.getRe());
                }
            } else {
                combined_dfz2 = temp2.pow(z_exponent.sub(1)).times_mutable(z_exponent);
            }
        }

        return new Complex[] {combined_dfz, combined_dfz2};
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

    private Complex combinedDDFZ(Complex z, Complex fz, Complex dfz) {

        Complex temp = null, combined_ddfz;

        if(nova_method == MainWindow.NOVA_RAFIS_RAFIULLAH) {
            temp = RafisRafiullahRootFindingMethod.getSecondDerivativeArgument(z, fz, dfz);
        }

        if (z_exponent.getIm() == 0) {
            if (z_exponent.getRe() == 2) {
                combined_ddfz = new Complex(2, 0);
            } else if (z_exponent.getRe() == 3) {
                combined_ddfz = temp.times_mutable(6);
            } else if (z_exponent.getRe() == 4) {
                combined_ddfz = temp.square_mutable().times_mutable(12);
            } else if (z_exponent.getRe() == 5) {
                combined_ddfz = temp.cube_mutable().times_mutable(20);
            } else if (z_exponent.getRe() == 6) {
                combined_ddfz = temp.fourth_mutable().times_mutable(30);
            } else if (z_exponent.getRe() == 7) {
                combined_ddfz = temp.fifth_mutable().times_mutable(42);
            } else if (z_exponent.getRe() == 8) {
                combined_ddfz = temp.sixth_mutable().times_mutable(56);
            } else if (z_exponent.getRe() == 9) {
                combined_ddfz = temp.seventh_mutable().times_mutable(72);
            } else if (z_exponent.getRe() == 10) {
                combined_ddfz = temp.eighth_mutable().times_mutable(90);
            } else {
                combined_ddfz = temp.pow_mutable(z_exponent.getRe() - 2).times_mutable(z_exponent.getRe()).times_mutable(z_exponent.getRe() - 1);
            }
        } else {
            combined_ddfz = temp.pow(z_exponent.sub(2)).times_mutable(z_exponent).times_mutable(z_exponent.sub(1));
        }

        return combined_ddfz;
    }

    @Override
    public void function(Complex[] complex) {

        Complex fz = null;
        Complex dfz = null;
        Complex ddfz = null;
        Complex dddfz = null;
        Complex ffz = null;
        Complex combined_dfz = null;
        Complex combined_ddfz = null;
        Complex combined_dfz2 = null;

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

        if (Settings.hasNovaCombinedDFZ(nova_method)) {
            Complex[] res = combinedDFZ(complex[0], fz, dfz);
            combined_dfz = res[0];
            combined_dfz2 = res[1];
        }
        else if (Settings.hasNovaCombinedDDFZ(nova_method)) {
            combined_ddfz = combinedDDFZ(complex[0], fz, dfz);
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
            case MainWindow.NOVA_CONTRA_HARMONIC_NEWTON:
                ContraHarmonicNewtonRootFindingMethod.chnMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_CHUN_HAM:
                ChunHamRootFindingMethod.chunHamMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_CHUN_KIM:
                ChunKimRootFindingMethod.chunKimMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_EULER_CHEBYSHEV:
                EulerChebyshevRootFindingMethod.eulerChebyshevMethod(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_EZZATI_SALEKI2:
                EzzatiSaleki2RootFindingMethod.ezzatiSaleki2Method(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HOMEIER1:
                Homeier1RootFindingMethod.homeier1Method(complex[0], fz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_ABBASBANDY2:
                Abbasbandy2RootFindingMethod.abbasbandy2Method(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_ABBASBANDY3:
                Abbasbandy3RootFindingMethod.abbasbandy3Method(complex[0], fz, dfz, ddfz, dddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_POPOVSKI1:
                Popovski1RootFindingMethod.popovski1Method(complex[0], fz, dfz, ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN1:
                ChangBumChun1RootFindingMethod.changbumChun1Method(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN2:
                ChangBumChun2RootFindingMethod.changbumChun2Method(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_KING3:
                King3RootFindingMethod.king3Method(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HOMEIER2:
                Homeier2RootFindingMethod.homeier2Method(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_KIM_CHUN:
                KimChunRootFindingMethod.kimChunMethod(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_KOU_LI_WANG1:
                KouLiWang1RootFindingMethod.kouLiWang1Method(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_MAHESHWERI:
                MaheshweriRootFindingMethod.maheshweriMethod(complex[0], fz, dfz, ffz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_RAFIULLAH1:
                Rafiullah1RootFindingMethod.rafiullah1Method(complex[0], fz, dfz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_RAFIS_RAFIULLAH:
                RafisRafiullahRootFindingMethod.rafisRafiullahMethod(complex[0], fz, dfz, ddfz, combined_ddfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_CHANGBUM_CHUN3:
                ChangBumChun3RootFindingMethod.changbumChun3Method(complex[0], fz, dfz, ffz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_EZZATI_SALEKI1:
                EzzatiSaleki1RootFindingMethod.ezzatiSaleki1Method(complex[0], fz, dfz, ffz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_FENG:
                FengRootFindingMethod.fengMethod(complex[0], fz, dfz, ffz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_KING1:
                King1RootFindingMethod.king1Method(complex[0], fz, dfz, ffz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_NOOR_GUPTA:
                NoorGuptaRootFindingMethod.noorGuptaMethod(complex[0], fz, dfz, ffz, combined_dfz, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_HARMONIC_SIMPSON_NEWTON:
                HarmonicSimpsonNewtonRootFindingMethod.hsnMethod(complex[0], fz, dfz, combined_dfz, combined_dfz2, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_NEDZHIBOV:
                NedzhibovRootFindingMethod.nedzhibovMethod(complex[0], fz, dfz, combined_dfz, combined_dfz2, relaxation).plus_mutable(complex[1]);
                break;
            case MainWindow.NOVA_SIMPSON_NEWTON:
                SimpsonNewtonRootFindingMethod.simpsonNewtonMethod(complex[0], fz, dfz, combined_dfz, combined_dfz2, relaxation).plus_mutable(complex[1]);
                break;

        }

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[6];

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
            if(!isOrbit && !isDomain) {
                Complex temp = pixel.plus(refPointSmall);
                complex[0] = new Complex(defaultInitVal.getValue(temp));
                complex[1] = new Complex(temp);
            }
            else {
                complex[0] = new Complex(defaultInitVal.getValue(pixel));
                complex[1] = new Complex(pixel);
            }

        }
        else {
            complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));
            complex[1] = new Complex(pixel);//c
            complex[2] = new Complex();
            complex[3] = new Complex(-1, 0);
            complex[4] = new Complex(1e-10, 0);
            complex[5] = complex[4].pow(z_exponent).sub_mutable(1);
        }

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public Complex[] initializeSeed(Complex pixel) {

        Complex[] complex = new Complex[6];

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {

            if(!isOrbit && !isDomain) {
                complex[0] = pixel.plus(refPointSmall);
                complex[1] = new Complex(seed);//c
            }
            else {
                complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
                complex[1] = new Complex(seed);//c
            }

        }
        else {
            complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
            complex[1] = new Complex(seed);//c
            complex[2] = new Complex();
            complex[3] = new Complex(-1, 0);
            complex[4] = new Complex(1e-10, 0);
            complex[5] = complex[4].pow(z_exponent).sub_mutable(1);
        }


        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public boolean supportsPerturbationTheory() {

        if(isJuliaMap) {
            return false;
        }

        if(isJulia && juliter) {
            return false;
        }

        return supportsPerturbation;
    }

    @Override
    public void calculateReferencePoint(GenericComplex gpixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if(iterations == 0) {
            Reference = new double[max_iterations << 1];

            if(isJulia) {
                ReferenceSubPixel = new double[max_iterations << 1];
            }
            else {
                ReferenceSubCp = new double[max_iterations << 1];
            }

            PrecalculatedTerms = new double[max_iterations << 1];

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep = new DeepReference(max_iterations);
                }
                else {
                    ReferenceSubCpDeep = new DeepReference(max_iterations);
                }

                PrecalculatedTermsDeep = new DeepReference(max_iterations);
            }
        }
        else if (max_iterations > getReferenceLength()){
            Reference = Arrays.copyOf(Reference, max_iterations << 1);

            if(isJulia) {
                ReferenceSubPixel = Arrays.copyOf(ReferenceSubPixel, max_iterations << 1);
            }
            else {
                ReferenceSubCp = Arrays.copyOf(ReferenceSubCp, max_iterations << 1);
            }

            PrecalculatedTerms = Arrays.copyOf(PrecalculatedTerms, max_iterations << 1);

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);

                if(isJulia) {
                    ReferenceSubPixelDeep.resize(max_iterations);
                }
                else {
                    ReferenceSubCpDeep.resize(max_iterations);
                }

                PrecalculatedTermsDeep.resize(max_iterations);
            }

            System.gc();
        }

        BigComplex pixel = (BigComplex)gpixel;

        //Due to zero, all around zero will not work
        if(isJulia && pixel.norm().compareTo(new MyApfloat(1e-4)) < 0) {
            Apfloat dx = new MyApfloat(1e-4);
            pixel = new BigComplex(dx, dx);
        }

        BigComplex initVal = new BigComplex(defaultInitVal.getValue(null));

        BigComplex z = iterations == 0 ? (isJulia ? pixel : initVal) : (BigComplex)lastZValue;

        BigComplex c = isJulia ? new BigComplex(seed) : pixel;

        BigComplex zold = iterations == 0 ? new BigComplex() : (BigComplex)secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : (BigComplex)thirdTolastZValue;
        BigComplex start = isJulia ? pixel : initVal;
        BigComplex c0 = c;

        Location loc = new Location();

        refPoint = pixel;

        refPointSmall = pixel.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        RefType = getRefType();

        Apfloat three = new MyApfloat(3.0);

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            setArrayValue(Reference, iterations, cz);

            BigComplex zsubcp = null;
            BigComplex zsubpixel = null;
            if(isJulia) {
                zsubpixel = z.sub(pixel);
                setArrayValue(ReferenceSubPixel, iterations, zsubpixel.toComplex());
            }
            else {
                zsubcp = z.sub(initVal);
                setArrayValue(ReferenceSubCp, iterations, zsubcp.toComplex());
            }

            BigComplex preCalc = z.fourth().sub(z); //Z^4-Z for catastrophic cancelation
            setArrayValue(PrecalculatedTerms, iterations, preCalc.toComplex());

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));

                if(isJulia) {
                    setArrayDeepValue(ReferenceSubPixelDeep, iterations, loc.getMantExpComplex(zsubpixel));
                }
                else {
                    setArrayDeepValue(ReferenceSubCpDeep, iterations, loc.getMantExpComplex(zsubcp));
                }

                setArrayDeepValue(PrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(z, zold, zold2, iterations, c, start, c0, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                z = z.sub(z.cube().sub(MyApfloat.ONE).divide(z.square().times(three))).plus(c);
            }
            catch (Exception ex) {
                break;
            }

            if(progress != null && iterations % 1000 == 0) {
                progress.setValue(iterations - initIterations);
                progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (iterations - initIterations) / progress.getMaximum() * 100)) + "%");
            }

        }

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        skippedIterations = 0;

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }
        ReferenceCalculationTime = System.currentTimeMillis() - time;

    }


    //(z * ((2*Z+z)*z*Z^2 + (Z^4 - Z) -0.5 *z)) / (1.5 * ((2*Z+z)*z*Z^2 + Z^4)) + c
    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

       /* Complex X = Reference[RefIteration];

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
*/

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;
        Complex c = DeltaSub0;

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(PrecalculatedTerms, RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5)).plus_mutable(c);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;
        MantExpComplex c = DeltaSub0;

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).sub_mutable(z.times05()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE)).plus_mutable(c);
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex Z = getArrayValue(Reference, RefIteration);
        Complex z = DeltaSubN;

        Complex temp = Z.times(2).plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayValue(PrecalculatedTerms, RefIteration)).sub_mutable(z.times(0.5)).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(1.5));

    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex Z = getArrayDeepValue(ReferenceDeep, RefIteration);
        MantExpComplex z = DeltaSubN;

        MantExpComplex temp = Z.times2().plus_mutable(z).times_mutable(z).times_mutable(Z.square());
        return temp.plus(getArrayDeepValue(PrecalculatedTermsDeep, RefIteration)).sub_mutable(z.times05()).times_mutable(z).divide_mutable(temp.plus(Z.fourth()).times_mutable(MantExp.ONEPOINTFIVE));
    }

    @Override
    public String getRefType() {
        return super.getRefType() + "-" + z_exponent.toString() + "-" + relaxation.toString() + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public void function(BigComplex[] complex) {
        complex[0] = complex[0].sub(complex[0].cube().sub(MyApfloat.ONE).divide(complex[0].square().times(new MyApfloat(3.0)))).plus(complex[1]);
    }

    @Override
    public BigComplex[] initialize(BigComplex pixel) {

        BigComplex[] complex = new BigComplex[2];

        complex[0] = new BigComplex(new BigComplex(1, 0));//z
        complex[1] = new BigComplex(pixel);//c

        //zold = new Complex();
        //zold2 = new Complex();
        //start = new Complex(complex[0]);
        //c0 = new Complex(complex[1]);

        return complex;

    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {

        if(!isJulia) {
            return null;
        }

        Complex fz;
        if (z_exponent.getIm() == 0) {
            if (z_exponent.getRe() == 2) {
                fz = z.square().sub_mutable(1);
            } else if (z_exponent.getRe() == 3) {
                fz = z.cube().sub_mutable(1);
            } else if (z_exponent.getRe() == 4) {
                fz = z.fourth().sub_mutable(1);
            } else if (z_exponent.getRe() == 5) {
                fz = z.fifth().sub_mutable(1);
            } else if (z_exponent.getRe() == 6) {
                fz = z.sixth().sub_mutable(1);
            } else if (z_exponent.getRe() == 7) {
                fz = z.seventh().sub_mutable(1);
            } else if (z_exponent.getRe() == 8) {
                fz = z.eighth().sub_mutable(1);
            } else if (z_exponent.getRe() == 9) {
                fz = z.ninth().sub_mutable(1);
            } else if (z_exponent.getRe() == 10) {
                fz = z.tenth().sub_mutable(1);
            } else {
                fz = z.pow(z_exponent.getRe()).sub_mutable(1);
            }
        } else {
            fz = z.pow(z_exponent).sub_mutable(1);
        }

        return fz;
    }

}
