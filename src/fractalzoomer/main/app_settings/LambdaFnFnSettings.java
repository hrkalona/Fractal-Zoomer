/*
 * Copyright (C) 2020 hrkalona2
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
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class LambdaFnFnSettings {
    public String[] lambda_formula_conditions;
    public String[] lambda_formula_condition_formula;
    
    public LambdaFnFnSettings() {
        
        lambda_formula_conditions = new String[2];
        lambda_formula_conditions[0] = "norm(z)";
        lambda_formula_conditions[1] = "1.0";

        lambda_formula_condition_formula = new String[3];
        lambda_formula_condition_formula[0] = "z^2";
        lambda_formula_condition_formula[1] = "sin(z)";
        lambda_formula_condition_formula[2] = "z^2";
        
    }
    
}
