
package fractalzoomer.fractal_options.initial_value;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskRender;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class VariableInitialValue extends PlanePointOption {

    private ExpressionNode expr;
    private Parser parser;
    private Complex[] globalVars;

    public VariableInitialValue(String initial_value_user_formula, double xCenter, double yCenter, double size, int max_iterations, double[] point, Complex[] globalVars) {

        super();
        
        this.globalVars = globalVars;

        parser = new Parser();
        expr = parser.parse(initial_value_user_formula);

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

    }

    @Override
    public Complex getValue(Complex pixel) {

        if (parser.foundC()) {
            parser.setCvalue(pixel);
        }

        if(parser.foundAnyVar()) {
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser.foundVar(i)) {
                    parser.setVarsvalue(i, globalVars[i]);
                }
            }
        }

        return expr.getValue();

    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return MantExpComplex.create(getValue(pixel.toComplex()));
    }

    @Override
    public String toString() {

        return "";

    }

    @Override
    public boolean isStatic() {return !parser.foundC();}

}
