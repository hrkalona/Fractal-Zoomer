package fractalzoomer.functions.root_finding_methods.broyden;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;
import org.apfloat.Apfloat;

import java.util.ArrayList;

/**
 *
 * @author hrkalona2
 */
public abstract class BroydenRootFindingMethod extends RootFindingMethods {

    public BroydenRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);
        setConvergentBailout(1E-12);
    }

    //orbit
    public BroydenRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, Apfloat[] plane_transform_center_hp, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_center_hp, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public static Complex getInitialJacobianApproximation(Complex z0, Complex z1, Complex fz0, Complex fz1) {
        return fz1.sub(fz0).divide_mutable(z1.sub(z0));
    }

    public static Complex advanceJacobianApproximation(Complex B, Complex z1, Complex z0, Complex fz1, Complex fz0) {
        Complex dz = z1.sub(z0);
        Complex df = fz1.sub(fz0);
        return B.plus_mutable(df.sub(B.times(dz)).divide_mutable(dz));
    }

    public Complex broydenMethod(Complex z, Complex fz, Complex B) {
        return z.sub(fz.divide(B));
    }

    public static Complex broydenMethod(Complex z, Complex fz, Complex B, Complex relaxation) {

        return z.sub(fz.divide(B).times_mutable(relaxation));

    }

    public static Complex broydenStep(Complex z, Complex fz, Complex B) {

        return fz.divide(B);

    }
}
