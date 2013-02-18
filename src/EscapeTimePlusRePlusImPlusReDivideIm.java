



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class EscapeTimePlusRePlusImPlusReDivideIm extends ColorAlgorithm {

    public EscapeTimePlusRePlusImPlusReDivideIm() {

        super();
        
    }

    @Override
    public double getResult(Object[] object) {

        double temp = ((Complex)object[1]).getRe();
        double temp2 = ((Complex)object[1]).getIm();
        double temp3 = ((Double)object[0]) + temp + temp2 + temp / temp2 + 100800;
        return (int)(temp3 >= 0 ? temp3 : -temp3);

    }

}
