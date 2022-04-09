package fractalzoomer.functions.root_finding_methods.jaratt2;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class Jaratt2RootFindingMethod extends RootFindingMethods {

    public Jaratt2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1e-8;

    }

    //orbit
    public Jaratt2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public static Complex getDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz).times_mutable(0.66666666666666666));

    }

    public Complex jaratt2Method(Complex z, Complex fz, Complex dfz, Complex combined_dfz) {

        Complex ufz = fz.divide(dfz);

        Complex hfz = (combined_dfz.sub_mutable(dfz)).divide_mutable(dfz);

        z.sub_mutable(ufz).plus_mutable(ufz.times(0.75).times_mutable(hfz).times_mutable(hfz.times(1.5).r_sub_mutable(1)));

        return z;

    }

    public static Complex jaratt2Method(Complex z, Complex fz, Complex dfz, Complex combined_dfz, Complex relaxation) {

        Complex ufz = fz.divide(dfz);

        Complex hfz = (combined_dfz.sub_mutable(dfz)).divide_mutable(dfz);

        z.sub_mutable(ufz.times(relaxation)).plus_mutable(ufz.times(0.75).times_mutable(hfz).times_mutable(hfz.times(1.5).r_sub_mutable(1)).times(relaxation));

        return z;

    }

    public static Complex jaratt2Step(Complex fz, Complex dfz, Complex combined_dfz) {

        Complex ufz = fz.divide(dfz);

        Complex hfz = (combined_dfz.sub_mutable(dfz)).divide_mutable(dfz);

        return ufz.plus_mutable(ufz.times(0.75).times_mutable(hfz).times_mutable(hfz.times(1.5).r_sub_mutable(1)));
    }
}
