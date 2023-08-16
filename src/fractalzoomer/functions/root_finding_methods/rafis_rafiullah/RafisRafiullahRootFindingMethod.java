package fractalzoomer.functions.root_finding_methods.rafis_rafiullah;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class RafisRafiullahRootFindingMethod extends RootFindingMethods {

    public RafisRafiullahRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

    }

    //orbit
    public RafisRafiullahRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public static Complex getSecondDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex rafisRafiullahMethod(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex ddf_combined) {

        z.sub_mutable(fz.divide(dfz)).sub_mutable(fz.square().times_mutable(ddf_combined).divide_mutable(dfz.cube().sub_mutable(fz.times(dfz).times_mutable(ddfz))).times_mutable(0.5));

        return z;

    }

    public static Complex rafisRafiullahMethod(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex ddf_combined, Complex relaxation) {

        z.sub_mutable(fz.divide(dfz)).sub_mutable(fz.square().times_mutable(ddf_combined).divide_mutable(dfz.cube().sub_mutable(fz.times(dfz).times_mutable(ddfz))).times_mutable(0.5).times_mutable(relaxation));

        return z;

    }

    public static Complex rafisRafiullahStep(Complex fz, Complex dfz, Complex ddfz, Complex ddf_combined) {

        return fz.divide(dfz).plus_mutable(fz.square().times_mutable(ddf_combined).divide_mutable(dfz.cube().sub_mutable(fz.times(dfz).times_mutable(ddfz))).times_mutable(0.5));

    }
}
