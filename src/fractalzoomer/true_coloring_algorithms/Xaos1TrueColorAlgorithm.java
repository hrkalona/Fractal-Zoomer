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
import fractalzoomer.utils.ColorSpaceConverter;

/**
 *
 * @author hrkalona
 */
public class Xaos1TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos1TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, double stat, double trap, boolean escaped) {

        double re = z.getRe();
        double im = -z.getIm();
        
        int r = (int) ((Math.sin(Math.atan2(re, im) * 20) + 1) * 127 + 0.5);
        int w = (int) (Math.sin((im / re)) * 127 + 0.5);
        int b = (int) (re * im + 0.5);
        int g = (int) ((Math.sin((re * re) * 0.5) + 1) * 127 + 0.5);

        r += w;
        g += w;
        b += w;
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return  0xFF000000 | (r << 16) | (g << 8) | b;
        
    }

}
