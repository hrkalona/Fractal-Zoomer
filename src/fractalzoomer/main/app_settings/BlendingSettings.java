package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

public class BlendingSettings {
    public int color_blending;
    public boolean blending_reversed_colors;

    public BlendingSettings() {
        color_blending = Constants.NORMAL_BLENDING;
        blending_reversed_colors = false;
    }
}
