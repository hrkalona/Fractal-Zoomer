/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class BinaryDecomposition2 extends OutColorAlgorithm {

    public BinaryDecomposition2() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Complex)object[1]).getRe() < 0 ? (Integer)object[0] + 100850 : (Integer)object[0];

    }
}
