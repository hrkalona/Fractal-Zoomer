
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class EntropyColoringSettings {
    public boolean entropy_coloring;
    public double entropy_palette_factor;
    public double en_noise_reducing_factor;
    public int entropy_offset;
    public double en_blending;
    public int entropy_algorithm;
    public int en_color_blending;
    public boolean en_reverse_color_blending;
    
    public EntropyColoringSettings() {
        entropy_coloring = false;
        entropy_palette_factor = 50;
        en_noise_reducing_factor = 1e-10;
        en_blending = 0.7;
        entropy_offset = 0;
        entropy_algorithm = 0;
        en_color_blending = 0;
        en_reverse_color_blending = false;
    }
}
