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
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class Nova extends ExtendedConvergentType {

    protected Complex z_exponent;
    protected Complex relaxation;
    protected int nova_method;

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1E-10;

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-10;

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

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

    }

    //orbit
    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

        if (perturbation) {
            if (variable_perturbation) {
                if (user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                } else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
                }
            } else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        } else {
            init_val = new DefaultInitialValue();
        }

    }

    public Nova(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, double[] z_exponent, double[] relaxation, int nova_method, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.nova_method = nova_method;

        this.z_exponent = new Complex(z_exponent[0], z_exponent[1]);

        this.relaxation = new Complex(relaxation[0], relaxation[1]);

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
                complex[0].sub_mutable(((fz).divide_mutable(dfz)).times_mutable(relaxation)).plus_mutable(complex[1]); //newton
                break;
            case MainWindow.NOVA_HALLEY:
                complex[0].sub_mutable(((fz.times(dfz).times_mutable(2)).divide_mutable((dfz.square_mutable().times_mutable(2)).sub_mutable(fz.times_mutable(ddfz)))).times_mutable(relaxation)).plus_mutable(complex[1]); //halley
                break;
            case MainWindow.NOVA_SCHRODER:
                complex[0].sub_mutable(((fz.times(dfz)).divide_mutable((dfz.square_mutable()).sub_mutable(fz.times_mutable(ddfz)))).times_mutable(relaxation)).plus_mutable(complex[1]);//schroeder
                break;
            case MainWindow.NOVA_HOUSEHOLDER:
                complex[0].sub_mutable(((fz.times_mutable(dfz.square().times_mutable(2).plus_mutable(fz.times(ddfz)))).divide_mutable(dfz.cube_mutable().times_mutable(2))).times_mutable(relaxation)).plus_mutable(complex[1]);//householder
                break;
            case MainWindow.NOVA_SECANT:
                Complex temp = new Complex(complex[0]);
                complex[0].sub_mutable((fz.times((complex[0].sub(complex[2])).divide_mutable(fz.sub(complex[3])))).times_mutable(relaxation)).plus_mutable(complex[1]); //secant
                complex[2].assign(temp);
                complex[3].assign(fz);
                break;
            case MainWindow.NOVA_STEFFENSEN:
                complex[0].sub_mutable(((fz.square()).divide_mutable(ffz.sub_mutable(fz))).times_mutable(relaxation)).plus_mutable(complex[1]); //steffensen
                break;
            case MainWindow.NOVA_MULLER:
                Complex fz1 = complex[5];
                Complex fz2 = complex[3];

                Complex hk = complex[0].sub(complex[4]);
                Complex hk1 = complex[4].sub(complex[2]);
                Complex rk = hk.divide(hk1);

                Complex rkp1 = rk.plus(1);
                Complex rksqr = rk.square();
                Complex ck = fz.times(rkp1);
                Complex bk = fz.times(rk.times(2).plus_mutable(1)).sub(fz1.times(rkp1.square())).plus(fz2.times(rksqr));
                Complex ak = fz.times(rk).sub(fz1.times(rkp1.times(rk))).plus(fz2.times(rksqr));

                Complex ck2 = ck.times(2);
                Complex temp2 = (bk.square().sub(ak.times(ck).times_mutable(4))).sqrt();

                Complex denom1 = bk.plus(temp2);
                Complex denom2 = bk.sub(temp2);

                Complex qk;
                if (denom1.norm_squared() > denom2.norm_squared()) {
                    qk = ck2.divide(denom1);
                } else {
                    qk = ck2.divide(denom2);
                }

                complex[2].assign(complex[4]);
                complex[4].assign(complex[0]);
                complex[0].sub_mutable(hk.times_mutable(qk).times_mutable(relaxation)).plus_mutable(complex[1]);
                complex[3].assign(complex[5]);
                complex[5].assign(fz);
                break;
            case MainWindow.NOVA_PARHALLEY:
                Complex sqrt = (dfz.square().sub_mutable(fz.times(ddfz).times_mutable(2))).sqrt_mutable();

                Complex denom11 = dfz.plus(sqrt);
                Complex denom21 = dfz.sub(sqrt);

                if (denom11.norm_squared() > denom21.norm_squared()) {
                    complex[0].sub_mutable(fz.times(2).divide_mutable(denom11).times_mutable(relaxation)).plus_mutable(complex[1]);
                } else {
                    complex[0].sub_mutable(fz.times(2).divide_mutable(denom21).times_mutable(relaxation)).plus_mutable(complex[1]);
                }
                break;
            case MainWindow.NOVA_LAGUERRE:
                Complex degree = z_exponent;
                Complex n1 = degree.sub(1);
                Complex sqrt2 = (n1.times(dfz).square_mutable().sub_mutable(degree.times(n1).times_mutable(ddfz).times_mutable(fz))).sqrt_mutable();

                Complex denom31 = dfz.plus(sqrt2);
                Complex denom32 = dfz.sub(sqrt2);

                if (denom31.norm_squared() > denom32.norm_squared()) {
                    complex[0].sub_mutable(degree.times(fz).divide_mutable(denom31).times_mutable(relaxation)).plus_mutable(complex[1]);
                } else {
                    complex[0].sub_mutable(degree.times(fz).divide_mutable(denom32).times_mutable(relaxation)).plus_mutable(complex[1]);
                }
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

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);

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

            if ((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);

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

            if ((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start, vars};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        return in_color_algorithm.getResult(object);

    }

    @Override
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
        int iterations = 0;
        double temp = 0;

        if (trap != null) {
            trap.initialize();
        }

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);

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

        double temp2;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if ((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start, vars};
                temp2 = out_color_algorithm.getResult(object);
                double[] array = {out_color_algorithm.transformResultToHeight(temp2, max_iterations), temp2};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        temp2 = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp2, max_iterations), temp2};
        return array;

    }

    @Override
    public double[] calculateJulia3DWithoutPeriodicity(Complex pixel) {
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

            if ((temp = complex[0].distance_squared(zold)) <= convergent_bailout) {
                Object[] object = {iterations, complex[0], temp, zold, zold2, complex[1], start, vars};
                double[] array = {out_color_algorithm.transformResultToHeight(out_color_algorithm.getResult3D(object), max_iterations), out_color_algorithm.getResult(object)};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, vars};
        double temp2 = in_color_algorithm.getResult(object);
        double[] array = {in_color_algorithm.transformResultToHeight(temp2, max_iterations), temp2};
        return array;

    }

    @Override
    public void calculateFractalOrbit() {
        int iterations = 0;

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);

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

        pertur_val.setGlobalVars(vars);
        init_val.setGlobalVars(vars);

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
