package fractalzoomer.palettes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class PaletteColorSmooth extends PaletteColor {
  
    public PaletteColorSmooth(int[] palette, double color_intensity) {
      
        super(palette, color_intensity);
  
    }
  
 
    @Override
    public int getPaletteColor(double result) {
      
        result *= 256 * color_intensity;
        int temp = ((int)(result / 256 + mod_offset * color_intensity));
        
        int color = getColor(temp);
        int color2 = getColor(temp - 1);

        int color_red = (color >> 16) & 0xff;
        int color_green = (color >> 8) & 0xff;
        int color_blue = color & 0xff;
        
        int color2_red = (color2 >> 16) & 0xff;
        int color2_green = (color2 >> 8) & 0xff;
        int color2_blue = color2 & 0xff;

        int k1 = (int)result % 256;
        int k2 = 255 - k1;
        
        int red = (k1 * color_red + k2 * color2_red) / 255;
        int green = (k1 * color_green + k2 * color2_green) / 255;
        int blue = (k1 * color_blue + k2 * color2_blue) / 255;

        
        return 0xff000000 | (red << 16) | (green << 8) | blue;

        //try {
          // return new Color(red, green, blue);
        //}
        //catch(Exception ex) {
           // return new Color(red > 255 ? red % 256 : (red < 0 ? 0 : red), green > 255 ? green % 256 : (green < 0 ? 0 : green), blue > 255 ? blue % 256 : (blue < 0 ? 0 : blue));  
        //}
      
    }
  
    private int getColor(int i) {

        i = i + palette.length < 0 ? 0 : i + palette.length;
        return palette[((int)((i + palette.length))) % palette.length];
    
    }
    
}
