
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
public class GenericCpAZpBCDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public GenericCpAZpBCDialog(MainWindow ptr, Settings s, int oldSelected, JRadioButtonMenuItem[] fractal_functions, boolean wasMagnetType, boolean wasConvergingType, boolean wasSimpleType, boolean wasMagneticPendulumType, boolean wasEscapingOrConvergingType, boolean wasMagnetPatakiType) {

        super(ptr);
        
        ptra = ptr;

        setTitle("z = (c^alpha) * (z^beta) + c");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JLabel func = new JLabel();
        func.setIcon(MainWindow.getIcon("cpazpbc.png"));

        JPanel alpha_panel = new JPanel();
        JLabel alpha_label = new JLabel();
        alpha_label.setIcon(MainWindow.getIcon("alpha.png"));
        MyJSpinner alpha_filed = new MyJSpinner(30, new SpinnerNumberModel(s.fns.gcps.alpha2, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        alpha_panel.setLayout(new FlowLayout());
        alpha_panel.add(alpha_label);
        alpha_panel.add(new JLabel(""));
        alpha_panel.add(alpha_filed);

        JPanel beta_panel = new JPanel();
        JLabel beta_label = new JLabel();
        beta_label.setIcon(MainWindow.getIcon("beta.png"));
        MyJSpinner beta_filed = new MyJSpinner(30, new SpinnerNumberModel(s.fns.gcps.beta2, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
        beta_panel.setLayout(new FlowLayout());
        beta_panel.add(beta_label);
        beta_panel.add(new JLabel(""));
        beta_panel.add(beta_filed);
        

        Object[] obj = {
            " ",
            "Insert the function parameters,",
            func,
            " ",
            alpha_panel,
            beta_panel,};

        optionPane = new JOptionPane(obj, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            double temp_alpha = Double.parseDouble(alpha_filed.getText());
                            double temp_beta = Double.parseDouble(beta_filed.getText());

                            s.fns.gcps.alpha2 = temp_alpha;
                            s.fns.gcps.beta2 = temp_beta;
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
