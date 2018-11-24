/* 
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.utils.ColorAlgorithm;

public abstract class OutColorAlgorithm extends ColorAlgorithm {
    public abstract double getResult(Object[] object);
    
    public double getResult3D(Object[] object) {
        
        return  getResult(object);
        
    }
    
    public static double fractionalPartEscaping(Complex z, Complex zold, double log_bailout_squared) {
        double temp = zold.norm_squared();
        double temp2 = z.norm_squared();
            
        temp2 = Math.log(temp2);
        double p = temp2 / Math.log(temp);
           
        p = p <= 0 ? 1e-33 : p;
        temp2 = temp2 <= 0 ? 1e-33 : temp2;
            
        double a = Math.log(temp2 / log_bailout_squared);
        double f =  a / Math.log(p);
            
        return f;
    }
    
    public static double fractionalPartConverging(Complex z, Complex zold, Complex zold2, double log_convergent_bailout) {
    
        double temp4 = Math.log(z.distance_squared(zold) + 1e-33);

        double power = temp4 / Math.log(zold.distance_squared(zold2));

        power = power <= 0 ? 1e-33 : power;
            
        double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

        return 1 - f;
        
    }
    
    public static double fractionalPartMagnetConverging(Complex z, Complex zold, Complex root, double log_convergent_bailout) {
    
        double temp4 = Math.log(z.distance_squared(root));

        double power = temp4 / Math.log(zold.distance_squared(root));

        double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

        return 1 - f;
        
    }
 
}
