
package fractalzoomer.palettes;

import fractalzoomer.main.MainWindow;

import java.awt.*;

import static fractalzoomer.palettes.PresetsPalettes.*;
import static fractalzoomer.palettes.PresetsPalettes2.*;

/**
 *
 * @author hrkalona2
 */
public class PresetPalette extends Palette {


    public PresetPalette(int color_choice, int[] direct_palette, boolean smoothing, Color special_color, int color_smoothing_method, boolean special_use_palette_color, int fractional_transfer_method, int smoothing_color_space, boolean color_smoothing) {

        createPalette(color_choice, direct_palette, smoothing, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);

    }

    private void createPalette(int color_choice, int[] direct_palette, boolean smoothing, Color special_color, int color_smoothing_method, boolean special_use_palette_color, int fractional_transfer_method, int smoothing_color_space, boolean color_smoothing) {

        switch (color_choice) {

            case 0:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(default_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(default_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 1:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(spectrum_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(spectrum_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 2:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 3:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative2_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative2_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 4:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative3_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative3_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 5:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative4_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative4_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 6:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative5_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative5_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 7:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative6_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative6_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 8:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative7_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative7_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 9:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative8_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative8_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 10:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(alternative9_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(alternative9_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 11:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(dusk_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(dusk_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 12:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(grayscale_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(grayscale_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 13:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(earthsky_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(earthsky_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 14:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(hotcold_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 15:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(hotcold2_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(hotcold2_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 16:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(fire_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(fire_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 17:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(jet_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(jet_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case MainWindow.DIRECT_PALETTE_ID:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(direct_palette, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(direct_palette, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 20:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(default_fractint, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(default_fractint, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 21:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(arriw, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(arriw, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 22:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(atomic, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(atomic, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 23:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(blue, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(blue, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 24:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(blues, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(blues, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 25:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(chroma, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(chroma, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 26:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(jfestival, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(jfestival, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 27:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(neon, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(neon, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 28:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(rich8z3, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(rich8z3, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 29:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(skydye11, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(skydye11, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 30:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(skydye12, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(skydye12, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 31:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(spiral, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(spiral, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 32:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(volcano, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(volcano, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 33:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(wizzl014, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(wizzl014, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 34:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(wizzl017, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(wizzl017, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 35:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(wizzl018, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(wizzl018, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 36:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(qfractalnow, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(qfractalnow, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 37:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(qfractalnow2, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(qfractalnow2, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 38:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(qfractalnow3, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(qfractalnow3, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 39:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(qfractalnow4, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(qfractalnow4, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 40:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(qfractalnow5, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(qfractalnow5, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 41:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(fx_fast_changes, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(fx_fast_changes, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 42:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(fx_rainbow, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(fx_rainbow, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 43:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(fx_three_primaries, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(fx_three_primaries, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 44:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(fx_six_primaries, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(fx_six_primaries, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 45:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(xaos_default, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(xaos_default, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;
            case 46:
                if (!smoothing) {
                    palette_color = new PaletteColorNormal(kf_default, special_color, special_use_palette_color);
                } else {
                    palette_color = new PaletteColorSmooth(kf_default, special_color, color_smoothing_method, special_use_palette_color, fractional_transfer_method, smoothing_color_space, color_smoothing);
                }
                break;

        }
    }

    public static Color[] getPalette(int[] palette, int color_cycling_location) {
        Color[] colors = new Color[palette.length];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(palette[(int)(((long)i + color_cycling_location) % colors.length)]);
        }

        return colors;

    }

    public static Color[] getPalette(int id, int color_cycling_location) {

        int[] palette;

        switch (id) {
            case 0:
                palette = default_palette;
                break;
            case 1:
                palette = spectrum_palette;
                break;
            case 2:
                palette = alternative_palette;
                break;
            case 3:
                palette = alternative2_palette;
                break;
            case 4:
                palette = alternative3_palette;
                break;
            case 5:
                palette = alternative4_palette;
                break;
            case 6:
                palette = alternative5_palette;
                break;
            case 7:
                palette = alternative6_palette;
                break;
            case 8:
                palette = alternative7_palette;
                break;
            case 9:
                palette = alternative8_palette;
                break;
            case 10:
                palette = alternative9_palette;
                break;
            case 11:
                palette = dusk_palette;
                break;
            case 12:
                palette = grayscale_palette;
                break;
            case 13:
                palette = earthsky_palette;
                break;
            case 14:
                palette = hotcold_palette;
                break;
            case 15:
                palette = hotcold2_palette;
                break;
            case 16:
                palette = fire_palette;
                break;
            case 17:
                palette = jet_palette;
                break;
            case 20:
                palette = default_fractint;
                break;
            case 21:
                palette = arriw;
                break;
            case 22:
                palette = atomic;
                break;
            case 23:
                palette = blue;
                break;
            case 24:
                palette = blues;
                break;
            case 25:
                palette = chroma;
                break;
            case 26:
                palette = jfestival;
                break;
            case 27:
                palette = neon;
                break;
            case 28:
                palette = rich8z3;
                break;
            case 29:
                palette = skydye11;
                break;
            case 30:
                palette = skydye12;
                break;
            case 31:
                palette = spiral;
                break;
            case 32:
                palette = volcano;
                break;
            case 33:
                palette = wizzl014;
                break;
            case 34:
                palette = wizzl017;
                break;
            case 35:
                palette = wizzl018;
                break;
            case 36:
                palette = qfractalnow;
                break;
            case 37:
                palette = qfractalnow2;
                break;
            case 38:
                palette = qfractalnow3;
                break;
            case 39:
                palette = qfractalnow4;
                break;
            case 40:
                palette = qfractalnow5;
                break;
            case 41:
                palette = fx_fast_changes;
                break;
            case 42:
                palette = fx_rainbow;
                break;
            case 43:
                palette = fx_three_primaries;
                break;
            case 44:
                palette = fx_six_primaries;
                break;
            case 45:
                palette = xaos_default;
                break;
            case 46:
                palette = kf_default;
                break;
            default:
                palette = default_fractint;
                break;

        }

        Color[] colors = new Color[palette.length];

        for (int i = 0; i < colors.length; i++) {
            colors[i] = new Color(palette[(int)(((long)i + color_cycling_location) % colors.length)]);
        }

        return colors;
    }
}
