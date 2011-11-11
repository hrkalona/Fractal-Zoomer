/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class SmoothLambda extends Smooth {

    public SmoothLambda(double log_bailout_squared) {

        super(log_bailout_squared, 0);

    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[4]).magnitude();
        temp += 0.000000001;
        temp = Math.log(temp);
        return (Double)object[0] + (log_bailout_squared - temp) / (Math.log((Double)object[2]) - temp);

    }

}
