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

/**
 *
 * @author hrkalona2
 */
/**
 * MySwing: Advanced Swing Utilites Copyright (C) 2005 Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class ComponentTitledBorder implements Border, MouseListener, MouseMotionListener, SwingConstants {

    private int offset = 5;
    private Component comp;
    private JComponent container;
    private Rectangle rect;
    private Border border;
    private boolean mouseEntered = false;
    private JFrame parentFrame;

    public ComponentTitledBorder(Component comp, JComponent container, Border border, JFrame ptr) {
        this.comp = comp;
        this.container = container;
        this.border = border;
        parentFrame = ptr;
        container.addMouseListener(this);
        container.addMouseMotionListener(this);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
    
    public void setChangeListener() {
        
        if(comp instanceof JCheckBox) {
            setCheckBoxListener((JCheckBox)comp);
        }
        else if(comp instanceof JRadioButton) {
            setRadioButtonListener((JRadioButton)comp);
        }
    }

    private void setCheckBoxListener(JCheckBox element) {
        element.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enable = element.isSelected();
                Component components[] = container.getComponents();
                
                for (int i = 0; i < components.length; i++) {
                    setComponentState(components[i], enable);
                }

                if (parentFrame instanceof OrbitTrapsFrame) {
                    ((OrbitTrapsFrame) parentFrame).toggled(element.isSelected());
                }
                else if(parentFrame instanceof CustomDomainColoringFrame) {
                    ((CustomDomainColoringFrame) parentFrame).toggled(element.isSelected());
                }
                else if(parentFrame instanceof StatisticsColoringFrame) {
                    ((StatisticsColoringFrame) parentFrame).toggled(element.isSelected());
                }
            }
        });

    }
    
    private void setRadioButtonListener(JRadioButton element) {
        
        element.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                boolean enable = element.isSelected();
                Component components[] = container.getComponents();
                
                for (int i = 0; i < components.length; i++) {
                    setComponentState(components[i], enable);
                }
                
                container.repaint();
            }
            
        });

    }

    public void setState(boolean state) {
        Component comp[] = container.getComponents();
        for (int i = 0; i < comp.length; i++) {
            setComponentState(comp[i], state);
        }
    }

    public void setComponentState(Component component, boolean state) {
        component.setEnabled(state);
        if (component instanceof JComponent) {
            JComponent a = (JComponent) component;
            Component comp[] = a.getComponents();
            for (int i = 0; i < comp.length; i++) {
                setComponentState(comp[i], state);
            }
        }
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Insets borderInsets = border.getBorderInsets(c);
        Insets insets = getBorderInsets(c);
        int temp = (insets.top - borderInsets.top) / 2;
        border.paintBorder(c, g, x, y + temp, width, height - temp);
        Dimension size = comp.getPreferredSize();
        rect = new Rectangle(offset, 0, size.width, size.height);
        SwingUtilities.paintComponent(g, comp, (Container) c, rect);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        Dimension size = comp.getPreferredSize();
        Insets insets = border.getBorderInsets(c);
        insets.top = Math.max(insets.top, size.height);
        return insets;
    }

    private void dispatchEvent(MouseEvent me) {
        if (rect != null && rect.contains(me.getX(), me.getY())) {
            dispatchEvent(me, me.getID());
        }
    }

    private void dispatchEvent(MouseEvent me, int id) {
        Point pt = me.getPoint();
        pt.translate(-offset, 0);

        comp.setSize(rect.width, rect.height);
        comp.dispatchEvent(new MouseEvent(comp, id, me.getWhen(),
                me.getModifiers(), pt.x, pt.y, me.getClickCount(),
                me.isPopupTrigger(), me.getButton()));
        if (!comp.isValid()) {
            container.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        dispatchEvent(me);
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (mouseEntered) {
            mouseEntered = false;
            dispatchEvent(me, MouseEvent.MOUSE_EXITED);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        dispatchEvent(me);
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        dispatchEvent(me);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (rect == null) {
            return;
        }

        if (mouseEntered == false && rect.contains(me.getX(), me.getY())) {
            mouseEntered = true;
            dispatchEvent(me, MouseEvent.MOUSE_ENTERED);
        } else if (mouseEntered == true) {
            if (rect.contains(me.getX(), me.getY()) == false) {
                mouseEntered = false;
                dispatchEvent(me, MouseEvent.MOUSE_EXITED);
            } else {
                dispatchEvent(me, MouseEvent.MOUSE_MOVED);
            }
        }
    }
}
