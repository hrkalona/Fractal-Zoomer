/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class ZMag extends InColorAlgorithm {
  private InColorOption option;
  
  public ZMag(int out_coloring_algorithm) { 
        super();
        
        if(out_coloring_algorithm == MainWindow.SMOOTH_COLOR) {
            option = new InColorOptionDouble();
        }
        else {
            option = new InColorOptionInt();
        }
  
    }
    
    @Override
    public double getResult(Object[] object) {
        
        return option.getFinalResult(((Complex)object[1]).norm_squared() * ((Integer)object[0] / 3.0) + 100920);

    }
    
}
