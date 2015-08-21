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
public class FractalFunctionsMenu extends JMenu {

    private MainWindow ptr;
    private JRadioButtonMenuItem[] fractal_functions;
    private JMenu mandelbrot_type_functions;
    private JMenu magnet_type_functions;
    private JMenu root_finding_functions;
    private JMenu newton_type_functions;
    private JMenu halley_type_functions;
    private JMenu schroder_type_functions;
    private JMenu householder_type_functions;
    private JMenu secant_type_functions;
    private JMenu steffensen_type_functions;
    private JMenu barnsley_type_functions;
    private JMenu szegedi_butterfly_type_functions;
    private JMenu math_type_functions;
    private JMenu formulas_type_functions;
    private JMenu kaliset_type_functions;
    private JMenu general_type_functions;
    private JMenu general_math_type_functions;
    private JMenu general_newton_variant_functions;
    private JMenu m_like_generalizations_type_functions;
    private JMenu c_azb_dze_type_functions;
    private JMenu c_azb_dze_f_g_type_functions;
    private JMenu zab_zde_fg_type_functions;
    private JMenu user_formulas_type_functions;
    private JCheckBoxMenuItem burning_ship_opt;
    private JCheckBoxMenuItem mandel_grass_opt;
    private int i;

    public FractalFunctionsMenu(MainWindow ptr2, String name) {

        super(name);

        this.ptr = ptr2;

        mandelbrot_type_functions = new JMenu("Mandelbrot Type");
        magnet_type_functions = new JMenu("Magnet Type");

        root_finding_functions = new JMenu("Root Finding Methods");
        newton_type_functions = new JMenu("Newton Method");
        halley_type_functions = new JMenu("Halley Method");
        schroder_type_functions = new JMenu("Schroder Method");
        householder_type_functions = new JMenu("Householder Method");
        secant_type_functions = new JMenu("Secant Method");
        steffensen_type_functions = new JMenu("Steffensen Method");

        barnsley_type_functions = new JMenu("Barnsley Type");

        szegedi_butterfly_type_functions = new JMenu("Szegedi Butterfly Type");

        math_type_functions = new JMenu("Math Library Type");

        formulas_type_functions = new JMenu("Formulas");

        kaliset_type_functions = new JMenu("Kaliset Type");
        m_like_generalizations_type_functions = new JMenu("M-Like Type");
        general_type_functions = new JMenu("General Type");
        general_math_type_functions = new JMenu("Math Library Type");
        general_newton_variant_functions = new JMenu("z = z - (z^n + c)/(nz^(n - 2))");
        c_azb_dze_type_functions = new JMenu("z = c(az^b + dz^e)");
        c_azb_dze_f_g_type_functions = new JMenu("z = (c(az^b + dz^e) + f)^g");

        zab_zde_fg_type_functions = new JMenu("z = (z^a + b)/(z^d + e) + f + g");

        user_formulas_type_functions = new JMenu("User Formulas");

        burning_ship_opt = new JCheckBoxMenuItem("Burning Ship");
        mandel_grass_opt = new JCheckBoxMenuItem("Mandel Grass");

        burning_ship_opt.setToolTipText("Enables the burning ship variation.");
        mandel_grass_opt.setToolTipText("Enables the mandel grass variation.");

        burning_ship_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        mandel_grass_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.SHIFT_MASK));

        fractal_functions = new JRadioButtonMenuItem[123];

        fractal_functions[0] = new JRadioButtonMenuItem("Mandelbrot z = z^2 + c");
        fractal_functions[0].addActionListener(new ActionListener() {

            int temp = 0;

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(temp);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[0]);

        for(i = 1; i < 9; i++) {
            fractal_functions[i] = new JRadioButtonMenuItem("Multibrot z = z^" + (i + 2) + " + c");
            fractal_functions[i].addActionListener(new ActionListener() {

                int temp = i;

                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            mandelbrot_type_functions.add(fractal_functions[i]);
        }

        fractal_functions[MainWindow.MANDELBROTNTH] = new JRadioButtonMenuItem("Multibrot z = z^n + c");
        fractal_functions[MainWindow.MANDELBROTNTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBROTNTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBROTNTH]);

        fractal_functions[MainWindow.MANDELBROTWTH] = new JRadioButtonMenuItem("Multibrot z = z^w + c");
        fractal_functions[MainWindow.MANDELBROTWTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBROTWTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBROTWTH]);

        fractal_functions[MainWindow.MANDELPOLY] = new JRadioButtonMenuItem("Multibrot Polynomial");
        fractal_functions[MainWindow.MANDELPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELPOLY);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELPOLY]);

        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(burning_ship_opt);
        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(mandel_grass_opt);
        mandelbrot_type_functions.addSeparator();

        add(mandelbrot_type_functions);

        addSeparator();

        fractal_functions[MainWindow.FORMULA1] = new JRadioButtonMenuItem("z = 0.25z^2 + c + (z^2 + c)^-2");
        fractal_functions[MainWindow.FORMULA1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA1);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA1]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA28] = new JRadioButtonMenuItem("z = cz^2 + (cz^2)^-1");
        fractal_functions[MainWindow.FORMULA28].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA28);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA28]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA29] = new JRadioButtonMenuItem("z = cz^2 + 1 + (cz^2 + 1)^-1");
        fractal_functions[MainWindow.FORMULA29].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA29);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA29]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA38] = new JRadioButtonMenuItem("z = z^2 + c/z^2");
        fractal_functions[MainWindow.FORMULA38].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA38);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA38]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA39] = new JRadioButtonMenuItem("z = (z^2)exp(1/z) + c");
        fractal_functions[MainWindow.FORMULA39].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA39);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA39]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA46] = new JRadioButtonMenuItem("z = (-0.4 + 0.2i)cz^2 + (-0.275i/c)z + c");
        fractal_functions[MainWindow.FORMULA46].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA46);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA46]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA2] = new JRadioButtonMenuItem("z = c(z + z^-1)");
        fractal_functions[MainWindow.FORMULA2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA2);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA2]);

        fractal_functions[MainWindow.FORMULA3] = new JRadioButtonMenuItem("z = c(z^3 + z^-3)");
        fractal_functions[MainWindow.FORMULA3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA3);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA3]);

        fractal_functions[MainWindow.FORMULA4] = new JRadioButtonMenuItem("z = c(5z^-1 + z^5)");
        fractal_functions[MainWindow.FORMULA4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA4);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA4]);

        fractal_functions[MainWindow.FORMULA5] = new JRadioButtonMenuItem("z = c(z^5 - 5z)");
        fractal_functions[MainWindow.FORMULA5].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA5);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA5]);

        fractal_functions[MainWindow.FORMULA6] = new JRadioButtonMenuItem("z = c(2z^2 - z^4)");
        fractal_functions[MainWindow.FORMULA6].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA6);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA6]);

        fractal_functions[MainWindow.FORMULA7] = new JRadioButtonMenuItem("z = c(2z^-2 - z^-4)");
        fractal_functions[MainWindow.FORMULA7].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA7);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA7]);

        fractal_functions[MainWindow.FORMULA8] = new JRadioButtonMenuItem("z = c(5z^-1 - z^-5)");
        fractal_functions[MainWindow.FORMULA8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA8);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA8]);

        fractal_functions[MainWindow.FORMULA9] = new JRadioButtonMenuItem("z = c(-3z^-3 + z^-9)");
        fractal_functions[MainWindow.FORMULA9].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA9);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA9]);

        fractal_functions[MainWindow.FORMULA10] = new JRadioButtonMenuItem("z = c(z^2.5 + z^-2.5)");
        fractal_functions[MainWindow.FORMULA10].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA10);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA10]);

        fractal_functions[MainWindow.FORMULA11] = new JRadioButtonMenuItem("z = c(z^2i + z^-2)");
        fractal_functions[MainWindow.FORMULA11].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA11);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA11]);

        fractal_functions[MainWindow.FORMULA12] = new JRadioButtonMenuItem("z = c(-3z^-2 + 2z^-3)");
        fractal_functions[MainWindow.FORMULA12].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA12);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA12]);

        fractal_functions[MainWindow.FORMULA27] = new JRadioButtonMenuItem("z = c((z + 2)^6 + (z + 2)^-6)");
        fractal_functions[MainWindow.FORMULA27].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA27);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA27]);

        m_like_generalizations_type_functions.add(c_azb_dze_type_functions);

        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA13] = new JRadioButtonMenuItem("z = (c(2z - z^2) + 1)^2");
        fractal_functions[MainWindow.FORMULA13].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA13);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA13]);

        fractal_functions[MainWindow.FORMULA14] = new JRadioButtonMenuItem("z = (c(2z - z^2) + 1)^3");
        fractal_functions[MainWindow.FORMULA14].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA14);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA14]);

        fractal_functions[MainWindow.FORMULA15] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^2");
        fractal_functions[MainWindow.FORMULA15].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA15);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA15]);

        fractal_functions[MainWindow.FORMULA16] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^3");
        fractal_functions[MainWindow.FORMULA16].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA16);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA16]);

        fractal_functions[MainWindow.FORMULA17] = new JRadioButtonMenuItem("z = (c(z - z^2) + 1)^4");
        fractal_functions[MainWindow.FORMULA17].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA17);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA17]);

        fractal_functions[MainWindow.FORMULA18] = new JRadioButtonMenuItem("z = (c(z^3 - z^4) + 1)^5");
        fractal_functions[MainWindow.FORMULA18].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA18);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA18]);

        m_like_generalizations_type_functions.add(c_azb_dze_f_g_type_functions);

        fractal_functions[MainWindow.FORMULA40] = new JRadioButtonMenuItem("z = (z^10 + c)/(z^8 + c) + c - 2");
        fractal_functions[MainWindow.FORMULA40].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA40);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[MainWindow.FORMULA40]);

        fractal_functions[MainWindow.FORMULA41] = new JRadioButtonMenuItem("z = (z^16 - 10)/(z^14 - c) + c - 6");
        fractal_functions[MainWindow.FORMULA41].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA41);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[MainWindow.FORMULA41]);

        m_like_generalizations_type_functions.addSeparator();
        m_like_generalizations_type_functions.add(zab_zde_fg_type_functions);

        fractal_functions[MainWindow.FORMULA19] = new JRadioButtonMenuItem("z = abs(z^-1) + c");
        fractal_functions[MainWindow.FORMULA19].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA19);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA19]);

        fractal_functions[MainWindow.FORMULA20] = new JRadioButtonMenuItem("z = abs(z^2) + c");
        fractal_functions[MainWindow.FORMULA20].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA20);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA20]);

        fractal_functions[MainWindow.FORMULA21] = new JRadioButtonMenuItem("z = abs(z)/abs(c) + c");
        fractal_functions[MainWindow.FORMULA21].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA21);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA21]);

        fractal_functions[MainWindow.FORMULA22] = new JRadioButtonMenuItem("z = abs(z/c) + c");
        fractal_functions[MainWindow.FORMULA22].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA22);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA22]);

        fractal_functions[MainWindow.FORMULA23] = new JRadioButtonMenuItem("z = abs(z/(0.5 + 0.5i)) + c");
        fractal_functions[MainWindow.FORMULA23].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA23);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA23]);

        fractal_functions[MainWindow.FORMULA24] = new JRadioButtonMenuItem("z = abs(z)/c + c");
        fractal_functions[MainWindow.FORMULA24].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA24);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA24]);

        fractal_functions[MainWindow.FORMULA25] = new JRadioButtonMenuItem("z = abs(z)^-3 + c");
        fractal_functions[MainWindow.FORMULA25].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA25);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA25]);

        fractal_functions[MainWindow.FORMULA26] = new JRadioButtonMenuItem("z = abs(z^-3) + c");
        fractal_functions[MainWindow.FORMULA26].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA26);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA26]);

        formulas_type_functions.add(m_like_generalizations_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(kaliset_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(general_type_functions);

        general_type_functions.add(general_math_type_functions);
        general_type_functions.addSeparator();
        general_type_functions.add(general_newton_variant_functions);

        add(formulas_type_functions);

        addSeparator();

        fractal_functions[MainWindow.LAMBDA] = new JRadioButtonMenuItem("Lambda");
        fractal_functions[MainWindow.LAMBDA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBDA);

            }
        });
        add(fractal_functions[MainWindow.LAMBDA]);
        addSeparator();

        fractal_functions[MainWindow.MAGNET1] = new JRadioButtonMenuItem("Magnet 1");
        fractal_functions[MainWindow.MAGNET1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET1);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET1]);

        fractal_functions[MainWindow.MAGNET2] = new JRadioButtonMenuItem("Magnet 2");
        fractal_functions[MainWindow.MAGNET2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET2);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET2]);
        add(magnet_type_functions);
        addSeparator();

        fractal_functions[MainWindow.NEWTON3] = new JRadioButtonMenuItem("Newton 3");
        fractal_functions[MainWindow.NEWTON3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTON3);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTON3]);

        fractal_functions[MainWindow.NEWTON4] = new JRadioButtonMenuItem("Newton 4");
        fractal_functions[MainWindow.NEWTON4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTON4);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTON4]);

        fractal_functions[MainWindow.NEWTONGENERALIZED3] = new JRadioButtonMenuItem("Newton Generalized 3");
        fractal_functions[MainWindow.NEWTONGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONGENERALIZED3);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONGENERALIZED3]);

        fractal_functions[MainWindow.NEWTONGENERALIZED8] = new JRadioButtonMenuItem("Newton Generalized 8");
        fractal_functions[MainWindow.NEWTONGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONGENERALIZED8);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONGENERALIZED8]);

        fractal_functions[MainWindow.NEWTONSIN] = new JRadioButtonMenuItem("Newton Sin");
        fractal_functions[MainWindow.NEWTONSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONSIN);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONSIN]);

        fractal_functions[MainWindow.NEWTONCOS] = new JRadioButtonMenuItem("Newton Cos");
        fractal_functions[MainWindow.NEWTONCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONCOS);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONCOS]);

        fractal_functions[MainWindow.NEWTONPOLY] = new JRadioButtonMenuItem("Newton Polynomial");
        fractal_functions[MainWindow.NEWTONPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONPOLY);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONPOLY]);

        root_finding_functions.add(newton_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(halley_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(schroder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(householder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(secant_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(steffensen_type_functions);

        add(root_finding_functions);
        addSeparator();

        fractal_functions[MainWindow.BARNSLEY1] = new JRadioButtonMenuItem("Barnsley 1");
        fractal_functions[MainWindow.BARNSLEY1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY1);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY1]);

        fractal_functions[MainWindow.BARNSLEY2] = new JRadioButtonMenuItem("Barnsley 2");
        fractal_functions[MainWindow.BARNSLEY2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY2);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY2]);

        fractal_functions[MainWindow.BARNSLEY3] = new JRadioButtonMenuItem("Barnsley 3");
        fractal_functions[MainWindow.BARNSLEY3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY3);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY3]);
        add(barnsley_type_functions);
        addSeparator();

        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1] = new JRadioButtonMenuItem("Szegedi Butterfly 1");
        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SZEGEDI_BUTTERFLY1);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1]);

        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2] = new JRadioButtonMenuItem("Szegedi Butterfly 2");
        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SZEGEDI_BUTTERFLY2);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2]);

        add(szegedi_butterfly_type_functions);
        addSeparator();

        fractal_functions[MainWindow.NOVA] = new JRadioButtonMenuItem("Nova");
        fractal_functions[MainWindow.NOVA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NOVA);

            }
        });
        add(fractal_functions[MainWindow.NOVA]);
        addSeparator();

        fractal_functions[MainWindow.MANDELBAR] = new JRadioButtonMenuItem("Mandelbar");
        fractal_functions[MainWindow.MANDELBAR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBAR);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBAR]);

        fractal_functions[MainWindow.SPIDER] = new JRadioButtonMenuItem("Spider");
        fractal_functions[MainWindow.SPIDER].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SPIDER);

            }
        });
        add(fractal_functions[MainWindow.SPIDER]);

        addSeparator();

        fractal_functions[MainWindow.MANOWAR] = new JRadioButtonMenuItem("Manowar");
        fractal_functions[MainWindow.MANOWAR].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANOWAR);

            }
        });
        add(fractal_functions[MainWindow.MANOWAR]);

        addSeparator();

        fractal_functions[MainWindow.PHOENIX] = new JRadioButtonMenuItem("Phoenix");
        fractal_functions[MainWindow.PHOENIX].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.PHOENIX);

            }
        });
        add(fractal_functions[MainWindow.PHOENIX]);

        addSeparator();

        fractal_functions[MainWindow.SIERPINSKI_GASKET] = new JRadioButtonMenuItem("Sierpinski Gasket");
        fractal_functions[MainWindow.SIERPINSKI_GASKET].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SIERPINSKI_GASKET);

            }
        });
        add(fractal_functions[MainWindow.SIERPINSKI_GASKET]);
        addSeparator();

        fractal_functions[MainWindow.FROTHY_BASIN] = new JRadioButtonMenuItem("Frothy Basin");
        fractal_functions[MainWindow.FROTHY_BASIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FROTHY_BASIN);

            }
        });
        add(fractal_functions[MainWindow.FROTHY_BASIN]);
        addSeparator();

        fractal_functions[MainWindow.EXP] = new JRadioButtonMenuItem("z = exp(z) + c");
        fractal_functions[MainWindow.EXP].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.EXP);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.EXP]);

        fractal_functions[MainWindow.LOG] = new JRadioButtonMenuItem("z = log(z) + c");
        fractal_functions[MainWindow.LOG].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LOG);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.LOG]);

        fractal_functions[MainWindow.SIN] = new JRadioButtonMenuItem("z = sin(z) + c");
        fractal_functions[MainWindow.SIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SIN);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.SIN]);

        fractal_functions[MainWindow.COS] = new JRadioButtonMenuItem("z = cos(z) + c");
        fractal_functions[MainWindow.COS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COS);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COS]);

        fractal_functions[MainWindow.TAN] = new JRadioButtonMenuItem("z = tan(z) + c");
        fractal_functions[MainWindow.TAN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.TAN);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.TAN]);

        fractal_functions[MainWindow.COT] = new JRadioButtonMenuItem("z = cot(z) + c");
        fractal_functions[MainWindow.COT].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COT);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COT]);

        fractal_functions[MainWindow.SINH] = new JRadioButtonMenuItem("z = sinh(z) + c");
        fractal_functions[MainWindow.SINH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SINH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.SINH]);

        fractal_functions[MainWindow.COSH] = new JRadioButtonMenuItem("z = cosh(z) + c");
        fractal_functions[MainWindow.COSH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COSH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COSH]);

        fractal_functions[MainWindow.TANH] = new JRadioButtonMenuItem("z = tanh(z) + c");
        fractal_functions[MainWindow.TANH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.TANH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.TANH]);

        fractal_functions[MainWindow.COTH] = new JRadioButtonMenuItem("z = coth(z) + c");
        fractal_functions[MainWindow.COTH].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COTH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COTH]);

        fractal_functions[MainWindow.FORMULA30] = new JRadioButtonMenuItem("z = sin(z)c");
        fractal_functions[MainWindow.FORMULA30].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA30);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA30]);

        fractal_functions[MainWindow.FORMULA31] = new JRadioButtonMenuItem("z = cos(z)c");
        fractal_functions[MainWindow.FORMULA31].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA31);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA31]);

        fractal_functions[MainWindow.FORMULA32] = new JRadioButtonMenuItem("z = zlog(z + 1) + c");
        fractal_functions[MainWindow.FORMULA32].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA32);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA32]);

        fractal_functions[MainWindow.FORMULA33] = new JRadioButtonMenuItem("z = zsin(z) + c");
        fractal_functions[MainWindow.FORMULA33].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA33);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA33]);

        fractal_functions[MainWindow.FORMULA34] = new JRadioButtonMenuItem("z = zsinh(z) + c");
        fractal_functions[MainWindow.FORMULA34].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA34);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA34]);

        fractal_functions[MainWindow.FORMULA35] = new JRadioButtonMenuItem("z = 2 - 2cos(z) + c");
        fractal_functions[MainWindow.FORMULA35].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA35);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA35]);

        fractal_functions[MainWindow.FORMULA36] = new JRadioButtonMenuItem("z = 2cosh(z) - 2 + c");
        fractal_functions[MainWindow.FORMULA36].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA36);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA36]);

        fractal_functions[MainWindow.FORMULA37] = new JRadioButtonMenuItem("z = zsin(z) - c^2");
        fractal_functions[MainWindow.FORMULA37].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA37);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA37]);

        fractal_functions[MainWindow.FORMULA42] = new JRadioButtonMenuItem("z = z - (z^3 + c)/(3z)");
        fractal_functions[MainWindow.FORMULA42].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA42);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA42]);

        fractal_functions[MainWindow.FORMULA43] = new JRadioButtonMenuItem("z = z - (z^4 + c)/(4z^2)");
        fractal_functions[MainWindow.FORMULA43].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA43);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA43]);

        fractal_functions[MainWindow.FORMULA44] = new JRadioButtonMenuItem("z = z - (z^5 + c)/(5z^3)");
        fractal_functions[MainWindow.FORMULA44].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA44);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA44]);

        fractal_functions[MainWindow.FORMULA45] = new JRadioButtonMenuItem("z = z - (z^8 + c)/(8z^6)");
        fractal_functions[MainWindow.FORMULA45].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA45);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA45]);

        add(math_type_functions);
        addSeparator();

        fractal_functions[MainWindow.USER_FORMULA] = new JRadioButtonMenuItem("User Formula");
        fractal_functions[MainWindow.USER_FORMULA].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED] = new JRadioButtonMenuItem("User Formula Iteration Based");
        fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_ITERATION_BASED);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL] = new JRadioButtonMenuItem("User Formula Conditional");
        fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_CONDITIONAL);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL]);

        add(user_formulas_type_functions);

        fractal_functions[MainWindow.HALLEY3] = new JRadioButtonMenuItem("Halley 3");
        fractal_functions[MainWindow.HALLEY3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEY3);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEY3]);

        fractal_functions[MainWindow.HALLEY4] = new JRadioButtonMenuItem("Halley 4");
        fractal_functions[MainWindow.HALLEY4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEY4);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEY4]);

        fractal_functions[MainWindow.HALLEYGENERALIZED3] = new JRadioButtonMenuItem("Halley Generalized 3");
        fractal_functions[MainWindow.HALLEYGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYGENERALIZED3);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYGENERALIZED3]);

        fractal_functions[MainWindow.HALLEYGENERALIZED8] = new JRadioButtonMenuItem("Halley Generalized 8");
        fractal_functions[MainWindow.HALLEYGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYGENERALIZED8);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYGENERALIZED8]);

        fractal_functions[MainWindow.HALLEYSIN] = new JRadioButtonMenuItem("Halley Sin");
        fractal_functions[MainWindow.HALLEYSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYSIN);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYSIN]);

        fractal_functions[MainWindow.HALLEYCOS] = new JRadioButtonMenuItem("Halley Cos");
        fractal_functions[MainWindow.HALLEYCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYCOS);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYCOS]);

        fractal_functions[MainWindow.HALLEYPOLY] = new JRadioButtonMenuItem("Halley Polynomial");
        fractal_functions[MainWindow.HALLEYPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYPOLY);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYPOLY]);

        fractal_functions[MainWindow.SCHRODER3] = new JRadioButtonMenuItem("Schroder 3");
        fractal_functions[MainWindow.SCHRODER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODER3);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODER3]);

        fractal_functions[MainWindow.SCHRODER4] = new JRadioButtonMenuItem("Schroder 4");
        fractal_functions[MainWindow.SCHRODER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODER4);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODER4]);

        fractal_functions[MainWindow.SCHRODERGENERALIZED3] = new JRadioButtonMenuItem("Schroder Generalized 3");
        fractal_functions[MainWindow.SCHRODERGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERGENERALIZED3);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERGENERALIZED3]);

        fractal_functions[MainWindow.SCHRODERGENERALIZED8] = new JRadioButtonMenuItem("Schroder Generalized 8");
        fractal_functions[MainWindow.SCHRODERGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERGENERALIZED8);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERGENERALIZED8]);

        fractal_functions[MainWindow.SCHRODERSIN] = new JRadioButtonMenuItem("Schroder Sin");
        fractal_functions[MainWindow.SCHRODERSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERSIN);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERSIN]);

        fractal_functions[MainWindow.SCHRODERCOS] = new JRadioButtonMenuItem("Schroder Cos");
        fractal_functions[MainWindow.SCHRODERCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERCOS);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERCOS]);

        fractal_functions[MainWindow.SCHRODERPOLY] = new JRadioButtonMenuItem("Schroder Polynomial");
        fractal_functions[MainWindow.SCHRODERPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERPOLY);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERPOLY]);

        fractal_functions[MainWindow.HOUSEHOLDER3] = new JRadioButtonMenuItem("Householder 3");
        fractal_functions[MainWindow.HOUSEHOLDER3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDER3);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDER3]);

        fractal_functions[MainWindow.HOUSEHOLDER4] = new JRadioButtonMenuItem("Householder 4");
        fractal_functions[MainWindow.HOUSEHOLDER4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDER4);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDER4]);

        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3] = new JRadioButtonMenuItem("Householder Generalized 3");
        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERGENERALIZED3);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3]);

        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8] = new JRadioButtonMenuItem("Householder Generalized 8");
        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERGENERALIZED8);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8]);

        fractal_functions[MainWindow.HOUSEHOLDERSIN] = new JRadioButtonMenuItem("Householder Sin");
        fractal_functions[MainWindow.HOUSEHOLDERSIN].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERSIN);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERSIN]);

        fractal_functions[MainWindow.HOUSEHOLDERCOS] = new JRadioButtonMenuItem("Householder Cos");
        fractal_functions[MainWindow.HOUSEHOLDERCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERCOS);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERCOS]);

        fractal_functions[MainWindow.HOUSEHOLDERPOLY] = new JRadioButtonMenuItem("Householder Polynomial");
        fractal_functions[MainWindow.HOUSEHOLDERPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERPOLY);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERPOLY]);

        fractal_functions[MainWindow.SECANT3] = new JRadioButtonMenuItem("Secant 3");
        fractal_functions[MainWindow.SECANT3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANT3);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANT3]);

        fractal_functions[MainWindow.SECANT4] = new JRadioButtonMenuItem("Secant 4");
        fractal_functions[MainWindow.SECANT4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANT4);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANT4]);

        fractal_functions[MainWindow.SECANTGENERALIZED3] = new JRadioButtonMenuItem("Secant Generalized 3");
        fractal_functions[MainWindow.SECANTGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTGENERALIZED3);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTGENERALIZED3]);

        fractal_functions[MainWindow.SECANTGENERALIZED8] = new JRadioButtonMenuItem("Secant Generalized 8");
        fractal_functions[MainWindow.SECANTGENERALIZED8].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTGENERALIZED8);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTGENERALIZED8]);

        fractal_functions[MainWindow.SECANTCOS] = new JRadioButtonMenuItem("Secant Cos");
        fractal_functions[MainWindow.SECANTCOS].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTCOS);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTCOS]);

        fractal_functions[MainWindow.SECANTPOLY] = new JRadioButtonMenuItem("Secant Polynomial");
        fractal_functions[MainWindow.SECANTPOLY].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTPOLY);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTPOLY]);

        fractal_functions[MainWindow.STEFFENSEN3] = new JRadioButtonMenuItem("Steffensen 3");
        fractal_functions[MainWindow.STEFFENSEN3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSEN3);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSEN3]);

        fractal_functions[MainWindow.STEFFENSEN4] = new JRadioButtonMenuItem("Steffensen 4");
        fractal_functions[MainWindow.STEFFENSEN4].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSEN4);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSEN4]);

        fractal_functions[MainWindow.STEFFENSENGENERALIZED3] = new JRadioButtonMenuItem("Steffensen Generalized 3");
        fractal_functions[MainWindow.STEFFENSENGENERALIZED3].addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSENGENERALIZED3);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSENGENERALIZED3]);

        burning_ship_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBurningShip();

            }
        });

        mandel_grass_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setMandelGrass();

            }
        });

    }

    public JRadioButtonMenuItem[] getFractalFunctions() {

        return fractal_functions;

    }
    
    public JCheckBoxMenuItem getBurningShipOpt() {
        
        return burning_ship_opt;
        
    }
    
    public JCheckBoxMenuItem getMandelGrassOpt() {
        
        return mandel_grass_opt;
        
    }

}
