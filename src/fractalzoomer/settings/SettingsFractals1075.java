
package fractalzoomer.settings;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 *
 * @author hrkalona
 */
public class SettingsFractals1075 extends SettingsFractals1074 implements Serializable {
    private static final long serialVersionUID = 5438695230524211L;
    private double circleWidth;
    private double gridWidth;
    private int gridAlgorithm;
    private int derivative_method;
    private int trapColorFillingMethod;
    private int[] trapImageData;
    private int trapImageWidth;
    private int trapImageHeight;
    
    
    public SettingsFractals1075(Settings s) {
        
        super(s);
        
        if((s.pps.ots.trapType == MainWindow.IMAGE_TRAP || s.pps.ots.trapType == MainWindow.IMAGE_TRANSPARENT_TRAP) && s.pps.ots.trapImage != null) {
            trapImageWidth = s.pps.ots.trapImage.getWidth();
            trapImageHeight = s.pps.ots.trapImage.getHeight();
            trapImageData = s.pps.ots.trapImage.getRGB(0, 0, trapImageWidth, trapImageHeight, null, 0, trapImageWidth);
        }
        trapColorFillingMethod = s.pps.ots.trapColorFillingMethod;
        derivative_method = s.fns.derivative_method;
        gridAlgorithm = s.ds.gridAlgorithm;
        circleWidth = s.ds.circleWidth;
        gridWidth = s.ds.gridWidth;      
        
    }
    
    @Override
    public int getVersion() {

        return 1075;

    }

    @Override
    public boolean isJulia() {

        return false;

    }

    /**
     * @return the circleWidth
     */
    public double getCircleWidth() {
        return circleWidth;
    }

    /**
     * @return the gridWidth
     */
    public double getGridWidth() {
        return gridWidth;
    }

    /**
     * @return the gridAlgorithm
     */
    public int getGridAlgorithm() {
        return gridAlgorithm;
    }

    /**
     * @return the derivative_method
     */
    public int getDerivativeMethod() {
        return derivative_method;
    }

    /**
     * @return the trapColorFillingMethod
     */
    public int getTrapColorFillingMethod() {
        return trapColorFillingMethod;
    }

    /**
     * @return the trapImage
     */
    public BufferedImage getTrapImage() {
        
        if(trapImageWidth == 0 || trapImageHeight == 0 || trapImageData == null) {
            return null;
        }
        
        BufferedImage img = new BufferedImage(trapImageWidth, trapImageHeight, BufferedImage.TYPE_INT_ARGB);
        img.setRGB(0, 0, trapImageWidth, trapImageHeight, trapImageData, 0, trapImageWidth);
        return img;
        
    }
    
}
