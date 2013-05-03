/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class DecompositionLike  extends InColorAlgorithm {
  private InColorOption option;
  private double pi2;
  private double pi59;
    

    public DecompositionLike(int out_coloring_algorithm) { 
        super();
        
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            option = new InColorOptionDouble();
        }
        else {
            option = new InColorOptionInt();
        }
        
        pi2 = 2 * Math.PI;
        pi59 = 59 * Math.PI;
        
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return option.getFinalResult((Math.atan2(((Complex)object[1]).getIm(), ((Complex)object[1]).getRe()) / (pi2)  + 0.75) * pi59 + 100820);

    }
    
}
