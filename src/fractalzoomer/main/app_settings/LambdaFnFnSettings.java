
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class LambdaFnFnSettings {
    public String[] lambda_formula_conditions;
    public String[] lambda_formula_condition_formula;
    
    public LambdaFnFnSettings() {
        
        lambda_formula_conditions = new String[2];
        lambda_formula_conditions[0] = "norm(z)";
        lambda_formula_conditions[1] = "1.0";

        lambda_formula_condition_formula = new String[3];
        lambda_formula_condition_formula[0] = "z^2";
        lambda_formula_condition_formula[1] = "sin(z)";
        lambda_formula_condition_formula[2] = "z^2";
        
    }
    
}
