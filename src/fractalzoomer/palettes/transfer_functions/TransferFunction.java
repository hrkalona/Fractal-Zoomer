/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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

import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public abstract class TransferFunction {
    private int paletteLength;
    
    public TransferFunction(int paletteLength) {
        
        this.paletteLength = paletteLength;
        
    }
    
    public double transfer(double result) {
       
        if(result < 0) {
            result *= -1; // transfer to positive
            result -= ColorAlgorithm.MAGIC_OFFSET_NUMBER;

            result /= paletteLength; //scale to palette multiple
            result = function(result); // apply transfer
            result *= paletteLength; // rescale to palette length

            result += ColorAlgorithm.MAGIC_OFFSET_NUMBER;
            result *= -1; // transfer to negative
        }
        else {
            result -= ColorAlgorithm.MAGIC_OFFSET_NUMBER;

            result /= paletteLength; //scale to palette multiple1
            result = function(result); // apply transfer
            result *= paletteLength; // rescale to palette length

            result += ColorAlgorithm.MAGIC_OFFSET_NUMBER;
        }

        return result;
    }
    
    protected abstract double function(double result);
}
