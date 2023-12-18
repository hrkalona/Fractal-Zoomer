package fractalzoomer.palettes.transfer_functions;

public class LinearTransferFunction extends TransferFunction {

    private double color_intensity;
    private double itPaletteDensity;

    public LinearTransferFunction(int paletteLength, double color_intensity, double colorDensity) {

        super(paletteLength);
        this.color_intensity = color_intensity;

        final double realColorDensity = colorDensity / 100.0;
        itPaletteDensity = Math.pow(10, realColorDensity) * IT_COLOR_DENSITY_MULTIPLIER;

    }

    @Override
    public double transfer(double result) {

        if (result < 0) {
            result = -result; // transfer to positive
            result *= itPaletteDensity;
            result *= paletteLength * paletteMultiplier;
            result = -result; // transfer to negative
        } else {
            result *= itPaletteDensity;
            result *= paletteLength * paletteMultiplier;
        }

        return result * color_intensity;

    }
}
