
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class OffsetColoringSettings {
    public boolean offset_coloring;
    public int post_process_offset;
    public double of_noise_reducing_factor;
    public double of_blending;
    public int of_color_blending;
    public boolean of_reverse_color_blending;
    
    public OffsetColoringSettings() {
        offset_coloring = false;
        post_process_offset = 300;
        of_noise_reducing_factor = 1e-10;
        of_blending = 0.7;
        of_color_blending = 0;
        of_reverse_color_blending = false;
    }
}
