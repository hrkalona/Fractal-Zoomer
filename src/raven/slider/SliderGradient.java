package raven.slider;

import java.awt.Color;
import javax.swing.JSlider;

public class SliderGradient extends JSlider {
    public static Color defaultColor1 = new Color(18, 98, 175);
    public static Color defaultColor2 = new Color(211, 203, 0);

    public SliderGradient(int orientation, int min, int max, int value) {
        super(orientation, min, max, value);
        setUI(new SliderGradientUI(this));
    }

    public Color getTicksColor() {
        return ticksColor;
    }

    public void setTicksColor(Color ticksColor) {
        this.ticksColor = ticksColor;
    }

    public int getTrackSize() {
        return trackSize;
    }

    public void setTrackSize(int trackSize) {
        this.trackSize = trackSize;
    }

    public Color getColor1() {
        return color1 == null ? defaultColor1 : color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2 == null ? defaultColor2 : color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    private Color color1;
    private Color color2;
    private Color ticksColor = new Color(0, 0, 0);
    private int trackSize = 3;

    public int getThumbSize() {
        return thumbSize;
    }

    public void setThumbSize(int thumbSize) {
        this.thumbSize = thumbSize;
    }

    private int thumbSize = 17;

    public SliderGradient() {
        setUI(new SliderGradientUI(this));
    }
}
