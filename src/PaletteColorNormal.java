
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
  
  public PaletteColorNormal(Color[] palette, double color_intensity) {
      
      super(palette, color_intensity);
      
  }

    @Override
    public Color getPaletteColor(double result) {
        
        return palette[((int)(result * color_intensity)) % palette.length];
        
    }
  
}
