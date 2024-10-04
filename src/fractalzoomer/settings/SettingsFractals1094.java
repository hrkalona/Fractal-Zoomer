package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

public class SettingsFractals1094 extends SettingsFractals1093 implements Serializable {
    private static final long serialVersionUID = 4384014L;
    private double[] zenex_re;
    private double[] zenex_im;
    private boolean blendNormalPaletteWithGeneratedPaletteOutColoring;
    private boolean blendNormalPaletteWithGeneratedPaletteInColoring;
    private double blendingOutColoring;
    private double blendingInColoring;
    private String outcoloring_multiwave_user_palette;
    private String incoloring_multiwave_user_palette;
    private String outcoloring_infinite_wave_user_palette;
    private String incoloring_infinite_wave_user_palette;
    private String outcoloring_simple_multiwave_user_palette;
    private String incoloring_simple_multiwave_user_palette;
    private int GeneratedOutColoringPaletteOffset;
    private int GeneratedInColoringPaletteOffset;
    private double GeneratedOutColoringPaletteFactor;
    private double GeneratedInColoringPaletteFactor;
    private int gradient_length;
    private boolean histogram_use_integer_iterations;
    private double histogram_mapping_exponent;
    private int global_color_space;
    private double gamma;
    private double intesity_exponent;
    private double interpolation_exponent;
    private boolean color_smoothing;
    private boolean slopesApplyWidthScaling;
    private boolean ndesApplyWidthScaling;

    public SettingsFractals1094(Settings s, boolean perturbation_theory, boolean greedy_drawing_algorithm, int brute_force_alg, int greedy_drawing_algorithm_id, boolean greedy_algorithm_check_iter_data, String userDefinedCode, int guess_blocks, int blocks_format, boolean two_step_refinement, boolean one_chunk_per_row, boolean split_into_rectagle_areas, int rectangle_area_split_algorithm, int area_dimension_x, int area_dimension_y) {
        super(s, perturbation_theory, greedy_drawing_algorithm, brute_force_alg, greedy_drawing_algorithm_id, greedy_algorithm_check_iter_data, userDefinedCode, guess_blocks, blocks_format, two_step_refinement,  one_chunk_per_row, split_into_rectagle_areas, rectangle_area_split_algorithm, area_dimension_x, area_dimension_y);

        zenex_re = s.fns.zenex_re;
        zenex_im = s.fns.zenex_im;
        blendNormalPaletteWithGeneratedPaletteOutColoring = s.gps.blendNormalPaletteWithGeneratedPaletteOutColoring;
        blendNormalPaletteWithGeneratedPaletteInColoring = s.gps.blendNormalPaletteWithGeneratedPaletteInColoring;
        blendingOutColoring = s.gps.blendingOutColoring;
        blendingInColoring = s.gps.blendingInColoring;
        outcoloring_multiwave_user_palette = s.gps.outcoloring_multiwave_user_palette;
        incoloring_multiwave_user_palette = s.gps.incoloring_multiwave_user_palette;
        outcoloring_infinite_wave_user_palette = s.gps.outcoloring_infinite_wave_user_palette;
        incoloring_infinite_wave_user_palette = s.gps.incoloring_infinite_wave_user_palette;
        outcoloring_simple_multiwave_user_palette = s.gps.outcoloring_simple_multiwave_user_palette;
        incoloring_simple_multiwave_user_palette = s.gps.incoloring_simple_multiwave_user_palette;
        GeneratedOutColoringPaletteOffset = s.gps.GeneratedOutColoringPaletteOffset;
        GeneratedInColoringPaletteOffset = s.gps.GeneratedInColoringPaletteOffset;
        GeneratedOutColoringPaletteFactor = s.gps.GeneratedOutColoringPaletteFactor;
        GeneratedInColoringPaletteFactor = s.gps.GeneratedInColoringPaletteFactor;
        gradient_length = s.gs.gradient_length;
        histogram_use_integer_iterations = s.pps.hss.use_integer_iterations;
        histogram_mapping_exponent = s.pps.hss.mapping_exponent;
        global_color_space = s.color_space;
        gamma = s.gamma;
        intesity_exponent = s.intesity_exponent;
        interpolation_exponent = s.interpolation_exponent;
        color_smoothing = s.color_smoothing;
        slopesApplyWidthScaling = s.pps.ss.applyWidthScaling;
        ndesApplyWidthScaling = s.pps.ndes.applyWidthScaling;
    }

    @Override
    public int getVersion() {

        return 1094;

    }

    public double[] getZenexRe() {
        return zenex_re;
    }

    public double[] getZenexIm() {
        return zenex_im;
    }

    public boolean getBlendNormalPaletteWithGeneratedPaletteOutColoring() {
        return blendNormalPaletteWithGeneratedPaletteOutColoring;
    }

    public boolean getBlendNormalPaletteWithGeneratedPaletteInColoring() {
        return blendNormalPaletteWithGeneratedPaletteInColoring;
    }

    public double getBlendingOutColoring() {
        return blendingOutColoring;
    }

    public double getBlendingInColoring() {
        return blendingInColoring;
    }

    public String getOutcoloringMultiwaveUserPalette() {
        return outcoloring_multiwave_user_palette;
    }

    public String getIncoloringMultiwaveUserPalette() {
        return incoloring_multiwave_user_palette;
    }

    public String getOutcoloringInfiniteWaveUserPalette() {
        return outcoloring_infinite_wave_user_palette;
    }

    public String getIncoloringInfiniteWaveUserPalette() {
        return incoloring_infinite_wave_user_palette;
    }

    public String getOutcoloringSimpleMultiwaveUserPalette() {
        return outcoloring_simple_multiwave_user_palette;
    }

    public String getIncoloringSimpleMultiwaveUserPalette() {
        return incoloring_simple_multiwave_user_palette;
    }

    public int getGeneratedOutColoringPaletteOffset() {
        return GeneratedOutColoringPaletteOffset;
    }

    public int getGeneratedInColoringPaletteOffset() {
        return GeneratedInColoringPaletteOffset;
    }

    public double getGeneratedOutColoringPaletteFactor() {
        return GeneratedOutColoringPaletteFactor;
    }

    public double getGeneratedInColoringPaletteFactor() {
        return GeneratedInColoringPaletteFactor;
    }

    public int getGradientLength() {
        return gradient_length;
    }

    public boolean getHistogramUseIntegerIterations() {
        return histogram_use_integer_iterations;
    }

    public double getHistogramMappingExponent() {
        return histogram_mapping_exponent;
    }

    public int getGlobalColorSpace() {
        return global_color_space;
    }

    public double getGamma() {
        return gamma;
    }

    public double getIntesityExponent() {
        return intesity_exponent;
    }

    public double getInterpolationExponent() {
        return interpolation_exponent;
    }

    public boolean getColorSmoothing() {
        return color_smoothing;
    }

    public boolean getSlopesApplyWidthScaling() {
        return slopesApplyWidthScaling;
    }

    public boolean getNdesApplyWidthScaling() {
        return ndesApplyWidthScaling;
    }
}
