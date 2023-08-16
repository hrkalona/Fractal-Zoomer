package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1085 extends SettingsFractals1084 implements Serializable {
    private static final long serialVersionUID = 1230304L;
    private boolean julia;
    private String xJuliaCenter;
    private String yJuliaCenter;
    private boolean blending_reversed_colors;

    private double min_contour;

    private boolean perturbation_theory;
    private boolean greedy_drawing_algorithm;

    public SettingsFractals1085(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm) {
        super(s);
        julia = s.fns.julia;
        xJuliaCenter = s.xJuliaCenter.toString(true);
        yJuliaCenter = s.yJuliaCenter.toString(true);
        blending_reversed_colors = s.color_blending.blending_reversed_colors;
        min_contour = s.pps.cns.min_contour;
        this.perturbation_theory = perturbation_theory;
        this.greedy_drawing_algorithm = greedy_drawing_algorithm;
    }

    @Override
    public int getVersion() {

        return 1085;

    }

    @Override
    public boolean isJulia() {

        return julia;

    }

    public String getXJuliaCenterStr() {

        return xJuliaCenter;

    }

    public String getYJuliaCenterStr() {

        return yJuliaCenter;

    }

    public boolean getBlendingReversedColors() {
        return blending_reversed_colors;
    }

    public double getMinContour() {
        return min_contour;
    }

    public boolean getPerturbationTheory() {
        return perturbation_theory;
    }

    public boolean getGreedyDrawingAlgorithm() {
        return greedy_drawing_algorithm;
    }
}
