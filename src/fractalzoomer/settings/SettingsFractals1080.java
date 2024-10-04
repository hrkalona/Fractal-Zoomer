package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1080 extends SettingsFractals1079 implements Serializable {
    private static final long serialVersionUID = 6548219321L;

    private int checkType;
    private double sfm1;
    private double sfm2;
    private double sfn1;
    private double sfn2;
    private double sfn3;
    private double sfa;
    private double sfb;

    private double rootIterationsScaling;
    private boolean rootShading;
    private int rootContourColorMethod;
    private double rootBlending;
    private int rootShadingFunction;
    private boolean revertRootShading;
    private boolean highlightRoots;
    private boolean rootSmooting;
    private int[] rootColors;

    private int MagnetColorOffset;

    private int convergent_bailout_test_algorithm;
    private String convergent_bailout_test_user_formula;
    private String convergent_bailout_test_user_formula2;
    private int convergent_bailout_test_comparison;
    private double convergent_n_norm;

    private int twlFunction;
    private double[] twlPoint;

    private int skip_convergent_bailout_iterations;

    public SettingsFractals1080(Settings s) {
        super(s);
        checkType = s.pps.ots.checkType;
        sfm1 = s.pps.ots.sfm1;
        sfm2 = s.pps.ots.sfm2;
        sfn1 = s.pps.ots.sfn1;
        sfn2 = s.pps.ots.sfn2;
        sfn3 = s.pps.ots.sfn3;
        sfa = s.pps.ots.sfa;
        sfb = s.pps.ots.sfb;

        rootIterationsScaling = s.pps.sts.rootIterationsScaling;
        rootShading = s.pps.sts.rootShading;
        rootContourColorMethod = s.pps.sts.rootContourColorMethod;
        rootBlending = s.pps.sts.rootBlending;
        rootShadingFunction = s.pps.sts.rootShadingFunction;
        revertRootShading = s.pps.sts.revertRootShading;
        highlightRoots = s.pps.sts.highlightRoots;
        rootSmooting = s.pps.sts.rootSmoothing;
        rootColors = s.pps.sts.rootColors;

        MagnetColorOffset = s.MagnetColorOffset;

        convergent_bailout_test_algorithm = s.fns.cbs.convergent_bailout_test_algorithm;
        convergent_bailout_test_user_formula = s.fns.cbs.convergent_bailout_test_user_formula;
        convergent_bailout_test_user_formula2 = s.fns.cbs.convergent_bailout_test_user_formula2;
        convergent_bailout_test_comparison = s.fns.cbs.convergent_bailout_test_comparison;
        convergent_n_norm = s.fns.cbs.convergent_n_norm;
        skip_convergent_bailout_iterations = s.fns.skip_convergent_bailout_iterations;

        twlFunction = s.pps.sts.twlFunction;
        twlPoint = s.pps.sts.twlPoint;
    }

    @Override
    public int getVersion() {

        return 1080;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public int getCheckType() {
        return checkType;
    }

    public double getSfm1() {
        return sfm1;
    }

    public double getSfm2() {
        return sfm2;
    }

    public double getSfn1() {
        return sfn1;
    }

    public double getSfn2() {
        return sfn2;
    }

    public double getSfn3() {
        return sfn3;
    }

    public double getSfa() {
        return sfa;
    }

    public double getSfb() {
        return sfb;
    }

    public double getRootIterationsScaling() {
        return rootIterationsScaling;
    }

    public boolean getRootShading() {
        return rootShading;
    }

    public int getRootContourColorMethod() {
        return rootContourColorMethod;
    }

    public double getRootBlending() {
        return rootBlending;
    }

    public int getRootShadingFunction() {
        return rootShadingFunction;
    }

    public boolean getRevertRootShading() {
        return revertRootShading;
    }

    public boolean getHighlightRoots() {
        return highlightRoots;
    }

    public boolean getRootSmooting() {
        return rootSmooting;
    }

    public int[] getRootColors() {
        return rootColors;
    }

    public int getMagnetColorOffset() {
        return MagnetColorOffset;
    }

    public int getConvergentBailoutTestAlgorithm() {
        return convergent_bailout_test_algorithm;
    }

    public String getConvergentBailoutTestUserFormula() {
        return convergent_bailout_test_user_formula;
    }

    public String getConvergentBailoutTestUserFormula2() {
        return convergent_bailout_test_user_formula2;
    }

    public int getConvergentBailoutTestComparison() {
        return convergent_bailout_test_comparison;
    }

    public double getConvergentNNorm() {
        return convergent_n_norm;
    }

    public int getTwlFunction() {
        return twlFunction;
    }

    public double[] getTwlPoint() {
        return twlPoint;
    }


    public int getSkipConvergentBailoutIterations() {
        return skip_convergent_bailout_iterations;
    }

}
