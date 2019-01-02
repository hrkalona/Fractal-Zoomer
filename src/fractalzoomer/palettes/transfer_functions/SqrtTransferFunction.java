/*
 * Fractal Zoomer, Copyright (C) 2019 hrkalona2
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
public class SqrtTransferFunction extends TransferFunction {

    private double color_intensity;

    public SqrtTransferFunction(int paletteLength, double color_intensity) {

        super(paletteLength);
        this.color_intensity = color_intensity;

    }

    @Override
    public double transfer(double result) {

        if (result < 0) {
            result *= -1; // transfer to positive
            result /= paletteLength; //scale to palette multiple        
            result = Math.sqrt(result * color_intensity + 1);
            result *= paletteLength; // rescale to palette length  
            result *= -1; // transfer to negative
        } else {
            result /= paletteLength; //scale to palette multiple        
            result = Math.sqrt(result * color_intensity + 1);
            result *= paletteLength; // rescale to palette length  
        }

        return result;

    }

}
