/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author hrkalona2
 */
public class ColorBlendingMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] color_blending;
    
    public static String[] colorBlendingNames;
    
    static {
        colorBlendingNames = new String[MainWindow.TOTAL_COLOR_BLENDING]; 
        colorBlendingNames[MainWindow.NORMAL_BLENDING] = "Normal";
        colorBlendingNames[MainWindow.MULTIPLY_BLENDING] = "Multiply";
        colorBlendingNames[MainWindow.DIVIDE_BLENDING] = "Divide";
        colorBlendingNames[MainWindow.ADDITION_BLENDING] = "Addition";
        colorBlendingNames[MainWindow.SUBTRACTION_BLENDING] = "Subtraction";
        colorBlendingNames[MainWindow.DIFFERENCE_BLENDING] = "Difference";
        colorBlendingNames[MainWindow.VALUE_BLENDING] = "Value";
        colorBlendingNames[MainWindow.OVERLAY_BLENDING] = "Overlay";
        colorBlendingNames[MainWindow.SCREEN_BLENDING] = "Screen";
        colorBlendingNames[MainWindow.DODGE_BLENDING] = "Dodge";
        colorBlendingNames[MainWindow.BURN_BLENDING] = "Burn";
        colorBlendingNames[MainWindow.DARKEN_ONLY_BLENDING] = "Darken Only";
        colorBlendingNames[MainWindow.LIGHTEN_ONLY_BLENDING] = "Lighten Only";
        colorBlendingNames[MainWindow.HARD_LIGHT_BLENDING] = "Hard Light";
        colorBlendingNames[MainWindow.GRAIN_EXTRACT_BLENDING] = "Grain Extract";
        colorBlendingNames[MainWindow.GRAIN_MERGE_BLENDING] = "Grain Merge";
        colorBlendingNames[MainWindow.SATURATION_BLENDING] = "Saturation";
        colorBlendingNames[MainWindow.COLOR_BLENDING] = "Color";
    }

    public ColorBlendingMenu(MainWindow ptr2, String name, int selection) {

        super(name);

        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/blending.png"));
        
        color_blending = new JRadioButtonMenuItem[colorBlendingNames.length];
        ButtonGroup color_transfer_group = new ButtonGroup();
        
        color_blending[MainWindow.NORMAL_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.NORMAL_BLENDING]);
        color_blending[MainWindow.NORMAL_BLENDING].setToolTipText("Sets the color blending to normal.");
        color_blending[MainWindow.NORMAL_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.NORMAL_BLENDING);

            }
        });
        add(color_blending[MainWindow.NORMAL_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.NORMAL_BLENDING]);
        
        color_blending[MainWindow.MULTIPLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.MULTIPLY_BLENDING]);
        color_blending[MainWindow.MULTIPLY_BLENDING].setToolTipText("Sets the color blending to multiply.");
        color_blending[MainWindow.MULTIPLY_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.MULTIPLY_BLENDING);

            }
        });
        add(color_blending[MainWindow.MULTIPLY_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.MULTIPLY_BLENDING]);
        
        color_blending[MainWindow.DIVIDE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DIVIDE_BLENDING]);
        color_blending[MainWindow.DIVIDE_BLENDING].setToolTipText("Sets the color blending to divide.");
        color_blending[MainWindow.DIVIDE_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.DIVIDE_BLENDING);

            }
        });
        add(color_blending[MainWindow.DIVIDE_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.DIVIDE_BLENDING]);
        
        color_blending[MainWindow.ADDITION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.ADDITION_BLENDING]);
        color_blending[MainWindow.ADDITION_BLENDING].setToolTipText("Sets the color blending to addition.");
        color_blending[MainWindow.ADDITION_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.ADDITION_BLENDING);

            }
        });
        add(color_blending[MainWindow.ADDITION_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.ADDITION_BLENDING]);
        
        color_blending[MainWindow.SUBTRACTION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SUBTRACTION_BLENDING]);
        color_blending[MainWindow.SUBTRACTION_BLENDING].setToolTipText("Sets the color blending to subtraction.");
        color_blending[MainWindow.SUBTRACTION_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.SUBTRACTION_BLENDING);

            }
        });
        add(color_blending[MainWindow.SUBTRACTION_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.SUBTRACTION_BLENDING]);
        
        color_blending[MainWindow.DIFFERENCE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DIFFERENCE_BLENDING]);
        color_blending[MainWindow.DIFFERENCE_BLENDING].setToolTipText("Sets the color blending to difference.");
        color_blending[MainWindow.DIFFERENCE_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.DIFFERENCE_BLENDING);

            }
        });
        add(color_blending[MainWindow.DIFFERENCE_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.DIFFERENCE_BLENDING]);
 
        color_blending[MainWindow.VALUE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.VALUE_BLENDING]);
        color_blending[MainWindow.VALUE_BLENDING].setToolTipText("Sets the color blending to value.");
        color_blending[MainWindow.VALUE_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.VALUE_BLENDING);

            }
        });
        add(color_blending[MainWindow.VALUE_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.VALUE_BLENDING]);
         
        color_blending[MainWindow.SATURATION_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SATURATION_BLENDING]);
        color_blending[MainWindow.SATURATION_BLENDING].setToolTipText("Sets the color blending to saturation.");
        color_blending[MainWindow.SATURATION_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.SATURATION_BLENDING);

            }
        });
        add(color_blending[MainWindow.SATURATION_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.SATURATION_BLENDING]);
        
        color_blending[MainWindow.COLOR_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.COLOR_BLENDING]);
        color_blending[MainWindow.COLOR_BLENDING].setToolTipText("Sets the color blending to color.");
        color_blending[MainWindow.COLOR_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.COLOR_BLENDING);

            }
        });
        add(color_blending[MainWindow.COLOR_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.COLOR_BLENDING]);
        
        color_blending[MainWindow.OVERLAY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.OVERLAY_BLENDING]);
        color_blending[MainWindow.OVERLAY_BLENDING].setToolTipText("Sets the color blending to overlay.");
        color_blending[MainWindow.OVERLAY_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.OVERLAY_BLENDING);

            }
        });
        add(color_blending[MainWindow.OVERLAY_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.OVERLAY_BLENDING]);
        
        color_blending[MainWindow.SCREEN_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.SCREEN_BLENDING]);
        color_blending[MainWindow.SCREEN_BLENDING].setToolTipText("Sets the color blending to screen.");
        color_blending[MainWindow.SCREEN_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.SCREEN_BLENDING);

            }
        });
        add(color_blending[MainWindow.SCREEN_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.SCREEN_BLENDING]);
        
        color_blending[MainWindow.DODGE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DODGE_BLENDING]);
        color_blending[MainWindow.DODGE_BLENDING].setToolTipText("Sets the color blending to dodge.");
        color_blending[MainWindow.DODGE_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.DODGE_BLENDING);

            }
        });
        add(color_blending[MainWindow.DODGE_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.DODGE_BLENDING]);
        
        color_blending[MainWindow.BURN_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.BURN_BLENDING]);
        color_blending[MainWindow.BURN_BLENDING].setToolTipText("Sets the color blending to burn.");
        color_blending[MainWindow.BURN_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.BURN_BLENDING);

            }
        });
        add(color_blending[MainWindow.BURN_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.BURN_BLENDING]);
        
        color_blending[MainWindow.DARKEN_ONLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.DARKEN_ONLY_BLENDING]);
        color_blending[MainWindow.DARKEN_ONLY_BLENDING].setToolTipText("Sets the color blending to darken only.");
        color_blending[MainWindow.DARKEN_ONLY_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.DARKEN_ONLY_BLENDING);

            }
        });
        add(color_blending[MainWindow.DARKEN_ONLY_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.DARKEN_ONLY_BLENDING]);
        
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.LIGHTEN_ONLY_BLENDING]);
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING].setToolTipText("Sets the color blending to lighten only.");
        color_blending[MainWindow.LIGHTEN_ONLY_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.LIGHTEN_ONLY_BLENDING);

            }
        });
        add(color_blending[MainWindow.LIGHTEN_ONLY_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.LIGHTEN_ONLY_BLENDING]);
        
        color_blending[MainWindow.HARD_LIGHT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.HARD_LIGHT_BLENDING]);
        color_blending[MainWindow.HARD_LIGHT_BLENDING].setToolTipText("Sets the color blending to hard light.");
        color_blending[MainWindow.HARD_LIGHT_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.HARD_LIGHT_BLENDING);

            }
        });
        add(color_blending[MainWindow.HARD_LIGHT_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.HARD_LIGHT_BLENDING]);
        
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.GRAIN_EXTRACT_BLENDING]);
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING].setToolTipText("Sets the color blending to grain extract.");
        color_blending[MainWindow.GRAIN_EXTRACT_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.GRAIN_EXTRACT_BLENDING);

            }
        });
        add(color_blending[MainWindow.GRAIN_EXTRACT_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.GRAIN_EXTRACT_BLENDING]);
        
        color_blending[MainWindow.GRAIN_MERGE_BLENDING] = new JRadioButtonMenuItem(colorBlendingNames[MainWindow.GRAIN_MERGE_BLENDING]);
        color_blending[MainWindow.GRAIN_MERGE_BLENDING].setToolTipText("Sets the color blending to grain merge.");
        color_blending[MainWindow.GRAIN_MERGE_BLENDING].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setColorBlending(MainWindow.GRAIN_MERGE_BLENDING);

            }
        });
        add(color_blending[MainWindow.GRAIN_MERGE_BLENDING]);
        color_transfer_group.add(color_blending[MainWindow.GRAIN_MERGE_BLENDING]);
 
        color_blending[selection].setSelected(true);
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
    public JRadioButtonMenuItem[] getBlendingModes() {
        
        return color_blending;
        
    }
}
