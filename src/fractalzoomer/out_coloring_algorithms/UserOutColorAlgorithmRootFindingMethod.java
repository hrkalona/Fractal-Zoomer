

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmRootFindingMethod extends OutColorAlgorithm {
    protected OutColorAlgorithm escape_time_alg;
    private ExpressionNode expr;
    private Parser parser;
    protected int max_iterations;
    private Complex[] globalVars;
    
    public UserOutColorAlgorithmRootFindingMethod(String outcoloring_formula, double convergent_bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars, OutColorAlgorithm escape_time_alg) {
        
        super();
        
        this.globalVars = globalVars;
        
        this.max_iterations = max_iterations;

        this.escape_time_alg = escape_time_alg;
        
        parser = new Parser();
        expr = parser.parse(outcoloring_formula);
                   
        if(parser.foundCbail()) {
            parser.setCbailvalue(new Complex(convergent_bailout, 0));
        }
        
        if(parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
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
        
        OutUsingIncrement = false;
        
    }

    @Override
    public double getResult(OutColorData data) {
        
        if(parser.foundN()) {
            parser.setNvalue(new Complex(data.iterations, 0));
        }

        if(parser.foundNF()) {
            parser.setNFvalue(new Complex(escape_time_alg.getFractionalPart(data), 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(data.z);
        }
        
        if(parser.foundC()) {
            parser.setCvalue(data.c);
        }
        
        if(parser.foundS()) {
            parser.setSvalue(data.start);
        }

        if(parser.foundC0()) {
            parser.setC0value(data.c0);
        }

        if (parser.foundPixel()) {
            parser.setPixelvalue(data.pixel);
        }

        if(parser.foundP()) {
            parser.setPvalue(data.zold);
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(data.zold2);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }
        
        double result = expr.getValue().getRe();
        
        if (TaskRender.USE_DIRECT_COLOR) {
            return result;
        }
        
        if(Math.abs(result) == max_iterations) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
        }

        if(Math.abs(result) == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            return result < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS_DE : ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
        }
        
        return result; 
        
    }
    
}
