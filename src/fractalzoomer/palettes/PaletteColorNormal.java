
package fractalzoomer.palettes;

import fractalzoomer.main.app_settings.CosinePaletteSettings;

import java.awt.*;

public class PaletteColorNormal extends PaletteColor {

    public PaletteColorNormal(int[] palette, Color special_color, boolean special_use_palette_color) {

        super(palette, special_color, special_use_palette_color);

    }

    @Override
    public int getPaletteColor(double result) {

        if(result < 0) {
            if(!special_use_palette_color) {
                return special_colors[(int)(((long)(result * (-1))) % special_colors.length)];
            }
            else {
                return palette[(int)(((long)(result * (-1))) % palette.length)];
            }
        }
        else {
            return palette[(int)(((long)result) % palette.length)];
        }

    }

    @Override
    public int calculateColor(double result, int paletteId,  int color_cycling_location, int cycle, CosinePaletteSettings iqps) {

        return getGeneratedColor((int)result, paletteId, color_cycling_location, cycle, iqps);

    }

}
