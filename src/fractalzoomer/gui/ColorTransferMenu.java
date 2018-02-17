/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author kaloch
 */
public class ColorTransferMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] color_transfer;
    
    public static String[] colorTransferNames;
    
    static {
        colorTransferNames = new String[MainWindow.TOTAL_COLOR_TRANSFER_FILTERS];
        colorTransferNames[MainWindow.LINEAR] = "Linear";
        colorTransferNames[MainWindow.SQUARE_ROOT] = "Square Root";
        colorTransferNames[MainWindow.CUBE_ROOT] = "Cube Root";
        colorTransferNames[MainWindow.FOURTH_ROOT] = "Fourth Root";
        colorTransferNames[MainWindow.LOGARITHM] = "Logarithm";
        colorTransferNames[MainWindow.LOG_LOG] = "Log Log";
        colorTransferNames[MainWindow.ATAN] = "Atan";
    }

    public ColorTransferMenu(MainWindow ptr2, String name, int selection) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/transfer_functions.png"));
        
        color_transfer = new JRadioButtonMenuItem[colorTransferNames.length];
        ButtonGroup color_transfer_group = new ButtonGroup();
        
        color_transfer[MainWindow.LINEAR] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LINEAR]);
        color_transfer[MainWindow.LINEAR].setToolTipText("Sets the color transfer function to linear.");
        color_transfer[MainWindow.LINEAR].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.LINEAR);

            }
        });
        add(color_transfer[MainWindow.LINEAR]);
        color_transfer_group.add(color_transfer[MainWindow.LINEAR]);
        
        
        color_transfer[MainWindow.SQUARE_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.SQUARE_ROOT]);
        color_transfer[MainWindow.SQUARE_ROOT].setToolTipText("Sets the color transfer function to square root.");
        color_transfer[MainWindow.SQUARE_ROOT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.SQUARE_ROOT);

            }
        });
        add(color_transfer[MainWindow.SQUARE_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.SQUARE_ROOT]);
        
        
        color_transfer[MainWindow.CUBE_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.CUBE_ROOT]);
        color_transfer[MainWindow.CUBE_ROOT].setToolTipText("Sets the color transfer function to cube root.");
        color_transfer[MainWindow.CUBE_ROOT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.CUBE_ROOT);

            }
        });
        add(color_transfer[MainWindow.CUBE_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.CUBE_ROOT]);
        
        
        color_transfer[MainWindow.FOURTH_ROOT] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.FOURTH_ROOT]);
        color_transfer[MainWindow.FOURTH_ROOT].setToolTipText("Sets the color transfer function to fourth root.");
        color_transfer[MainWindow.FOURTH_ROOT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.FOURTH_ROOT);

            }
        });
        add(color_transfer[MainWindow.FOURTH_ROOT]);
        color_transfer_group.add(color_transfer[MainWindow.FOURTH_ROOT]);
        
        
        color_transfer[MainWindow.LOGARITHM] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LOGARITHM]);
        color_transfer[MainWindow.LOGARITHM].setToolTipText("Sets the color transfer function to logarithm.");
        color_transfer[MainWindow.LOGARITHM].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.LOGARITHM);

            }
        });
        add(color_transfer[MainWindow.LOGARITHM]);
        color_transfer_group.add(color_transfer[MainWindow.LOGARITHM]);
        
        
        color_transfer[MainWindow.LOG_LOG] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.LOG_LOG]);
        color_transfer[MainWindow.LOG_LOG].setToolTipText("Sets the color transfer function to the logarithm of the logarithm.");
        color_transfer[MainWindow.LOG_LOG].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.LOG_LOG);

            }
        });
        add(color_transfer[MainWindow.LOG_LOG]);
        color_transfer_group.add(color_transfer[MainWindow.LOG_LOG]);
        
        
        color_transfer[MainWindow.ATAN] = new JRadioButtonMenuItem(colorTransferNames[MainWindow.ATAN]);
        color_transfer[MainWindow.ATAN].setToolTipText("Sets the color transfer function to atan.");
        color_transfer[MainWindow.ATAN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorTransfer(MainWindow.ATAN);

            }
        });
        add(color_transfer[MainWindow.ATAN]);
        color_transfer_group.add(color_transfer[MainWindow.ATAN]);
        
        color_transfer[selection].setSelected(true);
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JRadioButtonMenuItem[] getTranferFunctions() {
        
        return color_transfer;
        
    }
}
