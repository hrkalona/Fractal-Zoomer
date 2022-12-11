package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1086 extends SettingsFractals1085 implements Serializable {
    private static final long serialVersionUID = 1230871304L;
    private int brute_force_alg;
    private int greedy_drawing_algorithm_id;
    private boolean greedy_algorithm_check_iter_data;
    private String userDefinedCode;

    public SettingsFractals1086(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode) {
        super(s, perturbation_theory,  greedy_drawing_algorithm);
        this.brute_force_alg = brute_force_alg;
        this.greedy_drawing_algorithm_id = greedy_drawing_algorithm_id;
        this.greedy_algorithm_check_iter_data = greedy_algorithm_check_iter_data;
        this.userDefinedCode = userDefinedCode;
    }

    @Override
    public int getVersion() {

        return 1086;

    }

    public int getBruteForceAlg() {
        return brute_force_alg;
    }

    public int getGreedyDrawingAlgorithmId() {
        return greedy_drawing_algorithm_id;
    }

    public boolean getGreedyAlgorithmCheckIterData() {
        return greedy_algorithm_check_iter_data;
    }

    public String getUserDefinedCode() {
        return userDefinedCode;
    }

}
