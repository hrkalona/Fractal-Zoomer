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
public class NewtonGeneralized3Plane extends Plane {
    
    public NewtonGeneralized3Plane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub(temp.times(2)).plus(2);
            Complex dfz = temp.square().times(3).sub(2);
            
            temp = temp.sub(fz.divide(dfz));
        }

        return temp;
 
    }
}
