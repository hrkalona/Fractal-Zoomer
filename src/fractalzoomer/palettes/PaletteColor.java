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

import fractalzoomer.out_coloring_algorithms.OutColorAlgorithm;
import java.awt.Color;

public abstract class PaletteColor {

    protected int[] palette;
    protected static int mod_offset;
    protected double color_intensity;
    protected int[] special_color;

    public PaletteColor(int[] palette, double color_intensity, Color special_color) {

        this.palette = palette;
        mod_offset = (OutColorAlgorithm.MAGIC_OFFSET_NUMBER % palette.length) == 0 ? 0 : palette.length - (OutColorAlgorithm.MAGIC_OFFSET_NUMBER % palette.length);
        this.color_intensity = color_intensity;

        if(special_color == null) {
            this.special_color = null;
        }
        else {
            this.special_color = new int[2]; //create two almost the same colors just for boundaries

            this.special_color[0] = special_color.getRGB();

            boolean flag = true;

            int count = 0;
            
            Color last_color;
            boolean up = true;
            if(special_color.getBlue() == 255) {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() - 1);
                this.special_color[1] = last_color.getRGB();
                up = false;
            }
            else {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() + 1);
                this.special_color[1] = last_color.getRGB();
                up = true;
            }

            while(flag && count < 10) {
                flag = false;
                for(int j = 0; j < palette.length; j++) {
                    if(palette[j] == last_color.getRGB()) {

                        if(up) {
                            if(last_color.getBlue() == 255) {
                                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() - 1);
                                this.special_color[1] = last_color.getRGB();
                                up = false;
                            }
                            else {
                                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() + 1);
                                this.special_color[1] = last_color.getRGB();
                            }
                        }
                        else {
                            if(last_color.getBlue() == 0) {
                                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() + 1);
                                this.special_color[1] = last_color.getRGB();
                                up = true;
                            }
                            else {
                                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() - 1);
                                this.special_color[1] = last_color.getRGB();
                            }
                        }
                    }
                }
            }

        }

    }

    public abstract int getPaletteColor(double result);
    
    
    public int getPaletteLength() {
        
        return palette.length;
        
    }

}
