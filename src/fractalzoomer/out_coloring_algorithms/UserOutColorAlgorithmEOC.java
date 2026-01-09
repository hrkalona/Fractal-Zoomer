

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.Parser;
import fractalzoomer.utils.ColorAlgorithm;
import fractalzoomer.utils.OutColorData;

/**
 *
 * @author hrkalona2
 */
public class UserOutColorAlgorithmEOC extends  UserOutColorAlgorithm {

    public UserOutColorAlgorithmEOC(String outcoloring_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars, OutColorAlgorithm escape_time_alg) {

        super(outcoloring_formula, bailout, max_iterations, xCenter, yCenter, size, point, globalVars, escape_time_alg);

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
   
        if(result < 0) {
            return data.escaped ? result - MAGNET_INCREMENT  : result;
        }
        else {
            return data.escaped ? result + MAGNET_INCREMENT  : result;
        }
    }

}
