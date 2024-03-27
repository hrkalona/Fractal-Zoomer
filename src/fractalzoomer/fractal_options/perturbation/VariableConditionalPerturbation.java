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
package fractalzoomer.fractal_options.perturbation;

import fractalzoomer.core.Complex;
import fractalzoomer.core.MantExpComplex;
import fractalzoomer.core.TaskDraw;
import fractalzoomer.fractal_options.PlanePointOption;
import fractalzoomer.parser.ExpressionNode;
import fractalzoomer.parser.Parser;

/**
 *
 * @author hrkalona2
 */
public class VariableConditionalPerturbation extends PlanePointOption {
    private ExpressionNode[] expr;
    private Parser[] parser;
    private ExpressionNode[] expr2;
    private Parser[] parser2;
    private Complex[] globalVars;
    
    public VariableConditionalPerturbation(String[] user_perturbation_conditions, String[] user_perturbation_condition_formula, double xCenter, double yCenter, double size, int max_iterations, double[] point, Complex[] globalVars) {
        
        super();
        
        this.globalVars = globalVars;
        
        parser = new Parser[user_perturbation_conditions.length];
        expr = new ExpressionNode[user_perturbation_conditions.length];

        for(int i = 0; i < parser.length; i++) {
            parser[i] = new Parser();
            expr[i] = parser[i].parse(user_perturbation_conditions[i]);
        }

        parser2 = new Parser[user_perturbation_condition_formula.length];
        expr2 = new ExpressionNode[user_perturbation_condition_formula.length];

        for(int i = 0; i < parser2.length; i++) {
            parser2[i] = new Parser();
            expr2[i] = parser2[i].parse(user_perturbation_condition_formula[i]);
        }
        
        Complex c_max_iterations = new Complex(max_iterations, 0);
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

        Complex c_center = new Complex(xCenter, yCenter);
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
        
        Complex c_size = new Complex(size, 0);
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
        
        Complex c_isize = new Complex(TaskDraw.IMAGE_SIZE, 0);
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
        
    }
    
    @Override
    public Complex getValue(Complex pixel) {
        
        /* LEFT */
        if(parser[0].foundC()) {
            parser[0].setCvalue(pixel);
        }

        /* RIGHT */
        if(parser[1].foundC()) {
            parser[1].setCvalue(pixel);
        }
        
        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser[0].foundVar(i)) {
                parser[0].setVarsvalue(i, globalVars[i]);
            }
        }

        for (int i = 0; i < Parser.EXTRA_VARS; i++) {
            if (parser[1].foundVar(i)) {
                parser[1].setVarsvalue(i, globalVars[i]);
            }
        }

        int result = expr[0].getValue().compare(expr[1].getValue());

        if(result == -1) { // left > right
            if(parser2[0].foundC()) {
                parser2[0].setCvalue(pixel);
            }
            
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[0].foundVar(i)) {
                    parser2[0].setVarsvalue(i, globalVars[i]);
                }
            }
            
            return pixel.plus(expr2[0].getValue());
        }
        else if(result == 1) { // right > left
            if(parser2[1].foundC()) {
                parser2[1].setCvalue(pixel);
            }
            
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[1].foundVar(i)) {
                    parser2[1].setVarsvalue(i, globalVars[i]);
                }
            }
            
            return pixel.plus(expr2[1].getValue());
        }
        else if (result == 0) { //left == right
            if(parser2[2].foundC()) {
                parser2[2].setCvalue(pixel);
            }
            
            for (int i = 0; i < Parser.EXTRA_VARS; i++) {
                if (parser2[2].foundVar(i)) {
                    parser2[2].setVarsvalue(i, globalVars[i]);
                }
            }

            return pixel.plus(expr2[2].getValue());
        }
        
        return pixel;

    }

    @Override
    public MantExpComplex getValueDeep(MantExpComplex pixel) {
        return MantExpComplex.create(getValue(pixel.toComplex()));
    }
    
}
