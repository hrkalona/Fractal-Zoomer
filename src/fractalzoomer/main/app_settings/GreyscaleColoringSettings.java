
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class GreyscaleColoringSettings {
    public boolean greyscale_coloring;
    public double gs_noise_reducing_factor;
    
    public GreyscaleColoringSettings() {
        greyscale_coloring = false;
        gs_noise_reducing_factor = 1e-10;
    }
}
