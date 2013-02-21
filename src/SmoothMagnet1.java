



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class SmoothMagnet1 extends Smooth {
  private double log_convergent_bailout;
    
    public SmoothMagnet1(double log_bailout_squared, double log_convergent_bailout) {
        
        super(log_bailout_squared);
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[3]) {
            double temp = ((Complex)object[5]).norm_squared();
            temp += 0.000000001;
            temp = Math.log(temp);
            return (Double)object[0] + 100234 + (log_bailout_squared - temp) / (Math.log((Double)object[2]) - temp);
        }
        else {
            return (Double)object[0] - (log_convergent_bailout - (Double)object[4]) / ((Double)object[4] - Math.log(((Complex)object[1]).sub((Complex)object[5]).norm_squared()));
        }

    }
    

}
