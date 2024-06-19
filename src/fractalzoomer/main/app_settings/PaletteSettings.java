
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

/**
 *
 * @author kaloch
 */
public class PaletteSettings implements Constants {
    public int color_choice;
    public int color_cycling_location;
    public int color_interpolation;
    public int color_space;
    public boolean reversed_palette;
    public double scale_factor_palette_val;
    public int processing_alg;
    public double color_intensity;
    public double color_density;
    public int transfer_function;
    public int[] direct_palette;
    public int[][] custom_palette = {{12, 255, 0, 0}, {12, 255, 127, 0}, {12, 255, 255, 0}, {12, 0, 255, 0}, {12, 0, 0, 255}, {12, 75, 0, 130}, {12, 143, 0, 255}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

    public PaletteSettings() {
        
        color_choice = 0;
        color_interpolation = 0;
        color_space = 0;
        color_cycling_location = 0;
        scale_factor_palette_val = 0;
        processing_alg = PROCESSING_NONE;
        color_intensity = 1;
        transfer_function = DEFAULT;
        
        direct_palette = new int[1];
        direct_palette[0] = 0xffffffff;
        color_density = -100;

    }
}
