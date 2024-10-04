
package fractalzoomer.gui;

import fractalzoomer.main.CommonFunctions;
import fractalzoomer.main.Constants;
import fractalzoomer.main.MainWindow;
import fractalzoomer.main.app_settings.GeneratedPaletteSettings;
import fractalzoomer.utils.Multiwave;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static fractalzoomer.gui.CenterSizeDialog.TEMPLATE_TFIELD;

/**
 *
 * @author hrkalona2
 */
public class MultiwaveDialog extends JDialog {
    private JOptionPane optionPane;
    private GeneratedPaletteDialog ptra;

    public MultiwaveDialog(GeneratedPaletteDialog ptr, boolean outcoloring, GeneratedPaletteSettings gps) {

        super(ptr);
        
        ptra = ptr;

        Multiwave.MultiwaveColorParams[] inputParams;

        try {
            if (outcoloring) {
                inputParams = Multiwave.jsonToParams(gps.outcoloring_multiwave_user_palette);
            } else {
                inputParams = Multiwave.jsonToParams(gps.incoloring_multiwave_user_palette);
            }
        }
        catch (Exception ex) {
            inputParams = Multiwave.empty;
        }

        setTitle("Multiwave Palette");
        setModal(true);
        setIconImage(MainWindow.getIcon("mandel2.png").getImage());

        JComboBox<String> templates = new JComboBox<>(Constants.multiwavePalettes);
        templates.setSelectedIndex(0);
        templates.setFocusable(false);

        RSyntaxTextArea multiwaveTextArea = new RSyntaxTextArea(20, 60);
        multiwaveTextArea.setFont(TEMPLATE_TFIELD.getFont());
        multiwaveTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON);
        multiwaveTextArea.setCodeFoldingEnabled(true);
        multiwaveTextArea.setAntiAliasingEnabled(true);
        multiwaveTextArea.setAnimateBracketMatching(true);
        multiwaveTextArea.setLineWrap(true);
        multiwaveTextArea.setAutoIndentEnabled(true);
        multiwaveTextArea.setMarkOccurrences(true);

        try {
            Theme theme = Theme.load(getClass().getResourceAsStream(
                    "/org/fife/ui/rsyntaxtextarea/themes/idea.xml"));
            theme.apply(multiwaveTextArea);
        } catch (IOException ioe) { // Never happens

        }

        boolean showNulls = false;

        templates.addActionListener(e -> {
            String json = "";
            try {
                if(templates.getSelectedIndex() == 1) {
                    json = Multiwave.paramsToJson(Multiwave.default_params, showNulls);
                }
                else if(templates.getSelectedIndex() == 2) {
                    json = Multiwave.paramsToJson(Multiwave.g_spdz2_params, showNulls);
                }
                else if(templates.getSelectedIndex() == 3) {
                    json = Multiwave.paramsToJson(Multiwave.g_spdz2_params, showNulls);
                }
                else {
                    json = Multiwave.paramsToJson(Multiwave.empty, true);
                }
            }
            catch (Exception ex) {

            }

            if(!json.isEmpty()) {
                multiwaveTextArea.setText(json);
            }
        });


        try {
            multiwaveTextArea.setText(Multiwave.paramsToJson(inputParams, inputParams == Multiwave.empty ? true : showNulls));
            multiwaveTextArea.setCaretPosition(0);
        }
        catch (Exception ex) {

        }

        RTextScrollPane sp = new RTextScrollPane(multiwaveTextArea, true);

        SwingUtilities.invokeLater(() -> {
            sp.getVerticalScrollBar().setValue(0);
            multiwaveTextArea.setCaretPosition(0);
        });

        JPanel buttons_panel = new JPanel();

        JButton view_colors = new MyButton("View Colors");
        view_colors.setIcon(MainWindow.getIcon("colors_small.png"));
        view_colors.setFocusable(false);
        view_colors.addActionListener(e -> {
            Multiwave.MultiwaveColorParams[] params = null;
            try {
                params = Multiwave.jsonToParams(multiwaveTextArea.getText());
                Multiwave.MultiwaveColorParams.build(params, 0);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(ptra, "Illegal Argument: " + ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(params != null) {
                viewColors(this, params);
            }
        });

        buttons_panel.add(view_colors);

        JButton info_user = new MyButton("Help");
        info_user.setToolTipText("Shows info for the multiwave palette editor.");
        info_user.setFocusable(false);
        info_user.setIcon(MainWindow.getIcon("help2.png"));
        info_user.setPreferredSize(new Dimension(105, 23));

        info_user.addActionListener(e -> help());

        JPanel info_panel = new JPanel();
        info_panel.setLayout(new FlowLayout());
        info_panel.add(info_user);

        Object[] message = {
            " ",
                info_panel,
                " ",
                "Templates:",
                templates,
                " ",
                buttons_panel,
                " ",
            "Set the multi-wave parameters.",
            "Parameters:",
                sp,
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
                            Multiwave.MultiwaveColorParams[] params = Multiwave.jsonToParams(multiwaveTextArea.getText());
                            Multiwave.MultiwaveColorParams.build(params, 0);
                            if(outcoloring) {
                                try {
                                    gps.outcoloring_multiwave_user_palette = Multiwave.paramsToJson(params, false);
                                }
                                catch (Exception ex) {
                                    gps.outcoloring_multiwave_user_palette = "";
                                }
                            }
                            else {
                                try {
                                    gps.incoloring_multiwave_user_palette = Multiwave.paramsToJson(params, false);
                                }
                                catch (Exception ex) {
                                    gps.incoloring_multiwave_user_palette = "";
                                }
                            }
                            ptr.setParams(gps);
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

    public void viewColors(JDialog ptr, Multiwave.MultiwaveColorParams[] params) {
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(500, 300));
        //textArea.setLineWrap(false);
        //textArea.setWrapStyleWord(false);

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


        String data = "";
        data += "<ul>";
        for(int i = 0; i < params.length; i++) {
            Color[] tricubic_rgb = params[i].getTricubicRGBColors();
            Color[] tricubic_hsl = params[i].getTricubicHSLColors();
            Color[] linear_rgb = params[i].getLinearRGBColors();
            Color[] linear_hsl = params[i].getLinearHSLColors();
            Color[][] metatricubic_rgb = params[i].getMetaTricubicRGBColors();
            Color[][] metatricubic_hsl = params[i].getMetaTricubicHSLColors();

            Color[] colors = null;
            Color[][] meta_colors = null;

            String type = "";

            if(tricubic_rgb != null) {
                colors = tricubic_rgb;
                type = "Tricubic RGB";
            }
            else if(tricubic_hsl != null) {
                colors = tricubic_hsl;
                type = "Tricubic HSL";
            }
            else if(linear_rgb != null) {
                colors = linear_rgb;
                type = "Linear RGB";
            }
            else if(linear_hsl != null) {
                colors = linear_hsl;
                type = "Linear HSL";
            }
            else if(metatricubic_rgb != null) {
                meta_colors = metatricubic_rgb;
                type = "Meta-Tricubic RGB";
            }
            else if(metatricubic_hsl != null) {
                meta_colors = metatricubic_hsl;
                type = "Meta-Tricubic HSL";
            }

            data += "<li> Color Wave: <b>" + (i + 1) + " (" + type + ")</b>";

            data += "<ul>";

            if(colors != null) {
                data += "<li>{";
                for(int k = 0; k < colors.length; k++) {
                    if(k == 0) {
                        data += "<span style='font-size:20px;color: " + String.format("#%02x%02x%02x", colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue()) + ";'>&#9632;</span>";
                    }
                    else {
                        data += ", <span style='font-size:20px;color: " + String.format("#%02x%02x%02x", colors[k].getRed(), colors[k].getGreen(), colors[k].getBlue()) + ";'>&#9632;</span>";
                    }
                }
                data += "}</li>";
            }
            else if(meta_colors != null) {
                for(int l = 0; l < meta_colors.length; l++) {
                    if(l == 0) {
                        data += "<li>[{";
                    }
                    else {
                        data += "<li>{";
                    }
                    for (int k = 0; k < meta_colors[l].length; k++) {
                        if (k == 0) {
                            data += "<span style='font-size:20px;color: " + String.format("#%02x%02x%02x", meta_colors[l][k].getRed(), meta_colors[l][k].getGreen(), meta_colors[l][k].getBlue()) + ";'>&#9632;</span>";
                        } else {
                            data += ", <span style='font-size:20px;color: " + String.format("#%02x%02x%02x", meta_colors[l][k].getRed(), meta_colors[l][k].getGreen(), meta_colors[l][k].getBlue()) + ";'>&#9632;</span>";
                        }
                    }
                    if(l == meta_colors.length - 1) {
                         data += "}]</li>";
                    }
                    else {
                        data += "},";
                    }
                }
            }
            data += "</ul>";
        }
        data += "</ul>";

        textArea.setText("<html>" + "<center><b><u><font size='5' face='arial' color='blue'>Colors</font></u></b></center><br><br>" +
                "<font size='4' face='arial'>" + data + "</font></html>");

        Object[] message = {
                " ",
                scroll_pane_2,
                " "};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(ptr, message,  "Colors", JOptionPane.INFORMATION_MESSAGE, MainWindow.getIcon("colors_large.png"));

    }

    private void help() {
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(500, 160));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);

        String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Multiwave Palette Editor</u></b></font></center><br>"
                + "<font size='4' face='arial'>"
                + "A color wave can have the following options.<br><br>" +
                "  <b>period</b>: The period of the color wave. (If missing then the period is defined as end - start)<br><br>" +
                "  <b>mapping</b>: Value mapping (NORMAL, SQUARE, SQRT, LOG).<br><br>" +
                "  <b>start</b>: The start of the color wave.<br><br>" +
                "  <b>end</b>: The end of the color wave.<br><br>" +
                "  <b>blend</b>: Color blending (HSL_BIAS, HSL_BIAS_UFCOMPAT).<br><br>" +
                "  <b>tricubic_rgb</b>: Array of RGB values (0-255): [{\"r\" : 192, \"g\" : 64, \"b\" : 64},...]<br><br>" +
                "  <b>tricubic_hsl</b>: Array of HSL values: [{\"h\" : -1.0, \"s\" : 0.0, \"l\" : 0.0},...]<br><br>" +
                "  <b>linear_rgb</b>: Array of linear RGB points (x defines the point) with values (0-255): [{\"x\" : 0.0, \"rgb\" : {\"r\" : 11, \"g\" : 25, \"b\" : 12}}, {\"x\" : 0.375, \"rgb\" : {...}},...]<br><br>" +
                "  <b>linear_hsl</b>: Array of linear HSL points (x defines the point): [{\"x\" : 0.0, \"hsl\" : {\"h\" : -1.0, \"s\" : 0.0, \"l\" : 0.0}}, {\"x\" : 0.375, \"hsl\" : {...}},...]<br><br>" +
                "  <b>meta_tricubic_rgb</b>: 2 dimensional array of RGB values (0-255), with 2 period values: \"meta_tricubic_rgb\" : {\"rgb_colors\" : [ [ {\"r\" : 15, \"g\" : 91, \"b\" : 30},...],...], \"period1\" : 0.3, \"period2\" : 0.001}<br><br>" +
                "  <b>meta_tricubic_hsl</b>: 2 dimensional array of HSL values, with 2 period values: \"meta_tricubic_hsl\" : {\"hsl_colors\" : [ [ {\"h\" : -2.0, \"s\" : 0.3, \"l\" : 0.5},...],...], \"period1\" : 0.3, \"period2\" : 0.001}<br><br>"
                + "</font></html>";

        textArea.setText(help);

        Object[] message = {
                scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(this, message, "Help", JOptionPane.QUESTION_MESSAGE);
    }

}
