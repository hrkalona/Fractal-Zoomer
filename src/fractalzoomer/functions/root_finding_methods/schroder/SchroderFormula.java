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
package fractalzoomer.functions.root_finding_methods.schroder;

import fractalzoomer.core.Complex;
import fractalzoomer.core.Derivative;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public class SchroderFormula extends SchroderRootFindingMethod {

    private ExpressionNode expr;
    private Parser parser;
    private ExpressionNode expr2;
    private Parser parser2;
    private ExpressionNode expr3;
    private Parser parser3;
    private Complex point;

    public SchroderFormula(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        switch (out_coloring_algorithm) {

            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-7;
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);

        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);

        if (sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

    }

    //orbit
    public SchroderFormula(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, String user_fz_formula, String user_dfz_formula, String user_ddfz_formula) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        parser = new Parser();
        expr = parser.parse(user_fz_formula);

        parser2 = new Parser();
        expr2 = parser2.parse(user_dfz_formula);

        parser3 = new Parser();
        expr3 = parser3.parse(user_ddfz_formula);

        point = new Complex(plane_transform_center[0], plane_transform_center[1]);
    }

    @Override
    public void function(Complex[] complex) {

        if (parser.foundZ()) {
            parser.setZvalue(complex[0]);
        }

        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }

        Complex fz = expr.getValue();

        Complex dfz;
        Complex ddfz;

        if(Derivative.DERIVATIVE_METHOD == Derivative.DISABLED) {
            if (parser2.foundZ()) {
                parser2.setZvalue(complex[0]);
            }

            if (parser2.foundN()) {
                parser2.setNvalue(new Complex(iterations, 0));
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2.foundVar(i)) {
                    parser2.setVarsvalue(i, globalVars[i]);
                }
            }

            dfz = expr2.getValue();

            if (parser3.foundZ()) {
                parser3.setZvalue(complex[0]);
            }

            if (parser3.foundN()) {
                parser3.setNvalue(new Complex(iterations, 0));
            }

            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser3.foundVar(i)) {
                    parser3.setVarsvalue(i, globalVars[i]);
                }
            }

            ddfz = expr3.getValue();
        }
        else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_CENTRAL) {
            if (parser.foundZ()) {
                parser.setZvalue(complex[0].plus(Derivative.DZ));
            }

            Complex fzdz = expr.getValue();

            if (parser.foundZ()) {
                parser.setZvalue(complex[0].sub(Derivative.DZ));
            }

            Complex fzmdz = expr.getValue();

            dfz = Derivative.numericalCentralDerivativeFirstOrder(fzdz, fzmdz);

            ddfz = Derivative.numericalCentralDerivativeSecondOrder(fz, fzdz, fzmdz);
        } else if (Derivative.DERIVATIVE_METHOD == Derivative.NUMERICAL_FORWARD) {
            if (parser.foundZ()) {
                parser.setZvalue(complex[0].plus(Derivative.DZ));
            }

            Complex fzdz = expr.getValue();

            dfz = Derivative.numericalForwardDerivativeFirstOrder(fz, fzdz);

            if (parser.foundZ()) {
                parser.setZvalue(complex[0].plus(Derivative.DZ_2));
            }

            Complex fz2dz = expr.getValue();

            ddfz = Derivative.numericalForwardDerivativeSecondOrder(fz, fzdz, fz2dz);
        } else {
            if (parser.foundZ()) {
                parser.setZvalue(complex[0].sub(Derivative.DZ));
            }

            Complex fzmdz = expr.getValue();
            
            dfz = Derivative.numericalBackwardDerivativeFirstOrder(fz, fzmdz);
            
            if (parser.foundZ()) {
                parser.setZvalue(complex[0].sub(Derivative.DZ_2));
            }
            
            Complex fzm2dz = expr.getValue();
            
            ddfz = Derivative.numericalBackwardDerivativeSecondOrder(fz, fzmdz, fzm2dz);
        }

        schroderMethod(complex[0], fz, dfz, ddfz);

        setVariables(zold, zold2);
    }

    @Override
    public Complex[] initialize(Complex pixel) {

        Complex[] complex = super.initialize(pixel);

        setInitVariables(start, zold, zold2, pixel);

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

        if (parser.foundPP()) {
            parser.setPPvalue(zold2);
        }

        if (parser2.foundPP()) {
            parser2.setPPvalue(zold2);
        }

        if (parser3.foundPP()) {
            parser3.setPPvalue(zold2);
        }

    }

    private void setInitVariables(Complex start, Complex zold, Complex zold2, Complex pixel) {

        if (parser.foundPixel()) {
            parser.setPixelvalue(pixel);
        }

        if (parser2.foundPixel()) {
            parser2.setPixelvalue(pixel);
        }

        if (parser3.foundPixel()) {
            parser3.setPixelvalue(pixel);
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

        if (parser.foundP()) {
            parser.setPvalue(zold);
        }

        if (parser2.foundP()) {
            parser2.setPvalue(zold);
        }

        if (parser3.foundP()) {
            parser3.setPvalue(zold);
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

        if (parser.foundPoint()) {
            parser.setPointvalue(point);
        }

        if (parser2.foundPoint()) {
            parser2.setPointvalue(point);
        }

        if (parser3.foundPoint()) {
            parser3.setPointvalue(point);
        }
    }

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {

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

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }

        return expr.getValue();
    }

}
