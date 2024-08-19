
package fractalzoomer.main.app_settings;

import fractalzoomer.main.Constants;

import java.awt.*;

/**
 *
 * @author kaloch
 */
public class GradientSettings {
    public Color colorA;
    public Color colorB;
    public int gradient_color_space;
    public int gradient_interpolation;
    public boolean gradient_reversed;
    public int gradient_offset;
    
    public GradientSettings() {
        colorA = Color.BLACK;
        colorB = Color.WHITE;
        gradient_color_space = Constants.COLOR_SPACE_RGB;
        gradient_interpolation = Constants.INTERPOLATION_LINEAR;
        gradient_reversed = false;
        gradient_offset = 0;
    }           
}
