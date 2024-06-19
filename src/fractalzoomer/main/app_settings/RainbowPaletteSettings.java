
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class RainbowPaletteSettings {
    public boolean rainbow_palette;
    public double rainbow_palette_factor;   
    public double rp_blending;
    public int rainbow_offset;
    public double rp_noise_reducing_factor;
    public int rainbow_algorithm;
    public int rp_color_blending;
    public boolean rp_reverse_color_blending;
    
    public RainbowPaletteSettings() {
        rainbow_palette = false;
        rainbow_palette_factor = 1;
        rp_blending = 0.7;
        rainbow_offset = 0;
        rp_noise_reducing_factor = 1e-10;
        rainbow_algorithm = 0;
        rp_color_blending = 0;
        rp_reverse_color_blending = false;
    }

    
}
