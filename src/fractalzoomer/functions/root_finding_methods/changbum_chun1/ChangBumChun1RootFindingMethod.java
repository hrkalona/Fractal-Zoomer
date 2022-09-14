package fractalzoomer.functions.root_finding_methods.changbum_chun1;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class ChangBumChun1RootFindingMethod extends RootFindingMethods {

    public ChangBumChun1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

        convergent_bailout = 1e-8;

    }

    //orbit
    public ChangBumChun1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public static Complex getFunctionArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex changbumChun1Method(Complex z, Complex fz, Complex dfz, Complex ffz) {

        Complex fzSquared = fz.square();
        z.sub_mutable(fz.divide(dfz)).sub_mutable(fzSquared.times(ffz).divide_mutable((fzSquared.sub(fz.times(ffz).times_mutable(2)).plus_mutable(ffz.square())).times_mutable(dfz)));

        return z;

    }

    public static Complex changbumChun1Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex relaxation) {

        Complex fzSquared = fz.square();
        z.sub_mutable(fz.divide(dfz).times_mutable(relaxation)).sub_mutable(fzSquared.times(ffz).divide_mutable((fzSquared.sub(fz.times(ffz).times_mutable(2)).plus_mutable(ffz.square())).times_mutable(dfz)).times_mutable(relaxation));

        return z;

    }

    public static Complex changbumChun1Step(Complex fz, Complex dfz, Complex ffz) {

        Complex fzSquared = fz.square();
        return fz.divide(dfz).plus_mutable(fzSquared.times(ffz).divide_mutable((fzSquared.sub(fz.times(ffz).times_mutable(2)).plus_mutable(ffz.square())).times_mutable(dfz)));


    }
}
