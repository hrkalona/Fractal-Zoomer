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
package fractalzoomer.gui;

import fractalzoomer.main.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author hrkalona2
 */
public class FractalFunctionsMenu extends JMenu {
	private static final long serialVersionUID = -2045979247578434919L;
	private MainWindow ptr;
    private JRadioButtonMenuItem[] fractal_functions;
    private JMenu mandelbrot_type_functions;
    private JMenu magnet_type_functions;
    private JMenu root_finding_functions;
    private JMenu newton_type_functions;
    private JMenu halley_type_functions;
    private JMenu schroder_type_functions;
    private JMenu householder_type_functions;
    private JMenu secant_type_functions;
    private JMenu steffensen_type_functions;
    private JMenu muller_type_functions;
    private JMenu parhalley_type_functions;
    private JMenu laguerre_type_functions;
    private JMenu durand_kerner_type_functions;
    private JMenu bairstow_type_functions;
    private JMenu newton_hines_type_functions;
    private JMenu aberth_ehrlich_type_functions;
    private JMenu root_finding_functions2;
    private JMenu root_finding_functions3;
    private JMenu whittaker_type_functions;
    private JMenu whittaker_double_convex_type_functions;
    private JMenu super_halley_type_functions;
    private JMenu traub_ostrowski_type_functions;
    private JMenu stirling_type_functions;
    private JMenu midpoint_type_functions;
    private JMenu jaratt_type_functions;
    private JMenu jaratt2_type_functions;
    private JMenu third_order_newtton_type_functions;
    private JMenu weerakoon_fernando_type_functions;
    private JMenu householder3_type_functions;
    private JMenu abbasbandy_type_functions;

    private JMenu contra_harmonic_newton_type_functions;
    private JMenu chun_ham_type_functions;
    private JMenu chun_kim_type_functions;

    private JMenu euler_chebyshev_type_functions;

    private JMenu ezzati_saleki2_type_functions;

    private JMenu homeier1_type_functions;

    private JMenu abbasbandy2_type_functions;

    private JMenu abbasbandy3_type_functions;

    private JMenu popovski1_type_functions;

    private JMenu changbum_chun1_type_functions;

    private JMenu changbum_chun2_type_functions;

    private JMenu king3_type_functions;

    private JMenu homeier2_type_functions;
    private JMenu kim_chun_type_functions;
    private JMenu kou_li_wang1_type_functions;

    private JMenu maheshweri_type_functions;
    private JMenu rafiullah1_type_functions;
    private JMenu rafis_rafiullah_type_functions;

    private JMenu changbum_chun3_type_functions;

    private JMenu ezzati_saleki1_type_functions;

    private JMenu feng_type_functions;

    private JMenu king1_type_functions;
    private JMenu noor_gupta_type_functions;
    private JMenu harmonic_simpson_newton_type_functions;

    private JMenu nedzhibov_type_functions;
    private JMenu simpson_newton_type_functions;
    private JMenu lambda_type_functions;
    private JMenu barnsley_type_functions;
    private JMenu szegedi_butterfly_type_functions;
    private JMenu math_type_functions;
    private JMenu formulas_type_functions;
    private JMenu kaliset_type_functions;
    private JMenu general_type_functions;
    private JMenu general_math_type_functions;
    private JMenu general_newton_variant_functions;
    private JMenu m_like_generalizations_type_functions;
    private JMenu c_azb_dze_type_functions;
    private JMenu c_azb_dze_f_g_type_functions;
    private JMenu zab_zde_fg_type_functions;
    private JMenu user_formulas_type_functions;
    private JMenu coupled_type_functions;
    private JCheckBoxMenuItem burning_ship_opt;
    private JMenuItem mandel_grass_opt;
    private int i;

    public static String[] functionNames;

    static {

        String[] rootPostfixes = {"3", "4", "Generalized 3", "Generalized 8", "Sin", "Cos", "Polynomial", "Formula"};
        String[] rootPostfixes2 = {"3", "4", "Generalized 3", "Generalized 8", "Polynomial"};

        functionNames = new String[MainWindow.TOTAL_FUNCTIONS];
        functionNames[MainWindow.MANDELBROT] = "Mandelbrot z = z^2 + c";
        functionNames[MainWindow.MANDELBROTCUBED] = "Multibrot z = z^3 + c";
        functionNames[MainWindow.MANDELBROTFOURTH] = "Multibrot z = z^4 + c";
        functionNames[MainWindow.MANDELBROTFIFTH] = "Multibrot z = z^5 + c";
        functionNames[MainWindow.MANDELBROTSIXTH] = "Multibrot z = z^6 + c";
        functionNames[MainWindow.MANDELBROTSEVENTH] = "Multibrot z = z^7 + c";
        functionNames[MainWindow.MANDELBROTEIGHTH] = "Multibrot z = z^8 + c";
        functionNames[MainWindow.MANDELBROTNINTH] = "Multibrot z = z^9 + c";
        functionNames[MainWindow.MANDELBROTTENTH] = "Multibrot z = z^10 + c";
        functionNames[MainWindow.MANDELBROTNTH] = "Multibrot z = z^n + c";
        functionNames[MainWindow.LAMBDA] = "Lambda";
        functionNames[MainWindow.MAGNET1] = "Magnet 1";
        functionNames[MainWindow.MAGNET2] = "Magnet 2";
        functionNames[MainWindow.NEWTON3] = "Newton 3";
        functionNames[MainWindow.NEWTON4] = "Newton 4";
        functionNames[MainWindow.NEWTONGENERALIZED3] = "Newton Generalized 3";
        functionNames[MainWindow.NEWTONGENERALIZED8] = "Newton Generalized 8";
        functionNames[MainWindow.NEWTONSIN] = "Newton Sin";
        functionNames[MainWindow.NEWTONCOS] = "Newton Cos";
        functionNames[MainWindow.NEWTONPOLY] = "Newton Polynomial";
        functionNames[MainWindow.BARNSLEY1] = "Barnsley 1";
        functionNames[MainWindow.BARNSLEY2] = "Barnsley 2";
        functionNames[MainWindow.BARNSLEY3] = "Barnsley 3";
        functionNames[MainWindow.MANDELBAR] = "Mandelbar";
        functionNames[MainWindow.SPIDER] = "Spider";
        functionNames[MainWindow.PHOENIX] = "Phoenix";
        functionNames[MainWindow.SIERPINSKI_GASKET] = "Sierpinski Gasket";
        functionNames[MainWindow.HALLEY3] = "Halley 3";
        functionNames[MainWindow.HALLEY4] = "Halley 4";
        functionNames[MainWindow.HALLEYGENERALIZED3] = "Halley Generalized 3";
        functionNames[MainWindow.HALLEYGENERALIZED8] = "Halley Generalized 8";
        functionNames[MainWindow.HALLEYSIN] = "Halley Sin";
        functionNames[MainWindow.HALLEYCOS] = "Halley Cos";
        functionNames[MainWindow.HALLEYPOLY] = "Halley Polynomial";
        functionNames[MainWindow.SCHRODER3] = "Schroder 3";
        functionNames[MainWindow.SCHRODER4] = "Schroder 4";
        functionNames[MainWindow.SCHRODERGENERALIZED3] = "Schroder Generalized 3";
        functionNames[MainWindow.SCHRODERGENERALIZED8] = "Schroder Generalized 8";
        functionNames[MainWindow.SCHRODERSIN] = "Schroder Sin";
        functionNames[MainWindow.SCHRODERCOS] = "Schroder Cos";
        functionNames[MainWindow.SCHRODERPOLY] = "Schroder Polynomial";
        functionNames[MainWindow.HOUSEHOLDER3] = "Householder 3";
        functionNames[MainWindow.HOUSEHOLDER4] = "Householder 4";
        functionNames[MainWindow.HOUSEHOLDERGENERALIZED3] = "Householder Generalized 3";
        functionNames[MainWindow.HOUSEHOLDERGENERALIZED8] = "Householder Generalized 8";
        functionNames[MainWindow.HOUSEHOLDERSIN] = "Householder Sin";
        functionNames[MainWindow.HOUSEHOLDERCOS] = "Householder Cos";
        functionNames[MainWindow.HOUSEHOLDERPOLY] = "Householder Polynomial";
        functionNames[MainWindow.SECANT3] = "Secant 3";
        functionNames[MainWindow.SECANT4] = "Secant 4";
        functionNames[MainWindow.SECANTGENERALIZED3] = "Secant Generalized 3";
        functionNames[MainWindow.SECANTGENERALIZED8] = "Secant Generalized 8";
        functionNames[MainWindow.SECANTCOS] = "Secant Cos";
        functionNames[MainWindow.SECANTPOLY] = "Secant Polynomial";
        functionNames[MainWindow.STEFFENSEN3] = "Steffensen 3";
        functionNames[MainWindow.STEFFENSEN4] = "Steffensen 4";
        functionNames[MainWindow.STEFFENSENGENERALIZED3] = "Steffensen Generalized 3";
        functionNames[MainWindow.MANDELPOLY] = "Multibrot Polynomial";
        functionNames[MainWindow.MANOWAR] = "Manowar";
        functionNames[MainWindow.FROTHY_BASIN] = "Frothy Basin";
        functionNames[MainWindow.MANDELBROTWTH] = "Multibrot z = z^w + c";
        functionNames[MainWindow.SZEGEDI_BUTTERFLY1] = "Szegedi Butterfly 1";
        functionNames[MainWindow.SZEGEDI_BUTTERFLY2] = "Szegedi Butterfly 2";
        functionNames[MainWindow.NOVA] = "Nova";
        functionNames[MainWindow.EXP] = "z = exp(z) + c";
        functionNames[MainWindow.LOG] = "z = log(z) + c";
        functionNames[MainWindow.SIN] = "z = sin(z) + c";
        functionNames[MainWindow.COS] = "z = cos(z) + c";
        functionNames[MainWindow.TAN] = "z = tan(z) + c";
        functionNames[MainWindow.COT] = "z = cot(z) + c";
        functionNames[MainWindow.SINH] = "z = sinh(z) + c";
        functionNames[MainWindow.COSH] = "z = cosh(z) + c";
        functionNames[MainWindow.TANH] = "z = tanh(z) + c";
        functionNames[MainWindow.COTH] = "z = coth(z) + c";
        functionNames[MainWindow.FORMULA1] = "z = 0.25z^2 + c + (z^2 + c)^-2";
        functionNames[MainWindow.FORMULA2] = "z = c(z + z^-1)";
        functionNames[MainWindow.FORMULA3] = "z = c(z^3 + z^-3)";
        functionNames[MainWindow.FORMULA4] = "z = c(5z^-1 + z^5)";
        functionNames[MainWindow.FORMULA5] = "z = c(z^5 - 5z)";
        functionNames[MainWindow.FORMULA6] = "z = c(2z^2 - z^4)";
        functionNames[MainWindow.FORMULA7] = "z = c(2z^-2 - z^-4)";
        functionNames[MainWindow.FORMULA8] = "z = c(5z^-1 - z^-5)";
        functionNames[MainWindow.FORMULA9] = "z = c(-3z^-3 + z^-9)";
        functionNames[MainWindow.FORMULA10] = "z = c(z^2.5 + z^-2.5)";
        functionNames[MainWindow.FORMULA11] = "z = c(z^2i + z^-2)";
        functionNames[MainWindow.FORMULA12] = "z = c(-3z^-2 + 2z^-3)";
        functionNames[MainWindow.FORMULA13] = "z = (c(2z - z^2) + 1)^2";
        functionNames[MainWindow.FORMULA14] = "z = (c(2z - z^2) + 1)^3";
        functionNames[MainWindow.FORMULA15] = "z = (c(z - z^2) + 1)^2";
        functionNames[MainWindow.FORMULA16] = "z = (c(z - z^2) + 1)^3";
        functionNames[MainWindow.FORMULA17] = "z = (c(z - z^2) + 1)^4";
        functionNames[MainWindow.FORMULA18] = "z = (c(z^3 - z^4) + 1)^5";
        functionNames[MainWindow.FORMULA19] = "z = abs(z^-1) + c";
        functionNames[MainWindow.FORMULA20] = "z = abs(z^2) + c";
        functionNames[MainWindow.FORMULA21] = "z = abs(z)/abs(c) + c";
        functionNames[MainWindow.FORMULA22] = "z = abs(z/c) + c";
        functionNames[MainWindow.FORMULA23] = "z = abs(z/(0.5 + 0.5i)) + c";
        functionNames[MainWindow.FORMULA24] = "z = abs(z)/c + c";
        functionNames[MainWindow.FORMULA25] = "z = abs(z)^-3 + c";
        functionNames[MainWindow.FORMULA26] = "z = abs(z^-3) + c";
        functionNames[MainWindow.FORMULA27] = "z = c((z + 2)^6 + (z + 2)^-6)";
        functionNames[MainWindow.FORMULA28] = "z = cz^2 + (cz^2)^-1";
        functionNames[MainWindow.FORMULA29] = "z = cz^2 + 1 + (cz^2 + 1)^-1";
        functionNames[MainWindow.FORMULA30] = "z = sin(z)c";
        functionNames[MainWindow.FORMULA31] = "z = cos(z)c";
        functionNames[MainWindow.FORMULA32] = "z = zlog(z + 1) + c";
        functionNames[MainWindow.FORMULA33] = "z = zsin(z) + c";
        functionNames[MainWindow.FORMULA34] = "z = zsinh(z) + c";
        functionNames[MainWindow.FORMULA35] = "z = 2 - 2cos(z) + c";
        functionNames[MainWindow.FORMULA36] = "z = 2cosh(z) - 2 + c";
        functionNames[MainWindow.FORMULA37] = "z = zsin(z) - c^2";
        functionNames[MainWindow.FORMULA38] = "z = z^2 + c/z^2";
        functionNames[MainWindow.FORMULA39] = "z = (z^2)exp(1/z) + c";
        functionNames[MainWindow.USER_FORMULA] = "User Formula";
        functionNames[MainWindow.USER_FORMULA_ITERATION_BASED] = "User Formula Iteration Based";
        functionNames[MainWindow.USER_FORMULA_CONDITIONAL] = "User Formula Conditional";
        functionNames[MainWindow.FORMULA40] = "z = (z^10 + c)/(z^8 + c) + c - 2";
        functionNames[MainWindow.FORMULA41] = "z = (z^16 - 10)/(z^14 - c) + c - 6";
        functionNames[MainWindow.FORMULA42] = "z = z - (z^3 + c)/(3z)";
        functionNames[MainWindow.FORMULA43] = "z = z - (z^4 + c)/(4z^2)";
        functionNames[MainWindow.FORMULA44] = "z = z - (z^5 + c)/(5z^3)";
        functionNames[MainWindow.FORMULA45] = "z = z - (z^8 + c)/(8z^6)";
        functionNames[MainWindow.FORMULA46] = "z = (-0.4 + 0.2i)cz^2 + (-0.275i/c)z + c";
        functionNames[MainWindow.NEWTONFORMULA] = "Newton Formula";
        functionNames[MainWindow.HALLEYFORMULA] = "Halley Formula";
        functionNames[MainWindow.SCHRODERFORMULA] = "Schroder Formula";
        functionNames[MainWindow.HOUSEHOLDERFORMULA] = "Householder Formula";
        functionNames[MainWindow.SECANTFORMULA] = "Secant Formula";
        functionNames[MainWindow.STEFFENSENFORMULA] = "Steffensen Formula";
        functionNames[MainWindow.STEFFENSENPOLY] = "Steffensen Polynomial";
        functionNames[MainWindow.COUPLED_MANDELBROT] = "Coupled Mandelbrot";
        functionNames[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP] = "Coupled Mandelbrot-Burning Ship";
        functionNames[MainWindow.USER_FORMULA_COUPLED] = "User Formula Coupled";

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.MULLER3] = "Muller " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.PARHALLEY3] = "Parhalley " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.LAGUERRE3] = "Laguerre " + rootPostfixes[i];
        }

        functionNames[MainWindow.KLEINIAN] = "Kleinian";
        functionNames[MainWindow.LAMBDA2] = "Lambda 2";
        functionNames[MainWindow.LAMBDA3] = "Lambda 3";
        functionNames[MainWindow.GENERIC_CaZbdZe] = "z = c * (alpha*z^beta + delta*z^epsilon)";

        for(int i = 0; i < rootPostfixes2.length; i++) {
            functionNames[i + MainWindow.DURAND_KERNER3] = "Durand/Kerner " + rootPostfixes2[i];
        }

        functionNames[MainWindow.MAGNETIC_PENDULUM] = "Magnetic Pendulum";
        functionNames[MainWindow.LYAPUNOV] = "Lyapunov";

        for(int i = 0; i < rootPostfixes2.length; i++) {
            functionNames[i + MainWindow.BAIRSTOW3] = "Bairstow " + rootPostfixes2[i];
        }

        functionNames[MainWindow.USER_FORMULA_NOVA] = "User Formula Nova";
        functionNames[MainWindow.GENERIC_CpAZpBC] = "z = (c^alpha) * (z^beta) + c";
        functionNames[MainWindow.INERTIA_GRAVITY] = "Modified Inertia/Gravity";
        functionNames[MainWindow.LAMBDA_FN_FN] = "Lambda(Fn || Fn)";
        functionNames[MainWindow.MANDEL_NEWTON] = "Mandel Newton Variation";
        functionNames[MainWindow.LAMBERT_W_VARIATION] = "Lambert W Variation";

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.NEWTON_HINES3] = "Newton-Hines " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.WHITTAKER3] = "Whittaker " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.WHITTAKERDOUBLECONVEX3] = "Whittaker Double Convex " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.SUPERHALLEY3] = "Super Halley " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.TRAUB_OSTROWSKI3] = "Traub/Ostrowski " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.STIRLING3] = "Stirling " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.MIDPOINT3] = "Midpoint " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes2.length; i++) {
            functionNames[i + MainWindow.ABERTH_EHRLICH3] = "Aberth/Ehrlich " + rootPostfixes2[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.JARATT3] = "Jaratt " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.JARATT23] = "Jaratt 2 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.THIRDORDERNEWTON3] = "Third Order Newton " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.WEERAKOON_FERNANDO3] = "Weerakoon/Fernando " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.HOUSEHOLDER33] = "Householder 3 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.ABBASBANDY3] = "Abbasbandy " + rootPostfixes[i];
        }

        functionNames[MainWindow.MAGNET13] = "Magnet 1 Cubed";
        functionNames[MainWindow.MAGNET14] = "Magnet 1 Fourth";
        functionNames[MainWindow.MAGNET23] = "Magnet 2 Cubed";
        functionNames[MainWindow.MAGNET24] = "Magnet 2 Fourth";

        functionNames[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE] = "Newton Third Degree Parameter Space";

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CONTRA_HARMONIC_NEWTON3] = "Contra Harmonic Newton " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CHUN_HAM3] = "Chun-Ham " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CHUN_KIM3] = "Chun-Kim " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.EULER_CHEBYSHEV3] = "Euler-Chebyshev " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.EZZATI_SALEKI23] = "Ezzati-Saleki 2 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.HOMEIER13] = "Homeier " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.ABBASBANDY23] = "Abbasbandy 2 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.ABBASBANDY33] = "Abbasbandy 3 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.POPOVSKI13] = "Popovski " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CHANGBUM_CHUN13] = "Changbum-Chun " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CHANGBUM_CHUN23] = "Changbum-Chun 2 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.KING33] = "King 3 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.HOMEIER23] = "Homeier 2 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.KIM_CHUN3] = "Kim-Chun " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.KOU_LI_WANG13] = "Kou-Li-Wang " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.MAHESHWERI3] = "Maheshweri " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.RAFIULLAH13] = "Rafiullah " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.RAFIS_RAFIULLAH3] = "Rafis-Rafiullah " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.CHANGBUM_CHUN33] = "Changbum-Chun 3 " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.EZZATI_SALEKI13] = "Ezzati-Saleki " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.FENG3] = "Feng " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.KING13] = "King " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.NOOR_GUPTA3] = "Noor-Gupta " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.HARMONIC_SIMPSON_NEWTON3] = "Harmonic-Simpson-Newton " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.NEDZHIBOV3] = "Nedzhibov " + rootPostfixes[i];
        }

        for(int i = 0; i < rootPostfixes.length; i++) {
            functionNames[i + MainWindow.SIMPSON_NEWTON3] = "Simpson-Newton " + rootPostfixes[i];
        }
    }

    public FractalFunctionsMenu(MainWindow ptr2, String name, int function) {

        super(name);

        this.ptr = ptr2;

        setIcon(getIcon("/fractalzoomer/icons/functions.png"));

        mandelbrot_type_functions = new JMenu("Mandelbrot Type");
        magnet_type_functions = new JMenu("Magnet Type");

        root_finding_functions = new JMenu("Root Finding Methods");
        newton_type_functions = new JMenu("Newton Method");
        halley_type_functions = new JMenu("Halley Method");
        schroder_type_functions = new JMenu("Schroder Method");
        householder_type_functions = new JMenu("Householder Method");
        secant_type_functions = new JMenu("Secant Method");
        steffensen_type_functions = new JMenu("Steffensen Method");
        muller_type_functions = new JMenu("Muller Method");
        parhalley_type_functions = new JMenu("Parhalley Method");
        laguerre_type_functions = new JMenu("Laguerre Method");
        durand_kerner_type_functions = new JMenu("Durand/Kerner Method");
        bairstow_type_functions = new JMenu("Bairstow Method");
        newton_hines_type_functions = new JMenu("Newton-Hines Method");
        aberth_ehrlich_type_functions = new JMenu("Aberth/Ehrlich Method");
        jaratt_type_functions = new JMenu("Jaratt Method");
        jaratt2_type_functions = new JMenu("Jaratt 2 Method");
        third_order_newtton_type_functions = new JMenu("Third Order Newton Method");
        weerakoon_fernando_type_functions = new JMenu("Weerakoon/Fernando Method");
        householder3_type_functions = new JMenu("Householder 3 Method");
        abbasbandy_type_functions = new JMenu("Abbasbandy Method");
        contra_harmonic_newton_type_functions = new JMenu("Contra Harmonic Newton Method");
        chun_ham_type_functions = new JMenu("Chun-Ham Method");
        chun_kim_type_functions = new JMenu("Chun-Kim Method");
        euler_chebyshev_type_functions = new JMenu("Euler-Chebyshev Method");
        ezzati_saleki2_type_functions = new JMenu("Ezzati-Saleki 2 Method");
        homeier1_type_functions = new JMenu("Homeier Method");
        abbasbandy2_type_functions = new JMenu("Abbasbandy 2 Method");
        abbasbandy3_type_functions = new JMenu("Abbasbandy 3 Method");
        popovski1_type_functions = new JMenu("Popovski Method");
        changbum_chun1_type_functions = new JMenu("Changbum-Chun Method");
        changbum_chun2_type_functions = new JMenu("Changbum-Chun 2 Method");
        king3_type_functions = new JMenu("King 3 Method");
        homeier2_type_functions = new JMenu("Homeier 2 Method");
        kim_chun_type_functions = new JMenu("Kim-Chun Method");
        kou_li_wang1_type_functions = new JMenu("Kou-Li-Wang Method");
        maheshweri_type_functions = new JMenu("Maheshweri Method");
        rafiullah1_type_functions = new JMenu("Rafiullah Method");
        rafis_rafiullah_type_functions = new JMenu("Rafis-Rafiullah Method");
        changbum_chun3_type_functions = new JMenu("Changbum-Chun 3 Method");
        ezzati_saleki1_type_functions = new JMenu("Ezzati-Saleki Method");
        feng_type_functions = new JMenu("Feng Method");
        king1_type_functions = new JMenu("King Method");
        noor_gupta_type_functions = new JMenu("Noor-Gupta Method");
        harmonic_simpson_newton_type_functions = new JMenu("Harmonic-Simpson-Newton Method");
        nedzhibov_type_functions = new JMenu("Nedzhibov Method");
        simpson_newton_type_functions = new JMenu("Simpson-Newton Method");
        
        root_finding_functions2 = new JMenu("Root Finding Methods (2)");
        root_finding_functions3 = new JMenu("Root Finding Methods (3)");
        whittaker_type_functions = new JMenu("Whittaker Method");
        whittaker_double_convex_type_functions = new JMenu("Whittaker Double Convex Method");
        super_halley_type_functions = new JMenu("Super Halley Method");
        traub_ostrowski_type_functions = new JMenu("Traub/Ostrowski Method");
        stirling_type_functions = new JMenu("Stirling Method");
        midpoint_type_functions = new JMenu("Midpoint Method");

        barnsley_type_functions = new JMenu("Barnsley Type");
        
        lambda_type_functions = new JMenu("Lambda Type");

        szegedi_butterfly_type_functions = new JMenu("Szegedi Butterfly Type");

        math_type_functions = new JMenu("Math Library Type");

        formulas_type_functions = new JMenu("Formulas");

        kaliset_type_functions = new JMenu("Kaliset Type");
        m_like_generalizations_type_functions = new JMenu("M-Like Type");
        general_type_functions = new JMenu("General Type");
        general_math_type_functions = new JMenu("Math Library Type");
        general_newton_variant_functions = new JMenu("z = z - (z^n + c)/(nz^(n - 2))");
        c_azb_dze_type_functions = new JMenu("z = c(az^b + dz^e)");
        c_azb_dze_f_g_type_functions = new JMenu("z = (c(az^b + dz^e) + f)^g");

        coupled_type_functions = new JMenu("Coupled Type");

        zab_zde_fg_type_functions = new JMenu("z = (z^a + b)/(z^d + e) + f + g");

        user_formulas_type_functions = new JMenu("User Formulas");

        burning_ship_opt = new JCheckBoxMenuItem("Burning Ship");
        mandel_grass_opt = new JMenuItem("Mandel Grass");

        burning_ship_opt.setToolTipText("Enables the burning ship variation.");
        mandel_grass_opt.setToolTipText("Enables the mandel grass variation.");

        burning_ship_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
        mandel_grass_opt.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));

        fractal_functions = new JRadioButtonMenuItem[functionNames.length];

        ButtonGroup functions_button_group = new ButtonGroup();

        fractal_functions[MainWindow.MANDELBROT] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDELBROT]);
        fractal_functions[MainWindow.MANDELBROT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBROT);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBROT]);
        functions_button_group.add(fractal_functions[MainWindow.MANDELBROT]);

        for(i = 1; i < 9; i++) {
            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                int temp = i;

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            mandelbrot_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        fractal_functions[MainWindow.MANDELBROTNTH] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDELBROTNTH]);
        fractal_functions[MainWindow.MANDELBROTNTH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBROTNTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBROTNTH]);
        functions_button_group.add(fractal_functions[MainWindow.MANDELBROTNTH]);

        fractal_functions[MainWindow.MANDELBROTWTH] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDELBROTWTH]);
        fractal_functions[MainWindow.MANDELBROTWTH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBROTWTH);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBROTWTH]);
        functions_button_group.add(fractal_functions[MainWindow.MANDELBROTWTH]);

        fractal_functions[MainWindow.MANDELPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDELPOLY]);
        fractal_functions[MainWindow.MANDELPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELPOLY);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.MANDELPOLY]);

        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(burning_ship_opt);
        mandelbrot_type_functions.addSeparator();
        mandelbrot_type_functions.add(mandel_grass_opt);
        mandelbrot_type_functions.addSeparator();

        add(mandelbrot_type_functions);

        addSeparator();

        fractal_functions[MainWindow.FORMULA1] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA1]);
        fractal_functions[MainWindow.FORMULA1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA1);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA1]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA1]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA28] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA28]);
        fractal_functions[MainWindow.FORMULA28].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA28);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA28]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA28]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA29] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA29]);
        fractal_functions[MainWindow.FORMULA29].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA29);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA29]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA29]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA38] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA38]);
        fractal_functions[MainWindow.FORMULA38].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA38);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA38]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA38]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA39] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA39]);
        fractal_functions[MainWindow.FORMULA39].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA39);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA39]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA39]);
        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA46] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA46]);
        fractal_functions[MainWindow.FORMULA46].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA46);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.FORMULA46]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA46]);
        m_like_generalizations_type_functions.addSeparator();
        
        fractal_functions[MainWindow.GENERIC_CpAZpBC] = new JRadioButtonMenuItem(functionNames[MainWindow.GENERIC_CpAZpBC]);
        fractal_functions[MainWindow.GENERIC_CpAZpBC].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.GENERIC_CpAZpBC);

            }
        });
        m_like_generalizations_type_functions.add(fractal_functions[MainWindow.GENERIC_CpAZpBC]);
        functions_button_group.add(fractal_functions[MainWindow.GENERIC_CpAZpBC]);
        m_like_generalizations_type_functions.addSeparator();
          
        fractal_functions[MainWindow.FORMULA2] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA2]);
        fractal_functions[MainWindow.FORMULA2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA2);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA2]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA2]);

        fractal_functions[MainWindow.FORMULA3] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA3]);
        fractal_functions[MainWindow.FORMULA3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA3);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA3]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA3]);

        fractal_functions[MainWindow.FORMULA4] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA4]);
        fractal_functions[MainWindow.FORMULA4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA4);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA4]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA4]);

        fractal_functions[MainWindow.FORMULA5] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA5]);
        fractal_functions[MainWindow.FORMULA5].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA5);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA5]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA5]);

        fractal_functions[MainWindow.FORMULA6] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA6]);
        fractal_functions[MainWindow.FORMULA6].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA6);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA6]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA6]);

        fractal_functions[MainWindow.FORMULA7] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA7]);
        fractal_functions[MainWindow.FORMULA7].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA7);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA7]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA7]);

        fractal_functions[MainWindow.FORMULA8] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA8]);
        fractal_functions[MainWindow.FORMULA8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA8);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA8]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA8]);

        fractal_functions[MainWindow.FORMULA9] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA9]);
        fractal_functions[MainWindow.FORMULA9].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA9);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA9]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA9]);

        fractal_functions[MainWindow.FORMULA10] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA10]);
        fractal_functions[MainWindow.FORMULA10].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA10);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA10]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA10]);

        fractal_functions[MainWindow.FORMULA11] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA11]);
        fractal_functions[MainWindow.FORMULA11].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA11);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA11]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA11]);

        fractal_functions[MainWindow.FORMULA12] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA12]);
        fractal_functions[MainWindow.FORMULA12].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA12);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA12]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA12]);

        fractal_functions[MainWindow.FORMULA27] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA27]);
        fractal_functions[MainWindow.FORMULA27].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA27);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.FORMULA27]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA27]);
        
        fractal_functions[MainWindow.GENERIC_CaZbdZe] = new JRadioButtonMenuItem(functionNames[MainWindow.GENERIC_CaZbdZe]);
        fractal_functions[MainWindow.GENERIC_CaZbdZe].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.GENERIC_CaZbdZe);

            }
        });
        c_azb_dze_type_functions.add(fractal_functions[MainWindow.GENERIC_CaZbdZe]);
        functions_button_group.add(fractal_functions[MainWindow.GENERIC_CaZbdZe]);

        m_like_generalizations_type_functions.add(c_azb_dze_type_functions);

        m_like_generalizations_type_functions.addSeparator();

        fractal_functions[MainWindow.FORMULA13] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA13]);
        fractal_functions[MainWindow.FORMULA13].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA13);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA13]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA13]);

        fractal_functions[MainWindow.FORMULA14] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA14]);
        fractal_functions[MainWindow.FORMULA14].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA14);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA14]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA14]);

        fractal_functions[MainWindow.FORMULA15] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA15]);
        fractal_functions[MainWindow.FORMULA15].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA15);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA15]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA15]);

        fractal_functions[MainWindow.FORMULA16] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA16]);
        fractal_functions[MainWindow.FORMULA16].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA16);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA16]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA16]);

        fractal_functions[MainWindow.FORMULA17] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA17]);
        fractal_functions[MainWindow.FORMULA17].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA17);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA17]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA17]);

        fractal_functions[MainWindow.FORMULA18] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA18]);
        fractal_functions[MainWindow.FORMULA18].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA18);

            }
        });
        c_azb_dze_f_g_type_functions.add(fractal_functions[MainWindow.FORMULA18]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA18]);

        m_like_generalizations_type_functions.add(c_azb_dze_f_g_type_functions);

        fractal_functions[MainWindow.FORMULA40] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA40]);
        fractal_functions[MainWindow.FORMULA40].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA40);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[MainWindow.FORMULA40]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA40]);

        fractal_functions[MainWindow.FORMULA41] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA41]);
        fractal_functions[MainWindow.FORMULA41].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA41);

            }
        });
        zab_zde_fg_type_functions.add(fractal_functions[MainWindow.FORMULA41]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA41]);

        m_like_generalizations_type_functions.addSeparator();
        m_like_generalizations_type_functions.add(zab_zde_fg_type_functions);

        fractal_functions[MainWindow.FORMULA19] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA19]);
        fractal_functions[MainWindow.FORMULA19].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA19);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA19]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA19]);

        fractal_functions[MainWindow.FORMULA20] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA20]);
        fractal_functions[MainWindow.FORMULA20].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA20);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA20]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA20]);

        fractal_functions[MainWindow.FORMULA21] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA21]);
        fractal_functions[MainWindow.FORMULA21].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA21);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA21]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA21]);

        fractal_functions[MainWindow.FORMULA22] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA22]);
        fractal_functions[MainWindow.FORMULA22].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA22);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA22]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA22]);

        fractal_functions[MainWindow.FORMULA23] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA23]);
        fractal_functions[MainWindow.FORMULA23].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA23);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA23]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA23]);

        fractal_functions[MainWindow.FORMULA24] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA24]);
        fractal_functions[MainWindow.FORMULA24].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA24);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA24]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA24]);

        fractal_functions[MainWindow.FORMULA25] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA25]);
        fractal_functions[MainWindow.FORMULA25].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA25);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA25]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA25]);

        fractal_functions[MainWindow.FORMULA26] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA26]);
        fractal_functions[MainWindow.FORMULA26].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA26);

            }
        });
        kaliset_type_functions.add(fractal_functions[MainWindow.FORMULA26]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA26]);

        fractal_functions[MainWindow.COUPLED_MANDELBROT] = new JRadioButtonMenuItem(functionNames[MainWindow.COUPLED_MANDELBROT]);
        fractal_functions[MainWindow.COUPLED_MANDELBROT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COUPLED_MANDELBROT);

            }
        });
        coupled_type_functions.add(fractal_functions[MainWindow.COUPLED_MANDELBROT]);
        functions_button_group.add(fractal_functions[MainWindow.COUPLED_MANDELBROT]);

        fractal_functions[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP] = new JRadioButtonMenuItem(functionNames[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP]);
        fractal_functions[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COUPLED_MANDELBROT_BURNING_SHIP);

            }
        });
        coupled_type_functions.add(fractal_functions[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP]);
        functions_button_group.add(fractal_functions[MainWindow.COUPLED_MANDELBROT_BURNING_SHIP]);
       

        formulas_type_functions.add(m_like_generalizations_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(kaliset_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(general_type_functions);
        formulas_type_functions.addSeparator();
        formulas_type_functions.add(coupled_type_functions);

        general_type_functions.add(general_math_type_functions);
        general_type_functions.addSeparator();
        general_type_functions.add(general_newton_variant_functions);
        
        general_type_functions.addSeparator();
        
        fractal_functions[MainWindow.MANDEL_NEWTON] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDEL_NEWTON]);
        fractal_functions[MainWindow.MANDEL_NEWTON].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDEL_NEWTON);

            }
        });
        general_type_functions.add(fractal_functions[MainWindow.MANDEL_NEWTON]);
        functions_button_group.add(fractal_functions[MainWindow.MANDEL_NEWTON]);
        
        general_type_functions.addSeparator();
        
        fractal_functions[MainWindow.LAMBERT_W_VARIATION] = new JRadioButtonMenuItem(functionNames[MainWindow.LAMBERT_W_VARIATION]);
        fractal_functions[MainWindow.LAMBERT_W_VARIATION].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBERT_W_VARIATION);

            }
        });
        general_type_functions.add(fractal_functions[MainWindow.LAMBERT_W_VARIATION]);
        functions_button_group.add(fractal_functions[MainWindow.LAMBERT_W_VARIATION]);

        add(formulas_type_functions);

        addSeparator();
        

        fractal_functions[MainWindow.LAMBDA] = new JRadioButtonMenuItem(functionNames[MainWindow.LAMBDA]);
        fractal_functions[MainWindow.LAMBDA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBDA);

            }
        });
        lambda_type_functions.add(fractal_functions[MainWindow.LAMBDA]);
        functions_button_group.add(fractal_functions[MainWindow.LAMBDA]);
        
        fractal_functions[MainWindow.LAMBDA2] = new JRadioButtonMenuItem(functionNames[MainWindow.LAMBDA2]);
        fractal_functions[MainWindow.LAMBDA2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBDA2);

            }
        });
        lambda_type_functions.add(fractal_functions[MainWindow.LAMBDA2]);
        functions_button_group.add(fractal_functions[MainWindow.LAMBDA2]);
        
        fractal_functions[MainWindow.LAMBDA3] = new JRadioButtonMenuItem(functionNames[MainWindow.LAMBDA3]);
        fractal_functions[MainWindow.LAMBDA3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBDA3);

            }
        });
        lambda_type_functions.add(fractal_functions[MainWindow.LAMBDA3]);
        functions_button_group.add(fractal_functions[MainWindow.LAMBDA3]);
        
        fractal_functions[MainWindow.LAMBDA_FN_FN] = new JRadioButtonMenuItem(functionNames[MainWindow.LAMBDA_FN_FN]);
        fractal_functions[MainWindow.LAMBDA_FN_FN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LAMBDA_FN_FN);

            }
        });
        lambda_type_functions.add(fractal_functions[MainWindow.LAMBDA_FN_FN]);
        functions_button_group.add(fractal_functions[MainWindow.LAMBDA_FN_FN]);
        
        add(lambda_type_functions);
        addSeparator();

        fractal_functions[MainWindow.MAGNET1] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET1]);
        fractal_functions[MainWindow.MAGNET1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET1);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET1]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET1]);

        fractal_functions[MainWindow.MAGNET13] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET13]);
        fractal_functions[MainWindow.MAGNET13].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET13);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET13]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET13]);

        fractal_functions[MainWindow.MAGNET14] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET14]);
        fractal_functions[MainWindow.MAGNET14].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET14);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET14]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET14]);

        fractal_functions[MainWindow.MAGNET2] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET2]);
        fractal_functions[MainWindow.MAGNET2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET2);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET2]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET2]);

        fractal_functions[MainWindow.MAGNET23] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET23]);
        fractal_functions[MainWindow.MAGNET23].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET23);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET23]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET23]);

        fractal_functions[MainWindow.MAGNET24] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNET24]);
        fractal_functions[MainWindow.MAGNET24].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNET24);

            }
        });
        magnet_type_functions.add(fractal_functions[MainWindow.MAGNET24]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNET24]);

        add(magnet_type_functions);
        addSeparator();

        fractal_functions[MainWindow.NEWTON3] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTON3]);
        fractal_functions[MainWindow.NEWTON3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTON3);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTON3]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTON3]);

        fractal_functions[MainWindow.NEWTON4] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTON4]);
        fractal_functions[MainWindow.NEWTON4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTON4);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTON4]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTON4]);

        fractal_functions[MainWindow.NEWTONGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONGENERALIZED3]);
        fractal_functions[MainWindow.NEWTONGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONGENERALIZED3);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONGENERALIZED3]);

        fractal_functions[MainWindow.NEWTONGENERALIZED8] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONGENERALIZED8]);
        fractal_functions[MainWindow.NEWTONGENERALIZED8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONGENERALIZED8);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONGENERALIZED8]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONGENERALIZED8]);

        fractal_functions[MainWindow.NEWTONSIN] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONSIN]);
        fractal_functions[MainWindow.NEWTONSIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONSIN);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONSIN]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONSIN]);

        fractal_functions[MainWindow.NEWTONCOS] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONCOS]);
        fractal_functions[MainWindow.NEWTONCOS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONCOS);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONCOS]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONCOS]);

        fractal_functions[MainWindow.NEWTONPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONPOLY]);
        fractal_functions[MainWindow.NEWTONPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONPOLY);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONPOLY]);

        fractal_functions[MainWindow.NEWTONFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTONFORMULA]);
        fractal_functions[MainWindow.NEWTONFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTONFORMULA);

            }
        });
        newton_type_functions.add(fractal_functions[MainWindow.NEWTONFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTONFORMULA]);

        root_finding_functions.add(newton_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(halley_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(schroder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(householder_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(secant_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(steffensen_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(muller_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(parhalley_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(laguerre_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(durand_kerner_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(bairstow_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(newton_hines_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(aberth_ehrlich_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(feng_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(harmonic_simpson_newton_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(nedzhibov_type_functions);
        root_finding_functions.addSeparator();
        root_finding_functions.add(simpson_newton_type_functions);

        
        
        root_finding_functions2.add(whittaker_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(whittaker_double_convex_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(super_halley_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(traub_ostrowski_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(stirling_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(midpoint_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(jaratt_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(jaratt2_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(third_order_newtton_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(weerakoon_fernando_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(householder3_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(abbasbandy_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(abbasbandy2_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(abbasbandy3_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(king1_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(king3_type_functions);
        root_finding_functions2.addSeparator();
        root_finding_functions2.add(noor_gupta_type_functions);


        root_finding_functions3.add(contra_harmonic_newton_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(chun_ham_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(chun_kim_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(euler_chebyshev_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(ezzati_saleki1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(ezzati_saleki2_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(homeier1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(homeier2_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(popovski1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(changbum_chun1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(changbum_chun2_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(changbum_chun3_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(kim_chun_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(kou_li_wang1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(maheshweri_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(rafiullah1_type_functions);
        root_finding_functions3.addSeparator();
        root_finding_functions3.add(rafis_rafiullah_type_functions);


        add(root_finding_functions);
        add(root_finding_functions2);
        add(root_finding_functions3);
        addSeparator();

        fractal_functions[MainWindow.BARNSLEY1] = new JRadioButtonMenuItem(functionNames[MainWindow.BARNSLEY1]);
        fractal_functions[MainWindow.BARNSLEY1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY1);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY1]);
        functions_button_group.add(fractal_functions[MainWindow.BARNSLEY1]);

        fractal_functions[MainWindow.BARNSLEY2] = new JRadioButtonMenuItem(functionNames[MainWindow.BARNSLEY2]);
        fractal_functions[MainWindow.BARNSLEY2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY2);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY2]);
        functions_button_group.add(fractal_functions[MainWindow.BARNSLEY2]);

        fractal_functions[MainWindow.BARNSLEY3] = new JRadioButtonMenuItem(functionNames[MainWindow.BARNSLEY3]);
        fractal_functions[MainWindow.BARNSLEY3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.BARNSLEY3);

            }
        });
        barnsley_type_functions.add(fractal_functions[MainWindow.BARNSLEY3]);
        functions_button_group.add(fractal_functions[MainWindow.BARNSLEY3]);
        add(barnsley_type_functions);
        addSeparator();

        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1] = new JRadioButtonMenuItem(functionNames[MainWindow.SZEGEDI_BUTTERFLY1]);
        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SZEGEDI_BUTTERFLY1);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1]);
        functions_button_group.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY1]);

        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2] = new JRadioButtonMenuItem(functionNames[MainWindow.SZEGEDI_BUTTERFLY2]);
        fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SZEGEDI_BUTTERFLY2);

            }
        });
        szegedi_butterfly_type_functions.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2]);
        functions_button_group.add(fractal_functions[MainWindow.SZEGEDI_BUTTERFLY2]);

        add(szegedi_butterfly_type_functions);
        addSeparator();

        fractal_functions[MainWindow.NOVA] = new JRadioButtonMenuItem(functionNames[MainWindow.NOVA]);
        fractal_functions[MainWindow.NOVA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NOVA);

            }
        });
        add(fractal_functions[MainWindow.NOVA]);
        functions_button_group.add(fractal_functions[MainWindow.NOVA]);

        addSeparator();
        fractal_functions[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE] = new JRadioButtonMenuItem(functionNames[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE]);
        fractal_functions[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE);

            }
        });
        add(fractal_functions[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE]);
        functions_button_group.add(fractal_functions[MainWindow.NEWTON_THIRD_DEGREE_PARAMETER_SPACE]);

        addSeparator();

        fractal_functions[MainWindow.MANDELBAR] = new JRadioButtonMenuItem(functionNames[MainWindow.MANDELBAR]);
        fractal_functions[MainWindow.MANDELBAR].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANDELBAR);

            }
        });
        mandelbrot_type_functions.add(fractal_functions[MainWindow.MANDELBAR]);
        functions_button_group.add(fractal_functions[MainWindow.MANDELBAR]);

        fractal_functions[MainWindow.SPIDER] = new JRadioButtonMenuItem(functionNames[MainWindow.SPIDER]);
        fractal_functions[MainWindow.SPIDER].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SPIDER);

            }
        });
        add(fractal_functions[MainWindow.SPIDER]);
        functions_button_group.add(fractal_functions[MainWindow.SPIDER]);

        addSeparator();

        fractal_functions[MainWindow.MANOWAR] = new JRadioButtonMenuItem(functionNames[MainWindow.MANOWAR]);
        fractal_functions[MainWindow.MANOWAR].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MANOWAR);

            }
        });
        add(fractal_functions[MainWindow.MANOWAR]);
        functions_button_group.add(fractal_functions[MainWindow.MANOWAR]);

        addSeparator();

        fractal_functions[MainWindow.PHOENIX] = new JRadioButtonMenuItem(functionNames[MainWindow.PHOENIX]);
        fractal_functions[MainWindow.PHOENIX].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.PHOENIX);

            }
        });
        add(fractal_functions[MainWindow.PHOENIX]);
        functions_button_group.add(fractal_functions[MainWindow.PHOENIX]);

        addSeparator();

        fractal_functions[MainWindow.SIERPINSKI_GASKET] = new JRadioButtonMenuItem(functionNames[MainWindow.SIERPINSKI_GASKET]);
        fractal_functions[MainWindow.SIERPINSKI_GASKET].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SIERPINSKI_GASKET);

            }
        });
        add(fractal_functions[MainWindow.SIERPINSKI_GASKET]);
        functions_button_group.add(fractal_functions[MainWindow.SIERPINSKI_GASKET]);
        addSeparator();
        
        fractal_functions[MainWindow.KLEINIAN] = new JRadioButtonMenuItem(functionNames[MainWindow.KLEINIAN]);
        fractal_functions[MainWindow.KLEINIAN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.KLEINIAN);

            }
        });
        add(fractal_functions[MainWindow.KLEINIAN]);
        functions_button_group.add(fractal_functions[MainWindow.KLEINIAN]);
        addSeparator();
        
        fractal_functions[MainWindow.MAGNETIC_PENDULUM] = new JRadioButtonMenuItem(functionNames[MainWindow.MAGNETIC_PENDULUM]);
        fractal_functions[MainWindow.MAGNETIC_PENDULUM].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.MAGNETIC_PENDULUM);

            }
        });
        add(fractal_functions[MainWindow.MAGNETIC_PENDULUM]);
        functions_button_group.add(fractal_functions[MainWindow.MAGNETIC_PENDULUM]);
        addSeparator();
        
        fractal_functions[MainWindow.INERTIA_GRAVITY] = new JRadioButtonMenuItem(functionNames[MainWindow.INERTIA_GRAVITY]);
        fractal_functions[MainWindow.INERTIA_GRAVITY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.INERTIA_GRAVITY);

            }
        });
        add(fractal_functions[MainWindow.INERTIA_GRAVITY]);
        functions_button_group.add(fractal_functions[MainWindow.INERTIA_GRAVITY]);
        addSeparator();            
        
        fractal_functions[MainWindow.LYAPUNOV] = new JRadioButtonMenuItem(functionNames[MainWindow.LYAPUNOV]);
        fractal_functions[MainWindow.LYAPUNOV].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LYAPUNOV);

            }
        });
        add(fractal_functions[MainWindow.LYAPUNOV]);
        functions_button_group.add(fractal_functions[MainWindow.LYAPUNOV]);
        addSeparator();

        fractal_functions[MainWindow.FROTHY_BASIN] = new JRadioButtonMenuItem(functionNames[MainWindow.FROTHY_BASIN]);
        fractal_functions[MainWindow.FROTHY_BASIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FROTHY_BASIN);

            }
        });
        add(fractal_functions[MainWindow.FROTHY_BASIN]);
        functions_button_group.add(fractal_functions[MainWindow.FROTHY_BASIN]);
        addSeparator();

        fractal_functions[MainWindow.EXP] = new JRadioButtonMenuItem(functionNames[MainWindow.EXP]);
        fractal_functions[MainWindow.EXP].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.EXP);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.EXP]);
        functions_button_group.add(fractal_functions[MainWindow.EXP]);

        fractal_functions[MainWindow.LOG] = new JRadioButtonMenuItem(functionNames[MainWindow.LOG]);
        fractal_functions[MainWindow.LOG].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.LOG);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.LOG]);
        functions_button_group.add(fractal_functions[MainWindow.LOG]);

        fractal_functions[MainWindow.SIN] = new JRadioButtonMenuItem(functionNames[MainWindow.SIN]);
        fractal_functions[MainWindow.SIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SIN);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.SIN]);
        functions_button_group.add(fractal_functions[MainWindow.SIN]);

        fractal_functions[MainWindow.COS] = new JRadioButtonMenuItem(functionNames[MainWindow.COS]);
        fractal_functions[MainWindow.COS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COS);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COS]);
        functions_button_group.add(fractal_functions[MainWindow.COS]);

        fractal_functions[MainWindow.TAN] = new JRadioButtonMenuItem(functionNames[MainWindow.TAN]);
        fractal_functions[MainWindow.TAN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.TAN);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.TAN]);
        functions_button_group.add(fractal_functions[MainWindow.TAN]);

        fractal_functions[MainWindow.COT] = new JRadioButtonMenuItem(functionNames[MainWindow.COT]);
        fractal_functions[MainWindow.COT].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COT);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COT]);
        functions_button_group.add(fractal_functions[MainWindow.COT]);

        fractal_functions[MainWindow.SINH] = new JRadioButtonMenuItem(functionNames[MainWindow.SINH]);
        fractal_functions[MainWindow.SINH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SINH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.SINH]);
        functions_button_group.add(fractal_functions[MainWindow.SINH]);

        fractal_functions[MainWindow.COSH] = new JRadioButtonMenuItem(functionNames[MainWindow.COSH]);
        fractal_functions[MainWindow.COSH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COSH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COSH]);
        functions_button_group.add(fractal_functions[MainWindow.COSH]);

        fractal_functions[MainWindow.TANH] = new JRadioButtonMenuItem(functionNames[MainWindow.TANH]);
        fractal_functions[MainWindow.TANH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.TANH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.TANH]);
        functions_button_group.add(fractal_functions[MainWindow.TANH]);

        fractal_functions[MainWindow.COTH] = new JRadioButtonMenuItem(functionNames[MainWindow.COTH]);
        fractal_functions[MainWindow.COTH].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.COTH);

            }
        });
        math_type_functions.add(fractal_functions[MainWindow.COTH]);
        functions_button_group.add(fractal_functions[MainWindow.COTH]);

        fractal_functions[MainWindow.FORMULA30] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA30]);
        fractal_functions[MainWindow.FORMULA30].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA30);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA30]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA30]);

        fractal_functions[MainWindow.FORMULA31] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA31]);
        fractal_functions[MainWindow.FORMULA31].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA31);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA31]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA31]);

        fractal_functions[MainWindow.FORMULA32] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA32]);
        fractal_functions[MainWindow.FORMULA32].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA32);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA32]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA32]);

        fractal_functions[MainWindow.FORMULA33] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA33]);
        fractal_functions[MainWindow.FORMULA33].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA33);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA33]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA33]);

        fractal_functions[MainWindow.FORMULA34] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA34]);
        fractal_functions[MainWindow.FORMULA34].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA34);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA34]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA34]);

        fractal_functions[MainWindow.FORMULA35] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA35]);
        fractal_functions[MainWindow.FORMULA35].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA35);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA35]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA35]);

        fractal_functions[MainWindow.FORMULA36] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA36]);
        fractal_functions[MainWindow.FORMULA36].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA36);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA36]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA36]);

        fractal_functions[MainWindow.FORMULA37] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA37]);
        fractal_functions[MainWindow.FORMULA37].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA37);

            }
        });
        general_math_type_functions.add(fractal_functions[MainWindow.FORMULA37]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA37]);

        fractal_functions[MainWindow.FORMULA42] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA42]);
        fractal_functions[MainWindow.FORMULA42].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA42);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA42]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA42]);

        fractal_functions[MainWindow.FORMULA43] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA43]);
        fractal_functions[MainWindow.FORMULA43].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA43);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA43]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA43]);

        fractal_functions[MainWindow.FORMULA44] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA44]);
        fractal_functions[MainWindow.FORMULA44].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA44);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA44]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA44]);

        fractal_functions[MainWindow.FORMULA45] = new JRadioButtonMenuItem(functionNames[MainWindow.FORMULA45]);
        fractal_functions[MainWindow.FORMULA45].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.FORMULA45);

            }
        });
        general_newton_variant_functions.add(fractal_functions[MainWindow.FORMULA45]);
        functions_button_group.add(fractal_functions[MainWindow.FORMULA45]);

        add(math_type_functions);
        addSeparator();

        fractal_functions[MainWindow.USER_FORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.USER_FORMULA]);
        fractal_functions[MainWindow.USER_FORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA);

            }
        });
        fractal_functions[MainWindow.USER_FORMULA].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.USER_FORMULA]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED] = new JRadioButtonMenuItem(functionNames[MainWindow.USER_FORMULA_ITERATION_BASED]);
        fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_ITERATION_BASED);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED]);
        functions_button_group.add(fractal_functions[MainWindow.USER_FORMULA_ITERATION_BASED]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL] = new JRadioButtonMenuItem(functionNames[MainWindow.USER_FORMULA_CONDITIONAL]);
        fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_CONDITIONAL);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL]);
        functions_button_group.add(fractal_functions[MainWindow.USER_FORMULA_CONDITIONAL]);

        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_COUPLED] = new JRadioButtonMenuItem(functionNames[MainWindow.USER_FORMULA_COUPLED]);
        fractal_functions[MainWindow.USER_FORMULA_COUPLED].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_COUPLED);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_COUPLED]);
        functions_button_group.add(fractal_functions[MainWindow.USER_FORMULA_COUPLED]);
        
        user_formulas_type_functions.addSeparator();

        fractal_functions[MainWindow.USER_FORMULA_NOVA] = new JRadioButtonMenuItem(functionNames[MainWindow.USER_FORMULA_NOVA]);
        fractal_functions[MainWindow.USER_FORMULA_NOVA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.USER_FORMULA_NOVA);

            }
        });
        user_formulas_type_functions.add(fractal_functions[MainWindow.USER_FORMULA_NOVA]);
        functions_button_group.add(fractal_functions[MainWindow.USER_FORMULA_NOVA]);
                      

        add(user_formulas_type_functions);

        fractal_functions[MainWindow.HALLEY3] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEY3]);
        fractal_functions[MainWindow.HALLEY3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEY3);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEY3]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEY3]);

        fractal_functions[MainWindow.HALLEY4] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEY4]);
        fractal_functions[MainWindow.HALLEY4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEY4);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEY4]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEY4]);

        fractal_functions[MainWindow.HALLEYGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYGENERALIZED3]);
        fractal_functions[MainWindow.HALLEYGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYGENERALIZED3);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYGENERALIZED3]);

        fractal_functions[MainWindow.HALLEYGENERALIZED8] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYGENERALIZED8]);
        fractal_functions[MainWindow.HALLEYGENERALIZED8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYGENERALIZED8);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYGENERALIZED8]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYGENERALIZED8]);

        fractal_functions[MainWindow.HALLEYSIN] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYSIN]);
        fractal_functions[MainWindow.HALLEYSIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYSIN);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYSIN]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYSIN]);

        fractal_functions[MainWindow.HALLEYCOS] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYCOS]);
        fractal_functions[MainWindow.HALLEYCOS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYCOS);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYCOS]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYCOS]);

        fractal_functions[MainWindow.HALLEYPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYPOLY]);
        fractal_functions[MainWindow.HALLEYPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYPOLY);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYPOLY]);

        fractal_functions[MainWindow.HALLEYFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.HALLEYFORMULA]);
        fractal_functions[MainWindow.HALLEYFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HALLEYFORMULA);

            }
        });
        halley_type_functions.add(fractal_functions[MainWindow.HALLEYFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.HALLEYFORMULA]);

        fractal_functions[MainWindow.SCHRODER3] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODER3]);
        fractal_functions[MainWindow.SCHRODER3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODER3);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODER3]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODER3]);

        fractal_functions[MainWindow.SCHRODER4] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODER4]);
        fractal_functions[MainWindow.SCHRODER4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODER4);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODER4]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODER4]);

        fractal_functions[MainWindow.SCHRODERGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERGENERALIZED3]);
        fractal_functions[MainWindow.SCHRODERGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERGENERALIZED3);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERGENERALIZED3]);

        fractal_functions[MainWindow.SCHRODERGENERALIZED8] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERGENERALIZED8]);
        fractal_functions[MainWindow.SCHRODERGENERALIZED8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERGENERALIZED8);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERGENERALIZED8]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERGENERALIZED8]);

        fractal_functions[MainWindow.SCHRODERSIN] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERSIN]);
        fractal_functions[MainWindow.SCHRODERSIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERSIN);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERSIN]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERSIN]);

        fractal_functions[MainWindow.SCHRODERCOS] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERCOS]);
        fractal_functions[MainWindow.SCHRODERCOS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERCOS);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERCOS]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERCOS]);

        fractal_functions[MainWindow.SCHRODERPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERPOLY]);
        fractal_functions[MainWindow.SCHRODERPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERPOLY);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERPOLY]);

        fractal_functions[MainWindow.SCHRODERFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.SCHRODERFORMULA]);
        fractal_functions[MainWindow.SCHRODERFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SCHRODERFORMULA);

            }
        });
        schroder_type_functions.add(fractal_functions[MainWindow.SCHRODERFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.SCHRODERFORMULA]);

        fractal_functions[MainWindow.HOUSEHOLDER3] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDER3]);
        fractal_functions[MainWindow.HOUSEHOLDER3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDER3);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDER3]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDER3]);

        fractal_functions[MainWindow.HOUSEHOLDER4] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDER4]);
        fractal_functions[MainWindow.HOUSEHOLDER4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDER4);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDER4]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDER4]);

        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERGENERALIZED3]);
        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERGENERALIZED3);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED3]);

        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERGENERALIZED8]);
        fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERGENERALIZED8);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERGENERALIZED8]);

        fractal_functions[MainWindow.HOUSEHOLDERSIN] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERSIN]);
        fractal_functions[MainWindow.HOUSEHOLDERSIN].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERSIN);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERSIN]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERSIN]);

        fractal_functions[MainWindow.HOUSEHOLDERCOS] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERCOS]);
        fractal_functions[MainWindow.HOUSEHOLDERCOS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERCOS);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERCOS]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERCOS]);

        fractal_functions[MainWindow.HOUSEHOLDERPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERPOLY]);
        fractal_functions[MainWindow.HOUSEHOLDERPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERPOLY);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERPOLY]);

        fractal_functions[MainWindow.HOUSEHOLDERFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.HOUSEHOLDERFORMULA]);
        fractal_functions[MainWindow.HOUSEHOLDERFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.HOUSEHOLDERFORMULA);

            }
        });
        householder_type_functions.add(fractal_functions[MainWindow.HOUSEHOLDERFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.HOUSEHOLDERFORMULA]);

        fractal_functions[MainWindow.SECANT3] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANT3]);
        fractal_functions[MainWindow.SECANT3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANT3);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANT3]);
        functions_button_group.add(fractal_functions[MainWindow.SECANT3]);

        fractal_functions[MainWindow.SECANT4] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANT4]);
        fractal_functions[MainWindow.SECANT4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANT4);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANT4]);
        functions_button_group.add(fractal_functions[MainWindow.SECANT4]);

        fractal_functions[MainWindow.SECANTGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANTGENERALIZED3]);
        fractal_functions[MainWindow.SECANTGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTGENERALIZED3);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.SECANTGENERALIZED3]);

        fractal_functions[MainWindow.SECANTGENERALIZED8] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANTGENERALIZED8]);
        fractal_functions[MainWindow.SECANTGENERALIZED8].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTGENERALIZED8);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTGENERALIZED8]);
        functions_button_group.add(fractal_functions[MainWindow.SECANTGENERALIZED8]);

        fractal_functions[MainWindow.SECANTCOS] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANTCOS]);
        fractal_functions[MainWindow.SECANTCOS].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTCOS);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTCOS]);
        functions_button_group.add(fractal_functions[MainWindow.SECANTCOS]);

        fractal_functions[MainWindow.SECANTPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANTPOLY]);
        fractal_functions[MainWindow.SECANTPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTPOLY);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.SECANTPOLY]);

        fractal_functions[MainWindow.SECANTFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.SECANTFORMULA]);
        fractal_functions[MainWindow.SECANTFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.SECANTFORMULA);

            }
        });
        secant_type_functions.add(fractal_functions[MainWindow.SECANTFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.SECANTFORMULA]);

        fractal_functions[MainWindow.STEFFENSEN3] = new JRadioButtonMenuItem(functionNames[MainWindow.STEFFENSEN3]);
        fractal_functions[MainWindow.STEFFENSEN3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSEN3);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSEN3]);
        functions_button_group.add(fractal_functions[MainWindow.STEFFENSEN3]);

        fractal_functions[MainWindow.STEFFENSEN4] = new JRadioButtonMenuItem(functionNames[MainWindow.STEFFENSEN4]);
        fractal_functions[MainWindow.STEFFENSEN4].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSEN4);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSEN4]);
        functions_button_group.add(fractal_functions[MainWindow.STEFFENSEN4]);

        fractal_functions[MainWindow.STEFFENSENGENERALIZED3] = new JRadioButtonMenuItem(functionNames[MainWindow.STEFFENSENGENERALIZED3]);
        fractal_functions[MainWindow.STEFFENSENGENERALIZED3].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSENGENERALIZED3);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSENGENERALIZED3]);
        functions_button_group.add(fractal_functions[MainWindow.STEFFENSENGENERALIZED3]);

        fractal_functions[MainWindow.STEFFENSENPOLY] = new JRadioButtonMenuItem(functionNames[MainWindow.STEFFENSENPOLY]);
        fractal_functions[MainWindow.STEFFENSENPOLY].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSENPOLY);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSENPOLY]);
        functions_button_group.add(fractal_functions[MainWindow.STEFFENSENPOLY]);

        fractal_functions[MainWindow.STEFFENSENFORMULA] = new JRadioButtonMenuItem(functionNames[MainWindow.STEFFENSENFORMULA]);
        fractal_functions[MainWindow.STEFFENSENFORMULA].addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setFunction(MainWindow.STEFFENSENFORMULA);

            }
        });
        steffensen_type_functions.add(fractal_functions[MainWindow.STEFFENSENFORMULA]);
        functions_button_group.add(fractal_functions[MainWindow.STEFFENSENFORMULA]);

        for(int i = MainWindow.MULLER3; i <=  MainWindow.MULLERFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            muller_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.PARHALLEY3; i <=  MainWindow.PARHALLEYFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            parhalley_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.LAGUERRE3; i <=  MainWindow.LAGUERREFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            laguerre_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.DURAND_KERNER3; i <=  MainWindow.DURAND_KERNERPOLY; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            durand_kerner_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.BAIRSTOW3; i <=  MainWindow.BAIRSTOWPOLY; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            bairstow_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.NEWTON_HINES3; i <=  MainWindow.NEWTON_HINESFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            newton_hines_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.WHITTAKER3; i <=  MainWindow.WHITTAKERFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            whittaker_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.WHITTAKERDOUBLECONVEX3; i <=  MainWindow.WHITTAKERDOUBLECONVEXFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            whittaker_double_convex_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.SUPERHALLEY3; i <=  MainWindow.SUPERHALLEYFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            super_halley_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.TRAUB_OSTROWSKI3; i <=  MainWindow.TRAUB_OSTROWSKIFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            traub_ostrowski_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.STIRLING3; i <=  MainWindow.STIRLINGFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            stirling_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.MIDPOINT3; i <=  MainWindow.MIDPOINTFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            midpoint_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.ABERTH_EHRLICH3; i <=  MainWindow.ABERTH_EHRLICHPOLY; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            aberth_ehrlich_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.JARATT3; i <=  MainWindow.JARATTFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            jaratt_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.JARATT23; i <=  MainWindow.JARATT2FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            jaratt2_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.THIRDORDERNEWTON3; i <=  MainWindow.THIRDORDERNEWTONFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            third_order_newtton_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.WEERAKOON_FERNANDO3; i <=  MainWindow.WEERAKOON_FERNANDOFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            weerakoon_fernando_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.HOUSEHOLDER33; i <=  MainWindow.HOUSEHOLDER3FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            householder3_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.ABBASBANDY3; i <=  MainWindow.ABBASBANDYFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            abbasbandy_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CONTRA_HARMONIC_NEWTON3; i <=  MainWindow.CONTRA_HARMONIC_NEWTONFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            contra_harmonic_newton_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CHUN_HAM3; i <=  MainWindow.CHUN_HAMFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            chun_ham_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CHUN_KIM3; i <=  MainWindow.CHUN_KIMFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            chun_kim_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.EULER_CHEBYSHEV3; i <=  MainWindow.EULER_CHEBYSHEVFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            euler_chebyshev_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.EZZATI_SALEKI23; i <=  MainWindow.EZZATI_SALEKI2FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            ezzati_saleki2_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.HOMEIER13; i <=  MainWindow.HOMEIER1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            homeier1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.ABBASBANDY23; i <=  MainWindow.ABBASBANDY2FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            abbasbandy2_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.ABBASBANDY33; i <=  MainWindow.ABBASBANDY3FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            abbasbandy3_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.POPOVSKI13; i <=  MainWindow.POPOVSKI1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            popovski1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CHANGBUM_CHUN13; i <=  MainWindow.CHANGBUM_CHUN1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            changbum_chun1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CHANGBUM_CHUN23; i <=  MainWindow.CHANGBUM_CHUN2FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            changbum_chun2_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.KING33; i <=  MainWindow.KING3FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            king3_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.HOMEIER23; i <=  MainWindow.HOMEIER2FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            homeier2_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.KIM_CHUN3; i <=  MainWindow.KIM_CHUNFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            kim_chun_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.KOU_LI_WANG13; i <=  MainWindow.KOU_LI_WANG1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            kou_li_wang1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.MAHESHWERI3; i <=  MainWindow.MAHESHWERIFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            maheshweri_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.RAFIULLAH13; i <=  MainWindow.RAFIULLAH1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            rafiullah1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.RAFIS_RAFIULLAH3; i <=  MainWindow.RAFIS_RAFIULLAHFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            rafis_rafiullah_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.CHANGBUM_CHUN33; i <=  MainWindow.CHANGBUM_CHUN3FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            changbum_chun3_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.EZZATI_SALEKI13; i <=  MainWindow.EZZATI_SALEKI1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            ezzati_saleki1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.FENG3; i <=  MainWindow.FENGFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            feng_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.KING13; i <=  MainWindow.KING1FORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            king1_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.NOOR_GUPTA3; i <=  MainWindow.NOOR_GUPTAFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            noor_gupta_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.HARMONIC_SIMPSON_NEWTON3; i <=  MainWindow.HARMONIC_SIMPSON_NEWTONFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            harmonic_simpson_newton_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.NEDZHIBOV3; i <=  MainWindow.NEDZHIBOVFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            nedzhibov_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        for(int i = MainWindow.SIMPSON_NEWTON3; i <=  MainWindow.SIMPSON_NEWTONFORMULA; i++) {

            final int temp = i;

            fractal_functions[i] = new JRadioButtonMenuItem(functionNames[i]);
            fractal_functions[i].addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    ptr.setFunction(temp);

                }
            });
            simpson_newton_type_functions.add(fractal_functions[i]);
            functions_button_group.add(fractal_functions[i]);
        }

        burning_ship_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setBurningShip();

            }
        });           

        mandel_grass_opt.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ptr.setMandelGrass();

            }
        });

        fractal_functions[function].setSelected(true);

    }

    public JRadioButtonMenuItem[] getFractalFunctions() {

        return fractal_functions;

    }

    public JCheckBoxMenuItem getBurningShipOpt() {

        return burning_ship_opt;

    }

    public JMenuItem getMandelGrassOpt() {

        return mandel_grass_opt;

    }

    private ImageIcon getIcon(String path) {

        return new ImageIcon(getClass().getResource(path));

    }

}
