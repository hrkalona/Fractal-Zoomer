package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1078 extends SettingsFractals1077 implements Serializable {
    private static final long serialVersionUID = 324196075712399L;

    private boolean juliter;
    private int juliterIterations;
    private boolean juliterIncludeInitialIterations;
    private int influencePlane;
    private String userFormulaPlaneInfluence;
    private String[] user_plane_influence_conditions;
    private String[] user_plane_influence_condition_formula;
    private int user_plane_influence_algorithm;
    private String xCenterStr;
    private String yCenterStr;
    private String sizeStr;
    private String[] rotation_center_str;

    public SettingsFractals1078(Settings s) {
        super(s);
        juliter = s.fns.juliter;
        juliterIterations = s.fns.juliterIterations;
        juliterIncludeInitialIterations = s.fns.juliterIncludeInitialIterations;
        influencePlane = s.fns.ips.influencePlane;
        userFormulaPlaneInfluence = s.fns.ips.userFormulaPlaneInfluence;
        user_plane_influence_conditions = s.fns.ips.user_plane_influence_conditions;
        user_plane_influence_condition_formula = s.fns.ips.user_plane_influence_condition_formula;
        user_plane_influence_algorithm = s.fns.ips.user_plane_influence_algorithm;
        xCenterStr = s.xCenter.toString(true);
        yCenterStr = s.yCenter.toString(true);
        sizeStr = s.size.toString();
        rotation_center_str = new String[2];
        rotation_center_str[0] = s.fns.rotation_center[0].toString(true);
        rotation_center_str[1] = s.fns.rotation_center[1].toString(true);
    }

    @Override
    public int getVersion() {

        return 1078;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public boolean getJuliter() {
        return juliter;
    }

    public int getJuliterIterations() {
        return juliterIterations;
    }

    public boolean getJuliterIncludeInitialIterations() {
        return juliterIncludeInitialIterations;
    }

    public int getInfluencePlane() {
        return influencePlane;
    }

    public String getUserFormulaPlaneInfluence() {
        return userFormulaPlaneInfluence;
    }

    public String[] getUserPlaneInfluenceConditions() {
        return user_plane_influence_conditions;
    }

    public String[] getUserPlaneInfluenceConditionFormula() {
        return user_plane_influence_condition_formula;
    }

    public int getUserPlaneInfluenceAlgorithm() {
        return user_plane_influence_algorithm;
    }

    public String getxCenterStr() {
        return xCenterStr;
    }

    public String getyCenterStr() {
        return yCenterStr;
    }

    public String getSizeStr() {
        return sizeStr;
    }

    public String[] getRotationCenterStr() {
        return rotation_center_str;
    }
}
