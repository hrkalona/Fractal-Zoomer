

package fractalzoomer.main.app_settings;

/**
 *
 * @author kaloch
 */
public class HistogramColoringSettings {
    public boolean histogramColoring;
    public double histogramDensity;
    public double hs_noise_reducing_factor;
    public int histogramBinGranularity;
    public double hs_blending;
    public double histogramScaleMin;
    public double histogramScaleMax;
    public int hmapping;
    public boolean hs_remove_outliers;
    public int hs_outliers_method;
    public int rank_order_digits_grouping;
    public int hs_color_blending;
    public boolean hs_reverse_color_blending;
    public boolean use_integer_iterations;
    public double mapping_exponent;

    public HistogramColoringSettings() {
        histogramColoring = false;
        hs_remove_outliers = false;
        histogramDensity = 3;
        hs_noise_reducing_factor = 1e-10;
        histogramBinGranularity = 2;
        hs_blending = 1;
        histogramScaleMin = 0;
        histogramScaleMax = 1;
        hmapping = 0;
        hs_outliers_method= 0;
        rank_order_digits_grouping = 2;
        hs_color_blending = 0;
        hs_reverse_color_blending = false;
        use_integer_iterations = false;
        mapping_exponent = 1;
    }
}
