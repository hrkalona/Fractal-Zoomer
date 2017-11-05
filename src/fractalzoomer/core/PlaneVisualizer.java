/*
 * Copyright (C) 2017 hrkalona2
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
package fractalzoomer.core;

import fractalzoomer.main.MainWindow;
import fractalzoomer.planes.Plane;
import fractalzoomer.planes.distort.KaleidoscopePlane;
import fractalzoomer.planes.distort.PinchPlane;
import fractalzoomer.planes.distort.ShearPlane;
import fractalzoomer.planes.distort.TwirlPlane;
import fractalzoomer.planes.fold.FoldDownPlane;
import fractalzoomer.planes.fold.FoldInPlane;
import fractalzoomer.planes.fold.FoldLeftPlane;
import fractalzoomer.planes.fold.FoldOutPlane;
import fractalzoomer.planes.fold.FoldRightPlane;
import fractalzoomer.planes.fold.FoldUpPlane;
import fractalzoomer.planes.general.BipolarPlane;
import fractalzoomer.planes.general.CircleInversionPlane;
import fractalzoomer.planes.general.InflectionPlane;
import fractalzoomer.planes.general.InversedBipolarPlane;
import fractalzoomer.planes.general.InversedLambda2Plane;
import fractalzoomer.planes.general.InversedLambdaPlane;
import fractalzoomer.planes.general.InversedMu2Plane;
import fractalzoomer.planes.general.InversedMu3Plane;
import fractalzoomer.planes.general.InversedMu4Plane;
import fractalzoomer.planes.general.InversedMuPlane;
import fractalzoomer.planes.general.LambdaPlane;
import fractalzoomer.planes.general.MuPlane;
import fractalzoomer.planes.general.MuSquaredImaginaryPlane;
import fractalzoomer.planes.general.MuSquaredPlane;
import fractalzoomer.planes.general.MuVariationPlane;
import fractalzoomer.planes.math.AbsPlane;
import fractalzoomer.planes.math.ErfPlane;
import fractalzoomer.planes.math.ExpPlane;
import fractalzoomer.planes.math.FactorialPlane;
import fractalzoomer.planes.math.GammaFunctionPlane;
import fractalzoomer.planes.math.LogPlane;
import fractalzoomer.planes.math.RiemannZetaPlane;
import fractalzoomer.planes.math.SqrtPlane;
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
import fractalzoomer.planes.math.trigonometric.CosPlane;
import fractalzoomer.planes.math.trigonometric.CoshPlane;
import fractalzoomer.planes.math.trigonometric.CotPlane;
import fractalzoomer.planes.math.trigonometric.CothPlane;
import fractalzoomer.planes.math.trigonometric.CscPlane;
import fractalzoomer.planes.math.trigonometric.CschPlane;
import fractalzoomer.planes.math.trigonometric.SecPlane;
import fractalzoomer.planes.math.trigonometric.SechPlane;
import fractalzoomer.planes.math.trigonometric.SinPlane;
import fractalzoomer.planes.math.trigonometric.SinhPlane;
import fractalzoomer.planes.math.trigonometric.TanPlane;
import fractalzoomer.planes.math.trigonometric.TanhPlane;
import fractalzoomer.planes.newton.Newton3Plane;
import fractalzoomer.planes.newton.Newton4Plane;
import fractalzoomer.planes.newton.NewtonGeneralized3Plane;
import fractalzoomer.planes.newton.NewtonGeneralized8Plane;
import fractalzoomer.planes.user_plane.UserPlane;
import fractalzoomer.planes.user_plane.UserPlaneConditional;
import java.awt.Color;
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

    public PlaneVisualizer(BufferedImage plane_mu_image, BufferedImage new_plane_image, int plane_type, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, double xCenter, double yCenter, double size, int max_iterations) {

        this.plane_mu_image = plane_mu_image;
        this.new_plane_image = new_plane_image;
        this.size = size;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        
        usesCenter = false;

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
                if(user_plane_algorithm == 0) {
                    plane = new UserPlane(user_plane, xCenter, yCenter, size, max_iterations, plane_transform_center);
                    usesCenter = ((UserPlane)plane).usesCenter();
                }
                else {
                    plane = new UserPlaneConditional(user_plane_conditions, user_plane_condition_formula, xCenter, yCenter, size, max_iterations, plane_transform_center);
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
        }

    }

    public void visualizePlanes(int color_mode) {

        int image_size = plane_mu_image.getHeight();
        int[] rgbs = ((DataBufferInt)plane_mu_image.getRaster().getDataBuffer()).getData();
        int[] rgbs2 = ((DataBufferInt)new_plane_image.getRaster().getDataBuffer()).getData();

        Arrays.fill(rgbs, 0, image_size * image_size, Color.WHITE.getRGB());
        Arrays.fill(rgbs2, 0, image_size * image_size, Color.WHITE.getRGB());

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
