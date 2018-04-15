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
package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserConditionalOutColorAlgorithmMagnet extends UserConditionalOutColorAlgorithm {

    protected int max_iterations;
    
    public UserConditionalOutColorAlgorithmMagnet(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point) {

        super(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, point);
        this.max_iterations = max_iterations;
        OutNotUsingIncrement = true;
        
    }

    @Override
    public double getResult(Object[] object) {

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(((Complex)object[1]));
        }

        if(parser[0].foundC()) {
            parser[0].setCvalue(((Complex)object[6]));
        }

        if(parser[0].foundS()) {
            parser[0].setSvalue(((Complex)object[7]));
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(((Complex)object[4]));
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(((Complex)object[5]));
        }

        Complex[] vars = (Complex[])object[8];
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, vars[i]);
            }
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[1]));
        }

        if(parser[1].foundC()) {
            parser[1].setCvalue(((Complex)object[6]));
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(((Complex)object[7]));
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(((Complex)object[4]));
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(((Complex)object[5]));
        }

        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, vars[i]);
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[1]));
            }

            if(parser2[0].foundC()) {
                parser2[0].setCvalue(((Complex)object[6]));
            }

            if(parser2[0].foundS()) {
                parser2[0].setSvalue(((Complex)object[7]));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(((Complex)object[4]));
            }

            if(parser2[0].foundPP()) {
                parser2[0].setPPvalue(((Complex)object[5]));
            }

            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[0].foundVar(i)) {
                    parser2[0].setVarsvalue(i, vars[i]);
                }
            }
            
            double result2 = expr2[0].getValue().getRe();
        
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if(result2 < 0) {
                return (Boolean)object[2] ? result2 - MAGNET_INCREMENT  : result2;
            }
            else {
                return (Boolean)object[2] ? result2 + MAGNET_INCREMENT  : result2;
            }
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[1]));
            }

            if(parser2[1].foundC()) {
                parser2[1].setCvalue(((Complex)object[6]));
            }

            if(parser2[1].foundS()) {
                parser2[1].setSvalue(((Complex)object[7]));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(((Complex)object[4]));
            }

            if(parser2[1].foundPP()) {
                parser2[1].setPPvalue(((Complex)object[5]));
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[1].foundVar(i)) {
                    parser2[1].setVarsvalue(i, vars[i]);
                }
            }

            double result2 = expr2[1].getValue().getRe();
        
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if(result2 < 0) {
                return (Boolean)object[2] ? result2 - MAGNET_INCREMENT  : result2;
            }
            else {
                return (Boolean)object[2] ? result2 + MAGNET_INCREMENT  : result2;
            }
        }
        else if(result == 0) { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[1]));
            }

            if(parser2[2].foundC()) {
                parser2[2].setCvalue(((Complex)object[6]));
            }

            if(parser2[2].foundS()) {
                parser2[2].setSvalue(((Complex)object[7]));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(((Complex)object[4]));
            }

            if(parser2[2].foundPP()) {
                parser2[2].setPPvalue(((Complex)object[5]));
            }
            
            for(int i = 0; i < Parser.EXTRA_VARS; i++) {
                if(parser2[2].foundVar(i)) {
                    parser2[2].setVarsvalue(i, vars[i]);
                }
            }

            double result2 = expr2[2].getValue().getRe();
        
            if(Math.abs(result2) == max_iterations) {
                return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
            }

            if(result2 < 0) {
                return (Boolean)object[2] ? result2 - MAGNET_INCREMENT  : result2;
            }
            else {
                return (Boolean)object[2] ? result2 + MAGNET_INCREMENT  : result2;
            }
        }

        return 0;

    }

}
