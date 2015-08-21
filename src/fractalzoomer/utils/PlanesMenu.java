/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.utils;

import fractalzoomer.main.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author hrkalona2
 */
public class PlanesMenu extends JMenu {

    private JMenu planes_general_menu;
    private JMenu planes_fold_menu;
    private JMenu planes_distort_menu;
    private JMenu planes_newton_menu;
    private JMenu planes_math_menu;
    private JMenu planes_math_trigonometric_menu;
    private JMenu planes_math_inverse_trigonometric_menu;
    private MainWindow ptr;
    private JRadioButtonMenuItem[] planes;
    private JCheckBoxMenuItem apply_plane_on_julia_opt;

    public PlanesMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;

        planes = new JRadioButtonMenuItem[59];

        planes_general_menu = new JMenu("General Planes");
        planes_fold_menu = new JMenu("Fold Planes");
        planes_distort_menu = new JMenu("Distort Planes");
        planes_newton_menu = new JMenu("Newton Planes");
        planes_math_menu = new JMenu("Math Planes");
        planes_math_trigonometric_menu = new JMenu("Trigonometric Planes");
        planes_math_inverse_trigonometric_menu = new JMenu("Inverse Trigonometric Planes");

        apply_plane_on_julia_opt = new JCheckBoxMenuItem("Apply Planes on Julia");

        apply_plane_on_julia_opt.setToolTipText("Enables the application of the plane transformation to the julia set.");

        apply_plane_on_julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));

        add(planes_general_menu);
        addSeparator();
        add(planes_fold_menu);
        addSeparator();
        add(planes_distort_menu);
        addSeparator();
        add(planes_newton_menu);
        addSeparator();
        add(planes_math_menu);

        planes[MainWindow.MU_PLANE] = new JRadioButtonMenuItem("mu");
        planes[MainWindow.MU_PLANE].setToolTipText("The default plane.");
        planes[MainWindow.MU_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.MU_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.MU_PLANE]);

        planes[MainWindow.MU_SQUARED_PLANE] = new JRadioButtonMenuItem("mu^2");
        planes[MainWindow.MU_SQUARED_PLANE].setToolTipText("The mu squared plane.");
        planes[MainWindow.MU_SQUARED_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.MU_SQUARED_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.MU_SQUARED_PLANE]);

        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE] = new JRadioButtonMenuItem("mu^2i");
        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE].setToolTipText("The mu squared imaginary plane.");
        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.MU_SQUARED_IMAGINARY_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE]);

        planes[MainWindow.INVERSED_MU_PLANE] = new JRadioButtonMenuItem("1 / mu");
        planes[MainWindow.INVERSED_MU_PLANE].setToolTipText("The inversed mu plane.");
        planes[MainWindow.INVERSED_MU_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_MU_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_MU_PLANE]);

        planes[MainWindow.INVERSED_MU2_PLANE] = new JRadioButtonMenuItem("1 / (mu + 0.25)");
        planes[MainWindow.INVERSED_MU2_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU2_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_MU2_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_MU2_PLANE]);

        planes[MainWindow.INVERSED_MU3_PLANE] = new JRadioButtonMenuItem("1 / (mu - 1.40115)");
        planes[MainWindow.INVERSED_MU3_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_MU3_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_MU3_PLANE]);

        planes[MainWindow.INVERSED_MU4_PLANE] = new JRadioButtonMenuItem("1 / (mu - 2)");
        planes[MainWindow.INVERSED_MU4_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU4_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_MU4_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_MU4_PLANE]);

        planes[MainWindow.VARIATION_MU_PLANE] = new JRadioButtonMenuItem("(mu^2) / (mu^4 - 0.25)");
        planes[MainWindow.VARIATION_MU_PLANE].setToolTipText("An mu plane variation.");
        planes[MainWindow.VARIATION_MU_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.VARIATION_MU_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.VARIATION_MU_PLANE]);

        planes[MainWindow.LAMBDA_PLANE] = new JRadioButtonMenuItem("lambda");
        planes[MainWindow.LAMBDA_PLANE].setToolTipText("The lambda plane.");
        planes[MainWindow.LAMBDA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.LAMBDA_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.LAMBDA_PLANE]);

        planes[MainWindow.INVERSED_LAMBDA_PLANE] = new JRadioButtonMenuItem("1 / lambda");
        planes[MainWindow.INVERSED_LAMBDA_PLANE].setToolTipText("The inversed lambda plane.");
        planes[MainWindow.INVERSED_LAMBDA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_LAMBDA_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_LAMBDA_PLANE]);

        planes[MainWindow.INVERSED_LAMBDA2_PLANE] = new JRadioButtonMenuItem("1 / (lambda - 1)");
        planes[MainWindow.INVERSED_LAMBDA2_PLANE].setToolTipText("An inversed lambda plane variation.");
        planes[MainWindow.INVERSED_LAMBDA2_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_LAMBDA2_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_LAMBDA2_PLANE]);

        planes[MainWindow.BIPOLAR_PLANE] = new JRadioButtonMenuItem("Bipolar");
        planes[MainWindow.BIPOLAR_PLANE].setToolTipText("The bipolar plane.");
        planes[MainWindow.BIPOLAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.BIPOLAR_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.BIPOLAR_PLANE]);

        planes[MainWindow.INVERSED_BIPOLAR_PLANE] = new JRadioButtonMenuItem("Inversed Bipolar");
        planes[MainWindow.INVERSED_BIPOLAR_PLANE].setToolTipText("The inversed bipolar plane.");
        planes[MainWindow.INVERSED_BIPOLAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.INVERSED_BIPOLAR_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.INVERSED_BIPOLAR_PLANE]);

        planes[MainWindow.CIRCLEINVERSION_PLANE] = new JRadioButtonMenuItem("Circle Inversion");
        planes[MainWindow.CIRCLEINVERSION_PLANE].setToolTipText("The circle inversion plane.");
        planes[MainWindow.CIRCLEINVERSION_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.CIRCLEINVERSION_PLANE);

            }
        });
        planes_general_menu.add(planes[MainWindow.CIRCLEINVERSION_PLANE]);

        planes[MainWindow.FOLDUP_PLANE] = new JRadioButtonMenuItem("Fold up");
        planes[MainWindow.FOLDUP_PLANE].setToolTipText("The fold up plane.");
        planes[MainWindow.FOLDUP_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDUP_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDUP_PLANE]);

        planes[MainWindow.FOLDDOWN_PLANE] = new JRadioButtonMenuItem("Fold down");
        planes[MainWindow.FOLDDOWN_PLANE].setToolTipText("The fold down plane.");
        planes[MainWindow.FOLDDOWN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDDOWN_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDDOWN_PLANE]);

        planes[MainWindow.FOLDRIGHT_PLANE] = new JRadioButtonMenuItem("Fold right");
        planes[MainWindow.FOLDRIGHT_PLANE].setToolTipText("The fold right plane.");
        planes[MainWindow.FOLDRIGHT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDRIGHT_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDRIGHT_PLANE]);

        planes[MainWindow.FOLDLEFT_PLANE] = new JRadioButtonMenuItem("Fold left");
        planes[MainWindow.FOLDLEFT_PLANE].setToolTipText("The fold left plane.");
        planes[MainWindow.FOLDLEFT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDLEFT_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDLEFT_PLANE]);

        planes[MainWindow.FOLDIN_PLANE] = new JRadioButtonMenuItem("Fold in");
        planes[MainWindow.FOLDIN_PLANE].setToolTipText("The fold in plane.");
        planes[MainWindow.FOLDIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDIN_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDIN_PLANE]);

        planes[MainWindow.FOLDOUT_PLANE] = new JRadioButtonMenuItem("Fold out");
        planes[MainWindow.FOLDOUT_PLANE].setToolTipText("The fold out plane.");
        planes[MainWindow.FOLDOUT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FOLDOUT_PLANE);

            }
        });
        planes_fold_menu.add(planes[MainWindow.FOLDOUT_PLANE]);

        planes[MainWindow.TWIRL_PLANE] = new JRadioButtonMenuItem("Twirl");
        planes[MainWindow.TWIRL_PLANE].setToolTipText("The twirl plane.");
        planes[MainWindow.TWIRL_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.TWIRL_PLANE);

            }
        });
        planes_distort_menu.add(planes[MainWindow.TWIRL_PLANE]);

        planes[MainWindow.SHEAR_PLANE] = new JRadioButtonMenuItem("Shear");
        planes[MainWindow.SHEAR_PLANE].setToolTipText("The shear plane.");
        planes[MainWindow.SHEAR_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SHEAR_PLANE);

            }
        });
        planes_distort_menu.add(planes[MainWindow.SHEAR_PLANE]);

        planes[MainWindow.KALEIDOSCOPE_PLANE] = new JRadioButtonMenuItem("Kaleidoscope");
        planes[MainWindow.KALEIDOSCOPE_PLANE].setToolTipText("The kaleidoscope plane.");
        planes[MainWindow.KALEIDOSCOPE_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.KALEIDOSCOPE_PLANE);

            }
        });
        planes_distort_menu.add(planes[MainWindow.KALEIDOSCOPE_PLANE]);

        planes[MainWindow.PINCH_PLANE] = new JRadioButtonMenuItem("Pinch");
        planes[MainWindow.PINCH_PLANE].setToolTipText("The pinch plane.");
        planes[MainWindow.PINCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.PINCH_PLANE);

            }
        });
        planes_distort_menu.add(planes[MainWindow.PINCH_PLANE]);

        planes[MainWindow.NEWTON3_PLANE] = new JRadioButtonMenuItem("Newton 3");
        planes[MainWindow.NEWTON3_PLANE].setToolTipText("The Newton 3 plane.");
        planes[MainWindow.NEWTON3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.NEWTON3_PLANE);

            }
        });
        planes_newton_menu.add(planes[MainWindow.NEWTON3_PLANE]);

        planes[MainWindow.NEWTON4_PLANE] = new JRadioButtonMenuItem("Newton 4");
        planes[MainWindow.NEWTON4_PLANE].setToolTipText("The Newton 4 plane.");
        planes[MainWindow.NEWTON4_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.NEWTON4_PLANE);

            }
        });
        planes_newton_menu.add(planes[MainWindow.NEWTON4_PLANE]);

        planes[MainWindow.NEWTONGENERALIZED3_PLANE] = new JRadioButtonMenuItem("Newton Generalized 3");
        planes[MainWindow.NEWTONGENERALIZED3_PLANE].setToolTipText("The Newton Generalized 3 plane.");
        planes[MainWindow.NEWTONGENERALIZED3_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.NEWTONGENERALIZED3_PLANE);

            }
        });
        planes_newton_menu.add(planes[MainWindow.NEWTONGENERALIZED3_PLANE]);

        planes[MainWindow.NEWTONGENERALIZED8_PLANE] = new JRadioButtonMenuItem("Newton Generalized 8");
        planes[MainWindow.NEWTONGENERALIZED8_PLANE].setToolTipText("The Newton Generalized 8 plane.");
        planes[MainWindow.NEWTONGENERALIZED8_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.NEWTONGENERALIZED8_PLANE);

            }
        });
        planes_newton_menu.add(planes[MainWindow.NEWTONGENERALIZED8_PLANE]);

        planes[MainWindow.EXP_PLANE] = new JRadioButtonMenuItem("exp");
        planes[MainWindow.EXP_PLANE].setToolTipText("The exponential plane.");
        planes[MainWindow.EXP_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.EXP_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.EXP_PLANE]);

        planes[MainWindow.LOG_PLANE] = new JRadioButtonMenuItem("log");
        planes[MainWindow.LOG_PLANE].setToolTipText("The logarithmic plane.");
        planes[MainWindow.LOG_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.LOG_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.LOG_PLANE]);

        planes[MainWindow.SQRT_PLANE] = new JRadioButtonMenuItem("sqrt");
        planes[MainWindow.SQRT_PLANE].setToolTipText("The square root plane.");
        planes[MainWindow.SQRT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SQRT_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.SQRT_PLANE]);

        planes[MainWindow.ABS_PLANE] = new JRadioButtonMenuItem("abs");
        planes[MainWindow.ABS_PLANE].setToolTipText("The absolute value plane.");
        planes[MainWindow.ABS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ABS_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.ABS_PLANE]);

        planes[MainWindow.GAMMA_PLANE] = new JRadioButtonMenuItem("gamma");
        planes[MainWindow.GAMMA_PLANE].setToolTipText("The gamma function plane.");
        planes[MainWindow.GAMMA_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.GAMMA_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.GAMMA_PLANE]);

        planes[MainWindow.FACT_PLANE] = new JRadioButtonMenuItem("factorial");
        planes[MainWindow.FACT_PLANE].setToolTipText("The factorial plane.");
        planes[MainWindow.FACT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.FACT_PLANE);

            }
        });
        planes_math_menu.add(planes[MainWindow.FACT_PLANE]);

        planes[MainWindow.SIN_PLANE] = new JRadioButtonMenuItem("sin");
        planes[MainWindow.SIN_PLANE].setToolTipText("The sin plane.");
        planes[MainWindow.SIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SIN_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.SIN_PLANE]);

        planes[MainWindow.COS_PLANE] = new JRadioButtonMenuItem("cos");
        planes[MainWindow.COS_PLANE].setToolTipText("The cos plane.");
        planes[MainWindow.COS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.COS_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.COS_PLANE]);

        planes[MainWindow.TAN_PLANE] = new JRadioButtonMenuItem("tan");
        planes[MainWindow.TAN_PLANE].setToolTipText("The tan plane.");
        planes[MainWindow.TAN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.TAN_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.TAN_PLANE]);

        planes[MainWindow.COT_PLANE] = new JRadioButtonMenuItem("cot");
        planes[MainWindow.COT_PLANE].setToolTipText("The cot plane.");
        planes[MainWindow.COT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.COT_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.COT_PLANE]);

        planes[MainWindow.SINH_PLANE] = new JRadioButtonMenuItem("sinh");
        planes[MainWindow.SINH_PLANE].setToolTipText("The hyperbolic sin plane.");
        planes[MainWindow.SINH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SINH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.SINH_PLANE]);

        planes[MainWindow.COSH_PLANE] = new JRadioButtonMenuItem("cosh");
        planes[MainWindow.COSH_PLANE].setToolTipText("The hyperbolic cos plane.");
        planes[MainWindow.COSH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.COSH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.COSH_PLANE]);

        planes[MainWindow.TANH_PLANE] = new JRadioButtonMenuItem("tanh");
        planes[MainWindow.TANH_PLANE].setToolTipText("The hyperbolic tan plane.");
        planes[MainWindow.TANH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.TANH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.TANH_PLANE]);

        planes[MainWindow.COTH_PLANE] = new JRadioButtonMenuItem("coth");
        planes[MainWindow.COTH_PLANE].setToolTipText("The hyperbolic cot plane.");
        planes[MainWindow.COTH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.COTH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.COTH_PLANE]);

        planes[MainWindow.SEC_PLANE] = new JRadioButtonMenuItem("sec");
        planes[MainWindow.SEC_PLANE].setToolTipText("The sec plane.");
        planes[MainWindow.SEC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SEC_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.SEC_PLANE]);

        planes[MainWindow.CSC_PLANE] = new JRadioButtonMenuItem("csc");
        planes[MainWindow.CSC_PLANE].setToolTipText("The csc plane.");
        planes[MainWindow.CSC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.CSC_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.CSC_PLANE]);

        planes[MainWindow.SECH_PLANE] = new JRadioButtonMenuItem("sech");
        planes[MainWindow.SECH_PLANE].setToolTipText("The hyperbolic sec plane.");
        planes[MainWindow.SECH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.SECH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.SECH_PLANE]);

        planes[MainWindow.CSCH_PLANE] = new JRadioButtonMenuItem("csch");
        planes[MainWindow.CSCH_PLANE].setToolTipText("The hyperbolic csc plane.");
        planes[MainWindow.CSCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.CSCH_PLANE);

            }
        });
        planes_math_trigonometric_menu.add(planes[MainWindow.CSCH_PLANE]);

        planes[MainWindow.ASIN_PLANE] = new JRadioButtonMenuItem("asin");
        planes[MainWindow.ASIN_PLANE].setToolTipText("The inverse sin plane.");
        planes[MainWindow.ASIN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ASIN_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASIN_PLANE]);

        planes[MainWindow.ACOS_PLANE] = new JRadioButtonMenuItem("acos");
        planes[MainWindow.ACOS_PLANE].setToolTipText("The inverse cos plane.");
        planes[MainWindow.ACOS_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACOS_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOS_PLANE]);

        planes[MainWindow.ATAN_PLANE] = new JRadioButtonMenuItem("atan");
        planes[MainWindow.ATAN_PLANE].setToolTipText("The inverse tan plane.");
        planes[MainWindow.ATAN_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ATAN_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ATAN_PLANE]);

        planes[MainWindow.ACOT_PLANE] = new JRadioButtonMenuItem("acot");
        planes[MainWindow.ACOT_PLANE].setToolTipText("The inverse cot plane.");
        planes[MainWindow.ACOT_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACOT_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOT_PLANE]);

        planes[MainWindow.ASINH_PLANE] = new JRadioButtonMenuItem("asinh");
        planes[MainWindow.ASINH_PLANE].setToolTipText("The inverse hyperbolic sin plane.");
        planes[MainWindow.ASINH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ASINH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASINH_PLANE]);

        planes[MainWindow.ACOSH_PLANE] = new JRadioButtonMenuItem("acosh");
        planes[MainWindow.ACOSH_PLANE].setToolTipText("The inverse hyperbolic cos plane.");
        planes[MainWindow.ACOSH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACOSH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOSH_PLANE]);

        planes[MainWindow.ATANH_PLANE] = new JRadioButtonMenuItem("atanh");
        planes[MainWindow.ATANH_PLANE].setToolTipText("The inverse hyperbolic tan plane.");
        planes[MainWindow.ATANH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ATANH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ATANH_PLANE]);

        planes[MainWindow.ACOTH_PLANE] = new JRadioButtonMenuItem("acoth");
        planes[MainWindow.ACOTH_PLANE].setToolTipText("The inverse hyperbolic cot plane.");
        planes[MainWindow.ACOTH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACOTH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOTH_PLANE]);

        planes[MainWindow.ASEC_PLANE] = new JRadioButtonMenuItem("asec");
        planes[MainWindow.ASEC_PLANE].setToolTipText("The inverse sec plane.");
        planes[MainWindow.ASEC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ASEC_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASEC_PLANE]);

        planes[MainWindow.ACSC_PLANE] = new JRadioButtonMenuItem("acsc");
        planes[MainWindow.ACSC_PLANE].setToolTipText("The inverse csc plane.");
        planes[MainWindow.ACSC_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACSC_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACSC_PLANE]);

        planes[MainWindow.ASECH_PLANE] = new JRadioButtonMenuItem("asech");
        planes[MainWindow.ASECH_PLANE].setToolTipText("The inverse hyperbolic sec plane.");
        planes[MainWindow.ASECH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ASECH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASECH_PLANE]);

        planes[MainWindow.ACSCH_PLANE] = new JRadioButtonMenuItem("acsch");
        planes[MainWindow.ACSCH_PLANE].setToolTipText("The inverse hyperbolic csc plane.");
        planes[MainWindow.ACSCH_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.ACSCH_PLANE);

            }
        });
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACSCH_PLANE]);

        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_trigonometric_menu);
        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_inverse_trigonometric_menu);

        planes[MainWindow.USER_PLANE] = new JRadioButtonMenuItem("User Plane");
        planes[MainWindow.USER_PLANE].setToolTipText("A plane defined by the user.");
        planes[MainWindow.USER_PLANE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        planes[MainWindow.USER_PLANE].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setPlane(MainWindow.USER_PLANE);

            }
        });
        addSeparator();
        add(planes[MainWindow.USER_PLANE]);
        addSeparator();
        add(apply_plane_on_julia_opt);

    }

    public JRadioButtonMenuItem[] getPlanes() {

        return planes;

    }

    public JCheckBoxMenuItem getApplyPlaneOnJuliaOpt() {

        return apply_plane_on_julia_opt;

    }
}
