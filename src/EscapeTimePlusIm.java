/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusIm extends OutColorAlgorithm {

    public EscapeTimePlusIm() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = ((Complex)object[1]).getIm();
        double temp3 = ((Integer)object[0]) + temp2 + 100800;
        return (int)(Math.abs(temp3));

    }
    
}
