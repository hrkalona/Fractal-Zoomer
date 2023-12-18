package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1090 extends SettingsFractals1089 implements Serializable {
    private static final long serialVersionUID = 053203451L;

    private double convergent_bailout;
    private int rank_order_digits_grouping;

    private int blocks_format;
    private boolean two_step_refinement;
    private boolean one_chunk_per_row;

    public SettingsFractals1090(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks);

        this.blocks_format = blocks_format;
        convergent_bailout = s.fns.convergent_bailout;
        rank_order_digits_grouping = s.pps.hss.rank_order_digits_grouping;
        this.two_step_refinement = two_step_refinement;
        this.one_chunk_per_row = one_chunk_per_row;

    }

    public double getConvergentBailout() {
        return convergent_bailout;
    }

    public int getRankOrderDigitsGrouping() {
        return rank_order_digits_grouping;
    }

    public int getBlocksFormat() {
        return blocks_format;
    }

    public boolean getTwoStepRefinement() {
        return two_step_refinement;
    }

    public boolean getOneChunkPerRow() {
        return one_chunk_per_row;
    }

    @Override
    public int getVersion() {

        return 1090;

    }
}
