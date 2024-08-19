package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

public class ConvergentBailoutConditionSettings {
    public int convergent_bailout_test_algorithm;
    public String convergent_bailout_test_user_formula;
    public String convergent_bailout_test_user_formula2;
    public int convergent_bailout_test_comparison;
    public double convergent_n_norm;
    public double norm_a;
    public double norm_b;

    public ConvergentBailoutConditionSettings() {
        convergent_bailout_test_algorithm = Constants.CONVERGENT_BAILOUT_CONDITION_CIRCLE;
        convergent_bailout_test_user_formula = "norm(z - p)";
        convergent_bailout_test_user_formula2 = "cbail";
        convergent_bailout_test_comparison = Constants.LOWER_EQUAL;
        convergent_n_norm = 2.0;
        norm_a = 1;
        norm_b = 1;
    }
}
