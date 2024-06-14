package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1091 extends SettingsFractals1090 implements Serializable {
    private static final long serialVersionUID = 958196341248L;
    private int en_color_blending;
    private boolean en_reverse_color_blending;
    private int hs_color_blending;
    private boolean hs_reverse_color_blending;

    private int nde_color_blending;
    private boolean nde_reverse_color_blending;

    private int of_color_blending;
    private boolean of_reverse_color_blending;

    private int rp_color_blending;
    private boolean rp_reverse_color_blending;

    private int trapOffset;


    public SettingsFractals1091(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row);

        en_color_blending = s.pps.ens.en_color_blending;
        en_reverse_color_blending = s.pps.ens.en_reverse_color_blending;

        hs_color_blending = s.pps.hss.hs_color_blending;
        hs_reverse_color_blending = s.pps.hss.hs_reverse_color_blending;

        nde_color_blending = s.pps.ndes.nde_color_blending;
        nde_reverse_color_blending = s.pps.ndes.nde_reverse_color_blending;

        of_color_blending = s.pps.ofs.of_color_blending;
        of_reverse_color_blending = s.pps.ofs.of_reverse_color_blending;

        rp_color_blending = s.pps.rps.rp_color_blending;
        rp_reverse_color_blending = s.pps.rps.rp_reverse_color_blending;

        trapOffset = s.pps.ots.trapOffset;
    }

    @Override
    public int getVersion() {

        return 1091;

    }

    public int getEn_color_blending() {
        return en_color_blending;
    }

    public boolean getEn_reverse_color_blending() {
        return en_reverse_color_blending;
    }

    public int getHs_color_blending() {
        return hs_color_blending;
    }

    public boolean getHs_reverse_color_blending() {
        return hs_reverse_color_blending;
    }

    public int getNde_color_blending() {
        return nde_color_blending;
    }

    public boolean getNde_reverse_color_blending() {
        return nde_reverse_color_blending;
    }

    public int getOf_color_blending() {
        return of_color_blending;
    }

    public boolean getOf_reverse_color_blending() {
        return of_reverse_color_blending;
    }

    public int getRp_color_blending() {
        return rp_color_blending;
    }

    public boolean getRp_reverse_color_blending() {
        return rp_reverse_color_blending;
    }

    public int getTrapOffset() {
        return trapOffset;
    }
}
