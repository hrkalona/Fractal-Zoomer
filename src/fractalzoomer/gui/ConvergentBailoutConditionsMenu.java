

package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class ConvergentBailoutConditionsMenu extends MyMenu {
	private static final long serialVersionUID = -7944508537305034413L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] convergent_bailout_conditions;

    public static final String[] convergentBailoutConditionNames;

    static {
        convergentBailoutConditionNames = new String[MainWindow.TOTAL_CONVERGENT_BAILOUT_CONDITIONS];
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE] = "Distance (Euclidean norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE] = "Distance Square (Infinity norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS] = "Distance Rhombus (One norm)";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM] = "Distance N-Norm";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER] = "User Convergent Bailout Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT] = "No Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF] = "KF Norm-2 Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF] = "KF Norm-Infinity Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF] = "KF Norm-1 Condition";
        convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF] = "KF Norm-N Condition";
    }

    public ConvergentBailoutConditionsMenu(MainWindow ptr2, String name, int bailout_test_algorithm) {
        
        super(name);
        
        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("bailout_conditions.png"));

        convergent_bailout_conditions = new JRadioButtonMenuItem[convergentBailoutConditionNames.length];
        
        ButtonGroup bailout_tests_group = new ButtonGroup();

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE].setToolTipText("The default convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE].setToolTipText("The distance square convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS].setToolTipText("The distance rhombus convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS]);


        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM].setToolTipText("The Nth norm convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF].setToolTipText("A convergent bailout condition from Kalles Fraktaler.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_CIRCLE_KF]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF].setToolTipText("A convergent bailout condition from Kalles Fraktaler.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_SQUARE_KF]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF].setToolTipText("A convergent bailout condition from Kalles Fraktaler.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_RHOMBUS_KF]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF].setToolTipText("A convergent bailout condition from Kalles Fraktaler.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NNORM_KF]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT].setToolTipText("By setting this option, you are disabling the convergent bailout condition.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT]);

        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER] = new JRadioButtonMenuItem(convergentBailoutConditionNames[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].setToolTipText("A convergent bailout condition defined by the user.");
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].addActionListener(e -> ptr.setConvergentBailoutTest(MainWindow.CONVERGENT_BAILOUT_CONDITION_USER));
        convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
        add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);
        bailout_tests_group.add(convergent_bailout_conditions[MainWindow.CONVERGENT_BAILOUT_CONDITION_USER]);

        convergent_bailout_conditions[bailout_test_algorithm].setSelected(true);
        
        JMenuItem skip_bailout_iterations_opt = new MyMenuItem("Skip Convergent Bailout Condition Iterations", MainWindow.getIcon("skip_bailout.png"));

        skip_bailout_iterations_opt.setToolTipText("Skips the convergent bailout condition for the first N iterations.");
                
        skip_bailout_iterations_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.ALT_MASK));
        
        skip_bailout_iterations_opt.addActionListener(e -> ptr.setSkipConvergentBailoutIterations());
        
        addSeparator();
        add(skip_bailout_iterations_opt);

        setEnabled(false);
    }
    
    public JRadioButtonMenuItem[] getConvergentBailoutConditions() {
        
        return convergent_bailout_conditions;
        
    }
    
}
