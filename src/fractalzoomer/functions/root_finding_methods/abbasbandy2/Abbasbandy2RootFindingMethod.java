package fractalzoomer.functions.root_finding_methods.abbasbandy2;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class Abbasbandy2RootFindingMethod extends RootFindingMethods {

    public Abbasbandy2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

    }

    //orbit
    public Abbasbandy2RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public Complex abbasbandy2Method(Complex z, Complex fz, Complex dfz, Complex ddfz) {

        z.sub_mutable(fz.divide(dfz)).sub_mutable(fz.square().times_mutable(ddfz).divide_mutable(dfz.cube().times_mutable(2))).sub_mutable(fz.cube().times_mutable(ddfz.square()).divide_mutable(dfz.fifth().times_mutable(2)));

        return z;

    }

    public static Complex abbasbandy2Method(Complex z, Complex fz, Complex dfz, Complex ddfz, Complex relaxation) {

        z.sub_mutable(fz.divide(dfz).times_mutable(relaxation)).sub_mutable(fz.square().times_mutable(ddfz).divide_mutable(dfz.cube().times_mutable(2)).times_mutable(relaxation)).sub_mutable(fz.cube().times_mutable(ddfz.square()).divide_mutable(dfz.fifth().times_mutable(2)).times_mutable(relaxation));

        return z;

    }

    public static Complex abbasbandy2Step(Complex fz, Complex dfz, Complex ddfz) {

        return fz.divide(dfz).plus_mutable(fz.square().times_mutable(ddfz).divide_mutable(dfz.cube().times_mutable(2))).plus_mutable(fz.cube().times_mutable(ddfz.square()).divide_mutable(dfz.fifth().times_mutable(2)));

    }
}
