
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona
 */
public class ContrastVariationDialog extends JDialog {

    private ColorPaletteEditorDialog ptra;
    private JOptionPane optionPane;

    public ContrastVariationDialog(ColorPaletteEditorDialog ptr, boolean contrast_variation, double range_min, double range_max, int contrast_method, double period, double offset, double merging) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Contrast Variation");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JComboBox<String> mapping = new JComboBox<>(new String[]  {"Lab", "OKLab"});
        mapping.setSelectedIndex(contrast_method);
        mapping.setFocusable(false);
        mapping.setToolTipText("Sets the contrast method.");

        JTextField period_field = new JTextField();
        period_field.addAncestorListener(new RequestFocusListener());
        period_field.setText("" + period);

        JTextField offset_field = new JTextField();
        offset_field.setText("" + offset);


        final JCheckBox enable_contrast_variation = new JCheckBox("Contrast Variation");
        enable_contrast_variation.setSelected(contrast_variation);
        enable_contrast_variation.setFocusable(false);

        RangeSlider scale_range = new RangeSlider(0, 100);
        scale_range.setValue((int)(range_min * 100));
        scale_range.setUpperValue((int)(range_max * 100));
        scale_range.setMajorTickSpacing(25);
        scale_range.setMinorTickSpacing(1);
        scale_range.setToolTipText("Sets the contrast range.");
        scale_range.setFocusable(false);
        scale_range.setPaintLabels(true);

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (merging * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the contrast merging percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);


        Object[] message = {
            " ",
            enable_contrast_variation,
            " ",
            "Set the contrast method.",
            "Method: ",
            mapping,
            " ",
                "Set the period and the offset of the oscillation.",
                "Period:",
                period_field,
                "Offset:",
                offset_field,
                " ",
            "Set the contrast range.",
            "Contrast Range:",
            scale_range,
                " ",
                "Set the contrast merging.",
                "Contrast Merging:",
                color_blend_opt,
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

                        boolean new_contrast_variation;
                        double new_range_min;
                        double new_range_max;
                        double new_period;
                        double new_offset;
                        int new_contrast_method;
                        double new_contrast_merging;

                        try {
                            double temp2 = Double.parseDouble(period_field.getText());
                            double temp3 = Double.parseDouble(offset_field.getText());

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The period must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            new_contrast_variation = enable_contrast_variation.isSelected();
                            new_range_max = scale_range.getUpperValue() / 100.0;
                            new_range_min = scale_range.getValue() / 100.0;
                            new_contrast_method = mapping.getSelectedIndex();
                            new_period = temp2;
                            new_offset = temp3;
                            new_contrast_merging = color_blend_opt.getValue() / 100.0;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        ptra.setContrastVariationPost(new_contrast_variation, new_range_min, new_range_max, new_contrast_method, new_period, new_offset, new_contrast_merging);
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
