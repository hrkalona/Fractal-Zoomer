
package fractalzoomer.functions.root_finding_methods.laguerre;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class LaguerreRootFindingMethod extends RootFindingMethods {
    protected Complex degree;

    public LaguerreRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double[] laguerre_deg, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);
        
        degree = new Complex(laguerre_deg[0], laguerre_deg[1]);
        
    }

    //orbit
    public LaguerreRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, double[] laguerre_deg) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

        degree = new Complex(laguerre_deg[0], laguerre_deg[1]);
        
    }

    public Complex laguerreMethod(Complex z, Complex fz, Complex dfz, Complex ddfz) {

        Complex n1 = degree.sub(1);
        Complex sqrt = (n1.times(dfz).square_mutable().sub_mutable(degree.times(n1).times_mutable(ddfz).times_mutable(fz))).sqrt_mutable();

        Complex denom1 = dfz.plus(sqrt);
        Complex denom2 = dfz.sub(sqrt);

        if(denom1.norm_squared() > denom2.norm_squared()) {
            z.sub_mutable(degree.times(fz).divide_mutable(denom1));
        }
        else {
            z.sub_mutable(degree.times(fz).divide_mutable(denom2));
        }
        
        return z;
        
    }
    
    public static Complex laguerreMethod(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex degree, Complex relaxation) {

        Complex n1 = degree.sub(1);
        Complex sqrt = (n1.times(dfz).square_mutable().sub_mutable(degree.times(n1).times_mutable(ddfz).times_mutable(fz))).sqrt_mutable();

        Complex denom1 = dfz.plus(sqrt);
        Complex denom2 = dfz.sub(sqrt);

        if(denom1.norm_squared() > denom2.norm_squared()) {
            z.sub_mutable((degree.times(fz).divide_mutable(denom1)).times_mutable(relaxation));
        }
        else {
            z.sub_mutable((degree.times(fz).divide_mutable(denom2)).times_mutable(relaxation));
        }
        
        return z;
        
    }

    public static Complex laguerreStep(Complex fz, Complex dfz, Complex ddfz, Complex degree) {

        Complex n1 = degree.sub(1);
        Complex sqrt = (n1.times(dfz).square_mutable().sub_mutable(degree.times(n1).times_mutable(ddfz).times_mutable(fz))).sqrt_mutable();

        Complex denom1 = dfz.plus(sqrt);
        Complex denom2 = dfz.sub(sqrt);

        if(denom1.norm_squared() > denom2.norm_squared()) {
            return (degree.times(fz).divide_mutable(denom1));
        }
        else {
            return (degree.times(fz).divide_mutable(denom2));
        }

    }
}
