
package fractalzoomer.palettes;

import fractalzoomer.main.app_settings.CosinePaletteSettings;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.InfiniteWave;
import fractalzoomer.utils.Multiwave;
import fractalzoomer.utils.MultiwaveSimple;

import java.awt.*;

public abstract class PaletteColor {

    protected int[] palette;
    protected int[] special_colors;
    protected int special_color;
    protected boolean special_use_palette_color;
    protected int generatedPaletteLength;
    protected boolean useGeneratedPalette;

    protected boolean color_smoothing;
    

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

        color_smoothing = true;

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

    public int[] getPalette() {
        return palette;
    }
    
    public int getSpecialColor() {
        
        return special_color;
        
    }

    public abstract int calculateColor(double result, int paletteId,  int color_cycling_location, int extra_offset, int cycle, double factor, CosinePaletteSettings iqps, boolean outcoloring);

    private static double twoPi = Math.PI * 2;
    public static int getGeneratedColor(double result, int id, int color_cycling_location, int extra_offset, int cycle, double factor, CosinePaletteSettings iqps, boolean outcoloring, Multiwave.MultiwaveColorParams[] mw, InfiniteWave.InfiniteColorWaveParams[] iw, MultiwaveSimple.MultiwaveSimpleColorParams[] smw) {
        double value = (Math.abs(result) * factor + color_cycling_location + extra_offset) % cycle;
        switch (id) {
            case 0:
                try {
                    return Multiwave.multiwave_default(value);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
            case 1:
                try {
                    return Multiwave.g_spdz2(value);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
            case 2:
                try {
                    return Multiwave.g_spdz2_custom(value);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
            case 3://Inigo Quilez
                double t = value / cycle;
                int red = (int)(255.0 * (iqps.redA + iqps.redB * Math.cos( twoPi * (iqps.redC * t + iqps.redD) + iqps.redG)) + 0.5);
                int green = (int)(255.0 * (iqps.greenA + iqps.greenB * Math.cos( twoPi * (iqps.greenC * t + iqps.greenD) + iqps.greenG)) + 0.5);
                int blue = (int)(255.0 * (iqps.blueA + iqps.blueB * Math.cos( twoPi * (iqps.blueC * t + iqps.blueD) + iqps.blueG)) + 0.5);
                red = ColorSpaceConverter.clamp(red);
                green = ColorSpaceConverter.clamp(green);
                blue = ColorSpaceConverter.clamp(blue);
                return 0xff000000 | (red << 16) | (green << 8) | blue;
            case 4:
                try {
                    Multiwave.MultiwaveColorParams[] params = mw != null ? mw : (outcoloring ? Multiwave.user_params_out : Multiwave.user_params_in);
                    return Multiwave.general_palette(value, params);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
            case 5:
                try {
                    InfiniteWave.InfiniteColorWaveParams[] params = iw != null ? iw : (outcoloring ? InfiniteWave.user_params_out : InfiniteWave.user_params_in);
                    return InfiniteWave.get_color(value, params);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
            case 6:
                try {
                    MultiwaveSimple.MultiwaveSimpleColorParams[] params = smw != null ? smw : (outcoloring ? MultiwaveSimple.user_params_out : MultiwaveSimple.user_params_in);
                    return MultiwaveSimple.get_color(value, params);
                }
                catch (Exception ex) {
                    return 0xff000000;
                }
        }

        return 0;
    }

    public void setColorSmoothing(boolean color_smoothing) {
        this.color_smoothing = color_smoothing;
    }

    public boolean getColorSmoothing() {
        return color_smoothing;
    }

    public boolean usesGeneratedPalette() {
        return useGeneratedPalette;
    }

}
