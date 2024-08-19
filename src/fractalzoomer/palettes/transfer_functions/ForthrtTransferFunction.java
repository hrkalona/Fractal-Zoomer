
package fractalzoomer.palettes.transfer_functions;

/**
 *
 * @author hrkalona2
 */
public class ForthrtTransferFunction extends TransferFunction {

    private double color_intensity;
    private double itPaletteDensity;

    public ForthrtTransferFunction(int paletteLength, double color_intensity, double colorDensity, boolean banded) {

        super(paletteLength, banded);
        this.color_intensity = color_intensity;

        final double realColorDensity = colorDensity / 100.0;
        itPaletteDensity = Math.pow(10, realColorDensity) * IT_COLOR_DENSITY_MULTIPLIER;

    }

    @Override
    public double transfer(double result) {

        boolean isNeg = result < 0;

        if (isNeg) {
            result = -result;
        }

        if(banded) {
            result = (long) (result);
        }

        result *= itPaletteDensity;
        result = Math.sqrt(Math.sqrt(result));
        result *= paletteLength * paletteMultiplier;

        double final_result = result * color_intensity;
        return isNeg ? -final_result : final_result;

    }

}
