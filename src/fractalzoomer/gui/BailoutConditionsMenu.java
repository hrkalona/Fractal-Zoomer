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
public class BailoutConditionsMenu extends JMenu {
	private static final long serialVersionUID = -7944508537305034413L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] bailout_conditions;
    
    public static String[] bailoutConditionNames;
    
    static {
        bailoutConditionNames = new String[MainWindow.TOTAL_BAILOUT_CONDITIONS];
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CIRCLE] = "Circle (Euclidean norm)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_SQUARE] = "Square (Infinity norm)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RHOMBUS] = "Rhombus (One norm)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NNORM] = "N-Norm";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_STRIP] = "Strip";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_HALFPLANE] = "Halfplane";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_USER] = "User Bailout Condition";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_FIELD_LINES] = "Field Lines";
    }
    
    public BailoutConditionsMenu(MainWindow ptr2, String name, int bailout_test_algorithm) {
        
        super(name);
        
        this.ptr = ptr2;
        
        setIcon(getIcon("/fractalzoomer/icons/bailout_conditions.png"));
        
        bailout_conditions = new JRadioButtonMenuItem[bailoutConditionNames.length];
        
        ButtonGroup bailout_tests_group = new ButtonGroup();

        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CIRCLE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE].setToolTipText("The default bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_CIRCLE);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_SQUARE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE].setToolTipText("The square bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_SQUARE);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RHOMBUS]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS].setToolTipText("The rhombus bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_RHOMBUS);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NNORM]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM].setToolTipText("The Nth norm condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_NNORM);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_STRIP] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_STRIP]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_STRIP].setToolTipText("The strip bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_STRIP].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_STRIP);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_STRIP]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_STRIP]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE].setToolTipText("The halfplane bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_HALFPLANE);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES].setToolTipText("The field lines bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_FIELD_LINES);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_USER]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER].setToolTipText("A bailout condition defined by the user.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_USER);

            }
        });
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_USER]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_USER]);
        
        bailout_conditions[bailout_test_algorithm].setSelected(true);

    }
    
    public JRadioButtonMenuItem[] getBailoutConditions() {
        
        return bailout_conditions;
        
    }
    
    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
    
}
