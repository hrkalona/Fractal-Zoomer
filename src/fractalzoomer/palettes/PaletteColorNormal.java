package fractalzoomer.palettes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class PaletteColorNormal extends PaletteColor {
  
  public PaletteColorNormal(int[] palette, double color_intensity) {
      
      super(palette, color_intensity);
      
  }

    @Override
    public int getPaletteColor(double result) {
        
        return palette[((int)((result + mod_offset) * color_intensity)) % palette.length];
        
    }
  
}
