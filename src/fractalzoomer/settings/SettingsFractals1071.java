
package fractalzoomer.settings;

import fractalzoomer.main.app_settings.Settings;

import java.io.Serializable;

/**
 *
 * @author kaloch
 */
public class SettingsFractals1071 extends SettingsFractals1070 implements Serializable {
    private static final long serialVersionUID = 67310738624L;
    private int color_choice2;
    private int color_cycling_location2;
    private int color_interpolation2;
    private int color_space2;
    private boolean reversed_palette2;
    private double scale_factor_palette_val2;
    private int processing_alg2;
    private double color_intensity2;
    private int transfer_function2;
    private int[][] custom_palette2;
    private boolean usePaletteForInColoring;
    
    private int circleFadeFunction;
    private int gridFadeFunction;
    private double max_norm_re_im_value;
    private int contourMethod;
    private int domain_coloring_mode;
    private double domainProcessingHeightFactor;
    private int domainProcessingTransfer;
    
    private boolean palette_gradient_merge;
    private double gradient_intensity;
    private double palette_blending;
    private int gradient_offset;
    private int merging_type;
    
    private int contourColorMethod;
    
    private int trapColorMethod;
    private double trapIntensity;
    
    private boolean statistic;
    private int statistic_type;
    private double statistic_intensity;
    private double stripeAvgStripeDensity;
    private double cosArgStripeDensity;
    private double cosArgInvStripeDensity;
    private double StripeDenominatorFactor;
    private int statisticGroup;
    private String user_statistic_formula;
    private boolean useAverage;
    private int statistic_escape_type;
    
    private boolean lighting;
    private double lightintensity;
    private double ambientlight;
    private double specularintensity;
    private double shininess;
    private int lightHeightTransfer;
    private double lightHeightTransferFactor;
    private int lightMode;
    private int lightColorMode;
    private double l_noise_reducing_factor;
    private double light_blending;
    private double light_direction;
    private double light_magnitude;
    
    public SettingsFractals1071(Settings s) {
        
        super(s.xCenter.doubleValue(), s.yCenter.doubleValue(), s.size.doubleValue(), s.max_iterations, s.ps.color_choice, s.fractal_color, s.fns.out_coloring_algorithm, s.fns.user_out_coloring_algorithm, s.fns.outcoloring_formula, s.fns.user_outcoloring_conditions, s.fns.user_outcoloring_condition_formula, s.fns.in_coloring_algorithm, s.fns.user_in_coloring_algorithm, s.fns.incoloring_formula, s.fns.user_incoloring_conditions, s.fns.user_incoloring_condition_formula, s.fns.smoothing, s.fns.function, s.fns.bailout_test_algorithm, s.fns.bailout, s.fns.bailout_test_user_formula, s.fns.bailout_test_user_formula2, s.fns.bailout_test_comparison, s.fns.n_norm, s.fns.plane_type, s.fns.apply_plane_on_julia, s.fns.burning_ship, s.fns.z_exponent, s.fns.z_exponent_complex, s.ps.color_cycling_location, s.fns.coefficients, s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, s.fns.rotation, Settings.fromDDArray(s.fns.rotation_center), s.fns.perturbation, s.fns.perturbation_vals, s.fns.variable_perturbation, s.fns.user_perturbation_algorithm, s.fns.user_perturbation_conditions, s.fns.user_perturbation_condition_formula, s.fns.perturbation_user_formula, s.fns.init_val, s.fns.initial_vals, s.fns.variable_init_value, s.fns.user_initial_value_algorithm, s.fns.user_initial_value_conditions, s.fns.user_initial_value_condition_formula, s.fns.initial_value_user_formula, s.fns.mandel_grass, s.fns.mandel_grass_vals, s.fns.z_exponent_nova, s.fns.relaxation, s.fns.nova_method, s.fns.user_formula, s.fns.user_formula2, s.fns.bail_technique, s.fns.user_plane, s.fns.user_plane_algorithm, s.fns.user_plane_conditions, s.fns.user_plane_condition_formula, s.fns.user_formula_iteration_based, s.fns.user_formula_conditions, s.fns.user_formula_condition_formula, s.exterior_de, s.exterior_de_factor, s.height_ratio, s.fns.plane_transform_center, s.fns.plane_transform_angle, s.fns.plane_transform_radius, s.fns.plane_transform_scales, s.fns.plane_transform_angle2, s.fns.plane_transform_sides, s.fns.plane_transform_amount, s.ps.color_intensity, s.fns.escaping_smooth_algorithm, s.fns.converging_smooth_algorithm, s.pps.bms.bump_map, s.pps.bms.bumpMappingStrength, s.pps.bms.bumpMappingDepth, s.pps.bms.lightDirectionDegrees, s.polar_projection, s.circle_period, s.pps.fdes.fake_de, s.pps.fdes.fake_de_factor, s.fns.user_fz_formula, s.fns.user_dfz_formula, s.fns.user_ddfz_formula, s.dem_color, s.special_color, s.special_use_palette_color, s.pps.rps.rainbow_palette, s.pps.rps.rainbow_palette_factor, s.fs.filters, s.fs.filters_options_vals, s.ps.scale_factor_palette_val, s.ps.processing_alg, s.fs.filters_colors, s.fns.coupling, s.fns.user_formula_coupled, s.fns.coupling_method, s.fns.coupling_amplitude, s.fns.coupling_frequency, s.fns.coupling_seed, s.ds.domain_coloring, false, s.ds.domain_coloring_alg, s.inverse_dem, s.pps.fdes.inverse_fake_dem, s.fs.filters_options_extra_vals, s.fs.filters_extra_colors, s.color_smoothing_method, s.fs.filters_order, s.pps.bms.bm_noise_reducing_factor, s.pps.rps.rp_noise_reducing_factor, s.pps.ens.entropy_coloring, s.pps.ens.entropy_palette_factor, s.pps.ens.en_noise_reducing_factor, s.fns.apply_plane_on_julia_seed, s.pps.ofs.offset_coloring, s.pps.ofs.post_process_offset, s.pps.ofs.of_noise_reducing_factor, s.pps.ens.en_blending, s.pps.rps.rp_blending, s.pps.ofs.of_blending, s.pps.ens.entropy_offset, s.pps.rps.rainbow_offset, s.pps.gss.greyscale_coloring, s.pps.gss.gs_noise_reducing_factor, s.ps.transfer_function, s.color_blending.color_blending, s.pps.bms.bump_transfer_function, s.pps.bms.bump_transfer_factor, s.pps.bms.bump_blending, s.pps.bms.bumpProcessing, s.globalIncrementBypass, s.fns.waveType, s.fns.plane_transform_wavelength, s.fns.laguerre_deg, s.ds.iso_distance, s.ds.iso_factor, s.ds.logBase, s.ds.normType, s.ds.gridFactor, s.ds.circlesBlending, s.ds.isoLinesBlendingFactor, s.ds.gridBlending, s.ds.gridColor, s.ds.circlesColor, s.ds.isoLinesColor, s.ds.contourBlending, s.ds.drawColor, s.ds.drawContour, s.ds.drawGrid, s.ds.drawCircles, s.ds.drawIsoLines, s.ds.customDomainColoring, s.ds.colorType, s.ds.contourType, s.pps.ens.entropy_algorithm, s.ds.domainOrder, s.gs.colorA, s.gs.colorB, s.gs.gradient_color_space, s.gs.gradient_interpolation, s.gs.gradient_reversed, s.pps.rps.rainbow_algorithm, s.pps.ots.useTraps, s.pps.ots.trapPoint, s.pps.ots.trapLength, s.pps.ots.trapWidth, s.pps.ots.trapType, s.pps.ots.trapMaxDistance, s.pps.ots.trapBlending, s.pps.ots.trapNorm, false, s.pps.ots.lineType, s.pps.cns.contour_coloring, s.pps.cns.cn_noise_reducing_factor, s.pps.cns.cn_blending, s.pps.cns.contour_algorithm, s.useDirectColor, s.fns.kleinianLine, s.fns.kleinianK, s.fns.kleinianM, s.post_processing_order);
        
        color_choice2 = s.ps2.color_choice;
        color_cycling_location2 = s.ps2.color_cycling_location;
        color_interpolation2 = s.ps2.color_interpolation;
        color_space2 = s.ps2.color_space;
        reversed_palette2 = s.ps2.reversed_palette;
        scale_factor_palette_val2 = s.ps2.scale_factor_palette_val;
        processing_alg2 = s.ps2.processing_alg;
        color_intensity2 = s.ps2.color_intensity;
        transfer_function2 = s.ps2.transfer_function;
        custom_palette2 = s.ps2.custom_palette;
        usePaletteForInColoring = s.usePaletteForInColoring;
        
        circleFadeFunction = s.ds.circleFadeFunction;
        gridFadeFunction = s.ds.gridFadeFunction;
        max_norm_re_im_value = s.ds.max_norm_re_im_value;
        contourMethod = s.ds.contourMethod;
        domain_coloring_mode = s.ds.domain_coloring_mode;
        domainProcessingHeightFactor = s.ds.domainProcessingHeightFactor;
        domainProcessingTransfer = s.ds.domainProcessingTransfer;
        
        palette_gradient_merge = s.pbs.palette_gradient_merge;
        gradient_intensity = s.pbs.gradient_intensity;
        palette_blending = s.pbs.palette_blending;
        gradient_offset = s.pbs.gradient_offset;
        merging_type = s.pbs.merging_type;
        
        contourColorMethod = s.pps.cns.contourColorMethod;
        
        trapColorMethod = s.pps.ots.trapColorMethod;
        trapIntensity = s.pps.ots.trapIntensity;
        
        statistic = s.pps.sts.statistic;
        statistic_type = s.pps.sts.statistic_type;
        statistic_intensity = s.pps.sts.statistic_intensity;
        stripeAvgStripeDensity = s.pps.sts.stripeAvgStripeDensity;
        cosArgStripeDensity = s.pps.sts.cosArgStripeDensity;
        cosArgInvStripeDensity = s.pps.sts.cosArgInvStripeDensity;
        StripeDenominatorFactor = s.pps.sts.StripeDenominatorFactor;
        statisticGroup = s.pps.sts.statisticGroup;
        user_statistic_formula = s.pps.sts.user_statistic_formula;
        useAverage = s.pps.sts.useAverage;
        statistic_escape_type = s.pps.sts.statistic_escape_type;
        
        lighting = s.pps.ls.lighting;
        lightintensity = s.pps.ls.lightintensity;
        ambientlight = s.pps.ls.ambientlight;
        specularintensity = s.pps.ls.specularintensity;
        shininess = s.pps.ls.shininess;
        lightHeightTransfer = s.pps.ls.heightTransfer;
        lightHeightTransferFactor = s.pps.ls.heightTransferFactor;
        lightMode = s.pps.ls.lightMode;
        lightColorMode = s.pps.ls.colorMode;
        l_noise_reducing_factor = s.pps.ls.l_noise_reducing_factor;
        light_blending = s.pps.ls.light_blending;
        light_direction = s.pps.ls.light_direction;
        light_magnitude = s.pps.ls.light_magnitude;
        
    }
 
    
    @Override
    public int getVersion() {

        return 1071;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    public int getColorChoice2() {
        return color_choice2;
    }

    public int getColorCyclingLocation2() {
        return color_cycling_location2;
    }

    public int getColorInterpolation2() {
        return color_interpolation2;
    }

    public int getColorSpace2() {
        return color_space2;
    }

    public boolean getReversedPalette2() {
        return reversed_palette2;
    }

    public double getScaleFactorPaletteVal2() {
        return scale_factor_palette_val2;
    }

    public int getProcessingAlg2() {
        return processing_alg2;
    }

    public double getColorIntensity2() {
        return color_intensity2;
    }

    public int getTransferFunction2() {
        return transfer_function2;
    }

    public int[][] getCustomPalette2() {
        return custom_palette2;
    }

    public boolean getUsePaletteForInColoring() {
        return usePaletteForInColoring;
    }

    public int getCircleFadeFunction() {
        return circleFadeFunction;
    }

    public int getGridFadeFunction() {
        return gridFadeFunction;
    }

    public double getMaxNormReImValue() {
        return max_norm_re_im_value;
    }
    
    public int getContourMethod() {
        return contourMethod;
    }
    
    public int getDomainColoringMode() {
        return domain_coloring_mode;
    }
    
    public double getDomainProcessingHeightFactor() {
        return domainProcessingHeightFactor;
    }
    
    public int getDomainProcessingTransfer() {
        return domainProcessingTransfer;
    }
    
    public boolean getPaletteGradientMerge() {
        return palette_gradient_merge;
    }
    
    public double getGradienIntensity() {
        return gradient_intensity;
    }
    
    public double getPaletteBlending() {
        return palette_blending;
    }
    
    public int getGradientOffset() {       
        return gradient_offset;
    }
    
    public int getMergingType() {
        return merging_type;
    }
    
    public int getContourColorMethod() {
        return contourColorMethod;
    }
    
    public int getTrapColorMethod() {
        return trapColorMethod;
    }
    
    public double getTrapIntesity() {
        return trapIntensity;
    }

    /**
     * @return the statistic
     */
    public boolean getStatistic() {
        return statistic;
    }

    /**
     * @return the statistic_type
     */
    public int getStatisticType() {
        return statistic_type;
    }

    /**
     * @return the statistic_intensity
     */
    public double getStatisticIntensity() {
        return statistic_intensity;
    }

    /**
     * @return the stripeAvgStripeDensity
     */
    public double getStripeAvgStripeDensity() {
        return stripeAvgStripeDensity;
    }

    /**
     * @return the cosArgStripeDensity
     */
    public double getCosArgStripeDensity() {
        return cosArgStripeDensity;
    }

    /**
     * @return the cosArgInvStripeDensity
     */
    public double getCosArgInvStripeDensity() {
        return cosArgInvStripeDensity;
    }

    /**
     * @return the StripeDenominatorFactor
     */
    public double getStripeDenominatorFactor() {
        return StripeDenominatorFactor;
    }

    /**
     * @return the statisticGroup
     */
    public int getStatisticGroup() {
        return statisticGroup;
    }

    /**
     * @return the user_statistic_formula
     */
    public String getUserStatisticFormula() {
        return user_statistic_formula;
    }

    /**
     * @return the useAverage
     */
    public boolean getUseAverage() {
        return useAverage;
    }

    /**
     * @return the statistic_escape_type
     */
    public int getStatisticEscapeType() {
        return statistic_escape_type;
    }

    /**
     * @return the lighting
     */
    public boolean getLighting() {
        return lighting;
    }

    /**
     * @return the lightintensity
     */
    public double getLightintensity() {
        return lightintensity;
    }

    /**
     * @return the ambientlight
     */
    public double getAmbientlight() {
        return ambientlight;
    }

    /**
     * @return the specularintensity
     */
    public double getSpecularintensity() {
        return specularintensity;
    }

    /**
     * @return the shininess
     */
    public double getShininess() {
        return shininess;
    }

    /**
     * @return the lightHeightTransfer
     */
    public int getLightHeightTransfer() {
        return lightHeightTransfer;
    }

    /**
     * @return the lightHeightTransferFactor
     */
    public double getLightHeightTransferFactor() {
        return lightHeightTransferFactor;
    }

    /**
     * @return the lightMode
     */
    public int getLightMode() {
        return lightMode;
    }

    /**
     * @return the lightColorMode
     */
    public int getLightColorMode() {
        return lightColorMode;
    }

    /**
     * @return the l_noise_reducing_factor
     */
    public double getLightNoiseReducingFactor() {
        return l_noise_reducing_factor;
    }

    /**
     * @return the light_blending
     */
    public double getLightBlending() {
        return light_blending;
    }

    /**
     * @return the light_direction
     */
    public double getLightDirection() {
        return light_direction;
    }

    /**
     * @return the light_magnitude
     */
    public double getLightMagnitude() {
        return light_magnitude;
    }
   
}
