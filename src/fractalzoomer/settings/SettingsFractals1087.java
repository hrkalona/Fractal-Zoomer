package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1087 extends SettingsFractals1086 implements Serializable {
    private static final long serialVersionUID = 653109724L;

    private double normalMapDistanceEstimatorfactor;
    private boolean hs_remove_outliers;
    private int hs_outliers_method;
    private int traplastXItems;
    private int statisticlastXItems;
    private int fakeDEfadingAlgorithm;

    private int normalMapDEOffset;
    private double normalMapDEOffsetFactor;
    private boolean normalMapDEUseColorPerDepth;


    public SettingsFractals1087(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode) {
        super(s, perturbation_theory,  greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode);
        normalMapDistanceEstimatorfactor = s.pps.sts.normalMapDistanceEstimatorfactor;
        hs_remove_outliers = s.pps.hss.hs_remove_outliers;
        hs_outliers_method = s.pps.hss.hs_outliers_method;
        traplastXItems = s.pps.ots.lastXItems;
        statisticlastXItems = s.pps.sts.lastXItems;
        normalMapDEOffset = s.pps.sts.normalMapDEOffset;
        normalMapDEOffsetFactor = s.pps.sts.normalMapDEOffsetFactor;
        normalMapDEUseColorPerDepth = s.pps.sts.normalMapDEUseColorPerDepth;
        fakeDEfadingAlgorithm = s.pps.fdes.fade_algorithm;
    }

    @Override
    public int getVersion() {

        return 1087;

    }

    public double getNormalMapDistanceEstimatorfactor() {
        return normalMapDistanceEstimatorfactor;
    }

    public boolean getHsRemoveOutliers() {
        return hs_remove_outliers;
    }

    public int getHsOutliersMethod() {
        return hs_outliers_method;
    }

    public int getTraplastXItems() {
        return traplastXItems;
    }

    public int getStatisticlastXItems() {
        return statisticlastXItems;
    }

    public int getNormalMapDEOffset() {
        return normalMapDEOffset;
    }

    public double getNormalMapDEOffsetFactor() {
        return normalMapDEOffsetFactor;
    }

    public boolean getNormalMapDEUseColorPerDepth() {
        return normalMapDEUseColorPerDepth;
    }

    public int getFakeDEfadingAlgorithm() {
        return fakeDEfadingAlgorithm;
    }

}
