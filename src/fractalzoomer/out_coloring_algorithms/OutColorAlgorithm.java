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

package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;
import fractalzoomer.core.norms.Norm;
import fractalzoomer.utils.ColorAlgorithm;

public abstract class OutColorAlgorithm extends ColorAlgorithm {
    protected boolean smooth = false;
    public abstract double getResult(Object[] object);
    
    public double getResult3D(Object[] object, double result) {
        
        return result;
        
    }

    public void setNormImpl(Norm normImpl) {

    }
    
    public static double fractionalPartEscaping2(Complex z, Complex zold, double log_bailout, Norm normImpl) {
        double temp = normImpl.computeWithoutRoot(zold);
        double temp2 = normImpl.computeWithoutRoot(z);
            
        temp2 = Math.log(temp2);
        double p = temp2 / Math.log(temp);
           
        p = p <= 0 ? 1e-33 : p;
        temp2 = temp2 <= 0 ? 1e-33 : temp2;
            
        double a = Math.log(temp2 / log_bailout);
        return  a / Math.log(p);

    }

    public static double fractionalPartEscaping1(Complex z, Complex zold, double log_bailout, Norm normImpl) {
        double temp = normImpl.computeWithoutRoot(zold);

        if(temp == 0) {
            temp += 0.000000001;
        }
        temp = Math.log(temp);

        return 1.0 - (log_bailout - temp) / (Math.log(normImpl.computeWithoutRoot(z)) - temp);

    }

    public static double fractionalPartEscaping3(Complex z, Complex zold, double bailout, Norm normImpl) {

        double test1 = normImpl.computeWithRoot(z);
        double test2 = normImpl.computeWithRoot(zold);

        return (test1 - bailout) / (test1 - test2);

    }

    public static double fractionalPartEscapingWithPower(Complex z, double log_bailout, double log_power, Norm normImpl) {
        double temp2 = normImpl.computeWithoutRoot(z);
        temp2 = Math.log(temp2);

        temp2 = temp2 <= 0 ? 1e-33 : temp2;

        double a = Math.log(temp2 / log_bailout);
        return  a / log_power;
    }
    
    public static double fractionalPartConverging2(Complex z, Complex zold, Complex zold2, double log_convergent_bailout, Norm cNormImpl) {
    
        double temp4 = Math.log(cNormImpl.computeWithoutRoot(z.sub(zold)) + 1e-33);

        double power = temp4 / Math.log(cNormImpl.computeWithoutRoot(zold.sub(zold2)));

        power = power <= 0 ? 1e-33 : power;
            
        double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

        return 1 - f;
        
    }

    public static double fractionalPartConverging1(Complex z, Complex zold, Complex zold2, double log_convergent_bailout, Norm cNormImpl) {


        double temp = Math.log(cNormImpl.computeWithoutRoot(zold.sub(zold2)));
        return 1 - (log_convergent_bailout - temp) / (Math.log(cNormImpl.computeWithoutRoot(z.sub(zold))) - temp);

    }
    
    public static double fractionalPartMagnetConverging2(Complex z, Complex zold, Complex root, double log_convergent_bailout, Norm cNormImpl) {
    
        double temp4 = Math.log(cNormImpl.computeWithoutRoot(z.sub(root)));

        double power = temp4 / Math.log(cNormImpl.computeWithoutRoot(zold.sub(root)));

        double f = Math.log(log_convergent_bailout / temp4) / Math.log(power);

        return 1 - f;
        
    }

    public static double fractionalPartMagnetConverging1(Complex z, Complex zold, Complex root, double log_convergent_bailout, Norm cNormImpl) {

        double temp = Math.log(cNormImpl.computeWithoutRoot(zold.sub(root)));
        return 1 - (log_convergent_bailout - temp) / (Math.log(cNormImpl.computeWithoutRoot(z.sub(root))) - temp);

    }
 
}
