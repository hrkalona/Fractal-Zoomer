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

package fractalzoomer.planes.user_plane;

import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class UserPlane extends Plane {

    private ExpressionNode expr;
    private Parser parser;
    private boolean usesCenter;

    public UserPlane(String user_plane, double xCenter, double yCenter, double size, int max_iterations, double[] point) {

        super();

        usesCenter = false;
        
        parser = new Parser();
        expr = parser.parse(user_plane);
        
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
            usesCenter = true;
        }
        
        if(parser.foundSize()) {
            parser.setSizevalue(new Complex(size, 0));
        }

        if (parser.foundISize()) {
            parser.setISizevalue(new Complex(ThreadDraw.IMAGE_SIZE, 0));
        } 
        
        if(parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }

    }

    @Override
    public Complex transform(Complex pixel) {

        //expr.accept(new SetVariable("z", pixel));
        //expr.accept(new SetVariable("z", complex[0]));
        if(parser.foundZ()) {
            parser.setZvalue(pixel);
        }
        
        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }
        
        return expr.getValue();

    }
    
    public boolean usesCenter() {
        
        return usesCenter;
        
    }

}
