/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractalzoomer.gui;

import java.awt.Dimension;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author kaloch
 */
public class UserFormulasHelpDialog {
    
    public UserFormulasHelpDialog(MainPanel main_panel, JScrollPane scroll_pane) {
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(500, 400));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        String overview = "<font size='5' face='arial' color='blue'><center><b><u>User Formulas</u></b></center></font><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Variables</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>z</b></font>: current sequence point.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>c</b></font>: plane point.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>s</b></font>: starting sequence point.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>p</b></font>: previous sequence point.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>pp</b></font>: second to previous sequence point.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>n</b></font>: current iteration.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>maxn</b></font>: maximum iterations.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>bail</b></font>: bailout.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>cbail</b></font>: convergent bailout.<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Operations/Symbols</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>+</b></font>: addition.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>-</b></font>: subtraction.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>*</b></font>: multiplication.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>/</b></font>: division.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>%</b></font>: remainder.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>^</b></font>: exponentiation.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>(</b></font>: left parenthesis.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>)</b></font>: right parenthesis.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>,</b></font>: comma.<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Constants</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>pi</b></font>: 3.141592653589793.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>e</b></font>: 2.718281828459045.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>phi</b></font>: 1.618033988749895.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>c10 (Champernowne's)</b></font>: 0.1234567891011121314.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>alpha (Feigenbaum's)</b></font>: 2.5029078750958928.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>delta (Feigenbaum's)</b></font>: 4.669201609102990.<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Complex Numbers</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>a + bi</b></font>: a = Real, b = Imaginary.<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Trigonometric Functions: f(z)</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sin</b></font>: sin function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>cos</b></font>: cos function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>tan</b></font>: tan function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>cot</b></font>: cot function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sec</b></font>: sec function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>csc</b></font>: csc function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sinh</b></font>: hyperbolic sin function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>cosh</b></font>: hyperbolic cos function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>tanh</b></font>: hyperbolic tan function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>coth</b></font>: hyperbolic cot function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sech</b></font>: hyperbolic sec function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>csch</b></font>: hyperbolic csc function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>asin</b></font>: arc sin function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acos</b></font>: arc cos function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>atan</b></font>: arc tan function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acot</b></font>: arc cot function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>asec</b></font>: arc sec function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acsc</b></font>: arc csc function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>asinh</b></font>: hyperbolic arc sin function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acosh</b></font>: hyperbolic arc cos function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>atanh</b></font>: hyperbolic arc tan function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acoth</b></font>: hyperbolic arc cot function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>asech</b></font>: hyperbolic arc sec function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>acsch</b></font>: hyperbolic arc csc function.<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Other Functions: f(z)</u></b></center></font><br>"
                                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>exp</b></font>: exp function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>log</b></font>: natural logarithm function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>log10</b></font>: base 10 logarithm function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>log2</b></font>: base 2 logarithm function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sqrt</b></font>: square root function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>abs</b></font>: absolute value function (applied both on real and imaginary parts).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>absre</b></font>: absolute value function (applied only on real part).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>absim</b></font>: absolute value function (applied only on imaginary part).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>conj</b></font>: conjugate number function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>re</b></font>: real part function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>im</b></font>: imaginary part function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>norm</b></font>: norm function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>arg</b></font>: argument function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>gamma</b></font>: gamma function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>fact</b></font>: factorial function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>erf</b></font>: error function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>rzeta</b></font>: riemann zeta function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>gi</b></font>: gaussian integer function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>rec</b></font>: reciprocal function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>flip</b></font>: flip real to imaginary function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>round</b></font>: math round function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>ceil</b></font>: math ceil function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>floor</b></font>: math floor function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>trunc</b></font>: math truncate function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f1</b></font>: user function f1 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f2</b></font>: user function f2 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f3</b></font>: user function f3 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f4</b></font>: user function f4 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f5</b></font>: user function f5 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f6</b></font>: user function f6 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f7</b></font>: user function f7 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f8</b></font>: user function f8 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f9</b></font>: user function f9 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f10</b></font>: user function f10 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f11</b></font>: user function f11 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f12</b></font>: user function f12 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f13</b></font>: user function f13 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f14</b></font>: user function f14 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f15</b></font>: user function f15 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f16</b></font>: user function f16 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f17</b></font>: user function f17 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f18</b></font>: user function f18 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f19</b></font>: user function f19 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>f20</b></font>: user function f20 which can be changed in UserDefinedFunctions.java (Compilation is required).<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Two Argument Functions: f(z, w)</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>logn</b></font>: log base n (argument 1: z, argument 2: base).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>bipol</b></font>: bipolar function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>ibipol</b></font>: inversed bipolar function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>inflect</b></font>: inflection function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldu</b></font>: fold up function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldd</b></font>: fold down function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldl</b></font>: fold left function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldr</b></font>: fold right function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldi</b></font>: fold in function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>foldo</b></font>: fold out function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>shear</b></font>: shear function.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>cmp</b></font>: compare function (returns: -1 when z > w, 1 when z < w, 0 when equal).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g1</b></font>: user function g1 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g2</b></font>: user function g2 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g3</b></font>: user function g3 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g4</b></font>: user function g4 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g5</b></font>: user function g5 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g6</b></font>: user function g6 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g7</b></font>: user function g7 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g8</b></font>: user function g8 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g9</b></font>: user function g9 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g10</b></font>: user function g10 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g11</b></font>: user function g11 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g12</b></font>: user function g12 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g13</b></font>: user function g13 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g14</b></font>: user function g14 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g15</b></font>: user function g15 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g16</b></font>: user function g16 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g17</b></font>: user function g17 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g18</b></font>: user function g18 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g19</b></font>: user function g19 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>g20</b></font>: user function g20 which can be changed in UserDefinedFunctions.java (Compilation is required).<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Multiple Argument User Functions: f(z1, z2, ... z10)</u></b></center></font><br>"
                + "<font  face='arial'>All the following functions can have up to 10 arguments, for example<br>you can use them like this: m1(z, 3+2i) or m2(1+2i, z, c, 3).<br>All the missing arguments will be defaulted to 0.<br><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m1</b></font>: user function m1 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m2</b></font>: user function m2 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m3</b></font>: user function m3 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m4</b></font>: user function m4 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m5</b></font>: user function m5 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m6</b></font>: user function m6 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m7</b></font>: user function m7 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m8</b></font>: user function m8 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m9</b></font>: user function m9 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m10</b></font>: user function m10 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m11</b></font>: user function m11 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m12</b></font>: user function m12 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m13</b></font>: user function m13 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m14</b></font>: user function m14 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m15</b></font>: user function m15 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m16</b></font>: user function m16 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m17</b></font>: user function m17 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m18</b></font>: user function m18 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m19</b></font>: user function m19 which can be changed in UserDefinedFunctions.java (Compilation is required).<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>m20</b></font>: user function m20 which can be changed in UserDefinedFunctions.java (Compilation is required).<br><br><br>"
                + "<font  face='arial'><font color='blue'><center><b><u>Additional Two Argument Functions: f(z, w)</u></b></center></font><br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>add</b></font>: addition.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>sub</b></font>: subtraction.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>mult</b></font>: multiplication.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>div</b></font>: division.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>rem</b></font>: remainder.<br>"
                + "<font  face='arial'><font size='5' face='arial'>&#8226;</font><font color='red'><b>pow</b></font>: exponentiation.<br>"

                + "</font>";
        textArea.setText(overview);

        Object[] message = {
            scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(scroll_pane, message, "User Formulas Help", JOptionPane.INFORMATION_MESSAGE);
        main_panel.repaint();
    }
    
}
