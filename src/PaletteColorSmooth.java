
import java.awt.Color;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class PaletteColorSmooth extends PaletteColor {
  
    public PaletteColorSmooth(Color[] palette) {
      
        super(palette);
  
    }
  
 
    @Override
    public Color getPaletteColor(double result) {
      
        result *= 256;
        int temp = (int)result / 256 + mod_offset;
        
        Color color = getColor(temp);
        Color color2 = getColor(temp - 1);
   
        int k1 = (int)result % 256;
        int k2 = 255 - k1;
        int red = (k1 * color.getRed() + k2 * color2.getRed()) / 255;
        int green = (k1 * color.getGreen() + k2 * color2.getGreen()) / 255;
        int blue = (k1 * color.getBlue() + k2 * color2.getBlue()) / 255;

        try {
            return new Color(red, green, blue);
        }
        catch(Exception ex) {
            return new Color(red > 255 ? red % 256 : (red < 0 ? 0 : red), green > 255 ? green % 256 : (green < 0 ? 0 : green), blue > 255 ? blue % 256 : (blue < 0 ? 0 : blue));  
        }
      
    }
  
    private Color getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return palette[((int)((i + palette.length))) % palette.length];
    
    }
    
}
