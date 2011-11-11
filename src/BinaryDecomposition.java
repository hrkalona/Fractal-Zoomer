/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class BinaryDecomposition extends ColorAlgorithm {

    public BinaryDecomposition() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        return ((Complex)object[1]).getIm() < 0 ? (Double)object[0] + 50 : (Double)object[0];

    }

}
