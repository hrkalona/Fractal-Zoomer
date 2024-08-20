

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmMagnet extends  UserOutColorAlgorithm {

    public UserOutColorAlgorithmMagnet(String outcoloring_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars) {

        super(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, point, globalVars);
        
        this.max_iterations = max_iterations;
        OutUsingIncrement = false;

    }

    @Override
    public double getResult(Object[] object) {

        if(parser.foundN()) {
            parser.setNvalue(new Complex((int)object[0], 0));
        }
        
        if(parser.foundZ()) {
            parser.setZvalue(((Complex)object[1]));
        }
        
        if(parser.foundC()) {
            parser.setCvalue(((Complex)object[5]));
        }
        
        if(parser.foundS()) {
            parser.setSvalue(((Complex)object[6]));
        }

        if(parser.foundC0()) {
            parser.setC0value(((Complex)object[7]));
        }

        if (parser.foundPixel()) {
            parser.setPixelvalue(((Complex) object[8]));
        }

        if(parser.foundP()) {
            parser.setPvalue(((Complex)object[3]));
        }
        
        if(parser.foundPP()) {
            parser.setPPvalue(((Complex)object[4]));
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
   
        if(result < 0) {
            return (boolean)object[2] ? result - MAGNET_INCREMENT  : result;
        }
        else {
            return (boolean)object[2] ? result + MAGNET_INCREMENT  : result;
        }
    }

}
