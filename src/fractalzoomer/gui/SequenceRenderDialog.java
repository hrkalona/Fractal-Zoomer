
package fractalzoomer.gui;

import com.fasterxml.jackson.databind.ObjectMapper;
import fractalzoomer.core.numerics.MyApfloat;
import fractalzoomer.functions.Fractal;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.MinimalRendererWindow;
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
import java.io.IOException;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class SequenceRenderDialog extends JDialog {

    private MinimalRendererWindow ptra;
    private JOptionPane optionPane;

    private final JScrollPane scrollPane;
    private JTextArea field_size;
    private JTextArea end_size;

    private JTextArea override_max_iterations_size;
    private JComboBox<String> zoooming_mode;

    private MyJSpinner fieldZoom;

    private MyJSpinner fieldRotation;

    private MyJSpinner fieldColorCycling;

    private MyJSpinner fieldGradientColorCycling;

    private MyJSpinner fieldLightCycling;

    private MyJSpinner fieldSlopesCycling;
    private MyJSpinner fieldBlinnLightCycling;

    private MyJSpinner filedBumpLightCycling;

    private MyJSpinner fieldZoomEveryNFrame;

    private JTextField startAtIndex;

    private JTextField stopAfterNSteps;

    private JTextField indexOffset;
    private JTextField namePattern;

    private JCheckBox saveSettingsOnEachStep;
    private JCheckBox saveReference;
    private JCheckBox saveKFB;

    private JTextField overrideMaxIterations;
    private JTextField loadReferenceFilePath;

    public SequenceRenderDialog(MinimalRendererWindow ptr, Settings s, ZoomSequenceSettings zss) {

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

        reset.addActionListener(e -> reset(s));

        JPanel action_panel = new JPanel();
        action_panel.setLayout(new FlowLayout());
        action_panel.add(load);
        action_panel.add(reset);

        namePattern = new JTextField();
        namePattern.setText(zss.file_name_pattern);

        JLabel start_label = new JLabel("Start Size:");
        JLabel end_label = new JLabel("End Size:");

        zoooming_mode = new JComboBox<>(new String[] {"Zoom-In", "Zoom-Out"});
        zoooming_mode.setSelectedIndex(zss.zooming_mode);
        zoooming_mode.setFocusable(false);
        zoooming_mode.setToolTipText("Sets zooming mode.");
        zoooming_mode.setPreferredSize(new Dimension(150, 20));
        zoooming_mode.addActionListener(e -> {
            if(zoooming_mode.getSelectedIndex() == 0) {
                start_label.setText("Start Size:");
                end_label.setText("End Size:");
            }
            else {
                start_label.setText("End Size:");
                end_label.setText("Start Size:");
            }
        });


        field_size = new JTextArea(6, 50);
        field_size.setLineWrap(true);
        field_size.setFont(TEMPLATE_TFIELD.getFont());
        field_size.setText("" + zss.startSize);

        JScrollPane scrollSize = new JScrollPane (field_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(field_size.getInputMap());
        disableKeys(scrollSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        if(zss.endSize == null) {
            zss.setEndSize(s.size);
        }

        end_size = new JTextArea(6, 50);
        end_size.setLineWrap(true);
        end_size.setFont(TEMPLATE_TFIELD.getFont());
        end_size.setText("" + zss.endSize);

        JScrollPane scrollSettingsSize = new JScrollPane (end_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(end_size.getInputMap());
        disableKeys(scrollSettingsSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        SwingUtilities.invokeLater(() -> {
            scrollSize.getVerticalScrollBar().setValue(0);
            scrollSettingsSize.getVerticalScrollBar().setValue(0);
        });

        JButton magnification = new MyButton("Magnification/Zoom");
        magnification.setToolTipText("An alternative size option.");
        magnification.setFocusable(false);
        magnification.setIcon(MainWindow.getIcon("magnification.png"));

        magnification.addActionListener(e -> new SequenceMagnificationDialog(ptr, s, field_size, end_size));

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());
        button_panel.add(magnification);

        fieldZoom = new MyJSpinner(new SpinnerNumberModel(zss.zoom_factor, 1.001, 32.0, 0.5));

        fieldRotation = new MyJSpinner(new SpinnerNumberModel(zss.rotation_adjusting_value, -90.0, 90.0, 1));

        fieldColorCycling = new MyJSpinner(new SpinnerNumberModel(zss.color_cycling_adjusting_value, -10000, 10000, 1));

        fieldGradientColorCycling = new MyJSpinner(new SpinnerNumberModel(zss.gradient_color_cycling_adjusting_value, -10000, 10000, 1));

        fieldLightCycling = new MyJSpinner(new SpinnerNumberModel(zss.light_direction_adjusting_value, -90.0, 90.0, 1));

        fieldSlopesCycling = new MyJSpinner(new SpinnerNumberModel(zss.slopes_direction_adjusting_value, -90.0, 90.0, 1));

        fieldBlinnLightCycling = new MyJSpinner(new SpinnerNumberModel(zss.blinn_light_direction_adjusting_value, -90.0, 90.0, 1));

        filedBumpLightCycling = new MyJSpinner(new SpinnerNumberModel(zss.bump_direction_adjusting_value, -90.0, 90.0, 1));

        fieldZoomEveryNFrame = new MyJSpinner(new SpinnerNumberModel(zss.zoom_every_n_frame, 1, 20, 1));

        startAtIndex = new JTextField();
        startAtIndex.setText("" + zss.startAtSequenceIndex);

        indexOffset = new JTextField();
        indexOffset.setText("" + zss.sequenceIndexOffset);

        stopAfterNSteps = new JTextField();
        stopAfterNSteps.setText("" + zss.stop_after_n_steps);

        overrideMaxIterations = new JTextField();
        overrideMaxIterations.setText("" + zss.override_max_iterations);

        override_max_iterations_size = new JTextArea(6, 50);
        override_max_iterations_size.setLineWrap(true);
        override_max_iterations_size.setFont(TEMPLATE_TFIELD.getFont());
        override_max_iterations_size.setText("" + zss.overrideMaxIterationsSizeLimit);
        //Todo add magnigification?

        saveSettingsOnEachStep = new JCheckBox("Save Settings on Each Step");
        saveSettingsOnEachStep.setFocusable(false);
        saveSettingsOnEachStep.setToolTipText("Creates a .fzs file on each zoom sequence step.");
        saveSettingsOnEachStep.setSelected(zss.saveSettingsOnEachStep);

        saveKFB = new JCheckBox("Save KFB on Each Step");
        saveKFB.setFocusable(false);
        saveKFB.setToolTipText("Creates a .kfb file on each zoom sequence step with the raw iteration data.");
        saveKFB.setSelected(zss.saveKFB);

        saveReference = new JCheckBox("Save Reference");
        saveReference.setFocusable(false);
        saveReference.setToolTipText("Saves the reference orbit.");
        saveReference.setSelected(zss.saveReference);

        loadReferenceFilePath = new JTextField(40);
        loadReferenceFilePath.setText(zss.loadReferenceFilePath);
        loadReferenceFilePath.setEditable(false);

        MyButton selectReference = new MyButton("Load Reference");
        selectReference.setFocusable(false);
        selectReference.setToolTipText("Loads the reference data");
        selectReference.setIcon(MainWindow.getIcon("load_small.png"));

        selectReference.addActionListener(e -> loadReference(loadReferenceFilePath));

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        savePanel.add(saveSettingsOnEachStep);
        savePanel.add(saveKFB);
        savePanel.add(saveReference);


        JPanel loadRefPanel = new JPanel();
        loadRefPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        loadRefPanel.add(loadReferenceFilePath);
        loadRefPanel.add(selectReference);

        JScrollPane scrollOverrideMaxIterSize = new JScrollPane (override_max_iterations_size,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        disableKeys(override_max_iterations_size.getInputMap());
        disableKeys(scrollOverrideMaxIterSize.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT));

        Object[] message = {
            " ",
                action_panel,
                " ",
                "Set the Zooming Mode.",
                "Zooming Mode:",
                zoooming_mode,
                " ",
                "Set the start and end size of the sequence.",
                start_label,
                scrollSize,
                end_label,
                scrollSettingsSize,
                button_panel,
                " ",
                "Set the zoom sequence parameters.",
                "Zooming Factor:",
                fieldZoom,
                "Zoom Every N Frame:",
                fieldZoomEveryNFrame,
                "Rotation Adjusting Value in degrees:",
                fieldRotation,
                "Color Cycling Adjusting Value:",
                fieldColorCycling,
                "Gradient Color Cycling Adjusting Value:",
                fieldGradientColorCycling,
                "Light Direction Adjusting Value in degrees (When Light is Enabled):",
                fieldLightCycling,
                "Bump Mapping Light Direction Adjusting Value in degrees (When Bump Mapping is Enabled):",
                filedBumpLightCycling,
                "Slope Direction Adjusting Value in degrees (When Slopes is Enabled):",
                fieldSlopesCycling,
                "Blinn-Phong Light Polar Angle Adjusting Value in degrees (When Blinn-Phong Light is Enabled):",
                fieldBlinnLightCycling,
                " ",
                "Set the max iterations override.",
                "Override Max Iterations when size > than:",
                scrollOverrideMaxIterSize,
                "Max Iterations Override:",
                overrideMaxIterations,
                " ",
                "Set the zoom sequence index parameters.",
                "Skip Rendering before Sequence Index:",
                startAtIndex,
                "Sequence Index Offset:",
                indexOffset,
                "Stop Rendering After N Steps:",
                stopAfterNSteps,
                " ",
                "Set the file name pattern (E.g. zoom%08d).",
                "File Name Pattern:",
                namePattern,
                " ",
                "Reference Data:",
                loadRefPanel,
                " ",
                savePanel,

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
                            long stopAfterN = Long.parseLong(stopAfterNSteps.getText());
                            long indexOffs = Long.parseLong(indexOffset.getText());
                            int max_iterations_override = Integer.parseInt(overrideMaxIterations.getText());
                            double tempBlinnLight = Double.parseDouble(fieldBlinnLightCycling.getText());

                            if(MyApfloat.setAutomaticPrecision) {
                                long precision = MyApfloat.getAutomaticPrecision(new String[]{field_size.getText(), end_size.getText(), override_max_iterations_size.getText()}, new boolean[]{true, true, true}, s.fns.function);
                                if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                                    Fractal.clearReferences(true, true);
                                    MyApfloat.setPrecision(precision, s);
                                }
                            }

                            Apfloat tempStartSize = new MyApfloat(field_size.getText());
                            Apfloat tempEndSize = new MyApfloat(end_size.getText());
                            Apfloat tempOverrideMaxIterSize = new MyApfloat(override_max_iterations_size.getText());

                            if(tempStartSize.compareTo(MyApfloat.ZERO) <= 0 || tempEndSize.compareTo(MyApfloat.ZERO) <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The start and end sizes must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempStartSize.compareTo(tempEndSize) <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The value entered in the second size text-field must be greater than the size in the first size text-field.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(tempOverrideMaxIterSize.compareTo(MyApfloat.ZERO) < 0) {
                                JOptionPane.showMessageDialog(ptra, "The max iterations size override limit must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(max_iterations_override < 0) {
                                JOptionPane.showMessageDialog(ptra, "The max iterations override must be greater or equal to 0.", "Error!", JOptionPane.ERROR_MESSAGE);
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

                            if(tempBlinnLight > 90 || tempBlinnLight < -90) {
                                JOptionPane.showMessageDialog(ptra, "The blinn-phong light polar angle adjusting value must be a number in the range [-90, 90] degrees.", "Error!", JOptionPane.ERROR_MESSAGE);
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

                            if(indexOffs < 0) {
                                JOptionPane.showMessageDialog(ptra, "The sequence index offset value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(stopAfterN < 0) {
                                JOptionPane.showMessageDialog(ptra, "The stopping value must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            zss.zooming_mode = zoooming_mode.getSelectedIndex();
                            zss.zoom_factor = tempZoomFactor;
                            zss.rotation_adjusting_value = tempRotation;
                            zss.color_cycling_adjusting_value = tempColorCycling;
                            zss.light_direction_adjusting_value = tempLight;
                            zss.bump_direction_adjusting_value = tempBumpLight;
                            zss.blinn_light_direction_adjusting_value = tempBlinnLight;
                            zss.zoom_every_n_frame = tempZoomNFrame;
                            zss.gradient_color_cycling_adjusting_value = tempGradientColorCycling;
                            zss.startAtSequenceIndex = startAtIdx;
                            zss.slopes_direction_adjusting_value = tempSlopes;
                            zss.setStartSize(tempStartSize);
                            zss.setEndSize(tempEndSize);
                            zss.setOverrideMaxIterationsSizeLimit(tempOverrideMaxIterSize);
                            zss.file_name_pattern = namePattern.getText();
                            zss.stop_after_n_steps = stopAfterN;
                            zss.override_max_iterations = max_iterations_override;
                            zss.sequenceIndexOffset = indexOffs;
                            zss.saveSettingsOnEachStep = saveSettingsOnEachStep.isSelected();
                            zss.saveReference = saveReference.isSelected();
                            zss.loadReferenceFilePath = loadReferenceFilePath.getText();
                            zss.saveKFB = saveKFB.isSelected();

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

        JFileChooser file_chooser = new JFileChooser(MinimalRendererWindow.outputDirectory);

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

                if(zss.endSizeStr == null) {
                    zss.endSizeStr = s.size.toString();
                }

                if(MyApfloat.setAutomaticPrecision) {
                    long precision = MyApfloat.getAutomaticPrecision(new String[]{zss.sizeStr, zss.endSizeStr, zss.overrideMaxIterationsSizeLimitStr}, new boolean[]{true, true, true}, s.fns.function);
                    precision = Math.max(MyApfloat.precision, precision);
                    if (MyApfloat.shouldSetPrecision(precision, MyApfloat.alwaysCheckForDecrease, s.fns.function)) {
                        Fractal.clearReferences(true, true);
                        MyApfloat.setPrecision(precision, s);
                    }
                }

                zss.setStartSize(new MyApfloat(zss.sizeStr));
                zss.setEndSize(new MyApfloat(zss.endSizeStr));
                zss.setOverrideMaxIterationsSizeLimit(new MyApfloat(zss.overrideMaxIterationsSizeLimitStr));

                setFromSettings(zss, s);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while loading the file.", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void loadReference(JTextField textField) {

        JFileChooser file_chooser = new JFileChooser(MinimalRendererWindow.outputDirectory);

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("Reference Data (*.ref)", "ref"));

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(this, "Load Reference");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = file_chooser.getSelectedFile();
            if (file != null) {
                textField.setText(file.getAbsolutePath());
            }
        }

    }

    private void reset(Settings s) {

        setFromSettings(new ZoomSequenceSettings(), s);

    }

    private void setFromSettings(ZoomSequenceSettings zss, Settings s) {
        zoooming_mode.setSelectedIndex(zss.zooming_mode);

        if(zss.endSize == null) {
            zss.setEndSize(s.size);
        }

        field_size.setText("" + zss.startSize);
        end_size.setText("" + zss.endSize);

        fieldZoom.setValue(zss.zoom_factor);
        fieldRotation.setValue(zss.rotation_adjusting_value);
        fieldColorCycling.setValue(zss.color_cycling_adjusting_value);
        fieldGradientColorCycling.setValue(zss.gradient_color_cycling_adjusting_value);
        fieldLightCycling.setValue(zss.light_direction_adjusting_value);
        fieldSlopesCycling.setValue(zss.slopes_direction_adjusting_value);
        fieldBlinnLightCycling.setValue(zss.blinn_light_direction_adjusting_value);
        filedBumpLightCycling.setValue(zss.bump_direction_adjusting_value);
        fieldZoomEveryNFrame.setValue(zss.zoom_every_n_frame);
        startAtIndex.setText("" + zss.startAtSequenceIndex);
        stopAfterNSteps.setText("" + zss.stop_after_n_steps);
        indexOffset.setText("" + zss.sequenceIndexOffset);
        namePattern.setText(zss.file_name_pattern);
        override_max_iterations_size.setText("" + zss.overrideMaxIterationsSizeLimit);
        overrideMaxIterations.setText("" + zss.override_max_iterations);
        saveSettingsOnEachStep.setSelected(zss.saveSettingsOnEachStep);
        saveReference.setSelected(zss.saveReference);
        loadReferenceFilePath.setText(zss.loadReferenceFilePath);
        saveKFB.setSelected(zss.saveKFB);
    }

}
