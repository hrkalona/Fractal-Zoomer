
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class PaletteColorNormal extends PaletteColor {
  
  public PaletteColorNormal(Color[] palette) {
      
      super(palette);
      
  }

    @Override
    public Color getPaletteColor(double result) {
        
        return palette[((int)((result + mod_offset))) % palette.length];
        
    }
  
}
