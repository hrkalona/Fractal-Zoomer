/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.planes.newton;

import fractalzoomer.core.Complex;
import fractalzoomer.planes.Plane;

/**
 *
 * @author hrkalona2
 */
public class NewtonGeneralized8Plane extends Plane {
    
    public NewtonGeneralized8Plane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.eighth().plus(temp.fourth().times(15)).sub(16);
            Complex dfz = temp.seventh().times(8).plus(temp.cube().times(60));
            
            temp = temp.sub(fz.divide(dfz));
        }

        return temp;
 
    }
}
