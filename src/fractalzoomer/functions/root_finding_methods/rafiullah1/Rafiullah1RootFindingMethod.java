package fractalzoomer.functions.root_finding_methods.rafiullah1;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class Rafiullah1RootFindingMethod extends RootFindingMethods {

    public Rafiullah1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

    }

    //orbit
    public Rafiullah1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public static Complex getDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex rafiullah1Method(Complex z, Complex fz, Complex dfz, Complex combined_dfz) {

        Complex temp = combined_dfz.sub(dfz);
        z.sub_mutable(fz.divide(dfz)).sub_mutable(fz.times(dfz).times_mutable(temp).divide_mutable(dfz.cube().plus_mutable(dfz.square().times_mutable(temp)).times2_mutable()));
        return z;

    }

    public static Complex rafiullah1Method(Complex z, Complex fz, Complex dfz, Complex combined_dfz, Complex relaxation) {

        Complex temp = combined_dfz.sub(dfz);
        z.sub_mutable(fz.divide(dfz).times_mutable(relaxation)).sub_mutable(fz.times(dfz).times_mutable(temp).divide_mutable(dfz.cube().plus_mutable(dfz.square().times_mutable(temp)).times2_mutable()).times_mutable(relaxation));
        return z;

    }

    public static Complex rafiullah1Step(Complex fz, Complex dfz, Complex combined_dfz) {

        Complex temp = combined_dfz.sub(dfz);
        return fz.divide(dfz).plus_mutable(fz.times(dfz).times_mutable(temp).divide_mutable(dfz.cube().plus_mutable(dfz.square().times_mutable(temp)).times2_mutable()));

    }
}
