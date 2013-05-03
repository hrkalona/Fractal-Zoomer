
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
  protected double color_intensity;
  
  public PaletteColor(Color[] palette, double color_intensity) {
      
      this.palette = palette;
      this.color_intensity = color_intensity;
      
  }
  
  public abstract Color getPaletteColor(double result);
    
}
