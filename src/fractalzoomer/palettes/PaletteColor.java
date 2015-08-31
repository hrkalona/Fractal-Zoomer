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
