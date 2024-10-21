
package fractalzoomer.core.blending;

import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author kaloch
 */
public class LCHChromaBlending extends Blending {

    public LCHChromaBlending(int color_interpolation, int color_space) {

        super(color_interpolation, color_space);

    }

    @Override
    public int blendInternal(int redA, int greenA, int blueA, int redB, int greenB, int blueB, double coef) {

        int temp_red = 0, temp_green = 0, temp_blue = 0;
        
        if(redB == 0 && greenB == 0 && blueB == 0) {
            redB = 1;
            greenB = 1;
            blueB = 1;
        }

        double[] resB = ColorSpaceConverter.RGBtoLAB(redB, greenB, blueB);
        double[] resA = ColorSpaceConverter.RGBtoLAB(redA, greenA, blueA);

        double A1 = resB[1];
        double B1 = resB[2];
        double c1 = Math.hypot(A1, B1);
        
        c1 = c1 == 0 ?  1e-16 : c1;

        double A2 = resA[1];
        double B2 = resA[2];
        double c2 = Math.hypot(A2, B2);

        double A = c2 * A1 / c1;
        double B = c2 * B1 / c1;

        int[] rgb = ColorSpaceConverter.LABtoRGB(resB[0], A, B);

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
