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
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.functions.Julia;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public class MandelbrotFifth extends Julia {

    private MandelVariation type;
    private MandelVariation type2;

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        this.burning_ship = burning_ship;

        power = 5;

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
            init_val = new DefaultInitialValue();
        }

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, escaping_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
        
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);

        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, boolean periodicity_checking, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int escaping_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, periodicity_checking, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 5;

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
    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, String perturbation_user_formula, boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, String initial_value_user_formula, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        this.burning_ship = burning_ship;

        power = 5;

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
            init_val = new DefaultInitialValue();
        }

    }

    public MandelbrotFifth(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, double[] rotation_vals, double[] rotation_center, boolean burning_ship, boolean mandel_grass, double[] mandel_grass_vals, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xJuliaCenter, double yJuliaCenter) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, apply_plane_on_julia, apply_plane_on_julia_seed, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, xJuliaCenter, yJuliaCenter);

        this.burning_ship = burning_ship;

        power = 5;

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
        complex[0].fifth_mutable().plus_mutable(complex[1]);
        type2.getValue(complex[0]);

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

        refPoint = pixel;

        boolean fullReference = ThreadDraw.CALCULATE_FULL_REFERENCE;
        boolean isSeriesInUse = ThreadDraw.SERIES_APPROXIMATION && !burning_ship;
        RefType = getRefType();
        FullRef = fullReference;

        Location loc = new Location();

        for (; iterations < max_iterations; iterations++) {

            Complex cz = z.toComplex();
            if(cz.isInfinite()) {
                break;
            }

            Reference[iterations] = cz;

            if(deepZoom) {
                ReferenceDeep[iterations] = loc.getMantExpComplex(z);
                //ReferenceDeep[iterations] = new MantExpComplex(Reference[iterations]);
            }


            if (!fullReference && iterations > 0 && bailout_algorithm.escaped(z, zold, zold2, iterations, c, start, c0, Apfloat.ZERO)) {
                break;
            }

            zold2 = zold;
            zold = z;

            if (burning_ship) {
                z = z.abs().fifth().plus(c);
            } else {
                z = z.fifth().plus(c);
            }

        }

        lastZValue = z;
        secondTolastZValue = zold;
        thirdTolastZValue = zold2;

        MaxRefIteration = iterations - 1;

        skippedIterations = 0;
        if(isSeriesInUse) {
            calculateSeries(size, deepZoom, externalLocation);
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
            double ra = r*a;
            double ib = i*b;

            double b2b2 = b2 * b2;
            double r2r2 = r2 * r2;
            double r2i2 = r2 * i2;
            double i2i2 = i2 * i2;
            double r2ra = r2 * ra;
            double r2a2 = r2 * a2;
            double raa2 = ra * a2;
            double a2a2 = a2 * a2;
            double i2ib = i2 * ib;
            double i2b2 = i2 * b2;
            double ibb2 = ib * b2;

            double temp = 20 * r2 * ib + 10 * r2 * b2 + 20 * ra * i2 + 40 * ra * ib + 20 * ra*b2 + 10 * a2*i2 + 20 * a2*ib + 10 * a2*b2;

            double Dnr = Complex.DiffAbs(r, a);
            Dnr = Dnr * (r2r2 - 10 * r2i2 + 5 * i2i2) + Math.abs(r + a) * (4 * r2ra + 6 * r2a2 + 4 * raa2 + a2a2 - temp
                    + 20 * i2ib + 30 * i2b2 + 20 * ibb2 + 5 *b2b2);

            double Dni = Complex.DiffAbs(i, b);
            Dni = Dni * (5 * r2r2 - 10 * r2i2 + i2i2) +  Math.abs(i + b) * (20 * r2ra + 30 * r2a2 + 20 * raa2 + 5 * a2a2 - temp
                    + 4 * i2ib + 6 * i2b2 + 4 * ibb2 + b2b2);

            return new Complex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
        else
        {
            return DeltaSubN.times(5).times_mutable(X.fourth())
                    .plus_mutable(DeltaSubN.square().times_mutable(10).times_mutable(X.cube()))
                    .plus_mutable(DeltaSubN.cube().times_mutable(10).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.fourth().times_mutable(5).times_mutable(X))
                    .plus_mutable(DeltaSubN.fifth())
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
            MantExp ra = r.multiply(a);
            MantExp ib = i.multiply(b);

            MantExp b2b2 = b2.multiply(b2);
            MantExp r2r2 = r2.multiply(r2);
            MantExp r2i2 = r2.multiply(i2);
            MantExp i2i2 = i2.multiply(i2);
            MantExp r2ra = r2.multiply(ra);
            MantExp r2a2 = r2.multiply(a2);
            MantExp raa2 = ra.multiply(a2);
            MantExp a2a2 = a2.multiply(a2);
            MantExp i2ib = i2.multiply(ib);
            MantExp i2b2 = i2.multiply(b2);
            MantExp ibb2 = ib.multiply(b2);


            MantExp temp = r2.multiply(ib).multiply_mutable(MantExp.TWENTY)
                    .add_mutable(r2.multiply(b2).multiply_mutable(MantExp.TEN))
                    .add_mutable(ra.multiply(i2).multiply_mutable(MantExp.TWENTY))
                    .add_mutable(ra.multiply(ib).multiply_mutable(MantExp.FOURTY))
                    .add_mutable(ra.multiply(b2).multiply_mutable(MantExp.TWENTY))
                    .add_mutable(a2.multiply(i2).multiply_mutable(MantExp.TEN))
                    .add_mutable(a2.multiply(ib).multiply_mutable(MantExp.TWENTY))
                    .add_mutable(a2.multiply(b2).multiply_mutable(MantExp.TEN));

            MantExp Dnr = MantExpComplex.DiffAbs(r, a);


            Dnr = Dnr.multiply(r2r2.subtract(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2.multiply(MantExp.FIVE)))
            .add_mutable(r.add(a).abs_mutable().multiply_mutable(r2ra.multiply4()
            .add_mutable(r2a2.multiply(MantExp.SIX))
            .add_mutable(raa2.multiply4())
            .add_mutable(a2a2)
            .subtract_mutable(temp)
            .add_mutable(i2ib.multiply(MantExp.TWENTY))
            .add_mutable(i2b2.multiply(MantExp.THIRTY))
            .add_mutable(ibb2.multiply(MantExp.TWENTY))
            .add_mutable(b2b2.multiply(MantExp.FIVE))
            ));

            MantExp Dni = MantExpComplex.DiffAbs(i, b);


            Dni = Dni.multiply(r2r2.multiply(MantExp.FIVE).subtract_mutable(r2i2.multiply(MantExp.TEN)).add_mutable(i2i2))
            .add_mutable(i.add(b).abs_mutable().multiply_mutable(r2ra.multiply(MantExp.TWENTY)
            .add_mutable(r2a2.multiply(MantExp.THIRTY))
            .add_mutable(raa2.multiply(MantExp.TWENTY))
            .add_mutable(a2a2.multiply(MantExp.FIVE))
            .subtract_mutable(temp)
            .add_mutable(i2ib.multiply4())
            .add_mutable(i2b2.multiply(MantExp.SIX))
            .add_mutable(ibb2.multiply4())
            .add_mutable(b2b2)
            ));

            return new MantExpComplex(Dnr, Dni).plus_mutable(DeltaSub0);
        }
        else {
            return DeltaSubN.times(MantExp.FIVE).times_mutable(X.fourth())
                    .plus_mutable(DeltaSubN.square().times_mutable(MantExp.TEN).times_mutable(X.cube()))
                    .plus_mutable(DeltaSubN.cube().times_mutable(MantExp.TEN).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.fourth().times_mutable(MantExp.FIVE).times_mutable(X))
                    .plus_mutable(DeltaSubN.fifth())
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
            double ra = r*a;
            double ib = i*b;

            double b2b2 = b2 * b2;
            double r2r2 = r2 * r2;
            double r2i2 = r2 * i2;
            double i2i2 = i2 * i2;
            double r2ra = r2 * ra;
            double r2a2 = r2 * a2;
            double raa2 = ra * a2;
            double a2a2 = a2 * a2;
            double i2ib = i2 * ib;
            double i2b2 = i2 * b2;
            double ibb2 = ib * b2;

            double temp = 20 * r2 * ib + 10 * r2 * b2 + 20 * ra * i2 + 40 * ra * ib + 20 * ra*b2 + 10 * a2*i2 + 20 * a2*ib + 10 * a2*b2;

            double Dnr = Complex.DiffAbs(r, a);
            Dnr = Dnr * (r2r2 - 10 * r2i2 + 5 * i2i2) + Math.abs(r + a) * (4 * r2ra + 6 * r2a2 + 4 * raa2 + a2a2 - temp
                    + 20 * i2ib + 30 * i2b2 + 20 * ibb2 + 5 *b2b2);

            double Dni = Complex.DiffAbs(i, b);
            Dni = Dni * (5 * r2r2 - 10 * r2i2 + i2i2) +  Math.abs(i + b) * (20 * r2ra + 30 * r2a2 + 20 * raa2 + 5 * a2a2 - temp
                    + 4 * i2ib + 6 * i2b2 + 4 * ibb2 + b2b2);

            return new Complex(Dnr, Dni);
        }
        else {
            return DeltaSubN.times(5).times_mutable(X.fourth())
                    .plus_mutable(DeltaSubN.square().times_mutable(10).times_mutable(X.cube()))
                    .plus_mutable(DeltaSubN.cube().times_mutable(10).times_mutable(X.square()))
                    .plus_mutable(DeltaSubN.fourth().times_mutable(5).times_mutable(X))
                    .plus_mutable(DeltaSubN.fifth());
        }
    }

    @Override
    public void calculateSeries(Apfloat dsize, boolean deepZoom, Location loc) {

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

            MantExpComplex fiveRefFourth = ref.fourth().times(MantExp.FIVE);

            MantExpComplex refCubed = ref.cube();

            MantExpComplex refSquared = ref.square();

            MantExpComplex twentyRefCubed = null;
            MantExpComplex tenRefCubed = null;
            MantExpComplex thirtyRefSquared = null;
            MantExpComplex anSquared = null;
            MantExpComplex anCubed = null;
            MantExpComplex bnSquared = null;
            MantExpComplex thirtyRefSquaredAnSquared = null;
            MantExpComplex twentyRefCubedAn = null;

            int new_i = i % max_data;
            int old_i = (i - 1) % max_data;

            if (numCoefficients >= 1) {
                //An+1 = P * A * (X^(P-1)) + 1
                coefficients[0][new_i] = coefficients[0][old_i].times(fiveRefFourth).plus_mutable(MantExp.ONE); //5*Z^4*a_1 + 1
            }
            if (numCoefficients >= 2) {
                //Bn+1 = P * B * (X^(P-1)) + ((P*(P-1))/2) * (A^2) * (X^(P-2))
                tenRefCubed = refCubed.times(MantExp.TEN);
                anSquared = coefficients[0][old_i].square();
                coefficients[1][new_i] = coefficients[1][old_i].times(fiveRefFourth)
                        .plus_mutable(tenRefCubed.times(anSquared));

                        //10*Z^3*a_1^2 + 5*Z^4*a_2

            }
            if (numCoefficients >= 3) {
                //Cn+1 = P * C * (X^(P-1)) + (P*(P-1)) * A * B * (X^(P-2)) + ((P*(P-1)*(P-2))/6) * (A^3) * (X^(P-3))
                twentyRefCubed = refCubed.times(MantExp.TWENTY);
                twentyRefCubedAn = twentyRefCubed.times(coefficients[0][old_i]);
                anCubed = coefficients[0][old_i].cube();
                coefficients[2][new_i] = coefficients[2][old_i].times(fiveRefFourth)
                        .plus_mutable(refSquared.times(MantExp.TEN).times_mutable(anCubed))
                        .plus_mutable(twentyRefCubedAn.times(coefficients[1][old_i]));

                //10*Z^2*a_1^3 + 20*Z^3*a_1*a_2 + 5*Z^4*a_3
            }

            if (numCoefficients >= 4) {
                thirtyRefSquared = refSquared.times(MantExp.THIRTY);
                bnSquared = coefficients[1][old_i].square();
                thirtyRefSquaredAnSquared = thirtyRefSquared.times(anSquared);
                coefficients[3][new_i] = coefficients[3][old_i].times(fiveRefFourth)
                        .plus_mutable(ref.times(MantExp.FIVE).times_mutable(coefficients[0][old_i].fourth()))
                        .plus_mutable(thirtyRefSquaredAnSquared.times(coefficients[1][old_i]))
                        .plus_mutable(tenRefCubed.times(bnSquared))
                        .plus_mutable(twentyRefCubedAn.times(coefficients[2][old_i]));

                        //5*Z*a_1^4 + 30*Z^2*a_1^2*a_2 + 10*Z^3*a_2^2 + 20*Z^3*a_1*a_3 + 5*Z^4*a_4
            }

            if (numCoefficients >= 5) {
                coefficients[4][new_i] = coefficients[4][old_i].times(fiveRefFourth)
                        .plus_mutable(coefficients[0][old_i].fifth())
                        .plus_mutable(ref.times(MantExp.TWENTY).times_mutable(anCubed).times_mutable(coefficients[1][old_i]))
                        .plus_mutable(thirtyRefSquared.times(coefficients[0][old_i]).times_mutable(bnSquared))
                        .plus_mutable(thirtyRefSquaredAnSquared.times(coefficients[2][old_i]))
                        .plus_mutable(twentyRefCubed.times(coefficients[1][old_i]).times_mutable(coefficients[2][old_i]))
                        .plus_mutable(twentyRefCubedAn.times(coefficients[3][old_i]));

                        //a_1^5 + 20*Z*a_1^3*a_2 + 30*Z^2*a_1*a_2^2 + 30*Z^2*a_1^2*a_3 + 20*Z^3*a_2*a_3 + 20*Z^3*a_1*a_4 + 5*Z^4*a_5
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

        }

        i = length - 1;
        skippedIterations = i <= skippedThreshold ? 0 : i - skippedThreshold;
    }

    @Override
    public String getRefType() {
        return super.getRefType() + (burning_ship ? "-Burning Ship" : "");
    }
}
