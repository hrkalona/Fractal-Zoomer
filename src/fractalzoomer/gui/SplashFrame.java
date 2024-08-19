
package fractalzoomer.gui;

import fractalzoomer.app_updater.AppUpdater;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author hrkalona2
 */
public class SplashFrame extends JFrame {
	private static final long serialVersionUID = -3529067027804520472L;
	private Thread thread;
    

    public SplashFrame(int version) {

        BufferedImage image = convertToBufferedImage(MainWindow.getIcon("splash.png").getImage());

        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        setSize(image.getWidth(), image.getHeight());
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());


        SplashLabel l1 = new SplashLabel(image.getWidth(), image.getHeight());
        l1.setImage(image);

        if(Constants.beta) {
            l1.drawText("version " + AppUpdater.convertVersion(version) + " beta", image.getWidth() - 115, 50, new Color(27, 32, 99), 13);
        }
        else {
            l1.drawText("version " + AppUpdater.convertVersion(version), image.getWidth() - 90, 50, new Color(27, 32, 99), 13);
        }
        l1.drawText("Fractal Zoomer", image.getWidth() - 180, 30, Color.BLACK, 26);
        l1.repaint();

        add(l1, BorderLayout.PAGE_START);

        thread = new Thread(new SplashTask(this));
        thread.start();

        try {
            thread.join();
        }
        catch (Exception ex) {}
    }

    private BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
            image.getWidth(null), image.getHeight(null),
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
