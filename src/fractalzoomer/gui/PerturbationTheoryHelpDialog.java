package fractalzoomer.gui;

import javax.swing.*;
import java.awt.*;

public class PerturbationTheoryHelpDialog {

        public PerturbationTheoryHelpDialog(JDialog dialog) {
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(600, 400));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        String overview = "<font size='5' face='arial' color='blue'><center><b><u>Perturbation Theory</u></b></center></font><br><br>"
                + "<font  face='arial'>This theory let us zoom in deeper areas (deeper than 1e-13), where the normal double precision " +
                "would fail. This is done by only iterating one reference point in high precision and then calculating the rest " +
                "of the points based on the orbit of that point.<br><br>" +
                "The precision value is important as this is related to the reference point calculation. If you want to do a deep zoom, " +
                "for instance to e-400 then you have to set the precision to be in the 400s for instance 410. " +
                "You need to do that before you set the coordinates in the zoom window, because if the precision is lower, then " +
                "the coordinates will be truncated to meet the current precision value. The higher the precision the slower the calculation " +
                "of the reference point will be.<br><br>" +
                "Currently perturbation theory is implemented for the following functions and their Julia sets:<br>" +
                "<ul>" +
                "<li>Mandelbrot (2-5) powers and their burning ship variants</li>" +
                "<li>Mandelbar</li>" +
                "<li>Lambda</li>" +
                "<li>Magnet 1</li>" +
                "<li>Nova (power 3 + 0i, relaxation 1 + 0i)</li>" +
                "<li>Newton Third Degree Parameter Space</li>" +
                "<li>Newton 3</li>" +
                "</ul>" +
                "The Julia sets are still on an experimental phase as the glitch detection is not yet complete.<br><br>"+
                "Series Approximation is another useful feature of perturbation theory as it can approximate the iteration values for " +
                "a number of iterations and skip them. This feature is only implemented for the Mandelbrot (2-5) powers. " +
                "Mandelbrot (2) can use up to 129 terms, and there rest of the available powers can use up to 5 terms. " +
                "You can also set how aggressive the series approximation can be by changing the orders of magnitude difference to a lower value.<br><br>" +
                "If you zoom deeper than e-300, doubles cannot store the differences anymore so a custom type of FloatExp is used " +
                "which stores a mantissa and a extended exponent. You can choose to do all the iterations using this type on deep zooms, " +
                "or switch back to double precision when is appropriate.<br><br>" +
                "Some options like Plane Influence, Pre/Post Function Filters, Initial Value, Perturbation, Equicontinuity will be ignored if perturbation theory" +
                "is enabled and the current function supports it. Perturbation theory will not work on Julia sets with Juliter enabled (The low precision calculations will be used). " +
                "</font>"
                + "</font>";
        textArea.setText(overview);

        Object[] message = {
                scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(dialog, message, "Perturbation Theory Help", JOptionPane.QUESTION_MESSAGE);
    }
}
