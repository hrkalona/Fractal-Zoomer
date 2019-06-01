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
package fractalzoomer.functions.general;

import fractalzoomer.core.Complex;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.ExtendedConvergentType;
import fractalzoomer.functions.root_finding_methods.halley.HalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.householder.HouseholderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.laguerre.LaguerreRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.muller.MullerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton.NewtonRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton_hines.NewtonHinesRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.parhalley.ParhalleyRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.schroder.SchroderRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.secant.SecantRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.steffensen.SteffensenRootFindingMethod;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
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

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] newton_hines_k) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1E-10;

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

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
    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-10;

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
                if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
                    convergent_bailout = 1E-4;
                } else if (nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                    convergent_bailout = 1E-9;
                } else if (nova_method == MainWindow.NOVA_SCHRODER) {
                    convergent_bailout = 1E-6;
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
                    convergent_bailout = 1E-4;
                } else if (nova_method == MainWindow.NOVA_NEWTON || nova_method == MainWindow.NOVA_STEFFENSEN) {
                    convergent_bailout = 1E-9;
                } else if (nova_method == MainWindow.NOVA_SCHRODER) {
                    convergent_bailout = 1E-6;
                }
                break;
            case MainWindow.BANDED:
                if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_HOUSEHOLDER) {
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
    }

    //orbit
    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] newton_hines_k) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

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

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double[] newton_hines_k, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        newtonHinesK = new Complex(newton_hines_k[0], newton_hines_k[1]);

    }

    @Override
    protected void function(Complex[] complex) {

        Complex fz = null;
        Complex dfz = null;
        Complex ddfz = null;
        Complex ffz = null;

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

        if (nova_method != MainWindow.NOVA_SECANT && nova_method != MainWindow.NOVA_MULLER) {
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

        if (nova_method == MainWindow.NOVA_HALLEY || nova_method == MainWindow.NOVA_SCHRODER || nova_method == MainWindow.NOVA_HOUSEHOLDER || nova_method == MainWindow.NOVA_PARHALLEY || nova_method == MainWindow.NOVA_LAGUERRE) {
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

        if (nova_method == MainWindow.NOVA_STEFFENSEN) {

            Complex temp = complex[0].plus(fz);

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

        }

    }

    @Override
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        double temp = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[6];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (iterations > 0 && (temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;

    }

    @Override
    public double calculateJuliaWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        double temp = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (iterations > 0 && (temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                escaped = true;

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start);
                }

                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start};
                iterationData = object;
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start);
            }

        }

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start);
        }
        
        Object[] object = {complex[0], zold, zold2, complex[1], start};
        iterationData = object;
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        return in;

    }

    @Override
    public void calculateFractalOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel_orbit)));
        complex[1] = new Complex(pixel_orbit);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        Complex temp = null;

        for (; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public void calculateJuliaOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pixel_orbit);//z
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        Complex temp = null;

        for (; iterations < max_iterations; iterations++) {
            function(complex);
            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    @Override
    public Complex iterateFractalDomain(Complex pixel) {
        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[6];
        complex[0] = tempz;
        complex[1] = new Complex(pixel);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        for (; iterations < max_iterations; iterations++) {

            function(complex);

        }

        return complex[0];

    }

    @Override
    public Complex iterateJuliaDomain(Complex pixel) {
        int iterations = 0;

        Complex[] complex = new Complex[6];
        complex[0] = new Complex(pixel);
        complex[1] = new Complex(seed);//c
        complex[2] = new Complex();
        complex[3] = new Complex(-1, 0);
        complex[4] = new Complex(1e-10, 0);
        complex[5] = complex[4].pow(z_exponent).sub_mutable(1);

        for (; iterations < max_iterations; iterations++) {

            function(complex);

        }

        return complex[0];

    }

}
