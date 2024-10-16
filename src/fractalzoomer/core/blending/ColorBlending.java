
package fractalzoomer.core.blending;

import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public class ColorBlending extends Blending {

    public ColorBlending(int color_interpolation, int color_space) {
        super(color_interpolation, color_space);
    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        float[] hsbvalsContours = new float[3];
        Color.RGBtoHSB(redA, greenA, blueA, hsbvalsContours);

        float[] hsbvals = new float[3];
        Color.RGBtoHSB(redB, greenB, blueB, hsbvals);

        int temp_color = Color.HSBtoRGB(hsbvalsContours[0], hsbvalsContours[1], hsbvals[2]);

        int temp_red = (temp_color >> 16) & 0xFF;
        int temp_green = (temp_color >> 8) & 0xFF;
        int temp_blue = temp_color & 0xFF;

        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }

}
