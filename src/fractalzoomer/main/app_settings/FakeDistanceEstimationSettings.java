
package fractalzoomer.main.app_settings;

/**
 *
 * @author hrkalona2
 */
public class FakeDistanceEstimationSettings {
    public boolean fake_de;
    public boolean inverse_fake_dem;
    public double fake_de_factor;
    public int fade_algorithm;
    
    public FakeDistanceEstimationSettings() {
        
        fake_de = false;
        fake_de_factor = 1;
        inverse_fake_dem = false;
        fade_algorithm = 0;
        
    }
}
