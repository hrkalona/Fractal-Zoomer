/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes;

import fractalzoomer.core.Complex;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class UserPlane extends Plane {
  private ExpressionNode expr;
  private Parser parser;
    
    public UserPlane(String user_plane) {
        
        super();
        
        parser = new Parser();
        expr = parser.parse(user_plane);  
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        //expr.accept(new SetVariable("z", pixel));
        //expr.accept(new SetVariable("z", complex[0]));
        parser.setZvalue(pixel);
        return expr.getValue();
 
    }
    
}
