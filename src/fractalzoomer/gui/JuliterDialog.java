package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class JuliterDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public JuliterDialog(MainWindow ptr, int juliterIterations, boolean juliterIncludeInitialIterations, Settings s) {

        super(ptr);

        ptra = ptr;

        setTitle("Juliter");
        setModal(true);
        setIconImage(getIcon("/fractalzoomer/icons/mandel2.png").getImage());

        final JCheckBox juliter = new JCheckBox("Juliter");
        juliter.setSelected(s.fns.juliter);
        juliter.setFocusable(false);
        juliter.setToolTipText("Enables juliter.");


        JTextField juliterIterationsfield = new JTextField();
        juliterIterationsfield.addAncestorListener(new RequestFocusListener());
        juliterIterationsfield.setText("" + juliterIterations);

        JCheckBox includePreStarting = new JCheckBox("Include pre-starting iterations");
        includePreStarting.setFocusable(false);
        includePreStarting.setSelected(juliterIncludeInitialIterations);

        Object[] message3 = {
                " ",
                juliter,
                " ",
                "Set the starting iterations of Juliter.",
                "Starting Iterations:",
                juliterIterationsfield,
                " ",
                includePreStarting,
                " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
            }
        });

        optionPane.addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent e) {
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

                            int temp = 0;
                            try {
                                temp = Integer.parseInt(juliterIterationsfield.getText());

                                if (temp < 0) {
                                    JOptionPane.showMessageDialog(ptra, "Juliter's starting iterations must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }


                            dispose();

                            if (!s.julia_map && !s.fns.julia && juliter.isSelected()) {
                                JOptionPane.showMessageDialog(ptra, "Juliter is an extension of Julia or Julia-Map.\nMake sure you have any of those options selected, in order for Juliter to be applied.", "Warning!", JOptionPane.WARNING_MESSAGE);
                            }
                            ptr.setJuliterPost(juliter.isSelected(), temp, includePreStarting.isSelected());
                        }
                    }
                });

        //Make this dialog display it.
        setContentPane(optionPane);

        pack();

        setResizable(false);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }
}
