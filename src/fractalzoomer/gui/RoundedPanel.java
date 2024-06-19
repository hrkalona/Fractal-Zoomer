

package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public class RoundedPanel extends JPanel {
	private static final long serialVersionUID = -3983056970333600349L;
	protected int _strokeSize = 1;
    protected Color _shadowColor = Color.BLACK;
    protected boolean _shadowed;
    protected boolean _highQuality;
    protected Dimension _arcs;
    protected int _shadowGap = 5;
    protected int _shadowOffset = 4;
    protected int _shadowAlpha = 150;
    protected boolean draw_edge;

    protected Color _backgroundColor = Color.LIGHT_GRAY;

    public RoundedPanel(boolean shadowed, boolean highquality, boolean draw_edge, int arc_size)
    {
        super();
        setOpaque(false);
        
        _shadowed = shadowed;
        _highQuality = highquality;
        this.draw_edge = draw_edge;
        _arcs = new Dimension(arc_size, arc_size);
    }

    public RoundedPanel(boolean shadowed, boolean highquality, boolean draw_edge, int arc_size, LayoutManager layout)
    {
        super(layout);
        setOpaque(false);

        _shadowed = shadowed;
        _highQuality = highquality;
        this.draw_edge = draw_edge;
        _arcs = new Dimension(arc_size, arc_size);
    }

    @Override
    public void setBackground(Color c)
    {

        _backgroundColor = c;
        super.setBackground(c);
       
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int shadowGap = this._shadowGap;
        Color shadowColorA = new Color(_shadowColor.getRed(), _shadowColor.getGreen(), _shadowColor.getBlue(), _shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        if(_highQuality)
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        }

        if(_shadowed)
        {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(_shadowOffset, _shadowOffset, width - _strokeSize - _shadowOffset,
                    height - _strokeSize - _shadowOffset, _arcs.width, _arcs.height);
        }
        else
        {
            _shadowGap = 1;
        }

        graphics.setColor(_backgroundColor);
        graphics.fillRoundRect(0,  0, width - shadowGap, height - shadowGap, _arcs.width, _arcs.height);
        
        if(draw_edge) {
            graphics.setStroke(new BasicStroke(_strokeSize));
            graphics.setColor(getForeground());
            graphics.drawRoundRect(0,  0, width - shadowGap, height - shadowGap, _arcs.width, _arcs.height);
            graphics.setStroke(new BasicStroke());
        }
    }
}