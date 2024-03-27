/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.palettes.transfer_functions;

/**
 *
 * @author hrkalona2
 */
public class AtanTransferFunction extends TransferFunction {

    private double color_intensity;
    private double itPaletteDensity;

    public AtanTransferFunction(int paletteLength, double color_intensity, double colorDensity) {

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
            result = Math.atan(result);
            result *= paletteLength * paletteMultiplier;
            result = -result; // transfer to negative
        } else {
            result *= itPaletteDensity;
            result = Math.atan(result);
            result *= paletteLength * paletteMultiplier;
        }

        return result * color_intensity;

    }

}
