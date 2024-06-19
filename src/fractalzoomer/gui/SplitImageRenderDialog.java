
package fractalzoomer.gui;

import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class SplitImageRenderDialog extends JDialog {

    private ImageExpanderWindow ptra;
    private JOptionPane optionPane;

    public SplitImageRenderDialog(ImageExpanderWindow ptr, int split_image_grid_dimension) {
        super(ptr);
        
        ptra = ptr;

        setTitle("Split Image Render");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());


        MyJSpinner field = new MyJSpinner(new SpinnerNumberModel(split_image_grid_dimension, 1, 1000, 1));
        field.addAncestorListener(new RequestFocusListener());

        Object[] message3 = {
            " ",
            "Insert the grid dimension N for the image split (NxN).",
                "Grid Dimension N:",
            field,
            " ",};

        optionPane = new JOptionPane(message3, JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, null, null);

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
                            int temp = Integer.parseInt(field.getText());

                            if (temp <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The grid dimension N must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            else if(temp > 1000) {
                                JOptionPane.showMessageDialog(ptra, "The grid dimension N must be lower than 1001.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            ptr.startSplitImageRender(temp);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
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
