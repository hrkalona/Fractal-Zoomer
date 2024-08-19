
package fractalzoomer.settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsPalette1062 extends SettingsPalette implements Serializable {
    private static final long serialVersionUID = -6568200424825652679L;
    private double scale_factor_palette_val;
    private int processing_alg;
    
     public SettingsPalette1062(int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int offset, int processing_alg, double scale_factor_palette_val) {
        
        super(custom_palette, color_interpolation, color_space, reversed_palette, offset);
        this.processing_alg = processing_alg;
        this.scale_factor_palette_val = scale_factor_palette_val;
        
    }
     
    public int getProcessingAlgorithm() {
        
        return processing_alg;
        
    }
    
    public double getScaleFactorPaletteValue() {
        
        return scale_factor_palette_val;
        
    }
    
    @Override
    public int getVersion() {
        
        return 1062;
        
    }
     
}
