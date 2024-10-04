
package fractalzoomer.core.blending;

import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author kaloch
 */
public class LCHLightnessBlending extends Blending {
    
    public LCHLightnessBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {
      
        int temp_red = 0, temp_green = 0, temp_blue = 0;
        
        double[] resB = ColorSpaceConverter.RGBtoLAB(redB, greenB, blueB);
        double[] resA = ColorSpaceConverter.RGBtoLAB(redA, greenA, blueA);
        
        int[] rgb = ColorSpaceConverter.LABtoRGB(resA[0], resB[1], resB[2]);
        
        temp_red = rgb[0];
        temp_green = rgb[1];
        temp_blue = rgb[2];
        
        temp_red = temp_red > 255 ? 255 : temp_red;
        temp_green = temp_green > 255 ? 255 : temp_green;
        temp_blue = temp_blue > 255 ? 255 : temp_blue;
        
        temp_red = temp_red < 0 ? 0 : temp_red;
        temp_green = temp_green < 0 ? 0 : temp_green;
        temp_blue = temp_blue < 0 ? 0 : temp_blue;
        
        return method.interpolateColors(redB, greenB, blueB, temp_red, temp_green, temp_blue, coef, false);
        
    } 
    
}
