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
package fractalzoomer.parser.functions;

import fractalzoomer.core.Complex;

/**
 *
 * @author hrkalona2
 */
public class PowFunction extends AbstractTwoArgumentFunction {

    public PowFunction() {

        super();

    }

    @Override
    public Complex evaluate(Complex argument, Complex argument2) {

        double re = argument2.getRe();
        double im = argument2.getIm();

        if(im == 0) {
            if(re == 2) {
                return argument.square();
            }
            else if(re == 3) {
                return argument.cube();
            }
            else if(re == 4) {
                return argument.fourth();
            }
            else if(re == 5) {
                return argument.fifth();
            }
            else if(re == 6) {
                return argument.sixth();
            }
            else if(re == 7) {
                return argument.seventh();
            }
            else if(re == 8) {
                return argument.eighth();
            }
            else if(re == 9) {
                return argument.ninth();
            }
            else if(re == 10) {
                return argument.tenth();
            }
            return argument.pow(re);
        }
        else {
            return argument.pow(argument2);
        }

    }

}
