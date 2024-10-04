package fractalzoomer.utils.space_filling_curves;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class RenderFrame extends JFrame {
    public RenderFrame(BufferedImage img) {
        MainWindow.setLaf();
        setTitle("Render Frame");
        setSize(img.getWidth(), img.getHeight());

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                System.exit(0);

            }
        });

        JPanel p = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(img, 0, 0, null);
            }
        };

        JScrollPane pane = new JScrollPane(p);

        p.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
        setContentPane(pane);
        setVisible(true);
    }

    @Override
    public void repaint() {
        try {
            Thread.sleep(2);
        }
        catch (Exception ex) {

        }
        super.repaint();
    }
}
