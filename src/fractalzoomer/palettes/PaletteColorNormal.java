/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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

public class PaletteColorNormal extends PaletteColor {
  
  public PaletteColorNormal(int[] palette, double color_intensity) {
      
      super(palette, color_intensity);
      
  }

    @Override
    public int getPaletteColor(double result) {
        
        return palette[((int)((result + mod_offset) * color_intensity)) % palette.length];
        
    }
  
}
