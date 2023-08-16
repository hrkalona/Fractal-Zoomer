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

package fractalzoomer.functions.root_finding_methods.householder;

import fractalzoomer.core.Complex;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class HouseholderPoly extends HouseholderRootFindingMethod {

    private double[] coefficients;
    private Complex[] complex_coefficients;
    private boolean usesComplexCoefficients;

    public HouseholderPoly(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, double[] coefficients, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double[] coefficients_im) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

        usesComplexCoefficients = false;
        for(int i = 0; i < coefficients_im.length; i++) {
            if(coefficients_im[i] != 0) {
                usesComplexCoefficients = true;
                break;
            }
        }
        
        if(usesComplexCoefficients) {
            complex_coefficients = new Complex[coefficients.length];
            for(int i = 0; i < complex_coefficients.length; i++) {
                complex_coefficients[i] = new Complex(coefficients[i], coefficients_im[i]);
            }
        }
        else {
            this.coefficients = coefficients;
        }

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-4;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-4;
                break;            
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                convergent_bailout = 1E-4;
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
       
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    //orbit
    public HouseholderPoly(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, double[] coefficients, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double[] coefficients_im) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        usesComplexCoefficients = false;
        for(int i = 0; i < coefficients_im.length; i++) {
            if(coefficients_im[i] != 0) {
                usesComplexCoefficients = true;
                break;
            }
        }
        
        if(usesComplexCoefficients) {
            complex_coefficients = new Complex[coefficients.length];
            for(int i = 0; i < complex_coefficients.length; i++) {
                complex_coefficients[i] = new Complex(coefficients[i], coefficients_im[i]);
            }
        }
        else {
            this.coefficients = coefficients;
        }

    }

    @Override
    public void function(Complex[] complex) {

        Complex fz, dfz, ddfz;
        
        if(usesComplexCoefficients) {
            fz = complex[0].tenth().times_mutable(complex_coefficients[0]).plus_mutable(complex[0].ninth().times_mutable(complex_coefficients[1])).plus_mutable(complex[0].eighth().times_mutable(complex_coefficients[2])).plus_mutable(complex[0].seventh().times_mutable(complex_coefficients[3])).plus_mutable(complex[0].sixth().times_mutable(complex_coefficients[4])).plus_mutable(complex[0].fifth().times_mutable(complex_coefficients[5])).plus_mutable(complex[0].fourth().times_mutable(complex_coefficients[6])).plus_mutable(complex[0].cube().times_mutable(complex_coefficients[7])).plus_mutable(complex[0].square().times_mutable(complex_coefficients[8])).plus_mutable(complex[0].times(complex_coefficients[9])).plus_mutable(complex_coefficients[10]);
            dfz = complex[0].ninth().times_mutable(complex_coefficients[0].times(10)).plus_mutable(complex[0].eighth().times_mutable(complex_coefficients[1].times(9))).plus_mutable(complex[0].seventh().times_mutable(complex_coefficients[2].times(8))).plus_mutable(complex[0].sixth().times_mutable(complex_coefficients[3].times(7))).plus_mutable(complex[0].fifth().times_mutable(complex_coefficients[4].times(6))).plus_mutable(complex[0].fourth().times_mutable(complex_coefficients[5].times(5))).plus_mutable(complex[0].cube().times_mutable(complex_coefficients[6].times4())).plus_mutable(complex[0].square().times_mutable(complex_coefficients[7].times(3))).plus_mutable(complex[0].times(complex_coefficients[8].times2())).plus_mutable(complex_coefficients[9]);
            ddfz = complex[0].eighth().times_mutable(complex_coefficients[0].times(90)).plus_mutable(complex[0].seventh().times_mutable(complex_coefficients[1].times(72))).plus_mutable(complex[0].sixth().times_mutable(complex_coefficients[2].times(56))).plus_mutable(complex[0].fifth().times_mutable(complex_coefficients[3].times(42))).plus_mutable(complex[0].fourth().times_mutable(complex_coefficients[4].times(30))).plus_mutable(complex[0].cube().times_mutable(complex_coefficients[5].times(20))).plus_mutable(complex[0].square().times_mutable(complex_coefficients[6].times(12))).plus_mutable(complex[0].times(complex_coefficients[7].times(6))).plus_mutable(complex_coefficients[8].times2());
        }
        else {
            fz = complex[0].tenth().times_mutable(coefficients[0]).plus_mutable(complex[0].ninth().times_mutable(coefficients[1])).plus_mutable(complex[0].eighth().times_mutable(coefficients[2])).plus_mutable(complex[0].seventh().times_mutable(coefficients[3])).plus_mutable(complex[0].sixth().times_mutable(coefficients[4])).plus_mutable(complex[0].fifth().times_mutable(coefficients[5])).plus_mutable(complex[0].fourth().times_mutable(coefficients[6])).plus_mutable(complex[0].cube().times_mutable(coefficients[7])).plus_mutable(complex[0].square().times_mutable(coefficients[8])).plus_mutable(complex[0].times(coefficients[9])).plus_mutable(coefficients[10]);
            dfz = complex[0].ninth().times_mutable(10 * coefficients[0]).plus_mutable(complex[0].eighth().times_mutable(9 * coefficients[1])).plus_mutable(complex[0].seventh().times_mutable(8 * coefficients[2])).plus_mutable(complex[0].sixth().times_mutable(7 * coefficients[3])).plus_mutable(complex[0].fifth().times_mutable(6 * coefficients[4])).plus_mutable(complex[0].fourth().times_mutable(5 * coefficients[5])).plus_mutable(complex[0].cube().times_mutable(4 * coefficients[6])).plus_mutable(complex[0].square().times_mutable(3 * coefficients[7])).plus_mutable(complex[0].times(2 * coefficients[8])).plus_mutable(coefficients[9]);
            ddfz = complex[0].eighth().times_mutable(90 * coefficients[0]).plus_mutable(complex[0].seventh().times_mutable(72 * coefficients[1])).plus_mutable(complex[0].sixth().times_mutable(56 * coefficients[2])).plus_mutable(complex[0].fifth().times_mutable(42 * coefficients[3])).plus_mutable(complex[0].fourth().times_mutable(30 * coefficients[4])).plus_mutable(complex[0].cube().times_mutable(20 * coefficients[5])).plus_mutable(complex[0].square().times_mutable(12 * coefficients[6])).plus_mutable(complex[0].times(6 * coefficients[7])).plus_mutable(2 * coefficients[8]);
        }

        householderMethod(complex[0], fz, dfz, ddfz);

    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        if(usesComplexCoefficients) {
            return z.tenth().times_mutable(complex_coefficients[0]).plus_mutable(z.ninth().times_mutable(complex_coefficients[1])).plus_mutable(z.eighth().times_mutable(complex_coefficients[2])).plus_mutable(z.seventh().times_mutable(complex_coefficients[3])).plus_mutable(z.sixth().times_mutable(complex_coefficients[4])).plus_mutable(z.fifth().times_mutable(complex_coefficients[5])).plus_mutable(z.fourth().times_mutable(complex_coefficients[6])).plus_mutable(z.cube().times_mutable(complex_coefficients[7])).plus_mutable(z.square().times_mutable(complex_coefficients[8])).plus_mutable(z.times(complex_coefficients[9])).plus_mutable(complex_coefficients[10]);
        }
        else {
            return z.tenth().times_mutable(coefficients[0]).plus_mutable(z.ninth().times_mutable(coefficients[1])).plus_mutable(z.eighth().times_mutable(coefficients[2])).plus_mutable(z.seventh().times_mutable(coefficients[3])).plus_mutable(z.sixth().times_mutable(coefficients[4])).plus_mutable(z.fifth().times_mutable(coefficients[5])).plus_mutable(z.fourth().times_mutable(coefficients[6])).plus_mutable(z.cube().times_mutable(coefficients[7])).plus_mutable(z.square().times_mutable(coefficients[8])).plus_mutable(z.times(coefficients[9])).plus_mutable(coefficients[10]);
        }
    }

}
