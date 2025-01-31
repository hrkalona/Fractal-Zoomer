package fractalzoomer.main.app_settings;

import fractalzoomer.utils.InfiniteWave;
import fractalzoomer.utils.Multiwave;
import fractalzoomer.utils.MultiwaveSimple;

public class GeneratedPaletteSettings {
    public static final int DEFAULT_LARGE_LENGTH = 2100000000;
    public static final int DEFAULT_SMALL_LENGTH = 100;
    public boolean useGeneratedPaletteOutColoring;
    public int generatedPaletteOutColoringId;
    public boolean useGeneratedPaletteInColoring;
    public int generatedPaletteInColoringId;

    public boolean blendNormalPaletteWithGeneratedPaletteOutColoring;
    public boolean blendNormalPaletteWithGeneratedPaletteInColoring;
    public double blendingOutColoring;
    public double blendingInColoring;

    public int restartGeneratedOutColoringPaletteAt;
    public int restartGeneratedInColoringPaletteAt;
    public CosinePaletteSettings outColoringIQ;
    public CosinePaletteSettings inColoringIQ;
    public String outcoloring_multiwave_user_palette;
    public String incoloring_multiwave_user_palette;
    public String outcoloring_infinite_wave_user_palette;
    public String incoloring_infinite_wave_user_palette;
    public String outcoloring_simple_multiwave_user_palette;
    public String incoloring_simple_multiwave_user_palette;
    public int GeneratedOutColoringPaletteOffset;
    public int GeneratedInColoringPaletteOffset;
    public double GeneratedOutColoringPaletteFactor;
    public double GeneratedInColoringPaletteFactor;



    public GeneratedPaletteSettings() {
        useGeneratedPaletteOutColoring = false;
        useGeneratedPaletteInColoring = false;
        generatedPaletteOutColoringId = 0;
        generatedPaletteInColoringId = 0;
        restartGeneratedOutColoringPaletteAt = DEFAULT_LARGE_LENGTH;
        restartGeneratedInColoringPaletteAt = DEFAULT_LARGE_LENGTH;
        outColoringIQ = new CosinePaletteSettings();
        inColoringIQ = new CosinePaletteSettings();
        try {
            outcoloring_multiwave_user_palette = Multiwave.paramsToJson(Multiwave.simple_params, false);
        }
        catch (Exception ex) {
            outcoloring_multiwave_user_palette = "";
        }
        try {
            incoloring_multiwave_user_palette = Multiwave.paramsToJson(Multiwave.simple_params, false);
        }
        catch (Exception ex) {
            incoloring_multiwave_user_palette = "";
        }

        try {
            outcoloring_infinite_wave_user_palette = InfiniteWave.paramsToJson(InfiniteWave.defaultParams, false);
        }
        catch (Exception ex) {
            outcoloring_infinite_wave_user_palette = "";
        }

        try {
            incoloring_infinite_wave_user_palette = InfiniteWave.paramsToJson(InfiniteWave.defaultParams, false);
        }
        catch (Exception ex) {
            incoloring_infinite_wave_user_palette = "";
        }

        try {
            outcoloring_simple_multiwave_user_palette = MultiwaveSimple.paramsToJson(MultiwaveSimple.defaultParams, false);
        }
        catch (Exception ex) {
            outcoloring_simple_multiwave_user_palette = "";
        }

        try {
            incoloring_simple_multiwave_user_palette = MultiwaveSimple.paramsToJson(MultiwaveSimple.defaultParams, false);
        }
        catch (Exception ex) {
            incoloring_simple_multiwave_user_palette = "";
        }

        blendNormalPaletteWithGeneratedPaletteOutColoring = false;
        blendNormalPaletteWithGeneratedPaletteInColoring = false;
        blendingOutColoring = 0.5;
        blendingInColoring = 0.5;
        GeneratedOutColoringPaletteOffset = 0;
        GeneratedInColoringPaletteOffset = 0;
        GeneratedOutColoringPaletteFactor = 1;
        GeneratedInColoringPaletteFactor = 1;

    }

    public GeneratedPaletteSettings(GeneratedPaletteSettings other) {
        useGeneratedPaletteOutColoring = other.useGeneratedPaletteOutColoring;
        useGeneratedPaletteInColoring = other.useGeneratedPaletteInColoring;
        generatedPaletteOutColoringId = other.generatedPaletteOutColoringId;
        generatedPaletteInColoringId = other.generatedPaletteInColoringId;
        outColoringIQ = new CosinePaletteSettings(other.outColoringIQ);
        inColoringIQ = new CosinePaletteSettings(other.inColoringIQ);
        restartGeneratedOutColoringPaletteAt = other.restartGeneratedOutColoringPaletteAt;
        restartGeneratedInColoringPaletteAt = other.restartGeneratedInColoringPaletteAt;
        outcoloring_multiwave_user_palette = new String(other.outcoloring_multiwave_user_palette);
        incoloring_multiwave_user_palette = new String(other.incoloring_multiwave_user_palette);
        outcoloring_infinite_wave_user_palette = new String(other.outcoloring_infinite_wave_user_palette);
        incoloring_infinite_wave_user_palette = new String(other.incoloring_infinite_wave_user_palette);
        outcoloring_simple_multiwave_user_palette = new String(other.outcoloring_simple_multiwave_user_palette);
        incoloring_simple_multiwave_user_palette = new String(other.incoloring_simple_multiwave_user_palette);
        blendNormalPaletteWithGeneratedPaletteInColoring = other.blendNormalPaletteWithGeneratedPaletteInColoring;
        blendNormalPaletteWithGeneratedPaletteOutColoring = other.blendNormalPaletteWithGeneratedPaletteOutColoring;
        blendingOutColoring = other.blendingOutColoring;
        blendingInColoring = other.blendingInColoring;
        GeneratedOutColoringPaletteOffset = other.GeneratedOutColoringPaletteOffset;
        GeneratedInColoringPaletteOffset = other.GeneratedInColoringPaletteOffset;
        GeneratedOutColoringPaletteFactor = other.GeneratedOutColoringPaletteFactor;
        GeneratedInColoringPaletteFactor = other.GeneratedInColoringPaletteFactor;
    }
}
