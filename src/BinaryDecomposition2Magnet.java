/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class BinaryDecomposition2Magnet extends BinaryDecomposition2 {
    
    public BinaryDecomposition2Magnet() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Boolean)object[2] ? (((Complex)object[1]).getRe() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0] + 100234) : ((Complex)object[1]).getRe() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0]);

    } 
    
}
