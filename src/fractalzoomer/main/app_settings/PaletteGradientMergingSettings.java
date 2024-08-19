
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class PaletteGradientMergingSettings {
    public boolean palette_gradient_merge;
    public double gradient_intensity;
    public int gradient_offset;
    public double palette_blending;
    public int merging_type;
    
    public PaletteGradientMergingSettings() {
        
        palette_gradient_merge = false;
        gradient_intensity = 10;
        palette_blending = 0.5;
        merging_type = 3;
        gradient_offset = 0;
        
    }   
}
