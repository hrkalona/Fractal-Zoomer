package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1088 extends SettingsFractals1087 implements Serializable {
    private static final long serialVersionUID = 561930523451L;

    private double[] variable_re;
    private double[] variable_im;
    private boolean normalMapCombineWithOtherStatistics;


    public SettingsFractals1088(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode) {
        super(s, perturbation_theory,  greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode);
        variable_re = s.fns.variable_re;
        variable_im = s.fns.variable_im;
        normalMapCombineWithOtherStatistics = s.pps.sts.normalMapCombineWithOtherStatistics;
    }

    @Override
    public int getVersion() {

        return 1088;

    }

    public double[] getVariableRe() {
        return variable_re;
    }

    public double[] getVariableIm() {
        return variable_im;
    }

    public boolean getNormalMapCombineWithOtherStatistics() {
        return normalMapCombineWithOtherStatistics;
    }

}
