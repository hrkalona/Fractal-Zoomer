/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.utils;

import fractalzoomer.main.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 *
 * @author hrkalona2
 */
public class BailoutTestsMenu extends JMenu {
    private MainWindow ptr;
    private JRadioButtonMenuItem[] bailout_tests;
    
    public BailoutTestsMenu(MainWindow ptr2, String name) {
        
        super(name);
        
        this.ptr = ptr2;
        
        bailout_tests = new JRadioButtonMenuItem[7];

        bailout_tests[MainWindow.BAILOUT_TEST_CIRCLE] = new JRadioButtonMenuItem("Circle (Euclidean norm)");
        bailout_tests[MainWindow.BAILOUT_TEST_CIRCLE].setToolTipText("The default bailout test.");
        bailout_tests[MainWindow.BAILOUT_TEST_CIRCLE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_CIRCLE);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_CIRCLE]);

        bailout_tests[MainWindow.BAILOUT_TEST_SQUARE] = new JRadioButtonMenuItem("Square (Infinity norm)");
        bailout_tests[MainWindow.BAILOUT_TEST_SQUARE].setToolTipText("The square bailout test.");
        bailout_tests[MainWindow.BAILOUT_TEST_SQUARE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_SQUARE);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_SQUARE]);

        bailout_tests[MainWindow.BAILOUT_TEST_RHOMBUS] = new JRadioButtonMenuItem("Rhombus (One norm)");
        bailout_tests[MainWindow.BAILOUT_TEST_RHOMBUS].setToolTipText("The rhombus bailout test.");
        bailout_tests[MainWindow.BAILOUT_TEST_RHOMBUS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_RHOMBUS);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_RHOMBUS]);

        bailout_tests[MainWindow.BAILOUT_TEST_NNORM] = new JRadioButtonMenuItem("N-Norm");
        bailout_tests[MainWindow.BAILOUT_TEST_NNORM].setToolTipText("The Nth norm test.");
        bailout_tests[MainWindow.BAILOUT_TEST_NNORM].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_NNORM);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_NNORM]);

        bailout_tests[MainWindow.BAILOUT_TEST_STRIP] = new JRadioButtonMenuItem("Strip");
        bailout_tests[MainWindow.BAILOUT_TEST_STRIP].setToolTipText("The strip bailout test.");
        bailout_tests[MainWindow.BAILOUT_TEST_STRIP].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_STRIP);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_STRIP]);

        bailout_tests[MainWindow.BAILOUT_TEST_HALFPLANE] = new JRadioButtonMenuItem("Halfplane");
        bailout_tests[MainWindow.BAILOUT_TEST_HALFPLANE].setToolTipText("The halfplane bailout test.");
        bailout_tests[MainWindow.BAILOUT_TEST_HALFPLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_HALFPLANE);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_HALFPLANE]);

        bailout_tests[MainWindow.BAILOUT_TEST_USER] = new JRadioButtonMenuItem("User Test");
        bailout_tests[MainWindow.BAILOUT_TEST_USER].setToolTipText("A bailout test defined by the user.");
        bailout_tests[MainWindow.BAILOUT_TEST_USER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setBailoutTest(MainWindow.BAILOUT_TEST_USER);

            }
        });
        add(bailout_tests[MainWindow.BAILOUT_TEST_USER]);

    }
    
    public JRadioButtonMenuItem[] getBailoutTests() {
        
        return bailout_tests;
        
    }
    
}
