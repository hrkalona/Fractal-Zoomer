
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
public class UserConditionalOutColorAlgorithm extends OutColorAlgorithm {

    protected OutColorAlgorithm escape_time_alg;
    protected ExpressionNode[] expr;
    protected Parser[] parser;
    protected ExpressionNode[] expr2;
    protected Parser[] parser2;
    protected Complex c_bailout;
    protected Complex c_max_iterations;
    private Complex c_center;
    private Complex c_size;
    protected int max_iterations;
    protected Complex[] globalVars;

    public UserConditionalOutColorAlgorithm(String[] user_outcoloring_conditions, String[] user_outcoloring_condition_formula, double bailout, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars, OutColorAlgorithm escape_time_alg) {

        super();

        this.escape_time_alg = escape_time_alg;
        this.globalVars = globalVars;
        
        this.max_iterations = max_iterations;
        
        parser = new Parser[user_outcoloring_conditions.length];
        expr = new ExpressionNode[user_outcoloring_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_outcoloring_conditions[i]);
        }

        parser2 = new Parser[user_outcoloring_condition_formula.length];
        expr2 = new ExpressionNode[user_outcoloring_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_outcoloring_condition_formula[i]);
        }

        c_bailout = new Complex(bailout, 0);

        if(parser[0].foundBail()) {
            parser[0].setBailvalue(c_bailout);
        }

        if(parser[1].foundBail()) {
            parser[1].setBailvalue(c_bailout);
        }

        if(parser2[0].foundBail()) {
            parser2[0].setBailvalue(c_bailout);
        }

        if(parser2[1].foundBail()) {
            parser2[1].setBailvalue(c_bailout);
        }

        if(parser2[2].foundBail()) {
            parser2[2].setBailvalue(c_bailout);
        }

        c_max_iterations = new Complex(max_iterations, 0);

        if(parser[0].foundMaxn()) {
            parser[0].setMaxnvalue(c_max_iterations);
        }

        if(parser[1].foundMaxn()) {
            parser[1].setMaxnvalue(c_max_iterations);
        }

        if(parser2[0].foundMaxn()) {
            parser2[0].setMaxnvalue(c_max_iterations);
        }

        if(parser2[1].foundMaxn()) {
            parser2[1].setMaxnvalue(c_max_iterations);
        }

        if(parser2[2].foundMaxn()) {
            parser2[2].setMaxnvalue(c_max_iterations);
        }

        c_center = new Complex(xCenter, yCenter);

        if(parser[0].foundCenter()) {
            parser[0].setCentervalue(c_center);
        }

        if(parser[1].foundCenter()) {
            parser[1].setCentervalue(c_center);
        }

        if(parser2[0].foundCenter()) {
            parser2[0].setCentervalue(c_center);
        }

        if(parser2[1].foundCenter()) {
            parser2[1].setCentervalue(c_center);
        }

        if(parser2[2].foundCenter()) {
            parser2[2].setCentervalue(c_center);
        }

        c_size = new Complex(size, 0);

        if(parser[0].foundSize()) {
            parser[0].setSizevalue(c_size);
        }

        if(parser[1].foundSize()) {
            parser[1].setSizevalue(c_size);
        }

        if(parser2[0].foundSize()) {
            parser2[0].setSizevalue(c_size);
        }

        if(parser2[1].foundSize()) {
            parser2[1].setSizevalue(c_size);
        }

        if(parser2[2].foundSize()) {
            parser2[2].setSizevalue(c_size);
        }
        
        Complex c_isize = new Complex(Math.min(TaskRender.WIDTH, TaskRender.HEIGHT), 0);
        if (parser[0].foundISize()) {
            parser[0].setISizevalue(c_isize);
        }

        if (parser[1].foundISize()) {
            parser[1].setISizevalue(c_isize);
        }

        if (parser2[0].foundISize()) {
            parser2[0].setISizevalue(c_isize);
        }

        if (parser2[1].foundISize()) {
            parser2[1].setISizevalue(c_isize);
        }

        if (parser2[2].foundISize()) {
            parser2[2].setISizevalue(c_isize);
        }

        Complex c_width = new Complex(TaskRender.WIDTH, 0);

        if (parser[0].foundWidth()) {
            parser[0].setWidthvalue(c_width);
        }

        if (parser[1].foundWidth()) {
            parser[1].setWidthvalue(c_width);
        }

        if (parser2[0].foundWidth()) {
            parser2[0].setWidthvalue(c_width);
        }

        if (parser2[1].foundWidth()) {
            parser2[1].setWidthvalue(c_width);
        }

        if (parser2[2].foundWidth()) {
            parser2[2].setWidthvalue(c_width);
        }

        Complex c_height = new Complex(TaskRender.HEIGHT, 0);

        if (parser[0].foundHeight()) {
            parser[0].setHeightvalue(c_height);
        }

        if (parser[1].foundHeight()) {
            parser[1].setHeightvalue(c_height);
        }

        if (parser2[0].foundHeight()) {
            parser2[0].setHeightvalue(c_height);
        }

        if (parser2[1].foundHeight()) {
            parser2[1].setHeightvalue(c_height);
        }

        if (parser2[2].foundHeight()) {
            parser2[2].setHeightvalue(c_height);
        }
        
        Complex c_point = new Complex(point[0], point[1]);
        if(parser[0].foundPoint()) {
            parser[0].setPointvalue(c_point);
        }
        
        if(parser[1].foundPoint()) {
            parser[1].setPointvalue(c_point);
        }
        
        if(parser2[0].foundPoint()) {
            parser2[0].setPointvalue(c_point);
        }
        
        if(parser2[1].foundPoint()) {
            parser2[1].setPointvalue(c_point);
        }
        
        if(parser2[2].foundPoint()) {
            parser2[2].setPointvalue(c_point);
        }
        
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

        if (parser[0].foundC0()) {
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

        if (parser[1].foundC0()) {
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

        if (resultParser.foundC0()) {
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

        return result2;

    }

}
