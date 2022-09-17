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

        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");
        String version = System.getProperty("java.version");

        if(!version.startsWith("1.8")) {
            return new Object[]{0, ""};
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
            Files.copy(src, Paths.get("UserDefinedFunctions.java"));

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

        if ((s.fns.function <= 9 || s.fns.function == MANDELPOLY || s.fns.function == MANDELBROTWTH) && s.fns.mandel_grass && !s.isPertubationTheoryInUse()) {
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

        if (s.functionSupportsC() && !s.isPertubationTheoryInUse()) {
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

        if (!s.fns.init_val || s.isPertubationTheoryInUse()) {
            String res = ThreadDraw.getDefaultInitialValue();

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

                String res = ThreadDraw.getDefaultInitialValue();

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

        if (s.fns.perturbation && !s.isPertubationTheoryInUse()) {
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


        if(!s.isPertubationTheoryInUse()) {
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

        overview += "<b><font color='red'>User Point:</font></b> " + Complex.toString2(s.fns.plane_transform_center[0], s.fns.plane_transform_center[1]) + "<br><br>";

        overview += "<b><font color='red'>Maximum Iterations:</font></b> " + s.max_iterations + "<br><br>";

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
                    overview += "<b><font color='red'>Convergent Bailout:</font></b> " + ThreadDraw.getConvergentBailout() + "<br><br>";
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
                    overview += "<b><font color='red'>Convergent Bailout:</font></b> " + ThreadDraw.getConvergentBailout() + "<br><br>";
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
                overview += "<b><font color='red'>Convergent Bailout:</font></b> " + ThreadDraw.getConvergentBailout() + "<br><br>";
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

            if(s.isPertubationTheoryInUse() && s.fns.out_coloring_algorithm == MainWindow.DISTANCE_ESTIMATOR) {
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

            if (s.sts.statistic) {
                if (s.sts.statisticGroup == 0) {
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Algorithm = " + Constants.statisticalColoringName[s.sts.statistic_type] + "<br>";
                    if (s.sts.statistic_type == Constants.STRIPE_AVERAGE) {
                        overview += tab2 + "Stripe Density = " + s.sts.stripeAvgStripeDensity + "<br>";
                    } else if (s.sts.statistic_type == Constants.COS_ARG_DIVIDE_NORM_AVERAGE) {
                        overview += tab2 + "Stripe Density = " + s.sts.cosArgStripeDensity + "<br>";
                    } else if (s.sts.statistic_type == Constants.COS_ARG_DIVIDE_INVERSE_NORM) {
                        overview += tab2 + "Stripe Density = " + s.sts.cosArgInvStripeDensity + "<br>";
                        overview += tab2 + "Stripe Denominator Factor = " + s.sts.StripeDenominatorFactor + "<br>";
                    }
                    else if (s.sts.statistic_type == Constants.ATOM_DOMAIN_BOF60_BOF61) {
                        overview += tab2 + "Norm Type = " + Constants.atomNormTypes[s.sts.atomNormType] + "<br>";
                        if(s.sts.atomNormType == 3) {
                            overview += tab3 + "N-Norm = " + s.sts.atomNNorm + "<br>";
                        }
                        if (s.sts.showAtomDomains) {
                            overview += tab2 + "Shows atom domains<br>";
                        }
                    }
                    else if(s.sts.statistic_type == Constants.DISCRETE_LAGRANGIAN_DESCRIPTORS) {
                        overview += tab2 + "Power = " + s.sts.lagrangianPower + "<br>";
                        overview += tab2 + "Norm Type = " + Constants.langNormTypes[s.sts.langNormType] + "<br>";
                        if(s.sts.langNormType == 4) {
                            overview += tab3 + "N-Norm = " + s.sts.langNNorm + "<br>";
                        }
                    }
                    else if(s.sts.statistic_type == Constants.TWIN_LAMPS) {
                        overview += tab2 + "Point = " + Complex.toString2(s.sts.twlPoint[0], s.sts.twlPoint[1]) + "<br>";
                        overview += tab2 + "Function = " + Constants.twinLampsFunction[s.sts.twlFunction] + "<br>";
                    }
                } else if (s.sts.statisticGroup == 1){
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "User Statistical Formula: " + s.sts.user_statistic_formula + "<br>";
                    overview += tab + "Reduction Method = " + Constants.reductionMethod[s.sts.reductionFunction] + "<br>";
                    overview += tab + "Initial value = " + s.sts.user_statistic_init_value + "<br>";

                    if (s.sts.useIterations && (s.sts.reductionFunction == Constants.REDUCTION_MAX || s.sts.reductionFunction == Constants.REDUCTION_MIN)) {
                        overview += tab + "Using Similar Iterations<br>";
                    }

                    if (s.isMagnetType() || s.isEscapingOrConvergingType()) {
                        overview += tab + (s.sts.statistic_escape_type == ESCAPING ? "Escaping" : "Converging") + "<br>";
                    }
                }
                else if (!s.isPertubationTheoryInUse() && s.sts.statisticGroup == 2) {
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Equicontinuity<br>";
                    overview += tab2 + "Delta = " + s.sts.equicontinuityDelta + "<br>";
                    overview += tab2 + "Denominator Factor = " + s.sts.equicontinuityDenominatorFactor + "<br>";
                    if(s.sts.equicontinuityInvertFactor) {
                        overview += tab2 + "Inverted Factor<br>";
                    }

                    if(s.sts.equicontinuityOverrideColoring) {
                        if(s.sts.equicontinuityColorMethod != 4) {
                            overview += tab2 + "Argument Value = " + Constants.equicontinuityArgs[s.sts.equicontinuityArgValue] + "<br>";
                        }
                        overview += tab2 + "Coloring Method = " + Constants.equicontinuityColorMethods[s.sts.equicontinuityColorMethod] + "<br>";

                        if(s.sts.equicontinuityColorMethod != 3 && s.sts.equicontinuityColorMethod != 4) {
                            overview += tab2 + "Saturation/Chroma = " + s.sts.equicontinuitySatChroma + "<br>";
                        }
                        else {
                            overview += tab2 + "Mixing Method = " + Constants.colorMethod[s.sts.equicontinuityMixingMethod] + "<br>";

                            if(s.sts.equicontinuityMixingMethod == 3) {
                                overview += tab2 + "Color Blending = " + s.sts.equicontinuityBlending + "<br>";
                            }
                        }
                    }


                }
                else if (s.sts.statisticGroup == 3){
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    if(s.sts.useNormalMap) {
                        overview += tab + "Normal Map<br>";
                        overview += tab2 + "Height = " + s.sts.normalMapHeight + "<br>";
                        overview += tab2 + "Angle = " + s.sts.normalMapAngle + "<br>";

                        if(s.sts.normalMapUseSecondDerivative) {
                            overview += tab2 + "Using second derivative<br>";
                        }

                        if(s.sts.normalMapOverrideColoring) {
                            overview += tab2 + "Light = " + s.sts.normalMapLightFactor + "<br>";
                            overview += tab2 + "Color Mode = " + colorMethod[s.sts.normalMapColorMode] + "<br>";

                            if(s.sts.normalMapColorMode == 3) {
                                overview += tab3 + "Color Blending = " + s.sts.normalMapBlending + "<br>";
                            }
                        }
                    }

                    if(s.sts.normalMapUseDE) {
                        overview += tab + "Distance Estimation<br>";

                        if (s.sts.normalMapInvertDE) {
                            overview += tab2 + "Lower Limit Factor = " + s.sts.normalMapDEfactor + "<br>";
                            if(!s.sts.normalMapDEAAEffect) {
                                overview += tab2 + "Upper Limit Factor = " + s.sts.normalMapDEUpperLimitFactor + "<br>";
                            }
                            overview += tab2 + "Inverted Coloring<br><br>";
                        } else {
                            overview += tab2 + "Lower Limit Factor = " + s.sts.normalMapDEfactor + "<br>";
                            if(!s.sts.normalMapDEAAEffect) {
                                overview += tab2 + "Upper Limit Factor = " + s.sts.normalMapDEUpperLimitFactor + "<br>";
                            }
                        }

                        if(s.sts.normalMapDEAAEffect) {
                            overview += tab2 + "Fade Method = " + Constants.deFadeAlgs[s.sts.normalMapDeFadeAlgorithm] + "<br>";
                        }
                    }

                    if(s.sts.normalMapOverrideColoring) {
                        overview += tab + "Coloring Algorithm = " + normalMapColoringMethods[s.sts.normalMapColoring] + "<br>";
                    }

                }
                else if (s.sts.statisticGroup == 4){
                    overview += "<b><font color='red'>Statistical Coloring:</font></b><br>";
                    overview += tab + "Root Coloring<br>";

                    if(s.sts.rootShading) {
                        overview += tab + "Root Contouring<br>";

                        overview += tab2 + "Iterations Scaling = " + s.sts.rootIterationsScaling + "<br>";
                        overview += tab2 + "Contour Function = " + rootShadingFunction[s.sts.rootShadingFunction] + "<br>";

                        if(s.sts.rootShadingFunction != 5) {
                            overview += tab2 + "Contour Color Mode = " + colorMethod[s.sts.rootContourColorMethod] + "<br>";
                        }

                        if(s.sts.revertRootShading) {
                            overview += tab2 + "Inverted Coloring" + "<br>";
                        }

                        if(s.sts.highlightRoots) {
                            overview += tab2 + "Root highlighting" + "<br>";
                        }

                        if(s.sts.rootShadingFunction != 5) {
                            if (s.sts.rootContourColorMethod == 3) {
                                overview += tab3 + "Color Blending = " + s.sts.rootBlending + "<br>";
                            }

                            if (s.sts.rootSmooting) {
                                overview += tab2 + "Uses Contour Smoothing" + "<br>";
                            }
                        }
                    }
                }

                if(s.sts.statisticGroup != 2 || !s.isPertubationTheoryInUse()) {
                    if (s.sts.statisticIncludeEscaped) {
                        overview += tab + "Includes escaped points<br>";
                    }
                    if (s.sts.statisticIncludeNotEscaped) {
                        overview += tab + "Includes not escaped points<br>";
                    }

                    if (s.sts.statisticGroup == 1) {
                        if (s.sts.useSmoothing && s.sts.reductionFunction == Constants.REDUCTION_SUM) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.sts.useAverage && s.sts.reductionFunction == Constants.REDUCTION_SUM) {
                            overview += tab + "Using Average<br>";
                        }
                    } else if (s.sts.statisticGroup == 0) {
                        if (s.sts.useSmoothing && s.sts.statistic_type != Constants.ATOM_DOMAIN_BOF60_BOF61 && s.sts.statistic_type != Constants.COS_ARG_DIVIDE_INVERSE_NORM && s.sts.statistic_type != Constants.TWIN_LAMPS) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.sts.useAverage && s.sts.statistic_type != Constants.ATOM_DOMAIN_BOF60_BOF61 && s.sts.statistic_type != Constants.COS_ARG_DIVIDE_INVERSE_NORM && s.sts.statistic_type != Constants.TWIN_LAMPS) {
                            overview += tab + "Using Average<br>";
                        }
                    } else if (s.sts.statisticGroup == 2) {
                        if (s.sts.useSmoothing) {
                            overview += tab + "Smooth Sum<br>";
                        }

                        if (s.sts.useAverage) {
                            overview += tab + "Using Average<br>";
                        }
                    }

                    if(s.sts.statisticGroup != 4) {
                        overview += tab + "Intensity = " + s.sts.statistic_intensity + "<br><br>";
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
                overview += tab + "Color Intensity = " + s.ps2.color_intensity + "<br><br>";
            }

            if ((s.ds.domain_coloring && s.ds.domain_coloring_mode == 1)) {
                if (s.fns.smoothing) {
                    overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                    overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";
                }
            } else if (s.fns.smoothing) {
                overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
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
                        case ColorSpaceConverter.LCH:
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
                        case ColorSpaceConverter.LCH:
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

            if (s.exterior_de && !s.isPertubationTheoryInUse()) {
                overview += "<b><font color='red'>Distance Estimation:</font></b><br>";

                if (s.inverse_dem) {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                } else {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br><br>";
                }
            }

            if (!periodicity_checking && s.ots.useTraps) {
                overview += "<b><font color='red'>Orbit Traps:</font></b><br>";
                overview += tab + "Shape = " + Constants.orbitTrapsNames[s.ots.trapType] + "<br>";
                overview += tab + "Center = " + Complex.toString2(s.ots.trapPoint[0], s.ots.trapPoint[1]) + "<br>";

                int type = s.ots.trapType == Constants.IMAGE_TRAP && s.ots.checkType == Constants.TRAP_CHECK_TYPE_TRAPPED_MIN_DISTANCE ? Constants.TRAP_CHECK_TYPE_TRAPPED_FIRST : s.ots.checkType;
                overview += tab + "Check type = " + Constants.orbitTrapCheckTypes[type] + "<br>";

                if(s.ots.skipTrapCheckForIterations > 0) {
                    overview += tab + "Skip Trap Check for the first iterations = " + s.ots.skipTrapCheckForIterations + "<br>";
                }

                if (s.ots.trapType != Constants.SUPER_FORMULA_ORBIT_TRAP && s.ots.trapType != Constants.ATOM_DOMAIN_TRAP && s.ots.trapType != Constants.SQUARE_ATOM_DOMAIN_TRAP && s.ots.trapType != Constants.RHOMBUS_ATOM_DOMAIN_TRAP && s.ots.trapType != Constants.NNORM_ATOM_DOMAIN_TRAP && s.ots.trapType != Constants.GOLDEN_RATIO_SPIRAL_TRAP && s.ots.trapType != Constants.STALKS_TRAP) {
                    overview += tab + "Length = " + s.ots.trapLength + "<br>";
                }

                if (!(s.ots.trapType == Constants.ATOM_DOMAIN_TRAP || s.ots.trapType == Constants.SQUARE_ATOM_DOMAIN_TRAP || s.ots.trapType == Constants.RHOMBUS_ATOM_DOMAIN_TRAP || s.ots.trapType == Constants.NNORM_ATOM_DOMAIN_TRAP || s.ots.trapType == Constants.POINT_RHOMBUS_TRAP || s.ots.trapType == Constants.POINT_SQUARE_TRAP || s.ots.trapType == Constants.POINT_TRAP || s.ots.trapType == Constants.POINT_N_NORM_TRAP)) {
                    overview += tab + "Width = " + s.ots.trapWidth + "<br>";
                }

                if (s.ots.trapType == Constants.POINT_N_NORM_TRAP || s.ots.trapType == Constants.N_NORM_TRAP || s.ots.trapType == Constants.N_NORM_CROSS_TRAP || s.ots.trapType == Constants.N_NORM_POINT_TRAP || s.ots.trapType == Constants.N_NORM_POINT_N_NORM_TRAP || s.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_POINT_N_NORM_TRAP || s.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_N_NORM_TRAP || s.ots.trapType == Constants.STALKS_POINT_N_NORM_TRAP || s.ots.trapType == Constants.STALKS_N_NORM_TRAP || s.ots.trapType == Constants.NNORM_ATOM_DOMAIN_TRAP) {
                    overview += tab + "Norm = " + s.ots.trapNorm + "<br>";
                }

                if (s.ots.trapType == Constants.CROSS_TRAP || s.ots.trapType == Constants.RE_TRAP || s.ots.trapType == Constants.IM_TRAP || s.ots.trapType == Constants.CIRCLE_CROSS_TRAP || s.ots.trapType == Constants.SQUARE_CROSS_TRAP || s.ots.trapType == Constants.RHOMBUS_CROSS_TRAP || s.ots.trapType == Constants.N_NORM_CROSS_TRAP || s.ots.trapType == Constants.GOLDEN_RATIO_SPIRAL_CROSS_TRAP || s.ots.trapType == Constants.STALKS_CROSS_TRAP) {
                    overview += tab + "Line Function = " + Constants.orbitTrapLineTypes[s.ots.lineType] + "<br>";
                }

                if(s.ots.trapType == Constants.SUPER_FORMULA_ORBIT_TRAP) {
                    overview += tab + "m1 = " + s.ots.sfm1 + "<br>";
                    overview += tab + "m2 = " + s.ots.sfm2 + "<br>";
                    overview += tab + "n1 = " + s.ots.sfn1 + "<br>";
                    overview += tab + "n2 = " + s.ots.sfn2 + "<br>";
                    overview += tab + "n3 = " + s.ots.sfn3 + "<br>";
                    overview += tab + "a = " + s.ots.sfa + "<br>";
                    overview += tab + "b = " + s.ots.sfa + "<br>";
                }

                if (s.ots.trapType != Constants.IMAGE_TRAP) {
                    overview += tab + "Trap Color Method = " + Constants.colorMethod[s.ots.trapColorMethod] + "<br>";

                    if (s.ots.trapColorMethod == 3) {
                        overview += tab2 + "Trap Blending = " + s.ots.trapBlending + "<br>";
                    }

                    if (s.ots.trapMaxDistance != 0) {
                        overview += tab + "Max Distance = " + s.ots.trapMaxDistance + "<br>";
                    }
                    overview += tab + "Interpolation percent = " + s.ots.trapColorInterpolation + "<br>";

                    if (s.ots.trapCellularStructure) {
                        overview += tab + "Uses cellular structure<br>";
                        if (s.ots.trapCellularInverseColor) {
                            overview += tab2 + "Uses inverted coloring<br>";
                        }
                        overview += tab2 + "Cellular Size = " + s.ots.trapCellularSize + "<br>";
                    }
                }

                if (s.ots.trapIncludeEscaped) {
                    overview += tab + "Includes escaped points<br>";
                }
                if (s.ots.trapIncludeNotEscaped) {
                    overview += tab + "Includes not escaped points<br>";
                }
                if (s.ots.countTrapIterations) {
                    overview += tab + "Counts trap iterations<br>";
                }
                if (s.ots.showOnlyTraps) {
                    overview += tab + "Shows only traps<br>";
                }

                if (s.ots.trapType != Constants.IMAGE_TRAP) {
                    overview += tab + "Intensity = " + s.ots.trapIntensity + "<br>";
                    overview += tab + "Height Function = " + Constants.trapHeightAlgorithms[s.ots.trapHeightFunction] + "<br>";
                    
                    if(s.ots.invertTrapHeight) {
                        overview += tab + "Inverts trap height<br>";
                    }
                }

                overview += "<br>";
            }

            if (s.hss.histogramColoring && ! (s.sts.statistic && s.sts.statisticGroup == 4)) {
                overview += "<b><font color='red'>Histogram Coloring:</font></b><br>";

                overview += tab + "Mapping = " + histogramMapping[s.hss.hmapping] + "<br>";
                if(s.hss.hmapping == 0) {
                    overview += tab2 + "Bin Granularity = " + s.hss.histogramBinGranularity + "<br>";
                    overview += tab2 + "Density = " + s.hss.histogramDensity + "<br>";
                }
                overview += tab2 + "Min Scaling = " + s.hss.histogramScaleMin + "<br>";
                overview += tab2 + "Max Scaling = " + s.hss.histogramScaleMax + "<br>";
                overview += tab + "Color Blending = " + s.hss.hs_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + s.hss.hs_noise_reducing_factor + "<br><br>";
            }
        }

        if (!s.useDirectColor) {
            for (int i = 0; i < s.post_processing_order.length; i++) {
                switch (s.post_processing_order[i]) {
                    case LIGHT:
                        if (s.ls.lighting) {
                            overview += "<b><font color='red'>Light:</font></b><br>";
                            overview += tab + "Light Mode = " + Constants.lightModes[s.ls.lightMode] + "<br>";
                            overview += tab + "Direction = " + s.ls.light_direction + " degrees<br>";
                            overview += tab + "Magnitude = " + s.ls.light_magnitude + "<br>";
                            overview += tab + "Light Intensity = " + s.ls.lightintensity + "<br>";
                            overview += tab + "Ambient Light = " + s.ls.ambientlight + "<br>";
                            overview += tab + "Specular Intensity = " + s.ls.specularintensity + "<br>";
                            overview += tab + "Shininess = " + s.ls.shininess + "<br>";
                            overview += tab + "Height Transfer = " + Constants.lightTransfer[s.ls.heightTransfer] + "<br>";
                            overview += tab + "Height Transfer Factor = " + s.ls.heightTransferFactor + "<br>";
                            overview += tab + "Color Mode = " + Constants.colorMethod[s.ls.colorMode] + "<br>";
                            if (s.ls.colorMode == 3) {
                                overview += tab2 + "Color Blending = " + s.ls.light_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.ls.l_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case FAKE_DISTANCE_ESTIMATION:
                        if (!s.ds.domain_coloring && s.fdes.fake_de) {
                            overview += "<b><font color='red'>Fake Distance Estimation:</font></b><br>";

                            if (s.fdes.inverse_fake_dem) {
                                overview += tab + "Factor = " + s.fdes.fake_de_factor + "<br>";
                                overview += tab + "Inverted Coloring<br><br>";
                            } else {
                                overview += tab + "Factor = " + s.fdes.fake_de_factor + "<br><br>";
                            }
                        }
                        break;
                    case ENTROPY_COLORING:
                        if (!s.ds.domain_coloring && s.ens.entropy_coloring) {
                            overview += "<b><font color='red'>Entropy Coloring:</font></b><br>";
                            overview += tab + "Factor = " + s.ens.entropy_palette_factor + "<br>";
                            overview += tab + "Color Transfer Method = " + entropyMethod[s.ens.entropy_algorithm] + "<br>";

                            if (s.ens.entropy_algorithm == 0) {
                                overview += tab + "Offset = " + s.ens.entropy_offset + "<br>";
                            }

                            overview += tab + "Color Blending = " + s.ens.en_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.ens.en_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case OFFSET_COLORING:
                        if (!s.ds.domain_coloring && s.ofs.offset_coloring) {
                            overview += "<b><font color='red'>Offset Coloring:</font></b><br>";
                            overview += tab + "Offset = " + s.ofs.post_process_offset + "<br>";
                            overview += tab + "Color Blending = " + s.ofs.of_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.ofs.of_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case RAINBOW_PALETTE:
                        if (!s.ds.domain_coloring && s.rps.rainbow_palette) {
                            overview += "<b><font color='red'>Rainbow Palette:</font></b><br>";
                            overview += tab + "Factor = " + s.rps.rainbow_palette_factor + "<br>";
                            overview += tab + "Color Transfer Method = " + rainbowMethod[s.rps.rainbow_algorithm] + "<br>";

                            if (s.rps.rainbow_algorithm == 0) {
                                overview += tab + "Offset = " + s.rps.rainbow_offset + "<br>";
                            }

                            overview += tab + "Color Blending = " + s.rps.rp_blending + "<br>";
                            overview += tab + "Noise Reduction Factor = " + s.rps.rp_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case GREYSCALE_COLORING:
                        if (!s.ds.domain_coloring && s.gss.greyscale_coloring) {
                            overview += "<b><font color='red'>Greyscale Coloring:</font></b><br>";
                            overview += tab + "Noise Reduction Factor = " + s.gss.gs_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case CONTOUR_COLORING:
                        if (!s.ds.domain_coloring && s.cns.contour_coloring) {
                            overview += "<b><font color='red'>Contour Coloring:</font></b><br>";
                            overview += tab + "Algorithm = " + Constants.contourColorAlgorithmNames[s.cns.contour_algorithm] + "<br>";
                            overview += tab + "Color Method = " + Constants.colorMethod[s.cns.contourColorMethod] + "<br>";
                            if (s.cns.contourColorMethod == 3) {
                                overview += tab2 + "Color Blending = " + s.cns.cn_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.cns.cn_noise_reducing_factor + "<br><br>";
                        }
                        break;
                    case BUMP_MAPPING:
                        if (s.bms.bump_map) {
                            overview += "<b><font color='red'>Bump Mapping:</font></b><br>";
                            overview += tab + "Light Direction = " + s.bms.lightDirectionDegrees + " degrees<br>";
                            overview += tab + "Depth = " + s.bms.bumpMappingDepth + "<br>";
                            overview += tab + "Strength = " + s.bms.bumpMappingStrength + "<br>";
                            overview += tab + "Transfer Function = " + Constants.bumpTransferNames[s.bms.bump_transfer_function] + "<br>";
                            overview += tab + "Transfer Factor = " + s.bms.bump_transfer_factor + "<br>";
                            overview += tab + "Processing Method = " + Constants.bumpProcessingMethod[s.bms.bumpProcessing] + "<br>";

                            if (s.bms.bumpProcessing == 1 || s.bms.bumpProcessing == 2) {
                                overview += tab + "Color Blending = " + s.bms.bump_blending + "<br>";
                            }
                            overview += tab + "Noise Reduction Factor = " + s.bms.bm_noise_reducing_factor + "<br><br>";
                        }
                        break;
                }
            }
        }

        if (!s.useDirectColor) {
            overview += "<b><font color='red'>Color Blending:</font></b> " + ColorBlendingMenu.colorBlendingNames[s.color_blending] + "<br>";
            overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";


            overview += "<b><font color='red'>Contour Factor:</font></b> " + s.contourFactor + "<br><br>";
        }

        if (s.ds.domain_coloring && !s.useDirectColor) {
            if (!s.ds.customDomainColoring) {
                overview += "<b><font color='red'>Domain Coloring:</font></b><br>";
                overview += tab + "Algorithm = " + Constants.domainAlgNames[s.ds.domain_coloring_alg] + "<br>";
                overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br>";
                overview += tab + "Processing Transfer Function = " + Constants.domainProcessingTransferNames[s.ds.domainProcessingTransfer] + "<br>";
                overview += tab + "Processing Factor = " + s.ds.domainProcessingHeightFactor + "<br><br>";
            } else {
                overview += "<b><font color='red'>Custom Domain Coloring:</font></b><br>";

                if (s.ds.drawColor) {
                    overview += tab + "Color = " + Constants.domainColors[s.ds.colorType] + "<br>";

                    if (s.ds.colorType != 0) {
                        overview += tab2 + "Max Norm/Re/Im Value = " + s.ds.max_norm_re_im_value + "<br>";
                    }
                }

                if (s.ds.drawContour) {
                    overview += tab + "Contour = " + Constants.domainContours[s.ds.contourType] + "<br>";
                    overview += tab2 + "Color Method = " + Constants.colorMethod[s.ds.contourMethod] + "<br>";
                    if (s.ds.contourMethod == 3) {
                        overview += tab2 + "Color Blending = " + s.ds.contourBlending + "<br>";
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
                            }
                            break;
                        case MainWindow.CIRCLES:
                            if (s.ds.drawCircles) {
                                overview += tab + "Circles<br>";
                                overview += tab2 + "Width = " + s.ds.circleWidth + "<br>";
                                overview += tab2 + "Strength = " + s.ds.circlesBlending + "<br>";
                                overview += tab2 + "Fading = " + Constants.circleAndGridFadeNames[s.ds.circleFadeFunction] + "<br>";
                            }
                            break;
                        case MainWindow.ISO_LINES:
                            if (s.ds.drawIsoLines) {
                                overview += tab + "Iso-Argument Lines<br>";
                                overview += tab2 + "Width = " + s.ds.iso_factor + "<br>";
                                overview += tab2 + "Strength = " + s.ds.isoLinesBlendingFactor + "<br>";
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
                overview += tab + "Processing Factor = " + s.ds.domainProcessingHeightFactor + "<br><br>";
            }

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
                JLabel palette_label = new JLabel();
                palette_label.setIcon(new ImageIcon(getOutColoringPalettePreview(s, s.ps.color_cycling_location, 800, 36)));
                palette_label.setToolTipText("Displays the active out-coloring palette.");

                JLabel palette_text_label = new JLabel("Palette(Out): (Length = " + paletteOutLength + ")");

                JLabel palette_in_label = new JLabel();
                palette_in_label.setIcon(new ImageIcon(getInColoringPalettePreview(s, s.ps2.color_cycling_location, 800, 36)));
                palette_in_label.setToolTipText("Displays the active in-coloring palette.");

                JLabel palette_in_text_label = new JLabel("Palette(In): (Length = " + paletteInLength + ")");

                JLabel gradient_label = new JLabel();
                gradient_label.setIcon(new ImageIcon(getGradientPreview(s.gs, s.gs.gradient_offset, 800, 36)));
                gradient_label.setToolTipText("Displays the active gradient.");

                JLabel gradient_text_label = new JLabel("Gradient: (Length = " + gradientLength + ")");

                Object[] message = {
                    scroll_pane_2,
                    " ",
                    s.sts.statistic && s.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteOutColoring ? null : palette_text_label,
                        s.sts.statistic && s.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteOutColoring ? null : palette_label,
                        s.sts.statistic && s.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteInColoring ? null : palette_in_text_label,
                        s.sts.statistic && s.sts.statisticGroup == 4 || s.gps.useGeneratedPaletteInColoring ? null : palette_in_label,
                    gradient_text_label,
                    gradient_label};

                textArea.setCaretPosition(0);

                JOptionPane.showMessageDialog(parent, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JLabel palette_label = new JLabel();
                palette_label.setIcon(new ImageIcon(getOutColoringPalettePreview(s, s.ps.color_cycling_location, 800, 36)));
                palette_label.setToolTipText("Displays the active out-coloring palette.");

                JLabel palette_text_label = new JLabel("Palette(Out): (Length = " + paletteOutLength + ")");

                JLabel gradient_label = new JLabel();
                gradient_label.setIcon(new ImageIcon(getGradientPreview(s.gs, s.gs.gradient_offset, 800, 36)));
                gradient_label.setToolTipText("Displays the active gradient.");

                JLabel gradient_text_label = new JLabel("Gradient: (Length = " + gradientLength + ")");


                JButton saveOverview = new JButton("Save Overview");

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
                        FileNameExtensionFilter filter = (FileNameExtensionFilter) evt.getNewValue();

                        String extension = filter.getExtensions()[0];

                        String file_name = ((BasicFileChooserUI) file_chooser.getUI()).getFileName();

                        int index = file_name.lastIndexOf(".");

                        if (index != -1) {
                            file_name = file_name.substring(0, index);
                        }

                        file_chooser.setSelectedFile(new File(file_name + "." + extension));
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
                        s.sts.statistic && s.sts.statisticGroup == 4 || (s.gps.useGeneratedPaletteOutColoring && (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1))) ? null : palette_text_label,
                        s.sts.statistic && s.sts.statisticGroup == 4 || (s.gps.useGeneratedPaletteOutColoring && (!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.domain_coloring_mode == 1))) ? null : palette_label,
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
                c[i] = new Color(DomainColoring.LCHcolor(h, color_cycling_location));
            }
            isSmooth = true;
        } else if (s.ds.domain_coloring && s.ds.domain_coloring_mode == 3) {
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
                + "bypass an implementation design<br><br>"
                + "Coloring algorithms that apply an offset to the palette, based<br>"
                + "on a specific condition, (Binary Decomposition Variant, Biomorph, Escape Time + Grid,<br>"
                + "Escape Time + Field Lines Variant) will return a value offseted by 50<br>"
                + "The return value of these algorithms that contains the value 50 will be<br>"
                + "negative in order for the software to determine this special offset<br>"
                + "For instance Binary Decomposition will produce the following:<br>"
                + "<b>(if im(z) &lt 0) then return -(n + 50) else return n</b><br><br>"
                + "This constant offset value of 50, is creating noisy images when<br>"
                + "applying processing algorithms like Bump-Mapping or Entropy Coloring<br>"
                + "The same issue arises also in 3d mode, so in order to alleviate the problem,<br>"
                + "this constant needs to be subtracted before applying any processing<br><br>"
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
                + "pi, e, phi, c10, alpha, delta<br>"
                + "<b>Complex Numbers:</b><br>"
                + "a + bi<br>"
                + "<b>Trigonometric Functions: f(z)</b><br>"
                + "sin, cos, tan, cot, sec, csc, sinh, cosh, tanh, coth, sech, csch, asin, acos, atan, acot, asec,<br>"
                + "acsc, asinh, acosh, atanh, acoth, asech, acsch, vsin, vcos, cvsin, cvcos, hvsin, hvcos, hcvsin,<br>"
                + "hcvcos, exsec, excsc, avsin, avcos, acvsin, acvcos, ahvsin, ahvcos, ahcvsin, ahcvcos, aexsec, aexcsc<br>"
                + "<b>Other Functions: f(z)</b><br>"
                + "exp, log, log10, log2, sqrt, abs, absre, absim, conj, re, im, norm, arg, gamma, fact, erf, rzeta, gi, rec, flip, round,<br>"
                + "ceil, floor, trunc, deta, snorm, fib, f1, ... f60<br>"
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
}
