/* 
 * Fractal Zoomer, Copyright (C) 2017 hrkalona2
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

package fractalzoomer.planes.user_plane;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class UserPlaneConditional extends Plane {
    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    
    public UserPlaneConditional(String[] user_plane_conditions, String[] user_plane_condition_formula) {
        
        super();
        
        parser = new Parser[user_plane_conditions.length];
        expr = new ExpressionNode[user_plane_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_plane_conditions[i]);
        }

        parser2 = new Parser[user_plane_condition_formula.length];
        expr2 = new ExpressionNode[user_plane_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_plane_condition_formula[i]);
        }
        
    }
    
    @Override
    public Complex getPixel(Complex pixel) {
        
        /* LEFT */
        if(parser[0].foundZ()) {
            parser[0].setZvalue(pixel);
        }

        /* RIGHT */
        if(parser[1].foundZ()) {
            parser[1].setZvalue(pixel);
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundZ()) {
                parser2[0].setZvalue(pixel);
            }
            
            return expr2[0].getValue();
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundZ()) {
                parser2[1].setZvalue(pixel);
            }
            
            return expr2[1].getValue();
        }
        else if (result == 0) { //left == right
            if(parser2[2].foundZ()) {
                parser2[2].setZvalue(pixel);
            }
            
            return expr2[2].getValue();
        }
        
        return pixel;
    }
    
}
