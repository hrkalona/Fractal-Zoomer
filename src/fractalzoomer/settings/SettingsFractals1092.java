package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1092 extends SettingsFractals1091 implements Serializable {
    private static final long serialVersionUID = 87205941159L;
    private double convergent_norm_a;
    private double convergent_norm_b;
    private double domain_coloring_norm_a;
    private double domain_coloring_norm_b;
    private double norm_a;
    private double norm_b;
    private boolean banded;
    private String plane_transform_center_hp_re;
    private String plane_transform_center_hp_im;
    private double trap_norm_a;
    private double trap_norm_b;
    private boolean flip_real;
    private boolean flip_imaginary;
    private double lang_norm_a;
    private double lang_norm_b;
    private double atom_norm_a;
    private double atom_norm_b;
    private double checker_norm_a;
    private double checker_norm_b;
    private boolean root_scaling_cap1;
    private int smoothing_color_selection;


    public SettingsFractals1092(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row);

        convergent_norm_a = s.fns.cbs.norm_a;
        convergent_norm_b = s.fns.cbs.norm_b;
        domain_coloring_norm_a = s.ds.normA;
        domain_coloring_norm_b = s.ds.normB;
        norm_a = s.fns.norm_a;
        norm_b = s.fns.norm_b;
        banded = s.fns.banded;
        plane_transform_center_hp_re = s.fns.plane_transform_center_hp[0].toString(true);
        plane_transform_center_hp_im = s.fns.plane_transform_center_hp[1].toString(true);
        trap_norm_a = s.pps.ots.trapNormA;
        trap_norm_b = s.pps.ots.trapNormB;
        flip_real = s.flip_real;
        flip_imaginary = s.flip_imaginary;
        lang_norm_a = s.pps.sts.langNormA;
        lang_norm_b = s.pps.sts.langNormB;
        atom_norm_a = s.pps.sts.atomDomainNormA;
        atom_norm_b = s.pps.sts.atomDomainNormB;
        checker_norm_a = s.pps.sts.checkerNormA;
        checker_norm_b = s.pps.sts.checkerNormB;
        root_scaling_cap1 = s.pps.sts.rootScalingCapto1;
        smoothing_color_selection = s.fns.smoothing_color_selection;
    }

    @Override
    public int getVersion() {

        return 1092;

    }

    public double getConvergent_norm_a() {
        return convergent_norm_a;
    }

    public double getConvergent_norm_b() {
        return convergent_norm_b;
    }

    public double getDomain_coloring_norm_a() {
        return domain_coloring_norm_a;
    }

    public double getDomain_coloring_norm_b() {
        return domain_coloring_norm_b;
    }

    public double getNorm_a() {
        return norm_a;
    }

    public double getNorm_b() {
        return norm_b;
    }

    public boolean getBanded() {
        return banded;
    }

    public String getPlane_transform_center_hp_re() {
        return plane_transform_center_hp_re;
    }

    public String getPlane_transform_center_hp_im() {
        return plane_transform_center_hp_im;
    }

    public double getTrap_norm_a() {
        return trap_norm_a;
    }

    public double getTrap_norm_b() {
        return trap_norm_b;
    }

    public boolean getFlip_real() {
        return flip_real;
    }

    public boolean getFlip_imaginary() {
        return flip_imaginary;
    }

    public double getLang_norm_a() {
        return lang_norm_a;
    }

    public double getLang_norm_b() {
        return lang_norm_b;
    }

    public double getAtom_norm_a() {
        return atom_norm_a;
    }

    public double getAtom_norm_b() {
        return atom_norm_b;
    }

    public double getChecker_norm_a() {
        return checker_norm_a;
    }

    public double getChecker_norm_b() {
        return checker_norm_b;
    }

    public boolean getRoot_scaling_cap1() {
        return root_scaling_cap1;
    }

    public int getSmoothing_color_selection() {
        return smoothing_color_selection;
    }
}
