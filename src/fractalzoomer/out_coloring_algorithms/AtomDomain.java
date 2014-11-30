/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.out_coloring_algorithms;

/**
 *
 * @author hrkalona2
 */
public class AtomDomain extends OutColorAlgorithm {

    private double factor;

    public AtomDomain() {

        super();
        factor = 10 * Math.PI;
    }

    @Override
    public double getResult(Object[] object) {

        return (Integer)object[0] * factor + 100800;

    }

    @Override
    public double getResult3D(Object[] object) {

        return getResult(object);

    }
}
