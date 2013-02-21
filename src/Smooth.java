

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class Smooth extends ColorAlgorithm {
  protected double log_bailout_squared;

    public Smooth(double log_bailout_squared) {

        super();
        this.log_bailout_squared = log_bailout_squared;

    }

    @Override
    public double getResult(Object[] object) {

        //return (Double)object[0] - Math.log(Math.log((Double)object[2]) / log_bailout_squared) / log_base;
        double temp = ((Complex)object[3]).norm_squared();
        temp += 0.000000001;
        temp = Math.log(temp);
        return (Double)object[0] + (log_bailout_squared - temp) / (Math.log((Double)object[2]) - temp);

    }

}
