
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsFractals1073 extends SettingsFractals1072 implements Serializable {
    private static final long serialVersionUID = 9731046224531235L;
    private String user_relaxation_formula;
    private String user_nova_addend_formula;
    private double alpha2;
    private double beta2;
    private int gradient_offset;
    private String[] lambda_formula_conditions;
    private String[] lambda_formula_condition_formula;
    private String lyapunovFunction;
    private String lyapunovExponentFunction;
    private int lyapunovVariableId;
    private int magnetPendVariableId;
    private String user_statistic_init_value;
    private int skip_bailout_iterations;
    private double[][] bodyLocation;
    private double[][] bodyGravity;
    private double[] inertia_contribution;
    private double[] initial_inertia;
    private double inertia_exponent;
    private int pull_scaling_function;
    private double[] time_step;
    
    public SettingsFractals1073(Settings s) {
        
        super(s);
        
        user_relaxation_formula = s.fns.user_relaxation_formula;
        user_nova_addend_formula = s.fns.user_nova_addend_formula;
        alpha2 = s.fns.gcps.alpha2;
        beta2 = s.fns.gcps.beta2;
        gradient_offset = s.gs.gradient_offset;
        lambda_formula_conditions = s.fns.lfns.lambda_formula_conditions;
        lambda_formula_condition_formula = s.fns.lfns.lambda_formula_condition_formula;
        lyapunovFunction = s.fns.lpns.lyapunovFunction;
        lyapunovExponentFunction = s.fns.lpns.lyapunovExponentFunction;
        lyapunovVariableId = s.fns.lpns.lyapunovVariableId;
        magnetPendVariableId = s.fns.mps.magnetPendVariableId;
        user_statistic_init_value = s.pps.sts.user_statistic_init_value;
        skip_bailout_iterations = s.fns.skip_bailout_iterations;      
        bodyLocation = s.fns.igs.bodyLocation;
        bodyGravity = s.fns.igs.bodyGravity;
        inertia_contribution = s.fns.igs.inertia_contribution;
        initial_inertia = s.fns.igs.initial_inertia;
        inertia_exponent = s.fns.igs.inertia_exponent;
        pull_scaling_function = s.fns.igs.pull_scaling_function;
        time_step = s.fns.igs.time_step;
 
    }
    
    @Override
    public int getVersion() {

        return 1073;

    }

    @Override
    public boolean isJulia() {

        return false;

    }
    
    /**
     * @return the user_relaxation_formula
     */
    public String getUserRelaxationFormula() {
        return user_relaxation_formula;
    }

    /**
     * @return the user_nova_addend_formula
     */
    public String getUserNovaAddendFormula() {
        return user_nova_addend_formula;
    }

    /**
     * @return the alpha2
     */
    public double getAlpha2() {
        return alpha2;
    }

    /**
     * @return the beta2
     */
    public double getBeta2() {
        return beta2;
    }

    /**
     * @return the gradient_offset
     */
    public int getGSGradientOffset() {
        return gradient_offset;
    }

    /**
     * @return the lambda_formula_conditions
     */
    public String[] getLambdaFormulaConditions() {
        return lambda_formula_conditions;
    }

    /**
     * @return the lambda_formula_condition_formula
     */
    public String[] getLambdaFormulaConditionFormula() {
        return lambda_formula_condition_formula;
    }

    /**
     * @return the lyapunovFunction
     */
    public String getLyapunovFunction() {
        return lyapunovFunction;
    }

    /**
     * @return the lyapunovExponentFunction
     */
    public String getLyapunovExponentFunction() {
        return lyapunovExponentFunction;
    }

    /**
     * @return the lyapunovVariableId
     */
    public int getLyapunovVariableId() {
        return lyapunovVariableId;
    }

    /**
     * @return the magnetPendVariableId
     */
    public int getMagnetPendVariableId() {
        return magnetPendVariableId;
    }

    /**
     * @return the user_statistic_init_value
     */
    public String getUserStatisticInitValue() {
        return user_statistic_init_value;
    }

    /**
     * @return the skip_bailout_iterations
     */
    public int getSkipBailoutIterations() {
        return skip_bailout_iterations;
    }

    /**
     * @return the bodyLocation
     */
    public double[][] getBodyLocation() {
        return bodyLocation;
    }

    /**
     * @return the bodyGravity
     */
    public double[][] getBodyGravity() {
        return bodyGravity;
    }

    /**
     * @return the inertia_contribution
     */
    public double[] getInertiaContribution() {
        return inertia_contribution;
    }

    /**
     * @return the initial_inertia
     */
    public double[] getInitialInertia() {
        return initial_inertia;
    }

    /**
     * @return the inertia_exponent
     */
    public double getInertiaExponent() {
        return inertia_exponent;
    }

    /**
     * @return the pull_scaling_function
     */
    public int getPullScalingFunction() {
        return pull_scaling_function;
    }

    /**
     * @return the time_step
     */
    public double[] getTimeStep() {
        return time_step;
    }
    
}
