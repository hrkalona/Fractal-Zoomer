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
 * @author hrkalona2
 */
public class PlanesMenu extends MyMenu {
	private static final long serialVersionUID = -4578443676894540517L;
	private JMenu planes_general_menu;
    private JMenu planes_fold_menu;
    private JMenu planes_distort_menu;
    private JMenu planes_newton_menu;
    private JMenu planes_math_menu;
    private JMenu planes_math_trigonometric_menu;
    private JMenu planes_math_inverse_trigonometric_menu;
    private MainWindow ptr;
    private JRadioButtonMenuItem[] planes;
    private JCheckBoxMenuItem apply_plane_on_julia_seed_opt;
    private JCheckBoxMenuItem apply_plane_on_whole_julia_opt;

    public static final String[] planeNames;

    static {
        planeNames = new String[MainWindow.TOTAL_PLANES];
        planeNames[MainWindow.MU_PLANE] = "mu";
        planeNames[MainWindow.MU_SQUARED_PLANE] = "mu^2";
        planeNames[MainWindow.MU_SQUARED_IMAGINARY_PLANE] = "mu^2i";
        planeNames[MainWindow.INVERSED_MU_PLANE] = "1 / mu";
        planeNames[MainWindow.INVERSED_MU2_PLANE] = "1 / (mu + 0.25)";
        planeNames[MainWindow.INVERSED_MU3_PLANE] = "1 / (mu - 1.40115)";
        planeNames[MainWindow.INVERSED_MU4_PLANE] = "1 / (mu - 2)";
        planeNames[MainWindow.LAMBDA_PLANE] = "lambda";
        planeNames[MainWindow.INVERSED_LAMBDA_PLANE] = "1 / lambda";
        planeNames[MainWindow.INVERSED_LAMBDA2_PLANE] = "1 / (lambda - 1)";
        planeNames[MainWindow.EXP_PLANE] = "exp";
        planeNames[MainWindow.LOG_PLANE] = "log";
        planeNames[MainWindow.SIN_PLANE] = "sin";
        planeNames[MainWindow.COS_PLANE] = "cos";
        planeNames[MainWindow.TAN_PLANE] = "tan";
        planeNames[MainWindow.COT_PLANE] = "cot";
        planeNames[MainWindow.SINH_PLANE] = "sinh";
        planeNames[MainWindow.COSH_PLANE] = "cosh";
        planeNames[MainWindow.TANH_PLANE] = "tanh";
        planeNames[MainWindow.COTH_PLANE] = "coth";
        planeNames[MainWindow.SQRT_PLANE] = "sqrt";
        planeNames[MainWindow.ABS_PLANE] = "abs";
        planeNames[MainWindow.FOLDUP_PLANE] = "Fold up";
        planeNames[MainWindow.FOLDRIGHT_PLANE] = "Fold right";
        planeNames[MainWindow.FOLDIN_PLANE] = "Fold in";
        planeNames[MainWindow.FOLDOUT_PLANE] = "Fold out";
        planeNames[MainWindow.ASIN_PLANE] = "asin";
        planeNames[MainWindow.ACOS_PLANE] = "acos";
        planeNames[MainWindow.ATAN_PLANE] = "atan";
        planeNames[MainWindow.ACOT_PLANE] = "acot";
        planeNames[MainWindow.ASINH_PLANE] = "asinh";
        planeNames[MainWindow.ACOSH_PLANE] = "acosh";
        planeNames[MainWindow.ATANH_PLANE] = "atanh";
        planeNames[MainWindow.ACOTH_PLANE] = "acoth";
        planeNames[MainWindow.SEC_PLANE] = "sec";
        planeNames[MainWindow.CSC_PLANE] = "csc";
        planeNames[MainWindow.SECH_PLANE] = "sech";
        planeNames[MainWindow.CSCH_PLANE] = "csch";
        planeNames[MainWindow.ASEC_PLANE] = "asec";
        planeNames[MainWindow.ACSC_PLANE] = "acsc";
        planeNames[MainWindow.ASECH_PLANE] = "asech";
        planeNames[MainWindow.ACSCH_PLANE] = "acsch";
        planeNames[MainWindow.NEWTON3_PLANE] = "Newton 3";
        planeNames[MainWindow.NEWTON4_PLANE] = "Newton 4";
        planeNames[MainWindow.NEWTONGENERALIZED3_PLANE] = "Newton Generalized 3";
        planeNames[MainWindow.NEWTONGENERALIZED8_PLANE] = "Newton Generalized 8";
        planeNames[MainWindow.USER_PLANE] = "User Plane";
        planeNames[MainWindow.GAMMA_PLANE] = "gamma";
        planeNames[MainWindow.FACT_PLANE] = "factorial";
        planeNames[MainWindow.BIPOLAR_PLANE] = "Bipolar";
        planeNames[MainWindow.INVERSED_BIPOLAR_PLANE] = "Inversed Bipolar";
        planeNames[MainWindow.TWIRL_PLANE] = "Twirl";
        planeNames[MainWindow.SHEAR_PLANE] = "Shear";
        planeNames[MainWindow.KALEIDOSCOPE_PLANE] = "Kaleidoscope";
        planeNames[MainWindow.PINCH_PLANE] = "Pinch";
        planeNames[MainWindow.FOLDDOWN_PLANE] = "Fold down";
        planeNames[MainWindow.FOLDLEFT_PLANE] = "Fold left";
        planeNames[MainWindow.CIRCLEINVERSION_PLANE] = "Circle Inversion";
        planeNames[MainWindow.VARIATION_MU_PLANE] = "(mu^2) / (mu^4 - 0.25)";
        planeNames[MainWindow.ERF_PLANE] = "erf";
        planeNames[MainWindow.RZETA_PLANE] = "riemann zeta";
        planeNames[MainWindow.INFLECTION_PLANE] = "Inflection";
        planeNames[MainWindow.RIPPLES_PLANE] = "Ripples";
        planeNames[MainWindow.SKEW_PLANE] = "Skew";
        planeNames[MainWindow.INFLECTIONS_PLANE] = "Multiple Inflections";
        planeNames[MainWindow.MU_FOURTH_PLANE] = "mu^4";
    }

    public PlanesMenu(MainWindow ptr2, String name, boolean apply_plane_on_julia, boolean apply_plane_on_julia_seed, int plane_type) {

        super(name);

        this.ptr = ptr2;

        setIcon(MainWindow.getIcon("planes.png"));

        planes = new JRadioButtonMenuItem[planeNames.length];

        ButtonGroup planes_button_group = new ButtonGroup();

        planes_general_menu = new MyMenu("General Planes");
        planes_fold_menu = new MyMenu("Fold Planes");
        planes_distort_menu = new MyMenu("Distort Planes");
        planes_newton_menu = new MyMenu("Newton Planes");
        planes_math_menu = new MyMenu("Math Planes");
        planes_math_trigonometric_menu = new MyMenu("Trigonometric Planes");
        planes_math_inverse_trigonometric_menu = new MyMenu("Inverse Trigonometric Planes");

        apply_plane_on_whole_julia_opt = new MyCheckBoxMenuItem("Apply Planes on Julia Plane");

        apply_plane_on_whole_julia_opt.setToolTipText("Enables the application of the plane transformation to the julia set plane.");

        apply_plane_on_whole_julia_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));

        apply_plane_on_julia_seed_opt = new MyCheckBoxMenuItem("Apply Planes on Julia Seed");

        apply_plane_on_julia_seed_opt.setToolTipText("Enables the application of the plane transformation to the julia set seed.");

        apply_plane_on_julia_seed_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.SHIFT_MASK));

        apply_plane_on_whole_julia_opt.setSelected(apply_plane_on_julia);
        apply_plane_on_julia_seed_opt.setSelected(apply_plane_on_julia_seed);

        add(planes_general_menu);
        addSeparator();
        add(planes_fold_menu);
        addSeparator();
        add(planes_distort_menu);
        addSeparator();
        add(planes_newton_menu);
        addSeparator();
        add(planes_math_menu);

        apply_plane_on_whole_julia_opt.addActionListener(e -> ptr.setApplyPlaneOnWholeJulia());

        apply_plane_on_julia_seed_opt.addActionListener(e -> ptr.setApplyPlaneOnJuliaSeed());

        planes[MainWindow.MU_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.MU_PLANE]);
        planes[MainWindow.MU_PLANE].setToolTipText("The default plane.");
        planes[MainWindow.MU_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.MU_PLANE));
        planes_general_menu.add(planes[MainWindow.MU_PLANE]);
        planes_button_group.add(planes[MainWindow.MU_PLANE]);

        planes[MainWindow.MU_SQUARED_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.MU_SQUARED_PLANE]);
        planes[MainWindow.MU_SQUARED_PLANE].setToolTipText("The mu squared plane.");
        planes[MainWindow.MU_SQUARED_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.MU_SQUARED_PLANE));
        planes_general_menu.add(planes[MainWindow.MU_SQUARED_PLANE]);
        planes_button_group.add(planes[MainWindow.MU_SQUARED_PLANE]);

        planes[MainWindow.MU_FOURTH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.MU_FOURTH_PLANE]);
        planes[MainWindow.MU_FOURTH_PLANE].setToolTipText("The mu fourth plane.");
        planes[MainWindow.MU_FOURTH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.MU_FOURTH_PLANE));
        planes_general_menu.add(planes[MainWindow.MU_FOURTH_PLANE]);
        planes_button_group.add(planes[MainWindow.MU_FOURTH_PLANE]);

        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.MU_SQUARED_IMAGINARY_PLANE]);
        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE].setToolTipText("The mu squared imaginary plane.");
        planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.MU_SQUARED_IMAGINARY_PLANE));
        planes_general_menu.add(planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE]);
        planes_button_group.add(planes[MainWindow.MU_SQUARED_IMAGINARY_PLANE]);

        planes[MainWindow.INVERSED_MU_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_MU_PLANE]);
        planes[MainWindow.INVERSED_MU_PLANE].setToolTipText("The inversed mu plane.");
        planes[MainWindow.INVERSED_MU_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_MU_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_MU_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_MU_PLANE]);

        planes[MainWindow.INVERSED_MU2_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_MU2_PLANE]);
        planes[MainWindow.INVERSED_MU2_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU2_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_MU2_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_MU2_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_MU2_PLANE]);

        planes[MainWindow.INVERSED_MU3_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_MU3_PLANE]);
        planes[MainWindow.INVERSED_MU3_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU3_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_MU3_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_MU3_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_MU3_PLANE]);

        planes[MainWindow.INVERSED_MU4_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_MU4_PLANE]);
        planes[MainWindow.INVERSED_MU4_PLANE].setToolTipText("An inversed mu plane variation.");
        planes[MainWindow.INVERSED_MU4_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_MU4_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_MU4_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_MU4_PLANE]);

        planes[MainWindow.VARIATION_MU_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.VARIATION_MU_PLANE]);
        planes[MainWindow.VARIATION_MU_PLANE].setToolTipText("An mu plane variation.");
        planes[MainWindow.VARIATION_MU_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.VARIATION_MU_PLANE));
        planes_general_menu.add(planes[MainWindow.VARIATION_MU_PLANE]);
        planes_button_group.add(planes[MainWindow.VARIATION_MU_PLANE]);

        planes[MainWindow.LAMBDA_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.LAMBDA_PLANE]);
        planes[MainWindow.LAMBDA_PLANE].setToolTipText("The lambda plane.");
        planes[MainWindow.LAMBDA_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.LAMBDA_PLANE));
        planes_general_menu.add(planes[MainWindow.LAMBDA_PLANE]);
        planes_button_group.add(planes[MainWindow.LAMBDA_PLANE]);

        planes[MainWindow.INVERSED_LAMBDA_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_LAMBDA_PLANE]);
        planes[MainWindow.INVERSED_LAMBDA_PLANE].setToolTipText("The inversed lambda plane.");
        planes[MainWindow.INVERSED_LAMBDA_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_LAMBDA_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_LAMBDA_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_LAMBDA_PLANE]);

        planes[MainWindow.INVERSED_LAMBDA2_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_LAMBDA2_PLANE]);
        planes[MainWindow.INVERSED_LAMBDA2_PLANE].setToolTipText("An inversed lambda plane variation.");
        planes[MainWindow.INVERSED_LAMBDA2_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_LAMBDA2_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_LAMBDA2_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_LAMBDA2_PLANE]);

        planes[MainWindow.BIPOLAR_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.BIPOLAR_PLANE]);
        planes[MainWindow.BIPOLAR_PLANE].setToolTipText("The bipolar plane.");
        planes[MainWindow.BIPOLAR_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.BIPOLAR_PLANE));
        planes_general_menu.add(planes[MainWindow.BIPOLAR_PLANE]);
        planes_button_group.add(planes[MainWindow.BIPOLAR_PLANE]);

        planes[MainWindow.INVERSED_BIPOLAR_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INVERSED_BIPOLAR_PLANE]);
        planes[MainWindow.INVERSED_BIPOLAR_PLANE].setToolTipText("The inversed bipolar plane.");
        planes[MainWindow.INVERSED_BIPOLAR_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INVERSED_BIPOLAR_PLANE));
        planes_general_menu.add(planes[MainWindow.INVERSED_BIPOLAR_PLANE]);
        planes_button_group.add(planes[MainWindow.INVERSED_BIPOLAR_PLANE]);

        planes[MainWindow.CIRCLEINVERSION_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.CIRCLEINVERSION_PLANE]);
        planes[MainWindow.CIRCLEINVERSION_PLANE].setToolTipText("The circle inversion plane.");
        planes[MainWindow.CIRCLEINVERSION_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.CIRCLEINVERSION_PLANE));
        planes_general_menu.add(planes[MainWindow.CIRCLEINVERSION_PLANE]);
        planes_button_group.add(planes[MainWindow.CIRCLEINVERSION_PLANE]);

        planes[MainWindow.INFLECTION_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INFLECTION_PLANE]);
        planes[MainWindow.INFLECTION_PLANE].setToolTipText("The inflection plane.");
        planes[MainWindow.INFLECTION_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INFLECTION_PLANE));
        planes_general_menu.add(planes[MainWindow.INFLECTION_PLANE]);
        planes_button_group.add(planes[MainWindow.INFLECTION_PLANE]);

        planes[MainWindow.INFLECTIONS_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.INFLECTIONS_PLANE]);
        planes[MainWindow.INFLECTIONS_PLANE].setToolTipText("The multiple inflection plane.");
        planes[MainWindow.INFLECTIONS_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.INFLECTIONS_PLANE));
        planes_general_menu.add(planes[MainWindow.INFLECTIONS_PLANE]);
        planes_button_group.add(planes[MainWindow.INFLECTIONS_PLANE]);

        planes[MainWindow.FOLDUP_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDUP_PLANE]);
        planes[MainWindow.FOLDUP_PLANE].setToolTipText("The fold up plane.");
        planes[MainWindow.FOLDUP_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDUP_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDUP_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDUP_PLANE]);

        planes[MainWindow.FOLDDOWN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDDOWN_PLANE]);
        planes[MainWindow.FOLDDOWN_PLANE].setToolTipText("The fold down plane.");
        planes[MainWindow.FOLDDOWN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDDOWN_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDDOWN_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDDOWN_PLANE]);

        planes[MainWindow.FOLDRIGHT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDRIGHT_PLANE]);
        planes[MainWindow.FOLDRIGHT_PLANE].setToolTipText("The fold right plane.");
        planes[MainWindow.FOLDRIGHT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDRIGHT_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDRIGHT_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDRIGHT_PLANE]);

        planes[MainWindow.FOLDLEFT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDLEFT_PLANE]);
        planes[MainWindow.FOLDLEFT_PLANE].setToolTipText("The fold left plane.");
        planes[MainWindow.FOLDLEFT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDLEFT_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDLEFT_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDLEFT_PLANE]);

        planes[MainWindow.FOLDIN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDIN_PLANE]);
        planes[MainWindow.FOLDIN_PLANE].setToolTipText("The fold in plane.");
        planes[MainWindow.FOLDIN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDIN_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDIN_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDIN_PLANE]);

        planes[MainWindow.FOLDOUT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FOLDOUT_PLANE]);
        planes[MainWindow.FOLDOUT_PLANE].setToolTipText("The fold out plane.");
        planes[MainWindow.FOLDOUT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FOLDOUT_PLANE));
        planes_fold_menu.add(planes[MainWindow.FOLDOUT_PLANE]);
        planes_button_group.add(planes[MainWindow.FOLDOUT_PLANE]);

        planes[MainWindow.TWIRL_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.TWIRL_PLANE]);
        planes[MainWindow.TWIRL_PLANE].setToolTipText("The twirl plane.");
        planes[MainWindow.TWIRL_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.TWIRL_PLANE));
        planes_distort_menu.add(planes[MainWindow.TWIRL_PLANE]);
        planes_button_group.add(planes[MainWindow.TWIRL_PLANE]);

        planes[MainWindow.SHEAR_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SHEAR_PLANE]);
        planes[MainWindow.SHEAR_PLANE].setToolTipText("The shear plane.");
        planes[MainWindow.SHEAR_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SHEAR_PLANE));
        planes_distort_menu.add(planes[MainWindow.SHEAR_PLANE]);
        planes_button_group.add(planes[MainWindow.SHEAR_PLANE]);

        planes[MainWindow.KALEIDOSCOPE_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.KALEIDOSCOPE_PLANE]);
        planes[MainWindow.KALEIDOSCOPE_PLANE].setToolTipText("The kaleidoscope plane.");
        planes[MainWindow.KALEIDOSCOPE_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.KALEIDOSCOPE_PLANE));
        planes_distort_menu.add(planes[MainWindow.KALEIDOSCOPE_PLANE]);
        planes_button_group.add(planes[MainWindow.KALEIDOSCOPE_PLANE]);

        planes[MainWindow.PINCH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.PINCH_PLANE]);
        planes[MainWindow.PINCH_PLANE].setToolTipText("The pinch plane.");
        planes[MainWindow.PINCH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.PINCH_PLANE));
        planes_distort_menu.add(planes[MainWindow.PINCH_PLANE]);
        planes_button_group.add(planes[MainWindow.PINCH_PLANE]);
        
        planes[MainWindow.RIPPLES_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.RIPPLES_PLANE]);
        planes[MainWindow.RIPPLES_PLANE].setToolTipText("The ripples plane.");
        planes[MainWindow.RIPPLES_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.RIPPLES_PLANE));
        planes_distort_menu.add(planes[MainWindow.RIPPLES_PLANE]);
        planes_button_group.add(planes[MainWindow.RIPPLES_PLANE]);
        
        planes[MainWindow.SKEW_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SKEW_PLANE]);
        planes[MainWindow.SKEW_PLANE].setToolTipText("The skew plane.");
        planes[MainWindow.SKEW_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SKEW_PLANE));
        planes_distort_menu.add(planes[MainWindow.SKEW_PLANE]);
        planes_button_group.add(planes[MainWindow.SKEW_PLANE]);

        planes[MainWindow.NEWTON3_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.NEWTON3_PLANE]);
        planes[MainWindow.NEWTON3_PLANE].setToolTipText("The Newton 3 plane.");
        planes[MainWindow.NEWTON3_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.NEWTON3_PLANE));
        planes_newton_menu.add(planes[MainWindow.NEWTON3_PLANE]);
        planes_button_group.add(planes[MainWindow.NEWTON3_PLANE]);

        planes[MainWindow.NEWTON4_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.NEWTON4_PLANE]);
        planes[MainWindow.NEWTON4_PLANE].setToolTipText("The Newton 4 plane.");
        planes[MainWindow.NEWTON4_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.NEWTON4_PLANE));
        planes_newton_menu.add(planes[MainWindow.NEWTON4_PLANE]);
        planes_button_group.add(planes[MainWindow.NEWTON4_PLANE]);

        planes[MainWindow.NEWTONGENERALIZED3_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.NEWTONGENERALIZED3_PLANE]);
        planes[MainWindow.NEWTONGENERALIZED3_PLANE].setToolTipText("The Newton Generalized 3 plane.");
        planes[MainWindow.NEWTONGENERALIZED3_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.NEWTONGENERALIZED3_PLANE));
        planes_newton_menu.add(planes[MainWindow.NEWTONGENERALIZED3_PLANE]);
        planes_button_group.add(planes[MainWindow.NEWTONGENERALIZED3_PLANE]);

        planes[MainWindow.NEWTONGENERALIZED8_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.NEWTONGENERALIZED8_PLANE]);
        planes[MainWindow.NEWTONGENERALIZED8_PLANE].setToolTipText("The Newton Generalized 8 plane.");
        planes[MainWindow.NEWTONGENERALIZED8_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.NEWTONGENERALIZED8_PLANE));
        planes_newton_menu.add(planes[MainWindow.NEWTONGENERALIZED8_PLANE]);
        planes_button_group.add(planes[MainWindow.NEWTONGENERALIZED8_PLANE]);

        planes[MainWindow.EXP_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.EXP_PLANE]);
        planes[MainWindow.EXP_PLANE].setToolTipText("The exponential plane.");
        planes[MainWindow.EXP_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.EXP_PLANE));
        planes_math_menu.add(planes[MainWindow.EXP_PLANE]);
        planes_button_group.add(planes[MainWindow.EXP_PLANE]);

        planes[MainWindow.LOG_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.LOG_PLANE]);
        planes[MainWindow.LOG_PLANE].setToolTipText("The logarithmic plane.");
        planes[MainWindow.LOG_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.LOG_PLANE));
        planes_math_menu.add(planes[MainWindow.LOG_PLANE]);
        planes_button_group.add(planes[MainWindow.LOG_PLANE]);

        planes[MainWindow.SQRT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SQRT_PLANE]);
        planes[MainWindow.SQRT_PLANE].setToolTipText("The square root plane.");
        planes[MainWindow.SQRT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SQRT_PLANE));
        planes_math_menu.add(planes[MainWindow.SQRT_PLANE]);
        planes_button_group.add(planes[MainWindow.SQRT_PLANE]);

        planes[MainWindow.ABS_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ABS_PLANE]);
        planes[MainWindow.ABS_PLANE].setToolTipText("The absolute value plane.");
        planes[MainWindow.ABS_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ABS_PLANE));
        planes_math_menu.add(planes[MainWindow.ABS_PLANE]);
        planes_button_group.add(planes[MainWindow.ABS_PLANE]);

        planes[MainWindow.GAMMA_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.GAMMA_PLANE]);
        planes[MainWindow.GAMMA_PLANE].setToolTipText("The gamma function plane.");
        planes[MainWindow.GAMMA_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.GAMMA_PLANE));
        planes_math_menu.add(planes[MainWindow.GAMMA_PLANE]);
        planes_button_group.add(planes[MainWindow.GAMMA_PLANE]);

        planes[MainWindow.FACT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.FACT_PLANE]);
        planes[MainWindow.FACT_PLANE].setToolTipText("The factorial plane.");
        planes[MainWindow.FACT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.FACT_PLANE));
        planes_math_menu.add(planes[MainWindow.FACT_PLANE]);
        planes_button_group.add(planes[MainWindow.FACT_PLANE]);

        planes[MainWindow.ERF_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ERF_PLANE]);
        planes[MainWindow.ERF_PLANE].setToolTipText("The error function plane.");
        planes[MainWindow.ERF_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ERF_PLANE));
        planes_math_menu.add(planes[MainWindow.ERF_PLANE]);
        planes_button_group.add(planes[MainWindow.ERF_PLANE]);

        planes[MainWindow.RZETA_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.RZETA_PLANE]);
        planes[MainWindow.RZETA_PLANE].setToolTipText("The riemann zeta plane.");
        planes[MainWindow.RZETA_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.RZETA_PLANE));
        planes_math_menu.add(planes[MainWindow.RZETA_PLANE]);
        planes_button_group.add(planes[MainWindow.RZETA_PLANE]);

        planes[MainWindow.SIN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SIN_PLANE]);
        planes[MainWindow.SIN_PLANE].setToolTipText("The sin plane.");
        planes[MainWindow.SIN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SIN_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.SIN_PLANE]);
        planes_button_group.add(planes[MainWindow.SIN_PLANE]);

        planes[MainWindow.COS_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.COS_PLANE]);
        planes[MainWindow.COS_PLANE].setToolTipText("The cos plane.");
        planes[MainWindow.COS_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.COS_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.COS_PLANE]);
        planes_button_group.add(planes[MainWindow.COS_PLANE]);

        planes[MainWindow.TAN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.TAN_PLANE]);
        planes[MainWindow.TAN_PLANE].setToolTipText("The tan plane.");
        planes[MainWindow.TAN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.TAN_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.TAN_PLANE]);
        planes_button_group.add(planes[MainWindow.TAN_PLANE]);

        planes[MainWindow.COT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.COT_PLANE]);
        planes[MainWindow.COT_PLANE].setToolTipText("The cot plane.");
        planes[MainWindow.COT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.COT_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.COT_PLANE]);
        planes_button_group.add(planes[MainWindow.COT_PLANE]);

        planes[MainWindow.SINH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SINH_PLANE]);
        planes[MainWindow.SINH_PLANE].setToolTipText("The hyperbolic sin plane.");
        planes[MainWindow.SINH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SINH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.SINH_PLANE]);
        planes_button_group.add(planes[MainWindow.SINH_PLANE]);

        planes[MainWindow.COSH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.COSH_PLANE]);
        planes[MainWindow.COSH_PLANE].setToolTipText("The hyperbolic cos plane.");
        planes[MainWindow.COSH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.COSH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.COSH_PLANE]);
        planes_button_group.add(planes[MainWindow.COSH_PLANE]);

        planes[MainWindow.TANH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.TANH_PLANE]);
        planes[MainWindow.TANH_PLANE].setToolTipText("The hyperbolic tan plane.");
        planes[MainWindow.TANH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.TANH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.TANH_PLANE]);
        planes_button_group.add(planes[MainWindow.TANH_PLANE]);

        planes[MainWindow.COTH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.COTH_PLANE]);
        planes[MainWindow.COTH_PLANE].setToolTipText("The hyperbolic cot plane.");
        planes[MainWindow.COTH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.COTH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.COTH_PLANE]);
        planes_button_group.add(planes[MainWindow.COTH_PLANE]);

        planes[MainWindow.SEC_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SEC_PLANE]);
        planes[MainWindow.SEC_PLANE].setToolTipText("The sec plane.");
        planes[MainWindow.SEC_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SEC_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.SEC_PLANE]);
        planes_button_group.add(planes[MainWindow.SEC_PLANE]);

        planes[MainWindow.CSC_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.CSC_PLANE]);
        planes[MainWindow.CSC_PLANE].setToolTipText("The csc plane.");
        planes[MainWindow.CSC_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.CSC_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.CSC_PLANE]);
        planes_button_group.add(planes[MainWindow.CSC_PLANE]);

        planes[MainWindow.SECH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.SECH_PLANE]);
        planes[MainWindow.SECH_PLANE].setToolTipText("The hyperbolic sec plane.");
        planes[MainWindow.SECH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.SECH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.SECH_PLANE]);
        planes_button_group.add(planes[MainWindow.SECH_PLANE]);

        planes[MainWindow.CSCH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.CSCH_PLANE]);
        planes[MainWindow.CSCH_PLANE].setToolTipText("The hyperbolic csc plane.");
        planes[MainWindow.CSCH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.CSCH_PLANE));
        planes_math_trigonometric_menu.add(planes[MainWindow.CSCH_PLANE]);
        planes_button_group.add(planes[MainWindow.CSCH_PLANE]);

        planes[MainWindow.ASIN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ASIN_PLANE]);
        planes[MainWindow.ASIN_PLANE].setToolTipText("The inverse sin plane.");
        planes[MainWindow.ASIN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ASIN_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASIN_PLANE]);
        planes_button_group.add(planes[MainWindow.ASIN_PLANE]);

        planes[MainWindow.ACOS_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACOS_PLANE]);
        planes[MainWindow.ACOS_PLANE].setToolTipText("The inverse cos plane.");
        planes[MainWindow.ACOS_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACOS_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOS_PLANE]);
        planes_button_group.add(planes[MainWindow.ACOS_PLANE]);

        planes[MainWindow.ATAN_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ATAN_PLANE]);
        planes[MainWindow.ATAN_PLANE].setToolTipText("The inverse tan plane.");
        planes[MainWindow.ATAN_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ATAN_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ATAN_PLANE]);
        planes_button_group.add(planes[MainWindow.ATAN_PLANE]);

        planes[MainWindow.ACOT_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACOT_PLANE]);
        planes[MainWindow.ACOT_PLANE].setToolTipText("The inverse cot plane.");
        planes[MainWindow.ACOT_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACOT_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOT_PLANE]);
        planes_button_group.add(planes[MainWindow.ACOT_PLANE]);

        planes[MainWindow.ASINH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ASINH_PLANE]);
        planes[MainWindow.ASINH_PLANE].setToolTipText("The inverse hyperbolic sin plane.");
        planes[MainWindow.ASINH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ASINH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASINH_PLANE]);
        planes_button_group.add(planes[MainWindow.ASINH_PLANE]);

        planes[MainWindow.ACOSH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACOSH_PLANE]);
        planes[MainWindow.ACOSH_PLANE].setToolTipText("The inverse hyperbolic cos plane.");
        planes[MainWindow.ACOSH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACOSH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOSH_PLANE]);
        planes_button_group.add(planes[MainWindow.ACOSH_PLANE]);

        planes[MainWindow.ATANH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ATANH_PLANE]);
        planes[MainWindow.ATANH_PLANE].setToolTipText("The inverse hyperbolic tan plane.");
        planes[MainWindow.ATANH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ATANH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ATANH_PLANE]);
        planes_button_group.add(planes[MainWindow.ATANH_PLANE]);

        planes[MainWindow.ACOTH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACOTH_PLANE]);
        planes[MainWindow.ACOTH_PLANE].setToolTipText("The inverse hyperbolic cot plane.");
        planes[MainWindow.ACOTH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACOTH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACOTH_PLANE]);
        planes_button_group.add(planes[MainWindow.ACOTH_PLANE]);

        planes[MainWindow.ASEC_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ASEC_PLANE]);
        planes[MainWindow.ASEC_PLANE].setToolTipText("The inverse sec plane.");
        planes[MainWindow.ASEC_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ASEC_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASEC_PLANE]);
        planes_button_group.add(planes[MainWindow.ASEC_PLANE]);

        planes[MainWindow.ACSC_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACSC_PLANE]);
        planes[MainWindow.ACSC_PLANE].setToolTipText("The inverse csc plane.");
        planes[MainWindow.ACSC_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACSC_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACSC_PLANE]);
        planes_button_group.add(planes[MainWindow.ACSC_PLANE]);

        planes[MainWindow.ASECH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ASECH_PLANE]);
        planes[MainWindow.ASECH_PLANE].setToolTipText("The inverse hyperbolic sec plane.");
        planes[MainWindow.ASECH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ASECH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ASECH_PLANE]);
        planes_button_group.add(planes[MainWindow.ASECH_PLANE]);

        planes[MainWindow.ACSCH_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.ACSCH_PLANE]);
        planes[MainWindow.ACSCH_PLANE].setToolTipText("The inverse hyperbolic csc plane.");
        planes[MainWindow.ACSCH_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.ACSCH_PLANE));
        planes_math_inverse_trigonometric_menu.add(planes[MainWindow.ACSCH_PLANE]);
        planes_button_group.add(planes[MainWindow.ACSCH_PLANE]);

        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_trigonometric_menu);
        planes_math_menu.addSeparator();
        planes_math_menu.add(planes_math_inverse_trigonometric_menu);

        planes[MainWindow.USER_PLANE] = new JRadioButtonMenuItem(planeNames[MainWindow.USER_PLANE]);
        planes[MainWindow.USER_PLANE].setToolTipText("A plane defined by the user.");
        planes[MainWindow.USER_PLANE].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        planes[MainWindow.USER_PLANE].addActionListener(e -> ptr.setPlane(MainWindow.USER_PLANE));
        addSeparator();
        add(planes[MainWindow.USER_PLANE]);
        planes_button_group.add(planes[MainWindow.USER_PLANE]);

        addSeparator();
        add(apply_plane_on_julia_seed_opt);
        addSeparator();
        add(apply_plane_on_whole_julia_opt);

        planes[plane_type].setSelected(true);

    }

    public JRadioButtonMenuItem[] getPlanes() {

        return planes;

    }

    public JCheckBoxMenuItem getApplyPlaneOnWholeJuliaOpt() {

        return apply_plane_on_whole_julia_opt;

    }

    public JCheckBoxMenuItem getApplyPlaneOnJuliaSeedOpt() {

        return apply_plane_on_julia_seed_opt;

    }

}
