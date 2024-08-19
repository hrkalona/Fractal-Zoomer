
package fractalzoomer.gui;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class JitterDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public JitterDialog(MainWindow ptr, Settings s) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Jitter");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField jitter_seed_field = new JTextField();
        jitter_seed_field.setText("" + s.js.jitterSeed);

        JTextField jitter_scale_field = new JTextField();
        jitter_scale_field.setText("" + s.js.jitterScale);

        final JCheckBox enable_jitter = new JCheckBox("Jitter");
        enable_jitter.setSelected(s.js.enableJitter);
        enable_jitter.setFocusable(false);

        final JComboBox<String> jitter_method_combo = new JComboBox<>(Constants.jitterShape);
        jitter_method_combo.setSelectedIndex(s.js.jitterShape);
        jitter_method_combo.setFocusable(false);
        jitter_method_combo.setToolTipText("Sets jitter shape.");


        Object[] message = {
            " ",
                enable_jitter,
            " ",
            "Set jitter shape.",
            "Jitter Shape:", jitter_method_combo,
            " ",
            "Set the jitter seed.",
            "Jitter Seed:", jitter_seed_field,
            " ",
            "Set jitter scale.",
            "Jitter Scale:", jitter_scale_field,
            " "};

        optionPane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            dispose();
                            return;
                        }

                        try {
                            double temp = Double.parseDouble(jitter_scale_field.getText());
                            int temp2 = Integer.parseInt(jitter_seed_field.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The jitter scale must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The jitter seed must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.js.jitterSeed = temp2;
                            s.js.jitterScale = temp;
                            s.js.enableJitter = enable_jitter.isSelected();
                            s.js.jitterShape = jitter_method_combo.getSelectedIndex();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setJitterPost();
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
