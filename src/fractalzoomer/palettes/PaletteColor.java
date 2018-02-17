/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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

import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import java.awt.Color;

public abstract class PaletteColor {

    protected int[] palette;
    protected static int mod_offset;
    protected int[] special_color;

    public PaletteColor(int[] palette, Color special_color) {

        this.palette = palette;
        mod_offset = (OutColorAlgorithm.MAGIC_OFFSET_NUMBER % palette.length) == 0 ? 0 : palette.length - (OutColorAlgorithm.MAGIC_OFFSET_NUMBER % palette.length);

        if(special_color == null) {
            this.special_color = null;
        }
        else {
            this.special_color = new int[2]; //create two almost the same colors just for boundaries

            this.special_color[0] = special_color.getRGB();
            
            Color last_color;
            if(special_color.getBlue() == 255) {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() - 1);
                this.special_color[1] = last_color.getRGB();
            }
            else {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() + 1);
                this.special_color[1] = last_color.getRGB();
            }
        }
    }

    public abstract int getPaletteColor(double result);
    
    
    public int getPaletteLength() {
        
        return palette.length;
        
    }

}
