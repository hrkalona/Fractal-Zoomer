
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public abstract class PaletteColor {
  protected Color[] palette;
  protected static int mod_offset;
  
  public PaletteColor(Color[] palette) {
      
      this.palette = palette;
      mod_offset = (100800 % palette.length) == 0 ? 0 : palette.length - (100800 % palette.length);
    
  }
  
  public abstract Color getPaletteColor(double result);
   
}
