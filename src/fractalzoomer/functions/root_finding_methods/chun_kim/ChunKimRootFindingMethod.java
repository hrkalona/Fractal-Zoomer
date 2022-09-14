package fractalzoomer.functions.root_finding_methods.chun_kim;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class ChunKimRootFindingMethod extends RootFindingMethods {

    public ChunKimRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

    }

    //orbit
    public ChunKimRootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public static Complex getDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex chunKimMethod(Complex z, Complex fz, Complex dfz, Complex df_combined) {

        z.sub_mutable(fz.times(dfz.square().times_mutable(3).plus_mutable(2).sub_mutable(dfz.times(df_combined))).divide_mutable(dfz.plus(dfz.cube().times_mutable(2)).plus_mutable(df_combined)));

        return z;

    }

    public static Complex chunKimMethod(Complex z, Complex fz, Complex dfz, Complex df_combined, Complex relaxation) {

        z.sub_mutable(fz.times(dfz.square().times_mutable(3).plus_mutable(2).sub_mutable(dfz.times(df_combined))).divide_mutable(dfz.plus(dfz.cube().times_mutable(2)).plus_mutable(df_combined)).times_mutable(relaxation));

        return z;

    }

    public static Complex chunKimStep(Complex fz, Complex dfz, Complex df_combined) {

        return fz.times(dfz.square().times_mutable(3).plus_mutable(2).sub_mutable(dfz.times(df_combined))).divide_mutable(dfz.plus(dfz.cube().times_mutable(2)).plus_mutable(df_combined));


    }
}