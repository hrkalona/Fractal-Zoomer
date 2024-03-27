package fractalzoomer.main.app_settings;

public class ColorCyclingSettings {
    public int color_cycling_speed;
    public int color_cycling_adjusting_value;
    public int gradient_cycling_adjusting_value;
    public int slope_cycling_adjusting_value;
    public int light_cycling_adjusting_value;
    public int bump_cycling_adjusting_value;

    public ColorCyclingSettings() {
        color_cycling_adjusting_value = 1;
        gradient_cycling_adjusting_value = 0;
        slope_cycling_adjusting_value = 0;
        light_cycling_adjusting_value = 0;
        bump_cycling_adjusting_value = 0;
        color_cycling_speed = 140;
    }
}
