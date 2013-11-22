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
public class Newton4Plane extends Plane {
    
    public Newton4Plane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.fourth().sub(1);
            Complex dfz = temp.cube().times(4);
            
            temp = temp.sub(fz.divide(dfz));
        }

        return temp;
 
    }
}
