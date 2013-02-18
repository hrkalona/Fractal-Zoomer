/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class SmoothSchroder extends Smooth {
  private double log_convergent_bailout;

    public SmoothSchroder(double log_convergent_bailout) {

        super(0, 0);
        this.log_convergent_bailout = log_convergent_bailout;

    }

    @Override
    public double getResult(Object[] object) {

        double temp4 = Math.log(((Complex)object[3]).sub((Complex)object[4]).norm_squared());
        return (Double)object[0] - (log_convergent_bailout - temp4) / ((Double)object[2] - temp4);

    }
    
}
