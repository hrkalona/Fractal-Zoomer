
package fractalzoomer.core;

import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
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

        plane = Fractal.PlaneFactory(null, xCenter, yCenter, size, s.max_iterations, s.fns.plane_type, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.plane_transform_center, s.fns.plane_transform_center_hp, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_wavelength, s.fns.waveType, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.fns.inflections_re, s.fns.inflections_im, s.fns.inflectionsPower, vars);

        if (plane instanceof UserPlane) {
            usesCenter = ((UserPlane) plane).usesCenter();
        } else if (plane instanceof UserPlaneConditional) {
            usesCenter = ((UserPlaneConditional) plane).usesCenter();
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
        double temp_ycenter_size = y_center + size_2;

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
