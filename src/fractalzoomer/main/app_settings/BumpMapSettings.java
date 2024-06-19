
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class BumpMapSettings {
    public boolean bump_map;
    public double bumpMappingStrength;
    public double bumpMappingDepth;
    public double lightDirectionDegrees;
    public double bm_noise_reducing_factor;
    public int bump_transfer_function;
    public double bump_transfer_factor;
    public int bumpProcessing;
    public double bump_blending;
    public int fractionalTransfer;
    public int fractionalSmoothing;
    public int fractionalTransferMode;
    public double fractionalTransferScale;
    
    public BumpMapSettings(BumpMapSettings copy) {
        
        bump_map = copy.bump_map;
        bumpMappingStrength = copy.bumpMappingStrength;
        bumpMappingDepth = copy.bumpMappingDepth;
        lightDirectionDegrees = copy.lightDirectionDegrees;
        bump_transfer_function = copy.bump_transfer_function;
        bump_transfer_factor = copy.bump_transfer_factor;
        bm_noise_reducing_factor = copy.bm_noise_reducing_factor;
        bumpProcessing = copy.bumpProcessing;
        bump_blending = copy.bump_blending;
        fractionalTransfer = copy.fractionalTransfer;
        fractionalSmoothing = copy.fractionalSmoothing;
        fractionalTransferMode = copy.fractionalTransferMode;
        fractionalTransferScale = copy.fractionalTransferScale;
        
    }
    
    public BumpMapSettings() {
        
        bump_map = false;
        bumpMappingStrength = 50;
        bumpMappingDepth = 50;
        lightDirectionDegrees = 0;
        bump_transfer_function = 0;
        bump_transfer_factor = 1.0;
        bm_noise_reducing_factor = 1e-10;
        bumpProcessing = 0;
        bump_blending = 0.5;
        fractionalTransfer = 0;
        fractionalSmoothing = 0;
        fractionalTransferMode = 1;
        fractionalTransferScale = 1;
        
    }
}
