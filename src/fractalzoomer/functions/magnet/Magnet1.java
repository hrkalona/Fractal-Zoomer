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
package fractalzoomer.functions.magnet;

import fractalzoomer.core.*;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class Magnet1 extends MagnetType {

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1E-12;

        power = 4;

        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation();
        }

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new InitialValue(0, 0);
        }

        switch (out_coloring_algorithm) {

            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;

        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        convergent_bailout = 1E-12;

        power = 4;

        switch (out_coloring_algorithm) {
        
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                convergent_bailout = 1E-2;
                break;
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    //orbit
    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 4;

        if(perturbation) {
            if(variable_perturbation) {
                if(user_perturbation_algorithm == 0) {
                    pertur_val = new VariablePerturbation(perturbation_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    pertur_val = new VariableConditionalPerturbation(user_perturbation_conditions, user_perturbation_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                pertur_val = new Perturbation(perturbation_vals[0], perturbation_vals[1]);
            }
        }
        else {
            pertur_val = new DefaultPerturbation();
        }

        if(init_value) {
            if(variable_init_value) {
                if(user_initial_value_algorithm == 0) {
                    init_val = new VariableInitialValue(initial_value_user_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                else {
                    init_val = new VariableConditionalInitialValue(user_initial_value_conditions, user_initial_value_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
            }
            else {
                init_val = new InitialValue(initial_vals[0], initial_vals[1]);
            }
        }
        else {
            init_val = new InitialValue(0, 0);
        }

    }

    public Magnet1(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);
        power = 4;
        pertur_val = new DefaultPerturbation();
        init_val = new InitialValue(0, 0);
    }

    @Override
    public void function(Complex[] complex) {

        complex[0] = (complex[0].square().plus_mutable(complex[1].sub(1))).divide_mutable(complex[0].times(2).plus_mutable(complex[1].sub(2))).square_mutable();

    }

    @Override
    public boolean supportsPerturbationTheory() {
        return true;
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
        BigComplex start = z;
        BigComplex c0 = pixel;

        Location loc = new Location();

        refPoint = pixel;
        refPointSmall = refPoint.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;
        RefType = getRefType();
        FullRef = fullReference;

        Apfloat convergentB = new MyApfloat(convergent_bailout);
        BigComplex one = new BigComplex(MyApfloat.ONE, MyApfloat.ZERO);


        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
            }

            if (!fullReference && iterations > 0 && (z.distance_squared(one).compareTo(convergentB) <= 0 || bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, Apfloat.ZERO))) {
                break;
            }

            zold2 = zold;
            zold = z;

            z = (z.square().plus(c.sub(MyApfloat.ONE))).divide(z.times(MyApfloat.TWO).plus(c.sub(MyApfloat.TWO))).square();

        }

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        skippedIterations = 0;
    }


    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex Z = Reference[RefIteration];
        Complex z = DeltaSubN;

        Complex C = refPointSmall;
        Complex c = DeltaSub0;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        Complex Csqr = C.square();
        Complex Ccube = C.cube();
        Complex Zsqr = Z.square();
        Complex Zcube = Z.cube();
        Complex Zfourth = Z.fourth();
        Complex csqr = c.square();
        Complex zsqr = z.square();


        Complex C4 = C.times(4);

        Complex Csqr6 = Csqr.times(6);


        Complex Cm2 = C.sub(2); // C - 2
        Complex Cm24 = Cm2.times(4);
        Complex Cm24Z = Cm24.times(Z);

        Complex temp11 = Csqr.sub(C4);
        Complex temp12 = Ccube.sub(Csqr6);
        Complex temp1 = temp11.plus(4); //C^2 - 4*C + 4
        Complex temp1Z = temp1.times(Z);
        Complex temp1Zsqr = temp1.times(Zsqr);
        Complex temp2 = temp1.plus(Cm24Z).plus_mutable(Zsqr.times(4)); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp3 = temp12.plus(Cm2.times(12).times_mutable(Zsqr)).plus_mutable(Zcube.times(8)).plus_mutable(temp1Z.times(6));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        Complex C12 = C.times(12);
        Complex temp4 = C12.sub(8); //12*C - 8
        Complex temp8 = Cm2.times(Zcube); //(C - 2)*Z^3
        Complex temp9 = temp2.times(c);
        Complex temp10 = temp3.plus(temp4);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        Complex denom = C.fourth()
                .plus_mutable(temp8.times(32))
                .plus_mutable(Zfourth.times(16))
                .sub_mutable(Ccube.times(8))
                .plus_mutable(temp1Zsqr.times(24))
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times_mutable(4))
                .plus_mutable(Csqr.times(24))
                .plus_mutable((temp12.plus(temp4)).times(Z).times_mutable(8))
                .plus_mutable(temp10.times(c).times_mutable(2))
                .plus_mutable((temp10.plus(temp9)).times(z).times_mutable(4))
                .sub_mutable(C.times(32))
                .plus_mutable(16);


        Complex temp5 = C.sub(3).times(2).times_mutable(Zsqr); // 2*(C - 3)*Z^2
        Complex temp6 = Csqr.sub(C.times(3)).plus_mutable(2); // C^2 - 3*C + 2
        Complex temp7 = C12.sub(6); //12*C - 6


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        Complex Zfifth2 = Z.fifth().times_mutable(2);
        Complex Zcube4 = Zcube.times(4);
        Complex C2 = C.times(2);

        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (Cm24.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times_mutable(4);

        Complex c1 = (Zfourth.plus(temp5).sub_mutable(Cm24Z).plus_mutable(C2).sub_mutable(3)).times_mutable(csqr);

        Complex d1 = (temp8.times(12).plus_mutable(Zfourth.times(10)).plus_mutable(Ccube).plus_mutable(temp1Zsqr.times(3))
                .sub_mutable(Csqr.times(7)).plus_mutable(temp6.times(4).times_mutable(Z)).plus_mutable(temp9).plus_mutable(temp7)).times_mutable(2).times_mutable(zsqr);

        Complex e1 = (C.sub(6).times_mutable(Zfourth).plus_mutable(Zfifth2).plus_mutable((Csqr.sub(C.times(6)).plus_mutable(4)).times_mutable(Zsqr)).plus_mutable(Zcube4)
                .sub_mutable((temp11.plus(3)).times_mutable(2).times_mutable(Z)).plus_mutable(temp6)).times_mutable(2).times_mutable(c);

        Complex f1 = (Cm2.times(3).times_mutable(Zfourth).plus_mutable(Zfifth2).plus_mutable(temp1.times(Zcube)).sub_mutable(Ccube).plus_mutable(temp6.times(2).times_mutable(Zsqr))
                .plus_mutable(Csqr.times(4)).plus_mutable((Ccube.sub(Csqr.times(7)).plus_mutable(temp7)).times_mutable(Z)).sub_mutable((Zfourth.sub(temp5).sub_mutable(Zcube4).plus_mutable(Csqr).sub_mutable(temp1Z).sub_mutable(C2).plus_mutable(1)).times_mutable(c))
                .sub_mutable(C.times(5)).plus_mutable(2)).times_mutable(4).times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .sub_mutable(c1)
                .plus_mutable(d1)
                .sub_mutable(e1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex Z = ReferenceDeep[RefIteration];
        MantExpComplex z = DeltaSubN;

        MantExpComplex C = refPointSmallDeep;
        MantExpComplex c = DeltaSub0;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        MantExpComplex Csqr = C.square();
        MantExpComplex Ccube = C.cube();
        MantExpComplex Zsqr = Z.square();
        MantExpComplex Zcube = Z.cube();
        MantExpComplex Zfourth = Z.fourth();
        MantExpComplex csqr = c.square();
        MantExpComplex zsqr = z.square();


        MantExpComplex C4 = C.times4();

        MantExpComplex Csqr6 = Csqr.times(MantExp.SIX);


        MantExpComplex Cm2 = C.sub(MantExp.TWO); // C - 2
        MantExpComplex Cm24 = Cm2.times4();
        MantExpComplex Cm24Z = Cm24.times(Z);

        MantExpComplex temp11 = Csqr.sub(C4);
        MantExpComplex temp12 = Ccube.sub(Csqr6);
        MantExpComplex temp1 = temp11.plus(MantExp.FOUR); //C^2 - 4*C + 4
        MantExpComplex temp1Z = temp1.times(Z);
        MantExpComplex temp1Zsqr = temp1.times(Zsqr);
        MantExpComplex temp2 = temp1.plus(Cm24Z).plus_mutable(Zsqr.times4()); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        MantExpComplex temp3 = temp12.plus(Cm2.times(MantExp.TWELVE).times_mutable(Zsqr)).plus_mutable(Zcube.times8()).plus_mutable(temp1Z.times(MantExp.SIX));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        MantExpComplex C12 = C.times(MantExp.TWELVE);
        MantExpComplex temp4 = C12.sub(MantExp.EIGHT); //12*C - 8
        MantExpComplex temp8 = Cm2.times(Zcube); //(C - 2)*Z^3
        MantExpComplex temp9 = temp2.times(c);
        MantExpComplex temp10 = temp3.plus(temp4);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        MantExpComplex denom = C.fourth()
                .plus_mutable(temp8.times32())
                .plus_mutable(Zfourth.times16())
                .sub_mutable(Ccube.times8())
                .plus_mutable(temp1Zsqr.times(MantExp.TWENTYFOUR))
                .plus_mutable(temp2.times(csqr))
                .plus_mutable(temp2.times(zsqr).times4_mutable())
                .plus_mutable(Csqr.times(MantExp.TWENTYFOUR))
                .plus_mutable((temp12.plus(temp4)).times(Z).times8_mutable())
                .plus_mutable(temp10.times(c).times2_mutable())
                .plus_mutable((temp10.plus(temp9)).times(z).times4_mutable())
                .sub_mutable(C.times32())
                .plus_mutable(MantExp.SIXTEEN);


        MantExpComplex temp5 = C.sub(MantExp.THREE).times2().times_mutable(Zsqr); // 2*(C - 3)*Z^2
        MantExpComplex temp6 = Csqr.sub(C.times(MantExp.THREE)).plus_mutable(MantExp.TWO); // C^2 - 3*C + 2
        MantExpComplex temp7 = C12.sub(MantExp.SIX); //12*C - 6


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        MantExpComplex Zfifth2 = Z.fifth().times2_mutable();
        MantExpComplex Zcube4 = Zcube.times4();
        MantExpComplex C2 = C.times2();

        MantExpComplex a1 = temp2.times(z.fourth());

        MantExpComplex b1 = (Cm24.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times4_mutable();

        MantExpComplex c1 = (Zfourth.plus(temp5).sub_mutable(Cm24Z).plus_mutable(C2).sub_mutable(MantExp.THREE)).times_mutable(csqr);

        MantExpComplex d1 = (temp8.times(MantExp.TWELVE).plus_mutable(Zfourth.times(MantExp.TEN)).plus_mutable(Ccube).plus_mutable(temp1Zsqr.times(MantExp.THREE))
                .sub_mutable(Csqr.times(MantExp.SEVEN)).plus_mutable(temp6.times4().times_mutable(Z)).plus_mutable(temp9).plus_mutable(temp7)).times2_mutable().times_mutable(zsqr);

        MantExpComplex e1 = (C.sub(MantExp.SIX).times_mutable(Zfourth).plus_mutable(Zfifth2).plus_mutable((Csqr.sub(C.times(MantExp.SIX)).plus_mutable(MantExp.FOUR)).times_mutable(Zsqr)).plus_mutable(Zcube4)
                .sub_mutable((temp11.plus(MantExp.THREE)).times2_mutable().times_mutable(Z)).plus_mutable(temp6)).times2_mutable().times_mutable(c);

        MantExpComplex f1 = (Cm2.times(MantExp.THREE).times_mutable(Zfourth).plus_mutable(Zfifth2).plus_mutable(temp1.times(Zcube)).sub_mutable(Ccube).plus_mutable(temp6.times2().times_mutable(Zsqr))
                .plus_mutable(Csqr.times4()).plus_mutable((Ccube.sub(Csqr.times(MantExp.SEVEN)).plus_mutable(temp7)).times_mutable(Z)).sub_mutable((Zfourth.sub(temp5).sub_mutable(Zcube4).plus_mutable(Csqr).sub_mutable(temp1Z).sub_mutable(C2).plus_mutable(MantExp.ONE)).times_mutable(c))
                .sub_mutable(C.times(MantExp.FIVE)).plus_mutable(MantExp.TWO)).times4_mutable().times_mutable(z);

        MantExpComplex num = a1
                .plus_mutable(b1)
                .sub_mutable(c1)
                .plus_mutable(d1)
                .sub_mutable(e1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex Z = Reference[RefIteration];
        Complex z = DeltaSubN;

        Complex C = refPointSmall;

        //((C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4 + 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3 - (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2 + 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2 - 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c + 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z)
        //        /(C^4 + 32*(C - 2)*Z^3 + 16*Z^4 - 8*C^3 + 24*(C^2 - 4*C + 4)*Z^2 + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2 + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2 + 24*C^2 + 8*(C^3 - 6*C^2 + 12*C - 8)*Z + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z - 32*C + 16)


        Complex Csqr = C.square();
        Complex Ccube = C.cube();
        Complex Zsqr = Z.square();
        Complex Zcube = Z.cube();
        Complex Zfourth = Z.fourth();
        Complex zsqr = z.square();


        Complex C4 = C.times(4);

        Complex Csqr6 = Csqr.times(6);


        Complex Cm2 = C.sub(2); // C - 2
        Complex Cm24 = Cm2.times(4);
        Complex Cm24Z = Cm24.times(Z);

        Complex temp11 = Csqr.sub(C4);
        Complex temp12 = Ccube.sub(Csqr6);
        Complex temp1 = temp11.plus(4); //C^2 - 4*C + 4
        Complex temp1Z = temp1.times(Z);
        Complex temp1Zsqr = temp1.times(Zsqr);
        Complex temp2 = temp1.plus(Cm24Z).plus_mutable(Zsqr.times(4)); //C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4
        Complex temp3 = temp12.plus(Cm2.times(12).times_mutable(Zsqr)).plus_mutable(Zcube.times(8)).plus_mutable(temp1Z.times(6));//C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z
        Complex C12 = C.times(12);
        Complex temp4 = C12.sub(8); //12*C - 8
        Complex temp8 = Cm2.times(Zcube); //(C - 2)*Z^3
        Complex temp10 = temp3.plus(temp4);

        //        C^4
        //        + 32*(C - 2)*Z^3
        //        + 16*Z^4
        //        - 8*C^3
        //        + 24*(C^2 - 4*C + 4)*Z^2
        //        + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c^2
        //        + 4*(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^2
        //        + 24*C^2
        //        + 8*(C^3 - 6*C^2 + 12*C - 8)*Z
        //        + 2*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + 12*C - 8)*c
        //        + 4*(C^3 + 12*(C - 2)*Z^2 + 8*Z^3 - 6*C^2 + 6*(C^2 - 4*C + 4)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 8)*z
        //        - 32*C
        //        + 16
        Complex denom = C.fourth()
                .plus_mutable(temp8.times(32))
                .plus_mutable(Zfourth.times(16))
                .sub_mutable(Ccube.times(8))
                .plus_mutable(temp1Zsqr.times(24))
                .plus_mutable(temp2.times(zsqr).times_mutable(4))
                .plus_mutable(Csqr.times(24))
                .plus_mutable((temp12.plus(temp4)).times(Z).times_mutable(8))
                .plus_mutable(temp10.times(z).times_mutable(4))
                .sub_mutable(C.times(32))
                .plus_mutable(16);

        Complex temp6 = Csqr.sub(C.times(3)).plus_mutable(2); // C^2 - 3*C + 2
        Complex temp7 = C12.sub(6); //12*C - 6


        //(C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*z^4
        //+ 4*(4*(C - 2)*Z^2 + 4*Z^3 + (C^2 - 4*C + 4)*Z)*z^3
        //- (Z^4 + 2*(C - 3)*Z^2 - 4*(C - 2)*Z + 2*C - 3)*c^2
        //+ 2*(12*(C - 2)*Z^3 + 10*Z^4 + C^3 + 3*(C^2 - 4*C + 4)*Z^2 - 7*C^2 + 4*(C^2 - 3*C + 2)*Z + (C^2 + 4*(C - 2)*Z + 4*Z^2 - 4*C + 4)*c + 12*C - 6)*z^2
        //- 2*((C - 6)*Z^4 + 2*Z^5 + (C^2 - 6*C + 4)*Z^2 + 4*Z^3 + C^2 - 2*(C^2 - 4*C + 3)*Z - 3*C + 2)*c
        //+ 4*(3*(C - 2)*Z^4 + 2*Z^5 + (C^2 - 4*C + 4)*Z^3 - C^3 + 2*(C^2 - 3*C + 2)*Z^2 + 4*C^2 + (C^3 - 7*C^2 + 12*C - 6)*Z - (Z^4 - 2*(C - 3)*Z^2 - 4*Z^3 + C^2 - (C^2 - 4*C + 4)*Z - 2*C + 1)*c - 5*C + 2)*z

        Complex Zfifth2 = Z.fifth().times_mutable(2);
        Complex Zcube4 = Zcube.times(4);

        Complex a1 = temp2.times(z.fourth());

        Complex b1 = (Cm24.times(Zsqr).plus_mutable(Zcube4).plus_mutable(temp1Z)).times(z.cube()).times_mutable(4);

        Complex d1 = (temp8.times(12).plus_mutable(Zfourth.times(10)).plus_mutable(Ccube).plus_mutable(temp1Zsqr.times(3))
                .sub_mutable(Csqr.times(7)).plus_mutable(temp6.times(4).times_mutable(Z)).plus_mutable(temp7)).times_mutable(2).times_mutable(zsqr);


        Complex f1 = (Cm2.times(3).times_mutable(Zfourth).plus_mutable(Zfifth2).plus_mutable(temp1.times(Zcube)).sub_mutable(Ccube).plus_mutable(temp6.times(2).times_mutable(Zsqr))
                .plus_mutable(Csqr.times(4)).plus_mutable((Ccube.sub(Csqr.times(7)).plus_mutable(temp7)).times_mutable(Z))
                .sub_mutable(C.times(5)).plus_mutable(2)).times_mutable(4).times_mutable(z);

        Complex num = a1
                .plus_mutable(b1)
                .plus_mutable(d1)
                .plus_mutable(f1);

        return num.divide_mutable(denom);
    }

}
