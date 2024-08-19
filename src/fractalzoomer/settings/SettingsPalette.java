

package fractalzoomer.settings;

import java.io.Serializable;

/**
 *
 * @author hrkalona2
 */
public class SettingsPalette implements Serializable {
  private static final long serialVersionUID = -272776052070042242L;
  private int[][] custom_palette;
  private int color_interpolation;
  private int color_space; 
  private boolean reversed_palette;
  private int offset;
  
    public SettingsPalette(int[][] custom_palette, int color_interpolation, int color_space, boolean reversed_palette, int offset) {
        
        this.custom_palette = custom_palette;
        this.color_interpolation = color_interpolation;
        this.color_space = color_space;
        this.reversed_palette = reversed_palette;
        this.offset = offset;
        
    }
    
    public int[][] getCustomPalette() {
        
        return custom_palette;
        
    }
    
    public int getColorInterpolation() {
        
        return color_interpolation;
        
    }
    
    public int getColorSpace() {
        
        return color_space;
        
    }
    
    public boolean getReveresedPalette() {
        
        return reversed_palette;
        
    }
    
    public int getOffset() {
        
        return offset;
        
    }
    
    public int getVersion() {
        
        return 1048;
        
    }
    
}
