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

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmMagnet extends  UserOutColorAlgorithm {
    protected int max_iterations;
    
    public UserOutColorAlgorithmMagnet(String outcoloring_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point) {

        super(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, point);
        
        this.max_iterations = max_iterations;
        OutNotUsingIncrement = true;

    }

    @Override
    public double getResult(Object[] object) {

        if(parser.foundN()) {
            parser.setNvalue(new Complex((Integer)object[0], 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[1]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[6]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[7]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[4]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[5]));
        }
        
        Complex[] vars = (Complex[])object[8];
        for(int i = 0; i < Parser.EXTRA_VARS; i++) {
            if(parser.foundVar(i)) {
                parser.setVarsvalue(i, vars[i]);
            }
        }
        
        double result = expr.getValue().getRe();
        
        if(Math.abs(result) == max_iterations) {
            return result;
        }
   
        if(result < 0) {
            return (Boolean)object[2] ? result - MAGIC_OFFSET_NUMBER - MAGNET_INCREMENT  : result - MAGIC_OFFSET_NUMBER;
        }
        else {
            return (Boolean)object[2] ? result + MAGIC_OFFSET_NUMBER + MAGNET_INCREMENT  : result + MAGIC_OFFSET_NUMBER;
        }
    }

}
