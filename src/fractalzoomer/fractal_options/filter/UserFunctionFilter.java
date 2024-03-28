package fractalzoomer.fractal_options.filter;

import fractalzoomer.core.Complex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

public class UserFunctionFilter extends FunctionFilter {
    private ExpressionNode expr;
    private Parser parser;
    private Complex[] globalVars;

    public UserFunctionFilter(String functionFilter, int max_iterations, double xCenter, double yCenter, double size, double[] point, Complex[] globalVars) {
        super();

        parser = new Parser();
        expr = parser.parse(functionFilter);

        if (parser.foundMaxn()) {
            parser.setMaxnvalue(new Complex(max_iterations, 0));
        }

        if (parser.foundCenter()) {
            parser.setCentervalue(new Complex(xCenter, yCenter));
        }

        if (parser.foundSize()) {
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

        if (parser.foundPoint()) {
            parser.setPointvalue(new Complex(point[0], point[1]));
        }

        this.globalVars = globalVars;
    }

    @Override
    public Complex getValue(Complex z, int iterations, Complex c, Complex start, Complex c0, Complex pixel) {

        if (parser.foundN()) {
            parser.setNvalue(new Complex(iterations, 0));
        }

        if (parser.foundZ()) {
            parser.setZvalue(z);
        }

        if (parser.foundC()) {
            parser.setCvalue(c);
        }

        if (parser.foundS()) {
            parser.setSvalue(start);
        }

        if (parser.foundPixel()) {
            parser.setPixelvalue(pixel);
        }

        if (parser.foundC0()) {
            parser.setC0value(c0);
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
            }
        }

        return expr.getValue();

    }
}
