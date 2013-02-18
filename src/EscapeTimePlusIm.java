/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusIm extends ColorAlgorithm {

    public EscapeTimePlusIm() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        double temp2 = ((Complex)object[1]).getIm();
        double temp3 = ((Double)object[0]) + temp2 + 100800;
        return (int)(temp3 >= 0 ? temp3 : -temp3);

    }
    
}
