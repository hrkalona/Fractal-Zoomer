

package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class BailoutConditionsMenu extends MyMenu {
	private static final long serialVersionUID = -7944508537305034413L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] bailout_conditions;
    
    public static final String[] bailoutConditionNames;
    
    static {
        bailoutConditionNames = new String[MainWindow.TOTAL_BAILOUT_CONDITIONS];
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CIRCLE] = "Circle (Euclidean norm) (mod)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_SQUARE] = "Square (Infinity norm) (or)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RHOMBUS] = "Rhombus (One norm) (manh)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NNORM] = "N-Norm";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_REAL_STRIP] = "Real Strip (real)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_HALFPLANE] = "Halfplane";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_USER] = "User Bailout Condition";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_FIELD_LINES] = "Field Lines";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CROSS] = "Cross (and)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_IM_STRIP] = "Imaginary Strip (imag)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED] = "(Real + Imaginary)^2 (manr)";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NO_BAILOUT] = "No Condition";
        bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CUSTOM] = "|z| >= bail^(1 / sqrt(bail))";
    }
    
    public BailoutConditionsMenu(MainWindow ptr2, String name, int bailout_test_algorithm) {
        
        super(name);
        
        this.ptr = ptr2;
        
        setIcon(MainWindow.getIcon("bailout_conditions.png"));
        
        bailout_conditions = new JRadioButtonMenuItem[bailoutConditionNames.length];
        
        ButtonGroup bailout_tests_group = new ButtonGroup();

        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CIRCLE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE].setToolTipText("The default bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_CIRCLE));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CIRCLE]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_SQUARE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE].setToolTipText("The square bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_SQUARE));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_SQUARE]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RHOMBUS]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS].setToolTipText("The rhombus bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_RHOMBUS));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RHOMBUS]);
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CROSS] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CROSS]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CROSS].setToolTipText("The cross bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CROSS].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_CROSS));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CROSS]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CROSS]);
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_REAL_STRIP] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_REAL_STRIP]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_REAL_STRIP].setToolTipText("The real strip bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_REAL_STRIP].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_REAL_STRIP));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_REAL_STRIP]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_REAL_STRIP]);
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_IM_STRIP] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_IM_STRIP]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_IM_STRIP].setToolTipText("The imaginary strip condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_IM_STRIP].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_IM_STRIP));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_IM_STRIP]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_IM_STRIP]);
        
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED].setToolTipText("The (real + imaginary)^2 condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_RE_IM_SQUARED]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NNORM]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM].setToolTipText("The Nth norm bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_NNORM));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NNORM]);       

        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE].setToolTipText("The halfplane bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_HALFPLANE));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_HALFPLANE]);
        
        
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES].setToolTipText("The field lines bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_FIELD_LINES));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_FIELD_LINES]);


        bailout_conditions[MainWindow.BAILOUT_CONDITION_CUSTOM] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_CUSTOM]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CUSTOM].setToolTipText("A custom bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_CUSTOM].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_CUSTOM));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CUSTOM]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_CUSTOM]);


        bailout_conditions[MainWindow.BAILOUT_CONDITION_NO_BAILOUT] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_NO_BAILOUT]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NO_BAILOUT].setToolTipText("By setting this option, you are disabling the bailout condition.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_NO_BAILOUT].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_NO_BAILOUT));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NO_BAILOUT]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_NO_BAILOUT]);

        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER] = new JRadioButtonMenuItem(bailoutConditionNames[MainWindow.BAILOUT_CONDITION_USER]);
        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER].setToolTipText("A bailout condition defined by the user.");
        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER].addActionListener(e -> ptr.setBailoutTest(MainWindow.BAILOUT_CONDITION_USER));
        bailout_conditions[MainWindow.BAILOUT_CONDITION_USER].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        add(bailout_conditions[MainWindow.BAILOUT_CONDITION_USER]);
        bailout_tests_group.add(bailout_conditions[MainWindow.BAILOUT_CONDITION_USER]);
        
        bailout_conditions[bailout_test_algorithm].setSelected(true);
        
        JMenuItem skip_bailout_iterations_opt = new MyMenuItem("Skip Bailout Condition Iterations", MainWindow.getIcon("skip_bailout.png"));

        skip_bailout_iterations_opt.setToolTipText("Skips the bailout condition for the first N iterations.");
                
        skip_bailout_iterations_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_8, ActionEvent.CTRL_MASK));
        
        skip_bailout_iterations_opt.addActionListener(e -> ptr.setSkipBailoutIterations());
        
        addSeparator();
        add(skip_bailout_iterations_opt);
    }
    
    public JRadioButtonMenuItem[] getBailoutConditions() {
        
        return bailout_conditions;
        
    }
    
}
