
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
public class UserConditionalOutColorAlgorithmEOC extends UserConditionalOutColorAlgorithm {


    public UserConditionalOutColorAlgorithmEOC(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars, OutColorAlgorithm escape_time_alg) {

        super(user_outcoloring_conditions, user_outcoloring_condition_formula, bailout, max_iterations, xCenter, yCenter, size, point, globalVars, escape_time_alg);

        OutUsingIncrement = false;
        
    }

    @Override
    public double getResult(OutColorData data) {

        double nf = 0;
        if(parser[0].foundNF() || parser[1].foundNF() || parser2[0].foundNF() || parser2[1].foundNF() || parser2[2].foundNF()) {
            nf = escape_time_alg.getFractionalPart(data);
        }

        /* LEFT */
        if(parser[0].foundN()) {
            parser[0].setNvalue(new Complex(data.iterations, 0));
        }

        if(parser[0].foundNF()) {
            parser[0].setNFvalue(new Complex(nf, 0));
        }

        if(parser[0].foundZ()) {
            parser[0].setZvalue(data.z);
        }

        if(parser[0].foundC()) {
            parser[0].setCvalue(data.c);
        }

        if(parser[0].foundS()) {
            parser[0].setSvalue(data.start);
        }

        if(parser[0].foundC0()) {
            parser[0].setC0value(data.c0);
        }

        if (parser[0].foundPixel()) {
            parser[0].setPixelvalue(data.pixel);
        }

        if(parser[0].foundP()) {
            parser[0].setPvalue(data.zold);
        }

        if(parser[0].foundPP()) {
            parser[0].setPPvalue(data.zold2);
        }

        if(parser[0].foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser[0].foundVar(i)) {
                    parser[0].setVarsvalue(i, globalVars[i]);
                }
            }
        }

        /* RIGHT */
        if(parser[1].foundN()) {
            parser[1].setNvalue(new Complex(data.iterations, 0));
        }

        if(parser[1].foundNF()) {
            parser[1].setNFvalue(new Complex(nf, 0));
        }

        if(parser[1].foundZ()) {
            parser[1].setZvalue(data.z);
        }

        if(parser[1].foundC()) {
            parser[1].setCvalue(data.c);
        }

        if(parser[1].foundS()) {
            parser[1].setSvalue(data.start);
        }

        if(parser[1].foundC0()) {
            parser[1].setC0value(data.c0);
        }

        if (parser[1].foundPixel()) {
            parser[1].setPixelvalue(data.pixel);
        }


        if(parser[1].foundP()) {
            parser[1].setPvalue(data.zold);
        }

        if(parser[1].foundPP()) {
            parser[1].setPPvalue(data.zold2);
        }

        if(parser[1].foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser[1].foundVar(i)) {
                    parser[1].setVarsvalue(i, globalVars[i]);
                }
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        ExpressionNode resultExpr;
        Parser resultParser;
        if (result == -1) { // left > right
            resultParser = parser2[0];
            resultExpr = expr2[0];
        } else if (result == 1) { // right > left
            resultParser = parser2[1];
            resultExpr = expr2[1];
        } else { // right == left
            resultParser = parser2[2];
            resultExpr = expr2[2];
        }

        if(resultParser.foundN()) {
            resultParser.setNvalue(new Complex(data.iterations, 0));
        }

        if(resultParser.foundNF()) {
            resultParser.setNFvalue(new Complex(nf, 0));
        }

        if(resultParser.foundZ()) {
            resultParser.setZvalue(data.z);
        }

        if(resultParser.foundC()) {
            resultParser.setCvalue(data.c);
        }

        if(resultParser.foundS()) {
            resultParser.setSvalue(data.start);
        }

        if(resultParser.foundC0()) {
            resultParser.setC0value(data.c0);
        }

        if (resultParser.foundPixel()) {
            resultParser.setPixelvalue(data.pixel);
        }


        if(resultParser.foundP()) {
            resultParser.setPvalue(data.zold);
        }

        if(resultParser.foundPP()) {
            resultParser.setPPvalue(data.zold2);
        }

        if(resultParser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (resultParser.foundVar(i)) {
                    resultParser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        double result2 = resultExpr.getValue().getRe();

        if(TaskRender.USE_DIRECT_COLOR) {
            return result2;
        }

        if(Math.abs(result2) == max_iterations) {
            return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS : ColorAlgorithm.MAXIMUM_ITERATIONS;
        }

        if(Math.abs(result2) == ColorAlgorithm.MAXIMUM_ITERATIONS_DE) {
            return result2 < 0 ? -ColorAlgorithm.MAXIMUM_ITERATIONS_DE : ColorAlgorithm.MAXIMUM_ITERATIONS_DE;
        }

        if(result2 < 0) {
            return data.escaped ? result2 - MAGNET_INCREMENT  : result2;
        }
        else {
            return data.escaped ? result2 + MAGNET_INCREMENT  : result2;
        }

    }

}
