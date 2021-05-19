package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1077 extends SettingsFractals1076 implements Serializable {
    private static final long serialVersionUID = 53895231432L;

    private int pre_functionFilter;
    private String pre_userFormulaFunctionFilter;
    private String[] pre_user_function_filter_conditions;
    private String[] pre_user_function_filter_condition_formula;
    private int pre_user_function_filter_algorithm;

    private int post_functionFilter;
    private String post_userFormulaFunctionFilter;
    private String[] post_user_function_filter_conditions;
    private String[] post_user_function_filter_condition_formula;
    private int post_user_function_filter_algorithm;

    private String user_dddfz_formula;
    private int root_initialization_method;

    private double lagrangianPower;
    private double equicontinuityDenominatorFactor;
    private int equicontinuityColorMethod;
    private int equicontinuityMixingMethod;
    private double equicontinuityBlending;
    private double equicontinuitySatChroma;
    private int equicontinuityArgValue;
    private boolean equicontinuityInvertFactor;
    private boolean equicontinuityOverrideColoring;
    private double equicontinuityDelta;

    public SettingsFractals1077(Settings s) {

        super(s);
        pre_functionFilter = s.fns.preffs.functionFilter;
        pre_userFormulaFunctionFilter = s.fns.preffs.userFormulaFunctionFilter;
        pre_user_function_filter_conditions = s.fns.preffs.user_function_filter_conditions;
        pre_user_function_filter_condition_formula = s.fns.preffs.user_function_filter_condition_formula;
        pre_user_function_filter_algorithm = s.fns.preffs.user_function_filter_algorithm;

        post_functionFilter = s.fns.postffs.functionFilter;
        post_userFormulaFunctionFilter = s.fns.postffs.userFormulaFunctionFilter;
        post_user_function_filter_conditions = s.fns.postffs.user_function_filter_conditions;
        post_user_function_filter_condition_formula = s.fns.postffs.user_function_filter_condition_formula;
        post_user_function_filter_algorithm = s.fns.postffs.user_function_filter_algorithm;

        user_dddfz_formula = s.fns.user_dddfz_formula;
        root_initialization_method = s.fns.root_initialization_method;

        lagrangianPower = s.sts.lagrangianPower;
        equicontinuityDenominatorFactor = s.sts.equicontinuityDenominatorFactor;
        equicontinuityColorMethod = s.sts.equicontinuityColorMethod;
        equicontinuityMixingMethod = s.sts.equicontinuityMixingMethod;
        equicontinuityBlending = s.sts.equicontinuityBlending;
        equicontinuitySatChroma = s.sts.equicontinuitySatChroma;
        equicontinuityArgValue = s.sts.equicontinuityArgValue;
        equicontinuityInvertFactor = s.sts.equicontinuityInvertFactor;
        equicontinuityOverrideColoring = s.sts.equicontinuityOverrideColoring;
        equicontinuityDelta = s.sts.equicontinuityDelta;

    }

    @Override
    public int getVersion() {

        return 1077;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public int getPreFunctionFilter() {
        return pre_functionFilter;
    }

    public String getPreUserFormulaFunctionFilter() {
        return pre_userFormulaFunctionFilter;
    }

    public String[] getPreUserFunctionFilterConditions() {
        return pre_user_function_filter_conditions;
    }

    public String[] getPreUserFunctionFilterConditionFormula() {
        return pre_user_function_filter_condition_formula;
    }

    public int getPreUserFunctionFilterAlgorithm() {
        return pre_user_function_filter_algorithm;
    }

    public int getPostFunctionFilter() {
        return post_functionFilter;
    }

    public String getPostUserFormulaFunctionFilter() {
        return post_userFormulaFunctionFilter;
    }

    public String[] getPostUserFunctionFilterConditions() {
        return post_user_function_filter_conditions;
    }

    public String[] getPostUserFunctionFilterConditionFormula() {
        return post_user_function_filter_condition_formula;
    }

    public int getPostUserFunctionFilterAlgorithm() {
        return post_user_function_filter_algorithm;
    }

    public String getUserDddfzFormula() {
        return user_dddfz_formula;
    }

    public int getRootInitialization_method() {
        return root_initialization_method;
    }

    public double getLagrangianPower() {
        return lagrangianPower;
    }

    public double getEquicontinuityDenominatorFactor() {
        return equicontinuityDenominatorFactor;
    }

    public int getEquicontinuityColorMethod() {
        return equicontinuityColorMethod;
    }

    public int getEquicontinuityMixingMethod() {
        return equicontinuityMixingMethod;
    }

    public double getEquicontinuityBlending() {
        return equicontinuityBlending;
    }

    public double getEquicontinuitySatChroma() {
        return equicontinuitySatChroma;
    }

    public int getEquicontinuityArgValue() {
        return equicontinuityArgValue;
    }

    public boolean isEquicontinuityInvertFactor() {
        return equicontinuityInvertFactor;
    }

    public boolean isEquicontinuityOverrideColoring() {
        return equicontinuityOverrideColoring;
    }

    public double getEquicontinuityDelta() {
        return equicontinuityDelta;
    }
}
