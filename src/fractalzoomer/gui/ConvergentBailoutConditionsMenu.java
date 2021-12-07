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
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class ConvergentBailoutConditionsMenu extends JMenu {
	private static final long serialVersionUID = -7944508537305034413L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] convergent_bailout_conditions;

    public static String[] convergentBailoutConditionNames;

    static {
        convergentBailoutConditionNames = new String[MainWindow.TOTAL_CONVERGENT_BAILOUT_CONDITIONS];
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE] = "Distance (Euclidean norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE] = "Distance Square (Infinity norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS] = "Distance Rhombus (One norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM] = "Distance N-Norm";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER] = "User Convergent Bailout Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT] = "No Condition";
    }

    public ConvergentBailoutConditionsMenu(MainWindow ptr2, String name, int bailout_test_algorithm) {
        
        super(name);
        
        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/bailout_conditions.png"));

        convergent_bailout_conditions = new JRadioButtonMenuItem[convergentBailoutConditionNames.length];
        
        ButtonGroup bailout_tests_group = new ButtonGroup();

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE].setToolTipText("The default convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE);

            }
        });
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE].setToolTipText("The distance square convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE);

            }
        });
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS].setToolTipText("The distance rhombus convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS);

            }
        });
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);


        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM].setToolTipText("The Nth norm convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM);

            }
        });
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);


        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT].setToolTipText("By setting this option, you are disabling the convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT);

            }
        });
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].setToolTipText("A convergent bailout condition defined by the user.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_USER);

            }
        });
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);

        convergent_bailout_conditions[bailout_test_algorithm].setSelected(true);
        
        JMenuItem skip_bailout_iterations_opt = new JMenuItem("Skip Convergent Bailout Condition Iterations", getIcon("/fractalzoomer/icons/skip_bailout.png"));

        skip_bailout_iterations_opt.setToolTipText("Skips the convergent bailout condition for the first N iterations.");
                
        skip_bailout_iterations_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.ALT_MASK));
        
        skip_bailout_iterations_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setSkipConvergentBailoutIterations();

            }
        });
        
        addSeparator();
        add(skip_bailout_iterations_opt);

        setEnabled(false);
    }
    
    public JRadioButtonMenuItem[] getConvergentBailoutConditions() {
        
        return convergent_bailout_conditions;
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
}
