
package fractalzoomer.palettes;

import fractalzoomer.main.app_settings.CosinePaletteSettings;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.Multiwave;

import java.awt.*;

public abstract class PaletteColor {

    protected int[] palette;
    protected int[] special_colors;
    protected int special_color;
    protected boolean special_use_palette_color;
    protected int generatedPaletteLength;
    protected boolean useGeneratedPalette;
    

    public PaletteColor(int[] palette, Color special_color, boolean special_use_palette_color) {

        this.palette = palette;

        this.special_use_palette_color = special_use_palette_color;
        
        this.special_color = special_color.getRGB();

        if(!special_use_palette_color) {
            this.special_colors = new int[2]; //create two almost the same colors just for boundaries

            this.special_colors[0] = this.special_color;

            Color last_color;
            if(special_color.getBlue() == 255) {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() - 1);
                this.special_colors[1] = last_color.getRGB();
            }
            else {
                last_color = new Color(special_color.getRed(), special_color.getGreen(), special_color.getBlue() + 1);
                this.special_colors[1] = last_color.getRGB();
            }
        }

    }

    public abstract int getPaletteColor(double result);

    public void setGeneratedPaletteSettings(boolean outcoloring, GeneratedPaletteSettings gps) {
       if(outcoloring) {
           useGeneratedPalette = gps.useGeneratedPaletteOutColoring;
           generatedPaletteLength = gps.restartGeneratedOutColoringPaletteAt;
       }
       else {
           useGeneratedPalette = gps.useGeneratedPaletteInColoring;
           generatedPaletteLength = gps.restartGeneratedInColoringPaletteAt;
       }
    }

    public int getPaletteLength() {

        return useGeneratedPalette ? generatedPaletteLength : palette.length;

    }
    
    public int getSpecialColor() {
        
        return special_color;
        
    }

    public abstract int calculateColor(double result, int paletteId,  int color_cycling_location, int cycle, CosinePaletteSettings iqps);

    private static double twoPi = Math.PI * 2;
    protected static int getGeneratedColor(double result, int id, int color_cycling_location, int cycle, CosinePaletteSettings iqps) {
        double value = (Math.abs(result) + color_cycling_location) % cycle;
        switch (id) {
            case 0:
                return Multiwave.multiwave_default(value);
            case 1:
                return Multiwave.g_spdz2(value);
            case 2:
                return Multiwave.g_spdz2_custom(value);
            case 3://Inigo Quilez
                double t = value / cycle;
                int red = (int)(255.0 * (iqps.redA + iqps.redB * Math.cos( twoPi * (iqps.redC * t + iqps.redD) + iqps.redG)) + 0.5);
                int green = (int)(255.0 * (iqps.greenA + iqps.greenB * Math.cos( twoPi * (iqps.greenC * t + iqps.greenD) + iqps.greenG)) + 0.5);
                int blue = (int)(255.0 * (iqps.blueA + iqps.blueB * Math.cos( twoPi * (iqps.blueC * t + iqps.blueD) + iqps.blueG)) + 0.5);
                red = ColorSpaceConverter.clamp(red);
                green = ColorSpaceConverter.clamp(green);
                blue = ColorSpaceConverter.clamp(blue);
                return 0xff000000 | (red << 16) | (green << 8) | blue;
        }

        return 0;
    }

}
