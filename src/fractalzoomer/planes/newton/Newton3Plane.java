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
public class Newton3Plane extends Plane {
    
    public Newton3Plane() {
        
        super();
        
    }

    @Override
    public Complex getPixel(Complex pixel) {
        
        Complex temp = pixel;
        
        for(int iterations = 0; iterations < 5; iterations++) {
            Complex fz = temp.cube().sub(1);
            Complex dfz = temp.square().times(3);
            
            temp = temp.sub(fz.divide(dfz));
        }

        return temp;
 
    }
}
/*//Complex fz = pixel.cube().sub(1);
        //Complex dfz = pixel.square().times(3);
        
        //Complex fz = pixel.fourth().sub(1);
        //Complex dfz = pixel.cube().times(4);
        
        //Complex[] temp = pixel.der01_cos();
        
        //Complex fz = temp[0];
        //Complex dfz = temp[1];
        
        Complex fz = pixel.cube().sub_mutable(pixel.times(2)).plus_mutable(2);
        Complex dfz = pixel.square().times_mutable(3).sub_mutable(2);*/
        
        