/*
 * Copyright (C) 2019 hrkalona
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
public class Xaos8TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos8TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, boolean escaped) {

        double re = z.getRe();
        double im = z.getIm();

        double cre = c.getRe();
        double cim = c.getIm();

        int r, g, b;

        if (escaped) {
            r = (int) ((Math.abs(re * cim)) * 64 + 0.5);
            b = (int) ((Math.abs(im * cim)) * 64 + 0.5);
            g = (int) ((Math.abs(re * cre)) * 64 + 0.5);
        } else {
            r = (int) ((Math.abs(re * cim)) * 256 + 0.5);
            b = (int) ((Math.abs(im * cim)) * 256 + 0.5);
            g = (int) ((Math.abs(re * cre)) * 256 + 0.5);
        }
        
        r = ColorSpaceConverter.clamp(r);
        g = ColorSpaceConverter.clamp(g);
        b = ColorSpaceConverter.clamp(b);

        return 0xFF000000 | (r << 16) | (g << 8) | b;

    }

}
