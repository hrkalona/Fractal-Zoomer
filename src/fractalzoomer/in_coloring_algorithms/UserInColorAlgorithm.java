

package fractalzoomer.in_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserInColorAlgorithm extends InColorAlgorithm {
    private ExpressionNode expr;
    private Parser parser;
    private int max_iterations;
    private Complex[] globalVars;
    
    public UserInColorAlgorithm(String incoloring_formula, int max_iterations, double xCenter, double yCenter, double size, double[] point, double genericBail, Complex[] globalVars) {
        
        super();
        
        this.globalVars = globalVars;
        
        parser = new Parser();
        expr = parser.parse(incoloring_formula); 
                
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundN()) {
            parser.setNvalue(new Complex(max_iterations, 0));
        }
        
        if(parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
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
        
        if(parser.foundBail()) {
            parser.setBailvalue(new Complex(genericBail, 0));
        }
        
        if(parser.foundCbail()) {
            parser.setCbailvalue(new Complex(genericBail, 0));
        }
        
        this.max_iterations = max_iterations;
        
        InUsingIncrement = false;
        
    }

    @Override
    public double getResult(Object[] object) {

        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[0]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[3]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[4]));
        }

        if(parser.foundC0()) {
            parser.setC0value(((Complex)object[5]));
        }

        if(parser.foundPixel()) {
            parser.setPixelvalue(((Complex)object[6]));
        }
        
        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[1]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[2]));
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }
        
        double result = expr.getValue().getRe();
        
        if(TaskRender.USE_DIRECT_COLOR) {
            return result;
        }
 
        if(Math.abs(result) == max_iterations) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
        }

        if(Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS_DE : ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
        }

        return result < 0 ? result - max_iterations : result + max_iterations; 
        
    }
    
}
