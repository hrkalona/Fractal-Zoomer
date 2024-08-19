
package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author hrkalona2
 */
public class SplashLabel extends JLabel {
    private static final long serialVersionUID = 2790857258734510057L;
    private BufferedImage image;
    protected int strokeSize;
    protected Color _shadowColor;
    protected boolean shadowed;
    protected boolean _highQuality;
    protected Dimension _arcs;
    protected int _shadowGap;
    protected int _shadowOffset;
    protected int _shadowAlpha;
    private Font font;


    public SplashLabel(int custom_width, int custom_height) {

        super();

        _shadowAlpha = 150;
        _shadowOffset = 4;
        _shadowGap = 5;
        _arcs = new Dimension(25, 25);
        _highQuality = true;
        shadowed = false;
        strokeSize = 0;
        _shadowColor = Color.BLACK;

        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fractalzoomer/fonts/Blenda Script.otf"));
            ge.registerFont(font);
        }
        catch(IOException | FontFormatException e) {
            font = new Font("Arial", Font.BOLD, 25);
        }

        BufferedImage img = new BufferedImage(custom_width, custom_height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.createGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, custom_width, custom_height);

        setIcon(new ImageIcon(img));

    }

    public void drawText(String str, int x, int y, Color col, int size) {

        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g.setFont(font.deriveFont(Font.BOLD, size));
        g.setColor(col);
        g.drawString(str, x, y);

        g.dispose();

    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int shadowGap = this._shadowGap;
        Color shadowColorA = new Color(_shadowColor.getRed(), _shadowColor.getGreen(), _shadowColor.getBlue(), _shadowAlpha);
        Graphics2D graphics = (Graphics2D)g;

        if(_highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        }

        if(shadowed) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(_shadowOffset, _shadowOffset, width - strokeSize - _shadowOffset,
                    height - strokeSize - _shadowOffset, _arcs.width, _arcs.height);
        }
        else {
            _shadowGap = 1;
        }

        RoundRectangle2D.Float rr = new RoundRectangle2D.Float(0, 0, (width - shadowGap), (height - shadowGap), _arcs.width, _arcs.height);

        Shape clipShape = graphics.getClip();

        RoundRectangle2D.Float rr2 = new RoundRectangle2D.Float(0, 0, (width - strokeSize - shadowGap), (height - strokeSize - shadowGap), _arcs.width, _arcs.height);

        graphics.setClip(rr2);
        graphics.drawImage(image, 0, 0, null);
        graphics.setClip(clipShape);

        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.draw(rr);
        graphics.setStroke(new BasicStroke());

    }
}