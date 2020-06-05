/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
    private int custom_width;
    private int custom_height;
    private Font font;
 

    public SplashLabel(int custom_width, int custom_height) {

        super();

        _shadowAlpha = 150;
        _shadowOffset = 4;
        _shadowGap = 5;
        _arcs = new Dimension(50, 50);
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

        this.custom_width = custom_width;
        this.custom_height = custom_height;
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

    public int getCustomWidth() {

        return custom_width;

    }

    public int getCustomHeight() {

        return custom_height;

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
