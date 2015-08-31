/* 
 * Fractal Zoomer, Copyright (C) 2015 hrkalona2
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

/**
 *
 * @author hrkalona2
 */
public class UserConditionalOutColorAlgorithmMagnet extends UserConditionalOutColorAlgorithm {
    
    public UserConditionalOutColorAlgorithmMagnet(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double bailout) {

        super(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout);
        
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

        if(parser[0].foundP()) {
            parser[0].setPvalue(((Complex)object[4]));
        }

        if(parser[0].foundBail()) {
            parser[0].setBailvalue(c_bailout);
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex((Integer)object[0], 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(((Complex)object[1]));
        }

        if(parser[1].foundP()) {
            parser[1].setPvalue(((Complex)object[4]));
        }

        if(parser[1].foundBail()) {
            parser[1].setBailvalue(c_bailout);
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundN()) {
                parser2[0].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(((Complex)object[1]));
            }

            if(parser2[0].foundP()) {
                parser2[0].setPvalue(((Complex)object[4]));
            }

            if(parser2[0].foundBail()) {
                parser2[0].setBailvalue(c_bailout);
            }

            double temp = expr2[0].getValue().getAbsRe();
            
            return (Boolean)object[2] ? temp + 100906  : temp + 100800;
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundN()) {
                parser2[1].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(((Complex)object[1]));
            }

            if(parser2[1].foundP()) {
                parser2[1].setPvalue(((Complex)object[4]));
            }

            if(parser2[1].foundBail()) {
                parser2[1].setBailvalue(c_bailout);
            }

            double temp = expr2[1].getValue().getAbsRe();
            
            return (Boolean)object[2] ? temp + 100906  : temp + 100800;
        }
        else if (result == 0) { //left == right
            if(parser2[2].foundN()) {
                parser2[2].setNvalue(new Complex((Integer)object[0], 0));
            }

            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(((Complex)object[1]));
            }

            if(parser2[2].foundP()) {
                parser2[2].setPvalue(((Complex)object[4]));
            }

            if(parser2[2].foundBail()) {
                parser2[2].setBailvalue(c_bailout);
            }

            double temp = expr2[2].getValue().getAbsRe();
            
            return (Boolean)object[2] ? temp + 100906  : temp + 100800;
        }
        
        return 0;

    }

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }

}
