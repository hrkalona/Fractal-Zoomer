package fractalzoomer.functions.root_finding_methods.king1;

import fractalzoomer.core.Complex;
import fractalzoomer.functions.root_finding_methods.RootFindingMethods;
import fractalzoomer.main.app_settings.OrbitTrapSettings;

import java.util.ArrayList;

public abstract class King1RootFindingMethod extends RootFindingMethods {

    public King1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount, OrbitTrapSettings ots) {

        super(xCenter, yCenter, size, max_iterations, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount, ots);
        convergent_bailout = 1e-8;

    }

    //orbit
    public King1RootFindingMethod(double xCenter, double yCenter, double size, int max_iterations, ArrayList<Complex> complex_orbit, int plane_type, double[] rotation_vals, double[] rotation_center, String user_plane, int user_plane_algorithm, String[] user_plane_conditions, String[] user_plane_condition_formula, double[] plane_transform_center, double plane_transform_angle, double plane_transform_radius, double[] plane_transform_scales, double[] plane_transform_wavelength, int waveType, double plane_transform_angle2, int plane_transform_sides, double plane_transform_amount) {

        super(xCenter, yCenter, size, max_iterations, complex_orbit, plane_type, rotation_vals, rotation_center, user_plane, user_plane_algorithm, user_plane_conditions, user_plane_condition_formula, plane_transform_center, plane_transform_angle, plane_transform_radius, plane_transform_scales, plane_transform_wavelength, waveType, plane_transform_angle2, plane_transform_sides, plane_transform_amount);

    }

    public static Complex getFunctionAndDerivativeArgument(Complex z, Complex fz, Complex dfz) {

        return z.sub(fz.divide(dfz));

    }

    public Complex king1Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex df_combined) {

        Complex temp = fz.divide(dfz);
        z.sub_mutable(temp).sub_mutable(ffz.divide(df_combined)).sub_mutable(temp.times(ffz.divide(fz).cube_mutable()));
        return z;

    }

    public static Complex king1Method(Complex z, Complex fz, Complex dfz, Complex ffz, Complex df_combined, Complex relaxation) {

        Complex temp = fz.divide(dfz);
        z.sub_mutable(temp.times(relaxation)).sub_mutable(ffz.divide(df_combined).times_mutable(relaxation)).sub_mutable(temp.times(ffz.divide(fz).cube_mutable()).times_mutable(relaxation));
        return z;

    }

    public static Complex king1Step(Complex fz, Complex dfz, Complex ffz, Complex df_combined) {

        Complex temp = fz.divide(dfz);
        return temp.plus_mutable(ffz.divide(df_combined)).plus_mutable(temp.times(ffz.divide(fz).cube_mutable()));

    }
}
