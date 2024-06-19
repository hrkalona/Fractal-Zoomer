
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

    private JMenuItem donate;

    private JMenuItem useful_links;
    
    public HelpMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;
        
        help_contents = new MyMenuItem("Help Contents", MainWindow.getIcon("help.png"));

        fractInt_help = new MyMenuItem("FractInt Help", MainWindow.getIcon("help.png"));

        about = new MyMenuItem("About", MainWindow.getIcon("about.png"));

        useful_links = new MyMenuItem("Useful Links", MainWindow.getIcon("useful_links.png"));

        donate = new MyMenuItem("Donate", MainWindow.getIcon("paypal.png"));

        update = new MyMenuItem("Software Update", MainWindow.getIcon("update.png"));
        
        help_contents.setToolTipText("Loads the help file.");
        fractInt_help.setToolTipText("Loads a help file for FractInt users.");
        update.setToolTipText("Checks for software update.");
        useful_links.setToolTipText("Provides some useful links about fractals.");
        donate.setToolTipText("Help the development by donating any amount you like!");
        
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
        help_contents.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
        update.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
        
        help_contents.addActionListener(e -> ptr.showCHMHelpFile());

        useful_links.addActionListener(e -> ptr.showUsefulLinks());

        fractInt_help.addActionListener(e -> ptr.showFractIntHelpFile());

        about.addActionListener(e -> ptr.displayAboutInfo());

        update.addActionListener(e -> ptr.checkForUpdate(true));

        donate.addActionListener(e -> ptr.donate());
        
        add(help_contents);
        addSeparator();
        add(fractInt_help);
        addSeparator();
        add(useful_links);
        addSeparator();
        add(update);
        addSeparator();
        add(donate);
        addSeparator();
        add(about);
        
    }
    
}
