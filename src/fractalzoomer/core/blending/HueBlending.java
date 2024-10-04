
package fractalzoomer.core.blending;

import java.awt.*;

/**
 *
 * @author kaloch
 */
public class HueBlending extends Blending {

    public HueBlending(int color_interpolation, int color_space) {
        super(color_interpolation, color_space);
    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
        
        float[] hsbvalsContours = new float[3];
        Color.RGBtoHSB(redA, greenA, blueA, hsbvalsContours);

        float[] hsbvals = new float[3];
        Color.RGBtoHSB(redB, greenB, blueB, hsbvals);
        
        int temp_red = 0, temp_green = 0, temp_blue = 0;
        if(hsbvalsContours[1] == 0) {
            temp_red = redB;
            temp_green = greenB;
            temp_blue = blueB;
        }
        else {
            int temp_color = Color.HSBtoRGB(hsbvalsContours[0], hsbvals[1], hsbvals[2]);
            
            temp_red = (temp_color >> 16) & 0xFF;
            temp_green = (temp_color >> 8) & 0xFF;
            temp_blue = temp_color & 0xFF;
        }
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);

    }
    
}
