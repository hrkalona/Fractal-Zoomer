
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
public class OffsetColoringDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;

    public OffsetColoringDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Offset Coloring");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JTextField offset_field = new JTextField();
        offset_field.setText("" + s.pps.ofs.post_process_offset);

        final JCheckBox enable_offset_coloring = new JCheckBox("Offset Coloring");
        enable_offset_coloring.setSelected(s.pps.ofs.offset_coloring);
        enable_offset_coloring.setFocusable(false);

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.ofs.of_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.ofs.of_noise_reducing_factor);

        JPanel blend_panel = new JPanel();

        final JComboBox<String> blend_modes = new JComboBox<>(Constants.blend_algorithms);
        blend_modes.setSelectedIndex(s.pps.ofs.of_color_blending);
        blend_modes.setFocusable(false);
        blend_modes.setToolTipText("Sets the blending mode mode.");

        final JCheckBox reverse_blending = new JCheckBox("Reverse Order of Colors");
        reverse_blending.setSelected(s.pps.ofs.of_reverse_color_blending);
        reverse_blending.setFocusable(false);
        reverse_blending.setToolTipText("Reverts the order of colors in the blending operation.");

        blend_panel.add(new JLabel("Blend Mode: "));
        blend_panel.add(blend_modes);
        blend_panel.add(reverse_blending);

        Object[] message = {
            " ",
            enable_offset_coloring,
            " ",
            "Set the coloring offset.",
            "Coloring Offset:", offset_field,
            " ",
                "Set the color blending options.",
                blend_panel,
                " ",
            "Set the color blending percentage.",
            "Color Blending:", color_blend_opt,
            " ",
            "Set the image noise reduction factor.",
            "Noise Reduction Factor:",
            noise_factor_field,
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
                            int temp = Integer.parseInt(offset_field.getText());
                            double temp2 = Double.parseDouble(noise_factor_field.getText());

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "The coloring offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.ofs.offset_coloring = enable_offset_coloring.isSelected();
                            s.pps.ofs.post_process_offset = temp;
                            s.pps.ofs.of_noise_reducing_factor = temp2;
                            s.pps.ofs.of_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.ofs.of_color_blending = blend_modes.getSelectedIndex();
                            s.pps.ofs.of_reverse_color_blending = reverse_blending.isSelected();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_offset_coloring.isSelected() && !julia_map && !s.d3s.d3) {
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
