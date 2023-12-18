/*
 * Copyright (C) 2020 hrkalona2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fractalzoomer.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import fractalzoomer.core.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.main.app_settings.ZoomSequenceSettings;
import org.apfloat.Apfloat;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class SequenceRenderDialog extends JDialog {

    private ImageExpanderWindow ptra;
    private JOptionPane optionPane;

    private final JScrollPane scrollPane;
    private JTextArea field_size;
    private JComboBox<String> zoooming_mode;

    private MyJSpinner fieldZoom;

    private MyJSpinner fieldRotation;

    private MyJSpinner fieldColorCycling;

    private MyJSpinner fieldGradientColorCycling;

    private MyJSpinner fieldLightCycling;

    private MyJSpinner fieldSlopesCycling;

    private MyJSpinner filedBumpLightCycling;

    private MyJSpinner fieldZoomEveryNFrame;

    private JCheckBox flipIndex;

    private JTextField startAtIndex;
    private JTextField aspectRatio;

    public SequenceRenderDialog(ImageExpanderWindow ptr, Settings s, ZoomSequenceSettings zss) {

        super(ptr);
        
        ptra = ptr;

        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(700, 700));

        setTitle("Zoom Sequence Render");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JButton load = new MyButton("Load");
        load.setToolTipText("Loads some saved zoom sequence settings.");
        load.setFocusable(false);
        load.setIcon(MainWindow.getIcon("load_small.png"));

        load.addActionListener(e -> load(s));


        JButton reset = new MyButton("Reset");
        reset.setToolTipText("Resets the zoom sequence settings.");
        reset.setFocusable(false);
        reset.setIcon(MainWindow.getIcon("reset_small.png"));

        reset.addActionListener(e -> reset());

        JPanel action_panel = new JPanel();
        action_panel.setLayout(new FlowLayout());
        action_panel.add(load);
        action_panel.add(reset);

        zoooming_mode = new JComboBox<>(new String[] {"Zoom-In", "Zoom-Out"});
        zoooming_mode.setSelectedIndex(zss.zooming_mode);
        zoooming_mode.setFocusable(false);
        zoooming_mode.setToolTipText("Sets zooming mode.");
        zoooming_mode.setPreferredSize(new Dimension(150, 20));



        field_size = new JTextArea(3, 50);
        field_size.setLineWrap(true);
        field_size.setFont(TEMPLATE_TFIELD.getFont());
        field_size.setText("" + zss.size);

        JScrollPane scrollSize = new JScrollPane (field_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(field_size.getInputMap());
        disableKeys(scrollSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));


        JTextArea settings_size = new JTextArea(3, 50);
        settings_size.setLineWrap(true);
        settings_size.setFont(TEMPLATE_TFIELD.getFont());
        settings_size.setEditable(false);
        settings_size.setForeground(Color.GRAY);
        settings_size.setText("" + s.size);

        JScrollPane scrollSettingsSize = new JScrollPane (settings_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(settings_size.getInputMap());
        disableKeys(scrollSettingsSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        SwingUtilities.invokeLater(() -> {
            scrollSize.getVerticalScrollBar().setValue(0);
            scrollSettingsSize.getVerticalScrollBar().setValue(0);
        });


        fieldZoom = new MyJSpinner(new SpinnerNumberModel(zss.zoom_factor, 1.001, 32.0, 0.5));

        fieldRotation = new MyJSpinner(new SpinnerNumberModel(zss.rotation_adjusting_value, -90.0, 90.0, 1));

        fieldColorCycling = new MyJSpinner(new SpinnerNumberModel(zss.color_cycling_adjusting_value, -10000, 10000, 1));

        fieldGradientColorCycling = new MyJSpinner(new SpinnerNumberModel(zss.gradient_color_cycling_adjusting_value, -10000, 10000, 1));

        fieldLightCycling = new MyJSpinner(new SpinnerNumberModel(zss.light_direction_adjusting_value, -90.0, 90.0, 1));

        fieldSlopesCycling = new MyJSpinner(new SpinnerNumberModel(zss.slopes_direction_adjusting_value, -90.0, 90.0, 1));

        filedBumpLightCycling = new MyJSpinner(new SpinnerNumberModel(zss.bump_direction_adjusting_value, -90.0, 90.0, 1));

        fieldZoomEveryNFrame = new MyJSpinner(new SpinnerNumberModel(zss.zoom_every_n_frame, 1, 20, 1));

        flipIndex = new JCheckBox("Flip Sequence Indexing");
        flipIndex.setFocusable(false);
        flipIndex.setSelected(zss.flipSequenceIndexing);
        flipIndex.setToolTipText("Changes the indexing of the name to start backwards.");

        startAtIndex = new JTextField();
        startAtIndex.setText("" + zss.startAtSequenceIndex);

        aspectRatio = new JTextField();
        aspectRatio.setText("" + zss.aspect_ratio);


        Object[] message = {
            " ",
                action_panel,
                " ",
                "Set the Zooming Mode.",
                "Zooming Mode:",
                zoooming_mode,
                " ",
                "Set the Initial (Zoom-In) or End (Zoom-Out) size of the sequence.",
                "Size:",
                scrollSize,
                "Settings Size:",
                scrollSettingsSize,
                " ",
                "Set the zoom sequence parameters.",
                "Zooming Factor:",
                fieldZoom,
                "Zoom Every N Frame:",
                fieldZoomEveryNFrame,
                "Rotation Adjusting Value (in degrees):",
                fieldRotation,
                "Color Cycling Adjusting Value:",
                fieldColorCycling,
                "Gradient Color Cycling Adjusting Value:",
                fieldGradientColorCycling,
                "Light Direction Adjusting Value (When Light is Enabled):",
                fieldLightCycling,
                "Bump Mapping Light Direction Adjusting Value (When Bump Mapping is Enabled):",
                filedBumpLightCycling,
                "Slope Direction Adjusting Value (When Slopes is Enabled):",
                fieldSlopesCycling,
                " ",
                "Set the zoom sequence index parameters.",
                flipIndex,
                "Start Rendering at Sequence Index:",
                startAtIndex,
                " ",
                "Set the output image aspect ratio.",
                "Aspect Ratio:",
                aspectRatio,

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
                            double tempZoomFactor = Double.parseDouble(fieldZoom.getText());
                            double tempRotation = Double.parseDouble(fieldRotation.getText());
                            int tempColorCycling = Integer.parseInt(fieldColorCycling.getText());
                            double tempLight = Double.parseDouble(fieldLightCycling.getText());
                            double tempBumpLight = Double.parseDouble(filedBumpLightCycling.getText());
                            double tempSlopes = Double.parseDouble(fieldSlopesCycling.getText());
                            int tempZoomNFrame = Integer.parseInt(fieldZoomEveryNFrame.getText());
                            int tempGradientColorCycling = Integer.parseInt(fieldGradientColorCycling.getText());
                            long startAtIdx = Long.parseLong(startAtIndex.getText());
                            double tempAspectRatio = Double.parseDouble(aspectRatio.getText());

                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_size.getText(), s.size.toString()}, new boolean[]{true, true}, s.fns.function);

                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                                    Fractal.clearReferences(true, true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempSize = new MyApfloat(field_size.getText());

                            if(tempSize.compareTo(s.size) <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The value entered in the size must be greater than the settings size.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (tempZoomFactor < 1.001 || tempZoomFactor > 32) {
                                JOptionPane.showMessageDialog(ptra, "Zooming factor must be in the range [1.001, 32].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempColorCycling < -10000 || tempColorCycling > 10000) {
                                JOptionPane.showMessageDialog(ptra, "The color cycling adjusting value must be in the range [-10000, 10000].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempGradientColorCycling < -10000 || tempGradientColorCycling > 10000) {
                                JOptionPane.showMessageDialog(ptra, "The gradient color cycling adjusting value must be in the range [-10000, 10000].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempRotation > 90 || tempRotation < -90) {
                                JOptionPane.showMessageDialog(ptra, "The rotation adjusting value must be a number in the range [-90, 90] degrees.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempLight > 90 || tempLight < -90) {
                                JOptionPane.showMessageDialog(ptra, "The light direction adjusting value must be a number in the range [-90, 90] degrees.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempSlopes > 90 || tempSlopes < -90) {
                                JOptionPane.showMessageDialog(ptra, "The slope direction adjusting value must be a number in the range [-90, 90] degrees.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempBumpLight > 90 || tempBumpLight < -90) {
                                JOptionPane.showMessageDialog(ptra, "The bump mapping light direction adjusting value must be a number in the range [-90, 90] degrees.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempZoomNFrame < 1 || tempZoomNFrame > 20) {
                                JOptionPane.showMessageDialog(ptra, "The \"zoom every N frame\" value must be a number in the range [1, 20].", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(startAtIdx < 0) {
                                JOptionPane.showMessageDialog(ptra, "The starting index value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempAspectRatio <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The aspect ratio must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            zss.zooming_mode = zoooming_mode.getSelectedIndex();
                            zss.zoom_factor = tempZoomFactor;
                            zss.rotation_adjusting_value = tempRotation;
                            zss.color_cycling_adjusting_value = tempColorCycling;
                            zss.light_direction_adjusting_value = tempLight;
                            zss.bump_direction_adjusting_value = tempBumpLight;
                            zss.zoom_every_n_frame = tempZoomNFrame;
                            zss.gradient_color_cycling_adjusting_value = tempGradientColorCycling;
                            zss.flipSequenceIndexing = flipIndex.isSelected();
                            zss.startAtSequenceIndex = startAtIdx;
                            zss.slopes_direction_adjusting_value = tempSlopes;
                            zss.size = tempSize;
                            zss.sizeStr = tempSize.toString();
                            zss.aspect_ratio = tempAspectRatio;

                            ptr.startSequenceRender();

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                    }
                });

        //Make this dialog display it.
        scrollPane.setViewportView(optionPane);
        setContentPane(scrollPane);

        pack();

        setResizable(true);
        setLocation((int) (ptra.getLocation().getX() + ptra.getSize().getWidth() / 2) - (getWidth() / 2), (int) (ptra.getLocation().getY() + ptra.getSize().getHeight() / 2) - (getHeight() / 2));
        setVisible(true);

    }

    public static void disableKeys(InputMap inputMap) {
        String[] keys = {"ENTER"};
        for (String key : keys) {
            inputMap.put(KeyStroke.getKeyStroke(key), "none");
        }
    }

    private void load(Settings s) {

        JFileChooser file_chooser = new JFileChooser(ImageExpanderWindow.outputDirectory);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Zoom Sequence Settings (*.json)", "json"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(this, "Load Zoom Sequence Settings");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ZoomSequenceSettings zss = objectMapper.readValue(file, ZoomSequenceSettings.class);

                if(MyApfloat.setAutomaticPrecision) {
                    long precision = MyApfloat.getAutomaticPrecision(new String[]{zss.sizeStr, s.size.toString()}, new boolean[]{true, true}, s.fns.function);

                    if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.setPrecision(precision, s);
                    }
                }

                zss.size = new MyApfloat(zss.sizeStr);

                setFromSettings(zss);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void reset() {

        setFromSettings(new ZoomSequenceSettings());

    }

    private void setFromSettings(ZoomSequenceSettings zss) {
        zoooming_mode.setSelectedIndex(zss.zooming_mode);

        field_size.setText("" + zss.size);

        fieldZoom.setValue(zss.zoom_factor);
        fieldRotation.setValue(zss.rotation_adjusting_value);
        fieldColorCycling.setValue(zss.color_cycling_adjusting_value);
        fieldGradientColorCycling.setValue(zss.gradient_color_cycling_adjusting_value);
        fieldLightCycling.setValue(zss.light_direction_adjusting_value);
        fieldSlopesCycling.setValue(zss.slopes_direction_adjusting_value);
        filedBumpLightCycling.setValue(zss.bump_direction_adjusting_value);
        fieldZoomEveryNFrame.setValue(zss.zoom_every_n_frame);
        flipIndex.setSelected(zss.flipSequenceIndexing);
        startAtIndex.setText("" + zss.startAtSequenceIndex);
        aspectRatio.setText("" + zss.aspect_ratio);
    }

}
