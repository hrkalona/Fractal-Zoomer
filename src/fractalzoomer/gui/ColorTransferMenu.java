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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author kaloch
 */
public class ColorTransferMenu extends MyMenu {
	private static final long serialVersionUID = 187762488530185183L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] color_transfer;
    private JMenuItem color_density_opt;
    
    public static final String[] colorTransferNames;
    
    static {
        colorTransferNames = new String[MainWindow.TOTAL_COLOR_TRANSFER_FILTERS];
        colorTransferNames[MainWindow.DEFAULT] = "Default";
        colorTransferNames[MainWindow.SQUARE_ROOT] = "Square Root";
        colorTransferNames[MainWindow.CUBE_ROOT] = "Cube Root";
        colorTransferNames[MainWindow.FOURTH_ROOT] = "Fourth Root";
        colorTransferNames[MainWindow.LOGARITHM] = "Logarithm";
        colorTransferNames[MainWindow.LOG_LOG] = "Log Log";
        colorTransferNames[MainWindow.ATAN] = "Atan";
        colorTransferNames[MainWindow.LINEAR] = "Linear";
    }

    public ColorTransferMenu(MainWindow ptr2, String name, int selection, final boolean outcoloring) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("transfer_functions.png"));
        
        color_transfer = new JRadioButtonMenuItem[colorTransferNames.length];
        ButtonGroup color_transfer_group = new ButtonGroup();
        
        color_transfer[MainWindow.DEFAULT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.DEFAULT]);
        color_transfer[MainWindow.DEFAULT].setToolTipText("Sets the color transfer function to default.");
        color_transfer[MainWindow.DEFAULT].addActionListener(e -> ptr.setColorTransfer(MainWindow.DEFAULT, outcoloring));
        add(color_transfer[MainWindow.DEFAULT]);
        color_transfer_group.add(color_transfer[MainWindow.DEFAULT]);
        
        
        color_transfer[MainWindow.SQUARE_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.SQUARE_ROOT]);
        color_transfer[MainWindow.SQUARE_ROOT].setToolTipText("Sets the color transfer function to square root.");
        color_transfer[MainWindow.SQUARE_ROOT].addActionListener(e -> ptr.setColorTransfer(MainWindow.SQUARE_ROOT, outcoloring));
        add(color_transfer[MainWindow.SQUARE_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.SQUARE_ROOT]);
        
        
        color_transfer[MainWindow.CUBE_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.CUBE_ROOT]);
        color_transfer[MainWindow.CUBE_ROOT].setToolTipText("Sets the color transfer function to cube root.");
        color_transfer[MainWindow.CUBE_ROOT].addActionListener(e -> ptr.setColorTransfer(MainWindow.CUBE_ROOT, outcoloring));
        add(color_transfer[MainWindow.CUBE_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.CUBE_ROOT]);
        
        
        color_transfer[MainWindow.FOURTH_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.FOURTH_ROOT]);
        color_transfer[MainWindow.FOURTH_ROOT].setToolTipText("Sets the color transfer function to fourth root.");
        color_transfer[MainWindow.FOURTH_ROOT].addActionListener(e -> ptr.setColorTransfer(MainWindow.FOURTH_ROOT, outcoloring));
        add(color_transfer[MainWindow.FOURTH_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.FOURTH_ROOT]);
        
        
        color_transfer[MainWindow.LOGARITHM] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LOGARITHM]);
        color_transfer[MainWindow.LOGARITHM].setToolTipText("Sets the color transfer function to logarithm.");
        color_transfer[MainWindow.LOGARITHM].addActionListener(e -> ptr.setColorTransfer(MainWindow.LOGARITHM, outcoloring));
        add(color_transfer[MainWindow.LOGARITHM]);
        color_transfer_group.add(color_transfer[MainWindow.LOGARITHM]);
        
        
        color_transfer[MainWindow.LOG_LOG] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LOG_LOG]);
        color_transfer[MainWindow.LOG_LOG].setToolTipText("Sets the color transfer function to the logarithm of the logarithm.");
        color_transfer[MainWindow.LOG_LOG].addActionListener(e -> ptr.setColorTransfer(MainWindow.LOG_LOG, outcoloring));
        add(color_transfer[MainWindow.LOG_LOG]);
        color_transfer_group.add(color_transfer[MainWindow.LOG_LOG]);
        
        
        color_transfer[MainWindow.ATAN] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.ATAN]);
        color_transfer[MainWindow.ATAN].setToolTipText("Sets the color transfer function to atan.");
        color_transfer[MainWindow.ATAN].addActionListener(e -> ptr.setColorTransfer(MainWindow.ATAN, outcoloring));
        add(color_transfer[MainWindow.ATAN]);
        color_transfer_group.add(color_transfer[MainWindow.ATAN]);

        color_transfer[MainWindow.LINEAR] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LINEAR]);
        color_transfer[MainWindow.LINEAR].setToolTipText("Sets the color transfer function to linear.");
        color_transfer[MainWindow.LINEAR].addActionListener(e -> ptr.setColorTransfer(MainWindow.LINEAR, outcoloring));
        add(color_transfer[MainWindow.LINEAR]);
        color_transfer_group.add(color_transfer[MainWindow.LINEAR]);
        
        color_transfer[selection].setSelected(true);

        color_density_opt = new MyMenuItem("Color Density", MainWindow.getIcon("color_density.png"));
        color_density_opt.setToolTipText("Changes the color density of the transfer function.");

        color_density_opt.addActionListener(e -> ptr.setColorDensity(outcoloring));

        if(outcoloring) {
            color_density_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.SHIFT_MASK | ActionEvent.ALT_MASK));
        }
        else {
            color_density_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK | ActionEvent.ALT_MASK));
        }

        addSeparator();
        add(color_density_opt);
    }
    
    public JRadioButtonMenuItem[] getTranferFunctions() {
        
        return color_transfer;
        
    }
}
