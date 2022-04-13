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

package fractalzoomer.functions.root_finding_methods.newton;

import fractalzoomer.core.*;
import fractalzoomer.core.DeepReference;
import fractalzoomer.core.location.Location;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.main.app_settings.StatisticsSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import static fractalzoomer.main.Constants.REFERENCE_CALCULATION_STR;

/**
 *
 * @author hrkalona
 */
public class Newton3 extends NewtonRootFindingMethod {

    public Newton3(double xCenter, double yCenter, double size, int max_iterations, int out_coloring_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, boolean smoothing, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, int converging_smooth_algorithm, OrbitTrapSettings ots, StatisticsSettings sts) {

        super(xCenter, yCenter, size, max_iterations,  plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        switch (out_coloring_algorithm) {
            case MainWindow.BINARY_DECOMPOSITION:
                convergent_bailout = 1E-9;
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                convergent_bailout = 1E-9;
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                convergent_bailout = 1E-7;
                break;

        }

        power = 3;

        OutColoringAlgorithmFactory(out_coloring_algorithm, smoothing, converging_smooth_algorithm, user_out_coloring_algorithm, outcoloring_formula, user_outcoloring_conditions, user_outcoloring_condition_formula, plane_transform_center);
       
        InColoringAlgorithmFactory(in_coloring_algorithm, user_in_coloring_algorithm, incoloring_formula, user_incoloring_conditions, user_incoloring_condition_formula, plane_transform_center);
        
        if(sts.statistic) {
            StatisticFactory(sts, plane_transform_center);
        }
    }

    //orbit
    public Newton3(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula,  double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula,  plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        power = 3;

    }
    
   

    @Override
    public void function(Complex[] complex) {

        Complex fz = complex[0].cube().sub_mutable(1);
        Complex dfz = complex[0].square().times_mutable(3);
        
        newtonMethod(complex[0], fz, dfz);

    }

    @Override
    public boolean supportsPerturbationTheory() {
        return true;
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
            ReferenceSubPixel = new double[max_iterations << 1];
            PrecalculatedTerms = new double[max_iterations << 1];

            if (deepZoom) {
                ReferenceDeep = new DeepReference(max_iterations);
                ReferenceSubPixelDeep = new DeepReference(max_iterations);
                PrecalculatedTermsDeep = new DeepReference(max_iterations);
            }
        }
        else if (max_iterations > getReferenceLength()){
            Reference = Arrays.copyOf(Reference, max_iterations << 1);
            ReferenceSubPixel = Arrays.copyOf(ReferenceSubPixel, max_iterations << 1);
            PrecalculatedTerms = Arrays.copyOf(PrecalculatedTerms, max_iterations << 1);

            if (deepZoom) {
                ReferenceDeep.resize(max_iterations);
                ReferenceSubPixelDeep.resize(max_iterations);
                PrecalculatedTermsDeep.resize(max_iterations);
            }

            System.gc();
        }

        BigComplex pixel = (BigComplex)gpixel;

        //Due to zero, all around zero will not work
        if(pixel.norm().compareTo(new MyApfloat(1e-4)) < 0) {
            Apfloat dx = new MyApfloat(1e-4);
            pixel = new BigComplex(dx, dx);
        }

        BigComplex z = iterations == 0 ? pixel : (BigComplex)lastZValue;

        BigComplex zold = iterations == 0 ? new BigComplex() : (BigComplex)secondTolastZValue;
        BigComplex zold2 = iterations == 0 ? new BigComplex() : (BigComplex)thirdTolastZValue;
        BigComplex start = pixel;

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

            BigComplex zsubpixel = z.sub(pixel);
            BigComplex preCalc = z.fourth().sub(z); //Z^4-Z for catastrophic cancelation

            setArrayValue(ReferenceSubPixel, iterations, zsubpixel.toComplex());
            setArrayValue(PrecalculatedTerms, iterations, preCalc.toComplex());

            if(deepZoom) {
                setArrayDeepValue(ReferenceDeep, iterations, loc.getMantExpComplex(z));
                setArrayDeepValue(ReferenceSubPixelDeep, iterations, loc.getMantExpComplex(zsubpixel));
                setArrayDeepValue(PrecalculatedTermsDeep, iterations, loc.getMantExpComplex(preCalc));
            }

            if (iterations > 0 && convergent_bailout_algorithm.converged(z, zold, zold2, iterations, pixel, start, pixel, pixel)) {
                break;
            }

            zold2 = zold;
            zold = z;

            try {
                z = z.sub(z.cube().sub(MyApfloat.ONE).divide(z.square().times(three)));
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

    @Override
    public Complex evaluateFunction(Complex z, Complex c) {
        return z.cube().sub_mutable(1);
    }

}
