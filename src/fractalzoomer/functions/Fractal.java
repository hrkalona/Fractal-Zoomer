package fractalzoomer.functions;


import fractalzoomer.planes.math.AbsPlane;
import fractalzoomer.bailout_tests.BailoutTest;
import fractalzoomer.bailout_tests.CircleBailoutTest;
import fractalzoomer.core.Complex;
import fractalzoomer.planes.math.trigonometric.CosPlane;
import fractalzoomer.planes.math.trigonometric.CoshPlane;
import fractalzoomer.planes.math.trigonometric.CotPlane;
import fractalzoomer.planes.math.trigonometric.CothPlane;
import fractalzoomer.planes.math.ExpPlane;
import fractalzoomer.bailout_tests.HalfplaneBailoutTest;
import fractalzoomer.bailout_tests.NNormBailoutTest;
import fractalzoomer.bailout_tests.RhombusBailoutTest;
import fractalzoomer.fractal_options.DefaultPerturbation;
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
import fractalzoomer.bailout_tests.StripBailoutTest;
import fractalzoomer.bailout_tests.SquareBailoutTest;
import fractalzoomer.bailout_tests.UserBailoutTest;
import fractalzoomer.fractal_options.DefaultInitialValue;
import fractalzoomer.planes.UserPlane;
import fractalzoomer.planes.distort.KaleidoscopePlane;
import fractalzoomer.planes.distort.PinchPlane;
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
import fractalzoomer.planes.general.MuVariationPlane;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
  protected BailoutTest bailout_algorithm;
  protected Plane plane;
  protected Rotation rotation;
  protected DefaultInitialValue init_val;
  protected DefaultPerturbation pertur_val;
  protected boolean periodicity_checking;
  protected ArrayList<Complex> complex_orbit;
  protected Complex pixel_orbit;
  protected int check;
  protected int check_counter;
  protected int update;
  protected int update_counter;
  protected Complex period;


    public Fractal(double xCenter, double yCenter, double size, int max_iterations, int bailout_test_algorithm, double bailout,  String bailout_test_user_formula, int bailout_test_comparison, double n_norm, boolean periodicity_checking, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.bailout = bailout;
        bailout_squared = bailout * bailout;
        this.periodicity_checking = periodicity_checking;
   
        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);

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
                plane = new UserPlane(user_plane);
                break;
            case MainWindow.GAMMA_PLANE:
                plane = new GammaFunctionPlane();
                break;
            case MainWindow.FACT_PLANE:
                plane = new FactorialPlane();
                break;
            case MainWindow.BIPOLAR_PLANE:
                plane = new BipolarPlane();
                break;
            case MainWindow.INVERSED_BIPOLAR_PLANE:
                plane = new InversedBipolarPlane();
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
        }
        
   
        switch (bailout_test_algorithm) {
            
            case MainWindow.BAILOUT_TEST_CIRCLE:
                bailout_algorithm = new CircleBailoutTest(bailout_squared);
                break;
            case MainWindow.BAILOUT_TEST_SQUARE:
                bailout_algorithm = new SquareBailoutTest(bailout);
                break;
            case MainWindow.BAILOUT_TEST_RHOMBUS:
                bailout_algorithm = new RhombusBailoutTest(bailout);
                break;
            case MainWindow.BAILOUT_TEST_STRIP:
                bailout_algorithm = new StripBailoutTest(bailout);
                break;
            case MainWindow.BAILOUT_TEST_HALFPLANE:
                bailout_algorithm = new HalfplaneBailoutTest(bailout);
                break;
            case MainWindow.BAILOUT_TEST_NNORM:
                bailout_algorithm = new NNormBailoutTest(bailout, n_norm);
                break;
            case MainWindow.BAILOUT_TEST_USER:
                bailout_algorithm = new UserBailoutTest(bailout, bailout_test_user_formula, bailout_test_comparison);
                break;
                
        }
        
        

    }

    //orbit
    public Fractal(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double [] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.size = size;
        this.max_iterations = max_iterations;
        this.complex_orbit = complex_orbit;
        pixel_orbit = this.complex_orbit.get(0);
        
        rotation = new Rotation(rotation_vals[0], rotation_vals[1], rotation_center[0], rotation_center[1]);
        
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
                plane = new UserPlane(user_plane);
                break;
            case MainWindow.GAMMA_PLANE:
                plane = new GammaFunctionPlane();
                break;
            case MainWindow.FACT_PLANE:
                plane = new FactorialPlane();
                break;
            case MainWindow.BIPOLAR_PLANE:
                plane = new BipolarPlane();
                break;
            case MainWindow.INVERSED_BIPOLAR_PLANE:
                plane = new InversedBipolarPlane();
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
       
        }
        
        pixel_orbit = plane.getPixel(rotation.getPixel(pixel_orbit, false));

    }

    protected abstract void function(Complex[] complex);

    protected double getPeriodSize() {

        return 1e-13;

    }

    protected final boolean periodicityCheck(Complex z) {

        
        //Check for period
        if(z.distance_squared(period) < getPeriodSize()) {
            return true;
        }

                //Update history
        if(check == check_counter) {
            check_counter = 0;

            //Double the value of check
            if(update == update_counter) {
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
 
        return periodicity_checking ? calculateFractalWithPeriodicity(plane.getPixel(rotation.getPixel(pixel, false))) : calculateFractalWithoutPeriodicity(plane.getPixel(rotation.getPixel(pixel, false)));
 
    }
    
    public double[] calculateFractal3D(Complex pixel) {
        
        return periodicity_checking ? calculateFractal3DWithPeriodicity(plane.getPixel(rotation.getPixel(pixel, false))) : calculateFractal3DWithoutPeriodicity(plane.getPixel(rotation.getPixel(pixel, false)));

    }

    public double calculateFractalWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                return max_iterations;
            }

        }

        return max_iterations;

    }

    
    public double calculateFractalWithoutPeriodicity(Complex pixel) {
        
        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        

        Complex zold = new Complex();

        for (; iterations < max_iterations; iterations++) {
                  
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                return out_color_algorithm.getResult(object);
            }
            zold.assign(complex[0]);
            function(complex);
        
        }
  
        
        Object[] object = {max_iterations, complex[0]};
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
        
        return max_iterations;*/

    }
    
    public double[] calculateFractal3DWithPeriodicity(Complex pixel) {

        int iterations = 0;

        check = 3;
        check_counter = 0;

        update = 10;
        update_counter = 0;

        period = new Complex();

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c

        Complex zold = new Complex();
        
        double temp;

        for (; iterations < max_iterations; iterations++) {
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                temp = out_color_algorithm.getResult(object);
                double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                return array;
            }
            zold.assign(complex[0]);
            function(complex);

            if(periodicityCheck(complex[0])) {
                double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
                return array;
            }

        }

        double[] array = {40 * Math.log(max_iterations + 1) - 100, max_iterations};
        return array;

    }
    
    public double[] calculateFractal3DWithoutPeriodicity(Complex pixel) {
        
        int iterations = 0;

        Complex tempz = new Complex(pertur_val.getPixel(init_val.getPixel(pixel)));
        
        Complex[] complex = new Complex[2];
        complex[0] = tempz;//z
        complex[1] = new Complex(pixel);//c
        

        Complex zold = new Complex();
        double temp;

        for (; iterations < max_iterations; iterations++) {
                  
            if(bailout_algorithm.escaped(complex[0])) {
                Object[] object = {iterations, complex[0], zold};
                temp = out_color_algorithm.getResult(object);
                double[] array = {40 * Math.log(temp - 100799) - 100, temp};
                return array;
               
            }
            zold.assign(complex[0]);
            function(complex);
        
        }
  
        
        Object[] object = {max_iterations, complex[0]};
        temp = in_color_algorithm.getResult(object);
        double result = temp == max_iterations ? max_iterations : max_iterations + temp - 100820;
        double[] array = {40 * Math.log(result + 1) - 100, temp};
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
        
        return max_iterations;
     */
  
    public void calculateFractalOrbit() {
      int iterations = 0;

        Complex[] complex = new Complex[2];
        complex[0] = new Complex(pertur_val.getPixel(init_val.getPixel(pixel_orbit)));//z
        complex[1] = new Complex(pixel_orbit);//c
        

        Complex temp = null;
        
        for (; iterations < max_iterations; iterations++) {
           function(complex);
           temp = rotation.getPixel(complex[0], true);
           
           if(Double.isNaN(temp.getRe()) || Double.isNaN(temp.getIm()) || Double.isInfinite(temp.getRe()) || Double.isInfinite(temp.getIm())) {
               break;
           }
           
           complex_orbit.add(temp);
        }

    }

    public abstract void calculateJuliaOrbit();
    
    public abstract double calculateJulia(Complex pixel);
    
    public abstract double[] calculateJulia3D(Complex pixel);

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
        
        return plane.getPixel(z);
        
    }
 
}
