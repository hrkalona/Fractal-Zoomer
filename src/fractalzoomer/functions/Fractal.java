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
import fractalzoomer.convergent_bailout_conditions.*;
import fractalzoomer.core.*;
import fractalzoomer.core.bla.BLA;
import fractalzoomer.core.bla.BLADeep;
import fractalzoomer.core.bla.BLAS;
import fractalzoomer.core.interpolation.*;
import fractalzoomer.core.location.Location;
import fractalzoomer.core.nanomb1.Nanomb1;
import fractalzoomer.core.nanomb1.uniPoly;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.fractal_options.filter.*;
import fractalzoomer.fractal_options.initial_value.DefaultInitialValue;
import fractalzoomer.fractal_options.initial_value.InitialValue;
import fractalzoomer.fractal_options.initial_value.VariableConditionalInitialValue;
import fractalzoomer.fractal_options.initial_value.VariableInitialValue;
import fractalzoomer.fractal_options.iteration_statistics.*;
import fractalzoomer.fractal_options.orbit_traps.*;
import fractalzoomer.fractal_options.perturbation.DefaultPerturbation;
import fractalzoomer.fractal_options.perturbation.Perturbation;
import fractalzoomer.fractal_options.perturbation.VariableConditionalPerturbation;
import fractalzoomer.fractal_options.perturbation.VariablePerturbation;
import fractalzoomer.fractal_options.plane_influence.NoPlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.PlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.UserConditionalPlaneInfluence;
import fractalzoomer.fractal_options.plane_influence.UserPlaneInfluence;
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

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.LongAdder;

import static fractalzoomer.main.Constants.*;

/**
 *
 * @author hrkalona
 */
public abstract class Fractal {

    protected int functionId;
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
    protected ConvergentBailoutCondition convergent_bailout_algorithm;
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
    protected boolean isJuliaMap = false;
    protected PlanePointOption defaultInitVal;
    protected boolean isOrbit;
    protected boolean isDomain;
    protected int iterationPeriod;

    protected long bla_steps;
    protected long bla_iterations;
    protected long perturb_iterations;

    public static LongAdder total_bla_iterations;
    public static LongAdder total_bla_steps;
    public static LongAdder total_perturb_iterations;

    public static LongAdder total_nanomb1_skipped_iterations;

    public static double[] Reference;
    public static double[] SecondReference;
    public static double[] ReferenceSubCp;
    public static double[] SecondReferenceSubCp;

    public static double[] PrecalculatedTerms;
    public static double[] PrecalculatedTerms2;
    public static double[] SecondPrecalculatedTerms2;
    public static double[] SecondPrecalculatedTerms;
    public static DeepReference PrecalculatedTermsDeep;
    public static DeepReference PrecalculatedTerms2Deep;
    public static DeepReference SecondPrecalculatedTerms2Deep;
    public static DeepReference SecondPrecalculatedTermsDeep;
    public static DeepReference ReferenceDeep;
    public static DeepReference SecondReferenceDeep;
    public static DeepReference ReferenceSubCpDeep;
    public static DeepReference SecondReferenceSubCpDeep;
    public static BLAS B;
    public static int MaxRefIteration;
    public static int MaxRef2Iteration;
    public static GenericComplex refPoint;
    public static Complex C;
    public static MantExpComplex Cdeep;
    public static Complex refPointSmall;
    public static MantExpComplex refPointSmallDeep;
    public static GenericComplex lastZValue;
    public static GenericComplex secondTolastZValue;
    public static GenericComplex thirdTolastZValue;

    public static GenericComplex lastZ2Value;
    public static GenericComplex secondTolastZ2Value;
    public static GenericComplex thirdTolastZ2Value;
    public static Object minValue;
    public static String RefType = "";
    public static int skippedIterations;
    protected static final int skippedThreshold = 3;
    protected double power = 0;
    protected boolean burning_ship = false;
    protected static final int max_data = skippedThreshold * 2;
    public static DeepReference coefficients;
    public static int SATerms;
    public static int SAMaxSkip;
    public static long SAOOMDiff;
    public static long SASize;
    public static long ReferenceCalculationTime;
    public static long SecondReferenceCalculationTime;
    public static long SACalculationTime;
    public static long Nanomb1CalculationTime;
    public static long BLACalculationTime;
    public static int BLAbits;
    public static int BLAStartingLevel;
    public static int DetectedPeriod;
    public static MantExp BLASize;
    public static Nanomb1 nanomb1;

    public static ArrayList<Integer> tinyRefPts = new ArrayList<>();

    public Fractal() {
        plane = new MuPlane();
    }

    public Fractal(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        isJulia = false;
        isOrbit = false;
        isDomain = false;

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.periodicity_checking = periodicity_checking;
        log_bailout_squared = Math.log(bailout_squared);

        defaultInitVal = new InitialValue(0, 0);

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

        //skippedIterations = 0;
        
    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, boolean isJulia) {

        this.isJulia = isJulia;
        isOrbit = true;
        isDomain = false;

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);

        defaultInitVal = new InitialValue(0,0);

        globalVars = createGlobalVars();

        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

        PlaneFactory(plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        pixel_orbit = getTransformedPixel(pixel_orbit);

        point = plane_transform_center;

    }

    public abstract void function(Complex[] complex);
    public void function(BigComplex[] complex) {}
    public void function(BigNumComplex[] complex) {}

    public Complex perturbationFunction(Complex dz, Complex dc, int RefIteration) {
        return  new Complex();
    }

    public Complex perturbationFunctionScaled(Complex dz, Complex dc, double s, int RefIteration) {
        return  new Complex();
    }

    public Complex perturbationFunctionScaled(Complex dz, double s, int RefIteration) {
        return  new Complex();
    }

    public MantExpComplex perturbationFunction(MantExpComplex dz, MantExpComplex dc, int RefIteration) {
        return new MantExpComplex();
    }

    public Complex perturbationFunction(Complex dz, int RefIteration) {
        return  new Complex();
    }

    public MantExpComplex perturbationFunction(MantExpComplex dz, int RefIteration) {
        return new MantExpComplex();
    }

    public Complex perturbationFunction(Complex dz, double[] Reference, double[] PrecalculatedTerms, double[] PrecalculatedTerms2, int RefIteration) {
        return  new Complex();
    }

    public MantExpComplex perturbationFunction(MantExpComplex dz, DeepReference ReferenceDeep, DeepReference PrecalculatedTermsDeep, DeepReference PrecalculatedTerms2Deep, int RefIteration) {
        return new MantExpComplex();
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

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory() && !isOrbit && !isDomain) {
            return pixel;
        }

        return plane.transform(rotation.rotate(pixel));

    }

    public Complex getTransformedPixelJulia(Complex pixel) {

        return null;

    }

    public Complex getPlaneTransformedPixel(Complex pixel) {

        if(!isJulia || (isJulia && applyPlaneOnJulia())) {
            try {
                return plane.transform(pixel);
            }
            catch (Exception ex) {
                return pixel;
            }
        }
        return pixel;

    }

    public BigComplex getPlaneTransformedPixel(BigComplex pixel) {

        if(!isJulia || (isJulia && applyPlaneOnJulia())) {
            try {
                return plane.transform(pixel);
            }
            catch (Exception ex) {
                return pixel;
            }
        }
        return pixel;

    }

    public DDComplex getPlaneTransformedPixel(DDComplex pixel) {

        if(!isJulia || (isJulia && applyPlaneOnJulia())) {
            try {
                return plane.transform(pixel);
            }
            catch (Exception ex) {
                return pixel;
            }
        }
        return pixel;

    }

    public MpfrBigNumComplex getPlaneTransformedPixel(MpfrBigNumComplex pixel) {

        if(!isJulia || (isJulia && applyPlaneOnJulia())) {
            try {
                return plane.transform(pixel);
            }
            catch (Exception ex) {
                return pixel;
            }
        }
        return pixel;

    }

    public BigNumComplex getPlaneTransformedPixel(BigNumComplex pixel) {

        if(!isJulia || (isJulia && applyPlaneOnJulia())) {
            try {
                return plane.transform(pixel);
            }
            catch (Exception ex) {
                return pixel;
            }
        }
        return pixel;

    }

    public final double calculateFractal(GenericComplex gpixel) {

        escaped = false;
        hasTrueColor = false;

        statValue = 0;
        trapValue = 0;

        resetGlobalVars();

        if(gpixel instanceof Complex) {

            Complex pixel = (Complex)gpixel;

            if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {

                Complex pixelWithoutDelta = pixel.plus(refPointSmall);

                if (statistic != null) {
                    statistic.initialize(pixelWithoutDelta, null);
                }

                if (trap != null) {
                    trap.initialize(pixelWithoutDelta);
                }

                try {
                    return ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation() ? iterateFractalWithPerturbationBLAWithoutPeriodicity(initialize(pixel), pixel) : iterateFractalWithPerturbationWithoutPeriodicity(initialize(pixel), pixel);
                }
                catch (Exception ex) {
                    return 0;
                }
            }
            else {
                Complex transformed = getTransformedPixel(pixel);

                if (statistic != null) {
                    statistic.initialize(transformed, pixel);
                }

                if (trap != null) {
                    trap.initialize(transformed);
                }

                return periodicity_checking ? iterateFractalWithPeriodicity(initialize(transformed), transformed) : iterateFractalWithoutPeriodicity(initialize(transformed), transformed);
            }
        }
        else {
            MantExpComplex pixel = (MantExpComplex) gpixel;

            Complex pix = pixel.toComplex();

            Complex pixelWithoutDelta = pix.plus(refPointSmall);

            if (statistic != null) {
                statistic.initialize(pixelWithoutDelta, null);
            }

            if (trap != null) {
                trap.initialize(pixelWithoutDelta);
            }

            try {

                if(ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation()) {
                    return iterateFractalWithPerturbationBLAWithoutPeriodicity(initialize(pix), pixel);
                }
//                else if(ThreadDraw.SCALED_ITERATIONS && supportsScaledIterations())
//                {
//                    return iterateFractalWithPerturbationWithoutPeriodicityScaled(initialize(pix), pixel);
//                }
                else {
                    return iterateFractalWithPerturbationWithoutPeriodicity(initialize(pix), pixel);
                }
            }
            catch (Exception ex) {
                return 0;
            }
        }
        /*else {
            BigNumComplex pixel = (BigNumComplex) gpixel;
            return iterateFractalArbitraryPrecisionWithoutPeriodicity(initialize(pixel), pixel);
        }*/
        /*else {
            BigComplex pixel = (BigComplex) gpixel;
            return iterateFractalArbitraryPrecisionWithoutPeriodicity(initialize(pixel), pixel);
        }*/


    }

    public boolean supportsPerturbationTheory() {
        return false;
    }

    public boolean needsSecondReference() {
        return isJulia;
    }

    public boolean supportsSeriesApproximation() {
        return false;
    }

    public boolean supportsBilinearApproximation() {
        return false;
    }

    public boolean supportsNanomb1() {
        return false;
    }

    public boolean supportsPeriod() {
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
            if(!isOrbit && !isDomain) {
                Complex temp = pixel.plus(refPointSmall);
                complex[0] = new Complex(defaultInitVal.getValue(temp));
                complex[1] = new Complex(temp);
            }
            else {
                complex[0] = new Complex(defaultInitVal.getValue(pixel));
                complex[1] = new Complex(pixel);
            }
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

    public BigComplex[] initialize(BigComplex pixel) {

        BigComplex[] complex = new BigComplex[2];

        complex[0] = new BigComplex(pixel);//z
        complex[1] = new BigComplex(pixel);//c

        //zold = new Complex();
        //zold2 = new Complex();
        //start = new Complex(complex[0]);
        //c0 = new Complex(complex[1]);

        return complex;

    }

    public BigNumComplex[] initialize(BigNumComplex pixel) {

        BigNumComplex[] complex = new BigNumComplex[2];

        complex[0] = new BigNumComplex(pixel);//z
        complex[1] = new BigNumComplex(pixel);//c

        //zold = new Complex();
        //zold2 = new Complex();
        //start = new Complex(complex[0]);
        //c0 = new Complex(complex[1]);

        return complex;

    }

    public BigNumComplex[] initializeSeed(BigNumComplex pixel) {return null;}

    public BigComplex[] initializeSeed(BigComplex pixel) {return null;}

    public Complex[] initializeSeed(Complex pixel) {return null;}

    public Complex[] initializePerturbation(Complex dpixel) {

        Complex[] complex = new Complex[2];

        if(isJulia) {
            complex[0] = new Complex(dpixel);
            complex[1] = null;
        }
        else {
            complex[0] = new Complex();
            complex[1] = new Complex(dpixel);

            if(skippedIterations != 0 && ThreadDraw.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation()) {

                GenericComplex[] result = initializeFromSeries(dpixel);
                complex[0] = (Complex) result[0];

                if(statistic != null && statistic instanceof NormalMap) {
                    ((NormalMap)statistic).initializeApproximationDerivatives(
                            new MantExpComplex(getArrayValue(Reference, skippedIterations)).plus_mutable((MantExpComplex) result[1]),
                            new MantExpComplex(getArrayValue(Reference, skippedIterations)).plus_mutable((MantExpComplex) result[2])
                            );
                }

            }
            else if(ThreadDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1()) {
                GenericComplex[] result = initializeFromNanomb1(dpixel);

                complex[0] = new Complex((Complex)result[1]);
                complex[1] = new Complex((Complex)result[0]);

                if(statistic != null && statistic instanceof NormalMap && nanomb1SkippedIterations < max_iterations) {
                    ((NormalMap)statistic).initializeApproximationDerivatives(
                            new MantExpComplex(getArrayValue(Reference, nanomb1SkippedIterations % iterationPeriod)).plus_mutable((MantExpComplex) result[2]),
                            new MantExpComplex(getArrayValue(Reference, nanomb1SkippedIterations % iterationPeriod)).plus_mutable((MantExpComplex) result[3])
                    );
                }
            }
        }

        return complex;

    }

    public MantExpComplex[] initializePerturbation(MantExpComplex dpixel) {

        MantExpComplex[] complex = new MantExpComplex[2];

        if(isJulia) {
            complex[0] = new MantExpComplex(dpixel);
            complex[1] = null;
        }
        else {
            complex[0] = new MantExpComplex();
            complex[1] = new MantExpComplex(dpixel);

            if (skippedIterations != 0 && ThreadDraw.APPROXIMATION_ALGORITHM == 1 && supportsSeriesApproximation()) {

                GenericComplex[] result = initializeFromSeries(dpixel);
                complex[0] = (MantExpComplex) result[0];

                if (statistic != null && statistic instanceof NormalMap) {
                    ((NormalMap) statistic).initializeApproximationDerivatives(
                            getArrayDeepValue(ReferenceDeep, skippedIterations).plus_mutable((MantExpComplex) result[1]),
                            getArrayDeepValue(ReferenceDeep, skippedIterations).plus_mutable((MantExpComplex) result[2])
                    );
                }

            }
            else if(ThreadDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1()) {
                GenericComplex[] result = initializeFromNanomb1(dpixel);

                complex[0] = new MantExpComplex((MantExpComplex)result[1]);
                complex[1] = new MantExpComplex((MantExpComplex)result[0]);

                if(statistic != null && statistic instanceof NormalMap && nanomb1SkippedIterations < max_iterations) {
                    ((NormalMap) statistic).initializeApproximationDerivatives(
                            getArrayDeepValue(ReferenceDeep, nanomb1SkippedIterations % iterationPeriod).plus_mutable((MantExpComplex) result[2]),
                            getArrayDeepValue(ReferenceDeep, nanomb1SkippedIterations % iterationPeriod).plus_mutable((MantExpComplex) result[3])
                    );
                }

            }
        }

        return complex;

    }

    public void calculateReferencePoint(GenericComplex pixel, Apfloat size, boolean deepZoom, int iterations, int iterations2, Location externalLocation, JProgressBar progress) {

    }

    public void calculateSeriesWrapper(Apfloat dsize, boolean deepZoom, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();
        if(progress != null) {
            progress.setMaximum(Fractal.MaxRefIteration);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_sa_color);
            progress.setString(SA_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }
        SAOOMDiff = ThreadDraw.SERIES_APPROXIMATION_OOM_DIFFERENCE;
        SAMaxSkip = ThreadDraw.SERIES_APPROXIMATION_MAX_SKIP_ITER;
        calculateSeries(dsize, deepZoom, externalLocation, progress);
        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(SA_CALCULATION_STR + " 100%");
        }
        SACalculationTime = System.currentTimeMillis() - time;
    }

    protected void calculateNanomb1(boolean deepZoom, JProgressBar progress) {

    }

    public void calculateNanomb1Wrapper(boolean deepZoom, JProgressBar progress) {

        long time = System.currentTimeMillis();
        if(progress != null) {
            progress.setMaximum(getReferenceMaxIterations() * 2 - 1);
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_nanomb1_color);
            progress.setString(NANOMB1_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }
        calculateNanomb1(deepZoom, progress);
        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(NANOMB1_CALCULATION_STR + " 100%");
        }
        Nanomb1CalculationTime = System.currentTimeMillis() - time;
        total_nanomb1_skipped_iterations = new LongAdder();
    }

    public void calculateBLAWrapper(boolean deepZoom, Location externalLocation, JProgressBar progress) {

        long time = System.currentTimeMillis();
        if(progress != null) {
            progress.setValue(0);
            progress.setForeground(MainWindow.progress_bla_color);
            progress.setString(BLA_CALCULATION_STR + " " + String.format("%3d", 0) + "%");
        }
        BLAbits = ThreadDraw.BLA_BITS;
        BLAStartingLevel = ThreadDraw.BLA_STARTING_LEVEL;
        calculateBLA(deepZoom, externalLocation, progress);

        if(progress != null) {
            progress.setValue(progress.getMaximum());
            progress.setString(BLA_CALCULATION_STR + " 100%");
        }
        BLACalculationTime = System.currentTimeMillis() - time;
        total_bla_iterations = new LongAdder();
        total_bla_steps = new LongAdder();
        total_perturb_iterations = new LongAdder();
    }

    protected void calculateSeries(Apfloat dsize, boolean deepZoom, Location externalLocation, JProgressBar progress) {

    }

    public int getReferenceFinalIterationNumber() {
        return iterationPeriod != 0 ? iterationPeriod : MaxRefIteration;
    }

    public int getBLALength() {
        //If we have a period, its better to have the BLA table at period length
        return iterationPeriod != 0 ? getReferenceFinalIterationNumber() : getReferenceFinalIterationNumber() + 1;
    }

    protected void calculateBLA(boolean deepZoom, Location externalLocation, JProgressBar progress) {

        B = new BLAS(this);

        BLASize = externalLocation.getMaxSizeInImage();

        if(deepZoom) {
            B.init(getBLALength(), ReferenceDeep, BLASize, progress);
        }
        else {
            B.init(getBLALength(), Reference, BLASize.toDouble(), progress);
        }
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

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return out;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

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

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return out;
            }

            zold2.assign(zold);
            zold.assign(complex[0]);

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }
        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
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

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            temp = rotation.rotateInverse(complex[0]);

            if (Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
                break;
            }

            complex_orbit.add(temp);
        }

    }

    public final Complex calculateFractalDomain(Complex pixel) {

        isDomain = true;

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

            complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            function(complex);
            complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

        }

        return complex[0];

    }

    protected int nanomb1SkippedIterations;

    protected GenericComplex[] initializeFromNanomb1(GenericComplex dpixel) {
        MantExpComplex d = new MantExpComplex();
        MantExpComplex dd = new MantExpComplex();
        MantExpComplex ddd = new MantExpComplex();

        nanomb1SkippedIterations = 0;

        int iteration = 0;

        MantExpComplex d0;

        if(dpixel instanceof Complex) {
            d0 = new MantExpComplex((Complex) dpixel);
        }
        else {
            d0 = new MantExpComplex((MantExpComplex) dpixel);
        }

        int ReferencePeriod = iterationPeriod;

        if(d0.norm().compareTo(nanomb1.Bout) < 0) {

            uniPoly up;
            int derivatives = 0;
            if(statistic != null && statistic instanceof NormalMap) {
                if(((NormalMap) statistic).usesSecondDerivative()) {
                    derivatives = 2;
                }
                else {
                    derivatives = 1;
                }
            }

            up = new uniPoly(nanomb1.SSA, d0, derivatives);

            while(iteration < max_iterations && d.norm_squared().compareTo(nanomb1.Bout) < 0){
                if(derivatives == 0) {
                    up.eval(d);
                }
                else if(derivatives == 1) {
                    up.eval(d, dd);
                }
                else {
                    up.eval(d, dd, ddd);
                }
                iteration += ReferencePeriod;
            }
        }

        nanomb1SkippedIterations = iteration;

        total_nanomb1_skipped_iterations.add(nanomb1SkippedIterations > max_iterations ? max_iterations : nanomb1SkippedIterations);

        MantExpComplex d0_ = d0.sub(nanomb1.nucleusPos);

        if(dpixel instanceof Complex) {
            return new GenericComplex[] {d0_.toComplex(), d.toComplex(), dd, ddd};
        }
        else {
            return new GenericComplex[] {d0_, d, dd, ddd};
        }

    }

    protected GenericComplex[] initializeFromSeries(GenericComplex pixel) {

        if(power == 0) {
            return null;
        }

        int numCoefficients = SATerms;

        MantExpComplex DeltaSubNMant = new MantExpComplex();
        MantExpComplex DDeltaSubNMant = new MantExpComplex();
        MantExpComplex DDDeltaSubNMant = new MantExpComplex();

        MantExpComplex[] DeltaSub0ToThe = new MantExpComplex[numCoefficients + 1];

        DeltaSub0ToThe[0] = new MantExpComplex(MantExp.ONE, MantExp.ZERO);

        if(pixel instanceof Complex) {
            DeltaSub0ToThe[1] = new MantExpComplex((Complex) pixel);
        }
        else if(pixel instanceof MantExpComplex) {
            DeltaSub0ToThe[1] = new MantExpComplex((MantExpComplex)pixel);
        }

        MantExpComplex DeltaSub0ToThe1 = DeltaSub0ToThe[1];

        for (int i = 2; i <= numCoefficients; i++) {
            DeltaSub0ToThe[i] = DeltaSub0ToThe[i - 1].times(DeltaSub0ToThe1);
            DeltaSub0ToThe[i].Reduce();
        }

        MantExpComplex tempCoef = null;
        MantExpComplex temp = null;
        MantExpComplex temp2 = null;
        MantExpComplex temp3 = null;
        for (int i = 0; i < numCoefficients; i++) {
            tempCoef = getSACoefficient(i, skippedIterations);
            temp = tempCoef.times(DeltaSub0ToThe[i + 1]);
            temp.Reduce();
            DeltaSubNMant = DeltaSubNMant.plus_mutable(temp);

            if(statistic != null && statistic instanceof NormalMap) {
                temp2 = tempCoef.times(DeltaSub0ToThe[i]).times_mutable(new MantExp(i + 1));
                temp2.Reduce();
                DDeltaSubNMant = DDeltaSubNMant.plus_mutable(temp2);

                if(((NormalMap) statistic).usesSecondDerivative() && i > 0) {
                    temp3 = tempCoef.times(DeltaSub0ToThe[i - 1]).times_mutable(new MantExp(i * (i + 1)));
                    temp3.Reduce();
                    DDDeltaSubNMant = DDDeltaSubNMant.plus_mutable(temp3);
                }
            }

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

            return new GenericComplex[] {DeltaZn, DDeltaSubNMant, DDDeltaSubNMant};
        }
        else {
            return new GenericComplex[] {DeltaSubNMant, DDeltaSubNMant, DDDeltaSubNMant};
        }

    }

    public double iterateJuliaWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {
        return 0;
    }

    public double iterateJuliaWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {
        return 0;
    }

    public double getAndAccumulateStats(double val) {


        total_bla_iterations.add(bla_iterations);
        total_bla_steps.add(bla_steps);
        total_perturb_iterations.add(perturb_iterations);


        return val;
    }


    public double iterateFractalWithPerturbationBLAWithoutPeriodicity(Complex[] complex, Complex dpixel) {

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;
        iterations = 0;

        int RefIteration = iterations;

        int MaxRefIteration = getReferenceFinalIterationNumber();

        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        double normSquared = 0;
        double DeltaNormSquared = 0;

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {//&& perturb_iterations < PerturbIterations;

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, normSquared, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStats(res);
            }

            // bla steps
            BLA b = null;
            while ((b = B.lookupBackwards(RefIteration, DeltaNormSquared)) != null) {

                int l = b.getL();
                iterations += l;
                bla_steps++;
                bla_iterations += l;

                if(iterations >= max_iterations) {
                    break;
                }

                DeltaSubN = b.getValue(DeltaSubN, DeltaSub0);
                DeltaNormSquared = DeltaSubN.norm_squared();

                RefIteration += l;

                // rebase

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
                //No Post filters work
                normSquared = complex[0].norm_squared();

                if (normSquared < DeltaNormSquared || (RefIteration >= MaxRefIteration)) {
                    DeltaSubN = complex[0];
                    RefIteration = 0;
                    DeltaNormSquared = normSquared;
                }

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0, b);
                }


                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, normSquared, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStats(res);
                }
            }

            if(iterations >= max_iterations) {
                break;
            }

            // perturbation iteration
            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);
            DeltaNormSquared = DeltaSubN.norm_squared();

            RefIteration++;
            perturb_iterations++;

            // rebase
            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if (max_iterations > 1) {
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            }
            //No Post filters work
            normSquared = complex[0].norm_squared();

            if (normSquared < DeltaNormSquared || (RefIteration >= MaxRefIteration)) {
                DeltaSubN = complex[0];
                DeltaNormSquared = normSquared;
                RefIteration = 0;
            }

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStats(in);

    }

    public double iterateFractalWithPerturbationBLAWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {

        bla_steps = 0;
        bla_iterations = 0;
        perturb_iterations = 0;

        iterations = 0;

        int RefIteration = iterations;

        int MaxRefIteration = getReferenceFinalIterationNumber();

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        MantExp normSquared = MantExp.ZERO;
        MantExp DeltaNormSquared = MantExp.ZERO;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        MantExpComplex z = new MantExpComplex();

        for (; iterations < max_iterations; iterations++) {//&& perturb_iterations < PerturbIterations;

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, normSquared.toDouble(), pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return getAndAccumulateStats(res);
            }

            // bla steps
            BLADeep b = null;
            while ((b = B.lookupBackwards(RefIteration, DeltaNormSquared)) != null) {

                int l = b.getL();

                iterations += l;
                bla_steps++;
                bla_iterations += l;

                if(iterations >= max_iterations) {
                    break;
                }

                DeltaSubN = b.getValue(DeltaSubN, DeltaSub0);
                DeltaNormSquared = DeltaSubN.norm_squared();

                RefIteration += l;

                // rebase

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = z.toComplex();
                //No Post filters work
                normSquared = z.norm_squared();

                if (normSquared.compareTo(DeltaNormSquared) < 0 || (RefIteration >= MaxRefIteration)) {
                    DeltaSubN = z;
                    RefIteration = 0;
                    DeltaNormSquared = normSquared;
                }

                DeltaSubN.Reduce();

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0, b);
                }


                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, normSquared.toDouble(), pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return getAndAccumulateStats(res);
                }
            }

            if(iterations >= max_iterations) {
                break;
            }

            // perturbation iteration
            DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);
            DeltaNormSquared = DeltaSubN.norm_squared();

            RefIteration++;
            perturb_iterations++;

            // rebase
            zold2.assign(zold);
            zold.assign(complex[0]);

            //No Plane influence work
            //No Pre filters work
            if (max_iterations > 1) {
                z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
            }
            complex[0] = z.toComplex();
            //No Post filters work
            normSquared = z.norm_squared();

            if (normSquared.compareTo(DeltaNormSquared) < 0 || (RefIteration >= MaxRefIteration)) {
                DeltaSubN = z;
                DeltaNormSquared = normSquared;
                RefIteration = 0;
            }

            DeltaSubN.Reduce();

            if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return getAndAccumulateStats(in);

    }

    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, Complex dpixel) {


        Complex[] deltas = initializePerturbation(dpixel);
        Complex DeltaSubN = deltas[0]; // Delta z
        Complex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : skippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = iterationPeriod;

        double norm_squared = 0;

        if(iterations != 0 && RefIteration < MaxRefIteration) {
            complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            norm_squared = complex[0].norm_squared();
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
            norm_squared = complex[0].norm_squared();
        }

        Complex pixel = dpixel.plus(refPointSmall);

        for (; iterations < max_iterations; iterations++) {

            //No update values

            if (trap != null) {
                trap.check(complex[0], iterations);
            }

            if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                escaped = true;

                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double res = out_color_algorithm.getResult(object);

                res = getFinalValueOut(res);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
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
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubN);
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

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }


    //Todo add this at some point
    public double iterateFractalWithPerturbationWithoutPeriodicityScaled(Complex[] complex, MantExpComplex dpixel) {

        double reAlignThreshold = 1e300 / power;

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : skippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = iterationPeriod;

        MantExpComplex z = new MantExpComplex();
        double norm_squared  = 0;
        if(iterations != 0 && RefIteration < MaxRefIteration) {
            z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
            complex[0] = z.toComplex();
            norm_squared = complex[0].norm_squared();
        }
        else if(iterations != 0 && ReferencePeriod != 0) {
            RefIteration = RefIteration % ReferencePeriod;
            z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
            complex[0] = z.toComplex();
            norm_squared = complex[0].norm_squared();
        }

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        MantExp S = DeltaSubN.norm();
        double s = S.toDouble();
        double ss = s * s;
        Complex DeltaSubNscaled = DeltaSubN.divide(S).toComplex();
        Complex DeltaSub0scaled = DeltaSub0.divide(S).toComplex();

        boolean isDeltaSub0Zero = DeltaSub0scaled.isZero();

        int nextTinyRefIndex = 0;
        int nextTinyRefIteration = 0;
        int tinyRefPtsLength = tinyRefPts.size();
        do { //find the next tiny ref iteration which is after the skipped iterations
            if(nextTinyRefIndex < tinyRefPtsLength) {
                nextTinyRefIteration = tinyRefPts.get(nextTinyRefIndex);
                nextTinyRefIndex++;
            }
            else {
                nextTinyRefIteration = max_iterations;
            }

        }while (nextTinyRefIteration < RefIteration);

        for (; iterations < max_iterations; iterations++) {

            if(RefIteration == nextTinyRefIteration) {
                //No update values

                if(iterations != 0) {
                    DeltaSubN = new MantExpComplex(S.multiply(DeltaSubNscaled.getRe()), S.multiply(DeltaSubNscaled.getIm()));
                }

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
                    complex[0] = z.toComplex();
                    norm_squared = complex[0].norm_squared();
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                if (z.norm_squared().compareTo(DeltaSubN.norm_squared()) < 0 || RefIteration >= MaxRefIteration) {
                    DeltaSubN = z;
                    RefIteration = 0;
                    nextTinyRefIndex = 0;
                }

                DeltaSubN.Reduce();

                // rescale
                S = DeltaSubN.norm();
                s = S.toDouble();
                ss = s * s;
                DeltaSubNscaled = DeltaSubN.divide(S).toComplex();
                DeltaSub0scaled = DeltaSub0.divide(S).toComplex();

                isDeltaSub0Zero = DeltaSub0scaled.isZero();

                if(nextTinyRefIndex < tinyRefPtsLength) {
                    nextTinyRefIteration = tinyRefPts.get(nextTinyRefIndex);
                    nextTinyRefIndex++;
                }
                else {
                    nextTinyRefIteration = max_iterations;
                }
            }
            else {
                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                Complex DeltaSubNscaledNew = isDeltaSub0Zero ? perturbationFunctionScaled(DeltaSubNscaled, s, RefIteration) : perturbationFunctionScaled(DeltaSubNscaled, DeltaSub0scaled, s, RefIteration);

                double DeltaSubNscaledNormSqr = DeltaSubNscaledNew.norm_squared();
                if(DeltaSubNscaledNormSqr < reAlignThreshold) {
                    DeltaSubNscaled = DeltaSubNscaledNew;
                }
                else {
                    DeltaSubN = new MantExpComplex(S.multiply(DeltaSubNscaledNew.getRe()), S.multiply(DeltaSubNscaledNew.getIm()));
                    S = DeltaSubN.norm();
                    s = S.toDouble();
                    ss = s * s;
                    DeltaSubNscaled = DeltaSubN.divide(S).toComplex();
                    DeltaSub0scaled = DeltaSub0.divide(S).toComplex();

                    isDeltaSub0Zero = DeltaSub0scaled.isZero();

                    DeltaSubNscaledNormSqr = DeltaSubNscaled.norm_squared();
                }


                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                //No Plane influence work
                //No Pre filters work
                if (max_iterations > 1) {
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(DeltaSubNscaled.times(s));
                }
                //No Post filters work

                if (statistic != null) {
                    statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
                }

                norm_squared = complex[0].norm_squared();
                if (norm_squared < DeltaSubNscaledNormSqr * ss || RefIteration >= MaxRefIteration) {
                    DeltaSubNscaled = complex[0];
                    RefIteration = 0;
                    DeltaSubN = new MantExpComplex(DeltaSubNscaled);

                    // rescale
                    S = DeltaSubN.norm();
                    s = S.toDouble();
                    ss = s * s;
                    DeltaSubNscaled = DeltaSubN.divide(S).toComplex();
                    DeltaSub0scaled = DeltaSub0.divide(S).toComplex();

                    isDeltaSub0Zero = DeltaSub0scaled.isZero();

                    //u = DeltaSub0.norm().divide(S).toDouble();
                    nextTinyRefIndex = 0;
                    if(nextTinyRefIndex < tinyRefPtsLength) {
                        nextTinyRefIteration = tinyRefPts.get(nextTinyRefIndex);
                        nextTinyRefIndex++;
                    }
                    else {
                        nextTinyRefIteration = max_iterations;
                    }

                }

            }

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    public double iterateFractalWithPerturbationWithoutPeriodicity(Complex[] complex, MantExpComplex dpixel) {

        MantExpComplex[] deltas = initializePerturbation(dpixel);
        MantExpComplex DeltaSubN = deltas[0]; // Delta z
        MantExpComplex DeltaSub0 = deltas[1]; // Delta c

        iterations = nanomb1SkippedIterations != 0 ? nanomb1SkippedIterations : skippedIterations;

        int RefIteration = iterations;

        int ReferencePeriod = iterationPeriod;

        Complex pixel = dpixel.plus(refPointSmallDeep).toComplex();

        int minExp = -1000;
        int reducedExp = minExp / (int)power;

        DeltaSubN.Reduce();
        long exp = DeltaSubN.getExp();

        boolean useFullFloatExp = ThreadDraw.USE_FULL_FLOATEXP_FOR_DEEP_ZOOM;

        boolean usedDeepCode = false;
        if(useFullFloatExp || (skippedIterations == 0 && exp <= minExp) || (skippedIterations != 0 && exp <= reducedExp)) {
            usedDeepCode = true;

            MantExpComplex z = new MantExpComplex();
            if(iterations != 0 && RefIteration < MaxRefIteration) {
                z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = z.toComplex();
            }
            else if(iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
                complex[0] = z.toComplex();
            }

            for (; iterations < max_iterations; iterations++) {
                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (useFullFloatExp && bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, 0.0, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                    }

                    return res;
                }

                DeltaSubN = perturbationFunction(DeltaSubN, DeltaSub0, RefIteration);

                RefIteration++;

                zold2.assign(zold);
                zold.assign(complex[0]);

                if (max_iterations > 1) {
                    z = getArrayDeepValue(ReferenceDeep, RefIteration).plus_mutable(DeltaSubN);
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

                if(!useFullFloatExp) {
                    if (DeltaSubN.getExp() > reducedExp) {
                        iterations++;
                        break;
                    }
                }
            }
        }

        if(!useFullFloatExp) {
            Complex CDeltaSubN = DeltaSubN.toComplex();
            Complex CDeltaSub0 = DeltaSub0.toComplex();

            boolean isZero = CDeltaSub0.isZero();

            if(!usedDeepCode && iterations != 0 && RefIteration < MaxRefIteration) {
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
            }
            else if(!usedDeepCode && iterations != 0 && ReferencePeriod != 0) {
                RefIteration = RefIteration % ReferencePeriod;
                complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
            }

            double norm_squared = complex[0].norm_squared();

            for (; iterations < max_iterations; iterations++) {

                //No update values

                if (trap != null) {
                    trap.check(complex[0], iterations);
                }

                if (bailout_algorithm2.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, norm_squared, pixel)) {
                    escaped = true;

                    Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                    double res = out_color_algorithm.getResult(object);

                    res = getFinalValueOut(res);

                    if (outTrueColorAlgorithm != null) {
                        setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
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
                    complex[0] = getArrayValue(Reference, RefIteration).plus_mutable(CDeltaSubN);
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

        Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;

    }

    public double iterateFractalArbitraryPrecisionWithoutPeriodicity(BigComplex[] complex, BigComplex pixel) {

        iterations = 0;

        BigComplex start = complex[0];
        BigComplex c0 = complex[1];
        BigComplex zold = new BigComplex();
        BigComplex zold2 = new BigComplex();

        for (; iterations < max_iterations; iterations++) {

            //updateValues(complex);

            //if (trap != null) {
            //    trap.check(complex[0], iterations);
           // }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, MyApfloat.ZERO, pixel)) {
                escaped = true;

                /*Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return out;*/
                return iterations;
            }

            zold2 = zold;
            zold = complex[0];

            //complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            //complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            try {
                function(complex);
            }
            catch (Exception ex) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
            //complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            /*if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }*/
        }

        /*Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;*/
        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    public double iterateFractalArbitraryPrecisionWithoutPeriodicity(BigNumComplex[] complex, BigNumComplex pixel) {

        iterations = 0;

        BigNumComplex start = complex[0];
        BigNumComplex c0 = complex[1];
        BigNumComplex zold = new BigNumComplex();
        BigNumComplex zold2 = new BigNumComplex();
        BigNum zero = new BigNum();

        for (; iterations < max_iterations; iterations++) {

            //updateValues(complex);

            //if (trap != null) {
            //    trap.check(complex[0], iterations);
            // }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start, c0, zero, pixel)) {
                escaped = true;

                /*Object[] object = {iterations, complex[0], zold, zold2, complex[1], start, c0, pixel};
                double out = out_color_algorithm.getResult(object);

                out = getFinalValueOut(out);

                if (outTrueColorAlgorithm != null) {
                    setTrueColorOut(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
                }

                return out;*/
                return iterations;
            }

            zold2 = zold;
            zold = complex[0];

            //complex[1] = planeInfluence.getValue(complex[0], iterations, complex[1], start, zold, zold2, c0, pixel);
            //complex[0] = preFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);
            try {
                function(complex);
            }
            catch (Exception ex) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }
            //complex[0] = postFilter.getValue(complex[0], iterations, complex[1], start, c0, pixel);

            /*if (statistic != null) {
                statistic.insert(complex[0], zold, zold2, iterations, complex[1], start, c0);
            }*/
        }

        /*Object[] object = {complex[0], zold, zold2, complex[1], start, c0, pixel};
        double in = in_color_algorithm.getResult(object);

        in = getFinalValueIn(in);

        if (inTrueColorAlgorithm != null) {
            setTrueColorIn(complex[0], zold, zold2, iterations, complex[1], start, c0, pixel);
        }

        return in;*/
        return ColorAlgorithm.MAXIMUM_ITERATIONS;
    }

    public abstract void calculateJuliaOrbit();

    public abstract double calculateJulia(GenericComplex pixel);

    public abstract Complex calculateJuliaDomain(Complex pixel);

    public String getInitialValue() {

        if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
            if(isJulia) {
                return "c";
            }
            if(defaultInitVal != null) {
                return defaultInitVal.toString();
            }
            return "0.0 + 0.0i";
        }

        return init_val != null ? init_val.toString() : "c";

    }

    public double getConvergentBailout() {

        return 0;

    }

    private void BailoutConditionFactory(int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, double[] plane_transform_center) {

        switch (bailout_test_algorithm) {

            case MainWindow.BAILOUT_CONDITION_CIRCLE:
                if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                    bailout_algorithm2 = new CircleBailoutPreCalcNormCondition(bailout_squared);
                    bailout_algorithm = new CircleBailoutCondition(bailout_squared);
                }
                else {
                    bailout_algorithm2 = bailout_algorithm = new CircleBailoutCondition(bailout_squared);
                }
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
            case MainWindow.BAILOUT_CONDITION_CUSTOM:
                bailout_algorithm2 = bailout_algorithm = new CustomBailoutCondition(bailout);
                break;

        }

        if (SkipBailoutCondition.SKIPPED_ITERATION_COUNT > 0) {
            bailout_algorithm = new SkipBailoutCondition(bailout_algorithm);
            bailout_algorithm2 = new SkipBailoutCondition(bailout_algorithm2);
        }


        bailout_algorithm.setId(bailout_test_algorithm);
        bailout_algorithm2.setId(bailout_test_algorithm);

    }

    public void ConvergentBailoutConditionFactory(int convergent_bailout_test_algorithm, double convergent_bailout, String convergent_bailout_test_user_formula, String convergent_bailout_test_user_formula2, int convergent_bailout_test_comparison, double convergent_n_norm, double[] plane_transform_center) {

        switch (convergent_bailout_test_algorithm) {

            case MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE:
                convergent_bailout_algorithm = new CircleDistanceBailoutCondition(convergent_bailout * convergent_bailout);
                break;
            case MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE:
                convergent_bailout_algorithm = new SquareDistanceBailoutCondition(convergent_bailout);
                break;
            case MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS:
                convergent_bailout_algorithm = new RhombusDistanceBailoutCondition(convergent_bailout);
                break;
            case MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM:
                convergent_bailout_algorithm = new NNormDistanceBailoutCondition(convergent_bailout, convergent_n_norm);
                break;
            case MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT:
                convergent_bailout_algorithm = new NoConvergentBailoutCondition();
                break;
            case MainWindow.CONVERGENT_BAILOUT_CONDITION_USER:
                convergent_bailout_algorithm = new UserConvergentBailoutCondition(convergent_bailout, convergent_bailout_test_user_formula, convergent_bailout_test_user_formula2, convergent_bailout_test_comparison, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                break;

        }

        if (SkipConvergentBailoutCondition.SKIPPED_ITERATION_COUNT > 0) {
            convergent_bailout_algorithm = new SkipConvergentBailoutCondition(convergent_bailout_algorithm);
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
            if(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory()) {
                return;
            }
            statistic = new Equicontinuity(sts.statistic_intensity, sts.useSmoothing, sts.useAverage, log_bailout_squared, false, 0, sts.equicontinuityDenominatorFactor, sts.equicontinuityInvertFactor, sts.equicontinuityDelta);
            return;
        }
        else if(sts.statisticGroup == 3) {
            statistic = new NormalMap(sts.statistic_intensity, power, sts.normalMapHeight, sts.normalMapAngle, sts.normalMapUseSecondDerivative, size, sts.normalMapDEfactor, isJulia, sts.normalMapUseDE, sts.normalMapInvertDE, sts.normalMapColoring, sts.useNormalMap, sts.normalMapDEUpperLimitFactor, sts.normalMapDEAAEffect, sts.normalMapOverrideColoring, sts.normalMapDeFadeAlgorithm);
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
                statistic = new AtomDomain(sts.showAtomDomains, sts.statistic_intensity, sts.atomNormType, sts.atomNNorm);
                break;
            case MainWindow.DISCRETE_LAGRANGIAN_DESCRIPTORS:
                statistic = new DiscreteLagrangianDescriptors(sts.statistic_intensity, sts.lagrangianPower, log_bailout_squared, sts.useSmoothing, sts.useAverage, false, 0, sts.langNormType, sts.langNNorm);
                break;
            case MainWindow.TWIN_LAMPS:
                statistic = new TwinLamps(sts.statistic_intensity, sts.twlFunction, sts.twlPoint);
                break;

        }
    }

    protected double getStatistic(double result, boolean escaped) {

        statValue = escaped ? statistic.getValue() : statistic.getValueNotEscaped();

        if (Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS) {

            if(statistic.getIntensity() == 0) {
                if(statValue == 0) {
                    return result;
                }
                else if(Math.abs(statValue) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
                    return statValue;
                }
            }

            if(Math.abs(statValue) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
                return max_iterations;
            }

            return max_iterations + statValue;
        }

        if(Math.abs(statValue) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            if(statistic.getIntensity() == 0 || (escaped && !statisticIncludeNotEscaped)) {
                return statValue;
            }
            else {
                return max_iterations;
            }
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
                distance = CosineInterpolation.getCoefficient(distance);
                break;
            case 2:
                distance = SqrtInterpolation.getCoefficient(distance);
                break;
            case 3:
                distance = Exponential2Interpolation.getCoefficient(distance);
                break;
            case 4:
                distance = CbrtInterpolation.getCoefficient(distance);
                break;
            case 5:
                distance = FrthrootInterpolation.getCoefficient(distance);
                break;
            case 6:
                distance  = AccelerationInterpolation.getCoefficient(distance);
                break;
            case 7:
                distance = SineInterpolation.getCoefficient(distance);
                break;
            case 8:
                distance = DecelerationInterpolation.getCoefficient(distance);
                break;
            case 9:
                distance = ThirdPolynomialInterpolation.getCoefficient(distance);
                break;
            case 10:
                distance = FifthPolynomialInterpolation.getCoefficient(distance);
                break;
            case 11:
                distance = SmoothTransitionFunctionInterpolation.getCoefficient(distance);
                break;
            case 0:
            default:
                break;
        }
        
        distance = invertTrapHeight ? 1 - distance : distance;
        distance *= trapIntesity;

        trapValue = distance;
        
        if (Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS) {
            if(trapValue == 0) {
                return result;
            }
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
                trap = new PointOrbitTrap(ots.checkType, ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.POINT_SQUARE_TRAP:
                trap = new PointSquareOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.POINT_RHOMBUS_TRAP:
                trap = new PointRhombusOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.countTrapIterations);
                break;
            case MainWindow.CROSS_TRAP:
                trap = new CrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_TRAP:
                trap = new CircleOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_TRAP:
                trap = new SquareOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_TRAP:
                trap = new RhombusOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.POINT_N_NORM_TRAP:
                trap = new PointNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_TRAP:
                trap = new NNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.RE_TRAP:
                trap = new ReOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.IM_TRAP:
                trap = new ImOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_CROSS_TRAP:
                trap = new CircleCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_CROSS_TRAP:
                trap = new SquareCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_CROSS_TRAP:
                trap = new RhombusCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_CROSS_TRAP:
                trap = new NNormCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.CIRCLE_POINT_TRAP:
                trap = new CirclePointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_POINT_TRAP:
                trap = new SquarePointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_POINT_TRAP:
                trap = new RhombusPointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_POINT_TRAP:
                trap = new NNormPointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.N_NORM_POINT_N_NORM_TRAP:
                trap = new NNormPointNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_TRAP:
                trap = new GoldenRatioSpiralOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_POINT_TRAP:
                trap = new GoldenRatioSpiralPointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP:
                trap = new GoldenRatioSpiralPointNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_CROSS_TRAP:
                trap = new GoldenRatioSpiralCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_CIRCLE_TRAP:
                trap = new GoldenRatioSpiralCircleOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_SQUARE_TRAP:
                trap = new GoldenRatioSpiralSquareOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_RHOMBUS_TRAP:
                trap = new GoldenRatioSpiralRhombusOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP:
                trap = new GoldenRatioSpiralNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;              
            case MainWindow.STALKS_TRAP:
                trap = new StalksOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_POINT_TRAP:
                trap = new StalksPointOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_POINT_N_NORM_TRAP:
                trap = new StalksPointNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_CROSS_TRAP:
                trap = new StalksCrossOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_CIRCLE_TRAP:
                trap = new StalksCircleOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_SQUARE_TRAP:
                trap = new StalksSquareOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_RHOMBUS_TRAP:
                trap = new StalksRhombusOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.STALKS_N_NORM_TRAP:
                trap = new StalksNNormOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.IMAGE_TRAP:
                trap = new ImageOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;             
            case MainWindow.ATOM_DOMAIN_TRAP:
                trap = new AtomDomainOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.SQUARE_ATOM_DOMAIN_TRAP:
                trap = new SquareAtomDomainOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.RHOMBUS_ATOM_DOMAIN_TRAP:
                trap = new RhombusAtomDomainOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.countTrapIterations);
                break;
            case MainWindow.NNORM_ATOM_DOMAIN_TRAP:
                trap = new NNormAtomDomainOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapNorm, ots.countTrapIterations);
                break;
            case MainWindow.TEAR_DROP_ORBIT_TRAP:
                trap = new TearDropOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.countTrapIterations);
                break;
            case MainWindow.SUPER_FORMULA_ORBIT_TRAP:
                trap = new SuperFormulaOrbitTrap(ots.checkType,ots.trapPoint[0], ots.trapPoint[1], ots.trapWidth, ots.sfm1, ots.sfm2, ots.sfn1, ots.sfn2, ots.sfn3, ots.sfa, ots.sfb, ots.countTrapIterations);
                break;

        }

        trap.setJulia(isJulia);
        trap.setUsesPerturbation(ThreadDraw.PERTURBATION_THEORY && supportsPerturbationTheory());
        trap.setSkipTrapCheckForIterations(ots.skipTrapCheckForIterations);

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

    protected void setTrueColorOut(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        trueColorValue = outTrueColorAlgorithm.createColor(z, zold, zold2, iterations, c, start, c0, pixel, statValue, trapValue,true);
        hasTrueColor = true;

    }

    protected void setTrueColorIn(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        trueColorValue = inTrueColorAlgorithm.createColor(z, zold, zold2, iterations, c, start, c0, pixel, statValue, trapValue, false);
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

    public void setJuliterOptions(boolean juliter, int juliterIterations, boolean juliterIncludeInitialIterations) {
        this.juliter = juliter;
        this.juliterIterations = juliterIterations;
        this.juliterIncludeInitialIterations = juliterIncludeInitialIterations;

        if(!juliter && !(init_val instanceof DefaultInitialValue)) {
            init_val = new DefaultInitialValue();
        }

        if(statistic != null && statistic instanceof NormalMap) {
            ((NormalMap)statistic).setJuliterOptions(juliter, juliterIterations);
        }
    }

    public boolean isJulia() {
        return isJulia;
    }

    public static void clearApproximation() {
        coefficients = null;
        if(B != null) {
            B.b = null;
            B.bdeep = null;
            B = null;
        }
        SACalculationTime = 0;
        BLACalculationTime = 0;
        Nanomb1CalculationTime = 0;
        skippedIterations = 0;
    }

    public static void clearUnusedReferences(boolean isDeep, boolean supportsBla) {

        if(!isDeep) {
            if(ReferenceDeep != null) {
                ReferenceDeep.clear();
            }
            ReferenceDeep = null;

            if(SecondReferenceDeep != null) {
                SecondReferenceDeep.clear();
            }
            SecondReferenceDeep = null;

            if(ReferenceSubCpDeep != null) {
                ReferenceSubCpDeep.clear();
            }
            ReferenceSubCpDeep = null;

            if(SecondReferenceSubCpDeep != null) {
                SecondReferenceSubCpDeep.clear();
            }
            SecondReferenceSubCpDeep = null;

            if(PrecalculatedTerms2Deep != null) {
                PrecalculatedTerms2Deep.clear();
            }
            PrecalculatedTerms2Deep = null;

            if(SecondPrecalculatedTermsDeep != null) {
                SecondPrecalculatedTermsDeep.clear();
            }
            SecondPrecalculatedTermsDeep = null;

            if(SecondPrecalculatedTerms2Deep != null) {
                SecondPrecalculatedTerms2Deep.clear();
            }
            SecondPrecalculatedTerms2Deep = null;

            if(PrecalculatedTermsDeep != null) {
                PrecalculatedTermsDeep.clear();
            }
            PrecalculatedTermsDeep = null;

        }

        if((isDeep && ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBla)) {
            Reference = null;
        }
    }

    public static void clearReferences(boolean clearJuliaReference) {

        DetectedPeriod = 0;
        clearApproximation();
        ReferenceCalculationTime = 0;
        SecondReferenceCalculationTime = 0;
        refPoint = null;
        refPointSmall = null;
        refPointSmallDeep = null;
        lastZValue = null;
        secondTolastZValue = null;
        thirdTolastZValue = null;
        RefType = "";
        Reference = null;
        MaxRefIteration = 0;

        if(clearJuliaReference) {
            lastZ2Value = null;
            secondTolastZ2Value = null;
            thirdTolastZ2Value = null;
            MaxRef2Iteration = 0;
        }

        if(clearJuliaReference) {
            SecondReference = null;
            SecondReferenceSubCp = null;
            SecondPrecalculatedTerms = null;
            SecondPrecalculatedTerms2 = null;
        }

        ReferenceSubCp = null;
        PrecalculatedTerms = null;
        PrecalculatedTerms2 = null;

        if(ReferenceDeep != null) {
            ReferenceDeep.clear();
        }
        ReferenceDeep = null;

        if(clearJuliaReference) {
            if (SecondReferenceDeep != null) {
                SecondReferenceDeep.clear();
            }
            SecondReferenceDeep = null;

            if (SecondReferenceSubCpDeep != null) {
                SecondReferenceSubCpDeep.clear();
            }
            SecondReferenceSubCpDeep = null;

            if (SecondPrecalculatedTermsDeep != null) {
                SecondPrecalculatedTermsDeep.clear();
            }
            SecondPrecalculatedTermsDeep = null;

            if (SecondPrecalculatedTerms2Deep != null) {
                SecondPrecalculatedTerms2Deep.clear();
            }
            SecondPrecalculatedTerms2Deep = null;
        }

        if(ReferenceSubCpDeep != null) {
            ReferenceSubCpDeep.clear();
        }
        ReferenceSubCpDeep = null;

        if(PrecalculatedTerms2Deep != null) {
            PrecalculatedTerms2Deep.clear();
        }
        PrecalculatedTerms2Deep = null;

        if(PrecalculatedTermsDeep != null) {
            PrecalculatedTermsDeep.clear();
        }
        PrecalculatedTermsDeep = null;

        tinyRefPts.clear();

        nanomb1 = null;
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

    protected static boolean isLastTermNotNegligible(long[] magCoeff, long magDiffThreshold, int lastIndex) {
        long magLast = magCoeff[lastIndex];

        for(int k=0; k < lastIndex; k++) {
            if (magCoeff[k]-magLast<magDiffThreshold) return true;
        }
        return false;
    }

    public String getRefType() {
        return this.getClass().getName();
    }

    public void setPertubationOption(boolean perturbation, double[] perturbation_vals, boolean variable_perturbation, int user_perturbation_algorithm, String perturbation_user_formula, String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, double[] plane_transform_center) {
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
    }

    public void setInitialValueOption(boolean init_value, double[] initial_vals, boolean variable_init_value, int user_initial_value_algorithm, String initial_value_user_formula, String[] user_initial_value_conditions, String[] user_initial_value_condition_formula, double[] plane_transform_center) {
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
        }

        if(trap != null && init_val != null) {
            trap.setUsesStaticInitVal(init_val.isStatic());
        }
    }

    public Complex getBlaA(Complex z) {
        return null;
    }

    public MantExpComplex getBlaA(MantExpComplex z) {
        return null;
    }

    public static Complex getArrayValue(double[] array, int index) {
        int baseIndex = index << 1;
        return new Complex(array[baseIndex], array[baseIndex + 1]);
    }

    protected static void setArrayValue(double[] array, int index, Complex val) {
        int baseIndex = index << 1;
        array[baseIndex] = val.getRe();
        array[baseIndex + 1] = val.getIm();
    }

    public static MantExpComplex getArrayDeepValue(DeepReference array, int index) {
        int baseIndex = index << 1;
        return new MantExpComplex(array.exps[index], array.mants[baseIndex], array.mants[baseIndex + 1]);
    }

    protected static void setArrayDeepValue(DeepReference array, int index, MantExpComplex val) {
        int baseIndex = index << 1;
        array.exps[index] = val.getExp();
        array.mants[baseIndex] = val.getMantissaReal();
        array.mants[baseIndex + 1] = val.getMantissaImag();
    }

    protected static MantExpComplex getSACoefficient(int term, int i) {

        int dataIndex = i % max_data;
        int index = dataIndex * SATerms + term;
        int baseIndex = index << 1;
        return new MantExpComplex(coefficients.exps[index], coefficients.mants[baseIndex], coefficients.mants[baseIndex + 1]);

    }

    protected static void setSACoefficient(int term, int i, MantExpComplex val) {

        int dataIndex = i % max_data;
        int index = dataIndex * SATerms + term;
        int baseIndex = index << 1;
        coefficients.exps[index] = val.getExp();
        coefficients.mants[baseIndex] = val.getMantissaReal();
        coefficients.mants[baseIndex + 1] = val.getMantissaImag();

    }

    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;

        if(statistic != null && statistic instanceof NormalMap) {
            ((NormalMap)statistic).setFunctionId(functionId);
        }

    }

    public boolean applyPlaneOnJulia() {
        return false;
    }

    public void setJuliaMap(boolean isJuliaMap) {
        this.isJuliaMap = isJuliaMap;
    }

    public boolean supportsBignum() { return false;}
    public boolean supportsMpfrBignum() { return false;}

    public boolean supportsScaledIterations() { return false;}

    public void setPeriod(int period) {
        iterationPeriod = period;
    }

    public int getReferenceMaxIterations() {
        boolean isBLAInUse = ThreadDraw.APPROXIMATION_ALGORITHM == 2 && supportsBilinearApproximation();
        boolean isNanoMb1InUse = ThreadDraw.APPROXIMATION_ALGORITHM == 3 && supportsNanomb1();

        if(isBLAInUse) {
            return iterationPeriod != 0 ? iterationPeriod + 1 : max_iterations;
        }
        else if(isNanoMb1InUse) {
            return iterationPeriod + 1;
        }

        return max_iterations;
    }

    public int getReferenceLength() {
        if(Reference != null) {
            return Reference.length >> 1;
        }

        if(ReferenceDeep != null) {
            return ReferenceDeep.length();
        }

        return 0;
    }

    public int getSecondReferenceLength() {
        if(SecondReference != null) {
            return SecondReference.length >> 1;
        }

        if(SecondReferenceDeep != null) {
            return SecondReferenceDeep.length();
        }

        return 0;
    }

    public int getPeriod() {
        return iterationPeriod;
    }

    public void updateTrapsWithInitValData() {
        if(trap != null && init_val != null) {
            trap.setUsesStaticInitVal(init_val.isStatic());
        }
    }

    public void setSeed(BigComplex seed) {

    }

}
