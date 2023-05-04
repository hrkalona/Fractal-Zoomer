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
public class HelpMenu extends MyMenu {
	private static final long serialVersionUID = 4789486723955292724L;
	private MainWindow ptr;
    private JMenuItem help_contents;

    private JMenuItem fractInt_help;
    private JMenuItem about;
    private JMenuItem update;
    private JMenuItem useful_links;
    
    public HelpMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        help_contents = new MyMenuItem("Help Contents", MainWindow.getIcon("help.png"));

        fractInt_help = new MyMenuItem("FractInt Help", MainWindow.getIcon("help.png"));

        about = new MyMenuItem("About", MainWindow.getIcon("about.png"));

        useful_links = new MyMenuItem("Useful Links", MainWindow.getIcon("useful_links.png"));

        update = new MyMenuItem("Software Update", MainWindow.getIcon("update.png"));
        
        help_contents.setToolTipText("Loads the help file.");
        fractInt_help.setToolTipText("Loads a help file for FractInt users.");
        update.setToolTipText("Checks for software update.");
        useful_links.setToolTipText("Provides some useful links about fractals.");
        
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
        help_contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
        update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        
        help_contents.addActionListener(e -> ptr.showCHMHelpFile());

        useful_links.addActionListener(e -> ptr.showUsefulLinks());

        fractInt_help.addActionListener(e -> ptr.showFractIntHelpFile());

        about.addActionListener(e -> ptr.displayAboutInfo());

        update.addActionListener(e -> ptr.checkForUpdate(true));
        
        add(help_contents);
        addSeparator();
        add(fractInt_help);
        addSeparator();
        add(useful_links);
        addSeparator();
        add(update);
        addSeparator();
        add(about);
        
    }
    
}
