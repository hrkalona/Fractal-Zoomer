/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona
 */
public class CrossOrbitTraps extends ColorAlgorithm {
  private double trap_size;
    
    public CrossOrbitTraps(double trap_size) {
        
        super();
        this.trap_size = trap_size;
        
    }

    @Override
    public double getResult(Object[] object) {
        
        return ((Double)(object[3])) / trap_size * 100;

    }
    
}
