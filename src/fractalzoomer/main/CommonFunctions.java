/*
 * Fractal Zoomer, Copyright (C) 2020 hrkalona2
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
package fractalzoomer.main;

import fractalzoomer.app_updater.AppUpdater;
import fractalzoomer.core.*;
import fractalzoomer.core.domain_coloring.DomainColoring;
import fractalzoomer.functions.root_finding_methods.durand_kerner.DurandKernerRootFindingMethod;
import fractalzoomer.functions.root_finding_methods.newton_hines.NewtonHinesRootFindingMethod;
import fractalzoomer.gui.*;
import fractalzoomer.main.app_settings.GradientSettings;
import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.palettes.PresetPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.ColorSpaceConverter;
import fractalzoomer.utils.MathUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicFileChooserUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLWriter;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author hrkalona2
 */
public class CommonFunctions implements Constants {

    private Component parent;
    private boolean runsOnWindows;
    private static int gradientLength;
    private static int paletteOutLength;
    private static int paletteInLength;

    public CommonFunctions(Component parent, boolean runsOnWindows) {

        this.parent = parent;
        this.runsOnWindows = runsOnWindows;

    }

    public void checkForUpdate(boolean mode) {

        String[] res = AppUpdater.checkVersion(VERSION);

        if (res[1].equals("error")) {
            if (mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.ERROR_MESSAGE);
            }
        } else if (res[1].equals("up to date")) {

            if (mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.PLAIN_MESSAGE, MainWindow.getIcon("checkmark.png"));
            }
        } else {
            Object[] message = {
                new LinkLabel(res[0], res[1])};

            JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.PLAIN_MESSAGE, MainWindow.getIcon("update_larger.png"));
        }

    }

    public Object[] copyLibNoUI() {

        if (!runsOnWindows) {
            return new Object[]{0, ""};
        }

        if(getJavaVersion() != 8) {
            return new Object[]{0, ""};
        }

        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");

        if (!path.isEmpty() && !file_separator.isEmpty()) {
            path += file_separator + "lib" + file_separator;

            if (!path.contains(file_separator + "jdk")) {
                Path filePath = Paths.get(path + "tools.jar");
                if (!Files.exists(filePath)) {
                    try {
                        InputStream src = getClass().getResourceAsStream("/fractalzoomer/lib/tools.jar");
                        Files.copy(src, filePath);
                    } catch (Exception ex) {
                        return new Object[]{1, "Unable to copy tools.jar to " + path + "\nMake sure you run this application as an Administrator.\nThe application will not be able to compile and use User Code."};
                    }
                }
            }
        } else {
            return new Object[]{1, "Unable to copy tools.jar to the JRE lib folder.\nThe JRE installation path was not found.\nThe application will not be able to compile and use User Code."};
        }

        return new Object[]{0, ""};
    }

    public boolean copyLib() {

        if (!runsOnWindows) {
            return true;
        }

        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");
        String version = System.getProperty("java.version");

        if(!version.startsWith("1.8")) {
            return true;
        }

        if (!path.isEmpty() && !file_separator.isEmpty()) {
            path += file_separator + "lib" + file_separator;

            if (!path.contains(file_separator + "jdk")) {
                Path filePath = Paths.get(path + "tools.jar");
                if (!Files.exists(filePath)) {
                    try {
                        InputStream src = getClass().getResourceAsStream("/fractalzoomer/lib/tools.jar");
                        Files.copy(src, filePath);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(parent, "Unable to copy tools.jar to " + path + "\nMake sure you run this application as an Administrator.\nThe application will not be able to compile and use User Code.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(parent, "Unable to copy tools.jar to the JRE lib folder.\nThe JRE installation path was not found.\nThe application will not be able to compile and use User Code.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public Object[] exportCodeFilesNoUi() {

        try {


            InputStream src = getClass().getResourceAsStream("/fractalzoomer/parser/code/UserDefinedFunctions.javacode");
            Files.copy(src, Paths.get(Parser.DEFAULT_USER_CODE_FILE));

            Path path = Paths.get("Derivative.java");
            Files.deleteIfExists(path);
            path = Paths.get("Complex.java");
            Files.deleteIfExists(path);
        } catch (FileAlreadyExistsException ex) {
        } catch (Exception ex) {
            return new Object[]{2, ex.getMessage()};
        }

        return new Object[]{0, ""};

    }

    public Object[] compileCodeNoUi() {

        try {
            Parser.compileUserFunctions();
        } catch (ParserException ex) {
            return new Object[]{2, ex.getMessage()};
        }
        catch (Exception ex) {
            return new Object[]{2, ex.getMessage()};
        }

        return new Object[]{0, ""};

    }

    public void compileCode(boolean show_success) {

        try {
            Parser.compileUserFunctions();
            if (show_success) {
                JOptionPane.showMessageDialog(parent, "Compilation was successful!", "Success!", JOptionPane.INFORMATION_MESSAGE, MainWindow.getIcon("compile_sucess.png"));
            }
        } catch (ParserException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, MainWindow.getIcon("compile_error.png"));
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, MainWindow.getIcon("compile_error.png"));
        }

    }

    public void overview(Settings s, boolean periodicity_checking) {

        //JTextArea textArea = new JTextArea(32, 55); // 60
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(800, 380));
        //textArea.setPreferredSize(new Dimension(200, 200));
        //textArea.setLineWrap(false);
        //textArea.setWrapStyleWord(false);

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        BigPoint p = MathUtils.rotatePointRelativeToPoint(new BigPoint(s.xCenter, s.yCenter), s.fns.rotation_vals, s.fns.rotation_center);

        String keyword_color = "#008080";
        String condition_color = "#800000";

        String tab = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";
        String tab2 = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";
        String tab3 = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";

        String overview = "<html><center><b><u><font size='5' face='arial' color='blue'>Active Fractal Options</font></u></b></center><br><br><font  face='arial' size='3'>";

        overview += "<b><font color='red'>Center:</font></b> " + BigComplex.toString2Pretty(p.x, p.y, s.size) + "<br><br>";
        overview += "<b><font color='red'>Size:</font></b> " + MyApfloat.toString(s.size, s.size) + "<br><br>";

        if (s.polar_projection) {
            overview += "<b><font color='red'>Polar Projection:</font></b> " + "<br>" + tab + "Circle Periods = " + s.circle_period + "<br><br>";
        }

        overview += "<b><font color='red'>Function:</font></b> " + FractalFunctionsMenu.functionNames[s.fns.function] + "<br>";

        if (Settings.isPolynomialFunction(s.fns.function)) {
            if (s.fns.function == MANDELPOLY) {
                overview += tab + s.poly + " + c<br>";
            } else {
                overview += tab + s.poly + "<br>";
            }
        } else if (Settings.isRoot3Function(s.fns.function)) {
            overview += tab + "p(z) = z^3 - 1" + "<br>";
        } else if (Settings.isRoot4Function(s.fns.function)) {
            overview += tab + "p(z) = z^4 - 1" + "<br>";
        } else if (Settings.isRootGeneralized3Function(s.fns.function)) {
            overview += tab + "p(z) = z^3 - 2z + 2" + "<br>";
        } else if (Settings.isRootGeneralized8Function(s.fns.function)) {
            overview += tab + "p(z) = z^8 + 15z^4 - 16" + "<br>";
        } else if (Settings.isRootCosFunction(s.fns.function)) {
            overview += tab + "f(z) = cos(z)" + "<br>";
        } else if (Settings.isRootSinFunction(s.fns.function)) {
            overview += tab + "f(z) = sin(z)" + "<br>";
        } else if (s.fns.function == MANDELBROTNTH) {
            overview += tab + "z = z^" + s.fns.z_exponent + " + c<br>";
        } else if (s.fns.function == MANDELBROTWTH) {
            overview += tab + "z = z^(" + Complex.toString2(s.fns.z_exponent_complex[0], s.fns.z_exponent_complex[1]) + ") + c<br>";
        } else if (s.fns.function == LAMBDA) {
            overview += tab + "z = cz(1 - z)" + "<br>";
        } else if (s.fns.function == LAMBDA2) {
            overview += tab + "z = cz(1 - z^2)" + "<br>";
        } else if (s.fns.function == LAMBDA3) {
            overview += tab + "z = cz(1 - z^3)" + "<br>";
        } else if (s.fns.function == LAMBDA_FN_FN) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.lfns.lambda_formula_conditions[0] + " > " + s.fns.lfns.lambda_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = c * (" + s.fns.lfns.lambda_formula_condition_formula[0] + ")<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.lfns.lambda_formula_conditions[0] + " &#60; " + s.fns.lfns.lambda_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = c * (" + s.fns.lfns.lambda_formula_condition_formula[1] + ")<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.lfns.lambda_formula_conditions[0] + " = " + s.fns.lfns.lambda_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = c * (" + s.fns.lfns.lambda_formula_condition_formula[2] + ")<br>";
        } else if (s.fns.function == MAGNET1) {
            overview += tab + "z = ((z^2 + c - 1)/(2z + c - 2))^2" + "<br>";
        }
        else if (s.fns.function == MAGNET13) {
            overview += tab + "z = ((z^2 + c - 1)/(2z + c - 2))^3" + "<br>";
        }
        else if (s.fns.function == MAGNET14) {
            overview += tab + "z = ((z^2 + c - 1)/(2z + c - 2))^4" + "<br>";
        }
        else if (s.fns.function == MAGNET2) {
            overview += tab + "z = ((z^3 + 3(c - 1)z + (c - 1)(c - 2))/(3z^2 + 3(c - 2)z + (c - 1)(c - 2))))^2" + "<br>";
        }
        else if (s.fns.function == MAGNET23) {
            overview += tab + "z = ((z^3 + 3(c - 1)z + (c - 1)(c - 2))/(3z^2 + 3(c - 2)z + (c - 1)(c - 2))))^3" + "<br>";
        }
        else if (s.fns.function == MAGNET24) {
            overview += tab + "z = ((z^3 + 3(c - 1)z + (c - 1)(c - 2))/(3z^2 + 3(c - 2)z + (c - 1)(c - 2))))^4" + "<br>";
        }
        else if (s.fns.function == MAGNET_PATAKI2) {
            overview += tab + "z = (z^2 + c)/(z^2 - 1)" + "<br>";
        }
        else if (s.fns.function == MAGNET_PATAKI3) {
            overview += tab + "z = (z^3 + c)/(z^3 - 1)" + "<br>";
        }
        else if (s.fns.function == MAGNET_PATAKI4) {
            overview += tab + "z = (z^4 + c)/(z^4 - 1)" + "<br>";
        }
        else if (s.fns.function == MAGNET_PATAKI5) {
            overview += tab + "z = (z^5 + c)/(z^5 - 1)" + "<br>";
        }
        else if (s.fns.function == MAGNET_PATAKIK) {
            overview += tab + "z = (z^" + s.fns.z_exponent + " + c)/(z^" + s.fns.z_exponent + " - 1)" + "<br>";
        }
        else if (s.fns.function == FROTHY_BASIN) {
            overview += tab + "z = z^2 - (1 + 1.028713768218725i)conj(z) + c<br>";
        } else if (s.fns.function == SPIDER) {
            overview += tab + "z = z^2 + c<br>";
            overview += tab + "c = c/2 + z<br>";
        } else if (s.fns.function == PHOENIX) {
            overview += tab + "t = z^2 + (im(c)re(s) + re(c)) + (im(c)im(s))i<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        } else if (s.fns.function == SIERPINSKI_GASKET) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[im(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) + (2im(z) - 1)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else if</font> <font color='" + condition_color + "'>[re(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) - 1 + 2im(z)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = 2z<br>";
        } else if (s.fns.function == BARNSLEY1) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z - 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z + 1)c<br>";
        } else if (s.fns.function == BARNSLEY2) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z)im(c) + re(c)im(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z + 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z - 1)c<br>";
        } else if (s.fns.function == BARNSLEY3) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) > 0]</font> <font color='" + keyword_color + "'>then</font> z = z^2 - 1<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = z^2 + re(c)re(z) + im(c)re(z) - 1<br>";
        } else if (s.fns.function == SZEGEDI_BUTTERFLY1) {
            overview += tab + "z = (im(z)^2 - sqrt(abs(re(z)))) + (re(z)^2 - sqrt(abs(im(z))))i + c<br>";
        } else if (s.fns.function == SZEGEDI_BUTTERFLY2) {
            overview += tab + "z = (re(z)^2 - sqrt(abs(im(z)))) + (im(z)^2 - sqrt(abs(re(z))))i + c<br>";
        } else if (s.fns.function == MANOWAR) {
            overview += tab + "t = z^2 + s + c<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        } else if (s.fns.function == MANDELBAR) {
            overview += tab + "z = conj(z)^2 + c<br>";
        } else if (s.fns.function == COUPLED_MANDELBROT) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = w^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = c<br>";
        } else if (s.fns.function == COUPLED_MANDELBROT_BURNING_SHIP) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = abs(w)^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = 0.0 + 0.0i<br>";
        } else if (s.fns.function == NOVA) {
            overview += tab + Constants.novaMethods[s.fns.nova_method] + "<br>";
            overview += tab + "p(z) = z^(" + Complex.toString2(s.fns.z_exponent_nova[0], s.fns.z_exponent_nova[1]) + ") - 1<br>";
            overview += tab + "Relaxation = " + Complex.toString2(s.fns.relaxation[0], s.fns.relaxation[1]) + "<br>";
            if (s.fns.nova_method == NOVA_NEWTON_HINES) {
                overview += tab + "k = " + Complex.toString2(s.fns.newton_hines_k[0], s.fns.newton_hines_k[1]) + "<br>";
            }
        } else if (Settings.isTwoFunctionsRootFindingMethodFormula(s.fns.function)) {
            overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
            if (s.fns.derivative_method == Derivative.DISABLED) {
                overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
            }

            if(s.fns.function == NEWTON_HINESFORMULA) {
                overview += tab + "k = " + Complex.toString2(s.fns.newton_hines_k[0], s.fns.newton_hines_k[1]) + "<br>";
            }
        } else if (Settings.isThreeFunctionsRootFindingMethodFormula(s.fns.function)) {
            overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";

            if (s.fns.derivative_method == Derivative.DISABLED) {
                overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
                overview += tab + "f''(z) = " + s.fns.user_ddfz_formula + "<br>";
            }

            if(s.fns.function == LAGUERREFORMULA) {
                overview += tab + "Degree = " + Complex.toString2(s.fns.laguerre_deg[0], s.fns.laguerre_deg[1]) + "<br>";
            }
        } else if (Settings.isOneFunctionsRootFindingMethodFormula(s.fns.function)) {
            overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
        }
        else if (Settings.isFourFunctionsRootFindingMethodFormula(s.fns.function)) {
            overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";

            if (s.fns.derivative_method == Derivative.DISABLED) {
                overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
                overview += tab + "f''(z) = " + s.fns.user_ddfz_formula + "<br>";
                overview += tab + "f'''(z) = " + s.fns.user_dddfz_formula + "<br>";
            }
        }
        else if (s.fns.function == USER_FORMULA) {
            overview += tab + "z = " + s.fns.user_formula + "<br>";
            overview += tab + "c = " + s.fns.user_formula2 + "<br>";
            if (!s.ds.domain_coloring) {
                if (s.fns.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                } else if (s.fns.bail_technique == 1) {
                    overview += tab + "Converging Algorithm<br>";
                }
                else {
                    overview += tab + "Escaping or Converging Algorithm<br>";
                }
            }
        } else if (s.fns.function == USER_FORMULA_ITERATION_BASED) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 0]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_iteration_based[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 1]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_iteration_based[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 2]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_iteration_based[2] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 3]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_iteration_based[3] + "<br>";
            if (!s.ds.domain_coloring) {
                if (s.fns.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                } else if (s.fns.bail_technique == 1) {
                    overview += tab + "Converging Algorithm<br>";
                }
                else {
                    overview += tab + "Escaping or Converging Algorithm<br>";
                }
            }
        } else if (s.fns.function == USER_FORMULA_CONDITIONAL) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_formula_conditions[0] + " > " + s.fns.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_condition_formula[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_formula_conditions[0] + " &#60; " + s.fns.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_condition_formula[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_formula_conditions[0] + " = " + s.fns.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_formula_condition_formula[2] + "<br>";
            if (!s.ds.domain_coloring) {
                if (s.fns.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                } else if (s.fns.bail_technique == 1) {
                    overview += tab + "Converging Algorithm<br>";
                }
                else {
                    overview += tab + "Escaping or Converging Algorithm<br>";
                }
            }
        } else if (s.fns.function == USER_FORMULA_COUPLED) {
            overview += tab + "a = " + s.fns.user_formula_coupled[0] + "<br>";
            overview += tab + "b = " + s.fns.user_formula_coupled[1] + "<br>";

            overview += tab + "Coupling = " + s.fns.coupling + "<br>";

            if (s.fns.coupling_method == 0) {
                overview += tab + "Simple Coupling<br>";
                overview += tab2 + "Final_Coupling = Coupling<br>";
            } else if (s.fns.coupling_method == 1) {
                overview += tab + "Cosine Coupling<br>";
                overview += tab2 + "Amplitude = " + s.fns.coupling_amplitude + "<br>";
                overview += tab2 + "Frequency = " + s.fns.coupling_frequency + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * cos(Current_Iteration * Frequency))<br>";
            } else {
                overview += tab + "Random Coupling<br>";
                overview += tab2 + "Amplitude = " + s.fns.coupling_amplitude + "<br>";
                overview += tab2 + "Seed = " + s.fns.coupling_seed + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * (Random_Sequence_Number - 0.5) * 2)<br>";
            }

            overview += tab + "z = Final_Coupling * (b - a) + a<br>";
            overview += tab + "z2 = Final_Coupling * (a - b) + b<br>";

            overview += tab + "z2(0) = " + s.fns.user_formula_coupled[2] + "<br>";

            if (!s.ds.domain_coloring) {
                if (s.fns.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                } else if (s.fns.bail_technique == 1) {
                    overview += tab + "Converging Algorithm<br>";
                }
                else {
                    overview += tab + "Escaping or Converging Algorithm<br>";
                }
            }
        } else if (s.fns.function == KLEINIAN) {
            overview += tab + "Moebius Tranformation Value = " + Complex.toString2(s.fns.kleinianLine[0], s.fns.kleinianLine[1]) + "<br>";
            overview += tab + "K = " + s.fns.kleinianK + "<br>";
            overview += tab + "M = " + s.fns.kleinianM + "<br>";
        } else if (s.fns.function == GENERIC_CaZbdZe) {
            overview += tab + "alpha = " + s.fns.gcs.alpha + "<br>";
            overview += tab + "beta = " + s.fns.gcs.beta + "<br>";
            overview += tab + "delta = " + s.fns.gcs.delta + "<br>";
            overview += tab + "epsilon = " + s.fns.gcs.epsilon + "<br>";
        } else if (s.fns.function == GENERIC_CpAZpBC) {
            overview += tab + "alpha = " + s.fns.gcps.alpha2 + "<br>";
            overview += tab + "beta = " + s.fns.gcps.beta2 + "<br>";
        } else if (s.fns.function == MAGNETIC_PENDULUM) {
            for (int k = 0; k < s.fns.mps.magnetLocation.length; k++) {
                if (s.fns.mps.magnetStrength[k][0] != 0 || s.fns.mps.magnetStrength[k][1] != 0) {
                    overview += tab + "Magnet = " + Complex.toString2(s.fns.mps.magnetLocation[k][0], s.fns.mps.magnetLocation[k][1]) + "<br>";
                    overview += tab2 + "Strength = " + Complex.toString2(s.fns.mps.magnetStrength[k][0], s.fns.mps.magnetStrength[k][1]) + "<br>";
                }
            }
            overview += tab + "Pendulum = " + Complex.toString2(s.fns.mps.pendulum[0], s.fns.mps.pendulum[1]) + "<br>";
            overview += tab + "Gravity = " + Complex.toString2(s.fns.mps.gravity[0], s.fns.mps.gravity[1]) + "<br>";
            overview += tab + "Friction = " + Complex.toString2(s.fns.mps.friction[0], s.fns.mps.friction[1]) + "<br>";
            overview += tab + "Height = " + s.fns.mps.height + "<br>";
            overview += tab + "Stepsize = " + Complex.toString2(s.fns.mps.stepsize, s.fns.mps.stepsize_im) + "<br>";
        } else if (s.fns.function == INERTIA_GRAVITY) {
            for (int k = 0; k < s.fns.igs.bodyLocation.length; k++) {
                if (s.fns.igs.bodyGravity[k][0] != 0 || s.fns.igs.bodyGravity[k][1] != 0) {
                    overview += tab + "Body = " + Complex.toString2(s.fns.igs.bodyLocation[k][0], s.fns.igs.bodyLocation[k][1]) + "<br>";
                    overview += tab2 + "Gravity = " + Complex.toString2(s.fns.igs.bodyGravity[k][0], s.fns.igs.bodyGravity[k][1]) + "<br>";
                }
            }
            overview += tab + "Inertia Contribution = " + Complex.toString2(s.fns.igs.inertia_contribution[0], s.fns.igs.inertia_contribution[1]) + "<br>";
            overview += tab + "Initial Inertia = " + Complex.toString2(s.fns.igs.initial_inertia[0], s.fns.igs.initial_inertia[1]) + "<br>";
            overview += tab + "Timestep = " + Complex.toString2(s.fns.igs.time_step[0], s.fns.igs.time_step[1]) + "<br>";
            overview += tab + "Scaling Function = " + inertiaGravityPullFunction[s.fns.igs.pull_scaling_function] + "<br>";

            if (s.fns.igs.pull_scaling_function == PULL_EXP) {
                overview += tab + "Exponent = " + s.fns.igs.inertia_exponent + "<br>";
            }
        } else if (s.fns.function == LYAPUNOV) {
            overview += tab + "A = " + s.fns.lpns.lyapunovA + "<br>";
            overview += tab + "B = " + s.fns.lpns.lyapunovB + "<br>";
            overview += tab + "C = " + s.fns.lpns.lyapunovC + "<br>";
            overview += tab + "D = " + s.fns.lpns.lyapunovD + "<br>";
            overview += tab + "Original Expression: r = " + s.fns.lpns.lyapunovExpression + "<br>";
            overview += tab + "Final Expression: r = " + String.join("; ", s.fns.lpns.lyapunovFinalExpression) + "<br>";
            overview += tab + "Function = " + s.fns.lpns.lyapunovFunction + "<br>";
            if (!s.fns.init_val) {
                overview += tab + "Initial Value = " + s.fns.lpns.lyapunovInitialValue + "<br>";
            }
            overview += tab + "Exponent = <font color='" + keyword_color + "'>average of sum</font>[<font color='" + condition_color + "'>log</font>(<font color='" + condition_color + "'>norm</font>(" + s.fns.lpns.lyapunovExponentFunction + "))]<br>";
            overview += tab + "Initial Iterations = " + s.fns.lpns.lyapunovInitializationIteratons + "<br>";
        } else if (s.fns.function == USER_FORMULA_NOVA) {

            overview += tab + Constants.novaMethods[s.fns.nova_method] + "<br>";

            if(Settings.isTwoFunctionsNovaFormula(s.fns.nova_method)) {
                overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
                if (s.fns.derivative_method == Derivative.DISABLED) {
                    overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
                }

                if(s.fns.nova_method == NOVA_NEWTON_HINES) {
                    overview += tab + "k = " + Complex.toString2(s.fns.newton_hines_k[0], s.fns.newton_hines_k[1]) + "<br>";
                }
            }
            else if(Settings.isThreeFunctionsNovaFormula(s.fns.nova_method)) {
                overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
                if (s.fns.derivative_method == Derivative.DISABLED) {
                    overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
                    overview += tab + "f''(z) = " + s.fns.user_ddfz_formula + "<br>";
                }

                if(s.fns.nova_method == NOVA_LAGUERRE) {
                    overview += tab + "Degree = " + Complex.toString2(s.fns.laguerre_deg[0], s.fns.laguerre_deg[1]) + "<br>";
                }
            }
            else if(Settings.isFourFunctionsNovaFormula(s.fns.nova_method)) {
                overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
                if (s.fns.derivative_method == Derivative.DISABLED) {
                    overview += tab + "f'(z) = " + s.fns.user_dfz_formula + "<br>";
                    overview += tab + "f''(z) = " + s.fns.user_ddfz_formula + "<br>";
                    overview += tab + "f'''(z) = " + s.fns.user_dddfz_formula + "<br>";
                }
            }
            else if(Settings.isOneFunctionsNovaFormula(s.fns.nova_method)) {
                overview += tab + "f(z) = " + s.fns.user_fz_formula + "<br>";
            }

            if(s.fns.useGlobalMethod) {
                overview += tab + "Global Method<br>";
                overview += tab2 + "Step Factor = " + Complex.toString2(s.fns.globalMethodFactor[0], s.fns.globalMethodFactor[1]) + "<br>";
            }
            overview += tab + "Relaxation = " + s.fns.user_relaxation_formula + "<br>";
            overview += tab + "Addend = " + s.fns.user_nova_addend_formula + "<br>";
        }
        else if (s.fns.function == NEWTON_THIRD_DEGREE_PARAMETER_SPACE) {
            overview += tab + "z = z - ((z - 1)*(z + 1)*(z - c)) / (-2*c*z + 3*z^2 - 1)<br>";
        }

        if (s.fns.function == DURAND_KERNER3 || s.fns.function == DURAND_KERNER4 || s.fns.function == DURAND_KERNERGENERALIZED3 || s.fns.function == DURAND_KERNERGENERALIZED8
        || s.fns.function == ABERTH_EHRLICH3 || s.fns.function == ABERTH_EHRLICH4 || s.fns.function == ABERTH_EHRLICHGENERALIZED3 || s.fns.function == ABERTH_EHRLICHGENERALIZED8) {
            overview += tab + "Root Initialization Method = " + Constants.rootInitializationMethod[0] + "<br>";
            overview += tab + "a = " + DurandKernerRootFindingMethod.A + "<br>";
        } else if (s.fns.function == DURAND_KERNERPOLY || s.fns.function == ABERTH_EHRLICHPOLY) {
            overview += tab + "Root Initialization Method = " + Constants.rootInitializationMethod[s.fns.root_initialization_method] + "<br>";
            if(s.fns.root_initialization_method != 1) {
                overview += tab + "a = " + Complex.toString2(s.fns.durand_kerner_init_val[0], s.fns.durand_kerner_init_val[1]) + "<br>";
            }
        }

        if (s.fns.function == NEWTON_HINES3 || s.fns.function == NEWTON_HINES4 || s.fns.function == NEWTON_HINESGENERALIZED3 || s.fns.function == NEWTON_HINESGENERALIZED8 || s.fns.function == NEWTON_HINESSIN || s.fns.function == NEWTON_HINESCOS) {
            overview += tab + "k = " + NewtonHinesRootFindingMethod.K + "<br>";
        } else if (s.fns.function == NEWTON_HINESPOLY) {
            overview += tab + "k = " + Complex.toString2(s.fns.newton_hines_k[0], s.fns.newton_hines_k[1]) + "<br>";
        }

        if ((s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) && s.fns.burning_ship) {
            overview += tab + "Burning Ship<br>";
            overview += tab2 + "z = abs(z), applied before the function evaluation<br>";
        }

        if ((s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) && s.fns.mandel_grass && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
            overview += tab + "Mandel Grass = " + Complex.toString2(s.fns.mandel_grass_vals[0], s.fns.mandel_grass_vals[1]) + "<br>";
            overview += tab2 + "z = z + (MG * z)/(norm(z)), applied after the function evaluation<br>";
        }
        overview += "<br>";

        if (s.fns.julia) {
            if(s.fns.juliter) {
                overview += "<b><font color='red'>Julia Seed:</font></b> " + BigComplex.toString2Pretty(s.xJuliaCenter, s.yJuliaCenter) + " is replacing the c constant in the formula after the iteration " + s.fns.juliterIterations + " (Juliter)<br><br>";
            }
            else {
                overview += "<b><font color='red'>Julia Seed:</font></b> " + BigComplex.toString2Pretty(s.xJuliaCenter, s.yJuliaCenter)  + " is replacing the c constant in the formula<br><br>";
            }
        }

        overview += "<b><font color='red'>Plane Transformation:</font></b> Applies the transformation \"" + PlanesMenu.planeNames[s.fns.plane_type] + "\" to every plane point c<br>";

        if (s.fns.plane_type == CIRCLEINVERSION_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br>";
            overview += tab + "Radius = " + s.fns.plane_transform_radius + "<br>";
        } else if (s.fns.plane_type == INFLECTION_PLANE || s.fns.plane_type == FOLDUP_PLANE || s.fns.plane_type == FOLDDOWN_PLANE || s.fns.plane_type == FOLDRIGHT_PLANE || s.fns.plane_type == FOLDLEFT_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br>";
        } else if (s.fns.plane_type == FOLDIN_PLANE || s.fns.plane_type == FOLDOUT_PLANE) {
            overview += tab + "Radius = " + s.fns.plane_transform_radius + "<br>";
        } else if (s.fns.plane_type == PINCH_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.fns.plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + s.fns.plane_transform_radius + "<br>";
            overview += tab + "Amount = " + s.fns.plane_transform_amount + "<br>";
        } else if (s.fns.plane_type == SHEAR_PLANE) {
            overview += tab + "Scale Real = " + s.fns.plane_transform_scales[0] + "<br>";
            overview += tab + "Scale Imaginary = " + s.fns.plane_transform_scales[1] + "<br>";
        } else if (s.fns.plane_type == SKEW_PLANE) {
            overview += tab + "Angle = " + s.fns.plane_transform_angle + " degrees<br>";
            overview += tab + "Angle2 = " + s.fns.plane_transform_angle2 + " degrees<br>";
        } else if (s.fns.plane_type == RIPPLES_PLANE) {
            overview += tab + "Amplitude Real = " + s.fns.plane_transform_scales[0] + "<br>";
            overview += tab + "Amplitude Imaginary = " + s.fns.plane_transform_scales[1] + "<br>";
            overview += tab + "Wavelength Real = " + s.fns.plane_transform_wavelength[0] + "<br>";
            overview += tab + "Wavelength Imaginary = " + s.fns.plane_transform_wavelength[1] + "<br>";
            overview += tab + "Wave Type = " + waveTypes[s.fns.waveType] + "<br>";
        } else if (s.fns.plane_type == TWIRL_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.fns.plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + s.fns.plane_transform_radius + "<br>";
        } else if (s.fns.plane_type == KALEIDOSCOPE_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.fns.plane_transform_angle + " degrees<br>";
            overview += tab + "Angle 2 = " + s.fns.plane_transform_angle2 + " degrees<br>";
            overview += tab + "Radius = " + s.fns.plane_transform_radius + "<br>";
            overview += tab + "Sides = " + s.fns.plane_transform_sides + "<br>";
        } else if (s.fns.plane_type == BIPOLAR_PLANE || s.fns.plane_type == INVERSED_BIPOLAR_PLANE) {
            overview += tab + "Foci = (" + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + ") and (" + Complex.toString2(-s.fns.plane_transform_center[0], -s.fns.plane_transform_center[1]) + ")<br>";
        } else if (s.fns.plane_type == USER_PLANE) {
            if (s.fns.user_plane_algorithm == 0) {
                overview += tab + "z = " + s.fns.user_plane + "<br>";
            } else {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_plane_conditions[0] + " > " + s.fns.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_plane_condition_formula[0] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_plane_conditions[0] + " &#60; " + s.fns.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_plane_condition_formula[1] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_plane_conditions[0] + " = " + s.fns.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.user_plane_condition_formula[2] + "<br>";
            }
        }
        else if (s.fns.plane_type == INFLECTIONS_PLANE) {
            overview += tab + "Inflection Power = " + s.fns.inflectionsPower + "<br>";
            for(int i = 0; i < s.fns.inflections_re.size(); i++) {
                overview += tab + "Inflection Point " + (i + 1) + " = " + Complex.toString2(s.fns.inflections_re.get(i), s.fns.inflections_im.get(i)) + "<br>";
            }
        }

        overview += "<br>";

        if (!s.fns.perturbation && !s.fns.init_val && s.functionSupportsC() && (s.fns.julia || s.julia_map)) {
            if (s.fns.apply_plane_on_julia && s.fns.apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed and the Whole Plane (c)<br><br>";
            } else if (s.fns.apply_plane_on_julia) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to Whole Plane (c)<br><br>";
            } else if (s.fns.apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed<br><br>";
            }
        }

        if (s.functionSupportsC() && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
            if (s.fns.ips.influencePlane == USER_PLANE_INFLUENCE) {
                if (s.fns.ips.user_plane_influence_algorithm == 0) {
                    overview += "<b><font color='red'>Plane Influence:</font></b> User Plane Influence<br>";
                    overview += tab + "z = " + s.fns.ips.userFormulaPlaneInfluence + "<br>";
                } else {
                    overview += "<b><font color='red'>Plane Influence:</font></b> User Plane Influence Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.ips.user_plane_influence_conditions[0] + " > " + s.fns.ips.user_plane_influence_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.ips.user_plane_influence_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.ips.user_plane_influence_conditions[0] + " &#60; " + s.fns.ips.user_plane_influence_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.ips.user_plane_influence_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.ips.user_plane_influence_conditions[0] + " = " + s.fns.ips.user_plane_influence_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.ips.user_plane_influence_condition_formula[2] + "<br>";
                }
                overview += "<br>";
            } else {
                overview += "<b><font color='red'>Plane Influence:</font></b> " + PlaneInfluenceMenu.planeInfluenceNames[s.fns.ips.influencePlane] + "<br><br>";
            }
        }

        if (!s.fns.init_val || s.isPertubationTheoryInUse() || s.isHighPrecisionInUse()) {
            String res = TaskDraw.getDefaultInitialValue();

            if (!res.equals("")) {
                if(res.equals("c")) {
                    overview += "<b><font color='red'>Initial Value:</font></b> Default Value<br>";
                }
                else {
                    overview += "<b><font color='red'>Initial Value:</font></b> Static Value<br>";
                }
                overview += tab + "z(0) = " + res + "<br>";
                overview += "<br>";
            }
        } else {
            if((s.julia_map || s.fns.julia) && !(s.fns.function == MainWindow.USER_FORMULA || s.fns.function == MainWindow.USER_FORMULA_CONDITIONAL || s.fns.function == MainWindow.USER_FORMULA_ITERATION_BASED || s.fns.function == MainWindow.USER_FORMULA_COUPLED
                    || s.fns.function == MainWindow.USER_FORMULA_NOVA || s.fns.function == MainWindow.LYAPUNOV || s.fns.function == MainWindow.LAMBDA_FN_FN)) {

                String res = TaskDraw.getDefaultInitialValue();

                if (!res.equals("")) {
                    overview += "<b><font color='red'>Initial Value:</font></b> Default Value<br>";
                    overview += tab + "z(0) = " + res + "<br>";
                    overview += "<br>";
                }

            }
            else {
                if (s.fns.variable_init_value) {
                    if (s.fns.user_initial_value_algorithm == 0) {
                        overview += "<b><font color='red'>Initial Value:</font></b> Variable Value<br>";
                        overview += tab + "z(0) = " + s.fns.initial_value_user_formula + "<br>";
                    } else {
                        overview += "<b><font color='red'>Initial Value:</font></b> Variable Value Conditional<br>";
                        overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_initial_value_conditions[0] + " > " + s.fns.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.fns.user_initial_value_condition_formula[0] + "<br>";
                        overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_initial_value_conditions[0] + " &#60; " + s.fns.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.fns.user_initial_value_condition_formula[1] + "<br>";
                        overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_initial_value_conditions[0] + " = " + s.fns.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.fns.user_initial_value_condition_formula[2] + "<br>";
                    }
                } else {
                    overview += "<b><font color='red'>Initial Value:</font></b> Static Value<br>";
                    overview += tab + "z(0) = " + Complex.toString2(s.fns.initial_vals[0], s.fns.initial_vals[1]) + "<br>";
                }
                overview += "<br>";
            }

        }

        if (s.fns.perturbation && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
            if (s.fns.variable_perturbation) {
                if (s.fns.user_perturbation_algorithm == 0) {
                    overview += "<b><font color='red'>Initial Perturbation:</font></b> Variable Value<br>";
                    overview += tab + "z(0) = z(0) + " + s.fns.perturbation_user_formula + "<br>";
                } else {
                    overview += "<b><font color='red'>Initial Perturbation:</font></b> Variable Value Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_perturbation_conditions[0] + " > " + s.fns.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.fns.user_perturbation_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_perturbation_conditions[0] + " &#60; " + s.fns.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.fns.user_perturbation_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_perturbation_conditions[0] + " = " + s.fns.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.fns.user_perturbation_condition_formula[2] + "<br>";
                }
            } else {
                overview += "<b><font color='red'>Initial Perturbation:</font></b> Static Value<br>";
                overview += tab + "z(0) = z(0) + " + Complex.toString2(s.fns.perturbation_vals[0], s.fns.perturbation_vals[1]) + "<br>";
            }
            overview += "<br>";
        }


        if(!s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
            if (s.fns.preffs.functionFilter == USER_FUNCTION_FILTER) {
                if (s.fns.preffs.user_function_filter_algorithm == 0) {
                    overview += "<b><font color='red'>Pre Function Filter:</font></b> User Filter<br>";
                    overview += tab + "z = " + s.fns.preffs.userFormulaFunctionFilter + "<br>";
                } else {
                    overview += "<b><font color='red'>Pre Function Filter:</font></b> User Filter Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.preffs.user_function_filter_conditions[0] + " > " + s.fns.preffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.preffs.user_function_filter_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.preffs.user_function_filter_conditions[0] + " &#60; " + s.fns.preffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.preffs.user_function_filter_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.preffs.user_function_filter_conditions[0] + " = " + s.fns.preffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.preffs.user_function_filter_condition_formula[2] + "<br>";
                }
                overview += "<br>";
            } else {
                overview += "<b><font color='red'>Pre Function Filter:</font></b> " + FunctionFiltersMenu.functionFilternNames[s.fns.preffs.functionFilter] + "<br><br>";
            }

            if (s.fns.postffs.functionFilter == USER_FUNCTION_FILTER) {
                if (s.fns.postffs.user_function_filter_algorithm == 0) {
                    overview += "<b><font color='red'>Post Function Filter:</font></b> User Filter<br>";
                    overview += tab + "z = " + s.fns.postffs.userFormulaFunctionFilter + "<br>";
                } else {
                    overview += "<b><font color='red'>Post Function Filter:</font></b> User Filter Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.postffs.user_function_filter_conditions[0] + " > " + s.fns.postffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.postffs.user_function_filter_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.postffs.user_function_filter_conditions[0] + " &#60; " + s.fns.postffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.postffs.user_function_filter_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.postffs.user_function_filter_conditions[0] + " = " + s.fns.postffs.user_function_filter_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.fns.postffs.user_function_filter_condition_formula[2] + "<br>";
                }
                overview += "<br>";
            } else {
                overview += "<b><font color='red'>Post Function Filter:</font></b> " + FunctionFiltersMenu.functionFilternNames[s.fns.postffs.functionFilter] + "<br><br>";
            }
        }

        overview += "<b><font color='red'>Maximum Iterations:</font></b> " + s.max_iterations + "<br><br>";

        overview += "<b><font color='red'>User Point:</font></b> " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br><br>";

        boolean hasUserVariables = false;
        for(int i = 0; i < s.fns.variable_re.length; i++) {
            if (s.fns.variable_re[i] != 0 || s.fns.variable_im[i] != 0) {
                overview += "<b><font color='red'>User Variables:</font></b> <br>";
                hasUserVariables = true;
                break;
            }
        }

        if(hasUserVariables) {
            for (int i = 0; i < s.fns.variable_re.length; i++) {
                if (s.fns.variable_re[i] != 0 || s.fns.variable_im[i] != 0) {
                    overview += tab + "v" + (i + 1) + " = " + Complex.toString2(s.fns.variable_re[i], s.fns.variable_im[i]) + "<br>";
                }
            }

            overview += "<br>";
        }

        if(s.isPeriodInUse()) {
            overview += "<b><font color='red'>Period:</font></b> " + s.fns.period + "<br><br>";
        }

        if (!s.ds.domain_coloring && !s.isConvergingType() && s.fns.function != KLEINIAN && (s.fns.function != LYAPUNOV || (s.fns.function == LYAPUNOV && !s.fns.lpns.lyapunovskipBailoutCheck))) {

            if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_NO_BAILOUT) {
                overview += "<b><font color='red'>Bailout Condition:</font></b> The escaping condition is disabled<br>";
            } else {
                overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when the criterion defined by the bailout condition \"" + BailoutConditionsMenu.bailoutConditionNames[s.fns.bailout_test_algorithm] + "\" is met<br>";
                if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z))^" + s.fns.n_norm + " + abs(im(z))^" + s.fns.n_norm + ")^(1/" + s.fns.n_norm + ") >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
                    String greater = "", equal = "", lower = "";

                    if (s.fns.bailout_test_comparison == GREATER) { // >
                        greater = "Escaped";
                        equal = "Not Escaped";
                        lower = "Not Escaped";
                    } else if (s.fns.bailout_test_comparison == GREATER_EQUAL) { // >=
                        greater = "Escaped";
                        equal = "Escaped";
                        lower = "Not Escaped";
                    } else if (s.fns.bailout_test_comparison == LOWER) { // <
                        greater = "Not Escaped";
                        equal = "Not Escaped";
                        lower = "Escaped";
                    } else if (s.fns.bailout_test_comparison == LOWER_EQUAL) { // <=
                        greater = "Not Escaped";
                        equal = "Escaped";
                        lower = "Escaped";
                    } else if (s.fns.bailout_test_comparison == EQUAL) { // ==
                        greater = "Not Escaped";
                        equal = "Escaped";
                        lower = "Not Escaped";
                    } else if (s.fns.bailout_test_comparison == NOT_EQUAL) { // !=
                        greater = "Escaped";
                        equal = "Not Escaped";
                        lower = "Escaped";
                    }

                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.bailout_test_user_formula + " > " + s.fns.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.bailout_test_user_formula + " &#60; " + s.fns.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.bailout_test_user_formula + " = " + s.fns.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_CIRCLE) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_SQUARE) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout or abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_RHOMBUS) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) + abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_REAL_STRIP) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_HALFPLANE) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_FIELD_LINES) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) / re(p) >= bailout and im(z) / im(p) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                    overview += tab + "p is the previous value of z<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_CROSS) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout and abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_IM_STRIP) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                } else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_RE_IM_SQUARED) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(re(z) + im(z))^2 >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if (s.fns.bailout_test_algorithm == BAILOUT_CONDITION_CUSTOM) {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z) >= bailout^(1 / sqrt(bailout))]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
            }
            overview += "<br>";

            if (s.fns.bailout_test_algorithm != BAILOUT_CONDITION_NO_BAILOUT) {
                overview += "<b><font color='red'>Bailout:</font></b> " + s.fns.bailout + "<br><br>";
                overview += "<b><font color='red'>Skip Bailout Condition Iterations:</font></b> " + s.fns.skip_bailout_iterations + "<br><br>";
            }

            if (s.isMagnetType()) {
                overview += "<b><font color='red'>Bailout Condition 2:</font></b> Escaping when the criterion defined by the convergent bailout condition \"" + ConvergentBailoutConditionsMenu.convergentBailoutConditionNames[s.fns.cbs.convergent_bailout_test_algorithm] + "\" is met<br>";
                if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_CIRCLE) {
                    overview += tab + "Escaping when a complex value almost reaches 1 + 0i (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - 1 - 0i) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_SQUARE) {
                    overview += tab + "Escaping when a complex value almost reaches 1 + 0i (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - 1 - 0i)) &#60;= convergent bailout or abs(im(z - 1 - 0i)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_RHOMBUS) {
                    overview += tab + "Escaping when a complex value almost reaches 1 + 0i (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - 1 - 0i)) + abs(im(z - 1 - 0i)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_NNORM) {
                    overview += tab + "Escaping when a complex value almost reaches 1 + 0i (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z - 1 - 0i))^" + s.fns.n_norm + " + abs(im(z - 1 - 0i))^" + s.fns.n_norm + ")^(1/" + s.fns.n_norm + ") &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if (s.fns.cbs.convergent_bailout_test_algorithm ==  Constants.CONVERGENT_BAILOUT_CONDITION_USER) {
                    String greater = "", equal = "", lower = "";

                    if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER) { // >
                        greater = "Converged";
                        equal = "Not Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER_EQUAL) { // >=
                        greater = "Converged";
                        equal = "Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER) { // <
                        greater = "Not Converged";
                        equal = "Not Converged";
                        lower = "Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER_EQUAL) { // <=
                        greater = "Not Converged";
                        equal = "Converged";
                        lower = "Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == EQUAL) { // ==
                        greater = "Not Converged";
                        equal = "Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == NOT_EQUAL) { // !=
                        greater = "Converged";
                        equal = "Not Converged";
                        lower = "Converged";
                    }

                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " > " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula+ " &#60; " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " = " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
                }

                overview += "<br>";

                if (s.fns.cbs.convergent_bailout_test_algorithm != Constants.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT) {
                    overview += "<b><font color='red'>Convergent Bailout:</font></b> " + TaskDraw.getConvergentBailout() + "<br><br>";
                    overview += "<b><font color='red'>Skip Convergent Bailout Condition Iterations:</font></b> " + s.fns.skip_convergent_bailout_iterations + "<br><br>";
                }

            }
            else if(s.isEscapingOrConvergingType()) {
                overview += "<b><font color='red'>Bailout Condition 2:</font></b> Escaping when the criterion defined by the convergent bailout condition \"" + ConvergentBailoutConditionsMenu.convergentBailoutConditionNames[s.fns.cbs.convergent_bailout_test_algorithm] + "\" is met<br>";

                if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_CIRCLE) {
                    overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - p) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_SQUARE) {
                    overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - p)) &#60;= convergent bailout or abs(im(z - p)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_RHOMBUS) {
                    overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - p)) + abs(im(z - p)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_NNORM) {
                    overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z - p))^" + s.fns.n_norm + " + abs(im(z - p))^" + s.fns.n_norm + ")^(1/" + s.fns.n_norm + ") &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                    overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
                }
                else if (s.fns.cbs.convergent_bailout_test_algorithm ==  Constants.CONVERGENT_BAILOUT_CONDITION_USER) {
                    String greater = "", equal = "", lower = "";

                    if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER) { // >
                        greater = "Converged";
                        equal = "Not Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER_EQUAL) { // >=
                        greater = "Converged";
                        equal = "Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER) { // <
                        greater = "Not Converged";
                        equal = "Not Converged";
                        lower = "Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER_EQUAL) { // <=
                        greater = "Not Converged";
                        equal = "Converged";
                        lower = "Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == EQUAL) { // ==
                        greater = "Not Converged";
                        equal = "Converged";
                        lower = "Not Converged";
                    } else if (s.fns.cbs.convergent_bailout_test_algorithm == NOT_EQUAL) { // !=
                        greater = "Converged";
                        equal = "Not Converged";
                        lower = "Converged";
                    }

                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " > " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula+ " &#60; " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " = " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
                }

                overview += "<br>";

                if (s.fns.cbs.convergent_bailout_test_algorithm != Constants.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT) {
                    overview += "<b><font color='red'>Convergent Bailout:</font></b> " + TaskDraw.getConvergentBailout() + "<br><br>";
                    overview += "<b><font color='red'>Skip Convergent Bailout Condition Iterations:</font></b> " + s.fns.skip_convergent_bailout_iterations + "<br><br>";
                }
            }

        } else if (!s.ds.domain_coloring && s.fns.function != KLEINIAN && s.fns.function != MAGNETIC_PENDULUM) {

            overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when the criterion defined by the convergent bailout condition \"" + ConvergentBailoutConditionsMenu.convergentBailoutConditionNames[s.fns.cbs.convergent_bailout_test_algorithm] + "\" is met<br>";

            if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_CIRCLE) {
                overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - p) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_SQUARE) {
                overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - p)) &#60;= convergent bailout or abs(im(z - p)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_RHOMBUS) {
                overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z - p)) + abs(im(z - p)) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.fns.cbs.convergent_bailout_test_algorithm == Constants.CONVERGENT_BAILOUT_CONDITION_NNORM) {
                overview += tab + "Escaping when two consecutive complex values are almost the same (convergence)<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z - p))^" + s.fns.n_norm + " + abs(im(z - p))^" + s.fns.n_norm + ")^(1/" + s.fns.n_norm + ") &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if (s.fns.cbs.convergent_bailout_test_algorithm ==  Constants.CONVERGENT_BAILOUT_CONDITION_USER) {
                String greater = "", equal = "", lower = "";

                if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER) { // >
                    greater = "Converged";
                    equal = "Not Converged";
                    lower = "Not Converged";
                } else if (s.fns.cbs.convergent_bailout_test_algorithm == GREATER_EQUAL) { // >=
                    greater = "Converged";
                    equal = "Converged";
                    lower = "Not Converged";
                } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER) { // <
                    greater = "Not Converged";
                    equal = "Not Converged";
                    lower = "Converged";
                } else if (s.fns.cbs.convergent_bailout_test_algorithm == LOWER_EQUAL) { // <=
                    greater = "Not Converged";
                    equal = "Converged";
                    lower = "Converged";
                } else if (s.fns.cbs.convergent_bailout_test_algorithm == EQUAL) { // ==
                    greater = "Not Converged";
                    equal = "Converged";
                    lower = "Not Converged";
                } else if (s.fns.cbs.convergent_bailout_test_algorithm == NOT_EQUAL) { // !=
                    greater = "Converged";
                    equal = "Not Converged";
                    lower = "Converged";
                }

                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " > " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula+ " &#60; " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.cbs.convergent_bailout_test_user_formula + " = " + s.fns.cbs.convergent_bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
            }

            overview += "<br>";

            if (s.fns.cbs.convergent_bailout_test_algorithm != Constants.CONVERGENT_BAILOUT_CONDITION_NO_BAILOUT) {
                overview += "<b><font color='red'>Convergent Bailout:</font></b> " + TaskDraw.getConvergentBailout() + "<br><br>";
                overview += "<b><font color='red'>Skip Convergent Bailout Condition Iterations:</font></b> " + s.fns.skip_convergent_bailout_iterations + "<br><br>";
            }


        } else if (!s.ds.domain_coloring && s.fns.function == KLEINIAN) {
            overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when a value reaches out of bounds<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[Im(z) &#60; 0 or Im(z) > " + s.fns.kleinianLine[0] + "]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br><br>";
        } else if (!s.ds.domain_coloring && s.fns.function == MAGNETIC_PENDULUM) {
            overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when the maximum iterations value is reached<br><br>";
        }

        overview += "<b><font color='red'>Rotation:</font></b> " + s.fns.rotation + " <font color='" + keyword_color + "'>degrees about</font> " + BigComplex.toString2Pretty(s.fns.rotation_center[0], s.fns.rotation_center[1], s.size) + "<br><br>";

        overview += "<b><font color='red'>Stretch Factor:</font></b> " + s.height_ratio + "<br><br>";

        if(s.js.enableJitter) {
            overview += "<b><font color='red'>Jitter:</font></b><br>";
            overview += tab + "Shape = " + Constants.jitterShape[s.js.jitterShape] + "<br>";
            overview += tab + "Seed = " + s.js.jitterSeed + "<br>";
            overview += tab + "Scale = " + s.js.jitterScale + "<br><br>";
        }

        if (!s.ds.domain_coloring) {

            if((s.isPertubationTheoryInUse() || s.isHighPrecisionInUse()) && s.fns.out_coloring_algorithm == MainWindow.DISTANCE_ESTIMATOR) {
                overview += "<b><font color='red'>Out Coloring Mode:</font></b> " + OutColoringModesMenu.outColoringNames[0] + "<br>";
            }
            else {
                overview += "<b><font color='red'>Out Coloring Mode:</font></b> " + OutColoringModesMenu.outColoringNames[s.fns.out_coloring_algorithm] + "<br>";
            }

            if (s.fns.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
                if (s.fns.user_out_coloring_algorithm == 0) {
                    overview += tab + "out = " + s.fns.outcoloring_formula + "<br>";
                } else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_outcoloring_conditions[0] + " > " + s.fns.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.fns.user_outcoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_outcoloring_conditions[0] + " &#60; " + s.fns.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.fns.user_outcoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_outcoloring_conditions[0] + " = " + s.fns.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.fns.user_outcoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";

            overview += "<b><font color='red'>In Coloring Mode:</font></b> " + InColoringModesMenu.inColoringNames[s.fns.in_coloring_algorithm] + "<br>";
            if (s.fns.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                if (s.fns.user_in_coloring_algorithm == 0) {
                    overview += tab + "in = " + s.fns.incoloring_formula + "<br>";
                } else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_incoloring_conditions[0] + " > " + s.fns.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.fns.user_incoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_incoloring_conditions[0] + " &#60;" + s.fns.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.fns.user_incoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.fns.user_incoloring_conditions[0] + " = " + s.fns.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.fns.user_incoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";

            if (s.pps.sts.statistic) {
                if (s.pps.sts.statisticGroup == 0) {
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Algorithm = " + Constants.statisticalColoringName[s.pps.sts.statistic_type] + "<br>";
                    if (s.pps.sts.statistic_type == Constants.STRIPE_AVERAGE) {
                        overview += tab2 + "Stripe Density = " + s.pps.sts.stripeAvgStripeDensity + "<br>";
                    } else if (s.pps.sts.statistic_type == Constants.COS_ARG_DIVIDE_NORM_AVERAGE) {
                        overview += tab2 + "Stripe Density = " + s.pps.sts.cosArgStripeDensity + "<br>";
                    } else if (s.pps.sts.statistic_type == Constants.COS_ARG_DIVIDE_INVERSE_NORM) {
                        overview += tab2 + "Stripe Density = " + s.pps.sts.cosArgInvStripeDensity + "<br>";
                        overview += tab2 + "Stripe Denominator Factor = " + s.pps.sts.StripeDenominatorFactor + "<br>";
                    }
                    else if (s.pps.sts.statistic_type == Constants.ATOM_DOMAIN_BOF60_BOF61) {
                        overview += tab2 + "Norm Type = " + Constants.atomNormTypes[s.pps.sts.atomNormType] + "<br>";
                        if(s.pps.sts.atomNormType == 3) {
                            overview += tab3 + "N-Norm = " + s.pps.sts.atomNNorm + "<br>";
                        }
                        if (s.pps.sts.showAtomDomains) {
                            overview += tab2 + "Shows atom domains<br>";
                        }
                    }
                    else if(s.pps.sts.statistic_type == Constants.DISCRETE_LAGRANGIAN_DESCRIPTORS) {
                        overview += tab2 + "Power = " + s.pps.sts.lagrangianPower + "<br>";
                        overview += tab2 + "Norm Type = " + Constants.langNormTypes[s.pps.sts.langNormType] + "<br>";
                        if(s.pps.sts.langNormType == 4) {
                            overview += tab3 + "N-Norm = " + s.pps.sts.langNNorm + "<br>";
                        }
                    }
                    else if(s.pps.sts.statistic_type == Constants.TWIN_LAMPS) {
                        overview += tab2 + "Point = " + Complex.toString2(s.pps.sts.twlPoint[0], s.pps.sts.twlPoint[1]) + "<br>";
                        overview += tab2 + "Function = " + Constants.twinLampsFunction[s.pps.sts.twlFunction] + "<br>";
                    }
                    else if(s.pps.sts.statistic_type == Constants.CHECKERS) {
                        overview += tab2 + "Pattern Scale = " + s.pps.sts.patternScale + "<br>";
                        overview += tab2 + "Norm Type = " + Constants.atomNormTypes[s.pps.sts.checkerNormType] + "<br>";
                        if(s.pps.sts.checkerNormType == 3) {
                            overview += tab3 + "N-Norm = " + s.pps.sts.checkerNormValue + "<br>";
                        }
                    }
                } else if (s.pps.sts.statisticGroup == 1){
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "User Statistical Formula: " + s.pps.sts.user_statistic_formula + "<br>";
                    overview += tab + "Reduction Method = " + Constants.reductionMethod[s.pps.sts.reductionFunction] + "<br>";
                    overview += tab + "Initial value = " + s.pps.sts.user_statistic_init_value + "<br>";

                    if (s.pps.sts.useIterations && (s.pps.sts.reductionFunction == Constants.REDUCTION_MAX || s.pps.sts.reductionFunction == Constants.REDUCTION_MIN)) {
                        overview += tab + "Using Similar Iterations<br>";
                    }

                    if (s.isMagnetType() || s.isEscapingOrConvergingType()) {
                        overview += tab + (s.pps.sts.statistic_escape_type == ESCAPING ? "Escaping" : "Converging") + "<br>";
                    }
                }
                else if (!s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse() && s.pps.sts.statisticGroup == 2) {
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Equicontinuity<br>";
                    overview += tab2 + "Delta = " + s.pps.sts.equicontinuityDelta + "<br>";
                    overview += tab2 + "Denominator Factor = " + s.pps.sts.equicontinuityDenominatorFactor + "<br>";
                    if(s.pps.sts.equicontinuityInvertFactor) {
                        overview += tab2 + "Inverted Factor<br>";
                    }

                    if(s.pps.sts.equicontinuityOverrideColoring) {
                        if(s.pps.sts.equicontinuityColorMethod != 4) {
                            overview += tab2 + "Argument Value = " + Constants.equicontinuityArgs[s.pps.sts.equicontinuityArgValue] + "<br>";
                        }
                        overview += tab2 + "Coloring Method = " + Constants.equicontinuityColorMethods[s.pps.sts.equicontinuityColorMethod] + "<br>";

                        if(s.pps.sts.equicontinuityColorMethod != 3 && s.pps.sts.equicontinuityColorMethod != 4) {
                            overview += tab2 + "Saturation/Chroma = " + s.pps.sts.equicontinuitySatChroma + "<br>";
                        }
                        else {
                            overview += tab2 + "Mixing Method = " + Constants.colorMethod[s.pps.sts.equicontinuityMixingMethod] + "<br>";

                            if(s.pps.sts.equicontinuityMixingMethod == 3) {
                                overview += tab2 + "Color Blending = " + s.pps.sts.equicontinuityBlending + "<br>";
                            }
                        }
                    }


                }
                else if (s.pps.sts.statisticGroup == 4){
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Root Coloring<br>";

                    if(s.pps.sts.rootShading) {
                        overview += tab + "Root Contouring<br>";

                        overview += tab2 + "Iterations Scaling = " + s.pps.sts.rootIterationsScaling + "<br>";
                        overview += tab2 + "Contour Function = " + rootShadingFunction[s.pps.sts.rootShadingFunction] + "<br>";

                        if(s.pps.sts.rootShadingFunction != 5) {
                            overview += tab2 + "Contour Color Mode = " + colorMethod[s.pps.sts.rootContourColorMethod] + "<br>";
                        }

                        if(s.pps.sts.revertRootShading) {
                            overview += tab2 + "Inverted Coloring" + "<br>";
                        }

                        if(s.pps.sts.highlightRoots) {
                            overview += tab2 + "Root highlighting" + "<br>";
                        }

                        if(s.pps.sts.rootShadingFunction != 5) {
                            if (s.pps.sts.rootContourColorMethod == 3) {
                                overview += tab3 + "Color Blending = " + s.pps.sts.rootBlending + "<br>";
                            }

                            if (s.pps.sts.rootSmooting) {
                                overview += tab2 + "Uses Contour Smoothing" + "<br>";
                            }
                        }
                    }
                }

                if(s.pps.sts.statisticGroup == 3) {
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                }

                if (s.pps.sts.statisticGroup == 3 || s.pps.sts.normalMapCombineWithOtherStatistics){
                    if(s.pps.sts.useNormalMap) {
                        overview += tab + "Normal Map<br>";
                        overview += tab2 + "Height = " + s.pps.sts.normalMapHeight + "<br>";
                        overview += tab2 + "Angle = " + s.pps.sts.normalMapAngle + "<br>";

                        if(s.pps.sts.normalMapUseSecondDerivative) {
                            overview += tab2 + "Using second derivative<br>";
                        }

                        if(s.pps.sts.normalMapOverrideColoring) {
                            overview += tab2 + "Light = " + s.pps.sts.normalMapLightFactor + "<br>";
                            overview += tab2 + "Color Mode = " + colorMethod[s.pps.sts.normalMapColorMode] + "<br>";

                            if(s.pps.sts.normalMapColorMode == 3) {
                                overview += tab3 + "Color Blending = " + s.pps.sts.normalMapBlending + "<br>";
                            }
                        }
                    }

                    if(s.pps.sts.normalMapUseDE) {
                        overview += tab + "Distance Estimation<br>";

                        if (s.pps.sts.normalMapInvertDE) {
                            overview += tab2 + "Lower Limit Factor = " + s.pps.sts.normalMapDEfactor + "<br>";
                            if(!s.pps.sts.normalMapDEAAEffect) {
                                overview += tab2 + "Upper Limit Factor = " + s.pps.sts.normalMapDEUpperLimitFactor + "<br>";
                            }
                            overview += tab2 + "Inverted Coloring<br><br>";
                        } else {
                            overview += tab2 + "Lower Limit Factor = " + s.pps.sts.normalMapDEfactor + "<br>";
                            if(!s.pps.sts.normalMapDEAAEffect) {
                                overview += tab2 + "Upper Limit Factor = " + s.pps.sts.normalMapDEUpperLimitFactor + "<br>";
                            }
                        }

                        if(s.pps.sts.normalMapDEAAEffect) {
                            overview += tab2 + "Fade Method = " + Constants.FadeAlgs[s.pps.sts.normalMapDeFadeAlgorithm] + "<br>";
                            if(s.pps.sts.normalMapDEUseColorPerDepth) {
                                overview += tab2 + "Coloring Per-Depth<br>";
                                overview += tab3 + "Factor = " + s.pps.sts.normalMapDEOffsetFactor + "<br>";
                                overview += tab3 + "Offset = " + s.pps.sts.normalMapDEOffset + "<br>";
                            }
                        }
                    }

                    if(s.pps.sts.normalMapOverrideColoring) {
                        overview += tab + "Coloring Algorithm = " + normalMapColoringMethods[s.pps.sts.normalMapColoring] + "<br>";

                        if(s.pps.sts.normalMapColoring == 2 || s.pps.sts.normalMapColoring == 3) {
                            overview += tab2 + "Distance Estimator Factor = " + s.pps.sts.normalMapDistanceEstimatorfactor + "<br>";
                        }
                    }

                }

                if(s.pps.sts.statisticGroup != 2 || (!s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse())) {
                    if (s.pps.sts.statisticIncludeEscaped) {
                        overview += tab + "Includes escaped points<br>";
                    }
                    if (s.pps.sts.statisticIncludeNotEscaped) {
                        overview += tab + "Includes not escaped points<br>";
                    }

                    if (s.pps.sts.statisticGroup == 1) {
                        if (s.pps.sts.useSmoothing && (s.pps.sts.reductionFunction == Constants.REDUCTION_SUM || s.pps.sts.reductionFunction == Constants.REDUCTION_SUB)) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.pps.sts.useAverage && (s.pps.sts.reductionFunction == Constants.REDUCTION_SUM || s.pps.sts.reductionFunction == Constants.REDUCTION_SUB)) {
                            overview += tab + "Using Average<br>";
                        }

                        if(s.pps.sts.lastXItems > 0) {
                            overview += tab + "Using only last "  + s.pps.sts.lastXItems +" samples<br>";
                        }
                    } else if (s.pps.sts.statisticGroup == 0) {
                        if (s.pps.sts.useSmoothing && s.pps.sts.statistic_type != Constants.ATOM_DOMAIN_BOF60_BOF61 && s.pps.sts.statistic_type != Constants.COS_ARG_DIVIDE_INVERSE_NORM) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.pps.sts.useAverage && s.pps.sts.statistic_type != Constants.ATOM_DOMAIN_BOF60_BOF61 && s.pps.sts.statistic_type != Constants.COS_ARG_DIVIDE_INVERSE_NORM && s.pps.sts.statistic_type != Constants.TWIN_LAMPS) {
                            overview += tab + "Using Average<br>";
                        }

                        if(s.pps.sts.lastXItems > 0) {
                            overview += tab + "Using only last "  + s.pps.sts.lastXItems +" samples<br>";
                        }
                    } else if (s.pps.sts.statisticGroup == 2) {
                        if (s.pps.sts.useSmoothing) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.pps.sts.useAverage) {
                            overview += tab + "Using Average<br>";
                        }
                    }

                    if(s.pps.sts.statisticGroup != 4) {
                        overview += tab + "Intensity = " + s.pps.sts.statistic_intensity + "<br><br>";
                    }
                    else {
                        overview += "<br>";
                    }
                }
            }
        }

        if ((!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1)) && !s.useDirectColor) {

            if(s.gps.useGeneratedPaletteOutColoring) {
                overview += "<b><font color='red'>Palette(Out):</font></b> " + Constants.generatedPalettes[s.gps.generatedPaletteOutColoringId] + "<br>";
            }
            else {
                overview += "<b><font color='red'>Palette(Out):</font></b> " + PaletteMenu.paletteNames[s.ps.color_choice] + "<br>";
            }
            overview += tab + "Palette Offset = " + s.ps.color_cycling_location + "<br>";
            overview += tab + "Transfer Function = " + ColorTransferMenu.colorTransferNames[s.ps.transfer_function] + "<br>";
            if(s.ps.transfer_function != DEFAULT) {
                overview += tab + "Color Density = " + s.ps.color_density + "<br>";
            }
            overview += tab + "Color Intensity = " + s.ps.color_intensity + "<br><br>";

            if (!s.ds.domain_coloring && s.usePaletteForInColoring) {
                if(s.gps.useGeneratedPaletteInColoring) {
                    overview += "<b><font color='red'>Palette(In):</font></b> " + Constants.generatedPalettes[s.gps.generatedPaletteInColoringId] + "<br>";
                }
                else {
                    overview += "<b><font color='red'>Palette(In):</font></b> " + PaletteMenu.paletteNames[s.ps2.color_choice] + "<br>";
                }
                overview += tab + "Palette Offset = " + s.ps2.color_cycling_location + "<br>";
                overview += tab + "Transfer Function = " + ColorTransferMenu.colorTransferNames[s.ps2.transfer_function] + "<br>";
                if(s.ps2.transfer_function != DEFAULT) {
                    overview += tab + "Color Density = " + s.ps2.color_density + "<br>";
                }
                overview += tab + "Color Intensity = " + s.ps2.color_intensity + "<br><br>";
            }

            if ((s.ds.domain_coloring && s.ds.domain_coloring_mode == 1)) {
                if (s.fns.smoothing) {
                    overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                    overview += tab + "Fractional Transfer = " + Constants.smoothingFractionalTransfer[s.fns.smoothing_fractional_transfer_method] + "<br>";
                    overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";
                }
            } else if (s.fns.smoothing) {
                overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                overview += tab + "Fractional Transfer = " + Constants.smoothingFractionalTransfer[s.fns.smoothing_fractional_transfer_method] + "<br>";
                if (s.fns.escaping_smooth_algorithm == 0) {
                    overview += tab + "Escaping Smooth Algorithm 1<br>";
                } else {
                    overview += tab + "Escaping Smooth Algorithm 2<br>";
                }
                if (s.fns.converging_smooth_algorithm == 0) {
                    overview += tab + "Converging Smooth Algorithm 1<br>";
                } else {
                    overview += tab + "Converging Smooth Algorithm 2<br>";
                }
                overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";
            }
        }

        if (!s.ds.domain_coloring && !s.useDirectColor) {

            if (s.fns.tcs.trueColorOut) {
                overview += "<b><font color='red'>Out True Coloring Mode:</font></b><br>";
                if (s.fns.tcs.trueColorOutMode == 0) {
                    overview += tab + "Preset = " + Constants.trueColorModes[s.fns.tcs.trueColorOutPreset] + "<br>";
                } else {
                    overview += tab + "Color Space = " + Constants.trueColorSpaces[s.fns.tcs.outTcColorSpace] + "<br>";
                    switch (s.fns.tcs.outTcColorSpace) {
                        case ColorSpaceConverter.RGB:
                            overview += tab2 + "R = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "G = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.XYZ:
                            overview += tab2 + "X = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "Y = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "Z = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HSB:
                            overview += tab2 + "H = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "S = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HWB:
                            overview += tab2 + "H = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "W = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HSL:
                            overview += tab2 + "H = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "S = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "L = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.RYB:
                            overview += tab2 + "R = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "Y = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.LAB:
                            overview += tab2 + "L = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "A = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.LCH_ab:
                            overview += tab2 + "L = " + s.fns.tcs.outTcComponent1 + "<br>";
                            overview += tab2 + "C = " + s.fns.tcs.outTcComponent2 + "<br>";
                            overview += tab2 + "H = " + s.fns.tcs.outTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.DIRECT:
                            overview += tab2 + "Direct = " + s.fns.tcs.outTcComponent1 + "<br>";
                            break;
                        case ColorSpaceConverter.PALETTE:
                            overview += tab2 + "Palete = " + s.fns.tcs.outTcComponent1 + "<br>";
                            break;
                        case ColorSpaceConverter.GRADIENT:
                            overview += tab2 + "Gradient = " + s.fns.tcs.outTcComponent1 + "<br>";
                            break;
                    }
                }

                overview += "<br>";
            }

            if (s.fns.tcs.trueColorIn) {
                overview += "<b><font color='red'>In True Coloring Mode:</font></b><br>";
                if (s.fns.tcs.trueColorInMode == 0) {
                    overview += tab + "Preset = " + Constants.trueColorModes[s.fns.tcs.trueColorInPreset] + "<br>";
                } else {
                    overview += tab + "Color Space = " + Constants.trueColorSpaces[s.fns.tcs.inTcColorSpace] + "<br>";
                    switch (s.fns.tcs.inTcColorSpace) {
                        case ColorSpaceConverter.RGB:
                            overview += tab2 + "R = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "G = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.XYZ:
                            overview += tab2 + "X = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "Y = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "Z = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HSB:
                            overview += tab2 + "H = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "S = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HWB:
                            overview += tab2 + "H = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "W = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.HSL:
                            overview += tab2 + "H = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "S = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "L = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.RYB:
                            overview += tab2 + "R = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "Y = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.LAB:
                            overview += tab2 + "L = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "A = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "B = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.LCH_ab:
                            overview += tab2 + "L = " + s.fns.tcs.inTcComponent1 + "<br>";
                            overview += tab2 + "C = " + s.fns.tcs.inTcComponent2 + "<br>";
                            overview += tab2 + "H = " + s.fns.tcs.inTcComponent3 + "<br>";
                            break;
                        case ColorSpaceConverter.DIRECT:
                            overview += tab2 + "Direct = " + s.fns.tcs.inTcComponent1 + "<br>";
                            break;
                        case ColorSpaceConverter.PALETTE:
                            overview += tab2 + "Palete = " + s.fns.tcs.inTcComponent1 + "<br>";
                            break;
                        case ColorSpaceConverter.GRADIENT:
                            overview += tab2 + "Gradient = " + s.fns.tcs.inTcComponent1 + "<br>";
                            break;
                    }
                }

                overview += "<br>";
            }

            if (s.pbs.palette_gradient_merge) {
                overview += "<b><font color='red'>Palette/Gradient Merging:</font></b><br>";
                overview += tab + "Merging Type = " + Constants.colorMethod[s.pbs.merging_type] + "<br>";
                if (s.pbs.merging_type == 3) {
                    overview += tab2 + "Color Blending = " + s.pbs.palette_blending + "<br>";
                }
                overview += tab + "Gradient Offset = " + s.pbs.gradient_offset + "<br>";
                overview += tab + "Gradient Intensity = " + s.pbs.gradient_intensity + "<br><br>";
            }

            if (s.exterior_de && !s.isPertubationTheoryInUse() && !s.isHighPrecisionInUse()) {
                overview += "<b><font color='red'>Distance Estimation:</font></b><br>";

                if (s.inverse_dem) {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                } else {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br><br>";
                }
            }

            if (!periodicity_checking && s.pps.ots.useTraps) {
                overview += "<b><font color='red'>Orbit Traps:</font></b><br>";
                overview += tab + "Shape = " + Constants.orbitTrapsNames[s.pps.ots.trapType] + "<br>";
                overview += tab + "Center = " + Complex.toString2(s.pps.ots.trapPoint[0], s.pps.ots.trapPoint[1]) + "<br>";

                int type = (s.pps.ots.trapType == Constants.IMAGE_TRANSPARENT_TRAP || s.pps.ots.trapType == Constants.IMAGE_TRAP )&& s.pps.ots.checkType == Constants.TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE ? Constants.TRAP_CHECK_TYPE_TRAPPED_FIRST : s.pps.ots.checkType;
                overview += tab + "Check type = " + Constants.orbitTrapCheckTypes[type] + "<br>";

                if(s.pps.ots.skipTrapCheckForIterations > 0) {
                    overview += tab + "Skip Trap Check for the first iterations = " + s.pps.ots.skipTrapCheckForIterations + "<br>";
                }

                if (s.pps.ots.trapType != Constants.IMAGE_TRANSPARENT_TRAP && s.pps.ots.trapType != Constants.SUPER_FORMULA_ORBIT_TRAP && s.pps.ots.trapType != Constants.ATOM_DOMAIN_TRAP && s.pps.ots.trapType != Constants.SQUARE_ATOM_DOMAIN_TRAP && s.pps.ots.trapType != Constants.RHOMBUS_ATOM_DOMAIN_TRAP && s.pps.ots.trapType != Constants.NNORM_ATOM_DOMAIN_TRAP && s.pps.ots.trapType != Constants.GOLDEN_RATIO_SPIRAL_TRAP && s.pps.ots.trapType != Constants.STALKS_TRAP) {
                    overview += tab + "Length = " + s.pps.ots.trapLength + "<br>";
                }

                if (!(s.pps.ots.trapType == Constants.IMAGE_TRANSPARENT_TRAP || s.pps.ots.trapType == Constants.ATOM_DOMAIN_TRAP || s.pps.ots.trapType == Constants.SQUARE_ATOM_DOMAIN_TRAP || s.pps.ots.trapType == Constants.RHOMBUS_ATOM_DOMAIN_TRAP || s.pps.ots.trapType == Constants.NNORM_ATOM_DOMAIN_TRAP || s.pps.ots.trapType == Constants.POINT_RHOMBUS_TRAP || s.pps.ots.trapType == Constants.POINT_SQUARE_TRAP || s.pps.ots.trapType == Constants.POINT_TRAP || s.pps.ots.trapType == Constants.POINT_N_NORM_TRAP)) {
                    overview += tab + "Width = " + s.pps.ots.trapWidth + "<br>";
                }

                if (s.pps.ots.trapType == Constants.POINT_N_NORM_TRAP || s.pps.ots.trapType == Constants.N_NORM_TRAP || s.pps.ots.trapType == Constants.N_NORM_CROSS_TRAP || s.pps.ots.trapType == Constants.N_NORM_POINT_TRAP || s.pps.ots.trapType == Constants.N_NORM_POINT_N_NORM_TRAP || s.pps.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP || s.pps.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP || s.pps.ots.trapType == Constants.STALKS_POINT_N_NORM_TRAP || s.pps.ots.trapType == Constants.STALKS_N_NORM_TRAP || s.pps.ots.trapType == Constants.NNORM_ATOM_DOMAIN_TRAP) {
                    overview += tab + "Norm = " + s.pps.ots.trapNorm + "<br>";
                }

                if (s.pps.ots.trapType == Constants.CROSS_TRAP || s.pps.ots.trapType == Constants.RE_TRAP || s.pps.ots.trapType == Constants.IM_TRAP || s.pps.ots.trapType == Constants.CIRCLE_CROSS_TRAP || s.pps.ots.trapType == Constants.SQUARE_CROSS_TRAP || s.pps.ots.trapType == Constants.RHOMBUS_CROSS_TRAP || s.pps.ots.trapType == Constants.N_NORM_CROSS_TRAP || s.pps.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_CROSS_TRAP || s.pps.ots.trapType == Constants.STALKS_CROSS_TRAP) {
                    overview += tab + "Line Function = " + Constants.orbitTrapLineTypes[s.pps.ots.lineType] + "<br>";
                }

                if(s.pps.ots.trapType == Constants.SUPER_FORMULA_ORBIT_TRAP) {
                    overview += tab + "m1 = " + s.pps.ots.sfm1 + "<br>";
                    overview += tab + "m2 = " + s.pps.ots.sfm2 + "<br>";
                    overview += tab + "n1 = " + s.pps.ots.sfn1 + "<br>";
                    overview += tab + "n2 = " + s.pps.ots.sfn2 + "<br>";
                    overview += tab + "n3 = " + s.pps.ots.sfn3 + "<br>";
                    overview += tab + "a = " + s.pps.ots.sfa + "<br>";
                    overview += tab + "b = " + s.pps.ots.sfa + "<br>";
                }

                if (s.pps.ots.trapType != Constants.IMAGE_TRAP && s.pps.ots.trapType != Constants.IMAGE_TRANSPARENT_TRAP) {
                    overview += tab + "Trap Color Method = " + Constants.colorMethod[s.pps.ots.trapColorMethod] + "<br>";

                    if (s.pps.ots.trapColorMethod == 3) {
                        overview += tab2 + "Trap Blending = " + s.pps.ots.trapBlending + "<br>";
                    }

                    if (s.pps.ots.trapMaxDistance != 0) {
                        overview += tab + "Max Distance = " + s.pps.ots.trapMaxDistance + "<br>";
                    }
                    overview += tab + "Interpolation percent = " + s.pps.ots.trapColorInterpolation + "<br>";

                    if (s.pps.ots.trapCellularStructure) {
                        overview += tab + "Uses cellular structure<br>";
                        if (s.pps.ots.trapCellularInverseColor) {
                            overview += tab2 + "Uses inverted coloring<br>";
                        }
                        overview += tab2 + "Cellular Size = " + s.pps.ots.trapCellularSize + "<br>";
                    }
                }

                if (s.pps.ots.trapIncludeEscaped) {
                    overview += tab + "Includes escaped points<br>";
                }
                if (s.pps.ots.trapIncludeNotEscaped) {
                    overview += tab + "Includes not escaped points<br>";
                }
                if (s.pps.ots.countTrapIterations) {
                    overview += tab + "Counts trap iterations<br>";
                }
                if (s.pps.ots.showOnlyTraps) {
                    overview += tab + "Shows only traps<br>";
                }

                if (s.pps.ots.trapType != Constants.IMAGE_TRAP && s.pps.ots.trapType != Constants.IMAGE_TRANSPARENT_TRAP) {
                    overview += tab + "Intensity = " + s.pps.ots.trapIntensity + "<br>";
                    overview += tab + "Height Function = " + Constants.trapHeightAlgorithms[s.pps.ots.trapHeightFunction] + "<br>";
                    
                    if(s.pps.ots.invertTrapHeight) {
                        overview += tab + "Inverts trap height<br>";
                    }
                }

                if(s.pps.ots.lastXItems > 0) {
                    overview += tab + "Using only last "  + s.pps.ots.lastXItems +" samples<br>";
                }

                overview += "<br>";
            }

            if (s.pps.hss.histogramColoring && ! (s.pps.sts.statistic && s.pps.sts.statisticGroup == 4)) {
                overview += "<b><font color='red'>Histogram Coloring:</font></b><br>";

                overview += tab + "Mapping = " + histogramMapping[s.pps.hss.hmapping] + "<br>";
                if(s.pps.hss.hmapping == 0) {
                    overview += tab2 + "Bin Granularity = " + s.pps.hss.histogramBinGranularity + "<br>";
                    overview += tab2 + "Density = " + s.pps.hss.histogramDensity + "<br>";
                }
                overview += tab2 + "Min Scaling = " + s.pps.hss.histogramScaleMin + "<br>";
                overview += tab2 + "Max Scaling = " + s.pps.hss.histogramScaleMax + "<br>";
                overview += tab + "Color Blending = " + s.pps.hss.hs_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + s.pps.hss.hs_noise_reducing_factor + "<br><br>";
            }
        }

        if (!s.useDirectColor) {
            for (int i = 0; i < s.post_processing_order.length; i++) {
                switch (s.post_processing_order[i]) {
                    case LIGHT:
                        if (s.pps.ls.lighting) {
                            overview += "<b><font color='red'>Light:</font></b><br>";
                            overview += tab + "Light Mode = " + Constants.lightModes[s.pps.ls.lightMode] + "<br>";
                            overview += tab + "Reflection Mode = " + Constants.reflectionModes[s.pps.ls.specularReflectionMethod] + "<br>";
                            overview += tab + "Direction = " + s.pps.ls.light_direction + " degrees<br>";
                            overview += tab + "Magnitude = " + s.pps.ls.light_magnitude + "<br>";
                            overview += tab + "Light Intensity = " + s.pps.ls.lightintensity + "<br>";
                            overview += tab + "Ambient Light = " + s.pps.ls.ambientlight + "<br>";
                            overview += tab + "Specular Intensity = " + s.pps.ls.specularintensity + "<br>";
                            overview += tab + "Shininess = " + s.pps.ls.shininess + "<br>";
                            overview += tab + "Height Transfer = " + Constants.lightTransfer[s.pps.ls.heightTransfer] + "<br>";
                            overview += tab + "Height Transfer Factor = " + s.pps.ls.heightTransferFactor + "<br>";
                            overview += tab + "Fractional Transfer = " + Constants.fractionalTransfer[s.pps.ls.fractionalTransfer] + "<br";
                            if(s.pps.ls.fractionalTransfer != 0) {
                                overview += tab + "Fractional Transfer Mode = " + Constants.fractionalTransferMode[s.pps.ls.fractionalTransferMode] + "<br";
                            }
                            overview += tab + "Fractional Smoothing = " + Constants.FadeAlgs[s.pps.ls.fractionalSmoothing] + "<br";
                            overview += tab + "Color Mode = " + Constants.colorMethod[s.pps.ls.colorMode] + "<br>";
                            if (s.pps.ls.colorMode == 3) {
                                overview += tab2 + "Color Blending = " + s.pps.ls.light_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.pps.ls.l_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case SLOPES:
                        if (s.pps.ss.slopes) {
                            overview += "<b><font color='red'>Slopes:</font></b><br>";
                            overview += tab + "Angle = " + s.pps.ss.SlopeAngle + " degrees<br>";
                            overview += tab + "Power = " + s.pps.ss.SlopePower + "<br>";
                            overview += tab + "Ratio = " + s.pps.ss.SlopeRatio + "<br>";
                            overview += tab + "Height Transfer = " + Constants.lightTransfer[s.pps.ss.heightTransfer] + "<br>";
                            overview += tab + "Height Transfer Factor = " + s.pps.ss.heightTransferFactor + "<br>";
                            overview += tab + "Fractional Transfer = " + Constants.fractionalTransfer[s.pps.ss.fractionalTransfer] + "<br";
                            if(s.pps.ss.fractionalTransfer != 0) {
                                overview += tab + "Fractional Transfer Mode = " + Constants.fractionalTransferMode[s.pps.ss.fractionalTransferMode] + "<br";
                            }
                            overview += tab + "Fractional Smoothing = " + Constants.FadeAlgs[s.pps.ss.fractionalSmoothing] + "<br";
                            overview += tab + "Color Mode = " + Constants.colorMethod[s.pps.ss.colorMode] + "<br>";
                            if (s.pps.ss.colorMode == 3) {
                                overview += tab2 + "Color Blending = " + s.pps.ss.slope_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.pps.ss.s_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case FAKE_DISTANCE_ESTIMATION:
                        if (!s.ds.domain_coloring && s.pps.fdes.fake_de) {
                            overview += "<b><font color='red'>Fake Distance Estimation:</font></b><br>";

                            if (s.pps.fdes.inverse_fake_dem) {
                                overview += tab + "Factor = " + s.pps.fdes.fake_de_factor + "<br>";
                                overview += tab + "Fade Method = " + Constants.FadeAlgs[s.pps.fdes.fade_algorithm] + "<br>";
                                overview += tab + "Inverted Coloring<br><br>";
                            } else {
                                overview += tab + "Factor = " + s.pps.fdes.fake_de_factor + "<br>";
                                overview += tab + "Fade Method = " + Constants.FadeAlgs[s.pps.fdes.fade_algorithm] + "<br><br>";
                            }
                        }
                        break;
                    case ENTROPY_COLORING:
                        if (!s.ds.domain_coloring && s.pps.ens.entropy_coloring) {
                            overview += "<b><font color='red'>Entropy Coloring:</font></b><br>";
                            overview += tab + "Factor = " + s.pps.ens.entropy_palette_factor + "<br>";
                            overview += tab + "Color Transfer Method = " + entropyMethod[s.pps.ens.entropy_algorithm] + "<br>";

                            if (s.pps.ens.entropy_algorithm == 0) {
                                overview += tab + "Offset = " + s.pps.ens.entropy_offset + "<br>";
                            }

                            overview += tab + "Color Blending = " + s.pps.ens.en_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.pps.ens.en_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case OFFSET_COLORING:
                        if (!s.ds.domain_coloring && s.pps.ofs.offset_coloring) {
                            overview += "<b><font color='red'>Offset Coloring:</font></b><br>";
                            overview += tab + "Offset = " + s.pps.ofs.post_process_offset + "<br>";
                            overview += tab + "Color Blending = " + s.pps.ofs.of_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.pps.ofs.of_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case RAINBOW_PALETTE:
                        if (!s.ds.domain_coloring && s.pps.rps.rainbow_palette) {
                            overview += "<b><font color='red'>Rainbow Palette:</font></b><br>";
                            overview += tab + "Factor = " + s.pps.rps.rainbow_palette_factor + "<br>";
                            overview += tab + "Color Transfer Method = " + rainbowMethod[s.pps.rps.rainbow_algorithm] + "<br>";

                            if (s.pps.rps.rainbow_algorithm == 0) {
                                overview += tab + "Offset = " + s.pps.rps.rainbow_offset + "<br>";
                            }

                            overview += tab + "Color Blending = " + s.pps.rps.rp_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.pps.rps.rp_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case NUMERICAL_DISTANCE_ESTIMATOR:
                        if (!s.ds.domain_coloring && s.pps.ndes.useNumericalDem) {
                            overview += "<b><font color='red'>Numerical Distance Estimator:</font></b><br>";
                            overview += tab + "Difference Method = " + differencesMethod[s.pps.ndes.differencesMethod] + "<br>";
                            overview += tab + "Transfer Factor = " + s.pps.ndes.distanceFactor + "<br>";
                            overview += tab + "Offset = " + s.pps.ndes.distanceOffset + "<br>";
                            overview += tab + "Color Blending = " + s.pps.ndes.numerical_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.pps.ndes.n_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case GREYSCALE_COLORING:
                        if (!s.ds.domain_coloring && s.pps.gss.greyscale_coloring) {
                            overview += "<b><font color='red'>Greyscale Coloring:</font></b><br>";
                            overview += tab + "Noise Reduction Factor = " + s.pps.gss.gs_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case CONTOUR_COLORING:
                        if (!s.ds.domain_coloring && s.pps.cns.contour_coloring) {
                            overview += "<b><font color='red'>Contour Coloring:</font></b><br>";
                            overview += tab + "Algorithm = " + Constants.contourColorAlgorithmNames[s.pps.cns.contour_algorithm] + "<br>";
                            overview += tab + "Color Method = " + Constants.colorMethod[s.pps.cns.contourColorMethod] + "<br>";
                            if (s.pps.cns.contourColorMethod == 3) {
                                overview += tab2 + "Color Blending = " + s.pps.cns.cn_blending + "<br>";
                            }
                            if(s.pps.cns.contourColorMethod == 0 || s.pps.cns.contourColorMethod == 2 || s.pps.cns.contourColorMethod == 3) {
                                overview += tab + "Min Contour Factor = " + s.pps.cns.min_contour + "<br>";
                            }
                            overview += tab + "Fractional Transfer = " + Constants.fractionalTransfer[s.pps.cns.fractionalTransfer] + "<br";
                            overview += tab + "Fractional Smoothing = " + Constants.FadeAlgs[s.pps.cns.fractionalSmoothing] + "<br";
                            overview += tab + "Noise Reduction Factor = " + s.pps.cns.cn_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case BUMP_MAPPING:
                        if (s.pps.bms.bump_map) {
                            overview += "<b><font color='red'>Bump Mapping:</font></b><br>";
                            overview += tab + "Light Direction = " + s.pps.bms.lightDirectionDegrees + " degrees<br>";
                            overview += tab + "Depth = " + s.pps.bms.bumpMappingDepth + "<br>";
                            overview += tab + "Strength = " + s.pps.bms.bumpMappingStrength + "<br>";
                            overview += tab + "Transfer Function = " + Constants.bumpTransferNames[s.pps.bms.bump_transfer_function] + "<br>";
                            overview += tab + "Transfer Factor = " + s.pps.bms.bump_transfer_factor + "<br>";
                            overview += tab + "Fractional Transfer = " + Constants.fractionalTransfer[s.pps.bms.fractionalTransfer] + "<br";
                            if(s.pps.bms.fractionalTransfer != 0) {
                                overview += tab + "Fractional Transfer Mode = " + Constants.fractionalTransferMode[s.pps.bms.fractionalTransferMode] + "<br";
                            }
                            overview += tab + "Fractional Smoothing = " + Constants.FadeAlgs[s.pps.bms.fractionalSmoothing] + "<br";
                            overview += tab + "Processing Method = " + Constants.bumpProcessingMethod[s.pps.bms.bumpProcessing] + "<br>";

                            if (s.pps.bms.bumpProcessing == 1 || s.pps.bms.bumpProcessing == 2) {
                                overview += tab + "Color Blending = " + s.pps.bms.bump_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.pps.bms.bm_noise_reducing_factor + "<br><br>";
                        }
                        break;
                }
            }
        }

        if (!s.useDirectColor) {
            overview += "<b><font color='red'>Color Blending:</font></b> " + ColorBlendingMenu.colorBlendingNames[s.color_blending.color_blending] + "<br>";
            overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";


            overview += "<b><font color='red'>Contour Factor:</font></b> " + s.contourFactor + "<br><br>";
        }

        if (s.ds.domain_coloring && !s.useDirectColor) {
            if (!s.ds.customDomainColoring) {
                overview += "<b><font color='red'>Domain Coloring:</font></b><br>";
                overview += tab + "Algorithm = " + Constants.domainAlgNames[s.ds.domain_coloring_alg] + "<br>";
                overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br>";
                overview += tab + "Processing Transfer Function = " + Constants.domainProcessingTransferNames[s.ds.domainProcessingTransfer] + "<br>";
                overview += tab + "Processing Factor = " + s.ds.domainProcessingHeightFactor + "<br>";
            } else {
                overview += "<b><font color='red'>Custom Domain Coloring:</font></b><br>";

                if (s.ds.drawColor) {
                    overview += tab + "Color = " + Constants.domainColors[s.ds.colorType] + "<br>";
                }

                if (s.ds.drawContour) {
                    overview += tab + "Contour = " + Constants.domainContours[s.ds.contourType] + "<br>";
                    overview += tab2 + "Color Method = " + Constants.colorMethod[s.ds.contourMethod] + "<br>";
                    if (s.ds.contourMethod == 3) {
                        overview += tab2 + "Color Blending = " + s.ds.contourBlending + "<br>";
                    }
                }

                if (s.ds.applyShading) {
                    overview += tab + "Shading = " + Constants.domainShading[s.ds.shadingType] + "<br>";
                    overview += tab2 + "Coloring Mode = " + Constants.domainShadingColor[s.ds.shadingColorAlgorithm] + "<br>";
                    if(s.ds.shadingColorAlgorithm == 0) {
                        overview += tab3 + "Saturation Adjustment = " + s.ds.saturation_adjustment + "<br>";
                    }
                    if(s.ds.shadingColorAlgorithm == 1) {
                        overview += tab3 + "Shading Factor = " + s.ds.shadingPercent + "<br>";
                    }
                    if(s.ds.invertShading) {
                        overview += tab2 + "Inverted<br>";
                    }
                }

                for (int i = 0; i < s.ds.domainOrder.length; i++) {
                    switch (s.ds.domainOrder[i]) {
                        case MainWindow.GRID:
                            if (s.ds.drawGrid) {
                                overview += tab + "Grid<br>";
                                overview += tab2 + "Width = " + s.ds.gridWidth + "<br>";
                                overview += tab2 + "Strength = " + s.ds.gridBlending + "<br>";
                                overview += tab2 + "Fading = " + Constants.circleAndGridFadeNames[s.ds.gridFadeFunction] + "<br>";
                                overview += tab2 + "Color Type = " + Constants.gridColorType[s.ds.grid_color_type] + "<br>";
                            }
                            break;
                        case MainWindow.CIRCLES:
                            if (s.ds.drawCircles) {
                                overview += tab + "Circles<br>";
                                overview += tab2 + "Width = " + s.ds.circleWidth + "<br>";
                                overview += tab2 + "Strength = " + s.ds.circlesBlending + "<br>";
                                overview += tab2 + "Fading = " + Constants.circleAndGridFadeNames[s.ds.circleFadeFunction] + "<br>";
                                overview += tab2 + "Color Type = " + Constants.circleColorType[s.ds.circle_color_type] + "<br>";
                            }
                            break;
                        case MainWindow.ISO_LINES:
                            if (s.ds.drawIsoLines) {
                                overview += tab + "Iso-Argument Lines<br>";
                                overview += tab2 + "Width = " + s.ds.iso_factor + "<br>";
                                overview += tab2 + "Strength = " + s.ds.isoLinesBlendingFactor + "<br>";
                                overview += tab2 + "Color Type = " + Constants.isoColorType[s.ds.iso_color_type] + "<br>";
                            }
                            break;
                    }
                }

                overview += tab + "Interpolation = " + Constants.color_interp_str[s.color_smoothing_method] + "<br>";
                overview += tab + "Circle Log Base = " + s.ds.logBase + "<br>";
                overview += tab + "Grid Spacing = " + s.ds.gridFactor + "<br>";
                overview += tab + "Grid Algorithm = " + Constants.gridAlgorithms[s.ds.gridAlgorithm] + "<br>";
                overview += tab + "Combine Algorithm = " + Constants.combineAlgorithms[s.ds.combineType] + "<br>";
                overview += tab + "Norm Type = " + s.ds.normType + "<br>";
                overview += tab + "Iso-Argument Line Distance = " + Constants.argumentLinesDistance[s.ds.iso_distance] + "<br>";
                overview += tab + "Processing Transfer Function = " + Constants.domainProcessingTransferNames[s.ds.domainProcessingTransfer] + "<br>";
                overview += tab + "Processing Factor = " + s.ds.domainProcessingHeightFactor + "<br>";

                if (!s.ds.mapNormReImWithAbsScale) {
                    overview += tab + "Max Norm/Re/Im Value = " + s.ds.max_norm_re_im_value + "<br>";
                }
            }

            overview += tab + "Processing / 3D" + "<br>";
            overview += tab2 + "Height Method = " + MainWindow.domainHeightNames[s.ds.domain_height_method] + "<br>";
            overview += tab2 + "Normalization = " + MainWindow.domainNormalization[s.ds.domain_height_normalization_method] + "<br>";
            overview += tab2 + "Transfer Function = " + MainWindow.domainProcessingTransferNames[s.ds.domainProcessingTransfer] + "<br>";
            overview += tab2 + "Transfer Factor = " + s.ds.domainProcessingHeightFactor + "<br>";
            overview += "<br>";
        }

        int active = 0;
        for (int i = 0; i < s.fs.filters.length; i++) {
            if (s.fs.filters[i]) {
                active++;
            }
        }

        if (active > 0) {
            overview += "<b><font color='red'>Filters:</font></b><br>";
            if (s.fs.filters[ANTIALIASING]) {
                overview += tab + FiltersMenu.filterNames[ANTIALIASING] + "<br>";
            }

            for (int i = 0; i < s.fs.filters_order.length; i++) {
                if (s.fs.filters[s.fs.filters_order[i]]) {
                    overview += tab + FiltersMenu.filterNames[s.fs.filters_order[i]] + "<br>";
                }
            }
            overview += "<br>";
        }

        overview += "</font></html>";

        textArea.setText(overview);

        if (!s.useDirectColor) {

            if (!s.ds.domain_coloring && s.usePaletteForInColoring) {
                JLabel palette_label = new ImageLabel(new ImageIcon(getOutColoringPalettePreview(s, s.ps.color_cycling_location, 800, 36)));
                palette_label.setToolTipText("Displays the active out-coloring palette.");

                JLabel palette_text_label = new JLabel("Palette(Out): (Length = " + paletteOutLength + ")");

                JLabel palette_in_label = new ImageLabel(new ImageIcon(getInColoringPalettePreview(s, s.ps2.color_cycling_location, 800, 36)));
                palette_in_label.setToolTipText("Displays the active in-coloring palette.");

                JLabel palette_in_text_label = new JLabel("Palette(In): (Length = " + paletteInLength + ")");

                JLabel gradient_label = new ImageLabel(new ImageIcon(getGradientPreview(s.gs, s.gs.gradient_offset, 800, 36)));
                gradient_label.setToolTipText("Displays the active gradient.");

                JLabel gradient_text_label = new JLabel("Gradient: (Length = " + gradientLength + ")");

                Object[] message = {
                    scroll_pane_2,
                    " ",
                    s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteOutColoring ? null : palette_text_label,
                        s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteOutColoring ? null : palette_label,
                        s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteInColoring ? null : palette_in_text_label,
                        s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteInColoring ? null : palette_in_label,
                    gradient_text_label,
                    gradient_label};

                textArea.setCaretPosition(0);

                JOptionPane.showMessageDialog(parent, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JLabel palette_label = new ImageLabel(new ImageIcon(getOutColoringPalettePreview(s, s.ps.color_cycling_location, 800, 36)));
                palette_label.setToolTipText("Displays the active out-coloring palette.");

                JLabel palette_text_label = new JLabel("Palette(Out): (Length = " + paletteOutLength + ")");

                JLabel gradient_label = new ImageLabel(new ImageIcon(getGradientPreview(s.gs, s.gs.gradient_offset, 800, 36)));
                gradient_label.setToolTipText("Displays the active gradient.");

                JLabel gradient_text_label = new JLabel("Gradient: (Length = " + gradientLength + ")");


                JButton saveOverview = new MyButton("Save Overview");

                saveOverview.setToolTipText("Saves the overview as an html file.");
                saveOverview.setFocusable(false);
                saveOverview.setIcon(MainWindow.getIcon("save.png"));
                saveOverview.setPreferredSize(new Dimension(150, 32));


                saveOverview.addActionListener(e -> {
                    JFileChooser file_chooser = new JFileChooser(MainWindow.SaveSettingsPath.isEmpty() ? "." : MainWindow.SaveSettingsPath);
                    file_chooser.setAcceptAllFileFilterUsed(false);
                    file_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

                    file_chooser.addChoosableFileFilter(new FileNameExtensionFilter("HTML (*.html)", "html"));

                    String name = "fractal overview " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH;mm;ss").format(LocalDateTime.now()) + ".html";
                    file_chooser.setSelectedFile(new File(name));

                    file_chooser.addPropertyChangeListener(JFileChooser.FILE_FILTER_CHANGED_PROPERTY, evt -> {
                        String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();
                        file_chooser.setSelectedFile(new File(file_name));
                    });

                    int returnVal = file_chooser.showDialog(parent, "Save Image");

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = file_chooser.getSelectedFile();

                            FileNameExtensionFilter filter = (FileNameExtensionFilter) file_chooser.getFileFilter();

                            if (!file.getAbsolutePath().endsWith(".html")) {
                                file = new File(file.getAbsolutePath() + ".html");
                            }

                            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                            writer.write(writeOverview(textArea));

                            writer.close();

                            MainWindow.SaveSettingsPath = file.getParent();

                        } catch (IOException ex) {
                        }

                    }

                });

                Object[] message = {
                    scroll_pane_2,
                    " ",
                        s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || (s.gps.useGeneratedPaletteOutColoring && (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1))) ? null : palette_text_label,
                        s.pps.sts.statistic && s.pps.sts.statisticGroup == 4 || (s.gps.useGeneratedPaletteOutColoring && (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1))) ? null : palette_label,
                    gradient_text_label,
                    gradient_label
                    ," ",
                        saveOverview,};

                textArea.setCaretPosition(0);

                JOptionPane.showMessageDialog(parent, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            Object[] message = {
                scroll_pane_2,};

            textArea.setCaretPosition(0);

            JOptionPane.showMessageDialog(parent, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private String writeOverview(JEditorPane jtp) {
        StringWriter buf = new StringWriter();
        HTMLWriter writer = new HTMLWriter(buf,
                (HTMLDocument)jtp.getDocument(), 0, jtp.getText().length());
        try {
            writer.write();
        } catch (IOException | BadLocationException ex) {

        }
        return buf.toString();
    }

    public static BufferedImage getGradientPreview(GradientSettings gs, int offset, int width, int height) {

        Color[] c = CustomPalette.getGradient(gs.colorA.getRGB(), gs.colorB.getRGB(), GRADIENT_LENGTH, gs.gradient_interpolation, gs.gradient_color_space, gs.gradient_reversed, offset);

        gradientLength = c.length;

        BufferedImage palette_preview = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();

        for (int j = 0; j < c.length; j++) {
            Color c2 = null;
            if (j == c.length - 1) {
                c2 = c[j];
            } else {
                c2 = c[j + 1];
            }
            GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c2);
            g.setPaint(gp);
            g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
        }

        return palette_preview;

    }

    public static BufferedImage getOutColoringPalettePreview(Settings s, int color_cycling_location, int width, int height) {

        boolean isSmooth = false;
        Color[] c = null;
        if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 0) {
            c = new Color[width];

            for (int i = 0; i < c.length; i++) {
                double h = ((double) i) / (c.length - 1);
                c[i] = new Color(DomainColoring.HSBcolor(h, color_cycling_location));
            }
            isSmooth = true;
        } else if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 2) {
            c = new Color[width];

            for (int i = 0; i < c.length; i++) {
                double h = ((double) i) / (c.length - 1);
                c[i] = new Color(DomainColoring.LCHabcolor(h, color_cycling_location));
            }
            isSmooth = true;
        }
        else if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 5) {
            c = new Color[width];

            for (int i = 0; i < c.length; i++) {
                double h = ((double) i) / (c.length - 1);
                c[i] = new Color(DomainColoring.LCHuvcolor(h, color_cycling_location));
            }
            isSmooth = true;
        }
        else if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 3) {
            c = new Color[width];

            for (int i = 0; i < c.length; i++) {
                double h = ((double) i) / (c.length - 1);
                c[i] = new Color(DomainColoring.Cubehelix1(h, color_cycling_location));
            }
            isSmooth = true;
        } else if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 4) {
            c = new Color[width];

            for (int i = 0; i < c.length; i++) {
                double h = ((double) i) / (c.length - 1);
                c[i] = new Color(DomainColoring.Cubehelix3(h, color_cycling_location));
            }
            isSmooth = true;
        } else if (s.ps.color_choice == DIRECT_PALETTE_ID) {
            c = PresetPalette.getPalette(s.ps.direct_palette, color_cycling_location);
        } else if (s.ps.color_choice < CUSTOM_PALETTE_ID) {
            c = PresetPalette.getPalette(s.ps.color_choice, color_cycling_location);
        } else if (s.ps.color_choice == CUSTOM_PALETTE_ID) {
            c = CustomPalette.getPalette(s.ps.custom_palette, s.ps.color_interpolation, s.ps.color_space, s.ps.reversed_palette, color_cycling_location, s.ps.scale_factor_palette_val, s.ps.processing_alg);
        } else {
            c = PresetPalette.getPalette(s.ps.color_choice, color_cycling_location);
        }

        paletteOutLength = c.length;

        BufferedImage palette_preview = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();
        for (int j = 0; j < c.length; j++) {
            if (s.fns.smoothing || isSmooth) {
                GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
            } else {
                g.setColor(c[j]);
                g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
            }
        }

        return palette_preview;
    }

    public static BufferedImage getInColoringPalettePreview(Settings s, int color_cycling_location, int width, int height) {

        Color[] c = null;
        if (s.ps2.color_choice == DIRECT_PALETTE_ID) {
            c = PresetPalette.getPalette(s.ps2.direct_palette, color_cycling_location);
        } else if (s.ps2.color_choice < CUSTOM_PALETTE_ID) {
            c = PresetPalette.getPalette(s.ps2.color_choice, color_cycling_location);
        } else if (s.ps2.color_choice == CUSTOM_PALETTE_ID) {
            c = CustomPalette.getPalette(s.ps2.custom_palette, s.ps2.color_interpolation, s.ps2.color_space, s.ps2.reversed_palette, color_cycling_location, s.ps2.scale_factor_palette_val, s.ps2.processing_alg);
        } else {
            c = PresetPalette.getPalette(s.ps2.color_choice, color_cycling_location);
        }

        paletteInLength = c.length;

        BufferedImage palette_preview = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();
        for (int j = 0; j < c.length; j++) {
            if (s.fns.smoothing) {
                GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / ((float)c.length), 0, c[j], (j + 1) * palette_preview.getWidth() / ((float)c.length), 0, c[(j + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / ((double)c.length), 0, (j + 1) * palette_preview.getWidth() / ((double)c.length) - j * palette_preview.getWidth() / ((double)c.length), palette_preview.getHeight()));
            } else {
                g.setColor(c[j]);
                g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
            }
        }

        return palette_preview;
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void setUIFontSize(int size) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value != null && value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, ((javax.swing.plaf.FontUIResource)value).deriveFont((float) size));
            }
        }
    }

    public static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if(version.startsWith("1.")) {
            version = version.substring(2, 3);
        } else {
            int dot = version.indexOf(".");
            if(dot != -1) { version = version.substring(0, dot); }
        } return Integer.parseInt(version);
    }

    public void displaySpecialHelp(Component ptr) {

        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(600, 440));

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);

        String help = "<html><center><font size='5' face='arial' color='blue'><b><u>Special Offset Removal</u></b></font></center><br>"
                + "<font size='4' face='arial'>"
                + "Disclaimer, the Offset Removal option is tightly coupled with the<br>"
                + "implementation of the current software and its only provided in order to<br>"
                + "bypass an implementation design.<br><br>"
                + "Coloring algorithms that apply an offset to the palette, based<br>"
                + "on a specific condition, (Binary Decomposition Variant, Biomorph, Escape Time + Grid,<br>"
                + "Escape Time + Field Lines Variant) will return a value offseted by 50<br>"
                + "The return value of these algorithms that contains the value 50 will be<br>"
                + "negative in order for the software to determine this special offset.<br>"
                + "For instance Binary Decomposition will produce the following:<br>"
                + "<b>(if im(z) &lt 0) then return -(n + 50) else return n</b>.<br><br>"
                + "This constant offset value of 50, is creating noisy images when<br>"
                + "applying processing algorithms like Bump-Mapping or Entropy Coloring.<br>"
                + "The same issue arises also in 3d mode, so in order to alleviate the problem,<br>"
                + "this constant needs to be subtracted before applying any processing.<br><br>"
                + "This action is already applied on the included coloring algorithms, but<br>"
                + "it will not be used for the User Out/In Coloring Algorithms<br>"
                + "If you want to set your own coloring algorithm that uses this offset, you<br>"
                + "must always choose the value of 50 to be the offset, and always produce a<br>"
                + "negative value. Under those conditions you can enable the removal of the special<br>"
                + "offset in order to produce better images when you use processing or 3d."
                + "</font></html>";

        textArea.setText(help);

        Object[] message = {
            scroll_pane_2,};

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(ptr, message, "Help", JOptionPane.QUESTION_MESSAGE);
    }

    public static JLabel createUserFormulaHtmlLabels(String supported_vars, String mode, String mode2) {

        return new JLabel("<html><br><b>Variables:</b>"
                + "<br>" + supported_vars + ", rand<br>"
                + "<b>Operations:</b><br>"
                + "+ - * / % ^ ( ) ,<br>"
                + "<b>Constants:</b><br>"
                + "pi, e, phi, c10, alpha, delta, maxnde<br>"
                + "<b>Complex Numbers:</b><br>"
                + "a + bi<br>"
                + "<b>Trigonometric Functions: f(z)</b><br>"
                + "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,<br>"
                + "acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,<br>"
                + "hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc<br>"
                + "<b>Other Functions: f(z)</b><br>"
                + "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec, flip, round,<br>"
                + "ceil, floor, trunc, deta, snorm, fib, hypot, f1, ... f60<br>"
                + "<b>Two Argument Functions: f(z, w)</b><br>"
                + "logn, bipol, ibipol, inflect, foldu, foldd, foldl, foldr, foldi, foldo, shear, cmp, fuzz, normn, rot, dist, sdist, root, f',<br>"
                + "f'', f''', g1, ... g60<br>"
                + "<b>Multiple Argument User Functions: f(z1, ... z10) or f(z1, ... z20)</b><br>"
                + "m1, ... m60, k1, ... k60<br><br>"
                + "Set the " + mode + "." + mode2
                + "</html>");

    }

    public static void showPerturbationTheoryHelp(JDialog dialog) {

        new PerturbationTheoryHelpDialog(dialog);

    }

    public static void showHighPrecisionHelp(JDialog dialog) {
        new HighPrecisionHelpDialog(dialog);
    }

    public static void exportL4jIni(String fileName, String contents) {

        Path path = Paths.get(fileName + ".l4j.ini");

        if(!Files.exists(path)) {
            try {
                byte[] strToBytes = contents.getBytes();
                Files.write(path, strToBytes);
            }
            catch (Exception ex) {}
        }
    }

    public static ArrayList<Integer> getAllFactors(int n) {
        ArrayList<Integer> factors = new ArrayList<>();
        int step = n % 2 == 0 ? 1 : 2;
        double sqrt = Math.sqrt(n);
        for (int i = 1; i <= sqrt; i += step) {
            if (n % i == 0) {
                factors.add(i);
                factors.add(n / i);
            }
        }
        Collections.sort(factors);
        return factors;
    }
}
