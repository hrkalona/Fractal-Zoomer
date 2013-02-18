
import java.io.Serializable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class SettingsPalette implements Serializable {
  private int[][] custom_palette;
  
    public SettingsPalette(int[][] custom_palette) {
        
        this.custom_palette = custom_palette;
        
    }
    
    public int[][] getCustomPalette() {
        
        return custom_palette;
        
    }
    
}
