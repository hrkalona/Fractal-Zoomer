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
package fractalzoomer.functions;

import fractalzoomer.planes.math.AbsPlane;
import fractalzoomer.bailout_conditions.BailoutCondition;
import fractalzoomer.bailout_conditions.CircleBailoutCondition;
import fractalzoomer.bailout_conditions.FieldLinesBailoutCondition;
import fractalzoomer.core.Complex;
import fractalzoomer.planes.math.trigonometric.CosPlane;
import fractalzoomer.planes.math.trigonometric.CoshPlane;
import fractalzoomer.planes.math.trigonometric.CotPlane;
import fractalzoomer.planes.math.trigonometric.CothPlane;
import fractalzoomer.planes.math.ExpPlane;
import fractalzoomer.bailout_conditions.HalfplaneBailoutCondition;
import fractalzoomer.bailout_conditions.NNormBailoutCondition;
import fractalzoomer.bailout_conditions.RhombusBailoutCondition;
import fractalzoomer.main.MainWindow;
import fractalzoomer.fractal_options.Rotation;
import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.InColorAlgorithm;
import fractalzoomer.planes.general.InversedLambdaPlane;
import fractalzoomer.planes.general.InversedMuPlane;
import fractalzoomer.planes.general.InversedMu2Plane;
import fractalzoomer.planes.general.InversedMu3Plane;
import fractalzoomer.planes.general.InversedMu4Plane;
import fractalzoomer.planes.math.LogPlane;
import fractalzoomer.planes.general.LambdaPlane;
import fractalzoomer.planes.general.MuSquaredPlane;
import fractalzoomer.planes.Plane;
import fractalzoomer.planes.general.MuPlane;
import fractalzoomer.bailout_conditions.StripBailoutCondition;
import fractalzoomer.bailout_conditions.SquareBailoutCondition;
import fractalzoomer.bailout_conditions.UserBailoutCondition;
import fractalzoomer.fractal_options.orbit_traps.CircleOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.CrossOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.OrbitTrap;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.fractal_options.orbit_traps.CircleCrossOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.CirclePointOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.ImOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.NNormCrossOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.NNormOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.NNormPointNNormOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.NNormPointOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.PointNNormOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.PointOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.PointRhombusOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.PointSquareOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.ReOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.RhombusCrossOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.RhombusOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.RhombusPointOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.SquareCrossOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.SquareOrbitTrap;
import fractalzoomer.fractal_options.orbit_traps.SquarePointOrbitTrap;
import fractalzoomer.in_coloring_algorithms.AtanReTimesImTimesAbsReTimesAbsIm;
import fractalzoomer.in_coloring_algorithms.CosMag;
import fractalzoomer.in_coloring_algorithms.DecompositionLike;
import fractalzoomer.in_coloring_algorithms.MagTimesCosReSquared;
import fractalzoomer.in_coloring_algorithms.MaximumIterations;
import fractalzoomer.in_coloring_algorithms.ReDivideIm;
import fractalzoomer.in_coloring_algorithms.SinReSquaredMinusImSquared;
import fractalzoomer.in_coloring_algorithms.Squares;
import fractalzoomer.in_coloring_algorithms.Squares2;
import fractalzoomer.in_coloring_algorithms.UserConditionalInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.UserInColorAlgorithm;
import fractalzoomer.in_coloring_algorithms.ZMag;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import fractalzoomer.out_coloring_algorithms.Banded;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.BinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.Biomorphs;
import fractalzoomer.out_coloring_algorithms.ColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTime;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm1;
import fractalzoomer.out_coloring_algorithms.EscapeTimeAlgorithm2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeColorDecomposition;
import fractalzoomer.out_coloring_algorithms.EscapeTimeEscapeRadius;
import fractalzoomer.out_coloring_algorithms.EscapeTimeFieldLines;
import fractalzoomer.out_coloring_algorithms.EscapeTimeFieldLines2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger2;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger3;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger4;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGaussianInteger5;
import fractalzoomer.out_coloring_algorithms.EscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRe;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.EscapeTimePlusRePlusImPlusReDivideIm;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition;
import fractalzoomer.out_coloring_algorithms.SmoothBinaryDecomposition2;
import fractalzoomer.out_coloring_algorithms.SmoothBiomorphs;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTime;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeFieldLines;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeFieldLines2;
import fractalzoomer.out_coloring_algorithms.SmoothEscapeTimeGrid;
import fractalzoomer.out_coloring_algorithms.UserConditionalOutColorAlgorithm;
import fractalzoomer.out_coloring_algorithms.UserOutColorAlgorithm;
import fractalzoomer.parser.Parser;
import fractalzoomer.planes.user_plane.UserPlane;
import fractalzoomer.planes.user_plane.UserPlaneConditional;
import fractalzoomer.planes.distort.KaleidoscopePlane;
import fractalzoomer.planes.distort.PinchPlane;
import fractalzoomer.planes.distort.RipplesPlane;
import fractalzoomer.planes.fold.FoldInPlane;
import fractalzoomer.planes.fold.FoldOutPlane;
import fractalzoomer.planes.fold.FoldRightPlane;
import fractalzoomer.planes.fold.FoldUpPlane;
import fractalzoomer.planes.general.BipolarPlane;
import fractalzoomer.planes.general.InversedBipolarPlane;
import fractalzoomer.planes.general.InversedLambda2Plane;
import fractalzoomer.planes.general.MuSquaredImaginaryPlane;
import fractalzoomer.planes.math.FactorialPlane;
import fractalzoomer.planes.math.GammaFunctionPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACosPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACoshPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACotPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACothPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACscPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ACschPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ASecPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ASechPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ASinPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ASinhPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ATanPlane;
import fractalzoomer.planes.math.inverse_trigonometric.ATanhPlane;
import fractalzoomer.planes.math.trigonometric.CscPlane;
import fractalzoomer.planes.math.trigonometric.CschPlane;
import fractalzoomer.planes.math.trigonometric.SecPlane;
import fractalzoomer.planes.math.trigonometric.SechPlane;
import fractalzoomer.planes.math.SqrtPlane;
import fractalzoomer.planes.math.trigonometric.TanPlane;
import fractalzoomer.planes.math.trigonometric.SinhPlane;
import fractalzoomer.planes.math.trigonometric.SinPlane;
import fractalzoomer.planes.math.trigonometric.TanhPlane;
import fractalzoomer.planes.newton.Newton3Plane;
import fractalzoomer.planes.newton.Newton4Plane;
import fractalzoomer.planes.newton.NewtonGeneralized3Plane;
import fractalzoomer.planes.newton.NewtonGeneralized8Plane;
import fractalzoomer.planes.distort.ShearPlane;
import fractalzoomer.planes.distort.TwirlPlane;
import fractalzoomer.planes.fold.FoldDownPlane;
import fractalzoomer.planes.fold.FoldLeftPlane;
import fractalzoomer.planes.general.CircleInversionPlane;
import fractalzoomer.planes.general.InflectionPlane;
import fractalzoomer.planes.general.MuVariationPlane;
import fractalzoomer.planes.math.ErfPlane;
import fractalzoomer.planes.math.RiemannZetaPlane;
import fractalzoomer.utils.ColorAlgorithm;
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
    protected OutColorAlgorithm out_color_algorithm;
    protected InColorAlgorithm in_color_algorithm;
    protected BailoutCondition bailout_algorithm;
    protected Plane plane;
    protected Rotation rotation;
    protected PlanePointOption init_val;
    protected PlanePointOption pertur_val;
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

    public Fractal(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.periodicity_checking = periodicity_checking;
        
        globalVars = createGlobalVars();

        if (ots.useTraps) {
            TrapFactory(ots);
        }

        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

        PlaneFactory(plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        BailoutConditionFactory(bailout_test_algorithm, bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, n_norm, plane_transform_center);
        
    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);
        
        globalVars = createGlobalVars();

        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

        PlaneFactory(plane_type, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

        pixel_orbit = plane.transform(rotation.rotate(pixel_orbit));

    }

    protected abstract void function(Complex[] complex);

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

    public double calculateFractal(Complex pixel) {

        resetGlobalVars();

        return periodicity_checking ? calculateFractalWithPeriodicity(plane.transform(rotation.rotate(pixel))) : calculateFractalWithoutPeriodicity(plane.transform(rotation.rotate(pixel)));

    }

    public double[] calculateFractal3D(Complex pixel) {

        resetGlobalVars();

        return periodicity_checking ? calculateFractal3DWithPeriodicity(plane.transform(rotation.rotate(pixel))) : calculateFractal3DWithoutPeriodicity(plane.transform(rotation.rotate(pixel)));

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

    protected double calculateFractalWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                return ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

        }

        return ColorAlgorithm.MAXIMUM_ITERATIONS;

    }

    protected double calculateFractalWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                return out_color_algorithm.getResult(object);
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        return in_color_algorithm.getResult(object);

        /* int iterations = 0; 
        
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

    protected double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);

        double temp;

        for (; iterations < max_iterations; iterations++) {
            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                temp = out_color_algorithm.getResult(object);
                double[] array = {OutColorAlgorithm.transformResultToHeight(temp, max_iterations), temp};
                return array;
            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

            if (periodicityCheck(complex[0])) {
                double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
                return array;
            }

        }

        double[] array = {max_iterations, ColorAlgorithm.MAXIMUM_ITERATIONS};
        return array;

    }

    protected double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {

        int iterations = 0;

        if (trap != null) {
            trap.initialize();
        }

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        Complex zold2 = new Complex();
        Complex start = new Complex(complex[0]);
        double temp;

        for (; iterations < max_iterations; iterations++) {

            if (trap != null) {
                trap.check(complex[0]);
            }

            if (bailout_algorithm.escaped(complex[0], zold, zold2, iterations, complex[1], start)) {
                Object[] object = {iterations, complex[0], zold, zold2, complex[1], start};
                temp = out_color_algorithm.getResult(object);
                double[] array = {OutColorAlgorithm.transformResultToHeight(temp, max_iterations), temp};
                return array;

            }
            zold2.assign(zold);
            zold.assign(complex[0]);
            function(complex);

        }

        Object[] object = {complex[0], zold, zold2, complex[1], start};
        temp = in_color_algorithm.getResult(object);
        double[] array = {InColorAlgorithm.transformResultToHeight(temp, max_iterations), temp};
        return array;

    }

    /*
     int iterations = 0; 
        
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
    public void calculateFractalOrbit() {
        int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel_orbit)));//z
        complex[1] = new Complex(pixel_orbit);//c

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

    /*public ArrayList<Complex> calculateFractalOrbit2(Complex pixel) {
     int iterations = 0;

     pertur_val.setGlobalVars(vars);
     init_val.setGlobalVars(vars);
    
     Complex[] complex = new Complex[2];
     complex[0] = new Complex(pertur_val.getValue(init_val.getValue(pixel)));//z
     complex[1] = new Complex(pixel);//c
        
     ArrayList<Complex> complex_orbit = new ArrayList<Complex>();
     complex_orbit.add(pixel);

     Complex temp = null;

     for(; iterations < max_iterations; iterations++) {
     function(complex);
     temp = rotation.rotateInverse(complex[0]);

     if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
     break;
     }

     complex_orbit.add(temp);
     }
        
     return complex_orbit;

     }*/
    public Complex calculateFractalDomain(Complex pixel) {

        resetGlobalVars();

        return iterateFractalDomain(plane.transform(rotation.rotate(pixel)));

    }

    protected Complex iterateFractalDomain(Complex pixel) {

        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getValue(init_val.getValue(pixel)));

        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        for (; iterations < max_iterations; iterations++) {

            function(complex);

        }

        return complex[0];

    }

    public abstract void calculateJuliaOrbit();

    public abstract double calculateJulia(Complex pixel);

    public abstract double[] calculateJulia3D(Complex pixel);

    public abstract Complex calculateJuliaDomain(Complex pixel);

    public double getXCenter() {

        return xCenter;

    }

    public double getYCenter() {

        return yCenter;

    }

    public double getSize() {

        return size;

    }

    public int getMaxIterations() {

        return max_iterations;

    }

    public Complex getTransformedNumber(Complex z) {

        return plane.transform(z);

    }

    public String getInitialValue() {

        return init_val != null ? init_val.toString() : "c";

    }

    public OutColorAlgorithm getOutColorAlgorithm() {

        return out_color_algorithm;

    }

    public double getConvergentBailout() {

        return 0;

    }

    private void BailoutConditionFactory(int bailout_test_algorithm, double bailout, String bailout_test_user_formula, String bailout_test_user_formula2, int bailout_test_comparison, double n_norm, double[] plane_transform_center) {

        switch (bailout_test_algorithm) {

            case MainWindow.BAILOUT_CONDITION_CIRCLE:
                bailout_algorithm = new CircleBailoutCondition(bailout_squared);
                break;
            case MainWindow.BAILOUT_CONDITION_SQUARE:
                bailout_algorithm = new SquareBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_RHOMBUS:
                bailout_algorithm = new RhombusBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_STRIP:
                bailout_algorithm = new StripBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_HALFPLANE:
                bailout_algorithm = new HalfplaneBailoutCondition(bailout);
                break;
            case MainWindow.BAILOUT_CONDITION_NNORM:
                bailout_algorithm = new NNormBailoutCondition(bailout, n_norm);
                break;
            case MainWindow.BAILOUT_CONDITION_USER:
                bailout_algorithm = new UserBailoutCondition(bailout, bailout_test_user_formula, bailout_test_user_formula2, bailout_test_comparison, max_iterations, xCenter, yCenter, size, plane_transform_center, globalVars);
                break;
            case MainWindow.BAILOUT_CONDITION_FIELD_LINES:
                bailout_algorithm = new FieldLinesBailoutCondition(bailout);
                break;

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
        }

    }

    protected void OutColoringAlgorithmFactory(int out_coloring_algorithm, boolean smoothing, int escaping_smooth_algorithm, int user_out_coloring_algorithm, String outcoloring_formula, String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double[] plane_transform_center) {
        switch (out_coloring_algorithm) {

            case MainWindow.ESCAPE_TIME:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTime();
                } else {
                    out_color_algorithm = new SmoothEscapeTime(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BINARY_DECOMPOSITION2:
                if (!smoothing) {
                    out_color_algorithm = new BinaryDecomposition2();
                } else {
                    out_color_algorithm = new SmoothBinaryDecomposition2(Math.log(bailout_squared), escaping_smooth_algorithm);
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
                    out_color_algorithm = new SmoothBiomorphs(Math.log(bailout_squared), bailout, escaping_smooth_algorithm);
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
                out_color_algorithm = new EscapeTimeEscapeRadius(Math.log(bailout_squared));
                break;
            case MainWindow.ESCAPE_TIME_GRID:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeGrid(Math.log(bailout_squared));
                } else {
                    out_color_algorithm = new SmoothEscapeTimeGrid(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.BANDED:
                out_color_algorithm = new Banded();
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines(Math.log(bailout_squared));
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines(Math.log(bailout_squared), escaping_smooth_algorithm);
                }
                break;
            case MainWindow.ESCAPE_TIME_FIELD_LINES2:
                if (!smoothing) {
                    out_color_algorithm = new EscapeTimeFieldLines2(Math.log(bailout_squared));
                } else {
                    out_color_algorithm = new SmoothEscapeTimeFieldLines2(Math.log(bailout_squared), escaping_smooth_algorithm);
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
                in_color_algorithm = new DecompositionLike();
                break;
            case MainWindow.RE_DIVIDE_IM:
                in_color_algorithm = new ReDivideIm();
                break;
            case MainWindow.COS_MAG:
                in_color_algorithm = new CosMag();
                break;
            case MainWindow.MAG_TIMES_COS_RE_SQUARED:
                in_color_algorithm = new MagTimesCosReSquared();
                break;
            case MainWindow.SIN_RE_SQUARED_MINUS_IM_SQUARED:
                in_color_algorithm = new SinReSquaredMinusImSquared();
                break;
            case MainWindow.ATAN_RE_TIMES_IM_TIMES_ABS_RE_TIMES_ABS_IM:
                in_color_algorithm = new AtanReTimesImTimesAbsReTimesAbsIm();
                break;
            case MainWindow.SQUARES:
                in_color_algorithm = new Squares();
                break;
            case MainWindow.SQUARES2:
                in_color_algorithm = new Squares2();
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

    private void TrapFactory(OrbitTrapSettings ots) {

        switch (ots.trapType) {
            case MainWindow.POINT_TRAP:
                trap = new PointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength);
                break;
            case MainWindow.POINT_SQUARE_TRAP:
                trap = new PointSquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength);
                break;
            case MainWindow.POINT_RHOMBUS_TRAP:
                trap = new PointRhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength);
                break;
            case MainWindow.CROSS_TRAP:
                trap = new CrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.CIRCLE_TRAP:
                trap = new CircleOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.SQUARE_TRAP:
                trap = new SquareOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.RHOMBUS_TRAP:
                trap = new RhombusOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.POINT_N_NORM_TRAP:
                trap = new PointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapNorm);
                break;
            case MainWindow.N_NORM_TRAP:
                trap = new NNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm);
                break;
            case MainWindow.RE_TRAP:
                trap = new ReOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.IM_TRAP:
                trap = new ImOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.CIRCLE_CROSS_TRAP:
                trap = new CircleCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.SQUARE_CROSS_TRAP:
                trap = new SquareCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.RHOMBUS_CROSS_TRAP:
                trap = new RhombusCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.lineType);
                break;
            case MainWindow.N_NORM_CROSS_TRAP:
                trap = new NNormCrossOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm, ots.lineType);
                break;
            case MainWindow.CIRCLE_POINT_TRAP:
                trap = new CirclePointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.SQUARE_POINT_TRAP:
                trap = new SquarePointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.RHOMBUS_POINT_TRAP:
                trap = new RhombusPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth);
                break;
            case MainWindow.N_NORM_POINT_TRAP:
                trap = new NNormPointOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm);
                break;
            case MainWindow.N_NORM_POINT_N_NORM_TRAP:
                trap = new NNormPointNNormOrbitTrap(ots.trapPoint[0], ots.trapPoint[1], ots.trapLength, ots.trapWidth, ots.trapNorm);
                break;
        }

    }

    public OrbitTrap getOrbitTrap() {

        return trap;

    }
    
}
