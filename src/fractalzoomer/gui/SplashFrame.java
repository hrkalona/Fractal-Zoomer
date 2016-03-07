/*
 * Fractal Zoomer, Copyright (C) 2016 hrkalona2
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

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hrkalona2
 */
public class SplashFrame extends JFrame {

    public SplashFrame() {

        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());
        ImageIcon ic = getIcon("/fractalzoomer/icons/splash.png");
        setSize(ic.getIconWidth(), ic.getIconHeight());
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setLayout(new BorderLayout());

        JLabel l1 = new JLabel();
        l1.setIcon(ic);

        add(l1, BorderLayout.PAGE_START); 
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));
    }
}
