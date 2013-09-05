package fractalzoomer.palettes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public abstract class PaletteColor {
  protected int[] palette;
  protected static int mod_offset;
  
  public PaletteColor(int[] palette) {
      
      this.palette = palette;
      mod_offset = (100800 % palette.length) == 0 ? 0 : palette.length - (100800 % palette.length);
    
  }
  
  public abstract int getPaletteColor(double result);
   
}
