/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class EscapeTimePlusRe extends ColorAlgorithm {

    public EscapeTimePlusRe() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[1]).getRe();
        double temp3 = ((Double)object[0]) + temp + 100800;
        return (int)(temp3 >= 0 ? temp3 : -temp3);

    }
    
}
