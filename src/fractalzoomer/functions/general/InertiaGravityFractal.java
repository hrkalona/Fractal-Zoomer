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
package fractalzoomer.functions.general;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.FractalWithoutConstant;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.InertiaGravityFractalSettings;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 *
 * @author hrkalona2
 */
public class InertiaGravityFractal extends FractalWithoutConstant {

    private Complex[] bodies;
    private Complex[] gravity;
    private Complex inertiaContrib;
    private Complex initialInertia;
    private int pull_scaling_function;
    private double inertia_exponent;
    private Complex timeStep;

    public InertiaGravityFractal(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, InertiaGravityFractalSettings igs) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, false, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        Predicate<double[]> isEnabled = (double[] gravity) -> gravity[0] != 0 || gravity[1] != 0;

        int count = 0;
        for (int i = 0; i < igs.bodyGravity.length; i++) {
            if (isEnabled.test(igs.bodyGravity[i])) {
                count++;
            }
        }

        bodies = new Complex[count];
        gravity = new Complex[count];

        count = 0;
        for (int i = 0; i < igs.bodyGravity.length; i++) {
            if (isEnabled.test(igs.bodyGravity[i])) {
                bodies[count] = new Complex(igs.bodyLocation[i][0], igs.bodyLocation[i][1]);
                gravity[count] = new Complex(igs.bodyGravity[i][0], igs.bodyGravity[i][1]);
                count++;
            }
        }

        inertiaContrib = new Complex(igs.inertia_contribution[0], igs.inertia_contribution[1]);
        initialInertia = new Complex(igs.initial_inertia[0], igs.initial_inertia[1]);
        timeStep = new Complex(igs.time_step[0], igs.time_step[1]);
        inertia_exponent = igs.inertia_exponent;
        pull_scaling_function = igs.pull_scaling_function;

    }

    //orbit
    public InertiaGravityFractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, InertiaGravityFractalSettings igs) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        Predicate<double[]> isEnabled = (double[] gravity) -> gravity[0] != 0 || gravity[1] != 0;

        int count = 0;
        for (int i = 0; i < igs.bodyGravity.length; i++) {
            if (isEnabled.test(igs.bodyGravity[i])) {
                count++;
            }
        }

        bodies = new Complex[count];
        gravity = new Complex[count];

        count = 0;
        for (int i = 0; i < igs.bodyGravity.length; i++) {
            if (isEnabled.test(igs.bodyGravity[i])) {
                bodies[count] = new Complex(igs.bodyLocation[i][0], igs.bodyLocation[i][1]);
                gravity[count] = new Complex(igs.bodyGravity[i][0], igs.bodyGravity[i][1]);
                count++;
            }
        }

        inertiaContrib = new Complex(igs.inertia_contribution[0], igs.inertia_contribution[1]);
        initialInertia = new Complex(igs.initial_inertia[0], igs.initial_inertia[1]);
        timeStep = new Complex(igs.time_step[0], igs.time_step[1]);
        inertia_exponent = igs.inertia_exponent;
        pull_scaling_function = igs.pull_scaling_function;

    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pixel);//z
        complex[1] = new Complex(new Complex(initialInertia));

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[0]);

        return complex;

    }

    @Override
    public void function(Complex[] complex) {

        Complex pull = new Complex();

        for (int i = 0; i < bodies.length; i++) {
            Complex d = bodies[i].sub(complex[0]);
            double f = d.norm();

            d.divide_mutable(f); // normalize

            switch (pull_scaling_function) {
                case MainWindow.PULL_EXP:
                    d.times_mutable(gravity[i]); // = f = f * g
                    f = Math.pow(f, inertia_exponent);
                    d.divide_mutable(f); // re-normalize
                    break;
                case MainWindow.PULL_LINEAR:
                    d.times_mutable(gravity[i].sub(f));
                    break;
                case MainWindow.PULL_ODDBALL:
                    d.times_mutable(gravity[i]);
                    break;
            }

            pull.plus_mutable(d);
        }

        pull.times_mutable(timeStep);

        complex[1].times_mutable(inertiaContrib).plus_mutable(pull);	// inertia = scalar * inertia + pull			

        complex[0].plus_mutable(complex[1].times(timeStep)); // z = z + inertia

    }

}
