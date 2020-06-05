/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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

package fractalzoomer.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kaloch
 */
public class GoogleMaterialDesignPalette {
    public static final int[] RED = {0xFFEBEE, 0xFFCDD2, 0xEF9A9A, 0xE57373, 0xEF5350, 0xF44336, 0xE53935, 0xD32F2F, 0xC62828, 0xB71C1C};
    public static final int[] PINK = {0xFCE4EC, 0xF8BBD0, 0xF48FB1, 0xF06292, 0xEC407A, 0xE91E63, 0xD81B60, 0xC2185B, 0xAD1457, 0x880E4F};
    
    public static final int[] PURPLE = {0xF3E5F5, 0xE1BEE7, 0xCE93D8, 0xBA68C8, 0xAB47BC, 0x9C27B0, 0x8E24AA, 0x7B1FA2, 0x6A1B9A, 0x4A148C};
    public static final int[] DEEP_PURPLE = {0xEDE7F6, 0xD1C4E9, 0xB39DDB, 0x9575CD, 0x7E57C2, 0x673AB7, 0x5E35B1, 0x512DA8, 0x4527A0, 0x311B92};
    
    public static final int[] INDIGO = {0xE8EAF6, 0xC5CAE9, 0x9FA8DA, 0x7986CB, 0x5C6BC0, 0x3F51B5, 0x3949AB, 0x303F9F, 0x283593, 0x1A237E};
    public static final int[] BLUE = {0xE3F2FD, 0xBBDEFB, 0x90CAF9, 0x64B5F6, 0x42A5F5, 0x2196F3, 0x1E88E5, 0x1976D2, 0x1565C0, 0x0D47A1};
    
    public static final int[] LIGHT_BLUE = {0xE1F5FE, 0xB3E5FC, 0x81D4FA, 0x4FC3F7, 0x29B6F6, 0x03A9F4, 0x039BE5, 0x0288D1, 0x0277BD, 0x01579B};
    public static final int[] CYAN = {0xE0F7FA, 0xB2EBF2, 0x80DEEA, 0x4DD0E1, 0x26C6DA, 0x00BCD4, 0x00ACC1, 0x0097A7, 0x00838F, 0x006064};
    
    public static final int[] TEAL = {0xE0F2F1, 0xB2DFDB, 0x80CBC4, 0x4DB6AC, 0x26A69A, 0x009688, 0x00897B, 0x00796B, 0x00695C, 0x004D40};
    public static final int[] GREEN = {0xE8F5E9, 0xC8E6C9, 0xA5D6A7, 0x81C784, 0x66BB6A, 0x4CAF50, 0x43A047, 0x388E3C, 0x2E7D32, 0x1B5E20};
    
    public static final int[] LIGHT_GREEN = {0xF1F8E9, 0xDCEDC8, 0xC5E1A5, 0xAED581, 0x9CCC65, 0x8BC34A, 0x7CB342, 0x689F38, 0x558B2F, 0x33691E};
    public static final int[] LIME = {0xF9FBE7, 0xF0F4C3, 0xE6EE9C, 0xDCE775, 0xD4E157, 0xCDDC39, 0xC0CA33, 0xAFB42B, 0x9E9D24, 0x827717};
    
    public static final int[] YELLOW = {0xFFFDE7, 0xFFF9C4, 0xFFF59D, 0xFFF176, 0xFFEE58, 0xFFEB3B, 0xFDD835, 0xFBC02D, 0xF9A825, 0xF57F17};
    public static final int[] AMBER = {0xFFF8E1, 0xFFECB3, 0xFFE082, 0xFFD54F, 0xFFCA28, 0xFFC107, 0xFFB300, 0xFFA000, 0xFF8F00, 0xFF6F00};
    
    public static final int[] ORANGE = {0xFFF3E0, 0xFFE0B2, 0xFFCC80, 0xFFB74D, 0xFFA726, 0xFF9800, 0xFB8C00, 0xF57C00, 0xEF6C00, 0xE65100};
    public static final int[] DEEP_ORANGE = {0xFBE9E7, 0xFFCCBC, 0xFFAB91, 0xFF8A65, 0xFF7043, 0xFF5722, 0xF4511E, 0xE64A19, 0xD84315, 0xBF360C};
    
    public static final int[] BROWN = {0xEFEBE9, 0xD7CCC8, 0xBCAAA4, 0xA1887F, 0x8D6E63, 0x795548, 0x6D4C41, 0x5D4037, 0x4E342E, 0x3E2723};
    public static final int[] GREY = {0xFAFAFA, 0xF5F5F5, 0xEEEEEE, 0xE0E0E0, 0xBDBDBD, 0x9E9E9E, 0x757575, 0x616161, 0x424242, 0x212121};
    public static final int[] BLUE_GREY = {0xECEFF1, 0xCFD8DC, 0xB0BEC5, 0x90A4AE, 0x78909C, 0x607D8B, 0x546E7A, 0x455A64, 0x37474F, 0x263238};
    
    private static final int[] BLACK = {-16777216, -14342875, -11382190, -9211021, -6908266, -4342339, -2500135, -986896, -1};
    
    public static Color[] generate(int max_colors) {
        ArrayList<int[]> greens = new ArrayList<>();
        greens.add(TEAL);
        greens.add(GREEN);
        
        ArrayList<int[]> light_greens = new ArrayList<>();
        light_greens.add(LIGHT_GREEN);
        light_greens.add(LIME);
        
        ArrayList<int[]> blues = new ArrayList<>();
        blues.add(INDIGO);
        blues.add(BLUE);
        
        ArrayList<int[]> light_blues = new ArrayList<>();
        light_blues.add(LIGHT_BLUE);
        light_blues.add(CYAN);
        
        ArrayList<int[]> reds = new ArrayList<>();
        reds.add(RED);
        reds.add(PINK);
        
        ArrayList<int[]> purples = new ArrayList<>();
        purples.add(PURPLE);
        purples.add(DEEP_PURPLE);
        
        ArrayList<int[]> oranges = new ArrayList<>();
        oranges.add(ORANGE);
        oranges.add(DEEP_ORANGE);
        
        ArrayList<int[]> yellows = new ArrayList<>();
        yellows.add(YELLOW);
        yellows.add(AMBER);
        
        ArrayList<int[]> greys = new ArrayList<>();
        greys.add(GREY);
        greys.add(BLUE_GREY);
        
        ArrayList<int[]> browns = new ArrayList<>();
        browns.add(BROWN);
        
        ArrayList<int[]> blacks = new ArrayList<>();
        blacks.add(BLACK);
        
        ArrayList<ArrayList<int[]>> colors = new ArrayList<>();
        colors.add(greens);
        colors.add(light_greens);
        colors.add(blues);
        colors.add(light_blues);
        colors.add(purples);
        colors.add(reds);
        colors.add(oranges);
        colors.add(yellows);
        colors.add(blacks);
        colors.add(browns);
        colors.add(greys);
        
        int count = 0;
        
        Random generator = new Random();
        
        Color[] palette = new Color[max_colors];
        
        while(true) {
            
            if(count >= max_colors - 1 || colors.isEmpty()) {
                break;
            }
            
            int k = generator.nextInt(colors.size());
            
            ArrayList<int[]> category = colors.get(k);
            
            int r = generator.nextInt(category.size());
            
            int[] subcategory = category.get(r);
            
            colors.remove(k);
            
            int from = 0, to = 0;
            
            while(Math.abs(from - to) < 2)
            {
                from = generator.nextInt(subcategory.length);
                to = generator.nextInt(subcategory.length);
            }
            
            if(from > to) {
                for(int i = from; i >= to && count < max_colors; i--, count++) {
                    palette[count] = new Color(subcategory[i]);
                }
            }
            else {
                for(int i = from; i <= to && count < max_colors; i++, count++) {
                    palette[count] = new Color(subcategory[i]);
                }
            }
         
        }
      
        return palette;
    }
}
