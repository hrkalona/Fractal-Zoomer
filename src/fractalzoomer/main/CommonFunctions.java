/*
 * Fractal Zoomer, Copyright (C) 2018 hrkalona2
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

import fractalzoomer.main.app_settings.Settings;
import fractalzoomer.app_updater.AppUpdater;
import fractalzoomer.core.Complex;
import fractalzoomer.core.ThreadDraw;
import fractalzoomer.gui.BailoutConditionsMenu;
import fractalzoomer.gui.ColorBlendingMenu;
import fractalzoomer.gui.ColorTransferMenu;
import fractalzoomer.gui.CustomPaletteEditorFrame;
import fractalzoomer.gui.FiltersMenu;
import fractalzoomer.gui.FractalFunctionsMenu;
import fractalzoomer.gui.InColoringModesMenu;
import fractalzoomer.gui.LinkLabel;
import fractalzoomer.gui.OutColoringModesMenu;
import fractalzoomer.gui.PaletteMenu;
import fractalzoomer.gui.PlanesMenu;
import fractalzoomer.palettes.CustomPalette;
import fractalzoomer.parser.Parser;
import fractalzoomer.parser.ParserException;
import fractalzoomer.utils.MathUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 *
 * @author hrkalona2
 */
public class CommonFunctions implements Constants {

    private Component parent;
    private boolean runsOnWindows;

    public CommonFunctions(Component parent, boolean runsOnWindows) {

        this.parent = parent;
        this.runsOnWindows = runsOnWindows;

    }

    public void checkForUpdate(boolean mode) {

        String[] res = AppUpdater.checkVersion(VERSION);

        if(res[1].equals("error")) {
            if(mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(res[1].equals("up to date")) {

            if(mode) {
                Object[] message = {
                    res[0],};

                JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.PLAIN_MESSAGE, getIcon("/fractalzoomer/icons/checkmark.png"));
            }
        }
        else {
            Object[] message = {
                new LinkLabel(res[0], res[1])};

            JOptionPane.showMessageDialog(parent, message, "Software Update", JOptionPane.PLAIN_MESSAGE, getIcon("/fractalzoomer/icons/update_larger.png"));
        }

    }

    public boolean copyLib() {

        if(!runsOnWindows) {
            return true;
        }

        String path = System.getProperty("java.home");
        String file_separator = System.getProperty("file.separator");

        if(!path.isEmpty() && !file_separator.isEmpty()) {
            path += file_separator + "lib" + file_separator;

            if(!path.contains(file_separator + "jdk")) {
                File lib_file = new File(path + "tools.jar");

                if(!lib_file.exists()) {
                    try {
                        InputStream src = getClass().getResource("/fractalzoomer/lib/tools.jar").openStream();

                        FileOutputStream out = new FileOutputStream(lib_file);
                        byte[] temp = new byte[32768];
                        int rc;

                        while((rc = src.read(temp)) > 0) {
                            out.write(temp, 0, rc);
                        }

                        src.close();
                        out.close();
                    }
                    catch(Exception ex) {
                        JOptionPane.showMessageDialog(parent, "Unable to copy tools.jar to " + path + ".\nMake sure you have administrative rights.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        return false;
                    }
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(parent, "Unable to copy tools.jar to the JRE lib folder.\nThe JRE installation path was not found.", "Warning!", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public void exportCodeFiles(boolean libExists) {

        try {
            InputStream src = getClass().getResource("/fractalzoomer/parser/code/Complex.javacode").openStream();
            File complexFile = new File("Complex.java");

            FileOutputStream out = new FileOutputStream(complexFile);
            byte[] temp = new byte[32768];
            int rc;

            while((rc = src.read(temp)) > 0) {
                out.write(temp, 0, rc);
            }

            src.close();
            out.close();

            src = getClass().getResource("/fractalzoomer/parser/code/UserDefinedFunctions.javacode").openStream();
            File UserDefinedFunctionsFile = new File("UserDefinedFunctions.java");

            if(!UserDefinedFunctionsFile.exists()) {
                out = new FileOutputStream(UserDefinedFunctionsFile);
                temp = new byte[32768];

                while((rc = src.read(temp)) > 0) {
                    out.write(temp, 0, rc);
                }

                src.close();
                out.close();
            }

            if(libExists) {
                compileCode(false);
            }
        }
        catch(Exception ex) {
        }

    }

    public void compileCode(boolean show_success) {

        try {
            Parser.compileUserFunctions();
            if(show_success) {
                JOptionPane.showMessageDialog(parent, "Compilation was successful!", "Success!", JOptionPane.INFORMATION_MESSAGE, getIcon("/fractalzoomer/icons/compile_sucess.png"));
            }
        }
        catch(ParserException ex) {
            JOptionPane.showMessageDialog(parent, ex.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE, getIcon("/fractalzoomer/icons/compile_error.png"));
        }

    }

    public void overview(Settings s) {

        //JTextArea textArea = new JTextArea(32, 55); // 60
        JEditorPane textArea = new JEditorPane();

        textArea.setEditable(false);
        textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(800, 430));
        //textArea.setPreferredSize(new Dimension(200, 200));
        //textArea.setLineWrap(false);
        //textArea.setWrapStyleWord(false);

        JScrollPane scroll_pane_2 = new JScrollPane(textArea);
        scroll_pane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        Point2D.Double p = MathUtils.rotatePointRelativeToPoint(s.xCenter, s.yCenter, s.rotation_vals, s.rotation_center);

        String keyword_color = "#008080";
        String condition_color = "#800000";

        String tab = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";
        String tab2 = "<font size='5' face='arial'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#8226;&nbsp;</font>";

        String overview = "<html><center><b><u><font size='5' face='arial' color='blue'>Active Fractal Options</font></u></b></center><br><br><font  face='arial' size='3'>";

        overview += "<b><font color='red'>Center:</font></b> " + Complex.toString2(p.x, p.y) + "<br><br>";
        overview += "<b><font color='red'>Size:</font></b> " + s.size + "<br><br>";

        if(s.polar_projection) {
            overview += "<b><font color='red'>Polar Projection:</font></b> " + "<br>" + tab + "Circle Periods = " + s.circle_period + "<br><br>";
        }

        overview += "<b><font color='red'>Function:</font></b> " + FractalFunctionsMenu.functionNames[s.function] + "<br>";

        if(s.function == MANDELPOLY || s.function == NEWTONPOLY || s.function == HALLEYPOLY || s.function == SCHRODERPOLY || s.function == HOUSEHOLDERPOLY || s.function == SECANTPOLY || s.function == STEFFENSENPOLY || s.function == MULLERPOLY || s.function == PARHALLEYPOLY || s.function == LAGUERREPOLY) {
            overview += tab + s.poly + "<br>";
        }
        else if(s.function == NEWTON3 || s.function == HALLEY3 || s.function == HOUSEHOLDER3 || s.function == SCHRODER3 || s.function == SECANT3 || s.function == STEFFENSEN3 || s.function == MULLER3 || s.function == PARHALLEY3 || s.function == LAGUERRE3) {
            overview += tab + "p(z) = z^3 - 1" + "<br>";
        }
        else if(s.function == NEWTON4 || s.function == HALLEY4 || s.function == HOUSEHOLDER4 || s.function == SCHRODER4 || s.function == SECANT4 || s.function == STEFFENSEN4 || s.function == MULLER4 || s.function == PARHALLEY4 || s.function == LAGUERRE4) {
            overview += tab + "p(z) = z^4 - 1" + "<br>";
        }
        else if(s.function == NEWTONGENERALIZED3 || s.function == HALLEYGENERALIZED3 || s.function == HOUSEHOLDERGENERALIZED3 || s.function == SCHRODERGENERALIZED3 || s.function == SECANTGENERALIZED3 || s.function == STEFFENSENGENERALIZED3 || s.function == MULLERGENERALIZED3 || s.function == PARHALLEYGENERALIZED3 || s.function == LAGUERREGENERALIZED3) {
            overview += tab + "p(z) = z^3 - 2z + 2" + "<br>";
        }
        else if(s.function == NEWTONGENERALIZED8 || s.function == HALLEYGENERALIZED8 || s.function == HOUSEHOLDERGENERALIZED8 || s.function == SCHRODERGENERALIZED8 || s.function == SECANTGENERALIZED8 || s.function == MULLERGENERALIZED8 || s.function == PARHALLEYGENERALIZED8 || s.function == LAGUERREGENERALIZED8) {
            overview += tab + "p(z) = z^8 + 15z^4 - 16" + "<br>";
        }
        else if(s.function == NEWTONCOS || s.function == HALLEYCOS || s.function == HOUSEHOLDERCOS || s.function == SCHRODERCOS || s.function == SECANTCOS || s.function == MULLERCOS || s.function == PARHALLEYCOS || s.function == LAGUERRECOS) {
            overview += tab + "f(z) = cos(z)" + "<br>";
        }
        else if(s.function == NEWTONSIN || s.function == HALLEYSIN || s.function == HOUSEHOLDERSIN || s.function == SCHRODERSIN || s.function == MULLERSIN || s.function == PARHALLEYSIN || s.function == LAGUERRESIN) {
            overview += tab + "f(z) = sin(z)" + "<br>";
        }
        else if(s.function == MANDELBROTNTH) {
            overview += tab + "z = z^" + s.z_exponent + " + c<br>";
        }
        else if(s.function == MANDELBROTWTH) {
            overview += tab + "z = z^(" + Complex.toString2(s.z_exponent_complex[0], s.z_exponent_complex[1]) + ") + c<br>";
        }
        else if(s.function == LAMBDA) {
            overview += tab + "z = cz(1 - z)" + "<br>";
        }
        else if(s.function == MAGNET1) {
            overview += tab + "z = ((z^2 + c - 1)/(2z + c - 2))^2" + "<br>";
        }
        else if(s.function == MAGNET2) {
            overview += tab + "z = ((z^3 + 3(c - 1)z + (c - 1)(c - 2))/(3z^2 + 3(c - 2)z + (c - 1)(c - 2))))^2" + "<br>";
        }
        else if(s.function == FROTHY_BASIN) {
            overview += tab + "z = z^2 - (1 + 1.028713768218725i)conj(z) + c<br>";
        }
        else if(s.function == SPIDER) {
            overview += tab + "z = z^2 + c<br>";
            overview += tab + "c = c/2 + z<br>";
        }
        else if(s.function == PHOENIX) {
            overview += tab + "t = z^2 + (im(c)re(s) + re(c)) + (im(c)im(s))i<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        }
        else if(s.function == SIERPINSKI_GASKET) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[im(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) + (2im(z) - 1)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else if</font> <font color='" + condition_color + "'>[re(z) > 0.5]</font> <font color='" + keyword_color + "'>then</font> z = 2re(z) - 1 + 2im(z)i<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = 2z<br>";
        }
        else if(s.function == BARNSLEY1) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z - 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z + 1)c<br>";
        }
        else if(s.function == BARNSLEY2) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z)im(c) + re(c)im(z) >= 0]</font> <font color='" + keyword_color + "'>then</font> z = (z + 1)c<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = (z - 1)c<br>";
        }
        else if(s.function == BARNSLEY3) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) > 0]</font> <font color='" + keyword_color + "'>then</font> z = z^2 - 1<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> z = z^2 + re(c)re(z) + im(c)re(z) - 1<br>";
        }
        else if(s.function == SZEGEDI_BUTTERFLY1) {
            overview += tab + "z = (im(z)^2 - sqrt(abs(re(z)))) + (re(z)^2 - sqrt(abs(im(z))))i + c<br>";
        }
        else if(s.function == SZEGEDI_BUTTERFLY2) {
            overview += tab + "z = (re(z)^2 - sqrt(abs(im(z)))) + (im(z)^2 - sqrt(abs(re(z))))i + c<br>";
        }
        else if(s.function == MANOWAR) {
            overview += tab + "t = z^2 + s + c<br>";
            overview += tab + "s = z<br>";
            overview += tab + "z = t<br>";
        }
        else if(s.function == MANDELBAR) {
            overview += tab + "z = conj(z)^2 + c<br>";
        }
        else if(s.function == COUPLED_MANDELBROT) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = w^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = c<br>";
        }
        else if(s.function == COUPLED_MANDELBROT_BURNING_SHIP) {
            overview += tab + "a = z^2 + c<br>";
            overview += tab + "b = abs(w)^2 + c<br>";
            overview += tab + "z = 0.1(b - a) + a<br>";
            overview += tab + "w = 0.1(a - b) + b<br>";
            overview += tab + "w(0) = 0.0 + 0.0i<br>";
        }
        else if(s.function == NOVA) {
            switch (s.nova_method) {
                case NOVA_NEWTON:
                    overview += tab + "Newton Method<br>";
                    break;
                case NOVA_HALLEY:
                    overview += tab + "Halley Method<br>";
                    break;
                case NOVA_SCHRODER:
                    overview += tab + "Schroder Method<br>";
                    break;
                case NOVA_HOUSEHOLDER:
                    overview += tab + "Householder Method<br>";
                    break;
                case NOVA_SECANT:
                    overview += tab + "Secant Method<br>";
                    break;
                case NOVA_STEFFENSEN:
                    overview += tab + "Steffensen Method<br>";
                    break;
                case NOVA_MULLER:
                    overview += tab + "Muller Method<br>";
                    break;
                case NOVA_PARHALLEY:
                    overview += tab + "Parhalley Method<br>";
                    break;
                case NOVA_LAGUERRE:
                    overview += tab + "Laguerre Method<br>";
                    break;
            }

            overview += tab + "p(z) = z^(" + Complex.toString2(s.z_exponent_nova[0], s.z_exponent_nova[1]) + ") - 1<br>";
            overview += tab + "Relaxation = " + Complex.toString2(s.relaxation[0], s.relaxation[1]) + "<br>";
        }
        else if(s.function == NEWTONFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
        }
        else if(s.function == HALLEYFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + s.user_ddfz_formula + "<br>";
        }
        else if(s.function == SCHRODERFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + s.user_ddfz_formula + "<br>";
        }
        else if(s.function == HOUSEHOLDERFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + s.user_ddfz_formula + "<br>";
        }
        else if(s.function == SECANTFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
        }
        else if(s.function == STEFFENSENFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
        }
        else if(s.function == MULLERFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
        }
        else if(s.function == PARHALLEYFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + s.user_ddfz_formula + "<br>";
        }
        else if(s.function == LAGUERREFORMULA) {
            overview += tab + "f(z) = " + s.user_fz_formula + "<br>";
            overview += tab + "f '(z) = " + s.user_dfz_formula + "<br>";
            overview += tab + "f ''(z) = " + s.user_ddfz_formula + "<br>";
            overview += tab + "Degree = " + Complex.toString2(s.laguerre_deg[0], s.laguerre_deg[1]) + "<br>";
        }
        else if(s.function == USER_FORMULA) {
            overview += tab + "z = " + s.user_formula + "<br>";
            overview += tab + "c = " + s.user_formula2 + "<br>";
            if(!s.ds.domain_coloring) {
                if(s.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(s.function == USER_FORMULA_ITERATION_BASED) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 0]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_iteration_based[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 1]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_iteration_based[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 2]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_iteration_based[2] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[iteration % 4 = 3]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_iteration_based[3] + "<br>";
            if(!s.ds.domain_coloring) {
                if(s.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(s.function == USER_FORMULA_CONDITIONAL) {
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_formula_conditions[0] + " > " + s.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_condition_formula[0] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_formula_conditions[0] + " &#60; " + s.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_condition_formula[1] + "<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_formula_conditions[0] + " = " + s.user_formula_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_formula_condition_formula[2] + "<br>";
            if(!s.ds.domain_coloring) {
                if(s.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }
        else if(s.function == USER_FORMULA_COUPLED) {
            overview += tab + "a = " + s.user_formula_coupled[0] + "<br>";
            overview += tab + "b = " + s.user_formula_coupled[1] + "<br>";

            overview += tab + "Coupling = " + s.coupling + "<br>";

            if(s.coupling_method == 0) {
                overview += tab + "Simple Coupling<br>";
                overview += tab2 + "Final_Coupling = Coupling<br>";
            }
            else if(s.coupling_method == 1) {
                overview += tab + "Cosine Coupling<br>";
                overview += tab2 + "Amplitude = " + s.coupling_amplitude + "<br>";
                overview += tab2 + "Frequency = " + s.coupling_frequency + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * cos(Current_Iteration * Frequency))<br>";
            }
            else {
                overview += tab + "Random Coupling<br>";
                overview += tab2 + "Amplitude = " + s.coupling_amplitude + "<br>";
                overview += tab2 + "Seed = " + s.coupling_seed + "<br>";
                overview += tab2 + "Final_Coupling = Coupling * (1 + Amplitude * (Random_Sequence_Number - 0.5) * 2)<br>";
            }

            overview += tab + "z = Final_Coupling * (b - a) + a<br>";
            overview += tab + "z2 = Final_Coupling * (a - b) + b<br>";

            overview += tab + "z2(0) = " + s.user_formula_coupled[2] + "<br>";

            if(!s.ds.domain_coloring) {
                if(s.bail_technique == 0) {
                    overview += tab + "Escaping Algorithm<br>";
                }
                else {
                    overview += tab + "Converging Algorithm<br>";
                }
            }
        }

        if((s.function <= 9 || s.function == MANDELPOLY || s.function == MANDELBROTWTH) && s.burning_ship) {
            overview += tab + "Burning Ship<br>";
            overview += tab2 + "z = abs(z), applied before the function evaluation.<br>";
        }
        if((s.function <= 9 || s.function == MANDELPOLY || s.function == MANDELBROTWTH) && s.mandel_grass) {
            overview += tab + "Mandel Grass = " + Complex.toString2(s.mandel_grass_vals[0], s.mandel_grass_vals[1]) + "<br>";
            overview += tab2 + "z = z + (MG * z)/(norm(z)), applied after the function evaluation.<br>";
        }
        overview += "<br>";

        if(s.julia) {
            overview += "<b><font color='red'>Julia Seed:</font></b> " + Complex.toString2(s.xJuliaCenter, s.yJuliaCenter) + " is replacing the c constant in the formula.<br><br>";
        }

        overview += "<b><font color='red'>Plane Transformation:</font></b> Applies the transformation \"" + PlanesMenu.planeNames[s.plane_type] + "\" to every plane point c.<br>";

        if(s.plane_type == CIRCLEINVERSION_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br>";
            overview += tab + "Radius = " + s.plane_transform_radius + "<br>";
        }
        else if(s.plane_type == INFLECTION_PLANE || s.plane_type == FOLDUP_PLANE || s.plane_type == FOLDDOWN_PLANE || s.plane_type == FOLDRIGHT_PLANE || s.plane_type == FOLDLEFT_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br>";
        }
        else if(s.plane_type == FOLDIN_PLANE || s.plane_type == FOLDOUT_PLANE) {
            overview += tab + "Radius = " + s.plane_transform_radius + "<br>";
        }
        else if(s.plane_type == PINCH_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + s.plane_transform_radius + "<br>";
            overview += tab + "Amount = " + s.plane_transform_amount + "<br>";
        }
        else if(s.plane_type == SHEAR_PLANE) {
            overview += tab + "Scale Real = " + s.plane_transform_scales[0] + "<br>";
            overview += tab + "Scale Imaginary = " + s.plane_transform_scales[1] + "<br>";
        }
        else if(s.plane_type == RIPPLES_PLANE) {
            overview += tab + "Amplitude Real = " + s.plane_transform_scales[0] + "<br>";
            overview += tab + "Amplitude Imaginary = " + s.plane_transform_scales[1] + "<br>";
            overview += tab + "Wavelength Real = " + s.plane_transform_wavelength[0] + "<br>";
            overview += tab + "Wavelength Imaginary = " + s.plane_transform_wavelength[1] + "<br>";
            overview += tab + "Wave Type = " + waveTypes[s.waveType] + "<br>";
        }
        else if(s.plane_type == TWIRL_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.plane_transform_angle + " degrees<br>";
            overview += tab + "Radius = " + s.plane_transform_radius + "<br>";
        }
        else if(s.plane_type == KALEIDOSCOPE_PLANE) {
            overview += tab + "Center = " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br>";
            overview += tab + "Angle = " + s.plane_transform_angle + " degrees<br>";
            overview += tab + "Angle 2 = " + s.plane_transform_angle2 + " degrees<br>";
            overview += tab + "Radius = " + s.plane_transform_radius + "<br>";
            overview += tab + "Sides = " + s.plane_transform_sides + "<br>";
        }
        else if(s.plane_type == BIPOLAR_PLANE || s.plane_type == INVERSED_BIPOLAR_PLANE) {
            overview += tab + "Foci = (" + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + ") and (" + Complex.toString2(-s.plane_transform_center[0], -s.plane_transform_center[1]) + ")<br>";
        }
        else if(s.plane_type == USER_PLANE) {
            if(s.user_plane_algorithm == 0) {
                overview += tab + "z = " + s.user_plane + "<br>";
            }
            else {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_plane_conditions[0] + " > " + s.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_plane_condition_formula[0] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_plane_conditions[0] + " &#60; " + s.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_plane_condition_formula[1] + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_plane_conditions[0] + " = " + s.user_plane_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z = " + s.user_plane_condition_formula[2] + "<br>";
            }
        }

        overview += "<br>";

        if(!s.perturbation && !s.init_val && s.functionSupportsC()) {
            if(s.apply_plane_on_julia && s.apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed and the Whole Plane (c).<br><br>";
            }
            else if(s.apply_plane_on_julia) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to Whole Plane (c).<br><br>";
            }
            else if(s.apply_plane_on_julia_seed) {
                overview += "<b><font color='red'>Julia Set Plane Transformation:</font></b> Applicable to the Julia Seed.<br><br>";
            }
        }

        if(!s.init_val) {
            String res = ThreadDraw.getDefaultInitialValue();

            if(!res.equals("")) {
                overview += "<b><font color='red'>Initial Value:</font></b> Default Value<br>";
                overview += tab + "z(0) = " + res + "<br>";
                overview += "<br>";
            }
        }
        else {
            if(s.variable_init_value) {
                if(s.user_initial_value_algorithm == 0) {
                    overview += "<b><font color='red'>Initial Value:</font></b> Variable Value<br>";
                    overview += tab + "z(0) = " + s.initial_value_user_formula + "<br>";
                }
                else {
                    overview += "<b><font color='red'>Initial Value:</font></b> Variable Value Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_initial_value_conditions[0] + " > " + s.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.user_initial_value_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_initial_value_conditions[0] + " &#60; " + s.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.user_initial_value_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_initial_value_conditions[0] + " = " + s.user_initial_value_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = " + s.user_initial_value_condition_formula[2] + "<br>";
                }
            }
            else {
                overview += "<b><font color='red'>Initial Value:</font></b> Static Value<br>";
                overview += tab + "z(0) = " + Complex.toString2(s.initial_vals[0], s.initial_vals[1]) + "<br>";
            }
            overview += "<br>";
        }

        if(s.perturbation) {
            if(s.variable_perturbation) {
                if(s.user_perturbation_algorithm == 0) {
                    overview += "<b><font color='red'>Perturbation:</font></b> Variable Value<br>";
                    overview += tab + "z(0) = z(0) + " + s.perturbation_user_formula + "<br>";
                }
                else {
                    overview += "<b><font color='red'>Perturbation:</font></b> Variable Value Conditional<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_perturbation_conditions[0] + " > " + s.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.user_perturbation_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_perturbation_conditions[0] + " &#60; " + s.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.user_perturbation_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_perturbation_conditions[0] + " = " + s.user_perturbation_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> z(0) = z(0) + " + s.user_perturbation_condition_formula[2] + "<br>";
                }
            }
            else {
                overview += "<b><font color='red'>Perturbation:</font></b> Static Value<br>";
                overview += tab + "z(0) = z(0) + " + Complex.toString2(s.perturbation_vals[0], s.perturbation_vals[1]) + "<br>";
            }
            overview += "<br>";
        }

        overview += "<b><font color='red'>User Point:</font></b> " + Complex.toString2(s.plane_transform_center[0], s.plane_transform_center[1]) + "<br><br>";

        overview += "<b><font color='red'>Maximum Iterations:</font></b> " + s.max_iterations + "<br><br>";

        if(!s.ds.domain_coloring && !s.isConvergingType()) {

            overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when the criterion defined by the bailout condition \"" + BailoutConditionsMenu.bailoutConditionNames[s.bailout_test_algorithm] + "\" is met.<br>";
            if(s.bailout_test_algorithm == BAILOUT_CONDITION_NNORM) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[(abs(re(z))^" + s.n_norm + " + abs(im(z))^" + s.n_norm + ")^(1/" + s.n_norm + ") >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_USER) {
                String greater = "", equal = "", lower = "";

                if(s.bailout_test_comparison == GREATER) { // >
                    greater = "Escaped";
                    equal = "Not Escaped";
                    lower = "Not Escaped";
                }
                else if(s.bailout_test_comparison == GREATER_EQUAL) { // >=
                    greater = "Escaped";
                    equal = "Escaped";
                    lower = "Not Escaped";
                }
                else if(s.bailout_test_comparison == LOWER) { // <
                    greater = "Not Escaped";
                    equal = "Not Escaped";
                    lower = "Escaped";
                }
                else if(s.bailout_test_comparison == LOWER_EQUAL) { // <=
                    greater = "Not Escaped";
                    equal = "Escaped";
                    lower = "Escaped";
                }
                else if(s.bailout_test_comparison == EQUAL) { // ==
                    greater = "Not Escaped";
                    equal = "Escaped";
                    lower = "Not Escaped";
                }
                else if(s.bailout_test_comparison == NOT_EQUAL) { // !=
                    greater = "Escaped";
                    equal = "Not Escaped";
                    lower = "Escaped";
                }

                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.bailout_test_user_formula + " > " + s.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + greater + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.bailout_test_user_formula + " &#60; " + s.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + lower + "<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.bailout_test_user_formula + " = " + s.bailout_test_user_formula2 + "]</font> <font color='" + keyword_color + "'>then</font> " + equal + "<br>";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_CIRCLE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_SQUARE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout or abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_RHOMBUS) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) + abs(im(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_STRIP) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[abs(re(z)) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            else if(s.bailout_test_algorithm == BAILOUT_CONDITION_HALFPLANE) {
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[re(z) >= bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br>";
            }
            overview += "<br>";
            
            overview += "<b><font color='red'>Bailout:</font></b> " + s.bailout + "<br><br>";

            if(s.function == MAGNET1 || s.function == MAGNET2) {
                overview += "<b><font color='red'>Bailout Condition 2:</font></b> Escaping when a complex value almost reaches 1 + 0i (convergence).<br>";
                overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - 1 - 0i) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
                overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br><br>";
                
                overview += "<b><font color='red'>Convergent Bailout:</font></b> " + ThreadDraw.getConvergentBailout() + "<br><br>";
            }
  
        }
        else if(!s.ds.domain_coloring) {
            overview += "<b><font color='red'>Bailout Condition:</font></b> Escaping when two consecutive complex values are almost the same (convergence).<br>";
            overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[norm(z - p) &#60;= convergent bailout]</font> <font color='" + keyword_color + "'>then</font> Escaped<br>";
            overview += tab + "<font color='" + keyword_color + "'>else then</font> Not Escaped<br><br>";
        
            overview += "<b><font color='red'>Convergent Bailout:</font></b> " + ThreadDraw.getConvergentBailout() + "<br><br>";
        }
        overview += "<b><font color='red'>Rotation:</font></b> " + s.rotation + " <font color='" + keyword_color + "'>degrees about</font> " + Complex.toString2(s.rotation_center[0], s.rotation_center[1]) + "<br><br>";

        overview += "<b><font color='red'>Stretch Factor:</font></b> " + s.height_ratio + "<br><br>";

        if(!s.ds.domain_coloring) {
            overview += "<b><font color='red'>Out Coloring Method:</font></b> " + OutColoringModesMenu.outColoringNames[s.out_coloring_algorithm] + "<br>";

            if(s.out_coloring_algorithm == USER_OUTCOLORING_ALGORITHM) {
                if(s.user_out_coloring_algorithm == 0) {
                    overview += tab + "out = " + s.outcoloring_formula + "<br>";
                }
                else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_outcoloring_conditions[0] + " > " + s.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.user_outcoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_outcoloring_conditions[0] + " &#60; " + s.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.user_outcoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_outcoloring_conditions[0] + " = " + s.user_outcoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> out = " + s.user_outcoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";

            overview += "<b><font color='red'>In Coloring Method:</font></b> " + InColoringModesMenu.inColoringNames[s.in_coloring_algorithm] + "<br>";
            if(s.in_coloring_algorithm == USER_INCOLORING_ALGORITHM) {
                if(s.user_in_coloring_algorithm == 0) {
                    overview += tab + "in = " + s.incoloring_formula + "<br>";
                }
                else {
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_incoloring_conditions[0] + " > " + s.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.user_incoloring_condition_formula[0] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_incoloring_conditions[0] + " &#60;" + s.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.user_incoloring_condition_formula[1] + "<br>";
                    overview += tab + "<font color='" + keyword_color + "'>if</font> <font color='" + condition_color + "'>[" + s.user_incoloring_conditions[0] + " = " + s.user_incoloring_conditions[1] + "]</font> <font color='" + keyword_color + "'>then</font> in = " + s.user_incoloring_condition_formula[2] + "<br>";
                }
            }
            overview += "<br>";
        }

        if(!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring)) {
            overview += "<b><font color='red'>Palette:</font></b> " + PaletteMenu.paletteNames[s.color_choice] + "<br><br>";
            overview += "<b><font color='red'>Palette Offset:</font></b> " + s.color_cycling_location + "<br><br>";
            overview += "<b><font color='red'>Transfer Function:</font></b> " + ColorTransferMenu.colorTransferNames[s.transfer_function] + "<br><br>";
            overview += "<b><font color='red'>Color Intensity:</font></b> " + s.color_intensity + "<br><br>";
            
            if((s.ds.domain_coloring && s.ds.use_palette_domain_coloring)){
                if(s.smoothing) {
                    overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                    overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";
                }
            }
            else {
                if(s.smoothing) {
                    overview += "<b><font color='red'>Color Smoothing:</font></b><br>";
                    if(s.escaping_smooth_algorithm == 0) {
                        overview += tab + "Escaping Smooth Algorithm 1<br>";
                    }
                    else {
                        overview += tab + "Escaping Smooth Algorithm 2<br>";
                    }
                    if(s.converging_smooth_algorithm == 0) {
                        overview += tab + "Converging Smooth Algorithm 1<br>";
                    }
                    else {
                        overview += tab + "Converging Smooth Algorithm 2<br>";
                    }
                    overview += tab + "Interpolation = " + color_interp_str[s.color_smoothing_method] + "<br><br>";
                }
            }
        }

        if(!s.ds.domain_coloring) {
            if(s.bms.bump_map) {
                overview += "<b><font color='red'>Bump Mapping:</font></b><br>";
                overview += tab + "Light Direction = " + s.bms.lightDirectionDegrees + " degrees<br>";
                overview += tab + "Depth = " + s.bms.bumpMappingDepth + "<br>";
                overview += tab + "Strength = " + s.bms.bumpMappingStrength + "<br>";
                overview += tab + "Transfer Function = " + Constants.bumpTransferNames[s.bms.bump_transfer_function] + "<br>";
                overview += tab + "Transfer Factor = " + s.bms.bump_transfer_factor + "<br>";
                overview += tab + "Processing Method = " + Constants.bumpProcessingMethod[s.bms.bumpProcessing] + "<br>";
                
                if(s.bms.bumpProcessing == 1) {
                     overview += tab + "Color Blending = " + s.bms.bump_blending + "<br>";
                }
                overview += tab + "Noise Reduction Factor = " + s.bms.bm_noise_reducing_factor + "<br><br>";
            }

            if(s.ens.entropy_coloring) {
                overview += "<b><font color='red'>Entropy Coloring:</font></b><br>";
                overview += tab + "Factor = " + s.ens.entropy_palette_factor + "<br>";
                overview += tab + "Offset = " + s.ens.entropy_offset + "<br>";
                overview += tab + "Color Blending = " + s.ens.en_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + s.ens.en_noise_reducing_factor + "<br><br>";
            }

            if(s.ofs.offset_coloring) {
                overview += "<b><font color='red'>Offset Coloring:</font></b><br>";
                overview += tab + "Offset = " + s.ofs.post_process_offset + "<br>";
                overview += tab + "Color Blending = " + s.ofs.of_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + s.ofs.of_noise_reducing_factor + "<br><br>";
            }

            if(s.rps.rainbow_palette) {
                overview += "<b><font color='red'>Rainbow Palette:</font></b><br>";
                overview += tab + "Factor = " + s.rps.rainbow_palette_factor + "<br>";
                overview += tab + "Offset = " + s.rps.rainbow_offset + "<br>";
                overview += tab + "Color Blending = " + s.rps.rp_blending + "<br>";
                overview += tab + "Noise Reduction Factor = " + s.rps.rp_noise_reducing_factor + "<br><br>";
            }

            if(s.gss.greyscale_coloring) {
                overview += "<b><font color='red'>Greyscale Coloring:</font></b><br>";
                overview += tab + "Noise Reduction Factor = " + s.gss.gs_noise_reducing_factor + "<br><br>";
            }

            if(s.exterior_de) {
                overview += "<b><font color='red'>Distance Estimation:</font></b><br>";

                if(s.inverse_dem) {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                }
                else {
                    overview += tab + "Factor = " + s.exterior_de_factor + "<br><br>";
                }
            }

            if(s.fdes.fake_de) {
                overview += "<b><font color='red'>Fake Distance Estimation:</font></b><br>";

                if(s.fdes.inverse_fake_dem) {
                    overview += tab + "Factor = " + s.fdes.fake_de_factor + "<br>";
                    overview += tab + "Inverted Coloring<br><br>";
                }
                else {
                    overview += tab + "Factor = " + s.fdes.fake_de_factor + "<br><br>";
                }
            }
        }
        
        overview += "<b><font color='red'>Color Blending:</font></b> " + ColorBlendingMenu.colorBlendingNames[s.color_blending] + "<br><br>";
        
        if(s.ds.domain_coloring) {
            if(!s.ds.customDomainColoring) {
                overview += "<b><font color='red'>Domain Coloring:</font></b><br>";
                overview += tab + "Algorithm = " + Constants.domainAlgNames[s.ds.domain_coloring_alg] + "<br><br>";
            }
            else {
                overview += "<b><font color='red'>Custom Domain Coloring:</font></b><br>";
                
                if(s.ds.drawColor) {
                    overview += tab + "Color = " + Constants.domainColors[s.ds.colorType] + "<br>";
                }
                
                if(s.ds.drawContour) {
                    overview += tab + "Contour = " + Constants.domainContours[s.ds.contourType] + "<br>";
                    overview += tab2 + "Color Blending = " + s.ds.contourBlending + "<br>";
                }
                
                if(s.ds.drawGrid) {
                    overview += tab + "Grid<br>";
                    overview += tab2 + "Strength = " + s.ds.gridBlending + "<br>";
                }
                
                if(s.ds.drawCircles) {
                    overview += tab + "Circles<br>";
                    overview += tab2 + "Strength = " + s.ds.circlesBlending + "<br>";
                }
                
                if(s.ds.drawIsoLines) {
                    overview += tab + "Iso-Argument Lines<br>";
                    overview += tab2 + "Width = " + s.ds.iso_factor + "<br>";
                    overview += tab2 + "Strength = " + s.ds.isoLinesBlendingFactor + "<br>";
                }
                
                overview += tab + "Circle Log Base = " + s.ds.logBase + "<br>";
                overview += tab + "Grid Spacing = " + s.ds.gridFactor + "<br>";
                overview += tab + "Norm Type = " + s.ds.normType + "<br>";
                overview += tab + "Iso-Argument Line Distance = " + Constants.argumentLinesDistance[s.ds.iso_distance] + "<br><br>"; 
            }
            
        }
        
        int active = 0;
        for(int i = 0; i < s.filters.length; i++) {
            if(s.filters[i]) {
                active++;
            }
        }
        
        if(active > 0) {
            overview += "<b><font color='red'>Filters:</font></b><br>";
            if(s.filters[ANTIALIASING]) {
                overview += tab + FiltersMenu.filterNames[ANTIALIASING] + "<br>";
            }
            
            for(int i = 0; i < s.filters_order.length; i++) {
                if(s.filters[s.filters_order[i]]) {
                    overview += tab + FiltersMenu.filterNames[s.filters_order[i]] + "<br>";
                }
            }
            overview += "<br>";
        }

        overview += "</font></html>";

        textArea.setText(overview);
 
        JLabel palette_label = new JLabel();
        palette_label.setIcon(new ImageIcon(getPalettePreview(s, s.color_cycling_location, 800, 36)));
        palette_label.setToolTipText("Displays the active palette.");
        
        JLabel palette_text_label = new JLabel("Palette:");
        
        if(!(!s.ds.domain_coloring || (s.ds.domain_coloring && s.ds.use_palette_domain_coloring))) {
            palette_label.setVisible(false);
            palette_text_label.setVisible(false);
        }
        
        Object[] message = {
       scroll_pane_2,
            " ",
            palette_text_label,
            palette_label,
        };

        textArea.setCaretPosition(0);

        JOptionPane.showMessageDialog(parent, message, "Options Overview", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static BufferedImage getPalettePreview(Settings s, int color_cycling_location, int width, int height) {
        
        Color[] c = null;
        if(s.color_choice < TOTAL_PALETTES - 1) {
            c = CustomPalette.getPalette(CustomPaletteEditorFrame.editor_default_palettes[s.color_choice], INTERPOLATION_LINEAR, COLOR_SPACE_RGB, false, color_cycling_location, 0, PROCESSING_NONE);
        }
        else {
            c = CustomPalette.getPalette(s.custom_palette, s.color_interpolation, s.color_space, s.reversed_palette, color_cycling_location, s.scale_factor_palette_val, s.processing_alg);
        }

        BufferedImage palette_preview = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = palette_preview.createGraphics();
        for(int j = 0; j < c.length; j++) {
            if(s.smoothing) {
                GradientPaint gp = new GradientPaint(j * palette_preview.getWidth() / c.length, 0, c[j], (j + 1) * palette_preview.getWidth() / c.length, 0, c[(j + 1) % c.length]);
                g.setPaint(gp);
                g.fill(new Rectangle2D.Double(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight()));
            }
            else {
                g.setColor(c[j]);
                g.fillRect(j * palette_preview.getWidth() / c.length, 0, (j + 1) * palette_preview.getWidth() / c.length - j * palette_preview.getWidth() / c.length, palette_preview.getHeight());
            }
        }
        
        return palette_preview;
    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value != null && value instanceof javax.swing.plaf.FontUIResource) {
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
                + "implentation of the current software and its only provided in order to<br>"
                + "bypass an implementation design.<br><br>"
                + "Coloring algorithms that apply an offset to the palette, based<br>"
                + "on a specific condition, (Binary Decomposition Variant, Biomorph, Escape Time + Grid,<br>"
                + "Escape Time + Field Lines Variant) will return a value offseted by 50.<br>"
                + "The return value of these algorithms that contains the value 50 will be<br>"
                + "negative in order for the software to determine this special offset.<br>"
                + "For instance Binary Decomposition will produce the following:<br>"
                + "<b>(if im(z) &lt 0) then return -(n + 50) else return n</b><br><br>"
                + "This constant offset value of 50, is creating noisy images when<br>"
                + "applying processing algorithms like Bump-Mapping or Entropy Coloring.<br>"
                + "The same issue arises also in 3d mode, so in order to alleviate the problem,<br>"
                + "this constant needs to be subtracted before applying any processing.<br><br>"
                + "This action is already been taken for the included coloring algorithms, but<br>"
                + "it will not be used for the User Out/In Coloring Algorithms.<br>"
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
}
