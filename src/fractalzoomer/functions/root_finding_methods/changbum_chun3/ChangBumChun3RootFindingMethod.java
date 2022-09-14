package fractalzoomer.functions.root_finding_methods.changbum_chun3;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class ChangBumChun3RootFindingMethod extends RootFindingMethods {

    public ChangBumChun3RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);

    }

    //orbit
    public ChangBumChun3RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public static Complex getFunctionAndDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex changbumChun3Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex df_combined) {

       z.sub_mutable(fz.divide(dfz)).sub_mutable(ffz.times(2).divide_mutable(dfz)).plus_mutable(ffz.times(df_combined).divide_mutable(dfz.square()));
       return z;

    }

    public static Complex changbumChun3Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex df_combined, Complex relaxation) {

        z.sub_mutable(fz.divide(dfz).times_mutable(relaxation)).sub_mutable(ffz.times(2).divide_mutable(dfz).times_mutable(relaxation)).plus_mutable(ffz.times(df_combined).divide_mutable(dfz.square()).times_mutable(relaxation));
        return z;

    }

    public static Complex changbumChun3Step(Complex fz, Complex dfz, Complex ffz, Complex df_combined) {

        return fz.divide(dfz).plus_mutable(ffz.times(2).divide_mutable(dfz)).sub_mutable(ffz.times(df_combined).divide_mutable(dfz.square()));

    }
}
