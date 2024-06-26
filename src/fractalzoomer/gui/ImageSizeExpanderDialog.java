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

import fractalzoomer.main.ImageExpanderWindow;
import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author hrkalona2
 */
public class ImageSizeExpanderDialog extends JDialog {

    private ImageExpanderWindow ptra;
    private JOptionPane optionPane;

    public ImageSizeExpanderDialog(ImageExpanderWindow ptr, int image_width, int image_height, int imageFormat) {

        super(ptr);

        ptra = ptr;

        setTitle("Image Size");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandelExpander.png").getImage());

        JTextField field = new JTextField();
        field.addAncestorListener(new RequestFocusListener());
        field.setText("" + image_width);

        JTextField field2 = new JTextField();
        field2.setText("" + image_height);

        final JComboBox<String> templates = new JComboBox<>(new String[] {"", "788x788 1:1", "1024x768 4:3", "1280x720 16:9", "1920x1080 16:9", "2560x1440 16:9", "3840x2160 16:9"});

        templates.setFocusable(false);
        templates.addActionListener( e-> {
            switch (templates.getSelectedIndex()) {
                case 1:
                    field.setText("788");
                    field2.setText("788");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 2:
                    field.setText("1024");
                    field2.setText("768");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 3:
                    field.setText("1280");
                    field2.setText("720");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 4:
                    field.setText("1920");
                    field2.setText("1080");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 5:
                    field.setText("2560");
                    field2.setText("1440");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 6:
                    field.setText("3840");
                    field2.setText("2160");
                    field.setEnabled(false);
                    field2.setEnabled(false);
                    break;
                case 0:
                    field.setEnabled(true);
                    field2.setEnabled(true);
                    break;
            }
        });

        final JComboBox<String> imageFormatOpt = new JComboBox<>(new String[] {"PNG", "JPEG", "BMP", "PPM", "PGM"});
        imageFormatOpt.setFocusable(false);
        imageFormatOpt.setSelectedIndex(imageFormat);

        Object[] message3 = {
                " ",
                "Your image size is " + image_width + "x" + image_height + " .\nInsert the new image size.",
                "Templates:",
                templates,
                " ",
                "Width:",
                field,
                "Height:",
                field2,
                " ",
                "Output Format:",
                imageFormatOpt,
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

                        int temp = 0, temp2 = 0;
                        try {
                            temp = Integer.parseInt(field.getText());
                            temp2 = Integer.parseInt(field2.getText());

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
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();
                        ptra.setSizeOfImagePost(temp, temp2, imageFormatOpt.getSelectedIndex());
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
