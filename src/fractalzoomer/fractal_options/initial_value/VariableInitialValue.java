/* 
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser.foundVar(i)) {
                parser.setVarsvalue(i, globalVars[i]);
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
