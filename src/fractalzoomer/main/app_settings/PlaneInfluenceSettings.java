package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

public class PlaneInfluenceSettings {
    public int influencePlane;
    public String userFormulaPlaneInfluence;
    public String[] user_plane_influence_conditions;
    public String[] user_plane_influence_condition_formula;
    public int user_plane_influence_algorithm;

    public PlaneInfluenceSettings() {

        influencePlane = Constants.NO_PLANE_INFLUENCE;
        userFormulaPlaneInfluence = "(c0 - (-0.8+0.23i)) * 0.05 + (-0.8+0.23i)";

        user_plane_influence_algorithm = 0;

        user_plane_influence_conditions = new String[2];
        user_plane_influence_conditions[0] = "re(n)";
        user_plane_influence_conditions[1] = "10";

        user_plane_influence_condition_formula = new String[3];
        user_plane_influence_condition_formula[0] = "c0";
        user_plane_influence_condition_formula[1] = "c0";
        user_plane_influence_condition_formula[2] = "1 / c0";

    }
}
