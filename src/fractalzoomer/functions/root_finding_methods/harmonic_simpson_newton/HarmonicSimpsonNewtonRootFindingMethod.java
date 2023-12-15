package fractalzoomer.functions.root_finding_methods.harmonic_simpson_newton;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class HarmonicSimpsonNewtonRootFindingMethod extends RootFindingMethods {

    public HarmonicSimpsonNewtonRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower, ots);

    }

    //orbit
    public HarmonicSimpsonNewtonRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, ArrayList<Double> inflections_re, ArrayList<Double> inflections_im, double inflectionsPower) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, inflections_re, inflections_im, inflectionsPower);

    }

    public static Complex getDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex hsnMethod(Complex z, Complex fz, Complex dfz, Complex combined_dfz, Complex combined_dfz2) {

        z.sub_mutable(fz.times(3).divide_mutable(dfz.times(combined_dfz).times2_mutable().divide_mutable(dfz.plus(combined_dfz)).plus_mutable(combined_dfz2.times2())));

        return z;

    }

    public static Complex hsnMethod(Complex z, Complex fz, Complex dfz, Complex combined_dfz, Complex combined_dfz2, Complex relaxation) {

        z.sub_mutable(fz.times(3).divide_mutable(dfz.times(combined_dfz).times2_mutable().divide_mutable(dfz.plus(combined_dfz)).plus_mutable(combined_dfz2.times2())).times_mutable(relaxation));

        return z;

    }

    public static Complex hsnStep(Complex fz, Complex dfz, Complex combined_dfz, Complex combined_dfz2) {

        return fz.times(3).divide_mutable(dfz.times(combined_dfz).times2_mutable().divide_mutable(dfz.plus(combined_dfz)).plus_mutable(combined_dfz2.times2()));


    }
}
