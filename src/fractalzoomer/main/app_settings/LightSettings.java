
package fractalzoomer.main.app_settings;

/**
 *
 * @author kaloch
 */
public class LightSettings {
    public boolean lighting;
    public double[] lightVector;
    public double lightintensity;
    public double ambientlight;
    public double specularintensity;
    public double shininess;
    public int heightTransfer;
    public double heightTransferFactor;
    public int lightMode;
    public int colorMode;
    public double l_noise_reducing_factor;
    public double light_blending;
    public double light_direction;
    public double light_magnitude;
    public int specularReflectionMethod;
    public int fractionalTransfer;
    public int fractionalSmoothing;
    public int fractionalTransferMode;
    public double fractionalTransferScale;
    
    public LightSettings(LightSettings copy) {
        lighting = copy.lighting;
        lightVector = new double[2];
        
        light_direction = copy.light_direction;
        light_magnitude = copy.light_magnitude;
        
        double lightAngleRadians = Math.toRadians(light_direction);
        lightVector[0] = Math.cos(lightAngleRadians) * light_magnitude;
        lightVector[1] = Math.sin(lightAngleRadians) * light_magnitude;
 
        lightintensity = copy.lightintensity;
        ambientlight = copy.ambientlight;
        specularintensity = copy.specularintensity;
        shininess = copy.shininess;
        heightTransfer = copy.heightTransfer;
        lightMode = copy.lightMode;
        colorMode = copy.colorMode;
        l_noise_reducing_factor = copy.l_noise_reducing_factor;
        light_blending = copy.light_blending;
        heightTransferFactor = copy.heightTransferFactor;
        specularReflectionMethod = copy.specularReflectionMethod;
        fractionalTransfer = copy.fractionalTransfer;
        fractionalSmoothing = copy.fractionalSmoothing;
        fractionalTransferMode = copy.fractionalTransferMode;
        fractionalTransferScale = copy.fractionalTransferScale;
        
    }
    
    public LightSettings() {
        
        lighting = false;
        lightVector = new double[2];
        light_direction = 135;
        light_magnitude = 0.94328;
        
        double lightAngleRadians = Math.toRadians(light_direction);
        lightVector[0] = Math.cos(lightAngleRadians) * light_magnitude;
        lightVector[1] = Math.sin(lightAngleRadians) * light_magnitude;
 
        lightintensity = 1;
        ambientlight = 0.5;
        specularintensity = 1;
        shininess = 8;
        heightTransfer = 0;
        lightMode = 0;
        colorMode = 0;
        l_noise_reducing_factor = 1e-10;
        light_blending = 0.5;
        heightTransferFactor = 10;
        specularReflectionMethod = 0;
        fractionalTransfer = 0;
        fractionalSmoothing = 0;
        fractionalTransferMode = 1;
        fractionalTransferScale = 1;
        
    }
}
