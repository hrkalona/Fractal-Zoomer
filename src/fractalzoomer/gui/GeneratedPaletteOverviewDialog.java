package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.CosinePaletteSettings;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.palettes.PaletteColor;
import fractalzoomer.utils.InfiniteWave;
import fractalzoomer.utils.Multiwave;
import fractalzoomer.utils.MultiwaveSimple;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class GeneratedPaletteOverviewDialog extends JDialog {

    private static final long serialVersionUID = -1756188920359636585L;
    private GeneratedPaletteDialog ptra2;
    private GeneratedPaletteOverviewDialog this_frame;
    private JLabel gradient_label;
    private BufferedImage colors;
    private JSpinner offset_textfield;
    private Cursor grab_cursor;
    private Cursor grabbing_cursor;

    private int mouse_color_label_x;
    Multiwave.MultiwaveColorParams[] mw;
    InfiniteWave.InfiniteColorWaveParams[] iw;

    MultiwaveSimple.MultiwaveSimpleColorParams[] smw;

    public GeneratedPaletteOverviewDialog(GeneratedPaletteDialog ptra, boolean outcoloring, GeneratedPaletteSettings gps, int id, int cycle, int offset, double factor) {

        super();

        ptra2 = ptra;

        this_frame = this;

        setModal(true);
        int custom_palette_window_width = 760;
        int custom_palette_window_height = 260;
        setTitle("Generated Palette Preview");
        setIconImage(MainWindow.getIcon("blending.png").getImage());

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

        gradient_panel.setPreferredSize(new Dimension(680, 130));
        gradient_panel.setLayout(new GridLayout(2, 1));
        gradient_panel.setBackground(MainWindow.bg_color);

        JPanel options_panel = new JPanel();

        options_panel.setLayout(new FlowLayout());
        options_panel.setBackground(MainWindow.bg_color);

        colors = new BufferedImage(652, 45, BufferedImage.TYPE_INT_ARGB);

        int color_offset = 0;
        CosinePaletteSettings cps = outcoloring ? gps.outColoringIQ : gps.inColoringIQ;

        try {
            mw = outcoloring ? Multiwave.jsonToParams(gps.outcoloring_multiwave_user_palette) : Multiwave.jsonToParams(gps.incoloring_multiwave_user_palette);
        }
        catch (Exception ex) {
            mw = Multiwave.empty;
        }

        try {
            iw = outcoloring ? InfiniteWave.jsonToParams(gps.outcoloring_infinite_wave_user_palette) : InfiniteWave.jsonToParams(gps.incoloring_infinite_wave_user_palette);
        }
        catch (Exception ex) {
            iw = InfiniteWave.empty;
        }

        try {
            smw = outcoloring ? MultiwaveSimple.jsonToParams(gps.outcoloring_simple_multiwave_user_palette) : MultiwaveSimple.jsonToParams(gps.incoloring_simple_multiwave_user_palette);
        }
        catch (Exception ex) {
            smw = MultiwaveSimple.empty;
        }

        Color[] c = new Color[colors.getWidth()];
        for(int i = 0; i < c.length; i++) {
            c[i] = new Color(PaletteColor.getGeneratedColor(i, id, color_offset, offset, cycle, factor, cps, outcoloring, mw, iw, smw));
        }

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

                    int temp3 = Integer.parseInt(((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        throw new NumberFormatException();
                    }

                    temp3 -= diff;

                    if (temp3 < 0) {
                        temp3 = 0;
                    }

                    ((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().setText("" + temp3);

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

        JLabel offset_label = new JLabel(" Offset");

        SpinnerModel spinnerModel = new SpinnerNumberModel(0, //initial value
                0, //min
                Integer.MAX_VALUE, //max
                1);

        offset_textfield = new JSpinner(spinnerModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(offset_textfield);
        editor.getFormat().setGroupingUsed(false);
        offset_textfield.setEditor(editor);

        offset_textfield.setPreferredSize(new Dimension(70, 26));
        offset_textfield.setToolTipText("Adds an offset to the current gradient.");
        ((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {

                try {
                    int temp3 = Integer.parseInt(((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = new Color[colors.getWidth()];
                    for(int i = 0; i < c.length; i++) {
                        c[i] = new Color(PaletteColor.getGeneratedColor(i, id, temp3, offset, cycle, factor, cps, outcoloring, mw, iw, smw));
                    }

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
                    int temp3 = Integer.parseInt(((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = new Color[colors.getWidth()];
                    for(int i = 0; i < c.length; i++) {
                        c[i] = new Color(PaletteColor.getGeneratedColor(i, id, temp3, offset, cycle, factor, cps, outcoloring, mw, iw, smw));
                    }

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
                    int temp3 = Integer.parseInt(((JSpinner.DefaultEditor) offset_textfield.getEditor()).getTextField().getText());

                    if (temp3 < 0) {
                        throw new NumberFormatException();
                    }

                    Color[] c = new Color[colors.getWidth()];
                    for(int i = 0; i < c.length; i++) {
                        c[i] = new Color(PaletteColor.getGeneratedColor(i, id, temp3, offset, cycle, factor, cps, outcoloring, mw, iw, smw));
                    }

                    paintGradient(c);
                } catch (Exception ex) {
                    Graphics2D g = colors.createGraphics();
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, colors.getWidth(), colors.getHeight());
                    gradient_label.repaint();
                }

            }
        });

        JPanel color_panel = new JPanel();
        color_panel.setPreferredSize(new Dimension(340, 60));
        color_panel.setLayout(new FlowLayout());
        color_panel.setBackground(MainWindow.bg_color);

        color_panel.add(offset_label);
        color_panel.add(offset_textfield);

        options_panel.add(color_panel);

        gradient_panel.add(options_panel);
        gradient_panel.add(gp);

        RoundedPanel round_panel = LAFManager.createRoundedPanel();
        round_panel.setBackground(MainWindow.bg_color);
        round_panel.setPreferredSize(new Dimension(690, 180));
        round_panel.setLayout(new GridBagLayout());

        GridBagConstraints con = new GridBagConstraints();

        con.fill = GridBagConstraints.CENTER;
        con.gridx = 0;
        con.gridy = 0;

        round_panel.add(gradient_panel, con);

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
}
