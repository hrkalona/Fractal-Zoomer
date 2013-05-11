package fractalzoomer.out_coloring_algorithms;

import fractalzoomer.core.Complex;





/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Biomorphs extends OutColorAlgorithm {
  protected double bailout;

    public Biomorphs(double bailout) {

        super();
        this.bailout = bailout;

    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[1]).getRe();
        double temp2 = ((Complex)object[1]).getIm();
        return temp > -bailout && temp < bailout || temp2 > -bailout && temp2 < bailout ?  (Integer)object[0] + 100800 : (Integer)object[0] + 100850;

    }

}
