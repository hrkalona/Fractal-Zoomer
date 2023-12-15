package fractalzoomer.main.app_settings;

public class GeneratedPaletteSettings {
    public static final int DEFAULT_LARGE_LENGTH = 2100000000;
    public static final int DEFAULT_SMALL_LENGTH = 100;
    public boolean useGeneratedPaletteOutColoring;
    public int generatedPaletteOutColoringId;
    public boolean useGeneratedPaletteInColoring;
    public int generatedPaletteInColoringId;

    public int restartGeneratedOutColoringPaletteAt;
    public int restartGeneratedInColoringPaletteAt;
    public CosinePaletteSettings outColoringIQ;
    public CosinePaletteSettings inColoringIQ;



    public GeneratedPaletteSettings() {
        useGeneratedPaletteOutColoring = false;
        useGeneratedPaletteInColoring = false;
        generatedPaletteOutColoringId = 0;
        generatedPaletteInColoringId = 0;
        restartGeneratedOutColoringPaletteAt = DEFAULT_LARGE_LENGTH;
        restartGeneratedInColoringPaletteAt = DEFAULT_LARGE_LENGTH;
        outColoringIQ = new CosinePaletteSettings();
        inColoringIQ = new CosinePaletteSettings();

    }
}
