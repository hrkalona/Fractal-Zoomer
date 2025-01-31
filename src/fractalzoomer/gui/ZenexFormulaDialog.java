
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ZenexFormulaDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;


    public ZenexFormulaDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Zenex Formula");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JPanel[] var_panels = new JPanel[s.fns.zenex_re.length];
        JTextField[] variable_re = new JTextField[var_panels.length];
        JTextField[] variable_im = new JTextField[var_panels.length];

        for (int k = 0; k < var_panels.length; k++) {
            var_panels[k] = new JPanel();
            var_panels[k].setLayout(new FlowLayout());

            variable_re[k] = new JTextField(20);
            variable_re[k].setText("" + s.fns.zenex_re[k]);

            variable_im[k] = new JTextField(20);
            variable_im[k].setText("" + s.fns.zenex_im[k]);

            var_panels[k].add(new JLabel("" + (char)('A' + k)));
            var_panels[k].add(new JLabel(" Re: "));
            var_panels[k].add(variable_re[k]);
            var_panels[k].add(new JLabel(" Im: "));
            var_panels[k].add(variable_im[k]);
        }

        JLabel func = new JLabel();
        func.setIcon(MainWindow.getIcon("zenex.png"));


        Object[] poly_poly = {
            " ",
                "Insert the function parameters,",
                func,
                " ",
            var_panels[0],
            var_panels[1],
            var_panels[2],
            var_panels[3],
            var_panels[4],
            var_panels[5],
            var_panels[6],
            " ",};

        optionPane = new JOptionPane(poly_poly, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        optionPane.addPropertyChangeListener(
                e -> {
                    String prop = e.getPropertyName();

                    if (isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {

                        Object value = optionPane.getValue();

                        if (value == JOptionPane.UNINITIALIZED_VALUE) {
                            //ignore reset
                            return;
                        }

                        //Reset the JOptionPane's value.
                        //If you don't do this, then if the user
                        //presses the same button next time, no
                        //property change event will be fired.
                        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

                        if ((Integer) value == JOptionPane.CANCEL_OPTION || (Integer) value == JOptionPane.NO_OPTION || (Integer) value == JOptionPane.CLOSED_OPTION) {
                            fractal_functions[oldSelected].setSelected(true);
                            s.fns.function = oldSelected;
                            dispose();
                            return;
                        }

                        try {
                            double[] temp_coef = new double[variable_re.length];
                            double[] temp_coef_im = new double[variable_im.length];

                            for (int k = 0; k < variable_re.length; k++) {
                                temp_coef[k] = Double.parseDouble(variable_re[k].getText());
                                temp_coef_im[k] = Double.parseDouble(variable_im[k].getText());
                            }

                            s.fns.zenex_re = temp_coef;
                            s.fns.zenex_im = temp_coef_im;


                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        ptra.optionsEnableShortcut();
                        dispose();
                        ptra.setFunctionPost(oldSelected, wasMagnetType, wasConvergingType, wasSimpleType, wasMagneticPendulumType, wasEscapingOrConvergingType, wasMagnetPatakiType);
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

}
