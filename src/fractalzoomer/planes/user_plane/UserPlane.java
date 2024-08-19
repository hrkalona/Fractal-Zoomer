

package fractalzoomer.planes.user_plane;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
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
    private Complex[] globalVars;

    public UserPlane(String user_plane, double xCenter, double yCenter, double size, int max_iterations, double[] point, Complex[] globalVars) {

        super();

        usesCenter = false;
        
        this.globalVars = globalVars;
        
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
            parser.setISizevalue(new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0));
        }

        if (parser.foundWidth()) {
            parser.setWidthvalue(new Complex(TaskRender.WIDTH, 0));
        }

        if (parser.foundHeight()) {
            parser.setHeightvalue(new Complex(TaskRender.HEIGHT, 0));
        }
        
        if(parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }

    }

    @Override
    public Complex transform_internal(Complex pixel) {

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
