
package fractalzoomer.gui;

import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.GradientSettings;
import fractalzoomer.palettes.CustomPalette;

import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static fractalzoomer.main.Constants.color_interp_str;

/**
 *
 * @author kaloch
 */
public class GradientDialog extends JDialog {

    private static final long serialVersionUID = -1756188920359636585L;
    private MainWindow ptra2;
    private GradientDialog this_frame;
    private JLabel gradient_label;
    private BufferedImage colors;
    private JComboBox<String> combo_box_color_interp;
    private JLabel color_a_label;
    private JLabel color_b_label;
    private JComboBox<String> combo_box_color_space;
    private JCheckBox check_box_reveres_palette;
    private JSpinner offset_textfield;

    private JTextField gradient_length_textfield;
    private int mouse_color_label_x;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;

    public GradientDialog(MainWindow ptra, GradientSettings gs) {

        super();

        ptra2 = ptra;

        this_frame = this;

        setModal(true);
        int custom_palette_window_width = 800;
        int custom_palette_window_height = 260;
        setTitle("Gradient");
        setIconImage(MainWindow.getIcon("gradient.png").getImage());
        
        grab_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grab.gif").getImage(), new Point(16, 16), "grab");
        grabbing_cursor = Toolkit.getDefaultToolkit().createCustomCursor(MainWindow.getIcon("cursor_grabbing.gif").getImage(), new Point(16, 16), "grabbing");

        setSize(custom_palette_window_width, custom_palette_window_height);
        setLocation((int) (ptra2.getLocation().getX() + ptra2.getSize().getWidth() / 2) - (custom_palette_window_width / 2), (int) (ptra2.getLocation().getY() + ptra2.getSize().getHeight() / 2) - (custom_palette_window_height / 2));

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                dispose();

            }
        });

        JPanel gradient_panel = new JPanel();

        gradient_panel.setPreferredSize(new Dimension(720, 130));
        gradient_panel.setLayout(new GridLayout(2, 1));
        gradient_panel.setBackground(MainWindow.bg_color);

        color_a_label = new ColorLabel();

        color_a_label.setPreferredSize(new Dimension(22, 22));
        color_a_label.setBackground(gs.colorA);
        color_a_label.setToolTipText("Changes first color.");

        color_a_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserDialog(this_frame, "First Color", color_a_label, -1, -1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        color_b_label = new ColorLabel();

        color_b_label.setPreferredSize(new Dimension(22, 22));
        color_b_label.setBackground(gs.colorB);
        color_b_label.setToolTipText("Changes second color.");

        color_b_label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                new ColorChooserDialog(this_frame, "Second Color", color_b_label, -1, -1);

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        check_box_reveres_palette = new JCheckBox("Reversed");
        check_box_reveres_palette.setSelected(gs.gradient_reversed);
        check_box_reveres_palette.setFocusable(false);
        check_box_reveres_palette.setToolTipText("Reverses the current palette.");
        check_box_reveres_palette.setBackground(MainWindow.bg_color);

        check_box_reveres_palette.addActionListener(e -> {
            try {
                int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                if (temp3 < 0 || temp4 <= 0) {
                    throw new NumberFormatException();
                }

                Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                paintGradient(c);
            } catch (Exception ex) {
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient_label.repaint();
            }
        });

        combo_box_color_interp = new JComboBox<>(color_interp_str);
        combo_box_color_interp.setSelectedIndex(gs.gradient_interpolation);
        combo_box_color_interp.setToolTipText("Sets the color interpolation option.");

        combo_box_color_interp.addActionListener(e -> {
            try {
                int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                if (temp3 < 0 || temp4 <= 0) {
                    throw new NumberFormatException();
                }

                Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                paintGradient(c);
            } catch (Exception ex) {
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient_label.repaint();
            }
        });

        JPanel color_interp_panel = new JPanel();
        color_interp_panel.setPreferredSize(new Dimension(165, 60));
        color_interp_panel.setLayout(new FlowLayout());
        color_interp_panel.setBackground(MainWindow.bg_color);

        color_interp_panel.setBorder(LAFManager.createTitledBorder( "Color Interpolation"));

        color_interp_panel.add(combo_box_color_interp);

        combo_box_color_space = new JComboBox<>(Constants.colorSpaces);
        combo_box_color_space.setSelectedIndex(gs.gradient_color_space);
        combo_box_color_space.setToolTipText("Sets the color space option.");

        combo_box_color_space.addActionListener(e -> {
            try {
                int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                if (temp3 < 0 || temp4 <= 0) {
                    throw new NumberFormatException();
                }

                Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                paintGradient(c);
            } catch (Exception ex) {
                Graphics2D g = colors.createGraphics();
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                gradient_label.repaint();
            }
        });

        JPanel color_space_panel = new JPanel();
        color_space_panel.setPreferredSize(new Dimension(140, 60));
        color_space_panel.setLayout(new FlowLayout());
        color_space_panel.setBackground(MainWindow.bg_color);

        color_space_panel.setBorder(LAFManager.createTitledBorder( "Color Space"));

        color_space_panel.add(combo_box_color_space);

        JPanel options_panel = new JPanel();

        options_panel.setLayout(new FlowLayout());
        options_panel.setBackground(MainWindow.bg_color);

        JPanel color_panel = new JPanel();
        color_panel.setPreferredSize(new Dimension(380, 60));
        color_panel.setLayout(new FlowLayout());
        color_panel.setBackground(MainWindow.bg_color);

        color_panel.setBorder(LAFManager.createTitledBorder( "Colors"));

        color_panel.add(color_a_label);
        color_panel.add(color_b_label);
        color_panel.add(check_box_reveres_palette);

        options_panel.add(color_panel);
        options_panel.add(color_space_panel);
        options_panel.add(color_interp_panel);

        colors = new BufferedImage(692, 45, BufferedImage.TYPE_INT_ARGB);

        Color[] c = CustomPalette.getGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), gs.gradient_length, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed, gs.gradient_offset);

        try {
            Graphics2D g = colors.createGraphics();
            int length = c.length - 1;
            for (int i = 0; i < length; i++) {
                GradientPaint gp = new GradientPaint(i * colors.getWidth() / ((float)length), 0, c[i], (i + 1) * colors.getWidth() / ((float)length), 0, c[(i + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(i * colors.getWidth() / ((double)length), 0, (i + 1) * colors.getWidth() / ((double)length) - i * colors.getWidth() / ((double)length), colors.getHeight()));
            }
        } catch (Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient_label = new ImageLabel(new ImageIcon(colors));
        gradient_label.setPreferredSize(new Dimension(colors.getWidth(), colors.getHeight()));
        gradient_label.setBorder(LAFManager.createSimpleBorder());
        
        gradient_label.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                mouse_color_label_x = (int) gradient_label.getMousePosition().getX();

                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    gradient_label.setCursor(grabbing_cursor);
                } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    gradient_label.setCursor(grab_cursor);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                    gradient_label.setCursor(grab_cursor);
                } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                    gradient_label.setCursor(grab_cursor);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        gradient_label.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    if (e.getModifiers() == InputEvent.BUTTON1_MASK) {
                        gradient_label.setCursor(grabbing_cursor);
                    } else if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
                        gradient_label.setCursor(grab_cursor);
                    }

                    int diff = (int) gradient_label.getMousePosition().getX() - mouse_color_label_x;
                    
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        throw new NumberFormatException();
                    }

                    temp3 -= diff;

                    if (temp3 < 0) {
                        temp3 = 0;
                    }

                    ((DefaultEditor) offset_textfield.getEditor()).getTextField().setText("" + temp3);

                    mouse_color_label_x = (int) gradient_label.getMousePosition().getX();
                } catch (Exception ex) {
                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    gradient_label.setCursor(grab_cursor);
                    Color temp_color = new Color(colors.getRGB((int) gradient_label.getMousePosition().getX(), (int) gradient_label.getMousePosition().getY()));
                    String rr = Integer.toHexString(temp_color.getRed());
                    String bb = Integer.toHexString(temp_color.getBlue());
                    String gg = Integer.toHexString(temp_color.getGreen());

                    rr = rr.length() == 1 ? "0" + rr : rr;
                    gg = gg.length() == 1 ? "0" + gg : gg;
                    bb = bb.length() == 1 ? "0" + bb : bb;

                    gradient_label.setToolTipText("<html>R: " + temp_color.getRed() + " G: " + temp_color.getGreen() + " B: " + temp_color.getBlue() + "<br>"
                            + "#" + rr + gg + bb + "</html>");

                } catch (Exception ex) {
                }
            }
        });

        JPanel gp = new JPanel();
        gp.setBackground(MainWindow.bg_color);

        gp.add(gradient_label);

        gradient_length_textfield = new JTextField(4);
        gradient_length_textfield.setText("" + gs.gradient_length);
        gradient_length_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }
        });

        color_panel.add(new JLabel(" Length"));
        color_panel.add(gradient_length_textfield);

        JLabel offset_label = new JLabel(" Offset");

        color_panel.add(offset_label);

        SpinnerModel spinnerModel = new SpinnerNumberModel(gs.gradient_offset, //initial value
                0, //min
                Integer.MAX_VALUE, //max
                1);

        offset_textfield = new JSpinner(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(offset_textfield);
        editor.getFormat().setGroupingUsed(false);
        offset_textfield.setEditor(editor);

        offset_textfield.setPreferredSize(new Dimension(70, 26));
        offset_textfield.setToolTipText("Adds an offset to the current gradient.");
        ((DefaultEditor) offset_textfield.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

                try {
                    int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                    int temp4 = Integer.parseInt(gradient_length_textfield.getText());

                    if (temp3 < 0 || temp4 <= 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }

            }
        });
        
        color_panel.add(offset_textfield);

        gradient_panel.add(options_panel);
        gradient_panel.add(gp);

        JPanel buttons = new JPanel();

        buttons.setLayout(new FlowLayout());
        buttons.setBackground(MainWindow.bg_color);

        JButton ok = new MyButton("Ok");
        getRootPane().setDefaultButton(ok);
        ok.setFocusable(false);
        ok.addActionListener(e -> {

            int temp2;
            int temp4;
            try {
                temp2 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
                temp4 = Integer.parseInt(gradient_length_textfield.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this_frame, "Illegal Argument!", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp2 < 0) {
                JOptionPane.showMessageDialog(this_frame, "The offset must be greater than -1.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (temp4 <= 0) {
                JOptionPane.showMessageDialog(this_frame, "The length must be greater than 0.", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();

            ptra2.gradientChanged(color_a_label.getBackground(), color_b_label.getBackground(), combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp2, temp4);

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Ok");
        getRootPane().getActionMap().put("Ok", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                ok.doClick();
            }
        });

        buttons.add(ok);

        JButton cancel = new MyButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(e -> {

            ptra2.setEnabled(true);
            dispose();

        });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel");
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {

            public void actionPerformed(ActionEvent e)
            {
                cancel.doClick();
            }
        });

        buttons.add(cancel);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(730, 180));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(gradient_panel, con);

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 1;

        round_panel.add(buttons, con);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new GridBagLayout());
        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;
        main_panel.add(round_panel, con);

        JScrollPane scrollPane = new JScrollPane(main_panel);
        add(scrollPane);

        setVisible(true);

        repaint();
    }

    private void paintGradient(Color[] c) {

        try {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
            int length = c.length - 1;
            for (int i = 0; i < length; i++) {
                GradientPaint gp = new GradientPaint(i * colors.getWidth() / ((float)length), 0, c[i], (i + 1) * colors.getWidth() / ((float)length), 0, c[(i + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(i * colors.getWidth() / ((double)length), 0, (i + 1) * colors.getWidth() / ((double)length) - i * colors.getWidth() / ((double)length), colors.getHeight()));
            }
        } catch (Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
        }

        gradient_label.repaint();

    }

    public void colorChanged() {
        try {
            int temp3 = Integer.parseInt(((DefaultEditor) offset_textfield.getEditor()).getTextField().getText());
            int temp4 = Integer.parseInt(gradient_length_textfield.getText());

            if (temp3 < 0 || temp4 <= 0) {
                throw new NumberFormatException();
            }

            Color[] c = CustomPalette.getGradient(color_a_label.getBackground().getRGB(), color_b_label.getBackground().getRGB(), temp4, combo_box_color_interp.getSelectedIndex(), combo_box_color_space.getSelectedIndex(), check_box_reveres_palette.isSelected(), temp3);

            paintGradient(c);
        } catch (Exception ex) {
            Graphics2D g = colors.createGraphics();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
            gradient_label.repaint();
        }
    }
}
