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
public class Xaos3TrueColorAlgorithm extends TrueColorAlgorithm {

    public Xaos3TrueColorAlgorithm() {
        super();
    }

    @Override
    public int createColor(Complex z, Complex zold, Complex zold2, int iterations, Complex c, Complex start, boolean escaped) {

        double h, s, br;
        
        double re = z.getRe();
        double im = z.getIm();

        if (escaped) {
            h = 1 - (Math.atan2(re, im)) / (Math.PI);
            s = (Math.sin(re) + 1) * 0.5;
            br = (Math.sin(im + Math.PI) + 1) * 0.5;
        } else {
            h = 1 - (Math.atan2(re, im)) / (Math.PI);
            s = (Math.sin(re * 50) + 1) * 0.5;
            br = (Math.sin(im * 50 + Math.PI) + 1) * 0.5;
        }

        if (h < 0) {
            h = 0;
        }
        if (s < 0) {
            s = 0;
        }
        if (br < 0) {
            br = 0;
        }

        h = h % 1.0;
        if (s > 1) {
            s = 1;
        }
        if (br > 1) {
            br = 1;
        }

        int[] rgb = ColorSpaceConverter.HSBtoRGB(h, s, br);

        return 0xFF000000 | (rgb[2] << 16) | (rgb[1] << 8) | rgb[0]; //switched blue with red

    }

}
