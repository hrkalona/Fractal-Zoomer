
package fractalzoomer.core;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

/**
 *
 * @author hrkalona2
 */
public class PlaneVisualizer {

    private BufferedImage plane_mu_image;
    private BufferedImage new_plane_image;
    private Plane plane;
    private double size;
    private double xCenter;
    private double yCenter;
    private boolean usesCenter;
    private Complex[] vars;

    public PlaneVisualizer(BufferedImage plane_mu_image, BufferedImage new_plane_image, Settings s, double size) {

        this.plane_mu_image = plane_mu_image;
        this.new_plane_image = new_plane_image;
        this.size = size;
        this.xCenter = s.xCenter.doubleValue();
        this.yCenter = s.yCenter.doubleValue();
        
        usesCenter = false;
        
        vars = createGlobalVars();

        switch (s.fns.plane_type) {
            case MainWindow.MU_PLANE:
                plane = new MuPlane();
                break;
            case MainWindow.MU_SQUARED_PLANE:
                plane = new MuSquaredPlane(null);
                break;
            case MainWindow.MU_FOURTH_PLANE:
                plane = new MuFourthPlane();
                break;
            case MainWindow.MU_SQUARED_IMAGINARY_PLANE:
                plane = new MuSquaredImaginaryPlane();
                break;
            case MainWindow.INVERSED_MU_PLANE:
                plane = new InversedMuPlane(null);
                break;
            case MainWindow.INVERSED_MU2_PLANE:
                plane = new InversedMu2Plane(null);
                break;
            case MainWindow.INVERSED_MU3_PLANE:
                plane = new InversedMu3Plane(null);
                break;
            case MainWindow.INVERSED_MU4_PLANE:
                plane = new InversedMu4Plane(null);
                break;
            case MainWindow.LAMBDA_PLANE:
                plane = new LambdaPlane(null);
                break;
            case MainWindow.INVERSED_LAMBDA_PLANE:
                plane = new InversedLambdaPlane(null);
                break;
            case MainWindow.INVERSED_LAMBDA2_PLANE:
                plane = new InversedLambda2Plane(null);
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
                plane = new FoldUpPlane(s.fns.plane_transform_center, null);
                break;
            case MainWindow.FOLDDOWN_PLANE:
                plane = new FoldDownPlane(s.fns.plane_transform_center, null);
                break;
            case MainWindow.FOLDRIGHT_PLANE:
                plane = new FoldRightPlane(s.fns.plane_transform_center, null);
                break;
            case MainWindow.FOLDLEFT_PLANE:
                plane = new FoldLeftPlane(s.fns.plane_transform_center, null);
                break;
            case MainWindow.FOLDIN_PLANE:
                plane = new FoldInPlane(s.fns.plane_transform_radius, null);
                break;
            case MainWindow.FOLDOUT_PLANE:
                plane = new FoldOutPlane(s.fns.plane_transform_radius, null);
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
                if(s.fns.user_plane_algorithm == 0) {
                    plane = new UserPlane(s.fns.user_plane, xCenter, yCenter, size, s.max_iterations, s.fns.plane_transform_center, vars);
                    usesCenter = ((UserPlane)plane).usesCenter();
                }
                else {
                    plane = new UserPlaneConditional(s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, xCenter, yCenter, size, s.max_iterations, s.fns.plane_transform_center, vars);
                    usesCenter = ((UserPlaneConditional)plane).usesCenter();
                }
                break;
            case MainWindow.GAMMA_PLANE:
                plane = new GammaFunctionPlane();
                break;
            case MainWindow.FACT_PLANE:
                plane = new FactorialPlane();
                break;
            case MainWindow.BIPOLAR_PLANE:
                plane = new BipolarPlane(s.fns.plane_transform_center);
                break;
            case MainWindow.INVERSED_BIPOLAR_PLANE:
                plane = new InversedBipolarPlane(s.fns.plane_transform_center);
                break;
            case MainWindow.TWIRL_PLANE:
                plane = new TwirlPlane(s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius);
                break;
            case MainWindow.SHEAR_PLANE:
                plane = new ShearPlane(s.fns.plane_transform_scales, s.fns.plane_transform_center_hp, null);
                break;
            case MainWindow.KALEIDOSCOPE_PLANE:
                plane = new KaleidoscopePlane(s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_angle2, s.fns.plane_transform_radius, s.fns.plane_transform_sides);
                break;
            case MainWindow.PINCH_PLANE:
                plane = new PinchPlane(s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_amount);
                break;
            case MainWindow.CIRCLEINVERSION_PLANE:
                plane = new CircleInversionPlane(s.fns.plane_transform_center, s.fns.plane_transform_radius, null);
                break;
            case MainWindow.VARIATION_MU_PLANE:
                plane = new MuVariationPlane(null);
                break;
            case MainWindow.ERF_PLANE:
                plane = new ErfPlane();
                break;
            case MainWindow.RZETA_PLANE:
                plane = new RiemannZetaPlane();
                break;
            case MainWindow.INFLECTION_PLANE:
                plane = new InflectionPlane(s.fns.plane_transform_center, null);
                break;
            case MainWindow.RIPPLES_PLANE:
                plane = new RipplesPlane(s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType);
                break;
            case MainWindow.SKEW_PLANE:
                plane = new SkewPlane(s.fns.plane_transform_angle, s.fns.plane_transform_angle2, s.fns.plane_transform_center_hp, null);
                break;
            case MainWindow.STRETCH_PLANE:
                plane = new StretchPlane(s.fns.plane_transform_angle, s.fns.plane_transform_amount, s.fns.plane_transform_center_hp, null);
                break;
            case MainWindow.INFLECTIONS_PLANE:
                plane = new InflectionsPlane(s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, null);
                break;
        }

    }
    
    protected Complex[] createGlobalVars() {
        
        Complex[] vars = new Complex[Parser.EXTRA_VARS];
        
        for(int i = 0; i < vars.length; i++) {
            vars[i] = new Complex();
        }
        
        return vars;
    }

    public void visualizePlanes(int color_mode) {

        int image_size = plane_mu_image.getWidth();
        int[] rgbs = ((DataBufferInt)plane_mu_image.getRaster().getDataBuffer()).getData();
        int[] rgbs2 = ((DataBufferInt)new_plane_image.getRaster().getDataBuffer()).getData();

        Arrays.fill(rgbs, Color.WHITE.getRGB());
        Arrays.fill(rgbs2,  Color.WHITE.getRGB());

        double x_center = 0;
        double y_center = 0;
        
        if(usesCenter) {
            x_center = xCenter;
            y_center = yCenter;
        }

        double size_2 = size * 0.5;
        double temp_xcenter_size = x_center - size_2;
        double temp_ycenter_size = y_center+ size_2;

        double temp_size_image_size = size / image_size;

        int step = 20;
        int color_step = image_size / step;

        int FROMx = 0;
        int FROMy = 0;
        int TOx = image_size;
        int TOy = image_size;

        for(int x = FROMx; x < TOx; x++) {
            for(int y = FROMy; y < TOy; y++) {

                int new_x = x;
                int new_y = y;

                if(new_x % step == 0 || new_y % step == 0) {

                    int x0 = new_x;
                    int y0 = new_y;

                    if(x0 >= 0 && x0 < image_size && y0 >= 0 && y0 < image_size) {
                        if(color_mode == 1 ) {
                            rgbs[y0 * image_size + x0] = Color.HSBtoRGB((float)(new_x / step * 1.0 / color_step), (float)(new_x / step * 1.0 / color_step) * 0.5f + (float)(new_y / step * 1.0 / color_step) * 0.5f, 0.2f + 0.8f * (float)(new_y / step * 1.0 / color_step));
                        }
                        else {
                            if(new_y % step == 0) {
                                rgbs[y0 * image_size + x0] = new Color(255, 76, 0).getRGB();
                            }
                            else {
                                rgbs[y0 * image_size + x0] = new Color(63, 61, 153).getRGB();
                            }
                        }
                    }

                    int x1 = new_x + 1;
                    int y1 = new_y + 1;

                    if(x1 >= 0 && x1 < image_size && y1 >= 0 && y1 < image_size) {
                        if(color_mode == 1 ) {
                            rgbs[y1 * image_size + x1] = Color.HSBtoRGB((float)(new_x / step * 1.0 / color_step), (float)(new_x / step * 1.0 / color_step) * 0.5f + (float)(new_y / step * 1.0 / color_step) * 0.5f, 0.2f + 0.8f * (float)(new_y / step * 1.0 / color_step));
                        }
                        else {
                            if(new_y % step == 0) {
                                rgbs[y1 * image_size + x1] = new Color(255, 76, 0).getRGB();
                            }
                            else {
                                rgbs[y1 * image_size + x1] = new Color(63, 61, 153).getRGB();
                            }
                        }
                    }
                }
            }
        }

        for(int x = FROMx; x < TOx; x++) {
            for(int y = FROMy; y < TOy; y++) {

                int new_x = x;
                int new_y = y;

                if(new_x % step == 0 || new_y % step == 0) {
                    Complex new_complex = plane.transform(new Complex(temp_xcenter_size + new_x * temp_size_image_size, temp_ycenter_size - new_y * temp_size_image_size));

                    int x0 = (int)((new_complex.getRe() - temp_xcenter_size) / temp_size_image_size + 0.5);
                    int y0 = (int)((-new_complex.getIm() + temp_ycenter_size) / temp_size_image_size + 0.5);

                    if(x0 >= 0 && x0 < image_size && y0 >= 0 && y0 < image_size) {
                        if(color_mode == 1 ) {
                            rgbs2[y0 * image_size + x0] = Color.HSBtoRGB((float)(new_x / step * 1.0 / color_step), (float)(new_x / step * 1.0 / color_step) * 0.5f + (float)(new_y / step * 1.0 / color_step) * 0.5f, 0.2f + 0.8f * (float)(new_y / step * 1.0 / color_step));
                        }
                        else {
                            if(new_y % step == 0) {
                                rgbs2[y0 * image_size + x0] = new Color(255, 76, 0).getRGB();
                            }
                            else {
                                rgbs2[y0 * image_size + x0] = new Color(63, 61, 153).getRGB();
                            }
                        }
                    }

                    Complex new_complex2 = plane.transform(new Complex(temp_xcenter_size + (new_x + 1) * temp_size_image_size, temp_ycenter_size - (new_y + 1) * temp_size_image_size));

                    int x1 = (int)((new_complex2.getRe() - temp_xcenter_size) / temp_size_image_size + 0.5);
                    int y1 = (int)((-new_complex2.getIm() + temp_ycenter_size) / temp_size_image_size + 0.5);

                    if(x1 >= 0 && x1 < image_size && y1 >= 0 && y1 < image_size) {
                        if(color_mode == 1 ) {
                            rgbs2[y1 * image_size + x1] = Color.HSBtoRGB((float)(new_x / step * 1.0 / color_step), (float)(new_x / step * 1.0 / color_step) * 0.5f + (float)(new_y / step * 1.0 / color_step) * 0.5f, 0.2f + 0.8f * (float)(new_y / step * 1.0 / color_step));
                        }
                        else {
                            if(new_y % step == 0) {
                                rgbs2[y1 * image_size + x1] = new Color(255, 76, 0).getRGB();
                            }
                            else {
                                rgbs2[y1 * image_size + x1] = new Color(63, 61, 153).getRGB();
                            }
                        }
                    }
                }
            }
        }
    }

}
