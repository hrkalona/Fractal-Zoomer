/*
 * Copyright (C) 2020 hrkalona
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
package fractalzoomer.true_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.palettes.transfer_functions.TransferFunction;

/**
 *
 * @author hrkalona
 */
public abstract class TrueColorAlgorithm {
    public static PaletteColor palette_outcoloring;
    public static PaletteColor palette_incoloring;
    public static TransferFunction color_transfer_outcoloring;
    public static TransferFunction color_transfer_incoloring;
    public static boolean usePaletteForInColoring;
    public static int color_cycling_location_outcoloring;
    public static int color_cycling_location_incoloring;
    public static int[] gradient;
    public static int gradient_offset;

    public TrueColorAlgorithm() {

    }

    public abstract int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, Complex c0, Complex pixel, double stat, double trap, boolean escaped);

    
}
