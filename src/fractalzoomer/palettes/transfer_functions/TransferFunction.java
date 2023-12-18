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
public abstract class TransferFunction {
    protected static double IT_COLOR_DENSITY_MULTIPLIER = 0.05;
    protected int paletteLength;
    protected double paletteMultiplier;
    
    public TransferFunction(int paletteLength) {
        
        this.paletteLength = paletteLength;
        paletteMultiplier = paletteLength <= 2000 ? 1 : 2000.0 / paletteLength;
        
    }
    
    public abstract double transfer(double result);

}
