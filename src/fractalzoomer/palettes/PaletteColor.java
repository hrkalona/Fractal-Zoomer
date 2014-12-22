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
  protected double color_intensity;
  
  public PaletteColor(int[] palette, double color_intensity) {
      
      this.palette = palette;
      mod_offset = (100800 % palette.length) == 0 ? 0 : palette.length - (100800 % palette.length);
      this.color_intensity = color_intensity;
    
  }
  
  public abstract int getPaletteColor(double result);
   
}
