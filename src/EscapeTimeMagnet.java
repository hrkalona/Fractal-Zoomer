/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimeMagnet extends EscapeTime {
    
    public EscapeTimeMagnet() {
        super();
    }

    @Override
    public double getResult(Object[] object) {

        return  (Boolean)object[2] ? (Integer)object[0] + 100234  : (Integer)object[0] + 100800;

    }
    
}
