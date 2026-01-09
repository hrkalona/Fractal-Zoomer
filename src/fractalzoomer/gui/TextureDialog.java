
package fractalzoomer.gui;

import fractalzoomer.core.TaskRender;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.Settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author hrkalona2
 */
public class TextureDialog extends JDialog {

    private MainWindow ptra;
    private JOptionPane optionPane;
    private BufferedImage image;

    public TextureDialog(MainWindow ptr, Settings s, boolean greedy_algorithm, boolean julia_map) {

        super(ptr);
        
        ptra = ptr;

        setTitle("Texture Mapping");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        image = s.pps.ts.textureImg;

        MyButton load_image_button = new MyButton();
        load_image_button.setIcon(MainWindow.getIcon("save_image.png"));
        load_image_button.setFocusable(false);
        load_image_button.setToolTipText("Loads an image to be used as a texture");
        load_image_button.setPreferredSize(new Dimension(30, 30));

        load_image_button.addActionListener(e -> loadPatternImage(this));

        JPanel loadImagePanel = new JPanel();
        loadImagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        loadImagePanel.add(load_image_button);

        JTextField texture_scale_x_field = new JTextField();
        texture_scale_x_field.setText("" + s.pps.ts.textureScaleX);

        JTextField texture_scale_y_field = new JTextField();
        texture_scale_y_field.setText("" + s.pps.ts.textureScaleY);

        final JCheckBox enable_texture = new JCheckBox("Texture Mapping");
        enable_texture.setSelected(s.pps.ts.applyTexture);
        enable_texture.setFocusable(false);

        JTextField texture_offset_field = new JTextField();
        texture_offset_field.setText("" + s.pps.ts.textureOffset);

        JSlider color_blend_opt = new SliderGradient(JSlider.HORIZONTAL, 0, 100, (int) (s.pps.ts.texture_blending * 100));
        color_blend_opt.setMajorTickSpacing(25);
        color_blend_opt.setMinorTickSpacing(1);
        color_blend_opt.setToolTipText("Sets the color blending percentage.");
        color_blend_opt.setFocusable(false);
        color_blend_opt.setPaintLabels(true);

        JTextField noise_factor_field = new JTextField();
        noise_factor_field.setText("" + s.pps.ts.texture_noise_reducing_factor);

        JPanel blend_panel = new JPanel();

        final JComboBox<String> blend_modes = new JComboBox<>(Constants.blend_algorithms);
        blend_modes.setSelectedIndex(s.pps.ts.texture_color_blending);
        blend_modes.setFocusable(false);
        blend_modes.setToolTipText("Sets the blending mode mode.");

        final JCheckBox reverse_blending = new JCheckBox("Reverse Order of Colors");
        reverse_blending.setSelected(s.pps.ts.texture_reverse_color_blending);
        reverse_blending.setFocusable(false);
        reverse_blending.setToolTipText("Reverts the order of colors in the blending operation.");

        blend_panel.add(new JLabel("Blend Mode: "));
        blend_panel.add(blend_modes);
        blend_panel.add(reverse_blending);

        Object[] message = {
            " ",
            enable_texture,
            " ",
                "Select the texture image.",
            "Texture Image:",
                    loadImagePanel,
                " ",
            "Set texture scale factors.",
            "Scale Factor X:", texture_scale_x_field,
            "Scale Factor Y:", texture_scale_y_field,
            " ",
            "Set the texture offset.",
            "Offset:", texture_offset_field,
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
                            double temp = Double.parseDouble(texture_scale_x_field.getText());
                            double temp2 = Double.parseDouble(noise_factor_field.getText());
                            int temp3 = Integer.parseInt(texture_offset_field.getText());
                            double temp4 = Double.parseDouble(texture_scale_y_field.getText());

                            if (temp < 0) {
                                JOptionPane.showMessageDialog(ptra, "The texture scale factor x must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp4 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The texture scale factor y must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp2 <= 0) {
                                JOptionPane.showMessageDialog(ptra, "The noise reduction factor must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (temp3 < 0) {
                                JOptionPane.showMessageDialog(ptra, "The texture offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if(image == null) {
                                JOptionPane.showMessageDialog(ptra, "No texture image was selected.", "Error!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            s.pps.ts.applyTexture = enable_texture.isSelected();
                            s.pps.ts.textureScaleX = temp;
                            s.pps.ts.textureScaleY = temp4;
                            s.pps.ts.texture_noise_reducing_factor = temp2;
                            s.pps.ts.textureOffset = temp3;
                            s.pps.ts.texture_blending = color_blend_opt.getValue() / 100.0;
                            s.pps.ts.texture_color_blending = blend_modes.getSelectedIndex();
                            s.pps.ts.texture_reverse_color_blending = reverse_blending.isSelected();
                            s.pps.ts.textureImg = image;
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        dispose();

                        if (greedy_algorithm && !TaskRender.GREEDY_ALGORITHM_CHECK_ITER_DATA && enable_texture.isSelected() && !julia_map && !s.d3s.d3) {
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

    private void loadPatternImage(Component parent) {

        JFileChooser file_chooser = new JFileChooser(".");

        file_chooser.setAcceptAllFileFilterUsed(false);
        file_chooser.setDialogType(JFileChooser.OPEN_DIALOG);

        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image Files", ImageIO.getReaderFileSuffixes());

        file_chooser.addChoosableFileFilter(imageFilter);

        file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
            String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
            file_chooser.setSelectedFile(new File(file_name));
        });

        int returnVal = file_chooser.showDialog(parent, "Load Texture Image");

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String path = file_chooser.getSelectedFile().getPath();

            if(path != null && !path.isEmpty()) {
                try {
                    image = ImageIO.read(new File(path));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parent, "Error while loading the " + path + " file.", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
