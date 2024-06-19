
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
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
public class FakeDistanceEstimationDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public FakeDistanceEstimationDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Fake Distance Estimation");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField fake_de_factor_field = new JTextField();
        fake_de_factor_field.setText("" + s.pps.fdes.fake_de_factor);

        final JCheckBox enable_fake_de = new JCheckBox("Fake Distance Estimation");
        enable_fake_de.setSelected(s.pps.fdes.fake_de);
        enable_fake_de.setFocusable(false);

        final JCheckBox invert_fake_de = new JCheckBox("Invert Coloring");
        invert_fake_de.setSelected(s.pps.fdes.inverse_fake_dem);
        invert_fake_de.setFocusable(false);

        final JComboBox<String> de_fade_method_combo = new JComboBox<>(Constants.FadeAlgs);
        de_fade_method_combo.setSelectedIndex(s.pps.fdes.fade_algorithm);
        de_fade_method_combo.setFocusable(false);
        de_fade_method_combo.setToolTipText("Sets the fading method.");

        Object[] message = {
            " ",
            enable_fake_de,
            " ",
            "Set the fake distance estimation factor.",
            "Fake Distance Estimation factor:", fake_de_factor_field,
                " ",
                "Set the fading algorithm.",
                "Fading algorithm:", de_fade_method_combo,
            " ",
            invert_fake_de,
            " ",};

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
                            double temp = Double.parseDouble(fake_de_factor_field.getText());

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "The fake distance estimation factor must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.fdes.fake_de = enable_fake_de.isSelected();
                            s.pps.fdes.fake_de_factor = temp;
                            s.pps.fdes.inverse_fake_dem = invert_fake_de.isSelected();
                            s.pps.fdes.fade_algorithm = de_fade_method_combo.getSelectedIndex();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_fake_de.isSelected() && !julia_map && !s.d3s.d3) {
                            JOptionPane.showMessageDialog(ptra, Constants.greedyWarning, "Warning!", JOptionPane.WARNING_MESSAGE);
                        }

                        ptra.setPostProcessingPost();
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
