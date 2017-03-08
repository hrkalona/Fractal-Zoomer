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

package fractalzoomer.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author kaloch
 */
public class GoogleMaterialDesignPalette {
    private static final int[] RED = {0xFFEBEE, 0xFFCDD2, 0xEF9A9A, 0xE57373, 0xEF5350, 0xF44336, 0xE53935, 0xD32F2F, 0xC62828, 0xB71C1C};
    private static final int[] PINK = {0xFCE4EC, 0xF8BBD0, 0xF48FB1, 0xF06292, 0xEC407A, 0xE91E63, 0xD81B60, 0xC2185B, 0xAD1457, 0x880E4F};
    
    private static final int[] PURPLE = {0xF3E5F5, 0xE1BEE7, 0xCE93D8, 0xBA68C8, 0xAB47BC, 0x9C27B0, 0x8E24AA, 0x7B1FA2, 0x6A1B9A, 0x4A148C};
    private static final int[] DEEP_PURPLE = {0xEDE7F6, 0xD1C4E9, 0xB39DDB, 0x9575CD, 0x7E57C2, 0x673AB7, 0x5E35B1, 0x512DA8, 0x4527A0, 0x311B92};
    
    private static final int[] INDIGO = {0xE8EAF6, 0xC5CAE9, 0x9FA8DA, 0x7986CB, 0x5C6BC0, 0x3F51B5, 0x3949AB, 0x303F9F, 0x283593, 0x1A237E};
    private static final int[] BLUE = {0xE3F2FD, 0xBBDEFB, 0x90CAF9, 0x64B5F6, 0x42A5F5, 0x2196F3, 0x1E88E5, 0x1976D2, 0x1565C0, 0x0D47A1};
    
    private static final int[] LIGHT_BLUE = {0xE1F5FE, 0xB3E5FC, 0x81D4FA, 0x4FC3F7, 0x29B6F6, 0x03A9F4, 0x039BE5, 0x0288D1, 0x0277BD, 0x01579B};
    private static final int[] CYAN = {0xE0F7FA, 0xB2EBF2, 0x80DEEA, 0x4DD0E1, 0x26C6DA, 0x00BCD4, 0x00ACC1, 0x0097A7, 0x00838F, 0x006064};
    
    private static final int[] TEAL = {0xE0F2F1, 0xB2DFDB, 0x80CBC4, 0x4DB6AC, 0x26A69A, 0x009688, 0x00897B, 0x00796B, 0x00695C, 0x004D40};
    private static final int[] GREEN = {0xE8F5E9, 0xC8E6C9, 0xA5D6A7, 0x81C784, 0x66BB6A, 0x4CAF50, 0x43A047, 0x388E3C, 0x2E7D32, 0x1B5E20};
    
    private static final int[] LIGHT_GREEN = {0xF1F8E9, 0xDCEDC8, 0xC5E1A5, 0xAED581, 0x9CCC65, 0x8BC34A, 0x7CB342, 0x689F38, 0x558B2F, 0x33691E};
    private static final int[] LIME = {0xF9FBE7, 0xF0F4C3, 0xE6EE9C, 0xDCE775, 0xD4E157, 0xCDDC39, 0xC0CA33, 0xAFB42B, 0x9E9D24, 0x827717};
    
    private static final int[] YELLOW = {0xFFFDE7, 0xFFF9C4, 0xFFF59D, 0xFFF176, 0xFFEE58, 0xFFEB3B, 0xFDD835, 0xFBC02D, 0xF9A825, 0xF57F17};
    private static final int[] AMBER = {0xFFF8E1, 0xFFECB3, 0xFFE082, 0xFFD54F, 0xFFCA28, 0xFFC107, 0xFFB300, 0xFFA000, 0xFF8F00, 0xFF6F00};
    
    private static final int[] ORANGE = {0xFFF3E0, 0xFFE0B2, 0xFFCC80, 0xFFB74D, 0xFFA726, 0xFF9800, 0xFB8C00, 0xF57C00, 0xEF6C00, 0xE65100};
    private static final int[] DEEP_ORANGE = {0xFBE9E7, 0xFFCCBC, 0xFFAB91, 0xFF8A65, 0xFF7043, 0xFF5722, 0xF4511E, 0xE64A19, 0xD84315, 0xBF360C};
    
    private static final int[] BROWN = {0xEFEBE9, 0xD7CCC8, 0xBCAAA4, 0xA1887F, 0x8D6E63, 0x795548, 0x6D4C41, 0x5D4037, 0x4E342E, 0x3E2723};
    private static final int[] GREY = {0xFAFAFA, 0xF5F5F5, 0xEEEEEE, 0xE0E0E0, 0xBDBDBD, 0x9E9E9E, 0x757575, 0x616161, 0x424242, 0x212121};
    private static final int[] BLUE_GREY = {0xECEFF1, 0xCFD8DC, 0xB0BEC5, 0x90A4AE, 0x78909C, 0x607D8B, 0x546E7A, 0x455A64, 0x37474F, 0x263238};
    
    private static final int[] ALPHA_RED = {0xFF8A80, 0xFF5252, 0xFF1744, 0xD50000};
    private static final int[] ALPHA_PINK = {0xFF80AB, 0xFF4081, 0xF50057, 0xC51162};
    
    private static final int[] ALPHA_PURPLE = {0xEA80FC, 0xE040FB, 0xD500F9, 0xAA00FF};
    private static final int[] ALPHA_DEEP_PURPLE = {0xB388FF, 0x7C4DFF, 0x651FFF, 0x6200EA};
    
    private static final int[] ALPHA_INDIGO = {0x8C9EFF, 0x536DFE, 0x3D5AFE, 0x304FFE};
    private static final int[] ALPHA_BLUE = {0x82B1FF, 0x448AFF, 0x2979FF, 0x2962FF};
    
    private static final int[] ALPHA_LIGHT_BLUE = {0x80D8FF, 0x40C4FF, 0x00B0FF, 0x0091EA};
    private static final int[] ALPHA_CYAN = {0x84FFFF, 0x18FFFF, 0x00E5FF, 0x00B8D4};
    
    private static final int[] ALPHA_TEAL = {0xA7FFEB, 0x64FFDA, 0x1DE9B6, 0x00BFA5};
    private static final int[] ALPHA_GREEN = {0xB9F6CA, 0x69F0AE, 0x00E676, 0x00C853};
    
    private static final int[] ALPHA_LIGHT_GREEN = {0xCCFF90, 0xB2FF59, 0x76FF03, 0x64DD17};
    private static final int[] ALPHA_LIME = {0xF4FF81, 0xEEFF41, 0xC6FF00, 0xAEEA00};
    
    private static final int[] ALPHA_YELLOW = {0xFFFF8D, 0xFFFF00, 0xFFEA00, 0xFFD600};
    private static final int[] ALPHA_AMBER = {0xFFE57F, 0xFFD740, 0xFFC400, 0xFFAB00};
    
    private static final int[] ALPHA_ORANGE = {0xFFD180, 0xFFAB40, 0xFF9100, 0xFF6D00};
    private static final int[] ALPHA_DEEP_ORANGE = {0xFF9E80, 0xFF6E40, 0xFF3D00, 0xDD2C00};
    
    private static final int[] ALPHA_BROWN = {0xd7ccc8, 0xbcaaa4, 0x8d6e63, 0x5d4037};
    private static final int[] ALPHA_GREY = {0xf5f5f5, 0xf5f5f5, 0xbdbdbd, 616161};
    
    static int num = 0;
    
    public static Color[] generate() {
        
        ArrayList<int[]> normal = new ArrayList<int[]>();
        normal.add(RED);
        normal.add(PINK);
        
        normal.add(PURPLE);
        normal.add(DEEP_PURPLE);
        
        normal.add(INDIGO);
        normal.add(BLUE);
       
        normal.add(LIGHT_BLUE);
        normal.add(CYAN);
       
        normal.add(TEAL);
        normal.add(GREEN);
        
        normal.add(LIGHT_GREEN);
        normal.add(LIME);
        
        normal.add(YELLOW);
        normal.add(AMBER);
        
        normal.add(ORANGE);
        normal.add(DEEP_ORANGE);      
        
        normal.add(BROWN);
        normal.add(GREY);
        //normal.add(BLUE_GREY);
        
        ArrayList<int[]> a = new ArrayList<int[]>();
        a.add(ALPHA_RED);
        a.add(ALPHA_PINK);
        a.add(ALPHA_PURPLE);
        a.add(ALPHA_DEEP_PURPLE);
        a.add(ALPHA_INDIGO);
        a.add(ALPHA_BLUE);
        a.add(ALPHA_LIGHT_BLUE);
        a.add(ALPHA_CYAN);
        a.add(ALPHA_TEAL);
        a.add(ALPHA_GREEN);
        a.add(ALPHA_LIGHT_GREEN);
        a.add(ALPHA_LIME);
        a.add(ALPHA_YELLOW);
        a.add(ALPHA_AMBER);
        a.add(ALPHA_ORANGE);
        a.add(ALPHA_DEEP_ORANGE);
        a.add(ALPHA_BROWN);
        a.add(ALPHA_GREY);
        
        Color[] colors = new Color[normal.size() / 2 * 3];
    
        Random generator = new Random();
                
        int i = 0;
        while(!normal.isEmpty()) {
            int k = generator.nextInt(normal.size());
            
            int[] colors_array = normal.get(k);
            int[] alpha_array = a.get(k);
            
            if(k % 2 == 0) {
                normal.remove(k);
                normal.remove(k);
                a.remove(k);
                a.remove(k);
            }
            else {
                normal.remove(k - 1);
                normal.remove(k - 1);
                a.remove(k - 1);
                a.remove(k - 1);
            }
            
            int alpha = generator.nextInt(2);
            
            if(alpha == 0) {
                int l = generator.nextInt(colors_array.length - 2);

                colors[i] = new Color(colors_array[l]);            
                colors[i + 1] = new Color(colors_array[l + 1]);             
                colors[i + 2] = new Color(colors_array[l + 2]);         
            }
            else {
                int l = generator.nextInt(2);
                     
                colors[i] = new Color(alpha_array[l]);                        
                colors[i + 1] = new Color(alpha_array[l + 1]);                           
                colors[i + 2] = new Color(alpha_array[l + 2]); 
            }
            
            i+=3;
            
        }
        

        return colors;
    }
}
