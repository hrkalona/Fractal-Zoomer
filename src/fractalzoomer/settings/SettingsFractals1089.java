package fractalzoomer.settings;

import fractalzoomer.main.app_settings.CosinePaletteSettings;
import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;
import java.util.ArrayList;

public class SettingsFractals1089 extends SettingsFractals1088 implements Serializable {
    private static final long serialVersionUID = 0534110523451L;
    private int guess_blocks;
    private int bumpfractionalTransfer;
    private int bumpfractionalSmoothing;
    private int bumpfractionalTransferMode;
    private int contourfractionalTransfer;
    private int contourfractionalSmoothing;
    private CosinePaletteSettings inColoringIQ;
    private CosinePaletteSettings outColoringIQ;
    private int iso_color_type;
    private int grid_color_type;
    private int circle_color_type;

    private ArrayList<Double> inflections_re;
    private ArrayList<Double> inflections_im;
    private double inflectionsPower;
    private int smoothing_fractional_transfer_method;

    private int specularReflectionMethod;
    private int lightfractionalTransfer;
    private int lightfractionalSmoothing;
    private int lightfractionalTransferMode;

    private boolean useNumericalDem;
    private double distanceFactor;
    private int distanceOffset;
    private int differencesMethod;

    private double ndes_noise_reducing_factor;
    private double numerical_blending;
    private boolean cap_to_palette_length;
    private double out_color_density;
    private double in_color_density;

    private boolean slopes;
    private double SlopeAngle;
    private double SlopePower;
    private double SlopeRatio;

    private double slopes_noise_reducing_factor;
    private int slopescolorMode;
    private double slope_blending;
    private int slopesfractionalTransfer;
    private int slopesfractionalSmoothing;
    private int slopesfractionalTransferMode;

    private int slopesheightTransfer;
    private double slopesheightTransferFactor;
    private boolean applyShading;
    private double saturation_adjustment;
    private boolean mapNormReImWithAbsScale;
    private int shadingType;

    private int shadingColorAlgorithm;
    private float hsb_constant_b;
    private float hsb_constant_s;
    private double lchab_constant_l;
    private double lchab_constant_c;
    private double lchuv_constant_l;
    private double lchuv_constant_c;
    private int domain_height_normalization_method;
    private boolean invertShading;
    private double shadingPercent;
    private double patternScale;
    private int checkerNormType;
    private double checkerNormValue;
    private double aaSigmaR;
    private double aaSigmaS;
    private double bluringSigmaR;
    private double bluringSigmaS;
    private int blurringKernelSelection;
    public SettingsFractals1089(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks) {
        super(s, perturbation_theory,  greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode);
        this.guess_blocks = guess_blocks;

        bumpfractionalTransfer = s.pps.bms.fractionalTransfer;
        bumpfractionalSmoothing = s.pps.bms.fractionalSmoothing;
        bumpfractionalTransferMode = s.pps.bms.fractionalTransferMode;
        contourfractionalTransfer = s.pps.cns.fractionalTransfer;
        contourfractionalSmoothing = s.pps.cns.fractionalSmoothing;
        inColoringIQ = s.gps.inColoringIQ;
        outColoringIQ = s.gps.outColoringIQ;
        iso_color_type = s.ds.iso_color_type;
        grid_color_type = s.ds.grid_color_type;
        circle_color_type = s.ds.circle_color_type;

        inflections_re = s.fns.inflections_re;
        inflections_im = s.fns.inflections_im;
        inflectionsPower = s.fns.inflectionsPower;
        smoothing_fractional_transfer_method = s.fns.smoothing_fractional_transfer_method;

        specularReflectionMethod = s.pps.ls.specularReflectionMethod;
        lightfractionalTransfer = s.pps.ls.fractionalTransfer;
        lightfractionalSmoothing = s.pps.ls.fractionalSmoothing;
        lightfractionalTransferMode = s.pps.ls.fractionalTransferMode;

        useNumericalDem = s.pps.ndes.useNumericalDem;
        distanceFactor = s.pps.ndes.distanceFactor;
        distanceOffset = s.pps.ndes.distanceOffset;
        differencesMethod = s.pps.ndes.differencesMethod;

        ndes_noise_reducing_factor = s.pps.ndes.n_noise_reducing_factor;
        numerical_blending = s.pps.ndes.numerical_blending;
        cap_to_palette_length = s.pps.ndes.cap_to_palette_length;
        out_color_density = s.ps.color_density;
        in_color_density = s.ps2.color_density;

        slopes = s.pps.ss.slopes;
        SlopeAngle = s.pps.ss.SlopeAngle;
        SlopePower = s.pps.ss.SlopePower;
        SlopeRatio = s.pps.ss.SlopeRatio;

        slopes_noise_reducing_factor = s.pps.ss.s_noise_reducing_factor;
        slopescolorMode = s.pps.ss.colorMode;
        slope_blending = s.pps.ss.slope_blending;
        slopesfractionalTransfer = s.pps.ss.fractionalTransfer;
        slopesfractionalSmoothing = s.pps.ss.fractionalSmoothing;
        slopesfractionalTransferMode = s.pps.ss.fractionalTransferMode;

        slopesheightTransfer = s.pps.ss.heightTransfer;
        slopesheightTransferFactor = s.pps.ss.heightTransferFactor;

        applyShading = s.ds.applyShading;
        saturation_adjustment = s.ds.saturation_adjustment;
        mapNormReImWithAbsScale = s.ds.mapNormReImWithAbsScale;
        shadingType = s.ds.shadingType;
        shadingColorAlgorithm = s.ds.shadingColorAlgorithm;
        hsb_constant_b = s.hsb_constant_b;
        hsb_constant_s = s.hsb_constant_s;
        lchab_constant_l = s.lchab_constant_l;
        lchab_constant_c = s.lchab_constant_c;
        lchuv_constant_l = s.lchuv_constant_l;
        lchuv_constant_c = s.lchuv_constant_c;

        domain_height_normalization_method = s.ds.domain_height_normalization_method;
        invertShading = s.ds.invertShading;
        shadingPercent = s.ds.shadingPercent;

        patternScale = s.pps.sts.patternScale;
        checkerNormType = s.pps.sts.checkerNormType;
        checkerNormValue = s.pps.sts.checkerNormValue;

        aaSigmaR = s.fs.aaSigmaR;
        aaSigmaS = 0; //Unused
        bluringSigmaR = s.fs.bluringSigmaR;
        bluringSigmaS = s.fs.bluringSigmaS;
        blurringKernelSelection = s.fs.blurringKernelSelection;

    }

    @Override
    public int getVersion() {

        return 1089;

    }

    public int getGuessBlocks() {
        return guess_blocks;
    }

    public int getBumpfractionalTransfer() {
        return bumpfractionalTransfer;
    }

    public int getBumpfractionalSmoothing() {
        return bumpfractionalSmoothing;
    }

    public int getBumpfractionalTransferMode() {
        return bumpfractionalTransferMode;
    }

    public int getContourfractionalTransfer() {
        return contourfractionalTransfer;
    }

    public int getContourfractionalSmoothing() {
        return contourfractionalSmoothing;
    }

    public CosinePaletteSettings getInColoringIQ() {
        return inColoringIQ;
    }

    public CosinePaletteSettings getOutColoringIQ() {
        return outColoringIQ;
    }

    public int getIsoColorType() {
        return iso_color_type;
    }

    public int getGridColorType() {
        return grid_color_type;
    }

    public int getCircleColorType() {
        return circle_color_type;
    }

    public ArrayList<Double> getInflectionsRe() {
        return inflections_re;
    }

    public ArrayList<Double> getInflectionsIm() {
        return inflections_im;
    }

    public double getInflectionsPower() {
        return inflectionsPower;
    }

    public int getSmoothingFractionalTransferMethod() {
        return smoothing_fractional_transfer_method;
    }

    public int getSpecularReflectionMethod() {
        return specularReflectionMethod;
    }

    public int getLightfractionalTransfer() {
        return lightfractionalTransfer;
    }

    public int getLightfractionalSmoothing() {
        return lightfractionalSmoothing;
    }

    public int getLightfractionalTransferMode() {
        return lightfractionalTransferMode;
    }

    public boolean getUseNumericalDem() {
        return useNumericalDem;
    }

    public double getDistanceFactor() {
        return distanceFactor;
    }

    public int getDistanceOffset() {
        return distanceOffset;
    }

    public int getDifferencesMethod() {
        return differencesMethod;
    }

    public double getNdesNoiseReducingFactor() {
        return ndes_noise_reducing_factor;
    }

    public double getNumericalBlending() {
        return numerical_blending;
    }

    public boolean getCapToPaletteLength() {
        return cap_to_palette_length;
    }

    public double getOutColorDensity() {
        return out_color_density;
    }

    public double getInColorDensity() {
        return in_color_density;
    }

    public boolean getSlopes() {
        return slopes;
    }

    public double getSlopeAngle() {
        return SlopeAngle;
    }

    public double getSlopePower() {
        return SlopePower;
    }

    public double getSlopeRatio() {
        return SlopeRatio;
    }

    public double getSlopesNoiseReducingFactor() {
        return slopes_noise_reducing_factor;
    }

    public int getSlopescolorMode() {
        return slopescolorMode;
    }

    public double getSlopeBlending() {
        return slope_blending;
    }

    public int getSlopesfractionalTransfer() {
        return slopesfractionalTransfer;
    }

    public int getSlopesfractionalSmoothing() {
        return slopesfractionalSmoothing;
    }

    public int getSlopesfractionalTransferMode() {
        return slopesfractionalTransferMode;
    }

    public int getSlopesheightTransfer() {
        return slopesheightTransfer;
    }

    public double getSlopesheightTransferFactor() {
        return slopesheightTransferFactor;
    }

    public boolean getApplyShading() {
        return applyShading;
    }

    public double getSaturationAdjustment() {
        return saturation_adjustment;
    }

    public boolean getMapNormReImWithAbsScale() {
        return mapNormReImWithAbsScale;
    }

    public int getShadingType() {
        return shadingType;
    }

    public float getHsbConstantB() {
        return hsb_constant_b;
    }

    public float getHsbConstantS() {
        return hsb_constant_s;
    }

    public double getLchabConstantL() {
        return lchab_constant_l;
    }

    public double getLchabConstantC() {
        return lchab_constant_c;
    }

    public double getLchuvConstantL() {
        return lchuv_constant_l;
    }

    public double getLchuvConstantC() {
        return lchuv_constant_c;
    }

    public int getShadingColorAlgorithm() {
        return shadingColorAlgorithm;
    }

    public int getDomainHeightNormalizationMethod() {
        return domain_height_normalization_method;
    }

    public boolean getInvertShading() {
        return invertShading;
    }

    public double getShadingPercent() {
        return shadingPercent;
    }

    public double getPatternScale() {
        return patternScale;
    }

    public int getCheckerNormType() {
        return checkerNormType;
    }

    public double getCheckerNormValue() {
        return checkerNormValue;
    }

    public double getAaSigmaR() {
        return aaSigmaR;
    }

    public double getAaSigmaS() {
        return aaSigmaS;
    }

    public double getBluringSigmaR() {
        return bluringSigmaR;
    }

    public double getBluringSigmaS() {
        return bluringSigmaS;
    }

    public int getBlurringKernelSelection() {
        return blurringKernelSelection;
    }
}
