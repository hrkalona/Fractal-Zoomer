
package fractalzoomer.gui;

import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.MinimalRendererWindow;
import fractalzoomer.main.app_settings.Settings;
import org.apfloat.Apfloat;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class SequenceMagnificationDialog extends JDialog {

    private MinimalRendererWindow ptra;
    private JOptionPane optionPane;

    public SequenceMagnificationDialog(MinimalRendererWindow ptr, Settings s, JTextArea field_start_size, JTextArea field_end_size) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Magnification/Zoom");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        Apfloat tempStartSize;
        Apfloat tempEndSize;
        try {

            if(MyApfloat.setAutomaticPrecision) {
                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_start_size.getText(), field_end_size.getText()}, new boolean[] {true, true}, s.fns.function);
                precision = Math.max(MyApfloat.precision, precision);
                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                    Fractal.clearReferences(true, true);
                    MyApfloat.setPrecision(precision, s);
                }
            }

            tempStartSize = new MyApfloat(field_start_size.getText());
            tempEndSize = new MyApfloat(field_end_size.getText());
        } catch (Exception ex) {
            tempStartSize = s.size;
            tempEndSize = s.size;
        }

        Apfloat magnificationStartVal = MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, tempStartSize);
        Apfloat magnigificationEndVal = MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, tempEndSize);


        JTextArea magnificationStart = new JTextArea(6, 50);
        magnificationStart.setFont(TEMPLATE_TFIELD.getFont());
        magnificationStart.setLineWrap(true);

        JScrollPane magnScroll = new JScrollPane (magnificationStart,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(magnificationStart.getInputMap());
        CenterSizeDialog.disableKeys(magnScroll.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        magnificationStart.setText("" + magnificationStartVal);

        JTextArea magnificationEnd = new JTextArea(6, 50);
        magnificationEnd.setFont(TEMPLATE_TFIELD.getFont());
        magnificationEnd.setLineWrap(true);

        JScrollPane magnScroll2 = new JScrollPane (magnificationEnd,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        CenterSizeDialog.disableKeys(magnificationEnd.getInputMap());
        CenterSizeDialog.disableKeys(magnScroll2.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        magnificationEnd.setText("" + magnigificationEndVal);

        SwingUtilities.invokeLater(() -> {
            magnScroll.getVerticalScrollBar().setValue(0);
            magnScroll2.getVerticalScrollBar().setValue(0);
        });

        Object[] message = {
            " ",
            "Set the magnification/zoom.",
            "Start Magnification/Zoom:",
                magnScroll,
                "End Magnification/Zoom:",
                magnScroll2,
            " ",
            };

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

                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{magnificationStart.getText(), magnificationEnd.getText()}, new boolean[] {true, true}, s.fns.function);
                                precision = Math.max(MyApfloat.precision, precision);
                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                                    Fractal.clearReferences(true, true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempMagn = new MyApfloat(magnificationStart.getText());
                            Apfloat tempSettingsMagn = new MyApfloat(magnificationEnd.getText());
                            field_start_size.setText("" + MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, tempMagn));
                            field_end_size.setText("" + MyApfloat.fp.divide(Constants.DEFAULT_MAGNIFICATION, tempSettingsMagn));
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
