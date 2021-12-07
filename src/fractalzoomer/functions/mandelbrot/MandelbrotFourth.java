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
package fractalzoomer.functions.mandelbrot;

import fractalzoomer.core.*;
import fractalzoomer.fractal_options.BurningShip;
import fractalzoomer.fractal_options.MandelGrass;
import fractalzoomer.fractal_options.MandelVariation;
import fractalzoomer.fractal_options.NormalMandel;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;
import static fractalzoomer.main.Constants.SA_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class MandelbrotFourth extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    public MandelbrotFourth(boolean burning_ship) {
        super();
        this.burning_ship = burning_ship;
    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;

        power = 4;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

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
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 4;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();
    }

    //orbit
    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.burning_ship = burning_ship;

        power = 4;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        setPertubationOption(perturbation, perturbation_vals, variable_perturbation, user_perturbation_algorithm, perturbation_user_formula, user_perturbation_conditions, user_perturbation_condition_formula, plane_transform_center);

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
            init_val = new DefaultInitialValue();
        }

    }

    public MandelbrotFourth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 4;

        if(burning_ship) {
            type = new BurningShip();
        }
        else {
            type = new NormalMandel();
        }

        if(mandel_grass) {
            type2 = new MandelGrass(mandel_grass_vals[0], mandel_grass_vals[1]);
        }
        else {
            type2 = new NormalMandel();
        }

        pertur_val = new DefaultPerturbation();
        init_val = new DefaultInitialValue();

    }

    @Override
    public void function(Complex[] complex) {

        type.getValue(complex[0]);
        complex[0].fourth_mutable().plus_mutable(complex[1]);
        type2.getValue(complex[0]);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        if(isJuliaMap) {
            return false;
        }
        return !isJulia || (isJulia && !juliter);
    }

    @Override
    public void calculateReferencePoint(BigComplex pixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation, JProgressBar progress) {

        int initIterations = iterations;

        if(progress != null) {
            progress.setMaximum(max_iterations - initIterations);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_ref_color);
            progress.setString(REFERENCE_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }

        if (iterations == 0) {
            Reference = new Complex[max_iterations];

            if(isJulia) {
                ReferenceSubCp = new Complex[max_iterations];
            }

            if (deepZoom) {
                ReferenceDeep = new MantExpComplex[max_iterations];

                if(isJulia) {
                    ReferenceSubCpDeep = new MantExpComplex[max_iterations];
                }

            }
        } else if (max_iterations > Reference.length) {
            Reference = copyReference(Reference, new Complex[max_iterations]);

            if(isJulia) {
                ReferenceSubCp = copyReference(ReferenceSubCp, new Complex[max_iterations]);
            }

            if (deepZoom) {
                ReferenceDeep = copyDeepReference(ReferenceDeep, new MantExpComplex[max_iterations]);

                if(isJulia) {
                    ReferenceSubCpDeep = copyDeepReference(ReferenceSubCpDeep, new MantExpComplex[max_iterations]);
                }

            }
        }

        BigComplex z = iterations == 0 ? (isJulia ? pixel : new BigComplex()) : lastZValue;

        BigComplex c = isJulia ? new BigComplex(seed) : pixel;
        BigComplex zold = iterations == 0 ? new BigComplex() : secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : thirdTolastZValue;
        BigComplex start = isJulia ? pixel : new BigComplex();
        BigComplex c0 = c;

        refPoint = pixel;

        Location loc = new Location();

        refPointSmall = pixel.toComplex();

        if(deepZoom) {
            refPointSmallDeep = loc.getMantExpComplex(refPoint);
        }

        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;
        boolean isSeriesInUse = ThreadDraw.SERIES_APPROXIMATION && supportsSeriesApproximation();
        RefType = getRefType();
        FullRef = fullReference;

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            if(isJulia) {
                BigComplex zsubcp = z.sub(refPoint);
                ReferenceSubCp[iterations] = zsubcp.toComplex();

                if(deepZoom) {
                    ReferenceSubCpDeep[iterations] = loc.getMantExpComplex(zsubcp);
                }
            }

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }

            if (!fullReference && iterations > 0 && bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, Apfloat.ZERO, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {

                if (burning_ship) {
                    z = z.abs().fourth().plus(c);
                } else {
                    z = z.fourth().plus(c);
                }

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

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(REFERENCE_CALCULATION_STR + " 100%");
        }

        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeriesWrapper(size, deepZoom, externalLocation, progress);
        }

    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, Complex DeltaSub0, int RefIteration) {

        Complex X = Reference[RefIteration];

        if(burning_ship) {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab = a * b;
            double ri = r * i;
            double rb = r * b;
            double ai = a * i;

            double Dnr = 4 * r2*ar + 6 * r2*a2 + 4 * ar*a2 + a2 * a2 + 4 * i2*ib + 6 * i2*b2 + 4 * ib*b2 + b2 * b2 - 12 * r2*ib - 6 * r2*b2 - 12 * ar*i2 - 24 * ar*ib - 12 * ar*b2 - 6 * a2*i2 - 12 * a2*ib - 6 * a2*b2;
            double Dni = Complex.DiffAbs(ri, rb + ai + ab);
            Dni = 4 * (r2 - i2)*(Dni)+4 * Math.abs(ri + rb + ai + ab)*(2 * ar + a2 - 2 * ib - b2);

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
        else
        {
            return DeltaSubN.fourth()
                    .plus_mutable(DeltaSubN.cube().times_mutable(4).times_mutable(X))
                    .plus_mutable(DeltaSubN.square().times_mutable(6).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.times(4).times_mutable(X.cube()))
                    .plus_mutable(DeltaSub0);
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, MantExpComplex DeltaSub0, int RefIteration) {

        MantExpComplex X = ReferenceDeep[RefIteration];

        if(burning_ship) {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab = a.multiply(b);
            MantExp ri = r.multiply(i);
            MantExp rb = r.multiply(b);
            MantExp ai = a.multiply(i);

            MantExp Dnr = r2.multiply4().multiply_mutable(ar)
                    .add_mutable(MantExp.SIX.multiply(r2).multiply_mutable(a2))
                    .add_mutable(ar.multiply4().multiply_mutable(a2))
                    .add_mutable(a2.multiply(a2))
                    .add_mutable(i2.multiply4().multiply_mutable(ib))
                    .add_mutable(MantExp.SIX.multiply(i2).multiply_mutable(b2))
                    .add_mutable(ib.multiply4().multiply_mutable(b2))
                    .add_mutable(b2.multiply(b2))
                    .subtract_mutable(MantExp.TWELVE.multiply(r2).multiply_mutable(ib))
                    .subtract_mutable(MantExp.SIX.multiply(r2).multiply_mutable(b2))
                    .subtract_mutable(MantExp.TWELVE.multiply(ar).multiply_mutable(i2))
                    .subtract_mutable(MantExp.TWENTYFOUR.multiply(ar).multiply_mutable(ib))
                    .subtract_mutable(MantExp.TWELVE.multiply(ar).multiply_mutable(b2))
                    .subtract_mutable(MantExp.SIX.multiply(a2).multiply_mutable(i2))
                    .subtract_mutable(MantExp.TWELVE.multiply(a2).multiply_mutable(ib))
                    .subtract_mutable(MantExp.SIX.multiply(a2).multiply_mutable(b2));

            MantExp Dni = MantExpComplex.DiffAbs(ri, rb.add(ai).add_mutable(ab));

            Dni = (r2.subtract(i2)).multiply4_mutable().multiply_mutable(Dni)
                    .add_mutable(((ri.add(rb).add_mutable(ai).add_mutable(ab)).abs_mutable()).multiply4_mutable().multiply_mutable(ar.multiply2().add_mutable(a2).subtract_mutable(ib.multiply2()).subtract_mutable(b2)));

            return new MantExpComplex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
        else {
            return DeltaSubN.fourth()
                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.SIX).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()))
                    .plus_mutable(DeltaSub0);
        }
    }

    @Override
    public Complex perturbationFunction(Complex DeltaSubN, int RefIteration) {

        Complex X = Reference[RefIteration];

        if(burning_ship) {
            double r = X.getRe();
            double i = X.getIm();
            double a = DeltaSubN.getRe();
            double b = DeltaSubN.getIm();
            double r2 = r*r;
            double i2 = i*i;
            double a2 = a*a;
            double b2 = b*b;
            double ar = a*r;
            double ib = i*b;
            double ab = a * b;
            double ri = r * i;
            double rb = r * b;
            double ai = a * i;

            double Dnr = 4 * r2*ar + 6 * r2*a2 + 4 * ar*a2 + a2 * a2 + 4 * i2*ib + 6 * i2*b2 + 4 * ib*b2 + b2 * b2 - 12 * r2*ib - 6 * r2*b2 - 12 * ar*i2 - 24 * ar*ib - 12 * ar*b2 - 6 * a2*i2 - 12 * a2*ib - 6 * a2*b2;
            double Dni = Complex.DiffAbs(ri, rb + ai + ab);
            Dni = 4 * (r2 - i2)*(Dni)+4 * Math.abs(ri + rb + ai + ab)*(2 * ar + a2 - 2 * ib - b2);

            return new Complex(Dnr, Dni);
        }
        else
        {
            return DeltaSubN.fourth()
                    .plus_mutable(DeltaSubN.cube().times_mutable(4).times_mutable(X))
                    .plus_mutable(DeltaSubN.square().times_mutable(6).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.times(4).times_mutable(X.cube()));
        }
    }

    @Override
    public MantExpComplex perturbationFunction(MantExpComplex DeltaSubN, int RefIteration) {

        MantExpComplex X = ReferenceDeep[RefIteration];

        if(burning_ship) {
            MantExp r = X.getRe();
            MantExp i = X.getIm();
            MantExp a = DeltaSubN.getRe();
            MantExp b = DeltaSubN.getIm();
            MantExp r2 = r.multiply(r);
            MantExp i2 = i.multiply(i);
            MantExp a2 = a.multiply(a);
            MantExp b2 = b.multiply(b);
            MantExp ar = a.multiply(r);
            MantExp ib = i.multiply(b);
            MantExp ab = a.multiply(b);
            MantExp ri = r.multiply(i);
            MantExp rb = r.multiply(b);
            MantExp ai = a.multiply(i);

            MantExp Dnr = r2.multiply4().multiply_mutable(ar)
                    .add_mutable(MantExp.SIX.multiply(r2).multiply_mutable(a2))
                    .add_mutable(ar.multiply4().multiply_mutable(a2))
                    .add_mutable(a2.multiply(a2))
                    .add_mutable(i2.multiply4().multiply_mutable(ib))
                    .add_mutable(MantExp.SIX.multiply(i2).multiply_mutable(b2))
                    .add_mutable(ib.multiply4().multiply_mutable(b2))
                    .add_mutable(b2.multiply(b2))
                    .subtract_mutable(MantExp.TWELVE.multiply(r2).multiply_mutable(ib))
                    .subtract_mutable(MantExp.SIX.multiply(r2).multiply_mutable(b2))
                    .subtract_mutable(MantExp.TWELVE.multiply(ar).multiply_mutable(i2))
                    .subtract_mutable(MantExp.TWENTYFOUR.multiply(ar).multiply_mutable(ib))
                    .subtract_mutable(MantExp.TWELVE.multiply(ar).multiply_mutable(b2))
                    .subtract_mutable(MantExp.SIX.multiply(a2).multiply_mutable(i2))
                    .subtract_mutable(MantExp.TWELVE.multiply(a2).multiply_mutable(ib))
                    .subtract_mutable(MantExp.SIX.multiply(a2).multiply_mutable(b2));

            MantExp Dni = MantExpComplex.DiffAbs(ri, rb.add(ai).add_mutable(ab));

            Dni = (r2.subtract(i2)).multiply4_mutable().multiply_mutable(Dni)
                    .add_mutable(((ri.add(rb).add_mutable(ai).add_mutable(ab)).abs_mutable()).multiply4_mutable().multiply_mutable(ar.multiply2().add_mutable(a2).subtract_mutable(ib.multiply2()).subtract_mutable(b2)));

            return new MantExpComplex(Dnr, Dni);
        }
        else {
            return DeltaSubN.fourth()
                    .plus_mutable(DeltaSubN.cube().times4_mutable().times_mutable(X))
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.SIX).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.times4().times_mutable(X.cube()));
        }
    }

    @Override
    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc, JProgressBar progress) {

        skippedIterations = 0;

        int numCoefficients = ThreadDraw.SERIES_APPROXIMATION_TERMS;

        if (numCoefficients < 2 || dsize.compareTo(MyApfloat.SA_START_SIZE) > 0) {
            return;
        }

        if (numCoefficients > 5) {
            numCoefficients = 5;
        }

        long[] logwToThe  = new long[numCoefficients + 1];

        final long[] magCoeff = new long[numCoefficients];

        logwToThe[1] = loc.getSeriesApproxSize().log2approx();

        for (int i = 2; i <= numCoefficients; i++) {
            logwToThe[i] = logwToThe[1] * i;
        }

        coefficients = new MantExpComplex[numCoefficients][max_data];


        coefficients[0][0] = new MantExpComplex(1, 0);
        for(int i = 1; i < numCoefficients; i++){
            coefficients[i][0] = new MantExpComplex();
        }

        long oomDiff = ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE;

        int length = deepZoom ? ReferenceDeep.length : Reference.length;

        int i;
        for(i = 1; i < length; i++) {

            if(deepZoom) {
                if(ReferenceDeep[i - 1] == null) {
                    skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                    return;
                }
            }
            else {
                if(Reference[i - 1] == null) {
                    skippedIterations = i - 1 <= skippedThreshold ? 0 : i - 1 - skippedThreshold;
                    return;
                }
            }

            MantExpComplex ref = null;

            if(deepZoom) {
                ref = ReferenceDeep[i - 1];
            }
            else {
                ref = new MantExpComplex(Reference[i - 1]);
            }

            MantExpComplex fourRefCubed = ref.cube().times4_mutable();

            MantExpComplex refSquared = ref.square();

            int new_i = i % max_data;
            int old_i = (i - 1) % max_data;

            MantExpComplex anSquared = null;
            MantExpComplex sixRefSquared = null;
            MantExpComplex twelveRefSquared = null;
            MantExpComplex fourAnCube = null;
            MantExpComplex bnSquared = null;
            MantExpComplex twelveRef = null;
            MantExpComplex twelveRefSquaredAn = null;

            if (numCoefficients >= 1) {
                //An+1 = P * A * (X^(P-1)) + 1
                coefficients[0][new_i] = coefficients[0][old_i].times(fourRefCubed).plus_mutable(MantExp.ONE); //4*Z^3*a_1 + 1
            }
            if (numCoefficients >= 2) {
                //Bn+1 = P * B * (X^(P-1)) + ((P*(P-1))/2) * (A^2) * (X^(P-2))
                anSquared = coefficients[0][old_i].square();
                sixRefSquared = refSquared.times(MantExp.SIX);
                coefficients[1][new_i] = coefficients[1][old_i].times(fourRefCubed)
                        .plus_mutable(anSquared.times(sixRefSquared)); //6*Z^2*a_1^2 + 4*Z^3*a_2
            }
            if (numCoefficients >= 3) {
                //Cn+1 = P * C * (X^(P-1)) + (P*(P-1)) * A * B * (X^(P-2)) + ((P*(P-1)*(P-2))/6) * (A^3) * (X^(P-3))
                twelveRefSquared = refSquared.times(MantExp.TWELVE);
                twelveRefSquaredAn = twelveRefSquared.times(coefficients[0][old_i]);
                fourAnCube = coefficients[0][old_i].cube().times4_mutable();
                coefficients[2][new_i] = coefficients[2][old_i].times(fourRefCubed)
                        .plus_mutable(twelveRefSquaredAn.times(coefficients[1][old_i]))
                        .plus_mutable(fourAnCube.times(ref)); //4*Z*a_1^3 + 12*Z^2*a_1*a_2 + 4*Z^3*a_3
            }

            if (numCoefficients >= 4) {
                bnSquared = coefficients[1][old_i].square();
                twelveRef = ref.times(MantExp.TWELVE);
                coefficients[3][new_i] = coefficients[3][old_i].times(fourRefCubed)
                        .plus_mutable(coefficients[0][old_i].fourth())
                        .plus_mutable(anSquared.times(twelveRef).times_mutable(coefficients[1][old_i]))
                        .plus_mutable(sixRefSquared.times(bnSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coefficients[2][old_i])); //a_1^4 + 12*Z*a_1^2*a_2 + 6*Z^2*a_2^2 + 12*Z^2*a_1*a_3 + 4*Z^3*a_4
            }

            if (numCoefficients >= 5) {
                coefficients[4][new_i] = coefficients[4][old_i].times(fourRefCubed)
                        .plus_mutable(fourAnCube.times(coefficients[1][old_i]))
                        .plus_mutable(bnSquared.times(coefficients[0][old_i]).times_mutable(twelveRef))
                        .plus_mutable(anSquared.times(coefficients[2][old_i]).times_mutable(twelveRef))
                        .plus_mutable(coefficients[1][old_i].times(coefficients[2][old_i]).times_mutable(twelveRefSquared))
                        .plus_mutable(twelveRefSquaredAn.times(coefficients[3][old_i])); //4*a_1^3*a_2 + 12*Z*a_1*a_2^2 + 12*Z*a_1^2*a_3 + 12*Z^2*a_2*a_3 + 12*Z^2*a_1*a_4 + 4*Z^3*a_5
            }

            for (int j = 0; j < numCoefficients; j++) {
                coefficients[j][new_i].Reduce();
                magCoeff[j] = coefficients[j][new_i].log2normApprox() + logwToThe[j + 1];
            }

            //Check to see if the approximation is no longer valid. The validity is checked if an arbitrary point we approximated differs from the point it should be by too much. That is the tolerancy which scales with the depth.
            //if (coefficients[numCoefficients - 2][new_i].times(tempLimit).norm_squared().compareTo(coefficients[numCoefficients - 1][new_i].times(DeltaSub0ToThe[numCoefficients]).norm_squared()) < 0) {
            //if(coefficients[numCoefficients - 2][new_i].norm_squared().divide(coefficients[numCoefficients - 1][new_i].norm_squared()).compareTo(tempLimit2) < 0) {
            if(i > 1 && isLastTermNotNegligible(magCoeff, oomDiff, numCoefficients)) {
                //|Bn+1 * d^2 * tolerance| < |Cn+1 * d^3|
                //When we're breaking here, it means that we've found a point where the approximation no longer works. Returning that would create a messed up image. We should move a little further back to get an approximation that is good.
                skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
                return;
            }

            if(progress != null && i % 1000 == 0) {
                progress.setValue(i);
                progress.setString(SA_CALCULATION_STR + " " + String.format("%3d",(int) ((double) (i) / progress.getMaximum() * 100)) + "%");
            }

        }

        i = length - 1;
        skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "") + (isJulia ? "-Julia-" + seed : "");
    }

    @Override
    public boolean supportsSeriesApproximation() {
        return !burning_ship && !isJulia;
    }

    public static void main(String[] args) {
        MandelbrotFourth a = new MandelbrotFourth(false);

        Reference = new Complex[2];
        Reference[0] = new Complex();
        Reference[1] = new Complex(0.32, 7.321);
        ReferenceDeep = new MantExpComplex[2];
        ReferenceDeep[0] = new MantExpComplex(Reference[0]);
        ReferenceDeep[1] = new MantExpComplex(Reference[1]);

        refPointSmall = new Complex(0.4, 0.0002);
        refPointSmallDeep = new MantExpComplex(refPointSmall);

        Complex Dn = new Complex(1.321, -4.21);
        Complex D0 = new Complex();
        MantExpComplex MDn = new MantExpComplex(Dn);
        MantExpComplex MD0 = new MantExpComplex(D0);

        Complex nzD0 = new Complex(-0.5, 1.4);
        MantExpComplex nzMD0 = new MantExpComplex(nzD0);

        System.out.println(a.perturbationFunction(Dn, D0, 1));
        System.out.println(a.perturbationFunction(Dn, 1));
        System.out.println(a.perturbationFunction(MDn, MD0, 1));
        System.out.println("Non Zero D0");
        System.out.println(a.perturbationFunction(Dn, nzD0, 1));
        System.out.println(a.perturbationFunction(MDn, nzMD0, 1));

        MandelbrotFourth b = new MandelbrotFourth(true);

        System.out.println();
        System.out.println(b.perturbationFunction(Dn, D0, 1));
        System.out.println(b.perturbationFunction(Dn, 1));
        System.out.println(b.perturbationFunction(MDn, MD0, 1));
        System.out.println("Non Zero D0");
        System.out.println(b.perturbationFunction(Dn, nzD0, 1));
        System.out.println(b.perturbationFunction(MDn, nzMD0, 1));
    }
}
