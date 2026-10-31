package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1096 extends SettingsFractals1095 implements Serializable {
    private static final long serialVersionUID = 72431313L;
    private boolean applyStatisticIntensityDuringColoring;


    public SettingsFractals1096(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row, boolean split_into_rectagle_areas, int rectangle_area_split_algorithm, int area_dimension_x, int area_dimension_y) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row, split_into_rectagle_areas, rectangle_area_split_algorithm, area_dimension_x, area_dimension_y);
        applyStatisticIntensityDuringColoring = s.pps.sts.applyIntensityDuringColoring;
    }

    @Override
    public int getVersion() {

        return 1096;

    }

    public boolean getApplyStatisticIntensityDuringColoring() {
        return applyStatisticIntensityDuringColoring;
    }
}
