package fractalzoomer.functions.root_finding_methods.changbum_chun2;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class ChangBumChun2RootFindingMethod extends RootFindingMethods {

    public ChangBumChun2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

    }

    //orbit
    public ChangBumChun2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public static Complex getFunctionArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex changbumChun2Method(Complex z, Complex fz, Complex dfz, Complex ffz) {

        Complex fzCubed = fz.cube();
        z.sub_mutable(fz.divide(dfz)).sub_mutable(fzCubed.times(ffz).divide_mutable(fzCubed.sub(fz.square().times_mutable(ffz).times2_mutable()).plus_mutable(fz.times(ffz.square()).times2_mutable()).sub_mutable(ffz.cube().times2_mutable()).times_mutable(dfz)));
        return z;

    }

    public static Complex changbumChun2Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex relaxation) {

        Complex fzCubed = fz.cube();
        z.sub_mutable(fz.divide(dfz).times_mutable(relaxation)).sub_mutable(fzCubed.times(ffz).divide_mutable(fzCubed.sub(fz.square().times_mutable(ffz).times2_mutable()).plus_mutable(fz.times(ffz.square()).times2_mutable()).sub_mutable(ffz.cube().times2_mutable()).times_mutable(dfz)).times_mutable(relaxation));

        return z;

    }

    public static Complex changbumChun2Step(Complex fz, Complex dfz, Complex ffz) {

        Complex fzCubed = fz.cube();
        return fz.divide(dfz).plus_mutable(fzCubed.times(ffz).divide_mutable(fzCubed.sub(fz.square().times_mutable(ffz).times2_mutable()).plus_mutable(fz.times(ffz.square()).times2_mutable()).sub_mutable(ffz.cube().times2_mutable()).times_mutable(dfz)));

    }
}
