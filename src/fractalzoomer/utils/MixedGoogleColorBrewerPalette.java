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
public class MixedGoogleColorBrewerPalette {
    
    
    public static Color[] generate(int max_colors) {
        ArrayList<int[]> greens = new ArrayList<>();
        greens.add(ColorBrewerPalette.GREEN1);
        greens.add(ColorBrewerPalette.GREEN2);
        greens.add(ColorBrewerPalette.GREEN3);      
        greens.add(GoogleMaterialDesignPalette.TEAL);
        greens.add(GoogleMaterialDesignPalette.GREEN);      
        greens.add(GoogleMaterialDesignPalette.LIGHT_GREEN);
        greens.add(GoogleMaterialDesignPalette.LIME);

        ArrayList<int[]> blues = new ArrayList<>();
        blues.add(ColorBrewerPalette.BLUE1);
        blues.add(ColorBrewerPalette.BLUE2);
        blues.add(ColorBrewerPalette.BLUE3);
        blues.add(ColorBrewerPalette.BLUE4);
        blues.add(ColorBrewerPalette.BLUE5);
        blues.add(GoogleMaterialDesignPalette.INDIGO);
        blues.add(GoogleMaterialDesignPalette.BLUE);
        blues.add(GoogleMaterialDesignPalette.LIGHT_BLUE);
        blues.add(GoogleMaterialDesignPalette.CYAN);

        ArrayList<int[]> reds = new ArrayList<>();
        reds.add(ColorBrewerPalette.RED1);
        reds.add(ColorBrewerPalette.RED2);
        reds.add(ColorBrewerPalette.RED3);
        reds.add(GoogleMaterialDesignPalette.RED);
        reds.add(GoogleMaterialDesignPalette.PINK);

        ArrayList<int[]> purples = new ArrayList<>();
        purples.add(ColorBrewerPalette.PURPLE1);
        purples.add(ColorBrewerPalette.PURPLE2);
        purples.add(ColorBrewerPalette.PURPLE3);
        purples.add(ColorBrewerPalette.PURPLE4);
        purples.add(GoogleMaterialDesignPalette.PURPLE);
        purples.add(GoogleMaterialDesignPalette.DEEP_PURPLE);

        ArrayList<int[]> oranges = new ArrayList<>();
        oranges.add(ColorBrewerPalette.ORANGE1);
        oranges.add(ColorBrewerPalette.ORANGE2);
        oranges.add(GoogleMaterialDesignPalette.ORANGE);
        oranges.add(GoogleMaterialDesignPalette.DEEP_ORANGE);
        
        ArrayList<int[]> yellows = new ArrayList<>();
        yellows.add(GoogleMaterialDesignPalette.YELLOW);
        yellows.add(GoogleMaterialDesignPalette.AMBER);
        
        ArrayList<int[]> greys = new ArrayList<>();
        greys.add(GoogleMaterialDesignPalette.GREY);
        greys.add(GoogleMaterialDesignPalette.BLUE_GREY);
        
        ArrayList<int[]> browns = new ArrayList<>();
        browns.add(GoogleMaterialDesignPalette.BROWN);

        ArrayList<int[]> blacks = new ArrayList<>();
        blacks.add(ColorBrewerPalette.BLACK);

        ArrayList<ArrayList<int[]>> colors = new ArrayList<>();
        colors.add(greens);
        colors.add(blues);
        colors.add(purples);
        colors.add(reds);
        colors.add(oranges);
        colors.add(yellows);
        colors.add(blacks);
        colors.add(greys);
        colors.add(browns);

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

            while(Math.abs(from - to) < 2) {
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
