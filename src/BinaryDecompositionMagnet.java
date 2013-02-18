/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class BinaryDecompositionMagnet extends BinaryDecomposition {
    
    public BinaryDecompositionMagnet() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Boolean)object[3] ? (((Complex)object[1]).getIm() < 0 ? (Double)object[0] + 100850 : (Double)object[0] + 100234) : ((Complex)object[1]).getIm() < 0 ? (Double)object[0] + 100850 : (Double)object[0]);

    } 
    
}
