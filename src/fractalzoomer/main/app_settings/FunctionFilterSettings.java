package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

public class FunctionFilterSettings {
    public int functionFilter;
    public String userFormulaFunctionFilter;
    public String[] user_function_filter_conditions;
    public String[] user_function_filter_condition_formula;
    public int user_function_filter_algorithm;

    public FunctionFilterSettings() {

        functionFilter = Constants.NO_FUNCTION_FILTER;
        userFormulaFunctionFilter = "abs(z)";

        user_function_filter_algorithm = 0;

        user_function_filter_conditions = new String[2];
        user_function_filter_conditions[0] = "im(z)";
        user_function_filter_conditions[1] = "0.0";

        user_function_filter_condition_formula = new String[3];
        user_function_filter_condition_formula[0] = "abs(z)";
        user_function_filter_condition_formula[1] = "z";
        user_function_filter_condition_formula[2] = "abs(z)";

    }
}
