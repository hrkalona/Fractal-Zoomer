/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package fractalzoomer.palettes;

import java.awt.Color;

public class PaletteColorNormal extends PaletteColor {
  
  public PaletteColorNormal(int[] palette, double color_intensity, Color special_color) {
      
      super(palette, color_intensity, special_color);
      
  }

    @Override
    public int getPaletteColor(double result) {
        
        if(result < 0) {
            if(special_color != null) {
                return special_color[((int)(result * (-1))) % special_color.length];
            }
            else {
                return palette[((int)((result * (-1) + mod_offset) * color_intensity)) % palette.length];
            }           
        }
        else {
            return palette[((int)((result + mod_offset) * color_intensity)) % palette.length];
        }       
        
    }  
 
  
}
