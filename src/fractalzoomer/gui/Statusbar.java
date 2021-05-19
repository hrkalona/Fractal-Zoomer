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

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author hrkalona2
 */
public class Statusbar extends JToolBar {
	private static final long serialVersionUID = 5769474863261889826L;
	private JProgressBar progress;
    private JTextField real;
    private JTextField imaginary;
    private JLabel mode;
    
    public Statusbar() {
        super();
        
        setFloatable(false);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        setBorderPainted(true);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setPreferredSize(new Dimension(0, 22));
        
        JLabel label = new JLabel(" Re: ");
        // label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);
        
        real = new JTextField("Real");
        real.setHorizontalAlignment(JTextField.RIGHT);
        real.setPreferredSize(new Dimension(200, 22));
        real.setMaximumSize(real.getPreferredSize());
        //real.setMinimumSize(real.getPreferredSize());
        real.setEditable(false);
        real.setForeground(new Color(16, 78, 139));
        real.setToolTipText("Displays the Real part of the complex number.");

        add(real);
        add(new JLabel("   "));

        label = new JLabel("Im: ");
        //label.setFont(new Font("Arial", Font.PLAIN, 11));
        add(label);

        imaginary = new JTextField("Imaginary");
        imaginary.setPreferredSize(new Dimension(200, 22));
        imaginary.setMaximumSize(imaginary.getPreferredSize());
        //imaginary.setMinimumSize(imaginary.getPreferredSize());
        imaginary.setHorizontalAlignment(JTextField.RIGHT);
        imaginary.setEditable(false);
        imaginary.setForeground(new Color(0, 139, 69));
        imaginary.setToolTipText("Displays the Imaginary part of the complex number.");

        add(imaginary);

        add(Box.createHorizontalGlue());
        //statusbar.add(Box.createRigidArea(new Dimension(100,10)));
        addSeparator();
        
        progress = new JProgressBar();
        progress.setPreferredSize(new Dimension(220, 22));
        progress.setMaximumSize(progress.getPreferredSize());
        //progress.setMinimumSize(progress.getPreferredSize());
        progress.setStringPainted(true);
        progress.setForeground(MainWindow.progress_color);
        progress.setValue(0);

        add(progress);
        addSeparator();
        mode = new JLabel("Normal mode", SwingConstants.RIGHT);
        mode.setPreferredSize(new Dimension(100, 22));
        mode.setMaximumSize(mode.getPreferredSize());
        //mode.setMinimumSize(mode.getPreferredSize());
        mode.setToolTipText("Displays the active mode.");
        add(mode);

    }
    
    public JProgressBar getProgress() {
        
        return progress;
        
    }
    
    public JTextField getReal() {
        
        return real;
        
    }
    
    public JTextField getImaginary() {
        
        return imaginary;
        
    }
    
    public JLabel getMode() {
        
        return mode;
        
    }
    
}
