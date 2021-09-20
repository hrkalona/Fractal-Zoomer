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
package fractalzoomer.functions;

import fractalzoomer.bailout_conditions.*;
import fractalzoomer.core.*;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.fractal_options.filter.*;
import fractalzoomer.fractal_options.plane_influence.PlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.NoPlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.UserConditionalPlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.UserPlaneInfluence;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.fractal_options.orbit_traps.*;
import fractalzoomer.functions.mandelbrot.Mandelbrot;
import fractalzoomer.in_coloring_algorithms.*;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.*;
import fractalzoomer.out_coloring_algorithms.*;
import fractalzoomer.parser.Parser;
import fractalzoomer.planes.Plane;
import fractalzoomer.planes.distort.*;
import fractalzoomer.planes.fold.*;
import fractalzoomer.planes.general.*;
import fractalzoomer.planes.math.*;
import fractalzoomer.planes.math.inverse_trigonometric.*;
import fractalzoomer.planes.math.trigonometric.*;
import fractalzoomer.planes.newton.Newton3Plane;
import fractalzoomer.planes.newton.Newton4Plane;
import fractalzoomer.planes.newton.NewtonGeneralized3Plane;
import fractalzoomer.planes.newton.NewtonGeneralized8Plane;
import fractalzoomer.planes.user_plane.UserPlane;
import fractalzoomer.planes.user_plane.UserPlaneConditional;
import fractalzoomer.true_coloring_algorithms.*;
import fractalzoomer.utils.ColorAlgorithm;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona
 */
public abstract class Fractal {

    protected double xCenter;
    protected double yCenter;
    protected double size;
    protected int max_iterations;
    protected double bailout;
    protected double bailout_squared;
    protected double[] point;
    protected OutColorAlgorithm out_color_algorithm;
    protected InColorAlgorithm in_color_algorithm;
    protected BailoutCondition bailout_algorithm;
    protected BailoutCondition bailout_algorithm2;
    protected Plane plane;
    protected Rotation rotation;
    protected PlanePointOption init_val;
    protected PlanePointOption pertur_val;
    protected FunctionFilter preFilter;
    protected FunctionFilter postFilter;
    protected PlaneInfluence planeInfluence;
    protected boolean periodicity_checking;
    protected ArrayList<Complex> complex_orbit;
    protected Complex pixel_orbit;
    protected int check;
    protected int check_counter;
    protected int update;
    protected int update_counter;
    protected Complex period;
    protected Complex[] globalVars;
    protected OrbitTrap trap;
    protected boolean escaped;
    protected GenericStatistic statistic;
    protected double log_bailout_squared;
    private double trapIntesity;
    private boolean invertTrapHeight;
    private int trapHeightFunction;
    private boolean trapIncludeNotEscaped;
    private boolean trapIncludeEscaped;
    protected boolean statisticIncludeEscaped;
    protected boolean statisticIncludeNotEscaped;
    protected TrueColorAlgorithm outTrueColorAlgorithm;
    protected TrueColorAlgorithm inTrueColorAlgorithm;
    protected int trueColorValue;
    protected boolean hasTrueColor;
    protected int iterations;
    protected Complex zold;
    protected Complex zold2;
    protected Complex start;
    protected Complex c0;
    protected double statValue;
    protected double trapValue;
    protected boolean juliter;
    protected int juliterIterations;
    protected boolean juliterIncludeInitialIterations;
    protected boolean isJulia;

    public static Complex[] Reference;
    public static MantExpComplex[] ReferenceDeep;
    public static int MaxRefIteration;
    public static BigComplex refPoint;
    public static BigComplex lastZValue;
    public static BigComplex secondTolastZValue;
    public static BigComplex thirdTolastZValue;
    public static boolean FullRef = false;
    public static int RefPower = -1;
    public static boolean RefBurningShip = false;
    public static int skippedIterations;
    protected static final int skippedThreshold = 3;
    protected int power = 0;
    protected boolean burning_ship = false;
    protected static final int max_data = skippedThreshold * 2;
    protected static MantExpComplex[][] coefficients;

    public Fractal(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        isJulia = false;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.periodicity_checking = periodicity_checking;
        log_bailout_squared = Math.log(bailout_squared);

        globalVars = createGlobalVars();

        if (!periodicity_checking && ots.useTraps) {
            TrapFactory(ots);
            trapIntesity = ots.trapIntensity;
            invertTrapHeight = ots.invertTrapHeight;
            trapHeightFunction = ots.trapHeightFunction;
            trapIncludeEscaped = ots.trapIncludeEscaped;
            trapIncludeNotEscaped = ots.trapIncludeNotEscaped;
        }

        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

        PlaneFactory(plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        BailoutConditionFactory(bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_transform_center);

        point = plane_transform_center;

        skippedIterations = 0;
        
    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        isJulia = false;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);

        globalVars = createGlobalVars();

        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

        PlaneFactory(plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        pixel_orbit = getTransformedPixel(pixel_orbit);

        point = plane_transform_center;

    }

    public abstract void function(Complex[] complex);

    public Complex perturbationFunction(Complex dz, Complex dc, int RefIteration) {
        return  new Complex();
    }

    public MantExpComplex perturbationFunction(MantExpComplex dz, MantExpComplex dc, int RefIteration) {
        return new MantExpComplex();
    }

    public Complex perturbationFunction(Complex dz, int RefIteration) {
        return  new Complex();
    }

    protected double getPeriodSize() {

        return 1e-13;

    }

    protected final boolean periodicityCheck(Complex z) {

        //Check for period
        if (z.distance_squared(period) < getPeriodSize()) {
            return true;
        }

        //Update history
        if (check == check_counter) {
            check_counter = 0;

            //Double the value of check
            if (update == update_counter) {
                update_counter = 0;
                check <<= 1;
            }
            update_counter++;

            period.assign(z);
        } //End of update history

        check_counter++;

        return false;

    }

    public Complex getTransformedPixel(Complex pixel) {

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
            return pixel;
        }

        return plane.transform(rotation.rotate(pixel));

    }

    public Complex getTransformedPixelJulia(Complex pixel) {

        return null;

    }

    public BigComplex getPlaneTransformedPixel(BigComplex pixel) {

        try {
            return plane.transform(pixel);
        }
        catch (Exception ex) {
            return pixel;
        }
    }

    public final double calculateFractal(GenericComplex gpixel) {

        escaped = false;
        hasTrueColor = false;

        statValue = 0;
        trapValue = 0;

        resetGlobalVars();

        if(gpixel instanceof Complex) {
            Complex pixel = (Complex)gpixel;

            Complex transformed = getTransformedPixel(pixel);

            if (statistic != null) {
                statistic.initialize(transformed, pixel);
            }

            if (trap != null) {
                trap.initialize(transformed);
            }

            if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                return iterateFractalWithPerturbationWithoutPeriodicity(initialize(transformed), transformed);
            }

            return periodicity_checking ? iterateFractalWithPeriodicity(initialize(transformed), transformed) : iterateFractalWithoutPeriodicity(initialize(transformed), transformed);
        }
        else {
            MantExpComplex pixel = (MantExpComplex) gpixel;

            Complex pix = pixel.toComplex();
            if (statistic != null) {
                statistic.initialize(pix, pix);
            }

            if (trap != null) {
                trap.initialize(pix);
            }

            return iterateFractalWithPerturbationWithoutPeriodicity(initialize(pix), pixel);
        }


    }

    public boolean supportsPerturbationTheory() {
        return false;
    }

    protected Complex[] createGlobalVars() {

        Complex[] vars = new Complex[Parser.EXTRA_VARS];

        for (int i = 0; i < vars.length; i++) {
            vars[i] = new Complex();
        }

        return vars;
    }

    protected void resetGlobalVars() {

        for (int i = 0; i < globalVars.length; i++) {
            globalVars[i].reset();
        }
    }

    public Complex[] initialize(Complex pixel) {

        Complex[] complex = new Complex[2];

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
            complex[0] = new Complex();
            complex[1] = new Complex(pixel.getRe() + refPoint.getRe().doubleValue(), pixel.getIm() + refPoint.getIm().doubleValue());
        }
        else {
            complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
            complex[1] = new Complex(pixel);//c
        }

        zold = new Complex();
        zold2 = new Complex();
        start = new Complex(complex[0]);
        c0 = new Complex(complex[1]);

        return complex;

    }

    public Complex[] initializeSeed(Complex pixel) {return null;}

    public void calculateReferencePoint(BigComplex pixel, Apfloat size, boolean deepZoom, int iterations, Location externalLocation) {

    }

    public void calculateSeries(Apfloat dsize, boolean deepZoom, Location externalLocation) {

    }

    public void updateValues(Complex[] complex) {

    }

    protected double iterateFractalWithPeriodicity(Complex[] complex, Complex pixel) {

        iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }
        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;

    }

    protected double iterateFractalWithoutPeriodicity(Complex[] complex, Complex pixel) {

        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                return out;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0);
        }

        return in;

        /* iterations = 0;
        
         int exp = 28;
         long  dx = (long)Math.pow(2, exp);//(long)(Math.pow(2, 16) / (size) + 0.5);
        
          
        
         long real = (long)(pixel.getRe() * dx + 0.5);
         long imag = (long)(pixel.getIm() * dx + 0.5);

         long z_real = real;
         long z_imag = imag;
         long temp;
        
         long re_sqr;
         long im_sqr;
        
         long bail = dx << 2;
        
         int exp_1 = exp - 1;

         for(;iterations < max_iterations; iterations++) {
            
         re_sqr = (z_real * z_real) >> exp;
         im_sqr = (z_imag * z_imag) >> exp;
            
            
         if((re_sqr + im_sqr) >= bail) {
         return iterations;
         }
            
           
         temp = re_sqr - im_sqr + real;
         z_imag = ((z_real * z_imag) >> exp_1)  + imag;
         z_real = temp;
            
          
                
            
         }
        
         return ColorAlgorithm.MAXIMUM_ITERATIONS;*/
    }


    /*
     iterations = 0;
        
     int exp = 60;
     long  dx = (long)Math.pow(2, exp);//(long)(Math.pow(2, 16) / (size) + 0.5);
        
          
     BigInteger real = new BigInteger("" + (long)(pixel.getRe() * dx + 0.5));
     BigInteger imag = new BigInteger("" + (long)(pixel.getIm() * dx + 0.5));

     BigInteger z_real = real;
     BigInteger z_imag = imag;
     BigInteger temp;
        
     BigInteger re_sqr;
     BigInteger im_sqr;
        
     BigInteger bail = new BigInteger("" + (dx << 2));

     for(;iterations < max_iterations; iterations++) {
            
     re_sqr = z_real.multiply(z_real).shiftRight(exp);
     im_sqr = z_imag.multiply(z_imag).shiftRight(exp);
            
            
     if(re_sqr.add(im_sqr).compareTo(bail) >= 0) {
     return iterations;
     }
            
     temp = re_sqr.subtract(im_sqr).add(real);
     z_imag = z_real.multiply(z_imag).shiftRight(exp - 1).add(imag);
     z_real = temp;
            
          
                
            
     }
        
     return ColorAlgorithm.MAXIMUM_ITERATIONS;
     */

    public final void calculateFractalOrbit() {

        Complex[] complex = initialize(pixel_orbit);
        iterateFractalOrbit(complex, pixel_orbit);

    }

    protected void iterateFractalOrbit(Complex[] complex, Complex pixel) {
        iterations = 0;

        Complex temp = null;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    public final Complex calculateFractalDomain(Complex pixel) {

        resetGlobalVars();

        Complex transformed = getTransformedPixel(pixel);

        return iterateFractalDomain(initialize(transformed), transformed);

    }

    protected Complex iterateFractalDomain(Complex[] complex, Complex pixel) {

        iterations = 0;

        for (; iterations < max_iterations; iterations++) {

            updateValues(complex);

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0);

        }

        return complex[0];

    }

    protected GenericComplex initializeFromSeries(GenericComplex pixel) {

        if(power == 0) {
            return null;
        }

        int numCoefficients = ThreadDraw.SERIES_APPROXIMATION_TERMS;

        if(power > 2) {
            if(numCoefficients > 5) {
                numCoefficients = 5;
            }
        }

        MantExpComplex DeltaSubNMant = new MantExpComplex();

        MantExpComplex[] DeltaSub0ToThe = new MantExpComplex[numCoefficients + 1];

        if(pixel instanceof Complex) {
            DeltaSub0ToThe[1] = new MantExpComplex((Complex) pixel);
        }
        else if(pixel instanceof MantExpComplex) {
            DeltaSub0ToThe[1] = new MantExpComplex((MantExpComplex)pixel);
        }

        for (int i = 2; i <= numCoefficients; i++) {
            DeltaSub0ToThe[i] = DeltaSub0ToThe[i - 1].times(DeltaSub0ToThe[1]);
            DeltaSub0ToThe[i].Reduce();
        }

        for (int i = 0; i < numCoefficients; i++) {
            DeltaSubNMant = DeltaSubNMant.plus_mutable(coefficients[i][iterations % max_data].times(DeltaSub0ToThe[i + 1]));
        }

        if(pixel instanceof Complex) {
            Complex DeltaZn = DeltaSubNMant.toComplex();
            /*skipC = false;

            if(ThreadDraw.SMALL_ADDENDS_OPTIMIZATION) {
                MantExp deltaZnNorm = DeltaSubNMant.norm_squared();
                if (deltaZnNorm.compareTo(DeltaSub0ToThe[1].norm_squared().multiply(1e32)) > 0) {
                    skipC = true;
                }
            }*/

            return DeltaZn;
        }
        else {
            return DeltaSubNMant;
        }

    }


    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex pixel) {

        iterations = skippedIterations;

        Complex DeltaSubN = new Complex(complex[0]); // Delta z

        if(skippedIterations != 0) {

            DeltaSubN = (Complex) initializeFromSeries(pixel);

        }

        int RefIteration = iterations;

        Complex DeltaSub0 = new Complex(pixel); // Delta c
        double norm_squared = 0;

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                return res;
            }

            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

            RefIteration++;

            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if(max_iterations > 1){
                //complex[0] = Reference[RefIteration].plus(DeltaSubN.divide(1e100));
                complex[0] = Reference[RefIteration].plus(DeltaSubN);

            }
            //No Post filters work

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

            norm_squared = complex[0].norm_squared();
            if (norm_squared < DeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                DeltaSubN = complex[0];
                RefIteration = 0;
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0);
        }

        return in;

    }

    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex pixel) {

        iterations = skippedIterations;

        MantExpComplex DeltaSubN = new MantExpComplex(); // Delta z

        if(skippedIterations != 0) {

            DeltaSubN = (MantExpComplex) initializeFromSeries(pixel);

        }

        int RefIteration = iterations;

        MantExpComplex DeltaSub0 = new MantExpComplex(pixel); // Delta c

        int minExp = -1000;
        int reducedExp = minExp / power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        if(ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            MantExpComplex z = new MantExpComplex();
            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = ReferenceDeep[RefIteration].plus(DeltaSubN);
                    complex[0] = z.toComplex();
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                }

                DeltaSubN.Reduce();

                if(!ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM) {
            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();
            double norm_squared = complex[0].norm_squared();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0);
                    }

                    return res;
                }

                if (isZero) {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, RefIteration);
                } else {
                    CDeltaSubN = perturbationFunction(CDeltaSubN, CDeltaSub0, RefIteration);
                }

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    complex[0] = Reference[RefIteration].plus(CDeltaSubN);
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                norm_squared = complex[0].norm_squared();

                if (norm_squared < CDeltaSubN.norm_squared() || RefIteration >= MaxRefIteration) {
                    CDeltaSubN = complex[0];
                    RefIteration = 0;
                }

            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0);
        }

        return in;

    }

    public abstract void calculateJuliaOrbit();

    public abstract double calculateJulia(GenericComplex pixel);

    public abstract Complex calculateJuliaDomain(Complex pixel);

    public String getInitialValue() {

        if(ThreadDraw.PERTURBATION_THEORY && !isJulia && supportsPerturbationTheory()) {
            return "0.0";
        }

        return init_val != null ? init_val.toString() : "c";

    }

    public double getConvergentBailout() {

        return 0;

    }

    private void BailoutConditionFactory(int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, double[] plane_transform_center) {

        switch (bailout_test_algorithm) {

            case MainWindow.BAILOUT_CONDITION_CIRCLE:
                if(ThreadDraw.PERTURBATION_THEORY && !isJulia && supportsPerturbationTheory()) {
                    bailout_algorithm2 = new CircleBailoutPreCalcNormCondition(bailout_squared);
                }
                bailout_algorithm = new CircleBailoutCondition(bailout_squared);
                break;
            case MainWindow.BAILOUT_CONDITION_SQUARE:
                bailout_algorithm2 = bailout_algorithm = new SquareBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_RHOMBUS:
                bailout_algorithm2 = bailout_algorithm = new RhombusBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_REAL_STRIP:
                bailout_algorithm2 = bailout_algorithm = new RealStripBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_HALFPLANE:
                bailout_algorithm2 = bailout_algorithm = new HalfplaneBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_NNORM:
                bailout_algorithm2 = bailout_algorithm = new NNormBailoutCondition(bailout, n_norm);
                break;
            case MainWindow.BAILOUT_CONDITION_USER:
                bailout_algorithm2 = bailout_algorithm = new UserBailoutCondition(bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                break;
            case MainWindow.BAILOUT_CONDITION_FIELD_LINES:
                bailout_algorithm2 = bailout_algorithm = new FieldLinesBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_CROSS:
                bailout_algorithm2 = bailout_algorithm = new CrossBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_IM_STRIP:
                bailout_algorithm2 = bailout_algorithm = new ImaginaryStripBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED:
                bailout_algorithm2 = bailout_algorithm = new RealPlusImaginarySquaredBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_NO_BAILOUT:
                bailout_algorithm2 = bailout_algorithm = new NoBailoutCondition();
                break;

        }

        if (SkipBailoutCondition.SKIPPED_ITERATION_COUNT > 0) {
            bailout_algorithm = new SkipBailoutCondition(bailout_algorithm);
            bailout_algorithm2 = new SkipBailoutCondition(bailout_algorithm2);
        }

    }

    private void PlaneFactory(int plane_type, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        switch (plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.MU_SQUARED_PLANE:
                plane = new MuSquaredPlane();
                break;
            case MainWindow.MU_SQUARED_IMAGINARY_PLANE:
                plane = new MuSquaredImaginaryPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane();
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane();
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane();
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane();
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane();
                break;
            case MainWindow.INVERSED_LAMBDA2_PLANE:
                plane = new InversedLambda2Plane();
                break;
            case MainWindow.EXP_PLANE:
                plane = new ExpPlane();
                break;
            case MainWindow.LOG_PLANE:
                plane = new LogPlane();
                break;
            case MainWindow.SIN_PLANE:
                plane = new SinPlane();
                break;
            case MainWindow.COS_PLANE:
                plane = new CosPlane();
                break;
            case MainWindow.TAN_PLANE:
                plane = new TanPlane();
                break;
            case MainWindow.COT_PLANE:
                plane = new CotPlane();
                break;
            case MainWindow.SINH_PLANE:
                plane = new SinhPlane();
                break;
            case MainWindow.COSH_PLANE:
                plane = new CoshPlane();
                break;
            case MainWindow.TANH_PLANE:
                plane = new TanhPlane();
                break;
            case MainWindow.COTH_PLANE:
                plane = new CothPlane();
                break;
            case MainWindow.SEC_PLANE:
                plane = new SecPlane();
                break;
            case MainWindow.CSC_PLANE:
                plane = new CscPlane();
                break;
            case MainWindow.SECH_PLANE:
                plane = new SechPlane();
                break;
            case MainWindow.CSCH_PLANE:
                plane = new CschPlane();
                break;
            case MainWindow.ASIN_PLANE:
                plane = new ASinPlane();
                break;
            case MainWindow.ACOS_PLANE:
                plane = new ACosPlane();
                break;
            case MainWindow.ATAN_PLANE:
                plane = new ATanPlane();
                break;
            case MainWindow.ACOT_PLANE:
                plane = new ACotPlane();
                break;
            case MainWindow.ASINH_PLANE:
                plane = new ASinhPlane();
                break;
            case MainWindow.ACOSH_PLANE:
                plane = new ACoshPlane();
                break;
            case MainWindow.ATANH_PLANE:
                plane = new ATanhPlane();
                break;
            case MainWindow.ACOTH_PLANE:
                plane = new ACothPlane();
                break;
            case MainWindow.ASEC_PLANE:
                plane = new ASecPlane();
                break;
            case MainWindow.ACSC_PLANE:
                plane = new ACscPlane();
                break;
            case MainWindow.ASECH_PLANE:
                plane = new ASechPlane();
                break;
            case MainWindow.ACSCH_PLANE:
                plane = new ACschPlane();
                break;
            case MainWindow.SQRT_PLANE:
                plane = new SqrtPlane();
                break;
            case MainWindow.ABS_PLANE:
                plane = new AbsPlane();
                break;
            case MainWindow.FOLDUP_PLANE:
                plane = new FoldUpPlane(plane_transform_center);
                break;
            case MainWindow.FOLDDOWN_PLANE:
                plane = new FoldDownPlane(plane_transform_center);
                break;
            case MainWindow.FOLDRIGHT_PLANE:
                plane = new FoldRightPlane(plane_transform_center);
                break;
            case MainWindow.FOLDLEFT_PLANE:
                plane = new FoldLeftPlane(plane_transform_center);
                break;
            case MainWindow.FOLDIN_PLANE:
                plane = new FoldInPlane(plane_transform_radius);
                break;
            case MainWindow.FOLDOUT_PLANE:
                plane = new FoldOutPlane(plane_transform_radius);
                break;
            case MainWindow.NEWTON3_PLANE:
                plane = new Newton3Plane();
                break;
            case MainWindow.NEWTON4_PLANE:
                plane = new Newton4Plane();
                break;
            case MainWindow.NEWTONGENERALIZED3_PLANE:
                plane = new NewtonGeneralized3Plane();
                break;
            case MainWindow.NEWTONGENERALIZED8_PLANE:
                plane = new NewtonGeneralized8Plane();
                break;
            case MainWindow.USER_PLANE:
                if (user_plane_algorithm == 0) {
                    plane = new UserPlane(user_plane, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                } else {
                    plane = new UserPlaneConditional(user_plane_conditions, user_plane_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center, globalVars);
                }
                break;
            case MainWindow.GAMMA_PLANE:
                plane = new GammaFunctionPlane();
                break;
            case MainWindow.FACT_PLANE:
                plane = new FactorialPlane();
                break;
            case MainWindow.BIPOLAR_PLANE:
                plane = new BipolarPlane(plane_transform_center);
                break;
            case MainWindow.INVERSED_BIPOLAR_PLANE:
                plane = new InversedBipolarPlane(plane_transform_center);
                break;
            case MainWindow.TWIRL_PLANE:
                plane = new TwirlPlane(plane_transform_center, plane_transform_angle, plane_transform_radius);
                break;
            case MainWindow.SHEAR_PLANE:
                plane = new ShearPlane(plane_transform_scales);
                break;
            case MainWindow.KALEIDOSCOPE_PLANE:
                plane = new KaleidoscopePlane(plane_transform_center, plane_transform_angle, plane_transform_angle2, plane_transform_radius, plane_transform_sides);
                break;
            case MainWindow.PINCH_PLANE:
                plane = new PinchPlane(plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_amount);
                break;
            case MainWindow.CIRCLEINVERSION_PLANE:
                plane = new CircleInversionPlane(plane_transform_center, plane_transform_radius);
                break;
            case MainWindow.VARIATION_MU_PLANE:
                plane = new MuVariationPlane();
                break;
            case MainWindow.ERF_PLANE:
                plane = new ErfPlane();
                break;
            case MainWindow.RZETA_PLANE:
                plane = new RiemannZetaPlane();
                break;
            case MainWindow.INFLECTION_PLANE:
                plane = new InflectionPlane(plane_transform_center);
                break;
            case MainWindow.RIPPLES_PLANE:
                plane = new RipplesPlane(plane_transform_scales, plane_transform_wavelength, waveType);
                break;
            case MainWindow.SKEW_PLANE:
                plane = new SkewPlane(plane_transform_angle, plane_transform_angle2);
                break;
        }

    }

    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int escaping_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTime();
                } else {
                    out_color_algorithm = new SmoothEscapeTime(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.ITERATIONS_PLUS_RE:
                out_color_algorithm = new EscapeTimePlusRe();
                break;
            case MainWindow.ITERATIONS_PLUS_IM:
                out_color_algorithm = new EscapeTimePlusIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusReDivideIm();
                break;
            case MainWindow.ITERATIONS_PLUS_RE_PLUS_IM_PLUS_RE_DIVIDE_IM:
                out_color_algorithm = new EscapeTimePlusRePlusImPlusReDivideIm();
                break;
            case MainWindow.BIOMORPH:
                if (!smoothing) {
                    out_color_algorithm = new Biomorphs(bailout);
                } else {
                    out_color_algorithm = new SmoothBiomorphs(log_bailout_squared, bailout, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.COLOR_DECOMPOSITION:
                out_color_algorithm = new ColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_COLOR_DECOMPOSITION:
                out_color_algorithm = new EscapeTimeColorDecomposition();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER:
                out_color_algorithm = new EscapeTimeGaussianInteger();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER2:
                out_color_algorithm = new EscapeTimeGaussianInteger2();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER3:
                out_color_algorithm = new EscapeTimeGaussianInteger3();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER4:
                out_color_algorithm = new EscapeTimeGaussianInteger4();
                break;
            case MainWindow.ESCAPE_TIME_GAUSSIAN_INTEGER5:
                out_color_algorithm = new EscapeTimeGaussianInteger5();
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM:
                out_color_algorithm = new EscapeTimeAlgorithm1(2);
                break;
            case MainWindow.ESCAPE_TIME_ALGORITHM2:
                out_color_algorithm = new EscapeTimeAlgorithm2();
                break;
            case MainWindow.ESCAPE_TIME_ESCAPE_RADIUS:
                out_color_algorithm = new EscapeTimeEscapeRadius(log_bailout_squared);
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines2(log_bailout_squared);
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines2(log_bailout_squared, escaping_smooth_algorithm);
                }
                break;
            case MainWindow.USER_OUTCOLORING_ALGORITHM:
                if (user_out_coloring_algorithm == 0) {
                    out_color_algorithm = new UserOutColorAlgorithm(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                } else {
                    out_color_algorithm = new UserConditionalOutColorAlgorithm(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                }
                break;

        }
    }

    protected void InColoringAlgorithmFactory(int in_coloring_algorithm, int user_in_coloring_algorithm, String incoloring_formula, String[] user_incoloring_conditions, String[] user_incoloring_condition_formula, double[] plane_transform_center) {

        switch (in_coloring_algorithm) {

            case MainWindow.MAX_ITERATIONS:
                in_color_algorithm = new MaximumIterations();
                break;
            case MainWindow.Z_MAG:
                in_color_algorithm = new ZMag(max_iterations);
                break;
            case MainWindow.DECOMPOSITION_LIKE:
                in_color_algorithm = new DecompositionLike(max_iterations);
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm(max_iterations);
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag(max_iterations);
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared(max_iterations);
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared(max_iterations);
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm(max_iterations);
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares(max_iterations);
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2(max_iterations);
                break;
            case MainWindow.USER_INCOLORING_ALGORITHM:
                if (user_in_coloring_algorithm == 0) {
                    in_color_algorithm = new UserInColorAlgorithm(incoloring_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, bailout, globalVars);
                } else {
                    in_color_algorithm = new UserConditionalInColorAlgorithm(user_incoloring_conditions, user_incoloring_condition_formula, max_iterations, xCenter, yCenter, size, plane_transform_center, bailout, globalVars);
                }
                break;

        }

    }

    protected void StatisticFactory(StatisticsSettings sts, double[] plane_transform_center) {

        statisticIncludeEscaped = sts.statisticIncludeEscaped;
        statisticIncludeNotEscaped = sts.statisticIncludeNotEscaped;

        if (sts.statisticGroup == 1) {
            statistic = new UserStatisticColoring(sts.statistic_intensity, sts.user_statistic_formula, xCenter, yCenter, max_iterations, size, bailout, plane_transform_center, globalVars, sts.useAverage, sts.user_statistic_init_value, sts.reductionFunction, sts.useIterations, sts.useSmoothing);
            return;
        }
        else if(sts.statisticGroup == 2) {
            if(ThreadDraw.PERTURBATION_THEORY && !isJulia() && supportsPerturbationTheory()) {
                return;
            }
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, log_bailout_squared, false, 0, sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
            return;
        }

        switch (sts.statistic_type) {
            case MainWindow.STRIPE_AVERAGE:
                statistic = new StripeAverage(sts.statistic_intensity, sts.stripeAvgStripeDensity, log_bailout_squared, sts.useSmoothing , sts.useAverage);
                break;
            case MainWindow.CURVATURE_AVERAGE:
                statistic = new CurvatureAverage(sts.statistic_intensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.COS_ARG_DIVIDE_NORM_AVERAGE:
                statistic = new CosArgDivideNormAverage(sts.statistic_intensity, sts.cosArgStripeDensity, log_bailout_squared, sts.useSmoothing, sts.useAverage);
                break;
            case MainWindow.ATOM_DOMAIN_BOF60_BOF61:
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity);
                break;
            case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, log_bailout_squared, sts.useSmoothing, sts.useAverage, false, 0);
                break;

        }
    }

    protected double getStatistic(double result, boolean escaped) {

        statValue = escaped ? statistic.getValue() : statistic.getValueNotEscaped();

        if (Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return max_iterations + statValue;
        }

        return result < 0 ? result - statValue : result + statValue;

    }

    protected double getTrap(double result) {
 
        if (trapIntesity == 0 || trap.hasColor()) {
            return result;
        }
        
        double distance = trap.getDistance();

        if (distance == Double.MAX_VALUE) {
            return result;
        }

        double maxVal = trap.getMaxValue();
        distance = maxVal - distance;
        distance /= maxVal;
        
        //Function
        switch(trapHeightFunction) {
            case 1:
                distance = (-Math.cos(Math.PI * distance) * 0.5 + 0.5);
                break;
            case 2:
                distance = Math.sqrt(distance);
                break;
            case 3:
                distance = (Math.exp(distance) - 1) / (Math.E - 1);
                break;
            case 0:
            default:
                break;
        }
        
        distance = invertTrapHeight ? 1 - distance : distance;
        distance *= trapIntesity;

        trapValue = distance;
        
        if (Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            return max_iterations + trapValue;
        }
        
        return result < 0 ? result - trapValue : result + trapValue;

    }

    protected double getFinalValueOut(double result) {

        if (trap != null && trapIncludeEscaped) {
            result = getTrap(result);
        }

        if (statistic != null && statisticIncludeEscaped) {
            result = getStatistic(result, true);
        }

        return result;

    }

    protected double getFinalValueIn(double result) {

        if (trap != null && trapIncludeNotEscaped) {
            result = getTrap(result);
        }

        if (statistic != null && statisticIncludeNotEscaped) {
            result = getStatistic(result, false);
        }

        return result;

    }

    private FunctionFilter filterFactoryInternal(FunctionFilterSettings ffs) {

        switch (ffs.functionFilter) {
            case MainWindow.NO_FUNCTION_FILTER:
                return new NoFunctionFilter();
            case MainWindow.ABS_FUNCTION_FILTER:
                return new AbsFunctionFilter();
            case MainWindow.SQUARE_FUNCTION_FILTER:
                return new SquareFunctionFilter();
            case MainWindow.SQRT_FUNCTION_FILTER:
                return new SqrtFunctionFilter();
            case MainWindow.RECIPROCAL_FUNCTION_FILTER:
                return new ReciprocalFunctionFilter();
            case MainWindow.SIN_FUNCTION_FILTER:
                return new SinFunctionFilter();
            case MainWindow.COS_FUNCTION_FILTER:
                return new CosFunctionFilter();
            case MainWindow.EXP_FUNCTION_FILTER:
                return new ExpFunctionFilter();
            case MainWindow.LOG_FUNCTION_FILTER:
                return new LogFunctionFilter();
            case MainWindow.USER_FUNCTION_FILTER:
                if(ffs.user_function_filter_algorithm == 0) {
                    return new UserFunctionFilter(ffs.userFormulaFunctionFilter, max_iterations, xCenter, yCenter, size, point, globalVars);
                }
                else {
                    return new UserConditionalFunctionFilter(ffs.user_function_filter_conditions, ffs.user_function_filter_condition_formula, max_iterations, xCenter, yCenter, size, point, globalVars);
                }
        }

        return null;

    }

    public void preFilterFactory(FunctionFilterSettings ffs) {

        preFilter = filterFactoryInternal(ffs);

    }

    public void postFilterFactory(FunctionFilterSettings ffs) {

        postFilter = filterFactoryInternal(ffs);

    }

    public void influencePlaneFactory(PlaneInfluenceSettings ips) {

        planeInfluence = null;

        switch (ips.influencePlane) {
            case MainWindow.NO_PLANE_INFLUENCE:
                planeInfluence = new NoPlaneInfluence();
                break;
            case MainWindow.USER_PLANE_INFLUENCE:
                if(ips.user_plane_influence_algorithm == 0) {
                    planeInfluence = new UserPlaneInfluence(ips.userFormulaPlaneInfluence, max_iterations, xCenter, yCenter, size, point, globalVars);
                }
                else {
                    planeInfluence = new UserConditionalPlaneInfluence(ips.user_plane_influence_conditions, ips.user_plane_influence_condition_formula, max_iterations, xCenter, yCenter, size, point, globalVars);
                }
                break;
        }

    }

    private void TrapFactory(OrbitTrapSettings ots) {

        switch (ots.trapType) {
            case MainWindow.POINT_TRAP:        
                trap = new PointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.POINT_SQUARE_TRAP:
                trap = new PointSquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.POINT_RHOMBUS_TRAP:
                trap = new PointRhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.CROSS_TRAP:
                trap = new CrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_TRAP:
                trap = new CircleOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_TRAP:
                trap = new SquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_TRAP:
                trap = new RhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.POINT_N_NORM_TRAP:
                trap = new PointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_TRAP:
                trap = new NNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.RE_TRAP:
                trap = new ReOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.IM_TRAP:
                trap = new ImOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_CROSS_TRAP:
                trap = new CircleCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_CROSS_TRAP:
                trap = new SquareCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_CROSS_TRAP:
                trap = new RhombusCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_CROSS_TRAP:
                trap = new NNormCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_POINT_TRAP:
                trap = new CirclePointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_POINT_TRAP:
                trap = new SquarePointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_POINT_TRAP:
                trap = new RhombusPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_POINT_TRAP:
                trap = new NNormPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_POINT_N_NORM_TRAP:
                trap = new NNormPointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_TRAP:
                trap = new GoldenRatioSpiralOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_POINT_TRAP:
                trap = new GoldenRatioSpiralPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP:
                trap = new GoldenRatioSpiralPointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_CROSS_TRAP:
                trap = new GoldenRatioSpiralCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_CIRCLE_TRAP:
                trap = new GoldenRatioSpiralCircleOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_SQUARE_TRAP:
                trap = new GoldenRatioSpiralSquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_RHOMBUS_TRAP:
                trap = new GoldenRatioSpiralRhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP:
                trap = new GoldenRatioSpiralNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;              
            case MainWindow.STALKS_TRAP:
                trap = new StalksOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_POINT_TRAP:
                trap = new StalksPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_POINT_N_NORM_TRAP:
                trap = new StalksPointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_CROSS_TRAP:
                trap = new StalksCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_CIRCLE_TRAP:
                trap = new StalksCircleOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_SQUARE_TRAP:
                trap = new StalksSquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_RHOMBUS_TRAP:
                trap = new StalksRhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_N_NORM_TRAP:
                trap = new StalksNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.IMAGE_TRAP:
                trap = new ImageOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;             
            case MainWindow.ATOM_DOMAIN_TRAP:
                trap = new AtomDomainOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_ATOM_DOMAIN_TRAP:
                trap = new SquareAtomDomainOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_ATOM_DOMAIN_TRAP:
                trap = new RhombusAtomDomainOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.NNORM_ATOM_DOMAIN_TRAP:
                trap = new NNormAtomDomainOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.TEAR_DROP_ORBIT_TRAP:
                trap = new TearDropOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;

        }

    }

    public void setTrueColorAlgorithm(TrueColorSettings tcs) {

        if (tcs.trueColorOut) {

            if (tcs.trueColorOutMode == 0) {

                switch (tcs.trueColorOutPreset) {
                    case 0:
                        outTrueColorAlgorithm = new Xaos1TrueColorAlgorithm();
                        break;
                    case 1:
                        outTrueColorAlgorithm = new Xaos2TrueColorAlgorithm();
                        break;
                    case 2:
                        outTrueColorAlgorithm = new Xaos3TrueColorAlgorithm();
                        break;
                    case 3:
                        outTrueColorAlgorithm = new Xaos4TrueColorAlgorithm();
                        break;
                    case 4:
                        outTrueColorAlgorithm = new Xaos5TrueColorAlgorithm();
                        break;
                    case 5:
                        outTrueColorAlgorithm = new Xaos6TrueColorAlgorithm();
                        break;
                    case 6:
                        outTrueColorAlgorithm = new Xaos7TrueColorAlgorithm();
                        break;
                    case 7:
                        outTrueColorAlgorithm = new Xaos8TrueColorAlgorithm();
                        break;
                    case 8:
                        outTrueColorAlgorithm = new Xaos9TrueColorAlgorithm();
                        break;
                    case 9:
                        outTrueColorAlgorithm = new Xaos10TrueColorAlgorithm();
                        break;
                }

            } else {
                outTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.outTcComponent1, tcs.outTcComponent2, tcs.outTcComponent3, tcs.outTcColorSpace, bailout, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

        if (tcs.trueColorIn) {
            if (tcs.trueColorInMode == 0) {
                switch (tcs.trueColorInPreset) {
                    case 0:
                        inTrueColorAlgorithm = new Xaos1TrueColorAlgorithm();
                        break;
                    case 1:
                        inTrueColorAlgorithm = new Xaos2TrueColorAlgorithm();
                        break;
                    case 2:
                        inTrueColorAlgorithm = new Xaos3TrueColorAlgorithm();
                        break;
                    case 3:
                        inTrueColorAlgorithm = new Xaos4TrueColorAlgorithm();
                        break;
                    case 4:
                        inTrueColorAlgorithm = new Xaos5TrueColorAlgorithm();
                        break;
                    case 5:
                        inTrueColorAlgorithm = new Xaos6TrueColorAlgorithm();
                        break;
                    case 6:
                        inTrueColorAlgorithm = new Xaos7TrueColorAlgorithm();
                        break;
                    case 7:
                        inTrueColorAlgorithm = new Xaos8TrueColorAlgorithm();
                        break;
                    case 8:
                        inTrueColorAlgorithm = new Xaos9TrueColorAlgorithm();
                        break;
                    case 9:
                        inTrueColorAlgorithm = new Xaos10TrueColorAlgorithm();
                        break;
                }
            } else {
                inTrueColorAlgorithm = new UserTrueColorAlgorithm(tcs.inTcComponent1, tcs.inTcComponent2, tcs.inTcComponent3, tcs.inTcColorSpace, bailout, max_iterations, xCenter, yCenter, size, point, globalVars);
            }
        }

    }

    protected void setTrueColorOut(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        trueColorValue = outTrueColorAlgorithm.createColor(z, zold, zold2, iterations, c, start, c0, statValue, trapValue,true);
        hasTrueColor = true;

    }

    protected void setTrueColorIn(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0) {

        trueColorValue = inTrueColorAlgorithm.createColor(z, zold, zold2, iterations, c, start, c0, statValue, trapValue, false);
        hasTrueColor = true;

    }

    public boolean hasTrueColor() {
        return hasTrueColor;
    }
    
    public void resetTrueColor() {
        hasTrueColor = false;
    }

    public int getTrueColorValue() {
        return trueColorValue;
    }

    public OrbitTrap getOrbitTrapInstance() {

        return trap;

    }

    public GenericStatistic getStatisticInstance() {

        return statistic;

    }

    public boolean escaped() {

        return escaped;

    }

    public double getFractal3DHeight(double value) {

        return ColorAlgorithm.transformResultToHeight(value, max_iterations);

    }

    public abstract double getJulia3DHeight(double value);

    public int type() {

        return MainWindow.ESCAPING;

    }

    public void setIteration(int iterations) {
        this.iterations = iterations;
    }

    public void updateZoldAndZold2(Complex z) {
        zold2.assign(zold);
        zold.assign(z);
    }

    public Complex getZold() {
        return zold;
    }

    public Complex getZold2() {
        return zold2;
    }

    public FunctionFilter getPreFilter() {return preFilter;}
    public FunctionFilter getPostFilter() {return postFilter;}
    public PlaneInfluence getPlaneInfluence() {return planeInfluence;}

    public void setJuliter(boolean juliter) {
        this.juliter = juliter;
    }

    public void setJuliterIterations(int juliterIterations) {
        this.juliterIterations = juliterIterations;
    }

    public void setJuliterIncludeInitialIterations(boolean juliterIncludeInitialIterations) {
        this.juliterIncludeInitialIterations = juliterIncludeInitialIterations;
    }

    public boolean isJulia() {
        return isJulia;
    }

    public static void clearReferences() {
        refPoint = null;
        lastZValue = null;
        secondTolastZValue = null;
        thirdTolastZValue = null;
        RefPower = -1;
        RefBurningShip = false;
        Reference = null;
        ReferenceDeep = null;
        Mandelbrot.Referencex2 = null;
        Mandelbrot.ReferenceDeepx2 = null;
        coefficients = null;
    }

    /*protected static boolean isLastTermNotNegligible(MantExpComplex[][] coefs, MantExpComplex[] delta, MantExp limit, int i, int terms) {
        int lastIndex = terms - 1;
        MantExp magLast = coefs[lastIndex][i].norm_squared().multiply_mutable(limit);

        for(int k=0; k<lastIndex; k++) {
            //|Ak*d^k| * THRESHOLD < |Am*d^m|
            //|Ak*d^k|^2 * THRESHOLD^2 < |Am*d^m|^2
            //|Ak*d^k|^2 < |Am*d^m|^2 * 1/THRESHOLD^2
            //|Ak*d^k|^2 < |Am|^2 * (|d^m| * 1/THRESHOLD^2)
            //|Ak*d^k|^2 < |Am|^2 * limit
            if (coefs[k][i].times(delta[k + 1]).norm_squared().compareTo(magLast) < 0) return true;
        }
        return false;
    }*/

    protected static boolean isLastTermNotNegligible(long[] magCoeff, long magDiffThreshold, int terms) {
        int lastIndex = terms - 1;
        long magLast = magCoeff[lastIndex];

        for(int k=0; k < lastIndex; k++) {
            if (magCoeff[k]-magLast<magDiffThreshold) return true;
        }
        return false;
    }

    public int getPower() {
        return power;
    }

    public boolean getBurningShip() {
        return burning_ship;
    }

    protected Complex[] copyReference(Complex[] from, Complex[] to) {

        for(int i = 0; i < from.length && i < to.length; i++) {
            if(from[i] == null) {
                break;
            }
            to[i] = from[i];
        }

        return to;
    }

    protected MantExpComplex[] copyDeepReference(MantExpComplex[] from, MantExpComplex[] to) {

        for(int i = 0; i < from.length && i < to.length; i++) {
            if(from[i] == null) {
                break;
            }
            to[i] = from[i];
        }

        return to;
    }

}
