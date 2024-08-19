
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class ContourColoringSettings {
    public boolean contour_coloring;
    public double cn_noise_reducing_factor;
    public double cn_blending;
    public int contour_algorithm;
    public int contourColorMethod;
    public double min_contour;
    public int fractionalTransfer;
    public int fractionalSmoothing;
 
    public ContourColoringSettings() {
        contour_coloring = false;
        cn_noise_reducing_factor = 1e-10;
        cn_blending = 0.7;
        contour_algorithm = 0;
        contourColorMethod = 3;
        min_contour = 0.06;
        fractionalTransfer = 0;
        fractionalSmoothing = 0;
    }
}
