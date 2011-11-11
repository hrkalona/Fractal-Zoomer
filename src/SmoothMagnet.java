/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class SmoothMagnet extends Smooth {
  private double log_convergent_bailout;
    
    public SmoothMagnet(double log_bailout_squared, double log_convergent_bailout) {
        
        super(log_bailout_squared, 0);
        this.log_convergent_bailout = log_convergent_bailout;
        
    }
    
    @Override
    public double getResult(Object[] object) {

        if((Boolean)object[4]) {
            double temp = ((Complex)object[6]).magnitude();
            temp += 0.000000001;
            temp = Math.log(temp);
            return (Double)object[0] + (log_bailout_squared - temp) / (Math.log((Double)object[2]) - temp);
        }
        else {
            return (Double)object[0] - (log_convergent_bailout - (Double)object[5]) / ((Double)object[5] - Math.log(((Complex)object[1]).sub((Complex)object[6]).magnitude()));
        }

    }
    

}
