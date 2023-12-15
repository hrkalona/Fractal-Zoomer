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
                "of the reference point will be. An automatic way of setting the precision currently exists,<br>" +
                "but keep in mind the above behavior.<br><br>" +
                "Built-in BigNum is a fixed precision floating point implementation, which is faster than Apfloat arbitrary precision library. " +
                "Fixed Point BigInteger BigNum is a fixed precision floating point implementation, which is faster the the built-in type. " +
                "Mpfr BigNum is a wrapper for the MPFR library, it is faster than Built-in BigNum/Fixed Point BigInteger BigNum (After a specific bit precision) and Apfloat. " +
                "Mpir BigNum is a wrapper for the MPIR library, it is faster than MPFR (Currently it only works for intel cpus from Skylake architecture and onwards). " +
                "Currently MPFR library is only supported in Windows and Linux, while MPIR library is only supported in Windows." +
                "The automatic option will try to pick the best library depending on the depth and bit precision. " +
                "BigNum Libraries can be used for reference point calculation and pixel to coordinate mapping. " +
                "If you are not using any BigNum library, then Apfloat will be used.<br><br>" +
                "Currently perturbation theory is implemented for the following functions and their Julia sets:<br>" +
                "<ul>" +
                "<li>Mandelbrot (2-5) powers and their burning ship variants</li>" +
                "<li>Mandelbar</li>" +
                "<li>Lambda</li>" +
                "<li>Magnet 1</li>" +
                "<li>Magnet Pataki (2-5) powers</li>" +
                "<li>z = z^2 + c^2</li>" +
                "<li>Buffalo Mandelbrot</li>" +
                "<li>Celtic Mandelbrot</li>" +
                "<li>Perpendicular Mandelbrot</li>" +
                "<li>Perpendicular Burning Ship</li>" +
                "<li>Perpendicular Buffalo Mandelbrot</li>" +
                "<li>Perpendicular Celtic Mandelbrot</li>" +
                "<li>Nova (power 3 + 0i, relaxation 1 + 0i)</li>" +
                "<li>Newton Third Degree Parameter Space (Experimental, has glitches in some areas)</li>" +
                "<li>Newton 3 (No Julia Set available)</li>" +
                "</ul>" +
                "<br><br>"+
                "Series Approximation is another useful feature of perturbation theory as it can approximate the iteration values for " +
                "a number of iterations and skip them. This feature is only implemented for the Mandelbrot (2-5) powers (Not supported with burning ship or Julia sets). " +
                "Mandelbrot (2) can use up to 257 terms, and there rest of the available powers can use up to 5 terms. " +
                "You can also set how aggressive the series approximation can be by changing the orders of magnitude difference to a lower value.<br><br>" +
                "If you zoom deeper than e-300, doubles cannot store the differences anymore so a custom type of FloatExp is used " +
                "which stores a mantissa and a extended exponent. You can choose to do all the iterations using this type on deep zooms, " +
                "or switch back to double precision when is appropriate.<br><br>" +
                "Some options like Plane Influence, Pre/Post Function Filters, Initial Value, Perturbation, Equicontinuity will be ignored if perturbation theory " +
                "is enabled and the current function supports it. Perturbation theory will not work on Julia sets with Juliter enabled (The low precision calculations will be used).<br><br>" +
                "The thread option for Series Approximation is only supported by Mandelbrot. It may or may not decrease the SA completion time, and it is based on your system and number of coefficients selected.<br><br>" +
                "Bilinear (Bivariate) Approximation (claude's) is a new development in perturbation theory optimization and its goal is to create a look-up table of coefficients " +
                "in order to used during the fractal iteration and approximate the iteration value by applying multiple iterations at one step, in the form of a linear " +
                "function. Currently it is only implemented for the Mandelbrot (2-5) powers (Not with burning ship or Julia sets).<br><br>" +
                "Bilinear Approximation (Zhuoran's) works similar with first version of BLA. Currently it is only implemented for Mandelbrot (Not with burning ship or Julia sets).<br><br>" +
                "Nanomb1 or Super Series Approximation can skip multiples of the period, and its only implemented for Mandelbrot (2) (Not supported with burning ship or Julia sets).<br><br>" +
                "The deep zoom pixel calculation algorithm is using FloatExp for Non Scaled, and scaling between FloatExp and doubles for Scaled.<br>" +
                "Currently it is only supported for Mandelbrot (2) and its burning ship variant (Not supported with Julia sets).<br><br>" +
                "If an approximation method or deep zoom pixel calculation method is not supported for a specific fractal, " +
                "then a No Approximation/Not Scaled configuration will be used.<br><br>" +
                "Period detection, and period usage currently only works for Mandelbrot (2-5) powers, excluding burning ship variants and julia sets.<br><br>" +
                "Using Series Approximation or Bilinear Approximation or Nanomb1 with Statistical Coloring  or Orbit Traps will not produce accurate images, as those approximations skip or merge a number of iterations " +
                "together, so their corresponding data are not accumulated correctly. The workaround is to set a number of Last X samples, so only those are taken into account." +
                "</font>"
                + "</font>";
        textArea.setText(overview);

        Object[] message = {
                scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(dialog, message, "Perturbation Theory Help", JOptionPane.QUESTION_MESSAGE);
    }
}
