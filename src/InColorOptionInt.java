/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class InColorOptionInt extends InColorOption {
    
    public InColorOptionInt() {
        super();
    }
    
    @Override
    public double getFinalResult(double result) {
        
        return (int)result;
        
    }
    
}
