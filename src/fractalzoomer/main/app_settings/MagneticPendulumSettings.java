
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class MagneticPendulumSettings {
    public double height;
    public double[][] magnetLocation;
    public double[][] magnetStrength;
    public double[] pendulum;
    public double stepsize;
    public double stepsize_im;
    public double[] friction;
    public double[] gravity;
    public int magnetPendVariableId;
    
    public MagneticPendulumSettings() {
        
        stepsize = 0.05;
        stepsize_im = 0;
        
        friction = new double[2];
        friction[0] = 0.07;
        friction[1] = 0;
        
        gravity = new double[2];
        gravity[0] = 0.2;
        gravity[1] = 0;
        
        height = 0.25;
        
        pendulum = new double[2];
        pendulum[0] = 0;
        pendulum[1] = 0;
        
        magnetLocation = new double[10][2];
        magnetLocation[0][0] = 2;
        magnetLocation[0][1] = 0;
        magnetLocation[1][0] = -1;
        magnetLocation[1][1] = 1.73205081;
        magnetLocation[2][0] = -1;
        magnetLocation[2][1] = -1.73205081;

        magnetStrength = new double[10][2];
        magnetStrength[0][0] = 1;
        magnetStrength[1][0] = 1;
        magnetStrength[2][0] = 1;
        
        magnetPendVariableId = 0;
    }
}
