package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

public class HighPrecisionHelpDialog {

        public HighPrecisionHelpDialog(JDialog dialog) {
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(600, 300));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        String overview = "<font size='5' face='arial' color='blue'><center><b><u>High Precision</u></b></center></font><br><br>"
                + "<font  face='arial'>This option works only for fractals that support Perturbation Theory. " +
                "When using this option, then all the image is going to be calculated using the selected BigNum library " +
                "for all pixels. This option is only available for debugging reasons, and should never be used for normal calculations " +
                "as the image rendering will be dramatically slower. MPFR/MPIR libraries might also face memory issues, because a lot of code paths are unoptimized for memory allocation.<br><br>" +
                "When both High Precision and Perturbation Theory is enabled, then the High Precision render will take place " +
                "and it will override the use of Perturbation Theory. The same restrictions for feature support that is applied to " +
                "Perturbation Theory is also applied for High Precision." +
                "</font>"
                + "</font>";
        textArea.setText(overview);

        Object[] message = {
                scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(dialog, message, "High Precision Help", JOptionPane.QUESTION_MESSAGE);
    }
}
