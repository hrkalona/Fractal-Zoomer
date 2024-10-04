
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.MinimalRendererWindow;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ImageSizeDialog extends JDialog {

    private JFrame ptra;
    private JOptionPane optionPane;
    private double epsilon = 1e-2;
    private int max_denom = 1000;

    private DocumentListener field_listener;
    private DocumentListener field_listener2;
    private DocumentListener ar1_listener;
    private DocumentListener ar2_listener;
    private DocumentListener ar_fractional_listener;

    private ActionListener template_listener;
    private JTextField field;
    private JTextField field2;
    private JComboBox<String> templates;
    private JTextField ar1;
    private JTextField ar2;
    private JTextField ar_fractional;

    private JCheckBox keep_ar;

    public ImageSizeDialog(JFrame ptr, int image_width, int image_height, int imageFormat, float jpegQuality, int downscaleFactor) {
        
        super(ptr);

        ptra = ptr;

        setTitle("Image Size");
        setModal(true);
        if(ptr instanceof MainWindow) {
            setIconImage(MainWindow.getIcon("mandel2.png").getImage());
        }
        else {
            setIconImage(MainWindow.getIcon("mandelMinimalRenderer.png").getImage());
        }

        field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + image_width);

        field2 = new JTextField();
        field2.setText("" + image_height);

        int[] res = fareyApproximation(((double) image_width)/image_height, epsilon, max_denom);

        templates = new JComboBox<>(new String[] {"", "788x788 1:1", "1024x768 4:3", "1280x720 16:9", "1920x1080 16:9", "2560x1440 16:9", "3840x2160 16:9"});

        ar1 = new JTextField(5);
        ar1.setText("" + res[0]);
        ar2 = new JTextField(5);
        ar2.setText("" + res[1]);
        JPanel ar = new JPanel();
        ar.add(new JLabel("Aspect Ratio: "));
        ar.add(ar1);
        ar.add(new JLabel("/"));
        ar.add(ar2);

        ar1.setEnabled(false);
        ar2.setEnabled(false);

        ar_fractional = new JTextField(14);
        ar_fractional.setEnabled(false);
        ar_fractional.setText("" + ((double) image_width)/image_height);

        JPanel ar_fractional_panel = new JPanel();
        ar_fractional_panel.add(new JLabel("Aspect Ratio Decimal: "));
        ar_fractional_panel.add(ar_fractional);

        keep_ar = new JCheckBox("Keep Aspect Ratio");
        keep_ar.setFocusable(false);
        keep_ar.addActionListener(e -> {
            if(keep_ar.isSelected()) {
                removeListeners();
                field.setText("788");
                field2.setText("788");
                ar1.setText("1");
                ar2.setText("1");
                ar_fractional.setText("" + 1.0);
                ar1.setEnabled(true);
                ar2.setEnabled(true);
                ar_fractional.setEnabled(true);
                templates.setSelectedIndex(0);
                addListeners();
            }
            else {
                ar1.setEnabled(false);
                ar2.setEnabled(false);
                ar_fractional.setEnabled(false);
                templates.setSelectedIndex(0);
            }
        });

        if(keep_ar.isSelected()) {
            ar1.setEnabled(true);
            ar2.setEnabled(true);
            ar_fractional.setEnabled(true);
        }
        else {
            ar1.setEnabled(false);
            ar2.setEnabled(false);
            ar_fractional.setEnabled(false);
        }

        templates.setFocusable(false);

        template_listener = e -> {
            removeListeners();
            switch (templates.getSelectedIndex()) {
                case 1:
                    field.setText("788");
                    field2.setText("788");
                    ar1.setText("1");
                    ar2.setText("1");
                    ar_fractional.setText("" + 1.0);
                    break;
                case 2:
                    field.setText("1024");
                    field2.setText("768");
                    ar1.setText("4");
                    ar2.setText("3");
                    ar_fractional.setText("" + 4.0 / 3.0);
                    break;
                case 3:
                    field.setText("1280");
                    field2.setText("720");
                    ar1.setText("16");
                    ar2.setText("9");
                    ar_fractional.setText("" + 16.0 / 9.0);
                    break;
                case 4:
                    field.setText("1920");
                    field2.setText("1080");
                    ar1.setText("16");
                    ar2.setText("9");
                    ar_fractional.setText("" + 16.0 / 9.0);
                    break;
                case 5:
                    field.setText("2560");
                    field2.setText("1440");
                    ar1.setText("16");
                    ar2.setText("9");
                    ar_fractional.setText("" + 16.0 / 9.0);
                    break;
                case 6:
                    field.setText("3840");
                    field2.setText("2160");
                    ar1.setText("16");
                    ar2.setText("9");
                    ar_fractional.setText("" + 16.0 / 9.0);
                    break;
                case 0:
                    break;
            }
            addListeners();
        };

        templates.addActionListener(template_listener);

        ar1_listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                onArChange();
            }
            public void removeUpdate(DocumentEvent e) {
                onArChange();
            }
            public void insertUpdate(DocumentEvent e) {
                onArChange();
            }
        };

        ar2_listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                onArChange();
            }
            public void removeUpdate(DocumentEvent e) {
                onArChange();
            }
            public void insertUpdate(DocumentEvent e) {
                onArChange();
            }
        };

        field_listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                onField1Change();
            }
            public void removeUpdate(DocumentEvent e) {
                onField1Change();
            }
            public void insertUpdate(DocumentEvent e) {
                onField1Change();
            }
        };

        field_listener2 = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                onField2Change();
            }
            public void removeUpdate(DocumentEvent e) {
                onField2Change();
            }
            public void insertUpdate(DocumentEvent e) {
                onField2Change();
            }
        };

        ar_fractional_listener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                onArFractionalChange();
            }
            public void removeUpdate(DocumentEvent e) {
                onArFractionalChange();
            }
            public void insertUpdate(DocumentEvent e) {
                onArFractionalChange();
            }
        };

        ar1.getDocument().addDocumentListener(ar1_listener);
        ar2.getDocument().addDocumentListener(ar2_listener);
        field.getDocument().addDocumentListener(field_listener);
        field2.getDocument().addDocumentListener(field_listener2);
        ar_fractional.getDocument().addDocumentListener(ar_fractional_listener);

        final JComboBox<String> imageFormatOpt = new JComboBox<>(new String[] {"PNG", "JPEG", "BMP", "PPM", "PGM"});
        imageFormatOpt.setFocusable(false);
        imageFormatOpt.setSelectedIndex(imageFormat);
        imageFormatOpt.setVisible(ptr instanceof MinimalRendererWindow);

        JLabel out = new JLabel("Output Format:");
        out.setVisible(ptr instanceof MinimalRendererWindow);

        JLabel extraSpace = new JLabel(" ");
        extraSpace.setVisible(ptr instanceof MinimalRendererWindow);

        final JSlider jpeg_quality_opt = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        jpeg_quality_opt.setValue((int)(100 * jpegQuality));

        jpeg_quality_opt.setMajorTickSpacing(10);
        jpeg_quality_opt.setMinorTickSpacing(1);
        jpeg_quality_opt.setToolTipText("Sets the jpeg quality.");
        jpeg_quality_opt.setFocusable(false);
        jpeg_quality_opt.setPaintLabels(true);
        jpeg_quality_opt.setVisible(ptr instanceof MinimalRendererWindow);

        jpeg_quality_opt.setEnabled(imageFormatOpt.getSelectedIndex() == 1);
        imageFormatOpt.addActionListener(e -> jpeg_quality_opt.setEnabled(imageFormatOpt.getSelectedIndex() == 1));
        JLabel extraSpace2 = new JLabel(" ");
        JLabel jpegLabel = new JLabel("JPEG Quality:");
        jpegLabel.setVisible(ptr instanceof MinimalRendererWindow);
        extraSpace2.setVisible(ptr instanceof MinimalRendererWindow);

        JTextField downscaleFactorOpt = new JTextField();
        downscaleFactorOpt.setText("" + downscaleFactor);
        JLabel downscaleLabel = new JLabel("Downscale Factor:");
        downscaleLabel.setVisible(ptr instanceof MinimalRendererWindow);
        JLabel extraSpace3 = new JLabel(" ");
        extraSpace3.setVisible(ptr instanceof MinimalRendererWindow);
        downscaleFactorOpt.setVisible(ptr instanceof MinimalRendererWindow);

        Object[] message3 = {
            " ",
            "Your image size is " + image_width + "x" + image_height + " .\nInsert the new image size.",
                "Templates:",
                templates,
                " ",
                keep_ar,
                " ",
                ar,
                ar_fractional_panel,
                " ",
                "Width:",
                field,
                "Height:",
                field2,
                extraSpace,
                out,
                imageFormatOpt,
                extraSpace2,
                jpegLabel,
                jpeg_quality_opt,
                extraSpace3,
                downscaleLabel,
                downscaleFactorOpt,
        " "};

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

                        int temp = 0, temp2 = 0, temp3 = 0;
                        try {
                            temp = Integer.parseInt(field.getText());
                            temp2 = Integer.parseInt(field2.getText());
                            temp3 = Integer.parseInt(downscaleFactorOpt.getText());

                            if (temp < 1) {
                                JOptionPane.showMessageDialog(ptra, "Image width must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp > 46500) {
                                JOptionPane.showMessageDialog(ptra, "Image width must be less than than 46501.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 < 1) {
                                JOptionPane.showMessageDialog(ptra, "Image height must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 > 46500) {
                                JOptionPane.showMessageDialog(ptra, "Image height must be less than than 46501.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp3 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The downscale factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (temp3 > 25) {
                                JOptionPane.showMessageDialog(ptra, "The downscale factor must be lower than 26.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        if(ptr instanceof MainWindow) {
                            ((MainWindow)ptr).setSizeOfImagePost(temp, temp2);
                        }
                        else {
                            ((MinimalRendererWindow)ptra).setSizeOfImagePost(temp, temp2, imageFormatOpt.getSelectedIndex(), jpeg_quality_opt.getValue() / 100.f, temp3);
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

    public void onArChange() {
        if(!keep_ar.isSelected()) {
            return;
        }

        removeListeners();
        try {
            int width = Integer.parseInt(field.getText());
            int num = Integer.parseInt(ar1.getText());
            int denom = Integer.parseInt(ar2.getText());

            if(denom == 0 || num == 0) {
                throw new Exception();
            }

            field2.setText("" + (int)(width / (((double)num) / denom)));
            ar_fractional.setText("" + ((double)num) / denom);
        }
        catch (Exception ex) {
            field2.setText("");
            ar_fractional.setText("");
        }

        addListeners();
    }

    private void onArFractionalChange() {

        if(!keep_ar.isSelected()) {
            return;
        }

        removeListeners();

        int[] res = null;
        try {
            double val = Double.parseDouble(ar_fractional.getText());

            if(val == 0) {
                throw new Exception();
            }

            res = fareyApproximation(val, epsilon, max_denom);

            ar1.setText("" + res[0]);
            ar2.setText("" + res[1]);
        }
        catch (Exception ex) {
            ar1.setText("");
            ar2.setText("");
        }

        try {
            if(res == null) {
                throw new Exception();
            }
            int width = Integer.parseInt(field.getText());
            field2.setText("" + (int)(width / (((double)res[0]) / res[1])));
        }
        catch (Exception ex) {
            field2.setText("");
        }

        addListeners();

    }

    private void onField2Change() {
        removeListeners();
        if(!keep_ar.isSelected()) {
            try {
                int width = Integer.parseInt(field.getText());
                int height = Integer.parseInt(field2.getText());
                if(height == 0 || width == 0) {
                    throw new Exception();
                }
                int[] res = fareyApproximation(((double) width) / height, epsilon, max_denom);
                ar1.setText("" + res[0]);
                ar2.setText("" + res[1]);
                ar_fractional.setText("" + ((double) width)/height);
            }
            catch (Exception ex) {
                ar1.setText("");
                ar2.setText("");

                ar_fractional.setText("");
            }
        }
        else {
            try {
                int height = Integer.parseInt(field2.getText());
                int num = Integer.parseInt(ar1.getText());
                int denom = Integer.parseInt(ar2.getText());

                if(num == 0 || denom == 0) {
                    throw new Exception();
                }

                field.setText("" + (int)(height * (((double)num) / denom)));
            }
            catch (Exception ex) {
                field.setText("");
            }
        }
        addListeners();
    }

    private void onField1Change() {

        removeListeners();
        if(!keep_ar.isSelected()) {
            try {
                int width = Integer.parseInt(field.getText());
                int height = Integer.parseInt(field2.getText());
                if(height == 0 || width == 0) {
                    throw new Exception();
                }
                int[] res = fareyApproximation(((double) width) / height, epsilon, max_denom);
                ar1.setText("" + res[0]);
                ar2.setText("" + res[1]);
                ar_fractional.setText("" + ((double) width)/height);
            }
            catch (Exception ex) {
                ar1.setText("");
                ar2.setText("");
                ar_fractional.setText("");
            }
        }
        else {
            try {
                int width = Integer.parseInt(field.getText());
                int num = Integer.parseInt(ar1.getText());
                int denom = Integer.parseInt(ar2.getText());

                if(num == 0 || denom == 0) {
                    throw new Exception();
                }

                field2.setText("" + (int)(width / (((double)num) / denom)));
            }
            catch (Exception ex) {
                field2.setText("");
            }
        }
        addListeners();
    }

    private void removeListeners() {
        templates.removeActionListener(template_listener);
        field.getDocument().removeDocumentListener(field_listener);
        field2.getDocument().removeDocumentListener(field_listener2);
        ar1.getDocument().removeDocumentListener(ar1_listener);
        ar2.getDocument().removeDocumentListener(ar2_listener);
        ar_fractional.getDocument().removeDocumentListener(ar_fractional_listener);
    }

    private void addListeners() {
        templates.addActionListener(template_listener);
        field.getDocument().addDocumentListener(field_listener);
        field2.getDocument().addDocumentListener(field_listener2);
        ar1.getDocument().addDocumentListener(ar1_listener);
        ar2.getDocument().addDocumentListener(ar2_listener);
        ar_fractional.getDocument().addDocumentListener(ar_fractional_listener);
    }

    public static int[] fareyApproximation(double targetRatio, double epsilon, int maxDenominator) {
        double bestError = Double.MAX_VALUE;
        int bestNumerator = 0;
        int bestDenominator = 1;

        for (int denominator = 1; denominator <= maxDenominator; denominator++) {
            int numerator = (int) Math.round(targetRatio * denominator);
            double ratio = (double) numerator / denominator;
            double error = Math.abs(targetRatio - ratio);

            if(error < epsilon) {
                bestNumerator = numerator;
                bestDenominator = denominator;
                break;
            }

            if (error < bestError) {
                bestError = error;
                bestNumerator = numerator;
                bestDenominator = denominator;
            }
        }

        return new int[] {bestNumerator, bestDenominator};
    }

}
